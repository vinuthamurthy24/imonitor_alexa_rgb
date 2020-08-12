/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author Coladi
 *
 */
public class H264CameraConfiguration extends DeviceConfiguration{
	private String videoBrightness;
	private String videoContrast;
	private String videoAcFrequency;
	private String videoFrameRate;
	private String videoResolution;
	private String videoQuality;
	private String videoBitRate;
	private String videoBitRateMode;
	private String videoKeyFrame;
	private String ledMode;
	private String ptzPatrolRate;
	private String ptzPatrolUpRate;
	private String ptzPatrolDownRate;
	private String ptzPatrolLeftRate;
	private String ptzPatrolRightRate;
	private String adminUserName;
	private String adminPassword;
	private String pantiltControl;
	private String controlNightVision;
	//sumit start: Camera Orientation Feature
	private String cameraOrientation;
	//sumit end: Camera Orientation Feature
    private long presetvalue;

	public String getVideoBrightness() {
		return videoBrightness;
	}
	public void setVideoBrightness(String videoBrightness) {
		this.videoBrightness = videoBrightness;
	}
	public String getVideoContrast() {
		return videoContrast;
	}
	public void setVideoContrast(String videoContrast) {
		this.videoContrast = videoContrast;
	}
	public String getVideoAcFrequency() {
		return videoAcFrequency;
	}
	public void setVideoAcFrequency(String videoAcFrequency) {
		this.videoAcFrequency = videoAcFrequency;
	}
	public String getVideoFrameRate() {
		return videoFrameRate;
	}
	public void setVideoFrameRate(String videoFrameRate) {
		this.videoFrameRate = videoFrameRate;
	}
	public String getVideoResolution() {
		return videoResolution;
	}
	public void setVideoResolution(String videoResolution) {
		this.videoResolution = videoResolution;
	}
	public String getVideoQuality() {
		return videoQuality;
	}
	public void setVideoQuality(String videoQuality) {
		this.videoQuality = videoQuality;
	}
	public String getVideoBitRate() {
		return videoBitRate;
	}
	public void setVideoBitRate(String videoBitRate) {
		this.videoBitRate = videoBitRate;
	}
	public String getVideoBitRateMode() {
		return videoBitRateMode;
	}
	public void setVideoBitRateMode(String videoBitRateMode) {
		this.videoBitRateMode = videoBitRateMode;
	}
	public String getVideoKeyFrame() {
		return videoKeyFrame;
	}
	public void setVideoKeyFrame(String videoKeyFrame) {
		this.videoKeyFrame = videoKeyFrame;
	}
	public String getLedMode() {
		return ledMode;
	}
	public void setLedMode(String ledMode) {
		this.ledMode = ledMode;
	}
	public String getPtzPatrolRate() {
		return ptzPatrolRate;
	}
	public void setPtzPatrolRate(String ptzPatrolRate) {
		this.ptzPatrolRate = ptzPatrolRate;
	}
	public String getPtzPatrolUpRate() {
		return ptzPatrolUpRate;
	}
	public void setPtzPatrolUpRate(String ptzPatrolUpRate) {
		this.ptzPatrolUpRate = ptzPatrolUpRate;
	}
	public String getPtzPatrolDownRate() {
		return ptzPatrolDownRate;
	}
	public void setPtzPatrolDownRate(String ptzPatrolDownRate) {
		this.ptzPatrolDownRate = ptzPatrolDownRate;
	}
	public String getPtzPatrolLeftRate() {
		return ptzPatrolLeftRate;
	}
	public void setPtzPatrolLeftRate(String ptzPatrolLeftRate) {
		this.ptzPatrolLeftRate = ptzPatrolLeftRate;
	}
	public String getAdminUserName() {
		return adminUserName;
	}
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getPantiltControl() {
		return pantiltControl;
	}
	public void setPantiltControl(String pantiltControl) {
		this.pantiltControl = pantiltControl;
	}
	public String getControlNightVision() {
		return controlNightVision;
	}
	public void setControlNightVision(String controlNightVision) {
		this.controlNightVision = controlNightVision;
	}
	public String getPtzPatrolRightRate() {
		return ptzPatrolRightRate;
	}
	public void setPtzPatrolRightRate(String ptzPatrolRightRate) {
		this.ptzPatrolRightRate = ptzPatrolRightRate;
	}
	//sumit start: Camera Orientation Feature
	public String getCameraOrientation() {
		return cameraOrientation;
	}
	public void setCameraOrientation(String cameraOrientation) {
		this.cameraOrientation = cameraOrientation;
	}
	//sumit end: Camera Orientation Feature
	public long getPresetvalue() {
		return presetvalue;
	}
	public void setPresetvalue(long presetvalue) {
		this.presetvalue = presetvalue;
	}
}
