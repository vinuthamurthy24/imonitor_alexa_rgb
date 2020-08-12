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
 	
 		<s:if test="#valueStatus.index == 2">
		<s:if test="#agent[2] == 1">
		"<input style='text-align:center' id='SelectAll' checked='checked' type='checkbox' class='SelectAll'  name='alerts' value='<s:property value="#agent[0]"/>'>",
		</s:if>
		<s:else>
		 "<input style='text-align:center' class='SelectAll' id='SelectAll' type='checkbox'  name='alerts' value='<s:property value="#agent[0]"/>'>",
		</s:else>
		"<s:property value="#a"/>",
		</s:if>
		<s:else>
		 "<s:property value="#a"/>",
		</s:else>

    </s:iterator>
	"<a class='editlink' href='toEditSmsService.action?smsService.operatorcode=<s:property value="#agent[1]"/>' title=''><img src='/imonitor/resources/images/edit.jpg'/></a><a class='deletelink' href='deleteSmsService.action?smsService.id=<s:property value="#agent[0]"/>' title=''><img src='/imonitor/resources/images/delete.jpg'/></a>"
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }