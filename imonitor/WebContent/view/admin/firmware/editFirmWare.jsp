<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<h4>Editing FrimWare.</h4>
<s:form action="editFirmWare.action" method="post" id="editAgentForm" enctype="multipart/form-data" cssClass="ajaxfileuploadformpopup">
	<s:file name="firmWareFile" label="Choose the file to upload"></s:file>
	<table>
  		<tr>
			<td><s:text name="Firmware Activation:" /></td>
			<td><s:checkbox theme="simple" name="firmwareactivation"/></td>
		</tr>
</table>
	<s:hidden name="firmWare.id"></s:hidden>
	<s:submit value="Save"></s:submit>
</s:form>