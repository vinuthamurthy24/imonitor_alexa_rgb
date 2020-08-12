/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.DiagnosticInfoAction;
import in.xpeditions.jawlin.imonitor.controller.action.ExcuteUseCommandAction;
import in.xpeditions.jawlin.imonitor.controller.action.FirmwareUpgradeAction;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.action.ImvgLogs;
import in.xpeditions.jawlin.imonitor.controller.action.KeepAliveAction;
import in.xpeditions.jawlin.imonitor.controller.action.RemoveFailnodeAction;
import in.xpeditions.jawlin.imonitor.controller.communication.AlertNotiifier;
import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.control.DeviceDiscvoerController;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AdminSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertsFromImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceTypeModel;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GatewayStatus;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.H264CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MinimoteConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ModbusSlave;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MotorConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.NewDeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SceneControllerConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SceneControllerMake;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.WallmoteConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ZingGatewayList;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AgentManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AlertTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AlertsFromImvgManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.Alexamanager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceConfigurationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.IconManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.LocationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.SceneControllerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.StatusManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.SuperUserManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.SystemAlertManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.EMailNotifications;
import in.xpeditions.jawlin.imonitor.controller.notifications.SmsNotifications;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

import imonitor.cms.alexa.proactive.service.AlexaProactiveUpdateService;



public class GatewayServiceManager {

	public String register(String xml) {

		Map<String, String> commands = new HashMap<String, String>();
		XStream xStream = new XStream();
		String imvgd = (String) xStream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(imvgd);
		String customerId = gatewayManager.getCustomerIdOfGateWay(imvgd);

		if (gateWay == null) {
			commands.put("status", "failure");
			commands.put("failurereason", Constants.INVALID_IMVG_MAC);
		} else if (gateWay.getStatus().getName().compareTo(
				Constants.READY_TO_REGISTER) != 0) {
			commands.put("status", "failure");
			commands.put("failurereason", Constants.ALREADY_REGISTERED);
		}
		//vibhu added else if
		else if (customerId == null || customerId.isEmpty()){
			commands.put("status", "failure");
			commands.put("failurereason", Constants.CUSTOMER_NOT_CONFIGURED);
		}
		else {
			//sumit start
			//1. Call a method in gatewayManager to create SYSTEM DEVICES.
			Boolean result = gatewayManager.createSystemDevices(gateWay);
			if(result)
			{
			//sumit end
			commands.put("status", "success");
			// Agent agent = findAppropriateAgent();
			Agent agent = gateWay.getAgent();
			commands.put("primaryip", agent.getIp());
			commands.put("secondaryip", agent.getIp());
			commands.put("primaryport", Integer.toString(agent
					.getReceiverPort()));
			commands.put("secondaryport", Integer.toString(agent
					.getReceiverPort()));
			StatusManager statusManager = new StatusManager();
			Status status = statusManager.getStatusByName(Constants.OFFLINE);
			gateWay.setStatus(status);
			gateWay.setAgent(agent);
			gatewayManager.updateGateWay(gateWay);
			//sumit start
			}
			else
			{
			commands.put("status", "failure");
			commands.put("failurereason", "Could not add virtual devices");//vibhu TBD put message in constants
			}
			//sumit end
		}
		String valueInXml = IMonitorUtil.convertToXml(commands);
		return valueInXml;
	}

	/**
	 * 
	 * @param xml java.lang.String
	 * @param xml1 java.lang.String
	 * @return result java.lang.String
	 */
	public String setDeviceDiscoveryMode(String xml,String xml1,String xml2) {
		String result = new String();
		XStream xStream = new XStream();
		String macId = (String) xStream.fromXML(xml);
		Boolean mode = (Boolean) xStream.fromXML(xml1);
		String checkedDevice=(String) xStream.fromXML(xml2);  //Apoorva
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(macId);
		if (gateWay == null) {
			result = "failure";
		} else
		{
			String gatewayStatus=gateWay.getStatus().getName();
		
			
			if (gatewayStatus.equalsIgnoreCase("Online"))
			{
				
			result = "success";
			RequestProcessor requestProcessor = new RequestProcessor();

			Queue<KeyValuePair> messages = new LinkedList<KeyValuePair>();
			messages.add(new KeyValuePair(Constants.CMD_ID,
					Constants.DISCOVER_DEVICES));
			String tId = IMonitorUtil.generateTransactionId();
			messages.add(new KeyValuePair(Constants.TRANSACTION_ID, tId));
			messages.add(new KeyValuePair(Constants.IMVG_ID, macId));
			messages.add(new KeyValuePair(Constants.DISCOVERY_DURATION, ControllerProperties.getProperties().get(Constants.DISCOVERY_DURATION)));
			
			//Diaken(VIA Discovery)
			if ((checkedDevice.equals("VIA")))
			{
				
				messages.add(new KeyValuePair(Constants.DISCOVERY_MODE, "3"));
				String messageInXml = IMonitorUtil.convertQueueIntoXml(messages);
				requestProcessor.processRequest(messageInXml, gateWay);
				long discoveryTimeOut = Long.parseLong(ControllerProperties.getProperties().get(Constants.DISCOVERY_DURATION)) * 1000;
				DeviceDiscvoerController controller = new DeviceDiscvoerController(discoveryTimeOut);
				controller.setGateWay(gateWay);
				Thread t = new Thread(controller);
				IMonitorSession.put(macId, t);
				t.start();
			}
			else
			{
				if(mode){
					messages.add(new KeyValuePair(Constants.DISCOVERY_MODE, "0"));
				}else{
					messages.add(new KeyValuePair(Constants.DISCOVERY_MODE, "1"));
				}
				String messageInXml = IMonitorUtil.convertQueueIntoXml(messages);
				requestProcessor.processRequest(messageInXml, gateWay);
				long discoveryTimeOut = Long.parseLong(ControllerProperties.getProperties().get(Constants.DISCOVERY_DURATION)) * 1000;
				DeviceDiscvoerController controller = new DeviceDiscvoerController(discoveryTimeOut);
				controller.setGateWay(gateWay);
				Thread t = new Thread(controller);
				IMonitorSession.put(macId, t);
				t.start();
			}
			
		}
			else 
			{
				
				result="offline";
			}
			
		}
		return result;
	}

	/**
	 * 
	 * @param xml java.lang.String
	 * @param xml1 java.lang.String
	 * @return result java.lang.String
	 */
	public String setAbortMode(String xml, String xml1) {
		String result = new String();
		XStream xStream = new XStream();
		String macId = (String) xStream.fromXML(xml);
		String status = (String) xStream.fromXML(xml1);
		//Boolean mode = (Boolean) xStream.fromXML(xml1);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(macId);
		if (gateWay == null) {
			result = "failure";
		} else {

			result = "success";
			RequestProcessor requestProcessor = new RequestProcessor();

			Queue<KeyValuePair> messages = new LinkedList<KeyValuePair>();
			messages.add(new KeyValuePair(Constants.CMD_ID,
					"ABORT_MODE"));
			String tId = IMonitorUtil.generateTransactionId();
			messages.add(new KeyValuePair(Constants.TRANSACTION_ID, tId));
			messages.add(new KeyValuePair(Constants.IMVG_ID, macId));
			
			if(status.equalsIgnoreCase("Device Discovery Mode")){
				messages.add(new KeyValuePair("ABORT", "DISCOVERY_MODE"));
			}else {
				messages.add(new KeyValuePair("ABORT", "REMOVAL_MODE"));
			}
			
			String messageInXml = IMonitorUtil.convertQueueIntoXml(messages);
			requestProcessor.processRequest(messageInXml, gateWay);
			//long discoveryTimeOut = Long.parseLong(ControllerProperties.getProperties().get(Constants.DISCOVERY_DURATION)) * 1000;
			DeviceDiscvoerController controller = new DeviceDiscvoerController(0);
			controller.setGateWay(gateWay);
			Thread t = new Thread(controller);
			IMonitorSession.put(macId, t);
			t.start();
		}
		return result;
	}

	public String changeDeviceDiscoveryMode(String xml)
			throws ParserConfigurationException, SAXException, IOException {

		GatewayManager gatewayManager = new GatewayManager();
		Queue<KeyValuePair> message = new LinkedList<KeyValuePair>();
		message = IMonitorUtil.extractCommandsQueueFromXml(xml);
		String macId = IMonitorUtil.commandId(message, Constants.IMVG_ID);
		gatewayManager.updateGateWayToStatusDeviceDiscovery(macId);
		return "SUCCESS";
	}

	/**
	 * 
	 * @author sumit
	 */
	public void waitForDeviceRemoveTimeOut(String macId){
		try {
			GatewayManager gatewayManager = new GatewayManager();
			GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(macId);
			String deviceRemoveTimeOut = ControllerProperties.getProperties().get(Constants.DEVICE_REMOVE_TIMEOUT_DURATION);
			long timeout = Long.parseLong(deviceRemoveTimeOut);
			timeout = timeout * 1000;
			DeviceDiscvoerController controller = new DeviceDiscvoerController(timeout);
			controller.setGateWay(gateWay);
			Thread t = new Thread(controller);
			IMonitorSession.put(macId, t);
			t.start();
		} catch (NumberFormatException e) {
			LogUtil.info("NumberFormatException:", e);
		} catch (Exception e) {
			LogUtil.info("Got Exception:", e);
		}
	}


	/**
	 * @author sumit
	 * @param xml java.lang.String
	 * @return valueInXml java.lang.String
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public String removeDeviceStopper(String xml) 
			throws IOException, SAXException, ParserConfigurationException{
		String valueInXml = null;
		String macId = "";
		try {
			Map<String, String> commands = new HashMap<String, String>();
			Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
			macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
			String stopReason = IMonitorUtil.commandId(queue, Constants.STOP_REASON);
			if(stopReason.equalsIgnoreCase(Constants.DEVICE_REMOVE_TIMED_OUT) || stopReason.equalsIgnoreCase(Constants.DEVICE_REMOVE_DONE)
					|| stopReason.equalsIgnoreCase(Constants.DEVICE_REMOVE_FAILED)){
				//1. Update Gateway to Online.
				Thread t = (Thread) IMonitorSession.get(macId);
				if(t != null){
					t.interrupt();
				}
//				GatewayManager gatewayManager = new GatewayManager();
//				GateWay gateWay = gatewayManager.getGateWayByMacId(macId);
//				gatewayManager.updateGateWayToOnline(gateWay.getId());
				//2. Return DEVICE_REMOVE_STOP_SUCCESS to iMVG
				commands.put(Constants.CMD_ID, Constants.DEVICE_REMOVE_STOP_SUCCESS);
				commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID));
				commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue, Constants.IMVG_ID));
				valueInXml = IMonitorUtil.convertToXml(commands);
			}
		} catch (IOException e) {
			LogUtil.info("IOException: ", e);
		} catch (SAXException e) {
			LogUtil.info("SAXException: ", e);
		} catch (ParserConfigurationException e) {
			LogUtil.info("ParserConfigurationException: ", e);
		}
		return valueInXml;
	}

	/**
	 * @author vibhu
	 * @param xml java.lang.String
	 * @return valueInXml java.lang.String
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public String removeAllDevicesSuccess(String xml) 
			throws IOException, SAXException, ParserConfigurationException{

		try {
			Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
			String macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
			Thread t = (Thread) IMonitorSession.get(macId);
			if(t != null){
				t.interrupt();
			}

			//Delete all the devices for the gateway. Except virtual devices.
			new GatewayManager().deleteDevicesOfGateWay(macId);

		} catch (IOException e) {
			LogUtil.info("IOException: ", e);
		} catch (SAXException e) {
			LogUtil.info("SAXException: ", e);
		} catch (ParserConfigurationException e) {
			LogUtil.info("ParserConfigurationException: ", e);
		}
		return null;
	}

	/**
	 * @author vibhu
	 * @param xml java.lang.String
	 * @return valueInXml java.lang.String
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public String removeAllDevicesFailure(String xml) 
			throws IOException, SAXException, ParserConfigurationException{
		try {
			Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
			String macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
			Thread t = (Thread) IMonitorSession.get(macId);
			if(t != null){
				t.interrupt();
			}
			
		} catch (IOException e) {
			LogUtil.info("IOException: ", e);
		} catch (SAXException e) {
			LogUtil.info("SAXException: ", e);
		} catch (ParserConfigurationException e) {
			LogUtil.info("ParserConfigurationException: ", e);
		}
		return null;
	}
	public String saveGateWay(String xml) {
		String result = "no";
		XStream xStream = new XStream();
		try {
			GateWay gateWay = (GateWay) xStream.fromXML(xml);
			if(gateWay.getMaintenancemode() == null)
			{
				gateWay.setMaintenancemode("0");
			}
			GatewayManager gatewayManager = new GatewayManager();
			GateWay gateWay1 = gatewayManager.getGateWayByMacId(gateWay
					.getMacId());
			if (gateWay1 == null) {
				if (gatewayManager.saveGateWay(gateWay)) {
					result = "yes";
				}
			} else
				result = "already exist";
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return xStream.toXML(result);
	}

	public String deleteGateWay(String xml) {
		String result = "no";
		XStream xStream = new XStream();
		long id = (Long) xStream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayById(id);
		DeviceManager deviceManager = new DeviceManager();
		List<Device> devices = deviceManager.getDeviceByGateWay(gateWay);
		for (Device device : devices) {
			deviceManager.deleteDevice(device);
		}

		if (gatewayManager.deleteGateWay(gateWay)) {
			result = "yes";
		}
		return result;

	}

	public String deleteGateWayById(String xml) {
		XStream xStream = new XStream();
		String gateWayMacId = (String) xStream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayByMacId(gateWayMacId);
		gatewayManager.deleteGateWay(gateWay);
		return "success";
	}

	public String updateGateWay(String xml) throws SecurityException,
			DOMException, IllegalArgumentException,
			ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		String result = "no";
		XStream xStream = new XStream();
		GateWay gateWay = (GateWay) xStream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWayFromDb = gatewayManager.getGateWayById(gateWay.getId());
		gateWay.setDevices(gateWayFromDb.getDevices());
		gateWay.setGateWayType(gateWayFromDb.getGateWayType());
		gateWay.setCustomer(gateWayFromDb.getCustomer());
		gateWay.setCurrentMode(gateWayFromDb.getCurrentMode());
		gateWay.setDelayHome(gateWayFromDb.getDelayHome());
		gateWay.setDelayStay(gateWayFromDb.getDelayStay());
		gateWay.setDelayAway(gateWayFromDb.getDelayAway());
		gateWay.setMaintenancemode(gateWayFromDb.getMaintenancemode());


		if (gatewayManager.updateGateWay(gateWay)) {
			result = "yes";
		}
		return result;
	}

	public String listGateWay() throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		XStream xStream = new XStream();
		GatewayManager gatewayManager = new GatewayManager();
		List<GateWay> gateWays = gatewayManager.listOfGateways();
		String xml = xStream.toXML(gateWays);
		return xml;
	}

	public String getGateWayByMacId(String xml) {
		XStream xStream = new XStream();
		String macId = (String) xStream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayByMacId(macId);
		return xStream.toXML(gateWay);
	}

	public String handleImvgUp(String xml, String versionxml) {
		
		return updateImvgStatus(xml, Constants.ONLINE, versionxml);
	}

	private String updateImvgStatus(String xml, String statusName, String versionxml) {
		XStream xStream = new XStream();
		String macId = (String) xStream.fromXML(xml);
		String gateWayVersion = null;
		if (versionxml!= null) gateWayVersion = (String) xStream.fromXML(versionxml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayByMacId(macId);
		
		//Check the current status of the gateway and send failure if required.
		StatusManager statusManager = new StatusManager();
		String result ="error";
		Status status = statusManager.getStatusByName(statusName);
		gateWay.setStatus(status);
		if(gateWayVersion!=null){
			gateWay.setGateWayVersion(gateWayVersion);
		}		
		boolean update = gatewayManager.updateGateWay(gateWay);
		
		//Naveen Added on 13th december 2017
		// to store online/offline time of gateway
		GatewayStatus gatewayStatus = new GatewayStatus();
		gatewayStatus.setGateway(gateWay);
		gatewayStatus.setStatus(status);
		Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = formatter.format(currentDate.getTime());
		gatewayStatus.setAlertTime(dateNow);
		
		String lastStatus = gatewayManager.getLastStatusOfGateway(gateWay);
		
		if((statusName != lastStatus)&& update){
			gatewayManager.saveGatewayStatus(gatewayStatus);
			try {
				sendImvgNotoficationToUser(statusName,gateWay.getMacId());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}else{
		boolean statusUpdate = gatewayManager.saveGatewayStatus(gatewayStatus);
		
		}
		
		
		
			 result = "success";
		
		return xStream.toXML(result);
	}
	/*private String updateImvgStatus(String xml, String statusName, String versionxml) {
		XStream xStream = new XStream();
		String macId = (String) xStream.fromXML(xml);
		String gateWayVersion = null;
		if (versionxml!= null) gateWayVersion = (String) xStream.fromXML(versionxml);
		GatewayManager gatewayManager = new GatewayManager();
		CustomerManager customerManager = new CustomerManager();
		UserManager userManager = new UserManager();
		GateWay gateWay = gatewayManager.getGateWayByMacId(macId);
		Customer customer = customerManager.getCustomerByGateWay(gateWay); 
//		Check the current status of the gateway and send failure if required.
		StatusManager statusManager = new StatusManager();
		String result ="error";
		Status status = statusManager.getStatusByName(statusName);
		gateWay.setStatus(status);
		if(gateWayVersion!=null){
			gateWay.setGateWayVersion(gateWayVersion);
		}		
		
		if(gatewayManager.updateGateWay(gateWay));{
			SystemAlertManager systemAlertManager = new SystemAlertManager();
			List<Object[]> userNotifications = userManager.getMainUserNotifications(customer.getId(),Constants.MAIN_USER);
			
			SystemAlert systemAlert = null;
			if(statusName.equals(Constants.ONLINE)){
				 systemAlert = systemAlertManager.getSystemAlertByName(Constants.IMVG_ON);
			}
			if(statusName.equals(Constants.OFFLINE)){
				 systemAlert = systemAlertManager.getSystemAlertByName(Constants.IMVG_OFF);
			}
			if(userNotifications.size()>0){
			for(Object[] notification:userNotifications){
				
				//String message ="";
			
				List<Object[]> systemAlertByUser =  systemAlertManager.getSystemAlertByUserAndAlertId(notification[4].toString(),systemAlert.getId());
				
						if(systemAlertByUser != null){
						
				//	message += IMonitorUtil.generateMailMessage("IMVG is:"+statusName,notification[3].toString(),"", macId.toString(),notification[2].toString());
							
					
				*//***
				 *KanthaRaj Changed To Notify To Customer,Insted of MainUser For System Alerts. 
				 * 
				 * String[] recipiantsBYsms ={notification[1].toString()};
				 String[] recipiantsBYmail ={notification[0].toString()};*//*
							
				*//**
				 * KanthaRaj Changed To Notify To MainUser,Insted of Customer For System Alerts.to resolve bug-1039
				 * on 15-04-2013
				 * 
				 * String[] recipiantsBYsms ={notification[5].toString()};
				 * String[] recipiantsBYmail ={notification[6].toString()};
				 * 
				 * **//*
					
					String[] recipiantsBYsms ={notification[1].toString()};
					String[] recipiantsBYmail ={notification[0].toString()};
							 for (Object[] object : systemAlertByUser) {
								 if(object[1].equals(Constants.SEND_SMS)){
								 SmsNotifications notifications = new SmsNotifications();
								 notifications.notify("Your MasterController is "+statusName,recipiantsBYsms, null,customer);
							 }
								 if(object[1].equals(Constants.SEND_EMAIL)){
								 EMailNotifications eMailNotifications = new EMailNotifications();
								 eMailNotifications.notify("Your MasterController is "+statusName,recipiantsBYmail, null, notification[3].toString());
							 }
								 if(object[1].equals(Constants.SEND_WHATSAPP)){
									 SmsNotifications notifications = new SmsNotifications();
									 notifications.notifyForWhatsApp("Your MasterController is "+statusName,recipiantsBYsms, null);
								 }
							}
						}
				}
			}
			 result = "success";
		}
		return xStream.toXML(result);
	}*/
	
	public String handleImvgDown(String xml) {
		return updateImvgStatus(xml, Constants.OFFLINE, null);
	}

	/**
	 *
	 * @return	type	String
	 * 
	 * @see		in.xpeditions.jawlin.imonitor.controller.serviceGatewayServiceManager#discoverDeviceUpdater(java.lang.String)
	 *
	 * @since	This method is invoked from receiver component class: DiscoverDeviceUpdater, method: updateEvent() when ever any device is discovered.
	 * 			Changes made by SUMIT:
	 * 				1.Set Default Camera Orientation for different cameras.
	 * 				2.Set Default No Motion Detection Period for PIR_SENSORS and MULTI_SENSORS.
	 * 				3.Set Appropriate Position Index for device with default location based on which it should appear last in the view. 
	 * 				4.Set Default Polling Interval for ZXT120 AC_EXTENDER.
	 * 				
	 */
	
	public String discoverDeviceUpdater(String xml) {
		
		//Changed By Apoorva to send command messages in Order to Gateway
		//Changed from HashMap to LinkedHashMap
		Map<String, String> commands = new LinkedHashMap<String, String>();
		String valueInXml = null;
		String macId = "";
		String DeviceTypeName=null;
		
		String DevTypeId=null;
		Device device = new Device();
		try {
			Queue<KeyValuePair> queue = IMonitorUtil
					.extractCommandsQueueFromXml(xml);
			
			macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
			GatewayManager gatewayManager = new GatewayManager();
			GateWay gateWay = gatewayManager.getGateWayByMacId(macId);
			// device.setGateWay(gateWay);
			String deviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
			// Here we should check the device type also ...
			if ((deviceId.trim()).isEmpty()) {
				commands.put(Constants.CMD_ID,
						Constants.DEVICE_DISCOVERED_FAILED);
				commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue,
						Constants.TRANSACTION_ID));
				commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue,
						Constants.IMVG_ID));
				commands.put(Constants.DEVICE_ID, deviceId);
				commands
						.put(Constants.FAILURE_REASON, Constants.UNKNOWN_DEVICE);
				valueInXml = IMonitorUtil.convertToXml(commands);
				return valueInXml;
			}
			device.setDeviceId(deviceId);
			String generatedDeviceId = macId + "-" + deviceId;
			device.setGeneratedDeviceId(generatedDeviceId);
			device.setGateWay(gateWay);
			device.setFriendlyName(deviceId);
			/**
			 * To Avoid Desire timeout visibility by default for Switches/Dimmer 
			 * Kantharaj setting Enable status to 0 for Switches/Dimmer.
			 * For Camera Enable is Set to 1
			 * 
			 *  Changed on 2/26/2014
			 * */
			device.setEnableStatus("0");
			device.setLocationMap(null);
			AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_UP);
			device.setLastAlert(alertType);
			String deviceTypeName = IMonitorUtil.commandId(queue, Constants.DEVICE_TYPE);
			DeviceManager deviceManager = new DeviceManager();
			String modelNumber = IMonitorUtil.commandId(queue, Constants.MODEL_NO);
			DeviceConfigurationManager deviceConfigurationManager = new DeviceConfigurationManager();
			//sumit start: Camera Orientation Feature
			if (deviceTypeName.compareTo(Constants.IP_CAMERA) == 0) {
				
				 device.setEnableStatus("1");
				
				if(modelNumber.contains(Constants.H264Series)){
					H264CameraConfiguration h264CameraConfiguration = new H264CameraConfiguration();
					h264CameraConfiguration.setCameraOrientation(Constants.DEFAULT_H264_ORIENTATION);
					deviceConfigurationManager.saveDeviceConfiguration(h264CameraConfiguration);
					device.setDeviceConfiguration(h264CameraConfiguration);
				}else if (modelNumber.contains(Constants.RC80Series)){
					CameraConfiguration cameraConfiguration = new CameraConfiguration();
					cameraConfiguration.setCameraOrientation(Constants.DEFAULT_CAMERA_ORIENTATION);
					deviceConfigurationManager.saveDeviceConfiguration(cameraConfiguration);
					device.setDeviceConfiguration(cameraConfiguration);
				}
			}
			/* ********************************************************* ORIGINAL CODE ******************************************************
			 * if (deviceTypeName.compareTo(Constants.IP_CAMERA) == 0) {
				if(modelNumber.equalsIgnoreCase("H264PT")){
					H264CameraConfiguration h264CameraConfiguration = new H264CameraConfiguration();
					deviceConfigurationManager.saveDeviceConfiguration(h264CameraConfiguration);
					device.setDeviceConfiguration(h264CameraConfiguration);
				}else{
					CameraConfiguration cameraConfiguration = new CameraConfiguration();
					deviceConfigurationManager.saveDeviceConfiguration(cameraConfiguration);
					device.setDeviceConfiguration(cameraConfiguration);
				}
			}*/
			//sumit end: Camera Orientation Feature
			else if(deviceTypeName.compareTo(Constants.Z_WAVE_MOTOR_CONTROLLER) == 0)
			{
				MotorConfiguration motorConfiguration = new MotorConfiguration();
				motorConfiguration.setLength(0);
				motorConfiguration.setMountingtype(0);
				deviceConfigurationManager.saveDeviceConfiguration(motorConfiguration);
				device.setDeviceConfiguration(motorConfiguration);
			}else if((deviceTypeName.compareTo(Constants.Z_WAVE_PIR_SENSOR) == 0) 
					|| (deviceTypeName.compareTo(Constants.Z_WAVE_MULTI_SENSOR) == 0) || (deviceTypeName.compareTo(Constants.Z_WAVE_LUX_SENSOR) == 0)){			
				device.setModelNumber("5"); 	//sumit: for PIR/MULTI SENSOR(Use model number for No Motion Time-Out)
												//SUMIT TBD: Add this field to PirConfiguration 
			}else if((deviceTypeName.compareTo(Constants.Z_WAVE_PM_SENSOR)==0)){
				
				
			}else if((deviceTypeName.compareTo("Z_WAVE_AV_BLASTER") == 0)){			
				//sumit commented: Nov 7, 2013.
				//modelNumber = "AV Blaster"; 	//parishod: for AV blaster(Use model number for configuring on setup->devices page)
			}			
			//sumit start: ZXT120 Temperature Sensor
			else if((deviceTypeName.compareTo(Constants.Z_WAVE_AC_EXTENDER) == 0)){
				String acExtenderModel = IMonitorUtil.commandId(queue, Constants.DEVICE_MODEL);
				//Naveen made changes for supporting other type AC models
//				if(acExtenderModel.equalsIgnoreCase(Constants.ZXT120)){
//					acConfiguration acConfiguration = new acConfiguration();
//					acConfiguration.setFanModeValue(Constants.DEFAULT_FAN_MODES);
//					acConfiguration.setPollingInterval(Constants.DEFAULT_POLLING_INTERVAL);
//					acConfiguration.setFanMode("2");
//					acConfiguration.setAcSwing("0");
//					deviceConfigurationManager.saveDeviceConfiguration(acConfiguration);
//					device.setDeviceConfiguration(acConfiguration);
//				}
				 if(acExtenderModel.equalsIgnoreCase(Constants.ZXT110))
				{
					acConfiguration acConfiguration = new acConfiguration();
					acConfiguration.setFanModeValue(Constants.DEFAULT_FAN_MODES);
					acConfiguration.setPollingInterval("0");
					acConfiguration.setFanMode("2");
					acConfiguration.setAcSwing("0");
					deviceConfigurationManager.saveDeviceConfiguration(acConfiguration);
					device.setDeviceConfiguration(acConfiguration);	
				}else {
					acConfiguration acConfiguration = new acConfiguration();
					acConfiguration.setFanModeValue(Constants.DEFAULT_FAN_MODES);
					acConfiguration.setPollingInterval(Constants.DEFAULT_POLLING_INTERVAL);
					acConfiguration.setFanMode("2");
					acConfiguration.setAcSwing("0");
					deviceConfigurationManager.saveDeviceConfiguration(acConfiguration);
					device.setDeviceConfiguration(acConfiguration);
				}
			}
			//sumit end: ZXT120 Temperature Sensor
			//sumit start: UnsupportedDevice
			//Naveen Added for saving default minimote configuration on June 30th 2014
			else if((deviceTypeName.compareTo(Constants.Z_WAVE_MINIMOTE) == 0)){
				MinimoteConfig miniMoteConfig = new MinimoteConfig();
				miniMoteConfig.setButtonone(Constants.PANIC_SITUATION);
				miniMoteConfig.setButtontwo(macId+"-HOME");
				miniMoteConfig.setButtonthree(macId+"-AWAY");
				miniMoteConfig.setButtonfour(macId+"-STAY");
				deviceConfigurationManager.saveDeviceConfiguration(miniMoteConfig);
				device.setDeviceConfiguration(miniMoteConfig);
			}
			
					 
			
			else if((deviceTypeName.compareTo(Constants.ZWAVE_CLAMP_SWITCH) == 0)){
				modelNumber = "HMD";
			}
			
			else if((deviceTypeName.compareTo(Constants.DEV_ZWAVE_SCENE_CONTROLLER) == 0)){
				LogUtil.info("Zwave scene controller");
				SceneControllerManager sceneManager = new SceneControllerManager();
				List<SceneControllerMake> sceneMake = sceneManager.getSceneMakeByModel(modelNumber);
			//	List<KeyConfiguration> keyConfig = new ArrayList<KeyConfiguration>();
				KeyConfiguration key=null;
				long index=0;
				for(SceneControllerMake scene : sceneMake){
					key = new KeyConfiguration();
					key.setPosindex(index);
					key.setDevice(device);
					key.setKeyCode(scene.getKeyCode());
					key.setKeyName(scene.getKeyName());
					key.setPressType(scene.getPressType());
					boolean result=sceneManager.saveKeyConfiguration(key);
					LogUtil.info("Zwave scene controller result"+result);
					index++;
				} // end for loop
			
			}
			else if((deviceTypeName.compareTo(Constants.UNSUPPORTED) == 0)){
				
				String basicDeviceClass=IMonitorUtil.commandId(queue, Constants.BASIC_DEV_CLASS);
				String genericClass = IMonitorUtil.commandId(queue, Constants.GENERIC_CLASS);
				String specificClass = IMonitorUtil.commandId(queue, Constants.SPECIFIC_CLASS);
				String supportClass=IMonitorUtil.commandId(queue, Constants.SUPPORTED_CLASSES);
				String manufacturerId=IMonitorUtil.commandId(queue, Constants.MANUFACTURER_ID);
				String productId=IMonitorUtil.commandId(queue, Constants.PRODUCT_ID);
				//Finding new devicetype model from DB
				NewDeviceType newDeviceType =gatewayManager.getDeviceTypeBasedOnClass(basicDeviceClass,genericClass,specificClass,supportClass,manufacturerId,productId); 
				if(newDeviceType == null) {
					LogUtil.info("device is null");
				}else {
					LogUtil.info("not null unsupported");
				}
				if(newDeviceType == null){
					commands.put(Constants.CMD_ID,
							Constants.DEVICE_DISCOVERED_FAILED);
					commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue,
							Constants.TRANSACTION_ID));
					commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue,
							Constants.IMVG_ID));
					commands.put(Constants.DEVICE_ID, deviceId);
					commands
							.put(Constants.FAILURE_REASON, Constants.UNKNOWN_DEVICE);
					valueInXml = IMonitorUtil.convertToXml(commands);
					return valueInXml;
					
				}else{
					DeviceTypeName=newDeviceType.getDeviceType();
					DeviceTypeManager deviceTypeManager = new DeviceTypeManager();
					DeviceType deviceType = deviceTypeManager.getDeviceTypeByName(DeviceTypeName);
					device.setDeviceType(deviceType);
					device.setEnableList("0");
					modelNumber = newDeviceType.getModelNumber();
					device.setEnableStatus("0");
					device.setLocationMap(null);
					device.setLastAlert(alertType);
					IconManager iconManager = new IconManager();
					Icon defaultIcon = iconManager.getDefaultIcon(deviceType.getId());
					device.setIcon(defaultIcon);
					
					//RGB Changes
					if((DeviceTypeName.compareTo(Constants.DEV_ZWAVE_RGB) == 0) || (DeviceTypeName.compareTo(Constants.DEV_ZWAVE_VIRTUAL_SW) == 0)){
						LogUtil.info("RGB or Virtual Switch Home display true");
						device.setEnableList("1");
					}
					
					//Virtual Switch Changes 
												
					
					if (deviceManager.saveDeviceWithDefaultLocation(device)) {
						
						DevTypeId=newDeviceType.getDeviceTypeNumber();
						LogUtil.info("Device type number is :" + DevTypeId);
						if(DeviceTypeName.equals(Constants.DEV_ZWAVE_SCENE_CONTROLLER)){
							SceneControllerManager sceneManager = new SceneControllerManager();
							List<SceneControllerMake> sceneMake = sceneManager.getSceneMakeByModel(modelNumber);
						//	List<KeyConfiguration> keyConfig = new ArrayList<KeyConfiguration>();
							KeyConfiguration key=null;
							long index=0;
							for(SceneControllerMake scene : sceneMake){
								key = new KeyConfiguration();
								key.setPosindex(index);
								key.setDevice(device);
								key.setKeyCode(scene.getKeyCode());
								key.setKeyName(scene.getKeyName());
								key.setPressType(scene.getPressType());
								boolean result=sceneManager.saveKeyConfiguration(key);
								index++;
							} // end for loop
						}else if(DeviceTypeName.equals(Constants.DEV_ZWAVE_WALL_MOTE_QUAD)) {
						
							long index=1;
							WallmoteConfiguration config = null;
							do {
								
								config = new WallmoteConfiguration();
								config.setKeyCode("0");
								config.setKeyName("Key " + index);
								config.setPosindex(index);
								config.setDevice(device);
								deviceManager.saveWallmoteConfiguration(config);
								index++;
							}
							while(index <= 4);
							
							
						}else if(DeviceTypeName.equals(Constants.DEV_ZWAVE_FIB_KEYFOBE)) {
							KeyConfiguration singlepresskey=null;
							KeyConfiguration pressHoldKey=null;
							long index=0;
							SceneControllerManager sceneManager = new SceneControllerManager();
							for(int i =1;i<=6;i++){
								singlepresskey = new KeyConfiguration();
								singlepresskey.setPosindex(index);
								singlepresskey.setDevice(device);
								singlepresskey.setKeyCode("0");
								singlepresskey.setKeyName("Key"+i);
								singlepresskey.setPressType("Single Click");
								index++;
								pressHoldKey = new KeyConfiguration();
								pressHoldKey.setPosindex(index);
								pressHoldKey.setDevice(device);
								pressHoldKey.setKeyCode("5");
								pressHoldKey.setKeyName("Key"+i);
								pressHoldKey.setPressType("Press & hold");
								sceneManager.saveKeyConfiguration(singlepresskey);
								sceneManager.saveKeyConfiguration(pressHoldKey);
								index++;
							} // end for loop
							
							
						}
						commands.put(Constants.CMD_ID,Constants.DEVICE_DISCOVERED_SUCCESS);
						commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID));
						commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue,Constants.IMVG_ID));
						commands.put(Constants.DEVICE_ID,generatedDeviceId);
						commands.put(Constants.DEVICE_TYPE, DevTypeId);
						commands.put(Constants.DEVICE_TYPE_NAME,DeviceTypeName);
						
					
					}
					
					
					//RGB Discovery start
					if(DeviceTypeName.equals(Constants.DEV_ZWAVE_RGB)){
						device.setEnableList("1");
						LogUtil.info("DEV_ZWAVE_RGB discovered");
						commands.put(Constants.CMD_ID,Constants.DEVICE_DISCOVERED_SUCCESS);
						commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID));
						commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue,Constants.IMVG_ID));
						commands.put(Constants.DEVICE_ID,generatedDeviceId);
						commands.put(Constants.DEVICE_TYPE, DevTypeId);
						commands.put(Constants.DEVICE_TYPE_NAME,DeviceTypeName);
						LogUtil.info("US 6");
					}
					
					//RGB Discovery end
					
					
					//Virtual Switch Discovery start
					if(DeviceTypeName.equals(Constants.DEV_ZWAVE_VIRTUAL_SW)){
						
						KeyConfiguration ON=null;
						KeyConfiguration OFF=null;
						SceneControllerManager sceneManager = new SceneControllerManager();
							ON = new KeyConfiguration();
							ON.setPosindex(0);
							ON.setDevice(device);
							ON.setKeyCode("1");
							ON.setKeyName("Key1");
							ON.setPressType("Single Click");
							OFF = new KeyConfiguration();
							OFF.setPosindex(1);
							OFF.setDevice(device);
							OFF.setKeyCode("0");
							OFF.setKeyName("Key1");
							OFF.setPressType("Double Click");
							sceneManager.saveKeyConfiguration(ON);
							sceneManager.saveKeyConfiguration(OFF);
						
						
						device.setEnableList("1");
						LogUtil.info("DEV_ZWAVE_VIRTUAL_SW discovered");
						commands.put(Constants.CMD_ID,Constants.DEVICE_DISCOVERED_SUCCESS);
						commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID));
						commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue,Constants.IMVG_ID));
						commands.put(Constants.DEVICE_ID,generatedDeviceId);
						commands.put(Constants.DEVICE_TYPE, DevTypeId);
					//	commands.put(Constants.DEVICE_TYPE, "20");
						commands.put(Constants.DEVICE_TYPE_NAME,DeviceTypeName);
					}
					
					//Virtual Switch Discovery end
					
					
					
					valueInXml = IMonitorUtil.convertToXml(commands);
					return valueInXml;
				} // end else statement
				
					
			}
			//sumit end: UnsupportedDevice
			device.setModelNumber(modelNumber);

			DeviceTypeManager deviceTypeManager = new DeviceTypeManager();
			DeviceType deviceType = deviceTypeManager.getDeviceTypeByName(deviceTypeName);
			XStream stream = new XStream();
			
			//Sets Default Icon Image for the Device
			IconManager iconManager = new IconManager();
			Icon defaultIcon = iconManager.getDefaultIcon(deviceType.getId());
			device.setIcon(defaultIcon);
			//sumit start
			device.setMode(Constants.DEFAULT_DEVICE_MODE);
			device.setEnableList(Constants.DEFAULT_DEVICE_LISTING);	//by default, the device should be shown on home screen.
			
			//Diaken VIA after discovery
			if((deviceTypeName.compareTo(Constants.VIA) == 0)){
				
				device.setEnableList("0");
			}
			
			
			//sumit start: Set appropriate position Index for Device
			//1. Get the max of positionIndex for Default Room and set the positionIndex for the device as max(posIdx+1).
			String posIdx = deviceManager.getMaxPositionIndexBasedOnLocation(Constants.LIVING_ROOM);
			long positionIndex = Long.parseLong(posIdx);
			device.setPosIdx(++positionIndex);
			//sumit end: Set appropriate position Index for Device
			
			//added by naveen for rocker and tact configuration on 29Dec 2014
			if(deviceTypeName.compareTo(Constants.Z_WAVE_SWITCH) == 0){
				
				device.setSwitchType(2);
			}
			
			
			//end
			if(deviceType != null && defaultIcon != null){
				device.setDeviceType(deviceType);
				if (deviceManager.saveDeviceWithDefaultLocation(device)) {
								
					
					
						commands.put(Constants.CMD_ID,Constants.DEVICE_DISCOVERED_SUCCESS);
						commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID));
						commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue,Constants.IMVG_ID));
						commands.put(Constants.DEVICE_ID, generatedDeviceId);
						
						
				
					/**********Naveen added on 4th Feb 2016******/
					int count = deviceManager.getDeviceCountPerGateway(gateWay.getId());
					
					if(count == 1){
						
						CustomerManager customerManager = new CustomerManager();
						Customer customer = customerManager.getCustomerByGateWay(gateWay);
						Date date = new Date();
						/*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");*/
						customer.setDateOfInstallation(date);
						
						Calendar cal=Calendar.getInstance();
						cal.setTime(date);
						cal.add(Calendar.MONTH,customer.getRenewalDuration());
						customer.setDateOfExpiry(cal.getTime());
						customerManager.updateCustomer(customer);
												
						
					}// end device count
					
				}
				//VIBHUTI TBD Bug in the code else not handled 
				valueInXml = IMonitorUtil.convertToXml(commands);
				
			} else{
				commands.put(Constants.CMD_ID,
						Constants.DEVICE_DISCOVERED_FAILED);
				commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue,
						Constants.TRANSACTION_ID));
				commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue,
						Constants.IMVG_ID));
				commands.put(Constants.DEVICE_ID, deviceId);
				commands.put(Constants.FAILURE_REASON, Constants.UNKNOWN_DEVICE);
				valueInXml = IMonitorUtil.convertToXml(commands);
			}
		} catch (ParserConfigurationException e) {
			LogUtil.error("Error in saving device and message is : "
					+ e.getMessage());
			e.printStackTrace();
		} catch (SAXException e) {
			LogUtil.error("Error in saving device and message is : "
					+ e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LogUtil.error("Error in saving device and message is : "
					+ e.getMessage());
			e.printStackTrace();
		} 
		catch (Exception e) {
			LogUtil.error("General Error in saving device and message is : "
					+ e.getMessage());
			LogUtil.info("", e);
		} 
		/* sumit commented: DEVICE_DISCOVERY_STOP [Stopping the thread will be handled in discoverDeviceStopper() method now]
		
		finally {
			Thread t = (Thread) IMonitorSession.get(macId);
			if(t != null){
				t.interrupt();
			}
		}*/
		
		/*Naveen Added for proactive alexa discovery
		 * update device list to alexa after discovery
		 * 24th July 2018
		 */
		if((device.getDeviceType().getName().equals(Constants.Z_WAVE_AC_EXTENDER)) || (device.getDeviceType().getName().equals(Constants.Z_WAVE_DIMMER)) || (device.getDeviceType().getName().equals(Constants.Z_WAVE_SWITCH))) {
		CustomerManager customerManager = new CustomerManager();
		Customer customer =customerManager.getCustomerByMacId(macId);
		Alexamanager alexaManager = new Alexamanager();
		Alexa alexaUser = alexaManager.getAlexaUserByCustomerId(customer);
	
		if(alexaUser != null) {
			
			try {
				AlexaProactiveUpdateService alexaProactiveService = new AlexaProactiveUpdateService(customer, alexaUser,device);
				Thread t = new Thread(alexaProactiveService);
				 t.start();
				//alexaServiceManager.updateDeviceListToAlexa(customer,alexaUser);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}
		
		}
		
		
		return valueInXml;
	}

	/**
	 * @author sumit
	 * @param xml java.lang.String
	 * @return valueInXml java.lang.String
	 * @throws SAXException, IOException, ParserConfigurationException
	 */
	public String discoverDeviceStopper(String xml)
			throws SAXException, IOException, ParserConfigurationException{
		String valueInXml = null;
		String macId = "";
		try {
			Map<String, String> commands = new LinkedHashMap<String, String>();
			Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
			macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
			
			//1.Interrupt the Thread.
			String stopReason = IMonitorUtil.commandId(queue, Constants.STOP_REASON);
			if(stopReason.equalsIgnoreCase(Constants.DEVICE_DISCOVERED_FAILED) 
					|| stopReason.equalsIgnoreCase(Constants.DEVICE_ALREADY_ADDED)
					|| stopReason.equalsIgnoreCase(Constants.DEVICE_DISCOVERY_DONE)	
					|| stopReason.equalsIgnoreCase(Constants.DEVICE_DISCOVERY_TIMED_OUT)
					|| stopReason.equalsIgnoreCase(Constants.UNSUPPORTED_DEVICE_TYPE)){
				Thread t = (Thread) IMonitorSession.get(macId);
				if(t != null){
					t.interrupt();
				}
			}
			
			commands.put(Constants.CMD_ID, Constants.DEVICE_DISCOVERY_STOP_SUCCESS);
			commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID));
			commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue, Constants.IMVG_ID));
			
			valueInXml = IMonitorUtil.convertToXml(commands);
		} catch (IOException e) {
			LogUtil.info("IOException :", e);
		} catch (ParserConfigurationException e) {
			LogUtil.info("ParserConfigurationException :", e);
		} catch (SAXException e) {
			LogUtil.info("SAXException :", e);
		}
		return valueInXml;
	}
	
	public String alertEvent(String xml) {
		try {
			Queue<KeyValuePair> queue = IMonitorUtil
					.extractCommandsQueueFromXml(xml);
			String generatedDeviceId = IMonitorUtil.commandId(queue,
					Constants.DEVICE_ID);
			DeviceManager deviceManager = new DeviceManager();
			Device device = deviceManager
					.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceId);
			String alertParamString = IMonitorUtil.commandId(queue,
					Constants.ALERT_EVENT);
			AlertType alertType = IMonitorUtil.getAlertTypes().get(alertParamString);
			device.setLastAlert(alertType);
			deviceManager.updateDevice(device);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String listAskedGateWays(String xml) {
		XStream xStream = new XStream();
		DataTable dataTable = (DataTable) xStream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		int count = gatewayManager.getTotalGateWayCount();
		dataTable.setTotalRows(count);
		String sSearch = dataTable.getsSearch();
		String[] cols = { "g.macId","s.name","a.name","c.customerId","g.gateWayVersion"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = gatewayManager.listAskedGateWay(sSearch,sOrder, dataTable
				.getiDisplayStart(), dataTable.getiDisplayLength());
		dataTable.setResults(list);
		int displayRow = gatewayManager.getTotalGateWayCount(sSearch);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}

	public String getGateWayById(String xml) {
		XStream stream = new XStream();
		long id = (Long) stream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayById(id);
		String valueInXml = stream.toXML(gateWay);
		return valueInXml;
	}

	public String unRegisterGateWay(String xml)
			throws ParserConfigurationException, SAXException, IOException {
		String result = "no";
		XStream xStream = new XStream();
		long id = (Long) xStream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayWithAgentById(id);
		String imvgId = gateWay.getMacId();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.IMVG_UNREGISTER));
		String transId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, imvgId));
		String valueInxml = IMonitorUtil.convertQueueIntoXml(queue);
		RequestProcessor requestProcessor = new RequestProcessor();
		requestProcessor.processRequest(valueInxml, gateWay);
		return result;
	}

	public String removenodefromGateWay(String xml)
			throws ParserConfigurationException, SAXException, IOException {
		String result = "no";
		XStream xStream = new XStream();
		long id = (Long) xStream.fromXML(xml);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayWithAgentById(id);
		
		ActionModel actionModel = new ActionModel(null,xStream.toXML(gateWay));
		ImonitorControllerAction controllerAction = new RemoveFailnodeAction();
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg)
		{
			if(controllerAction.isActionSuccess())
			{
				result = "Remove fail node- Started successfully" ;
			}else{
				result = "Remove fail node- Could not be started";			
			}
		}
		else
		{
			result = "Remove fail node- Gateway did not respond in time";
		}
		
		LogUtil.info(" removenodefromGateWay " + result);
		return result;
	}

	public String updateUnRegisterGateWay(String xml) throws ParserConfigurationException, SAXException, IOException {
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
		String Status=IMonitorUtil.commandId(queue, Constants.STATUS);
		if(Status.equals("IMVG_UNREGISTER_SUCCESS")){
			GatewayManager gatewayManager = new GatewayManager();
			GateWay gateWay = gatewayManager.getGateWayByMacId(generatedDeviceId);
			StatusManager statusManager = new StatusManager();
			Status status = statusManager.getStatusByName(Constants.UN_REGISTERED);
			gateWay.setStatus(status);
			gatewayManager.updateGateWay(gateWay);
			return "success";
		}
		return "Failure";
		}

	public String updateFirmWare(String xml) {
		
		ActionModel actionModel = new ActionModel(null, xml);
		ImonitorControllerAction controllerAction = new FirmwareUpgradeAction();
		controllerAction.executeCommand(actionModel);
		String tOut = ControllerProperties.getProperties().get(Constants.TIMOUT_DURATION);
		long timeOut = Long.parseLong(tOut);
		IMonitorUtil.waitForResult(controllerAction,timeOut*2);

		return "success";
	}
	
	
	/**
	 * @author sumit
	 * @param xml java.lang.String
	 * @throws Exception
	 */
	public void updateFirmwareUpgradeSuccessful(String xml) throws Exception{
		//sumit TBD
	}
	
	/**
	 * @author sumit
	 * @param xml java.lang.String
	 * @throws Exception
	 */
	public void updateFirmwareUpgradeFailure(String xml) throws Exception{
		//sumit TBD
	}
	public String updateBatteryStatus(String xml) throws ParserConfigurationException, SAXException, IOException{
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDeviceByGeneratedDeviceId(generatedDeviceId);
		String batteryStatus = IMonitorUtil.commandId(queue, Constants.BATTERY_LEVEL);
		device.setBatteryStatus(batteryStatus);
		deviceManager.updateDevice(device);
		return null;
	}
	
	
	
	public String updateDeviceDownAlert(String xml) throws Exception {
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		Device device = saveAlert(queue);
		AlertType alertType = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_DOWN);
		device.setLastAlert(alertType);
		DeviceManager deviceManager = new DeviceManager();
		deviceManager.updateDevice(device);
		AlertNotiifier alertNotiifier = new AlertNotiifier(device, null);
		Thread t = new Thread(alertNotiifier);
		t.start();
		Queue<KeyValuePair> resultQueue = new LinkedList<KeyValuePair>();
		resultQueue.add(new KeyValuePair(Constants.CMD_ID, Constants.DEVICE_ALERT_ACK));
		resultQueue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID)));
		resultQueue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil.commandId(queue, Constants.IMVG_ID)));
		resultQueue.add(new KeyValuePair(Constants.DEVICE_ID, IMonitorUtil.commandId(queue, Constants.DEVICE_ID)));
		XStream stream = new XStream();
		return stream.toXML(resultQueue);
	}
	

	public String updateDeviceCommand(String xml) throws Exception {
		Device device = saveCommandsFromImvgAndUpdateDevice(xml);
		DeviceManager deviceManager = new DeviceManager();
		deviceManager.updateDevice(device);
		return null;
	}
	
	public Device saveAlertsFromImvgAndUpdateDevice(Queue<KeyValuePair> queue) throws Exception{
		Device device = saveAlert(queue);
		String alertParamS = IMonitorUtil.commandId(queue, Constants.ALERT_EVENT);
		AlertType alertType = IMonitorUtil.getAlertTypes().get(alertParamS);
		device.setLastAlert(alertType);
		return device;
	}
	
	public Device saveCommandsFromImvgAndUpdateDevice(String xml) throws Exception{
		Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
		Device device = saveAlert(queue);
		String commandParam = IMonitorUtil.commandId(queue, Constants.COMMAND_PARAM);
		device.setCommandParam(commandParam);
		return device;
	}

	private Device saveAlert(Queue<KeyValuePair> queue) throws Exception {
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDeviceWithRulesByGeneratedDeviceId(generatedDeviceId);
		String details = IMonitorUtil.commandId(queue, Constants.ALERT_EVENT);
		AlertTypeManager alertTypeManager = new AlertTypeManager();
		AlertType alertType = alertTypeManager.getAlertTypeByDetails(details);
		AlertsFromImvg alertsFromImvg = new AlertsFromImvg();
		alertsFromImvg.setAlertTime(new Date());
		alertsFromImvg.setAlertType(alertType);
		alertsFromImvg.setDevice(device);
		AlertsFromImvgManager alertsFromImvgManager = new AlertsFromImvgManager();
		alertsFromImvgManager.save(alertsFromImvg);
		return device;
	}
	
	public String iSValidMacId(String xml) {
		XStream xStream = new XStream();
		Customer customer=(Customer)xStream.fromXML(xml);
//		1. Setting the gateways to null.		
		Set<GateWay> gateWays = customer.getGateWays();
		GatewayManager gatewayManager = new GatewayManager();
		List<GateWay> usedGateWays = new ArrayList<GateWay>();
			for (GateWay  gateWay:gateWays) {
				GateWay selectedGateWay = gatewayManager.getGateWayByMacId(gateWay.getMacId());
				if(selectedGateWay == null){
					usedGateWays.add(gateWay);
				}
		}
		String gateWaysXml = xStream.toXML(usedGateWays);
		return gateWaysXml;
	}
	
	public String updateGateWayWanIp(String xml) {
		String valueInXml = null;
		XStream xStream = new XStream();
		Map<String, String> commands = new HashMap<String, String>();
		try {
			Queue<KeyValuePair> queue = IMonitorUtil
					.extractCommandsQueueFromXml(xml);
			String macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
			String imvgWanIp = IMonitorUtil.commandId(queue, Constants.IMVG_WAN_IP);
			String transactionId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
			GatewayManager gatewayManager = new GatewayManager();
			commands.put(Constants.CMD_ID, Constants.UPDATE_IMVG_WAN_IP_ACK);
			commands.put(Constants.IMVG_ID, macId);
			commands.put(Constants.TRANSACTION_ID, transactionId);
			gatewayManager.updateLocalIp(macId, imvgWanIp);
		} catch (ParserConfigurationException e) {
			LogUtil.error("Error in updating local ip and message is : "
					+ e.getMessage());
		} catch (SAXException e) {
			LogUtil.error("Error in updating local ip and message is : "
					+ e.getMessage());
		} catch (IOException e) {
			LogUtil.error("Error in updating local ip and message is : "
					+ e.getMessage());
		}
		valueInXml = xStream.toXML(commands);
		return valueInXml;
	}

public String logcapture(String xml,String selectedvalue,String timeout) {
	String result = "no";
	XStream xStream = new XStream();
	String macId = (String) xStream.fromXML(xml);
	GatewayManager gatewayManager = new GatewayManager();
	GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(macId);
	Device device=new Device();
	if (gateWay == null){
		result = "failure";
		return result;
	} 
	else 
	{
	device.setGateWay(gateWay);
	}
	ActionModel actionModel = new ActionModel(device,selectedvalue,timeout);
	ImonitorControllerAction controllerAction = new ImvgLogs();
	controllerAction.executeCommand(actionModel);
	
	boolean resultFromImvg = IMonitorUtil.waitForResult(controllerAction);
	if(resultFromImvg)
	{
	
		result="success";
	}
	return result;
	}


public String DeleteLogByfileName(String xml) {
	String result = "failure";
	XStream xStream = new XStream();

	String filename = (String) xStream.fromXML(xml);
	String temp[]=filename.split(",");
	
	DeviceManager deviceManager=new DeviceManager();
	try{
		File logFile=new File(""+ControllerProperties.getProperties().get("UPLOADED_FILE_PATH")+temp[1]+temp[0]);
		if(logFile.delete())
		{
			if(deviceManager.DeleteLogsUnderGatewayById(temp[0]))
			{
				result = "success";
			}
		}
	}
	catch(Exception e){
		LogUtil.error(e.getMessage());
		LogUtil.info(e.getMessage(), e);
	}
	return xStream.toXML(result);
	
}


public String ExcuteUserCommand(String xml,String CommandXml,String TimeoutXml) {
	
	String result = "failure";
	XStream xStream = new XStream();

	String Command = (String) xStream.fromXML(CommandXml);
	String Timeout = (String) xStream.fromXML(TimeoutXml);
	GateWay gateway= (GateWay)xStream.fromXML(xml);
	
	Device device=new Device();
	GatewayManager gatewayManager=new GatewayManager();
	gateway=gatewayManager.getGateWayWithAgentById(gateway.getId());
	
	device.setGateWay(gateway);
	ActionModel actionmodel=new ActionModel(device,Command,Timeout);
	ImonitorControllerAction ExcuteUserCommand=new ExcuteUseCommandAction();
	ExcuteUserCommand.executeCommand(actionmodel);
	boolean resultFromImvg = IMonitorUtil.waitForResultForCommand(ExcuteUserCommand);
	if(resultFromImvg)
	{
		if(ExcuteUserCommand.isActionSuccess())
		{
			result=actionmodel.getMessage();
		}
		result=actionmodel.getMessage();
	}
	
	return result;
	
}

public String GetGateWayAndAgentBYMacid(String xml) {
	XStream xStream = new XStream();
	String imvgd = (String) xStream.fromXML(xml);
	GatewayManager gatewayManager = new GatewayManager();
	GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(imvgd);
	return xStream.toXML(gateWay);
}

public String listAskedGateWaysWithMaintenance(String xml) {
	XStream xStream = new XStream();
	DataTable dataTable = (DataTable) xStream.fromXML(xml);
	GatewayManager gatewayManager = new GatewayManager();
	int count = gatewayManager.getTotalGateWayCount();
	dataTable.setTotalRows(count);
	String sSearch = dataTable.getsSearch();
	String[] cols = { "g.macId","s.name","a.name","c.customerId"};
	String sOrder = "";
	int col = Integer.parseInt(dataTable.getiSortCol_0());
	  String colName = cols[col];
	  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
	List<?> list = gatewayManager.listAskedGateWayWithMaintance(sSearch,sOrder, dataTable
			.getiDisplayStart(), dataTable.getiDisplayLength());
	dataTable.setResults(list);
	int displayRow = gatewayManager.getTotalGateWayCount(sSearch);
	dataTable.setTotalDispalyRows(displayRow);
	String valueInXml = xStream.toXML(dataTable);
	return valueInXml;
}

public String RequestToOpenChannel(String xml){
	XStream xStream = new XStream();
	GateWay gw = (GateWay) xStream.fromXML(xml);
	GatewayManager gatewayManager = new GatewayManager();
	gatewayManager.SetMaintanceMode(gw,1);
	return "Sucess";
	
}

public String RequestToCloseChannel(String xml){
	Queue<KeyValuePair> queue = null;
	try {
		queue= IMonitorUtil.extractCommandsQueueFromXml(xml);
	} catch (ParserConfigurationException e) {
		
		e.printStackTrace();
	} catch (SAXException e) {
		
		e.printStackTrace();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	String macId = IMonitorUtil.commandId(queue,Constants.IMVG_ID);
	GatewayManager gatewayManager = new GatewayManager();
	GateWay gateWay=gatewayManager.getGateWayByMacId(macId);
	gatewayManager.SetMaintanceMode(gateWay,0);
	return "Sucess";
	
}

public String checkForMaintence(String xml) {
	String valueInXml = null;
	Map<String, String> commands = new HashMap<String, String>();
		Queue<KeyValuePair> queue;
		try {
			queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
			String macId = IMonitorUtil.commandId(queue,Constants.IMVG_ID);
			LogUtil.info("macId"+macId);
			String transactionId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
			
			GatewayManager gatewayManager = new GatewayManager();
			GateWay gateWay=gatewayManager.getGateWayByMacId(macId);
			
			String ChannelSatus=gateWay.getMaintenancemode();
			int MaintenanceMode=-1;
			try {
				MaintenanceMode=Integer.parseInt(ChannelSatus);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
			
			
			
			commands.put(Constants.CMD_ID,"PANIC_CHANNEL_UP_ACK");
			commands.put(Constants.TRANSACTION_ID,transactionId);
			commands.put(Constants.IMVG_ID,macId);
			if(MaintenanceMode==1)
			{
				gatewayManager.SetMaintanceMode(gateWay,2);
				commands.put(Constants.STATUS,Constants.SUCCESS);
			}
			else
			{
				commands.put(Constants.STATUS,Constants.FAILURE);
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	valueInXml = IMonitorUtil.convertToXml(commands);
	return valueInXml;
}

public String migrateGateWay(String xml, String ip, String port) throws SecurityException,
DOMException, IllegalArgumentException,
ParserConfigurationException, SAXException, IOException,
ClassNotFoundException, InstantiationException,
IllegalAccessException, NoSuchMethodException,
InvocationTargetException {
		String result = "no";
		XStream xStream = new XStream();
		GateWay gateWay = (GateWay) xStream.fromXML(xml);
		
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWaywithagent = gatewayManager.getGateWayWithAgentByMacId(gateWay.getMacId());
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.IMVG_MIGRATION));
		String transId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.SERVER_IP, ip));
		queue.add(new KeyValuePair(Constants.SEVER_PORT, port));
		
		String valueInxml = IMonitorUtil.convertQueueIntoXml(queue);
		RequestProcessor requestProcessor = new RequestProcessor();
		requestProcessor.processRequest(valueInxml, gateWaywithagent);
		return result;

}

/*public String CheckForMaintenceChannelEnable(String xml){
	String valueInXml = null;
	XStream xStream = new XStream();
	Queue<KeyValuePair> resultQueue = new LinkedList<KeyValuePair>();
		LogUtil.info("XML"+xml);
		Queue<KeyValuePair> queue;
		try {
			queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
			String macId = IMonitorUtil.commandId(queue, "MAC");
			String transactionId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
			GatewayManager gatewayManager = new GatewayManager();
			
			GateWay gateWay=gatewayManager.getGateWayByMacId(macId);
			String ChannelSatus=gateWay.getMaintenancemode();
			int MaintenanceMode=Integer.parseInt(ChannelSatus);
			resultQueue.add(new KeyValuePair("CMD","PANIC_CHANNEL_UP_ACK"));
			resultQueue.add(new KeyValuePair(Constants.TRANSACTION_ID,transactionId));
			resultQueue.add(new KeyValuePair("MAC",macId));
			if(MaintenanceMode==1)
			{
				gatewayManager.SetMaintanceMode(gateWay,2);
			}
			else
			{
				resultQueue.add(new KeyValuePair("Failure","Please Connect Admin"));
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	valueInXml = xStream.toXML(resultQueue);
	return valueInXml;
	
}*/


public String GetUserDatails(String xml) {
	XStream stream = new XStream();
	String macid =  (String) stream.fromXML(xml);
	UserManager usermanager = new UserManager();
	Object[] userdetails = (Object[]) usermanager.getMainUserDetailsByMacid(macid);
	String valueInXml = stream.toXML(userdetails);
	return valueInXml;
}

public String verifyGateway(String xml) throws Exception{
	Map<String, String> commands = new HashMap<String, String>();
	Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
	String imvgID = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
	GatewayManager gateWayManager = new GatewayManager();
	GateWay gateWay = gateWayManager.getGateWayByMacId(imvgID);
	String valueInXml = null;
	if(gateWay != null){
		String customerID = gateWayManager.getCustomerIdOfGateWay(imvgID);
		if(customerID == null || customerID.isEmpty()){
			
			commands.put(Constants.CMD_ID, Constants.CUSTOMER_NOT_CONFIGURED);
			commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue,Constants.TRANSACTION_ID));
			commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue,
					Constants.IMVG_ID));
			valueInXml = IMonitorUtil.convertToXml(commands);
			return valueInXml;
			}else{
				commands.put(Constants.CMD_ID,Constants.IMVG_KEEP_ALIVE_ACK);
				commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue,
						Constants.TRANSACTION_ID));
				commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue,
						Constants.IMVG_ID));
				valueInXml = IMonitorUtil.convertToXml(commands);
				return valueInXml;
			}
	}else {
		
		commands.put(Constants.CMD_ID, Constants.INVALID_IMVG_MAC);
		commands.put(Constants.TRANSACTION_ID, IMonitorUtil.commandId(queue,Constants.TRANSACTION_ID));
		commands.put(Constants.IMVG_ID, IMonitorUtil.commandId(queue,
				Constants.IMVG_ID));
		valueInXml = IMonitorUtil.convertToXml(commands);
		return valueInXml;
		
	}
	
}

public String validateGateway(String xml) throws Exception{
	Queue<KeyValuePair> queue = IMonitorUtil.extractCommandsQueueFromXml(xml);
	String imvgID = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
	//String valueInXml = null;

	GatewayManager gateWayManager = new GatewayManager();
	GateWay gateway=gateWayManager.getGateWayByMacId(imvgID);
	
	ActionModel actionModel = new ActionModel(gateway, null);
	ImonitorControllerAction KeepAliveAction = new KeepAliveAction();

	KeepAliveAction.executeCommand(actionModel);
	
	
	return null;
}


public String getDiagnosticInfoOfGateway(String xml){
	
	XStream stream = new XStream();
	String result = "failure";
	GatewayManager gatewayManager = new GatewayManager();
	CustomerManager customerManager = new CustomerManager();
	String customerId = (String) stream.fromXML(xml);
	Customer customer = customerManager.getCustomerByCustomerId(customerId);
	GateWay gateway = gatewayManager.getGateWayByCustomer(customer);
	ImonitorControllerAction diagnosticInfo = new DiagnosticInfoAction();
	ActionModel actionModel = new ActionModel(gateway, null);
	diagnosticInfo.executeCommand(actionModel);
	boolean resultFromImvg = IMonitorUtil.waitForResult(diagnosticInfo);
	if(resultFromImvg){
		
		result = (String) diagnosticInfo.getActionModel().getData();
		
		
	}
	return result;
}

public void sendImvgNotoficationToUser(String statusName, String macId){
	
	UserManager userManager = new UserManager();
	GatewayManager gatewayManager = new GatewayManager();
	CustomerManager customerManager = new CustomerManager();
	GateWay gateWay = gatewayManager.getGateWayByMacId(macId);
	Customer customer = customerManager.getCustomerByGateWay(gateWay); 
	SystemAlertManager systemAlertManager = new SystemAlertManager();
	List<Object[]> userNotifications = userManager.getMainUserNotifications(customer.getId(),Constants.MAIN_USER);
	
	SystemAlert systemAlert = null;
	if(statusName.equals(Constants.ONLINE)){
		 systemAlert = systemAlertManager.getSystemAlertByName(Constants.IMVG_ON);
	}
	if(statusName.equals(Constants.OFFLINE)){
		 systemAlert = systemAlertManager.getSystemAlertByName(Constants.IMVG_OFF);
	}
	if(userNotifications.size()>0){
	for(Object[] notification:userNotifications){
		
		//String message ="";
	
		List<Object[]> systemAlertByUser =  systemAlertManager.getSystemAlertByUserAndAlertId(notification[4].toString(),systemAlert.getId());
		
				if(systemAlertByUser != null){
				
		//	message += IMonitorUtil.generateMailMessage("IMVG is:"+statusName,notification[3].toString(),"", macId.toString(),notification[2].toString());
					
			
		/***
		 *KanthaRaj Changed To Notify To Customer,Insted of MainUser For System Alerts. 
		 * 
		 * String[] recipiantsBYsms ={notification[1].toString()};
		 String[] recipiantsBYmail ={notification[0].toString()};*/
					
		/**
		 * KanthaRaj Changed To Notify To MainUser,Insted of Customer For System Alerts.to resolve bug-1039
		 * on 15-04-2013
		 * 
		 * String[] recipiantsBYsms ={notification[5].toString()};
		 * String[] recipiantsBYmail ={notification[6].toString()};
		 * 
		 * **/
			
			String[] recipiantsBYsms ={notification[1].toString()};
			String[] recipiantsBYmail ={notification[0].toString()};
					 for (Object[] object : systemAlertByUser) {
						 if(object[1].equals(Constants.SEND_SMS)){
						 SmsNotifications notifications = new SmsNotifications();
						 notifications.notify("Your MasterController with "+ macId+" is "+statusName,recipiantsBYsms, null,customer);
					 }
						 if(object[1].equals(Constants.SEND_EMAIL)){
						 EMailNotifications eMailNotifications = new EMailNotifications();
						 try {
							 eMailNotifications.notify("Your MasterController with "+ macId+" is "+statusName,recipiantsBYmail, null, notification[3].toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
						 
					 }
						 
					}
				}
		}
	}
	
}
//FirmWareUpdate Gateway verification
public String getGatewayByMacId(String macIdXml)
{
	XStream stream=new XStream();
	String macId=(String) stream.fromXML(macIdXml);
	
	GatewayManager gatewayManager=new GatewayManager();
	GateWay gateWay=gatewayManager.getGateWayByMacId(macId);
	return stream.toXML(gateWay);
}

//Added by naveen 
//To store zing gateway list

public String addZingGatewayToList(String gatewayXml)
{
	String result="Gateway details not saved!";
	XStream xStream=new XStream();
	ZingGatewayList gateway=(ZingGatewayList)xStream.fromXML(gatewayXml);
	GatewayManager gatewayManager = new GatewayManager();
	if(gatewayManager.saveGateWayMacIdinList(gateway)){
		result="Gateway MacId details saved successfully";
	}
	return result;
}

//3gp
public String getMultipleGateways(String customerXml)
{
	XStream stream=new XStream();
	String cid = (String) stream.fromXML(customerXml);
	long id = Long.parseLong(cid);
	CustomerManager customerManager=new CustomerManager();
	Customer customer=customerManager.getCustomerById(id);
	
	GatewayManager gatewayManager=new GatewayManager();
	List<GateWay> gateWays= gatewayManager.getGateWaysOfCustomerForRenaming(customer.getCustomerId());
	return stream.toXML(gateWays);
	
}

//3gp
public String saveGatewayNames(String gatewaysXml)
{
	XStream stream=new XStream();
	List<GateWay> gateWays= (List<GateWay>) stream.fromXML(gatewaysXml);
	GatewayManager gatewayManager= new GatewayManager();
	for (GateWay gateWay : gateWays)
	{
		GateWay gateFromDb = gatewayManager.getGateWayByMacId(gateWay.getMacId());
		gateFromDb.setName(gateWay.getName());
		boolean res=gatewayManager.updateGateWay(gateFromDb);
		
	}
	return null;
}

//Display zing gateway list admin
public String listAskedZingGateWays(String xml){
	XStream xStream=new XStream();
	DataTable dataTable =  (DataTable) xStream.fromXML(xml);
	GatewayManager gatewayManager= new GatewayManager();
	//int count = customerManager.getTotalGateWayCount();
	//dataTable.setTotalRows(count);
	String sSearch = dataTable.getsSearch();
	String[] cols = { "g.macId"};
	String sOrder = "";
	int col = Integer.parseInt(dataTable.getiSortCol_0());
	  String colName = cols[col];
	  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
	List<?> list = gatewayManager.listAskedZingGatewayList(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength());
	dataTable.setResults(list);
	int displayRow = gatewayManager.getTotalZingGatewaysCount(sSearch);
	LogUtil.info("Zing Gateways displayRow : " + displayRow);
	dataTable.setTotalDispalyRows(displayRow);
	String valueInXml = xStream.toXML(dataTable);
	return valueInXml;
}

public String getZingGatewayById(String xml)
{
	XStream stream=new XStream();
	ZingGatewayList zingGateway = (ZingGatewayList) stream.fromXML(xml);
	GatewayManager gatewayManager= new GatewayManager();
	ZingGatewayList zingGatewayFromDb =  gatewayManager.getZingGatewayById(zingGateway.getId());
	return stream.toXML(zingGatewayFromDb);
}

public String saveEditedZingDetails(String xml)
{
	String response = "failure";
	XStream stream=new XStream();
	LogUtil.info("Zing gateway to update : " + xml);
	ZingGatewayList zingGateway = (ZingGatewayList) stream.fromXML(xml);
	GatewayManager gatewayManager= new GatewayManager();
	//ZingGatewayList zingGatewayFromDb =  gatewayManager.getZingGatewayById(zingGateway.getId());
	//String password = superUser.getPassword();
	//String hashPassword = IMonitorUtil.getHashPassword(password);
	//superUserFromDb.setPassword(hashPassword);
	DaoManager daoManager=new DaoManager();
	boolean res= daoManager.update(zingGateway);
	if (res)
	{
		response="success";
	}
	
	return response;
}

public String deleteZingGateway(String xml)
{
	String response = "failure";
	XStream stream=new XStream();
	ZingGatewayList zingGateway = (ZingGatewayList) stream.fromXML(xml);
	GatewayManager gatewayManager= new GatewayManager();
	boolean result2 =  gatewayManager.deleteZingGateway(zingGateway.getId());
	if (result2)
	{
		response="success";
	}
	
	return response;
}

}
