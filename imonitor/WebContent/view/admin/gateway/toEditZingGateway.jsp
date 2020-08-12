<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
		<h3><i>Edit Gateway Details</i></h3>
<s:form theme="simple" action="saveEditedZingDetails.action" method="post" id="editgatewayform" cssClass="ajaxinlinepopupform">
<s:hidden name="zingGatewayList.id"></s:hidden>
	<table>
	<tr><td>Mac ID:</td><td><s:textfield name="zingGatewayList.macId"  label="MacId"  cssClass="" ></s:textfield> </td></tr>
	<tr><td>Description:</td><td><s:textfield name="zingGatewayList.description"  label="Description"  cssClass="" ></s:textfield> </td></tr>
	 <tr><td>Entry Date:</td><td><s:date  name="zingGatewayList.entryDate" format="%{dateFormat}" var="myFormat"/><s:textfield name="zingGatewayList.entryDate" label="" value="%{myFormat}" required="true" cssClass="datetime required" id="entryDate" readonly="true"></s:textfield></td></tr>
	<!-- <tr><td>Entry Date:</td><td><input type="date" name="zingGatewayList.entryDate"></td></tr> -->
	<tr><td>Serial Number:</td><td><s:textfield name="zingGatewayList.serialNumber"  label="Serial Number"  cssClass="" ></s:textfield> </td></tr>
	<tr><td>BatchNumber:</td><td><s:textfield name="zingGatewayList.batchNumber"  label="Batch Number"  cssClass="" ></s:textfield> </td></tr>
	<tr><td></td><td>&nbsp;</td></tr>
	<tr><td></td><td><s:submit cssClass="sumbit" value="Save"></s:submit></td></tr>	
	
	
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
		/* var cForm = $("#editgatewayform");
		Xpeditions.validateForm(cForm); */
	});
	</script>