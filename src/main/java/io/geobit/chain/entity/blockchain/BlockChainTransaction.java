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

import io.geobit.chain.providers.TransactionGetter;
import io.geobit.common.entity.Transaction;
import io.geobit.common.entity.TransactionInOut;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import static io.geobit.common.statics.Log.*;

@XmlRootElement
public class BlockChainTransaction  implements TransactionGetter {
	private Integer result;
	private Integer block_height;
	private Long time;
	private List<BlockChainInputOutput> inputs;
	private List<BlockChainInputOutput> out;
	private String hash;
	private String tx_index;
	
	
	
	public BlockChainTransaction() {
		super();
	}
	
	
	public String getTx_index() {
		return tx_index;
	}


	public void setTx_index(String tx_index) {
		this.tx_index = tx_index;
	}


	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getBlock_height() {
		return block_height;
	}
	public void setBlock_height(Integer block_height) {
		this.block_height = block_height;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public List<BlockChainInputOutput> getInputs() {
		return inputs;
	}
	public void setInputs(List<BlockChainInputOutput> inputs) {
		this.inputs = inputs;
	}
	public List<BlockChainInputOutput> getOut() {
		return out;
	}
	public void setOut(List<BlockChainInputOutput> out) {
		this.out = out;
	}
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}

 
	@Override
	public String toString() {
		return "BlockChainTransaction [result=" + result + ", blockHeight="
				+ block_height + ", time=" + time + ", inputs=" + inputs
				+ ", out=" + out + ", hash=" + hash + "]";
	}
	

	@Override
	public Transaction getTransaction() {
		log(this.toString() );
		Transaction t = new Transaction();
		
		t.setHash(hash);
		t.setBlockHeight(block_height);
		
		List<TransactionInOut> toOuts=new LinkedList<TransactionInOut>();
		for( BlockChainInputOutput current : getOut() ) {
			TransactionInOut nuova=new TransactionInOut();
			nuova.setAddress(current.getAddr());
			nuova.setValue(current.getValue());
			nuova.setIndex(current.getN());
			nuova.setSpent(current.getSpent());
			toOuts.add(nuova);
		}
		Collections.sort(toOuts);
		t.setOut(toOuts);
		
		List<TransactionInOut> fromIns=new LinkedList<TransactionInOut>();
		for( BlockChainInputOutput current : getInputs() ) {
			TransactionInOut nuova=new TransactionInOut();
			BlockChainInputOutput prev= current.getPrev_out();
			nuova.setAddress(prev.getAddr());
			nuova.setValue(prev.getValue());
			//nuova.setIndex(current.getN());
			fromIns.add(nuova);
		}
		Collections.sort(fromIns);
		t.setIn(fromIns);
		
		return t;
		
	}

}
