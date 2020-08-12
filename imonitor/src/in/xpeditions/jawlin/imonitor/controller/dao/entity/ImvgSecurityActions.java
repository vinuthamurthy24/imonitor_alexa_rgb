package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class ImvgSecurityActions {
	
	private long id;
	private Customer customer;
	private GateWay gateway;
	private Device device;
	private ActionType actionType;
	private String level;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public GateWay getGateway() {
		return gateway;
	}
	public void setGateway(GateWay gateway) {
		this.gateway = gateway;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public ActionType getActionType() {
		return actionType;
	}
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

}
