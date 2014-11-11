package io.geobit.chain.providers.balance;

import io.geobit.chain.providers.Prefix;

public interface BalanceProvider extends Prefix {
	public Long getBalance(String address);
}
