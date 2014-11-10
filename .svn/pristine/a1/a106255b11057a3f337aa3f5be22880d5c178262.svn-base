package io.geobit.chain.entity.helloblock;

import io.geobit.chain.providers.TransactionGetter;
import io.geobit.common.entity.Transaction;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HelloBlockTransaction implements TransactionGetter {
	private String txHash;
	private String blockHash;
	private Integer blockHeight;
	private Long    blockTime;
	private Integer confirmations;
	
	private List<HelloBlockInput>  inputs;
	private List<HelloBlockOutput> outputs;
	public String getTxHash() {
		return txHash;
	}
	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}
	public String getBlockHash() {
		return blockHash;
	}
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	public Integer getBlockHeight() {
		return blockHeight;
	}
	public void setBlockHeight(Integer blockHeight) {
		this.blockHeight = blockHeight;
	}
	public Integer getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}
	public List<HelloBlockInput> getInputs() {
		return inputs;
	}
	public void setInputs(List<HelloBlockInput> inputs) {
		this.inputs = inputs;
	}
	public List<HelloBlockOutput> getOutputs() {
		return outputs;
	}
	public void setOutputs(List<HelloBlockOutput> outputs) {
		this.outputs = outputs;
	}
	public Long getBlockTime() {
		return blockTime;
	}
	public void setBlockTime(Long blockTime) {
		this.blockTime = blockTime;
	}
	@Override
	public String toString() {
		return "HelloBlockTransaction [txHash=" + txHash + ", blockHash="
				+ blockHash + ", blockHeight=" + blockHeight + ", blockTime="
				+ blockTime + ", confirmations=" + confirmations + ", inputs="
				+ inputs + ", outputs=" + outputs + "]";
	}
	@Override
	public Transaction getTransaction() {
		Transaction t = new Transaction();
		System.out.println("processing=" + this );
//		
//		t.setSpent(false);  //TODO check
//		
//		t.setHash(getTxHash());
//		int n=0;
//		int index=0;
//		List<String> toList=new LinkedList<String>();
//		List<TransactionOut> toOuts=new LinkedList<TransactionOut>();
//		for( HelloBlockOutput current : getOutputs() ) {
//			String address = current.getAddress();
//			toList.add(address);
//			if(address.equals(myAddr)) {
//				t.setValue(current.getValue());
//				t.setIndex(index);
//				t.setSpent(current.getSpent());	
//				if(!current.getSpent())
//					n++;
//			} else {
//				t.setTemp(address);
//				t.setTempValue(current.getValue());
//				
//			}
//			
//			TransactionOut toOut = new TransactionOut();
//			toOut.setAddress(address);
//			toOut.setIndex(current.getIndex());
//			toOut.setValue(current.getValue());
//			toOut.setSpent(current.getSpent());
//			
//			index++;
//		}
//		t.setToList(toList);
//		t.setOutList(toOuts);
//		HelloBlockInput helloBlockInput = getInputs().get(0);
//		
//		String address = helloBlockInput.getAddress();
//		t.setFrom( address );
//		t.setPrevTxHash( helloBlockInput.getPrevTxHash() );
//		t.setTimestamp(getBlockTime());
//		t.setBlockHeight(getBlockHeight());
//		t.setN(n);
		return t;
	}

	
	
}
