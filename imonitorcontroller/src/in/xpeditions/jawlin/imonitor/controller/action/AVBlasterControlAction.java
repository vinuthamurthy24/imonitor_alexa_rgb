package in.xpeditions.jawlin.imonitor.controller.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

public class AVBlasterControlAction implements ImonitorControllerAction {

	private ActionModel actionModel;
	private boolean resultExecuted = false;

	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		
		String trasactionId = IMonitorUtil.generateTransactionId();
		LogUtil.info("trasactionId"+trasactionId);
		
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		Device device = actionModel.getDevice();
		LogUtil.info("device"+device);
		
		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));
		String generatedDeviceId = device.getGeneratedDeviceId();
		LogUtil.info("generatedDeviceId"+generatedDeviceId);
		
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		
		String value = (String) actionModel.getData();
		LogUtil.info("value"+value);
		
		queue.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,value));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		
		RequestProcessor requestProcessor = new RequestProcessor();
		
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		LogUtil.info("messageInXml"+messageInXml);
		
		Agent agent = device.getGateWay().getAgent();
		LogUtil.info("agent"+agent);
		
		String ip = agent.getIp();
		LogUtil.info("ip"+ip);
		
		int port = agent.getControllerReceiverPort();
		LogUtil.info("port"+port);
		
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);
		return null;
		
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		DeviceManager deviceManager = new DeviceManager();
		String commandParam = this.actionModel.getDevice().getCommandParam();
		LogUtil.info(" executeSuccessResults commandParam"+commandParam);
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		LogUtil.info(" executeSuccessResults generatedDeviceId"+generatedDeviceId);
		String gateWayId = this.actionModel.getDevice().getGateWay().getMacId();
		LogUtil.info("executeSuccessResults gateWayId"+gateWayId);
		Device device = deviceManager.updateCommadParamOfDeviceByGeneratedId(generatedDeviceId, gateWayId, commandParam);
		LogUtil.info("executeSuccessResults device"+device);
		this.actionModel.setDevice(device);
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		LogUtil.info("executeSuccessResults tId"+tId);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		DeviceManager deviceManager = new DeviceManager();
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		LogUtil.info("executeFailureResults generatedDeviceId "+generatedDeviceId);
		Device device = deviceManager.GetDeviceForFailureByGeneratedId(generatedDeviceId);
		LogUtil.info("executeFailureResults device "+device);
		this.actionModel.setDevice(device);
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
	public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder) {
		List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();
		LogUtil.info("createImvgConfigParams keyValuePairs "+keyValuePairs);
		keyValuePairs.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL, "1"));
		LogUtil.info("createImvgConfigParams return keyValuePairs =="+keyValuePairs);
		return keyValuePairs;
		
	}

	@Override
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return false;
	}

}
