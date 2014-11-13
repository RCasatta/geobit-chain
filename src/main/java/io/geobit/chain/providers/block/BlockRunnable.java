package io.geobit.chain.providers.block;

import io.geobit.common.entity.Block;

import java.util.concurrent.Callable;

public class BlockRunnable implements Callable<Block> {
	private BlockProvider provider;
	private Integer height;
	
	

	public BlockRunnable(BlockProvider provider, Integer height) {
		super();
		this.provider = provider;
		this.height   = height;
	}


	@Override
	public Block call() throws Exception {
		Block l=provider.getBlock(height);
		return l;
	}

}
