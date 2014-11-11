package io.geobit.chain.api;

import io.geobit.chain.dispatchers.BalanceAndReceivedDispatchers;

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
		
		BalanceAndReceivedDispatchers disp = BalanceAndReceivedDispatchers.getInstance();
		String pre1 = "<pre>" +	disp.getBalanceProviders().getTimers().toString() +"</pre>";
		String pre2 = "<pre>" +	disp.getReceivedProviders().getTimers().toString() +"</pre>";
		
		String page = head + pre1 + pre2 + foot;
		return Response.ok( page ).build();
		
	}
	
	
}
