package io.geobit.chain.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/btc/v1/block")
public class BlockAPI {

	@GET
	@Path("/{hash}")
	@Produces({MediaType.APPLICATION_JSON}) 
	public Response addressTransactions(@PathParam("hash") String hash) {
		return Response.ok("{}").build();
	}
}
