/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.control;

import in.xpeditions.jawlin.imonitor.util.ControlModel;

/**
 * @author Coladi
 *
 */

public class RemoveDeviceControl extends DeviceControlHelper {
	
	@Override
	public String updateControl(ControlModel controlModel) {
//		String generatedDeviceId = controlModel.getGeneratedDeviceId();
//		DeviceManager deviceManager = new DeviceManager();
//		Device device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceId);
//		deviceManager.deleteDevice(device);
		return SUCCESS;
	}
}
