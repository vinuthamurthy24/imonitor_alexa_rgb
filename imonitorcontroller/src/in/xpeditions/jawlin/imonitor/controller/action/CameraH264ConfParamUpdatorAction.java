/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.H264CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @author Coladi
 *
 */
public class CameraH264ConfParamUpdatorAction implements ImonitorControllerAction {
	

	private ActionModel actionModel;
	private boolean resultExecuted = false;

	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Device device = actionModel.getDevice();
		H264CameraConfiguration cameraConfiguration = (H264CameraConfiguration) device.getDeviceConfiguration();
		GateWay gateWay = device.getGateWay();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
		String generatedDeviceId = device.getGeneratedDeviceId();
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));

		queue.add(new KeyValuePair(Constants.IPC_USER_ADMIN_USERNAME, cameraConfiguration.getAdminUserName()));
		queue.add(new KeyValuePair(Constants.IPC_USER_ADMIN_PASSWORD, cameraConfiguration.getAdminPassword()));
		queue.add(new KeyValuePair(Constants.H264_VIDEO_BRIGHTNESS, "" + cameraConfiguration.getVideoBrightness()));
		queue.add(new KeyValuePair(Constants.H264_VIDEO_CONTRAST, "" + cameraConfiguration.getVideoContrast()));
		queue.add(new KeyValuePair(Constants.H264_VIDEO_AC_FREQUENCY, "" + cameraConfiguration.getVideoAcFrequency()));
		queue.add(new KeyValuePair(Constants.H264_VIDEO_FRAMERATE, "" + cameraConfiguration.getVideoFrameRate()));
		queue.add(new KeyValuePair(Constants.H264_VIDEO_RESOLUTION, "" + cameraConfiguration.getVideoResolution()));
		queue.add(new KeyValuePair(Constants.H264_IMAGE_QUALITY, "" + cameraConfiguration.getVideoQuality()));
		queue.add(new KeyValuePair(Constants.H264_VIDEO_BITRATE, "" + cameraConfiguration.getVideoBitRate()));
		queue.add(new KeyValuePair(Constants.H264_VIDEO_BITRATE_MODE, "" + cameraConfiguration.getVideoBitRateMode()));
		queue.add(new KeyValuePair(Constants.H264_VIDEO_KEYFRAME, "" + cameraConfiguration.getVideoKeyFrame()));
		queue.add(new KeyValuePair(Constants.H264_LED_MODE, "" + cameraConfiguration.getLedMode()));
		queue.add(new KeyValuePair(Constants.H264_PTZ_PATROL_RATE, "" + cameraConfiguration.getPtzPatrolRate()));
		queue.add(new KeyValuePair(Constants.H264_PTZ_PATROL_UP_RATE, "" + cameraConfiguration.getPtzPatrolUpRate()));
		queue.add(new KeyValuePair(Constants.H264_PTZ_PATROL_DOWN_RATE, "" + cameraConfiguration.getPtzPatrolDownRate()));
		queue.add(new KeyValuePair(Constants.H264_PTZ_PATROL_LEFT_RATE, "" + cameraConfiguration.getPtzPatrolLeftRate()));
		queue.add(new KeyValuePair(Constants.H264_PTZ_PATROL_RIGHT_RATE, "" + cameraConfiguration.getPtzPatrolRightRate()));
		queue.add(new KeyValuePair(Constants.H264_USER_ADMIN_USERNAME, "" + cameraConfiguration.getAdminUserName()));
		queue.add(new KeyValuePair(Constants.H264_USER_ADMIN_PASSWORD, "" + cameraConfiguration.getAdminPassword()));

		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);
		return null;
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		DeviceManager deviceManager = new DeviceManager();
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		String gateWayId = this.actionModel.getDevice().getGateWay().getMacId();
		H264CameraConfiguration cameraConfiguration = (H264CameraConfiguration) this.actionModel.getDevice().getDeviceConfiguration();
		Device device = deviceManager.updateH264CameraConfiguration(generatedDeviceId, gateWayId, cameraConfiguration);
		this.actionModel.setDevice(device);
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		return null;
	}

	@Override
	public boolean isResultExecuted() {
		return this.resultExecuted;
	}

	@Override
	public ActionModel getActionModel() {
		return this.actionModel;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return false;
	}
}
