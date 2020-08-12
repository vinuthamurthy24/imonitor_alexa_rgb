package in.imonitorapi.alexalogin;


import in.imonitorapi.util.LogUtil;



import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.sun.jersey.api.view.Viewable;


@Stateless
@Path("/login")
public class Loginaction  {
	
	
	@GET
    @Produces(MediaType.TEXT_HTML)
    public Response loginPage() throws  Exception {
		LogUtil.info("viewing login page");
		return Response.ok(new Viewable("/index")).build();
		}		
}
