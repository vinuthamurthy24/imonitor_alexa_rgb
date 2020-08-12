<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<imonitor>
	<storage>
		<totalspace><s:property value="user.customer.paidStorageMB + user.customer.freeStorageMB"/></totalspace>
		<usedspace><s:property value="#totalUsedSpace"/></usedspace>
	</storage>	
	<s:iterator value="user.customer.gateWays" var="gateWay">
		<gateway>
			<id><s:property value="#gateWay.id"/></id>
			<macid><s:property value="#gateWay.macId"/></macid>
			<status><s:property value="#gateWay.status.name"/></status>
			<devices>
			<s:iterator value="#gateWay.devices" var="device">
				<device>
					<id><s:property value="#device.generatedDeviceId"/></id>
					<name><s:property value="#device.friendlyName"/></name>
					<type><s:property value="#device.deviceType.details"/></type>
					<location><s:property value="#device.location.name"/></location>
					<commandparam><s:property value="#device.commandParam"/></commandparam>
					<alertparam><s:property value="#device.alertParam"/></alertparam>
					<batterylevel><s:property value="#device.batteryStatus"/></batterylevel>
					<iconfile><s:property value="#device.deviceType.iconFile"/></iconfile>
				</device>
			</s:iterator>
			</devices>
		</gateway>
	</s:iterator>
</imonitor>