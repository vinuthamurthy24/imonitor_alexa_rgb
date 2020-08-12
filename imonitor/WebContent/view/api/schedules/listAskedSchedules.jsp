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
	  <s:if test="#valueStatus.count <= 3">
 		<s:if test="#valueStatus.first != true">
	 		"<s:property value="#a.replace('\n', ' ').replace('\r', ' ')"/>",	
		</s:if> 
	  </s:if> 
 	</s:iterator>
 
 	"<a class='editlink' popupwidth='700' popupheight='600' popuptitle='Edit Schedule' href='toEditSchedule.action?schedule.id=<s:property value="#agent[0]"/>' title='Edit Record'><img src='/imonitor/resources/images/edit.jpg'/></a><a class='deleteandreloadtablelink' dataTableUrl='listAskedSchedules.action' dataTableId='listschedulestable' href='deleteSchedule.action?schedule.id=<s:property value="#agent[0]"/>' title='Delete Record'><img src='/imonitor/resources/images/delete.jpg'/></a><s:if test='#agent[4] == 1'><a class='activatedeactivateschedule' activation='0' schid='<s:property value="#agent[0]"/>'  href='toDeactivateSchedule.action?schedule.id=<s:property value="#agent[0]"/>' title='De-activate Schedule'><img src='/imonitor/resources/images/suspend.png'/></a>"</s:if><s:else><a class='activatedeactivateschedule' schid='<s:property value="#agent[0]"/>' activation='1' href='toActivateSchedule.action?schedule.id=<s:property value="#agent[0]"/>' title='Activate Schedule'><img src='/imonitor/resources/images/update_icon.png'/></a>"</s:else>


	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }
