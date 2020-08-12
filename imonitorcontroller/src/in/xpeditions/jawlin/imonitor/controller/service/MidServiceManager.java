/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.action.AcControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcFanModeControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcSwingControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.AllonoffAction;
import in.xpeditions.jawlin.imonitor.controller.action.CameraNightVisionControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.CameraPanTiltControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.ConfigureAddActions;
import in.xpeditions.jawlin.imonitor.controller.action.ConfigureDeleteAction;
import in.xpeditions.jawlin.imonitor.controller.action.ConfigureEditAction;
import in.xpeditions.jawlin.imonitor.controller.action.CurtainCloseAction;
import in.xpeditions.jawlin.imonitor.controller.action.CurtainopenAction;
import in.xpeditions.jawlin.imonitor.controller.action.GetLiveStreamAction;
import in.xpeditions.jawlin.imonitor.controller.action.IDUFanDirectionControl;
import in.xpeditions.jawlin.imonitor.controller.action.IDUFanVolumeControl;
import in.xpeditions.jawlin.imonitor.controller.action.IduTemperatureControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;

import in.xpeditions.jawlin.imonitor.controller.action.IndoorUnitActionControl;
import in.xpeditions.jawlin.imonitor.controller.action.ResetFilterAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScenarioCreateAction;
import in.xpeditions.jawlin.imonitor.controller.action.NetworkStatusAction;
import in.xpeditions.jawlin.imonitor.controller.action.PartialCurtainCloseStart;
import in.xpeditions.jawlin.imonitor.controller.action.PartialCurtainCloseStop;
import in.xpeditions.jawlin.imonitor.controller.action.PartialCurtainOpenStart;
import in.xpeditions.jawlin.imonitor.controller.action.PartialCurtainOpenStop;
import in.xpeditions.jawlin.imonitor.controller.action.ScenarioExecutionAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScenarioUpdateAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScheduleActivateAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScheduleCreateAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScheduleDeactivateAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScheduleDeleteAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScheduleUpdateAction;
import in.xpeditions.jawlin.imonitor.controller.action.UpdateAction;
import in.xpeditions.jawlin.imonitor.controller.action.VirtualSwitchConfigurationAction;
import in.xpeditions.jawlin.imonitor.controller.action.changePasswordUser;
import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Action;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AdminSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Functions;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.NetworkStats;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.OTPForSelfRegistration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.PushNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.RssiInfo;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScenarioAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScheduleAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserDeviceAccess;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserScenarioAccess;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ActionTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AgentManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.IconManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.LocationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScenarioManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScheduleManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.StatusManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.SuperUserManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.SmsNotifications;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.FTPUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;
import in.xpeditions.jawlin.imonitor.util.UpdatorModel;
import in.xpeditions.jawlin.imonitor.util.XmlUtil;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceConfigurationManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

import imonitor.cms.alexa.proactive.service.AlexaProactiveUpdateScenarioService;
import imsipl.cms.controller.user.notifications.WelComeNotifications;
import in.xpeditions.jawlin.imonitor.controller.action.RGBColorPickerAction;


/**
 * @author Coladi
 *
 */
public class MidServiceManager {
	
	private Map<String, ResourceBundle> resourceBundles = null; 
	public MidServiceManager()
	{
		resourceBundles = new HashMap<String, ResourceBundle>();
		resourceBundles.put(Constants.LOCALE_KEY_EN_US, ResourceBundle.getBundle(Constants.UI_MSG_RESOURCE, Constants.LOCALE_EN_US));
		resourceBundles.put(Constants.LOCALE_KEY_KA_IN, ResourceBundle.getBundle(Constants.UI_MSG_RESOURCE, Constants.LOCALE_KA_IN));
	}

	private String formatMessage(String localeKey, String message)
	{
		String result = message;
		ResourceBundle rb = resourceBundles.get(localeKey);
		if(rb == null)rb = resourceBundles.get(Constants.LOCALE_KEY_EN_US);
		String args[] = null;
		String messageId = message;
		try 
		{
			if(message.contains(Constants.MESSAGE_DELIMITER_1))
			{
				String temp[] = message.split(Constants.REGEX_ESCAPE+Constants.MESSAGE_DELIMITER_1);
				messageId = temp[0];
				args = temp[1].split(Constants.REGEX_ESCAPE+Constants.MESSAGE_DELIMITER_2);
				messageId = rb.getString(messageId);
				result = replaceMessageArguments(messageId, args);
			}
			else
			{
				result = rb.getString(messageId);
			}
		} 
		catch (Exception e) {LogUtil.info("ERROR",e);}
		
		return result;
	}
	
	private String replaceMessageArguments(String message, String[] args)
	{
		for(int i=0;i<args.length;i++)
		{
			message = message.replace("{"+i+"}", args[i]);
		}
		return message;
	}
	
	public String deviceStatus (String xml) {
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("macid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String gateWayId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			GatewayManager gateWay=new GatewayManager();
			CustomerManager customer=new CustomerManager();
			Device device=new Device();
			Customer customerfromdb=customer.getCustomerByCustomerId(customerId);
			GateWay gateWays=gateWay.getGateWayByCustomer(customerfromdb);
			String macid=gateWays.getMacId();
			
			if(!(macid.equals(gateWayId))){
				resultXml  = "We couldn't find a valid GateWay with your inputs !!";
			} 
			else
			{
			device.setGateWay(gateWays);
			ActionModel actionModel = new ActionModel(device,null);
			ImonitorControllerAction UpadteAction = new UpdateAction();
			UpadteAction.executeCommand(actionModel);
			boolean exResult = waitForResult(UpadteAction);
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += exResult ? "success" : "failure";
			resultXml += "</status>";
			if(exResult)
			{
			resultXml +="<responsetime>";
			resultXml +="30";
			resultXml +="</responsetime>";
			}
			resultXml += "</imonitor>";
		}
			} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		}
		return resultXml;
	}
	/***************************************************************************
	 * Project Name : CMS
	 * Project Code : CMS
	 * Function : Retrieve Gateway details for MID Application
	 * Desc: Below code retrieves gateway details to be sent to MID application.
	 * Developed by: Kantharaj
	 * Change History : Appending devicePostion to data sent to MID.
	 * Changes done by : Sumit Kumar Date: June 28, 2013.
	 * Date: July 01, 2013
	 * ************************************************************************/
	
	/* ************************ sumit start : SUB USER RESTRICTION **************************** */
	/*
	 * ******************************* ORIGINAL CODE BEFORE SUB USER RESTRICTION **************************	
	public String synchronizeGateWayDetailsOld(String xml){
		//LogUtil.info("synchronizeGateWayDetailsOld : IN");
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);

			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
//			Now we have customer Id as token. Later we'll take the customer id from our session using this token.
			//Vibhu start
			DeviceManager deviceManager = new DeviceManager();
			List <Rule>rules = null;
			Device devWithRules = null;
			//Vibhu end
			CustomerManager customerManager = new CustomerManager();
			List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfCustomerByCustomerId(customerId);
			
			
			
			resultXml  = "<imonitor>";
			if(gateWays.size() < 1){
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
			}else{
				resultXml += "<gateways>";
				for (GateWay gateWay : gateWays) {
					resultXml += "<gateway>";
					resultXml += "<macid>";
					resultXml += gateWay.getMacId();
					resultXml += "</macid>";
					resultXml += "<localip>";
					resultXml += gateWay.getLocalIp();
					resultXml += "</localip>";
					resultXml += "<status>";
					resultXml += gateWay.getStatus().getName();
					resultXml += "</status>";
					resultXml += "<mode>";
					String mode=gateWay.getCurrentMode();
					if(mode.equals("0"))
					resultXml += "HOME";
					if(mode.equals("1"))
					resultXml += "STAY";
					if(mode.equals("2"))
					resultXml += "AWAY";
					resultXml += "</mode>";
					resultXml += "<AllonStatus>";
					resultXml += gateWay.getAllOnOffStatus();
					resultXml += "</AllonStatus>";
					resultXml += "<devices>";
					Set<Device> devices = gateWay.getDevices();
					for (Device device : devices) {
						//sumit start: avoid listing of devices that are configured NOT to be listed by the user.	
						if(device.getFriendlyName().equals("Home") 
								|| device.getFriendlyName().equals("Stay") 
								|| device.getFriendlyName().equals("Away")
								|| device.getEnableList().equals("0"))
						{
							continue;
						}

						resultXml += "<device>";
						resultXml += "<id>";
						resultXml += device.getGeneratedDeviceId();
						resultXml += "</id>";
						resultXml += "<name>";
						resultXml += device.getFriendlyName();
						resultXml += "</name>";
						//sumit begin
						String modelNumber = device.getModelNumber();
						resultXml += "<modelnumber>";
						if(modelNumber != null){
							resultXml += modelNumber;
						}
						resultXml += "</modelnumber>";
						resultXml += "<icon>";
						resultXml += device.getIcon().getFileName();
						resultXml += "</icon>";
						//sumit end
						resultXml += "<type>";
						resultXml += device.getDeviceType().getDetails();
						resultXml += "</type>";
						resultXml += "<location>";
						resultXml += device.getLocation().getName();
						resultXml += "</location>";
						resultXml += "<licon>";
						resultXml += device.getLocation().getIconFile();
						resultXml += "</licon>";
						resultXml += "<status>";
						resultXml += device.getLastAlert().getAlertCommand();
						resultXml += "</status>";
						resultXml += "<levelstatus>";
						resultXml += device.getCommandParam();
						resultXml += "</levelstatus>";
						resultXml += "<batterylevel>";
						resultXml += device.getBatteryStatus();
						resultXml += "</batterylevel>";
						resultXml += "<armstatus>";
						//vibhu start
						devWithRules = deviceManager.getDeviceWithRulesByGeneratedDeviceId(device.getGeneratedDeviceId());
						rules = devWithRules.getRules();
						if(rules != null && rules.size() >0)
						{
							resultXml += device.getArmStatus().getName();
						}
						else
						{
							resultXml += "Running";
						}
						//vibhu end
						resultXml += "</armstatus>";
						resultXml +="<enablestatus>";
						resultXml += device.getEnableStatus();
						resultXml +="</enablestatus>";
						
						String top =device.getLocationMap().getTop(); ;
						resultXml += "<top>";
						if(top != null && !top.equals(""))
							resultXml +=top;
						else
							resultXml +="-1";
						resultXml += "</top>";
						String left =device.getLocationMap().getLeftMap(); 
						resultXml += "<left>";
						if(left != null && !left.equals(""))
							resultXml +=left;
						else
							resultXml +="-1";
						
						resultXml += "</left>";
						//sumit start
						resultXml += "<devicePosition>";
						resultXml += device.getPosIdx();
						resultXml += "</devicePosition>";
						//sumit end
						
						if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
						{
							acConfiguration accofig=(acConfiguration) device.getDeviceConfiguration();
							if(accofig != null)
							{
								resultXml +="<Config>";
								resultXml += "<FM>";   //FM- FanMode
								resultXml += accofig.getFanMode();
								resultXml += "</FM>";
								resultXml += "<MV>";   //ModeValue
								resultXml += accofig.getFanModeValue();
								resultXml += "</MV>";
								resultXml += "<Acswing>";   //ModeValue
								resultXml += accofig.getAcSwing();
								resultXml += "</Acswing>";
								resultXml +="</Config>";
							}
							
						}
						
						
						resultXml += "</device>";
						
					}
					resultXml += "</devices>";
					resultXml += "</gateway>";
				}
				resultXml += "</gateways>";
			}
			resultXml += "</imonitor>";
			//LogUtil.info(resultXml);
			
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		}
		LogUtil.info("synchronizeGateWayDetails : OUT");
		return resultXml;
	}*/
	


	public String synchronizeGateWayDetails(String xml){
		//LogUtil.info("synchronizeGateWayDetails : IN");
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);

			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
//			Now we have customer Id as token. Later we'll take the customer id from our session using this token.
			//Vibhu start
			DeviceManager deviceManager = new DeviceManager();
			List <Rule>rules = null;
			Device devWithRules = null;
			//Vibhu end
			CustomerManager customerManager = new CustomerManager();
			List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfCustomerByCustomerId(customerId);
int count = 0;
			resultXml  = "<imonitor>";
			if(gateWays.size() < 1){
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
			}else{
				resultXml += "<gateways>";
				for (GateWay gateWay : gateWays) {
					if(count == 0){
					resultXml += "<gateway>";
					resultXml += "<macid>";
					resultXml += gateWay.getMacId();
					resultXml += "</macid>";
					resultXml +="<version>";
					resultXml +=gateWay.getGateWayVersion();
					resultXml +="</version>";
					resultXml += "<localip>";
					resultXml += gateWay.getLocalIp();
					resultXml += "</localip>";
					resultXml += "<status>";
					resultXml += gateWay.getStatus().getName();
					resultXml += "</status>";
					resultXml += "<mode>";
					String mode=gateWay.getCurrentMode();
					if(mode.equals("0"))
					resultXml += "HOME";
					if(mode.equals("1"))
					resultXml += "STAY";
					if(mode.equals("2"))
					resultXml += "AWAY";
					resultXml += "</mode>";
					resultXml += "<AllonStatus>";
					resultXml += gateWay.getAllOnOffStatus();
					resultXml += "</AllonStatus>";
					
					resultXml += "<devices>";
					}
					Set<Device> devices = gateWay.getDevices();
					
					User userFromDB = userManager.getUserWithCustomerByUserIdAndUpdateStatus(userId, customerId, hashPassword);
					if(userFromDB.getRole().getName().equalsIgnoreCase(Constants.NORMAL_USER)){
						
						//LogUtil.info("NORMAL USER");
						
						List<subUserDeviceAccess> accessibleDevices = userManager.listDevicesForSubuser(userFromDB.getId());
						if((accessibleDevices != null) && (accessibleDevices.size() >0)){
						
							//LogUtil.info("DEVICES CONFIGURED FOR NORMAL USER");
							
							for (Device device : devices) {
								if(device.getFriendlyName().equals("Home") 
										|| device.getFriendlyName().equals("Stay") 
										|| device.getFriendlyName().equals("Away")
										|| device.getEnableList().equals("0"))
								{
									//LogUtil.info("Virtual Device");
									continue;
								}
								//sumit start: avoid listing of devices that are configured NOT to be listed by the user.	
								//LogUtil.info(new XStream().toXML(accessibleDevices));
								for(subUserDeviceAccess su : accessibleDevices){	
									
									//LogUtil.info("\n\n\nConf Device : "+su.getDevice().getId()+", Actual Device : "+device.getId());
									if(su.getDevice().getId() == device.getId()){
										
										//LogUtil.info("DEVICE MATCHED");
										
										resultXml += "<device>";
										resultXml += "<id>";
										resultXml += device.getGeneratedDeviceId();
										resultXml += "</id>";
										resultXml += "<deviceId>";
										resultXml += device.getDeviceId();
										resultXml += "</deviceId>";
										resultXml += "<name>";
										resultXml += device.getFriendlyName();
										resultXml += "</name>";
										//sumit begin
										String modelNumber = device.getModelNumber();
										resultXml += "<modelnumber>";
										if(modelNumber != null){
											resultXml += modelNumber;
										}
										resultXml += "</modelnumber>";
										resultXml += "<icon>";
										resultXml += device.getIcon().getFileName();
										resultXml += "</icon>";
										//sumit end
										resultXml += "<type>";
										resultXml += device.getDeviceType().getDetails();
										resultXml += "</type>";
										resultXml += "<location>";
										resultXml += device.getLocation().getName();
										resultXml += "</location>";
										resultXml += "<licon>";
										resultXml += device.getLocation().getIconFile();
										resultXml += "</licon>";
										resultXml += "<status>";
										resultXml += device.getLastAlert().getAlertCommand();
										resultXml += "</status>";
										resultXml += "<levelstatus>";
										resultXml += device.getCommandParam();
										resultXml += "</levelstatus>";
										resultXml += "<batterylevel>";
										resultXml += device.getBatteryStatus();
										resultXml += "</batterylevel>";
										resultXml += "<armstatus>";
										//vibhu start
										devWithRules = deviceManager.getDeviceWithRulesByGeneratedDeviceId(device.getGeneratedDeviceId());
										rules = devWithRules.getRules();
										if(rules != null && rules.size() >0){
											resultXml += device.getArmStatus().getName();
										}else{
											resultXml += "Running";
										}
										//vibhu end
										resultXml += "</armstatus>";
										resultXml +="<enablestatus>";
										resultXml += device.getEnableStatus();
										resultXml +="</enablestatus>";
										
										String top =device.getLocationMap().getTop(); ;
										resultXml += "<top>";
										if(top != null && !top.equals(""))
											resultXml +=top;
										else
											resultXml +="-1";
										resultXml += "</top>";
										String left =device.getLocationMap().getLeftMap(); 
										resultXml += "<left>";
										if(left != null && !left.equals(""))
											resultXml +=left;
										else
											resultXml +="-1";
										
										resultXml += "</left>";
										//sumit start
										resultXml += "<devicePosition>";
										resultXml += device.getPosIdx();
										resultXml += "</devicePosition>";
										//sumit end
										if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER)){
											acConfiguration accofig=(acConfiguration) device.getDeviceConfiguration();
											if(accofig != null){
												resultXml +="<Config>";
												resultXml += "<FM>";   //FM- FanMode
												resultXml += accofig.getFanMode();
												resultXml += "</FM>";
												resultXml += "<MV>";   //ModeValue
												resultXml += accofig.getFanModeValue();
												resultXml += "</MV>";
												resultXml += "<Acswing>";   //ModeValue
												resultXml += accofig.getAcSwing();
												resultXml += "</Acswing>";
												resultXml +="</Config>";
											}
										}
										if (device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
										{
											//LogUtil.info("VIA UNit config block");
											//DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
											//IndoorUnitConfiguration indoorUnitConfiguration=(IndoorUnitConfiguration) Dvconf.getDeviceConfigurationById(dcId);
											IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) device.getDeviceConfiguration();
											resultXml +="<Config>";
											resultXml +="<fanModeCapability>";
											resultXml +=configuration.getFanModeCapability();
											resultXml +="</fanModeCapability>";
											resultXml +="<coolModeCapability>";
											resultXml +=configuration.getCoolModeCapability();
											resultXml +="</coolModeCapability>";
											resultXml +="<heatModeCapability>";
											resultXml +=configuration.getHeatModeCapability();
											resultXml +="</heatModeCapability>";
											resultXml +="<autoModeCapability>";
											resultXml +=configuration.getAutoModeCapability();
											resultXml +="</autoModeCapability>";
											resultXml +="<dryModeCapability>";
											resultXml +=configuration.getDryModeCapability();
											resultXml +="</dryModeCapability>";
											resultXml +="<fanDirectionLevelCapability>";
											resultXml +=configuration.getFanDirectionLevelCapability();
											resultXml +="</fanDirectionLevelCapability>";
											resultXml +="<fanDirectionCapability>";
											resultXml +=configuration.getFanDirectionCapability();
											resultXml +="</fanDirectionCapability>";
											resultXml +="<fanVolumeLevelCapability>";
											resultXml +=configuration.getFanVolumeLevelCapability();
											resultXml +="</fanVolumeLevelCapability>";
											resultXml +="<fanVolumeCapability>";
											resultXml +=configuration.getFanVolumeCapability();
											resultXml +="</fanVolumeCapability>";
											resultXml +="<coolingUpperlimit>";
											resultXml +=configuration.getCoolingUpperlimit();
											resultXml +="</coolingUpperlimit>";
											resultXml +="<coolingLowerlimit>";
											resultXml +=configuration.getCoolingLowerlimit();
											resultXml +="</coolingLowerlimit>";
											resultXml +="<heatingUpperlimit>";
											resultXml +=configuration.getHeatingUpperlimit();
											resultXml +="</heatingUpperlimit>";
											resultXml +="<heatingLowerlimit>";
											resultXml +=configuration.getHeatingLowerlimit();
											resultXml +="</heatingLowerlimit>";
											resultXml +="<ConnectStatus>";
											resultXml +=configuration.getConnectStatus();
											resultXml +="</ConnectStatus>";
											resultXml +="<CommStatus>";
											resultXml +=configuration.getCommStatus();
											resultXml +="</CommStatus>";
											resultXml +="<fanModeValue>";
											resultXml +=configuration.getFanModeValue();
											resultXml +="</fanModeValue>";
											resultXml +="<sensedTemperature>";
											resultXml +=configuration.getSensedTemperature();
											resultXml +="</sensedTemperature>";
											resultXml +="<fanVolumeValue>";
											resultXml +=configuration.getFanVolumeValue();
											resultXml +="</fanVolumeValue>";
											resultXml +="<fanDirectionValue>";
											resultXml +=configuration.getFanDirectionValue();
											resultXml +="</fanDirectionValue>";
											resultXml +="<eMessage>";
											resultXml +=configuration.getErrorMessage();
											resultXml +="</eMessage>";
											resultXml +="<filterSignStatus>";
											resultXml +=configuration.getFilterSignStatus();
											resultXml +="</filterSignStatus>";
											resultXml +="<temperatureValue>";
											resultXml +=configuration.getTemperatureValue();
											resultXml +="</temperatureValue>";
											resultXml +="</Config>";
										}
										resultXml += "</device>";
									
									}	
								}
							}
						}
					}else{
						for (Device device : devices) {
							//sumit start: avoid listing of devices that are configured NOT to be listed by the user.	
							if(device.getFriendlyName().equals("Home") 
									|| device.getFriendlyName().equals("Stay") 
									|| device.getFriendlyName().equals("Away")
									|| device.getEnableList().equals("0"))
							{
								continue;
							}

							resultXml += "<device>";
							resultXml += "<id>";
							resultXml += device.getGeneratedDeviceId();
							resultXml += "</id>";
							resultXml += "<deviceId>";
							resultXml += device.getDeviceId();
							resultXml += "</deviceId>";
							resultXml += "<name>";
							resultXml += device.getFriendlyName();
							resultXml += "</name>";
							//sumit begin
							String modelNumber = device.getModelNumber();
							resultXml += "<modelnumber>";
							if(modelNumber != null){
								resultXml += modelNumber;
							}
							resultXml += "</modelnumber>";
							resultXml += "<icon>";
							resultXml += device.getIcon().getFileName();
							resultXml += "</icon>";
							//sumit end
							resultXml += "<type>";
							resultXml += device.getDeviceType().getDetails();
							resultXml += "</type>";
							resultXml += "<location>";
							resultXml += device.getLocation().getName();
							resultXml += "</location>";
							resultXml += "<licon>";
							resultXml += device.getLocation().getIconFile();
							resultXml += "</licon>";
							resultXml += "<status>";
							resultXml += device.getLastAlert().getAlertCommand();
							resultXml += "</status>";
							resultXml += "<levelstatus>";
							resultXml += device.getCommandParam();
							resultXml += "</levelstatus>";
							resultXml += "<batterylevel>";
							resultXml += device.getBatteryStatus();
							resultXml += "</batterylevel>";
							resultXml += "<armstatus>";
							//vibhu start
							devWithRules = deviceManager.getDeviceWithRulesByGeneratedDeviceId(device.getGeneratedDeviceId());
							rules = devWithRules.getRules();
							if(rules != null && rules.size() >0)
							{
								resultXml += device.getArmStatus().getName();
							}
							else
							{
								resultXml += "Running";
							}
							//vibhu end
							resultXml += "</armstatus>";
							resultXml +="<enablestatus>";
							resultXml += device.getEnableStatus();
							resultXml +="</enablestatus>";
							
							String top =device.getLocationMap().getTop(); ;
							resultXml += "<top>";
							if(top != null && !top.equals(""))
								resultXml +=top;
							else
								resultXml +="-1";
							resultXml += "</top>";
							String left =device.getLocationMap().getLeftMap(); 
							resultXml += "<left>";
							if(left != null && !left.equals(""))
								resultXml +=left;
							else
								resultXml +="-1";
							
							resultXml += "</left>";
							//sumit start
							resultXml += "<devicePosition>";
							resultXml += device.getPosIdx();
							resultXml += "</devicePosition>";
							//sumit end
							
							if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
							{
								acConfiguration accofig=(acConfiguration) device.getDeviceConfiguration();
								if(accofig != null)
								{
									resultXml +="<Config>";
									resultXml += "<FM>";   //FM- FanMode
									resultXml += accofig.getFanMode();
									resultXml += "</FM>";
									resultXml += "<MV>";   //ModeValue
									resultXml += accofig.getFanModeValue();
									resultXml += "</MV>";
									resultXml += "<Acswing>";   //ModeValue
									resultXml += accofig.getAcSwing();
									resultXml += "</Acswing>";
									resultXml +="</Config>";
								}
								
							}
							
							if (device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
							{
								//LogUtil.info("VIA UNit config block");
								//DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
								//IndoorUnitConfiguration indoorUnitConfiguration=(IndoorUnitConfiguration) Dvconf.getDeviceConfigurationById(dcId);
								IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) device.getDeviceConfiguration();
								resultXml +="<Config>";
								resultXml +="<fanModeCapability>";
								resultXml +=configuration.getFanModeCapability();
								resultXml +="</fanModeCapability>";
								resultXml +="<coolModeCapability>";
								resultXml +=configuration.getCoolModeCapability();
								resultXml +="</coolModeCapability>";
								resultXml +="<heatModeCapability>";
								resultXml +=configuration.getHeatModeCapability();
								resultXml +="</heatModeCapability>";
								resultXml +="<autoModeCapability>";
								resultXml +=configuration.getAutoModeCapability();
								resultXml +="</autoModeCapability>";
								resultXml +="<dryModeCapability>";
								resultXml +=configuration.getDryModeCapability();
								resultXml +="</dryModeCapability>";
								resultXml +="<fanDirectionLevelCapability>";
								resultXml +=configuration.getFanDirectionLevelCapability();
								resultXml +="</fanDirectionLevelCapability>";
								resultXml +="<fanDirectionCapability>";
								resultXml +=configuration.getFanDirectionCapability();
								resultXml +="</fanDirectionCapability>";
								resultXml +="<fanVolumeLevelCapability>";
								resultXml +=configuration.getFanVolumeLevelCapability();
								resultXml +="</fanVolumeLevelCapability>";
								resultXml +="<fanVolumeCapability>";
								resultXml +=configuration.getFanVolumeCapability();
								resultXml +="</fanVolumeCapability>";
								resultXml +="<coolingUpperlimit>";
								resultXml +=configuration.getCoolingUpperlimit();
								resultXml +="</coolingUpperlimit>";
								resultXml +="<coolingLowerlimit>";
								resultXml +=configuration.getCoolingLowerlimit();
								resultXml +="</coolingLowerlimit>";
								resultXml +="<heatingUpperlimit>";
								resultXml +=configuration.getHeatingUpperlimit();
								resultXml +="</heatingUpperlimit>";
								resultXml +="<heatingLowerlimit>";
								resultXml +=configuration.getHeatingLowerlimit();
								resultXml +="</heatingLowerlimit>";
								resultXml +="<ConnectStatus>";
								resultXml +=configuration.getConnectStatus();
								resultXml +="</ConnectStatus>";
								resultXml +="<CommStatus>";
								resultXml +=configuration.getCommStatus();
								resultXml +="</CommStatus>";
								resultXml +="<fanModeValue>";
								resultXml +=configuration.getFanModeValue();
								resultXml +="</fanModeValue>";
								resultXml +="<sensedTemperature>";
								resultXml +=configuration.getSensedTemperature();
								resultXml +="</sensedTemperature>";
								resultXml +="<fanVolumeValue>";
								resultXml +=configuration.getFanVolumeValue();
								resultXml +="</fanVolumeValue>";
								resultXml +="<fanDirectionValue>";
								resultXml +=configuration.getFanDirectionValue();
								resultXml +="</fanDirectionValue>";
							//	resultXml +="<eMessage>";
							//	resultXml +=configuration.getErrorMessage();
							//	resultXml +="</eMessage>";
								resultXml +="<filterSignStatus>";
								resultXml +=configuration.getFilterSignStatus();
								resultXml +="</filterSignStatus>";
								resultXml +="<temperatureValue>";
								resultXml +=configuration.getTemperatureValue();
								resultXml +="</temperatureValue>";
								resultXml +="</Config>";
							}
							resultXml += "</device>";
						}
					}
					count++;
				
					
					
					if((gateWays.size() > 1) && (count == 3)){
						resultXml += "</devices>";
						resultXml += "</gateway>";
					}else if(gateWays.size() == 1){
					resultXml += "</devices>";
					resultXml += "</gateway>";
					}
				}
				resultXml += "</gateways>";
			}
			resultXml += "</imonitor>";
			
			
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		} catch (Exception e) {
			LogUtil.info("Caught Exception : ", e);
		}
		return resultXml;
	}
	/* ************************ sumit end : SUB USER RESTRICTION **************************** */
	
	@SuppressWarnings("unused")
	public String synchronizeDeviceDetails(String xml){
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
//			Now we have customer Id as token. Later we'll take the customer id from our session using this token.
			DeviceManager deviceManager = new DeviceManager();
			Device device = deviceManager.retrieveDeviceDetails(deviceId, customerId);
		
			
			resultXml  = "<imonitor>";
			if(device == null){
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
			}else{
				resultXml += "<device>";
				resultXml += "<id>";
				resultXml += device.getGeneratedDeviceId();
				resultXml += "</id>";
				resultXml += "<name>";
				resultXml += device.getFriendlyName();
				resultXml += "</name>";
				//sumit begin
				resultXml += "<icon>";
				resultXml += device.getIcon().getFileName();
				resultXml += "</icon>";
				//sumit end
				resultXml += "<type>";
				resultXml += device.getDeviceType().getDetails();
				resultXml += "</type>";
				resultXml += "<location>";
				resultXml += device.getLocation().getName();
				resultXml += "</location>";
				resultXml += "<status>";
				resultXml += device.getLastAlert().getAlertCommand();
				resultXml += "</status>";
				resultXml += "<levelstatus>";
				resultXml += device.getCommandParam();
				resultXml += "</levelstatus>";
				resultXml += "<batterylevel>";
				resultXml += device.getBatteryStatus();
				resultXml += "</batterylevel>";
				
				
				
				resultXml += "</device>";
				
				
				}
			resultXml += "</imonitor>";
		
			
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		}
		return resultXml;
	}
	
	/*
	 * ********************** ORIGINAL CODE : BEFORE SUB USER RESTRICTION ***********************
	 * public String synchronizeScenarioDetails(String xml){
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
//			Let's fetch the scenarios using the customer id.
			ScenarioManager scenarioManager = new ScenarioManager();
			List<Object[]> scenarios = scenarioManager.listAllScenariosByCustomerId(customerId);
			if(scenarios == null){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
//			Not a good method. Better we retrieve as scenario objects.
			Map<String, String> scenarioActions = new HashMap<String, String>();
			Map<String, String> scenarioDetails = new HashMap<String, String>();
			Map<String, Integer> scenarioId=new HashMap<String, Integer>();
			for (Object[] objects : scenarios) 
			{
				String scenarioName = IMonitorUtil.convertToString(objects[1]);
				String scenarioDescription = IMonitorUtil.convertToString(objects[2]);
				String ScenarioiD=IMonitorUtil.convertToString(objects[0]);
				int id=Integer.parseInt(ScenarioiD);
				scenarioId.put(scenarioName, id);
				scenarioDetails.put(scenarioName, scenarioDescription);
				String deviceId = IMonitorUtil.convertToString(objects[3]);
				String command = IMonitorUtil.convertToString(objects[4]);
				String level = IMonitorUtil.convertToString(objects[5]);
				String actions = scenarioActions.get(scenarioName);
				if(actions == null){
					actions = "";
				}
				actions += "<command>";
				actions += "<deviceid>";
				actions += deviceId;
				actions += "</deviceid>";
				actions += "<eventname>";
				actions += command;
				actions += "</eventname>";
				if(level != null){
					actions += "<level>";
					actions += level;
					actions += "</level>";
				}
				actions += "</command>";
				scenarioActions.put(scenarioName, actions);
			}
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "<scenarios>";
			
			Set<String> keySet = scenarioActions.keySet();
			for (String scenarioName : keySet) {
				resultXml += "<scenario>";
				resultXml += "<id>";
				resultXml += scenarioId.get(scenarioName);
				resultXml += "</id>";
				resultXml += "<name>";
				resultXml += scenarioName;
				resultXml += "</name>";
				resultXml += "<description>";
				resultXml += scenarioDetails.get(scenarioName);
				resultXml += "</description>";
				resultXml += scenarioActions.get(scenarioName);
				resultXml += "</scenario>";
			}
			resultXml += "</scenarios>";
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
			e.printStackTrace();
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
			e.printStackTrace();
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
			e.printStackTrace();
		}
		return resultXml;
	}*/

	public String synchronizeScenarioDetails(String xml){
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
//			Let's fetch the scenarios using the customer id.
			ScenarioManager scenarioManager = new ScenarioManager();
			List<Object[]> scenarios = scenarioManager.listAllScenariosByCustomerId(customerId);
			if(scenarios == null){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
//			Not a good method. Better we retrieve as scenario objects.
			Map<String, String> scenarioActions = new HashMap<String, String>();
			Map<String, String> scenarioDetails = new HashMap<String, String>();
			Map<String, Integer> scenarioId=new HashMap<String, Integer>();
			for (Object[] objects : scenarios) 
			{
				String scenarioName = IMonitorUtil.convertToString(objects[1]);
				String scenarioDescription = IMonitorUtil.convertToString(objects[2]);
				String ScenarioiD=IMonitorUtil.convertToString(objects[0]);
				int id=Integer.parseInt(ScenarioiD);
				scenarioId.put(scenarioName, id);
				scenarioDetails.put(scenarioName, scenarioDescription);
				String deviceId = IMonitorUtil.convertToString(objects[3]);
				String command = IMonitorUtil.convertToString(objects[4]);
				String level = IMonitorUtil.convertToString(objects[5]);
				String actions = scenarioActions.get(scenarioName);
				if(actions == null){
					actions = "";
				}
				actions += "<command>";
				actions += "<deviceid>";
				actions += deviceId;
				actions += "</deviceid>";
				actions += "<eventname>";
				actions += command;
				actions += "</eventname>";
				if(level != null){
					actions += "<level>";
					actions += level;
					actions += "</level>";
				}
				actions += "</command>";
				scenarioActions.put(scenarioName, actions);
			}
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "<scenarios>";
			
			Set<String> keySet = scenarioActions.keySet();
			//Filter the scenarios based on Sub User Scenarios Configurations
			User userFromDB = userManager.getUserWithCustomerByUserIdAndUpdateStatus(userId, customerId, hashPassword);	
			if(userFromDB.getRole().getName().equalsIgnoreCase(Constants.NORMAL_USER)){
				List<subUserScenarioAccess> accessibleScenarios = userManager.listScenariosForSubuser(userFromDB.getId());
				if((accessibleScenarios != null) && (accessibleScenarios.size() >0)){
					
					for (String scenarioName : keySet) {
						//LogUtil.info(new XStream().toXML(accessibleScenarios));
						for(subUserScenarioAccess su : accessibleScenarios){	
							
							//LogUtil.info("\n\n\nConf Device : "+su.getScenario().getId()+", Actual Scenario : "+scenarioId.get(scenarioName));
							if(su.getScenario().getId() == scenarioId.get(scenarioName)){
						
								resultXml += "<scenario>";
								resultXml += "<id>";
								resultXml += scenarioId.get(scenarioName);
								resultXml += "</id>";
								resultXml += "<name>";
								resultXml += scenarioName;
								resultXml += "</name>";
								resultXml += "<description>";
								resultXml += scenarioDetails.get(scenarioName);
								resultXml += "</description>";
								resultXml += scenarioActions.get(scenarioName);
								resultXml += "</scenario>";
							}
						}
					}
				}
			}else{
				for (String scenarioName : keySet) {
					resultXml += "<scenario>";
					resultXml += "<id>";
					resultXml += scenarioId.get(scenarioName);
					resultXml += "</id>";
					resultXml += "<name>";
					resultXml += scenarioName;
					resultXml += "</name>";
					resultXml += "<description>";
					resultXml += scenarioDetails.get(scenarioName);
					resultXml += "</description>";
					resultXml += scenarioActions.get(scenarioName);
					resultXml += "</scenario>";
				}
			}
			
			resultXml += "</scenarios>";
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
			e.printStackTrace();
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
			e.printStackTrace();
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
			e.printStackTrace();
		}
		return resultXml;
	}
// ****************************************************************** sumit begin *************************************************************
	public String controldevice(String xml){
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String uResult = userManager.isUserExists(customerId, userId, hashPassword);
			if(uResult.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
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
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;			
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
				
				if(command.equals(Constants.AC_ON))
				device.setCommandParam("22");
				
				if(command.equals(Constants.AC_OFF))
				device.setCommandParam("0");
				
				
				
				if(level != null){
					device.setCommandParam(level);
				}
				
				
				
				ActionModel actionModel = new ActionModel(device, null);
				controllerAction.executeCommand(actionModel);
				boolean exResult = waitForResult(controllerAction);
				device = controllerAction.getActionModel().getDevice();
//				Refer alertnotifier and re-write the code.
//				IMonitorUtil.executeActions(command, queue , ip, port);
				resultXml  = "<imonitor>";
				resultXml  += "<status>";
				//resultXml  += "success";
				resultXml += exResult ? "success" : "failure";
				resultXml  += "</status>";
				resultXml  += "<levelstatus>";
				resultXml  += device.getCommandParam();
				resultXml  += "</levelstatus>";
				resultXml += "</imonitor>";
			}
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (InstantiationException e) {
			resultXml = "The command may not be implemented. Please check with CMS administrator " + e.getMessage();
		} catch (IllegalAccessException e) {
			resultXml = "The command may not be implemented. Please check with CMS administrator " + e.getMessage();
		}
		return resultXml;
	}
	
	public String executeScenario(String xml){
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList scneraioNameNodes = document.getElementsByTagName("scenarioname");
			Element scneraioNameNode = (Element) scneraioNameNodes.item(0);
			String scenarioName = XmlUtil.getCharacterDataFromElement(scneraioNameNode);
			
			ScenarioManager scenarioManager = new ScenarioManager();
			// vibhu commented boolean exeResult = scenarioManager.executeScenarioActionsBySenarioName(customerId, scenarioName);
                        boolean exeResult = false;
			Scenario s = scenarioManager.getScenarioWithGatewayByNameAndCustId(customerId, scenarioName);
			ActionModel actionModel = new ActionModel(null, s);
			ImonitorControllerAction scenarioExecutionAction = new ScenarioExecutionAction();
			scenarioExecutionAction.executeCommand(actionModel);
			boolean resultFromImvg = IMonitorUtil.waitForScenarioResult(scenarioExecutionAction);
			if(resultFromImvg)
			{
				if(scenarioExecutionAction.isActionSuccess())
				{
					exeResult = true;
				}
			}
			
			if(!exeResult){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		} catch (Exception e){
			resultXml = "<imonitor><status>failure</status></imonitor>";
		}
		return resultXml;
	}
	
	public String armDevice(String xml){
		Status status = IMonitorUtil.getStatuses().get(Constants.ARM);
		return changeArmStatus(xml, status);
	}
	
	public String disarmDevice(String xml){
		Status status = IMonitorUtil.getStatuses().get(Constants.DISARM);
		return changeArmStatus(xml, status);
	}
	
	public String startLiveStream(String xml){
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}

			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			DeviceManager deviceManager = new DeviceManager();
			Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new GetLiveStreamAction();
			controllerAction.executeCommand(actionModel);
			String url = Constants.FAILURE;
			IMonitorUtil.waitForResult(controllerAction);
			url = IMonitorUtil.convertToString(controllerAction.getActionModel().getData());
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "<rtspurl>";
			resultXml += url;
			resultXml += "</rtspurl>";
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		}
		return resultXml;
	}
	
	public String controlCamera(String xml){
		
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
			NodeList controlPanTilts = document.getElementsByTagName("controlpantilt");
			Element controlPanTiltNode = (Element) controlPanTilts.item(0);
			String controlPanTilt = XmlUtil.getCharacterDataFromElement(controlPanTiltNode);
			
			DeviceManager deviceManager = new DeviceManager();
			Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
			CameraConfiguration cameraConfiguration = new CameraConfiguration();
			
			cameraConfiguration.setControlPantilt(controlPanTilt);
			device.setDeviceConfiguration(cameraConfiguration);
		
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new CameraPanTiltControlAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		}
		return resultXml;
	}
	
	public String controlNightVision(String xml){
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
			NodeList controlNightVisions = document.getElementsByTagName("controlnightvision");
			Element controlNightVisionNode = (Element) controlNightVisions.item(0);
			String controlNightVision = XmlUtil.getCharacterDataFromElement(controlNightVisionNode);
			
			DeviceManager deviceManager = new DeviceManager();
			Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
			CameraConfiguration cameraConfiguration = null;
			DeviceConfiguration deviceConfiguration = device.getDeviceConfiguration();
			if(deviceConfiguration == null){
				cameraConfiguration = new CameraConfiguration();
			}else{
				cameraConfiguration = (CameraConfiguration) deviceConfiguration;
			}
			cameraConfiguration.setControlNightVision(controlNightVision);
			device.setDeviceConfiguration(cameraConfiguration);
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new CameraNightVisionControlAction();
			controllerAction.executeCommand(actionModel);
			IMonitorUtil.waitForResult(controllerAction);
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		}
		return resultXml;
	}
	
	private String changeArmStatus(String xml, Status status) {
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}

			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			DeviceManager deviceManager = new DeviceManager();
			
			boolean exResult = false;
			// *************************************** sumit start: Handle [ARM/AWAY]_DEVICES_PROGRESS_FAIL ********************************
			String message = null;
			if(deviceId != null){
				String retVal = "";
				if(deviceId.equalsIgnoreCase("*")){
					//exResult  = deviceManager.updateDevicesArmStatusByCustomerId(customerId, status);
					CommandIssueServiceManager cism = new CommandIssueServiceManager();
					if(status.getName().equals(Constants.ARM))//AWAY
					{
						retVal = cism.activateModeInternal(customerId,Constants.ARM);
						retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
						if(retVal.startsWith("SUCCESS")){
							exResult = true;
						}else{
							//sumit: Handle ARM_DEVICES_PROGRESS_FAIL
							message = retVal;
						}
					}
					else//HOME
					{
						retVal = cism.activateModeInternal(customerId,Constants.DISARM);
						retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
						if(retVal.startsWith("SUCCESS")){
							exResult = true;
						}
					}
				}else if(deviceId.equalsIgnoreCase("*STAY")){ //STAY
					CommandIssueServiceManager cism = new CommandIssueServiceManager();
					retVal = cism.activateModeInternal(customerId,Constants.STAY);
					retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
					if(retVal.startsWith("SUCCESS")){
						exResult = true;
					}else{
						//sumit: Handle STAY_DEVICES_PROGRESS_FAIL
						message = retVal;
					}
				}else{//obsolete code
						//vibhu start
						CommandIssueServiceManager cism = new CommandIssueServiceManager();
						exResult = cism.deviceArmDisarmStatusUpdateFromMID(customerId, deviceId, status);
						//exResult = deviceManager.updateDeviceArmStatus(customerId, deviceId, status);
						//vibhu end
				}
			}
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += exResult ? "success" : "failure";
			resultXml += "</status>";
			if(message != null){
				resultXml += "<message>";
				resultXml += message;
				resultXml += "</message>";
				
			}
// ****************************************************************** sumit end ************************************************************
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "1.Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "2.Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "3.Couldn't parse the input";
		}
		catch (Exception e) {
			LogUtil.info("", e);
			resultXml = "<imonitor><status>failure</status></imonitor>";
		}
		return resultXml;
	}
	
	
	private boolean waitForResult(ImonitorControllerAction controllerAction) {
		//String tOut = ControllerProperties.getProperties().get(Constants.TIMOUT_DURATION);
		String tOut = ControllerProperties.getProperties().get(Constants.MID_TIMEOUT_DURATION);
		
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
	/********************************************************************
	 *  Desc:This Function Return's List of 10 Recent Alarm's To MID
	 *  
	 *  <imonitor> -ims
		<status>-stus
		<Alm> -Alarm
		<aId> alertId
		<aNm> alertName
		<aCd> alertCommand
		<iAId> imvgAlertId
		<iAT> imvgAlertTime
		<dId> deviceId
		<dNm> deviceName
		<tDf> timeDiff
	 * *******************************************************************/
	public String GetAlarm (String xml) {
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<ims>";
				resultXml += "<stus>";
				resultXml += "failure";
				resultXml += "</stus>";
				resultXml += "</ims>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("macid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String gateWayId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
//			<timeStamp>2012-11-02 10:40:13</timeStamp>
			
			NodeList TimeStampNodes = document.getElementsByTagName("timeStamp");
			Element timestampNode = (Element) TimeStampNodes.item(0);
			String TimeStamp = XmlUtil.getCharacterDataFromElement(timestampNode);
			
			
		
			
			GatewayManager gateWay=new GatewayManager();
			CustomerManager customer=new CustomerManager();
			Customer customerfromdb=customer.getCustomerByCustomerId(customerId);
			GateWay gateWays=gateWay.getGateWayByCustomer(customerfromdb);
			String macid=gateWays.getMacId();
			
			if(!(macid.equals(gateWayId))){
				resultXml += "<ims>";
				resultXml += "We couldn't find a valid GateWay with your inputs !!";
				resultXml += "</ims>";
			} 
			else
			{
			    AlertServiceManager alertserviceManger=new AlertServiceManager();
			   
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
					Timestamp timestamp = null;
					java.util.Date date = null;
					if(!TimeStamp.equals("null"))
					{
						try 
						{
							date = sdf.parse(TimeStamp);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					timestamp = new java.sql.Timestamp(date.getTime());	
					}
					else
					{
						timestamp=new Timestamp(0);
					}
					
					
				
				List<Object[]> Objects = alertserviceManger.listAlarmsForMid(customerfromdb,timestamp);
				resultXml  = "<ims>";
				if(Objects == null){
					resultXml = "<ims>";
					resultXml += "<stus>";
					resultXml += "failure";
					resultXml += "</stus>";
					resultXml += "</ims>";
					return resultXml;
				}
				for (Object[] alarm : Objects) {
					//String AlertId=IMonitorUtil.convertToString(alarm[6]);
					String AlertName=IMonitorUtil.convertToString(alarm[2]);
					//String AlertCommand=IMonitorUtil.convertToString(alarm[5]);
					//String imvgalertid=IMonitorUtil.convertToString(alarm[3]);
					String imvgAlertTime=IMonitorUtil.convertToString(alarm[0]);
					
					//String Deviceid=IMonitorUtil.convertToString(alarm[4]);
					String DeviceName=IMonitorUtil.convertToString(alarm[1]);
					
					//String Uploadcount=IMonitorUtil.convertToString(alarm[5]);
					String Difference=IMonitorUtil.convertToString(alarm[3]);
					
					/*<time><s:date name="#object[0]" format="dd/MM/yy 'at' hh:mm a"/></time>
					<devicename><s:property value="#object[1]"/></devicename>
					<alert><s:property value="#object[2]" /></alert>
					<imvgalertid><s:property value="#object[3]"/></imvgalertid>
					<deviceid><s:property value="#object[4]"/></deviceid>
					<rulecount><s:property value="#object[5]"/></rulecount>
					<alertcommand><s:if test="#object[6] == 'STREAM_FTP_SUCCESS' ">1</s:if><s:if test="#object[6] == 'IMAGE_FTP_SUCCESS' ">2</s:if></alertcommand>
					<colour><s:property value="#object[8]"/></colour>
					<alertid><s:property value="#object[7]"/></alertid>*/
					
				/*	<Alarm>
					<alertId>10</alertId>
					<alertName>Door Close</alertName>
					<alertCommand>DOOR_CLOSE</alertCommand>
					<imvgAlertId>76611</imvgAlertId>
					<imvgAlertTime>2012-08-07 11:01:58.0</imvgAlertTime>
					<deviceId>1093</deviceId>
					<deviceName>27</deviceName>
					<uploadCount>0</uploadCount>
					<timeDiff>10</timeDiff>
					</Alarm>*/
					
					
					resultXml +="<Alm>";   //Alarm
					
				/*	resultXml +="<aId>";   //alertId
					resultXml +=AlertId;
					resultXml +="</aId>";*/
					
					resultXml +="<aNm>";   //alertName
					resultXml +=AlertName;
					resultXml +="</aNm>";
					
					/*resultXml +="<aCd>";   //alertCommand
					resultXml +=AlertCommand;
					resultXml +="</aCd>";*/
					
					/*resultXml +="<iAId>";   //imvgAlertId
					resultXml +=imvgalertid;
					resultXml +="</iAId>";*/
	
					resultXml +="<iAT>";    //imvgAlertTime
					resultXml +=imvgAlertTime;
					resultXml +="</iAT>";
					
					/*resultXml +="<dId>";    //deviceId
					resultXml +=Deviceid;
					resultXml +="</dId>";*/
					
					resultXml +="<dNm>";    //deviceName
					resultXml +=DeviceName;
					resultXml +="</dNm>";
		
					/*resultXml +="<upCount>";
					resultXml +=Uploadcount;
					resultXml +="</uploadCount>";*/
					
					resultXml +="<tDf>";    //timeDiff
					resultXml +=Difference;
					resultXml +="</tDf>";
					
					resultXml +="</Alm>";
				}
				resultXml += "</ims>";
				
			}
			
			} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		}
		return resultXml;
	}
	
	
//-----------------------------------------------------------------------
	
	public String controlFanModes(String xml){
		
		
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
			NodeList commandNodes = document.getElementsByTagName("commandname");
			Element commandNode = (Element) commandNodes.item(0);
			String command = XmlUtil.getCharacterDataFromElement(commandNode);
			
			NodeList fanModeValueNodes = document.getElementsByTagName("fanModeValue");
			Element fanModeValueNode = (Element) fanModeValueNodes.item(0);
			String fanModeValue = XmlUtil.getCharacterDataFromElement(fanModeValueNode);
			boolean exResult=false;
			ImonitorControllerAction controllerAction= null;
			if(command.equals("AC_MODE_CONTROL"))
			{
				DeviceManager deviceManager = new DeviceManager();
				Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
				device.setCommandParam(fanModeValue);
				ActionModel actionModel = new ActionModel(device, null);
				controllerAction= new AcControlAction();
				controllerAction.executeCommand(actionModel);
				exResult=IMonitorUtil.waitForResult(controllerAction);
			}
			else if(command.equals("AC_FAN_MODE"))
			{
			DeviceManager deviceManager = new DeviceManager();
			Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
			acConfiguration acConfiguration=null;
			DeviceConfiguration deviceConfiguration = device.getDeviceConfiguration();
			if(deviceConfiguration == null){
				acConfiguration = new acConfiguration();
			}else{
				acConfiguration = (acConfiguration) device.getDeviceConfiguration();
			}
			acConfiguration.setFanModeValue(fanModeValue);
			device.setDeviceConfiguration(acConfiguration);
			ActionModel actionModel = new ActionModel(device, null);
			controllerAction = new AcFanModeControlAction();
			controllerAction.executeCommand(actionModel);
			exResult=IMonitorUtil.waitForResult(controllerAction);
			}
			else if(command.equals("AC_SWING_CONTROL"))
			{
				DeviceManager deviceManager = new DeviceManager();
				Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
				acConfiguration acConfiguration=null;
				DeviceConfiguration deviceConfiguration = device.getDeviceConfiguration();
				if(deviceConfiguration == null){
					acConfiguration = new acConfiguration();
				}else{
					acConfiguration = (acConfiguration) device.getDeviceConfiguration();
				}
				acConfiguration.setAcSwing(fanModeValue);
				device.setDeviceConfiguration(acConfiguration);
				ActionModel actionModel = new ActionModel(device, null);
				controllerAction = new AcSwingControlAction();
				controllerAction.executeCommand(actionModel);
				exResult=IMonitorUtil.waitForResult(controllerAction);
			}
			
			Device deviceFromDb=controllerAction.getActionModel().getDevice();

			acConfiguration AcConfig=(acConfiguration) deviceFromDb.getDeviceConfiguration();
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += exResult ? "success" : "failure";
			resultXml += "</status>";
			
			if(fanModeValue.equals("5"))
			{
				resultXml  += "<Mode>";
				resultXml  +=AcConfig.getFanMode();
				resultXml  += "</Mode>";
				resultXml  += "<FanMode>";
				resultXml  +=AcConfig.getFanModeValue();
				resultXml  += "</FanMode>";
				resultXml  += "<ACSwing>";
				resultXml  +=AcConfig.getAcSwing();
				resultXml  += "</ACSwing>";
			}
			resultXml  += "<levelstatus>";
			resultXml  += deviceFromDb.getCommandParam();
			resultXml  += "</levelstatus>";
			
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		}
		return resultXml;
	}
	
	
	public String ControlGateway(String xml){
		
		
		
		
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			
			
			NodeList gatewayNodes = document.getElementsByTagName("macid");
			Element gatewaydNode = (Element) gatewayNodes.item(0);
			String gatewayId = XmlUtil.getCharacterDataFromElement(gatewaydNode);
			
			
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String uResult = userManager.isUserExists(customerId, userId, hashPassword);
		
			if(uResult.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			
			
			
			
			NodeList levelNodes = document.getElementsByTagName("status");
			Element levelNode = (Element) levelNodes.item(0);
			String level = null;
			if(levelNode != null){
				level = XmlUtil.getCharacterDataFromElement(levelNode);
			}
			
			
			
			
			
			
			GatewayManager gatewayManager = new GatewayManager();
			GateWay gateWay = gatewayManager.getGateWayByMacId(gatewayId);
			Device device = new Device();
			
			
			AgentManager agentManager = new AgentManager();
			Agent agent = agentManager.getReceiverIpAndPort(gatewayId);
		
			
			
			gateWay.setAllOnOffStatus(Integer.parseInt(level));
			
			gateWay.setAgent(agent);
			device.setGateWay(gateWay);
			
			
		
			
			ActionModel actionModel = new ActionModel(device, null);
			
		
			
			
			ImonitorControllerAction controllerAction = new AllonoffAction();
			
			controllerAction.executeCommand(actionModel);
			boolean exResult = waitForResult(controllerAction);
		

			resultXml  = "<imonitor>";
			resultXml  += "<status>";
			resultXml += exResult ? "success" : "failure";
			resultXml  += "</status>";
			resultXml += "</imonitor>";
			
			
			
			
	
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	
	return resultXml;
	}
	
	
	public String getRSSIValuesBasedOnDeviceId(String xml)
	{
		
		String resultXml = "";
		try{
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			NodeList gatewayNodes = document.getElementsByTagName("macid");
			Element gatewaydNode = (Element) gatewayNodes.item(0);
			String macId = XmlUtil.getCharacterDataFromElement(gatewaydNode);
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
			XStream stream=new XStream();
			GatewayManager gatewayManager=new GatewayManager();
			GateWay gateWay=gatewayManager.getGateWayByMacId(macId);
			
			DeviceManager deviceManager=new DeviceManager();
			BigInteger bid=deviceManager.getDeviceBasedOnDeviceId(deviceId, gateWay.getId());
			long id=bid.longValue();
			
			Device device=deviceManager.getDeviceById(id);
		
			Device device2=new Device();
			device2.setDeviceId(deviceId);
			device2.setGateWay(gateWay);
			
			
			ActionModel actionModel = new ActionModel(device2, null);
			
			ImonitorControllerAction actionCreate = new NetworkStatusAction();
			actionCreate.executeCommand(actionModel);
			
			boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
			
			if (resultFromImvg)
			{
			
				if (actionCreate.isActionSuccess()) // Save to DB success
				{
					
					
			//Device device=deviceManager.getDeviceByGeneratedDeviceId(generateddeviceId);
			
			
			String NetworkXml=deviceManager.getNetworkStatsBasedOnDeviceId(id);
			NetworkStats networkStats=(NetworkStats) stream.fromXML(NetworkXml);
		    if (networkStats!=null) 
		    {
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
						
						String did=singleNodeSplit[0];
						String gateWayid= gatewayManager.getIdOfGateway(networkStats.getImvgId());
						long gid=Long.parseLong(gateWayid);
						String deviceName=deviceManager.getFriendlyNameBasedOnDeviceIdAndGateway(did, gid);
						rssiInfo.setDeviceName(deviceName);
						
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
					
					String did=singleNodeSplit[0];
					String gateWayid= gatewayManager.getIdOfGateway(networkStats.getImvgId());
					long gid=Long.parseLong(gateWayid);
					String deviceName=deviceManager.getFriendlyNameBasedOnDeviceIdAndGateway(did, gid);
					rssiInfo.setDeviceName(deviceName);
					
					IconManager iconManager=new IconManager();
					Icon icon=iconManager.getIconById(networkStats.getDevice().getIcon().getId());
					rssiInfo.setIcon(icon.getFileName());
					rssiInfo.setRssiValue(singleNodeSplit[2]);
					//LogUtil.info("Rssi object : "+xStream.toXML(rssiInfo));
					rssiInfos.add(rssiInfo);
				}
				networkStats.setRssiInfosList(rssiInfos);
				//LogUtil.info("FInal Net Stats Obj Imvg success"+stream.toXML(networkStats));
				//Set<RssiInfo> rssiList= networkStats.getRssiInfosList();
				
				resultXml ="<imonitor>" +
						"<status>Success</status>";
				
				//Iterator<RssiInfo> iterator=rssiInfos.iterator();
				/*if (iterator.hasNext())
				{
					RssiInfo info= iterator.next();
					resultXml+="<RssiInfo><deviceName>"+info.getDeviceName()+"</deviceName>" +
							"<icon>"+info.getIcon()+"</icon>" +
									"<rssiValue>"+info.getRssiValue()+"</rssiValue>" +
											"<color>"+info.getColor()+"</color></RssiInfo>";
				}*/
				for (RssiInfo rssiInfo : rssiInfos)
				{
					//RssiInfo info= iterator.next();
					resultXml+="<RssiInfo><deviceName>"+rssiInfo.getDeviceName()+"</deviceName>" +
							"<icon>"+rssiInfo.getIcon()+"</icon>" +
									"<rssiValue>"+rssiInfo.getRssiValue()+"</rssiValue>" +
											"<color>"+rssiInfo.getColor()+"</color></RssiInfo>";
				}
				
						resultXml+=	"</imonitor>";
										
			
				
			}
		    
				}
		    else 
		    {
		    	
		    	resultXml = "<imonitor><status>Cant Access Device</status></imonitor>";
			}
			
			}
		    else
			{
		    
				resultXml = "<imonitor><status>failure</status></imonitor>";
			}
			
		}
		catch (ParserConfigurationException e) {
			resultXml  = "1.Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "2.Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "3.Couldn't parse the input";
		}
		catch (Exception e) {
			LogUtil.info("", e);
			resultXml = "<imonitor><status>failure</status></imonitor>";
		}
		
		return resultXml;
	}
	
	
	
	//Hard Coded Rssi
	/*public String getRSSIValuesBasedOnDeviceId(String xml)
	{
		LogUtil.info("AND Rssi start");
		String resultXml = "";
		try{
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			NodeList gatewayNodes = document.getElementsByTagName("macid");
			Element gatewaydNode = (Element) gatewayNodes.item(0);
			String macId = XmlUtil.getCharacterDataFromElement(gatewaydNode);
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			LogUtil.info("Customer "+customerId);
			LogUtil.info("Device Id  :"+deviceId);
			LogUtil.info("Mac Id :"+macId);
			XStream stream=new XStream();
			GatewayManager gatewayManager=new GatewayManager();
			GateWay gateWay=gatewayManager.getGateWayByMacId(macId);
			
			DeviceManager deviceManager=new DeviceManager();
			BigInteger bid=deviceManager.getDeviceBasedOnDeviceId(deviceId, gateWay.getId());
			long id=bid.longValue();
			LogUtil.info("Device id "+id);
			if (deviceId.equals("261"))
			{
				LogUtil.info("Device 261");
			resultXml ="<imonitor>" +
					"<status>Success</status>";
			resultXml+="<RssiInfo><deviceName>"+"261"+"</deviceName>" +
					"<icon>"+""+"</icon>" +
							"<rssiValue>"+"170"+"</rssiValue>" +
									"<color>"+"2"+"</color></RssiInfo>";
			resultXml+=	"</imonitor>";
			}
			else if (deviceId.equals("517")) 
			{
				LogUtil.info("Device 517");
				resultXml ="<imonitor>" +
						"<status>Success</status>";
				resultXml+="<RssiInfo><deviceName>"+"517"+"</deviceName>" +
						"<icon>"+""+"</icon>" +
								"<rssiValue>"+"160"+"</rssiValue>" +
										"<color>"+"1"+"</color></RssiInfo>";
				resultXml+="<RssiInfo><deviceName>"+"123"+"</deviceName>" +
						"<icon>"+""+"</icon>" +
								"<rssiValue>"+"170"+"</rssiValue>" +
										"<color>"+"2"+"</color></RssiInfo>";
				resultXml+=	"</imonitor>";
			}
			else if (deviceId.equals("6")) 
			{
				LogUtil.info("Device 6");
				resultXml ="<imonitor>" +
						"<status>Success</status>";
				resultXml+="<RssiInfo><deviceName>"+"6"+"</deviceName>" +
						"<icon>"+""+"</icon>" +
								"<rssiValue>"+"180"+"</rssiValue>" +
										"<color>"+"2"+"</color></RssiInfo>";
				resultXml+="<RssiInfo><deviceName>"+"452"+"</deviceName>" +
						"<icon>"+""+"</icon>" +
								"<rssiValue>"+"175"+"</rssiValue>" +
										"<color>"+"2"+"</color></RssiInfo>";
				resultXml+="<RssiInfo><deviceName>"+"356"+"</deviceName>" +
						"<icon>"+""+"</icon>" +
								"<rssiValue>"+"160"+"</rssiValue>" +
										"<color>"+"1"+"</color></RssiInfo>";
				resultXml+=	"</imonitor>";
			}
			else
			{
				LogUtil.info("Device 263");
				resultXml ="<imonitor>" +
						"<status>Success</status>";
				resultXml+="<RssiInfo><deviceName>"+"263"+"</deviceName>" +
						"<icon>"+""+"</icon>" +
								"<rssiValue>"+"185"+"</rssiValue>" +
										"<color>"+"2"+"</color></RssiInfo>";
				resultXml+="<RssiInfo><deviceName>"+"212"+"</deviceName>" +
						"<icon>"+""+"</icon>" +
								"<rssiValue>"+"170"+"</rssiValue>" +
										"<color>"+"2"+"</color></RssiInfo>";
				resultXml+="<RssiInfo><deviceName>"+"326"+"</deviceName>" +
						"<icon>"+""+"</icon>" +
								"<rssiValue>"+"165"+"</rssiValue>" +
										"<color>"+"1"+"</color></RssiInfo>";
				resultXml+="<RssiInfo><deviceName>"+"274"+"</deviceName>" +
						"<icon>"+""+"</icon>" +
								"<rssiValue>"+"160"+"</rssiValue>" +
										"<color>"+"1"+"</color></RssiInfo>";
				resultXml+=	"</imonitor>";
			}
			
			
		}
		catch (ParserConfigurationException e) {
			resultXml  = "1.Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "2.Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "3.Couldn't parse the input";
		}
		catch (Exception e) {
			LogUtil.info("", e);
			resultXml = "<imonitor><status>failure</status></imonitor>";
		}
		LogUtil.info("Result xml :"+resultXml);
		return resultXml;
	}*/
	
	
	public String partialOpenStart(String xml){
		
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
			
			DeviceManager deviceManager = new DeviceManager();
			Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
			
			
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new PartialCurtainOpenStart();
			controllerAction.executeCommand(actionModel);
			boolean exResult = waitForResult(controllerAction);
			
			device = controllerAction.getActionModel().getDevice();
//			Refer alertnotifier and re-write the code.
//			IMonitorUtil.executeActions(command, queue , ip, port);
			resultXml  = "<imonitor>";
			resultXml  += "<status>";
			//resultXml  += "success";
			resultXml += exResult ? "success" : "failure";
			resultXml  += "</status>";
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} 
		return resultXml;
	}
	public String partialOpenStop(String xml){
		
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
			
			DeviceManager deviceManager = new DeviceManager();
			Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
			
			
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new PartialCurtainOpenStop();
			controllerAction.executeCommand(actionModel);
			boolean exResult = waitForResult(controllerAction);
			
			device = controllerAction.getActionModel().getDevice();
//			Refer alertnotifier and re-write the code.
//			IMonitorUtil.executeActions(command, queue , ip, port);
			resultXml  = "<imonitor>";
			resultXml  += "<status>";
			//resultXml  += "success";
			resultXml += exResult ? "success" : "failure";
			resultXml  += "</status>";
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} 
		return resultXml;
	}
	public String partialCloseStart(String xml){
		
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
			
			DeviceManager deviceManager = new DeviceManager();
			Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
			
			
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new PartialCurtainCloseStart();
			controllerAction.executeCommand(actionModel);
			boolean exResult = waitForResult(controllerAction);
			
			device = controllerAction.getActionModel().getDevice();
//			Refer alertnotifier and re-write the code.
//			IMonitorUtil.executeActions(command, queue , ip, port);
			resultXml  = "<imonitor>";
			resultXml  += "<status>";
			//resultXml  += "success";
			resultXml += exResult ? "success" : "failure";
			resultXml  += "</status>";
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} 
		return resultXml;
	}
	public String partialCloseStop(String xml){
		
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
			
			DeviceManager deviceManager = new DeviceManager();
			Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
			
			
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = new PartialCurtainCloseStop();
			controllerAction.executeCommand(actionModel);
			boolean exResult = waitForResult(controllerAction);
		
			device = controllerAction.getActionModel().getDevice();
//			Refer alertnotifier and re-write the code.
//			IMonitorUtil.executeActions(command, queue , ip, port);
			resultXml  = "<imonitor>";
			resultXml  += "<status>";
			//resultXml  += "success";
			resultXml += exResult ? "success" : "failure";
			resultXml  += "</status>";
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} 
		return resultXml;
	}
	
	public String fullOpenClose(String xml){
	
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
			
			
			
			DeviceManager deviceManager = new DeviceManager();
			Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
			
			
			NodeList commandNodes = document.getElementsByTagName("commandname");
			Element commandNode = (Element) commandNodes.item(0);
			String command = XmlUtil.getCharacterDataFromElement(commandNode);
			
			ActionModel actionModel = new ActionModel(device, null);
			ImonitorControllerAction controllerAction = null;
			if(command.equals("CURTAIN_OPEN")) {
				controllerAction = new CurtainopenAction();
			}else {
				controllerAction = new CurtainCloseAction();
			}
		
			controllerAction.executeCommand(actionModel);
			boolean exResult = waitForResult(controllerAction);
			
			device = controllerAction.getActionModel().getDevice();
//			Refer alertnotifier and re-write the code.
//			IMonitorUtil.executeActions(command, queue , ip, port);
			resultXml  = "<imonitor>";
			resultXml  += "<status>";
			//resultXml  += "success";
			resultXml += exResult ? "success" : "failure";
			resultXml  += "</status>";
			resultXml  += "<levelstatus>";
			resultXml  += device.getCommandParam();
			resultXml  += "</levelstatus>";
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} 
		return resultXml;
	}
	/* Naveen Added for controling IDU
	 * 
	 */
	
	public String controlIdu(String xml){
		
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String uResult = userManager.isUserExists(customerId, userId, hashPassword);
			if(uResult.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
			NodeList commandNodes = document.getElementsByTagName("commandname");
			Element commandNode = (Element) commandNodes.item(0);
			String command = XmlUtil.getCharacterDataFromElement(commandNode);
			
			
			NodeList fanModeValueNodes = document.getElementsByTagName("fanModeValue");
			Element fanModeValueNode = (Element) fanModeValueNodes.item(0);
			String fanModeValue = XmlUtil.getCharacterDataFromElement(fanModeValueNode);
			
			NodeList levelNodes = document.getElementsByTagName("levelstatus");
			Element levelNode = (Element) levelNodes.item(0);
			String level = null;
			if(levelNode != null){
				level = XmlUtil.getCharacterDataFromElement(levelNode);
			}
			
			DeviceManager deviceManager = new DeviceManager();
			Object[] result = deviceManager.checkDeviceAndReturnCommunicationDetails(deviceId, customerId, command);
			if(result == null){
			
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;			
			} else{
				
				String ip = IMonitorUtil.convertToString(result[3]);
				String macId = IMonitorUtil.convertToString(result[0]);
				int port = Integer.parseInt(IMonitorUtil.convertToString(result[4]));
				/*UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(command);
				Class<?> className = updatorModel.getClassName();
				LogUtil.info("Class Name--->"+className.getName());*/
				ImonitorControllerAction controllerAction = null;
				Device device = new Device();
				
				//Setting Device Configuration
				Device deviceFromDB = deviceManager.getDeviceWithConfiguration(deviceId);
				IndoorUnitConfiguration unitConfiguration;
				XStream stream=new XStream();
				unitConfiguration = (IndoorUnitConfiguration) new DaoManager().get(deviceFromDB.getDeviceConfiguration().getId(), IndoorUnitConfiguration.class);
				device.setDeviceConfiguration(unitConfiguration);
				
				device.setGeneratedDeviceId(deviceId);
				GateWay gateWay = new GateWay();
				gateWay.setMacId(macId);
				Agent agent = new Agent();
				agent.setControllerReceiverPort(port);
				agent.setIp(ip);
				gateWay.setAgent(agent);
				device.setGateWay(gateWay);
				
				if(command.equals(Constants.AC_ON))
				{
					
					device.setCommandParam("5");
					controllerAction = new IndoorUnitActionControl();
				}
				
				else if(command.equals(Constants.AC_OFF))
				{
					
					device.setCommandParam("0");
					controllerAction = new IndoorUnitActionControl();
				}
				else if(command.equals(Constants.AC_MODE))
				{
					
					device.setCommandParam(fanModeValue);
					controllerAction = new IndoorUnitActionControl();
				}
				else if(command.equals(Constants.AC_CONTROL))
				{
					
					int value = Integer.parseInt(fanModeValue);
					
					if ((value == 0) || (value > 32) || (value < 16)) 
					{
						
						fanModeValue= "16";
					}
					
					device.setCommandParam(fanModeValue);
					controllerAction = new IduTemperatureControlAction();
				}
			/*	else if(command.equals(Constants.AC_SWING_CONTROL))
				{
					device.setCommandParam(fanModeValue);
					controllerAction = new IDUFanDirectionControl();
				}*/
				
				
				if(level != null){
					device.setCommandParam(level);
				}
				
				IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) device.getDeviceConfiguration();
				
				ActionModel actionModel = new ActionModel(device, null);
				controllerAction.executeCommand(actionModel);
				boolean exResult = waitForResult(controllerAction);
				device = controllerAction.getActionModel().getDevice();
				
				
				resultXml  = "<imonitor>";
				resultXml  += "<status>";
				//resultXml  += "success";
				resultXml += exResult ? "success" : "failure";
				resultXml  += "</status>";
				
				resultXml  += "<Mode>";
				resultXml  +=configuration.getFanModeValue();
				resultXml  += "</Mode>";
				resultXml  += "<FanVolumeValue>";
				resultXml  +=configuration.getFanVolumeValue();
				resultXml  += "</FanVolumeValue>";
				resultXml  += "<FanDirectionValue>";
				resultXml  +=configuration.getFanDirectionValue();
				resultXml  += "</FanDirectionValue>";
				
				String tempValue=Integer.toString(configuration.getTemperatureValue());
				
				resultXml  += "<TemperatureValue>";
				resultXml  +=tempValue;
				resultXml  += "</TemperatureValue>";
				String filterSign=Integer.toString(configuration.getFilterSignStatus());
				resultXml  += "<FilterStatus>";
				resultXml  +=filterSign;
				resultXml  += "</FilterStatus>";
				
				resultXml  += "<levelstatus>";
				resultXml  += device.getCommandParam();
				resultXml  += "</levelstatus>";
				resultXml += "</imonitor>";
			}
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			resultXml = "Couldn't parse the input " + e.getMessage();
		} /*catch (InstantiationException e) {
			resultXml = "The command may not be implemented. Please check with CMS administrator " + e.getMessage();
		} catch (IllegalAccessException e) {
			resultXml = "The command may not be implemented. Please check with CMS administrator " + e.getMessage();
		}*/
		
		return resultXml;
	}
	
	public String controlIDUFanModes(String xml){
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
			NodeList commandNodes = document.getElementsByTagName("commandname");
			Element commandNode = (Element) commandNodes.item(0);
			String command = XmlUtil.getCharacterDataFromElement(commandNode);
			
			NodeList fanModeValueNodes = document.getElementsByTagName("fanModeValue");
			Element fanModeValueNode = (Element) fanModeValueNodes.item(0);
			String fanModeValue = XmlUtil.getCharacterDataFromElement(fanModeValueNode);
			
			
			
			
			
			XStream stream=new XStream();
			boolean exResult=false;
			ImonitorControllerAction controllerAction= null;
			IndoorUnitConfiguration unitConfiguration=null;
			DeviceManager deviceManager = new DeviceManager();
			Device device= deviceManager.retrieveDeviceDetails(deviceId, customerId);
			//LogUtil.info("Device "+stream.toXML(device));
			device.setCommandParam(fanModeValue);
			DeviceConfiguration deviceConfiguration = device.getDeviceConfiguration();
			
			unitConfiguration = (IndoorUnitConfiguration) new DaoManager().get(deviceConfiguration.getId(), IndoorUnitConfiguration.class);
			device.setDeviceConfiguration(unitConfiguration);
		
			ActionModel actionModel = new ActionModel(device, null);
			if(command.equals("IDU_FAN_VOLUME"))
			{
				
				controllerAction= new IDUFanVolumeControl();
				controllerAction.executeCommand(actionModel);
				exResult=IMonitorUtil.waitForResult(controllerAction);
			}
			else if(command.equals("IDU_FAN_DIRECTION"))
			{
				
				controllerAction= new IDUFanDirectionControl();
				controllerAction.executeCommand(actionModel);
				exResult=IMonitorUtil.waitForResult(controllerAction);
			}
			
			Device deviceFromDb=controllerAction.getActionModel().getDevice();

			IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) deviceFromDb.getDeviceConfiguration();
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += exResult ? "success" : "failure";
			resultXml += "</status>";
			
			//if(fanModeValue.equals("5"))
			//{
				resultXml  += "<Mode>";
				resultXml  +=configuration.getFanModeValue();
				resultXml  += "</Mode>";
				resultXml  += "<FanVolumeValue>";
				resultXml  +=configuration.getFanVolumeValue();
				resultXml  += "</FanVolumeValue>";
				resultXml  += "<FanDirectionValue>";
				resultXml  +=configuration.getFanDirectionValue();
				resultXml  += "</FanDirectionValue>";
			//}
			resultXml  += "<levelstatus>";
			resultXml  += deviceFromDb.getCommandParam();
			resultXml  += "</levelstatus>";
			
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		}
		return resultXml;
	}
	
	
	public String filterSignMid(String xml){
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
			Element deviceIdNode = (Element) deviceIdNodes.item(0);
			String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
			
			XStream stream=new XStream();
			
			boolean exResult=false;
			ImonitorControllerAction controllerAction= null;
			DeviceManager deviceManager = new DeviceManager();
			Device device= deviceManager.retrieveDeviceDetails(deviceId, customerId);
			//LogUtil.info("Device "+stream.toXML(device));
			//device.setCommandParam(fanModeValue);
			ActionModel actionModel = new ActionModel(device, null);
		
			controllerAction= new ResetFilterAction();
			controllerAction.executeCommand(actionModel);
			exResult=IMonitorUtil.waitForResult(controllerAction);
				
				
			Device deviceFromDb=controllerAction.getActionModel().getDevice();
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += exResult ? "success" : "failure";
			resultXml += "</status>";
			
			resultXml  += "<levelstatus>";
			resultXml  += deviceFromDb.getCommandParam();
			resultXml  += "</levelstatus>";
			
			resultXml += "</imonitor>";
		} catch (ParserConfigurationException e) {
			resultXml  = "Couldn't parse the input";
		} catch (SAXException e) {
			resultXml = "Couldn't parse the input";
		} catch (IOException e) {
			resultXml = "Couldn't parse the input";
		}
		return resultXml;
	}
		

//Verify Super User
public String verifySuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);
	
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	return resultXml;
}

public String singleCustomerDeviceDetailsForSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		DeviceManager deviceManager = new DeviceManager();
		List <Rule>rules = null;
		Device devWithRules = null;
		//Vibhu end
		CustomerManager customerManager = new CustomerManager();
		List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfCustomerByCustomerId(customerId);
		int count = 0;
		resultXml  = "<imonitor>";
		if(gateWays.size() < 1){
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
		}else{
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "<gateways>";
			for (GateWay gateWay : gateWays) {
				if(count == 0){
				resultXml += "<gateway>";
				resultXml += "<macid>";
				resultXml += gateWay.getMacId();
				resultXml += "</macid>";
				resultXml +="<version>";
				resultXml +=gateWay.getGateWayVersion();
				resultXml +="</version>";
				resultXml += "<localip>";
				resultXml += gateWay.getLocalIp();
				resultXml += "</localip>";
				resultXml += "<status>";
				resultXml += gateWay.getStatus().getName();
				resultXml += "</status>";
				resultXml += "<mode>";
				String mode=gateWay.getCurrentMode();
				if(mode.equals("0"))
				resultXml += "HOME";
				if(mode.equals("1"))
				resultXml += "STAY";
				if(mode.equals("2"))
				resultXml += "AWAY";
				resultXml += "</mode>";
				/*resultXml += "<AllonStatus>";
				resultXml += gateWay.getAllOnOffStatus();
				resultXml += "</AllonStatus>";*/
				resultXml += "<customer>";
				resultXml += customerId;
				resultXml += "</customer>";
				
				
				
				resultXml += "<devices>";
				}
				Set<Device> devices = gateWay.getDevices();
					for (Device device : devices) {	
						if(device.getFriendlyName().equals("Home") 
								|| device.getFriendlyName().equals("Stay") 
								|| device.getFriendlyName().equals("Away")
								|| device.getEnableList().equals("0"))
						{
							continue;
						}

						resultXml += "<device>";
						resultXml += "<id>";
						resultXml += device.getGeneratedDeviceId();
						resultXml += "</id>";
						resultXml += "<deviceId>";
						resultXml += device.getDeviceId();
						resultXml += "</deviceId>";
						resultXml += "<name>";
						resultXml += device.getFriendlyName();
						resultXml += "</name>";
						//sumit begin
						String modelNumber = device.getModelNumber();
						resultXml += "<modelnumber>";
						if(modelNumber != null){
							resultXml += modelNumber;
						}
						resultXml += "</modelnumber>";
						resultXml += "<icon>";
						resultXml += device.getIcon().getFileName();
						resultXml += "</icon>";
						//sumit end
						resultXml += "<type>";
						resultXml += device.getDeviceType().getDetails();
						resultXml += "</type>";
						resultXml += "<location>";
						resultXml += device.getLocation().getName();
						resultXml += "</location>";
						resultXml += "<licon>";
						resultXml += device.getLocation().getIconFile();
						resultXml += "</licon>";
						resultXml += "<status>";
						resultXml += device.getLastAlert().getAlertCommand();
						resultXml += "</status>";
						resultXml += "<levelstatus>";
						resultXml += device.getCommandParam();
						resultXml += "</levelstatus>";
						resultXml += "<batterylevel>";
						resultXml += device.getBatteryStatus();
						resultXml += "</batterylevel>";
						resultXml += "<armstatus>";
						//vibhu start
						devWithRules = deviceManager.getDeviceWithRulesByGeneratedDeviceId(device.getGeneratedDeviceId());
						rules = devWithRules.getRules();
						if(rules != null && rules.size() >0)
						{
							resultXml += device.getArmStatus().getName();
						}
						else
						{
							resultXml += "Running";
						}
						//vibhu end
						resultXml += "</armstatus>";
						resultXml +="<enablestatus>";
						resultXml += device.getEnableStatus();
						resultXml +="</enablestatus>";
						
						String top =device.getLocationMap().getTop(); ;
						resultXml += "<top>";
						if(top != null && !top.equals(""))
							resultXml +=top;
						else
							resultXml +="-1";
						resultXml += "</top>";
						String left =device.getLocationMap().getLeftMap(); 
						resultXml += "<left>";
						if(left != null && !left.equals(""))
							resultXml +=left;
						else
							resultXml +="-1";
						
						resultXml += "</left>";
						//sumit start
						resultXml += "<devicePosition>";
						resultXml += device.getPosIdx();
						resultXml += "</devicePosition>";
						//sumit end
						
						if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
						{
							acConfiguration accofig=(acConfiguration) device.getDeviceConfiguration();
							if(accofig != null)
							{
								resultXml +="<Config>";
								resultXml += "<FM>";   //FM- FanMode
								resultXml += accofig.getFanMode();
								resultXml += "</FM>";
								resultXml += "<MV>";   //ModeValue
								resultXml += accofig.getFanModeValue();
								resultXml += "</MV>";
								resultXml += "<Acswing>";   //ModeValue
								resultXml += accofig.getAcSwing();
								resultXml += "</Acswing>";
								resultXml +="</Config>";
							}
							
						}
						
						if (device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
						{
							//LogUtil.info("VIA UNit config block");
							//DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
							//IndoorUnitConfiguration indoorUnitConfiguration=(IndoorUnitConfiguration) Dvconf.getDeviceConfigurationById(dcId);
							IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) device.getDeviceConfiguration();
							resultXml +="<Config>";
							resultXml +="<fanModeCapability>";
							resultXml +=configuration.getFanModeCapability();
							resultXml +="</fanModeCapability>";
							resultXml +="<coolModeCapability>";
							resultXml +=configuration.getCoolModeCapability();
							resultXml +="</coolModeCapability>";
							resultXml +="<heatModeCapability>";
							resultXml +=configuration.getHeatModeCapability();
							resultXml +="</heatModeCapability>";
							resultXml +="<autoModeCapability>";
							resultXml +=configuration.getAutoModeCapability();
							resultXml +="</autoModeCapability>";
							resultXml +="<dryModeCapability>";
							resultXml +=configuration.getDryModeCapability();
							resultXml +="</dryModeCapability>";
							resultXml +="<fanDirectionLevelCapability>";
							resultXml +=configuration.getFanDirectionLevelCapability();
							resultXml +="</fanDirectionLevelCapability>";
							resultXml +="<fanDirectionCapability>";
							resultXml +=configuration.getFanDirectionCapability();
							resultXml +="</fanDirectionCapability>";
							resultXml +="<fanVolumeLevelCapability>";
							resultXml +=configuration.getFanVolumeLevelCapability();
							resultXml +="</fanVolumeLevelCapability>";
							resultXml +="<fanVolumeCapability>";
							resultXml +=configuration.getFanVolumeCapability();
							resultXml +="</fanVolumeCapability>";
							resultXml +="<coolingUpperlimit>";
							resultXml +=configuration.getCoolingUpperlimit();
							resultXml +="</coolingUpperlimit>";
							resultXml +="<coolingLowerlimit>";
							resultXml +=configuration.getCoolingLowerlimit();
							resultXml +="</coolingLowerlimit>";
							resultXml +="<heatingUpperlimit>";
							resultXml +=configuration.getHeatingUpperlimit();
							resultXml +="</heatingUpperlimit>";
							resultXml +="<heatingLowerlimit>";
							resultXml +=configuration.getHeatingLowerlimit();
							resultXml +="</heatingLowerlimit>";
							resultXml +="<ConnectStatus>";
							resultXml +=configuration.getConnectStatus();
							resultXml +="</ConnectStatus>";
							resultXml +="<CommStatus>";
							resultXml +=configuration.getCommStatus();
							resultXml +="</CommStatus>";
							resultXml +="<fanModeValue>";
							resultXml +=configuration.getFanModeValue();
							resultXml +="</fanModeValue>";
							resultXml +="<sensedTemperature>";
							resultXml +=configuration.getSensedTemperature();
							resultXml +="</sensedTemperature>";
							resultXml +="<fanVolumeValue>";
							resultXml +=configuration.getFanVolumeValue();
							resultXml +="</fanVolumeValue>";
							resultXml +="<fanDirectionValue>";
							resultXml +=configuration.getFanDirectionValue();
							resultXml +="</fanDirectionValue>";
						//	resultXml +="<eMessage>";
						//	resultXml +=configuration.getErrorMessage();
						//	resultXml +="</eMessage>";
							resultXml +="<filterSignStatus>";
							resultXml +=configuration.getFilterSignStatus();
							resultXml +="</filterSignStatus>";
							resultXml +="<temperatureValue>";
							resultXml +=configuration.getTemperatureValue();
							resultXml +="</temperatureValue>";
							resultXml +="</Config>";
						}
						resultXml += "</device>";
					}
		
				count++;
			
				
				
				if((gateWays.size() > 1) && (count == 3)){
					resultXml += "</devices>";
					resultXml += "</gateway>";
				}else if(gateWays.size() == 1){
				resultXml += "</devices>";
				resultXml += "</gateway>";
				}
			}
			resultXml += "</gateways>";
		}
		resultXml += "</imonitor>";
		
		
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	} catch (Exception e) {
		LogUtil.info("Caught Exception : ", e);
	}
	return resultXml;
}

public String multipleCustomerDeviceDetailsForSuperUser(String xml){
	String resultXml = "";
	
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superuserNodes = document.getElementsByTagName("superUserId");
		Element superuserNode = (Element) superuserNodes.item(0);
		String superuser = XmlUtil.getCharacterDataFromElement(superuserNode);
		DeviceManager deviceManager = new DeviceManager();
		List <Rule>rules = null;
		Device devWithRules = null;
		CustomerManager customerManager = new CustomerManager();
		//String superuser="imszing";
		SuperUserManager superUserManager=new SuperUserManager();
		AdminSuperUser adminSuperUser=superUserManager.getsuperUserBySuperUserId(superuser);
		//LogUtil.info("SuperUSer id : "+ adminSuperUser.getId());
		XStream stream=new XStream();
		List<Customer> customerIdList= superUserManager.listOfCustomersForSuperUser(adminSuperUser.getId());
		//LogUtil.info("Customers list : "+ stream.toXML(customerIdList));
		resultXml  += "<imonitor>";
		resultXml += "<gateways>";
		for (Customer cust : customerIdList)
		{
			List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfCustomerByCustomerId(cust.getCustomerId());
			int count = 0;
			
			if(gateWays.size() < 1){
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
			}else{
				for (GateWay gateWay : gateWays) {
					if(count == 0){
					resultXml += "<gateway>";
					resultXml += "<macid>";
					resultXml += gateWay.getMacId();
					resultXml += "</macid>";
					resultXml +="<version>";
					resultXml +=gateWay.getGateWayVersion();
					resultXml +="</version>";
					resultXml += "<localip>";
					resultXml += gateWay.getLocalIp();
					resultXml += "</localip>";
					resultXml += "<status>";
					resultXml += gateWay.getStatus().getName();
					resultXml += "</status>";
					
					resultXml += "<customer>";
					resultXml += "<name>";
					resultXml += cust.getCustomerId();
					resultXml += "</name>";
					resultXml += "<mode>";
					String mode=gateWay.getCurrentMode();
					if(mode.equals("0"))
					resultXml += "HOME";
					if(mode.equals("1"))
					resultXml += "STAY";
					if(mode.equals("2"))
					resultXml += "AWAY";
					resultXml += "</mode>";
					resultXml += "</customer>";
					
					resultXml += "<devices>";
					}
					Set<Device> devices = gateWay.getDevices();
						for (Device device : devices) {	
							if(device.getFriendlyName().equals("Home") 
									|| device.getFriendlyName().equals("Stay") 
									|| device.getFriendlyName().equals("Away")
									|| device.getEnableList().equals("0"))
							{
								continue;
							}

							resultXml += "<device>";
							resultXml += "<id>";
							resultXml += device.getGeneratedDeviceId();
							resultXml += "</id>";
							resultXml += "<deviceId>";
							resultXml += device.getDeviceId();
							resultXml += "</deviceId>";
							resultXml += "<name>";
							resultXml += device.getFriendlyName();
							resultXml += "</name>";
							String modelNumber = device.getModelNumber();
							resultXml += "<modelnumber>";
							if(modelNumber != null){
								resultXml += modelNumber;
							}
							resultXml += "</modelnumber>";
							resultXml += "<icon>";
							resultXml += device.getIcon().getFileName();
							resultXml += "</icon>";
							resultXml += "<type>";
							resultXml += device.getDeviceType().getDetails();
							resultXml += "</type>";
							resultXml += "<location>";
							resultXml += device.getLocation().getName();
							resultXml += "</location>";
							resultXml += "<licon>";
							resultXml += device.getLocation().getIconFile();
							resultXml += "</licon>";
							resultXml += "<status>";
							resultXml += device.getLastAlert().getAlertCommand();
							resultXml += "</status>";
							resultXml += "<levelstatus>";
							resultXml += device.getCommandParam();
							resultXml += "</levelstatus>";
							resultXml += "<batterylevel>";
							resultXml += device.getBatteryStatus();
							resultXml += "</batterylevel>";
							resultXml += "<armstatus>";
							devWithRules = deviceManager.getDeviceWithRulesByGeneratedDeviceId(device.getGeneratedDeviceId());
							rules = devWithRules.getRules();
							if(rules != null && rules.size() >0)
							{
								resultXml += device.getArmStatus().getName();
							}
							else
							{
								resultXml += "Running";
							}
							resultXml += "</armstatus>";
							resultXml +="<enablestatus>";
							resultXml += device.getEnableStatus();
							resultXml +="</enablestatus>";
							
							String top =device.getLocationMap().getTop(); ;
							resultXml += "<top>";
							if(top != null && !top.equals(""))
								resultXml +=top;
							else
								resultXml +="-1";
							resultXml += "</top>";
							String left =device.getLocationMap().getLeftMap(); 
							resultXml += "<left>";
							if(left != null && !left.equals(""))
								resultXml +=left;
							else
								resultXml +="-1";
							
							resultXml += "</left>";
							resultXml += "<devicePosition>";
							resultXml += device.getPosIdx();
							resultXml += "</devicePosition>";
							
							if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
							{
								acConfiguration accofig=(acConfiguration) device.getDeviceConfiguration();
								if(accofig != null)
								{
									resultXml +="<Config>";
									resultXml += "<FM>";   //FM- FanMode
									resultXml += accofig.getFanMode();
									resultXml += "</FM>";
									resultXml += "<MV>";   //ModeValue
									resultXml += accofig.getFanModeValue();
									resultXml += "</MV>";
									resultXml += "<Acswing>";   //ModeValue
									resultXml += accofig.getAcSwing();
									resultXml += "</Acswing>";
									resultXml +="</Config>";
								}
								
							}
							
							if (device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
							{
								IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) device.getDeviceConfiguration();
								resultXml +="<Config>";
								resultXml +="<fanModeCapability>";
								resultXml +=configuration.getFanModeCapability();
								resultXml +="</fanModeCapability>";
								resultXml +="<coolModeCapability>";
								resultXml +=configuration.getCoolModeCapability();
								resultXml +="</coolModeCapability>";
								resultXml +="<heatModeCapability>";
								resultXml +=configuration.getHeatModeCapability();
								resultXml +="</heatModeCapability>";
								resultXml +="<autoModeCapability>";
								resultXml +=configuration.getAutoModeCapability();
								resultXml +="</autoModeCapability>";
								resultXml +="<dryModeCapability>";
								resultXml +=configuration.getDryModeCapability();
								resultXml +="</dryModeCapability>";
								resultXml +="<fanDirectionLevelCapability>";
								resultXml +=configuration.getFanDirectionLevelCapability();
								resultXml +="</fanDirectionLevelCapability>";
								resultXml +="<fanDirectionCapability>";
								resultXml +=configuration.getFanDirectionCapability();
								resultXml +="</fanDirectionCapability>";
								resultXml +="<fanVolumeLevelCapability>";
								resultXml +=configuration.getFanVolumeLevelCapability();
								resultXml +="</fanVolumeLevelCapability>";
								resultXml +="<fanVolumeCapability>";
								resultXml +=configuration.getFanVolumeCapability();
								resultXml +="</fanVolumeCapability>";
								resultXml +="<coolingUpperlimit>";
								resultXml +=configuration.getCoolingUpperlimit();
								resultXml +="</coolingUpperlimit>";
								resultXml +="<coolingLowerlimit>";
								resultXml +=configuration.getCoolingLowerlimit();
								resultXml +="</coolingLowerlimit>";
								resultXml +="<heatingUpperlimit>";
								resultXml +=configuration.getHeatingUpperlimit();
								resultXml +="</heatingUpperlimit>";
								resultXml +="<heatingLowerlimit>";
								resultXml +=configuration.getHeatingLowerlimit();
								resultXml +="</heatingLowerlimit>";
								resultXml +="<ConnectStatus>";
								resultXml +=configuration.getConnectStatus();
								resultXml +="</ConnectStatus>";
								resultXml +="<CommStatus>";
								resultXml +=configuration.getCommStatus();
								resultXml +="</CommStatus>";
								resultXml +="<fanModeValue>";
								resultXml +=configuration.getFanModeValue();
								resultXml +="</fanModeValue>";
								resultXml +="<sensedTemperature>";
								resultXml +=configuration.getSensedTemperature();
								resultXml +="</sensedTemperature>";
								resultXml +="<fanVolumeValue>";
								resultXml +=configuration.getFanVolumeValue();
								resultXml +="</fanVolumeValue>";
								resultXml +="<fanDirectionValue>";
								resultXml +=configuration.getFanDirectionValue();
								resultXml +="</fanDirectionValue>";
							//	resultXml +="<eMessage>";
							//	resultXml +=configuration.getErrorMessage();
							//	resultXml +="</eMessage>";
								resultXml +="<filterSignStatus>";
								resultXml +=configuration.getFilterSignStatus();
								resultXml +="</filterSignStatus>";
								resultXml +="<temperatureValue>";
								resultXml +=configuration.getTemperatureValue();
								resultXml +="</temperatureValue>";
								resultXml +="</Config>";
							}
							resultXml += "</device>";
						}
			
					count++;
					if((gateWays.size() > 1) && (count == 3)){
						resultXml += "</devices>";
						resultXml += "</gateway>";
					}else if(gateWays.size() == 1){
					resultXml += "</devices>";
					resultXml += "</gateway>";
					}
				}
			}
		}
		resultXml += "</gateways>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml += "</imonitor>";
		
		
	}
	 catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	 catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("Error : "+ e.getMessage());
		}
	//LogUtil.info("response : "+resultXml );
	return resultXml;
}

public String customerListForSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
			NodeList superUserNodes = document.getElementsByTagName("superUserId");
			Element superUserNode = (Element) superUserNodes.item(0);
			String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
			
			NodeList deviceTokenNodes = document.getElementsByTagName("devicetoken");
			Element deviceTokenNode = (Element) deviceTokenNodes.item(0);
			String deviceToken = XmlUtil.getCharacterDataFromElement(deviceTokenNode);
			
			NodeList ImeiNumberNodes = document.getElementsByTagName("ImeiNumber");
			Element ImeiNumberNode = (Element) ImeiNumberNodes.item(0);
			String ImeiNumber = XmlUtil.getCharacterDataFromElement(ImeiNumberNode);
			
			NodeList deviceTypeNodes = document.getElementsByTagName("devicetype");
			Element deviceTypeNode = (Element) deviceTypeNodes.item(0);
			String deviceType = XmlUtil.getCharacterDataFromElement(deviceTypeNode);
			
			SuperUserManager superUserManager=new SuperUserManager();
			AdminSuperUser adminSuperUser=superUserManager.getsuperUserBySuperUserId(superUserId);
			List<Customer> customerIdList= superUserManager.listOfCustomersForSuperUser(adminSuperUser.getId());
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			
			
			for (Customer customer : customerIdList)
			{
				//If superUser login success(uresult) add customers and device token to PushNotification table
				DeviceManager deviceManager=new DeviceManager();
				DaoManager daoManager=new DaoManager();
				
				if (deviceType.equalsIgnoreCase("Android"))
				{
					boolean res = deviceManager.verifyWhetherDevicePresentInDb(customer.getId(), ImeiNumber);
					if (res == false) 
					{
						PushNotification pushNotification=new PushNotification();
						pushNotification.setCustomer(customer);
						pushNotification.setDeviceToken(deviceToken);
						pushNotification.setDeviceType(deviceType);
						pushNotification.setImeiNumber(ImeiNumber);
						res = daoManager.save(pushNotification);
						
					}
					else
					{
						PushNotification pushFromDb = deviceManager.getPushNotificationObjectByCustomer(customer.getId());
						pushFromDb.setDeviceToken(deviceToken);
						//pushFromDb.setCustomer(customer);
						res=daoManager.update(pushFromDb);
						
					}
				}
				/*else 
				{
					
					boolean res = deviceManager.verifyWhetherDevicePresentInDbForIOS(customer.getId() , deviceToken);
					if (res == false) 
					{
						PushNotification pushNotification=new PushNotification();
						pushNotification.setCustomer(customer);
						pushNotification.setDeviceToken(deviceToken);
						pushNotification.setDeviceType(deviceType);
						pushNotification.setImeiNumber(ImeiNumber);
						res = daoManager.save(pushNotification);
						LogUtil.info("Save result : " + res);
					}
					else
					{
						PushNotification pushFromDb = deviceManager.getPushNotificationObjectByCustomerAndDeviceType(customer.getId(),deviceType);
						pushFromDb.setDeviceToken(deviceToken);
						//pushFromDb.setCustomer(customer);
						res=daoManager.update(pushFromDb);
						LogUtil.info("Update result : " + res);
					}
				}*/
				
				
				
				resultXml += "<customer>";
				resultXml += "<name>";
				resultXml += customer.getCustomerId();
				resultXml += "</name>";
				resultXml += "</customer>";
			}
			resultXml += "</imonitor>";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			LogUtil.info("Errorrr : "+ e.getMessage());
		}
	return resultXml;
}

//SuperUser Arm and disarm
/*public String armDevice(String xml){
	Status status = IMonitorUtil.getStatuses().get(Constants.ARM);
	return changeArmStatus(xml, status);
}

public String disarmDevice(String xml){
	Status status = IMonitorUtil.getStatuses().get(Constants.DISARM);
	return changeArmStatus(xml, status);
}*/

public String armDevicesSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		Status status = IMonitorUtil.getStatuses().get(Constants.ARM);
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		/*CustomerManager customerManager=new CustomerManager();
		GatewayManager gatewayManager=new GatewayManager();
		DeviceManager deviceManager = new DeviceManager();*/
		
		XStream stream=new XStream();
		//LogUtil.info("List of customers : "+stream.toXML(list) );
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else
		{
			//Customer customerfromDb = customerManager.getCustomerBycustomerId(customerId);
			//GateWay gateway = gatewayManager.getGateWayByCustomer(customerfromDb);
			
			boolean exResult = false;
			String message = null;
			if(deviceId != null){
				String retVal = "";
				if(deviceId.equalsIgnoreCase("*")){
					//exResult  = deviceManager.updateDevicesArmStatusByCustomerId(customerId, status);
					CommandIssueServiceManager cism = new CommandIssueServiceManager();
					if(status.getName().equals(Constants.ARM))//AWAY
					{
						retVal = cism.activateModeInternal(customerId,Constants.ARM);
						retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
						if(retVal.startsWith("SUCCESS")){
							exResult = true;
						}else{
							//sumit: Handle ARM_DEVICES_PROGRESS_FAIL
							message = retVal;
						}
					}
					else//HOME
					{
						retVal = cism.activateModeInternal(customerId,Constants.DISARM);
						retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
						if(retVal.startsWith("SUCCESS")){
							exResult = true;
						}
					}
				}else if(deviceId.equalsIgnoreCase("*STAY")){ //STAY
					CommandIssueServiceManager cism = new CommandIssueServiceManager();
					retVal = cism.activateModeInternal(customerId,Constants.STAY);
					retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
					if(retVal.startsWith("SUCCESS")){
						exResult = true;
					}else{
						//sumit: Handle STAY_DEVICES_PROGRESS_FAIL
						message = retVal;
					}
				}else{//obsolete code
						//vibhu start
						CommandIssueServiceManager cism = new CommandIssueServiceManager();
						exResult = cism.deviceArmDisarmStatusUpdateFromMID(customerId, deviceId, status);
						//exResult = deviceManager.updateDeviceArmStatus(customerId, deviceId, status);
						//vibhu end
				}
			}
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += exResult ? "success" : "failure";
			resultXml += "</status>";
			if(message != null){
				resultXml += "<message>";
				resultXml += message;
				resultXml += "</message>";
				
			}
			
		}
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	catch (Exception e) {
	
		LogUtil.info("Error "+  e.getMessage());
		resultXml = "<imonitor><status>failure</status></imonitor>";
	}
	return resultXml;
}

public String disArmDevicesSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		Status status = IMonitorUtil.getStatuses().get(Constants.DISARM);
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		/*CustomerManager customerManager=new CustomerManager();
		GatewayManager gatewayManager=new GatewayManager();
		DeviceManager deviceManager = new DeviceManager();*/
		
		XStream stream=new XStream();
		//LogUtil.info("List of customers : "+stream.toXML(list) );
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else
		{
			//Customer customerfromDb = customerManager.getCustomerBycustomerId(customerId);
			//GateWay gateway = gatewayManager.getGateWayByCustomer(customerfromDb);
			
			boolean exResult = false;
			String message = null;
			if(deviceId != null){
				String retVal = "";
				if(deviceId.equalsIgnoreCase("*")){
					//exResult  = deviceManager.updateDevicesArmStatusByCustomerId(customerId, status);
					CommandIssueServiceManager cism = new CommandIssueServiceManager();
					if(status.getName().equals(Constants.ARM))//AWAY
					{
						retVal = cism.activateModeInternal(customerId,Constants.ARM);
						retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
						if(retVal.startsWith("SUCCESS")){
							exResult = true;
						}else{
							//sumit: Handle ARM_DEVICES_PROGRESS_FAIL
							message = retVal;
						}
					}
					else//HOME
					{
						retVal = cism.activateModeInternal(customerId,Constants.DISARM);
						retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
						if(retVal.startsWith("SUCCESS")){
							exResult = true;
						}
					}
				}else if(deviceId.equalsIgnoreCase("*STAY")){ //STAY
					CommandIssueServiceManager cism = new CommandIssueServiceManager();
					retVal = cism.activateModeInternal(customerId,Constants.STAY);
					retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
					if(retVal.startsWith("SUCCESS")){
						exResult = true;
					}else{
						//sumit: Handle STAY_DEVICES_PROGRESS_FAIL
						message = retVal;
					}
				}else{//obsolete code
						//vibhu start
						CommandIssueServiceManager cism = new CommandIssueServiceManager();
						exResult = cism.deviceArmDisarmStatusUpdateFromMID(customerId, deviceId, status);
						//exResult = deviceManager.updateDeviceArmStatus(customerId, deviceId, status);
						//vibhu end
				}
			}
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += exResult ? "success" : "failure";
			resultXml += "</status>";
			if(message != null){
				resultXml += "<message>";
				resultXml += message;
				resultXml += "</message>";
				
			}
			
		}
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	catch (Exception e) {
		LogUtil.info("", e);
		LogUtil.info("Error "+  e.getMessage());
		resultXml = "<imonitor><status>failure</status></imonitor>";
	}
	return resultXml;
}

public String scenarioDetailsSingleUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		
//		fetch the schedules using the customer id.
		ScenarioManager scenarioManager = new ScenarioManager();
		
		
		List<Object[]> scenarios = scenarioManager.listAllScenariosByCustomerId(customerId);
		XStream stream=new XStream();
		if(scenarios == null){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			
			return resultXml;
		}
		Map<String, String> scenarioActions = new HashMap<String, String>();
		Map<String, String> scenarioDetails = new HashMap<String, String>();
		Map<String, Integer> scenarioId=new HashMap<String, Integer>();
		for (Object[] objects : scenarios) 
		{
			//LogUtil.info("Loop enter : Object : "+ objects);
			String scenarioName = IMonitorUtil.convertToString(objects[1]);
			String scenarioDescription = IMonitorUtil.convertToString(objects[2]);
			String ScenarioiD=IMonitorUtil.convertToString(objects[0]);
			int id=Integer.parseInt(ScenarioiD);
			scenarioId.put(scenarioName, id);
			scenarioDetails.put(scenarioName, scenarioDescription);
			String deviceId = IMonitorUtil.convertToString(objects[3]);
			String command = IMonitorUtil.convertToString(objects[4]);
			String level = IMonitorUtil.convertToString(objects[5]);
			//String activationStatus = IMonitorUtil.convertToString(objects[6]);
			String actions = scenarioActions.get(scenarioName);
			//LogUtil.info("deviceId : "+deviceId);
		//	LogUtil.info("command : "+command);
			if(actions == null){
				actions = "";
			}
			actions += "<command>";
			actions += "<deviceid>";
			actions += deviceId;
			actions += "</deviceid>";
			actions += "<eventname>";
			actions += command;
			actions += "</eventname>";
			if(level != null){
				actions += "<level>";
				actions += level;
				actions += "</level>";
			}
			actions += "</command>";
			/*actions += "<activationStatus>";
			actions += activationStatus;
			actions += "</activationStatus>";*/
			scenarioActions.put(scenarioName, actions);
		}
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml += "<scenarios>";
		
		Set<String> keySet = scenarioActions.keySet();
			for (String scheduleName : keySet) {
				resultXml += "<scenario>";
				resultXml += "<id>";
				resultXml += scenarioId.get(scheduleName);
				resultXml += "</id>";
				resultXml += "<name>";
				resultXml += scheduleName;
				resultXml += "</name>";
				resultXml += "<description>";
				resultXml += scenarioDetails.get(scheduleName);
				resultXml += "</description>";
				resultXml += scenarioActions.get(scheduleName);
				resultXml += "</scenario>";
			}
			
			
		
		resultXml += "</scenarios>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
		e.printStackTrace();
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
		e.printStackTrace();
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
		e.printStackTrace();
	}
	catch (Exception e) {
		e.printStackTrace();
		LogUtil.info("Errorrr : " + e.getMessage());
	}
	return resultXml;
}

public String executeScenarioSingleUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		NodeList scneraioNameNodes = document.getElementsByTagName("scenarioname");
		Element scneraioNameNode = (Element) scneraioNameNodes.item(0);
		String scenarioName = XmlUtil.getCharacterDataFromElement(scneraioNameNode);
		
		ScenarioManager scenarioManager = new ScenarioManager();
         boolean exeResult = false;
		Scenario s = scenarioManager.getScenarioWithGatewayByNameAndCustId(customerId, scenarioName);
		ActionModel actionModel = new ActionModel(null, s);
		ImonitorControllerAction scenarioExecutionAction = new ScenarioExecutionAction();
		scenarioExecutionAction.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForScenarioResult(scenarioExecutionAction);
		if(resultFromImvg)
		{
			if(scenarioExecutionAction.isActionSuccess())
			{
				exeResult = true;
			}
		}
		
		if(!exeResult){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	} catch (Exception e){
		resultXml = "<imonitor><status>failure</status></imonitor>";
	}
	return resultXml;
}

public String scenarioDetailsMultipleUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superuserNodes = document.getElementsByTagName("superUserId");
		Element superuserNode = (Element) superuserNodes.item(0);
		String superuser = XmlUtil.getCharacterDataFromElement(superuserNode);
		SuperUserManager superUserManager=new SuperUserManager();
		AdminSuperUser adminSuperUser=superUserManager.getsuperUserBySuperUserId(superuser);
		XStream stream=new XStream();
		List<Customer> customerIdList= superUserManager.listOfCustomersForSuperUser(adminSuperUser.getId());

//		fetch the schedules using the customer id.
		ScenarioManager scenarioManager = new ScenarioManager();
		
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml += "<scenarios>";
		for (Customer customer : customerIdList)
		{
			List<Object[]> scenarios = scenarioManager.listAllScenariosByCustomerId(customer.getCustomerId());
			if(scenarios == null){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				
				return resultXml;
			}
			Map<String, String> scenarioActions = new HashMap<String, String>();
			Map<String, String> scenarioDetails = new HashMap<String, String>();
			Map<String, Integer> scenarioId=new HashMap<String, Integer>();
			for (Object[] objects : scenarios) 
			{
				//LogUtil.info("Loop enter : Object : "+ objects);
				String scenarioName = IMonitorUtil.convertToString(objects[1]);
				String scenarioDescription = IMonitorUtil.convertToString(objects[2]);
				String ScenarioiD=IMonitorUtil.convertToString(objects[0]);
				int id=Integer.parseInt(ScenarioiD);
				scenarioId.put(scenarioName, id);
				scenarioDetails.put(scenarioName, scenarioDescription);
				String deviceId = IMonitorUtil.convertToString(objects[3]);
				String command = IMonitorUtil.convertToString(objects[4]);
				String level = IMonitorUtil.convertToString(objects[5]);
				//String activationStatus = IMonitorUtil.convertToString(objects[6]);
				String actions = scenarioActions.get(scenarioName);
				//LogUtil.info("deviceId : "+deviceId);
			//	LogUtil.info("command : "+command);
				if(actions == null){
					actions = "";
				}
				actions += "<command>";
				actions += "<deviceid>";
				actions += deviceId;
				actions += "</deviceid>";
				actions += "<eventname>";
				actions += command;
				actions += "</eventname>";
				if(level != null){
					actions += "<level>";
					actions += level;
					actions += "</level>";
				}
				actions += "</command>";
				/*actions += "<activationStatus>";
				actions += activationStatus;
				actions += "</activationStatus>";*/
				scenarioActions.put(scenarioName, actions);
			}
			
			Set<String> keySet = scenarioActions.keySet();
				for (String scheduleName : keySet) {
					resultXml += "<scenario>";
					resultXml += "<customerId>";
					resultXml += customer.getCustomerId();
					resultXml += "</customerId>";
					resultXml += "<id>";
					resultXml += scenarioId.get(scheduleName);
					resultXml += "</id>";
					resultXml += "<name>";
					resultXml += scheduleName;
					resultXml += "</name>";
					resultXml += "<description>";
					resultXml += scenarioDetails.get(scheduleName);
					resultXml += "</description>";
					resultXml += scenarioActions.get(scheduleName);
					resultXml += "</scenario>";
				}
		}
		resultXml += "</scenarios>";
		resultXml += "</imonitor>";
		
		
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
		e.printStackTrace();
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
		e.printStackTrace();
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
		e.printStackTrace();
	}
	catch (Exception e) {
		e.printStackTrace();
		LogUtil.info("Errorrr : " + e.getMessage());
	}
	return resultXml;
}

public String GetAlarmForSingleUser(String xml) {
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		NodeList deviceIdNodes = document.getElementsByTagName("macid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String gateWayId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		NodeList TimeStampNodes = document.getElementsByTagName("timeStamp");
		Element timestampNode = (Element) TimeStampNodes.item(0);
		String TimeStamp = XmlUtil.getCharacterDataFromElement(timestampNode);
		
		GatewayManager gateWay=new GatewayManager();
		CustomerManager customer=new CustomerManager();
		Customer customerfromdb=customer.getCustomerByCustomerId(customerId);
		GateWay gateWays=gateWay.getGateWayByCustomer(customerfromdb);
		String macid=gateWays.getMacId();
		
		if(!(macid.equals(gateWayId))){
			resultXml += "<ims>";
			resultXml += "We couldn't find a valid GateWay with your inputs !!";
			resultXml += "</ims>";
		} 
		else
		{
		    AlertServiceManager alertserviceManger=new AlertServiceManager();
		   
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
				Timestamp timestamp = null;
				java.util.Date date = null;
				if(!TimeStamp.equals("null"))
				{
					try 
					{
						date = sdf.parse(TimeStamp);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				timestamp = new java.sql.Timestamp(date.getTime());	
				}
				else
				{
					timestamp=new Timestamp(0);
				}
				
				XStream stream=new XStream();
			List<Object[]> Objects = alertserviceManger.listAlarmsForMid(customerfromdb,timestamp);
			resultXml  = "<ims>";
			if(Objects == null){
				resultXml = "<ims>";
				resultXml += "<stus>";
				resultXml += "failure";
				resultXml += "</stus>";
				resultXml += "</ims>";
				return resultXml;
			}
			for (Object[] alarm : Objects) {
				String AlertName=IMonitorUtil.convertToString(alarm[2]);
				String imvgAlertTime=IMonitorUtil.convertToString(alarm[0]);
				String DeviceName=IMonitorUtil.convertToString(alarm[1]);
				String Difference=IMonitorUtil.convertToString(alarm[3]);
				resultXml +="<Alm>";  
				resultXml +="<aNm>";   
				resultXml +=AlertName;
				resultXml +="</aNm>";
				resultXml +="<iAT>";    
				resultXml +=imvgAlertTime;
				resultXml +="</iAT>";
				resultXml +="<dNm>";   
				resultXml +=DeviceName;
				resultXml +="</dNm>";
				resultXml +="<tDf>";   
				resultXml +=Difference;
				resultXml +="</tDf>";
				resultXml +="</Alm>";
			}
			resultXml += "</ims>";
		}
		} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	return resultXml;
}

public String GetAlarmForMultipleUser(String xml) {
	LogUtil.info("GetAlarmForMultipleUser IN : "+ xml);
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superuserNodes = document.getElementsByTagName("superUserId");
		Element superuserNode = (Element) superuserNodes.item(0);
		String superuser = XmlUtil.getCharacterDataFromElement(superuserNode);
		SuperUserManager superUserManager=new SuperUserManager();
		AdminSuperUser adminSuperUser=superUserManager.getsuperUserBySuperUserId(superuser);
		XStream stream=new XStream();
		List<Customer> customerIdList= superUserManager.listOfCustomersForSuperUser(adminSuperUser.getId());
		NodeList TimeStampNodes = document.getElementsByTagName("timeStamp");
		Element timestampNode = (Element) TimeStampNodes.item(0);
		String TimeStamp = XmlUtil.getCharacterDataFromElement(timestampNode);
		
		resultXml  = "<ims>";
		for (Customer customer : customerIdList) {
		GatewayManager gateWay=new GatewayManager();
		CustomerManager customerManager=new CustomerManager();
		Customer customerfromdb=customerManager.getCustomerByCustomerId(customer.getCustomerId());
		GateWay gateWays=gateWay.getGateWayByCustomer(customerfromdb);
		String macid=gateWays.getMacId();

		    AlertServiceManager alertserviceManger=new AlertServiceManager();
		   
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
				Timestamp timestamp = null;
				java.util.Date date = null;
				if(!TimeStamp.equals("null"))
				{
					try 
					{
						date = sdf.parse(TimeStamp);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				timestamp = new java.sql.Timestamp(date.getTime());	
				}
				else
				{
					timestamp=new Timestamp(0);
				}
				
				
			
			List<Object[]> Objects = alertserviceManger.listAlarmsForMid(customerfromdb,timestamp);
			
			if(Objects == null){
				resultXml = "<ims>";
				resultXml += "<stus>";
				resultXml += "failure";
				resultXml += "</stus>";
				resultXml += "</ims>";
				return resultXml;
			}
			for (Object[] alarm : Objects) {
				
				String AlertName=IMonitorUtil.convertToString(alarm[2]);
				String imvgAlertTime=IMonitorUtil.convertToString(alarm[0]);
				String DeviceName=IMonitorUtil.convertToString(alarm[1]);
				String Difference=IMonitorUtil.convertToString(alarm[3]);
				resultXml +="<Alm>";   
				resultXml +="<aNm>";   
				resultXml +=AlertName;
				resultXml +="</aNm>";
				resultXml +="<iAT>";   
				resultXml +=imvgAlertTime;
				resultXml +="</iAT>";
				resultXml +="<dNm>";    
				resultXml +=DeviceName;
				resultXml +="</dNm>";
				resultXml +="<tDf>";    
				resultXml +=Difference;
				resultXml +="</tDf>";
				resultXml +="</Alm>";
			}

		}
		resultXml += "</ims>";

		} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	return resultXml;
}


public String ScheduleDetailsForSingleUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

//		fetch the schedules using the customer id.
		ScheduleManager scheduleManager = new ScheduleManager();
		
		
		List<Object[]> schedules = scheduleManager.listAllSchedulesByCustomerId(customerId);
		XStream stream=new XStream();
		//LogUtil.info("Schedules List : "+ stream.toXML(schedules));
		if(schedules == null){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		Map<String, String> scheduleActions = new HashMap<String, String>();
		Map<String, String> scheduleDetails = new HashMap<String, String>();
		Map<String, String> scheduleActivation = new HashMap<String, String>();
		Map<String, Integer> scheduleId=new HashMap<String, Integer>();
		String activationStatus = "";
		for (Object[] objects : schedules) 
		{
			//LogUtil.info("Loop enter : Object : "+ objects);
			String scheduleName = IMonitorUtil.convertToString(objects[1]);
			String scheduleDescription = IMonitorUtil.convertToString(objects[2]);
			String ScheduleiD=IMonitorUtil.convertToString(objects[0]);
			int id=Integer.parseInt(ScheduleiD);
			scheduleId.put(scheduleName, id);
			scheduleDetails.put(scheduleName, scheduleDescription);
			String deviceId = IMonitorUtil.convertToString(objects[3]);
			String command = IMonitorUtil.convertToString(objects[4]);
			String level = IMonitorUtil.convertToString(objects[5]);
			activationStatus = IMonitorUtil.convertToString(objects[6]);
			scheduleActivation.put(scheduleName, activationStatus);
			String actions = scheduleActions.get(scheduleName);
			//LogUtil.info("deviceId : "+deviceId);
		//	LogUtil.info("command : "+command);
			if(actions == null){
				actions = "";
			}
			/*actions += "<command>";
			actions += "<deviceid>";
			actions += deviceId;
			actions += "</deviceid>";
			actions += "<eventname>";
			actions += command;
			actions += "</eventname>";
			if(level != null){
				actions += "<level>";
				actions += level;
				actions += "</level>";
			}
			actions += "</command>";
			actions += "<activationStatus>";
			actions += activationStatus;
			actions += "</activationStatus>";*/
			scheduleActions.put(scheduleName, actions);
			scheduleActions.put(scheduleName, activationStatus);
		}
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml += "<schedules>";
		
		Set<String> keySet = scheduleActions.keySet();
			for (String scheduleName : keySet) {
				resultXml += "<schedule>";
				resultXml += "<id>";
				resultXml += scheduleId.get(scheduleName);
				resultXml += "</id>";
				resultXml += "<name>";
				resultXml += scheduleName;
				resultXml += "</name>";
				resultXml += "<description>";
				resultXml += scheduleDetails.get(scheduleName);
				resultXml += "</description>";
				resultXml +="<activationStatus>";
				resultXml += scheduleActivation.get(scheduleName);
				resultXml +="</activationStatus>";
				resultXml += "</schedule>";
			}
			
			
		
		resultXml += "</schedules>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
		e.printStackTrace();
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
		e.printStackTrace();
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
		e.printStackTrace();
	}
	
	return resultXml;
}


public String ScheduleDetailsForMultipleUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superuserNodes = document.getElementsByTagName("superUserId");
		Element superuserNode = (Element) superuserNodes.item(0);
		String superuser = XmlUtil.getCharacterDataFromElement(superuserNode);
		SuperUserManager superUserManager=new SuperUserManager();
		AdminSuperUser adminSuperUser=superUserManager.getsuperUserBySuperUserId(superuser);
		XStream stream=new XStream();
		List<Customer> customerIdList= superUserManager.listOfCustomersForSuperUser(adminSuperUser.getId());

//		fetch the schedules using the customer id.
		ScheduleManager scheduleManager = new ScheduleManager();
		
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml += "<schedules>";
		
		for (Customer customer : customerIdList) 
		{
			List<Object[]> schedules = scheduleManager.listAllSchedulesByCustomerId(customer.getCustomerId());
			//LogUtil.info("Schedules List : "+ stream.toXML(schedules));
			if(schedules == null){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			Map<String, String> scheduleActions = new HashMap<String, String>();
			Map<String, String> scheduleDetails = new HashMap<String, String>();
			Map<String, String> scheduleActivation = new HashMap<String, String>();
			Map<String, Integer> scheduleId=new HashMap<String, Integer>();
			String activationStatus = "";
			for (Object[] objects : schedules) 
			{
				//LogUtil.info("Loop enter : Object : "+ objects);
				String scheduleName = IMonitorUtil.convertToString(objects[1]);
				String scheduleDescription = IMonitorUtil.convertToString(objects[2]);
				String ScheduleiD=IMonitorUtil.convertToString(objects[0]);
				int id=Integer.parseInt(ScheduleiD);
				scheduleId.put(scheduleName, id);
				scheduleDetails.put(scheduleName, scheduleDescription);
				String deviceId = IMonitorUtil.convertToString(objects[3]);
				String command = IMonitorUtil.convertToString(objects[4]);
				String level = IMonitorUtil.convertToString(objects[5]);
				activationStatus = IMonitorUtil.convertToString(objects[6]);
				scheduleActivation.put(scheduleName, activationStatus);
				String actions = scheduleActions.get(scheduleName);
				//LogUtil.info("deviceId : "+deviceId);
			//	LogUtil.info("command : "+command);
				if(actions == null){
					actions = "";
				}
				/*actions += "<command>";
				actions += "<deviceid>";
				actions += deviceId;
				actions += "</deviceid>";
				actions += "<eventname>";
				actions += command;
				actions += "</eventname>";
				if(level != null){
					actions += "<level>";
					actions += level;
					actions += "</level>";
				}
				actions += "</command>";
				actions += "<activationStatus>";
				actions += activationStatus;
				actions += "</activationStatus>";*/
				scheduleActions.put(scheduleName, actions);
				scheduleActions.put(scheduleName, activationStatus);
			}

			Set<String> keySet = scheduleActions.keySet();
				for (String scheduleName : keySet) {
					resultXml += "<schedule>";
					resultXml += "<id>";
					resultXml += scheduleId.get(scheduleName);
					resultXml += "</id>";
					resultXml += "<name>";
					resultXml += scheduleName;
					resultXml += "</name>";
					resultXml += "<description>";
					resultXml += scheduleDetails.get(scheduleName);
					resultXml += "</description>";
					resultXml +="<activationStatus>";
					resultXml += scheduleActivation.get(scheduleName);
					resultXml +="</activationStatus>";
					resultXml += "</schedule>";
				}
		}

		resultXml += "</schedules>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
		e.printStackTrace();
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
		e.printStackTrace();
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
		e.printStackTrace();
	}
	
	return resultXml;
}


public String activateScheduleforSuperUser(String xml){
	String resultXml = "";
	
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		NodeList scheduleNameNodes = document.getElementsByTagName("schedulename");
		Element scheduleNode = (Element) scheduleNameNodes.item(0);
		String scheduleName = XmlUtil.getCharacterDataFromElement(scheduleNode);
		
		NodeList activationNode = document.getElementsByTagName("activationstatus");
		Element actstsNode = (Element) activationNode.item(0);
		String activationsts = XmlUtil.getCharacterDataFromElement(actstsNode);
		
		ScheduleManager schedulemng=new ScheduleManager();
		boolean exeResult = false;
		List<Long> scheduleIdsList=schedulemng.getscheduleidsfromschedulenameForSuperUser(scheduleName, customerId);
		for (Long long1 : scheduleIdsList) 
		{
			Schedule schedule=schedulemng.retrieveScheduleDetails(long1);
			
			schedule.setActivationStatus(Integer.parseInt(activationsts));
			ActionModel actionModel = new ActionModel(null, schedule);
			ImonitorControllerAction scheduleActivationAction = new ScheduleActivateAction();
			scheduleActivationAction.executeCommand(actionModel);
			boolean resultFromImvg = IMonitorUtil.waitForResult(scheduleActivationAction);
			if(resultFromImvg)
			{
				if(scheduleActivationAction.isActionSuccess())
				{schedulemng.updateScheduleWithDetails(schedule);
					exeResult = true;
				}
			}
			
		}
		if(!exeResult){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml +="<activationstatus>";
		resultXml +=activationsts;
		resultXml +="</activationstatus>";
		resultXml += "</imonitor>";
		
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}

	return resultXml;
}

/*To control device by super user login
 * Added by apoorva
 * 
 */
public String controlSuperUserdevice(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		XStream stream=new XStream();
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
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
		
		String[] splitString=deviceId.split("-");
		String macId1= splitString[0];
	
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByMacId(macId1);
		//LogUtil.info("Customer : " + stream.toXML(customer));
		DeviceManager deviceManager = new DeviceManager();
		Object[] result = deviceManager.checkDeviceAndReturnCommunicationDetails(deviceId, customer.getCustomerId(), command);
		if(result == null){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;			
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
			
			if(command.equals(Constants.AC_ON))
			device.setCommandParam("22");
			
			if(command.equals(Constants.AC_OFF))
			device.setCommandParam("0");
			
			
			
			if(level != null){
				device.setCommandParam(level);
			}
			
			
			
			ActionModel actionModel = new ActionModel(device, null);
			controllerAction.executeCommand(actionModel);
			boolean exResult = waitForResult(controllerAction);
			device = controllerAction.getActionModel().getDevice();
//			Refer alertnotifier and re-write the code.
//			IMonitorUtil.executeActions(command, queue , ip, port);
			resultXml  = "<imonitor>";
			resultXml  += "<status>";
			//resultXml  += "success";
			resultXml += exResult ? "success" : "failure";
			resultXml  += "</status>";
			resultXml  += "<levelstatus>";
			resultXml  += device.getCommandParam();
			resultXml  += "</levelstatus>";
			resultXml += "</imonitor>";
		}
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (InstantiationException e) {
		resultXml = "The command may not be implemented. Please check with CMS administrator " + e.getMessage();
	} catch (IllegalAccessException e) {
		resultXml = "The command may not be implemented. Please check with CMS administrator " + e.getMessage();
	}
	return resultXml;
}

public String startLiveStreamForSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		XStream stream=new XStream();
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		

		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		String[] splitString=deviceId.split("-");
		String macId1= splitString[0];
		
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByMacId(macId1);
		//LogUtil.info("Customer : " + stream.toXML(customer));
		
		DeviceManager deviceManager = new DeviceManager();
		Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new GetLiveStreamAction();
		controllerAction.executeCommand(actionModel);
		String url = Constants.FAILURE;
		IMonitorUtil.waitForResult(controllerAction);
		url = IMonitorUtil.convertToString(controllerAction.getActionModel().getData());
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml += "<rtspurl>";
		resultXml += url;
		resultXml += "</rtspurl>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	return resultXml;
}

public String controlCameraForSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		XStream stream=new XStream();
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		String[] splitString=deviceId.split("-");
		String macId1= splitString[0];
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByMacId(macId1);
		
		
		NodeList controlPanTilts = document.getElementsByTagName("controlpantilt");
		Element controlPanTiltNode = (Element) controlPanTilts.item(0);
		String controlPanTilt = XmlUtil.getCharacterDataFromElement(controlPanTiltNode);
		DeviceManager deviceManager = new DeviceManager();
		Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
		CameraConfiguration cameraConfiguration = new CameraConfiguration();
		
		cameraConfiguration.setControlPantilt(controlPanTilt);
		device.setDeviceConfiguration(cameraConfiguration);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new CameraPanTiltControlAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	return resultXml;
}

public String controlNightVisionForSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		XStream stream=new XStream();
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		String[] splitString=deviceId.split("-");
		String macId1= splitString[0];
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByMacId(macId1);
		
		NodeList controlNightVisions = document.getElementsByTagName("controlnightvision");
		Element controlNightVisionNode = (Element) controlNightVisions.item(0);
		String controlNightVision = XmlUtil.getCharacterDataFromElement(controlNightVisionNode);
		
		DeviceManager deviceManager = new DeviceManager();
		Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
		CameraConfiguration cameraConfiguration = null;
		DeviceConfiguration deviceConfiguration = device.getDeviceConfiguration();
		if(deviceConfiguration == null){
			cameraConfiguration = new CameraConfiguration();
		}else{
			cameraConfiguration = (CameraConfiguration) deviceConfiguration;
		}
		cameraConfiguration.setControlNightVision(controlNightVision);
		device.setDeviceConfiguration(cameraConfiguration);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new CameraNightVisionControlAction();
		controllerAction.executeCommand(actionModel);
		IMonitorUtil.waitForResult(controllerAction);
		
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	return resultXml;
}

public String partialOpenStartForSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		XStream stream=new XStream();
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		
		String[] splitString=deviceId.split("-");
		String macId1= splitString[0];
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByMacId(macId1);
		
		DeviceManager deviceManager = new DeviceManager();
		Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
		
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new PartialCurtainOpenStart();
		controllerAction.executeCommand(actionModel);
		boolean exResult = waitForResult(controllerAction);
	
		device = controllerAction.getActionModel().getDevice();
//		Refer alertnotifier and re-write the code.
//		IMonitorUtil.executeActions(command, queue , ip, port);
		resultXml  = "<imonitor>";
		resultXml  += "<status>";
		//resultXml  += "success";
		resultXml += exResult ? "success" : "failure";
		resultXml  += "</status>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	return resultXml;
}
public String partialOpenStopForSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		XStream stream=new XStream();
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
	
		
		String[] splitString=deviceId.split("-");
		String macId1= splitString[0];
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByMacId(macId1);
		
		DeviceManager deviceManager = new DeviceManager();
		Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
		
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new PartialCurtainOpenStop();
		controllerAction.executeCommand(actionModel);
		boolean exResult = waitForResult(controllerAction);
		
		device = controllerAction.getActionModel().getDevice();
//		Refer alertnotifier and re-write the code.
//		IMonitorUtil.executeActions(command, queue , ip, port);
		resultXml  = "<imonitor>";
		resultXml  += "<status>";
		//resultXml  += "success";
		resultXml += exResult ? "success" : "failure";
		resultXml  += "</status>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	return resultXml;
}
public String partialCloseStartForSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		XStream stream=new XStream();
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
	
		
		String[] splitString=deviceId.split("-");
		String macId1= splitString[0];
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByMacId(macId1);
		
		DeviceManager deviceManager = new DeviceManager();
		Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
		
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new PartialCurtainCloseStart();
		controllerAction.executeCommand(actionModel);
		boolean exResult = waitForResult(controllerAction);
		
		device = controllerAction.getActionModel().getDevice();
//		Refer alertnotifier and re-write the code.
//		IMonitorUtil.executeActions(command, queue , ip, port);
		resultXml  = "<imonitor>";
		resultXml  += "<status>";
		//resultXml  += "success";
		resultXml += exResult ? "success" : "failure";
		resultXml  += "</status>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	return resultXml;
}
public String partialCloseStopForSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		XStream stream=new XStream();
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		String[] splitString=deviceId.split("-");
		String macId1= splitString[0];
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByMacId(macId1);
		
		DeviceManager deviceManager = new DeviceManager();
		Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
		
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new PartialCurtainCloseStop();
		controllerAction.executeCommand(actionModel);
		boolean exResult = waitForResult(controllerAction);
		
		device = controllerAction.getActionModel().getDevice();
//		Refer alertnotifier and re-write the code.
//		IMonitorUtil.executeActions(command, queue , ip, port);
		resultXml  = "<imonitor>";
		resultXml  += "<status>";
		//resultXml  += "success";
		resultXml += exResult ? "success" : "failure";
		resultXml  += "</status>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	return resultXml;
}


public String fullOpenCloseForSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		XStream stream=new XStream();
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);

		String[] splitString=deviceId.split("-");
		String macId1= splitString[0];
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByMacId(macId1);
		
		DeviceManager deviceManager = new DeviceManager();
		Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
		
		NodeList commandNodes = document.getElementsByTagName("commandname");
		Element commandNode = (Element) commandNodes.item(0);
		String command = XmlUtil.getCharacterDataFromElement(commandNode);
		
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = null;
		if(command.equals("CURTAIN_OPEN")) {
			controllerAction = new CurtainopenAction();
		}else {
			controllerAction = new CurtainCloseAction();
		}
	
		controllerAction.executeCommand(actionModel);
		boolean exResult = waitForResult(controllerAction);
		
		device = controllerAction.getActionModel().getDevice();
//		Refer alertnotifier and re-write the code.
//		IMonitorUtil.executeActions(command, queue , ip, port);
		resultXml  = "<imonitor>";
		resultXml  += "<status>";
		//resultXml  += "success";
		resultXml += exResult ? "success" : "failure";
		resultXml  += "</status>";
		resultXml  += "<levelstatus>";
		resultXml  += device.getCommandParam();
		resultXml  += "</levelstatus>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	return resultXml;
}

public String controlFanModesForSuperUser(String xml){
	
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		XStream stream=new XStream();
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		String[] splitString=deviceId.split("-");
		String macId1= splitString[0];
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByMacId(macId1);
		
		NodeList commandNodes = document.getElementsByTagName("commandname");
		Element commandNode = (Element) commandNodes.item(0);
		String command = XmlUtil.getCharacterDataFromElement(commandNode);
		
		NodeList fanModeValueNodes = document.getElementsByTagName("fanModeValue");
		Element fanModeValueNode = (Element) fanModeValueNodes.item(0);
		String fanModeValue = XmlUtil.getCharacterDataFromElement(fanModeValueNode);
		boolean exResult=false;
		ImonitorControllerAction controllerAction= null;
		if(command.equals("AC_MODE_CONTROL"))
		{
			DeviceManager deviceManager = new DeviceManager();
			Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
			device.setCommandParam(fanModeValue);
			ActionModel actionModel = new ActionModel(device, null);
			controllerAction= new AcControlAction();
			controllerAction.executeCommand(actionModel);
			exResult=IMonitorUtil.waitForResult(controllerAction);
		}
		else if(command.equals("AC_FAN_MODE"))
		{
		DeviceManager deviceManager = new DeviceManager();
		Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
		acConfiguration acConfiguration=null;
		DeviceConfiguration deviceConfiguration = device.getDeviceConfiguration();
		if(deviceConfiguration == null){
			acConfiguration = new acConfiguration();
		}else{
			acConfiguration = (acConfiguration) device.getDeviceConfiguration();
		}
		acConfiguration.setFanModeValue(fanModeValue);
		device.setDeviceConfiguration(acConfiguration);
		ActionModel actionModel = new ActionModel(device, null);
		controllerAction = new AcFanModeControlAction();
		controllerAction.executeCommand(actionModel);
		exResult=IMonitorUtil.waitForResult(controllerAction);
		}
		else if(command.equals("AC_SWING_CONTROL"))
		{
			DeviceManager deviceManager = new DeviceManager();
			Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
			acConfiguration acConfiguration=null;
			DeviceConfiguration deviceConfiguration = device.getDeviceConfiguration();
			if(deviceConfiguration == null){
				acConfiguration = new acConfiguration();
			}else{
				acConfiguration = (acConfiguration) device.getDeviceConfiguration();
			}
			acConfiguration.setAcSwing(fanModeValue);
			device.setDeviceConfiguration(acConfiguration);
			ActionModel actionModel = new ActionModel(device, null);
			controllerAction = new AcSwingControlAction();
			controllerAction.executeCommand(actionModel);
			exResult=IMonitorUtil.waitForResult(controllerAction);
		}
		
		Device deviceFromDb=controllerAction.getActionModel().getDevice();

		acConfiguration AcConfig=(acConfiguration) deviceFromDb.getDeviceConfiguration();
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += exResult ? "success" : "failure";
		resultXml += "</status>";
		
		if(fanModeValue.equals("5"))
		{
			resultXml  += "<Mode>";
			resultXml  +=AcConfig.getFanMode();
			resultXml  += "</Mode>";
			resultXml  += "<FanMode>";
			resultXml  +=AcConfig.getFanModeValue();
			resultXml  += "</FanMode>";
			resultXml  += "<ACSwing>";
			resultXml  +=AcConfig.getAcSwing();
			resultXml  += "</ACSwing>";
		}
		resultXml  += "<levelstatus>";
		resultXml  += deviceFromDb.getCommandParam();
		resultXml  += "</levelstatus>";
		
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	return resultXml;
}

public String saveDeviceDetailsForAndroidPushNotification(String xml)
{
	
	String resultXml = "";
	XStream stream=new XStream();
	try {
		Document document = XmlUtil.getDocument(xml);
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		NodeList deviceTokenNodes = document.getElementsByTagName("devicetoken");
		Element deviceTokenNode = (Element) deviceTokenNodes.item(0);
		String deviceToken = XmlUtil.getCharacterDataFromElement(deviceTokenNode);
		
		
		NodeList ImeiNumberNodes = document.getElementsByTagName("ImeiNumber");
		Element ImeiNumberNode = (Element) ImeiNumberNodes.item(0);
		String ImeiNumber = XmlUtil.getCharacterDataFromElement(ImeiNumberNode);
		
		
		NodeList deviceTypeNodes = document.getElementsByTagName("devicetype");
		Element deviceTypeNode = (Element) deviceTypeNodes.item(0);
		String deviceType = XmlUtil.getCharacterDataFromElement(deviceTypeNode);
		
		NodeList appNameNodes = document.getElementsByTagName("appName");
		Element appNameNode = (Element) appNameNodes.item(0);
		String appName = XmlUtil.getCharacterDataFromElement(appNameNode);
		LogUtil.info("appName"+appName);
		
		
		if (deviceToken.equals("?") || deviceType.equals("?") || ImeiNumber.equals("?")) 
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			LogUtil.info("DeviceToken / DeviceType / ImeiNumber  is null");
			return resultXml;
		}
		
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByCustomerId(customerId);
		//LogUtil.info("Customer Object ---> "+stream.toXML(customer));
		
		DeviceManager deviceManager=new DeviceManager();
		
		List<Device> deviceList=deviceManager.getDeviceListOfCustomer(customer);
		LogUtil.info("deviceList="+deviceList);
		LogUtil.info("deviceList stream="+stream.toXML(deviceList));
		
		for(Device device : deviceList){
			LogUtil.info("after for loop=");
		DaoManager daoManager=new DaoManager();
		boolean res = deviceManager.verifyWhetherDevicePresentInDb(customer.getId(), ImeiNumber);
		LogUtil.info(" res="+res);
		if (res == false) 
		{
			PushNotification pushNotification=new PushNotification();
			pushNotification.setCustomer(customer);
			pushNotification.setDeviceToken(deviceToken);
			pushNotification.setDeviceType(deviceType);
			pushNotification.setImeiNumber(ImeiNumber);
			pushNotification.setAppName(appName);
			res = daoManager.save(pushNotification);
			
		}
		else
		{
			PushNotification pushFromDb = deviceManager.getPushNotificationObjectByImei(ImeiNumber);
			pushFromDb.setDeviceToken(deviceToken);
			pushFromDb.setCustomer(customer);
			pushFromDb.setAppName(appName);
			res=daoManager.update(pushFromDb);
			
		}
			
		if (res) 
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success" ;
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure" ;
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
	}
	} 
	catch (ParserConfigurationException e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml  = "Couldn't parse the input";
	}
	catch (SAXException e) 
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml = "Couldn't parse the input";
	} 
	catch (IOException e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml = "Couldn't parse the input";
	}
	catch (Exception e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
	}
	LogUtil.info("resultXml="+stream.toXML(resultXml));
	return resultXml;
}


public String disablePushNotificationAfterCustomerLogout(String xml)
{
	String resultXml = "";
	XStream stream=new XStream();
	try {
		Document document = XmlUtil.getDocument(xml);
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		
		NodeList ImeiNumberNodes = document.getElementsByTagName("ImeiNumber");
		Element ImeiNumberNode = (Element) ImeiNumberNodes.item(0);
		String ImeiNumber = XmlUtil.getCharacterDataFromElement(ImeiNumberNode);
		
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByCustomerId(customerId);
		//LogUtil.info("Customer Object ---> "+stream.toXML(customer));
		
		SuperUserManager superUserManager = new SuperUserManager();


		boolean res = superUserManager.disablePushNotificationAfterCustomerLogout(ImeiNumber);
		
		
		if (res) 
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success" ;
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure" ;
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}

	} 
	catch (ParserConfigurationException e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml  = "Couldn't parse the input";
	}
	catch (SAXException e) 
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml = "Couldn't parse the input";
	} 
	catch (IOException e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml = "Couldn't parse the input";
	}
	catch (Exception e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
	}
	return resultXml;
}

public String disablePushNotificationForSuperUser(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		
		
		NodeList ImeiNumberNodes = document.getElementsByTagName("ImeiNumber");
		Element ImeiNumberNode = (Element) ImeiNumberNodes.item(0);
		String ImeiNumber = XmlUtil.getCharacterDataFromElement(ImeiNumberNode);
	
		
		
		
		SuperUserManager superUserManager = new SuperUserManager();
		boolean res = superUserManager.disablePushNotificationAfterCustomerLogout(ImeiNumber);
		
		
		if(res){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	return resultXml;
}

public String armDevicesForMultipleUsers(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		Status status = IMonitorUtil.getStatuses().get(Constants.ARM);
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		AdminSuperUser adminSuperUser=superUserManager.getsuperUserBySuperUserId(superUserId);
		XStream stream=new XStream();
		//LogUtil.info("List of customers : "+stream.toXML(list) );
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else
		{
			List<Customer> customerIdList= superUserManager.listOfCustomersForSuperUser(adminSuperUser.getId());
			
			boolean exResult = false;
			String message = null;
			for (Customer customer : customerIdList)
			{
				if(deviceId != null){
					String retVal = "";
					if(deviceId.equalsIgnoreCase("*")){
						//exResult  = deviceManager.updateDevicesArmStatusByCustomerId(customerId, status);
						CommandIssueServiceManager cism = new CommandIssueServiceManager();
						if(status.getName().equals(Constants.ARM))//AWAY
						{
							retVal = cism.activateModeInternal(customer.getCustomerId(),Constants.ARM);
							retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
							if(retVal.startsWith("SUCCESS")){
								exResult = true;
							}else{
								//sumit: Handle ARM_DEVICES_PROGRESS_FAIL
								message = retVal;
							}
						}
						else//HOME
						{
							retVal = cism.activateModeInternal(customer.getCustomerId(),Constants.DISARM);
							retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
							if(retVal.startsWith("SUCCESS")){
								exResult = true;
							}
						}
					}else if(deviceId.equalsIgnoreCase("*STAY")){ //STAY
						CommandIssueServiceManager cism = new CommandIssueServiceManager();
						retVal = cism.activateModeInternal(customer.getCustomerId(),Constants.STAY);
						retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
						if(retVal.startsWith("SUCCESS")){
							exResult = true;
						}else{
							//sumit: Handle STAY_DEVICES_PROGRESS_FAIL
							message = retVal;
						}
					}else{//obsolete code
							//vibhu start
							CommandIssueServiceManager cism = new CommandIssueServiceManager();
							exResult = cism.deviceArmDisarmStatusUpdateFromMID(customer.getCustomerId(), deviceId, status);
							//exResult = deviceManager.updateDeviceArmStatus(customerId, deviceId, status);
							//vibhu end
					}
				}
			}
			
			
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += exResult ? "success" : "failure";
			resultXml += "</status>";
			if(message != null){
				resultXml += "<message>";
				resultXml += message;
				resultXml += "</message>";
				
			}
			
		}
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	catch (Exception e) {
		LogUtil.info("", e);
		LogUtil.info("Error "+  e.getMessage());
		resultXml = "<imonitor><status>failure</status></imonitor>";
	}
	return resultXml;
}

public String disArmDevicesForMultipleUsers(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		Status status = IMonitorUtil.getStatuses().get(Constants.DISARM);
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);
		
		AdminSuperUser adminSuperUser=superUserManager.getsuperUserBySuperUserId(superUserId);
		boolean exResult = false;
		String message = null;
		
		XStream stream=new XStream();
		//LogUtil.info("List of customers : "+stream.toXML(list) );
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else
		{
			
			List<Customer> customerIdList= superUserManager.listOfCustomersForSuperUser(adminSuperUser.getId());
			for (Customer customer : customerIdList) 
			{
				if(deviceId != null){
					String retVal = "";
					if(deviceId.equalsIgnoreCase("*")){
						//exResult  = deviceManager.updateDevicesArmStatusByCustomerId(customerId, status);
						CommandIssueServiceManager cism = new CommandIssueServiceManager();
						if(status.getName().equals(Constants.ARM))//AWAY
						{
							retVal = cism.activateModeInternal(customer.getCustomerId(),Constants.ARM);
							retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
							if(retVal.startsWith("SUCCESS")){
								exResult = true;
							}else{
								//sumit: Handle ARM_DEVICES_PROGRESS_FAIL
								message = retVal;
							}
						}
						else//HOME
						{
							retVal = cism.activateModeInternal(customer.getCustomerId(),Constants.DISARM);
							retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
							if(retVal.startsWith("SUCCESS")){
								exResult = true;
							}
						}
					}else if(deviceId.equalsIgnoreCase("*STAY")){ //STAY
						CommandIssueServiceManager cism = new CommandIssueServiceManager();
						retVal = cism.activateModeInternal(customer.getCustomerId(),Constants.STAY);
						retVal = formatMessage(Constants.LOCALE_KEY_EN_US, retVal);
						if(retVal.startsWith("SUCCESS")){
							exResult = true;
						}else{
							//sumit: Handle STAY_DEVICES_PROGRESS_FAIL
							message = retVal;
						}
					}else{//obsolete code
							//vibhu start
							CommandIssueServiceManager cism = new CommandIssueServiceManager();
							exResult = cism.deviceArmDisarmStatusUpdateFromMID(customer.getCustomerId(), deviceId, status);
							//exResult = deviceManager.updateDeviceArmStatus(customerId, deviceId, status);
							//vibhu end
					}
				}
			}
			
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += exResult ? "success" : "failure";
			resultXml += "</status>";
			if(message != null){
				resultXml += "<message>";
				resultXml += message;
				resultXml += "</message>";
				
			}
			
		}
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	catch (Exception e) {
		LogUtil.info("", e);
		LogUtil.info("Error "+  e.getMessage());
		resultXml = "<imonitor><status>failure</status></imonitor>";
	}
	return resultXml;
}

public String saveDeviceDetailsForIOSPushNotification(String xml)
{
	
	String resultXml = "";
	XStream stream=new XStream();
	try {
		Document document = XmlUtil.getDocument(xml);
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		NodeList deviceTokenNodes = document.getElementsByTagName("devicetoken");
		Element deviceTokenNode = (Element) deviceTokenNodes.item(0);
		String deviceToken = XmlUtil.getCharacterDataFromElement(deviceTokenNode); 
	
		
		
		NodeList deviceTypeNodes = document.getElementsByTagName("devicetype");
		Element deviceTypeNode = (Element) deviceTypeNodes.item(0);
		String deviceType = XmlUtil.getCharacterDataFromElement(deviceTypeNode);
		
		NodeList appNameNodes = document.getElementsByTagName("appName");
		Element appNameNode = (Element) appNameNodes.item(0);
		String appName = XmlUtil.getCharacterDataFromElement(appNameNode);
		LogUtil.info("appName"+appName);
		
		
		if (deviceToken.equals("?") || deviceType.equals("?") ) 
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			LogUtil.info("DeviceToken/DeviceType is null");
			return resultXml;
		}
		
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByCustomerId(customerId);
		//LogUtil.info("Customer Object ---> "+stream.toXML(customer));
		
		DeviceManager deviceManager=new DeviceManager();

		DaoManager daoManager=new DaoManager();
		boolean res = deviceManager.verifyWhetherDevicePresentInDbForIOS(customer.getId(), deviceToken);
		
		if (res == false) 
		{
			PushNotification pushNotification=new PushNotification();
			pushNotification.setCustomer(customer);
			pushNotification.setDeviceToken(deviceToken);
			pushNotification.setDeviceType(deviceType);
			pushNotification.setAppName(appName);
			pushNotification.setImeiNumber(null);
			res = daoManager.save(pushNotification);
			
		}
		
			
		if (res) 
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success" ;
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure" ;
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}

	} 
	catch (ParserConfigurationException e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml  = "Couldn't parse the input";
	}
	catch (SAXException e) 
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml = "Couldn't parse the input";
	} 
	catch (IOException e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml = "Couldn't parse the input";
	}
	catch (Exception e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
	}
	
	return resultXml;
}

public String disablePushNotificationForIOS(String xml)
{
	
	String resultXml = "";
	XStream stream=new XStream();
	try {
		Document document = XmlUtil.getDocument(xml);
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		
		NodeList devicetokenNodes = document.getElementsByTagName("devicetoken");
		Element devicetokenNode = (Element) devicetokenNodes.item(0);
		String devicetoken = XmlUtil.getCharacterDataFromElement(devicetokenNode);
		
		CustomerManager customerManager=new CustomerManager();
		Customer customer=customerManager.getCustomerByCustomerId(customerId);
		//LogUtil.info("Customer Object ---> "+stream.toXML(customer));
		
		SuperUserManager superUserManager = new SuperUserManager();


		boolean res = superUserManager.disablePushNotificationForIOS(devicetoken);
		
		
		if (res) 
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success" ;
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure" ;
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}

	} 
	catch (ParserConfigurationException e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml  = "Couldn't parse the input";
	}
	catch (SAXException e) 
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml = "Couldn't parse the input";
	} 
	catch (IOException e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
		resultXml = "Couldn't parse the input";
	}
	catch (Exception e)
	{
		e.printStackTrace();
		LogUtil.info("Error : "+e.getMessage());
	}

	return resultXml;
}

public String disablePushNotificationForSuperUserOfIOS(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
	
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);
	
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		
		NodeList devicetokenNodes = document.getElementsByTagName("devicetoken");
		Element devicetokenNode = (Element) devicetokenNodes.item(0);
		String devicetoken = XmlUtil.getCharacterDataFromElement(devicetokenNode);
	
	
		boolean res = superUserManager.disablePushNotificationForIOS(devicetoken);
		
		
		if(res){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	return resultXml;
}

//RGB
public String sendRGBValue(String xml){
	LogUtil.info("Mid sendRGBValue start : " + xml);
	String resultXml = "";	
	boolean exResult=false;
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		NodeList hexValueNodes = document.getElementsByTagName("hexValue");
		Element hexValueNode = (Element) hexValueNodes.item(0);
		String hexValue = XmlUtil.getCharacterDataFromElement(hexValueNode);
		
		DeviceManager deviceManager = new DeviceManager();
		Device device =  deviceManager.retrieveDeviceDetails(deviceId, customerId);
		ActionModel actionModel = new ActionModel(device, hexValue);
		ImonitorControllerAction controllerAction = new RGBColorPickerAction();
		controllerAction.executeCommand(actionModel);
		exResult =IMonitorUtil.waitForResult(controllerAction);
		if (exResult)
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else 
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
	
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}	
	LogUtil.info("Mid sendRGBValue end : " + resultXml);
	return resultXml;

	}

public String sendRGBValueForSuperUser(String xml){

	String resultXml = "";
	LogUtil.info("sendRGBValueForSuperUser IN : " + xml);	
	boolean exResult=false;
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superUserNodes = document.getElementsByTagName("superUserId");
		Element superUserNode = (Element) superUserNodes.item(0);
		String superUserId = XmlUtil.getCharacterDataFromElement(superUserNode);
		LogUtil.info("superUserId :" + superUserId);
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		LogUtil.info("hashPassword :" + hashPassword);
		SuperUserManager superUserManager = new SuperUserManager();
		String uResult = superUserManager.isSuperUserExists(superUserId, hashPassword);

		XStream stream=new XStream();
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		NodeList deviceIdNodes = document.getElementsByTagName("deviceid");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		LogUtil.info("deviceId :" + deviceId);
		String[] splitString=deviceId.split("-");
		String macId1= splitString[0];
		
		
		LogUtil.info("macId1 :" + macId1);
		CustomerManager customerManager=new CustomerManager();
		LogUtil.info("11111111");
		Customer customer=customerManager.getCustomerByMacId(macId1);
		LogUtil.info("customer : " + customer.getCustomerId());
		NodeList hexValueNodes = document.getElementsByTagName("hexValue");
		Element hexValueNode = (Element) hexValueNodes.item(0);
		String hexValue = XmlUtil.getCharacterDataFromElement(hexValueNode);
		
		DeviceManager deviceManager = new DeviceManager();
		Device device =  deviceManager.retrieveDeviceDetails(deviceId, customer.getCustomerId());
		LogUtil.info("RGB Device : " + stream.toXML(device));
		ActionModel actionModel = new ActionModel(device, hexValue);
		ImonitorControllerAction controllerAction = new RGBColorPickerAction();
		controllerAction.executeCommand(actionModel);
		exResult =IMonitorUtil.waitForResult(controllerAction);
		if (exResult)
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		else 
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
	
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	LogUtil.info("sendRGBValueForSuperUser OUT : " + resultXml);	
	return resultXml;

	}

//Sync Gateway details with Mobile Icons start
public String synchronizegatewaydetailsLiIcon(String xml){
	//LogUtil.info("synchronizegatewaydetailsLiIcon : IN " + xml);
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		//LogUtil.info("customerId"+customerId);
		
		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		//LogUtil.info("userId"+userId);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		//LogUtil.info("password"+password);

		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		//LogUtil.info(" userManager result"+result);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
//		Now we have customer Id as token. Later we'll take the customer id from our session using this token.
		//Vibhu start
		DeviceManager deviceManager = new DeviceManager();
		List <Rule>rules = null;
		Device devWithRules = null;
		//Vibhu end
		CustomerManager customerManager = new CustomerManager();
		List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfCustomerByCustomerIdWithMobileIcons(customerId);
		//LogUtil.info(" gateWays ="+ new XStream().toXML(gateWays));
		int count = 0;
		resultXml  = "<imonitor>";
		if(gateWays.size() < 1){
			
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
		}else{
			
			resultXml += "<gateways>";
			for (GateWay gateWay : gateWays) {
				
				if(count == 0){
				resultXml += "<gateway>";
				resultXml += "<macid>";
				resultXml += gateWay.getMacId();
				resultXml += "</macid>";
				resultXml +="<version>";
				resultXml +=gateWay.getGateWayVersion();
				resultXml +="</version>";
				resultXml += "<localip>";
				resultXml += gateWay.getLocalIp();
				resultXml += "</localip>";
				resultXml += "<status>";
				resultXml += gateWay.getStatus().getName();
				resultXml += "</status>";
				resultXml += "<mode>";
				String mode=gateWay.getCurrentMode();
				if(mode.equals("0"))
					
				resultXml += "HOME";
				if(mode.equals("1"))
				resultXml += "STAY";
				if(mode.equals("2"))
				resultXml += "AWAY";
				resultXml += "</mode>";
				resultXml += "<AllonStatus>";
				resultXml += gateWay.getAllOnOffStatus();
				resultXml += "</AllonStatus>";
				resultXml += "<devices>";
			
				}
				Set<Device> devices = gateWay.getDevices();
				
				User userFromDB = userManager.getUserWithCustomerByUserIdAndUpdateStatus(userId, customerId, hashPassword);
				if(userFromDB.getRole().getName().equalsIgnoreCase(Constants.NORMAL_USER)){
					
					//LogUtil.info("NORMAL USER");
					
					List<subUserDeviceAccess> accessibleDevices = userManager.listDevicesForSubuser(userFromDB.getId());
					if((accessibleDevices != null) && (accessibleDevices.size() >0)){
					
						//LogUtil.info("DEVICES CONFIGURED FOR NORMAL USER");
						
						for (Device device : devices) {
							if(device.getFriendlyName().equals("Home") 
									|| device.getFriendlyName().equals("Stay") 
									|| device.getFriendlyName().equals("Away")
									|| device.getEnableList().equals("0"))
							{
								//LogUtil.info("Virtual Device");
								continue;
							}
							//sumit start: avoid listing of devices that are configured NOT to be listed by the user.	
							//LogUtil.info(new XStream().toXML(accessibleDevices));
							for(subUserDeviceAccess su : accessibleDevices){	
								
								//LogUtil.info("\n\n\nConf Device : "+su.getDevice().getId()+", Actual Device : "+device.getId());
								if(su.getDevice().getId() == device.getId()){
									
									//LogUtil.info("DEVICE MATCHED");
									
									resultXml += "<device>";
									resultXml += "<id>";
									resultXml += device.getGeneratedDeviceId();
									resultXml += "</id>";
									resultXml += "<deviceId>";
									resultXml += device.getDeviceId();
									resultXml += "</deviceId>";
									resultXml += "<name>";
									resultXml += device.getFriendlyName();
									resultXml += "</name>";
									//sumit begin
									String modelNumber = device.getModelNumber();
									resultXml += "<modelnumber>";
									if(modelNumber != null){
										resultXml += modelNumber;
									}
									resultXml += "</modelnumber>";
									resultXml += "<icon>";
									resultXml += device.getIcon().getFileName();
									resultXml += "</icon>";
									//sumit end
									resultXml += "<type>";
									resultXml += device.getDeviceType().getDetails();
									resultXml += "</type>";
									//Added for MID device name change feature
									resultXml += "<locationId>";
									resultXml += device.getLocation().getId();
									resultXml += "</locationId>";
									//Added for MID device name change feature end
									resultXml += "<location>";
									resultXml += device.getLocation().getName();
									resultXml += "</location>";
									resultXml += "<licon>";
									resultXml += device.getLocation().getMobileIconFile();
									resultXml += "</licon>";
									resultXml += "<status>";
									resultXml += device.getLastAlert().getAlertCommand();
									resultXml += "</status>";
									resultXml += "<levelstatus>";
									resultXml += device.getCommandParam();
									resultXml += "</levelstatus>";
									resultXml += "<batterylevel>";
									resultXml += device.getBatteryStatus();
									resultXml += "</batterylevel>";
									resultXml += "<armstatus>";
									//vibhu start
									devWithRules = deviceManager.getDeviceWithRulesByGeneratedDeviceId(device.getGeneratedDeviceId());
									rules = devWithRules.getRules();
									if(rules != null && rules.size() >0){
										resultXml += device.getArmStatus().getName();
									}else{
										resultXml += "Running";
									}
									//vibhu end
									resultXml += "</armstatus>";
									resultXml +="<enablestatus>";
									resultXml += device.getEnableStatus();
									resultXml +="</enablestatus>";
									
									String top =device.getLocationMap().getTop(); ;
									resultXml += "<top>";
									if(top != null && !top.equals(""))
										resultXml +=top;
									else
										resultXml +="-1";
									resultXml += "</top>";
									String left =device.getLocationMap().getLeftMap(); 
									resultXml += "<left>";
									if(left != null && !left.equals(""))
										resultXml +=left;
									else
										resultXml +="-1";
									
									resultXml += "</left>";
									//sumit start
									resultXml += "<devicePosition>";
									resultXml += device.getPosIdx();
									resultXml += "</devicePosition>";
									//sumit end
									if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER)){
										acConfiguration accofig=(acConfiguration) device.getDeviceConfiguration();
										if(accofig != null){
											resultXml +="<Config>";
											resultXml += "<FM>";   //FM- FanMode
											resultXml += accofig.getFanMode();
											resultXml += "</FM>";
											resultXml += "<MV>";   //ModeValue
											resultXml += accofig.getFanModeValue();
											resultXml += "</MV>";
											resultXml += "<Acswing>";   //ModeValue
											resultXml += accofig.getAcSwing();
											resultXml += "</Acswing>";
											resultXml += "<SensedTemperature>";   //SensedTemperature
											resultXml += accofig.getSensedTemperature();
											resultXml += "</SensedTemperature>";
											resultXml +="</Config>";
										}
									}
									if (device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
									{
										//LogUtil.info("VIA UNit config block");
										//DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
										//IndoorUnitConfiguration indoorUnitConfiguration=(IndoorUnitConfiguration) Dvconf.getDeviceConfigurationById(dcId);
										IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) device.getDeviceConfiguration();
										resultXml +="<Config>";
										resultXml +="<fanModeCapability>";
										resultXml +=configuration.getFanModeCapability();
										resultXml +="</fanModeCapability>";
										resultXml +="<coolModeCapability>";
										resultXml +=configuration.getCoolModeCapability();
										resultXml +="</coolModeCapability>";
										resultXml +="<heatModeCapability>";
										resultXml +=configuration.getHeatModeCapability();
										resultXml +="</heatModeCapability>";
										resultXml +="<autoModeCapability>";
										resultXml +=configuration.getAutoModeCapability();
										resultXml +="</autoModeCapability>";
										resultXml +="<dryModeCapability>";
										resultXml +=configuration.getDryModeCapability();
										resultXml +="</dryModeCapability>";
										resultXml +="<fanDirectionLevelCapability>";
										resultXml +=configuration.getFanDirectionLevelCapability();
										resultXml +="</fanDirectionLevelCapability>";
										resultXml +="<fanDirectionCapability>";
										resultXml +=configuration.getFanDirectionCapability();
										resultXml +="</fanDirectionCapability>";
										resultXml +="<fanVolumeLevelCapability>";
										resultXml +=configuration.getFanVolumeLevelCapability();
										resultXml +="</fanVolumeLevelCapability>";
										resultXml +="<fanVolumeCapability>";
										resultXml +=configuration.getFanVolumeCapability();
										resultXml +="</fanVolumeCapability>";
										resultXml +="<coolingUpperlimit>";
										resultXml +=configuration.getCoolingUpperlimit();
										resultXml +="</coolingUpperlimit>";
										resultXml +="<coolingLowerlimit>";
										resultXml +=configuration.getCoolingLowerlimit();
										resultXml +="</coolingLowerlimit>";
										resultXml +="<heatingUpperlimit>";
										resultXml +=configuration.getHeatingUpperlimit();
										resultXml +="</heatingUpperlimit>";
										resultXml +="<heatingLowerlimit>";
										resultXml +=configuration.getHeatingLowerlimit();
										resultXml +="</heatingLowerlimit>";
										resultXml +="<ConnectStatus>";
										resultXml +=configuration.getConnectStatus();
										resultXml +="</ConnectStatus>";
										resultXml +="<CommStatus>";
										resultXml +=configuration.getCommStatus();
										resultXml +="</CommStatus>";
										resultXml +="<fanModeValue>";
										resultXml +=configuration.getFanModeValue();
										resultXml +="</fanModeValue>";
										resultXml +="<sensedTemperature>";
										resultXml +=configuration.getSensedTemperature();
										resultXml +="</sensedTemperature>";
										resultXml +="<fanVolumeValue>";
										resultXml +=configuration.getFanVolumeValue();
										resultXml +="</fanVolumeValue>";
										resultXml +="<fanDirectionValue>";
										resultXml +=configuration.getFanDirectionValue();
										resultXml +="</fanDirectionValue>";
										resultXml +="<eMessage>";
										resultXml +=configuration.getErrorMessage();
										resultXml +="</eMessage>";
										resultXml +="<filterSignStatus>";
										resultXml +=configuration.getFilterSignStatus();
										resultXml +="</filterSignStatus>";
										resultXml +="<temperatureValue>";
										resultXml +=configuration.getTemperatureValue();
										resultXml +="</temperatureValue>";
										resultXml +="</Config>";
									}
									resultXml += "</device>";
								
								}	
							}
						}
					}
				}else{
					for (Device device : devices) {
						
						//sumit start: avoid listing of devices that are configured NOT to be listed by the user.	
						
						if(device.getFriendlyName().equals("Home") 
								|| device.getFriendlyName().equals("Stay") 
								|| device.getFriendlyName().equals("Away")
								|| device.getEnableList().equals("0"))
						{
							continue;
						}
						
						resultXml += "<device>";
						resultXml += "<id>";
						resultXml += device.getGeneratedDeviceId();
						resultXml += "</id>";
						resultXml += "<deviceId>";
						resultXml += device.getDeviceId();
						resultXml += "</deviceId>";
						resultXml += "<name>";
						resultXml += device.getFriendlyName();
						resultXml += "</name>";
						//sumit begin
						
						String modelNumber = device.getModelNumber();
						resultXml += "<modelnumber>";
						if(modelNumber != null){
							resultXml += modelNumber;
						}
						resultXml += "</modelnumber>";
						resultXml += "<icon>";
						resultXml += device.getIcon().getFileName();
						resultXml += "</icon>";
						
						//sumit end
						resultXml += "<type>";
						resultXml += device.getDeviceType().getDetails();
						resultXml += "</type>";
						
						//Added for MID device name change feature
						resultXml += "<locationId>";
						resultXml += device.getLocation().getId();
						resultXml += "</locationId>";
						//Added for MID device name change feature end
						resultXml += "<location>";
						
						resultXml += device.getLocation().getName();
						resultXml += "</location>";
						resultXml += "<licon>";
						resultXml += device.getLocation().getMobileIconFile();
						resultXml += "</licon>";
						resultXml += "<status>";
						
						resultXml += device.getLastAlert().getAlertCommand();
						resultXml += "</status>";
						resultXml += "<levelstatus>";
						resultXml += device.getCommandParam();
						resultXml += "</levelstatus>";
						resultXml += "<batterylevel>";
						resultXml += device.getBatteryStatus();
						resultXml += "</batterylevel>";
						resultXml += "<armstatus>";
						//vibhu start
						
						devWithRules = deviceManager.getDeviceWithRulesByGeneratedDeviceId(device.getGeneratedDeviceId());
						
						rules = devWithRules.getRules();
						
						if(rules != null && rules.size() >0)
						{
							resultXml += device.getArmStatus().getName();
						}
						else
						{
							resultXml += "Running";
						}
						//vibhu end
						resultXml += "</armstatus>";
						
						resultXml +="<enablestatus>";
						resultXml += device.getEnableStatus();
						resultXml +="</enablestatus>";
						
						String top =device.getLocationMap().getTop(); ;
						resultXml += "<top>";
						
						if(top != null && !top.equals(""))
							resultXml +=top;
						else
							resultXml +="-1";
						resultXml += "</top>";
						
						String left =device.getLocationMap().getLeftMap(); 
						resultXml += "<left>";
						if(left != null && !left.equals(""))
							resultXml +=left;
						else
							resultXml +="-1";
						
						resultXml += "</left>";
						//sumit start
						resultXml += "<devicePosition>";
						
						resultXml += device.getPosIdx();
						resultXml += "</devicePosition>";
						//sumit end
						
						if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
						{
							
							acConfiguration accofig=(acConfiguration) device.getDeviceConfiguration();
							if(accofig != null)
							{
								resultXml +="<Config>";
								resultXml += "<FM>";   //FM- FanMode
								resultXml += accofig.getFanMode();
								resultXml += "</FM>";
								resultXml += "<MV>";   //ModeValue
								resultXml += accofig.getFanModeValue();
								resultXml += "</MV>";
								resultXml += "<Acswing>";   //ModeValue
								resultXml += accofig.getAcSwing();
								resultXml += "</Acswing>";
								resultXml +="</Config>";
								
							}
							
						}
						
						if (device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
						{	
							//LogUtil.info("VIA UNit config block");
							//DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
							//IndoorUnitConfiguration indoorUnitConfiguration=(IndoorUnitConfiguration) Dvconf.getDeviceConfigurationById(dcId);
							IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) device.getDeviceConfiguration();
							resultXml +="<Config>";
							resultXml +="<fanModeCapability>";
							resultXml +=configuration.getFanModeCapability();
							resultXml +="</fanModeCapability>";
							resultXml +="<coolModeCapability>";
							resultXml +=configuration.getCoolModeCapability();
							resultXml +="</coolModeCapability>";
							resultXml +="<heatModeCapability>";
							resultXml +=configuration.getHeatModeCapability();
							resultXml +="</heatModeCapability>";
							resultXml +="<autoModeCapability>";
							resultXml +=configuration.getAutoModeCapability();
							resultXml +="</autoModeCapability>";
							resultXml +="<dryModeCapability>";
							resultXml +=configuration.getDryModeCapability();
							resultXml +="</dryModeCapability>";
							resultXml +="<fanDirectionLevelCapability>";
							resultXml +=configuration.getFanDirectionLevelCapability();
							resultXml +="</fanDirectionLevelCapability>";
							resultXml +="<fanDirectionCapability>";
							resultXml +=configuration.getFanDirectionCapability();
							resultXml +="</fanDirectionCapability>";
							resultXml +="<fanVolumeLevelCapability>";
							resultXml +=configuration.getFanVolumeLevelCapability();
							resultXml +="</fanVolumeLevelCapability>";
							resultXml +="<fanVolumeCapability>";
							resultXml +=configuration.getFanVolumeCapability();
							resultXml +="</fanVolumeCapability>";
							resultXml +="<coolingUpperlimit>";
							resultXml +=configuration.getCoolingUpperlimit();
							resultXml +="</coolingUpperlimit>";
							resultXml +="<coolingLowerlimit>";
							resultXml +=configuration.getCoolingLowerlimit();
							resultXml +="</coolingLowerlimit>";
							resultXml +="<heatingUpperlimit>";
							resultXml +=configuration.getHeatingUpperlimit();
							resultXml +="</heatingUpperlimit>";
							resultXml +="<heatingLowerlimit>";
							resultXml +=configuration.getHeatingLowerlimit();
							resultXml +="</heatingLowerlimit>";
							resultXml +="<ConnectStatus>";
							resultXml +=configuration.getConnectStatus();
							resultXml +="</ConnectStatus>";
							resultXml +="<CommStatus>";
							resultXml +=configuration.getCommStatus();
							resultXml +="</CommStatus>";
							resultXml +="<fanModeValue>";
							resultXml +=configuration.getFanModeValue();
							resultXml +="</fanModeValue>";
							resultXml +="<sensedTemperature>";
							resultXml +=configuration.getSensedTemperature();
							resultXml +="</sensedTemperature>";
							resultXml +="<fanVolumeValue>";
							resultXml +=configuration.getFanVolumeValue();
							resultXml +="</fanVolumeValue>";
							resultXml +="<fanDirectionValue>";
							resultXml +=configuration.getFanDirectionValue();
							resultXml +="</fanDirectionValue>";
						//	resultXml +="<eMessage>";
						//	resultXml +=configuration.getErrorMessage();
						//	resultXml +="</eMessage>";
							resultXml +="<filterSignStatus>";
							resultXml +=configuration.getFilterSignStatus();
							resultXml +="</filterSignStatus>";
							resultXml +="<temperatureValue>";
							resultXml +=configuration.getTemperatureValue();
							resultXml +="</temperatureValue>";
							resultXml +="</Config>";
							
						}
						resultXml += "</device>";
					}
				}
				
				count++;
			
				
				
				if((gateWays.size() > 1) && (count == 3)){
					
					resultXml += "</devices>";
					resultXml += "</gateway>";
				}else if(gateWays.size() == 1){
					
				resultXml += "</devices>";
				resultXml += "</gateway>";
				}
			}
			resultXml += "</gateways>";
		}
		resultXml += "</imonitor>";
		
		
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	} catch (Exception e) {
		LogUtil.info("Caught Exception : ", e);
	}
	
	return resultXml;
}

public String singleCustomerDeviceDetailsForSuperUserLiIcon(String xml){
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		DeviceManager deviceManager = new DeviceManager();
		List <Rule>rules = null;
		Device devWithRules = null;
		//Vibhu end
		CustomerManager customerManager = new CustomerManager();
		List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfCustomerByCustomerIdWithMobileIcons(customerId);
		int count = 0;
		resultXml  = "<imonitor>";
		if(gateWays.size() < 1){
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
		}else{
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "<gateways>";
			for (GateWay gateWay : gateWays) {
				if(count == 0){
				resultXml += "<gateway>";
				resultXml += "<macid>";
				resultXml += gateWay.getMacId();
				resultXml += "</macid>";
				resultXml +="<version>";
				resultXml +=gateWay.getGateWayVersion();
				resultXml +="</version>";
				resultXml += "<localip>";
				resultXml += gateWay.getLocalIp();
				resultXml += "</localip>";
				resultXml += "<status>";
				resultXml += gateWay.getStatus().getName();
				resultXml += "</status>";
				resultXml += "<mode>";
				String mode=gateWay.getCurrentMode();
				if(mode.equals("0"))
				resultXml += "HOME";
				if(mode.equals("1"))
				resultXml += "STAY";
				if(mode.equals("2"))
				resultXml += "AWAY";
				resultXml += "</mode>";
				/*resultXml += "<AllonStatus>";
				resultXml += gateWay.getAllOnOffStatus();
				resultXml += "</AllonStatus>";*/
				resultXml += "<customer>";
				resultXml += customerId;
				resultXml += "</customer>";
				
				
				
				resultXml += "<devices>";
				}
				Set<Device> devices = gateWay.getDevices();
					for (Device device : devices) {	
						if(device.getFriendlyName().equals("Home") 
								|| device.getFriendlyName().equals("Stay") 
								|| device.getFriendlyName().equals("Away")
								|| device.getEnableList().equals("0"))
						{
							continue;
						}

						resultXml += "<device>";
						resultXml += "<id>";
						resultXml += device.getGeneratedDeviceId();
						resultXml += "</id>";
						resultXml += "<deviceId>";
						resultXml += device.getDeviceId();
						resultXml += "</deviceId>";
						resultXml += "<name>";
						resultXml += device.getFriendlyName();
						resultXml += "</name>";
						//sumit begin
						String modelNumber = device.getModelNumber();
						resultXml += "<modelnumber>";
						if(modelNumber != null){
							resultXml += modelNumber;
						}
						resultXml += "</modelnumber>";
						resultXml += "<icon>";
						resultXml += device.getIcon().getFileName();
						resultXml += "</icon>";
						//sumit end
						resultXml += "<type>";
						resultXml += device.getDeviceType().getDetails();
						resultXml += "</type>";
						//Added for MID device name change feature
						resultXml += "<locationId>";
						resultXml += device.getLocation().getId();
						resultXml += "</locationId>";
						//Added for MID device name change feature end
						resultXml += "<location>";
						resultXml += device.getLocation().getName();
						resultXml += "</location>";
						resultXml += "<licon>";
						resultXml += device.getLocation().getMobileIconFile();
						resultXml += "</licon>";
						resultXml += "<status>";
						resultXml += device.getLastAlert().getAlertCommand();
						resultXml += "</status>";
						resultXml += "<levelstatus>";
						resultXml += device.getCommandParam();
						resultXml += "</levelstatus>";
						resultXml += "<batterylevel>";
						resultXml += device.getBatteryStatus();
						resultXml += "</batterylevel>";
						resultXml += "<armstatus>";
						//vibhu start
						devWithRules = deviceManager.getDeviceWithRulesByGeneratedDeviceId(device.getGeneratedDeviceId());
						rules = devWithRules.getRules();
						if(rules != null && rules.size() >0)
						{
							resultXml += device.getArmStatus().getName();
						}
						else
						{
							resultXml += "Running";
						}
						//vibhu end
						resultXml += "</armstatus>";
						resultXml +="<enablestatus>";
						resultXml += device.getEnableStatus();
						resultXml +="</enablestatus>";
						
						String top =device.getLocationMap().getTop(); ;
						resultXml += "<top>";
						if(top != null && !top.equals(""))
							resultXml +=top;
						else
							resultXml +="-1";
						resultXml += "</top>";
						String left =device.getLocationMap().getLeftMap(); 
						resultXml += "<left>";
						if(left != null && !left.equals(""))
							resultXml +=left;
						else
							resultXml +="-1";
						
						resultXml += "</left>";
						//sumit start
						resultXml += "<devicePosition>";
						resultXml += device.getPosIdx();
						resultXml += "</devicePosition>";
						//sumit end
						
						if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
						{
							acConfiguration accofig=(acConfiguration) device.getDeviceConfiguration();
							if(accofig != null)
							{
								resultXml +="<Config>";
								resultXml += "<FM>";   //FM- FanMode
								resultXml += accofig.getFanMode();
								resultXml += "</FM>";
								resultXml += "<MV>";   //ModeValue
								resultXml += accofig.getFanModeValue();
								resultXml += "</MV>";
								resultXml += "<Acswing>";   //ModeValue
								resultXml += accofig.getAcSwing();
								resultXml += "</Acswing>";
								resultXml +="</Config>";
							}
							
						}
						
						if (device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
						{
							//LogUtil.info("VIA UNit config block");
							//DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
							//IndoorUnitConfiguration indoorUnitConfiguration=(IndoorUnitConfiguration) Dvconf.getDeviceConfigurationById(dcId);
							IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) device.getDeviceConfiguration();
							resultXml +="<Config>";
							resultXml +="<fanModeCapability>";
							resultXml +=configuration.getFanModeCapability();
							resultXml +="</fanModeCapability>";
							resultXml +="<coolModeCapability>";
							resultXml +=configuration.getCoolModeCapability();
							resultXml +="</coolModeCapability>";
							resultXml +="<heatModeCapability>";
							resultXml +=configuration.getHeatModeCapability();
							resultXml +="</heatModeCapability>";
							resultXml +="<autoModeCapability>";
							resultXml +=configuration.getAutoModeCapability();
							resultXml +="</autoModeCapability>";
							resultXml +="<dryModeCapability>";
							resultXml +=configuration.getDryModeCapability();
							resultXml +="</dryModeCapability>";
							resultXml +="<fanDirectionLevelCapability>";
							resultXml +=configuration.getFanDirectionLevelCapability();
							resultXml +="</fanDirectionLevelCapability>";
							resultXml +="<fanDirectionCapability>";
							resultXml +=configuration.getFanDirectionCapability();
							resultXml +="</fanDirectionCapability>";
							resultXml +="<fanVolumeLevelCapability>";
							resultXml +=configuration.getFanVolumeLevelCapability();
							resultXml +="</fanVolumeLevelCapability>";
							resultXml +="<fanVolumeCapability>";
							resultXml +=configuration.getFanVolumeCapability();
							resultXml +="</fanVolumeCapability>";
							resultXml +="<coolingUpperlimit>";
							resultXml +=configuration.getCoolingUpperlimit();
							resultXml +="</coolingUpperlimit>";
							resultXml +="<coolingLowerlimit>";
							resultXml +=configuration.getCoolingLowerlimit();
							resultXml +="</coolingLowerlimit>";
							resultXml +="<heatingUpperlimit>";
							resultXml +=configuration.getHeatingUpperlimit();
							resultXml +="</heatingUpperlimit>";
							resultXml +="<heatingLowerlimit>";
							resultXml +=configuration.getHeatingLowerlimit();
							resultXml +="</heatingLowerlimit>";
							resultXml +="<ConnectStatus>";
							resultXml +=configuration.getConnectStatus();
							resultXml +="</ConnectStatus>";
							resultXml +="<CommStatus>";
							resultXml +=configuration.getCommStatus();
							resultXml +="</CommStatus>";
							resultXml +="<fanModeValue>";
							resultXml +=configuration.getFanModeValue();
							resultXml +="</fanModeValue>";
							resultXml +="<sensedTemperature>";
							resultXml +=configuration.getSensedTemperature();
							resultXml +="</sensedTemperature>";
							resultXml +="<fanVolumeValue>";
							resultXml +=configuration.getFanVolumeValue();
							resultXml +="</fanVolumeValue>";
							resultXml +="<fanDirectionValue>";
							resultXml +=configuration.getFanDirectionValue();
							resultXml +="</fanDirectionValue>";
						//	resultXml +="<eMessage>";
						//	resultXml +=configuration.getErrorMessage();
						//	resultXml +="</eMessage>";
							resultXml +="<filterSignStatus>";
							resultXml +=configuration.getFilterSignStatus();
							resultXml +="</filterSignStatus>";
							resultXml +="<temperatureValue>";
							resultXml +=configuration.getTemperatureValue();
							resultXml +="</temperatureValue>";
							resultXml +="</Config>";
						}
						resultXml += "</device>";
					}
		
				count++;
			
				if((gateWays.size() > 1) && (count == 3)){
					resultXml += "</devices>";
					resultXml += "</gateway>";
				}else if(gateWays.size() == 1){
				resultXml += "</devices>";
				resultXml += "</gateway>";
				}
			}
			resultXml += "</gateways>";
		}
		resultXml += "</imonitor>";
		
		
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	} catch (Exception e) {
		LogUtil.info("Caught Exception : ", e);
	}
	return resultXml;
}

public String multipleCustomerDeviceDetailsForSuperUserLiIcon(String xml){
	String resultXml = "";
	
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList superuserNodes = document.getElementsByTagName("superUserId");
		Element superuserNode = (Element) superuserNodes.item(0);
		String superuser = XmlUtil.getCharacterDataFromElement(superuserNode);
		DeviceManager deviceManager = new DeviceManager();
		List <Rule>rules = null;
		Device devWithRules = null;
		CustomerManager customerManager = new CustomerManager();
		//String superuser="imszing";
		SuperUserManager superUserManager=new SuperUserManager();
		AdminSuperUser adminSuperUser=superUserManager.getsuperUserBySuperUserId(superuser);
		//LogUtil.info("SuperUSer id : "+ adminSuperUser.getId());
		XStream stream=new XStream();
		List<Customer> customerIdList= superUserManager.listOfCustomersForSuperUser(adminSuperUser.getId());
		//LogUtil.info("Customers list : "+ stream.toXML(customerIdList));
		resultXml  += "<imonitor>";
		resultXml += "<gateways>";
		for (Customer cust : customerIdList)
		{
			List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfCustomerByCustomerIdWithMobileIcons(cust.getCustomerId());
			int count = 0;
			
			if(gateWays.size() < 1){
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
			}else{
				for (GateWay gateWay : gateWays) {
					if(count == 0){
					resultXml += "<gateway>";
					resultXml += "<macid>";
					resultXml += gateWay.getMacId();
					resultXml += "</macid>";
					resultXml +="<version>";
					resultXml +=gateWay.getGateWayVersion();
					resultXml +="</version>";
					resultXml += "<localip>";
					resultXml += gateWay.getLocalIp();
					resultXml += "</localip>";
					resultXml += "<status>";
					resultXml += gateWay.getStatus().getName();
					resultXml += "</status>";
					
					resultXml += "<customer>";
					resultXml += "<name>";
					resultXml += cust.getCustomerId();
					resultXml += "</name>";
					resultXml += "<mode>";
					String mode=gateWay.getCurrentMode();
					if(mode.equals("0"))
					resultXml += "HOME";
					if(mode.equals("1"))
					resultXml += "STAY";
					if(mode.equals("2"))
					resultXml += "AWAY";
					resultXml += "</mode>";
					resultXml += "</customer>";
					
					resultXml += "<devices>";
					}
					Set<Device> devices = gateWay.getDevices();
						for (Device device : devices) {	
							if(device.getFriendlyName().equals("Home") 
									|| device.getFriendlyName().equals("Stay") 
									|| device.getFriendlyName().equals("Away")
									|| device.getEnableList().equals("0"))
							{
								continue;
							}

							resultXml += "<device>";
							resultXml += "<id>";
							resultXml += device.getGeneratedDeviceId();
							resultXml += "</id>";
							resultXml += "<deviceId>";
							resultXml += device.getDeviceId();
							resultXml += "</deviceId>";
							resultXml += "<name>";
							resultXml += device.getFriendlyName();
							resultXml += "</name>";
							String modelNumber = device.getModelNumber();
							resultXml += "<modelnumber>";
							if(modelNumber != null){
								resultXml += modelNumber;
							}
							resultXml += "</modelnumber>";
							resultXml += "<icon>";
							resultXml += device.getIcon().getFileName();
							resultXml += "</icon>";
							resultXml += "<type>";
							resultXml += device.getDeviceType().getDetails();
							resultXml += "</type>";
							//Added for MID device name change feature
							resultXml += "<locationId>";
							resultXml += device.getLocation().getId();
							resultXml += "</locationId>";
							//Added for MID device name change feature end
							resultXml += "<location>";
							resultXml += device.getLocation().getName();
							resultXml += "</location>";
							resultXml += "<licon>";
							resultXml += device.getLocation().getMobileIconFile();
							resultXml += "</licon>";
							resultXml += "<status>";
							resultXml += device.getLastAlert().getAlertCommand();
							resultXml += "</status>";
							resultXml += "<levelstatus>";
							resultXml += device.getCommandParam();
							resultXml += "</levelstatus>";
							resultXml += "<batterylevel>";
							resultXml += device.getBatteryStatus();
							resultXml += "</batterylevel>";
							resultXml += "<armstatus>";
							devWithRules = deviceManager.getDeviceWithRulesByGeneratedDeviceId(device.getGeneratedDeviceId());
							rules = devWithRules.getRules();
							if(rules != null && rules.size() >0)
							{
								resultXml += device.getArmStatus().getName();
							}
							else
							{
								resultXml += "Running";
							}
							resultXml += "</armstatus>";
							resultXml +="<enablestatus>";
							resultXml += device.getEnableStatus();
							resultXml +="</enablestatus>";
							
							String top =device.getLocationMap().getTop(); ;
							resultXml += "<top>";
							if(top != null && !top.equals(""))
								resultXml +=top;
							else
								resultXml +="-1";
							resultXml += "</top>";
							String left =device.getLocationMap().getLeftMap(); 
							resultXml += "<left>";
							if(left != null && !left.equals(""))
								resultXml +=left;
							else
								resultXml +="-1";
							
							resultXml += "</left>";
							resultXml += "<devicePosition>";
							resultXml += device.getPosIdx();
							resultXml += "</devicePosition>";
							
							if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
							{
								acConfiguration accofig=(acConfiguration) device.getDeviceConfiguration();
								if(accofig != null)
								{
									resultXml +="<Config>";
									resultXml += "<FM>";   //FM- FanMode
									resultXml += accofig.getFanMode();
									resultXml += "</FM>";
									resultXml += "<MV>";   //ModeValue
									resultXml += accofig.getFanModeValue();
									resultXml += "</MV>";
									resultXml += "<Acswing>";   //ModeValue
									resultXml += accofig.getAcSwing();
									resultXml += "</Acswing>";
									resultXml +="</Config>";
								}
								
							}
							
							if (device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
							{
								IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) device.getDeviceConfiguration();
								resultXml +="<Config>";
								resultXml +="<fanModeCapability>";
								resultXml +=configuration.getFanModeCapability();
								resultXml +="</fanModeCapability>";
								resultXml +="<coolModeCapability>";
								resultXml +=configuration.getCoolModeCapability();
								resultXml +="</coolModeCapability>";
								resultXml +="<heatModeCapability>";
								resultXml +=configuration.getHeatModeCapability();
								resultXml +="</heatModeCapability>";
								resultXml +="<autoModeCapability>";
								resultXml +=configuration.getAutoModeCapability();
								resultXml +="</autoModeCapability>";
								resultXml +="<dryModeCapability>";
								resultXml +=configuration.getDryModeCapability();
								resultXml +="</dryModeCapability>";
								resultXml +="<fanDirectionLevelCapability>";
								resultXml +=configuration.getFanDirectionLevelCapability();
								resultXml +="</fanDirectionLevelCapability>";
								resultXml +="<fanDirectionCapability>";
								resultXml +=configuration.getFanDirectionCapability();
								resultXml +="</fanDirectionCapability>";
								resultXml +="<fanVolumeLevelCapability>";
								resultXml +=configuration.getFanVolumeLevelCapability();
								resultXml +="</fanVolumeLevelCapability>";
								resultXml +="<fanVolumeCapability>";
								resultXml +=configuration.getFanVolumeCapability();
								resultXml +="</fanVolumeCapability>";
								resultXml +="<coolingUpperlimit>";
								resultXml +=configuration.getCoolingUpperlimit();
								resultXml +="</coolingUpperlimit>";
								resultXml +="<coolingLowerlimit>";
								resultXml +=configuration.getCoolingLowerlimit();
								resultXml +="</coolingLowerlimit>";
								resultXml +="<heatingUpperlimit>";
								resultXml +=configuration.getHeatingUpperlimit();
								resultXml +="</heatingUpperlimit>";
								resultXml +="<heatingLowerlimit>";
								resultXml +=configuration.getHeatingLowerlimit();
								resultXml +="</heatingLowerlimit>";
								resultXml +="<ConnectStatus>";
								resultXml +=configuration.getConnectStatus();
								resultXml +="</ConnectStatus>";
								resultXml +="<CommStatus>";
								resultXml +=configuration.getCommStatus();
								resultXml +="</CommStatus>";
								resultXml +="<fanModeValue>";
								resultXml +=configuration.getFanModeValue();
								resultXml +="</fanModeValue>";
								resultXml +="<sensedTemperature>";
								resultXml +=configuration.getSensedTemperature();
								resultXml +="</sensedTemperature>";
								resultXml +="<fanVolumeValue>";
								resultXml +=configuration.getFanVolumeValue();
								resultXml +="</fanVolumeValue>";
								resultXml +="<fanDirectionValue>";
								resultXml +=configuration.getFanDirectionValue();
								resultXml +="</fanDirectionValue>";
							//	resultXml +="<eMessage>";
							//	resultXml +=configuration.getErrorMessage();
							//	resultXml +="</eMessage>";
								resultXml +="<filterSignStatus>";
								resultXml +=configuration.getFilterSignStatus();
								resultXml +="</filterSignStatus>";
								resultXml +="<temperatureValue>";
								resultXml +=configuration.getTemperatureValue();
								resultXml +="</temperatureValue>";
								resultXml +="</Config>";
							}
							resultXml += "</device>";
						}
			
					count++;
					if((gateWays.size() > 1) && (count == 3)){
						resultXml += "</devices>";
						resultXml += "</gateway>";
					}else if(gateWays.size() == 1){
					resultXml += "</devices>";
					resultXml += "</gateway>";
					}
				}
			}
		}
		resultXml += "</gateways>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml += "</imonitor>";
		
		
	}
	 catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	 catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("Error : "+ e.getMessage());
		}
	//LogUtil.info("response : "+resultXml );
	return resultXml;
}

//Sync Gateway details with Mobile Icons end



//Self Registration Mid

public String zingMacIdValidationForMid(String xml){
	String resultXml = "";
	LogUtil.info("zingMacIdValidationForMid: IN "+ xml);
	if (xml==null)
	{
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += "Failure the request entity is null";
		resultXml += "</status>";
		resultXml += "</imonitor>";
		LogUtil.info("macId Error : " + resultXml);
		return resultXml;
	}
	
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList macIdNodes = document.getElementsByTagName("macId");
		Element macIdNode = (Element) macIdNodes.item(0);
		String macId = XmlUtil.getCharacterDataFromElement(macIdNode);

		//Verify gateway macId in the saved list of CMS
        GatewayManager gatewayManager=new GatewayManager();
		boolean macIdExist = gatewayManager.verifyGetwayMacIdFromSavedList(macId);
		LogUtil.info("boolean result: " + macIdExist);
		if(!macIdExist) {
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "Failure The following macId "+macId+ " is invalid.";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			LogUtil.info("macId Error : " + resultXml);
			return resultXml;
		}
		
		GateWay gateWay1 = gatewayManager.getGateWayByMacId(macId);
		if (gateWay1 != null)
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "Failure The following macId "+macId+ " is already registered.";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			LogUtil.info("macId already registered Error : " + resultXml);
			return resultXml;
		}
		
		
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	resultXml = "<imonitor>";
	resultXml += "<status>";
	resultXml += "Success";
	resultXml += "</status>";
	resultXml += "</imonitor>";
	LogUtil.info("zingMacIdValidationForMid OUT: " + resultXml);
	return resultXml;

	}

public String customerIdValidationForMid(String xml){

	LogUtil.info("customerIdValidationForMid: IN "+ xml);
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
				//Verify Customer Name in DB
		NodeList customerNodes = document.getElementsByTagName("customerId");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		CustomerManager customerManager=new CustomerManager();
		boolean customerCheck = customerManager.checkCustomerExistence(customerId);
		LogUtil.info("customerCheck result: " + customerCheck);
		if (customerCheck)
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "Failure The following customerId " +customerId+ " already Exists";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			LogUtil.info("customerCheck error : " + resultXml);
			return resultXml;
		}
			
		
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	resultXml = "<imonitor>";
	resultXml += "<status>";
	resultXml += "Success";
	resultXml += "</status>";
	resultXml += "</imonitor>";
	LogUtil.info("customerIdValidationForMid OUT: " + resultXml);
	return resultXml;

	}


public String saveZingGatewayForMid(String xml){

	LogUtil.info("saveZingGatewayForMid: IN "+ xml);
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList macIdNodes = document.getElementsByTagName("macId");
		Element macIdNode = (Element) macIdNodes.item(0);
		String macId = XmlUtil.getCharacterDataFromElement(macIdNode);

				//Verify Customer Name in DB
		NodeList customerNodes = document.getElementsByTagName("customerId");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		//Verify User Name in DB
		NodeList userNodes = document.getElementsByTagName("userId");
		Element userNode = (Element) userNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(userNode);
		
		//Verify gateway macId in the saved list of CMS
        /*GatewayManager gatewayManager=new GatewayManager();
		boolean macIdExist = gatewayManager.verifyGetwayMacIdFromSavedList(macId);
		LogUtil.info("boolean result: " + macIdExist);
		if(!macIdExist) {
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml = "Failure The following macId "+macId+ " is invalid.";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			LogUtil.info("macId Error : " + resultXml);
			return resultXml;
		}
		
		UserManager userManager = new UserManager();
		boolean userCheck = userManager.checkUserExistence(userId);
		LogUtil.info("userCheck result: " + userCheck);
		if (!userCheck)
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "Failure The following userId " +userId+ " already Exists";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			LogUtil.info("User check error : " + resultXml);
			return resultXml;
		}
		
		
		CustomerManager customerManager=new CustomerManager();
		boolean customerCheck = customerManager.checkCustomerExistence(customerId);
		LogUtil.info("customerCheck result: " + customerCheck);
		if (customerCheck)
		{
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "Failure The following customerId " +customerId+ " already Exists";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			LogUtil.info("customerCheck error : " + resultXml);
			return resultXml;
		}*/
		
		
		
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		
		NodeList firstNameNodes = document.getElementsByTagName("firstName");
		Element firstNameNode = (Element) firstNameNodes.item(0);
		String firstName = XmlUtil.getCharacterDataFromElement(firstNameNode);
		
		/*NodeList middleNameNodes = document.getElementsByTagName("middleName");
		Element middleNameNode = (Element) middleNameNodes.item(0);
		String middleName = XmlUtil.getCharacterDataFromElement(middleNameNode);*/
		
		NodeList lastNameNodes = document.getElementsByTagName("lastName");
		Element lastNameNode = (Element) lastNameNodes.item(0);
		String lastName = XmlUtil.getCharacterDataFromElement(lastNameNode);
		
		NodeList addressNodes = document.getElementsByTagName("address");
		Element addressNode = (Element) addressNodes.item(0);
		String address = XmlUtil.getCharacterDataFromElement(addressNode);
		
		/*NodeList postNodes = document.getElementsByTagName("post");
		Element postNode = (Element) postNodes.item(0);
		String post = XmlUtil.getCharacterDataFromElement(postNode);*/
		
		NodeList cityNodes = document.getElementsByTagName("city");
		Element cityNode = (Element) cityNodes.item(0);
		String city = XmlUtil.getCharacterDataFromElement(cityNode);
		
		NodeList stateNodes = document.getElementsByTagName("state");
		Element stateNode = (Element) stateNodes.item(0);
		String state = XmlUtil.getCharacterDataFromElement(stateNode);
		
		/*NodeList pinCodeNodes = document.getElementsByTagName("pinCode");
		Element pinCodeNode = (Element) pinCodeNodes.item(0);
		String pinCode = XmlUtil.getCharacterDataFromElement(pinCodeNode);*/
		
		NodeList emailNodes = document.getElementsByTagName("email");
		Element emailNode = (Element) emailNodes.item(0);
		String email = XmlUtil.getCharacterDataFromElement(emailNode);
		NodeList mobileNumberNodes = document.getElementsByTagName("mobileNumber");
		Element mobileNumberNode = (Element) mobileNumberNodes.item(0);
		String mobileNumber = XmlUtil.getCharacterDataFromElement(mobileNumberNode);
		
		NodeList otpNodes = document.getElementsByTagName("otp");
		Element otpNode = (Element) otpNodes.item(0);
		String otp = XmlUtil.getCharacterDataFromElement(otpNode);
		
		Customer customer =new Customer();
		GateWay gateway = new GateWay();
		User user = new User();
		CustomerServiceManager customerServiceManager = new CustomerServiceManager();
		XStream stream = new XStream();		
		gateway.setMacId(macId);
		customer.setCustomerId(customerId);
		customer.setFirstName(firstName);
		//customer.setMiddleName(middleName);
		customer.setLastName(lastName);
		customer.setPlace(address);
		//customer.setPost(post);
		customer.setCity(city);
		customer.setProvince(state);
		customer.setEmail(email);
		customer.setMobile(mobileNumber);
		//customer.setPincode(pinCode);
		user.setUserId(userId);
		
		String hashPassword = IMonitorUtil.getHashPassword(password);
		user.setPassword(hashPassword);
		
		
		String gatewayXml = stream.toXML(gateway);
		String customerXml = stream.toXML(customer);
		String userXml = stream.toXML(user);
		
		CustomerManager customerManager = new CustomerManager();
		OTPForSelfRegistration fromDb = customerManager.getOTPObjectByMacId(macId);
		LogUtil.info("fromDb Object" + new XStream().toXML(fromDb));
		
		if (fromDb!=null)
		{
			if (!fromDb.getOtp().equals(otp))
			{
				resultXml  = "<imonitor>";
				resultXml  += "<status>";
				resultXml += "OTP is invalid , Registration Failed";
				resultXml  += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
		}
		
		String resultAfterSving = customerServiceManager.saveCustomerForZing(customerXml, gatewayXml, userXml);
		LogUtil.info("resultAfterSving customer " + resultAfterSving);
		resultXml  = "<imonitor>";
		resultXml  += "<status>";
		if (resultAfterSving.equals("failure")) 
		{
			resultXml += "Failure The following customerId " +customer.getCustomerId()+ " already Exists";
		}
		else if (resultAfterSving.equals("GatewayFailure"))
		{
			resultXml += "Failure The following macId "+gateway.getMacId()+ " is invalid.";
		}
		else if (resultAfterSving.equals("GatewayExists"))
		{
			resultXml += "Failure The following macId "+gateway.getMacId()+ " already Exists.";
		}
		else
		{
			//resultXml += "Success";
			resultXml += "Registration Successful";
		}
		resultXml  += "</status>";
		resultXml += "</imonitor>";
		
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	LogUtil.info("saveZingGatewayForMid OUT: " + resultXml);
	return resultXml;

	}


public String sendOTPForSelfRegistration(String xml){

	//Fetch object based on macID and verify
	
	
	LogUtil.info("sendOTPForSelfRegistration: IN "+ xml);
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList macIdNodes = document.getElementsByTagName("macId");
		Element macIdNode = (Element) macIdNodes.item(0);
		String macId = XmlUtil.getCharacterDataFromElement(macIdNode);
		LogUtil.info("macId:"+macId);
		
		NodeList mobileNumberNodes = document.getElementsByTagName("phoneNumber");
		Element mobileNumberNode = (Element) mobileNumberNodes.item(0);
		String phoneNumber = XmlUtil.getCharacterDataFromElement(mobileNumberNode);
		LogUtil.info("phoneNumber:"+phoneNumber);
		
		SystemNotificaionServiceManager notificaionServiceManager = new SystemNotificaionServiceManager();
		SmsNotifications smsNotifications = new SmsNotifications();
		int otp = notificaionServiceManager.generateOtpNumberSixDigits();
		LogUtil.info("otp 6 digits: "  + otp);
		String otpString = Integer.toString(otp);
		String message = "OTP to enroll for alert SMS from Myhomeqi.com"+ Constants.NEW_LINE + 
		         "OTP Number: "+ otp;
		smsNotifications.notifywithOTPForSelfRegistration(message, phoneNumber);
		
		
		//Fetch object based on macId
		CustomerManager customerManager = new CustomerManager();
		DaoManager daoManager = new DaoManager();
		OTPForSelfRegistration fromDb = customerManager.getOTPObjectByMacId(macId);
		
		LogUtil.info("fromDb Object" + new XStream().toXML(fromDb));
		Date date =new Date();
		boolean saveResult = false;
		if (fromDb!=null) 
		{
			fromDb.setPhoneNumber(phoneNumber);
			fromDb.setOtp(otpString);
			fromDb.setDate(date);
			saveResult = daoManager.update(fromDb);
			LogUtil.info("updateResult : "  + saveResult);
		}
		else
		{
			OTPForSelfRegistration otpForSelfRegistration = new OTPForSelfRegistration();
			otpForSelfRegistration.setMacId(macId);
			otpForSelfRegistration.setPhoneNumber(phoneNumber);
			otpForSelfRegistration.setOtp(otpString);
			otpForSelfRegistration.setDate(date);
			saveResult = daoManager.save(otpForSelfRegistration);
			LogUtil.info("saveResult : "  + saveResult);
		}
		
		
	
		resultXml = "<imonitor>";
		resultXml += "<status>";
		if (saveResult)
		{
			resultXml += "Success";
		}
		else {
			resultXml += "Failure";
		}
		resultXml += "</status>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	
	LogUtil.info("sendOTPForSelfRegistration OUT: " + resultXml);
	return resultXml;

	}

public String editDeviceNameMID(String xml){

	//Fetch object based on macID and verify
	
	
	LogUtil.info("editDeviceNameMID: IN "+ xml);
	String resultXml = "Success";
	boolean updateResult = false;
	Location location = null;
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		NodeList deviceIdNodes = document.getElementsByTagName("generatedDeviceId");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		NodeList friendlyNameNodes = document.getElementsByTagName("friendlyName");
		Element friendlyNameNode = (Element) friendlyNameNodes.item(0);
		String friendlyName = XmlUtil.getCharacterDataFromElement(friendlyNameNode);
		
		NodeList oldLocationIdNodes = document.getElementsByTagName("oldLocationId");
		Element oldLocationIdNode = (Element) oldLocationIdNodes.item(0);
		String oldLocationId = XmlUtil.getCharacterDataFromElement(oldLocationIdNode);
		
		NodeList newLocationIdNodes = document.getElementsByTagName("newLocationId");
		Element newLocationIdNode = (Element) newLocationIdNodes.item(0);
		String newLocationId = XmlUtil.getCharacterDataFromElement(newLocationIdNode);
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		XStream stream = new XStream();
		LogUtil.info("generatedDeviceId : " + deviceId);
		DeviceManager deviceManager = new DeviceManager();
		Device devicefromDb = deviceManager.getDevice(deviceId);
		devicefromDb.setFriendlyName(friendlyName);
		
		LogUtil.info("New Location Id : " + newLocationId);
		LocationManager locationManager = new LocationManager();
		if (!newLocationId.equals("?"))
		{
			Long lid = Long.parseLong(newLocationId);
			LogUtil.info("lid : " + lid);
			location = locationManager.getLocationById(lid);
			devicefromDb.setLocation(location);
		}
		
		
		String devicexml = stream.toXML(devicefromDb);
		String oldLocIdXml = stream.toXML(oldLocationId);
		DeviceServiceManager deviceServiceManager= new DeviceServiceManager();
		String resultXm = deviceServiceManager.updateFriendlyNameAndLocation(devicexml, oldLocIdXml);
		String res = (String) stream.fromXML(resultXm);
		
		if(res.equals("DB_SUCCESS")) 
		{
			updateResult = true;
		}
		
		LogUtil.info("updateResult : " + updateResult);
		resultXml = "<imonitor>";
		resultXml += "<status>";
		if (updateResult)
		{
			resultXml += "Success";
		}
		else {
			resultXml += "Failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		resultXml += "<location>";
		resultXml += "<id>";
		resultXml += location.getId();
		resultXml += "</id>";
		resultXml += "<name>";
		resultXml += location.getName();
		resultXml += "</name>";
		resultXml += "</location>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	
	LogUtil.info("editDeviceNameMID OUT: " + resultXml);
	return resultXml;

	}

//Add new room from MID
public String addNewRoomMID(String xml){
LogUtil.info("addNewRoomMID IN:"+xml);
	//Fetch object based on macID and verify
	
	
	
	String resultXml = "Success";
	boolean saveResult = false;
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);

		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);

		NodeList locationNameNodes = document.getElementsByTagName("locationName");
		Element locationNameNode = (Element) locationNameNodes.item(0);
		String locationName = XmlUtil.getCharacterDataFromElement(locationNameNode);
		
		NodeList locationDetailsNodes = document.getElementsByTagName("locationDetails");
		Element locationDetailsNode = (Element) locationDetailsNodes.item(0);
		String locationDetails = XmlUtil.getCharacterDataFromElement(locationDetailsNode);
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		//LogUtil.info(" usercheckresult " + result);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		XStream stream = new XStream();

		CustomerManager customerManager = new CustomerManager();
		Customer customer = customerManager.getCustomerByCustomerId(customerId);
	
		Location location = new Location();
		location.setName(locationName);
		location.setDetails(locationDetails);
		location.setCustomer(customer);
		location.setIconFile("/imonitor/resources/images/systemicons/hall01.png");
		location.setMobileIconFile("guest_room");
		LocationServiceManager locationServiceManager = new LocationServiceManager();
		String isDuplicate = locationServiceManager.verifyRoomDetails(location);
		 
		if (isDuplicate.equalsIgnoreCase("validRoom")) {
			LocationManager locationManager = new LocationManager();
			if (locationManager.saveLocation(location)) {
				saveResult = true;
			}
		}
	
		LogUtil.info(" location saveResult : " + saveResult);
		resultXml = "<imonitor>";
		resultXml += "<status>";
		if (saveResult)
		{
			resultXml += "Success";
		}
		else {
			resultXml += "Failure";
		}
		resultXml += "</status>";
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	
	LogUtil.info("addNewRoomMID OUT: " + resultXml);
	return resultXml;

	}

public String synchronizeLocationDetails(String xml){

	//LogUtil.info("synchronizeLocationDetails: IN "+ xml);
	String resultXml = "Success";
	boolean saveResult = false;
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);

		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);

		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);

		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		XStream stream = new XStream();

		CustomerManager customerManager = new CustomerManager();
		Customer customer = customerManager.getCustomerByCustomerId(customerId);
	
		LocationManager locationManager = new LocationManager();
		List<Location> locationList =  locationManager.listOfLocations(customer);
		//LogUtil.info("locationList : " + stream.toXML(locationList));
		resultXml = "<imonitor>";
		resultXml += "<status>";
		if (!locationList.isEmpty())
		{
			resultXml += "Success";
			resultXml += "</status>";
			resultXml += "<locations>";
			for (Location location : locationList) 
			{
				resultXml += "<location>";
				resultXml += "<locationId>";
				resultXml += location.getId();
				resultXml += "</locationId>";
				resultXml += "<locationName>";
				resultXml += location.getName();
				resultXml += "</locationName>";
				resultXml += "</location>";
			}
			resultXml += "</locations>";
		}
		else {
			resultXml += "Failure";
			resultXml += "</status>";
		}
		resultXml += "</imonitor>";
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	
	//LogUtil.info("synchronizeLocationDetails OUT: " + resultXml);
	return resultXml;

	}

//deviceDiscoveryMID
public String deviceDiscoveryMID(String xml){
	LogUtil.info("deviceDiscoveryMID: IN "+ xml);
	String resultXml = "Success";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);

		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);

		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		LogUtil.info(" usercheckresult " + result);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		XStream stream = new XStream();

		NodeList macIdNodes = document.getElementsByTagName("macId");
		Element macIdNode = (Element) macIdNodes.item(0);
		String macId = XmlUtil.getCharacterDataFromElement(macIdNode);
		
		NodeList checkedDeviceNodes = document.getElementsByTagName("checkedDevice");
		Element checkedDeviceNode = (Element) checkedDeviceNodes.item(0);
		String checkedDevice = XmlUtil.getCharacterDataFromElement(checkedDeviceNode);
		
		boolean islimit = false ;
		String macIdXml = stream.toXML(macId);
		String xml1 = stream.toXML(islimit);
		String xml2 = stream.toXML(checkedDevice);
		GatewayServiceManager gatewayServiceManager = new GatewayServiceManager();
		String resultfrom = gatewayServiceManager.setDeviceDiscoveryMode(macIdXml, xml1, xml2);
		resultXml = "<imonitor>";
		resultXml += "<status>";
		if (resultfrom.equals("offline"))
		{
			
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "<message>";
			resultXml += "Gateway is offline";
		}
		else if (resultfrom.equals("failure"))
		{
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "<message>";
			resultXml += "Discovery Mode failed";
		}
		else {
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "<message>";
			resultXml += "Gateway is in Discovery Mode";
		}
		resultXml += "</message>";
		resultXml += "</imonitor>";
		
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	
	LogUtil.info("deviceDiscoveryMID OUT: " + resultXml);
	return resultXml;
}

//deviceDiscoveryAbortMID
public String deviceDiscoveryAbortMID(String xml){
	LogUtil.info("deviceDiscoveryAbortMID: IN "+ xml);
	String resultXml = "Success";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);

		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);

		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		//LogUtil.info(" usercheckresult " + result);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		XStream stream = new XStream();

		NodeList macIdNodes = document.getElementsByTagName("macId");
		Element macIdNode = (Element) macIdNodes.item(0);
		String macId = XmlUtil.getCharacterDataFromElement(macIdNode);
		
		String status = "Device Discovery Mode";
		String macIdXml = stream.toXML(macId);
		String xml1 = stream.toXML(status);
		GatewayServiceManager gatewayServiceManager = new GatewayServiceManager();
		String resultfrom = gatewayServiceManager.setAbortMode(macIdXml, xml1);
		resultXml = "<imonitor>";
		resultXml += "<status>";
		if (resultfrom.equals("failure"))
		{
			resultXml += "failure";
		}
		else {
			resultXml += "success";
		}
		resultXml += "</status>";
		resultXml += "</imonitor>";
		
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	
	LogUtil.info("deviceDiscoveryAbortMID OUT: " + resultXml);
	return resultXml;
}

public String checkAndUpdateUserPassword(String xml){
	LogUtil.info("deviceDiscoveryAbortMID: IN "+ xml);
	String resultXml = "Success";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);

		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);

		NodeList newpasswordNodes = document.getElementsByTagName("newPassword");
		Element npasswordNode = (Element) newpasswordNodes.item(0);
		String newPassword = XmlUtil.getCharacterDataFromElement(npasswordNode);
		String newHashPassword = IMonitorUtil.getHashPassword(newPassword);
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		//LogUtil.info(" usercheckresult " + result);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		
		User user = userManager.getUserWithCustomerByUserIdAndUpdatePassword(userId, customerId,newHashPassword);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateway=gatewayManager.getGateWayByCustomer(user.getCustomer());
		gateway=gatewayManager.getGateWayWithAgentById(gateway.getId());
		ActionModel actionModel = new ActionModel(gateway,user);
		ImonitorControllerAction changePasswordUser=new changePasswordUser();
		changePasswordUser.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(changePasswordUser);
		//result="no";
		LogUtil.info("resultFromImvg----"+resultFromImvg);
		if(resultFromImvg){
			
			if(changePasswordUser.isActionSuccess()){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "success";
				resultXml += "</status>";
				resultXml += "</imonitor>";
			}else{
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
			}
		}else{
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			
		}
		
				
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	
	
	return resultXml;
}

public String requestAssociateForSensor(String xml){
	
	String resultXml = "Success";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

		LogUtil.info("customer  " + customerId);
		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);

		LogUtil.info("user  " + userId);
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);

		LogUtil.info("password  " + password);
		
		NodeList deviceIdNodes = document.getElementsByTagName("generatedDeviceId");
		LogUtil.info("ji");
		Element deviceIdNode = (Element) deviceIdNodes.item(0);
		LogUtil.info("ji ji  " );
		String deviceId = XmlUtil.getCharacterDataFromElement(deviceIdNode);
		
		LogUtil.info("deviceId  " + deviceId);
		UserManager userManager = new UserManager();
		LogUtil.info("step ");
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		//LogUtil.info(" usercheckresult " + result);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		DeviceManager deviceManager = new DeviceManager();
		Device devicefromDb = deviceManager.getDevice(deviceId);
		devicefromDb.setSwitchType(2);
		CommandIssueServiceManager command = new CommandIssueServiceManager();
		
		XStream stream = new XStream();
		String deviceXml = stream.toXML(devicefromDb);
		command.nupdateDevice(deviceXml);
		
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "success";
				resultXml += "</status>";
				resultXml += "</imonitor>";
			
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	
	LogUtil.info("return result : " + resultXml);
	return resultXml;
}

	                         //deviceDiscoveryAbortMID
public String panicRingAlert(String xml){
	LogUtil.info("deviceDiscoveryAbortMID: IN "+ xml);
	String resultXml = "Success";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);

		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);

		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		//LogUtil.info(" usercheckresult " + result);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		XStream stream = new XStream();


		CustomerManager customerManager = new CustomerManager();
		Customer customer = customerManager.getCustomerByCustomerId(customerId);
		
		PanicRingService panicRingService= new PanicRingService(customer);
		Thread thread = new Thread(panicRingService);
		thread.start();
		
		
		resultXml = "<imonitor>";
		resultXml += "<status>";
		
			resultXml += "success";
		
		resultXml += "</status>";
		resultXml += "</imonitor>";
		
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} 
	
	LogUtil.info("deviceDiscoveryAbortMID OUT: " + resultXml);
	return resultXml;
}

							/*************Schedule start*****************/

//Create Schedule
public String createScheduleMID(String xml){
	LogUtil.info("CreateScheduleMID IN");
	
	XStream stream = new XStream();
	boolean saveResult = false;
	
	String resultXml = "Success";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		LogUtil.info("customer  " + customerId);
		
		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		LogUtil.info("user  " + userId);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		LogUtil.info("password  " + password);
		
		NodeList scheduleNameNodes = document.getElementsByTagName("scheduleName");
		Element scheduleNameNode = (Element) scheduleNameNodes.item(0);
		String scheduleName = XmlUtil.getCharacterDataFromElement(scheduleNameNode);
		LogUtil.info("scheduleName  " + scheduleName);
		
		NodeList scheduleDescriptionNodes = document.getElementsByTagName("scheduleDescription");
		Element scheduleDescriptionNode = (Element) scheduleDescriptionNodes.item(0);
		String scheduleDescription = XmlUtil.getCharacterDataFromElement(scheduleDescriptionNode);
		LogUtil.info("scheduleDescription  " + scheduleDescription);
		
		NodeList scheduleTimeNodes = document.getElementsByTagName("scheduleTime");
		Element scheduleTimeNode = (Element) scheduleTimeNodes.item(0);
		String scheduleTime = XmlUtil.getCharacterDataFromElement(scheduleTimeNode);
		LogUtil.info("scheduleTime  " + scheduleTime);
		
		/*String time=scheduleTime.replace(',',' ');
		LogUtil.info("time  " + time);*/
		
		NodeList macIdNodes = document.getElementsByTagName("macid");
		Element macIdNode = (Element) macIdNodes.item(0);
		String macId = XmlUtil.getCharacterDataFromElement(macIdNode);
		LogUtil.info("macId  " + macId);

		
		NodeList deviceNodes = document.getElementsByTagName("device");
		Element deviceNode = (Element) deviceNodes.item(0);
		String deviceid = XmlUtil.getCharacterDataFromElement(deviceNode);
		LogUtil.info("deviceid  " + deviceid);
		
		NodeList actionTypeNodes = document.getElementsByTagName("actionType");
		Element actionTypeNode = (Element) actionTypeNodes.item(0);
		String actionName = XmlUtil.getCharacterDataFromElement(actionTypeNode);
		LogUtil.info("actionName  " + actionName);
		
		NodeList levelNodes = document.getElementsByTagName("level");
		Element levelNode = (Element) levelNodes.item(0);
		String level = XmlUtil.getCharacterDataFromElement(levelNode);
		LogUtil.info("level  " + level);
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		LogUtil.info(" usercheckresult " + result);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		ScheduleServiceManager scheduleServiceManager = new ScheduleServiceManager();
		ScheduleManager scheduleManager = new ScheduleManager();
		Schedule schedule = new Schedule();
		Set<ScheduleAction> scheduleActions=new LinkedHashSet<ScheduleAction>(); 
		
		schedule.setName(scheduleName);
		schedule.setActivationStatus(1);
		schedule.setDescription(scheduleDescription);
		schedule.setScheduleTime(scheduleTime);
		
		
		
		GatewayManager gatewayManager =new GatewayManager();
		GateWay gateWay = new GateWay();
		gateWay=gatewayManager.getGateWayWithAgent(macId, customerId);
		schedule.setGateWay(gateWay);
		
		
		ScheduleAction scheduleAction = new ScheduleAction();
		Device device =new Device();
		DeviceManager devicemanager= new DeviceManager();
		device=devicemanager.getDeviceByGeneratedDeviceId(deviceid);
		scheduleAction.setDevice(device);
		
		ActionTypeManager actionTypeManager = new ActionTypeManager();
		ActionType actionType = new ActionType();
		actionType=actionTypeManager.getActionTypeName(actionName);
		scheduleAction.setActionType(actionType);
		
		
		scheduleAction.setLevel(level);
		scheduleActions.add(scheduleAction);
		schedule.setScheduleActions(scheduleActions);
		
		String isDuplicate = scheduleServiceManager.verifyscheduleDetails(schedule);
		LogUtil.info("isDuplicate=" + isDuplicate);
		
		
		if (isDuplicate.equalsIgnoreCase("validSchedule")) {
			
			if (scheduleManager.saveSchedule(schedule)) {
				saveResult = true;
			}
		}
	
		LogUtil.info(" schedule saveResult : " + saveResult);
		resultXml = "<imonitor>";
		resultXml += "<status>";
		if (saveResult)
		{
			resultXml += "success";
		}
		else {
			resultXml += "failure";
		}
		resultXml += "</status>";
		resultXml += "</imonitor>";
		
		/*GatewayManager gatewayManager =new GatewayManager();
		String gateWayid= gatewayManager.getIdOfGateway(macId);
		long gid=Long.parseLong(gateWayid);
		LogUtil.info("gid  " + gid);
		
		DeviceManager devicemanager= new DeviceManager();
		String deviceId=devicemanager.getIdOfDevice(deviceid);
		long did=Long.parseLong(deviceId);
		LogUtil.info("did  " + did);
		
		ActionTypeManager actionTypeManager = new ActionTypeManager();
		String actionTypeid=actionTypeManager.getIdOfActionType(actionName);
		long aid=Long.parseLong(actionTypeid);
		LogUtil.info("aid  " + did);
		
		ScheduleServiceManager scheduleServiceManager = new ScheduleServiceManager();
		ScheduleManager scheduleManager = new ScheduleManager();
		
		
		Schedule schedule = new Schedule();
		schedule.setName(scheduleName);
		schedule.setActivationStatus(1);		//By default Schedule should be active.
		
		GateWay gateWay = new GateWay();
		gateWay.setId(gid);
		schedule.setGateWay(gateWay);
		
		schedule.setScheduleTime(scheduleTime);
		
		schedule.setDescription(scheduleDescription);
		
		String isDuplicate = scheduleServiceManager.verifyscheduleDetails(schedule);
		LogUtil.info("isDuplicate=" + isDuplicate);
		
		
		if (isDuplicate.equalsIgnoreCase("validSchedule")) {
			
			if (scheduleManager.saveSchedule(schedule)) {
				saveResult = true;
			}
		}
	
		LogUtil.info(" schedule saveResult : " + saveResult);
		resultXml = "<imonitor>";
		resultXml += "<status>";
		if (saveResult)
		{
			resultXml += "success";
		}
		else {
			resultXml += "failure";
		}
		resultXml += "</status>";
		resultXml += "</imonitor>";
		
		String scheduleid=scheduleManager.getIdOfschedule(gid,scheduleName);
		long sid=Long.parseLong(scheduleid);
		LogUtil.info("sid  " + sid);
		
		ScheduleAction scheduleAction= new ScheduleAction();
		Device device =new Device();
		device.setId(did);
		scheduleAction.setDevice(device);
		
		ActionType actionType = new ActionType();
		actionType.setId(aid);
		scheduleAction.setActionType(actionType);
		
		schedule.setId(sid);
		scheduleAction.setSchedule(schedule);
		
		
		scheduleAction.setLevel(level);
		saveResult=scheduleManager.saveScheduleAction(scheduleAction);
		LogUtil.info(" schedule saveResult1 : " + saveResult);*/
		
		
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	}
	
	
	LogUtil.info("toCreateScheduleMID OUT: resultXml::"+stream.toXML(resultXml));	
	return resultXml;
}


//Schedules List
		public String synchronizeScheduleDetails(String xml){
			LogUtil.info("synchronizeScheduleDetails IN  : ");
			String resultXml = "";
			try {
				Document document = XmlUtil.getDocument(xml);
				
				NodeList customerNodes = document.getElementsByTagName("customer");
				Element customerNode = (Element) customerNodes.item(0);
				String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
				
				NodeList useridNodes = document.getElementsByTagName("userid");
				Element useridNode = (Element) useridNodes.item(0);
				String userId = XmlUtil.getCharacterDataFromElement(useridNode);
				
				NodeList passwordNodes = document.getElementsByTagName("password");
				Element passwordNode = (Element) passwordNodes.item(0);
				String password = XmlUtil.getCharacterDataFromElement(passwordNode);
				String hashPassword = IMonitorUtil.getHashPassword(password);
				
				UserManager userManager = new UserManager();
				String result = userManager.isUserExists(customerId, userId, hashPassword);
				
				if(result.equalsIgnoreCase("failure")){
					resultXml = "<imonitor>";
					resultXml += "<status>";
					resultXml += "failure";
					resultXml += "</status>";
					resultXml += "</imonitor>";
					return resultXml;
				}
//				fetch the schedules using the customer id.
				ScheduleManager scheduleManager = new ScheduleManager();
				
				
				List<Object[]> schedules = scheduleManager.listAllSchedulesByCustomerIdWithoutEndSchedule(customerId);
				XStream stream=new XStream();
				LogUtil.info("Schedules List from DB : "+ stream.toXML(schedules));
				if(schedules == null){
					resultXml = "<imonitor>";
					resultXml += "<status>";
					resultXml += "failure";
					resultXml += "</status>";
					resultXml += "</imonitor>";
					return resultXml;
				}
				Map<String, String> scheduleActions = new HashMap<String, String>();
				Map<String, String> scheduleDetails = new HashMap<String, String>();
				Map<String, String> scheduleActivation = new HashMap<String, String>();
				Map<String, Integer> scheduleId=new HashMap<String, Integer>();
				String activationStatus = "";
				for (Object[] objects : schedules) 
				{
					LogUtil.info("Loop enter : Object : "+ objects);
					String scheduleName = IMonitorUtil.convertToString(objects[1]);
					String scheduleDescription = IMonitorUtil.convertToString(objects[2]);
					String ScheduleiD=IMonitorUtil.convertToString(objects[0]);
					int id=Integer.parseInt(ScheduleiD);
					scheduleId.put(scheduleName, id);
					scheduleDetails.put(scheduleName, scheduleDescription);
					String deviceId = IMonitorUtil.convertToString(objects[3]);
					String command = IMonitorUtil.convertToString(objects[4]);
					String level = IMonitorUtil.convertToString(objects[5]);
					activationStatus = IMonitorUtil.convertToString(objects[6]);
					scheduleActivation.put(scheduleName, activationStatus);
					String actions = scheduleActions.get(scheduleName);
					LogUtil.info("deviceId : "+deviceId);
				LogUtil.info("command : "+command);
					if(actions == null){
						actions = "";
					}
					/*actions += "<command>";
					actions += "<deviceid>";
					actions += deviceId;
					actions += "</deviceid>";
					actions += "<eventname>";
					actions += command;
					actions += "</eventname>";
					if(level != null){
						actions += "<level>";
						actions += level;
						actions += "</level>";
					}
					actions += "</command>";
					actions += "<activationStatus>";
					actions += activationStatus;
					actions += "</activationStatus>";*/
					scheduleActions.put(scheduleName, actions);
					scheduleActions.put(scheduleName, activationStatus);
				}
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "success";
				resultXml += "</status>";
				resultXml += "<schedules>";
				
				Set<String> keySet = scheduleActions.keySet();
					for (String scheduleName : keySet) {
						resultXml += "<schedule>";
						resultXml += "<id>";
						resultXml += scheduleId.get(scheduleName);
						resultXml += "</id>";
						resultXml += "<name>";
						resultXml += scheduleName;
						resultXml += "</name>";
						resultXml += "<description>";
						resultXml += scheduleDetails.get(scheduleName);
						resultXml += "</description>";
						//resultXml += scheduleActions.get(scheduleName);
						resultXml +="<activationStatus>";
						resultXml += scheduleActivation.get(scheduleName);
						resultXml +="</activationStatus>";
						resultXml += "</schedule>";
					}
				resultXml += "</schedules>";
				resultXml += "</imonitor>";
			} catch (ParserConfigurationException e) {
				resultXml  = "Couldn't parse the input";
				e.printStackTrace();
			} catch (SAXException e) {
				resultXml = "Couldn't parse the input";
				e.printStackTrace();
			} catch (IOException e) {
				resultXml = "Couldn't parse the input";
				e.printStackTrace();
			}
			
			LogUtil.info("synchronizeScheduleDetails out:: resultXml: "+resultXml);
			return resultXml;
		}


//Schedule activation
public String activateScheduleFromMid(String xml){
	LogUtil.info("activateScheduleFromMid In:");
	String resultXml = "";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		
		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		
		String hashPassword = IMonitorUtil.getHashPassword(password);
		
		NodeList scheduleNameNodes = document.getElementsByTagName("schedulename");
		Element scheduleNode = (Element) scheduleNameNodes.item(0);
		String scheduleName = XmlUtil.getCharacterDataFromElement(scheduleNode);
		
		
		NodeList activationNode = document.getElementsByTagName("activationstatus");
		Element actstsNode = (Element) activationNode.item(0);
		String activationsts = XmlUtil.getCharacterDataFromElement(actstsNode);
		LogUtil.info("activationsts :"+activationsts);
		
		UserManager userManager = new UserManager();
		String uResult = userManager.isUserExists(customerId, userId, hashPassword);
		if(uResult.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		ScheduleManager schedulemng=new ScheduleManager();
		boolean exeResult = false;
		List<Long> scheduleIds=schedulemng.getscheduleidsfromschedulenameForSuperUser(scheduleName, customerId);
		
		for (Long long1 : scheduleIds)
		{
			Schedule schedule=schedulemng.retrieveScheduleDetails(long1);
			schedule.setActivationStatus(Integer.parseInt(activationsts));
			ActionModel actionModel = new ActionModel(null, schedule);
			ImonitorControllerAction scheduleActivationAction = new ScheduleActivateAction();
			scheduleActivationAction.executeCommand(actionModel);
			boolean resultFromImvg = IMonitorUtil.waitForResult(scheduleActivationAction);
			LogUtil.info("scheduleActivationAction resultFromImvg="+resultFromImvg);
			if(resultFromImvg)
			{
				if(scheduleActivationAction.isActionSuccess())
				{schedulemng.updateScheduleWithDetails(schedule);
					exeResult = true;
				}
			}
		}

		if(!exeResult){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		resultXml = "<imonitor>";
		resultXml += "<status>";
		resultXml += "success";
		resultXml += "</status>";
		resultXml +="<activationstatus>";
		resultXml +=activationsts;
		resultXml +="</activationstatus>";
		resultXml += "</imonitor>";
		
	} catch (ParserConfigurationException e) {
		resultXml  = "Couldn't parse the input";
	} catch (SAXException e) {
		resultXml = "Couldn't parse the input";
	} catch (IOException e) {
		resultXml = "Couldn't parse the input";
	}
	LogUtil.info("activateScheduleFromMid Out:resultXml"+resultXml);
	return resultXml;
}
// save schedule

public String saveScheduleFromMid(String xml){
	LogUtil.info("saveScheduleFromMid IN");
	
	XStream stream = new XStream();
	boolean saveResult = false;
	
	String resultXml = "Success";
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		LogUtil.info("customer  " + customerId);
		
		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		LogUtil.info("user  " + userId);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		LogUtil.info("password  " + password);
		
		NodeList scheduleIdNodes = document.getElementsByTagName("scheduleid");
		Element scheduleIdNode = (Element) scheduleIdNodes.item(0);
		String scheduleid = XmlUtil.getCharacterDataFromElement(scheduleIdNode);
		LogUtil.info("scheduleid  " + scheduleid);
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		LogUtil.info(" usercheckresult " + result);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		Long sid = Long.parseLong(scheduleid);
		ScheduleServiceManager scheduleServiceManager = new ScheduleServiceManager();
		ScheduleManager scheduleManager = new ScheduleManager();
		
		
		Schedule schedule=scheduleManager.retrieveScheduleDetails(sid);
		LogUtil.info(" schedule " + schedule);
		
		resultXml  = "<imonitor>";
		if(schedule == null){
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
		}else{
			
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "<schedule>";
			resultXml += "<id>";
			resultXml += schedule.getId();
			resultXml += "</id>";
			resultXml += "<name>";
			resultXml += schedule.getName();
			resultXml += "</name>";
			resultXml += "<description>";
			resultXml += schedule.getDescription();
			resultXml += "</description>";
			resultXml += "<time>";
			resultXml += schedule.getScheduleTime();
			resultXml += "</time>";
			
			resultXml += "<scheduleactions>";

		Set<ScheduleAction> scheduleActions = schedule.getScheduleActions();
		for(ScheduleAction scheduleAction:scheduleActions)
		{
			
			resultXml += "<scheduleaction>";
			resultXml += "<scheduleactionid>";
			resultXml += scheduleAction.getId();
			resultXml += "</scheduleactionid>";
			
			resultXml += "<device>";
			resultXml += "<Id>";
			resultXml += scheduleAction.getDevice().getId();
			resultXml += "</Id>";
			resultXml += "<name>";
			resultXml += scheduleAction.getDevice().getFriendlyName();
			resultXml += "</name>";
			
			resultXml += "</device>";
			
			resultXml += "<actiontype>";
			resultXml += "<id>";
			resultXml += scheduleAction.getActionType().getId();
			resultXml += "</id>";
			resultXml += "<actionname>";
			resultXml += scheduleAction.getActionType().getName();
			resultXml += "</actionname>";
			resultXml += "</actiontype>";
			
			resultXml += "<level>";
			resultXml += scheduleAction.getLevel();
			resultXml += "</level>";
			resultXml += "</scheduleaction>";
		}
		resultXml += "</scheduleactions>";
		resultXml += "</schedule>";
		}
			
		resultXml += "</imonitor>";
	
	
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	}
	
	
	LogUtil.info("saveScheduleFromMid OUT: resultXml::"+stream.toXML(resultXml));	
	return resultXml;
}


	
//Edit schedule
public String editScheduleMID(String xml){
	LogUtil.info("editScheduleMID"+xml);
	XStream stream = new XStream();
	String resultXml = "Success";
//	boolean res = false;
	
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		LogUtil.info("customer  " + customerId);
		
		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		LogUtil.info("user  " + userId);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		LogUtil.info("password  " + password);
		
		NodeList scheduleIdNodes = document.getElementsByTagName("scheduleId");
		Element scheduleIdNode = (Element) scheduleIdNodes.item(0);
		String scheduleid = XmlUtil.getCharacterDataFromElement(scheduleIdNode);
		LogUtil.info("scheduleid  " + scheduleid);
		
		NodeList scheduleNameNodes = document.getElementsByTagName("scheduleName");
		Element scheduleNameNode = (Element) scheduleNameNodes.item(0);
		String scheduleName = XmlUtil.getCharacterDataFromElement(scheduleNameNode);
		LogUtil.info("scheduleName  " + scheduleName);
		
		NodeList scheduleDescriptionNodes = document.getElementsByTagName("scheduleDescription");
		Element scheduleDescriptionNode = (Element) scheduleDescriptionNodes.item(0);
		String scheduleDescription = XmlUtil.getCharacterDataFromElement(scheduleDescriptionNode);
		LogUtil.info("scheduleDescription  " + scheduleDescription);
		
		NodeList scheduleTimeNodes = document.getElementsByTagName("scheduleTime");
		Element scheduleTimeNode = (Element) scheduleTimeNodes.item(0);
		String scheduleTime = XmlUtil.getCharacterDataFromElement(scheduleTimeNode);
		LogUtil.info("scheduleTime  " + scheduleTime);
		
		/*String time=scheduleTime.replace(',',' ');
		LogUtil.info("time  " + time);*/
		
		NodeList macIdNodes = document.getElementsByTagName("macid");
		Element macIdNode = (Element) macIdNodes.item(0);
		String macId = XmlUtil.getCharacterDataFromElement(macIdNode);
		LogUtil.info("macId  " + macId);

		
		NodeList deviceNodes = document.getElementsByTagName("device");
		Element deviceNode = (Element) deviceNodes.item(0);
		String deviceid = XmlUtil.getCharacterDataFromElement(deviceNode);
		LogUtil.info("deviceid  " + deviceid);
		
		NodeList actionTypeNodes = document.getElementsByTagName("actionType");
		Element actionTypeNode = (Element) actionTypeNodes.item(0);
		String actionName = XmlUtil.getCharacterDataFromElement(actionTypeNode);
		LogUtil.info("actionName  " + actionName);
		
		
		NodeList levelNodes = document.getElementsByTagName("level");
		Element levelNode = (Element) levelNodes.item(0);
		String level = XmlUtil.getCharacterDataFromElement(levelNode);
		LogUtil.info("level  " + level);
		
		
		Schedule scheduleToUpdate= new Schedule();
		Set<ScheduleAction> scheduleActions=new LinkedHashSet<ScheduleAction>(); 
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		LogUtil.info(" usercheckresult " + result);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		
		// set basic informations
		Long sid = Long.parseLong(scheduleid);
		LogUtil.info("sid"+sid);
		scheduleToUpdate.setId(sid);
		scheduleToUpdate.setName(scheduleName);
		scheduleToUpdate.setDescription(scheduleDescription);
		scheduleToUpdate.setScheduleTime(scheduleTime);
		
		ScheduleServiceManager scheduleServiceManager = new ScheduleServiceManager();
		ScheduleManager scheduleManager = new ScheduleManager();
		boolean exeResult = false;
		
		//Retrive existing schedule from database using schedule Id
		Schedule schedulefromdb = scheduleManager.retrieveScheduleDetails(sid);
		LogUtil.info("schedulefromdb"+schedulefromdb);
		
		//Set notification from DB
		scheduleToUpdate.setActivationStatus(schedulefromdb.getActivationStatus());
		scheduleToUpdate.setImvgAlertNotificationsForScedule(schedulefromdb.getImvgAlertNotificationsForScedule());
		
	
		
		// Set scheduleaction details 
		try{
		ScheduleAction scheduleAction = new ScheduleAction();
		Device device =new Device();
		DeviceManager devicemanager= new DeviceManager();
		device=devicemanager.getDeviceByGeneratedDeviceId(deviceid);
		scheduleAction.setDevice(device);
		
		ActionTypeManager actionTypeManager = new ActionTypeManager();
		ActionType actionType = new ActionType();
		actionType=actionTypeManager.getActionTypeName(actionName);
		scheduleAction.setActionType(actionType);
		
		GatewayManager gatewayManager =new GatewayManager();
		
		
		// Set gateway details with agent info
		GateWay gateWay = new GateWay();
		gateWay=gatewayManager.getGateWayWithAgent(macId, customerId);
		scheduleToUpdate.setGateWay(gateWay);
		
		
		//set level,null is saved for the device level is not supported
		scheduleAction.setLevel(level);
		
		
		scheduleActions.add(scheduleAction);
		}catch (Exception e) {
			LogUtil.error(result+e.getMessage());
			LogUtil.info(result, e);
		} 
		
		scheduleToUpdate.setScheduleActions(scheduleActions);
		
		ActionModel actionModel = new ActionModel(null, scheduleToUpdate);
		ImonitorControllerAction scheduleCreateAction = new ScheduleUpdateAction();
		scheduleCreateAction.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(scheduleCreateAction);
		LogUtil.info(" resultFromImvg="+resultFromImvg);
		if(resultFromImvg)
		{
			if(scheduleCreateAction.isActionSuccess())
			{scheduleManager.updateScheduleWithDetails(scheduleToUpdate);
				exeResult = true;
			}
		}
		
		resultXml = "<imonitor>";
		resultXml += "<status>";
		if (resultFromImvg)
		{
			resultXml += "success";
		}
		else {
			resultXml += "failure";
		}
		resultXml += "</status>";
		resultXml += "</imonitor>";
	
	//	LogUtil.info("scheduleToUpdate=="+stream.toXML(scheduleToUpdate));
			
	boolean updateSchedule= scheduleManager.updateScheduleWithDetails(scheduleToUpdate);
			LogUtil.info("updateSchedule=="+updateSchedule);
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			if (updateSchedule)
			{
				resultXml += "success";
			}
			else {
				resultXml += "failure";
			}
			resultXml += "</status>";
			resultXml += "</imonitor>";
			
		
		
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	}
	LogUtil.info("editScheduleMID Out resultXml::"+resultXml);
	return resultXml;
}

	
//Delete schedule		
public String deleteScheduleMID(String xml){
	LogUtil.info("deleteScheduleMID In::");
	XStream stream = new XStream();
	String resultXml = "Success";
	
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		LogUtil.info("customer  " + customerId);
		
		NodeList useridNodes = document.getElementsByTagName("userid");
		Element useridNode = (Element) useridNodes.item(0);
		String userId = XmlUtil.getCharacterDataFromElement(useridNode);
		LogUtil.info("user  " + userId);
		
		NodeList passwordNodes = document.getElementsByTagName("password");
		Element passwordNode = (Element) passwordNodes.item(0);
		String password = XmlUtil.getCharacterDataFromElement(passwordNode);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		LogUtil.info("password  " + password);
		
		
		NodeList scheduleidNodes = document.getElementsByTagName("scheduleid");
		Element scheduleidNode = (Element) scheduleidNodes.item(0);
		String scheduleid = XmlUtil.getCharacterDataFromElement(scheduleidNode);
		LogUtil.info("scheduleid ="+scheduleid);
		
		UserManager userManager = new UserManager();
		String result = userManager.isUserExists(customerId, userId, hashPassword);
		LogUtil.info(" usercheckresult " + result);
		if(result.equalsIgnoreCase("failure")){
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
			return resultXml;
		}
		
		Long sid = Long.parseLong(scheduleid);
		LogUtil.info("sid ="+sid);
		
		
		ScheduleManager scheduleManager = new ScheduleManager();
		Schedule schedule = scheduleManager.getScheduleById(sid);
		boolean deleteSchedule = scheduleManager.deleteSchedule(schedule);
		LogUtil.info("deleteSchedule ="+deleteSchedule);
		if (deleteSchedule) {
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		} else {
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "failure";
			resultXml += "</status>";
			resultXml += "</imonitor>";
		}
		
		
		ScheduleAction scheduleAction = scheduleManager.getScheduleActionById(sid);
		boolean deleteScheduleAction = scheduleManager.deleteScheduleAction(scheduleAction);
		LogUtil.info("deleteScheduleAction ="+deleteScheduleAction);
		
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	}
	
	
	LogUtil.info("deleteScheduleMID OUT:: resultXml ="+stream.toXML(resultXml));
	return resultXml;
}
                                   /****************Schedule end********************/

//save SSID
	public String getSSIDInfo(String xml){
	LogUtil.info("getSSIDInfo IN: xml="+xml);
	XStream stream = new XStream();
	String resultXml="Success";
	
	try {
		Document document = XmlUtil.getDocument(xml);
		
		NodeList customerNodes = document.getElementsByTagName("customer");
		Element customerNode = (Element) customerNodes.item(0);
		String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
		LogUtil.info("customer" + customerId);
		
		NodeList macIdNodes = document.getElementsByTagName("macId");
		Element macIdNode = (Element) macIdNodes.item(0);
		String macId = XmlUtil.getCharacterDataFromElement(macIdNode);
		LogUtil.info("macId" + macId);
		
		GateWay gateWay = new GateWay();
		GatewayManager getwayManager = new GatewayManager();
		gateWay=getwayManager.getGateWayWithAgent(macId, customerId);
		LogUtil.info("gateWay " + gateWay);
		
		
			resultXml  = "<imonitor>";
			if(gateWay == null){
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
			}else{
				resultXml += "<SSID>";
				resultXml += gateWay.getSSID();
				resultXml += "</SSID>";
			}	
				resultXml += "</imonitor>";
				
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
		resultXml  = "Couldn't parse the input " + e.getMessage();
	} catch (SAXException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	} catch (IOException e) {
		e.printStackTrace();
		resultXml = "Couldn't parse the input " + e.getMessage();
	}
	
	LogUtil.info("getSSIDInfo OUT:: resultXml ="+stream.toXML(resultXml));
	return resultXml;
	}
	
//retrive SSID
	public String setSSIDInfo(String xml){
		
		LogUtil.info("setSSIDInfo IN::xml="+xml);
		XStream stream=new XStream();
		String resultXml="Success";
		boolean saveResult = false;
		
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			LogUtil.info("customer  " + customerId);
			
			NodeList macIdNodes = document.getElementsByTagName("macId");
			Element macIdNode = (Element) macIdNodes.item(0);
			String macId = XmlUtil.getCharacterDataFromElement(macIdNode);
			LogUtil.info("macId" + macId);
			
			NodeList ssidNodes = document.getElementsByTagName("ssid");
			Element ssidNode = (Element) ssidNodes.item(0);
			String ssid = XmlUtil.getCharacterDataFromElement(ssidNode);
			LogUtil.info("ssid  " + ssid);
			
			GateWay gateWay = new GateWay();
			GatewayManager gatewayManager = new GatewayManager();
		//	gateWay=getwayManager.getGateWayWithAgent(macId, customerId);
			gateWay = gatewayManager.getGateWayByMacId(macId);
			LogUtil.info("gateWay  " + gateWay);
			gateWay.setSSID(ssid);
			
			
			if (gatewayManager.updateGateWay(gateWay)) {
				saveResult = true;
			}
			LogUtil.info(" gateway saveResult : " + saveResult);
			resultXml = "<imonitor>";
			resultXml += "<status>";
			if (saveResult)
			{
				resultXml += "Success";
			}
			else {
				resultXml += "Failure";
			}
			resultXml += "</status>";
			resultXml += "</imonitor>";
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		LogUtil.info("setSSIDInfo OUT:: resultXml ="+stream.toXML(resultXml));
		return resultXml;
	}

	public String removeDevice(String xml) throws SecurityException, DOMException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		LogUtil.info("removeDevice In::");
		XStream stream = new XStream();
		String resultXml = "Success";
		
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			LogUtil.info("customer  " + customerId);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			LogUtil.info("user  " + userId);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			LogUtil.info("password  " + password);
			
			NodeList macIdNodes = document.getElementsByTagName("macid");
			Element macIdNode = (Element) macIdNodes.item(0);
			String macid = XmlUtil.getCharacterDataFromElement(macIdNode);
			LogUtil.info("macId  " + macid);
			
			
		
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			LogUtil.info(" usercheckresult " + result);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			
			String macIdXml = stream.toXML(macid);
			CommandIssueServiceManager commandIssueServiceManager= new CommandIssueServiceManager();
			String resultfrom = commandIssueServiceManager.deleteDeviceup(macIdXml);
			LogUtil.info(" resultfrom " + resultfrom);
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			if (resultfrom.equals("offline"))
			{
				
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "<message>";
				resultXml += "Gateway is offline";
			}
			else if (resultfrom.equals("failure"))
			{
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "<message>";
				resultXml += "Remove Mode failed";
			}
			else {
				resultXml += "success";
				resultXml += "</status>";
				resultXml += "<message>";
				resultXml += "Gateway is in Removal Mode";
			}
			resultXml += "</message>";
			resultXml += "</imonitor>";
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		LogUtil.info("removeDevice OUT:: resultXml ="+stream.toXML(resultXml));
		return resultXml;
		
	}
		
	public String createScenario(String xml) throws SecurityException, DOMException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		LogUtil.info("CreateScheduleMID IN");
		
		XStream stream = new XStream();
		String resultXml = "Success";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			LogUtil.info("customer  " + customerId);
			
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			LogUtil.info("user  " + userId);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			LogUtil.info("password  " + password);
			
			NodeList scenarioNameNodes = document.getElementsByTagName("scenarioName");
			Element scenarioNameNode = (Element) scenarioNameNodes.item(0);
			String scenarioName = XmlUtil.getCharacterDataFromElement(scenarioNameNode);
			LogUtil.info("scenarioName  " + scenarioName);
			
			NodeList descriptionNodes = document.getElementsByTagName("description");
			Element descriptionNode = (Element) descriptionNodes.item(0);
			String description = XmlUtil.getCharacterDataFromElement(descriptionNode);
			LogUtil.info("description  " + description);
			
			
			
			/*String time=scheduleTime.replace(',',' ');
			LogUtil.info("time  " + time);*/
			
			NodeList macIdNodes = document.getElementsByTagName("gateWay");
			Element macIdNode = (Element) macIdNodes.item(0);
			String macId = XmlUtil.getCharacterDataFromElement(macIdNode);
			LogUtil.info("macId  " + macId);

			HashSet<ScenarioAction> scenarioActions = new HashSet<ScenarioAction>();
			DeviceManager deviceManager = new DeviceManager(); 
			ActionTypeManager actionManager = new ActionTypeManager();
			Device device = new Device();
			ActionType actionType = new ActionType();
			NodeList deviceNodes = document.getElementsByTagName("device");
			for (int j = 0; j < deviceNodes.getLength(); ++j)
		    {
				 ScenarioAction scenarioAction = new ScenarioAction();
				 Node nNode = deviceNodes.item(j);

				// LogUtil.info("\nCurrent Element: " + nNode.getNodeName());
				 if (nNode.getNodeType() == Node.ELEMENT_NODE) {

		                Element elem = (Element) nNode;

		                Node nodeId = elem.getElementsByTagName("id").item(0);
		                String did = nodeId.getTextContent();
		                LogUtil.info("\nCurrent Element: " + did);
		                device = deviceManager.getDeviceByGeneratedDeviceId(did);
		                scenarioAction.setDevice(device);
		                
		                
		                Node node1 = elem.getElementsByTagName("actionType").item(0);
		                String aName = node1.getTextContent();
		                LogUtil.info("\nCurrent Element: " + aName);
		                actionType = actionManager.getActionTypeName(aName);
		                scenarioAction.setActionType(actionType);
		                
		                Node nodeLevel = elem.getElementsByTagName("level").item(0);
		                String level = nodeLevel.getTextContent();
		                LogUtil.info("\nCurrent Element: " + level);
		                scenarioAction.setLevel(level);

		              
		            }
				 scenarioActions.add(scenarioAction);
		    }
			 
		
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			 LogUtil.info("User result " + result);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			Scenario scenario = new Scenario();
			scenario.setName(scenarioName);
			scenario.setDescription(description);
			scenario.setScenarioActions(scenarioActions);
			
			GatewayManager gatewayManager =  new GatewayManager();
			GateWay gateway = gatewayManager.getGateWayByMacId(macId);
			scenario.setGateWay(gateway);
			scenario.setIconFile("/imonitor/resources/images/systemicons/party01.png");
		
			String  error = verifyScenarioDetails(scenario);
			if(error != null)return error;
			
			 result = "Some problem occurred while saving the scenario : "+ Constants.MESSAGE_DELIMITER_1 + scenario.getName();
			
			ScenarioManager scenarioManager = new ScenarioManager();
			if(scenarioManager.saveScenarioWithDetails(scenario)){
				//1.Send Create-Scenario Command to iMVG.
				scenario = scenarioManager.retrieveScenarioDetails(scenario.getId());
				
				ActionModel actionModel = new ActionModel(null, scenario);
				ImonitorControllerAction scenarioCreateAction = new ScenarioCreateAction();
				
				scenarioCreateAction.executeCommand(actionModel);
				
				//2.Do appropriate actions if iMVG return a success/failure.
				boolean resultFromImvg = IMonitorUtil.waitForResult(scenarioCreateAction);
				if(resultFromImvg){
					
					//result = "msg.controller.scenarios.0003"+ Constants.MESSAGE_DELIMITER_1 + scenarioCreateAction.getActionModel().getMessage();
					result = scenarioCreateAction.getActionModel().getMessage();
					result= "scenario created successfully";
					
					/*Added By naveen for updating scenario to alexa end point
					 * Date: 26th July 2018
					 */
					boolean isAlexaUserExist = scenarioManager.checkAlexaUserByCustomerId(scenario.getGateWay().getCustomer());;
					
					if(isAlexaUserExist) {
						
						AlexaProactiveUpdateScenarioService updateScenario = new AlexaProactiveUpdateScenarioService(scenario, scenario.getGateWay().getCustomer());
						Thread t = new Thread(updateScenario);
						t.start();
					}
					
				}else{
					result = "The scenario with name:"+ scenario.getName()+" was not created."
							+ Constants.MESSAGE_DELIMITER_1 + scenario.getName()
							+ Constants.MESSAGE_DELIMITER_2 + scenarioCreateAction.getActionModel().getMessage();
					scenarioCreateAction.executeFailureResults(actionModel.getQueue());
				}		
				//result = "scenario : '" + scenario.getName() + "' is saved.";
			}
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += result;
			resultXml += "</status>";
			resultXml += "</imonitor>";
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		
		LogUtil.info("toCreateScheduleMID OUT: resultXml::"+stream.toXML(resultXml));	
		return resultXml;
	}
	
	
	public String saveScenario(String xml){
		LogUtil.info("saveScenario: IN "+ xml);
		XStream stream = new XStream();
		String resultXml = "Success";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);

			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			NodeList scenarioidNodes = document.getElementsByTagName("scenarioid");
			Element scenarioidNode = (Element) scenarioidNodes.item(0);
			String scenarioid = XmlUtil.getCharacterDataFromElement(scenarioidNode);


			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			//LogUtil.info(" usercheckresult " + result);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			
			Long sid = Long.parseLong(scenarioid);
			ScenarioServiceManager scenarioServiceManager = new ScenarioServiceManager();
			ScenarioManager scenarioManager = new ScenarioManager();
			
			
			Scenario scenario=scenarioManager.retrieveScenarioDetails(sid);
			LogUtil.info(" scenario " + scenario);
			
			resultXml  = "<imonitor>";
			if(scenario == null){
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
			}else{
				
				resultXml += "<status>";
				resultXml += "success";
				resultXml += "</status>";
				resultXml += "<scenario>";
				resultXml += "<id>";
				resultXml += scenario.getId();
				resultXml += "</id>";
				resultXml += "<name>";
				resultXml += scenario.getName();
				resultXml += "</name>";
				resultXml += "<description>";
				resultXml += scenario.getDescription();
				resultXml += "</description>";
				
				
				resultXml += "<scenarioActions>";

			Set<ScenarioAction> scenarioActions = scenario.getScenarioActions();
			for(ScenarioAction scenarioAction:scenarioActions)
			{
				
				resultXml += "<scenarioAction>";
				resultXml += "<scenarioActionid>";
				resultXml += scenarioAction.getId();
				resultXml += "</scenarioActionid>";
				
				resultXml += "<device>";
				resultXml += "<Id>";
				resultXml += scenarioAction.getDevice().getId();
				resultXml += "</Id>";
				resultXml += "<name>";
				resultXml += scenarioAction.getDevice().getFriendlyName();
				resultXml += "</name>";
				
				resultXml += "</device>";
				
				resultXml += "<actiontype>";
				resultXml += "<id>";
				resultXml += scenarioAction.getActionType().getId();
				resultXml += "</id>";
				resultXml += "<actionname>";
				resultXml += scenarioAction.getActionType().getName();
				resultXml += "</actionname>";
				resultXml += "</actiontype>";
				
				resultXml += "<level>";
				resultXml += scenarioAction.getLevel();
				resultXml += "</level>";
				resultXml += "</scenarioAction>";
			}
			resultXml += "</scenarioActions>";
			resultXml += "</scenario>";
			}
				
			resultXml += "</imonitor>";
		
		
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		LogUtil.info("saveScenario OUT:: resultXml ="+stream.toXML(resultXml));
		return resultXml;
		
	}
	
	
	
	public String editScenario(String xml){
		LogUtil.info("editScenario: IN "+ xml);
		XStream stream = new XStream();
		String resultXml = "Success";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);

			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);

			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			NodeList scenarioidNodes = document.getElementsByTagName("scenarioid");
			Element scenarioidNode = (Element) scenarioidNodes.item(0);
			String scenarioid = XmlUtil.getCharacterDataFromElement(scenarioidNode);
			
			
			NodeList scenarioNameNodes = document.getElementsByTagName("scenarioName");
			Element scenarioNameNode = (Element) scenarioNameNodes.item(0);
			String scenarioName = XmlUtil.getCharacterDataFromElement(scenarioNameNode);
			LogUtil.info("scenarioName  " + scenarioName);

			NodeList gateWayNodes = document.getElementsByTagName("gateWay");
			Element gateWayNode = (Element) gateWayNodes.item(0);
			String macId = XmlUtil.getCharacterDataFromElement(gateWayNode);
			LogUtil.info("macId  " + macId);
			
			NodeList descriptionNodes = document.getElementsByTagName("description");
			Element descriptionNode = (Element) descriptionNodes.item(0);
			String description = XmlUtil.getCharacterDataFromElement(descriptionNode);
			LogUtil.info("description  " + description);
			
			HashSet<ScenarioAction> scenarioActions = new HashSet<ScenarioAction>();
			DeviceManager deviceManager = new DeviceManager(); 
			ActionTypeManager actionManager = new ActionTypeManager();
			Device device = new Device();
			ActionType actionType = new ActionType();
			NodeList deviceNodes = document.getElementsByTagName("device");
			for (int j = 0; j < deviceNodes.getLength(); ++j)
		    {
				 ScenarioAction scenarioAction = new ScenarioAction();
				 Node nNode = deviceNodes.item(j);

				// LogUtil.info("\nCurrent Element: " + nNode.getNodeName());
				 if (nNode.getNodeType() == Node.ELEMENT_NODE) {

		                Element elem = (Element) nNode;

		                Node nodeId = elem.getElementsByTagName("id").item(0);
		                String did = nodeId.getTextContent();
		                LogUtil.info("\nCurrent Element: " + did);
		                device = deviceManager.getDeviceByGeneratedDeviceId(did);
		                scenarioAction.setDevice(device);
		                
		                
		                Node node1 = elem.getElementsByTagName("actionType").item(0);
		                String aName = node1.getTextContent();
		                LogUtil.info("\nCurrent Element: " + aName);
		                actionType = actionManager.getActionTypeName(aName);
		                scenarioAction.setActionType(actionType);
		                
		                Node nodeLevel = elem.getElementsByTagName("level").item(0);
		                String level = nodeLevel.getTextContent();
		                LogUtil.info("\nCurrent Element: " + level);
		                scenarioAction.setLevel(level);

		              
		            }
				 scenarioActions.add(scenarioAction);
		    }

			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			//LogUtil.info(" usercheckresult " + result);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			Scenario scenario=new Scenario();
			 
			
			// set basic informations
			Long sid = Long.parseLong(scenarioid);
			LogUtil.info("sid"+sid);
			scenario.setId(sid);
			scenario.setName(scenarioName);
			scenario.setDescription(description);
			
			
			ScenarioServiceManager scenarioServiceManager = new ScenarioServiceManager();
			ScenarioManager scenarioManager = new ScenarioManager();
			boolean exeResult = false;
			
			//Retrive existing scenario from database using scenario Id
			Scenario scenariofromdb = scenarioManager.retrieveScenarioDetails(sid);
			
			
			GatewayManager gatewayManager =new GatewayManager();
			
			// Set gateway details with agent info
			GateWay gateWay = new GateWay();
			gateWay=gatewayManager.getGateWayWithAgent(macId, customerId);
			scenario.setGateWay(gateWay);
			
			scenario.setScenarioActions(scenarioActions);	
		
			ActionModel actionModel = new ActionModel(null, scenario);
			ImonitorControllerAction scenarioUpdateAction = new ScenarioUpdateAction();
			scenarioUpdateAction.executeCommand(actionModel);
			boolean resultFromImvg = IMonitorUtil.waitForResult(scenarioUpdateAction);
			LogUtil.info(" resultFromImvg="+resultFromImvg);
			if(resultFromImvg)
			{
				if(scenarioUpdateAction.isActionSuccess())
				{scenarioManager.updateScenarioWithDetails(scenario);
					exeResult = true;
				}
			}
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			if (resultFromImvg)
			{
				resultXml += "success";
			}
			else {
				resultXml += "failure";
			}
			resultXml += "</status>";
			resultXml += "</imonitor>";
		
		
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		LogUtil.info("editScenario Out resultXml::"+resultXml);
		return resultXml;
	
	}
		
	
	public String deleteScenario(String xml){
		LogUtil.info("deleteScenario: IN "+ xml);
		XStream stream = new XStream();
		String resultXml = "Success";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			 LogUtil.info("customerId="+customerId);
			 
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			 LogUtil.info("userId="+userId);

			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			 LogUtil.info("password="+password);
			
			NodeList scenarioidNodes = document.getElementsByTagName("scenarioid");
			Element scenarioidNode = (Element) scenarioidNodes.item(0);
			String scenarioid = XmlUtil.getCharacterDataFromElement(scenarioidNode);
			 LogUtil.info("scenarioid="+scenarioid);

			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			LogUtil.info(" usercheckresult " + result);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			Long sid = Long.parseLong(scenarioid);
			LogUtil.info("sid ="+sid);
			
			
			ScenarioManager scenarioManager = new ScenarioManager();
			Scenario scenario = scenarioManager.getScenarioById(sid);
			boolean deleteScenario = scenarioManager.deleteScenario(scenario);
			LogUtil.info("deleteScenario ="+deleteScenario);
			if (deleteScenario) {
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "success";
				resultXml += "</status>";
				resultXml += "</imonitor>";
			} else {
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
			}
			
			
			//ScenarioAction scenarioAction = scenarioManager.getScenarioActionById(sid);
			boolean deleteScenarioAction = scenarioManager.deleteScenarioActionsAndScheduleTimeByScheduleId(sid);
			LogUtil.info("deleteScenarioAction ="+deleteScenarioAction);
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		LogUtil.info("deleteScenario OUT:: resultXml ="+stream.toXML(resultXml));
		return resultXml;
		
	}
	
	//function for create scenario 
	
	@SuppressWarnings("unchecked")
	public String verifyScenarioDetails(Scenario scenario)
	{
		XStream xStream  = new XStream();
		 String result = null;
		try {
			
			List<Scenario> scenarios = new DaoManager().get("gateWay",scenario.getGateWay().getId(), Scenario.class);
			for(Scenario scenarioFromDB : scenarios)
			{
				
				if(scenario.getName().equalsIgnoreCase(scenarioFromDB.getName())
					&& scenario.getGateWay().getId() == scenarioFromDB.getGateWay().getId()){
					return "A scenario with specified name" +scenario.getName() + "already exists. Please use another name.";
					
				}
			}
		} catch (Exception e) {
			LogUtil.info("Got Exception while saving scenario: ", e);
		}
		LogUtil.info(xStream.toXML(result));
		return result;
	}
	
	public String listActions(String xml){
		LogUtil.info("listActions: IN "+ xml);
		XStream stream = new XStream();
		String resultXml = "Success";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			 LogUtil.info("customerId="+customerId);
			 
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			 LogUtil.info("userId="+userId);

			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			 LogUtil.info("password="+password);
			
			

			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			LogUtil.info(" usercheckresult " + result);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			CustomerManager customerManager = new CustomerManager();
			Customer customer = customerManager.getCustomerByCustomerId(customerId);
			
			GatewayManager gatewayManager = new GatewayManager();
			
			GateWay gateway = gatewayManager.getGateWayByCustomer(customer) ;
			DeviceManager devicemanager = new DeviceManager();
			List<Object[]> actionsObjects = devicemanager.listAllActionsByCustomerId(gateway);
			
			List<Object[]> scenarioObjects = devicemanager.listAllScenarioActionsByCustomerId(gateway);
			
			
			if((actionsObjects == null) && (scenarioObjects == null)){
				resultXml  = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml  = "</imonitor>";
				return resultXml;
			}
			LogUtil.info("listActions OUT:: resultXml ="+ stream.toXML(actionsObjects) );
			LogUtil.info("listActions OUT:: resultXml ="+ stream.toXML(scenarioObjects) );
			resultXml  = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "<actions>";
			
			for(Object[] deviceObject : actionsObjects)  {
				resultXml += "<action>";
				resultXml += "<id>";
				resultXml += deviceObject[0];
				resultXml += "</id>";
				resultXml += "<name>";
				resultXml += deviceObject[1];
				resultXml += "</name>";
				resultXml += "</action>";
			}

			for(Object[] scenarioObject : scenarioObjects)  {
				resultXml += "<action>";
				resultXml += "<id>";
				resultXml += scenarioObject[0];
				resultXml += "</id>";
				resultXml += "<name>";
				resultXml += scenarioObject[1];
				resultXml += "</name>";
				resultXml += "</action>";
			}
			resultXml += "</actions>";
			resultXml  += "</imonitor>";
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		LogUtil.info("listActions OUT:: resultXml ="+resultXml);
		return resultXml;
		
	}
	
	public String listVirtualSwitchConfig(String xml){
		LogUtil.info("listVirtualSwitchConfig: IN "+ xml);
		XStream stream = new XStream();
		String resultXml = "Success";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			 LogUtil.info("customerId="+customerId);
			 
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			 LogUtil.info("userId="+userId);

			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			 LogUtil.info("password="+password);
			
			 NodeList deviceNodes = document.getElementsByTagName("device");
				Element deviceNode = (Element) deviceNodes.item(0);
				String deviceid = XmlUtil.getCharacterDataFromElement(deviceNode);
				LogUtil.info("deviceid  " + deviceid);

			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			LogUtil.info(" usercheckresult " + result);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			LogUtil.info(" step 1 " );
			resultXml = "<imonitor>";
			resultXml += "<status>";
			resultXml += "success";
			resultXml += "</status>";
			resultXml += "<config>";
			
			LogUtil.info(" step 2 " );
			DeviceManager deviceManager = new DeviceManager();
			LogUtil.info(" step 3 " );
			Device device = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(deviceid);
			List<Object[]> keyConfigObjects = deviceManager.getKeyConfigListbyGeneratedId(device) ;
			LogUtil.info(" step 4 " );
			LogUtil.info(" keyConfigObjects "+stream.toXML(keyConfigObjects) );
			
			if(keyConfigObjects != null){
				for (Object[] keyConfiguration:keyConfigObjects) 
				{
					LogUtil.info(" step 5 " );
				//	Action action = deviceManager.getActionByid(Long.parseLong(IMonitorUtil.convertToString(keyConfiguration[7])));
					
					if(keyConfiguration[3].equals("ON")) {
						resultXml += "<ON>";
						resultXml +=IMonitorUtil.convertToString(keyConfiguration[7]);
						resultXml += "</ON>";
					}
					LogUtil.info(" step 6 " );
					if(keyConfiguration[3].equals("OFF")) {
						resultXml += "<OFF>";
						resultXml += IMonitorUtil.convertToString(keyConfiguration[7]);
						resultXml += "</OFF>";
					}
					
				}
				resultXml += "</config>";
				
			}else {
				LogUtil.info(" step 7" );
				resultXml += "<ON>";
				resultXml += "NA";
				resultXml += "</ON>";
				resultXml += "<OFF>";
				resultXml += "NA";
				resultXml += "</OFF>";
				resultXml += "</config>";
			}
			LogUtil.info(" step 8 " );
			DeviceManager devicemanager = new DeviceManager();
	        List<Object[]> actionsObjects = devicemanager.listAllActionsByCustomerId(device.getGateWay());
			
			List<Object[]> scenarioObjects = devicemanager.listAllScenarioActionsByCustomerId(device.getGateWay());
			
		    resultXml += "<actions>";
		    if((actionsObjects == null) && (scenarioObjects == null)){
		    	resultXml += "Not Configured";
		    	LogUtil.info(" step 9" );
				
			}
		    LogUtil.info(" step 10 " );
			for(Object[] deviceObject : actionsObjects)  {
				resultXml += "<action>";
				resultXml += "<id>";
				resultXml += deviceObject[0];
				resultXml += "</id>";
				resultXml += "<name>";
				resultXml += deviceObject[1];
				resultXml += "</name>";
				resultXml += "</action>";
			}

			for(Object[] scenarioObject : scenarioObjects)  {
				resultXml += "<action>";
				resultXml += "<id>";
				resultXml += scenarioObject[0];
				resultXml += "</id>";
				resultXml += "<name>";
				resultXml += scenarioObject[1];
				resultXml += "</name>";
				resultXml += "</action>";
			}
			resultXml += "</actions>";
			resultXml  += "</imonitor>";
			
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		LogUtil.info("listVirtualSwitchConfig OUT:: resultXml ="+(resultXml));
		return resultXml;
		
	}
	
	public String saveVirtualSwitchConfig(String xml){
		LogUtil.info("saveVirtualSwitchConfig: IN "+ xml);
		XStream stream = new XStream();
		
		String resultXml = "Success";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			 LogUtil.info("customerId="+customerId);
			 
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			 LogUtil.info("userId="+userId);

			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			 LogUtil.info("password="+password);
			
			 NodeList deviceNodes = document.getElementsByTagName("device");
				Element deviceNode = (Element) deviceNodes.item(0);
				String deviceid = XmlUtil.getCharacterDataFromElement(deviceNode);
				LogUtil.info("deviceid  " + deviceid);
				
				NodeList key1Nodes = document.getElementsByTagName("key1");
				Element key1Node = (Element) key1Nodes.item(0);
				String key1 = XmlUtil.getCharacterDataFromElement(key1Node);
				LogUtil.info("key1  " + key1);
				
				NodeList key2Nodes = document.getElementsByTagName("key2");
				Element key2Node = (Element) key2Nodes.item(0);
				String key2 = XmlUtil.getCharacterDataFromElement(key2Node);
				LogUtil.info("key2  " + key2);


			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			LogUtil.info(" usercheckresult " + result);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			LogUtil.info("step 1  " );
			KeyConfiguration key1Object = new KeyConfiguration();
			Action action1 = new Action();
			action1.setId(Long.parseLong(key1));
			
			LogUtil.info("step 2  " );
			KeyConfiguration key2Object = new KeyConfiguration();
			Action action2= new Action();
			action2.setId(Long.parseLong(key2));
			
			LogUtil.info("step 3 " );
			Device device=new Device();
			DeviceManager devicemanager= new DeviceManager();
			device=devicemanager.getDeviceByGeneratedDeviceId(deviceid);
			
			LogUtil.info("step 4 " );
			key1Object.setDevice(device);
			key1Object.setAction(action1);
			key1Object.setPosindex(0);
			key1Object.setKeyName("ON");
			//key1Object.s
			
			LogUtil.info("step 5 " );
			key2Object.setDevice(device);
			key2Object.setAction(action2);
			key2Object.setPosindex(0);
			key2Object.setKeyName("OFF");
			
			LogUtil.info("step 6  " );
			List<KeyConfiguration> keyconfigObjects=new ArrayList<KeyConfiguration>();
			keyconfigObjects.add(key1Object);
			keyconfigObjects.add(key2Object);
			
			
			
			
			LogUtil.info("step 7  " );
			ActionModel actionModel = new ActionModel(device,keyconfigObjects);
			ImonitorControllerAction virtualSwitchAction = new VirtualSwitchConfigurationAction();
			LogUtil.info("step 8  " );
			virtualSwitchAction.executeCommand(actionModel);
			
			boolean resultFromImvg = IMonitorUtil.waitForResult(virtualSwitchAction);
			
			if(resultFromImvg){
				if(virtualSwitchAction.isActionSuccess())
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
							resultXml = "<imonitor>";
							resultXml += "<status>";
							resultXml += "success";
						
						resultXml += "</status>";
						resultXml += "</imonitor>";
						}
						
						
					}
					catch(Exception e)
					{
						result = Constants.DB_FAILURE;	//DB Update Failed.
						LogUtil.error(e.getMessage());
						LogUtil.info(e.getMessage(), e);
					}
					
				}else{ 
					resultXml = "<imonitor>";
					resultXml += "<status>";
					resultXml += "failure";
				
				resultXml += "</status>";
				resultXml += "</imonitor>";		//iMVG returned a failure.
				}
			}else{
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
			
			resultXml += "</status>";
			resultXml += "</imonitor>";					//GateWay did not respond.
			}
			
			
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		LogUtil.info("saveVirtualSwitchConfig OUT::" +(resultXml));
		return resultXml;
		
	}
	
	
	
	public String createAction(String xml){
		LogUtil.info("createAction In="+xml);
		XStream stream=new XStream();
		boolean exeResult = false;
		String resultXml="Success";
		
		try{
			
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			LogUtil.info("customerId="+customerId);
			 
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			LogUtil.info("userId="+userId);

			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			LogUtil.info("password="+password);
			
			NodeList actionNameNodes = document.getElementsByTagName("actionName");
			Element actionNameNode = (Element) actionNameNodes.item(0);
			String actionName = XmlUtil.getCharacterDataFromElement(actionNameNode);
			LogUtil.info("actionName="+actionName);
			
			NodeList functionsNodes = document.getElementsByTagName("functions");
			Element functionsNode = (Element) functionsNodes.item(0);
			String functionname= XmlUtil.getCharacterDataFromElement(functionsNode);
			LogUtil.info("functionname  " + functionname);
			
			NodeList deviceorscenarioNodes = document.getElementsByTagName("deviceorscenario");
			Element deviceorscenarioNode = (Element) deviceorscenarioNodes.item(0);
			String deviceorscenario= XmlUtil.getCharacterDataFromElement(deviceorscenarioNode);
			LogUtil.info("deviceorscenario  " + deviceorscenario); 
			
			
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			LogUtil.info(" usercheckresult " + result);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			DeviceManager devicemanager= new DeviceManager();
			ScenarioManager scenarioManager = new ScenarioManager();
			Action action=new Action();
			
			action.setActionName(actionName);
			
			Functions functions = new Functions();
			long id=devicemanager.getFunctionIdBasedOnName(functionname);
			LogUtil.info(" id " + id);
			functions.setId(id);
			functions.setName(functionname);
			action.setFunctions(functions);
			
				action.setParameter(0);
			
			
			if (functionname.equalsIgnoreCase("SCENARIO")) 
			{
				
			
				Scenario scenario = scenarioManager.getScenarioWithGatewayById(Long.parseLong(deviceorscenario));
			    GatewayManager gatewayManager = new GatewayManager();
			    GateWay gateway = gatewayManager.getGateWayWithAgentByMacId(scenario.getGateWay().getMacId());
				long sid=Long.parseLong(deviceorscenario);
				
				scenario.setId(sid);
				action.setScenario(scenario);
				action.setGateWay(gateway);
				action.setConfigurationId(sid);
				action.setConfigurationName(scenario.getName());
				action.setDevice(null);
				
			}
			else
			{
				
				Device device =devicemanager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(deviceorscenario);
				action.setDevice(device);
				action.setGateWay(device.getGateWay());
			    action.setScenario(null);
			    action.setConfigurationId(device.getId());
			    action.setConfigurationName(device.getFriendlyName());
				
			}
			
			//For testing
			new DaoManager().save(action);
			
			/*LogUtil.info(" step 6" );
			ActionModel actionModel = new ActionModel(null, action);
			ImonitorControllerAction actionCreate = new ConfigureAddActions();
			LogUtil.info(" step 7" );
			actionCreate.executeCommand(actionModel);
			LogUtil.info(" step 8" );
			boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
			LogUtil.info(" resultFromImvg= " + resultFromImvg);
			resultXml = "<imonitor>";
			resultXml += "<status>";
			if(resultFromImvg){
				if(actionCreate.isActionSuccess())
				{
					new DaoManager().save(action);
					LogUtil.info(" step 9" );
					exeResult = true;
				}
				resultXml += "success";
			}else {
				resultXml += "failure";
			}
			resultXml += "</status>";
			resultXml += "</imonitor>";*/
		
		}catch (ParserConfigurationException e){
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		
		LogUtil.info("createAction out="+stream.toXML(resultXml));
		return resultXml;
	}
	
	
	public String saveAction(String xml){
		LogUtil.info("saveAction in:"+xml);
		XStream stream = new XStream();
		String resultXml="SUCCESS";
		boolean exeResult=false;
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			 LogUtil.info("customerId="+customerId);
			 
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			 LogUtil.info("userId="+userId);

			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			 LogUtil.info("password="+password);
			
			 NodeList actionidNodes = document.getElementsByTagName("actionid");
			 Element actionidNode = (Element) actionidNodes.item(0);
			 String actionid = XmlUtil.getCharacterDataFromElement(actionidNode);
			 LogUtil.info("actionid="+actionid);
			 
			 UserManager userManager = new UserManager();
				String result = userManager.isUserExists(customerId, userId, hashPassword);
				LogUtil.info(" usercheckresult " + result);
				if(result.equalsIgnoreCase("failure")){
					resultXml = "<imonitor>";
					resultXml += "<status>";
					resultXml += "failure";
					resultXml += "</status>";
					resultXml += "</imonitor>";
					return resultXml;
				}
				DeviceManager devicemanager = new DeviceManager();
				long aid=Long.parseLong(actionid);
				LogUtil.info("aid="+aid);
				Action action = devicemanager.getDeviceActionByid(aid);
				
				LogUtil.info("saved action" + stream.toXML(action));
				
				
				resultXml  = "<imonitor>";
				if(action == null){
					resultXml += "<status>";
					resultXml += "failure";
					resultXml += "</status>";
				}
				else{
					
					resultXml += "<status>";
					resultXml += "success";
					resultXml += "</status>";
					resultXml += "<action>";
					resultXml += "<id>";
					resultXml += action.getId();
					resultXml += "</id>";
					resultXml += "<name>";
					resultXml += action.getActionName();
					resultXml += "</name>";
					resultXml += "<parameter>";
					resultXml += action.getParameter();
					resultXml += "</parameter>";
					if(action.getFunctions().getCategory().equalsIgnoreCase("SCENARIO")){
						resultXml += "<configurationid>";
						resultXml += action.getConfigurationId();
						resultXml += "</configurationid>";
						resultXml += "<scenarioname>";
						resultXml += action.getScenario().getName();
						resultXml += "</scenarioname>";
						resultXml += "<functionname>";
						resultXml += "SCENARIO";
						resultXml += "</functionname>";
						
					}else{
					resultXml += "<configurationid>";
					resultXml += action.getDevice().getGeneratedDeviceId();
					resultXml += "</configurationid>";
					resultXml += "<devicename>";
					resultXml += action.getDevice().getFriendlyName();
					resultXml += "</devicename>";
					resultXml += "<functionname>";
					resultXml += action.getFunctions().getName();
					resultXml += "</functionname>";
					}
					resultXml += "<configurationname>";
					resultXml += action.getConfigurationName();
					resultXml += "</configurationname>";
					
					resultXml += "</action>";
				}	
				   resultXml += "</imonitor>";
				
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		
		LogUtil.info("saveAction out:"+(resultXml));
		return resultXml;
	}
	
	public String editAction(String xml){
		LogUtil.info("editAction in:"+xml);
		XStream stream = new XStream();
		String resultXml="SUCCESS";
		boolean exeResult=false;
		
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			 LogUtil.info("customerId="+customerId);
			 
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			 LogUtil.info("userId="+userId);

			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			 LogUtil.info("password="+password);
			
			 NodeList actionidNodes = document.getElementsByTagName("actionid");
			 Element actionidNode = (Element) actionidNodes.item(0);
			 String actionid = XmlUtil.getCharacterDataFromElement(actionidNode);
			 LogUtil.info("actionid="+actionid);
				 
				 
			NodeList actionNameNodes = document.getElementsByTagName("actionName");
			Element actionNameNode = (Element) actionNameNodes.item(0);
			String actionName = XmlUtil.getCharacterDataFromElement(actionNameNode);
			LogUtil.info("actionName=" + actionName);

			
			NodeList functionsNodes = document.getElementsByTagName("functions");
			Element functionsNode = (Element) functionsNodes.item(0);
			String functionname= XmlUtil.getCharacterDataFromElement(functionsNode);
			LogUtil.info("functionname  " + functionname);
			
			NodeList deviceorscenarioNodes = document.getElementsByTagName("deviceorscenario");
			Element deviceorscenarioNode = (Element) deviceorscenarioNodes.item(0);
			String deviceorscenario= XmlUtil.getCharacterDataFromElement(deviceorscenarioNode);
			LogUtil.info("deviceorscenario  " + deviceorscenario); 
			

			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			LogUtil.info(" usercheckresult " + result);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "failure";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			
			ScenarioManager scenarioManager = new ScenarioManager();
			DeviceManager devicemanager = new DeviceManager();
			long aid=Long.parseLong(actionid);
			
			Action action = devicemanager.getDeviceActionByid(aid);
			long functionId=action.getFunctions().getFunctionId();
			
			action.setId(aid);
			action.setActionName(actionName);
			Functions functions=new Functions();
			functions.setId(functionId);
			functions.setName(functionname);
			action.setFunctions(functions);
			
			
			if (functionname.equalsIgnoreCase("SCENARIO")) 
			{
				
				LogUtil.info(" step 3" );
				
				Scenario scenario = scenarioManager.getScenarioWithGatewayById(Long.parseLong(deviceorscenario));
			    GatewayManager gatewayManager = new GatewayManager();
			    GateWay gateway = gatewayManager.getGateWayWithAgentByMacId(scenario.getGateWay().getMacId());
				long sid=Long.parseLong(deviceorscenario);
				LogUtil.info("sid  " + sid);
				scenario.setId(sid);
				action.setScenario(scenario);
				action.setGateWay(gateway);
				action.setConfigurationId(sid);
				action.setConfigurationName(scenario.getName());
				action.setDevice(null);
				
			}
			else
			{
				LogUtil.info(" step 4" );
				Device device =devicemanager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(deviceorscenario);
				LogUtil.info(" device= " + device);
				LogUtil.info(" step 5" );
				action.setDevice(device);
				action.setGateWay(device.getGateWay());
			    action.setScenario(null);
			    action.setConfigurationId(device.getId());
			    
			    action.setConfigurationName(device.getFriendlyName());
				
			}
			 ActionModel actionModel = new ActionModel(null, action);
			 ImonitorControllerAction actionCreate = new ConfigureEditAction();
			 actionCreate.executeCommand(actionModel);
			 boolean resultFromImvg = IMonitorUtil.waitForResult(actionCreate);
			 LogUtil.info(" resultFromImvg="+ resultFromImvg);
			 if(resultFromImvg){
			  if(actionCreate.isActionSuccess())
			  {
				  new DaoManager().saveOrUpdate(action);
					exeResult = true;
				}
			}
			resultXml = "<imonitor>";
			resultXml += "<status>";
			if (resultFromImvg)
			{
				resultXml += "success";
			}
			else {
				resultXml += "failure";
			}
			resultXml += "</status>";
			resultXml += "</imonitor>";
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		LogUtil.info("editAction out:"+stream.toXML(resultXml) );
		return resultXml;
		
	}
	
	public String deleteAction(String xml){
		LogUtil.info("deleteAction in:"+xml);
		XStream stream = new XStream();
		String resultXml="SUCCESS";
		boolean exeResult=false;
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList customerNodes = document.getElementsByTagName("customer");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			 LogUtil.info("customerId="+customerId);
			 
			NodeList useridNodes = document.getElementsByTagName("userid");
			Element useridNode = (Element) useridNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(useridNode);
			 LogUtil.info("userId="+userId);

			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			 LogUtil.info("password="+password);
			
			 NodeList actionidNodes = document.getElementsByTagName("actionid");
			 Element actionidNode = (Element) actionidNodes.item(0);
			 String actionid = XmlUtil.getCharacterDataFromElement(actionidNode);
			 LogUtil.info("actionid="+actionid);
			 
			 UserManager userManager = new UserManager();
				String result = userManager.isUserExists(customerId, userId, hashPassword);
				LogUtil.info(" usercheckresult " + result);
				if(result.equalsIgnoreCase("failure")){
					resultXml = "<imonitor>";
					resultXml += "<status>";
					resultXml += "failure";
					resultXml += "</status>";
					resultXml += "</imonitor>";
					return resultXml;
				}
				DeviceManager devicemanager = new DeviceManager();
				GatewayManager gatewayManager = new GatewayManager();
				long aid=Long.parseLong(actionid);
				LogUtil.info("aid="+aid);
				Action action = devicemanager.getDeviceActionByid(aid);
				long functionId=action.getFunctions().getFunctionId();
				LogUtil.info("functionId="+functionId);
				String categoryName=action.getFunctions().getCategory();
				LogUtil.info("categoryName="+categoryName);
				
				Functions functions=new Functions();
				functions.setId(functionId);
				functions.setName(categoryName);
				action.setFunctions(functions);
				LogUtil.info("steps");
				
				if(categoryName.equalsIgnoreCase("SCENARIO")) {
					ScenarioManager manager = new ScenarioManager();
					Scenario scenario = manager.getScenarioById(action.getScenario().getId());
					LogUtil.info("step 1");
					GateWay gateway = gatewayManager.getGateWayById(scenario.getGateWay().getId());
					LogUtil.info("step 2");
					action.setGateWay(gateway);
				}else{
				Device device = devicemanager.getDevice(action.getDevice().getId());
				LogUtil.info("step 5");
				GateWay gateway = gatewayManager.getGateWayByDevice(device);
				LogUtil.info("step 4");
				action.setGateWay(gateway);
				}
				
				ActionModel actionModel = new ActionModel(null, action);
				LogUtil.info("step 1");
				ImonitorControllerAction actiondelete = new ConfigureDeleteAction();
				LogUtil.info("step 2");
				actiondelete.executeCommand(actionModel);
				
				boolean resultFromImvg = IMonitorUtil.waitForResult(actiondelete);
				 LogUtil.info("resultFromImvg="+resultFromImvg);
				
				if(actiondelete.isActionSuccess())
				  {
					  new DaoManager().delete(action);
						exeResult = true;
					}
				
				
			
			resultXml = "<imonitor>";
			resultXml += "<status>";
			if (resultFromImvg)
			{
				resultXml += "success";
			}
			else {
				resultXml += "failure";
			}
			resultXml += "</status>";
			resultXml += "</imonitor>";
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		}
		
		
		LogUtil.info("deleteAction out:"+stream.toXML(resultXml));
		return resultXml;
	}
	
	public String registerCustomerDetails(String xml){

		LogUtil.info("saveZingGatewayForMid: IN "+ xml);
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			
			//Verify Customer Name in DB
			NodeList customerNodes = document.getElementsByTagName("customerId");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			//Verify User Name in DB
			NodeList userNodes = document.getElementsByTagName("userId");
			Element userNode = (Element) userNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(userNode);
			
		
			
			
			
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			
			NodeList firstNameNodes = document.getElementsByTagName("firstName");
			Element firstNameNode = (Element) firstNameNodes.item(0);
			String firstName = XmlUtil.getCharacterDataFromElement(firstNameNode);
			
		
			
			NodeList lastNameNodes = document.getElementsByTagName("lastName");
			Element lastNameNode = (Element) lastNameNodes.item(0);
			String lastName = XmlUtil.getCharacterDataFromElement(lastNameNode);
			
			NodeList addressNodes = document.getElementsByTagName("address");
			Element addressNode = (Element) addressNodes.item(0);
			String address = XmlUtil.getCharacterDataFromElement(addressNode);
			
			
			
			NodeList cityNodes = document.getElementsByTagName("city");
			Element cityNode = (Element) cityNodes.item(0);
			String city = XmlUtil.getCharacterDataFromElement(cityNode);
			
			NodeList stateNodes = document.getElementsByTagName("state");
			Element stateNode = (Element) stateNodes.item(0);
			String state = XmlUtil.getCharacterDataFromElement(stateNode);
			
		
			
			NodeList emailNodes = document.getElementsByTagName("email");
			Element emailNode = (Element) emailNodes.item(0);
			String email = XmlUtil.getCharacterDataFromElement(emailNode);
			NodeList mobileNumberNodes = document.getElementsByTagName("mobileNumber");
			Element mobileNumberNode = (Element) mobileNumberNodes.item(0);
			String mobileNumber = XmlUtil.getCharacterDataFromElement(mobileNumberNode);
			
			NodeList otpNodes = document.getElementsByTagName("otp");
			Element otpNode = (Element) otpNodes.item(0);
			String otp = XmlUtil.getCharacterDataFromElement(otpNode);
			
			LogUtil.info("OTPP : " + otp);
			
			Customer customer =new Customer();
			User user = new User();
			CustomerServiceManager customerServiceManager = new CustomerServiceManager();
			XStream stream = new XStream();		
			customer.setCustomerId(customerId);
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setPlace(address);
			customer.setCity(city);
			customer.setProvince(state);
			customer.setEmail(email);
			customer.setMobile(mobileNumber);
			user.setUserId(userId);
			
			String hashPassword = IMonitorUtil.getHashPassword(password);
			user.setPassword(hashPassword);
				
			String customerXml = stream.toXML(customer);
			String userXml = stream.toXML(user);
			
			CustomerManager customerManager = new CustomerManager();
			OTPForSelfRegistration fromDb = customerManager.getOTPObjectByMacId(customerId);
			LogUtil.info("fromDb Object" + new XStream().toXML(fromDb));
			
			if (fromDb!=null)
			{
				if (!(fromDb.getOtp().equals(otp)))
				{
					resultXml  = "<imonitor>";
					resultXml  += "<status>";
					resultXml += "OTP is invalid , Registration Failed";
					resultXml  += "</status>";
					resultXml += "</imonitor>";
					return resultXml;
				}
			}
			
			String resultAfterSving = customerServiceManager.saveCustomerDetailsFromMid(customerXml, userXml);
			LogUtil.info("resultAfterSving customer " + resultAfterSving);
			resultXml  = "<imonitor>";
			resultXml  += "<status>";
			if (resultAfterSving.equals("failure")) 
			{
				resultXml += "Failure The following customerId " +customer.getCustomerId()+ " already Exists";
			}
			
			
			else
			{
				//resultXml += "Success";
				resultXml += "Registration Successful";
				
				//Send Welcome notifications
				WelComeNotifications welcome = new WelComeNotifications(customer);
				Thread t = new Thread(welcome);
				t.start();
				
				
				
			}
			resultXml  += "</status>";
			resultXml += "</imonitor>";
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} 
		LogUtil.info("saveZingGatewayForMid OUT: " + resultXml);
		return resultXml;

		}

	public String registerGatewayWithCustomer(String xml){

		LogUtil.info("saveZingGatewayForMid: IN "+ xml);
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList macIdNodes = document.getElementsByTagName("macId");
			Element macIdNode = (Element) macIdNodes.item(0);
			String macId = XmlUtil.getCharacterDataFromElement(macIdNode);
			LogUtil.info("Step 1");
					//Verify Customer Name in DB
			NodeList customerNodes = document.getElementsByTagName("customerId");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			//Verify User Name in DB
			NodeList userNodes = document.getElementsByTagName("userId");
			Element userNode = (Element) userNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(userNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			LogUtil.info("Step 2");
			//Verify gateway macId in the saved list of CMS
	        GatewayManager gatewayManager=new GatewayManager();
			boolean macIdExist = gatewayManager.verifyGetwayMacIdFromSavedList(macId);
			LogUtil.info("boolean result: " + macIdExist);
			LogUtil.info("Step 3");
			if(!macIdExist) {
				
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml = "Failure The following macId "+macId+ " is invalid.";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				LogUtil.info("macId Error : " + resultXml);
				return resultXml;
			}
			UserManager userManager = new UserManager();
			LogUtil.info("Step 4");
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "Failure The following customerId " +customerId+ " is invalid";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
			LogUtil.info("Step 6");
			
			CustomerManager customerManager=new CustomerManager();

	        GateWay gateway = gatewayManager.getGateWayByMacId(macId);
	        LogUtil.info("Step 7");
	        if(gateway != null) {
	        	if(gateway.getCustomer() != null) {
	            	resultXml = "<imonitor>";
	    			resultXml += "<status>";
	    			resultXml += "Failure The following customerId " +customerId+ " is invalid";
	    			resultXml += "</status>";
	    			resultXml += "</imonitor>";
	    			return resultXml;
	            	
	            }else{
	            	resultXml = "<imonitor>";
	    			resultXml += "<status>";
	    			resultXml += "Failure The following macId is invalid";
	    			resultXml += "</status>";
	    			resultXml += "</imonitor>";
	    			return resultXml;
	            }
	        }
	        LogUtil.info("Step 8");
	        gateway = new GateWay();
			gateway.setMacId(macId);
			Customer customer = customerManager.getCustomerByCustomerId(customerId);
			LogUtil.info("Step 8a");
			gateway.setCustomer(customer);
			LogUtil.info("Step 8b");
			gateway.setDelayHome("0");
			gateway.setDelayStay("0");
			gateway.setDelayAway("0");
			gateway.setCurrentMode("0");
			gateway.setMaintenancemode("0");
			Date date=new Date();
			gateway.setEntryDate(date);
			LogUtil.info("Step 8c");
		    StatusManager statusManager = new StatusManager();
		    LogUtil.info("Step 8d");
			Status status = statusManager.getStatusByName(Constants.READY_TO_REGISTER);
			gateway.setStatus(status);
			LogUtil.info("Step 8e");
			AgentManager agentManager = new AgentManager();
			Agent agent = agentManager.getAgentForGateway();
			gateway.setAgent(agent);
			LogUtil.info("Step 8f");
			boolean saveGateway = gatewayManager.saveGateWay(gateway);		
			LogUtil.info("Step 9");
			
			
			
				
		
			
			
			resultXml  = "<imonitor>";
			resultXml  += "<status>";
			if (saveGateway) 
			{
				resultXml += "Registration Successful";
				
			}
			
			else
			{
				resultXml += "Failure The following macId "+gateway.getMacId()+ " is invalid.";
				
			}
			resultXml  += "</status>";
			resultXml += "</imonitor>";
			LogUtil.info("Step 10");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} 
		LogUtil.info("saveZingGatewayForMid OUT: " + resultXml);
		return resultXml;

		}
	public String unregisterGatewayRequest(String xml){

		LogUtil.info("saveZingGatewayForMid: IN "+ xml);
		String resultXml = "";
		try {
			Document document = XmlUtil.getDocument(xml);
			
			NodeList macIdNodes = document.getElementsByTagName("macId");
			Element macIdNode = (Element) macIdNodes.item(0);
			String macId = XmlUtil.getCharacterDataFromElement(macIdNode);

					//Verify Customer Name in DB
			NodeList customerNodes = document.getElementsByTagName("customerId");
			Element customerNode = (Element) customerNodes.item(0);
			String customerId = XmlUtil.getCharacterDataFromElement(customerNode);
			
			//Verify User Name in DB
			NodeList userNodes = document.getElementsByTagName("userId");
			Element userNode = (Element) userNodes.item(0);
			String userId = XmlUtil.getCharacterDataFromElement(userNode);
			
			NodeList passwordNodes = document.getElementsByTagName("password");
			Element passwordNode = (Element) passwordNodes.item(0);
			String password = XmlUtil.getCharacterDataFromElement(passwordNode);
			String hashPassword = IMonitorUtil.getHashPassword(password);
			
			//Verify gateway macId in the saved list of CMS
	        GatewayManager gatewayManager=new GatewayManager();
			boolean macIdExist = gatewayManager.verifyGetwayMacIdFromSavedList(macId);
			LogUtil.info("boolean result: " + macIdExist);
			if(!macIdExist) {
				
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml = "Failure The following macId "+macId+ " is invalid.";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				LogUtil.info("macId Error : " + resultXml);
				return resultXml;
			}
			UserManager userManager = new UserManager();
			String result = userManager.isUserExists(customerId, userId, hashPassword);
			if(result.equalsIgnoreCase("failure")){
				resultXml = "<imonitor>";
				resultXml += "<status>";
				resultXml += "Failure The following customerId " +customerId+ " is invalid";
				resultXml += "</status>";
				resultXml += "</imonitor>";
				return resultXml;
			}
		
			
			CustomerManager customerManager=new CustomerManager();

	        GateWay gateway = gatewayManager.getGateWayByMacId(macId);
	        
	        if(gateway != null) {
	        	if(gateway.getCustomer() != null) {
	            	resultXml = "<imonitor>";
	    			resultXml += "<status>";
	    			resultXml += "Failure The following customerId " +customerId+ " is invalid";
	    			resultXml += "</status>";
	    			resultXml += "</imonitor>";
	    			return resultXml;
	            	
	            }else{
	            	resultXml = "<imonitor>";
	    			resultXml += "<status>";
	    			resultXml += "Failure The following macId is invalid";
	    			resultXml += "</status>";
	    			resultXml += "</imonitor>";
	    			return resultXml;
	            }
	        }
	        
	        gateway = new GateWay();
			gateway.setMacId(macId);
			Customer customer = customerManager.getCustomerBycustomerId(customerId);
			gateway.setCustomer(customer);
			gateway.setDelayHome("0");
			gateway.setDelayStay("0");
			gateway.setDelayAway("0");
			gateway.setCurrentMode("0");
			gateway.setMaintenancemode("0");
			Date date=new Date();
			gateway.setEntryDate(date);
		    StatusManager statusManager = new StatusManager();
			Status status = statusManager.getStatusByName(Constants.READY_TO_REGISTER);
			gateway.setStatus(status);
			
			AgentManager agentManager = new AgentManager();
			Agent agent = agentManager.getAgentForGateway();
			gateway.setAgent(agent);
			
			boolean saveGateway = gatewayManager.saveGateWay(gateway);		
			
			
			
			
				
			
			
			
			resultXml  = "<imonitor>";
			resultXml  += "<status>";
			if (saveGateway) 
			{
				resultXml += "Registration Successful";
				resultXml += "Failure The following customerId " +customer.getCustomerId()+ " already Exists";
			}
			
			else
			{
				resultXml += "Failure The following macId "+gateway.getMacId()+ " is invalid.";
				
			}
			resultXml  += "</status>";
			resultXml += "</imonitor>";
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			resultXml  = "Couldn't parse the input " + e.getMessage();
		} catch (SAXException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			resultXml = "Couldn't parse the input " + e.getMessage();
		} 
		LogUtil.info("saveZingGatewayForMid OUT: " + resultXml);
		return resultXml;

		}

}