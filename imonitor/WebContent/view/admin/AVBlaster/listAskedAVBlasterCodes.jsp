<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
{
"sEcho" : 
<%@page import="in.xpeditions.jawlin.imonitor.general.DataTable"%><s:property value="dataTable.sEcho"/>,
"iTotalRecords" : <s:property value="dataTable.totalRows"/>,
"iTotalDisplayRecords" : <s:property value="dataTable.totalDispalyRows"/>, 
"aaData": [
 <s:iterator value="dataTable.results" var="avBlaster" status="valueStatus1">
 	[
 	<s:iterator value="#avBlaster" var="avb" status="valueStatus">	
 		<s:if test="#valueStatus.first != true">
	 		"<s:property value="#avb"/>",	
		</s:if> 
 	</s:iterator>
 	"<a class='editlink' href='toEditAVBlasterCodeInfo.action?avBlaster.id=<s:property value="#avBlaster[0]"/>' title='edit record'><img src='/imonitor/resources/images/edit.jpg'/></a><a class='deletelink' href='deleteAVBlasterCodeInfo.action?avBlaster.id=<s:property value="#avBlaster[0]"/>' title='Delete record'><img src='/imonitor/resources/images/delete.jpg'/></a>"
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }
