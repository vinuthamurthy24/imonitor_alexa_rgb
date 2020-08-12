<%-- Copyright Ãƒâ€šÃ‚Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>


<s:form action="Updatesiren.action" method="post" cssClass="ajaxinlinepopupform" id="usersaveform">
	<s:hidden name="device.id"></s:hidden>
	<s:hidden name="device.gateWay.macId"></s:hidden>
	<s:hidden name="device.generatedDeviceId"></s:hidden>

	<table>
		<tr><td style="font-weight:;font-weight: 700;"><s:text name="setup.devices.operationMode"></s:text></td></tr>
		<tr><td><s:radio list="#{'0':'Siren and Strobe','1':'Siren','2':'Strobe'}" value="Status" cssClass="required" name="sirenConfiguration.sirenType"></s:radio></td></tr>
		<tr>
		
		</tr>	
	</table>
	<table>
	<tr><td style="font-weight:;font-weight: 700;">Configure Siren On Timeout</td></tr>
	<tr>
	
	<td><lable>Siren Timeout: </label><s:select theme="simple" name="sirenConfiguration.timeOutValue" list="#{'0':getText('setup.devices.timeout.1'),'1':getText('setup.devices.timeout.2'),'2':getText('setup.devices.timeout.3'),'3':getText('setup.devices.timeout.4')}" ></s:select></td>
	</tr>
	<tr>
	<td style=" float: right;"><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel"/></td>
	<td><s:submit disabled="true" theme="simple" id="Save" key="general.save"></s:submit></td>
	</tr>
	
	</table>
</s:form>


<script type="text/javascript">

	 function DestoryDialog()
        {
                   $("#editmodal").dialog('destroy'); 
        }	

</script>