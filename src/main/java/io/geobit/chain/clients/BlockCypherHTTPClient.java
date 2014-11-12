package io.geobit.chain.clients;

import static io.geobit.common.statics.Log.log;
import io.geobit.chain.providers.PushTxProvider;
import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.common.statics.StaticNumbers;
import io.geobit.common.statics.StaticStrings;

import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.google.common.primitives.Booleans;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
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
					.queryParam("token", "4314170c99337e9984152a4af0ba77c0")
					.path(address + "/balance")
					.header("Accept-Encoding", "gzip")
					
					.get(String.class);
//			System.out.println(bilancio);

			JSONObject obj = new JSONObject(bilancio);
			Long val=obj.getLong("final_balance");
//			Long val=Long.parseLong(bilancio);
			return val;
		} catch (Exception e) { 
			String message = e.getMessage();
			if(message.contains("404 Not Found"))
				return 0L;
			else {
				String mes = "BlockCypherHTTPClient getBalance excpetion " + e.getMessage();
				System.out.println(mes);
				log(mes );
				
			}
		}
		//		Double val=bilancio.getFinalBalance().get("BTC");
		//		return Math.round(val*Numbers.SATOSHIS);
		return null;
	}


	@Override
	public String toString() {
		return getPrefix();
	}



	@Override
	public Boolean pushTx(String hex) {
		try {
			System.out.println("BlockCypherHTTPClient pushTx " + hex);
			String toPost=String.format("{\"tx\": \"%s\"}", hex);
			ClientResponse response = pushTx
					.header("User-Agent", StaticStrings.USER_AGENT)
					.type(MediaType.TEXT_PLAIN)
					.post(ClientResponse.class, toPost );

			String ret=response.getEntity(String.class);
			System.out.println("pushing transaction returned " + ret);
			return response.getStatus()==200;
		} catch (Exception e) {
			log("BlockCypherHTTPClient PushTx error " + e.getMessage());
		}
		return null;
	}



}
