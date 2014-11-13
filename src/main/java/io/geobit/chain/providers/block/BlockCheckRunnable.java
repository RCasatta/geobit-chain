package io.geobit.chain.providers.block;

import io.geobit.common.entity.Block;

import java.util.Map;
import java.util.concurrent.Future;

import com.google.common.base.Objects;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;

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
			System.out.println("started check block");
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
			System.out.println(format);
			if(firstResult!=null && Objects.equal( firstResult , secondResult) && !Objects.equal( firstResult , thirdResult))  {
				System.out.println(thirdProvider + " gave bad result " + thirdResult +". correct block(" + height + ") is " 
						+ firstResult  + " from " + firstProvider + " and " + secondProvider  );
				cache.put(height, firstResult);
				providers.record(thirdProvider, null);  // give penalty to provider giving bad result
			} else if(firstResult!=null && Objects.equal( firstResult , thirdResult) && !Objects.equal( firstResult , secondResult))  {
				System.out.println(secondProvider + " gave bad result " + secondResult +". correct block(" + height + ") is " 
						+ firstResult  + " from " + firstProvider + " and " + thirdProvider  );
				cache.put(height, firstResult);
				providers.record(secondProvider, null);  // give penalty to provider giving bad result
			} else if(secondResult!=null && Objects.equal( secondResult , thirdResult) && !Objects.equal( firstResult , thirdResult))  {
				System.out.println(firstProvider + " gave bad result " + firstResult +". correct block(" + height + ") is " 
						+ secondResult  + " from " + secondProvider + " and " + thirdProvider  );
				cache.put(height, firstResult);
				providers.record(firstProvider, null);  // give penalty to provider giving bad result
			}

		} catch (Exception e) {
			System.out.println("CheckBlockRunnable exception " + e.getMessage() );
		}
	}

}
