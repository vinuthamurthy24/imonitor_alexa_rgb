package imonitor.cms.alexa.proactive.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.Alexamanager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.AlexaConstants;
import in.xpeditions.jawlin.imonitor.util.Constants;

public class AlexaProactiveDeleteDeviceService implements Runnable{
	
	private Device device;
	private Alexa alexa;
	public  boolean toDeleteDevice = false;
	public Scenario scenario;
	
	public AlexaProactiveDeleteDeviceService(Alexa alexauser,Device device) {
        
        this.alexa = alexauser;
        this.device=device;
        this.toDeleteDevice=true;
    }
	
   public AlexaProactiveDeleteDeviceService(Alexa alexauser,Scenario scenario) {
        
        this.alexa = alexauser;
        this.scenario=scenario;
       
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Alexamanager alexamanager = new Alexamanager();
		String expireTime = alexa.getTokenExpires();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		try {
			Date expireDate = dateFormat.parse(expireTime);
			if(date.after(expireDate)){
				
				try {
					//URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_TOKEN_END_POINT));
					URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_TOKEN_END_POINT));
					HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
					con.setRequestMethod("POST");
					con.setRequestProperty("Host", "api.amazon.com");
					con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
					//String parameters = "grant_type=refresh_token&refresh_token="+alexa.getSkillRefreshToken()+"&client_id="+ControllerProperties.getProperties().get(Constants.ALEXA_CLIENT_ID)+"&client_secret="+ControllerProperties.getProperties().get(Constants.ALEXA_SECRET);
					String parameters = "grant_type=refresh_token&refresh_token="+alexa.getSkillRefreshToken()+"&client_id="+ControllerProperties.getProperties().get(Constants.ALEXA_CLIENT_ID)+"&client_secret="+ControllerProperties.getProperties().get(Constants.ALEXA_SECRET);
		
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
				   
				    if(alexa != null){
						
						alexa.setSkillToken(accessToken);
						alexa.setSkillRefreshToken(refreshToken);
						alexa.setTokenExpires(dateFormat.format(afterAddingMins));
						boolean update = alexamanager.updateAlexaUser(alexa);
						
					}
				} catch (Exception e) {
					LogUtil.info("error getting access token from alexa : "  +e.getMessage());
				}
							
			}
			
					
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
	}
		
		JSONObject header = new JSONObject();
		JSONObject scope = new JSONObject();
		JSONObject endPointsArray = new JSONObject();
		JSONObject updatediscovery = new JSONObject();
		JSONObject finalResponse = new JSONObject();
	
		try {
			header.put(AlexaConstants.MessageId, UUID.randomUUID());
			header.put(AlexaConstants.Name, AlexaConstants.DeleteReport);
			header.put(AlexaConstants.NameSpace, AlexaConstants.AlexaDiscovery);
			header.put(AlexaConstants.PayloadVersion,  "3");
			
			JSONArray endPoints = new JSONArray();
			JSONObject deviceArray = new JSONObject();
			if(toDeleteDevice) {
		//	deviceArray.put(AlexaConstants.EndpointId, device.getDeviceId());
		
			if(device.getDeviceType().getName().equals("Z_WAVE_AC_EXTENDER"))
			{
				deviceArray.put(AlexaConstants.EndpointId, device.getDeviceId());
				JSONObject sensorArray = new JSONObject();
				sensorArray.put(AlexaConstants.EndpointId, device.getDeviceId()+"-TEMPERATURE_SENSOR");
				endPoints.put(deviceArray);
				endPoints.put(sensorArray);
		
			}else{
				
				deviceArray.put(AlexaConstants.EndpointId, device.getDeviceId());
				endPoints.put(deviceArray);
			}
			
			
			}else {
				deviceArray.put(AlexaConstants.EndpointId, scenario.getId());
				endPoints.put(deviceArray);
			}
			
			
			
			scope.put(AlexaConstants.Type, AlexaConstants.BearerToken);
			scope.put(AlexaConstants.Token, alexa.getSkillToken());
			
			endPointsArray.put(AlexaConstants.Endpoints, endPoints);
			endPointsArray.put(AlexaConstants.Scope, scope);
			
			updatediscovery.put(AlexaConstants.Header, header);
			updatediscovery.put(AlexaConstants.Payload, endPointsArray);
			
			finalResponse.put(AlexaConstants.Events, updatediscovery);
			
			
			
			//call method
			try 
			{
				boolean result=updateAlexaEndPoint(finalResponse,alexa);
				
			} catch (Exception e)
			{
				e.printStackTrace();
				LogUtil.info("Excep :"+e.getMessage());
			}
			
		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.info("Excep Sensor :"+e.getMessage());
		}
		
		

		
		

}
	
	//Added by apoorva
	public boolean updateAlexaEndPoint(JSONObject finalResponse,Alexa alexauser) throws Exception
	{
		boolean result= false;
		Alexamanager alexamanager = new Alexamanager();
		try {
			
			URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_EVENT_POINT));
			//URL obj = new URL("https://api.eu.amazonalexa.com/v3/events");
			
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			
			try {
				
				con.setRequestMethod("POST");
			} catch (Exception e) {
				LogUtil.info("exception occuered: " + e.getMessage());
			}
			
		
			con.setRequestProperty("Host", "api.amazonalexa.com");
			
			con.setRequestProperty("Authorization", "Bearer "+alexa.getSkillToken());
		
			con.setRequestProperty("Content-Type", "application/json");
			
			con.setDoOutput(true);
			
			try {
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				
				wr.writeBytes(finalResponse.toString());
				
				wr.flush();
				wr.close();
			} catch (Exception e) {
				LogUtil.info("exception occured now:" + e.getMessage());
			}
			
			int responseCode = con.getResponseCode();
			
			LogUtil.info("Response Code from alexa end point : " + responseCode);
			if(responseCode == 403){
				
				
				alexamanager.deleteAlexaUser(alexa);
				
			}
			result= true;
		} 
		catch (Exception e) {
			LogUtil.info("could not send message exception occuered: " + e.getMessage());
		}
		return result;
	}
	
	
	
	
}