/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author Coladi
 *
 */
public class GateWayType {
	private long id; // Auto increment
	private String modelDetails;
	private String techninalDescription;
	private Make make;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getModelDetails() {
		return modelDetails;
	}
	public void setModelDetails(String modelDetails) {
		this.modelDetails = modelDetails;
	}
	public String getTechninalDescription() {
		return techninalDescription;
	}
	public void setTechninalDescription(String techninalDescription) {
		this.techninalDescription = techninalDescription;
	}
	public Make getMake() {
		return make;
	}
	public void setMake(Make make) {
		this.make = make;
	}
}
