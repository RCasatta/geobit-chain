package io.geobit.chain.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class IndexAPI {

	@GET
	@Produces({MediaType.TEXT_HTML}) 
	public Response addressTransactions() {
		String head="<html><body><ul>";
		String link="<li><a href=\"btc/v1/%s\">%s</a></li>";
		String foot="<html><body></ul>";
		
		String myAddress = "1DiWBDy3eejMtUUPAvVLqWmPVsecbqbrSU";
		String txHash    = "ec0b571d8eafa5ccbb81a509e563b8142d045aece050909154b638758cc5d425";
		
		
		String page= head +
				String.format(link, "address-transactions/" + myAddress , "address-transactions") +
				String.format(link, "balance/" +myAddress               , "balance")     +
				String.format(link, "block/300000"                      , "block")   +
				String.format(link, "cache-invalidate/" + myAddress     , "cache-invalidate")   +
				String.format(link, "received/" + myAddress             , "received")   +
				String.format(link, "transaction/" + txHash             , "transaction")     +
				String.format(link, "transHex/"                         , "transHex")    +
				foot;
		return Response.ok( page ).build();
		
	}
}
