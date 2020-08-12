package in.imonitorapi.googlelinking;

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

@Stateless
@Path("/imonitorhomelogin")
public class Loginaction {
	
	@GET
    @Produces(MediaType.TEXT_HTML)
    public Response loginPage() throws  Exception {
		LogUtil.info("viewing login page");
		return Response.ok(new Viewable("/index")).build();
		}		
}
