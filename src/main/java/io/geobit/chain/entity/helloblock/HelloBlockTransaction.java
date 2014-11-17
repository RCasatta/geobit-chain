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

import io.geobit.chain.providers.TransactionGetter;
import io.geobit.common.entity.Transaction;
import io.geobit.common.entity.TransactionInOut;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import static io.geobit.common.statics.Log.*;

@XmlRootElement
public class HelloBlockTransaction implements TransactionGetter {
	private String txHash;
	private String blockHash;
	private Integer blockHeight;
	private Long    blockTime;
	private Integer confirmations;
	
	private List<HelloBlockInput>  inputs;
	private List<HelloBlockOutput> outputs;
	public String getTxHash() {
		return txHash;
	}
	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}
	public String getBlockHash() {
		return blockHash;
	}
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	public Integer getBlockHeight() {
		return blockHeight;
	}
	public void setBlockHeight(Integer blockHeight) {
		this.blockHeight = blockHeight;
	}
	public Integer getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}
	public List<HelloBlockInput> getInputs() {
		return inputs;
	}
	public void setInputs(List<HelloBlockInput> inputs) {
		this.inputs = inputs;
	}
	public List<HelloBlockOutput> getOutputs() {
		return outputs;
	}
	public void setOutputs(List<HelloBlockOutput> outputs) {
		this.outputs = outputs;
	}
	public Long getBlockTime() {
		return blockTime;
	}
	public void setBlockTime(Long blockTime) {
		this.blockTime = blockTime;
	}
	@Override
	public String toString() {
		return "HelloBlockTransaction [txHash=" + txHash + ", blockHash="
				+ blockHash + ", blockHeight=" + blockHeight + ", blockTime="
				+ blockTime + ", confirmations=" + confirmations + ", inputs="
				+ inputs + ", outputs=" + outputs + "]";
	}
	@Override
	public Transaction getTransaction() {
		log(this.toString());
		Transaction t = new Transaction();
		
		t.setHash(getTxHash());
		t.setTimestamp(getBlockTime());
		t.setHash(getTxHash());
		t.setBlockHeight(getBlockHeight()); 
		
		List<TransactionInOut> toOuts=new LinkedList<TransactionInOut>();
		for( HelloBlockOutput current : getOutputs() ) {
			TransactionInOut nuova=new TransactionInOut();
			nuova.setAddress(current.getAddress());
			nuova.setValue(current.getValue());
			nuova.setIndex(current.getIndex());
			nuova.setSpent(current.getSpent());
			toOuts.add(nuova);
		}
		Collections.sort(toOuts);
		t.setOut(toOuts);
		
		List<TransactionInOut> fromIns=new LinkedList<TransactionInOut>();
		for( HelloBlockInput current : getInputs() ) {
			TransactionInOut nuova=new TransactionInOut();
			nuova.setAddress(current.getAddress());
			nuova.setValue(current.getValue());
			fromIns.add(nuova);
		}
		Collections.sort(fromIns);
		t.setIn(fromIns);
		
		return t;
	}

	
	
}
