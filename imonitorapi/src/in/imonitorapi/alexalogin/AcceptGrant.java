package in.imonitorapi.alexalogin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.net.URL;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.thoughtworks.xstream.XStream;


@Path("/acceptGrant")
public class AcceptGrant {
	
	@GET
	@Produces("application/xml")
	public Response getTokensForCustomer(@QueryParam("code")String code,@QueryParam("token")String token)throws Exception{
		//XStream stream = new XStream();
		LogUtil.info(" code: "+code+" token "+token);
			String out="<imonitor>";
			String result = ClientController.sendToController("alexaService", "getAccessTokensFromAmazonForCustomer",code,token);
			return Response.status(200)
				.entity(out)
				.build();
		
	}
	

}
