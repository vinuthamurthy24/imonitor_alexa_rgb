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

@Path("/accesstoken")
public class AccessResponse {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public  Response amazonlogin(@QueryParam("client_id")String client_id,@QueryParam("code")String code,@QueryParam("state")String state,@QueryParam("client_secret")String clientsecret) throws Exception{
		XStream stream = new XStream();
		URI uri = null;
		String clientid=client_id;
		String authcode=code;
		String client_secret=clientsecret;
		LogUtil.info(clientid+" "+authcode+" "+client_secret);
		String resultxml=ClientController.sendToController("voicecontrolservice","getalexausertoken",code);
		LogUtil.info(resultxml);
		String accesstoken=resultxml;
		uri=new URI("https://pitangui.amazon.com/api/skill/account-linking-status.html?vendorId=M1E3MJWNCV3BF9#state="+state+"&access_token="+accesstoken+"&token_type=Bearer");
		LogUtil.info("printing result " +uri);
		return Response.temporaryRedirect(uri).build();
		}

}
