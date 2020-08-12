/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Date;


/**
 * @author Coladi
 *
 */
public class CustomerServices {
	
	private long id;
	private int serviceEnabled;
	private int Intimation;
	private String date;
	private String Reason;
	private String Description;
	private Customer customer;
	private String IntimationMail;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getServiceEnabled() {
		return serviceEnabled;
	}

	public void setServiceEnabled(int serviceEnabled) {
		this.serviceEnabled = serviceEnabled;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIntimationMail() {
		return IntimationMail;
	}

	public void setIntimationMail(String intimationMail) {
		IntimationMail = intimationMail;
	}

	public int getIntimation() {
		return Intimation;
	}

	public void setIntimation(int intimation) {
		Intimation = intimation;
	}

	

	
}