package io.geobit.chain.entity.sochain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoChainData {
	private String network;
	private String address;
	private String balance;
	private String received_value;
	private String pending_value;
	private Long total_txs;
	private List<SoChainTransaction> txs;
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getReceived_value() {
		return received_value;
	}
	public void setReceived_value(String received_value) {
		this.received_value = received_value;
	}
	public String getPending_value() {
		return pending_value;
	}
	public void setPending_value(String pending_value) {
		this.pending_value = pending_value;
	}
	public Long getTotal_txs() {
		return total_txs;
	}
	public void setTotal_txs(Long total_txs) {
		this.total_txs = total_txs;
	}
	public List<SoChainTransaction> getTxs() {
		return txs;
	}
	public void setTxs(List<SoChainTransaction> txs) {
		this.txs = txs;
	}
	@Override
	public String toString() {
		return "SoChainData [network=" + network + ", address=" + address
				+ ", balance=" + balance + ", received_value=" + received_value
				+ ", pending_value=" + pending_value + ", total_txs="
				+ total_txs + ", txs=" + txs + "]";
	}
	
	
}
