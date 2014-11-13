package io.geobit.chain.providers.addresstransactions;

import io.geobit.common.entity.AddressTransactions;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.google.common.base.Objects;
import com.google.common.cache.LoadingCache;

public class AddressTransactionsCheckRunnable implements Runnable {
	private String address;
	private Future<AddressTransactions> firstFuture;
	private AddressTransactionsProvider firstProvider;
	private Future<AddressTransactions> secondFuture;
	private AddressTransactionsProvider secondProvider;
	private AddressTransactionsProviders providers;
	private LoadingCache<String, AddressTransactions> addressTransactionsCache;



	public AddressTransactionsCheckRunnable(String address, Future<AddressTransactions> firstFuture,
			AddressTransactionsProvider firstProvider, Future<AddressTransactions> secondFuture,
			AddressTransactionsProvider secondProvider, 
			AddressTransactionsProviders providers, LoadingCache<String, AddressTransactions> addressTransactionsCache) {
		super();
		this.address        = address;
		this.firstFuture    = firstFuture;
		this.firstProvider  = firstProvider;
		this.secondFuture   = secondFuture;
		this.secondProvider = secondProvider;
		this.providers      = providers;
		this.addressTransactionsCache   = addressTransactionsCache;
	}

	@Override
	public void run() {
		AddressTransactionsProvider thirdProvider=null;
		try {
			System.out.println("started check received");
			AddressTransactions firstResult  = firstFuture.get();
			AddressTransactions secondResult = secondFuture.get();
			String format = String.format("balance(%s)=%s from %s "
					+ "secondResult=%s from %s ", 
					address, 
					firstResult, firstProvider,
					secondResult, secondProvider );
			System.out.println(format);
			if(!Objects.equal( firstResult , secondResult))  {
				
				/* HANDLING DIFFERENCE */
				AddressTransactionsProvider current=null;
				do { /* need to take a provider different from the first and the second */
					current=providers.takeDifferent(firstProvider);
				} while( secondProvider==current );
				thirdProvider=current;
				
				AddressTransactions thirdResult=thirdProvider.getAddressTransactions(address);
				if( Objects.equal( firstResult , thirdResult) ) {
					System.out.println(secondProvider + " gave bad result " + secondResult +". correct received(" + address + ") is " + firstResult + " from " + firstProvider  + " and " + thirdProvider  );
					addressTransactionsCache.put(address, firstResult);
					providers.record(secondProvider, null);
				} else if (Objects.equal( secondResult , thirdResult)) {
					System.out.println(firstProvider  + " gave bad result " + firstResult +". correct received(" + address + ") is " + secondResult + " from " + secondProvider + " and " + thirdProvider  );
					addressTransactionsCache.put(address, secondResult);
					providers.record(firstProvider, null);
				} else {
					System.out.println("THREE DIFFERENT RESULT!!!! for " + address + " are " + firstResult + " " + secondResult + " " + thirdResult);
				}
				
			}
			
		} catch (Exception e) {
			System.out.println("CheckAddressTransactionsRunnable exception " + e.getMessage() );
		}
	}


}
