<%-- Copyright � 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
{
"sEcho" : 
<%@page import="in.xpeditions.jawlin.imonitor.general.DataTable"%><s:property value="dataTable.sEcho"/>,
"iTotalRecords" : <s:property value="dataTable.totalRows"/>,
"iTotalDisplayRecords" : <s:property value="dataTable.totalDispalyRows"/>, 
"aaData": [
 <s:iterator value="dataTable.results" var="agent" status="valueStatus1">
 	[
 	<s:iterator value="#agent" var="a" status="valueStatus">
    "<s:property value="#a"/>",	
    </s:iterator>
	"<a class='ajaxlink' href='toEditSystemIntegrator.action?systemIntegrator.id=<s:property value="#agent[0]"/>' title='Edit <s:property value="#agent[1]"/> '><img src='/imonitor/resources/images/edit.jpg'/></a><a class='deletelink' href='deleteSystemIntegrtor.action?systemIntegrator.id=<s:property value="#agent[0]"/>' title='Delete <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/delete.jpg'/></a>"
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] } 