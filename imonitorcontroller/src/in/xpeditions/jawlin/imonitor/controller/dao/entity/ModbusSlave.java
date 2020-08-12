package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class ModbusSlave
{
	private long id;
	private Device deviceId;
	private String HAIF_Id;
	private String FriendlyName;
	private Location location;
	private long Connected_Units;
	private DeviceType deviceType;
	private Icon icon;
	private long enableStatus;
	private GateWay gateWay;
	private LocationMap locationMap;
	
	private String enableList;	// 0 means disable from getting listed in home screen, 1 means enable listing device in home screen.
	private boolean checkEnableList;
	private long posIdx;
	private long SlaveId;
	
	
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
	
	public Device getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Device deviceId) {
		this.deviceId = deviceId;
	}
	public String getHAIF_Id() {
		return HAIF_Id;
	}
	public void setHAIF_Id(String hAIF_Id) {
		HAIF_Id = hAIF_Id;
	}
	public String getFriendlyName() {
		return FriendlyName;
	}
	public void setFriendlyName(String friendlyName) {
		FriendlyName = friendlyName;
	}
	public long getConnected_Units() {
		return Connected_Units;
	}
	public void setConnected_Units(long connected_Units) {
		Connected_Units = connected_Units;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public long getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(long enableStatus) {
		this.enableStatus = enableStatus;
	}
	public GateWay getGateWay() {
		return gateWay;
	}
	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}
	public LocationMap getLocationMap() {
		return locationMap;
	}
	public void setLocationMap(LocationMap locationMap) {
		this.locationMap = locationMap;
	}
	public long getPosIdx() {
		return posIdx;
	}
	public void setPosIdx(long posIdx) {
		this.posIdx = posIdx;
	}
	public long getSlaveId() {
		return SlaveId;
	}
	public void setSlaveId(long slaveId) {
		SlaveId = slaveId;
	}
}
