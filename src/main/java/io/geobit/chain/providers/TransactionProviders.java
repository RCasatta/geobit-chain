package io.geobit.chain.providers;

import io.geobit.chain.clients.BlockChainHTTPClient;
import io.geobit.chain.clients.BlockrHTTPClient;
import io.geobit.chain.clients.HelloBlockHTTPClient;

public class TransactionProviders extends Provider<TransactionProvider> {

	public TransactionProviders() {
		super();

		TransactionProvider b=new BlockChainHTTPClient();  
		add(b);
		TransactionProvider d=new BlockrHTTPClient();
		add(d);
		TransactionProvider e=new HelloBlockHTTPClient();
		add(e);
	}




}
