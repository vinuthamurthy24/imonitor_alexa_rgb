/********Code is for Google Home device API control **************************/
package in.imonitorapi.googlelinking;

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


@Path("/control")
public class DeviceControl {

	@POST
	@Produces("application/xml")
	public Response getdevicecontrol(@HeaderParam("deviceid")String generated,@HeaderParam("customerid") String token,@QueryParam("command")String command,@QueryParam("value")String value)throws Exception{
		XStream stream = new XStream();
		LogUtil.info(generated+" Devicename "+"customer"+token+"command"+command+"value"+value);
			String out="<imonitor>";
			String customer=ClientController.sendToController("voicecontrolservice","getgooglecustomerbytoken",token);
			LogUtil.info("customer name:"+customer);
			String result=ClientController.sendToController("deviceService","googlecontrolApi",generated,customer,command,value);
			LogUtil.info(result);
			String output =  result;
			if((output!=null)&&(output!="")){
				out+="<GenerateddeviceId>"+generated+"</GenerateddeviceId>";
				if(command.equalsIgnoreCase("ZwaveSw")||command.equalsIgnoreCase("ZwaveSir")){
				if(value.equalsIgnoreCase("1")){
					out+="<State>"+"on"+"</State>";
				}else{out+="<State>"+"off"+"</State>";}
				out+="<Status>"+"Success"+"</Status>"+"</imonitor>";
				}else if(command.equalsIgnoreCase("ZWDevDimUp")||command.equalsIgnoreCase("ZWDevDimDown")||command.equalsIgnoreCase("ZWDevCurtainOpen")||command.equalsIgnoreCase("ZWDevCurtainClose")){
					out+="<Status>"+"Success"+"</Status>"+"</imonitor>";
				}else{
					out+="<Status>"+"Success"+"</Status>"+"</imonitor>";
				}
				return Response.status(200)
						.entity(out)
						.build();
			}else{
				out="<status>Failure</status>";
				return Response.status(200)
						.entity(out)
						.build();
				
				}
		
	}

}
