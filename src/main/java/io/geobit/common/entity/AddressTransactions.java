package io.geobit.common.entity;

import java.util.List;

public class AddressTransactions {
	private String address;
	private Long   balance;
	private List<Transaction> taken;
	private List<Transaction> available;
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
	public List<Transaction> getTaken() {
		return taken;
	}
	public void setTaken(List<Transaction> taken) {
		this.taken = taken;
	}
	public List<Transaction> getAvailable() {
		return available;
	}
	public void setAvailable(List<Transaction> available) {
		this.available = available;
	}

	
}
