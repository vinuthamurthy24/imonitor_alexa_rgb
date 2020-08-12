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
public class GateWay {
	private long id; // id - auto increment
	private String macId;
	private String localIp;
	private GateWayType gateWayType;
	private Status status;
	private Date entryDate;
	private String remarks;
	private Agent agent;
	private Set<Device> devices;
	private Customer customer;
	// ******************************************************************* sumit start ************************************************************
	private Device alertDevice;
	private String currentMode;
	private String delayHome;
	private String delayStay;
	
	private String delayAway;
	// ******************************************************************* sumit end **********************************************************
	private String maintenancemode;
	private String gateWayVersion;
	private long FirmwareId;
	private String Latestversion;
	private long allOnOffStatus;
	private String name;
	private String SSID;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public GateWayType getGateWayType() {
		return gateWayType;
	}
	public void setGateWayType(GateWayType gateWayType) {
		this.gateWayType = gateWayType;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Set<Device> getDevices() {
		return devices;
	}
	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}
	public final String getLocalIp() {
		return localIp;
	}
	public final void setLocalIp(String localIp) {
		this.localIp = localIp;
	}
	//sumit start *************************************************************
	public String getDelayHome() {
		return delayHome;
	}
	public void setDelayHome(String delayHome) {
		this.delayHome = delayHome;
	}
	public String getDelayStay() {
		return delayStay;
	}
	public void setDelayStay(String delayStay) {
		this.delayStay = delayStay;
	}
	public String getDelayAway() {
		return delayAway;
	}
	public void setDelayAway(String delayAway) {
		this.delayAway = delayAway;
	}
	public String getCurrentMode() {
		return currentMode;
	}
	public void setCurrentMode(String currentMode) {
		this.currentMode = currentMode;
	}

	public Device getAlertDevice() {
		return alertDevice;
	}
	public void setAlertDevice(Device alertDevice) {
		this.alertDevice = alertDevice;
	}
	//sumit end *************************************************************
	public String getMaintenancemode() {
		return maintenancemode;
	}
	public void setMaintenancemode(String maintenancemode) {
		this.maintenancemode = maintenancemode;
	}
	//parishod start
	public String getGateWayVersion() {
		return gateWayVersion;
	}
	public void setGateWayVersion(String gateWayVersion) {
		this.gateWayVersion = gateWayVersion;
	}
	//parishod end
	
	public String getLatestversion() {
		return Latestversion;
	}
	public void setLatestversion(String latestversion) {
		Latestversion = latestversion;
	}
	public long getFirmwareId() {
		return FirmwareId;
	}
	public void setFirmwareId(long firmwareId) {
		FirmwareId = firmwareId;
	}
	public long getAllOnOffStatus() {
		return allOnOffStatus;
	}
	public void setAllOnOffStatus(long allOnOffStatus) {
		this.allOnOffStatus = allOnOffStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSSID() {
		return SSID;
	}
	public void setSSID(String sSID) {
		SSID = sSID;
	}
}
