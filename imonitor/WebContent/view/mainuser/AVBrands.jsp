<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
	<s:iterator value="avBrandList" var="avb" status="status">
		<avBrand>
			<brandName><s:property value="avb"/></brandName>
		</avBrand>
	</s:iterator>	
</imonitor>