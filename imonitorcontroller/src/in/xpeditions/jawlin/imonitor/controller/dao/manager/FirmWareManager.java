/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.FirmWare;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author computer
 *
 */
public class FirmWareManager {
	DaoManager daoManager=new DaoManager();
	public boolean saveFirmWare(FirmWare firmWare) {
		boolean result=false;
		try{
			daoManager.save(firmWare);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}
	public boolean deleteFirmWare(FirmWare firmWare) {
		boolean result=false;
		try{
			daoManager.delete(firmWare);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}
	public boolean updateFirmWare(FirmWare firmWare) {
		boolean result=false;
		try{
			daoManager.update(firmWare);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}
	@SuppressWarnings("unchecked")
	public List<FirmWare> listAllFirmwares() {
		List<FirmWare> firmWares = null;
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(FirmWare.class);
			firmWares = criteria.list();
			for (FirmWare firmWare : firmWares) {
				firmWare.setAgent(null);
			}
		}catch(Exception e){
			LogUtil.error("Error when retreiving list of firmwares. : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		return firmWares;
	}
	public int getTotalFirmWareCount(){
		int count =  daoManager.getCount(FirmWare.class);
		return count;
	}
	public List listAskedFirmWare(String sSearch,String sOrder, int start, int length) {
		String query = "";
        query += "select f.id,f.version,f.file" +
        		",f.description from " +
                "FirmWare as f " +
                "where  (f.version like '%"+sSearch+"%' " +
                				"or f.file like '%"+sSearch+"%' ) " +sOrder;
                						
        
		List list = daoManager.listAskedObjects(query,start,length,FirmWare.class);
		return list;
	}
	//bhavya start
public Object[] listLatesestFirmWare(String macid) {
		
		DBConnection dbc = null;
		String query = " select f.id,substr(f.version,1,14) from firmware f,gateway g where substr(f.version,1,5)=substr(g.gateWayVersion,1,5) and f.activation=1 and g.macId='"+macid+"' and substr(f.version,7,8)>(select substr(gateWayVersion,7,8) from gateway where macId='"+macid+"')order by version desc limit 1";		
		//LogUtil.info(query);
		Object[] objects = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = (Object[])q.uniqueResult();
	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
}
//bhavya start
public boolean updateFirmwareactivation(long id, long activation) {
	DBConnection dbc = null;
	String query = "update firmware set activation="+activation+" where id="+id+"";
	
	boolean result = false;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createSQLQuery(query);
		Transaction tx = session.beginTransaction();
		q.executeUpdate();
		tx.commit();
		result = true;
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return result;
}
//bhavya end
public Object[] getLatestFirmwareversionNA900() {
	DBConnection dbc = null;
	String query = "select id,substr(version,7,8) from firmware where substr(version,1,5)='NA900' order by version desc limit 1";		
	Object[] objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createSQLQuery(query);
		objects = (Object[])q.uniqueResult();

	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	
	return objects;
}
public Object[] getLatestFirmwareversionNA910() {
	
	DBConnection dbc = null;
	String query = "select id,substr(version,7,8) from firmware where substr(version,1,5)='NA910' order by version desc limit 1";		
	//LogUtil.info(query);
	Object[] objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createSQLQuery(query);
		objects = (Object[])q.uniqueResult();

	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}
//bhavya end
	public int getTotalFirmWareCount(String sSearch){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(f)" +
        		" from "+ 
			"FirmWare as f join f.agent as a " +
            "where f.agent=a.id and (f.id like '%"+sSearch+"%' " +
            		"or f.name like '%"+sSearch+"%' " +
            				"or f.file like '%"+sSearch+"%' " +
            						" or a.name like '%"+sSearch+"%' )";
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
	
	public FirmWare getFirmWareById(long id) {
		FirmWare firmWare = null;
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			firmWare = (FirmWare) session.get(FirmWare.class, id);
			Hibernate.initialize(firmWare);
			Hibernate.initialize(firmWare.getAgent());
		}catch(Exception e){
			LogUtil.error("Error when retreiving firmwares. : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		return firmWare;
	}
}
