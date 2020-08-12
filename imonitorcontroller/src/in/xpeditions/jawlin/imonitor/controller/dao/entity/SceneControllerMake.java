package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class SceneControllerMake 
{
	private long id;
	private String ModelName;
	private String keyName;
	private String noOfKeys;
	private String pressType;
	private String keyCode;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNoOfKeys() {
		return noOfKeys;
	}
	public void setNoOfKeys(String noOfKeys) {
		this.noOfKeys = noOfKeys;
	}
	public String getPressType() {
		return pressType;
	}
	public void setPressType(String pressType) {
		this.pressType = pressType;
	}
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public String getModelName() {
		return ModelName;
	}
	public void setModelName(String modelName) {
		ModelName = modelName;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
}
