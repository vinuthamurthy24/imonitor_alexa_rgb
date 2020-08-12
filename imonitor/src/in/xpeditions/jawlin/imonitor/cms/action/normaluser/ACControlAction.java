/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LCDRemoteConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class ACControlAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7968205511721903728L;
	private Device device;
	private acConfiguration acConfiguration;
	
	public String controlAc() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String result = IMonitorUtil.sendToController("commandIssueService", "controlAc", xmlString);
		this.device = (Device) stream.fromXML(result);
		
		/*if(roleName.equals(Constants.NORMAL_USER)){
			String serviceName2 = "userService";
			String method2 = "getMainUserAndSendSmsAndEmailing";
			String xmlMessage ="";
			 String actionXml = "";
			if(device.getCommandParam().equals("5")){
				 xmlMessage = stream.toXML(device.getGeneratedDeviceId()+":is On");
				 actionXml = stream.toXML(Constants.DEVICE_ON);
			}
			if(device.getCommandParam().equals("0")){
				xmlMessage = stream.toXML(device.getGeneratedDeviceId()+":is Off");
				actionXml = stream.toXML(Constants.DEVICE_OFF);
			}
			String xmlString2 = stream.toXML(user.getCustomer().getId());
			IMonitorUtil.sendToController(serviceName2, method2, xmlString2,xmlMessage,actionXml,xmlString);
		}*/
		return SUCCESS;
	}
	
	
	public String changeAcTemperature() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String result = IMonitorUtil.sendToController("commandIssueService", "changeAcTemperature", xmlString);
		this.device = (Device) stream.fromXML(result);
	
		/*if(roleName.equals(Constants.NORMAL_USER)){
			String serviceName2 = "userService";
			String method2 = "getMainUserAndSendSmsAndEmailing";
			String xmlMessage = stream.toXML(device.getGeneratedDeviceId()+" was changed");
			String xmlString2 = stream.toXML(user.getCustomer().getId());
			String actionXml = stream.toXML(Constants.AC_CHANGE);
			IMonitorUtil.sendToController(serviceName2, method2, xmlString2,xmlMessage,actionXml,xmlString);
		}*/
		return SUCCESS;
	}

	//starts fan mode bhavya 29 may start
	public String changeFanMode() throws Exception {
		XStream stream = new XStream();
		device.setDeviceConfiguration(acConfiguration);
		String xmlString = stream.toXML(device);
		String result = IMonitorUtil.sendToController("commandIssueService", "changeFanMode", xmlString);
		this.device = (Device) stream.fromXML(result);
		return SUCCESS;
	}
	
	public String controlacswing() throws Exception {
		XStream stream = new XStream();
		device.setDeviceConfiguration(acConfiguration);
		String xmlString = stream.toXML(this.device);
		String result = IMonitorUtil.sendToController("commandIssueService", "controlacswing", xmlString);
		this.device = (Device) stream.fromXML(result);
		return SUCCESS;
	}
	//end fan mode bhavya 29 may
	public final Device getDevice() {
		return device;
	}

	public final void setDevice(Device device) {
		this.device = device;
	}


	public acConfiguration getAcConfiguration() {
		return acConfiguration;
	}


	public void setAcConfiguration(acConfiguration acConfiguration) {
		this.acConfiguration = acConfiguration;
	}
}
