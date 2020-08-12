/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author computer
 *
 */
public class ImvgAlertsAction {
	
	private long id;
	private Device device;
	private ActionType actionType;
	private Rule rule;
	private String level;
	private int afterDelay;
	
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
	public final Rule getRule() {
		return rule;
	}
	public final void setRule(Rule rule) {
		this.rule = rule;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getAfterDelay() {
		return afterDelay;
	}
	public void setAfterDelay(int afterDelay) {
		this.afterDelay = afterDelay;
	}
}
