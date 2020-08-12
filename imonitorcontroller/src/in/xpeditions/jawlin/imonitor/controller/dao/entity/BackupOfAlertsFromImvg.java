package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Date;
import java.util.Set;

public class BackupOfAlertsFromImvg {

	private long id;
	private Device device;
	private AlertType alertType;
	private Date alertTime;
	private long alarmStatus;
	private String alertValue;
	private Set<UploadsByImvg> uploadsByImvgs;
	
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public AlertType getAlertType() {
		return alertType;
	}
	public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
	}
	public Date getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(Date alertTime) {
		this.alertTime = alertTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setUploadsByImvgs(Set<UploadsByImvg> uploadsByImvgs) {
		this.uploadsByImvgs = uploadsByImvgs;
	}
	public Set<UploadsByImvg> getUploadsByImvgs() {
		return uploadsByImvgs;
	}
	public long getAlarmStatus() {
		return alarmStatus;
	}
	public void setAlarmStatus(long alarmStatus) {
		this.alarmStatus = alarmStatus;
	}
	public String getAlertValue() {
		return alertValue;
	}
	public void setAlertValue(String alertValue) {
		this.alertValue = alertValue;
	}
	
}
