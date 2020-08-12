<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String applicationName = request.getContextPath();
%>


<%@page import="com.opensymphony.xwork2.ActionContext"%>
<span style="display:none;">
<div style="color: red;" id="messageSection">
	<s:property value="#session.message" />
</div> </span>
<%ActionContext.getContext().getSession().remove("message"); %>



<style type="text/css">

.signin_btn
{
background-color: #59595d; 
    color: white;
    padding: 6px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 21px 16px;
    cursor: pointer;
    border-radius: 10px;

}

</style>

<s:form action="toDiscoverSlaveUnit.action"  cssClass="ajaxinlinepopupform" method="POST" id="vrvForm">
<s:hidden name="device.generatedDeviceId"></s:hidden>
<s:hidden name="device.gateWay.macId"></s:hidden>
<s:hidden name="device.id"></s:hidden>
<s:hidden name="device.deviceId"></s:hidden>
<table><tr><td><s:textfield type="number" name="SlaveId" label="Enter Slave Id" id="modebus_slave" minvalue="1" maxvalue="15" cssClass="required editdisplayStar"></s:textfield></td></tr>
<tr><td><s:select name="SlaveModel"  label="Slave Model" list="#{'1':'Daiken'}"></s:select></td></tr>
<tr></tr>
<tr></tr>
<tr><td><input type="submit" value="Discover" class="signin_btn" id="discover" onclick=""  />&nbsp;&nbsp;</td>
<td><input  type="button" class="signin_btn" value="Cancel" onclick="javascript:DestoryDialog()" /></td></tr>
</table>

</s:form>
<script type="text/javascript">

//$('#discover').hide();

/* $('#modebus_slave').live('keyup', function() {
	var val = $('#modebus_slave').val();
	if(val == ""){
		$('#discover').hide();
	}else{
		$('#discover').show();
	}
});
 */
 $(document).ready(function() {
		var cForm = $("#vrvForm");
		Xpeditions.validateForm(cForm);
	});
	function DestoryDialog()
	{
	           $("#editmodal").dialog('destroy'); 
	}	

	


</script>