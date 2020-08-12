package in.imonitorapi.googlelinking;


import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.IMonitorProperties;
import in.imonitorapi.util.LogUtil;
import in.monitor.authprovider.Alexa;
import in.monitor.authprovider.GoogleHome;
import in.monitor.authprovider.Oauth2Response;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;	
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;


import com.thoughtworks.xstream.XStream;

import javax.ws.rs.core.UriBuilder;





import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.hibernate.transaction.WeblogicTransactionManagerLookup;

import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.uri.UriComponent;
import com.sun.jersey.core.util.MultivaluedMapImpl;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Path("/tokengeneration")
public class GoogleHomelinking {
	HttpServletRequest request;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public  Response googlelogin(@QueryParam("custid")String customer,@QueryParam("userid")String user,@QueryParam("password")String pass,@QueryParam("state")String state,@QueryParam("client_id")String client_id,@QueryParam("redirect_uri")String redirecturi) throws Exception{
		XStream stream = new XStream();
		LogUtil.info(customer+" "+user+" "+pass+" "+state);
		String result=ClientController.sendToController("customerService","logincheckforAlexa",customer,user,pass);
		LogUtil.info(result);
		if(result.equals("True")){
		String clientid=client_id;
		String keySource = customer+clientid;
		byte [] tokenByte = Base64.encodeBase64(keySource.getBytes());
		String token = new String(tokenByte);
		LogUtil.info("pass:"+token);
		String hashpass=ClientController.getHashPassword(pass);
		ClientController.sendToController("voicecontrolservice","savegoogleuser",customer,user,hashpass,token,clientid);
		//URI uri=new URI("https://oauth-redirect.googleusercontent.com/r/imonitorskill?access_token="+token+"&token_type=bearer"+"&state="+state+"");
		//URI uri=UriBuilder.fromUri("https://oauth-redirect.googleusercontent.com/r/imonitorskill").fragment(fragment).build();
		//OAuthResponse response=OAuthASResponse.tokenResponse(HttpServletResponse.SC_FOUND).setAccessToken(token).setExpiresIn("3600").setParam("token_type", "bearer").setParam("state", state).buildJSONMessage();
		String fragment=String.format("access_token=%s&token_type=bearer&state=%s",token,state);
		ClientConfig cc=new DefaultClientConfig();
		cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
		Client c=Client.create(cc);
		WebResource resource=c.resource(UriBuilder.fromUri("https://oauth-redirect.googleusercontent.com/r/imonitorskill#"+fragment).build());
		//URI uri=UriBuilder.fromUri("https://oauth-redirect.googleusercontent.com/r/imonitorskill#"+fragment).build();
		URI uri=resource.getURI();
		LogUtil.info("printing result " +uri );
		return Response.seeOther(uri).build();
		//return Response.status(200).build();       
		
		}
		else{
			LogUtil.info("error");
			return Response.status(200).entity("error messsage").build();
		}
		}
		

}


