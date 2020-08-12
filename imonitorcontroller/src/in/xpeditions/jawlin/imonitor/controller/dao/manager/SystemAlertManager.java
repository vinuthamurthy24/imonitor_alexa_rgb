/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlertAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SystemAlertManager {
	DaoManager daoManager = new DaoManager();

	//sumit start: [Sept 06, 2012] Main user Log in notification
	@SuppressWarnings("unchecked")
	public List<SystemAlertAction> getSystemAlertActionByUserId(long userId){
		List<SystemAlertAction> systemAlertActions = new  ArrayList<SystemAlertAction>();
		List<Object[]> objects = null;
		DBConnection dbc = null;
		SystemAlertAction alertAction = null;
		String query = "select saa.id , sa.id, sa.name , at.id, at.name from SystemAlertAction saa"
				+ " left join saa.systemAlert sa left join saa.actionType at where saa.user = "+userId;
		
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
			for(Object[] strings : objects){
				alertAction = new SystemAlertAction();
				long id = (Long) strings[0];
				alertAction.setId(id);
				
				SystemAlert systemAlert = new SystemAlert();
				long sysAlertId = (Long) strings[1];
				String sysAlertName = (String) strings[2];
				systemAlert.setId(sysAlertId);
				systemAlert.setName(sysAlertName);
				
				ActionType actionType = new ActionType();
				long actionTypeId = (Long) strings[3];
				String actionTypeName = (String) strings[4];
				actionType.setId(actionTypeId);
				actionType.setName(actionTypeName);
				
				alertAction.setSystemAlert(systemAlert);
				alertAction.setActionType(actionType);
				
				systemAlertActions.add(alertAction);
			}
		} catch (HibernateException e) {
			LogUtil.info("Hibernate Exception: ", e);
		} catch (Exception e) {
			LogUtil.info("Got Exception: ", e);
		} finally {
			dbc.closeConnection();
		}
		return systemAlertActions;
	}
	//sumit end

/*	public boolean saveSystemAlertByUserId(SystemAlertByUser user) {
		boolean result = false;
		try {
			daoManager.save(user);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	*/
	
	public boolean saveSystemAlertAction(String[] email,String[] sms,User user ) {
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Transaction tx = session.beginTransaction();
			if(email !=null){
				ActionType actionTypeEmail = IMonitorUtil.getActionTypes().get(Constants.SEND_EMAIL);
					for(int e=0;e<email.length;e++){
						SystemAlertAction systemAlertAction = new SystemAlertAction();
						SystemAlert systemAlert = new SystemAlert();
						systemAlert.setId(Long.parseLong(email[e]));
						systemAlertAction.setSystemAlert(systemAlert);
						systemAlertAction.setUser(user);
						systemAlertAction.setActionType(actionTypeEmail);
						session.save(systemAlertAction);
					}
				}
			if(sms !=null){
				ActionType actionTypeSms = IMonitorUtil.getActionTypes().get(Constants.SEND_SMS);
					for(int s=0;s<sms.length;s++){
						SystemAlertAction systemAlertAction = new SystemAlertAction();
						SystemAlert systemAlert = new SystemAlert();
						systemAlert.setId(Long.parseLong(sms[s]));
						systemAlertAction.setSystemAlert(systemAlert);
						systemAlertAction.setUser(user);
						systemAlertAction.setActionType(actionTypeSms);
						session.save(systemAlertAction);
					}
				}
			
			tx.commit();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public List<SystemAlert> listOfSystemAlerts() {
		List<SystemAlert> systemAlerts = new ArrayList<SystemAlert>();
		try {
			systemAlerts = (List<SystemAlert>) daoManager.list(SystemAlert.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return systemAlerts;

	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getSystemAlertByUserAndAlertId(String userId,long systemAlertId) {
		List<Object[]> objects =null;
		//String query = "select sa from SystemAlertAction sa left join sa.user u where u.userId='user1' and sa.systemAlert =1";
		String query = "select sa.id,at.name from SystemAlertAction sa "
			+ " left join sa.user u left join sa.actionType at where u.id="+userId+" and sa.systemAlert ="+systemAlertId;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = (List<Object[]>) q.list();
				
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
	
	public SystemAlert getSystemAlertByName(String name) {
		String query = "select sa from SystemAlert sa where sa.name='"+name+"'";
		SystemAlert object =null;
			DBConnection dbc = null;
			
			try {
				dbc = new DBConnection();
				Session session = dbc.openConnection();
				Query q = session.createQuery(query);
				 object = (SystemAlert) q.uniqueResult();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
			return object;
		}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getSystemAlertByUser(long id) {
		List<Object[]> objects =null;
		String query = "select sa.id,a.id,at.name from SystemAlertAction sa"
			+ " left join sa.systemAlert a left join sa.actionType at where sa.user = "+id;
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
	
	
	public boolean deleteSystemAlertByUser(long id) {
		String query = "delete from SystemAlertAction sa "
			+"where sa.user = "+id;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			 @SuppressWarnings("unused")
			int a = q.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return true;
	}
	
}
