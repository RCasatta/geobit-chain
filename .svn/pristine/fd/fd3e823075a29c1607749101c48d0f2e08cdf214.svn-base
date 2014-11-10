package io.geobit.chain.util;

public class Timed<S> {
	private S    value;
	private long dob;
	
	public Timed(S value) {
		super();
		this.value = value;
		dob=System.currentTimeMillis();
	}

	public S getValue() {
		return value;
	}

	public long getDob() {
		return dob;
	}
	
	public boolean lessThanTenSeconds() {
		return (System.currentTimeMillis()-dob)<10000;
	}

}
