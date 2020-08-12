package in.imonitorapi.alexa.eurovigil;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import in.imonitorapi.alexa.efl.communicator.EflClientController;
import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.Constants;
import in.imonitorapi.util.LogUtil;

@Path("/ZwaveControl")
public class EflAlexaDeviceControl {

	@GET
	@Produces("application/xml")
	public Response alexaZwaveVoicecontrol(@HeaderParam("deviceid")String deviceId,@HeaderParam("customerid") String cId,@HeaderParam("gateway") String gatewayId,@QueryParam("command")String command,@QueryParam("value")String value,@QueryParam("deviceType")String deviceType)throws Exception{
		
		LogUtil.info("device Id: " + deviceId+" customer"+cId+" command"+command+" value"+value + " gateway "+ gatewayId + " deviceType: " + deviceType);
		String result = null;
		if((deviceType.equals(Constants.ZwaveAc)) || (deviceType.equals(Constants.Z_WAVE_AC_EXTENDER))){
			
			 result=EflClientController.sendToController("alexaService","voiceControlForAC",deviceId,cId,gatewayId,command,value);
		}else if(deviceType.equals(Constants.Z_WAVE_DIMMER)){
			
			result=EflClientController.sendToController("alexaService","voiceControlForDimmer",deviceId,cId,gatewayId,command,value);
		}
		
		
		return Response.status(200)
				.entity(result)
				.build();
		
	}

}
