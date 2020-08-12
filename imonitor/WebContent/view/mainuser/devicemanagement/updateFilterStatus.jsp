<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<span style="display:none;">
<div style="color: red;" id="messageSection">
	<s:property value="#session.message" />
</div> </span>
<%ActionContext.getContext().getSession().remove("message"); %>
 
<s:form action="resetFilterStatus.action" theme="simple" method="post" cssClass="ajaxinlinepopupform" id="">
<s:hidden name="device.generatedDeviceId"></s:hidden>
<s:hidden name="device.gateWay.macId"></s:hidden>
<s:hidden name="device.id"></s:hidden>
<s:hidden name="device.deviceId"></s:hidden>
 	<table>
	<s:text name="">Update Filter Status ??</s:text>
	<tr><td></td><td style=" float: right;"><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="No" name="Cancel"/></td><td><s:submit theme="simple" key="Yes"></s:submit></td></tr>
	</table>
</s:form>



				