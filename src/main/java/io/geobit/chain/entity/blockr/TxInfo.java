package io.geobit.chain.entity.blockr;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TxInfo {
	private String status;
	private TxInfoData data;
	private Integer code;
	private String message;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public TxInfoData getData() {
		return data;
	}
	public void setData(TxInfoData data) {
		this.data = data;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "TxInfo [status=" + status + ", data=" + data + ", code=" + code
				+ ", message=" + message + "]";
	}
	
	

}
