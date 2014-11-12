package io.geobit.common.statics;

//import io.geobit.clients.GeobitAWSClient;

public class Booleans {
	public static final Boolean USE_PROXY    = false;

	public static boolean isProd() {
		return false;
//		return GeobitAWSClient.getInstance().isProd();
	}

	public static boolean checkHost(String host) {
		if( host.contains("geobit.io") || (!isProd() && host.contains("localhost")) )
			return true;
		else {
			System.out.println("request host not authorized");
			return false;
//			return true;
		}
	} 
	
	public static boolean isEncoded(String orig) {
		String decoded=StaticStrings.decode(orig);
		if(orig.length()<=decoded.length())
			return false;
		else
			return true;
		
	}
	
	public static boolean isGeobitAddr(String addr) {
		if(addr!=null && !"".equals(addr)) {
			if(addr.startsWith("1G"))
				if( Maps.levelsToArea.get(  addr.charAt(2) ) !=null )
					return true;
		}
		return false;
		
	}
}
