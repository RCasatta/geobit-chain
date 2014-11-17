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

package io.geobit.chain.entity.helloblock;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HelloBlockData {
	private HelloBlockAddressData       address;
	private List<HelloBlockTransaction> transactions;
	private HelloBlockTransaction       transaction;
	private HelloBlockBlock             block;
	private List<HelloBlockBlock>       blocks;

	public List<HelloBlockBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<HelloBlockBlock> blocks) {
		this.blocks = blocks;
	}

	public HelloBlockAddressData getAddress() {
		return address;
	}

	public void setAddress(HelloBlockAddressData address) {
		this.address = address;
	}

	public List<HelloBlockTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<HelloBlockTransaction> transactions) {
		this.transactions = transactions;
	}

	public HelloBlockTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(HelloBlockTransaction transaction) {
		this.transaction = transaction;
	}

	public HelloBlockBlock getBlock() {
		return block;
	}

	public void setBlock(HelloBlockBlock block) {
		this.block = block;
	}

	@Override
	public String toString() {
		return "HelloBlockData [address=" + address + ", transactions="
				+ transactions + ", transaction=" + transaction + ", block="
				+ block + "]";
	}
	


	
	

}
