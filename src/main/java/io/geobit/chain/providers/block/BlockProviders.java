package io.geobit.chain.providers.block;

import io.geobit.chain.clients.BlockChainHTTPClient;
import io.geobit.chain.clients.BlockrHTTPClient;
import io.geobit.chain.clients.HelloBlockHTTPClient;
import io.geobit.chain.providers.Provider;

public class BlockProviders extends Provider<BlockProvider> {

	public BlockProviders() {
		super();
		
		BlockProvider a=new BlockChainHTTPClient();
		add(a);
		BlockProvider b=new HelloBlockHTTPClient();
		add(b);
		BlockProvider c=new BlockrHTTPClient();
		add(c);
	
	}


}
