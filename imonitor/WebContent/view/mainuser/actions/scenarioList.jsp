<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
<s:iterator value="scenarios" var="scenario" status="devStatus">
<Scenario>
<id><s:property value="#scenario.id"/></id>
<name><s:property value="#scenario.name"/></name>
<description><s:property value="#scenario.description"/></description>
<iconFile><s:property value="#scenario.iconFile"/></iconFile>
</Scenario>
</s:iterator>
</imonitor>