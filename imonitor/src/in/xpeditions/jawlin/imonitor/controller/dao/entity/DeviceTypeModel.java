package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class DeviceTypeModel 
{
	private long id;
	private String model_name;
	private DeviceType deviceType;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getModel_name() {
		return model_name;
	}
	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}
	
	
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	@Override
	public String toString() {
		return "DeviceTypeModel [id=" + id + ", model_name=" + model_name
				+ ", deviceTypes=" + deviceType + "]";
	}
}
