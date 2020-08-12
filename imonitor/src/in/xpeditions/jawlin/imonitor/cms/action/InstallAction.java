/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action;

import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import com.opensymphony.xwork2.ActionSupport;

public class InstallAction extends ActionSupport {

	/**
	 * Asmodeus
	 */
	private static final long serialVersionUID = 1L;
	
	public String execute() throws Exception {
		String serviceName = "installService";
		String method = "install";
		IMonitorUtil.sendToController(serviceName, method);
		return "success";
	}

}
