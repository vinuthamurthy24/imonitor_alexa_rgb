package in.imonitorapi.service.manager;
import in.imonitorapi.authcontroller.dao.manager.ClentManager;
import in.imonitorapi.util.LogUtil;
import in.monitor.authprovider.Clients;

import in.imonitorapi.util.XmlUtil;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.thoughtworks.xstream.XStream;



public class TokenService {
	
	private Clients clients = new Clients();
	public static String generateAccessToken(String ClientID) throws ParseException{
		String tokenXml="";
		ClentManager clientManager = new ClentManager();	
		String Token=ClientID+System.nanoTime();
		String AccessToken=DigestUtils.md5Hex(Token);
		String RefreshToken=DigestUtils.md5Hex(AccessToken);
		LogUtil.info("AccessToken:"+ AccessToken+ "Refresh Time"+ RefreshToken);
		
		java.util.Date dt = new java.util.Date();

		java.text.SimpleDateFormat sdf = 
		new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentTime = sdf.format(dt);
		
		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date CreatedAT = new Date(); // Instantiate a Date object
		 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(CreatedAT);
		  cal.add(Calendar.MINUTE, 60);
		  Date ExpiresAt=cal.getTime();
		 	
		 String ExpireTime = sdf.format(ExpiresAt);
		 LogUtil.info("currentTime:"+ currentTime +"Expiresat"+ExpireTime);
		 
		String client = clientManager.updateTokensByQuerry(ClientID, AccessToken, RefreshToken,currentTime,ExpireTime);
		
		tokenXml=AccessToken+" "+RefreshToken+" "+currentTime+" "+ExpireTime;
		
		return tokenXml;
	}
	
	public static String VerifyToken(String AccessToken, String client_id) throws Exception{
		String Result="";
		Clients details=ClentManager.getTokensWithExpireTimeByClientIdAndUpdateStatus(client_id);
	    String CheckTokenAndExpireTime=new XStream().toXML(details);
	    Result=CheckToken(details ,AccessToken);
		LogUtil.info(new XStream().toXML(details));
		LogUtil.info("result "+ Result);
		return Result;
	}
	
	
	public static String CheckToken(Clients details, String AccessToken) throws Exception{
		String Result="";

		
		
		
		String GeneratedToken = details.getAccessToken();
		
		LogUtil.info("GeneratedToken " + GeneratedToken+ "SendToken"+ AccessToken);
		if(AccessToken.equals(GeneratedToken)){
			LogUtil.info("GeneratedToken " + GeneratedToken+ "SendToken"+ AccessToken);
			
			
			Date ExpireTime = details.getExpireTime();
			
			Date dt = new Date();
			SimpleDateFormat sdf = null;
			 sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String Ctime = sdf.format(dt);
			LogUtil.info("currentTime: "+ Ctime);
			Date ServerTime = (Date) sdf.parse(Ctime);
			if(ServerTime.getTime()<ExpireTime.getTime()){
				
				Result="ValidToken";
				
			}else{
				Result="TokenExpired";
				LogUtil.info(Result);
			}
			
		/*	SimpleDateFormat sdf=new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
		    Date currentdate;
		    currentdate=sdf.parse(ExpireTime);*/
			
			
			
			
		}else{
			Result="InvalidToken";
		}
		
	

		
		
		
		return Result;
	}
	
	public static String DeleteCustomer(String clientId, String id){
	LogUtil.info("Logging out:");	
	
	String result="Success";
	return result;
	}
	
	

}
