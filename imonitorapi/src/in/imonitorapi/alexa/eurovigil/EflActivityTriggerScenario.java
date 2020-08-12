package in.imonitorapi.alexa.eurovigil;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import in.imonitorapi.alexa.efl.communicator.EflClientController;
import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

@Path("/activateScenario")
public class EflActivityTriggerScenario {

	@GET
	@Produces("application/xml")
	public Response activateScenario(@HeaderParam("scenario_id")String scenarioId,@HeaderParam("gatewayId") String id)throws Exception{
		
		LogUtil.info("activating scenario: " + scenarioId + "for gateway : " + id);
			String out="failure";
			String result=EflClientController.sendToController("alexaService","activateScenario",scenarioId,id);
			LogUtil.info(result);
			
			
		return Response.status(200)
				.entity(result)
				.build();
		
	}
	
	
}
