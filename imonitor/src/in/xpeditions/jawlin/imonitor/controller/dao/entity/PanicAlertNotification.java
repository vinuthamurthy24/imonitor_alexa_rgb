/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author computer
 *
 */
public class PanicAlertNotification {
	private long id;
	private UserNotificationProfile userNotificationProfile;
	private Panic panic;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public UserNotificationProfile getUserNotificationProfile() {
		return userNotificationProfile;
	}
	public void setUserNotificationProfile(
			UserNotificationProfile userNotificationProfile) {
		this.userNotificationProfile = userNotificationProfile;
	}

	public void setPanic(Panic panic) {
		this.panic = panic;
	}
	public Panic getPanic() {
		return panic;
	}
}
