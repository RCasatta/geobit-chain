package io.geobit.common.providers;

import io.geobit.common.entity.AddressTransactions;

public interface AddressUnspentsProvider extends Provider {
	public AddressTransactions getAddressUnspents(String address);

}
