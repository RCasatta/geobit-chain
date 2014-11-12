package io.geobit.chain.clients;

import static io.geobit.common.statics.Log.log;
import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.common.statics.StaticNumbers;
import io.geobit.common.statics.StaticStrings;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
public class BlockExplorerHTTPClient implements BalanceProvider {

	private static final String prefix= "http://blockexplorer.com";
	
	private WebResource balance;
//	private WebResource received;

	public BlockExplorerHTTPClient() {
		super();
		
		Client client;
		client= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(StaticNumbers.READ_TIMEOUT);
		
		balance     = client.resource(prefix + "/q/addressbalance/" );
//		received    = client.resource(prefix + "/q/getreceivedbyaddress/" );

		//transaction.path(path);
	}
	
	@Override
	public String getPrefix() {
		return prefix;
	}
	

	@Override
	public Long getBalance(String address) {
		String result = balance
				.path(address)
				.accept(MediaType.TEXT_PLAIN)
				.header("User-Agent", StaticStrings.USER_AGENT)
				.get(String.class);
		Long resultLong=null;
		try {
			resultLong =Math.round( Double.parseDouble(result) *100000000L);
		} catch(Exception e) {
			String mes = "exception on blockexplorer getBalance " + e.getMessage();
			System.out.println(mes);
			log(mes );
			
		}
		
//		System.out.println( balance.getURI() + " returns " + resultLong);
		return resultLong;
		
		
	}


	/* NOT WORKING*/
//	@Override
//	public Long getReceived(String address) {
//		String result = received
//				.path(address)
//				.accept(MediaType.TEXT_PLAIN)
//				.header("User-Agent", Strings.USER_AGENT)
//				.get(String.class);
//		Long resultLong=null;
//		try {
//			resultLong =Math.round( Double.parseDouble(result) *100000000L);
//		} catch(Exception e) {}
//		return resultLong;
//	}

	@Override
	public String toString() {
		return getPrefix();
	}
}
