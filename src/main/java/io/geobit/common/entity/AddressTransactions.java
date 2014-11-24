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

package io.geobit.common.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Joiner;

@XmlRootElement
public class AddressTransactions implements Serializable  {
	private static final long serialVersionUID = 2L;
	private String address;
	private Long   balance;
	private List<Transaction> transactions;
	private boolean onlyUnspent=false;
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Long getBalance() {
		return balance;
	}
	
	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public boolean isOnlyUnspent() {
		return onlyUnspent;
	}

	public void setOnlyUnspent(boolean onlyUnspent) {
		this.onlyUnspent = onlyUnspent;
	}

	@Override
	public String toString() {
		return "AddressTransactions [address=" + address + ", balance="
				+ balance + ", transactions=" + (transactions!=null ? Joiner.on("\n").join(transactions) : "") + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result
				+ ((transactions == null) ? 0 : transactions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressTransactions other = (AddressTransactions) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (transactions == null) {
			if (other.transactions != null)
				return false;
		} else if (!transactions.equals(other.transactions))
			return false;
		return true;
	}
	
	
	
}
