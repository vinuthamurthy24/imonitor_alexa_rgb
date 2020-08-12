/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.imonitor.cms.communicator;

import in.imonitorapi.util.Constants;
import in.imonitorapi.util.IMonitorProperties;

/**
 * @author Coladi
 *
 */
public class IMonitorControllerCommunicator {

	public static String getControllerAddress() {
		return IMonitorProperties.getPropertyMap().get(Constants.CONTROLLER_IP);
	}

	public static String getControllerPort() {
		return IMonitorProperties.getPropertyMap().get(Constants.CONTROLLER_PORT);
	}
	
	public static String getControllerProtocol() {
		return IMonitorProperties.getPropertyMap().get(Constants.CONTROLLER_PROTOCOL);
	}
	
	public static String geteflControllerPort() {
		return IMonitorProperties.getPropertyMap().get(Constants.EflCONTROLLER_PORT);
	}
}