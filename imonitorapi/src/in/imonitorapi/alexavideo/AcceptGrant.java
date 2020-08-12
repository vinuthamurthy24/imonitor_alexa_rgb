package in.imonitorapi.alexavideo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

@Path("/acceptGrant")
public class AcceptGrant {
	
	@GET
	@Produces("application/xml")
	public Response getTokensForCustomer(@QueryParam("code")String code,@QueryParam("token")String token)throws Exception{
		//XStream stream = new XStream();
		LogUtil.info(" code: "+code+" token "+token);
			String out="<imonitor>";
			String result = ClientController.sendToController("alexaService", "getAccessTokensFromAmazonForCustomer",code,token);
			LogUtil.info("AcceptGrant result"+result);
			return Response.status(200)
				.entity(out)
				.build();
		
	}
	

}

