package io.geobit.chain.providers.addresstransactions;

import io.geobit.common.entity.AddressTransactions;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.SettableFuture;

public class AddressTransactionsFutureCallback implements FutureCallback<AddressTransactions> {
	private Long start;
	private SettableFuture<AddressTransactions> returned;
	private AddressTransactionsProvider  provider;
	private AddressTransactionsProviders receivedProviders;


	public AddressTransactionsFutureCallback(Long start, AddressTransactionsProvider provider
			, SettableFuture<AddressTransactions> returned, AddressTransactionsProviders receivedProviders) {
		super();
		this.start = start;
		this.returned = returned;
		this.provider = provider;
		this.receivedProviders = receivedProviders;
	}

	@Override
	public void onSuccess(AddressTransactions contents) {
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
