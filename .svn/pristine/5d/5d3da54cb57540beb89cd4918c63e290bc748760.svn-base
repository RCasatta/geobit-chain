package io.geobit.chain.providers;

import io.geobit.chain.clients.BlockChainHTTPClient;
import io.geobit.chain.clients.HelloBlockHTTPClient;
import io.geobit.chain.clients.SoChainHTTPClient;

public class AddressTransactionsProviders extends Provider<AddressTransactionsProvider> {

	public AddressTransactionsProviders() {
		super();

		AddressTransactionsProvider b=new BlockChainHTTPClient();
		add(b);
		AddressTransactionsProvider e=new HelloBlockHTTPClient();
		add(e);
		AddressTransactionsProvider f=new SoChainHTTPClient();
		add(f);
	}




}
