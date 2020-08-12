<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<h4>Edit Gateway.</h4>
<s:form theme="simple" action="editGateWay.action" method="post" id="editgatewayform" cssClass="ajaxinlinepopupform">
	<table>
	<tr><td>Mac ID:</td><td><s:textfield name="gateWay.macId"  label="Mac ID" required="true" cssClass="required macid editdisplayStar" ></s:textfield></td></tr>
	<tr><td>Gateway software Version:</td><td><s:property   value="gateWay.gateWayVersion" /></td></tr>
	<tr><td>Local IP:</td><td><s:textfield name="gateWay.localIp" label=""></s:textfield></td></tr>
	<tr><td>Entry Date:</td><td>	<s:date name="gateWay.entryDate" format="%{dateFormat}" var="myFormat"/><s:textfield name="gateWay.entryDate" label="" value="%{myFormat}" required="true" cssClass="datetime required" id="entryDate" readonly="true"></s:textfield></td></tr>
	<tr><td>Remarks:</td><td><s:textarea name="gateWay.remarks" id="Details" cssClass="Details" label="" rows="4" cols="17"></s:textarea></td></tr>
	<tr><td>Select Agent:</td><td><s:select label="" name="gateWay.agent.id" list="agents" listKey="id" listValue="name" ></s:select></td></tr>
	<tr><td>Status:</td><td><s:select label="" name="gateWay.status.id" list="statuses" listKey="id" listValue="name"></s:select></td></tr>
	<tr><td></td><td>&nbsp;</td></tr>
	<tr><td></td><td><s:submit cssClass="sumbit" value="Save"></s:submit></td></tr>	
	
	<s:hidden name="gateWay.id"></s:hidden>
	
	</table>
</s:form>
	<script>
	$(document).ready(function() {
		$("#entryDate").datepicker({
			showOn: 'button', 
			buttonImage: '/imonitor/resources/images/calendar.gif', 
			buttonImageOnly: true,
			beforeShow: function(input, inst) {
				$(".ui-datepicker").css('z-index', 5000);
			},
			onClose: function(dateText, inst) { 
				$(".ui-datepicker").css('z-index', 1);
			},
			dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'
			});
		var cForm = $("#editgatewayform");
		Xpeditions.validateForm(cForm);
	});
	</script>