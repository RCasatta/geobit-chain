package io.geobit.common.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddressTransactions implements Serializable  {
	private static final long serialVersionUID = 1L;
	private String address;
	private Long   balance;
	private List<Transaction> transactions;
	
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
	
	public void setAvailable(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
}
