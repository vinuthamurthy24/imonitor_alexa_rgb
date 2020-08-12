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
 	<s:if test="#agent[4] == null">
 	"<a class='editlink' href='toAddCustomerByGateWay.action?gateWayMacIds=<s:property value="#agent[1]"/>' title='Add Customer <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/useraddsmall.png'/></a><a class='editlink' href='toEditGateWay.action?gateWay.id=<s:property value="#agent[0]"/>' title='Edit <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/edit.jpg'/></a><a class='deletelink' href='deleteGateWay.action?gateWay.id=<s:property value="#agent[0]"/>' title='Delete <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/delete.jpg'/></a><a class='unregisterlink' href='unregisterGateWay.action?gateWay.id=<s:property value="#agent[0]"/>' title='Unregister <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/unregister.png'/></a><a class='ajaxlink' href='toUpdateFirmWare.action?gateWay.macId=<s:property value="#agent[1]"/>' title='Update Firmware of <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/update_icon.png'/></a><a class='ajaxlink' href='toListDevices.action?device.gateWay.macId=<s:property value="#agent[1]"/>' title='List Devices of <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/delete.jpg'/></a>"
</s:if>
<s:else>
 	"<a class='editlink' href='toAddCustomerByGateWay.action?gateWayMacIds=<s:property value="#agent[1]"/>' title='Add Customer <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/useraddsmall.png'/></a><a class='editlink' href='toEditGateWay.action?gateWay.id=<s:property value="#agent[0]"/>' title='Edit <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/edit.jpg'/></a><a onClick='javascript: cannotDelete()' href='#' title='Delete <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/delete.jpg'/></a><a class='unregisterlink' href='unregisterGateWay.action?gateWay.id=<s:property value="#agent[0]"/>' title='Unregister <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/unregister.png'/></a><a class='ajaxlink' href='toUpdateFirmWare.action?gateWay.macId=<s:property value="#agent[1]"/>' title='Update Firmware of <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/update_icon.png'/></a><a class='ajaxlink' href='toListDevices.action?device.gateWay.macId=<s:property value="#agent[1]"/>' title='List Devices of <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/delete.jpg'/></a><a class='removenodelink' href='removefailednodefromGateWay.action?gateWay.id=<s:property value="#agent[0]"/>' title='Remove failed nodes of <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/delete.jpg'/></a>"
</s:else>
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }
