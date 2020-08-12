/*Author : Naveen
 * Date: 25th July 2018 
 * iMonitor Solutions India Pvt Ltd
 */

package imonitor.cms.alexa.proactive.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;

import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.Alexamanager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.AlexaConstants;
import in.xpeditions.jawlin.imonitor.util.Constants;

public class AlexaProactiveUpdateService implements Runnable{

	private Customer customer;
	private Alexa alexauser;
	private Device device;
	private AlertType alertType;

	public AlexaProactiveUpdateService(Customer customer,Alexa alexauser,Device device) {
		this.customer = customer;
		this.alexauser = alexauser;
		this.device=device;
	}
	
	public AlexaProactiveUpdateService(Customer customer,AlertType alertType) {
		this.customer = customer;
	
		this.alertType=alertType;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub


		Alexamanager alexamanager = new Alexamanager();
		String expireTime = alexauser.getTokenExpires();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		JSONObject updatediscovery = new JSONObject();
		JSONObject header = new JSONObject();
		JSONObject scope = new JSONObject();
		JSONObject endPointsArray = new JSONObject();
		JSONObject finalResponse = new JSONObject();
		DeviceManager deviceManager = new DeviceManager();	
		device = deviceManager.getDeviceByGeneratedDeviceIdForAlexa(device.getGeneratedDeviceId());
		try {
			Date expireDate = dateFormat.parse(expireTime);
			if(date.after(expireDate)){
			
				try {
					URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_TOKEN_END_POINT));
					HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
					con.setRequestMethod("POST");
					con.setRequestProperty("Host", "api.amazon.com");
					con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
					String parameters = "grant_type=refresh_token&refresh_token="+alexauser.getSkillRefreshToken()+"&client_id="+ControllerProperties.getProperties().get(Constants.ALEXA_CLIENT_ID)+"&client_secret="+ControllerProperties.getProperties().get(Constants.ALEXA_SECRET);

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

					if(alexauser != null){

						alexauser.setSkillToken(accessToken);
						alexauser.setSkillRefreshToken(refreshToken);
						alexauser.setTokenExpires(dateFormat.format(afterAddingMins));
						boolean update = alexamanager.updateAlexaUser(alexauser);
						
					}
				} catch (Exception e) {
					LogUtil.info("error getting access token from alexa : "  +e.getMessage());
				}

			}


		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			header.put(AlexaConstants.NameSpace, AlexaConstants.AlexaDiscovery);
			header.put(AlexaConstants.Name, AlexaConstants.AddOrUpdateReport);
			header.put(AlexaConstants.PayloadVersion, "3");
			header.put(AlexaConstants.MessageId,  UUID.randomUUID());

			scope.put(AlexaConstants.Type, AlexaConstants.BearerToken);
			scope.put(AlexaConstants.Token, alexauser.getSkillToken());

			if(device.getDeviceType().getName().equals(Constants.Z_WAVE_SWITCH)) {
				HashMap<String,String> cookie = new HashMap<String, String>();
				cookie.put("customerId",customer.getCustomerId() );
				cookie.put("gateway", String.valueOf(device.getGateWay().getId()));
				cookie.put("category",device.getDeviceType().getName());
				
				JSONObject deviceArray = new JSONObject();
				deviceArray.put(AlexaConstants.EndpointId, device.getDeviceId());
				deviceArray.put(AlexaConstants.Manufacturer, "iMonitor Solutions");
				deviceArray.put(AlexaConstants.FriendlyName, device.getFriendlyName());
				deviceArray.put(AlexaConstants.Description, "Smart light from iMonitor Solutions");
				
				JSONArray displaycateg = new JSONArray(); //new String[] { "ACTIVITY_TRIGGER" };
				displaycateg.put("LIGHT");
				deviceArray.put(AlexaConstants.DisplayCategories, displaycateg);
				deviceArray.put(AlexaConstants.Cookie, cookie);
				
				JSONArray deviceCapabilities = new JSONArray();
				deviceCapabilities.put(getSwicthCapabability());
				deviceCapabilities.put(getinterfaceCapabability());
				deviceCapabilities.put(getendpointCapability());
				deviceArray.put(AlexaConstants.Capabilities, deviceCapabilities);
				
				JSONArray endPoints = new JSONArray();
				endPoints.put(deviceArray);
				endPointsArray.put(AlexaConstants.Endpoints, endPoints);
				endPointsArray.put(AlexaConstants.Scope, scope);
				updatediscovery.put(AlexaConstants.Header, header);
				updatediscovery.put(AlexaConstants.Payload, endPointsArray);


				finalResponse.put(AlexaConstants.Events, updatediscovery);

				try 
				{
					boolean result=updateAlexaEndPoint(finalResponse,alexauser);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
			
			
			
			
			if(device.getDeviceType().getName().equals(Constants.Z_WAVE_DIMMER)) {

				HashMap<String,String> cookie = new HashMap<String, String>();
				cookie.put("customerId",customer.getCustomerId() );
				cookie.put("gateway", String.valueOf(device.getGateWay().getId()));
				cookie.put("category",Constants.Z_WAVE_DIMMER);

				JSONObject deviceArray = new JSONObject();
				deviceArray.put(AlexaConstants.EndpointId, device.getDeviceId());
				deviceArray.put(AlexaConstants.Manufacturer, "iMonitor Solutions");
				deviceArray.put(AlexaConstants.FriendlyName, device.getFriendlyName());
				deviceArray.put(AlexaConstants.Description, "Smart dimmer from iMonitor Solutions");
				JSONArray displaycateg = new JSONArray(); //new String[] { "ACTIVITY_TRIGGER" };
				displaycateg.put("LIGHT");
				deviceArray.put(AlexaConstants.DisplayCategories, displaycateg);
				deviceArray.put(AlexaConstants.Cookie, cookie);

				JSONArray deviceCapabilities = new JSONArray();
				deviceCapabilities.put(getDimmerCapabability());
				deviceCapabilities.put(getinterfaceCapabability());
				deviceCapabilities.put(getendpointCapability());
				deviceArray.put(AlexaConstants.Capabilities, deviceCapabilities);

				JSONArray endPoints = new JSONArray();
				endPoints.put(deviceArray);
				endPointsArray.put(AlexaConstants.Endpoints, endPoints);
				endPointsArray.put(AlexaConstants.Scope, scope);
				updatediscovery.put(AlexaConstants.Header, header);
				updatediscovery.put(AlexaConstants.Payload, endPointsArray);

				finalResponse.put(AlexaConstants.Events, updatediscovery);

				
				
				try 
				{
					boolean result=updateAlexaEndPoint(finalResponse,alexauser);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
			}
			if(device.getDeviceType().getName().equals(Constants.Z_WAVE_AC_EXTENDER)) {

				HashMap<String,String> cookie = new HashMap<String, String>();
				cookie.put("customerId",customer.getCustomerId() );
				cookie.put("gateway", String.valueOf(device.getGateWay().getId()));
				cookie.put("category","THERMOSTAT");
				cookie.put("devtype","ZwaveAc");
				cookie.put("device",device.getDeviceType().getName());

				JSONObject deviceArray = new JSONObject();
				deviceArray.put(AlexaConstants.EndpointId, device.getDeviceId());
				deviceArray.put(AlexaConstants.Manufacturer, "iMonitor Solutions");
				deviceArray.put(AlexaConstants.FriendlyName, device.getFriendlyName());
				deviceArray.put(AlexaConstants.Description, "Smart thermostat from iMonitor Solutions");
				JSONArray displaycateg = new JSONArray(); 
				displaycateg.put("THERMOSTAT");
				deviceArray.put(AlexaConstants.DisplayCategories, displaycateg);
				deviceArray.put(AlexaConstants.Cookie, cookie);

				JSONArray deviceCapabilities = new JSONArray();
				deviceCapabilities.put(getThermostatCapabability());
				deviceCapabilities.put(getinterfaceCapabability());
				deviceCapabilities.put(getendpointCapability());
				deviceArray.put(AlexaConstants.Capabilities, deviceCapabilities);

				JSONArray endPoints = new JSONArray();
				endPoints.put(deviceArray);
				endPointsArray.put(AlexaConstants.Endpoints, endPoints);
				endPointsArray.put(AlexaConstants.Scope, scope);
				updatediscovery.put(AlexaConstants.Header, header);
				updatediscovery.put(AlexaConstants.Payload, endPointsArray);

				finalResponse.put(AlexaConstants.Events, updatediscovery);
				
				try 
				{
					boolean result=updateAlexaEndPoint(finalResponse,alexauser);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				//Temperature Sensor block
				HashMap<String,String> cookie1 = new HashMap<String, String>();
				cookie1.put("customerId",customer.getCustomerId() );
				cookie1.put("gateway", String.valueOf(device.getGateWay().getId()));
				cookie1.put("category","TEMPERATURE_SENSOR");
				cookie1.put("devtype","ZwaveAc");
				cookie1.put("device",device.getDeviceType().getName());
				
				JSONObject deviceArray1 = new JSONObject();
				deviceArray1.put(AlexaConstants.EndpointId, device.getDeviceId()+"-TEMPERATURE_SENSOR");
				deviceArray1.put(AlexaConstants.Manufacturer, "iMonitor Solutions");
				deviceArray1.put(AlexaConstants.FriendlyName, device.getFriendlyName()+ " Sensor");
				deviceArray1.put(AlexaConstants.Description, "Smart Sensor from iMonitor Solutions");
				JSONArray displaycateg1 = new JSONArray(); 
				displaycateg1.put("TEMPERATURE_SENSOR");
				deviceArray1.put(AlexaConstants.DisplayCategories, displaycateg1);
				deviceArray1.put(AlexaConstants.Cookie, cookie1);
				
				JSONArray deviceCapabilities1 = new JSONArray();
				deviceCapabilities1.put(getSensorCapabability());
				deviceCapabilities1.put(getinterfaceCapabability());
				deviceCapabilities1.put(getendpointCapability());
				deviceArray1.put(AlexaConstants.Capabilities, deviceCapabilities1);
				
				JSONArray endPoints1 = new JSONArray();
				endPoints1.put(deviceArray1);
				endPointsArray.put(AlexaConstants.Endpoints, endPoints1);
				//endPointsArray.put(AlexaConstants.Scope, scope);
				//updatediscovery.put(AlexaConstants.Header, header);
				updatediscovery.put(AlexaConstants.Payload, endPointsArray);
				
				finalResponse.put(AlexaConstants.Events, updatediscovery);
				

				try 
				{
					boolean result=updateAlexaEndPoint(finalResponse,alexauser);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
			
			
			if(device.getDeviceType().getName().equals(Constants.Z_WAVE_MULTI_SENSOR)) {

				HashMap<String,String> cookie = new HashMap<String, String>();
				cookie.put("customerId",customer.getCustomerId() );
				cookie.put("gateway", String.valueOf(device.getGateWay().getId()));
				cookie.put("category",Constants.Z_WAVE_MULTI_SENSOR);

				JSONObject deviceArray = new JSONObject();
				deviceArray.put(AlexaConstants.EndpointId, device.getDeviceId());
				deviceArray.put(AlexaConstants.Manufacturer, "iMonitor Solutions");
				deviceArray.put(AlexaConstants.FriendlyName, device.getFriendlyName());
				deviceArray.put(AlexaConstants.Description, "Motion Sensor from iMonitor Solutions");
				JSONArray displaycateg = new JSONArray(); //new String[] { "ACTIVITY_TRIGGER" };
				displaycateg.put("MOTION_SENSOR");
				deviceArray.put(AlexaConstants.DisplayCategories, displaycateg);
				deviceArray.put(AlexaConstants.Cookie, cookie);

				JSONArray deviceCapabilities = new JSONArray();
				deviceCapabilities.put(getDimmerCapabability());
				deviceCapabilities.put(getinterfaceCapabability());
				deviceCapabilities.put(getendpointCapability());
				deviceArray.put(AlexaConstants.Capabilities, deviceCapabilities);

				JSONArray endPoints = new JSONArray();
				endPoints.put(deviceArray);
				endPointsArray.put(AlexaConstants.Endpoints, endPoints);
				endPointsArray.put(AlexaConstants.Scope, scope);
				updatediscovery.put(AlexaConstants.Header, header);
				updatediscovery.put(AlexaConstants.Payload, endPointsArray);

				finalResponse.put(AlexaConstants.Events, updatediscovery);

				
				
				try 
				{
					boolean result=updateAlexaEndPoint(finalResponse,alexauser);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
			}
			
			if(device.getDeviceType().getName().equals(Constants.Z_WAVE_DOOR_SENSOR)) {

				HashMap<String,String> cookie = new HashMap<String, String>();
				cookie.put("customerId",customer.getCustomerId() );
				cookie.put("gateway", String.valueOf(device.getGateWay().getId()));
				cookie.put("category",Constants.Z_WAVE_DOOR_SENSOR);

				JSONObject deviceArray = new JSONObject();
				deviceArray.put(AlexaConstants.EndpointId, device.getDeviceId());
				deviceArray.put(AlexaConstants.Manufacturer, "iMonitor Solutions");
				deviceArray.put(AlexaConstants.FriendlyName, device.getFriendlyName());
				deviceArray.put(AlexaConstants.Description, "Door Sensor from iMonitor Solutions");
				JSONArray displaycateg = new JSONArray(); //new String[] { "ACTIVITY_TRIGGER" };
				displaycateg.put("CONTACT_SENSOR");
				deviceArray.put(AlexaConstants.DisplayCategories, displaycateg);
				deviceArray.put(AlexaConstants.Cookie, cookie);

				JSONArray deviceCapabilities = new JSONArray();
				deviceCapabilities.put(getDimmerCapabability());
				deviceCapabilities.put(getinterfaceCapabability());
				deviceCapabilities.put(getendpointCapability());
				deviceArray.put(AlexaConstants.Capabilities, deviceCapabilities);

				JSONArray endPoints = new JSONArray();
				endPoints.put(deviceArray);
				endPointsArray.put(AlexaConstants.Endpoints, endPoints);
				endPointsArray.put(AlexaConstants.Scope, scope);
				updatediscovery.put(AlexaConstants.Header, header);
				updatediscovery.put(AlexaConstants.Payload, endPointsArray);

				finalResponse.put(AlexaConstants.Events, updatediscovery);
				try 
				{
					boolean result=updateAlexaEndPoint(finalResponse,alexauser);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
			}

		} catch (JSONException e) {
			LogUtil.info("exception occured while preparing alexa message format: " + e.getMessage());
		}
	}

	public JSONObject getSwicthCapabability() throws JSONException {
		JSONObject switchCapability = new JSONObject();
		switchCapability.put(AlexaConstants.Type, "AlexaInterface");
		switchCapability.put(AlexaConstants.Interface, "Alexa.PowerController");
		switchCapability.put(AlexaConstants.Version, "3");

		JSONObject Content = new JSONObject();
		Content.put("name", "powerState");
		JSONArray contProp = new JSONArray();
		contProp.put(Content);
		JSONObject sup = new JSONObject();
		sup.put("supported", contProp);
		switchCapability.put("properties", sup);
		switchCapability.put(AlexaConstants.ProactivelyReported, true);
		switchCapability.put(AlexaConstants.Retrievable, true);
		return switchCapability;

	}


	public JSONObject getDimmerCapabability() throws JSONException {
		JSONObject dimmerCapability = new JSONObject();
		dimmerCapability.put(AlexaConstants.Type, "AlexaInterface");
		dimmerCapability.put(AlexaConstants.Interface, "Alexa.BrightnessController");
		dimmerCapability.put(AlexaConstants.Version, "3");

		JSONObject Content = new JSONObject();
		Content.put("name", "brightness");
		JSONArray contProp = new JSONArray();
		contProp.put(Content);
		JSONObject sup = new JSONObject();
		sup.put("supported", contProp);
		dimmerCapability.put("properties", sup);
		dimmerCapability.put(AlexaConstants.ProactivelyReported, true);
		dimmerCapability.put(AlexaConstants.Retrievable, true);
		return dimmerCapability;

	}
	
	
	public JSONObject getThermostatCapabability() throws JSONException {
		JSONObject dimmerCapability = new JSONObject();
		dimmerCapability.put(AlexaConstants.Type, "AlexaInterface");
		dimmerCapability.put(AlexaConstants.Interface, "Alexa.ThermostatController");
		dimmerCapability.put(AlexaConstants.Version, "3");

		JSONObject Content = new JSONObject();
		Content.put("name", "lowerSetpoint");
		Content.put("name", "targetSetpoint");
		Content.put("name", "upperSetpoint");
		Content.put("name", "thermostatMode");
		JSONArray contProp = new JSONArray();
		contProp.put(Content);
		JSONObject sup = new JSONObject();
		sup.put("supported", contProp);
		dimmerCapability.put("properties", sup);
		dimmerCapability.put(AlexaConstants.ProactivelyReported, true);
		dimmerCapability.put(AlexaConstants.Retrievable, true);
		return dimmerCapability;

	}

	public JSONObject getinterfaceCapabability() throws JSONException {
		JSONObject interfaceCapability = new JSONObject();
		interfaceCapability.put(AlexaConstants.Type, "AlexaInterface");
		interfaceCapability.put(AlexaConstants.Interface, "Alexa");
		interfaceCapability.put(AlexaConstants.Version, "3");
		return interfaceCapability;

	}

	public JSONObject getendpointCapability() throws JSONException {
		JSONObject endpointCapability = new JSONObject();
		endpointCapability.put(AlexaConstants.Type, "AlexaInterface");
		endpointCapability.put(AlexaConstants.Interface, "Alexa.EndpointHealth");
		endpointCapability.put(AlexaConstants.Version, "3");
		JSONObject Content = new JSONObject();
		Content.put("name", "connectivity");
		JSONArray contProp = new JSONArray();
		contProp.put(Content);
		JSONObject sup = new JSONObject();
		sup.put(AlexaConstants.Supported, contProp);
		endpointCapability.put("properties", sup);
		endpointCapability.put(AlexaConstants.ProactivelyReported, "true");
		endpointCapability.put(AlexaConstants.Retrievable, "true");

		return endpointCapability;

	}
	
	
	
	//Changed by APoorva
	public boolean updateAlexaEndPoint(JSONObject finalResponse,Alexa alexauser) throws Exception
	{
		boolean result= false;
		Alexamanager alexamanager = new Alexamanager();
		//JSONObject finalResponse = new JSONObject();
				try {

					URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_EVENT_POINT));
					HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
					con.setRequestMethod("POST");
					con.setRequestProperty("Host", "api.amazonalexa.com");
					con.setRequestProperty("Authorization", "Bearer "+alexauser.getSkillToken());
					con.setRequestProperty("Content-Type", "application/json");
					con.setDoOutput(true);
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					wr.writeBytes(finalResponse.toString());
					wr.flush();
					wr.close();



					int responseCode = con.getResponseCode();

					LogUtil.info("Response Code from alexa end point : " + responseCode);
					if(responseCode == 403){


						alexamanager.deleteAlexaUser(alexauser);

					}
						result = true;
				}
				catch (Exception e) {
					LogUtil.info("could not send message exception occuered: " + e.getMessage());
				}
		return result;
	}
	
	
	public JSONObject getSensorCapabability() throws JSONException {
		JSONObject dimmerCapability = new JSONObject();
		dimmerCapability.put(AlexaConstants.Type, "AlexaInterface");
		dimmerCapability.put(AlexaConstants.Interface, "Alexa.TemperatureSensor");
		dimmerCapability.put(AlexaConstants.Version, "3");

		JSONObject Content = new JSONObject();
		Content.put("name", "temperature");
		JSONArray contProp = new JSONArray();
		contProp.put(Content);
		JSONObject sup = new JSONObject();
		sup.put("supported", contProp);
		dimmerCapability.put("properties", sup);
		dimmerCapability.put(AlexaConstants.ProactivelyReported, true);
		dimmerCapability.put(AlexaConstants.Retrievable, true);
		return dimmerCapability;

	}
	
	public JSONObject getMotionSensorCapabability() throws JSONException {
		JSONObject sensorCapability = new JSONObject();
		sensorCapability.put(AlexaConstants.Type, "AlexaInterface");
		sensorCapability.put(AlexaConstants.Interface, "Alexa.MotionSensor");
		sensorCapability.put(AlexaConstants.Version, "3");

		JSONObject Content = new JSONObject();
		Content.put("name", "MotionSensor");
		JSONArray contProp = new JSONArray();
		contProp.put(Content);
		JSONObject sup = new JSONObject();
		sup.put("supported", contProp);
		sensorCapability.put("properties", sup);
		sensorCapability.put(AlexaConstants.ProactivelyReported, true);
		sensorCapability.put(AlexaConstants.Retrievable, true);
		return sensorCapability;

	}

	public JSONObject getDoorSensorCapabability() throws JSONException {
		JSONObject sensorCapability = new JSONObject();
		sensorCapability.put(AlexaConstants.Type, "AlexaInterface");
		sensorCapability.put(AlexaConstants.Interface, "Alexa.ContactSensor");
		sensorCapability.put(AlexaConstants.Version, "3");

		JSONObject Content = new JSONObject();
		Content.put("name", "ContactSensor");
		JSONArray contProp = new JSONArray();
		contProp.put(Content);
		JSONObject sup = new JSONObject();
		sup.put("supported", contProp);
		sensorCapability.put("properties", sup);
		sensorCapability.put(AlexaConstants.ProactivelyReported, true);
		sensorCapability.put(AlexaConstants.Retrievable, true);
		return sensorCapability;

	}


}
