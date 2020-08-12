package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class SirenConfiguration extends DeviceConfiguration{
	private int timeOutValue;
	private int sirenType;

	public int getTimeOutValue() {
		return timeOutValue;
	}

	public void setTimeOutValue(int timeOutValue) {
		this.timeOutValue = timeOutValue;
	}

	public int getSirenType() {
		return sirenType;
	}

	public void setSirenType(int sirenType) {
		this.sirenType = sirenType;
	}

}
