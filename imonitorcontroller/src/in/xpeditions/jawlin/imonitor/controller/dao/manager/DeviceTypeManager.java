/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.NewDeviceType;

import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class DeviceTypeManager {
	DaoManager daoManager=new DaoManager();
	public boolean saveDeviceType(DeviceType deviceType){
		boolean result=false;
		try{
			daoManager.save(deviceType);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
	return result;
	}
	public boolean deleteDeviceType(DeviceType deviceType){
		boolean result=false;
		try{
			daoManager.delete(deviceType);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public boolean updateDeviceType(DeviceType deviceType){
		boolean result=false;
		try{
			daoManager.update(deviceType);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public List<DeviceType> listOfDeviceTypes() {
		List<DeviceType>deviceTypes=new ArrayList<DeviceType>();
		try{
			deviceTypes=(List<DeviceType>)daoManager.list(DeviceType.class);
		}catch(Exception e){
			e.printStackTrace();
		}return deviceTypes;
	
	}
	public DeviceType getDeviceTypeByName(String name) {
		return (DeviceType) daoManager.getOne("name", name, DeviceType.class);
	}
	@SuppressWarnings("unchecked")
	public List<DeviceType> listOfDeviceTypesWithoutVirtualDevice() {
		List<DeviceType>deviceTypes=new ArrayList<DeviceType>();
		try{
			deviceTypes=(List<DeviceType>)daoManager.listWithFilterColum("name",DeviceType.class);
		}catch(Exception e){
			e.printStackTrace();
		}return deviceTypes;
	}
	
	//Admin New Device Type
	
	public int getTotalDeviceTypeCount() {
		
		int count =  daoManager.getCount(NewDeviceType.class);
	
		return count;
	}
	public List<?> listAskedDeviceTypes(String sSearch,String sOrder, int getiDisplayStart,
			int getiDisplayLength) {
		String query = "";
		query += "select m.id,m.name,m.number" +
        		",m.details, d.name from " +
                "Make as m join m.deviceType as d " +
                "where m.deviceType=d.id and (m.name like '%"+sSearch+"%' " +
    				"or m.number like '%"+sSearch+"%' " +
    				"or m.details like '%"+sSearch+"%' " +
    				" or d.name like '%"+sSearch+"%' ) "+sOrder;
        //pari end
        
		List<?> list = daoManager.listAskedObjects(query,getiDisplayStart,getiDisplayLength,NewDeviceType.class);
		return list;
	}
	
	public int getTotalDeviceTypeCount(String sSearch){
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
		return 0;
	}
	
	
	public DeviceType getDeviceTypeById(long id) {
		DBConnection dbc = null;
		DeviceType deviceType = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			deviceType = (DeviceType) dbc.getSession().get(DeviceType.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return deviceType;

	}
	
}
