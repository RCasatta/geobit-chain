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
import io.geobit.common.providers.ReceivedProvider;
import io.geobit.common.statics.StaticNumbers;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
public class MyChainHTTPClient implements ReceivedProvider {

	private static final String prefix= "http://mychain.io/api";

	private WebResource block;
	private WebResource tx;
	private WebResource address;

	public MyChainHTTPClient() {
		super();

		Client client;

		client= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(StaticNumbers.READ_TIMEOUT);

		block   = client.resource(prefix + "/blockhash/" );
		tx      = client.resource(prefix + "/tx/" );
		address = client.resource(prefix + "/address/" );
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public Long getReceived(String address) {
		Long resultLong=null;
		try {
			String json = this.address
					.queryParam("address", address)
					.accept(MediaType.TEXT_PLAIN)
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			JSONObject obj = new JSONObject(json);
			obj = obj.getJSONObject("data");
			double received = obj.getDouble("confirmedReceived");
			received += obj.getDouble("confirmedPossiblyReceived");
			received += obj.getDouble("unconfirmedReceived");
			received += obj.getDouble("unconfirmedPossiblyReceived");
			resultLong = (long) (received * 100000000);
		} catch(Exception e) {
			String mes = "exception on blockchain getBalance " + e.getMessage();
			error(mes);
		}
		return resultLong;
	}
		
}
