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
public class LoginAction extends ActionSupport {

	private String data;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2697137989391135646L;

	@Override
	public String execute() throws Exception {
		String serviceName = "mobileService";
		String method = "authenticate";
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
