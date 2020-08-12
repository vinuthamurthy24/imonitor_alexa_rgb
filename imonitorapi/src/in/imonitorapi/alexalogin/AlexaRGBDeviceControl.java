package in.imonitorapi.alexalogin;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.Constants;
import in.imonitorapi.util.LogUtil;

@Path("/ZwaveRGBControl")
public class AlexaRGBDeviceControl {
	
	@GET
	@Produces("application/xml")
	public Response alexaZwaveVoicecontrol(@HeaderParam("deviceid")String deviceId,@HeaderParam("customerid") String cId,@HeaderParam("gateway") String gatewayId,@QueryParam("command")String command,@QueryParam("hue")String hue,@QueryParam("saturation")String saturation,@QueryParam("brightness")String brightness,@QueryParam("deviceType")String deviceType)throws Exception{
		
		LogUtil.info("device Id: " + deviceId+" customer"+cId+" command"+command+ " gateway "+ gatewayId + " deviceType: " + deviceType);
		LogUtil.info("hue: " + hue+" saturation"+saturation+" brightness"+brightness);
		String result = null;
		
		
			
			result=ClientController.sendToController("alexaService","voiceControlForRGB",deviceId,cId,gatewayId,command,hue,saturation,brightness);
			LogUtil.info("result"+result);
			
		
		
		
		return Response.status(200)
				.entity(result)
				.build();
		
	}

}
