package io.geobit.chain.entity.sochain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoChainInput {
	private Integer input_no;
	private String address;
	private SoChainTxIdNo received_from;
	
	public Integer getInput_no() {
		return input_no;
	}
	public void setInput_no(Integer input_no) {
		this.input_no = input_no;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public SoChainTxIdNo getReceived_from() {
		return received_from;
	}
	public void setReceived_from(SoChainTxIdNo received_from) {
		this.received_from = received_from;
	}
	@Override
	public String toString() {
		return "SoChainInput [input_no=" + input_no + ", address=" + address
				+ ", received_from=" + received_from + "]";
	}
	
}
