package in.imonitorapi.scopes;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.mobile.login.OAuth2Util;
import in.imonitorapi.service.manager.TokenService;
import in.imonitorapi.util.LogUtil;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import Oauth2.imonitor.detail.OAuth2ErrorConstant;
import Oauth2.imonitor.detail.OAuth2Exception;
import Oauth2.imonitor.detail.OAuth2Scope;


@Path("/GetAlarms")
public class GetAlarms {
	
String data;
	
	
	@POST
	@Path("/scope/{GetAlarms}/{Customerid}/{gatewayid}")
    @Produces("application/xml")
	public Response synchronizescenariodetails(@HeaderParam("Client_id") String Client_id,@HeaderParam("Authorization") String authHeader, @PathParam("GetAlarms") String GetAlarms,@PathParam("Customerid") String Customerid, @PathParam("gatewayid") String gatewayid,@HeaderParam("AccessToken") String AccessToken) throws Exception{
		String data="success";
		if(GetAlarms.equals(OAuth2Scope.SCOPE_ALARMS)){	
			String Verify=TokenService.VerifyToken(AccessToken,Client_id);
			if(Verify=="ValidToken"){
		String userdetail=OAuth2Util.parseBasicAuthHeader(authHeader);
		String params="<?xml version='1.0' encoding='UTF-8'?><imonitor><customer>"+Customerid+"</customer>"+userdetail+"<macid>00:0e:8f:75:92:a0</macid><timeStamp>null</timeStamp></imonitor>";
		String serviceName = "midService";
		String method = "GetAlarm";
		LogUtil.info("thisdata"+ this.data);
		data = ClientController.sendToController(serviceName, method, params);
			}else if(Verify=="TokenExpired"){
				throw new OAuth2Exception(401, OAuth2ErrorConstant.EXPIRED_TOKEN);
			}
			
			else{
				throw new OAuth2Exception(401, OAuth2ErrorConstant.INVALID_TOKEN);
			}
		}else{
			throw new OAuth2Exception(401, OAuth2ErrorConstant.INVALID_SCOPE);
		}
		return Response.status(200)
				.entity(" Login status : " + data)
				.build();
	}
	
	

}
