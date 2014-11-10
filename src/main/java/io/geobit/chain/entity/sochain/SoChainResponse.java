package io.geobit.chain.entity.sochain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoChainResponse {
	private String status;
	private SoChainData data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public SoChainData getData() {
		return data;
	}
	public void setData(SoChainData data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "SoChainResponse [status=" + status + ", data=" + data + "]";
	}

	
	
	
}
