package io.geobit.chain.entity.sochain;

import io.geobit.chain.api.BlockAPI;
import io.geobit.chain.providers.TransactionGetter;
import io.geobit.common.entity.Transaction;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoChainTransaction implements TransactionGetter {
	private String txid;
	private Integer block_no;
	private Integer confirmations;
	private Long time;
	private SoChainOutgoing outgoing;
	private SoChainIncoming incoming;
	public String getTxid() {
		return txid;
	}
	public void setTxid(String txid) {
		this.txid = txid;
	}
	public Integer getBlock_no() {
		return block_no;
	}
	public void setBlock_no(Integer block_no) {
		this.block_no = block_no;
	}
	public Integer getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public SoChainOutgoing getOutgoing() {
		return outgoing;
	}
	public void setOutgoing(SoChainOutgoing outgoing) {
		this.outgoing = outgoing;
	}
	public SoChainIncoming getIncoming() {
		return incoming;
	}
	public void setIncoming(SoChainIncoming incoming) {
		this.incoming = incoming;
	}
	@Override
	public String toString() {
		return '\n' + "SoChainTransaction [txid=" + txid + ", block_no=" + block_no
				+ ", confirmations=" + confirmations + ", time=" + time
				+ ", outgoing=" + outgoing + ", incoming=" + incoming + "]";
	}
	@Override
	public Transaction getTransaction() {
		Transaction t = new Transaction() ;
		t.setHash( getTxid() );
		
//		
//		Integer h = getBlock_no();
//		if(h!=null) {
//			t.setBlockHeight(h);	
//			BlockAPI b= HTTPDispatcher.getInstance().getBlock(h);
//			t.setTimestamp(b.getTime());
//		}
//
////		t.setTimestamp(getTime());
//		t.setSpent(true);
//		t.setValue(11111L);
//
//		if( getOutgoing()!=null ) {
//			List<String> toList=new LinkedList<String>();
//			for (SoChainOutput current : getOutgoing().getOutputs() ) {
//				String currentAddr = current.getAddress();
//				t.setTo( currentAddr );
//				t.setValue( current.getValueLong() );
//				toList.add(currentAddr);
//				if( currentAddr.equals(myAddr)  ) {
//					t.setValue(  current.getValueLong()  );
//					t.setSpent( current.getSpent()!=null );
//					
//				}
//
//			}
//
//			t.setToList(toList);
//		}
//		if(getIncoming()!=null && getIncoming().getInputs()!=null) {
//			SoChainInput inp= getIncoming().getInputs().get(0);
//			t.setFrom( inp.getAddress() );
//			t.setIndex(0);
//
//			t.setValue( getIncoming().getValueLong() );
//
//			if(getIncoming().getSpent()!=null)
//				t.setTemp( getIncoming().getSpent().getTxid() );
//			else
//				t.setSpent(false);
//		}

		return t;
	}



}
