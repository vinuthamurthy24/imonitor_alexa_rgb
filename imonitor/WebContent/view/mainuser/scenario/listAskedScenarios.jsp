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
		<s:if test="#valueStatus.count != 4">
			<s:if test="#valueStatus.first != true">
				"<s:property value="#a.replace('\n', ' ').replace('\r', ' ')"/>",	
			</s:if> 
		</s:if> 
	</s:iterator>
 	"<a class='editlink' popupwidth='850' popupheight='550' popuptitle='Edit Senario' href='toEditScenario.action?scenario.id=<s:property value="#agent[0]"/>' title='Edit Record'><img src='/imonitor/resources/images/edit.jpg'/></a><a class='deleteandreloadtablelink' dataTableUrl='listAskedScenario.action' dataTableId='listruletable' href='deleteScenario.action?scenario.id=<s:property value="#agent[0]"/>' title='Delete Record'><img src='/imonitor/resources/images/delete.jpg'/></a>"
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }
