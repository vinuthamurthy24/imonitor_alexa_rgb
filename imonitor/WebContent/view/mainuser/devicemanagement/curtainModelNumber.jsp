<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
<s:form action="updateCurtainModel.action" method="post" cssClass="ajaxinlinepopupform" id="updateCurtainModel">
	<s:hidden name="device.generatedDeviceId"></s:hidden>
	<s:hidden name="device.gateWay.macId"></s:hidden>
	<table>
	<tr><td><s:text name='setup.devices.model.selectmodel' /></td><td><s:select theme="simple" name="device.make.id" id="hiddenname"  list="makes" listKey="id" listValue="name" headerKey="0" headerValue="%{getText('home.msg.0015')}"></s:select></td></tr>
    <tr><td><s:text name='setup.devices.curtain.length' /></td><td><s:textfield theme="simple"  name="motorConfiguration.length"  cssClass="makenumber required editdisplayStar"></s:textfield></td><td><s:text name='cm' /></tr>
	<tr><td><s:text name='setup.devices.curtain.type' /></td><td><s:select theme="simple" name="motorConfiguration.mountingtype" list="#{'1':getText('setup.devices.curtain.type.h'),'2':getText('setup.devices.curtain.type.hs'),'3':getText('setup.devices.curtain.type.v')}" ></s:select></td></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr><td></td><td style=" float: right;"><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel" /></td><td><s:submit theme="simple" key="general.save"></s:submit></td></tr>
	</table>
</s:form>
<script>
	var cForm = $("#updateCurtainModel");
	Xpeditions.validateForm(cForm);
	function DestoryDialog()
        {
                   $("#editmodal").dialog('destroy'); 
        }
</script>