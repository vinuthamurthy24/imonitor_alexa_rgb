package in.xpeditions.jawlin.imonitor.controller.action;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.communication.AlertNotiifier;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Powerinformation;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.TarrifConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.dashboardconfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.targetcost;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DashboardManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ImvgAlertNotificationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.RuleManager;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.dao.util.dashboardDBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.EMailNotifications;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class sampleaction implements Job {
  @SuppressWarnings({ "unchecked", "null" })
public void execute(JobExecutionContext arg0) throws JobExecutionException{
	  XStream xStream=new XStream();
	
	  
	  String query= "select d.id,d.friendlyName,i.fileName," +//(2)
	  		"d.gateWay,l.name,d.location,d.enableList,dt.name as devicetype,d.lastAlert,g.customer " +//(9)
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
	List<Object[]> objects1 = null;
	long custid;
	Object[] last;
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
					//LogUtil.info("devicedetailsFromDb----- "+xStream.toXML(devicedetailsFromDb));
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
	
	query= "select sum(pw.alertValue) as alertValue,pw.device,pw.alertTime,pw.alertType,g.customer from powerInformationFromImvg as pw join device as d on pw.device=d.id join gateway as g on d.gateWay=g.id where g.customer="+custId+" and alertTime >= date_sub(NOW(), interval 1 hour) group by device";
	
	
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
			/*if(stringss[0]!=null){*/LogUtil.info("device is not zero"+stringss[0]);
		    long divisionvalue=(60/Alertgenerationtimeinsec)*60;
		  	double averagealertvalue=((Number) stringss[0]).doubleValue();
			averagealertvalue=averagealertvalue/divisionvalue;
			avgalertvalue=Double.toString(averagealertvalue);
			/*}else{
				LogUtil.info("NO value for device: "+ stringss[1] );
		    	avgalertvalue = "0.0";
			}*/
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
	
			//Calendar cal = Calendar.getInstance();
	        //cal.add(Calendar.HOUR_OF_DAY, -1);
	        //LogUtil.info("dateeeeeeeeeee"+dateFormat.format(cal.getTime()));
			//LogUtil.info("avgalertvalue----"+avgalertvalue);
			Powerinformation Powerinformation=new Powerinformation();
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
	
	 //Naveen commented on 02 Jan 2018
	
	// query="delete from powerInformationFromImvg where alertTime <=now()";
	 query="delete from powerInformationFromImvg where alertTime <= DATE_SUB(NOW(), INTERVAL 1 MONTH)";
		//LogUtil.info(" power info "+query);
		try{
			dbc = new DBConnection();
			Session sessdelete = dbc.openConnection();
			Query q = sessdelete.createSQLQuery(query);
			Transaction tx = sessdelete.beginTransaction();
			q.executeUpdate();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("Catch clause  sessdelete info caught\n"+ e.getMessage());
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}	
		
	 
	//=========================================for persentage usage alert================
	
	
	query = "select * from customer";
	
	
	
	long totalcost=0;
	long persentageusage=0;
	long gatewayid = 0;
	String macId="";
	// LogUtil.info("query----"+query);
	 try{
		 	dbc = new DBConnection();
			Session sess = dbc.openConnection();
			Query q = sess.createSQLQuery(query);
			objects = q.list();
			dbc.closeConnection();
			for (Object[] strings : objects) 
			{
				
				Customer customer=new Customer();
				GateWay gateway=new GateWay();
				long id=(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
				String customerId=IMonitorUtil.convertToString(strings[1]);
				customer.setCustomerId(customerId);
				customer.setId(id);
				
			
			
			
				
				Object kwPerUnit=null;
				List<Object[]> slabrange = null;
				long totalUnit=0;
				long Unitvalue=100;
				
					query = "select * from tariffconfiguration where customer="+customer.getId() +"";
					
					try {
						DashBoard = new dashboardDBConnection();	
						Session session = DashBoard.openConnection();
						q = session.createSQLQuery(query);
					slabrange= q.list();
					
					
					query = "select kwPerUnit from targetcost where customer="+customer.getId() +"";
					try {
						DashBoard = new dashboardDBConnection();	
						Session sessionkw = DashBoard.openConnection();
					    q = sessionkw.createSQLQuery(query);
					    kwPerUnit = q.uniqueResult();
				
					   
					
					
					if(kwPerUnit != null){
						
						 Unitvalue= ((Number) kwPerUnit).longValue();
					
					}
					else
					{
						
						Unitvalue=100;
					}
					}catch (Exception e) {
						e.printStackTrace();
						LogUtil.info("kwPerUnitt catch\n"+ e.getMessage());
					} finally {
						if (null != DashBoard) {
							DashBoard.closeConnection();
						         }
					          }
					
					
					long totaluptodatecost=0;
					if(!slabrange.isEmpty())
					{
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
							"month(pw.alertTime)=(MONTH(CURDATE())) and hour(alertTime)<="+tariffconfig.getEndHour()+" and hour(alertTime)>="+tariffconfig.getStartHour()+"";
					
					
					
					double uptodateusage=0;
					double treshouldlevel=0;
					
					// LogUtil.info("query----"+query);
					
					try {
						DashBoard = new dashboardDBConnection();
					    Session sessionsum = DashBoard.openConnection();
						 q = sessionsum.createSQLQuery(query);
						Object[] objectsusage = (Object[])q.uniqueResult();
						if(objectsusage != null){

							uptodateusage=((Number) objectsusage[0]).doubleValue();
							uptodateusage=uptodateusage/1000;
					
						}
					
						
						 totalUnit=(long) (uptodateusage/Unitvalue);
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
				//	LogUtil.info("totalcost----"+totalcost);
					}
					else
					{
						//LogUtil.info("null value in slabrange \n");	
					}
					
					}catch (Exception e) {
						e.printStackTrace();
						LogUtil.info(" tariffconfiguration catch\n"+ e.getMessage());
					} finally {
						if (null != DashBoard) {
							DashBoard.closeConnection();
						         }
					          }
					
					
					
					//LogUtil.info("totalcost--"+totalcost+ "id---"+id);

					
					query = "select g from GateWay as g where g.customer="+id+"";
					//LogUtil.info("query---"+query);
					try{
					 	dbc = new DBConnection();
						Session ses = dbc.openConnection();
						q = ses.createQuery(query);
						 gateway = (GateWay)q.uniqueResult();
						
						dbc.closeConnection();
						if(gateway != null){
							
							
							gateway.setId(gateway.getId());
							 gateway.setAgent(gateway.getAgent());
							 gateway.setAlertDevice(gateway.getAlertDevice());
							 gateway.setCustomer(customer);
							 gateway.setMacId(gateway.getMacId());
							 gateway.setDevices(gateway.getDevices());
						
						

					//query = "select comparatorName,device,rule from devicealert where device=(select id from device where deviceId='ENERGY_MONITOR' and gateWay="+gateway.getId()+")";
					
					//LogUtil.info("beforeeeeeeeeeeeeeee"+gateway.getId());
					query = "select dl.comparatorName,dl.device,dl.rule,at.details from devicealert as dl,alerttype as at where dl.device=(select id from device where deviceId='ENERGY_MONITOR' and gateWay="+gateway.getId()+") and at.id=dl.alertType";
					
					//LogUtil.info("query---"+query);
					try{
						dbc = new DBConnection();
						Session sess1 = dbc.openConnection();
						q = sess1.createSQLQuery(query);
						objects = q.list();
						dbc.closeConnection();
                  // LogUtil.info("objects---------"+xStream.toXML(objects));
                   if(!objects.isEmpty())
                   {
						for (Object[] enterdthreshouldlevel : objects) 
						{
							
							Device devices=new Device();
							String treshouldlevelentryfromuser=IMonitorUtil.convertToString(enterdthreshouldlevel[0]);
							long device=Long.parseLong(IMonitorUtil.convertToString(enterdthreshouldlevel[1]));
							long rule=Long.parseLong(IMonitorUtil.convertToString(enterdthreshouldlevel[2]));
							String alerttype=IMonitorUtil.convertToString(enterdthreshouldlevel[3]);
							
							devices.setId(device);
							devices.setDeviceId("ENERGY_MONITOR");
							devices.setGateWay(gateway);
							String[] temp = treshouldlevelentryfromuser.split("-");
						    long threshouldlevel=Long.parseLong(temp[1]);
						  
						  

						   // ============================ to get target cost ==============
targetcost targetcost=new targetcost();
 query = "select t.id,t.targetCost,t.customer,t.kwPerUnit,t.ReachedPowerlimit,t.WarningPowerlimit" +
		" from targetcost as t where " +
		"t.customer="+customer.getId()+"";
//LogUtil.info("query-----"+query);
try {
	DashBoard = new dashboardDBConnection();
	Session session11 = DashBoard.openConnection();
	 q = session11.createQuery(query);
	 
	Object[] objectstargetcost = (Object[])q.uniqueResult();
//	LogUtil.info("objectstargetcost-----"+xStream.toXML(objectstargetcost));
	if(objects != null){
	//	LogUtil.info("gghhhhhhhhh");
		long tid=Long.parseLong(IMonitorUtil.convertToString(objectstargetcost[0]));
		String tcost=IMonitorUtil.convertToString(objectstargetcost[1]);
		long customerid=Long.parseLong(IMonitorUtil.convertToString(objectstargetcost[2]));
		long kwPerUnitt=Long.parseLong(IMonitorUtil.convertToString(objectstargetcost[3]));
		long reachedpowerlimit=Long.parseLong(IMonitorUtil.convertToString(objectstargetcost[4]));
		long warningpowerlimit=Long.parseLong(IMonitorUtil.convertToString(objectstargetcost[5]));
		targetcost.setTargetCost(tcost);
		targetcost.setId(tid);
		targetcost.setCustomer(customerid);
		targetcost.setKwPerUnit(kwPerUnitt);
		targetcost.setWarningPowerlimit(warningpowerlimit);
		targetcost.setReachedPowerlimit(reachedpowerlimit);
		String[] temp1 = tcost.split(",");
		int month=Calendar.getInstance().get(Calendar.MONTH);
		//month=month-1;
		
	    long targetcostforthismonth=Long.parseLong(temp1[month]);
	   
	    persentageusage=(totalcost*100)/targetcostforthismonth;
	   
	    List<Object> data = new ArrayList<Object>(25);
	    data.add(persentageusage);
	    data.add(alerttype);
	    data.add(threshouldlevel);
	   // LogUtil.info("alerttype========"+alerttype);
	    long limit=0;
	    if(alerttype.equalsIgnoreCase("PWR_LMT_REACHED"))
	    {
	    	//LogUtil.info("insideeeeee PWR_LMT_REACHED eeeeeeeee");
	     limit=targetcost.getReachedPowerlimit();
	   //  LogUtil.info("upto==inside reached======"+limit);
	    }
	    else if(alerttype.equalsIgnoreCase("PWR_LMT_WARNING"))
	    {
	    	//LogUtil.info("insideeeeee PWR_LMT_WARNING eeeeeeeee");
	      limit=targetcost.getWarningPowerlimit();
	      //LogUtil.info("upto==inside======"+limit);
	    }
	   // LogUtil.info("upto====out===="+limit);
	 //  LogUtil.info("threshouldlevel--"+threshouldlevel);
	    if(threshouldlevel <= persentageusage && limit != threshouldlevel)
	    {
	    	GatewayManager gatewayManager = new GatewayManager();
			String macid =devices.getGateWay().getMacId();
			GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(macid);
	    	devices.setGateWay(gateWay);
	    	ActionModel actionModel = new ActionModel(devices,data);
	    	ImonitorControllerAction DashboardAlertForReachedLimitAction=new DashboardAlertForReachedLimitAction();	
	    	DashboardAlertForReachedLimitAction.executeCommand(actionModel);
	    	
	    	Date date= new Date();
	    	SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				date = (Date) time.parse(date.toString());
			} catch (ParseException e) 
			{
				LogUtil.error("");
			}
			
			DeviceManager devicemanager=new DeviceManager();
			devices=devicemanager.getDevice(devices.getId());
			Rule referenceRule = new RuleManager().retrieveRuleDetails((rule));
			
			List<ImvgAlertNotification> userNotificationProfiles=new ArrayList<ImvgAlertNotification>(referenceRule.getImvgAlertNotifications());
			
			for (ImvgAlertNotification imvgAlertNotification : userNotificationProfiles) 
			{
				UserNotificationProfile userNotificationProfile = imvgAlertNotification.getUserNotificationProfile();
				String notificationName = userNotificationProfile.getActionType()
						.getName();
				String[] receipients = new String[] { userNotificationProfile
						.getRecipient() };
				String messageToRecipient = "";
				messageToRecipient += "Rule Name: '"+IMonitorUtil.convertUnicodeEscape(IMonitorUtil.unicodeEscape(referenceRule.getName()))+"'. Description: ";
				messageToRecipient += IMonitorUtil.convertUnicodeEscape(IMonitorUtil.unicodeEscape(referenceRule.getDescription()))+".\n";
				messageToRecipient +="Up to date Usage reached "+ persentageusage +"% of Target cost in this Month.";
				try 
				{
					IMonitorUtil.notifyRecipients(notificationName, messageToRecipient, receipients, date, userNotificationProfile.getName(),referenceRule.getGateWay().getCustomer());
				} catch (IllegalArgumentException e) {
					
				} catch (IllegalAccessException e) {
					
				} catch (InvocationTargetException e) {
					
				} catch (InstantiationException e) {
					
				}
			}  
								    }
								}
								
							} catch (Exception ex) {
								ex.printStackTrace();
								LogUtil.info("Catch clause target cost caught\n"+ ex.getMessage());
							} finally {
								DashBoard.closeConnection();
							}
						// LogUtil.info("finished rule");   
			}
                   }
                   else
                   {
                	  // LogUtil.info("get comparater name and rule ");   
                   }
			
	
			    //============================get target cost===============
	
		}catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("Catch clause get comparater name and rule caught\n"+ e.getMessage());
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
					}
						else
						{
						//	LogUtil.info("gateway is null");	
						}
		
					}catch (Exception e) {
						e.printStackTrace();
						LogUtil.info("Catch clause to get gateway and macid caught\n"+ e.getMessage());
					} finally {
						if (null != dbc) {
							dbc.closeConnection();
						}
					}
				
	//=====================================usage alert===========================
  
     }
			
			
			
    }catch (Exception e) {
		e.printStackTrace();
		LogUtil.info("Catch clause  power info last caught\n"+ e.getMessage());
	} finally {
		if (null != DashBoard) {
			DashBoard.closeConnection();
		         }
	          }
  }
}

//insert into averagepowerinformation(deviceId,deviceName,deviceIcon,gateway,locationName,enableList,deviceType,alertParam,customer,avgalertvalue)select d.deviceId,d.friendlyName,i.fileName,d.gateWay,l.name,d.enableList,dt.name,d.lastAlert,g.customer,avg(a.alertValue) from imonitor3.alertsfromimvg as a  join imonitor3.device as d on a.device=d.id join icon as i on d.icon=i.id join imonitor3.location as l on d.location=l.id  join imonitor3.gateway as g on d.gateWay=g.id join imonitor3.devicetype as dt on d.deviceType=dt.id group by a.device";

