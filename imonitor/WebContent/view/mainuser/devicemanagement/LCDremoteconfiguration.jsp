<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ page language="java" %>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>




<br>
<div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
		<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<s:if test="scenarios.size >0">
<s:form id="editModeForm" theme="simple" action="saveLCDremoteConfiguration.action" method="POST" cssClass="ajaxinlinepopupform" >
	
<table>
<thead>
<tr>
<th Width="20px">key</th>
<th Width="120px">Associated Scenario To Execute</th>
</tr>
</thead>
<tbody>
<s:hidden name="device.generatedDeviceId"></s:hidden>
<tr><tr><td><s:text name='setup.devices.LCD.f1' /></td><td><s:select name="LCDRemoteConfiguration.f1" list="scenarios" listKey="id" listValue="name" headerKey="0" headerValue="%{getText('setup.devices.LCD.select')}" cssClass="required editdisplayStar"></s:select></td></tr>
<tr><tr><td><s:text name='setup.devices.LCD.f2' /></td><td><s:select name="LCDRemoteConfiguration.f2" list="scenarios" listKey="id" listValue="name" headerKey="0" headerValue="%{getText('setup.devices.LCD.select')}" cssClass="required editdisplayStar"></s:select></td></tr>
<tr><tr><td><s:text name='setup.devices.LCD.f3' /></td><td><s:select name="LCDRemoteConfiguration.f3" list="scenarios" listKey="id" listValue="name" headerKey="0" headerValue="%{getText('setup.devices.LCD.select')}" cssClass="required editdisplayStar"></s:select></td></tr>
</tbody>
</table>
<s:submit key="general.save" onclick="javascript:handleSync()"/>
</s:form>
</s:if>
<s:else>
<p><s:text name="setup.scenario.LCD.error" /></p>
</s:else>
<script>
	var cForm = $("#editModeForm");
	Xpeditions.validateForm(cForm);
	</script>
