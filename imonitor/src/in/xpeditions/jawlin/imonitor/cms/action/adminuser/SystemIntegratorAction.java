/* Copyright © 2012 iMonitor Solutions India Private Limited */

package in.xpeditions.jawlin.imonitor.cms.action.adminuser;



import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemIntegrator;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.FTPUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class SystemIntegratorAction extends ActionSupport{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SystemIntegrator systemIntegrator;
	private List<Status> statuses;
    private DataTable dataTable;
    private List<Agent> agents;
    private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	
	private String action;
 
	
	
	
	
	public String execute() throws Exception {
		
		try {
		
		
		
		XStream stream = new XStream();
		
		
		
		if(fileUpload != null)
		{
			
		
		InputStream inputFile = new FileInputStream(this.fileUpload);
		String serviceName = "systemIntegratorService";
	 	String method = "getSILogoPath";
	 	String resultValue=(String) IMonitorUtil.sendToController(serviceName, method);
	 	
	 	
	 	
	 	String filePath = (String) stream.fromXML(resultValue);
		
	 	
	 	
		String xmlString = stream.toXML(this.systemIntegrator.getAgent().getId());
		
		
		
		serviceName = "agentService";
		method = "getAgentById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		
	
		
		
        Agent agent = (Agent) stream.fromXML(resultXml);
        
       
        String LogoFileNamePath =  filePath + "/" + this.systemIntegrator.getName()+".jpg";
        
       
        systemIntegrator.setLogo(LogoFileNamePath);
        
        
        
        FTPUtil.createFolders(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(),filePath);
        FTPUtil.uploadFile(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(), inputFile, LogoFileNamePath);
		
		}
		
        String xmlString = stream.toXML(systemIntegrator);
		
        if(this.action.equals("add"))
        {
        	String serviceName = "systemIntegratorService";
            String method = "saveSystemIntegrator";
    		
    		
    		String result = IMonitorUtil.sendToController(serviceName, method, xmlString);
    		
    		ActionContext.getContext().getSession().put("message","Sucess~System Integrator with id " + this.systemIntegrator.getSystemIntegratorId() + " is saved successfully!");
    		/*if(result.equalsIgnoreCase("alreadyExists")){
    			ActionContext.getContext().getSession().put(Constants.MESSAGE, "System Integrator with given name already exists.");
    			return ERROR;
    		}else if(result.equalsIgnoreCase("DB_ERROR")){
    			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Save unsuccessful due to database error");
    		}else{
    			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Successfully saved");
    		}*/
    		
        }
        else
        {
        	String serviceName = "systemIntegratorService";
    		String method = "UpdateSystemIntegrator";
    		
    		IMonitorUtil.sendToController(serviceName, method, xmlString);
    		
    		ActionContext.getContext().getSession().put("message",
    				"Sucess~System Integrator with id " + this.systemIntegrator.getSystemIntegratorId() + " is Updated successfully!");
    		
        }
        
        return SUCCESS;
        
		} catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
            return INPUT;
        }
		
	}

	@SuppressWarnings("unchecked")
	public String toAddSystemIntegrator() throws Exception {
		String serviceName = "statusService";
		String method = "listStatuses";
		String valueInXml = IMonitorUtil.sendToController(serviceName, method);
		
		XStream stream = new XStream();
		
		List<Status> statusFromDB = new ArrayList<Status>();
		List<Status> statusList = (List<Status>) stream.fromXML(valueInXml);
		try {
			for(Status status : statusList){
				if(status.getName().equalsIgnoreCase("Running") ||
						status.getName().equalsIgnoreCase("Suspend")){
					statusFromDB.add(status);
				}
			}
		} catch (Exception e) {
			LogUtil.info("Caught Exception 1 - ", e);
		}
		this.statuses = statusFromDB;
		
//		1. Fill the agent
		serviceName = "agentService";
		method = "listAgents";
		String result = IMonitorUtil.sendToController(serviceName, method);
		this.agents = (List<Agent>) stream.fromXML(result);
		
		return SUCCESS;
	}
	
	


	public String addSystemIntegrator() throws Exception{
		
		XStream stream = new XStream();
		
		
		if(fileUpload != null)
		{
			
		
		InputStream inputFile = new FileInputStream(this.fileUpload);
		String serviceName = "systemIntegratorService";
	 	String method = "getSILogoPath";
	 	String resultValue=(String) IMonitorUtil.sendToController(serviceName, method);
	 	
	 	
	 	
	 	String filePath = (String) stream.fromXML(resultValue);
		
	 	
	 	
		String xmlString = stream.toXML(this.systemIntegrator.getAgent().getId());
		
		
		
		serviceName = "agentService";
		method = "getAgentById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		
		
		
		
        Agent agent = (Agent) stream.fromXML(resultXml);
        
     
        String LogoFileNamePath =  filePath + "/" + this.systemIntegrator.getName()+".jpg";
        
      
        systemIntegrator.setLogo(LogoFileNamePath);
        
        
        
        FTPUtil.createFolders(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(),filePath);
        FTPUtil.uploadFile(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(), inputFile, LogoFileNamePath);
		
		}
		
        String xmlString = stream.toXML(systemIntegrator);
		
        String serviceName = "systemIntegratorService";
        String method = "saveSystemIntegrator";
		
		
		String result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		
		ActionContext.getContext().getSession().put("message","Sucess~System Integrator with id " + this.systemIntegrator.getSystemIntegratorId() + " is saved successfully!");
		/*if(result.equalsIgnoreCase("alreadyExists")){
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "System Integrator with given name already exists.");
			return ERROR;
		}else if(result.equalsIgnoreCase("DB_ERROR")){
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Save unsuccessful due to database error");
		}else{
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Successfully saved");
		}*/
		return SUCCESS;
	}

	
	public String toListSystemIntegrators() throws Exception {
		return SUCCESS;
	}
    
	public String listAskedSystemIntegrators(){
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String serviceName = "systemIntegratorService";
		String method = "listAskedSystemIntegrators";
		String resultXml = null;
		try {
			resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	
	public String toEditSystemIntegrator() throws Exception
	{
		
		
		
		
		
		String serviceName = "statusService";
		String method = "listStatuses";
		String valueInXml = IMonitorUtil.sendToController(serviceName, method);
		
		XStream stream = new XStream();
		
		List<Status> statusFromDB = new ArrayList<Status>();
		List<Status> statusList = (List<Status>) stream.fromXML(valueInXml);
		try {
			for(Status status : statusList){
				if(status.getName().equalsIgnoreCase("Running") ||
						status.getName().equalsIgnoreCase("Suspend")){
					statusFromDB.add(status);
				}
			}
		} catch (Exception e) {
			LogUtil.info("Caught Exception 1 - ", e);
		}
		this.statuses = statusFromDB;
		
//		1. Fill the agent
		serviceName = "agentService";
		method = "listAgents";
		String result = IMonitorUtil.sendToController(serviceName, method);
		this.agents = (List<Agent>) stream.fromXML(result);
		
		
		serviceName = "systemIntegratorService";
		method = "toEditSystemIntegrator";
		String xmlString = stream.toXML(this.systemIntegrator);
		result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.systemIntegrator=(SystemIntegrator) stream.fromXML(result);
		return SUCCESS;
	
	}
	
	public String deleteSystemIntegrtor() throws Exception
	{
		XStream stream = new XStream();
		String serviceName = "systemIntegratorService";
		String method = "deleteSystemIntegrtor";
		
		String xmlString = stream.toXML(this.systemIntegrator);
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	
	}
	public String UpdateSystemIntegrator()throws Exception
	{
		XStream stream = new XStream();
		
		
		if(fileUpload != null)
		{
			
		
		InputStream inputFile = new FileInputStream(this.fileUpload);
		String serviceName = "systemIntegratorService";
	 	String method = "getSILogoPath";
	 	String resultValue=(String) IMonitorUtil.sendToController(serviceName, method);
	 	
	 	
	 	
	 	String filePath = (String) stream.fromXML(resultValue);
		
	 	
	 	
		String xmlString = stream.toXML(this.systemIntegrator.getAgent().getId());
		
		
		
		serviceName = "agentService";
		method = "getAgentById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		
		
		
		
        Agent agent = (Agent) stream.fromXML(resultXml);
        
       
        String LogoFileNamePath =  filePath + "/" + this.systemIntegrator.getName()+".jpg";
        
      
        systemIntegrator.setLogo(LogoFileNamePath);
        
        
        
        FTPUtil.createFolders(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(),filePath);
        FTPUtil.uploadFile(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(), inputFile, LogoFileNamePath);
		
		}
		
		
		String serviceName = "systemIntegratorService";
		String method = "UpdateSystemIntegrator";
		String xmlString = stream.toXML(this.systemIntegrator);
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		
		ActionContext.getContext().getSession().put("message",
				"Sucess~System Integrator with id " + this.systemIntegrator.getSystemIntegratorId() + " is Updated successfully!");
		return SUCCESS;
	}
	
	
	public List<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

	public SystemIntegrator getSystemIntegrator() {
		return systemIntegrator;
	}

	public void setSystemIntegrator(SystemIntegrator systemIntegrator) {
		this.systemIntegrator = systemIntegrator;
	}
	
	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}




	public List<Agent> getAgents() {
		return agents;
	}




	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	
	public String getFileUploadContentType() {
		return fileUploadContentType;
	}
 
	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}
 
	public String getFileUploadFileName() {
		return fileUploadFileName;
	}
 
	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}
 
	public File getFileUpload() {
		return fileUpload;
	}
 
	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
	
}
