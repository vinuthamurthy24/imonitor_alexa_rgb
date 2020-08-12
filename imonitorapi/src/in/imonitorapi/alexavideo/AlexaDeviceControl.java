package in.imonitorapi.alexavideo;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.Constants;
import in.imonitorapi.util.LogUtil;

@Path("/ZwaveControl")
public class AlexaDeviceControl {
	
	@GET
	@Produces("application/xml")
	public Response alexaZwaveVoicecontrol(@HeaderParam("deviceid")String deviceId,@HeaderParam("customerid") String cId,@HeaderParam("gateway") String gatewayId,@QueryParam("command")String command,@QueryParam("value")String value,@QueryParam("deviceType")String deviceType)throws Exception{
		LogUtil.info("AV blaster !!!!!!!!!");
		LogUtil.info("device Id: " + deviceId+" customer"+cId+" command"+command+" value"+value + " gateway "+ gatewayId + " deviceType: " + deviceType);
		String result = null;
		if((deviceType.equals(Constants.Z_WAVE_AV_BLASTER))){
			LogUtil.info(" Z_WAVE_AV_BLASTER");
			 result=ClientController.sendToController("alexaService","voiceControlForAVBlaster",deviceId,cId,gatewayId,command,value);
			 LogUtil.info("AV blaster result!!!!!!!!!"+result);
		}
		return Response.status(200)
				.entity(result)
				.build();
		
	}
}
