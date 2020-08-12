<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
	<s:iterator value="acAutoMake" var="acb" status="status">
		<acBrand>
			<brandName><s:property value="acb"/></brandName>
		</acBrand>
	</s:iterator>	
</imonitor>