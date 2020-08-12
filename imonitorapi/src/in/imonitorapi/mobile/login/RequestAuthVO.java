package in.imonitorapi.mobile.login;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URLDecoder;
import java.net.URLEncoder;
import in.imonitorapi.mobile.login.RequestBaseVO;
import Oauth2.imonitor.detail.OAuth2Constant;
import in.imonitorapi.mobile.login.OAuth2Util;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;


/*@Path("/requestoauth")*/
public class RequestAuthVO extends RequestBaseVO {
	/*private String scope;
	private String response_type;
	private String state;
	
	public RequestAuthVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RequestAuthVO(HttpServletRequest request) throws OAuth2Exception {
		this.setClient_id(request.getParameter(OAuth2Constant.CLIENT_ID));
		this.setClient_secret(request.getParameter(OAuth2Constant.CLIENT_SECRET));
		this.response_type = request.getParameter(OAuth2Constant.RESPONSE_TYPE);
		
		this.scope = request.getParameter(OAuth2Constant.CODE);
		this.state = request.getParameter(OAuth2Constant.STATE);
	}
	
	public RequestAuthVO(String client_id, String client_secret,
			String redirect_uri, String scope, String response_type,
			String state) {
		super(client_id, client_secret, redirect_uri);
		this.scope = scope;
		this.response_type = response_type;
		this.state = state;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getResponse_type() {
		return response_type;
	}

	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return this.toString(OAuth2Constant.CODE, false);
	}
	
	public String toString(String response_type, boolean isUseAuthorizationHeader) {
		String val = "scope=" + scope + 
				"&response_type=" + response_type + 
				"&state=" + state ;
		
		if (!isUseAuthorizationHeader) {
			val += "&client_id=" + OAuth2Util.encodeURIComponent(this.getClient_id());
			if (response_type.equals(OAuth2Constant.CODE)) {
				val += "&client_secret=" + OAuth2Util.encodeURIComponent(this.getClient_secret());
			}
		}
				
		return val;
	}
	
	 /*@GET
	 @Produces(MediaType.TEXT_XML)
	public String getBasicAuthorizationHeader() {
		return OAuth2Util.generateBasicAuthHeaderString(this);
	}*/
}
