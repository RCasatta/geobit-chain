package io.geobit.chain.clients;

import static io.geobit.statics.Log.log;
import io.geobit.chain.entity.blockr.BlockrBalance;
import io.geobit.chain.entity.blockr.BlockrBalanceData;
import io.geobit.chain.providers.BalanceProvider;
import io.geobit.chain.providers.BlockProvider;
import io.geobit.chain.providers.PushTxProvider;
import io.geobit.chain.providers.ReceivedProvider;
import io.geobit.chain.providers.TransHexProvider;
import io.geobit.chain.providers.TransactionProvider;
import io.geobit.common.entity.Block;
import io.geobit.common.entity.Transaction;
import io.geobit.statics.StaticNumbers;
import io.geobit.statics.SimpleDateFormats;
import io.geobit.statics.StaticStrings;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONObject;

import com.google.common.collect.EvictingQueue;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class BlockrHTTPClient implements BalanceProvider, ReceivedProvider, TransactionProvider, PushTxProvider, TransHexProvider, BlockProvider {
	private static final String prefix= "http://btc.blockr.io/api/v1";

	private WebResource balance;
	private WebResource received;
	private WebResource transaction;
	private WebResource pushTx;
	//	private WebResource addressTxs;
	private WebResource transHex;
	private WebResource lastBlock;
	private WebResource blockNumber;
	private WebResource blockTxs;

	private EvictingQueue<Integer> doneBlocks=EvictingQueue.create(10);

	public BlockrHTTPClient() {
		super();

		Client client;
		Client clientNoTimeout;
		client= Client.create();
		clientNoTimeout= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(   StaticNumbers.READ_TIMEOUT);

		balance     = client.resource(prefix + "/address/balance/" );
		received    = client.resource(prefix + "/address/info/" );
		transaction = client.resource(prefix + "/tx/info/" );
		pushTx      = client.resource(prefix + "/tx/push/" );
		//		addressTxs  = client.resource(prefix + "/address/txs/");
		transHex    = client.resource(prefix + "/tx/raw/");
		lastBlock   = client.resource(prefix + "/block/info/last");
		blockNumber = client.resource(prefix + "/block/info/");
		blockTxs    = clientNoTimeout.resource(prefix + "/block/txs/"); 
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public Long getBalance(String address) {
		BlockrBalance result = balance
				.path(address)
				.accept( MediaType.APPLICATION_JSON)
				.header("User-Agent", StaticStrings.USER_AGENT)
				.get(BlockrBalance.class);
		//		System.out.println("result=" + result);
		try {
			if(result!=null && result.getCode()==200) {
				BlockrBalanceData balData= result.getData();
				if(balData!=null) {	
					long bal = Math.round(balData.getBalance()*100000000L);
					//				System.out.println( balance.getURI() + " returns " + bal);
					return bal;
				}
			}
		} catch (Exception e) {
			String mes = "exception on blockr getBalance " + e.getMessage();
			log(mes );
			
		}
		return null;
	}

	@Override
	public Long getReceived(String address) {
		BlockrBalance result = received
				.path(address)
				.accept( MediaType.APPLICATION_JSON)
				.header("User-Agent", StaticStrings.USER_AGENT)
				.get(BlockrBalance.class);
		//		System.out.println("result=" + result);
		try {
			if(result!=null && result.getCode()==200) {
				BlockrBalanceData balData= result.getData();
				if(balData!=null) {	
					long bal = Math.round(balData.getTotalreceived()*100000000L);
					//				System.out.println( balance.getURI() + " returns " + bal);
					return bal;
				}
			}
		} catch (Exception e) {
			String mes = "exception on blockr getReceived " + e.getMessage();
			log(mes );
		}
		return null;
	}

	@Override
	public Transaction getTransaction(String txhash) {
		return null;
	}
//		System.out.println("blockr getTransaction " + txhash);
//		TxInfo tx=transaction
//				.path(txhash)
//				.get(TxInfo.class);
//		System.out.println("tx=" + tx);
//
//		if(tx!=null && tx.getData() !=null 
//				&& tx.getData().getVouts()!=null 
//				&& tx.getData().getVins()!=null 
//				&& tx.getData().getVins().get(0)!=null
//				) {
//			Transaction t =new Transaction();
//			TxInfoData data=tx.getData();
//			List<V> vout=tx.getData().getVouts();
//			int index=0;
//			
//			List<TransactionInOut> outs = new LinkedList<TransactionInOut>();
//			for(V current : vout) {
//				long round = Math.round(current.getAmount()*Numbers.SATOSHIS);
//	
//				TransactionInOut toOut=new TransactionInOut();
//				toOut.setAddress( current.getAddress() );
//				toOut.setValue(   round );
//				toOut.setIndex(   current.getN() );
//				toOut.setSpent(   current.getIs_spent()==1 );
//				outs.add(toOut);
//				index++;
//			}
//			t.setOut(outs);
//			List<TransactionInOut> ins = new LinkedList<TransactionInOut>();
//			List<V> vin=tx.getData().getVins();
////			t.setFrom( vin.get(0).getAddress() );
//			try {
//				System.out.println(data.getTime_utc());
//				Date dd=SimpleDateFormats.m_ISO8601Local.parse(data.getTime_utc());
//
//				long timestamp = (dd.getTime()/1000L)+7200;  /* 2 hour different from blockchain */
//				String result=SimpleDateFormats.m_ISO8601Local.format(dd);
//				System.out.println(result);
//				t.setTimestamp(timestamp);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			t.setHash(data.getTx());
//			t.setBlockHeight(data.getBlock());
//
//			GeobitAWSClient awsClient = GeobitAWSClient.getInstance();
//			Map<String, String> m=awsClient.getAirdropMap( t.getFrom() );
//			if(m!=null ) {
//				String message=m.get(StaticStrings.ADDR_MESSAGE);
//				if(message!=null)
//					t.setMessage(message);
//				String alias=m.get(StaticStrings.ADDR_ALIAS);
//				if(alias!=null && alias.length()>0)
//					t.setFromAlias(alias);
//				String password=m.get(StaticStrings.ADDR_PSWD);
//				if(password!=null && password.length()>0)
//					t.setPasswordHash(password);
//			}
//
//			return t;
//		}
//
//		return null;
//	}
//


	@Override
	public Boolean pushTx(String hex) {
		try {
			System.out.println("Blockr pushTx " + hex);
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("hex", hex);
			ClientResponse response = pushTx
					.header("User-Agent", StaticStrings.USER_AGENT)
					.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					.post(ClientResponse.class, formData);

			String ret=response.getEntity(String.class);
			System.out.println("pushing transaction returned " + ret);
			return response.getStatus()==200;
		} catch (Exception e) {
			System.out.println("Blockr PushTx error " + e.getMessage());
		}
		return null;

	}


	//	public Geobit getGeobitNotWorking(String address) {
	//		try {
	//			AddressTxs trans=addressTxs
	//					.path(address)
	//					.accept( MediaType.APPLICATION_JSON)
	//					.header("User-Agent", Strings.USER_AGENT)
	//					.get(AddressTxs.class);
	//
	//			if(trans!=null && trans.getData()!=null) {
	//				System.out.println("trans=" + trans);
	//				Geobit geobit=new Geobit();
	//				List<Transaction> unspent=new LinkedList<Transaction>();
	//				List<Transaction> taken=new LinkedList<Transaction>();
	//				AddressTxsData data= trans.getData();
	//				if(data.getTxs()!=null) {
	//					for(Txs current : data.getTxs() ) {
	//						String hash=current.getTx();
	//						System.out.println("getTransaction " + hash);
	//						Transaction tt=getTransaction(address, hash);
	//						unspent.add(tt);
	//
	//					}
	//				}
	//				geobit.setUnspent(unspent);
	//				geobit.setBalance(getBalance(address));
	//			}
	//
	//
	//
	//		}  catch (Exception e) {
	//			System.out.println("Blockr getGeobit error " + e.getMessage());
	//		}
	//		return null;
	//	}

	@Override
	public String getTransHex(String txhash) {

		//		System.out.println(result);
		String returned=null;
		try {
			String result=transHex
					.path(txhash)
					.queryParam("format", "json")
					.accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);

			JSONObject obj = new JSONObject(result);
			returned=obj.getJSONObject("data").getJSONObject("tx").getString("hex");
			return returned;
		} catch (Exception e) {
			System.out.println("Blockr getTransactionHex error " + e.getMessage());
		}
		return returned;
	}
	@Override
	public String toString() {
		return getPrefix();
	}


	@Override
	public Block getBlock(Integer height) {
		try {
			String rawBlock=blockNumber
					.path(height.toString())
					.accept(MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);
			JSONObject obj      = new JSONObject(rawBlock);
			JSONObject jsonData = obj.getJSONObject("data");
			String hash     = jsonData.getString("hash");
			String nb       = jsonData.getString("nb");
			String time_utc = jsonData.getString("time_utc");
			Block b = new Block();
			b.setHash(hash);
			b.setHeight( Integer.parseInt( nb) ) ;
			long l=SimpleDateFormats.m_ISO8601Local.parse(time_utc).getTime();
			
			b.setTime( (l/1000)+7200 );
			return b;
			
		} catch (Exception e) {
			System.out.println("error on Blockr getBlock() " + e.getMessage() );
		}

		return null;
	}
}
