package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GoogleHome;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.VoiceControlManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import com.thoughtworks.xstream.XStream;

public class VoiceControlServices {
	
	public void savealexauser(String customerid,String userid,String password,String accesstoken,String code,String refreshToken)throws Exception{
		LogUtil.info("comming to savealexauser");
		CustomerManager customerManager = new CustomerManager();
		Customer customer = customerManager.getCustometidByCustomerName(customerid);
		UserManager userManager = new UserManager();
		User user = userManager.getUserByUserId(userid);
		VoiceControlManager voicemanager=new VoiceControlManager();
		Alexa aUser = voicemanager.checkAlexaUser(customer);
		if(aUser != null){
			
			aUser.setAccesstoken(accesstoken);
			aUser.setCode(code);
			aUser.setRefreshToken(refreshToken);
			aUser.setUser(user);
			boolean res = voicemanager.updateAlexaUser(aUser);
			
		}else{
		Alexa alexa=new Alexa();
		alexa.setCustomer(customer);
		alexa.setUserId(userid);
		alexa.setPassword(password);
		alexa.setAccesstoken(accesstoken);
		alexa.setCode(code);
		alexa.setRefreshToken(refreshToken);
		alexa.setUser(user);
		
		boolean result=voicemanager.savealexa(alexa);
		
		}
	}
	public void savegoogleuser(String customerid,String userid,String password,String code,String clientid)throws Exception{
		LogUtil.info("comming to save user");
	
		VoiceControlManager voicemanager=new VoiceControlManager();
		LogUtil.info("VoiceControlManager"+voicemanager);
		GoogleHome home=new GoogleHome();
		home.setCustomerId(customerid);
		home.setUserId(userid);
		home.setPassword(password);
		home.setCode(code);
		home.setClientId(clientid);
		boolean result=voicemanager.savegoogle(home);
		LogUtil.info("boolean result"+result);
		
		
	}
	public String getgooglecustomerbycode(String code)throws Exception{
		VoiceControlManager voicemanager=new VoiceControlManager();
		String customer=voicemanager.gettokenforghome(code);
		return customer;
	}
	public String getgooglecustomerbytoken(String token)throws Exception{
		VoiceControlManager voicemanager=new VoiceControlManager();
		String customer=voicemanager.getcustomerforghome(token);
		return customer;
	}
	public void updategoogleuser(String code,String token)throws Exception{
		VoiceControlManager voicemanager=new VoiceControlManager();
		voicemanager.updategooglehome(code, token);
	}
	
	public String getalexausertoken(String code)throws Exception{
		XStream stream = new XStream();
		VoiceControlManager voicemanager=new VoiceControlManager();
		Object[] result=voicemanager.gettokenforalexauser(code);
		String resultXml = stream.toXML(result);
		return resultXml;
	}
	
	public String getalexavideoausertoken(String code)throws Exception{
		//LogUtil.info("comming to TokenEndPoint getalexavideoausertoken");
		XStream stream = new XStream();
		VoiceControlManager voicemanager=new VoiceControlManager();
		Object[] result=voicemanager.gettokenforalexavideouser(code);
		String resultXml = stream.toXML(result);
		//LogUtil.info("getalexavideoausertoken resultXml=="+resultXml);
		return resultXml;
	}
	
public String updatealexausertokens(String oldRefreshToken,String newResfreshToken,String newAccessToken)throws Exception{
		
		VoiceControlManager voicemanager=new VoiceControlManager();
		String result = voicemanager.updateAlexaToken(oldRefreshToken, newResfreshToken, newAccessToken);
		
		return result;
	}







}
