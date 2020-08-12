package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class AVRemoteControl {
	private long id;
	private String keyname;
	private String decimalValue;
	public String getDecimalValue() {
		return decimalValue;
	}
	public void setDecimalValue(String decimalValue) {
		this.decimalValue = decimalValue;
	}
	private String hexAVKey;
	private String data;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKeyname() {
		return keyname;
	}
	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}
	public String getHexAVKey() {
		return hexAVKey;
	}
	public void setHexAVKey(String hexAVKey) {
		this.hexAVKey = hexAVKey;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
