<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
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
 		<s:if test="#valueStatus.first != true">
	 		"<s:property value="#a"/>",	
		</s:if> 
 	</s:iterator>
 	"<a class='editlink' href='toRequestForLog.action?gateWay.id=<s:property value="#agent[0]"/>' title='Request For Log <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/useraddsmall.png'/></a><a class='ajaxlink' href='toListLogs.action?device.gateWay.macId=<s:property value="#agent[1]"/>' title='Logs of <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/delete.jpg'/></a><a class='checkpass' href='toCkeckPassWord.action?gateWay.id=<s:property value="#agent[0]"/>' title='Execute Command on <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/useraddsmall.png'/></a><a class='ajaxlink' href='MaintenanceChanelopen.action?gateWay.id=<s:property value="#agent[0]"/>' title='<s:if test="#agent[5]==0"> Establish Connection For <s:property value="#agent[1]"/></s:if><s:elseif test="#agent[5]==1"> Ready to Esatalish Connection to <s:property value="#agent[1]"/> </s:elseif><s:else >Connected to <s:property value="#agent[1]"/></s:else>'><s:if test="#agent[5]==0"><img src='/imonitor/resources/images/idle.png'/></s:if><s:elseif test="#agent[5]==1"><img src='/imonitor/resources/images/disconnected.png'/></s:elseif><s:else ><img src='/imonitor/resources/images/connected.png'/></s:else></a><a class='remoteReboot' href='toReboot.action?gateWayId=<s:property value="#agent[1]"/>' gateWayId='<s:property value="#agent[1]"/>' title='reboot of<s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/adminReboot.png'/></a><a class='editlink' href='EditToMigrateGateWay.action?gateWay.id=<s:property value="#agent[0]"/>' title='Edit <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/edit.jpg'/></a>"
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }
