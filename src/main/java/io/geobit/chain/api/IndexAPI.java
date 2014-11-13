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
		String head   = "<html><body>";
		String title  = "<h1>Redundant blockchain data provider</h1>";
		String sub1   = "<h2>Api methods</h2>";
		String sub2   = "<h2>Links</h2>";
		String link   = "<a href=\"btc/v1/%s\">%s</a>";
		String linkli = "<li><a href=\"btc/v1/%s\">%s</a></li>";
		String foot   = "</ul></html></body>";
		
		String myAddress      = "1DiWBDy3eejMtUUPAvVLqWmPVsecbqbrSU";
		String anotherAddress = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
		String txHash         = "ec0b571d8eafa5ccbb81a509e563b8142d045aece050909154b638758cc5d425";
		
		String balanceLinks = String.format(link, "balance/" +myAddress               , "balance") + ", " +
				String.format(link, "balance/" +myAddress + ","  +anotherAddress      , "multi balance") + ", " +
				String.format(link, "balance/" +myAddress + "?cache=true"             , "balance cache")
				;
		
		String receivedLinks = String.format(link, "received/" +myAddress               , "received") + ", " +
				String.format(link, "received/" +myAddress + ","  +anotherAddress      , "multi received") + ", " +
				String.format(link, "received/" +myAddress + "?cache=true"             , "received cache")
				;
		
		String apiMethods = "<ul>" + 
				"<li>" + balanceLinks  + "</li>"    + 
				"<li>" + receivedLinks + "</li>"    +
				String.format(linkli, "address-transactions/" + myAddress , "address-transactions") +
				String.format(linkli, "block/300000"                      , "block")   +
				String.format(linkli, "cache-invalidate/" + myAddress     , "cache-invalidate")   +
				String.format(linkli, "transaction/" + txHash             , "transaction")     +
				String.format(linkli, "trans-hex/" + txHash               , "trans-hex") + 
				"</ul>";
		
		String links = "<ul>" +
				String.format(linkli, "stats"                             , "stats")    +
				"<li><a href=\"https://github.com/RCasatta/geobit-chain\">github</a></li>" +
				"</ul>";
		
		String page= head + title + sub1 + apiMethods + sub2 + links + foot;
		
		return Response.ok( page ).build();
		
	}
}
