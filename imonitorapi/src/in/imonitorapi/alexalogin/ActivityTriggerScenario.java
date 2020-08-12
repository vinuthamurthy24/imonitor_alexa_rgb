package in.imonitorapi.alexalogin;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;
import in.imonitorapi.util.XmlUtil;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.thoughtworks.xstream.XStream;


@Path("/activateScenario")
public class ActivityTriggerScenario {

	
	@GET
	@Produces("application/xml")
	public Response activateScenario(@HeaderParam("scenario_id")String scenarioId,@HeaderParam("gatewayId") String id)throws Exception{
		
		LogUtil.info("activating scenario: " + scenarioId + "for gateway : " + id);
			String out="failure";
			String result=ClientController.sendToController("alexaService","activateScenario",scenarioId,id);
			LogUtil.info(result);
			
			
		return Response.status(200)
				.entity(result)
				.build();
		
	}
	
}
