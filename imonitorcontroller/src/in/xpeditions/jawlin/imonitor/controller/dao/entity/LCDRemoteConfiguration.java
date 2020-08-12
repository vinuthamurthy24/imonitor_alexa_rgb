/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;
/**
 * 
 * @author bhavya
 *
 */
public class LCDRemoteConfiguration extends DeviceConfiguration {
	private long f1;
	private long f2;
	private long f3;
	private String LCDRemoteConfiguration;
	
	public long getF1() {
		return f1;
	}
	public void setF1(long f1) {
		this.f1 = f1;
	}
	public long getF2() {
		return f2;
	}
	public void setF2(long f2) {
		this.f2 = f2;
	}
	public long getF3() {
		return f3;
	}
	public void setF3(long f3) {
		this.f3 = f3;
	}
	//bhavya start: LCD remote Feature
		public String getLCDRemoteConfiguration() {
			return LCDRemoteConfiguration;
		}
		public void setLCDRemoteConfiguration(String LCDRemoteConfiguration) {
			this.LCDRemoteConfiguration = LCDRemoteConfiguration;
		}
		//bhavya end: LCD remote Feature	
}
