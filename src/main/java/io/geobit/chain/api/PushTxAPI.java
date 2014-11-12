package io.geobit.chain.api;

import io.geobit.common.statics.StaticStrings;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/btc/v1/push-tx")
public class PushTxAPI {
	
	@POST
	@Produces({MediaType.APPLICATION_JSON}) 
	public Response cacheInvalidate(@FormParam("tx-hex") String txHex) {
		return Response.ok(StaticStrings.UNDER_CONSTRUCTION_JSON).build();
	}
}
