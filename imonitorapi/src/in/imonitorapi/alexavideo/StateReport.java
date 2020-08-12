package in.imonitorapi.alexavideo;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

@Path("/statereport")
public class StateReport {

	@GET
	@Produces("application/xml")
	public Response getDeviceState(@HeaderParam("deviceid")String generated,@HeaderParam("gatewayId") String id,@HeaderParam("deviceType") String type)throws Exception{
		XStream stream = new XStream();
		LogUtil.info(generated+" Devicename "+"gateway"+id + "device Type: " + type);
			String out="<imonitor>";
			String result=ClientController.sendToController("alexaService","StateReport",generated,id, type);
			LogUtil.info(result);
			
			
			
		return Response.status(200)
				.entity(result)
				.build();
		
	}
	
	
	
}