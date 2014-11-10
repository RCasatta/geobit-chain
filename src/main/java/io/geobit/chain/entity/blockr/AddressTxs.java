package io.geobit.chain.entity.blockr;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddressTxs {
	private String status;
	private AddressTxsData data;
	private Integer code;
	private String message;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public AddressTxsData getData() {
		return data;
	}
	public void setData(AddressTxsData data) {
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
		return "AddressTxs [status=" + status + ", data=" + data + ", code="
				+ code + ", message=" + message + "]";
	}

}
