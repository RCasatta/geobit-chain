package io.geobit.chain.providers.block;

import io.geobit.common.entity.Block;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.SettableFuture;

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
		System.out.println("success from " + provider.getPrefix() + " val=" + contents + " in=" + elabsed);

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
		System.out.println("failure from " + provider.getPrefix());
		blockProviders.record(provider , null );
	}

}
