/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author computer
 *
 */
public class ImvgAlertNotification {
	private long id;
	private UserNotificationProfile userNotificationProfile;
	private Rule rule;
	private String NotificationCheck;
	private Long WhatsApp;
	
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
	public final Rule getRule() {
		return rule;
	}
	public final void setRule(Rule rule) {
		this.rule = rule;
	}
	public String getNotificationCheck() {
		return NotificationCheck;
	}
	public void setNotificationCheck(String notificationCheck) {
		NotificationCheck = notificationCheck;
	}
	public Long getWhatsApp() {
		return WhatsApp;
	}
	public void setWhatsApp(Long whatsApp) {
		WhatsApp = whatsApp;
	}
}
