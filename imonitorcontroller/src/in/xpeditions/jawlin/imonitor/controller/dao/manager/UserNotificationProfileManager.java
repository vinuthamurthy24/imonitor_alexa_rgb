/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotificationForScedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Coladi
 *
 */
public class UserNotificationProfileManager {

	public String saveSystemNotificaion(UserNotificationProfile systemNotificaion) {
		DBConnection dbc = null;
		String result="no";
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Transaction tx = session.beginTransaction();
			session.save(systemNotificaion);
			result="yes";
			tx.commit();
		}catch(Exception e){
			LogUtil.error("Error when saving  systemNotificaion: " + e.getMessage());
			e.printStackTrace();
			return result="no";
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		return result;
	}

	public List<?> listSystemNotificaion(String sSearch,String sOrder, int start,
			int length, long customerId) {
		String query = "";
       //query += "select sn.id,sn.name,sn.recipient" +
         query += "select sn.id,sn.name" +
        		",at.details,sn.whatsAppStatus,s.name from " +
                " UserNotificationProfile as sn join sn.actionType as at join sn.customer as c join sn.status as s " +
                "where sn.actionType=at.id and sn.customer=" + customerId + " and ( sn.name like '%"+sSearch+"%' or " +
                		" at.name like '%"+sSearch+"%') "+ sOrder;
        
		DBConnection dbc = null;
		List<?> objects = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			q.setMaxResults(length);
			q.setFirstResult(start);
			objects = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}

	public int getCustomerSystemNotificationCount(String sSearch,long customerId){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(sn)" +
        		" from " +
			" UserNotificationProfile as sn join sn.actionType as at join sn.customer as c " +
            "where sn.actionType=at.id and sn.customer=" + customerId + " and ( sn.name like '%"+sSearch+"%' or  " +
            		" at.name like '%"+sSearch+"%')";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Long lCount = (Long) q.uniqueResult();
			count = lCount.intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return count;
	}

	public int getTotalSystemNotificationCount(long customerId){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(sn)" +
        		" from " +
			" UserNotificationProfile as sn join sn.actionType as at join sn.customer as c " +
            "where sn.actionType=at.id and sn.customer=" + customerId ;
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Long lCount = (Long) q.uniqueResult();
			count = lCount.intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return count;
	}
	
	public String updateSystemNotificaion(UserNotificationProfile systemNotificaion) {
		DBConnection dbc = null;
		String result="no";
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Transaction tx = session.beginTransaction();
			session.update(systemNotificaion);
			result="yes";
			tx.commit();
		}catch(Exception e){
			LogUtil.error("Error when updating systemNotificaion: " + e.getMessage());
			e.printStackTrace();
			result="no";
			return result;
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}	
		return result;
	}
	

	public boolean deleteSystemNotificaion(UserNotificationProfile systemNotificaion) {
		boolean result = false; 
		DBConnection dbc = null;
		//vibhu start
		List<ImvgAlertNotification> alertNotifications = new DaoManager().get("userNotificationProfile",systemNotificaion.getId(), ImvgAlertNotification.class);
		List<ImvgAlertNotificationForScedule> scheduleNotifications = new DaoManager().get("userNotificationProfile", systemNotificaion.getId(), ImvgAlertNotificationForScedule.class);
		//vibhu end
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Transaction tx = session.beginTransaction();

			//vibhu start
			//delete notifications from  rule actions
			
			for( ImvgAlertNotification ian: alertNotifications)
			{
				
				session.delete(ian);
			}
			//Naveen Added for deleting schedule notifications
			for(ImvgAlertNotificationForScedule ias: scheduleNotifications){
				
				session.delete(ias);
			}
            //naveen end
			//vibhu end
			session.delete(systemNotificaion);
			tx.commit();
			
			result = true;
		}catch(Exception e){
			LogUtil.error("Error when deleting systemNotificaion: " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}		
		return result;
	}

	public UserNotificationProfile getSystemNotificaion(long systemNotificaionId) {
		UserNotificationProfile systemNotificaion = null;
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Transaction tx = session.beginTransaction();
			systemNotificaion = (UserNotificationProfile) session.get(UserNotificationProfile.class, systemNotificaionId);
			tx.commit();
			Hibernate.initialize(systemNotificaion);
			systemNotificaion.setStatus(systemNotificaion.getStatus());
		}catch(Exception e){
			LogUtil.error("Error when retieiving systemNotificaion: " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}	
		return systemNotificaion;
	}

	@SuppressWarnings("unchecked")
	public List<UserNotificationProfile> getSystemNotificaions(Customer customer) {
		List<UserNotificationProfile> systemNotificaions = null;
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Criteria criteria = session.createCriteria(UserNotificationProfile.class);
			Criterion criterion = Restrictions.eq("customer", customer);
			criteria.add(criterion);
			systemNotificaions = criteria.list();
			for(UserNotificationProfile systemNotificaion:systemNotificaions){
				Hibernate.initialize(systemNotificaion);
				ActionTypeManager actionTypeManager = new ActionTypeManager();
				long id = systemNotificaion.getActionType().getId();
				ActionType actionType = actionTypeManager.getActionTypeById(id);
				systemNotificaion.setActionType(actionType);
				systemNotificaion.setCustomer(null);
			}
		}catch (Exception e){
			LogUtil.error("Error when retieiving systemNotificaion: " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		return systemNotificaions;
	}

}
