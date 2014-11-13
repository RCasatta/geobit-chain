package io.geobit.chain.providers.addresstransactions;

import io.geobit.chain.providers.Prefix;
import io.geobit.common.entity.AddressTransactions;


public interface AddressTransactionsProvider extends Prefix  {
	public AddressTransactions getAddressTransactions(String address);

}
