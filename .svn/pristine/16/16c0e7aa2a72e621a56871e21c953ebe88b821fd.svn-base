package io.geobit.chain.clients;

import static io.geobit.statics.Log.log;
import io.geobit.chain.providers.BalanceProvider;
import io.geobit.chain.providers.ReceivedProvider;
import io.geobit.statics.StaticNumbers;

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
//			System.out.println(bilancio);

			JSONObject obj = new JSONObject(bilancio);
			Long val=obj.getLong("balance");
			return val;
		} catch (Exception e) {
			String mes = "ChainHTTPClient getBalance error " + e.getMessage();
			System.out.println(mes);
			log(mes );
			
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
			System.out.println(bilancio);

			JSONObject obj = new JSONObject(bilancio);
			Long val=obj.getLong("received");
			return val;
		} catch (Exception e) {}

		return null;
		
	}




	@Override
	public String toString() {
		return getPrefix();
	}

}
