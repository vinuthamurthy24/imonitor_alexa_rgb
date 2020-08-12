/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.FirmWare;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.FTPUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Asmodeus
 *
 */
public class FirmWareAction extends ActionSupport  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FirmWare firmWare;
	private File firmWareFile;
	private List<FirmWare> firmWares;
	private List<Agent> agents;
	private GateWay gateWay;
	private DataTable dataTable;
	private String gateWayId;
	private long FirmwareId;
	private String gatewaymodel;
	private boolean firmwareactivation=false;
	private String version1;
	private String version2;
	private String LatestVersionofFirmware900;
	private String LatestVersionofFirmware910;
	private String LatestVersionofZing;
	
	
	public String execute() throws Exception {
	try {
			 	XStream stream = new XStream();
			 	String serviceName = "firmWareService";
			 	String method = "getPath";
			 	String resultValue=(String) IMonitorUtil.sendToController(serviceName, method);
	            String filePath = (String) stream.fromXML(resultValue);
	            String version=this.firmWare.getVersion();
	            if((this.firmWare.getVersion()).length()==1)
	            {
	            version="0"+this.firmWare.getVersion();
	            }
	            String ver1=(this.version1);
	            if((this.version1).length()==1)
	            {
	            	ver1="0"+this.version1;
	            }
	            String ver2=this.version2;
	            if((this.version2).length()==1)
	            {
	            	ver2="0"+this.version2;
	            }
	            String versionPath = filePath + "/" + (this.gatewaymodel)+"_"+ version +"_"+ver1+"_"+ver2;
				String firmWireFileNamePath =  versionPath + "/" + this.firmWare.getName();
	            firmWare.setFile(firmWireFileNamePath);
	            if(this.firmWare.getDate()==null)
	            {
	            	Calendar currentDate = Calendar.getInstance();
		            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		            String dateNow = formatter.format(currentDate.getTime());
		            firmWare.setVersion((this.gatewaymodel)+"_"+ version +"."+ver1+"."+ver2+"_"+dateNow);
	            }
	            else
	            {
	 
	            firmWare.setVersion((this.gatewaymodel)+"_"+ version +"."+ver1+"."+ver2+"_"+this.firmWare.getDate());
	            }
	    		String xmlString = stream.toXML(this.firmWare.getAgent().getId());
	    		firmWare.setActivation((long) 0);
	    		serviceName = "agentService";
    			method = "getAgentById";
    			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
    			xmlString = stream.toXML(firmWare);
    			InputStream inputFile = new FileInputStream(this.firmWareFile);
	            Agent agent = (Agent) stream.fromXML(resultXml);
	          
	            FTPUtil.createFolders(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(),filePath, versionPath);
	            FTPUtil.uploadFile(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(), inputFile, firmWireFileNamePath);
	    		String result = "No";
	    			serviceName = "firmWareService";
	    			method = "saveFirmWare";
	    			resultValue=(String) IMonitorUtil.sendToController(serviceName, method, xmlString);
	    			result = (String) stream.fromXML(resultValue);
	    		if(result.equals("yes")){
	    			ActionContext.getContext().getSession().put(Constants.MESSAGE, "SuccessFully Saved");
	    		}else{
	    			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Cannot be saved");
	    		}
	    		
	    		inputFile.close(); //Naveen added to avoid potential resource leak
	    		
	        } catch (Exception e) {
	            e.printStackTrace();
	            addActionError(e.getMessage());
	            return INPUT;
	        }
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String toUploadFirmWare() throws Exception {
	 	XStream stream = new XStream();
 	    String serviceName = "firmWareService";
		String method = "getLatestFirmwareversion900";
	    String result = IMonitorUtil.sendToController(serviceName, method);
	    this.LatestVersionofFirmware900=result;
		serviceName = "firmWareService";
		method = "getLatestFirmwareversion910";
		result = IMonitorUtil.sendToController(serviceName, method); 
		this.LatestVersionofFirmware910=result;
//		1. Fill the agent
		serviceName = "agentService";
		method = "listAgents";
		 result = IMonitorUtil.sendToController(serviceName, method);
		this.agents = (List<Agent>) stream.fromXML(result);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String toUpdateFirmWare() throws Exception {
		XStream stream = new XStream();
//		1. List all firmwares.
		String serviceName = "firmWareService";
		String method = "listAllFirmWares";
		String result =  IMonitorUtil.sendToController(serviceName, method);
		this.firmWares = (List<FirmWare>) stream.fromXML(result);
		
		return SUCCESS;
	}
	
	public String updateFirmWare() throws Exception {
		XStream stream = new XStream();
		boolean isfileExit;
		String xmlString;
		
		
		//Gateway online verify
		String mcXml=stream.toXML(this.gateWay.getMacId());
		String GatewayResult=IMonitorUtil.sendToController("gateWayService", "getGatewayByMacId",mcXml );
		
		GateWay gateWay=(GateWay) stream.fromXML(GatewayResult);
		if (gateWay.getStatus().getName().equals("Offline"))
		{
			
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Gateway is offline..Update Failed");	
		}
		else
		{
	
	    xmlString = stream.toXML(this.firmWare);
		String serviceName = "firmWareService";
		String method = "getFirmWareById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.firmWare = (FirmWare) stream.fromXML(resultXml);
		String path=this.firmWare.getFile();
		xmlString = stream.toXML(this.firmWare.getAgent().getId());
		serviceName = "agentService";
		method = "getAgentById";
		resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
        Agent agent = (Agent) stream.fromXML(resultXml);
		isfileExit=FTPUtil.isFileExist(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(), path);
		LogUtil.info("---isfileExit----"+isfileExit);
		
		if(isfileExit)
		{
		 serviceName = "gateWayService";
		 method = "updateFirmWare";
		String macXml = stream.toXML(this.gateWay.getMacId());
		String firmXml = stream.toXML(this.firmWare.getId());
		String xml = macXml + "_" + firmXml;
		IMonitorUtil.sendToController(serviceName, method, xml);
		ActionContext.getContext().getSession().put(Constants.MESSAGE, "Gateway is now updating with selected Firmware.");
		
		}
		else
		{
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Uploaded file is not Exists.");	
		}
		
		}
		
		return SUCCESS;
	}
	
	public String updateLatestFirmWare() throws Exception {
		XStream stream = new XStream();
//		1. List all firmwares.
		String serviceName = "gateWayService";
		String method = "updateFirmWare";
		String macXml = stream.toXML(this.gateWayId);
		String firmXml = stream.toXML(this.FirmwareId);
		String xml = macXml + "_" + firmXml;
		IMonitorUtil.sendToController(serviceName, method, xml);
		ActionContext.getContext().getSession().put(Constants.MESSAGE, "Gateway is now updating with selected Firmware.");
		return SUCCESS;
	}
	public String listFirmWare()throws Exception{
		return SUCCESS;
	}

	public String listAskedFirmWare()throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String serviceName = "firmWareService";
		String method = "listAskedFirmWare";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	public String toEditFirmWare()throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.firmWare);
		String serviceName = "firmWareService";
		String method = "getFirmWareById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.firmWare = (FirmWare) stream.fromXML(resultXml);
		if(this.firmWare.getActivation()==1)
		{
			firmwareactivation=true;
		}
		return SUCCESS;
	}
	public String editFirmWare() throws Exception{
		long firmwareactivation =  0;
		if(this.isFirmwareactivation())
		{
			firmwareactivation |= 1;
		}
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.firmWare);
		String serviceName = "firmWareService";
		String method = "getFirmWareById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.firmWare = (FirmWare) stream.fromXML(resultXml);
		String firmWireFileName = this.firmWare.getFile();
		xmlString = stream.toXML(this.firmWare.getAgent().getId());
        serviceName = "agentService";
		method = "getAgentById";
		String result1Xml = IMonitorUtil.sendToController(serviceName, method, xmlString);
        Agent agent = (Agent) stream.fromXML(result1Xml);
		String result = "No";
		this.firmWare = (FirmWare) stream.fromXML(resultXml);
		xmlString = stream.toXML(firmWare);
		firmWare.setActivation(firmwareactivation);
		if(this.firmWareFile == null)
		{
			String xmlid = stream.toXML(firmWare.getId());
			String xmlactivation = stream.toXML(firmWare.getActivation());
			serviceName = "firmWareService";
			method = "updateFirmwareactivation";
			resultXml=IMonitorUtil.sendToController(serviceName, method, xmlid, xmlactivation);
		}
		InputStream inputFile = new FileInputStream(this.firmWareFile);
		FTPUtil.uploadFile(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(), inputFile, firmWireFileName);
		serviceName = "firmWareService";
		method = "updateFirmWare";
		resultXml=(String) IMonitorUtil.sendToController(serviceName, method, xmlString);
		result = (String) stream.fromXML(resultXml);
		if(result.equals("yes")){
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "SuccessFully Saved");
		}else{
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Cannot be saved");
		}
		
		inputFile.close(); //Naveen added to avoid potential resource leak
		return SUCCESS;
		
	}
	public String deleteFirmWare()throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.firmWare);
		String serviceName = "firmWareService";
		String method = "getFirmWareById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.firmWare = (FirmWare) stream.fromXML(resultXml);
		serviceName = "firmWareService";
	 	method = "getPath";
	 	resultXml=(String) IMonitorUtil.sendToController(serviceName, method);
	 	xmlString = stream.toXML(this.firmWare.getAgent().getId());
        serviceName = "agentService";
		method = "getAgentById";
		resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
        Agent agent = (Agent) stream.fromXML(resultXml);
        String filepath=firmWare.getFile();
        try
        {
        	FTPUtil.removeFile(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(), filepath);
        }
        catch (SftpException se)
        {}
        catch (JSchException je)
        {}
	    
        String result = "No";
		xmlString = stream.toXML(firmWare);
		serviceName = "firmWareService";
		method = "deleteFirmWare";
		resultXml=(String) IMonitorUtil.sendToController(serviceName, method, xmlString);
		result = (String) stream.fromXML(resultXml);
			
			if(result.equals("yes")){
				ActionContext.getContext().getSession().put(Constants.MESSAGE, "Successfully deleted the firmware "+this.firmWare.getVersion());
			}else{
				ActionContext.getContext().getSession().put(Constants.MESSAGE, "Cannot delete firmware "+this.firmWare.getVersion());
			}
        
        
		return SUCCESS;
	}

	public File getFirmWareFile() {
		return firmWareFile;
	}

	public void setFirmWareFile(File firmWareFile) {
		this.firmWareFile = firmWareFile;
	}

	public List<FirmWare> getFirmWares() {
		return firmWares;
	}

	public void setFirmWares(List<FirmWare> firmWares) {
		this.firmWares = firmWares;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public GateWay getGateWay() {
		return gateWay;
	}

	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}

	public FirmWare getFirmWare() {
		return this.firmWare;
	}

	public void setFirmWare(FirmWare firmWare) {
		this.firmWare = firmWare;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public String getGateWayId() {
		return gateWayId;
	}

	public void setGateWayId(String gateWayId) {
		this.gateWayId = gateWayId;
	}

	public long getFirmwareId() {
		return FirmwareId;
	}

	public void setFirmwareId(long firmwareId) {
		FirmwareId = firmwareId;
	}

	public String getGatewaymodel() {
		return gatewaymodel;
	}

	public void setGatewaymodel(String gatewaymodel) {
		this.gatewaymodel = gatewaymodel;
	}
	public String getVersion1() {
		return version1;
	}

	public void setVersion1(String version1) {
		this.version1 = version1;
	}

	public String getVersion2() {
		return version2;
	}

	public void setVersion2(String version2) {
		this.version2 = version2;
	}

	public boolean isFirmwareactivation() {
		return firmwareactivation;
	}

	public void setFirmwareactivation(boolean firmwareactivation) {
		this.firmwareactivation = firmwareactivation;
	}

	public String getLatestVersionofFirmware900() {
		return LatestVersionofFirmware900;
	}

	public void setLatestVersionofFirmware900(String latestVersionofFirmware900) {
		LatestVersionofFirmware900 = latestVersionofFirmware900;
	}

	public String getLatestVersionofFirmware910() {
		return LatestVersionofFirmware910;
	}

	public void setLatestVersionofFirmware910(String latestVersionofFirmware910) {
		LatestVersionofFirmware910 = latestVersionofFirmware910;
	}

	public String getLatestVersionofZing() {
		return LatestVersionofZing;
	}

	public void setLatestVersionofZing(String latestVersionofZing) {
		LatestVersionofZing = latestVersionofZing;
	}
}
