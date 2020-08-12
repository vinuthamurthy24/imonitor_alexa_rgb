package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class GatewayStatus {
	
	private long id;
	private GateWay gateway;
	private Status status;
	private String alertTime;
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(String alertTime) {
		this.alertTime = alertTime;
	}

}
