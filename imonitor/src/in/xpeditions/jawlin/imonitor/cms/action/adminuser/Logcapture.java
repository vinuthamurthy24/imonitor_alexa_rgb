/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorProperties;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class Logcapture extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private GateWay gateWay;
	
	private String imvgUploadContextPath=IMonitorProperties.getPropertyMap().get(Constants.IMVG_UPLOAD_CONTEXT_PATH);
	private String selectedValue;
	private DataTable dataTable;
	private long id;
	private String macId;

	private List<String> select;
	private List<String> days;
	private List<Agent> agents;
	
	private Queue<String> queue;
	private String request;
	
	private String timeout;
	private Device device;
	private String upload;
	private String delete;
	private String fileName;
	private String filePath;
	private String Command;
	private String PassWord;
	public String listGateWays() throws Exception {
		return SUCCESS;
	}
	

	public String toRequestForLog() throws Exception{	
		return SUCCESS;
	}
	
	
	public Logcapture(){
		days = new ArrayList<String>();
		days.add("1");
		days.add("2");
		days.add("3");
		days.add("4");
		days.add("5");
		days.add("6");
		days.add("7");
		
		select = new ArrayList<String>();
		select.add("START CAPTURE OF LOG");
		select.add("REQUEST FOR UPLOAD");
		select.add("REQUEST FOR DELETE");
		
		
	}
public String toEditGateWay() throws Exception{	
		long id = gateWay.getId();
		XStream stream = new XStream();
		String xml = stream.toXML(id);
		String serviceName = "gateWayService";
		String method = "getGateWayById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xml);
		this.gateWay = (GateWay)stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	
	public String RequestForLog() throws Exception 
	{
		XStream stream=new XStream();
		String serviceName = "gateWayService";
		String method = "logcapture";
		String macid=gateWay.getMacId();
		
		
		String upload=this.upload;
		String delete=this.delete;
		String selectedValue=this.selectedValue;
		String timeout=this.timeout;
		LogUtil.info("selectedValue"+selectedValue+"timeout"+timeout+"upload"+upload+"delete"+delete);
		if(upload==null)
		{
			if(delete==null)
			{
				
				String xml = stream.toXML(macid);
				IMonitorUtil.sendToController(serviceName, method, xml,selectedValue,timeout);
			}
		}
		if(!(upload==null))
		{
			String Upload="UPLOAD";
			
			String xml = stream.toXML(macid);
			IMonitorUtil.sendToController(serviceName, method, xml,Upload,upload);
		}
		
		if(!(delete==null))
		{
			String Delete="DELETE";
			String xml = stream.toXML(macid);
			IMonitorUtil.sendToController(serviceName, method, xml,Delete,delete);
		}
		return SUCCESS;
	}
	
	
	public String listAskedGateWays() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String serviceName = "gateWayService";
		String method = "listAskedGateWaysWithMaintenance";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	public String deleteLog() throws Exception
	{
		LogUtil.info("fileName"+fileName);
		XStream stream = new XStream();
		setMacId(this.device.getGateWay().getMacId());
		String xmlString = stream.toXML(this.fileName+","+this.filePath);
		String serviceName = "gateWayService";
		String method = "DeleteLogByfileName";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		
		return SUCCESS;
	}
	
	public String listLogs() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		xmlString += "-" + stream.toXML(this.device.getGateWay().getMacId());
		LogUtil.info(xmlString);
		String serviceName = "deviceService";
		String method = "listlogsUnderAGateWayById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	
	
	public String toCkeckPassWord() throws Exception{
		long id = gateWay.getId();
		XStream stream = new XStream();
		String xml = stream.toXML(id);
		String serviceName = "gateWayService";
		String method = "getGateWayById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xml);
		this.gateWay = (GateWay)stream.fromXML(resultXml);
		return SUCCESS;
	}
	public String toExcuteCommand() throws Exception{
		String PassWord=this.PassWord;
		String Adminpass=IMonitorProperties.getPropertyMap().get(Constants.ADMINPASS);
		
		if(!(PassWord.equals(Adminpass)))
		{
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Failure~Not Autorized.");
			return ERROR;
		}
		return SUCCESS;
		/*String xml = stream.toXML(id);
		String serviceName = "gateWayService";
		String method = "getGateWayById";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xml);
		this.gateWay = (GateWay)stream.fromXML(resultXml);*/
		
		
	}
	public String ExecuteCommand() throws Exception
	{
		XStream stream = new XStream();
		String GateWayxml = stream.toXML(gateWay);
		String CommandXml=stream.toXML(Command);
		String TimeoutXml=stream.toXML(timeout);
		LogUtil.info(TimeoutXml);
		String serviceName = "gateWayService";
		String method = "ExcuteUserCommand";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, GateWayxml,CommandXml,TimeoutXml);
		ActionContext.getContext().getSession().put("message",resultXml);
		return SUCCESS;
	}
	
	public String MaintenanceChanelopen() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.gateWay);
		String serviceName = "gateWayService";
		String method = "RequestToOpenChannel";
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	}
	
	public GateWay getGateWay() {
		return gateWay;
	}

	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
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


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getMacId() {
		return macId;
	}


	public void setMacId(String macId) {
		this.macId = macId;
	}


	public List<Agent> getAgents() {
		return agents;
	}


	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}
	public String getTimeout() {
		return timeout;
	}


	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}


	public List<String> getDays() {
		return days;
	}


	public void setDays(List<String> days) {
		this.days = days;
	}


	public String getUpload() {
		return upload;
	}


	public void setUpload(String upload) {
		this.upload = upload;
	}


	public String getDelete() {
		return delete;
	}


	public void setDelete(String delete) {
		this.delete = delete;
	}


	public List<String> getSelect() {
		return select;
	}


	public void setSelect(List<String> select) {
		this.select = select;
	}


	public String getRequest() {
		return request;
	}


	public void setRequest(String request) {
		this.request = request;
	}


	public Queue<String> getQueue() {
		return queue;
	}


	public void setQueue(Queue<String> queue) {
		this.queue = queue;
	}


	public Device getDevice() {
		return device;
	}


	public void setDevice(Device device) {
		this.device = device;
	}


	public String getImvgUploadContextPath() {
		LogUtil.info("VVN1"+imvgUploadContextPath);
		return imvgUploadContextPath;
	}


	public void setImvgUploadContextPath(String imvgUploadContextPath) {
		this.imvgUploadContextPath = imvgUploadContextPath;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getCommand() {
		return Command;
	}


	public void setCommand(String command) {
		Command = command;
	}


	public String getPassWord() {
		return PassWord;
	}


	public void setPassWord(String passWord) {
		PassWord = passWord;
	}


	
	
	
}


