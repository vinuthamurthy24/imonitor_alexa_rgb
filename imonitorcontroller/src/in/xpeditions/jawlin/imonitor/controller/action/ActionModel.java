/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import java.util.Queue;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DoorLockConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

/**
 * @author Coladi
 *
 */
public class ActionModel {
	private Device device;
	private Object data;
	private Queue<KeyValuePair> queue;
	private String message;
	private String timeout;
	private User user;
	private DoorLockConfiguration doorLockConfig;
	
	public ActionModel(Device device, Object data) {
		this.device = device;
		this.data = data;
	}
	
	public ActionModel(Device device, Queue<KeyValuePair> queue) {
		this.device = device;
		this.queue = queue;
	}
	
	public ActionModel(Device device2, String selectedvalue, String timeout) {
		this.device = device2;
		this.data = selectedvalue;
		this.timeout=timeout;
	}

	public ActionModel(GateWay gateway,User saveduser) {
		// TODO Auto-generated constructor stub
		this.setUser(saveduser);
		this.data = gateway;
		
	}
	


	
	public ActionModel(DoorLockConfiguration doorLockConfiguration) {
		// TODO Auto-generated constructor stub
		
		this.setDoorLockConfig(doorLockConfiguration);
	}

	public final Device getDevice() {
		return device;
	}
	public final void setDevice(Device device) {
		this.device = device;
	}
	public final Object getData() {
		return data;
	}
	public final void setData(Object data) {
		this.data = data;
	}

	public final Queue<KeyValuePair> getQueue() {
		return queue;
	}

	public final void setQueue(Queue<KeyValuePair> queue) {
		this.queue = queue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DoorLockConfiguration getDoorLockConfig() {
		return doorLockConfig;
	}

	public void setDoorLockConfig(DoorLockConfiguration doorLockConfig) {
		this.doorLockConfig = doorLockConfig;
	}

	
}
