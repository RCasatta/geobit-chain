package io.geobit.chain.entity.sochain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoChainOutput {
	private Integer output_no;
	private String address;
	private String value;
	private SoChainTxIdNo spent;
	public Integer getOutput_no() {
		return output_no;
	}
	public void setOutput_no(Integer output_no) {
		this.output_no = output_no;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public SoChainTxIdNo getSpent() {
		return spent;
	}
	public void setSpent(SoChainTxIdNo spent) {
		this.spent = spent;
	}
	@Override
	public String toString() {
		return "SoChainOutput [output_no=" + output_no + ", address=" + address
				+ ", value=" + value + ", spent=" + spent + "]";
	}
	public Long getValueLong() {
		
		return Long.parseLong( getValue().replace(".", "") );
		
	}
	
	
}
