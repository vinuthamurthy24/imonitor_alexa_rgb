/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.imonitorapi.authcontroller.dao.manager;

import in.monitor.authprovider.Clients;



import in.imonitorapi.util.LogUtil;

import in.imonitor.authcontroller.DBConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ClentManager {
	DaoManager daoManager = new DaoManager();
	
	
	public boolean saveCode(Clients AuthCode) {
		boolean result = false;
		try {
			daoManager.save(AuthCode);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	public String isClientExists(String clientId, String ClientSecret) {
		
		
		String result = "failure";
		
		String query = "select count(c) " + " from Clients as c "
				+ "  where Client_id= '" + clientId + "' and ClientSecret = '" + ClientSecret + "'";
		
		DBConnection dbc = null;
		try {
			LogUtil.info("trying : " + query);
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Long lCount = (Long) q.uniqueResult();
			int count = lCount.intValue();
			
			
			if(count > 0){
				result = "success";
			/*result=generateAuthcode(clientId,ClientSecret );*/
			}
		} catch (Exception e) {
			LogUtil.info("Error when retreiving user by userid : "
					+ e.getMessage() +query);
			
		 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return result;
	}
	
public String isClientExistswithRefreshtoken(String clientId, String ClientSecret, String RefreshToken) {
		
		
		String result = "failure";
		
		String query = "select count(c) " + " from Clients as c "
				+ "  where RefreshToken= '" + RefreshToken + "' and ClientSecret = '" + ClientSecret + "'";
		
		DBConnection dbc = null;
		try {
			LogUtil.info("trying : " + query);
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Long lCount = (Long) q.uniqueResult();
			int count = lCount.intValue();
			
			
			if(count > 0){
				result = "success";
			/*result=generateAuthcode(clientId,ClientSecret );*/
			}
		} catch (Exception e) {
			LogUtil.info("Error when retreiving user by userid : "
					+ e.getMessage() +query);
			
		 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return result;
	}
	

	public String updateClientsByQuerry(String Client_id,String Authcode){
		String result="success";
		String query = "";
		query += "update Clients " + " set AuthCode = '" + Authcode+ "' where Client_id = " + Client_id;
	
		daoManager.executeUpdateQuery(query);
		return result;
	}
	
	public Clients getClient(String Client_id) {
		
		Clients client = (Clients) daoManager.get(Client_id, Clients.class);
		 LogUtil.info("dgfghdhgfhfghgfh***********************: "+ client);
		return client;
		/*Clients client = null; 
		Session session = null;
	    Transaction transaction = null;
	    try{
	    	 LogUtil.info("dgfghdhgfhfghgfh***********************: "+ client);
	      SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	      session = sessionFactory.openSession();
	      transaction = session.beginTransaction();
	      client = (Clients)session.get(Clients.class, Client_id);
	     
	      transaction.commit();
	    }
	    catch(Exception ex){
	      ex.printStackTrace();
	    }
		
	    return client;*/
	}
	
public String VerifyAuthCode(String clientId, String Authcode) {
		
		
		String result = "failure";
		
		String query = "select count(c) " + " from Clients as c "
				+ "  where Client_id= '" + clientId + "' and AuthCode = '" + Authcode + "'";
		
		DBConnection dbc = null;
		try {
			LogUtil.info("trying : " + query);
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Long lCount = (Long) q.uniqueResult();
			int count = lCount.intValue();
			
			
			if(count > 0){
				result = "success";
			/*result=generateAuthcode(clientId,ClientSecret );*/
			}
		} catch (Exception e) {
			LogUtil.info("Error when retreiving user by userid : "
					+ e.getMessage() +query);
			
		 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return result;
	}

public String updateTokensByQuerry(String Client_id,String AccessToken, String RefreshToken, String CreatedAT, String ExpireTime){
	String result="success";
	String query = "";
	query += "update Clients " + " set AccessToken = '" + AccessToken+ "'  , RefreshToken = '" + RefreshToken +
			"', CreatedAT = '"+CreatedAT+ "' , ExpireTime = '" +ExpireTime+ "' where Client_id= " + Client_id  ;
	//LogUtil.info("Tokens Updatinggggggggg : " + query);
	daoManager.executeUpdateQuery(query);
	return result;
}


public static Clients getTokensWithExpireTimeByClientIdAndUpdateStatus(
		String client_id) {
	
	Clients client = null;
	
	
	String query = "select c " + " from Clients as c "
			+ "  where Client_id= '" + client_id + "'";
	
	DBConnection dbc = null;
	try {
		//LogUtil.info("trying : " + query);
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		client = (Clients) q.uniqueResult();
		
		
	} catch (Exception e) {
		LogUtil.info("Error when retreiving user by userid : "
				+ e.getMessage() +query);
		
	 e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return client;
	
	
}
	
	

}
