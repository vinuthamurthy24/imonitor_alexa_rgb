package in.imonitorapi.alexalogin;


import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.IMonitorProperties;
import in.imonitorapi.util.LogUtil;
import in.monitor.authprovider.Alexa;



import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;	
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;


import com.thoughtworks.xstream.XStream;

import javax.ws.rs.core.UriBuilder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import com.sun.jersey.api.client.*;
import com.sun.jersey.core.util.MultivaluedMapImpl;


import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Map;

@Path("/controller")
public class controller {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public  Response amazonalogin(@QueryParam("custid")String customer,@QueryParam("userid")String user,@QueryParam("password")String pass,@QueryParam("state")String state,@QueryParam("client_id")String client_id,@QueryParam("redirect_uri")String redirecturi) throws Exception{
		XStream stream = new XStream();
		LogUtil.info(customer+" "+user+" "+pass+" "+state);
		String result=ClientController.sendToController("customerService","logincheckforAlexa",customer,user,pass);
		LogUtil.info(result);
		if(result.equals("True")){
		String hashpass=ClientController.getHashPassword(pass);
		String keySource = customer;
		byte [] tokenByte = Base64.encodeBase64(keySource.getBytes());
		String token = new String(tokenByte);
		LogUtil.info("pass:"+token);
		String clientid=client_id;
		String code=generateAuthcode(clientid,customer);
		ClientController.sendToController("voicecontrolservice","savealexauser",customer,user,hashpass,token,code);
		URI uri=UriBuilder.fromUri(redirecturi).queryParam("state", state).queryParam("code",code).build();
		LogUtil.info("printing result " +uri);
		return Response.temporaryRedirect(uri).build();
		//return Response.status(200).build();
		}else{
			LogUtil.info("error");
			return Response.status(200).entity("error messsage").build();
		}
		}
	
	public String generateAuthcode(String clientId, String customer) {
		
	    /*String abc=DigestUtils.md5Hex(ClientSecret);*/
		 String code=clientId +customer;
		 String Authcode = DigestUtils.md5Hex(code);
		 LogUtil.info("autherization code:" +Authcode );			
		return Authcode;
	}
}
