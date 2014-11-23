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
import io.geobit.chain.clients.BlockrHTTPClient;
import io.geobit.common.entity.Transaction;
import io.geobit.common.providers.BalanceProvider;
import io.geobit.common.providers.ReceivedProvider;
import io.geobit.common.providers.TransHexProvider;
import io.geobit.common.providers.TransactionProvider;

import org.junit.Test;

public class BlockrHTTPClientTest {
	
	@Test 
	public void blockrTest() {
		BalanceProvider balanceProv=new BlockrHTTPClient();
		Long result=balanceProv.getBalance("1DiWBDy3eejMtUUPAvVLqWmPVsecbqbrSU");
		assertNotNull(result);
		assertTrue(result>0);
		
		ReceivedProvider receivedProv=new BlockrHTTPClient();
		result=receivedProv.getReceived("1DiWBDy3eejMtUUPAvVLqWmPVsecbqbrSU");
		assertNotNull(result);
		assertTrue(result>0);
		
		TransactionProvider transProv = new  BlockrHTTPClient();
		Transaction t=transProv.getTransaction("dc3fdb02f11c037f90c25a83e188bfc6ede64327dd658a33cc981fc5750564c3");
		assertNotNull(t);
		System.out.println("t=" + t);
		 
		
//		GeobitProvider geoProv = new BlockrHTTPClient();
//		Geobit gg=geoProv.getGeobit("1GGhRitEs16iCQoi37SmdxVEChhJe3HGzX");
//		assertNotNull(gg);
//		System.out.println("gg=" + gg);
		
		TransHexProvider transHexProv=new BlockrHTTPClient();
		String hex=transHexProv.getTransHex("dc3fdb02f11c037f90c25a83e188bfc6ede64327dd658a33cc981fc5750564c3");
		System.out.println("hex=" + hex);
		assertEquals(
				"01000000017d9d5ee684871124984352a619a2ba7e47dcbe938fe03d309954c5c37dead35b000000006b483045022100ef2ff0eaa8592ba063b23cb5ed4426ec8da75e084eb5d0c639e7ab557244b6c0022062faf9cad264d5d940f5f41b764da8150b20e3ea38f04154cd3aee7674dce600012103e932232ef41a3d2ef858ec3d6eb4ea13b28a096fa5e9a85761f7f578deea1e0effffffff0230d12501000000001976a9146f4c339296f9cb2d45419564f45121ccd84d505188acb8560000000000001976a914a781df4fda5411ecf867101cb18212403d07b6eb88ac00000000"
				,hex);
		
	}

}
