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
<s:form id="editModeForm" theme="simple" action="saveSceneControlConfiguration.action" method="POST" cssClass="ajaxinlinepopupform" >
	
<table>
<thead>
</thead>
<tbody>
<s:hidden name="device.generatedDeviceId"></s:hidden>
<s:text name="SceneController" />
<tr>
<th Width="20px">Key</th>
<th Width="120px">Single Click Function</th>
<th Width="120px">Double Click Function</th>
</tr>
<tr>
<td Width="20px">1.</td>
<td Width="120px"><s:select name="sceneControllerConfig.key1" list="scenarios" listKey="id" listValue="name" headerKey="0" headerValue="%{getText('setup.devices.LCD.select')}" cssClass="required editdisplayStar"></s:select></td>
<td Width="120px"><s:select name="sceneControllerConfig.key1D" list="scenarios" listKey="id" listValue="name" headerKey="0" headerValue="%{getText('setup.devices.LCD.select')}" cssClass="required editdisplayStar"></s:select></td>
</tr>
<tr>
<td Width="20px">2.</td>
<td Width="120px"><s:select name="sceneControllerConfig.key2" list="scenarios" listKey="id" listValue="name" headerKey="0" headerValue="%{getText('setup.devices.LCD.select')}" cssClass="required editdisplayStar"></s:select></td>
<td Width="120px"><s:select name="sceneControllerConfig.key2D" list="scenarios" listKey="id" listValue="name" headerKey="0" headerValue="%{getText('setup.devices.LCD.select')}" cssClass="required editdisplayStar"></s:select></td>
</tr>
<tr>
<td Width="20px">3.</td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
</tr>
<tr>
<td Width="20px">4.</td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
</tr>
<tr>
<td Width="20px">5.</td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
</tr>
<tr>
<td Width="20px">6.</td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
</tr>
<tr>
<td Width="20px">7.</td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
</tr>
<tr>
<td Width="20px">8.</td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
<td Width="120px"><s:select headerKey="-1" headerValue="Select Scenario/Switch" list="#{'01':'Jan', '02':'Feb'}" name="yourSearchEngine" /></td>
</tr>
</tbody>
</table>
<s:submit key="general.save" onclick="javascript:handleSync()"/>
</s:form>
</s:if>
<s:else>
<p><s:text name="Scene Controller Error" /></p>
</s:else>

<script>
	var cForm = $("#editModeForm");
	Xpeditions.validateForm(cForm);
	</script>
