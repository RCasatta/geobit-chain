package io.geobit.chain.providers;

import io.geobit.chain.clients.BlockChainHTTPClient;
import io.geobit.chain.clients.BlockCypherHTTPClient;
import io.geobit.chain.clients.BlockrHTTPClient;

public class PushTxProviders extends Provider<PushTxProvider> {

	public PushTxProviders() {
		super();
		
		PushTxProvider b=new BlockChainHTTPClient(); 
		add(b);
		PushTxProvider d=new BlockrHTTPClient();
		add(d);
		
		PushTxProvider e=new BlockCypherHTTPClient();
		add(e);
	
	}




}
