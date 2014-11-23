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
import io.geobit.chain.entity.sochain.SoChainData;
import io.geobit.chain.entity.sochain.SoChainResponse;
import io.geobit.chain.entity.sochain.SoChainTransaction;
import io.geobit.chain.entity.sochain.SoChainTxResponse;
import io.geobit.common.entity.AddressTransactions;
import io.geobit.common.entity.Transaction;
import io.geobit.common.providers.AddressTransactionsProvider;
import io.geobit.common.providers.BalanceProvider;
import io.geobit.common.providers.TransHexProvider;
import io.geobit.common.providers.TransactionProvider;
import io.geobit.common.statics.StaticNumbers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class SoChainHTTPClient implements BalanceProvider, AddressTransactionsProvider, TransHexProvider, TransactionProvider {
	private static final String prefix="https://chain.so/api/v2";
	
	private WebResource balance;
	private WebResource geobit;
	private WebResource tx;
	
	public SoChainHTTPClient() {
		super();
		
		Client client;
		Client clientNoTimeout;
		
		client= Client.create();
		clientNoTimeout= Client.create();
		
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(StaticNumbers.READ_TIMEOUT);

		balance     = client.resource(prefix + "/get_address_balance/BTC/" );
		geobit      = client.resource(prefix +  "/address/BTC/" );
		tx          = client.resource(prefix + "/tx/BTC/");
		}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public AddressTransactions getAddressTransactions(String address) {
	
		log("SoChainResponse AddressTransactions(" + address +")");
		try {
			SoChainResponse result = geobit
					.path(address)
					.get(SoChainResponse.class);
			log("SoChainResponse=" + result);
			if(result!=null && result.getData()!=null && "success".equals(result.getStatus())) {
				SoChainData data=result.getData();
				AddressTransactions addTxs=new AddressTransactions();
				addTxs.setAddress(data.getAddress());
				addTxs.setBalance(data.getBalanceLong());
				
				List<Transaction> lista=new LinkedList<Transaction>();
				BlockAndTransactionDispatcher disp=BlockAndTransactionDispatcher.getInstance();
				for(SoChainTransaction cur : data.getTxs() ){
					String hash=cur.getTxid();
					Transaction t=disp.getTransaction(hash);
					lista.add(t);
				}
				Collections.sort(lista);
				addTxs.setTransactions(lista);
				return addTxs;	
			}
			
		} catch (Exception e) {
			error("SoChainHTTPClient getAddressTransactions " + e.getMessage() );
		}
		return null;
	}

	@Override
	public Long getBalance(String address) {
		try {
			String bilancio=balance
					.path(address)
					.get(String.class);

			JSONObject obj = new JSONObject(bilancio);
			String val=obj.getJSONObject("data").getString("confirmed_balance");
			return longBalance(val);
		}
		catch (Exception e) {
			String mes = "exception on sochain getBalance " + e.getMessage();
			error(mes);
		}
		return null;
	}

	private long longBalance(String val) {
		return Long.parseLong( val.replace(".", "") );
	}

	@Override
	public String toString() {
		return getPrefix();
	}

	@Override
	public String getTransHex(String txHash) {
		String result=tx
				.path(txHash)
				.get(String.class);

		JSONObject obj;
		try {
			obj = new JSONObject(result);
			String val=obj.getJSONObject("data").getString("tx_hex");
			return val;
		} catch (JSONException e) {
			error("SoChainHTTPCLient getTransHex " + e.getMessage() );
		}
		
		
		return null;
	}

	@Override
	public Transaction getTransaction(String txHash) {
		log("asking " + txHash);
		
		SoChainTxResponse result2=tx
				.path(txHash)
				.get(SoChainTxResponse.class);
		
		SoChainTransaction soTx=result2.getData();
		return soTx.getTransaction();
	}
	
	

}
