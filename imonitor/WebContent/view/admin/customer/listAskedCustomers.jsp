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
 	"<a class='editlink' href='toAddUserfromcustomer.action?user.customer.customerId=<s:property value="#agent[1]"/>' title='Add User <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/useraddsmall.png'/></a><a class='editlink' href='toEditCustomer.action?customer.id=<s:property value="#agent[0]"/>' title='Edit <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/edit.jpg'/></a><a class='deletelink' href='deleteCustomer.action?customer.id=<s:property value="#agent[0]"/>' title='Delete <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/delete.jpg'/></a><a class='editlink' popuptitle='<s:text name="general.servicedisabled" />' href='ToupdateCustomerService.action?customer.id=<s:property value="#agent[0]"/>' title='Disable Services in <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/revoke.png'/></a><a class='editlink' popuptitle='<s:text name="general.renew.customer.service" />' href='ToRenewCustomerService.action?customer.id=<s:property value="#agent[0]"/>' title='Renew service for <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/renewal.jpg'/></a><a class='editlink' popuptitle='<s:text name="Add SuperUser" />' href='toMapSuperUser.action?customer.id=<s:property value="#agent[0]"/>' title='Add SuperUser for <s:property value="#agent[1]"/>'><img src='/imonitor/resources/images/addbutton.png'/></a>" 
	
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }
