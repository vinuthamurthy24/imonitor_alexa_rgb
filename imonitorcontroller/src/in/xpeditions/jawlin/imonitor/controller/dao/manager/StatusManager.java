/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

public class StatusManager {
	DaoManager daoManager=new DaoManager();
	public boolean save(Status status){
		boolean result=false;
		try{
			daoManager.save(status);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
	return result;
	}
	@SuppressWarnings("unchecked")
	public List<Status> listStatuses() {
		List<Status>statuses=new ArrayList<Status>();
		try{
			statuses=(List<Status>)daoManager.list(Status.class);
		}catch(Exception e){
			e.printStackTrace();
		}return statuses;
	
	}
	public Status getStatusByName(String name) {
		Status status=(Status) daoManager.getOne("name", name, Status.class);
		return status;
	}
	public Status getStatusById(long id) {
		return (Status) daoManager.get(id, Status.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Status> listStatuseInMap() {
		List<Status> statuss;
		Map<String, Status> statusMap = new HashMap<String, Status>();
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria crt = session.createCriteria(Status.class);
			statuss = crt.list();
			for (Status status : statuss) {
				statusMap.put(status.getName(), status);
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving all statuss : "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return statusMap;
	}
}
