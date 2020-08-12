<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
	<storage>
		<UN><s:property value="user.userId"/></UN>
	    <ILT><s:date name="user.lastLoginTime" format="dd/MM/yy 'at' hh:mm a"/></ILT>
	    <TC><s:property value="targetcost.targetCost"/></TC>
	    <UPU><s:property value="uptodateusage"/></UPU>
	    <UPCost><s:property value="uptodatecost"/></UPCost>
	    <UPTU><s:property value="uptotimeusage"/></UPTU>
	    <UPTTime><s:date name="uptotime" format="dd/MM/yy 'at' hh:mm a"/></UPTTime>
	    <UPTCost><s:property value="uptotimecost"/></UPTCost>
	    <LMU><s:property value="lastMonthUsage"/></LMU>
	    <TOW><s:property value="totalwattage"/></TOW>
	  	<locale> <s:property value="locale"/></locale>
	</storage>
	<watroom>
	<s:iterator value="wattageroom" var="object">
	<watdetails>
	 	<value><s:property value="#object[0]"/></value>
	 	<locname><s:property value="#object[1]"/></locname>
	 </watdetails>
	 	</s:iterator>
	 	</watroom>
	<topdevpowerconsumed>
	<s:iterator value="toppowerdev" var="objects">
	<toppower>
		<devname><s:property value="#objects[1]"/></devname>
		<devval><s:property value="#objects[0]"/></devval>
	</toppower>
	</s:iterator>
	</topdevpowerconsumed>
	<gateway>
	<devices>
	<s:iterator value="gateWays" var="DeviceDetails">
				<device>
					<id><s:property value="#DeviceDetails.id"/></id>
					<deviceid><s:property value="#DeviceDetails.did"/></deviceid>
					<name><s:property value="#DeviceDetails.deviceName"/></name>
					<iconfile><s:property value="#DeviceDetails.deviceIcon"/></iconfile>
					<macid><s:property value="#DeviceDetails.gateway"/></macid>
					<location><s:property value="#DeviceDetails.locationName"/></location>
					<locationId><s:property value="#DeviceDetails.locationId"/></locationId>
					<enableList><s:property value="#DeviceDetails.enableList"/></enableList>
					<type><s:property value="#DeviceDetails.deviceType"/></type>
					<alertparam><s:property value="#DeviceDetails.alertCommand"/></alertparam>
					<customer><s:property value="#DeviceDetails.customer"/></customer>
					<dashboardpowerinfo><s:property value="#DeviceDetails.HMDpowerinfo"/></dashboardpowerinfo>
			<s:iterator value="#DeviceDetails.powerinformation" var="DeviceDetails">
				      <alertValue><s:property value="#DeviceDetails.alertValue"/></alertValue>
				      <alerttime><s:property value="#DeviceDetails.alertTime"/></alerttime>
				</s:iterator>
				</device>
	</s:iterator>
	</devices>
	</gateway>
</imonitor>