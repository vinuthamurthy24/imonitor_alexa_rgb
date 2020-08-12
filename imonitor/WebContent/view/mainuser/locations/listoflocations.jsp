<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>

<locations>
<totalcount><s:property value="dataTable.totalRows"/></totalcount>
			<s:iterator value="dataTable.results" var="object">
			<location>	
				<id><s:property value="#object[0]"/></id>
    			<name><s:property value="#object[1]"/></name>
    			<details><s:property value="#object[2]"/></details>
			</location>
			</s:iterator>
</locations>