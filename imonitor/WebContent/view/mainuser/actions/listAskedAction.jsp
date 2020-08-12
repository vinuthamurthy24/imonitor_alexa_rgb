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
 	"<a class='editlink' popuptitle='Edit Action' href='toEditDeviceActions.action?action.id=<s:property value="#agent[0]"/>&action.device.id=<s:property value="#agent[3]"/>'  title='Edit Action'><img src='/imonitor/resources/images/edit.jpg'/></a><a class='deleteandreloadtablelink' dataTableUrl='listAskedDeviceActions.action' dataTableId='listactiontable' href='deleteDeviceAction.action?action.id=<s:property value="#agent[0]"/>&action.device.id=<s:property value="#agent[3]"/>&action.functions.name=<s:property value="#agent[4]"/>' title='Delete Action'><img src='/imonitor/resources/images/delete.jpg'/></a>"
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }




