package io.geobit.chain.util;

import java.util.ArrayList;
import java.util.List;

public class CircularBuffer<T> {
	List<T> lista=new ArrayList<T>();
	int current=0;
	
	public void add(T el) {
		lista.add(el);
	}
	
	public T get() {
		current= (current++)%lista.size(); 
		return lista.get(current);
	}
	
}
