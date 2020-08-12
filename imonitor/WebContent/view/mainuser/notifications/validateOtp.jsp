<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: red" id="ErrorMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<s:form action="VerifyMobileNumber.action" method="post" id="validateMobileform" cssClass="ajaxinlinepopupform">
	<s:hidden name="systemNotificaion.id" id="genId"></s:hidden>
	<s:hidden name="systemNotificaion.OTP" id="genOtp"></s:hidden>
	<s:textfield name="systemNotificaion.name"  key="general.name" readonly="true"></s:textfield>
	<s:textfield name="systemNotificaion.SMS"  key="setup.not.SMS" readonly="true"></s:textfield>
	<s:textfield name="Otp" key="general.notification.otp" maxlength='4' ccsClass="required"></s:textfield>
	<s:submit value="Verify"></s:submit>
</s:form>
<%-- <button style="margin: 0px -89px 0px 375px;width: 130px;" onClick='regenerateOTP()' type='button' class='bt bbtn backupAlerts' title='click to Re-generate otp'><s:text name="Old Alerts" /></button> --%>
<script>

$(document).ready(function() {
	var cForm = $("#validateMobileform");
	Xpeditions.validateForm(cForm);
});

</script>