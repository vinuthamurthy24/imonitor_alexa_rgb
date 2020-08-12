package in.xpeditions.jawlin.imonitor.util;

import org.codehaus.jettison.json.JSONArray;

import net.sf.json.JSONObject;

public class AlexaDeviceCapabilities {
	
	private static JSONObject switchCapability = new JSONObject();
	private static JSONObject dimmerCapability = new JSONObject();
	private static JSONObject acCapability = new JSONObject();
	private static JSONObject endpointCapability = new JSONObject();
	private static JSONObject interfaceCapability = new JSONObject();
	private static JSONObject scenarioCapability = new JSONObject();
	private static JSONObject videoCapability = new JSONObject();
	
	
	public JSONObject getSwicthCapabability() {
		
		switchCapability.put("type", "AlexaInterface");
		switchCapability.put("interface", "Alexa.PowerController");
		switchCapability.put("version", "3");
		
		JSONObject Content = new JSONObject();
		Content.put("name", "powerState");
		JSONArray contProp = new JSONArray();
		contProp.put(Content);
		JSONObject sup = new JSONObject();
		sup.put("supported", "contProp");
		switchCapability.put("properties", sup);
		switchCapability.put("proactivelyReported", "true");
		switchCapability.put("retrievable", "true");
		return switchCapability;
		
	}
	
public JSONObject getVideoCapabability() {
		
		switchCapability.put("type", "AlexaInterface");
		switchCapability.put("interface", "Alexa.RemoteVideoPlayer");
		switchCapability.put("version", "3");
		
		JSONObject Content = new JSONObject();
		Content.put("name", "powerState");
		JSONArray contProp = new JSONArray();
		contProp.put(Content);
		JSONObject sup = new JSONObject();
		sup.put("supported", "contProp");
		switchCapability.put("properties", sup);
		switchCapability.put("proactivelyReported", "true");
		switchCapability.put("retrievable", "true");
		return switchCapability;
		
	}
	
	public JSONObject getRGBCapabability() {
		
		switchCapability.put("type", "AlexaInterface");
		switchCapability.put("interface", "Alexa.ColorController");
		switchCapability.put("version", "3");
		
		JSONObject Content = new JSONObject();
		Content.put("name", "color");
		JSONArray contProp = new JSONArray();
		contProp.put(Content);
		JSONObject sup = new JSONObject();
		sup.put("supported", "contProp");
		switchCapability.put("properties", sup);
		switchCapability.put("proactivelyReported", "true");
		switchCapability.put("retrievable", "true");
		return switchCapability;
		
	}
	
public JSONObject getinterfaceCapabability() {
		
	interfaceCapability.put("type", "AlexaInterface");
	interfaceCapability.put("interface", "Alexa");
	interfaceCapability.put("version", "3");
		
		
		return interfaceCapability;
		
	}

public JSONObject getendpointCapability() {
	
	endpointCapability.put("type", "AlexaInterface");
	endpointCapability.put("interface", "Alexa.EndpointHealth");
	endpointCapability.put("version", "3");
	JSONObject Content = new JSONObject();
	Content.put("name", "connectivity");
	JSONArray contProp = new JSONArray();
	contProp.put(Content);
	JSONObject sup = new JSONObject();
	sup.put("supported", "contProp");
	endpointCapability.put("properties", sup);
	endpointCapability.put("proactivelyReported", "true");
	endpointCapability.put("retrievable", "true");
		
		return endpointCapability;
		
	}



	

}
