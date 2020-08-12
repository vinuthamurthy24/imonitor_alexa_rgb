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
	 		<%--  "<s:property value="#a"/>", --%>	 
	 		
	 		 <s:if test="#valueStatus.index == 2">
      			"<s:date name="#a" format="dd-MM-yyyy HH:mm"/>"
   			 </s:if>
   			 <s:elseif test="#valueStatus.index == 7">
      			"<s:date name="#a" format="dd-MM-yyyy"/>"
   			 </s:elseif>
   			  
			<s:else>
      			 "<s:property value="#a"/>"
		    </s:else> ,
		</s:if>
 	</s:iterator>

 	"<a class='editlink' href='toViewCustomerReport.action?selectedReport=<s:property value="#agent[0]"/>&customerId=<s:property value="#agent[1]"/>' title='View report'><img src='/imonitor/resources/images/edit.jpg'/></a><a class='editlink' href='toDeleteCustomerReport.action?selectedReport=<s:property value="#agent[0]"/>&customerId=<s:property value="#agent[1]"/>' title='Delete report'><img src='/imonitor/resources/images/delete.jpg'/></a>"
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 	</s:iterator>
]}
