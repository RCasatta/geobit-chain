package io.geobit.chain.api;

import io.geobit.common.statics.StaticStrings;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/btc/v1/cache-invalidate")
public class CacheInvalidateAPI {
	
	@GET
	@Path("/{address}")
	@Produces({MediaType.APPLICATION_JSON}) 
	public Response cacheInvalidate(@PathParam("address") String address) {
		return Response.ok(StaticStrings.UNDER_CONSTRUCTION_JSON).build();
	}

}
