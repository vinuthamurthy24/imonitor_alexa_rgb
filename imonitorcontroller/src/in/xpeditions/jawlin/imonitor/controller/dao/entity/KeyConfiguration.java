package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class KeyConfiguration {
	
	private long id;
	private Device device;
	private String keyName;
	private String keyCode;
	private String pressType;
	private Action action;
	private long posindex;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getPressType() {
		return pressType;
	}
	public void setPressType(String pressType) {
		this.pressType = pressType;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public long getPosindex() {
		return posindex;
	}
	public void setPosindex(long posindex) {
		this.posindex = posindex;
	}
	

}
