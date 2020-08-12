<%-- Copyright Ã‚Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ page language="java" %>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>




<br>
<div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
		<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<style>
.button {
    position: absolute;
    left: 45%;
    top: 85%;
}

.centerTable { margin: 0px auto; }
</style>

<s:form id="editModeForm" theme="simple" action="saveKeyfobeConfiguration.action" method="POST" cssClass="ajaxinlinepopupform" >


<s:hidden name="key1.action.actionName"> </s:hidden>
<s:hidden name="key1.device.id"> </s:hidden>
<s:hidden name="key1.keyCode"> </s:hidden>
<s:hidden name="key1.id"> </s:hidden>
<s:hidden name="key1.keyName"> </s:hidden>
<s:hidden name="key1.pressType"> </s:hidden>
<s:hidden name="key1.posindex"> </s:hidden>

<s:hidden name="key12.action.actionName"> </s:hidden>
<s:hidden name="key12.device.id"> </s:hidden>
<s:hidden name="key12.keyCode"> </s:hidden>
<s:hidden name="key12.id"> </s:hidden>
<s:hidden name="key12.keyName"> </s:hidden>
<s:hidden name="key12.pressType"> </s:hidden>
<s:hidden name="key12.posindex"> </s:hidden>

<s:hidden name="key2.action.actionName"> </s:hidden>
<s:hidden name="key2.device.id"> </s:hidden>
<s:hidden name="key2.keyCode"> </s:hidden>
<s:hidden name="key2.id"> </s:hidden>
<s:hidden name="key2.keyName"> </s:hidden>
<s:hidden name="key2.pressType"> </s:hidden>
<s:hidden name="key2.posindex"> </s:hidden>

<s:hidden name="key22.action.actionName"> </s:hidden>
<s:hidden name="key22.device.id"> </s:hidden>
<s:hidden name="key22.keyCode"> </s:hidden>
<s:hidden name="key22.id"> </s:hidden>
<s:hidden name="key22.keyName"> </s:hidden>
<s:hidden name="key22.pressType"> </s:hidden>
<s:hidden name="key22.posindex"> </s:hidden>

<s:hidden name="key3.action.actionName"> </s:hidden>
<s:hidden name="key3.device.id"> </s:hidden>
<s:hidden name="key3.keyCode"> </s:hidden>
<s:hidden name="key3.id"> </s:hidden>
<s:hidden name="key3.keyName"> </s:hidden>
<s:hidden name="key3.pressType"> </s:hidden>
<s:hidden name="key3.posindex"> </s:hidden>

<s:hidden name="key32.action.actionName"> </s:hidden>
<s:hidden name="key32.device.id"> </s:hidden>
<s:hidden name="key32.keyCode"> </s:hidden>
<s:hidden name="key32.id"> </s:hidden>
<s:hidden name="key32.keyName"> </s:hidden>
<s:hidden name="key32.pressType"> </s:hidden>
<s:hidden name="key32.posindex"> </s:hidden>

<s:hidden name="key4.action.actionName"> </s:hidden>
<s:hidden name="key4.device.id"> </s:hidden>
<s:hidden name="key4.keyCode"> </s:hidden>
<s:hidden name="key4.id"> </s:hidden>
<s:hidden name="key4.keyName"> </s:hidden>
<s:hidden name="key4.pressType"> </s:hidden>
<s:hidden name="key4.posindex"> </s:hidden>

<s:hidden name="key42.action.actionName"> </s:hidden>
<s:hidden name="key42.device.id"> </s:hidden>
<s:hidden name="key42.keyCode"> </s:hidden>
<s:hidden name="key42.id"> </s:hidden>
<s:hidden name="key42.keyName"> </s:hidden>
<s:hidden name="key42.pressType"> </s:hidden>
<s:hidden name="key42.posindex"> </s:hidden>

<s:hidden name="key5.action.actionName"> </s:hidden>
<s:hidden name="key5.device.id"> </s:hidden>
<s:hidden name="key5.keyCode"> </s:hidden>
<s:hidden name="key5.id"> </s:hidden>
<s:hidden name="key5.keyName"> </s:hidden>
<s:hidden name="key5.pressType"> </s:hidden>
<s:hidden name="key5.posindex"> </s:hidden>

<s:hidden name="key52.action.actionName"> </s:hidden>
<s:hidden name="key52.device.id"> </s:hidden>
<s:hidden name="key52.keyCode"> </s:hidden>
<s:hidden name="key52.id"> </s:hidden>
<s:hidden name="key52.keyName"> </s:hidden>
<s:hidden name="key52.pressType"> </s:hidden>
<s:hidden name="key52.posindex"> </s:hidden>

<s:hidden name="key6.action.actionName"> </s:hidden>
<s:hidden name="key6.device.id"> </s:hidden>
<s:hidden name="key6.keyCode"> </s:hidden>
<s:hidden name="key6.id"> </s:hidden>
<s:hidden name="key6.keyName"> </s:hidden>
<s:hidden name="key6.pressType"> </s:hidden>
<s:hidden name="key6.posindex"> </s:hidden>

<s:hidden name="key62.action.actionName"> </s:hidden>
<s:hidden name="key62.device.id"> </s:hidden>
<s:hidden name="key62.keyCode"> </s:hidden>
<s:hidden name="key62.id"> </s:hidden>
<s:hidden name="key62.keyName"> </s:hidden>
<s:hidden name="key62.pressType"> </s:hidden>
<s:hidden name="key62.posindex"> </s:hidden>




<table cellpadding="5" cellspacing="4" class="centerTable" >
<tbody>
<s:hidden name="device.generatedDeviceId"></s:hidden>

<tr >
<th Width="20px" >Key</th>
<th Width="120px">Single Click Function</th>
<th Width="120px">Press & hold Function</th>
</tr>
<tr>
<td Width="20px" ><s:text name="key1.keyName"></s:text></td>
<td Width="120px"><s:select name="key1.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
<td Width="120px"><s:select name="key12.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
</tr>
<tr>
<td Width="20px"><s:text name="key2.keyName"></s:text></td>
<td Width="120px"><s:select name="key2.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
<td Width="120px"><s:select name="key22.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
</tr>
<tr>
<td Width="20px"><s:text name="key3.keyName"></s:text></td>
<td Width="120px"><s:select name="key3.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
<td Width="120px"><s:select name="key32.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
</tr>
<tr>
<td Width="20px"><s:text name="key4.keyName"></s:text></td>
<td Width="120px"><s:select name="key4.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
<td Width="120px"><s:select name="key42.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
</tr>
<tr>
<td Width="20px"><s:text name="key5.keyName"></s:text></td>
<td Width="120px"><s:select name="key5.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
<td Width="120px"><s:select name="key52.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
</tr>
<tr>
<td Width="20px"><s:text name="key6.keyName"></s:text></td>
<td Width="120px"><s:select name="key6.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
<td Width="120px"><s:select name="key62.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
</tr>

</tbody>
</table>
<s:submit cssClass="button" key="general.save" onclick="javascript:handleSync()"/>
</s:form>

<s:else>
<p><s:text name="Scene Controller Error" /></p>
</s:else>

<script>
	var cForm = $("#editModeForm");
	Xpeditions.validateForm(cForm);
	
	$(document).ready(function(){
		
		 var rows = null;
		
		
		
	});
	
	
	
	</script>
