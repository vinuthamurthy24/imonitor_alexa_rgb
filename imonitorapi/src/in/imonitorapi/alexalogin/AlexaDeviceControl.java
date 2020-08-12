package in.imonitorapi.alexalogin;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import in.imonitorapi.util.Constants;


@Path("/ZwaveControl")
public class AlexaDeviceControl {
	
	
	
	@GET
	@Produces("application/xml")
	public Response alexaZwaveVoicecontrol(@HeaderParam("deviceid")String deviceId,@HeaderParam("customerid") String cId,@HeaderParam("gateway") String gatewayId,@QueryParam("command")String command,@QueryParam("value")String value,@QueryParam("deviceType")String deviceType)throws Exception{
		LogUtil.info("RGB !!!!!!!!!");
		LogUtil.info("device Id: " + deviceId+" customer"+cId+" command"+command+" value"+value + " gateway "+ gatewayId + " deviceType: " + deviceType);
		String result = null;
		if((deviceType.equals(Constants.ZwaveAc)) || (deviceType.equals(Constants.Z_WAVE_AC_EXTENDER))){
			
			 result=ClientController.sendToController("alexaService","voiceControlForAC",deviceId,cId,gatewayId,command,value);
		}else if(deviceType.equals(Constants.Z_WAVE_DIMMER)){
			
			result=ClientController.sendToController("alexaService","voiceControlForDimmer",deviceId,cId,gatewayId,command,value);
		}else if(deviceType.equals(Constants.DEV_ZWAVE_RGB)){
			
			result=ClientController.sendToController("alexaService","voiceControlForRGB",deviceId,cId,gatewayId,command,value);
		}
		
		
		return Response.status(200)
				.entity(result)
				.build();
		
	}

}
