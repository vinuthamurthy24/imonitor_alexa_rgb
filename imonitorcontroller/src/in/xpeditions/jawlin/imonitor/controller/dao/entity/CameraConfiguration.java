/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author Coladi
 *
 */
public class CameraConfiguration extends DeviceConfiguration{
	private String jpegResolution;
	private String jpegQuality;
	private String videoColorBalance;
	private String videoBrightness;
	private String videoSharpness;
	private String videoHue;
	private String videoSaturation;
	private String videoContrast;
	private String videoAcFrequency;
	private String mpeg4Resolution;
	private String mpeg4QualityControl;
	private String mpeg4QualityLevel;
	private String mpeg4BitRate;
	private String mpeg4FrameRate;
	private String mpeg4BandWidth;
	private String pantiltControl;
	private String pantiltVsMotionArbitration;
	private String pantiltPatrolStyle;
	private String pantiltPanSpeed;
	private String pantiltTiltSpeed;
	private String controlPantilt;
	private String networkMode;
	private String adminUserName;
	private String adminPassword;
	private String systemNightVisionControl;
	private String systemPrivacyControl;
	private String systemPirControl;
	private String motionDetectionWindow;
	private String controlNightVision;
        private long presetvalue;
	//sumit start: Camera Orientation Feature
	private String cameraOrientation;
	//sumit end: Camera Orientation Feature
	
	public String getJpegResolution() {
		return jpegResolution;
	}
	public void setJpegResolution(String jpegResolution) {
		this.jpegResolution = jpegResolution;
	}
	public String getJpegQuality() {
		return jpegQuality;
	}
	public void setJpegQuality(String jpegQuality) {
		this.jpegQuality = jpegQuality;
	}
	public String getVideoColorBalance() {
		return videoColorBalance;
	}
	public void setVideoColorBalance(String videoColorBalance) {
		this.videoColorBalance = videoColorBalance;
	}
	public String getVideoBrightness() {
		return videoBrightness;
	}
	public void setVideoBrightness(String videoBrightness) {
		this.videoBrightness = videoBrightness;
	}
	public String getVideoSharpness() {
		return videoSharpness;
	}
	public void setVideoSharpness(String videoSharpness) {
		this.videoSharpness = videoSharpness;
	}
	public String getVideoHue() {
		return videoHue;
	}
	public void setVideoHue(String videoHue) {
		this.videoHue = videoHue;
	}
	public String getVideoSaturation() {
		return videoSaturation;
	}
	public void setVideoSaturation(String videoSaturation) {
		this.videoSaturation = videoSaturation;
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
	public String getMpeg4Resolution() {
		return mpeg4Resolution;
	}
	public void setMpeg4Resolution(String mpeg4Resolution) {
		this.mpeg4Resolution = mpeg4Resolution;
	}
	public String getMpeg4QualityControl() {
		return mpeg4QualityControl;
	}
	public void setMpeg4QualityControl(String mpeg4QualityControl) {
		this.mpeg4QualityControl = mpeg4QualityControl;
	}
	public String getMpeg4QualityLevel() {
		return mpeg4QualityLevel;
	}
	public void setMpeg4QualityLevel(String mpeg4QualityLevel) {
		this.mpeg4QualityLevel = mpeg4QualityLevel;
	}
	public String getMpeg4BitRate() {
		return mpeg4BitRate;
	}
	public void setMpeg4BitRate(String mpeg4BitRate) {
		this.mpeg4BitRate = mpeg4BitRate;
	}
	public String getMpeg4FrameRate() {
		return mpeg4FrameRate;
	}
	public void setMpeg4FrameRate(String mpeg4FrameRate) {
		this.mpeg4FrameRate = mpeg4FrameRate;
	}
	public String getMpeg4BandWidth() {
		return mpeg4BandWidth;
	}
	public void setMpeg4BandWidth(String mpeg4BandWidth) {
		this.mpeg4BandWidth = mpeg4BandWidth;
	}
	public String getPantiltControl() {
		return pantiltControl;
	}
	public void setPantiltControl(String pantiltControl) {
		this.pantiltControl = pantiltControl;
	}
	public String getPantiltVsMotionArbitration() {
		return pantiltVsMotionArbitration;
	}
	public void setPantiltVsMotionArbitration(String pantiltVsMotionArbitration) {
		this.pantiltVsMotionArbitration = pantiltVsMotionArbitration;
	}
	public String getPantiltPatrolStyle() {
		return pantiltPatrolStyle;
	}
	public void setPantiltPatrolStyle(String pantiltPatrolStyle) {
		this.pantiltPatrolStyle = pantiltPatrolStyle;
	}
	public String getPantiltPanSpeed() {
		return pantiltPanSpeed;
	}
	public void setPantiltPanSpeed(String pantiltPanSpeed) {
		this.pantiltPanSpeed = pantiltPanSpeed;
	}
	public String getPantiltTiltSpeed() {
		return pantiltTiltSpeed;
	}
	public void setPantiltTiltSpeed(String pantiltTiltSpeed) {
		this.pantiltTiltSpeed = pantiltTiltSpeed;
	}
	public String getControlPantilt() {
		return controlPantilt;
	}
	public void setControlPantilt(String controlPantilt) {
		this.controlPantilt = controlPantilt;
	}
	public String getNetworkMode() {
		return networkMode;
	}
	public void setNetworkMode(String networkMode) {
		this.networkMode = networkMode;
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
	public String getSystemNightVisionControl() {
		return systemNightVisionControl;
	}
	public void setSystemNightVisionControl(String systemNightVisionControl) {
		this.systemNightVisionControl = systemNightVisionControl;
	}
	public String getSystemPrivacyControl() {
		return systemPrivacyControl;
	}
	public void setSystemPrivacyControl(String systemPrivacyControl) {
		this.systemPrivacyControl = systemPrivacyControl;
	}
	public String getSystemPirControl() {
		return systemPirControl;
	}
	public void setSystemPirControl(String systemPirControl) {
		this.systemPirControl = systemPirControl;
	}
	public String getMotionDetectionWindow() {
		return motionDetectionWindow;
	}
	public void setMotionDetectionWindow(String motionDetectionWindow) {
		this.motionDetectionWindow = motionDetectionWindow;
	}
	public String getControlNightVision() {
		return controlNightVision;
	}
	public void setControlNightVision(String controlNightVision) {
		this.controlNightVision = controlNightVision;
	}
	public long getPresetvalue() {
		return presetvalue;
	}
	public void setPresetvalue(long presetvalue) {
		this.presetvalue = presetvalue;
	}
	
	//sumit start: Camera Orientation Feature
	public String getCameraOrientation() {
		return cameraOrientation;
	}
	public void setCameraOrientation(String cameraOrientation) {
		this.cameraOrientation = cameraOrientation;
	}
	//sumit end: Camera Orientation Feature
}
