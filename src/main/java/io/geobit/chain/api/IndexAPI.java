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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.base.Strings;

@Path("/")
public class IndexAPI {
	private static final String head   = "<html><body>";
	private static final String foot   = "</html></body>";
	private static final String title  = "<h1>Rebecca <small style=\"color:gray;font-size:60%\">redundant blockchain data provider</small></h1>";
	
	@GET
	@Produces({MediaType.TEXT_HTML}) 
	public Response addressTransactions() {
		
		
		String sub1   = "<h2>Api</h2>";
		String sub2   = "<h2>Links</h2>";
		String link   = "<a href=\"btc/v1/%s\">%s</a>";
		String linkli = "<li><a href=\"btc/v1/%s\">%s</a></li>";

		String myAddress      = "1DiWBDy3eejMtUUPAvVLqWmPVsecbqbrSU";
		String anotherAddress = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
		String txHash         = "ec0b571d8eafa5ccbb81a509e563b8142d045aece050909154b638758cc5d425";
		
		String balanceLinks = String.format(link, "balance/" +myAddress               , "balance") + ", " +
				String.format(link, "balance/" +myAddress + ","  +anotherAddress      , "multi balance") 
		
				;
		
		String receivedLinks = String.format(link, "received/" +myAddress               , "received") + ", " +
				String.format(link, "received/" +myAddress + ","  +anotherAddress      , "multi received")
				;
		
		String apiMethods = "<ul>" + 
				"<li>" + balanceLinks  + "</li>"    + 
				"<li>" + receivedLinks + "</li>"    +
				String.format(linkli, "address-transactions/" + myAddress , "address-transactions") +
				String.format(linkli, "address-unspents/" + "1G8sGKyw4wFGQXBZxk4df6uvCxGb1jR5sJ"     , "address-unspents") +
				String.format(linkli, "block/300000"                      , "block")   +
				String.format(linkli, "cache-invalidate/" + myAddress     , "cache-invalidate")   +
				String.format(linkli, "transaction/" + txHash             , "transaction")     +
				String.format(linkli, "trans-hex/" + txHash               , "trans-hex") + 
				"</ul>";
		
		String links = "<ul>" +
				String.format(linkli, "stats"                             , "Stats")    +
				String.format(linkli, "logs"                              , "Logs")    +
				"<li><a href=\"https://github.com/RCasatta/geobit-chain\">Github</a></li>" +
				"</ul>";
		
		String page= head + IndexAPI.header() + sub1 + apiMethods + sub2 + links + IndexAPI.footer("Home") + foot;
		
		return Response.ok( page ).build();
		
	}
	
	public static String header() {
		return header("");
	}
	
	public static String header(String sub) {
		if(Strings.isNullOrEmpty(sub))
			return title;
		else
			return title + "<h2>" + sub + "</h2>";
	}
	
	public static String footer(String name) {
		String genericLink="<a href=\"%s\">%s</a>";

		StringBuffer sb=new StringBuffer();
		
		if("Home".equals(name)) 
			sb.append("<b>Home</b>");
		else 
			sb.append(String.format(genericLink, "/", "Home"));
		
		sb.append(" | ");
		
		if("Stats".equals(name)) 
			sb.append("<b>Stats</b>");
		else 
			sb.append(String.format(genericLink, "/btc/v1/stats", "Stats"));
		
		sb.append(" | ");
		
		if("Logs".equals(name)) 
			sb.append("<b>Logs</b>");
		else 
			sb.append(String.format(genericLink, "/btc/v1/logs", "Logs"));
		
		return "<div align=\"center\">" + sb.toString() + "</div>";
		
	}
}
