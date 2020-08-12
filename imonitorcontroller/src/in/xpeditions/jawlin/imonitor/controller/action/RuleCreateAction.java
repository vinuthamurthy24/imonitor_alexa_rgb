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
import in.xpeditions.jawlin.imonitor.controller.dao.manager.RuleManager;
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

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class RuleCreateAction implements ImonitorControllerAction {
	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess = false;
	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeCommand(in.xpeditions.jawlin.imonitor.controller.action.ActionModel)
	 */
	@Override
	public String executeCommand(ActionModel actionModel) {
		Device device=null;
		List<KeyValuePair> CaptureList = new ArrayList<KeyValuePair>();
		this.actionModel = actionModel;
		XStream stream = new XStream();
		Rule rule = (Rule) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.CREATE_RULE));
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
			if(alertEvent.equalsIgnoreCase(Constants.TEMPERATURE_CHANGE) || alertEvent.equalsIgnoreCase(Constants.LUXLEVEL_CHANGE) || alertEvent.equalsIgnoreCase(Constants.PWR_LMT_WARNING) || alertEvent.equalsIgnoreCase(Constants.PWR_LMT_REACHED)|| alertEvent.equalsIgnoreCase(Constants.PMLEVEL_CHANGE) ){
				String deviceSpecificAlertParams = deviceAlert.getComparatorName();
				//LogUtil.info("deviceSpecificAlertParams: "+deviceSpecificAlertParams);
				String deviceSpecificAlert = "";
				if(!deviceSpecificAlertParams.equals(null) && deviceSpecificAlertParams != "" && deviceSpecificAlertParams != "0-0"){
					String[] devcSpecificAlert = deviceSpecificAlertParams.split("-");
					long devcSpecificAlertId = Long.parseLong(devcSpecificAlert[0]);
					if(alertEvent.equalsIgnoreCase(Constants.PWR_LMT_WARNING) || alertEvent.equalsIgnoreCase(Constants.PWR_LMT_REACHED))
					{
						deviceSpecificAlert = "ET";	
					}
					
					
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
		
		for (ImvgAlertsAction imvgAlertsAction : imvgAlertsActions) 
		{	
			String Command=imvgAlertsAction.getActionType().getCommand();
			/*if(Command.equals("CAPTURE_IMAGE"))
			{
				CaptureList.add(new KeyValuePair(Constants.ACTION_DEF,Constants.START));
				CaptureList.add(new KeyValuePair(Constants.ACT_DEVICE_ID,imvgAlertsAction.getDevice().getGeneratedDeviceId()));	
				device=imvgAlertsAction.getDevice();
				CaptureList.add(new KeyValuePair(Constants.EXECUTE_DELAY, ""+imvgAlertsAction.getAfterDelay()));
				CaptureList.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
				
				UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(imvgAlertsAction.getActionType().getCommand());
				Class<?> className = updatorModel.getClassName();
				try {
					ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
					ActionDataHolder actionDataHolder = new ActionDataHolder();
					actionDataHolder.setLevel(imvgAlertsAction.getLevel());
					actionDataHolder.setGateWay(gateWay);
					actionDataHolder.setDevice(device);
				
					List<KeyValuePair> imvgConfigParams = controllerAction.createImvgConfigParams(actionDataHolder);
					CaptureList.addAll(imvgConfigParams);
				
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				CaptureList.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
				CaptureList.add(new KeyValuePair(Constants.ACTION_DEF,Constants.END));
			}
			else
			{	*/
			
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
				LogUtil.info("2.Got Exception: ", e);
			}
			}
		//}
		//queue.addAll(CaptureList);
		try {
			for(String s1 : actionDef)
				queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF, s1));
			
			/*for(String s2 : actionDefForCamera)
				queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF, s2));*/
		} catch (NullPointerException e) {
			LogUtil.info(e.getMessage(), e);
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

	/* sumit commented: Need to optimize the message
	 * @Override
	public String executeCommand(ActionModel actionModel) {
		Device device=null;
		List<KeyValuePair> CaptureList = new ArrayList<KeyValuePair>();
		this.actionModel = actionModel;
		Rule rule = (Rule) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.CREATE_RULE));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		GateWay gateWay = rule.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.RULE_ID, "" + rule.getId()));
		queue.add(new KeyValuePair(Constants.RULE_DELAY, "" + rule.getDelay()));
		
		Set<DeviceAlert> deviceAlerts = rule.getDeviceAlerts();
		for (DeviceAlert deviceAlert : deviceAlerts) {
			queue.add(new KeyValuePair(Constants.DEVICE_ID, deviceAlert.getDevice().getGeneratedDeviceId()));
			
			//sumit start: ZXT120
			String alertEvent = deviceAlert.getAlertType().getAlertCommand();
			queue.add(new KeyValuePair(Constants.ALERT_EVENT, alertEvent));
			if(alertEvent.equalsIgnoreCase(Constants.TEMPERATURE_CHANGE)){
				String deviceSpecificAlertParams = deviceAlert.getComparatorName();
				//LogUtil.info("deviceSpecificAlertParams: "+deviceSpecificAlertParams);
				String deviceSpecificAlert = "";
				if(!deviceSpecificAlertParams.equals(null) && deviceSpecificAlertParams != "" && deviceSpecificAlertParams != "0-0"){
					String[] devcSpecificAlert = deviceSpecificAlertParams.split("-");
					long devcSpecificAlertId = Long.parseLong(devcSpecificAlert[0]);
					if(devcSpecificAlertId == 1){
						deviceSpecificAlert = "EQUAL_TO";//Constants.EQUAL_TO;
					}else if(devcSpecificAlertId == 2){
						deviceSpecificAlert = "LESS_THAN";//Constants.LESS_THAN;
					}else if(devcSpecificAlertId == 3){
						deviceSpecificAlert = "GREATER_THAN";//Constants.GREATER_THAN;
					}
					queue.add(new KeyValuePair(deviceSpecificAlert, devcSpecificAlert[1]));
	
				}
			}
						
			//sumit end: ZXT120
			long StartTime=deviceAlert.getStartTime();
			long StartHour=(int)StartTime/60;
			long StartMin=StartTime-(StartHour*60);
			queue.add(new KeyValuePair("START_TIME", ""+StartHour+" "+StartMin));
			long EndTime=deviceAlert.getEndTime();
			long EndtHour=(int)EndTime/60;
			long EndMin=EndTime-(EndtHour*60);
			queue.add(new KeyValuePair("END_TIME",""+EndtHour+" "+EndMin));
			queue.add(new KeyValuePair(Constants.COMFORT_SECURITY,""+rule.getMode())); //vibhu added
		}
		Set<ImvgAlertsAction> imvgAlertsActions = rule.getImvgAlertsActions();
		
		Agent agent = rule.getGateWay().getAgent();
		for (ImvgAlertsAction imvgAlertsAction : imvgAlertsActions) 
		{	
			String Command=imvgAlertsAction.getActionType().getCommand();
			if(Command.equals("CAPTURE_IMAGE"))
			{
				CaptureList.add(new KeyValuePair(Constants.ACTION_DEF,Constants.START));
				CaptureList.add(new KeyValuePair(Constants.ACT_DEVICE_ID,imvgAlertsAction.getDevice().getGeneratedDeviceId()));	
				device=imvgAlertsAction.getDevice();
				CaptureList.add(new KeyValuePair(Constants.EXECUTE_DELAY, ""+imvgAlertsAction.getAfterDelay()));
				CaptureList.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
				
				UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(imvgAlertsAction.getActionType().getCommand());
				Class<?> className = updatorModel.getClassName();
				try {
					ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
					ActionDataHolder actionDataHolder = new ActionDataHolder();
					actionDataHolder.setLevel(imvgAlertsAction.getLevel());
					actionDataHolder.setGateWay(gateWay);
					actionDataHolder.setDevice(device);
				
					List<KeyValuePair> imvgConfigParams = controllerAction.createImvgConfigParams(actionDataHolder);
					CaptureList.addAll(imvgConfigParams);
				
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				CaptureList.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
				CaptureList.add(new KeyValuePair(Constants.ACTION_DEF,Constants.END));
			}
			else
			{	
			queue.add(new KeyValuePair(Constants.ACTION_DEF,Constants.START));	
			queue.add(new KeyValuePair(Constants.ACT_DEVICE_ID,imvgAlertsAction.getDevice().getGeneratedDeviceId()));	
			device=imvgAlertsAction.getDevice();
			queue.add(new KeyValuePair(Constants.EXECUTE_DELAY, ""+imvgAlertsAction.getAfterDelay()));
			queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
			UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(imvgAlertsAction.getActionType().getCommand());
			Class<?> className = updatorModel.getClassName();
			try {
				ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
				ActionDataHolder actionDataHolder = new ActionDataHolder();
				actionDataHolder.setLevel(imvgAlertsAction.getLevel());
				actionDataHolder.setGateWay(gateWay);
				actionDataHolder.setDevice(device);
			
				List<KeyValuePair> imvgConfigParams = controllerAction.createImvgConfigParams(actionDataHolder);
				queue.addAll(imvgConfigParams);
				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
			queue.add(new KeyValuePair(Constants.ACTION_DEF,Constants.END));
			}
		}
		queue.addAll(CaptureList);
		this.actionModel.setQueue(queue);
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		IMonitorSession.put(transactionId, this);
		requestProcessor.processRequest(messageInXml, ip , port);
		return null;
	}*/

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeFailureResults(java.util.Queue)
	 */
	
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
//		If it is a failure results, we should remove the saved rules from db.
		Rule rule = (Rule) this.actionModel.getData();
		RuleManager ruleManager = new RuleManager();
		//vibhu commented
		//ruleManager.deleteRuleWithAllDetails(rule);
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.actionModel.setMessage("Create Rule with name '" + rule.getName() + "' is failed. The reason is " + IMonitorUtil.commandId(queue, Constants.FAILURE_REASON));
		this.resultExecuted = true;
		this.actionSuccess = false;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeSuccessResults(java.util.Queue)
	 */
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
//		If success, let's pass it. We already saved the data.
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		Rule rule = (Rule) this.actionModel.getData();
		this.actionModel.setMessage("Rule with name '" + rule.getName() + "' is created");
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
	public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder) {
		return null;
	}


	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}

}
