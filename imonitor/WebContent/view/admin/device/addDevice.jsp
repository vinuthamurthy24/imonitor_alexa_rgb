<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<s:form action="addDevice.action" method="post">
	<s:textfield name="device.deviceId" label="Real Device ID" readonly="true" id="deviceId"></s:textfield>
	<s:textfield name="device.name" label="Device name"></s:textfield>
	<s:select name="device.deviceType.id" label="Select Device Type" list="deviceTypes" listKey="id" listValue="name"></s:select>
	<s:textfield name="device.initialFirmware" label="Initial firmware"></s:textfield>
	<s:textfield name="device.latestFirmware" label="Latest firmware"></s:textfield>
	<s:select name="device.make.id" label="Select Make" list="makes" listKey="id" listValue="name"></s:select>
	<s:checkboxlist label="Commands" name="selectedCommands" list="commands" listKey="id" listValue="name"></s:checkboxlist>
	<s:submit value="Save"></s:submit>
</s:form>
<div id="showGateWayDetails">
</div>
