package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class PushNotification 
{
	private long id ;
	private Customer customer;
	private String deviceToken;
	private String deviceType;
	private String ImeiNumber;
	private String appName;
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getImeiNumber() {
		return ImeiNumber;
	}
	public void setImeiNumber(String imeiNumber) {
		ImeiNumber = imeiNumber;
	}
	
	
}
