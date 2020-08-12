package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.EnergyInformation;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Powerinformation;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.TarrifConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.dashboardconfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.targetcost;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.RuleManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.dao.util.dashboardDBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.thoughtworks.xstream.XStream;

public class EnergyConsumptionAction implements Job{
	
	 @SuppressWarnings({ "unchecked", "null" })
	 public void execute(JobExecutionContext arg0) throws JobExecutionException{
	 	  XStream xStream=new XStream();
	 	
	 	  
	 	  String query= "select d.id,d.friendlyName,i.fileName," +//(2)
	 	  		"d.gateWay,l.name,d.location,d.enableList,dt.name,d.lastAlert,g.customer " +//(9)
	 	  		"from  device as d join icon as i " +
	 	  		"on d.icon=i.id join location as l on d.location=l.id  " +
	 	  		"join gateway as g on d.gateWay=g.id " +
	 	  		"join devicetype as dt on d.deviceType=dt.id " +
	 	  		"where d.deviceId!='HOME'and d.deviceId!='AWAY' and d.deviceId!='STAY' " +
	 	  		"and d.modelNumber='HMD'";
	 	  
	 	  
	 	//LogUtil.info("query-----"+query);
	 	DBConnection dbc = null;
	 	dashboardDBConnection DashBoard = null;
	 	List<Object[]> objects = null;
	 	List<Object[]> objects2 = null;
	 	try {
	 		dbc = new DBConnection();
	 		Session session = dbc.openConnection();
	 		Query q = session.createSQLQuery(query);
	 		objects = q.list();
	 		dbc.closeConnection();
	 		
	 		
	 		
	 		for (Object[] strings : objects) 
	 		{
	 			
	 		
	 			DeviceDetails devicedetails=new DeviceDetails();
	 			devicedetails.setDeviceName(IMonitorUtil.convertToString(strings[1]));
	 			devicedetails.setDeviceIcon(IMonitorUtil.convertToString(strings[2]));
	 			devicedetails.setGateway(IMonitorUtil.convertToString(strings[3]));
	 			devicedetails.setLocationName(IMonitorUtil.convertToString(strings[4]));
	 			devicedetails.setEnableList(Long.parseLong(IMonitorUtil.convertToString(strings[6])));
	 			devicedetails.setDeviceType(IMonitorUtil.convertToString(strings[7]));
	 			devicedetails.setAlertParam(IMonitorUtil.convertToString(strings[8]));
	 			devicedetails.setCustomer(IMonitorUtil.convertToString(strings[9]));
	 			devicedetails.setDid(IMonitorUtil.convertToString(strings[0]));
	 			devicedetails.setLocationId(Long.parseLong(IMonitorUtil.convertToString(strings[5])));
	 			
	 		
	 			
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
	 				
	 						//LogUtil.info(" Null !!!");
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
	 				if (null != DashBoard) {
	 					DashBoard.closeConnection();
	 				}
	 				}
	 				
	 			}
	 		
	 		
	 		/*query= " select a.alertValue,a.device from " +
	 		 		"(select avg(alertValue) as alertValue,device,alertTime " +
	 		 		"from alertsfromimvg where alertTime >= date_sub(NOW(), interval 1 hour)" +
	 		 		" group by device) as a,(select id from device " +
	 		 		"where modelNumber='HMD') as d " +
	 		 		"where d.id=a.device group by a.device";*/
	 		
	 	
	 				
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		LogUtil.info("Catch clause caught\n"+ e.getMessage());
	 	} finally {
	 		if (null != dbc) {
	 			dbc.closeConnection();
	 		}
	 	}
	 	
	 	
	 	
	 	

	 	query = "select * from customer";
	 	
	 	
	 	
	 	
	 	// LogUtil.info("query----"+query);
	 	 try{
	 		 	dbc = new DBConnection();
	 			Session sess = dbc.openConnection();
	 			Query q = sess.createSQLQuery(query);
	 			objects = q.list();
	 			dbc.closeConnection();
	 			for (Object[] strings : objects) 
	 			{
	 				long custId=(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
	 	//copy 1 hour average alert value to another table
	 	
	 	query= "select sum(pw.alertValue) as alertValue,pw.device,pw.alertTime,pw.alertType,g.customer from powerInformationFromImvg as pw join device as d on pw.device=d.id join gateway as g on d.gateWay=g.id where g.customer="+custId+" and alertTime >= date_sub(NOW(), interval 15 minute) group by device";
	 	
	 	
	 	//LogUtil.info("query-----"+query);
	 		
	 	try{
	 	 	dbc = new DBConnection();
	 		sess = dbc.openConnection();
	 		q = sess.createSQLQuery(query);
	 		objects2 = q.list();
	 		
	 		query = "select dc from dashboardconfig as dc where dc.customer="+custId+"";
	 		long Alertgenerationtimeinsec=10;
	 		try {
	 			DashBoard = new dashboardDBConnection();
	 			Session sess1 = DashBoard.openConnection();
	 			 q = sess1.createQuery(query);
	 			dashboardconfig deviceFromDb = (dashboardconfig)q.uniqueResult();
	 			DashBoard.closeConnection();
	 			if(deviceFromDb!=null)
	 			{
	 				 Alertgenerationtimeinsec=deviceFromDb.getAlertgenerationtimeinsec();
	 			}
	 			else
	 			{
	 				Alertgenerationtimeinsec=10;	
	 			}
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 			LogUtil.info("Catch clause  Alertgenerationtimeinsec power info caught\n"+ e.getMessage());
	 		} finally {
	 			if (null != DashBoard) {
	 				DashBoard.closeConnection();
	 			}
	 		
	 		}

	 		
	 		
	 	//LogUtil.info("objects2 values "+xStream.toXML(objects2));	
	 	if(objects2 !=null)
	 	{
	 		for (Object[] stringss : objects2) 
	 		{	String avgalertvalue=null;
	 			/*if(stringss[0]!=null){*/
	 		    //LogUtil.info("device is not zero"+stringss[0]);
	 		    long divisionvalue=(60/Alertgenerationtimeinsec)*60;
	 		  	double averagealertvalue=((Number) stringss[0]).doubleValue();
	 			averagealertvalue=averagealertvalue/90;
	 			avgalertvalue=Double.toString(averagealertvalue);
	 			/*}else{
	 				LogUtil.info("NO value for device: "+ stringss[1] );
	 		    	avgalertvalue = "0.0";
	 			}*/
	 			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	 			Date date = new Date();
	 			EnergyInformation Powerinformation=new EnergyInformation();
	 			Powerinformation.setAlertValue(avgalertvalue);
	 			Powerinformation.setDid(IMonitorUtil.convertToString(stringss[1]));
	 			Powerinformation.setAlertTime(dateFormat.format(date));

	 			
	 			
	 			try {
	 				DashBoard = new dashboardDBConnection();
	 				Session powsess = DashBoard.openConnection();
	 				powsess.save(Powerinformation);
	 				Transaction tx = powsess.beginTransaction();
	 				tx.commit();
	 				
	 			} catch (Exception e) {
	 				e.printStackTrace();
	 				LogUtil.info("Catch clause  power info caught\n"+ e.getMessage());
	 			} finally {
	 				if (null != DashBoard) {
	 					DashBoard.closeConnection();
	 				}
	 			
	 			}

	 			
	 		}
	 	}
	 	else
	 	{
	 		LogUtil.info("Null value is there");	
	 	}
	 	}catch (Exception e) {
	 		e.printStackTrace();
	 		LogUtil.info("Catch clause Null value power info caught\n"+ e.getMessage());
	 	} finally {
	 		
	 		dbc.closeConnection();
	 		
	 	}
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	//end 1 hour 
	 			}
	 			
	 	 }catch (Exception e) {
	 			e.printStackTrace();
	 			LogUtil.info("Catch clause customer list caught\n"+ e.getMessage());
	 		} finally {
	 			
	 			dbc.closeConnection();
	 			
	 		}
	 	
	 	
	 	 
	 	//=========================================for persentage usage alert================
	  	 
	 	//Naveen start
	 	 //For tpddl
	 	 query = "select * from customer where customerId !='admin'";
	 	 
	 	 try {
	 		    dbc = new DBConnection();
	 			Session sess = dbc.openConnection();
	 			Query q = sess.createSQLQuery(query);
	 			objects = q.list();
	 			dbc.closeConnection();
	 			
	 			for (Object[] strings : objects) 
	 			{
	 				Customer customer=new Customer();
	 				//GateWay gateway=new GateWay();
	 				long id=(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
	 				String customerId=IMonitorUtil.convertToString(strings[1]);
	 				customer.setCustomerId(customerId);
	 				customer.setId(id);
	 			
	 				GatewayManager gatewayManager=new GatewayManager();
	 				//LogUtil.info(" customer info : " + xStream.toXML(customer));
	 				GateWay gateWay2=gatewayManager.getGateWayByCustomer(customer);
	 				//LogUtil.info(" gateway info : " + xStream.toXML(gateWay2));
	 				if(gateWay2 == null){
	 					
	 					continue;
	 					
	 				}
	 				String remarks = gateWay2.getRemarks();
	 				
	 				if ((remarks != null ) && (gateWay2.getRemarks().contains("TPDDL"))) 
	 				{
	 					
	 					query="select pw.did,pw.alertValue,pw.alertTime,d.deviceName,d.deviceType,d.gateway from " +
	 				 			" (select did,customer,deviceName,deviceType,gateway from devicedetails) as d," +
	 				 			"(select alertValue,did,alertTime from energyInformation) as pw " +
	 				 			"where d.did=pw.did and pw.alertTime >= date_sub(NOW(), interval 10 minute) and d.customer="+customer.getId();
	 					
	 					
	 					List<Object[]> objectsfromDb = null;
	 					
	 					try{
	 						
	 						DashBoard = new dashboardDBConnection();
	 						Session session = DashBoard.openConnection();
	 						Query q1 = session.createSQLQuery(query);
	 						objectsfromDb = q1.list();
	 						
	 						//LogUtil.info("String xml is :"+customer.getId()+"Objects :"+xStream.toXML(objectsfromDb));
	 						if (objectsfromDb!=null)
	 						{
	 							
	 						
	 						for (Object[] pinfo : objectsfromDb)
	 						{
	 							
	 							UserManager userManager=new UserManager();
	 							User user=userManager.getUserDetailByCustomer(customer);
	 						
	 							String userId=user.getUserId();
	 							
	 							Date date=(Date)pinfo[2];
	 							DateFormat dateformat = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");
	 							 //String date = dateformat.format(datefrmDb);
	 							String AlertTime =dateformat.format(date);
	 						
	 							
	 							HttpURLConnection conn=null;
	 							try 
	 							{
	 								
	 								
	 								String url="http://www.tatapower-ddl.com/smartniwasapi/consumption/powerpush.asmx/Push_PowerValues?" +
	 										"weblogin=tpddl&webpassword=smartniwas@0000&CustmerID="+URLEncoder.encode(customer.getCustomerId(), "UTF-8")+"&UserID="+URLEncoder.encode(userId, "UTF-8")+
	 												"&DeviceName="+URLEncoder.encode(IMonitorUtil.convertToString(pinfo[3]), "UTF-8")+"&DeviceType="+URLEncoder.encode(IMonitorUtil.convertToString(pinfo[4]), "UTF-8")+
	 														"&AlertTime="+URLEncoder.encode(AlertTime, "UTF-8")+"&AlertValue="+URLEncoder.encode(IMonitorUtil.convertToString(pinfo[1]), "UTF-8");
	 								
	 								
	 								
	 								URL obj = new URL(url);
	 								
	 								conn=(HttpURLConnection) obj.openConnection();
	 								conn.setRequestMethod("GET");
	 								conn.setDoInput( true );
	 							    conn.connect();
	 								conn.getInputStream();
	 								int responseCode = conn.getResponseCode();
	 								
	 								
	 								
	 								BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	 								
	 								String inputLine;
	 								
	 								StringBuffer response = new StringBuffer();
	 							//	LogUtil.info("Get req step 6");
	 								while ((inputLine = in.readLine()) != null) {
	 									response.append(inputLine);
	 								}
	 								in.close();
	 								//LogUtil.info(response.toString());
	 								//LogUtil.info("Post req end");
	 								
	 							}
	 							catch(IOException exception)
	 							{
	 								LogUtil.info("IO Exception :"+exception.getMessage());
	 							}
	 							catch (Exception e) 
	 							{
	 								LogUtil.info("Sending error: " +e.getMessage());
	 								e.printStackTrace();
	 							}

	 						}
	 					}
	 						
	 					}catch (Exception e) {
	 						e.printStackTrace();
	 						LogUtil.info(e.getMessage());
	 					} finally {
	 						if (null != dbc) {
	 							dbc.closeConnection();
	 						}
	 					}
	 				}
	 				else {
	 					//LogUtil.info("Else Block Executed");
	 				}
	 				
	 			}
	 			
	 			
	 	} catch (Exception e) {
	 		// TODO: handle exception
	 	}
	 	 
	 	 
	 	 
	   }

}
