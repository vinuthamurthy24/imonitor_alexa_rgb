/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.communicator;

import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorProperties;

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
}