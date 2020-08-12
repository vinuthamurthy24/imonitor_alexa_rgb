package in.imonitorapi.action;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.mobile.login.OAuth2Util;
import in.imonitorapi.service.manager.TokenService;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import Oauth2.imonitor.detail.OAuth2ErrorConstant;
import Oauth2.imonitor.detail.OAuth2Exception;
import Oauth2.imonitor.detail.OAuth2Scope;




@Path("/device")
public class DeviceControlApi {
		
	
	private String data;


	@POST
	@Path("/{DeviceID}/{CommandName}/{Value}/{CustomerId}/{ScopeValue}")
    @Produces("application/xml")
public Response AcControlFanmodes(@HeaderParam("Client_id") String Client_id, 
		                          @PathParam("DeviceID") String DeviceID, 
		                          @PathParam("CustomerId") String Customerid, 
		                          @PathParam("CommandName") String CommandName,
		                          @HeaderParam("Authorization") String authHeader,
		                          @PathParam("ScopeValue") String ScopeValue,
		                          @PathParam("Value") String Value,
		                          @HeaderParam("AccessToken") String AccessToken) throws Exception{
		if(ScopeValue.equals(OAuth2Scope.SCOPE_AC_FAN_MODE)){	
			String Verify=TokenService.VerifyToken(AccessToken,Client_id);
			
			if(Verify=="ValidToken"){
				if(CommandName=="AC_MODE_CONTROL"){
				
				String userdetail=OAuth2Util.parseBasicAuthHeader(authHeader);
				String params="<?xml version='1.0' encoding='UTF-8'?><imonitor><customer>"+Customerid+"</customer>"+userdetail+"<deviceid>"+DeviceID+"</deviceid><commandname>"+CommandName+"</commandname><fanModeValue>"+Value+"</fanModeValue></imonitor>";
				String serviceName = "midService";
				String method = "controlFanModes";
				
				 data = ClientController.sendToController(serviceName, method, params);
				
				}else{
					String userdetail=OAuth2Util.parseBasicAuthHeader(authHeader);
					String params="<?xml version='1.0' encoding='UTF-8'?><imonitor><customer>"+Customerid+"</customer>"+userdetail+"<deviceid>"+DeviceID+"</deviceid><commandname>"+CommandName+"</commandname><levelstatus>"+Value+"</levelstatus></imonitor>";
					String serviceName = "midService";
					String method = "controlFanModes";
					
					data = ClientController.sendToController(serviceName, method, params);
					
				}
				}else if(Verify=="TokenExpired"){
						throw new OAuth2Exception(401, OAuth2ErrorConstant.EXPIRED_TOKEN);
					}
		
		}
		
	
		return Response.status(200)
				.entity(" Control status : " + data)
				.build();
	}
	
	
	@POST
	@Path("control/{DeviceID}/{CommandName}/{CustomerId}/{ScopeValue}")
    @Produces("application/xml")
public Response ControlDevice(@HeaderParam("Client_id") String Client_id, 
		                          @PathParam("DeviceID") String DeviceID, 
		                          @PathParam("CustomerId") String Customerid, 
		                          @PathParam("CommandName") String CommandName,
		                          @HeaderParam("Authorization") String authHeader,
		                          @PathParam("ScopeValue") String ScopeValue,
		                          @PathParam("Value") String Value,
		                          @HeaderParam("AccessToken") String AccessToken, @HeaderParam("LevelStatus") String levelstatus) throws Exception{
		if(ScopeValue.equals(OAuth2Scope.SCOPE_CONTROL_DEVICE )){	
			String Verify=TokenService.VerifyToken(AccessToken,Client_id);
			
			if(Verify=="ValidToken"){
				
				if(CommandName=="DIMMER_CHANGE"){
				String userdetail=OAuth2Util.parseBasicAuthHeader(authHeader);
				String params="<?xml version='1.0' encoding='UTF-8'?><imonitor><customer>"+Customerid+"</customer>"+userdetail+"<deviceid>"+DeviceID+"</deviceid><commandname>"+CommandName+"</commandname><levelstatus>"+levelstatus+"</levelstatus></imonitor>";
				String serviceName = "midService";
				String method = "controldevice";
				
				data = ClientController.sendToController(serviceName, method, params);
				}else{
					String userdetail=OAuth2Util.parseBasicAuthHeader(authHeader);
					String params="<?xml version='1.0' encoding='UTF-8'?><imonitor><customer>"+Customerid+"</customer>"+userdetail+"<deviceid>"+DeviceID+"</deviceid><commandname>"+CommandName+"</commandname><levelstatus>"+levelstatus+"</levelstatus></imonitor>";
					String serviceName = "midService";
					String method = "controldevice";
					data = ClientController.sendToController(serviceName, method, params);
					
				}
					}else if(Verify=="TokenExpired"){
						throw new OAuth2Exception(401, OAuth2ErrorConstant.EXPIRED_TOKEN);
					}
		
		}
		
		return Response.status(200)
				.entity(" Control status : " + data)
				.build();
	}
}