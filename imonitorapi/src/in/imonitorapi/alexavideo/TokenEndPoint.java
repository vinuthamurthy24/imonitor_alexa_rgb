package in.imonitorapi.alexavideo;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;
import in.monitor.authprovider.AccessToken;

@Path("/token")
public class TokenEndPoint {
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response authorize(@FormParam("client_id")String clientid,@FormParam("client_secret")String secret,@FormParam("grant_type")String granttype,@FormParam("code")String code,@FormParam("refresh_token")String refresh_token) throws Exception {
			LogUtil.info("client details "+clientid+" "+granttype+" "+code );
			
			AccessToken token = new AccessToken();
			XStream stream = new XStream();
			if((granttype.equals("authorization_code"))||(granttype == "authorization_code")){
			String resultxml=ClientController.sendToController("voicecontrolservice","getalexausertoken",code);
			LogUtil.info("token "+resultxml);
			Object[] objects = (Object[]) stream.fromXML(resultxml);
			
			
			token.setAccess_token(objects[0].toString());
			token.setExpires_in(3600);
			token.setToken_type("bearer");
			token.setRefresh_token(objects[1].toString());
			}else if((granttype.equals("refresh_token")) || (granttype == "refresh_token")){
				
				OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
				LogUtil.info("refresh token "+refresh_token );
				String newRefreshToken = oauthIssuerImpl.refreshToken();
				String newAccessToken = oauthIssuerImpl.accessToken();
				
				String res = ClientController.sendToController("voicecontrolservice","updatealexausertokens",refresh_token,newRefreshToken,newAccessToken);
				LogUtil.info("result: "+ res);
				if(res.equals("Success")){
					token.setAccess_token(newAccessToken);
					token.setExpires_in(3600);
					token.setToken_type("bearer");
					token.setRefresh_token(newRefreshToken);
					
				}
			}
		try {
			OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
			
		LogUtil.info("token xml: "+ stream.toXML(token));
			Gson gson=new Gson();
			Object output=gson.toJson(token);
				// fake check of client id and client secret (...)
		        // create and add new token to database for a given user
		       /* OAuthResponse response = OAuthASResponse
		                .tokenResponse(HttpServletResponse.SC_OK)
		                .setAccessToken(accessToken)
		                .setRefreshToken(oauthIssuerImpl.refreshToken())
		                .setTokenType("bearer")
		                .setExpiresIn("300")
		                .buildJSONMessage();*/
		                
		      //  java.net.URI location = new java.net.URI(redirect);
				
		      
				return Response.status(200)
						    .header("Content-Type", "application/json;charset=UTF-8")
			        		.header("Cache-Control", "no-store")
			        		.header("Pragma", "no-cache")
							.entity(output)
			         		.build();
		      
		       
		} catch (Exception e) {
				// TODO Auto-generated catch block
				 final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND); 
				String redirectUri = ((OAuthProblemException) e).getRedirectUri();
				e.printStackTrace();
				final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
		                .error((OAuthProblemException) e)
		                .location(redirectUri).buildQueryMessage();
		            final URI location = new URI(response.getLocationUri());
		            return responseBuilder.location(location).build();
			}       
	}
}
