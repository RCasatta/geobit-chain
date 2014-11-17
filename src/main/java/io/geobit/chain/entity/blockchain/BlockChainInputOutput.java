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

package io.geobit.chain.entity.blockchain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BlockChainInputOutput {
	private BlockChainInputOutput prev_out;
	private Integer n;
	private Long value;
	private String addr;
	private Boolean spent;   /* true means available to spend */
	private String tx_index;
	
	public BlockChainInputOutput() {
		super();
	}

	
	
	public BlockChainInputOutput getPrev_out() {
		return prev_out;
	}



	public void setPrev_out(BlockChainInputOutput prev_out) {
		this.prev_out = prev_out;
	}



	public Integer getN() {
		return n;
	}



	public void setN(Integer n) {
		this.n = n;
	}



	public Long getValue() {
		return value;
	}



	public void setValue(Long value) {
		this.value = value;
	}



	public String getAddr() {
		return addr;
	}



	public void setAddr(String addr) {
		this.addr = addr;
	}



	public Boolean getSpent() {
		return spent;
	}



	public void setSpent(Boolean spent) {
		this.spent = spent;
	}



	public String getTx_index() {
		return tx_index;
	}



	public void setTx_index(String tx_index) {
		this.tx_index = tx_index;
	}



	@Override
	public String toString() {
		return "BlockChainInputOutput [prev_out=" + prev_out + ", n=" + n
				+ ", value=" + value + ", addr=" + addr + ", spent=" + spent
				+ ", tx_index=" + tx_index + "]";
	}



	

}
