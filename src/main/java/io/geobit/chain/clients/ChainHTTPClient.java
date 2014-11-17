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
import io.geobit.chain.providers.received.ReceivedProvider;
import io.geobit.common.statics.StaticNumbers;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class ChainHTTPClient implements BalanceProvider, ReceivedProvider {
	private static final String prefix= "https://api.chain.com/v1/bitcoin";
	private static final String apiKey="2dc5fc7097de3560189a5e2f46cf4d15";
	private WebResource balance;

	public ChainHTTPClient() {
		super();
		Client client= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT*2);
		client.setReadTimeout(StaticNumbers.READ_TIMEOUT*2);

		balance     = client.resource(prefix + "/addresses/" );

		//transaction.path(path);
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	/*
	 * curl https://api.chain.com/v1/bitcoin/addresses/17x23dNjXJLzGMev6R63uyRhMWP1VHawKc?key=DEMO-4a5e1e4
	 * 
	 */

	@Override
	public Long getBalance(String address) {
		try {

			String bilancio=balance
					.path(address)
					.queryParam("key", apiKey)
					.get(String.class);


			JSONObject obj = new JSONObject(bilancio);
			Long val=obj.getLong("balance");
			return val;
		} catch (Exception e) {
			String mes = "ChainHTTPClient getBalance error " + e.getMessage();
			error(mes );

		}

		return null;
	}

	@Override
	public Long getReceived(String address) {
		try {

			String bilancio=balance
					.path(address)
					.queryParam("key", apiKey)
					.get(String.class);
	

			JSONObject obj = new JSONObject(bilancio);
			Long val=obj.getLong("received");
			return val;
		} catch (Exception e) {
			error("chain Http Client getReceived " +  e.getMessage() );
		}

		return null;

	}

	@Override
	public String toString() {
		return getPrefix();
	}

}
