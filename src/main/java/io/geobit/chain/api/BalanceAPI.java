package io.geobit.chain.api;

import io.geobit.chain.dispatchers.BalanceAndReceivedDispatchers;
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

@Path("/btc/v1/balance")
public class BalanceAPI {
	
	@GET
	@Path("/{address}")
	@Produces({MediaType.TEXT_PLAIN}) 
	public Response balance(@PathParam("address") String address, @QueryParam("cache") String cache) {
		String returned="";
		BalanceAndReceivedDispatchers disp = BalanceAndReceivedDispatchers.getInstance();
		boolean useCache = (cache!=null);
		
		if(address.contains(",")) {
			String addresses[]=address.split(",");
			List<String> results=new LinkedList<String>();
			for(String current : addresses) {
				if(!Validator.isValidAddress(current))
					return Response.status(Status.BAD_REQUEST).build();
				
				Long ret= useCache ? 
						disp.getBalanceCache(current) : 
						disp.getBalance(current);
				
				results.add(ret.toString());
				System.out.println(ret);
			}
			returned = Joiner.on(",").join(results);
		} else {
			if(!Validator.isValidAddress(address))
				return Response.status(Status.BAD_REQUEST).build();
			
			Long ret= useCache ? 
					disp.getBalanceCache(address) : 
					disp.getBalance(address);	
					
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
