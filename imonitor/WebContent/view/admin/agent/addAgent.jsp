<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: green;"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<h4>Add Agent</h4>
	<s:form action="addAgent" method="post" id="saveAgentForm" cssClass="ajaxinlineform">
	<s:textfield name="agent.ip" label="Domain Name" cssClass="required domainName editdisplayStaradmin"></s:textfield>
	<s:textfield name="agent.name" label="Agent Name" cssClass="required alphanumeric editdisplayStaradmin" maxlength="30"></s:textfield>
	<s:textfield name="agent.balancerPort" label="Balancer Port" cssClass="required alphanumeric editdisplayStaradmin" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.receiverPort" label="Receiver Port" cssClass="required alphanumeric editdisplayStaradmin" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.controllerReceiverPort" label="Controller To Receiver Port" cssClass="required alphanumeric editdisplayStaradmin" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.controllerBroadCasterPort" label="Controller To Broadcaster Port" cssClass="required alphanumeric editdisplayStaradmin" minvalue="1" maxvalue="65000"></s:textfield>
	<s:textfield name="agent.streamingIp" label="Streaming Ip" cssClass="required ipaddress editdisplayStaradmin"></s:textfield>
	<s:textfield name="agent.streamingPort" label="Streaming Port" cssClass="required alphanumeric editdisplayStaradmin" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.rtspPort" label="RTSP Port" cssClass="required alphanumeric editdisplayStaradmin" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.ftpIp" label="Ftp Ip" cssClass="required domainName editdisplayStaradmin"></s:textfield>
	<s:textfield name="agent.ftpPort" label="Ftp Port" cssClass="required alphanumeric editdisplayStaradmin" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.ftpNonSecurePort" label="Ftp Non Secure Port" cssClass="required alphanumeric editdisplayStaradmin" minvalue="0" maxvalue="65000"></s:textfield>
	<s:textfield name="agent.ftpUserName" label="Ftp UserName" cssClass="required editdisplayStaradmin"></s:textfield>
	<s:password name="agent.ftpPassword" label="Ftp Password" cssClass="required password editdisplayStaradmin"></s:password>
	<s:select label="Status" name="agent.status.id" list="statuses" listKey="id" listValue="name" cssClass="required"></s:select>
	<s:textfield name="agent.location" label="Location" cssClass="alphanumeric" maxlength="30"></s:textfield>
	<s:textfield name="agent.details" label="Details"></s:textfield>
	<s:submit value="Save"></s:submit>
</s:form>
<script>
	$(document).ready(function() {
		var cForm = $("#saveAgentForm");
		Xpeditions.validateForm(cForm);
	});
</script>