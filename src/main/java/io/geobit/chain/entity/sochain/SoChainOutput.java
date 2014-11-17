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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoChainOutput {
	private Integer output_no;
	private String address;
	private String value;
	private SoChainTxIdNo spent;
	public Integer getOutput_no() {
		return output_no;
	}
	public void setOutput_no(Integer output_no) {
		this.output_no = output_no;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public SoChainTxIdNo getSpent() {
		return spent;
	}
	public void setSpent(SoChainTxIdNo spent) {
		this.spent = spent;
	}
	@Override
	public String toString() {
		return "SoChainOutput [output_no=" + output_no + ", address=" + address
				+ ", value=" + value + ", spent=" + spent + "]";
	}
	public Long getValueLong() {
		
		return Long.parseLong( getValue().replace(".", "") );
		
	}
	
	
}
