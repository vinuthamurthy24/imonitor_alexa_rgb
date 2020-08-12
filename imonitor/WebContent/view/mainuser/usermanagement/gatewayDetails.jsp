<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
<s:iterator value="zWaveVersion" var="gateway" status="Status">
<gatewayVersion>
			<version><s:property value="gateway"/></version>
</gatewayVersion>
</s:iterator>
</imonitor>