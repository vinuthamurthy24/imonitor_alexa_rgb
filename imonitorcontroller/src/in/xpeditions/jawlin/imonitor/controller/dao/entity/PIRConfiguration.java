/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;
/**
 * 
 * @author sumit
 *
 */
public class PIRConfiguration extends DeviceConfiguration {
	private String noMotionDetectionTimeOut;
	private String pollingInterval;
	
	public String getNoMotionDetectionTimeOut() {
		return noMotionDetectionTimeOut;
	}
	public void setNoMotionDetectionTimeOut(String noMotionDetectionTimeOut) {
		this.noMotionDetectionTimeOut = noMotionDetectionTimeOut;
	}
	public String getPollingInterval() {
		return pollingInterval;
	}
	public void setPollingInterval(String pollingInterval) {
		this.pollingInterval = pollingInterval;
	}
}
