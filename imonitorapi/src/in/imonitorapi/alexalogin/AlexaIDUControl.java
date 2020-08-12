package in.imonitorapi.alexalogin;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.Constants;
import in.imonitorapi.util.LogUtil;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/ViaUnitControl")
public class AlexaIDUControl 
{
	@GET
	@Produces("application/xml")
	public Response alexaIDUVoicecontrol(@HeaderParam("deviceid")String deviceId,@HeaderParam("customerid") String cId,@HeaderParam("gateway") String gatewayId,@QueryParam("command")String command,@QueryParam("value")String value,@QueryParam("deviceType")String deviceType)throws Exception{
		LogUtil.info("AlexaIDUControl method start");
		LogUtil.info("device Id: " + deviceId+" customer"+cId+" command"+command+" value"+value + " gateway "+ gatewayId + " deviceType: " + deviceType);
		String result = null;
		if((deviceType.equals(Constants.ViaUnit) || (deviceType.equals(Constants.VIA_UNIT)) ))
		{
			 result=ClientController.sendToController("alexaService","voiceControlForIDU",deviceId,cId,gatewayId,command,value);
			 LogUtil.info("IDU control result : " + result);
		}
		return Response.status(200)
				.entity(result)
				.build();
}
}
