/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class SystemAlertByUser {
	
	private long alertid;
	private long systemAlertId;
	private long userId;
	private String sms;
	private String email;
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public void setAlertid(long alertid) {
		this.alertid = alertid;
	}
	public long getAlertid() {
		return alertid;
	}
	public void setSystemAlertId(long systemAlertId) {
		this.systemAlertId = systemAlertId;
	}
	public long getSystemAlertId() {
		return systemAlertId;
	}
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
