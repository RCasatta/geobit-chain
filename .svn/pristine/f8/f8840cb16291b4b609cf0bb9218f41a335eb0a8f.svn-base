package io.geobit.chain.providers;

import io.geobit.chain.clients.BitEasyHTTPClient;
import io.geobit.chain.clients.BlockChainHTTPClient;
import io.geobit.chain.clients.BlockCypherHTTPClient;
import io.geobit.chain.clients.BlockExplorerHTTPClient;
import io.geobit.chain.clients.BlockrHTTPClient;
import io.geobit.chain.clients.ChainHTTPClient;
import io.geobit.chain.clients.HelloBlockHTTPClient;
import io.geobit.chain.clients.SoChainHTTPClient;

public class BalanceProviders extends Provider<BalanceProvider> {


	public BalanceProviders() {
		super();

		BalanceProvider a=new BitEasyHTTPClient();
		add(a);
		BalanceProvider b=new BlockChainHTTPClient();  
		add(b);
		BalanceProvider c=new BlockExplorerHTTPClient();
		add(c);
		BalanceProvider d=new BlockrHTTPClient();
		add(d);
		BalanceProvider e=new HelloBlockHTTPClient();
		add(e);
		BalanceProvider f=new ChainHTTPClient();
		add(f);
		BalanceProvider g=new BlockCypherHTTPClient();
		add(g);
		BalanceProvider h=new SoChainHTTPClient();
		add(h);


	}





}
