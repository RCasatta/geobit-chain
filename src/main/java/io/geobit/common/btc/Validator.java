package io.geobit.common.btc;

import com.google.bitcoin.core.Address;
import com.google.bitcoin.core.AddressFormatException;
import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.params.MainNetParams;

public class Validator {
	private static final NetworkParameters params=MainNetParams.get();
	
	public static boolean isValidAddress(String address) {
	    try {
	        new Address(params, address);
	        return true;
	    } catch(AddressFormatException e) {
	        return false;
	    }
	}
}
