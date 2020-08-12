package in.imonitorapi.googlelinking;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.cglib.proxy.Callback;

import org.apache.commons.codec.binary.Base64;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.sun.jersey.api.json.JSONWithPadding;
import com.sun.jersey.api.view.Viewable;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.Base64Encoder;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Locale;


@Stateless
@Path("/auth")
public class AuthEndpoint{	
	/**
	 * 
	 */
	@GET
	@Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public Response loginPage() throws  Exception {
		LogUtil.info("viewing login page");
		return Response.ok(new Viewable("/index")).build();
		}		
	
	
	@GET
	@Path("/authcode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/javascript")
    public JSONWithPadding authorize(@Context HttpServletRequest request)
        throws Exception {
        OAuthAuthzRequest oauthRequest = null;
        String customer=request.getParameter("custid");
        String user=request.getParameter("userid");
        String pass=request.getParameter("password");
        String state=request.getParameter("state");
        String clientid=request.getParameter("client_id");
        LogUtil.info("coming2"+customer+" "+user+" "+pass+" "+state);
        String result=ClientController.sendToController("customerService","logincheckforAlexa",customer,user,pass);
        //OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        LogUtil.info(result);
		if(result.equals("True")){
       try {
        	
        	
            oauthRequest = new OAuthAuthzRequest(request);
            //build response according to response_type
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse
                .authorizationResponse(request,HttpServletResponse.SC_FOUND);
            String code="";
            if (responseType.equals(ResponseType.CODE.toString())) {
            	/*SecureRandom random=new SecureRandom();
            	byte bytes[]=new byte[16];
            	random.nextBytes(bytes);
            	code=Base64.encodeBase64String(bytes);
            	byte[] decoded=Base64.decodeBase64(code);
            	codestring=decoded.toString();*/
            	SecureRandom random=new SecureRandom();
            	String alpha="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            	StringBuilder sb = new StringBuilder( 6 );
            	for( int i = 0; i < 6; i++ ) 
            	sb.append( alpha.charAt( random.nextInt(alpha.length()) ) );
            	String randm=sb.toString();
            	String keySource = customer+clientid+randm;
        		byte [] tokenByte = Base64.encodeBase64(keySource.getBytes());
        		code = new String(tokenByte);
            	LogUtil.info("token value: "+code );
                builder.setCode(code);
            }
            String hashpass=ClientController.getHashPassword(pass);
           
            ClientController.sendToController("voicecontrolservice","savegoogleuser",customer,user,hashpass,code,clientid);
            String redirectURI =oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            LogUtil.info("redirection url:"+redirectURI);
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            URI url = new URI(response.getLocationUri());
           /*return Response.status(200).entity(url.toString()).build();*/
           /*return Response.status(response.getResponseStatus()).location(url).build();*/
            String uri=url.toString();
            uri=uri.replaceAll("=","\":\"");
            uri=uri.replaceAll("&", "\",\"");
            uri="{\"" + uri + "\"}";
            LogUtil.info(uri);
           /* Gson gson=new Gson();
            String urlout=gson.toJson(uri.trim());
            LogUtil.info(urlout);*/
            JSONObject object=new JSONObject(uri);
            LogUtil.info("object="+object);
            JSONWithPadding  padding=new JSONWithPadding(object);
            return padding;
            
            //return Response.status(200).build();
        } catch (OAuthProblemException e) {

            final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND);

            String redirectUri = e.getRedirectUri();
            
            LogUtil.info("redirectUri==="+redirectUri);

            if (OAuthUtils.isEmpty(redirectUri)) {
                throw new WebApplicationException(
                    responseBuilder.entity("OAuth callback url needs to be provided by client!!!").build());
            }
            final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                    .error(e)
                    .location(redirectUri).buildQueryMessage();
            final URI location = new URI(response.getLocationUri());
            /*return Response.status(200).entity(location.toString()).build();*/
            /*return responseBuilder.location(location).build();*/
            String uri=location.toString();
            uri=uri.replaceAll("=","\":\"");
            uri=uri.replaceAll("&", "\",\"");
            uri="{\"" + uri + "\"}";
            /*Gson gson=new Gson();
            String urlout=gson.toJson(uri.trim());
            LogUtil.info(urlout);*/
            JSONObject object=new JSONObject(uri);
            JSONWithPadding  padding=new JSONWithPadding(object);
            return padding;
           
        }
		}else{
			LogUtil.info("error");
			/*return Response.status(200).entity("error messsage").build();*/
			JSONObject object=new JSONObject("{error : message}");
			 JSONWithPadding  padding=new JSONWithPadding(object);
			 return padding;
		}
		
    }
}
