/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;


import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.action.RuleCreateAction;
import in.xpeditions.jawlin.imonitor.controller.action.RuleDeleteAction;
import in.xpeditions.jawlin.imonitor.controller.action.RuleUpdateAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgSecurityActions;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.RuleManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class RuleServiceManager {
	
	// ********************************************************** sumit start ******************************************************
	public String getRuleCountForDevice(String Id,String xml){
		String result = "NO_RULE_EXISTS";
		XStream xStream = new XStream();
		Long id = (Long) xStream.fromXML(Id);
		String genDevcId = (String) xStream.fromXML(xml);
		RuleManager ruleManager = new RuleManager();
		long count = ruleManager.getRuleCountByGeneratedDeviceId(id, genDevcId);
		if(count > 0){
			result = "RULE_EXISTS";
		}
		return result;
	}
	// *********************************************************** sumit end ******************************************************

	public String saveRuleWithAllDetails(String ruleXml){
		XStream xStream  = new XStream();
		Rule rule = (Rule) xStream.fromXML(ruleXml);
		//vibhu start
		String  error = verifyRuleDetails(rule);
		if(error != null)return error;
		//vibhu end
		RuleManager ruleManager = new RuleManager();
		long lastSavedStart=0;
		long lastSavedEnd=0;
		long NwStart=0;
		long NwEnd=0;
		String rov="msg001rov";
		String comparatorName = "";
		Device devicetset=null;
		AlertType alerttype=null;
		
		String result = "msg.controller.rules.0006"+Constants.MESSAGE_DELIMITER_1 + rule.getName();
		Set<DeviceAlert> deviceAlerts = rule.getDeviceAlerts();
		
		
		for (DeviceAlert deviceAlert : deviceAlerts)
		{
			devicetset=deviceAlert.getDevice();
			alerttype=deviceAlert.getAlertType();
			NwStart=deviceAlert.getStartTime();
			NwEnd=deviceAlert.getEndTime();
			comparatorName = deviceAlert.getComparatorName();
		}
		
		//LogUtil.info("alerttype---"+xStream.toXML(alerttype));
		//LogUtil.info("alerttype.getName()---"+alerttype.getName());
		if((alerttype.getName().contentEquals("power limit reached")) || (alerttype.getName().contentEquals("power limit warning")))
		{
		//	LogUtil.info("inside if");
			List<Object[]> energyrule=ruleManager.checkRuleCountForEnergy(devicetset,alerttype,rule.getId());
		//	LogUtil.info("energyrule---"+energyrule);
			if(!energyrule.isEmpty())
			{
				result = "Energy.rulecreate";	
				
			}
			else
			{
			//	LogUtil.info("rules not there save");
	/*List<Object[]> objects=ruleManager.checkRuleStartAndEndTime(devicetset,alerttype,rule.getId());
		
		if(!objects.isEmpty())
		{
		for (Object[] object : objects) 
		{
			lastSavedStart=(Long)object[0];
			lastSavedEnd=(Long)object[1];
			
			
	
			if(lastSavedEnd>=lastSavedStart)
			{
				if((NwEnd>=lastSavedEnd && NwStart>=lastSavedEnd)||(NwEnd<=lastSavedStart && NwStart<=lastSavedStart)||((NwEnd<NwStart) && (NwEnd<=lastSavedStart)&&(NwStart>lastSavedEnd)))
				{
					continue;
				}
				else
				{
					Rule dbrule = (Rule) object[2];
					int StartHour=(int) (lastSavedStart/60);
					int Startmin=(int) (lastSavedStart-(StartHour*60));
					int EndHour=(int) (lastSavedEnd/60);
					int Endmin=(int) (lastSavedEnd-(EndHour*60));
					result=IMonitorUtil.createMessage(rov,dbrule.getName(),StartHour,Startmin,EndHour,Endmin);
					
					int NowStartHour=(int) (NwStart/60);
					int NowStartmin=(int) (NwStart-(NowStartHour*60));
					int NowEndHour=(int) (NwEnd/60);
					int NowEndmin=(int) (NwEnd-(NowEndHour*60));
					result=IMonitorUtil.createMessage(rov,dbrule.getName(),StartHour,Startmin,EndHour,Endmin,NowStartHour,NowStartmin,NowEndHour,NowEndmin);
					//result = "Rule is OverLaping With Rule: " +dbrule.getName() ;
					return result;
				}
			}
			if(lastSavedEnd<=lastSavedStart)
			{
				if((NwStart >= lastSavedEnd) && (NwEnd <=lastSavedStart))
				{
					continue;
				}
				else
				{
					Rule dbrule = (Rule) object[2];
					int StartHour=(int) (lastSavedStart/60);
					int Startmin=(int) (lastSavedStart-(StartHour*60));
					int EndHour=(int) (lastSavedEnd/60);
					int Endmin=(int) (lastSavedEnd-(EndHour*60));
					
					int NowStartHour=(int) (NwStart/60);
					int NowStartmin=(int) (NwStart-(NowStartHour*60));
					int NowEndHour=(int) (NwEnd/60);
					int NowEndmin=(int) (NwEnd-(NowEndHour*60));
					result=IMonitorUtil.createMessage(rov,dbrule.getName(),StartHour,Startmin,EndHour,Endmin,NowStartHour,NowStartmin,NowEndHour,NowEndmin);
					
					//result=IMonitorUtil.createMessage(rov,dbrule.getName(),StartHour,Startmin,EndHour,Endmin);
					
					//result = "Rule is OverLaping With Rule: " +dbrule.getName() ;
					return result;
				}
			}
		}
		}*/
		
		
		
		
		if(ruleManager.saveRuleWithAllDetails(rule)){
				//Send the Create-Rule Command to iMVG only if there are some actions.
				rule = ruleManager.retrieveRuleDetails(rule.getId());
				try{
					//vibhu adding if-else for condition rule.getImvgAlertsActions() != null...
					if(rule.getImvgAlertsActions() != null && rule.getImvgAlertsActions().size() > 0 )
					{
				ActionModel actionModel = new ActionModel(null, rule);
				ImonitorControllerAction ruleCreateAction = new RuleCreateAction();
				ruleCreateAction.executeCommand(actionModel);
				boolean resultFromImvg = IMonitorUtil.waitForResult(ruleCreateAction);
						if(resultFromImvg && ruleCreateAction.isActionSuccess())
						{
					result = "msg.controller.rules.0008";
				}else{
					result = "msg.controller.rules.0007"
							+ Constants.MESSAGE_DELIMITER_1 + rule.getName() 
							+ Constants.MESSAGE_DELIMITER_2 + ruleCreateAction.getActionModel().getMessage();
					
					//result = "msg.controller.rules.0007" + Constants.MESSAGE_DELIMITER_1 + rule.getName() + Constants.MESSAGE_DELIMITER_2 + ruleCreateAction.getActionModel().getMessage() +"'";
							//vibhu start
							ruleManager.deleteRuleWithAllDetails(rule);
							//ruleCreateAction.executeFailureResults(actionModel.getQueue());
							//vibhu end
						}
					}
					else
					{
						result = "msg.controller.rules.0008";
					}
				}
				catch(Exception e)
				{
					LogUtil.info("Some serious error while trying to save rule. Delete rule if created.",e);
					try{ruleManager.deleteRuleWithAllDetails(rule);}catch(Exception e1){}
				}
			}
			else{
				result = "msg.controller.rules.0009'"+ Constants.MESSAGE_DELIMITER_1  + rule.getName() + "'";
			}
			}
		}
		else
		{
			/*List<Object[]> objects=ruleManager.checkRuleStartAndEndTime(devicetset,alerttype,rule.getId());
				
				if(!objects.isEmpty())
				{
				for (Object[] object : objects) 
				{
					lastSavedStart=(Long)object[0];
					lastSavedEnd=(Long)object[1];
					
					
			
					if(lastSavedEnd>=lastSavedStart)
					{
						if((NwEnd>=lastSavedEnd && NwStart>=lastSavedEnd)||(NwEnd<=lastSavedStart && NwStart<=lastSavedStart)||((NwEnd<NwStart) && (NwEnd<=lastSavedStart)&&(NwStart>lastSavedEnd)))
						{
							continue;
						}
						else
						{
							Rule dbrule = (Rule) object[2];
							int StartHour=(int) (lastSavedStart/60);
							int Startmin=(int) (lastSavedStart-(StartHour*60));
							int EndHour=(int) (lastSavedEnd/60);
							int Endmin=(int) (lastSavedEnd-(EndHour*60));
							result=IMonitorUtil.createMessage(rov,dbrule.getName(),StartHour,Startmin,EndHour,Endmin);
							
							int NowStartHour=(int) (NwStart/60);
							int NowStartmin=(int) (NwStart-(NowStartHour*60));
							int NowEndHour=(int) (NwEnd/60);
							int NowEndmin=(int) (NwEnd-(NowEndHour*60));
							result=IMonitorUtil.createMessage(rov,dbrule.getName(),StartHour,Startmin,EndHour,Endmin,NowStartHour,NowStartmin,NowEndHour,NowEndmin);
							//result = "Rule is OverLaping With Rule: " +dbrule.getName() ;
							return result;
						}
					}
					if(lastSavedEnd<=lastSavedStart)
					{
						if((NwStart >= lastSavedEnd) && (NwEnd <=lastSavedStart))
						{
							continue;
						}
						else
						{
							Rule dbrule = (Rule) object[2];
							int StartHour=(int) (lastSavedStart/60);
							int Startmin=(int) (lastSavedStart-(StartHour*60));
							int EndHour=(int) (lastSavedEnd/60);
							int Endmin=(int) (lastSavedEnd-(EndHour*60));
							
							int NowStartHour=(int) (NwStart/60);
							int NowStartmin=(int) (NwStart-(NowStartHour*60));
							int NowEndHour=(int) (NwEnd/60);
							int NowEndmin=(int) (NwEnd-(NowEndHour*60));
							result=IMonitorUtil.createMessage(rov,dbrule.getName(),StartHour,Startmin,EndHour,Endmin,NowStartHour,NowStartmin,NowEndHour,NowEndmin);
							
							//result=IMonitorUtil.createMessage(rov,dbrule.getName(),StartHour,Startmin,EndHour,Endmin);
							
							//result = "Rule is OverLaping With Rule: " +dbrule.getName() ;
							return result;
						}
					}
				}
				}*/
				
				
				
			
				if(ruleManager.saveRuleWithAllDetails(rule)){
						//Send the Create-Rule Command to iMVG only if there are some actions.
						rule = ruleManager.retrieveRuleDetails(rule.getId());
						LogUtil.info("rule:"+xStream.toXML(rule));
						try{
							//vibhu adding if-else for condition rule.getImvgAlertsActions() != null...
							if(rule.getImvgAlertsActions() != null && rule.getImvgAlertsActions().size() > 0 )
							{
						ActionModel actionModel = new ActionModel(null, rule);
						ImonitorControllerAction ruleCreateAction = new RuleCreateAction();
						ruleCreateAction.executeCommand(actionModel);
						boolean resultFromImvg = IMonitorUtil.waitForResult(ruleCreateAction);
								if(resultFromImvg && ruleCreateAction.isActionSuccess())
								{
							result = "msg.controller.rules.0008";
						}else{
							result = "msg.controller.rules.0007"
									+ Constants.MESSAGE_DELIMITER_1 + rule.getName() 
									+ Constants.MESSAGE_DELIMITER_2 + ruleCreateAction.getActionModel().getMessage();
							
							//result = "msg.controller.rules.0007" + Constants.MESSAGE_DELIMITER_1 + rule.getName() + Constants.MESSAGE_DELIMITER_2 + ruleCreateAction.getActionModel().getMessage() +"'";
									//vibhu start
									ruleManager.deleteRuleWithAllDetails(rule);
									//ruleCreateAction.executeFailureResults(actionModel.getQueue());
									//vibhu end
								}
							}
							else
							{
								result = "msg.controller.rules.0008";
							}
						}
						catch(Exception e)
						{
							LogUtil.info("Some serious error while trying to save rule. Delete rule if created.",e);
							try{ruleManager.deleteRuleWithAllDetails(rule);}catch(Exception e1){}
						}
					}
					else{
						result = "msg.controller.rules.0009'"+ Constants.MESSAGE_DELIMITER_1  + rule.getName() + "'";
					}
						
		}
			
		return result;
	}
	
	
	public String updateRuleWithAllDetails(String ruleXml){
		XStream xStream  = new XStream();
		boolean resultFromImvg = false;
		Rule rule = (Rule) xStream.fromXML(ruleXml);
		//vibhu start
		String  error = verifyRuleDetails(rule);
		if(error != null)return error;
		//vibhu end		
		RuleManager ruleManager = new RuleManager();
		long lastSavedStart=0;
		long lastSavedEnd=0;
		long NwStart=0;
		long NwEnd=0;
		String comparatorName = "";
		Device devicetset=null;
		AlertType alerttype=null;
		String result = "msg.controller.rules.0010" + Constants.MESSAGE_DELIMITER_1 + rule.getName();
//		rule2 contains the present value
		Rule rule2 = ruleManager.retrieveRuleDetails(rule.getId());
//		rule contains the values to be updated.
		Set<DeviceAlert> deviceAlerts = rule.getDeviceAlerts();
		for(DeviceAlert deviceAlert : deviceAlerts) {
			devicetset=deviceAlert.getDevice();
			alerttype=deviceAlert.getAlertType();
			NwStart=deviceAlert.getStartTime();
			NwEnd=deviceAlert.getEndTime();
			comparatorName = deviceAlert.getComparatorName();
		}
		
		//LogUtil.info("alerttype---"+xStream.toXML(alerttype));
		//LogUtil.info("alerttype.getName()---"+alerttype.getName());
		if((alerttype.getName().contentEquals("power limit reached")) || (alerttype.getName().contentEquals("power limit warning")))
		{
			//LogUtil.info("inside if");
			AlertType retrivedalerttype=null;
			Device retriveddevicetset=null;
			Set<DeviceAlert> deviceAlerts1 = rule2.getDeviceAlerts();
			for(DeviceAlert deviceAlert : deviceAlerts1) {
				retriveddevicetset=deviceAlert.getDevice();
				retrivedalerttype=deviceAlert.getAlertType();
			}
			if(!retrivedalerttype.getName().equalsIgnoreCase(alerttype.getName()))
			{
			
			List<Object[]> energyrule=ruleManager.checkRuleCountForEnergy(devicetset,alerttype,rule.getId());
			//LogUtil.info("energyrule---"+energyrule);
			if(!energyrule.isEmpty())
			{
				result = "Energy.rulecreate";	
				//LogUtil.info("rules already exists cant save");
			}
			else
			{
		
		/*List<Object[]> objects=ruleManager.checkRuleStartAndEndTime(devicetset,alerttype,rule.getId());
		
		if(!objects.isEmpty())
		{
		for (Object[] object : objects) 
		{
			lastSavedStart=(Long)object[0];
			lastSavedEnd=(Long)object[1];
			
			

			if(lastSavedEnd>=lastSavedStart)
			{
				if((NwEnd>=lastSavedEnd && NwStart>=lastSavedEnd)||(NwEnd<=lastSavedStart && NwStart<=lastSavedStart)||((NwEnd<NwStart) && (NwEnd<=lastSavedStart)&&(NwStart>lastSavedEnd)))
				{
					continue;
				}
				else
				{
					Rule dbrule = (Rule) object[2];
					int StartHour=(int) (lastSavedStart/60);
					int Startmin=(int) (lastSavedStart-(StartHour*60));
					int EndHour=(int) (lastSavedEnd/60);
					int Endmin=(int) (lastSavedEnd-(EndHour*60));
					
					int NowStartHour=(int) (NwStart/60);
					int NowStartmin=(int) (NwStart-(NowStartHour*60));
					int NowEndHour=(int) (NwEnd/60);
					int NowEndmin=(int) (NwEnd-(NowEndHour*60));
					result=IMonitorUtil.createMessage("msg001rov",dbrule.getName(),StartHour,Startmin,EndHour,Endmin,NowStartHour,NowStartmin,NowEndHour,NowEndmin);
					
					return result;
				}
			}
			if(lastSavedEnd<=lastSavedStart)
			{
				if((NwStart >= lastSavedEnd) && (NwEnd <=lastSavedStart))
				{
					continue;
				}
				else
				{
					Rule dbrule = (Rule) object[2];
					int StartHour=(int) (lastSavedStart/60);
					int Startmin=(int) (lastSavedStart-(StartHour*60));
					int EndHour=(int) (lastSavedEnd/60);
					int Endmin=(int) (lastSavedEnd-(EndHour*60));
					
					int NowStartHour=(int) (NwStart/60);
					int NowStartmin=(int) (NwStart-(NowStartHour*60));
					int NowEndHour=(int) (NwEnd/60);
					int NowEndmin=(int) (NwEnd-(NowEndHour*60));
					result=IMonitorUtil.createMessage("msg001rov",dbrule.getName(),StartHour,Startmin,EndHour,Endmin,NowStartHour,NowStartmin,NowEndHour,NowEndmin);
					return result;
				}
			}
		}
		}*/
		if(ruleManager.updateRuleWithAllDetails(rule)){

				rule = ruleManager.retrieveRuleDetails(rule.getId());
	//			Send the Create-Rule Command to iMVG.
				ActionModel actionModel = new ActionModel(null, rule);
				//vibhu start
	//			ImonitorControllerAction ruleUpdateacton = new RuleUpdateAction();
				ImonitorControllerAction ruleUpdateacton = null;
				boolean actionsInOldRule = false;
				boolean actionsInNewRule = false;
				if(rule.getImvgAlertsActions() != null && rule.getImvgAlertsActions().size() > 0) actionsInNewRule = true;
				if(rule2.getImvgAlertsActions() != null && rule2.getImvgAlertsActions().size() > 0) actionsInOldRule = true;
				if(actionsInOldRule && actionsInNewRule)
				{
					ruleUpdateacton = new RuleUpdateAction();
				}
				else if(actionsInOldRule && !actionsInNewRule)
				{
					ruleUpdateacton = new RuleDeleteAction();
				}
				else if(!actionsInOldRule && actionsInNewRule)
				{
					ruleUpdateacton = new RuleCreateAction();
				}
				else
				{
					return "msg.controller.rules.0011";
				}
				//vibhu end
				try
				{
					ruleUpdateacton.executeCommand(actionModel);
					resultFromImvg = IMonitorUtil.waitForResult(ruleUpdateacton);
				}
				catch (Exception e)
				{
					LogUtil.info("Some serious error while trying to modify rule. Revert to origonal rule.", e);
					actionModel.setMessage("Internal Error while updating rule.");
					resultFromImvg = false;

				}
				
				if(resultFromImvg){
					result = "msg.controller.rules.0005" + Constants.MESSAGE_DELIMITER_1 + ruleUpdateacton.getActionModel().getMessage();
	//				If the update is a failure, then we should roll back the updated rule.
					if(!ruleUpdateacton.isActionSuccess()){
						boolean res = ruleManager.updateRuleWithAllDetails(rule2);
						if(!res){
							result = "msg.controller.rules.0013"+ Constants.MESSAGE_DELIMITER_1 + rule.getName()+ Constants.MESSAGE_DELIMITER_2 + ruleUpdateacton.getActionModel().getMessage();
						}
						}
					else
					{
						result = "msg.controller.rules.0011";
					}
				}else{
					result = "msg.controller.rules.0013"+ Constants.MESSAGE_DELIMITER_1 + rule.getName() + Constants.MESSAGE_DELIMITER_1 + "msg.controller.rules.0015"+ Constants.MESSAGE_DELIMITER_1 + ruleUpdateacton.getActionModel().getMessage();
					//ruleUpdateacton.executeFailureResults(ruleUpdateacton.getActionModel().getQueue());
					boolean res = ruleManager.updateRuleWithAllDetails(rule2);
					if(!res){
						result = "msg.controller.rules.0013"+ Constants.MESSAGE_DELIMITER_1 + rule.getName()+ Constants.MESSAGE_DELIMITER_2 + ruleUpdateacton.getActionModel().getMessage();
					}
				}

		}else{
			result = "msg.controller.rules.0010" + Constants.MESSAGE_DELIMITER_1+rule.getName();
		}
			}
		}
			else
			{
				
				/*List<Object[]> objects=ruleManager.checkRuleStartAndEndTime(devicetset,alerttype,rule.getId());
				
				if(!objects.isEmpty())
				{
				for (Object[] object : objects) 
				{
					lastSavedStart=(Long)object[0];
					lastSavedEnd=(Long)object[1];
					
					

					if(lastSavedEnd>=lastSavedStart)
					{
						if((NwEnd>=lastSavedEnd && NwStart>=lastSavedEnd)||(NwEnd<=lastSavedStart && NwStart<=lastSavedStart)||((NwEnd<NwStart) && (NwEnd<=lastSavedStart)&&(NwStart>lastSavedEnd)))
						{
							continue;
						}
						else
						{
							Rule dbrule = (Rule) object[2];
							int StartHour=(int) (lastSavedStart/60);
							int Startmin=(int) (lastSavedStart-(StartHour*60));
							int EndHour=(int) (lastSavedEnd/60);
							int Endmin=(int) (lastSavedEnd-(EndHour*60));
							
							int NowStartHour=(int) (NwStart/60);
							int NowStartmin=(int) (NwStart-(NowStartHour*60));
							int NowEndHour=(int) (NwEnd/60);
							int NowEndmin=(int) (NwEnd-(NowEndHour*60));
							result=IMonitorUtil.createMessage("msg001rov",dbrule.getName(),StartHour,Startmin,EndHour,Endmin,NowStartHour,NowStartmin,NowEndHour,NowEndmin);
							
							return result;
						}
					}
					if(lastSavedEnd<=lastSavedStart)
					{
						if((NwStart >= lastSavedEnd) && (NwEnd <=lastSavedStart))
						{
							continue;
						}
						else
						{
							Rule dbrule = (Rule) object[2];
							int StartHour=(int) (lastSavedStart/60);
							int Startmin=(int) (lastSavedStart-(StartHour*60));
							int EndHour=(int) (lastSavedEnd/60);
							int Endmin=(int) (lastSavedEnd-(EndHour*60));
							
							int NowStartHour=(int) (NwStart/60);
							int NowStartmin=(int) (NwStart-(NowStartHour*60));
							int NowEndHour=(int) (NwEnd/60);
							int NowEndmin=(int) (NwEnd-(NowEndHour*60));
							result=IMonitorUtil.createMessage("msg001rov",dbrule.getName(),StartHour,Startmin,EndHour,Endmin,NowStartHour,NowStartmin,NowEndHour,NowEndmin);
							return result;
						}
					}
				}
				}*/
				if(ruleManager.updateRuleWithAllDetails(rule)){

						rule = ruleManager.retrieveRuleDetails(rule.getId());
			//			Send the Create-Rule Command to iMVG.
						ActionModel actionModel = new ActionModel(null, rule);
						//vibhu start
			//			ImonitorControllerAction ruleUpdateacton = new RuleUpdateAction();
						ImonitorControllerAction ruleUpdateacton = null;
						boolean actionsInOldRule = false;
						boolean actionsInNewRule = false;
						if(rule.getImvgAlertsActions() != null && rule.getImvgAlertsActions().size() > 0) actionsInNewRule = true;
						if(rule2.getImvgAlertsActions() != null && rule2.getImvgAlertsActions().size() > 0) actionsInOldRule = true;
						if(actionsInOldRule && actionsInNewRule)
						{
							ruleUpdateacton = new RuleUpdateAction();
						}
						else if(actionsInOldRule && !actionsInNewRule)
						{
							ruleUpdateacton = new RuleDeleteAction();
						}
						else if(!actionsInOldRule && actionsInNewRule)
						{
							ruleUpdateacton = new RuleCreateAction();
						}
						else
						{
							return "msg.controller.rules.0011";
						}
						//vibhu end
						try
						{
							ruleUpdateacton.executeCommand(actionModel);
							resultFromImvg = IMonitorUtil.waitForResult(ruleUpdateacton);
						}
						catch (Exception e)
						{
							LogUtil.info("Some serious error while trying to modify rule. Revert to origonal rule.", e);
							actionModel.setMessage("Internal Error while updating rule.");
							resultFromImvg = false;

						}
						
						if(resultFromImvg){
							result = "msg.controller.rules.0005" + Constants.MESSAGE_DELIMITER_1 + ruleUpdateacton.getActionModel().getMessage();
			//				If the update is a failure, then we should roll back the updated rule.
							if(!ruleUpdateacton.isActionSuccess()){
								boolean res = ruleManager.updateRuleWithAllDetails(rule2);
								if(!res){
									result = "msg.controller.rules.0013"+ Constants.MESSAGE_DELIMITER_1 + rule.getName()+ Constants.MESSAGE_DELIMITER_2 + ruleUpdateacton.getActionModel().getMessage();
								}
								}
							else
							{
								result = "msg.controller.rules.0011";
							}
						}else{
							result = "msg.controller.rules.0013"+ Constants.MESSAGE_DELIMITER_1 + rule.getName() + Constants.MESSAGE_DELIMITER_1 + "msg.controller.rules.0015"+ Constants.MESSAGE_DELIMITER_1 + ruleUpdateacton.getActionModel().getMessage();
							//ruleUpdateacton.executeFailureResults(ruleUpdateacton.getActionModel().getQueue());
							boolean res = ruleManager.updateRuleWithAllDetails(rule2);
							if(!res){
								result = "msg.controller.rules.0013"+ Constants.MESSAGE_DELIMITER_1 + rule.getName()+ Constants.MESSAGE_DELIMITER_2 + ruleUpdateacton.getActionModel().getMessage();
							}
						}

				}else{
					result = "msg.controller.rules.0010" + Constants.MESSAGE_DELIMITER_1+rule.getName();
				}	
			}
		}
		else
		{
			/*List<Object[]> objects=ruleManager.checkRuleStartAndEndTime(devicetset,alerttype,rule.getId());
			
			if(!objects.isEmpty())
			{
			for (Object[] object : objects) 
			{
				lastSavedStart=(Long)object[0];
				lastSavedEnd=(Long)object[1];
				
				

				if(lastSavedEnd>=lastSavedStart)
				{
					if((NwEnd>=lastSavedEnd && NwStart>=lastSavedEnd)||(NwEnd<=lastSavedStart && NwStart<=lastSavedStart)||((NwEnd<NwStart) && (NwEnd<=lastSavedStart)&&(NwStart>lastSavedEnd)))
					{
						continue;
					}
					else
					{
						Rule dbrule = (Rule) object[2];
						int StartHour=(int) (lastSavedStart/60);
						int Startmin=(int) (lastSavedStart-(StartHour*60));
						int EndHour=(int) (lastSavedEnd/60);
						int Endmin=(int) (lastSavedEnd-(EndHour*60));
						
						int NowStartHour=(int) (NwStart/60);
						int NowStartmin=(int) (NwStart-(NowStartHour*60));
						int NowEndHour=(int) (NwEnd/60);
						int NowEndmin=(int) (NwEnd-(NowEndHour*60));
						result=IMonitorUtil.createMessage("msg001rov",dbrule.getName(),StartHour,Startmin,EndHour,Endmin,NowStartHour,NowStartmin,NowEndHour,NowEndmin);
						
						return result;
					}
				}
				if(lastSavedEnd<=lastSavedStart)
				{
					if((NwStart >= lastSavedEnd) && (NwEnd <=lastSavedStart))
					{
						continue;
					}
					else
					{
						Rule dbrule = (Rule) object[2];
						int StartHour=(int) (lastSavedStart/60);
						int Startmin=(int) (lastSavedStart-(StartHour*60));
						int EndHour=(int) (lastSavedEnd/60);
						int Endmin=(int) (lastSavedEnd-(EndHour*60));
						
						int NowStartHour=(int) (NwStart/60);
						int NowStartmin=(int) (NwStart-(NowStartHour*60));
						int NowEndHour=(int) (NwEnd/60);
						int NowEndmin=(int) (NwEnd-(NowEndHour*60));
						result=IMonitorUtil.createMessage("msg001rov",dbrule.getName(),StartHour,Startmin,EndHour,Endmin,NowStartHour,NowStartmin,NowEndHour,NowEndmin);
						return result;
					}
				}
			}
			}*/
			if(ruleManager.updateRuleWithAllDetails(rule)){

					rule = ruleManager.retrieveRuleDetails(rule.getId());
		//			Send the Create-Rule Command to iMVG.
					ActionModel actionModel = new ActionModel(null, rule);
					//vibhu start
		//			ImonitorControllerAction ruleUpdateacton = new RuleUpdateAction();
					ImonitorControllerAction ruleUpdateacton = null;
					boolean actionsInOldRule = false;
					boolean actionsInNewRule = false;
					if(rule.getImvgAlertsActions() != null && rule.getImvgAlertsActions().size() > 0) actionsInNewRule = true;
					if(rule2.getImvgAlertsActions() != null && rule2.getImvgAlertsActions().size() > 0) actionsInOldRule = true;
					if(actionsInOldRule && actionsInNewRule)
					{
						ruleUpdateacton = new RuleUpdateAction();
					}
					else if(actionsInOldRule && !actionsInNewRule)
					{
						ruleUpdateacton = new RuleDeleteAction();
					}
					else if(!actionsInOldRule && actionsInNewRule)
					{
						ruleUpdateacton = new RuleCreateAction();
					}
					else
					{
						return "msg.controller.rules.0011";
					}
					//vibhu end
					try
					{
						ruleUpdateacton.executeCommand(actionModel);
						resultFromImvg = IMonitorUtil.waitForResult(ruleUpdateacton);
					}
					catch (Exception e)
					{
						LogUtil.info("Some serious error while trying to modify rule. Revert to origonal rule.", e);
						actionModel.setMessage("Internal Error while updating rule.");
						resultFromImvg = false;

					}
					
					if(resultFromImvg){
						result = "msg.controller.rules.0005" + Constants.MESSAGE_DELIMITER_1 + ruleUpdateacton.getActionModel().getMessage();
		//				If the update is a failure, then we should roll back the updated rule.
						if(!ruleUpdateacton.isActionSuccess()){
							boolean res = ruleManager.updateRuleWithAllDetails(rule2);
							if(!res){
								result = "msg.controller.rules.0013"+ Constants.MESSAGE_DELIMITER_1 + rule.getName()+ Constants.MESSAGE_DELIMITER_2 + ruleUpdateacton.getActionModel().getMessage();
							}
							}
						else
						{
							result = "msg.controller.rules.0011";
						}
					}else{
						result = "msg.controller.rules.0013"+ Constants.MESSAGE_DELIMITER_1 + rule.getName() + Constants.MESSAGE_DELIMITER_1 + "msg.controller.rules.0015"+ Constants.MESSAGE_DELIMITER_1 + ruleUpdateacton.getActionModel().getMessage();
						//ruleUpdateacton.executeFailureResults(ruleUpdateacton.getActionModel().getQueue());
						boolean res = ruleManager.updateRuleWithAllDetails(rule2);
						if(!res){
							result = "msg.controller.rules.0013"+ Constants.MESSAGE_DELIMITER_1 + rule.getName()+ Constants.MESSAGE_DELIMITER_2 + ruleUpdateacton.getActionModel().getMessage();
						}
					}

			}else{
				result = "msg.controller.rules.0010" + Constants.MESSAGE_DELIMITER_1+rule.getName();
			}	
		}
			
		return result;
	}
	
	public String deleteRuleWithAllDetails(String ruleXml){
		XStream xStream  = new XStream();
		Rule rule = (Rule) xStream.fromXML(ruleXml);
		RuleManager ruleManager = new RuleManager();
//			Send the Delete-Rule Command to iMVG. Only if there are some actions.
		rule = ruleManager.retrieveRuleDetails(rule.getId());
		String result = "msg.controller.rules.0018" + Constants.MESSAGE_DELIMITER_1+rule.getName();
		
		//vibhu adding if-else for condition rule.getImvgAlertsActions() != null...
		if(rule.getImvgAlertsActions() != null && rule.getImvgAlertsActions().size() > 0 )
		{
		
		ActionModel actionModel = new ActionModel(null, rule);
		ImonitorControllerAction ruleDeleteAction = new RuleDeleteAction();
		ruleDeleteAction.executeCommand(actionModel);
		
		boolean resultFromImvg = IMonitorUtil.waitForResult(ruleDeleteAction);
		if(resultFromImvg){
			if(ruleDeleteAction.isActionSuccess()){
				boolean res = ruleManager.deleteRuleWithAllDetails(rule);
				if(res){
					result = "msg.controller.rules.0014"+Constants.MESSAGE_DELIMITER_1  + rule.getName();
				}else{
					result = "msg.controller.rules.0015" +Constants.MESSAGE_DELIMITER_1  + rule.getName();
				}
			}else{
				result = "msg.controller.rules.0005" + Constants.MESSAGE_DELIMITER_1 + ruleDeleteAction.getActionModel().getMessage();
			}
		}else{
			result = "msg.controller.rules.0016"
			+ Constants.MESSAGE_DELIMITER_1 + rule.getName() 
			+ Constants.MESSAGE_DELIMITER_2 + ruleDeleteAction.getActionModel().getMessage();
						
		}
		}
		else
		{
			boolean res = ruleManager.deleteRuleWithAllDetails(rule);
			if(res){
				result = "msg.controller.rules.0014";
			}else{
				result = "msg.controller.rules.0017" +Constants.MESSAGE_DELIMITER_1+ rule.getName();
			}
		}
		return result;
	}
	
	public String retrieveAllRuleDetails(String ruleXml){
		XStream xStream  = new XStream();
		Rule rule = (Rule) xStream.fromXML(ruleXml);
		RuleManager ruleManager = new RuleManager();
		rule = ruleManager.retrieveRuleDetails(rule.getId());
		return xStream.toXML(rule);
	}
	
	public String listAskedRules(String dataTableXml, String xml){
		XStream xStream=new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(dataTableXml);
		long id = (Long) xStream.fromXML(xml);
		RuleManager ruleManager=new RuleManager();
		String sSearch = dataTable.getsSearch();
		String[] cols = { "r.name", "r.description","r.gateWay.macId"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = ruleManager.listAskedRules(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),id);
		dataTable.setTotalRows(list.size());
		dataTable.setResults(list);
		int displayRow = ruleManager.getRuleCountByCustomer(sSearch,id);
		dataTable.setTotalRows(ruleManager.getTotalRuleCountByCustomer(id));
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	
//vibhu start
	public String verifyRuleDetails(Rule rule)
	{
		String result = "msg.controller.rules.0001";
	
		/*
		List<Rule> rules = new DaoManager().get("gateWay",rule.getGateWay().getId(), Rule.class);
		for(Rule rule1:rules)
		{
			if(rule.getName().equalsIgnoreCase(rule1.getName())
				&& rule.getId() != rule1.getId()) //this condition is to take care of updates
				return "A rule with specified name already exists. Please use another name.";
		}
		*/

		//The validations would be more efficient using DB queries 
		int ret = new RuleManager().validateRule(rule);
		LogUtil.info("RETURNED "+ret);
		if (ret >= 0) result = null;
		else if(ret == -2)result = "msg.controller.rules.0002";
		else if(ret == -3)result = "msg.controller.rules.0003"+Constants.MESSAGE_DELIMITER_1+Constants.MAX_RULE_PER_DEVICE_ALERT;
		else if(ret == -4)result = "msg.controller.rules.0004";
		//LogUtil.info("RETURNED "+ret+result);
		return result;
	}
//vibhu end
	
	//Get Gateway
	public String getGateway(String did)
	{
		XStream stream=new XStream();
		String devId=(String) stream.fromXML(did);
		long deviceid=Long.parseLong(devId);
		DeviceManager deviceManager=new DeviceManager();
		Device device=deviceManager.getDeviceById(deviceid);
		GatewayManager gatewayManager=new GatewayManager();
		GateWay gateWay=gatewayManager.getGateWayById(device.getGateWay().getId());
		return stream.toXML(gateWay);
	}
	
	public String saveImvgSecurityActions(String xml, String cXml) {
		String result = "msg.controller.security.0001";
		DaoManager manager = new DaoManager();
		XStream stream = new XStream();
		Set<ImvgSecurityActions> imvgAlertsActions = (Set<ImvgSecurityActions>) stream.fromXML(xml);
		long customerId = (Long) stream.fromXML(cXml);
		
		RuleManager ruleManager = new RuleManager();
		try {
			ruleManager.deleteAllImvgSecurityActions(customerId);
		} catch (Exception e) {
			LogUtil.info("coul not delete security actions");
		}
		
		
	
		
		try {
			for (ImvgSecurityActions action : imvgAlertsActions) {
				
				manager.save(action);
				result = "msg.controller.security.0002";
			}
		} catch (Exception e) {
			LogUtil.info("could'nt save imvg alert actions");
		}
		
		return result;
	}
	
	public String retrieveAllImvgSecurityDetails(String cXml){
		XStream xStream  = new XStream();
		long cid = (Long) xStream.fromXML(cXml);
		RuleManager ruleManager = new RuleManager();
		Set<ImvgSecurityActions> actions = ruleManager.retrieveImvgSecurityDetails(cid);
		
		return xStream.toXML(actions);
	}
	
}
