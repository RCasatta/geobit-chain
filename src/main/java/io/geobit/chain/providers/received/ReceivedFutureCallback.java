package io.geobit.chain.providers.received;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.SettableFuture;

public class ReceivedFutureCallback implements FutureCallback<Long> {
	private Long start;
	private SettableFuture<Long> returned;
	private ReceivedProvider  provider;
	private ReceivedProviders receivedProviders;


	public ReceivedFutureCallback(Long start, ReceivedProvider provider
			, SettableFuture<Long> returned, ReceivedProviders receivedProviders) {
		super();
		this.start = start;
		this.returned = returned;
		this.provider = provider;
		this.receivedProviders = receivedProviders;
	}

	@Override
	public void onSuccess(Long contents) {
		System.out.println("success from " + provider.getPrefix() + " val=" + contents);
		Long elabsed= System.currentTimeMillis() - start;   /* influenced by thread time but anyway it's for every thread */
				
		if(contents!=null) 
			receivedProviders.record(provider , elabsed );
		else
			receivedProviders.record(provider , null );
		
		returned.set(contents);
	}

	@Override
	public void onFailure(Throwable throwable) {
		System.out.println("failure from " + provider.getPrefix());
		receivedProviders.record(provider , null );
		returned.set(null);
	}

}
