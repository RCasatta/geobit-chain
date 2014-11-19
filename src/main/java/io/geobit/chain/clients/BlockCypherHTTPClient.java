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
import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.chain.providers.pushtx.PushTxProvider;
import io.geobit.common.statics.ApiKeys;
import io.geobit.common.statics.StaticNumbers;
import io.geobit.common.statics.StaticStrings;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;

public class BlockCypherHTTPClient implements BalanceProvider,  PushTxProvider {
	private static final String prefix=  "http://api.blockcypher.com/v1/btc/main";

	private WebResource balance;
	private WebResource pushTx;
	
	public BlockCypherHTTPClient() {
		super();
		Client client;
		client= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(StaticNumbers.READ_TIMEOUT);
		client.addFilter(new GZIPContentEncodingFilter(false));

		balance     = client.resource(prefix + "/addrs/" );
		pushTx      = client.resource(prefix + "/txs/push");

		//transaction.path(path);
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public Long getBalance(String address) {
		try {
			String bilancio=balance
					.queryParam("token", ApiKeys.BLOCKCYPHER)
					.path(address + "/balance")
					.header("Accept-Encoding", "gzip")
					
					.get(String.class);

			JSONObject obj = new JSONObject(bilancio);
			Long val=obj.getLong("final_balance");
			return val;
		} catch (Exception e) { 
			String message = e.getMessage();
			if(message.contains("404 Not Found"))
				return 0L;
			else {
				String mes = "BlockCypherHTTPClient getBalance excpetion " + e.getMessage();
				error(mes);
			}
		}
		return null;
	}


	@Override
	public String toString() {
		return getPrefix();
	}



	@Override
	public Boolean pushTx(String hex) {
		try {
			log("BlockCypherHTTPClient pushTx " + hex);
			String toPost=String.format("{\"tx\": \"%s\"}", hex);
			ClientResponse response = pushTx
					.header("User-Agent", StaticStrings.USER_AGENT)
					.type(MediaType.TEXT_PLAIN)
					.post(ClientResponse.class, toPost );

			String ret=response.getEntity(String.class);
			log("pushing transaction returned " + ret);
			return response.getStatus()==200;
		} catch (Exception e) {
			error("BlockCypherHTTPClient PushTx error " + e.getMessage());
		}
		return null;
	}



}
