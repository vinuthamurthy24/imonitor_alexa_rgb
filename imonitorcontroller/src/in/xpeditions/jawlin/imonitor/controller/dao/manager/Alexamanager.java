package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.thoughtworks.xstream.XStream;

public class Alexamanager {
	
	
	DaoManager daoManager = new DaoManager();
	//Naveen Commented on 26th Oct 2017
	//Below function is used to check user existence for login with amazon
	/*public boolean checkAmazonUserAndUpdate(String amazonUserId, String email, String username, String token ){
		
		boolean result = false;
		String query = "select a from Alexa as a where a.amazonId='"+email+"' and a.userName='"+username+"'";
		DBConnection dbc = null;
		try {
			
			dbc = new DBConnection();
			Session sess1 = dbc.openConnection();
			Query q = sess1.createQuery(query);
			Alexa alexaFromDb = (Alexa)q.uniqueResult();
			if(alexaFromDb != null){
				
				query = "update Alexa set userid='"+amazonUserId+"' where id="+alexaFromDb.getId()+"";
				LogUtil.info("alexa query: "+ query);
				dbc = new DBConnection();
				dbc.openConnection();
				Session session = dbc.getSession();
				q = session.createSQLQuery(query);
				Transaction tx = session.beginTransaction();
				q.executeUpdate();
				tx.commit();
				
				result= true;
				
			}else{
				
				LogUtil.info("No alexa user exists");
				result=false;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.info("Could not update amazon alexa user");
		}
		return result;
	}*/
	
	public String getCustomerByAmazonid(String accessToken){
		XStream stream=new XStream();
		
		DBConnection dbc = null;
		String customerId = null;
	try {
		String query = "select c.customerId from  Alexa as a left join customer as c on a.customer=c.id  where a.accesstoken='"+accessToken+"' ";
		LogUtil.info(" query customerId !"+query);
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		customerId = (String) q.uniqueResult();

	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	LogUtil.info("  customerId !="+stream.toXML(customerId));
	return customerId;	
	}
	
public String getCustomerByAmazonVideoid(String accessToken){
	//LogUtil.info("getCustomerByAmazonVideoid !");
		
		DBConnection dbc = null;
		String customerId = null;
	try {
		String query = "select c.customerId from  AlexaVideo as a left join customer as c on a.customer=c.id  where a.accesstoken='"+accessToken+"' ";
		LogUtil.info("getCustomerByAmazonVideoid query!"+query);
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		customerId = (String) q.uniqueResult();

	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	LogUtil.info("getCustomerByAmazonVideoid customerId!"+customerId);
	return customerId;	
	}

	
	
public Object[] getDeviceWithReportValues(String id, String deviceId){
	DBConnection dbc = null;
	Object[] heatValue = null;
	String query = "select a.sensedTemperature,fanModeValue from device as d left join acConfiguration as a on a.id=d.deviceConfiguration left join gateway as g on g.id=d.gateway where g.id='"+id+"' and d.deviceId='"+deviceId+"'";
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		heatValue = (Object[]) q.uniqueResult();
	} catch (Exception e) {
		// TODO: handle exception
	}finally {
		dbc.closeConnection();
	}
	
	return heatValue;
}

public Alexa getCustomerDetailsByImonitorAccessToken(String token){
	
	DBConnection dbc = null;
	Alexa alexaUser = (Alexa) daoManager.getOne("accesstoken", token, Alexa.class);
	XStream stream = new XStream();
	
	
	return alexaUser;
}

/*public AlexaVideo getCustomerDetailsByAlexaVideoImonitorAccessToken(String token){
	LogUtil.info("getCustomerDetailsByAlexaVideoImonitorAccessToken"+token);
	XStream stream = new XStream();
	DBConnection dbc = null;
	AlexaVideo alexavideoUser = (AlexaVideo) daoManager.getOne("accesstoken", token, AlexaVideo.class);
	LogUtil.info("alexavideoUser"+stream.toXML(alexavideoUser));
	return alexavideoUser;
}*/

public boolean updateAlexaUser(Alexa alexaUser) {
	boolean result = false;
	try {
		daoManager.update(alexaUser);
		result = true;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}

/*public boolean updateAlexaVideoUser(AlexaVideo alexavideoUser) {
	boolean result = false;
	try {
		daoManager.update(alexavideoUser);
		result = true;
	} catch (Exception e) {
		e.printStackTrace();
	}
	LogUtil.info("updateAlexaVideoUser"+result);
	return result;
}*/

public Alexa getAlexaUserByCustomerId(Customer customer){
	
		return (Alexa) daoManager.getOne("customer", customer, Alexa.class);
	
}

/*public AlexaVideo getAlexaVideoUserByCustomerId(Customer customer){
	
	return (AlexaVideo) daoManager.getOne("customer", customer, AlexaVideo.class);

}*/

public void deleteAlexaUser(Alexa alexaUser){
	boolean result = false;

	try {
		daoManager.delete(alexaUser);
		result = true;
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}



}
