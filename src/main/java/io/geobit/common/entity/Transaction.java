package io.geobit.common.entity;


import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {
	private String  hash;       /* hash */
	private Long    timestamp;  /* unix timestamp UTC of the block when the transaction is first confirmed */
	private Integer blockHeight;
	private String  blockHash;
	private List<TransactionInOut> in;
	private List<TransactionInOut> out;

	public Transaction() {
		super();
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getBlockHeight() {
		return blockHeight;
	}

	public void setBlockHeight(Integer blockHeight) {
		this.blockHeight = blockHeight;
	}

	public String getBlockHash() {
		return blockHash;
	}

	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}

	public List<TransactionInOut> getIn() {
		return in;
	}

	public void setIn(List<TransactionInOut> in) {
		this.in = in;
	}

	public List<TransactionInOut> getOut() {
		return out;
	}

	public void setOut(List<TransactionInOut> out) {
		this.out = out;
	}
	
	

}
