/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 geobit.io 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.geobit.chain.providers.block;

import static io.geobit.common.statics.Log.log;
import io.geobit.chain.providers.GetPut;
import io.geobit.common.entity.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class BlocksCache implements GetPut<Integer, Block> {
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
		return "BlocksCache [higherSeen=" + higherSeen + ", confirmedBlocks.size()="
				+ confirmedBlocks.size() + ", recentBlocks.size()=" + recentBlocks.size() + "]";
	}
	
}
