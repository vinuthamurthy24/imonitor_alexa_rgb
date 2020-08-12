/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.control;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.ControlModel;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.Random;

/**
 * @author Coladi
 * 
 */
public class CameraLiveStramControl extends DeviceControlHelper {
	@Override
	public String updateControl(ControlModel controlModel) {
		String generatedDeviceId = controlModel.getGeneratedDeviceId();
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceId);
		Agent agent = device.getGateWay().getAgent();
		String streamingIp = agent.getStreamingIp();
		Random randomGenerator = new Random();
		String mediaPart = IMonitorUtil.generateTransactionId() + randomGenerator.nextInt(1000);
		String rtspUrl = "rtsp://" + streamingIp + ":" + agent.getRtspPort() + "/" + mediaPart;
		IMonitorSession.put(controlModel.getTrasactionId() + Constants.OBJ_APPENDER, rtspUrl);
		return SUCCESS;
	}
}
