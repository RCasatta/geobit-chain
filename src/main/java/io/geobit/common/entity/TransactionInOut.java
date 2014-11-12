package io.geobit.common.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransactionInOut {
	private String  address;
	private Long    value;
	private Boolean spent;
	private Integer index;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public Boolean isSpent() {
		return spent;
	}
	public void setSpent(Boolean spent) {
		this.spent = spent;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "TransactionOut [address=" + address + ", value=" + value
				+ ", spent=" + spent + ", index=" + index + "]";
	}
	
	

}
