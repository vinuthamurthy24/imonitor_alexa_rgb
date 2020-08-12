package in.imonitorapi.scopes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.mobile.login.OAuth2Util;
import in.imonitorapi.service.manager.TokenService;
import in.imonitorapi.util.LogUtil;
import Oauth2.imonitor.detail.OAuth2ErrorConstant;
import Oauth2.imonitor.detail.OAuth2Exception;
import Oauth2.imonitor.detail.OAuth2Scope;

import com.opensymphony.xwork2.ActionSupport;
import Oauth2.imonitor.detail.OAuth2Scope;


@Path("/GetDevice")
public class getDeviceList {
	
	private String data;
	
	    @GET
	    @Path("/scope/{ScopeValue}/{customerid}")
	    @Produces("application/xml")
	public Response synchronizeGateWayDetails(@HeaderParam("Authorization") String authHeader,@HeaderParam("Client_id") String Client_id, @HeaderParam("AccessToken") String AccessToken, @PathParam("ScopeValue") String Scope,@PathParam("customerid") String customerid) throws Exception {
	    	
		String Result="success";
		if(Scope.equals(OAuth2Scope.SCOPE_GET_DEVICES)){	
			String Verify=TokenService.VerifyToken(AccessToken,Client_id);
			if(Verify=="ValidToken"){
		String userdetail=OAuth2Util.parseBasicAuthHeader(authHeader);
		String params="<?xml version='1.0' encoding='UTF-8'?><imonitor><customer>"+customerid+"</customer>"+userdetail+"</imonitor>";
		String serviceName = "midService";
		String method = "synchronizeGateWayDetails";
		LogUtil.info(params);
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
