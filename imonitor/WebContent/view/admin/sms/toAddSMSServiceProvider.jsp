<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>


<div style="color: blue"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form action="addsmsserviceprovider.action" method="post" id="ServiceProviderForm" cssClass="ajaxinlineform">
<s:select name="smsService.operatorcode"  label="OperatorCode" list="#{'efl':'efl','ims':'ims'}"></s:select>
<%-- <s:textfield name="smsService.operatorcode" label="OperatorCode" cssClass="required"></s:textfield> --%>
<s:textfield name="smsService.username" label="UserName" cssClass="required"></s:textfield>
<s:password name="smsService.password" label="Password" cssClass="required"></s:password>
<s:textfield name="smsService.optin" label="StartServiceOption" cssClass="required"></s:textfield>
<s:textfield name="smsService.optout" label="StopServiceOption" cssClass="required"></s:textfield>
<s:textfield name="smsService.subnumber" label="Subscription Number " cssClass="required mobilenumber"></s:textfield>

<s:submit value="Save" ></s:submit>
</s:form>

	<script>
		$(document).ready(function() {
			var cForm = $("#ServiceProviderForm");
			Xpeditions.validateForm(cForm);
		});
	</script>