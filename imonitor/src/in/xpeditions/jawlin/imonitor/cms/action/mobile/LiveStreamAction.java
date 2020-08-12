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
public class LiveStreamAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5411463577712311246L;
	private String data;
	

	@Override
	public String execute() throws Exception {
		String serviceName = "mobileService";
		String method = "startLiveStreaming";
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
