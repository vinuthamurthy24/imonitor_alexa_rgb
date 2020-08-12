/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.listener;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ActionTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AlertTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.StatusManager;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.ControlModel;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.UpdatorModel;
import in.xpeditions.jawlin.imonitor.util.XmlUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * @author Coladi
 *
 */
public class XpeditionsServletListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
//		Initializing properties.
		Properties properties = new Properties();
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("imvgservercontroller.properties"); 
			properties.load(inputStream);
			Map<String, String> propertiesMap = new HashMap<String, String>();
			propertiesMap.put(Constants.HIBERNATE_CONFIG_FILE, properties.getProperty(Constants.HIBERNATE_CONFIG_FILE));
			propertiesMap.put(Constants.DISCOVERY_DURATION, properties.getProperty(Constants.DISCOVERY_DURATION));
			propertiesMap.put(Constants.TIMOUT_DURATION, properties.getProperty(Constants.TIMOUT_DURATION));
			//sumit start
			propertiesMap.put(Constants.MID_TIMEOUT_DURATION, properties.getProperty(Constants.MID_TIMEOUT_DURATION));
			propertiesMap.put(Constants.UPLOADED_LOC_IMAGES_DIR , properties.getProperty(Constants.UPLOADED_LOC_IMAGES_DIR));
			propertiesMap.put(Constants.DEVICE_REMOVE_TIMEOUT_DURATION, properties.getProperty(Constants.DEVICE_REMOVE_TIMEOUT_DURATION));
			propertiesMap.put(Constants.SMS_ALERT_NOTIFIER, properties.getProperty(Constants.SMS_ALERT_NOTIFIER));
			propertiesMap.put(Constants.EMAIL_ALERT_NOTIFIER, properties.getProperty(Constants.EMAIL_ALERT_NOTIFIER));
			propertiesMap.put(Constants.VOICE_ALERT_NOTIFIER, properties.getProperty(Constants.VOICE_ALERT_NOTIFIER));
			//sumit end
			propertiesMap.put(Constants.AC_WAIT_DURATION, properties.getProperty(Constants.AC_WAIT_DURATION));
			propertiesMap.put(Constants.FILE_LOCATION, properties.getProperty(Constants.FILE_LOCATION));
			propertiesMap.put(Constants.IMAGES_DIR, properties.getProperty(Constants.IMAGES_DIR));
			propertiesMap.put(Constants.STREAMS_DIR, properties.getProperty(Constants.STREAMS_DIR));
			propertiesMap.put(Constants.IMAGE_FILE_EXTENSION, properties.getProperty(Constants.IMAGE_FILE_EXTENSION));
			propertiesMap.put(Constants.POLLING_TIME, properties.getProperty(Constants.POLLING_TIME));
			propertiesMap.put(Constants.LOG_DIR, properties.getProperty(Constants.LOG_DIR));
			propertiesMap.put(Constants.MAIL_SMTP_HOST, properties.getProperty(Constants.MAIL_SMTP_HOST));
			propertiesMap.put(Constants.MAIL_SMTP_STARTTLS_ENABLE, properties.getProperty(Constants.MAIL_SMTP_STARTTLS_ENABLE));
			propertiesMap.put(Constants.MAIL_SMTP_AUTH, properties.getProperty(Constants.MAIL_SMTP_AUTH));
			propertiesMap.put(Constants.MAIL_SMTP_PORT, properties.getProperty(Constants.MAIL_SMTP_PORT));
			propertiesMap.put(Constants.INTERNET_ADDRESS, properties.getProperty(Constants.INTERNET_ADDRESS));
			propertiesMap.put(Constants.MAIL_USERNAME, properties.getProperty(Constants.MAIL_USERNAME));
			propertiesMap.put(Constants.MAIL_PASSWORD, properties.getProperty(Constants.MAIL_PASSWORD));
			propertiesMap.put("UPLOADED_FILE_PATH", properties.getProperty("UPLOADED_FILE_PATH"));
			propertiesMap.put(Constants.SMS_ALERT_NOTIFIER_ADDRESS, properties.getProperty(Constants.SMS_ALERT_NOTIFIER_ADDRESS));
			propertiesMap.put(Constants.EMAIL_ALERT_NOTIFIER_ADDRESS,properties.getProperty(Constants.EMAIL_ALERT_NOTIFIER_ADDRESS));
			propertiesMap.put(Constants.imvg_upload_context_path,properties.getProperty(Constants.imvg_upload_context_path));
			propertiesMap.put(Constants.SMS_ALERT_NOTIFIER_ADDRESS_1, properties.getProperty(Constants.SMS_ALERT_NOTIFIER_ADDRESS_1));
			propertiesMap.put(Constants.SI_DIR, properties.getProperty(Constants.SI_DIR));
		    propertiesMap.put(Constants.ALEXA_CLIENT_ID, properties.getProperty(Constants.ALEXA_CLIENT_ID));
		    propertiesMap.put(Constants.ALEXA_SECRET, properties.getProperty(Constants.ALEXA_SECRET));
			propertiesMap.put(Constants.ALEXA_EVENT_POINT, properties.getProperty(Constants.ALEXA_EVENT_POINT));
			propertiesMap.put(Constants.ALEXA_TOKEN_END_POINT, properties.getProperty(Constants.ALEXA_TOKEN_END_POINT));
			propertiesMap.put(Constants.ANDROID_URL, properties.getProperty(Constants.ANDROID_URL));
			propertiesMap.put(Constants.ANDROID_AUTH_KEY, properties.getProperty(Constants.ANDROID_AUTH_KEY));
			propertiesMap.put(Constants.APN_CERTIFICATE_NAME, properties.getProperty(Constants.APN_CERTIFICATE_NAME));
			propertiesMap.put(Constants.APN_PASSWORD, properties.getProperty(Constants.APN_PASSWORD));
		    ControllerProperties.setProperties(propertiesMap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Properties Msgproperties = new Properties();
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("imonitormessage.properties"); 
			Msgproperties.load(inputStream);
			//Map<String, String> msgproperties = new HashMap<String, String>();
			//msgproperties.put("msg001rov", Msgproperties.getProperty("msg001rov"));
			ControllerProperties.setMsgproperties(Msgproperties);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		Reading Control Command Executors.
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("controls.xml"); 
		try {
			Document document = XmlUtil.getDocument(inputStream);
			NodeList commandElements = document.getElementsByTagName(Constants.IMONITOR_COTROL_STRING);
			int commandsLength = commandElements.getLength();
			for (int i = 0; i < commandsLength; i++) {
				Node commandNode = commandElements.item(i);
				Element idNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_ID);
				Element classNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_CLASS);
				Element methodNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_METHOD);
				String id = XmlUtil.getCharacterDataFromElement(idNode);
				String className = XmlUtil.getCharacterDataFromElement(classNode);
				String methodName = XmlUtil.getCharacterDataFromElement(methodNode);
				Class<?> classRep = Class.forName(className);
				Method method = classRep.getDeclaredMethod(methodName, ControlModel.class);
				UpdatorModel<?> model = new UpdatorModel<Object>(classRep, method);
				IMonitorUtil.getControlUpdators().put(id, model);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
//		Reading notifications.
		inputStream = this.getClass().getClassLoader().getResourceAsStream("notificationls.xml"); 
		try {
			Document document = XmlUtil.getDocument(inputStream);
			NodeList commandElements = document.getElementsByTagName(Constants.IMONITOR_NOTIFICATION_STRING);
			int commandsLength = commandElements.getLength();
			for (int i = 0; i < commandsLength; i++) {
				Node commandNode = commandElements.item(i);
				Element idNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_ID);
				Element classNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_CLASS);
				Element methodNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_METHOD);
				String id = XmlUtil.getCharacterDataFromElement(idNode);
				String className = XmlUtil.getCharacterDataFromElement(classNode);
				String methodName = XmlUtil.getCharacterDataFromElement(methodNode);
				Class<?> classRep = Class.forName(className);
				Method method;
				if(id.equalsIgnoreCase("E-Mail"))
				{
					method = classRep.getDeclaredMethod(methodName,String.class, String[].class, Date.class, String.class);
				}
				
				else
				{
					method = classRep.getDeclaredMethod(methodName,String.class, String[].class, Date.class, Customer.class);
				}
				
				//Method method = classRep.getDeclaredMethod(methodName,String.class, String[].class);
				UpdatorModel<?> model = new UpdatorModel<Object>(classRep, method);
				IMonitorUtil.getNotifierUpdators().put(id, model);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
//		Reading actions.
		inputStream = this.getClass().getClassLoader().getResourceAsStream("actions.xml"); 
		try {
			Document document = XmlUtil.getDocument(inputStream);
			NodeList commandElements = document.getElementsByTagName(Constants.IMONITOR_ACTION_STRING);
			int commandsLength = commandElements.getLength();
			for (int i = 0; i < commandsLength; i++) {
				Node commandNode = commandElements.item(i);
				Element idNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_ID);
				Element classNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_CLASS);
				String id = XmlUtil.getCharacterDataFromElement(idNode);
				String className = XmlUtil.getCharacterDataFromElement(classNode);
				Class<?> classRep = Class.forName(className);
				UpdatorModel<?> model = new UpdatorModel<Object>(classRep);
				IMonitorUtil.getActionUpdators().put(id, model);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
//		Listing all action types and keep it in one Map
		ActionTypeManager actionTypeManager = new ActionTypeManager();
		Map<String, ActionType> actionTypesInMap = actionTypeManager.listAllActionTypesInMap();
		IMonitorUtil.setActionTypes(actionTypesInMap);
		
//		Listing all alert types and keep it in one Map
		AlertTypeManager alertTypeManager = new AlertTypeManager();
		Map<String, AlertType> alertTypesInMap = alertTypeManager.listAllAlertTypesInMap();
		IMonitorUtil.setAlertTypes(alertTypesInMap);
		
//		Listing all alert types and keep it in one Map
		StatusManager statusManager = new StatusManager();
		Map<String, Status> statusInMap = statusManager.listStatuseInMap();
		IMonitorUtil.setStatuses(statusInMap);
		
/*//	Initializing all devices & gateways to Offline.
		DBConnection dbc = null;
		AlertType downAlert = IMonitorUtil.getAlertTypes().get(Constants.DEVICE_DOWN);
		if(downAlert == null){
//			We have not installed the database till now.
			return;
		}
		Status offlineStatus = IMonitorUtil.getStatuses().get(Constants.OFFLINE);
		Status onlineStatus = IMonitorUtil.getStatuses().get(Constants.ONLINE);
		String hql = "update device d set d.lastAlert = " + downAlert.getId();
		String hqlG = "update gateway g set g.status = " + offlineStatus.getId() + " where g.status="  +onlineStatus.getId();
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(hql);
			Query qG = session.createSQLQuery(hqlG);
			Transaction tx = session.beginTransaction();
			q.executeUpdate();
			qG.executeUpdate();
			tx.commit();
		} catch (Exception ex) {
			LogUtil.error("Erro when setting the device and gateway into offline !!! " + ex.getMessage());
		} finally {
			dbc.closeConnection();
		}*/
		
//		WatchDog watchDog = new WatchDog();
//		Thread  thread = new Thread(watchDog);
//		thread.start();
	}

}
