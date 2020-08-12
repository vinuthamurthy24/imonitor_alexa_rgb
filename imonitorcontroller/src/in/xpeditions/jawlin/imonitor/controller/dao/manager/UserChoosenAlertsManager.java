package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserChoosenAlerts;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

public class UserChoosenAlertsManager
{
	//Original code commented on 7 Feb for Push Notification Change
	/*public List<UserChoosenAlerts> listUserChoosenAlertsfromdevice(long deviceId,long alertTypeId) {
		DBConnection dbc = null;
		List<UserChoosenAlerts> choosenAlerts = null;
		try {
			String query = "";
	        query +="select u.id,u.gateway,u.device,u.alerts from userChoosenAlerts as u where u.device="+deviceId+ " and u.alerts="+alertTypeId ;
	        LogUtil.info("Query : " + query);
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			List<Object[]> list = q.list();
			choosenAlerts = new ArrayList<UserChoosenAlerts>();
			for (Object[] object : list) 
			{
				UserChoosenAlerts alert=new UserChoosenAlerts();
				alert.setId(((BigInteger)object[0]).longValue());
				GateWay gateWay=new GateWay();
				gateWay.setId(((BigInteger)object[1]).longValue());
				alert.setGateway(gateWay);
				
				String dId=IMonitorUtil.convertToString(object[2]);
				alert.setDevice(dId);
				String alertId=IMonitorUtil.convertToString(object[3]);
				alert.setAlerts(alertId);
				choosenAlerts.add(alert);
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			LogUtil.info("Errorr :"+ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		LogUtil.info("User alerts list "+new XStream().toXML(choosenAlerts));
		
		
		return choosenAlerts;
	}*/
	
	
	
	
	
	
	public UserChoosenAlerts listUserChoosenAlertsfromdevice(long deviceId,long alertTypeId) {
		String query = "";
        query +="select u from UserChoosenAlerts as u where u.device="+deviceId+ " and u.alerts="+alertTypeId ;
        
		DBConnection dbc = null;
		UserChoosenAlerts userChoosenAlerts = null;

		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			userChoosenAlerts = (UserChoosenAlerts) q.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.info("Error Message : " + ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return userChoosenAlerts;
	}
}
