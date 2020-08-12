/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Coladi
 *
 */
public class CameraConfParam {

//	Holding configuration values.
	private List<KeyValuePair> jpegResolutions;
	private List<KeyValuePair> jpegQualitys;
	private List<KeyValuePair> videoColorBalances;
	private List<KeyValuePair> videoBrightness;
	private List<KeyValuePair> videoSharpness;
	private List<KeyValuePair> videoHues;
	private List<KeyValuePair> videoSaturations;
	private List<KeyValuePair> videoContrasts;
	private List<KeyValuePair> videoAcFrequencys;
	private List<KeyValuePair> mpeg4Resolutions;
	private List<KeyValuePair> mpeg4QualityControls;
	private List<KeyValuePair> mpeg4QualityLevels;
	private List<KeyValuePair> mpeg4BitRates;
	private List<KeyValuePair> mpeg4FrameRate;
	private List<KeyValuePair> mpeg4BandWidths;
	private List<KeyValuePair> pantiltControls;
	private List<KeyValuePair> pantiltVsMotionArbitrations;
	private List<KeyValuePair> pantiltPatrolStyles;
	private List<KeyValuePair> pantiltPanSpeeds;
	private List<KeyValuePair> pantiltTiltSpeeds;
	private List<KeyValuePair> networkModes;
	private List<KeyValuePair> systemNightVisionControls;
	private List<KeyValuePair> systemPrivacyControls;
	private List<KeyValuePair> systemPirControls;
	private List<KeyValuePair> motionDetectionWindows;
	private List<KeyValuePair> controlNightVision;
	
	public CameraConfParam() {
		this.jpegResolutions = new ArrayList<KeyValuePair>();
		this.jpegResolutions.add(new KeyValuePair("1", "160x120"));
		this.jpegResolutions.add(new KeyValuePair("2", "320x240"));
		this.jpegResolutions.add(new KeyValuePair("3", "640x480"));
		
		this.jpegQualitys = new ArrayList<KeyValuePair>();
		this.jpegQualitys.add(new KeyValuePair("1","Very High"));
		this.jpegQualitys.add(new KeyValuePair("2","High"));
		this.jpegQualitys.add(new KeyValuePair("3","Normal"));
		this.jpegQualitys.add(new KeyValuePair("4","Low"));
		this.jpegQualitys.add(new KeyValuePair("5","Very Low"));
		
		this.videoColorBalances = new ArrayList<KeyValuePair>();
		this.videoColorBalances.add(new KeyValuePair("0", "Auto"));
		this.videoColorBalances.add(new KeyValuePair("1", "Indoor"));
		this.videoColorBalances.add(new KeyValuePair("2", "White Light"));
		this.videoColorBalances.add(new KeyValuePair("3", "Yellow Light"));
		this.videoColorBalances.add(new KeyValuePair("4", "Outdoor"));
		this.videoColorBalances.add(new KeyValuePair("5", "Black & White"));
		
		this.videoBrightness = new ArrayList<KeyValuePair>();
		this.videoBrightness.add(new KeyValuePair("1", "1"));
		this.videoBrightness.add(new KeyValuePair("2", "2"));
		this.videoBrightness.add(new KeyValuePair("3", "3"));
		this.videoBrightness.add(new KeyValuePair("4", "4"));
		this.videoBrightness.add(new KeyValuePair("5", "5"));
		this.videoBrightness.add(new KeyValuePair("6", "6"));
		this.videoBrightness.add(new KeyValuePair("7", "7"));
		
		this.videoSharpness = new ArrayList<KeyValuePair>();
		this.videoSharpness.add(new KeyValuePair("1", "1"));
		this.videoSharpness.add(new KeyValuePair("2", "2"));
		this.videoSharpness.add(new KeyValuePair("3", "3"));
		this.videoSharpness.add(new KeyValuePair("4", "4"));
		this.videoSharpness.add(new KeyValuePair("5", "5"));
		this.videoSharpness.add(new KeyValuePair("6", "6"));
		this.videoSharpness.add(new KeyValuePair("7", "7"));
		
		this.videoHues = new ArrayList<KeyValuePair>();
		this.videoHues.add(new KeyValuePair("1", "1"));
		this.videoHues.add(new KeyValuePair("2", "2"));
		this.videoHues.add(new KeyValuePair("3", "3"));
		this.videoHues.add(new KeyValuePair("4", "4"));
		this.videoHues.add(new KeyValuePair("5", "5"));
		this.videoHues.add(new KeyValuePair("6", "6"));
		this.videoHues.add(new KeyValuePair("7", "7"));
		
		this.videoSaturations = new ArrayList<KeyValuePair>();
		this.videoSaturations.add(new KeyValuePair("1", "1"));
		this.videoSaturations.add(new KeyValuePair("2", "2"));
		this.videoSaturations.add(new KeyValuePair("3", "3"));
		this.videoSaturations.add(new KeyValuePair("4", "4"));
		this.videoSaturations.add(new KeyValuePair("5", "5"));
		this.videoSaturations.add(new KeyValuePair("6", "6"));
		this.videoSaturations.add(new KeyValuePair("7", "7"));
		
		this.videoContrasts = new ArrayList<KeyValuePair>();
		this.videoContrasts.add(new KeyValuePair("1", "1"));
		this.videoContrasts.add(new KeyValuePair("2", "2"));
		this.videoContrasts.add(new KeyValuePair("3", "3"));
		this.videoContrasts.add(new KeyValuePair("4", "4"));
		this.videoContrasts.add(new KeyValuePair("5", "5"));
		this.videoContrasts.add(new KeyValuePair("6", "6"));
		this.videoContrasts.add(new KeyValuePair("7", "7"));
		
		this.videoAcFrequencys = new ArrayList<KeyValuePair>();
		this.videoAcFrequencys.add(new KeyValuePair("50", "50"));
		this.videoAcFrequencys.add(new KeyValuePair("60", "60"));
		
		this.mpeg4Resolutions = new ArrayList<KeyValuePair>();
		this.mpeg4Resolutions.add(new KeyValuePair("1", "160x120"));
		this.mpeg4Resolutions.add(new KeyValuePair("2", "320x240"));
		this.mpeg4Resolutions.add(new KeyValuePair("3", "640x480"));
		
		this.mpeg4QualityControls = new ArrayList<KeyValuePair>();
		this.mpeg4QualityControls.add(new KeyValuePair("0", "Fix bit rate"));
		this.mpeg4QualityControls.add(new KeyValuePair("1", "Fix quality"));
		
		this.mpeg4QualityLevels = new ArrayList<KeyValuePair>();
		this.mpeg4QualityLevels.add(new KeyValuePair("5","Very High"));
		this.mpeg4QualityLevels.add(new KeyValuePair("4","High"));
		this.mpeg4QualityLevels.add(new KeyValuePair("3","Normal"));
		this.mpeg4QualityLevels.add(new KeyValuePair("2","Low"));
		this.mpeg4QualityLevels.add(new KeyValuePair("1","Very Low"));
		
		this.mpeg4BitRates = new ArrayList<KeyValuePair>();
		this.mpeg4BitRates.add(new KeyValuePair("0", "32K"));
		this.mpeg4BitRates.add(new KeyValuePair("1", "64K"));
		this.mpeg4BitRates.add(new KeyValuePair("2", "96K"));
		this.mpeg4BitRates.add(new KeyValuePair("3", "128K"));
		this.mpeg4BitRates.add(new KeyValuePair("4", "256K"));
		this.mpeg4BitRates.add(new KeyValuePair("5", "384K"));
		this.mpeg4BitRates.add(new KeyValuePair("6", "512K"));
		this.mpeg4BitRates.add(new KeyValuePair("7", "768K"));
		this.mpeg4BitRates.add(new KeyValuePair("8", "1024K"));
		this.mpeg4BitRates.add(new KeyValuePair("9", "1280K"));
		this.mpeg4BitRates.add(new KeyValuePair("10", "2048"));
		
		
		this.mpeg4FrameRate = new ArrayList<KeyValuePair>();
		
		this.mpeg4BandWidths = new ArrayList<KeyValuePair>();
		this.mpeg4BandWidths.add(new KeyValuePair("0", "Auto"));
		this.mpeg4BandWidths.add(new KeyValuePair("1", "64K"));
		this.mpeg4BandWidths.add(new KeyValuePair("2", "128K"));
		this.mpeg4BandWidths.add(new KeyValuePair("3", "256K"));
		this.mpeg4BandWidths.add(new KeyValuePair("4", "512K"));
		this.mpeg4BandWidths.add(new KeyValuePair("5", "768K"));
		this.mpeg4BandWidths.add(new KeyValuePair("6", "1024K"));
		this.mpeg4BandWidths.add(new KeyValuePair("7", "1.5M"));
		this.mpeg4BandWidths.add(new KeyValuePair("8", "2M"));
		
		this.pantiltControls = new ArrayList<KeyValuePair>();
		this.pantiltVsMotionArbitrations = new ArrayList<KeyValuePair>();
		this.pantiltPatrolStyles = new ArrayList<KeyValuePair>();
		this.pantiltPanSpeeds = new ArrayList<KeyValuePair>();
		this.pantiltTiltSpeeds = new ArrayList<KeyValuePair>();
		
		this.networkModes = new ArrayList<KeyValuePair>();
		this.networkModes.add(new KeyValuePair("0", "Fixed IP address"));
		this.networkModes.add(new KeyValuePair("1", "DHCP"));
		
		this.systemNightVisionControls = new ArrayList<KeyValuePair>();
		this.systemNightVisionControls.add(new KeyValuePair("0", "Auto"));
		this.systemNightVisionControls.add(new KeyValuePair("1", "Default ON"));
		this.systemNightVisionControls.add(new KeyValuePair("2", "Default OFF"));
		
		this.systemPrivacyControls = new ArrayList<KeyValuePair>();
		this.systemPirControls = new ArrayList<KeyValuePair>();
		this.motionDetectionWindows = new ArrayList<KeyValuePair>();
		this.controlNightVision = new ArrayList<KeyValuePair>();
	}

	public List<KeyValuePair> getJpegResolutions() {
		return jpegResolutions;
	}

	public void setJpegResolutions(List<KeyValuePair> jpegResolutions) {
		this.jpegResolutions = jpegResolutions;
	}

	public List<KeyValuePair> getJpegQualitys() {
		return jpegQualitys;
	}

	public void setJpegQualitys(List<KeyValuePair> jpegQualitys) {
		this.jpegQualitys = jpegQualitys;
	}

	public List<KeyValuePair> getVideoColorBalances() {
		return videoColorBalances;
	}

	public void setVideoColorBalances(List<KeyValuePair> videoColorBalances) {
		this.videoColorBalances = videoColorBalances;
	}

	public List<KeyValuePair> getVideoBrightness() {
		return videoBrightness;
	}

	public void setVideoBrightness(List<KeyValuePair> videoBrightness) {
		this.videoBrightness = videoBrightness;
	}

	public List<KeyValuePair> getVideoSharpness() {
		return videoSharpness;
	}

	public void setVideoSharpness(List<KeyValuePair> videoSharpness) {
		this.videoSharpness = videoSharpness;
	}

	public List<KeyValuePair> getVideoHues() {
		return videoHues;
	}

	public void setVideoHues(List<KeyValuePair> videoHues) {
		this.videoHues = videoHues;
	}

	public List<KeyValuePair> getVideoSaturations() {
		return videoSaturations;
	}

	public void setVideoSaturations(List<KeyValuePair> videoSaturations) {
		this.videoSaturations = videoSaturations;
	}

	public List<KeyValuePair> getVideoContrasts() {
		return videoContrasts;
	}

	public void setVideoContrasts(List<KeyValuePair> videoContrasts) {
		this.videoContrasts = videoContrasts;
	}

	public List<KeyValuePair> getVideoAcFrequencys() {
		return videoAcFrequencys;
	}

	public void setVideoAcFrequencys(List<KeyValuePair> videoAcFrequencys) {
		this.videoAcFrequencys = videoAcFrequencys;
	}

	public List<KeyValuePair> getMpeg4Resolutions() {
		return mpeg4Resolutions;
	}

	public void setMpeg4Resolutions(List<KeyValuePair> mpeg4Resolutions) {
		this.mpeg4Resolutions = mpeg4Resolutions;
	}

	public List<KeyValuePair> getMpeg4QualityControls() {
		return mpeg4QualityControls;
	}

	public void setMpeg4QualityControls(List<KeyValuePair> mpeg4QualityControls) {
		this.mpeg4QualityControls = mpeg4QualityControls;
	}

	public List<KeyValuePair> getMpeg4QualityLevels() {
		return mpeg4QualityLevels;
	}

	public void setMpeg4QualityLevels(List<KeyValuePair> mpeg4QualityLevels) {
		this.mpeg4QualityLevels = mpeg4QualityLevels;
	}

	public List<KeyValuePair> getMpeg4BitRates() {
		return mpeg4BitRates;
	}

	public void setMpeg4BitRates(List<KeyValuePair> mpeg4BitRates) {
		this.mpeg4BitRates = mpeg4BitRates;
	}

	public List<KeyValuePair> getMpeg4FrameRate() {
		return mpeg4FrameRate;
	}

	public void setMpeg4FrameRate(List<KeyValuePair> mpeg4FrameRate) {
		this.mpeg4FrameRate = mpeg4FrameRate;
	}

	public List<KeyValuePair> getMpeg4BandWidths() {
		return mpeg4BandWidths;
	}

	public void setMpeg4BandWidths(List<KeyValuePair> mpeg4BandWidths) {
		this.mpeg4BandWidths = mpeg4BandWidths;
	}

	public List<KeyValuePair> getPantiltControls() {
		return pantiltControls;
	}

	public void setPantiltControls(List<KeyValuePair> pantiltControls) {
		this.pantiltControls = pantiltControls;
	}

	public List<KeyValuePair> getPantiltVsMotionArbitrations() {
		return pantiltVsMotionArbitrations;
	}

	public void setPantiltVsMotionArbitrations(
			List<KeyValuePair> pantiltVsMotionArbitrations) {
		this.pantiltVsMotionArbitrations = pantiltVsMotionArbitrations;
	}

	public List<KeyValuePair> getPantiltPatrolStyles() {
		return pantiltPatrolStyles;
	}

	public void setPantiltPatrolStyles(List<KeyValuePair> pantiltPatrolStyles) {
		this.pantiltPatrolStyles = pantiltPatrolStyles;
	}

	public List<KeyValuePair> getPantiltPanSpeeds() {
		return pantiltPanSpeeds;
	}

	public void setPantiltPanSpeeds(List<KeyValuePair> pantiltPanSpeeds) {
		this.pantiltPanSpeeds = pantiltPanSpeeds;
	}

	public List<KeyValuePair> getPantiltTiltSpeeds() {
		return pantiltTiltSpeeds;
	}

	public void setPantiltTiltSpeeds(List<KeyValuePair> pantiltTiltSpeeds) {
		this.pantiltTiltSpeeds = pantiltTiltSpeeds;
	}

	public List<KeyValuePair> getNetworkModes() {
		return networkModes;
	}

	public void setNetworkModes(List<KeyValuePair> networkModes) {
		this.networkModes = networkModes;
	}

	public List<KeyValuePair> getSystemNightVisionControls() {
		return systemNightVisionControls;
	}

	public void setSystemNightVisionControls(
			List<KeyValuePair> systemNightVisionControls) {
		this.systemNightVisionControls = systemNightVisionControls;
	}

	public List<KeyValuePair> getSystemPrivacyControls() {
		return systemPrivacyControls;
	}

	public void setSystemPrivacyControls(List<KeyValuePair> systemPrivacyControls) {
		this.systemPrivacyControls = systemPrivacyControls;
	}

	public List<KeyValuePair> getSystemPirControls() {
		return systemPirControls;
	}

	public void setSystemPirControls(List<KeyValuePair> systemPirControls) {
		this.systemPirControls = systemPirControls;
	}

	public List<KeyValuePair> getMotionDetectionWindows() {
		return motionDetectionWindows;
	}

	public void setMotionDetectionWindows(List<KeyValuePair> motionDetectionWindows) {
		this.motionDetectionWindows = motionDetectionWindows;
	}

	public List<KeyValuePair> getControlNightVision() {
		return controlNightVision;
	}

	public void setControlNightVision(List<KeyValuePair> controlNightVision) {
		this.controlNightVision = controlNightVision;
	}
}
