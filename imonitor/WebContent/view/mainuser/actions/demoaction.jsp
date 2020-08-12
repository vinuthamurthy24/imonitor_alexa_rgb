<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: red" id="ErrorMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form action="addNotification.action" method="post" id="" cssClass="ajaxinlinepopupform">
<table>
<tbody>
<tr>
<th Width="120px">Action Name</th>
<th Width="120px">DeviceId/Scenario/Alert</th>
<th Width="120px">Parameter</th>
</tr>
<tr>
<td Width="120px"><s:textfield key = "" name = "action1" /></td>
<td Width="120px"><s:textfield key = "" name = "device1" /></td>
<td Width="120px"><s:textfield key = "" name = "para1" /></td>
</tr>
<tr>
<td Width="120px"><s:textfield key = "" name = "action2" /></td>
<td Width="120px"><s:textfield key = "" name = "device2" /></td>
<td Width="120px"><s:textfield key = "" name = "para2" /></td>
</tr>
</tbody>
</table>
	<s:submit value="Save"></s:submit>
</s:form>
<script>
</script>	