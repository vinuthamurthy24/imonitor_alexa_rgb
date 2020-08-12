/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class ScheduledScenarios {
	
	private long id;
	private long scheduledId;
	private long scenarioId;
	
	public long getScheduledId() {
		return scheduledId;
	}
	public void setScheduledId(long scheduledId) {
		this.scheduledId = scheduledId;
	}
	public long getScenarioId() {
		return scenarioId;
	}
	public void setScenarioId(long scenarioId) {
		this.scenarioId = scenarioId;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	
	
}
