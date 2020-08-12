/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.action.AVBlasterIROutputAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcControlOffAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcFanModeControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcSwingControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcTempeartureControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.AlexaControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.ConfigAlarms;
import in.xpeditions.jawlin.imonitor.controller.action.ConfigMinimoteAction;
import in.xpeditions.jawlin.imonitor.controller.action.ConfigureAddActions;
import in.xpeditions.jawlin.imonitor.controller.action.ConfigureDeleteAction;
import in.xpeditions.jawlin.imonitor.controller.action.ConfigureEditAction;
import in.xpeditions.jawlin.imonitor.controller.action.ConfigureRGB;
import in.xpeditions.jawlin.imonitor.controller.action.ConfigureSwitchType;
import in.xpeditions.jawlin.imonitor.controller.action.CurtainCloseAction;
import in.xpeditions.jawlin.imonitor.controller.action.CurtainopenAction;
import in.xpeditions.jawlin.imonitor.controller.action.DeleteDeadNodeAction;
import in.xpeditions.jawlin.imonitor.controller.action.DimmerLevelChangeAction;
import in.xpeditions.jawlin.imonitor.controller.action.DiscoverIndoorUnitAction;

import in.xpeditions.jawlin.imonitor.controller.action.DiscoverSlaveUnitAction;
import in.xpeditions.jawlin.imonitor.controller.action.EditModeAction;
import in.xpeditions.jawlin.imonitor.controller.action.GetDeviceListAction;

import in.xpeditions.jawlin.imonitor.controller.action.GetIndoorUnitCapabilityAction;
import in.xpeditions.jawlin.imonitor.controller.action.GetZWaveVersion;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.action.KeyFobeAction;
import in.xpeditions.jawlin.imonitor.controller.action.LCDRemoteAction;
import in.xpeditions.jawlin.imonitor.controller.action.NetworkStatusAction;
import in.xpeditions.jawlin.imonitor.controller.action.RGBColorPickerAction;
import in.xpeditions.jawlin.imonitor.controller.action.RemoveIndoorUnitAction;
import in.xpeditions.jawlin.imonitor.controller.action.RemoveSlaveAction;
import in.xpeditions.jawlin.imonitor.controller.action.RemoveVIAAction;
import in.xpeditions.jawlin.imonitor.controller.action.ResetFilterAction;
import in.xpeditions.jawlin.imonitor.controller.action.UpdateFriendlyNameForSlave;
import in.xpeditions.jawlin.imonitor.controller.action.SceneControllerRemoteAction;
import in.xpeditions.jawlin.imonitor.controller.action.SetCameraOrientation;
import in.xpeditions.jawlin.imonitor.controller.action.SetNoMotionTimeOut;
import in.xpeditions.jawlin.imonitor.controller.action.SetPassCodeForDoorLock;
import in.xpeditions.jawlin.imonitor.controller.action.SwitchOffAction;
import in.xpeditions.jawlin.imonitor.controller.action.SwitchOnAction;
import in.xpeditions.jawlin.imonitor.controller.action.UpdateAVBlasterModelNumber;
import in.xpeditions.jawlin.imonitor.controller.action.UpdateFriendlyName;
import in.xpeditions.jawlin.imonitor.controller.action.UpdateModelNumberAction;
import in.xpeditions.jawlin.imonitor.controller.action.VirtualSwitchConfigurationAction;
import in.xpeditions.jawlin.imonitor.controller.action.WallmoteDeviceAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Action;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DoorLockConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Functions;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.H264CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;


import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LCDRemoteConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LocationMap;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MinimoteConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.NetworkStats;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ModbusSlave;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.PIRConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.RssiInfo;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SceneControllerConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.WallmoteConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AgentManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AlertTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.Alexamanager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CameraConfigurationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceConfigurationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.IconManager;

import in.xpeditions.jawlin.imonitor.controller.dao.manager.LocationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.MakeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ModbusDeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.PIRConfigurationManager;

import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScenarioManager;

import in.xpeditions.jawlin.imonitor.controller.dao.manager.ViaSlaveDeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.acConfigurationManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.security.NoSuchAlgorithmException;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Hibernate;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

import imonitor.cms.alexa.proactive.service.AlexaProactiveUpdateService;

public class DeviceServiceManager {
	public String saveDevice(String xml) throws SecurityException,
			DOMException, IllegalArgumentException,
			ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		String result = "no";
		XStream stream = new XStream();
		Device device = (Device) stream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		if (deviceManager.saveDevice(device)) {
			result = "yes";
		}
		return result;
	}
	
	// ************************************************************** sumit begin ***************************************************************
	
	//To configure Model Number and Polling Interval for ZXT120
	
	/**
	 * @author sumit
	 * @param xml
	 * @return
	 */
	public String getDeviceWithMake(String xml){
		XStream stream = new XStream();
		String generatedDeviceId = (String) stream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDevice(generatedDeviceId);
		return stream.toXML(device);
	}
	
	/**
	 * @author sumit
	 * @param xml
	 * @return
	 */
	public String getPollingInterval(String xml){//deviceIdXml, String devcConfIdXml){
		XStream stream = new XStream();
        String pollingInterval = "0";
        String deviceTypeName = "";
        String modelNumber = "";
		Device device = new Device();
        try {
			//1.Get Device with details.
			String generatedDeviceId = (String) stream.fromXML(xml);
			DeviceManager deviceManager = new DeviceManager();
			device = deviceManager.getDeviceWithConfiguration(generatedDeviceId);
			deviceTypeName = device.getDeviceType().getName();
			modelNumber = device.getModelNumber();
			if(deviceTypeName.equalsIgnoreCase(Constants.Z_WAVE_AC_EXTENDER)){
				if(((modelNumber.equalsIgnoreCase(Constants.ZXT120)) || (modelNumber.equalsIgnoreCase(Constants.ZXT600))) && (modelNumber != null)){
					//Device device = deviceManager.getDevice(generatedDeviceId);
					long confId = device.getDeviceConfiguration().getId();
					
					//sumit TBD: getting Exception ClassCastException for PIRConfiguration from DeviceConfiguration
					acConfigurationManager acConfigurationManager = new acConfigurationManager();
					acConfiguration acConfiguration = acConfigurationManager.getacConfigurationById(confId);//(PIRConfiguration) device.getDeviceConfiguration();
					
			        device.setDeviceConfiguration(acConfiguration);
					pollingInterval = acConfiguration.getPollingInterval();
				}
				
			}
		} catch (Exception e) {
			LogUtil.info("Error while getting polling interval:", e);
		}
        //return stream.toXML(device);
		return stream.toXML(pollingInterval);
	}
	
	/**
	 * @author sumit kumar
	 * @param xmlDevice
	 * @return
	 */
	public String updateAVBlasterModelNumber(String xmlDevice, String categoryXml, String brandXml, String codeXml){
		XStream stream = new XStream();
		Device device =  (Device) stream.fromXML(xmlDevice);
		String category = (String) stream.fromXML(categoryXml);
		String brand = (String) stream.fromXML(brandXml);
		String code = (String) stream.fromXML(codeXml);
		String generatedDeviceId = device.getGeneratedDeviceId();
		Device deviceFromDB = null;
		DeviceManager deviceManager = new DeviceManager();
		String modelNumber = device.getModelNumber();
		String deviceTypeName = "";
		String result = "";
		try {
			//2.Get device from DB to update make and deviceConfiguration.
			deviceFromDB = deviceManager.getDeviceWithConfigurationAndMake(generatedDeviceId);
			deviceTypeName = deviceFromDB.getDeviceType().getName();
			deviceFromDB.setModelNumber(modelNumber);
		} catch (Exception e) {
			LogUtil.info("Error:", e);
		}
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		deviceFromDB.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(deviceFromDB, null);
		ImonitorControllerAction controllerAction = null;
		if(deviceTypeName.equalsIgnoreCase("Z_WAVE_AV_BLASTER")){
			controllerAction = new UpdateAVBlasterModelNumber(category, brand, code);
		}
		
		try {
			controllerAction.executeCommand(actionModel);
		} catch (Exception e) {
			LogUtil.info("Error", e);
		}
		boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			if(controllerAction.isActionSuccess()){
				//4.update device for AV Configuration
				boolean dbUpdate1 = deviceManager.updateDevice(deviceFromDB);
				if(!(dbUpdate1)){
					result = Constants.DB_FAILURE;
				}else {
					result = Constants.DB_SUCCESS;
				}
			}else{
				result = Constants.iMVG_FAILURE;
			}
		}else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY;
		}
		return stream.toXML(result);
	}
	

	/**
	 * 
	 * @author sumit
	 * @param xml java.lang.String
	 * @param pollingIntervalXml java.lang.String
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String updateModelNumber(String xml, String pollingIntervalXml, String brand, String acModelNumber, String selectedLearnValue) 
			throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
		Device device =  (Device) stream.fromXML(xml);
		String pollingInterval = (String) stream.fromXML(pollingIntervalXml);
		String result = "";
		String selectedBrand = (String) stream.fromXML(brand);
		String acLearnValues = null;
		//Naveen add for Ac key learn feature....
		if(selectedBrand.equals("Learn")){
			
			 acLearnValues = (String) stream.fromXML(selectedLearnValue);
			 
		}
		String selectedModelNumber = (String) stream.fromXML(acModelNumber);
	//	LogUtil.info("selectedBrand"+ selectedBrand+"selectedModelNumber"+ selectedModelNumber);
		//PIRConfiguration configurationFromWeb = (PIRConfiguration) stream.fromXML(pollingIntervalXml);
		//String pollingInterval = configurationFromWeb.getPollingInterval();
		String generatedDeviceId = device.getGeneratedDeviceId();
		acConfigurationManager acConfigurationManager = new acConfigurationManager();
		DeviceManager deviceManager = new DeviceManager();
		acConfiguration acConfiguration = null;//pirConfigurationManager.getPIRConfigurationById(confId);
		
		PIRConfiguration pirConfiguration;
		PIRConfigurationManager pirConfigurationManager= new PIRConfigurationManager();
		
		Device deviceFromDB = null;
		Make make = null;
		String deviceTypeName = "";
		String modelNumber = "";
		try {
			//1.Get make using device for number to be passed to iMVG.
			MakeManager makeManager = new MakeManager();
		//	make = makeManager.getMakeById(device.getMake().getId()); naveen commented
			
			make = makeManager.getMakeBySelectedBrandAndModel(selectedBrand,selectedModelNumber);
			//2.Get device from DB to update make and deviceConfiguration.
			deviceFromDB = deviceManager.getDeviceWithConfigurationAndMake(generatedDeviceId);
			deviceTypeName = deviceFromDB.getDeviceType().getName();
			modelNumber = deviceFromDB.getModelNumber();
			
			deviceFromDB.setMake(make);
			
			if(deviceFromDB.getDeviceConfiguration() != null){
				long confId = deviceFromDB.getDeviceConfiguration().getId();
				
				//3.Get PIRConfiguration from DB to update.
				if(deviceTypeName.equalsIgnoreCase(Constants.Z_WAVE_AC_EXTENDER)){
					if((modelNumber.equalsIgnoreCase(Constants.ZXT120)) || (modelNumber.equalsIgnoreCase(Constants.ZXT600))){		
						acConfiguration = acConfigurationManager.getacConfigurationById(confId);
						acConfiguration.setPollingInterval(pollingInterval);
						deviceFromDB.setDeviceConfiguration(acConfiguration);
						//naveen added for ac key learn feature
						if(selectedBrand.equals("Learn")){
							acConfiguration.setAcLearnValue(acLearnValues);
							
						}else{
							acConfiguration.setAcLearnValue(null);
							
						}
						//naveen end
					}
					else if(deviceTypeName.equalsIgnoreCase("Z_WAVE_AV_BLASTER")){
					if(modelNumber.equalsIgnoreCase("AV Blaster")){		
						pirConfiguration = pirConfigurationManager.getPIRConfigurationById(confId);
						pirConfiguration.setPollingInterval(pollingInterval);
						deviceFromDB.setDeviceConfiguration(pirConfiguration);
					}
				}
					
					
				}
			}		
		} catch (Exception e) {
			LogUtil.info("Error:", e);
		}
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
		deviceFromDB.getGateWay().setAgent(agent);
		ActionModel actionModel = new ActionModel(deviceFromDB, make);
		ImonitorControllerAction controllerAction = null;
		if(deviceTypeName.equalsIgnoreCase(Constants.Z_WAVE_AC_EXTENDER)){
			if((modelNumber.equalsIgnoreCase(Constants.ZXT120)) || (modelNumber.equalsIgnoreCase(Constants.ZXT600))){
				controllerAction = new UpdateModelNumberAction(pollingInterval);
			}else{
				controllerAction = new UpdateModelNumberAction();
			}
		}else if(deviceTypeName.equalsIgnoreCase("Z_WAVE_AV_BLASTER")){
			if(modelNumber.equalsIgnoreCase("AV Blaster")){
				controllerAction = new UpdateModelNumberAction(pollingInterval);
			}else{
				controllerAction = new UpdateModelNumberAction();
			}
		}
		
		try {
			controllerAction.executeCommand(actionModel);
		} catch (Exception e) {
			LogUtil.info("Error", e);
		}
		boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			if(controllerAction.isActionSuccess()){
				//4.update device for make
				boolean dbUpdate1 = deviceManager.updateDevice(deviceFromDB);
				//5.update pirconfiguration
				boolean dbUpdate2 = false;
				result = Constants.DB_SUCCESS;
				if(deviceFromDB.getDeviceConfiguration() != null){
					dbUpdate2 = acConfigurationManager.updateacConfiguration(acConfiguration);
					if(!(dbUpdate1 && dbUpdate2)){
						result = Constants.DB_FAILURE;
					}
				}else{
					if(!dbUpdate1){
						result = Constants.DB_FAILURE;
					}
				}
				
				
				
			}else{
				result = Constants.iMVG_FAILURE;
			}
		}else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY;
		}
		//device = controllerAction.getActionModel().getDevice();
		//result = Constants.DB_SUCCESS;
	
		return stream.toXML(result);
		//return stream.toXML(deviceFromDB);
	}
	//To configure Model Number and Polling Interval for ZXT120: end
	
	//Re-Ordering of devices: start
	/**
	 * @author sumit
	 * @param xmlString java.lang.String
	 */
	public void savePositionIndexForDevices(String xmlString){
		XStream stream = new XStream();
		List<String> devcIds = new ArrayList<String>();
		String devicesOrder = (String) stream.fromXML(xmlString);
		String[] deviceOrder = devicesOrder.split(",");
		
		//1.Filter out each device id, store them in a list(Array) in correct order.
		for(String idx : deviceOrder){
			int startValue = idx.indexOf("-");
			String deviceId = idx.substring(++startValue);
			devcIds.add(deviceId);
		}

		//2.Finally send the list to device manager to update the position.
		DeviceManager deviceManager = new DeviceManager();
		deviceManager.savePositionIndexForDevices(devcIds);
	}
	//Re-Ordering Of Device: end
	
	//for RULE Feature
	public String getIdByGeneratedDeviceId(String xmlString){
		String result = "";
		XStream xStream = new XStream();
		String generatedDevcId = (String) xStream.fromXML(xmlString);
		long id = 0L;
		DeviceManager deviceManager = new DeviceManager();
		id = deviceManager.getIdByGeneratedDeviceId(generatedDevcId);
		result = xStream.toXML(id);
		return result;
	}
	
	// sumit start: Alert Device Feature
	@SuppressWarnings("unchecked")
	public String saveModeWithAllDetailsWithNoAlertDevice(String deviceListForModeXml, String alertDeviceXml){//, String noDeviceXml){
		String result = "msg.controller.rulesConfiguremode.0005";
		XStream xStream  = new XStream();
		List<Device> devices = (List<Device>) xStream.fromXML(deviceListForModeXml);
		String noDevice = Constants.NO_DEVICE;
		Device firstDevice = devices.get(0);
		GateWay gateWayfromDeviceList = firstDevice.getGateWay();
		long gatewayId = gateWayfromDeviceList.getId();
		if(devices.size() > 0)
		{
			ActionModel actionModel = new ActionModel(null, devices);				
			ImonitorControllerAction editModeAction = new EditModeAction(noDevice);
			editModeAction.executeCommand(actionModel);
			
			boolean resultFromImvg = IMonitorUtil.waitForResult(editModeAction);
			if(resultFromImvg)
			{
				if(editModeAction.isActionSuccess())
				{
					//Send the message received from gateway.
					result = "msg.controller.rules.0005"+ Constants.MESSAGE_DELIMITER_1 + editModeAction.getActionModel().getMessage();
					//Update the DB with provided Details
					DeviceManager deviceManager = new DeviceManager();
					GatewayManager gatewayManager = new GatewayManager();
					
					GateWay gateWayFromDB = gatewayManager.getGateWayById(gatewayId);
					Device alertDevice = null; 												//USER did not select any alert device.
					gateWayFromDB.setAlertDevice(alertDevice);
					gateWayFromDB.setDelayHome(gateWayfromDeviceList.getDelayHome());
					gateWayFromDB.setDelayStay(gateWayfromDeviceList.getDelayStay());
					gateWayFromDB.setDelayAway(gateWayfromDeviceList.getDelayAway());
					
					boolean dbUpdate1 = deviceManager.saveModeWithAllDetails(devices);
					boolean dbUpdate2 = gatewayManager.updateGateWay(gateWayFromDB);
					if(dbUpdate1 && dbUpdate2){		//DB Update Successful
						result = "msg.controller.rulesConfiguremode.0001";
					}
					else{
						result = "msg.controller.rulesConfiguremode.0002";
					}
				}
				else{ //iMVG returned a failure
					result = "msg.controller.rulesConfiguremode.0003" + Constants.MESSAGE_DELIMITER_1 + editModeAction.getActionModel().getMessage();
				}
					
			}
			else{
				result = "msg.controller.rulesConfiguremode.0004";
			}
		}
		return result;
	}	
	
	
	@SuppressWarnings("unchecked")
	public String saveModeWithAllDetails(String deviceListForModeXml, String alertDeviceXml, String generatedDevcId){
		String result = "";
		try {
			result = "msg.controller.rulesConfiguremode.0004";
			XStream xStream  = new XStream();
			List<Device> devices = (List<Device>) xStream.fromXML(deviceListForModeXml);
			long alertDeviceId = (Long) xStream.fromXML(alertDeviceXml);// use this value for gateway table.
			String generatedDeviceId = (String) xStream.fromXML(generatedDevcId);// use this value action class
			Device firstDevice = devices.get(0);
			GateWay gateWayfromDeviceList = firstDevice.getGateWay();
			long gatewayId = gateWayfromDeviceList.getId();
			if(devices.size() > 0)
			{
				ActionModel actionModel = new ActionModel(null, devices);				
				ImonitorControllerAction editModeAction = new EditModeAction(generatedDeviceId);
				editModeAction.executeCommand(actionModel);
				
				boolean resultFromImvg = IMonitorUtil.waitForResult(editModeAction);
				if(resultFromImvg)
				{
					if(editModeAction.isActionSuccess())
					{
						//Send the message received from gateway.
						result = "msg.controller.rules.0005" + editModeAction.getActionModel().getMessage();
						//Update the DB with provided Details
						DeviceManager deviceManager = new DeviceManager();
						GatewayManager gatewayManager = new GatewayManager();
						
						GateWay gateWayFromDB = gatewayManager.getGateWayById(gatewayId);
						Device alertDevice = new Device();
						alertDevice.setId(alertDeviceId);
						gateWayFromDB.setAlertDevice(alertDevice);
						gateWayFromDB.setDelayHome(gateWayfromDeviceList.getDelayHome());
						gateWayFromDB.setDelayStay(gateWayfromDeviceList.getDelayStay());
						gateWayFromDB.setDelayAway(gateWayfromDeviceList.getDelayAway());
						
						boolean dbUpdate1 = deviceManager.saveModeWithAllDetails(devices);
						boolean dbUpdate2 =gatewayManager.updateGateWay(gateWayFromDB);
						if(dbUpdate1 && dbUpdate2){		//DB Update Successful
							result = "msg.controller.rulesConfiguremode.0001";
						}
						else{
							result = "msg.controller.rulesConfiguremode.0002";
						}
					}
					else{ //iMVG returned a failure
						//parishod added delimeter to this message.
						result = "msg.controller.rulesConfiguremode.0003" + Constants.MESSAGE_DELIMITER_1 + editModeAction.getActionModel().getMessage() +"'";
					}
						
				}
				else{
					result = "msg.controller.rulesConfiguremode.0004";
				}
			}
		} catch (Exception e) {
			LogUtil.info("Got Exception while saving mode: ", e);
		}
		return result;
	}
/* ********************************************************** ORIGINAL CODE **************************************************************	
	@SuppressWarnings("unchecked")
	public String saveModeWithAllDetails(String deviceListForModeXml){
		String result = "A problem occured while saving the mode...";
		XStream xStream  = new XStream();
		List<Device> devices = (List<Device>) xStream.fromXML(deviceListForModeXml);
		if(devices.size() > 0)
		{
			ActionModel actionModel = new ActionModel(null, devices);				
			ImonitorControllerAction editModeAction = new EditModeAction();
			editModeAction.executeCommand(actionModel);
			
			boolean resultFromImvg = IMonitorUtil.waitForResult(editModeAction);
			if(resultFromImvg)
			{
				if(editModeAction.isActionSuccess())
				{
					//Send the message received from gateway.
					result = "Message from your Gateway : " + editModeAction.getActionModel().getMessage();
					//Update the DB with provided Details
					DeviceManager deviceManager = new DeviceManager();
					boolean dbUpdate = deviceManager.saveModeWithAllDetails(devices);
					if(dbUpdate){		//DB Update Successful
						result = "Mode successfully saved.";
					}
					else{
						result = "Internal error occured while saving mode values for your devices.";
					}
				}
				else{ //iMVG returned a failure
					result = "Your Gateway is unable to save mode values for your devices and the message is : '" + editModeAction.getActionModel().getMessage() +"'";
				}
					
			}
			else{
				result = "Your Gateway did not respond back. Mode not saved.";
			}
		}
		
		return result;
	}	
*/
	// ************************************************************** sumit ends ***************************************************************

	public String deleteDevice(String xml) throws SecurityException,
			DOMException, IllegalArgumentException,
			ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		String result = "no";
		XStream stream = new XStream();
		Device device = (Device) stream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		if (deviceManager.deleteDeviceTree(device.getGeneratedDeviceId())) {
			result = "yes";
		}
		return result;
	}

	public String updateDevice(String xml) throws SecurityException,
			DOMException, IllegalArgumentException,
			ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		String result = "no";
		XStream stream = new XStream();
		Device device = (Device) stream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		if (deviceManager.updateDevice(device)) {
			result = "yes";
		}
		return result;
	}

	public String listOfDevice() throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		XStream xStream = new XStream();
		DeviceManager deviceManager = new DeviceManager();
		List<Device> devices = deviceManager.listOfDevices();
		String xml = xStream.toXML(devices);
		return xml;
	}

	public String listDevicesOfGateWays(String xml) {
		XStream xStream = new XStream();
		GateWay gateWay = (GateWay) xStream.fromXML(xml);
		Set<Device> devices = gateWay.getDevices();
		String valueInXml = xStream.toXML(devices);
		return valueInXml;
	}

	public String listDevicesOfCustomer(String xml) {
		XStream stream = new XStream();
		Customer customer = (Customer) stream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		List<Device> devices = deviceManager
				.getDevicesWithAlertTypeAndActionTypeByCustomer(customer);
		String result = stream.toXML(devices);
		return result;
	}

	public String getActionsOfDevice(String xml) {
		XStream stream = new XStream();
		long id = (Long) stream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDevice(id);
		List<AlertType> alertTypes = device.getDeviceType().getAlertTypes();
		return stream.toXML(alertTypes);

	}

	public String getAlertsOfDevice(String xml) {
		
		XStream stream = new XStream();
		String GeneratedDeviceId = (String) stream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		
		List<AlertType> alertTypes = deviceManager.getAlertsOfDeviceByDeviceType(GeneratedDeviceId);
	
		
		/*long id=device.getId();
		LogUtil.info(id);
		device=deviceManager.getDevice(id);*/
		//List<AlertType> alertTypes =deviceManager.getAlertsOfDeviceByDeviceType(Devicetype.getName());
		//List<AlertType> alertTypes = device.getDeviceType().getAlertTypes();
		//LogUtil.info(stream.toXML(alertTypes));
		return stream.toXML(alertTypes);

	}
	
	public String getUserChoosenAlertsOfDevice(String xml) {
		XStream stream = new XStream();
		Device device = (Device) stream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		long DeviceId=device.getId();
		List<String> Alerts = deviceManager.GetUserChoosenAlerts(DeviceId);
		
		
		/*long id=device.getId();
		LogUtil.info(id);
		device=deviceManager.getDevice(id);*/
		//List<AlertType> alertTypes =deviceManager.getAlertsOfDeviceByDeviceType(Devicetype.getName());
		//List<AlertType> alertTypes = device.getDeviceType().getAlertTypes();
		//LogUtil.info(stream.toXML(alertTypes));
		//LogUtil.info(Alerts.get(0)+Alerts);
		
		return stream.toXML(Alerts);

	}

	public String saveUserChoosenAlerts(String Xml){
		
		XStream stream=new XStream();
		String Result="";
		DeviceManager deviceManger = new DeviceManager();
		String Temp[]=Xml.split("` ");
		Device device=(Device) stream.fromXML(Temp[0]);
		device = deviceManger.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
		
		String Param=(String)stream.fromXML(Temp[1]);
		String NotSelected=(String)stream.fromXML(Temp[2]);
		List<AlertType> alertTypes = deviceManger.getAlertsOfDeviceByDeviceType(device.getGeneratedDeviceId());
		DeviceType devicetype=device.getDeviceType();
		devicetype.setAlertTypes(alertTypes);
		device.setDeviceType(devicetype);
		
		ActionModel actionModel = new ActionModel(device,Xml);				
		ImonitorControllerAction ConfigAlarmAction = new ConfigAlarms();
		ConfigAlarmAction.executeCommand(actionModel);
		
		/*boolean ResultExecuted=IMonitorUtil.waitForResult(ConfigAlarmAction);
		if(ResultExecuted){
			if(ConfigAlarmAction.isActionSuccess()){
				LogUtil.info("actionSuccess");*/
				boolean dbUpdate = false;
				if(NotSelected.isEmpty()){
					boolean checkForFailure = false;
					Temp=Param.split(", ");
					for(int i=0;i<Temp.length;i++){
						boolean result = deviceManger.setUserChoosenAlerts(device.getId(),device.getGateWay().getId(),Temp[i]);
						if(!result){
							checkForFailure = true;	//failure occurred, set dbUpdate to false.
						}
					}	
					if(!checkForFailure){
						dbUpdate = true;		//alerts saved successfully.
					}
				}else{
					boolean checkForFailure1 = false;
					boolean checkForFailure2 = false;
					boolean checkForFailure3 = false;
					boolean result = false;
					if(Param==null){
						Temp=NotSelected.split(",");			
						for(int i=0;i<Temp.length;i++){
							result = deviceManger.RemoveUserChoosenAlerts(device.getId(),device.getGateWay().getId(),Temp[i]);
							if(!result){
								checkForFailure1 = true;		//failure occurred, set dbUpdate to false.
							}
						}	
						if(!checkForFailure1){
							dbUpdate = true;
						}
					}else{
						
						Temp=Param.split(", ");
						for(int i=0;i<Temp.length;i++){
							result = deviceManger.setUserChoosenAlerts(device.getId(),device.getGateWay().getId(),Temp[i]);
							if(!result){
								checkForFailure2 = true;		//failure occurred, set dbUpdate to false.
							}
						}
						Temp=NotSelected.split(",");
						for(int i=0;i<Temp.length;i++){
							result = deviceManger.RemoveUserChoosenAlerts(device.getId(),device.getGateWay().getId(),Temp[i]);
							if(!result){
								checkForFailure3 = true;		//failure occurred, set dbUpdate to false.
							}
						}
						if(!checkForFailure2 && !checkForFailure3){
							dbUpdate = true;
						}
					}
				}
				if(dbUpdate){
					Result = Constants.DB_SUCCESS;
				}else {
					Result = Constants.DB_FAILURE;		//DB Failure.
				}
				//Result="Sucess:Sucessfully Configured";
				//return Result;
			/*}else{
				Result = Constants.iMVG_FAILURE;//"Failure:Failed to configure";
		}
		}else{
			Result = Constants.NO_RESPONSE_FROM_GATEWAY;
		}*/
		//return Result;
		return stream.toXML(Result);
	}

	public String listDevicesUnderAGateWayByMacId(String xml) {
		XStream xStream = new XStream();
		String[] items = xml.split("-");
		DataTable dataTable =  (DataTable) xStream.fromXML(items[0]);
		String macId = (String) xStream.fromXML(items[1]);
		DeviceManager deviceManager = new DeviceManager();
		String sSearch = dataTable.getsSearch();
		String[] cols = { "d.generatedDeviceId","d.friendlyName","dt.name","l.name","g.macId"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = deviceManager.listDevicesUnderGatewayByMacId(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),macId
				);
		dataTable.setResults(list);
		int displayRow = deviceManager.getTotalDeviceCount(sSearch,macId);
		dataTable.setTotalDispalyRows(displayRow);
		dataTable.setTotalRows(deviceManager.getTotalDeviceCountByGateWay(macId));
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}

	public String listlogsUnderAGateWayById(String xml) {
		XStream xStream = new XStream();
		String[] items = xml.split("-");
		DataTable dataTable =  (DataTable) xStream.fromXML(items[0]);
		String macId = (String) xStream.fromXML(items[1]);
		DeviceManager deviceManager = new DeviceManager();
		String sSearch = dataTable.getsSearch();
		String[] cols = {"l.filename","l.filepath","l.uploadeddate","g.macid",};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		  
		 GatewayManager gatewaymanger =new GatewayManager();
		 GateWay gateway =gatewaymanger.getGateWayByMacId(macId);
		 long id=gateway.getId();
		 
		List<?> list = deviceManager.listLogsUnderGatewayById(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),id);
		dataTable.setResults(list);
		int displayRow = deviceManager.getTotalDeviceCount(sSearch,macId);
		dataTable.setTotalDispalyRows(displayRow);
		dataTable.setTotalRows(deviceManager.getTotalDeviceCountByGateWay(macId));
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	public String getDevice(String xml) {
		XStream xStream = new XStream();
		String generatedDeviceId = (String) xStream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager
				.getDeviceByGeneratedDeviceId(generatedDeviceId);
		return xStream.toXML(device);
	}

	/*public String getDeviceByGenetatedDevice(String xml) {
		XStream xStream = new XStream();
		
		String generatedDeviceId = (String) xStream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDeviceWithConfiguration(generatedDeviceId);
		LogUtil.info(xStream.toXML(device));
		return xStream.toXML(device);
	}*/
	
	public String getDeviceByGenetatedDevice(String xml) {
		
		XStream xStream = new XStream();
		String generatedDeviceId = (String) xStream.fromXML(xml);
		Device devWithConf = new DeviceManager().getDeviceWithConfigurationForMAp(generatedDeviceId);
	
		String xml1 = xStream.toXML(devWithConf);
	
		return xml1;
	}
	
	public String toUpdateFriendlyNameAndLocation(String xml) {
		XStream xStream = new XStream();
		String generatedDeviceId = (String) xStream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager
				.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceId);
		return xStream.toXML(device);
	}
	
	// ******************************************** sumit start: Camera Orientation Configuration *******************************************
		public String toConfigureCameraOrientation(String xmlId, String xmldeviceId, String xmlMacId){
			XStream xStream = new XStream();
			Long id = (Long) xStream.fromXML(xmlId);
			String generatedDeviceId = (String) xStream.fromXML(xmldeviceId);
			String macId = (String) xStream.fromXML(xmlMacId);
			String xmlDevice = "";
			DeviceManager deviceManager = new DeviceManager();
			Device device = new Device();
			GateWay gateWay = new GateWay();

			try {
				//1.Get Camera Configuration Details from DB.
				Device deviceFromDB = deviceManager.getCameraConfiguration(id.longValue(), generatedDeviceId);//, macId);

				//2.Set the device with appropriate details and return the device to the calling function.
				device.setId(id.longValue());
				device.setGeneratedDeviceId(generatedDeviceId);
				gateWay.setMacId(macId);
				device.setGateWay(gateWay);
				device.setDeviceConfiguration(deviceFromDB.getDeviceConfiguration());
				device.setModelNumber(deviceFromDB.getModelNumber());
				device.setFriendlyName(deviceFromDB.getFriendlyName());
				xmlDevice = xStream.toXML(deviceFromDB);
			} catch (Exception e) {
				LogUtil.error("Got Exception: "+e.getMessage());
			}
			return xmlDevice;
		}
	
	/**
	 * Saves the changes done by the user to configure camera orientation. 
	 * Saves details for camera orientation for cameras of RC80 Series and H264 Series.
	 * 
	 * @param deviceXml java.lang.String
	 * @param configurationXml java.lang.String
	 * @return result com.thoughtworks.xstream.XStream
	 */
	public String saveCameraOrientationConfiguration(String deviceXml, String configurationXml, String PTXml){
		String result = "";
		XStream stream = new XStream();
		Device device = (Device) stream.fromXML(deviceXml);
		String cameraOrientation = (String) stream.fromXML(configurationXml);
		String PTcam = (String) stream.fromXML(PTXml); //parishod added for camera PT button feature
		String modelNumber = device.getModelNumber();
		String generatedDeviceId = device.getGeneratedDeviceId();
		boolean dbUpdate = false;

		CameraConfigurationManager cameraConfigurationManager = new CameraConfigurationManager();
		DeviceManager deviceManager = new DeviceManager();
		Device deviceFromDB = deviceManager.getDeviceWithConfiguration(generatedDeviceId);
		
		//1.Send message to iMVG
		ActionModel actionModel = new ActionModel(deviceFromDB, null);
		ImonitorControllerAction setCameraOrientation = new SetCameraOrientation(cameraOrientation);
		setCameraOrientation.executeCommand(actionModel);
		
		//2.If iMVG returns success then update DeviceConfiguration
		boolean resultFromImvg = IMonitorUtil.waitForResult(setCameraOrientation);
		if(resultFromImvg){
			if(setCameraOrientation.isActionSuccess()){
				//3.Get from DB the cameraConfiguration to update based on modelNumber.
				if(modelNumber.contains(Constants.RC80Series)){
					long confId = deviceFromDB.getDeviceConfiguration().getId();
					CameraConfiguration cameraConfiguration = cameraConfigurationManager.getCameraConfiguration(confId);
					//4a.Set Camera Orientation & update.
					cameraConfiguration.setPantiltControl(PTcam); //parishod added for setting the camera PT options
					cameraConfiguration.setCameraOrientation(cameraOrientation);
					dbUpdate = cameraConfigurationManager.updateCameraConfiguration(cameraConfiguration);
					if(!dbUpdate){
						result = Constants.DB_FAILURE;	//DB Update Failed.
					}
					else{
						result = Constants.DB_SUCCESS;
					}
				}else if(modelNumber.contains(Constants.H264Series)){
					long confId = deviceFromDB.getDeviceConfiguration().getId();
					H264CameraConfiguration h264CameraConfiguration = cameraConfigurationManager.getH264CameraConfigurationById(confId);
					//4b.Set Camera Orientation	& update
					h264CameraConfiguration.setPantiltControl(PTcam); //parishod added for setting the camera PT options
					h264CameraConfiguration.setCameraOrientation(cameraOrientation);
					dbUpdate = cameraConfigurationManager.updateH264CameraConfiguration(h264CameraConfiguration);
					if(!dbUpdate){
						result = Constants.DB_FAILURE;	//DB Update Failed.
					}
					else{
						result = Constants.DB_SUCCESS;
					}
				}
			}else{ 
				result = Constants.iMVG_FAILURE;			//iMVG returned a failure.
			}
		}else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY;				//GateWay did not respond.
		}
		return stream.toXML(result);
	}
	
	// ********************************************* sumit end: Camera Orientation Configuration ********************************************
//bhavya start
	public String saveLCDremoteConfiguration(String deviceXml){
		String result = Constants.FAILURE;
		XStream stream = new XStream();
		try
		{
			Device device = (Device) stream.fromXML(deviceXml);
			//String modelNumber = device.getModelNumber();
			String generatedDeviceId = device.getGeneratedDeviceId();
			DeviceManager deviceManager = new DeviceManager();
			Device deviceFromDB = deviceManager.getDeviceWithConfiguration(generatedDeviceId);
			LCDRemoteConfiguration lcdrc;
			if(null == deviceFromDB.getDeviceConfiguration())
			{
				lcdrc = new LCDRemoteConfiguration();
			}
			else
			{
				lcdrc = (LCDRemoteConfiguration) new DaoManager().get(deviceFromDB.getDeviceConfiguration().getId(), LCDRemoteConfiguration.class);
			}
			deviceFromDB.setDeviceConfiguration(lcdrc);
			lcdrc = (LCDRemoteConfiguration)deviceFromDB.getDeviceConfiguration();
			lcdrc.setF1(((LCDRemoteConfiguration)device.getDeviceConfiguration()).getF1());
			lcdrc.setF2(((LCDRemoteConfiguration)device.getDeviceConfiguration()).getF2());
			lcdrc.setF3(((LCDRemoteConfiguration)device.getDeviceConfiguration()).getF3());
			//1.Send message to iMVG
			ActionModel actionModel = new ActionModel(deviceFromDB,null);
			ImonitorControllerAction LCDRemoteAction = new LCDRemoteAction();
			LCDRemoteAction.executeCommand(actionModel);
			//2.If iMVG returns success then update DeviceConfiguration
			boolean resultFromImvg = IMonitorUtil.waitForResult(LCDRemoteAction);
			if(resultFromImvg){
				if(LCDRemoteAction.isActionSuccess())
				{
					
					//3.Get from DB the LConfiguration to update based on modelNumber.
					try
					{
						new DaoManager().saveOrUpdate(lcdrc);
						new DeviceManager().updateDevice(deviceFromDB);
						result = Constants.DB_SUCCESS;
					}
					catch(Exception e)
					{
						result = Constants.DB_FAILURE;	//DB Update Failed.
						LogUtil.error(e.getMessage());
						LogUtil.info(e.getMessage(), e);
					}
					
				}else{ 
					result = Constants.iMVG_FAILURE;			//iMVG returned a failure.
				}
			}else{
				result = Constants.NO_RESPONSE_FROM_GATEWAY;				//GateWay did not respond.
			}
		}
		catch(Exception e)
		{
			LogUtil.error(e.getMessage());
			LogUtil.info(e.getMessage(), e);
		}
		return stream.toXML(result);
	}
	
	
	public String getLCDRemoteconfiguration(String generatedDevIdXml){
		XStream stream = new XStream();
		String generatedDeviceId = (String) stream.fromXML(generatedDevIdXml);
		DeviceManager deviceManager = new DeviceManager();
		Device deviceFromDB = deviceManager.getDeviceWithConfiguration(generatedDeviceId);
		LCDRemoteConfiguration lcdrc=(LCDRemoteConfiguration) new DaoManager().get(deviceFromDB.getDeviceConfiguration().getId(), LCDRemoteConfiguration.class);
		return stream.toXML(lcdrc);
	}
	
	
	
	//Scene Controller
	public String getSceneControllerconfiguration(String generatedDevIdXml){
		XStream stream = new XStream();
		String generatedDeviceId = (String) stream.fromXML(generatedDevIdXml);
		DeviceManager deviceManager = new DeviceManager();
		Device deviceFromDB = deviceManager.getDeviceWithConfiguration(generatedDeviceId);
		SceneControllerConfig scrc=(SceneControllerConfig) new DaoManager().get(deviceFromDB.getDeviceConfiguration().getId(), SceneControllerConfig.class);
		return stream.toXML(scrc);
	}
	
	
	@SuppressWarnings("unchecked")
	public String saveSceneControllerConfiguration(String deviceXml,String keyconfigObjectsXml){
		
		
		String result = Constants.FAILURE;
		XStream stream = new XStream();
		try
		{
			Device device1=(Device) stream.fromXML(deviceXml);
		   	DeviceManager deviceManager=new DeviceManager();
			Device device=deviceManager.getDevice(device1.getId());
			
			ArrayList<KeyConfiguration> keyconfigObjects=(ArrayList<KeyConfiguration>) stream.fromXML(keyconfigObjectsXml);
			
			ActionModel actionModel = new ActionModel(device,keyconfigObjects);
			ImonitorControllerAction SceneContRemoteAction = new SceneControllerRemoteAction();
			SceneContRemoteAction.executeCommand(actionModel);
		
			boolean resultFromImvg = IMonitorUtil.waitForResult(SceneContRemoteAction);
			
			if(resultFromImvg){
				if(SceneContRemoteAction.isActionSuccess())
				{
					
					try
					{
						for (int i = 0; i < keyconfigObjects.size(); i++)
						{
							//LogUtil.info(stream.toXML("Object index"+i));
							
							KeyConfiguration configuration=keyconfigObjects.get(i);		
							if(configuration.getAction().getId() == 0){
								configuration.setAction(null);
							}
							
							//KeyConfiguration configuration=keyconfigObjects.get(i);
							new DaoManager().update(configuration);
							result = Constants.DB_SUCCESS;
						}
						
						
					}
					catch(Exception e)
					{
						result = Constants.DB_FAILURE;	//DB Update Failed.
						LogUtil.error(e.getMessage());
						LogUtil.info(e.getMessage(), e);
					}
					
				}else{ 
					result = Constants.iMVG_FAILURE;			//iMVG returned a failure.
				}
			}else{
				result = Constants.NO_RESPONSE_FROM_GATEWAY;				//GateWay did not respond.
			}
		}
		catch(Exception e)
		{
			LogUtil.error(e.getMessage());
			LogUtil.info(e.getMessage(), e);
		}
		return stream.toXML(result);
	}
	
	@SuppressWarnings("unchecked")
	public String saveKeyFobeConfiguration(String deviceXml,String keyconfigObjectsXml){
		
		
		String result = Constants.FAILURE;
		XStream stream = new XStream();
		try
		{
			Device device1=(Device) stream.fromXML(deviceXml);
		   	DeviceManager deviceManager=new DeviceManager();
			Device device=deviceManager.getDevice(device1.getId());
			
			ArrayList<KeyConfiguration> keyconfigObjects=(ArrayList<KeyConfiguration>) stream.fromXML(keyconfigObjectsXml);
			
			ActionModel actionModel = new ActionModel(device,keyconfigObjects);
			ImonitorControllerAction SceneContRemoteAction = new KeyFobeAction();
			SceneContRemoteAction.executeCommand(actionModel);
		
			boolean resultFromImvg = IMonitorUtil.waitForResult(SceneContRemoteAction);
			
			if(resultFromImvg){
				if(SceneContRemoteAction.isActionSuccess())
				{
					
					try
					{
						for (int i = 0; i < keyconfigObjects.size(); i++)
						{
							//LogUtil.info(stream.toXML("Object index"+i));
							
							KeyConfiguration configuration=keyconfigObjects.get(i);		
							if(configuration.getAction().getId() == 0){
								configuration.setAction(null);
							}
						
							//KeyConfiguration configuration=keyconfigObjects.get(i);
							new DaoManager().update(configuration);
							result = Constants.DB_SUCCESS;
						}
						
						
					}
					catch(Exception e)
					{
						result = Constants.DB_FAILURE;	//DB Update Failed.
						LogUtil.error(e.getMessage());
						LogUtil.info(e.getMessage(), e);
					}
					
				}else{ 
					result = Constants.iMVG_FAILURE;			//iMVG returned a failure.
				}
			}else{
				result = Constants.NO_RESPONSE_FROM_GATEWAY;				//GateWay did not respond.
			}
		}
		catch(Exception e)
		{
			LogUtil.error(e.getMessage());
			LogUtil.info(e.getMessage(), e);
		}
		return stream.toXML(result);
	}
	
	
	//*********************************************** sumit start: Rocker and Tact Switch Type ***********************************************
	/**
	 * @author sumitkumar
	 * @param xmlDevice
	 * @return
	 */
	public String toConfigureSwitchType(String xmlDevice){
		XStream stream = new XStream();
		Device deviceFromWeb = (Device) stream.fromXML(xmlDevice);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getConfigureSwitchByGeneratedDeviceId(deviceFromWeb.getGeneratedDeviceId());
		//Device device = deviceManager.getDeviceByGeneratedDeviceId(deviceFromWeb.getGeneratedDeviceId());
		return stream.toXML(device);
	}
	
	public String toConfigureRGB(String xmlDevice){
		LogUtil.info("toConfigureRGB=="+xmlDevice);
		XStream stream = new XStream();
		Device deviceFromWeb = (Device) stream.fromXML(xmlDevice);
		LogUtil.info("deviceFromWeb=="+deviceFromWeb);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getConfigureSwitchByGeneratedDeviceId(deviceFromWeb.getGeneratedDeviceId());
		LogUtil.info("device=="+device);
		//Device device = deviceManager.getDeviceByGeneratedDeviceId(deviceFromWeb.getGeneratedDeviceId());
		
		return stream.toXML(device);
	}
	
	
	/**
	 * @author sumitkumar
	 * @param xmlDevice
	 * @return
	 */
	public String updateSwitchType(String xmlDevice){
		XStream stream = new XStream();
		String result = "";
		
		Device deviceFromWeb = (Device) stream.fromXML(xmlDevice);
		
		//1. Get Device from DB.
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDeviceByGeneratedDeviceId(deviceFromWeb.getGeneratedDeviceId());
		//2. Send Message to Master Controller
		/*if((device.getDeviceType().getName().equalsIgnoreCase(Constants.Z_WAVE_DIMMER)) && (deviceFromWeb.getSwitchType() == 3)){
			
			device.setSwitchType(deviceFromWeb.getSwitchType());
			deviceManager.updateDevice(device);
			result = Constants.DB_SUCCESS;
		}else{*/
		ActionModel actionModel = new ActionModel(deviceFromWeb, null);
		ImonitorControllerAction configureSwitchType = new ConfigureSwitchType();
		configureSwitchType.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(configureSwitchType);
		
		//3. Update Device if Master Controller sends success
		if(resultFromImvg){
			if(configureSwitchType.isActionSuccess()){
				device.setSwitchType(deviceFromWeb.getSwitchType());
				if(deviceManager.updateDevice(device)){
					result = Constants.DB_SUCCESS;
				}else{
					result = Constants.DB_FAILURE;//"db";
				}
				
			}else{ //iMVG returned a failure
				result = Constants.iMVG_FAILURE;//"iMVG";
			}
		}else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY;//"no";
		}
		//}
		
		return stream.toXML(result);
	}
	
	
	
	
	//*********************************************** sumit end: Rocker and Tact Switch Type ***********************************************

	
	public String UpdateRgb(String xmlDevice){
		LogUtil.info("UpdateRgb=="+xmlDevice);
		XStream stream = new XStream();
		String result = "";
		
		Device deviceFromWeb = (Device) stream.fromXML(xmlDevice);
		LogUtil.info("deviceFromWeb=="+deviceFromWeb);
		//1. Get Device from DB.
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDeviceByGeneratedDeviceId(deviceFromWeb.getGeneratedDeviceId());
		LogUtil.info("device=="+device);
	
		ActionModel actionModel = new ActionModel(deviceFromWeb, null);
		ImonitorControllerAction configureRGB = new ConfigureRGB();
		configureRGB.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(configureRGB);
		LogUtil.info("resultFromImvg=="+resultFromImvg);
		
		//2. Update Device if Master Controller sends success
		if(resultFromImvg){
			if(configureRGB.isActionSuccess()){
				device.setSwitchType(deviceFromWeb.getSwitchType());
				LogUtil.info("device update=="+device);
				if(deviceManager.updateDevice(device)){
					result = Constants.DB_SUCCESS;
				}else{
					result = Constants.DB_FAILURE;//"db";
				}
				
			}else{ //iMVG returned a failure
				result = Constants.iMVG_FAILURE;//"iMVG";
			}
		}else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY;//"no";
		}
		//}
		LogUtil.info("result=="+stream.toXML(result));
		return stream.toXML(result);
	}
	
	
	
	// ********************************************* sumit start: No Motion Detected for X hours ********************************************
	public String toConfigureNoMotionPeriod(String xml0, String xml1, String xml2){
		XStream xStream = new XStream();
		Long id = (Long) xStream.fromXML(xml0); 
		String generatedDeviceId = (String) xStream.fromXML(xml1);
		String macId = (String) xStream.fromXML(xml2);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getNoMotionTimeOutByGeneratedDeviceId(id.longValue(), generatedDeviceId, macId);
		return xStream.toXML(device);
	}
	
	public String updateNoMotionTimeOut(String xmlDevice){
		String result = "";
		XStream xStream = new XStream();
		Device deviceFromWeb = (Device) xStream.fromXML(xmlDevice);
		String timeOutValue = deviceFromWeb.getModelNumber();
		String generatedDeviceId = deviceFromWeb.getGeneratedDeviceId();
		String macId = deviceFromWeb.getGateWay().getMacId();
		//LogUtil.info("timeout: "+timeOutValue+", devcId: "+generatedDeviceId+", macId: "+macId);
		
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDeviceByGeneratedDeviceId(generatedDeviceId);
		//1.Send message to iMVG
		ActionModel actionModel = new ActionModel(deviceFromWeb, null);
		ImonitorControllerAction setNoMotionTimeOut = new SetNoMotionTimeOut();
		setNoMotionTimeOut.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(setNoMotionTimeOut);
		
		//2.Update device if iMVG returns success.
		if(resultFromImvg){
			if(setNoMotionTimeOut.isActionSuccess()){
				device.setModelNumber(timeOutValue);
				if(deviceManager.updateDevice(device)){
					result = Constants.DB_SUCCESS;
				}else{
					result = Constants.DB_FAILURE;//"db";
				}
				
			}else{ //iMVG returned a failure
				result = Constants.iMVG_FAILURE;//"iMVG";
			}
		}else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY;//"no";
		}
		return xStream.toXML(result);
	}
	//********************************************** sumit end: No Motion Detected for X hours ************************************************

/*	 public String updateFriendlyNameAndLocation(String xml) {
		XStream xStream = new XStream();
		Device deviceFromWeb = (Device) xStream.fromXML(xml);
		long id = deviceFromWeb.getId();
		long locationId = deviceFromWeb.getLocation().getId();
		String friendlyName = deviceFromWeb.getFriendlyName();
		LocationManager locationManager = new LocationManager();
		Location location = locationManager.getLocationById(locationId);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDevice(id);
		device.setFriendlyName(friendlyName);
		device.setLocation(location);
		deviceManager.updateDevice(device);
		return xStream.toXML(device);
	}*/
	

	//sumit start: Re-Ordering Of Device <Update Position Index for the new Location>
	/**
	 * Updates Device related information as updated/modified/changed by the user as per his convenience.
	 * 
	 * @param xml java.lang.String
	 * @param oldLocIdXml java.lang.String
	 * @return  xml of Device Object.
	 */
	public String updateFriendlyNameAndLocation(String xml, String oldLocIdXml) {
		XStream xStream = new XStream();
		Device deviceFromWeb = (Device) xStream.fromXML(xml);
		
		String oldLocationId = (String) xStream.fromXML(oldLocIdXml);
		String result = "";
		
		long id = deviceFromWeb.getId();
		long locationId = deviceFromWeb.getLocation().getId();
		long iconId = deviceFromWeb.getIcon().getId();
		long oldLocId = Long.parseLong(oldLocationId);
		long posIdxMax = 0L;
		
		
		String friendlyName = deviceFromWeb.getFriendlyName();
		String locationName = "";
		String maxPosIdx = "";
		//sumit: Configure Device for Listing on Home Page.
		String enableList = deviceFromWeb.getEnableList();
				
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDevice(id);
		//String deviceFromDBXml = xStream.toXML(device);
		String generatedDeviceId = device.getGeneratedDeviceId();
		Device devicewithtype = deviceManager.getDeviceWithConfigurationAndMake(generatedDeviceId);
		String typeName = devicewithtype.getDeviceType().getName();
		LocationManager locationManager = new LocationManager();
		Location location = locationManager.getLocationById(locationId);
		locationName = location.getName();
				
		
		LocationMap locationMap = null;
		try {
			locationMap = device.getLocationMap();
			
		} catch (Exception e) {
			LogUtil.info("Location Map", e);
		}		
		
		
		//update the position index of the device as the last device for the location.
		if(oldLocId != locationId)
		{	
			//parishod added try catch block
			try{				
				if(locationMap != null)
				resetDevicePosition(xStream.toXML(device));
				
				}catch (Exception e) {
						// TODO: handle exception
				}
			
			locationMap = null;
			maxPosIdx = deviceManager.getMaxPositionIndexBasedOnLocation(locationName);
			if(maxPosIdx != null){
				posIdxMax = Long.parseLong(maxPosIdx);
				posIdxMax +=1;
			}
		}else{
			posIdxMax = device.getPosIdx();
			
		}
			
		IconManager iconManager = new IconManager();
		Icon icon = iconManager.getIconById(iconId);
		
		ActionModel actionModel = new ActionModel(deviceFromWeb,null);
		ImonitorControllerAction UpdateFriendlyName=new UpdateFriendlyName();
		UpdateFriendlyName.executeCommand(actionModel);
	
		boolean resultFromImvg = IMonitorUtil.waitForResult(UpdateFriendlyName);
		if(resultFromImvg)
		{
			if(UpdateFriendlyName.isActionSuccess())
			{
				device.setFriendlyName(friendlyName);
				device.setLocation(location);
				device.setIcon(icon);
				device.setEnableList(enableList);
				device.setPosIdx(posIdxMax);
				device.setLocationMap(locationMap);
				
				device.setPulseTimeOut(deviceFromWeb.getPulseTimeOut());
				//Naveen made changes for enable/disable hmd device
				if(typeName.equals(Constants.IP_CAMERA)){
			    	device.setEnableStatus(devicewithtype.getEnableStatus());
			    }else{
			    	device.setEnableStatus(deviceFromWeb.getEnableStatus());
			    }
			    if(typeName.equals("Z_WAVE_SWITCH") || typeName == "Z_WAVE_SWITCH"){
				device.setModelNumber(deviceFromWeb.getModelNumber());
			    }
				// end
				boolean dbUpdate = deviceManager.updateDevice(device);
				/*Naveen M S added
				 * 
				 */
				String modelnumber = deviceFromWeb.getModelNumber();
				//LogUtil.info("modeldelde: "+ modelnumber + " device type: "+ typeName);
				if((typeName.equals("Z_WAVE_SWITCH")) && (modelnumber == null)){
			
					deviceManager.deleteDeviceDetailsfromDashboard(friendlyName,id);
				}
				//End
				if(dbUpdate){
					result = Constants.DB_SUCCESS;//xStream.toXML(device);
					
					/*Naveen added on 6th sept 2018
					For updating device details to Alexa end point
					*/
					if((typeName.equals(Constants.Z_WAVE_AC_EXTENDER)) || (typeName.equals(Constants.Z_WAVE_SWITCH)) || (typeName.equals(Constants.Z_WAVE_DIMMER)) || (typeName.equals(Constants.Z_WAVE_AV_BLASTER))) {
					try {
						CustomerManager customerManager = new CustomerManager();
					//	device = deviceManager.getDeviceByGeneratedDeviceIdForAlexa(generatedDeviceId);
						String[] info = generatedDeviceId.split("-");
						
						Customer customer = customerManager.getCustomerByMacId(info[0]);
						
						Alexamanager alexaManager = new Alexamanager();
						Alexa alexaUser = alexaManager.getAlexaUserByCustomerId(customer);
						
						if(alexaUser != null) {
							AlexaProactiveUpdateService alexaProactiveService = new AlexaProactiveUpdateService(customer, alexaUser,device);
							Thread t = new Thread(alexaProactiveService);
							 t.start();
						}
						
						
					} catch (Exception e) {
						// TODO: handle exception
						LogUtil.info("could not update device info to alexa : " + e.getMessage());
					}
					
					}
				}else{
					result = Constants.DB_FAILURE;		//DB update failure.
				}
			}else{
				result = Constants.iMVG_FAILURE;		//iMVG returned failure.
			}
		}else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY; //Gateway did not respond.
		}
		
		return xStream.toXML(result);
	}
// ******************************************************************* sumit end ********************************************************

	public String listDeviceDetailsActionByCustomerId(
			String customerId) {
		List<Object[]> listOfDevicesWithActions = new DeviceManager()
				.listActionTypesOfDevicesByCustomerId(customerId);
		Object[] results = new Object[] {listOfDevicesWithActions};
		XStream stream = new XStream();
		String xml = stream.toXML(results);
		return xml;
	}
	
	public String listDeviceDetailsAlertTypesOfDevicesByCustomerId(
			String customerId) {
		List<Object[]> listOfDevicesWithAlerts = new DeviceManager()
				.listAlertTypesOfDevicesByCustomerId(customerId);
		List<Object[]> listOfDevicesWithActions = new DeviceManager()
				.listActionTypesOfDevicesByCustomerId(customerId);
		List<Object[]> listOfUserNotifications = new DeviceManager()
				.listUserNotificatioinsByCustomerId(customerId);
		Object[] results = new Object[] { listOfDevicesWithAlerts,
				listOfDevicesWithActions, listOfUserNotifications };
		XStream stream = new XStream();
		String xml = stream.toXML(results);
		return xml;
	}
	
//bhavya start

	public String listdevicefromdevicetype(String decicetype,String customerid){
		String result = "";
		XStream xStream = new XStream();
	    String devicetype = (String) xStream.fromXML(decicetype);
	    long custid = (Long)xStream.fromXML(customerid);
		DeviceManager deviceManager = new DeviceManager();
		List<Device> device = (List<Device>) deviceManager.listdevicefromdevicetype(devicetype,custid);
		result = xStream.toXML(device);
		return result;
	}
	//bhavya end
	/*
	
	public String getMotorConfigurationByDeviceId(
			String xml) {
		
		XStream xStream = new XStream();
		String generatedDeviceId = (String) xStream.fromXML(xml);
		MotorConfiguration devCongif = new DeviceManager()
				.getMotorConfigurationByDeviceId(generatedDeviceId);
		
		String xml1 = xStream.toXML(devCongif);
		return xml1;
	}
*/	
 	public String getMotorConfigurationByDeviceId(
			String xml) {
		
		XStream xStream = new XStream();
		String generatedDeviceId = (String) xStream.fromXML(xml);
		Device devWithConf = new DeviceManager()
				.getDeviceWithConfigurationAndMake(generatedDeviceId);
		
		Device d = new Device();
		Make m = new Make();
		GateWay gateway=new GateWay();
		gateway.setId(devWithConf.getGateWay().getId());
		gateway.setMacId(devWithConf.getGateWay().getMacId());
		if(devWithConf.getMake() != null)
		{
			m.setName(devWithConf.getMake().getName());
			m.setNumber(devWithConf.getMake().getNumber());
			m.setDetails(devWithConf.getMake().getDetails());
			m.setId(devWithConf.getMake().getId());
		}
		d.setGeneratedDeviceId(devWithConf.getGeneratedDeviceId());
		d.setGateWay(gateway);
		d.setMake(m);
		d.setDeviceConfiguration(devWithConf.getDeviceConfiguration());
		
		String xml1 = xStream.toXML(d);
		return xml1;
	}
	
 	public String getDeviceById(String xmlString){
		String result = "";
		XStream xStream = new XStream();
		long id = (Long) xStream.fromXML(xmlString);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDevice(id);
		result = xStream.toXML(device);
		return result;
	}
 	public String updateDevicePosition(String xml) throws Exception {
 		XStream xStream = new XStream();
		Device device = (Device) xStream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		LocationMap locMap=device.getLocationMap();
		device=deviceManager.getDevice(device.getId());
		locMap.setDevice(device);
		device.setLocationMap(locMap);
		deviceManager.updateDevice(device);
		/*deviceManager.SaveDevicePosition(device);
		deviceManager.getDevicePosition(device);*/
		return null;
	}
 	public String resetDevicePosition(String xml) throws Exception {
 		XStream xStream = new XStream();
		Device device = (Device) xStream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		device=deviceManager.getDevice(device.getId());
		deviceManager.RemoveDevicePosition(device);
		return null;
	}
 	
 	
 	
 	public String updatepasswordfordoorlock(String xml) 
			throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
		XStream stream = new XStream();
		DoorLockConfiguration doorLockConfiguration = (DoorLockConfiguration) stream.fromXML(xml);
		String result = "";
	//	String generatedDeviceId = device.getGeneratedDeviceId();
		DoorLockConfiguration doorConfiguration = new DoorLockConfiguration();
		DeviceManager deviceManager = new DeviceManager();
		ActionModel actionModel = new ActionModel(doorLockConfiguration);
		ImonitorControllerAction setPassCodeForDoorLock = new SetPassCodeForDoorLock();
		try {
			setPassCodeForDoorLock.executeCommand(actionModel);
		} catch (Exception e) {
			LogUtil.info("Error", e);
		}
		boolean resultFromImvg = IMonitorUtil.waitForResult(setPassCodeForDoorLock);
		if(resultFromImvg){
			if(setPassCodeForDoorLock.isActionSuccess()){
							deviceManager.updateDoorLock(doorConfiguration);
		
				
			}else{
				
				result = Constants.iMVG_FAILURE;
			}
		}else{
			
			result = Constants.NO_RESPONSE_FROM_GATEWAY;
		}
	
	
		return stream.toXML(result);
		
	}
 	
 	public String getMakeWithId(String xml){
		XStream stream = new XStream();
		String Id = (String) stream.fromXML(xml);
		DeviceManager deviceManager = new DeviceManager();
		Make make = deviceManager.getMakeById(Id);
		return stream.toXML(make);
	}
	
 	public String getdeviceConfig(String xml){
		XStream stream = new XStream();
		Device device = (Device) stream.fromXML(xml);
		String generatedDeviceId = device.getGeneratedDeviceId();
		DeviceManager deviceManager = new DeviceManager();
		Device deviceFromDB = deviceManager.getDeviceWithConfiguration(generatedDeviceId);		
		String learnValuesOfAc = deviceManager.getLearnValuesOfAc(deviceFromDB.getDeviceConfiguration().getId());
		return stream.toXML(learnValuesOfAc);
	}
 	
 	
	/*Fetaure: To remove dead nodes we get list of node ids from gateway and compare it with CMS device list
 	 * Implemented by: Naveen M S
 	 * date: 04-april-2014
 	 *  
 	 */

 	public String getComaparedDeviceList(String xml){
 		String result = "";
		XStream stream = new XStream();
		
		String macId = (String) stream.fromXML(xml);
		ActionModel actionModel = new ActionModel(null, xml);
		ImonitorControllerAction controllerAction = new GetDeviceListAction();
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			
			
			
			
		
			CustomerManager customerManager=new CustomerManager();
			Customer customer = customerManager.getCustomerByMacId(macId);
			List<Device> devices = customerManager.getAllDevicesOfCustomer(customer);
			List<Device> devicesListOfCustomer = new ArrayList<Device>(devices);
			return stream.toXML(devicesListOfCustomer);
			
		}else{
			result = "imvg_failure";
		
			return stream.toXML(result);
		}
		
		
	}
 	
 	public String deleteSelectedDeadNode(String xml){
 		
		XStream stream = new XStream();
		String xmlData = (String) stream.fromXML(xml);
		
		
		DeviceManager deviceManager = new DeviceManager();
		ActionModel actionModel = new ActionModel(null, xml);
		ImonitorControllerAction controllerAction = new DeleteDeadNodeAction();
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
		
		String result;
		if(resultFromImvg){
			if(controllerAction.isActionSuccess()){
				Device device = deviceManager.getDeviceByGeneratedDeviceId(xmlData);
			//	LogUtil.info("Deleting device from database: "+ device.getGeneratedDeviceId());
				boolean dbUpdate1 = deviceManager.deleteDevice(device);
						
				
				result = Constants.DB_SUCCESS;
				
					if(!dbUpdate1){
						result = Constants.DB_FAILURE;
					}
				
				
				
				
			}else{
				result = Constants.iMVG_FAILURE;
			}
		}else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY;
		}
		
	
		return stream.toXML(result);
		
		
		
	}
 	
 	
public String togetZwaveVersion(String xml){
 		String result = "failure";
	
	
		ActionModel actionModel = new ActionModel(null, xml);
		ImonitorControllerAction controllerAction = new GetZWaveVersion();
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			
			result = (String) controllerAction.getActionModel().getData();
			//LogUtil.info("result from imvg: "+ result);
		}
		return result;
		
		
		
	}
 	
 	//end

public String getDiviceListForMinimote(String xml){
	XStream stream = new XStream();
   
    String macid = (String) stream.fromXML(xml);
    DeviceManager deviceManger = new DeviceManager();
    List<Device> Device = deviceManger.listDevicesByMacIdforMinimoteConfig(macid);
    //  LogUtil.info("list of devices: "+ stream.toXML(Device));
    
	return stream.toXML(Device);
}

public String getMiniMoteConfigurationByDeviceId(
		String xml) {
	
	XStream xStream = new XStream();
	String generatedDeviceId = (String) xStream.fromXML(xml);
	Device devWithConf = new DeviceManager()
			.getDeviceWithConfigurationAndMake(generatedDeviceId);
	
	Device d = new Device();
	
	GateWay gateway=new GateWay();
	gateway.setId(devWithConf.getGateWay().getId());
	gateway.setMacId(devWithConf.getGateWay().getMacId());
	
	d.setGeneratedDeviceId(devWithConf.getGeneratedDeviceId());
	d.setGateWay(gateway);
	d.setDeviceConfiguration(devWithConf.getDeviceConfiguration());
	
	String xml1 = xStream.toXML(d);
	return xml1;
}


public String configMinimote(String deviceXml){
	XStream stream = new XStream();
    String result = null;
    Device device = (Device) stream.fromXML(deviceXml);
    String generatedDeviceID = device.getGeneratedDeviceId();
    String macID = device.getGateWay().getMacId();
    MinimoteConfig miniMoteConfig = null;
    miniMoteConfig = (MinimoteConfig)device.getDeviceConfiguration();
	DeviceManager deviceManager = new DeviceManager();
 //   ActionModel actionModel = new ActionModel(device, data);
    ActionModel actionModel = new ActionModel(device, null);
    ImonitorControllerAction configMinimoteAction = new ConfigMinimoteAction();
    configMinimoteAction.executeCommand(actionModel);    
    boolean resultFromImvg = IMonitorUtil.waitForResult(configMinimoteAction);
    if(resultFromImvg){
    
    	if(configMinimoteAction.isActionSuccess()){
    		if(miniMoteConfig.getButtonone().contains("HOME") || miniMoteConfig.getButtonone().contains("STAY") || miniMoteConfig.getButtonone().contains("AWAY")){
    			miniMoteConfig.setButtonone(macID+"-"+miniMoteConfig.getButtonone());
    		}
    		if(miniMoteConfig.getButtontwo().contains("HOME") || miniMoteConfig.getButtontwo().contains("STAY") || miniMoteConfig.getButtontwo().contains("AWAY")){
    			miniMoteConfig.setButtontwo(macID+"-"+miniMoteConfig.getButtontwo());
    		}
    		if(miniMoteConfig.getButtonthree().contains("HOME") || miniMoteConfig.getButtonthree().contains("STAY") || miniMoteConfig.getButtonthree().contains("AWAY")){
    			miniMoteConfig.setButtonthree(macID+"-"+miniMoteConfig.getButtonthree());
    		}
    		if(miniMoteConfig.getButtonfour().contains("HOME") || miniMoteConfig.getButtonfour().contains("STAY") || miniMoteConfig.getButtonfour().contains("AWAY")){
    			miniMoteConfig.setButtonfour(macID+"-"+miniMoteConfig.getButtonfour());
    		}
    	
    		if(!deviceManager.updateMinimoteByGeneratedDeviceId(generatedDeviceID,miniMoteConfig)){
    			
    			result = Constants.DB_FAILURE;
    		}else{
    			result = Constants.DB_SUCCESS;
    		}
    		
    	}else{
    		
    		result = Constants.iMVG_FAILURE;	
    	}
    	
    	
    	
    }else{
    	
    	result = Constants.NO_RESPONSE_FROM_GATEWAY; //gateway did not respond back.
    }
   	return stream.toXML(result);
}

public String resetDevicePositionIdex(String xml) throws Exception {
	XStream xStream = new XStream();
	Device device = (Device) xStream.fromXML(xml);
	DeviceManager deviceManager = new DeviceManager();
	device=deviceManager.getDevice(device.getId());
	deviceManager.RemoveDevicePositionIndex(device);
	return null;
}

public String devicecontrolApi(String generated,String command,String userid,String password,String id,String value)throws Exception{
	XStream stream=new XStream();
	String result=null;
	CustomerManager customerManager=new CustomerManager();
	DeviceManager devicemanger=new DeviceManager();
	GatewayManager gtmang=new GatewayManager();
	AgentManager agentManager = new AgentManager();
	DeviceManager devmanager=new DeviceManager();
	Device device=new Device();
	boolean user=customerManager.loginpasscheckapi(userid,password,id);
	
	if(user==true){
		Customer cusid=customerManager.getCustomerByCustomerId(id);
		long custid=cusid.getId();
		
		String macId=gtmang.getmacidbycustomerid(custid);
		Agent agent = agentManager.getReceiverIpAndPort(macId);
		
		GateWay gateway=gtmang.getGateWayByMacId(macId);
		device=devmanager.getDeviceByGeneratedDeviceId(generated);
		gateway.setAgent(agent);
		device.setGateWay(gateway);
		device.getGateWay().setAgent(agent);
		device.setCommandParam(value);
		
		if(command.equalsIgnoreCase("ZwaveAc")){
		
			int val=Integer.parseInt(value);
			if(val>=16 && val<=28){
				device.setCommandParam(value);
				
				ActionModel actionModel = new ActionModel(device, null);
				ImonitorControllerAction controllerAction = new AcTempeartureControlAction();
				controllerAction.executeCommand(actionModel);
				IMonitorUtil.waitForResult(controllerAction);
				if(IMonitorUtil.waitForResult(controllerAction)==false){
					result=null;
				}else{device = controllerAction.getActionModel().getDevice();
				
					result=stream.toXML(device);}
			}else{
			device.setCommandParam(value);
			
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new AcControlAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			if(IMonitorUtil.waitForResult(controllerAction)==false){
				result=null;
			}else{device = controllerAction.getActionModel().getDevice();
			
				result=stream.toXML(device);}}
		}else if(command.equalsIgnoreCase("ZwaveAcFanMode")){
			acConfiguration acconfig = new acConfiguration();
			acconfig.setFanModeValue(value);
			device.setDeviceConfiguration(acconfig);
			Device deviceFromDB = devicemanger.getDeviceWithConfiguration(generated);
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
			
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new AcFanModeControlAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			if(IMonitorUtil.waitForResult(controllerAction)==false){
				result=null;
			}else{device = controllerAction.getActionModel().getDevice();
			
				result=stream.toXML(device);}
		}else if(command.equalsIgnoreCase("ZwaveAcSwing")){
			acConfiguration acconfig = new acConfiguration();
			acconfig.setAcSwing(value);
			device.setDeviceConfiguration(acconfig);
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new AcSwingControlAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			if(IMonitorUtil.waitForResult(controllerAction)==false){
				result=null;
			}else{device = controllerAction.getActionModel().getDevice();
			
				result=stream.toXML(device);}
		}else if(command.equalsIgnoreCase("ZwaveSw")||command.equalsIgnoreCase("ZwaveSir")){
		
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = null;
		if(device.getCommandParam().equalsIgnoreCase("1")){
			controllerAction = new SwitchOnAction();
		} else{
			controllerAction = new SwitchOffAction();
		}
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		
		if(IMonitorUtil.waitForResult(controllerAction)==false){
			result=null;
		}else{device = controllerAction.getActionModel().getDevice();
		
			result=stream.toXML(device);}
		}else if(command.equalsIgnoreCase("ZWDevDimUp")||command.equalsIgnoreCase("ZWDevDimDown")){
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new DimmerLevelChangeAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		
		if(IMonitorUtil.waitForResult(controllerAction)==false){
			result=null;
		}else{device = controllerAction.getActionModel().getDevice();
		result=stream.toXML(device);
		}
		}else if(command.equalsIgnoreCase("ZWDevCurtainOpen")||command.equalsIgnoreCase("ZWDevCurtainClose")){
			ActionModel actionModel = new ActionModel(device,null);
			ImonitorControllerAction controllerAction = null;
			String id1=device.getGeneratedDeviceId();
			Device devicefromdb=devicemanger.getDeviceByGeneratedDeviceId(id1);
			long commandparamFromDb=Long.parseLong(devicefromdb.getCommandParam());
			long commandparamFromWeb=Long.parseLong(device.getCommandParam());
			
			if(commandparamFromWeb >= commandparamFromDb){
				controllerAction = new CurtainopenAction();
			} 
			else
			{
				controllerAction = new CurtainCloseAction();
			}
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
		
			if(IMonitorUtil.waitForResult(controllerAction)==false){
				result=null;
			}else{device = controllerAction.getActionModel().getDevice();
			result=stream.toXML(device);
			}
		}
	}else{
	result=null;}
	return result;
}
public String alexacontrolApi(String generated,String id,String gatewayId,String command,String value)throws Exception{
	XStream stream=new XStream();
	String result=null;
	CustomerManager customerManager=new CustomerManager();
	DeviceManager devicemanger=new DeviceManager();
	GatewayManager gtmang=new GatewayManager();
	AgentManager agentManager = new AgentManager();
	DeviceManager devmanager=new DeviceManager();
	Device device=new Device();
	boolean checking=customerManager.checkCustomerExistence(id);
	if(checking){
	Customer customer=customerManager.getCustomerByCustomerId(id);
	long custid=customer.getId();
	
	String macId=gtmang.getmacidbycustomerid(custid);
	Agent agent = agentManager.getReceiverIpAndPort(macId);
	
	GateWay gateway=gtmang.getGateWayByMacId(macId);
	long gatewayid=gateway.getId();
	String generatedid=devmanager.getDeviceByDid(generated, gatewayid);
	
	if(generatedid==null){
		return result=null;
	}
	//device=devmanager.getDeviceByGeneratedDeviceId(generatedid);
	device = devmanager.getDeviceByGeneratedIdWithStatus(generatedid);
		gateway.setAgent(agent);
		device.setGateWay(gateway);
		device.getGateWay().setAgent(agent);
		device.setCommandParam(value);
		
		
		
		if(command.equalsIgnoreCase("SetAcTemp")){
		
		
			
			device.setCommandParam(value);
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new AcTempeartureControlAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			if(IMonitorUtil.waitForResult(controllerAction)==false){
				result=null;
			}else{device = controllerAction.getActionModel().getDevice();
		
				result=stream.toXML(device);}
		
	}
		else if(command.equalsIgnoreCase("SetAcMode")){
			
			acConfiguration acconfig = new acConfiguration();
			acconfig.setFanModeValue(value);
			device.setDeviceConfiguration(acconfig);
			Device deviceFromDB = devicemanger.getDeviceWithConfiguration(generatedid);
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
			
			ActionModel actionModel = new ActionModel(device, null);
		//	ImonitorControllerAction controllerAction = new AcFanModeControlAction();
			ImonitorControllerAction controllerAction = new AcControlAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			if(IMonitorUtil.waitForResult(controllerAction)==false){
				result=null;
			}else{device = controllerAction.getActionModel().getDevice();
			
				result=stream.toXML(device);}
			
		}
		else if(command.equalsIgnoreCase("SetAcOff")){
			
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new AcControlOffAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			if(IMonitorUtil.waitForResult(controllerAction)==false){
				result=null;
			}else{device = controllerAction.getActionModel().getDevice();
			
				result=stream.toXML(device);}
			
		}
		else if(command.equalsIgnoreCase("AdjustAcTemp")){
			
			Device deviceAc =devmanager.getDeviceByGeneratedDeviceId(generatedid);
			int previous=Integer.parseInt(deviceAc.getCommandParam());
			int current=Integer.parseInt(value);
			
			int m=previous+current;
			if(m > 28){
				m = 28;
			}else if(m < 16){
				m = 16;
			}
			
			device.setCommandParam(String.valueOf(m));
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new AcTempeartureControlAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			if(IMonitorUtil.waitForResult(controllerAction)==false){
				result=null;
			}else{device = controllerAction.getActionModel().getDevice();
			
				result=stream.toXML(device);}
		}
		else if(command.equalsIgnoreCase("ZwaveAcFanMode")){
		
		acConfiguration acconfig = new acConfiguration();
		acconfig.setFanModeValue(value);
		device.setDeviceConfiguration(acconfig);
		Device deviceFromDB = devicemanger.getDeviceWithConfiguration(generatedid);
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
		
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new AcFanModeControlAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		if(IMonitorUtil.waitForResult(controllerAction)==false){
			result=null;
		}else{device = controllerAction.getActionModel().getDevice();
		
			result=stream.toXML(device);}
	}
	
	else if(command.equalsIgnoreCase("ZwaveSw")){
		
        if(device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_DOWN)) {
			
			return result=Constants.ENDPOINT_UNREACHABLE;
		}else{
			
			AlexaControlAction alexaControl = new AlexaControlAction(device, command, value);
			Thread t1 = new Thread(alexaControl);
			t1.start();
			return result=stream.toXML(device);
			
		}
        
        }else if(command.equalsIgnoreCase("ZWDevDimUp")){
			int previous=Integer.parseInt(device.getCommandParam());
			int current=Integer.parseInt(value);
			int m=previous+current;
			 if (m > 100) {
	             m = 100;
	           }
			device.setCommandParam(String.valueOf(m));
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new DimmerLevelChangeAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			
			if(IMonitorUtil.waitForResult(controllerAction)==false){
				result=null;
			}else{device = controllerAction.getActionModel().getDevice();
			result=stream.toXML(device);
		}
		}
		else if(command.equalsIgnoreCase("ZWDevDimDown")){
		int previous=Integer.parseInt(device.getCommandParam());
		int current=Integer.parseInt(value);
		int m=previous-current;
		 if (m < 0) {
             m = 0;
           }
		device.setCommandParam(String.valueOf(m));
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new DimmerLevelChangeAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
	
		if(IMonitorUtil.waitForResult(controllerAction)==false){
			result=null;
		}else{device = controllerAction.getActionModel().getDevice();
		result=stream.toXML(device);
		}
		}   
		else if(command.equalsIgnoreCase("SetZWDim")){
		  
           
            device.setCommandParam(value);
            ActionModel actionModel = new ActionModel(device, null);
            ImonitorControllerAction controllerAction = new DimmerLevelChangeAction();
            controllerAction.executeCommand(actionModel);
            IMonitorUtil.waitForResult(controllerAction);
            //  LogUtil.info(Boolean.valueOf(IMonitorUtil.waitForResult((ImonitorControllerAction)localObject3)));
            if(IMonitorUtil.waitForResult(controllerAction)==false){
    			result=null;
    		}else{device = controllerAction.getActionModel().getDevice();
    		result=stream.toXML(device);
    		}
		} 
		
		
	}else{
	result=null;}
	return result;
}

public String googlecontrolApi(String generated,String id,String command,String value)throws Exception{
	XStream stream=new XStream();
	String result=null;
	String values="";
	CustomerManager customerManager=new CustomerManager();
	DeviceManager devicemanger=new DeviceManager();
	GatewayManager gtmang=new GatewayManager();
	AgentManager agentManager = new AgentManager();
	DeviceManager devmanager=new DeviceManager();
	Device device=new Device();
	boolean checking=customerManager.checkCustomerExistence(id);
	if(checking){
	Customer customer=customerManager.getCustomerByCustomerId(id);
	long custid=customer.getId();
	
	String macId=gtmang.getmacidbycustomerid(custid);
	Agent agent = agentManager.getReceiverIpAndPort(macId);
	GateWay gateway=gtmang.getGateWayByMacId(macId);
	long gatewayid=gateway.getId();
	String generatedid=devmanager.getDeviceByfriendlyName(generated,gatewayid);
	
	if(generatedid==null){
		return result=null;
	}
	device=devmanager.getDeviceByGeneratedDeviceId(generatedid);
		gateway.setAgent(agent);
		device.setGateWay(gateway);
		device.getGateWay().setAgent(agent);
		DeviceType devtype=device.getDeviceType();
		if(devtype.getName().equalsIgnoreCase("Z_WAVE_AC_EXTENDER")&&(!command.equalsIgnoreCase("ZwaveAcFanMode"))&&(!command.equalsIgnoreCase("ZwaveAcSwing"))){
			if(value.equals("1")){
				values="5";
				command="ZwaveAc";
				device.setCommandParam(values);
			}else if(value.equals("0")){
				command="ZwaveAc";
				device.setCommandParam(value);
			}else{
				command="ZwaveAc";
				
				device.setCommandParam(value);
			}
		}else{
			device.setCommandParam(value);
		}
		if(command.equalsIgnoreCase("ZwaveAc")){
		int val=Integer.parseInt(value);
		if(val>=16 && val<=28){
			device.setCommandParam(value);
		
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new AcTempeartureControlAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			if(IMonitorUtil.waitForResult(controllerAction)==false){
				result=null;
			}else{device = controllerAction.getActionModel().getDevice();
			
				result=stream.toXML(device);}
		}else{
		
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new AcControlAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		if(IMonitorUtil.waitForResult(controllerAction)==false){
			result=null;
		}else{device = controllerAction.getActionModel().getDevice();
		
			result=stream.toXML(device);}}
	}else if(command.equalsIgnoreCase("ZwaveAcFanMode")){
		acConfiguration acconfig = new acConfiguration();
		acconfig.setFanModeValue(value);
		device.setDeviceConfiguration(acconfig);
		Device deviceFromDB = devicemanger.getDeviceWithConfiguration(generatedid);
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
		
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new AcFanModeControlAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		if(IMonitorUtil.waitForResult(controllerAction)==false){
			result=null;
		}else{device = controllerAction.getActionModel().getDevice();
		
			result=stream.toXML(device);}
	}else if(command.equalsIgnoreCase("ZwaveAcSwing")){
		acConfiguration acconfig = new acConfiguration();
		acconfig.setAcSwing(value);
		device.setDeviceConfiguration(acconfig);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new AcSwingControlAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		if(IMonitorUtil.waitForResult(controllerAction)==false){
			result=null;
		}else{device = controllerAction.getActionModel().getDevice();
		
			result=stream.toXML(device);}
	}else if(command.equalsIgnoreCase("ZwaveSw")||command.equalsIgnoreCase("ZwaveSir")){
		
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = null;
		if(device.getCommandParam().equalsIgnoreCase("1")){
			controllerAction = new SwitchOnAction();
		} else{
			controllerAction = new SwitchOffAction();
		}
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		if(IMonitorUtil.waitForResult(controllerAction)==false){
			result=null;
		}else{device = controllerAction.getActionModel().getDevice();
		
			result=stream.toXML(device);}
		}else if(command.equalsIgnoreCase("ZWDevDimUp")||command.equalsIgnoreCase("ZWDevDimDown")){
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new DimmerLevelChangeAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		
		if(IMonitorUtil.waitForResult(controllerAction)==false){
			result=null;
		}else{device = controllerAction.getActionModel().getDevice();
		result=stream.toXML(device);
		}
		}else if(command.equalsIgnoreCase("ZWDevCurtainOpen")||command.equalsIgnoreCase("ZWDevCurtainClose")){
			ActionModel actionModel = new ActionModel(device,null);
			ImonitorControllerAction controllerAction = null;
			String id1=device.getGeneratedDeviceId();
			Device devicefromdb=devicemanger.getDeviceByGeneratedDeviceId(id1);
			long commandparamFromDb=Long.parseLong(devicefromdb.getCommandParam());
			long commandparamFromWeb=Long.parseLong(device.getCommandParam());
			
			if(commandparamFromWeb >= commandparamFromDb){
				controllerAction = new CurtainopenAction();
			} 
			else
			{
				controllerAction = new CurtainCloseAction();
			}
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			
			if(IMonitorUtil.waitForResult(controllerAction)==false){
				result=null;
			}else{device = controllerAction.getActionModel().getDevice();
			result=stream.toXML(device);
			}
		}
	}else{
	result=null;}
	return result;
}

public String listAllFunctions(){
	
	XStream xStream=new XStream();
	DeviceManager functionsManager = new DeviceManager();
	List<Object[]> functionsObjects = functionsManager.listAllFunctions();
	List<Functions> functions = new ArrayList<Functions>();
	if(functionsObjects != null)
	{
		for(Object[] scenario:functionsObjects){
			Functions functions1 = new Functions();
			functions1.setId(Long.parseLong(IMonitorUtil.convertToString(scenario[0])));
			functions1.setName(IMonitorUtil.convertToString(scenario[1]));	
			functions1.setCategory(IMonitorUtil.convertToString(scenario[2]));
			functions1.setFunctionId(Long.parseLong(IMonitorUtil.convertToString(scenario[3])));
			functions.add(functions1);
		}
	}
	String valueInXml = xStream.toXML(functions);
	
	return valueInXml;
}

public String listDeviceForActions(String actionXml,String gatewayIdXml){
	
	XStream stream = new XStream();
	String deviceCategory = (String) stream.fromXML(actionXml);
	
	long gatewayId = (Long)stream.fromXML(gatewayIdXml);
	
	DeviceManager deviceManager = new DeviceManager();

	List<Object[]> objects = deviceManager.listActionDevices(deviceCategory,gatewayId);
	
	List<Device> deviceList = new ArrayList<Device>();
	if(objects != null)
	{
		
		for(Object[] devicefrmdb:objects){
			Device device=new Device();
			DeviceType deviceType =new DeviceType();
			device.setId(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[0])));
			device.setDeviceId(((IMonitorUtil.convertToString(devicefrmdb[1]))));
			device.setGateWay(null);
			device.setGeneratedDeviceId(((IMonitorUtil.convertToString(devicefrmdb[3]))));
			device.setFriendlyName(((IMonitorUtil.convertToString(devicefrmdb[4]))));
			device.setBatteryStatus(((IMonitorUtil.convertToString(devicefrmdb[5]))));
			device.setModelNumber(((IMonitorUtil.convertToString(devicefrmdb[6]))));
			device.setDeviceType(null);
			device.setCommandParam(((IMonitorUtil.convertToString(devicefrmdb[8]))));
			device.setLastAlert(null);
			device.setArmStatus(null);
			device.setMake(null);
			device.setLocation(null);
			device.setDeviceConfiguration(null);
			device.setEnableStatus(((IMonitorUtil.convertToString(devicefrmdb[14]))));
			device.setIcon(null);
			device.setMode(((IMonitorUtil.convertToString(devicefrmdb[16]))));
			device.setEnableList(((IMonitorUtil.convertToString(devicefrmdb[17]))));
			device.setPosIdx(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[18])));
			device.setLocationMap(null);
			device.setSwitchType(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[20])));
			device.setDevicetimeout(((IMonitorUtil.convertToString(devicefrmdb[21]))));
			device.setPulseTimeOut(((IMonitorUtil.convertToString(devicefrmdb[22]))));
			deviceType.setName(((IMonitorUtil.convertToString(devicefrmdb[23]))));
			device.setDeviceType(deviceType);
			deviceList.add(device);
			
			
			
		}
		
	}
	String valueInXml = stream.toXML(deviceList);
	
	
	return valueInXml;
}

public String listNonHmdDeviceForActions(String actionXml,String gatewayIdXml){
	
	XStream stream = new XStream();
	String deviceCategory = (String) stream.fromXML(actionXml);
	long gatewayId = (Long)stream.fromXML(gatewayIdXml);
	DeviceManager deviceManager = new DeviceManager();
	List<Object[]> objects = deviceManager.listActionDevices(deviceCategory,gatewayId);
	
	List<Device> deviceList = new ArrayList<Device>();
	if(objects != null)
	{
		
		for(Object[] devicefrmdb:objects){
			Device device=new Device();
			DeviceType deviceType =new DeviceType();
			device.setId(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[0])));
			device.setDeviceId(((IMonitorUtil.convertToString(devicefrmdb[1]))));
			device.setGateWay(null);
			device.setGeneratedDeviceId(((IMonitorUtil.convertToString(devicefrmdb[3]))));
			device.setFriendlyName(((IMonitorUtil.convertToString(devicefrmdb[4]))));
			device.setBatteryStatus(((IMonitorUtil.convertToString(devicefrmdb[5]))));
			device.setModelNumber(((IMonitorUtil.convertToString(devicefrmdb[6]))));
			device.setDeviceType(null);
			device.setCommandParam(((IMonitorUtil.convertToString(devicefrmdb[8]))));
			device.setLastAlert(null);
			device.setArmStatus(null);
			device.setMake(null);
			device.setLocation(null);
			device.setDeviceConfiguration(null);
			device.setEnableStatus(((IMonitorUtil.convertToString(devicefrmdb[14]))));
			device.setIcon(null);
			device.setMode(((IMonitorUtil.convertToString(devicefrmdb[16]))));
			device.setEnableList(((IMonitorUtil.convertToString(devicefrmdb[17]))));
			device.setPosIdx(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[18])));
			device.setLocationMap(null);
			device.setSwitchType(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[20])));
			device.setDevicetimeout(((IMonitorUtil.convertToString(devicefrmdb[21]))));
			device.setPulseTimeOut(((IMonitorUtil.convertToString(devicefrmdb[22]))));
			deviceType.setName(((IMonitorUtil.convertToString(devicefrmdb[23]))));
			device.setDeviceType(deviceType);
			//Added for 16Amp Switch Bug Fixing
			if (device.getModelNumber().equals("HMD"))
			{
				LogUtil.info("Model HMD not added to device List");
			}
			else 
			{
				deviceList.add(device);
			}
			
			
		}
		
	}
	String valueInXml = stream.toXML(deviceList);
	
	
	return valueInXml;
}

public String listScenariosForActions(String gatewayIdXml)
{
	
	XStream stream = new XStream();
	long gatewayId = (Long)stream.fromXML(gatewayIdXml);
	GatewayManager gatewayManager=new GatewayManager();
	GateWay gateWay=gatewayManager.getGateWayById(gatewayId);
	CustomerManager customerManager=new CustomerManager();
	Customer customer=customerManager.getCustomerByGateWay(gateWay);
	long customerId=customer.getId();
	
	ScenarioManager scenarioManager = new ScenarioManager();
	List<Object[]> scenariosObjects = scenarioManager.listAllScenariosByCustomerId(customerId);
	List<Scenario> scenarios = new ArrayList<Scenario>();
	if(scenariosObjects != null)
	{
		for(Object[] scenario:scenariosObjects){
			Scenario scenario2 = new Scenario();
			scenario2.setId(Long.parseLong(IMonitorUtil.convertToString(scenario[0])));
			scenario2.setName(IMonitorUtil.convertToString(scenario[1]));				
			scenario2.setDescription(IMonitorUtil.convertToString(scenario[2]));
			scenario2.setIconFile(IMonitorUtil.convertToString(scenario[3]));
			scenario2.setScenarioActions(null);
			scenarios.add(scenario2);
		}
	}
	String valueInXml = stream.toXML(scenarios);
	
	return valueInXml;
}

public String getGateWay(String customerxml){
	
	XStream xStream=new XStream();
	Customer customer = (Customer)xStream.fromXML(customerxml);
	GatewayManager gatewayManager = new GatewayManager();
	GateWay gateway =gatewayManager.getGateWayByCustomer(customer);
	String valueInXml = xStream.toXML(gateway);
	
	
	return valueInXml;
}

public String saveAction(String actionxml){
	
	
	XStream xStream=new XStream();
	Action action = (Action)xStream.fromXML(actionxml);
    ScenarioManager manager = new ScenarioManager();
	DeviceManager devicemanager = new DeviceManager();
	if (action.getFunctions().getName().equals("SCENARIO")) 
	{
		
		Scenario scenario = manager.getScenarioById(action.getConfigurationId());
		action.setScenario(scenario);
		action.setDevice(null);
		
	}
	else
	{
		
		Device device = devicemanager.getDevice(action.getConfigurationId());
		action.setDevice(device);
	    action.setScenario(null);
		
	}
		
	//Validate Action Object 
	GatewayManager gatewayManager=new GatewayManager();
	GateWay gateWay=gatewayManager.getGateWayById(action.getGateWay().getId());
	Customer customer =gateWay.getCustomer();
	String customerxml=xStream.toXML(customer);
	String ListOfActionsFrmDb=listAllActions(customerxml);
	
	List<Action> ActionList=(List<Action>) xStream.fromXML(ListOfActionsFrmDb);
	for (Action action2 : ActionList)
	{
		if (action2.getActionName().equalsIgnoreCase(action.getActionName())) 
		{
			
			String result="duplicateName";
			return xStream.toXML(result);
			
		}
		
	}
	

	new DaoManager().save(action);

	String result = Constants.FAILURE;
	try
	{
	
	//Saving into Db
	ActionModel actionModel = new ActionModel(null, action);
	ImonitorControllerAction actionCreate = new ConfigureAddActions();
	actionCreate.executeCommand(actionModel);
	boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
	if(resultFromImvg){
		if(actionCreate.isActionSuccess())
		{
			
			//3.Get from DB the LConfiguration to update based on modelNumber.
			try
			{
			
				result = Constants.DB_SUCCESS;
			}
			catch(Exception e)
			{
				
				result = Constants.DB_FAILURE;	//DB Update Failed.
				LogUtil.error(e.getMessage());
			
			}
			
		}else{ 
			
			result = Constants.iMVG_FAILURE;			//iMVG returned a failure.
		}
	}else{
		
		result = Constants.NO_RESPONSE_FROM_GATEWAY;				//GateWay did not respond.
	}
	}
catch(Exception e)
{
	new DaoManager().delete(action);
	result = Constants.DB_FAILURE;
	LogUtil.error(e.getMessage());
	LogUtil.info(e.getMessage(), e);	
}
	
return xStream.toXML(result);
	
	
	
}

public String getFunctionIdBasedOnNameAndCategory(String categoryXml,String aNamexml){
	
	XStream xStream=new XStream();
	String category = (String)xStream.fromXML(categoryXml);
	String name = (String)xStream.fromXML(aNamexml);
	DeviceManager deviceManager=new DeviceManager();
	long functionId=deviceManager.getFunctionIdBasedOnNameAndCategory(category,name);
	String valueInXml = xStream.toXML(functionId);
	
	return valueInXml;
}

public String deleteDeviceActions(String actionxml)
{
	
	String result="";
	XStream xStream=new XStream();
	DeviceManager devicemanager = new DeviceManager();
	GatewayManager gatewayManager = new GatewayManager();
	Action action=(Action) xStream.fromXML(actionxml);
	
	
	if(action.getFunctions().getName().equals("SCENARIO")){
		ScenarioManager manager = new ScenarioManager();
		Scenario scenario = manager.getScenarioById(action.getDevice().getId());
		GateWay gateway = gatewayManager.getGateWayById(scenario.getGateWay().getId());
		action.setGateWay(gateway);
	}else{
	Device device = devicemanager.getDevice(action.getDevice().getId());
	GateWay gateway = gatewayManager.getGateWayByDevice(device);
	action.setGateWay(gateway);
	}
	
	long id =(action.getId());
	Action action1 = devicemanager.getDeviceActionByid(id);
	long functionId=action1.getFunctions().getFunctionId();
	
	Functions functions=new Functions();
	functions.setId(functionId);
	action.setFunctions(functions);
	ActionModel actionModel = new ActionModel(null, action);
	
	ImonitorControllerAction actionCreate = new ConfigureDeleteAction();
	actionCreate.executeCommand(actionModel);
	
	boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
	
	if(resultFromImvg)
	{
		
		new DaoManager().delete(action);
		
		result="Success";
		
		return result;
	}
	else 
	{
		
	
		result="failure";
		return result;
	}
}


public String editAction(String actionxml)
{
 XStream xStream=new XStream();
 Action action = (Action)xStream.fromXML(actionxml);

 
 DeviceManager devicemanager = new DeviceManager();
 if (action.getFunctions().getName().equals("SCENARIO")) 
 {

  ScenarioManager scenarioManager=new ScenarioManager();
  Scenario scenario=scenarioManager.getScenarioById(action.getConfigurationId());
  String scenarioName=scenario.getName();
  action.setScenario(scenario);
  action.setConfigurationName(scenarioName);
  action.setDevice(null);
 }
 else
 {

  Device device = devicemanager.getDevice(action.getConfigurationId());
  String deviceFriendlyName=device.getFriendlyName();

  action.setDevice(device);

  action.setScenario(null);

  action.setConfigurationName(deviceFriendlyName);
  
 }

 //new DaoManager().saveOrUpdate(action);
 String result = Constants.FAILURE;
 try
 {

 //Saving into Db
 ActionModel actionModel = new ActionModel(null, action);
 ImonitorControllerAction actionCreate = new ConfigureEditAction();
 actionCreate.executeCommand(actionModel);
 boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
 if(resultFromImvg){
  if(actionCreate.isActionSuccess())
  {
 
   //3.Get from DB the LConfiguration to update based on modelNumber.
   try
   {
  
    new DaoManager().saveOrUpdate(action);
   
    result = Constants.DB_SUCCESS;
   }
   catch(Exception e)
   {
    
    result = Constants.DB_FAILURE; //DB Update Failed.
    LogUtil.error(e.getMessage());

   }
   
  }else{ 
   
   new DaoManager().delete(action);
   result = Constants.iMVG_FAILURE;   //iMVG returned a failure.
  }
 }else{

  result = Constants.NO_RESPONSE_FROM_GATEWAY;    //GateWay did not respond.
 }
 }
catch(Exception e)
{

 LogUtil.error(e.getMessage());

}

return xStream.toXML(result);
}


/*public String editAction(String actionxml)
{
	XStream xStream=new XStream();
	Action action = (Action)xStream.fromXML(actionxml);
	LogUtil.info("Action function id is "+action.getFunctions().getId());
	
	DeviceManager devicemanager = new DeviceManager();
	Device device = devicemanager.getDevice(action.getDevice().getId());
	String deviceId=device.getDeviceId();
	action.getDevice().setDeviceId(deviceId);
	
	//new DaoManager().saveOrUpdate(action);
	String result = Constants.FAILURE;
	try
	{
	LogUtil.info("editAction try block started");
	//Saving into Db
	ActionModel actionModel = new ActionModel(null, action);
	ImonitorControllerAction actionCreate = new ConfigureEditAction();
	actionCreate.executeCommand(actionModel);
	boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
	if(resultFromImvg){
		if(actionCreate.isActionSuccess())
		{
			LogUtil.info("LOG success");	
			//3.Get from DB the LConfiguration to update based on modelNumber.
			try
			{
				LogUtil.info("Db update success");
				new DaoManager().saveOrUpdate(action);
				LogUtil.info("Db update success end");
				result = Constants.DB_SUCCESS;
			}
			catch(Exception e)
			{
				LogUtil.info("Db update failureee");
				result = Constants.DB_FAILURE;	//DB Update Failed.
				LogUtil.error(e.getMessage());
				LogUtil.info(e.getMessage(), e);
			}
			
		}else{ 
			LogUtil.info("editAction step8");
			new DaoManager().delete(action);
			result = Constants.iMVG_FAILURE;			//iMVG returned a failure.
		}
	}else{
		LogUtil.info("editAction step9");
		result = Constants.NO_RESPONSE_FROM_GATEWAY;				//GateWay did not respond.
	}
	}
catch(Exception e)
{
	LogUtil.info("editAction error");
	LogUtil.error(e.getMessage());
	LogUtil.info(e.getMessage(), e);
}
	LogUtil.info("editAction ended");
return xStream.toXML(result);
}*/

public String getDeviceAction(String idxml){
XStream xstream = new XStream();
long id = (Long)xstream.fromXML(idxml);
DeviceManager manager = new DeviceManager();
Action action = manager.getDeviceActionByid(id);

String result = xstream.toXML(action);

return result;



}

public String listAllActions(String customerxml){
	
	XStream xStream=new XStream();
	Customer customer = (Customer)xStream.fromXML(customerxml);
	GatewayManager gatewayManager = new GatewayManager();
	GateWay gateway = gatewayManager.getGateWayByCustomer(customer) ;
	DeviceManager devicemanager = new DeviceManager();
	List<Object[]> actionsObjects = devicemanager.listAllActionsByCustomerId(gateway);
	List<Object[]> scenarioObjects = devicemanager.listAllScenarioActionsByCustomerId(gateway);
	List<Action> actions = new ArrayList<Action>();
	if(actionsObjects != null)
	{
	for (Object[] action:actionsObjects) 
	{
		Action action2=new Action();
		action2.setId(Long.parseLong(IMonitorUtil.convertToString(action[0])));
		action2.setActionName(IMonitorUtil.convertToString(action[1]));
		action2.setParameter(Long.parseLong(IMonitorUtil.convertToString(action[2])));
		/*Device device = (Device) action[3];
		long did=device.getId();
		Device device2 =new Device();
		device2.setId(did);
		action2.setDevice(device2);
		Functions functions= (Functions) action[4];
		long fid = functions.getId();
		Functions functions2=new Functions();
		functions2.setId(fid);
		action2.setFunctions(functions2);*/
		actions.add(action2);
	}
	}
	
	if(scenarioObjects != null)
	{
	for (Object[] action:scenarioObjects) 
	{
		Action action3=new Action();
		action3.setId(Long.parseLong(IMonitorUtil.convertToString(action[0])));
		action3.setActionName(IMonitorUtil.convertToString(action[1]));
		action3.setParameter(Long.parseLong(IMonitorUtil.convertToString(action[2])));
		actions.add(action3);
	}
	}
	
	
	
	
	return xStream.toXML(actions);
}

public String listAllKeyConfiguration(String devicexml)
{
	XStream xStream=new XStream();
	Device device=(Device) xStream.fromXML(devicexml);
	DeviceManager devicemanager = new DeviceManager();
	List<Object[]> keyConfigObjects = devicemanager.getKeyConfigListbyGeneratedId(device) ;
	//List<KeyConfiguration> keyConfigObjects = devicemanager.getKeysByDevice(device) ;
	
	List<KeyConfiguration> keyConfigs = new ArrayList<KeyConfiguration>();
	if(keyConfigs != null)
	{
		for (Object[] keyConfiguration:keyConfigObjects) 
		{
			KeyConfiguration configuration=new KeyConfiguration();
			
			configuration.setId(Long.parseLong(IMonitorUtil.convertToString(keyConfiguration[0])));
			
			Device device2 = new Device();
			device2.setId(Long.parseLong(IMonitorUtil.convertToString(keyConfiguration[1])));
			configuration.setDevice(device2);
			
			configuration.setKeyName(IMonitorUtil.convertToString(keyConfiguration[2]));
			configuration.setPressType(IMonitorUtil.convertToString(keyConfiguration[3]));
			
			if(keyConfiguration[4] != null){
				Action action = devicemanager.getActionByid(Long.parseLong(IMonitorUtil.convertToString(keyConfiguration[4])));
				configuration.setAction(action);
			}else{
				Action action=new Action();
				configuration.setAction(action);
			}
			
			//action.setActionName((IMonitorUtil.convertToString(keyConfiguration[6])));
			
			configuration.setKeyCode(IMonitorUtil.convertToString(keyConfiguration[5]));
			configuration.setPosindex(Long.parseLong(IMonitorUtil.convertToString(keyConfiguration[6])));
			keyConfigs.add(configuration);
		}
	}
	
	return xStream.toXML(keyConfigs);
}

public String sendNetworkStatusRequest(String devicexml)
{
	
	XStream xStream=new XStream();
	Device device=(Device) xStream.fromXML(devicexml);
	String result="";
	try 
	{
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction actionCreate = new NetworkStatusAction();
		actionCreate.executeCommand(actionModel);
		
		boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
		
		DeviceManager deviceManager=new DeviceManager();
		if (resultFromImvg)//Imvg Success
		{
			
			if (actionCreate.isActionSuccess()) // Save to DB success
			{
				
				Device deviceInNS=actionCreate.getActionModel().getDevice();
				String NetworkXml=deviceManager.getNetworkStatsBasedOnDeviceId(deviceInNS.getId());
				NetworkStats networkStats=(NetworkStats) xStream.fromXML(NetworkXml);
				//LogUtil.info("NS : "+xStream.toXML(networkStats));
				String rssi=networkStats.getRssi();
				Set<RssiInfo> rssiInfos=new LinkedHashSet<RssiInfo>();
				if (rssi.contains(";")) 
				{
					
					String[] nodeSplit=rssi.split(";");
					for (int i = 0; i < nodeSplit.length; i++)
					{
						String singleNode=nodeSplit[i];
						String[] singleNodeSplit=singleNode.split(":");
						RssiInfo rssiInfo=new RssiInfo();
						rssiInfo.setColor(singleNodeSplit[3]);
						String deviceId=singleNodeSplit[0];
					//	String deviceName=deviceManager.getFriendlyNameBasedOnDeviceId(deviceId);
						rssiInfo.setDeviceName(deviceId);
						IconManager iconManager=new IconManager();
						Icon icon=iconManager.getIconById(networkStats.getDevice().getIcon().getId());
						rssiInfo.setIcon(icon.getFileName());
						rssiInfo.setRssiValue(singleNodeSplit[2]);
						rssiInfos.add(rssiInfo);
					}
				}
				else
				{
					
					String[] singleNodeSplit=rssi.split(":");
					RssiInfo rssiInfo=new RssiInfo();
					rssiInfo.setColor(singleNodeSplit[3]);
					String deviceId=singleNodeSplit[0];
					//String deviceName=deviceManager.getFriendlyNameBasedOnDeviceId(deviceId);
					rssiInfo.setDeviceName(deviceId);
					IconManager iconManager=new IconManager();
					Icon icon=iconManager.getIconById(networkStats.getDevice().getIcon().getId());
					rssiInfo.setIcon(icon.getFileName());
					rssiInfo.setRssiValue(singleNodeSplit[2]);
					//LogUtil.info("Rssi object : "+xStream.toXML(rssiInfo));
					rssiInfos.add(rssiInfo);
				}
				networkStats.setRssiInfosList(rssiInfos);
				
				result=xStream.toXML(networkStats);
				
			}
			else			// Save Failure
			{
				
				Device deviceInNS=actionCreate.getActionModel().getDevice();
				String NetworkXml=deviceManager.getNetworkStatsBasedOnDeviceId(deviceInNS.getId());
				
				if (NetworkXml!=null)//Imvg failure Device present
				{
					NetworkStats networkStats=(NetworkStats) xStream.fromXML(NetworkXml);
					//LogUtil.info("NS : "+xStream.toXML(networkStats));
					String rssi=networkStats.getRssi();
					Set<RssiInfo> rssiInfos=new LinkedHashSet<RssiInfo>();
					if (rssi.contains(";")) 
					{
						
						String[] nodeSplit=rssi.split(";");
						for (int i = 0; i < nodeSplit.length; i++)
						{
							String singleNode=nodeSplit[i];
							String[] singleNodeSplit=singleNode.split(":");
							RssiInfo rssiInfo=new RssiInfo();
							rssiInfo.setColor(singleNodeSplit[3]);
							String deviceId=singleNodeSplit[0];
							//String deviceName=deviceManager.getFriendlyNameBasedOnDeviceId(deviceId);
							rssiInfo.setDeviceName(deviceId);
							IconManager iconManager=new IconManager();
							Icon icon=iconManager.getIconById(networkStats.getDevice().getIcon().getId());
							rssiInfo.setIcon(icon.getFileName());
							rssiInfo.setRssiValue(singleNodeSplit[2]);
							//LogUtil.info("Rssi object : "+xStream.toXML(rssiInfo));
							rssiInfos.add(rssiInfo);
						}
					}
					else
					{
						
						String[] singleNodeSplit=rssi.split(":");
						RssiInfo rssiInfo=new RssiInfo();
						rssiInfo.setColor(singleNodeSplit[3]);
						String deviceId=singleNodeSplit[0];
						//String deviceName=deviceManager.getFriendlyNameBasedOnDeviceId(deviceId);
						rssiInfo.setDeviceName(deviceId);
						IconManager iconManager=new IconManager();
						Icon icon=iconManager.getIconById(networkStats.getDevice().getIcon().getId());
						rssiInfo.setIcon(icon.getFileName());
						rssiInfo.setRssiValue(singleNodeSplit[2]);
						//LogUtil.info("Rssi object : "+xStream.toXML(rssiInfo));
						rssiInfos.add(rssiInfo);
					}
					networkStats.setRssiInfosList(rssiInfos);
				
					result=xStream.toXML(networkStats);
				}
				else//Imvg Failure No deviceInDb
				{
					
				  /*String failure=Constants.DB_FAILURE;
					 result=xStream.toXML(failure);*/
					result=xStream.toXML(null);
				}
			}
			
		}
		else // Imvg Failure
		{
			
			/*String failure=Constants.iMVG_FAILURE;
			result=xStream.toXML(failure);*/
			result=xStream.toXML(null);
		}
	} catch (Exception e) 
	{
		LogUtil.error(e.getMessage());
		LogUtil.info(e.getMessage(), e);
		/*String failure=Constants.iMVG_FAILURE;*/
		result=xStream.toXML(null);
	}
	
	return result;
}


public String getDeviceByGeneratedDeviceId(String deviceIdxml)
{
	
	
	XStream stream =new XStream();
	Long did=(Long) stream.fromXML(deviceIdxml);
	
	long DeviceId=did.longValue();
	DeviceManager deviceManager=new DeviceManager();
	Device device=deviceManager.getDeviceById(DeviceId);
	long deviceTypeId=device.getDeviceType().getId();
	DeviceTypeManager deviceTypeManager=new DeviceTypeManager();
	DeviceType deviceType=deviceTypeManager.getDeviceTypeById(deviceTypeId);
	
	
	device.setDeviceType(deviceType);
	return stream.toXML(device);
}

public String checkAlertDeviceOfGateway(String xmlString){
	
	XStream xStream = new XStream();
	String generatedDevcId = (String) xStream.fromXML(xmlString);
	DeviceManager deviceManager = new DeviceManager();
	Device device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDevcId);
	boolean res = deviceManager.checkAlertingDeviceOfGateway(device);
	
	return xStream.toXML(res);
}

//Diaken VIA
public String toDiscoverSlaveUnit(String devicexml,String SlaveIdXml,String SlaveModel)
{
	XStream xStream=new XStream();
	Device device1=(Device) xStream.fromXML(devicexml);
	String SlaveId1=(String) xStream.fromXML(SlaveIdXml);
	String SlaveMod=(String) xStream.fromXML(SlaveModel);

	String result="NoResponse";
	try
	{
		ActionModel actionModel = new ActionModel(device1, SlaveId1,SlaveMod);
		ImonitorControllerAction actionCreate = new DiscoverSlaveUnitAction();
		actionCreate.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
		
		if(resultFromImvg)
		{
			Queue<KeyValuePair> queue = actionCreate.getActionModel().getQueue();
			
		
			String macId = "";
			String DeviceTypeName=null;
			
			ModbusSlave modbusSlave=null;
			DeviceManager deviceManager=null;
			
			try
			{
				deviceManager=new DeviceManager();
				modbusSlave=new ModbusSlave();
				//Queue<KeyValuePair> queue = IMonitorUtil
					//	.extractCommandsQueueFromXml(xml);
				macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
				GatewayManager gatewayManager = new GatewayManager();
				
				String generateddeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
				String SlaveId= IMonitorUtil.commandId(queue, Constants.SLAVEID);
				long sid=Long.parseLong(SlaveId);
				String HAIF_ID=generateddeviceId+"-1"+SlaveId;
			
				//DeviceType from Db
				DeviceTypeName="HA_IF";
				DeviceTypeManager deviceTypeManager = new DeviceTypeManager();
				DeviceType deviceType = deviceTypeManager.getDeviceTypeByName(DeviceTypeName);
				//LogUtil.info("Device Type --"+stream.toXML(deviceType));
				//Icon from DB
				IconManager iconManager = new IconManager();
				Icon defaultIcon = iconManager.getDefaultIcon(deviceType.getId());
				//LogUtil.info("Icon --"+stream.toXML(defaultIcon));
				//Gateway from Db
				GateWay gateWay1 = gatewayManager.getGateWayWithAgentByMacId(macId);
				//LogUtil.info("Gateway --"+stream.toXML(gateWay1));
				
				
				//Extract device id from deviceId and setId and set that device
				String devIdfromString=generateddeviceId.substring(18,22);
				
				Device device=deviceManager.getDevice(generateddeviceId);
				
				
				
				device.setId(device.getId());
				modbusSlave.setDeviceId(device);
				modbusSlave.setHAIF_Id(HAIF_ID);
				modbusSlave.setFriendlyName("Slave-"+devIdfromString+"-"+SlaveId);
				

				
				CustomerManager customerManager=new CustomerManager();
				Customer customer=customerManager.getCustomerByMacId(macId);
				//LogUtil.info("Customer --"+stream.toXML(customer));
				LocationManager locationManager=new LocationManager();
				long locationid=locationManager.getLocationIdByName(Constants.DEFAULT_ROOM, customer.getCustomerId());
				Location location=locationManager.getLocationById(locationid);
				//LogUtil.info("Location  --"+stream.toXML(location));
				modbusSlave.setLocation(location);
				modbusSlave.setDeviceType(deviceType);
				modbusSlave.setIcon(defaultIcon);
				modbusSlave.setGateWay(gateWay1);
				modbusSlave.setEnableList("0");
				modbusSlave.setEnableStatus(0);
				modbusSlave.setSlaveId(sid);
				
				
				String posIdx = deviceManager.getMaxPositionIndexBasedOnLocation(Constants.DEFAULT_ROOM);
				long positionIndex = Long.parseLong(posIdx);
				modbusSlave.setPosIdx(++positionIndex);
				//LogUtil.info("ModBus device --"+stream.toXML(modbusSlave));
				DaoManager daoManager=new DaoManager();
				daoManager.save(modbusSlave);
				
				
			} 
			catch (Exception e) {
				LogUtil.error("General Error in saving device and message is : "
						+ e.getMessage());
				LogUtil.info("", e);
			} 
			
			  if(actionCreate.isActionSuccess())
			  {
			  
			   //3.Get from DB the LConfiguration to update based on modelNumber.
			   try
			   { 
			    result = Constants.DB_SUCCESS;
			   }
			   catch(Exception e)
			   {
			    
			    result = Constants.DB_FAILURE; //DB Update Failed.
			    LogUtil.error(e.getMessage());
			    LogUtil.info(e.getMessage(), e);
			   }
			   
			  }else{ 
			   result = Constants.iMVG_FAILURE;   //iMVG returned a failure.
			  }
			 }else{
			  result = Constants.NO_RESPONSE_FROM_GATEWAY;    //GateWay did not respond.
			 }
	} 
	
	catch (Exception e)
	{
		LogUtil.error(e.getMessage());
		LogUtil.info(e.getMessage(), e);
	}
	
	return xStream.toXML(result);
}

//Diaken VIA for Indoor Unit
public String DiscoverIndoorUnit(String devicexml)
{
	XStream xStream=new XStream();
	Device device1=(Device) xStream.fromXML(devicexml);
	String result="";
	try
	{

ActionModel actionModel = new ActionModel(device1, null);
		ImonitorControllerAction actionCreate = new DiscoverIndoorUnitAction();
		actionCreate.executeCommand(actionModel);
		DaoManager daoManager=new DaoManager();
		DeviceConfigurationManager deviceConfigurationManager = new DeviceConfigurationManager();
		GatewayManager gatewayManager = new GatewayManager();
		boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
		
		if (resultFromImvg)//Imvg Success
		{
			Queue<KeyValuePair> queue = actionCreate.getActionModel().getQueue();
			
			String macId = "";
			String DeviceTypeName=null;
			XStream stream=new XStream();
			try
			{
				macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
				String HAIF_Id = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
				String VRV1 = IMonitorUtil.commandId(queue, Constants.VRV1);
				String VRV2 = IMonitorUtil.commandId(queue, Constants.VRV2);
				String VRV3 = IMonitorUtil.commandId(queue, Constants.VRV3);
				String VRV4 = IMonitorUtil.commandId(queue, Constants.VRV4);
				
				//LogUtil.info("VRV 1 is --->"+VRV1);
				
				//Modbus Slave from DB
				
				DeviceManager deviceManager=new DeviceManager();
				
				
				//LogUtil.info("Slave Device From Db -->"+stream.toXML(modbusSlave));
				ArrayList<String> arrayList=new ArrayList<String>();
				arrayList.add(VRV1);
				arrayList.add(VRV2);
				arrayList.add(VRV3);
				arrayList.add(VRV4);
				int vrvNumber=1;
				for (String IDU : arrayList)
				{
					
					String[] Idus=IDU.split(";");
					
					
				//String[] Idus=VRV1.split(";");
					Device iduDevice = null;
				for (int i = 0; i < Idus.length; i++) 
				{
					//IndoorUnit indoorUnit=new IndoorUnit();
					Device device=new Device();
					String IndivialIDU=Idus[i];
					String[] splitIDU=IndivialIDU.split(":");
					String iduID=splitIDU[0];
					String ConnectionStatus=splitIDU[1];
					//String CommStatus=splitIDU[2];
					String vrvNumbString=Integer.toString(vrvNumber);
					String UnitId=HAIF_Id+"-"+vrvNumbString+":"+iduID;
					
					iduDevice = deviceManager.getDeviceByGeneratedDeviceId(UnitId);
					
					if(iduDevice != null)
					{
						//Update existing device
						
						long connStatus=Long.valueOf(ConnectionStatus);
						DeviceConfiguration devconfiguration=deviceConfigurationManager.getDeviceConfigurationById(iduDevice.getDeviceConfiguration().getId());
						IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) devconfiguration;
						configuration.setConnectStatus(connStatus);
						boolean afterSave=deviceConfigurationManager.updateIduConfiguration(configuration);
						iduDevice.setDeviceConfiguration(configuration);
						//boolean afterSave=daoManager.update(iduDevice);
						
						
						
					}else{
						
						/*long connStatus=Long.valueOf(ConnectionStatus);
						if(connStatus == 0){
							continue;
						}
						else{
							
						}*/
						
						//Add new Device
						
						String deviceId = UnitId.substring(18,UnitId.length());
						device.setDeviceId(deviceId);
						device.setGeneratedDeviceId(UnitId);
						//indoorUnit.setFriendlyName(UnitId);
						device.setFriendlyName(UnitId);
						long connStatus=Long.valueOf(ConnectionStatus);
							//LogUtil.info("Connection status before save "+connStatus);
							IndoorUnitConfiguration configuration=new IndoorUnitConfiguration();
							//Saving Device Configuration
							configuration.setConnectStatus(connStatus);
							configuration.setTemperatureValue(16);
							deviceConfigurationManager.saveDeviceConfiguration(configuration);
							device.setDeviceConfiguration(configuration);
							
							//Customer and Location
							CustomerManager customerManager=new CustomerManager();
							Customer customer=customerManager.getCustomerByMacId(macId);
							LocationManager locationManager=new LocationManager();
							long locationid=locationManager.getLocationIdByName(Constants.DEFAULT_ROOM, customer.getCustomerId());
							Location location=locationManager.getLocationById(locationid);
							//indoorUnit.setLocation(location);
							device.setLocation(location);
							//DeviceType
							DeviceTypeName="VIA_UNIT";
							DeviceTypeManager deviceTypeManager = new DeviceTypeManager();
							DeviceType deviceType = deviceTypeManager.getDeviceTypeByName(DeviceTypeName);
							//indoorUnit.setDeviceType(deviceType);
							device.setDeviceType(deviceType);
							//Gateway
							GateWay gateWay = gatewayManager.getGateWayByMacId(macId);
							//indoorUnit.setGateWay(gateWay);
							device.setGateWay(gateWay);
							//Icon
							IconManager iconManager = new IconManager();
							Icon defaultIcon = iconManager.getDefaultIcon(deviceType.getId());
							//indoorUnit.setIcon(defaultIcon);
							device.setIcon(defaultIcon);
							//indoorUnit.setEnableStatus(0);
							device.setEnableStatus("0");
							//indoorUnit.setEnableList("1");
							device.setEnableList("1");
							
							AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_UP);
							device.setLastAlert(alertType);
							
							String posIdx = deviceManager.getMaxPositionIndexBasedOnLocation(Constants.DEFAULT_ROOM);
							long positionIndex = Long.parseLong(posIdx);
							//indoorUnit.setPosIdx(++positionIndex);
							device.setPosIdx(++positionIndex);
							//boolean afterSave=daoManager.save(indoorUnit);
							boolean afterSave=daoManager.save(device);
							
						
						
						
						
					}
					
					
								
				}
				
				vrvNumber=vrvNumber+1;
					
				}
				
			}
			catch (Exception e) {
				LogUtil.error("General Error in saving device and message is : "
						+ e.getMessage());
				LogUtil.info("", e);
				result = Constants.DB_FAILURE; //Added by apoorva 17 Sep
			} 
			
			
			if(actionCreate.isActionSuccess())
			  {
			 
			   //3.Get from DB the LConfiguration to update based on modelNumber.
			   try
			   { 
			    result = Constants.DB_SUCCESS;
			   }
			   catch(Exception e)
			   {
			    
			    result = Constants.DB_FAILURE; //DB Update Failed.
			    LogUtil.error(e.getMessage());
			  
			   }
			   
			  }
			 else{
			  result = Constants.NO_RESPONSE_FROM_GATEWAY;    //GateWay did not respond.
			 }
		}
	} 
	
	catch (Exception e)
	{
		LogUtil.error(e.getMessage());
		LogUtil.info(e.getMessage(), e);
	}
	
	
	return xStream.toXML(result);
}

//Diaken VIA for Modbus slave device
public String toUpdateFriendlyNameAndLocationForSlave(String xml) {
	XStream xStream = new XStream();
	String generatedDeviceId = (String) xStream.fromXML(xml);
	DeviceManager deviceManager = new DeviceManager();
	Device device = deviceManager
			.getDeviceWithGateWayAndAgentByGeneratedDeviceIdForSlave(generatedDeviceId);
	return xStream.toXML(device);
}


//Diaken VIA for Modbus slave device
public String updateFriendlyNameAndLocationForSlave(String xml, String oldLocIdXml) {
	XStream xStream = new XStream();
	Device deviceFromWeb = (Device) xStream.fromXML(xml);
	String oldLocationId = (String) xStream.fromXML(oldLocIdXml);
	//LogUtil.info("Device xml "+xStream.toXML(deviceFromWeb));
	GatewayManager gatewayManager=new GatewayManager();
	long gatewayid=deviceFromWeb.getGateWay().getId();
	GateWay gateWay=gatewayManager.getGateWayWithAgentById(gatewayid);
	deviceFromWeb.setGateWay(gateWay);
	String result = "";
	long id = deviceFromWeb.getId();
	long locationId = deviceFromWeb.getLocation().getId();
	long iconId = deviceFromWeb.getIcon().getId();
	long oldLocId = Long.parseLong(oldLocationId);
	long posIdxMax = 0L;
	String friendlyName = deviceFromWeb.getFriendlyName();
	String locationName = "";
	String maxPosIdx = "";
	String enableList = deviceFromWeb.getEnableList();
	DeviceManager deviceManager = new DeviceManager();
	ModbusSlave MSdevice = deviceManager.getModbusSlaveDevice(id);
	//String deviceFromDBXml = xStream.toXML(MSdevice);
	//String generatedDeviceId = MSdevice.getHAIF_Id();
	//Device devicewithtype = deviceManager.getDeviceWithConfigurationAndMake(generatedDeviceId);
	
	LocationManager locationManager = new LocationManager();
	Location location = locationManager.getLocationById(locationId);
	locationName = location.getName();
			
	LocationMap locationMap = null;
	try {
		locationMap = MSdevice.getLocationMap();
	} catch (Exception e) {
		LogUtil.info("Location Map", e);
	}	
	//update the position index of the device as the last device for the location.
	if(oldLocId != locationId)
	{	
		//parishod added try catch block
		try{				
			if(locationMap != null)
			resetDevicePosition(xStream.toXML(MSdevice));
			}catch (Exception e) {
					// TODO: handle exception
			}
		
		locationMap = null;
		maxPosIdx = deviceManager.getMaxPositionIndexBasedOnLocation(locationName);
		if(maxPosIdx != null){
			posIdxMax = Long.parseLong(maxPosIdx);
			posIdxMax +=1;
		}
	}else{
		posIdxMax = MSdevice.getPosIdx();
		LogUtil.info("NO CHANGE IN LOCATION");
	}
	IconManager iconManager = new IconManager();
	Icon icon = iconManager.getIconById(iconId);
	ActionModel actionModel = new ActionModel(deviceFromWeb,null);
	ImonitorControllerAction UpdateFriendlyName=new UpdateFriendlyNameForSlave();
	UpdateFriendlyName.executeCommand(actionModel);
	boolean resultFromImvg = IMonitorUtil.waitForResult(UpdateFriendlyName);
	if(resultFromImvg)
	{
		if(UpdateFriendlyName.isActionSuccess())
		{
			MSdevice.setFriendlyName(friendlyName);
			MSdevice.setLocation(location);
			MSdevice.setIcon(icon);
			MSdevice.setEnableList(enableList);
			MSdevice.setPosIdx(posIdxMax);
			MSdevice.setLocationMap(locationMap);
			
			//device.setPulseTimeOut(deviceFromWeb.getPulseTimeOut());
			//Naveen made changes for enable/disable hmd device
			/*if(typeName.equals(Constants.IP_CAMERA)){
		    	device.setEnableStatus(devicewithtype.getEnableStatus());
		    }else{
		    	device.setEnableStatus(deviceFromWeb.getEnableStatus());
		    }
		    if(typeName.equals("Z_WAVE_SWITCH") || typeName == "Z_WAVE_SWITCH"){
			device.setModelNumber(deviceFromWeb.getModelNumber());
		    }*/
			// end
			boolean dbUpdate = deviceManager.updateModbusSlaveDevice(MSdevice);
			
			 
			
			
			//End
			if(dbUpdate){
				result = Constants.DB_SUCCESS;//xStream.toXML(device);
			}else{
				result = Constants.DB_FAILURE;		//DB update failure.
			}
		}else{
			result = Constants.iMVG_FAILURE;		//iMVG returned failure.
		}
	}else{
		result = Constants.NO_RESPONSE_FROM_GATEWAY; //Gateway did not respond.
	}
	return xStream.toXML(result);
}


//Diaken VIA for Indorr Unit
public String toUpdateFriendlyNameAndLocationForIndoorUnit(String xml)
{
	XStream xStream = new XStream();
	String generatedDeviceId = (String) xStream.fromXML(xml);
	DeviceManager deviceManager = new DeviceManager();
	/*Device device = deviceManager
			.getDeviceWithGateWayAndAgentByGeneratedDeviceIdForIndoorUnit(generatedDeviceId);*/
	Device device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceId);
	return xStream.toXML(device);
}




	//Diaken VIA
	public String removeVIADevice(String devicexml) 
	{
		XStream xStream=new XStream();
		DeviceManager deviceManager = new DeviceManager();
		Device device=(Device) xStream.fromXML(devicexml);
		String result="";
		try
		{
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction actionCreate = new RemoveVIAAction();
			actionCreate.executeCommand(actionModel);
			boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
			
			if(resultFromImvg)
			{
				device = deviceManager.getDevice(device.getId());
				new DaoManager().delete(device);
				result="Success";
				return result;
			}
			else 
			{
				result="failure";
				return result;
			}
		} 
		
		catch (Exception e)
		{
			LogUtil.error(e.getMessage());
			LogUtil.info(e.getMessage(), e);
		}
		
		return null;
	}
	
	
	//Diaken VIA
		public String removeSlaveDevice(String devicexml) 
		{
			XStream xStream=new XStream();
			DeviceManager deviceManager = new DeviceManager();
			Device device=(Device) xStream.fromXML(devicexml);
			String result="";
			try
			{
				ActionModel actionModel = new ActionModel(device, null);
				ImonitorControllerAction actionCreate = new RemoveSlaveAction();
				actionCreate.executeCommand(actionModel);
				boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
				
				if(resultFromImvg)
				{
					ModbusSlave modbusSlave=deviceManager.getModbusSlaveDevice(device.getId());
					new DaoManager().delete(modbusSlave);
					result="Success";
					return result;
				}
				else 
				{
					result="failure";
					return result;
				}
			} 
			
			catch (Exception e)
			{
				LogUtil.error(e.getMessage());
				LogUtil.info(e.getMessage(), e);
			}
			
			return result;
		}
		
		
		//removeIndoorUnit
		public String removeIndoorUnit(String devicexml) 
		{
			DeviceManager deviceManager = new DeviceManager();
			XStream xStream=new XStream();
			Device device=(Device) xStream.fromXML(devicexml);
			String result="";
			try
			{
				ActionModel actionModel = new ActionModel(device, null);
				ImonitorControllerAction actionCreate = new RemoveIndoorUnitAction();
				actionCreate.executeCommand(actionModel);
				boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
				
				if(resultFromImvg)
				{
					device = deviceManager.getDevice(device.getId());
					new DaoManager().delete(device);
					result="Success";
				}
				else 
				{
					result="failure";
				}
			} 
			
			catch (Exception e)
			{
				LogUtil.error(e.getMessage());
				LogUtil.info(e.getMessage(), e);
			}
			
			return result;
		}
		
		//Diaken VIA
		public String getCapability(String devicexml)
		{
			XStream xStream=new XStream();
			Device device1=(Device) xStream.fromXML(devicexml);
			String result="";
			try
			{
				ActionModel actionModel = new ActionModel(device1, null);
				ImonitorControllerAction actionCreate = new GetIndoorUnitCapabilityAction();
				actionCreate.executeCommand(actionModel);
				boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
				
				if (resultFromImvg)//Imvg Success
				{
					Queue<KeyValuePair> queue = actionCreate.getActionModel().getQueue();
					
					String macId = "";
					
					try
					{

						macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
						String UnitId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
						String fanModeCapability = IMonitorUtil.commandId(queue, Constants.FANMODE_CAPABILITY);
						String coolModeCapability = IMonitorUtil.commandId(queue, Constants.COOLMODE_CAPABILITY);
						String heatModeCapability = IMonitorUtil.commandId(queue, Constants.HEATMODE_CAPABILITY);
						String autoModeCapability = IMonitorUtil.commandId(queue, Constants.AUTOMODE_CAPABILITY);
						String dryModeCapability = IMonitorUtil.commandId(queue, Constants.DRYMODE_CAPABILITY);
						String fanDirectionLevelCapability = IMonitorUtil.commandId(queue, Constants.FANDIRECTION_LEVEL_CAPABILITY);
						String fanDirectionCapability = IMonitorUtil.commandId(queue, Constants.FANDIRECTION_CAPABILITY);
						String fanVolumeLevelCapability = IMonitorUtil.commandId(queue, Constants.FANVOLUME_LEVEL_CAPABILITY);
						String fanVolumeCapability = IMonitorUtil.commandId(queue, Constants.FANVOLUME_CAPABILITY);
						String coolingUpperlimit = IMonitorUtil.commandId(queue, Constants.COOLING_UPPERLIMIT);
						String coolingLowerlimit = IMonitorUtil.commandId(queue, Constants.COOLING_LOWERLIMIT);
						String heatingUpperlimit = IMonitorUtil.commandId(queue, Constants.HEATING_UPPERLIMIT);
						String heatingLowerlimit = IMonitorUtil.commandId(queue, Constants.HEATING_LOWERLIMIT);
						
						DeviceManager deviceManager=new DeviceManager();
						Device device=deviceManager.getDevice(UnitId);
						
						
						DeviceConfigurationManager deviceConfigurationManager = new DeviceConfigurationManager();
						DeviceConfiguration devconfiguration=deviceConfigurationManager.getDeviceConfigurationById(device.getDeviceConfiguration().getId());
						IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) devconfiguration;
						configuration.setFanModeCapability(fanModeCapability);
						configuration.setCoolModeCapability(coolModeCapability);
						configuration.setHeatModeCapability(heatModeCapability);
						configuration.setAutoModeCapability(autoModeCapability);
						configuration.setDryModeCapability(dryModeCapability);
						configuration.setFanDirectionLevelCapability(fanDirectionLevelCapability);
						configuration.setFanDirectionCapability(fanDirectionCapability);
						configuration.setFanVolumeLevelCapability(fanVolumeLevelCapability);
						configuration.setFanVolumeCapability(fanVolumeCapability);
						configuration.setCoolingUpperlimit(coolingUpperlimit);
						configuration.setCoolingLowerlimit(coolingLowerlimit);
						configuration.setHeatingUpperlimit(heatingUpperlimit);
						configuration.setHeatingLowerlimit(heatingLowerlimit);
						configuration.setFanModeValue("1");
						//configuration.setFanDirectionValue("2");
						//configuration.setFanVolumeValue("1");
						deviceConfigurationManager.updateIduConfiguration(configuration);
						device.setDeviceConfiguration(configuration);
						DaoManager daoManager=new DaoManager();
						boolean result1=daoManager.update(device);
						
						
					} 
					catch (Exception e)
					{
						// TODO: handle exception
					}
					
					if(actionCreate.isActionSuccess())
					  {
					  
					    result = Constants.DB_SUCCESS;
					  }
					 else{
					  result = Constants.NO_RESPONSE_FROM_GATEWAY;    //GateWay did not respond.
					 }
				}
			} 
			
			catch (Exception e)
			{
				LogUtil.error(e.getMessage());
				LogUtil.info(e.getMessage(), e);
			}
			
			return result;
		}
		
		//CheckSlaveInDB
		public String CheckSlaveInDB(String devicexml,String SlaveIdXml)
		{
			String result="False";
			XStream xStream=new XStream();
			Device device=(Device) xStream.fromXML(devicexml);
			String SlaveId=(String) xStream.fromXML(SlaveIdXml);
			DeviceManager deviceManager=new DeviceManager();
			List<ModbusSlave> modbusSlaves=deviceManager.listOfSlavesFromDb(device);
			//LogUtil.info("modbusSlave list in from Db for comparing slaveId"+xStream.toXML(modbusSlaves));
			if (modbusSlaves!=null) 
			{
				for (ModbusSlave slave : modbusSlaves) 
				{
					long slaveIdFromDb=slave.getSlaveId();
					String did=Long.toString(slaveIdFromDb);
					if (did.equals(SlaveId)) 
					{
						
						result="True";
					}
				}
			}
			return result;
		}
		
public String getSlaveCountForVia(String devicexml){
			String result = "NO_SLAVE_EXISTS";
			XStream xStream = new XStream();
			Device device = (Device) xStream.fromXML(devicexml);
			ModbusDeviceManager modbusManager = new ModbusDeviceManager();
			long count = modbusManager.getSlaveCountByViaDevice(device);
			if(count > 0){
				result = "SLAVE_EXISTS";
			}
			return result;
		}


public String getIndoorUnitsCountForSlave(String devicexml)
{
	String result = "NO_INDOOR_UNITS";
	XStream xStream = new XStream();
	Device device = (Device) xStream.fromXML(devicexml);
	GatewayManager gatewayManager=new GatewayManager();
	GateWay gateWay=gatewayManager.getGateWayByMacId(device.getGateWay().getMacId());
	device.setGateWay(gateWay);
	ViaSlaveDeviceManager slaveDeviceManager=new ViaSlaveDeviceManager();
	long count = slaveDeviceManager.getIDUCountBySlaveDevice(device);
	if(count > 0){
		result = "INDOOR_UNITS_EXISTS";
	}
	return result;
}

public String CountOfSlavesForVIA(String devicexml)
{
	int count=0;
	XStream xStream = new XStream();
	Device device = (Device) xStream.fromXML(devicexml);
	GatewayManager gatewayManager=new GatewayManager();
	GateWay gateWay=gatewayManager.getGateWayByMacId(device.getGateWay().getMacId());
	device.setGateWay(gateWay);
	
	ModbusDeviceManager modbusManager = new ModbusDeviceManager();
	count = modbusManager.getSlaveCountByViaDevice(device);
	return xStream.toXML(count);
}

public String resetFilterStatus(String devicexml)
{
	XStream xStream=new XStream();
	Device device=(Device) xStream.fromXML(devicexml);
	String result="";
	try
	{
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction actionCreate = new ResetFilterAction();
		actionCreate.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
		
		if (resultFromImvg)//Imvg Success
		{
			Queue<KeyValuePair> queue = actionCreate.getActionModel().getQueue();
			XStream stream=new XStream();
			
			String DeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
			DeviceManager deviceManager=new DeviceManager();
			DeviceConfigurationManager deviceConfigurationManager = new DeviceConfigurationManager();
			IndoorUnitConfiguration configuration=null;
			Device device1=deviceManager.getDevice(DeviceId);
			if(actionCreate.isActionSuccess())
			  {
				DeviceConfiguration devconfiguration=deviceConfigurationManager.getDeviceConfigurationById(device1.getDeviceConfiguration().getId());
				 configuration=(IndoorUnitConfiguration) devconfiguration;
				configuration.setFilterSignStatus(0);
			   result = "UpdateSuccess";
			  }
			 else{
					DeviceConfiguration devconfiguration=deviceConfigurationManager.getDeviceConfigurationById(device1.getDeviceConfiguration().getId());
					 configuration=(IndoorUnitConfiguration) devconfiguration;
					configuration.setFilterSignStatus(1); 
			  result = "UpdateFailure";    //GateWay did not respond.
			 }
			boolean aftersave=deviceConfigurationManager.updateIduConfiguration(configuration);
			
		}
		else
		{
			result = Constants.NO_RESPONSE_FROM_GATEWAY;
		}
	} 
	
	catch (Exception e)
	{
		LogUtil.error(e.getMessage());
		LogUtil.info(e.getMessage(), e);
	}
	
	return result;
}
//3 Gateways proj
public String getGateWayId(String macId){
	
	XStream xStream = new XStream();
	GatewayManager gatewayManager=new GatewayManager();
	GateWay gateWay=gatewayManager.getGateWayByMacId(macId);
	long res = gateWay.getId();
	
	return xStream.toXML(gateWay);
}


//For wallmote configuration
//Added by Naveen
public String listWallmoteConfiguration(String devicexml)
{

	XStream xStream=new XStream();
	Device device=(Device) xStream.fromXML(devicexml);
	DeviceManager devicemanager = new DeviceManager();
	
	List<Object[]> keyConfigObjects = devicemanager.getWallmoteConfigbyGeneratedId(device) ;
   
	List<WallmoteConfiguration> keyConfigs = new ArrayList<WallmoteConfiguration>();
	if(keyConfigObjects != null)
	{
		for (Object[] keyConfiguration:keyConfigObjects) 
		{
			WallmoteConfiguration configuration=new WallmoteConfiguration();
			configuration.setId(Long.parseLong(IMonitorUtil.convertToString(keyConfiguration[0])));
			Device device2 = new Device();
			device2.setId(Long.parseLong(IMonitorUtil.convertToString(keyConfiguration[1])));
			configuration.setDevice(device2);
			configuration.setKeyName(IMonitorUtil.convertToString(keyConfiguration[2]));
			if(keyConfiguration[3] != null){
				Action action = devicemanager.getActionByid(Long.parseLong(IMonitorUtil.convertToString(keyConfiguration[3])));
				configuration.setAction(action);
			}else{
				Action action=new Action();
				configuration.setAction(action);
			}
			configuration.setKeyCode(IMonitorUtil.convertToString(keyConfiguration[4]));
			configuration.setPosindex(Long.parseLong(IMonitorUtil.convertToString(keyConfiguration[5])));
			keyConfigs.add(configuration);
		}
	}
	
	return xStream.toXML(keyConfigs);
}
@SuppressWarnings("unchecked")
public String saveWallMoteConfiguration(String deviceXml,String keyconfigObjectsXml){
	
	
	String result = Constants.FAILURE;
	XStream stream = new XStream();
	try
	{
		Device device1=(Device) stream.fromXML(deviceXml);
	   	DeviceManager deviceManager=new DeviceManager();
		Device device=deviceManager.getDevice(device1.getId());
		
		ArrayList<WallmoteConfiguration> keyconfigObjects=(ArrayList<WallmoteConfiguration>) stream.fromXML(keyconfigObjectsXml);
		
		ActionModel actionModel = new ActionModel(device,keyconfigObjects);
		ImonitorControllerAction WallmoteAction = new WallmoteDeviceAction();
		WallmoteAction.executeCommand(actionModel);
	
		boolean resultFromImvg = IMonitorUtil.waitForResult(WallmoteAction);
		
		if(resultFromImvg){
			if(WallmoteAction.isActionSuccess())
			{
				
				try
				{
					for (int i = 0; i < keyconfigObjects.size(); i++)
					{
						//LogUtil.info(stream.toXML("Object index"+i));
						
						WallmoteConfiguration configuration=keyconfigObjects.get(i);		
						if(configuration.getAction().getId() == 0){
							configuration.setAction(null);
						}
						
						//KeyConfiguration configuration=keyconfigObjects.get(i);
						new DaoManager().update(configuration);
						result = Constants.DB_SUCCESS;
					}
					
					
				}
				catch(Exception e)
				{
					result = Constants.DB_FAILURE;	//DB Update Failed.
					LogUtil.error(e.getMessage());
					LogUtil.info(e.getMessage(), e);
				}
				
			}else{ 
				result = Constants.iMVG_FAILURE;			//iMVG returned a failure.
			}
		}else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY;				//GateWay did not respond.
		}
	}
	catch(Exception e)
	{
		LogUtil.error(e.getMessage());
		LogUtil.info(e.getMessage(), e);
	}
	return stream.toXML(result);
}

public String listWallmoteActions(String customerxml){
	
	XStream xStream=new XStream();
	Customer customer = (Customer)xStream.fromXML(customerxml);
	GatewayManager gatewayManager = new GatewayManager();
	GateWay gateway = gatewayManager.getGateWayByCustomer(customer) ;
	DeviceManager devicemanager = new DeviceManager();
	List<Object[]> actionsObjects = devicemanager.listAllActionsByCustomerIdForWallmote(gateway);
	List<Object[]> scenarioObjects = devicemanager.listAllScenarioActionsByCustomerId(gateway);
	List<Action> actions = new ArrayList<Action>();
	LogUtil.info("actions: " + xStream.toXML(actionsObjects));
	if(actionsObjects != null)
	{
	for (Object[] action:actionsObjects) 
	{
		Action action2=new Action();
		action2.setId(Long.parseLong(IMonitorUtil.convertToString(action[0])));
		action2.setActionName(IMonitorUtil.convertToString(action[1]));
		action2.setParameter(Long.parseLong(IMonitorUtil.convertToString(action[2])));
		actions.add(action2);
	}
	}
	
	if(scenarioObjects != null)
	{
	for (Object[] action:scenarioObjects) 
	{
		Action action3=new Action();
		action3.setId(Long.parseLong(IMonitorUtil.convertToString(action[0])));
		action3.setActionName(IMonitorUtil.convertToString(action[1]));
		action3.setParameter(Long.parseLong(IMonitorUtil.convertToString(action[2])));
		actions.add(action3);
	}
	}
	
	
	
	
	return xStream.toXML(actions);
}

//RGB
public String sendRGBValue(String xmlDevice,String hexXml){
	LogUtil.info("sendRGBValue start");
	XStream stream = new XStream();
	String result = "";
	
	Device deviceFromWeb = (Device) stream.fromXML(xmlDevice);
	String hexValue = (String) stream.fromXML(hexXml);
	
	
	//Device device1=deviceManager.getDevice(DeviceId);
	//deviceFromWeb.setPosIdx(scenarioId);
	DeviceManager deviceManager = new DeviceManager();
	GatewayManager gatewayManager = new GatewayManager();
	GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(deviceFromWeb.getGateWay().getMacId());
	deviceFromWeb.setGateWay(gateWay);
	Device device = deviceManager.getDeviceByGeneratedDeviceId(deviceFromWeb.getGeneratedDeviceId());
		try 
		{
			ActionModel actionModel = new ActionModel(deviceFromWeb, hexValue);
			ImonitorControllerAction configureSwitchType = new RGBColorPickerAction();
			configureSwitchType.executeCommand(actionModel);
			boolean resultFromImvg = IMonitorUtil.waitForResult(configureSwitchType);

			if(resultFromImvg){
				
					result = Constants.DB_SUCCESS;
				
			}else{
				
				result = Constants.NO_RESPONSE_FROM_GATEWAY;//"no";
			}
		} catch (Exception e) 
		{
			 e.printStackTrace();
			 LogUtil.info("Error message : " + e.getMessage());
			 result = Constants.iMVG_FAILURE;
		}
		

	LogUtil.info("resultttt :   "+result);
	LogUtil.info("sendRGBValue end");
	return stream.toXML(result);
}

//IR AV Blaster Output changes
public String updateIROutputValue(String xmlDevice, String IRPortXml){
	XStream stream = new XStream();
	Device device =  (Device) stream.fromXML(xmlDevice);
	String IRPort =  (String) stream.fromXML(IRPortXml);
	String generatedDeviceId = device.getGeneratedDeviceId();
	Device deviceFromDB = null;
	DeviceManager deviceManager = new DeviceManager();
	String deviceTypeName = "";
	String result = "";
	try {
		
		deviceFromDB = deviceManager.getDeviceWithConfigurationAndMake(generatedDeviceId);
		deviceTypeName = deviceFromDB.getDeviceType().getName();
	} catch (Exception e) {
		LogUtil.info("Error:", e);
	}
	AgentManager agentManager = new AgentManager();
	Agent agent = agentManager.getReceiverIpAndPort(device.getGateWay().getMacId());
	deviceFromDB.getGateWay().setAgent(agent);
	ActionModel actionModel = new ActionModel(deviceFromDB, null);
	ImonitorControllerAction controllerAction = null;
	if(deviceTypeName.equalsIgnoreCase("Z_WAVE_AV_BLASTER")){
		controllerAction = new AVBlasterIROutputAction(IRPort);
	}
	
	try {
		controllerAction.executeCommand(actionModel);
	} catch (Exception e) {
		LogUtil.info("Error", e);
	}
	boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
	if(resultFromImvg){
		if(controllerAction.isActionSuccess()){
			//Setting IR port(channel Code) value to battery Status variable to reflect in the UI
			deviceFromDB.setBatteryStatus(IRPort);
			boolean dbUpdate1 = deviceManager.updateDevice(deviceFromDB);
			if(!(dbUpdate1)){
				result = Constants.DB_FAILURE;
			}else {
				result = Constants.DB_SUCCESS;
			}
		}else{
			result = Constants.iMVG_FAILURE;
		}
	}else{
		result = Constants.NO_RESPONSE_FROM_GATEWAY;
	}
	return stream.toXML(result);
}

//Virtual Switch Changes
public String updateSwitchTypeForScenario(String xmlDevice,String scenarioXml){

	XStream stream = new XStream();
	String result = "";
	
	Device deviceFromWeb = (Device) stream.fromXML(xmlDevice);
	long scenarioId = (Long) stream.fromXML(scenarioXml);
	
	//Stored in posIdx variable just to send messsage to gateway 
	deviceFromWeb.setPosIdx(scenarioId);
	DeviceManager deviceManager = new DeviceManager();
	Device device = deviceManager.getDeviceByGeneratedDeviceId(deviceFromWeb.getGeneratedDeviceId());
	
	//Make changes for scenario
	ActionModel actionModel = new ActionModel(deviceFromWeb, null);
	ImonitorControllerAction configureSwitchType = new ConfigureSwitchType();
	configureSwitchType.executeCommand(actionModel);
	boolean resultFromImvg = IMonitorUtil.waitForResult(configureSwitchType);
	
	//3. Update Device if Master Controller sends success
	if(resultFromImvg){
		if(configureSwitchType.isActionSuccess()){
			DeviceTypeManager manager=new DeviceTypeManager();
			
			//Changes done by apoorva
			//Saving scenarioId after success to battery status variable for TouchPanel(Need the id to fetch scenario object to display while editing )
			String sceId = Long.toString(scenarioId);
			device.setBatteryStatus(sceId);
		
			 if (deviceFromWeb.getSwitchType()==7)
			{
				LogUtil.info("Scenario Control");
			}
			device.setSwitchType(deviceFromWeb.getSwitchType());
			if(deviceManager.updateDevice(device)){
				result = Constants.DB_SUCCESS;
			}else{
				result = Constants.DB_FAILURE;//"db";
			}
			
		}else{ //iMVG returned a failure
			result = Constants.iMVG_FAILURE;//"iMVG";
		}
	}else{
		
		result = Constants.NO_RESPONSE_FROM_GATEWAY;//"no";
	}
	//}
	
	return stream.toXML(result);
}

//Virtual Switch
@SuppressWarnings("unchecked")
public String saveVirtualSwitchConfiguration(String deviceXml,String keyconfigObjectsXml){
	
//	LogUtil.info("key config: " + keyconfigObjectsXml);
	String result = Constants.FAILURE;
	XStream stream = new XStream();
	try
	{
		Device device1=(Device) stream.fromXML(deviceXml);
	   	DeviceManager deviceManager=new DeviceManager();
		Device device=deviceManager.getDevice(device1.getId());
		
		ArrayList<KeyConfiguration> keyconfigObjects=(ArrayList<KeyConfiguration>) stream.fromXML(keyconfigObjectsXml);
		
		ActionModel actionModel = new ActionModel(device,keyconfigObjects);
		ImonitorControllerAction SceneContRemoteAction = new VirtualSwitchConfigurationAction();
		SceneContRemoteAction.executeCommand(actionModel);
	
		boolean resultFromImvg = IMonitorUtil.waitForResult(SceneContRemoteAction);
		
		if(resultFromImvg){
			if(SceneContRemoteAction.isActionSuccess())
			{
				
				try
				{
					for (int i = 0; i < keyconfigObjects.size(); i++)
					{
						//LogUtil.info(stream.toXML("Object index"+i));
						
						KeyConfiguration configuration=keyconfigObjects.get(i);		
						if(configuration.getAction().getId() == 0){
							configuration.setAction(null);
						}
					//	LogUtil.info(stream.toXML(keyconfigObjects.get(i)));
						//KeyConfiguration configuration=keyconfigObjects.get(i);
						new DaoManager().update(configuration);
						result = Constants.DB_SUCCESS;
					}
					
					
				}
				catch(Exception e)
				{
					result = Constants.DB_FAILURE;	//DB Update Failed.
					LogUtil.error(e.getMessage());
					LogUtil.info(e.getMessage(), e);
				}
				
			}else{ 
				result = Constants.iMVG_FAILURE;			//iMVG returned a failure.
			}
		}else{
			result = Constants.NO_RESPONSE_FROM_GATEWAY;				//GateWay did not respond.
		}
	}
	catch(Exception e)
	{
		LogUtil.error(e.getMessage());
		LogUtil.info(e.getMessage(), e);
	}
	return stream.toXML(result);
}


//Touch Panel
public String getDeviceListByMacId(String xml)
{
	XStream stream=new XStream();
	List<Device> deviceList = new ArrayList<Device>();
	try
	{
		
		Device device1=(Device) stream.fromXML(xml);
		
		GatewayManager manager=new GatewayManager();
		GateWay gateWay=manager.getGateWayByMacId(device1.getGateWay().getMacId());
		DeviceManager deviceManager=new DeviceManager();
		List<Object[]> objects =  deviceManager.listDevicesByGatewayId(gateWay.getId());
		if(objects != null)
		{
			for(Object[] devicefrmdb:objects){
				Device device=new Device();
				DeviceType deviceType =new DeviceType();
				device.setId(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[0])));
				device.setDeviceId(((IMonitorUtil.convertToString(devicefrmdb[1]))));
				device.setGateWay(null);
				device.setGeneratedDeviceId(((IMonitorUtil.convertToString(devicefrmdb[3]))));
				device.setFriendlyName(((IMonitorUtil.convertToString(devicefrmdb[4]))));
				device.setBatteryStatus(((IMonitorUtil.convertToString(devicefrmdb[5]))));
				device.setModelNumber(((IMonitorUtil.convertToString(devicefrmdb[6]))));
				device.setDeviceType(null);
				device.setCommandParam(((IMonitorUtil.convertToString(devicefrmdb[8]))));
				device.setLastAlert(null);
				device.setArmStatus(null);
				device.setMake(null);
				device.setLocation(null);
				device.setDeviceConfiguration(null);
				device.setEnableStatus(((IMonitorUtil.convertToString(devicefrmdb[14]))));
				device.setIcon(null);
				device.setMode(((IMonitorUtil.convertToString(devicefrmdb[16]))));
				device.setEnableList(((IMonitorUtil.convertToString(devicefrmdb[17]))));
				device.setPosIdx(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[18])));
				device.setLocationMap(null);
				device.setSwitchType(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[20])));
				device.setDevicetimeout(((IMonitorUtil.convertToString(devicefrmdb[21]))));
				device.setPulseTimeOut(((IMonitorUtil.convertToString(devicefrmdb[22]))));
				deviceType.setName(((IMonitorUtil.convertToString(devicefrmdb[23]))));
				device.setDeviceType(deviceType);
				deviceList.add(device);
			}
		}
		
		
	} 
	catch (Exception e)
	{
		e.printStackTrace();
		LogUtil.info("DSM Error "+ e.getMessage());
	}
	
	return stream.toXML(deviceList);
}

//IDU status alert 
//updateIDUStatus
public String updateIDUStatus(String xml)
		throws ParserConfigurationException, SAXException, IOException {
	GatewayManager gatewayManager = new GatewayManager();
	DeviceManager deviceManager=new DeviceManager();
	DeviceConfigurationManager deviceConfigurationManager = new DeviceConfigurationManager();
	XStream stream = new XStream();
	
	Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
	queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
	String macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
	//gatewayManager.updateGateWayToStatusDeviceDiscovery(macId);
	
	String HAIF_Id = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
	String VRV1 = IMonitorUtil.commandId(queue, Constants.VRV1);
	String VRV2 = IMonitorUtil.commandId(queue, Constants.VRV2);
	String VRV3 = IMonitorUtil.commandId(queue, Constants.VRV3);
	String VRV4 = IMonitorUtil.commandId(queue, Constants.VRV4);
	
	ArrayList<String> arrayList=new ArrayList<String>();
	arrayList.add(VRV1);
	//if((VRV2 != null))
	arrayList.add(VRV2);
	arrayList.add(VRV3);
	arrayList.add(VRV4);
	int vrvNumber=1;
	
	for (String IDU : arrayList)
	{
		String[] Idus=IDU.split(";");
		
		Device iduDevice = null;
	for (int i = 0; i < Idus.length; i++) 
	{
	
		String IndivialIDU=Idus[i];
		String[] splitIDU=IndivialIDU.split(":");
		String iduID=splitIDU[0];
		String decimalValue=splitIDU[1];
		
		String fanModeValue = "";
		String fanVolumeValue = "0";
		String fanDirectionValue = "0";
		int filterSignStatus = 0;
		int temperatureValue = 16;
		
		String vrvNumbString=Integer.toString(vrvNumber);
		String UnitId=HAIF_Id+"-"+vrvNumbString+":"+iduID;
		//String UnitId=HAIF_Id;
		LogUtil.info("UnitId : " + UnitId);
		iduDevice = deviceManager.getDeviceByGeneratedDeviceId(UnitId);
		
		int value = Integer.valueOf(decimalValue);
		if ( (value & 64 ) == 64 ) {
			//Device is online
			 LogUtil.info("IDU is online");
			if ((value & 128 ) == 128) {
				//Device is ON
             LogUtil.info("IDU is on");
				//Temperature value start
				temperatureValue = ( value & 15 ) + 16 ;

				//Temperature value end
				 LogUtil.info("temp is " + temperatureValue);
				 iduDevice.setCommandParam(temperatureValue+"");
				 deviceManager.updateDevice(iduDevice);
				//Modes start
				if ((value & 256 ) == 256) {
					//FanMode
					fanModeValue = "6";
					
					//Fan Volume start
					if ((value & 8192 ) == 8192) {
						//1
						fanVolumeValue = "1";
						
					}
					else if ((value & 16384 ) == 16384) {
						//2
						fanVolumeValue = "2";
					}
					else if ((value & 32768 ) == 32768) {
						//3
						fanVolumeValue = "3";
					}
					else if ((value & 65536 ) == 65536) {
						//4
						fanVolumeValue = "4";
					}
					else if ((value & 131072 ) == 131072) {
						//5
						fanVolumeValue = "5";
					}
					//Fan Volume end

					//Swings start
					if ((value & 262144 ) == 262144) {
						//Swing On
						fanDirectionValue = "1";
					}
					else if ((value & 524288 ) == 524288) {
						//Swing Off
						fanDirectionValue = "0";
					}
					//Swings end

					//Fan Direction start
					else if ((value & 1048576 ) == 1048576) {
						//1
						fanDirectionValue = "2";
					}
					else if ((value & 2097152 ) == 2097152) {
						//2
						fanDirectionValue = "3";
					}
					else if ((value & 4194304 ) == 4194304) {
						//3
						fanDirectionValue = "4";
					}
					else if ((value & 8388608 ) == 8388608) {
						//4
						fanDirectionValue = "5";
					}
					else if ((value & 16777216 ) == 16777216) {
						//5
						fanDirectionValue = "6";
					}
					//Fan Direction end
				}
				else if ((value & 512 ) == 512) {
					//HeatMode
					fanModeValue = "1";

				}
				else if ((value & 1024 ) == 1024) {
					//CoolMode
					fanModeValue = "2";
				}
				else if ((value & 2048 ) == 2048) {
					//AutoMode
					fanModeValue = "10";
				}
				else if ((value & 4096 ) == 4096) {
					//Dry Mode
					fanModeValue = "8";
				} 
				//Modes end
				

				
				//Filter Sign
				
				else if ((value & 67108864 ) == 67108864) {
					filterSignStatus = 1;
				}
				
				
			}else{
				 LogUtil.info("IDU is off");
				 iduDevice.setCommandParam("0");
				 deviceManager.updateDevice(iduDevice);
				
			}
		}else {
			 LogUtil.info("IDU is offline");
			 AlertType alertType = IMonitorUtil.getAlertTypes().get(
						Constants.DEVICE_DOWN);
			 Date date = new Date();
		//	 deviceManager.updateAlertsFromImvg(device.getId(),alertType,date,device,alertValue);
		}
		
		
		if(iduDevice != null)
		{
			DeviceConfiguration devconfiguration=deviceConfigurationManager.getDeviceConfigurationById(iduDevice.getDeviceConfiguration().getId());
			IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) devconfiguration;
			
			configuration.setFanModeValue(fanModeValue);
			configuration.setFanVolumeValue(fanVolumeValue);
			configuration.setFanDirectionValue(fanDirectionValue);
			configuration.setFilterSignStatus(filterSignStatus);
			configuration.setTemperatureValue(temperatureValue);
		//	LogUtil.info("final update " + stream.toXML(configuration));
			boolean afterSave=deviceConfigurationManager.updateIduConfiguration(configuration);                                                                                                                                         
			iduDevice.setDeviceConfiguration(configuration);
			//boolean afterSave=daoManager.update(iduDevice);
			LogUtil.info(" Config update result-->"+afterSave);
		}			
	}
	
	vrvNumber=vrvNumber+1;
		
	}
	
	
	return "SUCCESS";
}

}
