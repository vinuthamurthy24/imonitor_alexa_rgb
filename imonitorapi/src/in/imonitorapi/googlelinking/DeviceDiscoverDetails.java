package in.imonitorapi.googlelinking;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import in.imonitorapi.alexalogin.DeviceDiscoverDetails.discoveredAppliances;
import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.Constants;
import in.imonitorapi.util.LogUtil;

@Path("/Devicediscover")
public class DeviceDiscoverDetails {
	
	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public  Response discoverdevice(@HeaderParam("type") String type,@HeaderParam("accesstoken") String accessToken) throws Exception{
		XStream stream = new XStream();
		 LogUtil.info("access token: "+accessToken );
			String tokenXml = stream.toXML(accessToken);
			String customerxml = ClientController.sendToController("alexaService", "checkAlexaUserProfile",tokenXml);
			String customerId = (String)stream.fromXML(customerxml);
			LogUtil.info("customer"+customerId);
			//to be done to handle error if customer doesn't exist
			String result=ClientController.sendToController("customerService","Discoverdevicegoogle",customerId);
			LogUtil.info("devicedetails for alexa discover"+result);
			String scenarioList = ClientController.sendToController("alexaService","discoverScenarios",customerId);
			LogUtil.info("Scenario details: "+scenarioList);
			List<discoveredAppliances> discovered=null;
			if(result!=null){
			List<Object[]> objects=(List<Object[]>) stream.fromXML(result);
			discovered=new ArrayList<discoveredAppliances>();
			List<Object[]> scenarios=(List<Object[]>) stream.fromXML(scenarioList);
			 HashMap<String,String> additionalAttributes = new HashMap<String, String>();;
			
			for (Object[] obj:objects){
				
				String appid=obj[0].toString();
				String deviceName=obj[2].toString();
				discoveredAppliances discover=new discoveredAppliances();
				String zwtype=(String) obj[3];
				String desc;
				String[] displaycateg;
				String[] connections = new String[] {};
				
				HashMap<String,String> cookie;
				if(zwtype.equalsIgnoreCase("Z_WAVE_SWITCH")){
					LogUtil.info("Switch section");
					
					  desc="Smart Light";
					  displaycateg = new String[] { "LIGHT" };
					  cookie = new HashMap<String, String>();
			          cookie.put("customerId",customerId );
			          cookie.put("gateway", obj[1].toString());
			          cookie.put("device", zwtype);
			          discover.setEndpointId(appid);
			          discover.setFriendlyName(deviceName);
			          discover.setManufacturerName("iMonitor Solutions");
			          discover.setDescription(desc);
			          discover.setDisplayCategories(displaycateg);
			          discover.setCookie(cookie);
			          discover.setCapabilities(zwtype);
			          discover.setAdditionalAttributes(additionalAttributes);
			          discover.setConnections(connections);
			          discovered.add(discover);
				}
				
				else if(zwtype.equalsIgnoreCase("DEV_ZWAVE_RGB")){
					
					  desc="RGB COLOR";
					  displaycateg = new String[] {"LIGHT" };
					  cookie = new HashMap<String, String>();
			          cookie.put("customerId",customerId );
			          cookie.put("gateway", obj[1].toString());
			          cookie.put("device", zwtype);
			          discover.setEndpointId(appid);
			          discover.setFriendlyName(deviceName);
			          discover.setManufacturerName("iMonitor Solutions");
			          discover.setDescription(desc);
			          discover.setDisplayCategories(displaycateg);
			          discover.setCookie(cookie);
			          discover.setCapabilities(zwtype);
			          discover.setAdditionalAttributes(additionalAttributes);
			          discover.setConnections(connections);
			          discovered.add(discover);
				}
				
				
				
				else if(zwtype.equalsIgnoreCase("Z_WAVE_DIMMER")){
					
					BigInteger switchType = (BigInteger) obj[4];
					
					int stype = switchType.intValue();
					
					    if(stype == 3){
					    	desc = "Smart brightness controller by iMonitor";
				            displaycateg = new String[] { "LIGHT" };
				            cookie = new HashMap<String, String>();
				            cookie.put("customerId", customerId);
				            cookie.put("gateway", obj[1].toString());
				            cookie.put("device", zwtype);
				            discover.setEndpointId(appid);
				            discover.setFriendlyName(deviceName);
				            discover.setManufacturerName("iMonitor Solutions");
				            discover.setDescription(desc);
				            discover.setDisplayCategories(displaycateg);
				            discover.setCookie(cookie);
				            discover.setAdditionalAttributes(additionalAttributes);
				            discover.setConnections(connections);
				            discover.setCapabilities("FanController");
				            discovered.add(discover);
					    }else{
					    	desc = "Smart brightness controller by iMonitor";
					    	displaycateg = new String[] { "LIGHT" };
					    	cookie = new HashMap<String, String>();
					    	cookie.put("customerId", customerId);
					    	cookie.put("gateway", obj[1].toString());
					    	cookie.put("device", zwtype);
					    	discover.setEndpointId(appid);
					    	discover.setFriendlyName(deviceName);
					    	discover.setManufacturerName("iMonitor Solutions");
					    	discover.setDescription(desc);
					    	discover.setDisplayCategories(displaycateg);
					    	discover.setCookie(cookie);
					    	discover.setAdditionalAttributes(additionalAttributes);
					    	discover.setConnections(connections);
					    	discover.setCapabilities(zwtype);
					    	discovered.add(discover);
					    }
				}
				 else if (zwtype.equalsIgnoreCase("Z_WAVE_AC_EXTENDER"))
		          {
		             
					 desc = "Smart thermostat controller by iMonitor";
					 displaycateg = new String[] { "THERMOSTAT" };
					 cookie = new HashMap();
					 cookie.put("customerId", customerId);
					 cookie.put("gateway", obj[1].toString());
					 cookie.put("devtype", "ZwaveAc");
					 cookie.put("device", zwtype);
					 cookie.put("category","THERMOSTAT" );
					 discover.setEndpointId(appid);
					 discover.setFriendlyName(deviceName);
					 discover.setManufacturerName("iMonitor Solutions");
					 discover.setDescription(desc);
					 discover.setDisplayCategories(displaycateg);
					 discover.setCookie(cookie);
					 discover.setAdditionalAttributes(additionalAttributes);
					 discover.setConnections(connections);
					 discover.setCapabilities("THERMOSTAT");
					 discovered.add(discover);
		          
					 desc = "Smart temperature sencor by iMonitor";
					 discoveredAppliances discoverSencor=new discoveredAppliances();
					 displaycateg = new String[] { "TEMPERATURE_SENSOR" };
					 cookie = new HashMap();
					 cookie.put("customerId", customerId);
					 cookie.put("gateway", obj[1].toString());
					 cookie.put("devtype", "ZwaveAc");
					 cookie.put("device", zwtype);
					 cookie.put("category","TEMPERATURE_SENSOR" );
					 discoverSencor.setEndpointId(appid+"-TEMPERATURE_SENSOR");
					 discoverSencor.setFriendlyName(deviceName + " Sensor");
					 discoverSencor.setManufacturerName("iMonitor Solutions");
					 discoverSencor.setDescription(desc);
					 discoverSencor.setDisplayCategories(displaycateg);
					 discoverSencor.setCookie(cookie);
					 discover.setAdditionalAttributes(additionalAttributes);
					 discover.setConnections(connections);
					 discoverSencor.setCapabilities("TEMPERATURE_SENSOR");
					 discovered.add(discoverSencor);
					 
					 
		          }
				
				 else if (zwtype.equalsIgnoreCase("VIA_UNIT"))
		          {
		             LogUtil.info("VIA unit block");
					 desc = "Smart thermostat controller by iMonitor";
					 displaycateg = new String[] { "THERMOSTAT" };
					 cookie = new HashMap();
					 cookie.put("customerId", customerId);
					 cookie.put("gateway", obj[1].toString());
					 cookie.put("devtype", "ViaUnit");
					 cookie.put("device", zwtype);
					 cookie.put("category","THERMOSTAT" );
					 discover.setEndpointId(appid);
					 discover.setFriendlyName(deviceName);
					 discover.setManufacturerName("iMonitor Solutions");
					 discover.setDescription(desc);
					 discover.setDisplayCategories(displaycateg);
					 discover.setCookie(cookie);
					 discover.setAdditionalAttributes(additionalAttributes);
					 discover.setConnections(connections);
					 discover.setCapabilities("THERMOSTAT");
					 discovered.add(discover);
		          
					 desc = "Smart temperature sencor by iMonitor";
					 discoveredAppliances discoverSencor=new discoveredAppliances();
					 displaycateg = new String[] { "TEMPERATURE_SENSOR" };
					 cookie = new HashMap();
					 cookie.put("customerId", customerId);
					 cookie.put("gateway", obj[1].toString());
					 cookie.put("devtype", "ViaUnit");
					 cookie.put("device", zwtype);
					 cookie.put("category","TEMPERATURE_SENSOR" );
					 discoverSencor.setEndpointId(appid+"-TEMPERATURE_SENSOR");
					 discoverSencor.setFriendlyName(deviceName + " Sensor");
					 discoverSencor.setManufacturerName("iMonitor Solutions");
					 discoverSencor.setDescription(desc);
					 discoverSencor.setDisplayCategories(displaycateg);
					 discoverSencor.setCookie(cookie);
					 discover.setAdditionalAttributes(additionalAttributes);
					 discover.setConnections(connections);
					 discoverSencor.setCapabilities("TEMPERATURE_SENSOR");
					 discovered.add(discoverSencor);
					 
					 
		          }
			}
			
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
					 cookie.put("customerId", customerId);
					 cookie.put("gateway", obj[1].toString());
					 cookie.put("category","SCENARIO" );
					 discover.setDisplayCategories(displaycateg);
					 discover.setCookie(cookie);
					 discover.setCapabilities(Constants.ACTIVITY_TRIGGER);
					 discovered.add(discover);
				}
				
				
				
			}
			
			Gson gson=new Gson();
			Object output=gson.toJson(discovered);
			LogUtil.info("discoverdevice op:" + output);
			return Response.status(200).entity(output).build();
			}else{
				String output="<imonitor><status>Failure</status></imonitor>";
				return Response.status(200).entity(output).build();
			}
		}
		public class discoveredAppliances{
			private String endpointId;
			private String friendlyName;
			private String description;
			private String manufacturerName;
			private String[] displayCategories =  new String[5];
			private HashMap<String, String> cookie;
			private HashMap<String, String> additionalAttributes;
			private String capabilities;
			private String[] connections;
			
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
			public String getCapabilities() {
				return capabilities;
			}
			public void setCapabilities(String capabilities) {
				this.capabilities = capabilities;
			}
			public HashMap<String, String> getCookie() {
				return cookie;
			}
			public void setCookie(HashMap<String, String> cookie) {
				this.cookie = cookie;
			}
			public HashMap<String, String> getAdditionalAttributes() {
				return additionalAttributes;
			}
			public void setAdditionalAttributes(HashMap<String, String> additionalAttributes) {
				this.additionalAttributes = additionalAttributes;
			}
			public String[] getConnections() {
				return connections;
			}
			public void setConnections(String[] connections) {
				this.connections = connections;
			}	
		}

	}


