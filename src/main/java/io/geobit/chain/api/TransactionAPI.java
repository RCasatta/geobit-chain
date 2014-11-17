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

import io.geobit.chain.clients.SoChainHTTPClient;
import io.geobit.chain.dispatchers.BlockAndTransactionDispatcher;
import io.geobit.chain.providers.transaction.TransactionProvider;
import io.geobit.common.entity.Transaction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/btc/v1/transaction")
public class TransactionAPI {
	
	@GET
	@Path("/{tx-hash}")
	@Produces({MediaType.APPLICATION_JSON}) 
	public Response transaction(@PathParam("tx-hash") String txHash) {
		BlockAndTransactionDispatcher disp=BlockAndTransactionDispatcher.getInstance();

		Transaction t=disp.getTransaction(txHash);
		if(t!=null)
			return Response.ok(t).build();
		else
			return Response.status(400).build();
	}
	
	@GET
	@Path("/sochain/{tx-hash}")
	@Produces({MediaType.APPLICATION_JSON}) 
	public Response soChainTransaction(@PathParam("tx-hash") String txHash) {
		SoChainHTTPClient disp=new SoChainHTTPClient();
		Transaction t=disp.getTransaction(txHash);
		if(t!=null)
			return Response.ok(t).build();
		else
			return Response.status(400).build();
	}
}
