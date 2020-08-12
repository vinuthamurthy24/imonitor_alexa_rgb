/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.action.AcControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcFanModeControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcSwingControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcTempeartureControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.AllArmAction;
import in.xpeditions.jawlin.imonitor.controller.action.AllDisArmAction;
import in.xpeditions.jawlin.imonitor.controller.action.AllonoffAction;
import in.xpeditions.jawlin.imonitor.controller.action.ArmDisarmAction;
import in.xpeditions.jawlin.imonitor.controller.action.CameraConfParamUpdatorAction;
import in.xpeditions.jawlin.imonitor.controller.action.CameraH264ConfParamUpdatorAction;
import in.xpeditions.jawlin.imonitor.controller.action.CameraMoveControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.CameraNightVisionControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.CameraPanTiltControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.CameraSetControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.CaptureImageAction;
import in.xpeditions.jawlin.imonitor.controller.action.CurtainCloseAction;
import in.xpeditions.jawlin.imonitor.controller.action.CurtainCloseSliderAction;
import in.xpeditions.jawlin.imonitor.controller.action.CurtainModelUpdate;
import in.xpeditions.jawlin.imonitor.controller.action.CurtainOpenSliderAction;
import in.xpeditions.jawlin.imonitor.controller.action.CurtainopenAction;
import in.xpeditions.jawlin.imonitor.controller.action.DimmerLevelChangeAction;
import in.xpeditions.jawlin.imonitor.controller.action.DisableAction;
import in.xpeditions.jawlin.imonitor.controller.action.DoorUnlock;
import in.xpeditions.jawlin.imonitor.controller.action.Doorlock;
import in.xpeditions.jawlin.imonitor.controller.action.EnableAction;
import in.xpeditions.jawlin.imonitor.controller.action.GetLiveStreamAction;
import in.xpeditions.jawlin.imonitor.controller.action.GetStoredStreamAction;
import in.xpeditions.jawlin.imonitor.controller.action.IDUFanDirectionControl;
import in.xpeditions.jawlin.imonitor.controller.action.IDUFanVolumeControl;
import in.xpeditions.jawlin.imonitor.controller.action.IMVGRebootAction;
import in.xpeditions.jawlin.imonitor.controller.action.IduTemperatureControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.action.IndoorUnitActionControl;
import in.xpeditions.jawlin.imonitor.controller.action.RetrieveTemperatureAction;
import in.xpeditions.jawlin.imonitor.controller.action.SetPassCodeForDoorLock;
import in.xpeditions.jawlin.imonitor.controller.action.UpdateAction;
import in.xpeditions.jawlin.imonitor.controller.action.UpdateNeighbor;
import in.xpeditions.jawlin.imonitor.controller.action.healthcheckCurrentStatusdeviceModelUpdate;
import in.xpeditions.jawlin.imonitor.controller.action.healthcheckScanNowdeviceModelUpdate;
import in.xpeditions.jawlin.imonitor.controller.action.healthcheckdeviceModel;
import in.xpeditions.jawlin.imonitor.controller.action.healthcheckdeviceModelCreate;
import in.xpeditions.jawlin.imonitor.controller.action.healthcheckdeviceModelDelete;
import in.xpeditions.jawlin.imonitor.controller.action.healthcheckdeviceModelUpdate;
//sumit begin
import in.xpeditions.jawlin.imonitor.controller.action.SelectiveArmAction;
//sumit end
import in.xpeditions.jawlin.imonitor.controller.action.SwitchOffAction;
import in.xpeditions.jawlin.imonitor.controller.action.SwitchOnAction;
import in.xpeditions.jawlin.imonitor.controller.action.UpdateModelNumberAction;
import in.xpeditions.jawlin.imonitor.controller.communication.BroadCasterCommunicator;
import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DoorLockConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.H264CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LCDRemoteConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MotorConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SirenConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AgentManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CameraConfigurationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ImvgAlertsActionsManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScheduleManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
/**
 * @author Asmodeus
 *
 */
public class CommandIssueServiceManager {
	
	//sumit start for Validating DeviceList before Activating MODE
	private List<Device> devices;
	//sumit end

	public String switchOnOff(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		device.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = null;
		if(device.getCommandParam().equalsIgnoreCase("1")){
			controllerAction = new SwitchOnAction();
		} else{
			controllerAction = new SwitchOffAction();
		}
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		
		return stream.toXML(device);
	}
	

	public String CurtainOpenClose(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		
		Device device =  null;
		DeviceManager devicemanger=new DeviceManager();
		XStream stream = new XStream();
		device =  (Device) stream.fromXML(xml);

		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		device.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(device,null);
		ImonitorControllerAction controllerAction = null;
		String id=device.getGeneratedDeviceId();
		Device devicefromdb=devicemanger.getDeviceByGeneratedDeviceId(id);
		long commandparamFromDb=Long.parseLong(devicefromdb.getCommandParam());
		long commandparamFromWeb=Long.parseLong(device.getCommandParam());
		//int sendingvalue=(int) (commandparamFromDb-commandparamFromWeb);
		
		if(commandparamFromWeb == 0){
			controllerAction = new CurtainopenAction();
			/*sendingvalue=(int) (-commandparamFromDb+commandparamFromWeb);
			if(commandparamFromWeb!=100)
			device.setCommandParam(""+sendingvalue);*/
		} 
		else
		{
			controllerAction = new CurtainCloseAction();
			/*sendingvalue=(int) (-commandparamFromWeb+commandparamFromDb);
			if(commandparamFromWeb!=0)
			device.setCommandParam(""+sendingvalue);
			else
			device.setCommandParam("100");*/
		}
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		return stream.toXML(device);
	}
	

	public String controlAc(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		device.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new AcControlAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		return stream.toXML(device);
	}
	
	public String controlacswing(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		device.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new AcSwingControlAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		return stream.toXML(device);
	}	
	
	
	public String changeAcTemperature(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		//LogUtil.info("actempchange "+stream.toXML(device));
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		device.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new AcTempeartureControlAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		return stream.toXML(device);
	}
	
	
	/* THIS METHOD HAS BEEN MOVED TO DEVICE_SERVICE_MANAGER BY: Sumit
	 * 
	 * public String updateModelNumber(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		device.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new UpdateModelNumberAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		return stream.toXML(device);
	}*/
	
	
	public String updateCurtainModel(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		
		Device device =  null;
		DeviceManager deviceManager = new DeviceManager();
		XStream stream = new XStream();
		String result = "";
		
		
		device =  (Device) stream.fromXML(xml);
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		device.getGateWay().setAgent(agent);
		String generatedDeviceId = device.getGeneratedDeviceId();
		
		MotorConfiguration motorconfi=null;
		motorconfi = (MotorConfiguration)device.getDeviceConfiguration();
	
		
		ActionModel actionModel = new ActionModel(device,null);
		ImonitorControllerAction CurtainModelAction = new CurtainModelUpdate();
		CurtainModelAction.executeCommand(actionModel);
		
		boolean resultFromImvg = IMonitorUtil.waitForResult(CurtainModelAction);
		if(resultFromImvg){
			if(CurtainModelAction.isActionSuccess()){
				
				if(!deviceManager.updateMotorLengthByGeneratedId(generatedDeviceId,motorconfi,device.getMake().getId())){
					
					result = Constants.DB_FAILURE;		//DB failure.
				}else{
					result = Constants.DB_SUCCESS;
				}
				actionModel.setDevice(device);
			}else{
				result = Constants.iMVG_FAILURE;		//gateway returned failure.
			}
		}else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY; //gateway did not respond back.
		}
		
		return stream.toXML(result);//device);
	}
	//bhavya start for HMD device
		@SuppressWarnings({ "unchecked" })
		public String updatehealthcheckmodel(String xml,String xmlacdevice,String schedulexml,String xmlselectedoptionforHMDshedule) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{

			Device device =  null;
			
			DeviceManager deviceManager = new DeviceManager();
			XStream stream = new XStream();
			String result = "";
			
			device =  (Device) stream.fromXML(xml);
			Device acdevice =  (Device) stream.fromXML(xmlacdevice);
			Schedule schedule=(Schedule)stream.fromXML(schedulexml);
			String selectedoptionforHMDshedule=(String) stream.fromXML(xmlselectedoptionforHMDshedule);
			
			
			
			// result = "msg.controller.schedules.0001";
				
			
//				
				
				
				schedule.setActivationStatus(2);
				schedule.setName("HMD:"+device.getGeneratedDeviceId());
				schedule.setDescription(device.getGeneratedDeviceId());
				GateWay g = new GatewayManager().getGateWayByMacId(device.getGateWay().getMacId());
				schedule.setGateWay(g);
				
				ScheduleManager scheduleManager = new ScheduleManager();
			
			List<Schedule> schedules = new DaoManager().get("name","'"+schedule.getName()+"'", Schedule.class);

			
			if((schedules == null)||(schedules.isEmpty()))
					{
				
			//	result = "msg.controller.schedules.0003" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
				
			
			String GeneratedDeviceId=device.getGeneratedDeviceId();
			
			AgentManager agentManager = new AgentManager();
			Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
			device.getGateWay().setAgent(agent);
			
			String generateddeviceid = ""; 
			String sheduletime = ""; 
			long scheduleid;
			List<Object> listforupdatehealth = new ArrayList<Object>(25);
		generateddeviceid=acdevice.getGeneratedDeviceId();
		if(selectedoptionforHMDshedule.equalsIgnoreCase("enable"))
		{
			scheduleManager.saveScheduleWithDetails(schedule);
		 scheduleid =schedule.getId();
		 sheduletime=schedule.getScheduleTime();
		listforupdatehealth.add(sheduletime);
		listforupdatehealth.add(generateddeviceid);
		listforupdatehealth.add(new Long(scheduleid));
			ActionModel actionModel = new ActionModel(device,listforupdatehealth);
			ImonitorControllerAction healthcheckdeviceModelAction = new healthcheckdeviceModelCreate();
			healthcheckdeviceModelAction.executeCommand(actionModel);
		
			boolean resultFromImvg = IMonitorUtil.waitForResult(healthcheckdeviceModelAction);
			if(resultFromImvg){
				if(healthcheckdeviceModelAction.isActionSuccess()){

					if(!deviceManager.updatehealthmodelByGeneratedId(GeneratedDeviceId,device.getMake().getId())){
						                        //DB failure.
						result = Constants.DB_FAILURE;
						scheduleManager.deleteSchedule(schedule);
					}
					else
					{
						result = Constants.DB_SUCCESS;
						
					}
					actionModel.setDevice(device);
				}else{
					result = Constants.iMVG_FAILURE;		//gateway returned failure.
					scheduleManager.deleteSchedule(schedule);
				}
			}
			else{
				result = Constants.NO_RESPONSE_FROM_GATEWAY; //gateway did not respond back.
				scheduleManager.deleteSchedule(schedule);
			}
		}
		else if(selectedoptionforHMDshedule.equalsIgnoreCase("disable"))
		{
			//listforupdatehealth.add(generateddeviceid);
		ActionModel actionModel = new ActionModel(device,generateddeviceid);
		ImonitorControllerAction healthcheckdeviceModelAction = new healthcheckdeviceModel();
		healthcheckdeviceModelAction.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(healthcheckdeviceModelAction);
		if(resultFromImvg){
			if(healthcheckdeviceModelAction.isActionSuccess()){
				
				if(!deviceManager.updatehealthmodelByGeneratedId(GeneratedDeviceId,device.getMake().getId())){
				if(!scheduleManager.deleteSchedule(schedule)){
                                //DB failure.
					result = Constants.DB_FAILURE;
				}
				else
				{
					result = Constants.DB_SUCCESS;
					
				}
				actionModel.setDevice(device);
				}
			}else{
				result = Constants.iMVG_FAILURE;		//gateway returned failure.
				//scheduleManager.deleteSchedule(schedule);
			}
		}
		else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY; //gateway did not respond back.
		}
			
		}
					}
					
			else
			{
				for(Schedule sched : schedules)
				{
					schedule.setId(sched.getId());
					break;
				}
				Schedule schedule2 = scheduleManager.retrieveScheduleDetails(schedule.getId());
				schedule.setActivationStatus(schedule2.getActivationStatus());
				result = "msg.controller.schedules.0021" + Constants.MESSAGE_DELIMITER_1 + schedule.getName();
				scheduleManager.updateScheduleWithDetails(schedule);
				{
				String GeneratedDeviceId=device.getGeneratedDeviceId();
				AgentManager agentManager = new AgentManager();
				Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
				device.getGateWay().setAgent(agent);
				String generateddeviceid = ""; 
				String sheduletime = ""; 
				long scheduleid;
				List<Object> listforupdatehealth = new ArrayList<Object>(25);
			generateddeviceid=acdevice.getGeneratedDeviceId();
			 scheduleid =schedule.getId();
			 sheduletime=schedule.getScheduleTime();
			listforupdatehealth.add(sheduletime);
			listforupdatehealth.add(generateddeviceid);
			listforupdatehealth.add(new Long(scheduleid));
			if(selectedoptionforHMDshedule.equalsIgnoreCase("enable"))
			{
				ActionModel actionModel = new ActionModel(device,listforupdatehealth);
				ImonitorControllerAction healthcheckdeviceModelAction = new healthcheckdeviceModelUpdate();
				healthcheckdeviceModelAction.executeCommand(actionModel);
				
				boolean resultFromImvg = IMonitorUtil.waitForResult(healthcheckdeviceModelAction);
				if(resultFromImvg){
					if(healthcheckdeviceModelAction.isActionSuccess()){
						
						if(!deviceManager.updatehealthmodelByGeneratedId(GeneratedDeviceId,device.getMake().getId())){
							               //DB failure.
							result = Constants.DB_FAILURE;
						}
						else
						{
							result = Constants.DB_SUCCESS;
							
						}
						actionModel.setDevice(device);
					}else{
						result = Constants.iMVG_FAILURE;		//gateway returned failure.
						scheduleManager.updateScheduleWithDetails(schedule2);
					}
				}
				else{
					result = Constants.NO_RESPONSE_FROM_GATEWAY; //gateway did not respond back.
					scheduleManager.updateScheduleWithDetails(schedule2);
				}
			}
			else if(selectedoptionforHMDshedule.equalsIgnoreCase("disable"))
				{
				ActionModel actionModel = new ActionModel(device,listforupdatehealth);
				ImonitorControllerAction healthcheckdeviceModelAction = new healthcheckdeviceModelDelete();
				healthcheckdeviceModelAction.executeCommand(actionModel);
				boolean resultFromImvg = IMonitorUtil.waitForResult(healthcheckdeviceModelAction);
				if(resultFromImvg){
					if(healthcheckdeviceModelAction.isActionSuccess()){
						if(!scheduleManager.deleteSchedule(schedule)){
							LogUtil.error("Unable to update updatehealthmodelByGeneratedId");		                                //DB failure.
							result = Constants.DB_FAILURE;
						}
						else
						{
							result = Constants.DB_SUCCESS;
							
						}
						actionModel.setDevice(device);
					}else{
						result = Constants.iMVG_FAILURE;		//gateway returned failure.
						//scheduleManager.deleteSchedule(schedule);
					}
				}
				else{
					result = Constants.NO_RESPONSE_FROM_GATEWAY; //gateway did not respond back.
				}
					
				}
			}
				
			}
			return stream.toXML(result);//device);		
		}
		//bhavya end HMD  device
		//bhavya start for Scan Now HMD device
				public String ScanNow(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
					
					Device device =  null;
					
					
					XStream stream = new XStream();
					String result = "";
					
					device =  (Device) stream.fromXML(xml);
					
					AgentManager agentManager = new AgentManager();
					Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
					device.getGateWay().setAgent(agent);
					
				
					
					ActionModel actionModel = new ActionModel(device,null);
					ImonitorControllerAction healthcheckScanNowdeviceModelUpdate = new healthcheckScanNowdeviceModelUpdate();
					healthcheckScanNowdeviceModelUpdate.executeCommand(actionModel);
					
					boolean resultFromImvg = IMonitorUtil.waitForResult(healthcheckScanNowdeviceModelUpdate);
					if(resultFromImvg){
						if(healthcheckScanNowdeviceModelUpdate.isActionSuccess()){
							//LogUtil.info("updating device");
							
								result = Constants.DB_SUCCESS;
							
							actionModel.setDevice(device);
						}else{
							result = Constants.iMVG_FAILURE +Constants.MESSAGE_DELIMITER_2+actionModel.getMessage();		//gateway returned failure.
							//LogUtil.info("Scan now in failure msg=="+actionModel.getMessage());
							
						}
					}else{
						result = Constants.NO_RESPONSE_FROM_GATEWAY; //gateway did not respond back.
					}
					
					return stream.toXML(result);//device);
				}
				public String ListImvgAlertTime(String xmlString1,String alertwarning,String alertnormal,String alertfailure) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
					XStream stream = new XStream();
					
					
					String gendeviceid=(String) stream.fromXML(xmlString1);
					String alerttype1=(String) stream.fromXML(alertwarning);
					String alerttype2=(String) stream.fromXML(alertnormal);
					String alerttype3=(String) stream.fromXML(alertfailure);
					
					ImvgAlertsActionsManager ImvgAlertsActionsManager=new ImvgAlertsActionsManager();
					Object[] objects = (Object[]) ImvgAlertsActionsManager.listLatesestHMDAlerts(gendeviceid, alerttype1, alerttype2, alerttype3);
					
					//String xmlValue = stream.toXML(objects);
					
					return stream.toXML(objects);
				}
				
				public String CurrentStatus(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
	
					Device device =  null;
					
					
					XStream stream = new XStream();
					String result = "";
					
					device =  (Device) stream.fromXML(xml);
					
					AgentManager agentManager = new AgentManager();
					Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
					device.getGateWay().setAgent(agent);
					
				
					
					ActionModel actionModel = new ActionModel(device,null);
					ImonitorControllerAction healthcheckCurrentStatusdeviceModelUpdate = new healthcheckCurrentStatusdeviceModelUpdate();
					healthcheckCurrentStatusdeviceModelUpdate.executeCommand(actionModel);
					
					boolean resultFromImvg = IMonitorUtil.waitForResult(healthcheckCurrentStatusdeviceModelUpdate);
					if(resultFromImvg){
						if(healthcheckCurrentStatusdeviceModelUpdate.isActionSuccess()){
							//LogUtil.info("updating device");
							
								result = Constants.DB_SUCCESS;
							
							actionModel.setDevice(device);
						}else{
							result = Constants.iMVG_FAILURE;		//gateway returned failure.
						}
					}else{
						result = Constants.NO_RESPONSE_FROM_GATEWAY; //gateway did not respond back.
					}
				
					return stream.toXML(result);//device);
				} 
				//bhavya end HMD Scan Now device
	//vibhu start
	public boolean deviceArmDisarmStatusUpdateFromMID(String customerId, String genDeviceId, Status armStatus){
		DeviceManager deviceManager =new DeviceManager();
		Device device =  (Device) deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(genDeviceId);
		GateWay gateWay = device.getGateWay();
		String macId = gateWay.getMacId();
		String generatedDeviceId = device.getGeneratedDeviceId();
//		1. Find the agent.
		device = deviceManager.getDeviceWithGateWayAndAgent(generatedDeviceId, macId, customerId);
		Boolean result = false;
		device.setArmStatus(armStatus);
//		Create ArmDisarm Action.
		ActionModel actionModel = new ActionModel(null, device);
		ImonitorControllerAction armDisarmAction = new ArmDisarmAction();
		armDisarmAction.executeCommand(actionModel);
//		2. Wait for success/failure
		boolean resultFromImvg = IMonitorUtil.waitForResult(armDisarmAction);
		if(resultFromImvg){

			if(armDisarmAction.isActionSuccess()){
//				Update the arm status here.
				if(deviceManager.updateDeviceArmStatus(generatedDeviceId, armStatus)){
					result = true;
				}
			}
		}
//		3. update status if success.

		return result;
	}
	//vibhu end
	public String deviceArmDisarmStatusUpdate(String xml){
		XStream  stream = new XStream();
		DeviceManager deviceManager =new DeviceManager();
		Device device =  (Device) stream.fromXML(xml);
		GateWay gateWay = device.getGateWay();
		Customer customer = gateWay.getCustomer();
		String customerId = customer.getCustomerId();
		String macId = gateWay.getMacId();
		String generatedDeviceId = device.getGeneratedDeviceId();
//		1. Find the agent.
		Status armStatus = IMonitorUtil.getStatuses().get(device.getArmStatus().getName());
		device = deviceManager.getDeviceWithGateWayAndAgent(generatedDeviceId, macId, customerId);
		String result = stream.toXML(device);
		device.setArmStatus(armStatus);
//		Create ArmDisarm Action.
		ActionModel actionModel = new ActionModel(null, device);
		ImonitorControllerAction armDisarmAction = new ArmDisarmAction();
		armDisarmAction.executeCommand(actionModel);
//		2. Wait for success/failure
		boolean resultFromImvg = IMonitorUtil.waitForResult(armDisarmAction);
		if(resultFromImvg){
			if(armDisarmAction.isActionSuccess()){
//				Update the arm status here.
				if(deviceManager.updateDeviceArmStatus(generatedDeviceId, armStatus)){
					result = stream.toXML(device);
				}
			}
		}
//		3. update status if success.
		return result;
	}
	
	public String deviceDisarmStatusUpdate(String customerXml,String statusNameXml){
		XStream  stream = new XStream();
		Customer customer = (Customer) stream.fromXML(customerXml);
		String statusName = (String) stream.fromXML(statusNameXml);
//		1. Obtain all the gateways of the customer.
		String customerId = customer.getCustomerId();
		GatewayManager gatewayManager = new GatewayManager();
		List<GateWay> gateWays = gatewayManager.getGateWaysOfCustomer(customerId);
//		2. Send request to each of the gateway as per the arm/disarm
		if(gateWays != null){
			for (GateWay gateWay : gateWays) {
				
				ActionModel actionModel = new ActionModel(null, gateWay);
				ImonitorControllerAction controllerAction = null;
				if(statusName.equalsIgnoreCase(Constants.ARM)){
					controllerAction = new AllArmAction();
				}else if(statusName.equalsIgnoreCase(Constants.DISARM)){
					controllerAction = new AllDisArmAction();
				}
				if(controllerAction != null){
					controllerAction.executeCommand(actionModel);
				}
				
			}
		}
		String result = "msg.controller.deviceDisarmStatusUpdate.0001";
		return result;
	}
	
	// ******************************************************************* sumit start **********************************************************
	@SuppressWarnings("unchecked")
	public String validateMode(String statusXml, String deviceListForValidation){
		XStream stream = new XStream();
		String result = "";
		int count_h = 0;
		int count_s = 0;
		int count_a = 0;
		String status = (String) stream.fromXML(statusXml);
		List<Device> devicesListOfCustomer = (List<Device>) stream.fromXML(deviceListForValidation);
		this.setDevices(devicesListOfCustomer);
		if(status.equalsIgnoreCase("DISARM")){
			for(Device d : this.devices){
				if(d.getModeHome()){
					count_h += 1;
				}
			}
			if(count_h < 1){
				result = "HOME_FAIL";
			}
		}else if(status.equalsIgnoreCase("STAY")){
			for(Device d : this.devices){
				if(d.getModeStay()){
					count_s += 1;
				}
			}
			if(count_s < 1){
				result = "STAY_FAIL";
			}
		}else if(status.equalsIgnoreCase("ARM")){
			for(Device d : this.devices){
				if(d.getModeAway()){
					count_a += 1;
				}
			}
			if(count_a < 1){
				result = "AWAY_FAIL";
			}
		}
		return result;
	}
	
	public String activateMode(String customerXml,String statusNameXml){
		XStream  stream = new XStream();
		Customer customer = (Customer) stream.fromXML(customerXml);
		String statusName = (String) stream.fromXML(statusNameXml);
//		1. Obtain all the gateways of the customer.
		String customerId = customer.getCustomerId();
		return activateModeInternal(customerId,statusName );
	}
	//********pari added for preset
	public String activateSet(String customerXml,String presetNumberXml,String DeviceidXml){
		XStream  stream = new XStream();
		//LogUtil.info("Am in strt activateSet of cmdissu serv mgr");

		Customer customer = (Customer) stream.fromXML(customerXml);
		String presetNumber = (String) stream.fromXML(presetNumberXml);
		String deviceid = (String) stream.fromXML(DeviceidXml);
		
//		1. Obtain all the gateways of the customer.
		//LogUtil.info("Am in end of activateSet of cmdissu serv mgr");
		String customerId = customer.getCustomerId();
		return activateSetInternal(customerId,presetNumber,deviceid );
	}
	
	public String activateMove(String customerXml,String presetNumberXml,String DeviceidXml){
		XStream  stream = new XStream();
		Customer customer = (Customer) stream.fromXML(customerXml);
		String presetNumber = (String) stream.fromXML(presetNumberXml);
		String deviceid = (String) stream.fromXML(DeviceidXml);

//		1. Obtain all the gateways of the customer.
		
		String customerId = customer.getCustomerId();
		return activateMoveInternal(customerId,presetNumber,deviceid );
	}
	
	public String activateSetInternal(String customerId, String preNumber, String deviceid)
	{	
		String result = "failure";
		try
		{
			GatewayManager gatewayManager = new GatewayManager();
			List<GateWay> gateWays = gatewayManager.getGateWaysOfCustomer(customerId);
		
			if(gateWays != null){
				for (GateWay gateWay : gateWays) {
	
					
					DeviceManager deviceManager = new DeviceManager();
					Device device = deviceManager.getDeviceWithConfiguration(deviceid);
					device.setGateWay(gateWay);
					device.setDeviceId(deviceid);
					if (device.getDeviceType() != null) device.getDeviceType().setAlertTypes(null);
					
					
					long id=device.getDeviceConfiguration().getId();
					CameraConfigurationManager cam=new CameraConfigurationManager();
					CameraConfiguration cameraConfiguration=cam.getCameraConfiguration(id);
					H264CameraConfiguration h264cameraconf = cam.getH264CameraConfigurationById(id);

					if(device.getModelNumber().equalsIgnoreCase("H264PT")){
						
						long preset;
						long j=1;
		
						preset=Integer.parseInt(preNumber);		
						j= j<<(preset-1);						
		
						long presetVal = h264cameraconf.getPresetvalue();
		
						j= j | presetVal;
						
						h264cameraconf.setPresetvalue(j);
						h264cameraconf.setAdminPassword("ABCD");
						
						//long Setedpreset = h264cameraconf.getPresetvalue();
		
						device.setDeviceConfiguration(h264cameraconf);
												
					}else{
						long preset;
						long j=1;
		
						preset=Integer.parseInt(preNumber);
		
						
							j= j<<(preset-1);					
		
						long presetVal = cameraConfiguration.getPresetvalue();
		
						j= j | presetVal;
						
						cameraConfiguration.setPresetvalue(j);
						cameraConfiguration.setAdminPassword("ABCD");
						
						//long Setedpreset = cameraConfiguration.getPresetvalue();
		
						device.setDeviceConfiguration(cameraConfiguration);
					}				
					
					ActionModel actionModel = new ActionModel(device, preNumber);
					ImonitorControllerAction controllerAction = new CameraSetControlAction();
					
					if(controllerAction != null){
						//LogUtil.info("Am inside active set internal controllerAction");
						controllerAction.executeCommand(actionModel);
						boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
						//LogUtil.info("result of bool is-"+resultFromImvg);
						if(resultFromImvg){
							if(controllerAction.isActionSuccess()){
								result = "setup.devices.msg.preset.set";
								deviceManager.updateDevice(device);
								//LogUtil.info(st1.toXML(cameraConfiguration));
							
								if(device.getModelNumber().equalsIgnoreCase("H264PT"))cam.updateH264CameraConfiguration(h264cameraconf);
								else cam.updateCameraConfiguration(cameraConfiguration);

							}
							else{//iMVG returned a failure
								result = "setup.devices.msg.preset.set1";// + controllerAction.getActionModel().getMessage();
							}
						}
						else{ 
							result = "setup.devices.msg.preset.set2";
							//LogUtil.info(deviceManager.updateDevice(device));
							//LogUtil.info(cam.updateCameraConfiguration(cameraConfiguration));
							}
							
					}else{
						result = "setup.devices.msg.preset.set3";
					}
				}
			}else{
				result = "setup.devices.msg.preset.set3";
			}
		}
		catch(Exception e)
		{
			
			LogUtil.info("Caught exception", e);
		}
		
		return result;
	}
	//Sumit: TBD Camera Preset Internationalization not handled.	
	public String activateMoveInternal(String customerId,String preNumber,String deviceid)
	{	
		String result = null;
		GatewayManager gatewayManager = new GatewayManager();
		List<GateWay> gateWays = gatewayManager.getGateWaysOfCustomer(customerId);
		
		if(gateWays != null){
			for (GateWay gateWay : gateWays) {
				Device d = new Device();
				d.setGateWay(gateWay);
				d.setGeneratedDeviceId(deviceid);
				d.setCommandParam(preNumber);
				ActionModel actionModel = new ActionModel(d, null);
				ImonitorControllerAction controllerAction = null;
					controllerAction = new CameraMoveControlAction();
				
				if(controllerAction != null){
					//LogUtil.info("Am inside active move internal controllerAction");
					controllerAction.executeCommand(actionModel);
					boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
					if(resultFromImvg){
						if(controllerAction.isActionSuccess()){
						result = "setup.devices.msg.preset.set5";
						}
						else{//iMVG returned a failure
							result = "setup.devices.msg.preset.set6";
						}
					}
					else{ 
						result = "setup.devices.msg.preset.set4";
						}
						
				}
			}
		}else{
			result = "setup.devices.msg.preset.set3";
		}
		
		return result;
	}
	//********pari end 
		
	public String activateModeInternal(String customerId,String statusName)
	{	
		String result = null;
		GatewayManager gatewayManager = new GatewayManager();
		List<GateWay> gateWays = gatewayManager.getGateWaysOfCustomer(customerId);
//		2. Send request to each of the gateway to activate ARM/DISARM/STAY
		if(gateWays != null){
			for (GateWay gateWay : gateWays) {
				ActionModel actionModel = new ActionModel(null, gateWay);
				ImonitorControllerAction controllerAction = null;
				if(statusName.equalsIgnoreCase(Constants.ARM)){
					controllerAction = new AllArmAction();
				}else if(statusName.equalsIgnoreCase(Constants.DISARM)){
					controllerAction = new AllDisArmAction();
				}else if(statusName.equalsIgnoreCase(Constants.STAY)){
					controllerAction = new SelectiveArmAction();
				}
				if(controllerAction != null){
					controllerAction.executeCommand(actionModel);
					boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
					if(resultFromImvg){
						if(controllerAction.isActionSuccess()){
						result = "msg.controller.activateModeInternal.0004";
						}
						else{//iMVG returned a failure
							result = "msg.controller.activateModeInternal.0003"+ Constants.MESSAGE_DELIMITER_1 + controllerAction.getActionModel().getMessage();
						}
					}
					else{ 
						result = "msg.controller.activateModeInternal.0002";
						}
						
				}
				else{
					result = "msg.controller.activateModeInternal.0001";
				}
			}
		}else{
			result = "msg.controller.activateModeInternal.0001";
		}
			
		return result;
	}
	// ******************************************************************** sumit end ***********************************************************
		
	public String retrieveTemperature(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		device.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new RetrieveTemperatureAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		
		return stream.toXML(device);
	}
	
	public String dimmer(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		device.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new DimmerLevelChangeAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		
		return stream.toXML(device);
	}
	
	public String temperatureSensor(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
		Device device =  (Device) stream.fromXML(xml);
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.generateTransactionId()));
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayByDevice(device);
		queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,device.getGeneratedDeviceId()));
		queue.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,device.getCommandParam()));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		requestProcessor.processRequest(messageInXml);
		return null;
	}
	
	public String getLiveStream(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new GetLiveStreamAction();
		controllerAction.executeCommand(actionModel);
		String url = Constants.FAILURE;
		IMonitorUtil.waitForResult(controllerAction);
		url = IMonitorUtil.convertToString(controllerAction.getActionModel().getData());
//		return the url
		return url;
	}
	
	public String listAllStreamsWithClients(){
		BroadCasterCommunicator broadCasterCommunicator = new BroadCasterCommunicator();
		List<Agent> agents = new AgentManager().listOfAgents();
		String results = broadCasterCommunicator.listAllStreamsWithClients(agents);
		return results;
	}
	
	public String killLiveStream(String deviceId)
	{
		
		BroadCasterCommunicator broadCasterCommunicator = new BroadCasterCommunicator();
		Agent agent = new DeviceManager().getAgentOfDevice(deviceId);
		String results = broadCasterCommunicator.killLiveStream(deviceId, agent);
		return results;
	}

	public String getStoredStream(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		String macId = device.getGateWay().getMacId();
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getCustomerIdWithReceiverIpAndPortAndFtp(macId);
		device.setGateWay(gateWay);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new GetStoredStreamAction();
		controllerAction.executeCommand(actionModel);
//		How much time we should wait .... Ask to Sundaresh !!!
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		
		return null;
	}
	
	public String captureIamge(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		String macId = device.getGateWay().getMacId();
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getCustomerIdWithReceiverIpAndPortAndFtp(macId);
		device.setGateWay(gateWay);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new CaptureImageAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		
		return null;
	}
	
	public String commandSuccessUpdater(String xml){
		Queue<KeyValuePair> events;
		try {
			LogUtil.info("commandSuccessUpdater xml="+xml);
			events = IMonitorUtil.extractCommandsQueueFromXml(xml);
			String transactionId = IMonitorUtil.commandId(events, Constants.TRANSACTION_ID);
//			We remove it from session after doing the operation in the action class
			ImonitorControllerAction controllerAction = (ImonitorControllerAction) IMonitorSession.get(transactionId);
			LogUtil.info("controllerAction ="+controllerAction);
			controllerAction.executeSuccessResults(events);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//LogUtil.info("p15hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
		return null;
	}
	
	public String commandFailureUpdater(String xml){
		LogUtil.info("commandFailureUpdater xml="+xml);
		Queue<KeyValuePair> events;
		try {
			events = IMonitorUtil.extractCommandsQueueFromXml(xml);
			String transactionId = IMonitorUtil.commandId(events, Constants.TRANSACTION_ID);
			ImonitorControllerAction controllerAction = (ImonitorControllerAction) IMonitorSession.remove(transactionId);
			LogUtil.info("controllerAction ="+controllerAction);
			controllerAction.executeFailureResults(events);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String deleteDevice(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		XStream stream=new XStream();
		String generatedDeviceId = (String)stream.fromXML(xml);
		DeviceManager deviceManager=new DeviceManager();
		Device device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceId);
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();;
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		GateWay gateWay = device.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair(Constants.REMOVE_DEVICE, null));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
//		ControlModel controlModel = new ControlModel(trasactionId, Constants.REMOVE_DEVICE, generatedDeviceId);
//		IMonitorSession.put(trasactionId, controlModel);
		requestProcessor.processRequest(messageInXml, gateWay);
		
		//sumit start: REMOVAL MODE TIME OUT
		//1. Use device discovery mode to send gateway to device removal mode. 
		//We are not adding new status into database for removal mode, instead we are using device discovery mode.
		GatewayManager gatewayManager = new GatewayManager();
		gatewayManager.updateGateWayToStatusremovalmode(gateWay.getMacId());
		
		//2.Wait for Device Remove TimeOut
		GatewayServiceManager gatewayServiceManager = new GatewayServiceManager();
		gatewayServiceManager.waitForDeviceRemoveTimeOut(gateWay.getMacId());
		//sumit end: REMOVAL MODE TIME OUT
		return null;
	}
	//bhavya start
	public String deleteDeviceup(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		XStream stream=new XStream();
		String macId = (String)stream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(macId);
		String gatewayStatus=gateWay.getStatus().getName();
	
		if (gatewayStatus.equalsIgnoreCase("Online"))
		{
			
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();;
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,macId));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,macId));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair(Constants.REMOVE_DEVICE, null));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
     	requestProcessor.processRequest(messageInXml, gateWay);
		
		//sumit start: REMOVAL MODE TIME OUT
		//1. Use device discovery mode to send gateway to device removal mode.
		//We are not adding new status into database for removal mode, instead we are using device discovery mode.
		gatewayManager.updateGateWayToStatusremovalmode(macId);
     	
     	//2.Wait for Device Remove TimeOut
		GatewayServiceManager gatewayServiceManager = new GatewayServiceManager();
		gatewayServiceManager.waitForDeviceRemoveTimeOut(macId);
		//sumit end: REMOVAL MODE TIME OUT
		return null;
		}
		else
		{
			
			String result="offline";
			return result;
		}
		
	}
	//bhavya end
	
//vibhu start
	public String deleteAllDevices(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		XStream stream=new XStream();
		String macId = (String)stream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(macId);
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.IMVG_DEFAULTSET));
		String trasactionId = IMonitorUtil.generateTransactionId();;
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,macId));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
     	requestProcessor.processRequest(messageInXml, gateWay);
		
     	//sumit start: REMOVAL MODE TIME OUT
     	//1. Use device discovery mode to send gateway to device removal mode.
     	//We are not adding new status into database for removal mode, instead we are using device discovery mode.
     	gatewayManager.updateGateWayToStatusDeviceDiscovery(macId);
     	
     	//2.Wait for Device Remove TimeOut
		GatewayServiceManager gatewayServiceManager = new GatewayServiceManager();
		gatewayServiceManager.waitForDeviceRemoveTimeOut(macId);
		//sumit end: REMOVAL MODE TIME OUT
		return null;
	}

	public String nupdateDevice(String xml)  {
		
		Device device = (Device) new XStream().fromXML(xml);
		DeviceManager deviceManager=new DeviceManager();
		Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		GateWay gateWay = deviceDB.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,deviceDB.getGeneratedDeviceId()));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair(Constants.NEIGHBOUR_UPDATE, ""+device.getSwitchType()));
		queue.add(new KeyValuePair(Constants.PARAM_VAL,"0"));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
//		ControlModel controlModel = new ControlModel(trasactionId, Constants.REMOVE_DEVICE, generatedDeviceId);
//		IMonitorSession.put(trasactionId, controlModel);
		requestProcessor.processRequest(messageInXml, gateWay);
		return null;
	}

	
//vibhu end	

	public String deviceStatusUpdate(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		String result="msg.controller.rulesConfiguremode.0005";
		XStream stream = new XStream();
		Device device =new Device();
		String gateWayId =  (String) stream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayByMacId(gateWayId);
		device.setGateWay(gateWay);
		ActionModel actionModel = new ActionModel(device,null);
		ImonitorControllerAction UpadteAction = new UpdateAction();
		UpadteAction.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(UpadteAction);
		if(resultFromImvg)
		{
			if(UpadteAction.isActionSuccess())
			{
				result = "msg.controller.Configuremode.0001";
			}		
		else
			{
				result = "msg.controller.Configuremode.0002";
			}
		}
	
		return result;
	}
	
	
	public String updateCameraSystemConfiguration(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String message="";
		XStream stream=new XStream();
		Device device1 = (Device)stream.fromXML(xml);
		DeviceConfiguration cameraConfiguration = device1.getDeviceConfiguration();
		DeviceManager deviceManager=new DeviceManager();
		Device device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device1.getGeneratedDeviceId());
		device.setDeviceConfiguration(cameraConfiguration);
		
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = null;
		
		if(device.getModelNumber().equalsIgnoreCase("H264PT")){
			controllerAction = new CameraH264ConfParamUpdatorAction();
		}else{
			controllerAction = new CameraConfParamUpdatorAction();
		}
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg)
		{
			if(controllerAction.isActionSuccess())
			{
				message = "msg.controller.Configurecamera.0001";
			}		
		}
		else
		message = "msg.controller.Configuremode.0002";
		return message;
	}
	
	public String updateCameraNightVisionControl(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		device.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new CameraNightVisionControlAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		
		return stream.toXML(device);
	}
	
	public String updatePanTiltControl(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		
		XStream stream = new XStream();
//		Device as input - It has generatedDeviceId and imvg Id.
		Device device =  (Device) stream.fromXML(xml);
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		device.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new CameraPanTiltControlAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		device = controllerAction.getActionModel().getDevice();
		
		return stream.toXML(device);
	}
	
	public String remoteReboot(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		XStream stream = new XStream();
		Device device = new Device();
//		Device as input - It has generatedDeviceId and imvg Id.
		String gateWayId =  (String) stream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayByMacId(gateWayId);
		device.setGateWay(gateWay);
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(gateWayId);
		device.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new IMVGRebootAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		//device = controllerAction.getActionModel().getDevice();
		String result= "yes";
		return stream.toXML(result);
	}
	
	public String EnableStatusUpdate(String xml)
	{
		
		XStream  stream = new XStream();
		String result = null;
		DeviceManager deviceManager =new DeviceManager();
		Device device =  (Device) stream.fromXML(xml);
		GateWay gateWay = device.getGateWay();
		Customer customer = gateWay.getCustomer();
		String customerId = customer.getCustomerId();
		String macId = gateWay.getMacId();
		String generatedDeviceId = device.getGeneratedDeviceId();

//		1. Find the agent.
		String enableStatus = device.getEnableStatus();
		
		device = deviceManager.getDeviceWithGateWayAndAgent(generatedDeviceId, macId, customerId);
		
		device.setEnableStatus(enableStatus);
		
//		Create ArmDisarm Action.
		ActionModel actionModel = new ActionModel(device,null);
		ImonitorControllerAction enableAction = null;
		if(enableStatus.equals("0"))
		{
			enableAction = new DisableAction();
		}
		else
		{
			enableAction = new EnableAction();
		}
		
		
		enableAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(enableAction);
		device = enableAction.getActionModel().getDevice();
		result = stream.toXML(device);
		return result;
	
} 
	//sumit start
		public List<Device> getDevices() {
			return devices;
		}
		
		public void setDevices(List<Device> devices) {
			this.devices = devices;
		}
	//sumit end

		
		public String ConfigSuccessUpdater(String xml){
			Queue<KeyValuePair> events;
			DeviceManager deviceManager = new DeviceManager();
			try {
				events = IMonitorUtil.extractCommandsQueueFromXml(xml);
				String ReftransactionId = IMonitorUtil.commandId(events, Constants.REF_TRANSACTION_ID);
				String generatedDeviceId = IMonitorUtil.commandId(events, Constants.DEVICE_ID);
				ImonitorControllerAction controllerAction = (ImonitorControllerAction) IMonitorSession.get(ReftransactionId);
				ActionModel RefAction=controllerAction.getActionModel();
				Device device=RefAction.getDevice();
				
				
				String gateWayId = device.getGateWay().getMacId();
				CameraConfiguration cameraConfiguration = (CameraConfiguration) device.getDeviceConfiguration();
				deviceManager.updateCameraConfiguration(generatedDeviceId, gateWayId, cameraConfiguration);
				IMonitorSession.remove(ReftransactionId);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public String lockUnlock(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
			XStream stream = new XStream();
			Device device =  (Device) stream.fromXML(xml);
			AgentManager agentManager = new AgentManager();
			Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
			device.getGateWay().setAgent(agent);
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = null;
			if(device.getCommandParam().equalsIgnoreCase("1")){
				controllerAction = new Doorlock();
			} else{
				controllerAction = new DoorUnlock();
			}
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			device = controllerAction.getActionModel().getDevice();
			return stream.toXML(device);
		}
		
		//bhavya starts for ac thermostats
		public String changeFanMode(String deviceXml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
			XStream stream = new XStream();
				Device device = (Device) stream.fromXML(deviceXml);
				
				//String modelNumber = device.getModelNumber();
				String generatedDeviceId = device.getGeneratedDeviceId();
				DeviceManager deviceManager = new DeviceManager();
				Device deviceFromDB = deviceManager.getDeviceWithConfiguration(generatedDeviceId);
				acConfiguration acconfig;
				if(null == deviceFromDB.getDeviceConfiguration())
				{
					acconfig = new acConfiguration();
				}
				else
				{
					acconfig = (acConfiguration) new DaoManager().get(deviceFromDB.getDeviceConfiguration().getId(), acConfiguration.class);
				}
				deviceFromDB.setDeviceConfiguration(acconfig);
				acconfig = (acConfiguration)deviceFromDB.getDeviceConfiguration();
				acconfig.setFanModeValue(((acConfiguration)device.getDeviceConfiguration()).getFanModeValue());
				
			
//			Device as input - It has generatedDeviceId and imvg Id.
			//Device device =  (Device) stream.fromXML(xml);
			AgentManager agentManager = new AgentManager();
			Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
			device.getGateWay().setAgent(agent);
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new AcFanModeControlAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			device = controllerAction.getActionModel().getDevice();
		
		return stream.toXML(device);
		//bhavya end ac thermostats
		}
		
		public String AllONoff(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
			XStream stream = new XStream();
			Device device = new Device();
			GatewayManager gatewayManager = new GatewayManager();
			AgentManager agentManager = new AgentManager();

			GateWay gateWayId =  (GateWay) stream.fromXML(xml);
			GateWay gateWay = gatewayManager.getGateWayByMacId(gateWayId.getMacId());
			gateWay.setAllOnOffStatus(gateWayId.getAllOnOffStatus());
			device.setGateWay(gateWay);
		
			Agent agent = agentManager.getReceiverIpAndPort(gateWayId.getMacId());
			device.getGateWay().setAgent(agent);
			
			
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new AllonoffAction();
			controllerAction.executeCommand(actionModel);
			
			
			boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
		//	LogUtil.info("resultFromImvg"+resultFromImvg);
			String result;
			if(resultFromImvg){
				if(controllerAction.isActionSuccess())
				{
						result= "YES";
				}else{
					result = "NO"	;	//gateway returned failure.
				}
			}else{
				result = "NO"; //gateway did not respond back.
			}
			
		//	LogUtil.info(result); 
			return stream.toXML(result);//device);
			
			
		
			
		}

		public String ConfigureAndUpdateSiren(String xml)  {
						
			Device device = (Device) new XStream().fromXML(xml);
			SirenConfiguration motorconfi=null;
			motorconfi = (SirenConfiguration)device.getDeviceConfiguration();
			DeviceManager deviceManager=new DeviceManager();
			Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
			Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
			queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_UPDATE_MODEL ));
			String trasactionId = IMonitorUtil.generateTransactionId();
			queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
			GateWay gateWay = deviceDB.getGateWay();
			queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
			queue.add(new KeyValuePair(Constants.DEVICE_ID,deviceDB.getGeneratedDeviceId()));
			queue.add(new KeyValuePair(Constants.CONFIG_SIREN,"1"));
			queue.add(new KeyValuePair(Constants.SIREN_STROBE,"0"));
			queue.add(new KeyValuePair(Constants.CONFIG_VAL, ""+motorconfi.getSirenType()));
			queue.add(new KeyValuePair(Constants.SIREN_TIMEOUT,"1"));
			queue.add(new KeyValuePair(Constants.CONFIG_VAL1, ""+motorconfi.getTimeOutValue()));
			RequestProcessor requestProcessor = new RequestProcessor();
			String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
//			ControlModel controlModel = new ControlModel(trasactionId, Constants.REMOVE_DEVICE, generatedDeviceId);
//			IMonitorSession.put(trasactionId, controlModel);
			requestProcessor.processRequest(messageInXml, gateWay);
			return null;
		}
	
	
	public String updatepasswordfordoorlock(String xml) throws Exception {
 		XStream xStream = new XStream();
		DoorLockConfiguration doorLockConfiguration = (DoorLockConfiguration) xStream.fromXML(xml);
		String code = doorLockConfiguration.getUserpassword();
		Device device = doorLockConfiguration.getDevice();
		DeviceManager deviceManager=new DeviceManager();
		Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
		GateWay gateWay = deviceDB.getGateWay();
		String imvgId = device.getGateWay().getMacId();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,imvgId ));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,device.getGeneratedDeviceId()));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair(Constants.SET_USER_CODE,null));
		queue.add(new KeyValuePair(Constants.USER_CODE,code));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		requestProcessor.processRequest(messageInXml, gateWay);
		return null;

}
	
	public String setacbrandcode(String acSetcode, String xml ) throws Exception {
 		XStream xStream = new XStream();
	
 		Device device =  (Device) xStream.fromXML(xml);
 		String code = (String) xStream.fromXML(acSetcode);
		DeviceManager deviceManager=new DeviceManager();
		String generatedDeviceId = device.getGeneratedDeviceId();
		Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
		GateWay gateWay = deviceDB.getGateWay();
		String imvgId = device.getGateWay().getMacId();
	
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,imvgId ));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair(Constants.AUTO_SEARCH_AC,null));
		queue.add(new KeyValuePair(Constants.CODE_NO,code));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		requestProcessor.processRequest(messageInXml, gateWay);
		return null;

}
	
	
	public String setavbrandcode(String acSetcode, String xml ) throws Exception {
 		XStream xStream = new XStream();
	
 		Device device =  (Device) xStream.fromXML(xml);
 		String code = (String) xStream.fromXML(acSetcode);
		DeviceManager deviceManager=new DeviceManager();
		String generatedDeviceId = device.getGeneratedDeviceId();
		Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
		GateWay gateWay = deviceDB.getGateWay();
		String imvgId = device.getGateWay().getMacId();
	
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,imvgId ));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair(Constants.AUTO_SEARCH_AV,null));
		//queue.add(new KeyValuePair(Constants.CODE_NO,code));
		queue.add(new KeyValuePair(Constants.CODE_NO,code));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		requestProcessor.processRequest(messageInXml, gateWay);
		return null;

}

public String setAcToLearnValue(String acValuetoLearn, String xml ) throws Exception {
 		XStream xStream = new XStream();
		Device device =  (Device) xStream.fromXML(xml);
 		String learnValue = (String) xStream.fromXML(acValuetoLearn);
		DeviceManager deviceManager=new DeviceManager();
		String generatedDeviceId = device.getGeneratedDeviceId();
		Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
		GateWay gateWay = deviceDB.getGateWay();
		String imvgId = device.getGateWay().getMacId();
	
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,imvgId ));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair(Constants.AC_LEARN_IR_KEY,learnValue));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		requestProcessor.processRequest(messageInXml, gateWay);
		return null;

}

public String loginpasscheckapi(String userid,String password,String id){
	String result=null;
	CustomerManager customerManager=new CustomerManager();
	boolean user=customerManager.loginpasscheckapi(userid,password,id);
	
	if(user==true){
		result="True";
	}else{
		result="False";}
		return result;	
}

//Control VIA Indoor Unit 
public String controlIndoorUnit(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
	XStream stream = new XStream();
//	Device as input - It has generatedDeviceId and imvg Id.
	Device device =  (Device) stream.fromXML(xml);
	AgentManager agentManager = new AgentManager();
	Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
	device.getGateWay().setAgent(agent);
	
	DeviceManager deviceManager = new DeviceManager();
	Device deviceFromDB = deviceManager.getDeviceWithConfiguration(device.getGeneratedDeviceId());
	IndoorUnitConfiguration unitConfiguration=(IndoorUnitConfiguration) new DaoManager().get(deviceFromDB.getDeviceConfiguration().getId(), IndoorUnitConfiguration.class);
	device.setDeviceConfiguration(unitConfiguration);
	ActionModel actionModel = new ActionModel(device, null);
	ImonitorControllerAction controllerAction = new IndoorUnitActionControl();
	controllerAction.executeCommand(actionModel);
	IMonitorUtil.waitForResult(controllerAction);
	device = controllerAction.getActionModel().getDevice();
	
	return stream.toXML(device);
}

//fanDirectionControl
public String fanDirectionControl(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
	XStream stream = new XStream();
	Device device =  (Device) stream.fromXML(xml);
	String generatedDeviceId = device.getGeneratedDeviceId();
	DeviceManager deviceManager = new DeviceManager();
	Device deviceFromDB = deviceManager.getDeviceWithConfiguration(generatedDeviceId);
	IndoorUnitConfiguration unitConfiguration;
	unitConfiguration = (IndoorUnitConfiguration) new DaoManager().get(deviceFromDB.getDeviceConfiguration().getId(), IndoorUnitConfiguration.class);
	
	device.setDeviceConfiguration(unitConfiguration);
	AgentManager agentManager = new AgentManager();
	Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
	device.getGateWay().setAgent(agent);
	ActionModel actionModel = new ActionModel(device, null);
	ImonitorControllerAction controllerAction = new IDUFanDirectionControl();
	controllerAction.executeCommand(actionModel);
	IMonitorUtil.waitForResult(controllerAction);
	device = controllerAction.getActionModel().getDevice();
	
	return stream.toXML(device);
}

//fanVolumeControl
public String fanVolumeControl(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
	XStream stream = new XStream();
	Device device =  (Device) stream.fromXML(xml);
	
	String generatedDeviceId = device.getGeneratedDeviceId();
	DeviceManager deviceManager = new DeviceManager();
	Device deviceFromDB = deviceManager.getDeviceWithConfiguration(generatedDeviceId);
	IndoorUnitConfiguration unitConfiguration;
	if(null == deviceFromDB.getDeviceConfiguration())
	{
		
		unitConfiguration = new IndoorUnitConfiguration();
	}
	else
	{
		unitConfiguration = (IndoorUnitConfiguration) new DaoManager().get(deviceFromDB.getDeviceConfiguration().getId(), IndoorUnitConfiguration.class);
	
	}
	deviceFromDB.setDeviceConfiguration(unitConfiguration);
	unitConfiguration = (IndoorUnitConfiguration)deviceFromDB.getDeviceConfiguration();
	//unitConfiguration.setFanModeValue(((IndoorUnitConfiguration)device.getDeviceConfiguration()).getFanModeValue());
	
	AgentManager agentManager = new AgentManager();
	Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
	device.getGateWay().setAgent(agent);
	ActionModel actionModel = new ActionModel(device, null);
	ImonitorControllerAction controllerAction = new IDUFanVolumeControl();
	controllerAction.executeCommand(actionModel);
	IMonitorUtil.waitForResult(controllerAction);
	device = controllerAction.getActionModel().getDevice();
	
	return stream.toXML(device);
}

/*Added by apoorva
 * For controling IDU temperature
 */
public String changeIduTemperature(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
	XStream stream = new XStream();
	Device device =  (Device) stream.fromXML(xml);
	AgentManager agentManager = new AgentManager();
	Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
	device.getGateWay().setAgent(agent);
	ActionModel actionModel = new ActionModel(device, null);
	ImonitorControllerAction controllerAction = new IduTemperatureControlAction();
	controllerAction.executeCommand(actionModel);
	IMonitorUtil.waitForResult(controllerAction);
	device = controllerAction.getActionModel().getDevice();
	return stream.toXML(device);
}
public String CurtainOpenStart(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
	XStream stream = new XStream();
	
	Device device =  (Device) stream.fromXML(xml);
	Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
	queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
	queue.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.generateTransactionId()));
	DeviceManager deviceManager =  new DeviceManager();
	Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
	GateWay gateWay = deviceDB.getGateWay();
	
	queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
	queue.add(new KeyValuePair(Constants.DEVICE_ID,device.getGeneratedDeviceId()));
	queue.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,"1"));
	queue.add(new KeyValuePair(Constants.ZW_CONTROL_TYPE,"1"));
	RequestProcessor requestProcessor = new RequestProcessor();
	String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
	
	requestProcessor.processRequest(messageInXml, gateWay);
	return null;
}

public String CurtainOpenStop(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
	XStream stream = new XStream();
	Device device =  (Device) stream.fromXML(xml);
	Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
	queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
	queue.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.generateTransactionId()));
	DeviceManager deviceManager =  new DeviceManager();
	Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
	GateWay gateWay = deviceDB.getGateWay();
	queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
	queue.add(new KeyValuePair(Constants.DEVICE_ID,device.getGeneratedDeviceId()));
	queue.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,"0"));
	queue.add(new KeyValuePair(Constants.ZW_CONTROL_TYPE,"1"));
	RequestProcessor requestProcessor = new RequestProcessor();
	String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
	requestProcessor.processRequest(messageInXml,gateWay);
	return null;
}

public String CurtainCloseStart(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
	XStream stream = new XStream();
	
	Device device =  (Device) stream.fromXML(xml);
	Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
	queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
	queue.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.generateTransactionId()));
	DeviceManager deviceManager =  new DeviceManager();
	Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
	GateWay gateWay = deviceDB.getGateWay();
	
	queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
	queue.add(new KeyValuePair(Constants.DEVICE_ID,device.getGeneratedDeviceId()));
	queue.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,"0"));
	queue.add(new KeyValuePair(Constants.ZW_CONTROL_TYPE,"2"));
	RequestProcessor requestProcessor = new RequestProcessor();
	String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
	requestProcessor.processRequest(messageInXml, gateWay);
	return null;
}

public String CurtainCloseStop(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
	XStream stream = new XStream();
	Device device =  (Device) stream.fromXML(xml);
	Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
	queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
	queue.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.generateTransactionId()));
	DeviceManager deviceManager =  new DeviceManager();
	Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
	GateWay gateWay = deviceDB.getGateWay();
	queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
	queue.add(new KeyValuePair(Constants.DEVICE_ID,device.getGeneratedDeviceId()));
	queue.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,"1"));
	queue.add(new KeyValuePair(Constants.ZW_CONTROL_TYPE,"2"));
	RequestProcessor requestProcessor = new RequestProcessor();
	String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
	
	requestProcessor.processRequest(messageInXml,gateWay);
	return null;
}
public String CurtainSliderControl(String xml) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
	
	Device device =  null;
	DeviceManager devicemanger=new DeviceManager();
	XStream stream = new XStream();
	device =  (Device) stream.fromXML(xml);

	AgentManager agentManager = new AgentManager();
	Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
	device.getGateWay().setAgent(agent);
	ActionModel actionModel = new ActionModel(device,null);
	ImonitorControllerAction controllerAction = null;
	String id=device.getGeneratedDeviceId();
	Device devicefromdb=devicemanger.getDeviceByGeneratedDeviceId(id);
	long commandparamFromDb=Long.parseLong(devicefromdb.getCommandParam());
	long commandparamFromWeb=Long.parseLong(device.getCommandParam());
	int sendingvalue=(int) (commandparamFromDb-commandparamFromWeb);
	
	if(commandparamFromWeb >= commandparamFromDb){
		controllerAction = new CurtainOpenSliderAction();
		/*sendingvalue=(int) (-commandparamFromDb+commandparamFromWeb);
		if(commandparamFromWeb!=100)
		device.setCommandParam(""+sendingvalue);*/
	} 
	else
	{
		controllerAction = new CurtainCloseSliderAction();
		/*sendingvalue=(int) (-commandparamFromWeb+commandparamFromDb);
		if(commandparamFromWeb!=0)
		device.setCommandParam(""+sendingvalue);
		else
		device.setCommandParam("100");*/
	}
	controllerAction.executeCommand(actionModel);
	IMonitorUtil.waitForResult(controllerAction);
	device = controllerAction.getActionModel().getDevice();
	return stream.toXML(device);
}

//Touch Panel
public String nupdateDeviceForMulipleDevices(String devicexml,String xml)  {
	
	Device device = (Device) new XStream().fromXML(devicexml);
	XStream stream=new XStream();
	List<Device> devices = (List<Device>) stream.fromXML(xml);
	
	Device device1=devices.get(0);
	Device device2=devices.get(1);
	Device device3=devices.get(2);
	Device device4=devices.get(3);
	Device device5=devices.get(4);
	
	DeviceManager deviceManager=new DeviceManager();
	Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
	Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
	queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
	String trasactionId = IMonitorUtil.generateTransactionId();
	queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
	GateWay gateWay = deviceDB.getGateWay();
	queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
	queue.add(new KeyValuePair(Constants.DEVICE_ID,deviceDB.getGeneratedDeviceId()));
	queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
	queue.add(new KeyValuePair(Constants.NEIGHBOUR_UPDATE, "8"));
	if (!device1.getGeneratedDeviceId().equals("0"))
	{
		queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF, device1.getGeneratedDeviceId()));
	}
	if (!device2.getGeneratedDeviceId().equals("0")) {
		queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF, device2.getGeneratedDeviceId()));
	}
	if (!device3.getGeneratedDeviceId().equals("0")) {
		queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF, device3.getGeneratedDeviceId()));
	}
	if (!device4.getGeneratedDeviceId().equals("0")) {
		queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF, device4.getGeneratedDeviceId()));
	}
	if (!device5.getGeneratedDeviceId().equals("0")) {
		queue.add(new KeyValuePair(Constants.RULE_SPECIFIC_ACTION_DEF,device5.getGeneratedDeviceId()));
	}

	queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
	RequestProcessor requestProcessor = new RequestProcessor();
	String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
//	ControlModel controlModel = new ControlModel(trasactionId, Constants.REMOVE_DEVICE, generatedDeviceId);
//	IMonitorSession.put(trasactionId, controlModel);
	requestProcessor.processRequest(messageInXml, gateWay);
	return null;
}

}
