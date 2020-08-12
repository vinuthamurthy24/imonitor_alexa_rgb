/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertsAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UploadsByImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.dao.util.dashboardDBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.thoughtworks.xstream.XStream;

/**
 * @author computer
 *
 */
public class ImvgAlertsActionsManager {

	DaoManager daoManager=new DaoManager();
	public boolean save(ImvgAlertsAction responseToActions ){
		boolean result=false;
		try{
			daoManager.save(responseToActions);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
	return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> listAskedAlertsImvgType(int start, int length,long id) {
		
		DBConnection dbc = null;
		/*String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand from " +
        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
        " where a.device.gateWay = "+id+" group by a order by a.id desc";*/
		
String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,'dummy',a.alertValue,a.alarmStatus from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+" and a.alarmStatus>0 group by a order by a.id desc";
	
		
		List<Object[]> objects = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			q.setFirstResult(start);
			q.setMaxResults(length);
			
			objects = q.list();
			/*XStream stream=new XStream();
			LogUtil.info(stream.toXML(objects));*/
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> listAskedAlerts(int start, int length,long id) {
		
		DBConnection dbc = null;
		String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand from " +
        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
        " where a.device.gateWay = "+id+" group by a order by a.id desc";
		
/*String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+" and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc";*/
	 
		
		List<Object[]> objects = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			q.setFirstResult(start);
			q.setMaxResults(length);
			objects = q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
	//bhavya start
@SuppressWarnings("unchecked")
public Object[] listLatesestHMDAlerts(String deviceid,String alerttype1,String alerttype2,String alerttype3) {
		
		DBConnection dbc = null;
		String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id from alertsfromimvg as a,device as d,alerttype as at where d.generatedDeviceId='"+ deviceid +"' and (at.name='"+ alerttype1 +"' or at.name='"+alerttype2+"' or at.name='"+alerttype3+"') and a.alertType=at.id and d.id=a.device order by a.alertTime desc limit 1";		
		
		Object[] objects = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects = (Object[])q.uniqueResult();
	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
}
public Object[] listpowerinfo(long deviceid) {
	
	dashboardDBConnection dbc = null;
	String query = "select a.alertTime,a.id,a.alertValue,a.device,a.alertTypeName from instantpowerinformation as a where a.device="+ deviceid +" and a.alertTypeName='POWER_INFORMATION' order by a.alertTime desc limit 1";
	
	Object[] objects = null;
	try {
		dbc = new dashboardDBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createSQLQuery(query);
		objects = (Object[])q.uniqueResult();

	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}

	return objects;
}
	//bhavya end
public List<Object[]> getUploadedImage(long id,long deviceId) {
		DBConnection dbc = null;
		
		List<Object[]> objects1 =null;
		List<Object[]> objects2 =null;
		String query = " select ui.filePath,ui.id,ai.id,ai.alertTime,at.name,at.alertCommand from UploadsByImvg as ui left join ui.alertsFromImvg as ai left join ui.alertsFromImvg.alertType as at where ui.alertsFromImvg.id <"+id+" and at.alertCommand='IMAGE_FTP_SUCCESS' and ui.alertsFromImvg.device.id ="+deviceId+" and ui.filePath != 'nullnull' order by ui.id desc" ;
		
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			q.setMaxResults(10);
			 objects1 =q.list();
			 query= " select ui.filePath,ui.id,ai.id,ai.alertTime,at.name,at.alertCommand from UploadsByImvg as ui left join ui.alertsFromImvg as ai left join ui.alertsFromImvg.alertType as at where ui.alertsFromImvg.id >="+id+"and at.alertCommand='IMAGE_FTP_SUCCESS' and ui.alertsFromImvg.device.id ="+deviceId+" and ui.filePath != 'nullnull' order by ui.id desc " ;
			q = dbc.getSession().createQuery(query);
				q.setMaxResults(10);
				objects1.addAll(q.list());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		//XStream stream = new XStream();
		return objects1;
	}

public List<Object[]> getUploadedStream(long id,long deviceId) {
	DBConnection dbc = null;
	//UploadsByImvg uploadsByImvg =null;
	List<Object[]> objects1 =null;
	List<Object[]> objects2 =null;
	String query = " select ui.filePath,ui.id,ai.id,ai.alertTime,at.name,at.alertCommand from UploadsByImvg as ui left join ui.alertsFromImvg as ai left join ui.alertsFromImvg.alertType as at where ui.alertsFromImvg.id ="+id+" and at.alertCommand='STREAM_FTP_SUCCESS' and ui.alertsFromImvg.device.id ="+deviceId+" order by ui.id desc" ;
	//		String query = " select ui.id,ui.filePath from UploadsByImvg as ui where ui.alertsFromImvg="+id;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		q.setMaxResults(10);
		 objects1 =q.list();
		q = dbc.getSession().createQuery(query);
			q.setMaxResults(10);
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects1;
}
	
	public Object getCountOfImvgAlerts(long id){
		DBConnection dbc = null;
		Object count = null;
		/*String query = "select count(a) from "+
		"AlertsFromImvg as a join a.device.gateWay"+
		" where a.device.gateWay ="+id;*/
		
		String query = "select count(a) from "+
				"AlertsFromImvg as a join a.device.gateWay left join a.device as d left join a.alertType as at "+
				" where a.device.gateWay ="+id+"" +
				" and a.alarmStatus>0";
		
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			count = q.list().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> GetUserChoosenAlertsByGateWayId(long GateWayId) {
	    List<Object[]> objects = null;
		String query = "select u.alerts ,u.device from  UserChoosenAlerts as u where u.gateway='"+ GateWayId + "'";
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects=q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}

		return objects;
	}
	
@SuppressWarnings("unchecked")
public List<?> listAskedAlertsImvgTypeForAlarm(int start, int length,long id) {
		
		DBConnection dbc = null;
		
String query = "select a.id,a.alertTime,d.friendlyName,at.name,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+" group by a order by a.id desc";
		

List<Object[]> objects = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			
			objects = q.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlertsImvgTypeForAlarm(String sSearch,
		String sOrder, int getiDisplayStart, int getiDisplayLength, long id, String deviceToSearch, Timestamp from, Timestamp to) {
	
	String query=""; 
	if((deviceToSearch.equals("") || deviceToSearch == null) && (from ==null) && (to==null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ " group by a order by a.id desc";
	}
	else if((!deviceToSearch.equals("") || deviceToSearch != null) && (from == null) && (to == null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+
			        " group by a order by a.id desc";
	}
	else if((deviceToSearch.equals("") || deviceToSearch == null) && (from !=null) && (to!=null))
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ 
			       " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
			        " group by a order by a.id desc";
	}
	else
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+
		        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		        " group by a order by a.id desc";
	}
	
	DBConnection dbc = null;
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		q.setMaxResults(getiDisplayLength);
		q.setFirstResult(getiDisplayStart);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}

public int getCountofImvgAlertsByGatewayid(String sSearch, long id) {
	String query = "select count(a) from " +
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
	        " where a.device.gateWay = "+id+ " and (a.alertTime like '%"+sSearch+"%' or d.friendlyName like '%"+sSearch+"%' or at.name like '%"+sSearch+"%') ";
		Long count = new Long(0);
		DBConnection dbc = null;
        try {
        	dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			count = (Long) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != dbc) {
                dbc.closeConnection();
            }
        }
        return count.intValue();
}

public void DeleteSelectedAlerts(String AlertId) {
	
	String query = "delete from alertsfromimvg where id='"+ AlertId + "'";
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		Transaction tx = session.beginTransaction();
		q.executeUpdate();
		tx.commit();
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}

	
}

@SuppressWarnings("unchecked")
/*public List<Object[]> listAskedAlarmPerDevice(Long selectedDevice, java.util.Date fromDate, java.util.Date toDate) {
	  List<Object[]> objects = null;
			
	 /* String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand from alertsfromimvg as a,device as d,alerttype as at" +
	  		" where a.device='" + selectedDevice +"'" +
	  		" and d.id = a.device " +
	  		" and at.id = a.alertType" +
	  		" and  a.alertType in (select alerts FROM userChoosenAlerts where device = a.device )"+
	  		/*" and  a.alertTime " +
	  		" between " +fromDate+
	  		" and " +toDate+
	  		" order by a.id asc";*/
	  
	 /* String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u) from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
		        " where a.device= "+selectedDevice+" and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc";
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				q.setMaxResults(10);
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;

}*/


public List<Object[]> listAskedAlarmPerDevice(Long selectedDevice, int displaystart) {
	List<Object[]> objects = null;
	
	 /* String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand from alertsfromimvg as a,device as d,alerttype as at" +
	  		" where a.device='" + selectedDevice +"'" +
	  		" and d.id = a.device " +
	  		" and at.id = a.alertType" +
	  		" and  a.alertType in (select alerts FROM userChoosenAlerts where device = a.device )"+
	  		/*" and  a.alertTime " +
	  		" between " +fromDate+
	  		" and " +toDate+
	  		" order by a.id asc";*/
	  
	  String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u),a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
		        " where a.device= "+selectedDevice+" and a.alarmStatus=1 group by a order by a.id desc";
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				q.setFirstResult(displaystart);
				q.setMaxResults(10);
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;

}

@SuppressWarnings("unchecked")
public  List<Object[]>  getCountOflistAskedAlarmPerDevice(Long selectedDevice, Timestamp fromdate, Timestamp todate) {
	List<Object[]> objects = null;
	//vibhu added a day to 'to'
		todate.setTime(todate.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	
	 /* String query = "select a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand from alertsfromimvg as a,device as d,alerttype as at" +
	  		" where a.device='" + selectedDevice +"'" +
	  		" and d.id = a.device " +
	  		" and at.id = a.alertType" +
	  		" and a.alarmStatus=1"+
	  		" and  a.alertTime " +
	  		" between '"+fromdate+"'"+
	  		" and '" +todate+"'"+
	  		" order by a.id asc";*/
	  
	  String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device = "+selectedDevice+
		        " and a.alarmStatus=1 " +
		        " and a.alertTime between '"+ fromdate +"'"+" and '"+ todate+"'" +
		        " group by a order by a.id desc";
	
	  /*String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u) from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
		        " where a.device= "+selectedDevice+"" +
		        " and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc" +
		        " and a.alertTime between "+ from +" and "+ to +"";*/
		
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				//Query q = dbc.getSession().createSQLQuery(query);
				
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;

}


//bhavya start
@SuppressWarnings("unchecked")
public  List<Object[]>  getCountOflistAskedAlarmPerDeviceandalertType(Long selectedDevice, String alertType) {
	List<Object[]> objects = null;

	  
	  String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device = "+selectedDevice+
		        " and a.alarmStatus=1 " +
		        " and a.alertType="+ alertType +
		        " group by a order by a.id desc";
	
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;

}

//bhavya stop
@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlarmPerDevice(Long selectedDevice,
		int displaystart, java.util.Date fromdate, java.util.Date todate) {
	
	 List<Object[]> objects = null;
		
	 /* String query = "select a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand from alertsfromimvg as a,device as d,alerttype as at" +
	  		" where a.device='" + selectedDevice +"'" +
	  		" and d.id = a.device " +
	  		" and at.id = a.alertType" +
	  		" and a.alarmStatus=1"+
	  		" and  a.alertTime " +
	  		" between '"+fromdate+"'"+
	  		" and '" +todate+"'"+
	  		" order by a.id asc";*/
	  
	  String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device = "+selectedDevice+
		        " and a.alarmStatus=1 " +
		        " and a.alertTime between '"+ fromdate +"'"+" and '"+ todate+"'" +
		        " group by a order by a.id desc";
	
	  /*String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u) from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
		        " where a.device= "+selectedDevice+"" +
		        " and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc" +
		        " and a.alertTime between "+ from +" and "+ to +"";*/
		
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				//Query q = dbc.getSession().createSQLQuery(query);
				q.setFirstResult(displaystart);
				q.setMaxResults(10);
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;

}

public Object getCountAlerts(long id) {
	DBConnection dbc = null;
	Object count = null;
	String query = "select count(a) from "+
	"AlertsFromImvg as a join a.device.gateWay"+
	" where a.device.gateWay ="+id;
	
	/*String query = "select count(a) from "+
			"AlertsFromImvg as a join a.device.gateWay left join a.device as d left join a.alertType as at "+
			" where a.device.gateWay ="+id+"" +
			" and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc)";*/
	
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		count = q.list().get(0);
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return count;
	
}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlarmPerDeviceWithoutdate(Long selectedDevice,
		int displaystart) {
	List<Object[]> objects = null;
	
	 /* String query = "select a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand from alertsfromimvg as a,device as d,alerttype as at" +
	  		" where a.device='" + selectedDevice +"'" +
	  		" and d.id = a.device " +
	  		" and at.id = a.alertType" +
	  		" and a.alarmStatus=1"+
	  		" and  a.alertTime " +
	  		" between '"+fromdate+"'"+
	  		" and '" +todate+"'"+
	  		" order by a.id asc";*/
	  
	  String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device = "+selectedDevice+
		        " and a.alarmStatus=1 " +
		        " group by a order by a.id desc";
	
	  /*String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u) from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
		        " where a.device= "+selectedDevice+"" +
		        " and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc" +
		        " and a.alertTime between "+ from +" and "+ to +"";*/
		
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				
				q.setFirstResult(displaystart);
				q.setMaxResults(10);
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;

}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlarmPerDate(
		int displaystart, Timestamp from, Timestamp to) {
	List<Object[]> objects = null;
	//vibhu added a day to 'to'
		to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
		
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
	        " where a.alarmStatus=1 " +
	        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
	        " group by a order by a.id desc";

  /*String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u) from " +
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
	        " where a.device= "+selectedDevice+"" +
	        " and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc" +
	        " and a.alertTime between "+ from +" and "+ to +"";*/
	
  DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			
			//Query q = dbc.getSession().createSQLQuery(query);
			q.setFirstResult(displaystart);
			q.setMaxResults(10);
			objects=q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}

		return objects;

}

@SuppressWarnings("unchecked")
public List<Object[]> getCountOfAlarmPerDeviceWithoutdate(Long selectedDevice) {
	List<Object[]> objects = null;
	
	 /* String query = "select a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand from alertsfromimvg as a,device as d,alerttype as at" +
	  		" where a.device='" + selectedDevice +"'" +
	  		" and d.id = a.device " +
	  		" and at.id = a.alertType" +
	  		" and a.alarmStatus=1"+
	  		" and  a.alertTime " +
	  		" between '"+fromdate+"'"+
	  		" and '" +todate+"'"+
	  		" order by a.id asc";*/
	  
	  String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device = "+selectedDevice+
		        " and a.alarmStatus=1 " +
		        " group by a order by a.id desc";
	
	  /*String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u) from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
		        " where a.device= "+selectedDevice+"" +
		        " and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc" +
		        " and a.alertTime between "+ from +" and "+ to +"";*/
		
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				//Query q = dbc.getSession().createSQLQuery(query);
				
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;

}

@SuppressWarnings("unchecked")
public List<Object[]> getCountOfAlarmPerDate(Timestamp from, Timestamp to) {
	List<Object[]> objects = null;
	//vibhu added a day to 'to'
		to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
		
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
	        " where a.alarmStatus=1 " +
	        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
	        " group by a order by a.id desc";
  DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			objects=q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}

		return objects;

}

@SuppressWarnings("unchecked")
public List<?> listAskedAlerts(long id) {
	
	DBConnection dbc = null;
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
    " where a.device.gateWay = "+id+" group by a order by a.id desc";
	
/*String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id from " +
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
	        " where a.device.gateWay = "+id+" and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc";*/

	
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlarmPerDevice(long id) {
	 String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+" and a.alarmStatus=1 " +
		        " group by a order by a.id desc";
	
	  /*String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u) from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
		        " where a.device= "+selectedDevice+"" +
		        " and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc" +
		        " and a.alertTime between "+ from +" and "+ to +"";*/
	 List<Object[]> objects = null;
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				//Query q = dbc.getSession().createSQLQuery(query);
				
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;
}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlarmPerDeviceWithoutdate(Long selectedDevice) {
	List<Object[]> objects = null;
	
	 /* String query = "select a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand from alertsfromimvg as a,device as d,alerttype as at" +
	  		" where a.device='" + selectedDevice +"'" +
	  		" and d.id = a.device " +
	  		" and at.id = a.alertType" +
	  		" and a.alarmStatus=1"+
	  		" and  a.alertTime " +
	  		" between '"+fromdate+"'"+
	  		" and '" +todate+"'"+
	  		" order by a.id asc";*/
	  
	  String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device = "+selectedDevice+
		        " and a.alarmStatus=1 " +
		        " group by a order by a.id desc";
	
	  /*String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u) from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
		        " where a.device= "+selectedDevice+"" +
		        " and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc" +
		        " and a.alertTime between "+ from +" and "+ to +"";*/
		
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				//Query q = dbc.getSession().createSQLQuery(query);
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;

}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlarmPerDate(Timestamp from, Timestamp to) {
	List<Object[]> objects = null;
	//vibhu added a day to 'to'
		to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
	        " where a.alarmStatus=1 " +
	        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
	        " group by a order by a.id desc";

  /*String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u) from " +
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
	        " where a.device= "+selectedDevice+"" +
	        " and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc" +
	        " and a.alertTime between "+ from +" and "+ to +"";*/
	
  DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			
			//Query q = dbc.getSession().createSQLQuery(query);
			objects=q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}

		return objects;
}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlarmPerDevice(Long selectedDevice,
		Timestamp from, Timestamp to) {
	 List<Object[]> objects = null;
	//vibhu added a day to 'to'
		to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	 /* String query = "select a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand from alertsfromimvg as a,device as d,alerttype as at" +
	  		" where a.device='" + selectedDevice +"'" +
	  		" and d.id = a.device " +
	  		" and at.id = a.alertType" +
	  		" and a.alarmStatus=1"+
	  		" and  a.alertTime " +
	  		" between '"+fromdate+"'"+
	  		" and '" +todate+"'"+
	  		" order by a.id asc";*/
	  
	  String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device = "+selectedDevice+
		        " and a.alarmStatus=1 " +
		        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		        " group by a order by a.id desc";
	
	  /*String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u) from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
		        " where a.device= "+selectedDevice+"" +
		        " and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc" +
		        " and a.alertTime between "+ from +" and "+ to +"";*/
		
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				//Query q = dbc.getSession().createSQLQuery(query);
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;

}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlertsPerDeviceWithoutdate(Long selectedDevice,int displaystart, long id) {
	DBConnection dbc = null;
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
    " where a.device = "+selectedDevice +
    " and a.device.gateWay = "+id+" group by a order by a.id desc";
	
/*String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id from " +
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
	        " where a.device.gateWay = "+id+" and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc";*/

	
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		q.setFirstResult(displaystart);
		q.setMaxResults(10);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlertsPerDate(int displaystart, Timestamp from,
		Timestamp to, long id) {
	//vibhu added a day to 'to'
		to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	DBConnection dbc = null;
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
    " where a.device.gateWay = "+id+
    " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
    " group by a order by a.id desc";
	
/*String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id from " +
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
	        " where a.device.gateWay = "+id+" and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc";*/

	
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		q.setFirstResult(displaystart);
		q.setMaxResults(10);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}
@SuppressWarnings("unchecked")
public List<Object[]> getCountOfAlertsPerDeviceWithoutdate(long id,Long selectedDevice) {
	List<Object[]> objects = null;
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
		    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
		    "  where a.device = "+selectedDevice +
		    " and a.device.gateWay = "+id+" group by a order by a.id desc";
		
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}
			return objects;
}
@SuppressWarnings("unchecked")
public List<Object[]> getCountOfAlertsPerDate(long id,Timestamp from, Timestamp to) {
	List<Object[]> objects = null;
	//vibhu added a day to 'to'
		to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
		    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
		    " where a.device.gateWay = "+id+
		    " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		    " group by a order by a.id desc";
	
	DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			objects=q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}

		return objects;

}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlertsPerDeviceAndDate(long id,
		Long selectedDevice, Timestamp from, Timestamp to, int displaystart) {
	DBConnection dbc = null;
	//vibhu added a day to 'to'
	to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
    " where a.device.gateWay = "+id+
    " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
    " and a.device = "+selectedDevice +
    " group by a order by a.id desc";
	
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		q.setFirstResult(displaystart);
		q.setMaxResults(10);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}

@SuppressWarnings("unchecked")
public List<Object[]> getCountOfAlertsPerDeviceAndDate(long id,
		Long selectedDevice, Timestamp from, Timestamp to) {
	//vibhu added a day to 'to'
	to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
		    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
		    " where a.device.gateWay = "+id+
		    " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		    " and a.device = "+selectedDevice +
		    " group by a order by a.id desc";
			DBConnection dbc = null;
			List<Object[]> objects = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				objects = q.list();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
			return objects;
}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlamPerGateWay(long id, Timestamp timest) {
	DBConnection dbc = null;
	/*String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand from " +
    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
    " where a.device.gateWay = "+id+" group by a order by a.id desc";*/
	
String query = "select a.alertTime,d.friendlyName,at.name,'dummy',a.alertValue from " +			
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
	        " where a.device.gateWay = "+id+" and a.alarmStatus>0 and a.alertTime >'"+timest+"' group by a order by a.id desc";

/*String query = "select a.alertTime,d.friendlyName,at.name,'dummy',a.alertValue,d.generatedDeviceId from " +
        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
        " where a.device.gateWay = "+id+" and a.alarmStatus>0 and a.alertTime >'"+timest+"' group by a order by a.id desc";*/

	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		
		q.setMaxResults(10);		
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}

public Object getCountOfImvgAlerts(long id, String deviceToSearch,
		Timestamp from, Timestamp to) {
	DBConnection dbc = null;
	Object count = null;
	/*String query = "select count(a) from "+
	"AlertsFromImvg as a join a.device.gateWay"+
	" where a.device.gateWay ="+id;*/
	
	String query="";
	if((deviceToSearch.equals("") || deviceToSearch == null) && (from ==null) && (to==null) )
	{
		 query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+"";
	}
	else if((!deviceToSearch.equals("") || deviceToSearch != null) && (from == null) && (to == null) )
	{
		 query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+"" +
				" and d.friendlyName = '"+deviceToSearch+"'";
	}
	else if((deviceToSearch.equals("") || deviceToSearch == null) && (from !=null) && (to!=null))
	{
		 query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+"" +
				" and a.alertTime between '"+ from +"'"+" and '"+ to+"'";
	}
	else
	{
		query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+"" +
				" and d.friendlyName = '"+deviceToSearch+"'"+
				" and a.alertTime between '"+ from +"'"+" and '"+ to+"'";
	}
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		count = q.list().get(0);
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return count;
}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlarm(int getiDisplayStart,
		int getiDisplayLength, long id, String deviceToSearch, Timestamp from,
		Timestamp to) {
	String query=""; 
	if((deviceToSearch.equals("") || deviceToSearch == null) && (from ==null) && (to==null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+
		        " and a.alarmStatus>0 " +
		        " group by a order by a.id desc";
	}
	else if((!deviceToSearch.equals("") || deviceToSearch != null) && (from == null) && (to == null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+  " and a.alarmStatus>0 " +
			        " group by a order by a.id desc";
	}
	else if((deviceToSearch.equals("") || deviceToSearch == null) && (from !=null) && (to!=null))
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ 
		         " and a.alarmStatus>0 " +
			       " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
			        " group by a order by a.id desc";
	}
	else
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+
		        " and a.alarmStatus>0 " +
		        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		        " group by a order by a.id desc";
	}
	DBConnection dbc = null;
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		q.setMaxResults(getiDisplayLength);
		q.setFirstResult(getiDisplayStart);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}

public Object getCountOfImvgAlarms(long id, String deviceToSearch,
		Timestamp from, Timestamp to) {
	DBConnection dbc = null;
	Object count = null;
	
	
	String query="";
	if((deviceToSearch.equals("") || deviceToSearch == null) && (from ==null) && (to==null) )
	{
		 query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+""+
		 		" and a.alarmStatus>0 " ;
	}
	else if((!deviceToSearch.equals("") || deviceToSearch != null) && (from == null) && (to == null) )
	{
		 query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+"" +
				 " and a.alarmStatus>0 " +
				" and d.friendlyName = '"+deviceToSearch+"'";
	}
	else if((deviceToSearch.equals("") || deviceToSearch == null) && (from !=null) && (to!=null))
	{
		 query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+"" +
				 " and a.alarmStatus>0 " +
				" and a.alertTime between '"+ from +"'"+" and '"+ to+"'";
	}
	else
	{
		query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+"" +
				 " and a.alarmStatus>0 " +
				" and d.friendlyName = '"+deviceToSearch+"'"+
				" and a.alertTime between '"+ from +"'"+" and '"+ to+"'";
	}
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		count = q.list().get(0);
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return count;
}

//parishod start: added function to update the alarmStatus of alertsfromimvg table

public void updateStatusoftheAlert(String alertId) {
	DBConnection dbc = null;
	int status = 3;
	
	try {
		
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		String hql =  "update alertsfromimvg set alarmStatus=" + status
						+ " where id ='"+alertId+"'";
		
		
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
//parishod end
@SuppressWarnings("unchecked")
public List<?>  backupAlerts(long id) {
	DBConnection dbc = null;
	
	
	List<Object[]> objects = null;
	String query= "select a from "+
			"AlertsFromImvg as a join a.device.gateWay"+
			" where a.device.gateWay ="+id;
	/*String query = "insert into BackupOfAlertsFromImvg(id,device,alertType,alertTime,alarmStatus,alertValue)"+
	" select a.id, a.device, a.alertType, a.alertTime, a.alarmStatus, a.alertValue from AlertsFromImvg as a join a.device.gateWay"+" where a.device.gateWay ="+id;*/

   
    
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
	
}

//naveen start
public boolean  backup(long id) {
	DBConnection dbc = null;
	
	
	
	
	/*String query = "insert into backupOfAlertsFromImvg"+
	" select a.id, a.device, a.alertType, a.alertTime, a.alarmStatus, a.alertValue from AlertsFromImvg as a "+" Where device ="+id;*/

   
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = dbc.getSession().createSQLQuery("insert into backupOfAlertsFromimvg select * from alertsfromimvg where device="+id);
		Transaction tx = session.beginTransaction();
		q.executeUpdate();
		tx.commit();
		
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return true;
	
}
//naveen end

//parishod start: Sending power info to mid
@SuppressWarnings("unchecked")
public List<?> listAskedAlertsforMid(long id) {
	
	DBConnection dbc = null;
	String query = "select a.alertTime,d.generatedDeviceId,a.alertValue from " +
    " AlertsFromImvg as a left join a.device as d left join a.device.gateWay as  g " +
    " where a.device.gateWay = "+id+" and a.alertValue != null group by a order by a.id desc";
	
/*String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id from " +
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
	        " where a.device.gateWay = "+id+" and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc";*/

	
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}
//parishod end

//Naveen start: to handle alerts from backup table on jsp
@SuppressWarnings("unchecked")
public List<Object[]> listAskedOldAlertsImvgTypeForAlarm(String sSearch,
		String sOrder, int getiDisplayStart, int getiDisplayLength, long id, String deviceToSearch, Timestamp from, Timestamp to) {
	
	String query=""; 
	if((deviceToSearch.equals("") || deviceToSearch == null) && (from ==null) && (to==null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ " group by a order by a.id desc";
	}
	else if((!deviceToSearch.equals("") || deviceToSearch != null) && (from == null) && (to == null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+
			        " group by a order by a.id desc";
	}
	else if((deviceToSearch.equals("") || deviceToSearch == null) && (from !=null) && (to!=null))
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ 
			       " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
			        " group by a order by a.id desc";
	}
	else
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+
		        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		        " group by a order by a.id desc";
	}
	DBConnection dbc = null;
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		q.setMaxResults(getiDisplayLength);
		q.setFirstResult(getiDisplayStart);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}

public void deleteCurrentAlerts(long id) {
	
	String query = "delete from alertsfromimvg where device='"+ id + "'";
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		Transaction tx = session.beginTransaction();
		q.executeUpdate();
		tx.commit();
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}

	
}


public List<Object[]> listAskedAlarmFromBackup(int getiDisplayStart,
		int getiDisplayLength, long id, String deviceToSearch, Timestamp from,
		Timestamp to) {
	String query=""; 
	if((deviceToSearch.equals("") || deviceToSearch == null) && (from ==null) && (to==null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+
		        " and a.alarmStatus>0 " +
		        " group by a order by a.id desc";
	}
	else if((!deviceToSearch.equals("") || deviceToSearch != null) && (from == null) && (to == null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+  " and a.alarmStatus>0 " +
			        " group by a order by a.id desc";
	}
	else if((deviceToSearch.equals("") || deviceToSearch == null) && (from !=null) && (to!=null))
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ 
		         " and a.alarmStatus>0 " +
			       " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
			        " group by a order by a.id desc";
	}
	else
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+
		        " and a.alarmStatus>0 " +
		        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		        " group by a order by a.id desc";
	}
	DBConnection dbc = null;
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		q.setMaxResults(getiDisplayLength);
		q.setFirstResult(getiDisplayStart);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}

public Object getCountOfImvgBackupAlerts(long id, String deviceToSearch,
		Timestamp from, Timestamp to) {
	DBConnection dbc = null;
	Object count = null;
	/*String query = "select count(a) from "+
	"AlertsFromImvg as a join a.device.gateWay"+
	" where a.device.gateWay ="+id;*/
	
	String query="";
	if((deviceToSearch.equals("") || deviceToSearch == null) && (from ==null) && (to==null) )
	{
		 query = "select count(a) from "+
				"BackupOfAlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+"";
	}
	else if((!deviceToSearch.equals("") || deviceToSearch != null) && (from == null) && (to == null) )
	{
		 query = "select count(a) from "+
				"BackupOfAlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+"" +
				" and d.friendlyName = '"+deviceToSearch+"'";
	}
	else if((deviceToSearch.equals("") || deviceToSearch == null) && (from !=null) && (to!=null))
	{
		 query = "select count(a) from "+
				"BackupOfAlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+"" +
				" and a.alertTime between '"+ from +"'"+" and '"+ to+"'";
	}
	else
	{
		query = "select count(a) from "+
				"BackupOfAlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay ="+id+"" +
				" and d.friendlyName = '"+deviceToSearch+"'"+
				" and a.alertTime between '"+ from +"'"+" and '"+ to+"'";
	}
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		count = q.list().get(0);
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return count;
}














@SuppressWarnings("unchecked")
public List<Object[]> listAskedOldAlarmPerDevice(long id) {
	 String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device.gateWay = "+id+" and a.alarmStatus=1 " +
		        " group by a order by a.id desc";
	
	 
	 List<Object[]> objects = null;
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;
}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedOldAlarmPerDeviceWithoutdate(Long selectedDevice) {
	List<Object[]> objects = null;
	  
	  String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device = "+selectedDevice+
		        " and a.alarmStatus=1 " +
		        " group by a order by a.id desc";
	
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;

}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedOldAlarmPerDate(Timestamp from, Timestamp to) {
	List<Object[]> objects = null;
	
		to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
	        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
	        " where a.alarmStatus=1 " +
	        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
	        " group by a order by a.id desc";

	
  DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			
			objects=q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}

		return objects;
}

@SuppressWarnings("unchecked")
public List<Object[]> listAskedOldAlarmPerDevice(Long selectedDevice,
		Timestamp from, Timestamp to) {
	 List<Object[]> objects = null;
		to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	  
	  String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device = "+selectedDevice+
		        " and a.alarmStatus=1 " +
		        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		        " group by a order by a.id desc";
	
		
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;

}

@SuppressWarnings("unchecked")
public List<?> listAskedOldAlerts(long id) {
	
	DBConnection dbc = null;
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
    " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
    " where a.device.gateWay = "+id+" group by a order by a.id desc";
	
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}

@SuppressWarnings("unchecked")
public List<Object[]> getCountOfOldAlertsPerDeviceWithoutdate(long id,Long selectedDevice) {
	List<Object[]> objects = null;
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
		    " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
		    "  where a.device = "+selectedDevice +
		    " and a.device.gateWay = "+id+" group by a order by a.id desc";
		
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}
			return objects;
}

@SuppressWarnings("unchecked")
public List<Object[]> getCountOfOldAlertsPerDate(long id,Timestamp from, Timestamp to) {
	List<Object[]> objects = null;
		to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
		    " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
		    " where a.device.gateWay = "+id+
		    " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		    " group by a order by a.id desc";
	
	DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			objects=q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}

		return objects;

}

@SuppressWarnings("unchecked")
public List<Object[]> getCountOfOldAlertsPerDeviceAndDate(long id,
		Long selectedDevice, Timestamp from, Timestamp to) {
	to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
		    " BackupOfAlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
		    " where a.device.gateWay = "+id+
		    " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		    " and a.device = "+selectedDevice +
		    " group by a order by a.id desc";
			DBConnection dbc = null;
			List<Object[]> objects = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				objects = q.list();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
			return objects;
}

public void DeleteSelectedOldAlerts(String AlertId) {
	
	String query = "delete from backupOfAlertsFromimvg where id='"+ AlertId + "'";
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		Transaction tx = session.beginTransaction();
		q.executeUpdate();
		tx.commit();
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}

	
}

//naveen end



public Object getUploadedImagefrombackup(long id,long deviceId) {
	DBConnection dbc = null;
	Object objects1 =null;
	
	String query1 = " select ui.filePath,ui.id from UploadsByImvg as ui where ui.alertsFromImvg="+ id  ;
	//String query2 = " select b.alertTime from BackupOfAlertsFromImvg as b where b.id="+ id ;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query1);
		objects1 = q.list().get(0);
		
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects1;
}

//3gp by apoorva
@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlertsImvgTypeForAlarmOfMultipleGateway(String sSearch,
		String sOrder, int getiDisplayStart, int getiDisplayLength, long id, String deviceToSearch, Timestamp from, Timestamp to) {
	String query=""; 
	if((deviceToSearch.equals("") || deviceToSearch == null) && (from ==null) && (to==null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay.customer = "+id+ " group by a order by a.id desc";
	}
	else if((!deviceToSearch.equals("") || deviceToSearch != null) && (from == null) && (to == null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay.customer = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+
			        " group by a order by a.id desc";
	}
	else if((deviceToSearch.equals("") || deviceToSearch == null) && (from !=null) && (to!=null))
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay.customer = "+id+ 
			       " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
			        " group by a order by a.id desc";
	}
	else
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay.customer = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+
		        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		        " group by a order by a.id desc";
	}
	
	DBConnection dbc = null;
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		q.setMaxResults(getiDisplayLength);
		q.setFirstResult(getiDisplayStart);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}


@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlarmPerDeviceForMultipleGateways(long id) {
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id,a.alertValue from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
		        " where a.device.gateWay.customer = "+id+" and a.alarmStatus=1 " +
		        " group by a order by a.id desc";
	
	  /*String query = "select a.id,a.alertTime,d.friendlyName,d.id,at.name,at.alertCommand,at.id,count(u) from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u " +		       
		        " where a.device= "+selectedDevice+"" +
		        " and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc" +
		        " and a.alertTime between "+ from +" and "+ to +"";*/
	 List<Object[]> objects = null;
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				//Query q = dbc.getSession().createSQLQuery(query);
				
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}

			return objects;
}

@SuppressWarnings("unchecked")
public List<?> listAskedAlertsForMultipleGateway(long id) {
	
	DBConnection dbc = null;
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
    " where a.device.gateWay.customer = "+id+" group by a order by a.id desc";
	
/*String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,at.id from " +
	        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as g " +		       
	        " where a.device.gateWay = "+id+" and (at.id,d.id) in (select uc.alerts,uc.device from UserChoosenAlerts as uc) group by a order by a.id desc";*/

	
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}

//3 gp
@SuppressWarnings("unchecked")
public List<Object[]> listAskedAlarmForMultipleGateway(int getiDisplayStart,
		int getiDisplayLength, long id, String deviceToSearch, Timestamp from,
		Timestamp to) {
	String query=""; 
	if((deviceToSearch.equals("") || deviceToSearch == null) && (from ==null) && (to==null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay.customer = "+id+
		        " and a.alarmStatus>0 " +
		        " group by a order by a.id desc";
	}
	else if((!deviceToSearch.equals("") || deviceToSearch != null) && (from == null) && (to == null) )
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay.customer = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+  " and a.alarmStatus>0 " +
			        " group by a order by a.id desc";
	}
	else if((deviceToSearch.equals("") || deviceToSearch == null) && (from !=null) && (to!=null))
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay.customer = "+id+ 
		         " and a.alarmStatus>0 " +
			       " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
			        " group by a order by a.id desc";
	}
	else
	{
		query = "select a.id,a.alertTime,d.friendlyName,at.name,at.alertCommand,d.id from " +
		        " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.device.gateWay as g " +		       
		        " where a.device.gateWay.customer = "+id+ " and " + " d.friendlyName = '"+deviceToSearch+"'"+
		        " and a.alarmStatus>0 " +
		        " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		        " group by a order by a.id desc";
	}
	DBConnection dbc = null;
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		q.setMaxResults(getiDisplayLength);
		q.setFirstResult(getiDisplayStart);
		objects = q.list();
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return objects;
}


public Object getCountOfImvgAlarmsForMultipleGateways(long id, String deviceToSearch,
		Timestamp from, Timestamp to) {
	DBConnection dbc = null;
	Object count = null;
	
	String query="";
	if((deviceToSearch.equals("") || deviceToSearch == null) && (from ==null) && (to==null) )
	{
		 query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay.customer ="+id+""+
		 		" and a.alarmStatus>0 " ;
	}
	else if((!deviceToSearch.equals("") || deviceToSearch != null) && (from == null) && (to == null) )
	{
		 query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay.customer ="+id+"" +
				 " and a.alarmStatus>0 " +
				" and d.friendlyName = '"+deviceToSearch+"'";
	}
	else if((deviceToSearch.equals("") || deviceToSearch == null) && (from !=null) && (to!=null))
	{
		 query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay.customer ="+id+"" +
				 " and a.alarmStatus>0 " +
				" and a.alertTime between '"+ from +"'"+" and '"+ to+"'";
	}
	else
	{
		query = "select count(a) from "+
				"AlertsFromImvg as a left join a.device as d "+
				" where a.device.gateWay.customer ="+id+"" +
				 " and a.alarmStatus>0 " +
				" and d.friendlyName = '"+deviceToSearch+"'"+
				" and a.alertTime between '"+ from +"'"+" and '"+ to+"'";
	}
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		count = q.list().get(0);
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return count;
}

//3gp
@SuppressWarnings("unchecked")
public List<Object[]> getCountOfAlertsPerDeviceWithoutdateForMultipleGateway(long id,Long selectedDevice) {
	List<Object[]> objects = null;
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
		    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
		    "  where a.device = "+selectedDevice +
		    " and a.device.gateWay.customer = "+id+" group by a order by a.id desc";
		
	  DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				objects=q.list();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}
			return objects;
}

@SuppressWarnings("unchecked")
public List<Object[]> getCountOfAlertsPerDateFormultipleGateway(long id,Timestamp from, Timestamp to) {
	List<Object[]> objects = null;
	//vibhu added a day to 'to'
		to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
		    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
		    " where a.device.gateWay.customer = "+id+
		    " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		    " group by a order by a.id desc";
	
	DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			objects=q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}

		return objects;

}

@SuppressWarnings("unchecked")
public List<Object[]> getCountOfAlertsPerDeviceAndDateForMultipleGateway(long id,
		Long selectedDevice, Timestamp from, Timestamp to) {
	//vibhu added a day to 'to'
	to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
	
	String query = "select a.alertTime,d.friendlyName,at.name,a.id,d.id,count(u),at.alertCommand,a.alertValue from " +
		    " AlertsFromImvg as a left join a.device as d left join a.alertType as at left join a.uploadsByImvgs as u left join a.device.gateWay as  g " +
		    " where a.device.gateWay.customer = "+id+
		    " and a.alertTime between '"+ from +"'"+" and '"+ to+"'" +
		    " and a.device = "+selectedDevice +
		    " group by a order by a.id desc";
			DBConnection dbc = null;
			List<Object[]> objects = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				
				objects = q.list();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
			return objects;
}

}

