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

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoChainIncoming {
	private Integer output_no;
	private String value;
	private SoChainTxIdNo spent;
	private List<SoChainInput> inputs;
	private Integer req_sigs;
	private String script_asm;
	private String script_hex;
	public Integer getOutput_no() {
		return output_no;
	}
	public void setOutput_no(Integer output_no) {
		this.output_no = output_no;
	}
	public String getValue() {
		return value;
	}
	public Long getValueLong() {
		return Long.parseLong( getValue().replace(".", "") );
		
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
	public List<SoChainInput> getInputs() {
		return inputs;
	}
	public void setInputs(List<SoChainInput> inputs) {
		this.inputs = inputs;
	}
	public Integer getReq_sigs() {
		return req_sigs;
	}
	public void setReq_sigs(Integer req_sigs) {
		this.req_sigs = req_sigs;
	}
	public String getScript_asm() {
		return script_asm;
	}
	public void setScript_asm(String script_asm) {
		this.script_asm = script_asm;
	}
	public String getScript_hex() {
		return script_hex;
	}
	public void setScript_hex(String script_hex) {
		this.script_hex = script_hex;
	}
	@Override
	public String toString() {
		return "SoChainIncoming [output_no=" + output_no + ", value=" + value
				+ ", spent=" + spent + ", inputs=" + inputs + ", req_sigs="
				+ req_sigs + ", script_asm=" + script_asm + ", script_hex="
				+ script_hex + "]";
	}
	
	
	

}
