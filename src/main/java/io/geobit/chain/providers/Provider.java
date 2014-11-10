package io.geobit.chain.providers;

import io.geobit.chain.util.Performance;

import java.util.LinkedList;
import java.util.Random;

public abstract class Provider<T> {

	protected Performance<T> perf = new Performance<T>();
	protected LinkedList<T>  list = new LinkedList<T>();
	protected Integer       total = 0;
	private   Random       random = new Random();
	

	public synchronized T take() {
		T t = list.get(random.nextInt(total));
		
		if( perf.isBlacklisted(t) ) {
			return take();
		}
		return t;
	}
	
	public synchronized T takeDifferent(T other) {
		T t = list.get(random.nextInt(total));
		
		if( perf.isBlacklisted(t) || other==t) {
			return takeDifferent(other);
		}
		return t;
	}

	public synchronized void add(T provider) {
		list.add(provider);
		perf.add(provider);
		total++;
	}

	public void record(T provider, Long millis) {
		perf.record(provider, millis);	
		

	}

	public String getElapsedOf(T provider) {
		return perf.getElapsedOf(provider);
	}


	public Performance<T> getTimers() {
		return perf;
	}

	@Override
	public String toString() {
		return "Provider [timers=" + perf + ", list=" + list.size() + " " + list + "]";
	}



}
