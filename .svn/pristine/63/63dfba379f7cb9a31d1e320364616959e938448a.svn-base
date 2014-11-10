package io.geobit.chain.util;

import io.geobit.statics.StaticNumbers;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.EvictingQueue;

public class Performance<T> {

	private   Map<T, EvictingQueue<Long> > timers  = new HashMap< T , EvictingQueue<Long>>();
	private   Map<T, Integer>            blacklist = new HashMap< T , Integer>();
	private   Map<T, Long>                      ko = new HashMap< T , Long>();
	private   Map<T, Long>                      ok = new HashMap< T , Long>();

	public void record(T t, Long millis) {
		Integer blackListRound=blacklist.get(t);
		if(millis==null) {
			Long error=ko.get(t);
			ko.put(t, error+1);
			blacklist.put(t, blackListRound + StaticNumbers.PENALTY_ROUND );
		} else {
			Long good=ok.get(t);
			ok.put(t, good+1);
			EvictingQueue<Long> times = timers.get(t);
			times.add(millis);
			int penalty=millis.intValue()/1000; /* every seconds 1 penalty round */
			if(penalty>0) {		
				blacklist.put(t, blackListRound + Math.min( penalty, StaticNumbers.PENALTY_ROUND ) );		
			}
		}
	}
	
	public String getElapsedOf(T prov) {
		EvictingQueue<Long> timer=timers.get(prov);
		
		if(timer!=null) {
			StringBuffer sb=new StringBuffer();
			int maxEl=10;
			for(Long l : timer) {
				if(maxEl--<=0)
					break;
				sb.append(l);
				sb.append(", ");
			}
			return sb.toString();
		}
		else
			return "";
	}
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		
		for (Map.Entry<T, EvictingQueue<Long>> entry : timers.entrySet()) {
			T            key = entry.getKey();
		    EvictingQueue<Long> value = entry.getValue();
		    Long min=Long.MAX_VALUE;
		    Long max=Long.MIN_VALUE;
		    Long total=0L;
		    for(Long current : value) {
		    	total+=current;
		    	if(current<min)
		    		min=current;
		    	if(current>max)
		    		max=current;
		    }
		    int size = value.size();
			Double mean=(total*1.0)/size;
		    Double totVar=0.0;
		    for(Long current : value) {
		    	totVar+= Math.pow(mean-current,2);
		    }
		    Double var=Math.pow(totVar/size,0.5);
		    Long tot=ok.get(key) + ko.get(key);
		    Double percKo= (ko.get(key).doubleValue()/ tot.doubleValue())*100; 
		    sb.append("tot=");
		    sb.append(String.format("%6d", tot ));
		    sb.append(" ko=" );
		    sb.append(String.format("%5.2f", percKo));
		    sb.append("% m=");
		    sb.append(String.format("%5d", Math.round(mean)));
		    sb.append(" v=");
		    sb.append(String.format("%5d", Math.round(var)));
		    sb.append(" min=");
		    sb.append(String.format("%5d", min));
		    sb.append(" max=");
		    sb.append(String.format("%5d", max));
		    sb.append(" ");
		    sb.append(key);
		    sb.append("\n");
		}
		
		return sb.toString();
	}

	public boolean isBlacklisted(T t) {
		Integer val=blacklist.get(t);
		if(val<=0)
			return false;
		blacklist.put(t, val-1);
		return true;
	}
	
	public void add(T t) {
		blacklist.put(t, 0);
		ko.put(t, 0L);
		ok.put(t, 0L);
		EvictingQueue<Long> q= EvictingQueue.create(10000);
		timers.put(t, q);
		
	}

}
