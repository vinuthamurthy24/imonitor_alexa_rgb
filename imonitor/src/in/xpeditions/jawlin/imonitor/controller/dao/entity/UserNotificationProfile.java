/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author Coladi
 *
 */
public class UserNotificationProfile {
	private long id;
	private String name;
	private String recipient;
	private ActionType actionType;
	private Customer customer;
	private String EMail;
	private String SMS;
	private String countryCode;
	private String notifycheck;
	private Long waCheck;
	private long OTP;
	private Status status;
	private String whatsApp;
	private int whatsAppStatus;
	
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
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public ActionType getActionType() {
		return actionType;
	}
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getEMail() {
		return EMail;
	}
	public void setEMail(String eMail) {
		EMail = eMail;
	}
	public String getSMS() {
		return SMS;
	}
	public void setSMS(String sMS) {
		SMS = sMS;
	}
	public String getNotifycheck() {
		return notifycheck;
	}
	public void setNotifycheck(String notifycheck) {
		this.notifycheck = notifycheck;
	}
	public Long getWaCheck() {
		return waCheck;
	}
	public void setWaCheck(Long waCheck) {
		this.waCheck = waCheck;
	}
	public long getOTP() {
		return OTP;
	}
	public void setOTP(long oTP) {
		OTP = oTP;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getWhatsApp() {
		return whatsApp;
	}
	public void setWhatsApp(String whatsApp) {
		this.whatsApp = whatsApp;
	}
	public int getWhatsAppStatus() {
		return whatsAppStatus;
	}
	public void setWhatsAppStatus(int whatsAppStatus) {
		this.whatsAppStatus = whatsAppStatus;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

}
