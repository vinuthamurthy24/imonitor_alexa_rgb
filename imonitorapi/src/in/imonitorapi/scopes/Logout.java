package in.imonitorapi.scopes;

import in.imonitorapi.service.manager.TokenService;
import in.imonitorapi.util.LogUtil;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import Oauth2.imonitor.detail.OAuth2Scope;



@Path("/logout")
public class Logout {
	
	   @POST
	   @Path("/{ScopeValue}/{customerid}")
	   @Produces("application/xml")  
	public Response LogOut(@HeaderParam("Client_id") String clientId,@PathParam("ScopeValue") String ScopeValue, @PathParam("customerid") String id) throws Exception{
		   
		   System.out.println("ScopeValue"+ ScopeValue);
		   if(ScopeValue.equals(OAuth2Scope.SCOPE_LOG_OUT)) {
			LogUtil.info("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
			String isLogoutSuccess=TokenService.DeleteCustomer(clientId, id);
			
		}else{
			LogUtil.info("elsel;mjkjh");
		}
		
		
		
		return Response.status(200)
				.entity(" status : ")
			    .build();

	}

}
