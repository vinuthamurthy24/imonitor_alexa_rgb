/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SetNoMotionTimeOut implements ImonitorControllerAction {

	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		
		Device device = actionModel.getDevice();
		Long id = device.getId();
		String timeOutValue = device.getModelNumber();
		String generatedDeviceId = device.getGeneratedDeviceId();
		String macId = device.getGateWay().getMacId();
		
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWayFromDB = null;
		gateWayFromDB = gatewayManager.getGateWayWithAgentByMacId(macId);
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,"DEVICE_UPDATE_MODEL"));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,macId));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair(Constants.NO_MOTION_DELAY,timeOutValue));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		
		Agent agent = gateWayFromDB.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		IMonitorSession.put(transactionId, this);
		requestProcessor.processRequest(messageInXml, ip , port);		
		return null;
	}
	
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = true;
		
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		this.resultExecuted = true;
		this.actionSuccess = false;
		return null;
	}
	
	public ActionModel getActionModel() {
		return actionModel;
	}
	
	public void setActionModel(ActionModel actionModel) {
		this.actionModel = actionModel;
	}
	
	public boolean isResultExecuted() {
		return resultExecuted;
	}
	
	public void setResultExecuted(boolean resultExecuted) {
		this.resultExecuted = resultExecuted;
	}
	
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}
	
	public void setActionSuccess(boolean actionSuccess) {
		this.actionSuccess = actionSuccess;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		return null;
	}
	
	
}
