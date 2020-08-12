/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.util.CameraConfParam;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class CameraConfigurationManagement extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3013380992480387663L;
	private Device device;
	private CameraConfiguration cameraConfiguration;
	private CameraConfParam camaraConfParam;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String toUpdateCameraConfiguration() throws Exception {
		XStream stream = new XStream();
		if(this.device != null){
			String xmlString = stream.toXML(device.getGeneratedDeviceId());
			String deviceXml = IMonitorUtil.sendToController("deviceService", "getDevice", xmlString);
			Device device = (Device) stream.fromXML(deviceXml);
			if(device != null){
				this.cameraConfiguration = (CameraConfiguration) device.getDeviceConfiguration();
				this.device=device;
			}
		}
		this.camaraConfParam = new CameraConfParam();
		return SUCCESS;
	}

	public String updateCameraConfiguration() throws Exception {
		this.device.setDeviceConfiguration(this.cameraConfiguration);
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String message=IMonitorUtil.sendToController("commandIssueService", "updateCameraSystemConfiguration", xmlString);	
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message",message );	
		return SUCCESS;
	}
	
	public String cameraPanTilt() throws Exception {
//		Write the code here to tilt the camera.
		return SUCCESS;
	}
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public CameraConfiguration getCameraConfiguration() {
		return cameraConfiguration;
	}

	public void setCameraConfiguration(CameraConfiguration cameraConfiguration) {
		this.cameraConfiguration = cameraConfiguration;
	}

	public CameraConfParam getCamaraConfParam() {
		return camaraConfParam;
	}

	public void setCamaraConfParam(CameraConfParam camaraConfParam) {
		this.camaraConfParam = camaraConfParam;
	}
}
