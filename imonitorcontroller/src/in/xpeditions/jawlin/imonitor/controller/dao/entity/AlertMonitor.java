package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Date;



public class AlertMonitor {
	
	private long id;
	private Date alertTime;
	private AreaCode areaCode;
	private Customer customer;
	private Rule rule;
	private AlertStatus alertStatus;
	private String attended;
	private String contactnumber;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public AreaCode getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(AreaCode areaCode) {
		this.areaCode = areaCode;
	}
	public Rule getRule() {
		return rule;
	}
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	public AlertStatus getAlertStatus() {
		return alertStatus;
	}
	public void setAlertStatus(AlertStatus alertStatus) {
		this.alertStatus = alertStatus;
	}
	public Date getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(Date alertTime) {
		this.alertTime = alertTime;
	}
	public String getContactnumber() {
		return contactnumber;
	}
	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}
	public String getAttended() {
		return attended;
	}
	public void setAttended(String attended) {
		this.attended = attended;
	}
	

}
