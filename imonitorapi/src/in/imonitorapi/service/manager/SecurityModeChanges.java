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

@Path("/securitymode")
public class SecurityModeChanges {
	
	@POST
	@Produces("application/xml")
	public Response getmodechange(@QueryParam("Mode")String mode,@HeaderParam("Client_Id")String Client_id,@HeaderParam("customerId") String id,@HeaderParam("UserId") String UserId,@HeaderParam("password") String password)throws Exception{
		String statusName=null;
		String out="<imonitor>";
		if((!Client_id.equalsIgnoreCase(Constants.TPDDL))){
			out+="failure</imonitor>";
		}else{
			if(mode.equalsIgnoreCase("HOME")){
				statusName="DISARM";
			}else if(mode.equalsIgnoreCase("AWAY")){
				statusName="ARM";
			}else if(mode.equalsIgnoreCase("STAY")){
				statusName="STAY";
			}else{
				statusName=mode;
			}
			String result=ClientController.sendToController("commandIssueService","loginpasscheckapi",UserId,password,id);
			LogUtil.info(result+"final output");
			if(result.equals("True")){
				result=ClientController.sendToController("commandIssueService","activateModeInternal",id,statusName);
				if(result.equalsIgnoreCase("msg.controller.activateModeInternal.0001")){
					out+="<status>Failure</status>"+"<Reason>Unable to process your request due to internal error</Reason>"+"</imonitor>";
				}else if(result.equalsIgnoreCase("msg.controller.activateModeInternal.0002")){
					out+="<status>Failure</status>"+"<Reason>Your Gateway did not respond back. Mode not changed.</Reason>"+"</imonitor>";
				}else if(result.equalsIgnoreCase("msg.controller.activateModeInternal.0003")){
					out+="<status>Failure</status>"+"</imonitor>";
				}else if(result.equalsIgnoreCase("msg.controller.activateModeInternal.0004")){
					out+="<status>Success: The Mode will be activated as configured.</status>"+"<SecurityMode>"+mode+"</SecurityMode>"+"</imonitor>";
				}
				
			}else{
				out+="failure</imonitor>";
			}
		}
		
		return Response.status(200)
				.entity(out)
				.build();
	}

}
