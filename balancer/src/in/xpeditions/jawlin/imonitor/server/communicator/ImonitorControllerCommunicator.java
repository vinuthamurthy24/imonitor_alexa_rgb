/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.communicator;

import in.xpeditions.jawlin.imonitor.server.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Asmodeus
 *
 */
public class ImonitorControllerCommunicator {
	
	private static Map<String, String> propertyMap = new HashMap<String, String>();

	public static String getControllerAddress() {
		return ImonitorControllerCommunicator.getPropertyMap().get(Constants.CONTROLLER_IP);
	}

	public static String getControllerPort() {
		return ImonitorControllerCommunicator.getPropertyMap().get(Constants.CONTROLLER_PORT);
	}
	
	public static String getControllerProtocol() {
		return ImonitorControllerCommunicator.getPropertyMap().get(Constants.CONTROLLER_PROTOCOL);
	}

	public static Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public static void setPropertyMap(Map<String, String> propertyMap) {
		ImonitorControllerCommunicator.propertyMap = propertyMap;
	}

	
}
