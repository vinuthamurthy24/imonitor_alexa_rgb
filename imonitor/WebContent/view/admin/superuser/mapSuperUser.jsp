<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%><h4>Add SuperUser to Customer</h4>
<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form action="saveSuperUserToCustomer.action" method="POST" id="editcustomerform" cssClass="ajaxinlinepopupform">
		<s:hidden name="customer.id"></s:hidden>
	<s:select label="SuperUserName" name="customerAndSuperUser.superUser.id" list="adminSuperUsers" listKey="id" listValue="superUserId"></s:select>
	<s:submit value="Save"></s:submit>
</s:form>
<script>
	$(document).ready(function() {
		
		var cForm = $("#editcustomerform");
		Xpeditions.validateForm(cForm);
	});
</script>