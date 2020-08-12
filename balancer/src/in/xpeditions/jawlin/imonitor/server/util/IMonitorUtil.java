/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.util;



import in.xpeditions.jawlin.imonitor.server.communicator.ImonitorControllerCommunicator;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;




/**
 * @author Coladi
 * 
 */
public class IMonitorUtil {
	
	public static String NEW_LINE = "\r\n";
	private static Map<String, UpdatorModel<?>> eventUpdators = new HashMap<String, UpdatorModel<?>>();
	private static Map<String, UpdatorModel<?>> alertUpdators = new HashMap<String, UpdatorModel<?>>();
	
	/**
	 * @param commands - commands in hash map
	 * @return - the commands in string format.
	 */
	public static String convertToStringFromQueue(Queue<KeyValuePair> queue) {
		String command="";
		for (KeyValuePair keyValuePair : queue) {
			command += keyValuePair.getKey();
			String value = keyValuePair.getValue();
			if(value != null){
				command += "=";
				command += value;
			}
			command += IMonitorUtil.NEW_LINE;
		}
		return command;
	}

	/**
	 * @param commands - commands in string format.
	 * @return - commands in Map.
	 * @throws IOException 
	 * @throws IOException 
	 */
	public static Queue<KeyValuePair> commandToQueue(String command ) throws IOException {
		
		Properties properties = new Properties();
		
		Reader stringReader = new StringReader(command);
		properties.load(stringReader);
		Set<Object> keySet = properties.keySet();
		Queue<KeyValuePair> queue=new LinkedList<KeyValuePair>();
		for (Object object : keySet) {
			String key=(String)object;
			KeyValuePair keyValuePair=new KeyValuePair(key,(String)properties.get(key));
			queue.add(keyValuePair);
		}
		return queue;
	}
	public static Map<String, String> extractCommandsFromXml(String commandInXml) throws ParserConfigurationException, SAXException, IOException {
		Map<String,String> map=new HashMap<String, String>();
		Document doc = XmlUtil.getDocument(commandInXml);
		NodeList nodes = doc.getElementsByTagName("field");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			
			NodeList keys=element.getElementsByTagName("key");
			Element line1=(Element)keys.item(0);
			String key=XmlUtil.getCharacterDataFromElement(line1);
			NodeList values = element.getElementsByTagName("value");
			Element line2 = (Element) values.item(0);
			String value=XmlUtil.getCharacterDataFromElement(line2);
			map.put(key, value);
		}
	return map;
	}

	public static Map<String, UpdatorModel<?>> getEventUpdators() {
		return eventUpdators;
	}
					
	@SuppressWarnings("unchecked")
	public static Queue<KeyValuePair> updateEvent(Queue<KeyValuePair> events) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		UpdatorModel<?> eventUpdatorModel = IMonitorUtil.eventUpdators.get(IMonitorUtil.commandId(events, Constants.CMD_ID));
		Class<?> className = eventUpdatorModel.getClassName();
		Method method = eventUpdatorModel.getMethod();
		if(className == null || method == null){
			return new LinkedList<KeyValuePair>();
		}
		Queue<KeyValuePair> results = (Queue<KeyValuePair>) method.invoke(className.newInstance(), events);
		return results;
	}

	@SuppressWarnings("unchecked")
	public static Queue<KeyValuePair> updateAlert(Queue<KeyValuePair> events) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		UpdatorModel<?> alertUpdatorModel = IMonitorUtil.alertUpdators.get(IMonitorUtil.commandId(events, Constants.ALERT_EVENT));
		Class<?> className = alertUpdatorModel.getClassName();
		Method method = alertUpdatorModel.getMethod();
		if(className == null || method == null){
			return new LinkedList<KeyValuePair>();
		}
		Queue<KeyValuePair> results = (Queue<KeyValuePair>) method.invoke(className.newInstance(), events);
		return results;
	}

	public static Queue<KeyValuePair> extractCommandsQueueFromXml(
			String commandInXml) throws ParserConfigurationException, SAXException, IOException {
		Queue<KeyValuePair> queue=new LinkedList<KeyValuePair>();
		Document doc = XmlUtil.getDocument(commandInXml);
		NodeList nodes = doc.getElementsByTagName("field");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			String key = null;
			String value = null;
			NodeList keys=element.getElementsByTagName("key");
			if(keys != null){
				Element line1=(Element)keys.item(0);
				if(line1 != null){
					key=XmlUtil.getCharacterDataFromElement(line1);
				}
			}
			NodeList values = element.getElementsByTagName("value");
			if(values != null){
				Element line2 = (Element) values.item(0);
				if(line2 != null){
					value=XmlUtil.getCharacterDataFromElement(line2);
				}
			}
			queue.add(new KeyValuePair(key, value));
		}
		return queue;
	}
	
	public static String convertQueueIntoXml(Queue<KeyValuePair> messages) {
		String message = "<xpeditions>";
		for (KeyValuePair keyValuePair : messages) {
			String key = keyValuePair.getKey();
			String value = keyValuePair.getValue();
			
			String msg = "<field>"+	
		      "<key>"+key+"</key>" +
		      "<value>"+value+"</value>" +
		      "</field>";
			message += msg;
		}
		message += "</xpeditions>";
		return message ;
	}

	public static String appendSignature(String commandConverted) {
		String createSignature = Hash.createSignature(commandConverted);
		commandConverted += Constants.SIGNATURE;
		commandConverted += "=";
		commandConverted += createSignature;
		commandConverted += IMonitorUtil.NEW_LINE;
		return commandConverted;
	}
	
	public static boolean checkMessageSignature(String message){
		
		return true;
	}

	public static Map<String, UpdatorModel<?>> getAlertUpdators() {
		return IMonitorUtil.alertUpdators;
	}
	public static String sendToController(String xml, String serviceName,
			String methodName) throws ServiceException, MalformedURLException,
			RemoteException {
		String address = ImonitorControllerCommunicator.getControllerAddress();
		String port = ImonitorControllerCommunicator.getControllerPort();
		String protocol = ImonitorControllerCommunicator.getControllerProtocol();
		String endpoint = protocol + "://" + address + ":" + port
				+ "/imonitorcontroller/services/" + serviceName;
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName(methodName));
		String result = (String) call.invoke(new Object[] { xml });
		return result;
	}

	public static String generateTransactionId() {
		return Long.toHexString(System.currentTimeMillis());
	}

	public static String commandId(Queue<KeyValuePair> events,String commandId) {
		for (KeyValuePair keyValuePair : events) {
			if(keyValuePair.getKey().equals(commandId)){
				return keyValuePair.getValue();
			}
		}
		return null;
	}

	public static String createSignature(String message) {
		// TODO Auto-generated method stub
		return null;
	}
}
