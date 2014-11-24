package io.geobit.clients.http;

import static org.junit.Assert.*;
import io.geobit.chain.clients.InsightBitpayHTTPClient;
import io.geobit.common.entity.Block;
import io.geobit.common.providers.BalanceProvider;
import io.geobit.common.providers.BlockProvider;

import org.junit.Test;

public class InsightHTTPClientTest {
	
	@Test
	public void insightTest() {
		BlockProvider prov=new InsightBitpayHTTPClient();
		Block b=prov.getBlock(300000);
		System.out.println(b);
		assertNotNull(b);
		
		BalanceProvider prov2 =new InsightBitpayHTTPClient();
		Long l = prov2.getBalance("1G8sGKyw4wFGQXBZxk4df6uvCxGb1jR5sJ");
		assertNotNull(l);
		System.out.println(l);
	}

}
