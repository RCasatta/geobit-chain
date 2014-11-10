package io.geobit.chain.clients;

import static io.geobit.statics.Log.log;
import io.geobit.chain.entity.biteasy.BitEasyAddressData;
import io.geobit.chain.entity.biteasy.BitEasyResponse;
import io.geobit.chain.providers.BalanceProvider;
import io.geobit.chain.providers.ReceivedProvider;
import io.geobit.statics.StaticNumbers;
import io.geobit.statics.StaticStrings;


import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;


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
			//		System.out.println("result=" + result);
			if(result!=null) {
				if(result.getStatus()==200) {
					BitEasyAddressData balData= result.getData();
					if(balData!=null) {
						Long balance2 = balData.getBalance();
						//				System.out.println( balance.getURI() + " returns " + balance2);
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
				System.out.println(mes);
				log(mes );
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
			//		System.out.println("result=" + result);
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
				System.out.println(mes);
				log(mes );
			}
		}
		return null;
	}



	
	@Override
	public String toString() {
		return getPrefix();
	}
}
