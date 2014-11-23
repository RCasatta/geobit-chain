/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 geobit.io 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.geobit.chain.clients;

import static io.geobit.common.statics.Log.error;
import static io.geobit.common.statics.Log.log;
import io.geobit.chain.dispatchers.BlockAndTransactionDispatcher;
import io.geobit.chain.entity.blockr.BlockrBalance;
import io.geobit.chain.entity.blockr.BlockrBalanceData;
import io.geobit.chain.entity.blockr.TxInfo;
import io.geobit.chain.entity.blockr.TxInfoData;
import io.geobit.chain.entity.blockr.V;
import io.geobit.chain.providers.pushtx.PushTxProvider;
import io.geobit.common.entity.Block;
import io.geobit.common.entity.Transaction;
import io.geobit.common.entity.TransactionInOut;
import io.geobit.common.providers.BalanceProvider;
import io.geobit.common.providers.BlockProvider;
import io.geobit.common.providers.ReceivedProvider;
import io.geobit.common.providers.TransHexProvider;
import io.geobit.common.providers.TransactionProvider;
import io.geobit.common.statics.SimpleDateFormats;
import io.geobit.common.statics.StaticNumbers;
import io.geobit.common.statics.StaticStrings;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class BlockrHTTPClient implements BalanceProvider, ReceivedProvider, TransactionProvider, PushTxProvider, TransHexProvider, BlockProvider {
	private static final String prefix= "http://btc.blockr.io/api/v1";

	private WebResource balance;
	private WebResource received;
	private WebResource transaction;
	private WebResource pushTx;
	//	private WebResource addressTxs;
	private WebResource transHex;
	private WebResource lastBlock;
	private WebResource blockNumber;
	private WebResource blockTxs;

	public BlockrHTTPClient() {
		super();

		Client client;
		Client clientNoTimeout;
		client= Client.create();
		clientNoTimeout= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(   StaticNumbers.READ_TIMEOUT);

		balance     = client.resource(prefix + "/address/balance/" );
		received    = client.resource(prefix + "/address/info/" );
		transaction = client.resource(prefix + "/tx/info/" );
		pushTx      = client.resource(prefix + "/tx/push/" );
		//		addressTxs  = client.resource(prefix + "/address/txs/");
		transHex    = client.resource(prefix + "/tx/raw/");
		lastBlock   = client.resource(prefix + "/block/info/last");
		blockNumber = client.resource(prefix + "/block/raw/");
		blockTxs    = clientNoTimeout.resource(prefix + "/block/txs/"); 
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public Long getBalance(String address) {
		BlockrBalance result = balance
				.path(address)
				.accept( MediaType.APPLICATION_JSON)
				.header("User-Agent", StaticStrings.USER_AGENT)
				.get(BlockrBalance.class);

		try {
			if(result!=null && result.getCode()==200) {
				BlockrBalanceData balData= result.getData();
				if(balData!=null) {	
					long bal = Math.round(balData.getBalance()*100000000L);
					return bal;
				}
			}
		} catch (Exception e) {
			String mes = "exception on blockr getBalance " + e.getMessage();
		    error(mes );

		}
		return null;
	}

	@Override
	public Long getReceived(String address) {
		BlockrBalance result = received
				.path(address)
				.accept( MediaType.APPLICATION_JSON)
				.header("User-Agent", StaticStrings.USER_AGENT)
				.get(BlockrBalance.class);

		try {
			if(result!=null && result.getCode()==200) {
				BlockrBalanceData balData= result.getData();
				if(balData!=null) {	
					long bal = Math.round(balData.getTotalreceived()*100000000L);
					return bal;
				}
			}
		} catch (Exception e) {
			String mes = "exception on blockr getReceived " + e.getMessage();
			error(mes );
		}
		return null;
	}

	@Override
	public Transaction getTransaction(String txhash) {
		TxInfo tx=transaction
				.path(txhash)
				.get(TxInfo.class);
		
		if(tx!=null) {
			log(tx.toString());
			TxInfoData data = tx.getData();
			if(data!=null) {
				Transaction t =new Transaction();
				t.setBlockHeight(data.getBlock() );
				t.setHash(data.getTx());

				BlockAndTransactionDispatcher disp=BlockAndTransactionDispatcher.getInstance();
				Block b=disp.getBlock(t.getBlockHeight());
				t.setTimestamp(b.getTime());

				List<TransactionInOut> outs=new LinkedList<TransactionInOut>();
				for(V current : data.getVouts()) {
					TransactionInOut a=new TransactionInOut();
					a.setAddress( current.getAddress() );
					a.setIndex( current.getN() );
					a.setSpent( current.getIs_spent()==1 );
					a.setValue( Math.round(current.getAmount()*100000000) );
					outs.add(a);
				}
				Collections.sort(outs);
				t.setOut(outs);
				List<TransactionInOut> ins=new LinkedList<TransactionInOut>();
				for(V current : data.getVins()) {
					TransactionInOut a=new TransactionInOut();
					a.setAddress( current.getAddress() );
					//a.setIndex( current.getN() );
					a.setValue( -Math.round(current.getAmount()*100000000) );
					ins.add(a);
				}
				Collections.sort(ins);
				t.setIn(ins);

				return t;

			} else error("tx.getData()!=null");
		} else 
			error("tx!=null");
		
		
		return null;

	}

	@Override
	public Boolean pushTx(String hex) {
		try {
			log("Blockr pushTx " + hex);
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("hex", hex);
			ClientResponse response = pushTx
					.header("User-Agent", StaticStrings.USER_AGENT)
					.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					.post(ClientResponse.class, formData);

			String ret=response.getEntity(String.class);
			log("pushing transaction returned " + ret);
			return response.getStatus()==200;
		} catch (Exception e) {
			error("Blockr PushTx error " + e.getMessage());
		}
		return null;

	}

	@Override
	public String getTransHex(String txhash) {

		String returned=null;
		try {
			String result=transHex
					.path(txhash)
					.queryParam("format", "json")
					.accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);

			JSONObject obj = new JSONObject(result);
			returned=obj.getJSONObject("data").getJSONObject("tx").getString("hex");
			return returned;
		} catch (Exception e) {
			error("Blockr getTransactionHex error " + e.getMessage());
		}
		return returned;
	}
	@Override
	public String toString() {
		return getPrefix();
	}


	@Override
	public Block getBlock(Integer height) {
		try {
			String rawBlock=blockNumber
					.path(height.toString())
					.accept(MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);
			JSONObject obj      = new JSONObject(rawBlock);
			JSONObject jsonData = obj.getJSONObject("data");
			String hash     = jsonData.getString("hash");
			int heightRet       = jsonData.getInt("height");
			Long timestamp = jsonData.getLong("time");
			Block b = new Block();
			b.setHash(hash);
			b.setHeight( heightRet ) ;

			b.setTime( timestamp );
			return b;

		} catch (Exception e) {
			error("error on Blockr getBlock() " + e.getMessage() );
		}

		return null;
	}
}
