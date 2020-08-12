<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<imonitor>
	<gateway>
		<id><s:property value="gateWay.id"/></id>
		<macid><s:property value="gateWay.macId"/></macid>
		<status><s:property value="gateWay.status.name"/></status>
	</gateway>
</imonitor>