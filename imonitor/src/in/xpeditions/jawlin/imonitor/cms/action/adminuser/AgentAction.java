/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;


import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class AgentAction extends ActionSupport {
	
	/**
	 * Asmodeus
	 */
	
	private static final long serialVersionUID = 1L;
	private List<Status> statuses;
	private Agent agent;
	private DataTable dataTable;

	@SuppressWarnings("unchecked")
	public String toAddAgent() throws Exception {
		String valueInXml = IMonitorUtil.sendToController("statusService", "listStatuses");
		XStream stream = new XStream();
		statuses = (List<Status>) stream.fromXML(valueInXml);
		return SUCCESS;
	}
	// *************************************************** sumit start: handle Duplicate Agent Name.*********************************************
	public String addAgent() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(agent);
		String serviceName = "agentService";
		String method = "saveAgent";
		String result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		if(result.equalsIgnoreCase("no")){
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Agent Name already exists.");
			return ERROR;
		}else{
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Successfully saved");
		}
		return SUCCESS;
	}
// ********************************************************************* sumit end **************************************************************
	public String listAgent() throws Exception {
		return SUCCESS;
	}

	public String listAskedAgents() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String serviceName = "agentService";
		String method = "listAskedAgents";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String toEditAgent() throws Exception {
		long id = agent.getId();
		XStream stream = new XStream();
		String xmlString = stream.toXML(id);
		String serviceName = "agentService";
		String method = "getAgentById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.agent = (Agent) stream.fromXML(resultXml);
		 serviceName = "statusService";
		 method = "listStatuses";
		String valueInXml = IMonitorUtil.sendToController(serviceName, method);
		statuses = (List<Status>) stream.fromXML(valueInXml);
		return SUCCESS;
	}
	
	public String editAgent() throws Exception {
		XStream stream = new XStream();
		String message = "";
		String xmlString = stream.toXML(agent);
		String serviceName = "agentService";
		String method = "updateAgent";
		String Value = IMonitorUtil.sendToController(serviceName, method, xmlString);
		//Pari added to check success or failure message
		if(Value.equalsIgnoreCase("no")){
			message = "Failure : Updating Agent Failed";
		}else {
			message = "Success : Updating Agent Success";
		}
		ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}		
		return SUCCESS;
	}

	public String deleteAgent() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.agent);
		String serviceName = "agentService";
		String method = "deleteAgentIfNoGateWay";
		String message = IMonitorUtil.sendToController(serviceName, method, xmlString);
		ActionContext.getContext().getSession().put(Constants.MESSAGE, message);
		return SUCCESS;
	}
	
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
}
