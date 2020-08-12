/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.util.LogUtil;

public class ShowNotification {
	
	private String name;
	private String action;
	private long email;
	private long sms;
	private long whatsApp;
	private boolean emailCheck;
	private boolean smsCheck;
	private boolean isEmailDefined;
	private boolean isSmsDefined;
	private boolean isSmsAndEmailDefined;
	private boolean whatsAppCheck;
	private boolean isWhatsAppDefined;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public boolean getEmailCheck() {
		return emailCheck;
	}
	public void setEmailCheck(boolean emailCheck) {
		
		this.emailCheck = emailCheck;
	}
	public void setEmailCheck(String emailCheck) {
		
		//this.emailCheck = emailCheck;
	}
	public boolean getSmsCheck() {
		return smsCheck;
	}
	public void setSmsCheck(boolean smsCheck) {
		
		this.smsCheck = smsCheck;
	}
	public void setSmsCheck(String smsCheck) {
		
		//this.smsCheck = smsCheck;
	}
	public boolean getIsEmailDefined() {
		return isEmailDefined;
	}
	public void setIsEmailDefined(boolean isEmailDefined) {
		this.isEmailDefined = isEmailDefined;
	}
	public boolean getIsSmsDefined() {
		return isSmsDefined;
	}
	public void setIsSmsDefined(boolean isSmsDefined) {
		this.isSmsDefined = isSmsDefined;
	}
	public long getEmail() {
		return email;
	}
	public void setEmail(long email) {
		this.email = email;
	}
	public void setEmail(String email) {
		this.email = Integer.parseInt(email);
	}
	public long getSms() {
		return sms;
	}
	public void setSms(long sms) {
		this.sms = sms;
	}
	public void setSms(String sms) {
		this.sms = Integer.parseInt(sms);
	}
	public boolean getisSmsAndEmailDefined() {
		return isSmsAndEmailDefined;
	}
	public void setSmsAndEmailDefined(boolean isSmsAndEmailDefined) {
		this.isSmsAndEmailDefined = isSmsAndEmailDefined;
	}
	public boolean getWhatsAppCheck() {
		return whatsAppCheck;
	}
	public void setWhatsAppCheck(boolean whatsAppCheck) {
		
		this.whatsAppCheck = whatsAppCheck;
	}
	public void setWhatsAppCheck(String whatsAppCheck) {
		
		//this.whatsAppCheck = whatsAppCheck;
	}
	public boolean getisWhatsAppDefined() {
		return isWhatsAppDefined;
	}
	public void setWhatsAppDefined(boolean isWhatsAppDefined) {
		this.isWhatsAppDefined = isWhatsAppDefined;
	}
	public long getWhatsApp() {
		return whatsApp;
	}
	public void setWhatsApp(long whatsApp) {
		this.whatsApp = whatsApp;
	}
	
}
