<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
{
"sEcho" : 
<%@page import="in.xpeditions.jawlin.imonitor.general.DataTable"%><s:property value="dataTable.sEcho"/>,
"iTotalRecords" : <s:property value="dataTable.totalRows"/>,
"iTotalDisplayRecords" : <s:property value="dataTable.totalDispalyRows"/>, 
"aaData": [
 <s:iterator value="dataTable.results" var="agent" status="valueStatus1">
 	[
 	<s:iterator value="#agent" var="a" status="valueStatus">
 	
 	<s:if test="#valueStatus.first != true">
	 		"<s:property value="#a"/>",	
	</s:if>
 	</s:iterator>
 	"<input class='required' name='param' type='checkbox' value='<s:property value="#agent[0]"/>' >"

	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }
