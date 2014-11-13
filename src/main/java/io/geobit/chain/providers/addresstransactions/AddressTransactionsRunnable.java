package io.geobit.chain.providers.addresstransactions;

import io.geobit.common.entity.AddressTransactions;

import java.util.concurrent.Callable;

public class AddressTransactionsRunnable implements Callable<AddressTransactions> {
	private AddressTransactionsProvider provider;
	private String address;
	
	

	public AddressTransactionsRunnable(AddressTransactionsProvider provider, String address) {
		super();
		this.provider = provider;
		this.address  = address;
	}


	@Override
	public AddressTransactions call() throws Exception {
		AddressTransactions l = provider.getAddressTransactions(address);
		return l;
	}

}
