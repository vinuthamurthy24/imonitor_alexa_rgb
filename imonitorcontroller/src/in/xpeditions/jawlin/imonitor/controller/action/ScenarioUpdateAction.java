/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScenarioAction;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;
import in.xpeditions.jawlin.imonitor.util.UpdatorModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ScenarioUpdateAction implements ImonitorControllerAction {

	private ActionModel actionModel;
	private boolean resultExecuted = false;
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
		Device device=null;
		this.actionModel = actionModel;
		Scenario scenario = (Scenario) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.MODIFY_SCENARIO));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		GateWay gateWay = scenario.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.SCENARIO_ID, "" + scenario.getId()));
		queue.add(new KeyValuePair(Constants.SCENARIO_NAME, IMonitorUtil.unicodeEscape(scenario.getName())));
		String scenarioDesc = scenario.getDescription();
		if(scenarioDesc.equals("") || scenarioDesc.equals(null)){
			scenarioDesc = "NA";
		}
		queue.add(new KeyValuePair(Constants.SCENARIO_DES, IMonitorUtil.unicodeEscape(scenarioDesc)));
		Set<ScenarioAction> scenarioActions = scenario.getScenarioActions();
		Agent agent = scenario.getGateWay().getAgent();
		
		String actionDefinition = "";
		List<String> actionDef = new ArrayList<String>(25);			//Max 25 Actions per Scenario
		for (ScenarioAction scenarioAction : scenarioActions) {
			//sumit start: HANDLE VIRTUAL DEVICES UPDATE.
			String generatedDeviceId = scenarioAction.getDevice().getGeneratedDeviceId();
			
			if((!generatedDeviceId.contains("HOME")) && (!generatedDeviceId.contains("STAY"))
					&& (!generatedDeviceId.contains("AWAY"))){
				device = scenarioAction.getDevice();
				actionDefinition = generatedDeviceId.substring(18);
				UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(scenarioAction.getActionType().getCommand());
				Class<?> className = updatorModel.getClassName();
				try {
					ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
					ActionDataHolder actionDataHolder = new ActionDataHolder();
					actionDataHolder.setLevel(scenarioAction.getLevel());
					actionDataHolder.setGateWay(gateWay);
					actionDataHolder.setDevice(device);
					List<KeyValuePair> imvgConfigParams = controllerAction.createImvgConfigParams(actionDataHolder);
					if(!imvgConfigParams.isEmpty()){
						for(KeyValuePair i : imvgConfigParams){
							actionDefinition += ";" + i.getKey() + "=" + i.getValue();
						}	
					}
					actionDef.add(actionDefinition);
				} catch (InstantiationException e) {
					e.printStackTrace();
					LogUtil.info(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					LogUtil.info(e.getMessage(), e);
				}
			}
		}
		
		for(String s1 : actionDef)
			queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF, s1));
		
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
		//Scenario scenario = (Scenario) this.actionModel.getData();
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		String message = IMonitorUtil.commandId(queue, Constants.FAILURE_REASON);
		this.actionModel.setMessage(message);
		this.resultExecuted = true;
		this.actionSuccess = false;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeSuccessResults(java.util.Queue)
	 */
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
//		If success, then update the schedule.
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		//Scenario scenario = (Scenario) this.actionModel.getData();
		String message = "msg.controller.scenarios.0012";
		this.actionModel.setMessage(message);
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
