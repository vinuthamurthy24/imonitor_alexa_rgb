package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xerces.internal.impl.xs.models.XSAllCM;
import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AdminSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerAndSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerPasswordHintQuestionAnswer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

public class SuperUserAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	private Customer customer = new Customer();
	private DataTable dataTable;
	private CustomerAndSuperUser customerAndSuperUser;
	private User user;
	private AdminSuperUser superuser;
    private List<AdminSuperUser>  adminSuperUsers;
	private List<Customer> customersList;
	private String superuserName;
	private Map<String, Object> session;
	private String reqLocale = null;
	
	
	public String getReqLocale() {
		return reqLocale;
	}

	public void setReqLocale(String reqLocale) {
		this.reqLocale = reqLocale;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
  public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	//SuperUser
  	@SuppressWarnings("unchecked")
  	public String toAddSuperUser() throws Exception 
  	{
  		String result = SUCCESS;
  		return result;
  	}
  	
  	//SuperUser
  	public String saveSuperUser() throws Exception
  	{
  		XStream stream = new XStream();
  			String hashPassword = IMonitorUtil.getHashPassword(this.superuser.getPassword());
  			this.superuser.setPassword(hashPassword);
  			String xml = stream.toXML(this.superuser);
  			String serviceName = "superUserService";
  			String method = "saveSuperUser";
  			
  			String result = IMonitorUtil.sendToController(serviceName, method, xml);
  			if(result.equals("duplicate")){
  				ActionContext.getContext().getSession().put("message", "Failure~A user by the specified User Id already exists .Please enter other name");
  			}
  			else if(result.equals("yes")){
  				ActionContext.getContext().getSession().put("message", "Sucess~User with Id " + this.superuser.getSuperUserId() + " is saved successfully!");
  			}
  			else if(result.equals("no")){
  				ActionContext.getContext().getSession().put("message", "Sucess~User with Id " + this.superuser.getSuperUserId() + " is saved successfully!");
  			}		
  		return SUCCESS;
  	}
  	
  	
  	public String tolistAskedSuperUsers() throws Exception {
  		return SUCCESS;
  	}
  	
  	public String listAskedSuperUsers() throws Exception {
  		XStream stream = new XStream();
  		String xmlString = stream.toXML(this.dataTable);
  		String serviceName = "superUserService";	
  		String method = "listAskedSuperUsers";
  		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
  		this.dataTable = (DataTable) stream.fromXML(resultXml);
  		return SUCCESS;
  	}
  	
  	@SuppressWarnings("unchecked")
  	public String toMapSuperUser() throws Exception 
  	{
  		long id = customer.getId();
  		XStream stream = new XStream();
  		String valueInXml = stream.toXML(id);
  		String serviceName = "customerService";
  		String method = "getCustomerById";
  		String xml = IMonitorUtil.sendToController(serviceName,
  				method, valueInXml);
  		this.customer = (Customer) stream.fromXML(xml);
  		
  		String serviceName1 = "superUserService";
  		String method1 = "getSuperUserAndCustomer";
  		String xml1 = IMonitorUtil.sendToController(serviceName1,
  				method1, valueInXml);
  		this.customerAndSuperUser = (CustomerAndSuperUser) stream.fromXML(xml1);
  		
  		serviceName = "superUserService";
  		method = "listSuperUsers";
  		valueInXml = IMonitorUtil.sendToController(serviceName, method);
  		this.adminSuperUsers = (List<AdminSuperUser>) stream.fromXML(valueInXml);
  		return SUCCESS;
  	}
  	
  	public String saveSuperUserToCustomer() throws Exception
  	{
  		XStream stream = new XStream();
  		String serviceName = "superUserService";
  		String method = "saveSuperUserToCustomer";
  		String customerId = stream.toXML(this.customer.getId());
  		String superUserId = stream.toXML(this.customerAndSuperUser.getSuperUser().getId());
  		String result = IMonitorUtil.sendToController(serviceName,
  				method,customerId,superUserId);
  		
  		if(result.equals("success")){
  			ActionContext.getContext().getSession().put("message", "SuperUser was saved successfully");
  		}
  		else if(result.equals("updatesuccess")){
  			ActionContext.getContext().getSession().put("message", "SuperUser was updated successfully");
  		}
  		else 
  		{
  			ActionContext.getContext().getSession().put("message", "Failure, SuperUser was not saved/updated");
  		}
  		return SUCCESS;
  	}
  	
  	public String verifySuperUserLogin() throws Exception
  	{
  		XStream stream = new XStream();
  		String hashPassword = IMonitorUtil.getHashPassword(this.user.getPassword());
  		AdminSuperUser adminSuperUser=new AdminSuperUser();
  		adminSuperUser.setSuperUserId(this.user.getUserId());
  		adminSuperUser.setPassword(hashPassword);
  		String userXml = stream.toXML(adminSuperUser);
  		
  		String serviceName = "superUserService";
  		String method = "verifySuperUserLogin";
  		String result = IMonitorUtil.sendToController(serviceName,
  				method,userXml);
  		this.superuser = adminSuperUser;
  		if (result.equals("success")) 
  		{
  			
  			 serviceName = "superUserService";
  	  		 method = "listOfCustomersForSuperUser";
  	  		String listxmll = IMonitorUtil.sendToController(serviceName,
  	  				method,userXml);
  	  		this.customersList= (List<Customer>) stream.fromXML(listxmll);
  	  	
  			ActionContext.getContext().getSession().put(Constants.MESSAGE,"SuperUser login Success");
  			return SUCCESS;
		} 
  		else
		{
  			ActionContext.getContext().getSession().put(Constants.MESSAGE,"SuperUser login fail");
  			return ERROR;
		}
  		
  	}
  	
  	public String getDeviceDetailsForSingleSuperUser() throws Exception
  	{
  		XStream stream=new XStream();
  		
  	/*	String serviceName = "superUserService";
	  	String	 method = "getDeviceDetailsForSingleSuperUser";
	  	String listxmll = IMonitorUtil.sendToController(serviceName,method,this.superuserName);
	  	List<GateWay> gateWays = (List<GateWay>) stream.fromXML(listxmll);
  		LogUtil.info("Device details Single Customer: "+ listxmll);*/
  		try
  		{
  			String serviceName = "superUserService";
  		  	String	 method = "getUserFromCustomerId";
  		  	String userXMl = IMonitorUtil.sendToController(serviceName,method,this.superuserName);
  		  	
  		  	
  		  	Locale locale = new Locale("en", "US");
  			if(reqLocale == null)
  			{
  				locale = new Locale("en", "US");
  			}
  			else
  			{
  				if(reqLocale.contains("_"))
  				{
  					String t[] = reqLocale.split("_");
  					locale = new Locale(t[0], t[1]);
  				}
  				else
  					locale = new Locale(reqLocale);
  			}
  			ActionContext.getContext().setLocale(locale);
  			ActionContext.getContext().getSession().put(Constants.LOCALE,locale);
  		  	
  	  		User user1 = (User) stream.fromXML(userXMl);
  	  		ActionContext.getContext().getSession().put(Constants.USER, user1);
  			session.put("loginId", user1.getUserId());
  			session.put("userlogin",2);
		} catch (Exception e)
  		{
			e.printStackTrace();
			LogUtil.info("Error : "+ e.getMessage());
		}
  		
  		return SUCCESS;
  	}
  	
  	public String getDeviceDetailsForMultipleSuperUser() throws Exception
  	{
  		XStream stream=new XStream();
  		String serviceName = "superUserService";
	  	String	 method = "getDeviceDetailsForMultipleSuperUser";
	  	String listxmll = IMonitorUtil.sendToController(serviceName,method,this.superuserName);
	  	List<GateWay> gateWays = (List<GateWay>) stream.fromXML(listxmll);
  		return SUCCESS;
  	}
  	
  	public String toEditAdminSuperUser() throws Exception
  	{
  		XStream stream=new XStream();
  		String Xml =stream.toXML(this.superuser);
  		
  		//Fetching superUSer object From Db
  		String serviceName = "superUserService";
	  	String	method = "getSuperUserById";
	  	String superUserxml = IMonitorUtil.sendToController(serviceName,method,Xml);
	  	this.superuser = (AdminSuperUser) stream.fromXML(superUserxml);
	  	
	  	//Getting list of all customers From Db mapped to superUser
	  	
	  	String serviceName1 = "superUserService";
	  	String	method1 = "listOfCustomersForSuperUser";
	  	String listxml = IMonitorUtil.sendToController(serviceName1,method1,superUserxml);
	  	this.customersList = (List<Customer>) stream.fromXML(listxml);
	  
  		return SUCCESS;
  	}
  	
  	public String saveEditedSuperUserDetails() throws Exception
  	{
  		XStream stream=new XStream();
  		String Xml =stream.toXML(this.superuser);
  		String serviceName = "superUserService";
	  	String	method = "saveEditedSuperUserDetails";
	  	String superUserxml = IMonitorUtil.sendToController(serviceName,method,Xml);
	  	if(superUserxml.equals("success")){
  			ActionContext.getContext().getSession().put("message", "Update Success");
  		}
  		else
  		{
  			ActionContext.getContext().getSession().put("message", "Failure : Update Failure");
  		}
  		return SUCCESS;
  	}
  	
  	public String deleteAdminSuperUser() throws Exception
  	{
  		XStream stream=new XStream();
  		String Xml =stream.toXML(this.superuser);
  		String serviceName = "superUserService";
	  	String	method = "deleteAdminSuperUser";
	  	String superUserxml = IMonitorUtil.sendToController(serviceName,method,Xml);
	  	if(superUserxml.equals("success")){
  			ActionContext.getContext().getSession().put("message", "SuperUser deleted successfully.");
  		}
  		else
  		{
  			ActionContext.getContext().getSession().put("message", "Delete operation failed");
  		}
  		//Delete from customerAndSuperUSer table and then delete from superuser table
  		return SUCCESS;
  	}
  	

    public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<AdminSuperUser> getAdminSuperUsers() {
		return adminSuperUsers;
	}
	public void setAdminSuperUsers(List<AdminSuperUser> adminSuperUsers) {
		this.adminSuperUsers = adminSuperUsers;
	}
	public AdminSuperUser getSuperuser() {
		return superuser;
	}
	public void setSuperuser(AdminSuperUser superuser) {
		this.superuser = superuser;
	}

	public CustomerAndSuperUser getCustomerAndSuperUser() {
		return customerAndSuperUser;
	}

	public void setCustomerAndSuperUser(CustomerAndSuperUser customerAndSuperUser) {
		this.customerAndSuperUser = customerAndSuperUser;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Customer> getCustomersList() {
		return customersList;
	}

	public void setCustomersList(List<Customer> customersList) {
		this.customersList = customersList;
	}

	public String getSuperuserName() {
		return superuserName;
	}

	public void setSuperuserName(String superuserName) {
		this.superuserName = superuserName;
	}
}
