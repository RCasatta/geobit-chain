package io.geobit.chain.clients;

import io.geobit.chain.api.TransactionAPI;
import io.geobit.chain.entity.sochain.SoChainData;
import io.geobit.chain.entity.sochain.SoChainResponse;
import io.geobit.chain.entity.sochain.SoChainTransaction;
import io.geobit.chain.providers.AddressTransactionsProvider;
import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.common.entity.AddressTransactions;
import io.geobit.common.statics.StaticNumbers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class SoChainHTTPClient implements BalanceProvider, AddressTransactionsProvider {
	private static final String prefix="https://chain.so/api/v2";
	
	private WebResource balance;
	private WebResource geobit;
	
	public SoChainHTTPClient() {
		super();
		
		Client client;
		Client clientNoTimeout;
		
		client= Client.create();
		clientNoTimeout= Client.create();
		
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(StaticNumbers.READ_TIMEOUT);

		balance     = client.resource(prefix + "/get_address_balance/BTC/" );
		geobit      = client.resource(prefix +  "/address/BTC/" );
		
		}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public AddressTransactions getAddressTransactions(String address) {
		return null;
	}
//		System.out.println("SoChainResponse getGeobit(" + address +")");
//		try {
//			SoChainResponse result = geobit
//					.path(address)
//					.accept( MediaType.APPLICATION_JSON)
//					.header("User-Agent", StaticStrings.USER_AGENT)
//					.get(SoChainResponse.class);
//			System.out.println("SoChainResponse=" + result);
//			if(result!=null && result.getData()!=null && "success".equals(result.getStatus())) {
//				SoChainData data=result.getData();
//				Geobit g=new Geobit();
//				g.setAddress(address);
//				g.setBalance(longBalance(data.getBalance() ) );
//				List<TransactionAPI> taken=new LinkedList<TransactionAPI>();
//				List<TransactionAPI> unspent=new LinkedList<TransactionAPI>();
//				Map<String,TransactionAPI> mappa=new HashMap<String, TransactionAPI>();
//				Map<String,String> mappaFrom=new HashMap<String, String>();
//				
//				for ( SoChainTransaction soChaintrans : data.getTxs() ) {
//					TransactionAPI t = soChaintrans.getTransaction(address);
//					
//					System.out.println("processed SoChain transaction=" + t + " temp=" + t.getTemp());
//					mappa.put(t.getHash(), t);
//					if(t.getTemp()!=null)
//						mappaFrom.put(t.getTemp() , t.getFrom() );
//							
//					if( t.getFrom()!=null && t.getTemp()==null) {
//						if(t.getValue()>StaticNumbers.BTC_NETWORK_FEE)
//							unspent.add(t);
//					}
//					else if(t.getFrom()==null && t.getTemp()==null ) {
////						Transaction from=mappa.get(t.getTemp());
////						t.setValue(from.getValue());
//						taken.add(t);
//					}
//				}
//				
//				for(TransactionAPI cur : taken) {
//					if(cur.getFrom()==null) {
//						String from=mappaFrom.get(cur.getHash() );
//						if(from!=null)
//							cur.setFrom(from);
//					}
//
//				}
//				
//				for(TransactionAPI cur : unspent) {
//					if(cur.getFrom()==null) {
//						String from=mappaFrom.get(cur.getHash() );
//						if(from!=null)
//							cur.setFrom(from);
//					}
//				}
//				
//				
//				g.setUnspent(unspent);
//				g.setTaken(taken);
//				
//				return g;
//			}
//		} catch (Exception e) {
//			String mes = "exception on sochain getGeobit " + e.getMessage();
//			System.out.println(mes);
//		}
//		return null;
//	}

	@Override
	public Long getBalance(String address) {
		try {
			String bilancio=balance
					.path(address)
					.get(String.class);
//			System.out.println(bilancio);
			JSONObject obj = new JSONObject(bilancio);
			String val=obj.getJSONObject("data").getString("confirmed_balance");
			return longBalance(val);
		}
		catch (Exception e) {
			String mes = "exception on sochain getBalance " + e.getMessage();
//			Memory.getInstance().log(mes );
			System.out.println(mes);
			
		}
		return null;
	}

	private long longBalance(String val) {
		return Long.parseLong( val.replace(".", "") );
	}

	@Override
	public String toString() {
		return getPrefix();
	}
	
	

}
