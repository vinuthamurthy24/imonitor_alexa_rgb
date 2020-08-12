<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
	<s:iterator value="userTips" var="ut" status="status">
		<userTip>
			<tips><s:property value="ut"/></tips>
		</userTip>
	</s:iterator>	
</imonitor>