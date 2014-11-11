package io.geobit.chain.providers.received;

import io.geobit.chain.clients.BitEasyHTTPClient;
import io.geobit.chain.clients.BlockChainHTTPClient;
import io.geobit.chain.clients.BlockrHTTPClient;
import io.geobit.chain.clients.HelloBlockHTTPClient;
import io.geobit.chain.providers.Provider;

public class ReceivedProviders extends Provider<ReceivedProvider> {

	public ReceivedProviders() {
		super();

		ReceivedProvider a=new BitEasyHTTPClient();
		add(a);
		ReceivedProvider b=new BlockChainHTTPClient();  
		add(b);
		ReceivedProvider e=new HelloBlockHTTPClient();
		add(e);
		ReceivedProvider f=new BlockrHTTPClient();
		add(f);
	}




}
