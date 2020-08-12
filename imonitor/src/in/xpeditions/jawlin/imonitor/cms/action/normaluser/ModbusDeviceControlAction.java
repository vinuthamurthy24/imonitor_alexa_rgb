package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

public class ModbusDeviceControlAction extends ActionSupport{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Device device;
	
	
	public String checkViaForSlave() throws Exception {
		XStream stream = new XStream();
		String message = "msg.imonitor.devicedelete.0000";
		String deviceXml=stream.toXML(device);
		String countResult = IMonitorUtil.sendToController("deviceService", "getSlaveCountForVia", deviceXml);
		message = IMonitorUtil.formatFailureMessage(this, message);		
		//2.if rules exists for the device prompt the user about it and proceed further
		if(countResult.equalsIgnoreCase("SLAVE_EXISTS")){
			
			message = "msg.imonitor.viadelete.0001";
			message = IMonitorUtil.formatMessage(this, message);	
		}
		
		ActionContext.getContext().getSession().put("message", message);
		//ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	
	
	public String changeIduTemperature() throws Exception {
		
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		
		String result = IMonitorUtil.sendToController("commandIssueService", "changeIduTemperature", xmlString);
		this.device = (Device) stream.fromXML(result);
	
		
		return SUCCESS;
	}


	public Device getDevice() {
		return device;
	}


	public void setDevice(Device device) {
		this.device = device;
	}

}
