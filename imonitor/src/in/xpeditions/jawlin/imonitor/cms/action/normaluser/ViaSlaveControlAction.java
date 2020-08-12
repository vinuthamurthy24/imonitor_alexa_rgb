package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class ViaSlaveControlAction extends ActionSupport
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Device device;
	
	
	public String checkSlaveForIndoorUnits() throws Exception {
		XStream stream = new XStream();
		String message = "msg.imonitor.devicedelete.0000";
		String deviceXml=stream.toXML(device);
				
		String countResult = IMonitorUtil.sendToController("deviceService", "getIndoorUnitsCountForSlave", deviceXml);
		message = IMonitorUtil.formatFailureMessage(this, message);	
		//2.if rules exists for the device prompt the user about it and proceed further
		if(countResult.equalsIgnoreCase("INDOOR_UNITS_EXISTS")){
			
			message = "msg.imonitor.slavedelete.0001";
			message = IMonitorUtil.formatMessage(this, message);	
		}	
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}


	public Device getDevice() {
		return device;
	}


	public void setDevice(Device device) {
		this.device = device;
	}
}
