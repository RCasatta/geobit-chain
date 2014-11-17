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

package io.geobit.chain.entity.sochain;

import io.geobit.chain.providers.TransactionGetter;
import io.geobit.common.entity.Transaction;
import io.geobit.common.entity.TransactionInOut;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoChainTransaction implements TransactionGetter {
	private String txid;
	private Integer block_no;
	private Integer confirmations;
	private Long time;
	private List<SoChainInput>  inputs;
	private List<SoChainOutput> outputs;
	public String getTxid() {
		return txid;
	}
	public void setTxid(String txid) {
		this.txid = txid;
	}
	public Integer getBlock_no() {
		return block_no;
	}
	public void setBlock_no(Integer block_no) {
		this.block_no = block_no;
	}
	public Integer getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	public List<SoChainInput> getInputs() {
		return inputs;
	}
	public void setInputs(List<SoChainInput> inputs) {
		this.inputs = inputs;
	}
	public List<SoChainOutput> getOutputs() {
		return outputs;
	}
	public void setOutputs(List<SoChainOutput> outputs) {
		this.outputs = outputs;
	}
	@Override
	public String toString() {
		return "SoChainTransaction [txid=" + txid + ", block_no=" + block_no
				+ ", confirmations=" + confirmations + ", time=" + time
				+ ", inputs=" + inputs + ", outputs=" + outputs + "]";
	}
	@Override
	public Transaction getTransaction() {
		Transaction t = new Transaction() ;
		t.setHash( getTxid() );
		t.setBlockHeight(getBlock_no());
		t.setTimestamp(getTime());
		
		List<TransactionInOut> inputs=new LinkedList<TransactionInOut>();
		for(SoChainInput current : getInputs() ) {
			TransactionInOut nuova=new TransactionInOut();
			nuova.setAddress(current.getAddress());
			nuova.setValue(current.getValueLong());
			inputs.add(nuova);
		}
		Collections.sort(inputs);
		t.setIn(inputs);
		
		List<TransactionInOut> outputs=new LinkedList<TransactionInOut>();
		for(SoChainOutput current : getOutputs() ) {
			TransactionInOut nuova=new TransactionInOut();
			nuova.setAddress(current.getAddress());
			nuova.setValue(current.getValueLong());
			nuova.setIndex(current.getOutput_no());
			nuova.setSpent(current.getSpent()!=null);
			outputs.add(nuova);
		}
		Collections.sort(outputs);
		t.setOut(outputs);
		
		return t;

	}



}
