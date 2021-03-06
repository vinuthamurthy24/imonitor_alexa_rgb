/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author computer
 *
 */
public class ScheduleAction {
	
	private long id;
	private Device device;
	private ActionType actionType;
	private String level;
	private Schedule schedule;
	
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
	public ActionType getActionType() {
		return actionType;
	}
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public Schedule getSchedule() {
		return schedule;
	}
}
