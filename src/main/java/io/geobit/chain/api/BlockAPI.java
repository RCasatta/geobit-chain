package io.geobit.chain.api;

import io.geobit.chain.dispatchers.BlockAndTransactionDispatcher;
import io.geobit.common.entity.Block;
import io.geobit.common.statics.StaticStrings;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/btc/v1/block")
public class BlockAPI {

	@GET
	@Path("/{height}")
	@Produces({MediaType.APPLICATION_JSON}) 
	public Response addressTransactions(@PathParam("height") String sHeight) {
		BlockAndTransactionDispatcher disp=BlockAndTransactionDispatcher.getInstance();
		Integer height;
		try {
			height=Integer.parseInt(sHeight);
		} catch (Exception e) {
			return Response.status(400).build();
		}
		Block b=disp.getBlock(height);
		
		return Response.ok(b).build();
	}
}
