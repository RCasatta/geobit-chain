package io.geobit.chain.providers.received;

import java.util.concurrent.Future;

import com.google.common.base.Objects;
import com.google.common.cache.LoadingCache;

public class ReceivedCheckRunnable implements Runnable {
	private String address;
	private Future<Long> firstFuture;
	private ReceivedProvider firstProvider;
	private Future<Long> secondFuture;
	private ReceivedProvider secondProvider;
	private ReceivedProviders providers;
	private LoadingCache<String, Long> receivedCache;



	public ReceivedCheckRunnable(String address, Future<Long> firstFuture,
			ReceivedProvider firstProvider, Future<Long> secondFuture,
			ReceivedProvider secondProvider, 
			ReceivedProviders providers, LoadingCache<String, Long> receivedCache) {
		super();
		this.address        = address;
		this.firstFuture    = firstFuture;
		this.firstProvider  = firstProvider;
		this.secondFuture   = secondFuture;
		this.secondProvider = secondProvider;
		this.providers      = providers;
		this.receivedCache   = receivedCache;
	}

	@Override
	public void run() {
		try {
			System.out.println("started check received");
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
				ReceivedProvider thirdProvider=providers.takeDifferent(firstProvider,secondProvider);
				
				Long thirdResult=thirdProvider.getReceived(address);
				if( Objects.equal( firstResult , thirdResult) ) {
					System.out.println(secondProvider + " gave bad result " + secondResult +". correct received(" + address + ") is " + firstResult + " from " + firstProvider  + " and " + thirdProvider  );
					receivedCache.put(address, firstResult);
					providers.record(secondProvider, null);
				} else if (Objects.equal( secondResult , thirdResult)) {
					System.out.println(firstProvider  + " gave bad result " + firstResult +". correct received(" + address + ") is " + secondResult + " from " + secondProvider + " and " + thirdProvider  );
					receivedCache.put(address, secondResult);
					providers.record(firstProvider, null);
				} else {
					System.out.println("THREE DIFFERENT RESULT!!!! for " + address + " are " + firstResult + " " + secondResult + " " + thirdResult);
				}
				
			}
			
		} catch (Exception e) {
			System.out.println("CheckReceivedRunnable exception " + e.getMessage() );
		}
	}


}
