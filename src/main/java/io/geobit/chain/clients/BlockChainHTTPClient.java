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
import io.geobit.chain.entity.blockchain.BlockChainAddress;
import io.geobit.chain.entity.blockchain.BlockChainTransaction;
import io.geobit.common.entity.AddressTransactions;
import io.geobit.common.entity.Block;
import io.geobit.common.entity.Transaction;
import io.geobit.common.providers.AddressTransactionsProvider;
import io.geobit.common.providers.BalanceProvider;
import io.geobit.common.providers.BlockProvider;
import io.geobit.common.providers.PushTxProvider;
import io.geobit.common.providers.ReceivedProvider;
import io.geobit.common.providers.TransHexProvider;
import io.geobit.common.providers.TransactionProvider;
import io.geobit.common.statics.ApiKeys;
import io.geobit.common.statics.StaticNumbers;
import io.geobit.common.statics.StaticStrings;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
public class BlockChainHTTPClient implements BalanceProvider, AddressTransactionsProvider, 
PushTxProvider, TransactionProvider, ReceivedProvider, BlockProvider,
TransHexProvider {
	//	private static final String dns="https://blockchain.info";
	private static final String prefix= "http://blockchain.info";

	private WebResource balance;
	private WebResource received;
	private WebResource addrTxs;
	private WebResource pushTx;
	private WebResource transaction;
	private WebResource block;

	public BlockChainHTTPClient() {
		super();

		Client client;

		client= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(StaticNumbers.READ_TIMEOUT);

		balance     = client.resource(prefix + "/q/addressbalance/" );
		received    = client.resource(prefix + "/q/getreceivedbyaddress/" );
		addrTxs     = client.resource(prefix + "/address/" );
		pushTx      = client.resource(prefix + "/pushtx" );
		transaction = client.resource(prefix + "/rawtx/" );
		block       = client.resource(prefix + "/block-height/");
		//transaction.path(path);
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public Transaction getTransaction(String txHash) {

		try {

			BlockChainTransaction add=transaction
					.path(txHash)
					.queryParam("api_code", ApiKeys.BLOCKCHAIN)
					.accept( MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(BlockChainTransaction.class);

			Transaction t=add.getTransaction();

			BlockAndTransactionDispatcher disp=BlockAndTransactionDispatcher.getInstance();
			Block b=disp.getBlock(t.getBlockHeight());
			t.setTimestamp(b.getTime());

			return t;
		} catch (Exception e) {
			error("BlockChain getTransaction error " + e.getMessage());

		}
		return null;
		//		Transaction t=new Transaction();
		//		BlockChainProcTrans procTrans= add.getBlockChainProcTrans(myAddr);
		//
		//		t.setHash(      procTrans.getHash()    );
		//		t.setIndex(     procTrans.getIndex()   );
		//		//		t.setTxIndex(   procTrans.getTxIndex()  );
		//		t.setFrom(      procTrans.getAddress() );
		//		t.setValue(     procTrans.getValue()   );
		//		//		t.setTimestamp( procTrans.getTimestamp() );
		//		t.setBlockHeight( procTrans.getBlockHeight() );
		//		t.setSpent(procTrans.getSpent());
		//
		//		Integer height=procTrans.getBlockHeight();
		//		if(height!=null) {  /* it's confirmed, getting time */
		//			Block b=getBlock(height);
		//			t.setTimestamp(b.getTime());
		//		}
		//
		//		GeobitAWSClient awsClient = GeobitAWSClient.getInstance();
		//		Map<String, String> m=awsClient.getSenderAddressMap( t.getFrom() );
		//		if(m!=null ) {
		//			String message=m.get(Strings.ADDR_MESSAGE);
		//			if(message!=null)
		//				t.setMessage(message);
		//			String alias=m.get(Strings.ADDR_ALIAS);
		//			if(alias!=null && alias.length()>0)
		//				t.setFromAlias(alias);
		//			String password=m.get(Strings.ADDR_PSWD);
		//			if(password!=null && password.length()>0)
		//				t.setPasswordHash(password);
		//		}


	}

	@Override
	public Boolean pushTx(String hex) {
		try {
			log("Blockchain pushTx " + hex);
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("tx", hex);
			ClientResponse response = pushTx
					.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
					.header("User-Agent", StaticStrings.USER_AGENT)
					.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					.post(ClientResponse.class, formData);

			String ret=response.getEntity(String.class);
			log("pushing transaction returned " + ret);
			return response.getStatus()==200;
		} catch (Exception e) {
			error("Blockchain PushTx error " + e.getMessage());
		}
		return null;
	}

	@Override
	public AddressTransactions getAddressTransactions(String address) {
		try {
			AddressTransactions addressTxs=new AddressTransactions();

			BlockChainAddress add=this.addrTxs
					.path(address)
					.queryParam("format", "json")
					.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
					.accept(MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(BlockChainAddress.class);


			addressTxs.setAddress(add.getAddress());
			addressTxs.setBalance(add.getFinal_balance());
			List<Transaction> lista=new LinkedList<Transaction>();
			for(BlockChainTransaction bt : add.getTxs()) {
				Transaction t = bt.getTransaction();
				
				BlockAndTransactionDispatcher disp=BlockAndTransactionDispatcher.getInstance();
				Block b=disp.getBlock(t.getBlockHeight());
				t.setTimestamp(b.getTime());
				lista.add(t);
			}
			Collections.sort(lista);
			addressTxs.setTransactions(lista);
			
			return addressTxs;
		} catch(Exception e) {}
		return null;
	}
	
		@Override
		public Long getBalance(String address) {

			Long resultLong=null;
			try {
				String result = balance
						.path(address)
						.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
						.accept(MediaType.TEXT_PLAIN)
						.header("User-Agent", StaticStrings.USER_AGENT)
						.get(String.class);

				resultLong = Long.parseLong(result);
			} catch(Exception e) {
				String mes = "exception on blockchain getBalance " + e.getMessage();
				error(mes);
			}
			return resultLong;
		}

		@Override
		public Long getReceived(String address) {
			String result = received
					.path(address)
					.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
					.accept(MediaType.TEXT_PLAIN)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);
			Long resultLong=null;
			try {
				resultLong = Long.parseLong(result);
			} catch(Exception e) {}
			return resultLong;
		}

		public Block getBlock(Integer height) {

			String result=block
					.path(height.toString())
					.queryParam("format", "json")
					.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
					.accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);

			Block b=null;
			try {
				JSONObject obj = new JSONObject(result);
				JSONArray arr  = obj.getJSONArray("blocks");
				JSONObject jsonBlock = (JSONObject) arr.get(0);
				Long time   = jsonBlock.getLong("time");
				String hash = jsonBlock.getString("hash");
				b=new Block();
				b.setTime(time);
				b.setHeight(height);
				b.setHash(hash);
			} catch (JSONException e) {
				error("getBlockException " +  e.getMessage() );
			}
			return b;
		}

		@Override
		public String getTransHex(String txhash) {

			String result=null;
			try {
				result = transaction
						.path(txhash)
						.queryParam("format", "hex")
						.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
						.accept(MediaType.TEXT_PLAIN)
						.header("User-Agent", StaticStrings.USER_AGENT)
						.get(String.class);
				return result;
			} catch(Exception e) {}


			return result;
		}
		@Override
		public String toString() {
			return getPrefix();
		}
	}
