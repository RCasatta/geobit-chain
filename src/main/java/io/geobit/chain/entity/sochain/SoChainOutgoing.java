package io.geobit.chain.entity.sochain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoChainOutgoing {
	private String value;
	private List<SoChainOutput> outputs;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<SoChainOutput> getOutputs() {
		return outputs;
	}
	public void setOutputs(List<SoChainOutput> outputs) {
		this.outputs = outputs;
	}
	@Override
	public String toString() {
		return "SoChainOutgoing [value=" + value + ", outputs=" + outputs + "]";
	}
	
	
}
