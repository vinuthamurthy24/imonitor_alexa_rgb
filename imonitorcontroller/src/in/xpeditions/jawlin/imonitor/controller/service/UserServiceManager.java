/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.AddUser;
import in.xpeditions.jawlin.imonitor.controller.action.DeleteUser;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.action.RevokeUser;
import in.xpeditions.jawlin.imonitor.controller.action.SuspendUser;
import in.xpeditions.jawlin.imonitor.controller.action.UpdateFriendlyName;
import in.xpeditions.jawlin.imonitor.controller.action.UpdateUser;
import in.xpeditions.jawlin.imonitor.controller.action.changePasswordUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ForgotPasswordHintQuestion;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlertAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserScenarioAccess;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.RoleManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScenarioManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.SystemAlertManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.EMailNotifications;
import in.xpeditions.jawlin.imonitor.controller.notifications.SmsNotifications;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.thoughtworks.xstream.XStream;


public class UserServiceManager {
	
	
	
	public String attatchCustomerAndSaveUser(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		UserManager userManager=new UserManager();
		XStream xStream=new XStream();

		User user=(User)xStream.fromXML(xml);
		//LogUtil.info("user.getUserId()---"+user.getUserId());
		
		
		String customerId = user.getCustomer().getCustomerId();
		CustomerManager customerManager = new CustomerManager();
		Customer customer = customerManager.getCustomerByCustomerId(customerId);
		
		String isDuplicate = verifyUserDetails(user,customer.getId());
		
		
		
		if(customer != null){
			user.setCustomer(customer);
			
			//boolean result1 = userManager.checkUserExistence(user.getUserId());
			//if(result1){
			
			if(user.getRole().getId() == 1)
			{
				String mainuserExist = verifyMainUserExist(user,customer.getId());
				if(mainuserExist.equalsIgnoreCase("MainUserExist")){
					result="MainUserExist";
				}
				else
				{
					user.setCustomer(customer);
					
					//boolean result1 = userManager.checkUserExistence(user.getUserId());
					//if(result1){
					
					
					/*if(mainuserExist.equalsIgnoreCase("MainUserExist")){
						result="MainUserExist";
					}
					else
					{*/
					if(isDuplicate.equalsIgnoreCase("duplicateuser")){
						result="no";
					}
					else{
						try {
						if(userManager.saveUser(user))
						{
							result = "yes";
							
							//Naveen added for whatsapp alert and sms and email
							SystemAlertManager systemAlertManager = new SystemAlertManager();
							List<Object[]> userNotifications = userManager.getMainUserNotifications(user.getCustomer().getId(),Constants.MAIN_USER);
							SystemAlert systemAlert = null;
							systemAlert = systemAlertManager.getSystemAlertByName(Constants.ADD_USER);
							if(userNotifications.size()>0){
								for(Object[] notification:userNotifications){
									List<Object[]> systemAlertByUser =  systemAlertManager.getSystemAlertByUserAndAlertId(notification[4].toString(),systemAlert.getId());
									
											if(systemAlertByUser != null){
											
							String[] recipiantsBYsms ={notification[1].toString()};
							String[] recipiantsBYmail ={notification[0].toString()};
								for (Object[] object : systemAlertByUser) {
													 if(object[1].equals(Constants.SEND_SMS)){
													 SmsNotifications notifications = new SmsNotifications();
													 notifications.notify("User "+user.getUserId()+ " is suspended.",recipiantsBYsms, null,user.getCustomer());
												 }
													 if(object[1].equals(Constants.SEND_EMAIL)){
													 EMailNotifications eMailNotifications = new EMailNotifications();
													 eMailNotifications.notify("User "+user.getUserId()+ " is suspended.",recipiantsBYmail, null, notification[3].toString());
												 }
													
												}
											}
									}
								}
							
						}
						else
						{
							result = "false";
						}
						} catch (Exception e) {
							// TODO: handle exception
							}
					
				}
					
				}
			
			}
			else
			{

				user.setCustomer(customer);
				
				
				if(isDuplicate.equalsIgnoreCase("duplicateuser")){
					result="no";
				}
				else{
					try {
					if(userManager.saveUser(user))
					{
						//LogUtil.info("user.getUserId()--"+user.getUserId());
						User saveduser=userManager.getUserByUserId(user.getUserId());
						
						GatewayManager GatewayManager=new GatewayManager();
						//3gp start
						List<GateWay> gateWays=GatewayManager.getGateWaysByCustomer(user.getCustomer());
						if (gateWays.size() > 1) 
						{
							for (GateWay gateWay : gateWays)
							{
							//GateWay macid=GatewayManager.getGateWayByCustomer(user.getCustomer());
							GateWay gateway=GatewayManager.getGateWayWithAgentById(gateWay.getId());
							ActionModel actionModel = new ActionModel(gateway,saveduser);
							ImonitorControllerAction AddUser=new AddUser();
							AddUser.executeCommand(actionModel);

							boolean resultFromImvg = IMonitorUtil.waitForResult(AddUser);
							result="no";
							if(resultFromImvg){
								if(AddUser.isActionSuccess()){
									//LogUtil.info("updating device");
									
										result = "yes";
										//Naveen added for whatsapp alert and sms and email
										SystemAlertManager systemAlertManager = new SystemAlertManager();
										List<Object[]> userNotifications = userManager.getMainUserNotifications(user.getCustomer().getId(),Constants.MAIN_USER);
										SystemAlert systemAlert = null;
										systemAlert = systemAlertManager.getSystemAlertByName(Constants.SUSPEND_USER);
										if(userNotifications.size()>0){
											for(Object[] notification:userNotifications){
												List<Object[]> systemAlertByUser =  systemAlertManager.getSystemAlertByUserAndAlertId(notification[4].toString(),systemAlert.getId());
												
														if(systemAlertByUser != null){
														
										String[] recipiantsBYsms ={notification[1].toString()};
										String[] recipiantsBYmail ={notification[0].toString()};
											for (Object[] object : systemAlertByUser) {
																 if(object[1].equals(Constants.SEND_SMS)){
																 SmsNotifications notifications = new SmsNotifications();
																 notifications.notify("User "+user.getUserId()+ " is added.",recipiantsBYsms, null,user.getCustomer());
															 }
																 if(object[1].equals(Constants.SEND_EMAIL)){
																 EMailNotifications eMailNotifications = new EMailNotifications();
																 eMailNotifications.notify("User "+user.getUserId()+ " is added.",recipiantsBYmail, null, notification[3].toString());
															 }
																
															}
														}
												}
											}
										
										
								}
								else
								{
								
									    result = "Actionfailure";
									    userManager.deleteUser(user);
								}
								
							}
							else
							{
								 result = "imvg_failure";
								 userManager.deleteUser(user);
								 
							}		
						}
							return xStream.toXML(result);

						}	
						else
						{
							GateWay macid=GatewayManager.getGateWayByCustomer(user.getCustomer());
							GateWay gateway=GatewayManager.getGateWayWithAgentById(macid.getId());
							ActionModel actionModel = new ActionModel(gateway,saveduser);
							ImonitorControllerAction AddUser=new AddUser();
							AddUser.executeCommand(actionModel);

							boolean resultFromImvg = IMonitorUtil.waitForResult(AddUser);
							result="no";
							if(resultFromImvg){
								if(AddUser.isActionSuccess()){
									//LogUtil.info("updating device");
									
										result = "yes";
										//Naveen added for whatsapp alert and sms and email
										SystemAlertManager systemAlertManager = new SystemAlertManager();
										List<Object[]> userNotifications = userManager.getMainUserNotifications(user.getCustomer().getId(),Constants.MAIN_USER);
										SystemAlert systemAlert = null;
										systemAlert = systemAlertManager.getSystemAlertByName(Constants.SUSPEND_USER);
										if(userNotifications.size()>0){
											for(Object[] notification:userNotifications){
												List<Object[]> systemAlertByUser =  systemAlertManager.getSystemAlertByUserAndAlertId(notification[4].toString(),systemAlert.getId());
												
														if(systemAlertByUser != null){
														
										String[] recipiantsBYsms ={notification[1].toString()};
										String[] recipiantsBYmail ={notification[0].toString()};
											for (Object[] object : systemAlertByUser) {
																 if(object[1].equals(Constants.SEND_SMS)){
																 SmsNotifications notifications = new SmsNotifications();
																 notifications.notify("User "+user.getUserId()+ " is added.",recipiantsBYsms, null,user.getCustomer());
															 }
																 if(object[1].equals(Constants.SEND_EMAIL)){
																 EMailNotifications eMailNotifications = new EMailNotifications();
																 eMailNotifications.notify("User "+user.getUserId()+ " is added.",recipiantsBYmail, null, notification[3].toString());
															 }
																 
															}
														}
												}
											}
										
										
								}
								else
								{
								
									    result = "Actionfailure";
									    userManager.deleteUser(user);
								}
								
							}
							else
							{
								 result = "imvg_failure";
								 userManager.deleteUser(user);
								 
							}
							return xStream.toXML(result);
						}
						//3gp end
							
				}
					else
					{
						result = "false";
					}
					} catch (Exception e) {
						// TODO: handle exception
						}
				
			}
			
			
		}
		}else{
			result = "false";
		}
		
		return xStream.toXML(result);
	}
	
	@SuppressWarnings("unchecked")
	public String verifyUserDetails(User user,long customer){
		String result = "validUser";
		try {
			
			List<User> users = new DaoManager().get("customer",customer, User.class);
			
			//LogUtil.info(" verifyUserDetails user.getCustomer().getId()---"+cust.getId());
			
			for(User userFromDb : users){
				
			if(user.getUserId().equals(userFromDb.getUserId()))
			{
				
				CustomerManager ss=new CustomerManager();
				Customer cust=ss.getCustometidByCustomerName(user.getCustomer().getCustomerId());
				
				
				
				
				if((cust.getId() == userFromDb.getCustomer().getId()))
				{
					result = "duplicateuser";
					return result;
				} 
			}
			}
		} catch (Exception e) {
			LogUtil.info("Got Exception while saving Room: ", e);
		}
		return result;
	}
	
	public String deleteUser(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		
		String result="no";
		XStream xStream=new XStream();
		long id=(Long)xStream.fromXML(xml);
		UserManager userManager=new UserManager();
		User user = userManager.getUserById(id);
		User saveduser=userManager.getUserByUserId(user.getUserId());
		
		
		GatewayManager GatewayManager=new GatewayManager();
		GateWay macid=GatewayManager.getGateWayByCustomer(user.getCustomer());
		GateWay gateway=GatewayManager.getGateWayWithAgentById(macid.getId());
		ActionModel actionModel = new ActionModel(gateway,saveduser);
		ImonitorControllerAction DeleteUser=new DeleteUser();
		DeleteUser.executeCommand(actionModel);
		
		boolean resultFromImvg = IMonitorUtil.waitForResult(DeleteUser);
		result="no";
		if(resultFromImvg){
			if(DeleteUser.isActionSuccess()){
				//LogUtil.info("updating device");
				if(userManager.deleteUser(user)){
					result="yes";
					
					//Naveen added for whatsapp alert and sms and email
					SystemAlertManager systemAlertManager = new SystemAlertManager();
					List<Object[]> userNotifications = userManager.getMainUserNotifications(user.getCustomer().getId(),Constants.MAIN_USER);
					SystemAlert systemAlert = null;
					systemAlert = systemAlertManager.getSystemAlertByName(Constants.DELETE_USER);
					if(userNotifications.size()>0){
						for(Object[] notification:userNotifications){
							List<Object[]> systemAlertByUser =  systemAlertManager.getSystemAlertByUserAndAlertId(notification[4].toString(),systemAlert.getId());
							
									if(systemAlertByUser != null){
									
										String[] recipiantsBYsms ={notification[1].toString()};
										String[] recipiantsBYmail ={notification[0].toString()};
						for (Object[] object : systemAlertByUser) {
											 if(object[1].equals(Constants.SEND_SMS)){
											 SmsNotifications notifications = new SmsNotifications();
											 notifications.notify("User "+user.getUserId()+ " is deleted.",recipiantsBYsms, null,user.getCustomer());
										 }
											 if(object[1].equals(Constants.SEND_EMAIL)){
											 EMailNotifications eMailNotifications = new EMailNotifications();
											 try {
												 eMailNotifications.notify("User "+user.getUserId()+ " is deleted.",recipiantsBYmail, null, notification[3].toString());
											} catch (Exception e) {
												// TODO: handle exception
												e.printStackTrace();
											}
											 
										 }
											
										}
									}
							}
						}
					
					
				}
				else
				{
					result="no";
				}
					
			}
			else
			{
				    result = "Actionfailure";
				    
			}
			
		}
		else
		{
			 result = "imvg_failure";
			
			 
		}
		if(userManager.deleteUser(user)){result="yes";}
		
		return result;
	}
	
	public String revokeUser(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		long id=(Long)xStream.fromXML(xml);
		UserManager userManager=new UserManager();
		User userFromDb = userManager.getUserById(id);
		Status status = IMonitorUtil.getStatuses().get(Constants.OFFLINE);
		userFromDb.setStatus(status);
		
		
		User saveduser=userManager.getUserByUserId(userFromDb.getUserId());
		
		GatewayManager GatewayManager=new GatewayManager();
		GateWay macid=GatewayManager.getGateWayByCustomer(userFromDb.getCustomer());
		GateWay gateway=GatewayManager.getGateWayWithAgentById(macid.getId());
		ActionModel actionModel = new ActionModel(gateway,saveduser);
		ImonitorControllerAction RevokeUser=new RevokeUser();
		RevokeUser.executeCommand(actionModel);
		
		boolean resultFromImvg = IMonitorUtil.waitForResult(RevokeUser);
		result="no";
		if(resultFromImvg){
			if(RevokeUser.isActionSuccess()){
				//LogUtil.info("updating device");
				if(userManager.updateUser(userFromDb))
				{
					result="yes";
				}
				else
				{
					result="no";
				}
					
			}
			else
			{
				    result = "Actionfailure";
				    
			}
			
		}
		else
		{
			 result = "imvg_failure";
			
			 
		}
		
		
		
		return result;
	}
	
	public String suspendUser(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		long id=(Long)xStream.fromXML(xml);
		UserManager userManager=new UserManager();
		User userFromDb = userManager.getUserById(id);
		Status status = IMonitorUtil.getStatuses().get(Constants.SUSPEND);
		userFromDb.setStatus(status);
		
		
		User saveduser=userManager.getUserByUserId(userFromDb.getUserId());
		
		GatewayManager GatewayManager=new GatewayManager();
		GateWay macid=GatewayManager.getGateWayByCustomer(userFromDb.getCustomer());
		GateWay gateway=GatewayManager.getGateWayWithAgentById(macid.getId());
		ActionModel actionModel = new ActionModel(gateway,saveduser);
		ImonitorControllerAction SuspendUser=new SuspendUser();
		SuspendUser.executeCommand(actionModel);
		
		boolean resultFromImvg = IMonitorUtil.waitForResult(SuspendUser);
		result="no";
		if(resultFromImvg){
			if(SuspendUser.isActionSuccess()){
				//LogUtil.info("updating device");
				if(userManager.updateUser(userFromDb))
				{
					result="yes";
					
					//Naveen added for whatsapp alert and sms and email
					SystemAlertManager systemAlertManager = new SystemAlertManager();
					List<Object[]> userNotifications = userManager.getMainUserNotifications(userFromDb.getCustomer().getId(),Constants.MAIN_USER);
					SystemAlert systemAlert = null;
					systemAlert = systemAlertManager.getSystemAlertByName(Constants.SUSPEND_USER);
					if(userNotifications.size()>0){
						for(Object[] notification:userNotifications){
							List<Object[]> systemAlertByUser =  systemAlertManager.getSystemAlertByUserAndAlertId(notification[4].toString(),systemAlert.getId());
							
									if(systemAlertByUser != null){
									
					String[] recipiantsBYsms ={notification[1].toString()};
					String[] recipiantsBYmail ={notification[0].toString()};
						for (Object[] object : systemAlertByUser) {
											 if(object[1].equals(Constants.SEND_SMS)){
											 SmsNotifications notifications = new SmsNotifications();
											 notifications.notify("User "+userFromDb.getUserId()+ " is suspended.",recipiantsBYsms, null,userFromDb.getCustomer());
										 }
											 if(object[1].equals(Constants.SEND_EMAIL)){
											 EMailNotifications eMailNotifications = new EMailNotifications();
											 try {
												 eMailNotifications.notify("User "+userFromDb.getUserId()+ " is suspended.",recipiantsBYmail, null, notification[3].toString());
											} catch (Exception e) {
												e.printStackTrace();
											}
											
										 }
											
										}
									}
							}
						}
					
				}
				else
				{
					result="no";
				}
					
			}
			else
			{
				    result = "Actionfailure";
				    
			}
			
		}
		else
		{
			 result = "imvg_failure";
			
			 
		}
		return result;
	}
	public String updateUser(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		User user=(User)xStream.fromXML(xml);
		UserManager userManager=new UserManager();
		User userFromDb = userManager.getUserById(user.getId());
		if(user.getPassword() != null && !user.getPassword().equals(""))
		{
			userFromDb.setPassword(user.getPassword());
		}
		userFromDb.setEmail(user.getEmail());
		userFromDb.setMobile(user.getMobile());
		
			
			User saveduser=userManager.getUserById(userFromDb.getId());
			GatewayManager GatewayManager=new GatewayManager();
			GateWay macid=GatewayManager.getGateWayByCustomer(saveduser.getCustomer());
			GateWay gateway=GatewayManager.getGateWayWithAgentById(macid.getId());
			ActionModel actionModel = new ActionModel(gateway,saveduser);
			ImonitorControllerAction UpdateUser=new UpdateUser();
			UpdateUser.executeCommand(actionModel);
			
			boolean resultFromImvg = IMonitorUtil.waitForResult(UpdateUser);
			result="no";
			
			if(resultFromImvg){
				if(UpdateUser.isActionSuccess()){
					//LogUtil.info("updating device");
					if(userManager.updateUser(userFromDb))
					{
						result = "yes";
				    }
				}
				else
				{
					    result = "Actionfailure";
					  
				}
				
			}
			else
			{
				 result = "imvg_failure";
				
				 
			}
			return xStream.toXML(result);
		
		
		
	}
	public String listOfUser() throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream=new XStream();
		UserManager userManager=new UserManager();
		List<User>users=userManager.listOfUsers();
		String xml=xStream.toXML(users);
		return xml;
	}
	//bhavya start
	public String CountUserByCustomer(String xmlcustomer) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream=new XStream();
		long id = (Long) xStream.fromXML(xmlcustomer);
		UserManager userManager=new UserManager();
		long count=userManager.getTtoalUserCountByCustomerId(id);
		String xml=xStream.toXML(count);
		return xml;
	}
	//bhavya end
	// ******************************************** sumit start: forgot password user authentication *********************************************
	//called from forgotPasswordAssistance() in UserManagementAction.java 
	public String toValidateUser(String xml){
		String result = "";
		XStream stream = new XStream();
		User userToValidate = (User) stream.fromXML(xml);
		String userId = userToValidate.getUserId();
		String customerId = userToValidate.getCustomer().getCustomerId();
		
		CustomerManager customerManager = new CustomerManager();
		UserManager userManager = new UserManager();
		
		//1.Validate customerExistence for specified customerId.
		boolean customerExists = customerManager.checkCustomerExistence(customerId);
		if(customerExists){
			//If customerExists = true, means customer exists.
			//2.Validate userExistence for specified userId.
			boolean userExists = userManager.checkUserExistence(userId);
			if(userExists){
				//if userExists = true, means user doesn't exist.
			
				return null;
			}else{
				result = "yes";
			}
		}else {
			LogUtil.info("CUSTOMER DOES NOT EXIST");
			result = "invalidcustomerid";
		}
		return result;
	}
	
	//called from forgotPasswordAssistance() in UserManagementAction.java
	public String getUserForPasswordChange(String xml){
		String resultXml = "";
		XStream stream = new XStream();
		User userToGet = (User) stream.fromXML(xml);
		String userId = userToGet.getUserId();
		UserManager userManager = new UserManager();
		User userFromDB = userManager.getUserForPasswordChange(userId, userToGet.getCustomer().getCustomerId());
		resultXml = stream.toXML(userFromDB);
		return resultXml;
	}
	
	public String listResetPasswordHintQuestions(){
		
		XStream stream = new XStream();
		List<ForgotPasswordHintQuestion> list = new ArrayList<ForgotPasswordHintQuestion>();
		UserManager userManager = new UserManager();
		//LogUtil.info("l1");
		//list = userManager.listAllResetPasswordHintQuestions();
		list = userManager.listOfHintQuestions();
//		for(ForgotPasswordHintQuestion f: list){
//			LogUtil.info("id: "+f.getId());
//			LogUtil.info("ques: "+f.getQuestion());
//		}
		//for(String s: list1){
//		for(CustomerPasswordHintQuestionAnswer s: list1){
//			LogUtil.info("id: "+s.getQuesId());
//			LogUtil.info("Ques: "+s.getHintQuestion());
//		}
		String questionListXml = stream.toXML(list);
		
		return questionListXml;
	}
	// ******************************************** sumit end: forgot password user authentication ***********************************************
	public String resetPassword(String xml){
		XStream xStream=new XStream();
		User userToCheck =  (User) xStream.fromXML(xml);
		UserManager userManager=new UserManager();
		 String  password = IMonitorUtil.generateTransactionId();
		 String hashPassword = IMonitorUtil.getHashPassword(password);
		 User user = userManager.getUserWithCustomerByUserIdAndUpdatePassword(userToCheck.getUserId(), userToCheck.getCustomer().getCustomerId(),hashPassword);
		 User selectedUser = null;
		 if(user != null){
				 selectedUser = userManager.getUserWithCustomerByUserIdAndUpdateStatus(userToCheck.getUserId(), userToCheck.getCustomer().getCustomerId(),hashPassword);
				 String[] recipiantsBYsms ={user.getMobile()};
				 String[] recipiantsBYmail ={user.getEmail()};
				 String message = IMonitorUtil.generateSmsMessage(password);
				 
				 EMailNotifications eMailNotifications = new EMailNotifications();
				 try {
					 eMailNotifications.notify(message+" </br></br>  :",recipiantsBYmail, null,selectedUser.getUserId());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			
				 SmsNotifications notifications = new SmsNotifications();
				 notifications.notify(message,recipiantsBYsms, null,selectedUser.getCustomer());
				 
		 }
		String resultXml=xStream.toXML(selectedUser);
		return resultXml;
	}
	public String changePasswordByAdmin(String xml,String confirmPassword){
		String result="no";
		XStream xStream=new XStream();
		User userToCheck =  (User) xStream.fromXML(xml);
		String password = (String) xStream.fromXML(confirmPassword);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		UserManager userManager=new UserManager();
		User user = userManager.getUserByUserIdAndUpdatePasswordByAdmin(userToCheck.getUserId(), userToCheck.getCustomer().getCustomerId(),hashPassword);
			if(user != null){
				result="yes";
			}
		String resultXml=xStream.toXML(result);
		return resultXml;
	}
	public String changePassword(String xml,String confirmPassword){
		String result="no";
		XStream xStream=new XStream();
		User userToCheck =  (User) xStream.fromXML(xml);
		String password = (String) xStream.fromXML(confirmPassword);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		UserManager userManager=new UserManager();
		User olduser=userManager.getUserById(userToCheck.getId());
		User user = userManager.getUserWithCustomerByUserIdAndUpdatePassword(userToCheck.getUserId(), userToCheck.getCustomer().getCustomerId(),hashPassword);
		
		
		User saveduser=userManager.getUserById(user.getId());
		GatewayManager GatewayManager=new GatewayManager();
		//3gp start		
		List<GateWay> gateWays= GatewayManager.getGateWaysByCustomer(user.getCustomer());
		if (gateWays.size() > 1) 
		{
			//Commented by apoorva for 3gp issue password change start (17-Nov-2018)
			/*for (GateWay gateWay : gateWays)
			{
				//Changed by apoorva for 3gp
			//GateWay macid=GatewayManager.getGateWayByCustomer(saveduser.getCustomer());
			GateWay gateway=GatewayManager.getGateWayWithAgentById(gateWay.getId());
			ActionModel actionModel = new ActionModel(gateway,saveduser);
			ImonitorControllerAction changePasswordUser=new changePasswordUser();
			changePasswordUser.executeCommand(actionModel);
			
			boolean resultFromImvg = IMonitorUtil.waitForResult(changePasswordUser);
			//result="no";
			LogUtil.info("resultFromImvg----"+resultFromImvg);
			try {
				if(resultFromImvg){
					if(changePasswordUser.isActionSuccess()){
							if(user != null){
								User userFromDB = userManager.getUserForPasswordChange(user.getUserId(), userToCheck.getCustomer().getCustomerId());
								String userXml = xStream.toXML(userFromDB);
								String alertXml = xStream.toXML(Constants.PASSWORD_CHANGED);
								notifyMainUserBySmsAndEmail(userXml, alertXml);
								result="yes";
							}
							
							//Naveen added
							SystemAlertManager systemAlertManager = new SystemAlertManager();
							List<Object[]> userNotifications = userManager.getMainUserNotifications(saveduser.getId(),Constants.MAIN_USER);
							
							SystemAlert systemAlert = null;
						
								 systemAlert = systemAlertManager.getSystemAlertByName(Constants.PW_CHANGE);
							
							
							if(userNotifications.size()>0){
								for(Object[] notification:userNotifications){
									
									//String message ="";
								
									List<Object[]> systemAlertByUser =  systemAlertManager.getSystemAlertByUserAndAlertId(notification[4].toString(),systemAlert.getId());
									
											if(systemAlertByUser != null){
											
									//	message += IMonitorUtil.generateMailMessage("IMVG is:"+statusName,notification[3].toString(),"", macId.toString(),notification[2].toString());
												
										
									//**
									 *KanthaRaj Changed To Notify To Customer,Insted of MainUser For System Alerts. 
									 * 
									 * String[] recipiantsBYsms ={notification[1].toString()};
									 String[] recipiantsBYmail ={notification[0].toString()};*//*
												
									//*
									 * KanthaRaj Changed To Notify To MainUser,Insted of Customer For System Alerts.to resolve bug-1039
									 * on 15-04-2013
									 * 
									 * String[] recipiantsBYsms ={notification[5].toString()};
									 * String[] recipiantsBYmail ={notification[6].toString()};
									 * 
									  *//*
										
										String[] recipiantsBYsms ={notification[1].toString()};
										String[] recipiantsBYmail ={notification[0].toString()};
												 for (Object[] object : systemAlertByUser) {
													 if(object[1].equals(Constants.SEND_SMS)){
													 SmsNotifications notifications = new SmsNotifications();
													 notifications.notify("Password changed for user "+saveduser.getUserId(),recipiantsBYsms, null,saveduser.getCustomer());
												 }
													 if(object[1].equals(Constants.SEND_EMAIL)){
													 EMailNotifications eMailNotifications = new EMailNotifications();
													 eMailNotifications.notify("Password changed for user "+saveduser.getUserId(),recipiantsBYmail, null, notification[3].toString());
												 }
													 
												}
											}
									}
								}
							
							
					}else{
						    result = "Actionfailure";
						    userManager.updateUser(olduser);
					}
					
				}else{
					 result = "imvg_failure";
					 userManager.updateUser(olduser);
					 
				}
			} catch (Exception e) {
				LogUtil.info("Caught Exception while changing password : ", e);
			}
			
			}*/
			//Commented by apoorva for 3gp issue password change end
			
			User userFromDB = userManager.getUserForPasswordChange(user.getUserId(), userToCheck.getCustomer().getCustomerId());
			String userXml = xStream.toXML(userFromDB);
			String alertXml = xStream.toXML(Constants.PASSWORD_CHANGED);
			notifyMainUserBySmsAndEmail(userXml, alertXml);
			result="yes";
			
			//Naveen added
			SystemAlertManager systemAlertManager = new SystemAlertManager();
			List<Object[]> userNotifications = userManager.getMainUserNotifications(saveduser.getId(),Constants.MAIN_USER);
			
			SystemAlert systemAlert = null;
		
				 systemAlert = systemAlertManager.getSystemAlertByName(Constants.PW_CHANGE);
			
			
			if(userNotifications.size()>0){
				for(Object[] notification:userNotifications){
					
					//String message ="";
				
					List<Object[]> systemAlertByUser =  systemAlertManager.getSystemAlertByUserAndAlertId(notification[4].toString(),systemAlert.getId());
					
							if(systemAlertByUser != null){
							
					//	message += IMonitorUtil.generateMailMessage("IMVG is:"+statusName,notification[3].toString(),"", macId.toString(),notification[2].toString());
								
						
					/***
					 *KanthaRaj Changed To Notify To Customer,Insted of MainUser For System Alerts. 
					 * 
					 * String[] recipiantsBYsms ={notification[1].toString()};
					 String[] recipiantsBYmail ={notification[0].toString()};*/
								
					/**
					 * KanthaRaj Changed To Notify To MainUser,Insted of Customer For System Alerts.to resolve bug-1039
					 * on 15-04-2013
					 * 
					 * String[] recipiantsBYsms ={notification[5].toString()};
					 * String[] recipiantsBYmail ={notification[6].toString()};
					 * 
					  */
						
						String[] recipiantsBYsms ={notification[1].toString()};
						String[] recipiantsBYmail ={notification[0].toString()};
								 for (Object[] object : systemAlertByUser) {
									 if(object[1].equals(Constants.SEND_SMS)){
									 SmsNotifications notifications = new SmsNotifications();
									 notifications.notify("Password changed for user "+saveduser.getUserId(),recipiantsBYsms, null,saveduser.getCustomer());
								 }
									 if(object[1].equals(Constants.SEND_EMAIL)){
									 EMailNotifications eMailNotifications = new EMailNotifications();
									 try {
										 eMailNotifications.notify("Password changed for user "+saveduser.getUserId(),recipiantsBYmail, null, notification[3].toString());
									} catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
									}
									
								 }
									 
								}
							}
					}
				}
		}
		//3gp end
		else 
		{
				//Changed by apoorva for 3gp
			GateWay macid=GatewayManager.getGateWayByCustomer(saveduser.getCustomer());
			GateWay gateway=GatewayManager.getGateWayWithAgentById(macid.getId());
			ActionModel actionModel = new ActionModel(gateway,saveduser);
			ImonitorControllerAction changePasswordUser=new changePasswordUser();
			changePasswordUser.executeCommand(actionModel);
			
			boolean resultFromImvg = IMonitorUtil.waitForResult(changePasswordUser);
			//result="no";
			
			try {
				if(resultFromImvg){
					if(changePasswordUser.isActionSuccess()){
							if(user != null){
								User userFromDB = userManager.getUserForPasswordChange(user.getUserId(), userToCheck.getCustomer().getCustomerId());
								String userXml = xStream.toXML(userFromDB);
								String alertXml = xStream.toXML(Constants.PASSWORD_CHANGED);
								notifyMainUserBySmsAndEmail(userXml, alertXml);
								result="yes";
							}
							
							//Naveen added
							SystemAlertManager systemAlertManager = new SystemAlertManager();
							List<Object[]> userNotifications = userManager.getMainUserNotifications(saveduser.getId(),Constants.MAIN_USER);
							
							SystemAlert systemAlert = null;
						
								 systemAlert = systemAlertManager.getSystemAlertByName(Constants.PW_CHANGE);
							
							
							if(userNotifications.size()>0){
								for(Object[] notification:userNotifications){
									
									//String message ="";
								
									List<Object[]> systemAlertByUser =  systemAlertManager.getSystemAlertByUserAndAlertId(notification[4].toString(),systemAlert.getId());
									
											if(systemAlertByUser != null){
											
									//	message += IMonitorUtil.generateMailMessage("IMVG is:"+statusName,notification[3].toString(),"", macId.toString(),notification[2].toString());
												
										
									/***
									 *KanthaRaj Changed To Notify To Customer,Insted of MainUser For System Alerts. 
									 * 
									 * String[] recipiantsBYsms ={notification[1].toString()};
									 String[] recipiantsBYmail ={notification[0].toString()};*/
												
									/**
									 * KanthaRaj Changed To Notify To MainUser,Insted of Customer For System Alerts.to resolve bug-1039
									 * on 15-04-2013
									 * 
									 * String[] recipiantsBYsms ={notification[5].toString()};
									 * String[] recipiantsBYmail ={notification[6].toString()};
									 * 
									 * **/
										
										String[] recipiantsBYsms ={notification[1].toString()};
										String[] recipiantsBYmail ={notification[0].toString()};
												 for (Object[] object : systemAlertByUser) {
													 if(object[1].equals(Constants.SEND_SMS)){
													 SmsNotifications notifications = new SmsNotifications();
													 notifications.notify("Password changed for user "+saveduser.getUserId(),recipiantsBYsms, null,saveduser.getCustomer());
												 }
													 if(object[1].equals(Constants.SEND_EMAIL)){
													 EMailNotifications eMailNotifications = new EMailNotifications();
													 eMailNotifications.notify("Password changed for user "+saveduser.getUserId(),recipiantsBYmail, null, notification[3].toString());
												 }
													
												}
											}
									}
								}
							
							
					}else{
						    result = "Actionfailure";
						    userManager.updateUser(olduser);
					}
					
				}else{
					 result = "imvg_failure";
					 userManager.updateUser(olduser);
					 
				}
			} catch (Exception e) {
				LogUtil.info("Caught Exception while changing password : ", e);
			}

		}
		
		return xStream.toXML(result);
		
	}
	public String listAskedUsers(String xml){
		XStream xStream=new XStream();
		String[] items = xml.split("-");
		DataTable dataTable =  (DataTable) xStream.fromXML(items[0]);
		long id = (Long) xStream.fromXML(items[1]);
		UserManager userManager = new UserManager();
		String sSearch = dataTable.getsSearch();
		String[] cols = { "u.userId","s.name","u.lastLoginTime"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = userManager.listAskedUsers(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),id);
		dataTable.setTotalRows(userManager.getTtoalUserCountByCustomerId(id));
		dataTable.setResults(list);
		int displayRow = userManager.getUserCountByCustomerId(sSearch,id);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	public String getUserById(String xml){
		XStream xStream=new XStream();
		long id = (Long) xStream.fromXML(xml);
		UserManager userManager = new UserManager();
		User user = userManager.getUserById(id);
		return xStream.toXML(user);
	}
	
	//sumit start: [Sept 06, 2012] Logged in notification to main user
	public void notifyMainUserBySmsAndEmail(String userXml, String alertXml){
		XStream stream = new XStream();
		User user = (User) stream.fromXML(userXml);
		UserManager userManager = new UserManager();
		Customer customer = userManager.getCustomerByMainUserId(user.getId());
		SystemAlertManager systemAlertManager = new SystemAlertManager();
		List<SystemAlertAction> systemAlertActions = systemAlertManager.getSystemAlertActionByUserId(user.getId());
		String forAlert = (String) stream.fromXML(alertXml);
		String messageMail = "";//"Customer ID: "+user.getCustomer().getCustomerId();
		String messageSms = "";//"Customer ID: "+user.getCustomer().getCustomerId();
		//messageMail += ".<br>";
		messageMail += "User ID: "+user.getUserId();
		//messageMail += ".<br><br>";
		if(forAlert.equalsIgnoreCase(Constants.LOG_IN))
		{
			messageMail += " is Logged in. <br>";
			messageSms += " User ID: "+user.getUserId()+" is Logged in";
		}else if(forAlert.equalsIgnoreCase(Constants.PASSWORD_CHANGED)){
			messageMail += "Your password has changed. <br>";
			messageSms += " User ID: "+user.getUserId()+"  Your password has changed ";
		}
		
		/**
		 * KanthaRaj Changed To Notify To MainUser,Insted of Customer For System Alerts.to resolve bug-1039
		 * on 15-04-2013
		 * 
		 * String[] recipiantsBYmail = {user.getCustomer().getEmail()};
		 * String[] recipiantsBYsms = {user.getCustomer().getMobile()};*/
		 
		
		
		
		 String[] recipiantsBYsms ={user.getMobile()};
		 String[] recipiantsBYmail ={user.getEmail()};
		
		
		//1. Check if system alert has been configured or not.
		for(SystemAlertAction saa : systemAlertActions){
			String alertTypeName = saa.getSystemAlert().getName();
			String actionTypeName = saa.getActionType().getName();
			//2. Send appropriate notification if configured.
			if(alertTypeName.equalsIgnoreCase(Constants.LOG_IN) && actionTypeName.equalsIgnoreCase(Constants.SEND_EMAIL)){
				EMailNotifications eMailNotifications = new EMailNotifications();
				try {
					eMailNotifications.notify(messageMail,recipiantsBYmail, null,user.getUserId());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				 
			}
			if(alertTypeName.equalsIgnoreCase(Constants.LOG_IN) && actionTypeName.equalsIgnoreCase(Constants.SEND_SMS)){
				SmsNotifications notifications = new SmsNotifications();
				 notifications.notify(messageSms,recipiantsBYsms, null,customer);
			}
		}
	}
	//sumit end
	public String getMainUserAndSendSmsAndEmailing(String xml,String xmlMessage,String action,String devceXmlString){

		XStream xStream=new XStream();
		long id = (Long) xStream.fromXML(xml);
		String messageFromXml = (String) xStream.fromXML(xmlMessage);
		UserManager userManager = new UserManager();
		Customer customer = new Customer();
		customer.setId(id);
		SystemAlertManager systemAlertManager = new SystemAlertManager();
		String actiomxXml = (String) xStream.fromXML(action);
		SystemAlert systemAlert = null;
	
			 systemAlert = systemAlertManager.getSystemAlertByName(actiomxXml);
			 Device device = (Device) xStream.fromXML(devceXmlString);
			/* if(device == null){
				 DeviceManager deviceManager = new DeviceManager();
				 device = deviceManager .getDeviceMacIdByCustomerId(id);
			 }*/
		List<Object[]> userNotifications = userManager.getMainUserNotifications(id,Constants.MAIN_USER);
		
		if(userNotifications.size()>0){
		for(Object[] notification:userNotifications){
			String message ="";
		List<Object[]> systemAlertByUser =  systemAlertManager.getSystemAlertByUserAndAlertId(notification[4].toString(),systemAlert.getId());
				if(systemAlertByUser.size()>0 ){
					if(device !=null){
						message+=IMonitorUtil.generateMailMessage(messageFromXml,notification[3].toString(),device.getGeneratedDeviceId(),device.getGateWay().getMacId() ,notification[2].toString());
					}else{
						message+=IMonitorUtil.generateMailMessage(messageFromXml,notification[3].toString(),"","",notification[2].toString());
					}
					
					/***
					 *KanthaRaj Changed To Notify To Customer,Insted of MainUser For System Alerts. 
					 * 
					 * String[] recipiantsBYsms ={notification[1].toString()};
					 String[] recipiantsBYmail ={notification[0].toString()};*/
					
					/**
					 * KanthaRaj Changed To Notify To MainUser,Insted of Customer For System Alerts.to resolve bug-1039
					 * on 15-04-2013
					 * 
					 * String[] recipiantsBYsms ={notification[5].toString()};
					 * String[] recipiantsBYmail ={notification[6].toString()};
					 * **/
					String[] recipiantsBYsms ={notification[1].toString()};
					String[] recipiantsBYmail ={notification[0].toString()};
					 for (Object[] object : systemAlertByUser) {
						 if(object[1].equals(Constants.SEND_SMS)){
							 SmsNotifications notifications = new SmsNotifications();
							 message=IMonitorUtil.generateSmsMessage(messageFromXml,notification[3].toString(),"","",notification[2].toString());
							 notifications.notify(messageFromXml,recipiantsBYsms, null,customer);
						 }
						 if(object[1].equals(Constants.SEND_EMAIL)){
							 EMailNotifications eMailNotifications = new EMailNotifications();
							 try {
								 eMailNotifications.notify(messageFromXml,recipiantsBYmail, null,notification[3].toString());
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						 }
						 
					 }
				}
			}
		}
		return xStream.toXML("yes");
	}
	
	public String updateUserMailandMobile(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		User user=(User)xStream.fromXML(xml);
		UserManager userManager=new UserManager();
		User userFromDb = userManager.getUserById(user.getId());
		userFromDb.setEmail(user.getEmail());
		userFromDb.setMobile(user.getMobile());
		if(
			userManager.updateUser(userFromDb))
		{
			result="yes";
		}
		return result;
	}
	

	/* ****************************************** sumit start : SUB USER RESTRICTION*********************************** */
	
	/**
	 * @author sumit kumar
	 * @param subUserIdXml
	 * @return
	 */
	public String listDevicesForSubuser(String subUserIdXml){
		XStream stream=new XStream();
		long subUserId = (Long) stream.fromXML(subUserIdXml);
		UserManager userManager=new UserManager();
		return stream.toXML(userManager.listDevicesForSubuser(subUserId));
	}
	
	/**
	 * @author sumit kumar
	 * @param subUserIdXml
	 * @return
	 */
	public String listScenariosForSubuser(String subUserIdXml){
		XStream stream=new XStream();
		long subUserId = (Long) stream.fromXML(subUserIdXml);
		UserManager userManager=new UserManager();
		return stream.toXML(userManager.listScenariosForSubuser(subUserId));
	}
		
	/**
	 * @author sumit kumar
	 * @param userIdXml
	 * @param devicesXml
	 * @param scenariosXml
	 * @return
	 */
	public String customizeSubUserAccess(String userIdXml, String devicesXml, String scenariosXml){
		String result = "no";
		XStream stream = new XStream();
		long subUserId = (Long) stream.fromXML(userIdXml);
		String accesibleDeviceList = (String) stream.fromXML(devicesXml);
		String accesibleScenarioList = (String) stream.fromXML(scenariosXml);
		UserManager userManager = new UserManager();
		User user = userManager.getUserById(subUserId);
		
		//1. Save Device List for Sub User.
		DeviceManager deviceManager = new DeviceManager();
		deviceManager.updateAccessibleDevicesForSubUser(user, accesibleDeviceList);
		
		//2. Save Scenario List for Sub User.
		ScenarioManager scenarioManager = new ScenarioManager();
		boolean scenariosSaved = scenarioManager.updateAccessibleScenariosForSubUser(user, accesibleScenarioList);
		
		if(scenariosSaved) result = "yes";
		return stream.toXML(result);
	}
	
	/* ****************************************** sumit end : SUB USER RESTRICTION*********************************** */


	public String verifyMainUserExist(User user,long customer){
		String result = "validUser";
	//	LogUtil.info("---inside -MainUserExist----"+customer);
		try {
			
			
			List<User> users = new DaoManager().get("customer",customer, User.class);
			
		//	LogUtil.info("----MainUserExist----"+xStream.toXML(users));
			
			for(User userFromDb : users){
			//	LogUtil.info("----MainUserExist----"+userFromDb.getClass());
				if((userFromDb.getRole().getId() == 1) && (customer == userFromDb.getCustomer().getId())){
					result = "MainUserExist";
				
				} 
			}
		} catch (Exception e) {
			LogUtil.info("Got Exception while saving Room: ", e);
		}
		return result;
	}
	
	
	
	public String listAskedUsersfromAdmin(String xml){
		
		//LogUtil.info("hey this is UserService controller class");
		XStream xStream=new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(xml);
		UserManager userManager = new UserManager();
		int usercount = userManager.getTotalUserCountfromadmin();
		dataTable.setTotalRows(usercount);
		String sSearch = dataTable.getsSearch();
		String[] cols = { "u.userId","c.customerId","s.name","r.name"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = userManager.listAskedUserFromAdmin(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength());
		dataTable.setResults(list);
	    int displayRow = userManager.getTotalUserCount(sSearch);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		//LogUtil.info("hey this is UserService valueInXml"+valueInXml);
		return valueInXml;
	}
	
	
	public String getGatewayDetails(String xml){
		XStream stream = new XStream();
		Customer customer = (Customer) stream.fromXML(xml);
		GatewayManager gatewaymanager = new GatewayManager();
		GateWay gateway = gatewaymanager.getGateWayByCustomer(customer);
		
	/*	CustomerManager customermanager = new CustomerManager();
		String id = (String) stream.fromXML(xml); 
		List<GateWay> gateWays = customermanager.getGateWaysAndDeviceOfCustomerByCustomerId(id);*/
		//LogUtil.info("gateway details: "+ stream.toXML(gateWays));
		return stream.toXML(gateway);
	}

	
	public String saveTemporaryPassword(String userXml, String dateXml,String passwordxml, String smsXml, String emailXml) throws ParseException{
		XStream stream = new XStream();
		User user = (User) stream.fromXML(userXml);
		String message = null;
		String date = (String) stream.fromXML(dateXml);
		String sms = (String) stream.fromXML(smsXml);
		String email = (String) stream.fromXML(emailXml);
		String password = null;
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date expireDate = format.parse(date);
		UserManager userManager = new UserManager();
		User saveduser=userManager.getUserById(user.getId());
		Customer customer=user.getCustomer();
		saveduser.setTempPassword(user.getTempPassword());
		saveduser.setExpireDate(expireDate);
		boolean result = userManager.updateUser(saveduser);
		if(result){
			
			if(sms != null){
				password = (String) stream.fromXML(passwordxml);
				message = "Your temparory password is "+password+" and will expire after "+expireDate+" ";
				SmsNotifications notifications = new SmsNotifications();
				notifications.notifywithTempPassword(message,sms,customer);
			
			}
			
			if(email != null){
				String[] strArray = new String[] {email};
				password = (String) stream.fromXML(passwordxml);
				message = "Your temparory password is "+password+" and will expire after "+expireDate+"";
				 EMailNotifications eMailNotifications = new EMailNotifications();
				 try {
					 eMailNotifications.notify(message,strArray,null,"User");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
		}
		
		//LogUtil.info("gateway details: "+ stream.toXML(gateWays));
		return "Success";
	}
	
	public String getListOfUserTips (){
		
		XStream stream = new XStream();
		UserManager userManager = new UserManager();
		List<String> userTips = userManager.getUserTips();
		
	return stream.toXML(userTips);	
	}
	
	public String updateUserTipsForUser(String xml){
		
		XStream stream = new XStream();
		String result = "failure";
		User user = (User) stream.fromXML(xml);
		UserManager userManager = new UserManager();
		User userFrmDb = userManager.getUserById(user.getId());
		userFrmDb.setShowTips(1);
		boolean update = userManager.updateUser(userFrmDb);
		if(update){
			result = "success";
		}
		
		return result;
	}
	

public String toValidateAlexaUser(String userxml,String customerXml){
	
	String result = "";
	XStream stream = new XStream();
	
	String userId = (String) stream.fromXML(userxml);
	String customerId = (String) stream.fromXML(customerXml);
	
	CustomerManager customerManager = new CustomerManager();
	UserManager userManager = new UserManager();
	
	//1.Validate customerExistence for specified customerId.
	boolean customerExists = customerManager.checkCustomerExistence(customerId);
	if(customerExists){
		//If customerExists = true, means customer exists.
		//2.Validate userExistence for specified userId.
		boolean userExists = userManager.checkUserExistence(userId);
		if(userExists){
			//if userExists = true, means user doesn't exist.
			LogUtil.info("USER DOES NOT EXIST");
			return null;
		}else{
			result = "yes";
		}
	}else {
		LogUtil.info("CUSTOMER DOES NOT EXIST");
		result = "invalidcustomerid";
	}
	
	return result;
}


public String generatePasswordAndNotifyUser(String userxml,String customerxml)
{
	//Update
	
	XStream xStream=new XStream();
	String userx =  (String) xStream.fromXML(userxml);
	String customerx =  (String) xStream.fromXML(customerxml);
	
	UserManager userManager=new UserManager();
	User userToCheck =userManager.getUserByUserId(userx);
	
	
	 String  password = IMonitorUtil.generateTransactionId();
	
	 String hashPassword = IMonitorUtil.getHashPassword(password);
	
	 User user = userManager.getUserWithCustomerByUserIdAndUpdatePassword(userx,customerx,hashPassword);
	
	 //Notify
	
	 if(user != null)
	 {
		
		 
			Customer customer = userManager.getCustomerByMainUserId(user.getId());
			SystemAlertManager systemAlertManager = new SystemAlertManager();
			
			systemAlertManager.getSystemAlertActionByUserId(user.getId());
			String forAlert = "Password Changed";
			
			
			String messageMail = "";
			String messageSms = "";
			messageMail += "User ID: "+user.getUserId();
			
			
			 if(forAlert.equalsIgnoreCase(Constants.PASSWORD_CHANGED)){
				messageMail += "Your password has changed. <br>" + "passord ="+password;
				messageSms += " User ID: "+user.getUserId()+"  Your password has changed "+password;
			}
			
			 String[] recipiantsBYsms ={user.getMobile()};
			 String[] recipiantsBYmail ={user.getEmail()};
			 EMailNotifications eMailNotifications = new EMailNotifications();
			 try {
				 eMailNotifications.notify(messageMail,recipiantsBYmail, null,user.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			 SmsNotifications notifications = new SmsNotifications();
			 notifications.notify(messageSms,recipiantsBYsms, null,customer);
			
			
			 
	 }
	String resultXml=xStream.toXML(user);
	return resultXml;

}
public String listAskedUserDeviceActions(String xml, String customerIdXml){
	XStream xStream=new XStream();
	String[] items = xml.split("-");
	DataTable dataTable =  (DataTable) xStream.fromXML(items[0]);
	long id = (Long) xStream.fromXML(customerIdXml);
	UserManager userManager = new UserManager();
	String sSearch = dataTable.getsSearch();
	String[] cols = { "d.friendlyName","f.name"};
	String sOrder = "";
	int col = Integer.parseInt(dataTable.getiSortCol_0());
	  String colName = cols[col];
	  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
	List<Object[]> list = userManager.listAskedUsersDeviceActions(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),id);
	List<Object[]> Scenelist = userManager.listAskedUsersDeviceActionsWithScenario(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),id);
	dataTable.setTotalRows(userManager.getTotalActionsCountByCustomerId(id));
	list.addAll(Scenelist);
	dataTable.setResults(list);
	int displayRow = userManager.getActionCountByCustomerId(sSearch,id);
	
	dataTable.setTotalDispalyRows(displayRow);
	String valueInXml = xStream.toXML(dataTable);
	
	return valueInXml;
}

}



