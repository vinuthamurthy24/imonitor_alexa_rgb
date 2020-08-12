/* Copyright  2012 iMonitor Solutions India Private Limited */

package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * 
 * @author sumit kumar
 *
 */
public class AVBlaster {

	private long id;
	private AVCategory category;
	private String region;
	private String brand;
	private String code;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public AVCategory getCategory() {
		return category;
	}
	public void setCategory(AVCategory category) {
		this.category = category;
	}
}
