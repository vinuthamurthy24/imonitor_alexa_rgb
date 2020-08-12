<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
	<table class="alertstables">
		<th class="alertstablecell">Time</th>
		<th style="text-align: left;">Device Name</th>
		<th style="text-align: left;">Alert</th>
	<tr class="alertstabledatas">
		<td style="text-align: right;" id="time">
			<s:if test="#object[5] >0 ">
				<s:if test="#object[6] == 'STREAM_FTP_SUCCESS' ">
					<a class='imvgalertStream'  href="isStreamAttachment.action" alertFromImvgId="<s:property value="#object[3]"/>" deviceId="<s:property value="#object[4]"/>" popupheight="250" popupwidth="500" popuptitle="Add Notification !" 
					 style="border: none;text-decoration: none;"><img src="../resources/images/videoicon.PNG" style="float: left;"></a>
				</s:if>
				<s:if test="#object[6] == 'IMAGE_FTP_SUCCESS' ">
					<a class='imvgalertImage'  href="isAttachment.action" alertFromImvgId="<s:property value="#object[3]"/>" deviceId="<s:property value="#object[4]"/>" popupheight="250" popupwidth="500" popuptitle="Add Notification !" 
					 style="border: none;text-decoration: none;"><img src="../resources/images/attachment.gif" style="float: left;"></a>
				</s:if>
			</s:if>
			<s:date name="#object[0]" format="dd/MM/yy 'at' hh:mm a"/>&nbsp;&nbsp;
		</td>
		<td style="text-align: left;" id="devicename"><s:property value="#object[1]"/></td>
		<td style="text-align: left;" id="alert"><s:property value="#object[2]" /><s:property value="#object[8]" /></td>
	</tr>
	<tr class="alertsnextpreviouslink">
		<s:if test="dataTable.results.size != 0">
			<s:if test="displayStart==0">
				<td><b>1</b>-<b><s:property value="displayStart+dataTable.results.size"/></b>&nbsp;&nbsp;of&nbsp;&nbsp;<b><s:property value="dataTable.totalRows"/></b></td>
			</s:if>
			<s:else>
				<td><b><s:property value="displayStart+1"/></b>-<b><s:property value="displayStart+dataTable.results.size"/></b>&nbsp;&nbsp;of&nbsp;&nbsp;<b><s:property value="dataTable.totalRows"/></b></td>
			</s:else>
		</s:if>
		<td colspan="3" align="right">
			<s:if test="(displayStart+dataTable.results.size)>rowSize">
					<s:url action="listImvgAlerts.action" id="Url_listImvgAlerts"  >
						<s:param name="displayStart" value="displayStart-rowSize"></s:param>
					</s:url>
					<s:a href="%{Url_listImvgAlerts}" cssClass="ajaxlinkalert"><img src="../resources/images/back_enabled.jpg"></s:a> 
			</s:if>
			<s:else>
				<img src="../resources/images/back_disabled.jpg">
			</s:else>
			<s:if test="dataTable.totalRows >(displayStart+dataTable.results.size) ">
				<s:url action="listImvgAlerts.action" id="Url_listImvgAlerts" >
					<s:param name="displayStart" value="displayStart+rowSize"></s:param>
				</s:url>
				<s:a href="%{Url_listImvgAlerts}" cssClass="ajaxlinkalert"><img src="../resources/images/forward_enabled.jpg"></s:a>
			</s:if>
			<s:else>
				<img src="../resources/images/forward_disabled.jpg">
			</s:else>
		</td>
	</tr>
	</table>
