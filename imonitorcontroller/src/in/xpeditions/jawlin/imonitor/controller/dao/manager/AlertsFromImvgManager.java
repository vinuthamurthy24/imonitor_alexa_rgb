/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import java.util.List;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertMonitor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertStatus;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertsFromImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AreaCode;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.thoughtworks.xstream.XStream;

/**
 * @author computer
 * 
 */

public class AlertsFromImvgManager {
	DaoManager daoManager = new DaoManager();

	public boolean save(AlertsFromImvg alertsFromImvg) {
		boolean result = false;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Transaction tx = session.beginTransaction();
			session.save(alertsFromImvg);
			tx.commit();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return result;
	}
	
	public List<Object[]> listalertsforapi(long id){
		DBConnection dbc = null;
		List<Object[]> objects=null;
		String query="select a.alertTime,d.generatedDeviceId,aa.name from  alertsfromimvg as a left join device as d on a.device=d.id left join gateway as g on d.gateway=g.id left join alerttype as aa on a.alertType=aa.id where d.gateway="+id+" order by a.id desc";
		try{
			dbc=new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects=q.list();
			}
		catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
		
	}
	public List<Object[]> listalarmsforapi(long id){
		DBConnection dbc = null;
		List<Object[]> objects=null;
		String query="select a.alertTime,d.generatedDeviceId,aa.name from  alertsfromimvg as a left join device as d on a.device=d.id left join gateway as g on d.gateway=g.id left join alerttype as aa on a.alertType=aa.id where d.gateway="+id+" and a.alarmStatus>0 order by a.id desc";
		try{
			dbc=new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects=q.list();
			}
		catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
		
	}
	
	public boolean savealertsformonitor(AlertMonitor alertmonitor){
		DBConnection db = null;
		boolean result = false;
		Transaction tx = null;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			tx = session.beginTransaction();
			session.save(alertmonitor);
			tx.commit();
			result = true;
		} catch (Exception ex) {
			
			if(tx != null)
			{
				tx.rollback();
			}
	
			
			LogUtil.error("Error when savign the alertsmonitor : " + alertmonitor.getRule() + " message is " + ex.getMessage());
		} finally {
			db.closeConnection();
		}
		return result;
	}

	@SuppressWarnings("null")
	public AreaCode getareacode(){
		DBConnection db = null;
		XStream stream=new XStream();
		AreaCode areacode=new AreaCode();
		try {
		String query="select id,code,areaName from areaCode where id=1";
		db = new DBConnection();
		Session session = db.openConnection();
		//LogUtil.info(query);
		Query q = session.createSQLQuery(query);
		//LogUtil.info(q);
		Object[] objects=(Object[]) q.uniqueResult();
		//LogUtil.info(stream.toXML(objects));
		if(objects != null){
			long id=Long.parseLong(IMonitorUtil.convertToString(objects[0]));
			//LogUtil.info(id);
			String code=IMonitorUtil.convertToString(objects[1]);
			String name=IMonitorUtil.convertToString(objects[2]);
			//LogUtil.info(name + code);
			areacode.setId(id);
			areacode.setCode(code);
			areacode.setAreaName(name);
			//LogUtil.info("yes");
		//LogUtil.info(areacode);
		}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return areacode;
	}
	@SuppressWarnings("null")
	public AlertStatus getalertstatus(){
		DBConnection db = null;
		AlertStatus altstatus=new AlertStatus();
		try {
		String query="select id,name from alertstatus where id=1";
		db = new DBConnection();
		Session session = db.openConnection();
		Query q = session.createSQLQuery(query);
		//LogUtil.info(query);
		Object[] objects=(Object[]) q.uniqueResult();
		if(objects != null){
			long id=Long.parseLong(IMonitorUtil.convertToString(objects[0]));
			String name=IMonitorUtil.convertToString( objects[1]);
			//LogUtil.info(name);
			altstatus.setId(id);
			altstatus.setName(name);
		//LogUtil.info(altstatus);
		}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return altstatus;
	}
	
	public AlertStatus getalertstatusbyid(long id){
		DBConnection db = null;
		AlertStatus altstatus=new AlertStatus();
		try {
		String query="select id,name from alertstatus where id="+id+"";
		db = new DBConnection();
		Session session = db.openConnection();
		Query q = session.createSQLQuery(query);
		//LogUtil.info(query);
		Object[] objects=(Object[]) q.uniqueResult();
		if(objects != null){
			long id1=Long.parseLong(IMonitorUtil.convertToString(objects[0]));
			String name=IMonitorUtil.convertToString( objects[1]);
			//LogUtil.info(name);
			altstatus.setId(id1);
			altstatus.setName(name);
		//LogUtil.info(altstatus);
		}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return altstatus;
	}
	
	public boolean updatealertsmonitor (AlertMonitor alertmonitor){
		boolean result=false;
		try{
			daoManager.update(alertmonitor);
			result=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;	
	}
	
	public AlertMonitor getalertsmonitorById(long id) {
		return (AlertMonitor) daoManager.get(id, AlertMonitor.class);
	}
	
	public boolean deletealertmonitor(AlertMonitor alertmonitor) {
		boolean result = false;
		try {
			daoManager.delete(alertmonitor);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public AreaCode getareaCodeById(long id) {
		return (AreaCode) daoManager.get(id, AreaCode.class);
	}
	
}
