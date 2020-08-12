package in.xpeditions.jawlin.imonitor.controller.service;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.ws.http.HTTPException;

import net.sf.json.JSONSerializer;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


import in.xpeditions.jawlin.imonitor.controller.action.AcControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcControlOffAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcFanModeControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.AcTempeartureControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.AlexaControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.DimmerLevelChangeAction;
import in.xpeditions.jawlin.imonitor.controller.action.IduTemperatureControlAction;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScenarioExecutionAction;
import in.xpeditions.jawlin.imonitor.controller.action.IndoorUnitActionControl;
import in.xpeditions.jawlin.imonitor.controller.action.SwitchOffAction;
import in.xpeditions.jawlin.imonitor.controller.action.SwitchOnAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AgentManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.Alexamanager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScenarioManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.AlexaDeviceCapabilities;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class AlexaServiceManager {
	
	final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
//	public static String alexaEventEndPoint = "https://api.eu.amazonalexa.com/v3/events";
//	public static String alexaTokenEndPoint = "https://api.amazon.com/auth/o2/token";
	
public String checkAlexaUserProfile(String tokenXml){
	LogUtil.info("checkAlexaUserProfile !"+tokenXml);
		
		XStream stream = new XStream();
		String result = "failure";
		String token = (String) stream.fromXML(tokenXml);
		
		Alexamanager alexaManager = new Alexamanager();
		String customerId = alexaManager.getCustomerByAmazonid(token);
		LogUtil.info("customerId !"+customerId);
		result = stream.toXML(customerId);
		
		LogUtil.info(" result customerId !"+result);
		return result;
	}



public String StateReport (String deviceId, String gatewayId, String type){
	
	
	String result = null;
	//XStream stream = new XStream();
	Alexamanager alexaManager = new Alexamanager();
	DeviceManager deviceManager = new DeviceManager();
	//GatewayManager gatewayManager = new GatewayManager();
	//gatewayManager.getGateWayById(gatewayId);
	if(type.equals(Constants.Z_WAVE_SWITCH)){
	
		Device device = deviceManager.getDeviceWithStatus(gatewayId,deviceId);
		
		if(device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_UP) || (device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_STATE_CHANGED))){
			
			
			if(device.getCommandParam().equals("1")){
				result = "ON";
			}else{
				
				result = "OFF";
			}
		}else{
			
			result = "ENDPOINT_UNREACHABLE";
		}
	
	
	}else if(type.equals(Constants.DEV_ZWAVE_RGB)){
		
		Device device = deviceManager.getDeviceWithStatus(gatewayId,deviceId);
		
		if(device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_UP) || (device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_STATE_CHANGED))){
			
			
			
				result = device.getCommandParam();
			
		}else{
			
			result = "ENDPOINT_UNREACHABLE";
		}
		
	}
else if(type.equals(Constants.Z_WAVE_MULTI_SENSOR )){
		
		Device device = deviceManager.getDeviceWithStatus(gatewayId,deviceId);
	
		LogUtil.info("before device alert="+device.getLastAlert().getAlertCommand());
		if(device.getLastAlert().getAlertCommand().equals(Constants.MOTION_DETECTED) || (device.getLastAlert().getAlertCommand().equals(Constants.NOMOTION_DETECTED ))){
			LogUtil.info("after device alert="+device.getLastAlert().getAlertCommand());
			
			
				result = device.getLastAlert().getAlertCommand();
				LogUtil.info("sensor result !"+result);
			
		}else{
			
			result = "ENDPOINT_UNREACHABLE";
		}
		
	}
	
	
else if(type.equals(Constants.Z_WAVE_DOOR_SENSOR )){
	
	Device device = deviceManager.getDeviceWithStatus(gatewayId,deviceId);

	LogUtil.info("before device alert="+device.getLastAlert().getAlertCommand());
	if(device.getLastAlert().getAlertCommand().equals(Constants.DOOR_OPEN) || (device.getLastAlert().getAlertCommand().equals(Constants.DOOR_CLOSE))){
		LogUtil.info("after device alert="+device.getLastAlert().getAlertCommand());
		
		
			result = device.getLastAlert().getAlertCommand();
			LogUtil.info("sensor result !"+result);
		
	}else{
		
		result = "ENDPOINT_UNREACHABLE";
	}
	
}

else if(type.equals(Constants.Z_WAVE_AV_BLASTER)){
		
		Device device = deviceManager.getDeviceWithStatus(gatewayId,deviceId);
		
		if(device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_UP) || (device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_STATE_CHANGED))){
			
			
			
				result = device.getCommandParam();
			
		}else{
			
			result = "ENDPOINT_UNREACHABLE";
		}
		
	}

else if(type.equals(Constants.Z_WAVE_DIMMER)){
		
		Device device = deviceManager.getDeviceWithStatus(gatewayId,deviceId);
		
		if(device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_UP) || (device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_STATE_CHANGED))){
			
			
			
				result = device.getCommandParam();
			
		}else{
			
			result = "ENDPOINT_UNREACHABLE";
		}
		
	}
	else{
		
		if(deviceId.contains("TEMPERATURE_SENSOR")){
			String[] spli = deviceId.split("-");
			deviceId = spli[0];
		}
	//Device device = deviceManager.getDeviceWithStatus(gatewayId,deviceId);
	
		Object[] heatValue = alexaManager.getDeviceWithReportValues(gatewayId,deviceId);
		//result= heatValue[0] + "-" +heatValue[1];
		result= heatValue[0]+"";
	
	}
	
	return result;
};

public String getAccessTokensFromAmazonForCustomer(String code, String token) throws HTTPException {
	
	String result="Success";
	
	try {
		
		URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_TOKEN_END_POINT));
		
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Host", "api.amazon.com");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		String parameters = "grant_type=authorization_code&code="+code+"&client_id="+ControllerProperties.getProperties().get(Constants.ALEXA_CLIENT_ID)+"&client_secret="+ControllerProperties.getProperties().get(Constants.ALEXA_SECRET);
		
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

		//print result
	
		JSONObject json = new JSONObject(response.toString());
		String accessToken = json.getString("access_token");
		String refreshToken = json.getString("refresh_token");
		int expires = json.getInt("expires_in");
		expires = expires/60;
		String tokenType = json.getString("token_type");
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		long curTimeInMs = date.getTime();
	    Date afterAddingMins = new Date(curTimeInMs + (expires * ONE_MINUTE_IN_MILLIS));
		Alexamanager alexaManager = new Alexamanager();
		Alexa alexaUser = alexaManager.getCustomerDetailsByImonitorAccessToken(token);
		if(alexaUser != null){
			
			alexaUser.setSkillToken(accessToken);
			alexaUser.setSkillRefreshToken(refreshToken);
			alexaUser.setSkillTokenType(tokenType);
			alexaUser.setTokenExpires(dateFormat.format(afterAddingMins));
			alexaUser.setAlexaAuthCode(code);
			boolean update = alexaManager.updateAlexaUser(alexaUser);
			
		}
		
	    
	} catch (Exception e) {
		// TODO: handle exception
		LogUtil.info("Fail to get access token from amazon: "+ e.getMessage());
	}
	
	return result;
	
}





public void updateAccessTokenAndRefreshToken(){}

public String voiceControlForAC(String deviceId,String customerid,String gatewayId,String command,String value)throws Exception{
	
	String result=null;
	XStream stream = new XStream();
	CustomerManager customerManager=new CustomerManager();
	GatewayManager gtmang=new GatewayManager();
	DeviceManager devmanager=new DeviceManager();
	result = Constants.ENDPOINT_UNREACHABLE;
	boolean checking=customerManager.checkCustomerExistence(customerid); //Checking user existense
	
	if(checking){
		
	
	GateWay gateway=gtmang.getGateWayWithAgentById(Long.valueOf(gatewayId));
	
	String generatedid=devmanager.getDeviceByDid(deviceId, Long.valueOf(gatewayId));
	
	Device device=devmanager.getDeviceByGeneratedIdWithStatus(generatedid);
	device.setGateWay(gateway);
	
	
	if(generatedid==null){
		return result=null;
	}
	if(device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_DOWN)) {
		
		return result=Constants.ENDPOINT_UNREACHABLE;
	}else {
		
		AlexaControlAction alexaControl = new AlexaControlAction(device, command, value);
		Thread t1 = new Thread(alexaControl);
		t1.start();
		return result=Constants.SUCCESS;
	}
	
/*	if((command.equals(Constants.ZwaveAcOn)) || (command.equals(Constants.ZwaveAcOff))){
		
		device.setCommandParam(value);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new AcControlAction();
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg= IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			result = Constants.SUCCESS;
		}
	}else if(command.equals(Constants.SetAcTemp)){
		
		device.setCommandParam(value);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new AcTempeartureControlAction();
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			result = Constants.SUCCESS;
		}

	}else if(command.equals(Constants.SetAcMode)){
		
		
		device.setCommandParam(value);
		ActionModel actionModel = new ActionModel(device, null);
	
		ImonitorControllerAction controllerAction = new AcControlAction();
		
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			result = Constants.SUCCESS;
		}

		
	}else if(command.equalsIgnoreCase(Constants.SetAcOff)){
		device.setCommandParam(value);
		ImonitorControllerAction controllerAction = new AcControlOffAction();
		ActionModel actionModel = new ActionModel(device, null);
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			result = Constants.SUCCESS;
		}
	}else if (command.equalsIgnoreCase(Constants.AdjustAcTemp)){
		int previous=Integer.parseInt(device.getCommandParam());
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
		boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			result = Constants.SUCCESS;
		}
	}*/
	

}
	return result;
}


public String voiceControlForDimmer(String deviceId,String customerid,String gatewayId,String command,String value)throws Exception{
	
	String result=null;
	CustomerManager customerManager=new CustomerManager();
	GatewayManager gtmang=new GatewayManager();
	DeviceManager devmanager=new DeviceManager();
	result = Constants.ENDPOINT_UNREACHABLE;
	boolean checking=customerManager.checkCustomerExistence(customerid); //Checking user existense

	if(checking){
		
		GateWay gateway=gtmang.getGateWayWithAgentById(Long.valueOf(gatewayId));
		String generatedid=devmanager.getDeviceByDid(deviceId, Long.valueOf(gatewayId));
		Device device=devmanager.getDeviceByGeneratedIdWithStatus(generatedid);
		device.setGateWay(gateway);
		
		
		if(generatedid==null){
			return result=null;
		}
		
		
		if(device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_DOWN)) {
			
			return result=Constants.ENDPOINT_UNREACHABLE;
		}else {
			
			AlexaControlAction alexaControl = new AlexaControlAction(device, command, value);
			Thread t1 = new Thread(alexaControl);
			t1.start();
			return result=Constants.SUCCESS;
		}
		
/*	if(command.equals(Constants.SetZWDim)){
			device.setCommandParam(value);
			ActionModel actionModel = new ActionModel(device, null);
            ImonitorControllerAction controllerAction = new DimmerLevelChangeAction();
            controllerAction.executeCommand(actionModel);
            boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
            
            if(resultFromImvg){
    			result = Constants.SUCCESS;
    		}
		}else if(command.equals(Constants.ZWDevDimUp)){
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
			boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
			 if(resultFromImvg){
	    			result = Constants.SUCCESS;
	    		}
		}else if(command.equals(Constants.ZWDevDimDown)){
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
			boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
			if(resultFromImvg){
    			result = Constants.SUCCESS;
    		}
		}*/
		
	}
	
	return result;
}



public String voiceControlForRGB(String deviceId,String customerid,String gatewayId,String command,String hue,String saturation,String brightness)throws Exception{
	
	
	float h = Float.parseFloat(hue);
	float s = Float.parseFloat(saturation);
	float b = Float.parseFloat(brightness);
	Color color=Color.getHSBColor(h, s, b);
	
	
	String hex = "#"+Integer.toHexString(color.getRGB()).substring(2);
	//LogUtil.info("hex"+hex);
	
	String result=null;
	CustomerManager customerManager=new CustomerManager();
	
	GatewayManager gtmang=new GatewayManager();
	
	DeviceManager devmanager=new DeviceManager();

	result = Constants.ENDPOINT_UNREACHABLE;
	boolean checking=customerManager.checkCustomerExistence(customerid); //Checking user existense
	//LogUtil.info("checking"+checking);

	if(checking){
		
		GateWay gateway=gtmang.getGateWayWithAgentById(Long.valueOf(gatewayId));
		String generatedid=devmanager.getDeviceByDid(deviceId, Long.valueOf(gatewayId));
		Device device=devmanager.getDeviceByGeneratedIdWithStatus(generatedid);
		device.setGateWay(gateway);
		//LogUtil.info("gateway !"+gateway);
		//LogUtil.info("device !!!!"+device);
		
	
		
		if(generatedid==null){
			return result=null;
			
		}
		
		
		if(device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_DOWN)) {
			
			return result=Constants.ENDPOINT_UNREACHABLE;
		}else {
			
			AlexaControlAction alexaControl = new AlexaControlAction(device, command, hex);
			Thread t1 = new Thread(alexaControl);
			t1.start();
			return result=Constants.SUCCESS;
		}
	}
	//LogUtil.info("resultttt :   "+result);
	//LogUtil.info("sendRGBValue end");
	return result;
}


public String voiceControlForAVBlaster(String deviceId,String customerid,String gatewayId,String command,String value)throws Exception{
	LogUtil.info("voiceControlForAVblaster :");
	String result=null;
	CustomerManager customerManager=new CustomerManager();
	GatewayManager gtmang=new GatewayManager();
	DeviceManager devmanager=new DeviceManager();
	result = Constants.ENDPOINT_UNREACHABLE;
	boolean checking=customerManager.checkCustomerExistence(customerid); //Checking user existense
	
	if(checking){
		
		GateWay gateway=gtmang.getGateWayWithAgentById(Long.valueOf(gatewayId));
		String generatedid=devmanager.getDeviceByDid(deviceId, Long.valueOf(gatewayId));
		Device device=devmanager.getDeviceByGeneratedIdWithStatus(generatedid);
		device.setGateWay(gateway);
		
		
		if(generatedid==null){
			return result=null;
		}
		
		
		if(device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_DOWN)) {
			
			return result=Constants.ENDPOINT_UNREACHABLE;
		}else {
			
			AlexaControlAction alexaControl = new AlexaControlAction(device, command, value);
			LogUtil.info("alexaControl :   "+alexaControl);
			Thread t1 = new Thread(alexaControl);
			LogUtil.info("alexaControl t1 :   "+t1);
			t1.start();
			LogUtil.info("voiceControlForAVblaster result :"+result);
			return result=Constants.SUCCESS;
			

		}
	}
	LogUtil.info("resultttt :   "+result);
	LogUtil.info("sendAVblasterValue end");
	return result;
}


public void sendProactiveDeviceDiscover(String tokenxml) throws JSONException{
	
	XStream stream = new XStream();
	String result = "failure";
	String token = (String) stream.fromXML(tokenxml);
	Alexamanager alexaManager = new Alexamanager();
	String customerId = alexaManager.getCustomerByAmazonid(token);
	CustomerManager customerManager = new CustomerManager();
	Customer customer = customerManager.getCustometidByCustomerName(customerId);
	Alexa alexa = alexaManager.getAlexaUserByCustomerId(customer);
	List<Device> devices = customerManager.listAlexaDevicesOfCustomer(customerId);
	
	Date date = new Date();
	
	JSONObject header = new JSONObject();
	header.put("messageId", UUID.randomUUID());
	header.put("namespace", "Alexa");
	header.put("name", Constants.ChangeReport);
	header.put("payloadVersion", "3");
	
	JSONObject alexaResponse = new JSONObject();
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
	TimeZone timeZone = TimeZone.getTimeZone("UTC");
	Calendar calendar = Calendar.getInstance(timeZone);
	sdf.setTimeZone(timeZone);
	for(Device device : devices){
		
		alexaResponse = new JSONObject();
		
		if(device.getDeviceType().getName().equals(Constants.Z_WAVE_SWITCH)){
			//LogUtil.info("Z_wave");
			
			JSONObject propertiesContent = new JSONObject();
			propertiesContent.put("namespace", "Alexa.PowerController");
			propertiesContent.put("name", "powerState");
			if(device.getCommandParam().equals("1")){
			propertiesContent.put("Value", "ON");
			}else{
			propertiesContent.put("Value", "OFF");
			}
			propertiesContent.put("timeOfSample", date);
			propertiesContent.put("uncertaintyInMilliseconds", "0");
			
			JSONObject connectivityContent = new JSONObject();
			connectivityContent.put("namespace", "Alexa.EndpointHealth");
			connectivityContent.put("name", "connectivity");
			JSONObject valuecontent = new JSONObject();
			valuecontent.put("value", "OK");
			connectivityContent.put("Value", valuecontent);
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
			JSONObject scope = new JSONObject();
			scope.put("type", "BearerToken");
			scope.put("token", alexa.getSkillToken());
			JSONObject endpointobj = new JSONObject();
			endpointobj.put("scope", scope);
			endpointobj.put("endpointId", device.getDeviceId());
			JSONObject eventsObject = new JSONObject();
			eventsObject.put("header", header);
			eventsObject.put("endpoint", endpointobj);
			eventsObject.put("payload", payLoadObject);
			
			
			alexaResponse.put("context",new JSONObject());
			alexaResponse.put("event", eventsObject);
				
		
			
		}
		
		
		else if(device.getDeviceType().getName().equals(Constants.DEV_ZWAVE_RGB)){
			
			LogUtil.info("Z_wave RGBBB");
			JSONObject propertiesContent = new JSONObject();
			propertiesContent.put("namespace", "Alexa.ColorController");
			propertiesContent.put("name", "color");
			if(device.getCommandParam().equals("1")){
			propertiesContent.put("Value", "ON");
			}else{
			propertiesContent.put("Value", "OFF");
			}
			propertiesContent.put("timeOfSample", date);
			propertiesContent.put("uncertaintyInMilliseconds", "0");
			
			JSONObject connectivityContent = new JSONObject();
			connectivityContent.put("namespace", "Alexa.EndpointHealth");
			connectivityContent.put("name", "connectivity");
			JSONObject valuecontent = new JSONObject();
			valuecontent.put("value", "OK");
			connectivityContent.put("Value", valuecontent);
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
			JSONObject scope = new JSONObject();
			scope.put("type", "BearerToken");
			scope.put("token", alexa.getSkillToken());
			JSONObject endpointobj = new JSONObject();
			endpointobj.put("scope", scope);
			endpointobj.put("endpointId", device.getDeviceId());
			JSONObject eventsObject = new JSONObject();
			eventsObject.put("header", header);
			eventsObject.put("endpoint", endpointobj);
			eventsObject.put("payload", payLoadObject);
			
			
			alexaResponse.put("context",new JSONObject());
			alexaResponse.put("event", eventsObject);
				
		
			
		}else if(device.getDeviceType().getName().equals(Constants.Z_WAVE_MULTI_SENSOR)){
			LogUtil.info("Z_WAVE_MULTI_SENSOR");
			
			JSONObject propertiesContent = new JSONObject();
			propertiesContent.put("namespace", "Alexa.MotionSensor");
			propertiesContent.put("name", "detectionState");
			propertiesContent.put("value", device.getLastAlert().getAlertCommand());
			propertiesContent.put("timeOfSample", date);
			propertiesContent.put("uncertaintyInMilliseconds", "0");
			
			JSONObject connectivityContent = new JSONObject();
			connectivityContent.put("namespace", "Alexa.EndpointHealth");
			connectivityContent.put("name", "connectivity");
			JSONObject valuecontent = new JSONObject();
			valuecontent.put("value", "OK");
			connectivityContent.put("Value", valuecontent);
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
			JSONObject scope = new JSONObject();
			scope.put("type", "BearerToken");
			scope.put("token", alexa.getSkillToken());
			JSONObject endpointobj = new JSONObject();
			endpointobj.put("scope", scope);
			endpointobj.put("endpointId", device.getDeviceId());
			JSONObject eventsObject = new JSONObject();
			eventsObject.put("header", header);
			eventsObject.put("endpoint", endpointobj);
			eventsObject.put("payload", payLoadObject);
			
			
			alexaResponse.put("context",new JSONObject());
			alexaResponse.put("event", eventsObject);
				
		
			
		}
		else if(device.getDeviceType().getName().equals(Constants.Z_WAVE_DOOR_SENSOR)){
			LogUtil.info("Z_WAVE_DOOR_SENSOR");
			
			JSONObject propertiesContent = new JSONObject();
			propertiesContent.put("namespace", "Alexa.ContactSensor");
			propertiesContent.put("name", "detectionState");
			propertiesContent.put("value", device.getLastAlert().getAlertCommand());
			propertiesContent.put("timeOfSample", date);
			propertiesContent.put("uncertaintyInMilliseconds", "0");
			
			JSONObject connectivityContent = new JSONObject();
			connectivityContent.put("namespace", "Alexa.EndpointHealth");
			connectivityContent.put("name", "connectivity");
			JSONObject valuecontent = new JSONObject();
			valuecontent.put("value", "OK");
			connectivityContent.put("Value", valuecontent);
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
			JSONObject scope = new JSONObject();
			scope.put("type", "BearerToken");
			scope.put("token", alexa.getSkillToken());
			JSONObject endpointobj = new JSONObject();
			endpointobj.put("scope", scope);
			endpointobj.put("endpointId", device.getDeviceId());
			JSONObject eventsObject = new JSONObject();
			eventsObject.put("header", header);
			eventsObject.put("endpoint", endpointobj);
			eventsObject.put("payload", payLoadObject);
			
			
			alexaResponse.put("context",new JSONObject());
			alexaResponse.put("event", eventsObject);
				
		
			
		}
		
		
		else if(device.getDeviceType().getName().equals(Constants.Z_WAVE_DIMMER)){
			JSONObject propertiesContent = new JSONObject();
			propertiesContent.put("namespace", "Alexa.BrightnessController");
			propertiesContent.put("name", "brightness");
			propertiesContent.put("value", device.getCommandParam());
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
			
			JSONObject scope = new JSONObject();
			scope.put("type", "BearerToken");
			scope.put("token", alexa.getSkillToken());
			JSONObject endpointobj = new JSONObject();
			endpointobj.put("scope", scope);
			endpointobj.put("endpointId", device.getDeviceId());
			
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
				
			}else{
			continue;
		}
		
			boolean update = false;
		try {
			update = sendProactiveDeviceUpdateToAlexa(alexaResponse,alexa);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

public boolean sendProactiveDeviceUpdateToAlexa(JSONObject alexaResponse, Alexa alexaUser) throws ParseException{
	
	boolean result = false;
	
	String expireTime = alexaUser.getTokenExpires();
	Alexamanager alexaManager = new Alexamanager();
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	Date expireDate = dateFormat.parse(expireTime);
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
	}
	
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
		
		LogUtil.info("Response Code from alexa end point : " + responseCode);
		if(responseCode == 403){
			
			
			alexaManager.deleteAlexaUser(alexaUser);
			
		}
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return result;
	
}

    
public String discoverScenarios(String customerId,String tokenXml){
	LogUtil.info(" alexa user scenario discover=="+"customerId=="+customerId+"tokenXml=="+tokenXml);
	XStream stream=new XStream();
	String result=null;
	DeviceManager devmanager=new DeviceManager();
	ScenarioManager scenarioManager = new ScenarioManager();
	Alexa alexa = devmanager.getdetailsforalexanormaluser(customerId,tokenXml);
	
	LogUtil.info(alexa.getUser().getRole().getName());
	if(alexa.getUser().getRole().getName().equals(Constants.NORMAL_USER)){
		
		List<Object[]> objects=scenarioManager.listSubUserScenariosByCustomerIdForApi(customerId,alexa.getUser());
		result=stream.toXML(objects);
	}else{
		
		List<Object[]> objects=scenarioManager.listAllScenariosByCustomerIdForApi(customerId);
		result=stream.toXML(objects);
	}
	
	
	LogUtil.info(" alexa user Scenario discover result=="+result);
	return result;
}

public String activateScenario(String sceneId, String gatewayId){
	
	String result=null;
	ScenarioManager scenarioManager = new ScenarioManager();
	Scenario s = scenarioManager.getScenarioWithGatewayById(Long.parseLong(sceneId));
	ActionModel actionModel = new ActionModel(null, s);
	ImonitorControllerAction scenarioExecutionAction = new ScenarioExecutionAction();
	scenarioExecutionAction.executeCommand(actionModel);
	boolean resultFromImvg = IMonitorUtil.waitForScenarioResult(scenarioExecutionAction);
	if(resultFromImvg)
	{
		if(scenarioExecutionAction.isActionSuccess())
		{
			CustomerManager customerManager = new CustomerManager();
			Customer customer = customerManager.getCustomerByGateWay(s.getGateWay());
			Alexamanager alexaManger = new Alexamanager();
			Alexa alexa = alexaManger.getAlexaUserByCustomerId(customer);
			//result = "<imonitor>";
			//result += "success";
			//result += "</imonitor>";
			//result += "<token>";
			result = alexa.getSkillToken();
			//result += "</token>";
			
		}
	}
	else
	{
		//result = "<imonitor>";
		result = "failure";
		//result += "</imonitor>";
    }
	
	return result;
}

public void updateDeviceListToAlexa(Customer customer,Alexa alexaUser) throws JSONException{
	XStream stream=new XStream();
	DeviceManager devmanager=new DeviceManager();
	ScenarioManager scenarioManager =  new ScenarioManager();
	List<Object[]> objects=devmanager.getdevicedetailsforalexa(customer.getCustomerId());
	List<Object[]> scenarios=scenarioManager.listAllScenariosByCustomerIdForApi(customer.getCustomerId());
	
	AlexaDeviceCapabilities deviceCapabilities = new AlexaDeviceCapabilities();
	List<discoveredAppliances> discovered=new ArrayList<discoveredAppliances>();
	
	JSONObject updatediscovery = new JSONObject();
	JSONObject header = new JSONObject();
	header.put("namespace", "Alexa.Discovery");
	header.put("names", "AddOrUpdateReport");
	header.put("payloadVersion", "3");
	header.put("messageId",  UUID.randomUUID());
	
	JSONObject scope = new JSONObject();
	scope.put("type", "Alexa.Discovery");
	scope.put("token", alexaUser.getSkillToken());
	
	JSONArray endPointsArray = new JSONArray();
	
	
	try {
		
		if(objects != null) {
			
			for (Object[] obj:objects){
				
				String appid=obj[0].toString();
				String deviceName=obj[2].toString();
				discoveredAppliances discover=new discoveredAppliances();
				JSONObject deviceArray = new JSONObject();
				String zwtype=(String) obj[3];
				String desc;
				String[] displaycateg;
				
				HashMap<String,String> cookie = new HashMap<String, String>();
				cookie.put("customerId",customer.getCustomerId() );
		        cookie.put("gateway", obj[1].toString());
		        cookie.put("device", zwtype);
		        
				if(zwtype.equalsIgnoreCase("Z_WAVE_SWITCH")){
					
					deviceArray.put("endpointId", appid);
					deviceArray.put("friendlyName", deviceName);
					deviceArray.put("description", "Smart light by iMonitor");
					deviceArray.put("manufacturerName", "iMonitor Solutions");
					deviceArray.put("displayCategories", new String[] {"LIGHT"});
					deviceArray.put("cookie", cookie);
					JSONArray switchCapabilities = new JSONArray();
					switchCapabilities.put(deviceCapabilities.getRGBCapabability());
					switchCapabilities.put(deviceCapabilities.getinterfaceCapabability());
					switchCapabilities.put(deviceCapabilities.getendpointCapability());
					deviceArray.put("capabilities", switchCapabilities);
					
				}
				else if(zwtype.equalsIgnoreCase("DEV_ZWAVE_RGB")){
					LogUtil.info("RGB alexa");
					deviceArray.put("endpointId", appid);
					deviceArray.put("friendlyName", deviceName);
					deviceArray.put("description", "Smart light by iMonitor");
					deviceArray.put("manufacturerName", "iMonitor Solutions");
					deviceArray.put("displayCategories", new String[] {"LIGHT"});
					deviceArray.put("cookie", cookie);
					JSONArray switchCapabilities = new JSONArray();
					switchCapabilities.put(deviceCapabilities.getSwicthCapabability());
					switchCapabilities.put(deviceCapabilities.getinterfaceCapabability());
					switchCapabilities.put(deviceCapabilities.getendpointCapability());
					deviceArray.put("capabilities", switchCapabilities);
					
				}
				else if(zwtype.equalsIgnoreCase("Z_WAVE_MULTI_SENSOR")){
					
					deviceArray.put("endpointId", appid);
					deviceArray.put("friendlyName", deviceName);
					deviceArray.put("description", "Multi sensor by iMonitor");
					deviceArray.put("manufacturerName", "iMonitor Solutions");
					deviceArray.put("displayCategories", new String[] {"Z_WAVE_MULTI_SENSOR"});
					deviceArray.put("cookie", cookie);
					JSONArray switchCapabilities = new JSONArray();
					switchCapabilities.put(deviceCapabilities.getRGBCapabability());
					switchCapabilities.put(deviceCapabilities.getinterfaceCapabability());
					switchCapabilities.put(deviceCapabilities.getendpointCapability());
					deviceArray.put("capabilities", switchCapabilities);
					
				}
				else if(zwtype.equalsIgnoreCase("Z_WAVE_DOOR_SENSOR")){
					
					deviceArray.put("endpointId", appid);
					deviceArray.put("friendlyName", deviceName);
					deviceArray.put("description", "Multi sensor by iMonitor");
					deviceArray.put("manufacturerName", "iMonitor Solutions");
					deviceArray.put("displayCategories", new String[] {"CONTACT_SENSOR"});
					deviceArray.put("cookie", cookie);
					JSONArray switchCapabilities = new JSONArray();
					switchCapabilities.put(deviceCapabilities.getRGBCapabability());
					switchCapabilities.put(deviceCapabilities.getinterfaceCapabability());
					switchCapabilities.put(deviceCapabilities.getendpointCapability());
					deviceArray.put("capabilities", switchCapabilities);
					
				}
				else if(zwtype.equalsIgnoreCase("Z_WAVE_DIMMER")){/*
					
					BigInteger switchType = (BigInteger) obj[4];
					
					int stype = switchType.intValue();
					
					    if(stype == 3){
					    	desc = "Smart brightness controller by iMonitor";
				            displaycateg = new String[] { "Z_WAVE_DIMMER" };
				            discover.setEndpointId(appid);
				            discover.setFriendlyName(deviceName);
				            discover.setManufacturerName("iMonitor Solutions");
				            discover.setDescription(desc);
				            discover.setDisplayCategories(displaycateg);
				            discover.setCookie(cookie);
				            discover.setCapabilities("FanController");
				            discovered.add(discover);
					    }else{
					    	desc = "Smart brightness controller by iMonitor";
					    	displaycateg = new String[] { "Z_WAVE_DIMMER" };
					    	discover.setEndpointId(appid);
					    	discover.setFriendlyName(deviceName);
					    	discover.setManufacturerName("iMonitor Solutions");
					    	discover.setDescription(desc);
					    	discover.setDisplayCategories(displaycateg);
					    	discover.setCookie(cookie);
					    	discover.setCapabilities(zwtype);
					    	discovered.add(discover);
					    }
				*/}
				 else if (zwtype.equalsIgnoreCase("Z_WAVE_AC_EXTENDER"))
		          {/*
		             
					 desc = "Smart thermostat controller by iMonitor";
					 displaycateg = new String[] { "THERMOSTAT" };
					 cookie.put("device", zwtype);
					 cookie.put("category","THERMOSTAT" );
					 discover.setEndpointId(appid);
					 discover.setFriendlyName(deviceName);
					 discover.setManufacturerName("iMonitor Solutions");
					 discover.setDescription(desc);
					 discover.setDisplayCategories(displaycateg);
					 discover.setCookie(cookie);
					 discover.setCapabilities("THERMOSTAT");
					 discovered.add(discover);
		          
					 desc = "Smart temperature sencor by iMonitor";
					 discoveredAppliances discoverSencor=new discoveredAppliances();
					 displaycateg = new String[] { "TEMPERATURE_SENSOR" };
					 cookie.put("device", zwtype);
					 cookie.put("category","TEMPERATURE_SENSOR" );
					 discoverSencor.setEndpointId(appid+"-TEMPERATURE_SENSOR");
					 discoverSencor.setFriendlyName(deviceName + " Sencor");
					 discoverSencor.setManufacturerName("iMonitor Solutions");
					 discoverSencor.setDescription(desc);
					 discoverSencor.setDisplayCategories(displaycateg);
					 discoverSencor.setCookie(cookie);
					 discoverSencor.setCapabilities("TEMPERATURE_SENSOR");
					 discovered.add(discoverSencor);
					 
					 
		          */}
				
				endPointsArray.put(deviceArray);
				
			}
			
		}
		
		updatediscovery.putOpt("header", header);
		JSONObject payload = new JSONObject();
		payload.putOpt("endpoints", endPointsArray);
		updatediscovery.putOpt("payload", payload);
		
		
		//Discover scenarios
				if(scenarios != null){
					HashMap<String,String> cookie;
					for (Object[] obj:scenarios){
						String sceneId=obj[0].toString();
						String sceneName=obj[1].toString();
						String sceneDesc=obj[2].toString();
						discoveredAppliances discover=new discoveredAppliances();
						 discover.setEndpointId(sceneId);
						 discover.setManufacturerName("iMonitor Solutions");
						 discover.setFriendlyName(sceneName);
						 discover.setDescription(sceneDesc);
						 String[] displaycateg = new String[] { "ACTIVITY_TRIGGER" };
						 cookie = new HashMap();
						 cookie.put("customerId", customer.getCustomerId());
						 cookie.put("gateway", obj[1].toString());
						 cookie.put("category","SCENARIO" );
						 discover.setDisplayCategories(displaycateg);
						 discover.setCookie(cookie);
						// discover.setCapabilities("ACTIVITY_TRIGGER");
						 discovered.add(discover);
					}
				}
		
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	//Send device list to alexa end point
	Gson gson=new Gson();
	Object output=gson.toJson(discovered);
	
	try {
		String expireTime = alexaUser.getTokenExpires();
		Alexamanager alexaManager = new Alexamanager();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Date expireDate = dateFormat.parse(expireTime);
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
				
				LogUtil.info("Response Code for refresh token : " + responseCode);
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
			
			
			URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_EVENT_POINT));
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Host", "api.amazonalexa.com");
			con.setRequestProperty("Authorization", "Bearer "+alexaUser.getSkillToken());
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(discovered.toString());
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			
			LogUtil.info("Response Code from alexa end point : " + responseCode);
			if(responseCode == 403){
				alexaManager.deleteAlexaUser(alexaUser);
			}
						
		}
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	
	
	
}



//VIA_UNIT method for controlling from alexa

public String voiceControlForIDU(String deviceId,String customerid,String gatewayId,String command,String value)throws Exception{
	
	String result=null;
	XStream stream = new XStream();
	CustomerManager customerManager=new CustomerManager();
	GatewayManager gtmang=new GatewayManager();
	DeviceManager devmanager=new DeviceManager();
	result = Constants.ENDPOINT_UNREACHABLE;
	boolean checking=customerManager.checkCustomerExistence(customerid); //Checking user existense
	
	if(checking){
		
	
	GateWay gateway=gtmang.getGateWayWithAgentById(Long.valueOf(gatewayId));
	
	String generatedid=devmanager.getDeviceByDid(deviceId, Long.valueOf(gatewayId));
	
	Device device=devmanager.getDeviceByGeneratedDeviceId(generatedid);
	
	
	device.setGateWay(gateway);

	
	if(generatedid==null){
		return result=null;
	}
	
//if(device.getLastAlert().getAlertCommand().equals(Constants.DEVICE_DOWN)) {
		
	//	result=Constants.ENDPOINT_UNREACHABLE;
//	}else {
		LogUtil.info("starting Alexa thread");
		AlexaControlAction alexaControl = new AlexaControlAction(device, command, value);
		Thread t1 = new Thread(alexaControl);
		t1.start();
		LogUtil.info("exiting  and returning");
		result=Constants.SUCCESS;
//	}
	
/*	if((command.equals(Constants.IduOn)) || (command.equals(Constants.IduOff))){
		
		device.setCommandParam(value);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new IndoorUnitActionControl();
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg= IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			result = Constants.SUCCESS;
		}
	}else if(command.equals(Constants.SetIduTemp)){
		
		device.setCommandParam(value);
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new IduTemperatureControlAction();
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			result = Constants.SUCCESS;
		}

	}else if(command.equals(Constants.SetIduMode)){
		
		device.setCommandParam(value);
		ActionModel actionModel = new ActionModel(device, null);
	
		ImonitorControllerAction controllerAction = new IndoorUnitActionControl();
		
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			result = Constants.SUCCESS;
		}

		
	}else if(command.equalsIgnoreCase(Constants.SetIduOff)){
		
		device.setCommandParam(value);
		ImonitorControllerAction controllerAction = new IndoorUnitActionControl();
		ActionModel actionModel = new ActionModel(device, null);
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			result = Constants.SUCCESS;
		}
	}else if (command.equalsIgnoreCase(Constants.AdjustIduTemp)){
		
		int previous=Integer.parseInt(device.getCommandParam());
		
		int current=Integer.parseInt(value);
		
		int m=previous+current;
		if(m > 32){
			m = 32;
		}else if(m < 16){
			m = 16;
		}
		
		device.setCommandParam(String.valueOf(m));
		ActionModel actionModel = new ActionModel(device, null);
		ImonitorControllerAction controllerAction = new IduTemperatureControlAction();
		controllerAction.executeCommand(actionModel);
		boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
		if(resultFromImvg){
			result = Constants.SUCCESS;
		}
	}*/
	

}
	return result;
}


public class discoveredAppliances{
	private String endpointId;
	private String friendlyName;
	private String description;
	private String manufacturerName;
	private String[] displayCategories =  new String[5];
	private HashMap<String, String> cookie;
	private String[] capabilities = new String[5];
	
	public String getEndpointId() {
		return endpointId;
	}
	public void setEndpointId(String endpointId) {
		this.endpointId = endpointId;
	}
	public String getFriendlyName() {
		return friendlyName;
	}
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String[] getDisplayCategories() {
		return displayCategories;
	}
	public void setDisplayCategories(String[] displayCategories) {
		this.displayCategories = displayCategories;
	}
	
	public HashMap<String, String> getCookie() {
		return cookie;
	}
	public void setCookie(HashMap<String, String> cookie) {
		this.cookie = cookie;
	}
	public String[] getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(String[] capabilities) {
		this.capabilities = capabilities;
	}	
}



}