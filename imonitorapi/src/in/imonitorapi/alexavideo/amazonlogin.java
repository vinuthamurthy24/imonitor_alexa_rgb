package in.imonitorapi.alexavideo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import in.imonitorapi.util.LogUtil;

@Path("/amazon")
public class amazonlogin {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public  Response amazonlogin() throws Exception{
		LogUtil.info("getting");
		Client client=Client.create();
		WebResource web=client.resource("https://pitangui.amazon.com/api/skill/link/M1E3MJWNCV3BF9");
		web.header("client_id","amzn1.application-oa2-client.f7774fff859f4e2489ddee4129572e67");
		web.header("clien_secret", "1c5ea0b74e10765faca0d9abec78d9eefe26a14760edbb78aa2a5b8fa9bc4ed6");
									
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
