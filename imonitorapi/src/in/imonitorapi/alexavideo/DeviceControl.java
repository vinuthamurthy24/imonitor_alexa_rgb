package in.imonitorapi.alexavideo;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

@Path("/control")
public class DeviceControl {

	@GET
	@Produces("application/xml")
	public Response getdevicecontrol(@HeaderParam("deviceid")String generated,@HeaderParam("customerid") String id,@HeaderParam("gateway") String gatewayId,@QueryParam("command")String command,@QueryParam("value")String value)throws Exception{
		//XStream stream = new XStream();
		LogUtil.info(generated+" Devicename "+"customer"+id+"command"+command+"value"+value + " gateway "+ gatewayId);
			String out="<imonitor>";
			String result=ClientController.sendToController("deviceService","alexacontrolApi",generated,id,gatewayId,command,value);
			LogUtil.info(result);
			if(result!=null){
				out+="<GenerateddeviceId>" + generated + "</GenerateddeviceId>" + "<Status>" + "Success" + "</Status>" + "</imonitor>";
			}else{
				out+="<GenerateddeviceId>"+generated+"</GenerateddeviceId>"+"<Status>"+"Failure"+"</Status>"+"</imonitor>";
				}
		return Response.status(200)
				.entity(out)
				.build();
		
	}
}