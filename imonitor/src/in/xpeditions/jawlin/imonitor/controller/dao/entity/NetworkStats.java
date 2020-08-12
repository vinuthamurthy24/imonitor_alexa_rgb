package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Set;

public class NetworkStats
{
	private long id;
	private String imvgId;
	private Device device;
	private String rssi;
	private long retries;
	private long nc;
	private long rc;
	private Set<RssiInfo> rssiInfosList;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImvgId() {
		return imvgId;
	}
	public void setImvgId(String imvgId) {
		this.imvgId = imvgId;
	}
	
	
	public String getRssi() {
		return rssi;
	}
	public void setRssi(String rssi) {
		this.rssi = rssi;
	}
	public long getRetries() {
		return retries;
	}
	public void setRetries(long retries) {
		this.retries = retries;
	}
	
	public long getNc() {
		return nc;
	}
	public void setNc(long nc) {
		this.nc = nc;
	}
	public long getRc() {
		return rc;
	}
	public void setRc(long rc) {
		this.rc = rc;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Set<RssiInfo> getRssiInfosList() {
		return rssiInfosList;
	}
	public void setRssiInfosList(Set<RssiInfo> rssiInfosList) {
		this.rssiInfosList = rssiInfosList;
	}
	
}
