/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.service.CommandIssueServiceManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class DisableAction implements ImonitorControllerAction 
{


	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;

	@Override
	public String executeCommand(ActionModel actionModel) {
		
		this.actionModel = actionModel;
		Device device = (Device) this.actionModel.getDevice();
		//we are trying to disable camera so it must be enabled.
		device.setEnableStatus("1");
		device.setCommandParam("1");

		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		GateWay gateWay = device.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID, device.getGeneratedDeviceId()));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair(Constants.IPC_ENABLE_LIVE_STREAM,Constants.DISABLE));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		
		int port = agent.getControllerReceiverPort();
		
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		
		IMonitorSession.put(transactionId, this);
		requestProcessor.processRequest(messageInXml, ip , port);
		return null;
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		Device device = (Device) this.actionModel.getDevice();
		String generatedDeviceId = device.getGeneratedDeviceId();
		DeviceManager deviceManager =new DeviceManager();
		if(deviceManager.updateDeviceEnableStatus(generatedDeviceId,"0"))
		{
			device.setEnableStatus("0");
			device.setCommandParam("0");
		}
		else
		{
			LogUtil.error("We were able to update imvg but unable to update database");
		}
		//CommandIssueServiceManager Command=new CommandIssueServiceManager();
		//Command.killLiveStream(generatedDeviceId);
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
		keyValuePairs.add(new KeyValuePair(Constants.IPC_ENABLE_LIVE_STREAM,Constants.DISABLE));
		return keyValuePairs;
	}

	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}


}
