package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;

import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CameraSetControlAction implements ImonitorControllerAction{
	
	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;


	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		this.actionModel = actionModel;

		String presetval = (String)actionModel.getData();
		GateWay gateWay = (GateWay) this.actionModel.getDevice().getGateWay();
		String generatedDeviceId = this.actionModel.getDevice().getDeviceId();
		//LogUtil.info("Am in executeCommand and preset val is"+presetval);		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.PRESET_CAMERA));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.PRESET_SET,presetval));
		//queue.add(new KeyValuePair(Constants.PRESET_VALUE,presetval));
		Agent agent = gateWay.getAgent();
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
		// TODO Auto-generated method stub
		this.setResultExecuted(true);
		this.setActionSuccess(true);
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		// TODO Auto-generated method stub
		this.setResultExecuted(true);
		this.setActionSuccess(false);
		String message = IMonitorUtil.commandId(queue, Constants.FAILURE_REASON);
		message += ": "+ IMonitorUtil.commandId(queue, Constants.DEVICE_NAME);
		this.actionModel.setMessage(message);
		return null;
	}

	public boolean isResultExecuted() {
		return resultExecuted;
	}

	public void setResultExecuted(boolean resultExecuted) {
		this.resultExecuted = resultExecuted;
	}

	public boolean isActionSuccess() {
		return actionSuccess;
	}

	public void setActionSuccess(boolean actionSuccess) {
		this.actionSuccess = actionSuccess;
	}

	@Override
	public ActionModel getActionModel() {
		// TODO Auto-generated method stub
		return this.actionModel;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder) {
		return null;
	}


	

}
