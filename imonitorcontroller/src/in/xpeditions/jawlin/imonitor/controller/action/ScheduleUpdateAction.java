/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScheduleAction;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;
import in.xpeditions.jawlin.imonitor.util.UpdatorModel;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * @author Coladi
 *
 */
public class ScheduleUpdateAction implements ImonitorControllerAction {

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
		Schedule schedule = (Schedule) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.MODIFY_SCHEDULE));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		GateWay gateWay = schedule.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.SCHEDULE_SPECIFIC_SCHEDULE_ID, "" + schedule.getId()));
		queue.add(new KeyValuePair(Constants.SCHEDULE_NAME, "" + schedule.getName()));
		queue.add(new KeyValuePair(Constants.SCHEDULE_SPECIFIC_SCHEDULE_TIME, formatScheduleTime(schedule.getScheduleTime())));
		Set<ScheduleAction> scheduleActions = schedule.getScheduleActions();
		Agent agent = schedule.getGateWay().getAgent();
		
		String actionDefinition = "";
		List<String> actionDef = new ArrayList<String>(25);			//Max 25 Actions per Schedule
		for (ScheduleAction scheduleAction : scheduleActions) {
			//sumit start: HANDLE VIRTUAL DEVICES UPDATE.
			String generatedDeviceId = scheduleAction.getDevice().getGeneratedDeviceId();
			if((generatedDeviceId.contains("HOME")) || (generatedDeviceId.contains("STAY"))
					|| (generatedDeviceId.contains("AWAY"))){
				actionDefinition = scheduleAction.getDevice().getGeneratedDeviceId().substring(18);
				actionDefinition += ";VIR_DEV=1";
				actionDef.add(actionDefinition);
			}else{	
				actionDefinition = scheduleAction.getDevice().getGeneratedDeviceId().substring(18);
				device = scheduleAction.getDevice();
				String actionName = scheduleAction.getActionType().getCommand();
				
				if(actionName.equals(Constants.NEIGHBOUR_UPDATE)){
					actionDefinition += ";" + Constants.NEIGHBOUR_UPDATE + "=1";
					actionDef.add(actionDefinition);
				}else{
				UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(scheduleAction.getActionType().getCommand());
				Class<?> className = updatorModel.getClassName();
				try {
					ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
					ActionDataHolder actionDataHolder = new ActionDataHolder();
					actionDataHolder.setLevel(scheduleAction.getLevel());
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
	
/*	sumit commented: [Sept 06, 2012] Original code
 * @Override
	public String executeCommand(ActionModel actionModel) {
		Device device=null;
		this.actionModel = actionModel;
		Schedule schedule = (Schedule) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.MODIFY_SCHEDULE));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		GateWay gateWay = schedule.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.SCHEDULE_ID, "" + schedule.getId()));
		queue.add(new KeyValuePair(Constants.SCHEDULE_TIME, schedule.getScheduleTime()));
		Set<ScheduleAction> scheduleActions = schedule.getScheduleActions();
		Agent agent = schedule.getGateWay().getAgent();
		for (ScheduleAction scheduleAction : scheduleActions) {
			queue.add(new KeyValuePair(Constants.ACTION_DEF,Constants.START));
			//sumit start: HANDLE VIRTUAL DEVICES UPDATE.
			String generatedDeviceId = scheduleAction.getDevice().getGeneratedDeviceId();
			queue.add(new KeyValuePair(Constants.ACT_DEVICE_ID,generatedDeviceId));
			
			if((!generatedDeviceId.contains("HOME")) && (!generatedDeviceId.contains("STAY"))
					&& (!generatedDeviceId.contains("AWAY"))){
				queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
				device = scheduleAction.getDevice();
				UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(scheduleAction.getActionType().getCommand());
				Class<?> className = updatorModel.getClassName();
				try {
					ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
					ActionDataHolder actionDataHolder = new ActionDataHolder();
					actionDataHolder.setLevel(scheduleAction.getLevel());
					actionDataHolder.setGateWay(gateWay);
					actionDataHolder.setDevice(device);
					List<KeyValuePair> imvgConfigParams = controllerAction.createImvgConfigParams(actionDataHolder);
					if(imvgConfigParams != null)
						queue.addAll(imvgConfigParams);
				} catch (InstantiationException e) {
					e.printStackTrace();
					LogUtil.info(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					LogUtil.info(e.getMessage(), e);
				}
				queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
			}
			queue.add(new KeyValuePair(Constants.ACTION_DEF,Constants.END));
		}
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
//		If it is a failure results, pass it. Nothing more to do...
	//	Schedule schedule = (Schedule) this.actionModel.getData();
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
	//	Schedule schedule = (Schedule) this.actionModel.getData();
		String message = "msg.controller.schedules.0023";
		this.actionModel.setMessage(message);
//		ScheduleManager scheduleManager = new ScheduleManager();
//		boolean result = scheduleManager.updateScheduleWithDetails(schedule);
//		if(result){
//		}else{
//			this.actionModel.setMessage("Rule with name '" + schedule.getName() + "' is updated in iMVG, but CMS we coulnd't, please contact Customer Care...");
//		}
		this.resultExecuted = true;
		this.actionSuccess = true;
		return null;
	}
	
	private String formatScheduleTime(String time)
	{
		String result = "";
		if( time == null)return result;
		
		String arr1[] = time.split(" ");
		if(arr1.length == 5)
		{
			result += arr1[0]+" "+arr1[1];
			
			int lowerLimit = 1;
			for(int i=2;i<5;i++)
			{
				long val = 0;
				if(i==3)lowerLimit = 0;
				if(arr1[i].equals("*"))
				{
					if(i==2)val = 2147483647L;
					else if (i==3)val = 4095L;
					else if (i==4)val = 127L;
				}
				else
				{
					String arr2[] = arr1[i].split(",");
					for (String s : arr2)
					{
						int j = Integer.parseInt(s);
						val ^= 1<<(j-lowerLimit); 
					}
				}
				result += " "+val;
			}
			
		}
		return result;
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
