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

import io.geobit.common.entity.Block;
import io.geobit.common.providers.BlockProvider;

import java.util.Map;
import java.util.concurrent.Future;

import com.google.common.base.Objects;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;

import static io.geobit.common.statics.Log.*;
public class BlockCheckRunnable implements Runnable {
	private Integer height;
	private Future<Block>  firstFuture;
	private BlockProvider  firstProvider;
	private Future<Block>  secondFuture;
	private BlockProvider  secondProvider;
	private Future<Block>  thirdFuture;
	private BlockProvider  thirdProvider;
	private BlockProviders providers;
	private BlocksCache cache;

	public BlockCheckRunnable(Integer height, Future<Block> firstFuture,
			BlockProvider firstProvider, Future<Block> secondFuture,
			BlockProvider secondProvider, 
			ListenableFuture<Block> thirdFuture, BlockProvider thirdProvider, 
			BlockProviders providers, BlocksCache cache) {
		super();
		this.height         = height;
		this.firstFuture    = firstFuture;
		this.firstProvider  = firstProvider;
		this.secondFuture   = secondFuture;
		this.secondProvider = secondProvider;
		this.thirdFuture    = thirdFuture;
		this.thirdProvider  = thirdProvider;
		this.providers      = providers;
		this.cache          = cache;
	}

	@Override
	public void run() {
		try {
			log("started check block");
			Block firstResult  = firstFuture.get();
			Block secondResult = secondFuture.get();
			Block thirdResult  = thirdFuture.get();
			String format = String.format("block(%d)=%s from %s "
					+ "secondResult=%s from %s " 
					+ "thirdResult=%s from %s ",
					height, 
					firstResult, firstProvider,
					secondResult, secondProvider,
					thirdResult, thirdProvider );
			log(format);
			if(firstResult!=null && Objects.equal( firstResult , secondResult) && !Objects.equal( firstResult , thirdResult))  {
				error(thirdProvider + " gave bad result " + "\n" + thirdResult +". correct block(" + height + ") is " 
						+ "\n" + firstResult  + " from " + firstProvider + " and " + secondProvider  );
				cache.put(height, firstResult);
				providers.record(thirdProvider, null);  // give penalty to provider giving bad result
			} else if(firstResult!=null && Objects.equal( firstResult , thirdResult) && !Objects.equal( firstResult , secondResult))  {
				error(secondProvider + " gave bad result " + "\n" + secondResult +". correct block(" + height + ") is " 
						+ "\n" + firstResult  + " from " + firstProvider + " and " + thirdProvider  );
				cache.put(height, firstResult);
				providers.record(secondProvider, null);  // give penalty to provider giving bad result
			} else if(secondResult!=null && Objects.equal( secondResult , thirdResult) && !Objects.equal( firstResult , thirdResult))  {
				error(firstProvider + " gave bad result " +"\n" +  firstResult +". correct block(" + height + ") is " 
						+ "\n" + secondResult  + " from " + secondProvider + " and " + thirdProvider  );
				cache.put(height, firstResult);
				providers.record(firstProvider, null);  // give penalty to provider giving bad result
			}

		} catch (Exception e) {
			error("CheckBlockRunnable exception " + e.getMessage() );
		}
	}

}
