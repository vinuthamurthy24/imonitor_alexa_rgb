/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author computer
 *
 */
public class TarrifConfig {
	private long id;
	private long customer;
	private String tarrifRate;
	private long startHour;
	private long endHour;
	private long startSlabRange;
	private long endSlabRange;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getStartHour() {
		return startHour;
	}
	public void setStartHour(long startHour) {
		this.startHour = startHour;
	}
	public long getEndHour() {
		return endHour;
	}
	public void setEndHour(long endHour) {
		this.endHour = endHour;
	}
	public String getTarrifRate() {
		return tarrifRate;
	}
	public void setTarrifRate(String tarrifRate) {
		this.tarrifRate = tarrifRate;
	}
	public long getStartSlabRange() {
		return startSlabRange;
	}
	public void setStartSlabRange(long startSlabRange) {
		this.startSlabRange = startSlabRange;
	}
	public long getEndSlabRange() {
		return endSlabRange;
	}
	public void setEndSlabRange(long endSlabRange) {
		this.endSlabRange = endSlabRange;
	}
	public long getCustomer() {
		return customer;
	}
	public void setCustomer(long customer) {
		this.customer = customer;
	}
}