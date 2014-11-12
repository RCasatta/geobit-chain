package io.geobit.chain.clients;

import static io.geobit.common.statics.Log.log;
import io.geobit.chain.entity.blockchain.BlockChainTransaction;
import io.geobit.chain.providers.AddressTransactionsProvider;
import io.geobit.chain.providers.BlockProvider;
import io.geobit.chain.providers.PushTxProvider;
import io.geobit.chain.providers.TransHexProvider;
import io.geobit.chain.providers.TransactionProvider;
import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.chain.providers.received.ReceivedProvider;
import io.geobit.common.entity.AddressTransactions;
import io.geobit.common.entity.Block;
import io.geobit.common.entity.Transaction;
import io.geobit.common.statics.StaticNumbers;
import io.geobit.common.statics.StaticStrings;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class BlockChainHTTPClient implements BalanceProvider, AddressTransactionsProvider, 
PushTxProvider, TransactionProvider, ReceivedProvider, BlockProvider,
TransHexProvider {
	//	private static final String dns="https://blockchain.info";
	private static final String prefix= "http://blockchain.info";

	private WebResource balance;
	private WebResource received;
	private WebResource addrTxs;
	private WebResource pushTx;
	private WebResource transaction;
	private WebResource block;

	public BlockChainHTTPClient() {
		super();

		Client client;

		client= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(StaticNumbers.READ_TIMEOUT);

		balance     = client.resource(prefix + "/q/addressbalance/" );
		received    = client.resource(prefix + "/q/getreceivedbyaddress/" );
		addrTxs     = client.resource(prefix + "/address/" );
		pushTx      = client.resource(prefix + "/pushtx" );
		transaction = client.resource(prefix + "/rawtx/" );
		block       = client.resource(prefix + "/block-height/");
		//transaction.path(path);
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public Transaction getTransaction(String txHash) {

		try {
			
			BlockChainTransaction add=transaction
					.path(txHash)
					.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
					.accept( MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(BlockChainTransaction.class);

			Transaction t=add.getTransaction();

			return t;
		} catch (Exception e) {
			System.out.println("BlockChain getTransaction error " + e.getMessage());

		}
		return null;
		//		Transaction t=new Transaction();
		//		BlockChainProcTrans procTrans= add.getBlockChainProcTrans(myAddr);
		//
		//		t.setHash(      procTrans.getHash()    );
		//		t.setIndex(     procTrans.getIndex()   );
		//		//		t.setTxIndex(   procTrans.getTxIndex()  );
		//		t.setFrom(      procTrans.getAddress() );
		//		t.setValue(     procTrans.getValue()   );
		//		//		t.setTimestamp( procTrans.getTimestamp() );
		//		t.setBlockHeight( procTrans.getBlockHeight() );
		//		t.setSpent(procTrans.getSpent());
		//
		//		Integer height=procTrans.getBlockHeight();
		//		if(height!=null) {  /* it's confirmed, getting time */
		//			Block b=getBlock(height);
		//			t.setTimestamp(b.getTime());
		//		}
		//
		//		GeobitAWSClient awsClient = GeobitAWSClient.getInstance();
		//		Map<String, String> m=awsClient.getSenderAddressMap( t.getFrom() );
		//		if(m!=null ) {
		//			String message=m.get(Strings.ADDR_MESSAGE);
		//			if(message!=null)
		//				t.setMessage(message);
		//			String alias=m.get(Strings.ADDR_ALIAS);
		//			if(alias!=null && alias.length()>0)
		//				t.setFromAlias(alias);
		//			String password=m.get(Strings.ADDR_PSWD);
		//			if(password!=null && password.length()>0)
		//				t.setPasswordHash(password);
		//		}


	}

	@Override
	public Boolean pushTx(String hex) {
		try {
			System.out.println("Blockchain pushTx " + hex);
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("tx", hex);
			ClientResponse response = pushTx
					.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
					.header("User-Agent", StaticStrings.USER_AGENT)
					.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					.post(ClientResponse.class, formData);

			String ret=response.getEntity(String.class);
			System.out.println("pushing transaction returned " + ret);
			return response.getStatus()==200;
		} catch (Exception e) {
			System.out.println("Blockchain PushTx error " + e.getMessage());
		}
		return null;
	}
	
		@Override
		public AddressTransactions getAddressTransactions(String address) {
			return null;
		}
	//		System.out.println("Blockchain getGeobit(" + address +")");
	//
	//		try {
	//			AddressTransactions addressTxs=new AddressTransactions();
	//
	//			BlockChainAddress add=this.addrTxs
	//					.path(address)
	//					.queryParam("format", "json")
	//					.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
	//					.accept(MediaType.APPLICATION_JSON)
	//					.header("User-Agent", StaticStrings.USER_AGENT)
	//					.get(BlockChainAddress.class);
	//			System.out.println("BlockChainAddress=" + add);
	//
	//			addressTxs.setAddress(add.getAddress());
	//			addressTxs.setBalance(add.getFinal_balance());
	//
	//			List<BlockChainProcTrans> unspent=new ArrayList<BlockChainProcTrans>();
	//			List<BlockChainProcTrans> taken=new ArrayList<BlockChainProcTrans>();
	//
	//			System.out.println("-----------------------------");
	//			Map<String, String> hashPrevOut=new HashMap<String, String>();
	//
	//			if(add.getTxs()!=null) {
	//				System.out.println("for on transaction starting total txs=" + add.getTxs().size());
	//				int indice=0;
	//
	//				for(BlockChainTransaction bt : add.getTxs()) {
	//					/*if(bt.getBlock_height()!=null) {  */          /* without is unconfirmed*/
	//					BlockChainProcTrans procTrans = bt.getBlockChainProcTrans(add.getAddress() ) ;
	//					//					System.out.println("current procTrans=" +procTrans);
	//
	//					char type=procTrans.getType();
	//					if( type=='D' && procTrans.getSpent()==false ) {  /* it's a deposit and is available */
	//						//						System.out.println(indice + "-----------------> it's a deposit and is available");
	//						unspent.add(procTrans);
	//					} else if( type=='W') {  /* it's a withdraw*/
	//						//						System.out.println(indice + "-------------------> it's a withdraw");
	//						taken.add(procTrans);
	//					} else {  /* it's a taken deposit */
	//						//						System.out.println(indice + "--------------------------> it's a taken deposit!");
	//						//						System.out.println("saving id=" + procTrans.getPrevOutId() + " " + procTrans.getPrevOutAddress() );
	//						hashPrevOut.put(procTrans.getTxIndex(), procTrans.getPrevOutAddress() );
	//					}
	//					/*} else {
	//						System.out.println(indice + " -------------------> skipping this, not confirmed");
	//					}*/
	//					indice++;
	//				}
	//			}
	//
	//			List<Transaction> listaTaken=new ArrayList<Transaction>();
	//			
	//			HTTPDispatcher dispatcher = HTTPDispatcher.getInstance();
	//			int maxTaken=5;
	//			for(BlockChainProcTrans current : taken) {
	//				if(maxTaken--<=0)
	//					break;
	//				Transaction t=new Transaction();
	//				t.setHash(    current.getHash()    );
	//				t.setTo(      current.getAddress() );
	//				t.setValue(  -current.getValue()   );
	//				t.setToList(current.getToList());
	////				if(t.getValue()<Numbers.BTC_NETWORK_FEE)
	////					continue;
	//				//				t.setTxIndex( current.getTxIndex() );
	//				//				t.setTimestamp(current.getTimestamp());
	//				Integer height=current.getBlockHeight();
	//				if(height!=null) {  /* it's confirmed, getting time */
	//
	//					t.setBlockHeight(height);
	//					Block b=dispatcher.getBlock(height);    
	//					t.setTimestamp(b.getTime());
	//				}
	//				t.setSpent(true);
	//
	//				System.out.println("current taken=" + current);
	//				System.out.println("current.getPrevOutId()=" + current.getPrevOutId());
	//				String fromStartingAddress=hashPrevOut.get(current.getPrevOutId() );
	//				t.setFrom(fromStartingAddress);
	//
	//				if(t.getFrom()!=null && t.getFrom().length()>10) {
	//					Map<String, String> m=awsClient.getAirdropMap( t.getFrom() );
	//					if(m!=null ) {
	//						String message=m.get(StaticStrings.ADDR_MESSAGE);
	//						if(message!=null)
	//							t.setMessage(message);
	//						String alias=m.get(StaticStrings.ADDR_ALIAS);
	//						if(alias!=null && alias.length()>0)
	//							t.setFromAlias(alias);
	//					}
	//				}
	//
	//				if(t.getTo()!=null && t.getTo().length()>10) {
	//					Map<String, String> m=awsClient.getAirdropMap( t.getTo());
	//					if(m!=null ) {
	//						String alias=m.get(StaticStrings.ADDR_ALIAS);
	//						if(alias!=null && alias.length()>0)
	//							t.setToAlias(alias);
	//					}
	//				}
	//
	//
	//				listaTaken.add(t);
	//			}
	//			addressTxs.setTaken(listaTaken);
	//
	//			System.out.println("lista unspent");
	//			List<Transaction> listaUnspent=new ArrayList<Transaction>();
	//			for(BlockChainProcTrans current : unspent) {
	//				System.out.println("current=" + current);
	//				if(current.getValue()>StaticNumbers.BTC_NETWORK_FEE) {
	//					Transaction t=new Transaction();
	//					t.setIndex(   current.getIndex()   );
	//					t.setHash(    current.getHash()    );
	//					t.setFrom(    current.getAddress() );
	//					t.setValue(   current.getValue()   );
	//					t.setN(current.getN() );
	//					//					t.setTxIndex( current.getTxIndex() );
	//					Integer height=current.getBlockHeight();
	//					if(height!=null) {  /* it's confirmed, getting time */
	//						t.setBlockHeight(height);
	//						Block b=dispatcher.getBlock(height);
	//						t.setTimestamp(b.getTime());
	//					}
	//					t.setSpent(false);
	////					System.out.println("heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
	//					Map<String, String> m=awsClient.getAirdropMap( t.getFrom() );
	//					if(m!=null ) {
	//						String message=m.get(StaticStrings.ADDR_MESSAGE);
	//						if(message!=null)
	//							t.setMessage(message);
	//						String alias=m.get(StaticStrings.ADDR_ALIAS);
	//						if(alias!=null && alias.length()>0)
	//							t.setFromAlias(alias);
	//						String password=m.get(StaticStrings.ADDR_PSWD);
	//						if(password!=null && password.length()>0)
	//							t.setPasswordHash(password);
	//					}
	//
	//					listaUnspent.add(t);
	//				}
	//			}
	//			System.out.println("finalizing " + addressTxs + " " + listaUnspent);
	//			addressTxs.setUnspent(listaUnspent);
	//			System.out.println("finalized");
	//			return addressTxs;
	//
	//		} catch (Exception e) {
	//			System.out.println("Blockchain getGeobit error " + e.getMessage() + " address=" +address);
	//
	//		}
	//
	//		return null;
	//	}

	@Override
	public Long getBalance(String address) {

		Long resultLong=null;
		try {
			String result = balance
					.path(address)
					.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
					.accept(MediaType.TEXT_PLAIN)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);

			resultLong = Long.parseLong(result);
		} catch(Exception e) {

			String mes = "exception on blockchain getBalance " + e.getMessage();
			System.out.println(mes);
			log(mes );

		}
		return resultLong;
	}

	//		System.out.println( balance.getURI() + " returns " + resultLong);




	@Override
	public Long getReceived(String address) {
		String result = received
				.path(address)
				.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
				.accept(MediaType.TEXT_PLAIN)
				.header("User-Agent", StaticStrings.USER_AGENT)
				.get(String.class);
		Long resultLong=null;
		try {
			resultLong = Long.parseLong(result);
		} catch(Exception e) {}
		return resultLong;
	}

	public Block getBlock(Integer height) {
		//		System.out.println("getBlock(" + height + ")");

		//		String path = height.toString() + "?format=json";
		//		System.out.println("path=" + path);
		//		System.out.println("uri="+ block.getURI() );
		String result=block
				.path(height.toString())
				.queryParam("format", "json")
				.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
				.accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
				.header("User-Agent", StaticStrings.USER_AGENT)
				.get(String.class);
		//		System.out.println(result);
		Block b=null;
		try {
			JSONObject obj = new JSONObject(result);
			JSONArray arr  = obj.getJSONArray("blocks");
			JSONObject jsonBlock = (JSONObject) arr.get(0);
			Long time   = jsonBlock.getLong("time");
			String hash = jsonBlock.getString("hash");
			b=new Block();
			b.setTime(time);
			b.setHeight(height);
			b.setHash(hash);
			//		List<String> transactions=new LinkedList<String>();
			//		JSONArray arrTx  = jsonBlock.getJSONArray("tx");
			//		for(int i=0;i<arrTx.length();i++) {
			//			JSONObject tx=(JSONObject) arrTx.get(i);
			//			transactions.add(tx.getString("hash"));
			//		}
			//		b.setTransactions(transactions);
		} catch (JSONException e) {
			System.out.println("getBlockException " +  e.getMessage() );
		}


		return b;
	}


	@Override
	public String getTransHex(String txhash) {

		String result=null;
		try {
			result = transaction
					.path(txhash)
					.queryParam("format", "hex")
					.queryParam("api_code", "90ab63e1-3d4a-4d20-a64b-560f845c14b0")
					.accept(MediaType.TEXT_PLAIN)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(String.class);
			return result;
		} catch(Exception e) {}


		return result;
	}
	@Override
	public String toString() {
		return getPrefix();
	}
}
