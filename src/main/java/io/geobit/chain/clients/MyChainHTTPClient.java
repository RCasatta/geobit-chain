package io.geobit.chain.clients;

import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.chain.providers.transaction.TransactionProvider;
import io.geobit.common.entity.Transaction;

public class MyChainHTTPClient implements BalanceProvider, TransactionProvider {

	@Override
	public String getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * http://mychain.io/api/tx?txid=d6d3ba8e88d7abc4aa61f313fc403786a0d53c27dc8dcb9d5cf3304fb627ae70
	 * 
	 */
	@Override
	public Transaction getTransaction(String txHash) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getBalance(String address) {
		// TODO Auto-generated method stub
		return null;
	}


}
