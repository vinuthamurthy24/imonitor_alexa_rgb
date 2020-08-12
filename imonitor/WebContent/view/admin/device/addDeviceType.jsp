<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<s:form action="addDeviceType.action" method="post" cssClass="ajaxinlineform">
	<s:textfield name="deviceType.name" label="Name"></s:textfield>
	<s:textfield name="deviceType.details" label="Details"></s:textfield>
	<s:submit value="Save"></s:submit>
</s:form>