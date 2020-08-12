<%-- Copyright ? 2012 iMonitor Solutions India Private Limited --%>
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
		var modeSeleted =  $("#selectedMode").val();
		if(modeSeleted == "7" || modeSeleted == "8"){
			$(".Away").attr('checked', true);
			$(".Stay").attr('checked', true);
			$(".Home").attr('checked', true);
			$("#timetable").show();
		
		}
		else if(modeSeleted == "6"){
			$(".Stay").attr('checked', true);
			$(".Home").attr('checked', true);
			
		}
		else if(modeSeleted == "5"){
			$(".Away").attr('checked', true);
			$(".Home").attr('checked', true);
			
		}
		else if(modeSeleted == "4"){
			
			$(".Home").attr('checked', true);
		
		}
		else if(modeSeleted == "3"){
			$(".Away").attr('checked', true);
			$(".Stay").attr('checked', true);
		}
		else if(modeSeleted == "2" ){
			
			$(".Stay").attr('checked', true);
			$("#timetable").hide();
		}else if(modeSeleted == "1" ){
			
			$(".Away").attr('checked', true);
			$("#timetable").hide();
		}
		rss = "ruleedit";
		// handling the gateway change...
		$("#gatewayselect").hide();
	
		if($('input[name="rule.mode"]:checked').val() == 8)
		{
			$("#timetable").show();
		}
		

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

		$(".camerapresetvalueclass").die("change");
		$(".camerapresetvalueclass").live("change",handleActionValueChange);

		$(".Afterdelay").die("click");
		$(".Afterdelay").live("click",handleActionDelayClick );
		
		$(".Afterdelay").die("change");
		$(".Afterdelay").live("change",handleActionDelayClick );

		$(".FanModevalue").die("change");
		$(".FanModevalue").live("change",handleFANModeChange );
		
		$("#ruledelay").die("keyup");
		$("#ruledelay").live("keyup",handaleRuleDelayChange);
		
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

		/*
		var handleModeRadioChange = function(ev)
		{
			modeSelected = $('input[name="rule.mode"]:checked').val();
			
			if(modeSelected == 8)
			{
				$("#timetable").show();
			}
			else
			{
				$("#timetable").hide();
				$('#StartHourSelect').val('0');
				$('#StartMinuteSelect').val('0');
				$('#EndHourSelect').val('0');
				$('#EndMinuteSelect').val('0');
			}
			handleStartEndChange();
		};
		
		var handleStartEndChange = function(ev){
			var cRow = $("#firstalertrow");
			var tRow = $("#timetable");
			//var cRow = $(this).parent().parent();
			var cDeviceId = cRow.find('.selectAlertDevice').val();
			var cStartHours = tRow.find('#StartHourSelect').val();
			var cStartMinutes = tRow.find('#StartMinuteSelect').val();
			var cEndHours = tRow.find('#EndHourSelect').val();
			var cEndMinute = tRow.find('#EndMinuteSelect').val();

			var cAlertId = cRow.find('.selectAlertName').val();
			var alertValueText = cRow.find('.alertvalueclass').val();
			var deviceSpecificAlertValue = cRow.find('.selectDeviceSpecificAlert').val();
			var experssion = "";
			if($.trim(cDeviceId + cAlertId) != ""){
				experssion = cDeviceId + "=" + cAlertId+ "=" +cStartHours+ "=" +cStartMinutes+ "=" +cEndHours+ "=" +cEndMinute;
				if(alertValueText==""){
					alertValueText=0;
				}
				if(deviceSpecificAlertValue==""||deviceSpecificAlertValue==null){
					deviceSpecificAlertValue=0;
				}
				experssion += "="+deviceSpecificAlertValue+"="+alertValueText;	
			}
			
			cRow.find('input[type="hidden"]').val(experssion); 
			$("#criteriaValidateHidden").val("");
			$('input[name="alertExpressions"]').each(function(){
				$("#criteriaValidateHidden").val($("#criteriaValidateHidden").val() + $(this).val());
			});
		};*/
		
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

		
		

		
		// Updating the boxes with current values.
		var ruleDetails = {
				"alerts":{
				<s:iterator value="rule.deviceAlerts" var="deviceAlert" status="deviceAlertStatus">
					"<s:property value="#deviceAlert.id"/>":{
						"deviceId":"<s:property value="#deviceAlert.device.id"/>",
						"alertId":"<s:property value="#deviceAlert.alertType.id"/>",
						"StartTime":"<s:property value="#deviceAlert.startTime"/>",
						"EndTime":"<s:property value="#deviceAlert.endTime"/>",
						"comparatorName":"<s:property value="#deviceAlert.comparatorName"/>"
						
					}<s:if test="#deviceAlertStatus.last != true">,</s:if>
				</s:iterator>
				},
				"actions":{
				<s:iterator value="rule.imvgAlertsActions" var="deviceAction" status="deviceActionStatus">
					"<s:property value="#deviceAction.id"/>":{
						"deviceId":"<s:property value="#deviceAction.device.id"/>",
						"actionId":"<s:property value="#deviceAction.actionType.id"/>",
						"level":"<s:property value="#deviceAction.level"/>",
						"AfterDelay":"<s:property value="#deviceAction.afterDelay"/>"
					}<s:if test="#deviceActionStatus.last != true">,</s:if>
				</s:iterator>
				}
			};
		var cAlerts = ruleDetails["alerts"];
		for(var aId in cAlerts){
			var cAlert = cAlerts[aId];
			var alDeviceId = cAlert["deviceId"];
			var alAlertId = cAlert["alertId"];
			var StartTime=cAlert["StartTime"];
			var EndTime=cAlert["EndTime"];
			var ComparatorName=cAlert["comparatorName"];
			
			var n=ComparatorName.split("-");
			
			var alDeviceSelect = $("#createCriteriaTable").find('.selectAlertDevice');
			alDeviceSelect.val(alDeviceId);
			alDeviceSelect.change();
			var alAlertSelect = $("#createCriteriaTable").find('.selectAlertName');
			alAlertSelect.val(alAlertId);
			alAlertSelect.change();
			
			var SelectDeviceSpecifAlert = $("#createCriteriaTable").find('.selectDeviceSpecificAlert');
			SelectDeviceSpecifAlert.val(n[0]);
			SelectDeviceSpecifAlert.change();
			
			var alertActionValue =  $("#createCriteriaTable").find('.alertvalueclass');
			alertActionValue.val(n[1]);
			alertActionValue.keyup();

				var StartHour=Math.floor(StartTime/60);
				var StartMinute=StartTime-(StartHour*60);	
				
				var selectStarthour = $('#StartHourSelect');
				selectStarthour.val(StartHour);
				selectStarthour.change();
				var selectStartminu = $('#StartMinuteSelect');
				selectStartminu.val(StartMinute);
				selectStartminu.change();		
			
				var EndHour=Math.floor(EndTime/60);
				var EndMinute=EndTime-(EndHour*60);
				var selectEndhour = $('#EndHourSelect');
				selectEndhour.val(EndHour);
				selectEndhour.change();
				var selectEndminu = $('#EndMinuteSelect');
				selectEndminu.val(EndMinute);
				selectEndminu.change();	
				
			
			
		}
		var cActions = ruleDetails["actions"];
		for(var aId in cActions){
			var cAction = cActions[aId];
			var alDeviceId = cAction["deviceId"];
			var alActionId = cAction["actionId"];
			var splited = cAction["level"].split(":");
			
			var afterDelay=cAction["AfterDelay"];
			var cRow = $("#defineActionTable").find('tr:last');
					
			var alDeviceSelect = cRow.find('.selectActionDevice');
			alDeviceSelect.val(alDeviceId);
			alDeviceSelect.change();
			
			
			var AfterDelay = cRow.find('.Afterdelay');
			if(afterDelay == 1)
			{
				
				AfterDelay.attr('checked',true);
				
			}
			AfterDelay.change();
			
			
			
			var alActionSelect = cRow.find('.selectActionName');
			alActionSelect.val(alActionId);
			alActionSelect.change();			
			var level = splited[0];		
			
			var alActionValueText = cRow.find('.actionvalueclass');	

						
			alActionValueText.val(level);			
			alActionValueText.keyup();	
			
			var AcModeSelect = cRow.find('.FanModevalue');

			
			if(splited.length == 2)
			{			
				AcModeSelect.val(splited[1]);
				AcModeSelect.change();
			}
			else
			{			
				AcModeSelect.val(level);
				AcModeSelect.change();			
			}
			
			
		}
		
		// Controlling the user notifiation profiles.
		var notificatioinProfiles = {
			<s:iterator value="userNotificationProfiles" var="userNotificationProfile" status="userNotificationProfileStatus">
				"<s:property value="#userNotificationProfile.id" />":{
					"name":"<s:property value="#userNotificationProfile.name" />",
					"recipient":"<s:property value="#userNotificationProfile.recipient" />",
					"actionType":"<s:property value="#userNotificationProfile.actionType.name" />"
				 }<s:if test="#userNotificationProfileStatus.last != true">,</s:if>
			</s:iterator>
		};
		
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
		//Selecting the saved values.

		var savedNotificationProfiles = {
			<s:iterator value="rule.imvgAlertNotifications" var="imvgAlertNotification" status="imvgAlertNotificationStatus">
				"<s:property value="#imvgAlertNotification.userNotificationProfile.id" />":{
								"name":"<s:property value="#imvgAlertNotification.userNotificationProfile.name" />",
								"recipient":"<s:property value="#imvgAlertNotification.userNotificationProfile.recipient" />",
								"actionType":"<s:property value="#imvgAlertNotification.userNotificationProfile.actionType.name" />"
							 }<s:if test="#imvgAlertNotificationStatus.last != true">,</s:if>
			</s:iterator>
		};
		for(var notificationId in savedNotificationProfiles){
			var notification = notificatioinProfiles[notificationId];
			var notificationRecipient = notification["recipient"];
			var notificationType = notification["actionType"];
			if(notificationType == "E-Mail"){
				$("#emailSourceSelect").find("option[value='" + notificationId + "']").attr("selected",true);
			}
			if(notificationType == "SMS"){
				$("#smsSourceSelect").find("option[value='" + notificationId + "']").attr("selected",true);
			}
		}
		$("#emailSourceSelect").dblclick();
		$("#smsSourceSelect").dblclick();
	});
	
	$("#saveRule").die("click");
	$("#saveRule").live("click", function(){
	 	var check = false;
		if( $("#defineActionsHidden").val() != "")check = true;
		
		$('.notifi').each(function(index) {
				if( $(this).attr('checked') == true )check = true;
		});
		
		if(check == false)
		{
			//alert("Either actions or notification must be added before saving the rule");
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
<s:form id="ruleForm" action="#" method="POST" cssClass="ajaxinlinepopupform">
<s:hidden name="rule.mode" id="selectedMode" ></s:hidden>
<select id="gatewayselect" name="rule.gateWay.id"></select>
 <table>
 	<tr>
 		<td>
			<s:textfield name="rule.name" maxlength='35' size='35' key="setup.rules.name" cssClass="required utf8alphanumeric editdisplayStar"></s:textfield>
			<s:hidden name="rule.id"></s:hidden>
 		</td>
 	</tr>
 	<tr>
 		<td>
 		
			<s:textarea key="setup.rules.description"  maxlength="120" name="rule.description" cols="35" rows="1" id="ruleDescriptions" cssClass="required utf8alphanumeric ruleDescriptions editdisplayStar"></s:textarea>
 		</td>
 	</tr>
 	<tr>
 		<td><s:text name="setup.rules.delay" /> <s:text name="setup.rules.delay.s" /></td>
 		<td>
			<s:textfield id="ruledelay" size="5" name="rule.delay" theme="simple" ></s:textfield>
 		</td>
 	</tr>
 </table>
<div id="popwarning" class="popwarning"></div>

<br/>
<span class="actionheader"><s:text name="setup.rules.when" /><input type="hidden" id="criteriaValidateHidden" class="required displayStarHeader"/></span>
<div id="expressionsection" class="expressionsection">
	<table id="createCriteriaTable">
	</table>
</div>
<!-- vibhu start -->
<div id="securityModeDiv">
<br/>
<span class="actionheader"><s:text name="setup.rules.smode" /></br></span>
<table id="modeSelection">
<tr>
<td><input type="checkbox"  name="modeselect"  value="Away" Class="Away modecheck" /></td><td>Away Mode</td>
</tr>
<tr>
<td>
<input type="checkbox" name="modeselect"  value="Stay" Class="Stay modecheck"/></td><td>Stay Mode</td>
</tr>
<tr>
<td><input type="checkbox" name="modeselect"  value="Home" Class="Home modecheck" /></td><td>Home mode</td>
</tr>
</table>


<!--<s:radio theme="simple" name="rule.mode" list="#{'1':getText('setup.rules.smode.1')}" value="rule.mode" cssClass="moderadio"/> <br />
<s:radio theme="simple" name="rule.mode" list="#{'2':getText('setup.rules.smode.2')}" value="rule.mode" cssClass="moderadio"/> <br />
<s:radio theme="simple" name="rule.mode" list="#{'3':getText('setup.rules.smode.3')}" value="rule.mode" cssClass="moderadio"/> <br />
<s:radio theme="simple" name="rule.mode" list="#{'8':getText('setup.rules.smode.8')}" value="rule.mode" cssClass="moderadio"/> <br /><br /> -->
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
<span class="actionheader"><s:text name="setup.rules.which" /><input type="hidden" id="defineActionsHidden" /></span>
<div id="deviceactionsection" class="deviceactionsection">
	<table id="defineActionTable">
	</table>
</div>
<!-- ************************************************************ sumit start ************************************************************ -->
<%-- <s:if test="userNotificationProfiles.size >0">
<br/>
<span class="actionheader"><s:text name="setup.rules.not" /></span>
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
					<td  align="center"><s:checkbox id="emailSelect"   cssClass="notifi"  theme="simple" 
						name="notification[%{#stat.index}].emailCheck" value="%{notification[#stat.index].emailCheck}"/></td>										
				</s:if>
				<s:elseif test="notification[#stat.index].isSmsAndEmailDefined == true">
			<s:hidden id="emailId" name="notification[%{#stat.index}].email"/>
			<td align="center"><s:checkbox id="emailSelect" cssClass="notifi" theme="simple" 
				name="notification[%{#stat.index}].emailCheck" value="%{notification[#stat.index].emailCheck}"/></td>
			
			</s:elseif>
				<s:else>
					<td align="center">-</td>
				</s:else>
	
				<s:if test="notification[#stat.index].isSmsDefined == true" >
					<s:hidden id="smsId" name="notification[%{#stat.index}].sms"/>
					<td  align="center"><s:checkbox id="smsSelect"  cssClass="notifi"  theme="simple" 
						name="notification[%{#stat.index}].smsCheck" value="%{notification[#stat.index].smsCheck}"/></td>
				</s:if>
				<s:elseif test="notification[#stat.index].isSmsAndEmailDefined == true">
			<s:hidden id="smsId" name="notification[%{#stat.index}].sms"/>
			<td align="center"><s:checkbox id="smsSelect"  cssClass="notifi"  theme="simple" 
				name="notification[%{#stat.index}].smsCheck" value="%{notification[#stat.index].smsCheck}"/></td>
			
			</s:elseif>
				<s:else>
					<td align="center">-</td>
				</s:else>
				
	<s:if test="notification[#stat.index].isWhatsAppDefined == true" >
			<s:hidden id="smsId" name="notification[%{#stat.index}].whatsApp"/>
			<td align="center"><s:checkbox id="smsSelect" cssClass="notifi" theme="simple" name="notification[%{#stat.index}].whatsAppCheck"
			 value="%{notification[#stat.index].whatsAppCheck}"/></td>
			</s:if>
			<!-- Naveen start -->
			<s:else>
			<td align="center">-</td>
			</s:else>
			

			</tr>	
		</s:iterator>
	</table>
</div>
</s:if>
<br/> --%>

<!-- *********************************************************** sumit end ****************************************************************** -->
<input type="submit" value='<s:text name="general.save" />' onClick="if(validateActions() == true){$('#ruleForm').attr('action','updateRule.action');}" id="saveRule"/> <!-- vibhu added onclick validation -->
</s:form>
