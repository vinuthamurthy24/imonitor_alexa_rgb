/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;


/**
 * @author Coladi
 *
 */
public class Location {
	private long id;
	private String name;
	private String details;
	private String iconFile;
	private Customer customer;
	private String MobileIconFile;
	
	
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
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getIconFile() {
		return iconFile;
	}
	public void setIconFile(String iconFile) {
		this.iconFile = iconFile;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getMobileIconFile() {
		return MobileIconFile;
	}
	public void setMobileIconFile(String mobileIconFile) {
		MobileIconFile = mobileIconFile;
	}
}
