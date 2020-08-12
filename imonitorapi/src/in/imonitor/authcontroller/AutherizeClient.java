package in.imonitor.authcontroller;

import in.imonitorapi.authcontroller.dao.manager.ClentManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.soap.MimeHeaders;

import org.apache.commons.codec.digest.DigestUtils;

import Oauth2.imonitor.detail.OAuth2Constant;
import Oauth2.imonitor.detail.OAuth2ErrorConstant;
import in.imonitorapi.mobile.login.ClientController;
/*import Oauth2.imonitor.detail.OAuth2ErrorConstant;*/

import in.imonitorapi.mobile.login.OAuth2Exception;
import in.imonitorapi.mobile.login.OAuth2Util;
import in.imonitorapi.util.LogUtil;
import in.monitor.authprovider.Clients;

import in.monitor.authprovider.Clients;
/*import in.monitor.authprovider.Oauth2Response;*/
import  com.sun.jersey.core.spi.factory.ResponseImpl;

@Path("/autherize")
public class AutherizeClient {
	
	protected MimeHeaders headers = new MimeHeaders();

	

	@GET
	public Response addUser(@HeaderParam("Authorization") String Authorization,
			                @HeaderParam("authcode") String authcode, 
			                @HeaderParam("Grant_Type") String grantType,
			                @HeaderParam("Response_Type") String Response_Type) throws Exception{
		
		/*if (grantType.equals(OAuth2Constant.GRANT_TYPE_CLIENT_CREDENTIALS)) {*/
		  String stat="";
		  String result = "success";
		  if (grantType.equals(OAuth2Constant.GRANT_TYPE_PASSWORD) && Response_Type.equals(OAuth2Constant.RESPONSE_TYPE_CODE)) {
			
			  ClentManager clientManager = new ClentManager();
			  String clientDetail=OAuth2Util.parseBasicAutherization(Authorization);
			  String[] ClientDetails1= clientDetail.split(":");
			  String clientId=ClientDetails1[0];
			  String ClientSecret=ClientDetails1[1];
			
			  result = clientManager.isClientExists(clientId, ClientSecret);
			
			 
			   if (result=="success"){
									  
						  authcode= generateAuthcode(clientId,ClientSecret );
						  String client = clientManager.updateClientsByQuerry(clientId, authcode);
						
						 /* res .ok().header("AutherizationCode", authcode).entity("Status");*/
						  stat= "Client register is success";							  
				  }else{
					  
					  stat = OAuth2ErrorConstant.UNAUTHORIZED_CLIENT;
				  }
			
			 
			  
			}else {
				/*throw new OAuth2Exception(500, OAuth2ErrorConstant.UNSUPPORTED_RESPONSE_TYPE);*/
			stat=OAuth2ErrorConstant.UNSUPPORTED_RESPONSE_TYPE+" Or "+OAuth2ErrorConstant.UNSUPPORTED_GRANT_TYPE;
			
			
				
				
			}
		
				return Response.status(200)
						.entity(stat)
					    .header("AutherizationCode", authcode)
					    .build();

			}
	
	

	public String generateAuthcode(String clientId, String ClientSecret) {
						
		    /*String abc=DigestUtils.md5Hex(ClientSecret);*/
			 String code=clientId + System.nanoTime();
			 String Authcode = DigestUtils.md5Hex(code);
			 LogUtil.info("autherization code:" +Authcode );			
			return Authcode;
		}
	  
	  
	  



}
