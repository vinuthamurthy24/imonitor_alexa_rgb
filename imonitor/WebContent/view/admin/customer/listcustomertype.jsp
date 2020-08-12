<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<h4>Customer Type Details</h4>
<s:form action="customertypedetails.action" method="POST" id="customertypeform" cssClass="ajaxinlinepopupform">
<s:textfield name="customertype.name" label="Customer Type Name" cssClass="required alphanumeric editdisplayStar"></s:textfield>
<s:submit value="Save"></s:submit>
</s:form>
<script>
	$(document).ready(function() {
		var cForm = $("#customertypeform");
		Xpeditions.validateForm(cForm);
	});
</script>