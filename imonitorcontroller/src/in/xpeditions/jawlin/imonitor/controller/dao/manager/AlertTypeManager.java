/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

public class AlertTypeManager {
	DaoManager daoManager=new DaoManager();
	public boolean save(AlertType alertType){
		boolean result=false;
		try{
			daoManager.save(alertType);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
	return result;
	}
	public AlertType getAlertTypeByDetails(String details) {
		return (AlertType) daoManager.getOne("details", details, AlertType.class);
	}
	public AlertType getAlertTypeById(long id) {
		return (AlertType) daoManager.get(id, AlertType.class);
	}
	@SuppressWarnings("unchecked")
	public Map<String, AlertType> listAllAlertTypesInMap() {
		List<AlertType> alertTypes;
		Map<String, AlertType> alertTypeMap = new HashMap<String, AlertType>();
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria crt = session.createCriteria(AlertType.class);
			alertTypes = crt.list();
			for (AlertType alertType : alertTypes) {
				alertTypeMap.put(alertType.getAlertCommand(), alertType);
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving all alert types : "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return alertTypeMap;
	}
}
