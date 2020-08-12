package in.imonitorapi.scopes;

import in.imonitor.cms.communicator.IMonitorControllerCommunicator;
import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.service.manager.JsonMessageParser;
import in.imonitorapi.util.Constants;
import in.imonitorapi.util.KeyValuePair;
import in.imonitorapi.util.LogUtil;

import java.util.Queue;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.thoughtworks.xstream.XStream;


@Path("/sample")
public class SampleToTestJson {
	
	@POST
	@Path("/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response TestJsonObject () throws Exception{
		 
				
	    LogUtil.info("json object:");
			
		
	    return Response.status(200).build();
		
		
		}

}
