/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import java.util.List;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Event;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class DeviceControlAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2378825267657311326L;

	private Device device;
	private String  StoredDeviceID;
	private GateWay gateWay;
	private String deviceStatusName;
	private String currentParam;
	private String nextParam;
	private String urlString;
	private String statusName;
	private String presetNumber;
	private String gateWayId;
	private String deviceid;
	private Event event;
	
	public String getCameraView() throws Exception{
		return SUCCESS;
	}
	public String GetStoredDevice() throws Exception{
		XStream stream = new XStream();
		GateWay gateway=new GateWay();
		gateway.setMacId(this.device.getGateWay().getMacId());
		
		String xmlString = stream.toXML(this.device.getGeneratedDeviceId());
		String result = IMonitorUtil.sendToController("deviceService", "getDeviceByGenetatedDevice", xmlString);
		this.device = (Device) stream.fromXML(result);
		this.device.setGateWay(gateway);
		return SUCCESS;
	}
	
	
	public String switchOnOff() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String result = IMonitorUtil.sendToController("commandIssueService", "switchOnOff", xmlString);
		this.device = (Device) stream.fromXML(result);
		/*
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String roleName=user.getRole().getName();
		if(roleName.equals(Constants.NORMAL_USER)){
			String serviceName2 = "userService";
			String method2 = "getMainUserAndSendSmsAndEmailing";
			String xmlMessage ="";
			 String actionXml = "";
			if(device.getCommandParam().equals("1")){
				 xmlMessage = stream.toXML(device.getGeneratedDeviceId()+":is On");
				 actionXml = stream.toXML(Constants.DEVICE_ON);
			}
			if(device.getCommandParam().equals("0")){
				xmlMessage = stream.toXML(device.getGeneratedDeviceId()+":is Off");
				actionXml = stream.toXML(Constants.DEVICE_OFF);
			}
			String xmlString2 = stream.toXML(user.getCustomer().getId());
			IMonitorUtil.sendToController(serviceName2, method2, xmlString2,xmlMessage,actionXml,xmlString);
		}*/
		return SUCCESS;
	}
	

	public String Curtainopenclose() throws Exception{
		
		XStream stream = new XStream();
		String xmlString = stream.toXML(device);
		String result = IMonitorUtil.sendToController("commandIssueService", "CurtainOpenClose", xmlString);
		this.device = (Device) stream.fromXML(result);
		return SUCCESS;
	}

public String deviceStatusUpdate() throws Exception{	
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.gateWayId);
		String result = IMonitorUtil.sendToController("commandIssueService", "deviceStatusUpdate", xmlString);
		result = IMonitorUtil.formatMessage(this, result);
		ActionContext.getContext().getSession().put("message", result);
		//ActionContext.getContext().getSession().put("message", result);
		return SUCCESS;
	}


	public String deviceArmDisarmStatusUpdate() throws Exception{
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		this.device.getGateWay().setCustomer(customer);
		XStream stream = new XStream();
		String deviceXml = stream.toXML(this.device);
		String result = IMonitorUtil.sendToController("commandIssueService", "deviceArmDisarmStatusUpdate",deviceXml);
		this.device = (Device) stream.fromXML(result);
		return SUCCESS;
	}
	
	public String deviceDisarmStatusUpdate() throws Exception{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String customerXml = stream.toXML(customer);
		String statusXml = stream.toXML(this.statusName);
		String message = IMonitorUtil.sendToController("commandIssueService", "deviceDisarmStatusUpdate",customerXml,statusXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message",message);
		return SUCCESS;
	}
	
	// ************************************************************* sumit begin **************************************************************
	@SuppressWarnings("unchecked")
	public String activateMode() throws Exception{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		
		String customerXml = stream.toXML(customer);
		String statusXml = stream.toXML(this.statusName);

		String message = IMonitorUtil.sendToController("commandIssueService", "activateMode", customerXml, statusXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message",message);
		return SUCCESS;
	}
	
	//*********pari start
	@SuppressWarnings("unchecked")
	public String activateSet() throws Exception{
		XStream stream = new XStream();
		//LogUtil.info("Am in entry ofactivateSet");

		String deviceId = this.device.getGeneratedDeviceId();
		String xmlString = stream.toXML(deviceId);
		
		//LogUtil.info("xmlString value of dev id is"+xmlString);

		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		
		String customerXml = stream.toXML(customer);
		//LogUtil.info("Preset nor in "+preset);

		String presetXml = stream.toXML(this.presetNumber);
		//LogUtil.info("Preset nor in xml "+presetXml);
				
		String message = "";
		
		message = IMonitorUtil.sendToController("commandIssueService", "activateSet", customerXml, presetXml,xmlString);
			//LogUtil.info("Am at end of activateset");
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message",message);
		return SUCCESS;
	}
	
	
	public String activateMove() throws Exception{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		
		/*String customerId = user.getCustomer().getCustomerId();
		String customerIdXml = stream.toXML(customerId);
		String preset = this.presetNumber;*/
		String deviceId = this.device.getGeneratedDeviceId();
		String customerXml = stream.toXML(customer);
		String presetXml = stream.toXML(this.presetNumber);
		String deviceidXml = stream.toXML(deviceId);
		String message = "";
		
			message = IMonitorUtil.sendToController("commandIssueService", "activateMove", customerXml, presetXml,deviceidXml);
		
			
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message",message);
		return SUCCESS;
	}
	//*********pari end
	public String dimmer() throws Exception{
		XStream stream = new XStream();
		String devceXmlString = stream.toXML(device);
		String deviceXml = IMonitorUtil.sendToController("commandIssueService", "dimmer", devceXmlString);
		this.device = (Device) stream.fromXML(deviceXml);
		return SUCCESS;
	}
	
	public String temperatureSensor() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(device);
		IMonitorUtil.sendToController("commandIssueService", "temperatureSensor", xmlString);
		return SUCCESS;
		
	}
	
	public String getLiveStream() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(device);
		this.urlString = IMonitorUtil.sendToController("commandIssueService", "getLiveStream", xmlString);
		/*
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String roleName=user.getRole().getName();
		if(roleName.equals(Constants.NORMAL_USER)){
			String serviceName2 = "userService";
			String method2 = "getMainUserAndSendSmsAndEmailing";
			String xmlMessage = stream.toXML(device.getGeneratedDeviceId()+":was Started the streaming");
			String xmlString2 = stream.toXML(user.getCustomer().getId());
			String actionXml = stream.toXML(Constants.STREAMING_STARTED);
			IMonitorUtil.sendToController(serviceName2, method2, xmlString2,xmlMessage,actionXml,xmlString);
		}*/
		return SUCCESS;
		
	}
	
	public String stopStreaming() throws Exception{
		/*
		XStream stream = new XStream();
		String xmlString = stream.toXML(device);
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String roleName=user.getRole().getName();
		if(roleName.equals(Constants.NORMAL_USER)){
			String serviceName2 = "userService";
			String method2 = "getMainUserAndSendSmsAndEmailing";
			String xmlMessage = stream.toXML(device.getGeneratedDeviceId()+":was Stopped the streaming");
			String xmlString2 = stream.toXML(user.getCustomer().getId());
			String actionXml = stream.toXML(Constants.STREAMING_STOPPED);
			IMonitorUtil.sendToController(serviceName2, method2, xmlString2,xmlMessage,actionXml,xmlString);
		}*/
		return SUCCESS;
		
	}
	
	public String getStoredStream() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(device);
		IMonitorUtil.sendToController("commandIssueService", "getStoredStream", xmlString);
		return SUCCESS;
		
	}
	
	public String captureIamge() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(device);
		IMonitorUtil.sendToController("commandIssueService", "captureIamge", xmlString);
		return SUCCESS;
		
	}
	
	public String retrieveTemperature() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(device);
		String deviceXml = IMonitorUtil.sendToController("commandIssueService", "retrieveTemperature", xmlString);
		this.device = (Device) stream.fromXML(deviceXml);
		return SUCCESS;
		
	}
	
	public String remoteReboot() throws Exception{
		XStream stream = new XStream();
		String message ="";
		String xmlString = stream.toXML(this.gateWayId);
		IMonitorUtil.sendToController("commandIssueService", "remoteReboot", xmlString);
		message = IMonitorUtil.formatMessage(this, "msg.imonitor.homereboot.0001");
		ActionContext.getContext().getSession().put("message", message);
		
		//String message = "Sucessfully IMVG Rebooted";
		//ActionContext.getContext().getSession().put("message",message);
		return SUCCESS;
	}
	public String ScanNow() throws Exception{
		XStream stream = new XStream();
		String message ="";
		String xmlString = stream.toXML(this.gateWayId);
		IMonitorUtil.sendToController("commandIssueService", "remoteReboot", xmlString);
		message = IMonitorUtil.formatMessage(this, "msg.imonitor.homereboot.0001");
		ActionContext.getContext().getSession().put("message", message);
		
		return SUCCESS;
	}
	
	public String deviceEnableDisableStatusUpdate() throws Exception{
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
	
		Customer customer = user.getCustomer();
		
		this.device.getGateWay().setCustomer(customer);
		XStream stream = new XStream();
		String deviceXml = stream.toXML(device);
		
		String result = IMonitorUtil.sendToController("commandIssueService", "EnableStatusUpdate",deviceXml);
		this.device = (Device) stream.fromXML(result);
		return SUCCESS;
	}
	
	public String lockUnlock() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String result = IMonitorUtil.sendToController("commandIssueService", "lockUnlock", xmlString);
		this.device = (Device) stream.fromXML(result);

		return SUCCESS;
	}
	
	
	public String killLiveStream() throws Exception {
		String serviceName = "commandIssueService";
		String method = "killLiveStream";
		IMonitorUtil.sendToController(serviceName, method, device.getGeneratedDeviceId());
		return SUCCESS;
	}
	
	public String allOnOff() throws Exception{
		XStream stream = new XStream();
		String message ="";
		String xmlString = stream.toXML(this.gateWay);
		String result=IMonitorUtil.sendToController("commandIssueService", "AllONoff", xmlString);

		if(result.contains("YES"))
		{
			if(this.gateWay.getAllOnOffStatus() == 1)
			{
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.homereon");
				
			}
			else
			{
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.homereoff");
				
			}
			
		}
		else
		{
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.homereonofffailure");
		}
		
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
/*Added by naveen
	 * Date: 24th Feb
	 * To open the curtain till mouse is released
	 
	 */
public String Curtainopenstart() throws Exception{
		
		XStream stream = new XStream();
		String xmlString = stream.toXML(device);
		
		String result = IMonitorUtil.sendToController("commandIssueService", "CurtainOpenStart", xmlString);
		this.device = (Device) stream.fromXML(result);
		return SUCCESS;
	}

public String Curtainopenstop() throws Exception{
	
	XStream stream = new XStream();
	String xmlString = stream.toXML(device);
	//LogUtil.info("device: "+ xmlString);
	String result = IMonitorUtil.sendToController("commandIssueService", "CurtainOpenStop", xmlString);
	this.device = (Device) stream.fromXML(result);
	return SUCCESS;
}

public String Curtainclosestart() throws Exception{
	
	XStream stream = new XStream();
	String xmlString = stream.toXML(device);
	//LogUtil.info("device: "+ xmlString);
	String result = IMonitorUtil.sendToController("commandIssueService", "CurtainCloseStart", xmlString);
	this.device = (Device) stream.fromXML(result);
	return SUCCESS;
}

	public String Curtainclosestop() throws Exception{

		XStream stream = new XStream();
		String xmlString = stream.toXML(device);
		String result = IMonitorUtil.sendToController("commandIssueService", "CurtainCloseStop", xmlString);
		this.device = (Device) stream.fromXML(result);
		return SUCCESS;
	}
	
 public String CurtainSliderControl() throws Exception{
		
		XStream stream = new XStream();
		String xmlString = stream.toXML(device);
		String result = IMonitorUtil.sendToController("commandIssueService", "CurtainSliderControl", xmlString);
		this.device = (Device) stream.fromXML(result);
		return SUCCESS;
	}
        
        public String Curtainfullopenclose() throws Exception{
    		
    		XStream stream = new XStream();
    		String xmlString = stream.toXML(device);
    		String result = IMonitorUtil.sendToController("commandIssueService", "CurtainOpenClose", xmlString);
    		this.device = (Device) stream.fromXML(result);
    		return SUCCESS;
    	}

	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getDeviceId() {
		return deviceid;
	}

	public void setDeviceId(String device) {
		this.deviceid = device;
	}

	public String getCurrentParam() {
		return currentParam;
	}

	public void setCurrentParam(String currentParam) {
		this.currentParam = currentParam;
	}

	public String getNextParam() {
		return nextParam;
	}

	public void setNextParam(String nextParam) {
		this.nextParam = nextParam;
	}

	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}

	public GateWay getGateWay() {
		return gateWay;
	}

	public void setDeviceStatusName(String deviceStatusName) {
		this.deviceStatusName = deviceStatusName;
	}

	public String getDeviceStatusName() {
		return deviceStatusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return statusName;
	}

        public void setPresetNumber(String presetNumber) {
		this.presetNumber = presetNumber;
	}

	public String getPresetNumber() {
		return presetNumber;
	}

	public void setGateWayId(String gateWayId) {
		this.gateWayId = gateWayId;
	}

	public String getGateWayId() {
		return gateWayId;
	}

	public String getStoredDevice() {
		return StoredDeviceID;
	}

	public void setStoredDevice(String storedDevice) {
		StoredDeviceID = storedDevice;
	}
	
	
	
	
}
