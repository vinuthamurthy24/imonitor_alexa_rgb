/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.control;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.ControlModel;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;

/**
 * @author Coladi
 *
 */

public class ZwDeviceControl extends DeviceControlHelper {

	@Override
	public String updateControl(ControlModel controlModel) {
		return SUCCESS;
	}
	
	public String updateTemperature(ControlModel controlModel) {
		String generatedDeviceId = controlModel.getGeneratedDeviceId();
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceId);
		String commandParam = controlModel.getCommandParam();
		device.setCommandParam(commandParam);
		deviceManager.updateDevice(device);
		IMonitorSession.put(controlModel.getTrasactionId() + Constants.OBJ_APPENDER, device);
		return SUCCESS;
	}
}
