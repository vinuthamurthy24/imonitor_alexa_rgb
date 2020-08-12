package in.imsipl.cms.action.supportuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertMonitor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertStatus;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class MonitoringAlertAction extends ActionSupport{
	
	
	private DataTable dataTable;
	private int displayStart;
	private AlertMonitor alertMonitor;
	 
	public String togetMonitoringAlerts() throws Exception{
		
		
		return SUCCESS;
	}
	
public String getListToModifyAlerts() throws Exception{
		
		
		return SUCCESS;
	}
	
public String toListAllCustomerAlerts() throws Exception{
	
	XStream stream = new XStream();
	String xmlString = stream.toXML(this.dataTable);

	String resultXml = IMonitorUtil.sendToController("customerService", "listAskedCustomersAlerts", xmlString);
	dataTable = (DataTable) stream.fromXML(resultXml);
	
	
	
	return SUCCESS;
}

public String toManageCustomerAlerts() throws Exception{
	
	XStream stream = new XStream();
	String xmlString = stream.toXML(this.dataTable);
	
	String resultXml = IMonitorUtil.sendToController("customerService", "listAskedCustomersAlertsForModify", xmlString);
	dataTable = (DataTable) stream.fromXML(resultXml);
	
	
	
	return SUCCESS;
}

public String toViewCustomerReport() throws Exception{
	XStream stream = new XStream();
	long id=this.alertMonitor.getId();
	String xml=stream.toXML(id);
	String resultxml=IMonitorUtil.sendToController("customerService", "getalertreportsbyid",xml );
	this.alertMonitor=(AlertMonitor) stream.fromXML(resultxml);
	return SUCCESS;
}

public String updatecustomeralerts() throws Exception{
	
	XStream stream = new XStream();
	String message="";
	long id=this.alertMonitor.getId();
	String xml=stream.toXML(id);
	String attended=this.alertMonitor.getAttended();
	String contactno=this.alertMonitor.getContactnumber();
	String status=stream.toXML(this.alertMonitor.getAlertStatus().getId());
	String resultXml = IMonitorUtil.sendToController("customerService", "Savealertmonitor",xml,attended,contactno,status);
	if(resultXml.equalsIgnoreCase("yes")){
		message = IMonitorUtil.formatMessage(this, "Sucess~Successfully saved.");
		ActionContext.getContext().getSession().put("message", message);
	}else{
		ActionContext.getContext().getSession().put("message","Failure~Unable To save Provided information");
	}
	return SUCCESS;
}
public String deleteCustomerReport() throws Exception{
	long id=this.alertMonitor.getId();
	XStream stream = new XStream();
	String xml=stream.toXML(id);
	IMonitorUtil.sendToController("customerService", "deletealertmonitor",xml );
	return SUCCESS;
}

public DataTable getDataTable() {
	return dataTable;
}

public void setDataTable(DataTable dataTable) {
	this.dataTable = dataTable;
}

public int getDisplayStart() {
	return displayStart;
}

public void setDisplayStart(int displayStart) {
	this.displayStart = displayStart;
}

public AlertMonitor getAlertMonitor() {
	return alertMonitor;
}

public void setAlertMonitor(AlertMonitor alertMonitor) {
	this.alertMonitor = alertMonitor;
}
}
