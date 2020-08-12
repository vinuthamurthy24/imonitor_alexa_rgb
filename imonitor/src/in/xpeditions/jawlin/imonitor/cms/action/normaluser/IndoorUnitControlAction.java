package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class IndoorUnitControlAction extends ActionSupport
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7968205511721903728L;
	private Device device;
	private IndoorUnitConfiguration indoorUnitConfiguration;
	
	
	
	public String controlIndoorUnit() throws Exception {
		
		XStream stream = new XStream();
		
		String xmlString = stream.toXML(this.device);
		String result = IMonitorUtil.sendToController("commandIssueService", "controlIndoorUnit", xmlString);
		this.device = (Device) stream.fromXML(result);
		
		return SUCCESS;
	}
	
	public String fanDirectionControl() throws Exception {
	
		XStream stream = new XStream();
		device.setDeviceConfiguration(indoorUnitConfiguration);
		
		String xmlString = stream.toXML(this.device);
		String result = IMonitorUtil.sendToController("commandIssueService", "fanDirectionControl", xmlString);
		this.device = (Device) stream.fromXML(result);
		
		return SUCCESS;
	}
	
	public String fanVolumeControl() throws Exception {
		
		XStream stream = new XStream();
		device.setDeviceConfiguration(indoorUnitConfiguration);
		
		String xmlString = stream.toXML(this.device);
		String result = IMonitorUtil.sendToController("commandIssueService", "fanVolumeControl", xmlString);
		this.device = (Device) stream.fromXML(result);
		
		return SUCCESS;
	}
	
	
	
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public IndoorUnitConfiguration getIndoorUnitConfiguration() {
		return indoorUnitConfiguration;
	}
	public void setIndoorUnitConfiguration(IndoorUnitConfiguration indoorUnitConfiguration) {
		this.indoorUnitConfiguration = indoorUnitConfiguration;
	}
}
