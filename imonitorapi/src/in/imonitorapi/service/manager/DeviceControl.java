package in.imonitorapi.service.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.Constants;
import in.imonitorapi.util.LogUtil;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;


@Path("/devicecontrol")
public class DeviceControl {

	@POST
	@Produces("application/xml")
	public Response getdevicecontrol(@QueryParam("deviceId")String generated,@QueryParam("Command")String command,@QueryParam("value")String value,@HeaderParam("Client_Id")String Client_id,@HeaderParam("customerId") String id,@HeaderParam("UserId") String UserId,@HeaderParam("password") String password)throws Exception{
		XStream stream = new XStream();
		//LogUtil.info(generated+" deviceID "+command+" value "+id+" customer "+UserId+"  user  "+password+"  client"+Client_id +value+" ac command");
		String out="<imonitor>";
		if((!Client_id.equalsIgnoreCase(Constants.TPDDL))){
			out+="failure</imonitor>";
		}else{
			String result=ClientController.sendToController("deviceService","devicecontrolApi",generated,command,UserId,password,id,value);
			//LogUtil.info(result);
			String output =  result;
			if(output!=null){
				out+="<GenerateddeviceId>"+generated+"</GenerateddeviceId>";
				
				if(command.equalsIgnoreCase("ZwaveAc")||command.equalsIgnoreCase("ZwaveAcFanMode")||command.equalsIgnoreCase("ZwaveAcSwing")){
					if(command.equalsIgnoreCase("ZwaveAc")){
					if(value.equalsIgnoreCase("1")){out+="<State>"+"Heat"+"</State>";}
					else if(value.equalsIgnoreCase("2")){out+="<State>"+"Cool"+"</State>";}
					else if(value.equalsIgnoreCase("5")){out+="<State>"+"on"+"</State>";}
					else if(value.equalsIgnoreCase("6")){out+="<State>"+"Fan"+"</State>";}
					else if(value.equalsIgnoreCase("8")){out+="<State>"+"Dry"+"</State>";}
					else if(value.equalsIgnoreCase("10")){out+="<State>"+"Auto"+"</State>";}
					else if(value.equalsIgnoreCase("0")){out+="<State>"+"off"+"</State>";}
					else if(Integer.parseInt(value)>=16 && Integer.parseInt(value)<=28){
						out+="<State>"+"AcTemperatureChange"+"</State>";
					}}else if(command.equalsIgnoreCase("ZwaveAcFanMode")){
						if(value.equals("1")){
							out+="<State>"+"FanModeLow"+"</State>";}
						else if(value.equals("2")){out+="<State>"+"FanModeMedium"+"</State>";}
						else if(value.equals("3")){out+="<State>"+"FanModeHigh"+"</State>";}}
					else if(command.equalsIgnoreCase("ZwaveAcSwing")){
						if(value.equals("1")){
							out+="<State>"+"AcSwingOn"+"</State>";}
						else if(value.equals("0")){out+="<State>"+"AcSwingOFF"+"</State>";}
					}
					out+="<Status>"+"Success"+"</Status>"+"</imonitor>";	
				}else if(command.equalsIgnoreCase("ZwaveSw")||command.equalsIgnoreCase("ZwaveSir")){
				if(value.equalsIgnoreCase("1")){
					out+="<State>"+"on"+"</State>";
				}else{out+="<State>"+"off"+"</State>";}
				out+="<Status>"+"Success"+"</Status>"+"</imonitor>";
				}else if(command.equalsIgnoreCase("ZWDevDimUp")||command.equalsIgnoreCase("ZWDevDimDown")||command.equalsIgnoreCase("ZWDevCurtainOpen")||command.equalsIgnoreCase("ZWDevCurtainClose")){
					out+="<Status>"+"Success"+"</Status>"+"</imonitor>";
				}
			}else{
				out+="<GenerateddeviceId>"+generated+"</GenerateddeviceId>"+"<Status>"+"Failure"+"</Status>"+"</imonitor>";
				}
			
		}
		
		return Response.status(200)
				.entity(out)
				.build();
		
	}

}
