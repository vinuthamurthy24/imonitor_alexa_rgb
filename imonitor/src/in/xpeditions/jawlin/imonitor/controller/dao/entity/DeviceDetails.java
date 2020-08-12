/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author bhavya
 *
 */
public class DeviceDetails {
	private long id;
	private String did;
	private String deviceName;
	private String deviceIcon;
	private String gateway;
	private String locationName;
	private long locationId;
	private long enableList;
	private String deviceType;
	private String alertParam;
	private String customer;
	private String  HMDpowerinfo;
	private List<Powerinformation> powerinformation;
	
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceIcon() {
		return deviceIcon;
	}
	public void setDeviceIcon(String deviceIcon) {
		this.deviceIcon = deviceIcon;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public long getEnableList() {
		return enableList;
	}
	public void setEnableList(long enableList) {
		this.enableList = enableList;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getAlertParam() {
		return alertParam;
	}
	public void setAlertParam(String alertParam) {
		this.alertParam = alertParam;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
//	public Set<Powerinformation> getPowerinformation() {
//		return powerinformation;
//	}
//	public void setPowerinformation(Set<Powerinformation> powerinformation) {
//		this.powerinformation = powerinformation;
//	}
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	public String getHMDpowerinfo() {
		return HMDpowerinfo;
	}
	public void setHMDpowerinfo(String hMDpowerinfo) {
		HMDpowerinfo = hMDpowerinfo;
	}
	public List<Powerinformation> getPowerinformation() {
		return powerinformation;
	}
	public void setPowerinformation(List<Powerinformation> powerinformation) {
		this.powerinformation = powerinformation;
	}
	
	
			
}
