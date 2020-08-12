<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
<h4>Select ZwSwitch Power Input Type</h4>
<s:form action="ConfigureZwswitch.action" theme="simple" method="post" cssClass="ajaxinlinepopupform" id="configrezwswitch">
 <s:hidden name="device.id" ></s:hidden>
 <s:hidden name="powertype" ></s:hidden>
<br>
<s:radio name="powertype" list="#{'MainPower':'MainPower'}"></s:radio>
<s:radio name="powertype" list="#{'DGPower':'DGPower'}"></s:radio>
<br>
<br>
<s:submit value="Save"></s:submit>
</s:form>

<script>	
	$(document).ready(function() {
		var cForm = $("#configrezwswitch");
		Xpeditions.validateForm(cForm);
	});	
</script>