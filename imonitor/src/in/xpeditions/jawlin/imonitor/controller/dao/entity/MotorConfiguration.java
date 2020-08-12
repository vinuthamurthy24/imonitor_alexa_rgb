/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class MotorConfiguration extends DeviceConfiguration {
	private long length;
	private int mountingtype;
	
	public int getMountingtype() {
		return mountingtype;
	}
	public void setMountingtype(int mountingtype) {
		this.mountingtype = mountingtype;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
}
