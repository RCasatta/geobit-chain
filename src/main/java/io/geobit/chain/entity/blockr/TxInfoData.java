package io.geobit.chain.entity.blockr;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TxInfoData {
	private String tx;
	private Integer block;
	private Integer confirmations;
	private String time_utc;
	private Integer is_coinbased;
	private List<V> vouts;
	private List<V> vins;
	public String getTx() {
		return tx;
	}
	public void setTx(String tx) {
		this.tx = tx;
	}
	public Integer getBlock() {
		return block;
	}
	public void setBlock(Integer block) {
		this.block = block;
	}
	public Integer getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}
	public String getTime_utc() {
		return time_utc;
	}
	public void setTime_utc(String time_utc) {
		this.time_utc = time_utc;
	}
	public Integer getIs_coinbased() {
		return is_coinbased;
	}
	public void setIs_coinbased(Integer is_coinbased) {
		this.is_coinbased = is_coinbased;
	}

	public List<V> getVouts() {
		return vouts;
	}
	public void setVouts(List<V> vouts) {
		this.vouts = vouts;
	}
	public List<V> getVins() {
		return vins;
	}
	public void setVins(List<V> vins) {
		this.vins = vins;
	}
	@Override
	public String toString() {
		return "TxInfoData [tx=" + tx + ", block=" + block + ", confirmations="
				+ confirmations + ", time_utc=" + time_utc + ", is_coinbased="
				+ is_coinbased + ", vouts=" + vouts + ", vins=" + vins + "]";
	}
	
	
	

}
