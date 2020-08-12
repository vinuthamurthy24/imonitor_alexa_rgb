/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertsAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
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

/**
 * @author Coladi
 *
 */
public class RuleUpdateAction implements ImonitorControllerAction {
	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;
	
	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#createImvgConfigParams(in.xpeditions.jawlin.imonitor.controller.action.ActionDataHolder)
	 */
	@Override
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		// TODO Auto-generated method stub
		return null;
	}

	// *************************************************************** sumit start *************************************************************
	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeCommand(in.xpeditions.jawlin.imonitor.controller.action.ActionModel)
	 */
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Device device=null;
		Rule rule = (Rule) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.MODIFY_RULE));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		GateWay gateWay = rule.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_RULE_ID, "" + rule.getId()));
		queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_RULE_DELAY, "" + rule.getDelay()));
		
		Set<DeviceAlert> deviceAlerts = rule.getDeviceAlerts();
		for (DeviceAlert deviceAlert : deviceAlerts) {
			queue.add(new KeyValuePair(Constants.DEVICE_ID, deviceAlert.getDevice().getGeneratedDeviceId()));
			//sumit start: ZXT120
			String alertEvent = deviceAlert.getAlertType().getAlertCommand();
			queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ALERT_EVENT, alertEvent));
			if(alertEvent.equalsIgnoreCase(Constants.TEMPERATURE_CHANGE) || alertEvent.equalsIgnoreCase(Constants.LUXLEVEL_CHANGE)||alertEvent.equalsIgnoreCase(Constants.PMLEVEL_CHANGE)){
				String deviceSpecificAlertParams = deviceAlert.getComparatorName();
				String deviceSpecificAlert = "";
				if(!deviceSpecificAlertParams.equals(null) && deviceSpecificAlertParams != "" && deviceSpecificAlertParams != "0-0"){
					String[] devcSpecificAlert = deviceSpecificAlertParams.split("-");
					long devcSpecificAlertId = Long.parseLong(devcSpecificAlert[0]);
					if(devcSpecificAlertId == 1){
						deviceSpecificAlert = "ET";//Constants.EQUAL_TO;
					}else if(devcSpecificAlertId == 2){
						deviceSpecificAlert = "LT";//Constants.LESS_THAN;
					}else if(devcSpecificAlertId == 3){
						deviceSpecificAlert = "GT";//Constants.GREATER_THAN;
					}
					queue.add(new KeyValuePair(deviceSpecificAlert, devcSpecificAlert[1]));
	
				}
			}			
			//sumit end: ZXT120
			long StartTime=deviceAlert.getStartTime();
			long StartHour=(int)StartTime/60;
			long StartMin=StartTime-(StartHour*60);
			
			queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_START_TIME, ""+StartHour+" "+StartMin));
			long EndTime=deviceAlert.getEndTime();
			long EndtHour=(int)EndTime/60;
			long EndMin=EndTime-(EndtHour*60);
			
			queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_END_TIME,""+EndtHour+" "+EndMin));
			queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_COMFORT_SECURITY,""+rule.getMode())); //vibhu added
		}
		
		Set<ImvgAlertsAction> imvgAlertsActions = rule.getImvgAlertsActions();
		Agent agent = rule.getGateWay().getAgent();
		String actionDefinition = "";
		//String actionDefinitionForCamera = "";
		List<String> actionDef = new ArrayList<String>(25);			//Max 25 actions allowed per rule
		//List<String> actionDefForCamera = new ArrayList<String>(2);	//Max 2 Cameras allowed per rule

		for (ImvgAlertsAction imvgAlertsAction : imvgAlertsActions) {
			/*String command = imvgAlertsAction.getActionType().getCommand();
			if(command.equalsIgnoreCase("CAPTURE_IMAGE")){			//If it is for camera append it at the last of the message.
				LogUtil.info("s0");
				actionDefinitionForCamera = imvgAlertsAction.getDevice().getGeneratedDeviceId().substring(18);
				LogUtil.info("s1");
				actionDefinitionForCamera += ";" + imvgAlertsAction.getAfterDelay();
				device=imvgAlertsAction.getDevice();
				UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(imvgAlertsAction.getActionType().getCommand());
				Class<?> className = updatorModel.getClassName();
				try {
					ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
					ActionDataHolder actionDataHolder = new ActionDataHolder();
					actionDataHolder.setLevel(imvgAlertsAction.getLevel());
					actionDataHolder.setGateWay(gateWay);
					actionDataHolder.setDevice(device);
					List<KeyValuePair> imvgConfigParams = controllerAction.createImvgConfigParams(actionDataHolder);
					if(!imvgConfigParams.isEmpty()){
						for(KeyValuePair i : imvgConfigParams){
							LogUtil.info(i.getKey()+", "+i.getValue());
							actionDefinitionForCamera += ";" + i.getKey() + "=" + i.getValue();
						}	
					}
					actionDefForCamera.add(actionDefinitionForCamera);
				} catch (InstantiationException e) {
					LogUtil.info("1.Got Exception: ", e);
				} catch (IllegalAccessException e) {
					LogUtil.info("2.Got Exception; ", e);
				}
			}else{
*/
				actionDefinition = imvgAlertsAction.getDevice().getGeneratedDeviceId().substring(18);
				actionDefinition += ";" + imvgAlertsAction.getAfterDelay();
				device=imvgAlertsAction.getDevice();
				UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(imvgAlertsAction.getActionType().getCommand());
				Class<?> className = updatorModel.getClassName();
				try {
					ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
					ActionDataHolder actionDataHolder = new ActionDataHolder();
					actionDataHolder.setLevel(imvgAlertsAction.getLevel());
					actionDataHolder.setGateWay(gateWay);
					actionDataHolder.setDevice(device);
					List<KeyValuePair> imvgConfigParams = controllerAction.createImvgConfigParams(actionDataHolder);
					if(!imvgConfigParams.isEmpty()){
						for(KeyValuePair i : imvgConfigParams){
							
							actionDefinition += ";" + i.getKey() + "=" + i.getValue();
						}	
					}
					//queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF,actionDefinition));
					actionDef.add(actionDefinition);
				} catch (InstantiationException e) {
					LogUtil.info("1.Got Exception: ", e);
				} catch (IllegalAccessException e) {
					LogUtil.info("2.Got Exception; ", e);
				}
			//}
		}
		try {
			for(String s1 : actionDef)
				queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF, s1));
			
			/*for(String s2 : actionDefForCamera)
				queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF, s2));*/
		} catch (NullPointerException e) {
			LogUtil.info("Got Exception: ", e);
		}
		
		this.actionModel.setQueue(queue);
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		IMonitorSession.put(transactionId, this);
		requestProcessor.processRequest(messageInXml, ip , port);
		return null;
	}
/*	sumit commented

	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Device device=null;
		Rule rule = (Rule) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.MODIFY_RULE));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		GateWay gateWay = rule.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_RULE_ID, "" + rule.getId()));
		queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_RULE_DELAY, "" + rule.getDelay()));
		
		Set<DeviceAlert> deviceAlerts = rule.getDeviceAlerts();
		for (DeviceAlert deviceAlert : deviceAlerts) {
			queue.add(new KeyValuePair(Constants.DEVICE_ID, deviceAlert.getDevice().getGeneratedDeviceId()));
			//sumit start: ZXT120
			String alertEvent = deviceAlert.getAlertType().getAlertCommand();
			queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ALERT_EVENT, alertEvent));
			if(alertEvent.equalsIgnoreCase(Constants.TEMPERATURE_CHANGE)){
				String deviceSpecificAlertParams = deviceAlert.getComparatorName();
				String deviceSpecificAlert = "";
				if(!deviceSpecificAlertParams.equals(null) && deviceSpecificAlertParams != "" && deviceSpecificAlertParams != "0-0"){
					String[] devcSpecificAlert = deviceSpecificAlertParams.split("-");
					long devcSpecificAlertId = Long.parseLong(devcSpecificAlert[0]);
					if(devcSpecificAlertId == 1){
						deviceSpecificAlert = "ET";//Constants.EQUAL_TO;
					}else if(devcSpecificAlertId == 2){
						deviceSpecificAlert = "LT";//Constants.LESS_THAN;
					}else if(devcSpecificAlertId == 3){
						deviceSpecificAlert = "GT";//Constants.GREATER_THAN;
					}
					queue.add(new KeyValuePair(deviceSpecificAlert, devcSpecificAlert[1]));
	
				}
			}			
			//sumit end: ZXT120
			long StartTime=deviceAlert.getStartTime();
			long StartHour=(int)StartTime/60;
			long StartMin=StartTime-(StartHour*60);
			
			queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_START_TIME, ""+StartHour+" "+StartMin));
			long EndTime=deviceAlert.getEndTime();
			long EndtHour=(int)EndTime/60;
			long EndMin=EndTime-(EndtHour*60);
			
			queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_END_TIME,""+EndtHour+" "+EndMin));
			queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_COMFORT_SECURITY,""+rule.getMode())); //vibhu added
		}
		
		Set<ImvgAlertsAction> imvgAlertsActions = rule.getImvgAlertsActions();
		Agent agent = rule.getGateWay().getAgent();
		String actionDefinition = "";
		String actionDefinitionForCamera = "";
		List<String> actionDef = new ArrayList<String>(25);			//Max 25 actions allowed per rule
		List<String> actionDefForCamera = new ArrayList<String>(2);	//Max 2 Cameras allowed per rule

		for (ImvgAlertsAction imvgAlertsAction : imvgAlertsActions) {
			String command = imvgAlertsAction.getActionType().getCommand();
			if(command.equalsIgnoreCase("CAPTURE_IMAGE")){			//If it is for camera append it at the last of the message.
				LogUtil.info("s0");
				actionDefinitionForCamera = imvgAlertsAction.getDevice().getGeneratedDeviceId().substring(18);
				LogUtil.info("s1");
				actionDefinitionForCamera += ";" + imvgAlertsAction.getAfterDelay();
				device=imvgAlertsAction.getDevice();
				UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(imvgAlertsAction.getActionType().getCommand());
				Class<?> className = updatorModel.getClassName();
				try {
					ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
					ActionDataHolder actionDataHolder = new ActionDataHolder();
					actionDataHolder.setLevel(imvgAlertsAction.getLevel());
					actionDataHolder.setGateWay(gateWay);
					actionDataHolder.setDevice(device);
					List<KeyValuePair> imvgConfigParams = controllerAction.createImvgConfigParams(actionDataHolder);
					if(!imvgConfigParams.isEmpty()){
						for(KeyValuePair i : imvgConfigParams){
							LogUtil.info(i.getKey()+", "+i.getValue());
							actionDefinitionForCamera += ";" + i.getKey() + "=" + i.getValue();
						}	
					}
					actionDefForCamera.add(actionDefinitionForCamera);
				} catch (InstantiationException e) {
					LogUtil.info("1.Got Exception: ", e);
				} catch (IllegalAccessException e) {
					LogUtil.info("2.Got Exception; ", e);
				}
			}else{
				LogUtil.info("s2");
				actionDefinition = imvgAlertsAction.getDevice().getGeneratedDeviceId().substring(18);
				LogUtil.info("s3");
				actionDefinition += ";" + imvgAlertsAction.getAfterDelay();
				device=imvgAlertsAction.getDevice();
				UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(imvgAlertsAction.getActionType().getCommand());
				Class<?> className = updatorModel.getClassName();
				try {
					ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
					ActionDataHolder actionDataHolder = new ActionDataHolder();
					actionDataHolder.setLevel(imvgAlertsAction.getLevel());
					actionDataHolder.setGateWay(gateWay);
					actionDataHolder.setDevice(device);
					List<KeyValuePair> imvgConfigParams = controllerAction.createImvgConfigParams(actionDataHolder);
					if(!imvgConfigParams.isEmpty()){
						for(KeyValuePair i : imvgConfigParams){
							LogUtil.info(i.getKey()+", "+i.getValue());
							actionDefinition += ";" + i.getKey() + "=" + i.getValue();
						}	
					}
					//queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF,actionDefinition));
					actionDef.add(actionDefinition);
				} catch (InstantiationException e) {
					LogUtil.info("1.Got Exception: ", e);
				} catch (IllegalAccessException e) {
					LogUtil.info("2.Got Exception; ", e);
				}
			}
		}
		try {
			for(String s1 : actionDef)
				queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF, s1));
			
			for(String s2 : actionDefForCamera)
				queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF, s2));
		} catch (NullPointerException e) {
			LogUtil.info("Got Exception: ", e);
		}
		
		this.actionModel.setQueue(queue);
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		IMonitorSession.put(transactionId, this);
		requestProcessor.processRequest(messageInXml, ip , port);
		return null;
	}*/
	
// **************************************************************** sumit end *****************************************************************
	
	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeFailureResults(java.util.Queue)
	 */
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
//		If it is a failure results, pass it. Nothing more to do...
		Rule rule = (Rule) this.actionModel.getData();
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		if(tId != null)IMonitorSession.remove(tId);
		this.actionModel.setMessage("Update Rule with name '" + rule.getName() + "' is failed. The reason is " + IMonitorUtil.commandId(queue, Constants.FAILURE_REASON));
		this.resultExecuted = true;
		this.actionSuccess = false;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeSuccessResults(java.util.Queue)
	 */
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
//		If success, then update the rule.
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		Rule rule = (Rule) this.actionModel.getData();
		this.actionModel.setMessage("Rule with name '" + rule.getName() + "' is updated");
//		RuleManager ruleManager = new RuleManager();
//		boolean result = ruleManager.updateRuleWithAllDetails(rule);
//		if(result){
//		}else{
//			this.actionModel.setMessage("Rule with name '" + rule.getName() + "' is updated in iMVG, but CMS we coulnd't, please contact Customer Care...");
//		}
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
