package in.xpeditions.jawlin.imonitor.controller.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import in.xpeditions.jawlin.imonitor.controller.action.ActionDataHolder;
import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

public class CameraMoveControlAction implements ImonitorControllerAction {

	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		this.actionModel = actionModel;
		String presetval = this.actionModel.getDevice().getCommandParam();
		GateWay gateWay = (GateWay) this.actionModel.getDevice().getGateWay();
		String device = this.actionModel.getDevice().getGeneratedDeviceId();
		//LogUtil.info("Am in executeCommand and preset val is"+presetval);
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();

		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.PRESET_CAMERA));

		String transactionId = IMonitorUtil.generateTransactionId();

		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));

		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));

		//String generatedDeviceId = device.getGeneratedDeviceId();

		queue.add(new KeyValuePair(Constants.DEVICE_ID,device));

		queue.add(new KeyValuePair(Constants.PRESET_MOVE,presetval));

		//queue.add(new KeyValuePair(Constants.PRESET_VALUE,presetval));

		Agent agent = gateWay.getAgent();

		String ip = agent.getIp();

		int port = agent.getControllerReceiverPort();

		RequestProcessor requestProcessor = new RequestProcessor();

		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);

		IMonitorSession.put(transactionId, this);

		requestProcessor.processRequest(messageInXml, ip , port);
		//LogUtil.info("Am returning from executeCommand");
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

	@Override
	public boolean isResultExecuted() {
		// TODO Auto-generated method stub
		return resultExecuted;
	}

	@Override
	public ActionModel getActionModel() {
		// TODO Auto-generated method stub
		return this.actionModel;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();
		keyValuePairs.add(new KeyValuePair(Constants.PRESET_MOVE,actionDataHolder.getLevel()));
		return keyValuePairs;
	}

	@Override
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return actionSuccess;
	}

	public void setResultExecuted(boolean resultExecuted) {
		this.resultExecuted = resultExecuted;
	}

	public void setActionSuccess(boolean actionSuccess) {
		this.actionSuccess = actionSuccess;
	}

}
