/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Date;

/**
 * @author Coladi
 *
 */
public class powerInformationFromImvg {
	private long id;
	private Device device;
	private AlertType alertType;
	private Date alertTime;
	private long alarmStatus;
	private String alertValue;
	public Device getDevice(){
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
