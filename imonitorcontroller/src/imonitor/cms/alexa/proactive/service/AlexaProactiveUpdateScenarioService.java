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



import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.Alexamanager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.AlexaConstants;
import in.xpeditions.jawlin.imonitor.util.Constants;

public class AlexaProactiveUpdateScenarioService implements Runnable{

	private Scenario scenario;
	private Customer customer;
	
	
	public AlexaProactiveUpdateScenarioService(Scenario scenario, Customer customer) {
		this.scenario = scenario;
		this.customer = customer;
		
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		CustomerManager customerManager = new CustomerManager();
		Alexamanager alexamanager = new Alexamanager();
		customer = customerManager.getCustomerByCustomerId(customer.getCustomerId());
		Alexa alexaUser = alexamanager.getAlexaUserByCustomerId(customer);
		String expireTime = alexaUser.getTokenExpires();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		
		
		try {
			Date expireDate = dateFormat.parse(expireTime);
			if(date.after(expireDate)){
			
				try {
					URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_TOKEN_END_POINT));
					//LogUtil.info("alexa token end point: " + ControllerProperties.getProperties().get(Constants.ALEXA_TOKEN_END_POINT));
				//	URL obj = new URL("https://api.amazon.com/auth/o2/token");
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
						boolean update = alexamanager.updateAlexaUser(alexaUser);
						
					}
				} catch (Exception e) {
					LogUtil.info("error getting access token from alexa : "  +e.getMessage());
				}
							
			}
			
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Update scenario
		JSONObject updatediscovery = new JSONObject();
		JSONObject header = new JSONObject();
		JSONObject scope = new JSONObject();
		JSONObject endPointsArray = new JSONObject();
		JSONObject finalResponse = new JSONObject();
		
		
		
		try {
			header.put(AlexaConstants.NameSpace, "Alexa.Discovery");
			header.put(AlexaConstants.Name, "AddOrUpdateReport");
			header.put(AlexaConstants.PayloadVersion, "3");
			header.put(AlexaConstants.MessageId,  UUID.randomUUID());
		
			scope.put(AlexaConstants.Type, AlexaConstants.BearerToken);
			scope.put(AlexaConstants.Token, alexaUser.getSkillToken());
			
			HashMap<String,String> cookie = new HashMap<String, String>();
			cookie.put("customerId",customer.getCustomerId() );
	        cookie.put("gateway", String.valueOf(scenario.getGateWay().getId()));
	        cookie.put(AlexaConstants.Category,"SCENARIO" );
			
			
			
			JSONObject scenarioArray = new JSONObject();
			scenarioArray.put(AlexaConstants.EndpointId, scenario.getId());
			scenarioArray.put(AlexaConstants.Manufacturer, "iMonitor Solutions");
			scenarioArray.put(AlexaConstants.FriendlyName, scenario.getName());
			scenarioArray.put(AlexaConstants.Description, scenario.getDescription());
			
			JSONArray displaycateg = new JSONArray(); //new String[] { "ACTIVITY_TRIGGER" };
			displaycateg.put(AlexaConstants.ACTIVITY_TRIGGER);
			scenarioArray.put(AlexaConstants.DisplayCategories, displaycateg);
			scenarioArray.put(AlexaConstants.Cookie, cookie);
			
			JSONArray scenarioCapabilities = new JSONArray();
			scenarioCapabilities.put(getScenarioCapabability());
			scenarioArray.put(AlexaConstants.Capabilities, scenarioCapabilities);
			
			
			JSONArray endPoints = new JSONArray();
			endPoints.put(scenarioArray);
			
			endPointsArray.put(AlexaConstants.Endpoints, endPoints);
			endPointsArray.put(AlexaConstants.Scope, scope);
			
			updatediscovery.put(AlexaConstants.Header, header);
			updatediscovery.put(AlexaConstants.Payload, endPointsArray);
			
			
			finalResponse.put(AlexaConstants.Events, updatediscovery);
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.info("Excc : "+e.getMessage());
		}
		
		//Update to alexa end point
		try {
			
			//URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_EVENT_POINT));
			URL obj = new URL(ControllerProperties.getProperties().get(Constants.ALEXA_EVENT_POINT));
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
			con.setRequestMethod("POST");
			con.setRequestProperty("Host", "api.amazon.com");
			con.setRequestProperty("Authorization", "Bearer "+alexaUser.getSkillToken());
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(finalResponse.toString());
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			
			LogUtil.info("Response Code from alexa end point : " + responseCode);
			if(responseCode == 403){
				
				
				alexamanager.deleteAlexaUser(alexaUser);
				
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
			LogUtil.info("Exccep : "+e.getMessage());
		}
		
		
		
		
		
	}

	
	public JSONObject getScenarioCapabability() throws JSONException {
		JSONObject scenarioCapability = new JSONObject();
		scenarioCapability.put(AlexaConstants.Type, AlexaConstants.AlexaInterface);
		scenarioCapability.put(AlexaConstants.Interface, AlexaConstants.AlexaSceneController);
		scenarioCapability.put(AlexaConstants.Version, "3");
		scenarioCapability.put(AlexaConstants.SupportsDeactivation, "false");
		scenarioCapability.put(AlexaConstants.ProactivelyReported, "true");
		return scenarioCapability;
		
	};
	
}
