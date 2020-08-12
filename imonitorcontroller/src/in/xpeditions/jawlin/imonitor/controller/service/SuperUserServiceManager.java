package in.xpeditions.jawlin.imonitor.controller.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AdminSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerAndSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.SuperUserManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

public class SuperUserServiceManager
{	
	//SuperUser
		public String saveSuperUser(String xml)
		{
			String result="no";
			XStream stream=new XStream();
			AdminSuperUser superUser=(AdminSuperUser)stream.fromXML(xml);
//			1. Setting the gateways to null.		
			String superUserid = superUser.getSuperUserId();
			String isDuplicate = verifySuperUserDetails(superUserid);
			if (isDuplicate.equals("validUser"))
			{
				SuperUserManager superUserManager=new SuperUserManager();
				boolean saveresult = superUserManager.saveSuperUser(superUser);
				if (saveresult) 
				{
					result="yes";
				}
			} 
			else 
			{
				result="duplicate";
			}
			return result;
		}
		
		//SuperUser
		@SuppressWarnings("unchecked")
		public String verifySuperUserDetails(String superUserid){
			String result = "validUser";
			try {
				SuperUserManager superUserManager=new SuperUserManager();
				List<AdminSuperUser> superUsers = superUserManager.listOfAdminSuperUsers();
				XStream stream=new XStream();
				
				for(AdminSuperUser userFromDb : superUsers){
					
				if(superUserid.equals(userFromDb.getSuperUserId()))
				{
						result = "duplicateuser";
						return result;
				}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				LogUtil.info("Got Exception while feting list: "+e.getMessage());	
			}
			return result;
		}
		
		//SuperUser
		public String listAskedSuperUsers(String xml){
			XStream xStream=new XStream();
			DataTable dataTable =  (DataTable) xStream.fromXML(xml);
			SuperUserManager superUserManager=new SuperUserManager();
			//int count = customerManager.getTotalGateWayCount();
			//dataTable.setTotalRows(count);
			String sSearch = dataTable.getsSearch();
			String[] cols = { "s.superUserId"};
			String sOrder = "";
			int col = Integer.parseInt(dataTable.getiSortCol_0());
			  String colName = cols[col];
			  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
			List<?> list = superUserManager.listAskedSuperUsers(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength());
			dataTable.setResults(list);
			int displayRow = superUserManager.getTotalSuperUsersCount(sSearch);
			dataTable.setTotalDispalyRows(displayRow);
			String valueInXml = xStream.toXML(dataTable);
			return valueInXml;
		}
		
		//Get list of superusers
		public String listSuperUsers() {
			String xml = "";
			XStream xStream = new XStream();
			SuperUserManager superUserManager=new SuperUserManager();
			List<AdminSuperUser> superUsers=superUserManager.listSuperUsers();
			xml = xStream.toXML(superUsers);
			return xml;
		}
		
		public String saveSuperUserToCustomer(String customerId,String superUserid)
		{
			String result = "failure";
			try
			{
					XStream stream=new XStream();
					Long custId = (Long) stream.fromXML(customerId);
					Long superId = (Long) stream.fromXML(superUserid);
					CustomerManager customerManager=new CustomerManager();
					Customer custFromDb = customerManager.getCustomerById(custId);
					SuperUserManager superUserManager=new SuperUserManager();
					AdminSuperUser superUser=superUserManager.getSuperUserById(superId);
					DaoManager daoManager=new DaoManager();
					CustomerAndSuperUser customerAndSuperUser= superUserManager.getCustomerAndSuperUserByCustomerId(custId);
					if (customerAndSuperUser!=null) 
					{
						customerAndSuperUser.setSuperUser(superUser);
						boolean res = daoManager.update(customerAndSuperUser);
						result="updatesuccess";
					}
					else
					{
						CustomerAndSuperUser user=new CustomerAndSuperUser();
						user.setCustomer(custFromDb);
						user.setSuperUser(superUser);
						boolean res =daoManager.save(user);
						result="success";
					}
	
			}
			catch (Exception e)
			{
				e.printStackTrace();
				LogUtil.info("exrror : " +e.getMessage());
			}
			return result;
		}
		
		public String getSuperUserAndCustomer(String customerId)
		{
			XStream stream=new XStream();
			CustomerAndSuperUser customerAndSuperUser=null;
			try
			{
					Long custId = (Long) stream.fromXML(customerId);
					SuperUserManager superUserManager=new SuperUserManager();
					customerAndSuperUser= superUserManager.getCustomerAndSuperUserByCustomerId(custId);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				LogUtil.info("exrror : " +e.getMessage());
			}
			
			return stream.toXML(customerAndSuperUser);
		}
		
		public String verifySuperUserLogin(String superUserXml){
			XStream stream=new XStream();
			String result = "failure";
			try {
				AdminSuperUser superUser=(AdminSuperUser) stream.fromXML(superUserXml);
				SuperUserManager superUserManager=new SuperUserManager();
				 result  = superUserManager.isSuperUserExists(superUser.getSuperUserId(), superUser.getPassword());
			} catch (Exception e)
			{
				e.printStackTrace();
				LogUtil.info("Got Exception while verifySuperUserLogin: "+e.getMessage());	
			}
			return result;
		}
		
		public String listOfCustomersForSuperUser(String superUserXml){
			XStream stream=new XStream();
			String result = "failure";
			List<Customer> customerList = null;
			try {
				AdminSuperUser superUser=(AdminSuperUser) stream.fromXML(superUserXml);
				SuperUserManager superUserManager=new SuperUserManager();
				
				AdminSuperUser adminfromDb=superUserManager.getsuperUserBySuperUserId(superUser.getSuperUserId());
				 customerList  = superUserManager.listOfCustomersForSuperUser(adminfromDb.getId());
			} catch (Exception e)
			{
				e.printStackTrace();
				LogUtil.info("Got Exception while verifySuperUserLogin: "+e.getMessage());	
			}
			return stream.toXML(customerList);
		}
		
		public String getDeviceDetailsForSingleSuperUser(String customerId){
			XStream stream=new XStream();
			String result = "failure";
			List<GateWay> gateWays = null;
			try {
				CustomerManager customerManager=new CustomerManager();
				gateWays = customerManager.getGateWaysAndDeviceOfCustomerByCustomerId(customerId);
			} catch (Exception e)
			{
				e.printStackTrace();
				LogUtil.info("Got Exception while verifySuperUserLogin: "+e.getMessage());	
			}
			return stream.toXML(gateWays);
		}
		
		public String getDeviceDetailsForMultipleSuperUser(String superUserId){
			XStream stream=new XStream();
			String result = "failure";
			List<GateWay> gateWays = null;
			List<GateWay> fullList = new ArrayList<GateWay>();
			try {
				
				SuperUserManager superUserManager=new SuperUserManager();
				CustomerManager customerManager=new CustomerManager();
				AdminSuperUser adminSuperUser=superUserManager.getsuperUserBySuperUserId(superUserId);
				
				List<Customer> customerIdList= superUserManager.listOfCustomersForSuperUser(adminSuperUser.getId());
				for (Customer cust : customerIdList)
				{
					
					gateWays = customerManager.getGateWaysAndDeviceOfCustomerByCustomerId(cust.getCustomerId());
					fullList.addAll(gateWays);
				}

			} catch (Exception e)
			{
				e.printStackTrace();
				LogUtil.info("Got Exception while verifySuperUserLogin: "+e.getMessage());	
			}
			return stream.toXML(fullList);
		}
		
		public String getUserFromCustomerId(String customerId)
		{
			
			XStream stream=new XStream();
			String result = "failure";
			Customer customer = null;
			User user = null;
			try {
				CustomerManager customerManager= new CustomerManager();
				customer= customerManager.getCustomerByCustomerId(customerId);
				
				UserManager userManager=new UserManager();
				user = userManager.getUserByCustomerId(customer.getId());
			} catch (Exception e)
			{
				e.printStackTrace();
				LogUtil.info("Got Exception while getUserFromCustomerId: "+e.getMessage());	
			}
			return stream.toXML(user);
		}
		
		public String getSuperUserById(String xml)
		{
			XStream stream=new XStream();
			AdminSuperUser superUser = (AdminSuperUser) stream.fromXML(xml);
			SuperUserManager superUserManager=new SuperUserManager();
			AdminSuperUser superUserFromDb =  superUserManager.getSuperUserById(superUser.getId());
			return stream.toXML(superUserFromDb);
		}
		
		public String saveEditedSuperUserDetails(String xml)
		{
			String response = "failure";
			XStream stream=new XStream();
			AdminSuperUser superUser = (AdminSuperUser) stream.fromXML(xml);
			SuperUserManager superUserManager=new SuperUserManager();
			AdminSuperUser superUserFromDb =  superUserManager.getSuperUserById(superUser.getId());
			String password = superUser.getPassword();
			String hashPassword = IMonitorUtil.getHashPassword(password);
			superUserFromDb.setPassword(hashPassword);
			DaoManager daoManager=new DaoManager();
			boolean res= daoManager.update(superUserFromDb);
			if (res)
			{
				response="success";
			}
			
			return response;
		}
		
		public String deleteAdminSuperUser(String xml)
		{
			String response = "failure";
			XStream stream=new XStream();
			AdminSuperUser superUser = (AdminSuperUser) stream.fromXML(xml);
			SuperUserManager superUserManager=new SuperUserManager();
			boolean result1 =  superUserManager.deleteSuperUserMappingToCustomerById(superUser.getId());
			boolean result2 =  superUserManager.deleteAdminSuperUserEntityById(superUser.getId());
			if (result2)
			{
				response="success";
			}
			
			return response;
		}
		
		public String superUserMappingVerification(String Customerxml,String superUserXml)
		{
			String response = "";
			XStream stream=new XStream();
			Customer customer = (Customer) stream.fromXML(Customerxml);
			AdminSuperUser superUser = (AdminSuperUser) stream.fromXML(superUserXml);
			SuperUserManager superUserManager=new SuperUserManager();
			CustomerAndSuperUser customerAndSuperUser = superUserManager.getCustomerAndSuperUserByCustomerId(customer.getId());
			//LogUtil.info("customerAndSuperUser : "+ stream.toXML(customerAndSuperUser));
			AdminSuperUser superUserFromDb = superUserManager.getSuperUserById(customerAndSuperUser.getSuperUser().getId());
			//LogUtil.info("superUserFromDb: "+ stream.toXML(superUserFromDb));
			
			String hashPassword = IMonitorUtil.getHashPassword(superUser.getOldPassword());
			if (hashPassword.equals(superUserFromDb.getPassword())) 
			{
				
				String newhashPassword = IMonitorUtil.getHashPassword(superUser.getPassword());
				superUserFromDb.setPassword(newhashPassword);
				DaoManager daoManager=new DaoManager();
				boolean res= daoManager.update(superUserFromDb);
				
				if (res)
				{
					response="success";
				} else
				{
					response="updateFailed";
				}
				
				return response;
			}
			else
			{
			
				return response;
			}
			
			
			
		}
		
		public String checkMappingOfSuperUserToCustomer(String Customerxml)
		{
			String response = "present";
			XStream stream=new XStream();
			String cid = (String) stream.fromXML(Customerxml);
			long id = Long.parseLong(cid);
			SuperUserManager superUserManager=new SuperUserManager();
			CustomerAndSuperUser customerAndSuperUser = superUserManager.getCustomerAndSuperUserByCustomerId(id);
			//LogUtil.info("customerAndSuperUser : "+ stream.toXML(customerAndSuperUser));
			
			if (customerAndSuperUser==null)
			{
				response="NotPresent";
				return response;
			}
			return response;
		}
		
		
		
}
