/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import in.xpeditions.jawlin.imonitor.controller.action.ActionDataHolder;
import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;

import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

public class PartialCurtainCloseStart implements ImonitorControllerAction {
	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;

	@Override
	public String executeCommand(ActionModel actionModel) {
		
		this.actionModel = actionModel;
		Device device=actionModel.getDevice();
		DeviceManager devicemanger = new DeviceManager();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,transactionId));
		DeviceManager deviceManager =  new DeviceManager();
		Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
		GateWay gateWay = deviceDB.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,device.getGeneratedDeviceId()));
		queue.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,"0"));
		queue.add(new KeyValuePair(Constants.ZW_CONTROL_TYPE,"2"));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		IMonitorSession.put(transactionId, this);
		requestProcessor.processRequest(messageInXml, gateWay);
		return null;
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
//		If success, let's pass it. We already saved the data.
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = true;
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		DeviceManager deviceManager = new DeviceManager();
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		Device device = deviceManager.GetDeviceForFailureByGeneratedId(generatedDeviceId);
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
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();
		keyValuePairs.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,"0"));
		keyValuePairs.add(new KeyValuePair("ZW_CONTROL_TIME","100"));
	//	keyValuePairs.add(new KeyValuePair("ZW_CONTROL_TIME",actionDataHolder.getLevel()));
//		keyValuePairs.add(new KeyValuePair("ZW_MOTOR_LEVEL", actionDataHolder.getLevel()));
		return keyValuePairs;
	}

	@Override
	public boolean isActionSuccess() {
		return actionSuccess;
	}

}
