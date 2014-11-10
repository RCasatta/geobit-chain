package io.geobit.chain.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/btc/v1/push-tx")
public class PushTxAPI {
	
	@POST
	@Produces({MediaType.TEXT_PLAIN}) 
	public Response cacheInvalidate(@FormParam("tx-hex") String txHex) {
		return Response.ok().build();
	}
}
