package io.geobit.chain.providers.balance;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.SettableFuture;

public class BalanceFutureCallback implements FutureCallback<Long> {
	private Long start;
	private SettableFuture<Long> returned;
	private BalanceProvider  provider;
	private BalanceProviders balanceProviders;


	public BalanceFutureCallback(Long start, BalanceProvider provider
			, SettableFuture<Long> returned, BalanceProviders balanceProviders) {
		super();
		this.start = start;
		this.returned = returned;
		this.provider = provider;
		this.balanceProviders = balanceProviders;
	}

	@Override
	public void onSuccess(Long contents) {
		System.out.println("success from " + provider.getPrefix() + " val=" + contents);
		Long elabsed= System.currentTimeMillis() - start;   /* influenced by thread time but anyway it's for every thread */
				
		if(contents!=null) 
			balanceProviders.record(provider , elabsed );
		else
			balanceProviders.record(provider , null );
		
		returned.set(contents);
	}

	@Override
	public void onFailure(Throwable throwable) {
		System.out.println("failure from " + provider.getPrefix());
		balanceProviders.record(provider , null );
		returned.set(null);
	}

}
