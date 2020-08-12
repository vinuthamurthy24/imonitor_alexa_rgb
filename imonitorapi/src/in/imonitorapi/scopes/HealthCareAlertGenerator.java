package in.imonitorapi.scopes;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import in.imonitor.cms.communicator.IMonitorControllerCommunicator;
import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.mobile.login.OAuth2Util;
import in.imonitorapi.service.manager.JsonMessageParser;
import in.imonitorapi.util.LogUtil;
import in.imonitorapi.util.XmlUtil;

import in.imonitorapi.util.KeyValuePair;
import in.imonitorapi.util.Constants;



import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mysql.jdbc.log.LogUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.thoughtworks.xstream.XStream;




@Path("/execute")
public class HealthCareAlertGenerator {
	
	private String data;
	
@POST
@Path("/{event}/{email}/{transid}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response UpdateAlert (@PathParam("event") String event, 
	        @PathParam("email") String eMail, 
	        @PathParam("transid") String transactionId) throws Exception{
	 
	
	String source = "iMonitor";
	String status = "success";
	XStream stream = new XStream();
	String deviceEvent = stream.toXML(event);
	String customerEmail = stream.toXML(eMail);
	String result = ClientController.sendToController("alertService", "handleFallEvent", deviceEvent, customerEmail);
	Queue<KeyValuePair> queue = HealthCareAlertGenerator.extractCommandsQueueFromXml(result);
	String uploadStatus = HealthCareAlertGenerator.commandId(queue, Constants.STATUS);
	LogUtil.info("this is the result: "+ uploadStatus);
	String imageUrl = null;
	if(uploadStatus.equals("Done")){
		
		String filePath = HealthCareAlertGenerator.commandId(queue, Constants.FILE_PATH);
		String fileName = HealthCareAlertGenerator.commandId(queue, Constants.FILE_NAME);
		String protocol = IMonitorControllerCommunicator.getControllerProtocol();
		String address = "10.0.0.16";
		//String address = IMonitorControllerCommunicator.getControllerAddress();	
		String port = IMonitorControllerCommunicator.getControllerPort();
	    imageUrl = protocol + "://" + address + ":" + port
				+ "/ftpuploads/"+filePath+fileName;
	}else{
		
		status = "failure";
		imageUrl = null;
	}

	JsonMessageParser jso = new JsonMessageParser(event,eMail,source,transactionId,status,imageUrl);
	

    		
   /* Client client = Client.create();
 	WebResource webresource = client.resource("http://10.0.0.15:8080/imonitorapi/action/sample/json");
	webresource.post();
	LogUtil.info("uri: "+ webresource.getURI());
	webresource.entity(jso, "application/json");*/
	

	
	/*Client wsClient = Client.create();
    WebResource rs = wsClient.resource("http://10.0.0.15:8080/imonitorapi/action/sample/json");
    String response = rs.path("http://10.0.0.15:8080/imonitorapi/action/sample/json").type(MediaType.APPLICATION_JSON).post(String.class, jso);

	LogUtil.info("Complete");*/
	
	
	
	LogUtil.info("json Object: "+ jso);
	
	
	URL requestUrl;
	HttpURLConnection connection = null;  
	String targetURL="http://techmhealthplatform.appspot.com:8888/rest/homeautomation/json";
	LogUtil.info("step1: ");
	String urlParameters=jso.toString();
	try {
	      //Create connection
		  requestUrl = new URL(targetURL);
		  LogUtil.info("step2: "+ requestUrl);
	      connection = (HttpURLConnection)requestUrl.openConnection();
	      LogUtil.info("step3: ");
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", 
	           "application/json");
	      LogUtil.info("step4: ");
	      connection.setRequestProperty("Content-Length", "" + 
	               Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
	      LogUtil.info("step5: ");
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();
	      LogUtil.info("step6:");
	      //Get Response    
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      LogUtil.info("message="+response.toString());

	    } catch (Exception e) {

	      e.printStackTrace();
	      LogUtil.info("step7:");

	    } finally {

	      if(connection != null) {
	        connection.disconnect();
	        LogUtil.info("step8: ");
	      }
	    }
	
	//return jso;
	
    return Response.status(200).build();
	
	
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
public static String commandId(Queue<KeyValuePair> events,String commandId) {
	for (KeyValuePair keyValuePair : events) {
		if(keyValuePair.getKey().equals(commandId)){
			return keyValuePair.getValue();
		}
	}
	return null;
}
}
