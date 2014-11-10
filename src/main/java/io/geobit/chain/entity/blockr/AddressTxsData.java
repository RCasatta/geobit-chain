package io.geobit.chain.entity.blockr;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddressTxsData {
	private String address;
	private Integer limit_txs;
	private Integer nb_txs;
	private Integer nb_txs_displayed;
	private List<Txs> txs;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getLimit_txs() {
		return limit_txs;
	}
	public void setLimit_txs(Integer limit_txs) {
		this.limit_txs = limit_txs;
	}
	public Integer getNb_txs() {
		return nb_txs;
	}
	public void setNb_txs(Integer nb_txs) {
		this.nb_txs = nb_txs;
	}
	public Integer getNb_txs_displayed() {
		return nb_txs_displayed;
	}
	public void setNb_txs_displayed(Integer nb_txs_displayed) {
		this.nb_txs_displayed = nb_txs_displayed;
	}
	public List<Txs> getTxs() {
		return txs;
	}
	public void setTxs(List<Txs> txs) {
		this.txs = txs;
	}
	@Override
	public String toString() {
		return "Data [address=" + address + ", limit_txs=" + limit_txs
				+ ", nb_txs=" + nb_txs + ", nb_txs_displayed="
				+ nb_txs_displayed + ", txs=" + txs + "]";
	}

	
}
