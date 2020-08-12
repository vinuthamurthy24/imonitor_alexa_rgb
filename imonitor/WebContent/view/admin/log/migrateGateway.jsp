<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<h4>Migrate Gateway.</h4>
<s:form theme="simple" action="MigrateGateWay.action" method="post" id="editgatewayform" cssClass="ajaxinlinepopupform">
	<table>
	<tr><td>Mac ID:</td><td><s:textfield name="gateWay.macId"  label="Mac ID" required="true" cssClass="required macid editdisplayStar" ></s:textfield></td></tr>
	
	<tr><td>MigrationServer IP:</td><td><s:textfield name="ServerIp" label=""></s:textfield></td></tr>
	
	<tr><td>MigrationServer Port:</td><td><s:textfield name="Port" label="Receiver Port" cssClass="required alphanumeric editdisplayStaradmin" minvalue="1" maxvalue="65535"></s:textfield></td></tr>
	
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