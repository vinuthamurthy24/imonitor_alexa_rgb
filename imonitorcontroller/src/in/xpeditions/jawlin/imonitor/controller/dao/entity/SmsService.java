/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class SmsService {
	private long id;
	private String operatorcode;
	private String username;
	private String password;
	private String status;
	private String optin;
	private String optout;
	private String subnumber;
	public long getId() {
		return id;
	}
	public void setId(long id) 
	{
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOperatorcode() {
		return operatorcode;
	}
	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}
	public String getOptin() {
		return optin;
	}
	public void setOptin(String optin) {
		this.optin = optin;
	}
	public String getOptout() {
		return optout;
	}
	public void setOptout(String optout) {
		this.optout = optout;
	}
	public String getSubnumber() {
		return subnumber;
	}
	public void setSubnumber(String subnumber) {
		this.subnumber = subnumber;
	}
	
	
}
