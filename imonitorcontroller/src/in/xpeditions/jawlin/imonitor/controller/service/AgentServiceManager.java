/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AgentManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.general.DataTable;

import java.util.List;

import com.thoughtworks.xstream.XStream;

public class AgentServiceManager {
	//sumit start: For updating Location image
	public String getAgentByCustomerId(String customerIdXml){
		
		XStream stream = new XStream();
		String customerId = (String) stream.fromXML(customerIdXml);
		CustomerManager customerManager = new CustomerManager();
		Customer customer = customerManager.getCustomerByCustomerId(customerId);
		GatewayManager gatewayManager = new GatewayManager();
		
		GateWay gateWay = gatewayManager.getGateWayByCustomer(customer);
		
		AgentManager agentManager = new AgentManager();
		
		Agent agent = agentManager.getAgentById(gateWay.getAgent().getId());
		
		return stream.toXML(agent);
	}
	//sumit end:
	// ******************************************************* sumit start: handle duplicate agent name *****************************************
	public String saveAgent(String xml){
		String result="no";
		XStream xStream=new XStream();
		Agent agent=(Agent)xStream.fromXML(xml);
		AgentManager agentManager=new AgentManager();
		
		List<Agent> agents = agentManager.listOfAgents();
		//check if name already exists.
		for(Agent a: agents){
			if(!agent.getName().equalsIgnoreCase(a.getName())){
				continue;
			}else{
				return result;
			}
		}
		if(agentManager.save(agent)){
			result="yes";
		}
		return result;
	}
	// ***************************************************************** sumit end. **************************************************************
	public String updateAgent(String xml){
		String result="no";
		XStream xStream=new XStream();
		Agent agent=(Agent)xStream.fromXML(xml);
		AgentManager agentManager=new AgentManager();
		if(agentManager.update(agent)){
			result="yes";
		}
		return result;
	}
	public String deleteAgent(String xml){
		String result="no";
		XStream xStream=new XStream();
		long id = (Long)xStream.fromXML(xml);
		AgentManager agentManager=new AgentManager();
		agentManager.updateGatwayAfterAgentDeletion(id);
		Agent agent = agentManager.getAgentById(id);
		if(agentManager.delete(agent)){
			
			result="yes";
		}
		return result;
	}
	
	public String deleteAgentIfNoGateWay(String xml){
		XStream xStream=new XStream();
		Agent agent = (Agent)xStream.fromXML(xml);
		AgentManager agentManager=new AgentManager();
		String result = "Unable to Delete the agent \n" +
				" Possigble Reasons.\n" +
				" 1. GateWay(s) is/are attached to the agent.";

		if(agentManager.deleteAgentIfNoGateWay(agent)){
			result = "Agent is deleted successfully.";
		}
		
		return result;
	}
	
	public String listAgents(){
		String xml="";
		XStream xStream=new XStream();
		AgentManager agentManager=new AgentManager();
		List<Agent>agents=agentManager.listOfAgents();
		xml=xStream.toXML(agents);
		return xml;
	}
	public String getTotalAgentCount(){
		String xml="";
		XStream xStream=new XStream();
		AgentManager agentManager=new AgentManager();
		long count = agentManager.getTotalAgentCount();
		xml = xStream.toXML(count);
		return xml;
	}
	public String listAskedAgents(String xml){
		XStream xStream=new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(xml);
		AgentManager agentManager = new AgentManager();
		int count = agentManager.getTotalAgentCount();
		dataTable.setTotalRows(count);
		String sSearch = dataTable.getsSearch();
		String[] cols = { "a.name","a.ip","a.receiverPort","a.controllerReceiverPort","a.controllerBroadCasterPort","a.streamingIp","a.streamingPort","a.ftpIp","a.ftpPort","s.name"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		
		List<?> list = agentManager.listAskedAgents(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength());
		dataTable.setResults(list);
		int displayRow = agentManager.getTotalAgentCount(sSearch);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	public String getAgentById(String xml){
		XStream stream = new XStream();
		long id = (Long) stream.fromXML(xml);
		AgentManager agentManager = new AgentManager();
		Agent agent = agentManager.getAgentById(id);
		String valueInXml = stream.toXML(agent); 
		return valueInXml;
	}
}
