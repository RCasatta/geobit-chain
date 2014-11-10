package io.geobit.chain.providers;

import io.geobit.common.entity.Block;


public interface BlockProvider extends Prefix  {
	public Block getBlock(Integer height);
}
