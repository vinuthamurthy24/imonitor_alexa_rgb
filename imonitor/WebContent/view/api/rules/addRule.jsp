<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
String applicationName=request.getContextPath();
%>
<script type="text/javascript" src="<%=applicationName %>/resources/js/com.imonitorsolutions.rss.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/com.imonitorsolutions.rules.js"></script>

<script>
	$(document).ready(function() {
		rss = "ruleadd";
		// handling the gateway change...
		$("#gatewayselect").hide();
		
		toggleSteps(1);
        
		
        
		$(".alertvalueclass").die("keyup");
		$(".alertvalueclass").live("keyup",handleAlertValueChange);
			
		$(".selectDeviceSpecificAlert").die("change");
		$(".selectDeviceSpecificAlert").live("change",handleDeviceSpecificAlertChange);
	   	
		$(".selectAlertDevice").die("change");
		$(".selectAlertDevice").live("change",handleAlertSelectChange);
		
		$(".selectActionDevice").die("change");
		$(".selectActionDevice").live("change",handleActionSelectChange);
		
		$(".selectActionName").die("change");
		$(".selectActionName").live("change",handleActionNameSelectChange);
		
		$(".selectAlertName").die("change");
		$(".selectAlertName").live("change",handleAlertNameSelectChange);

		$(".removeCurrentRow").die("click");
		$(".removeCurrentRow").live("click",removeCurrentRow);

		$(".actionvalueclass").die("keyup");
		$(".actionvalueclass").live("keyup",handleActionValueChange);
		
		$(".Afterdelay").die("click");
		$(".Afterdelay").live("click",handleActionDelayClick);
		
		$("#ruledelay").die("keyup");
		$("#ruledelay").live("keyup",handaleRuleDelayChange);
		
		$(".FanModevalue").die("click");
		$(".FanModevalue").live("click",handleFANModeChange);
		
		gateWayDetailsMap = {
				<s:iterator value="gateWays" var="gateWay" status="gateWayStatus">
					"<s:property value="#gateWay.id"/>":{
					    "gatewayid":"<s:property value="#gateWay.macId"/>",
					    "devices":{<s:iterator value="#gateWay.devices" var="device" status="deviceStatus">
					    	"<s:property value="#device.id"/>":{"devicename":"<s:property value="#device.friendlyName"/>",
					    	"modelNumber":"<s:property value="#device.modelNumber"/>",
					    	"makets":"<s:property value="#device.make.id"/>",
					    	"deviceType":"<s:property value="#device.deviceType.name"/>",
                                                <s:if test="#device.deviceConfiguration != null">
							"presets":"<s:property value="#device.deviceConfiguration.presetvalue"/>",
						</s:if>
					    	<s:if test="#device.deviceType.alertTypes.size > 0">
						    "alerts":{<s:iterator value="#device.deviceType.alertTypes" var="alertType" status="alertTypeStatus">
						    			"<s:property value="#alertType.id"/>":{"name":"<s:property value="#alertType.name"/>","details":"<s:property value="#alertType.details"/>"}<s:if test="#alertTypeStatus.last != true">,</s:if>
						    			</s:iterator>}
					    	</s:if>
					    	<s:if test="#device.deviceType.actionTypes.size > 0 && #device.deviceType.alertTypes.size > 0">,</s:if>
					    	<s:if test="#device.deviceType.actionTypes.size > 0">
						    "actions":{<s:iterator value="#device.deviceType.actionTypes" var="actionType" status="actionTypeStatus">
						    			"<s:property value="#actionType.id"/>":{"name":"<s:property value="#actionType.name"/>","details":"<s:property value="#actionType.details"/>"}<s:if test="#actionTypeStatus.last != true">,</s:if>
						    			</s:iterator>}
					    	</s:if>
					    	}<s:if test="#deviceStatus.last != true">,</s:if>
					    </s:iterator>}
					  }<s:if test="#gateWayStatus.last != true">,</s:if> 
				</s:iterator>
				};
		for(var gateWayId in gateWayDetailsMap){
			var oneGateWayDetails = gateWayDetailsMap[gateWayId];
			var macId = oneGateWayDetails["gatewayid"];
			
			// 1. Filling the gateway.
			$("#gatewayselect").append("<option value='" + gateWayId + "'>" + macId + "</option>");
		}
		// 2. Add the change listener to gateway select.
		$("#gatewayselect").bind("change",handleGatewaySelectChange);
		$("#gatewayselect").change();
		// Controlling the user notifiation profiles.
		var notificatioinProfiles = {
			<s:iterator value="userNotificationProfiles" var="userNotificationProfile" status="userNotificationProfileStatus">
				"<s:property value="#userNotificationProfile.id" />":{
					"name":"<s:property value="#userNotificationProfile.name" />",
					"recipient":"<s:property value="#userNotificationProfile.recipient" />",
					"actionType":"<s:property value="#userNotificationProfile.actionType.name" />"
				 }<s:if test="#userNotificationProfileStatus.last != true">,</s:if>
			</s:iterator>
		}
		for(var notificationId in notificatioinProfiles){
			var notification = notificatioinProfiles[notificationId];
			var notificationRecipient = notification["recipient"];
			var notificationType = notification["actionType"];
			if(notificationType == "E-Mail"){
				$("#emailSourceSelect").append("<option value='" + notificationId + "'>" + notificationRecipient + "</option>");
			}
			if(notificationType == "SMS"){
				$("#smsSourceSelect").append("<option value='" + notificationId + "'>" + notificationRecipient + "</option>");
			}
		}

		// Double Select.
		$(".doubleselect").die('dblclick');
		$(".doubleselect").live('dblclick', function(){
			var options = $(this).find(":selected");
			var targetSelectId = $(this).attr("target");
			var targetSelect = $("#" + targetSelectId);
			targetSelect.append(options);
		});
		$(".singleadd").die("click");
		$(".singleremove").die("click");
		$(".alladd").die("click");
		$(".allremove").die("click");
		$(".singleadd").live("click", function(){
			var pTable = $(this).closest("table");
			pTable.find(".sourceselect").dblclick();
			return false;
		});
		$(".singleremove").live("click", function(){
			var pTable = $(this).closest("table");
			pTable.find(".destinationselect").dblclick();
			return false;
		});
		$(".alladd").live("click", function(){
			var pTable = $(this).closest("table");
			var sourceselect = pTable.find(".sourceselect");
			sourceselect.find("option").attr("selected", true);
			sourceselect.dblclick();
			return false;
		});
		$(".allremove").live("click", function(){
			var pTable = $(this).closest("table");
			var destinationselect = pTable.find(".destinationselect");
			destinationselect.find("option").attr("selected", true);
			destinationselect.dblclick();
			return false;
		});

		<s:iterator value="hours" var ="hr">
   			$('#StartHourSelect').append('<option value="<s:property value="#hr"/>">'+ "<s:property value="#hr"/>" +'</option>');
			$('#EndHourSelect').append('<option value="<s:property value="#hr"/>">'+ "<s:property value="#hr"/>" +'</option>');
		</s:iterator>
		<s:iterator value="minutes" var ="min">
   			$('#StartMinuteSelect').append('<option value="<s:property value="#min"/>">'+ "<s:property value="#min"/>" +'</option>');
			$('#EndMinuteSelect').append('<option value="<s:property value="#min"/>">'+ "<s:property value="#min"/>" +'</option>');
		</s:iterator>
		
		
		
		$("#StartHourSelect").die("change");
		$("#StartHourSelect").live("change",handleStartEndChange);

		$("#EndHourSelect").die("change");
		$("#EndHourSelect").live("change",handleStartEndChange);
		
		$("#StartMinuteSelect").die("change");
		$("#StartMinuteSelect").live("change",handleStartEndChange);

		$("#EndMinuteSelect").die("change");
		$("#EndMinuteSelect").live("change",handleStartEndChange);

		$(".modecheck").die("change");
		$(".modecheck").live("change",handleModeCheckChange);

		
		});

		
	$("#saveRule").die("click");
	$("#saveRule").live("click", function(){
	 	var check = false;
		if( $("#defineActionsHidden").val() != "")check = true;
		
		$('.notifi').each(function(index) {
				if( $(this).attr('checked') == true ){
					
					check = true;}
		});
		
		if(check == false)
		{
			//vibhu changed
			showResultAlert(formatMessage("msg.controller.rules.0019"));
			return false;
		}
		if(!$(".Home").attr('checked') && !$(".Stay").attr('checked') && $(".Away").attr('checked')){
			
			$("#selectedMode").val("1");
		}
		else if($(".Stay").attr('checked') && !$(".Away").attr('checked') && !$(".Home").attr('checked')){
			
    	   $("#selectedMode").val("2");
		}else if($(".Stay").attr('checked') && $(".Away").attr('checked') && !$(".Home").attr('checked')){
			 $("#selectedMode").val("3");
		}else if(!$(".Stay").attr('checked') && !$(".Away").attr('checked') && $(".Home").attr('checked')){
			 $("#selectedMode").val("4");
		}else if($(".Home").attr('checked') && !$(".Stay").attr('checked') && $(".Away").attr('checked')){
			$("#selectedMode").val("5");
			
		}else if($(".Home").attr('checked') && $(".Stay").attr('checked') && !$(".Away").attr('checked')){
			$("#selectedMode").val("6");
		}else{
			$("#selectedMode").val("7");
		}
       
		var kForm=$("#ruledelay");
		if(kForm.val()== "")
		kForm.val("0");
		});


</script>
<br/>
<div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<form id="ruleForm" action="saveRule.action" method="POST" class="ajaxinlinepopupform">
<s:hidden name="rule.mode" id="selectedMode" ></s:hidden>
<span id="step1" >
	<select id="gatewayselect" name="rule.gateWay.id"></select>
<span class="actionheader"><s:text name="setup.rules.gendetails" /><br/></span>
 <table style='width:100%;'>
<tr></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr></tr>
 	<tr>
 		<td width="20%"><s:text name="setup.rules.name" /></td>
 		<td width="80%">
			<s:textfield name="rule.name" maxlength='35' size="35" theme="simple" cssClass="required utf8alphanumeric"></s:textfield>
 		</td>
 	</tr>
 	<tr>
 		<td width="20%"><s:text name="setup.rules.description" /></td>
 		<td width="80%">
 		<span style="color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: 1px -9px 0px -193px;"> *</span>
			<s:textarea  name="rule.description" maxlength="120" cols="35" rows="1" theme="simple" id="ruleDescriptions" cssClass="required utf8alphanumeric ruleDescriptions"></s:textarea>
 		</td>
 	</tr>
 	<tr>
 		<td width="20%"><s:text name="setup.rules.delay" /></td>
 		<td width="80%">
			<s:textfield id="ruledelay" size="5" name="rule.delay" theme="simple" ></s:textfield><span class="tablespan"><s:text name="setup.rules.delay.s" /></span>
 		</td>
 	</tr>
 </table>
<div id="popwarning" class="popwarning"></div>
<br/>
<table width="100%" ><tr ><td align="left"></td><td  align="right"><input type="button" value='<s:text name="general.next" />' onClick="toggleSteps('2')" class="navigate" /></td></tr></table>
</span>
<span id="step2">
<span class="actionheader"><s:text name="setup.rules.when" /><input type="hidden" id="criteriaValidateHidden" class="required"/></span>
<br><br/>
<div id="expressionsection" class="expressionsection">
	<table id="createCriteriaTable"></table> 
</div>
<!-- vibhu start -->
<br/>
<div id="securityModeDiv">
<span class="actionheader"><s:text name="setup.rules.smode" /></br></span>
<table id="modeSelection">
<tr>
<td><input type="checkbox"  name="modeselect"  value="Away" Class="Away modecheck" /></td><td>Away Mode</td>
</tr>
<tr>
<td>
<input type="checkbox" name="modeselect"  value="Stay" Class="Stay modecheck" /></td><td>Stay Mode</td>
</tr>
<tr>
<td><input type="checkbox" name="modeselect"  value="Home" Class="Home modecheck" checked/></td><td>Home mode</td>
</tr>
</table>

<!--<s:radio theme="simple" name="rule.mode" list="#{'1':getText('setup.rules.smode.1')}" cssClass="moderadio"/> <br />
<s:radio theme="simple" name="rule.mode" list="#{'2':getText('setup.rules.smode.2')}" cssClass="moderadio"/> <br />
<s:radio theme="simple" name="rule.mode" list="#{'3':getText('setup.rules.smode.3')}" cssClass="moderadio"/> <br />
<s:radio theme="simple" name="rule.mode" list="#{'8':getText('setup.rules.smode.8')}" value="%{8}" cssClass="moderadio"/> <br /><br />-->
<table ><tr><td width="50px"></td><td>
<table style='border: 1px solid lightgrey;' class='selectTimeRange' id='timetable'>
	<tr><th colspan=2 ><s:text name="setup.rules.time.start" /></th><th></th><th colspan=2 ><s:text name="setup.rules.time.end" /></th></tr>
	<tr>
		<td><s:text name="setup.rules.time.hour" /><select id='StartHourSelect'></select></td>
		<td><s:text name="setup.rules.time.min" /><select id='StartMinuteSelect'></select></td>
		<td>&nbsp;&nbsp;&nbsp;</td><td><s:text name="setup.rules.time.hour" /><select id='EndHourSelect'></select></td>
		<td><s:text name="setup.rules.time.min" /><select id='EndMinuteSelect'></select></td>
	</tr>
</table>
</td></tr></table>
</div>
<!-- vibhu end -->
<br/>
<table width="100%" ><tr ><td align="left"><input type="button" value='<s:text name="general.prev" />' onClick="toggleSteps('1')" /></td><td  align="right"><input type="button" value='<s:text name="general.next" />' onClick="toggleSteps('3')" class="navigate" /></td></tr></table>
</span>

<span id="step3">

<span class="actionheader"><s:text name="setup.rules.which" /><input type="hidden" id="defineActionsHidden" /></br></span>
<br/>
<div id="deviceactionsection" class="deviceactionsection">
	<table id="defineActionTable">
	</table>
</div><br>
<table width="100%" ><tr ><td align="left"><input type="button" value='<s:text name="general.prev" />' onClick="toggleSteps('2')" /></td><td  align="right"><input type="button" value='<s:text name="general.next" />' onClick="toggleSteps('4',true)" class="navigate " /></td></tr></table>
</span>
<!-- **************************************************************** sumit start *********************************************************** -->
<span id="step4">

<%-- <span class="actionheader"><s:text name="setup.rules.not" /></br></span>
<br> --%>
<%-- <s:if test="userNotificationProfiles.size >0">
<div>
	<table>
		<thead bgcolor="#D1D1D1">
			<th width="270px"><s:text name="general.name" /></th>
			<th width="370px"><s:text name="general.email" /></th>
			<th width="270px"><s:text name="general.sms" /></th>
			<th width="270px"><s:text name="WhatsApp" /></th>
		</thead>
		
		<s:iterator value="notification" var="notification" status="stat" >
		<tr bgcolor="#F1F1F1">			
			<td width="270px"><s:property value="%{notification[#stat.index].name}"/></td>
			
			<s:if test="notification[#stat.index].isEmailDefined == true" >
			<s:hidden id="emailId" name="notification[%{#stat.index}].email"/>
			<td align="center"><s:checkbox id="emailSelect" cssClass="notifi" theme="simple" 
				name="notification[%{#stat.index}].emailCheck" value="%{notification[#stat.index].emailCheck}"/></td>
			</s:if>
			<!-- Naveen start -->
			<s:elseif test="notification[#stat.index].isSmsAndEmailDefined == true">
			<s:hidden id="emailId" name="notification[%{#stat.index}].email"/>
			<td align="center"><s:checkbox id="emailSelect" cssClass="notifi" theme="simple" 
				name="notification[%{#stat.index}].emailCheck" value="%{notification[#stat.index].emailCheck}"/></td>
			</s:elseif>
			<!-- Naveen End -->
			<s:else>
			<td align="center">-</td>
			</s:else>

			<s:if test="notification[#stat.index].isSmsDefined == true" >
			<s:hidden id="smsId" name="notification[%{#stat.index}].sms"/>
			<td align="center"><s:checkbox id="smsSelect"  cssClass="notifi"  theme="simple" 
				name="notification[%{#stat.index}].smsCheck" value="%{notification[#stat.index].smsCheck}"/></td>
			</s:if>
			<!-- Naveen start -->
			<s:elseif test="notification[#stat.index].isSmsAndEmailDefined == true">
			<s:hidden id="smsId" name="notification[%{#stat.index}].sms"/>
			<td align="center"><s:checkbox id="smsSelect"  cssClass="notifi"  theme="simple" 
				name="notification[%{#stat.index}].smsCheck" value="%{notification[#stat.index].smsCheck}"/></td>
			</s:elseif>
			<!-- Naveen start -->
			<s:else>
			<td align="center">-</td>
			</s:else>
			
			
			<s:if test="notification[#stat.index].isWhatsAppDefined == true" >
			<s:hidden id="smsId" name="notification[%{#stat.index}].whatsApp"/>
			<td align="center"><s:checkbox id="smsSelect" cssClass="notifi" theme="simple" name="notification[%{#stat.index}].whatsAppCheck"
			 value="%{notification[#stat.index].whatsAppCheck}"/></td>
			</s:if>
			<!-- Naveen end -->
			<s:else>
			<td align="center">-</td>
			</s:else>
			</tr>	
		</s:iterator>
	</table>
</div>
</s:if>
<s:else>
<span><br/><s:text name="setup.rules.not.absent" /><br/></span>
</s:else> --%>
<br>

<!-- ***************************************************************** sumit end ********************************************************** -->
<table width="100%" ><tr ><td align="left"><input type="button" value='<s:text name="general.prev" />' onClick="toggleSteps('3')" /></td><td  align="right"><input type="submit" value='<s:text name="general.save" />' id="saveRule"/> <!-- vibhu added onclick validation -->
</td></tr></table>

<!-- <input type="submit" value="Save" id="saveRule"/> -->

</span>

</form>
