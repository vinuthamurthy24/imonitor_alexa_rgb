<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<h4>Editing Agent.</h4>
<s:form action="editAgent" method="post" id="editAgentForm" cssClass="ajaxinlinepopupform">
	<s:textfield name="agent.ip" label="Domain Name" cssClass="required domainName editdisplayStar"></s:textfield>
	<s:textfield name="agent.name" label="Agent Name" cssClass="required alphanumeric editdisplayStar" maxlength="30"></s:textfield>
	<s:textfield name="agent.balancerPort" label="Balancer Port" cssClass="required number editdisplayStar" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.receiverPort" label="Receiver Port"  cssClass="required number editdisplayStar" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.controllerReceiverPort" label="Controller To Receiver Port"  cssClass="required number editdisplayStar" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.controllerBroadCasterPort" label="Controller To Broadcaster Port"  cssClass="required number editdisplayStar" minvalue="1" maxvalue="65000"></s:textfield>
	<s:textfield name="agent.streamingIp" label="Streaming Ip" cssClass="required ipaddress editdisplayStar"></s:textfield>
	<s:textfield name="agent.streamingPort" label="Streaming Port"  cssClass="required number editdisplayStar" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.rtspPort" label="RTSP Port"  cssClass="required number editdisplayStar" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.ftpIp" label="Ftp Ip" cssClass="required domainName editdisplayStar"></s:textfield>
	<s:textfield name="agent.ftpPort" label="Ftp Port"  cssClass="required number editdisplayStar" minvalue="1" maxvalue="65535"></s:textfield>
	<s:textfield name="agent.ftpNonSecurePort" label="Ftp Non Secure Port" cssClass="required number editdisplayStar" minvalue="0" maxvalue="65000"></s:textfield>
	<s:textfield name="agent.ftpUserName" label="Ftp UserName" cssClass="required editdisplayStar"></s:textfield>
	<s:textfield name="agent.ftpPassword" label="Ftp Password" cssClass="required password editdisplayStar"></s:textfield>
	<s:select label="Status" name="agent.status.id" list="statuses" listKey="id" listValue="name" cssClass="required editdisplayStar"></s:select>
	<s:textfield name="agent.location" label="Location" cssClass="alphanumeric" maxlength="30"></s:textfield>
	<s:textfield name="agent.details" label="Details"></s:textfield>
	<s:hidden name="agent.id"></s:hidden>
	<s:submit value="Save"></s:submit>
</s:form>
<script>
	$(document).ready(function() {
		var cForm = $("#editAgentForm");
		Xpeditions.validateForm(cForm);
	});
</script>