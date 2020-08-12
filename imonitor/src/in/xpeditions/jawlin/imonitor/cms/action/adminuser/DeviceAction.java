/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class DeviceAction extends ActionSupport {
	/**
	 * Asmodeus
	 */
	private static final long serialVersionUID = 1L;
	private Device device;
	private List<Make> makes;
	private List<DeviceType> deviceTypes;
	private List<String> selectedCommands;
	private DataTable dataTable;
	
	public String addDevice() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String serviceName = "deviceService";
		String method = "saveDevice";
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	}
	
	public String deleteDevice() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String serviceName = "deviceService";
		String method = "deleteDevice";
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String toAddDevice() throws Exception {
//		1. List makes.
		XStream stream = new XStream();
		String serviceName = "makeService";
		String method = "listMakes";
		String valueInXml = IMonitorUtil.sendToController(serviceName, method);
		this.makes = (List<Make>) stream.fromXML(valueInXml);
		
//		2. List device types.
		serviceName = "deviceTypeService";
		method = "listDeviceTypes";
		valueInXml = IMonitorUtil.sendToController(serviceName, method);
		this.deviceTypes = (List<DeviceType>) stream.fromXML(valueInXml);
		
		return SUCCESS;
	}
	
	public String toListDevices() throws Exception {
		return SUCCESS;
	}
	
	public String toListLogs() throws Exception {
		return SUCCESS;
	}
	public String listAskedDevices() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		xmlString += "-" + stream.toXML(this.device.getGateWay().getMacId());
		String serviceName = "deviceService";
		String method = "listDevicesUnderAGateWayByMacId";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public List<Make> getMakes() {
		return makes;
	}
	public void setMakes(List<Make> makes) {
		this.makes = makes;
	}
	public List<DeviceType> getDeviceTypes() {
		return deviceTypes;
	}
	public void setDeviceTypes(List<DeviceType> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}
	public List<String> getSelectedCommands() {
		return selectedCommands;
	}
	public void setSelectedCommands(List<String> selectedCommands) {
		this.selectedCommands = selectedCommands;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
}
