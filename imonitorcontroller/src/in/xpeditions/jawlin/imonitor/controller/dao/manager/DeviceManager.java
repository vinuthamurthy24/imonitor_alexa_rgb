/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Action;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertsFromImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DoorLockConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Functions;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.H264CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgLog;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LCDRemoteConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LocationMap;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MinimoteConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ModbusSlave;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MotorConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.NetworkStats;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.PushNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Role;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UploadsByImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.WallmoteConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.dashboardconfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.energyInformationFromImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.instantpow;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.powerInformationFromImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserDeviceAccess;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.dao.util.dashboardDBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

//import sun.org.mozilla.javascript.internal.ast.NewExpression;


import com.thoughtworks.xstream.XStream;

public class DeviceManager {
	DaoManager daoManager = new DaoManager();

	public boolean saveDevice(Device device) {
		boolean result = false;
		try {
			if (device.getCommandParam() == null) {
				device.setCommandParam("0");
			}
			if (daoManager.save(device)) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// ************************************************************* sumit start ************************************************************
	
	// ***************************************************** Re-Ordering of Devices: start **************************************************
	public void savePositionIndexForDevices(List<String> ListOfDeviceIds){
		String query = "";
		
		try {
			for(int i=0; i<ListOfDeviceIds.size(); i++){
				
				query = "update device d set d.posIdx=" + (i+1) + " where d.id="+ Long.parseLong(ListOfDeviceIds.get(i)) +"";
			
				daoManager.executeUpdateQuery(query);
			}
		} catch (HibernateException e) {
			LogUtil.error("1.Error while saving position for devices:"+e.getMessage());
		} catch (NumberFormatException e) {
			LogUtil.error("2.Error while saving position for devices:"+e.getMessage());
		}	
	}
	
	public String getMaxPositionIndexBasedOnLocation(String locationName){	//Currently called only when a new device is added
		String posIdx = "";													//to set appropriate position index for default location.
		DBConnection dbc = null;											//Later can be used for other locations also.
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select max(posIdx) from device as d, location as l, customer as c"
					+" where d.location=l.id"
					+" and l.name='"+locationName+"' ";
			Query q = session.createSQLQuery(query);
			posIdx = IMonitorUtil.convertToString(q.uniqueResult());
			if(posIdx.isEmpty()){
				posIdx = "0";
			}
		} catch (HibernateException e) {
			LogUtil.error("1.Error while getting required field: "+ e.getMessage());
		} catch (NullPointerException e) {
			LogUtil.error("2.Error while getting required field: "+ e.getMessage());
		} catch (NumberFormatException e) {
			LogUtil.error("3.Error while getting required field: "+ e.getMessage());
		}finally {
			dbc.closeConnection();
		}
		return posIdx;
	}
	// ***************************************************** Re-Ordering of Devices: end **************************************************
	
	public long getIdByGeneratedDeviceId(String generateddeviceId){
		Long id = new Long(0);
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select d.id from Device as d where d.generatedDeviceId='"+generateddeviceId+"'";
			Query q = session.createQuery(query);
			id = (Long) q.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			LogUtil.error("ERROR while getting Id by GeneratedDeviceId");
		}finally{
			dbc.closeConnection();
		}
		return id.longValue();
	}
	
	public Device getNoMotionTimeOutByGeneratedDeviceId(long id,String generatedDeviceId, String macId){
		DBConnection dbc = null;
		Device device = new Device();
		try {
			String query = "select d.modelNumber, d.friendlyName from "
				+ " Device as d left join d.gateWay as g where d.generatedDeviceId='" + generatedDeviceId
				+ "' and g.macId='" + macId +"' ";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Object[] object = (Object[]) q.uniqueResult();
			device.setModelNumber(IMonitorUtil.convertToString(object[0]));
			device.setFriendlyName(IMonitorUtil.convertToString(object[1]));
			device.setGeneratedDeviceId(generatedDeviceId);
			
			GateWay gateWay = new GateWay();
			gateWay.setMacId(macId);
			
			device.setId(id);
			device.setGateWay(gateWay);
		
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return device;
	}
	// *************************************************************** sumit end ************************************************************
	
	//Added to retrieve device's friendly name based on the deviceId
	public static List<String> getDeviceFriendlyNameByDeviceId(String macId, List<String> deviceId){
		List<String> friendlyName = new ArrayList<String>();
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			for(String dId : deviceId){
				String query = "select d.friendlyName"
						+ " from Device as d"
						+ " where d.deviceId='"+dId+"'";
				Query q = session.createQuery(query);
				String fName = q.uniqueResult().toString();
				friendlyName.add(fName);
			}
		}catch(Exception ex){
			LogUtil.error("Error while retrieving friendlyName by deviceId");
			ex.printStackTrace();
		}finally{
			dbc.closeConnection();	
		}
		return friendlyName;
	}
	
	public void updateCurrentModeForCustomer(String macId, String statusName) {
		DBConnection dbc = null;
		int curMode = 2;
		if(statusName.equalsIgnoreCase("DISARM")){
			curMode = 0;
		}else if(statusName.equalsIgnoreCase("STAY")){
			curMode = 1;
		}
		try {
			
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String hql =  "update gateway g set g.currentMode=" + curMode
							+ " where g.macId ='"+macId+"'";
			
			
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

	public boolean updateArmStatusForAllDevicesOfCustomer(String macId, String statusName ){
		boolean result = false;
		DBConnection dbc = null;		
		String query = null;
		String s_query = null;

		/*boolean testStay1 = false;
		boolean testStay2 = false;*/
		
		try 
		{
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();

			if(statusName.equalsIgnoreCase("DISARM"))
			{	
				query = "update device d, gateway g, status s "
						+ " set d.armStatus =s.id"
						+ " where g.macId='" + macId + "'"
						+ " and d.gateway=g.id and substr(d.mode,1,1)='1' and s.name='"+statusName+"'";
				Query q = session.createSQLQuery(query);
				Transaction tx = session.beginTransaction();
				q.executeUpdate();
				tx.commit();
				result = true;
			}
			else if(statusName.equalsIgnoreCase("STAY"))
			{

				query  ="update device d, gateway g, status s "
						+ " set d.armStatus =s.id"
						+ " where g.macId='" + macId + "'"
						+ " and d.gateway=g.id and substr(d.mode,1,1)='1' and s.name='DISARM'";
				
				Query q = session.createSQLQuery(query);



				s_query  ="update device d, gateway g, status s "
						+ " set d.armStatus =s.id"
						+ " where g.macId='" + macId + "'"
						+ " and d.gateway=g.id and substr(d.mode,2,1)='1' and s.name='ARM'";
				Query q1 = session.createSQLQuery(s_query);
				
				
				
				Transaction tx = session.beginTransaction();
				q.executeUpdate();
				q1.executeUpdate();
				tx.commit();

				result = true;
				
			}
			else if(statusName.equalsIgnoreCase("ARM"))
			{
				query = "update device d, gateway g, status s "
						+ " set d.armStatus =s.id"
						+ " where g.macId='" + macId + "'"
						+ " and d.gateway=g.id and substr(d.mode,3,1)='1' and s.name='"+statusName+"'";
				Query q = session.createSQLQuery(query);
				Transaction tx = session.beginTransaction();
				q.executeUpdate();
				tx.commit();
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		} finally {
			dbc.closeConnection();
		}
		

		return result;
	}
	
	public boolean saveModeWithAllDetails(List<Device> devices) {
		boolean result = false;
			List<Device> deviceListWithDelay = devices;
			//UPDATE GATEWAY
			Device firstDevice = devices.get(0);
			GateWay gateWay = firstDevice.getGateWay();
			long g_id = gateWay.getId();
			boolean test_g = false;
			boolean test_d = false; 
			String query_g = "update gateway"
					+ " set delayHome = " + gateWay.getDelayHome()
					+ " , delayStay = " + gateWay.getDelayStay() 
					+ " , delayAway = " + gateWay.getDelayAway()
					+ " where id =" + g_id ;
			test_g = daoManager.executeUpdateQuery(query_g);
			

			//UPDATE DEVICE		
			for(Device device: deviceListWithDelay){
				String mode = device.getMode();
				String currentmode = device.getCurrentMode();
				String generatedDeviceId = device.getGeneratedDeviceId();
				test_d=true;
				if(!mode.equalsIgnoreCase(currentmode)){
					String query_d = "update device"
							+ " set mode = '" + mode +"'"
							+ " where generatedDeviceId = '" + generatedDeviceId+"'";
					test_d = daoManager.executeUpdateQuery(query_d);
//					Hibernate.initialize(device);
//					device.setMode(mode);
//					session.update(device);
				}
			}
			if(test_g && test_d){
				result = true;
			}
		return result;
	}

	@SuppressWarnings("unchecked")
	public boolean saveDeviceWithDefaultLocation(Device device) {
		boolean result = false;
		DBConnection dbConnection = new DBConnection();
		try {
			Location location = null;
			
			Session session = dbConnection.openConnection();
			Criteria criteria = session.createCriteria(Location.class);
			Criterion crn = Restrictions.eq("customer", device.getGateWay()
					.getCustomer());
			criteria.add(crn);
			//sumit start: assign default Room to device.
			//criteria.setMaxResults(1);
			List<Location> locations = criteria.list();
			for(Location loc: locations){
				String locName = loc.getName();
				if(locName.equalsIgnoreCase(Constants.LIVING_ROOM)){
					location = loc;
				}
			}
			//location = locations.get(0);
			//sumit end: assign default Room to device.
			device.setLocation(location);
			Transaction tx = session.beginTransaction();
			session.save(device);
			tx.commit();
			result = true;
		} catch (Exception e) {
			LogUtil.error("Error when saving device with generateid id : "
					+ device.getGeneratedDeviceId() + e.getMessage());
			e.printStackTrace();
		}finally {
			dbConnection.closeConnection();
		}
		return result;
	}

	public boolean deleteDevice(Device device) {
		boolean result = false;
		try {
			daoManager.delete(device);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateDevice(Device device) {
		boolean result = false;
		try {
			daoManager.update(device);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Device> listOfDevices() {
		List<Device> devices = new ArrayList<Device>();
		try {
			devices = (List<Device>) daoManager.list(Device.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devices;

	}

	@SuppressWarnings("unchecked")
	public List<Device> getDeviceByGateWay(GateWay gateWay) {
		List<Device> devices = daoManager
				.list("gateWay", gateWay, Device.class);
		return devices;
	}

	@SuppressWarnings("unchecked")
	public List<Device> getDevicesWithAlertTypeAndActionTypeByCustomer(
			Customer customer) {
		List<Device> devices = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(Device.class);
			Criterion criterion = Restrictions.eq("gateWay.customer", customer);
			criteria.add(criterion);
			List<Device> gateWays = criteria.list();

			DBConnection dbConnection = new DBConnection();
			Session sess = dbConnection.openConnection();
			Criteria crt = sess.createCriteria(Device.class);
			Criterion crtn = Restrictions.in("gateWay", gateWays);
			crt.add(crtn);
			devices = crt.list();

			for (Device device : devices) {
				Hibernate.initialize(device);
				Hibernate.initialize(device.getDeviceType());
				Hibernate.initialize(device.getDeviceType().getActionTypes());
				Hibernate.initialize(device.getDeviceType().getAlertTypes());
				device.setLocation(null);
				device.setGateWay(null);
				device.setDeviceConfiguration(null);
			}
		} catch (Exception e) {
			LogUtil
					.error("1.Error when retreiving device by generatedDeviceId : "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return devices;
	}

	public MotorConfiguration getMotorConfigurationByDeviceId(String generatedDeviceId)
	{		
		DBConnection dbc = null;
		MotorConfiguration motorConfig = null;
	try {
		String query = "select m from "
				+ " Device as d join d.deviceConfiguration as m "
				+ " where d.generatedDeviceId='" + generatedDeviceId+"'";
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		motorConfig = (MotorConfiguration) q.uniqueResult();

	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return motorConfig;
	}
	
	public Device getDeviceWithGateWayAndAgentByGeneratedDeviceId(
			String generatedDeviceId) {
		Device device = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(Device.class);
			Criterion criterion = Restrictions.eq("generatedDeviceId",
					generatedDeviceId);
			criteria.add(criterion);
			device = (Device) criteria.uniqueResult();
			if (device != null) {
				Hibernate.initialize(device);
				Hibernate.initialize(device.getGateWay());
				if(device.getGateWay() != null)device.getGateWay().setAlertDevice(null);
				Hibernate.initialize(device.getGateWay().getAgent());
				Hibernate.initialize(device.getDeviceType());
				Hibernate.initialize(device.getIcon());
				// Hibernate.initialize(device.getGateWay().getCustomer());
				Hibernate.initialize(device.getLocation());				
				Hibernate.initialize(device.getEnableList());			//sumit start: Enable/Disable Listing of device
				Hibernate.initialize(device.getDeviceConfiguration());	//sumit start: Get PIRConfiguration if its a ZXT120
				Hibernate.initialize(device.getModelNumber());
				device.getGateWay().setDevices(null);
				device.getGateWay().setCustomer(null);
				//device.setDeviceConfiguration(null);
				device.getLocation().setCustomer(null);
				device.setMake(null);
				device.setLocationMap(null);
				
				device.getDeviceType().setActionTypes(null);
				device.getDeviceType().setAlertTypes(null);
			}
		} catch (Exception e) {
			LogUtil
					.error("2.Error when retreiving device by generatedDeviceId : "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}

	public Device getDevice(long id) {
		Device device = (Device) daoManager.get(id, Device.class);
		return device;
	}

	public Make getMake(long id) {
		Make make = (Make) daoManager.get(id, Make.class);
		return make;

	}	
	public MotorConfiguration getMotorConfiguration(long id) {
		MotorConfiguration motorconfig = (MotorConfiguration) daoManager.get(id, MotorConfiguration.class);
		return motorconfig;

	}

	public Device getDeviceById(long id) {
		DBConnection dbc = null;
		Device device = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			device = (Device) dbc.getSession().get(Device.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return device;

	}

	public void updateDeviceByQuerry(long id, Location location,
			String friendlyName) {
		String query = "";
		query += "update device as d set d.friendlyName=" + friendlyName
				+ ",d.location=" + location + "where d.id=" + id;
		daoManager.updateByQuerry(query, Device.class);
	}

	public Device getDeviceByGeneratedDeviceId(String generatedDeviceId) {
		XStream stream=new XStream();
		Device device = null;
		DBConnection dbc = null;
		try {
			
			dbc = new DBConnection();
			dbc.openConnection();
			
			Session session = dbc.getSession();
			
			Criteria criteria = session.createCriteria(Device.class);
			
			Criterion criterion = Restrictions.eq("generatedDeviceId",generatedDeviceId);
			
			criteria.add(criterion);
			device = (Device) criteria.uniqueResult();
			
			
			
			Hibernate.initialize(device);
			
			Hibernate.initialize(device.getDeviceConfiguration());
			
			Hibernate.initialize(device.getDeviceType());
			
			Hibernate.initialize(device.getDeviceType().getActionTypes());
			
			Hibernate.initialize(device.getDeviceType().getAlertTypes());
			
	
			
		} catch (Exception e) {
			LogUtil
					.error("3.Error when retreiving device by generatedDeviceId : "+generatedDeviceId+""
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		
		return device;
	}
	
	
	public String getDeviceByfriendlyName(String friendlyName,long gatwyid) {
		XStream stream=new XStream();
		String result=null;
		DBConnection dbc = null;
		String query="select generatedDeviceId from device where friendlyName='"+friendlyName+"' and gateway='"+gatwyid+"'";
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(query);
			result= (String) q.uniqueResult();	
		} catch (Exception e) {
			LogUtil
					.error("3.Error when retreiving device by friendlyname : "+friendlyName+""
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return result;
	}
	public Device getDeviceByGenerateIdAndUpdateStatus(
			String generatedDeviceId, String gateWayId, Status status) {
		DBConnection dbc = null;
		String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), a.ip, a.controllerReceiverPort from "
				+ " Device as d left join d.deviceType as dt left join d.location as l left join d.lastAlert as la left join d.rules as r left join d.make as m" +
						" left join d.gateWay as g" +
						" left join g.agent as a"
				+ " where d.generatedDeviceId= '"
				+ generatedDeviceId
				+ "' and g.macId ='" + gateWayId + "'";
		Device device = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createQuery(query);
			Object[] object = (Object[]) q.uniqueResult();
			device = (Device) object[0];
			device.setArmStatus(status);
			Transaction tx = session.beginTransaction();
			session.update(device);
			tx.commit();

			DeviceType deviceType = new DeviceType();
			deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
			deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
			device.setDeviceType(deviceType);

			Location location = new Location();
			location.setName(IMonitorUtil.convertToString(object[3]));
			device.setLocation(location);

			AlertType alertType = new AlertType();
			alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
			device.setLastAlert(alertType);

			int count = Integer.parseInt(IMonitorUtil
					.convertToString(object[5]));
			if (count > 0) {
				device.setRules(new ArrayList<Rule>());
			}

			String ip = (String) object[6];
			Integer port = (Integer)object[7];
			GateWay gateWay = new GateWay();
			gateWay.setMacId(gateWayId);
			Agent agent = new Agent();
			agent.setIp(ip);
			agent.setControllerReceiverPort(port);
			gateWay.setAgent(agent);
			device.setGateWay(gateWay);
			device.setMake(null);
			device.setLocationMap(null);
			device.setDeviceConfiguration(null);

		} catch (Exception e) {
			LogUtil
					.error("4.Error when retreiving device by generatedDeviceId : "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}

	public Device getDeviceWithRulesByGeneratedDeviceId(String generatedDeviceId) {
		Device device = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(Device.class);
			Criterion criterion = Restrictions.eq("generatedDeviceId",
					generatedDeviceId);
			criteria.add(criterion);
			device = (Device) criteria.uniqueResult();
			if (device != null) {
				Hibernate.initialize(device);
				Hibernate.initialize(device.getRules());
			}
		} catch (Exception e) {
			LogUtil
					.error("5.Error when retreiving device by generatedDeviceId : "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}

	//sumit start: Camera Orientation Configuration Feature
	public Device getCameraConfiguration(long id, String generatedDeviceId){//, String macId){
		
		DBConnection dbc = null;
		Device device = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(Device.class);
			Criterion criterion = Restrictions.eq("generatedDeviceId", generatedDeviceId);
			criteria.add(criterion);
			device = (Device) criteria.uniqueResult();
			Hibernate.initialize(device);
			Hibernate.initialize(device.getDeviceConfiguration());
			Hibernate.initialize(device.getModelNumber());
			Hibernate.initialize(device.getFriendlyName());
//			Hibernate.initialize(device.getMode());
//			Hibernate.initialize(device.getModeHome());
//			Hibernate.initialize(device.getModeStay());
//			Hibernate.initialize(device.getModeAway());
		} catch (HibernateException e) {
			LogUtil.error("Error while retrieving Camera Orientation details. "+e.getMessage());
		} finally{
			if(dbc != null){
				dbc.closeConnection();
			}
		}
		
		return device;
	}
	//sumit end: Camera Orientation Configuration Feature
	
	public Device getDeviceWithConfiguration(String generatedDeviceId) {
		Device device = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(Device.class);
			Criterion criterion = Restrictions.eq("generatedDeviceId",generatedDeviceId);
			criteria.add(criterion);
			device = (Device) criteria.uniqueResult();
			Hibernate.initialize(device);
			Hibernate.initialize(device.getDeviceConfiguration());
			Hibernate.initialize(device.getDeviceType());
			//sumit added 1 line to avoid Exception Encounter.
			Hibernate.initialize(device.getGateWay());
		} catch (Exception e) {
			LogUtil
					.error("6.Error when retreiving device by generatedDeviceId : "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}
	public Device getDevice(String generatedDeviceId) {
		Device device = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(Device.class);
			Criterion criterion = Restrictions.eq("generatedDeviceId",generatedDeviceId);
			criteria.add(criterion);
			device = (Device) criteria.uniqueResult();
			Hibernate.initialize(device);
			Hibernate.initialize(device.getDeviceConfiguration());
		//	Hibernate.initialize(device.getDeviceType());
		} catch (Exception e) {
			LogUtil
					.error("7.Error when retreiving device by generatedDeviceId : "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}
	@SuppressWarnings("unchecked")
	public List<?> listDevicesUnderGatewayByMacId(String sSearch,String sOrder, int start,
			int length, String macId) {
		String query = "";
		query += "select d.id, d.generatedDeviceId, d.friendlyName, dt.name, l.name, g.macId from "
				+ " Device as d join d.deviceType as dt join d.location as l join d.gateWay as g "
				+ " where g.macId='" + macId
//vibhu start
				//+ "' and (d.generatedDeviceId like '%" + sSearch + "%'	"
				//+ "or d.friendlyName like '%" + sSearch + "%'	"
				+ "' and (d.friendlyName like '%" + sSearch + "%'	"
//vibhu end
				+ "or dt.name like '%" + sSearch + "%' "
				+ "or l.name like '%" + sSearch + "%' "
//vibhu start				
				//+ "or g.macId like '%" + sSearch + "%' ) "+ sOrder;
				+ ") "+ sOrder;
//vibhu end				

		List list = daoManager.listAskedObjects(query, start, length,
				Device.class);
		return list;
	}
	
	public List<?> listLogsUnderGatewayById(String sSearch,String sOrder, int start,
			int length, long Id) {
		String query = "";
        query += "select l.id,l.filename,l.filepath" +
        		",l.uploadeddate, g.id from " +
                "ImvgLog as l join l.gateWay as g " +
//vibhu start
                //"where l.gateWay= '" +Id+"' and (l.id like '%"+sSearch+"%' " +
            	//"or l.filename like '%"+sSearch+"%' " +
                	"where l.gateWay= '" +Id+"' and (l.filename like '%"+sSearch+"%' " +
//vibhu end
    				"or l.filepath like '%"+sSearch+"%' " +
    				"or l.uploadeddate like '%"+sSearch+"%' " +
//vibhu start    				
    				//" or g.id like '%"+sSearch+"%' ) "+sOrder;
        			"  ) "+sOrder;
//vibhu end        
		List list = daoManager.listAskedObjects(query,start,length,ImvgLog.class);
		return list;
	}

	public int getTotalDeviceCount(String sSearch, String macId) {
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "select count(d) from "
					+ " Device as d join d.deviceType as dt join d.location as l join d.gateWay as g "
					+ " where g.macId='" + macId
//vibhu start
					//+ "' and (d.generatedDeviceId like '%" + sSearch + "%'	"
					//+ "or d.friendlyName like '%" + sSearch + "%'	"
					+ "' and (d.friendlyName like '%" + sSearch + "%'	"
//vibhu end
					+ "or dt.name like '%" + sSearch + "%' "
					+ "or l.name like '%" + sSearch + "%' "
//vibhu start					
					//+ "or g.macId like '%" + sSearch + "%' )";
					+ ")";
//vibhu end
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

	public int getTotalDeviceCountByGateWay(String macId){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "select count(d) from "
				+ " Device as d join d.deviceType as dt join d.location as l join d.gateWay as g "
				+ " where g.macId='" + macId+"'" ;
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
	//bhavya start
	
	@SuppressWarnings("unchecked")
	public List<Device> listdevicefromdevicetype(String devicetype,long customerid) {
		DBConnection dbc = null;
		List<Device> devices = null;
		try {
			String query = "";
	        query +="select d.id,d.friendlyName,d.generatedDeviceId,dt.details,g.id,c.id,c.customerId from customer as c,gateway as g,device as d,devicetype as dt where g.customer='"+ customerid + "' and g.customer=c.id and d.gateWay=g.id and d.deviceType=dt.id and dt.details='"+ devicetype +"'";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			List<Object[]> list = q.list();
			devices = new ArrayList<Device>();
			for (Object[] object : list) {
				Device device = new Device();
				device.setId(((BigInteger)object[0]).longValue());
				device.setFriendlyName(IMonitorUtil.convertToString(object[1]));
				device.setGeneratedDeviceId(IMonitorUtil.convertToString(object[2]));
				DeviceType deviceType = new DeviceType();
				deviceType.setDetails(IMonitorUtil.convertToString(object[3]));
				device.setDeviceType(deviceType);
				GateWay gateway=new GateWay();
				gateway.setId(((BigInteger)object[4]).longValue());
				Customer customer=new Customer();
				customer.setId(((BigInteger)object[5]).longValue());
				customer.setCustomerId(IMonitorUtil.convertToString(object[6]));
				devices.add(device);
			}
		} catch (Exception ex) {
			LogUtil.info("",ex);
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return devices;
	}

	//bhavya end
	
	//IconBug Resolved here
	public Device updateCommadParamOfDeviceByGeneratedId(
			String generatedDeviceId, String gateWayId, String commandParam) {
		DBConnection dbc = null;
		dashboardDBConnection DashBoard = null;
		//String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r) from "
		String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), i.fileName, ast.id, ast.name from "
				+ " Device as d left join d.deviceType as dt"
				+ " left join d.location as l"
				+ " left join d.lastAlert as la"
				+ " left join d.rules as r"
				+ " left join d.icon as i"
				+ " left join d.armStatus as ast" 
				+ " where d.generatedDeviceId= '"
				+ generatedDeviceId
				+ "' and d.gateWay.macId ='" + gateWayId + "'";
		Device inDevice = null;
		Device device = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createQuery(query);
			Object[] object = (Object[]) q.uniqueResult();
			inDevice = (Device) object[0];
			inDevice.setCommandParam(commandParam);
			Transaction tx = session.beginTransaction();
			session.update(inDevice);
			tx.commit();

			// We are creating new device to avoid the hibernate proxy mapping
			// problem.
			device = new Device();
			device.setId(inDevice.getId());
			device.setDeviceId(inDevice.getDeviceId());
			device.setFriendlyName(inDevice.getFriendlyName());
			device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
			device.setBatteryStatus(inDevice.getBatteryStatus());
			device.setCommandParam(inDevice.getCommandParam());
			device.setModelNumber(inDevice.getModelNumber());
			device.setEnableStatus(inDevice.getEnableStatus());
			device.setDeviceConfiguration(inDevice.getDeviceConfiguration());
			DeviceType deviceType = new DeviceType();
			deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
			deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
			device.setDeviceType(deviceType);
			Location location = new Location();
			location.setName(IMonitorUtil.convertToString(object[3]));
			device.setLocation(location);
            device.setSwitchType(inDevice.getSwitchType());
			AlertType alertType = new AlertType();
			alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
			device.setLastAlert(alertType);

			Status armStatus = null;
			Long armStatusId = (Long) object[7];
			if(armStatusId != null){
				armStatus = new Status();
				String armStatusName = (String) object[8];
				armStatus.setId(armStatusId);
				armStatus.setName(armStatusName);
			}
			device.setArmStatus(armStatus);
			
			int count = Integer.parseInt(IMonitorUtil
					.convertToString(object[5]));
			if (count > 0) {
				device.setRules(new ArrayList<Rule>());
			}
			
			//Icon Bug
			//device.getIcon().setFileName(inDevice.getIcon().getFileName());
			device.setIcon(inDevice.getIcon());
			if(device.getIcon() != null) device.getIcon().setFileName(IMonitorUtil.convertToString(object[6]));
			
			if(device.getModelNumber().equals("HMD"))
			{
				String HMDquery = "select a.alertTime,a.id,a.alertValue,a.device,a.alertTypeName from instantpowerinformation as a where a.device="+ device.getId() +" and a.alertTypeName='POWER_INFORMATION' order by a.alertTime desc limit 1";
				
				DashBoard = new dashboardDBConnection();
				q = DashBoard.getSession().createSQLQuery(HMDquery);
				Object[] Hmdobjects = (Object[])q.uniqueResult();
				DashBoard.closeConnection();
				
				
			//Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(device.getGeneratedDeviceId());
		
			if(Hmdobjects != null && Hmdobjects[2]!= null)
			{
			
				device.setHMDalertValue((""+(String)Hmdobjects[2]));
				device.setHMDpowerinfo(""+(String)Hmdobjects[4]);
			
			}
			else
			{
				device.setHMDalertValue("");
				device.setHMDpowerinfo("");
			
			}
			}
			

		} catch (Exception e) {
			LogUtil
					.error("8.Error when retreiving device by generatedDeviceId : "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}

	@SuppressWarnings("unchecked")
	public boolean updateDevicesArmStatusByCustomerId(Customer customer,
			Status status) {
		String query = "select d from Device d left join d.gateWay g where g.customer ="
				+ customer.getId() + " and d.rules.size >0";

		List<Device> devices = null;
		DBConnection dbc = null;
		Boolean result=false;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			devices = q.list();
			Transaction tx = session.beginTransaction();
			for (Device device : devices) {
				device.setArmStatus(status);
				session.update(device);
			}
			tx.commit();
			result=true;
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public boolean updateDevicesArmStatusByCustomerId(String customerId,
			Status status) {
		String query = "select d from Device d left join d.gateWay g "
				+ " left join g.customer as c" + " where c.customerId = '"
				+ customerId + "' and d.rules.size >0";
		boolean result = false;
		List<Device> devices = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			devices = q.list();
			Transaction tx = session.beginTransaction();
			for (Device device : devices) {
				device.setArmStatus(status);
				session.update(device);
			}
			tx.commit();
			result=true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return result ;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> listAlertTypesOfDevicesByCustomerId(String customerId) {
		String query = "select d.id, d.generatedDeviceId, d.friendlyName, dt.name"
				+ " , altp.id, altp.name"
				+ " from Device d"
				+ " left join d.gateWay g"
				+ " left join d.deviceType dt"
				+ " left join dt.alertTypes as altp"
				+ " where d.deviceType=dt.id" + " and dt.alertTypes.id=altp.id"
				// + " and altp.alertCommand not in ('"
				// + Constants.DEVICE_DOWN
				// + "','"
				// + Constants.DEVICE_UP
				// + "')"
				+ " and g.customer.id=" + customerId;

		List<Object[]> objects = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> listActionTypesOfDevicesByCustomerId(String customerId) {
		String query = "select d.id, d.generatedDeviceId, d.friendlyName, dt.name"
				+ " , actp.id, actp.name"
				+ " from Device d"
				+ " left join d.gateWay g"
				+ " left join d.deviceType dt"
				+ " left join dt.actionTypes as actp"
				+ " where d.deviceType=dt.id"
				+ " and dt.actionTypes.id=actp.id"
				+ " and g.customer.id="
				+ customerId;

		List<Object[]> objects = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> listUserNotificatioinsByCustomerId(String customerId) {
		String query = "select u.id, u.name, u.recipient, at.name"
				+ " from UserNotificationProfile u"
				+ " left join u.actionType at" + " where u.actionType=at.id"
				+ " and u.customer.id=" + customerId;

		List<Object[]> objects = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> listDeviceWithLatestStatus(long id) {
		DBConnection dbc = null;
		// Using HQL for performance Optimization.

		String query = "select d.id, d.friendlyName, la.id, la.alertCommand, d.batteryStatus"
				+ " from Device as d"
				+ " left join d.lastAlert as la"
				+ " join d.rules as r"
				+ " where d.lastAlert=la.id"
				+ " and r.id=" + id;

		List<Object[]> objects = null;
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		try {
			Query q = session.createQuery(query);
			objects = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}

	public Object[] checkDeviceAndReturnCommunicationDetails(String deviceId,
			String customerId, String command) {
		String query = "select "
				+ " g.macId, d.generatedDeviceId, c.customerId, a.ip, a.controllerReceiverPort"
				+ " from Device as d" 
				+ " left join d.gateWay as g"
				+ " left join g.customer as c" 
				+ " left join g.agent as a"
				+ " left join d.deviceType as dt"
				+ " left join dt.actionTypes as at"
				+ " where d.generatedDeviceId='" + deviceId + "'"
				+ " and c.customerId='" + customerId + "'"
				+ " and at.command='" + command + "'" + "";
		DBConnection dbc = null;
		Object[] objects = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = (Object[]) q.uniqueResult();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}

	public Device changeDeviceLastAlertSaveAlertAndCheckArm(
			String generatedDeviceId, AlertType alertType, String batteryStatus, Date date) {
		String query = "select d,ast.name " + " from Device as d"
				+ " left join d.armStatus as ast"
				+ " where d.generatedDeviceId='" + generatedDeviceId + "'" + "";

				
		Object[] objects = null;
		DBConnection dbc = null;
		Device device = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = (Object[]) q.uniqueResult();
			device = (Device) objects[0];
			device.setLastAlert(alertType);
			if (batteryStatus != null) {
				device.setBatteryStatus(batteryStatus);
			}
			
			/*AlertsFromImvg alertsFromImvg = new AlertsFromImvg();
			alertsFromImvg.setAlertTime(new Date());
			alertsFromImvg.setAlertType(alertType);
			alertsFromImvg.setDevice(device);*/
			
			Transaction tx = session.beginTransaction();
			session.update(device);
			//session.save(alertsFromImvg);
			tx.commit();

			Hibernate.initialize(device.getRules());
			String armStatusName = IMonitorUtil.convertToString(objects[1]);
			Status armStatus = IMonitorUtil.getStatuses().get(armStatusName);
			device.setArmStatus(armStatus);
			device.setDeviceType(null);
			device.setMake(null);
			device.setLocationMap(null);
			device.setLocation(null);
			device.setDeviceConfiguration(null);
			device.setGateWay(null);
		
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
		return device;
	}

	public Device changeDeviceLastAlertSaveAlertAndCheckArm(
			String generatedDeviceId, AlertType alertType, String batteryStatus) {
		String query = "select d,ast.name" + " from Device as d"
				+ " left join d.armStatus as ast " 
				+ " where d.generatedDeviceId='" + generatedDeviceId + "'" ;
	
		Object[] objects = null;
		DBConnection dbc = null;
		Device device = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = (Object[]) q.uniqueResult();
			device = (Device) objects[0];
			device.setLastAlert(alertType);
			if (batteryStatus != null) {
				device.setBatteryStatus(batteryStatus);
			}
			
			
			Transaction tx = session.beginTransaction();
			session.update(device);
			
			tx.commit();

			Hibernate.initialize(device.getRules());
			String armStatusName = IMonitorUtil.convertToString(objects[1]);
			Status armStatus = IMonitorUtil.getStatuses().get(armStatusName);
			device.setArmStatus(armStatus);
			device.setDeviceType(null);
			device.setMake(null);
			device.setLocationMap(null);
			device.setLocation(null);
			device.setDeviceConfiguration(null);
			device.setGateWay(null);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return device;
	}
	public Device saveAlertAndCheckArm(String generatedDeviceId,
			AlertType alertType, String filePath, Date date) {
		String query = "select d, ast.name " + " from Device as d"
				+ " left join d.armStatus as ast"
				+ " where d.generatedDeviceId='" + generatedDeviceId + "'" + "";

		Object[] objects = null;
		DBConnection dbc = null;
		Device device = null;
		Date dateToSave = date;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = (Object[]) q.uniqueResult();
			device = (Device) objects[0];

			// device.setLastAlert(alertType);

			AlertsFromImvg alertsFromImvg = new AlertsFromImvg();
			if(dateToSave == null){
				dateToSave = new Date();
			}
			alertsFromImvg.setAlertTime(dateToSave);
			alertsFromImvg.setAlertType(alertType);
			alertsFromImvg.setDevice(device);
			alertsFromImvg.setAlarmStatus(1);
			Transaction tx = session.beginTransaction();
			// session.update(device);
			session.save(alertsFromImvg);

			if(filePath != null){
				UploadsByImvg uploadsByImvg = new UploadsByImvg();
				uploadsByImvg.setFilePath(filePath);
				uploadsByImvg.setAlertsFromImvg(alertsFromImvg);
				session.save(uploadsByImvg);
			}
			tx.commit();

//			Hibernate.initialize(device.getRules());
			device.setRules(null);
			String armStatusName = IMonitorUtil.convertToString(objects[1]);
			Status armStatus = IMonitorUtil.getStatuses().get(armStatusName);
			device.setArmStatus(armStatus);
			device.setDeviceType(null);
			device.setMake(null);
			device.setLocation(null);
			device.setLocationMap(null);
			device.setDeviceConfiguration(null);
			device.setGateWay(null);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return device;
	}

	public boolean deleteDeviceTree(String generatedDeviceId) {
		boolean result = false;
		DBConnection dbc = null;
		dashboardDBConnection reportngdatabases = null;
		try {
			// 1. Delete Device
			// The rest will be deleted automatically
			String deleteDeviceQuery = "select d from Device as d where d.generatedDeviceId='"
					+ generatedDeviceId + "'" + "";
			dbc = new DBConnection();
			
			Session session = dbc.openConnection();
			Query q = session.createQuery(deleteDeviceQuery);
			Device device = (Device) q.uniqueResult();
			Device dev = device.getGateWay().getAlertDevice();
			long did=device.getId();
			// LogUtil.info("did---"+did);
			String query = "delete from ScenarioAction where device="
					+ device.getId();
			q = session.createQuery(query);
			q.executeUpdate();
			query = "delete from ScheduleAction where device=" + device.getId();
			q = session.createQuery(query);
			q.executeUpdate();
			
				
			q = session.createQuery(query);
			q.executeUpdate();
			
			if(dev == device)
			{
					String hql = "update gateway g set g.alertDevice=NULL"+
							" where g.macId='" + device.getGateWay().getMacId() + "'";
					try {
						dbc = new DBConnection();
						dbc.openConnection();
						 session = dbc.getSession();
						 q = session.createSQLQuery(hql);
						Transaction tx = session.beginTransaction();
						q.executeUpdate();
						tx.commit();
					} catch (Exception e) {
						e.printStackTrace();
					}				
			}
			Transaction tx = session.beginTransaction();
			session.delete(device);
			tx.commit();
			
	       		
			 query = "select sum(alertvalue) from averagepowerinformation where did="
					+ did;
			// LogUtil.info("query---"+query);
			  reportngdatabases=new dashboardDBConnection();
			  Session sess=reportngdatabases.openConnection();
			q = sess.createSQLQuery(query);
			Object object = (Object)q.uniqueResult();
			//LogUtil.info("object---"+object);
			query = "delete from devicedetails where did="
					+ did;
			if(object != null){
			//	LogUtil.info("object.toString()---"+object.toString());
				if(object.toString().equalsIgnoreCase("0.0"))
				{
					// LogUtil.info("inside equal");
					q = sess.createSQLQuery(query);
					 tx = session.beginTransaction();
					q.executeUpdate();
					tx.commit();
				}
				else
				{
					LogUtil.info("not do any hing");	
				}
			}
			else{
				q = sess.createSQLQuery(query);
				 tx = session.beginTransaction();
				q.executeUpdate();
				tx.commit();
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return result;
	}

	public Device updatePanAndTilt(String generatedDeviceId, String gateWayId,
			CameraConfiguration cameraConfiguration) {
		DBConnection dbc = null;
		String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), cf from "
				+ " Device as d left join d.deviceType as dt left join d.location as l left join d.lastAlert as la left join d.rules as r"
				+ " left join d.deviceConfiguration as cf"
				+ " where d.generatedDeviceId= '"
				+ generatedDeviceId
				+ "' and d.gateWay.macId ='" + gateWayId + "'";
		Device device = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createQuery(query);
			Object[] object = (Object[]) q.uniqueResult();
			device = (Device) object[0];
			CameraConfiguration configuration = (CameraConfiguration) object[6];
			//vibhu commented to fix bug where pan tilt config option for a camera is getting reset
			//configuration.setPantiltControl(cameraConfiguration
			//		.getPantiltControl());
			configuration.setControlPantilt(cameraConfiguration
					.getControlPantilt());
			Transaction tx = session.beginTransaction();
			session.update(device);
			tx.commit();

			DeviceType deviceType = new DeviceType();
			deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
			deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
			device.setDeviceType(deviceType);

			Location location = new Location();
			location.setName(IMonitorUtil.convertToString(object[3]));
			device.setLocation(location);

			AlertType alertType = new AlertType();
			alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
			device.setLastAlert(alertType);

			int count = Integer.parseInt(IMonitorUtil
					.convertToString(object[5]));
			device.setRules(null);
			if (count > 0) {
				device.setRules(new ArrayList<Rule>());
			}

			device.setGateWay(null);
			device.setMake(null);
			device.setLocationMap(null);
			device.setDeviceConfiguration(null);

		} catch (Exception e) {
			LogUtil
					.error("9.Error when retreiving device by generatedDeviceId : "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}

	public Device updateNightVisionControl(String generatedDeviceId,
			String gateWayId, CameraConfiguration cameraConfiguration) {
		DBConnection dbc = null;
		String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), cf from "
				+ " Device as d left join d.deviceType as dt left join d.location as l left join d.lastAlert as la left join d.rules as r"
				+ " left join d.deviceConfiguration as cf"
				+ " where d.generatedDeviceId= '"
				+ generatedDeviceId
				+ "' and d.gateWay.macId ='" + gateWayId + "'";
		Device device = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createQuery(query);
			Object[] object = (Object[]) q.uniqueResult();
			device = (Device) object[0];
			CameraConfiguration configuration = (CameraConfiguration) object[6];
			configuration.setControlNightVision(cameraConfiguration
					.getControlNightVision());
			Transaction tx = session.beginTransaction();
			session.update(device);
			tx.commit();

			DeviceType deviceType = new DeviceType();
			deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
			deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
			device.setDeviceType(deviceType);

			Location location = new Location();
			location.setName(IMonitorUtil.convertToString(object[3]));
			device.setLocation(location);

			AlertType alertType = new AlertType();
			alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
			device.setLastAlert(alertType);

			int count = Integer.parseInt(IMonitorUtil
					.convertToString(object[5]));
			device.setRules(null);
			if (count > 0) {
				device.setRules(new ArrayList<Rule>());
			}

			device.setGateWay(null);
			device.setMake(null);
			device.setLocationMap(null);
			device.setDeviceConfiguration(null);

		} catch (Exception e) {
			LogUtil
					.error("10.Error when retreiving device by generatedDeviceId : "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}
	
	public Device updateCameraConfiguration(String generatedDeviceId,
			String gateWayId, CameraConfiguration cameraConfiguration) {
		DBConnection dbc = null;
		String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), cf from "
			+ " Device as d left join d.deviceType as dt left join d.location as l left join d.lastAlert as la left join d.rules as r"
			+ " left join d.deviceConfiguration as cf"
			+ " where d.generatedDeviceId= '"
			+ generatedDeviceId
			+ "' and d.gateWay.macId ='" + gateWayId + "'";
		Device device = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createQuery(query);
			Object[] object = (Object[]) q.uniqueResult();
			device = (Device) object[0];
			CameraConfiguration configuration = (CameraConfiguration) object[6];
//			Start : set configurations into device.
			configuration.setJpegResolution(cameraConfiguration.getJpegResolution());
			configuration.setJpegQuality(cameraConfiguration.getJpegQuality());
			configuration.setVideoColorBalance(cameraConfiguration.getVideoColorBalance());
			configuration.setVideoBrightness(cameraConfiguration.getVideoBrightness());
			configuration.setVideoSharpness(cameraConfiguration.getVideoSharpness());
			configuration.setVideoHue(cameraConfiguration.getVideoHue());
			configuration.setVideoSaturation(cameraConfiguration.getVideoSaturation());
			configuration.setVideoContrast(cameraConfiguration.getVideoContrast());
			configuration.setVideoAcFrequency(cameraConfiguration.getVideoAcFrequency());
			configuration.setMpeg4Resolution(cameraConfiguration.getMpeg4Resolution());
			configuration.setMpeg4QualityControl(cameraConfiguration.getMpeg4QualityControl());
			configuration.setMpeg4QualityLevel(cameraConfiguration.getMpeg4QualityLevel());
			configuration.setMpeg4BitRate(cameraConfiguration.getMpeg4BitRate());
			configuration.setMpeg4FrameRate(cameraConfiguration.getMpeg4FrameRate());
			configuration.setMpeg4BandWidth(cameraConfiguration.getMpeg4BandWidth());
			configuration.setAdminUserName(cameraConfiguration.getAdminUserName());
			configuration.setAdminPassword(cameraConfiguration.getAdminPassword());
			configuration.setNetworkMode(cameraConfiguration.getNetworkMode());
			configuration.setSystemNightVisionControl(cameraConfiguration.getSystemNightVisionControl());
			configuration.setPresetvalue(cameraConfiguration.getPresetvalue());
//			End : set configurations into device.
			
			Transaction tx = session.beginTransaction();
			session.update(device);
			tx.commit();
			
			DeviceType deviceType = new DeviceType();
			deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
			deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
			device.setDeviceType(deviceType);
			
			Location location = new Location();
			location.setName(IMonitorUtil.convertToString(object[3]));
			device.setLocation(location);
			
			AlertType alertType = new AlertType();
			alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
			device.setLastAlert(alertType);
			
			int count = Integer.parseInt(IMonitorUtil
					.convertToString(object[5]));
			device.setRules(null);
			if (count > 0) {
				device.setRules(new ArrayList<Rule>());
			}
			
			device.setGateWay(null);
			device.setMake(null);
			device.setLocationMap(null);
			device.setDeviceConfiguration(null);
			
		} catch (Exception e) {
			LogUtil
			.error("11.Error when retreiving device by generatedDeviceId : "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}
	public Device updateH264CameraConfiguration(String generatedDeviceId,
			String gateWayId, H264CameraConfiguration cameraConfiguration) {
		DBConnection dbc = null;
		String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), cf from "
			+ " Device as d left join d.deviceType as dt left join d.location as l left join d.lastAlert as la left join d.rules as r"
			+ " left join d.deviceConfiguration as cf"
			+ " where d.generatedDeviceId= '"
			+ generatedDeviceId
			+ "' and d.gateWay.macId ='" + gateWayId + "'";
		Device device = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createQuery(query);
			Object[] object = (Object[]) q.uniqueResult();
			device = (Device) object[0];
			H264CameraConfiguration configuration = (H264CameraConfiguration) object[6];
//			Start : set configurations into device.
			configuration.setVideoBrightness(cameraConfiguration.getVideoBrightness());
			configuration.setVideoContrast(cameraConfiguration.getVideoContrast());
			configuration.setVideoAcFrequency(cameraConfiguration.getVideoAcFrequency());
			configuration.setVideoFrameRate(cameraConfiguration.getVideoFrameRate());
			configuration.setVideoResolution(cameraConfiguration.getVideoResolution());
			configuration.setVideoQuality(cameraConfiguration.getVideoQuality());
			configuration.setVideoBitRate(cameraConfiguration.getVideoBitRate());
			configuration.setVideoBitRateMode(cameraConfiguration.getVideoBitRateMode());
			configuration.setVideoKeyFrame(cameraConfiguration.getVideoKeyFrame());
			configuration.setLedMode(cameraConfiguration.getLedMode());
			configuration.setPtzPatrolRate(cameraConfiguration.getPtzPatrolRate());
			configuration.setPtzPatrolUpRate(cameraConfiguration.getPtzPatrolUpRate());
			configuration.setPtzPatrolDownRate(cameraConfiguration.getPtzPatrolDownRate());
			configuration.setPtzPatrolLeftRate(cameraConfiguration.getPtzPatrolLeftRate());
			configuration.setPtzPatrolRightRate(cameraConfiguration.getPtzPatrolRightRate());
			configuration.setAdminUserName(cameraConfiguration.getAdminUserName());
			configuration.setAdminPassword(cameraConfiguration.getAdminPassword());
//			End : set configurations into device.
			
			Transaction tx = session.beginTransaction();
			session.update(device);
			tx.commit();
			
			DeviceType deviceType = new DeviceType();
			deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
			deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
			device.setDeviceType(deviceType);
			
			Location location = new Location();
			location.setName(IMonitorUtil.convertToString(object[3]));
			device.setLocation(location);
			
			AlertType alertType = new AlertType();
			alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
			device.setLastAlert(alertType);
			
			int count = Integer.parseInt(IMonitorUtil
					.convertToString(object[5]));
			device.setRules(null);
			if (count > 0) {
				device.setRules(new ArrayList<Rule>());
			}
			
			device.setGateWay(null);
			device.setMake(null);
			device.setLocationMap(null);
			device.setDeviceConfiguration(null);
			
		} catch (Exception e) {
			LogUtil
			.error("12.Error when retreiving device by generatedDeviceId : "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}

	public GateWay checkDeviceAndReturnGateWayDetails(String deviceId) {
		String query = "select " + " g" + " from Device as d"
				+ " left join d.gateWay as g" + " where d.generatedDeviceId='"
				+ deviceId + "'";
		DBConnection dbc = null;
		GateWay gateWayDB = null;
		GateWay gateWay = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			gateWayDB = (GateWay) q.uniqueResult();
			gateWay = new GateWay();
			gateWay.setMacId(gateWayDB.getMacId());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return gateWay;
	}

/* ********************************************** sumit start: ZXT120-ORIGINAL CODE ************************************************
 * 	public Device updateModelNumberOfDeviceByGeneratedId(
			String generatedDeviceId, String modelNumber) {
		DBConnection dbc = null;
		String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r) from "
				+ " Device as d left join d.deviceType as dt left join d.location as l left join d.lastAlert as la left join d.rules as r"
				+ " where d.generatedDeviceId= '" + generatedDeviceId + "'";
		Device inDevice = null;
		Device device = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createQuery(query);
			Object[] object = (Object[]) q.uniqueResult();
			inDevice = (Device) object[0];
			inDevice.setModelNumber(modelNumber);
			Transaction tx = session.beginTransaction();
			session.update(inDevice);
			tx.commit();

			// We are creating new device to avoid the hibernate proxy mapping
			// problem.
			device = new Device();
			device.setId(inDevice.getId());
			device.setDeviceId(inDevice.getDeviceId());
			device.setFriendlyName(inDevice.getFriendlyName());
			device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
			device.setBatteryStatus(inDevice.getBatteryStatus());
			device.setCommandParam(inDevice.getCommandParam());

			DeviceType deviceType = new DeviceType();
			deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
			deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
			device.setDeviceType(deviceType);

			Location location = new Location();
			location.setName(IMonitorUtil.convertToString(object[3]));
			device.setLocation(location);

			AlertType alertType = new AlertType();
			alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
			device.setLastAlert(alertType);

			int count = Integer.parseInt(IMonitorUtil
					.convertToString(object[5]));
			if (count > 0) {
				device.setRules(new ArrayList<Rule>());
			}

		} catch (Exception e) {
			LogUtil
					.error("Error when retreiving device by generatedDeviceId : "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}*/
	
	public Device updateModelNumberOfDeviceByGeneratedId(
			String generatedDeviceId, Device deviceToUpdate){//String modelNumber) {
		DBConnection dbc = null;
		String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r),  from "
				+ " Device as d left join d.deviceType as dt left join d.location as l left join d.lastAlert as la left join d.rules as r"
				+ " where d.generatedDeviceId= '" + generatedDeviceId + "'";
		Device inDevice = null;
		Device device = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createQuery(query);
			Object[] object = (Object[]) q.uniqueResult();
			inDevice = (Device) object[0];
			//inDevice.setModelNumber(modelNumber);
			inDevice.setModelNumber(deviceToUpdate.getModelNumber());
			inDevice.setDeviceConfiguration(deviceToUpdate.getDeviceConfiguration());
			inDevice.setMake(deviceToUpdate.getMake());
			Transaction tx = session.beginTransaction();
			session.update(inDevice);
			tx.commit();

			// We are creating new device to avoid the hibernate proxy mapping
			// problem.
			device = new Device();
			device.setId(inDevice.getId());
			device.setDeviceId(inDevice.getDeviceId());
			device.setFriendlyName(inDevice.getFriendlyName());
			device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
			device.setBatteryStatus(inDevice.getBatteryStatus());
			device.setCommandParam(inDevice.getCommandParam());

			DeviceType deviceType = new DeviceType();
			deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
			deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
			device.setDeviceType(deviceType);

			Location location = new Location();
			location.setName(IMonitorUtil.convertToString(object[3]));
			device.setLocation(location);

			AlertType alertType = new AlertType();
			alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
			device.setLastAlert(alertType);

			int count = Integer.parseInt(IMonitorUtil
					.convertToString(object[5]));
			if (count > 0) {
				device.setRules(new ArrayList<Rule>());
			}

		} catch (Exception e) {
			LogUtil
					.error("13.Error when retreiving device by generatedDeviceId : "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return device;
	}
	// ******************************************************** sumit end: ZXT120 ***********************************************************

	public Agent getAgentOfDevice(String deviceId) {
		DBConnection dbc = null;
		Agent agent = null;
		try {
			String query = "";
			query += "select a from " + " Device as d"
					+ " left join d.gateWay as g"
					+ " left join g.agent as a where d.generatedDeviceId='"
					+ deviceId + "'";

			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			agent = (Agent) q.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return agent;
	}

	public Device getDeviceMacIdByCustomerId(long customerId) {
		String query = "select g.macId from GateWay g where g.customer="
				+ customerId;
		Device device = new Device();
		DBConnection dbc = null;

		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			String macId = (String) q.uniqueResult();
			GateWay gateWay = new GateWay();
			gateWay.setMacId(macId);
			device.setGateWay(gateWay);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return device;
	}

	public Device changeDeviceLastAlertAndStatusAlsoSaveAlertAndCheckArm(
			String generatedDeviceId, AlertType alertType, String deviceStatus, Date date) {

		String query = "select d, ast.name " + " from Device as d"
				+ " left join d.armStatus as ast"
				+ " where d.generatedDeviceId='" + generatedDeviceId + "'" + "";
		
		

		Object[] objects = null;
		DBConnection dbc = null;
		Device device = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = (Object[]) q.uniqueResult();
			device = (Device) objects[0];
			device.setLastAlert(alertType);

			if (null != deviceStatus) {
				device.setCommandParam(deviceStatus);
			}

		/*	AlertsFromImvg alertsFromImvg = new AlertsFromImvg();
			alertsFromImvg.setAlertTime(date);
			alertsFromImvg.setAlertType(alertType);
			alertsFromImvg.setDevice(device);*/

			Transaction tx = session.beginTransaction();
			session.update(device);
		//	session.save(alertsFromImvg);
			tx.commit();

			Hibernate.initialize(device.getRules());
			String armStatusName = IMonitorUtil.convertToString(objects[1]);
			Status armStatus = IMonitorUtil.getStatuses().get(armStatusName);
			device.setArmStatus(armStatus);
			device.setDeviceType(null);
			device.setMake(null);
			device.setLocation(null);
			device.setLocationMap(null);
			device.setDeviceConfiguration(null);
			device.setGateWay(null);
		} catch (Exception ex) {
			LogUtil.error("Error in updating device to "
					+ alertType.getAlertCommand());
		} finally {
			dbc.closeConnection();
		}
		return device;
	}

	public boolean updateDeviceArmStatus(String customerId, String deviceId,
			Status status) {
		boolean result = false;
		String query = "select d from Device d left join d.gateWay g "
				+ " left join g.customer as c" + " where c.customerId = '"
				+ customerId + "'" +
				" and d.generatedDeviceId='" + deviceId + "'" +
				" and d.rules.size > 0";

		Device device = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Object uniqueResult = q.uniqueResult();
			if(uniqueResult != null){
				device = (Device) uniqueResult;
				Transaction tx = session.beginTransaction();
				device.setArmStatus(status);
				session.update(device);
				tx.commit();
				result = true;
			}
		} catch (Exception ex) {
			LogUtil.error("Error when changing status of device " + deviceId + " of customer " + customerId + " to status " + status.getName());
		} finally {
			dbc.closeConnection();
		}
		return result;

	}

	public boolean updateDeviceArmStatus(String generatedDeviceId, Status status) {
		DBConnection dbc = null;
		String hql = "update device d set d.armStatus=" + status.getId() +
				" where d.generatedDeviceId='" + generatedDeviceId + "'";
		boolean result = false;
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
		return result;
	}

	@SuppressWarnings("unchecked")
	public void updateAllDevicesArmStatus(String imvgId, Status armStatus) {
		DBConnection dbc = null;
		String query = "select d.id from Device as d" +
				" left join d.gateWay as g" +
				" where g.macId='" + imvgId + "'";
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query nq = session.createQuery(query);
			List<Long> deviceIds = nq.list();
			String dIds = "-1";
			for (Long long1 : deviceIds) {
				dIds += ", " + long1.longValue();
			}
			String hql = "update device d set d.armStatus=1 " +
			" where d.id in (" + dIds + ")";
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

	public Device retrieveDeviceDetails(String deviceId, String customerId) {
		String query = "";
		query += "select d.id, d.generatedDeviceId, d.friendlyName, d.batteryStatus, d.modelNumber, d.commandParam," +
				" dt.id, dt.name, dt.details," +
				" l.name," +
				" la.name, la.alertCommand," +
				" g.id, g.macId," +
				" a.id, a.ip, a.controllerReceiverPort,a.streamingIp, a.streamingPort, a.rtspPort," +
				//" ast.id, ast.name" +
				" ast.id, ast.name,"+
				" i.fileName ," +
				" dc.id" +
				" from Device as d" +
				" left join d.deviceType as dt" +
				" left join d.location as l" +
				" left join d.gateWay as g" +
				" left join d.lastAlert as la" +
				" left join d.deviceConfiguration as dc"+
				" left join d.armStatus as ast" +
				" left join g.customer as c" +
				" left join g.agent as a" +
				" left join d.icon as i" +
				" where d.generatedDeviceId='" + deviceId + "' and c.customerId='" + customerId + "'";
		DBConnection dbc = null;
		Object[] objects;
		Device device = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			objects = (Object[]) q.uniqueResult();
			device = new Device();
			Long id = (Long) objects[0];
			String generatedDeviceId = (String) objects[1];
			String friendlyName = (String) objects[2];
			String batteryStatus = (String) objects[3];
			String modelNumber = (String) objects[4];
			String commandParam = (String) objects[5];
			
			String deviceTypeName = (String) objects[7];
			String deviceTypeDetails = (String) objects[8];
			
			String locationName = (String) objects[9];
			
			String lastAlertName = (String) objects[10];
			String lastAlertCommand = (String) objects[11];
			
			Long gateWayId = (Long) objects[12];
			String macId = (String) objects[13];
			
			Long aId = (Long) objects[14];
			String aIp = (String) objects[15];
			Integer controllerReceiverPort = (Integer) objects[16];
			String streamingIp = (String) objects[17];
			Integer streamingPort = (Integer) objects[18];
			Integer rtspPort = (Integer) objects[19];
			
			Status armStatus = null;
			Long armStatusId = (Long) objects[20];
			if(armStatusId != null){
				armStatus = new Status();
				String armStatusName = (String) objects[21];
				armStatus.setId(armStatusId);
				armStatus.setName(armStatusName);
			}else{
				armStatus = IMonitorUtil.getStatuses().get(Constants.DISARM);
			}
			device.setArmStatus(armStatus);
			

			Icon icon = new Icon();
			String deviceIconFileName = (String) objects[22];
			icon.setFileName(deviceIconFileName);
			device.setIcon(icon);
			
			device.setId(id);
			device.setGeneratedDeviceId(generatedDeviceId);
			device.setFriendlyName(friendlyName);
			device.setBatteryStatus(batteryStatus);
			device.setModelNumber(modelNumber);
			device.setCommandParam(commandParam);
			
			DeviceType deviceType = new DeviceType();
			deviceType.setName(deviceTypeName);
			deviceType.setDetails(deviceTypeDetails);
			device.setDeviceType(deviceType);
			
			Long dcId =  (Long) objects[23];
			
			if(dcId != null)
			{
				DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
				if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
				{
					
					acConfiguration acConfiguration = (acConfiguration) Dvconf.getDeviceConfigurationById(dcId);
					device.setDeviceConfiguration(acConfiguration);
				}else if(device.getDeviceType().getDetails().equals(Constants.IP_CAMERA)){
					if(modelNumber.contains(Constants.H264Series)){
						
						H264CameraConfiguration cameraConfig = (H264CameraConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(cameraConfig);
						
					}else{
						
						CameraConfiguration camareConfig = (CameraConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(camareConfig);
						
					}
					
				}	
				else{
					DeviceConfiguration deviceConfiguration = Dvconf.getDeviceConfigurationById(dcId);
					device.setDeviceConfiguration(deviceConfiguration);
				}
			}
			
			Location location = new Location();
			location.setName(locationName);
			device.setLocation(location);
			
			AlertType alertType = new AlertType();
			alertType.setName(lastAlertName);
			alertType.setAlertCommand(lastAlertCommand);
			device.setLastAlert(alertType);
			
			GateWay gateWay = new GateWay();
			gateWay.setId(gateWayId);
			gateWay.setMacId(macId);
			
			Agent agent = new Agent();
			agent.setId(aId);
			agent.setIp(aIp);
			agent.setControllerReceiverPort(controllerReceiverPort);
			agent.setStreamingIp(streamingIp);
			agent.setStreamingPort(streamingPort);
			agent.setRtspPort(rtspPort);
			gateWay.setAgent(agent);
			device.setGateWay(gateWay);
			
		} catch (Exception ex) {
			LogUtil.error("Couldn't retrieve the details of the device : " + deviceId);
		} finally {
			dbc.closeConnection();
		}
		return device;
	}

	public Device getDeviceWithGateWayAndAgent(String generatedDeviceId,
			String macId, String customerId) {
		String query = "";
		query += "select d.id, d.generatedDeviceId, d.friendlyName, d.batteryStatus, d.modelNumber, d.commandParam, count(r)," +
				" dt.id, dt.name, dt.details,dt.iconFile," +
				" l.id, l.name, l.details," +
				" la.id, la.name, la.alertCommand, la.details," +
				" g.id, g.macId, g.localIp, g.remarks," +
				" a.id, a.ip, a.controllerReceiverPort,d.enableStatus" +
				" from Device as d" +
				" left join d.deviceType as dt" +
				" left join d.location as l" +
				" left join d.lastAlert as la" +
				" left join d.gateWay as g" +
				" left join d.rules as r" +
				" left join g.agent as a" +
				" left join g.customer as c" +
				" where d.generatedDeviceId='" + generatedDeviceId +"' and g.macId='" + macId + "' and c.customerId='" + customerId + "'";
		DBConnection dbc = null;
		Object[] objects;
		Device device  = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			objects = (Object[]) q.uniqueResult();
			
			Long dId = (Long) objects[0];
			generatedDeviceId = (String) objects[1];
			String friendlyName = (String) objects[2];
			String batteryStatus = (String) objects[3];
			String modelNumber = (String) objects[4];
			String commandParam = (String) objects[5];
			Long count  = (Long) objects[6];
			String EnableStatus = (String) objects[25];
			device = new Device();
			device.setId(dId);
			device.setGeneratedDeviceId(generatedDeviceId);
			device.setFriendlyName(friendlyName);
			device.setBatteryStatus(batteryStatus);
			device.setModelNumber(modelNumber);
			device.setCommandParam(commandParam);
			device.setEnableStatus(EnableStatus);
			if(count > 0){
				device.setRules(new ArrayList<Rule>());
			} else{
				device.setRules(null);
			}
			
			Long dtId = (Long) objects[7];
			String dtName = (String) objects[8]; 
			String dtDetails = (String) objects[9];
			String iconFile = (String) objects[10];
			DeviceType deviceType = new DeviceType();
			deviceType.setId(dtId);
			deviceType.setName(dtName);
			deviceType.setDetails(dtDetails);
			deviceType.setIconFile(iconFile);
			device.setDeviceType(deviceType);

			Long lId = (Long) objects[11];
			String lName = (String) objects[12]; 
			String lDetails = (String) objects[13];
			Location location = new Location();
			location.setId(lId);
			location.setName(lName);
			location.setDetails(lDetails);
			device.setLocation(location);
			
			Long laId = (Long) objects[14];
			String laName = (String) objects[15]; 
			String alertCommand = (String) objects[16]; 
			String laDetails = (String) objects[17];
			AlertType lastAlert = new AlertType();
			lastAlert.setId(laId);
			lastAlert.setName(laName);
			lastAlert.setAlertCommand(alertCommand);
			lastAlert.setDetails(laDetails);
			device.setLastAlert(lastAlert);
			
			Long gId = (Long) objects[18];
			macId = (String) objects[19]; 
			String localIp = (String) objects[20]; 
			String remarks = (String) objects[21];
			GateWay gateWay = new GateWay();
			gateWay.setId(gId);
			gateWay.setMacId(macId);
			gateWay.setLocalIp(localIp);
			gateWay.setRemarks(remarks);
			
			Long aId = (Long) objects[22];
			String ip = (String) objects[23]; 
			Integer controllerReceiverPort = (Integer) objects[24];
			Agent agent = new Agent();
			agent.setId(aId);
			agent.setIp(ip);
			agent.setControllerReceiverPort(controllerReceiverPort);
			gateWay.setAgent(agent);
			device.setGateWay(gateWay);
			
		} catch (Exception ex) {
			LogUtil.error("Couldn't retrieve the details of the device : " + generatedDeviceId);
		} finally {
			dbc.closeConnection();
		}
		return device;
	}


public boolean updateDeviceEnableStatus(String generatedDeviceId, String status) {
	
	DBConnection dbc = null;

	String hql = "update device d set d.enableStatus="+status+ " where d.generatedDeviceId='" + generatedDeviceId + "'";

	boolean result = false;
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
	return result;
}
//bhavya start
public boolean updatehealthmodelByGeneratedId(String generatedDeviceId,
		 long makeid) {
	DBConnection dbc = null;
	String hql = "update device d set d.make="+makeid +
			" where d.generatedDeviceId='" + generatedDeviceId+"'";
	boolean result = false;
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
	return result;
}
//bhavya end
public boolean updateMotorLengthByGeneratedId(String generatedDeviceId,
		MotorConfiguration  motorconfig, long makeid) {
	DBConnection dbc = null;
	String hql = "update device d,motorconfiguration m set m.length=" + motorconfig.getLength() +",m.mountingtype=" + motorconfig.getMountingtype() +", d.make="+makeid +
			" where d.generatedDeviceId='" + generatedDeviceId + "' and d.deviceconfiguration=m.id";
	boolean result = false;
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
	return result;
}

public Device getDeviceWithConfigurationForMAp(String generatedDeviceId) {
	
	
/*	Device device = null;
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Criteria criteria = session.createCriteria(Device.class);
		Criterion criterion = Restrictions.eq("generatedDeviceId",
				generatedDeviceId);
		criteria.add(criterion);
		device = (Device) criteria.uniqueResult();
		Hibernate.initialize(device);
		Hibernate.initialize(device.getDeviceConfiguration());
		
	} catch (Exception e) {
		LogUtil
				.error("Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;*/
	
	DBConnection dbc = null;
	String query = "select d,dc.id,dc.controlNightVision from "
			+ " Device as d "
			+ " left join d.deviceConfiguration as dc" 
			+ " where d.generatedDeviceId= '" + generatedDeviceId + "'";
	Device inDevice = null;
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		inDevice = (Device) object[0];
		Transaction tx = session.beginTransaction();
		tx.commit();

		device = new Device();
		device.setId(inDevice.getId());
		device.setDeviceId(inDevice.getDeviceId());
		device.setFriendlyName(inDevice.getFriendlyName());
		device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
		device.setBatteryStatus(inDevice.getBatteryStatus());
		device.setCommandParam(inDevice.getCommandParam());
		device.setModelNumber(inDevice.getModelNumber());
		Long dcId =  (Long) object[1];
		if(dcId != null){
			String controlNightVision = (String) object[2];
			if(controlNightVision != null){
				CameraConfiguration cameraConfiguration = new CameraConfiguration();
				cameraConfiguration.setId(dcId);
				cameraConfiguration.setControlNightVision(controlNightVision);
				device.setDeviceConfiguration(cameraConfiguration);
			}
			else{
				CameraConfiguration cameraConfiguration = new CameraConfiguration();
				cameraConfiguration.setId(dcId);
				cameraConfiguration.setControlNightVision(controlNightVision);
				device.setDeviceConfiguration(cameraConfiguration);
				
				/*DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
				deviceConfiguration.setId(dcId);
				device.setDeviceConfiguration(deviceConfiguration);*/
				
			}
		}
		

	} catch (Exception e) {
		LogUtil
				.error("14.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
	
}
public Device getDeviceWithConfigurationAndMake(String generatedDeviceId) {
	Device device = null;
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Criteria criteria = session.createCriteria(Device.class);
		Criterion criterion = Restrictions.eq("generatedDeviceId",
				generatedDeviceId);
		criteria.add(criterion);
		device = (Device) criteria.uniqueResult();
		Hibernate.initialize(device);
		Hibernate.initialize(device.getDeviceConfiguration());
		Hibernate.initialize(device.getMake());
		Hibernate.initialize(device.getGateWay());
		Hibernate.initialize(device.getDeviceType());		//sumit added this line for ZXT120
		if(device.getGateWay() != null)
		device.getGateWay().setAlertDevice(null);
	} catch (Exception e) {
		LogUtil
				.error("15.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}

public boolean updateDeviceEnableStatus(String generatedDeviceId, String status,AlertType alertType) {
	DBConnection dbc = null;
	//String query = "update device d set d.enableStatus="+status+ " where d.generatedDeviceId='" + generatedDeviceId + "'";
	
	//String query = "select Device as d where d.generatedDeviceId = '" + generatedDeviceId + "'"+ "";

	//LogUtil.info("generatedDeviceId:"+generatedDeviceId+"deviceStatus"+status+"alerttype"+alertType.getName());
	String query = "update device d set d.enableStatus="+status+
			",d.lastAlert="+alertType.getId()+" where d.generatedDeviceId='" + generatedDeviceId + "'";
	/*String query = "select d, ast.name " + " from Device as d"
			+ " left join d.enableStatus as ast"
			+ " where d.generatedDeviceId='" + generatedDeviceId + "'" + "";*/
	
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

public Boolean updateDeviceStatusAndAlert(String generatedDeviceId, String deviceStatus,AlertType alerttype) {
	//LogUtil.error("updateDeviceStatusAndAlert usin id and alert and device satus to write into data base");
	Boolean result=false;
	//LogUtil.info("generatedDeviceId:"+generatedDeviceId+"deviceStatus"+deviceStatus+"alerttype"+alerttype.getName());
	String query = "update device d set d.commandParam="+deviceStatus+
			",d.lastAlert="+alerttype.getId()+" where d.generatedDeviceId='" + generatedDeviceId + "'";
	
	DBConnection dbc = null;
	
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createSQLQuery(query);
		Transaction tx = session.beginTransaction();
		q.executeUpdate();
		tx.commit();
		result = true;
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return result;
}


public boolean updateDeviceAlert(String generatedDeviceId, AlertType alertType) {
	
	String query = "update device d set d.lastAlert="+alertType.getId()+" where d.generatedDeviceId='" + generatedDeviceId + "'";	
	DBConnection dbc = null;
	
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
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return result;
}

public boolean SaveUploadedLogDetails(String generatedDeviceId, String filename,
		String filePath, String imvgTimeStamp) 
{
	GatewayManager gateway=new GatewayManager();
	GateWay gate=gateway.getGateWayByMacId(generatedDeviceId);
	long id=gate.getId();
	
	String query = "insert into imvglog(filename,filepath,gateWay) values ('"+filename+"','"+filePath+"',"+id+")";	
	DBConnection dbc = null;
	
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
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return result;
}

public boolean DeleteLogsUnderGatewayById(String filename) {
	String query = "delete from imvglog where filename ='"+filename+"'";	
	DBConnection dbc = null;
	
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
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return result;
}
public boolean updateDeviceCommandParam(String generatedDeviceId, String status) {
	DBConnection dbc = null;
	String hql = "update device set commandParam="+status+ " where generatedDeviceId='" + generatedDeviceId + "'";

	boolean result = false;
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
		//e.printStackTrace();
	
		LogUtil.info("updateDeviceEnableStatus to write into data base",e);
	} finally {
		dbc.closeConnection();
	}
	return result;
}

@SuppressWarnings("unchecked")
public List<AlertType> getAlertsOfDeviceByDeviceType(String generatedDeviceId) {
		List<AlertType> Alerts = new ArrayList<AlertType>();
		List<Object[]> detailsArray = null;
		DBConnection dbc = null;
		try {
		
	        	
	        String query = "select d.id ,d.friendlyName, dt.name,d.modelNumber"
					+ " ,altp.id ,altp.name ,altp.details ,c.featuresEnabled" //vibhu added details [6]
					+ " from Device d"
					+ " left join d.deviceType dt"
					+ " left join dt.alertTypes as altp"
					+ " left join d.gateWay as g"
					+ " left join g.customer as c"
					+ " where d.deviceType=dt.id" + " and dt.alertTypes.id=altp.id" 
					+ " and d.generatedDeviceId='" + generatedDeviceId + "'"
					+ " and d.gateWay = g.id and g.customer=c.id";
					


	        
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			detailsArray=q.list();
			
			for (Object[] details : detailsArray) 
			{
				XStream stream = new XStream();
			
				AlertType a = new AlertType();
				long id=(Long) details[4];
				a.setId(id);
				String name=(String)details[5];
				String model=(String)details[3];
				a.setName(name);
				a.setDetails((String)details[6]); //vibhu added
				long FeatureEnabled=(Long)details[7];
				
				
				if((FeatureEnabled & 1)== 1)
				{
				if(name.equals("HMD_WARNING") || name.equals("HMD_NORMAL") || name.equals("HMD_FAILURE") || name.equals("POWER_INFORMATION"))
				{
					if(model.equals("HMD"))
						Alerts.add(a);
				}
				else
					{
				Alerts.add(a);
			}
				}
				else
				{
					if(!name.contains("HMD_WARNING") && !name.contains("HMD_NORMAL") && !name.contains("HMD_FAILURE") && !name.contains("POWER_INFORMATION"))
					{
						Alerts.add(a);
					}
				}
				
			}
		
			/*query="select alerts from userChoosenAlerts where device ='"+ Deviceid+"'";
			q=session.createSQLQuery(query);
			detailsArray.addAll(q.list());
			LogUtil.info(strem.toXML(q.list()));*/
		} catch (Exception e) {
			LogUtil.error("Error when listing all Alerts.");
			e.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return Alerts;
		
		
	}
	
public boolean setUserChoosenAlerts(long deviceId, long id, String alerts) {
	
//	String query = " update  UserChoosenAlerts as u set u.device="+deviceId+",u.alerts"+alerts+",u.gateway="+id;
	
	/*String query="IF EXISTS(select device from userChoosenAlerts where device ="+deviceId+")" +
			"BEGIN" +
			"update userChoosenAlerts set alerts="+alerts+"where device'"+deviceId+ "'"+
			"END" +
			"ELSE" +
			"BEGIN" +
			"insert into userChoosenAlerts(gateway,device,alerts) values('"+id+"','"+deviceId+"','"+alerts+"')" +
			"END";*/
	String query ="insert into userChoosenAlerts(gateway,device,alerts) values('"+id+"','"+deviceId+"','"+alerts+"') ON DUPLICATE KEY UPDATE alerts='"+alerts+"'";	
	DBConnection dbc = null;
	boolean result = false;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		Transaction tx = session.beginTransaction();
		q.executeUpdate();
		tx.commit();
		result = true;
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return result;
	
}
@SuppressWarnings({ "unchecked" })
public List<String> GetUserChoosenAlerts(long DeviceId) {
	List<String> Result = null;
	
	String query = " select alerts from  UserChoosenAlerts where device='"+ DeviceId + "'"+"order by id";
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);

		Result=q.list();
		//LogUtil.info(stream.toXML(q.uniqueResult()));
		if(Result.isEmpty())
		{
			LogUtil.info("EMpty");
			
		}
	} catch (Exception e) 
	{
		
	} 
	finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}

	return Result;
}

public boolean RemoveUserChoosenAlerts(long deviceId, long id, String alerts) {
	String query ="delete from userChoosenAlerts  where gateway="+id+" and device="+deviceId+" and alerts="+alerts+"";
	boolean result = false;
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		Transaction tx = session.beginTransaction();
		q.executeUpdate();
		tx.commit();
		result = true;
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return result;
	
}

@SuppressWarnings("rawtypes")
public void updateAlertsFromImvg(long id, AlertType alertType, Date date, Device device,String alertValue) 
{
	List objects = null;
	DBConnection dbc = null;
	String query = "select alerts, device from  UserChoosenAlerts where device='"+ id + "'"+"and alerts='"+alertType.getId()+"'";
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);	
		objects = q.list();	
		AlertsFromImvg alertsFromImvg = new AlertsFromImvg();
		if(objects.isEmpty())
		{
			
			alertsFromImvg.setAlertTime(date);
			alertsFromImvg.setAlertType(alertType);
			alertsFromImvg.setDevice(device);	
			alertsFromImvg.setAlertValue(alertValue);
			
		}
		else
		{
			alertsFromImvg.setAlertTime(date);
			alertsFromImvg.setAlertType(alertType);
			alertsFromImvg.setDevice(device);
			alertsFromImvg.setAlarmStatus(1);
			alertsFromImvg.setAlertValue(alertValue);
		}
		session.save(alertsFromImvg);
		Transaction tx = session.beginTransaction();
		tx.commit();
	}
	catch (Exception e) {
		LogUtil.info("logs in catch(updateAlertsFromImvg)Query+++++"+e);
		// TODO: handle exception
	}finally {
		dbc.closeConnection();
	}
}

public void updateAlertsFromImvgForVirtualDevice(long id, AlertType alertType,Date date, Device device,String alertValue) 
{
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		AlertsFromImvg alertsFromImvg = new AlertsFromImvg();
		alertsFromImvg.setAlertTime(date);
		alertsFromImvg.setAlertType(alertType);
		alertsFromImvg.setDevice(device);
		alertsFromImvg.setAlarmStatus(1);
		alertsFromImvg.setAlertValue(alertValue);
		session.save(alertsFromImvg);
		Transaction tx = session.beginTransaction();
		tx.commit();
	} catch (Exception e) {
		// TODO: handle exception
	}finally {
		dbc.closeConnection();
	}
	
	
	
}

public void SaveDevicePosition(Device device) {
	String query ="insert into locationMap(top,leftMap,device) values('"+device.getLocationMap().getTop()+"','"+device.getLocationMap().getLeftMap()+"','"+device.getId()+"') ON DUPLICATE KEY UPDATE top='"+device.getLocationMap().getTop()+"'"+",leftMap='"+device.getLocationMap().getLeftMap()+"'";	
	DBConnection dbc = null;
	//Object[] objects = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		q.executeUpdate();
		/*query = "select id from LocationMap where device='"+ device.getId() + "'";
		q = session.createQuery(query);	
		objects = (Object[]) q.uniqueResult();
		LocationMap map = new LocationMap();
		
		
		long id = (Long) objects[0];	
		map.setId(id);
		XStream stream=new XStream();
		LogUtil.info(stream.toXML(map));
		device.setMap(map);
		session.update(device);*/
		Transaction tx = session.beginTransaction();
		tx.commit();
	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
}

public void RemoveDevicePosition(Device device){
	String query ="delete from locationMap  where device="+device.getId();
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		q.executeUpdate();
		
		device.setLocationMap(null);
		session.update(device);
		
		Transaction tx = session.beginTransaction();
		tx.commit();
	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	
}

public void getDevicePosition(Device device) {
	Object[] objects = null;
	DBConnection dbc = null;
	String query = "select lm.id,lm.top,lm.leftMap,lm.device from LocationMap as lm where lm.device='"+ device.getId() + "'";
	
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery(query);	
		objects = (Object[]) q.uniqueResult();
		XStream stream=new XStream();
		LocationMap map = new LocationMap();
		
		
		long id = (Long) objects[0];
		
		String Top = (String) objects[1]; 
		String LeftMap = (String) objects[2];
		device=(Device)objects[3];
		map.setId(id);
		map.setLeftMap(LeftMap);
		map.setTop(Top);
		map.setDevice(device);
		device.setLocationMap(map);
		session.update(device);
		
		tx.commit();
	}
	catch (Exception e) {
		LogUtil
		.error("Error when updateing device by generatedDeviceId : "
				+ e.getMessage());
e.printStackTrace();
	}finally {
		dbc.closeConnection();
	}	
}
public Device GetDeviceForFailureByGeneratedId(String generatedDeviceId) {
	LogUtil.info("GetDeviceForFailureByGeneratedId ");
	XStream stream=new XStream();
	DBConnection dbc = null;
	dashboardDBConnection DashBoard = null;
	//String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r) from "
	String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), i.fileName, ast.id, ast.name,cf from "
			+ " Device as d left join d.deviceType as dt"
			+ " left join d.deviceConfiguration as cf"
			+ " left join d.location as l"
			+ " left join d.lastAlert as la"
			+ " left join d.rules as r"
			+ " left join d.icon as i"
			+ " left join d.armStatus as ast" 
			+ " where d.generatedDeviceId= '"
			+ generatedDeviceId +"'";
	
	Device inDevice = null;
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		inDevice = (Device) object[0];
		LogUtil.info("GetDeviceForFailureByGeneratedId object=="+stream.toXML(object));
		
		device = new Device();
		device.setId(inDevice.getId());
		device.setDeviceId(inDevice.getDeviceId());
		device.setFriendlyName(inDevice.getFriendlyName());
		device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
		device.setBatteryStatus(inDevice.getBatteryStatus());
		device.setCommandParam(inDevice.getCommandParam());
		device.setModelNumber(inDevice.getModelNumber());
		device.setEnableStatus(inDevice.getEnableStatus());
		
		device.setDeviceConfiguration(inDevice.getDeviceConfiguration());
		
		
		DeviceType deviceType = new DeviceType();
		deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
		deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
		device.setDeviceType(deviceType);

		Location location = new Location();
		location.setName(IMonitorUtil.convertToString(object[3]));
		device.setLocation(location);

		AlertType alertType = new AlertType();
		alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
		device.setLastAlert(alertType);

		Status armStatus = null;
		Long armStatusId = (Long) object[7];
		if(armStatusId != null){
			armStatus = new Status();
			String armStatusName = (String) object[8];
			armStatus.setId(armStatusId);
			armStatus.setName(armStatusName);
		}
		device.setArmStatus(armStatus);
		
		int count = Integer.parseInt(IMonitorUtil
				.convertToString(object[5]));
		if (count > 0) {
			device.setRules(new ArrayList<Rule>());
		}
		
		//Icon Bug
		//device.getIcon().setFileName(inDevice.getIcon().getFileName());
		device.setIcon(inDevice.getIcon());
		if(device.getIcon() != null) 
		device.getIcon().setFileName(IMonitorUtil.convertToString(object[6]));
		
		
		if(device.getModelNumber().equals("HMD"))
		{
			
			String HMDquery = "select a.alertTime,a.id,a.alertValue,a.device,a.alertTypeName from instantpowerinformation as a where a.device="+ device.getId() +" and a.alertTypeName='POWER_INFORMATION' order by a.alertTime desc limit 1";
			LogUtil.info("GetDeviceForFailureByGeneratedId HMDquery=="+HMDquery);
			DashBoard = new dashboardDBConnection();
			q = DashBoard.getSession().createSQLQuery(HMDquery);
			Object[] Hmdobjects = (Object[])q.uniqueResult();
			DashBoard.closeConnection();
		
			
		//Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(device.getGeneratedDeviceId());
	
		if(Hmdobjects != null && Hmdobjects[2]!= null)
		{
		
			device.setHMDalertValue((""+(String)Hmdobjects[2]));
			device.setHMDpowerinfo(""+(String)Hmdobjects[4]);
		
		}
		else
		{
			device.setHMDalertValue("");
			device.setHMDpowerinfo("");
		
		}
		}

	} catch (Exception e) {
		LogUtil
				.error("16.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	LogUtil.info("GetDeviceForFailureByGeneratedId device=="+stream.toXML(device));
	return device;
}

@SuppressWarnings("unused")
public void updatePowerInformation(long id, AlertType alertType, Date date,
		Device device, String alertValue) {
	//naveen made changes for parsing into integer
	alertValue = alertValue.replace("W", "");
	//naveen end
	int alertvalue=Integer.parseInt(alertValue);
	//LogUtil.info("alertvalue==="+alertvalue);
	if(alertvalue <= 15000 && alertvalue >= 0)
	{
	Object[] objects = null;
	List Listobjects = null;
	DBConnection dbc = null;
	dashboardDBConnection DashBoard = null;
	if (null != DashBoard) {
		DashBoard.closeConnection();
	         }
	if (null != dbc) {
		dbc.closeConnection();
	         }
	String query = "select alerts, device from  UserChoosenAlerts where device='"+ id + "'"+"and alerts='"+alertType.getId()+"'";
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);	
		Listobjects = q.list();	
		powerInformationFromImvg powerinformationfromimvg = new powerInformationFromImvg();
		if(Listobjects.isEmpty())
		{
			
			powerinformationfromimvg.setAlertTime(date);
			powerinformationfromimvg.setAlertType(alertType);
			powerinformationfromimvg.setDevice(device);	
			powerinformationfromimvg.setAlertValue(alertValue);
			
		}
		else
		{
			powerinformationfromimvg.setAlertTime(date);
			powerinformationfromimvg.setAlertType(alertType);
			powerinformationfromimvg.setDevice(device);
			powerinformationfromimvg.setAlarmStatus(1);
			powerinformationfromimvg.setAlertValue(alertValue);
		}
		session.save(powerinformationfromimvg);
		Transaction tx = session.beginTransaction();
		tx.commit();
	
	
	}
	catch (Exception e) {
		LogUtil.info("logs in catch(updateAlertsFromImvg)Query+++++"+e);}
       finally{dbc.closeConnection();}
	
	
	 query = "select ip from " +
		        " instantpow as ip"+
		        " where ip.device="+ id +" order by ip.alertTime desc limit 1";
	try {
		DashBoard = new dashboardDBConnection();
		Session session = DashBoard.openConnection();
		Query q = session.createQuery(query);	
		instantpow Instantpowerinformation = (instantpow) q.uniqueResult();
		
		if(Instantpowerinformation != null)
		{
			Instantpowerinformation.setAlertTime(date);
			Instantpowerinformation.setAlertType(alertType.getId());
			Instantpowerinformation.setGeneratedDeviceId(device.getGeneratedDeviceId());
			Instantpowerinformation.setAlertTypeName(alertType.getName());
			Instantpowerinformation.setAlertValue(alertValue);
			session.update(Instantpowerinformation);
			
		}
		else
	    {
			instantpow InstantPowerInformation = new instantpow();
			InstantPowerInformation.setAlertTime(date);
			InstantPowerInformation.setAlertType(alertType.getId());
			InstantPowerInformation.setAlertTypeName(alertType.getName());
			InstantPowerInformation.setDevice(device.getId());
			InstantPowerInformation.setGeneratedDeviceId(device.getGeneratedDeviceId());
			InstantPowerInformation.setAlarmStatus(0);
			InstantPowerInformation.setAlertValue(alertValue);
			session.save(InstantPowerInformation);
			
		
		}
		
		Transaction tx = session.beginTransaction();
		tx.commit();
		DashBoard.closeConnection();
	}
		catch (Exception e) {
		LogUtil.info("logs in catch(updateAlertsFromImvg)Query+++++"+e);}
		finally{DashBoard.closeConnection();}
	}	
	else
	{
		LogUtil.info("Got Negative value or More than 15000 Please inform to iMVG Team:"+alertvalue);	
	}
}

@SuppressWarnings("unused")
public void updateEnergyInformation(long id, AlertType alertType, Date date,
		Device device, String alertValue) {
	//naveen made changes for parsing into integer
	alertValue = alertValue.replace("kWh", "");
	//naveen end
	//LogUtil.info("alertvalue==="+alertvalue);
	Object[] objects = null;
	List Listobjects = null;
	DBConnection dbc = null;
	String query = "select alerts, device from  UserChoosenAlerts where device='"+ id + "'"+"and alerts='"+alertType.getId()+"'";
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);	
		Listobjects = q.list();	
		
		energyInformationFromImvg energyinformationfromimvg = new energyInformationFromImvg();
		if(Listobjects.isEmpty())
		{
			
			energyinformationfromimvg.setAlertTime(date);
			energyinformationfromimvg.setAlertType(alertType);
			energyinformationfromimvg.setDevice(device);	
			energyinformationfromimvg.setAlertValue(alertValue);
			
		}
		else
		{
			energyinformationfromimvg.setAlertTime(date);
			energyinformationfromimvg.setAlertType(alertType);
			energyinformationfromimvg.setDevice(device);
			energyinformationfromimvg.setAlarmStatus(1);
			energyinformationfromimvg.setAlertValue(alertValue);
		}
		
		session.save(energyinformationfromimvg);
		Transaction tx = session.beginTransaction();
		tx.commit();
	
	
	}
	catch (Exception e) {
		LogUtil.info("logs in catch(updateAlertsFromImvg)Query+++++"+e);}
       finally{dbc.closeConnection();}
} 
public Device getConfigureSwitchByGeneratedDeviceId(String generatedDeviceId){
	DBConnection dbc = null;
	Device device = new Device();
	try {
		String query = "select d.id,d.generatedDeviceId,d.switchType,d.friendlyName,g.macId from "
			+ " Device as d left join d.gateWay as g where d.generatedDeviceId='" + generatedDeviceId+"' ";
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		device.setId((Long)object[0]);
		device.setGeneratedDeviceId(IMonitorUtil.convertToString(object[1]));
		device.setSwitchType((Long)object[2]);
		device.setFriendlyName(IMonitorUtil.convertToString(object[3]));
		
		GateWay gateWay = new GateWay();
		gateWay.setMacId(IMonitorUtil.convertToString(object[4]));
	    device.setGateWay(gateWay);
	    
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	
	return device;
}

//bhavya start 

public Device updateFanModesOfDeviceByGeneratedId(String generatedDeviceId, String gateWayId, String FanModeAndSwingValue,int FanSwing) {
	DBConnection dbc = null;
	dashboardDBConnection DashBoard = null;
	//String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r) from "
	String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), i.fileName, ast.id, ast.name,cf from "
			+ " Device as d left join d.deviceType as dt"
			+ " left join d.deviceConfiguration as cf"
			+ " left join d.location as l"
			+ " left join d.lastAlert as la"
			+ " left join d.rules as r"
			+ " left join d.icon as i"
			+ " left join d.armStatus as ast" 
			+ " where d.generatedDeviceId= '"
			+ generatedDeviceId
			+ "' and d.gateWay.macId ='" + gateWayId + "'";
	Device inDevice = null;
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		inDevice = (Device) object[0];
		acConfiguration configuration = (acConfiguration) object[9];
	//	configuration.setFanModeValue(acConfiguration.getFanModeValue());		LogUtil.info("object[9]-------------"+configuration);
		/***
		 * Fan Swing value is differentiate between FAN mode control and Swing control
		 * **/
		if(FanSwing==1)
		configuration.setFanModeValue(FanModeAndSwingValue);
		else if(FanSwing==0)
		configuration.setAcSwing(FanModeAndSwingValue);	
		
		Transaction tx = session.beginTransaction();
		session.update(inDevice);
		tx.commit();

		// We are creating new device to avoid the hibernate proxy mapping
		// problem.
		device = new Device();
		device.setId(inDevice.getId());
		device.setDeviceId(inDevice.getDeviceId());
		device.setFriendlyName(inDevice.getFriendlyName());
		device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
		device.setBatteryStatus(inDevice.getBatteryStatus());
		device.setCommandParam(inDevice.getCommandParam());
		device.setModelNumber(inDevice.getModelNumber());
		device.setEnableStatus(inDevice.getEnableStatus());
		device.setDeviceConfiguration(inDevice.getDeviceConfiguration());
		DeviceType deviceType = new DeviceType();
		deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
		deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
		device.setDeviceType(deviceType);
		Location location = new Location();
		location.setName(IMonitorUtil.convertToString(object[3]));
		device.setLocation(location);

		AlertType alertType = new AlertType();
		alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
		device.setLastAlert(alertType);

		Status armStatus = null;
		Long armStatusId = (Long) object[7];
		if(armStatusId != null){
			armStatus = new Status();
			String armStatusName = (String) object[8];
			armStatus.setId(armStatusId);
			armStatus.setName(armStatusName);
		}
		device.setArmStatus(armStatus);
		
		int count = Integer.parseInt(IMonitorUtil
				.convertToString(object[5]));
		if (count > 0) {
			device.setRules(new ArrayList<Rule>());
		}
		
		//Icon Bug
		//device.getIcon().setFileName(inDevice.getIcon().getFileName());
		device.setIcon(inDevice.getIcon());
		if(device.getIcon() != null) device.getIcon().setFileName(IMonitorUtil.convertToString(object[6]));
		
		if(device.getModelNumber().equals("HMD"))
		{
			
			String HMDquery = "select a.alertTime,a.id,a.alertValue,a.device,a.alertTypeName from instantpowerinformation as a where a.device="+ device.getId() +" and a.alertTypeName='POWER_INFORMATION' order by a.alertTime desc limit 1";
			
			DashBoard = new dashboardDBConnection();
			q = DashBoard.getSession().createSQLQuery(HMDquery);
			Object[] Hmdobjects = (Object[])q.uniqueResult();
			DashBoard.closeConnection();
			
		//Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(device.getGeneratedDeviceId());
	
		if(Hmdobjects != null && Hmdobjects[2]!= null)
		{
		
			device.setHMDalertValue((""+(String)Hmdobjects[2]));
			device.setHMDpowerinfo(""+(String)Hmdobjects[4]);
		
		}
		else
		{
			device.setHMDalertValue("");
			device.setHMDpowerinfo("");
		
		}
		}
		

	} catch (Exception e) {
		LogUtil
				.error("17.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}

public Device updateCommadParamAndFanModesOfDeviceByGeneratedId(
		String generatedDeviceId, String gateWayId, String commandParam,String setpointvalue) {
	DBConnection dbc = null;
	dashboardDBConnection DashBoard = null;
	//String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r) from "
	String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), i.fileName, ast.id, ast.name,cf,dt.name from "
			+ " Device as d left join d.deviceType as dt"
			+ " left join d.deviceConfiguration as cf"
			+ " left join d.location as l"
			+ " left join d.lastAlert as la"
			+ " left join d.rules as r"
			+ " left join d.icon as i"
			+ " left join d.armStatus as ast" 
			+ " where d.generatedDeviceId= '"
			+ generatedDeviceId
			+ "' and d.gateWay.macId ='" + gateWayId + "'";
	Device inDevice = null;
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		inDevice = (Device) object[0];
		inDevice.setCommandParam(commandParam);
		acConfiguration configuration = (acConfiguration) object[9];
		configuration.setFanMode(setpointvalue);
		Transaction tx = session.beginTransaction();
		session.update(inDevice);
		tx.commit();

		// We are creating new device to avoid the hibernate proxy mapping
		// problem.
		device = new Device();
		device.setId(inDevice.getId());
		device.setDeviceId(inDevice.getDeviceId());
		device.setFriendlyName(inDevice.getFriendlyName());
		device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
		device.setBatteryStatus(inDevice.getBatteryStatus());
		device.setCommandParam(inDevice.getCommandParam());
		device.setModelNumber(inDevice.getModelNumber());
		device.setEnableStatus(inDevice.getEnableStatus());
		device.setDeviceConfiguration(inDevice.getDeviceConfiguration());
		DeviceType deviceType = new DeviceType();
		deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
		deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
		deviceType.setName(IMonitorUtil.convertToString(object[10]));
		device.setDeviceType(deviceType);
		Location location = new Location();
		location.setName(IMonitorUtil.convertToString(object[3]));
		device.setLocation(location);

		AlertType alertType = new AlertType();
		alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
		device.setLastAlert(alertType);

		Status armStatus = null;
		Long armStatusId = (Long) object[7];
		if(armStatusId != null){
			armStatus = new Status();
			String armStatusName = (String) object[8];
			armStatus.setId(armStatusId);
			armStatus.setName(armStatusName);
		}
		device.setArmStatus(armStatus);
		
		int count = Integer.parseInt(IMonitorUtil
				.convertToString(object[5]));
		if (count > 0) {
			device.setRules(new ArrayList<Rule>());
		}
		
		//Icon Bug
		//device.getIcon().setFileName(inDevice.getIcon().getFileName());
		device.setIcon(inDevice.getIcon());
		if(device.getIcon() != null) device.getIcon().setFileName(IMonitorUtil.convertToString(object[6]));
		
		if(device.getModelNumber().equals("HMD"))
		{
			
			String HMDquery = "select a.alertTime,a.id,a.alertValue,a.device,a.alertTypeName from instantpowerinformation as a where a.device="+ device.getId() +" and a.alertTypeName='POWER_INFORMATION' order by a.alertTime desc limit 1";
			
			DashBoard = new dashboardDBConnection();
			q = DashBoard.getSession().createSQLQuery(HMDquery);
			Object[] Hmdobjects = (Object[])q.uniqueResult();
			DashBoard.closeConnection();
			
		//Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(device.getGeneratedDeviceId());
	
		if(Hmdobjects != null && Hmdobjects[2]!= null)
		{
		
			device.setHMDalertValue((""+(String)Hmdobjects[2]));
			device.setHMDpowerinfo(""+(String)Hmdobjects[4]);
		
		}
		else
		{
			device.setHMDalertValue("");
			device.setHMDpowerinfo("");
		
		}
		}
		

	} catch (Exception e) {
		LogUtil
				.error("18.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}

public Device changeDeviceLastAlertAndStatusAlsoSaveAlert(
		String generatedDeviceId, AlertType alertType, String deviceStatus, Date date) {

	String query = "select d, ast.name " + " from Device as d"
			+ " left join d.armStatus as ast"
			+ " where d.generatedDeviceId='" + generatedDeviceId + "'" + "";

	Object[] objects = null;
	DBConnection dbc = null;
	Device device = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		objects = (Object[]) q.uniqueResult();
		device = (Device) objects[0];
		device.setLastAlert(alertType);

		if (null != deviceStatus) {
			device.setCommandParam(deviceStatus);
		}

		/*AlertsFromImvg alertsFromImvg = new AlertsFromImvg();
		alertsFromImvg.setAlertTime(date);
		alertsFromImvg.setAlertType(alertType);
		alertsFromImvg.setDevice(device);*/

		Transaction tx = session.beginTransaction();
		session.update(device);
	//	session.save(alertsFromImvg);
		tx.commit();

		Hibernate.initialize(device.getRules());
		String armStatusName = IMonitorUtil.convertToString(objects[1]);
		Status armStatus = IMonitorUtil.getStatuses().get(armStatusName);
		device.setArmStatus(armStatus);
		device.setDeviceType(null);
		device.setMake(null);
		device.setLocation(null);
		device.setLocationMap(null);
		device.setDeviceConfiguration(device.getDeviceConfiguration());
		device.setGateWay(null);
	} catch (Exception ex) {
		LogUtil.error("Error in updating device to "
				+ alertType.getAlertCommand());
	} finally {
		dbc.closeConnection();
	}
	return device;
}

public Device updateCommadParamOnlyForACOffandMode(
		String generatedDeviceId, String gateWayId, String commandParam,String setpointvalue) {
	DBConnection dbc = null;
	//String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r) from "
	String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), i.fileName, ast.id, ast.name,cf from "
			+ " Device as d left join d.deviceType as dt"
			+ " left join d.deviceConfiguration as cf"
			+ " left join d.location as l"
			+ " left join d.lastAlert as la"
			+ " left join d.rules as r"
			+ " left join d.icon as i"
			+ " left join d.armStatus as ast" 
			+ " where d.generatedDeviceId= '"
			+ generatedDeviceId
			+ "' and d.gateWay.macId ='" + gateWayId + "'";
	Device inDevice = null;
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		inDevice = (Device) object[0];
		
	//	LogUtil.info("HELLI COMMAND "+commandParam);
		if(commandParam.equals("0"))
		inDevice.setCommandParam(commandParam);
	
		acConfiguration configuration = (acConfiguration) object[9];
		if(!(commandParam.equals("5")))
		{
			
			configuration.setFanMode(setpointvalue);
			
		}
		else
		{
			if(inDevice.getCommandParam().equals("0"))
			inDevice.setCommandParam("22");
			
			if(configuration.getFanMode().equals("0"))
			configuration.setFanMode("2");	
		}
		
		Transaction tx = session.beginTransaction();
		session.update(inDevice);
		tx.commit();

		// We are creating new device to avoid the hibernate proxy mapping
		// problem.
		device = new Device();
		device.setId(inDevice.getId());
		device.setDeviceId(inDevice.getDeviceId());
		device.setFriendlyName(inDevice.getFriendlyName());
		device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
		device.setBatteryStatus(inDevice.getBatteryStatus());
		device.setCommandParam(inDevice.getCommandParam());
		device.setModelNumber(inDevice.getModelNumber());
		device.setEnableStatus(inDevice.getEnableStatus());
		device.setDeviceConfiguration(inDevice.getDeviceConfiguration());
		DeviceType deviceType = new DeviceType();
		deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
		deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
		device.setDeviceType(deviceType);
		Location location = new Location();
		location.setName(IMonitorUtil.convertToString(object[3]));
		device.setLocation(location);

		AlertType alertType = new AlertType();
		alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
		device.setLastAlert(alertType);

		Status armStatus = null;
		Long armStatusId = (Long) object[7];
		if(armStatusId != null){
			armStatus = new Status();
			String armStatusName = (String) object[8];
			armStatus.setId(armStatusId);
			armStatus.setName(armStatusName);
		}
		device.setArmStatus(armStatus);
		
		int count = Integer.parseInt(IMonitorUtil
				.convertToString(object[5]));
		if (count > 0) {
			device.setRules(new ArrayList<Rule>());
		}
		
		//Icon Bug
		//device.getIcon().setFileName(inDevice.getIcon().getFileName());
		device.setIcon(inDevice.getIcon());
		if(device.getIcon() != null) device.getIcon().setFileName(IMonitorUtil.convertToString(object[6]));
		
		if(device.getModelNumber().equals("HMD"))
		{
			
			String HMDquery = "select a.alertTime,a.id,a.alertValue,d.id,at.name from alertsfromimvg as a,device as d,alerttype as at where d.generatedDeviceId='"+ device.getGeneratedDeviceId() +"' and at.name='POWER_INFORMATION' and a.alertType=at.id and d.id=a.device order by a.alertTime desc limit 1";
			
		
			q = dbc.getSession().createSQLQuery(HMDquery);
			Object[] Hmdobjects = (Object[])q.uniqueResult();

			
		//Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(device.getGeneratedDeviceId());
	
		if(Hmdobjects != null && Hmdobjects[2]!= null)
		{
		
			device.setHMDalertValue((""+(String)Hmdobjects[2]));
			device.setHMDpowerinfo(""+(String)Hmdobjects[4]);
		
		}
		else
		{
			device.setHMDalertValue("");
			device.setHMDpowerinfo("");
		
		}
		}
		

	} catch (Exception e) {
		LogUtil.error("19.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}

	


public Device updateAllforacOfDeviceByGeneratedId(
		String generatedDeviceId, String gateWayId, String commandParam,String fanmode,String fanspeed,String acSwing) {
	DBConnection dbc = null;
	//String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r) from "
	String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), i.fileName, ast.id, ast.name,cf from "
			+ " Device as d left join d.deviceType as dt"
			+ " left join d.deviceConfiguration as cf"
			+ " left join d.location as l"
			+ " left join d.lastAlert as la"
			+ " left join d.rules as r"
			+ " left join d.icon as i"
			+ " left join d.armStatus as ast" 
			+ " where d.generatedDeviceId= '"
			+ generatedDeviceId
			+ "' and d.gateWay.macId ='" + gateWayId + "'";
	Device inDevice = null;
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		inDevice = (Device) object[0];
		
		inDevice.setCommandParam(commandParam);
	
		
		//LogUtil.info("fanmode---"+fanmode+"acSwing"+acSwing+"fanspeed"+fanspeed);
		
		acConfiguration configuration = (acConfiguration) object[9];
		configuration.setFanMode(fanmode);
		configuration.setAcSwing(acSwing);
		configuration.setFanModeValue(fanspeed);
		
		
		
		Transaction tx = session.beginTransaction();
		session.update(inDevice);
		tx.commit();

		// We are creating new device to avoid the hibernate proxy mapping
		// problem.
		device = new Device();
		device.setId(inDevice.getId());
		device.setDeviceId(inDevice.getDeviceId());
		device.setFriendlyName(inDevice.getFriendlyName());
		device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
		device.setBatteryStatus(inDevice.getBatteryStatus());
		device.setCommandParam(inDevice.getCommandParam());
		device.setModelNumber(inDevice.getModelNumber());
		device.setEnableStatus(inDevice.getEnableStatus());
		device.setDeviceConfiguration(inDevice.getDeviceConfiguration());
		DeviceType deviceType = new DeviceType();
		deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
		deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
		device.setDeviceType(deviceType);
		Location location = new Location();
		location.setName(IMonitorUtil.convertToString(object[3]));
		device.setLocation(location);

		AlertType alertType = new AlertType();
		alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
		device.setLastAlert(alertType);

		Status armStatus = null;
		Long armStatusId = (Long) object[7];
		if(armStatusId != null){
			armStatus = new Status();
			String armStatusName = (String) object[8];
			armStatus.setId(armStatusId);
			armStatus.setName(armStatusName);
		}
		device.setArmStatus(armStatus);
		
		int count = Integer.parseInt(IMonitorUtil
				.convertToString(object[5]));
		if (count > 0) {
			device.setRules(new ArrayList<Rule>());
		}
		
		//Icon Bug
		//device.getIcon().setFileName(inDevice.getIcon().getFileName());
		device.setIcon(inDevice.getIcon());
		if(device.getIcon() != null) device.getIcon().setFileName(IMonitorUtil.convertToString(object[6]));
		
		if(device.getModelNumber().equals("HMD"))
		{
			
			String HMDquery = "select a.alertTime,a.id,a.alertValue,d.id,at.name from alertsfromimvg as a,device as d,alerttype as at where d.generatedDeviceId='"+ device.getGeneratedDeviceId() +"' and at.name='POWER_INFORMATION' and a.alertType=at.id and d.id=a.device order by a.alertTime desc limit 1";
			
		
			q = dbc.getSession().createSQLQuery(HMDquery);
			Object[] Hmdobjects = (Object[])q.uniqueResult();

			
		//Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(device.getGeneratedDeviceId());
	
		if(Hmdobjects != null && Hmdobjects[2]!= null)
		{
		
			device.setHMDalertValue((""+(String)Hmdobjects[2]));
			device.setHMDpowerinfo(""+(String)Hmdobjects[4]);
		
		}
		else
		{
			device.setHMDalertValue("");
			device.setHMDpowerinfo("");
		
		}
		}
		

	} catch (Exception e) {
		LogUtil
				.error("20.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}

public Object[] checkDeviceAndReturnCommunicationDetails(String customerId) {
	String query = "select "
			+ " g.macId, c.customerId, a.ip, a.controllerReceiverPort"
			+ " from gateWay as g" 
			
			+ " left join g.customer as c" 
			+ " left join g.agent as a"

			+ " where c.customerId='" + customerId +"'";
	
	
	DBConnection dbc = null;
	Object[] objects = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		objects = (Object[]) q.uniqueResult();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}


public boolean createSystemDevicesFordashboard(GateWay gateWay){
	boolean result = false;
	DBConnection dbc = null;
	dashboardDBConnection dashboard = null;
	long custid =gateWay.getCustomer().getId();
	
	String macId=gateWay.getMacId();
	
	
	String query ;
	//query to create System Device-HOME
//	query = "insert into device(deviceId, gateWay, generatedDeviceId, friendlyName, deviceType, commandParam, location, enableList)"
//			+ " select 'ENERGY_MONITOR', "+gateWayId+", '"+macId+"-ENERGY_MONITOR', 'ENERGY_MONITOR', dt.id, '0', l.id, '0' from location as l, customer as c , devicetype dt, gateway g "
//			+ " where l.customer=c.id  and l.name='Default Room' and dt.name='Z_WAVE_ENERGY_MONITOR' and c.id=g.customer and  g.id='"+gateWayId+"' ";
//	
	
	
query = "select d from Device as d where d.deviceId='ENERGY_MONITOR' and d.generatedDeviceId='"+macId+"-ENERGY_MONITOR'";
	
	
	

		try {
			dbc = new DBConnection();
			Session sess1 = dbc.openConnection();
			Query q = sess1.createQuery(query);
			Device deviceFromDb = (Device)q.uniqueResult();
			dbc.closeConnection();
			
			if(deviceFromDb==null)		
			{
			
		query="insert into device(deviceId, gateWay, generatedDeviceId, friendlyName, deviceType, commandParam, location, enableList) " +
				" select 'ENERGY_MONITOR', g.id, '"+macId+"-ENERGY_MONITOR', 'ENERGY_MONITOR'," +
				" dt.id, '0', l.id, '0' from (select ll.id as id,ll.customer as" +
				" customer from location as ll where" +
				" ll.name='Default Room' and ll.customer="+custid+")" +
				" as l,(select dtt.id as id,dtt.name as name from devicetype " +
				"as dtt where dtt.name='Z_WAVE_ENERGY_MONITOR')as dt,(select gg.id as id,gg.macId as macId from gateway " +
				"as gg where gg.macId='"+macId+"')as g";
		
		//LogUtil.info("query-------"+query);
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Transaction tx = session.beginTransaction();
				 q = session.createSQLQuery(query);
				q.executeUpdate();
			
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			dbc.closeConnection();
		}
			
			}
			else
			{
				
				result = true;
			}
			
			result = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			dbc.closeConnection();
		}
			
		//to add default dashboatd configuration	
			
		
		query = "select dc from dashboardconfig as dc where dc.customer="+custid+"";
			
			
			
			//LogUtil.info(query);
				try {
					dashboard = new dashboardDBConnection();
					Session sess1 = dashboard.openConnection();
					Query q = sess1.createQuery(query);
					dashboardconfig deviceFromDb = (dashboardconfig)q.uniqueResult();
					dashboard.closeConnection();
					//LogUtil.info("deviceFromDb----"+deviceFromDb);
					if(deviceFromDb==null)		
					{
					
				query="insert into dashboardconfig(customer,alertgenerationtimeinsec)values("+custid+",10)";
				
				//LogUtil.info("query-------"+query);
				try {
					dashboard = new dashboardDBConnection();
					dashboard.openConnection();
					Session session = dashboard.getSession();
					Transaction tx = session.beginTransaction();
						 q = session.createSQLQuery(query);
						q.executeUpdate();
					
					tx.commit();
					result = true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				} finally {
					dashboard.closeConnection();
				}
					
					}
					else
					{
						
						result = true;
					}
					
					result = true;
					
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				} finally {
					dashboard.closeConnection();
				}
		
		//end to add default dashboatd configuration
	return result;
}

public boolean deleteSystemDevicesFordashboard(GateWay gateWay){
	boolean result = false;
	DBConnection dbc = null;
	dashboardDBConnection dashboard = null;
	long custid =gateWay.getCustomer().getId();
	
	String macId=gateWay.getMacId();
	
	String query ;
	//query to create System Device-HOME
//	query = "insert into device(deviceId, gateWay, generatedDeviceId, friendlyName, deviceType, commandParam, location, enableList)"
//			+ " select 'ENERGY_MONITOR', "+gateWayId+", '"+macId+"-ENERGY_MONITOR', 'ENERGY_MONITOR', dt.id, '0', l.id, '0' from location as l, customer as c , devicetype dt, gateway g "
//			+ " where l.customer=c.id  and l.name='Default Room' and dt.name='Z_WAVE_ENERGY_MONITOR' and c.id=g.customer and  g.id='"+gateWayId+"' ";
//	
	
	query = "select d from Device as d where d.deviceId='ENERGY_MONITOR' and d.generatedDeviceId='"+macId+"-ENERGY_MONITOR'";
	
	
	
	
		try {
			dbc = new DBConnection();
			Session sess1 = dbc.openConnection();
			Query q = sess1.createQuery(query);
			Device deviceFromDb = (Device)q.uniqueResult();
			dbc.closeConnection();
			
			if(deviceFromDb != null)
			{
			
				
				query = "delete from device where deviceId='ENERGY_MONITOR' and generatedDeviceId='"+macId+"-ENERGY_MONITOR'";
             
				//LogUtil.info("query-------"+query);
				try {
					dbc = new DBConnection();
					dbc.openConnection();
					Session session = dbc.getSession();
					Transaction tx = session.beginTransaction();
						 q = session.createSQLQuery(query);
						q.executeUpdate();
					
					tx.commit();
					result = true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				} finally {
					dbc.closeConnection();
				}
			}
			else
			{
				
				result = true;
			}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				dbc.closeConnection();
			}
		
		//delete the dashboard config if exists
		
		query = "select dc from dashboardconfig as dc where dc.customer="+custid+"";
		
				
			try {
				dashboard = new dashboardDBConnection();
				Session sess1 = dashboard.openConnection();
				Query q = sess1.createQuery(query);
				dashboardconfig deviceFromDb = (dashboardconfig)q.uniqueResult();
				dashboard.closeConnection();
				
		
				if(deviceFromDb != null)
				{
		
					
					query = "delete from dashboardconfig where customer="+custid+"";
	             
			
					try {
						dashboard = new dashboardDBConnection();
						dashboard.openConnection();
						Session session = dashboard.getSession();
						Transaction tx = session.beginTransaction();
							 q = session.createSQLQuery(query);
							q.executeUpdate();
						
						tx.commit();
						result = true;
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					} finally {
						dashboard.closeConnection();
					}
				}
				else
				{
					
					result = true;
				}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				} finally {
					dashboard.closeConnection();
				}
			
		
		//end delete dashboard config
	
	return result;
}
//bhavya end

	/**
	 * @author sumit kumar
	 * @param user
	 * @param deviceIdList
	 * @return
	 */
	public boolean updateAccessibleDevicesForSubUser(User user, String deviceIdList){
		boolean result = false;
		String deviceIds[] = deviceIdList.split(",");
		DaoManager daoManager = new DaoManager();
		boolean deleteStatus = false;
		boolean insertStatus = false;
		
		//1. Delete entries in subUserDeviceAccess table with [user = subuserId].
		String deleteQuery  = "delete from subUserDeviceAccess where user="+user.getId()+" ";
		deleteStatus = daoManager.executeSQLQuery(deleteQuery);
		
		//2. Update Scenarios to update subUserDeviceAccess table.
		for(String deviceID : deviceIds){
			try {
				if(deviceID != null && deviceID != ""){
					String query = "insert into subUserDeviceAccess(user,device) values("+user.getId()+","+Long.parseLong(deviceID)+") ";
					insertStatus = daoManager.executeSQLQuery(query);
				}
			} catch (NumberFormatException e) {
				LogUtil.info("Got Exception while updating subUserDeviceAccess : ", e);
			}
		}
		
		if(deleteStatus && insertStatus) result =true;
		return result;
	
	}
	
	public boolean updateDoorLock(DoorLockConfiguration doorLockConfig) {
		boolean result = false;
		try {
			daoManager.update(doorLockConfig);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
	
	public Make getMakeById(String makeId){
		Make make = new Make();
		DBConnection dbc = null;
		try {
			String query = "select m.id,m.name,m.number,m.deviceType from "
				+ " Make as m where m.id=" + makeId+"";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Object[] object = (Object[]) q.uniqueResult();
			make.setId((Long)object[0]);
			make.setName(IMonitorUtil.convertToString(object[1]));
			make.setNumber(IMonitorUtil.convertToString(object[2]));
			make.setDeviceType((DeviceType)(object[3]));
			
			
		    
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return make;
	}
	
	
	
	public String getLearnValuesOfAc(long id) {
		DBConnection dbc = null;
		String learnedValues = "";
		try {
			String query = "";
			query += " select acLearnValue from acConfiguration "
					+ " where id = "+ id;
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			learnedValues = IMonitorUtil.convertToString(q.uniqueResult());
			
		} catch (Exception ex) {
			LogUtil.info("Caught Exception  while getting Ac Brands : ", ex);
		} finally {
			dbc.closeConnection();
		}
		return learnedValues;
	}
	
	public void updateAcConfigurationWithSensedTemperature(long id, String Value) {
		DBConnection dbc = null;
	
		
		try {
			
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String hql =  "update acConfiguration ac set ac.sensedTemperature=" + Value
							+ " where ac.id ="+id+"";
			
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
	
	/*Added by Naveen for minimote Configuration
	 * Date: 23 June 2014
	 * To get the device list under gateway to list in jsp
	 */
	
	@SuppressWarnings("unchecked")
	public  List<Device> listDevicesByMacIdforMinimoteConfig(String macId) {
		List<Device> devices = null;
		DBConnection dbc = null;
		String query = "";

	/*	query += "select d.id, d.generatedDeviceId, d.friendlyName, g.macId, dt.name from "
				+ " Device as d join d.gateWay as g join d.deviceType as dt"
				+ " where dt.name = 'MODE_STAY' or dt.name = 'MODE_HOME' or dt.name = 'MODE_AWAY' or dt.name = 'Z_WAVE_SWITCH' or dt.name = 'Z_WAVE_DIMMER' or dt.name = 'Z_WAVE_SIREN' and d.gateWay = g.id and g.macId='" + macId +"'" ;*/
		
		query += "select d.id, d.generatedDeviceId, d.friendlyName, dt.name from Device as d join d.gateWay as g join d.deviceType as dt where d.gateWay = g.id "+
				  "and d.deviceType = dt.id and g.macId='"+macId+"'";
		
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			List<Object[]> list = q.list();
			devices = new ArrayList<Device>();
			for(Object[] object : list){
				Device device = new Device();
				String deviceTypeName = IMonitorUtil.convertToString(object[3]);
				if((deviceTypeName.equals("Z_WAVE_SWITCH")) || (deviceTypeName.equals("Z_WAVE_DIMMER")) || (deviceTypeName.equals("Z_WAVE_SIREN")) || (deviceTypeName.equals("MODE_HOME")) ||
						(deviceTypeName.equals("MODE_STAY")) || (deviceTypeName.equals("MODE_AWAY"))){
				device.setId((Long)object[0]);
				device.setGeneratedDeviceId(IMonitorUtil.convertToString(object[1]));
				device.setFriendlyName(IMonitorUtil.convertToString(object[2]));
				
				devices.add(device);
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		
		return devices;
	}
	
	//Added by Naveen For saving minimote configuration
	
	public boolean updateMinimoteByGeneratedDeviceId(String generatedDeviceId,
			MinimoteConfig minimote) {
		DBConnection dbc = null;
		String hql = "update device d,minimoteconfiguration m set m.buttonone='" + minimote.getButtonone() +"',m.buttontwo='" + minimote.getButtontwo() +"', m.buttonthree='"+minimote.getButtonthree()+"', m.buttonfour='"+minimote.getButtonfour()+"'" +
				" where d.generatedDeviceId='" + generatedDeviceId + "' and d.deviceconfiguration=m.id";
			boolean result = false;
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
		
		return result;
	}
	//End
	
	public void deleteDeviceDetailsfromDashboard(String friendlyName, long id) {
	
		dashboardDBConnection dbc = null;
		String query = "";
        
		
		query += "delete from devicedetails where deviceName='"+friendlyName+"' and did='"+id+"'";
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			Transaction tx = dbc.getSession().beginTransaction();
			q.executeUpdate();
			tx.commit();
				
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		
		
	}
	
	public void RemoveDevicePositionIndex(Device device){
		String query ="delete from locationMap  where device="+device.getId();
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			q.executeUpdate();
			
			device.setLocationMap(null);
			session.update(device);
			
			Transaction tx = session.beginTransaction();
			tx.commit();
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		
	}
	
	
	public static Object[] getFriendlyNameByDeviceId(String Id, Customer customer){
		
		Object[] object = null;
		DBConnection dbc = null;
		try{
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			
				String query = "select d.friendlyName,l.name "
						+ " from Device as d"
						+ " left join d.gateWay as g"
						+ " left join g.customer as c"
						+ " left join d.location as l"
						+ " where d.deviceId='"+Id+"' and c.id='"+ customer.getId() + "'";
			
				Query q = session.createQuery(query);
				object = (Object[]) q.uniqueResult();
				
			    
		}catch(Exception ex){
			LogUtil.error("Error while retrieving friendlyName and location by deviceId");
			ex.printStackTrace();
		}finally{
			dbc.closeConnection();	
		}
		return object;
	}

	
	public int getDeviceCountPerGateway(long id){
		
		int count = 0;
		DBConnection dbc = null;
		
		String query = "select count(*) from  device as d"
               + " where d.gateway="+id+" and d.deviceId not in ('AWAY','HOME','STAY','ENERGY_MONITOR')";
		
		
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			BigInteger lCount  = (BigInteger) q.uniqueResult();
			count = lCount.intValue();
		} catch (Exception e) {
			LogUtil.info("could not count the device fro gateway: "+ id);
		}
		
		
		return count;
	}
	public Device getDeviebyGeneratedId(String generatedId){
		DBConnection dbc = null;
		Device device= null;
		try {
			String query = "select * from device where generatedDeviceId='"+generatedId+"'";
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			
			Query q = session.createSQLQuery(query);
			device =(Device) q.uniqueResult();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return device;	
		
	}
	
	/*public List<Object[]> getdevicedetailsforalexa(String customerId){
		DBConnection dbc = null;
		String query="select deviceId,gateWay,friendlyName,dd.name,switchType from device as d left join gateway as g on d.gateWay=g.id left join customer as c on g.customer=c.id left join devicetype as dd on d.deviceType=dd.id where c.customerId='"+customerId+"'"+" and dd.name in ('Z_WAVE_SWITCH','Z_WAVE_DIMMER','Z_WAVE_AC_EXTENDER')";
		
		
		List<Object[]> objects=null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();	
			Query q = session.createSQLQuery(query);
			objects=q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
		return objects;
	}*/
	
	//CHanged by Apoorva for IDus discovery Alexa
	@SuppressWarnings("unchecked")
	public List<Object[]> getdevicedetailsforalexa(String customerId){
		
		XStream stream = new XStream();
		DBConnection dbc = null;
		String query="select d.deviceId,d.gateWay,d.friendlyName,dd.name,d.switchType,d.id from device as d left join gateway as g on d.gateWay=g.id left join customer as c on g.customer=c.id left join devicetype as dd on d.deviceType=dd.id where c.customerId='"+customerId+"'"+" and dd.name in ('Z_WAVE_MULTI_SENSOR','Z_WAVE_DOOR_SENSOR','Z_WAVE_AV_BLASTER','Z_WAVE_SWITCH','DEV_ZWAVE_RGB','Z_WAVE_DIMMER','Z_WAVE_AC_EXTENDER','VIA_UNIT')";
		
		LogUtil.info("query main user"+query);
		
		List<Object[]> objects=null;
		List<Object[]> deviceList=new ArrayList<Object[]>();
		
			
			
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();	
			Query q = session.createSQLQuery(query);
			objects=q.list();
			//LogUtil.info("devicelog check"+stream.toXML(objects));
			
			for (Object[] details : objects) 
			{
				String deviceTypeName = IMonitorUtil.convertToString(details[3]);
				BigInteger deviceId = (BigInteger) details[5];
				if (deviceId == null) {
					continue;
				}
			
				
				if(deviceTypeName.equals(Constants.VIA_UNIT))
				{
					IndoorUnitConfiguration indoorUnitConfiguration = null;
					String query1 = "select d.deviceConfiguration from Device as d where d.id="+deviceId; 
					Query q1 = session.createQuery(query1);
					indoorUnitConfiguration = (IndoorUnitConfiguration) q1.uniqueResult();
					if(indoorUnitConfiguration.getConnectStatus() == 0)
					{
						continue;
					}
				}
				
				deviceList.add(details);
				
			}
			
			
		} catch (Exception ex) 
		{
			ex.printStackTrace();
			LogUtil.info("Error ::: "+ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		LogUtil.info("devicelog check main user"+stream.toXML(deviceList));
		return deviceList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getDeviceDetailsForAlexaVideo(String customerId){
		
		XStream stream = new XStream();
		DBConnection dbc = null;
		String query="select d.deviceId,d.gateWay,d.friendlyName,dd.name,d.switchType,d.id,d.modelNumber from device as d left join gateway as g on d.gateWay=g.id left join customer as c on g.customer=c.id left join devicetype as dd on d.deviceType=dd.id where c.customerId='"+customerId+"'"+" and dd.name='Z_WAVE_AV_BLASTER'";
		
		LogUtil.info("getdevicedetailsforAlexaVideo query"+query);
		
		List<Object[]> objects=null;
		List<Object[]> deviceList=new ArrayList<Object[]>();
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();	
			Query q = session.createSQLQuery(query);
			objects=q.list();
			//LogUtil.info("devicelog check"+stream.toXML(objects));
			
			for (Object[] details : objects) 
			{
				String deviceTypeName = IMonitorUtil.convertToString(details[3]);
				
				BigInteger deviceId = (BigInteger) details[5];
				if (deviceId == null) {
					continue;
				}
				
				
				if(deviceTypeName.equals(Constants.VIA_UNIT))
				{
					IndoorUnitConfiguration indoorUnitConfiguration = null;
					String query1 = "select d.deviceConfiguration from Device as d where d.id="+deviceId; 
					Query q1 = session.createQuery(query1);
					indoorUnitConfiguration = (IndoorUnitConfiguration) q1.uniqueResult();
					if(indoorUnitConfiguration.getConnectStatus() == 0)
					{
						continue;
					}
				}
				
				deviceList.add(details);
			}
			
		} catch (Exception ex) 
		{
			ex.printStackTrace();
			LogUtil.info("Error ::: "+ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		LogUtil.info("devicelog check for alexa video deviceList=="+stream.toXML(deviceList));
		return deviceList;
	}
	
	public Alexa getdetailsforalexanormaluser(String customerId,String tokenXml){
		LogUtil.info("Alexa user details");
		
		XStream stream=new XStream();
		String token = (String)stream.fromXML(tokenXml);
		DBConnection dbc = null;
		
		String query="select a.accesstoken,a.refreshToken,c.customerId,u.id,u.userId,r.name from Alexa as a left join customer as c on a.customer=c.id left join user as u on a.user=u.id  left join role as r on u.role=r.id  where c.customerId='"+customerId+"'"+" and a.accesstoken='"+token+"'";
		LogUtil.info("query=:"+query);
		
		Alexa alexa = null;
	
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();	
			Query q = session.createSQLQuery(query);
		
		//	LogUtil.info("devicelog query list"+stream.toXML(q));
			Object[] object = (Object[]) q.uniqueResult();
			
			if(object != null){
			String accesstoken = IMonitorUtil.convertToString(object[0]);
			String refreshToken = IMonitorUtil.convertToString(object[1]);
			String customerid=IMonitorUtil.convertToString(object[2]);
			long id=Long.parseLong(IMonitorUtil.convertToString(object[3]));
			String userId=IMonitorUtil.convertToString(object[4]);
			String roleName = IMonitorUtil.convertToString(object[5]);
			
			alexa=new Alexa();
			alexa.setAccesstoken(accesstoken);
			alexa.setRefreshToken(refreshToken);
			
			Customer customer =new Customer();
			customer.setCustomerId(customerid);
			alexa.setCustomer(customer);
			
			User user=new User();
			user.setId(id);
			user.setUserId(userId);
			
			
			Role role =new Role();
			role.setName(roleName);
			user.setRole(role);
			alexa.setUser(user);
			
			}
			
			} catch (Exception e) {
			LogUtil.info("Error ::: "+e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		
		LogUtil.info("devicelog check for alexa"+stream.toXML(alexa));
		return alexa;
	}
	
	//Google home discovery
	
	
	public List<Object[]> getdevicedetailsforgoogle(String customerId){
		LogUtil.info("google home ");
		
		DBConnection dbc = null;
		String query="select d.deviceId,d.gateWay,d.friendlyName,dd.name,d.switchType,d.id from device as d left join gateway as g on d.gateWay=g.id left join customer as c on g.customer=c.id left join devicetype as dd on d.deviceType=dd.id where c.customerId='"+customerId+"'"+" and dd.name in ('Z_WAVE_SWITCH','DEV_ZWAVE_RGB','Z_WAVE_DIMMER','Z_WAVE_AC_EXTENDER','VIA_UNIT')";
		
		List<Object[]> objects=null;
		List<Object[]> deviceList=new ArrayList<Object[]>();
		
			try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();	
			Query q = session.createSQLQuery(query);
			objects=q.list();
			for (Object[] details : objects) 
			{
				String deviceTypeName = IMonitorUtil.convertToString(details[3]);
				BigInteger deviceId = (BigInteger) details[5];
				if (deviceId == null) {
					continue;
				}
			
				
				if(deviceTypeName.equals(Constants.VIA_UNIT))
				{
					IndoorUnitConfiguration indoorUnitConfiguration = null;
					String query1 = "select d.deviceConfiguration from Device as d where d.id="+deviceId; 
					Query q1 = session.createQuery(query1);
					indoorUnitConfiguration = (IndoorUnitConfiguration) q1.uniqueResult();
					if(indoorUnitConfiguration.getConnectStatus() == 0)
					{
						continue;
					}
				}
				deviceList.add(details);
			}
			
			
		} catch (Exception ex) 
		{
			ex.printStackTrace();
			LogUtil.info("Error ::: "+ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return deviceList;
	}
	
	
	
	
	public String getDeviceByDid(String generated,long gatwayid){
		XStream stream=new XStream();
		String deviceid=null;
		DBConnection dbc = null;
		  String query = "select generatedDeviceId from device where deviceId='" + generated + "' and gateway='"+ gatwayid +"'";
		  try{
			dbc=new DBConnection();
			Session session = dbc.openConnection();	
			Query q = session.createSQLQuery(query);
			deviceid=(String) q.uniqueResult();
		  } catch (Exception ex) {
			  LogUtil.error("3.Error when retreiving device by friendlyname : " + generated + "" + ex.getMessage());
				ex.printStackTrace();
		 } finally {
				dbc.closeConnection();
			}
			
			return deviceid;
		  
	}
	
public Device getDeviceWithStatus(String gatewayId,String deviceId){
	
	DBConnection dbc = null;
	String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand from "
			+ " Device as d left join d.deviceType as dt left join d.location as l left join d.lastAlert as la " +
					" left join d.gateWay as g" +
					" where d.deviceId= '"
			+ deviceId
			+ "' and g.id ='" + gatewayId + "'";
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		device = (Device) object[0];
		
		DeviceType deviceType = new DeviceType();
		deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
		deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
		device.setDeviceType(deviceType);

		Location location = new Location();
		location.setName(IMonitorUtil.convertToString(object[3]));
		device.setLocation(location);

		AlertType alertType = new AlertType();
		alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
		device.setLastAlert(alertType);
		device.setMake(null);
		device.setLocationMap(null);
		device.setDeviceConfiguration(null);

	} catch (Exception e) {
		LogUtil
				.error("4.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
	
	
	
}

public Device getDeviceByGeneratedIdWithStatus(String deviceId){
	
	DBConnection dbc = null;
	String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand,dt.name from "
			+ " Device as d left join d.deviceType as dt left join d.location as l left join d.lastAlert as la " +
					" left join d.gateWay as g" +
					" where d.generatedDeviceId= '"
			+ deviceId + "'";
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		device = (Device) object[0];
		
		DeviceType deviceType = new DeviceType();
		deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
		deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
		deviceType.setName(IMonitorUtil.convertToString(object[5]));
		device.setDeviceType(deviceType);

		Location location = new Location();
		location.setName(IMonitorUtil.convertToString(object[3]));
		device.setLocation(location);

		AlertType alertType = new AlertType();
		alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
		device.setLastAlert(alertType);
		device.setMake(null);
		device.setLocationMap(null);
		device.setDeviceConfiguration(null);

	} catch (Exception e) {
		LogUtil
				.error("4.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}


@SuppressWarnings("unchecked")
	public List<Object[]> listAllFunctions() {
		
		String query = "";
        query += "select f.id,f.name,f.category,f.functionId from functions as f ";
                DBConnection dbc = null;
                List<Object[]> functions =null;
                
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			functions= q.list();
		} catch (Exception ex) {
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
			return functions;
	}
	
	public List<Object[]> listActionDevices(String devicetype, long gatewayId)
	{
		String query = null;
		if(devicetype.equals(Constants.DEV_ZWAVE_SCENE_CONTROLLER)) {
			query = "select d.id,d.deviceId,d.gateWay,d.generatedDeviceId,d.friendlyName,d.batteryStatus,d.modelNumber,d.deviceType," +
					"d.commandParam,d.lastAlert, d.armStatus,d.make,d.location,d.deviceConfiguration,d.enableStatus,d.icon,d.mode," +
					"d.enableList,d.posIdx,d.locationMap,d.switchType,d.devicetimeout,d.pulseTimeOut,dt.name " +
					"from device as d join devicetype as dt on dt.id=d.deviceType where d.gateWay="+gatewayId+" and dt.name in ('"+devicetype+"','DEV_ZWAVE_FIB_KEYFOBE') group by d.deviceId";
		LogUtil.info("query: " + query);
		}else {
		
		query = "select d.id,d.deviceId,d.gateWay,d.generatedDeviceId,d.friendlyName,d.batteryStatus,d.modelNumber,d.deviceType," +
				"d.commandParam,d.lastAlert, d.armStatus,d.make,d.location,d.deviceConfiguration,d.enableStatus,d.icon,d.mode," +
				"d.enableList,d.posIdx,d.locationMap,d.switchType,d.devicetimeout,d.pulseTimeOut,dt.name " +
				"from device as d join devicetype as dt on dt.id=d.deviceType where d.gateWay="+gatewayId+" and dt.name='"+devicetype+"' group by d.deviceId";
     
		}
    
              DBConnection dbc = null;
              List<Object[]> devices =null;
            
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			
			Query q = dbc.getSession().createSQLQuery(query);
			devices= q.list();
			
		} catch (Exception ex) {
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		
		return devices;
		
	}
	
/*	@SuppressWarnings("unchecked")
	public GateWay getGateWay(long id) {
		DBConnection dbc = null;
		GateWay gateway = null;
		LogUtil.info(" getGateWay from Db started");
		try {
			String query = "";
			query += "select * from gateway where customer="+id;
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			LogUtil.info(query);
			LogUtil.info(" getGateWay from Db started step 1 ");
			Query q = session.createSQLQuery(query);
			LogUtil.info(" getGateWay from Db started step 2");
			gateway =(GateWay) q.uniqueResult();
			LogUtil.info("Valur from Database is "+gateway);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		LogUtil.info(" getGateWay from Db ended");
		return gateway;
	}*/
	
	
public long getFunctionIdBasedOnNameAndCategory(String category,String name){

		long functionId = 0;
		DBConnection dbc = null;
		
		//String query = "select f.functionId from functions as f where f.name='"+name+"' and f.category='"+category+"'";
		//Naveen commented to fix bug. Actualy to save function table id
		
		String query = "select f.functionId from functions as f where f.name='"+name+"' and f.category='"+category+"'";
		
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			BigInteger lCount  = (BigInteger) q.uniqueResult();
			functionId = lCount.longValue();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbc.closeConnection();
		}
		
		
		return functionId;
	}

public Action getDeviceActionByid(long id){
	
	DBConnection dbc = null;
	String query ="select a.actionName,d.id,d.deviceId,d.friendlyName,f.functionId,f.name,f.category,a.id, a.configurationId,d.generatedDeviceId " +
			"from action as a left join device as d on a.device=d.id left join functions as f on f.id=a.functions " +
			"where a.id="+id+" and a.device=d.id and a.functions=f.id";
	
	
	Action action = new Action();
	try 
	{
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createSQLQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		
		if(object != null){
			
		
		action.setActionName(IMonitorUtil.convertToString(object[0]));
		long actionid=Long.parseLong(IMonitorUtil.convertToString(object[7]));
		action.setId(id);
		long confId=Long.parseLong(IMonitorUtil.convertToString(object[8]));
		action.setConfigurationId(confId);
		Device device=new Device();
		long deviceid=Long.parseLong(IMonitorUtil.convertToString(object[1]));
		device.setId(deviceid);
		device.setDeviceId(IMonitorUtil.convertToString(object[2]));
		device.setFriendlyName(IMonitorUtil.convertToString(object[3]));
		device.setGeneratedDeviceId (IMonitorUtil.convertToString(object[9]));
		Functions functions=new Functions();
		long functionid=Long.parseLong(IMonitorUtil.convertToString(object[4]));
		functions.setFunctionId(functionid);
		functions.setName(IMonitorUtil.convertToString(object[5]));
		functions.setCategory(IMonitorUtil.convertToString(object[6]));
		action.setDevice(device);
		action.setFunctions(functions);
		}else{
			
			query ="select a.actionName,s.id,s.name ,f.functionId,f.name ,f.category,a.id, a.configurationId " +
					"from action as a left join scenario as s on a.scenario=s.id left join functions as f on f.id=a.functions " +
					"where a.id="+id+" and a.scenario=s.id and a.functions=f.id";
			dbc = new DBConnection();
			dbc.openConnection();
			Session sessionn = dbc.getSession();
			Query q1 = sessionn.createSQLQuery(query);
			Object[] sObject = (Object[]) q1.uniqueResult();
			
			action.setActionName(IMonitorUtil.convertToString(sObject[0]));
			action.setId(id);
			long confId=Long.parseLong(IMonitorUtil.convertToString(sObject[6]));
			action.setConfigurationId(confId);
			Scenario scenario = new Scenario();
			long scenarioId=Long.parseLong(IMonitorUtil.convertToString(sObject[1]));
			scenario.setId(scenarioId);
			scenario.setName(IMonitorUtil.convertToString(sObject[2]));
			Functions functions=new Functions();
			long functionid=Long.parseLong(IMonitorUtil.convertToString(sObject[3]));
			functions.setFunctionId(functionid);
			functions.setName(IMonitorUtil.convertToString(sObject[4]));
			functions.setCategory("SCENARIO");
			action.setScenario(scenario);
			action.setFunctions(functions);
		}
	} 
	catch (Exception e)
	{
		e.printStackTrace();
		// TODO: handle exception
	}
	
	 finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return action;
	
}

public List<Object[]> listAllActionsByCustomerId(GateWay gateway)
{
	LogUtil.info(" listAllActionsByCustomerId ");
	XStream stream = new XStream();
	String query = "";
    query += "select a.id,a.actionName,a.parameter " 
			+ " from Action as a" 
    		+ " left join a.device as d" 
			+ " left join d.gateWay as g" 
            + " where g.id="+gateway.getId()
            + " and d.gateWay=g.id ";
            DBConnection dbc = null;
            List<Object[]> actions =null;
            
            
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		actions= q.list();
	} catch (Exception ex) {
		LogUtil.error(ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	
	LogUtil.info(" listAllActionsByCustomerId actions "+stream.toXML(actions));
	return actions;
}

public List<Object[]> listAllScenarioActionsByCustomerId(GateWay gateway)
{
	XStream stream = new XStream();
	String query = "";
    query += "select a.id,a.actionName,a.parameter " 
			+ " from Action as a" 
    		+ " left join a.scenario as s" 
			+ " left join s.gateWay as g" 
            + " where g.id="+gateway.getId()
            + " and s.gateWay=g.id ";
            DBConnection dbc = null;
            List<Object[]> actions =null;
            
            
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		actions= q.list();
	} catch (Exception ex) {
		LogUtil.error(ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	
	return actions;
}

@SuppressWarnings("unchecked")
public List<Object[]> getKeyConfigListbyGeneratedId(Device device)
{
	XStream stream = new XStream();
	String query="";
	query +="select k.id,k.device,k.keyName,k.pressType,k.action,k.keyCode,k.posindex,a.actionName " +
			" from keyconfiguration as k left join device as d on k.device=d.id left join action as a on k.action=a.id " +
			" where d.id='"+device.getId()+"'" +
					" order by k.posindex"; // Naveen added k.id to fix key ordering issue.
	
	 DBConnection dbc = null;
     List<Object[]> keyConfigs =null;
	/*List<KeyConfiguration> keyConfig = daoManager
			.list("device", device, KeyConfiguration.class);*/

     try {
 		dbc = new DBConnection();
 		dbc.openConnection();
 		Query q = dbc.getSession().createSQLQuery(query);
 		keyConfigs= q.list();
 	} catch (Exception ex) {
 		LogUtil.error(ex.getMessage());
 	} finally {
 		dbc.closeConnection();
 	}
 	
	return keyConfigs;
}

@SuppressWarnings("unchecked")
public List<KeyConfiguration> getKeysByDevice(Device device) {
	List<KeyConfiguration> keys = daoManager
			.list("device", device, KeyConfiguration.class);
	return keys;
}

public Action getActionByid(long id){
	
	LogUtil.info("getActionByid" + id);
	

	
	DBConnection dbc = null;
	String query ="select a.id,a.actionName " +
			"from action as a " +
			"where a.id="+id+"";
	
	LogUtil.info("getActionByid" + query);
	
	Action action = new Action();
	try 
	{
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createSQLQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		
		
			
		
		action.setActionName(IMonitorUtil.convertToString(object[1]));
		action.setId(id);
		
		
	} 
	catch (Exception e)
	{
		e.printStackTrace();
		// TODO: handle exception
	}
	
	 finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
	
	return action;
	
}

public boolean deleteDeviceKeyConfiguration(Device device) {
	
	DBConnection dbc = null;
	String query = "";
    boolean result = false;
	
	query += "delete from keyconfiguration where device='"+device.getId()+"'";
	
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createSQLQuery(query);
		Transaction tx = dbc.getSession().beginTransaction();
		q.executeUpdate();
		tx.commit();
		result = true;
			
	} catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.error(ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	
	return result;
}
//12.2.2018 Apoorva
@SuppressWarnings("unchecked")
public String getNetworkStatsBasedOnDeviceId(long Id)
{
	DBConnection dbc = null;
	Object[] obj=null ;
	XStream stream = new XStream();
	NetworkStats networkStats=null;
	try {
		String query = "";
		query += " select n.id,n.device,n.imvgId,n.rssi,n.retries,n.nc,n.rc,d.friendlyName,d.icon from networkStats as n left join device as d on n.device=d.id "
				+ " where n.device = "+Id +" order by id";
		
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		obj =(Object[]) q.uniqueResult();
		
		if (obj!=null)
		{
			networkStats=new NetworkStats();
			networkStats.setId(Long.parseLong(IMonitorUtil.convertToString(obj[0])));
			Device device=new Device();
			device.setId(Long.parseLong(IMonitorUtil.convertToString(obj[1])));
			device.setFriendlyName(IMonitorUtil.convertToString(obj[7]));
			Icon icon=new Icon();
			icon.setId(Long.parseLong(IMonitorUtil.convertToString(obj[8])));
			device.setIcon(icon);
			
			networkStats.setDevice(device);
			networkStats.setImvgId(IMonitorUtil.convertToString(obj[2]));
			networkStats.setRssi(IMonitorUtil.convertToString(obj[3]));
			networkStats.setRetries(Long.parseLong(IMonitorUtil.convertToString(obj[4])));
			networkStats.setNc(Long.parseLong(IMonitorUtil.convertToString(obj[5])));
			networkStats.setRc(Long.parseLong(IMonitorUtil.convertToString(obj[6])));
		}
		else
		{
			return stream.toXML(networkStats);
		}
		
	} catch (Exception ex) {
		LogUtil.info("Caught Exception  while getting NetworkStats obj : ", ex);
	} finally {
		dbc.closeConnection();
	}
	//LogUtil.info("List of network stats obj :"+stream.toXML(networkStats));

 return stream.toXML(networkStats);
}	
public BigInteger getDeviceBasedOnDeviceId(String deviceId,long gateWayid)
{
	
	DBConnection dbc = null;
	BigInteger id=null;

	try {
		String query = "select id from device where deviceId='"+deviceId+"' and gateWay="+gateWayid;
		
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		
		Query q = session.createSQLQuery(query);
		id =  (BigInteger) q.uniqueResult();
		
	} catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.info(" Error MEssage"+ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	
	return id;	
}

public String getFriendlyNameBasedOnDeviceId(String deviceId)
{
 DBConnection dbc = null;
 XStream stream=new XStream();
 String friendlyName="";
 try {
  String query = "select d.friendlyName from device as d where d.deviceId='"+deviceId+"'";
  
  dbc = new DBConnection();
  Session session = dbc.openConnection();
  
  Query q = session.createSQLQuery(query);
  friendlyName = (String) q.uniqueResult();
  
 } catch (Exception ex) {
  ex.printStackTrace();
 
 } finally {
  dbc.closeConnection();
 }
 return friendlyName;
}

public String getFriendlyNameBasedOnDeviceIdAndGateway(String deviceId,long gateWayid)
{
	DBConnection dbc = null;
	XStream stream=new XStream();
	String friendlyName="";
	try {
		String query = "select d.friendlyName from device as d where d.deviceId='"+deviceId+"' and d.gateWay="+gateWayid;
		
		dbc = new DBConnection();
		Session session = dbc.openConnection();
	
		Query q = session.createSQLQuery(query);
		friendlyName = (String) q.uniqueResult();
		
	} catch (Exception ex) {
		ex.printStackTrace();
		
	} finally {
		dbc.closeConnection();
	}

	return friendlyName;
}

public boolean checkAlertingDeviceOfGateway(Device device) {
	boolean result = false;
	DBConnection dbc = null;
	GateWay gateway = null;
	String query = "select g from GateWay as g where g.alertDevice="+device.getId()+"";
	try {
		dbc = new DBConnection();
		Session ses = dbc.openConnection();
		Query q = ses.createQuery(query);
		 gateway = (GateWay)q.uniqueResult();
		 
		 if(gateway != null) {
			 result = true;
		 }
	} catch (Exception e) {
		// TODO: handle exception
	}finally {
		dbc.closeConnection();
	}
	
	
	return result;
}

//Diaken VIA for Slave 
public Device getDeviceWithGateWayAndAgentByGeneratedDeviceIdForSlave(
		String generatedDeviceId) {
	ModbusSlave slave = null;
	Device device = new Device();
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Criteria criteria = session.createCriteria(ModbusSlave.class);
		Criterion criterion = Restrictions.eq("HAIF_Id",
				generatedDeviceId);
		criteria.add(criterion);
		slave = (ModbusSlave) criteria.uniqueResult();
		if (slave != null) {
			Hibernate.initialize(slave);
			//Hibernate.initialize(device.getGateWay());
			//if(device.getGateWay() != null)device.getGateWay().setAlertDevice(null);
			//Hibernate.initialize(device.getGateWay().getAgent());
			Hibernate.initialize(slave.getDeviceType());
			Hibernate.initialize(slave.getFriendlyName());
			Hibernate.initialize(slave.getConnected_Units());
		
			Hibernate.initialize(slave.getIcon());
			// Hibernate.initialize(device.getGateWay().getCustomer());
			Hibernate.initialize(slave.getLocation());				
			//Hibernate.initialize(device.getEnableList());			//sumit start: Enable/Disable Listing of device
			//Hibernate.initialize(device.getDeviceConfiguration());	//sumit start: Get PIRConfiguration if its a ZXT120
			//Hibernate.initialize(device.getModelNumber());
			//device.getGateWay().setDevices(null);
			//device.getGateWay().setCustomer(null);
			//device.setDeviceConfiguration(null);
			//device.getLocation().setCustomer(null);
			//device.setMake(null);
			//device.setLocationMap(null);
			slave.getDeviceType().setActionTypes(null);
			slave.getDeviceType().setAlertTypes(null);
			
			device.setDeviceType(slave.getDeviceType());
			device.setFriendlyName(slave.getFriendlyName());
			device.setLocation(slave.getLocation());
			device.setIcon(slave.getIcon());
			device.setId(slave.getId());
			device.setGeneratedDeviceId(slave.getHAIF_Id());
			device.setGateWay(slave.getGateWay());
			
		}
	} catch (Exception e) {
		LogUtil
				.error("2.Error when retreiving device by HAIF  Id : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}

//Diaken VIA for Modbus slave device
public ModbusSlave getModbusSlaveDevice(long id) {
	ModbusSlave device = (ModbusSlave) daoManager.get(id, ModbusSlave.class);
	return device;
}

//Diaken VIA for Modbus slave device
public boolean updateModbusSlaveDevice(ModbusSlave modbusSlave) {
	boolean result = false;
	try {
		daoManager.update(modbusSlave);
		result = true;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}








//Diaken VIA get Slave 
public ModbusSlave getVIASlavesBasedOnDeviceId(long deviceId) 
{
	DBConnection dbc = null;
	Object[] object = null;
	XStream stream=new XStream();
	ModbusSlave modbusSlave=new ModbusSlave();
	try{
		dbc = new DBConnection();
	String query="select m.id,m.deviceId,m.HAIF_Id,m.FriendlyName,m.Location,m.Connected_Units,l.name,l.iconFile,dt.details,dt.iconFile,m.SlaveId from Modbus_Slave as m " +
			"left join location as l on m.Location=l.id left join devicetype as dt on m.deviceType=dt.id where m.deviceId="+deviceId;
	//LogUtil.info("Query :-"+query1);
	Session session1 = dbc.openConnection();
	Query q1 = session1.createSQLQuery(query);
	object = (Object[]) q1.uniqueResult();
	
	
	BigInteger BigId= (BigInteger) object[0];
	long SlaveId=BigId.longValue();
	modbusSlave.setId(SlaveId);
	
	
	
	}
	 catch (Exception ex)
	 {
			ex.printStackTrace();
		} finally
		{
			dbc.closeConnection();
		}
	
	return modbusSlave;
}


//Diaken VIA get Slave 
public ModbusSlave getVIASlavesBasedOnHAIFId(String HAIF_ID) 
{
	DBConnection dbc = null;
	Object[] object = null;
	XStream stream=new XStream();
	ModbusSlave modbusSlave=new ModbusSlave();
	try{
		dbc = new DBConnection();
	String query="select m.id,m.deviceId,m.HAIF_Id,m.FriendlyName,m.Location,m.Connected_Units,l.name,l.iconFile,dt.details,dt.iconFile,m.SlaveId from Modbus_Slave as m " +
			"left join location as l on m.Location=l.id left join devicetype as dt on m.deviceType=dt.id where m.HAIF_Id='"+HAIF_ID+"'";
	
	Session session1 = dbc.openConnection();
	Query q1 = session1.createSQLQuery(query);
	object = (Object[]) q1.uniqueResult();
	
	
	BigInteger BigId= (BigInteger) object[0];
	long SlaveId=BigId.longValue();
	modbusSlave.setId(SlaveId);
	
	
	
	}
	 catch (Exception ex)
	 {
			ex.printStackTrace();
		} finally
		{
			dbc.closeConnection();
		}
	
	return modbusSlave;
}



/*public IndoorUnit getIndoorUnitBasedOnUnitId(String UnitId) 
{
	DBConnection dbc = null;
	Object[] object = null;
	XStream stream=new XStream();
	IndoorUnit indoorUnit=new IndoorUnit();
	try{
		dbc = new DBConnection();
	String query="select m.id,m.SlaveId,m.Unit_Id,m.FriendlyName,m.Location,m.DeviceConfiguration,m.deviceType,m.gateway,m.enableList,m.locationMap," +
			"m.posIdx,m.icon,m.enableStatus,m.ConnectStatus,m.CommStatus,l.name,l.iconFile,dt.details,dt.iconFile from Indoor_Unit_Devices as m " +
			"left join location as l on m.Location=l.id left join devicetype as dt on m.deviceType=dt.id where m.Unit_Id='"+UnitId+"'";
	LogUtil.info("Get Indoor Unit query :-"+query);
	Session session1 = dbc.openConnection();
	Query q1 = session1.createSQLQuery(query);
	object = (Object[]) q1.uniqueResult();
	LogUtil.info("Indoor Unit from Db "+stream.toXML(object));

	BigInteger BigId= (BigInteger) object[0];
	long indoorId=BigId.longValue();
	indoorUnit.setId(indoorId);
	BigInteger bSlaveId= (BigInteger) object[1];
	long slaveId=bSlaveId.longValue();
	ModbusSlave modbusSlave=new ModbusSlave();
	modbusSlave.setId(slaveId);
	indoorUnit.setSlaveId(modbusSlave);
	String unitId=(String) object[2];
	indoorUnit.setUnit_Id(unitId);
	String friendlyName=(String) object[3];
	indoorUnit.setFriendlyName(friendlyName);
	
	BigInteger blocation= (BigInteger) object[4];
	long locationId=blocation.longValue();
	Location location=new Location();
	location.setId(locationId);
	indoorUnit.setLocation(location);
	
	BigInteger bdevConfig= (BigInteger) object[5];
	long devConfigID=bdevConfig.longValue();
	IndoorUnitConfiguration configuration=new IndoorUnitConfiguration();
	configuration.setId(devConfigID);
	indoorUnit.setDeviceConfiguration(configuration);
	
	BigInteger bdevType= (BigInteger) object[6];
	long devTypeId=bdevType.longValue();
	DeviceType deviceType=new DeviceType();
	deviceType.setId(devTypeId);
	indoorUnit.setDeviceType(deviceType);
	
	BigInteger bgateway= (BigInteger) object[7];
	long gatewayId=bgateway.longValue();
	GateWay gateWay=new GateWay();
	gateWay.setId(gatewayId);
	indoorUnit.setGateWay(gateWay);
	
	String enableList= (String) object[8];
	indoorUnit.setEnableList(enableList);
	
	BigInteger blocMap= (BigInteger) object[9];
	long locMapId=blocMap.longValue();
	LocationMap locationMap=new LocationMap();
	locationMap.setId(locMapId);
	indoorUnit.setLocationMap(locationMap);
	
	BigInteger bposIdx= (BigInteger) object[10];
	long posIDXId=bposIdx.longValue();
	indoorUnit.setPosIdx(posIDXId);
	
	BigInteger bicon= (BigInteger) object[11];
	long iconId=bicon.longValue();
	Icon icon=new Icon();
	icon.setId(iconId);
	indoorUnit.setIcon(icon);
	
	BigInteger benableStat= (BigInteger) object[12];
	long enableStatus=benableStat.longValue();
	indoorUnit.setEnableStatus(enableStatus);
	
	BigInteger bconnect= (BigInteger) object[13];
	long connect=bconnect.longValue();
	indoorUnit.setConnectStatus(connect);
	
	BigInteger bcomm= (BigInteger) object[14];
	long comm=bcomm.longValue();
	indoorUnit.setCommStatus(comm);
	
	}
	 catch (Exception ex)
	 {
			ex.printStackTrace();
			LogUtil.info("Exception :"+ex.getMessage());
		} finally
		{
			dbc.closeConnection();
		}
	
	return indoorUnit;
}*/




//IndoorUnit Configuration From DB
public IndoorUnitConfiguration getIndoorUnitConfigurationById(long id) {
	IndoorUnitConfiguration device = (IndoorUnitConfiguration) daoManager.get(id, IndoorUnitConfiguration.class);
	return device;
}

/*public List<ModbusSlave> listOfSlavesFromDb() {
	List<ModbusSlave> devices = new ArrayList<ModbusSlave>();
	try {
		devices = (List<ModbusSlave>) daoManager.list(ModbusSlave.class);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return devices;

}*/

@SuppressWarnings("unchecked")
public List<ModbusSlave> listOfSlavesFromDb(Device device) {
	String query = "";
	XStream stream=new XStream();
    query += "select m.id,m.SlaveId,m.HAIF_Id,m.deviceId from Modbus_Slave as m where deviceId="+device.getId();
            DBConnection dbc = null;
            List<Object[]> objects =null;
            List<ModbusSlave> modbusSlaves =new ArrayList<ModbusSlave>();
	try
	{
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createSQLQuery(query);
		objects= q.list();
		for (Object[] objFromDb : objects)
		{
			
			BigInteger bigInteger=(BigInteger) objFromDb[1];
			long Slaveid=bigInteger.longValue();
			ModbusSlave modbusSlave=new ModbusSlave();
			modbusSlave.setSlaveId(Slaveid);
			modbusSlaves.add(modbusSlave);
		}
		
	} 
	catch (Exception ex)
	{
		LogUtil.error(ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	return modbusSlaves;
}


public List<Device> getIndoorUnitsFromDb (long gatewayId) {
	DBConnection dbc = null;
	/*String query = "select count(i) from IndoorUnit as i join i.SlaveId as m where m.id="+device.getId();*/
	String query="select d.id,d.deviceId,d.generatedDeviceId,d.friendlyName from device as d left join devicetype on d.deviceType=devicetype.id where gateway="+gatewayId+
			" and devicetype.name='VIA_UNIT'";
	//LogUtil.info("IDU list query: " + query);
	
	List<Object[]> objects =null;
    List<Device> devices =new ArrayList<Device>();
	XStream stream=new XStream();
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		objects=  q.list();
		//LogUtil.info("Objects from Db : "+stream.toXML(objects));
		for (Object[] deviceFromDb : objects)
		{
			Device device=new Device();
			BigInteger bigInteger=(BigInteger) deviceFromDb[0];
			//LogUtil.info("bid"+stream.toXML(bigInteger));
			long did=bigInteger.longValue();
			//LogUtil.info("lid"+did);
			String deviceid=(IMonitorUtil.convertToString(deviceFromDb[1]));
			device.setId(did);
			device.setDeviceId(deviceid);
			device.setFriendlyName(IMonitorUtil.convertToString(deviceFromDb[3]));
			device.setGeneratedDeviceId(IMonitorUtil.convertToString(deviceFromDb[2]));
			devices.add(device);
		}
		//LogUtil.info("Devices set : "+stream.toXML(devices));
		
	} catch (Exception e)
	{
		LogUtil.info("Exception IDU"+e.getMessage());
		e.printStackTrace();
	}		
	return devices;
}

//Update Command param for IDU
public Device updateCommadParamOnlyForIDUOffandMode(
		String generatedDeviceId, String gateWayId, String commandParam,String setpointvalue) {
	
	
	DBConnection dbc = null;
	//String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r) from "
	String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r), i.fileName, ast.id, ast.name,cf from "
			+ " Device as d left join d.deviceType as dt"
			+ " left join d.deviceConfiguration as cf"
			+ " left join d.location as l"
			+ " left join d.lastAlert as la"
			+ " left join d.rules as r"
			+ " left join d.icon as i"
			+ " left join d.armStatus as ast" 
			+ " where d.generatedDeviceId= '"
			+ generatedDeviceId
			+ "' and d.gateWay.macId ='" + gateWayId + "'";
	Device inDevice = null;
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		inDevice = (Device) object[0];
		
	//	LogUtil.info("HELLI COMMAND "+commandParam);
		/*if(commandParam.equals("0"))
		inDevice.setCommandParam(commandParam);*/
		XStream stream=new XStream();
		//LogUtil.info("In device"+stream.toXML(inDevice));
		IndoorUnitConfiguration configuration = (IndoorUnitConfiguration) object[9];
		//LogUtil.info("2--->Config object"+stream.toXML(configuration));
		if(!(commandParam.equals("5")))
		{
			
			if (commandParam.equals("0"))
			{
				
				inDevice.setCommandParam("0");
			}
			else
			{
				
				//inDevice.setCommandParam(commandParam);
				configuration.setFanModeValue(setpointvalue);
				
			}
		}
		else
		{
			
			if ((commandParam.equals("5")))
			{
				String value=Integer.toString(configuration.getTemperatureValue());
				inDevice.setCommandParam(value);
			}
			
			
			//configuration.setFanModeValue(configuration.getFanModeValue());
		}
		
		Transaction tx = session.beginTransaction();
		session.update(inDevice);
		tx.commit();
		// We are creating new device to avoid the hibernate proxy mapping
		// problem.
		device = new Device();
		device.setId(inDevice.getId());
		device.setDeviceId(inDevice.getDeviceId());
		device.setFriendlyName(inDevice.getFriendlyName());
		device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
		device.setBatteryStatus(inDevice.getBatteryStatus());
		device.setCommandParam(inDevice.getCommandParam());
		device.setModelNumber(inDevice.getModelNumber());
		device.setEnableStatus(inDevice.getEnableStatus());
		device.setDeviceConfiguration(inDevice.getDeviceConfiguration());
		DeviceType deviceType = new DeviceType();
		deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
		deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
		device.setDeviceType(deviceType);
		Location location = new Location();
		location.setName(IMonitorUtil.convertToString(object[3]));
		device.setLocation(location);
		AlertType alertType = new AlertType();
		alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
		device.setLastAlert(alertType);
		Status armStatus = null;
		Long armStatusId = (Long) object[7];
		if(armStatusId != null){
			armStatus = new Status();
			String armStatusName = (String) object[8];
			armStatus.setId(armStatusId);
			armStatus.setName(armStatusName);
		}
		device.setArmStatus(armStatus);
		
		int count = Integer.parseInt(IMonitorUtil
				.convertToString(object[5]));
		if (count > 0) {
			device.setRules(new ArrayList<Rule>());
		}
		
		//Icon Bug
		//device.getIcon().setFileName(inDevice.getIcon().getFileName());
		device.setIcon(inDevice.getIcon());
		if(device.getIcon() != null) device.getIcon().setFileName(IMonitorUtil.convertToString(object[6]));
		//LogUtil.info("Device to UI "+stream.toXML(device));
	} catch (Exception e) {
		LogUtil.error("19.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}

//Update IDU Fan Volume value
public Device updateFanModesOfIDUByGeneratedId(
		String generatedDeviceId, String gateWayId, String FanVolumeValue) {
	
	DBConnection dbc = null;
	//String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r) from "
	String query = "select d, dt.details,dt.iconFile,l.name,cf,i.fileName from "
			+ " Device as d left join d.deviceType as dt"
			+ " left join d.deviceConfiguration as cf"
			+ " left join d.icon as i"
			+ " left join d.location as l"
			+ " where d.generatedDeviceId= '"
			+ generatedDeviceId
			+ "' and d.gateWay.macId ='" + gateWayId + "'";
	Device inDevice = null;
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		inDevice = (Device) object[0];
		
		XStream stream=new XStream();
		IndoorUnitConfiguration configuration = (IndoorUnitConfiguration) object[4];
		//LogUtil.info("2--->Device"+stream.toXML(inDevice));
		configuration.setFanVolumeValue(FanVolumeValue);
		DeviceConfigurationManager configurationManager=new DeviceConfigurationManager();
		boolean resultAfteIDUupdate=configurationManager.updateIduConfiguration(configuration);
		
		
		// We are creating new device to avoid the hibernate proxy mapping
		// problem.
		device = new Device();
		device.setId(inDevice.getId());
		device.setDeviceId(inDevice.getDeviceId());
		device.setFriendlyName(inDevice.getFriendlyName());
		device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
		device.setBatteryStatus(inDevice.getBatteryStatus());
		device.setCommandParam(inDevice.getCommandParam());
		device.setModelNumber(inDevice.getModelNumber());
		device.setEnableStatus(inDevice.getEnableStatus());
		device.setDeviceConfiguration(inDevice.getDeviceConfiguration());
		DeviceType deviceType = new DeviceType();
		deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
		deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
		device.setDeviceType(deviceType);
		Location location = new Location();
		location.setName(IMonitorUtil.convertToString(object[3]));
		device.setLocation(location);
		/*AlertType alertType = new AlertType();
		alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
		device.setLastAlert(alertType);*/
		/*Status armStatus = null;
		Long armStatusId = (Long) object[7];
		if(armStatusId != null){
			armStatus = new Status();
			String armStatusName = (String) object[8];
			armStatus.setId(armStatusId);
			armStatus.setName(armStatusName);
		}
		device.setArmStatus(armStatus);
		
		int count = Integer.parseInt(IMonitorUtil
				.convertToString(object[5]));
		if (count > 0) {
			device.setRules(new ArrayList<Rule>());
		}
		*/
		//Icon Bug
		//device.getIcon().setFileName(inDevice.getIcon().getFileName());
		//device.setIcon(inDevice.getIcon());
		Icon icon=new Icon();
		device.setIcon(icon);
		//LogUtil.info("Device to UI "+stream.toXML(device));
		if(device.getIcon() != null) device.getIcon().setFileName(IMonitorUtil.convertToString(object[5]));
	} catch (Exception e) {
		LogUtil.error("19.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}




//Update IDU Fan Direction value

public Device updateFanDirectionOfIDUByGeneratedId(
		String generatedDeviceId, String gateWayId, String FanDirectionValue) {
	
	DBConnection dbc = null;
	//String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r) from "
	String query = "select d, dt.details,dt.iconFile,l.name,cf,i.fileName from "
			+ " Device as d left join d.deviceType as dt"
			+ " left join d.deviceConfiguration as cf"
			+ " left join d.icon as i"
			+ " left join d.location as l"
			+ " where d.generatedDeviceId= '"
			+ generatedDeviceId
			+ "' and d.gateWay.macId ='" + gateWayId + "'";
	Device inDevice = null;
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		inDevice = (Device) object[0];
		
		XStream stream=new XStream();
		IndoorUnitConfiguration configuration = (IndoorUnitConfiguration) object[4];
		//LogUtil.info("2--->Device"+stream.toXML(inDevice));
		configuration.setFanDirectionValue(FanDirectionValue);
		DeviceConfigurationManager configurationManager=new DeviceConfigurationManager();
		boolean resultAfteIDUupdate=configurationManager.updateIduConfiguration(configuration);
		
		
		// We are creating new device to avoid the hibernate proxy mapping
		// problem.
		device = new Device();
		device.setId(inDevice.getId());
		device.setDeviceId(inDevice.getDeviceId());
		device.setFriendlyName(inDevice.getFriendlyName());
		device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
		device.setBatteryStatus(inDevice.getBatteryStatus());
		device.setCommandParam(inDevice.getCommandParam());
		device.setModelNumber(inDevice.getModelNumber());
		device.setEnableStatus(inDevice.getEnableStatus());
		device.setDeviceConfiguration(inDevice.getDeviceConfiguration());
		DeviceType deviceType = new DeviceType();
		deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
		deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
		device.setDeviceType(deviceType);
		Location location = new Location();
		location.setName(IMonitorUtil.convertToString(object[3]));
		device.setLocation(location);
		/*AlertType alertType = new AlertType();
		alertType.setAlertCommand(IMonitorUtil.convertToString(object[4]));
		device.setLastAlert(alertType);*/
		/*Status armStatus = null;
		Long armStatusId = (Long) object[7];
		if(armStatusId != null){
			armStatus = new Status();
			String armStatusName = (String) object[8];
			armStatus.setId(armStatusId);
			armStatus.setName(armStatusName);
		}
		device.setArmStatus(armStatus);
		
		int count = Integer.parseInt(IMonitorUtil
				.convertToString(object[5]));
		if (count > 0) {
			device.setRules(new ArrayList<Rule>());
		}
		*/
		//Icon Bug
		//device.getIcon().setFileName(inDevice.getIcon().getFileName());
		//device.setIcon(inDevice.getIcon());
		Icon icon=new Icon();
		device.setIcon(icon);
		//LogUtil.info("Device to UI "+stream.toXML(device));
		if(device.getIcon() != null) device.getIcon().setFileName(IMonitorUtil.convertToString(object[5]));
		
	} catch (Exception e) {
		LogUtil.error("19.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}


//Added by apoorva for updating IDU Temperature

public Device updateCommadParamForIDU(
		String generatedDeviceId, String gateWayId, String commandParam) {
	
	DBConnection dbc = null;
	//String query = "select d, dt.details,dt.iconFile,l.name,la.alertCommand, count(r) from "
	String query = "select d, dt.details,dt.iconFile,l.name,cf,i.fileName from "
			+ " Device as d left join d.deviceType as dt"
			+ " left join d.deviceConfiguration as cf"
			+ " left join d.icon as i"
			+ " left join d.location as l"
			+ " where d.generatedDeviceId= '"
			+ generatedDeviceId
			+ "' and d.gateWay.macId ='" + gateWayId + "'";
	Device inDevice = null;
	Device device = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createQuery(query);
		Object[] object = (Object[]) q.uniqueResult();
		inDevice = (Device) object[0];
		
		XStream stream=new XStream();
		IndoorUnitConfiguration configuration = (IndoorUnitConfiguration) object[4];
		
		inDevice.setCommandParam(commandParam);
		int comm=Integer.parseInt(commandParam);
		configuration.setTemperatureValue(comm);
		//DeviceConfigurationManager configurationManager=new DeviceConfigurationManager();
		//boolean res=configurationManager.updateIduConfiguration(configuration);
		//LogUtil.info("Slider temp update result "+res);
		device = new Device();
		device.setId(inDevice.getId());
		device.setDeviceId(inDevice.getDeviceId());
		device.setFriendlyName(inDevice.getFriendlyName());
		device.setGeneratedDeviceId(inDevice.getGeneratedDeviceId());
		device.setBatteryStatus(inDevice.getBatteryStatus());
		device.setCommandParam(inDevice.getCommandParam());
		device.setModelNumber(inDevice.getModelNumber());
		device.setEnableStatus(inDevice.getEnableStatus());
		device.setDeviceConfiguration(inDevice.getDeviceConfiguration());
		DeviceType deviceType = new DeviceType();
		deviceType.setDetails(IMonitorUtil.convertToString(object[1]));
		deviceType.setIconFile(IMonitorUtil.convertToString(object[2]));
		device.setDeviceType(deviceType);
		Location location = new Location();
		location.setName(IMonitorUtil.convertToString(object[3]));
		device.setLocation(location);
		device.setDeviceConfiguration(configuration);
		Transaction tx = session.beginTransaction();
		session.update(inDevice);
		tx.commit();
		Icon icon=new Icon();
		device.setIcon(icon);
		if(device.getIcon() != null) device.getIcon().setFileName(IMonitorUtil.convertToString(object[5]));
		
	} catch (Exception e) {
		LogUtil.error("19.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}


@SuppressWarnings("unchecked")
public void updateSlaveAndIduLastAlert(AlertType alertType, Device device) {
	DBConnection dbc = null;

	String query = "select d from Device as d"
					+ " where d.generatedDeviceId like '%" + device.getGeneratedDeviceId()+ "%'" + "";
	
	
	
    List<Device> devices =new ArrayList<Device>();
	XStream stream=new XStream();
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		devices=  (List<Device>) q.list();
		for (Device dev : devices)
		{
			dev.setLastAlert(alertType);
			daoManager.update(dev);

		}
		//LogUtil.info("Devices set : "+stream.toXML(devices));
	} catch (Exception e)
	{
		LogUtil.info("Exception IDU"+e.getMessage());
		e.printStackTrace();
	}		
}

public Device getDeviceByGeneratedDeviceIdForAlexa(String generatedDeviceId) {
	XStream stream=new XStream();
	Device device = null;
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Criteria criteria = session.createCriteria(Device.class);
		Criterion criterion = Restrictions.eq("generatedDeviceId",generatedDeviceId);
		criteria.add(criterion);
		device = (Device) criteria.uniqueResult();
		
		Hibernate.initialize(device);
		Hibernate.initialize(device.getDeviceConfiguration());
		Hibernate.initialize(device.getDeviceType());
		//Hibernate.initialize(device.getDeviceType().getActionTypes());
		//Hibernate.initialize(device.getDeviceType().getAlertTypes());
		
	} catch (Exception e) {
		LogUtil
				.error("3.Error when retreiving device by generatedDeviceId : "+generatedDeviceId+""
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return device;
}

public boolean saveWallmoteConfiguration(WallmoteConfiguration configuration) {
	boolean result=false;
	try{
		result=daoManager.save(configuration);
	}catch(Exception e){
		e.printStackTrace();
	}
return result;
}

//Wallmote configuration
@SuppressWarnings("unchecked")
public List<Object[]> getWallmoteConfigbyGeneratedId(Device device)
{
	
	String query="";
	query +="select w.id,w.device,w.keyName,w.action,w.keyCode,w.posindex " +
			" from wallmoteconfiguration as w left join device as d on w.device=d.id left join action as a on w.action=a.id " +
			" where d.id='"+device.getId()+"'" +
					" order by w.posindex"; // Naveen added k.id to fix key ordering issue.
	
	 DBConnection dbc = null;
     List<Object[]> keyConfigs =null;
	
     try {
 		dbc = new DBConnection();
 		dbc.openConnection();
 		Query q = dbc.getSession().createSQLQuery(query);
 		keyConfigs= q.list();
 	} catch (Exception ex) {
 		LogUtil.error(ex.getMessage());
 	} finally {
 		dbc.closeConnection();
 	}
 	
	return keyConfigs;
}

public boolean deleteWallmoteConfiguration(Device device) {
	
	DBConnection dbc = null;
	String query = "";
    boolean result = false;
	
	query += "delete from wallmoteconfiguration where device='"+device.getId()+"'";
	
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createSQLQuery(query);
		Transaction tx = dbc.getSession().beginTransaction();
		q.executeUpdate();
		tx.commit();
		result = true;
			
	} catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.error(ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	
	return result;
}
public List<Object[]> listAllActionsByCustomerIdForWallmote(GateWay gateway)
{

	String query = "";
    query += "select a.id,a.actionName,a.parameter " 
			+ " from Action as a" 
    		+ " left join a.device as d" 
			+ " left join d.gateWay as g" 
    		+ " left join a.functions as f"
            + " where g.id="+gateway.getId()
            + " and d.gateWay=g.id";
            DBConnection dbc = null;
            List<Object[]> actions =null;
            LogUtil.info("query : " + query);
            
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		actions= q.list();
	} catch (Exception ex) {
		LogUtil.error(ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	
	return actions;
}


public PushNotification getPushNotificationObject(long customerId) {
	DBConnection dbc = null;
	PushNotification pushNotification = null;
	try {
		String query = "";
		query += "select p from " + " PushNotification as p"
				+ " left join p.customer as c"
				+ " where p.customer='"
				+ customerId + "'";

		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		pushNotification = (PushNotification) q.uniqueResult();
	} catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.info("Error msg : "+ ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	return pushNotification;
}

public List<Device> getDeviceListOfCustomer(Customer customer)

{
	LogUtil.info("getDevicesListOfCustomer customer"+customer);
	List<Device> devices = null;
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Criteria criteria = session.createCriteria(Device.class);
		Criterion criterion = Restrictions.eq("gateWay.customer", customer);
		criteria.add(criterion);
		List<Device> gateWays = criteria.list();

		DBConnection dbConnection = new DBConnection();
		Session sess = dbConnection.openConnection();
		Criteria crt = sess.createCriteria(Device.class);
		Criterion crtn = Restrictions.in("gateWay", gateWays);
		crt.add(crtn);
		devices = crt.list();

		for (Device device : devices) {
			Hibernate.initialize(device);
			Hibernate.initialize(device.getDeviceType());
			Hibernate.initialize(device.getDeviceType().getActionTypes());
			Hibernate.initialize(device.getDeviceType().getAlertTypes());
			device.setLocation(null);
			device.setGateWay(null);
			device.setDeviceConfiguration(null);
		}
	} catch (Exception e) {
		LogUtil
				.error("1.Error when retreiving device by generatedDeviceId : "
						+ e.getMessage());
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	XStream stream=new XStream();
	LogUtil.info("getDevicesListOfCustomer devices:"+devices);
	LogUtil.info("getDevicesListOfCustomer devices stream:"+stream.toXML(devices));
	return devices;
	

}




public List<PushNotification> getDevicesListOfCustomer(long customerId)
{
	
	String query = "";
	 List<Object[]> Objectslist =null;
	 List<PushNotification> devicesList = new ArrayList<PushNotification>();
    query += "select p.id,p.deviceToken,p.deviceType " 
			+ " from PushNotification as p" 
    		//+ " left join p.customer as c" 
            + " where p.customer="+customerId ;
    //LogUtil.info("Query : "+query);
            DBConnection dbc = null;    
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		Objectslist= q.list();
		
		for (Object[] object : Objectslist) 
		{
			PushNotification notification=new PushNotification();
			notification.setId(((Long)object[0]).longValue());
			//Customer customer=new Customer();
			//customer.setId(((Long)object[1]).longValue());
			notification.setCustomer(null);
			notification.setDeviceToken(IMonitorUtil.convertToString(object[1]));
			notification.setDeviceType(IMonitorUtil.convertToString(object[2]));
			devicesList.add(notification);
		}
	
	
	}
catch (Exception ex) {
		LogUtil.error(ex.getMessage());
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	
	return devicesList;
}

public boolean verifyWhetherDevicePresentInDb(long customerId,String ImeiNumber)
{
	XStream stream = new XStream();
	String query = "";
	 List<Object[]> Objectslist =null;
	 boolean result = false;
    query += "select p.id,p.ImeiNumber,p.deviceType,p.appName" 
			+ " from PushNotification as p" 
            + " where p.customer="+customerId ;
    LogUtil.info("Query : "+query);
            DBConnection dbc = null;    
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		Objectslist= q.list();
		for (Object[] object : Objectslist) 
		{
			String imeiNumberFromDb = IMonitorUtil.convertToString(object[1]);
			if (imeiNumberFromDb.equals(ImeiNumber)) 
			{
				result = true;
				return result;
			}
		}

	}
catch (Exception ex) {
		LogUtil.error(ex.getMessage());
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	
	return result;
}

public PushNotification getPushNotificationObjectByImei(String imei) {
	DBConnection dbc = null;
	PushNotification pushNotification = null;
	try {
		String query = "";
		query += "select p from " + " PushNotification as p"
				+ " left join p.customer as c"
				+ " where p.ImeiNumber='"
				+ imei + "'";

		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		pushNotification = (PushNotification) q.uniqueResult();
	} catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.info("Error msg : "+ ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	return pushNotification;
}

public PushNotification getPushNotificationObjectByCustomer(long custId) {
	DBConnection dbc = null;
	PushNotification pushNotification = null;
	try {
		String query = "";
		query += "select p from " + " PushNotification as p"
				+ " left join p.customer as c"
				+ " where p.customer="+ custId ;

		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		pushNotification = (PushNotification) q.uniqueResult();
	} catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.info("Error msg : "+ ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	return pushNotification;
}

public boolean verifyWhetherDevicePresentInDbForIOS(long customerId,String deviceToken)
{
	XStream stream = new XStream();
	String query = "";
	 List<Object[]> Objectslist =null;
	 boolean result = false;
    query += "select p.id,p.deviceToken,p.deviceType " 
			+ " from PushNotification as p" 
            + " where p.customer="+customerId ;
    //LogUtil.info("Query : "+query);
            DBConnection dbc = null;    
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		Objectslist= q.list();
		for (Object[] object : Objectslist) 
		{
			String deviceTokenFromDb = IMonitorUtil.convertToString(object[1]);
			if (deviceTokenFromDb.equals(deviceToken)) 
			{
				result = true;
				return result;
			}
		}

	}
catch (Exception ex) {
		LogUtil.error(ex.getMessage());
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	
	return result;
}


public PushNotification getPushNotificationObjectByCustomerAndDeviceType(long custId,String deviceType) {
	DBConnection dbc = null;
	PushNotification pushNotification = null;
	try {
		String query = "";
		query += "select p from " + " PushNotification as p"
				+ " left join p.customer as c"
				+ " where p.customer="+ custId +" and p.deviceType='"+deviceType+"'" ;

		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		pushNotification = (PushNotification) q.uniqueResult();
	} catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.info("Error msg : "+ ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	return pushNotification;
}


//Touch aPanel 

@SuppressWarnings("unchecked")
public List<Object[]> listDevicesByGatewayId(long gatewayId)
{
	String query = "select d.id,d.deviceId,d.gateWay,d.generatedDeviceId,d.friendlyName,d.batteryStatus,d.modelNumber,d.deviceType," +
			"d.commandParam,d.lastAlert, d.armStatus,d.make,d.location,d.deviceConfiguration,d.enableStatus,d.icon,d.mode," +
			"d.enableList,d.posIdx,d.locationMap,d.switchType,d.devicetimeout,d.pulseTimeOut,dt.name " +
			"from device as d join devicetype as dt on dt.id=d.deviceType where d.gateWay="+gatewayId+" group by d.deviceId";
query += "";
        DBConnection dbc = null;
        List<Object[]> devices =null; 
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createSQLQuery(query);
		devices= q.list();
	} catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.error(ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	
	return devices;
	
}



//IOS

//discover device for alexa subuser restriction

@SuppressWarnings("unchecked")
public List<Object[]> getdevicedetailsforalexaforsubuser(String customerId,User user){
	//LogUtil.info("Alexa sub user details");
	
	XStream stream = new XStream();
	DBConnection dbc = null;
	String query="select d.deviceId,d.gateWay,d.friendlyName,dd.name,d.switchType,d.id from device as d left join gateway as g on d.gateWay=g.id left join customer as c on g.customer=c.id left join devicetype as dd on d.deviceType=dd.id where c.customerId='"+customerId+"'"+" and dd.name in ('Z_WAVE_SWITCH','DEV_ZWAVE_RGB','Z_WAVE_DIMMER','Z_WAVE_AC_EXTENDER','VIA_UNIT','Z_WAVE_MULTI_SENSOR','Z_WAVE_DOOR_SENSOR','Z_WAVE_AV_BLASTER')";
	
	LogUtil.info("Alexa sub user query=="+query);
	
	List<Object[]> objects=null;
	List<Object[]> deviceList=new ArrayList<Object[]>();
	
	UserManager userManager=new UserManager();
	List<subUserDeviceAccess> accessibleDeviceList = userManager.listDevicesForSubuser(user.getId());
	
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();	
		Query q = session.createSQLQuery(query);
		objects=q.list();
		
		
		for (Object[] details : objects) 
		{
			String deviceTypeName = IMonitorUtil.convertToString(details[3]);
			BigInteger deviceId = (BigInteger) details[5];
			if (deviceId == null) {
				continue;
			}
		
			
			if(deviceTypeName.equals(Constants.VIA_UNIT))
			{
				IndoorUnitConfiguration indoorUnitConfiguration = null;
				String query1 = "select d.deviceConfiguration from Device as d where d.id="+deviceId; 
				Query q1 = session.createQuery(query1);
				indoorUnitConfiguration = (IndoorUnitConfiguration) q1.uniqueResult();
				if(indoorUnitConfiguration.getConnectStatus() == 0)
				{
					continue;
				}
			}
			
			for(subUserDeviceAccess su : accessibleDeviceList){
				long did = su.getDevice().getId();
				
				if(did == deviceId.longValue()){
				
					deviceList.add(details);
				}
				
				
			}
			
		}
		
		} catch (Exception ex) 
	{
		ex.printStackTrace();
		LogUtil.info("Error ::: "+ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	LogUtil.info("device log check"+stream.toXML(deviceList));
	return deviceList;
}


@SuppressWarnings("unchecked")
public List<Object[]> getDeviceDetailsForAlexaVideoForSubuser(String customerId,User user){
	//LogUtil.info("Alexa sub user details");
	
	XStream stream = new XStream();
	DBConnection dbc = null;
	String query="select d.deviceId,d.gateWay,d.friendlyName,dd.name,d.switchType,d.id,d.modelNumber from device as d left join gateway as g on d.gateWay=g.id left join customer as c on g.customer=c.id left join devicetype as dd on d.deviceType=dd.id where c.customerId='"+customerId+"'"+" and dd.name ='Z_WAVE_AV_BLASTER'";
	
	LogUtil.info("Alexa sub user query=="+query);
	
	List<Object[]> objects=null;
	List<Object[]> deviceList=new ArrayList<Object[]>();
	
	UserManager userManager=new UserManager();
	List<subUserDeviceAccess> accessibleDeviceList = userManager.listDevicesForSubuser(user.getId());
	
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();	
		Query q = session.createSQLQuery(query);
		objects=q.list();
		
		
		for (Object[] details : objects) 
		{
			String deviceTypeName = IMonitorUtil.convertToString(details[3]);
			BigInteger deviceId = (BigInteger) details[5];
			if (deviceId == null) {
				continue;
			}
		
			
			if(deviceTypeName.equals(Constants.VIA_UNIT))
			{
				IndoorUnitConfiguration indoorUnitConfiguration = null;
				String query1 = "select d.deviceConfiguration from Device as d where d.id="+deviceId; 
				Query q1 = session.createQuery(query1);
				indoorUnitConfiguration = (IndoorUnitConfiguration) q1.uniqueResult();
				if(indoorUnitConfiguration.getConnectStatus() == 0)
				{
					continue;
				}
			}
			
			for(subUserDeviceAccess su : accessibleDeviceList){
				long did = su.getDevice().getId();
				
				if(did == deviceId.longValue()){
				
					deviceList.add(details);
				}
				
				
			}
			
		}
		
		} catch (Exception ex) 
	{
		ex.printStackTrace();
		LogUtil.info("Error ::: "+ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	LogUtil.info("device log check for sub user"+stream.toXML(deviceList));
	return deviceList;
}

	public String getIdOfDevice(String deviceid){
	DBConnection dbc = null;
	String result = null;
	try {
		String query = "select id" +
				" from Device as d" +
				" where d.generatedDeviceId='" + deviceid + "'";
		
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
	

public boolean checkAlexaUserByCustomerId(Customer customer) {
		LogUtil.info("checkAlexaUserByCustomerId customer::"+customer);
		boolean result = false;
		String query = "";
		query += "select a  from Alexa as a" +
				" left join a.customer as c" +
				" where c.customerId='" + customer.getCustomerId() + "'";
		DBConnection dbc = null;
		Object objects =null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			objects= q.uniqueResult();
			
			if(objects != null) {
				result = true;
			}
			
		} catch (Exception ex) {
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		
		LogUtil.info("checkAlexaUserByCustomerId result::"+result);
	return result;
}
public long getFunctionIdBasedOnName(String name){

	long functionId = 0;
	DBConnection dbc = null;
	
	//String query = "select f.functionId from functions as f where f.name='"+name+"' and f.category='"+category+"'";
	//Naveen commented to fix bug. Actualy to save function table id
	
	String query = "select f.functionId from functions as f where f.name='"+name+"'";
	
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		BigInteger lCount  = (BigInteger) q.uniqueResult();
		functionId = lCount.longValue();
		
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		dbc.closeConnection();
	}
	
	
	return functionId;
}


}


	

