<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
<s:form action="saveUserAlerts.action" method="post" cssClass="ajaxinlinepopupform" id="updateUserChoosenAlerts">
	<s:hidden name="device.generatedDeviceId"></s:hidden>
	<s:hidden name="device.gateWay.macId"></s:hidden>
	<s:hidden name="device.deviceType.name"></s:hidden>
	<!--<s:if test="(SelectedAlerts == null || SelectedAlerts.equals(''))">
  	<s:iterator value="alertype" var="dev" status="stat" >
	<input class='required' name='param' type='checkbox' value='<s:property value="#dev.id" />'><s:property value="#dev.name" /> </input></br>                             
	</s:iterator>
	</s:if>
	<s:else>
  	<s:iterator value="alertype" var="dev" status="stat" >
	<s:iterator value="SelectedAlerts" var="alert" status="AlertStatus" >
	<s:if test='#alert == #dev.id'>
		<input class='required' name='param' type='checkbox' value='<s:property value="#dev.id" />' checked='checked'><s:property value="#dev.name" /></input></br>
	</s:if>
	<s:else>
	
	</s:else>
	</s:iterator>
		<input class='required' name='param' type='checkbox' value='<s:property value="#dev.id" />'><s:property value="#dev.name" /></input></br>		
	</s:iterator>		
	</s:else>-->

	<div id="expressionsection" class="expressionsection">
	</div>
	<table>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr><td style=" width: 145px; "><td><td style=" float: right;margin-top: 73px;"><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel"/></td><td><s:submit theme="simple" key="general.save" onclick="handleFormSerialization()" style=" margin-top: 73px; "></s:submit></td></tr>
    </table>
	<!--<s:select name="" label="Select Alarms" list="alertype" listKey="id" listValue="name"></s:select>-->
</s:form>
<script>
    $(document).ready(function() 
    {
    	var cForm = $("#updateUserChoosenAlerts");
    	Xpeditions.validateForm(cForm);
		var alertOptionHtml="";
       	var count=0;
      	<s:iterator  value="alertype" var="dev" status="x">
      	<s:set var="selectedOffer" value="#dev.name"/>
    	<s:if test='#selectedOffer == "PANIC_SITUATION"'>
  		count=2;
  		alertOptionHtml+= "<input class='required' name='param' type='checkbox' value='<s:property value="#dev.id" />' checked='checked'>"+formatMessage("<s:property value='#dev.details' />")+"</input></br>";  	
  		</s:if>	
	 	<s:iterator value="SelectedAlerts" var="alert" status="AlertStatus" >
	 	<s:if test="#alert == #dev.id || #dev.id == '15' ">
	 	if(count != 2)
		{
		 	count=1;
			alertOptionHtml+= "<input class='required' name='param' type='checkbox' value='<s:property value="#dev.id" />' checked='checked'>"+formatMessage("<s:property value='#dev.details' />")+"</input></br>";  	
		}
	 	
	</s:if>
	</s:iterator>
    if(count == 0)
	{
		alertOptionHtml+= "<input class='required' name='param' type='checkbox' value='<s:property value="#dev.id" />'>"+formatMessage("<s:property value='#dev.details' />")+"</input></br>";
	}
	$(".expressionsection").html(alertOptionHtml);
	count=0; 
	</s:iterator>
	alertOptionHtml+= "<input name='NotSelected' type='hidden'></input>";
	$(".expressionsection").html(alertOptionHtml);

  });
	
    function handleFormSerialization()
	{
		var selectedValues="";
		$checkedCheckboxes = $("input:checkbox[name=param]:not(:checked)");
		$checkedCheckboxes.each(function ()
		{
		selectedValues +=  $(this).val() +",";
		});
		
		$("input[name=NotSelected]").val(selectedValues);
		
	}
		function DestoryDialog()
        {
                   $("#editmodal").dialog('destroy'); 
        }
</script>