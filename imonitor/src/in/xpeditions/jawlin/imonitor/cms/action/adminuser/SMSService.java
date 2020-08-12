/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.SmsService;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;


import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class SMSService extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SmsService smsService;
	private boolean isstatus;
	private DataTable dataTable;
	private List<String> select;
	

	public SMSService(){
		select = new ArrayList<String>();
		select.add("ims");
		select.add("efl");
	}
	public String toAddSMSServiceProvider() throws Exception
	{
		return SUCCESS;
	}
	public String tolistSMSServiceProvider() throws Exception
	{
		return SUCCESS;
	}
	public String AddSMSServiceProvider() throws Exception
	{
		XStream stream = new XStream();
		
		smsService.setStatus("0");//By default it is disabled
		
		
		String xmlString = stream.toXML(smsService);
		String serviceName = "serverConfiguration";
		String method = "SaveServiceProvider";
		
		
		String result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		if(result.equalsIgnoreCase("no")){
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "SMS Service Provider With Operator Code already exists.");
			return ERROR;
		}else{
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Successfully saved");
		}
		return SUCCESS;
		
	}
	
	public String editsmsserviceprovider() throws Exception
	{
		XStream stream = new XStream();
		
		
		
	
		
		String xmlString = stream.toXML(smsService);
		String serviceName = "serverConfiguration";
		String method = "UpdateServiceProvider";
		String result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		
			
		ActionContext.getContext().getSession().put(Constants.MESSAGE, "Successfully Updated");
		return SUCCESS;
		
	}
	
	
	public String listAskedSMSServices() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String serviceName = "serverConfiguration";
		String method = "listAskedService";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	public String toEditSmsService() throws Exception
	{
		XStream stream = new XStream();
		String xmlString = stream.toXML(smsService);
		String serviceName = "serverConfiguration";
		String method = "GetsmsServiceById";
		String result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		
		
		this.smsService=(SmsService) stream.fromXML(result);
		
		
		
		
		return SUCCESS;
	
	}
	
	public String deleteSmsService() throws Exception
	{
		XStream stream = new XStream();
		String xmlString = stream.toXML(smsService);
		String serviceName = "serverConfiguration";
		String method = "DeletesmsServiceById";
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	
	}
	public String UpdateServiceStatus()throws Exception
	{
		
		XStream stream = new XStream();
		String xmlString = stream.toXML(smsService);
		String serviceName = "serverConfiguration";
		String method = "UpdateStatusServiceProvider";
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	}
	
	
	public String listAskedSmsReports() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String resultXml = IMonitorUtil.sendToController("serverConfiguration", "listAskedSmsReportsWithCount", xmlString);
	  	this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
		
	public SmsService getSmsService() {
		return smsService;
	}
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	
	public boolean isIsstatus() {
		return isstatus;
	}
	public void setIsstatus(boolean isstatus) {
		this.isstatus = isstatus;
	}
	public DataTable getDataTable() {
		return dataTable;
	}
	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
	public List<String> getSelect() {
		return select;
	}
	public void setSelect(List<String> select) {
		this.select = select;
	}

	
}
