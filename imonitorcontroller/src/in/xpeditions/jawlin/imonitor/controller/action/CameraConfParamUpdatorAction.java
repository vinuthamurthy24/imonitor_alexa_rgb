/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
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
public class CameraConfParamUpdatorAction implements ImonitorControllerAction {
	

	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess=false;

	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Device device = actionModel.getDevice();
		CameraConfiguration cameraConfiguration = (CameraConfiguration) device.getDeviceConfiguration();
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
		queue.add(new KeyValuePair(Constants.IPC_JPEG_RESOLUTION, "" + cameraConfiguration.getJpegResolution()));
		queue.add(new KeyValuePair(Constants.IPC_JPEG_QUALITY, "" + cameraConfiguration.getJpegQuality()));
		queue.add(new KeyValuePair(Constants.IPC_VIDEO_COLOR_BALANCE, "" + cameraConfiguration.getVideoColorBalance()));
		queue.add(new KeyValuePair(Constants.IPC_VIDEO_BRIGHTNESS, "" + cameraConfiguration.getVideoBrightness()));
		queue.add(new KeyValuePair(Constants.IPC_VIDEO_SHARPNESS, "" + cameraConfiguration.getVideoSharpness()));
		queue.add(new KeyValuePair(Constants.IPC_VIDEO_HUE, "" + cameraConfiguration.getVideoHue()));
		queue.add(new KeyValuePair(Constants.IPC_VIDEO_SATURATION, "" + cameraConfiguration.getVideoSaturation()));
		queue.add(new KeyValuePair(Constants.IPC_VIDEO_CONTRAST, "" + cameraConfiguration.getVideoContrast()));
		queue.add(new KeyValuePair(Constants.IPC_VIDEO_AC_FREQUENCY, "" + cameraConfiguration.getVideoAcFrequency()));
		queue.add(new KeyValuePair(Constants.IPC_MPEG4_RESOLUTION, "" + cameraConfiguration.getMpeg4Resolution()));
		queue.add(new KeyValuePair(Constants.IPC_MPEG4_QUALITY_CONTROL, "" + cameraConfiguration.getMpeg4QualityControl()));
		queue.add(new KeyValuePair(Constants.IPC_MPEG4_QUALITY_LEVEL, "" + cameraConfiguration.getMpeg4QualityLevel()));
		queue.add(new KeyValuePair(Constants.IPC_MPEG4_BITRATE, "" + cameraConfiguration.getMpeg4BitRate()));
		queue.add(new KeyValuePair(Constants.IPC_MPEG4_FRAMERATE, "" + cameraConfiguration.getMpeg4FrameRate()));
		queue.add(new KeyValuePair(Constants.IPC_MPEG4_BANDWIDTH, "" + cameraConfiguration.getMpeg4BandWidth()));
		queue.add(new KeyValuePair(Constants.IPC_NETWORK_MODE, "" + cameraConfiguration.getNetworkMode()));
		queue.add(new KeyValuePair(Constants.IPC_SYSTEM_NIGHTVISION_CONTROL, "" + cameraConfiguration.getSystemNightVisionControl()));

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
		/*DeviceManager deviceManager = new DeviceManager();
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		String gateWayId = this.actionModel.getDevice().getGateWay().getMacId();
		CameraConfiguration cameraConfiguration = (CameraConfiguration) this.actionModel.getDevice().getDeviceConfiguration();
		Device device = deviceManager.updateCameraConfiguration(generatedDeviceId, gateWayId, cameraConfiguration);
		this.actionModel.setDevice(device);
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);*/
		this.resultExecuted = true;
		return null;
	}

	@Override
	public boolean isResultExecuted() {
		this.actionSuccess = true;
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
		return this.actionSuccess;
	}
}
