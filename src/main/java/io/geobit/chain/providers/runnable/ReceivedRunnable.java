package io.geobit.chain.providers.runnable;

import io.geobit.chain.providers.ReceivedProvider;

import java.util.concurrent.Callable;

public class ReceivedRunnable implements Callable<Long> {
	private ReceivedProvider provider;
	private String address;
	
	

	public ReceivedRunnable(ReceivedProvider provider, String address) {
		super();
		this.provider = provider;
		this.address  = address;
	}


	@Override
	public Long call() throws Exception {
		Long l = provider.getReceived(address);
		return l;
	}

}
