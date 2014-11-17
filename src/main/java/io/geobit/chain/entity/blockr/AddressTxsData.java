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
