<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
	<s:iterator value="acCodeList" var="ac" status="status">
		<acCode>
			<code><s:property value="ac"/></code>
		</acCode>
	</s:iterator>	
</imonitor>