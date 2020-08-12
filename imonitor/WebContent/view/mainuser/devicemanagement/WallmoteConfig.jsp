<%@ page language="java" %>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>

<br>
<div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
		<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<s:form id="editModeForm" theme="simple" action="saveWallmoteConfiguration.action" method="POST" cssClass="ajaxinlinepopupform" >


<s:hidden name="key1.action.actionName"> </s:hidden>
<s:hidden name="key1.device.id"> </s:hidden>
<s:hidden name="key1.keyCode"> </s:hidden>
<s:hidden name="key1.id"> </s:hidden>
<s:hidden name="key1.keyName"> </s:hidden>
<s:hidden name="key1.posindex"> </s:hidden>



<s:hidden name="key2.action.actionName"> </s:hidden>
<s:hidden name="key2.device.id"> </s:hidden>
<s:hidden name="key2.keyCode"> </s:hidden>
<s:hidden name="key2.id"> </s:hidden>
<s:hidden name="key2.keyName"> </s:hidden>
<s:hidden name="key2.posindex"> </s:hidden>


<s:hidden name="key3.action.actionName"> </s:hidden>
<s:hidden name="key3.device.id"> </s:hidden>
<s:hidden name="key3.keyCode"> </s:hidden>
<s:hidden name="key3.id"> </s:hidden>
<s:hidden name="key3.keyName"> </s:hidden>
<s:hidden name="key3.posindex"> </s:hidden>



<s:hidden name="key4.action.actionName"> </s:hidden>
<s:hidden name="key4.device.id"> </s:hidden>
<s:hidden name="key4.keyCode"> </s:hidden>
<s:hidden name="key4.id"> </s:hidden>
<s:hidden name="key4.keyName"> </s:hidden>
<s:hidden name="key4.posindex"> </s:hidden>




<table cellpadding="5" cellspacing="4" class="centerTable" >
<tbody>
<s:hidden name="device.generatedDeviceId"></s:hidden>

<tr >
<th Width="20px" >Keys</th>
<th Width="120px">Functions</th>

</tr>
<tr>
<td Width="20px" ><s:text name="key1.keyName"></s:text></td>
<td Width="120px"><s:select name="key1.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
</tr>
<tr>
<td Width="20px"><s:text name="key2.keyName"></s:text></td>
<td Width="120px"><s:select name="key2.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
</tr>
<tr>
<td Width="20px"><s:text name="key3.keyName"></s:text></td>
<td Width="120px"><s:select name="key3.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
</tr>
<tr>
<td Width="20px"><s:text name="key4.keyName"></s:text></td>
<td Width="120px"><s:select name="key4.action.id" list="actions" listKey="id" listValue="actionName" headerKey="0" headerValue="%{getText('setup.devices.action.select')}" ></s:select></td>
</tr>


</tbody>
</table>
<s:submit cssClass="button" key="general.save" onclick="javascript:handleSync()"/>
</s:form>
<script>
	var cForm = $("#editModeForm");
	Xpeditions.validateForm(cForm);
	
	$(document).ready(function(){
		
		
		
		
		
	});
	
	
	
	</script>