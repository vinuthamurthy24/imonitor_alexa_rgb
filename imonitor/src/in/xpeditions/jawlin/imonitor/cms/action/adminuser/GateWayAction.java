/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AdminSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWayType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ZingGatewayList;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorProperties;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class GateWayAction extends ActionSupport {
	/**
	 * Asmodeus
	 */
	private static final long serialVersionUID = 1L;
	private GateWay gateWay;
	private List<Make> makes;
	private List<GateWayType> gateWayTypes;
	private List<Agent> agents;
	private List<Status> statuses;
	private String selectedValue;
	private DataTable dataTable;
	private String dateFormat;
	private String ServerIp;
	private String Port;
	private ZingGatewayList zingGatewayList;

	public String addGateWay() throws Exception {
		XStream stream = new XStream();
		String capL = gateWay.getMacId();
		String lowL = capL.toLowerCase();
		gateWay.setMacId(lowL);
		String xml = stream.toXML(gateWay);
		String serviceName = "gateWayService";
		String method = "saveGateWay";
		String resultInXml = IMonitorUtil.sendToController(serviceName, method, xml);
		String result = (String) stream.fromXML(resultInXml);
		if(result.equals("yes")){
			ActionContext.getContext().getSession().put("message", "Sucess~Successfully saved");
		}
		else if(
			result.equals("already exist")){ActionContext.getContext().getSession().put("message", "Failure~Gateway already exists");
		}
		return SUCCESS;
	}

	@SuppressWarnings( { "unchecked" })
	public String toAddGateWay() throws Exception {
		// 1. Listing makes
//		String serviceName = "makeService";
//		String method = "listMakes";
//		String valueInXml = IMonitorUtil.sendToController(serviceName, method);
		XStream stream = new XStream();
//		this.makes = (List<Make>) stream.fromXML(valueInXml);

		// 2. Listing gatewaytypes
		String serviceName = "gateWayTypeService";
		String method = "listGateWayTypes";
		String valueInXml = IMonitorUtil.sendToController(serviceName, method);
		this.gateWayTypes = (List<GateWayType>) stream.fromXML(valueInXml);

		// 3. Listing statuses
		serviceName = "statusService";
		method = "listStatuses";
		valueInXml = IMonitorUtil.sendToController(serviceName, method);
		this.statuses = (List<Status>) stream.fromXML(valueInXml);

		// 3. Listing agents
		serviceName = "agentService";
		method = "listAgents";
		valueInXml = IMonitorUtil.sendToController(serviceName, method);
		this.agents = (List<Agent>) stream.fromXML(valueInXml);

		return SUCCESS;
	}

	public String getGateWayByMacId() throws Exception {
		XStream stream = new XStream();
		String valueInXml = stream.toXML(this.selectedValue);
		String serviceName = "gateWayService";
		String method = "getGateWayByMacId";
		String xml = IMonitorUtil.sendToController(serviceName, method, valueInXml);
		
		this.gateWay = (GateWay) stream.fromXML(xml);
		if (this.gateWay == null) {
			ActionContext.getContext().getSession().put("message","Failure~No gateway by the specified mac Id");
		}
		return SUCCESS;
	}


	public String listGateWays() throws Exception {
		return SUCCESS;
	}
	
	public String listAskedGateWays() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String serviceName = "gateWayService";
		String method = "listAskedGateWays";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String toEditGateWay() throws Exception{
		
		long id = gateWay.getId();
		XStream stream = new XStream();
		String xml = stream.toXML(id);
		String serviceName = "gateWayService";
		String method = "getGateWayById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xml);
		this.gateWay = (GateWay) stream.fromXML(resultXml);
		
		// 1. Listing makes
//		serviceName = "makeService";
//		method = "listOnlyMakes";
//		String valueInXml = IMonitorUtil.sendToController(serviceName, method);
//		this.makes = (List<Make>) stream.fromXML(valueInXml);
		
		// 2. Listing statuses
		serviceName = "statusService";
		method = "listStatuses";
		String valueInXml = IMonitorUtil.sendToController(serviceName, method);
		this.statuses = (List<Status>) stream.fromXML(valueInXml);

		// 3. Listing agents
		serviceName = "agentService";
		method = "listAgents";
		valueInXml = IMonitorUtil.sendToController(serviceName, method);
		this.agents = (List<Agent>) stream.fromXML(valueInXml);
		
		this.dateFormat = IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER_DISPLAY);
		
		return SUCCESS;
		
	}
	public String editGateWay() throws Exception{
		XStream stream = new XStream();
		String message = "";
		String capL = gateWay.getMacId();
		String lowL = capL.toLowerCase();
		gateWay.setMacId(lowL);
		String xmlString = stream.toXML(gateWay);
		String serviceName = "gateWayService";
		String method = "updateGateWay";
		String Value = IMonitorUtil.sendToController(serviceName, method, xmlString);
		//pari added success or failure message
		if(Value.equalsIgnoreCase("no")){
			 message = "Failure~ Gateway Updating Failed";
		}else
		{
			message = "Sucess~ Gateway updated Successfully";
		}
		ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}		
		return SUCCESS;
	}
	
	public String tomigrateGateWay() throws Exception{
		
		long id = gateWay.getId();
		XStream stream = new XStream();
		String xml = stream.toXML(id);
		String serviceName = "gateWayService";
		String method = "getGateWayById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xml);
		this.gateWay = (GateWay) stream.fromXML(resultXml);	
		
		return SUCCESS;
		
	}
	
	public String migrateGateWay() throws Exception{
		XStream stream = new XStream();
		String message = "";
		String capL = gateWay.getMacId();
		String lowL = capL.toLowerCase();
		String ip = this.ServerIp;
		String port = this.Port;
		gateWay.setMacId(lowL);
		String xmlString = stream.toXML(gateWay);
		
		String serviceName = "gateWayService";
		String method = "migrateGateWay";
		String Value = IMonitorUtil.sendToController(serviceName, method, xmlString, ip, port);
		//pari added success or failure message
		if(Value.equalsIgnoreCase("no")){
			 message = "Failure~ Gateway Updating Failed";
		}else
		{
			message = "Sucess~ Gateway updated Successfully";
		}
		ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}		
		return SUCCESS;
	}
	
	public String deleteGateWay() throws Exception{
		long id = gateWay.getId();
		XStream stream = new XStream();
		String xmlString = stream.toXML(id);
		String serviceName = "gateWayService";
		String method = "deleteGateWay";
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	}

	public String unregisterGateWay() throws Exception{
		long id = gateWay.getId();
		XStream stream = new XStream();
		String xmlString = stream.toXML(id);
		String serviceName = "gateWayService";
		String method = "unRegisterGateWay";
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	}
	
	public String removefailednodefromGateWay() throws Exception{
		long id = gateWay.getId();
		XStream stream = new XStream();
		String xmlString = stream.toXML(id);
		String serviceName = "gateWayService";
		String method = "removenodefromGateWay";
		String message = IMonitorUtil.sendToController(serviceName, method, xmlString);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	
	
	
	public String addZingGatewayEntry() throws Exception{
		
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.zingGatewayList);
		String serviceName = "gateWayService";
		String method = "addZingGatewayToList";
		String message = IMonitorUtil.sendToController(serviceName, method, xmlString);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	//To add zing gateway list
	public String toAddGatewwayList() throws Exception{
		
		return SUCCESS;
	}
	
	//Display list of zing gateways'
	public String toListZingGateWays() throws Exception {
		return SUCCESS;
	}
	
	public String listAskedZingGateWays() throws Exception {
  		XStream stream = new XStream();
  		String xmlString = stream.toXML(this.dataTable);
  		String serviceName = "gateWayService";	
  		String method = "listAskedZingGateWays";
  		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
  		this.dataTable = (DataTable) stream.fromXML(resultXml);
  		return SUCCESS;
  	}
	
	public String toEditZingGateway() throws Exception
  	{
  		XStream stream=new XStream();
  		String Xml =stream.toXML(this.zingGatewayList);
  		
  		//Fetching superUSer object From Db
  		String serviceName = "gateWayService";
	  	String	method = "getZingGatewayById";
	  	String zingGatewayxml = IMonitorUtil.sendToController(serviceName,method,Xml);
	  	this.zingGatewayList = (ZingGatewayList) stream.fromXML(zingGatewayxml);
	  	LogUtil.info("ZingGateway to edit : " + zingGatewayxml);
  		return SUCCESS;
  	}
	
	public String saveEditedZingDetails() throws Exception
  	{
  		XStream stream=new XStream();
  		String Xml =stream.toXML(this.zingGatewayList);
  		String serviceName = "gateWayService";
	  	String	method = "saveEditedZingDetails";
	  	String zingGatewayxml = IMonitorUtil.sendToController(serviceName,method,Xml);
	  	if(zingGatewayxml.equals("success")){
  			ActionContext.getContext().getSession().put("message", "Update Success");
  		}
  		else
  		{
  			ActionContext.getContext().getSession().put("message", "Failure : Update Failure");
  		}
  		return SUCCESS;
  	}
	
	public String deleteZingGateway() throws Exception
  	{
  		XStream stream=new XStream();
  		String Xml =stream.toXML(this.zingGatewayList);
  		String serviceName = "gateWayService";
	  	String	method = "deleteZingGateway";
	  	String zingGatewayxml = IMonitorUtil.sendToController(serviceName,method,Xml);
	  	if(zingGatewayxml.equals("success")){
  			ActionContext.getContext().getSession().put("message", "Gateway deleted successfully.");
  		}
  		else
  		{
  			ActionContext.getContext().getSession().put("message", "Delete operation failed");
  		}
  		//Delete from customerAndSuperUSer table and then delete from superuser table
  		return SUCCESS;
  	}
	
	public GateWay getGateWay() {
		return gateWay;
	}

	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}

	public List<Make> getMakes() {
		return makes;
	}

	public void setMakes(List<Make> makes) {
		this.makes = makes;
	}

	public List<GateWayType> getGateWayTypes() {
		return gateWayTypes;
	}

	public void setGateWayTypes(List<GateWayType> gateWayTypes) {
		this.gateWayTypes = gateWayTypes;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public List<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getServerIp() {
		return ServerIp;
	}

	public void setServerIp(String serverIp) {
		ServerIp = serverIp;
	}

	public String getPort() {
		return Port;
	}

	public void setPort(String port) {
		Port = port;
	}

	public ZingGatewayList getZingGatewayList() {
		return zingGatewayList;
	}

	public void setZingGatewayList(ZingGatewayList zingGatewayList) {
		this.zingGatewayList = zingGatewayList;
	}
}
