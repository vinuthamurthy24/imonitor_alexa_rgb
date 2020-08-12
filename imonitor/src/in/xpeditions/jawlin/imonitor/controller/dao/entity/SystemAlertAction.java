/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author computer
 *
 */
public class SystemAlertAction {
	
	private long id;
	private ActionType actionType;
	private User user;
	private SystemAlert systemAlert;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public ActionType getActionType() {
		return actionType;
	}
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setSystemAlert(SystemAlert systemAlert) {
		this.systemAlert = systemAlert;
	}
	public SystemAlert getSystemAlert() {
		return systemAlert;
	}

}
