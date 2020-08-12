<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>


<div style="color: blue"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form action="addZingGatewayEntry.action" method="post" id="ServiceProviderForm" cssClass="ajaxinlineform">


<s:textfield name="zingGatewayList.macId" label="Mac Id" cssClass="macid required"></s:textfield>
<s:textarea name="zingGatewayList.description" label="Description" cssClass="required"></s:textarea>
<s:textfield name="zingGatewayList.entryDate" label="Entry Date" required="true" cssClass="datetime required" id="entryDate" readonly="true"></s:textfield>
<s:textfield name="zingGatewayList.serialNumber" label="Serial number" cssClass="required editdisplayStar"></s:textfield>
<s:textfield name="zingGatewayList.batchNumber" label="Batch number" cssClass="required editdisplayStar"></s:textfield>

<s:submit value="Save" ></s:submit>
</s:form>

	<script>
		$(document).ready(function() {
			$("#entryDate").datepicker({showOn: 'button', buttonImage: '/imonitor/resources/images/calendar.gif', buttonImageOnly: true,dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'});
			var cForm = $("#ServiceProviderForm");
			Xpeditions.validateForm(cForm);
		});
	</script>