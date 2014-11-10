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
