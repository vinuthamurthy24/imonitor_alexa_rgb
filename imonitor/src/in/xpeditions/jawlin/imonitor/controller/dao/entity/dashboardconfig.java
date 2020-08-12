/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author Coladi
 *
 */
public class dashboardconfig {
	private long id; // Auto increment
	private long customer;
	private long alertgenerationtimeinsec;
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public long getAlertgenerationtimeinsec() {
		return alertgenerationtimeinsec;
	}

	public void setAlertgenerationtimeinsec(long alertgenerationtimeinsec) {
		this.alertgenerationtimeinsec = alertgenerationtimeinsec;
	}

	

	

	
	


}
