package io.geobit.chain.util;

public class PrefixMean implements Comparable<PrefixMean> {
	private String prefix;
	private Double mean;
	
	
	
	public PrefixMean(String prefix, Double mean) {
		super();
		this.prefix = prefix;
		this.mean = mean;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public Double getMean() {
		return mean;
	}
	public void setMean(Double mean) {
		this.mean = mean;
	}
	
	


	@Override
	public String toString() {
		return "PrefixMean [prefix=" + prefix + ", mean=" + mean + "]";
	}
	@Override
	public int compareTo(PrefixMean arg0) {
		if(mean>arg0.getMean())
			return 1;
		else if(mean<arg0.getMean()) 
			return -1;
		else
			return 0;

	}

}
