<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<s:form action="addCommand" method="post" cssClass="ajaxinlineform" id="saveCommandForm">
	<s:textfield name="command.name" label="Name" cssClass="required"></s:textfield>
	<s:textfield name="command.command" label="Command" cssClass="required"></s:textfield>
	<s:textfield name="command.description" label="Description"></s:textfield>
	<s:submit value="Save"></s:submit>
</s:form>
	<script>
$(document).ready(function() {
	var cForm = $("#saveCommandForm");
	Xpeditions.validateForm(cForm);
});
</script>
