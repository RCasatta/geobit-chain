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
import io.geobit.common.statics.StaticNumbers;
import io.geobit.common.statics.StaticStrings;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class BlockHTTPClient implements BalanceProvider {
	private static final String prefix=  "http://block.io/api/v1";

	private WebResource balance;
	
	public BlockHTTPClient() {
		super();
		
		Client client;
		client= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(   StaticNumbers.READ_TIMEOUT);
		
		balance     = client.resource(prefix + "/get_address_balance/" );
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public Long getBalance(String address) {
		Long resultLong=null;
		try {
			String result = balance
					.path(address)
					.queryParam("api_key", "94e7-3862-0147-0f17")
					.queryParam("address", address)
					.accept(MediaType.TEXT_PLAIN)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);

			resultLong = Long.parseLong(result);
		} catch(Exception e) {
			String mes = "exception on BlockHTTPClient getBalance " + e.getMessage();
			error(mes);

		}
		return resultLong;

	}

}
