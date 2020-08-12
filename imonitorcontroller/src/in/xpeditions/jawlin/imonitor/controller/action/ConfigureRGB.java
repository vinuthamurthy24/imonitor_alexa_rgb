



/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 
 * @author sumitkumar
 *
 */
public class ConfigureRGB implements ImonitorControllerAction {
	
	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		LogUtil.info("ImonitorControllerAction=");
		Device device = actionModel.getDevice();
		Long id = device.getId();
		long switchTypeNumber = device.getSwitchType();
		LogUtil.info("switchTypeNumber=="+switchTypeNumber);
		String generatedDeviceId = device.getGeneratedDeviceId();
		String macId = device.getGateWay().getMacId();
		
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWayFromDB = null;
		gateWayFromDB = gatewayManager.getGateWayWithAgentByMacId(macId);
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.SET_ZWAVE_DEV_CONFIG));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,macId));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.PARAM_ID,"10"));
		
		switch ((int) switchTypeNumber) {
		case 0:
			queue.add(new KeyValuePair(Constants.PARAM_VALUE,"0"));
			break;
		case 1:
			queue.add(new KeyValuePair(Constants.PARAM_VALUE,"1"));
			break;
		default:
			break;
		}
		
		Agent agent = gateWayFromDB.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		LogUtil.info("port=="+port);
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		LogUtil.info("messageInXml=="+messageInXml);
		IMonitorSession.put(transactionId, this);
		requestProcessor.processRequest(messageInXml, ip , port);	
		LogUtil.info("requestProcessor=="+requestProcessor);
		return null;
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		LogUtil.info("executeSuccessResults tId=="+tId);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = true;
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		if(tId != null)IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = false;
		return null;
	}

	@Override
	public boolean isResultExecuted() {
		return resultExecuted;
	}

	@Override
	public ActionModel getActionModel() {
		return actionModel;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}

}
