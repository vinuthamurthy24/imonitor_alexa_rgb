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
 	<s:if test="#valueStatus.index == 1">
      	"<s:date name="#a" format="dd-MM-yyyy HH:mm"/>"
    </s:if>
	<s:else>
	
   "<s:date name="#a" />",
    </s:else>			
 	</s:iterator>
 	 	
	]
	
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
]}
