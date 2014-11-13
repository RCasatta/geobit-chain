package io.geobit.chain.dispatchers;

import static io.geobit.common.statics.Log.log;
import io.geobit.chain.providers.TransHexProvider;
import io.geobit.chain.providers.TransactionProvider;
import io.geobit.chain.providers.TransactionProviders;
import io.geobit.chain.providers.balance.BalanceCheckRunnable;
import io.geobit.chain.providers.balance.BalanceFutureCallback;
import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.chain.providers.balance.BalanceRunnable;
import io.geobit.chain.providers.block.BlockCheckRunnable;
import io.geobit.chain.providers.block.BlockFutureCallback;
import io.geobit.chain.providers.block.BlockProvider;
import io.geobit.chain.providers.block.BlockProviders;
import io.geobit.chain.providers.block.BlockRunnable;
import io.geobit.chain.providers.block.BlocksCache;
import io.geobit.common.entity.Block;
import io.geobit.common.entity.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Objects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

public class BlockAndTransactionDispatcher implements BlockProvider, TransactionProvider, TransHexProvider {
	private ExecutorService              executor     = Executors.newFixedThreadPool(10);
	private ListeningExecutorService moreExecutor     = MoreExecutors.listeningDecorator(executor);
	
	private static BlockAndTransactionDispatcher me;

	BlocksCache blocksCache = new BlocksCache();
	
	private LoadingCache<String,Transaction> transactions;

	private BlockProviders             blockProviders = new BlockProviders();
	private TransactionProviders transactionProviders = new TransactionProviders();

	public static BlockAndTransactionDispatcher getInstance() {
		if(me==null) {
			me=new BlockAndTransactionDispatcher();
		}	
		return me;
	}

	public BlockAndTransactionDispatcher() {
		super();
		initialize();
	}

	private void initialize() {

		
		transactions = CacheBuilder.newBuilder()
				.maximumSize(100000) /* keep in memory 100k transactions */
				.build(
						new CacheLoader<String, Transaction>() {
							public Transaction load(String txHash) {  /* never been called */
								log("BlockAndTransactionDispatcher transactions cache. key " + txHash);
								throw new RuntimeException("BlockAndTransactionDispatcher recent blocks cache address invalid");			
							}
						}
						);
		
	}

	@Override
	public String getPrefix() {
		return "mixed";
	}

	@Override
	public Block getBlock(Integer height) {
		return getBlock(height,0);
	}

	private Block getBlock(Integer height, int cont) {
		if(cont>5)
			return null;
		Block valCache = blocksCache.get(height);
		if(valCache!=null)
			return valCache;  /* the block in confirmed cache is returned */
		
		BlockProvider blo1 = blockProviders.take();
		BlockProvider blo2 = blockProviders.takeDifferent(blo1);
		BlockProvider blo3 = blockProviders.takeDifferent(blo1,blo2);
		
		System.out.println("blo1=" + blo1 + " blo2=" + blo2 + " blo3=" + blo3 );
		Callable<Block> runner1 = new BlockRunnable(blo1, height);
		Callable<Block> runner2 = new BlockRunnable(blo2, height);
		Callable<Block> runner3 = new BlockRunnable(blo3, height);
		final Long start=System.currentTimeMillis();
		ListenableFuture<Block> listenableFuture1 = moreExecutor.submit(runner1);
		ListenableFuture<Block> listenableFuture2 = moreExecutor.submit(runner2);
		ListenableFuture<Block> listenableFuture3 = moreExecutor.submit(runner3);
		SettableFuture<Block> first  = SettableFuture.create();
		SettableFuture<Block> second = SettableFuture.create();
		 
		Futures.addCallback(listenableFuture1,new BlockFutureCallback(start,  blo1, first, second, blockProviders ));
		Futures.addCallback(listenableFuture2,new BlockFutureCallback(start,  blo2, first, second, blockProviders ));
		Futures.addCallback(listenableFuture3,new BlockFutureCallback(start,  blo3, first, second, blockProviders ));
		Runnable checker = new BlockCheckRunnable(height,listenableFuture1, blo1, 
				listenableFuture2, blo2, 
				listenableFuture3, blo3, blockProviders, blocksCache); 
		moreExecutor.execute(checker);
		try {
			
			Block firstBlock   = first.get();
			Block secondBlock  = second.get();
			if( firstBlock!=null && firstBlock.equals(secondBlock) ) {
				blocksCache.put(height, firstBlock);
				System.out.println(blocksCache);
				return firstBlock;
			}
		} catch (Exception e) {		
		}
		return getBlock(height,cont+1);
	}

	
	
	@Override
	public String getTransHex(String txhash) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction getTransaction(String txHash) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
}
