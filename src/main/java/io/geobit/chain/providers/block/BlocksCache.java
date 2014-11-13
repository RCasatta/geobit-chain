package io.geobit.chain.providers.block;

import static io.geobit.common.statics.Log.log;
import io.geobit.common.entity.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class BlocksCache {
	private Integer                     higherSeen=0;
	private Map<Integer,Block>          confirmedBlocks;
	private LoadingCache<Integer,Block> recentBlocks;

	public BlocksCache() {
		super();
		confirmedBlocks=new HashMap<Integer,Block>();   /* all the confirmed blocks could fit in HashMap */

		recentBlocks = CacheBuilder.newBuilder()
				.maximumSize(100) 
				.expireAfterWrite(5, TimeUnit.MINUTES)   /* expiring after five minutes, if recent, could be orphaned block */
				.build(
						new CacheLoader<Integer, Block>() {
							public Block load(Integer height) {  /* never been called */
								log("BlockAndTransactionDispatcher recent blocks cache. key " + height);
								throw new RuntimeException("BlockAndTransactionDispatcher recent blocks cache address invalid");			
							}
						}
						);
	}


	public Block get(Integer height) {
		Block b=confirmedBlocks.get(height);
		if(b!=null)
			return b;
		b = recentBlocks.getIfPresent(height);
		if(b!=null)
			return b;
		return null;
	}

	public void put(Integer height, Block b) {
		if(b!=null) {
			int blockHeight=b.getHeight();
			if( blockHeight < (higherSeen-10) )
				confirmedBlocks.put(height, b); //block confirmed for sure;
			else
				recentBlocks.put(height, b);  //expiring cache for possible expiring blocks
			if(higherSeen<blockHeight)
				higherSeen=blockHeight;
		}

	}


	@Override
	public String toString() {
		return "BlocksCache [higherSeen=" + higherSeen + ", confirmedBlocks="
				+ confirmedBlocks + ", recentBlocks=" + recentBlocks + "]";
	}

	

	
	
}
