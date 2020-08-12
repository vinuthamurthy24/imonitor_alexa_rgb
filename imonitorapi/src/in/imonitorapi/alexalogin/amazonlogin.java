package in.imonitorapi.alexalogin;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;


import com.thoughtworks.xstream.XStream;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import com.sun.jersey.api.client.*;
import com.sun.jersey.core.util.MultivaluedMapImpl;


import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@Path("/amazon")
public class amazonlogin {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public  Response amazonlogin() throws Exception{
		LogUtil.info("getting");
		Client client=Client.create();
		WebResource web=client.resource("https://pitangui.amazon.com/api/skill/link/M1E3MJWNCV3BF9");
		web.header("client_id","amzn1.application-oa2-client.1dff5e91b5064c2d8f03d8551af59c7a");
		web.header("clien_secret", "a571f207c6fd44cc684446978f10574b3ee5f72b7914f8c14d391928b81d1c36");
		ClientResponse response=web.get(ClientResponse.class);
		if(response.getStatus()!=200){
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}else{
			LogUtil.info("success");
		}
		return Response.ok().build();		
	}

}
