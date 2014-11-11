package io.geobit.chain.providers.balance;

import java.util.concurrent.Callable;

public class BalanceRunnable implements Callable<Long> {
	private BalanceProvider provider;
	private String address;
	
	

	public BalanceRunnable(BalanceProvider provider, String address) {
		super();
		this.provider = provider;
		this.address  = address;
	}


	@Override
	public Long call() throws Exception {
		Long l=provider.getBalance(address);
		return l;
	}

}
