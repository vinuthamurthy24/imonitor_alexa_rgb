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
      	"<s:date name="#a" />",
    </s:if>
	<s:else>
	<s:if test="#valueStatus.index == 3">
		<s:if test="#agent[4] == 'IMAGE_FTP_SUCCESS' ">
				"<s:property value="#a"/> <a class='imvgalertImagefrombackup' href='isAttachmentforbackup.action' alertFromImvgId='<s:property value="#agent[0]"/>' deviceId='<s:property value="#agent[5]"/>' popupheight='250' popupwidth='500' popuptitle='Add Notification !' style='border: none;text-decoration: none;'><img src='../resources/images/attachment.gif' style='float: left;'></a>",
		</s:if>
		<s:elseif test="#agent[4] == 'TEMPERATURE_CHANGE'">
			"<s:property value="#a"/>  : <s:property value="#agent[6]"/> &#176;C",
		</s:elseif>
		<%-- <s:if test="#agent[4] == 'BATTERY_STATUS'">
				"<s:property value="#a"/>  : <s:property value="#agent[6]"/> %",
		</s:if> --%>
		<s:else>
		 "<s:property value="#a"/>",
		</s:else>
	</s:if>
    <s:else>
	<s:if test="#valueStatus.index != 4">
	<s:if test="#valueStatus.index != 5">
     "<s:property value="#a"/>",</s:if></s:if>

    </s:else>
    </s:else>			
 	</s:iterator>
 	<s:if test="#session.user.role.name == 'mainuser' ">
		"<input style='text-align:center' id='SelectAll' type='checkbox' name='alerts' value='<s:property value="#agent[0]"/>'>"	
			</s:if>
			<s:else>
			""
			</s:else>
 	
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }
