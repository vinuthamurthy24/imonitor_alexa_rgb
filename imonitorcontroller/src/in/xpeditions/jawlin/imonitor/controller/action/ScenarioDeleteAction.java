/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ScenarioDeleteAction implements ImonitorControllerAction {

	private ActionModel actionModel;
	private boolean resultExecuted;
	private boolean actionSuccess;
	
	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#createImvgConfigParams(in.xpeditions.jawlin.imonitor.controller.action.ActionDataHolder)
	 */
	@Override
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeCommand(in.xpeditions.jawlin.imonitor.controller.action.ActionModel)
	 */
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Scenario scenario = (Scenario) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.DELETE_SCENARIO));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		GateWay gateWay = scenario.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.SCENARIO_ID, "" + scenario.getId()));
		//sumit start
		queue.add(new KeyValuePair(Constants.SCENARIO_NAME, "" + scenario.getName()));
		String scenarioDesc = scenario.getDescription();
		if(scenarioDesc.equals("") || scenarioDesc.equals(null)){
			scenarioDesc = "NA";
		}
		queue.add(new KeyValuePair(Constants.SCENARIO_DES, scenarioDesc));
		//sumit end
		Agent agent = scenario.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		IMonitorSession.put(transactionId, this);
		requestProcessor.processRequest(messageInXml, ip , port);
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeFailureResults(java.util.Queue)
	 */
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
//		If it is a failure results, pass it. Nothing more to do...
	//	Scenario scenario = (Scenario) this.actionModel.getData();
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		String failureReason = IMonitorUtil.commandId(queue, Constants.FAILURE_REASON);
		this.actionModel.setMessage(failureReason);
		this.actionSuccess = false;
		if(failureReason.equalsIgnoreCase(Constants.INVALID_SCENARIO_ID)){
			this.actionModel.setMessage("Delete Scenario from CMS.");
			this.actionSuccess = true;
		}
		this.resultExecuted = true;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeSuccessResults(java.util.Queue)
	 */
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
//		If success, then delete the schedule.
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		
		this.resultExecuted = true;
		this.actionSuccess = true;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#getActionModel()
	 */
	@Override
	public ActionModel getActionModel() {
		return this.actionModel;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#isResultExecuted()
	 */
	@Override
	public boolean isResultExecuted() {
		return this.resultExecuted;
	}

	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}
}
