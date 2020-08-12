package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class Action
{
	private long id;
	private String actionName;
	private long parameter;
	private Scenario scenario;
	private GateWay gateWay;
	private Device device;
	private long configurationId;
	private String configurationName;
	private Functions functions;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public long getParameter() {
		return parameter;
	}
	public void setParameter(long parameter) {
		this.parameter = parameter;
	}
	public GateWay getGateWay() {
		return gateWay;
	}
	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Functions getFunctions() {
		return functions;
	}
	public void setFunctions(Functions functions) {
		this.functions = functions;
	}
	public long getConfigurationId() {
		return configurationId;
	}
	public void setConfigurationId(long configurationId) {
		this.configurationId = configurationId;
	}
	public String getConfigurationName() {
		return configurationName;
	}
	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}
	public Scenario getScenario() {
		return scenario;
	}
	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}
}
