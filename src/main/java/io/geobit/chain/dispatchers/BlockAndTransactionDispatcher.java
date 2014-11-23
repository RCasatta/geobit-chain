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

package io.geobit.chain.dispatchers;

import static io.geobit.common.statics.Log.log;
import io.geobit.chain.providers.block.BlockCheckRunnable;
import io.geobit.chain.providers.block.BlockFutureCallback;
import io.geobit.chain.providers.block.BlockProviders;
import io.geobit.chain.providers.block.BlockRunnable;
import io.geobit.chain.providers.block.BlocksCache;
import io.geobit.chain.providers.transaction.TransactionCheckRunnable;
import io.geobit.chain.providers.transaction.TransactionFutureCallback;
import io.geobit.chain.providers.transaction.TransactionProviders;
import io.geobit.chain.providers.transaction.TransactionRunnable;
import io.geobit.chain.providers.transhex.TransHexCheckRunnable;
import io.geobit.chain.providers.transhex.TransHexFutureCallback;
import io.geobit.chain.providers.transhex.TransHexProviders;
import io.geobit.chain.providers.transhex.TransHexRunnable;
import io.geobit.common.entity.Block;
import io.geobit.common.entity.Transaction;
import io.geobit.common.providers.BlockProvider;
import io.geobit.common.providers.TransHexProvider;
import io.geobit.common.providers.TransactionProvider;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	private BlocksCache blocksCache = new BlocksCache();
	
	private LoadingCache<String,Transaction>  txCache;
	private LoadingCache<String,String> transHexCache;

	private BlockProviders             blockProviders = new BlockProviders();
	private TransactionProviders transactionProviders = new TransactionProviders();
	private TransHexProviders       transHexProviders = new TransHexProviders();
	
	public static BlockAndTransactionDispatcher getInstance() {
		if(me==null) {
			me=new BlockAndTransactionDispatcher();
		}	
		return me;
	}

	private BlockAndTransactionDispatcher() {
		super();

		txCache = CacheBuilder.newBuilder()
				.maximumSize(100000) 
				.build(
						new CacheLoader<String, Transaction>() {
							public Transaction load(String txHash) {  /* never been called */
								log("txCache  " + txHash);
								throw new RuntimeException("txCache");			
							}
						}
						);

		transHexCache = CacheBuilder.newBuilder()
				.maximumSize(100000) 
				.build(
						new CacheLoader<String, String>() {
							public String load(String txHash) {  /* never been called */
								log("transHexCache  " + txHash);
								throw new RuntimeException("transHexCache");			
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
		
		log("blo1=" + blo1 + " blo2=" + blo2 + " blo3=" + blo3 );
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
				log(blocksCache.toString());
				return firstBlock;
			}
		} catch (Exception e) {		
		}
		return getBlock(height,cont+1);
	}

	
	
	@Override
	public String getTransHex(String txHash) {
		return getTransHex(txHash,0);
	}
	
	public String getTransHex(String txHash, int cont) {
		if(cont>5)
			return null;    
		String valCache = transHexCache.getIfPresent(txHash);
		if(valCache!=null)
			return valCache;
		
		TransHexProvider trh1 = transHexProviders.take();
		TransHexProvider trh2 = transHexProviders.takeDifferent(trh1);
		TransHexProvider trh3 = transHexProviders.takeDifferent(trh1,trh2);
		
		log("trh1=" + trh1 + " trh2=" + trh2 + " trh3=" + trh3 );
		Callable<String> runner1 = new TransHexRunnable(trh1, txHash);
		Callable<String> runner2 = new TransHexRunnable(trh2, txHash);
		Callable<String> runner3 = new TransHexRunnable(trh3, txHash);
		final Long start=System.currentTimeMillis();
		ListenableFuture<String> listenableFuture1 = moreExecutor.submit(runner1);
		ListenableFuture<String> listenableFuture2 = moreExecutor.submit(runner2);
		ListenableFuture<String> listenableFuture3 = moreExecutor.submit(runner3);
		SettableFuture<String> first  = SettableFuture.create();
		SettableFuture<String> second = SettableFuture.create();
		 
		Futures.addCallback(listenableFuture1,new TransHexFutureCallback(start,  trh1, first, second, transHexProviders ));
		Futures.addCallback(listenableFuture2,new TransHexFutureCallback(start,  trh2, first, second, transHexProviders ));
		Futures.addCallback(listenableFuture3,new TransHexFutureCallback(start,  trh3, first, second, transHexProviders ));
		Runnable checker = new TransHexCheckRunnable(txHash,
				listenableFuture1, trh1, 
				listenableFuture2, trh2,  
				listenableFuture3, trh3, 
				transHexProviders, transHexCache); 
		moreExecutor.execute(checker);
		try {
			
			String firstTx   = first.get();
			String secondTx  = second.get();
			if( firstTx!=null && firstTx.equals(secondTx) ) {
				transHexCache.put(txHash, firstTx);
				log(txCache.toString());
				return firstTx;
			}
		} catch (Exception e) {		
		}
		return getTransHex(txHash,cont+1);
	}

	@Override
	public Transaction getTransaction(String txHash) {
		return getTransaction(txHash, 0);
	}
	
	private Transaction getTransaction(String txHash, int cont) {
		if(cont>5)
			return null;    
		Transaction valCache = txCache.getIfPresent(txHash);
		if(valCache!=null)
			return valCache;  /* the block in confirmed cache is returned */
		
		TransactionProvider tra1 = transactionProviders.take();
		TransactionProvider tra2 = transactionProviders.takeDifferent(tra1);
		TransactionProvider tra3 = transactionProviders.takeDifferent(tra1,tra2);
		
		log("tra1=" + tra1 + " tra2=" + tra2 + " tra3=" + tra3 );
		Callable<Transaction> runner1 = new TransactionRunnable(tra1, txHash);
		Callable<Transaction> runner2 = new TransactionRunnable(tra2, txHash);
		Callable<Transaction> runner3 = new TransactionRunnable(tra3, txHash);
		final Long start=System.currentTimeMillis();
		ListenableFuture<Transaction> listenableFuture1 = moreExecutor.submit(runner1);
		ListenableFuture<Transaction> listenableFuture2 = moreExecutor.submit(runner2);
		ListenableFuture<Transaction> listenableFuture3 = moreExecutor.submit(runner3);
		SettableFuture<Transaction> first  = SettableFuture.create();
		SettableFuture<Transaction> second = SettableFuture.create();
		 
		Futures.addCallback(listenableFuture1,new TransactionFutureCallback(start,  tra1, first, second, transactionProviders ));
		Futures.addCallback(listenableFuture2,new TransactionFutureCallback(start,  tra2, first, second, transactionProviders ));
		Futures.addCallback(listenableFuture3,new TransactionFutureCallback(start,  tra3, first, second, transactionProviders ));
		Runnable checker = new TransactionCheckRunnable(txHash,
				listenableFuture1, tra1, 
				listenableFuture2, tra2,  
				listenableFuture3, tra3, 
				transactionProviders, txCache); 
		moreExecutor.execute(checker);
		try {
			
			Transaction firstTx   = first.get();
			Transaction secondTx  = second.get();
			if( firstTx!=null && firstTx.equals(secondTx) ) {
				txCache.put(txHash, firstTx);
				log(txCache.toString() );
				return firstTx;
			}
		} catch (Exception e) {		
		}
		return getTransaction(txHash,cont+1);
	}

	public BlockProviders getBlockProviders() {
		return blockProviders;
	}

	public TransactionProviders getTransactionProviders() {
		return transactionProviders;
	}

	public TransHexProviders getTransHexProviders() {
		return transHexProviders;
	}

	public BlocksCache getBlocksCache() {
		return blocksCache;
	}

	public LoadingCache<String, Transaction> getTxCache() {
		return txCache;
	}

	public LoadingCache<String, String> getTransHexCache() {
		return transHexCache;
	}

	
	
	
	
	
	
}
