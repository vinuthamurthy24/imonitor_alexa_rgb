/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Set;

/**
 * @author Coladi
 *
 */

public class Rule {
	private long id;
	private String name;
	private String description;
	private Set<DeviceAlert> deviceAlerts;
	private Set<ImvgAlertsAction> imvgAlertsActions;
	private Set<ImvgAlertNotification> imvgAlertNotifications;
	private GateWay gateWay;
	private String delay;
	//vibhu start
	private byte mode;
	//vibhu end
	private int alert;
	private int security;
	
	public final long getId() {
		return id;
	}
	public final void setId(long id) {
		this.id = id;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getDescription() {
		return description;
	}
	public final void setDescription(String description) {
		this.description = description;
	}
	public final Set<ImvgAlertsAction> getImvgAlertsActions() {
		return imvgAlertsActions;
	}
	public final void setImvgAlertsActions(Set<ImvgAlertsAction> imvgAlertsActions) {
		this.imvgAlertsActions = imvgAlertsActions;
	}
	public final Set<ImvgAlertNotification> getImvgAlertNotifications() {
		return imvgAlertNotifications;
	}
	public final void setImvgAlertNotifications(
			Set<ImvgAlertNotification> imvgAlertNotifications) {
		this.imvgAlertNotifications = imvgAlertNotifications;
	}
	public final GateWay getGateWay() {
		return gateWay;
	}
	public final void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}
	public final Set<DeviceAlert> getDeviceAlerts() {
		return deviceAlerts;
	}
	public final void setDeviceAlerts(Set<DeviceAlert> deviceAlerts) {
		this.deviceAlerts = deviceAlerts;
	}
	public String getDelay() {
		return delay;
	}
	public void setDelay(String delay) {
		this.delay = delay;
	}
//vibhu start	
	public byte getMode() {
		return mode;
	}
	public void setMode(byte mode) {
		this.mode = mode;
	}
//vibhu end
	public int getAlert() {
		return alert;
	}
	public void setAlert(int alert) {
		this.alert = alert;
	}
	public int getSecurity() {
		return security;
	}
	public void setSecurity(int security) {
		this.security = security;
	}
}
