/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.properties;

import java.util.Map;
import java.util.Properties;

/**
 * @author Coladi
 *
 */
public class ControllerProperties {
	private static Map<String, String> properties;
	private static Properties Msgproperties;

	public static Map<String, String> getProperties() {
		return properties;
	}

	public static void setProperties(Map<String, String> properties) {
		ControllerProperties.properties = properties;
	}

	public static Properties getMsgproperties() {
		return Msgproperties;
	}

	public static void setMsgproperties(Properties msgproperties) {
		Msgproperties = msgproperties;
	}
}
