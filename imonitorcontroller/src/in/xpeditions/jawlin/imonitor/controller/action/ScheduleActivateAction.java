/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ScheduleActivateAction implements ImonitorControllerAction {
	private ActionModel actionModel;
	private boolean resultExecuted;
	private boolean actionSuccess;

	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Schedule schedule = (Schedule) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.SCHEDULE_ACTIVATION));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		GateWay gateWay = schedule.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.SCHEDULE_ID, "" + schedule.getId()));
		queue.add(new KeyValuePair(Constants.ACTIVATION, "" + schedule.getActivationStatus()));
		
		Agent agent = schedule.getGateWay().getAgent();
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
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		//Schedule schedule = (Schedule) this.actionModel.getData();
		String message = "msg.controller.schedules.0017"; //parishod changed message
		this.actionModel.setMessage(message);
		this.resultExecuted = true;
		this.actionSuccess = true;
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		Schedule schedule = (Schedule) this.actionModel.getData();
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		String message = "msg.controller.schedules.0018"
						+ Constants.MESSAGE_DELIMITER_1 + schedule.getName()
						+ Constants.MESSAGE_DELIMITER_2 + IMonitorUtil.commandId(queue, Constants.FAILURE_REASON);
		this.actionModel.setMessage(message);
		this.resultExecuted = true;
		this.actionSuccess = false;
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
		return null;
	}

	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}

}
