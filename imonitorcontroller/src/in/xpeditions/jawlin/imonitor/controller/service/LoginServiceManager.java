/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.StatusManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;


import com.thoughtworks.xstream.XStream;

public class LoginServiceManager {
	
	

	public String loginCheck(String xml) {
		XStream stream = new XStream();
		UserManager userManager = new UserManager();
		
		User userToCheck = (User) stream.fromXML(xml);
		User user = userManager.getUserWithCustomerByUserIdAndUpdateStatusAndUpdateLastLogin(userToCheck.getUserId(), userToCheck.getCustomer().getCustomerId(), userToCheck.getPassword());
		if(user == null){
			
			user = userManager.getUserWithCustomerByUserIdAndUpdateStatusAndUpdateLastLoginAndTempPassword(userToCheck.getUserId(), userToCheck.getCustomer().getCustomerId(), userToCheck.getPassword());
			
			if(user == null){
				
				return Constants.LOGIN_FAIL_MESSAGE;
			}else{
				
				return stream.toXML(user);
		
				
			}
			
			
			//return Constants.LOGIN_FAIL_MESSAGE;
		}
//		StatusManager statusManager = new StatusManager();
//		Status onlineStatus = statusManager.getStatusByName(Constants.ONLINE);
//		user.setStatus(onlineStatus);
//		userManager.updateUser(user);
		
		String resultValue = stream.toXML(user);
		return resultValue;
	}
	
	public String adminLoginCheck(String xml) {
		XStream stream = new XStream();
		UserManager userManager = new UserManager();
		User userToCheck = (User) stream.fromXML(xml);
		User user = userManager.getAdminUserWithCustomerByUserIdAndUpdateStatus(userToCheck.getUserId(), userToCheck.getPassword());
		if(user == null){
			return Constants.LOGIN_FAIL_MESSAGE_ADMIN;
		}
//		StatusManager statusManager = new StatusManager();
//		Status onlineStatus = statusManager.getStatusByName(Constants.ONLINE);
//		user.setStatus(onlineStatus);
//		userManager.updateUser(user);
		
		String resultValue = stream.toXML(user);
		return resultValue;
	}
	public String logout(String xml) {
		XStream stream = new XStream();
		StatusManager statusManager = new StatusManager();
		Status offlineStatus = statusManager.getStatusByName(Constants.OFFLINE);
		User user = (User) stream.fromXML(xml);
		UserManager userManager = new UserManager();
		//vvn TBD should pass id not userid
		user = userManager.getUserByUserId(user.getUserId());
		user.setStatus(offlineStatus);
		userManager.updateUser(user);
		String result = "success";
		if(user.getUserId().equalsIgnoreCase("admin")){
			result = "admin";
		}else if(user.getUserId().equalsIgnoreCase("alertmonitor")){
			result = "supportuser";
		}
		return stream.toXML(result);
	}
	
	public String supportlogincheck(String xml) {
		XStream stream = new XStream();
		UserManager userManager = new UserManager();
		User userToCheck = (User) stream.fromXML(xml);
		User user = userManager.getSupportUserAndUpdateStatus(userToCheck.getUserId(), userToCheck.getPassword());
		if(user == null){
			return Constants.LOGIN_FAIL_MESSAGE_ADMIN;
		}
//		StatusManager statusManager = new StatusManager();
//		Status onlineStatus = statusManager.getStatusByName(Constants.ONLINE);
//		user.setStatus(onlineStatus);
//		userManager.updateUser(user);
		
		String resultValue = stream.toXML(user);
		return resultValue;
	}
	
	public String loginCheckapi(String userid,String password,String id) {
		XStream stream = new XStream();
		UserManager userManager = new UserManager();
		User user = userManager.getUserWithCustomerByUserIdAndUpdateStatusAndUpdateLastLogin(userid,id,password);
		if(user == null){
			user = userManager.getUserWithCustomerByUserIdAndUpdateStatusAndUpdateLastLoginAndTempPassword(userid,id,password);
			if(user == null){
				
				return "Invalid CustomerId or UserId or Password";
			}else{
				
				return stream.toXML(user);	
			}
			//return Constants.LOGIN_FAIL_MESSAGE;
		}
		String resultValue = stream.toXML(user);
		return resultValue;
	}
	
	public String alertServicelogincheck(String xml) {
		XStream stream = new XStream();
		UserManager userManager = new UserManager();
		User userToCheck = (User) stream.fromXML(xml);
		User user = userManager.getalertUserAndUpdateStatus(userToCheck.getUserId(), userToCheck.getPassword());
		if(user == null){
			return Constants.LOGIN_FAIL_MESSAGE_ADMIN;
		}
//		StatusManager statusManager = new StatusManager();
//		Status onlineStatus = statusManager.getStatusByName(Constants.ONLINE);
//		user.setStatus(onlineStatus);
//		userManager.updateUser(user);
		
		String resultValue = stream.toXML(user);
		return resultValue;
	}
}
