package io.geobit.chain.entity.sochain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoChainIncoming {
	private Integer output_no;
	private String value;
	private SoChainTxIdNo spent;
	private List<SoChainInput> inputs;
	private Integer req_sigs;
	private String script_asm;
	private String script_hex;
	public Integer getOutput_no() {
		return output_no;
	}
	public void setOutput_no(Integer output_no) {
		this.output_no = output_no;
	}
	public String getValue() {
		return value;
	}
	public Long getValueLong() {
		return Long.parseLong( getValue().replace(".", "") );
		
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
	public List<SoChainInput> getInputs() {
		return inputs;
	}
	public void setInputs(List<SoChainInput> inputs) {
		this.inputs = inputs;
	}
	public Integer getReq_sigs() {
		return req_sigs;
	}
	public void setReq_sigs(Integer req_sigs) {
		this.req_sigs = req_sigs;
	}
	public String getScript_asm() {
		return script_asm;
	}
	public void setScript_asm(String script_asm) {
		this.script_asm = script_asm;
	}
	public String getScript_hex() {
		return script_hex;
	}
	public void setScript_hex(String script_hex) {
		this.script_hex = script_hex;
	}
	@Override
	public String toString() {
		return "SoChainIncoming [output_no=" + output_no + ", value=" + value
				+ ", spent=" + spent + ", inputs=" + inputs + ", req_sigs="
				+ req_sigs + ", script_asm=" + script_asm + ", script_hex="
				+ script_hex + "]";
	}
	
	
	

}
