package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GoogleHome;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

public class VoiceControlManager {
	DaoManager daoManager = new DaoManager();
	
	public boolean savealexa(Alexa alexa) {
		boolean result = false;
		try {
			daoManager.save(alexa);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
	public boolean savegoogle(GoogleHome google) {
		boolean result = false;
		try {
			daoManager.save(google);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Object[] gettokenforalexauser(String code){
		DBConnection dbc = null;
		 Object[] objects = null;
		String query="select accesstoken,refreshToken from Alexa where code='"+code+"'";
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			objects = (Object[])q.uniqueResult();
		}	 
			catch (Exception ex) {
				LogUtil.info("Caught Exception while retrieving alexa usr token: ", ex);
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
			return objects;
	}
	
	public Object[] gettokenforalexavideouser(String code){
		
		DBConnection dbc = null;
		 Object[] objects = null;
		String query="select accesstoken,refreshToken from AlexaVideo where code='"+code+"'";
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			objects = (Object[])q.uniqueResult();
		}	 
			catch (Exception ex) {
				LogUtil.info("Caught Exception while retrieving alexa usr token: ", ex);
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
			return objects;
	}
	
	public String gettokenforghome(String code){
		DBConnection dbc = null;
		String customer="";
		String query="select customerid from GoogleHome where code='"+code+"'";
		
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			 customer=(String) q.uniqueResult();
		}	 
			catch (Exception ex) {
				LogUtil.info("Caught Exception while retrieving google customer: ", ex);
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
			return customer;
	}
	public void updategooglehome(String code,String token){
		DBConnection dbc = null;
		
		String query="update GoogleHome set accesstoken='"+token+"'"+" where code='"+code+"'";
				try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Transaction tx = session.beginTransaction();
			Query q = session.createSQLQuery(query);
			q.executeUpdate();
			tx.commit();
		}
			catch (Exception ex) {
				LogUtil.info("Caught Exception while updating google customer: ", ex);
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
	}
	public String getcustomerforghome (String token){
		DBConnection dbc = null;
		String customer="";
		String query="select customerid from GoogleHome where accesstoken='"+token+"'";
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			customer=(String) q.uniqueResult();
		}catch (Exception ex) {
				LogUtil.info("Caught Exception while retrieving google customer: ", ex);
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
		return customer;
	}
	
	public String updateAlexaToken(String oldRef,String newRefres, String newAcc){
		
		DBConnection dbc = null;
		String query = "select a from Alexa as a " +
				"where a.refreshToken='"+oldRef+"'";
		
		String result = "Failure";
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Alexa alexa = (Alexa) q.uniqueResult();
			if(alexa != null){
				
				alexa.setRefreshToken(newRefres);
				alexa.setAccesstoken(newAcc);
				Transaction tx = session.beginTransaction();
			   	session.update(alexa);
				tx.commit();
				result = "Success";
			}else{//Checking for previous refresh token
				
				query = "select a from Alexa as a " +
						"where a.previousRefreshToken='"+oldRef+"'";
				
				try {
					dbc = new DBConnection();
					session = dbc.openConnection();
					q = session.createQuery(query);
					alexa = (Alexa) q.uniqueResult();
					if(alexa != null){
						alexa.setPreviousRefreshToken(alexa.getRefreshToken());
						alexa.setRefreshToken(newRefres);
						Transaction tx = session.beginTransaction();
					   	session.update(alexa);
						tx.commit();
						result = "Success";
					}else{
						
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			dbc.closeConnection();
		}
		
		return result;
	}
	
	

	
	public Alexa checkAlexaUser(Customer customer){
		
		String query="select a from Alexa as a where a.customer='"+customer.getId()+"'";
		Alexa aUser = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			aUser=(Alexa) q.uniqueResult();
						
		}	 
			catch (Exception ex) {
				LogUtil.info("Caught Exception while retrieving alexa user existence: ", ex);
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
		
		
		return aUser;
		
	}
	



	
	public boolean updateAlexaUser(Alexa alexa){
		
		DBConnection dbc = null;
		boolean result = false;
		try {
			daoManager.update(alexa);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

}
