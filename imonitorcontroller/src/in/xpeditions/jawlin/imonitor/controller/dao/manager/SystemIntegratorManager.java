/* Copyright  2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.FirmWare;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SmsService;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemIntegrator;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;

public class SystemIntegratorManager {
	
	DaoManager daoManager = new DaoManager();
	
	@SuppressWarnings("unchecked")
	public List<SystemIntegrator> listOfSystemIntegrators() {
		
		
		List<SystemIntegrator> systemIntegrators = new ArrayList<SystemIntegrator>();
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(SystemIntegrator.class);
			systemIntegrators = criteria.list();
			for (SystemIntegrator si : systemIntegrators) {
				si.setAgent(null);
			}
		}catch(Exception e){
			LogUtil.error("Error when retreiving list of firmwares. : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		
		
		return systemIntegrators;
	}
	
	public boolean saveSystemIntegrator(SystemIntegrator systemIntegrator) {
		
		boolean result=false;
		try{
			daoManager.save(systemIntegrator);
			result=true;
		}catch(Exception e){
			LogUtil.info("DB Exception : ", e);
		}
		LogUtil.info("saveSystemIntegrator : OUT");
		return result;
	}

	
	
	@SuppressWarnings("rawtypes")
	public List listAskedSystemIntegrator(String sSearch,String sOrder, int start, int length) {
		String query = "";
        query += "select si.id,si.systemIntegratorId,si.name from " + " SystemIntegrator as si " +
                "where (si.systemIntegratorId like '%"+sSearch+"%') "+sOrder;   
        List list = daoManager.listAskedObjects(query,start,length,SystemIntegrator.class);
		return list;
	}

	public int getTotalSystemIntegratorCount() {
		
			int count =  daoManager.getCount(SystemIntegrator.class);
			return count;
		
	}

	public SystemIntegrator getSystemIntergratorById(long id) {
		
		SystemIntegrator SI = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			SI = (SystemIntegrator) session.get(SystemIntegrator.class, id);
			if (null != SI) {
				Hibernate.initialize(SI);
				SI.setStatus(SI.getStatus());
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving SI by id : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		
		return SI;
	}

	public boolean DeleteSystemIntegratorById(SystemIntegrator systemIntegrator) {
		boolean result=false;
		try{
			daoManager.delete(systemIntegrator);
			result=true;
		}catch(Exception e){
			LogUtil.info("DB Exception : ", e);
		}
		LogUtil.info("saveSystemIntegrator : OUT");
		return result;
	}

	public Boolean UpdatedSystemIntegrator(SystemIntegrator systemIntegrator) {
		boolean result=false;
		try{
			daoManager.update(systemIntegrator);
			result=true;
		}catch(Exception e){
			LogUtil.info("DB Exception : ", e);
		}
		LogUtil.info("saveSystemIntegrator : OUT");
		return result;
	}

}
