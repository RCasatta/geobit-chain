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

package io.geobit.chain.entity.biteasy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BitEasyAddressData {
	private String address;
	private Long totalReceived;
	private Long balance;
	private Long totalSent;
	private String hash160;
	private String status;
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@XmlElement(name="total_received")
	public Long getTotalReceived() {
		return totalReceived;
	}
	public void setTotalReceived(Long totalReceived) {
		this.totalReceived = totalReceived;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	@XmlElement(name="total_sent")
	public Long getTotalSent() {
		return totalSent;
	}
	public void setTotalSent(Long totalSent) {
		this.totalSent = totalSent;
	}
	public String getHash160() {
		return hash160;
	}
	public void setHash160(String hash160) {
		this.hash160 = hash160;
	}
	@Override
	public String toString() {
		return "BitEasyAddressData [address=" + address + ", totalReceived="
				+ totalReceived + ", balance=" + balance + ", totalSent="
				+ totalSent + ", hash160=" + hash160 + "]";
	}
	
	
	
}
