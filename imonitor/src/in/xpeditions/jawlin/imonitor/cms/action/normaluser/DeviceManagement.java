/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;


import in.xpeditions.jawlin.imonitor.controller.dao.entity.AVBlaster;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AVCategory;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Action;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertsFromImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceTypeModel;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DoorLockConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.H264CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyObjects;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LCDRemoteConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MinimoteConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ModbusSlave;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MotorConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.NetworkStats;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.PIRConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.RssiInfo;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SirenConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;

import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
//vibhu start
import in.xpeditions.jawlin.imonitor.util.LogUtil;
//vibhu end

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.XStreamException;
//import com.thoughtworks.xstream.core.AbstractReferenceMarshaller.ReferencedImplicitElementException;

/**
 * @author Coladi
 *
 */
public class DeviceManagement extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5006560264147576121L;
	private Device device;
	private DeviceType deviceType;
	private List<Device> devices;
	private Device acdevice;
	private List<Location> locations;
	private List<String> responses = new ArrayList<String>();
	private String alert[];
	private GateWay gateWay;
	private List<Make> makes;
	private List<AlertsFromImvg> AlertsFromImvg;
	private List<Icon> listIcons;
	MotorConfiguration motorConfiguration;
	SirenConfiguration sirenConfiguration;
	private List<String> SelectedAlerts;
	private String alertDeviceId;				//sumit start: Alert Device Feature
	private String oldLocationId;				//For Updating Order of device when location is changed.
	private String pollingInterval = "0";		//For ZXT120
	private PIRConfiguration pirConfiguration;	//For ZXT120
	private List<AlertType> alertype;
	private boolean isLowPower = false;			//parishod: Low power Discovery for Door Locks.
	private String status;
	//naveen start for ac auto search
	private List<String> acAutoMake;
	

	private String param;
	private String  NotSelected;
	//sumit start: Camera Orientation Configuration
	private CameraConfiguration cameraConfiguration;
	private H264CameraConfiguration h264CameraConfiguration;
	private boolean horrizontalFlip;
	private boolean verticalFlip;
	private boolean PTOption = false;
	//sumit end
	//sumit start: AV Blaster Feature
	private List<AVCategory> avCategoryList;
	private List<AVBlaster> avBlasterList;
	private List<String> avBrandList;
	private List<String> avCodeList;
	private long categoryId;
	private String brandName;
	private String avCategorySelect;
	private String avBrandSelect;
	private String avCodeSelect;
	//sumit end: AV Blaster Feature
	//bhavya start: Camera Orientation Configuration
	private List<Scenario> scenarios;
	private LCDRemoteConfiguration lCDRemoteConfiguration;
	private Schedule schedule;
	private String  selected;
	private  String timeout;
	//bhavya end
	private DoorLockConfiguration doorLockConfiguration;
	private String acSelectedBrand;
	private List<String> acCodeList;
	private String acSetCode;
	private String acBrandSelected;
	private String avCodeSelected;
	private List<String> acManualSelect;
	private String avSetCode;
	private Make configuredMake;
	private String learnValue;
	private String selectedValue;
	private  boolean hmdDevice;
	private MinimoteConfig minimoteConfig;
	
//Scene Controller
	
	//Diaken VIA
	private String checkedDevice;
	
	private Set<GateWay> gateWays;
	private List<Action> actions;
	private List<KeyObjects> keyObjects;
	private List<KeyConfiguration> keyConfig;
	private List<NetworkStats> networkStats;
	private String icon;
	

	//Diaken Via
	private String SlaveId;
	private String SlaveModel;
	private ModbusSlave modbusSlave;


	//NetworkStats
	private Set<RssiInfo> rssiInfos;
	
	//RGB
	private String hexValue;
	
	//AV Blaster Changes
	private String avChannelCode ;
	
	//Virtual Switch
	private KeyConfiguration key1;
	private KeyConfiguration key12;
	
	//Touch Panel
		private Device device1;
		private Device device2;
		private Device device3;
		private Device device4;
		private Device device5;
public String getHexValue() {
		return hexValue;
	}

	public void setHexValue(String hexValue) {
		this.hexValue = hexValue;
	}
	public Device getDevice1() {
		return device1;
	}

	public void setDevice1(Device device1) {
		this.device1 = device1;
	}

	public Device getDevice2() {
		return device2;
	}

	public void setDevice2(Device device2) {
		this.device2 = device2;
	}

	public Device getDevice3() {
		return device3;
	}

	public void setDevice3(Device device3) {
		this.device3 = device3;
	}

	public Device getDevice4() {
		return device4;
	}

	public void setDevice4(Device device4) {
		this.device4 = device4;
	}

	public Device getDevice5() {
		return device5;
	}

	public void setDevice5(Device device5) {
		this.device5 = device5;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
//		GateWay gateWay = user.getCustomer().getGateWay();
		XStream stream = new XStream();
		String xmlString = stream.toXML(gateWay);
		String xmlOfDeviceList = IMonitorUtil.sendToController("deviceService", "listDevicesOfGateWays", xmlString);
		devices = (List<Device>) stream.fromXML(xmlOfDeviceList);
//		Filling locations. fill it only if the logged in user is super user.
		String xmlOfLocationList = IMonitorUtil.sendToController("locationService", "listLocations");
		this.locations = (List<Location>) stream.fromXML(xmlOfLocationList);
		return SUCCESS;
	}
	
	// *********************************** sumit start: HANDLE deleting device that is a part of some rule. ***********************	
	
	@SuppressWarnings("unchecked")
	public String checkDeviceForRule() throws Exception {
		XStream stream = new XStream();
		String message = "msg.imonitor.devicedelete.0000";
		String genDeviceId = stream.toXML(device.getGeneratedDeviceId());
		//String xmlMac = stream.toXML(device.getGateWay().getMacId());
		
		//1a.get device id for the generatedDeviceId.
		String Id =  IMonitorUtil.sendToController("deviceService", "getIdByGeneratedDeviceId", genDeviceId);
		//1b.get rules associated to the device.
		long count =0;
		String countResult = IMonitorUtil.sendToController("ruleService", "getRuleCountForDevice", Id, genDeviceId);
		String countResult1 = IMonitorUtil.sendToController("scheduleService", "getScheduleCountForDevice",genDeviceId);
		String countResult2 = IMonitorUtil.sendToController("scenarioService", "getScenarioCountForDevice",genDeviceId);
		
		//2.if rules exists for the device prompt the user about it and proceed further
		if(countResult.equalsIgnoreCase("RULE_EXISTS")){
			count += 4;
		}
		if(countResult1.equalsIgnoreCase("SCHEDULE_EXISTS")){
			count += 2;
		}if(countResult2.equalsIgnoreCase("SCENARIO_EXISTS")){
			count += 1;
		}
		if(count > 0){
			message = "msg.imonitor.devicedelete.000"+count;
			message = IMonitorUtil.formatFailureMessage(this, message);
		}
		else
		{
		message = IMonitorUtil.formatMessage(this, message);
		}
		
		ActionContext.getContext().getSession().put("message", message);
		//ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	
	public String removeDevice() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(device.getGeneratedDeviceId());
		IMonitorUtil.sendToController("commandIssueService", "deleteDevice", xmlString);
		return SUCCESS;
	}
	//bhavya start

	@SuppressWarnings("unchecked")
	public String ScanNow() throws Exception {
		XStream stream = new XStream();
		
		String xmlString = stream.toXML(this.device);
		
		String xmlString1 = stream.toXML(this.device.getGeneratedDeviceId());
		
		String xmlMessage=IMonitorUtil.sendToController("commandIssueService", "ScanNow", xmlString);
		String result = (String) stream.fromXML(xmlMessage);
		String message = "";
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){//"db")){
			message = IMonitorUtil.formatMessage(this,"msg.imonitor.scanforhealth.0001");
		}else if(result.startsWith(Constants.iMVG_FAILURE)){//"iMVG")){
			result = result.substring(Constants.iMVG_FAILURE.length()+1);
			message = "msg.imonitor.scandevice.0002"+Constants.MESSAGE_DELIMITER_1 + result;
			message = IMonitorUtil.formatFailureMessage(this, message);
			//LogUtil.info("msg.imonitor.scandevice.0002 and reason is:"+result);
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){//"no")){
			message = IMonitorUtil.formatFailureMessage(this, "msg.imonitor.scandevice.0001");
		}

		ActionContext.getContext().getSession().put("message", message);
		
		if(message.startsWith(Constants.MSG_FAILURE)){
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String CurrentStatus() throws Exception {
		XStream stream = new XStream();
		
		String xmlString = stream.toXML(this.device);
		
		String xmlMessage=IMonitorUtil.sendToController("commandIssueService", "CurrentStatus", xmlString);
		String result = (String) stream.fromXML(xmlMessage);
		String message = "";
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){//"db")){
			message = "msg.imonitor.scanforhealth.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){//"iMVG")){
			message = "msg.imonitor.scandevice.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){//"no")){
			message = "msg.imonitor.scandevice.0001";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		
		if(message.startsWith(Constants.MSG_FAILURE)){
			return ERROR;
		}
		return SUCCESS;
	}
	public String removeDeviceup() throws Exception {
		XStream stream = new XStream();
	    String valueInXml = stream.toXML(this.gateWay.getMacId());
		String result=IMonitorUtil.sendToController("commandIssueService", "deleteDeviceup", valueInXml);
		if (result.equals("offline"))
		{
			
			String message="Gateway is in Discover or Removal Mode";
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		else
		{
		
		return SUCCESS;
		}
	}
	//bhavya end
	//vibhu start
	public String removeAllDevices() throws Exception {
		XStream stream = new XStream();
		String valueInXml = stream.toXML(this.gateWay.getMacId());
		IMonitorUtil.sendToController("commandIssueService", "deleteAllDevices", valueInXml);
		return SUCCESS;
	}

	public String nupdateDevice() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(device);
		String message = IMonitorUtil.sendToController("commandIssueService", "nupdateDevice", xmlString);
	/*	String result = (String) stream.fromXML(message);
		result = IMonitorUtil.formatMessage(this, result);
		ActionContext.getContext().getSession().put("message", message);*/
		return SUCCESS;
	}
	//vibhu end
	
	/**
	 * Called to change/modify device details on Setup/Devices page. 
	 * <p>
	 * Gets the device details based on generatedDeviceId, list of locations based on customer, list of icons based on devicetype
	 * 
	 * @return		SUCCESS.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String toUpdateFriendlyNameAndLocation() throws Exception{
		String generatedDeviceId = device.getGeneratedDeviceId();
		XStream stream = new XStream();
		String xmlString = stream.toXML(generatedDeviceId);
		String xmlDevice = IMonitorUtil.sendToController("deviceService", "toUpdateFriendlyNameAndLocation", xmlString);
		this.device = (Device) stream.fromXML(xmlDevice);
		//sumit start: Required To Update the Postion Index if user changes Location for device. 	
		this.oldLocationId = Long.toString(this.device.getLocation().getId());
		//sumit end
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String customerXmlString = stream.toXML(customer);
		String xmlOfLocationList = IMonitorUtil.sendToController("locationService", "listLocations", customerXmlString);
		this.locations = (List<Location>) stream.fromXML(xmlOfLocationList);
//		new feature: Icon Selection for Device. Gets list of Icons based on DeviceType
		long deviceType = device.getDeviceType().getId();		
		String xmlDeviceType = stream.toXML(deviceType);	
		String xmlOfIconList = null;
		
		this.timeout = device.getEnableStatus();
	/*if(device.getEnableStatus().equalsIgnoreCase("1"))
		{
		timeout=true;
		}
		else
		{
	    timeout=false;
		}*/
	
	if((device.getModelNumber()!= null) && (device.getModelNumber().equalsIgnoreCase("HMD")))
	   {
		hmdDevice=true;
	   }
	else
	   {
		hmdDevice=false;
		
	   }
		try {
			xmlOfIconList = IMonitorUtil.sendToController("iconService", "listIconsByDeviceType", xmlDeviceType);
			this.listIcons = (List<Icon>) stream.fromXML(xmlOfIconList);												
			} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * Updates Device related information as updated/modified/changed by the user as per his convenience.
	 * 
	 * @return  SUCCESS if operation successful, ERROR otherwise.
	 * @throws Exception
	 */
	public String updateFriendlyNameAndLocation() throws Exception {
		XStream stream = new XStream();
		device.setEnableStatus(this.timeout);
	   
		//Naveen made changes to enable/disable hmd device as per user knowledge
		
		if(this.isHmdDevice())
		{
		    device.setModelNumber("HMD");			
		}
	
		
	     
		String xmlString = stream.toXML(this.device);
		
		//sumit start: Re-Ordering Of Device
		//String xmlDevice = "";
		String oldLocId = this.oldLocationId;
		String oldLocationIdXml = stream.toXML(oldLocId);
		//xmlDevice = IMonitorUtil.sendToController("deviceService", "updateFriendlyNameAndLocation", xmlString, oldLocationIdXml);
		String xmlMessage = IMonitorUtil.sendToController("deviceService", "updateFriendlyNameAndLocation", xmlString, oldLocationIdXml);
		String message = "";
		String result = (String) stream.fromXML(xmlMessage);
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){
			message = "msg.imonitor.deviceupdatesuccess.0001";
		}
		else if(result.equalsIgnoreCase(Constants.DB_FAILURE)){
			message = "msg.imonitor.deviceupdate.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){
			message = "msg.imonitor.deviceupdate.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
			message = "msg.imonitor.deviceupdateCurtainModel.0003";
		}
		
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		//ActionContext.getContext().getSession().put("message", message);
		if(message.startsWith(Constants.MSG_FAILURE)){
			return ERROR;
		}
		//sumit end: Re-Ordering Of Device
		
//			Sets the selected icon for the Device
//			String xmlIcon = IMonitorUtil.sendToController("iconService", "updateIcon", xmlString);
//			this.device = (Device) stream.fromXML(xmlIcon);
//			this.device = (Device) stream.fromXML(xmlDevice);
		return SUCCESS;
	}
	
	// ************************************************************* sumit begin **************************************************************
	
	// Camera Orientation Configuration START.
	/**
	 * Called to configure camera orientation from Setup/Devices page.
	 * <p>
	 * Gets the device details for which orientation needs to be configured. Capable of configuring cameras of RC80 Series as well as H264 Series.
	 * 
	 * @author sumit
	 * @return  SUCCESS
	 * @throws Exception
	 */
	public String toConfigureCameraOrientation() throws Exception {
		//1.Retrieve the previous configuration if set earlier. We have device.id, generatedDeviceId and macId for the device.
		long id = device.getId();
		String generatedDeviceId = device.getGeneratedDeviceId();
		String macId = device.getGateWay().getMacId();
		String cameraOrientation = "";
		
		XStream stream = new XStream();
		String xmlId = stream.toXML(id);
		String xmlGenDeviceId = stream.toXML(generatedDeviceId);
		String xmlMacId = stream.toXML(macId);
		String Pantilt = "";
		String serviceName = "deviceService";
		String method = "toConfigureCameraOrientation";
		String xmlDevice = IMonitorUtil.sendToController(serviceName, method, xmlId, xmlGenDeviceId, xmlMacId);

		this.device = (Device) stream.fromXML(xmlDevice);
		String modelNumberOfCamera = this.device.getModelNumber();
		
		//2.Pre-select the configured Orientation based on the model number of the camera.
			if(modelNumberOfCamera.contains(Constants.RC80Series)){
			try {
				this.cameraConfiguration = (CameraConfiguration) this.device.getDeviceConfiguration();
			} catch (Exception e1) {
				LogUtil.error("1.Got Exception: "+e1.getMessage());
			}
			cameraOrientation = this.cameraConfiguration.getCameraOrientation();
			Pantilt = this.cameraConfiguration.getPantiltControl();
			if(Pantilt != null && Pantilt.equalsIgnoreCase("1")){
				this.PTOption = true;	
			}else{
				this.PTOption = false;
			}

			//Use binary values (00,01,10,11) IP_CAMERA
			String firstBit = cameraOrientation.substring(0, 1);
			String secondBit = cameraOrientation.substring(1, 2);

			if((firstBit.equals("0")) && secondBit.equals("0")){		//set to default
				this.verticalFlip = false;
				this.horrizontalFlip = false;
			}else if((firstBit.equals("0")) && secondBit.equals("1")){	//set horizontal flip
				this.verticalFlip = false;
				this.horrizontalFlip = true;
			}else if((firstBit.equals("1")) && secondBit.equals("0")){	//set vertical flip
				this.verticalFlip = true;
				this.horrizontalFlip =false;
			}else if((firstBit.equals("1")) && secondBit.equals("1")){	//set mirror flip
				this.verticalFlip = true;
				this.horrizontalFlip = true;
			}			
		}else if(modelNumberOfCamera.contains(Constants.H264Series)){
			try {
				this.h264CameraConfiguration = (H264CameraConfiguration) this.device.getDeviceConfiguration();
			} catch (Exception e2) {
				LogUtil.error("2.Got Exception: "+e2.getMessage());
			}
			cameraOrientation = this.h264CameraConfiguration.getCameraOrientation();

			
			Pantilt = this.h264CameraConfiguration.getPantiltControl(); //Kantharaj Changed for fix Bug-921
			
			if(Pantilt != null && Pantilt.equalsIgnoreCase("1")){
				this.PTOption = true;	
			}else{
				this.PTOption = false;
			}
			//Use numeric values (1,2,3,4) for H264_Cameras
			if(cameraOrientation.equals("1")){			//set horizontal flip
				this.verticalFlip = false;
				this.horrizontalFlip = true;
			}else if (cameraOrientation.equals("2")) {	//set vertical flip
				this.verticalFlip = true;
				this.horrizontalFlip = false;
			}else if (cameraOrientation.equals("3")) {	//set mirror flip
				this.verticalFlip = true;
				this.horrizontalFlip = true;
			}else if (cameraOrientation.equals("4")) {	//set to default
				this.verticalFlip = false;
				this.horrizontalFlip = false;
			}
		}
		return SUCCESS;
	}
	
	/**
	 * Saves the changes done by the user to configure camera orientation. 
	 * Saves details for camera orientation for cameras of RC80 Series and H264 Series.
	 * 
	 * @author sumit
	 * @return  java.lang.String. SUCCESS if operation successful, ERROR otherwise.
	 * @throws Exception
	 */
	public String saveCameraOrientationConfiguration() throws Exception{
		XStream stream = new XStream();
		boolean isVerticalFlipChecked = this.verticalFlip;
		boolean isHorizontalFlipChecked = this.horrizontalFlip;
		boolean isPTchecked = this.PTOption;
		String PTXml = stream.toXML("null");
		String modelNumber = this.device.getModelNumber();
		String result = "";
		String message = "";
		String cameraConfiguration = "";
		String resultXml = "";
		String configurationXml = "";
		String deviceXml = stream.toXML(this.device);
		String serviceName = "deviceService";
		String method = "saveCameraOrientationConfiguration";
		
		//1.pass the cameraOrientationConfiguration to DeviceServiceManager based on CAMERA MODEL NUMBER.
		if(modelNumber.contains(Constants.RC80Series)){
			if(!isHorizontalFlipChecked && !isVerticalFlipChecked){	cameraConfiguration = "00";			//set to default
			}else if(!isHorizontalFlipChecked && isVerticalFlipChecked){ cameraConfiguration = "10";	//set to vertical flip
			}else if(isHorizontalFlipChecked && !isVerticalFlipChecked){ cameraConfiguration = "01";	//set to horizontal flip
			}else if(isHorizontalFlipChecked && isVerticalFlipChecked){	cameraConfiguration = "11";		//set to mirror & flip
			}
			
			if(isPTchecked){
				PTXml = stream.toXML("1");				  
			}
			configurationXml = stream.toXML(cameraConfiguration);
			resultXml = IMonitorUtil.sendToController(serviceName, method, deviceXml, configurationXml, PTXml);
			result = (String) stream.fromXML(resultXml);
		}else if(modelNumber.contains(Constants.H264Series)){
			if(!isHorizontalFlipChecked && !isVerticalFlipChecked){	cameraConfiguration = "4";			//set to default
			}else if(isHorizontalFlipChecked && !isVerticalFlipChecked){ cameraConfiguration = "1";		//set to horizontal flip
			}else if(!isHorizontalFlipChecked && isVerticalFlipChecked){ cameraConfiguration = "2";		//set to vertical flip
			}else if(isHorizontalFlipChecked && isVerticalFlipChecked){	cameraConfiguration = "3";		//set to mirror & flip
			}
			if(isPTchecked){
				PTXml = stream.toXML("1");				  
			}
			configurationXml = stream.toXML(cameraConfiguration);
			resultXml = IMonitorUtil.sendToController(serviceName, method, deviceXml, configurationXml, PTXml);//, modelNumberXml);
			result = (String) stream.fromXML(resultXml);
		}
		if(result.equalsIgnoreCase(Constants.DB_FAILURE)){
			message = "msg.imonitor.devicecameraorientation.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){
			message = "msg.imonitor.devicecameraorientation.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
			message = "msg.imonitor.devicecameraorientation.0003";
			//return ERROR;
		}else if (result.equalsIgnoreCase(Constants.DB_SUCCESS)) {
			message = "msg.imonitor.devicecameraorientation.0004";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		//ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}
		return SUCCESS;
	}
	// Camera Orientation Configuration END.
	

	// ************************************************************* bhavya begin **************************************************************
	
		// LCD Remote Configuration START.
		/**
		 * Called to configure camera orientation from Setup/Devices page.
		 * <p>
		 * Gets the device details for which orientation needs to be configured. Capable of configuring cameras of RC80 Series as well as H264 Series.
		 * 
		 * @author 
		 * @return  SUCCESS
		 * @throws Exception
		 */
	@SuppressWarnings("unchecked")
	public String toLCDremoteConfiguration() throws Exception {
		try {
			String serviceName = "scenarioService";
			String method = "listAllScnarios";
			User user = (User) ActionContext.getContext().getSession().get(
					Constants.USER);
			long customerId = user.getCustomer().getId();
			XStream stream = new XStream();
			String customerIdXml = stream.toXML(customerId);
			String result = IMonitorUtil.sendToController(serviceName, method,customerIdXml);
			List<Scenario> scenarios = (List<Scenario>) stream.fromXML(result);
			this.setSenarios(scenarios);
			
			String resultXml = IMonitorUtil.sendToController("deviceService", "getLCDRemoteconfiguration", stream.toXML(device.getGeneratedDeviceId()));
			lCDRemoteConfiguration = (LCDRemoteConfiguration)stream.fromXML(resultXml);
		} catch (Exception e) {
			LogUtil.info("EX", e);
		}
		
		return SUCCESS;
	}
	

		/**
		 * Saves the changes done by the user to configure camera orientation. 
		 * Saves details for camera orientation for cameras of RC80 Series and H264 Series.
		 * 
		 * @author sumit
		 * @return  java.lang.String. SUCCESS if operation successful, ERROR otherwise.
		 * @throws Exception
		 */
		
		
		public String saveLCDremoteConfiguration() throws Exception {
			XStream stream = new XStream();
			device.setDeviceConfiguration(lCDRemoteConfiguration);
			String xmlString = stream.toXML(device);

			//String xmlString = stream.toXML(this.device);
			String result = "";
			String message = "";
			String resultXml = IMonitorUtil.sendToController("deviceService", "saveLCDremoteConfiguration", xmlString);
			result = (String) stream.fromXML(resultXml);

			if(result.equalsIgnoreCase(Constants.DB_SUCCESS))
			{
				message = "msg.imonitor.LCDconfiguration.success.0001";
			}
			else
			{
				if(result.equalsIgnoreCase(Constants.DB_FAILURE))
				{
					message = "msg.imonitor.LCDconfiguration.0001";
				}
				else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE))
				{
					message = "msg.imonitor.LCDconfiguration.0002";
				}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY))
				{
					message = "msg.imonitor.LCDconfiguration.0003";
				}
				else
				{
					message = "msg.imonitor.LCDconfiguration.0004";
				}
				
			}
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			
			if(message.startsWith(Constants.MSG_FAILURE))
			return ERROR;
			else
			return SUCCESS;
		}
		
		
		
		
		// bhavya end:LCD remote Configuration END.
		


	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @author sumit
	 * @return  SUCCESS
	 * @throws Exception
	 */
	public String toEditMode() throws Exception {
		String serviceName = "customerService";
		String method = "listAllDevicesForAllGateWaysOfCustomerByCustomerId";
		User user = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		String customerId = user.getCustomer().getCustomerId();
		XStream stream = new XStream();
		String customerIdXml = stream.toXML(customerId);
		String result = IMonitorUtil.sendToController(serviceName, method,
				customerIdXml);
		List<Device> devicesListOfCustomer = (List<Device>) stream.fromXML(result);
		this.setDevices(devicesListOfCustomer);
		for(Device d: this.devices)
		{
			d.getFriendlyName();
			d.getGeneratedDeviceId();
			d.getDeviceId();
			d.setCurrentMode(d.getMode());
		}
		//Get the gateway to retrieve the alertDevice.
		Device oneDevice = devicesListOfCustomer.get(0);
		Long gatewayId = oneDevice.getGateWay().getId();
		String gatewayIdXml = stream.toXML(gatewayId);
		String gatewayXml = IMonitorUtil.sendToController("gateWayService", "getGateWayById", gatewayIdXml);
		GateWay gateWayFromDB = (GateWay) stream.fromXML(gatewayXml);
		String alertDevcString = "";
		try {
			Long alertDevcId = gateWayFromDB.getAlertDevice().getId();
			if(alertDevcId != null){
				alertDevcString = Long.toString(alertDevcId.longValue());
				this.alertDeviceId = alertDevcString;
			}else{
				this.alertDeviceId = "";
			}
		} catch (Exception e) {
			LogUtil.info("Got Exception while getting alertDevice: ", e);
		}
		return SUCCESS;
	}

	/**
	 * Saves Mode configuration for all devices of customer except, System/Virtual Devices namely, HOME, STAY and AWAY Devices.
	 * 
	 * @author sumit
	 * @return  SUCCESS
	 * @throws Exception
	 */
	public String saveMode() throws Exception {
		//Pass this alertDeviceId to the controller.
		String message = "";
		String alertDevice = this.alertDeviceId;
		Long alertDevcId = null;
		if(alertDevice.equalsIgnoreCase("")){						//no device selected by the user.
			alertDevcId = new Long(0);
		}else{														//Alert Device set by user.
			alertDevcId = new Long(Long.parseLong(alertDevice));
		}
		XStream stream = new XStream();
		String alertDeviceXml = stream.toXML(alertDevcId);
		String deviceListForModeXml = stream.toXML(this.devices);
		String serviceName = "deviceService";
		String method = "";
		if(alertDevcId.longValue() == 0){
			method = "saveModeWithAllDetailsWithNoAlertDevice";
			message = IMonitorUtil.sendToController(serviceName, method, deviceListForModeXml, alertDeviceXml);
		}else{
			//get the device details for the selected Alert Device
			String method1 = "getDeviceById";
			String deviceXml = IMonitorUtil.sendToController(serviceName, method1, alertDeviceXml);
			Device device = (Device) stream.fromXML(deviceXml);
			String generatedDeviceId = device.getGeneratedDeviceId();
			String genDeviceIdXml = stream.toXML(generatedDeviceId);
			
			//pass the generated device id of the selected alert device to controller
			method = "saveModeWithAllDetails";
			message = IMonitorUtil.sendToController(serviceName, method, deviceListForModeXml, alertDeviceXml, genDeviceIdXml);
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		//ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;

	}
	
/*	public String saveMode() throws Exception {
		
		XStream stream = new XStream();
		String deviceListForModeXml = stream.toXML(this.devices);
		String serviceName = "deviceService";
		String method = "saveModeWithAllDetails";
		String message = IMonitorUtil.sendToController(serviceName, method, deviceListForModeXml);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;

	}
	// ***************************************************** sumit end: Alert Device Feature ****************************************************
	*/
	
	/**
	 * Called from Setup/Devices page to configure no motion timeout for PIR_SENSOR and MULTI_SENSOR.
	 * 
	 * @author sumit
	 * @return  SUCCESS
	 * @throws Exception
	 */
	public String toConfigureNoMotionPeriod() throws Exception {
		long id = device.getId();
		String generatedDeviceId = device.getGeneratedDeviceId();
		String macId = device.getGateWay().getMacId();
		//LogUtil.info("devcId: "+generatedDeviceId+", macId: "+macId);
		XStream xStream = new XStream();
		String xmlId = xStream.toXML(id);
		String xmlString = xStream.toXML(generatedDeviceId);
		String xmlMacId = xStream.toXML(macId);
		String serviceName = "deviceService";
		String method = "toConfigureNoMotionPeriod";
		String xmlDevice = IMonitorUtil.sendToController(serviceName, method, xmlId, xmlString, xmlMacId); 
		this.device = (Device) xStream.fromXML(xmlDevice);
		return SUCCESS;
	}
	
	/**
	 * Saves no motion timeout value for PIR_SENSOR and MULTI_SENSOR as configured by the user.
	 * 
	 * @author sumit
	 * @return  SUCCESS if operation successful, ERROR otherwise.
	 * @throws Exception
	 */
	public String updateNoMotionTimeOut() throws Exception {
		XStream stream = new XStream();
		//LogUtil.info("macId: "+this.device.getGateWay().getMacId());
		String xmlDevice = stream.toXML(this.device);//this device has only id, generatedDeviceId, friendlyName and modelNumber(as a substitute for time)
		String serviceName = "deviceService";
		String method = "updateNoMotionTimeOut";
		String xmlMessage = IMonitorUtil.sendToController(serviceName, method, xmlDevice);
		String result = (String) stream.fromXML(xmlMessage);
		String message = "";
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){//"db")){
			message = "msg.imonitor.deviceNoMotiondetectionsuccess.0001";
		}
		else if(result.equalsIgnoreCase(Constants.DB_FAILURE)){//"db")){
			message = "msg.imonitor.deviceNoMotiondetection.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){//"iMVG")){
			message = "msg.imonitor.deviceNoMotiondetection.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){//"no")){
			message = "msg.imonitor.deviceNoMotiondetection.0003";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		//ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}
		return SUCCESS;
	}
	//***************************************************************** sumit end *************************************************************

	//*********************************************** sumit start: Rocker and Tact Switch Type ***********************************************
	/**
	 * Gets the device for which Switch Type needs to be updated.
	 * 
	 * @author sumitkumar
	 * @return
	 * @throws Exception
	 */
	public String toConfigureSwitchType() throws Exception {
		XStream stream = new XStream();
		String xmlDevice = stream.toXML(this.device);
	
		
	
		try {
			String serviceName = "deviceService";
			String method = "toConfigureSwitchType";
			String xmlResult = IMonitorUtil.sendToController(serviceName, method, xmlDevice); 
			this.device = (Device) stream.fromXML(xmlResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return SUCCESS;
	}
	
	public String toConfigureRGB() throws Exception {
		LogUtil.info("toConfigureRGB");
		XStream stream = new XStream();
		String xmlDevice = stream.toXML(this.device);
	try {
			String serviceName = "deviceService";
			String method = "toConfigureRGB";
			String xmlResult = IMonitorUtil.sendToController(serviceName, method, xmlDevice); 
			this.device = (Device) stream.fromXML(xmlResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return SUCCESS;
	}
	
	/**
	 * Updates the Switch Type of the device [Switches/Dimmers] to user selected type.
	 * 
	 * @author sumitkumar
	 * @return java.lang.String
	 * @throws Exception
	 */
	public String updateSwitchType() throws Exception {
		XStream stream = new XStream();
		//LogUtil.info("macId: "+this.device.getGateWay().getMacId());
		String xmlDevice = stream.toXML(this.device);
		String serviceName = "deviceService";
		String method = "updateSwitchType";
		
		String xmlMessage = IMonitorUtil.sendToController(serviceName, method, xmlDevice);
		String result = (String) stream.fromXML(xmlMessage);
		String message = "";
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS))
		{
			message = "msg.imonitor.deviceSwitchTypeSuccess.0001";
		}
		if(result.equalsIgnoreCase(Constants.DB_FAILURE)){//"db")){
			message = "msg.imonitor.deviceSwitchType.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){//"iMVG")){
			message = "msg.imonitor.deviceSwitchType.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){//"no")){
			message = "msg.imonitor.deviceSwitchType.0003";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		//ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}
		return SUCCESS;
	}
	//*********************************************** sumit end: Rocker and Tact Switch Type ***********************************************
	
	public String UpdateRgb() throws Exception {
		LogUtil.info("UpdateRgb");
		XStream stream = new XStream();
		//LogUtil.info("macId: "+this.device.getGateWay().getMacId());
		String xmlDevice = stream.toXML(this.device);
		String serviceName = "deviceService";
		String method = "UpdateRgb";
		
		String xmlMessage = IMonitorUtil.sendToController(serviceName, method, xmlDevice);
		String result = (String) stream.fromXML(xmlMessage);
		LogUtil.info("result="+result);
		String message = "";
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS))
		{
			message = "msg.imonitor.deviceSwitchTypeSuccess.0001";
		}
		if(result.equalsIgnoreCase(Constants.DB_FAILURE)){//"db")){
			message = "msg.imonitor.deviceSwitchType.0004";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){//"iMVG")){
			message = "msg.imonitor.deviceSwitchType.0005";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){//"no")){
			message = "msg.imonitor.deviceSwitchType.0003";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		
		if(message.contains("Failure")){
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	
	
@SuppressWarnings("unchecked")
	/**
	 * Get list of makes and polling interval for particular device. 
	 * Polling interval is required for ZXT120 Ac Extender, ZXT110 may not require polling interval 
	 * 
	 * @return SUCCESS
	 * @throws Exception
	 */
	public String toUpdateModelNumber() throws Exception{
		String generatedDeviceId = device.getGeneratedDeviceId();
		XStream stream = new XStream();
		
		String xmlString = stream.toXML(generatedDeviceId);
	//	String xmlOfMakeList = IMonitorUtil.sendToController("makeService", "listMakesByDeviceType", xmlString);
	//	this.makes = (List<Make>) stream.fromXML(xmlOfMakeList);
		String xmlofMakeBrandList = IMonitorUtil.sendToController("makeService", "listAutoMakeForAc");
		this.acManualSelect = (List<String>) stream.fromXML(xmlofMakeBrandList);
		//1.sumit TBD: Also code for preselecting the previously selected model
		String deviceXml = IMonitorUtil.sendToController("deviceService", "getDeviceWithMake", xmlString);
		Device deviceFromDB = (Device) stream.fromXML(deviceXml);
		Make make = deviceFromDB.getMake();
			
		if(make != null){
			device.setMake(make);
			String Id = String.valueOf(deviceFromDB.getMake().getId());
			String makeId = stream.toXML(Id);
			String selectedmake = IMonitorUtil.sendToController("deviceService","getMakeWithId", makeId);
			this.configuredMake = (Make) stream.fromXML(selectedmake);
			String device = stream.toXML(this.device);
			String acConfig = IMonitorUtil.sendToController("deviceService","getdeviceConfig", device);
			this.learnValue = (String) stream.fromXML(acConfig);
		//	this.device = (Device) stream.fromXML(acConfig);
			
		}
		
		//2.Get polling interval based on deviceType and model number.
		String pollingIntervalXml = IMonitorUtil.sendToController("deviceService", "getPollingInterval", xmlString);
		//if pollingInterval is 0, it is not for ZXT120, hence hide the corresponding field on jsp
	//	this.pollingInterval = (String) stream.fromXML(pollingIntervalXml);
		this.pollingInterval = String.valueOf(10);
		return SUCCESS;
	}
	
	/* ************************************************** sumit start: ZXT120 - ORIGINAL CODE *************************************************
	 * public String updateModelNumber() throws Exception {
	 
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);	
		String xmlDevice = IMonitorUtil.sendToController("commandIssueService", "updateModelNumber", xmlString);
		this.device = (Device) stream.fromXML(xmlDevice);
		return SUCCESS;
	} */

	/**
	 * Saves the model number as well polling interval for ZXT120 Ac Extender.
	 * For ZXT110 Ac Extender, will save only the model number.
	 * 
	 * @author sumit
	 * @return  SUCCESS if operation is successful, ERROR otherwise.
	 * @throws Exception
	 */
	public String updateModelNumber() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String brand = stream.toXML(this.acBrandSelected);
		String selectedmodelNumber = stream.toXML(this.avCodeSelected);
		String result = "";
		String message = "";
		if(this.pollingInterval.equals("")){
			this.pollingInterval = "10";
		}
		String pollingIntervalXml = stream.toXML(this.pollingInterval);
			String selectedLearnValue = stream.toXML(this.selectedValue);
			
		try {
			String resultXml = IMonitorUtil.sendToController("deviceService", "updateModelNumber", xmlString, pollingIntervalXml, brand, selectedmodelNumber, selectedLearnValue);
			result = (String) stream.fromXML(resultXml);
			
			//String xmlDevice = IMonitorUtil.sendToController("deviceService", "updateModelNumber", xmlString, pollingIntervalXml);
			//this.device = (Device) stream.fromXML(xmlDevice);
		} catch (Exception e) {
			LogUtil.info("Got Exception", e);
		}
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){//"db")){
			message = "msg.imonitor.deviceupdateModeNosuccess.0001";
		}
		else if(result.equalsIgnoreCase(Constants.DB_FAILURE)){//"db")){
			message = "msg.imonitor.deviceupdateModeNo.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){//"iMVG")){
			message = "msg.imonitor.deviceupdateModeNo.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){//"no")){
			message = "msg.imonitor.deviceupdateModeNo.0003";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}
		
		return SUCCESS;
	}
	// *********************************************************** sumit end: ZXT120 **********************************************************

	/*
	@SuppressWarnings("unchecked")
	public String toupdateCurtainModel() throws Exception{
		String generatedDeviceId = device.getGeneratedDeviceId();
		XStream stream = new XStream();
		String xmlString = stream.toXML(generatedDeviceId);
		String xmlOfMakeList = IMonitorUtil.sendToController("makeService", "listMakesByDeviceType", xmlString);
		this.makes = (List<Make>) stream.fromXML(xmlOfMakeList);
		xmlString = stream.toXML(generatedDeviceId);
		String xmlConfig = IMonitorUtil.sendToController("deviceService", "getMotorConfigurationByDeviceId", xmlString);
		this.motorConfiguration=(MotorConfiguration)stream.fromXML(xmlConfig);
		return SUCCESS;
	}
*/
	
  	@SuppressWarnings("unchecked")
	/**
	 * Gets details of the device for which model number has to be updated.
	 * 
	 * @return SUCCESS
	 * @throws Exception
	 */
  	public String toupdateCurtainModel() throws Exception{
		String generatedDeviceId = device.getGeneratedDeviceId();
		XStream stream = new XStream();
		String xmlString = stream.toXML(generatedDeviceId);
		String xmlOfMakeList = IMonitorUtil.sendToController("makeService", "listMakesByDeviceType", xmlString);
		this.makes = (List<Make>) stream.fromXML(xmlOfMakeList);
		xmlString = stream.toXML(generatedDeviceId);
		String xmlConfig = IMonitorUtil.sendToController("deviceService", "getMotorConfigurationByDeviceId", xmlString);
		this.device=(Device) stream.fromXML(xmlConfig);
		this.motorConfiguration=(MotorConfiguration) this.device.getDeviceConfiguration();
		return SUCCESS;
	}
  	
  	//bhavya start to update health monitor
  	@SuppressWarnings("unchecked")
	public String toupdatehealthcheckModel() throws Exception{
		String generatedDeviceId = device.getGeneratedDeviceId();
		XStream stream = new XStream();
		String xmldevicetype=stream.toXML("Z_WAVE_HEALTH_MONITOR");
		String xmlString = stream.toXML(generatedDeviceId);
		String xmlOfMakeList = IMonitorUtil.sendToController("makeService", "listMakesByDeviceTypehealth", xmldevicetype);
		this.makes = (List<Make>) stream.fromXML(xmlOfMakeList);
		xmlString = stream.toXML(generatedDeviceId);
		String xmlConfig = IMonitorUtil.sendToController("deviceService", "getMotorConfigurationByDeviceId", xmlString);
		this.device=(Device) stream.fromXML(xmlConfig);
		String xmldevicetype1=stream.toXML("Z_WAVE_AC_EXTENDER");
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String xmlcustomer = stream.toXML(user.getCustomer().getId());
		String devicelistxml = IMonitorUtil.sendToController("deviceService", "listdevicefromdevicetype",xmldevicetype1,xmlcustomer);
		
		this.devices=(List<Device>)stream.fromXML(devicelistxml);
		//to retrive schedule details
				String serviceName = "scheduleService";
		String method = "retrieveScheduleDetailsfromgenerateddeviceid";
		
		String resultXml = IMonitorUtil.sendToController(serviceName, method,
				xmlString);
	
		this.schedule = (Schedule) stream.fromXML(resultXml);
		return SUCCESS;
	}
  	//bhavya end
  	/**
  	 * Saves the model number for Curtain Controller.
  	 * 
  	 * @return SUCCESS if operation successful, ERROR otherwise. 
  	 * @throws Exception
  	 */
	public String updateCurtainModel() throws Exception {
		
		XStream stream = new XStream();
		this.device.setDeviceConfiguration(this.motorConfiguration);
		String xmlString = stream.toXML(this.device);
		String message = "";
		String resultXml = IMonitorUtil.sendToController("commandIssueService", "updateCurtainModel", xmlString);
		String result = (String) stream.fromXML(resultXml);
		if(result.equalsIgnoreCase(Constants.DB_FAILURE)){
			message = "msg.imonitor.deviceupdateCurtainModel.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){
			message = "msg.imonitor.deviceupdateCurtainModel.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
			message = "msg.imonitor.deviceupdateCurtainModel.0003";
		}else if (result.equalsIgnoreCase(Constants.DB_SUCCESS)) {
			message = "msg.imonitor.deviceupdateCurtainModel.0004";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}		
		
		return SUCCESS;
	}
	//bhavya start
public String updatehealthcheckModel() throws Exception {
		
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String xmlacdeviceid=stream.toXML(this.acdevice);
		
		String selectedoptionforHMDshedule=stream.toXML(this.selected);
		String message = "";
		String schedulexml = stream.toXML(this.schedule);
      	
		String resultXml = IMonitorUtil.sendToController("commandIssueService", "updatehealthcheckmodel", xmlString,xmlacdeviceid,schedulexml,selectedoptionforHMDshedule);
		String result = (String) stream.fromXML(resultXml);
		
		if(result.equalsIgnoreCase(Constants.DB_FAILURE)){
			message = "msg.imonitor.deviceupdateHealthCheckModel.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){
			message = "msg.imonitor.deviceupdateHealthCheckModel.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
			message = "msg.imonitor.deviceupdateHealthCheckModel.0003";
		}else if (result.equalsIgnoreCase(Constants.DB_SUCCESS))
				{
			message = "msg.imonitor.deviceupdateHealthCheckModel.0004";
		}
		else if(result.equalsIgnoreCase(""))
		{
			message = "msg.imonitor.deviceupdateHealthCheckModel.0004";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}		
		
		return SUCCESS;
	}
	//bhavya end
 
	public String toAlertManagement() throws Exception {
		return SUCCESS;
	}
	public String alertManagement() throws Exception{
		responses.add("Sms");
		responses.add("Email");
		responses.add("Alert Imvg");
		return SUCCESS;
	}
	
	/**
	 * Sets the iMVG to discovery mode.
	 * 
	 * @return SUCCESS after setting the gateway to discovery mode.
	 * @throws Exception
	 */
	public String sendDeviceDiscoveryMode() throws Exception {
		XStream stream = new XStream();
		String valueInXml = stream.toXML(this.gateWay.getMacId());
		String boolInXml = stream.toXML(this.isLowPower);
		String deviceInXml = stream.toXML(this.checkedDevice); //Apoorva
		String serviceName = "gateWayService";
		String method = "setDeviceDiscoveryMode";
		IMonitorUtil.sendToController(serviceName, method, valueInXml, boolInXml,deviceInXml);

		
		serviceName = "gateWayService";
		method = "getGateWayByMacId";
//		valueInXml = stream.toXML(user.getCustomer().getGateWay().getMacId());
		String result = IMonitorUtil.sendToController(serviceName, method, valueInXml);
		if (result.equals("offline"))
		{
			
			String message="Gateway is in Discover or Removal Mode";
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		else
		{
		
		this.gateWay = (GateWay) stream.fromXML(result);
		return SUCCESS;
		}
	}
	
	public String sendAbortMode() throws Exception {
		XStream stream = new XStream();
		String valueInXml = stream.toXML(this.gateWay.getMacId());
		String statusInxml = stream.toXML(this.status);
		//String boolInXml = stream.toXML(this.isLowPower);
		String serviceName = "gateWayService";
		String method = "setAbortMode";
		IMonitorUtil.sendToController(serviceName, method, valueInXml,statusInxml);

		
		serviceName = "gateWayService";
		method = "getGateWayByMacId";
//		valueInXml = stream.toXML(user.getCustomer().getGateWay().getMacId());
		String result = IMonitorUtil.sendToController(serviceName, method, valueInXml);
		this.gateWay = (GateWay) stream.fromXML(result);
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	/**
	 * 
	 * @return SUCCESS
	 * @throws Exception
	 */
	public String toChooseAlert() throws Exception{
		String generatedDeviceId = device.getGeneratedDeviceId();
		
		XStream stream = new XStream();
		String xmlString = stream.toXML(generatedDeviceId);
		String xmlOfALertList = IMonitorUtil.sendToController("deviceService", "getAlertsOfDevice", xmlString);
		
		List<AlertType> Alerttype=(List<AlertType>) stream.fromXML(xmlOfALertList);
		this.alertype=Alerttype;
		Device Device=this.device;
		xmlString = stream.toXML(Device);
		String SelectedAlarmsPerDevice=IMonitorUtil.sendToController("deviceService", "getUserChoosenAlertsOfDevice", xmlString);
		
		if(SelectedAlarmsPerDevice.length()>7)
		{
		List<String> param=(List<String>)stream.fromXML(SelectedAlarmsPerDevice);	
		this.SelectedAlerts=param;		
		}
		else
		{
			this.SelectedAlerts=null;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveUserAlerts() throws Exception {	
		Device device=this.device;	
		XStream Stream=new XStream();
		String Xmlvalue=Stream.toXML(device);
		String message = "";
		
		String NotSelected=this.NotSelected;
		//LogUtil.info(NotSelected);
		String Param=this.param;
		Xmlvalue+="` "+Stream.toXML(Param);
		Xmlvalue+="` "+Stream.toXML(NotSelected);	
		String serviceName = "deviceService";
		String method = "saveUserChoosenAlerts";
		String resultXml = IMonitorUtil.sendToController(serviceName, method,Xmlvalue);
		
		String result = (String) Stream.fromXML(resultXml);
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){
			message = "msg.imonitor.deviceconfigalarmsuccess.0001";  
		}
			else if(result.equalsIgnoreCase(Constants.DB_FAILURE)){
			message = "msg.imonitor.deviceconfigalarm.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){
			message = "msg.imonitor.deviceconfigalarm.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
			message = "msg.imonitor.deviceupdateCurtainModel.0003";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updatePositionOfDevice() throws Exception 
	{
		Device device=this.device;
		XStream stream=new XStream();
		String xmlString = stream.toXML(device);
		IMonitorUtil.sendToController("deviceService", "updateDevicePosition", xmlString);
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String resetPositionOfDevice() throws Exception 
	{
		Device device=this.device;
		XStream stream=new XStream();
		String xmlString = stream.toXML(device);
		IMonitorUtil.sendToController("deviceService", "resetDevicePosition", xmlString);
		return SUCCESS;
	}
	
	
	
	//Changed Method for implementation of MultiChannel Associate for TouchPanel 
	/*public String ToNupdateDevice() throws Exception 
	{
		
		XStream stream = new XStream();
		String deviceIdXml = stream.toXML(device.getId());
		String deviceXml=IMonitorUtil.sendToController("deviceService", "getDeviceByGeneratedDeviceId", deviceIdXml);
		this.device=(Device) stream.fromXML(deviceXml);
		return SUCCESS;
	}*/
	
	public String ToNupdateDevice() throws Exception 
	{
		return SUCCESS;
	}
	// ************************************************* sumit start: AV Blaster Feature ********************************************************
	@SuppressWarnings("unchecked")
	/**
	 * @author sumit kumar
	 * @return SUCCESS after retrieving AV Category List.
	 * @throws Exception
	 */
	public String toAVBlasterModelNumber() throws Exception{
		XStream stream = new XStream();
		//1. Get AV Category List
		String avCategoriesXml = IMonitorUtil.sendToController("avBlasterService", "getListOfAVCategories");
		this.avCategoryList = (List<AVCategory>) stream.fromXML(avCategoriesXml);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * @author sumit kumar
	 * @return SUCCESS after retrieving List of AV Brands based on Selected Category Id
	 * @throws Exception
	 */
	public String getBrandListByCategory() throws Exception{
		XStream stream = new XStream();
		String categoryIdXml = stream.toXML(categoryId);
		//1. Get AV Brand List by selected category
		String avBrandXml = IMonitorUtil.sendToController("avBlasterService", "getBrandListByCategory", categoryIdXml);
		this.avBrandList = (List<String>) stream.fromXML(avBrandXml);
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	/**
	 * @author sumit kumar
	 * @return SUCCESS after retrieving List of AV Codes based on Selected Category Id and Brand Name.
	 * @throws Exception
	 */
	public String getCodeListByCategoryAndBrand() throws Exception{
		XStream stream = new XStream();
		String cIdXml = stream.toXML(categoryId);
		String bNameXml = stream.toXML(brandName);
		//1. Get AV Code List by selected category and brand
		String avCodeXml = IMonitorUtil.sendToController("avBlasterService", "getCodeListByCategoryAndBrand", cIdXml, bNameXml);
		this.avCodeList = (List<String>) stream.fromXML(avCodeXml);
		return SUCCESS;
	}
	
	/**
	 * @author sumit kumar
	 * @return SUCCESS if able to save configuration, ERROR otherwise.
	 * @throws Exception
	 */
	public String updateAVBlasterModelNumber() throws Exception{
		XStream stream = new XStream();
		String categorySelect = this.avCategorySelect;
		String brandSelect = this.avBrandSelect;
		String codeSelect = this.avCodeSelect;
		//LogUtil.info("this.avCategorySelect;"+ categorySelect + "brand: "+ brandSelect+"code: "+ codeSelect);
		
		//Learn changes done by apoorva start
		if (brandSelect.equals("-1") && codeSelect.equals("-1")) 
		{
			brandSelect= "Learn";
			codeSelect = "0";
			 
		}
		//Learn changes done by apoorva end
		String categoryXml = stream.toXML(categorySelect);
		String brandXml = stream.toXML(brandSelect);
		String codeXml = stream.toXML(codeSelect);

		//Using model Number to store AV Configuration. TBD: use AVConfiguration instead
		
		//if no model and no brand code selected (Learn Selected)
		
		String modelNumber = categorySelect +"-"+brandSelect+"-"+codeSelect;
		
		this.device.setModelNumber(modelNumber);
		String xmlString = stream.toXML(this.device);
				
		String result = "";
		String message = "";
		try {
			String resultXml = IMonitorUtil.sendToController("deviceService", "updateAVBlasterModelNumber", xmlString, categoryXml , brandXml, codeXml);
			result = (String) stream.fromXML(resultXml);
		} catch (Exception e) {
			LogUtil.info("Got Exception", e);
		}
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){//"db")){
			message = "AV Configuration saved successfully";//"msg.imonitor.deviceupdateModeNosuccess.0001";
		}
		else if(result.equalsIgnoreCase(Constants.DB_FAILURE)){//"db")){
			message = "AV Configuration could not be saved.";//"msg.imonitor.deviceupdateModeNo.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){//"iMVG")){
			message = "msg.imonitor.deviceupdateModeNo.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){//"no")){
			message = "msg.imonitor.deviceupdateModeNo.0003";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	// *************************************************** sumit end: AV Blaster Feature ********************************************************
	
	public String resetDevicePositionIndex() throws Exception 
	{
		Device device=this.device;
		XStream stream=new XStream();
		String xmlString = stream.toXML(device);
		IMonitorUtil.sendToController("deviceService", "resetDevicePositionIdex", xmlString);
		return SUCCESS;
	}
	
	public String show(){
		return SUCCESS;
	}
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public String[] getAlert() {
		return alert;
	}

	public void setAlert(String[] alert) {
		this.alert = alert;
	}

	public List<String> getResponses() {
		return responses;
	}

	public void setResponses(List<String> responses) {
		this.responses = responses;
	}

	public GateWay getGateWay() {
		return gateWay;
	}

	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}

	public final List<Make> getMakes() {
		return makes;
	}

	public final void setMakes(List<Make> makes) {
		this.makes = makes;
	}

	public MotorConfiguration getMotorConfiguration() {
		
		return motorConfiguration;
	}

	public void setMotorConfiguration(MotorConfiguration motorConfiguration) {
		this.motorConfiguration = motorConfiguration;
	}
   
	
	
	
	public List<Icon> getListIcons() {
		return listIcons;
	}
	
	public void setListIcons(List<Icon> listIcons) {
		this.listIcons = listIcons;
	}

	
	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public List<AlertType> getAlertype() {
		return alertype;
	}

	public void setAlertype(List<AlertType> alertype) {
		this.alertype = alertype;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public List<String> getSelectedAlerts() {
		return SelectedAlerts;
	}

	public void setSelectedAlerts(List<String> selectedAlerts) {
		SelectedAlerts = selectedAlerts;
	}

	public String getNotSelected() {
		return NotSelected;
	}

	public void setNotSelected(String notSelected) {
		NotSelected = notSelected;
	}
//sumit start: Alert Device Feature
	public String getAlertDeviceId() {
		return alertDeviceId;
	}

	public void setAlertDeviceId(String alertDeviceId) {
		this.alertDeviceId = alertDeviceId;
	}
//sumit end: Alert Device Feature
	
//sumit start: Camera Orientation Configuration
	public boolean getHorrizontalFlip() {
		return horrizontalFlip;
	}

	public void setHorrizontalFlip(boolean horrizontalFlip) {
		this.horrizontalFlip = horrizontalFlip;
	}

	public boolean getVerticalFlip() {
		return verticalFlip;
	}

	public void setVerticalFlip(boolean verticalFlip) {
		this.verticalFlip = verticalFlip;
	}
	
	public CameraConfiguration getCameraConfiguration() {
		return cameraConfiguration;
	}

	public void setCameraConfiguration(CameraConfiguration cameraConfiguration) {
		this.cameraConfiguration = cameraConfiguration;
	}

	public H264CameraConfiguration getH264CameraConfiguration() {
		return h264CameraConfiguration;
	}

	public void setH264CameraConfiguration(H264CameraConfiguration h264CameraConfiguration) {
		this.h264CameraConfiguration = h264CameraConfiguration;
	}
//sumit end: Camera Orientation Configuration
//sumit start: Re-Ordering Of Devices
	public String getOldLocationId() {
		return oldLocationId;
	}

	public void setOldLocationId(String oldLocationId) {
		this.oldLocationId = oldLocationId;
	}
//sumit end: Re-Ordering Of Devices	
//sumit start: ZXT120
	public String getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(String pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	public PIRConfiguration getPirConfiguration() {
		return pirConfiguration;
	}

	public void setPirConfiguration(PIRConfiguration pirConfiguration) {
		this.pirConfiguration = pirConfiguration;
	}
	//sumit end: ZXT120
	//parishod start
	public boolean isLowPower() {
		return isLowPower;
	}

	public void setLowPower(boolean isLowPower) {	
		this.isLowPower = isLowPower;
	}
//parishod end

	//parishod added for Pt control options
	public boolean getPTOption() {
		return PTOption;
	}

	public void setPTOption(boolean pTOption) {
		PTOption = pTOption;
	}
	//parishod end
//bhavya start
	public LCDRemoteConfiguration getLCDRemoteConfiguration() {
		return lCDRemoteConfiguration;
	}

	public void setLCDRemoteConfiguration(LCDRemoteConfiguration lCDRemoteConfiguration) {
		this.lCDRemoteConfiguration = lCDRemoteConfiguration;
	}
	public List<Scenario> getScenarios() {
		return scenarios;
	}
	public void setSenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

	public List<AlertsFromImvg> getAlertsFromImvg() {
		return AlertsFromImvg;
	}

	public void setAlertsFromImvg(List<AlertsFromImvg> alertsFromImvg) {
		AlertsFromImvg = alertsFromImvg;
	}

	public Device getAcdevice() {
		return acdevice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAcdevice(Device acdevice) {
		this.acdevice = acdevice;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	
//bhavya end

	//sumit start: AV Blaster Feature
	public List<AVCategory> getAvCategoryList() {
		return avCategoryList;
	}

	public void setAvCategoryList(List<AVCategory> avCategoryList) {
		this.avCategoryList = avCategoryList;
	}

	public List<AVBlaster> getAvBlasterList() {
		return avBlasterList;
	}

	public void setAvBlasterList(List<AVBlaster> avBlasterList) {
		this.avBlasterList = avBlasterList;
	}

	public List<String> getAvBrandList() {
		return avBrandList;
	}

	public void setAvBrandList(List<String> avBrandList) {
		this.avBrandList = avBrandList;
	}

	public List<String> getAvCodeList() {
		return avCodeList;
	}

	public void setAvCodeList(List<String> avCodeList) {
		this.avCodeList = avCodeList;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getAvCategorySelect() {
		return avCategorySelect;
	}

	public void setAvCategorySelect(String avCategorySelect) {
		this.avCategorySelect = avCategorySelect;
	}

	public String getAvBrandSelect() {
		return avBrandSelect;
	}

	public void setAvBrandSelect(String avBrandSelect) {
		this.avBrandSelect = avBrandSelect;
	}

	public String getAvCodeSelect() {
		return avCodeSelect;
	}

	public void setAvCodeSelect(String avCodeSelect) {
		this.avCodeSelect = avCodeSelect;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	//sumit end: AV Blaster Feature
	
	
public SirenConfiguration getSirenConfiguration() {
		
		return sirenConfiguration;
	}

	public void setSirenConfiguration(SirenConfiguration sirenConfiguration) {
		this.sirenConfiguration = sirenConfiguration;
	}
	
	public String ToConfigureSiren() throws Exception 
	{
	
		return SUCCESS;
	}
	
	
	public String UpdateSiren() throws Exception {
		XStream stream = new XStream();
		/*this.device.setDeviceConfiguration(this.motorConfiguration);*/
		this.device.setDeviceConfiguration(this.sirenConfiguration);
		String xmlString = stream.toXML(this.device);
	  
		
		IMonitorUtil.sendToController("commandIssueService", "ConfigureAndUpdateSiren", xmlString);
		return SUCCESS;
	}
	
	public DoorLockConfiguration getDoorLockConfiguration() {
		return doorLockConfiguration;
	}

	public void setDoorLockConfiguration(DoorLockConfiguration doorLockConfiguration) {
		this.doorLockConfiguration = doorLockConfiguration;
	}
	
	public String toupdateUsermode() throws Exception{
		
		return SUCCESS;
	}
	
/*	public String toupdateUsermodefordoorlock() throws Exception {
		XStream stream = new XStream();
		this.doorLockConfiguration.setDevice(device);
		String xmlString = stream.toXML(this.doorLockConfiguration);
		String result = "";
		String message = "";
		try {
	
			String resultXml = IMonitorUtil.sendToController("commandIssueService", "updatepasswordfordoorlock", xmlString);
			result = (String) stream.fromXML(resultXml);
			
		
		} catch (Exception e) {
			LogUtil.info("Got Exception", e);
		}
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){//"db")){
			message = "msg.imonitor.deviceupdateModeNosuccess.0001";
		}
		else if(result.equalsIgnoreCase(Constants.DB_FAILURE)){//"db")){
			message = "msg.imonitor.deviceupdateModeNo.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){//"iMVG")){
			message = "msg.imonitor.deviceupdateModeNo.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){//"no")){
			message = "msg.imonitor.deviceupdateModeNo.0003";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		//ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}
		
		return SUCCESS;
	}*/
	public String toupdateUsermodefordoorlock() throws Exception {
		XStream stream = new XStream();
		this.doorLockConfiguration.setDevice(device);
		String xmlString = stream.toXML(this.doorLockConfiguration);
		String result = "";
		String message = "";
		try {
	
			String resultXml = IMonitorUtil.sendToController("deviceService", "updatepasswordfordoorlock", xmlString);
			result = (String) stream.fromXML(resultXml);
			
	
		} catch (Exception e) {
			LogUtil.info("Got Exception", e);
		}
		if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){//"db")){
			message = "msg.imonitor.doorlocksetcode.0003";
		}
		else if(result.equalsIgnoreCase(Constants.DB_FAILURE)){//"db")){
			message = "msg.imonitor.doorlocksetcode.0004";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){//"iMVG")){
			message = "msg.imonitor.doorlocksetcode.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){//"no")){
			message = "msg.imonitor.deviceupdateModeNo.0003";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		//ActionContext.getContext().getSession().put("message", message);
		/*if(message.contains("Failure")){
			return ERROR;
		}*/
		
		return SUCCESS;
	}

	public List<String> getAcAutoMake() {
		return acAutoMake;
	}

	public void setAcAutoMake(List<String> acAutoMake) {
		this.acAutoMake = acAutoMake;
	}
	
	public String tosetAcbrandcode() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String selectedcode = stream.toXML(acSetCode);
		IMonitorUtil.sendToController("commandIssueService", "setacbrandcode", selectedcode, xmlString);
		
		return SUCCESS;
	}

	public String getAcSelectedBrand() {
		return acSelectedBrand;
	}

	public void setAcSelectedBrand(String acSelectedBrand) {
		this.acSelectedBrand = acSelectedBrand;
	}
	
	@SuppressWarnings("unchecked")
	public String togetAcbrandCodes() throws Exception{
		XStream stream = new XStream();
		String selectedBrand = stream.toXML(acSelectedBrand);
		String acBrandCodes = IMonitorUtil.sendToController("makeService", "getbrandcodesforselectedAc", selectedBrand);
		this.acCodeList = (List<String>) stream.fromXML(acBrandCodes);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String togetAcbrandCodesForManualSelect() throws Exception{
		XStream stream = new XStream();
		String selectedBrand = stream.toXML(acSelectedBrand);
		
		String acBrandCodes = IMonitorUtil.sendToController("makeService", "getbrandcodesforselectedAc", selectedBrand);
		this.acCodeList = (List<String>) stream.fromXML(acBrandCodes);
		return SUCCESS;
	}
   
	public List<String> getacCodeList() {
		return acCodeList;
	}

	public void setacCodeList(List<String> aacCodeList) {
		acCodeList = aacCodeList;
	}

	public String getAcSetCode() {
		return acSetCode;
	}

	public void setAcSetCode(String acSetCode) {
		this.acSetCode = acSetCode;
	}
	
	@SuppressWarnings("unchecked")
	public String togetModelListofAc() throws Exception{
		XStream stream = new XStream();
		String xmlOfAutomake = IMonitorUtil.sendToController("makeService", "listAutoMakeForAc");
		this.acAutoMake = ((List<String>) stream.fromXML(xmlOfAutomake));
		return SUCCESS;
	}

	public String getAcBrandSelected() {
		return acBrandSelected;
	}

	public void setAcBrandSelected(String acBrandSelected) {
		this.acBrandSelected = acBrandSelected;
	}

	public String getAvCodeSelected() {
		return avCodeSelected;
	}

	public void setAvCodeSelected(String avCodeSelected) {
		this.avCodeSelected = avCodeSelected;
	}

	public List<String> getAcManualSelect() {
		return acManualSelect;
	}

	public void setAcManualSelect(List<String> acManualSelect) {
		this.acManualSelect = acManualSelect;
	}

	public String tosetAvcodeForSelectedBrand() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String selectedcode = stream.toXML(avSetCode);
		IMonitorUtil.sendToController("commandIssueService", "setavbrandcode", selectedcode, xmlString);
		
		return SUCCESS;
	}

	public String getAvSetCode() {
		return avSetCode;
	}

	public void setAvSetCode(String avSetCode) {
		this.avSetCode = avSetCode;
	}

	public Make getConfiguredMake() {
		return configuredMake;
	}

	public void setConfiguredMake(Make configuredMake) {
		this.configuredMake = configuredMake;
	}

	public String toLearnAcMode() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.device);
		String selectedcode = stream.toXML(learnValue);
		IMonitorUtil.sendToController("commandIssueService", "setAcToLearnValue", selectedcode, xmlString);
		
		return SUCCESS;
	}
	
	/*Implemented by naveen for Minimote Configuration
	 * Get list of all devices (Only switch, dimmers, sirens, Panic button, Home,stay,away)
	*/
	@SuppressWarnings("unchecked")
	public String toUpdateminimoteConfiguration() throws Exception {
		XStream stream = new XStream();
	    String xmlMacId = stream.toXML(device.getGateWay().getMacId());
	    String xmlString = stream.toXML(this.device.getGeneratedDeviceId());
	    String xmlResult =  IMonitorUtil.sendToController("deviceService", "getDiviceListForMinimote", xmlMacId);
		this.devices = (List<Device>) stream.fromXML(xmlResult);
		String xmlConfig = IMonitorUtil.sendToController("deviceService", "getMiniMoteConfigurationByDeviceId", xmlString);
		this.device=(Device) stream.fromXML(xmlConfig);
		this.minimoteConfig = (MinimoteConfig) this.device.getDeviceConfiguration();
		return SUCCESS;
	}
	
	
	public String UpdateminimoteConfiguration() throws Exception {
		XStream stream = new XStream();
		String message = "";
	    this.device.setDeviceConfiguration(this.minimoteConfig);
	    String devicexml = stream.toXML(this.device);
	   // String xmlResult =  IMonitorUtil.sendToController("deviceService", "configMinimote", devicexml,buttonOne, buttonTwo, buttonThree, buttonFour);
	    String xmlResult =  IMonitorUtil.sendToController("deviceService", "configMinimote", devicexml);
	    String result = (String) stream.fromXML(xmlResult);
		if(result.equalsIgnoreCase(Constants.DB_FAILURE)){
			message = "msg.imonitor.deviceupdateCurtainModel.0001";
		}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){
			message = "msg.imonitor.deviceupdateCurtainModel.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
			message = "msg.imonitor.deviceupdateCurtainModel.0003";
		}else if (result.equalsIgnoreCase(Constants.DB_SUCCESS)) {
			message = "msg.imonitor.deviceupdateCurtainModel.0004";
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
//		ActionContext.getContext().getSession().put("message", message);
		if(message.contains("Failure")){
			return ERROR;
		}		
	    return SUCCESS;
	}
	
	// Minimote configuration end
	
	public String toConfigureDimmerType() throws Exception {
		XStream stream = new XStream();
		String xmlDevice = stream.toXML(this.device);
	
		
	
		try {
			String serviceName = "deviceService";
			String method = "toConfigureSwitchType";
			String xmlResult = IMonitorUtil.sendToController(serviceName, method, xmlDevice); 
			this.device = (Device) stream.fromXML(xmlResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return SUCCESS;
	}

	public String getLearnValue() {
		return learnValue;
	}

	public void setLearnValue(String learnValue) {
		this.learnValue = learnValue;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public boolean isHmdDevice() {
		return hmdDevice;
	}

	public void setHmdDevice(boolean hmdDevice) {
		this.hmdDevice = hmdDevice;
	}

	

	public MinimoteConfig getMinimoteConfig() {
		return minimoteConfig;
	}

	public void setMinimoteConfig(MinimoteConfig minimoteConfig) {
		this.minimoteConfig = minimoteConfig;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getCheckedDevice() {
		return checkedDevice;
	}

	public void setCheckedDevice(String checkedDevice) {
		this.checkedDevice = checkedDevice;
	}

	

	
	



	
	
	public Set<GateWay> getGateWays() {
		return gateWays;
	}

	public void setGateWays(Set<GateWay> gateWays) {
		this.gateWays = gateWays;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	
	@SuppressWarnings("unchecked")
	public String toSceneControlConfiguration() throws Exception {
		try {
			User user = (User) ActionContext.getContext().getSession().get(
					Constants.USER);
			long customerId = user.getCustomer().getId();
			Customer customer =  user.getCustomer();
			XStream stream = new XStream();
					
			//KeyConfiguration List
			String xmlDevice = stream.toXML(this.device);
			String serviceName = "deviceService";
			String method = "listAllKeyConfiguration";
			String result = IMonitorUtil.sendToController(serviceName, method,xmlDevice);
			this.keyConfig = (List<KeyConfiguration>)stream.fromXML(result);
			//String resultGateWay = IMonitorUtil.sendToController("deviceService", "getGateWay",customerIdXml);
			//LogUtil.info("Gateway id from method is "+resultGateWay);
			
		
			
			//Scenarios list
			/*LogUtil.info("Scenarios started--");
			String result = IMonitorUtil.sendToController(serviceName, method,customerIdXml);
			List<Scenario> scenarios = (List<Scenario>) stream.fromXML(result);
			this.setSenarios(scenarios);
			LogUtil.info("Scenarios ended--");*/
			String customerXml = stream.toXML(customer);
			//Action List
			
			String serviceName1 = "deviceService";
			String method1 = "listAllActions";
			String result1 = IMonitorUtil.sendToController(serviceName1, method1,customerXml);
			LogUtil.info("Result 1 : "+result1);
			List<Action> actionsObjects = (List<Action>) stream.fromXML(result1);
			keyObjects=new ArrayList<KeyObjects>();
			/*for(Action object : actionsObjects){
				LogUtil.info("Action for Start");
				KeyObjects keyObject = new KeyObjects();
				
				keyObject.setId(object.getId());
				LogUtil.info("Action for Step 1 "+object.getId());
				keyObject.setType("Action");
				LogUtil.info("Action for Step 2 "+object.getActionName());
				keyObject.setName(object.getActionName());
				LogUtil.info("Action for Step 3 ");
				LogUtil.info("Key object "+keyObject.getId()+" "+keyObject.getName());
				this.keyObjects.add(keyObject);
				LogUtil.info("Action for end");
				
			}*/
			/*for(Scenario sc : scenarios){
				KeyObjects keyObject = new KeyObjects();
				
				keyObject.setId(sc.getId());
				keyObject.setType("Scenario");
				keyObject.setName(sc.getName());
				this.keyObjects.add(keyObject);
			}
			*/
			
			
			
			
			//String resultXml = IMonitorUtil.sendToController("deviceService", "getSceneControllerconfiguration", stream.toXML(device.getGeneratedDeviceId()));//Scene Control object from db
		//	sceneControllerConfig = (SceneControllerConfig)stream.fromXML(resultXml);
			//LogUtil.info(resultXml);
		} catch (Exception e) {
			LogUtil.info("EX", e);
		}
		
		return SUCCESS;
	}
	
		
	//Diaken VIA
		public String toModbusSlave() throws Exception
		{
			XStream stream=new XStream();
			String message="";
			String deviceXml=stream.toXML(this.device);
			String resultXml = IMonitorUtil.sendToController("deviceService", "CountOfSlavesForVIA",deviceXml);
			
			Integer count=(Integer) stream.fromXML(resultXml);
			if (count>=4)
			{
				
				message = IMonitorUtil.formatMessage(this, "Maximum Slaves has been added");
				ActionContext.getContext().getSession().put("message", message);
				return ERROR;
			}
			
			return SUCCESS;
		}
		
		//Diaken VIA for Slave
		public String toDiscoverSlaveUnit() throws Exception
		{
			String message="";
			
			XStream stream = new XStream();
			
			String deviceXml=stream.toXML(device);
			String SlaveIdXml=stream.toXML(this.getSlaveId());
			String SlaveModel=stream.toXML(this.getSlaveModel());
			
			//Check for Slave Id in Db
			String resultofSlave = IMonitorUtil.sendToController("deviceService", "CheckSlaveInDB", deviceXml,SlaveIdXml);
			if (resultofSlave.equals("True"))
			{
				
				message = IMonitorUtil.formatMessage(this, "Slave with id "+this.getSlaveId()+" is already present");
				ActionContext.getContext().getSession().put("message", message);
				return ERROR;
			}
			
			String resultXml = IMonitorUtil.sendToController("deviceService", "toDiscoverSlaveUnit", deviceXml,SlaveIdXml,SlaveModel);
		    String result = (String) stream.fromXML(resultXml);
			if(result.equalsIgnoreCase("NoResponse"))
			{
				message = "Gateway did not respond";
			}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){
				message = "msg.imonitor.deviceupdateCurtainModel.0002";
			}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
				message = "Gateway did not respond";
			}else if (result.equalsIgnoreCase(Constants.DB_SUCCESS)) {
				message = "Slave added successfully";
			}
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		
		//Diaken VIA
		public String toDiscoverIndoorUnit() throws Exception
		{
			return SUCCESS;
		}
		
		//Diaken VIA
		public String DiscoverIndoorUnit() throws Exception
		{
			XStream stream = new XStream();
			String message="IDU Discovery Failed";
			String deviceXml=stream.toXML(device);
			String resultXml = IMonitorUtil.sendToController("deviceService", "DiscoverIndoorUnit", deviceXml);
		
			 String result = (String) stream.fromXML(resultXml);
				if(result.equalsIgnoreCase(Constants.DB_FAILURE)){
					message = "Indoor unit was not saved";
				}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){
					message = "Failure from gateway";
				}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
					message = "Gateway did not respond";
				}else if (result.equalsIgnoreCase(Constants.DB_SUCCESS)) {
					message = "Indoor units was successfully saved or updated";
				}
				message = IMonitorUtil.formatMessage(this, message);
				ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		
		//Diaken VIA
		public String toUpdateFriendlyNameAndLocationForSlave() throws Exception
		{
			String generatedDeviceId = device.getGeneratedDeviceId();
			XStream stream = new XStream();
			String xmlString = stream.toXML(generatedDeviceId);
			String xmlDevice = IMonitorUtil.sendToController("deviceService", "toUpdateFriendlyNameAndLocationForSlave", xmlString);
			this.device = (Device) stream.fromXML(xmlDevice);
			//sumit start: Required To Update the Postion Index if user changes Location for device. 	
			this.oldLocationId = Long.toString(this.device.getLocation().getId());
			//sumit end
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String customerXmlString = stream.toXML(customer);
			String xmlOfLocationList = IMonitorUtil.sendToController("locationService", "listLocations", customerXmlString);
			this.locations = (List<Location>) stream.fromXML(xmlOfLocationList);
//			new feature: Icon Selection for Device. Gets list of Icons based on DeviceType
			long deviceType = device.getDeviceType().getId();		
			String xmlDeviceType = stream.toXML(deviceType);	
			String xmlOfIconList = null;
			
			this.timeout = device.getEnableStatus();
		
		if((device.getModelNumber()!= null) && (device.getModelNumber().equalsIgnoreCase("HMD")))
		   {
			hmdDevice=true;
		   }
		else
		   {
			hmdDevice=false;
			
		   }
			try {
				xmlOfIconList = IMonitorUtil.sendToController("iconService", "listIconsByDeviceType", xmlDeviceType);
				this.listIcons = (List<Icon>) stream.fromXML(xmlOfIconList);												
				} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
		}
		
		
		//Diaken VIA
		public String updateFriendlyNameAndLocationForSlave() throws Exception 
		{
			 
			XStream stream = new XStream();
			device.setEnableStatus(this.timeout);

			if(this.isHmdDevice())
			{
			    device.setModelNumber("HMD");			
			}

			String xmlString = stream.toXML(this.device);

			String oldLocId = this.oldLocationId;
			
			
			String oldLocationIdXml = stream.toXML(oldLocId);
			
			String xmlMessage = IMonitorUtil.sendToController("deviceService", "updateFriendlyNameAndLocationForSlave", xmlString, oldLocationIdXml);
			
			return SUCCESS;
		}
		
		
		//Diaken VIA for Indoor unit
		public String toUpdateFriendlyNameAndLocationForIndoorUnit() throws Exception
		{
			String generatedDeviceId = device.getGeneratedDeviceId();
			XStream stream = new XStream();
			String xmlString = stream.toXML(generatedDeviceId);
			String xmlDevice = IMonitorUtil.sendToController("deviceService", "toUpdateFriendlyNameAndLocationForIndoorUnit", xmlString);
			this.device = (Device) stream.fromXML(xmlDevice);	
			this.oldLocationId = Long.toString(this.device.getLocation().getId());
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String customerXmlString = stream.toXML(customer);
			String xmlOfLocationList = IMonitorUtil.sendToController("locationService", "listLocations", customerXmlString);
			this.locations = (List<Location>) stream.fromXML(xmlOfLocationList);
			long deviceType = device.getDeviceType().getId();		
			String xmlDeviceType = stream.toXML(deviceType);	
			String xmlOfIconList = null;
			
			this.timeout = device.getEnableStatus();
		
		
		if((device.getModelNumber()!= null) && (device.getModelNumber().equalsIgnoreCase("HMD")))
		   {
			hmdDevice=true;
		   }
		else
		   {
			hmdDevice=false;
			
		   }
			try {
				xmlOfIconList = IMonitorUtil.sendToController("iconService", "listIconsByDeviceType", xmlDeviceType);
				this.listIcons = (List<Icon>) stream.fromXML(xmlOfIconList);												
				} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
		}
		
		
				//Diaken VIA
				public String updateFriendlyNameAndLocationForIndoorUnit() throws Exception 
				{
					XStream stream = new XStream();
					device.setEnableStatus(this.timeout);
					
					if(this.isHmdDevice())
					{
					    device.setModelNumber("HMD");			
					}
				
					
				     
					String xmlString = stream.toXML(this.device);
					String oldLocId = this.oldLocationId;
					
					
					
					String oldLocationIdXml = stream.toXML(oldLocId);		
					String xmlMessage = IMonitorUtil.sendToController("deviceService", "updateFriendlyNameAndLocationForIndoorUnit", xmlString, oldLocationIdXml);
					
					return SUCCESS;
				}
		
				
				
	//Diaken VIA			
	public String removeVIADevice() throws Exception
	{
		XStream stream = new XStream();
		String macId = device.getGateWay().getMacId();
		String did = device.getDeviceId();
		device.setGeneratedDeviceId(macId+"-"+did);
		String deviceXml=stream.toXML(device);
		String resultXml = IMonitorUtil.sendToController("deviceService", "removeVIADevice", deviceXml);
		if (resultXml.equals("Success"))
		{
			String message="VIA is Successfully deleted";
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		else
		{
			String message="Error while deleting VIA ";
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
	}
		
	//Diaken VIA			
	public String removeSlaveDevice() throws Exception
	{
		XStream stream = new XStream();
		String deviceXml=stream.toXML(device);
		String resultXml = IMonitorUtil.sendToController("deviceService", "removeSlaveDevice", deviceXml);
		if (resultXml.equals("Success"))
		{
			String message="Slave Device is Successfully deleted";
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		else
		{
			String message="Error while deleting Slave Device";
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		
	}
	
	//Remove Indoor Unit
	public String removeIndoorUnit() throws Exception
	{
		XStream stream = new XStream();
		String macId= device.getGateWay().getMacId();
		String dId = device.getDeviceId();
		device.setGeneratedDeviceId(macId+"-"+dId);
		String deviceXml=stream.toXML(device);
		String resultXml = IMonitorUtil.sendToController("deviceService", "removeIndoorUnit", deviceXml);
		if (resultXml.equals("Success"))
		{
			String message="Indoor Unit is Successfully deleted";
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		else
		{
			String message="Error while deleting Indoor Unit";
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		
	}



























	public String sendNetworkStatusRequest() throws Exception
	{
		
		XStream stream = new XStream();
		String deviceXml=stream.toXML(device);
		String resultXml = IMonitorUtil.sendToController("deviceService", "sendNetworkStatusRequest", deviceXml);
		NetworkStats networkStats=(NetworkStats) stream.fromXML(resultXml);
		if (networkStats!=null)
		{
			
			this.setRssiInfos(networkStats.getRssiInfosList());
			return SUCCESS;
		}
		else 
		{
			String message="Gateway did not respond";
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		
		
	}
	
	//Diaken VIA
	public String getCapability() throws Exception
	{
		XStream stream = new XStream();
		String deviceXml=stream.toXML(device);
		String result = IMonitorUtil.sendToController("deviceService", "getCapability", deviceXml);
		String message="";
			if(result.equalsIgnoreCase(Constants.DB_FAILURE)){
				message = "Failure Configuration was not saved";
			}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){
				message = "Failure from gateway";
			}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
				message = "Gateway did not respond";
			}else if (result.equalsIgnoreCase(Constants.DB_SUCCESS)) {
				message = "IDU Configuration was updated successfully";
			}
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	
	//Added for Removal of IDU by Apoorva
	@SuppressWarnings("unchecked")
	public String checkIDUDeviceForRule() throws Exception {
		XStream stream = new XStream();
		String message = "msg.imonitor.devicedelete.0000";
		String genDeviceId = stream.toXML(device.getGeneratedDeviceId());
		String Id =  IMonitorUtil.sendToController("deviceService", "getIdByGeneratedDeviceId", genDeviceId);
		long count =0;
		String countResult = IMonitorUtil.sendToController("ruleService", "getRuleCountForDevice", Id, genDeviceId);
		String countResult1 = IMonitorUtil.sendToController("scheduleService", "getScheduleCountForDevice",genDeviceId);
		String countResult2 = IMonitorUtil.sendToController("scenarioService", "getScenarioCountForDevice",genDeviceId);
		
		if(countResult.equalsIgnoreCase("RULE_EXISTS")){
			LogUtil.info("RULE_EXISTS");
			count += 4;
		}
		if(countResult1.equalsIgnoreCase("SCHEDULE_EXISTS")){
			LogUtil.info("SCHEDULE_EXISTS");
			count += 2;
		}if(countResult2.equalsIgnoreCase("SCENARIO_EXISTS")){
			LogUtil.info("SCENARIO_EXISTS");
			count += 1;
		}
		if(count >0){
			message = "msg.imonitor.devicedelete.000"+count;
			message = IMonitorUtil.formatMessage(this, message);
		}
		//Changes done in else block
		else
		{
		message = IMonitorUtil.formatFailureMessage(this, message);
		}
		
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	
	public String toGetCapability() throws Exception
	{
		return SUCCESS;
	}
	
	public String toResetFilterStatus() throws Exception
	{
		return SUCCESS;
	}
	
	public String resetFilterStatus() throws Exception
	{
		XStream stream = new XStream();
		String message="";
		String deviceXml=stream.toXML(device);
		String result = IMonitorUtil.sendToController("deviceService", "resetFilterStatus", deviceXml);	
			if(result.equalsIgnoreCase("UpdateFailure")){
				message = "Failure, Filter status was not updated";
			}else if (result.equalsIgnoreCase("UpdateSuccess")) {
				message = "Filter Status updated successfully";
			}
			else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
				message = "Gateway did not respond";
				message = IMonitorUtil.formatMessage(this, message);
				ActionContext.getContext().getSession().put("message", message);
				return ERROR;
			}
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	
	//RGB
		public String sendRGBValue() throws Exception
		{
			String message = "";
			LogUtil.info("sendRGBValue method");
			XStream stream=new XStream();
			LogUtil.info("Device details :"+this.device.getId() + "   "+ this.device.getGeneratedDeviceId() + this.device.getGateWay().getMacId());
			String hexValue= this.hexValue;
			
			LogUtil.info("hexValue : "+this.hexValue);
			
			/*if (hexValue.length() > 7)
			{ 
				LogUtil.info("Length is more than 6");
				message = IMonitorUtil.formatMessage(this, "Please enter 6 digits");
				ActionContext.getContext().getSession().put("message", message);
				return ERROR;
			}*/
			
			String hex = hexValue.toLowerCase();
			
			if (hex.matches(".*[g-z].*") || hex.matches(".*[!@#$%&*()_+=|<>?{}\\[\\]~-].*")) { 
				LogUtil.info("Hex value character error");
				message = IMonitorUtil.formatMessage(this, "Invalid hex code");
				ActionContext.getContext().getSession().put("message", message);
				return ERROR;
			}
			
			String devicexml=stream.toXML(device);
			String hexXml = stream.toXML(hexValue);
			String resultxml = IMonitorUtil.sendToController("deviceService", "sendRGBValue",devicexml,hexXml);
			String result = (String) stream.fromXML(resultxml);
			LogUtil.info("resulttt : "+result);
			if(result.equalsIgnoreCase(Constants.DB_SUCCESS))
			{
				message = "Success:Value is set";
			}
			else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){
				message = "Failure:Failure From Gateway";
			}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
				message = "Failure:Gateway did not Respond";
			}
			//message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			if(message.contains("Failure")){
				ActionContext.getContext().getSession().put("message", message);
				return ERROR;
			}
			LogUtil.info(message);
			return SUCCESS;
		}
		
		//IR Output AV Blaster
		public String toIROutputValue() throws Exception{
			XStream stream = new XStream();
			return SUCCESS;
		}
		
		//IR AV Blaster Output changes
		public String updateIROutputValue() throws Exception {
			LogUtil.info("Ac Channel Code : " + this.avChannelCode);
			XStream stream = new XStream();
			String channelCode = this.avChannelCode;
			String channelCodeXml = stream.toXML(channelCode);
			String deviceXml = stream.toXML(this.device);	
			String result = "";
			String message = "";
			try
			{
				String resultXml = IMonitorUtil.sendToController("deviceService", "updateIROutputValue", deviceXml,channelCodeXml);
				result = (String) stream.fromXML(resultXml);
			} catch (Exception e) {
				LogUtil.info("Got Exception", e);
			}
			if(result.equalsIgnoreCase(Constants.DB_SUCCESS)){
				message = "Port Configuration saved successfully";
			}
			else if(result.equalsIgnoreCase(Constants.DB_FAILURE)){
				message = "Port Configuration could not be saved.";
			}else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE)){
				message = "msg.imonitor.deviceupdateIR.0002";
			}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY)){
				message = "msg.imonitor.deviceupdateIR.0003";
			}
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			if(message.contains("Failure")){
				return ERROR;
			}
			return SUCCESS;
		}
		
		//Virtual Switch Changes
		
		
		//Virtual Switch Changes
		@SuppressWarnings("unchecked")
		public String toVirtualSwitchConfiguration() throws Exception {
			try {
				User user = (User) ActionContext.getContext().getSession().get(
						Constants.USER);
				Customer customer =  user.getCustomer();
				XStream stream = new XStream();
				//KeyConfiguration List
				String xmlDevice = stream.toXML(this.device);
				String serviceName = "deviceService";
				String method = "listAllKeyConfiguration";
				String result = IMonitorUtil.sendToController(serviceName, method,xmlDevice);
				this.keyConfig = (List<KeyConfiguration>)stream.fromXML(result);
				LogUtil.info("KeyConfiguration Objects From DB "+result);
				List<KeyConfiguration> keyconfigObjects = (List<KeyConfiguration>) stream.fromXML(result);
				key1=keyconfigObjects.get(0);
				key12=keyconfigObjects.get(1);
				String customerXml = stream.toXML(customer);
				//Action List
				LogUtil.info("Action List started--");
				LogUtil.info("Gateway macId: "+this.device.getGateWay().getMacId());
				String serviceName1 = "deviceService";
				String method1 = "listAllActions";
				String result1 = IMonitorUtil.sendToController(serviceName1, method1,customerXml);
				//LogUtil.info("Result 1 : "+result1);
				 actions = (List<Action>) stream.fromXML(result1);
				 if (actions.isEmpty())
				 {
						return ERROR;
				 }
				 LogUtil.info("ActionList"+result1);
			} catch (Exception e) {
				LogUtil.info("EX", e);
			}
			
			return SUCCESS;
		}
		
		
		public String saveVirtualSwitchConfiguration() throws Exception {
			XStream stream = new XStream();
			List<KeyConfiguration> keyconfigObjects=new ArrayList<KeyConfiguration>();
			keyconfigObjects.add(key1);
			keyconfigObjects.add(key12);
			String keyconfigObjectsXml = stream.toXML(keyconfigObjects);
			Device device=new Device();
			device.setId(key1.getDevice().getId());
			String xmlString = stream.toXML(device);
			String result = "";
			String message = "";
			String resultXml = IMonitorUtil.sendToController("deviceService", "saveVirtualSwitchConfiguration", xmlString, keyconfigObjectsXml);
			result = (String) stream.fromXML(resultXml);
			if(result.equalsIgnoreCase(Constants.DB_SUCCESS))
			{
				message = "msg.imonitor.VirtualSwitchConfiguration.success.0001";
			}
			else
			{
				if(result.equalsIgnoreCase(Constants.DB_FAILURE))
				{
					message = "msg.imonitor.VirtualSwitchConfiguration.0001";
				}
				else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE))
				{
					message = "msg.imonitor.VirtualSwitchConfiguration.0002";
				}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY))
				{
					message = "msg.imonitor.VirtualSwitchConfiguration.0003";
				}
				else
				{
					message = "msg.imonitor.VirtualSwitchConfiguration.0004";
				}
				
			}
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			
			if(message.startsWith(Constants.MSG_FAILURE))
			return ERROR;
			else
			return SUCCESS;
		}
		
		@SuppressWarnings("unchecked")
		public String multichannel() throws Exception
		{
			LogUtil.info("multi channel method");
			XStream stream = new XStream();
			String deviceXml=stream.toXML(device);
			
			
			
			String deviceListXml = IMonitorUtil.sendToController("deviceService", "getDeviceListByMacId",deviceXml);
		//	LogUtil.info("Devices list multi channel"+deviceListXml);
			this.device=new Device();
			this.device1=new Device();
			this.device2=new Device();
			this.device3=new Device();
			this.device4=new Device();
			this.device5=new Device();
			this.devices=(List<Device>) stream.fromXML(deviceListXml);
			return SUCCESS;
		}
		
		public String sendMultiAssociateDevices() throws Exception
		{
			LogUtil.info("sendMultiAssociateDevices method");
			XStream stream=new XStream();
			List<Device> devices=new ArrayList<Device>();
			LogUtil.info("1 ");
			devices.add(device1);
			devices.add(device2);
			devices.add(device3);
			devices.add(device4);
			devices.add(device5);
			LogUtil.info("2 ");
			String devicexml=stream.toXML(device);
			String xmlString = stream.toXML(devices);
			String message = IMonitorUtil.sendToController("commandIssueService", "nupdateDeviceForMulipleDevices",devicexml, xmlString);
			
			return SUCCESS;
		}
		

	public List<KeyObjects> getKeyObjects() {
		return keyObjects;
	}

	public void setKeyObjects(List<KeyObjects> keyObjects) {
		this.keyObjects = keyObjects;
	}

	public List<KeyConfiguration> getKeyConfig() {
		return keyConfig;
	}

	public void setKeyConfig(List<KeyConfiguration> keyConfig) {
		this.keyConfig = keyConfig;
	}

	public List<NetworkStats> getNetworkStats() {
		return networkStats;
	}

	public void setNetworkStats(List<NetworkStats> networkStats) {
		this.networkStats = networkStats;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Set<RssiInfo> getRssiInfos() {
		return rssiInfos;
	}

	public void setRssiInfos(Set<RssiInfo> rssiInfos) {
		this.rssiInfos = rssiInfos;
	}

public String getSlaveId() {
		return SlaveId;
	}

	public void setSlaveId(String slaveId) {
		SlaveId = slaveId;
	}

	public ModbusSlave getModbusSlave() {
		return modbusSlave;
	}

	public void setModbusSlave(ModbusSlave modbusSlave) {
		this.modbusSlave = modbusSlave;
	}

	public String getSlaveModel() {
		return SlaveModel;
	}

	public void setSlaveModel(String slaveModel) {
		SlaveModel = slaveModel;
	}
	public String getAvChannelCode() {
		return avChannelCode;
	}

	public void setAvChannelCode(String avChannelCode) {
		this.avChannelCode = avChannelCode;
	}

	public KeyConfiguration getKey1() {
		return key1;
	}

	public void setKey1(KeyConfiguration key1) {
		this.key1 = key1;
	}

	public KeyConfiguration getKey12() {
		return key12;
	}

	public void setKey12(KeyConfiguration key12) {
		this.key12 = key12;
	}


	

	
	
}
