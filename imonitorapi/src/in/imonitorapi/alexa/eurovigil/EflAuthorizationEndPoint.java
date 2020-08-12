package in.imonitorapi.alexa.eurovigil;

import java.net.URI;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.json.JSONWithPadding;
import com.sun.jersey.api.view.Viewable;

import in.imonitorapi.alexa.efl.communicator.EflClientController;
import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

@Path("/auth")
public class EflAuthorizationEndPoint {

	@GET
	@Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public Response loginPage() throws  Exception {
		LogUtil.info("viewing login page");
		return Response.ok(new Viewable("/indexEfl")).build();
		}
	
	@GET
	@Path("/sample")
	@Produces("application/x-www-form-urlencoded")
    public Response SmaplePage(@QueryParam("code")String code,@QueryParam("state")String state,@QueryParam("redirecturi")String redirect_uri) throws  Exception {
				
		LogUtil.info("redirect_Uri: "+ redirect_uri);
		LogUtil.info("code: "+ code);
		LogUtil.info("state: "+ state);
				
		java.net.URI location = new java.net.URI(redirect_uri+"?code="+URLEncoder.encode(code, "UTF-8")+"&state="+state);
		LogUtil.info("url to redirect=  "+ redirect_uri+"?code="+URLEncoder.encode(code, "UTF-8")+"&state="+state);
        return Response.seeOther(location).status(302).header("Access-Control-Allow-Origin", "*")
        		.header("Connection", "Keep-Alive")
         		.header("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE")
         		.header("Access-Control-Allow-Headers", "Origin, Content-Type")
         		.build();
		}	
	
	@GET
	@Path("/authcode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/javascript")
    public JSONWithPadding authorize(@Context HttpServletRequest request)throws Exception {
        String customer=request.getParameter("custid");
        String user=request.getParameter("userid");
        String pass=request.getParameter("password");
     
        String clientid=request.getParameter("client_id");
        LogUtil.info("coming2"+customer+" "+user+" "+pass);
        String result=EflClientController.sendToController("customerService","logincheckforAlexa",customer,user,pass);
        OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        String refreshToken = oauthIssuerImpl.refreshToken();
      //  String result="True";
		if(result.equals("True")){
        try {
        	OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            //LogUtil.info("httpserveletrequest"+new XStream().toXML(oauthRequest));
         
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            LogUtil.info("response type:"+responseType);
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse
                .authorizationResponse(request,HttpServletResponse.SC_FOUND);
            String token="";
            String code="";
            if (responseType.equals(ResponseType.CODE.toString())) {
            	String keySource = customer+clientid;
          		byte [] tokenByte = Base64.encodeBase64(keySource.getBytes());
          		code = new String(tokenByte);
        		token =oauthIssuerImpl.accessToken();
        		LogUtil.info("code + token"+code +" "+ token);
                builder.setParam("code", code);
            }
          
            String hashpass=ClientController.getHashPassword(pass);
            EflClientController.sendToController("voicecontrolservice","savealexauser",customer,user,hashpass,token,code,refreshToken);
            String redirectURI =oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
           
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            URI url = new URI(response.getLocationUri());
            String uri=url.toString();
            uri=uri.replaceAll("=","\":\"");
            uri=uri.replaceAll("&", "\",\"");
            uri="{\"" + uri + "\"}";
           
            JSONObject object=new JSONObject(uri);
           
            JSONWithPadding  padding=new JSONWithPadding(object);
            return padding;
             
           // return Response.status(200).build();
        } catch (OAuthProblemException e) {

            final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND);

            String redirectUri = e.getRedirectUri();

            if (OAuthUtils.isEmpty(redirectUri)) {
                throw new WebApplicationException(
                    responseBuilder.entity("OAuth callback url needs to be provided by client!!!").build());
            }
            final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                .error(e)
                .location(redirectUri).buildQueryMessage();
            final URI location = new URI(response.getLocationUri());
            String uri=location.toString();
            uri=uri.replaceAll("=","\":\"");
            uri=uri.replaceAll("&", "\",\"");
            uri="{\"" + uri + "\"}";
            JSONObject object=new JSONObject(uri);
            JSONWithPadding  padding=new JSONWithPadding(object);
            return padding;
        }
		}else{
			LogUtil.info("error");
			JSONObject object=new JSONObject("{error : message}");
			 JSONWithPadding  padding=new JSONWithPadding(object);
			 return padding;
		}
    }
	
}
