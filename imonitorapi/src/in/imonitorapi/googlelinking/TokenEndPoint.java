package in.imonitorapi.googlelinking;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.sun.jersey.core.util.Base64;

@Path("/token")
public class TokenEndPoint {
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
	public Response authorize(@FormParam("client_id")String clientid,@FormParam("client_secret")String secret,@FormParam("grant_type")String granttype,@FormParam("code")String code) throws Exception {
			LogUtil.info("client details "+clientid+" "+secret+" "+granttype+" "+code);
			String customer=ClientController.sendToController("voicecontrolservice","getgooglecustomerbycode",code);
		try {
			
			OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
				// fake check of client id and client secret (...)
		        // create and add new token to database for a given user
			LogUtil.info("customer "+customer);
			if(granttype.equals(GrantType.REFRESH_TOKEN.toString())){
				final String accessToken = oauthIssuerImpl.accessToken();
		        OAuthResponse response = OAuthASResponse
		                .tokenResponse(HttpServletResponse.SC_OK)
		                .setAccessToken(accessToken)
		                .setTokenType("bearer")
		                .buildJSONMessage();
		       ClientController.sendToController("voicecontrolservice","updategoogleuser",code,accessToken);
		        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
			}else{LogUtil.info("comming");
				final String accessToken = oauthIssuerImpl.accessToken();
		        OAuthResponse response = OAuthASResponse
		                .tokenResponse(HttpServletResponse.SC_OK)
		                .setAccessToken(accessToken)
		                .setRefreshToken(oauthIssuerImpl.refreshToken())
		                .setTokenType("bearer")
		                .setExpiresIn("3600")
		                .buildJSONMessage();
		        ClientController.sendToController("voicecontrolservice","updategoogleuser",code,accessToken);
		        LogUtil.info("redirect");
		        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
			}
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
