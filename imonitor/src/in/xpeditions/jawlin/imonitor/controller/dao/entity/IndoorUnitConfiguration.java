package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class IndoorUnitConfiguration extends DeviceConfiguration
{
	private String fanModeCapability;
	private String coolModeCapability;
	private String heatModeCapability;
	private String autoModeCapability;
	private String dryModeCapability;
	private String fanDirectionLevelCapability;
	private String fanDirectionCapability;
	private String fanVolumeLevelCapability;
	private String fanVolumeCapability;
	private String coolingUpperlimit;
	private String coolingLowerlimit;
	private String heatingUpperlimit;
	private String heatingLowerlimit;
	private long ConnectStatus;
	private long CommStatus;
	private String fanModeValue;
	private String sensedTemperature;
	private String fanVolumeValue;
	private String fanDirectionValue;
	private String errorMessage;
	private int filterSignStatus;
	private int temperatureValue;
	
	public String getFanModeCapability() {
		return fanModeCapability;
	}
	public void setFanModeCapability(String fanModeCapability) {
		this.fanModeCapability = fanModeCapability;
	}
	public String getCoolModeCapability() {
		return coolModeCapability;
	}
	public void setCoolModeCapability(String coolModeCapability) {
		this.coolModeCapability = coolModeCapability;
	}
	public String getHeatModeCapability() {
		return heatModeCapability;
	}
	public void setHeatModeCapability(String heatModeCapability) {
		this.heatModeCapability = heatModeCapability;
	}
	public String getAutoModeCapability() {
		return autoModeCapability;
	}
	public void setAutoModeCapability(String autoModeCapability) {
		this.autoModeCapability = autoModeCapability;
	}
	public String getDryModeCapability() {
		return dryModeCapability;
	}
	public void setDryModeCapability(String dryModeCapability) {
		this.dryModeCapability = dryModeCapability;
	}
	public String getFanDirectionLevelCapability() {
		return fanDirectionLevelCapability;
	}
	public void setFanDirectionLevelCapability(String fanDirectionLevelCapability) {
		this.fanDirectionLevelCapability = fanDirectionLevelCapability;
	}
	public String getFanDirectionCapability() {
		return fanDirectionCapability;
	}
	public void setFanDirectionCapability(String fanDirectionCapability) {
		this.fanDirectionCapability = fanDirectionCapability;
	}
	
	public String getFanVolumeCapability() {
		return fanVolumeCapability;
	}
	public void setFanVolumeCapability(String fanVolumeCapability) {
		this.fanVolumeCapability = fanVolumeCapability;
	}
	public String getCoolingUpperlimit() {
		return coolingUpperlimit;
	}
	public void setCoolingUpperlimit(String coolingUpperlimit) {
		this.coolingUpperlimit = coolingUpperlimit;
	}
	
	public String getHeatingUpperlimit() {
		return heatingUpperlimit;
	}
	public void setHeatingUpperlimit(String heatingUpperlimit) {
		this.heatingUpperlimit = heatingUpperlimit;
	}
	public String getHeatingLowerlimit() {
		return heatingLowerlimit;
	}
	public void setHeatingLowerlimit(String heatingLowerlimit) {
		this.heatingLowerlimit = heatingLowerlimit;
	}
	public String getFanVolumeLevelCapability() {
		return fanVolumeLevelCapability;
	}
	public void setFanVolumeLevelCapability(String fanVolumeLevelCapability) {
		this.fanVolumeLevelCapability = fanVolumeLevelCapability;
	}
	public String getCoolingLowerlimit() {
		return coolingLowerlimit;
	}
	public void setCoolingLowerlimit(String coolingLowerlimit) {
		this.coolingLowerlimit = coolingLowerlimit;
	}
	public long getConnectStatus() {
		return ConnectStatus;
	}
	public void setConnectStatus(long connectStatus) {
		ConnectStatus = connectStatus;
	}
	public long getCommStatus() {
		return CommStatus;
	}
	public void setCommStatus(long commStatus) {
		CommStatus = commStatus;
	}
	public String getFanModeValue() {
		return fanModeValue;
	}
	public void setFanModeValue(String fanModeValue) {
		this.fanModeValue = fanModeValue;
	}
	public String getSensedTemperature() {
		return sensedTemperature;
	}
	public void setSensedTemperature(String sensedTemperature) {
		this.sensedTemperature = sensedTemperature;
	}
	public String getFanVolumeValue() {
		return fanVolumeValue;
	}
	public void setFanVolumeValue(String fanVolumeValue) {
		this.fanVolumeValue = fanVolumeValue;
	}
	public String getFanDirectionValue() {
		return fanDirectionValue;
	}
	public void setFanDirectionValue(String fanDirectionValue) {
		this.fanDirectionValue = fanDirectionValue;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getFilterSignStatus() {
		return filterSignStatus;
	}
	public void setFilterSignStatus(int filterSignStatus) {
		this.filterSignStatus = filterSignStatus;
	}
	public int getTemperatureValue() {
		return temperatureValue;
	}
	public void setTemperatureValue(int temperatureValue) {
		this.temperatureValue = temperatureValue;
	}
}
