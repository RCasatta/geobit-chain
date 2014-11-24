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
import io.geobit.chain.dispatchers.BalanceAndReceivedDispatcher;
import io.geobit.chain.dispatchers.BlockAndTransactionDispatcher;
import io.geobit.chain.entity.helloblock.HelloBlockBlock;
import io.geobit.chain.entity.helloblock.HelloBlockData;
import io.geobit.chain.entity.helloblock.HelloBlockResponse;
import io.geobit.chain.entity.helloblock.HelloBlockTransaction;
import io.geobit.common.entity.AddressTransactions;
import io.geobit.common.entity.Block;
import io.geobit.common.entity.Transaction;
import io.geobit.common.providers.AddressTransactionsProvider;
import io.geobit.common.providers.AddressUnspentsProvider;
import io.geobit.common.providers.BalanceProvider;
import io.geobit.common.providers.BlockProvider;
import io.geobit.common.providers.ReceivedProvider;
import io.geobit.common.providers.TransactionProvider;
import io.geobit.common.statics.StaticNumbers;
import io.geobit.common.statics.StaticStrings;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class HelloBlockHTTPClient 
implements BalanceProvider , ReceivedProvider, 
TransactionProvider,  BlockProvider, 
AddressTransactionsProvider, AddressUnspentsProvider {

	private static final String prefix=  "http://mainnet.helloblock.io/v1";

	private WebResource unspents;
	private WebResource addresses;
	private WebResource transactions;
	private WebResource blocks;
	//	private WebResource latestBlocks;

	public HelloBlockHTTPClient() {
		super();

		Client client= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(   StaticNumbers.READ_TIMEOUT);

		addresses     = client.resource(prefix + "/addresses/" );
		unspents     = client.resource(prefix + "/addresses/" );
		transactions  = client.resource(prefix + "/transactions/" );
		blocks        = client.resource(prefix + "/blocks/" );
		//		latestBlocks  = client.resource(prefix + "/blocks/latest" );
		//transaction.path(path);
	}

	@Override
	public String getPrefix() {
		return prefix;
	}


	@Override
	public Long getBalance(String address) {
		try {
			HelloBlockResponse result = addresses
					.path(address)
					.accept( MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(HelloBlockResponse.class);

			if(result!=null && "success".equals(result.getStatus())) {
				HelloBlockData balData= result.getData();
				if(balData!=null && balData.getAddress()!=null) {
					Long balance = balData.getAddress().getBalance();
					return balance;
				}

			} 
		} catch(Exception e) {
			String mes = "HelloBlock getBalance error " + e.getMessage();
			error(mes );

		}
		return null;


	}



	@Override
	public Long getReceived(String address) {
		HelloBlockResponse result = addresses
				.path(address)
				.accept( MediaType.APPLICATION_JSON)
				.header("User-Agent", StaticStrings.USER_AGENT)
				.get(HelloBlockResponse.class);
		if(result!=null && "success".equals(result.getStatus())) {

			HelloBlockData balData= result.getData();
			if(balData!=null && balData.getAddress()!=null)
				return balData.getAddress().getTotalReceivedValue();

		}
		error("HelloBlock getReceived error");
		return null;
	}



	@Override
	public Transaction getTransaction( String txhash) {
		HelloBlockResponse result = transactions
				.path(txhash)
				.accept( MediaType.APPLICATION_JSON)
				.header("User-Agent", StaticStrings.USER_AGENT)
				.get(HelloBlockResponse.class);
		if(result!=null && "success".equals(result.getStatus())) {
			if(result.getData()!=null && result.getData().getTransaction()!=null) {

				HelloBlockTransaction trans=result.getData().getTransaction();
				Transaction t=trans.getTransaction();	
				return t;
			}
		}
		return null;
	}



	@Override
	public AddressTransactions getAddressTransactions(String address) {
		try {
			HelloBlockResponse result = addresses
					.path(address + "/transactions")
					.queryParam("limit", "50")
					.accept( MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(HelloBlockResponse.class);
			log("HelloBlockResponse=" + result);
			if(result!=null && result.getData()!=null && "success".equals(result.getStatus())) {
				AddressTransactions addTxs=new AddressTransactions();
				addTxs.setAddress(address);
				addTxs.setBalance(getBalance(address));
				List<Transaction> lista=new LinkedList<Transaction>();
				log(" result.getData().getTransactions().size()=" + result.getData().getTransactions().size());

				for( HelloBlockTransaction trans : result.getData().getTransactions() ) {
					Transaction t=trans.getTransaction();
					lista.add(t);
				}
				Collections.sort(lista);
				
				addTxs.setTransactions(lista);
				return addTxs;
			}
		} catch(Exception e) {
			error("HelloBlockHTTPClient  getAddressTransactions " + e.getMessage() );
		}

		return null;
	}

	@Override
	public Block getBlock(Integer height) {
		try {
			HelloBlockResponse resultBase = blocks
					.path(height.toString())

					.accept( MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(HelloBlockResponse.class);

			HelloBlockBlock block=resultBase.getData().getBlock();
			Block b=new Block();
			b.setHash(block.getBlockHash());
			b.setHeight(block.getBlockHeight());
			b.setTime(block.getBlockTime());


			return b;

		}  catch (Exception e) {
			error("HelloBlock getBlock error " + e.getMessage());
		}
		return null;
	}

	@Override
	public String toString() {
		return getPrefix();
	}

	/**
	 * https://mainnet.helloblock.io/v1/addresses/1G8sGKyw4wFGQXBZxk4df6uvCxGb1jR5sJ/unspents
	 */
	@Override
	public AddressTransactions getAddressUnspents(String address) {
		AddressTransactions result=null;
		try {
			result=new AddressTransactions();
			
			String json = unspents
					.path(address)
					.path("/unspents")
					.accept(MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);
			JSONObject obj = new JSONObject(json);
			JSONArray arr  = obj.getJSONObject("data").getJSONArray("unspents");
			Set<Transaction> insieme=new HashSet<Transaction>();
			for(int i=0;i< arr.length(); i++) {
				JSONObject obj2= (JSONObject) arr.get( i);
				String hash=obj2.getString("txHash");

				BlockAndTransactionDispatcher disp=BlockAndTransactionDispatcher.getInstance();
				Transaction t=disp.getTransaction(hash);
				insieme.add(t);
				
			}
			List<Transaction> lista=new LinkedList<Transaction>(insieme);
			BalanceAndReceivedDispatcher disp= BalanceAndReceivedDispatcher.getInstance();
			Long l=disp.getBalance(address);
			result.setBalance(l);
			result.setAddress(address);
			result.setOnlyUnspent(true);
			Collections.sort(lista);
			result.setTransactions(lista);
				
			return result;
			
		} catch(Exception e) {}
		return result;
	}

}
