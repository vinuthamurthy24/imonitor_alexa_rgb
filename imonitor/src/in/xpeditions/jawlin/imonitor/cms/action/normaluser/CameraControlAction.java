/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class CameraControlAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Device device;
	private CameraConfiguration cameraConfiguration;

	public String updateNightVisionControl() throws Exception {
		this.device.setDeviceConfiguration(this.cameraConfiguration);
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		IMonitorUtil.sendToController("commandIssueService", "updateCameraNightVisionControl", xmlString);
		return SUCCESS;
	}

	public String updatePanTiltControl() throws Exception {
		this.device.setDeviceConfiguration(this.cameraConfiguration);
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		IMonitorUtil.sendToController("commandIssueService", "updatePanTiltControl", xmlString);
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
}
