package io.geobit.chain.entity.sochain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoChainTxIdNo {
	private String txid;
	private Integer input_no;
	private Integer output_no;
	public String getTxid() {
		return txid;
	}
	public void setTxid(String txid) {
		this.txid = txid;
	}
	public Integer getInput_no() {
		return input_no;
	}
	public void setInput_no(Integer input_no) {
		this.input_no = input_no;
	}
	public Integer getOutput_no() {
		return output_no;
	}
	public void setOutput_no(Integer output_no) {
		this.output_no = output_no;
	}
	@Override
	public String toString() {
		return "SoChainTxIdNo [txid=" + txid + ", input_no=" + input_no
				+ ", output_no=" + output_no + "]";
	}
	
	

}
