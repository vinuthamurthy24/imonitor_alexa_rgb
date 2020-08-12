/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class TimeZone {

	private long id;
	private String name;
	private String houre;
	private String minute;
	private String pole;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getHoure() {
		return houre;
	}
	public void setHoure(String houre) {
		this.houre = houre;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getPole() {
		return pole;
	}
	public final void setPole(String pole) {
		this.pole = pole;
	}
}
