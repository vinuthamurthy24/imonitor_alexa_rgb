package in.imonitorapi.service.manager;

import in.imonitorapi.authcontroller.dao.manager.ClentManager;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import Oauth2.imonitor.detail.OAuth2Constant;
import Oauth2.imonitor.detail.OAuth2ErrorConstant;
import Oauth2.imonitor.detail.OAuth2Exception;


@Path("/Token")
public class TokenEndPoint {
	private String TokenDetails;
	
	@POST
	public Response GenerateToken(@HeaderParam("AccessToken") String AccessToken,@HeaderParam("ExpiresAT") String ExpiresAT,
			                            @HeaderParam("Client_id") String clientId,@HeaderParam("code") String Authcode,
			                            @HeaderParam("ResponseType") String ResponseType,@HeaderParam("Client_Secret") String ClientSecret,@HeaderParam("RefreshToken") String RefreshToken,@HeaderParam("GrantType") String GrantType ) throws Exception{
		String result = "success";     
		 ClentManager clientManager = new ClentManager();
		 result = clientManager.isClientExists(clientId, ClientSecret);
		if(GrantType.equals(OAuth2Constant.GRANT_TYPE_REFRESH_TOKEN)){
			if(ResponseType.equals(OAuth2Constant.RESPONSE_TYPE_TOKEN)){
			
			 result = clientManager.isClientExistswithRefreshtoken(clientId, ClientSecret, RefreshToken);
			  if (result=="success"){
				  TokenDetails=TokenService.generateAccessToken(clientId);
				  String[] temp = TokenDetails.split(" ");
   	              AccessToken=temp[0];
   	              RefreshToken=temp[1];
   	              String CreatedAt=temp[2];
   	              ExpiresAT=temp[3];
			  }
		}else{
			throw new OAuth2Exception(401, OAuth2ErrorConstant.UNSUPPORTED_RESPONSE_TYPE);
		}
		}else if(GrantType.equals(OAuth2Constant.GRANT_TYPE_AUTHORIZATION_CODE)){
			if(ResponseType.equals(OAuth2Constant.RESPONSE_TYPE_TOKEN)){
			 
			 String Verify=clientManager.VerifyAuthCode(clientId, Authcode);
			 if (Verify=="success"){
				  TokenDetails=TokenService.generateAccessToken(clientId);
				  String[] temp = TokenDetails.split(" ");
   	              AccessToken=temp[0];
   	              RefreshToken=temp[1];
   	              String CreatedAt=temp[2];
   	              ExpiresAT=temp[3];
			 }
			}
			
		}else{
			throw new OAuth2Exception(401, OAuth2ErrorConstant.INVALID_GRANT);
		}
		
		
		return Response.status(200)
				.entity(" TokenDetails :")
				 .header("AccessToken", AccessToken)
					  .header("RefreshToken", RefreshToken)
					   .header("ExpiresAT", ExpiresAT)
				.build();
}
}
