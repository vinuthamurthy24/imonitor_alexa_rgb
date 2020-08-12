/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.GetLiveStreamAction;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlertAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.SystemAlertManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.EMailNotifications;
import in.xpeditions.jawlin.imonitor.controller.notifications.SmsNotifications;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.UpdatorModel;
import in.xpeditions.jawlin.imonitor.util.XmlUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * @author Coladi
 *
 */
public class MobileServiceManager {

	/* ****************************************** sumit start : Notification for MID Login ************************************** */
	/**
	 * @author sumit kumar
	 * @param xml
	 * @return
	 * 
	 * Date Modified: Dec12, 2013
	 * Modified by Sumit Kumar to send out notifications
	 * for MID Login if configured by the Main User.
	 */
	public String authenticate(String xml){
		String resutlXml, uID;
		String customerId = "";
		String userId = ""; 
		String password = "";
		UserManager userManager = new UserManager();
		try {
			Document document = XmlUtil.getDocument(xml);
			NodeList customerNodes = document.getElementsByTagName("customerid");
			Element customerNode = (Element) customerNodes.item(0);
			//String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			//LogUtil.info("customerId"+customerId);
			
			NodeList userNodes = document.getElementsByTagName("userid");
			Element userNode = (Element) userNodes.item(0);
			/*String userId = XmlUtil.getCharacterDataFromElement(userNode);*/
			//String userId = uID = XmlUtil.getCharacterDataFromElement(userNode);
			userId = uID = XmlUtil.getCharacterDataFromElement(userNode);
			//LogUtil.info("userId"+userId);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			//String password = IMonitorUtil.getHashPassword(XmlUtil.getCharacterDataFromElement(passwordNode));
			password = IMonitorUtil.getHashPassword(XmlUtil.getCharacterDataFromElement(passwordNode));
			//LogUtil.info("password"+password);
			
			
			String result = userManager.isUserExists(customerId, userId, password);
			//LogUtil.info("result"+result);
			
			resutlXml  = "<imonitor>";
			resutlXml += "<customerid>";
			resutlXml += customerId;
			resutlXml += "</customerid>";
			resutlXml += "<userid>";
			resutlXml += userId;
			resutlXml += "</userid>";
			resutlXml += "<status>";
			resutlXml += result;
			resutlXml += "</status>";
			if(result.equalsIgnoreCase("success")){
				resutlXml += "<token>";
				resutlXml += customerId;
				resutlXml += "</token>";
			}
			resutlXml += "</imonitor>";
			
		} catch (ParserConfigurationException e) {
			resutlXml = "Couldn't parse the input";
			e.printStackTrace();
		} catch (SAXException e) {
			resutlXml = "Couldn't parse the input";
			e.printStackTrace();
		} catch (IOException e) {
			resutlXml = "Couldn't parse the input";
			e.printStackTrace();
		}

		//sumit start - Dec12, 2013: Check for Notification
		User user = null;
		try {
			user = (User) userManager.getUserByUserIdForMID(customerId, userId, password);
		} catch (Exception e) {
			LogUtil.info("ERROR with user: "+e);
		}
		
		
		if(user != null)
		{
			if(user.getRole().getName().equals(Constants.MAIN_USER)){
				// Function Call to Notify Main User
				notifyMainUserForMIDLogin(user, Constants.LOG_IN);
			}else if(user.getRole().getName().equals(Constants.NORMAL_USER)){
				// Function Call to get MainUser and Notify
				getMainUserForMIDLoginAndNotify(user, Constants.LOG_IN);
				
			}else if(user.getRole().getName().equals(Constants.ADMIN_USER)){
				// Function stub: TBD
			}
			//sumit end - Dec12, 2013: Check for Notification
		}
		//LogUtil.info("resutlXml: "+resutlXml);
		return resutlXml;
	}
	
	/**
	 * @author sumit kumar
	 * @param user
	 * @param alertName
	 * 
	 * Notify Main user for MID Login if system alert is configured.
	 */
	protected void notifyMainUserForMIDLogin(User user, String alertName){
		SystemAlertManager systemAlertManager = new SystemAlertManager();
		UserManager userManager = new UserManager();
		Customer customer = userManager.getCustomerByMainUserId(user.getId());
		List<SystemAlertAction> systemAlertActions = systemAlertManager.getSystemAlertActionByUserId(user.getId());
		String messageMail = "User ID: "+user.getUserId();
		String messageSms = "User ID: "+user.getUserId();
 
		try {
			if(alertName.equalsIgnoreCase(Constants.LOG_IN)){
				messageMail += " is Logged in (Mobile Internet Device). <br>";
				messageSms += " is Logged in via MID.";
			}
			
			String[] recipiantsBYsms ={user.getMobile()};
			String[] recipiantsBYmail ={user.getEmail()};
			
			
			//1. Check if system alert has been configured or not.
			for(SystemAlertAction saa : systemAlertActions){
				String alertTypeName = saa.getSystemAlert().getName();
				String actionTypeName = saa.getActionType().getName();
				//2. Send appropriate notification if configured.
				if(alertTypeName.equalsIgnoreCase(Constants.LOG_IN) && actionTypeName.equalsIgnoreCase(Constants.SEND_EMAIL)){
					EMailNotifications eMailNotifications = new EMailNotifications();
					eMailNotifications.notify(messageMail,recipiantsBYmail, null,user.getUserId());
				}
				if(alertTypeName.equalsIgnoreCase(Constants.LOG_IN) && actionTypeName.equalsIgnoreCase(Constants.SEND_SMS)){
					SmsNotifications smsNotifications = new SmsNotifications();
					smsNotifications.notify(messageSms,recipiantsBYsms, null,customer);
				}
			}
		} catch (Exception e) {
			LogUtil.info("Error while sending notification for MID Login.", e);
		}
	}
	
	/**
	 * @author sumit kumar
	 * @param user
	 * @param alertName
	 * 
	 * Get Main User and notify for MID Login if system alert is configured
	 */
	protected void getMainUserForMIDLoginAndNotify(User user, String alertName){
		UserManager userManager = new UserManager();
		//1. Get Main user for the normal user.
		User mainUser = userManager.getMainUserForNormalUser(user);
		Customer customer = userManager.getCustomerByMainUserId(user.getId());
		SystemAlertManager systemAlertManager = new SystemAlertManager();
		List<SystemAlertAction> systemAlertActions = systemAlertManager.getSystemAlertActionByUserId(mainUser.getId());
		String messageMail = "User ID: "+user.getUserId();
		String messageSms = "User ID: "+user.getUserId();
 
		try {
			if(alertName.equalsIgnoreCase(Constants.LOG_IN)){
				messageMail += " is Logged in (Mobile Internet Device). <br>";
				messageSms += " is Logged in via MID.";
			}
			
			String[] recipiantsBYsms ={mainUser.getMobile()};
			String[] recipiantsBYmail ={mainUser.getEmail()};
			
			
			//1. Check if system alert has been configured or not.
			for(SystemAlertAction saa : systemAlertActions){
				String alertTypeName = saa.getSystemAlert().getName();
				String actionTypeName = saa.getActionType().getName();
				//2. Send appropriate notification if configured.
				if(alertTypeName.equalsIgnoreCase(Constants.LOG_IN) && actionTypeName.equalsIgnoreCase(Constants.SEND_EMAIL)){
					EMailNotifications eMailNotifications = new EMailNotifications();
					eMailNotifications.notify(messageMail,recipiantsBYmail, null,mainUser.getUserId());
				}else if(alertTypeName.equalsIgnoreCase(Constants.LOG_IN) && actionTypeName.equalsIgnoreCase(Constants.SEND_SMS)){
					SmsNotifications smsNotifications = new SmsNotifications();
					smsNotifications.notify(messageSms,recipiantsBYsms, null,customer);
				}
			}
		} catch (Exception e) {
			LogUtil.info("Error while sending notification for MID Login.", e);
		}
	}
	/* ****************************************** sumit end : Notification for MID Login ************************************** */
	
	public String listDevices(String xml){
		String resutlXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			NodeList tokenNodes = document.getElementsByTagName("token");
			Element tokenNode = (Element) tokenNodes.item(0);
			String token = XmlUtil.getCharacterDataFromElement(tokenNode);
//			Now we have customer Id as token. Later we'll take the customer id from our session using this token.
			CustomerManager customerManager = new CustomerManager();
			List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfCustomerByCustomerId(token);
			resutlXml  = "<imonitor>";
			if(gateWays.size() < 1){
				resutlXml += "<status>";
				resutlXml += "failure";
				resutlXml += "</status>";
			}else{
				
			}
			
			resutlXml += "<storage>";
				resutlXml += "<totalspace>";
				resutlXml += "978";
				resutlXml += "</totalspace>";
				resutlXml += "<usedspace>";
				resutlXml += "652";
				resutlXml += "</usedspace>";
			resutlXml += "</storage>";
			resutlXml += "<gateways>";
			for (GateWay gateWay : gateWays) {
				resutlXml += "<id>";
				resutlXml += gateWay.getId();
				resutlXml += "</id>";
				resutlXml += "<macid>";
				resutlXml += gateWay.getMacId();
				resutlXml += "</macid>";
				resutlXml += "<status>";
				resutlXml += gateWay.getStatus().getName();
				resutlXml += "</status>";
				resutlXml += "<devices>";
				Set<Device> devices = gateWay.getDevices();
				for (Device device : devices) {
					resutlXml += "<device>";
					resutlXml += "<id>";
					resutlXml += device.getGeneratedDeviceId();
					resutlXml += "</id>";
					resutlXml += "<name>";
					resutlXml += device.getFriendlyName();
					resutlXml += "</name>";
					resutlXml += "<type>";
					resutlXml += device.getDeviceType().getDetails();
					resutlXml += "</type>";
					resutlXml += "<status>";
					resutlXml += device.getLastAlert().getAlertCommand();
					resutlXml += "</status>";
					resutlXml += "<levelstatus>";
					resutlXml += device.getCommandParam();
					resutlXml += "</levelstatus>";
					resutlXml += "</device>";
				}
				resutlXml += "</devices>";
			}
			resutlXml += "</gateways>";
			resutlXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resutlXml  = "Couldn't parse the input";
			e.printStackTrace();
		} catch (SAXException e) {
			resutlXml = "Couldn't parse the input";
			e.printStackTrace();
		} catch (IOException e) {
			resutlXml = "Couldn't parse the input";
			e.printStackTrace();
		}
		return resutlXml;
	}
	

	public String controlDevice(String xml){
		String resutlXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			NodeList tokenNodes = document.getElementsByTagName("token");
			Element tokenNode = (Element) tokenNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(tokenNode);
//			Now we have customer Id as token. Later we'll take the customer id from our session using this token.
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
			NodeList commandNodes = document.getElementsByTagName("commandname");
			Element commandNode = (Element) commandNodes.item(0);
			String command = XmlUtil.getCharacterDataFromElement(commandNode);
			
			NodeList levelNodes = document.getElementsByTagName("levelstatus");
			Element levelNode = (Element) levelNodes.item(0);
			String level = null;
			if(levelNode != null){
				level = XmlUtil.getCharacterDataFromElement(levelNode);
			}
			
			DeviceManager deviceManager = new DeviceManager();
			Object[] result = deviceManager.checkDeviceAndReturnCommunicationDetails(deviceId, customerId, command);
			if(result == null){
				resutlXml  = "We couldn't find a valid device with your inputs !!";
			} else{
				String ip = IMonitorUtil.convertToString(result[3]);
				String macId = IMonitorUtil.convertToString(result[0]);
				int port = Integer.parseInt(IMonitorUtil.convertToString(result[4]));
				UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(command);
				Class<?> className = updatorModel.getClassName();
				ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
				Device device = new Device();
				device.setGeneratedDeviceId(deviceId);
				GateWay gateWay = new GateWay();
				gateWay.setMacId(macId);
				Agent agent = new Agent();
				agent.setControllerReceiverPort(port);
				agent.setIp(ip);
				gateWay.setAgent(agent);
				device.setGateWay(gateWay);
				if(level != null){
					device.setCommandParam(level);
				}
				ActionModel actionModel = new ActionModel(device, null);
				controllerAction.executeCommand(actionModel);
				waitForResult(controllerAction);
				device = controllerAction.getActionModel().getDevice();
//				Refer alertnotifier and re-write the code.
//				IMonitorUtil.executeActions(command, queue , ip, port);
				resutlXml  = "<imonitor>";
				resutlXml  += "<status>";
				resutlXml  += "success";
				resutlXml  += "</status>";
				resutlXml  += "<levelstatus>";
				resutlXml  += device.getCommandParam();
				resutlXml  += "</levelstatus>";
				resutlXml += "</imonitor>";
			}
			
		} catch (ParserConfigurationException e) {
			resutlXml = "Couldn't parse the input";
			e.printStackTrace();
		} catch (SAXException e) {
			resutlXml = "Couldn't parse the input";
			e.printStackTrace();
		} catch (IOException e) {
			resutlXml = "Couldn't parse the input";
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			resutlXml = "The command may not be implemented. Please check with CMS administrator";
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			resutlXml = "The command may not be implemented. Please check with CMS administrator";
			e.printStackTrace();
		}
		
		return resutlXml;
	}
	
	public String startLiveStreaming(String xml){
		String resutlXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
//			NodeList tokenNodes = document.getElementsByTagName("token");
//			Element tokenNode = (Element) tokenNodes.item(0);
//			String customerId = XmlUtil.getCharacterDataFromElement(tokenNode);
//			Now we have customer Id as token. Later we'll take the customer id from our session using this token.
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
//			Device as input - It has generatedDeviceId and imvg Id.
			Device device =  new Device();
			device.setGeneratedDeviceId(deviceId);
			
			DeviceManager deviceManager = new DeviceManager();
			GateWay gateWay = deviceManager.checkDeviceAndReturnGateWayDetails(deviceId);
			device.setGateWay(gateWay);
			
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new GetLiveStreamAction();
			controllerAction.executeCommand(actionModel);
			String url = Constants.FAILURE;
			waitForResult(controllerAction);
			url = IMonitorUtil.convertToString(controllerAction.getActionModel().getData());
			resutlXml  = "<imonitor>";
			resutlXml  += "<rtspurl>";
			resutlXml  += url;
			resutlXml  += "</rtspurl>";
			resutlXml += "</imonitor>";
			
		} catch (ParserConfigurationException e) {
			resutlXml = "Couldn't parse the input";
			e.printStackTrace();
		} catch (SAXException e) {
			resutlXml = "Couldn't parse the input";
			e.printStackTrace();
		} catch (IOException e) {
			resutlXml = "Couldn't parse the input";
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			resutlXml = "The command may not be implemented. Please check with CMS administrator";
			e.printStackTrace();
		} 
		
		return resutlXml;
	}
	private boolean waitForResult(ImonitorControllerAction controllerAction) {
		String tOut = ControllerProperties.getProperties().get(Constants.TIMOUT_DURATION);
		long timeOut = Long.parseLong(tOut);
		long waitTime = 1000;
		do{
			timeOut = timeOut - waitTime;
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(timeOut < 0){
				return false;
			}
		}while(!controllerAction.isResultExecuted());
		return true;
	}
}
