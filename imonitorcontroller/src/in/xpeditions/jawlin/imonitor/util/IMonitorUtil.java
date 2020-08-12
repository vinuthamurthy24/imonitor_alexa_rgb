/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.util;


import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class IMonitorUtil {
	public static String NEW_LINE = "\r\n";
	private static Map<String, UpdatorModel<?>> controlUpdators = new HashMap<String, UpdatorModel<?>>();
	private static Map<String, UpdatorModel<?>> notifierUpdators = new HashMap<String, UpdatorModel<?>>();
	private static Map<String, UpdatorModel<?>> actionUpdators = new HashMap<String, UpdatorModel<?>>();
	private static Map<String, AlertType> alertTypes;
	private static Map<String, ActionType> actionTypes;
	private static Map<String, Status> statuses;
	private static final char[] hexChar = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    public static String unicodeEscape(String s) 
    {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) 
		{
		    char c = s.charAt(i);
		    if ((c >> 7) > 0) {
			sb.append("\\u");
			sb.append(hexChar[(c >> 12) & 0xF]); // append the hex character for the left-most 4-bits
			sb.append(hexChar[(c >> 8) & 0xF]);  // hex for the second group of 4-bits from the left
			sb.append(hexChar[(c >> 4) & 0xF]);  // hex for the third group
			sb.append(hexChar[c & 0xF]);         // hex for the last group, e.g., the right most 4-bits
		    }
		    else {
			sb.append(c);
		    }
		}

		String ret;
		if(sb.length() > Constants.MAX_IMVG_UNICODE_STRING_LEN)
			ret = sb.substring(0, Constants.MAX_IMVG_UNICODE_STRING_LEN);
		else
			ret = sb.toString();
		
		return ret;
    }
	
	public static Queue<KeyValuePair> extractCommandsQueueFromXml(
			String commandInXml) throws ParserConfigurationException, SAXException, IOException {
		Queue<KeyValuePair> queue=new LinkedList<KeyValuePair>();
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
			queue.add(new KeyValuePair(key, value));
		}
		return queue;
	}
	
	public static Queue<KeyValuePair> extractCommandsQueueFromXmlForDeviceList(
			String commandInXml) throws ParserConfigurationException, SAXException, IOException {
			Queue<KeyValuePair> queue=new LinkedList<KeyValuePair>();
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
				if((key.equals("DEVICE_ID")) || (key.equals("DEVICE_STATUS"))) 
				queue.add(new KeyValuePair(key, value));
			}
			return queue;
		}
		
	
	
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
				String key = null;
				String value = null;
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
	public static String convertQueueIntoXml(Queue<KeyValuePair> messages) {
		String message = "<xpeditions>";
		for (KeyValuePair keyValuePair : messages) {
			String key = keyValuePair.getKey();
			String value = keyValuePair.getValue();
			
			String msg = "<field>";
			msg += ("<key>"+key+"</key>");
			if(value != null){
				msg += ("<value>"+value+"</value>");
			}
			msg += "</field>";
			
			message += msg;
		}
		message += "</xpeditions>";
		return message ;
	}
	
	
	public static String convertToStringFromQueue(Queue<KeyValuePair> queue) {
		String command=new String();
		for (KeyValuePair keyValuePair : queue) {
			command=command+keyValuePair.getKey()+"="+keyValuePair.getValue()+IMonitorUtil.NEW_LINE;
		}
		return command;
	}

	public static String generateTransactionId() {
		Random generator = new Random();
		int random = generator.nextInt(10000);
		String transactionId = Long.toHexString(System.currentTimeMillis()) + random;
		return transactionId.substring(transactionId.length()-6);
	}

	public static void executeControlUpdate(ControlModel controlModel) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		String controlCommand = controlModel.getControlCommand();
		UpdatorModel<?> controlUpdatorModel = IMonitorUtil.controlUpdators.get(controlCommand);
		if(controlUpdatorModel == null){
			return;
		}
		Class<?> className = controlUpdatorModel.getClassName();
		Method method = controlUpdatorModel.getMethod();
		if(className == null || method == null){
			return;
		}
		method.invoke(className.newInstance(), controlModel);
	}

	public static Map<String, UpdatorModel<?>> getControlUpdators() {
		return controlUpdators;
	}
	public static void openURLConnection(String recipient)
    {
        try
        {
            URL url = new URL("http://enterprise.smsgupshup.com/GatewayAPI/rest?method=sendMessage&auth_scheme=PLAIN&userid=2000033167&password=eureka&msg_type=TEXT&v=1.1&mask=Eurosmil&send_to="+recipient+"&msg=hi");
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);
            InputStream inStream = connection.getInputStream();
            BufferedReader input =
            new BufferedReader(new InputStreamReader(inStream));
    
            String line = "";
            while ((line = input.readLine()) != null)
            LogUtil.info(line);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


	public static String convertToString(Object object) {
		if(object == null){
			return "";
		}
		return object.toString();
	}

	public static final Map<String, UpdatorModel<?>> getNotifierUpdators() {
		return notifierUpdators;
	}
	

	//****************************************************** sumit start: Notify user for attachment ********************************************
	public static void notifyRecipients(String notifierName, String message, String[] recipients, Date date,String UserName,Customer customer) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		
		UpdatorModel<?> controlUpdatorModel = IMonitorUtil.notifierUpdators.get(notifierName);
		XStream stream = new XStream();
		if(controlUpdatorModel == null){
			LogUtil.info("controlUpdatorModel = null");
			return;
		}
		Class<?> className = controlUpdatorModel.getClassName();
		Method method = controlUpdatorModel.getMethod();
		
		if(className == null || method == null){
			return;
		}
		if(notifierName.equalsIgnoreCase("E-Mail"))
		{
			method.invoke(className.newInstance(), message, recipients, date, UserName);
		}
		else
		{
		method.invoke(className.newInstance(), message, recipients, date, customer);
	}
		
	}
/*	
 * ************************************************************** ORIGINAL CODE *****************************************************************	
 * public static void notifyRecipients(String notifierName, String message, String[] recipients) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		UpdatorModel<?> controlUpdatorModel = IMonitorUtil.notifierUpdators.get(notifierName);
		if(controlUpdatorModel == null){
			return;
		}
		Class<?> className = controlUpdatorModel.getClassName();
		Method method = controlUpdatorModel.getMethod();
		if(className == null || method == null){
			return;
		}
		method.invoke(className.newInstance(), message, recipients);
	}
 ******************************************************* ORIGINAL CODE ENDS *********************************************************************/
// ******************************************************************** sumit end ***********************************************************
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
	public static String generateSmsMessage(String password){
		String message = "";
		message+="Your new Password is  :"+password; 
		return message;
	}

	public static String generateMailMessage(String action,String customerId,String deviceId,String gateWayId,String userId){
		String message = "";
		message+="<p>"+action+"</p></br>";
		if(!gateWayId.equals("") && gateWayId !=null){
			message+="<p>    ImvgId :"+gateWayId+"</p>";
		}
		if(!deviceId.equals("") && deviceId !=null){
			message+="<p> Device id :"+deviceId+"</p>";
		}
		message+="<p>CustomerId :"+customerId+"</p>";
		message+="<p>    UserId :"+userId+"</p></br></br>";
		return message;
	}
	
	public static String generateSmsMessage(String action,String customerId,String deviceId,String gateWayId,String userId){
		String message = "";
		message+=action;
		
		return message;
	}
	
	public static String commandId(Queue<KeyValuePair> events,String commandId) {
		for (KeyValuePair keyValuePair : events) {
			if(keyValuePair.getKey().equals(commandId)){
				return keyValuePair.getValue();
			}
		}
		return null;
	}


	public static ArrayList<KeyValuePair> commandIdReturnArray(Queue<KeyValuePair> events,String devicestatus,String deviceid ) {
		
	ArrayList<KeyValuePair> array =new ArrayList<KeyValuePair>(); 
		for (KeyValuePair keyValuePair :events) 
		{
			if((keyValuePair.getKey().equals(deviceid))||(keyValuePair.getKey().equals(devicestatus)))
			{
				int i=0;
				array.set(i, keyValuePair);
				i++;
			}
		}
		return array;
	}

	public static String createMessage(String i)
	{
		String messageString=(String)ControllerProperties.getMsgproperties().get(i);
		if(messageString == null)messageString = "General Error!";
		return messageString;
		
	}

	public static String createMessage(String MsgName,Object ...params)
	{
		String messageString=(String)ControllerProperties.getMsgproperties().get(MsgName);
		if(messageString == null)messageString = "General Error!";
		Formatter formatter = new Formatter();
		formatter.format(messageString,params);
		return formatter.toString();
		
	}
	public static boolean waitForResult(ImonitorControllerAction controllerAction) {
		String tOut = ControllerProperties.getProperties().get(Constants.TIMOUT_DURATION);
		long timeOut = Long.parseLong(tOut);
		return waitForResult(controllerAction, timeOut);
	}
	public static boolean waitForResultForCommand(ImonitorControllerAction controllerAction) {
		String tOut = ControllerProperties.getProperties().get(Constants.TIMOUT_DURATION);
		long timeOut = Long.parseLong(tOut)*5;
		return waitForResult(controllerAction, timeOut);
	}
	public static boolean waitForResult(ImonitorControllerAction controllerAction,long timeOut) 
	{
		long waitTime = 1000;
		do{
			timeOut = timeOut - waitTime;
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				LogUtil.info("Got Exception: ", e);
				e.printStackTrace();
			}
			if(timeOut < 0){
				controllerAction.getActionModel().setMessage("Please check your Gateway connectivity.");
				return false;
			}
		}while(!controllerAction.isResultExecuted());
		return true;
	}
	
	
	public static final Map<String, AlertType> getAlertTypes() {
		return alertTypes;
	}

	public static final void setAlertTypes(Map<String, AlertType> alertTypes) {
		IMonitorUtil.alertTypes = alertTypes;
	}

	public static final Map<String, UpdatorModel<?>> getActionUpdators() {
		return actionUpdators;
	}

	public static final Map<String, Status> getStatuses() {
		return statuses;
	}

	public static final void setStatuses(Map<String, Status> statuses) {
		IMonitorUtil.statuses = statuses;
	}

	public static void setActionTypes(Map<String, ActionType> actionTypes) {
		IMonitorUtil.actionTypes = actionTypes;
	}

	public static Map<String, ActionType> getActionTypes() {
		return actionTypes;
	}

	public static String commandId(KeyValuePair element, String commandId) {
		if(element.getKey().equals(commandId))
		{
			return element.getValue();
		
		}
		return null;
	}
	
	
	

		

	    static enum ParseState {
		NORMAL,
		ESCAPE,
		UNICODE_ESCAPE
	    }

	    // convert unicode escapes back to char
	    public static String convertUnicodeEscape(String s) {
		char[] out = new char[s.length()];

		ParseState state = ParseState.NORMAL;
		int j = 0, k = 0, unicode = 0;
		char c = ' ';
		for (int i = 0; i < s.length(); i++) {
		    c = s.charAt(i);
		    if (state == ParseState.ESCAPE) {
			if (c == 'u') {
			    state = ParseState.UNICODE_ESCAPE;
			    unicode = 0;
			}
			else { // we don't care about other escapes
			    out[j++] = '\\';
			    out[j++] = c;
			    state = ParseState.NORMAL;
			}
		    }
		    else if (state == ParseState.UNICODE_ESCAPE) {
			if ((c >= '0') && (c <= '9')) {
			    unicode = (unicode << 4) + c - '0';
			}
			else if ((c >= 'a') && (c <= 'f')) {
			    unicode = (unicode << 4) + 10 + c - 'a';
			}
			else if ((c >= 'A') && (c <= 'F')) {
			    unicode = (unicode << 4) + 10 + c - 'A';
			}
			else {
			    throw new IllegalArgumentException("Malformed unicode escape");
			}
			k++;

			if (k == 4) {
			    out[j++] = (char) unicode;
			    k = 0;
			    state = ParseState.NORMAL;
			}
		    }
		    else if (c == '\\') {
			state = ParseState.ESCAPE;
		    }
		    else {
			out[j++] = c;
		    }
		}

		if (state == ParseState.ESCAPE) {
		    out[j++] = c;
		}

		return new String(out, 0, j);
	    }
	    
	  //Naveen made changes for VIA execution 
	    public static boolean waitForScenarioResult(ImonitorControllerAction controllerAction) {
			
			long timeOut = 30000;
			return waitForScenarioResult(controllerAction, timeOut);
		}
	    public static boolean waitForScenarioResult(ImonitorControllerAction controllerAction,long timeOut) 
		{
			long waitTime = 3000;
			do{
				timeOut = timeOut - waitTime;
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					LogUtil.info("Got Exception: ", e);
					e.printStackTrace();
				}
				if(timeOut < 0){
					controllerAction.getActionModel().setMessage("Please check your Gateway connectivity.");
					return false;
				}
			}while(!controllerAction.isResultExecuted());
			return true;
		}
	
}
