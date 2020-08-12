package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AdminSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerAndSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerPasswordHintQuestionAnswer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

public class SuperUserManager {

	DaoManager daoManager = new DaoManager();
	//SuperUser
		public boolean saveSuperUser(AdminSuperUser superUser) {
			boolean result = false;
			try {
				daoManager.save(superUser);
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		
		//SuperUser
		@SuppressWarnings("unchecked")
		public List<AdminSuperUser> listOfAdminSuperUsers() {
			List<AdminSuperUser> superUsers = new ArrayList<AdminSuperUser>();
			try {
				superUsers = (List<AdminSuperUser>) daoManager.list(AdminSuperUser.class);
			} catch (Exception e) {
				e.printStackTrace();
			}                                                                                                                   
			return superUsers;

		}
		
		//SuperUser
		@SuppressWarnings("unchecked")
		public List listAskedSuperUsers(String sSearch, String sOrder, int start,
				int length)
		{
			List list = null;
			try 
			{
				String query = "";
				query += "select s.id,s.superUserId"
						+ " from "
						+ "AdminSuperUser as s "
						+ "where (" + "s.superUserId like '%"
						+ sSearch + "%' ) " + sOrder;
				list = daoManager.listAskedObjects(query, start, length,
						AdminSuperUser.class);
				XStream stream=new XStream();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
				LogUtil.info("Exception : "+e.getMessage());
			}
			
			return list;
		}
		
		//SuperUser
		public int getTotalSuperUsersCount(String sSearch) {
			DBConnection dbc = null;
			int count = 0;
			try {
				String query = "";
				query += "select count(s)" + " from "
						+ "AdminSuperUser as s  "
						+ "where (" + "s.superUserId like '%"
						+ sSearch + "%' ) " ;
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
		
		//SuperUser
		public String isSuperUserExists(String superUserId, String password) {
			String result = "failure";
			String query = "select count(s) " + " from AdminSuperUser as s"
					+ "  where s.superUserId='" + superUserId + "' and s.password='" + password + "'";
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				Session session = dbc.openConnection();
				Query q = session.createQuery(query);
				Long cnt = (Long) q.uniqueResult();
				
				if(cnt > 0){
					result = "success";
				}
			} catch (Exception e) {
				LogUtil.error("Error when retreiving user by userid : "
						+ e.getMessage());
				// e.printStackTrace();
			} finally {
				if (null != dbc) {
					dbc.closeConnection();
				}
			}
			return result;
		}
		
		public List<AdminSuperUser> listSuperUsers() {
			List<AdminSuperUser> superUsers=new ArrayList<AdminSuperUser>();
			try{
				superUsers=(List<AdminSuperUser>)daoManager.list(AdminSuperUser.class);
			}catch(Exception e){
				e.printStackTrace();
			}return superUsers;
		
		}
		
		public AdminSuperUser getSuperUserById(long id) {
			return (AdminSuperUser) daoManager.get(id, AdminSuperUser.class);
		}
			
		public CustomerAndSuperUser getCustomerAndSuperUserByCustomerId(long id){
			DBConnection dbc = null;
			CustomerAndSuperUser customerAndSuperUser=null;
			String query = "select c.id, c.customer, c.superUser"
						+ " from customerAndSuperUser as c "
						+ " where c.customer = "+id;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Session session = dbc.getSession();
				Query q = session.createSQLQuery(query);
				Object[] objects = (Object[]) q.uniqueResult();
				if(objects != null){
					customerAndSuperUser=new CustomerAndSuperUser();
					customerAndSuperUser.setId(((BigInteger)objects[0]).longValue());
					Customer customer=new Customer();
					customer.setId(((BigInteger)objects[1]).longValue());
					AdminSuperUser superUser=new AdminSuperUser();
					superUser.setId(((BigInteger)objects[2]).longValue());
					customerAndSuperUser.setCustomer(customer);
					customerAndSuperUser.setSuperUser(superUser);
				}
			} catch (Exception e) {
				LogUtil.error("Error while retieving getCustomerAndSuperUserByCustomerId."+ e.getMessage());
				e.printStackTrace();
			} finally {
				if(dbc != null){
					dbc.closeConnection();
				}
			}
			return customerAndSuperUser;
			
		}
		
		public AdminSuperUser getsuperUserBySuperUserId(String superUserId){
			DBConnection dbc = null;
			AdminSuperUser adminSuperUser=null;
			String query = "select a.id,a.superUserId "
						+ " from adminSuperuser as a "
						+ " where a.superUserId = '"+superUserId + "'";
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Session session = dbc.getSession();
				Query q = session.createSQLQuery(query);
				Object[] objects = (Object[]) q.uniqueResult();
				if(objects != null){
					adminSuperUser=new AdminSuperUser();
					adminSuperUser.setId(((BigInteger)objects[0]).longValue());
					//adminSuperUser.setSuperUserId(superUserId);
				}
			} catch (Exception e) {
				LogUtil.error("Error while retieving getsuperUserBySuperUserId."+ e.getMessage());
				e.printStackTrace();
			} finally {
				if(dbc != null){
					dbc.closeConnection();
				}
			}
			return adminSuperUser;
		}
		
		@SuppressWarnings("unchecked")
		public List<Customer> listOfCustomersForSuperUser(long superuserId) {
			DBConnection dbc = null;
			List<Customer> customerIdList = null;
			XStream stream=new XStream();
			try {
				String query = "";
		        query +="select c.id,c.customer,c.superUser,s.customerId from customerAndSuperUser as c left join customer as s on c.customer=s.id where c.superUser="+ superuserId ;
		        //LogUtil.info("Query : "+ query);
				dbc = new DBConnection();
				Session session = dbc.openConnection();
				Query q = session.createSQLQuery(query);
				List<Object[]> list = q.list();
				customerIdList = new ArrayList<Customer>();
				//LogUtil.info("listOfCustomersForSuperUser objects: "+stream.toXML(list) );
				for (Object[] object : list) {
					Customer customer=new Customer();
					customer.setId(((BigInteger)object[1]).longValue());
					customer.setCustomerId(IMonitorUtil.convertToString(object[3]));
					customerIdList.add(customer);
				}
			} catch (Exception ex) {
				LogUtil.info("Error "+ex.getMessage());
				ex.printStackTrace();
			} finally {
				dbc.closeConnection();
			}
			//LogUtil.info("listOfCustomersForSuperUser : "+stream.toXML(customerIdList) );
			return customerIdList;
		}
		
		public boolean deleteSuperUserMappingToCustomerById(long id) {
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				String hql =  "delete from CustomerAndSuperUser where superUser ="+id;
				
				Session session = dbc.openConnection();
				dbc.beginTransaction();
				Query query = session.createQuery(hql);
			    int rowCount = query.executeUpdate();
			    dbc.commit();
			    return (rowCount>0)?true:false;
			}catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.info("Errrrror : " + ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return false;
		}
		
		public boolean deleteAdminSuperUserEntityById(long id) {
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				String hql =  "delete from AdminSuperUser where id ="+id;
				Session session = dbc.openConnection();
				dbc.beginTransaction();
				Query query = session.createQuery(hql);
			    int rowCount = query.executeUpdate();
			    dbc.commit();
			    return (rowCount>0)?true:false;
			}catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.info("Errrrror : " + ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return false;
		}
		
		public boolean disablePushNotificationAfterCustomerLogout(String imei) {
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				String query = "";
				query += "delete from PushNotification as p where p.ImeiNumber='"+ imei + "'";
				
				Session session = dbc.openConnection();
				dbc.beginTransaction();
				Query q = session.createQuery(query);
			    int rowCount = q.executeUpdate();
			  
			    dbc.commit();
			    return (rowCount>0)?true:false;
			}catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.info("Errrrror : " + ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return false;
		}
		
		public boolean disablePushNotificationForIOS(String deviceToken) {
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				String query = "";
				query += "delete from PushNotification as p where p.deviceToken='"+ deviceToken + "'";
				
				Session session = dbc.openConnection();
				dbc.beginTransaction();
				Query q = session.createQuery(query);
			    int rowCount = q.executeUpdate();
			  
			    dbc.commit();
			    return (rowCount>0)?true:false;
			}catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.info("Errrrror : " + ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return false;
		}
		
		
		
		
		
}
