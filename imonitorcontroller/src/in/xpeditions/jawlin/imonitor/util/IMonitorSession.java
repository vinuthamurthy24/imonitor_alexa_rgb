/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.util;

import java.util.HashMap;
import java.util.Map;

public class IMonitorSession {
	
	private static Map<String, Object> map = new HashMap<String, Object>();

	public static void put(String key,Object value){
		IMonitorSession.map.put(key, value);
	}  
	public static Object get(String key){
		return IMonitorSession.map.get(key);
	}  
	public static Object remove(String key){
		return IMonitorSession.map.remove(key);
	}
	public static boolean containsKey(String key) {
		return IMonitorSession.map.containsKey(key);
	}  
	
	//sumit start
	public static int getMapSize(){
		return IMonitorSession.map.size();
	}
	//sumit end
}
