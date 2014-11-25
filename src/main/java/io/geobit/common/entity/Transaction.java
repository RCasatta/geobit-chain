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

package io.geobit.common.entity;


import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction implements Comparable<Transaction> {
	private String  hash;       /* hash */
	private Long    timestamp;  /* unix timestamp UTC of the block when the transaction is first confirmed */
	private Integer blockHeight;
	private List<TransactionInOut> in;
	private List<TransactionInOut> out;

	public Transaction() {
		super();
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getBlockHeight() {
		return blockHeight;
	}

	public void setBlockHeight(Integer blockHeight) {
		this.blockHeight = blockHeight;
	}

	public List<TransactionInOut> getIn() {
		return in;
	}

	public void setIn(List<TransactionInOut> in) {
		this.in = in;
	}

	public List<TransactionInOut> getOut() {
		return out;
	}

	public void setOut(List<TransactionInOut> out) {
		this.out = out;
	}
	
	public String getFrom() {
		return getIn().get(0).getAddress();
	}
	
	public String getTo() {
		return getOut().get(0).getAddress();
	}
	
	public boolean areAllSpent() {
		boolean returned=true;
		for(TransactionInOut cur : getOut()) {
			if(cur.isSpent()==false)
				return false;
		}
		return returned;
	}

	@Override
	public String toString() {
		return "Transaction [hash=" + hash + ", timestamp=" + timestamp
				+ ", blockHeight=" + blockHeight
				+ ", in=" + in + ", out=" + out + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blockHeight == null) ? 0 : blockHeight.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + ((in == null) ? 0 : in.hashCode());
		result = prime * result + ((out == null) ? 0 : out.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (blockHeight == null) {
			if (other.blockHeight != null)
				return false;
		} else if (!blockHeight.equals(other.blockHeight))
			return false;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (in == null) {
			if (other.in != null)
				return false;
		} else if (!in.equals(other.in))
			return false;
		if (out == null) {
			if (other.out != null)
				return false;
		} else if (!out.equals(other.out))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	@Override
	public int compareTo(Transaction o) {
		if( o.getBlockHeight().intValue()==getBlockHeight().intValue() ) {
			return getHash().compareTo(o.getHash());
		} else {
			return o.getBlockHeight()-getBlockHeight();
		}
 		
	}
	
	
	
	

}
