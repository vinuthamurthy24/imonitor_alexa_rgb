/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Set;

public class Schedule {

	private long id;
	private String name;
	private String description;
	private String scheduleTime;
	private Set<ScheduleAction> scheduleActions;
	private Set<ImvgAlertNotificationForScedule> imvgAlertNotificationsForScedule;
	private GateWay gateWay;
	//sumit start: Schedule Activate/De-Activate
	private int activationStatus;		//1 for active, -1 for inactive, by default it should be active.
	private long endSchedule;
	private int scheduleType; // 0 for start schedule, 1 for schedule with start and end time
	
	public int getActivationStatus(){
		return this.activationStatus;
	}
	public void setActivationStatus(int activationStatus){
		this.activationStatus = activationStatus;
	}
	//sumit end: Schedule Activate/De-Activate
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<ScheduleAction> getScheduleActions() {
		return scheduleActions;
	}
	public void setScheduleActions(Set<ScheduleAction> scheduleActions) {
		this.scheduleActions = scheduleActions;
	}
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public String getScheduleTime() {
		return scheduleTime;
	}
	public GateWay getGateWay() {
		return gateWay;
	}
	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}
	public Set<ImvgAlertNotificationForScedule> getImvgAlertNotificationsForScedule() {
		return imvgAlertNotificationsForScedule;
	}
	public void setImvgAlertNotificationsForScedule(
			Set<ImvgAlertNotificationForScedule> imvgAlertNotificationsForScedule) {
		this.imvgAlertNotificationsForScedule = imvgAlertNotificationsForScedule;
	}
	public long getEndSchedule() {
		return endSchedule;
	}
	public void setEndSchedule(long endSchedule) {
		this.endSchedule = endSchedule;
	}
	public int getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(int scheduleType) {
		this.scheduleType = scheduleType;
	}
	
	
}
