/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.List;
import java.util.Set;


/**
 * @author Coladi
 *
 */
public class Device {
	private long id; // id - auto increment.
	private String deviceId; // What customer gives
	private String generatedDeviceId; // What we generate
	private String friendlyName;
	private String batteryStatus;
	private DeviceType deviceType;
	private String enableStatus;
	private Make make;
	private Location location;
	private String modelNumber;
	private String commandParam = "0"; // 0 means off, 1 means on.
	private AlertType lastAlert; // 0 means down, 1 means up.
	private DeviceConfiguration deviceConfiguration;
	private GateWay gateWay;
	private List<Rule> rules;
	private Status armStatus;
	private Set<ImvgAlertsAction> imvgAlertsActions;
	private Set<AlertsFromImvg> alertsFromImvgs;
	private Icon icon;			//For Select Icon Based on DeviceType feature
	//sumit begin
	private String mode;
	private boolean modeHome;
	private boolean modeStay;
	private boolean modeAway;
	private String currentMode;
	private String enableList;	// 0 means disable from getting listed in home screen, 1 means enable listing device in home screen.
	private boolean checkEnableList;
	
	private long posIdx; //Re-Ordering Of Device 
	private long switchType; //Rocker or Tact (only for Switches and Dimmers)
	//sumit ends
	private LocationMap locationMap;
	private String  selected;
	private String  HMDalertTime;
	private String  HMDalertstatus;
	private String  HMDalertValue;
	private String  HMDpowerinfo;
	private String  devicetimeout;
	private String pulseTimeOut;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public String getEnableStatus() {
		return enableStatus;
	}
	
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}

	public Make getMake() {
		return make;
	}
	public void setMake(Make make) {
		this.make = make;
	}
	public String getGeneratedDeviceId() {
		return generatedDeviceId;
	}
	public void setGeneratedDeviceId(String generatedDeviceId) {
		this.generatedDeviceId = generatedDeviceId;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public DeviceConfiguration getDeviceConfiguration() {
		return deviceConfiguration;
	}
	public void setDeviceConfiguration(DeviceConfiguration deviceConfiguration) {
		this.deviceConfiguration = deviceConfiguration;
	}
	public String getFriendlyName() {
		return friendlyName;
	}
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}
	public GateWay getGateWay() {
		return gateWay;
	}
	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}
	public String getCommandParam() {
		return commandParam;
	}
	public void setCommandParam(String commandParam) {
		this.commandParam = commandParam;
	}
	public String getBatteryStatus() {
		return batteryStatus;
	}
	public void setBatteryStatus(String batteryStatus) {
		this.batteryStatus = batteryStatus;
	}
	public final List<Rule> getRules() {
		return rules;
	}
	public final void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	public final AlertType getLastAlert() {
		return lastAlert;
	}
	public final void setLastAlert(AlertType lastAlert) {
		this.lastAlert = lastAlert;
	}
	public final Status getArmStatus() {
		return armStatus;
	}
	public final void setArmStatus(Status armStatus) {
		this.armStatus = armStatus;
	}
	public final Set<ImvgAlertsAction> getImvgAlertsActions() {
		return imvgAlertsActions;
	}
	public final void setImvgAlertsActions(Set<ImvgAlertsAction> imvgAlertsActions) {
		this.imvgAlertsActions = imvgAlertsActions;
	}
	public final Set<AlertsFromImvg> getAlertsFromImvgs() {
		return alertsFromImvgs;
	}
	public final void setAlertsFromImvgs(Set<AlertsFromImvg> alertsFromImvgs) {
		this.alertsFromImvgs = alertsFromImvgs;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getModelNumber() {
		return modelNumber;
	}

	public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	//sumit begin
	public String getMode() {
		mode = "";
		if(this.modeHome)
		{
			mode += "1";
		}
		else
		{
			mode += "0";
		}if(this.modeStay)
		{
			mode += "1";
		}
		else
		{
			mode += "0";
		}if(this.modeAway)
		{
			mode += "1";
		}
		else
		{
			mode += "0";
		}
		/*	
		if(this.modeHome != null && this.modeStay != null && this.modeAway != null)
				this.mode = this.modeHome+this.modeStay+this.modeAway;*/
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
		if(mode != null && mode.length() == 3)
		{
			this.modeHome = false;
			this.modeStay = false;
			this.modeAway = false;
			if(mode.substring(0, 1).equals("1"))
				this.modeHome = true;
			if(mode.substring(1, 2).equals("1"))
				this.modeStay = true;
			if(mode.substring(2, 3).equals("1"))
				this.modeAway = true;
			//this.modeStay = mode.substring(1, 2);
			//this.modeAway = mode.substring(2, 3);
		}
	}
	
	public boolean getModeHome() {
		return modeHome;
	}
	public void setModeHome(boolean modeHome) {
		this.modeHome = modeHome;
	}
	public boolean getModeStay() {
		return modeStay;
	}
	public void setModeStay(boolean modeStay) {
		this.modeStay = modeStay;
	}
	public boolean getModeAway() {
		return modeAway;
	}
	public void setModeAway(boolean modeAway) {
		this.modeAway = modeAway;
	}
	
	public String getCurrentMode() {
		return currentMode;
	}
	public void setCurrentMode(String currentMode) {
		this.currentMode = currentMode;
	}
	public String getEnableList() {
		enableList = "";
		if(this.checkEnableList){
			enableList = "1";
		}else{
			enableList = "0";
		}
		return enableList;
	}
	public void setEnableList(String enableList) {
		this.enableList = enableList;
		if(enableList != null && enableList.length() == 1){
			this.checkEnableList = false;
			if(enableList.equals("1")){
				this.checkEnableList = true;
			}
		}
	}
	public boolean isCheckEnableList() {
		return checkEnableList;
	}
	public void setCheckEnableList(boolean checkEnableList) {
		this.checkEnableList = checkEnableList;
	}
	//Re-Ordering Of Decvice
	public long getPosIdx() {
		return posIdx;
	}
	public void setPosIdx(long posIdx) {
		this.posIdx = posIdx;
	}
	//Re-Ordering Of Device
	//Rocker or Tact switch
	public long getSwitchType() {
		return switchType;
	}
	public void setSwitchType(long switchType) {
		this.switchType = switchType;
	}
	//Rocker or Tact switch
	//sumit end
	public LocationMap getLocationMap() {
		return locationMap;
	}
	public void setLocationMap(LocationMap locationMap) {
		this.locationMap = locationMap;
	}

	
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	public String getHMDalertTime() {
		return HMDalertTime;
	}
	public void setHMDalertTime(String hMDalertTime) {
		HMDalertTime = hMDalertTime;
	}
	public String getHMDalertstatus() {
		return HMDalertstatus;
	}
	public void setHMDalertstatus(String hMDalertstatus) {
		HMDalertstatus = hMDalertstatus;
	}
	public String getHMDalertValue() {
		return HMDalertValue;
	}
	public void setHMDalertValue(String hMDalertValue) {
		HMDalertValue = hMDalertValue;
	}
	public String getHMDpowerinfo() {
		return HMDpowerinfo;
	}
	public void setHMDpowerinfo(String hMDpowerinfo) {
		HMDpowerinfo = hMDpowerinfo;
	}
	public String getDevicetimeout() {
		return devicetimeout;
	}
	public void setDevicetimeout(String devicetimeout) {
		this.devicetimeout = devicetimeout;
	}
	public String getPulseTimeOut() {
		return pulseTimeOut;
	}
	public void setPulseTimeOut(String pulseTimeOut) {
		this.pulseTimeOut = pulseTimeOut;
	}
	
	
	
	
	
}