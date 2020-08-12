/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;


import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScheduleCreateAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScheduleDeleteAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScheduleUpdateAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScheduleActivateAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScheduleDeactivateAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScheduleAction;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScheduleManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class ScheduleServiceManager {

	public String saveScheduleWithDetails(String scheduleXml)throws SecurityException,
	DOMException, IllegalArgumentException,
	ParserConfigurationException, SAXException, IOException,
	ClassNotFoundException, InstantiationException,
	IllegalAccessException, NoSuchMethodException,
	InvocationTargetException{
		String result = "msg.controller.schedules.0001";
		try
		{
			XStream xStream  = new XStream();
			boolean resultFromImvg = false;
			Schedule schedule = (Schedule) xStream.fromXML(scheduleXml);
			
			//vibhu start
			/*result = verifyScheduleDetails(schedule);
			if(result != null)return result;*/
			//vibhu end
			
			schedule.setActivationStatus(1);							//By default Schedule should be active.
			ScheduleManager scheduleManager = new ScheduleManager();
			
			result = "msg.controller.schedules.0003" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
			if(scheduleManager.saveScheduleWithDetails(schedule)){
	//			Send the Create-Rule Command to iMVG.
				schedule = scheduleManager.retrieveScheduleDetails(schedule.getId());
				ActionModel actionModel = new ActionModel(null, schedule);
				ImonitorControllerAction scheduleCreateAction = new ScheduleCreateAction();
				try
				{
					scheduleCreateAction.executeCommand(actionModel);
					resultFromImvg = IMonitorUtil.waitForResult(scheduleCreateAction);
				}
				catch (Exception e) 
				{
					resultFromImvg = false;
					String res = "msg.controller.schedules.0006";
					actionModel.setMessage(res);
				}
				if(resultFromImvg){
					//result = "msg.controller.schedules.0007" + Constants.MESSAGE_DELIMITER_1 + scheduleCreateAction.getActionModel().getMessage();
					result = scheduleCreateAction.getActionModel().getMessage();
				}else{
					result = "msg.controller.schedules.0008"
							+ Constants.MESSAGE_DELIMITER_1 + schedule.getName()
							+ Constants.MESSAGE_DELIMITER_2 + scheduleCreateAction.getActionModel().getMessage();
					scheduleManager.deleteSchedule(schedule);
					//scheduleCreateAction.executeFailureResults(actionModel.getQueue());
				}
			}else{
				result = "msg.controller.schedules.0009" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
			}
		}
		catch(Exception e)
		{
			//result = "General error occured while trying to save schedule";
			LogUtil.error("Got Exception"+e.getMessage());
			LogUtil.info("Got Exception", e);
		}
		return result;
	}
	
	public String updateScheduleWithDetails(String scheduleXml)throws SecurityException,
	DOMException, IllegalArgumentException,
	ParserConfigurationException, SAXException, IOException,
	ClassNotFoundException, InstantiationException,
	IllegalAccessException, NoSuchMethodException,
	InvocationTargetException{
		String result = "msg.controller.schedules.0020";
		try
		{
			XStream xStream  = new XStream();
			boolean resultFromImvg = false;
			Schedule schedule = (Schedule) xStream.fromXML(scheduleXml);
			//vibhu start
			/*result = verifyScheduleDetails(schedule);
			if(result != null)return result;*/
			//vibhu end
			ScheduleManager scheduleManager = new ScheduleManager();
			Schedule schedule2 = scheduleManager.retrieveScheduleDetails(schedule.getId());
			schedule.setActivationStatus(schedule2.getActivationStatus());					//sumit added this line to retain Schedule Activation Status.
			if(schedule2.getEndSchedule() != 0){
				
				Schedule endSchedule2 = scheduleManager.retrieveScheduleDetails(schedule2.getEndSchedule());
				
				ActionModel actionModel1 = new ActionModel(null, endSchedule2);
				
				ImonitorControllerAction scheduleDeleteAction = new ScheduleDeleteAction();
				try {
					scheduleDeleteAction.executeCommand(actionModel1);
					
					resultFromImvg = IMonitorUtil.waitForResult(scheduleDeleteAction);
					
					if(resultFromImvg){
						
						scheduleManager.deleteSchedule(endSchedule2);
						
						schedule.setEndSchedule(0);
					
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
			
			result = "msg.controller.schedules.0021" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
			if(scheduleManager.updateScheduleWithDetails(schedule)){
	//			Send the Create-Rule Command to iMVG.
				schedule = scheduleManager.retrieveScheduleDetails(schedule.getId());
				ActionModel actionModel = new ActionModel(null, schedule);
				ImonitorControllerAction scheduleCreateAction = new ScheduleUpdateAction();
				try
				{
					scheduleCreateAction.executeCommand(actionModel);
					resultFromImvg = IMonitorUtil.waitForResult(scheduleCreateAction);
				}
				catch (Exception e) 
				{
					resultFromImvg = false;
					String res = "msg.controller.schedules.0024";
					actionModel.setMessage(res);
				}
				if(resultFromImvg){
					//result = "msg.controller.schedules.0007" + Constants.MESSAGE_DELIMITER_1 + scheduleCreateAction.getActionModel().getMessage();
					result = scheduleCreateAction.getActionModel().getMessage();
	//				If the update is a failure, then we should roll back the updated rule.
					if(!scheduleCreateAction.isActionSuccess()){
						boolean res = scheduleManager.updateScheduleWithDetails(schedule2);
						if(!res){
							result = "msg.controller.schedules.0025" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
						}
					}
				}else{
					result = "msg.controller.schedules.0026"
							+ Constants.MESSAGE_DELIMITER_1 + schedule.getName() 
							+ Constants.MESSAGE_DELIMITER_2 + scheduleCreateAction.getActionModel().getMessage();
					boolean res = scheduleManager.updateScheduleWithDetails(schedule2);
					if(!res){
						result = "msg.controller.schedules.0025" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
					}
				}
			}else{
				result = "msg.controller.schedules.0027" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
			}
		}
		catch(Exception e)
		{
			//result = "General error occured while trying to update schedule";
			LogUtil.error(result+e.getMessage());
			LogUtil.info(result, e);
		}
		return result;
	}
	
	public String listAskedSchedules(String xml, String idXml){
		XStream xStream=new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(xml);
		long id = (Long) xStream.fromXML(idXml);
		ScheduleManager scheduleManager = new ScheduleManager();
		String sSearch = dataTable.getsSearch();
		String[] cols = { "s.name","s.description"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		
		List<?> list = scheduleManager.listAskedSchedule(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),id);
		dataTable.setTotalRows(list.size());
		dataTable.setResults(list);
		int displayRow = scheduleManager.getScheduleCountByCustomerId(sSearch,id);
		dataTable.setTotalDispalyRows(displayRow);
		dataTable.setTotalRows(scheduleManager.getTotalScheduleCountByCustomerId(id));
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	
	public String deleteSchedule(String idXml){
		String result = "msg.controller.schedules.0028";
		try
		{
			ScheduleManager scheduleManager = new ScheduleManager();
			XStream xStream = new XStream();
			Long id = (Long) xStream.fromXML(idXml);
			
			Schedule schedule = scheduleManager.retrieveScheduleDetails(id.longValue());
			
			result = "msg.controller.schedules.0029" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
			ActionModel actionModel = new ActionModel(null, schedule);
			ImonitorControllerAction scheduleDeleteAction = new ScheduleDeleteAction();
			scheduleDeleteAction.executeCommand(actionModel);
			boolean resultFromImvg = IMonitorUtil.waitForResult(scheduleDeleteAction);
			if(resultFromImvg){
				//naveen added to delete entries in imvgalertnotificationforScedule table
				if(scheduleDeleteAction.isActionSuccess()){
					boolean res = scheduleManager.deleteSchedule(schedule);
					if(res){
						result = "msg.controller.schedules.0031" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
					}else{
						result = "msg.controller.schedules.0032" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
					}
				}else{
					//result = "msg.controller.schedules.0007" + Constants.MESSAGE_DELIMITER_1 + scheduleDeleteAction.getActionModel().getMessage();
					result = scheduleDeleteAction.getActionModel().getMessage();
				}
				
			}else{
				
				result = "msg.controller.schedules.0033" 
						 + Constants.MESSAGE_DELIMITER_1 + schedule.getName()
						 + Constants.MESSAGE_DELIMITER_2 + scheduleDeleteAction.getActionModel().getMessage();
			}
			
			if(schedule.getEndSchedule() != 0){
				
				Schedule endSchedule = scheduleManager.retrieveScheduleDetails(schedule.getEndSchedule());
				actionModel = new ActionModel(null, endSchedule);  	
				scheduleDeleteAction.executeCommand(actionModel);
				resultFromImvg = IMonitorUtil.waitForResult(scheduleDeleteAction);
				if(resultFromImvg){
					if(scheduleDeleteAction.isActionSuccess()){
						scheduleManager.deleteSchedule(endSchedule);
					}
				}
	
			}
			
			
			
		}
		catch(Exception e)
		{
			//result = "General error occured while trying to delete schedule";
			LogUtil.error(result+e.getMessage());
			LogUtil.info(result, e);
		}
		return result;
	}
	
	public String getScheduleById(String scheduleId) {
//		ScheduleManager scheduleManager = new ScheduleManager();
//		Schedule schedule = scheduleManager.getScheduleWithCustomerByScheduleId(scheduleId);
//		XStream stream = new XStream();
		return "";
	}
	public String retrieveAllScheduleDetails(String scheduleXml){
		XStream xStream  = new XStream();
		Schedule schedule = (Schedule) xStream.fromXML(scheduleXml);
		ScheduleManager scheduleManager = new ScheduleManager();
		schedule = scheduleManager.retrieveScheduleDetails(schedule.getId());
		return xStream.toXML(schedule);
	}
	public String retrieveScheduleDetailsfromgenerateddeviceid(String xmlgenerateddeviceid){
		XStream xStream  = new XStream();
		Schedule schedule =new Schedule();
		String genarateddeviceid =(String) xStream.fromXML(xmlgenerateddeviceid);
		@SuppressWarnings("unused")
		ScheduleManager scheduleManager = new ScheduleManager();
		schedule = (Schedule)new DaoManager().getOne("description",genarateddeviceid, Schedule.class);
		return xStream.toXML(schedule);
	}
	
	// ************************************************ sumit start: Schedule Activate/De-Activate *******************************************
	public String deActivateSchedule(String scheduleXml){
		String result = "msg.controller.schedules.0010";
		try
		{
			XStream stream = new XStream();
			Schedule scheduleFromWeb = (Schedule) stream.fromXML(scheduleXml);

			
			//1. Get schedule details. (Gatway: macId)
			ScheduleManager scheduleManager = new ScheduleManager();
			scheduleFromWeb = scheduleManager.retrieveScheduleDetails(scheduleFromWeb.getId());
			scheduleFromWeb.setActivationStatus(-1);
			//result = "msg.controller.schedules.0011" + Constants.MESSAGE_DELIMITER_1 + scheduleFromWeb.getName();
			
			//2. Send appropriate message to iMVG.
			ActionModel actionModel = new ActionModel(null, scheduleFromWeb);
			ImonitorControllerAction scheduleDeactivateAction = new ScheduleDeactivateAction();
			scheduleDeactivateAction.executeCommand(actionModel);
			boolean resultFromImvg = IMonitorUtil.waitForResult(scheduleDeactivateAction);
			if(resultFromImvg){
				if(scheduleDeactivateAction.isActionSuccess()){
					//3. update schedule with new Activation Status
					if(scheduleFromWeb.getEndSchedule() != 0 ){
						Schedule endSchedule = scheduleManager.retrieveScheduleDetails(scheduleFromWeb.getEndSchedule());
						endSchedule.setActivationStatus(-1);
						actionModel = new ActionModel(null, endSchedule);
						scheduleDeactivateAction.executeCommand(actionModel);
						resultFromImvg = IMonitorUtil.waitForResult(scheduleDeactivateAction);
						if(resultFromImvg){
							scheduleManager.updateScheduleWithDetails(scheduleFromWeb);
							scheduleManager.updateScheduleWithDetails(endSchedule);
							result = scheduleDeactivateAction.getActionModel().getMessage();
						}
					}else{
						
					if(scheduleManager.updateScheduleWithDetails(scheduleFromWeb)){
						//result = "msg.controller.schedules.0007"+ Constants.MESSAGE_DELIMITER_1 + scheduleDeactivateAction.getActionModel().getMessage();//DB successfully updated
						result = scheduleDeactivateAction.getActionModel().getMessage();
					}
					
					}// end else part
				}else{
					//result = "msg.controller.schedules.0007"+ Constants.MESSAGE_DELIMITER_1 + scheduleDeactivateAction.getActionModel().getMessage();
					result = scheduleDeactivateAction.getActionModel().getMessage();
				}
			}else{
				result = "msg.controller.schedules.0007";
			}
		}
		catch(Exception e)
		{
			//result = "General error occured while trying to deActivate schedule";
			LogUtil.error(result+e.getMessage());
			LogUtil.info(result, e);
		}
		return result;
	}
	public String activateSchedule(String scheduleXml){
		String result = "msg.controller.schedules.0015";
		try
		{
			XStream stream = new XStream();
			Schedule scheduleFromWeb = (Schedule) stream.fromXML(scheduleXml);
			
			
			//1. Get schedule details. (Gatway: macId)
			ScheduleManager scheduleManager = new ScheduleManager();
			scheduleFromWeb = scheduleManager.retrieveScheduleDetails(scheduleFromWeb.getId());
			scheduleFromWeb.setActivationStatus(1);
	
			//result = "msg.controller.schedules.001" + Constants.MESSAGE_DELIMITER_1 + scheduleFromWeb.getName();
			
			//2. Send appropriate message to iMVG.
			ActionModel actionModel = new ActionModel(null, scheduleFromWeb);
			ImonitorControllerAction scheduleActivateAction = new ScheduleActivateAction();
			scheduleActivateAction.executeCommand(actionModel);
			boolean resultFromImvg = IMonitorUtil.waitForResult(scheduleActivateAction);
			if(resultFromImvg){
				if(scheduleActivateAction.isActionSuccess()){
	
					//3. update schedule with new Activation Status
					if(scheduleFromWeb.getEndSchedule() != 0 ){
						Schedule endSchedule = scheduleManager.retrieveScheduleDetails(scheduleFromWeb.getEndSchedule());
						endSchedule.setActivationStatus(1);
						actionModel = new ActionModel(null, endSchedule);
						scheduleActivateAction.executeCommand(actionModel);
						resultFromImvg = IMonitorUtil.waitForResult(scheduleActivateAction);
						if(resultFromImvg){
							scheduleManager.updateScheduleWithDetails(scheduleFromWeb);
							scheduleManager.updateScheduleWithDetails(endSchedule);
							result = scheduleActivateAction.getActionModel().getMessage();
						}
					}else{
										
					if(scheduleManager.updateScheduleWithDetails(scheduleFromWeb)){
						//result = "msg.controller.schedules.0007" + Constants.MESSAGE_DELIMITER_1 + scheduleActivateAction.getActionModel().getMessage();//DB successfully updated
						result = scheduleActivateAction.getActionModel().getMessage();
					}
					
					}// end else part
				}else{
					//result = "msg.controller.schedules.0007" + Constants.MESSAGE_DELIMITER_1 + scheduleActivateAction.getActionModel().getMessage();
					result = scheduleActivateAction.getActionModel().getMessage();
				}
			}else{
				result = "msg.controller.schedules.0007";
			}
		}
		catch(Exception e)
		{
			//result = "General error occured while trying to activate schedule";
			LogUtil.error(result+e.getMessage());
			LogUtil.info(result, e);
		}
		return result;
	}
	// ************************************************** sumit end: Schedule Activate/De-Activate *******************************************
	
	//vibhu start
		public String verifyScheduleDetails(Schedule schedule)
		{
			String result = null;
		
			List<Schedule> schedules = new DaoManager().get("gateWay",schedule.getGateWay().getId(), Schedule.class);
			for(Schedule schedule1:schedules)
			{
				if(schedule.getName().equalsIgnoreCase(schedule1.getName())
					&& schedule.getId() != schedule1.getId()) //this condition is to take care of updates
					return "msg.controller.schedules.0002";
			}
			return result;
		}
	//vibhu end
		
	//parishod start
		public String getScheduleCountForDevice(String xml){
			String result = "NO_SCHEDULE_EXISTS";
			XStream xStream = new XStream();
			String genDevcId = (String) xStream.fromXML(xml);
			ScheduleManager scheduleManager = new ScheduleManager();
			long count = scheduleManager.getScheduleCountByGeneratedDeviceId(genDevcId);
			if(count > 0){
				result = "SCHEDULE_EXISTS";
			}
			return result;
		}
		//parishod end
		
        /*Date: 27th Jan 2016		
        Naveen added to save schedule with start and end actions*/
		
		public String saveScheduleWithStartAndEndDetails(String scheduleXml,String endScheduleXml)throws SecurityException,
		DOMException, IllegalArgumentException,
		ParserConfigurationException, SAXException, IOException,
		ClassNotFoundException, InstantiationException,
		IllegalAccessException, NoSuchMethodException,
		InvocationTargetException{
			String result = "msg.controller.schedules.0001";
			
			
			try
			{
				XStream xStream  = new XStream();
				boolean resultFromImvg = false;
				Schedule schedule = (Schedule) xStream.fromXML(scheduleXml);
			
				//vibhu start
				/*result = verifyScheduleDetails(schedule);
				if(result != null)return result;*/
				//vibhu end
				
				schedule.setActivationStatus(1);//By default Schedule should be active.
				ScheduleManager scheduleManager = new ScheduleManager();
				Schedule endSchedule = (Schedule) xStream.fromXML(endScheduleXml);
				
				result = "msg.controller.schedules.0003" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
				
				
				try {
					if(scheduleManager.saveScheduleWithDetails(endSchedule));
					schedule.setEndSchedule(endSchedule.getId());
				} catch (Exception e) {
					LogUtil.info("could not save end schedule and the message is: " + e.getMessage());
				}
				
			
				if(scheduleManager.saveScheduleWithDetails(schedule)){
		//			Send the Create-Rule Command to iMVG.
					
					schedule = scheduleManager.retrieveScheduleDetails(schedule.getId());
					
					ActionModel actionModel = new ActionModel(null, schedule);
					ImonitorControllerAction scheduleCreateAction = new ScheduleCreateAction();
					try
					{
						scheduleCreateAction.executeCommand(actionModel);
						resultFromImvg = IMonitorUtil.waitForResult(scheduleCreateAction);
					}
					catch (Exception e) 
					{
						resultFromImvg = false;
						String res = "msg.controller.schedules.0006";
						actionModel.setMessage(res);
					}
					if(resultFromImvg){
						//result = "msg.controller.schedules.0007" + Constants.MESSAGE_DELIMITER_1 + scheduleCreateAction.getActionModel().getMessage();
						result = scheduleCreateAction.getActionModel().getMessage();
						
						endSchedule = scheduleManager.retrieveScheduleDetails(endSchedule.getId());
						actionModel = new ActionModel(null, endSchedule);
						try
						{
							scheduleCreateAction.executeCommand(actionModel);
							resultFromImvg = IMonitorUtil.waitForResult(scheduleCreateAction);
						}
						catch (Exception e) 
						{
							resultFromImvg = false;
							String res = "msg.controller.schedules.0006";
							actionModel.setMessage(res);
						}
						if(resultFromImvg){
							result = scheduleCreateAction.getActionModel().getMessage();
						}else{
							result = "msg.controller.schedules.0008"
									+ Constants.MESSAGE_DELIMITER_1 + schedule.getName()
									+ Constants.MESSAGE_DELIMITER_2 + scheduleCreateAction.getActionModel().getMessage();
							scheduleManager.deleteSchedule(endSchedule);
                           
							// to do 
							// remove endschedule id from start schedule;
							 schedule.setEndSchedule(0);
	                         scheduleManager.saveScheduleWithDetails(schedule);
						}
						
					}else{
						result = "msg.controller.schedules.0008"
								+ Constants.MESSAGE_DELIMITER_1 + schedule.getName()
								+ Constants.MESSAGE_DELIMITER_2 + scheduleCreateAction.getActionModel().getMessage();
						scheduleManager.deleteSchedule(schedule);
						scheduleManager.deleteSchedule(endSchedule);
						//scheduleCreateAction.executeFailureResults(actionModel.getQueue());
					}
				}else{
					result = "msg.controller.schedules.0009" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
				}
			}
			catch(Exception e)
			{
				//result = "General error occured while trying to save schedule";
				LogUtil.error("Got Exception"+e.getMessage());
				LogUtil.info("Got Exception", e);
			}
			return result;
		}
		
		
		public String updateScheduleWithStartAndEndDetails(String scheduleXml, String endSchedulexml)throws SecurityException,
		DOMException, IllegalArgumentException,
		ParserConfigurationException, SAXException, IOException,
		ClassNotFoundException, InstantiationException,
		IllegalAccessException, NoSuchMethodException,
		InvocationTargetException{
			String result = "msg.controller.schedules.0023";
			try
			{
				XStream xStream  = new XStream();
				boolean resultFromImvg = false;
				Schedule schedule = (Schedule) xStream.fromXML(scheduleXml);
				Schedule endSchedule = (Schedule) xStream.fromXML(endSchedulexml);
				
				/*//vibhu start
				result = verifyScheduleDetails(schedule);
				if(result != null)return result;*/
				//vibhu end
				ScheduleManager scheduleManager = new ScheduleManager();
				Schedule schedule2 = scheduleManager.retrieveScheduleDetails(schedule.getId());
				schedule.setActivationStatus(schedule2.getActivationStatus());					//sumit added this line to retain Schedule Activation Status.
				result = "msg.controller.schedules.0021" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
				
				if(schedule2.getEndSchedule() == 0){
					if(scheduleManager.saveScheduleWithDetails(endSchedule)){
						schedule.setEndSchedule(endSchedule.getId());
					}
				}else{
					scheduleManager.updateScheduleWithDetails(endSchedule);
				}
				
				if(scheduleManager.updateScheduleWithDetails(schedule)){
		//			Send the Create-Rule Command to iMVG.
					schedule = scheduleManager.retrieveScheduleDetails(schedule.getId());
					ActionModel actionModel = new ActionModel(null, schedule);
					ImonitorControllerAction scheduleCreateAction = new ScheduleUpdateAction();
					try
					{
						scheduleCreateAction.executeCommand(actionModel);
						resultFromImvg = IMonitorUtil.waitForResult(scheduleCreateAction);
					}
					catch (Exception e) 
					{
						resultFromImvg = false;
						String res = "msg.controller.schedules.0024";
						actionModel.setMessage(res);
					}
					if(resultFromImvg){
						
						result = scheduleCreateAction.getActionModel().getMessage();
		//				If the update is a failure, then we should roll back the updated rule.
						if(!scheduleCreateAction.isActionSuccess()){
							boolean res = scheduleManager.updateScheduleWithDetails(schedule2);
							
							if(!res){
								result = "msg.controller.schedules.0025" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
							}
						}
						//if(scheduleManager.updateScheduleWithDetails(endSchedule)){
						endSchedule = scheduleManager.retrieveScheduleDetails(schedule.getEndSchedule());
						
						//}
						ActionModel actionModel1 = new ActionModel(null, endSchedule);
						try
						{
							ImonitorControllerAction scheduleUpdateAction = new ScheduleUpdateAction();
							if(schedule2.getEndSchedule() == 0){
								
								scheduleUpdateAction = new ScheduleCreateAction();
								scheduleUpdateAction.executeCommand(actionModel1);
								
							}else{
								scheduleUpdateAction.executeCommand(actionModel1);
							}
							
							resultFromImvg = IMonitorUtil.waitForResult(scheduleUpdateAction);
						
							if(resultFromImvg){
								result = scheduleUpdateAction.getActionModel().getMessage();
															
							}else{
								
								result = "msg.controller.schedules.0008"
										+ Constants.MESSAGE_DELIMITER_1 + schedule.getName()
										+ Constants.MESSAGE_DELIMITER_2 + scheduleUpdateAction.getActionModel().getMessage();
								scheduleManager.deleteSchedule(endSchedule);
								// to do 
								// remove endschedule id from start schedule;
							}
						}
						catch (Exception e) 
						{
							resultFromImvg = false;
							String res = "msg.controller.schedules.0006";
							actionModel.setMessage(res);
						}
				
				}else{
						
						result = "msg.controller.schedules.0026"
								+ Constants.MESSAGE_DELIMITER_1 + schedule.getName() 
								+ Constants.MESSAGE_DELIMITER_2 + scheduleCreateAction.getActionModel().getMessage();
						boolean res = scheduleManager.updateScheduleWithDetails(schedule2);
						if(!res){
							result = "msg.controller.schedules.0025" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
						}
					}
				}else{
					
					result = "msg.controller.schedules.0027" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
				   
				}
			}
			catch(Exception e)
			{
				//result = "General error occured while trying to update schedule";
				LogUtil.error(result+e.getMessage());
				
			}
			return result;
		}
		
		public String retrieveAllEndScheduleDetails(String scheduleXml){
			XStream xStream  = new XStream();
			Schedule schedule = (Schedule) xStream.fromXML(scheduleXml);
			ScheduleManager scheduleManager = new ScheduleManager();
			schedule = scheduleManager.retrieveScheduleDetails(schedule.getEndSchedule());
			return xStream.toXML(schedule);
		}
		
		
		@SuppressWarnings("unchecked")
		public String verifyscheduleDetails(Schedule schedule) {
			
			String result = "validSchedule";
			try {
				List<Schedule> schedules = new DaoManager().get("gateWay", schedule.getGateWay().getId(), Schedule.class);
				LogUtil.info("verifyscheduleDetails schedules=="+schedules);
				XStream stream = new XStream();
				for (Schedule scheduleFromDb : schedules) {
					if (schedule.getName().equals(scheduleFromDb.getName())
							&& (schedule.getGateWay().getId() == scheduleFromDb.getGateWay().getId())) {
						if (schedule.getId() != scheduleFromDb.getId())
							result = "duplicateSchedule";
					}
				}
			} catch (Exception e) {
				LogUtil.info("Got Exception while saving Schedule: ", e);
			}
			LogUtil.info("verifyscheduleDetails result=="+result);
			return result;
		}
		

		
}
