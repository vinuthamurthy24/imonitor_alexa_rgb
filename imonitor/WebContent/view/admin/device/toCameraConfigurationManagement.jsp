<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<s:form action="toUpdateCameraConfiguration.action" cssClass="ajaxinlineform">
	<s:textfield name="device.generatedDeviceId" label="Device Id"></s:textfield>
	<s:submit value="Change Device"></s:submit>
</s:form>

<s:form action="updateCameraConfiguration.action" cssClass="ajaxinlineform">
	<s:hidden name="device.id"></s:hidden>
	<s:textfield name="device.friendlyName" label="Friendly Name" readonly="true"></s:textfield>
	<s:textfield name="device.generatedDeviceId" label="Device Id" readonly="true"></s:textfield>
	<s:radio name="cameraConfiguration.jpegResolution" label="Jpeg Resolution" list="camaraConfParam.jpegResolutions" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.jpegQuality" label="Jpeg Quality" list="camaraConfParam.jpegQualitys" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.videoColorBalance" label="Jpeg Color Balance" list="camaraConfParam.videoColorBalances" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.videoBrightness" label="Jpeg Brightnes" list="camaraConfParam.videoBrightness" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.videoSharpness" label="Jpeg Sharpness" list="camaraConfParam.videoSharpness" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.videoHue" label="Jpeg Hue" list="camaraConfParam.videoHues" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.videoSaturation" label="Jpeg Saturation" list="camaraConfParam.videoSaturations" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.videoContrast" label="Jpeg Contrast" list="camaraConfParam.videoContrasts" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.videoAcFrequency" label="Jpeg Ac Frequency" list="camaraConfParam.videoAcFrequencys" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.mpeg4Resolution" label="Mpeg4 Resolution" list="camaraConfParam.mpeg4Resolutions" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.mpeg4QualityControl" label="Mpeg4 Quality Control" list="camaraConfParam.mpeg4QualityControls" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.mpeg4QualityLevel" label="Mpeg4 Quality Level" list="camaraConfParam.mpeg4QualityLevels" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.mpeg4BitRate" label="Mpeg4 Bit Rate (1-30 FPS)" list="camaraConfParam.mpeg4BitRates" listKey="key" listValue="value"></s:radio>
	<s:textfield name="cameraConfiguration.mpeg4FrameRate" label="mpeg4FrameRate"></s:textfield>
	<s:radio name="cameraConfiguration.mpeg4BandWidth" label="Mpeg4 Band Width" list="camaraConfParam.mpeg4BandWidths" listKey="key" listValue="value"></s:radio>
	<s:textfield name="cameraConfiguration.adminUserName" label="adminUserName"></s:textfield>
	<s:textfield name="cameraConfiguration.adminPassword" label="adminPassword"></s:textfield>
	<s:radio name="cameraConfiguration.networkMode" label="Network Mode" list="camaraConfParam.networkModes" listKey="key" listValue="value"></s:radio>
	<s:radio name="cameraConfiguration.systemNightVisionControl" label="System Night Vision Control" list="camaraConfParam.systemNightVisionControls" listKey="key" listValue="value"></s:radio>
<%--
	<s:textfield name="cameraConfiguration.pantiltControl" label="pantiltControl"></s:textfield>
	<s:textfield name="cameraConfiguration.pantiltVsMotionArbitration" label="pantiltVsMotionArbitration"></s:textfield>
	<s:textfield name="cameraConfiguration.pantiltPatrolStyle" label="pantiltPatrolStyle"></s:textfield>
	<s:textfield name="cameraConfiguration.pantiltPanSpeed" label="pantiltPanSpeed"></s:textfield>
	<s:textfield name="cameraConfiguration.pantiltTiltSpeed" label="pantiltTiltSpeed"></s:textfield>
	<s:textfield name="cameraConfiguration.controlPantilt" label="controlPantilt"></s:textfield>
	<s:textfield name="cameraConfiguration.systemNightVisionControl" label="systemNightVisionControl"></s:textfield>
	<s:textfield name="cameraConfiguration.systemPrivacyControl" label="systemPrivacyControl"></s:textfield>
	<s:textfield name="cameraConfiguration.systemPirControl" label="systemPirControl"></s:textfield>
	<s:textfield name="cameraConfiguration.motionDetectionWindow" label="motionDetectionWindow"></s:textfield>
	<s:textfield name="cameraConfiguration.controlNightVision" label="controlNightVision"></s:textfield>
 --%>	
	<s:submit></s:submit>
</s:form>
</body>
</html>