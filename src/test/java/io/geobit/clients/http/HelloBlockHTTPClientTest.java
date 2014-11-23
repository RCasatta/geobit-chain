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

package io.geobit.clients.http;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.geobit.chain.clients.HelloBlockHTTPClient;
import io.geobit.common.entity.Transaction;
import io.geobit.common.providers.BalanceProvider;
import io.geobit.common.providers.ReceivedProvider;
import io.geobit.common.providers.TransactionProvider;

import org.junit.Test;

public class HelloBlockHTTPClientTest {
	
	

	@Test 
	public void helloblockTest() {

		BalanceProvider balanceProv=new HelloBlockHTTPClient();
		
		Long result=balanceProv.getBalance("1DiWBDy3eejMtUUPAvVLqWmPVsecbqbrSU");
		assertNotNull(result);
		assertTrue(result>0);
		
		ReceivedProvider receivedProv=new HelloBlockHTTPClient();
		
		Long result2=receivedProv.getReceived("1DiWBDy3eejMtUUPAvVLqWmPVsecbqbrSU");
		assertNotNull(result2);
		assertTrue(result2>0);
		
		TransactionProvider transProv=new HelloBlockHTTPClient();
		
		Transaction t=transProv.getTransaction(TestData.TRANS_OF_GEO_ADDRESS_1);
		assertNotNull(t);
		
//		NewTransactionListenerProvider transListProv=new HelloBlockHTTPClient();
//		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(40);
//		transListProv.listenNewTransactions(queue);
//		transListProv.listenNewTransactions(queue);
		
		
	}

}
