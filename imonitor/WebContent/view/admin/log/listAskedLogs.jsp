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
 	"<a  href='<s:property value="imvgUploadContextPath"/><s:property value="#agent[2]"/><s:property value="#agent[1]"/>' title='Download <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/download.png'/></a><a class='deletelink' href='deleteLog.action?fileName=<s:property value="#agent[1]"/>&filePath=<s:property value="#agent[2]"/>&device.gateWay.macId=<s:property value="device.gateWay.macId"/>' title='Delete <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/delete.jpg'/></a>"
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }