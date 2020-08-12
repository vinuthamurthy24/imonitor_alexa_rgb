/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.SmsService;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ActionTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserNotificationProfileManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.SmsNotifications;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;

import java.util.List;

import com.thoughtworks.xstream.XStream;


/**
 * @author Coladi
 * 
 */
public class SystemNotificaionServiceManager {
	
	public String saveSystemNotificaion(String xml) throws Exception {
		
		XStream stream = new XStream();
		String result = "no";
		UserNotificationProfile systemNotificaion =  (UserNotificationProfile) stream.fromXML(xml);
		String sms = systemNotificaion.getSMS();
		String isDuplicate = verifyNotificationDetails(systemNotificaion);
		ServerConfigurationSevice serverConfiguration = new ServerConfigurationSevice();
		String xml1 = serverConfiguration.getSelectedSMSServiceProvider();
		SmsService smsService = new SmsService();
		smsService = (SmsService) stream.fromXML(xml1);
		if(isDuplicate.equalsIgnoreCase("duplicatenotification")){
			return result;
		}else{
		UserNotificationProfileManager systemNotificaionManager = new UserNotificationProfileManager();
		StatusServiceManager statusManager = new StatusServiceManager();
		Status status = null;
		if((!sms.equals(""))){
			
		status = statusManager.getStatusTypeByName(Constants.UN_SUBSCRIBED);
		if(smsService.getOperatorcode().equals("efl")){
		
			status = statusManager.getStatusTypeByName(Constants.SMS_SUBSCRIBED);
			systemNotificaion.setStatus(status);
			result = systemNotificaionManager.saveSystemNotificaion(systemNotificaion);
		}else{
		int otp = generateOtpNumber();
		systemNotificaion.setOTP(otp);
		systemNotificaion.setStatus(status);
		String res = systemNotificaionManager.saveSystemNotificaion(systemNotificaion);
		if(res.equalsIgnoreCase("yes")){
			String message = "OTP to enroll for alert SMS from Myhomeqi.com"+ Constants.NEW_LINE + 
					         "OTP Number: "+ otp;
			SmsNotifications smsNotify = new SmsNotifications();
			smsNotify.notifywithOTP(message, systemNotificaion.getSMS(),systemNotificaion.getCustomer());
			result="yes";
		      }
		  }
		}else{
	    status = statusManager.getStatusTypeByName(Constants.NOT_ENABLED);	
	    systemNotificaion.setStatus(status);
	    String res = systemNotificaionManager.saveSystemNotificaion(systemNotificaion);
	    result = res;
		}
		
		}
	    return result;    
	}
	 
	@SuppressWarnings("unchecked")
	public String verifyNotificationDetails(UserNotificationProfile systemNotificaion){
		String result = "validRoom";
		int abc = 0;
		try {
			List<UserNotificationProfile> notifications = new DaoManager().get("customer",systemNotificaion.getCustomer().getId(), UserNotificationProfile.class);
			
			for(UserNotificationProfile notificationFromDb : notifications){
				if(systemNotificaion.getName().equals(notificationFromDb.getName()) && (systemNotificaion.getActionType().equals(notificationFromDb.getActionType()))
						&& (systemNotificaion.getCustomer().getId() == notificationFromDb.getCustomer().getId())){
					result = "duplicatenotification";
					abc++;
				}if(systemNotificaion.getName().equals(notificationFromDb.getName()) && (systemNotificaion.getCustomer().getId() == notificationFromDb.getCustomer().getId())){
					
					abc++;
				}
				if(abc>=2){
					
					result = "duplicatenotification";
				};
			}
		} catch (Exception e) {
			LogUtil.info("Got Exception while saving Notification: ", e);
		}
		return result;
	}
	
	public String editSystemNotificaion(String xml) throws Exception {
		XStream stream = new XStream();
		StatusServiceManager statusManager = new StatusServiceManager();
		UserNotificationProfile systemNotificaion =  (UserNotificationProfile) stream.fromXML(xml);
		UserNotificationProfileManager systemNotificaionManager = new UserNotificationProfileManager();
		UserNotificationProfile notificationFromDb = systemNotificaionManager.getSystemNotificaion(systemNotificaion.getId());//Naveen Modified to generate OTP if mobile number is changed
		ServerConfigurationSevice serverConfiguration = new ServerConfigurationSevice();
		String xml1 = serverConfiguration.getSelectedSMSServiceProvider();
		SmsService smsService = new SmsService();
		smsService = (SmsService) stream.fromXML(xml1);
		String sms = systemNotificaion.getSMS();
		if(systemNotificaion.getSMS().equals(notificationFromDb.getSMS())){
			LogUtil.info("Same SMS");
			return systemNotificaionManager.updateSystemNotificaion(systemNotificaion);
		
		}
		
			Status status = statusManager.getStatusTypeByName(Constants.UN_SUBSCRIBED);
			if(smsService.getOperatorcode().equals("efl")){
				LogUtil.info("its efl customer: ");
				status = statusManager.getStatusTypeByName(Constants.SMS_SUBSCRIBED);
				systemNotificaion.setStatus(status);
				return systemNotificaionManager.saveSystemNotificaion(systemNotificaion);
			}else if((!sms.equals(""))){
			int otp = generateOtpNumber();
			systemNotificaion.setOTP(otp);
			String res = systemNotificaionManager.saveSystemNotificaion(systemNotificaion);
			if(res.equalsIgnoreCase("yes")){
				String message = "OTP to enroll for alert SMS from Myhomeqi.com"+ Constants.NEW_LINE + 
						         "OTP Number: "+ otp;
				SmsNotifications smsNotify = new SmsNotifications();
				smsNotify.notifywithOTP(message, systemNotificaion.getSMS(),systemNotificaion.getCustomer());
				return "yes";
			      }
			  }
	
			status = statusManager.getStatusTypeByName(Constants.NOT_ENABLED);	
		    systemNotificaion.setStatus(status);
		    String res = systemNotificaionManager.saveSystemNotificaion(systemNotificaion);
		    return res;
		
		
		
	}
	
	public String getSystemNotificaion(String xml) {
		XStream stream = new XStream();
		long systemNotificaionId =  (Long) stream.fromXML(xml);
		UserNotificationProfileManager systemNotificaionManager = new UserNotificationProfileManager();
		UserNotificationProfile systemNotificaion = systemNotificaionManager.getSystemNotificaion(systemNotificaionId);
		// Naveen made changes to handle old notifications type
		if(systemNotificaion.getEMail() == null && systemNotificaion.getSMS() == null)
		{
			
			//LogUtil.info(stream.toXML(systemNotificaion));
			ActionTypeManager actionTypeManager = new ActionTypeManager();
			if(actionTypeManager.getActionTypeById(systemNotificaion.getActionType().getId()).getName().equals("E-Mail"))
			{
				systemNotificaion.setEMail(systemNotificaion.getRecipient());
				systemNotificaion.setRecipient(null);
			}
			else 
			{
				systemNotificaion.setSMS(systemNotificaion.getRecipient());
				systemNotificaion.setRecipient(null);
			}
			
			
		}
		//Naveen end
		String result = stream.toXML(systemNotificaion);
		return result;
	}
	
	public String deleteSystemNotificaion(String xml) {
		XStream stream = new XStream();
		UserNotificationProfile systemNotificaion =  (UserNotificationProfile) stream.fromXML(xml);
		UserNotificationProfileManager systemNotificaionManager = new UserNotificationProfileManager();
		boolean success = systemNotificaionManager.deleteSystemNotificaion(systemNotificaion);
		if(success)
		{
		return "success";
	}
		else
			return "failure";
	}
	
	public String listAskedSystemNotificaions(String dataTableXml, String customerIdXml) {
		XStream xStream = new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(dataTableXml);
		long customerId = (Long) xStream.fromXML(customerIdXml);
		UserNotificationProfileManager systemNotificaionManager = new UserNotificationProfileManager();
		String sSearch = dataTable.getsSearch();
		//String[] cols = { "sn.name", "sn.recipient", "at.name"};
		String[] cols = { "sn.name","at.name"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = systemNotificaionManager.listSystemNotificaion(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(), customerId);
		dataTable.setTotalRows(systemNotificaionManager.getTotalSystemNotificationCount(customerId));
		dataTable.setResults(list);
		int displayRow = systemNotificaionManager.getCustomerSystemNotificationCount(sSearch,customerId);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	public String getNotificationsOfCustomer(String xml){
		XStream stream = new XStream();
		Customer customer = (Customer) stream.fromXML(xml);
		UserNotificationProfileManager systemNotificaionManager = new UserNotificationProfileManager();
		List<UserNotificationProfile> systemNotificaions = systemNotificaionManager.getSystemNotificaions(customer);
		String result = stream.toXML(systemNotificaions);
		return result;
	}
	
	public int generateOtpNumber(){
		
		int otp = 0000;
		//generate a 4 digit integer 1000 <10000
		otp = (int)(Math.random()*9000)+1000; 
		return otp;
		
	}
	
	public String toVerfifyMobile(String xml){
		
		XStream stream = new XStream();
		long id = (Long) stream.fromXML(xml);
		UserNotificationProfileManager notificationManager = new UserNotificationProfileManager();
		UserNotificationProfile notification = notificationManager.getSystemNotificaion(id);
		return stream.toXML(notification);
	}
	
	public String VerfifyAndUpdateNumber(String xml){
		
		XStream stream = new XStream();
		long id = (Long) stream.fromXML(xml);
		UserNotificationProfileManager notificationManager = new UserNotificationProfileManager();
		UserNotificationProfile notifyProfile = notificationManager.getSystemNotificaion(id);
		StatusServiceManager statusManager = new StatusServiceManager();
		Status status = statusManager.getStatusTypeByName(Constants.SMS_SUBSCRIBED);
		notifyProfile.setStatus(status);
		String update = notificationManager.updateSystemNotificaion(notifyProfile);
		return update;
		
	}
	
	//Mobile Self Registration changes start
	public int generateOtpNumberSixDigits(){
			LogUtil.info("generateOtpNumberSixDigits");
			int otp = 000000;
			//generate a 6 digit integer 1000 <10000
			otp = (int)(Math.random()*9000)+100000; 
			LogUtil.info("otp:"+otp);
			return otp;
			
		}
		//end
		
}
