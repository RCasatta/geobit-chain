package io.geobit.chain.entity.blockchain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BlockChainInputOutput {
	private BlockChainInputOutput prev_out;
	private Integer n;
	private Long value;
	private String addr;
	private Boolean spent;   /* true means available to spend */
	private String tx_index;
	
	public BlockChainInputOutput() {
		super();
	}

	
	
	public BlockChainInputOutput getPrev_out() {
		return prev_out;
	}



	public void setPrev_out(BlockChainInputOutput prev_out) {
		this.prev_out = prev_out;
	}



	public Integer getN() {
		return n;
	}



	public void setN(Integer n) {
		this.n = n;
	}



	public Long getValue() {
		return value;
	}



	public void setValue(Long value) {
		this.value = value;
	}



	public String getAddr() {
		return addr;
	}



	public void setAddr(String addr) {
		this.addr = addr;
	}



	public Boolean getSpent() {
		return spent;
	}



	public void setSpent(Boolean spent) {
		this.spent = spent;
	}



	public String getTx_index() {
		return tx_index;
	}



	public void setTx_index(String tx_index) {
		this.tx_index = tx_index;
	}



	@Override
	public String toString() {
		return "BlockChainInputOutput [prev_out=" + prev_out + ", n=" + n
				+ ", value=" + value + ", addr=" + addr + ", spent=" + spent
				+ ", tx_index=" + tx_index + "]";
	}



	

}
