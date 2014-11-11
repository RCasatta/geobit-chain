package io.geobit.chain.providers.balance;

import java.util.concurrent.Future;

import com.google.common.base.Objects;
import com.google.common.cache.LoadingCache;

public class BalanceCheckRunnable implements Runnable {
	private String address;
	private Future<Long> firstFuture;
	private BalanceProvider firstProvider;
	private Future<Long> secondFuture;
	private BalanceProvider secondProvider;
	private BalanceProviders providers;
	private LoadingCache<String, Long> balanceCache;



	public BalanceCheckRunnable(String address, Future<Long> firstFuture,
			BalanceProvider firstProvider, Future<Long> secondFuture,
			BalanceProvider secondProvider, 
			BalanceProviders providers, LoadingCache<String, Long> balanceCache) {
		super();
		this.address        = address;
		this.firstFuture    = firstFuture;
		this.firstProvider  = firstProvider;
		this.secondFuture   = secondFuture;
		this.secondProvider = secondProvider;
		this.providers      = providers;
		this.balanceCache   = balanceCache;
	}

	@Override
	public void run() {
		BalanceProvider thirdProvider=null;
		try {
			System.out.println("started check balance");
			Long firstResult  = firstFuture.get();
			Long secondResult = secondFuture.get();
			String format = String.format("balance(%s)=%s from %s "
					+ "secondResult=%s from %s ", 
					address, 
					firstResult, firstProvider,
					secondResult, secondProvider );
			System.out.println(format);
			if(!Objects.equal( firstResult , secondResult))  {
				
				/* HANDLING DIFFERENCE */
				BalanceProvider current=null;
				do { /* need to take a provider different from the first and the second */
					current=providers.takeDifferent(firstProvider);
				} while( secondProvider==current );
				thirdProvider=current;
				
				Long thirdResult=thirdProvider.getBalance(address);
				if( Objects.equal( firstResult , thirdResult) ) {
					System.out.println(secondProvider + " gave bad result " + secondResult +". correct balance(" + address + ") is " + firstResult + " from " + firstProvider + " and " + thirdProvider  );
					balanceCache.put(address, firstResult);
					providers.record(secondProvider, null);  // give penalty to provider giving bad result
				} else if (Objects.equal( secondResult , thirdResult)) {
					System.out.println(firstProvider + " gave bad result " + firstResult +". correct balance(" + address + ") is " + secondResult + " from " + secondProvider + " and " + thirdProvider  );
					balanceCache.put(address, secondResult); 
					providers.record(firstProvider , null); // give penalty to provider giving bad result
				} else {
					System.out.println("THREE DIFFERENT RESULT!!!! for " + address + " are " + firstResult + " " + secondResult + " " + thirdResult);
				}
				
			}
			
		} catch (Exception e) {
			System.out.println("CheckBalanceRunnable exception " + e.getMessage() );
		}
	}

}
