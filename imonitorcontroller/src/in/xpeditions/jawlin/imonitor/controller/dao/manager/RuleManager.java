/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertsAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgSecurityActions;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 * 
 */

public class RuleManager {


	
	// ************************************************************ sumit start *********************************************************
	public long getRuleCountByGeneratedDeviceId(long Id,String generatedDeviceId){
		//Query for criteria device
		String query1 = "select count(dr.ruleId)"
				+ " from device as d, device_rules as dr"
				+ " where dr.deviceId=d.id and d.id="+Id+" and d.generatedDeviceId='"+generatedDeviceId+"'";
		//Query for action device
		String query2 = "select count(iaa.rule)"
				+ " from device as d, imvgalertaction as iaa"
				+ " where iaa.device=d.id and d.id="+Id+" and d.generatedDeviceId='"+generatedDeviceId+"'";
		long ruleCount = 0;
		long criteriaCount = 0;	//count of rule if criteria device
		long actionCount = 0;	//count of rule if action device
		
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			
			Query q1 = session.createSQLQuery(query1);
			String c1 = q1.uniqueResult().toString();
			criteriaCount = Long.parseLong(c1);
			
			Query q2 = session.createSQLQuery(query2);
			String c2 = q2.uniqueResult().toString();
			actionCount = Long.parseLong(c2);
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error("Error while getting rule count for device.");
		}finally{
			dbc.closeConnection();
		}
		ruleCount = criteriaCount + actionCount;
		return ruleCount;

		
	}
	// ************************************************************* sumit end ******************************************************

	public int getRuleCountByCustomer(String sSearch, long customerId) {
		String query = "select count(r)"
			+ " from Rule as r"
			+ " left join r.gateWay as g"
			+ " left join g.customer as c"
//vibhu start			
//			+ " where c.id=" + customerId + " and (r.name like '%"+sSearch+"%' or r.description like '%"+sSearch+"%' or g.macId like '%"+sSearch+"%')";
	    	+ " where c.id=" + customerId + " and (r.name like '%"+sSearch+"%' or r.description like '%"+sSearch+"%')";
//vibhu end		
		Long count = new Long(0);
		DBConnection dbc = null;
        try {
        	dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			count = (Long) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != dbc) {
                dbc.closeConnection();
            }
        }
        return count.intValue();
	}

	public int getTotalRuleCountByCustomer(long customerId) {
		String query = "select count(r)"
			+ " from Rule as r"
			+ " left join r.gateWay as g"
			+ " left join g.customer as c"
			+ " where c.id=" + customerId;
		Long count = new Long(0);
		DBConnection dbc = null;
        try {
        	dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			count = (Long) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != dbc) {
                dbc.closeConnection();
            }
        }
        return count.intValue();
	}

	public List<?> listAskedRules(String sSearch, String sOrder, int start,
			int length, long customerId) {
		/*
		 * DaoManager daoManager=new DaoManager(); String query = ""; query +=
		 * "select r.id,r.name,r.description from " +
		 * "Rule as r join r.customer as c " + "where r.customer=" + id +
		 * " and r.name like '%"+sSearch+"%' "+sOrder;
		 * 
		 * List<?> list =
		 * daoManager.listAskedObjects(query,start,length,Rule.class); return
		 * list;
		 */
		String query = "select r.id, r.name, r.description,"
				+ " g.macId"
				+ " from Rule as r"
				+ " left join r.gateWay as g"
				+ " left join g.customer as c"
				//vibhu start
				//+ " where c.id=" + customerId + " and (r.name like '%"+sSearch+"%' or r.description like '%"+sSearch+"%' or g.macId like '%"+sSearch+"%') " + sOrder;
				+ " where c.id=" + customerId + " and (r.name like '%"+sSearch+"%' or r.description like '%"+sSearch+"%') " + sOrder;
				//vibhu end
		List<?> detailsList = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			q.setMaxResults(length);
			q.setFirstResult(start);
			detailsList = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return detailsList;
	}


	public boolean saveRuleWithAllDetails(Rule rule) {
		DBConnection db = null;
		boolean result = false;
		Transaction tx = null;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Set<DeviceAlert> deviceAlerts = rule.getDeviceAlerts();
			
			
			tx = session.beginTransaction();
			session.save(rule);
			for (DeviceAlert deviceAlert : deviceAlerts) {
				Device device = deviceAlert.getDevice();
				device = (Device) session.get(Device.class, device.getId());
				device.getRules().add(rule);
				session.update(device);
			}
			tx.commit();
			result = true;
		} catch (Exception ex) {
			//vibhu start
			if(tx != null)
			{
				tx.rollback();
			}
			//vibhu end
			
			LogUtil.error("Error when savign the rule with name : " + rule.getName() + " message is " + ex.getMessage());
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateRuleWithAllDetails(Rule rule) {
		DBConnection db = null;
		boolean result = false;
//		1. Get the devices which is having the rule.
		long ruleId = rule.getId();
		String query = "select d from Device as d" +
				" left join d.rules as r" +
				" where r.id=" + ruleId;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Query nq = session.createQuery(query);
			List<Device> devices = nq.list();
			Transaction tx = session.beginTransaction();
			for (Device device : devices) {
				List<Rule> rules = device.getRules();
				for (Rule rule1 : rules) {
					if(rule1.getId() == ruleId){
						device.getRules().remove(rule1);
						break;
					}
				}
				session.update(device);
			}
			tx.commit();
			db.closeConnection();
//			Updating the rule. Creating new session to delete it.
			db = new DBConnection();
			db.openConnection();
			session = db.getSession();
			tx = session.beginTransaction();
			session.update(rule);
			Set<DeviceAlert> deviceAlerts = rule.getDeviceAlerts();
			for (DeviceAlert deviceAlert : deviceAlerts) {
				Device device = deviceAlert.getDevice();
				device = (Device) session.get(Device.class, device.getId());
				device.getRules().add(rule);
				session.update(device);
			}
			tx.commit();
			result = true;
			//vibhu intentionally putting this block after commit. TBD Find a better way
			{
				DaoManager dm = new DaoManager();
				dm.executeHQLUpdateQuery("delete from DeviceAlert where rule is NULL");
				dm.executeHQLUpdateQuery("delete from ImvgAlertsAction where rule is NULL");
				dm.executeSQLQuery("delete from device_rules where ruleId is NULL");
			}
		} catch (Exception ex) {
			LogUtil.error("Error When updating the rule with id : " + ruleId + " and the message is : " + ex.getMessage());
		} finally {
			db.closeConnection();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteRuleWithAllDetails(Rule rule) {
		DBConnection db = null;
		boolean result = false;
//		1. Get the devices which is having the rule.
		long ruleId = rule.getId();
		String query = "select d from Device as d" +
				" left join d.rules as r" +
				" where r.id=" + ruleId;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Query nq = session.createQuery(query);
			List<Device> devices = nq.list();
			Transaction tx = session.beginTransaction();
			for (Device device : devices) {
				List<Rule> rules = device.getRules();
				for (Rule rule1 : rules) {
					if(rule1.getId() == ruleId){
						device.getRules().remove(rule1);
						break;
					}
				}
				session.update(device);
			}
			tx.commit();
			db.closeConnection();
//			Deleting the rule. Creating new session to delete it.
			db = new DBConnection();
			db.openConnection();
			session = db.getSession();
			tx = session.beginTransaction();
			session.delete(rule);
			tx.commit();
			result = true;
			//vibhu intentionally putting this block after commit. TBD Find a better way
			{
				DaoManager dm = new DaoManager();
				dm.executeHQLUpdateQuery("delete from DeviceAlert where rule is NULL");
				dm.executeHQLUpdateQuery("delete from ImvgAlertsAction where rule is NULL");
				dm.executeSQLQuery("delete from device_rules where ruleId is NULL");
			}
			
		} catch (Exception ex) {
			LogUtil.error("Error while removing Rules with id : " + rule.getId() + " message is " + ex.getMessage());
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Rule retrieveRuleDetails(long id) {
		Rule rule = null;
		String query = "select r.id, r.name, r.description,"
				+ " g.id, g.macId,"
				+ " iaa.id, iaa.level,"
				+ " d.id, d.generatedDeviceId, d.friendlyName,"
				+ " at.id, at.name, at.command,"
				+ " da.id, dd.id, dd.generatedDeviceId, dd.friendlyName,"
				+ " dat.id, dat.name, dat.alertCommand,"
				+ " a.id, a.ip, a.controllerReceiverPort, a.ftpIp, a.ftpPort, a.ftpNonSecurePort, a.ftpUserName, a.ftpPassword,"
				//+ " c.customerId" + ",r.delay,da.startTime,da.endTime,iaa.afterDelay" + " from Rule as r"
				//+ " c.customerId" + ",r.delay,da.startTime,da.endTime,iaa.afterDelay, da.comparatorName" + " from Rule as r" //sumit: ZXT120
				+ " c.customerId" + ",r.delay,da.startTime,da.endTime,iaa.afterDelay, da.comparatorName, r.mode, r.alert, g.currentMode, r.security " + " from Rule as r" //sumit: ZXT120 //vibhu added mode(34), g.currentMode (35
				+ " left join r.gateWay as g" + " left join g.customer as c"
				+ " left join r.imvgAlertsActions as iaa"
				+ " left join iaa.device as d"
				+ " left join iaa.actionType as at"
				+ " left join r.deviceAlerts as da"
				+ " left join da.device as dd"
				+ " left join da.alertType as dat" + " left join g.agent as a"
				+ " where r.id=" + id+" order by iaa.id asc";

		List<Object[]> detailsArray = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			detailsArray = q.list();
			boolean isFirst = true;
			rule = new Rule();
			for (Object[] details : detailsArray) {
				if (isFirst) {
					Long ruleId = (Long) details[0];
					String ruleName = (String) details[1];
					String ruleDescription = (String) details[2];
					String ruleDelay=(String)details[29];
					byte ruleMode = (Byte) details[34];//vibhu mode
					int alert=(Integer)details[35];
					String currentMode = ""+details[36];//vibhu added
					int security = (Integer)details[37];
					Long gateWayId = (Long) details[3];
					String gateWayMacId = (String) details[4];
					Long agentId = (Long) details[20];
					String agentIp = (String) details[21];
					int aPort = (Integer) details[22];
					String ftpIp = (String) details[23];
					int ftpPort = (Integer) details[24];
					int ftpNonSecPort = (Integer) details[25];
					String ftpUserName = (String) details[26];
					String ftpPassword = (String) details[27];
					String customerId = (String) details[28];

					Agent agent = new Agent();
					agent.setId(agentId);
					agent.setIp(agentIp);
					agent.setControllerReceiverPort(aPort);
					agent.setFtpIp(ftpIp);
					agent.setFtpPort(ftpPort);
					agent.setFtpNonSecurePort(ftpNonSecPort);
					agent.setFtpUserName(ftpUserName);
					agent.setFtpPassword(ftpPassword);
					rule.setId(ruleId);
					rule.setName(ruleName);
					rule.setDescription(ruleDescription);
					rule.setDelay(ruleDelay);
					rule.setMode(ruleMode); //vibhu added
					rule.setAlert(alert);
					rule.setSecurity(security);
					GateWay gateWay = new GateWay();
					gateWay.setId(gateWayId);
					gateWay.setMacId(gateWayMacId);
					gateWay.setAgent(agent);
					gateWay.setCurrentMode(currentMode);//vibhu added
					Customer customer = new Customer();
					customer.setCustomerId(customerId);
					gateWay.setCustomer(customer);
					rule.setGateWay(gateWay);
					//Set<ImvgAlertsAction> imvgAlertsActions = new HashSet<ImvgAlertsAction>();
										Set<ImvgAlertsAction> imvgAlertsActions = new LinkedHashSet<ImvgAlertsAction>(15);
					rule.setImvgAlertsActions(imvgAlertsActions);
					Set<DeviceAlert> deviceAlerts = new HashSet<DeviceAlert>();
					rule.setDeviceAlerts(deviceAlerts);
				}
				isFirst = false;
				Long imvgAlertActionId = (Long) details[5];
				if (imvgAlertActionId != null) {
					Set<ImvgAlertsAction> imvgAlertsActions = rule
							.getImvgAlertsActions();
					ImvgAlertsAction imvgAlertsAction = null;
					for (ImvgAlertsAction imvgAlertsAction1 : imvgAlertsActions) {
						if (imvgAlertsAction1.getId() == imvgAlertActionId) {
							imvgAlertsAction = imvgAlertsAction1;
							break;
						}
					}
					if (imvgAlertsAction == null) {
						imvgAlertsAction = new ImvgAlertsAction();
						imvgAlertsAction.setId(imvgAlertActionId);
						String imvgActionLevel = (String) details[6];
						int afterDelay=(Integer) details[32];
						imvgAlertsAction.setLevel(imvgActionLevel);
						imvgAlertsAction.setAfterDelay(afterDelay);
						Long deviceId = (Long) details[7];
						String generatedDeviceId = (String) details[8];
						String friendlyName = (String) details[9];
						Long actionTypeId = (Long) details[10];
						String actionTypeName = (String) details[11];
						String actionTypeCommand = (String) details[12];

						Device device = new Device();
						device.setId(deviceId);
						device.setGeneratedDeviceId(generatedDeviceId);
						device.setFriendlyName(friendlyName);
						imvgAlertsAction.setDevice(device);

						ActionType actionType = new ActionType();
						actionType.setId(actionTypeId);
						actionType.setName(actionTypeName);
						actionType.setCommand(actionTypeCommand);
						imvgAlertsAction.setActionType(actionType);

						imvgAlertsActions.add(imvgAlertsAction);
					}
				}

				Long deviceAlertId = (Long) details[13];
				Set<DeviceAlert> deviceAlerts = rule.getDeviceAlerts();
				if (deviceAlertId != null) {
					DeviceAlert deviceAlert = null;
					for (DeviceAlert deviceAlert1 : deviceAlerts) {
						if (deviceAlert1.getId() == deviceAlertId) {
							deviceAlert = deviceAlert1;
							break;
						}
					}
					if (deviceAlert == null) {
						deviceAlert = new DeviceAlert();
						deviceAlert.setId(deviceAlertId);
						long Start=(Long) details[30];
						long End=(Long) details[31];
						Long deviceId = (Long) details[14];
						String generatedDeviceId = (String) details[15];
						String friendlyName = (String) details[16];
						Long alertTypeId = (Long) details[17];
						String alertTypeName = (String) details[18];
						String alertTypeCommand = (String) details[19];

						Device device = new Device();
						device.setId(deviceId);
						device.setGeneratedDeviceId(generatedDeviceId);
						device.setFriendlyName(friendlyName);
						deviceAlert.setDevice(device);
						deviceAlert.setStartTime(Start);
						deviceAlert.setEndTime(End);
						AlertType alertType = new AlertType();
						alertType.setId(alertTypeId);
						alertType.setAlertCommand(alertTypeCommand);
						alertType.setName(alertTypeName);
						deviceAlert.setAlertType(alertType);
						
						String comparatorName = (String) details[33];	//sumit: comparatorName holds device Specific Alert Parameters for ZXT120
						deviceAlert.setComparatorName(comparatorName);
						deviceAlerts.add(deviceAlert);
					}
				}
			}
//			Retrieving user notification profile.
			query = "select ian.id, ian.NotificationCheck, ian.WhatsApp, u.id, u.name, u.recipient, u.EMail, u.SMS," +
			" at.id, at.name, at.command, at.details" +
			" from ImvgAlertNotification as ian" +
			" left join ian.userNotificationProfile as u" +
			" left join u.actionType as at" +
			" left join ian.rule as r" +
			" where r.id=" + id;
			q = session.createQuery(query);
			List<Object[]> results = q.list();
			Set<ImvgAlertNotification> imvgAlertNotifications = new HashSet<ImvgAlertNotification>();
			for (Object[] objects : results) {
				Long ianId = (Long) objects[0];
				String notificationcheck = (String) objects[1];
				Long wsappCheck = (Long) objects[2];
				ImvgAlertNotification imvgAlertNotification = new ImvgAlertNotification();
				imvgAlertNotification.setId(ianId);
				imvgAlertNotification.setNotificationCheck(notificationcheck);
				imvgAlertNotification.setWhatsApp(wsappCheck);
				
				Long uId = (Long) objects[3];
				String uName = (String) objects[4];
				String uReceipient = (String) objects[5];
				String uEmail = (String) objects[6];
				String uSMS = (String) objects[7];
				UserNotificationProfile userNotificationProfile = new UserNotificationProfile();
				userNotificationProfile.setId(uId);
				userNotificationProfile.setName(uName);
				userNotificationProfile.setRecipient(uReceipient);
				userNotificationProfile.setEMail(uEmail);
				userNotificationProfile.setSMS(uSMS);
				imvgAlertNotification.setUserNotificationProfile(userNotificationProfile);
				
				Long atId = (Long) objects[8];
				String atName = (String) objects[9];
				String atCommand = (String) objects[10];
				String atDetails = (String) objects[11];
				ActionType actionType = new ActionType();
				actionType.setId(atId);
				actionType.setName(atName);
				actionType.setCommand(atCommand);
				actionType.setDetails(atDetails);
				userNotificationProfile.setActionType(actionType);
				imvgAlertNotification.setUserNotificationProfile(userNotificationProfile);
				imvgAlertNotifications.add(imvgAlertNotification);
			}
			rule.setImvgAlertNotifications(imvgAlertNotifications);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return rule;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> checkRuleStartAndEndTime(Device devicetset,
			AlertType alerttype, long ruleid) {
		DBConnection db = null;
		List<Object[]> objects=null;
		
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			String query = "select dt.startTime,dt.endTime,dt.rule from DeviceAlert as dt" +
					" where dt.device="+devicetset.getId()+" and dt.alertType="+alerttype.getId()+"and dt.rule !="+ruleid;
			
			Query q = session.createQuery(query);
			objects = q.list();
			
		} catch (Exception ex) {
			
		} finally {
			db.closeConnection();
		}
		return objects;
	}

	@SuppressWarnings("unchecked")
	public Set<DeviceAlert> GetRuleStartAndEndTimeByDeviceId(Device devicetset, AlertType alertType) {
		DBConnection db = null;
		List<Object[]> objects=null;
		Set<DeviceAlert> deviceAlerts=new HashSet<DeviceAlert>();
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			String query = "select dt.startTime,dt.endTime,dt.rule from DeviceAlert as dt" +
					" where dt.device="+devicetset.getId()+" and dt.alertType="+alertType.getId()+ " and dt.rule != NULL";	
			Query q = session.createQuery(query);
			objects = q.list();
			
			
			for(Object[] Devicealert : objects)
			{
				long StartTime = (Long) Devicealert[0];
				long EndTime=(Long)Devicealert[1];
				DeviceAlert deviceale=new DeviceAlert();
				deviceale.setStartTime(StartTime);
				deviceale.setEndTime(EndTime);
				Rule rule=(Rule) Devicealert[2];
				deviceale.setRule(rule);
				deviceAlerts.add(deviceale);
			}
			
		} catch (Exception ex) {
			
		} finally {
			db.closeConnection();
		}
		return deviceAlerts;
	}

	@SuppressWarnings("unchecked")
	public Set<DeviceAlert> GetRuleStartAndEndTimeByDeviceId(Device device,
			AlertType alertType, int ruleid) {
		DBConnection db = null;
		List<Object[]> objects=null;
		Set<DeviceAlert> deviceAlerts=new HashSet<DeviceAlert>();
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			String query = "select dt.startTime,dt.endTime,dt.rule from DeviceAlert as dt" +
					" where dt.rule = "+ruleid;
			
			Query q = session.createQuery(query);
			objects = q.list();
			
			for(Object[] Devicealert : objects)
			{
				long StartTime = (Long) Devicealert[0];
				long EndTime=(Long)Devicealert[1];
				DeviceAlert deviceale=new DeviceAlert();
				deviceale.setStartTime(StartTime);
				deviceale.setEndTime(EndTime);
				Rule rule=(Rule) Devicealert[2];
				deviceale.setRule(rule);
				deviceAlerts.add(deviceale);
			}
			
		} catch (Exception ex) {
			
		} finally {
			db.closeConnection();
		}
		return deviceAlerts;
		
	}
//vibhu start
	public int validateRule(Rule rule)
	{
		int result = 0;
		DBConnection dbc = null;
		
		try
		{
			dbc = new DBConnection();
			Session session = dbc.openConnection();

			//Check if rule with same name exists
			{
				String query = 	"select count(r) "
								+ 	"from Rule as r "
								+ 	"where r.name like '"+rule.getName()+"' "
				                +   " and r.gateWay.id =" + rule.getGateWay().getId()
								+   " and r.id !=" + rule.getId();
				
				Query q = session.createQuery(query);	
				long count = (Long)q.uniqueResult();
				if(count > 0)return -2;
			}
			
			//Check if we already have MAX_RULE_PER_DEVICE_ALERT rules with same device and alert type pair
			{
				for(DeviceAlert da: rule.getDeviceAlerts())
				{
					
					String query = 	"select count(da) "
									+ 	"from DeviceAlert as da "
									+ 	"left join da.rule as r "
									+ 	"where da.rule is not NULL "
									+   " and da.device="+da.getDevice().getId()
									+	" and  da.alertType="+da.getAlertType().getId()
									+   " and r.gateWay.id =" + rule.getGateWay().getId()
									+   " and da.rule !=" + rule.getId();
					Query q = session.createQuery(query);	
					long count = (Long)q.uniqueResult();
					if(count >= Constants.MAX_RULE_PER_DEVICE_ALERT)return -3;

	
					//Check that there is at most one device for DEVICE_UP or DEVICE_DOWN
					if( 	da.getAlertType().getName().equals(Constants.DEVICE_UP_NAME)
						|| 	da.getAlertType().getName().equals(Constants.DEVICE_DOWN_NAME)
						)
					{
						query = 	"select count(da) "
								+ 	"from DeviceAlert as da "
								+ 	"left join da.alertType as at "
								+ 	"left join da.rule as r "
								+ 	"where da.rule is not NULL "
								+   " and da.device !="+da.getDevice().getId()
								+   " and r.gateWay.id = "+rule.getGateWay().getId()
								+	" and  (at.name='"+Constants.DEVICE_UP_NAME+"' or at.name='"+Constants.DEVICE_DOWN_NAME+"')";
						q = session.createQuery(query);	
						count = (Long)q.uniqueResult();
						if(count > 0)return -4;
					}
				}
			}
			
		}
		catch(Exception e)
		{
			LogUtil.error(e.getMessage());
			LogUtil.info(e.getMessage(), e);
			result = -1;
		}
		finally
		{
			if(dbc != null)dbc.closeConnection();
		}
		return result;
	}
	
	
//vibhu end	
	
//	bhavya start energu rule count
	@SuppressWarnings("unchecked")
	public List<Object[]> checkRuleCountForEnergy(Device devicetset,
			AlertType alerttype, long ruleid) {
		DBConnection db = null;
		List<Object[]> objects=null;
		
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			String query = "select dt.alertType,dt.device,dt.rule from DeviceAlert as dt" +
					" where dt.device="+devicetset.getId()+" and dt.alertType="+alerttype.getId()+"";
			
			Query q = session.createQuery(query);
			objects = q.list();
			
		} catch (Exception ex) {
			
		} finally {
			db.closeConnection();
		}
		
		return objects;
	}
//	bhavya end

	

public String Getdevicealertcompname(long ruleid){
	DBConnection db = null;
	String result=null;
	String query="select comparatorName from devicealert where rule="+ruleid+"";
	try {
		db = new DBConnection();
		Session session = db.openConnection();
		Query q = session.createSQLQuery(query);
		result = (String) q.uniqueResult();
	} catch (Exception ex) {
		
	} finally {
		db.closeConnection();
	}
	return result;
}

public void deleteAllImvgSecurityActions(long id) {
	DBConnection db = null;
	String sql = "delete is from imvgSecurityActions as is where is.customer=" + id;
	
	
	try {
		db = new DBConnection();
		Session session = db.openConnection();
		Query q = session.createSQLQuery(sql);
		Transaction tx = session.beginTransaction();
		q.executeUpdate();
		tx.commit();
	} catch (Exception ex) {
		
	} finally {
		db.closeConnection();
	}
	
}

@SuppressWarnings("unchecked")
public  Set<ImvgSecurityActions> retrieveImvgSecurityDetails(long id) {
	Set<ImvgSecurityActions> imvgSecurityActions = new LinkedHashSet<ImvgSecurityActions>(6);
	
	
	String query = "select isa.id, g.id, g.macId, isa.level,"
			+ " d.id, d.generatedDeviceId, d.friendlyName,"
			+ " at.id, at.name, at.command,"
			+ " c.customerId, c.id  from ImvgSecurityActions as isa" 
			+ " left join isa.gateway as g" + " left join isa.customer as c"
			+ " left join isa.device as d"
			+ " left join isa.actionType as at"
			+ " where c.id=" + id ;
	//String query = "select isa.id, g.id, g.macId, isa.level, d.id, d.generatedDeviceId, d.friendlyName, at.id, at.name, at.command, c.customerId, c.id  from imvgSecurityActions as isa left join gateway as g on isa.gateway=g.id left join customer as c on isa.customer=c.id left join device as d on isa.device=d.id left join actionType as at on isa.actionType=at.id where isa.customer=c.id and c.id="+id;
	
	
	List<Object[]> detailsArray = null;
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		detailsArray = (List<Object[]>) q.list();
		
		if(detailsArray != null) {
		for (Object[] details : detailsArray) {
			
				Long securityId = (Long) details[0];
				Long gateWayId = (Long) details[1];
				String gateWayMacId = (String) details[2];
				String level = (String) details[3];
				Long deviceId = (Long) details[4];
				String generatedDeviceId = (String) details[5];
				String friendlyName = (String) details[6];
				Long actionTypeId = (Long) details[7];
				String actionTypeName = (String) details[8];
				String actionTypeCommand = (String) details[9];
				String customerId = (String) details[10];

				GateWay gateWay = new GateWay();
				gateWay.setId(gateWayId);
				gateWay.setMacId(gateWayMacId);
				
				Customer customer = new Customer();
				customer.setCustomerId(customerId);
				customer.setId(id);
				
				ActionType actionType = new ActionType();
				actionType.setId(actionTypeId);
				actionType.setName(actionTypeName);
				actionType.setCommand(actionTypeCommand);
				
				Device device = new Device();
				device.setId(deviceId);
				device.setGeneratedDeviceId(generatedDeviceId);
				device.setFriendlyName(friendlyName);
				
				
				ImvgSecurityActions actions = new ImvgSecurityActions();
				actions.setActionType(actionType);
				actions.setCustomer(customer);
				actions.setGateway(gateWay);
				actions.setDevice(device);
				actions.setLevel(level);
				actions.setId(securityId);
				
				imvgSecurityActions.add(actions);	
		
		}
	}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return imvgSecurityActions;
}

}
