/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceTypeModel;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GatewayStatus;
//import in.xpeditions.jawlin.imonitor.controller.dao.entity.GatewayStatus;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.NewDeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ZingGatewayList;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.thoughtworks.xstream.XStream;

public class GatewayManager {
	DaoManager daoManager=new DaoManager();
	public boolean saveGateWay(GateWay gateWay) {
		boolean result=false;
		try{
			daoManager.save(gateWay);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}
	public boolean deleteDevicesOfGateWay(String macId) {
		boolean result=false;
		String query = "delete d.* from device d, gateway g, devicetype dt "
				+" where d.gateWay = g.id  "
				+" and g.macId = '"+macId+"'"
				+" and d.deviceType = dt.id"
				+" and dt.category != 'VIRTUAL_DEVICE'";
		try{
			result = daoManager.executeSQLQuery(query);
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}
	
	public boolean deleteGateWay(GateWay gateWay) {
		boolean result=false;
		try{
			daoManager.delete(gateWay);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}
	public boolean updateGateWay(GateWay gateWay) {
		boolean result=false;
		try{
			LogUtil.info("updateGateWay gateWay"+gateWay);
			daoManager.update(gateWay);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}
	@SuppressWarnings("unchecked")
	public List<GateWay> listOfGateways() {
		List<GateWay>gateWays=new ArrayList<GateWay>();
		try{
			gateWays=(List<GateWay>)daoManager.list(GateWay.class);
		}catch(Exception e){
			e.printStackTrace();
		}return gateWays;
	
	}
	public GateWay getGateWayByMacId(String macId) {
		GateWay gateWay = (GateWay) daoManager.getOne("macId", macId, GateWay.class);
		return gateWay;
	}
	
	// ************************************************************* sumit start *****************************************************************
	//Define a method to create SYSTEM DEVICES.
	public boolean createSystemDevices(GateWay gateway){
		boolean result = false;
		DBConnection dbc = null;
		long gateWayId = gateway.getId();
		String macId = gateway.getMacId();
		//String customerId = gateway.getCustomer().getCustomerId();
		String[] query = new String[3];
		//query to create System Device-HOME
		query[0] = "insert into device(deviceId, gateWay, generatedDeviceId, friendlyName, deviceType, commandParam, location, enableList)"
				+ " select 'HOME', "+gateWayId+", '"+macId+"-HOME', 'Home', dt.id, '0', l.id, '0' from location as l, customer as c , devicetype dt, gateway g "
				+ " where l.customer=c.id  and l.name='Default Room' and dt.name='MODE_HOME' and c.id=g.customer and  g.id='"+gateWayId+"' ";
		//query to create System Device-STAY		
		query[1] = "insert into device(deviceId, gateWay, generatedDeviceId, friendlyName, deviceType, commandParam, location, enableList)"
				+ " select 'STAY', "+gateWayId+", '"+macId+"-STAY', 'Stay', dt.id, '0', l.id, '0' from location as l, customer as c , devicetype dt, gateway g "
				+ " where l.customer=c.id  and l.name='Default Room' and dt.name='MODE_STAY' and c.id=g.customer and  g.id='"+gateWayId+"' ";
		//query to create System Device-AWAY
		query[2] = "insert into device(deviceId, gateWay, generatedDeviceId, friendlyName, deviceType, commandParam, location, enableList)"
				+ " select 'AWAY', "+gateWayId+", '"+macId+"-AWAY', 'Away', dt.id, '0', l.id, '0' from location as l, customer as c , devicetype dt, gateway g "
				+ " where l.customer=c.id  and l.name='Default Room' and dt.name='MODE_AWAY' and c.id=g.customer and  g.id='"+gateWayId+"' ";
		
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Transaction tx = session.beginTransaction();
			for(String s: query){
				Query q = session.createSQLQuery(s);
				q.executeUpdate();
			}
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			dbc.closeConnection();
		}
		return result;
	}
	// ************************************************************** sumit end *****************************************************************
	
	@SuppressWarnings("unchecked")
	public List listAskedGateWay(String sSearch,String sOrder, int start, int length) {
		String query = "";
        query += "select g.id,g.macId,s.name" +
//        		",a.name from " +
//				",a.name,c.customerId from " +
        		",a.name,c.customerId, g.gateWayVersion from " +
//                "GateWay as g join g.status as s left join g.agent as a " +
                "GateWay as g join g.status as s left join g.agent as a left join g.customer as c " +
//                "where (g.id like '%"+sSearch+"%' " +
                "where (g.macId like '%"+sSearch+"%' " +
                				"or s.name like '%"+sSearch+"%' " +
        						" or a.name like '%"+sSearch+"%' " +
//                				"or c.customerId like '%"+sSearch+"%') "+ sOrder;
								"or c.customerId like '%"+sSearch+"%' "+
                				"or g.gateWayVersion like '%"+sSearch+"%') "+ sOrder;     
		List list = daoManager.listAskedObjects(query,start,length,GateWay.class);
		return list;
	}
	
	public List listAskedGateWayWithMaintance(String sSearch,String sOrder, int start, int length) {
		String query = "";
        query += "select g.id,g.macId,s.name" +
//        		",a.name from " +
        		",a.name,c.customerId,g.maintenancemode from " +
//                "GateWay as g join g.status as s left join g.agent as a " +
                "GateWay as g join g.status as s left join g.agent as a left join g.customer as c " +
//                "where (g.id like '%"+sSearch+"%' " +
                "where (g.macId like '%"+sSearch+"%' " +
                				"or s.name like '%"+sSearch+"%' " +
        						" or a.name like '%"+sSearch+"%' " +
                				"or c.customerId like '%"+sSearch+"%') "+sOrder;     
		List list = daoManager.listAskedObjects(query,start,length,GateWay.class);
		return list;
	}
	
	public int getTotalGateWayCount(String sSearch){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(g)" +
        		" from " +
                "GateWay as g join g.status as s left join g.agent as a " +
                "where (g.id like '%"+sSearch +"%' " +
                		"or g.macId like '%"+sSearch+"%' " +
                				"or s.name like '%"+sSearch+"%' " +
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
	public int getTotalGateWayCount(){
		int count =  daoManager.getCount(GateWay.class);
		return count;
	}
	public GateWay getGateWayById(long id) {
		return (GateWay) daoManager.get(id, GateWay.class);
	}
	@SuppressWarnings("unchecked")
	public GateWay getGateWayByDevice(Device device) {
		String generatedDeviceId = device.getGeneratedDeviceId();
		List<GateWay>gateWays = daoManager.list("devices", "generatedDeviceId", generatedDeviceId, GateWay.class);
		if(gateWays.size()<1){
			return null;
		}
		if(gateWays.size() < 1){
			return null;
		}
		GateWay gateWay = gateWays.get(0);
		return gateWay;
	}
	
	public GateWay updateCustomerOfGateWay(GateWay gateWay) {
		String macId = gateWay.getMacId();
		GateWay gateWayByMacId = getGateWayByMacId(macId);
		gateWayByMacId.setCustomer(gateWay.getCustomer());
		if(daoManager.update(gateWayByMacId)){
			return gateWayByMacId;
		}
		return null;
	}
	public GateWay updateCustomerOfGateWay(GateWay gateWay, Customer customer) {
		DBConnection dbc = null;
		GateWay gateWaysOne = new GateWay();
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(GateWay.class);
			Criterion criterion = Restrictions.eq("macId", gateWay.getMacId());
			criteria.add(criterion);
			gateWaysOne = (GateWay) criteria.uniqueResult();
			gateWaysOne.setCustomer(customer);
			session.update(gateWaysOne);
			tx.commit();
		}catch(Exception e){
			LogUtil.error("Error when retreiving gateways by customerid : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		return gateWaysOne;
	}
	@SuppressWarnings("unchecked")
	public Set<GateWay> getGateWaysOfCustomer(Customer customer) {
		Set<GateWay> gateWays = null;
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(GateWay.class);
			Criterion criterion = Restrictions.eq("customer", customer);
			criteria.add(criterion);
			List<GateWay> gateWaysList = criteria.list();
			for (GateWay gateWay : gateWaysList) {
				Hibernate.initialize(gateWay);
				gateWay.setCustomer(customer);
				gateWay.setAgent(null);
				gateWay.setDevices(null);
			}
			gateWays = new HashSet<GateWay>();
			gateWays.addAll(gateWaysList);
		}catch(Exception e){
			LogUtil.error("Error when retreiving gateways by customerid : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		return gateWays;
	}
	@SuppressWarnings("unchecked")
	public Set<Device> getDevicesOfGateWay(GateWay gateWay) {
		DBConnection dbc = null;
		List<Device> devices = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria crt = session.createCriteria(Device.class);
			Criterion curstomerRestriction = Restrictions.eq("gateWay", gateWay);
			crt.add(curstomerRestriction);
			devices = crt.list();
			for (Device device : devices) {
				device.setGateWay(gateWay);
				device.setMake(null);
			}
		}catch(Exception e){
			devices = new ArrayList<Device>();
			LogUtil.error("Error when retreiving gateways of customer : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		Set<Device> deviceSet = new HashSet<Device>(devices);
		return deviceSet;
	}
	public GateWay getGateWayWithAgentByMacId(String macId) {
//		Optimize this query with HQL.
		GateWay gateWay = null;
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(GateWay.class);
			Criterion criterion = Restrictions.eq("macId", macId);
			criteria.add(criterion);
			gateWay = (GateWay) criteria.uniqueResult();
			Hibernate.initialize(gateWay);
			Hibernate.initialize(gateWay.getAgent());
			gateWay.setDevices(null);
		}catch(Exception e){
			LogUtil.error("Error when retreiving gateway by macId : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		return gateWay;
	}
	public GateWay getGateWayWithAgentById(long id) {
		GateWay gateWay = null;
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			gateWay = (GateWay) session.get(GateWay.class, id);
			Hibernate.initialize(gateWay);
			Hibernate.initialize(gateWay.getAgent());
			gateWay.setDevices(null);
		}catch(Exception e){
			LogUtil.error("Error when retreiving gateway by Id : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		return gateWay;
	}
	@SuppressWarnings("unchecked")
	public List<GateWay> getGateWaysByCustomer(Customer customer) {
		return daoManager.list("customer", customer, GateWay.class);
	}
	
	public GateWay getGateWayByCustomer(Customer customer) {
		return (GateWay) daoManager.getOne("customer", customer, GateWay.class);
	}
	
	public void updateGateWayToOnline(long id) {
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			GateWay gateWay = (GateWay) session.get(GateWay.class, id);
			Status status = IMonitorUtil.getStatuses().get(Constants.ONLINE);
			gateWay.setStatus(status);
			Transaction tx = session.beginTransaction();
			session.update(gateWay);
			tx.commit();
		}catch(Exception e){
			LogUtil.error("Error when updating gateway to status online : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
	}
	public void updateGateWayToStatusDeviceDiscovery(String macId) {
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria crt = session.createCriteria(GateWay.class);
			crt.add(Restrictions.eq("macId", macId));
			GateWay gateWay = (GateWay) crt.uniqueResult();
			Status status = IMonitorUtil.getStatuses().get(Constants.DEVICE_DISCOVERY_MODE);
			gateWay.setStatus(status);
			Transaction tx = session.beginTransaction();
			session.update(gateWay);
			tx.commit();
		}catch(Exception e){
			LogUtil.error("Error when updating gateway to status device discovery. : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
	}
	
	public void updateGateWayToStatusremovalmode(String macId) {
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria crt = session.createCriteria(GateWay.class);
			crt.add(Restrictions.eq("macId", macId));
			GateWay gateWay = (GateWay) crt.uniqueResult();
			Status status = IMonitorUtil.getStatuses().get(Constants.GATEWAY_RECOVERY);
			gateWay.setStatus(status);
			Transaction tx = session.beginTransaction();
			session.update(gateWay);
			tx.commit();
		}catch(Exception e){
			LogUtil.error("Error when updating gateway to status device discovery. : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
	}
	public GateWay getCustomerIdWithReceiverIpAndPortAndFtp(String macId) {
		DBConnection dbc = null;
		GateWay gateWay = null;
		try {
			String query = "select c.customerId, ag.controllerReceiverPort, ag.ftpIp, ag.ftpPort, ag.ftpUserName, ag.ftpPassword, ag.ftpNonSecurePort" +
					" from GateWay as g" +
					" left join g.customer as c" +
					" left join g.agent as ag" +
					" where g.macId='" + macId + "'";
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Object[] uniqueResult = (Object[]) q.uniqueResult();
			String customerId = IMonitorUtil.convertToString(uniqueResult[0]);
			int controllerReceiverPort  = (Integer) uniqueResult[1];
			String  ftpIp = IMonitorUtil.convertToString(uniqueResult[2]);
			int ftpPort  = (Integer) uniqueResult[3];
			String ftpUserName  = IMonitorUtil.convertToString(uniqueResult[4]);
			String  ftpPassword = IMonitorUtil.convertToString(uniqueResult[5]);
			int ftpNonSecurePort  = (Integer) uniqueResult[6];
			
			gateWay  = new GateWay();
			gateWay.setMacId(macId);
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			gateWay.setCustomer(customer);
			Agent agent = new Agent();
			agent.setControllerReceiverPort(controllerReceiverPort);
			agent.setFtpIp(ftpIp);
			agent.setFtpPort(ftpPort);
			agent.setFtpNonSecurePort(ftpNonSecurePort);
			agent.setFtpUserName(ftpUserName);
			agent.setFtpPassword(ftpPassword);
			gateWay.setAgent(agent);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return gateWay;
	}
	public String getCustomerIdOfGateWay(String macId) {
		DBConnection dbc = null;
		String customerId = null;
		try {
			String query = "select c.customerId" +
					" from GateWay as g" +
					" left join g.customer as c" +
					" where g.macId='" + macId + "'";
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			customerId = IMonitorUtil.convertToString(q.uniqueResult());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return customerId;
	}
	public GateWay getGateWayWithAgent(String macId, String customerId) {
		LogUtil.info("getGateWayWithAgent= " + macId+""+customerId);
		String query = "";
		XStream stream = new XStream();
		query += "select g.id, g.macId, g.localIp, g.remarks," +
				" a.id, a.ip, a.controllerReceiverPort,g.SSID" +
				" from GateWay as g" +
				" left join g.agent as a" +
				" left join g.customer as c" +
				" where g.macId='" + macId + "' and c.customerId='" + customerId + "'";
		LogUtil.info("Query"+query);
		DBConnection dbc = null;
		Object[] objects;
		
		GateWay gateWay = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			objects = (Object[]) q.uniqueResult();
			
			Long id = (Long) objects[0];
			macId = (String) objects[1];
			String localIp = (String) objects[2];
			String remarks = (String) objects[3];
			
			Long aId = (Long) objects[4];
			String ip = (String) objects[5];
			Integer controllerReceiverPort = (Integer) objects[6];
			String ssid=(String) objects[7];
			
			Agent agent = new Agent();
			agent.setId(aId);
			agent.setIp(ip);
			agent.setControllerReceiverPort(controllerReceiverPort);
			
			gateWay = new GateWay();
			gateWay.setId(id);
			gateWay.setMacId(macId);
			gateWay.setLocalIp(localIp);
			gateWay.setRemarks(remarks);
			gateWay.setAgent(agent);
			gateWay.setSSID(ssid);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
		
		return gateWay;
	}
	
	@SuppressWarnings("unchecked")
	public List<GateWay> getGateWaysOfCustomer(String customerId) {
		List<GateWay> gateWays = null;
		String query = "";
		query += "select g.id, g.macId, g.localIp, g.remarks," +
				" a.id, a.ip, a.controllerReceiverPort" +
				" from GateWay as g" +
				" left join g.customer as c" +
				" left join g.agent as a" +
				" where c.customerId='" + customerId + "'";
		DBConnection dbc = null;
		List<Object[]> list = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			list = q.list();
			gateWays = new ArrayList<GateWay>();
			for (Object[] details : list) {
				Long id = (Long) details[0];
				String macId = (String) details[1];
				String localIp = (String) details[2];
				String remarks = (String) details[3];
				GateWay gateWay = new GateWay();
				gateWay.setId(id);
				gateWay.setLocalIp(localIp);
				gateWay.setMacId(macId);
				gateWay.setRemarks(remarks);
				Agent agent = new Agent();
				Long aId = (Long) details[4];
				String ip = (String) details[5];
				Integer controllerReceiverPort = (Integer) details[6];
				agent.setId(aId);
				agent.setIp(ip);
				agent.setControllerReceiverPort(controllerReceiverPort);
				gateWay.setAgent(agent);
				
				gateWays.add(gateWay);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.info("Exceptio nmssgs : "+ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return gateWays;
	}
	public boolean updateLocalIp(String macId, String imvgWanIp) {
		boolean result = false;
		DBConnection dbc = null;
		String hql = "update gateway g set g.localip='" + imvgWanIp + "'"+
				" where g.macId='" + macId + "'";
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(hql);
			Transaction tx = session.beginTransaction();
			q.executeUpdate();
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return result ;
	}
	public void SetMaintanceMode(GateWay gw, int i) {
		
		DBConnection dbc = null;
		String hql = "update gateway g set g.maintenancemode='" +i+ "'"+
				" where g.id='" + gw.getId() + "'";
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(hql);
			Transaction tx = session.beginTransaction();
			q.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
	}
	public void UpdateAllOnOffStates(GateWay gateWay) {
		DBConnection dbc = null;
		GateWay gateWaysOne = new GateWay();
		try{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(GateWay.class);
			Criterion criterion = Restrictions.eq("macId", gateWay.getMacId());
			criteria.add(criterion);
			gateWaysOne = (GateWay) criteria.uniqueResult();
			gateWaysOne.setAllOnOffStatus(gateWay.getAllOnOffStatus());
			session.update(gateWaysOne);
			tx.commit();
		}catch(Exception e){
			LogUtil.error("Error when retreiving gateways by customerid : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(null != dbc){
				dbc.closeConnection();
			}
		}
		
	}
	
	
	public String getIdOfGateway(String macid){
		DBConnection dbc = null;
		String result = null;
		try {
			String query = "select id" +
					" from GateWay as g" +
					" where g.macId='" + macid + "'";
			
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
	
	public String getmacidbycustomerid(long custid){
		DBConnection dbc = null;
		String macId = null;
		try {
			String query = "select macId from gateway where customer='"+custid+"'";
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			macId = (String) q.uniqueResult();
		
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return macId;
		
		
		
	}
	
public NewDeviceType getDeviceTypeBasedOnClass(String basicDeviceClass,String genericClass,String specificClass,String supportClass,String manufacturerId,String productId)
	{
		DBConnection dbc = null;
		NewDeviceType parameterList=new NewDeviceType();
		try {
			
			String query = "";
			query += "select * from newDeviceType as n where n.basicDeviceClass='"+basicDeviceClass+"' and n.genericDeviceClass='"+genericClass+"' and n.specificDeviceClass='"+specificClass+"'" +
					"and n.supportedClass='"+supportClass+"' and n.manufacturerId='"+manufacturerId+"' and n.productId='"+productId+"'";
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			if (objects!=null)
			{
				String id =objects[0].toString();
				Long lid=Long.parseLong(id);
				parameterList.setId(lid);
				//parameterList.setId((Long) objects[0]);
				parameterList.setDeviceType((String) objects[1]);
				parameterList.setBasicDeviceClass((String) objects[2]);
				parameterList.setGenericDeviceClass((String) objects[3]);
				parameterList.setSpecificDeviceClass((String) objects[4]);
				parameterList.setSupportedClass((String) objects[5]);
				parameterList.setManufacturerId((String) objects[6]);
				parameterList.setProductId((String) objects[7]);
				parameterList.setModelNumber((String) objects[8]);
				parameterList.setDeviceTypeNumber((String) objects[9]);
				
			}
			
			
		} catch (Exception ex) {
			LogUtil.info("Caught Exception  while getting IR Codes Count : ", ex);
		} finally {
			dbc.closeConnection();
		}
		
		return parameterList;
	}
	
	/*public boolean saveGatewayStatus(GatewayStatus status) {
		boolean result = false;
		try {
			daoManager.save(status);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}*/
	
public String getLastStatusOfGateway(GateWay gateway){
		
		String status = null;
		DBConnection dbc = null;
		try {
			String query = "select s.name" +
					" from GatewayStatus as gs" +
					" left join gs.status as s" +
					" left join gs.gateway as g" +
					" where g.id='" + gateway.getId() + "' order by g.id desc limit 1";
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			status = IMonitorUtil.convertToString(q.setMaxResults(1).uniqueResult());
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("exeception while updating gateway UP/DOWN : " + e.getMessage());
		}finally {
			dbc.closeConnection();
		}
		
		return status;
		
	}	

public boolean saveGatewayStatus(GatewayStatus status) {
	boolean result = false;
	try {
		daoManager.save(status);
		result = true;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}
public GateWay getCustomerWithReceiverIpAndPortAndFtp(String macId) {
	DBConnection dbc = null;
	GateWay gateWay = null;
	try {
		String query = "select c.customerId, ag.controllerReceiverPort, ag.ftpIp, ag.ftpPort, ag.ftpUserName, ag.ftpPassword, ag.ftpNonSecurePort,c.id" +
				" from GateWay as g" +
				" left join g.customer as c" +
				" left join g.agent as ag" +
				" where g.macId='" + macId + "'";
		
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		Object[] uniqueResult = (Object[]) q.uniqueResult();
		String customerId = IMonitorUtil.convertToString(uniqueResult[0]);
		int controllerReceiverPort  = (Integer) uniqueResult[1];
		String  ftpIp = IMonitorUtil.convertToString(uniqueResult[2]);
		int ftpPort  = (Integer) uniqueResult[3];
		String ftpUserName  = IMonitorUtil.convertToString(uniqueResult[4]);
		String  ftpPassword = IMonitorUtil.convertToString(uniqueResult[5]);
		int ftpNonSecurePort  = (Integer) uniqueResult[6];
		Long id = (Long) uniqueResult[7];
		gateWay  = new GateWay();
		gateWay.setMacId(macId);
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		customer.setId(id);
		gateWay.setCustomer(customer);
		Agent agent = new Agent();
		agent.setControllerReceiverPort(controllerReceiverPort);
		agent.setFtpIp(ftpIp);
		agent.setFtpPort(ftpPort);
		agent.setFtpNonSecurePort(ftpNonSecurePort);
		agent.setFtpUserName(ftpUserName);
		agent.setFtpPassword(ftpPassword);
		gateWay.setAgent(agent);
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return gateWay;
}

public boolean saveGateWayMacIdinList(ZingGatewayList gateWay) {
	boolean result=false;
	try{
		daoManager.save(gateWay);
		result=true;
	}catch(Exception e){
		e.printStackTrace();
	}return result;
}


public boolean verifyGetwayMacIdFromSavedList(String macId) {
	
	boolean result=false;
	ZingGatewayList gateWay = (ZingGatewayList) daoManager.getOne("macId", macId, ZingGatewayList.class);
	if(gateWay != null) {
		result = true;
	}
	
	return result;
}

//3gp 26 Sep
@SuppressWarnings("unchecked")
public List<GateWay> getGateWaysOfCustomerForRenaming(String customerId) {
	List<GateWay> gateWays = null;
	String query = "";
	query += "select g.id, g.macId, g.localIp, g.remarks," +
			" a.id, a.ip, a.controllerReceiverPort,g.name " +
			" from GateWay as g" +
			" left join g.customer as c" +
			" left join g.agent as a" +
			" where c.customerId='" + customerId + "'";
	
	DBConnection dbc = null;
	List<Object[]> list = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		list = q.list();
		gateWays = new ArrayList<GateWay>();
		for (Object[] details : list) {
			Long id = (Long) details[0];
			String macId = (String) details[1];
			String localIp = (String) details[2];
			String remarks = (String) details[3];
			String gname = (String) details[7];
			GateWay gateWay = new GateWay();
			gateWay.setId(id);
			gateWay.setLocalIp(localIp);
			gateWay.setMacId(macId);
			gateWay.setRemarks(remarks);
			gateWay.setName(gname);
			Agent agent = new Agent();
			Long aId = (Long) details[4];
			String ip = (String) details[5];
			Integer controllerReceiverPort = (Integer) details[6];
			agent.setId(aId);
			agent.setIp(ip);
			agent.setControllerReceiverPort(controllerReceiverPort);
			gateWay.setAgent(agent);
			
			gateWays.add(gateWay);
		}
	} catch (Exception ex) 
	{
		ex.printStackTrace();
		LogUtil.info("Exceptio nmssgs : "+ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	return gateWays;
}

//Display zing gateway list
@SuppressWarnings("unchecked")
public List listAskedZingGatewayList(String sSearch, String sOrder, int start,
		int length)
{
	List list = null;
	try 
	{
		String query = "";
		query += "select g.id,g.macId,g.description,g.entryDate,g.serialNumber,g.batchNumber"
				+ " from "
				+ "ZingGatewayList as g "
				+ "where (" + "g.macId like '%"
				+ sSearch + "%' ) " + sOrder;
		
		list = daoManager.listAskedObjects(query, start, length,
				ZingGatewayList.class);
		XStream stream=new XStream();
	} 
	catch (Exception e)
	{
		e.printStackTrace();
		LogUtil.info("Exception : "+e.getMessage());
	}
	
	return list;
}

public int getTotalZingGatewaysCount(String sSearch) {
	DBConnection dbc = null;
	int count = 0;
	try {
		String query = "";
		query += "select count(g)" + " from "
				+ "ZingGatewayList as g  "
				+ "where (" + "g.macId like '%"
				+ sSearch + "%' ) " ;
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

public ZingGatewayList getZingGatewayById(long id) {
	return (ZingGatewayList) daoManager.get(id, ZingGatewayList.class);
}

public boolean deleteZingGateway(long id) {
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		String hql =  "delete from ZingGatewayList where id ="+id;
		Session session = dbc.openConnection();
		dbc.beginTransaction();
		Query query = session.createQuery(hql);
	    int rowCount = query.executeUpdate();
	    dbc.commit();
	    return (rowCount>0)?true:false;
	}catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.info("Errrrror : " + ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	return false;
}


	
}
