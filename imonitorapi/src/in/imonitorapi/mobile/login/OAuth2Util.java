package in.imonitorapi.mobile.login;
import java.io.IOException;

import com.sun.jersey.api.Responses;
import in.imonitorapi.service.manager.TokenService;
import in.imonitorapi.authcontroller.dao.manager.ClentManager;
import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.Produces;
import in.imonitor.authcontroller.StoreAccessToken;
import in.monitor.authprovider.Clients;

import in.imonitorapi.util.XmlUtil;

import com.sun.jersey.core.util.Base64;
import Oauth2.imonitor.detail.OAuth2Constant;
import Oauth2.imonitor.detail.OAuth2ErrorConstant;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.ws.rs.core.Response.ResponseBuilder;
import Oauth2.imonitor.detail.OAuth2Exception;
import com.sun.jersey.api.container.MappableContainerException;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.jersey.api.core.HttpResponseContext;
import com.sun.jersey.api.core.TraceInformation;
import  com.sun.jersey.core.reflection.ReflectionHelper;
import  com.sun.jersey.core.spi.factory.ResponseImpl;
import  com.sun.jersey.server.impl.uri.rules.HttpMethodRule;
import  com.sun.jersey.spi.MessageBodyWorkers;

@Path("/login")
public class OAuth2Util  extends ActionSupport{
	
	private String data;
	private Object success;
	
	    @GET
		@Path("/autherize/{customerid}")
	    @Produces("application/xml")
		public Response addUser(@HeaderParam("RefreshToken") String RefreshToken,@HeaderParam("ExpiresAT") String ExpiresAT,@HeaderParam("AccessToken") String AccessToken, @HeaderParam("Authorization") String authHeader,
				                             @PathParam("customerid") String id, @HeaderParam("ResponseType") String responsetype,
				                             @HeaderParam("GrantType") String grantType, @HeaderParam("code") String Authcode, @HeaderParam("Client_Id") String Client_id) throws Exception{
	    	
	    	ClentManager clientManager = new ClentManager();
	    	
	    	if (grantType.equals(OAuth2Constant.GRANT_TYPE_AUTHORIZATION_CODE))
	    	   {
	    	     String Verify=clientManager.VerifyAuthCode(Client_id, Authcode);
	    	     LogUtil.info("Verified Authcode is:"+  Verify);
	    	
	    	      if (Verify=="success")
	    	         {
	    	
	    	          String userdetail=OAuth2Util.parseBasicAuthHeader(authHeader);
	    	          String params="<?xml version='1.0' encoding='UTF-8'?><imonitor><customerid>"+id+"</customerid>"+userdetail+"</imonitor>";
	    	          String serviceName = "mobileService";
	    	          String method = "authenticate";
	    	          data = ClientController.sendToController(serviceName, method, params);
	    	          String Loginstatus=authenticate(data);
	    	          LogUtil.info("Login staus:"+Loginstatus);
	    	          
	    	          if(Loginstatus.equals(success))
	    	          {
	    	          
	    	          String TokenDetails=TokenService.generateAccessToken(Client_id);
	    	          LogUtil.info("TokenDetails  "+TokenDetails);
	    	          String[] temp = TokenDetails.split(" ");
	    	           AccessToken=temp[0];
	    	           RefreshToken=temp[1];
	    	          String CreatedAt=temp[2];
	    	           ExpiresAT=temp[3];
	    	          
	    	          }
			
		
			
	    	}
	    	}
			return Response.status(200)
					.entity(" Login status : " + data)
					 .header("AccessToken", AccessToken)
					  .header("RefreshToken", RefreshToken)
					   .header("ExpiresAT", ExpiresAT)
					.build();
			       
			/*.header("CustomHeader", "CustomValue");
	    	/*return Response.ok().header("AccessToken", AccessToken).build();*/    
			      
			       
		}
	    	
	    /*public abstract Response.ResponseBuilder header(String name,
                Object value){
	    	
	    }*/
	    	

	    public static String  parseBasicAuthHeader(String authHeader) throws OAuth2Exception {
	    	String Username=null;
	    	String Password=null;
			try {
				// Basic XXXXXXXXX
				String basicToken = authHeader.split(" ")[1];
				String decoded = new String(Base64.decode(basicToken), "utf-8");
				String[] temp = decoded.split(":");
				if (temp.length == 2) {
					Username=(temp[0]);
					Password=(temp[1]);
				} else {
					Username=(temp[0]);
					
				}
			} catch (Exception e) {
				throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_PARAMETER);
			}
			  
			  
			  return "<userid>"+Username+"</userid><password>"+Password+"</password>";
			  
		
		}
	
	    public static String  parseBasicAutherization(String authHeader) throws OAuth2Exception {
	    	String ClientID=null;
	    	String ClientSecret=null;
			try {
				// Basic XXXXXXXXX
				String basicToken = authHeader.split(" ")[1];
				String decoded = new String(Base64.decode(basicToken), "utf-8");
				String[] temp = decoded.split(":");
				if (temp.length == 2) {
					ClientID=(temp[0]);
					ClientSecret=(temp[1]);
				} else {
					ClientID=(temp[0]);
					
				}
			} catch (Exception e) {
				throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_PARAMETER);
			}
			  
			  
			  return ClientID+":"+ClientSecret;
			  
		
		}
	
	    public String authenticate(String xml){
	    	String resutlXml = "";
	    	try {
				Document document = XmlUtil.getDocument(xml);
				NodeList customerNodes = document.getElementsByTagName("status");
				Element statusNode = (Element) customerNodes.item(0);
				String status = XmlUtil.getCharacterDataFromElement(statusNode);
				resutlXml=status;			
			} catch (ParserConfigurationException e) {
				resutlXml = "Couldn't parse the input";
				e.printStackTrace();
			} catch (SAXException e) {
				resutlXml = "Couldn't parse the input";
				e.printStackTrace();
			} catch (IOException e) {
				resutlXml = "Couldn't parse the input";
				e.printStackTrace();
			}

	    	
	    	return resutlXml;
	    	
	    }
	
	
	
	
	
	
	
	
	
	 /*private static final String SUCCESS = null;
     @GET
	 @Produces(MediaType.TEXT_XML)
	 private String accessTokenServerFlow(String user, @Context HttpServletRequest httpRequest) throws OAuth2Exception {
		
			if (httpRequest.getMethod().equalsIgnoreCase("GET")) {
				String authHeader = (String)httpRequest.getHeader("Authorization");
				if (authHeader == null || authHeader.equals("")) {
					throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_PARAMETER);
				}
				
				OAuth2Util.parseBasicAuthHeader(authHeader, user);
			}
			
			return SUCCESS;
			
		}*/
		
	
	 
		
 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	/*public ContainerRequest filter(ContainerRequest containerRequest) throws WebApplicationException {
	        //GET, POST, PUT, DELETE, ...
	        String method = containerRequest.getMethod();
	        // myresource/get/56bCA for example
	        String path = containerRequest.getPath(true);
	 
	        //We do allow wadl to be retrieve
	        if(method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))){
	            return containerRequest;
	        }
	 
	        //Get the authentification passed in HTTP headers parameters
	        String auth = containerRequest.getHeaderValue("authorization");
	 
	        //If the user does not have the right (does not provide any HTTP Basic Auth)
	        if(auth == null){
	            throw new WebApplicationException(Status.UNAUTHORIZED);
	        }
	 
	        //lap : loginAndPassword
	        String[] lap = OAuth2Util.decode(auth);
	        System.out.println("credentilas="+lap);
	        //If login or password fail
	        if(lap == null || lap.length != 2){
	            throw new WebApplicationException(Status.UNAUTHORIZED);
	        }
	 
	        //DO YOUR DATABASE CHECK HERE (replace that line behind)...
	       /* User authentificationResult =  AuthentificationThirdParty.authentification(lap[0], lap[1]);
	 
	        //Our system refuse login and password
	        if(authentificationResult == null){
	            throw new WebApplicationException(Status.UNAUTHORIZED);
	        }
	 
	        //TODO : HERE YOU SHOULD ADD PARAMETER TO REQUEST, TO REMEMBER USER ON YOUR REST SERVICE...
	 
	        return containerRequest ;
	    }
	 
	 public static String[] decode(String auth) {
	        //Replacing "Basic THE_BASE_64" to "THE_BASE_64" directly
	        auth = auth.replaceFirst("[B|b]asic ", "");
	 
	        //Decode the Base64 into byte[]
	        byte[] decodedBytes = DatatypeConverter.parseBase64Binary(auth);
	 
	        //If the decode fails in any case
	        if(decodedBytes == null || decodedBytes.length == 0){
	            return null;
	        }
	 
	        //Now we can convert the byte[] into a splitted array :
	        //  - the first one is login,
	        //  - the second one password
	        return new String(decodedBytes).split(":", 2);
	        
	    }*/
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	/* public static String generateClientId() {
		   int abc=133456789;
		   double id=abc + Math.random() * 5;
		   String total2 = String.valueOf(id);
		   String Client_id = total2 + System.nanoTime();
		   String token = DigestUtils.md5Hex(Client_id);
		   String secret_data = Client_id + System.nanoTime() + token;
		   ClientVO vo = new ClientVO();
		   vo.setClient_id(Client_id);
		   return "<ims><Client_id>"+Client_id+"</Client_id><Client_secret>"+secret_data+"</Client_secret></ims>";
			}*/
	 
	 
	
	/* @Consumes(MediaType.APPLICATION_XML)*/
	 /*public static String generateRequestToken(OAuthAccessor accessor) {
		  String consumer_key = (String) accessor.consumer.getProperty("name");
		  String token_data = consumer_key + System.nanoTime();
		  String token = DigestUtils.md5Hex(token_data);
		  String secret_data = consumer_key + System.nanoTime() + token;
		  String secret = DigestUtils.md5Hex(secret_data);
		    accessor.requestToken = token;
		    /*System.out.println(token);
	        accessor.tokenSecret = secret;
	        accessor.accessToken = null;
	        
	            
	        return "<ims>Request_token="+accessor.requestToken+"</ims>";
	 }*/
	
	
}
