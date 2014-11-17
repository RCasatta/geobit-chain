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

import io.geobit.common.entity.TransactionInOut;

import java.util.List;

public class BlockChainProcTrans {
	private String hash;   /* hash della transazione */
	private Character type; /* D: deposit | W: withdraw | T: taken deposit*/
	private String address;  /* from address or destination address*/
	private Long timestamp;  
	private Boolean spent;   /* true means already spent */
	private Long value;    /* value>0:INCOMING  value<0:OUTGOING */
	private Integer index;	   /* index of output??? */
	private String prevOutId;
	private String prevOutAddress;
	private String txIndex;    /* indice di transazione*/
	private Integer blockHeight;
	private int n;
	private List<String> toList;
	private List<TransactionInOut> toOut;
	
	

	
	public List<TransactionInOut> getToOut() {
		return toOut;
	}
	public void setToOut(List<TransactionInOut> toOut) {
		this.toOut = toOut;
	}
	public List<String> getToList() {
		return toList;
	}
	public void setToList(List<String> toList) {
		this.toList = toList;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public Integer getBlockHeight() {
		return blockHeight;
	}
	public void setBlockHeight(Integer blockHeight) {
		this.blockHeight = blockHeight;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getPrevOutAddress() {
		return prevOutAddress;
	}
	public void setPrevOutAddress(String prevOutAddress) {
		this.prevOutAddress = prevOutAddress;
	}
	public Character getType() {
		return type;
	}
	public void setType(Character type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Boolean getSpent() {
		return spent;
	}
	public void setSpent(Boolean spent) {
		this.spent = spent;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	
	
	public String getTxIndex() {
		return txIndex;
	}
	public void setTxIndex(String txIndex) {
		this.txIndex = txIndex;
	}
	public String getPrevOutId() {
		return prevOutId;
	}
	public void setPrevOutId(String prevOutId) {
		this.prevOutId = prevOutId;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "BlockChainProcTrans [hash=" + hash + ", type=" + type
				+ ", address=" + address + ", timestamp=" + timestamp
				+ ", spent=" + spent + ", value=" + value + ", index=" + index
				+ ", prevOutId=" + prevOutId + ", prevOutAddress="
				+ prevOutAddress + ", txIndex=" + txIndex + "]";
	}

	
	
	
	
	

	
	

}
