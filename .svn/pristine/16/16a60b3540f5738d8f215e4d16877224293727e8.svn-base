package io.geobit.chain.entity.blockchain;

import io.geobit.common.entity.Transaction;
import io.geobit.chain.providers.TransactionGetter;
import io.geobit.common.entity.TransactionInOut;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BlockChainTransaction  implements TransactionGetter {
	private Integer result;
	private Integer block_height;
	private Long time;
	private List<BlockChainInputOutput> inputs;
	private List<BlockChainInputOutput> out;
	private String hash;
	private String tx_index;
	
	
	
	public BlockChainTransaction() {
		super();
	}
	
	
	public String getTx_index() {
		return tx_index;
	}


	public void setTx_index(String tx_index) {
		this.tx_index = tx_index;
	}


	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getBlock_height() {
		return block_height;
	}
	public void setBlock_height(Integer block_height) {
		this.block_height = block_height;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public List<BlockChainInputOutput> getInputs() {
		return inputs;
	}
	public void setInputs(List<BlockChainInputOutput> inputs) {
		this.inputs = inputs;
	}
	public List<BlockChainInputOutput> getOut() {
		return out;
	}
	public void setOut(List<BlockChainInputOutput> out) {
		this.out = out;
	}
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}

 
	@Override
	public String toString() {
		return "BlockChainTransaction [result=" + result + ", blockHeight="
				+ block_height + ", time=" + time + ", inputs=" + inputs
				+ ", out=" + out + ", hash=" + hash + "]";
	}
	
	public BlockChainProcTrans getBlockChainProcTrans(String myAddr) {
		BlockChainProcTrans t=new BlockChainProcTrans();
		String fromAddr=null, depAddr=null, withAddr=null;
		Long fromValue=0L, depValue=0L, withValue=0L, changeValue=0L;
		Boolean depSpent=null, withSpent=null;
//		System.out.println("myAddr=" + myAddr);
		
		String idPrevOut=null;
		for(BlockChainInputOutput in : getInputs() ) {
			BlockChainInputOutput prevOut = in.getPrev_out();
			if(prevOut.getValue()>fromValue) {
				fromAddr    = prevOut.getAddr();
				fromValue   = prevOut.getValue();
				idPrevOut= prevOut.getTx_index();
			}
		}
//		System.out.println("fromAddr=" + fromAddr +" fromValue=" + fromValue + " idPrevOut=" + idPrevOut);
		Long curValue=0L;
		int indexOfOutput=-1;
		int i=0;
		int n=0;
		List<String> outsAddr=new LinkedList<String>();
		List<TransactionInOut> outsAddrVal=new LinkedList<TransactionInOut>();
		for(BlockChainInputOutput out : getOut() ) {
			curValue=out.getValue();
			String curAddr=out.getAddr();
			outsAddr.add(curAddr);
			TransactionInOut tout=new TransactionInOut();
			tout.setAddress(curAddr);
			
			tout.setIndex(out.getN());
			tout.setSpent(!out.getSpent());
			tout.setValue(out.getValue());
			outsAddrVal.add(tout);
			
			if( myAddr.equals( curAddr  ) ) {         /* it's a deposit */
//				System.out.println("1 myAddr=" + myAddr + " curAddr=" + curAddr + " curValue=" + curValue);
				depValue=curValue;
				depAddr=fromAddr;
				depSpent=!out.getSpent();
				indexOfOutput=i;
				if(!depSpent)
					n++;

			} else if (fromAddr.equals( curAddr ) ) { /* it's a change */ 
				changeValue=curValue;
//				System.out.println("2 myAddr=" + myAddr + " curAddr=" + curAddr + " curValue=" + curValue);
			} else {                                  /* it's a withdraw */
//				System.out.println("3 myAddr=" + myAddr + " curAddr=" + curAddr + " curValue=" + curValue);

				withValue=-curValue;
				withAddr=curAddr;
				withSpent=!out.getSpent();
			}
			

			
			i++;
		}
//		System.out.println("--------------------------------------------------");
//		System.out.println("fromAddr=" + fromAddr  + " fromValue=" + fromValue + " idPrevOut=" + idPrevOut ); 
//		System.out.println("depAddr=" + depAddr  + " depValue=" + depValue + " depSpent=" + depSpent);
//		System.out.println("withAddr=" + withAddr  + " withValue=" + withValue + " withSpent=" + withSpent );
//		System.out.println("change=" + changeValue );
		t.setToList(outsAddr);
//		t.setToOut(outsAddrVal);
		t.setHash(getHash());
		t.setBlockHeight(getBlock_height());
		t.setPrevOutId(idPrevOut);
		t.setTxIndex(getTx_index());
		t.setSpent(depSpent);
		t.setTimestamp(getTime());
		t.setN(n);
		
		
		if(depAddr!=null) {          /* it's a deposit */	
			System.out.println("it's a deposit");
			t.setType('D');
			t.setAddress(depAddr);
			t.setValue(depValue);
			t.setIndex(indexOfOutput);
			t.setPrevOutAddress(fromAddr);		
		} else if(withAddr!=null) {   /* it's a withdraw */
			System.out.println("it's a withdraw");
			t.setType('W');
			t.setAddress(withAddr);
			t.setValue(withValue);
		}

		return t;
	}


	@Override
	public Transaction getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

}
