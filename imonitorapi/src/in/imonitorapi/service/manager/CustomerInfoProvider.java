package in.imonitorapi.service.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



@Path("/customerlist")
public class CustomerInfoProvider {
	
	
	
	@GET
	@Produces("application/xml")
	public Response getCustomerDetailsWithDeviceInfo(@HeaderParam("Client_Id") String Client_id) throws Exception{
		
		
		//LogUtil.info("client id: "+ Client_id);
		XStream stream = new XStream();
		if(Constants.CLIENT_ID.equalsIgnoreCase(Constants.TPDDL)){
			//LogUtil.info("comming");
		String resultxml=ClientController.sendToController("customerService","listCustomersapi");
		List<Object[]> list=(List<Object[]>)stream.fromXML(resultxml);
		
		//LogUtil.info("comming2");
		//LogUtil.info(resultxml+"CustomerList");
		/*LogUtil.info(str+"CustomerList1");*/
		String result="<imonitor>";
		for(Object[] obj:list){
			result+="<customer><customerId>"+obj[0]+"</customerId><userId>"+obj[1]+"</userId><password>"+obj[2]+"</password><name>"+obj[3]+"</name>"+
		            "<mobile>"+obj[4]+"</mobile><email>"+obj[5]+"</email><gateway>"+"<macId>"+obj[6]+"</macId><status>"+obj[7]+"</status>"+
					"</gateway>"+"</customer>";
			
		}
		result+="</imonitor>";
		return Response.status(200)
				.entity(result)
				.build();
		 }else{
		 return Response.status(403)
				 .build();
		}
				 
				
	}

}
