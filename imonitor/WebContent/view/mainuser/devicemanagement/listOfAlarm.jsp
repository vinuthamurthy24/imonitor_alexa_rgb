<%-- <%-- Copyright Â© 2012 iMonitor Solutions India Private Limited
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imvgalert>
	<count>
		<startcount><s:if test="dataTable.results.size==0">0</s:if><s:else><s:property value="displayStart+1"/></s:else></startcount>
		<endcount><s:property value="displayStart+dataTable.results.size"/></endcount>
		<totalcount><s:property value="dataTable.totalRows"/></totalcount>
		<rowSize><s:property value="displayStart-rowSizeAlarm"/></rowSize>
		<displayrow><s:property value="rowSizeAlarm"/></displayrow>
		<displayStart><s:property value="displayStart"/></displayStart>
	</count>
			<s:iterator value="dataTable.results" var="object">
				<message>
					<time><s:date name="#object[0]" format="dd/MM/yy 'at' hh:mm a"/></time>
					<devicename><s:property value="#object[1]"/></devicename>
					<alert><s:property value="#object[2]" /></alert>
					<imvgalertid><s:property value="#object[3]"/></imvgalertid>
					<deviceid><s:property value="#object[4]"/></deviceid>
					<rulecount><s:property value="#object[5]"/></rulecount>
					<alertcommand><s:if test="#object[6] == 'STREAM_FTP_SUCCESS' ">1</s:if><s:elseif test="#object[6] == 'IMAGE_FTP_SUCCESS' ">2</s:elseif><s:else><s:property value="#object[6]"/></s:else></alertcommand>
					<alertValue><s:property value="#object[7]"/></alertValue>
					
					
				</message>
			</s:iterator>
</imvgalert> --%>


 
<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
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
      	"<s:date name="#a"/>",
    </s:if>
	<s:else>
       <s:if test="#valueStatus.index == 3">
		<s:if test="#agent[4] == 'IMAGE_FTP_SUCCESS' ">
				"<s:property value="#a"/> <a class='imvgalertImage' href='isAttachment.action' alertFromImvgId='<s:property value="#agent[0]"/>' deviceId='<s:property value="#agent[5]"/>' popupheight='250' popupwidth='500' popuptitle='Add Notification !' style='border: none;text-decoration: none;'><img src='../resources/images/attachment.gif' style='float: left;'></a>",
		</s:if>
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
		"<input style='text-align:center' id='SelectAll' type='checkbox' name='alarm' value='<s:property value="#agent[0]"/>'>"	
			</s:if>
			<s:else>
			""
			</s:else>	
 	
	]
	<s:if test="#valueStatus1.last != true">,</s:if> 
 </s:iterator>
] }
