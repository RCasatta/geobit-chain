package io.geobit.chain.clients;

import static io.geobit.statics.Log.log;
import io.geobit.chain.providers.BalanceProvider;
import io.geobit.statics.StaticNumbers;
import io.geobit.statics.StaticStrings;

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
			System.out.println(mes);
			log(mes );
			
		}
		return resultLong;

	}

}
