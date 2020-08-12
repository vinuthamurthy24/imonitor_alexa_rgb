<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>


<div style="color: blue"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<s:form action="addSystemIntegrator.action" method="post" id="SystemIntegratorForm"  enctype="multipart/form-data" cssClass="ajaxinlinefileuploadformWithDialog">
	<s:hidden value="add" name="action"></s:hidden>
	<s:textfield name="systemIntegrator.systemIntegratorId" label="Integrator Id" cssClass="required alphanumeric "></s:textfield>
	<s:textfield name="systemIntegrator.name" label="Contact Person" cssClass="required alphanumeric "></s:textfield>
	<s:textfield name="systemIntegrator.address" label="Address" cssClass=" alphanumeric "></s:textfield>
	<s:textfield name="systemIntegrator.city" label="City" cssClass=" alphanumeric "></s:textfield>
	<s:textfield name="systemIntegrator.province" label="State" cssClass=" alphanumeric "></s:textfield>
	<s:textfield name="systemIntegrator.pincode" cssClass="number" label="PinCode" minvalue="100000" maxvalue="999999"></s:textfield>
	<s:textfield name="systemIntegrator.phoneNumber" label="Phone Number " cssClass="phonenumber"></s:textfield>
	<s:textfield name="systemIntegrator.email" label="Email ID" cssClass="required email "></s:textfield>
	<s:textfield name="systemIntegrator.mobile" label="Mobile Number" cssClass="required mobilenumber "></s:textfield>
	<s:select name="systemIntegrator.status.id" label="Status" list="statuses" listKey="id" listValue="name"></s:select>
	<s:select list="agents" listKey="id" listValue="name" label="Agent" name="systemIntegrator.agent.id"></s:select>
	<!-- Later Change to File to upload logo for SI -->
	<s:file name="fileUpload" cssClass="" label="Choose the Logo image file to upload"></s:file>
	<s:submit value="Save"></s:submit>
</s:form>

	<script>
		$(document).ready(function() {
			var cForm = $("#SystemIntegratorForm");
			Xpeditions.validateForm(cForm);
		});
	</script>