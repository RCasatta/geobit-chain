/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 geobit.io 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
