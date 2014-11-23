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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.geobit.chain.clients.BitEasyHTTPClient;
import io.geobit.chain.clients.BlockChainHTTPClient;
import io.geobit.chain.clients.BlockCypherHTTPClient;
import io.geobit.chain.clients.BlockrHTTPClient;
import io.geobit.chain.clients.ChainHTTPClient;
import io.geobit.chain.clients.HelloBlockHTTPClient;
import io.geobit.chain.clients.SoChainHTTPClient;
import io.geobit.common.entity.Block;
import io.geobit.common.entity.Transaction;
import io.geobit.common.providers.BalanceProvider;
import io.geobit.common.providers.BlockProvider;
import io.geobit.common.providers.ReceivedProvider;
import io.geobit.common.providers.TransactionProvider;

import org.junit.Test;

public class AllHTTPClientTest {

	private static final String MYADDRESS = "1DiWBDy3eejMtUUPAvVLqWmPVsecbqbrSU";


	@Test 
	public void getBalanceTest() {
		BalanceProvider balanceProv=new BlockChainHTTPClient();

		Long result=balanceProv.getBalance(MYADDRESS);
		assertNotNull(result);
		assertTrue(result>0);

		BalanceProvider balanceProv2=new BlockrHTTPClient();

		Long result2=balanceProv2.getBalance(MYADDRESS);
		assertNotNull(result2);
		assertTrue(result2>0);
		System.out.println("getBalance=" + result);
		assertEquals(result, result2);


		BalanceProvider balanceProv3=new BlockrHTTPClient();

		Long result3=balanceProv3.getBalance(MYADDRESS);
		assertNotNull(result3);
		assertTrue(result3>0);

		System.out.println("getBalance2=" + result2);
		assertEquals(result2, result3);

		BalanceProvider balanceProv4=new BitEasyHTTPClient();

		Long result4=balanceProv4.getBalance(MYADDRESS);
		assertNotNull(result4);
		assertTrue(result4>0);

		System.out.println("getBalance3=" + result3);
		assertEquals(result3, result4);
		System.out.println("getBalance4=" + result4);

		BalanceProvider balanceProv5=new BitEasyHTTPClient();

		Long result5=balanceProv5.getBalance(MYADDRESS);
		assertNotNull(result5);
		assertTrue(result5>0);

		System.out.println("getBalance5=" + result5);
		assertEquals(result4, result5);
		
		BalanceProvider balanceProv6=new ChainHTTPClient();

		Long result6=balanceProv6.getBalance(MYADDRESS);
		assertNotNull(result6);
		assertTrue(result6>0);

		System.out.println("getBalance6=" + result6);
		assertEquals(result5, result6);
		
		
		BalanceProvider balanceProvBroken=new BlockCypherHTTPClient();

		Long result7=balanceProvBroken.getBalance(MYADDRESS);
		System.out.println("getBalance7= " + result7);
	    if(result7!=null) assertEquals(result6, result7);
	    
//		BalanceProvider balanceProvCopper=new CopperLarkMerdaHTTPClient();
//
//		Long result8=balanceProvCopper.getBalance(MYADDRESS);
//		System.out.println("getBalance8= " + result8);
//	    assertEquals(result7, result8);
	}

	@Test 
	public void getReceivedTest() {
		ReceivedProvider balanceProv=new BlockChainHTTPClient();

		Long result=balanceProv.getReceived(MYADDRESS);
		assertNotNull(result);
		assertTrue(result>0);
		System.out.println("getReceived=" + result );
		
		ReceivedProvider balanceProv2=new BitEasyHTTPClient();
		Long result2=balanceProv2.getReceived(MYADDRESS);
		assertNotNull(result2);
		assertTrue(result2>0);
		assertEquals(result, result2);
		System.out.println("getReceived2=" + result2 );


		ReceivedProvider balanceProv3=new HelloBlockHTTPClient();
		Long result3=balanceProv3.getReceived(MYADDRESS);
		assertNotNull(result3);
		assertTrue(result3>0);
		System.out.println("getReceived3=" + result3 );
		assertEquals(result2, result3);
		
		
		ReceivedProvider balanceProv4=new ChainHTTPClient();

		Long result4=balanceProv4.getReceived(MYADDRESS);
		assertNotNull(result4);
		assertTrue(result4>0);
		System.out.println("getReceived4=" + result4);
		
		
//		ReceivedProvider balanceProvBroken=new BlockCypherHTTPClient();
//
//		Long result5=balanceProvBroken.getReceived(MYADDRESS);
//		System.out.println("getReceived5=" + result5);
//		
//		if(result5!=null) assertEquals(result4, result5);
	}

	@Test 
	public void getTransactionTest() {
		TransactionProvider transProv = new BlockChainHTTPClient();
		
		Transaction t=transProv.getTransaction("dc3fdb02f11c037f90c25a83e188bfc6ede64327dd658a33cc981fc5750564c3");
		assertNotNull(t);
		System.out.println("t=" + t);


		TransactionProvider transProv2 = new  BlockrHTTPClient();
		Transaction t2=transProv2.getTransaction("dc3fdb02f11c037f90c25a83e188bfc6ede64327dd658a33cc981fc5750564c3");
		assertNotNull(t2);
		System.out.println("t2=" + t2);

		assertEquals(t.toString().replaceAll(",", "\n"), t2.toString().replaceAll(",", "\n"));

		TransactionProvider transProv3=new HelloBlockHTTPClient();
		Transaction t3=transProv3.getTransaction("dc3fdb02f11c037f90c25a83e188bfc6ede64327dd658a33cc981fc5750564c3");
		assertNotNull(t3);
		System.out.println("t3=" + t3);

		assertEquals(t.toString().replaceAll(",", "\n"), t3.toString().replaceAll(",", "\n"));
		
		
		Transaction tWithLocked=transProv.getTransaction("78796c8e7226712b51f022bda1441fdcd949a5c3b069a7d0e0702e91a0030b12");
		Transaction tWithLocked2=transProv2.getTransaction("78796c8e7226712b51f022bda1441fdcd949a5c3b069a7d0e0702e91a0030b12");
		Transaction tWithLocked3=transProv3.getTransaction("78796c8e7226712b51f022bda1441fdcd949a5c3b069a7d0e0702e91a0030b12");
		
		assertEquals(tWithLocked.toString().replaceAll(",", "\n"), tWithLocked2.toString().replaceAll(",", "\n"));
		assertEquals(tWithLocked.toString().replaceAll(",", "\n"), tWithLocked3.toString().replaceAll(",", "\n"));
	}



	@Test 
	public void getGeobitTest() {	
		getGeobitTestWithAddress("1G8sGKyw4wFGQXBZxk4df6uvCxGb1jR5sJ");
		
		getGeobitTestWithAddress("1GHu8a9fccceHCosQY4X7EgtPxmCH636VL");
		getGeobitTestWithAddress("1GGhRitEs16iCQoi37SmdxVEChhJe3HGzX");	
		getGeobitTestWithAddress("1G9ohyYk2VgANevktHETWKqNSzbDXMQu55");
	}

	@Test
	public void getBlockTest() {
		BlockProvider blockProv=new BlockChainHTTPClient();
		Block b=blockProv.getBlock(123123);
		assertNotNull(b);

		BlockProvider blockProv2=new HelloBlockHTTPClient();
		Block b2=blockProv2.getBlock(123123);
		assertNotNull(b2);

		assertEquals(b.toString().replaceAll(",", "\n"), b2.toString().replaceAll(",", "\n"));
		System.out.println("Block=" +b);
		
		
		BlockProvider blockProv3=new BlockrHTTPClient();
		Block b3=blockProv3.getBlock(123123);
		assertNotNull(b3);
		
		assertEquals(b.toString().replaceAll(",", "\n"), b3.toString().replaceAll(",", "\n"));
		


	}


	private void getGeobitTestWithAddress(String address) {
	}
//				HTTPClient client=HTTPClient.getInstance();
//				Geobit old=client.getInfoNoCache(address);
//				String dataOld = old.toString().replaceAll(",", "\n");
//				System.out.println("geobit old="+dataOld);
//				System.out.println("total transactions on old Geobit=" + old.getTotalTransactions());
//
//		GeobitProvider geoProv = new BlockChainHTTPClient();
//		Geobit g=geoProv.getGeobit(address);
//		assertNotNull(g);
//		String data = g.toString().replaceAll(",", "\n");
//		//		System.out.println("geobit1="+data);
//		//		System.out.println("total transactions on g=" + old.getTotalTransactions());
//
//		//		assertEquals(dataOld, data);
//
//		GeobitProvider geoProv3 = new HelloBlockHTTPClient();
//		Geobit g3=geoProv3.getGeobit(address);
//		assertNotNull(g3);
//
//		int total3=g3.getTotalTransactions();
//		System.out.println("total transactions on g3=" + total3);
//		int total=g.getTotalTransactions();
//		assertEquals(total, total3);
//
//		String data3 = g3.toString().replaceAll(",", "\n");
//		//		System.out.println("geobit3="+data);
//
//		System.out.println("ASSERT EQUALS 1!");
//		//assertEquals(data, data3);
//
//		//		GeobitProvider geoProv2 = new BlockrHTTPClient();
//		//		Geobit g2=geoProv2.getGeobit("1GGhRitEs16iCQoi37SmdxVEChhJe3HGzX");
//		//		assertNotNull(g2);
//		//		String data2 = g2.toString().replaceAll(",", "\n");
//		//		System.out.println("geobit2="+data2);
//		//		
//		//		assertEquals(data, data2);		
//		
//		
//				GeobitProvider geoProv5 = new SoChainHTTPClient();
//				Geobit g5=geoProv5.getGeobit(address);
//				assertNotNull(g5);
//				String data5 = g5.toString().replaceAll(",", "\n");
//				System.out.println("geobit2="+data5);
//
//				System.out.println("ASSERT EQUALS 2!");
//				assertEquals(data, data5);		
//	}
}
