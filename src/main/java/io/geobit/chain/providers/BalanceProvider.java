package io.geobit.chain.providers;

public interface BalanceProvider extends Prefix {
	public Long getBalance(String address);
	
}
