<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%><div style="color: blue">
	<s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<h4>Add Gateway.</h4>
<s:form action="addGateWay.action" method="post" id="addgatewayform" cssClass="ajaxinlineform">
	<s:hidden name="gateWay.currentMode" value="0"></s:hidden>
	<s:hidden name="gateWay.delayHome" value="0"></s:hidden>
	<s:hidden name="gateWay.delayStay" value="0"></s:hidden>
	<s:hidden name="gateWay.delayAway" value="0"></s:hidden>
	<s:textfield name="gateWay.macId" label="Mac ID" cssClass="macid required editdisplayStar"></s:textfield>
	<s:textfield name="gateWay.localIp" label="Local IP"></s:textfield>
	<s:textfield name="gateWay.entryDate" label="Entry Date" required="true" cssClass="datetime required" id="entryDate" readonly="true"></s:textfield>
	<s:textarea name="gateWay.remarks" id="Details" cssClass="Details" label="Remarks" rows="4" cols="17"></s:textarea>
	<s:select label="Select Agent" name="gateWay.agent.id" list="agents" listKey="id" listValue="name" ></s:select>
	<s:select label="Status" name="gateWay.status.id" list="statuses" listKey="id" listValue="name" value="4"></s:select>
	<s:submit cssClass="sumbit" value="Save"></s:submit>
</s:form>
	<script>
	$(document).ready(function() {
		$("#entryDate").datepicker({showOn: 'button', buttonImage: '/imonitor/resources/images/calendar.gif', buttonImageOnly: true,dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'});
		var cForm = $("#addgatewayform");
		Xpeditions.validateForm(cForm);
	});
	</script>