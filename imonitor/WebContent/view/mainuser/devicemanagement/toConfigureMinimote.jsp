<%-- Copyright Ãƒâ€šÃ‚Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>


</script>
<s:form action="UpdateminimoteConfiguration.action" method="post" cssClass="ajaxinlinepopupform" id="updateMinimoteConfig">
<s:hidden name="device.generatedDeviceId" id="genDeviceId"></s:hidden>
<s:hidden name="device.gateWay.macId" id="gwMacID"></s:hidden>

<div>

<img class="" style="margin:0px 0px 0px 329px;height:399px;" src="../resources/images/configMinimote.png" />
</div> 

<table style="float: left; margin: -327px 0px 0px 59px;position:fixed;">

<tr>
<s:select  style="width:200px;" name="minimoteConfig.buttonone" list="devices" listKey="generatedDeviceId" listValue="friendlyName" headerKey="PANIC_SITUATION" headerValue="Panic Situation" id='configOne' cssClass="required"></s:select>

</tr></table>
<span style="position:fixed; margin: -323px 63px 0px 271px ;float: right;">:Button 1</span>
<table style="margin: -334px 63px 0px 500px;float: right;position:fixed">
<tr>
<s:select style="width:200px;" label="Button 2" name="minimoteConfig.buttontwo" list="devices" listKey="generatedDeviceId" listValue="friendlyName" headerKey="PANIC_SITUATION" headerValue="Panic Situation" id='configTwo' cssClass="required"></s:select>
<tr>
</table>
<table style="margin: -263px -6px 0px 59px;float: left;position:fixed;">
<tr>
<s:select   style="width:200px;" name="minimoteConfig.buttonthree" list="devices" listKey="generatedDeviceId" listValue="friendlyName" headerKey="PANIC_SITUATION" headerValue="Panic Situation" id='configThree' cssClass="required"></s:select>
</tr>
</table>
<span style="position:fixed; margin: -263px 63px 0px 271px ;float: right;">:Button 3</span>
<table style="margin: -276px 63px 0px 500px;float: right;position:fixed"><tr>
<s:select  style="width:200px;" label="Button 4" name="minimoteConfig.buttonfour" list="devices" listKey="generatedDeviceId" listValue="friendlyName" headerKey="PANIC_SITUATION" headerValue="Panic Situation" id='configFour' cssClass="required"></s:select>
</tr>
</table>




<s:submit key="general.save" id="validateMinimoteBeforeSave"></s:submit>
</s:form>



