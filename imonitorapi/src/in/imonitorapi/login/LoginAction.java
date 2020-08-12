package in.imonitorapi.login;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;



import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.view.Viewable;
import com.thoughtworks.xstream.XStream;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;



import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@Path("/get")
public class LoginAction {
	
	
	@SuppressWarnings("deprecation")
	@GET
    @Produces(MediaType.TEXT_HTML)
    public Response index(@QueryParam("customerId") String id,@QueryParam("UserId") String UserId,@QueryParam("password") String password) throws  Exception {
		URI uri=new URI("https://myhomeqi.com:9443/imonitor/apiLogin?customerId="+id+"&UserId="+UserId+"&password="+password);
		LogUtil.info("printing result " +uri);
		return Response.temporaryRedirect(uri).build();
		
		}		
}
  