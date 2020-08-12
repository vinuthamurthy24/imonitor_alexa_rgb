package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class ImvgAlertNotificationForScedule {

	private long id;
	private UserNotificationProfile userNotificationProfile;
	private Schedule schedule;
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
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
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
