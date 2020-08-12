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



 <s:form id="editModeForm" theme="simple" action="saveVirtualSwitchConfiguration.action" method="POST" cssClass="ajaxinlinepopupform" >


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

<table cellpadding="5" cellspacing="4" class="centerTable" >
<tbody>
<s:hidden name="device.generatedDeviceId"></s:hidden>

<tr >
<th Width="20px" >Key</th>
<th Width="120px">On</th>
<th Width="120px">OFF</th>
</tr>
<tr>
<td Width="20px" ><s:text name="key1.keyName"></s:text></td>
<td Width="120px"><s:select name="key1.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
<td Width="120px"><s:select name="key12.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
</tr>
</tbody>
</table>
<s:submit cssClass="button" key="general.save" onclick="javascript:handleSync()"/>
</s:form>

<s:else>
<p><s:text name="Virtual Switch Error" /></p>
</s:else> 

<%-- <script>
	var cForm = $("#editModeForm");
	Xpeditions.validateForm(cForm);
	
	$(document).ready(function(){
		 var rows = null;
	});
	
	
	
	</script> --%>
