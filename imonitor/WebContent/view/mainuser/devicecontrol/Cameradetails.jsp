<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<device>
	<index><s:property value="device.id"/></index>
	<id><s:property value="device.generatedDeviceId"/></id>
	<name><s:property value="device.friendlyName"/></name>
	<modelnumber><s:property value="device.modelNumber"/></modelnumber>
	<controlnightvision><s:property value="device.deviceConfiguration.controlNightVision"/></controlnightvision>
	<macid><s:property value="device.gateWay.macId"/></macid>
</device>