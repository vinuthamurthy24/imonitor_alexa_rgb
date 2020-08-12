/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Date;
import java.util.Set;

/**
 * @author Coladi
 *
 */
public class instantpow {
	private long id;
	private long device;
	private long alertType;
	private Date alertTime;
	private long alarmStatus;
	private String alertValue;
	private String alertTypeName;
	private String generatedDeviceId;
	
	
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
	public String getAlertTypeName() {
		return alertTypeName;
	}
	public void setAlertTypeName(String alertTypeName) {
		this.alertTypeName = alertTypeName;
	}
	public long getDevice() {
		return device;
	}
	public void setDevice(long device) {
		this.device = device;
	}
	public long getAlertType() {
		return alertType;
	}
	public void setAlertType(long alertType) {
		this.alertType = alertType;
	}
	public String getGeneratedDeviceId() {
		return generatedDeviceId;
	}
	public void setGeneratedDeviceId(String generatedDeviceId) {
		this.generatedDeviceId = generatedDeviceId;
	}
	
	
}
