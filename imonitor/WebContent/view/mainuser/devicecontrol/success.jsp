<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<script>
$("#formessage").html("Operation Success !!!");
$("#formessage").dialog('open');
deviceLocationOrDeviceTypeWise['<s:property value="currentParam"/>']['<s:property value="device.generatedDeviceId"/>'] = ['<s:property value="device.deviceId"/>','<s:property value="device.deviceType.category"/>','<s:property value="device.commandParam"/>','<s:property value="device.gateWay.macId"/>','<s:property value="device.generatedDeviceId"/>','<s:property value="nextParam"/>','<s:property value="#device.friendlyName"/>'];
deviceLocationOrDeviceTypeWise['<s:property value="nextParam"/>']['<s:property value="device.generatedDeviceId"/>'] = ['<s:property value="device.deviceId"/>','<s:property value="device.deviceType.category"/>','<s:property value="device.commandParam"/>','<s:property value="device.gateWay.macId"/>','<s:property value="device.generatedDeviceId"/>','<s:property value="currentParam"/>','<s:property value="#device.friendlyName"/>'];
var currentMenus = $("#devicemenu").find('li a');
currentMenus.each(function(index, cMenu){
	if($(cMenu).attr("generateddeviceid") == '<s:property value="device.generatedDeviceId"/>'){
		$(cMenu).attr("commandParam",'<s:property value="device.commandParam"/>');
	}
});
</script>