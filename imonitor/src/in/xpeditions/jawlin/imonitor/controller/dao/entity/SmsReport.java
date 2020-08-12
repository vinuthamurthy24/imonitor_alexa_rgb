/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Date;

public class SmsReport {

private long id;

private String sessionId;

private String mobileNumber;

private String submittedTime;

private Date sentTime;

private Date deliveryTime;

private String status;
private Customer customer;

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getSessionId() {
	return sessionId;
}

public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
}

public String getMobileNumber() {
	return mobileNumber;
}

public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
}

public String getSubmittedTime() {
	return submittedTime;
}

public void setSubmittedTime(String submittedTime) {
	this.submittedTime = submittedTime;
}

public Date getSentTime() {
	return sentTime;
}

public void setSentTime(Date sentTime) {
	this.sentTime = sentTime;
}

public Date getDeliveryTime() {
	return deliveryTime;
}

public void setDeliveryTime(Date deliveryTime) {
	this.deliveryTime = deliveryTime;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public Customer getCustomer() {
	return customer;
}

public void setCustomer(Customer customer) {
	this.customer = customer;
}
}
