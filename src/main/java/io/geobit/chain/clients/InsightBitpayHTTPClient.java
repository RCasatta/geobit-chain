package io.geobit.chain.clients;

import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.chain.providers.block.BlockProvider;
import io.geobit.common.entity.Block;

public class InsightBitpayHTTPClient implements BalanceProvider, BlockProvider {

	@Override
	public String getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * https://insight.bitpay.com/api/block/00000000000000000e3485183cfcc7b218f23f18d8df72dc1d4a11ac0a4c56a3
	 */
	@Override
	public Block getBlock(Integer height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getBalance(String address) {
		// TODO Auto-generated method stub
		return null;
	}

}
