package in.imonitorapi.scopes;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.mobile.login.OAuth2Util;
import in.imonitorapi.service.manager.TokenService;
import in.imonitorapi.util.LogUtil;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import Oauth2.imonitor.detail.OAuth2ErrorConstant;
import Oauth2.imonitor.detail.OAuth2Exception;
import Oauth2.imonitor.detail.OAuth2Scope;

@Path("/armstatus")
public class ArmDisarmStatus {
	String data;
	@POST
	@Path("/scope/{armStatus}/{Customerid}")
    @Produces("application/xml")
	public Response synchronizescenariodetails(@HeaderParam("Client_id") String Client_id,@HeaderParam("Authorization") String authHeader,
			                                   @PathParam("armStatus") String armStatus,@PathParam("Customerid") String Customerid,@HeaderParam("AccessToken") String AccessToken) throws Exception{
		
		String data="success";
		
		if(armStatus.equals(OAuth2Scope.SCOPE_ARM_AWAY)){
			String Verify=TokenService.VerifyToken(AccessToken,Client_id);
			if(Verify=="ValidToken"){
		
		String userdetail=OAuth2Util.parseBasicAuthHeader(authHeader);
		String params="<?xml version='1.0' encoding='UTF-8'?><imonitor><customer>"+Customerid+"</customer>"+userdetail+"<deviceid>*</deviceid></imonitor>";
				
		String serviceName = "midService";
		String method = "armDevice";
		
		data = ClientController.sendToController(serviceName, method, params);
			}else if(Verify=="TokenExpired"){
				throw new OAuth2Exception(401, OAuth2ErrorConstant.EXPIRED_TOKEN);
			}
			
			else{
				throw new OAuth2Exception(401, OAuth2ErrorConstant.INVALID_TOKEN);
			}
			
			
		}else if(armStatus.equals(OAuth2Scope.SCOPE_ARM_STAY)) {
			String Verify=TokenService.VerifyToken(AccessToken,Client_id);
			if(Verify=="ValidToken"){
				
			String userdetail=OAuth2Util.parseBasicAuthHeader(authHeader);
			String params="<?xml version='1.0' encoding='UTF-8'?><imonitor><customer>"+Customerid+"</customer>"+userdetail+"<deviceid>*STAY</deviceid></imonitor>";
					
			String serviceName = "midService";
			String method = "armDevice";
			
			data = ClientController.sendToController(serviceName, method, params);
			}else if(Verify=="TokenExpired"){
				throw new OAuth2Exception(401, OAuth2ErrorConstant.EXPIRED_TOKEN);
			}
			
			else{
				throw new OAuth2Exception(401, OAuth2ErrorConstant.INVALID_TOKEN);
			}
			
			
		}else if(armStatus.equals(OAuth2Scope.SCOPE_DISARM_HOME)) {
			String Verify=TokenService.VerifyToken(AccessToken,Client_id);
			if(Verify=="ValidToken"){
			String userdetail=OAuth2Util.parseBasicAuthHeader(authHeader);
			String params="<?xml version='1.0' encoding='UTF-8'?><imonitor><customer>"+Customerid+"</customer>"+userdetail+"<deviceid>*</deviceid></imonitor>";
					
			String serviceName = "midService";
			String method = "disarmDevice";
			
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
