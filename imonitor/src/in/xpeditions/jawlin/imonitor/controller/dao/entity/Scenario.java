/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Set;

public class Scenario {

	private long id;
	private String name;
	private String description;
	private Set<ScenarioAction> scenarioActions;
	private GateWay gateWay;
	private String iconFile; 
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<ScenarioAction> getScenarioActions() {
		return scenarioActions;
	}
	public void setScenarioActions(Set<ScenarioAction> scenarioActions) {
		this.scenarioActions = scenarioActions;
	}
	public GateWay getGateWay() {
		return gateWay;
	}
	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}
	//pari added icon file
	public String getIconFile() {
		return iconFile;
	}
	public void setIconFile(String iconFile) {
		this.iconFile = iconFile;
	}
	//pari end
}