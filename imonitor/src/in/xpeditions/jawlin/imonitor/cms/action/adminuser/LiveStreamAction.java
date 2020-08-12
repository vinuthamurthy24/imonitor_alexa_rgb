/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

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
	private static final long serialVersionUID = 5815974172981619946L;
	private String deviceId;
	private String liveStreams;
	@Override
	public String execute() throws Exception {
		String serviceName = "commandIssueService";
		String method = "listAllStreamsWithClients";
		this.liveStreams = IMonitorUtil.sendToController(serviceName, method);
		return SUCCESS;
	}
	public String killLiveStream() throws Exception {
		String serviceName = "commandIssueService";
		String method = "killLiveStream";
		IMonitorUtil.sendToController(serviceName, method, this.deviceId);
		return SUCCESS;
	}
	public final String getDeviceId() {
		return deviceId;
	}
	public final void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public final String getLiveStreams() {
		return liveStreams;
	}
	public final void setLiveStreams(String liveStreams) {
		this.liveStreams = liveStreams;
	}
}
