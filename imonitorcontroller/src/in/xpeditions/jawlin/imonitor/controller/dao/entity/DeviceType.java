/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.List;

/**
 * @author Coladi
 *
 */
public class DeviceType {
	private long id; // id - auto incement.
	private String name;
	private String details;
	private String category; //on-off, dimmer, temperature etc
	private String iconFile;
	private List<AlertType> alertTypes;
	private List<ActionType> actionTypes;
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<AlertType> getAlertTypes() {
		return alertTypes;
	}
	public void setAlertTypes(List<AlertType> alertTypes) {
		this.alertTypes = alertTypes;
	}
	public List<ActionType> getActionTypes() {
		return actionTypes;
	}
	public void setActionTypes(List<ActionType> actionTypes) {
		this.actionTypes = actionTypes;
	}
}
