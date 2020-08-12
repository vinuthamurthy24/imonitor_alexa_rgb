/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AVCategory;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SceneControllerMake;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MakeManager {
	DaoManager daoManager=new DaoManager();
	public boolean saveMake(Make make) {
		boolean result=false;
		try{
			result=daoManager.save(make);
		}catch(Exception e){
			e.printStackTrace();
		}
	return result;
	}
	public boolean deleteMake(Make make){
		boolean result=false;
		try{
			daoManager.delete(make);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public boolean updateMake(Make make){
		DBConnection dbc = null;
		try {
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Transaction tx = session.beginTransaction();
			session.update(make);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			dbc.closeConnection();
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public List<Make> listOfMakes() {
		List<Make>makes=new ArrayList<Make>();
		try{
			makes=(List<Make>)daoManager.list(Make.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return makes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Make> listOnlyMakes() {
		List<Make> makes = new ArrayList<Make>();
		DBConnection dbc = null;
		try {
			String query = "";
	        query += "select m from " +
	                "Make as m ";
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			List<Make> makeList = q.list();
			for (Make make : makeList) {
				Make m = new Make();
				m.setId(make.getId());
				m.setName(make.getName());
				m.setNumber(make.getNumber());
				m.setDetails(make.getDetails());
				makes.add(m);
			}
		} catch (Exception e) {
			LogUtil.error("Error when listing all makes.");
		} finally {
			dbc.closeConnection();
		}
		
		return makes;
	}
	
	public int getTotalMakeCount() {
		int count =  daoManager.getCount(Make.class);
		return count;
	}
	public List<?> listAskedMakes(String sSearch,String sOrder, int getiDisplayStart,
			int getiDisplayLength) {
		String query = "";
       /* query += "select m.id,m.name,m.number" +
        		",m.details, d.name from " +
                "Make as m join m.deviceType as d " +
                "where m.deviceType=d.id and (m.id like '%"+sSearch+"%' " +
                	"or m.name like '%"+sSearch+"%' " +
    				"or m.number like '%"+sSearch+"%' " +
    				"or m.details like '%"+sSearch+"%' " +
    				" or d.name like '%"+sSearch+"%' ) "+sOrder;*/
		
        //pari start- fixed search should'nt happen based on id
        query += "select m.id,m.name,m.number" +
        		",m.details, d.name from " +
                "Make as m join m.deviceType as d " +
                "where m.deviceType=d.id and (m.name like '%"+sSearch+"%' " +
    				"or m.number like '%"+sSearch+"%' " +
    				"or m.details like '%"+sSearch+"%' " +
    				" or d.name like '%"+sSearch+"%' ) "+sOrder;
        //pari end
        
		List<?> list = daoManager.listAskedObjects(query,getiDisplayStart,getiDisplayLength,Make.class);
		return list;
	}
	
	public int getTotalMakeCount(String sSearch){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(m)" +
        		" from " +
			 "Make as m join m.deviceType as d " +
             "where m.deviceType=d.id and (m.id like '%"+sSearch+"%' " +
             	"or m.name like '%"+sSearch+"%' " +
 				"or m.number like '%"+sSearch+"%' " +
 				"or m.details like '%"+sSearch+"%' " +
 				" or d.name like '%"+sSearch+"%' )";
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
	
	
	public Make getMakeById(long id) {
		Make make = null;
		DBConnection dbc = null;
		try {
			String query = "";
	        query += "select m.id,m.name,m.number" +
	        		",m.details, d.id, d.name from " +
	                "Make as m join m.deviceType as d " +
	                "where m.id=" + id;
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Object[] columns = (Object[]) q.uniqueResult();
			make = new Make();
			make.setId(id);
			make.setName(IMonitorUtil.convertToString(columns[1]));
			make.setNumber(IMonitorUtil.convertToString(columns[2]));
			make.setDetails(IMonitorUtil.convertToString(columns[3]));
			
			DeviceType deviceType = new DeviceType();
			deviceType.setId(Long.parseLong(IMonitorUtil.convertToString(columns[4])));
			deviceType.setName(IMonitorUtil.convertToString(columns[5]));
			make.setDeviceType(deviceType);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return make;
	}
	public void deleteMake(long id) {
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Make make = (Make) session.get(Make.class, id);
			Transaction tx = session.beginTransaction();
			session.delete(make);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
	}
	@SuppressWarnings("unchecked")
	public List<Make> listMakesByDeviceType(String generatedDeviceId) {
		DBConnection dbc = null;
		List<Make> makes = null;
		try {
			String query = "";
	        query += "select m.id, m.name, m.number" +
	        		", m.details, d.id, d.name from " +
	                "Make as m join m.deviceType as d " +
	                "where d.id in (" +
	                "select dt.id from " +
	                " Device as dv " +
	                " join dv.deviceType as dt" +
	                " where dv.generatedDeviceId='" + generatedDeviceId + "')";
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			List<Object[]> list = q.list();
			makes = new ArrayList<Make>();
			for (Object[] object : list) {
				Make make = new Make();
				make.setId((Long)object[0]);
				make.setName(IMonitorUtil.convertToString(object[1]));
				make.setNumber(IMonitorUtil.convertToString(object[2]));
				make.setDetails(IMonitorUtil.convertToString(object[3]));
				DeviceType deviceType = new DeviceType();
				deviceType.setId((Long)object[4]);
				deviceType.setName(IMonitorUtil.convertToString(object[5]));
				make.setDeviceType(deviceType);
				makes.add(make);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return makes;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Make> listMakesByDeviceTypehealth(String devicetype) {
		DBConnection dbc = null;
		List<Make> makes = null;
		try {
			String query = "";
	        query += "select m.id, m.name, m.number" +
	        		", m.details, dt.id, dt.name from " +
	                " Make as m join m.deviceType as dt " +
	                " where dt.name='" + devicetype + "'";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			List<Object[]> list = q.list();
			makes = new ArrayList<Make>();
			for (Object[] object : list) {
				Make make = new Make();
				make.setId((Long)object[0]);
				make.setName(IMonitorUtil.convertToString(object[1]));
				make.setNumber(IMonitorUtil.convertToString(object[2]));
				make.setDetails(IMonitorUtil.convertToString(object[3]));
				DeviceType deviceType = new DeviceType();
				deviceType.setId((Long)object[4]);
				deviceType.setName(IMonitorUtil.convertToString(object[5]));
				make.setDeviceType(deviceType);
				makes.add(make);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return makes;
	}
	
	
	//naveen start for ac auto search
	@SuppressWarnings("unchecked")
	public List<String> listAutoMakesForAc() {
		DBConnection dbc = null;
		List<String> acBrandList = new ArrayList<String>();
		try {
			String query = "";
			query += " select m.name from make as m "
					+ " group by m.name ";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			acBrandList = q.list();
		} catch (Exception ex) {
			LogUtil.info("Caught Exception  while getting Ac Brands : ", ex);
		} finally {
			dbc.closeConnection();
		}
		return acBrandList;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getCodeListbySelectedBrand(String selectedBrand) {
		DBConnection dbc = null;
		List<String> acCodeList = new ArrayList<String>();
		try {
			String query = "";
			query += " select m.number from make as m where"
					+ " m.name = '" + selectedBrand + "'";
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			acCodeList = q.list();
		} catch (Exception ex) {
			LogUtil.info("Caught Exception  while getting brand Codes for AC : ", ex);
		} finally {
			dbc.closeConnection();
		}
		return acCodeList;
	}
	
	
	public Make getMakeBySelectedBrandAndModel(String selectedBrand, String selectedModelNumber) {
		Make make = null;
		DBConnection dbc = null;
		try {
			String query = "";
	        query += "select m.id,m.name,m.number" +
	        		",m.details, d.id, d.name from " +
	                "Make as m join m.deviceType as d " +
	                "where m.name='" + selectedBrand + "'" +
	                "and m.number='" + selectedModelNumber+"'";
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Object[] columns = (Object[]) q.uniqueResult();
			make = new Make();
			make.setId(Long.parseLong(IMonitorUtil.convertToString(columns[0])));
			make.setName(selectedBrand);
			make.setNumber(selectedModelNumber);
			make.setDetails(IMonitorUtil.convertToString(columns[3]));
			
			DeviceType deviceType = new DeviceType();
			deviceType.setId(Long.parseLong(IMonitorUtil.convertToString(columns[4])));
			deviceType.setName(IMonitorUtil.convertToString(columns[5]));
			make.setDeviceType(deviceType);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return make;
	}
	
	@SuppressWarnings("unchecked")
	public List<Make> listMakesByDeviceTyp(String barand, String generatedDeviceId) {
		DBConnection dbc = null;
		List<Make> makes = null;
		try {
			String query = "";
	        query += "select m.id, m.name, m.number" +
	        		", m.details, d.id, d.name from " +
	                "Make as m join m.deviceType as d " +
	                "where m.name= '"+barand+"'"+" and d.id in (" +
	                "select dt.id from " +
	                " Device as dv " +
	                " join dv.deviceType as dt" +
	                " where dv.generatedDeviceId='" + generatedDeviceId + "')";
			query += "select m.id, m.number" +
	        		", m.details, m.deviceType from " +
	                "Make as m " +
	                "where m.name = '" + generatedDeviceId + "'";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			List<Object[]> list = q.list();
			makes = new ArrayList<Make>();
			for (Object[] object : list) {
				Make make = new Make();
				make.setId((Long)object[0]);
				make.setName(barand);
				make.setNumber(IMonitorUtil.convertToString(object[2]));
				make.setDetails(IMonitorUtil.convertToString(object[3]));
				DeviceType deviceType = new DeviceType();
				deviceType.setId((Long)object[4]);
				make.setDeviceType(deviceType);
				makes.add(make);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return makes;
	}
	
	public boolean saveSceneControllermake(SceneControllerMake scenemake) {
		boolean result=false;
		try{
			result=daoManager.save(scenemake);
		}catch(Exception e){
			e.printStackTrace();
		}
	return result;
	}
	
}
