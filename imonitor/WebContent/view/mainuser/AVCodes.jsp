<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
	<s:iterator value="avCodeList" var="avc" status="status">
		<avCode>
			<code><s:property value="avc"/></code>
		</avCode>
	</s:iterator>	
</imonitor>