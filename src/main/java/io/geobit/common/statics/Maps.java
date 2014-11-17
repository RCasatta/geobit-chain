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

package io.geobit.common.statics;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Maps {

	public static final Map<String,String> whereLabels;
	static {
		whereLabels =new HashMap<String, String>();
		whereLabels.put("wherechoice1", "Across the world");
		whereLabels.put("wherechoice2", "Across specific area");
		whereLabels.put("wherechoice3", "Same geobit");
	}
	
	public static final Map<String,String> howManyLabels;
	static {
		howManyLabels =new HashMap<String, String>();
		howManyLabels.put("howmanychoice1", "5");
		howManyLabels.put("howmanychoice2", "10");
		howManyLabels.put("howmanychoice3", "20");
		howManyLabels.put("howmanychoice4", "50");
		howManyLabels.put("howmanychoice5", "100");
	}
	
	public static final Map<Character,Integer> levelsToZoom;
	
	static
    {
		levelsToZoom = new HashMap<Character, Integer>();
		
		levelsToZoom.put('1', 2);
		levelsToZoom.put('2', 3);
		levelsToZoom.put('3', 4);
		levelsToZoom.put('4', 5);
		levelsToZoom.put('5', 6);
		levelsToZoom.put('6', 7);
		levelsToZoom.put('7', 8);
		levelsToZoom.put('8', 9);
		levelsToZoom.put('9', 10);
		levelsToZoom.put('A', 11);
		levelsToZoom.put('B', 12);
		levelsToZoom.put('C', 13);
		levelsToZoom.put('D', 14);
		levelsToZoom.put('E', 15);
		levelsToZoom.put('F', 16);
		levelsToZoom.put('G', 17);
		levelsToZoom.put('H', 18);
		levelsToZoom.put('i', 19);
		levelsToZoom.put('J', 20);
         
    }
	
	public static final Map<Character,Integer> levelsToIndex;
	static
    {
		levelsToIndex = new HashMap<Character, Integer>();
		
		levelsToIndex.put('1', 0);
		levelsToIndex.put('2', 1);
		levelsToIndex.put('3', 2);
		levelsToIndex.put('4', 3);
		levelsToIndex.put('5', 4);
		levelsToIndex.put('6', 5);
		levelsToIndex.put('7', 6);
		levelsToIndex.put('8', 7);
		levelsToIndex.put('9', 8);
		levelsToIndex.put('A', 9);
		levelsToIndex.put('B', 10);
		levelsToIndex.put('C', 11);
		levelsToIndex.put('D', 12);
		levelsToIndex.put('E', 13);
		levelsToIndex.put('F', 14);
		levelsToIndex.put('G', 15);
		levelsToIndex.put('H', 16);
		levelsToIndex.put('i', 17);
		levelsToIndex.put('J', 18);
		levelsToIndex.put('X', 19);
         
    }
	
	
	public static final Map<Character,Integer> levelsToLimit;
	static
    {
		levelsToLimit = new HashMap<Character, Integer>();
		
		levelsToLimit.put('1', 60);
		levelsToLimit.put('2', 70);
		levelsToLimit.put('3', 75);
		levelsToLimit.put('4', 80);
		levelsToLimit.put('5', 80);
		levelsToLimit.put('6', 80);
		levelsToLimit.put('7', 80);
		levelsToLimit.put('8', 80);
		levelsToLimit.put('9', 80);
		levelsToLimit.put('A', 80);
		levelsToLimit.put('B', 80);
		levelsToLimit.put('C', 80);
		levelsToLimit.put('D', 80);
		levelsToLimit.put('E', 80);
		levelsToLimit.put('F', 80);
		levelsToLimit.put('G', 80);
		levelsToLimit.put('H', 80);
		levelsToLimit.put('i', 80);
		levelsToLimit.put('J', 80);
         
    }
	

	public static final Map<Character,Integer> levelsToArea;
	
	static
    {
		levelsToArea = new HashMap<Character, Integer>();
		
		levelsToArea.put('1', 0);
		levelsToArea.put('2', 0);
		levelsToArea.put('3', 0);
		levelsToArea.put('4', 1);
		levelsToArea.put('5', 1);
		levelsToArea.put('6', 1);
		levelsToArea.put('7', 1);
		levelsToArea.put('8', 2);
		levelsToArea.put('9', 2);
		levelsToArea.put('A', 2);
		levelsToArea.put('B', 3);
		levelsToArea.put('C', 3);
		levelsToArea.put('D', 3);
		levelsToArea.put('E', 3);
		levelsToArea.put('F', 3);
		levelsToArea.put('G', 4);
		levelsToArea.put('H', 4);
		levelsToArea.put('i', 4);
		levelsToArea.put('J', 4);
         
    }
	
	public static final Map<Integer,Double> PERCENTUALS;
	static
    {
		PERCENTUALS = new HashMap<Integer, Double>();
		
		PERCENTUALS.put(2, 0.8);
		PERCENTUALS.put(3, 0.7);
		PERCENTUALS.put(4, 0.6);
		PERCENTUALS.put(5, 0.5);
		PERCENTUALS.put(6, 0.5);
		PERCENTUALS.put(7, 0.5);
		PERCENTUALS.put(8, 0.5);
		
         
    }
	
	
	
	

}
