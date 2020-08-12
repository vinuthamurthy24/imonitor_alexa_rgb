/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;


/**
 * @author Coladi
 * 
 */
public class ActionDataHolder {
	private String level;
	private GateWay gateWay;
	private Device device;
	
	public final String getLevel() {
		return level;
	}
	public final void setLevel(String level) {
		this.level = level;
	}
	public final GateWay getGateWay() {
		return gateWay;
	}
	public final void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
}
