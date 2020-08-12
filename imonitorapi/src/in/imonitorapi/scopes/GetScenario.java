package in.imonitorapi.scopes;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.mobile.login.OAuth2Util;
import in.imonitorapi.service.manager.TokenService;
import in.imonitorapi.util.LogUtil;
import in.monitor.authprovider.Clients;
import Oauth2.imonitor.detail.OAuth2Exception;
import Oauth2.imonitor.detail.OAuth2ErrorConstant;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import Oauth2.imonitor.detail.OAuth2Constant;
import Oauth2.imonitor.detail.OAuth2Scope;



@Path("/GetScenario")
public class GetScenario {
	String data;
	
	
	@GET
	@Path("/scope/{GetScenario}/{Customerid}")
    @Produces("application/xml")
	public Response synchronizescenariodetails(@HeaderParam("Client_id") String Client_id,@HeaderParam("AccessToken") String AccessToken,@HeaderParam("Authorization") String authHeader, @PathParam("GetScenario") String GetScenario,@PathParam("Customerid") String Customerid) throws Exception{
		String data="success";
		LogUtil.info(Client_id+AccessToken);
		if(GetScenario.equals(OAuth2Scope.SCOPE_GET_SCENARIO)){	
		String Verify=TokenService.VerifyToken(AccessToken,Client_id);
		
		if(Verify=="ValidToken"){
			
		String userdetail=OAuth2Util.parseBasicAuthHeader(authHeader);
		String params="<?xml version='1.0' encoding='UTF-8'?><imonitor><customer>"+Customerid+"</customer>"+userdetail+"</imonitor>";
		String serviceName = "midService";
		String method = "synchronizeScenarioDetails";
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
				.entity(" status : " + data)
				.header(Client_id, null)
				.build();
	}

	
		
	

}
