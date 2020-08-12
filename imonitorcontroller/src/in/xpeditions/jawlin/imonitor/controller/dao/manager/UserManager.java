/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ForgotPasswordHintQuestion;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Role;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemIntegrator;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserDeviceAccess;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserScenarioAccess;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.thoughtworks.xstream.XStream;


public class UserManager {
	DaoManager daoManager = new DaoManager();

	public boolean saveUser(User user) {
		boolean result = false;
		try {
			daoManager.save(user);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	public boolean deleteUser(User user) {
		boolean result = false;
		try {
			daoManager.delete(user);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	//	LogUtil.info("user Manager class delete user result="+result);
		return result;
	}

	public boolean updateUser(User user) {
		boolean result = false;
		try {
			daoManager.update(user);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<User> listOfUsers() {
		List<User> users = new ArrayList<User>();
		try {
			users = (List<User>) daoManager.list(User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;

	}
	

	public User getUserByUserId(String userId) {
		User user = (User) daoManager.getOne("userId", userId, User.class);
		return user;
	}

	// ******************************************* sumit start: forgot password user authentication *********************************************
	public User getUserForPasswordChange(String userId, String cust){
		User user = null;
		DBConnection dbc = null;
		String query = "select u.id, u.role, u.customer, u.status, u.email , u.mobile, r.name, s.name as name1, c.customerId, c.mobile as mobile1, c.email as email1 "
					+ " from user as u,"
					+ " role as r,"
					+ " status as s,"
					+ " customer as c"
					+ " where u.userId='"+userId+"' "
					+ " and r.id = u.role"
					+ " and s.id = u.status"
					+ " and c.id = u.customer"
					+ " and c.customerId = '"+cust+"'"
					+ " and s.name NOT IN('"+Constants.SUSPEND+"') ";
		
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			if(objects != null){
				long uId = Long.parseLong(IMonitorUtil.convertToString(objects[0]));
				long roleId = Long.parseLong(IMonitorUtil.convertToString(objects[1]));
				long custId = Long.parseLong(IMonitorUtil.convertToString(objects[2]));
				long statId = Long.parseLong(IMonitorUtil.convertToString(objects[3]));
				String email = IMonitorUtil.convertToString(objects[4]);
				String mobile = IMonitorUtil.convertToString(objects[5]);
				String rName = IMonitorUtil.convertToString(objects[6]);
				String sName = IMonitorUtil.convertToString(objects[7]);
				//long cId = Long.parseLong(IMonitorUtil.convertToString(objects[8]));
				String customerId = IMonitorUtil.convertToString(objects[8]);
				
				String CustomerMail= IMonitorUtil.convertToString(objects[10]);
				String CustomerMobile=IMonitorUtil.convertToString(objects[9]);
				
				
				Role role = new Role();
				role.setId(roleId);
				role.setName(rName);
				
				Status status = new Status();
				status.setId(statId);
				status.setName(sName);
				
				Customer customer = new Customer();
				customer.setId(custId);
				//customer.setId(cId);
				customer.setCustomerId(customerId);
				customer.setMobile(CustomerMobile);
				customer.setEmail(CustomerMail);
				
				user = new User();
				user.setId(uId);
				user.setUserId(userId);
				user.setRole(role);
				user.setStatus(status);
				user.setCustomer(customer);
				user.setEmail(email);
				user.setMobile(mobile);
			}
		} catch (Exception e) {
			LogUtil.error("Error while retreiving user by userid :"+ e.getMessage());
			e.printStackTrace();
		}finally {
			if(dbc != null){
				dbc.closeConnection();
			}
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<ForgotPasswordHintQuestion> listOfHintQuestions() {
		List<ForgotPasswordHintQuestion> listOfHintQuestion = new ArrayList<ForgotPasswordHintQuestion>();
		try {
			listOfHintQuestion = (List<ForgotPasswordHintQuestion>) daoManager.list(ForgotPasswordHintQuestion.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfHintQuestion;

	}
	// ************************************************************* sumit end ******************************************************************
	/**
	 * last login time was showing but it was current login.
	 *  created new colomn currentLoginTime
	 * and swaped that two colomn
	 * 
	 * DateMOdified:10/09/2013 by Kantharaj N R
	 *  to get System Integrator information on Login
	 *
	 ***/
	public User getUserWithCustomerByUserIdAndUpdateStatus(String userId, String customerId, String password) {
		User user = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select " +
			" u, s.name, r.name" +
			" ,c.id, c.customerId, c.freeStorageMB, c.paidStorageMB, c.mobile, c.email, s.id" +
			" from User as u" +
			" left join u.customer as c" +
			" left join u.status as s" +
			" left join u.role as r" +
			" left join c.systemIntegrator as s"+
			" where u.userId='" + userId + "'" +
			"and u.status.name not in('"+Constants.SUSPEND+"')" +
			"and c.status.name not in('"+Constants.SUSPEND+"')" +
			" and c.customerId='" + customerId + "'" +
			" and u.password='" + password + "'" +
			"";
			Query q = session.createQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			if(objects != null){
				user = (User) objects[0];
				long cId = Long.parseLong(IMonitorUtil.convertToString(objects[3]));
				String rName = IMonitorUtil.convertToString(objects[2]);
				int freeMB = Integer.parseInt(IMonitorUtil.convertToString(objects[5]));
				int paidMB = Integer.parseInt(IMonitorUtil.convertToString(objects[6]));
			//	user.setLastLoginTime(user.getCurrentLoginTime());
				String CustomerMail= IMonitorUtil.convertToString(objects[8]);
				String CustomerMobile=IMonitorUtil.convertToString(objects[7]);
				
				Long SIiD = (Long) objects[9];;
				SystemIntegrator SI= null;
				if(SIiD != null){
				SI = (SystemIntegrator) session.get(SystemIntegrator.class, SIiD);
				
				}
				
				
				
				Customer customer = new Customer();
				customer.setId(cId);
				customer.setCustomerId(customerId);
				customer.setFreeStorageMB(freeMB);
				customer.setPaidStorageMB(paidMB);
				customer.setMobile(CustomerMobile);
				customer.setEmail(CustomerMail);
				customer.setSystemIntegrator(SI);
				user.setCustomer(customer);
				user.setCurrentLoginTime(new Date());
				//user.setLastLoginTime(new Date());
				Status status = IMonitorUtil.getStatuses().get(Constants.ONLINE);
				user.setStatus(status);
				Transaction tx = session.beginTransaction();
				session.update(user);
				tx.commit();
				Role role = new Role();
				role.setName(rName);
				user.setRole(role);
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving user by userid : "
					+ e.getMessage());
			 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return user;
	}

	
	public User getUserWithCustomerByUserIdAndUpdateStatusAndUpdateLastLogin(String userId, String customerId, String password) {
		User user = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select " +
			" u, s.name, r.name" +
			" ,c.id, c.customerId, c.freeStorageMB, c.paidStorageMB, c.mobile, c.email, c.featuresEnabled, s.id,u.showTips" +
			" from User as u" +
			" left join u.customer as c" +
			" left join u.status as s" +
			" left join u.role as r" +
			" left join c.systemIntegrator as s"+
			" where u.userId='" + userId + "'" +
			"and u.status.name not in('"+Constants.SUSPEND+"')" +
			"and c.status.name not in('"+Constants.SUSPEND+"')" +
			" and c.customerId='" + customerId + "'" +
			" and u.password='" + password + "'" +
			"";
			Query q = session.createQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			if(objects != null){
				user = (User) objects[0];
				long cId = Long.parseLong(IMonitorUtil.convertToString(objects[3]));
				String rName = IMonitorUtil.convertToString(objects[2]);
				int freeMB = Integer.parseInt(IMonitorUtil.convertToString(objects[5]));
				int paidMB = Integer.parseInt(IMonitorUtil.convertToString(objects[6]));
				user.setLastLoginTime(user.getCurrentLoginTime());
				String CustomerMail= IMonitorUtil.convertToString(objects[8]);
				String CustomerMobile=IMonitorUtil.convertToString(objects[7]);
				long feature = Integer.parseInt(IMonitorUtil.convertToString(objects[9]));
				Long SIiD = (Long) objects[10];;
				SystemIntegrator SI= null;
				if(SIiD != null){
				SI = (SystemIntegrator) session.get(SystemIntegrator.class, SIiD);
				
				}
				
				
				
				Customer customer = new Customer();
				customer.setId(cId);
				customer.setCustomerId(customerId);
				customer.setFreeStorageMB(freeMB);
				customer.setPaidStorageMB(paidMB);
				customer.setMobile(CustomerMobile);
				customer.setEmail(CustomerMail);
				customer.setSystemIntegrator(SI);
				customer.setFeaturesEnabled(feature);
				user.setCustomer(customer);
				user.setCurrentLoginTime(new Date());
				int showTips = Integer.parseInt(IMonitorUtil.convertToString(objects[11]));
				user.setShowTips(showTips);
				//user.setLastLoginTime(new Date());
				Status status = IMonitorUtil.getStatuses().get(Constants.ONLINE);
				user.setStatus(status);
				Transaction tx = session.beginTransaction();
				session.update(user);
				tx.commit();
				Role role = new Role();
				role.setName(rName);
				user.setRole(role);
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving user by userid : "
					+ e.getMessage());
			 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return user;
	}
	
	
	public User getUserWithCustomerByUserIdAndUpdatePassword(String userId, String customerId,String password) {
		User user = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select " +
			" u from User as u" +
			" left join u.customer as c" +
			" where u.userId='" + userId + "'" +
			" and c.customerId='" + customerId + "'";
		
			Query q = session.createQuery(query);
			user = (User) q.uniqueResult();
			if(user != null){
				user.setForgetPassword("1");
				user.setPassword(password);
				Transaction tx = session.beginTransaction();
				session.update(user);
				tx.commit();
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving user by userid : "
					+ e.getMessage());
			 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return user;
	}
	
	//bhavya start bug no 986
	
	public User getUserWithCustomerByUserIdAndUpdatePasswordForResetPassword(String userId, String customerId,String password) {
		User user = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select " +
			" u from User as u" +
			" left join u.customer as c" +
			" where u.userId='" + userId + "'" +
			" and c.customerId='" + customerId + "'";
		
			Query q = session.createQuery(query);
			user = (User) q.uniqueResult();
			if(user != null){
				user.setForgetPassword("1");
				user.setPassword(password);
				Transaction tx = session.beginTransaction();
				session.update(user);
				tx.commit();
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving user by userid : "
					+ e.getMessage());
			 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return user;
	}
	//bhavya end
	public User getUserByUserIdAndUpdatePasswordByAdmin(String userId, String customerId,String password) {
		User user = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select " +
			" u from User as u" +
			" left join u.customer as c" +
			" where u.userId='" + userId + "'" +
			" and c.customerId='"+customerId+"'";
			Query q = session.createQuery(query);
			Object objects = (Object) q.uniqueResult();
			if(objects != null){
				user = (User) objects;
				user.setPassword(password);
				Transaction tx = session.beginTransaction();
				session.update(user);
				tx.commit();
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving user by userid : "
					+ e.getMessage());
			 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return user;
	}
	
	public boolean checkUserExistence(String userId) {
		User user = (User) daoManager.getOne("userId", userId, User.class);
		if (user == null) {
			return true;
		}
		return false;
	}

	public int getTotalGateWayCount() {
		int count = daoManager.getCount(User.class);
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> listAskedUsers(String sSearch,String sOrder, int start, int length,long id) {
		String query = "";
		query += "select u.id,u.userId,s.name" + ",u.lastLoginTime from "
				+ "User as u join u.status as s join u.customer as c "
				+ "where u.status=s.id and u.customer=" + id + " and u.role.name in('normaluser')"
				+ " and (u.userId like '%" + sSearch + "%'" 
				+ "or s.name like '%"+sSearch+"%'"+"or u.lastLoginTime like '%"+sSearch+"%' )"
				+ sOrder;
				

		List<Object[]> list = daoManager.listAskedObjects(query, start, length,
				User.class);
		return list;
	}

	public int getUserCountByCustomerId(String sSearch,long id){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(u)" +
        		" from " 
        		+ "User as u join u.status as s join u.customer as c "
				+ "where u.status=s.id and u.customer=" + id + " and u.role.name in('normaluser')"
				+ " and (u.userId like '%" + sSearch + "%'" 
				+ "or s.name like '%"+sSearch+"%'"+"or u.lastLoginTime like '%"+sSearch+"%' )";
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

	public int getTtoalUserCountByCustomerId(long id){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(u)" +
        		" from " 
        		+ "User as u join u.status as s join u.customer as c "
				+ "where u.status=s.id and u.customer=" + id + " and u.role.name in('normaluser')";
				
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

	
	public User getUserById(long id) {
		
		return (User) daoManager.get(id, User.class);
	}

	public User getUserByCustomer(Customer customer) {
		return (User) daoManager.list("customer", customer, User.class);
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsersByCustomer(Customer customer) {
		return (List<User>) daoManager.list("customer", customer, User.class);
	}

	public String isUserExists(String customerId, String userId, String password) {
	
		String result = "failure";
		String query = "select count(u) " + " from User as u"
				+ " left join u.customer as c" + "  where u.customer=c.id"
				+ " and u.userId='" + userId + "' and u.password='" + password + "'"
				+ " and c.customerId='" + customerId + "'";
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
	
	
	public User getAdminUserWithCustomerByUserIdAndUpdateStatus(String userId, String password) {
		User user = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select " +
			" u, s.name, r.name" +
			" from User as u" +
			" left join u.status as s" +
			" left join u.role as r" +
			" where u.userId='" + userId + "'" +
			" and u.password='" + password + "'" +
			" and r.name='" + Constants.ADMIN_USER + "'";
			Query q = session.createQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			if(objects != null){
				user = (User) objects[0];
//				We may need sName to check whether this user is blocked or not !!
				Status status = IMonitorUtil.getStatuses().get(Constants.ONLINE);
				user.setStatus(status);
				Transaction tx = session.beginTransaction();
				session.update(user);
				tx.commit();
				String rName = IMonitorUtil.convertToString(objects[2]);
				Role role = new Role();
				role.setName(rName);
				user.setRole(role);
				user.setCustomer(null);
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving user by userid : "
					+ e.getMessage());
			 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return user;
	}
	@SuppressWarnings("unchecked")
	public List<Object[]> getMainUserNotifications(long id,String roleName) {
		List<Object[]> objects =null;
		String query = "select u.email,u.mobile,u.userId,c.customerId,u.id,c.mobile,c.email from User u left join u.role r "
			+  " left join u.customer as c "
			+"where u.customer = "+id+" and r.name= '"+roleName+"'";
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			 objects = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return objects;
	}
	
	/* ****************************************** sumit start : Notification for MID Login ************************************** */
	/**
	 * @author sumit kumar
	 * @param user
	 * @return
	 */
	public User getUserByUserIdForMID(String customerId, String userId, String password){
		XStream stream=new XStream();
		
		User user = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select " +
			" u, r.id, r.name, c.id" +
			" from User as u" +
			" left join u.status as s" +
			" left join u.role as r" +
			" left join u.customer as c" +
			" where u.userId='" + userId + "'" +
			" and u.password='" + password + "'" +
			" and c.customerId='" + customerId+ "'";
			
			Query q = session.createQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			
			if(objects != null){
				user = (User) objects[0];
				long roleId =(Long) objects[1];
				String roleName = IMonitorUtil.convertToString(objects[2]);
				Role role = new Role();
				role.setId(roleId);
				role.setName(roleName);
				user.setRole(role);
				
				long cId = (Long) objects[3];
				Customer customer = new Customer();
				customer.setId(cId);
				customer.setCustomerId(customerId);
				user.setCustomer(customer);
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving user by userid : "
					+ e.getMessage());
			 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		//LogUtil.info("user"+user);
		return user;
	
	}
	
	/**
	 * @author sumit kumar
	 * @param normalUser
	 * @return
	 */
	public User getMainUserForNormalUser(User normalUser){
		User mainUser = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select u from User as u" +
			" left join u.customer as c" +
			" left join u.role as r" +
			" where c.id='" + normalUser.getCustomer().getId() + "'" +
			" and r.name='" + Constants.MAIN_USER + "'";
			Query q = session.createQuery(query);
			Object objects = (Object) q.uniqueResult();
			if(objects != null){
				mainUser = (User) objects;
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving Main User : "
					+ e.getMessage());
			 
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return mainUser;
	}
	/* ****************************************** sumit end : Notification for MID Login ************************************** */
	
	public Object getMainUserDetailsByMacid(String macid) {
				
		Object[] objects =null;
		String query = "select u.id,u.userId,u.customer,c.customerId,u.password from customer as c,gateway as g,user as u where g.macId='"+macid+"' and c.id=g.customer and u.customer=c.id and u.role=1 limit 1";
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			objects = (Object[])q.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
	
		//LogUtil.info("user details=="+stream.toXML(objects));
		return objects;
	}

	@SuppressWarnings("unchecked")
	public List<subUserDeviceAccess> listDevicesForSubuser(long userId) throws IllegalArgumentException {
		
		XStream stream = new XStream();
		String query = "";
        query += "select s.id, u.id , d.id " 
    			+ " from subUserDeviceAccess as s" 
        		+ " left join s.user as u" 
				+ " left join s.device as d"
                + " where s.user="+userId+" ";
    
        DBConnection dbc = null;
        List<Object[]> objects =null;
        List<subUserDeviceAccess> devicesListOfSubUser = new ArrayList<subUserDeviceAccess>();
       
                
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			objects= q.list();
		//	LogUtil.info("devicelog check"+stream.toXML(objects));
			
			for (Object[] strings : objects) {
				
				User user = new User();
				user.setId(Long.parseLong(IMonitorUtil.convertToString(strings[1])));
				
				Device device = new Device();
				device.setId(Long.parseLong(IMonitorUtil.convertToString(strings[2])));
				
				subUserDeviceAccess userDeviceAccess = new subUserDeviceAccess();
				userDeviceAccess.setId(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
				userDeviceAccess.setUser(user);
				userDeviceAccess.setDevice(device);
				
				devicesListOfSubUser.add(userDeviceAccess);
				
			}
		} catch (Exception ex) {
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		//LogUtil.info("devicelog check"+stream.toXML(devicesListOfSubUser));
		return devicesListOfSubUser;
	
	}
	
	/**
	 * @author sumit kumar
	 * @param userId
	 * @return
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("unchecked")
	public List<subUserScenarioAccess> listScenariosForSubuser(long userId) throws IllegalArgumentException {
		String query = "";
        query += "select s.id, u.id , sc.id " 
    			+ " from subUserScenarioAccess as s" 
        		+ " left join s.user as u" 
				+ " left join s.scenario as sc"
                + " where s.user="+userId+" ";
        DBConnection dbc = null;
        List<Object[]> objects =null;
        List<subUserScenarioAccess> scenarioListOfSubUser = new ArrayList<subUserScenarioAccess>();
                
                
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			objects= q.list();
			
			for (Object[] strings : objects) {
				
				User user = new User();
				user.setId(Long.parseLong(IMonitorUtil.convertToString(strings[1])));
				
				Scenario scenario = new Scenario();
				scenario.setId(Long.parseLong(IMonitorUtil.convertToString(strings[2])));
				
				subUserScenarioAccess userScenarioAccess = new subUserScenarioAccess();
				userScenarioAccess.setId(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
				userScenarioAccess.setUser(user);
				userScenarioAccess.setScenario(scenario);
				
				scenarioListOfSubUser.add(userScenarioAccess);
				
			}
		} catch (Exception ex) {
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return scenarioListOfSubUser;
	}
	
	public List listAskedUserFromAdmin(String sSearch, String sOrder, int start,
			int length) {
		
		String query = "";
		query += " 	select u.id,u.userId,c.customerId,s.name,r.name " 
				+ " from User as u  "
				+ " join u.customer as c "
				+ " left join u.status as s "
				+ " left join u.role as r "
				+ " where u.customer=c.id and u.status=s.id and u.role=r.id and (" + "u.userId like '%"
				+   sSearch + "%' or c.customerId like '%" + sSearch + "%' "
				+ " or s.name like '%" + sSearch + "%' "
				+ " or r.name like '%" + sSearch + "%' ) " + sOrder;
		
		List list = daoManager.listAskedObjects(query, start, length,
				User.class);
		return list;
	}
	
	
	public int getTotalUserCount(String sSearch) {
		XStream xstream =new XStream();
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(u)" + " from "
					+ "User as u join u.status as s "
					+ "where u.status=s.id and (" + "u.userId like '%"
					+ sSearch + "%' or u.email like '%" + sSearch + "%' "
					+ " or u.mobile like '%" + sSearch + "%' "
					+ "or u.lastLoginTime like '%" + sSearch + "%' "
					+ "or s.name like '%" + sSearch + "%' )";
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

	public int getTotalUserCountfromadmin() {
	XStream xstream=new XStream();
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(u)" + " from "
					+ "User as u join u.status as s "
					+" left join u.customer as c "
					+ "where u.status=s.id and u.customer=c.id";
			
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
		//LogUtil.info("hey this is UserManager Query list"+xstream.toXML(count));
		return count;
	}
	
	public User getSupportUserAndUpdateStatus(String userId, String password) {
		User user = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select " +
			" u, s.name, r.name" +
			" from User as u" +
			" left join u.status as s" +
			" left join u.role as r" +
			" where u.userId='" + userId + "'" +
			" and u.password='" + password + "'" +
			" and r.name='" + Constants.SUPPORT_USER + "'";
			Query q = session.createQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			if(objects != null){
				user = (User) objects[0];
//				We may need sName to check whether this user is blocked or not !!
				Status status = IMonitorUtil.getStatuses().get(Constants.ONLINE);
				user.setStatus(status);
				Transaction tx = session.beginTransaction();
				session.update(user);
				tx.commit();
				String rName = IMonitorUtil.convertToString(objects[2]);
				Role role = new Role();
				role.setName(rName);
				user.setRole(role);
				user.setCustomer(null);
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving user by userid : "
					+ e.getMessage());
			 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return user;
	}
	

	/*Added by Naveen
	 * Date: 05-May-2015
	 * To check user login through temporary password
	 */
	
	public User getUserWithCustomerByUserIdAndUpdateStatusAndUpdateLastLoginAndTempPassword(String userId, String customerId, String password) {
		User user = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			String query = "select " +
			" u, s.name, r.name" +
			" ,c.id, c.customerId, c.freeStorageMB, c.paidStorageMB, c.mobile, c.email, c.featuresEnabled, s.id, u.expireDate" +
			" from User as u" +
			" left join u.customer as c" +
			" left join u.status as s" +
			" left join u.role as r" +
			" left join c.systemIntegrator as s"+
			" where u.userId='" + userId + "'" +
			"and u.status.name not in('"+Constants.SUSPEND+"')" +
			"and c.status.name not in('"+Constants.SUSPEND+"')" +
			" and c.customerId='" + customerId + "'" +
			" and u.tempPassword='" + password + "'" +
			"";
			Query q = session.createQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			if(objects != null){
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				user = (User) objects[0];
			    String expireDate = IMonitorUtil.convertToString(objects[11]);
			    Date date = format.parse(expireDate);
			    Date currentDate = new Date();
			   
			    if(currentDate.after(date)){
			    	
			    	
			    	return null;
			    }
				long cId = Long.parseLong(IMonitorUtil.convertToString(objects[3]));
				String rName = IMonitorUtil.convertToString(objects[2]);
				int freeMB = Integer.parseInt(IMonitorUtil.convertToString(objects[5]));
				int paidMB = Integer.parseInt(IMonitorUtil.convertToString(objects[6]));
				user.setLastLoginTime(user.getCurrentLoginTime());
				String CustomerMail= IMonitorUtil.convertToString(objects[8]);
				String CustomerMobile=IMonitorUtil.convertToString(objects[7]);
				long feature = Integer.parseInt(IMonitorUtil.convertToString(objects[9]));
				Long SIiD = (Long) objects[10];
				SystemIntegrator SI= null;
				if(SIiD != null){
				SI = (SystemIntegrator) session.get(SystemIntegrator.class, SIiD);
				
				}
				
				
				
				Customer customer = new Customer();
				customer.setId(cId);
				customer.setCustomerId(customerId);
				customer.setFreeStorageMB(freeMB);
				customer.setPaidStorageMB(paidMB);
				customer.setMobile(CustomerMobile);
				customer.setEmail(CustomerMail);
				customer.setSystemIntegrator(SI);
				customer.setFeaturesEnabled(feature);
				user.setCustomer(customer);
				user.setCurrentLoginTime(new Date());
				//user.setLastLoginTime(new Date());
				Status status = IMonitorUtil.getStatuses().get(Constants.ONLINE);
				user.setStatus(status);
				Transaction tx = session.beginTransaction();
				session.update(user);
				tx.commit();
				Role role = new Role();
				role.setName(rName);
				user.setRole(role);
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving user by userid : "
					+ e.getMessage());
			 e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return user;
	}

public Customer getCustomerByMainUserId(long id){
		
		Customer customer = new Customer();
		DBConnection dbc = null;
		String query = "select u.customer from User as u where u.id="+id+"";
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createQuery(query);
			Object objects = (Object) q.uniqueResult();
			if(objects != null){
				customer = (Customer) objects;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			dbc.closeConnection();
		}
		return customer;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getUserTips(){
		
		DBConnection dbc = new DBConnection();
		List<String> tips = new ArrayList<String>();
		String query="select s.tips from showTips as s";
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			tips = q.list();
		} catch (Exception e) {
			LogUtil.info("could not fetch user tips from DB and message is: "+ e.getMessage());
		}finally{
			dbc.closeConnection();
		}
		
		return tips;
	}
public List<Object[]> getUserIdlastloginapi(String userId){
	
	XStream stream = new XStream();
	DBConnection dbc = null;
	String query = "select u.userId,u.lastloginTime,g.macId,s.name,g.currentmode from user as u left join customer as c on u.customer=c.id left join gateway as g on c.id=g.customer left join status as s on g.status=s.id where userId='"+userId+"'";
	List<Object[]> list=null;
	try {
		
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createSQLQuery(query);
		list=q.list();
		
	}catch (Exception e) {
		// TODO: handle exception
	}finally {
		dbc.closeConnection();
	}
	
	return list;
	
	
	
	
	
	
	
}			

public User getUserDetailByCustomer(Customer customer) {
	return (User) daoManager.getOne("customer", customer, User.class);
}
	
@SuppressWarnings("unchecked")
public List<Object[]> listAskedUsersDeviceActions(String sSearch,String sOrder, int start, int length,long id) {
	String query = "";
	query += "select a.id,a.actionName,d.friendlyName,d.id,f.name" 
			+ " from Action as a join a.device as d join a.functions as f join d.gateWay as g "
			+ "where a.device=d.id and a.scenario is null and g.customer=" + id 
			+ " and (d.friendlyName like '%" + sSearch + "%'" 
			+ "or f.name like '%"+sSearch+"%')"
			+ sOrder;
			

	List<Object[]> list = daoManager.listAskedObjects(query, start, length,
			User.class);
	return list;
}

	

@SuppressWarnings("unchecked")
public List<Object[]> listAskedUsersDeviceActionsWithScenario(String sSearch,String sOrder, int start, int length,long id) {
	String query = "";
	query += "select a.id,a.actionName,s.name ,s.id,f.name" 
			+ " from Action as a join a.scenario as s join a.functions as f join s.gateWay as g "
			+ "where a.scenario=s.id and a.device is null and g.customer=" + id 
			+ " and (s.name like '%" + sSearch + "%'" 
			+ "or f.name like '%"+sSearch+"%')";
			

	List<Object[]> list = daoManager.listAskedObjects(query, start, length,
			User.class);
	return list;
}
public int getTotalActionsCountByCustomerId(long id){
	DBConnection dbc = null;
	int count = 0;
	try {
		String query = "";
		query += "select count(a)" +
    		" from " 
    		+ "Action as a join a.device as d join d.gateWay as g "
			+ "where a.device=d.id and g.customer=" + id + " and d.gateWay=g.id";
			
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

public int getActionCountByCustomerId(String sSearch,long id){
	DBConnection dbc = null;
	int count = 0;
	try {
		String query = "";
		query += "select count(a)" +
    		" from " 
    		+ "Action as a join a.device as d join d.gateWay as g join a.functions as f "
			+ "where a.device=d.id and g.customer=" + id + " "
			+ " and (d.friendlyName like '%" + sSearch + "%'" 
			+ "or f.name like '%"+sSearch+"%')";
		
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

public User getalertUserAndUpdateStatus(String userId, String password) {
	User user = null;
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		String query = "select " +
		" u, s.name, r.name" +
		" from User as u" +
		" left join u.status as s" +
		" left join u.role as r" +
		" where u.userId='" + userId + "'" +
		" and u.password='" + password + "'" +
		" and r.name='" + Constants.ALERT_SERVICE + "'";
		Query q = session.createQuery(query);
		Object[] objects = (Object[]) q.uniqueResult();
		if(objects != null){
			user = (User) objects[0];
//			We may need sName to check whether this user is blocked or not !!
			Status status = IMonitorUtil.getStatuses().get(Constants.ONLINE);
			user.setStatus(status);
			Transaction tx = session.beginTransaction();
			session.update(user);
			tx.commit();
			String rName = IMonitorUtil.convertToString(objects[2]);
			Role role = new Role();
			role.setName(rName);
			user.setRole(role);
			user.setCustomer(null);
		}
	} catch (Exception e) {
		LogUtil.error("Error when retreiving user by userid : "
				+ e.getMessage());
		 e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return user;
}

public User getUserByCustomerId(long customerId) {
	User user = null;
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		String query = "select " +
		" u, s.name, r.name" +
		" ,c.id, c.customerId, c.freeStorageMB, c.paidStorageMB, c.mobile, c.email, s.id" +
		" from User as u" +
		" left join u.customer as c" +
		" left join u.status as s" +
		" left join u.role as r" +
		" left join c.systemIntegrator as s"+
		" where " +
		" u.customer=" + customerId ;
		Query q = session.createQuery(query);
		Object[] objects = (Object[]) q.uniqueResult();
		if(objects != null){
			user = (User) objects[0];
			long cId = Long.parseLong(IMonitorUtil.convertToString(objects[3]));
			String rName = IMonitorUtil.convertToString(objects[2]);
			int freeMB = Integer.parseInt(IMonitorUtil.convertToString(objects[5]));
			int paidMB = Integer.parseInt(IMonitorUtil.convertToString(objects[6]));
		//	user.setLastLoginTime(user.getCurrentLoginTime());
			String CustomerMail= IMonitorUtil.convertToString(objects[8]);
			String CustomerMobile=IMonitorUtil.convertToString(objects[7]);
			
			Long SIiD = (Long) objects[9];;
			SystemIntegrator SI= null;
			if(SIiD != null){
			SI = (SystemIntegrator) session.get(SystemIntegrator.class, SIiD);
			LogUtil.info(SIiD);
			}
			

			Customer customer = new Customer();
			customer.setId(cId);
			customer.setCustomerId(IMonitorUtil.convertToString(objects[4]));
			customer.setFreeStorageMB(freeMB);
			customer.setPaidStorageMB(paidMB);
			customer.setMobile(CustomerMobile);
			customer.setEmail(CustomerMail);
			customer.setSystemIntegrator(SI);
			user.setCustomer(customer);
			user.setCurrentLoginTime(new Date());
			//user.setLastLoginTime(new Date());
			Status status = IMonitorUtil.getStatuses().get(Constants.ONLINE);
			user.setStatus(status);
			Role role = new Role();
			role.setName(rName);
			user.setRole(role);
		}
	} catch (Exception e) {
		LogUtil.error("Error when retreiving user by userid : "
				+ e.getMessage());
		 e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}
	return user;
}





}
