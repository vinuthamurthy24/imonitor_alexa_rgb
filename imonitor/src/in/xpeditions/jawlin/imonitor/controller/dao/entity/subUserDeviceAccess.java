/* Copyright  2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * 
 * @author sumit kumar
 *
 */
public class subUserDeviceAccess {

	private long id;
	private User user;
	private Device device;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}

	
	
}
