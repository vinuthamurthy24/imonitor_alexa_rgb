<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>

<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>

<s:form action="saveCameraOrientationConfiguration.action" method="post" cssClass="ajaxinlinepopupform" id="cameraOrientationForm">
	<s:hidden name="device.generatedDeviceId"></s:hidden>
	<s:textfield readonly="true" name="device.friendlyName" key="setup.devices.name"></s:textfield>
	<s:textfield readonly="true" name="device.modelNumber" key="setup.devices.camera.model"></s:textfield>
	<table>
		<tr>
			<td><s:text name="setup.devices.camera.ptopt" /></td>
			<td><s:checkbox theme="simple" name="PTOption"/></td>
		</tr>
		<tr>
			<td style="color:#FF0000; font-family:'Times New Roman'; font-size:15px;"><s:text name="setup.devices.camera.title" /> </td>
		</tr>
		<tr>
			<td><s:text name="setup.devices.camera.flipv" /></td>
			<td><s:checkbox theme="simple" name="verticalFlip"/></td>
		</tr>
		<tr>
			<td><s:text name="setup.devices.camera.fliph" /></td>
			<td><s:checkbox theme="simple" name="horrizontalFlip"/></td>
		</tr>
	</table>
	
	<s:submit key="general.save"></s:submit>
</s:form>

<script>
	var cForm = $("#cameraOrientationForm");
	Xpeditions.validateForm(cForm);
</script>