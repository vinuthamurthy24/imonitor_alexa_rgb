<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:actionmessage/>
<s:form action="addGateWayType.action" method="post" id="saveGateWayTypeForm">
	<s:select label="Select the make" name="gateWayType.make.id" list="makes" listKey="id" listValue="name" cssClass="required"></s:select>
	<s:textfield name="gateWayType.modelDetails" label="Model" required="true" cssClass="required"></s:textfield>
	<s:textarea name="gateWayType.techninalDescription" label="Tech Description" rows="4" cols="17"></s:textarea>
	<s:submit cssClass="sumbit" value="Save"></s:submit>
</s:form>
	<script>
		$(document).ready(function() {
			var cForm = $("#saveGateWayTypeForm");
			Xpeditions.validateForm(cForm);
		});
		</script>