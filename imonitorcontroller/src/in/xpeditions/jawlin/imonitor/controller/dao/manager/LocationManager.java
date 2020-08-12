/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.thoughtworks.xstream.XStream;

public class LocationManager {

	DaoManager daoManager = new DaoManager();
	
	//sumit start: Location Image for Map Feature
	public long getLocationIdByName(String locName, String customerId){
		
		long locId = new Long(0);
		DBConnection dbc = null;
		String query = "select l.id"
				+ " from location as l, customer as c"
				+ " where l.name='"+locName+"' and l.customer=c.id and c.customerId='"+customerId+"' ";
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(query);
			BigInteger locationId = (BigInteger) q.uniqueResult();
			long test = locationId.longValue();
			
			locId = locationId.longValue();	
		} catch (HibernateException e) {
			LogUtil.error("Error while gettin location by name: "+e.getMessage());
		} finally{
			if(dbc != null){
				dbc.closeConnection();
			}
		}
		
		return locId;
	}
	
	public boolean saveUploadedImageForLocation(long locationId, String filePath){
		boolean result = false;
		Location location = getLocationById(locationId);
		location.setIconFile(filePath);
		if(updateLocation(location)){
			result = true;
		}
		return result;
	}
	//sumit end: Location Image for Map Feature

	public boolean saveLocation(Location location) {
		boolean result = false;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Transaction tx = session.beginTransaction();
			session.save(location);
			tx.commit();
			session.flush();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
			try {
				dbc.rollback();
			} catch (Exception ex1) {
				LogUtil.error(ex1.getMessage());
			}
		} finally {
			dbc.closeConnection();
		}
		return result;
	}

	public boolean deleteLocation(Location location) {
		boolean result = false;
		try {
			daoManager.delete(location);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateLocation(Location location) {
		boolean result = false;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Transaction tx = session.beginTransaction();
			session.update(location);
			tx.commit();
			session.flush();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
			try {
				dbc.rollback();
			} catch (Exception ex1) {
				LogUtil.error(ex1.getMessage());
			}
		} finally {
			dbc.closeConnection();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Location> listOfLocations() {
		List<Location> locations = new ArrayList<Location>();
		try {
			locations = (List<Location>) daoManager.list(Location.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locations;
	}

	public Location getLocationById(long locationId) {
		return (Location) daoManager.get(locationId, Location.class);
	}

	public List<?> listAskedLocations(String sSearch,String sOrder, int start, int length,
			long id) {
		String query = "";
		query += "select l.id,l.name,l.details from "
				+ "Location as l join l.customer as c " + "where l.customer="
				//+ id + " and l.name like '%" + sSearch + "%'"+ sOrder;
				+ id + " and (l.name like '%" + sSearch + "%'"+ " or l.details like '%" + sSearch + "%')"+sOrder;

		List<?> list = daoManager.listAskedObjects(query, start, length,
				Location.class);
		return list;
	}

	public int getTotalLocationCount(String sSearch,long id){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(l)" +
        		" from " 
			+ "Location as l join l.customer as c " + "where l.customer="
			//+ id + " and l.name like '%" + sSearch + "%'";
			+ id + " and l.name like '%" + sSearch + "%'" + " and l.details like '%" + sSearch + "%'";


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
	public int getTotalLocationCountByCustomerId(long id){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(l)" +
        		" from " 
			+ "Location as l join l.customer as c " + "where l.customer="
			+ id ;
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

	
	@SuppressWarnings("unchecked")
	public List<Location> listOfLocations(Customer customer) {
		List<Location> locations = daoManager.list("customer", customer,
				Location.class);
		Hibernate.initialize(locations);
		for (Location location : locations) {
			location.setCustomer(null);
		}
		return locations;
	}

	@SuppressWarnings("unchecked")
	public Location deleteLocationIfNoOtherReferenceAndNotLast(long id) {
		Location location = new Location();
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			String query = "select count(l)" +
			" from Location as l" +
			" where l.customer.id in " +
			"(select c.id from " +
			" Location as lc " +
			" left join lc.customer as c" +
			" where lc.customer=c.id" +
			" and lc.id=" + id + ")" +
			"";
			Query q = session.createQuery(query);
			Long cnt = (Long) q.uniqueResult();
			if(cnt > 1){
				Transaction tx = session.beginTransaction();
				query = "select d from Device d where d.location ="+id;
				q = session.createQuery(query);
				List<Object> objects = null;
				objects = q.list();
				
				
				if(objects.size() == 0){
					location = (Location) session.get(Location.class, id);
					session.delete(location);
				}
				tx.commit();
				session.flush();
			}
		} catch (Exception ex) {
			location = null;
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
			try {
				dbc.rollback();
			} catch (Exception ex1) {
				LogUtil.error(ex1.getMessage());
			}
		} finally {
			dbc.closeConnection();
		}
		return location;
	}
	public List<Object[]>  listOfLocations(long id) {
		String query = "";
		query += "select l.id,l.name,l.details from "
				+ "Location as l join l.customer as c " + "where l.customer="
				+ id ;

		List<Object[]> objects = daoManager.listAskedObjects(query,Location.class);
		return objects;
	}
	
	public String verifyLocation(Location location)
	{
		int result=0;
		DBConnection dbc = null;
		try
		{
			
		} 
		catch (Exception e)
		{
 
		}
		return null;
	}
	
}
