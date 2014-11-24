package io.geobit.chain.clients;

import static io.geobit.common.statics.Log.error;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.chain.providers.block.BlockProvider;
import io.geobit.common.entity.Block;
import io.geobit.common.statics.StaticNumbers;
import io.geobit.common.statics.StaticStrings;
import javax.ws.rs.core.MediaType;


public class InsightBitpayHTTPClient implements BalanceProvider,BlockProvider {
	private static final String prefix= "https://insight.bitpay.com/api";
	
	private WebResource addr;
	private WebResource block;
	
	/**
	https://github.com/bitpay/insight-api#api
	*/
	public InsightBitpayHTTPClient() {
		super();

		Client client;
		client= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(   StaticNumbers.READ_TIMEOUT);

		addr     = client.resource(prefix + "/addr/" );
		block     = client.resource(prefix + "/block/" );
	}
	
	@Override
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * https://insight.bitpay.com/api/block/00000000000000000e3485183cfcc7b218f23f18d8df72dc1d4a11ac0a4c56a3
	 */
	@Override
	public Block getBlock(Integer height) {
		try {
			String rawBlock=block
					.path(height.toString())
					.accept(MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);
			JSONObject obj      = new JSONObject(rawBlock);
			JSONObject jsonData = obj.getJSONObject("data");
			String hash     = jsonData.getString("hash");
			int heightRet       = jsonData.getInt("height");
			Long timestamp = jsonData.getLong("time");
			Block b = new Block();
			b.setHash(hash);
			b.setHeight( heightRet ) ;

			b.setTime( timestamp );
			return b;

		} catch (Exception e) {
			error("error on InsightBitpayHTTPClient getBlock() " + e.getMessage() );
		}

		return null;
	}
	
	
	@Override
	public Long getBalance(String address) {
		Long resultLong=null;
		try {
			
			String result = addr
					.path(address+"/balance")
					.queryParam("noTxList","1")
					.queryParam("noCache", "1")
					.accept(MediaType.TEXT_PLAIN)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);

			resultLong = Long.parseLong(result);
		} catch(Exception e) {
			String mes = "exception on InsightBitpayHTTPClient getBalance " + e.getMessage();
			error(mes);

		}
		return resultLong;
	}

	@Override
	public String toString() {
		return getPrefix();
	}
}
