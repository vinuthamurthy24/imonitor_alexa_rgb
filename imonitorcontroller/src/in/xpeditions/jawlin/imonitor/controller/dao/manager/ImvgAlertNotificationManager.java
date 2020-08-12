/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotificationForScedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

public class ImvgAlertNotificationManager {

	DaoManager daoManager = new DaoManager();

	public boolean save(ImvgAlertNotification imvgAlertNotification) {
		boolean result = false;
		try {
			daoManager.save(imvgAlertNotification);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//*************************************************** sumit start: get filePath for FTP-UploadedImage ****************************************
	//public String getFilePathForAttachment(String transactionId, long deviceId, long interval){
	public String getFilePathForAttachment(String transactionId, long deviceId){
		String imageFilePath = null;
		//long intervalInSeconds = 1000L;
		
		String query = "select u.filePath from uploadsbyimvg u, alertsfromimvg a"
						+" where u.filePath like '%"+ transactionId +"%'"
						+" and a.id=u.alertsFromImvg"
						+" and a.device="+deviceId;
						//Currrently we are not using the this condition. Might be used in future.
						//+" and a.alertTime > SUBDATE(NOW(),INTERVAL "+(interval/intervalInSeconds)+" SECOND)";
		DBConnection dbc = null;
		
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			imageFilePath = (String)q.uniqueResult();
		} catch (Exception e) {
			LogUtil.error("ERROR :"+e.getMessage());
			e.printStackTrace();
		}finally {
			dbc.closeConnection();
		}
		return imageFilePath;
	}
	// *************************************************************** sumit end *****************************************************************
	@SuppressWarnings("unchecked")
	public List<ImvgAlertNotification> listAllAlertNotificationOfLastAlert(
			Device device, long id) {
		String query = "select ian.id,ian.NotificationCheck, ian.WhatsApp, u.id, u.name, u.recipient, u.EMail, u.SMS,"
				+ " at.id, at.name, at.command, at.details,"
				+ " r.id, r.name, r.description,u.countryCode,u.whatsApp, c.id, s.id,s.name"
				+ " from ImvgAlertNotification as ian"
				+ " left join ian.userNotificationProfile as u"
				+ " left join u.customer as c"
				+ " left join u.status as s"
				+ " left join u.actionType as at" + " left join ian.rule as r"
				+ " left join r.deviceAlerts as da" + " where da.device=" + device.getId()
				+ " and da.alertType=" + device.getLastAlert().getId()
				+ " and r.id="+id;

		List<Object[]> results = null;
		DBConnection dbc = null;
		List<ImvgAlertNotification> imvgAlertNotifications = new ArrayList<ImvgAlertNotification>();
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			results = q.list();
			for (Object[] objects : results) {
				
				Long ianId = (Long) objects[0];
				String notifycheck = (String) objects[1];
				Long wsApp = (Long) objects[2];
				ImvgAlertNotification imvgAlertNotification = new ImvgAlertNotification();
				imvgAlertNotification.setId(ianId);
				imvgAlertNotification.setNotificationCheck(notifycheck);
				imvgAlertNotification.setWhatsApp(wsApp);

				Long uId = (Long) objects[3];
				String uName = (String) objects[4];
				String uReceipient=null;
				if((String) objects[4]!=null)
				{
					uReceipient = (String) objects[5];
				}
				else
				{
					
					LogUtil.info("GOT NULL OBJECTSSS");
					
				}
				String uEmail = (String) objects[6];
				String uSMS = (String) objects[7];
				String countryCode = (String)objects[15];
				String whatsApp = (String)objects[16];
				Long cId = (Long) objects[17];
				Long sId = (Long) objects[18];
				String sName = (String)objects[19];
				Status status = new Status();
				status.setId(sId);
				status.setName(sName);
				Customer customer = new Customer();
				customer.setId(cId);
				UserNotificationProfile notificationProfile = new UserNotificationProfile();
				notificationProfile.setId(uId);
				notificationProfile.setName(uName);
				notificationProfile.setRecipient(uReceipient);
				notificationProfile.setEMail(uEmail);
				notificationProfile.setSMS(uSMS);
				notificationProfile.setCountryCode(countryCode);
				notificationProfile.setWhatsApp(whatsApp);
				notificationProfile.setCustomer(customer);
				notificationProfile.setStatus(status);

				Long atId = (Long) objects[8];
				String atName = (String) objects[9];
				String atCommand = (String) objects[10];
				String atDetails = (String) objects[11];
				ActionType actionType = new ActionType();
				actionType.setId(atId);
				actionType.setName(atName);
				actionType.setCommand(atCommand);
				actionType.setDetails(atDetails);
				notificationProfile.setActionType(actionType);
				imvgAlertNotification.setUserNotificationProfile(notificationProfile);
				
				Long rId = (Long) objects[12];
				String rName = (String) objects[13];
				String rDetails = (String) objects[14];
				Rule rule = new Rule();
				rule.setId(rId);
				rule.setName(rName);
				rule.setDescription(rDetails);
				imvgAlertNotification.setRule(rule);

				imvgAlertNotifications.add(imvgAlertNotification);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return imvgAlertNotifications;
	}
	
	
	
	
	
	
	

	@SuppressWarnings("unchecked")
	public List<ImvgAlertNotificationForScedule> listAllAlertNotificationForSchedule(
			long id) {
		String query = "select ian.id, ian.NotificationCheck, ian.WhatsApp, u.id, u.name, u.recipient, u.EMail, u.SMS,"
		+ " at.id, at.name, at.command, at.details,"
				+ " s.id, s.name, s.description, u.countryCode,u.whatsApp,c.id,st.id,st.name"
				+ " from ImvgAlertNotificationForScedule as ian"
				+ " left join ian.userNotificationProfile as u"
				+ " left join u.customer as c"
				+ " left join u.status as st"
				+ " left join u.actionType as at" + " left join ian.schedule as s"
				+ " where s.id="+id;

		List<Object[]> results = null;
		DBConnection dbc = null;
		List<ImvgAlertNotificationForScedule> imvgAlertNotifications = new ArrayList<ImvgAlertNotificationForScedule>();
		try {
			
			dbc = new DBConnection();		
			Session session = dbc.openConnection();			
			Query q = session.createQuery(query);			
			results = q.list();			
			for (Object[] objects : results) {
			//	XStream stream = new XStream();
				
				Long ianId = (Long) objects[0];
				String notificationCheck = (String) objects[1];
				Long waValue = (Long) objects[2];
				ImvgAlertNotificationForScedule imvgAlertNotification = new ImvgAlertNotificationForScedule();
				imvgAlertNotification.setId(ianId);
				imvgAlertNotification.setNotificationCheck(notificationCheck);
				imvgAlertNotification.setWhatsApp(waValue);
				Long uId = (Long) objects[3];
				String uName = (String) objects[4];
				String uReceipient = (String) objects[5];
				String uEmail = (String) objects[6];
				String uSMS = (String) objects[7];
				String countryCode = (String)objects[15];
				String whatsApp = (String)objects[16];
				Long cId = (Long) objects[17];
				Long sId = (Long) objects[18];
				String sName = (String) objects[19];
				Customer customer = new Customer();
				customer.setId(cId);
				Status status = new Status();
				status.setId(sId);
				status.setName(sName);
				UserNotificationProfile notificationProfile = new UserNotificationProfile();
				notificationProfile.setId(uId);
				notificationProfile.setName(uName);
				notificationProfile.setRecipient(uReceipient);
				notificationProfile.setEMail(uEmail);
				notificationProfile.setSMS(uSMS);
				notificationProfile.setCountryCode(countryCode);
				notificationProfile.setWhatsApp(whatsApp);
				notificationProfile.setCustomer(customer);
				notificationProfile.setStatus(status);

				Long atId = (Long) objects[8];
				String atName = (String) objects[9];
				String atCommand = (String) objects[10];
				String atDetails = (String) objects[11];
				ActionType actionType = new ActionType();
				actionType.setId(atId);
				actionType.setName(atName);
				actionType.setCommand(atCommand);
				actionType.setDetails(atDetails);
				notificationProfile.setActionType(actionType);
				imvgAlertNotification.setUserNotificationProfile(notificationProfile);
				
				Long rId = (Long) objects[12];
				String rName = (String) objects[13];
				String rDetails = (String) objects[14];
				Schedule schedule = new Schedule();
				schedule.setId(rId);
				schedule.setName(rName);
				schedule.setDescription(rDetails);
				imvgAlertNotification.setSchedule(schedule);

				imvgAlertNotifications.add(imvgAlertNotification);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
		return imvgAlertNotifications;
	}
}
