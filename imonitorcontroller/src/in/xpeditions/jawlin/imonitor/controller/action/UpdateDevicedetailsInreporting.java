package in.xpeditions.jawlin.imonitor.controller.action;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Powerinformation;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DashboardManager;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.dao.util.dashboardDBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class UpdateDevicedetailsInreporting implements Job {
  @SuppressWarnings("unchecked")
  public void execute(JobExecutionContext arg0) throws JobExecutionException{
	// LogUtil.info("UpdateDevicedetailsInreporting");
		  
	  String query= "select d.id,d.friendlyName,i.fileName," +//(2)
		  		"d.gateWay,l.name,d.location,d.enableList,dt.name,d.lastAlert,g.customer " +//(9)
		  		"from  device as d join icon as i " +
		  		"on d.icon=i.id join location as l on d.location=l.id  " +
		  		"join gateway as g on d.gateWay=g.id " +
		  		"join devicetype as dt on d.deviceType=dt.id " +
		  		"where d.deviceId!='HOME'and d.deviceId!='AWAY' and d.deviceId!='STAY' " +
		  		"and d.modelNumber='HMD'";
		  
		  
		
		DBConnection dbc = null;
		dashboardDBConnection DashBoard = null;
		List<Object[]> objects = null;
		
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			objects = q.list();
			dbc.closeConnection();
			
		//	XStream xStream=new XStream();
			
			
			
			for (Object[] strings : objects) 
			{

				DeviceDetails devicedetails=new DeviceDetails();
				
				devicedetails.setDid(IMonitorUtil.convertToString(strings[0]));
				devicedetails.setDeviceName(IMonitorUtil.convertToString(strings[1]));
				devicedetails.setDeviceIcon(IMonitorUtil.convertToString(strings[2]));
				devicedetails.setGateway(IMonitorUtil.convertToString(strings[3]));
				devicedetails.setLocationName(IMonitorUtil.convertToString(strings[4]));
				devicedetails.setLocationId(Long.parseLong(IMonitorUtil.convertToString(strings[5])));
				devicedetails.setEnableList(Long.parseLong(IMonitorUtil.convertToString(strings[6])));
				devicedetails.setDeviceType(IMonitorUtil.convertToString(strings[7]));
				devicedetails.setAlertParam(IMonitorUtil.convertToString(strings[8]));
				devicedetails.setCustomer(IMonitorUtil.convertToString(strings[9]));
				
				
			
				
				
				query = "select d from DeviceDetails as d where d.did='"+devicedetails.getDid()+"'";
					
					
					
				
					try {
						DashBoard = new dashboardDBConnection();
						Session sess1 = DashBoard.openConnection();
						q = sess1.createQuery(query);
						DeviceDetails devicedetailsFromDb = (DeviceDetails)q.uniqueResult();
						if(devicedetailsFromDb != null)
						{
							
							
							devicedetailsFromDb.setAlertParam(devicedetails.getAlertParam());
							devicedetailsFromDb.setDeviceName(devicedetails.getDeviceName());
							devicedetailsFromDb.setDeviceIcon(devicedetails.getDeviceIcon());
							devicedetailsFromDb.setDeviceType(devicedetails.getDeviceType());
							devicedetailsFromDb.setCustomer(devicedetails.getCustomer());
							devicedetailsFromDb.setLocationName(devicedetails.getLocationName());
							devicedetailsFromDb.setLocationId(devicedetails.getLocationId());
							devicedetailsFromDb.setDid(devicedetails.getDid());
							devicedetailsFromDb.setEnableList(devicedetails.getEnableList());
							devicedetailsFromDb.setGateway(devicedetails.getGateway());
							devicedetailsFromDb.setId(devicedetailsFromDb.getId());
						
							sess1.update(devicedetailsFromDb);
								
						}
						else
						{
					
						
							sess1.save(devicedetails);
							
						}
					
						Transaction tx = sess1.beginTransaction();
						tx.commit();
						DashBoard.closeConnection();
						
						
					}
					catch (Exception e) 
					{
						e.printStackTrace();
						LogUtil.info("DashBoard Connections"+e.getMessage());
					}
						
					finally
					{
						DashBoard.closeConnection();
						
					}
					
				}
			
			
			
				
			
				
					
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("Catch clause every 10 sec caught\n"+ e.getMessage());
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
	  }
	}
