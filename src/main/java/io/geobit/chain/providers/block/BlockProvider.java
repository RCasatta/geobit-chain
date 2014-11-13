package io.geobit.chain.providers.block;

import io.geobit.chain.providers.Prefix;
import io.geobit.common.entity.Block;


public interface BlockProvider extends Prefix  {
	public Block getBlock(Integer height);
}
