package in.imonitorapi.alexa.eurovigil;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import in.imonitorapi.util.LogUtil;

@Path("/proactive")
public class EFLProactiveStateUpdate {

	 @POST
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	public JSONObject  getTokensForCustomer(JSONObject inputJsonObj)throws Exception{
		
		  

		    String input = (String) inputJsonObj.get("input");
		    String output = "The input you sent is :" + input;
		    JSONObject outputJsonObj = new JSONObject();
		    outputJsonObj.put("output", output);

		    return outputJsonObj;
		
		
	}
	
	
	
}
