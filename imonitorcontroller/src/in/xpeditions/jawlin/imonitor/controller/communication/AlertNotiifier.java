/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.communication;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ImvgAlertNotificationManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.EMailNotifications;
import in.xpeditions.jawlin.imonitor.controller.notifications.SmsNotifications;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 * 
 */
public class AlertNotiifier implements Runnable {

	private Device device;
	private String referenceTransactionId;
	private Rule   referenceRule;
	private boolean isForImageUploadSuccess = false;
	//private String referenceTimeStamp;
	private Date date;

	public AlertNotiifier(Device device, Date date) {
		this.device = device;
		this.date = date;
		//this.referenceTimeStamp = referenceTimeStamp;
	}
	//sumit: Currently we are considering ruleId and ReferenceTime Stamp to notify Alerts for IMAGE CAPTURE.
	public AlertNotiifier(Device device, String transactionId, Rule rule, Date date){
		this.device = device;
		this.referenceTransactionId = transactionId;
		this.referenceRule = rule;
		this.date = date;
		isForImageUploadSuccess = true;
	}

	public AlertNotiifier(Device device, Date date, Rule rule) {
		this.device = device;
		this.date = date;
		this.referenceRule=rule;
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings("unused")
	@Override
	public void run() {
		List<ImvgAlertNotification> userNotificationProfiles = null;
		SmsNotifications smsNotifications = new SmsNotifications();
		if(isForImageUploadSuccess)
		{
			XStream stream = new XStream();
			userNotificationProfiles = new ArrayList<ImvgAlertNotification>(referenceRule.getImvgAlertNotifications());
            	
		}
		else
		{
			ImvgAlertNotificationManager alertNotificationManager = new ImvgAlertNotificationManager();
			userNotificationProfiles = alertNotificationManager.listAllAlertNotificationOfLastAlert(this.device,referenceRule.getId());
		}
		
		
		
		for (ImvgAlertNotification imvgAlertNotification : userNotificationProfiles) 
		{
			
			XStream stream = new XStream();
			UserNotificationProfile userNotificationProfile = imvgAlertNotification.getUserNotificationProfile();
			String notifiCheck = imvgAlertNotification.getNotificationCheck();
			
			String notificationName = userNotificationProfile.getActionType()
					.getName();
			String[] emails = new String[] {userNotificationProfile.getEMail()};
		//	LogUtil.info("emails to send: "+ stream.toXML(emails));
			String[] receipients=null;
			
			if(userNotificationProfile.getRecipient() != null)
			receipients = new String[] { userNotificationProfile.getRecipient() };
			String[] sms = new String[] {userNotificationProfile.getSMS()};
			String[] whatsApp = new String[]{userNotificationProfile.getCountryCode()+userNotificationProfile.getWhatsApp()};
			
			Status status = userNotificationProfile.getStatus();
			Rule rule = null;
			
			/*if(notificationName.equalsIgnoreCase("EmailAndSMS")){
				rule = imvgAlertNotification.getRule();
				String messageToRecipient = "Rule executed. ";
				messageToRecipient += "Name: '"+rule.getName()+"'. Description: ";
				messageToRecipient += rule.getDescription()+".\n";
				EMailNotifications eMailNotifications = new EMailNotifications();
				eMailNotifications.notifyforschedule(messageToRecipient, emails, date, userNotificationProfile.getName());
				
			}else{*/
			
			
			if(isForImageUploadSuccess)
			{
				rule = this.referenceRule;
			}
			else
			{
			    rule = imvgAlertNotification.getRule();
			}
			
			String messageToRecipient = "Rule executed. ";
			messageToRecipient += "Name: '"+rule.getName()+"'. Description: ";
			messageToRecipient += rule.getDescription()+".\n";
			
			// Naveen changed to shorten the rule message
			String RuleDescription = rule.getDescription();
 			//String interval = ControllerProperties.getProperties().get(Constants.ATTACH_UPLOADED_FILE_INTERVAL);
			//LogUtil.info("t1-interval: "+interval);
			long deviceId = this.device.getId();
			//long timeOut = Long.parseLong(interval);
			//long timeOut = 10000L;
			if(isForImageUploadSuccess)
			{
				
				try{
					String referenceTransactionId = this.referenceTransactionId;
					if(notificationName.equalsIgnoreCase("E-Mail") || notificationName.equalsIgnoreCase("EmailAndSMS"))
					{
						
						//1a.Wait for the configured time.
						//IMonitorUtil.waitForImageUploadTimeOut(timeOut);
//						Need not sleep
//						try{
//							Thread.sleep(timeOut);
//						}catch (InterruptedException e) {
//							LogUtil.error(e.getMessage());
//						}
						
						//1b.Get the filePath using query.
						ImvgAlertNotificationManager alertNotificationManager1 = new ImvgAlertNotificationManager();
						
						String filePath = null;
						try {
							//filePath = alertNotificationManager1.getFilePathForAttachment(referenceTransactionId, deviceId, interval);
							//filePath = alertNotificationManager1.getFilePathForAttachment(referenceTransactionId, deviceId, timeOut + 5000L);
							filePath = alertNotificationManager1.getFilePathForAttachment(referenceTransactionId, deviceId);
						} catch (Exception e) {
							LogUtil.error("error:"+e.getMessage());
						}

						//1c.If filePath is not empty, then send mail with attachment.
						if((filePath != null) && (!filePath.isEmpty())){
							
							try {
								EMailNotifications email = new EMailNotifications();
								if (notificationName.equalsIgnoreCase("EmailAndSMS") || (receipients == null && notificationName.equalsIgnoreCase("E-Mail"))){
								   if(notifiCheck.equals("EMail") || notifiCheck.equals("EmailAndSMS")){
									   
									email.notifyWithAttchment(RuleDescription, emails , filePath, this.date,userNotificationProfile.getName(),rule.getGateWay().getAgent());
								   }
								   }else{
									    
									email.notifyWithAttchment(RuleDescription, receipients, filePath, this.date,userNotificationProfile.getName(),rule.getGateWay().getAgent());
									   
									   }
								} catch (IllegalArgumentException e) {
								LogUtil.error(e.getMessage());
								e.printStackTrace();
							}
						}
				}
				}catch (IllegalArgumentException e) {
					LogUtil.error("Error when notifying. " + notificationName + ","
							+ rule.getId() + "," + rule.getDescription());
				}
			}else{
				
				try 
				{
					
					
					
					if(receipients == null && notificationName.equalsIgnoreCase("E-Mail")){
						
						IMonitorUtil.notifyRecipients(notificationName, messageToRecipient, emails, date, userNotificationProfile.getName(),userNotificationProfile.getCustomer());
						
						
					}else if(receipients == null && notificationName.equalsIgnoreCase("SMS")){
						
							if(status.getName().equals(Constants.SMS_SUBSCRIBED)){
						IMonitorUtil.notifyRecipients(notificationName, RuleDescription, sms, date, userNotificationProfile.getName(),userNotificationProfile.getCustomer());
							}
						
						
						
					}else if(receipients == null && notificationName.equalsIgnoreCase("EmailAndSMS")){
						
						
						String smsname = "SMS";
						String emailname = "E-Mail";
						if(notifiCheck.equals("EMail")){
							IMonitorUtil.notifyRecipients(emailname, RuleDescription, emails, date, userNotificationProfile.getName(),userNotificationProfile.getCustomer());
							
						}else if(notifiCheck.equals("SMS")){
							//LogUtil.info("inside sms function");
							if(status.getName().equals(Constants.SMS_SUBSCRIBED)){
							IMonitorUtil.notifyRecipients(smsname, RuleDescription, sms, date, userNotificationProfile.getName(),userNotificationProfile.getCustomer());
							}
							
						}else if(notifiCheck.equals("EmailAndSMS")){
							
							IMonitorUtil.notifyRecipients(emailname, RuleDescription, emails, date, userNotificationProfile.getName(),userNotificationProfile.getCustomer());
						  if(status.getName().equals(Constants.SMS_SUBSCRIBED)){
							IMonitorUtil.notifyRecipients(smsname, RuleDescription, sms, date, userNotificationProfile.getName(),userNotificationProfile.getCustomer());
						  }
						   
						}
						
						
						
					}else{
					
						
					IMonitorUtil.notifyRecipients(notificationName, RuleDescription, receipients, date, userNotificationProfile.getName(),userNotificationProfile.getCustomer());
					
					}
				} catch (IllegalArgumentException e) {
					LogUtil.error("1.Error when notifying. " + notificationName + ","
							+ rule.getId() + "," + rule.getDescription());
				} catch (IllegalAccessException e) {
					LogUtil.error("2.Error when notifying. " + notificationName + ","
							+ rule.getId() + "," + rule.getDescription());
				} catch (InvocationTargetException e) {
					LogUtil.error("3.Error when notifying. " + notificationName + ","
							+ rule.getId() + "," + rule.getDescription());
				} catch (InstantiationException e) {
					LogUtil.error("4.Error when notifying. " + notificationName + ","
							+ rule.getId() + "," + rule.getDescription());
				}
			}
			
		//}
		}
	}
	
/*	
 * *********************************************************** ORIGINAL CODE ********************************************************************
 * @Override
	public void run() {
		// Select the user notification profiles, which is configured for the
		// device and alert.
		ImvgAlertNotificationManager alertNotificationManager = new ImvgAlertNotificationManager();
		List<ImvgAlertNotification> userNotificationProfiles = alertNotificationManager
				.listAllAlertNotificationOfLastAlert(this.device);
		for (ImvgAlertNotification imvgAlertNotification : userNotificationProfiles) {
			UserNotificationProfile userNotificationProfile = imvgAlertNotification
					.getUserNotificationProfile();
			String notificationName = userNotificationProfile.getActionType()
					.getName();
			String[] receipients = new String[] { userNotificationProfile
					.getRecipient() };
			Rule rule = imvgAlertNotification.getRule();
			//String messageToRecipient = "Dear Customer,\n";
			//messageToRecipient += "A rule with name '"+rule.getName()+"' was triggered from your account.";
			//messageToRecipient += "\nDescription for the rule: ";
			String messageToRecipient = "Rule executed. ";
			messageToRecipient += "Name: '"+rule.getName()+"'. Description: ";
			messageToRecipient += rule.getDescription()+".\n";
			try {
//				IMonitorUtil.notifyRecipients(notificationName, rule
//						.getDescription(), receipients);
				IMonitorUtil.notifyRecipients(notificationName, messageToRecipient, receipients);
			} catch (IllegalArgumentException e) {
				LogUtil.error("Error when notifying. " + notificationName + ","
						+ rule.getId() + "," + rule.getDescription());
			} catch (IllegalAccessException e) {
				LogUtil.error("Error when notifying. " + notificationName + ","
						+ rule.getId() + "," + rule.getDescription());
			} catch (InvocationTargetException e) {
				LogUtil.error("Error when notifying. " + notificationName + ","
						+ rule.getId() + "," + rule.getDescription());
			} catch (InstantiationException e) {
				LogUtil.error("Error when notifying. " + notificationName + ","
						+ rule.getId() + "," + rule.getDescription());
			}
		}
	} */

}
