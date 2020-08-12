package in.imonitorapi.alexa.eurovigil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import in.imonitorapi.alexa.efl.communicator.EflClientController;
import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

@Path("/password")
public class EflPasswordAssistence {

	
	@GET
	@Path("/generate")
	public Response SmaplePage(@QueryParam("customer")String customer,@QueryParam("user")String user) throws  Exception 
	{
		XStream stream = new XStream();
		String result=null;
		LogUtil.info("customer: "+ customer + " user Id"+ user);
		String userxml = stream.toXML(user);
		String customerxml = stream.toXML(customer);
		//1.Get User details to cross check mobile & email
		String resultXml = EflClientController.sendToController("userService", "toValidateAlexaUser", userxml,customerxml);
		LogUtil.info("Method step1");
		LogUtil.info(resultXml);
		if(resultXml.equals("yes")){
			LogUtil.info("if block executed");
			String resultXml1 = EflClientController.sendToController("userService", "generatePasswordAndNotifyUserForAlexa",userxml,customerxml);
			LogUtil.info(resultXml1);
			result="Your Password has been changed for "+user+".Please check your Mail and MobileNumber";
		}
		else {
			result = "Invalid CustomerId/UserId";
		}
		
		LogUtil.info("PasswordAssistence completed");	
		
		
		return Response.status(200).entity(result).build();
	}
	
}
