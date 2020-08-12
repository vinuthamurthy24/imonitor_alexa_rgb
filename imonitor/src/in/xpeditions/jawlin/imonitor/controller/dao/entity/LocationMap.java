/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class LocationMap {
	private long id; 
	private String top;
	private String leftMap;
	private Device device;
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getLeftMap() {
		return leftMap;
	}
	public void setLeftMap(String leftMap) {
		this.leftMap = leftMap;
	}
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

}
