<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
<s:iterator value="devices" var="device" status="devStatus">
<Device>
<id><s:property value="#device.id"/></id>
<nodeId><s:property value="#device.deviceId"/></nodeId>
<macId><s:property value="#device.gateWay.macId"/></macId>
<friendlyName><s:property value="#device.friendlyName"/></friendlyName>
<name><s:property value="#device.deviceType.name"/></name>
<deviceIcon><s:property value="#device.icon.fileName"/></deviceIcon>
<deviceGenId><s:property value="#device.generatedDeviceId"/></deviceGenId>
</Device>
</s:iterator>
</imonitor>