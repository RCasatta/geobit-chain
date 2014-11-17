/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 geobit.io 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
	public Long getBalanceLong() {
		return Long.parseLong( balance.replace(".", "") );
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
