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

package io.geobit.chain.api;

import io.geobit.chain.dispatchers.AddressTransactionsDispatcher;
import io.geobit.chain.dispatchers.BalanceAndReceivedDispatcher;
import io.geobit.chain.dispatchers.BlockAndTransactionDispatcher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/btc/v1/stats")
public class StatsAPI {
	
	@GET
	@Produces({MediaType.TEXT_HTML}) 
	public Response stats() {
		String head="<html><body>";
		String foot="</html></body>";
		
		BalanceAndReceivedDispatcher disp = BalanceAndReceivedDispatcher.getInstance();
		String pre1 = "<pre>" +	"BalanceProviders\n" + disp.getBalanceProviders().getTimers().toString() +"</pre>";
		String pre2 = "<pre>" +	"ReceivedProviders\n" + disp.getReceivedProviders().getTimers().toString() +"</pre>";
		
		BlockAndTransactionDispatcher disp2 = BlockAndTransactionDispatcher.getInstance();
		String pre3 = "<pre>" +	"BlockProviders\n" + disp2.getBlockProviders().getTimers().toString() +"</pre>";
		String pre4 = "<pre>" +	"TransactionProviders\n" + disp2.getTransactionProviders().getTimers().toString() +"</pre>";
		String pre5 = "<pre>" +	"TransHexProviders\n" + disp2.getTransHexProviders().getTimers().toString() +"</pre>";
		
		AddressTransactionsDispatcher disp3 = AddressTransactionsDispatcher.getInstance();
		String pre6 = "<pre>" +	"AddressTransactionsProviders\n" + disp3.getAddTxsProviders().getTimers().toString() +"</pre>";
		
		
		String cache1 =   "Balance and Received cache size=" +disp.getCache().size() + "\n";
		String cache2 =  disp2.getBlocksCache().toString() + "\n";
		String cache3 = "Transaction cache size=" +disp2.getTxCache().size() + "\n";
		String cache4 = "Trans Hex cache size=" +disp2.getTransHexCache().size() + "\n";
		String cache5 = "AddressTransactionsProviders cache size=" +disp3.getCache().size() + "\n";
		
		String page = head + IndexAPI.header("Statistics") +
				pre1 + pre2 + 
				pre3 + pre4 + 
				pre5 + pre6 + 
				"<pre>" + cache1 + cache2 + 
				cache3 + cache4 + cache5 + "</pre>" +
				IndexAPI.footer("Stats") + foot;
		return Response.ok( page ).build();
		
	}
	
	
}
