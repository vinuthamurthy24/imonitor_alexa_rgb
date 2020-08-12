package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class NewDeviceType
{
	private long id;
	private String deviceType;
	private String deviceTypeNumber;
	private String basicDeviceClass;
	private String genericDeviceClass;
	private String specificDeviceClass;
	private String supportedClass;
	private String manufacturerId;
	private String productId;
	private String modelNumber;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getBasicDeviceClass() {
		return basicDeviceClass;
	}
	public void setBasicDeviceClass(String basicDeviceClass) {
		this.basicDeviceClass = basicDeviceClass;
	}
	public String getGenericDeviceClass() {
		return genericDeviceClass;
	}
	public void setGenericDeviceClass(String genericDeviceClass) {
		this.genericDeviceClass = genericDeviceClass;
	}
	public String getSpecificDeviceClass() {
		return specificDeviceClass;
	}
	public void setSpecificDeviceClass(String specificDeviceClass) {
		this.specificDeviceClass = specificDeviceClass;
	}
	public String getSupportedClass() {
		return supportedClass;
	}
	public void setSupportedClass(String supportedClass) {
		this.supportedClass = supportedClass;
	}
	public String getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getDeviceTypeNumber() {
		return deviceTypeNumber;
	}
	public void setDeviceTypeNumber(String deviceTypeNumber) {
		this.deviceTypeNumber = deviceTypeNumber;
	}
	
}
