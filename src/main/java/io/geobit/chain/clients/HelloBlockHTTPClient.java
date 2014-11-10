package io.geobit.chain.clients;

import static io.geobit.statics.Log.log;
import io.geobit.chain.api.BlockAPI;
import io.geobit.chain.api.TransactionAPI;
import io.geobit.chain.entity.helloblock.HelloBlockBlock;
import io.geobit.chain.entity.helloblock.HelloBlockData;
import io.geobit.chain.entity.helloblock.HelloBlockResponse;
import io.geobit.chain.entity.helloblock.HelloBlockTransaction;
import io.geobit.chain.providers.AddressTransactionsProvider;
import io.geobit.chain.providers.BalanceProvider;
import io.geobit.chain.providers.BlockProvider;
import io.geobit.chain.providers.ReceivedProvider;
import io.geobit.chain.providers.TransactionProvider;
import io.geobit.common.entity.AddressTransactions;
import io.geobit.common.entity.Block;
import io.geobit.common.entity.Transaction;
import io.geobit.statics.StaticNumbers;
import io.geobit.statics.StaticStrings;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class HelloBlockHTTPClient 
implements BalanceProvider , ReceivedProvider, 
TransactionProvider,  BlockProvider, AddressTransactionsProvider {

	private static final String prefix=  "http://mainnet.helloblock.io/v1";

	private WebResource addresses;
	private WebResource transactions;
	private WebResource blocks;
//	private WebResource latestBlocks;

	public HelloBlockHTTPClient() {
		super();

		Client client= Client.create();
		client.setConnectTimeout(StaticNumbers.CONNECT_TIMEOUT);
		client.setReadTimeout(   StaticNumbers.READ_TIMEOUT);

		addresses     = client.resource(prefix + "/addresses/" );
		transactions  = client.resource(prefix + "/transactions/" );
		blocks        = client.resource(prefix + "/blocks/" );
//		latestBlocks  = client.resource(prefix + "/blocks/latest" );
		//transaction.path(path);
	}

	@Override
	public String getPrefix() {
		return prefix;
	}


	@Override
	public Long getBalance(String address) {
		try {
			HelloBlockResponse result = addresses
					.path(address)
					.accept( MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(HelloBlockResponse.class);
			//			System.out.println("result=" + result);
			if(result!=null && "success".equals(result.getStatus())) {
				HelloBlockData balData= result.getData();
				if(balData!=null && balData.getAddress()!=null) {
					Long balance = balData.getAddress().getBalance();
					//					System.out.println( addresses.getURI() + address + " returns " + balance);
					return balance;
				}

			} 
		} catch(Exception e) {
			String mes = "HelloBlock getBalance error " + e.getMessage();
			System.out.println(mes);
			log(mes );
			
		}
		return null;


	}



	@Override
	public Long getReceived(String address) {
		HelloBlockResponse result = addresses
				.path(address)
				.accept( MediaType.APPLICATION_JSON)
				.header("User-Agent", StaticStrings.USER_AGENT)
				.get(HelloBlockResponse.class);
		//		System.out.println("result=" + result);
		if(result!=null && "success".equals(result.getStatus())) {

			HelloBlockData balData= result.getData();
			if(balData!=null && balData.getAddress()!=null)
				return balData.getAddress().getTotalReceivedValue();

		}
		return null;
	}



	@Override
	public Transaction getTransaction( String txhash) {
		HelloBlockResponse result = transactions
				.path(txhash)
				.accept( MediaType.APPLICATION_JSON)
				.header("User-Agent", StaticStrings.USER_AGENT)
				.get(HelloBlockResponse.class);
		//		System.out.println("result=" + result);
		if(result!=null && "success".equals(result.getStatus())) {
			if(result.getData()!=null && result.getData().getTransaction()!=null) {

				HelloBlockTransaction trans=result.getData().getTransaction();
				Transaction t=trans.getTransaction();	
				return t;
			}
		}
		return null;
	}



	@Override
	public AddressTransactions getAddressTransactions(String address) {
		return null;
	}
//		System.out.println("HelloBlock getGeobit(" + address +")");
//		try {
//			HelloBlockResponse result = addresses
//					.path(address + "/transactions")
//					.queryParam("limit", "200")
//					.accept( MediaType.APPLICATION_JSON)
//					.header("User-Agent", StaticStrings.USER_AGENT)
//					.get(HelloBlockResponse.class);
//			System.out.println("HelloBlockResponse=" + result);
//			if(result!=null && result.getData()!=null && "success".equals(result.getStatus())) {
//				Geobit g=new Geobit();
//				g.setAddress(address);
//				g.setBalance(getBalance(address));
//				List<TransactionAPI> taken=new LinkedList<TransactionAPI>();
//				List<TransactionAPI> unspent=new LinkedList<TransactionAPI>();
//				System.out.println(" result.getData().getTransactions().size()=" + result.getData().getTransactions().size());
//				Map<String,TransactionAPI> mappa=new HashMap<String, TransactionAPI>();
//				for( HelloBlockTransaction trans : result.getData().getTransactions() ) {
//					TransactionAPI t=trans.getTransaction(address);
//					
//					System.out.println("processed Hello Block transaction=" + t);
//					mappa.put(t.getHash(), t);
//					if(!t.getSpent() && !address.equals(t.getFrom())) {
//						if(t.getValue()>StaticNumbers.BTC_NETWORK_FEE)
//							unspent.add(t);
//					}
//					else if(!t.getSpent() && address.equals(t.getFrom())) {
//						t.setValue(t.getTempValue());
//						taken.add(t);
//					}
//				}
//				System.out.println("taken size=" + taken.size() + " unspent size=" + unspent.size());
//				GeobitAWSClient awsClient = GeobitAWSClient.getInstance();
//				for(TransactionAPI current : unspent) {
//
//					Map<String, String> m=awsClient.getAirdropMap( current.getFrom() );
//					if(m!=null ) {
//						String message=m.get(StaticStrings.ADDR_MESSAGE);
//						if(message!=null)
//							current.setMessage(message);
//						String alias=m.get(StaticStrings.ADDR_ALIAS);
//						if(alias!=null && alias.length()>0)
//							current.setFromAlias(alias);
//						String password=m.get(StaticStrings.ADDR_PSWD);
//						if(password!=null && password.length()>0)
//							current.setPasswordHash(password);
//					}
//				}
//
//				g.setUnspent(unspent);
//				System.out.println("mappa=" + mappa);
//				for(TransactionAPI current : taken) {
//					System.out.println("prevHash=" + current.getPrevTxHash());
//					TransactionAPI from=mappa.get(current.getPrevTxHash());
//					if(from!=null)
//						current.setFrom(from.getFrom());
//					current.setTo( current.getTemp() );
//					current.setSpent(true);
//
//					if(current.getFrom()!=null && current.getFrom().length()>10) {
//						Map<String, String> m=awsClient.getAirdropMap( current.getFrom() );
//						if(m!=null ) {
//							String message=m.get(StaticStrings.ADDR_MESSAGE);
//							if(message!=null)
//								current.setMessage(message);
//							String alias=m.get(StaticStrings.ADDR_ALIAS);
//							if(alias!=null && alias.length()>0)
//								current.setFromAlias(alias);
//						}
//					}
//
//					if(current.getTo()!=null && current.getTo().length()>10) {
//						Map<String, String> m=awsClient.getAirdropMap( current.getTo());
//						if(m!=null ) {
//							String alias=m.get(StaticStrings.ADDR_ALIAS);
//							if(alias!=null && alias.length()>0)
//								current.setToAlias(alias);
//						}
//					}
//				}
//				g.setTaken(taken.subList(0, Math.min(5,taken.size())));
//				System.out.println("unspent.size()=" + unspent.size());
//				System.out.println("taken.size()=" + taken.size());
//				return g;
//
//			}
//
//		} catch (Exception e) {
//			System.out.println("HelloBlock getGeobit error " + e.getMessage() + " address=" +address);
//		}
//		return null;
//	}



	@Override
	public Block getBlock(Integer height) {
		try {
			HelloBlockResponse resultBase = blocks
					.path(height.toString())

					.accept( MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(HelloBlockResponse.class);

			HelloBlockBlock block=resultBase.getData().getBlock();
			Block b=new Block();
			b.setHash(block.getBlockHash());
			b.setHeight(block.getBlockHeight());
			b.setTime(block.getBlockTime());
			
			HelloBlockResponse result = blocks
					.path(height.toString())
					.path("/transactions")
					.queryParam("limit","10000")
					.accept( MediaType.APPLICATION_JSON)
					.header("User-Agent", StaticStrings.USER_AGENT)
					.get(HelloBlockResponse.class);
			System.out.println("helloblock getblock " +result);

//			List<String> transactions = new ArrayList<String>();
//			for( HelloBlockTransaction curr : result.getData().getTransactions() )
//				transactions.add( curr.getTxHash() );
//			b.setTransactions(transactions);
			return b;

		}  catch (Exception e) {
			System.out.println("HelloBlock getBlock error " + e.getMessage());
		}
		return null;
	}

	@Override
	public String toString() {
		return getPrefix();
	}

//	@Override
//	public void listenNewTransactions(BlockingQueue<String> queue) {
//		while(true) {
//			try {
//				Thread.sleep(300000);
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//			}
//			try {
//				HelloBlockResponse result = latestBlocks
//						.queryParam("limit", "2")
//						.accept( MediaType.APPLICATION_JSON)
//						.header("User-Agent", Strings.USER_AGENT)
//						.get(HelloBlockResponse.class);
////				System.out.println( "listenNewTransactions="+ result.getData().getBlocks() );
//				
//					List<HelloBlockBlock> blocchi=result.getData().getBlocks();
//					for(HelloBlockBlock current : blocchi) {
//						
//						Integer height=current.getBlockHeight();
//						if(!fatti.contains(height)) {
//							System.out.println("never processed block height " + height);
//							HTTPDispatcher disp=HTTPDispatcher.getInstance();
//							Block b=disp.getBlock(height);
//							for(String txhash : b.getTransactions()) {
//								System.out.println("getting transaction " + txhash);
//								String resultString = transactions
//										.path(txhash)
//										.accept( MediaType.APPLICATION_JSON)
//										.header("User-Agent", Strings.USER_AGENT)
//										.get(String.class);
//								Matcher matcher = Patterns.ADDR_REGEX_HELLOBLOCK_PATTERN.matcher(resultString);
////								System.out.println("resultString=" + resultString);
//								while( matcher.find() ) {
//									String addr = matcher.group(1);
////									System.out.println("found addr:" + addr);
//									Set<String> allAddress = Memory.getInstance().getAllAddress();
//									if(allAddress.contains(addr) ) { // it's a Geobit address!!
//										String string = "Hello block found geobit addr:" + addr;
//										System.out.println(string);
//										Memory.getInstance().log( string );
//										queue.add(addr);
//									}
//
//								}
//							}
//							fatti.add(height);
//						}
//					}
//				}catch(Exception e) {}
//		}
//		
//
//	}

//	private EvictingQueue<Integer> fatti=EvictingQueue.create(20);



}
