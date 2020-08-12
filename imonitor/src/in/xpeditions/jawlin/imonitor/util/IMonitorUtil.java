/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.util;


import in.xpeditions.jawlin.imonitor.cms.communicator.IMonitorControllerCommunicator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Coladi
 *
 */
public class IMonitorUtil {
	
	private static Service service = new Service();
	public static String NEW_LINE = "\r\n";
	public static String convertToXml(Map<String,String> commands){
		String message;
		String xml=new String();
		String str;
		Object value;
		Set<String> keys=commands.keySet();
		Iterator<String> itr=keys.iterator();
		while (itr.hasNext()){
			str=(String)itr.next();
			value=commands.get(str);
			message="	  <field>"+	
		      "   <key>"+str+"</key>" +
		      "   <value>"+value+"</value>" +
		      "	  </field>";
			xml=xml.concat(message);
		}
		xml="<xpeditions>"+xml+"</xpeditions>";
		return xml;
	}
	public static Map<String,String> convertToMapFromXml(String xml) throws ParserConfigurationException, SAXException, IOException{
		
		Map<String,String> map=new HashMap<String, String>();
			Document doc = XmlUtil.getDocument(xml);
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
	public static String convertToCommand(Map<String,String> commands){
		String str;
		Object value;
		Set<String> keys=commands.keySet();
		Iterator<String> itr=keys.iterator();
		String message=new String();
		while(itr.hasNext()){
			str=(String)itr.next();
			value=(String)commands.get(str);
			message+=str+"="+value+"="+System.getProperty("line.separator");
		}
		return message;
	}
	public static Map<String,String> convertToMapFromCommand(String command){
		Map<String,String> map=new HashMap<String, String>();
		String[]items;
		String[] eachItems;
		items=command.split(System.getProperty("line.separator"));
		for(int i=0;i<items.length;i++){
			eachItems=items[i].split("=");
			map.put(eachItems[0], eachItems[1]);
		}
		return map;
	}
	
	
	public static String convertToXml(Object object) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String xmlString = "";
		xmlString += "<xpeditions>";
		Class<?> c = object.getClass();
		xmlString += "<classname>";
		xmlString += c.getName();
		xmlString += "</classname>";
		
        Field[] declaredFields = c.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
			Field field = declaredFields[i];
			String fieldName = field.getName();
			String getterMethod = IMonitorUtil.extractGetterMethod(fieldName);
			Method declaredGetterMethod;
				declaredGetterMethod = c.getDeclaredMethod(getterMethod);
				Object getterReturnObject = declaredGetterMethod.invoke(object);
				if(getterReturnObject != null){
					xmlString += "<filed>";
					xmlString += "<key>";
					xmlString += fieldName;
					xmlString += "</key>";
					xmlString += "<value>";
					String getterReturnValue = "";
					if(IMonitorUtil.isPrimitiveType(field)){
						String getterReturnObjectToString = getterReturnObject.toString();
						getterReturnValue = getterReturnObjectToString;
					} else{
						getterReturnValue = IMonitorUtil.convertToXml(getterReturnObject);
					}
					xmlString += getterReturnValue;
					xmlString += "</value>";
					xmlString += "</filed>";
				}
		}
		xmlString += "</xpeditions>";
		return xmlString;
	}
	
	public static String extractGetterMethod(String fieldName){
		String getterMethod = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		return getterMethod;
	}
	
	public static boolean isPrimitiveType(Field field){
		Class<?> type = field.getType();
		return type.isPrimitive() || type.isInstance(new String(""));
	}
	
	public static String extractSetterMethod(String fieldName){
		String setterMethod = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		return setterMethod;
	}
	
	public static Object convertXmlToObject(String xml) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, DOMException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		Document document = XmlUtil.getDocument(xml);
		Node rootNode = document.getFirstChild();
		Object object = IMonitorUtil.convertNodeToObject(rootNode);
		return object;
	}
	
	public static Object convertNodeToObject(Node node) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, DOMException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		Node classNode = XmlUtil.getChildElementByTagName(node, "classname");
		String className = classNode.getTextContent();
		Class<?> forClassName = Class.forName(className);
		Object objectInstance = forClassName.newInstance();
		
		Class<?> c = objectInstance.getClass();
		Map<String, Class<?>> fieldTypeMap = new HashMap<String, Class<?>>();
        Field[] declaredFields = c.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
			Field field = declaredFields[i];
			fieldTypeMap.put(field.getName(), field.getType());
		}
		List<Node> fieldNodes = XmlUtil.getChildElementsByTagName(node, "field");
		for (Node fieldNode : fieldNodes) {
			Node fieldNameNode = XmlUtil.getChildElementByTagName(fieldNode, "key");
			Node fieldValueNode = XmlUtil.getChildElementByTagName(fieldNode, "value");
			String methodName = fieldNameNode.getTextContent();
			String setterMethod = IMonitorUtil.extractSetterMethod(methodName);
			Class<?> fieldClass = fieldTypeMap.get(methodName);
			Method declaredMethod = c.getDeclaredMethod(setterMethod, fieldClass);
			Object castedFieldValue = null;
			Node anotherEntityNode = XmlUtil.getChildElementByTagName(fieldValueNode, "xpeditions");
			if(anotherEntityNode != null){
				castedFieldValue = IMonitorUtil.convertNodeToObject(anotherEntityNode);
			}else{
				String setterValue = fieldValueNode.getTextContent();
				castedFieldValue = IMonitorUtil.castTo(fieldClass, setterValue);
			}
			declaredMethod.invoke(objectInstance, castedFieldValue);
		}
		return objectInstance;
	}
	
	public static Object castTo(Class<?> fieldClass, String setterTextValue) {
//		Here we should handle all the primitive data type including date.
		if(fieldClass.getName().equals("long")){
			return Long.parseLong(setterTextValue);
		}
		return fieldClass.cast(setterTextValue);
	}
	public static String convertToStringFromQueue(Queue<KeyValuePair> queue) {
		String command=new String();
		for (KeyValuePair keyValuePair : queue) {
			command=command+keyValuePair.getKey()+"="+keyValuePair.getValue()+IMonitorUtil.NEW_LINE;
		}
		return command;
	}
	
	public static String sendToController(String serviceName,String method, String... params) throws Exception{
		String address = IMonitorControllerCommunicator.getControllerAddress();
		String port = IMonitorControllerCommunicator.getControllerPort();
		String protocol = IMonitorControllerCommunicator.getControllerProtocol();
		String endpoint = protocol + "://" + address + ":" + port
		+ "/imonitorcontroller/services/"+serviceName;
		//Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName(method));
		String result = (String) call.invoke(params);
		return result;
	}
	public static String sendToController(String serviceName,String method) throws Exception{
		String address = IMonitorControllerCommunicator.getControllerAddress();
		String port = IMonitorControllerCommunicator.getControllerPort();
		String protocol = IMonitorControllerCommunicator.getControllerProtocol();
		String endpoint = protocol + "://" + address + ":" + port
		+ "/imonitorcontroller/services/"+serviceName;
		//Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName(method));
		String deviceXml = (String) call.invoke(new Object[] {});
		return deviceXml;
	}
	public static String getHashPassword(String password) {
		String strHashPwd = ""; 
		try {
			strHashPwd = Hash.getHashText(password,"MD5");
			//strHashPwd = hash.
		} catch (NoSuchAlgorithmException e) {
			//e.printStackTrace();
		}
		return strHashPwd;
	}
	
	public static String formatMessage(ActionSupport actSupport, String message) 
	{
		String result = "A generic UI Error has occured."; 
		String args[] = null;
		String messageId = message;
		try 
		{
			if(message.contains(Constants.MESSAGE_DELIMITER_1))
			{
				String temp[] = message.split(Constants.REGEX_ESCAPE+Constants.MESSAGE_DELIMITER_1);
				messageId = temp[0];
				args = temp[1].split(Constants.REGEX_ESCAPE+Constants.MESSAGE_DELIMITER_2);
				result = actSupport.getText(messageId, args);
			}
			else
			{
				result = actSupport.getText(messageId);
			}
		} 
		catch (Exception e) {LogUtil.info("ERROR",e);}
		return result;
	}
	
	public static String formatFailureMessage(ActionSupport actSupport, String message) 
	{
		return Constants.MSG_FAILURE + formatMessage(actSupport, message);
	}
}
