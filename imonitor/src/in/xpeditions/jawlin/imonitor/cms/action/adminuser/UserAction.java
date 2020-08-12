/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.cms.communicator.IMonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Role;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlertByUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.general.DataTable;

import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class UserAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2042756356309463050L;
	
	private User user;
	private List<Role> roles;
	private List<Status> statuses;
	private List<Customer> customers;
	private DataTable dataTable;
	
	@SuppressWarnings("unchecked")
	public String toAddUser() throws Exception {
		
//		1. Listing statuses
		String address = IMonitorControllerCommunicator.getControllerAddress();
		String port = IMonitorControllerCommunicator.getControllerPort();
		String endpoint = "http://" + address + ":" + port
		+ "/imonitorcontroller/services/statusService";
		Service service = new Service();
		Call call = (Call) service .createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName("listStatuses"));
		String valueInXml = (String) call.invoke(new Object[] {});
		XStream stream = new XStream();
		this.statuses = (List<Status>) stream .fromXML(valueInXml); 
		
//		2. Filling Roles.
		endpoint = "http://" + address + ":" + port
		+ "/imonitorcontroller/services/roleService";
		call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName("listRoles"));
		valueInXml = (String) call.invoke(new Object[] {});
		this.roles = (List<Role>) stream.fromXML(valueInXml);
	
//		3. Filling the customerId
		endpoint = "http://" + address + ":" + port
		+ "/imonitorcontroller/services/customerService";
		call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName("listCustomers"));
		valueInXml = (String) call.invoke(new Object[] {});
		this.customers = (List<Customer>) stream.fromXML(valueInXml);
		return SUCCESS;
	}
	
	public String addUser() throws Exception {
		if(user.getCustomer()==null){}
		else
			{
			XStream stream = new XStream();
			String hashPassword = IMonitorUtil.getHashPassword(this.user.getPassword());
			this.user.setPassword(hashPassword);
			String xml = stream.toXML(this.user);
			String serviceName = "userService";
			String method = "attatchCustomerAndSaveUser";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xml);
			
			String result = (String) stream.fromXML(resultXml);
			if(result.equals("no")){
				ActionContext.getContext().getSession().put("message", "Failure~A user by the specified User Id alreary exist,please choose another one");
			}
			else if(result.equals("yes")){
				ActionContext.getContext().getSession().put("message", "Sucess~User with Id " + this.user.getUserId() + " is saved successfully!");
			}
			else if(result.equals("false")){
				ActionContext.getContext().getSession().put("message", "Failure~Specified customer with Id " + this.user.getCustomer().getCustomerId() + " does not exist,please enter valid customer");
			}
			else if(result.equals("MainUserExist"))
			{
				ActionContext.getContext().getSession().put("message", "Failure~Main user Exists Already");
			}
			else if(result.equals("imvg_failure"))
			{
				ActionContext.getContext().getSession().put("message", "Failure~Gateway is not able to add user.Please Check the connectivity");
			}
			else if(result.equals("Actionfailure"))
			{
				ActionContext.getContext().getSession().put("message", "Failure~Not able to add user "+ this.user.getUserId() + "");
			}
			else 
			{
				ActionContext.getContext().getSession().put("message", "Failure~Not able to add user "+ this.user.getUserId() + "");
			}
			}
		return SUCCESS;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	public String listUsers() throws Exception {
		return SUCCESS;
	}
	
	public String listAskedUsers() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String serviceName = "userService";
		String method = "listAskedUsersfromAdmin";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	

	public String deleteUser() throws Exception {
		long id = user.getId();
		XStream stream = new XStream();
		String xmlString = stream.toXML(id);
		String serviceName = "userService";
		String method = "deleteUser";
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	}
	
	
	
	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

}
