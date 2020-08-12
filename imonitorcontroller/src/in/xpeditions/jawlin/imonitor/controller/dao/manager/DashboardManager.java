/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CostDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.H264CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LocationMap;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Powerinformation;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.TarrifConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.powerinfo;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.targetcost;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.dao.util.dashboardDBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

import com.mchange.v2.log.LogUtils;
import com.thoughtworks.xstream.XStream;

public class DashboardManager {
	DaoManager daoManager = new DaoManager();
	
	
	
	
	public List<DeviceDetails> getdashboarddetailsOfCustomer(Customer customer) {

		dashboardDBConnection dashboard = null;
		XStream xStream=new XStream();
		List<DeviceDetails> deviceDetails = new ArrayList<DeviceDetails>();
		String query = "select "
				+ " dd.id,dd.did,dd.deviceName,dd.deviceIcon,dd.gateway,dd.locationName,dd.enableList,dd.deviceType,dd.alertParam,dd.customer,dd.locationId "
				+ " from devicedetails as dd"
				+ " where dd.customer='"
				+ customer.getId() + "'";
				
		
		List<Object[]> objects = null;
		@SuppressWarnings("unused")
		List<Object[]> targetcost = null;
		 //LogUtil.info("query----"+query);
		try {
			dashboard = new dashboardDBConnection();
			Session session = dashboard.openConnection();
			Query q = session.createSQLQuery(query);
			objects = q.list();
			dashboard.closeConnection();
			for (Object[] strings : objects)
			{
			
				
			DeviceDetails devicedetails=new DeviceDetails();
			devicedetails.setId(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
		    devicedetails.setDid(IMonitorUtil.convertToString(strings[1]));
		    devicedetails.setDeviceName(IMonitorUtil.convertToString(strings[2]));
		    devicedetails.setDeviceIcon(IMonitorUtil.convertToString(strings[3]));
		    devicedetails.setGateway(IMonitorUtil.convertToString(strings[4]));
		    devicedetails.setLocationName(IMonitorUtil.convertToString(strings[5]));
		    devicedetails.setLocationId(Long.parseLong(IMonitorUtil.convertToString(strings[10])));
		    devicedetails.setEnableList(Long.parseLong(IMonitorUtil.convertToString(strings[6])));
		    devicedetails.setDeviceType(IMonitorUtil.convertToString(strings[7]));
		    devicedetails.setAlertParam(IMonitorUtil.convertToString(strings[8]));
		    devicedetails.setCustomer(IMonitorUtil.convertToString(strings[9]));
		   long deviceid=Long.parseLong(IMonitorUtil.convertToString(strings[1]));
		   
		   ImvgAlertsActionsManager ImvgAlertsActionsManager=new ImvgAlertsActionsManager();
		    Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(deviceid);
		    if(objects2 != null && objects2[2]!= null)
			{
			
		    	devicedetails.setHMDpowerinfo(""+(String)objects2[2]);
			}
			else
			{
				devicedetails.setHMDpowerinfo("");
			
			}
		    deviceDetails.add(devicedetails);
	
			}
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("Catch clause caught : {0} \n"+ e.getMessage());
		} finally {
			if (null != dashboard) {
				dashboard.closeConnection();
			}
		}
		return deviceDetails;
	}



	
	
	public List<Object[]> listdashboarddetailsfordevice(String gateWay,String deviceid,long days) {
		// TODO Auto-generated method stub
		
		//LogUtil.info("gateWay----"+gateWay);
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(gateWay);
		dashboardDBConnection dbc = null;
		
		
		String query="";
		if(days == 7)
		{
			 query = "select a.alertvalue,d.gateway,d.did,d.locationName,a.alertTime from" +
						" (select pw.alertValue as alertvalue,pw.did as did,pw.alertTime as alertTime from " +
						"powerinfo as pw where week(pw.alertTime)= week(CURDATE()) and year(alertTime)=year(CURDATE()))as a," +
						"(select did,locationName,customer,gateway from devicedetails where id='"+deviceid+"') as d " +
								"where d.did=a.did and d.customer="+customer+"";
			 
			  
		}
		else if(days == 30)
		{
			 query = "select a.alertvalue,d.gateway,d.did,d.locationName,a.alertTime from" +
						" (select pw.alertValue as alertvalue,pw.did as did,pw.alertTime as alertTime from " +
						"powerinfo as pw where month(pw.alertTime)= month(CURDATE()) and year(alertTime)=year(CURDATE()))as a," +
						"(select did,locationName,customer,gateway from devicedetails where id='"+deviceid+"') as d " +
								"where d.did=a.did and d.customer="+customer+" order by a.alertTime  ";
			 
			  //  LogUtil.info("query----"+query);
		}
		else if(days==365)
		{
			 query = "select a.alertvalue,d.gateway,d.did,d.locationName,a.alertTime from" +
						" (select pw.alertValue as alertvalue,pw.did as did,pw.alertTime as alertTime from " +
						"powerinfo as pw where year(pw.alertTime)= year(CURDATE()))as a," +
						"(select did,locationName,customer,gateway from devicedetails where id='"+deviceid+"') as d " +
								"where d.did=a.did and d.customer="+customer+" group by month(a.alertTime)";
			 
			   // LogUtil.info("query----"+query);
		}
		
		
		
		
		
		/*String query = "select a.alertvalue,d.gateway,d.did,d.locationName,a.alertTime from" +
				" (select pw.alertValue as alertvalue,pw.did as did,pw.alertTime as alertTime from " +
				"powerinfo as pw where date(pw.alertTime)> date(date_sub(NOW(), interval "+days+" day)))as a," +
				"(select did,locationName,gateway from devicedetails where deviceName='"+deviceid+"') as d " +
						"where d.did=a.did and d.gateway='"+gateWay+"'"; */
				
	/*String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+" and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc";*/

		// LogUtil.info("query----"+query);
		
		List<Object[]> objects = null;
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}

		return objects;
	}
	
	
	@SuppressWarnings({ "unchecked", "null" })
	public List<Object[]> listDashboarddetailsforDevicesforyear(String gateWay,String deviceid,long days) {
		// TODO Auto-generated method stub
		
		//LogUtil.info("gateWay----++++"+gateWay);
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(gateWay);
		
		dashboardDBConnection dbc = null;
		/* String query = "select a.alertvalue,d.customer,d.did,d.locationName,a.alertTime from" +
				" (select pw.alertValue as alertvalue,pw.did as did,pw.alertTime as alertTime from " +
				"powerinfo as pw where year(pw.alertTime)= year(CURDATE()))as a," +
				"(select did,locationName,customer,gateway from devicedetails where deviceName='"+deviceid+"') as d " +
						"where d.did=a.did and d.customer="+customer+" group by month(a.alertTime)";
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+" and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc";*/
        
		
		 
		 
		 String query = "select month(alertTime),alertTime from powerinfo where year(alertTime)= year(CURDATE()) group by month(alertTime)";
		 
		 
		List<Object[]> objects = null;
		@SuppressWarnings("unused")
		List<Object[]> objectsforvalue = null;
		
		
		
		List<Object[]> listforSumOfYearDataForDevice = new ArrayList<Object[]>(25);
	
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = q.list();
			dbc.closeConnection();
			
			
			
			
			
			for (Object[] monthvalue : objects) 
			{
				String months=IMonitorUtil.convertToString(monthvalue[0]);
				
				
				 query = "select sum(a.alertvalue),d.customer,d.did,d.locationName,month(a.alertTime) from" +
						" (select pw.alertValue as alertvalue,pw.did as did,pw.alertTime as alertTime from " +
						"powerinfo as pw where year(pw.alertTime)= year(CURDATE()))as a," +
						"(select did,locationName,customer,gateway from devicedetails where id='"+deviceid+"') as d " +
								"where d.did=a.did and d.customer="+customer+" and month(a.alertTime)="+months+"";	
				 //LogUtil.info("query----"+query);
			try {
				dbc = new dashboardDBConnection();
				dbc.openConnection();
				 q = dbc.getSession().createSQLQuery(query);
				objectsforvalue = q.list();
				dbc.closeConnection();
				String SumOfalertvalue="0";
				
				
				
				
				for (Object[] value : objectsforvalue) 
				{
					
					 SumOfalertvalue=IMonitorUtil.convertToString(value[0]);
					
					
					String MonthOfalertValue=IMonitorUtil.convertToString(value[4]);
					
					
					listforSumOfYearDataForDevice.add(value);

				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
			
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
       //  LogUtil.info("listforSumOfYearDataForDevice---"+xStream.toXML(listforSumOfYearDataForDevice));
		return listforSumOfYearDataForDevice;
	}
	
	public List<Object[]> listDashboarddetailsforlocation(String gateWay,String locationId,long days) {
		// TODO Auto-generated method stub
		
		//LogUtil.info("gateWay----"+gateWay);
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(gateWay);
		dashboardDBConnection dbc = null;
		
		
		String query="";
		if(days == 7)
		{
			/* query = "select a.alertvalue,d.deviceName,d.did,d.locationName from " +
						"(select sum(pw.alertValue) as alertvalue,pw.did as did from " +
						"powerinfo as pw where week(pw.alertTime)= week(CURDATE()) and year(alertTime)=year(CURDATE())" +
								"group by pw.did)as a,(select did,deviceName,locationName,locationId,customer,gateway from " +
								"devicedetails where locationId='"+locationId+"') as d " +
										"where d.did=a.did and d.customer="+customer+"";*/
			  //  LogUtil.info("query----"+query);
			query = "select a.alertvalue,d.locationName,a.alertTime from "+
					   "(select sum(pw.alertValue) as alertvalue, pw.alertTime as alertTime,dd.did from powerinfo as pw,devicedetails as dd where pw.did = dd.did "+
						"and dd.locationId="+locationId+" and week(pw.alertTime)= week(CURDATE()) and year(alertTime)=year(CURDATE()) group by alertTime)as a,"+
				       "(select did,deviceName,locationName,customer,gateway from  devicedetails where locationId="+locationId+")as d where d.did=a.did and  d.customer="+customer+"";
		}
		else if(days == 30)
		{
			/* query = "select a.alertvalue,d.deviceName,d.did,d.locationName from " +
						"(select sum(pw.alertValue) as alertvalue,pw.did as did from " +
						"powerinfo as pw where month(pw.alertTime)= month(CURDATE()) and year(alertTime)=year(CURDATE()) " +
								"group by pw.did)as a,(select did,deviceName,locationName,locationId,customer,gateway from " +
								"devicedetails where locationId='"+locationId+"') as d " +
										"where d.did=a.did and d.customer="+customer+"";*/
			   // LogUtil.info("query----"+query);
			query = "select a.alertvalue,d.locationName,a.alertTime from "+
			   "(select sum(pw.alertValue) as alertvalue, pw.alertTime as alertTime,dd.did from powerinfo as pw,devicedetails as dd where pw.did = dd.did "+
					"and dd.locationId="+locationId+" and month(pw.alertTime)= month(CURDATE()) and year(alertTime)=year(CURDATE()) group by alertTime)as a,"+
			       "(select did,deviceName,locationName,customer,gateway from  devicedetails where locationId="+locationId+")as d where d.did=a.did and  d.customer="+customer+"";
		
			
		}
		else if(days==365)
		{
			 /*query = "select a.alertvalue,d.deviceName,d.did,d.locationName from " +
						"(select sum(pw.alertValue) as alertvalue,pw.did as did from " +
						"powerinfo as pw where year(pw.alertTime)= year(CURDATE()) " +
								"group by pw.did)as a,(select did,deviceName,locationName,locationId,customer,gateway from " +
								"devicedetails where locationId='"+locationId+"') as d " +
										"where d.did=a.did and d.customer="+customer+"";*/
			query = "select a.alertvalue,d.gateway,a.alertTime from (select sum(pw.alertValue) as alertvalue, pw.alertTime as "+
					 "alertTime,dd.did from powerinfo as pw,devicedetails as dd where pw.did = dd.did and dd.locationId="+locationId+" and "+
					  "year(alertTime)=year(CURDATE()) group by month(alertTime))as a,(select did,deviceName,locationName,customer,gateway from "+ 
					  "devicedetails where locationId="+locationId+")as d where d.did=a.did and  d.customer="+customer+"";
			   // LogUtil.info("query----"+query);
			
		}
		
		
		
		
		/*String query = "select a.alertvalue,d.deviceName,d.did,d.locationName from " +
				"(select sum(pw.alertValue) as alertvalue,pw.did as did from " +
				"powerinfo as pw where date(pw.alertTime)> date(date_sub(NOW(), interval "+days+" day)) " +
						"group by pw.did)as a,(select did,deviceName,locationName,locationId,gateway from " +
						"devicedetails where locationId='"+locationId+"') as d " +
								"where d.did=a.did and d.gateway='"+gateWay+"'"; */
				
	/*String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+" and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc";*/
    
		// LogUtil.info("query----"+query);
		
		List<Object[]> objects = null;
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = q.list();
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
	
	public List<Object[]> listDashboarddetailsforTotal(String gateWay,long days) {
		// TODO Auto-generated method stub
		//LogUtil.info("gateWay----"+gateWay);
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(gateWay);
		dashboardDBConnection dbc = null;
		String query="";
		if(days == 7)
		{
			/* query = "select a.alertvalue,d.gateway,d.locationName,a.alertTime from" +
						" (select pw.alertValue as alertvalue,pw.did as did,pw.alertTime as alertTime from " +
						"powerinfo as pw where week(pw.alertTime)= week(CURDATE()) and year(alertTime)=year(CURDATE()))as a," +
						"(select did,locationName,customer,gateway from devicedetails ) as d " +
								"where d.locationName=a.did and d.customer="+customer+"";*/
			
/* Naveen changed to get total power consumption irrespective of device and location to show in bar 
 * chart instead of pie chart
 * Date Modified: 07March2014
 * */			
	
			query = "select a.alertvalue,d.gateway,a.alertTime from (select sum(pw.alertValue) as alertvalue, pw.alertTime as alertTime,dd.did from powerinfo as pw,devicedetails as dd "+
			        "where pw.did = dd.did and dd.customer="+customer+" and week(pw.alertTime)= week(CURDATE()) and year(alertTime)=year(CURDATE()) group by alertTime)as a,"+
					"(select did,deviceName,locationName,customer,gateway from  devicedetails)as d where d.did=a.did and  d.customer="+customer+"" ;

		      
			  //  LogUtil.info("query----"+query);
		}
		else if(days == 30)
		{
			 query = "select a.alertvalue,d.gateway,a.alertTime from (select sum(pw.alertValue) as alertvalue, pw.alertTime as alertTime,dd.did from powerinfo as pw,devicedetails as dd "+
				        "where pw.did = dd.did and dd.customer="+customer+" and month(pw.alertTime)= month(CURDATE()) and year(alertTime)=year(CURDATE()) group by alertTime)as a,"+
						"(select did,deviceName,locationName,customer,gateway from  devicedetails)as d where d.did=a.did and  d.customer="+customer+"" ;
		        
			  //  LogUtil.info("query----"+query);
		}
		else if(days==365)
		{
			
			 query = "select a.alertvalue,d.gateway,a.alertTime,month(a.alertTime) from (select sum(pw.alertValue) as alertvalue, pw.alertTime as alertTime,dd.did from "+
			         "powerinfo as pw,devicedetails as dd where pw.did = dd.did and dd.customer="+customer+" and year(alertTime)=year(CURDATE()) group by month(alertTime))as a,"+
			         "(select did,deviceName,locationName,customer,gateway from  devicedetails)as d where d.did=a.did and  d.customer="+customer+"";
		      
			  
		}
		
			//LogUtil.info("query----"+query);
//		String query = "select sum(pw.alertValue),dd.locationName,dd.gateway " +
//				"from powerinfo as pw,devicedetails as dd " +
//				"where date(pw.alertTime)> date(date_sub(NOW(), interval "+days+" day)) " +
//				"and dd.gateway='"+gateWay+"' " +
//				"group by dd.locationName";
		
		/*String query = "select sum(pw.alertValue),d.locationName from " +
				"(select did,locationName,gateway from devicedetails) as d," +
				"(select sum(alertValue) as alertValue,did,alertTime from " +
				"powerinfo group by did) as pw where d.did=pw.did and date(pw.alertTime)> date(date_sub(NOW(), interval "+days+" day))" +
				" and d.gateway='"+gateWay+"' group by d.locationName"; */
				
	/*String query = "select sum(pw.alertValue),d.locationName from " +
			"(select did,locationName,gateway from devicedetails) as d," +
			"(select alertValue as alertValue,did,alertTime from " +
			"powerinfo where date(alertTime)> date(date_sub(NOW(), interval "+days+" day))) as pw " +
			"where d.did=pw.did and d.gateway='30133' group by d.locationName";
      
	    LogUtil.info("query----"+query); */
		
		List<Object[]> objects = null;
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}

	
	
	 @SuppressWarnings("unchecked")
	public boolean saveTargetcost(String gateWay,Customer customer) {
		// TODO Auto-generated method stub
		dashboardDBConnection DashBoard = null;
		boolean result = false;
		Transaction tx = null;

		String query = "select * from targetcost where customer="+customer.getId()+"";
				
		XStream xStream = new XStream();
     
       long customerId=customer.getId();
		try {
			DashBoard = new dashboardDBConnection();
			Session session = DashBoard.openConnection();
			Query q = session.createSQLQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			if(objects != null){
				long id=Long.parseLong(IMonitorUtil.convertToString(objects[0]));
				String customerid=IMonitorUtil.convertToString(objects[2]);
				query = "update targetcost set targetCost='"+gateWay+"' where id="+id+" and customer="+customerid+"";
				q = session.createSQLQuery(query);
				 tx = session.beginTransaction();
				q.executeUpdate();
				result = true;
				
			}
			else
			{
			targetcost targetcost=new targetcost();
			targetcost.setTargetCost(gateWay);
			targetcost.setCustomer(customerId);
			
				session.save(targetcost);
				result=true;
				
			}
			
			
			tx.commit();
			result = true;
		} catch (Exception ex) {
			//vibhu start
			if(tx != null)
			{
				tx.rollback();
			}
			//vibhu end
			
		} finally {
			DashBoard.closeConnection();
		}
		return result;
	}

	 
	 

	 @SuppressWarnings({ "null", "unchecked" })
	public targetcost listDashboardtargetcost(Customer customer) {
			// TODO Auto-generated method stub
		 
		 targetcost targetcost=new targetcost();
			String query = "select t.id,t.targetCost,t.customer,t.kwPerUnit" +
					" from targetcost as t where " +
					"t.customer="+customer.getId()+"";
		    
			XStream xStream=new XStream();
			dashboardDBConnection DashBoard = null;
			try {
				DashBoard = new dashboardDBConnection();
				Session session = DashBoard.openConnection();
				Query q = session.createQuery(query);
				Object[] objects = (Object[])q.uniqueResult();
				if(objects != null){
					long id=Long.parseLong(IMonitorUtil.convertToString(objects[0]));
					String tcost=IMonitorUtil.convertToString(objects[1]);
					long customerid=Long.parseLong(IMonitorUtil.convertToString(objects[2]));
					long kwPerUnit=Long.parseLong(IMonitorUtil.convertToString(objects[3]));
					targetcost.setTargetCost(tcost);
					targetcost.setId(id);
					targetcost.setCustomer(customerid);
					targetcost.setKwPerUnit(kwPerUnit);
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				DashBoard.closeConnection();
			}
			return targetcost;
		}
	 
	 
	 
	 
	 //----------------------------------------------------
	 
	 @SuppressWarnings({ "null", "unchecked" })
		public String listDashboardUptodateUsage(Customer customer) {
				// TODO Auto-generated method stub
			 
		 String uptodateusage = "";
//				String query = "select t.id,t.targetCost,t.customer" +
//						" from targetcost as t where " +
//						"t.customer="+customer.getId()+"";
			    
				
				String query = "select sum(pw.alertValue) as alertValue,customer from" +
						" (select did,customer,gateway from devicedetails) as d," +
						"(select alertValue,did,alertTime from powerinfo) as pw " +
						"where d.did=pw.did and d.customer="+customer.getId()+" and " +
						"month(pw.alertTime)=(MONTH(CURDATE())) and year(pw.alertTime)=year(CURDATE())";
				
				XStream xStream=new XStream();
				dashboardDBConnection DashBoard = null;
				// LogUtil.info("query----"+query);
				
				try {
					DashBoard = new dashboardDBConnection();
					Session session = DashBoard.openConnection();
					Query q = session.createSQLQuery(query);
					Object[] objects = (Object[])q.uniqueResult();
					if(objects != null){
					
						 uptodateusage=IMonitorUtil.convertToString(objects[0]);
					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					DashBoard.closeConnection();
				}
				return uptodateusage;
			}
	 
	 
	 public Object[] listDashboardUptotimeUsage(Customer customer) {
			// TODO Auto-generated method stub
		 Object[] objects = null;
	 String uptodateusage = "";
	 @SuppressWarnings("unused")
	String alerttime = "";
//			String query = "select t.id,t.targetCost,t.customer" +
//					" from targetcost as t where " +
//					"t.customer="+customer.getId()+"";
		    
			
			String query = "select sum(pw.alertValue) as alertValue,max(alertTime) from" +
					" (select did,customer,gateway from devicedetails) as d," +
					"(select alertValue,did,alertTime from averagepowerinformation) as pw " +
					"where d.did=pw.did and d.customer="+customer.getId()+" and " +
					"month(pw.alertTime)=(MONTH(CURDATE())) and year(pw.alertTime)=year(CURDATE())";
			
			XStream xStream=new XStream();
			dashboardDBConnection DashBoard = null;
			 //LogUtil.info("query----"+query);
			
			try {
				DashBoard = new dashboardDBConnection();
				Session session = DashBoard.openConnection();
				Query q = session.createSQLQuery(query);
				 objects = (Object[])q.uniqueResult();
				
				
				 // naveen commented on 24 Nov 2014 to avoid exception in tomcat logs. 
				 /*if(objects != null)
				{
					
					if(objects[0] != null && objects[2]!= null)
					{
						 uptodateusage=IMonitorUtil.convertToString(objects[0]);
						 alerttime=IMonitorUtil.convertToString(objects[2]);
					}
				    
					
					
				}*/
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				DashBoard.closeConnection();
			}
			
			
			
			return objects;
		}
	 
	 @SuppressWarnings({ "unchecked", "unused", "null" })
		public void saveTariffConfiguration(TarrifConfig tariffconfig) {
			// TODO Auto-generated method stub
			dashboardDBConnection DashBoard = null;
			boolean result = false;
			Transaction tx = null;

			//LogUtil.info("saveTariffConfiguration--");
			XStream xStream = new XStream();
			try {
				DashBoard = new dashboardDBConnection();
				Session session = DashBoard.openConnection();
				
				TarrifConfig tarrifconfig=new TarrifConfig();
				tarrifconfig.setCustomer(tariffconfig.getCustomer());
				tarrifconfig.setEndHour(tariffconfig.getEndHour());
				tarrifconfig.setStartHour(tariffconfig.getStartHour());
				tarrifconfig.setStartSlabRange(tariffconfig.getStartSlabRange());
				tarrifconfig.setTarrifRate(tariffconfig.getTarrifRate());
				tarrifconfig.setEndSlabRange(tariffconfig.getEndSlabRange());
			
				tx = session.beginTransaction();
					session.save(tarrifconfig);
					
					result=true;
					
			
				
				tx.commit();
				result = true;
			} catch (Exception ex) {
				//vibhu start
				if(tx != null)
				{
					tx.rollback();
				}
				//vibhu end
				
				LogUtil.error("Error when savign the rule with name : message is " + ex.getMessage());
			} finally {
				DashBoard.closeConnection();
			}
		//	return result;
			//return result;
		}



	public List<TarrifConfig> RetriveTariffConfigurationByCustomer(Customer customer) {
		
		
		dashboardDBConnection DashBoard = null;
		
		
		List<TarrifConfig> tarrifConfig = null ;

		XStream xStream = new XStream();
       
		try {
			DashBoard = new dashboardDBConnection();
			Session session = DashBoard.openConnection();
			
			Criteria criteria = session.createCriteria(TarrifConfig.class);
			Criterion crn = Restrictions.eq("customer", customer.getId());
			criteria.add(crn);
			
			tarrifConfig = criteria.list();
			
			//LogUtil.info(xStream.toXML(tarrifConfig));
			
			
			
		} catch (Exception ex) {
		} 
		DashBoard.closeConnection();
		//LogUtil.info("Retursn");
		return tarrifConfig;
	}

	
	public void updateTariffConfiguration(TarrifConfig tariffconfig) {
		// TODO Auto-generated method stub
		dashboardDBConnection DashBoard = null;
		boolean result = false;
		Transaction tx = null;

		String query = "select tfc from TarrifConfig as tfc " +
				"where tfc.id="+tariffconfig.getId()+"";
				
		XStream xStream = new XStream();
     //  LogUtil.info("query----"+query);
      
     
		try {
			DashBoard = new dashboardDBConnection();
			Session session = DashBoard.openConnection();
			Query q = session.createQuery(query);
			TarrifConfig TarrifConfigFromDb = (TarrifConfig)q.uniqueResult();
			TarrifConfigFromDb.setId(tariffconfig.getId());
			TarrifConfigFromDb.setCustomer(tariffconfig.getCustomer());
			TarrifConfigFromDb.setEndHour(tariffconfig.getEndHour());
			TarrifConfigFromDb.setStartHour(tariffconfig.getStartHour());
			TarrifConfigFromDb.setStartSlabRange(tariffconfig.getStartSlabRange());
			TarrifConfigFromDb.setTarrifRate(tariffconfig.getTarrifRate());
			TarrifConfigFromDb.setEndSlabRange(tariffconfig.getEndSlabRange());
			tx = session.beginTransaction();
		   	session.update(TarrifConfigFromDb);
			tx.commit();
			
		} catch (Exception ex) {
			//vibhu start
			if(tx != null)
			{
				tx.rollback();
			}
			//vibhu end
			
			LogUtil.error("Error when updateing tarrif configuration with name : message is " + ex.getMessage());
		} finally {
			DashBoard.closeConnection();
		}
		return;
		
	}
	
	
	
	public boolean saveUnitvalue(targetcost unitvalue,Customer customer) {
		// TODO Auto-generated method stub
		dashboardDBConnection DashBoard = null;
		boolean result = false;
		Transaction tx = null;

		String query = "select * from targetcost where customer="+customer.getId()+"";
				
		XStream xStream = new XStream();
       long customerId=customer.getId();

		try {
			DashBoard = new dashboardDBConnection();
			Session session = DashBoard.openConnection();
			Query q = session.createSQLQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			
			if(objects != null){
				long id=Long.parseLong(IMonitorUtil.convertToString(objects[0]));
				String target=IMonitorUtil.convertToString(objects[1]);
				String customerid=IMonitorUtil.convertToString(objects[2]);
				
				query = "update targetcost set kwPerUnit='"+unitvalue.getKwPerUnit()+"' where id="+id+" and customer="+customerid+"";
				q = session.createSQLQuery(query);
				 tx = session.beginTransaction();
				q.executeUpdate();
				result = true;
				
			}
			else
			{
				targetcost targetcost=new targetcost();
				targetcost.setCustomer(customerId);
				targetcost.setKwPerUnit(unitvalue.getKwPerUnit());
				targetcost.setTargetCost("0,0,0,0,0,0,0,0,0,0,0,0");
				//targetcost.setTargetCost("0");
				 tx = session.beginTransaction();
					session.save(targetcost);
					result=true;		
				
			}
			
			
			tx.commit();
			result = true;
		} catch (Exception ex) {
			//vibhu start
			if(tx != null)
			{
				tx.rollback();
			}
			//vibhu end
			
			LogUtil.error("Error when savign the rule with name : message is " + ex.getMessage());
		} finally {
			DashBoard.closeConnection();
		}
		return result;
	}
	
	
	public boolean saveReachedPowerlimit(long reachedlimit,Customer customer) {
		// TODO Auto-generated method stub
		dashboardDBConnection DashBoard = null;
		boolean result = false;
		Transaction tx = null;

		String query = "select * from targetcost where customer="+customer.getId()+"";
				
		XStream xStream = new XStream();
       long customerId=customer.getId();

		try {
			DashBoard = new dashboardDBConnection();
			Session session = DashBoard.openConnection();
			Query q = session.createSQLQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			long id=Long.parseLong(IMonitorUtil.convertToString(objects[0]));
			String target=IMonitorUtil.convertToString(objects[1]);
			String customerid=IMonitorUtil.convertToString(objects[2]);
			if(objects != null){
				
				
				query = "update targetcost set ReachedPowerlimit='"+reachedlimit+"' where id="+id+" and customer="+customerid+"";
				q = session.createSQLQuery(query);
				 tx = session.beginTransaction();
				q.executeUpdate();
				result = true;
				
			}
			else
			{
			targetcost targetcost=new targetcost();
			targetcost.setId(id);
			targetcost.setReachedPowerlimit(reachedlimit);
			targetcost.setCustomer(customerId);
			targetcost.setTargetCost(target);
				
				session.save(targetcost);
				
				result=true;
				
			}
			
			
			tx.commit();
			result = true;
		} catch (Exception ex) {
			
			if(tx != null)
			{
				tx.rollback();
			}
			
			
			LogUtil.error("Error when savign the rule with name : message is " + ex.getMessage());
		} finally {
			DashBoard.closeConnection();
		}
		return result;
	}
	
	
	public boolean savewarningPowerlimit(long warninglimit,Customer customer) {
		// TODO Auto-generated method stub
		dashboardDBConnection DashBoard = null;
		boolean result = false;
		Transaction tx = null;

		String query = "select * from targetcost where customer="+customer.getId()+"";
				
		XStream xStream = new XStream();
       long customerId=customer.getId();

		try {
			DashBoard = new dashboardDBConnection();
			Session session = DashBoard.openConnection();
			Query q = session.createSQLQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			long id=Long.parseLong(IMonitorUtil.convertToString(objects[0]));
			String target=IMonitorUtil.convertToString(objects[1]);
			String customerid=IMonitorUtil.convertToString(objects[2]);
			if(objects != null){
				
				
				query = "update targetcost set WarningPowerlimit='"+warninglimit+"' where id="+id+" and customer="+customerid+"";
				q = session.createSQLQuery(query);
				 tx = session.beginTransaction();
				q.executeUpdate();
				result = true;
				
			}
			else
			{
			targetcost targetcost=new targetcost();
			targetcost.setId(id);
			targetcost.setWarningPowerlimit(warninglimit);
			targetcost.setCustomer(customerId);
			targetcost.setTargetCost(target);
				
				session.save(targetcost);
				
				result=true;
				
			}
			
			
			tx.commit();
			result = true;
		} catch (Exception ex) {
			
			if(tx != null)
			{
				tx.rollback();
			}
			
			LogUtil.error("Error when savign the rule with name : message is " + ex.getMessage());
		} finally {
			DashBoard.closeConnection();
		}
		return result;
	}
	
	
	@SuppressWarnings("unused")
	public long listDashboardUptodatecost(Customer customer) {
		// TODO Auto-generated method stub
	 
		long totalcost=0;
		
//		String query = "select t.id,t.targetCost,t.customer" +
//				" from targetcost as t where " +
//				"t.customer="+customer.getId()+"";
	    
		
		String query = "select sum(pw.alertValue) as alertValue,customer from" +
				" (select did,customer from devicedetails) as d," +
				"(select alertValue,did,alertTime from powerinfo) as pw " +
				"where d.did=pw.did and d.customer="+customer.getId()+" and " +
				"month(pw.alertTime)=(MONTH(CURDATE())) and year(pw.alertTime)=year(CURDATE())";
		
		XStream xStream=new XStream();
		double uptodateusage=0;
		List<Object[]> slabrange = null;
	 // LogUtil.info("query----"+query);
		dashboardDBConnection DashBoard = null;
		try {
			DashBoard = new dashboardDBConnection();
			Session session = DashBoard.openConnection();
			Query q = session.createSQLQuery(query);
			Object[] objects = (Object[])q.uniqueResult();
			if(objects != null)
			{
				
				if(objects[0] != null)
				{
					uptodateusage=((Number) objects[0]).doubleValue();
					uptodateusage=uptodateusage/1000;
				}
				
			
			
			query = "select * from tariffconfiguration where customer="+customer.getId() +"";
			q = session.createSQLQuery(query);
			slabrange= q.list();
			
			query = "select kwPerUnit from targetcost where customer="+customer.getId() +"";
			q = session.createSQLQuery(query);
			Object kwPerUnit = q.uniqueResult();
			
			long Unitvalue=100;
			if(kwPerUnit != null){
				 Unitvalue= ((Number) kwPerUnit).longValue();
			}
			else
			{
				Unitvalue=100;
			}
			float totalUnit=(float) (uptodateusage/Unitvalue);
			
			//LogUtil.info("totalUnit===="+totalUnit);
			
			long totaluptodatecost=0;
			for (Object[] strings : slabrange)
			{	
			TarrifConfig tariffconfig=new TarrifConfig();
			tariffconfig.setId(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
			tariffconfig.setCustomer(Long.parseLong(IMonitorUtil.convertToString(strings[1])));
			tariffconfig.setStartHour(Long.parseLong(IMonitorUtil.convertToString(strings[2])));
			tariffconfig.setEndHour(Long.parseLong(IMonitorUtil.convertToString(strings[3])));
			tariffconfig.setTarrifRate(IMonitorUtil.convertToString(strings[4]));
			tariffconfig.setStartSlabRange(Long.parseLong(IMonitorUtil.convertToString(strings[5])));
			tariffconfig.setEndSlabRange(Long.parseLong(IMonitorUtil.convertToString(strings[6])));
			

			long endslab=tariffconfig.getEndSlabRange();

			long startslab=tariffconfig.getStartSlabRange();
	
			long tarrifRate=(Long.parseLong(IMonitorUtil.convertToString(strings[4])));
		
			
			if(startslab==0)
			{
				if(totalUnit > startslab && totalUnit > endslab)
				{
					totaluptodatecost=totaluptodatecost+((endslab-(startslab))*tarrifRate);
				}
				else if(totalUnit > startslab && totalUnit < endslab)
				{
					totaluptodatecost=(long) (totaluptodatecost+((totalUnit-(startslab))*tarrifRate));
				}
				else if(totalUnit < startslab)
				{
					totaluptodatecost=totaluptodatecost+0;
				}
				
			}
			
			else
				{
	
				if(totalUnit > startslab && totalUnit > endslab)
				
			{
				totaluptodatecost=totaluptodatecost+((endslab-(startslab-1))*tarrifRate);
			}
			else if(totalUnit > startslab && totalUnit < endslab)
			{
				totaluptodatecost=(long) (totaluptodatecost+((totalUnit-(startslab-1))*tarrifRate));
			}
			else if(totalUnit < startslab)
			{
				totaluptodatecost=totaluptodatecost+0;
			}
			}
			}
			totalcost=totaluptodatecost;
			//LogUtil.info("totalcost===="+totalcost);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DashBoard.closeConnection();
		}
		return totalcost;
	}


	
	@SuppressWarnings("unchecked")
	public float listDashboardUptotimecost(Customer customer) {
		// TODO Auto-generated method stub
	 
		float totalcost=0;
		dashboardDBConnection DashBoard = null;
		String query="";
//		String query = "select t.id,t.targetCost,t.customer" +
//				" from targetcost as t where " +
//				"t.customer="+customer.getId()+"";
	    
		
		List<Object[]> slabrange = null;
		float totalUnit=0;
		DashBoard = new dashboardDBConnection();	
		Session session = DashBoard.openConnection();
			query = "select * from tariffconfiguration where customer="+customer.getId() +"";
			Query q = session.createSQLQuery(query);
			slabrange= q.list();
			
			query = "select kwPerUnit from targetcost where customer="+customer.getId() +"";
			q = session.createSQLQuery(query);
			Object kwPerUnit = q.uniqueResult();
			
			DashBoard.closeConnection();
			
			long Unitvalue=100;
			if(kwPerUnit != null){
				 Unitvalue= ((Number) kwPerUnit).longValue();
			}
			else
			{
				Unitvalue=1;
			}
			
			
			
			float totaluptodatecost=0;
			for (Object[] slabrangefordash : slabrange)
			{	
				
				
			TarrifConfig tariffconfig=new TarrifConfig();
			tariffconfig.setId(Long.parseLong(IMonitorUtil.convertToString(slabrangefordash[0])));
			tariffconfig.setCustomer(Long.parseLong(IMonitorUtil.convertToString(slabrangefordash[1])));
			tariffconfig.setStartHour(Long.parseLong(IMonitorUtil.convertToString(slabrangefordash[2])));
			tariffconfig.setEndHour(Long.parseLong(IMonitorUtil.convertToString(slabrangefordash[3])));
			tariffconfig.setTarrifRate(IMonitorUtil.convertToString(slabrangefordash[4]));
			tariffconfig.setStartSlabRange(Long.parseLong(IMonitorUtil.convertToString(slabrangefordash[5])));
			tariffconfig.setEndSlabRange(Long.parseLong(IMonitorUtil.convertToString(slabrangefordash[6])));
			
			

			long endslab=tariffconfig.getEndSlabRange();

			
			
			long startslab=tariffconfig.getStartSlabRange();

			long tarrifRate=(Long.parseLong(IMonitorUtil.convertToString(slabrangefordash[4])));
		
			
			query = "select sum(pw.alertValue) as alertValue,customer from" +
					" (select did,customer from devicedetails) as d," +
					"(select alertValue,did,alertTime from averagepowerinformation) as pw " +
					"where d.did=pw.did and d.customer="+customer.getId()+" and " +
					"month(pw.alertTime)=(MONTH(CURDATE())) and year(pw.alertTime)=year(CURDATE()) and hour(alertTime)<="+tariffconfig.getEndHour()+" and hour(alertTime)>="+tariffconfig.getStartHour()+"";
			
			
			
			double uptodateusage=0;
			
			// LogUtil.info("query----"+query);
			
			try {
				DashBoard = new dashboardDBConnection();
				 session = DashBoard.openConnection();
				 q = session.createSQLQuery(query);
				Object[] objectsusage = (Object[])q.uniqueResult();
				if(objectsusage != null){

					uptodateusage=((Number) objectsusage[0]).doubleValue();
					//LogUtil.info("uptodateusage-----"+uptodateusage);
					uptodateusage=uptodateusage/1000;
			
				}
			
				
				 totalUnit=(float) (uptodateusage/Unitvalue);
			//	LogUtil.info("totalUnit---"+totalUnit);
				
				//LogUtil.info("tarrifRate---"+tarrifRate);
				//LogUtil.info("totalUnit---"+totalUnit);
			}catch (Exception e) {
				e.printStackTrace();
				LogUtil.info("Catch clause  power info from average power caught\n"+ e.getMessage());
			} finally {
				if (null != DashBoard) {
					DashBoard.closeConnection();
				         }
			          }
			
			
			
			
			
			
			if(startslab==0)
			{
				if(totalUnit > startslab && totalUnit > endslab)
				{
					totaluptodatecost=totaluptodatecost+((endslab-(startslab))*tarrifRate);
				}
				else if(totalUnit > startslab && totalUnit < endslab)
				{
					totaluptodatecost=totaluptodatecost+((totalUnit-(startslab))*tarrifRate);
				}
				else if(totalUnit < startslab)
				{
					totaluptodatecost=totaluptodatecost+0;
				}
				
			}
			
			else
				{
			
				if(totalUnit > startslab && totalUnit > endslab)
				
			{
				totaluptodatecost=totaluptodatecost+((endslab-(startslab-1))*tarrifRate);
			}
			else if(totalUnit > startslab && totalUnit < endslab)
			{
				totaluptodatecost=totaluptodatecost+((totalUnit-(startslab-1))*tarrifRate);
			}
			else if(totalUnit < startslab)
			{
				totaluptodatecost=totaluptodatecost+0;
			}
			}
			totalcost=totaluptodatecost;
			}
		return totalcost;
	}


	public String RemoveTarriffRow(String xmlRowId) {
		// TODO Auto-generated method stub
		String result="success";
		XStream xStream=new XStream();
		String RowId=(String) xStream.fromXML(xmlRowId);
        dashboardDBConnection DashBoard = null;
        
        String query = "delete from tariffconfiguration where id="+RowId+"";
        
		try {
			DashBoard = new dashboardDBConnection();
			Session session = DashBoard.openConnection();
			Query q = session.createSQLQuery(query);
			Transaction tx = session.beginTransaction();
			q.executeUpdate();
			tx.commit();
			result = "success";
		    
	} catch (Exception ex) {
		LogUtil.error("Error is deleting row with id and message : " + ex.getMessage());
		result = "failure";
	} finally {
		DashBoard.closeConnection();
	}
	return result;
	}
	//bhavya end
	
	@SuppressWarnings("unchecked")
	public List<Powerinformation> listHourlyConsumptionOfdevice(String deviceid, String selectedType) {
			
		
		List<Powerinformation> PowerInformation = new ArrayList<Powerinformation>();
		dashboardDBConnection dbc = null;
		String query = null;
		
		if(selectedType.equals("hour"))
		{
			query = "select pw.alertValue,unix_timestamp(pw.alertTime)*1000 from averagepowerinformation as pw where DATE(pw.alertTime) >= CURDATE() and pw.did ='"+deviceid+"' order by pw.alertTime";
		}
		else if(selectedType.equals("day"))
		{
			query = "select pw.alertValue,unix_timestamp(pw.alertTime)*1000,pw.did from powerinfo as pw where week(pw.alertTime)= week(CURDATE()) and year(pw.alertTime) = year(CURDATE())" +
					" and pw.did = '"+deviceid+"' order by pw.alertTime";
		}
		else if(selectedType.equals("month"))
		{
			query = "select pw.alertValue,unix_timestamp(pw.alertTime)*1000,pw.did from powerinfo as pw where month(pw.alertTime)= month(CURDATE()) and year(pw.alertTime) = year(CURDATE())" +
					"and pw.did = '"+deviceid+"' order by pw.alertTime";
		}
		else if(selectedType.equals("year"))
		{
			query = "select pw.alertValue,unix_timestamp(pw.alertTime)*1000,pw.did from powerinfo as pw where year(pw.alertTime)= year(CURDATE()) and year(pw.alertTime) = year(CURDATE()) group by month(pw.alertTime)" +
					"and pw.did = '"+deviceid+"' order by pw.alertTime";
		}
		
		
	
		
		List<Object[]> objects=null;
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = (List<Object[]>) q.list();
			
			for (Object[] strings : objects)
			{
				Powerinformation pw=new Powerinformation();
				pw.setAlertTime(IMonitorUtil.convertToString(strings[1]));
				pw.setAlertValue(IMonitorUtil.convertToString(strings[0]));
				PowerInformation.add(pw);
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}

		return PowerInformation;
	}
	
	public List<DeviceDetails> getdashboarddetailsOfCustomerForSelectedRoom(Customer customer,String roomName) {

		dashboardDBConnection dashboard = null;
		XStream xStream=new XStream();
		List<DeviceDetails> deviceDetails = new ArrayList<DeviceDetails>();
		String query = "select "
				+ " dd.id,dd.did,dd.deviceName,dd.deviceIcon,dd.gateway,dd.locationName,dd.enableList,dd.deviceType,dd.alertParam,dd.customer,dd.locationId "
				+ " from devicedetails as dd"
				+ " where dd.customer='"
				+ customer.getId() + "' and dd.locationName='"+roomName+"'";
				
		
		List<Object[]> objects = null;
		@SuppressWarnings("unused")
		List<Object[]> targetcost = null;
		 //LogUtil.info("query----"+query);
		try {
			dashboard = new dashboardDBConnection();
			Session session = dashboard.openConnection();
			Query q = session.createSQLQuery(query);
			objects = q.list();
			dashboard.closeConnection();
			for (Object[] strings : objects)
			{
			
				
			DeviceDetails devicedetails=new DeviceDetails();
			devicedetails.setId(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
		    devicedetails.setDid(IMonitorUtil.convertToString(strings[1]));
		    devicedetails.setDeviceName(IMonitorUtil.convertToString(strings[2]));
		    devicedetails.setDeviceIcon(IMonitorUtil.convertToString(strings[3]));
		    devicedetails.setGateway(IMonitorUtil.convertToString(strings[4]));
		    devicedetails.setLocationName(IMonitorUtil.convertToString(strings[5]));
		    devicedetails.setLocationId(Long.parseLong(IMonitorUtil.convertToString(strings[10])));
		    devicedetails.setEnableList(Long.parseLong(IMonitorUtil.convertToString(strings[6])));
		    devicedetails.setDeviceType(IMonitorUtil.convertToString(strings[7]));
		    devicedetails.setAlertParam(IMonitorUtil.convertToString(strings[8]));
		    devicedetails.setCustomer(IMonitorUtil.convertToString(strings[9]));
		   long deviceid=Long.parseLong(IMonitorUtil.convertToString(strings[1]));
		   
		   ImvgAlertsActionsManager ImvgAlertsActionsManager=new ImvgAlertsActionsManager();
		    Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(deviceid);
		    if(objects2 != null && objects2[2]!= null)
			{
			
		    	devicedetails.setHMDpowerinfo(""+(String)objects2[2]);
			}
			else
			{
				devicedetails.setHMDpowerinfo("");
			
			}
		    deviceDetails.add(devicedetails);
	
			}
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("Catch clause caught : {0} \n"+ e.getMessage());
		} finally {
			if (null != dashboard) {
				dashboard.closeConnection();
			}
		}
		return deviceDetails;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Powerinformation> listYearlyConsumptionOfdevice(String did) {

		List<Powerinformation> PowerInformation = new ArrayList<Powerinformation>();
		
		dashboardDBConnection dbc = null;
		
		
		String query = "select sum(pw.alertValue),unix_timestamp(pw.alertTime)*1000 from (select did,customer,gateway from devicedetails) as d,"+
		                "(select alertValue as alertValue,did,alertTime from powerinfo where year(alertTime)= year(CURDATE())) as pw where d.did=pw.did and d.did='"+did+"'";
		


		
		List<Object[]> objects = null;
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = q.list();
			dbc.closeConnection();
			for (Object[] value : objects) 
			{
				
				Powerinformation pw=new Powerinformation();
				pw.setAlertTime(IMonitorUtil.convertToString(value[1]));
				pw.setAlertValue(IMonitorUtil.convertToString(value[0]));
				PowerInformation.add(pw);
				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
	   
		return PowerInformation;

	}

	public List<Object[]> listDashboarddetailsforDaily(String gateWay) {
		// TODO Auto-generated method stub
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(gateWay);
		dashboardDBConnection dbc = null;
		String query="";
		
		query = "select a.alertvalue,d.locationName,d.gateway from  (select sum(pw.alertValue) as alertvalue,pw.did as did,pw.alertTime " +
				" as alertTime from averagepowerinformation as pw where DATE(pw.alertTime)= DATE(CURDATE()) " +
				"group by pw.did)as a, (select did,locationName,customer,gateway from devicedetails ) " +
				"as d where d.did=a.did and  d.customer="+customer+" order by d.locationName";
				
			
    	List<Object[]> objects = null;
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
	public List<Object[]> listDashboarddetailsforweek(String gateWay) {
		// TODO Auto-generated method stub
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(gateWay);
		dashboardDBConnection dbc = null;
		String query="";
		
		query = "select a.alertvalue,d.locationName,d.gateway from  (select sum(pw.alertValue) as alertvalue,pw.did as did,pw.alertTime " +
				" as alertTime from powerinfo as pw where week(pw.alertTime)= week(CURDATE()) " +
				"group by pw.did)as a, (select did,locationName,customer,gateway from devicedetails ) " +
				"as d where d.did=a.did and  d.customer="+customer+" order by d.locationName";
				
			
    	List<Object[]> objects = null;
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
	
	@SuppressWarnings({ "unused", "null" })
	public List<Object[]> listDashboarddetailsforMonth(String gateWay) {
		// TODO Auto-generated method stub
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(gateWay);
		dashboardDBConnection dbc = null;
		String query="";
		
		query = "select a.alertvalue,d.locationName,d.gateway from  (select sum(pw.alertValue) as alertvalue,pw.did as did,pw.alertTime " +
				" as alertTime from powerinfo as pw where MONTH(pw.alertTime)= MONTH(CURDATE()-INTERVAL 1 MONTH) " +
				"group by pw.did)as a, (select did,locationName,customer,gateway from devicedetails ) " +
				"as d where d.did=a.did and  d.customer="+customer+" order by d.locationName";
				
		
    	List<Object[]> queryobjects = null;
    	List<Object[]> objects = new ArrayList<Object[]>();
    	Object[] locationObject = new Object[3];
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			queryobjects = q.list();
			/*String previousLocation = null;
			Double alertValue = (double) 0;
			for(Object[] object: queryobjects){
				
				String locationName = IMonitorUtil.convertToString(object[1]);
				LogUtil.info("location Name: "+ locationName + " current location: "+ previousLocation);
				if((locationName == previousLocation) || previousLocation == null){
					
					alertValue = alertValue+Double.parseDouble(IMonitorUtil.convertToString(object[0]));
				    LogUtil.info("alertValue: "+ alertValue);			
				}else{
					LogUtil.info("step 1");
					locationObject[0] = locationName;
					LogUtil.info("step 2");
					locationObject[1] = alertValue;
					LogUtil.info("step 3");
					alertValue = Double.parseDouble(IMonitorUtil.convertToString(object[0]));
					LogUtil.info("location objects: "+ xStream.toXML(locationObject));
					LogUtil.info("step 4");
					
					objects.add(locationObject);
					LogUtil.info("step 5");
				}
				previousLocation = locationName;
			}*/
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return queryobjects;
	}
	
	@SuppressWarnings({ "null", "unchecked" })
	public List<Object[]> listDashboardCostDetails(Customer customer) {
			// TODO Auto-generated method stub
		 
		 CostDetails targetcost=new CostDetails();
			String query = "select c.id,c.customer,c.cost,unix_timestamp(c.alertTime)*1000" +
					" from costdetails as c where " +
					"c.customer="+customer.getId()+" and month(c.alertTime)=(MONTH(CURDATE())) and year(c.alertTime)=year(CURDATE())";
		    
			
			
			List<Object[]> objects = null;
			XStream xStream=new XStream();
			dashboardDBConnection DashBoard = null;
			try {
				DashBoard = new dashboardDBConnection();
				Session session = DashBoard.openConnection();
				Query q = session.createSQLQuery(query);
				objects = q.list();
				
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				DashBoard.closeConnection();
			}
			return objects;
		}
	
	public DeviceDetails getdashboarddetailsOfCustomerForSelectedDevice(Customer customer,String device) {

		dashboardDBConnection dashboard = null;
		XStream xStream=new XStream();
		DeviceDetails deviceDetails = new DeviceDetails();
		String query = "select "
				+ " dd.id,dd.did,dd.deviceName,dd.deviceIcon,dd.gateway,dd.locationName,dd.enableList,dd.deviceType,dd.alertParam,dd.customer,dd.locationId "
				+ " from devicedetails as dd"
				+ " where dd.customer='"
				+ customer.getId() + "' and dd.id='"+device+"'";
				
		
		
		
		Object[] objects = null;
		@SuppressWarnings("unused")
		List<Object[]> targetcost = null;
		 //LogUtil.info("query----"+query);
		try {
			dashboard = new dashboardDBConnection();
			Session session = dashboard.openConnection();
			Query q = session.createSQLQuery(query);
			objects = (Object[])q.uniqueResult();
			dashboard.closeConnection();
						
				
			DeviceDetails devicedetails=new DeviceDetails();
			deviceDetails.setId(Long.parseLong(IMonitorUtil.convertToString(objects[0])));
			deviceDetails.setDid(IMonitorUtil.convertToString(objects[1]));
			deviceDetails.setDeviceName(IMonitorUtil.convertToString(objects[2]));
			deviceDetails.setDeviceIcon(IMonitorUtil.convertToString(objects[3]));
			deviceDetails.setGateway(IMonitorUtil.convertToString(objects[4]));
			deviceDetails.setLocationName(IMonitorUtil.convertToString(objects[5]));
			deviceDetails.setLocationId(Long.parseLong(IMonitorUtil.convertToString(objects[10])));
			deviceDetails.setEnableList(Long.parseLong(IMonitorUtil.convertToString(objects[6])));
			deviceDetails.setDeviceType(IMonitorUtil.convertToString(objects[7]));
			deviceDetails.setAlertParam(IMonitorUtil.convertToString(objects[8]));
			deviceDetails.setCustomer(IMonitorUtil.convertToString(objects[9]));
		   long deviceid=Long.parseLong(IMonitorUtil.convertToString(objects[1]));
		   
		   ImvgAlertsActionsManager ImvgAlertsActionsManager=new ImvgAlertsActionsManager();
		    Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(deviceid);
		    if(objects2 != null && objects2[2]!= null)
			{
			
		    	deviceDetails.setHMDpowerinfo(""+(String)objects2[2]);
			}
			else
			{
				deviceDetails.setHMDpowerinfo("");
			
			}
		   
	
		
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("Catch clause caught : {0} \n"+ e.getMessage());
		} finally {
			if (null != dashboard) {
				dashboard.closeConnection();
			}
		}
		return deviceDetails;
	}
	
	
	@SuppressWarnings({ "null", "unchecked" })
	public String listMonthUsage(Customer customer) {
			// TODO Auto-generated method stub
		 
	 String lastMonthusage = "";
String query = null;
	 int month=Calendar.getInstance().get(Calendar.MONTH);
	 int year = Calendar.getInstance().get(Calendar.YEAR);
	
	        if(month == 0){
	        	year = year - 1;
	        	query = "select sum(pw.alertValue) as alertValue from" +
						" (select did,customer,gateway from devicedetails) as d," +
						"(select alertValue,did,alertTime from powerinfo) as pw " +
						"where d.did=pw.did and d.customer="+customer.getId()+" and " +
						"month(pw.alertTime)='12' and year(pw.alertTime)="+year+"";
	        }else{
	        	query = "select sum(pw.alertValue) as alertValue from" +
						" (select did,customer,gateway from devicedetails) as d," +
						"(select alertValue,did,alertTime from powerinfo) as pw " +
						"where d.did=pw.did and d.customer="+customer.getId()+" and " +
						"month(pw.alertTime)=MONTH(NOW()-1) and year(pw.alertTime)=year(CURDATE())";
	        	
	        }
			
			
			
			XStream xStream=new XStream();
			dashboardDBConnection DashBoard = null;
			
			
			try {
				DashBoard = new dashboardDBConnection();
				Session session = DashBoard.openConnection();
				Query q = session.createSQLQuery(query);
				Object obj = (Object)q.uniqueResult();
				if(obj != null){
				
					lastMonthusage=IMonitorUtil.convertToString(obj);
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				DashBoard.closeConnection();
			}
			return lastMonthusage;
		}
	public List<Object[]> energyconsumptiondatafromdb(String id,String selectedtype) {
		dashboardDBConnection dbc = null;
		String query = null;
		
		if(selectedtype.equals("hour"))
		{
			query = "select dev.deviceName,dev.locationName,truncate(avg.alertValue/1000,4),avg.alertTime from devicedetails as dev left join averagepowerinformation as avg on dev.did=avg.did where DATE(avg.alertTime) >= CURDATE() and dev.customer='"+id+"' order by avg.alertTime";
		}
		else if(selectedtype.equals("day"))
		{
			query = "select dev.deviceName,dev.locationName,truncate(pw.alertValue/1000,4),pw.alertTime from devicedetails as dev left join powerinfo as pw on dev.did=pw.did where week(pw.alertTime)= week(CURDATE()) and year(pw.alertTime) = year(CURDATE()) and dev.customer='"+id
					+"' order by pw.alertTime";
		}
		else if(selectedtype.equals("month"))
		{
			query = "select dev.deviceName,dev.locationName,truncate(pw.alertValue/1000,4),pw.alertTime from devicedetails as dev left join powerinfo as pw on dev.did=pw.did where month(pw.alertTime)= month(CURDATE()) and year(pw.alertTime) = year(CURDATE())and dev.customer='"+id
					+"' order by pw.alertTime";
		}
		else if(selectedtype.equals("year"))
		{
			query = "select dev.deviceName,dev.locationName,truncate(pw.alertValue/1000,4),pw.alertTime from devicedetails as dev left join powerinfo as pw on dev.did=pw.did where year(pw.alertTime)= year(CURDATE()) and year(pw.alertTime) = year(CURDATE()) group by month(pw.alertTime)" +
					"and dev.customer='"+id+"' order by pw.alertTime";
		}
		
		
		
		
		List<Object[]> objects=null;
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = (List<Object[]>) q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
	public List<Object[]> energyintervaldatafromdb(String id) {
		DBConnection dbc = null;
		//String query = "select d.friendlyName,l.name,truncate(e.alertValue,4),e.alertTime from device as d left join gateway as g on d.gateWay=g.id left join location as l on d.location=l.id left join energyInformationFromImvg as e on d.id=e.device where g.customer='"+id+"' and d.modelNumber='HMD'";
		String query = "select d.friendlyName,l.name,truncate(e.alertValue,4),e.alertTime from energyInformationFromImvg as e left join device as d on d.id=e.device left join gateway as g on d.gateWay=g.id left join location as l on d.location=l.id  where g.customer='"+id+"' and d.modelNumber='HMD'";
		
		List<Object[]> objects=null;
		
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = (List<Object[]>) q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
public List<Powerinformation> listHourlyConsumptionOfdeviceWithTwodates(
			Customer customer, Timestamp fromTimeStamp, Timestamp toTimeStamp) {
		List<Powerinformation> PowerInformation = new ArrayList<Powerinformation>();
		dashboardDBConnection dbc = null;
		
		
		String query = "select sum(pw.alertValue),unix_timestamp(pw.alertTime)*1000 " +
				"from (select did,customer from devicedetails) as d," +
				"(select alertValue as alertValue,did,alertTime from powerinfo where  " +
				"alertTime >= '"+ fromTimeStamp +"'"+" and alertTime <= '"+ toTimeStamp +"'"+")" +
				"as pw where d.customer='"+customer.getId()+"' and pw.did = d.did group by pw.alertTime";
		
		
		
		
	
		
		List<Object[]> objects=null;
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = (List<Object[]>) q.list();
			
			for (Object[] strings : objects)
			{
				Powerinformation pw=new Powerinformation();
				pw.setAlertTime(IMonitorUtil.convertToString(strings[1]));
				pw.setAlertValue(IMonitorUtil.convertToString(strings[0]));
				PowerInformation.add(pw);
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}

		return PowerInformation;
	}
	public List<Powerinformation> listDeviceWiseConsumptionWithTwodates(
			String deviceid, Timestamp fromTimeStamp, Timestamp toTimeStamp) {
		List<Powerinformation> PowerInformation = new ArrayList<Powerinformation>();
		dashboardDBConnection dbc = null;
		
		
		String query = "select sum(pw.alertValue),unix_timestamp(pw.alertTime)*1000 " +
				"from (select did,customer from devicedetails) as d," +
				"(select alertValue as alertValue,did,alertTime from powerinfo where  " +
				"alertTime >= '"+ fromTimeStamp +"'"+" and alertTime <= '"+ toTimeStamp +"'"+")" +
				"as pw where pw.did ='"+deviceid+"' and pw.did = d.did group by pw.alertTime";
		
		
		
		
	
		
		List<Object[]> objects=null;
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = (List<Object[]>) q.list();
			
			for (Object[] strings : objects)
			{
				Powerinformation pw=new Powerinformation();
				pw.setAlertTime(IMonitorUtil.convertToString(strings[1]));
				pw.setAlertValue(IMonitorUtil.convertToString(strings[0]));
				PowerInformation.add(pw);
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}

		return PowerInformation;
	}
	
	public String listDashboardtotalwattage(long customerid) {
		dashboardDBConnection dbc = null;
		String query = "select round(sum(pw.alertValue)) from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customerid+"'"+" and MONTH(pw.alertTime)= MONTH(CURDATE())";
		//LogUtil.info(query);
		
		String result=null;
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			Object obj=q.uniqueResult();
			result= obj.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return result;
	}
	public List<Object[]> listDashboardwattageroom(Customer customer) {
		// TODO Auto-generated method stub
		XStream xStream = new XStream();
		dashboardDBConnection dbc = null;
		String query="";
		
		query = "select (round(sum(pw.alertValue))/1000) as alertvalue,d.locationName from powerinfo as pw left join  devicedetails as d on d.did=pw.did where  d.customer='"+customer.getId()+"'"+"and  MONTH(pw.alertTime)= MONTH(CURDATE()) group by d.locationName";
				
		
    	List<Object[]> queryobjects = null;
    	
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			queryobjects = q.list();
			//LogUtil.info("final objects for room:"+xStream.toXML(queryobjects));		
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return queryobjects;
	}
	
	public List<Object[]> listtoppowerdevices(Customer customer) {
		// TODO Auto-generated method stub
		XStream xStream = new XStream();
		dashboardDBConnection dbc = null;
		String query="";
		
		query = "select (round(sum(pw.alertValue))/1000),d.deviceName from powerinfo as pw left join devicedetails as d on pw.did=d.did where MONTH(pw.alertTime)=MONTH(CURDATE()) and d.customer='"+customer.getId()+"'"+"group by pw.did order by pw.alertValue desc limit 3";
		
    	List<Object[]> queryobjects = null;
    	
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			queryobjects = q.list();
			//LogUtil.info("final objects for device:"+xStream.toXML(queryobjects));		
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return queryobjects;
	}
	public List<Object[]> subpiedaily(String gateWay,long locid) {
		// TODO Auto-generated method stub
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(gateWay);
		dashboardDBConnection dbc = null;
		String query="";
		
		query ="select a.alertvalue,d.deviceName,d.locationName from  (select sum(pw.alertValue) as alertvalue,pw.did as did,pw.alertTime as alertTime from averagepowerinformation as pw where DATE(pw.alertTime)= DATE(CURDATE())group by pw.did)as a left join (select did,deviceName,locationName,locationId,customer,gateway from devicedetails )as d on a.did=d.did" +
		" where d.locationId='"+locid+"'"+" and d.customer='"+customer+"'"; 
				
		//LogUtil.info(query);	
    	List<Object[]> objects = null;
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
	public List<Object[]> subpieweek(String gateWay,long locid) {
		// TODO Auto-generated method stub
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(gateWay);
		dashboardDBConnection dbc = null;
		String query="";
		
		query ="select a.alertvalue,d.deviceName,d.locationName from  (select sum(pw.alertValue) as alertvalue,pw.did as did,pw.alertTime as alertTime from powerinfo as pw where WEEK(pw.alertTime)= WEEK(CURDATE())group by pw.did)as a left join (select did,deviceName,locationName,locationId,customer,gateway from devicedetails )as d on a.did=d.did " +
		" where d.locationId='"+locid+"'"+"  and  d.customer='"+customer+"'"; 
				
			
    	List<Object[]> objects = null;
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
	public List<Object[]> subpiemonth(String gateWay,long locid) {
		// TODO Auto-generated method stub
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(gateWay);
		dashboardDBConnection dbc = null;
		String query="";
		
		query ="select a.alertvalue,d.deviceName,d.locationName from  (select sum(pw.alertValue) as alertvalue,pw.did as did,pw.alertTime as alertTime from powerinfo as pw where MONTH(pw.alertTime)= MONTH(CURDATE()-INTERVAL 1 MONTH)group by pw.did)as a left join (select did,deviceName,locationName,locationId,customer,gateway from devicedetails )as d on a.did=d.did" +
		" where d.locationId='"+locid+"'"+" and  d.customer='"+customer+"'"; 
				
			
    	List<Object[]> objects = null;
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}

	public List<Object[]> barchartmonthly(String gateWay) {
		// TODO Auto-generated method stub
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(gateWay);
		dashboardDBConnection dbc = null;
		String query="";
		
		query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customer+"'"+" and MONTH(pw.alertTime)= MONTH(CURDATE())"; 
		
		//LogUtil.info("bar"+query);
		
		List<Object[]> objects =new ArrayList<Object[]>();
    	List<Object[]> firstmon=null;
    	List<Object[]> secondmon=null;
    	List<Object[]> thirdmon=null;
    	List<Object[]> fourthmon=null;
    	List<Object[]> fifthmon=null;
    	List<Object[]> sixthmon=null;
    	
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			firstmon=q.list();	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
		query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customer+"'"+" and MONTH(pw.alertTime)= MONTH(CURDATE()-INTERVAL 1 MONTH)"; 
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			secondmon=q.list();	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customer+"'"+" and MONTH(pw.alertTime)= MONTH(CURDATE()-INTERVAL 2 MONTH)"; 
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			thirdmon=q.list();	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customer+"'"+" and MONTH(pw.alertTime)= MONTH(CURDATE()-INTERVAL 3 MONTH)"; 
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			fourthmon=q.list();	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customer+"'"+" and MONTH(pw.alertTime)= MONTH(CURDATE()-INTERVAL 4 MONTH)"; 
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			fifthmon=q.list();	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customer+"'"+" and MONTH(pw.alertTime)= MONTH(CURDATE()-INTERVAL 5 MONTH)"; 
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			sixthmon=q.list();	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		objects.addAll(firstmon);
		objects.addAll(secondmon);
		objects.addAll(thirdmon);
		objects.addAll(fourthmon);
		objects.addAll(fifthmon);
		objects.addAll(sixthmon);
		
		
		
		return objects;
	}	
	public List<Object[]> barchartthreemonthly(String customerid) {
		// TODO Auto-generated method stub
		XStream xStream = new XStream();
		long customer=(Long) xStream.fromXML(customerid);
		dashboardDBConnection dbc = null;
		String query="";
		
		query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customer+"'"+" and MONTH(pw.alertTime)= MONTH(CURDATE())"; 
		
		//LogUtil.info("bar"+query);
		
		List<Object[]> objects =new ArrayList<Object[]>();
    	List<Object[]> firstmon=null;
    	List<Object[]> secondmon=null;
    	List<Object[]> thirdmon=null;
    	
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			firstmon=q.list();	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
		query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customer+"'"+" and MONTH(pw.alertTime)= MONTH(CURDATE()-INTERVAL 1 MONTH)"; 
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			secondmon=q.list();	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customer+"'"+" and MONTH(pw.alertTime)= MONTH(CURDATE()-INTERVAL 2 MONTH)"; 
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			thirdmon=q.list();	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		objects.addAll(firstmon);
		objects.addAll(secondmon);
		objects.addAll(thirdmon);
		
		return objects;
	}	
	
	public List<Object[]> overtimeusage(long customerid, String time) {
		
		// TODO Auto-generated method stub
		XStream xStream = new XStream();
		dashboardDBConnection dbc = null;
		String query="";
		
		if((time == "1") || (time.equals("1"))){
			
			query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customerid+"'"+" and pw.alertTime BETWEEN CURDATE() - INTERVAL 2 MONTH AND CURDATE() group by MONTH(pw.alertTime);"; 
		}else if((time == "2") || (time.equals("2"))){
			
			query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customerid+"'"+" and pw.alertTime BETWEEN CURDATE() - INTERVAL 5 MONTH AND CURDATE() group by MONTH(pw.alertTime);"; 
		}else{
			
			query ="select round(sum(pw.alertValue)),pw.alertTime from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.customer='"+customerid+"'"+" and YEAR(pw.alertTime)=YEAR(CURDATE()) group by MONTH(pw.alertTime)"; 
		}
			
		
		List<Object[]> objects = null;
    	    	
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects=q.list();	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
		
}
	
	@SuppressWarnings("unchecked")
	public List<Powerinformation> listYearlyComparativeData(String location) {

		List<Powerinformation> PowerInformation = new ArrayList<Powerinformation>();
		
		dashboardDBConnection dbc = null;
		
		
		String query = "select dd.locationName,round(sum(pw.alertValue)),unix_timestamp(pw.alertTime)*1000 from powerinfo as pw left join devicedetails as dd on pw.did=dd.did where dd.locationName='"+location+"' and YEAR(pw.alertTime)=YEAR(CURDATE()) group by MONTH(pw.alertTime);";
		               
		


		
		List<Object[]> objects = null;
		
		try {
			dbc = new dashboardDBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = q.list();
			dbc.closeConnection();
			for (Object[] value : objects) 
			{
				
				Powerinformation pw=new Powerinformation();
				pw.setAlertTime(IMonitorUtil.convertToString(value[2]));
				pw.setAlertValue(IMonitorUtil.convertToString(value[1]));
				PowerInformation.add(pw);
				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
	   
		return PowerInformation;

	}
	
	
	public List<DeviceDetails> getdashboardLocationOfCustomer(Customer customer) {

		dashboardDBConnection dashboard = null;
		XStream xStream=new XStream();
		List<DeviceDetails> deviceDetails = new ArrayList<DeviceDetails>();
		String query = "select "
				+ " dd.id,dd.did,dd.deviceName,dd.deviceIcon,dd.gateway,dd.locationName,dd.enableList,dd.deviceType,dd.alertParam,dd.customer,dd.locationId "
				+ " from devicedetails as dd"
				+ " where dd.customer='"
				+ customer.getId() + "' group by dd.locationName";
				
		
		List<Object[]> objects = null;
		@SuppressWarnings("unused")
		List<Object[]> targetcost = null;
		 //LogUtil.info("query----"+query);
		try {
			dashboard = new dashboardDBConnection();
			Session session = dashboard.openConnection();
			Query q = session.createSQLQuery(query);
			objects = q.list();
			dashboard.closeConnection();
			for (Object[] strings : objects)
			{
			
				
			DeviceDetails devicedetails=new DeviceDetails();
			devicedetails.setId(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
		    devicedetails.setDid(IMonitorUtil.convertToString(strings[1]));
		    devicedetails.setDeviceName(IMonitorUtil.convertToString(strings[2]));
		    devicedetails.setDeviceIcon(IMonitorUtil.convertToString(strings[3]));
		    devicedetails.setGateway(IMonitorUtil.convertToString(strings[4]));
		    devicedetails.setLocationName(IMonitorUtil.convertToString(strings[5]));
		    devicedetails.setLocationId(Long.parseLong(IMonitorUtil.convertToString(strings[10])));
		    devicedetails.setEnableList(Long.parseLong(IMonitorUtil.convertToString(strings[6])));
		    devicedetails.setDeviceType(IMonitorUtil.convertToString(strings[7]));
		    devicedetails.setAlertParam(IMonitorUtil.convertToString(strings[8]));
		    devicedetails.setCustomer(IMonitorUtil.convertToString(strings[9]));
		   long deviceid=Long.parseLong(IMonitorUtil.convertToString(strings[1]));
		   
		   ImvgAlertsActionsManager ImvgAlertsActionsManager=new ImvgAlertsActionsManager();
		    Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(deviceid);
		    if(objects2 != null && objects2[2]!= null)
			{
			
		    	devicedetails.setHMDpowerinfo(""+(String)objects2[2]);
			}
			else
			{
				devicedetails.setHMDpowerinfo("");
			
			}
		    deviceDetails.add(devicedetails);
	
			}
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("Catch clause caught : {0} \n"+ e.getMessage());
		} finally {
			if (null != dashboard) {
				dashboard.closeConnection();
			}
		}
		return deviceDetails;
	}

}


