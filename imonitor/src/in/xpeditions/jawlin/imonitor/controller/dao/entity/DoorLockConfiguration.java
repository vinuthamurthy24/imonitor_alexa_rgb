package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class DoorLockConfiguration {
	
	private long id;
	private Device device;
	private String userpassword;
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
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

}
