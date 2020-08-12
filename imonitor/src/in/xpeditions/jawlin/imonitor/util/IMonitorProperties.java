/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.util;

import java.util.Map;

/**
 * @author Coladi
 *
 */
public class IMonitorProperties {
	private static Map<String, String> propertyMap;
	private static Map<String,String> Msgproperties;
	public static Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public static void setPropertyMap(Map<String, String> propertyMap) {
		IMonitorProperties.propertyMap = propertyMap;
	}

	public static Map<String,String> getMsgproperties() {
		return Msgproperties;
	}

	public static void setMsgproperties(Map<String,String> msgproperties) {
		Msgproperties = msgproperties;
	}
}
