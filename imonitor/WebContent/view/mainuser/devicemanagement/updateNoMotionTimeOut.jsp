<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>

<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>


<s:form action="updateNoMotionTimeOut.action" method="post" cssClass="ajaxinlinepopupform" id="updateNoMotionTimeOut">
	<s:hidden name="device.gateWay.macId"></s:hidden>
	<s:hidden name="device.generatedDeviceId"></s:hidden>
	<table><tr>
	<s:textfield readonly="true" cssclass="required" name="device.friendlyName" key="setup.devices.pir.name"></s:textfield></tr>
	<tr>
       <s:textfield  id="timeout" name="device.modelNumber" minvalue="5" maxvalue="180" key="setup.devices.pir.timeout" Class="required number editdisplayStar" maxlength="3"></s:textfield>
       </tr>
	
	</table>
	<s:submit key="general.save"></s:submit>
</s:form>
<script>
    
	var cForm = $("#updateNoMotionTimeOut");
	Xpeditions.validateForm(cForm);
</script>