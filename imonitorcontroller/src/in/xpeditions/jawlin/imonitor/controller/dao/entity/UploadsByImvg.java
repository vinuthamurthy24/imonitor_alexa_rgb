/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author Coladi
 *
 */
public class UploadsByImvg {
	private long id;
	private AlertsFromImvg alertsFromImvg;
	private String filePath;
	
	public final long getId() {
		return id;
	}
	public final void setId(long id) {
		this.id = id;
	}
	public final AlertsFromImvg getAlertsFromImvg() {
		return alertsFromImvg;
	}
	public final void setAlertsFromImvg(AlertsFromImvg alertsFromImvg) {
		this.alertsFromImvg = alertsFromImvg;
	}
	public final String getFilePath() {
		return filePath;
	}
	public final void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
