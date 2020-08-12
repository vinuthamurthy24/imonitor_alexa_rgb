/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author computer
 *
 */
public class DeviceAlert {
	private long id;
	private Device device;
	private String comparatorName;
	private Rule rule;
	private AlertType alertType;
	private long endTime;
	private long startTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getComparatorName() {
		return comparatorName;
	}
	public void setComparatorName(String comparatorName) {
		this.comparatorName = comparatorName;
	}
	public AlertType getAlertType() {
		return alertType;
	}
	public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
	}
	public final Rule getRule() {
		return rule;
	}
	public final void setRule(Rule rule) {
		this.rule = rule;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
}