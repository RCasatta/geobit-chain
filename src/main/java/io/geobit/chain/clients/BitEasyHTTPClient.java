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

import io.geobit.chain.entity.biteasy.BitEasyAddressData;
import io.geobit.chain.entity.biteasy.BitEasyResponse;
import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.chain.providers.received.ReceivedProvider;
import io.geobit.common.statics.StaticNumbers;
import io.geobit.common.statics.StaticStrings;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import static io.geobit.common.statics.Log.*;


public class BitEasyHTTPClient implements BalanceProvider, ReceivedProvider {
	private static final String prefix = "https://api.biteasy.com/blockchain/v1";
	private static final String key    = "IMCm6utjPw76RyJdsLror79a20t7EBEHB9L9eU36";

	private WebResource balance;

	public BitEasyHTTPClient() {
		super();
		Client client;
		client= Client.create();
		
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(StaticNumbers.READ_TIMEOUT);
		balance     = client.resource(prefix + "/addresses/" );

		//transaction.path(path);
	}


	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public Long getBalance(String address) {
		try {
			BitEasyResponse result = balance
					.path(address)
					.queryParam("api_key", key)
					.accept( MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(BitEasyResponse.class);

			if(result!=null) {
				if(result.getStatus()==200) {
					BitEasyAddressData balData= result.getData();
					if(balData!=null) {
						Long balance2 = balData.getBalance();
						return balance2;
					}

				} else if (result.getStatus()==404) {
					return 0L;
				}
			}
		} catch(Exception e) {
			String message = e.getMessage();
			if(message.contains("404 Not Found")) {
				return 0L;
			} else if(message.contains("429"))  {
				log("biteasy getBalance reach threshold" );
			} else {
				String mes = "exception on biteasy getBalance " + message;
				error(mes);
			}
		}
		return null;
	}


	@Override
	public Long getReceived(String address) {
		try {
			BitEasyResponse result = balance
					.path(address)
					.queryParam("api_key", key)
					.accept( MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(BitEasyResponse.class);

			if(result!=null && result.getStatus()==200) {
				BitEasyAddressData balData= result.getData();
				if(balData!=null)
					return balData.getTotalReceived();
			}
			if(result.getStatus()==404) {
				BitEasyAddressData balData= result.getData();
				if(balData!=null)
					if("404".equals(balData.getStatus()))
						return 0L;
			}
		} catch(Exception e) {
			String message = e.getMessage();
			if(message.contains("404 Not Found")) {
				return 0L;
			} else if(message.contains("429"))  {
				log("biteasy getReceived reach threshold" );
			} else {
				String mes = "exception on biteasy getReceived " + message;
				error(mes);
			}
		}
		return null;
	}



	
	@Override
	public String toString() {
		return getPrefix();
	}
}
