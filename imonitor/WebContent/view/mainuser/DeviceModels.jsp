<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
	<s:iterator value="getModelsListByDeviceType" var="dml" status="status">
		<deviceBrand>
			<modelName><s:property value="dml"/></modelName>
		</deviceBrand>
	</s:iterator>	
</imonitor>