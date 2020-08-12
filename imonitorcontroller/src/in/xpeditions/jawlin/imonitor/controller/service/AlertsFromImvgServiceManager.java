/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.communication.AlertNotiifier;
import in.xpeditions.jawlin.imonitor.controller.control.SecurityActionControl;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertMonitor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertStatus;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AreaCode;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotificationForScedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertsAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserChoosenAlerts;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AlertTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AlertsFromImvgManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.Alexamanager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DashboardManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceConfigurationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ImvgAlertsActionsManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ImvgAlertNotificationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.RuleManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScheduleManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserChoosenAlertsManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.acConfigurationManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.EMailNotifications;
import in.xpeditions.jawlin.imonitor.controller.notifications.SmsNotifications;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;
import in.xpeditions.jawlin.imonitor.util.XmlUtil;
import in.xpeditions.jawlin.imonitor.util.ModbusConstants;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import java.util.Queue;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.http.HTTPException;




import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;

import imonitor.cms.alexa.proactive.service.AlexaProactiveDeleteDeviceService;
import imonitor.cms.alexa.proactive.service.AlexaProactiveUpdateScenarioService;
import imonitor.cms.alexa.proactive.service.AlexaProactiveUpdateService;

/**
 *  Updates alerts received from iMVG, executes Rules if exists any corresponding to received alert and finally notifies the customer if configured.
 * 
 * @author Coladi
 * 
 */
public class AlertsFromImvgServiceManager {

	/**
	 * Updates ArmStatus for a particular device.
	 *
	 * @param	xml		java.lang.String
	 * @return	type	java.lang.String
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#armADevice(java.lang.String)
	 * 				
	 */
	public String armADevice(String xml) throws Exception {
		Status armStatus = IMonitorUtil.getStatuses().get(Constants.ARM);
		updateArmStatus(xml, armStatus);
		return "success";
	}
	
	/**
	 * Updates alerts received from iMVG and executes rules corresponding to AWAY_SUCCESS.
	 * 
	 * @param	xml		java.lang.String
	 * @return			xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#armAllDevices(java.lang.String)
	 * 				
	 */
	public String armAllDevices(String xml) throws Exception {
		changeArmStatusOfAllDevices(xml, Constants.ARM);
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.AWAY_SUCCESS);
		Queue<KeyValuePair> resultQueue =updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
		//return "success";
	}
	
	/**
	 * Updates alerts received from iMVG and executes rules corresponding to HOME_SUCCESS.
	 *
	 * @param	xml		java.lang.String
	 * @return			xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#disarmAllDevices(java.lang.String)
	 * 				
	 */
	public String disarmAllDevices(String xml) throws Exception {
		changeArmStatusOfAllDevices(xml, Constants.DISARM);
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.HOME_SUCCESS);
		Queue<KeyValuePair> resultQueue =updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
		//return "success";
	}
	// ************************************************************** sumit begin ***********************************************************
	/**
	 * Updates alerts received from iMVG and executes rules corresponding to STAY_SUCCESS.
	 *
	 * @param	xml		java.lang.String
	 * @return			xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#armStayDevices(java.lang.String)
	 * 				
	 */
	public String armStayDevices(String xml) throws Exception {
		changeArmStatusOfAllDevices(xml, Constants.STAY);
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.STAY_SUCCESS);
		Queue<KeyValuePair> resultQueue =updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
		//return "success";
	}
	
	/**
	 * Updates current mode for the customer.
	 * <p>
	 * Updates Arm Status for all devices of the customer.
	 * 
	 * @param	xml		java.lang.String
	 * @param	armStatus	java.lang.String
	 * @return			void
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#changeArmStatusOfAllDevices(java.lang.String, java.lang.String)
	 * 				
	 */
	private void changeArmStatusOfAllDevices(String xml, String armStatus)
			throws ParserConfigurationException, SAXException, IOException {
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		String imvgId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
		DeviceManager deviceManager = new DeviceManager();
//		deviceManager.updateAllDevicesArmStatus(imvgId, armStatus);
		
		deviceManager.updateCurrentModeForCustomer(imvgId, armStatus);
		deviceManager.updateArmStatusForAllDevicesOfCustomer(imvgId, armStatus);
	}
	// ************************************************************** sumit end ************************************************************	
	/**
	 * Updates arm status for a device to DISARM.
	 * 
	 * @param	xml		java.lang.String
	 * @return	type	java.lang.String
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#disarmADevice(java.lang.String)
	 * 				
	 */
	public String disarmADevice(String xml) throws Exception {
		Status armStatus = IMonitorUtil.getStatuses().get(Constants.DISARM);
		updateArmStatus(xml, armStatus);
		return "success";
	}
	
	/**
	 * Updates arm status of a device.
	 *
	 * @param	xml		java.lang.String
	 * @param	armStatus java.lang.String
	 * @return			void
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateArmStatus(java.lang.String, in.xpeditions.jawlin.imonitor.controller.dao.entity.Status)
	 * 				
	 */
	private void updateArmStatus(String xml, Status armStatus)
			throws ParserConfigurationException, SAXException, IOException {
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		DeviceManager deviceManager = new DeviceManager();
		deviceManager.updateDeviceArmStatus(generatedDeviceId, armStatus);
	}
	
	/**
	 * Updates alert 'DEVICE_UP' received from iMVG and executes corresponding rules.
	 *
	 * @param	xml		java.lang.String
	 * @return			xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateDeviceUpAlert(java.lang.String)
	 * 				
	 */
	public String updateDeviceUpAlert(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_UP);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}

	/**
	 * Updates alert 'DEVICE_DOWN' received from iMVG and executes corresponding rules.
	 *
	 * @param	xml		java.lang.String
	 * @return			xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateDeviceDownAlert(java.lang.String)
	 * 				
	 */
	public String updateDeviceDownAlert(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.DEVICE_DOWN);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);

		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	
	/**
	 * Updates enable status for camera to 'ENABLE/DISABLE'.
	 * <p>
	 * Updates alert received from iMVG.
	 * 
	 * @param	xml		java.lang.String
	 * @return			xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateCameraStatus(java.lang.String)
	 * 				
	 */
	public String updateCameraStatus(String xml) throws Exception {
		DeviceManager deviceManager = new DeviceManager();
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.CAMERA_STATE_CHANGED);
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		// Get the Device Id.
		String generatedDeviceId = IMonitorUtil.commandId(queue,Constants.DEVICE_ID);
		// Save the alerts.
		// Save the status.
		String Status=IMonitorUtil.commandId(queue,
				Constants.IPC_ENABLE_LIVE_STREAM);
		
		if(( Status.equals(Constants.ENABLE)))
		{
				Status="1";
				deviceManager.updateDeviceEnableStatus(generatedDeviceId,Status,alertType);
		}
		if((Status.equals(Constants.DISABLE)))
		{
				Status="0";
				deviceManager.updateDeviceEnableStatus(generatedDeviceId,Status,alertType);
		}
		Queue<KeyValuePair> resultQueue = new LinkedList<KeyValuePair>();
		resultQueue.add(new KeyValuePair(Constants.CMD_ID,
				Constants.DEVICE_ALERT_ACK));
		resultQueue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil
				.commandId(queue, Constants.TRANSACTION_ID)));
		resultQueue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil
				.commandId(queue, Constants.IMVG_ID)));
		resultQueue
				.add(new KeyValuePair(Constants.DEVICE_ID, generatedDeviceId));
		
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	
	/**
	 * Updates device last alert, status.
	 *
	 * @param xml java.lang.String
	 * @return xml java.util.Queue<KeyValuePair>
	 * @see in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateDeviceStatus(java.lang.String)
	 * 				
	 */
	@SuppressWarnings("unused")
	public String updateDeviceStatus(String xml) throws Exception {
		DeviceManager deviceManager = new DeviceManager();
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.DEVICE_STATE_CHANGED);
		// Get the Device Id.
		// Save the alerts.
		// Save the status.

        // Save the alert
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		
		String generatedDeviceId = IMonitorUtil.commandId(queue,Constants.DEVICE_ID);
		String imvg = IMonitorUtil.commandId(queue,Constants.IMVG_ID);
		
		String referenceTimeStamp = IMonitorUtil.commandId(queue, "IMVG_TIME_STAMP");
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = (Date) time.parse(referenceTimeStamp);
		} catch (ParseException e) 
		{
			date = new Date();
			LogUtil.error("Error while parsing Reference Time Stamp: "+ referenceTimeStamp);
		}
		XStream stream = new XStream();
		Device device=deviceManager.getDeviceByGeneratedDeviceId(generatedDeviceId);
		String deviceStatus= null;
		String acmode= null;
		String acSwing= null;
		String Fanspeed= null;
		String actemparature= null;
		String acsetpoint= null;
		String alexaDeviceState = null;
		deviceStatus = IMonitorUtil.commandId(queue,Constants.SWITCH_DIMMER_STATE);
		if(device.getDeviceType().getName().equalsIgnoreCase("Z_WAVE_DOOR_LOCK"))
		{
			deviceStatus = IMonitorUtil.commandId(queue,Constants.DOOR_LOCK_STATE);
		}
		else if(device.getDeviceType().getName().equalsIgnoreCase(Constants.Z_WAVE_MOTOR_CONTROLLER))
		{
			deviceStatus = IMonitorUtil.commandId(queue,Constants.PERCENTAGE);
		}
		/* if(device.getDeviceType().getName().equalsIgnoreCase(Constants.Z_WAVE_DIMMER) || (device.getDeviceType().getName().equalsIgnoreCase(Constants.Z_WAVE_SWITCH)))*/
		 if(device.getDeviceType().getName().equalsIgnoreCase(Constants.Z_WAVE_SWITCH))
		{
			
			
			deviceStatus = IMonitorUtil.commandId(queue,Constants.SWITCH_DIMMER_STATE);
            AlertTypeManager alertTypeManager = new AlertTypeManager();
			
			if(deviceStatus == "1" || deviceStatus.equals("1") ){
				String deviceOn = "DEVICE_ON";
				alexaDeviceState = "ON";
				AlertType alert = alertTypeManager.getAlertTypeByDetails(deviceOn);
				Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
						alert);
				
			}else if(deviceStatus == "0" || deviceStatus.equals("0")){
				
				String deviceOff = "DEVICE_OFF";
				alexaDeviceState = "OFF";
				AlertType alert = alertTypeManager.getAlertTypeByDetails(deviceOff);
				Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
						alert);
			}
			
			/*Naveen added on 9th Feb 2018
			 * This part of code is used to update device to alexa end point
			 * Send only for alexa user
			 */
			
			
			try {
				
				checkTokenAndUpdateDeviceToAlexaEndpoint(imvg,device,alexaDeviceState,null);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
        		
			
		}
		
		
		if(device.getDeviceType().getName().equalsIgnoreCase("Z_WAVE_AC_EXTENDER"))
		{
			
			if(IMonitorUtil.commandId(queue,Constants.AC_MODE_TEMP).equals("0"))
			{
			acmode = IMonitorUtil.commandId(queue,Constants.AC_MODE_CHANGED);
			if(acmode.equals("0"))
			{
				deviceStatus ="0";
			}
			else
			{
			deviceStatus ="22";
			}
			deviceManager.updateCommadParamAndFanModesOfDeviceByGeneratedId(generatedDeviceId, imvg, deviceStatus, acmode);
			}
			else if(IMonitorUtil.commandId(queue,Constants.AC_MODE_TEMP).equals("1"))
			{
			acsetpoint = IMonitorUtil.commandId(queue,Constants.AC_THERMOSTATSETPOINTTYPE);
			deviceStatus = IMonitorUtil.commandId(queue,Constants.AC_TEMPERATURE_STATE);
			deviceManager.changeDeviceLastAlertAndStatusAlsoSaveAlert(generatedDeviceId, alertType, deviceStatus, date);
			
			//Updating for Alexa
			try {
				
				checkTokenAndUpdateDeviceToAlexaEndpoint(imvg, device, deviceStatus, acmode);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			} 
			else if(IMonitorUtil.commandId(queue,Constants.AC_MODE_TEMP).equals("2"))
			{
				
				
				/**Kantharaj is changed due to When FanSpeed is controlled from Local iMVG will send
				 * 1-Low
				 * 5-Medium
				 * 3-High
				 * 
				 * In CMS Fan Speed is saved as below 
				 * 1-Low
				 * 2-Medium
				 * 3-High
				 * 
				 * 
				 * **/
				
					Fanspeed=IMonitorUtil.commandId(queue,Constants.AC_FAN_MODE_CHANGED);
				
				
					if(Fanspeed.equals("1"))
					{
						Fanspeed="1";	
					}
					else if(Fanspeed.equals("5"))
					{
						Fanspeed="2";	
					}
					else if(Fanspeed.equals("3"))
					{
						Fanspeed="3";	
					}
					
					
				deviceManager.updateFanModesOfDeviceByGeneratedId(generatedDeviceId, imvg, Fanspeed,1);
				
				
				
			} 
			else if(IMonitorUtil.commandId(queue,Constants.AC_MODE_TEMP).equals("3"))
			{
				acSwing=IMonitorUtil.commandId(queue,Constants.AC_SWING_CONTROL);
				deviceManager.updateFanModesOfDeviceByGeneratedId(generatedDeviceId, imvg, acSwing,0);
			} 
			else if(IMonitorUtil.commandId(queue,Constants.AC_MODE_TEMP).equals("4"))
			{
				
				acmode = IMonitorUtil.commandId(queue,Constants.AC_MODE_CHANGED);
				//LogUtil.info("acmode----"+acmode);
				if(!(acmode.equals("6")))
				{
					deviceStatus = IMonitorUtil.commandId(queue,Constants.AC_TEMPERATURE_STATE);	
				}
				else
				{
					deviceStatus="22";
				}
				Fanspeed=IMonitorUtil.commandId(queue,Constants.AC_FAN_MODE_CHANGED);
			//	LogUtil.info("Fanspeed---"+Fanspeed);
				
				String FanModevalue="1";
				if(Fanspeed.equals("1"))
				{
					Fanspeed="1";	
				}
				else if(Fanspeed.equals("5"))
				{
					Fanspeed="2";	
				}
				else if(Fanspeed.equals("3"))
				{
					Fanspeed="3";	
				}
				
				acSwing=IMonitorUtil.commandId(queue,Constants.AC_SWING_CONTROL);
				deviceManager.updateAllforacOfDeviceByGeneratedId(generatedDeviceId, imvg, deviceStatus, acmode,Fanspeed,acSwing);
			
			
				
				
			
			} 
		}
		if(device.getDeviceType().getName().equalsIgnoreCase(Constants.Z_WAVE_DIMMER))
		{
			deviceStatus = IMonitorUtil.commandId(queue,Constants.SWITCH_DIMMER_STATE);
			
			/*Naveen added on 9th Feb 2018
			 * This part of code is used to update device to alexa end point
			 * Send only for alexa user
			 */
			
			
			
			try {
				
				checkTokenAndUpdateDeviceToAlexaEndpoint(imvg,device,deviceStatus,null);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			deviceManager.changeDeviceLastAlertAndStatusAlsoSaveAlertAndCheckArm(generatedDeviceId, alertType, deviceStatus, date);
			
		}
		else
		{
		deviceManager.changeDeviceLastAlertAndStatusAlsoSaveAlertAndCheckArm(generatedDeviceId, alertType, deviceStatus, date);
		}
		
//		SWITCH_DIMMER_STATE
//		Preparing the return queue.
		Queue<KeyValuePair> resultQueue = new LinkedList<KeyValuePair>();
		resultQueue.add(new KeyValuePair(Constants.CMD_ID,
				Constants.DEVICE_ALERT_ACK));
		resultQueue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil
				.commandId(queue, Constants.TRANSACTION_ID)));
		resultQueue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil
				.commandId(queue, Constants.IMVG_ID)));
		resultQueue
				.add(new KeyValuePair(Constants.DEVICE_ID, generatedDeviceId));
		
	
		return stream.toXML(resultQueue);
	}

	/**
	 * @author sumit
	 * @param xml java.lang.String
	 * @throws Exception
	 */
	public void syncDeviceStatus(String xml) throws Exception {
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);	
		//String macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		String alert = IMonitorUtil.commandId(queue, Constants.ALERT_EVENT);
		String deviceStatus = IMonitorUtil.commandId(queue, Constants.DEVICE_STATUS);
		
		if(deviceStatus.equals("-1")) //Checking Whether status value is -1,
			deviceStatus="0";		  //If -1 it's indicates deviceCurrent level is zero,Changes by Bhavya and kantharaj
			
		
		AlertType alertType = new AlertType();
		DeviceManager deviceManager = new DeviceManager();
		
		Device device = deviceManager.getDeviceWithConfiguration(generatedDeviceId);
		DeviceType deviceType = device.getDeviceType();
		
		if(alert.equalsIgnoreCase(Constants.DEVICE_UP) && !deviceStatus.equals("0"))
			alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_UP);
			
		if(alert.equalsIgnoreCase(Constants.DEVICE_DOWN) && deviceStatus.endsWith("0"))
			alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_DOWN);
		
		//1.Update both [Device Status] and [Device Last Alert] if Device Type is either Z_WAVE_SWITCH or Z_WAVE_SIREN or Z_WAVE_LOCK or Z_WAVE_DIMMER
		String deviceTypeName = deviceType.getName();
		if(deviceTypeName.equalsIgnoreCase(Constants.Z_WAVE_SWITCH) 
				|| deviceTypeName.equalsIgnoreCase(Constants.Z_WAVE_DIMMER)
				|| deviceTypeName.equalsIgnoreCase(Constants.Z_WAVE_DOOR_LOCK)
				|| deviceTypeName.equalsIgnoreCase(Constants.Z_WAVE_DOOR_LOCK_NUM_PAD)
				|| deviceTypeName.equalsIgnoreCase(Constants.Z_WAVE_SIREN)){
			
			deviceManager.updateDeviceStatusAndAlert(generatedDeviceId, deviceStatus, alertType);
			
		}else{
			//2.Update only [Device Last Alert] for other device types.
			deviceManager.updateDeviceAlert(generatedDeviceId,alertType);
		}
	}

	//kantha added
	/**
	 * Updates device status.
	 *
	 * @param	xml		java.lang.String
	 * @return			xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#UpdatedDeviceStatus(java.lang.String)
	 * 				
	 */
	public String UpdatedDeviceStatus(String xml) throws Exception {
		AlertType alertType=new AlertType();
		DeviceManager deviceManager = new DeviceManager();
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);	
		queue.poll();
		KeyValuePair element=queue.poll();
		String transationid=IMonitorUtil.commandId(element, Constants.TRANSACTION_ID);
		element=queue.poll();
		String imvgid=IMonitorUtil.commandId(element, Constants.IMVG_ID);
		element=queue.poll();
		//String config=IMonitorUtil.commandId(element, Constants.CONFIG_PARAM);
		while(queue.size()!=2)
		{
			element=queue.poll();
			String generatedDeviceId=IMonitorUtil.commandId(element, Constants.DEVICE_ID);
			DeviceManager devicemanger=new DeviceManager();
			Device device=devicemanger.getDeviceWithConfiguration(generatedDeviceId);
			if(device!=null)
			{
				
				DeviceType deviceType=device.getDeviceType();
				String devicetype=deviceType.getName();
				
				element=queue.poll();
				String deviceStatus=IMonitorUtil.commandId(element, Constants.DEVICE_STATUS);
				
				element=queue.poll();
				String deviceLastAlert=IMonitorUtil.commandId(element,"ALERT_TYPE");
				if(deviceLastAlert.equals("1"))
				alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_UP);
				if(deviceLastAlert.equals("0"))
				alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_DOWN);
				
				
				if((devicetype.equals("Z_WAVE_MOTOR_CONTROLLER"))||(devicetype.equals("Z_WAVE_AC_EXTENDER"))||(devicetype.equals("Z_WAVE_DOOR_SENSOR")))
				{
					
					deviceManager.updateDeviceAlert(generatedDeviceId,alertType);
				
				}
				else if((devicetype.equals("IP_CAMERA")))
				{
					if(deviceStatus.equals("0"))
					deviceStatus="1";
					else if(deviceStatus.equals("1"))
					deviceStatus="0";
					
					deviceManager.updateDeviceEnableStatus(generatedDeviceId, deviceStatus,alertType);
				}
				else if((devicetype.equals("Z_WAVE_SWITCH")))
				{
					if(deviceStatus.equals("-1"))
						deviceStatus="0";
					
					if(deviceStatus.equals("255"))
					{
						deviceStatus="1";
						alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_UP);
					}
				deviceManager.updateDeviceStatusAndAlert(generatedDeviceId, deviceStatus,alertType);
				}
				else if((devicetype.equals("Z_WAVE_DIMMER")))
				{
					if(deviceStatus.equals("-1"))
					deviceManager.updateDeviceAlert(generatedDeviceId,alertType);
					else
					deviceManager.updateDeviceStatusAndAlert(generatedDeviceId, deviceStatus,alertType);
				}
				else if((devicetype.equals("Z_WAVE_MINIMOTE"))||((devicetype.equals("Z_WAVE_HEALTH_MONITOR")))||((devicetype.equals("Z_WAVE_MULTI_SENSOR")))||((devicetype.equals("Z_WAVE_LCD_REMOTE")))||((devicetype.equals("Z_WAVE_PIR_SENSOR"))))
				{
					
				}
				else
				{
					if(deviceStatus.equals("-1"))
					deviceStatus="0";
					deviceManager.updateDeviceStatusAndAlert(generatedDeviceId, deviceStatus,alertType);
				}
		}
			else
			{
				queue.poll();
				queue.poll();
			}
		}
	
//Preparing the return queue.
		Queue<KeyValuePair> resultQueue = new LinkedList<KeyValuePair>();
		resultQueue.add(new KeyValuePair(Constants.CMD_ID,Constants.UPDATED_DEVICE_STATUS_ACK));
		resultQueue.add(new KeyValuePair(Constants.TRANSACTION_ID,transationid));
		resultQueue.add(new KeyValuePair(Constants.IMVG_ID,imvgid ));
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}

//closing

	/**
	 * Updates alert for Panic Situation and executes corresponding rule.
	 * 
	 * @param	xml		java.lang.String
	 * @return			xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#panicAlert(java.lang.String)
	 * 				
	 */
	public String panicAlert(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.PANIC_SITUATION);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}

	// ***************************************** sumit start: Shock and Smoke Detetctor ****************************************************
	/**
	 * Inimates CMS that Shock has been sensed. Execute corresponding rules.
	 * 
	 * @author sumit
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateShockDetected(java.lang.String)
	 * 				
	 */
	public String updateShockDetected(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.SHOCK_DETECTED);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	/**
	 * Intimates CMS that Shock has stopped. Execute corresponding rule.
	 * 
	 * @author sumit
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateShockStopped(java.lang.String)
	 * 				
	 */
	public String updateShockStopped(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.SHOCK_STOPPED);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	// ************************************************ sumit end: ZXT120 Temperature Sensor *************************************************
	/**
	 * Intimates CMS that a particular door has been opened, thereby update device status and execute corresponding rule.
	 * 
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateDoorOpenAlert(java.lang.String)
	 * 				
	 */
	public String updateDoorOpenAlert(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.DOOR_OPEN);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		String generatedDeviceId = IMonitorUtil.commandId(queue,Constants.DEVICE_ID);
		DeviceManager deviceManager=new DeviceManager();
		Device device=new Device();
		String deviceStatus="1";
		deviceManager.updateDeviceCommandParam(generatedDeviceId, deviceStatus);
		boolean isAlexaUserExist = deviceManager.checkAlexaUserByCustomerId(device.getGateWay().getCustomer());;
		LogUtil.info("isAlexaUserExist"+isAlexaUserExist);
		if(isAlexaUserExist) {
			
			AlexaProactiveUpdateService updateAlert = new AlexaProactiveUpdateService(device.getGateWay().getCustomer(),device.getLastAlert());
			LogUtil.info("updateAlert"+updateAlert);
			Thread t = new Thread(updateAlert);
			t.start();
		}
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}

	/**
	 * Intimates CMS that Smoke has been sensed therefore execute corresponding rule.
	 *
	 * @author sumit
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateSmokeDetected(java.lang.String)
	 * 				
	 */
	public String updateSmokeDetected(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.SMOKE_SENSED);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	/**
	 * Intimate CMS that Smoke has cleared therefore execute corresponding rule if exists any.
	 * 
	 * @author sumit
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateSmokeCleared(java.lang.String)
	 * 				
	 */
	public String updateSmokeCleared(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.SMOKE_CLEARED);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	// *********************************************** sumit end: Shock and Smoke Detetctor **************************************************
	
	// *********************************************** sumit start: ZXT120 Temperature Sensor ************************************************
	/**
	 * Update that change in temperature has been sensed, therefore execute corresponding rule if exists any.
	 * 
	 * @author sumit
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateTemperatureChange(java.lang.String)
	 * 				
	 */
	public String updateHMDWarning(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.HMD_WARNING);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}  
	
	
	public String updateHMDNormal(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.HMD_NORMAL);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	
	public String updateHMDFailure(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.HMD_FAILURE);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	public String updateHMDPowerInformation(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.POWER_INFORMATION);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	public String updateHMDEnergyInformation(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.ENERGY_INFORMATION);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	public String updateTemperatureChange(String xml) throws Exception {
		
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.TEMPERATURE_CHANGE);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	/**
	 * Intimates CMS about Motion Detected and thereby update device status and execute corresponding rule.
	 * 
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateMotionDetectionAlert(java.lang.String)
	 * 				
	 */
	public String updateMotionDetectionAlert(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.MOTION_DETECTED);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);
		LogUtil.info("resultQueue"+resultQueue);
			Device device=new Device();
			DeviceManager deviceManager=new DeviceManager();
			boolean isAlexaUserExist = deviceManager.checkAlexaUserByCustomerId(device.getGateWay().getCustomer());;
			LogUtil.info("isAlexaUserExist"+isAlexaUserExist);
			if(isAlexaUserExist) {
				
				AlexaProactiveUpdateService updateAlert = new AlexaProactiveUpdateService(device.getGateWay().getCustomer(),device.getLastAlert());
				LogUtil.info("updateAlert"+updateAlert);
				Thread t = new Thread(updateAlert);
				t.start();
			}
		
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	//sumit start: No Motion Detected alert.
	/**
	 * Intimates CMS that No Motion was detected for configured timeout value, thereby update device status and execute corresponding rule.
	 *
	 * @author sumit
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateNoMotionDetectionAlert(java.lang.String)
	 * 				
	 */
	public String updateNoMotionDetectionAlert (String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.NOMOTION_DETECTED);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		Device device=new Device();
		DeviceManager deviceManager=new DeviceManager();
		boolean isAlexaUserExist = deviceManager.checkAlexaUserByCustomerId(device.getGateWay().getCustomer());;
		LogUtil.info("isAlexaUserExist"+isAlexaUserExist);
		if(isAlexaUserExist) {
			
			AlexaProactiveUpdateService updateAlert = new AlexaProactiveUpdateService(device.getGateWay().getCustomer(),device.getLastAlert());
			LogUtil.info("updateAlert"+updateAlert);
			Thread t = new Thread(updateAlert);
			t.start();
		}
	
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
//sumit end
	/**
	 * Updates CMS that particular door has been closed, thereby update device status and execute corresponding rule.
	 *
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateDoorCloseAlert(java.lang.String)
	 * 				
	 */
	public String updateDoorCloseAlert(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.DOOR_CLOSE);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		String generatedDeviceId = IMonitorUtil.commandId(queue,Constants.DEVICE_ID);
		Device device=new Device();
		DeviceManager deviceManager=new DeviceManager();
		String deviceStatus="0";
		deviceManager.updateDeviceCommandParam(generatedDeviceId, deviceStatus);
		boolean isAlexaUserExist = deviceManager.checkAlexaUserByCustomerId(device.getGateWay().getCustomer());;
		LogUtil.info("isAlexaUserExist"+isAlexaUserExist);
		if(isAlexaUserExist) {
			
			AlexaProactiveUpdateService updateAlert = new AlexaProactiveUpdateService(device.getGateWay().getCustomer(),device.getLastAlert());
			LogUtil.info("updateAlert"+updateAlert);
			Thread t = new Thread(updateAlert);
			t.start();
		}
	
		
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
//vibhu start
	/**
	 * Intimates CMS that Neighbour Update has stated, thereby execute corresponding rule if exists any.
	 * 
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateNUpdateStart(java.lang.String)
	 * 				
	 */
	public String updateNUpdateStart(String xml) throws Exception {
		
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
	
		String alertEvent = IMonitorUtil.commandId(queue, Constants.ALERT_EVENT);
		AlertType alertType=null;
		
		if(alertEvent.equals(Constants.REQUEST_NEIGHBOR_UPDATE_STARTED))
			alertType= IMonitorUtil.getAlertTypes().get(Constants.REQUEST_NEIGHBOR_UPDATE_STARTED);
		else if(alertEvent.equals(Constants.REQUEST_ASSOCIATION_STARTED))
			alertType= IMonitorUtil.getAlertTypes().get(Constants.REQUEST_ASSOCIATION_STARTED);
		else if(alertEvent.equals(Constants.REQUEST_CONFIGURATION_STARTED))
			alertType= IMonitorUtil.getAlertTypes().get(Constants.REQUEST_CONFIGURATION_STARTED);
		else if(alertEvent.equals(Constants.ASSIGN_RETURN_NO_ROUTE))
			alertType = IMonitorUtil.getAlertTypes().get(Constants.ASSIGN_RETURN_NO_ROUTE);
		
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,alertType);

		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}

	/**
	 * Updates alert for Neighbour Update Success and execute corresponding rule.
	 * 
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateNUpdateDone(java.lang.String)
	 * 				
	 */
	public String updateNUpdateDone(String xml) throws Exception {
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		
		String alertEvent = IMonitorUtil.commandId(queue, Constants.ALERT_EVENT);
		AlertType alertType=null;
		
		//Naveen start
		if(alertEvent.equals(Constants.REQUEST_NEIGHBOR_UPDATE_DONE))
			alertType= IMonitorUtil.getAlertTypes().get(Constants.REQUEST_NEIGHBOR_UPDATE_DONE);
		else if(alertEvent.equals(Constants.REQUEST_ASSOCIATION_DONE))
			alertType= IMonitorUtil.getAlertTypes().get(Constants.REQUEST_ASSOCIATION_DONE);
		else if(alertEvent.equals(Constants.REQUEST_CONFIGURATION_DONE))
			alertType= IMonitorUtil.getAlertTypes().get(Constants.REQUEST_CONFIGURATION_DONE);
		else if(alertEvent.equals(Constants.ASSIGN_RETURN_ROUTE_SUCCESS))
			alertType = IMonitorUtil.getAlertTypes().get(Constants.ASSIGN_RETURN_ROUTE_SUCCESS);
		else if(alertEvent.equals(Constants.NORMAL_SWITCH_CONFIG_SUCCESS))
			alertType = IMonitorUtil.getAlertTypes().get(Constants.NORMAL_SWITCH_CONFIG_SUCCESS);
		else if(alertEvent.equals(Constants.ROUTING_INFO_SUCCESS))
		    alertType = IMonitorUtil.getAlertTypes().get(Constants.ROUTING_INFO_SUCCESS);
		else if(alertEvent.equals(Constants.NODE_PROTOCOL_INFO_SUCCESS))
			alertType = IMonitorUtil.getAlertTypes().get(Constants.NODE_PROTOCOL_INFO_SUCCESS);
		//end
		
		
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);

		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}

	/**
	 * Update alert for Neighbour Update has Failed. Execute corresponding rules.
	 * 
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateNUpdateFailed(java.lang.String)
	 * 				
	 */
	public String updateNUpdateFailed(String xml) throws Exception {
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		
		String alertEvent = IMonitorUtil.commandId(queue, Constants.ALERT_EVENT);
		AlertType alertType=null;
				
		if(alertEvent.equals(Constants.REQUEST_NEIGHBOR_UPDATE_FAILED))
			alertType= IMonitorUtil.getAlertTypes().get(Constants.REQUEST_NEIGHBOR_UPDATE_FAILED);
		else if(alertEvent.equals(Constants.REQUEST_ASSOCIATION_FAILED))
			alertType= IMonitorUtil.getAlertTypes().get(Constants.REQUEST_ASSOCIATION_FAILED);
		else if(alertEvent.equals(Constants.REQUEST_CONFIGURATION_FAILED))
			alertType= IMonitorUtil.getAlertTypes().get(Constants.REQUEST_CONFIGURATION_FAILED);
		else if(alertEvent.equals(Constants.ASSIGN_RETURN_ROUTE_FAIL))
			alertType = IMonitorUtil.getAlertTypes().get(Constants.ASSIGN_RETURN_ROUTE_FAIL);
		else if(alertEvent.equals(Constants.NORMAL_SWITCH_CONFIG_FAIL))
			alertType = IMonitorUtil.getAlertTypes().get(Constants.NORMAL_SWITCH_CONFIG_FAIL);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);

		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
//vibhu end	
	/**
	 * Initiates removal of a device from CMS.
	 *
	 * @param	xml		java.lang.String
	 * @return	type	java.lang.String
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#removeDeviceAlert(java.lang.String)
	 * 				
	 */
	//bhavya start
	public String healthmonitorscansuccess(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.MONITOR_DEVICE_HEALTH_SUCCESS);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);

		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}

	/**
	 * Update alert for Neighbour Update has Failed. Execute corresponding rules.
	 * 
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateNUpdateFailed(java.lang.String)
	 * 				
	 */
	public String healthmonitorscanfailure(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.MONITOR_DEVICE_HEALTH_FAIL);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);

		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	//bhavya end
	public String removeDeviceAlert(String xml) {
		LogUtil.info("remove");
		try {
			Queue<KeyValuePair> queue = IMonitorUtil
					.extractCommandsQueueFromXml(xml);
			String generatedDeviceId = IMonitorUtil.commandId(queue,
					Constants.DEVICE_ID);
		
			DeviceManager deviceManager = new DeviceManager();
			Device device = deviceManager.getDeviceByGeneratedDeviceId(generatedDeviceId);
		
			if((device.getDeviceType().getName().equals(Constants.DEV_ZWAVE_SCENE_CONTROLLER)) || (device.getDeviceType().getName().equals(Constants.DEV_ZWAVE_FIB_KEYFOBE))){
				
				boolean delete = deviceManager.deleteDeviceKeyConfiguration(device);
				
				
			}
			if(device.getDeviceType().getName().equals(Constants.DEV_ZWAVE_WALL_MOTE_QUAD)) {
				
				deviceManager.deleteWallmoteConfiguration(device);
			}
			// Drawbacks of Z-WAVE - Whatever request we got, we remove it.
			// Not checking whether they removed the asked device.
			// Here we need to check, whether we are removing the correct device
			// id which we send for remove.
			
			deviceManager.deleteDeviceTree(generatedDeviceId);
			
			
			/*Added by naveen for updating alexa end point
			 * 26th July 2018
			 * 
			 */
			String macId = IMonitorUtil.commandId(queue,Constants.IMVG_ID);
			CustomerManager customerManager = new CustomerManager();
			Customer customer = customerManager.getCustomerByMacId(macId);
			Alexamanager alexaManager = new Alexamanager();
			Alexa alexaUser = alexaManager.getAlexaUserByCustomerId(customer);
			if(alexaUser != null) {
				try {
					AlexaProactiveDeleteDeviceService alexaProactiveService = new AlexaProactiveDeleteDeviceService(alexaUser,device);
					Thread t = new Thread(alexaProactiveService);
					 t.start();
					//alexaServiceManager.updateDeviceListToAlexa(customer,alexaUser);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Intimate CMS that an Image upload to FTP Server is successful. Execute corresponding rules.
	 * 
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#saveFtpImvgAlert(java.lang.String)
	 * 				
	 */
	public String saveFtpImvgAlert(String xml) throws Exception {

		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.IMAGE_FTP_SUCCESS);
		Queue<KeyValuePair> resultQueue = saveAlertAndExecuteRule(xml,alertType);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}

	/**
	 * Update CMS for battery status of a device. Execute corresponding rule.
	 *
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateBatteryStatus(java.lang.String)
	 * 				seley on[e[
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public String updateBatteryStatus(String xml)
			throws ParserConfigurationException, SAXException, IOException {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.BATTERY_STATUS);
		//AlertType BatteryLevel = IMonitorUtil.getAlertTypes().get(Constants.BATTERY_LEVEL);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);

		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	
	/**
	 * Updates alerts and executes rule.
	 * 
	 * @param	xml		java.lang.String
	 * @param	alertType	in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType
	 * @return	type	java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#updateBatteryStatus(java.lang.String, in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType)
	 * 				
	 */

	private Queue<KeyValuePair> updateAlertAndExecuteRule(String xml,
			AlertType alertType) throws ParserConfigurationException,
			SAXException, IOException {
		// 1. Get the device.
		// 2. Update the device
		// 3. Save the alerts
		// 4. Execute the rules
		
		Queue<KeyValuePair> queue = IMonitorUtil
				.extractCommandsQueueFromXml(xml);
		String generatedDeviceId = IMonitorUtil.commandId(queue,
				Constants.DEVICE_ID);
		String Gatewayid= IMonitorUtil.commandId(queue,
				Constants.IMVG_ID); 
		
		CustomerManager customerManager = new CustomerManager();
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		String customerId = null;
		GatewayManager gatewayManager = new GatewayManager();
		DeviceManager deviceManager = new DeviceManager();
		XStream stream = new XStream();
		// naveen start
		long id = Long.parseLong(gatewayManager.getIdOfGateway(Gatewayid));
		//customerId = gatewayManager.getCustomerIdOfGateWay(Gatewayid);
		Customer customer = customerManager.getCustomerByMacId(Gatewayid);
		//PushNotify changes 7Feb start
		//Get alerts list from userChoosenALerts table start
		Device device = null;
		//Changes done by apoorva to display Home Stay Away in alerts page
		String alertEvent = IMonitorUtil.commandId(queue, Constants.ALERT_EVENT);
		GateWay gateWay=gatewayManager.getGateWayByMacId(Gatewayid);
		String gatewayName = gateWay.getName();
		String alertValue=null;

		if(alertEvent.equalsIgnoreCase("ARM_DEVICES_SUCCESS")){
			generatedDeviceId += ("-AWAY");
			alertValue= gatewayName;
			//generatedDeviceId.concat("-AWAY");			
		}else if(alertEvent.equalsIgnoreCase("DISARM_DEVICES_SUCCESS")){
			generatedDeviceId += ("-HOME");
			alertValue= gatewayName;
			//generatedDeviceId.concat("-HOME");
		}else if(alertEvent.equalsIgnoreCase("STAY_DEVICES_SUCCESS")){
			generatedDeviceId +=("-STAY");
			alertValue= gatewayName;
			//generatedDeviceId.concat("-STAY");
			//3gp change start end
		}
		device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceId);
		long deviceId = device.getId();
		if (!alertEvent.equalsIgnoreCase("ARM_DEVICES_SUCCESS") || !alertEvent.equalsIgnoreCase("DISARM_DEVICES_SUCCESS") || !alertEvent.equalsIgnoreCase("STAY_DEVICES_SUCCESS") || !alertEvent.equalsIgnoreCase(Constants.BATTERY_STATUS))
		{
			UserChoosenAlertsManager alertsManager=new UserChoosenAlertsManager();
			UserChoosenAlerts choosenAlerts= alertsManager.listUserChoosenAlertsfromdevice(deviceId,alertType.getId());
			if (choosenAlerts!=null)
			{
					
					//LogUtil.info("Alert Name: " + alertType.getDetails());
					//LogUtil.info("Device Friendly Name  : " + device.getFriendlyName());
					PushNotificationsService pushNotificationsService= new PushNotificationsService(alertType, customer,device);
					Thread thread = new Thread(pushNotificationsService);
					thread.start();
			}
		}
	
		if(alertEvent.equalsIgnoreCase("TEMPERATURE_CHANGE")){
			
		
			alertValue = IMonitorUtil.commandId(queue, Constants.TEMPERATURE_VALUE);
			
			Device acDevice = deviceManager.getDevice(generatedDeviceId);
			long dcID = acDevice.getDeviceConfiguration().getId();
			
			deviceManager.updateAcConfigurationWithSensedTemperature(dcID,alertValue);
			
			
			
		}
		//Changes done by apoorva to display Home Stay Away in alerts page end
		else if(alertEvent.equalsIgnoreCase("PMLEVEL_CHANGE")){
			String generatedDeviceId1 = IMonitorUtil.commandId(queue,Constants.DEVICE_ID);
			String pmvalue = IMonitorUtil.commandId(queue,Constants.PM_VALUE);
			DeviceManager deviceManager1=new DeviceManager();
			deviceManager1.updateDeviceCommandParam(generatedDeviceId1, pmvalue);	
		}
		
		
		// naveen start: to take back up from the existing alertfromimvg table
		int count =Integer.parseInt(imvgAlertsActionsManager.getCountAlerts(id).toString());
		DateFormat dateFormat = new SimpleDateFormat("dd");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		
		int bck = customer.getBackup();
		if(count>10000 || ((dateFormat.format(cal.getTime()).equals("01")) && bck == 0)){
			GateWay gw = gatewayManager.getGateWayByMacId(Gatewayid);
			Set<Device> deviceList = gatewayManager.getDevicesOfGateWay(gw);
			
			//Get Entries from existing table
			for(Device d : deviceList){
				long dId = d.getId();
				imvgAlertsActionsManager.backup(dId);
				
				imvgAlertsActionsManager.deleteCurrentAlerts(dId);
				
			}
			if(dateFormat.format(cal.getTime()).equals("01")){
				customerId = customer.getCustomerId();
				LogUtil.info("customerId : " + customerId);
				customerManager.setbackupdone(customerId);
			}
		}else if(!dateFormat.format(cal.getTime()).equals("01")){
			
			customerManager.resetbackupdone(customerId);
		}
		// Naveen end
		
		String batteryStatus = IMonitorUtil.commandId(queue, Constants.BATTERY_LEVEL);
		
		String TimeStamp=IMonitorUtil.commandId(queue,"IMVG_TIME_STAMP");
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		
		try {
			date = time.parse(TimeStamp);
			
			
			
			/*to update IDU's if Slave is down
			 * Added by apoorva
			 * */
			if ((alertEvent.equalsIgnoreCase("SLAVE_DOWN")) || (alertEvent.equalsIgnoreCase("SLAVE_UP"))  )
			{
				device=new Device();
				String generatedDeviceId11 = IMonitorUtil.commandId(queue,Constants.DEVICE_ID);
				device.setGeneratedDeviceId(generatedDeviceId11);
				deviceManager.updateSlaveAndIduLastAlert(alertType,device);
			}
			
			
			device=deviceManager.changeDeviceLastAlertSaveAlertAndCheckArm(generatedDeviceId,alertType,batteryStatus,date);
			
			if(alertEvent.equalsIgnoreCase("PANIC_SITUATION"))
			{
				deviceManager.updateAlertsFromImvgForVirtualDevice(device.getId(),alertType,date,device,alertValue);
			}else if(alertEvent.equalsIgnoreCase("POWER_INFORMATION"))
			{
				alertValue=IMonitorUtil.commandId(queue, Constants.POWER_CONSUMED);
				deviceManager.updatePowerInformation(device.getId(),alertType,date,device,alertValue);
			}else if(alertEvent.equalsIgnoreCase("ENERGY_INFORMATION"))
			{
				alertValue=IMonitorUtil.commandId(queue, Constants.ENERGY_CONSUMED);
				//LogUtil.info("energy value"+alertValue);
				
				//LogUtil.info("device"+new XStream().toXML(device));
				deviceManager.updateEnergyInformation(device.getId(),alertType,date,device,alertValue);
			}
			else if(alertEvent.equalsIgnoreCase(Constants.BATTERY_STATUS))
			{
				int BatteryLevelValue=0;
				
				try {
					
					BatteryLevelValue = Integer.parseInt(batteryStatus);
					
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
				
				
				if(BatteryLevelValue <= 10)
				deviceManager.updateAlertsFromImvg(device.getId(),alertType,date,device,alertValue);
				
				//Send push notofocations
				UserChoosenAlertsManager alertsManager=new UserChoosenAlertsManager();
				UserChoosenAlerts choosenAlerts= alertsManager.listUserChoosenAlertsfromdevice(deviceId,alertType.getId());
				if (choosenAlerts!=null)
				{
						
						//LogUtil.info("Alert Name: " + alertType.getDetails());
						//LogUtil.info("Device Friendly Name  : " + device.getFriendlyName());
						PushNotificationsService pushNotificationsService= new PushNotificationsService(alertType, customer,device);
						Thread thread = new Thread(pushNotificationsService);
						thread.start();
				}
				
				
			}else if(alertEvent.equalsIgnoreCase(Constants.NODE_PROTOCOL_INFO_SUCCESS)){ // Added by Naveen
				
				/*String basicType = IMonitorUtil.commandId(queue, Constants.BASIC);				
				String generic = IMonitorUtil.commandId(queue, Constants.GENERIC);
				String specific = IMonitorUtil.commandId(queue, Constants.SPECIFIC);
				String protocolInfo = basicType +"-"+ generic+ "-" + specific;*/
				StringBuilder sb = new StringBuilder(IMonitorUtil.commandId(queue, Constants.BASIC)).append("-").append(IMonitorUtil.commandId(queue, Constants.GENERIC)).append("-").append(IMonitorUtil.commandId(queue, Constants.SPECIFIC));
				String protocolInfo = sb.toString();
				deviceManager.updateAlertsFromImvg(device.getId(),alertType,date,device,protocolInfo);
				
			}else if(alertEvent.equalsIgnoreCase(Constants.ROUTING_INFO_SUCCESS)){ // Added by Naveen
				
				String List = IMonitorUtil.commandId(queue, Constants.NEIGHBOURS);
				
				
				deviceManager.updateAlertsFromImvg(device.getId(),alertType,date,device,List);
				
			}else if((alertEvent.equalsIgnoreCase(Constants.DEVICE_UP))||(alertEvent.equalsIgnoreCase(Constants.DEVICE_DOWN))){
				deviceManager.updateAlertsFromImvg(device.getId(),alertType,date,device,alertValue);
				
				//Updating to alexa end point
				try {
					device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceId);
					updateConnectivityStatusToAlexa(Gatewayid,device,alertEvent);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				
				
				/*to update IDU's if VIA or IDU is down
				 * Added by Naveen
				 * */
				try {
					
					
					if((device.getDeviceType().getName().equals(Constants.VIA)) || (device.getDeviceType().getName().equals(Constants.VIA_UNIT )) ) {
						deviceManager.updateSlaveAndIduLastAlert(alertType,device);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				
			}
			else
			{
				
				deviceManager.updateAlertsFromImvg(device.getId(),alertType,date,device,alertValue);
			}
		} catch (ParseException e) {
			LogUtil.info("ParseException", e);
		} catch (Exception e){
			LogUtil.info("Got Exception", e);
		}
						
		return checkArmStatusAndExecuteRules(queue, generatedDeviceId, device,alertType);
	}
	/**
	 * Saves alert and executes corresponding rules.
	 * 
	 * @param	xml java.lang.String
	 * @param	alertType	in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType
	 * @return	type	java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#saveAlertAndExecuteRule(java.lang.String, in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType)
	 * 				
	 */
	private Queue<KeyValuePair> saveAlertAndExecuteRule(String xml,
			AlertType alertType) throws ParserConfigurationException,
			SAXException, IOException {
		// 1. Get the device.
		// 2. Save the alerts
		// 3. Execute the rules
		//vibhuti moded file path to top
		String filePath = "";
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		String generatedDeviceId = IMonitorUtil.commandId(queue,Constants.DEVICE_ID);
		String referenceTransaction = IMonitorUtil.commandId(queue,Constants.REF_TRANSACTION_ID);
		ImonitorControllerAction imonitorControllerAction = (ImonitorControllerAction) IMonitorSession.remove(referenceTransaction);
		//vibhuti start : we will not have an action if alert is for a rule/schedule
		if(imonitorControllerAction != null)
		{
			Queue<KeyValuePair> queue2 = imonitorControllerAction.getActionModel().getQueue();
			filePath = IMonitorUtil.commandId(queue2,Constants.FTP_PATH_FILE_NAME);
			filePath += ControllerProperties.getProperties().get(Constants.IMAGE_FILE_EXTENSION);
		}
		else
		{
			filePath = ""+ IMonitorUtil.commandId(queue,Constants.FILE_PATH)+ IMonitorUtil.commandId(queue,Constants.FILE_NAME);
		}
		//vibhuti end
		DeviceManager deviceManager = new DeviceManager();
		String TimeStamp = IMonitorUtil.commandId(queue,"IMVG_TIME_STAMP");
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try 
		{
			date = time.parse(TimeStamp);
		}catch (ParseException e) {
			LogUtil.error("Error while parsing Time Stamp :"+TimeStamp);
		}
		Device device = deviceManager.saveAlertAndCheckArm(generatedDeviceId,alertType, filePath, date);
		
		return checkArmStatusAndExecuteRules(queue, generatedDeviceId, device,alertType);
	}
// ************************************************* sumit start: IMAGE ATTACHMENT for email notification ***************************************
	/**
	 * Checks for arm status. If device is arm-ed, executes rule.
	 * 
	 * @param	queue	java.util.Queue<KeyValuePair>
	 * @param	generatedDeviceId	java.lang.String
	 * @param	device	in.xpeditions.jawlin.imonitor.controller.dao.entity.Device
	 * @param	alertType	in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType
	 * @return	type	java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#checkArmStatusAndExecuteRules(java.util.Queue, java.lang.String, in.xpeditions.jawlin.imonitor.controller.dao.entity.Device, in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType)
	 * 				
	 */
	@SuppressWarnings("unused")
	private Queue<KeyValuePair> checkArmStatusAndExecuteRules(Queue<KeyValuePair> queue, String generatedDeviceId, Device device, AlertType alertType) {
		
		AlertsFromImvgManager alertsmanager=new AlertsFromImvgManager();
		DeviceManager devicemanager=new DeviceManager();
		GatewayManager gatewaymanager=new GatewayManager();
		CustomerManager customermang=new CustomerManager();
		AlertMonitor alertmonitor=new AlertMonitor();
		Date date=new Date();
		
		RuleManager rulemanger=new RuleManager();
		String ruleId = IMonitorUtil.commandId(queue, Constants.RULE_ID);
		Queue<KeyValuePair> resultQueue = new LinkedList<KeyValuePair>();
		resultQueue.add(new KeyValuePair(Constants.CMD_ID,
				Constants.DEVICE_ALERT_ACK));
		resultQueue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil
				.commandId(queue, Constants.TRANSACTION_ID)));
		resultQueue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil
				.commandId(queue, Constants.IMVG_ID)));
		resultQueue
				.add(new KeyValuePair(Constants.DEVICE_ID, generatedDeviceId));
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	   
	    String[] Temp=strDate.split(":");
	   
	    long NwStart=Integer.parseInt(Temp[0]);
		long NwEnd=Integer.parseInt(Temp[1]);
		
	    long TotalCurrentTime=(NwStart*60)+NwEnd;
	   
	  
	    Set<DeviceAlert> deviceAlerts=new HashSet<DeviceAlert>();
	    
	    if(ruleId==null)
	    {
	    	
	    	deviceAlerts=rulemanger.GetRuleStartAndEndTimeByDeviceId(device,alertType);
	    }
	    else
	    {
	    	deviceAlerts=rulemanger.GetRuleStartAndEndTimeByDeviceId(device,alertType,Integer.parseInt(ruleId));
	       
	    }
	    
	    //vibhu Note that we have only one deviceAlert per rule.
	    for(DeviceAlert Devicealert : deviceAlerts)
	    {
	    	XStream stream = new XStream();
	    	long StartTime=Devicealert.getStartTime();
	    	long EndTime=Devicealert.getEndTime();
	    	Rule rule=Devicealert.getRule();
	    	
	    	//LogUtil.info("rule list:"+stream.toXML(rule));
	    	
	    	int alert=rule.getAlert();
	    	int security = rule.getSecurity();
			
	    	if(alert==1){
	    		Device device1=devicemanager.getDevice(generatedDeviceId);
	    		
	    		GateWay gateway=gatewaymanager.getGateWayByDevice(device1);
	    		Customer customer=customermang.getCustomerByGateWay(gateway);
	    		
	    		AreaCode arc=alertsmanager.getareacode();
	    		AlertStatus altstatus=alertsmanager.getalertstatus();
	    		
	    		//LogUtil.info(" Date :"+date);
	    		/*alertmonitor.setCustomer(customer);*/
	    		alertmonitor.setRule(rule);
	    		alertmonitor.setCustomer(customer);
	    		alertmonitor.setAlertTime(date);
	    	//	alertmonitor.setAreaCode(arc);
	    		alertmonitor.setAlertStatus(altstatus);
	    		alertsmanager.savealertsformonitor(alertmonitor);
	    	}
	    	
	    	//vibhu start
	    	byte mode=rule.getMode();
	    	if(mode != 8 && mode != 7)
	    	{
	    		Rule ruleWithGW = rulemanger.retrieveRuleDetails(rule.getId());
	    		String curMode = ruleWithGW.getGateWay().getCurrentMode();
	    		
	    		if(
		    			(curMode.equals("2") && mode!=2 && mode !=4 && mode != 6 && mode != 7 && mode != 8 )
			    		   ||	(curMode.equals("1") && mode != 1 && mode != 4 && mode != 5 && mode != 7 && mode != 8)
			    		   ||	(curMode.equals("0") && mode != 8 && mode != 7 && mode != 3 && mode != 1 &&mode != 2)
		    		   ){
	    			
		    			   
		    		    	if(StartTime>=EndTime)
		    				{
		    			    	if(TotalCurrentTime>=StartTime || TotalCurrentTime<=EndTime)
		    			    	{
		    			    		
		    			    		     sendNotifications(queue, generatedDeviceId, device, alertType,rule);
		    			    		     if (security == 1) {
		    			    					Device device1 = devicemanager.getDevice(generatedDeviceId);
		    			    					GateWay gateway = gatewaymanager.getGateWayByDevice(device1);
		    			    					Customer customer = customermang.getCustomerByGateWay(gateway);
		    			    					SecurityActionControl securityActionControl = new SecurityActionControl(customer);
		    			    					Thread t = new Thread(securityActionControl);
		    			    					t.start();

		    			    				}
		    			    	}
		    			    }
		    			    
		    				
		    		    	else if(StartTime<=EndTime)
		    		    	{
		    		    		
		    		    		if(TotalCurrentTime<=EndTime && TotalCurrentTime >=StartTime)
		    			    	{
		    		    			
		    		    			
		    				    			sendNotifications(queue, generatedDeviceId, device, alertType,rule);
		    				    			if (security == 1) {
		    				    				Device device1 = devicemanager.getDevice(generatedDeviceId);
		    				    				GateWay gateway = gatewaymanager.getGateWayByDevice(device1);
		    				    				Customer customer = customermang.getCustomerByGateWay(gateway);
		    				    				SecurityActionControl securityActionControl = new SecurityActionControl(customer);
		    				    				Thread t = new Thread(securityActionControl);
		    				    				t.start();

		    				    			}
		    			    	}
		    			   
		    		    	}
		    			   
		    		   }else{
		    			continue;
		    		   }
	    	

		    		

	    	}
	    	//vibhu end
	    	if(mode==7){
	    	    	if(StartTime>=EndTime)
			{
	    		
		    	if(TotalCurrentTime>=StartTime || TotalCurrentTime<=EndTime)
		    	{
		    		
		    		sendNotifications(queue, generatedDeviceId, device, alertType,rule);
		    		if (security == 1) {
						Device device1 = devicemanager.getDevice(generatedDeviceId);
						GateWay gateway = gatewaymanager.getGateWayByDevice(device1);
						Customer customer = customermang.getCustomerByGateWay(gateway);
						SecurityActionControl securityActionControl = new SecurityActionControl(customer);
						Thread t = new Thread(securityActionControl);
						t.start();

					}
		    	}
		    
			}
	    	else if(StartTime<=EndTime)
	    	{
	    		
	    		if(TotalCurrentTime<=EndTime && TotalCurrentTime >=StartTime)
		    	{
	    			
	    			sendNotifications(queue, generatedDeviceId, device, alertType,rule);
	    			if (security == 1) {
	    				Device device1 = devicemanager.getDevice(generatedDeviceId);
	    				GateWay gateway = gatewaymanager.getGateWayByDevice(device1);
	    				Customer customer = customermang.getCustomerByGateWay(gateway);
	    				SecurityActionControl securityActionControl = new SecurityActionControl(customer);
	    				Thread t = new Thread(securityActionControl);
	    				t.start();

	    			}
		    	}
	    	}
	    }
	    }
	    return resultQueue;
	    
	}

/* ***************************************************************** ORIGINAL CODE **********************************************************
 * 	private Queue<KeyValuePair> checkArmStatusAndExecuteRules(
			Queue<KeyValuePair> queue, String generatedDeviceId, Device device) {
		Status armStatus = device.getArmStatus();
		if (null == armStatus
				|| !Constants.DISARM.equalsIgnoreCase(armStatus.getName())) {
			AlertNotiifier alertNotiifier = new AlertNotiifier(device);
			Thread t = new Thread(alertNotiifier);
			t.start();
		}

		Queue<KeyValuePair> resultQueue = new LinkedList<KeyValuePair>();
		resultQueue.add(new KeyValuePair(Constants.CMD_ID,
				Constants.DEVICE_ALERT_ACK));
		resultQueue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil
				.commandId(queue, Constants.TRANSACTION_ID)));
		resultQueue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil
				.commandId(queue, Constants.IMVG_ID)));
		resultQueue
				.add(new KeyValuePair(Constants.DEVICE_ID, generatedDeviceId));
		return resultQueue;
	} */
	
	/**
	 * Intimates CMS that Log has been uploaded successfully.
	 *
	 * @param	xml		java.lang.String
	 * @return		xml of java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#LogUploadSucess(java.lang.String)
	 * 				
	 */
	public String LogUploadSucess(String xml) throws Exception {
		
		DeviceManager deviceManager = new DeviceManager();
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		
		String generatedDeviceId = IMonitorUtil.commandId(queue,Constants.IMVG_ID);
		
		String filename = IMonitorUtil.commandId(queue,"FILE_NAME");
		
		String filePath = IMonitorUtil.commandId(queue,"FILE_PATH");
		
		String ImvgTimeStamp = IMonitorUtil.commandId(queue,"IMVG_TIME_STAMP");
		
		deviceManager.SaveUploadedLogDetails(generatedDeviceId,filename, filePath,ImvgTimeStamp);
		
		Queue<KeyValuePair> resultQueue = new LinkedList<KeyValuePair>();
		resultQueue.add(new KeyValuePair(Constants.CMD_ID,
				Constants.DEVICE_ALERT_ACK));
		resultQueue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil
				.commandId(queue, Constants.TRANSACTION_ID)));
		resultQueue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil
				.commandId(queue, Constants.IMVG_ID)));
		resultQueue
				.add(new KeyValuePair(Constants.DEVICE_ID, generatedDeviceId));
		XStream stream=new XStream();
		return stream.toXML(resultQueue);
		
	}
	
	/**
	 * Sends notifications if configured as part of some rule.
	 * 
	 * @param	queue	java.util.Queue<KeyValuePair>
	 * @param	generatedDeviceId	java.lang.String
	 * @param	device	in.xpeditions.jawlin.imonitor.controller.dao.entity.Device
	 * @param	alertType	in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType
	 * @param	rule	in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule
	 * @return	type	void
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#sendNotifications(java.util.Queue, java.lang.String, in.xpeditions.jawlin.imonitor.controller.dao.entity.Device, in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType, in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule)
	 * 				
	 */
	private void sendNotifications(Queue<KeyValuePair> queue, String generatedDeviceId, Device device, AlertType alertType, Rule rule ) {
		
		SimpleDateFormat time = null;
		String ruleId = null;
		String referenceTransactionId = "";
		String referenceTimeStamp = "";
		Date date = null;
		
		Status armStatus = device.getArmStatus();
		String alertEvent = IMonitorUtil.commandId(queue, Constants.ALERT_EVENT);
		
		//String transactionId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		AlertNotiifier alertNotiifier = null;
		
		if(null == armStatus|| !Constants.DISARM.equalsIgnoreCase(armStatus.getName())) 
		{
			//AlertNotiifier alertNotiifier = new AlertNotiifier(device);
			if(alertEvent.equalsIgnoreCase("IMAGE_FTP_SUCCESS")){
				//vibhu Modified so that FILE_NAME received in the message is used instead of REF_TRANSACTION_ID. This has to be done
				//as iMVG cannot send the filename created using the reference transaction id
				referenceTransactionId = IMonitorUtil.commandId(queue, Constants.FILE_NAME);
				//referenceTransactionId = IMonitorUtil.commandId(queue, Constants.REF_TRANSACTION_ID);
				referenceTimeStamp = IMonitorUtil.commandId(queue, Constants.REF_IMVG_TIME_STAMP);
				time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					date = (Date) time.parse(referenceTimeStamp);
				} catch (ParseException e) 
				{
					LogUtil.error("Error while parsing Reference Time Stamp: "+ referenceTimeStamp);
				}
				
				ruleId = IMonitorUtil.commandId(queue, Constants.RULE_ID);
				//1.Based on the REF_TRANSACTION_ID get the corresponding device
				Rule referenceRule = new RuleManager().retrieveRuleDetails(Integer.parseInt(ruleId));
				//2.Notify user with attachment.
				alertNotiifier = new AlertNotiifier(device, referenceTransactionId, referenceRule, date);
			}else if(alertEvent.equalsIgnoreCase("TEMPERATURE_CHANGE")){
				/****abhi Given the temperature value********/
				RuleManager rulemanger=new RuleManager();
				String alertvalue=IMonitorUtil.commandId(queue, Constants.TEMPERATURE_VALUE);
				int alertvalu1=Integer.parseInt(alertvalue);
				
				/****abhi Given the temperature value********/
				
				/****abhi Notify the user for temp value*****/
				String comp=rulemanger.Getdevicealertcompname(rule.getId());
		    	String[] splits = comp.split("-");
		    	String a1=splits[0];
		    	String a2=splits[1];
		    	int cond=Integer.parseInt(a1);
		    	int altval=Integer.parseInt(a2);
		    	//LogUtil.info(altval);
		    	
			    
				if(altval> alertvalu1 && cond==3){
					alertNotiifier = new AlertNotiifier(device,date,rule);
				}else if(altval < alertvalu1 && cond==2){
					alertNotiifier = new AlertNotiifier(device,date, rule);
				}else if(altval==alertvalu1 && cond==1){
					alertNotiifier = new AlertNotiifier(device,date,rule);
				}	
			
			}else if(alertEvent.equalsIgnoreCase("PMLEVEL_CHANGE")){
				/****abhi Given the Pm level value********/
				RuleManager rulemanger=new RuleManager();
				String alertvalue=IMonitorUtil.commandId(queue, Constants.PM_VALUE);
				int alertvalu1=Integer.parseInt(alertvalue);
				
				/****abhi Given the Pm level value********/
				
				/****abhi Notify the user for Pm level value*****/
				String comp=rulemanger.Getdevicealertcompname(rule.getId());
		    	String[] splits = comp.split("-");
		    	String a1=splits[0];
		    	String a2=splits[1];
		    	int cond=Integer.parseInt(a1);
		    	int altval=Integer.parseInt(a2);
		    	
		    	
			    
				if(altval> alertvalu1 && cond==3){
					alertNotiifier = new AlertNotiifier(device,date,rule);
				}else if(altval < alertvalu1 && cond==2){
					alertNotiifier = new AlertNotiifier(device,date, rule);
				}else if(altval==alertvalu1 && cond==1){
					alertNotiifier = new AlertNotiifier(device,date,rule);
				}	
			
			}
			else{
				//Notify user with only the Alert.
				//referenceTransactionId = IMonitorUtil.commandId(queue, Constants.REF_TRANSACTION_ID);
				referenceTimeStamp = IMonitorUtil.commandId(queue, "IMVG_TIME_STAMP");
				
				time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					date = (Date) time.parse(referenceTimeStamp);
				} catch (ParseException e) 
				{
					LogUtil.error("Error while parsing Reference Time Stamp: "+ referenceTimeStamp);
				}
				alertNotiifier = new AlertNotiifier(device, date,rule);
			}
			
			Thread t = new Thread(alertNotiifier);
			t.start();
		}

	}
	public String updateLUXDeviceAlert(String xml) throws Exception {
	
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		String generatedDeviceId = IMonitorUtil.commandId(queue,Constants.DEVICE_ID);
		String luxvalue = IMonitorUtil.commandId(queue,Constants.LUX_VALUE);
		DeviceManager deviceManager=new DeviceManager();
		deviceManager.updateDeviceCommandParam(generatedDeviceId, luxvalue);
		XStream stream = new XStream();
		return stream.toXML(queue);
	}
	
	public String PowerLimitReachedsuccess(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.PWR_LMT_REACHED_SUCCESS);
		long reachedlimit=1;
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		String macId = IMonitorUtil.commandId(queue,Constants.IMVG_ID);
		GatewayManager gatewaymanager=new GatewayManager();
		GateWay gateway=gatewaymanager.getGateWayByMacId(macId);
		Customer customer=gateway.getCustomer();
		DashboardManager dashboardManager = new DashboardManager();
		dashboardManager.saveReachedPowerlimit(reachedlimit,customer);
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}

	
	public String PowerLimitReachedfailure(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.PWR_LMT_REACHED_FAIL);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
				alertType);

		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	
	
	
	
	public String updateScheduleExecutionSuccess(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.SCHEDULE_EXEC_SUCCESS);
		Queue<KeyValuePair> resultQueue = sendSuccessOrFailureAfterScheduleExecution(xml,
				alertType);
        
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
		
		
	}
	
	
	public String updateScheduleExecFailure(String xml) throws Exception {
		AlertType alertType = IMonitorUtil.getAlertTypes().get(
				Constants.SCHEDULE_EXEC_FAIL);
		Queue<KeyValuePair> resultQueue = sendSuccessOrFailureAfterScheduleExecution(xml,
				alertType);
        
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
		
		
	}
	
	
	private Queue<KeyValuePair> sendSuccessOrFailureAfterScheduleExecution(String xml,
			AlertType alertType) throws ParserConfigurationException,
			SAXException, IOException, ParseException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Queue<KeyValuePair> queue = IMonitorUtil
				.extractCommandsQueueFromXml(xml);
		Queue<KeyValuePair> resultQueue = new LinkedList<KeyValuePair>();
		resultQueue.add(new KeyValuePair(Constants.CMD_ID,
				Constants.DEVICE_ALERT_ACK));
		resultQueue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil
				.commandId(queue, Constants.TRANSACTION_ID)));
		resultQueue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil
				.commandId(queue, Constants.IMVG_ID)));
		
		String imvgId = IMonitorUtil.commandId(queue,
				Constants.IMVG_ID);
		resultQueue
		.add(new KeyValuePair(Constants.DEVICE_ID, imvgId));
		String scheduleId= IMonitorUtil.commandId(queue,
				Constants.SCHEDULE_ID);
		long scID = Long.parseLong(scheduleId);
		String alertEvent = IMonitorUtil.commandId(queue, Constants.ALERT_EVENT);
		String TimeStamp=IMonitorUtil.commandId(queue,"IMVG_TIME_STAMP");
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
	    date = time.parse(TimeStamp);
	    Schedule schedule = ScheduleManager.getScheduleById(scID);
	   
	    
	    if(scheduleId != null){
	    	List<ImvgAlertNotificationForScedule> userNotificationProfiles = null;
	    	ImvgAlertNotificationManager alertNotificationManager = new ImvgAlertNotificationManager();
	    	userNotificationProfiles = alertNotificationManager.listAllAlertNotificationForSchedule(scID);
	    	for(ImvgAlertNotificationForScedule imvgAlertNotificationForScedule: userNotificationProfiles){
	    		
	    		UserNotificationProfile userNotificationProfile= imvgAlertNotificationForScedule.getUserNotificationProfile();
	    		String notifiCheck = imvgAlertNotificationForScedule.getNotificationCheck();
	    		
	    	//	LogUtil.info("user notification:  "+ stream.toXML(userNotificationProfile) + "WhatsAppCheck: "+ wsAppValue);
	    		String notificationName = userNotificationProfile.getActionType()
						.getName();
	    		String[] receipients=null;
	    		if(userNotificationProfile.getRecipient() != null)
	    			receipients = new String[] { userNotificationProfile.getRecipient() };
	    		
		        
				String[] emails = new String[] {userNotificationProfile.getEMail()};
				String[] sms = new String[] {userNotificationProfile.getSMS()};
				String[] whatsApp = new String[] {userNotificationProfile.getCountryCode()+userNotificationProfile.getWhatsApp()};
				Status status = userNotificationProfile.getStatus();
				//LogUtil.info("status name: "+ status.getName());
				schedule = imvgAlertNotificationForScedule.getSchedule();
				String messageToRecipient = null;
				if(alertEvent.equalsIgnoreCase("SCHEDULE_EXEC_SUCCESS")){
					
				    messageToRecipient = "Schedule successfully executed. ";
					messageToRecipient += "Name: '"+schedule.getName()+"'. Description: ";
					messageToRecipient += schedule.getDescription()+".\n";
				}else{
					
					 messageToRecipient = "Schedule execution failed. ";
					 messageToRecipient += "Name: '"+schedule.getName()+"'. Description: ";
					 messageToRecipient += schedule.getDescription()+".\n";
					 
				}
				    String scheduleDescription = "Schedule executed."+".\n" + schedule.getDescription();
				
				EMailNotifications eMailNotifications = new EMailNotifications();
				SmsNotifications smsNotifications = new SmsNotifications();
				if(notificationName.equalsIgnoreCase("EmailAndSMS")){
					schedule = imvgAlertNotificationForScedule.getSchedule();
					if (notifiCheck.equalsIgnoreCase("EMail")){
					try {
						eMailNotifications.notifyforschedule(messageToRecipient, emails, date, userNotificationProfile.getName());
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					}else if (notifiCheck.equalsIgnoreCase("SMS")){
						if(status.getName().equals(Constants.SMS_SUBSCRIBED)){
						smsNotifications.notifyforschedule(messageToRecipient, sms, date, userNotificationProfile.getCustomer());
						}
						
					}else if(notifiCheck.equalsIgnoreCase("EmailAndSMS")){
						try {
							eMailNotifications.notifyforschedule(messageToRecipient, emails, date, userNotificationProfile.getName());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if(status.getName().equals(Constants.SMS_SUBSCRIBED)){
						
							smsNotifications.notifyforschedule(messageToRecipient, sms, date,userNotificationProfile.getCustomer());
						}
						
					}
				}else{
					
					try 
					{
						if(receipients == null && notificationName.equalsIgnoreCase("E-Mail")){
							
							IMonitorUtil.notifyRecipients(notificationName, messageToRecipient, emails, date, userNotificationProfile.getName(),userNotificationProfile.getCustomer());
													
						}else if(receipients == null && notificationName.equalsIgnoreCase("SMS")){
							if((notifiCheck.equalsIgnoreCase("SMS")) && (status.getName().equals(Constants.SMS_SUBSCRIBED))){
							IMonitorUtil.notifyRecipients(notificationName, messageToRecipient, sms, date, userNotificationProfile.getName(),userNotificationProfile.getCustomer());
							}
							
						}else{
						
						IMonitorUtil.notifyRecipients(notificationName, messageToRecipient, receipients, date, userNotificationProfile.getName(),userNotificationProfile.getCustomer());
						
						}
					} catch (IllegalArgumentException e) {
						LogUtil.error("1.Error when notifying. " + notificationName + ","
								+ schedule.getId() + "," + schedule.getDescription());
					} catch (IllegalAccessException e) {
						LogUtil.error("2.Error when notifying. " + notificationName + ","
								+ schedule.getId() + "," + schedule.getDescription());
					} catch (InvocationTargetException e) {
						LogUtil.error("3.Error when notifying. " + notificationName + ","
								+ schedule.getId() + "," + schedule.getDescription());
					} catch (InstantiationException e) {
						LogUtil.error("4.Error when notifying. " + notificationName + " " +schedule.getId() + "," + schedule.getDescription());
					}
				}
				
				
					
					
				
				
	    	}
	    	/*scheduleNotiifier=new ScheduleNotifier(schedule, imvgId, alertEvent);*/
	    	
	    }
			

		return resultQueue;
	}
	





public String updateNewAlarmAlert(String xml) throws Exception {
	AlertType alertType = IMonitorUtil.getAlertTypes().get(
			Constants.NEW_ALARM);
	
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml,
			alertType);
	Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
	String generatedDeviceId = IMonitorUtil.commandId(queue,Constants.DEVICE_ID);
	DeviceManager deviceManager=new DeviceManager();
	String deviceStatus="1";
	deviceManager.updateDeviceCommandParam(generatedDeviceId, deviceStatus);
	XStream stream = new XStream();
	return stream.toXML(resultQueue);
}

public String verifyGateway(String xml) throws Exception {
	String result = Constants.REGISTERED;
	Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
	String imvgID = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
	GatewayManager gateWayManager = new GatewayManager();
	GateWay gateWay = gateWayManager.getGateWayByMacId(imvgID);
	if(!gateWay.equals(null)){
		String customerID = gateWayManager.getCustomerIdOfGateWay(imvgID);
		if(customerID.equals(null)){
			result= Constants.CUSTOMER_NOT_CONFIGURED;
			
		}else{
			result = Constants.UN_REGISTERED;
		}
		
	}
	
	return result;
}
	
public String updatePMDeviceAlert(String xml) throws Exception{
		XStream stream = new XStream();
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.PMLEVEL_CHANGE);
		Queue<KeyValuePair> resultQueue = updateAlertAndExecuteRule(xml, alertType);
		return stream.toXML(resultQueue);
	
}


public void checkTokenAndUpdateDeviceToAlexaEndpoint(String imvg, Device device, String deviceStatus,String mode) throws ParseException, JSONException, HTTPException{
	
	
	Alexamanager alexaManager = new Alexamanager();
	GatewayManager gatewayManager = new GatewayManager();
	
	GateWay gateway = gatewayManager.getCustomerWithReceiverIpAndPortAndFtp(imvg);
	Alexa alexaUser = alexaManager.getAlexaUserByCustomerId(gateway.getCustomer());
	
	
	if(alexaUser == null){
		
		return;
	}
	
	String expireTime = alexaUser.getTokenExpires();
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	Date expireDate = dateFormat.parse(expireTime);
	
	JSONObject alexaResponse = new JSONObject();
	//Time zone 
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
	TimeZone timeZone = TimeZone.getTimeZone("UTC");
	Calendar calendar = Calendar.getInstance(timeZone);
	sdf.setTimeZone(timeZone);
	//Time zone end
	
	if(date.after(expireDate)){
		try {
			URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_TOKEN_END_POINT));
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Host", "api.amazon.com");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		
			String parameters = "grant_type=refresh_token&refresh_token="+alexaUser.getSkillRefreshToken()+"&client_id="+ControllerProperties.getProperties().get(Constants.ALEXA_CLIENT_ID)+"&client_secret="+ControllerProperties.getProperties().get(Constants.ALEXA_SECRET);
			
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(parameters);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			
			
			//LogUtil.info(con.getHeaderField("WWW-Authenticate"));
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			JSONObject json = new JSONObject(response.toString());
			String accessToken = json.getString("access_token");
			String refreshToken = json.getString("refresh_token");
			int expires = json.getInt("expires_in");
			expires = expires/60;
			long curTimeInMs = date.getTime();
		    Date afterAddingMins = new Date(curTimeInMs + (expires * 60000));
		   
		    if(alexaUser != null){
				
				alexaUser.setSkillToken(accessToken);
				alexaUser.setSkillRefreshToken(refreshToken);
				alexaUser.setTokenExpires(dateFormat.format(afterAddingMins));
				boolean update = alexaManager.updateAlexaUser(alexaUser);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
	}// end of if statement
	
	//Prepare json message format for alexa endpoint
	
	
	JSONObject header = new JSONObject();
	header.put("messageId", UUID.randomUUID());
	header.put("namespace", "Alexa");
	header.put("name", Constants.ChangeReport);
	header.put("payloadVersion", "3");
	
	JSONObject scope = new JSONObject();
	scope.put("type", "BearerToken");
	scope.put("token", alexaUser.getSkillToken());
	JSONObject endpointobj = new JSONObject();
	endpointobj.put("scope", scope);
	endpointobj.put("endpointId", device.getDeviceId());
	
	if(device.getDeviceType().getName().equals(Constants.Z_WAVE_SWITCH)){
		
		JSONObject propertiesContent = new JSONObject();
		propertiesContent.put("namespace", "Alexa.PowerController");
		propertiesContent.put("name", "powerState");
		propertiesContent.put("value", deviceStatus);
		propertiesContent.put("timeOfSample", sdf.format(calendar.getTime())+"Z");
		propertiesContent.put("uncertaintyInMilliseconds", "0");
		
		/*JSONObject connectivityContent = new JSONObject();
		connectivityContent.put("namespace", "Alexa.EndpointHealth");
		connectivityContent.put("name", "connectivity");
		JSONObject valuecontent = new JSONObject();
		valuecontent.put("value", "OK");
		connectivityContent.put("value", valuecontent);
		connectivityContent.put("timeOfSample", sdf.format(calendar.getTime()));
		connectivityContent.put("uncertaintyInMilliseconds", "0");*/
		
		JSONArray propAr = new JSONArray();
		propAr.put(propertiesContent);
		//propAr.put(connectivityContent);
					
		JSONArray cpropAr = new JSONArray();
		//cpropAr.put(connectivityContent);
		
		JSONObject causeType = new JSONObject();
		causeType.put("type",Constants.APP_INTERACTION);
		JSONObject cause = new JSONObject();
		cause.put("cause", causeType);
		cause.put("properties", propAr);
		JSONObject payLoadObject = new JSONObject();
		payLoadObject.put("change", cause);
		
		
		
		JSONObject eventsObject = new JSONObject();
		eventsObject.put("header", header);
		eventsObject.put("endpoint", endpointobj);
		eventsObject.put("payload", payLoadObject);
		
		//Naveen added
		JSONObject contectObject = new JSONObject();
		contectObject.put("properties",cpropAr);
		
		//alexaResponse.put("context",new JSONObject());
		alexaResponse.put("context",contectObject);
		alexaResponse.put("event", eventsObject);
			
	
			
		
	}else if(device.getDeviceType().getName().equals(Constants.Z_WAVE_MULTI_SENSOR)){
		
		JSONObject propertiesContent = new JSONObject();
		propertiesContent.put("namespace", "Alexa.MotionSensor");
		propertiesContent.put("name", "detectionState");
		propertiesContent.put("value", device.getLastAlert().getAlertCommand());
		propertiesContent.put("timeOfSample", sdf.format(calendar.getTime())+"Z");
		propertiesContent.put("uncertaintyInMilliseconds", "0");
		
		JSONObject connectivityContent = new JSONObject();
		connectivityContent.put("namespace", "Alexa.EndpointHealth");
		connectivityContent.put("name", "connectivity");
		JSONObject valuecontent = new JSONObject();
		valuecontent.put("value", "OK");
		connectivityContent.put("value", valuecontent);
		connectivityContent.put("timeOfSample", sdf.format(calendar.getTime())+"Z");
		connectivityContent.put("uncertaintyInMilliseconds", "0");
		
		JSONArray propAr = new JSONArray();
		propAr.put(propertiesContent);
		propAr.put(connectivityContent);
		JSONObject causeType = new JSONObject();
		causeType.put("type",Constants.PHYSICAL_INTERACTION);
		JSONObject cause = new JSONObject();
		cause.put("cause", causeType);
		cause.put("properties", propAr);
		JSONObject payLoadObject = new JSONObject();
		payLoadObject.put("change", cause);
		
		
		
		JSONObject eventsObject = new JSONObject();
		eventsObject.put("header", header);
		eventsObject.put("endpoint", endpointobj);
		eventsObject.put("payload", payLoadObject);
		
		JSONArray contProp = new JSONArray();
		contProp.put(connectivityContent);
		JSONObject prop = new JSONObject();
		prop.put("properties", contProp);
		
		alexaResponse.put("context",prop);
		alexaResponse.put("event", eventsObject);
			
		
		
		
	}else if(device.getDeviceType().getName().equals(Constants.Z_WAVE_DOOR_SENSOR)){
		
		JSONObject propertiesContent = new JSONObject();
		propertiesContent.put("namespace", "Alexa.ContactSensor");
		propertiesContent.put("name", "detectionState");
		propertiesContent.put("value", device.getLastAlert().getAlertCommand());
		propertiesContent.put("timeOfSample", sdf.format(calendar.getTime())+"Z");
		propertiesContent.put("uncertaintyInMilliseconds", "0");
		
		JSONObject connectivityContent = new JSONObject();
		connectivityContent.put("namespace", "Alexa.EndpointHealth");
		connectivityContent.put("name", "connectivity");
		JSONObject valuecontent = new JSONObject();
		valuecontent.put("value", "OK");
		connectivityContent.put("value", valuecontent);
		connectivityContent.put("timeOfSample", sdf.format(calendar.getTime())+"Z");
		connectivityContent.put("uncertaintyInMilliseconds", "0");
		
		JSONArray propAr = new JSONArray();
		propAr.put(propertiesContent);
		propAr.put(connectivityContent);
		JSONObject causeType = new JSONObject();
		causeType.put("type",Constants.PHYSICAL_INTERACTION);
		JSONObject cause = new JSONObject();
		cause.put("cause", causeType);
		cause.put("properties", propAr);
		JSONObject payLoadObject = new JSONObject();
		payLoadObject.put("change", cause);
		
		
		
		JSONObject eventsObject = new JSONObject();
		eventsObject.put("header", header);
		eventsObject.put("endpoint", endpointobj);
		eventsObject.put("payload", payLoadObject);
		
		JSONArray contProp = new JSONArray();
		contProp.put(connectivityContent);
		JSONObject prop = new JSONObject();
		prop.put("properties", contProp);
		
		alexaResponse.put("context",prop);
		alexaResponse.put("event", eventsObject);
			
		
		
		
	}
	
	
	else if(device.getDeviceType().getName().equals(Constants.Z_WAVE_DIMMER)){
		
		JSONObject propertiesContent = new JSONObject();
		propertiesContent.put("namespace", "Alexa.BrightnessController");
		propertiesContent.put("name", "brightness");
		propertiesContent.put("value", deviceStatus);
		propertiesContent.put("timeOfSample", sdf.format(calendar.getTime())+"Z");
		propertiesContent.put("uncertaintyInMilliseconds", "0");
		
		JSONObject connectivityContent = new JSONObject();
		connectivityContent.put("namespace", "Alexa.EndpointHealth");
		connectivityContent.put("name", "connectivity");
		JSONObject valuecontent = new JSONObject();
		valuecontent.put("value", "OK");
		connectivityContent.put("value", valuecontent);
		connectivityContent.put("timeOfSample", sdf.format(calendar.getTime())+"Z");
		connectivityContent.put("uncertaintyInMilliseconds", "0");
		
		JSONArray propAr = new JSONArray();
		propAr.put(propertiesContent);
		propAr.put(connectivityContent);
		JSONObject causeType = new JSONObject();
		causeType.put("type",Constants.APP_INTERACTION);
		JSONObject cause = new JSONObject();
		cause.put("cause", causeType);
		cause.put("properties", propAr);
		JSONObject payLoadObject = new JSONObject();
		payLoadObject.put("change", cause);
		
		
		
		JSONObject eventsObject = new JSONObject();
		eventsObject.put("header", header);
		eventsObject.put("endpoint", endpointobj);
		eventsObject.put("payload", payLoadObject);
		
		JSONArray contProp = new JSONArray();
		contProp.put(connectivityContent);
		JSONObject prop = new JSONObject();
		prop.put("properties", contProp);
		
		alexaResponse.put("context",prop);
		alexaResponse.put("event", eventsObject);
			
		
		
		
	}else if(device.getDeviceType().getName().equals(Constants.Z_WAVE_AC_EXTENDER)){
		
		if(Integer.valueOf(deviceStatus) == 0){
						
			
		}else{
			String state = "ON";
			
			if(mode.equals("1")){
				mode = "HEAT";
			}else if(mode.equals("2")){
				mode = "COOL";
			}else if(mode.equals("10")){
				mode = "AUTO";
			}else if(mode.equals(null)){
				
			}
			
		
			JSONObject propertiesarray1 = new JSONObject();
			propertiesarray1.put("namespace", "Alexa.ThermostatController");
			propertiesarray1.put("name", "targetSetpoint");
			JSONObject tempValue = new JSONObject();
			tempValue.put("value", deviceStatus);
			tempValue.put("scale", "CELSIUS");
			propertiesarray1.put("value", tempValue);
			propertiesarray1.put("timeOfSample", sdf.format(calendar.getTime())+"Z");
			propertiesarray1.put("uncertaintyInMilliseconds", "0");
			
			JSONObject propertiesarray2 = new JSONObject();
			propertiesarray2.put("namespace", "Alexa.ThermostatController");
			propertiesarray2.put("name", "thermostatMode");
			propertiesarray2.put("value", mode);
			propertiesarray2.put("timeOfSample", sdf.format(calendar.getTime())+"Z");
			propertiesarray2.put("uncertaintyInMilliseconds", "0");
			
			JSONArray propAr = new JSONArray();
			propAr.put(propertiesarray1);
			//propAr.put(propertiesarray2);
			
			JSONObject contextProperties = new JSONObject();
			contextProperties.put("properties", propAr);
			
						
			JSONArray prop = new JSONArray();
			prop.put(propertiesarray2);
			prop.put(propertiesarray1);
						
			
			
			JSONObject causeType = new JSONObject();
			causeType.put("type",Constants.APP_INTERACTION);
			JSONObject cause = new JSONObject();
			cause.put("cause", causeType);
			cause.put("properties", prop);
			JSONObject payLoadObject = new JSONObject();
			payLoadObject.put("change", cause);
			
			JSONObject eventsObject = new JSONObject();
			eventsObject.put("header", header);
			eventsObject.put("endpoint", endpointobj);
			eventsObject.put("payload", payLoadObject);
			
			
		//	alexaResponse.put("context",contextProperties);
			alexaResponse.put("context",new JSONObject());
			alexaResponse.put("event", eventsObject);
			
		
			
		}
		
		
	}
	
// Send PSU for Alexa end point	
	
	//https://api.eu.amazonalexa.com/v3/events
	sendChangeReportToAlexa(alexaResponse,alexaUser);
	
	
	
	
}


public void sendChangeReportToAlexa(JSONObject alexaResponse, Alexa alexaUser){
	
	
	try {
		URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_EVENT_POINT));
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "Bearer "+alexaUser.getSkillToken());
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(alexaResponse.toString());
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		
	
		if(responseCode == 403){
			
			Alexamanager alexaManager = new Alexamanager();
			alexaManager.deleteAlexaUser(alexaUser);
			
		}
		/*BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();*/
		//LogUtil.info("response from alexa end point: " + response.toString());
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	
}


public void updateConnectivityStatusToAlexa(String imvg, Device device, String deviceStatus) throws ParseException, JSONException, HTTPException{
	
	
	
	if((!device.getDeviceType().getName().equals(Constants.Z_WAVE_SWITCH))|| (!device.getDeviceType().getName().equals(Constants.Z_WAVE_DIMMER))||(!device.getDeviceType().getName().equals(Constants.Z_WAVE_AC_EXTENDER))){
		
		return;
	}
	
	Alexamanager alexaManager = new Alexamanager();
	GatewayManager gatewayManager = new GatewayManager();
	GateWay gateway = gatewayManager.getCustomerWithReceiverIpAndPortAndFtp(imvg);
	Alexa alexaUser = alexaManager.getAlexaUserByCustomerId(gateway.getCustomer());
	
	
	if(alexaUser == null){
		
		return;
	}
	
	String expireTime = alexaUser.getTokenExpires();
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	Date expireDate = dateFormat.parse(expireTime);

	JSONObject alexaResponse = new JSONObject();
	//TimeZone tz = TimeZone.getDefault();
	
	
	
	//Time zone 
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
	TimeZone timeZone = TimeZone.getTimeZone("UTC");
	Calendar calendar = Calendar.getInstance(timeZone);
	
	sdf.setTimeZone(timeZone);
	
	
	//Time zone end
	
	
	
	
	
	
	
	if(date.after(expireDate)){
		
		try {
			URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_TOKEN_END_POINT));
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Host", "api.amazon.com");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			String parameters = "grant_type=refresh_token&refresh_token="+alexaUser.getSkillRefreshToken()+"&client_id="+ControllerProperties.getProperties().get(Constants.ALEXA_CLIENT_ID)+"&client_secret="+ControllerProperties.getProperties().get(Constants.ALEXA_SECRET);
			
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(parameters);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			
			
			//LogUtil.info(con.getHeaderField("WWW-Authenticate"));
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			JSONObject json = new JSONObject(response.toString());
			String accessToken = json.getString("access_token");
			String refreshToken = json.getString("refresh_token");
			int expires = json.getInt("expires_in");
			expires = expires/60;
			long curTimeInMs = date.getTime();
		    Date afterAddingMins = new Date(curTimeInMs + (expires * 60000));
		   
		    if(alexaUser != null){
				
				alexaUser.setSkillToken(accessToken);
				alexaUser.setSkillRefreshToken(refreshToken);
				alexaUser.setTokenExpires(dateFormat.format(afterAddingMins));
				boolean update = alexaManager.updateAlexaUser(alexaUser);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
	}// end of if statement
	
	//Prepare json message format for alexa endpoint
	
	
	JSONObject header = new JSONObject();
	header.put("messageId", UUID.randomUUID());
	header.put("namespace", "Alexa");
	header.put("name", Constants.ChangeReport);
	header.put("payloadVersion", "3");
	
	JSONObject scope = new JSONObject();
	scope.put("type", "BearerToken");
	scope.put("token", alexaUser.getSkillToken());
	JSONObject endpointobj = new JSONObject();
	endpointobj.put("scope", scope);
	endpointobj.put("endpointId", device.getDeviceId());
	
	
		
		JSONObject propertiesContent = new JSONObject();
		propertiesContent.put("namespace", "Alexa.EndpointHealth");
		propertiesContent.put("name", "connectivity");
		
		JSONObject value = new JSONObject();
		
		if(deviceStatus.equals(Constants.DEVICE_DOWN)){
			value.put("value",Constants.UNREACHABLE);
		}else{
			value.put("value","OK");
		}
		propertiesContent.put("value", value);
		propertiesContent.put("timeOfSample", sdf.format(calendar.getTime())+"Z");
		propertiesContent.put("uncertaintyInMilliseconds", "0");
		

		
		JSONArray propAr = new JSONArray();
		propAr.put(propertiesContent);
		
					
		JSONArray cpropAr = new JSONArray();
		
		
		JSONObject causeType = new JSONObject();
		causeType.put("type",Constants.APP_INTERACTION);
		JSONObject cause = new JSONObject();
		cause.put("cause", causeType);
		cause.put("properties", propAr);
		JSONObject payLoadObject = new JSONObject();
		payLoadObject.put("change", cause);
		
		
		
		JSONObject eventsObject = new JSONObject();
		eventsObject.put("header", header);
		eventsObject.put("endpoint", endpointobj);
		eventsObject.put("payload", payLoadObject);
		
		//Naveen added
		JSONObject contectObject = new JSONObject();
		contectObject.put("properties",cpropAr);
		
		
		alexaResponse.put("context",contectObject);
		alexaResponse.put("event", eventsObject);
			
		
			
	
	

	sendChangeReportToAlexa(alexaResponse,alexaUser);
}

/**
 * @author naveen
 * @param xml java.lang.String
 * @throws Exception
 */
public void updateSyncDeviceStatus(String xml) throws Exception {
	
	Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);	
	String macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
	Document doc = XmlUtil.getDocument(xml);
	NodeList nodes = doc.getElementsByTagName("field");
	DeviceManager deviceManager = new DeviceManager();
	Device device = null;
	AlertType alertType = null;
	acConfigurationManager acConfigManager = new acConfigurationManager();
	try {
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			
			NodeList keys=element.getElementsByTagName("key");
			Element line1=(Element)keys.item(0);
			String key=XmlUtil.getCharacterDataFromElement(line1);
			NodeList values = element.getElementsByTagName("value");
			Element line2 = (Element) values.item(0);
			String value=XmlUtil.getCharacterDataFromElement(line2);
			
			if(key.equals("DID")) {	
				String[] val = value.split(":");
			
				device = deviceManager.getDeviceByGeneratedDeviceId(macId+"-"+val[0]);
				if (device!=null)
				{
					
				if(device.getDeviceType().getName().equals(Constants.Z_WAVE_AC_EXTENDER)) {
					//acConfiguration acConfig = (acConfiguration) device.getDeviceConfiguration();
					DeviceConfiguration configuration=device.getDeviceConfiguration();
					
					acConfigurationManager acConfigurationManager=new acConfigurationManager();
					acConfiguration acConfig=acConfigurationManager.getacConfigurationById(configuration.getId());
					acConfig.setSensedTemperature(val[2]);
					acConfigManager.updateacConfiguration(acConfig);
					device.setDeviceConfiguration(acConfig);
					if(val[1].equals("1"))
				    alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_UP);
					else
					alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_DOWN);
					
					device.setLastAlert(alertType);
					boolean result= deviceManager.updateDevice(device);
					
				}else {
					device.setCommandParam(val[2]);
					    if(val[1].equals("1"))
					    alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_UP);
						else
						alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_DOWN);
						
						device.setLastAlert(alertType);
					    boolean result=deviceManager.updateDevice(device);
					   
				}	
			}
					
			}//end if loop	
		}//end for loop
	} catch (Exception e) {
		// TODO: handle exception
		LogUtil.info("could not update sync status: " + e.getMessage());
	}
	
	 
	
}


/*Added by Naveen
 * For updating Modbus status and error codes
 * 
 * */

public void updateModBusStatus(String xml) throws Exception {
	
	
	DeviceManager deviceManager = new DeviceManager();
	Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
	String errorCode = IMonitorUtil.commandId(queue,Constants.ERROR_CODE_VALUE);
	String generatedDeviceId = IMonitorUtil.commandId(queue,Constants.DEVICE_ID);
	String filterSignStatus= IMonitorUtil.commandId(queue,Constants.FILTER_SIGN_STATUS);
	String SensedTemp=IMonitorUtil.commandId(queue,Constants.ROOMTEMP);
	
	int filterValue=Integer.parseInt(filterSignStatus);
	DeviceConfigurationManager deviceConfigurationManager = new DeviceConfigurationManager();
	Device device = deviceManager.getDeviceByGeneratedDeviceId(generatedDeviceId);
	IndoorUnitConfiguration config = (IndoorUnitConfiguration) deviceConfigurationManager.getDeviceConfigurationById(device.getDeviceConfiguration().getId());
	config.setFilterSignStatus(filterValue);
	config.setSensedTemperature(SensedTemp);
	ModbusConstants errorCodes = new ModbusConstants();
	String errorMesssage=errorCodes.errormap.get(errorCode);
	//String errorMesssage=ModbusConstants.errormap.get(errorCode);
	
	//errorCodes.errormap<Object,String>;
	config.setErrorMessage(errorMesssage);
	boolean res=deviceConfigurationManager.updateIduConfiguration(config);
	
	
	
	
}

public void upDateImageFTPSuccessForDevice(String customerId,String generatedDeviceId,String imageName,String timeStamp,String ruleId) {
	AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.IMAGE_FTP_SUCCESS);
	DeviceManager deviceManager = new DeviceManager();
	String filepath = customerId+"/images/"+imageName; 
	
	SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = null;
	
		try {
			date = time.parse(timeStamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	Device device = deviceManager.saveAlertAndCheckArm(generatedDeviceId,alertType, filepath, date);
	
	checkArmStatusAndExecuteRulesForZing(generatedDeviceId, device,alertType,imageName,ruleId);
}

//************************************************* Naveen start: IMAGE ATTACHMENT from zing gateway for email notification ***************************************
	/**
	 * Checks for arm status. If device is arm-ed, executes rule.
	 * 
	 * @param	generatedDeviceId	java.lang.String
	 * @param	device	in.xpeditions.jawlin.imonitor.controller.dao.entity.Device
	 * @param	alertType	in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType
	 * @return	type	java.util.Queue<KeyValuePair>
	 * @see		in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager#checkArmStatusAndExecuteRules(java.util.Queue, java.lang.String, in.xpeditions.jawlin.imonitor.controller.dao.entity.Device, in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType)
	 * 				
	 */
	@SuppressWarnings("unused")
	private void checkArmStatusAndExecuteRulesForZing(String generatedDeviceId, Device device, AlertType alertType,String fileName,String ruleId) {
		
		AlertsFromImvgManager alertsmanager=new AlertsFromImvgManager();
		DeviceManager devicemanager=new DeviceManager();
		GatewayManager gatewaymanager=new GatewayManager();
		CustomerManager customermang=new CustomerManager();
		AlertMonitor alertmonitor=new AlertMonitor();
		Date date=new Date();
		RuleManager rulemanger=new RuleManager();
	
		try {
			Set<DeviceAlert> deviceAlerts=new HashSet<DeviceAlert>();
			deviceAlerts=rulemanger.GetRuleStartAndEndTimeByDeviceId(device,alertType,Integer.parseInt(ruleId));
			SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");
		    Date now = new Date();
		    String strDate = sdfDate.format(now);
		   
		    String[] Temp=strDate.split(":");
		   
		    long NwStart=Integer.parseInt(Temp[0]);
			long NwEnd=Integer.parseInt(Temp[1]);
			long TotalCurrentTime=(NwStart*60)+NwEnd;
		    if(deviceAlerts != null) {
		    	
		   
		    for(DeviceAlert Devicealert : deviceAlerts)
		    {
		    	
		    	long StartTime=Devicealert.getStartTime();
		    	long EndTime=Devicealert.getEndTime();
		    	Rule rule=Devicealert.getRule();
		    	
		    	
		    	
		    	int alert=rule.getAlert();
		    	
		    	
		    
		    	
		    	byte mode=rule.getMode();
		    	if(mode != 8 && mode != 7)
		    	{
		    		Rule ruleWithGW = rulemanger.retrieveRuleDetails(rule.getId());
		    		String curMode = ruleWithGW.getGateWay().getCurrentMode();
		    		
		    		if(
			    			(curMode.equals("2") && mode!=2 && mode !=4 && mode != 6 && mode != 7 && mode != 8 )
				    		   ||	(curMode.equals("1") && mode != 1 && mode != 4 && mode != 5 && mode != 7 && mode != 8)
				    		   ||	(curMode.equals("0") && mode != 8 && mode != 7 && mode != 3 && mode != 1 &&mode != 2)
			    		   ){
		    			
			    			   
			    		    	if(StartTime>=EndTime)
			    				{
			    			    	if(TotalCurrentTime>=StartTime || TotalCurrentTime<=EndTime)
			    			    	{
			    			    		
			    			    		sendImageNotifications(generatedDeviceId, device, alertType,rule,fileName);
			    			
			    			    	}
			    			    }
			    			    
			    				
			    		    	else if(StartTime<=EndTime)
			    		    	{
			    		    		
			    		    		if(TotalCurrentTime<=EndTime && TotalCurrentTime >=StartTime)
			    			    	{
			    		    			
			    		    			
			    		    			sendImageNotifications(generatedDeviceId, device, alertType,rule,fileName);
			    				    	
			    			    	}
			    			   
			    		    	}
			    			   
			    		   }else{
			    			continue;
			    		   }
		    	

			    		

		    	}
		    	//vibhu end
		    	if(mode==7){
		    	    	if(StartTime>=EndTime)
				{
		    		
			    	if(TotalCurrentTime>=StartTime || TotalCurrentTime<=EndTime)
			    	{
			    		
			    		sendImageNotifications(generatedDeviceId, device, alertType,rule,fileName);
			    	}
			    
				}
		    	else if(StartTime<=EndTime)
		    	{
		    		
		    		if(TotalCurrentTime<=EndTime && TotalCurrentTime >=StartTime)
			    	{
		    			
		    			sendImageNotifications(generatedDeviceId, device, alertType,rule,fileName);
			    	}
		    	}
		    }
		    }
		}
		} catch (Exception e) {

LogUtil.info("could not send image: " + e.getMessage());
		}
		
	   
	    
	}
	
private void sendImageNotifications(String generatedDeviceId, Device device, AlertType alertType, Rule rule , String fileName) {
		
		SimpleDateFormat time = null;
		String referenceTimeStamp = "";
		Date date = null;
		Status armStatus = device.getArmStatus();
		AlertNotiifier alertNotiifier = null;
		
		if(null == armStatus|| !Constants.DISARM.equalsIgnoreCase(armStatus.getName())) 
		{
			
				
				SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");
			    Date now = new Date();
			    String strDate = sdfDate.format(now);
				time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					date = (Date) time.parse(strDate);
				} catch (ParseException e) 
				{
					LogUtil.error("Error while parsing Reference Time Stamp: "+ referenceTimeStamp);
				}
				
				
				//1.Based on the REF_TRANSACTION_ID get the corresponding device
				Rule referenceRule = new RuleManager().retrieveRuleDetails(rule.getId());
				//2.Notify user with attachment.
				alertNotiifier = new AlertNotiifier(device, fileName, referenceRule, date);
			
			
			
			Thread t = new Thread(alertNotiifier);
			t.start();
		}

	}

/*Naveen Added
 * To send push notification when door is opened after schedule execution
 */
public void updateDoorOpenStatus(String xml) throws Exception{
	
	AlertType alertType = IMonitorUtil.getAlertTypes().get(
			Constants.DOOR_IS_OPENED);
	
	Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
	DeviceManager deviceManager=new DeviceManager();
	CustomerManager customerManager = new CustomerManager();
	String TimeStamp=IMonitorUtil.commandId(queue,"IMVG_TIME_STAMP");
	String macId=IMonitorUtil.commandId(queue,"IMVG_ID");
	Customer customer = customerManager.getCustomerByMacId(macId);
	SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date();
	try {
		date = time.parse(TimeStamp);
		}catch (ParseException e){
			LogUtil.info("could not parse IMVG time stamp");
		}
	
	String generatedDeviceIdArray = IMonitorUtil.commandId(queue,Constants.DEVICE_ID);
	Device device = null;
	String[] deviceIdArray = null;
	
	if(generatedDeviceIdArray.contains(",")){
		deviceIdArray = generatedDeviceIdArray.split(",");
		for (String generatedDeviceId: deviceIdArray ) {
		device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceId);
		deviceManager.updateAlertsFromImvg(device.getId(),alertType,date,device,null);
		PushNotificationsService pushNotificationsService= new PushNotificationsService(alertType, customer,device);
		Thread thread = new Thread(pushNotificationsService);
		thread.start();
		}
	}else {
		
		device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceIdArray);
		deviceManager.updateAlertsFromImvg(device.getId(),alertType,date,device,null);
		
		PushNotificationsService pushNotificationsService= new PushNotificationsService(alertType, customer,device);
		Thread thread = new Thread(pushNotificationsService);
		thread.start();
	}
	
} 
}
