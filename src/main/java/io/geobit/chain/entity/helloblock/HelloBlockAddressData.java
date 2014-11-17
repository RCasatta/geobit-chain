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

package io.geobit.chain.entity.helloblock;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HelloBlockAddressData {
	private Long balance;
	private Long confirmedBalance;
	private Long txsCount;
	private Long confirmedTxsCount;
	private Long totalReceivedValue;
	private Long confirmedReceivedValue;
	private String address;
	private String hash160;
	private String type;
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Long getConfirmedBalance() {
		return confirmedBalance;
	}
	public void setConfirmedBalance(Long confirmedBalance) {
		this.confirmedBalance = confirmedBalance;
	}
	public Long getTxsCount() {
		return txsCount;
	}
	public void setTxsCount(Long txsCount) {
		this.txsCount = txsCount;
	}
	public Long getConfirmedTxsCount() {
		return confirmedTxsCount;
	}
	public void setConfirmedTxsCount(Long confirmedTxsCount) {
		this.confirmedTxsCount = confirmedTxsCount;
	}


	
	
	public Long getTotalReceivedValue() {
		return totalReceivedValue;
	}
	public void setTotalReceivedValue(Long totalReceivedValue) {
		this.totalReceivedValue = totalReceivedValue;
	}
	public Long getConfirmedReceivedValue() {
		return confirmedReceivedValue;
	}
	public void setConfirmedReceivedValue(Long confirmedReceivedValue) {
		this.confirmedReceivedValue = confirmedReceivedValue;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHash160() {
		return hash160;
	}
	public void setHash160(String hash160) {
		this.hash160 = hash160;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "HelloBlockAddressData [balance=" + balance
				+ ", confirmedBalance=" + confirmedBalance + ", txsCount="
				+ txsCount + ", confirmedTxsCount=" + confirmedTxsCount
				+ ", totalReceivedValue=" + totalReceivedValue
				+ ", confirmedReceivedValue=" + confirmedReceivedValue
				+ ", address=" + address + ", hash160=" + hash160 + ", type="
				+ type + "]";
	}

	
	
	
}
