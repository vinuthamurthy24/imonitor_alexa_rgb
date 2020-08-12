<%-- Copyright Ãƒâ€šÃ‚Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>


<s:form action="Updateuserconfigurfordoorlock.action" method="post" cssClass="ajaxinlinepopupform" id="usersaveform">
	<s:hidden name="device.id"></s:hidden>
	<s:hidden name="device.gateWay.macId"></s:hidden>
	<s:hidden name="device.generatedDeviceId"></s:hidden>
<table>
<tr>
<td>
<s:textfield name="doorLockConfiguration.userpassword" cssClass="required usercode" id="userpassword" maxlength="6" key="setup.doorlock.password" ></s:textfield>
</td>

</tr>
<tr>
	<td style=" float: right;"><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" cssClass="required" name="Cancel"/></td>
	<td><s:submit disabled="true" theme="simple" id="Save" key="general.save"></s:submit></td>
	</tr>

</table>
<script type="text/javascript">

	 function DestoryDialog()
        {
                   $("#editmodal").dialog('destroy'); 
        }	

</script>

</s:form>