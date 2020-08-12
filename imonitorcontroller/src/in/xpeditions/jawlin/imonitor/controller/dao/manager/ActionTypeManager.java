/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author computer
 *
 */
public class ActionTypeManager {
	DaoManager daoManager=new DaoManager();
	public boolean save(ActionType actionType ){
		boolean result=false;
		try{
			daoManager.save(actionType);
			result=true;
		}catch(Exception e){
			LogUtil.error("Error when saving action type with name : " + actionType.getName() + "message : " + e.getMessage());
		}
	return result;
	}
	@SuppressWarnings("unchecked")
	public List<ActionType> getActionTypesByName(String[] actions) {
		List<ActionType> actionTypes = null;
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(ActionType.class);
			Criterion crtn = Restrictions.in("name", actions);
			criteria.add(crtn);
			actionTypes = criteria.list();
		}catch(Exception e){
			LogUtil.error("Error when retreiving actiontypes by names : " + e.getMessage());
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		return actionTypes;
	}
	public ActionType getActionTypeById(long id) {
		return (ActionType) daoManager.get(id, ActionType.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, ActionType> listAllActionTypesInMap() {
		List<ActionType> actionTypes;
		Map<String, ActionType> actionTypeMap = new HashMap<String, ActionType>();
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria crt = session.createCriteria(ActionType.class);
			actionTypes = crt.list();
			for (ActionType actionType : actionTypes) {
				actionTypeMap.put(actionType.getCommand(), actionType);
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving all action types : "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return actionTypeMap;
	}
	
	public ActionType getActionTypeName(String name) {
		return (ActionType) daoManager.getOne("name", name, ActionType.class);
	}
	
	
	public String getIdOfActionType(String  actionName){
	DBConnection dbc = null;
	String result = null;
	try {
		String query = "select id" +
				" from ActionType as a" +
				" where a.name='" + actionName + "'";
		
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		result = IMonitorUtil.convertToString(q.uniqueResult());
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return result;
	
}

	
}
