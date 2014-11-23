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

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.SettableFuture;

import static io.geobit.common.statics.Log.*;
public class BlockFutureCallback implements FutureCallback<Block> {
	private Long start;
	private SettableFuture<Block> first;
	private SettableFuture<Block> second;
	private BlockProvider  provider;
	private BlockProviders blockProviders;

	public BlockFutureCallback(Long start, BlockProvider provider
			, SettableFuture<Block> first,SettableFuture<Block> second, 
			BlockProviders blockProviders) {
		super();
		this.start    = start;
		this.first    = first;
		this.second   = second;
		this.provider = provider;
		this.blockProviders = blockProviders;
	}

	@Override
	public void onSuccess(Block contents) {
		Long elabsed= System.currentTimeMillis() - start;   /* influenced by thread time but anyway it's for every thread */
		log("success from " + provider.getPrefix() + " val=" + contents + " in=" + elabsed);

		if(contents!=null) 
			blockProviders.record(provider , elabsed );
		else
			blockProviders.record(provider , null );

		synchronized(this) {
			if(!first.isDone()) {
				first.set(contents);
			} else {
				second.set(contents);
			}
		}

	}

	@Override
	public void onFailure(Throwable throwable) {
		error("failure from " + provider.getPrefix());
		blockProviders.record(provider , null );
	}

}
