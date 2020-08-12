<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
	<storage>
	<UN><s:property value="user.userId"/></UN>
	    <ILT><s:date name="user.lastLoginTime" format="dd/MM/yy 'at' hh:mm a"/></ILT>
		<totalspace><s:property value="#user.customer.paidStorageMB + #user.customer.freeStorageMB"/></totalspace>
		<usedspace><s:property value="#totalUsedSpace"/></usedspace>
	</storage>	
	<s:iterator value="gateWays" var="gateWay">
		<gateway>
			<id><s:property value="#gateWay.id"/></id>
			<macid><s:property value="#gateWay.macId"/></macid>
			<currentMode><s:property value="#gateWay.currentMode"/></currentMode>
			<status><s:property value="#gateWay.status.name"/></status>
			<devices>

			</devices>
		</gateway>
	</s:iterator>
	<s:iterator value="scenarios" var="scenario">
		<scenario>
			<id><s:property value="#scenario.id"/></id>
			<name><s:property value="#scenario.name"/></name>
			<description><s:property value="#scenario.description"/></description>
			<iconfile><s:property value="#scenario.iconFile"/></iconfile>
		</scenario>
	</s:iterator>	
</imonitor>