package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Action;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Functions;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyObjects;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;


public class AddNewAction extends ActionSupport
{
	
	private static List<Functions> functions;
	private Action action;
	private String actionName;
	private User users;
	private GateWay gateWay;
	private List<Device> devices;
	private Device device;
	private String aName;
	private DataTable dataTable;
	private List<Scenario> scenarios;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String toAddNewAction() throws Exception 
	{
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String toAddAction() throws Exception 
	{	
		//Functions List
		String serviceName = "deviceService";
		String method = "listAllFunctions";
		XStream stream = new XStream();
		String functionList = IMonitorUtil.sendToController(serviceName, method);
		List<Functions> functions = (List<Functions>) stream.fromXML(functionList);
		
	
		users = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		Customer customer = users.getCustomer();
		String customerIdXml = stream.toXML(customer);
		String resultGateWay = IMonitorUtil.sendToController("deviceService", "getGateWay",customerIdXml);
		GateWay gateway = (GateWay) stream.fromXML(resultGateWay);
		this.setGateWay(gateway);
		this.setFunctions(functions);	
		action=new Action();
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getDeviceListByAction() throws Exception 
	{	
		
		XStream stream = new XStream();
		String actionCategoryXml = stream.toXML(actionName);
		
		long gatewayid=this.gateWay.getId();
		String gatewayIdXml = stream.toXML(gatewayid);
		
		String serviceName = "deviceService";
		String method = "listDeviceForActions";
		String deviceListXml = IMonitorUtil.sendToController(serviceName, method, actionCategoryXml,gatewayIdXml);
		this.devices=(List<Device>) stream.fromXML(deviceListXml);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String getNonHmdDeviceListByAction() throws Exception 
	{	
		
		XStream stream = new XStream();
		String actionCategoryXml = stream.toXML(actionName);
		
		long gatewayid=this.gateWay.getId();
		String gatewayIdXml = stream.toXML(gatewayid);
		
		String serviceName = "deviceService";
		String method = "listNonHmdDeviceForActions";
		String deviceListXml = IMonitorUtil.sendToController(serviceName, method, actionCategoryXml,gatewayIdXml);
		this.devices=(List<Device>) stream.fromXML(deviceListXml);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String getScenarioListByAction() throws Exception 
	{	
		
		XStream stream = new XStream();
		//String actionNameXml = stream.toXML(actionName);
		long gatewayid=this.gateWay.getId();
		String gatewayIdXml = stream.toXML(gatewayid);
		String serviceName = "deviceService";
		String method = "listScenariosForActions";
		String scenarioListXml = IMonitorUtil.sendToController(serviceName, method,gatewayIdXml);
		this.scenarios=(List<Scenario>) stream.fromXML(scenarioListXml);
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String toSaveAction() throws Exception 
	{	
		
		XStream stream = new XStream();
		
		GateWay gateWay1=this.getGateWay();
		this.action.setGateWay(gateWay1);
		String category=actionName;
		String categoryXml = stream.toXML(category);
		String aNamexml = stream.toXML(aName);
		
		if (aName.equals("2_WAY"))
		{
			categoryXml = stream.toXML("2_WAY");
		}
		
		//SceneCOntroller Panic
		else if (aName.equals("PANIC_SITUATION"))
		{
			categoryXml = stream.toXML("PANIC_SITUATION");
		}
		
		//getting the Function Id from database
		String serviceName = "deviceService";
		String method = "getFunctionIdBasedOnNameAndCategory";
		String functionIdXml = IMonitorUtil.sendToController(serviceName, method, categoryXml,aNamexml);
		long fid=(Long) stream.fromXML(functionIdXml);
		Functions functions =new Functions();
		functions.setId(fid);
		functions.setName(aName);
		this.action.setFunctions(functions);
		String actionxml = stream.toXML(action);
		//String result = "";
		String message = "";
		String resultXml = IMonitorUtil.sendToController("deviceService", "saveAction", actionxml);
		String result = (String) stream.fromXML(resultXml);
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.configureaction.success.0001");
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		else if (result.equalsIgnoreCase("duplicateName"))
		{
			
			message = "Action already exist.Please select another Name";
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		
		
		else{
			
			if(result.equalsIgnoreCase(Constants.DB_FAILURE))
			{
				//message = "msg.imonitor.configureaction.0001";
				message="Failure:An internal error occurred while saving Action. Not saved. Try again.";
			}
			else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE))
			{
				//message = "msg.imonitor.configureaction.0002";
				message="Failure:Your Gateway is unable to save Action configuration for your device.";
			}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY))
			{
				//message = "msg.imonitor.configureaction.0003";
				message="Failure:Your gateway did not respond back. Action  not saved.";
			}
			else
			{
				//message = "msg.imonitor.configureaction.0004";
				message="Failure:General error occurred while trying to save Action configuration.";
			}
			ActionContext.getContext().getSession().put("message", message);
			
			
			return ERROR;
		}
	}
	
	public String tolistAskedDeviceActions() throws Exception{
		
		XStream stream = new XStream();
		String xmlDataTable = stream.toXML(this.dataTable);
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String customerIdXml = stream.toXML(user.getCustomer().getId());
		String serviceName = "userService";
		String method = "listAskedUserDeviceActions";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlDataTable, customerIdXml);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
		
	}
	
	public String toDeleteDeviceActions() throws Exception{
		
		XStream stream = new XStream();
		String message="";
		String actionxml = stream.toXML(action);
		String serviceName = "deviceService";
		String method = "deleteDeviceActions";
		String result = IMonitorUtil.sendToController(serviceName, method, actionxml);
		
		if (result.equals("Success")) 
		{
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.configureaction.deletesuccess.0001");
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		} else 
		{
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.configureaction.deletefailed.0001");
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String toEditDeviceActions() throws Exception
	{
		String serviceName = "deviceService";
		String method = "listAllFunctions";
		XStream stream = new XStream();
		String result = IMonitorUtil.sendToController(serviceName, method);
		List<Functions> functions = (List<Functions>) stream.fromXML(result);
		users = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		Customer customer = users.getCustomer();
		String customerIdXml = stream.toXML(customer);
		String resultGateWay = IMonitorUtil.sendToController("deviceService", "getGateWay",customerIdXml);
		GateWay gateway = (GateWay) stream.fromXML(resultGateWay);
		this.setGateWay(gateway);
		this.setFunctions(functions);	
		
		String actionidxml = stream.toXML(this.action.getId());
		result = IMonitorUtil.sendToController("deviceService", "getDeviceAction",actionidxml);
		action = (Action)stream.fromXML(result);
		return SUCCESS;
	}
	
	public String editDeviceActions() throws Exception
	{
		XStream stream = new XStream();
		String macId=this.getGateWay().getMacId();
		GateWay gateWay1=this.getGateWay();
		this.action.setGateWay(gateWay1);
		String category=actionName;
		String categoryXml = stream.toXML(category);
		String aNamexml = stream.toXML(aName);
		if (aName.equals("2_WAY"))
		{
			
			categoryXml = stream.toXML("2_WAY");
		}
		
		//SceneCOntroller Panic
				else if (aName.equals("PANIC_SITUATION"))
				{
					
					categoryXml = stream.toXML("PANIC_SITUATION");
				}
		
		//getting the Function Id from database
		String serviceName = "deviceService";
		String method = "getFunctionIdBasedOnNameAndCategory";
		String functionIdXml = IMonitorUtil.sendToController(serviceName, method, categoryXml,aNamexml);
		long fid=(Long) stream.fromXML(functionIdXml);
		Functions functions =new Functions();
		functions.setId(fid);
		functions.setName(aName);
		this.action.setFunctions(functions);
		String actionxml = stream.toXML(action);
		//String result = "";
		String message = "";
		String resultXml = IMonitorUtil.sendToController("deviceService", "editAction", actionxml);
		String result = (String) stream.fromXML(resultXml);
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){
			
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.configureactionedit.success.0001");
			ActionContext.getContext().getSession().put("message", message);
			
			return SUCCESS;
		}
		else{
			
			if(result.equalsIgnoreCase(Constants.DB_FAILURE))
			{
				message = "msg.imonitor.configureaction.0001";
			}
			else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE))
			{
				message = "msg.imonitor.configureaction.0002";
			}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY))
			{
				message = "msg.imonitor.configureaction.0003";
			}
			else
			{
				message = "msg.imonitor.configureaction.0004";
			}
			ActionContext.getContext().getSession().put("message", message);
			
			return ERROR;
		}
	}
	
	//3 gateways proj
	public String getGatewayId() throws Exception
	{
		XStream stream=new XStream();
		String macId= this.getGateWay().getMacId();
		
		String resultGateWay = IMonitorUtil.sendToController("deviceService", "getGateWayId",macId);
		GateWay gateway = (GateWay) stream.fromXML(resultGateWay);
		
		this.setGateWay(gateway);
		return SUCCESS;
	}
	
	public List<Functions> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Functions> functions) {
		this.functions = functions;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public GateWay getGateWay() {
		return gateWay;
	}

	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}


	

	


	
}
