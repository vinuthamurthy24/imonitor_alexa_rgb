package in.imonitorapi.service.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.Constants;
import in.imonitorapi.util.LogUtil;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;


@Path("/scenarios")
public class ScenarioControl {
	
	@POST
	@Path("/execute")
	@Produces("application/xml")
	public Response getscenarioforapi(@QueryParam("Id")String Id,@HeaderParam("Client_Id")String Client_id,@HeaderParam("customerId") String Custid,@HeaderParam("UserId") String UserId,@HeaderParam("password") String password)throws Exception{
		XStream stream = new XStream();
		//LogUtil.info(Client_id+UserId+password+Custid+Id);
		String out="<imonitor>";
		if((!Client_id.equalsIgnoreCase(Constants.TPDDL))){
			out+="failure</imonitor>";
		}
		else{
		String result=ClientController.sendToController("commandIssueService","loginpasscheckapi",UserId,password,Custid);
		//LogUtil.info(result+"final output");
		if(result.equals("True")){
		String valuexml=ClientController.sendToController("scenarioService","getScenarioControl",Id,Custid);
		String val=(String) stream.fromXML(valuexml);
		if(val.equals("True")){
		out+="<Status>Success</Status>"+"</imonitor>";
		}else{
			out+="<Status>Failure</Status>"+"</imonitor>";
		}
		}else{
			out+="failure</imonitor>";
	}
}
return Response.status(200)
		.entity(out)
		.build();	
}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/getlist")
	@Produces("application/xml")
	public Response getscenarioforapi(@HeaderParam("Client_Id")String Client_id,@HeaderParam("customerId") String id,@HeaderParam("UserId") String UserId,@HeaderParam("password") String password)throws Exception{
		XStream stream = new XStream();
		//LogUtil.info(Client_id+UserId+password);
		String out="<imonitor>";
		if((!Client_id.equalsIgnoreCase(Constants.TPDDL))){
			out+="failure</imonitor>";
		}else{
			String result=ClientController.sendToController("commandIssueService","loginpasscheckapi",UserId,password,id);
			//LogUtil.info(result+"final output");
			if(result.equals("True")){
				String valuexml=ClientController.sendToController("scenarioService","getscenariosforapi",id);	
				List<Object[]> objects=(List<Object[]>)stream.fromXML(valuexml);
				for(Object[] obj:objects){
					out+="<scenario>"+"<Id>"+obj[0]+"</Id>"+"<Name>"+obj[1]+"</Name>"+"<Description>"+obj[2]+"</Description>"+"</scenario>";
				}
		out+="</imonitor>";
		}else{
				out+="failure</imonitor>";
		}
	}
	return Response.status(200)
			.entity(out)
			.build();	
}

}
