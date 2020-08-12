<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: blue"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<h4>Update Firmware</h4>
<s:form action="updateFirmWare" method="post" id="firmwaresaveform" cssClass="ajaxinlineform">
	<s:textfield name="gateWay.macId" label="Gateway Mac Id" cssClass="required macid editdisplayStar"></s:textfield>
	<s:select name="firmWare.id" list="firmWares" listKey="id" listValue="version" label="Select Firmware"></s:select>
	<s:submit value="Save" ></s:submit>
</s:form>
<script>
		$(document).ready(function() {
			var cForm = $("#firmwaresaveform");
			Xpeditions.validateForm(cForm);
		});
	</script>
