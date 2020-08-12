/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.util;

/**
 * @author Coladi
 *
 */
public class ControlModel {
	private String trasactionId;
	private String controlCommand;
	private String deviceId;
	private String generatedDeviceId;
	private String commandParam;
	
	public ControlModel(String trasactionId, String controlCommand,
			String generatedDeviceId) {
		this.trasactionId = trasactionId;
		this.controlCommand = controlCommand;
		this.generatedDeviceId = generatedDeviceId;
	}
	public String getTrasactionId() {
		return trasactionId;
	}
	public void setTrasactionId(String trasactionId) {
		this.trasactionId = trasactionId;
	}
	public String getControlCommand() {
		return controlCommand;
	}
	public void setControlCommand(String controlCommand) {
		this.controlCommand = controlCommand;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getCommandParam() {
		return commandParam;
	}
	public void setCommandParam(String commandParam) {
		this.commandParam = commandParam;
	}
	public String getGeneratedDeviceId() {
		return generatedDeviceId;
	}
	public void setGeneratedDeviceId(String generatedDeviceId) {
		this.generatedDeviceId = generatedDeviceId;
	}
}
