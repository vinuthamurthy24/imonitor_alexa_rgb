package in.imonitorapi.alexalogin;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;


@Path("/proActiveUpdate")
public class ProactiveDeviceDiscover {
	
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public  Response discoverdevice(@HeaderParam("type") String type,@HeaderParam("accesstoken") String accessToken) throws Exception{
	
		XStream stream = new XStream();
		LogUtil.info("access token: "+accessToken );
		String tokenXml = stream.toXML(accessToken);
		ClientController.sendToController("alexaService", "sendProactiveDeviceDiscover",tokenXml);
		
		return Response.status(200).build();
	}

}
