/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.NewDeviceType;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class DeviceTypeAction extends ActionSupport 
{
	/**
	 * Asmodeus
	 */
	private static final long serialVersionUID = 1L;
	private DeviceType deviceType;
	
	private NewDeviceType newDeviceType;
	private DataTable dataTable;
	
	
	public String addDeviceType() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.deviceType);
		String serviceName = "deviceTypeService";
		String method = "saveDeviceType";
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	}
	
	public String toAddNewDeviceType() throws Exception {
	/*	XStream stream = new XStream();
		String xmlString = stream.toXML(this.deviceType);
		String serviceName = "deviceTypeService";
		String method = "saveDeviceType";
		IMonitorUtil.sendToController(serviceName, method, xmlString);*/
		return SUCCESS;
	}
	
	
	public String toSaveNewDeviceType() throws Exception 
		{
		
		XStream stream = new XStream();
		String newDevicexml = stream.toXML(newDeviceType);
		//String result = "";
		String message = "";
		String result = IMonitorUtil.sendToController("deviceTypeService", "saveNewDeviceType", newDevicexml);
				
		if(result.equals("yes")){
		
			message = IMonitorUtil.formatMessage(this, "Device save successfull");
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		else{
		
			message = IMonitorUtil.formatFailureMessage(this, "Device save failed");
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		}
	
	
	
	public String listDeviceTypes(){
		
		return SUCCESS;
	}
	
	public String listAskedDeviceTypes(){
		/*XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String serviceName = "deviceTypeService";
		String method = "listAskedDeviceTypes";
		String resultXml = null;
		try {
			resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(resultXml == null){
			return "failure";
		}
		this.dataTable = (DataTable) stream.fromXML(resultXml);*/
		return SUCCESS;
	}
	
	
	
	
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public NewDeviceType getNewDeviceType() {
		return newDeviceType;
	}

	public void setNewDeviceType(NewDeviceType newDeviceType) {
		this.newDeviceType = newDeviceType;
	}

	public final DataTable getDataTable() {
		return dataTable;
	}

	public final void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
}
