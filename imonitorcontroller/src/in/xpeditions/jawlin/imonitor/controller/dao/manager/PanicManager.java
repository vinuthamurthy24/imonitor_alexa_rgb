/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Panic;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.PanicAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.PanicAlertNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.HashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Coladi
 * 
 */

public class PanicManager {

	public boolean savePanicWithActionAndNotification(Panic panic, String action,
			String[] userNotificationProfiles) {
		String[] actions = action.split(Constants.EXPRESSION_OPERATOR_PATTERN);
		DBConnection db = null;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			db.beginTransaction();
			session.save(panic);
			if(!actions.equals("")){
				for (int i = 0; i < actions.length; i++) {
					if (!actions[i].trim().isEmpty()) {
						String[] details = actions[i].split("=");
						Device device = new Device();
						device.setId(Long.parseLong(details[0]));
						ActionType actionType = new ActionType();
						PanicAction panicAction = new PanicAction();
						if(details[1].contains("_")){
							String[] batteryRange = details[1].split("_");
							actionType.setId(Long.parseLong(batteryRange[0]));
							panicAction.setLevel(batteryRange[1]);
							}else{
								actionType.setId(Long.parseLong(details[1]));
							}
						panicAction.setDevice(device);
						panicAction.setActionType(actionType);
						panicAction.setPanic(panic);
						session.save(panicAction);
					}
				}
			}
			if(userNotificationProfiles != null){
				for (int i = 0; i < userNotificationProfiles.length; i++) {
					long userNotficationId = Long
							.parseLong(userNotificationProfiles[i]);
					UserNotificationProfile notificationProfile = new UserNotificationProfile();
					notificationProfile.setId(userNotficationId);
					PanicAlertNotification panicAlertNotification = new PanicAlertNotification();
					panicAlertNotification
							.setUserNotificationProfile(notificationProfile);
					panicAlertNotification.setPanic(panic);
					session.save(panicAlertNotification);
				}
			}
			db.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> listAllNotifications(Rule rule) {
		String query = "select u.name, u.recipient, at.name"
				+ " from ImvgAlertNotification ia"
				+ " left join ia.userNotificationProfile u"
				+ " left join u.actionType at"
				+ " where ia.userNotificationProfile=u.id"
				+ " and u.actionType=at.id" + " and ia.rule.id=" + rule.getId();

		List<Object[]> objects = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}

	public boolean deleteAlertActionsAndUserNotificationsByPanicId(long id) {
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			String hql = "delete from PanicAction  where panic ="+id;
			Session session = dbc.openConnection();
			dbc.beginTransaction();
			Query query = session.createQuery(hql);
		    int rowCount = query.executeUpdate();
		    hql = "delete from PanicAlertNotification  where panic ="+id;
		    query = session.createQuery(hql);
		    rowCount = query.executeUpdate();
		    hql = "delete from Panic where id="+id;
		    query = session.createQuery(hql);
		    rowCount = query.executeUpdate();
		    dbc.commit();
		    return (rowCount>0)?true:false;
		}catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return false;
	}
	
	public Panic getPanicWithByCustomerId(String customerId) {
//		1. Get the rule.
		DBConnection dbc = null;
		String query = "select p from Panic as p where p.customer =" +customerId;
		Panic panic = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			panic =  (Panic) q.uniqueResult();
			panic.setPanicAlertNotifications(new HashSet<PanicAlertNotification>());
			panic.setPanicActions(new HashSet<PanicAction>());
			if(panic != null){
				query = "select pa.id, d.id, d.friendlyName, at.id, at.command,pa.level " +
						" from PanicAction as pa" +
						" left join pa.device as d" +
						" left join pa.actionType as at" +
						" left join pa.panic as p" +
						" where p.id=" +panic.getId() +
						"";
				q = session.createQuery(query);
				List<Object[]> iaObjects = q.list();
				for (Object[] objects : iaObjects) {
					PanicAction panicAction = new PanicAction();
					long id = (Long)objects[0];
					panicAction.setId(id);
					panicAction.setLevel((String) objects[5]);
					Device device = new Device();
					id = (Long) objects[1];
					device.setId(id);
					device.setFriendlyName(IMonitorUtil.convertToString(objects[2]));
					panicAction.setDevice(device);
					ActionType actionType = new ActionType();
					id = (Long) objects[3];
					actionType.setId(id);
					actionType.setCommand(IMonitorUtil.convertToString(objects[4]));
					panicAction.setActionType(actionType);
					panic.getPanicActions().add(panicAction);
				}
				
				query = "select ia.id, u.id, u.name, u.recipient, at.id, at.command " +
				" from PanicAlertNotification as ia" +
				" left join ia.userNotificationProfile as u" +
				" left join u.actionType as at" +
				" left join ia.panic as p" +
				" where p.id=" + panic.getId() +
				"";
				q = session.createQuery(query);
				List<Object[]> inObjects = q.list();
				for (Object[] objects : inObjects) {
					PanicAlertNotification panicAlertNotification = new PanicAlertNotification();
					long id = (Long) objects[0];
					panicAlertNotification.setId(id);
					UserNotificationProfile userNotificationProfile = new UserNotificationProfile();
					id = (Long)objects[1];
					userNotificationProfile.setId(id);
					userNotificationProfile.setName(IMonitorUtil.convertToString(objects[2]));
					userNotificationProfile.setRecipient(IMonitorUtil.convertToString(objects[3]));
					ActionType actionType = new ActionType();
					id = (Long)objects[4];
					actionType.setId(id);
					actionType.setCommand(IMonitorUtil.convertToString(objects[5]));
					userNotificationProfile.setActionType(actionType);
					panicAlertNotification.setUserNotificationProfile(userNotificationProfile);
					panic.getPanicAlertNotifications().add(panicAlertNotification);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			panic = null;
		} finally {
			dbc.closeConnection();
		}
		return panic;
	}
	public Panic getPanicByCustomerId(long customerId) {
//		1. Get the rule.
		DBConnection dbc = null;
		Panic panic = null;
		try {
			String query = "select p from Panic as p where p.customer ="+customerId ;
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			 panic = (Panic) q.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
	return panic;
	}
	
	public List<Object[]> listAllNotifications(Panic panic) {
		String query = "select u.name, u.recipient, at.name"
				+ " from PanicAlertNotification ia"
				+ " left join ia.userNotificationProfile u"
				+ " left join u.actionType at"
				+ " where ia.userNotificationProfile=u.id"
				+ " and u.actionType=at.id" + " and ia.panic.id=" + panic.getId();

		List<Object[]> objects = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> listAllPanicActions(Panic panic) {
		String query = "select d.id, d.generatedDeviceId, g.macId, at.command, ag.ip, ag.controllerReceiverPort, ag.ftpIp, ag.ftpPort" +
				" ,ag.ftpUserName, ag.ftpPassword, c.customerId, pa.level"
				+ " from PanicAction pa"
				+ " left join pa.device d"
				+ " left join pa.actionType at"
				+ " left join d.gateWay as g"
				+ " left join g.agent as ag"
				+ " left join g.customer as c"
				+ " where pa.device=d.id"
				+ " and pa.actionType=at.id" 
				+ " and d.gateWay=g.id" 
				+ " and g.agent=ag.id" 
				+ " and pa.panic.id=" + panic.getId();

		List<Object[]> objects = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
	
	public Customer getCustomerByMacId(String macId) {
//		1. Get the rule.
		DBConnection dbc = null;
		Customer customer = new Customer();
		try {
			String query = "select c.id,c.customerId from GateWay g "
				+"left join g.customer as c where g.macId ='"+macId+"'";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			List<Object[]> objects = q.list();
			for(Object[] objects2:objects){
				customer.setId(Long.parseLong(objects2[0].toString()));
				customer.setCustomerId(objects2[1].toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
	return customer;
	}
}
