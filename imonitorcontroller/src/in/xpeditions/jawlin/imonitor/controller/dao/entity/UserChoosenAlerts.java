/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class UserChoosenAlerts {
	
	
	private long id;
	private String alerts;
	private GateWay gateway;
	//private Device device;
	private String device;
	//private AlertType alerts;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	
	
	public GateWay getGateway() {
		return gateway;
	}
	public void setGateway(GateWay gateway) {
		this.gateway = gateway;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}

	public String getAlerts() {
		return alerts;
	}
	public void setAlerts(String alerts) {
		this.alerts = alerts;
	}

}
