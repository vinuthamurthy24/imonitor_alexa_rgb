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
 	"<a class='editroomlink' href='toEditUser.action?user.id=<s:property value="#agent[0]"/>' popuptitle='Edit User' title='Edit Record'><img src='/imonitor/resources/images/edit.jpg'/></a><a class='deleteandreloadtablelink' dataTableUrl='listAskedUsers.action' dataTableId='listusertable' href='deleteUser.action?user.id=<s:property value="#agent[0]"/>'  title='Delete Record'><img src='/imonitor/resources/images/delete.jpg'/></a><s:if test="#agent[2]=='Offline'"><a class='ajaxsuspenandrevoke' dataTableUrl='listAskedUsers.action' dataTableId='listusertable' href='suspendUser.action?user.id=<s:property value="#agent[0]"/>'  title='Suspend User' type='1'><img src='/imonitor/resources/images/suspend.png'/></a></s:if><s:if test="#agent[2]=='Suspend'"><a class='ajaxsuspenandrevoke'  dataTableUrl='listAskedUsers.action' dataTableId='listusertable' type='2' href='revokeUser.action?user.id=<s:property value="#agent[0]"/>'  title='Revoke User'><img src='/imonitor/resources/images/revoke.png'/></a></s:if><a class='customizeUserAccess' href='toCustomizeSubUserAccess.action?user.id=<s:property value="#agent[0]"/>' popuptitle='Customize Sub-User Access' title='Customize Sub User Access'><img src='/imonitor/resources/images/useraddsmall.png'/></a>"
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }
