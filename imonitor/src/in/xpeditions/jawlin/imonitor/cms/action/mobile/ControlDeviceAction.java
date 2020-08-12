/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.mobile;

import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Coladi
 * 
 */
public class ControlDeviceAction extends ActionSupport {

	private String data;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5649654060490242187L;

	@Override
	public String execute() throws Exception {
		String serviceName = "mobileService";
		String method = "controlDevice";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}

	public final String getData() {
		return data;
	}

	public final void setData(String data) {
		this.data = data;
	}
}
