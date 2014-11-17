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

package io.geobit.chain.entity.apicoin;

import java.util.Map;

public class ApiCoinAddress {
	private String address;
	private Boolean isValid;
	private String hash160;
	private Map<String,Double> totalReceived;
	private Map<String,Double> totalSent;
	private Map<String,Double> finalBalance;
	private Map<String,Double> unconfirmedBalance;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	public String getHash160() {
		return hash160;
	}
	public void setHash160(String hash160) {
		this.hash160 = hash160;
	}
	public Map<String, Double> getTotalReceived() {
		return totalReceived;
	}
	public void setTotalReceived(Map<String, Double> totalReceived) {
		this.totalReceived = totalReceived;
	}
	public Map<String, Double> getTotalSent() {
		return totalSent;
	}
	public void setTotalSent(Map<String, Double> totalSent) {
		this.totalSent = totalSent;
	}
	public Map<String, Double> getFinalBalance() {
		return finalBalance;
	}
	public void setFinalBalance(Map<String, Double> finalBalance) {
		this.finalBalance = finalBalance;
	}
	public Map<String, Double> getUnconfirmedBalance() {
		return unconfirmedBalance;
	}
	public void setUnconfirmedBalance(Map<String, Double> unconfirmedBalance) {
		this.unconfirmedBalance = unconfirmedBalance;
	}
	
	
}
