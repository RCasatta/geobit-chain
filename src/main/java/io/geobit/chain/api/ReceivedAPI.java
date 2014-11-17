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

import io.geobit.chain.dispatchers.BalanceAndReceivedDispatcher;
import io.geobit.common.btc.Validator;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.Joiner;

@Path("/btc/v1/received")
public class ReceivedAPI {
	
	@GET
	@Path("/{address}")
	@Produces({MediaType.TEXT_PLAIN}) 
	public Response received(@PathParam("address") String address, @QueryParam("cache") String cache) {
		String returned="";
		BalanceAndReceivedDispatcher disp = BalanceAndReceivedDispatcher.getInstance();
		boolean useCache = (cache!=null);
		
		if(address.contains(",")) {
			String addresses[]=address.split(",");
			List<String> results=new LinkedList<String>();
			for(String current : addresses) {
				if(!Validator.isValidAddress(current))
					return Response.status(Status.BAD_REQUEST).build();
				
				Long ret= useCache ? 
						disp.getReceivedCache(current) : 
						disp.getReceived(current);
				
				results.add(ret.toString());
				
			}
			returned = Joiner.on(",").join(results);
		} else {
			if(!Validator.isValidAddress(address))
				return Response.status(Status.BAD_REQUEST).build();
			
			Long ret= useCache ? 
					disp.getReceivedCache(address) : 
					disp.getReceived(address);	
					
			returned = ret.toString();
		}
		
		CacheControl control = new CacheControl();
		control.setMaxAge( 10 );
		control.setNoTransform(false);
		
		return Response
				.ok(returned)
				.cacheControl(control)
				.lastModified( new Date() )
				.build();
	}
}
