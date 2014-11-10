package io.geobit.chain.entity.blockr;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Txs {
	private String tx;
	private String time_utc;
	private Integer confirmations;
	private Double amount;
	public String getTx() {
		return tx;
	}
	public void setTx(String tx) {
		this.tx = tx;
	}
	public String getTime_utc() {
		return time_utc;
	}
	public void setTime_utc(String time_utc) {
		this.time_utc = time_utc;
	}
	public Integer getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Txs [tx=" + tx + ", time_utc=" + time_utc + ", confirmations="
				+ confirmations + ", amount=" + amount + "]";
	}

	
}
