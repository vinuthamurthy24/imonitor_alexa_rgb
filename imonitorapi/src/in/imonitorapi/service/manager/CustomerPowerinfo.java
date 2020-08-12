package in.imonitorapi.service.manager;

import java.util.List;

import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.Constants;
import in.imonitorapi.util.LogUtil;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

@Path("/devicedata/{customerId}")
public class CustomerPowerinfo {
	
	@GET
	@Produces("application/xml")
	public Response getCustomerpowerDetails(@HeaderParam("Client_Id") String Client_id,@HeaderParam("UserId") String UserId,@HeaderParam("password") String password,@PathParam("customerId") String id)throws Exception{
		//LogUtil.info("client id: "+ Client_id);
		String out="<imonitor>";
		XStream stream = new XStream();
		if((!Client_id.equalsIgnoreCase(Constants.TPDDL))){
			//LogUtil.info("pass");
			out+="failure</imonitor>";
		}else{
		
		String result=ClientController.sendToController("customerService","loginpasscheckapi1",UserId,password,id);
		//LogUtil.info(result+"checking");
		
		List<Object[]> list=(List<Object[]>)stream.fromXML(result);
		if(list!=null){
			for(Object[] obj:list){
				if((("HMD".equals(obj[6]))||obj[6] != null)){
				out+="<Devicedetails>"+"<gatewayMacId>"+obj[0]+"</gatewayMacId>"+"<generatedDeviceId>"+obj[1]+"</generatedDeviceId>"+"<deviceName>"+obj[2]+"</deviceName>"+"<deviceType>"+obj[3]+"</deviceType>"+"<powerinformation>"+"<alertValue>"+obj[4]+"</alertValue>"+
						"<alertTime>"+obj[5]+"</alertTime>"+"</powerinformation>"+"</Devicedetails>";
				}
			}
			out+="</imonitor>";
			
		}else{
			out+="failure</imonitor>";}
		}
		return Response.status(200)
				.entity(out)
				.build();
		}	
}
