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

@Path("/alerts")
public class AlertsAPI {
	
	@SuppressWarnings("unchecked")
	@GET
	@Produces("application/xml")
	public Response getalertsforapi(@HeaderParam("Client_Id")String Client_id,@HeaderParam("customerId") String id,@HeaderParam("UserId") String UserId,@HeaderParam("password") String password)throws Exception{
		XStream stream = new XStream();
		//LogUtil.info(Client_id+UserId+password);
		String out="<imonitor>";
		if((!Client_id.equalsIgnoreCase(Constants.TPDDL))){
			out+="failure</imonitor>";
		}else{
			String result=ClientController.sendToController("commandIssueService","loginpasscheckapi",UserId,password,id);
			//LogUtil.info(result+"final output");
			if(result.equals("True")){
			String valuexml=ClientController.sendToController("alertService","getalertsapi",id);
			//LogUtil.info(valuexml);
			List<Object[]> objects=(List<Object[]>)stream.fromXML(valuexml);
			for(Object[] obj:objects){
				out+="<alerts>"+"<Time>"+obj[0]+"</Time>"+"<Device>"+obj[1]+"</Device>"+"<alerttype>"+obj[2]+"</alerttype>"+"</alerts>";
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
