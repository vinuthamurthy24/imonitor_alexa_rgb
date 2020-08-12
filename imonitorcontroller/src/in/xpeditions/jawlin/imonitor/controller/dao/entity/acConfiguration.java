/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;
/**
 * 
 * @author bhavya
 *
 */
public class acConfiguration extends DeviceConfiguration {
	private String pollingInterval;
	private String fanModeValue;
	private String fanMode;
	private String acSwing;
	private String acLearnValue;
	private String sensedTemperature;
	
		public String getFanModeValue() {
			return fanModeValue;
		}
		public void setFanModeValue(String fanModeValue) {
			this.fanModeValue = fanModeValue;
		}
		public String getPollingInterval() {
			return pollingInterval;
		}
		public void setPollingInterval(String pollingInterval) {
			this.pollingInterval = pollingInterval;
		}
		public String getFanMode() {
			return fanMode;
		}
		public void setFanMode(String fanMode) {
			this.fanMode = fanMode;
		}
		public String getAcSwing() {
			return acSwing;
		}
		public void setAcSwing(String acSwing) {
			this.acSwing = acSwing;
		}
		public String getAcLearnValue() {
			return acLearnValue;
		}
		public void setAcLearnValue(String acLearnValue) {
			this.acLearnValue = acLearnValue;
		}
		public String getSensedTemperature() {
			return sensedTemperature;
		}
		public void setSensedTemperature(String sensedTemperature) {
			this.sensedTemperature = sensedTemperature;
		}
		
}
