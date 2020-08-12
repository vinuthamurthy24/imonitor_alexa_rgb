/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class ScheduleTime {

	private long id;
	private String houre;
	private String minute;
	private String scheduledDay;
	private Schedule schedule;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getScheduledDay() {
		return scheduledDay;
	}
	public void setScheduledDay(String scheduledDay) {
		this.scheduledDay = scheduledDay;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public Schedule getSchedule() {
		return schedule;
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
	
	
}
