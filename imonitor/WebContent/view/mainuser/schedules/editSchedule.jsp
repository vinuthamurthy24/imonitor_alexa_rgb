<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
String applicationName = request.getContextPath();
%>
<script type="text/javascript" src="<%=applicationName %>/resources/js/com.imonitorsolutions.rss.js"></script>

<script>
	$(document).ready(function() {
		
		rss = "scheduleedit";
		// handling the gateway change...
		//$("#gatewayselect").hide();

		$(".selectActionDevice").die("change");
		$(".selectActionDevice").live("change",handleActionSelectChange);
		
		$(".selectActionDeviceEnd").die("change");
		$(".selectActionDeviceEnd").live("change",handleActionSelectChangeEnd);
		
		$(".selectActionName").die("change");
		$(".selectActionName").live("change",handleActionNameSelectChange);
		
		$(".selectActionNameEnd").die("change");
		$(".selectActionNameEnd").live("change",handleActionNameSelectChangeEnd);

		$(".removeCurrentRow").die("click");
		$(".removeCurrentRow").live("click",removeCurrentRow);
		
		$(".removeCurrentRowEnd").die("click");
		$(".removeCurrentRowEnd").live("click",removeCurrentRowEnd);

		$(".actionvalueclass").die("keyup");
		$(".actionvalueclass").live("keyup",handleActionValueChange);
		
		$(".actionvalueclassEnd").die("keyup");
		$(".actionvalueclassEnd").live("keyup",handleActionValueChangeEnd);

		$(".camerapresetvalueclass").die("change");
		$(".camerapresetvalueclass").live("change",handleActionValueChange);
		
		$(".camerapresetvalueclassEnd").die("change");
		$(".camerapresetvalueclassEnd").live("change",handleActionValueChangeEnd);

		$(".FanModevalue").die("change");
		$(".FanModevalue").live("change",handleFANModeChange);
		
		$(".FanModevalueEnd").die("click");
		$(".FanModevalueEnd").live("click",handleFANModeChangeEnd);
	
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
		$("#gatewayselect").bind("change",handleGatewaySelectChangeForEndAction);
		$("#gatewayselect").change();

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
		
			var savedNotificationProfiles = {
					<s:iterator value="schedule.ImvgAlertNotificationsForScedule" var="ImvgAlertNotificationsForScedule" status="ImvgAlertNotificationsForSceduleStatus">
		                             
						"<s:property value="#ImvgAlertNotificationsForScedule.userNotificationProfile.id" />":{
										"name":"<s:property value="#ImvgAlertNotificationsForScedule.userNotificationProfile.name" />",
										"recipient":"<s:property value="#ImvgAlertNotificationsForScedule.userNotificationProfile.recipient" />",
										"actionType":"<s:property value="#ImvgAlertNotificationsForScedule.userNotificationProfile.actionType.name" />"
		                                                       
									 }<s:if test="#ImvgAlertNotificationsForSceduleStatus.last != true">,</s:if>
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

		
		
		// Schedule Time selector Starts here....
		
		
				
		
		var handleTimeSelectionChange = function(){
			var sHour = $("#scheduleHourSelect").val();
			var sMinute = $("#scheduleMinuteSelect").val();
			var sDates = $(".dateSelect:checked").filter(':not(:disabled)');
			var seDates = "";
			for(var i = 0; i < sDates.length; i++){
				var sDate = $(sDates[i]).val();
				if(i != 0){
					seDates += ",";
				}
				seDates += sDate;
			}
			
			var sMonths = $(".monthSelect:checked").filter(':not(:disabled)');
			var seMonths = "";
			for(var i = 0; i < sMonths.length; i++){
				var ssMonth = $(sMonths[i]).val();
				if(i != 0){
					seMonths += ",";
				}
				seMonths += ssMonth;
			}
			
			var sDays = $(".daySelect:checked").filter(':not(:disabled)');
			var seDays = "";
			for(var i = 0; i < sDays.length; i++){
				var seDay = $(sDays[i]).val();
				if(i != 0){
					seDays += ",";
				}
				seDays += seDay;
			}
			$("#dayexpressionholder").val(seDays);
			$("#monthexpressionholder").val(seMonths);
			$("#dateexpressionholder").val(seDates);
			var timeExpression = sMinute + " " + sHour + " " + seDates + " " + seMonths + " " + seDays;
			$("#scheduleTimeExpression").val(timeExpression);
		};
		
	
	
		var handleDateSelectCheckBoxChange = function(){
			var isChecked = $(this).attr("checked");
			var value = $(this).val();
			if(value == "*" && isChecked == true){
				$(this).siblings().attr("checked","checked");
				$(this).siblings().attr("disabled", true);
			}
			if(value == "*" && isChecked == false){
				$(this).siblings().removeAttr("disabled");
			}
			handleTimeSelectionChange();
			return true;
		};
		var handleMonthSelectCheckBoxChange = function(){
			var isChecked = $(this).attr("checked");
			var value = $(this).val();
			if(value == "*" && isChecked == true){
				$(this).siblings().attr("checked","checked");
				$(this).siblings().attr("disabled", true);
			}
			if(value == "*" && isChecked == false){
				$(this).siblings().removeAttr("disabled");
			}
			handleTimeSelectionChange();
			return true;
		};
		var handleDaySelectCheckBoxChange = function(){
			var isChecked = $(this).attr("checked");
			var value = $(this).val();
			if(value == "*" && isChecked == true){
				$(this).siblings().attr("checked","checked");
				$(this).siblings().attr("disabled", true);
			}
			if(value == "*" && isChecked == false){
				$(this).siblings().removeAttr("disabled");
			}
			handleTimeSelectionChange();
			return true;
		};
		var handleScheduleHourSelectChange = function(){
			handleTimeSelectionChange();
		};
		var handleScheduleMinuteSelectChange = function(){
			handleTimeSelectionChange();
		};
		
	var handleEndScheduleHourSelectChange = function(){
			handleEndTimeSelectionChange();
		};
		var handleEndScheduleMinuteSelectChange = function(){
			handleEndTimeSelectionChange();
		};
		$(".dateSelect").die("change");
		$(".dateSelect").live("change", handleDateSelectCheckBoxChange);
		
		$(".monthSelect").die("change");
		$(".monthSelect").live("change", handleMonthSelectCheckBoxChange);
		
		$(".daySelect").die("change");
		$(".daySelect").live("change", handleDaySelectCheckBoxChange);
		$("#scheduleHourSelect").unbind("change");
		$("#scheduleHourSelect").bind("change", handleScheduleHourSelectChange);
		$("#scheduleMinuteSelect").bind("change", handleScheduleMinuteSelectChange);
		
		$("#scheduleHourSelectEnd").bind("change", handleEndScheduleHourSelectChange);
		$("#scheduleMinuteSelectEnd").bind("change", handleEndScheduleMinuteSelectChange);
		
		var sheduleTime = "<s:property value="schedule.scheduleTime"/>";
		var split = sheduleTime.split(" ");
		var cMinute = split[0];
		var cHour = split[1];
		var cDates = split[2].split(",");
		var cMonths = split[3].split(",");
		var cDays = split[4].split(",");
		
		$("#scheduleHourSelect").val(cHour);
		$("#scheduleMinuteSelect").val(cMinute);
		for(var i = 0; i < cDates.length; i++){
			var cDate = cDates[i];
			var cCheck = $(".dateSelect[value='" + cDate + "']")
			cCheck.attr("checked","checked");
			cCheck.change();
		}
		for(var i = 0; i < cMonths.length; i++){
			var cMonth = cMonths[i];
			var cCheck = $(".monthSelect[value='" + cMonth + "']")
			cCheck.attr("checked","checked");
			cCheck.change();
		}
		for(var i = 0; i < cDays.length; i++){
			var cDay = cDays[i];
			var cCheck = $(".daySelect[value='" + cDay + "']")
			cCheck.attr("checked","checked");
			cCheck.change();
		}
        
		var handleEndTimeSelectionChange = function(){
			
			var eHour = $("#scheduleHourSelectEnd").val();
			var eMinute = $("#scheduleMinuteSelectEnd").val();
			var sHour = $("#scheduleHourSelect").val();
			var sMinute = $("#scheduleMinuteSelect").val();
			var x= "no";
			var y = "no";
			var z = "no";
			var fHour = parseInt(eHour) + parseInt(sHour);
			var fMinute = parseInt(sMinute) + parseInt(eMinute);
			/* if(fMinute >= 60){
				
				eMinute = fMinute - 60;
				z="yes";
			}else{
				eMinute= fMinute;
			} */
			
			if(z == "yes"){
				fHour = fHour+1;
			}
			
			/* if(fHour >= 24){
				eHour = fHour - 24;
				x= "yes";
			}else{
				eHour = fHour;
			} */

			
			//Handling dates
			var sDates = $(".dateSelect:checked").filter(':not(:disabled)');
			var esDates = "";
			for(var i = 0; i < sDates.length; i++){
				var sDate = $(sDates[i]).val();
				
				if((x == "yes") && (sDate != "*")){
					sDate = parseInt(sDate) + 1;
				}
				if((sDate >= 31) && (x == "yes")){
					sDate = 1;
					y = "yes";
				}
				
				if(i != 0){
					
					esDates += ",";
				}
				
				esDates += sDate;
				
				
			}
			
			var sMonths = $(".monthSelect:checked").filter(':not(:disabled)');
			var seMonths = "";
			for(var i = 0; i < sMonths.length; i++){
				var ssMonth = $(sMonths[i]).val();
				if((y == "yes") && (ssMonth != "*")){
					ssMonth = parseInt(ssMonth) + 1;
				}
				
				if((ssMonth >= 11) && (y=="yes")){
					ssMonth = 0;
				}
				if(i != 0){
					seMonths += ",";
				}
				seMonths += ssMonth;
			}
			
			var sDays = $(".daySelect:checked").filter(':not(:disabled)');
			var seDays = "";
			for(var i = 0; i < sDays.length; i++){
				var seDay = $(sDays[i]).val();
				if((x == "yes") && (seDay != "*")){
					seDay = parseInt(seDay) + 1;
				}
				
				if(seDay == 6){
					seDay = 0;
				}
				if(i != 0){
					seDays += ",";
				}
				seDays += seDay;
			}		
			
			var timeExpression = eMinute + " " + eHour  + " " + esDates + " " + seMonths + " " + seDays;;
			$("#endScheduleTimeExpression").val(timeExpression);
		};

		
		
		// Updating the end schedule details.
		var endSheduleTime = "<s:property value="endSchedule.scheduleTime"/>";
		var split = endSheduleTime.split(" ");
		var eMinute = parseInt(split[0]);
		var eHour = parseInt(split[1]);
		var sHour = parseInt($("#scheduleHourSelect").val());
		var sMinute = parseInt($("#scheduleMinuteSelect").val());
		var fHour = sHour + eHour;
		/*  alert("Updating the end schedule details");
		alert(endSheduleTime);
		 alert("s hour "+sHour);
		alert("End hour "+eHour);
		alert("f hour"+fHour); */  
		
		
		/* var d = new Date();
		d.setHours(sHour);
		d.setMinutes(sMinute);
		var d1 = new Date();
		
		
		if((fHour >= 24) || (eHour < sHour)){
			//alert("if block");
			d1.setDate(d1.getDate() + 1);
		}
		d1.setHours(eHour);
		d1.setMinutes(eMinute);
		
	    var diff = d1 - d;
	    eHour = parseInt((diff/(1000*60*60))%24);
	    eMinute = parseInt((diff/(1000*60))%60); */
		//alert("end hour after update "+eHour);
		$("#scheduleHourSelectEnd").val(eHour);
		$("#scheduleMinuteSelectEnd").val(eMinute);
       
		// Updating end action devices
		var endScheduleDetails = {
				"actions":{
				<s:iterator value="endSchedule.scheduleActions" var="scheduleEndAction" status="scheduleEndActionStatus">
					"<s:property value="#scheduleEndAction.id"/>":{
						"deviceId":"<s:property value="#scheduleEndAction.device.id"/>",
						"actionId":"<s:property value="#scheduleEndAction.actionType.id"/>",
						"level":"<s:property value="#scheduleEndAction.level"/>"
					}<s:if test="#scheduleEndActionStatus.last != true">,</s:if>
				</s:iterator>
				}
			};
		var eActions = endScheduleDetails["actions"];
		for(var aId in eActions){
			var eAction = eActions[aId];
			var alDeviceId = eAction["deviceId"];
			var alActionId = eAction["actionId"];
			var splited = eAction["level"].split(":");
			var level = splited[0];
			var cRow = $("#defineEndActionTable").find('tr:last');
			var alDeviceSelect = cRow.find('.selectActionDeviceEnd');
			alDeviceSelect.val(alDeviceId);
			alDeviceSelect.change();
			var alActionSelect = cRow.find('.selectActionNameEnd');
			alActionSelect.val(alActionId);
			alActionSelect.change();
			var alActionValueText = cRow.find('.actionvalueclassEnd');
			alActionValueText.val(level);
			alActionValueText.keyup();
			
			if(splited.length == 2)
			{
				//alert(splited[1]);
				var AcModeSelect = cRow.find('.FanModevalue');
				AcModeSelect.val(splited[1]);
				AcModeSelect.change();
			}
			else
			{
			var AcModeSelect = cRow.find('.FanModevalue');
				AcModeSelect.val(splited[0]);
				AcModeSelect.change();
			}
		}
		// Updating the boxes with current values.
		var scheduleDetails = {
			"actions":{
			<s:iterator value="schedule.scheduleActions" var="scheduleAction" status="scheduleActionStatus">
				"<s:property value="#scheduleAction.id"/>":{
					"deviceId":"<s:property value="#scheduleAction.device.id"/>",
					"actionId":"<s:property value="#scheduleAction.actionType.id"/>",
					"level":"<s:property value="#scheduleAction.level"/>"
				}<s:if test="#scheduleActionStatus.last != true">,</s:if>
			</s:iterator>
			}
		};
		var cActions = scheduleDetails["actions"];
		for(var aId in cActions){
			var cAction = cActions[aId];
			var alDeviceId = cAction["deviceId"];
			var alActionId = cAction["actionId"];
			var splited = cAction["level"].split(":");
			var level = splited[0];
			var cRow = $("#defineActionTable").find('tr:last');
			var alDeviceSelect = cRow.find('.selectActionDevice');
			alDeviceSelect.val(alDeviceId);
			alDeviceSelect.change();
			var alActionSelect = cRow.find('.selectActionName');
			alActionSelect.val(alActionId);
			alActionSelect.change();
			var alActionValueText = cRow.find('.actionvalueclass');
			alActionValueText.val(level);
			alActionValueText.keyup();
			
			if(splited.length == 2)
			{
				//alert(splited[1]);
				var AcModeSelect = cRow.find('.FanModevalue');
				AcModeSelect.val(splited[1]);
				AcModeSelect.change();
			}
			else
			{
			var AcModeSelect = cRow.find('.FanModevalue');
				AcModeSelect.val(splited[0]);
				AcModeSelect.change();
			}
		}

		// Show hide Weekly/Monthly Selection.
		$(".toggletimeselect").die("click");
		$(".toggletimeselect").live("click", function(){
			var selectedOption = $(this).val();
			if(selectedOption == "month"){
				$(".weeklyselectrow").hide();
				$(".monthlyselectrow").show();
				$(".everyDaySelect").attr("checked","checked");
				$(".everyDaySelect").change();
			}
			if(selectedOption == "week"){
				$(".monthlyselectrow").hide();
				$(".weeklyselectrow").show();
				$(".everyMonthSelect").attr("checked","checked");
				$(".everyMonthSelect").change();
				$(".everyDateSelect").attr("checked","checked");
				$(".everyDateSelect").change();
			}
			return true;
		});
		if($("#dayexpressionholder").val() == "*"){
			$('.toggletimeselect[value="month"]').click();
		}
		if($("#dateexpressionholder").val() == "*" && $("#monthexpressionholder").val() == "*"){
			$('.toggletimeselect[value="week"]').click();
		}
	});
	
$("#special1").live('click', function(){
  		
  		if($(this).attr('checked')){
  			$("#endSchedule").show();
  			$(".actionvalueclassEnd").addClass("required");
  			
  		}else{
  			$("#endSchedule").hide();
  			$("#defineActionsHiddenEnd").val("");
  			$('#scheduleMinuteSelectEnd').val('0');
  			$('#scheduleHourSelectEnd').val('0');
  			$(".actionvalueclassEnd").removeClass("required");
  			
  		}  		
  		});
	
	
	
	
</script>
<br/>
<div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form id="scheduleForm" action="#" method="POST" cssClass="ajaxinlinepopupform">
 <table>
 	<tr>
 		<!-- <td>Select Gateway:</td> -->
 		<td>
			<select id="gatewayselect" name="schedule.gateWay.id"></select>
 		</td>
 	</tr>
 	<tr>
 		<td><s:text name="setup.schedules.name" /></td>
 		<td>
			<s:hidden name="schedule.id"></s:hidden>
			<s:hidden name="endSchedule.id" id="eSchedule"></s:hidden>
			<s:hidden name="schedule.endSchedule"></s:hidden>
			<s:textfield name="schedule.name" maxlength='35' theme="simple" cssClass="required  editdisplayStar"></s:textfield>
 		</td>
 	</tr>
 	<tr>
 		<td>
 			<s:text name="setup.schedules.description" />
 		</td>
 		<td>
			<s:textarea  name="schedule.description" maxlength="120" cols="18" rows="1" theme="simple" cssClass="utf8alphanumeric"></s:textarea>
 		</td>
 	</tr>
 </table>
<div id="popwarning" class="popwarning"></div>
<input type="radio" value="week" class="toggletimeselect" name="toggletimeselect"/> <s:text name="setup.schedules.weekly" />
<input type="radio" value="month" class="toggletimeselect" name="toggletimeselect"/> <s:text name="setup.schedules.monthly" />
<br/>
<span class="actionheader"><s:text name="setup.schedules.when" /></span>
<div id="expressionsection" class="expressionsection">
	<table>
		<tr>
			<td>
				<s:text name="setup.schedules.select.h" />
				<select id="scheduleHourSelect">
				    <option value="0">00</option>
				    <option value="1">01</option>
				    <option value="2">02</option>
				    <option value="3">03</option>
				    <option value="4">04</option>
				    <option value="5">05</option>
				    <option value="6">06</option>
				    <option value="7">07</option>
				    <option value="8">08</option>
				    <option value="9">09</option>
				    <option value="10">10</option>
				    <option value="11">11</option>
				    <option value="12">12</option>
				    <option value="13">13</option>
				    <option value="14">14</option>
				    <option value="15">15</option>
				    <option value="16">16</option>
				    <option value="17">17</option>
				    <option value="18">18</option>
				    <option value="19">19</option>
				    <option value="20">20</option>
				    <option value="21">21</option>
				    <option value="22">22</option>
				    <option value="23">23</option>
				</select>
				:
				<s:text name="setup.schedules.select.m" />
				<select id="scheduleMinuteSelect">
				    <option value="0">00</option>
				    <option value="1">01</option>
				    <option value="2">02</option>
				    <option value="3">03</option>
				    <option value="4">04</option>
				    <option value="5">05</option>
				    <option value="6">06</option>
				    <option value="7">07</option>
				    <option value="8">08</option>
				    <option value="9">09</option>
				    <option value="10">10</option>
				    <option value="11">11</option>
				    <option value="12">12</option>
				    <option value="13">13</option>
				    <option value="14">14</option>
				    <option value="15">15</option>
				    <option value="16">16</option>
				    <option value="17">17</option>
				    <option value="18">18</option>
				    <option value="19">19</option>
				    <option value="20">20</option>
				    <option value="21">21</option>
				    <option value="22">22</option>
				    <option value="23">23</option>
				    <option value="24">24</option>
				    <option value="25">25</option>
				    <option value="26">26</option>
				    <option value="27">27</option>
				    <option value="28">28</option>
				    <option value="29">29</option>
				    <option value="30">30</option>
				    <option value="31">31</option>
				    <option value="31">31</option>
				    <option value="32">32</option>
				    <option value="33">33</option>
				    <option value="34">34</option>
				    <option value="35">35</option>
				    <option value="36">36</option>
				    <option value="37">37</option>
				    <option value="38">38</option>
				    <option value="39">39</option>
				    <option value="40">40</option>
				    <option value="41">41</option>
				    <option value="42">42</option>
				    <option value="43">43</option>
				    <option value="44">44</option>
				    <option value="45">45</option>
				    <option value="46">46</option>
				    <option value="47">47</option>
				    <option value="48">48</option>
				    <option value="49">49</option>
				    <option value="50">50</option>
				    <option value="51">51</option>
				    <option value="52">52</option>
				    <option value="53">53</option>
				    <option value="54">54</option>
				    <option value="55">55</option>
				    <option value="56">56</option>
				    <option value="57">57</option>
				    <option value="58">58</option>
				    <option value="59">59</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr class="weeklyselectrow">
			<td>
				<input type="hidden" id="dayexpressionholder" class="required displayStarSelect" value=""/>
			</td>
		</tr>
		<tr class="weeklyselectrow">
			<td>
				<s:text name="setup.schedules.everyday" />&nbsp;&nbsp;&nbsp; <input type="checkbox" class="everyDaySelect daySelect" value="*" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="setup.schedules.sun" /> <input type="checkbox" class="daySelect" value="0" />
				<s:text name="setup.schedules.mon" /> <input type="checkbox" class="daySelect" value="1" />
				<s:text name="setup.schedules.tue" /> <input type="checkbox" class="daySelect" value="2" />
				<s:text name="setup.schedules.wed" /> <input type="checkbox" class="daySelect" value="3" />
				<s:text name="setup.schedules.thu" /> <input type="checkbox" class="daySelect" value="4" />
				<s:text name="setup.schedules.fri" /> <input type="checkbox" class="daySelect" value="5" />
				<s:text name="setup.schedules.sat" /> <input type="checkbox" class="daySelect" value="6" />
			</td>
		</tr>
		<tr class="weeklyselectrow">
			<td>&nbsp;</td>
		</tr>
		<tr class="monthlyselectrow">
			<td>
				<input type="hidden" id="monthexpressionholder" class="required displayStarSelect" value=""/>
			</td>
		</tr>
		<tr class="monthlyselectrow">
			<td>
				<s:text name="setup.schedules.everymonth" /><input type="checkbox" class="everyMonthSelect monthSelect" value="*" /> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="setup.schedules.jan" /> <input type="checkbox" class="monthSelect" value="0" />
				<s:text name="setup.schedules.feb" /> <input type="checkbox" class="monthSelect" value="1" />
				<s:text name="setup.schedules.mar" /> <input type="checkbox" class="monthSelect" value="2" />
				<s:text name="setup.schedules.apr" /> <input type="checkbox" class="monthSelect" value="3" />
				<s:text name="setup.schedules.may" /> <input type="checkbox" class="monthSelect" value="4" />
				<s:text name="setup.schedules.jun" /> <input type="checkbox" class="monthSelect" value="5" />
				<br/>
				    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="setup.schedules.jul" /> <input type="checkbox" class="monthSelect" value="6" />
				<s:text name="setup.schedules.aug" /> <input type="checkbox" class="monthSelect" value="7" />
				<s:text name="setup.schedules.sep" /> <input type="checkbox" class="monthSelect" value="8" />
				<s:text name="setup.schedules.oct" /> <input type="checkbox" class="monthSelect" value="9" />
				<s:text name="setup.schedules.nov" /> <input type="checkbox" class="monthSelect" value="10" />
				<s:text name="setup.schedules.dec" /> <input type="checkbox" class="monthSelect" value="11" />
			</td>
		</tr>
		<tr class="monthlyselectrow">
			<td>&nbsp;</td>
		</tr>
		<tr class="monthlyselectrow">
			<td>
				<input type="hidden" id="dateexpressionholder" class="required displayStarSelect" value=""/>
			</td>
		</tr>
		<tr class="monthlyselectrow">
			<td>
				<s:text name="setup.schedules.everydate" />&nbsp;&nbsp;<input type="checkbox" class="everyDateSelect dateSelect" value="*" /> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				1 <input type="checkbox" class="dateSelect" value="1" />&nbsp;&nbsp;&nbsp;&nbsp;
				2 <input type="checkbox" class="dateSelect" value="2" />&nbsp;&nbsp;&nbsp;&nbsp;
				3 <input type="checkbox" class="dateSelect" value="3" />&nbsp;&nbsp;&nbsp;&nbsp;
				4 <input type="checkbox" class="dateSelect" value="4" />&nbsp;&nbsp;&nbsp;&nbsp;
				5 <input type="checkbox" class="dateSelect" value="5" />&nbsp;&nbsp;&nbsp;&nbsp;
				6 <input type="checkbox" class="dateSelect" value="6" />&nbsp;&nbsp;&nbsp;&nbsp;
				7 <input type="checkbox" class="dateSelect" value="7" />&nbsp;&nbsp;&nbsp;&nbsp;
				8 <input type="checkbox" class="dateSelect" value="8" />&nbsp;&nbsp;&nbsp;
				<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				9 <input type="checkbox" class="dateSelect" value="9" />&nbsp;&nbsp;
				10 <input type="checkbox" class="dateSelect" value="10" />&nbsp;&nbsp;
				11 <input type="checkbox" class="dateSelect" value="11" />&nbsp;&nbsp;
				12 <input type="checkbox" class="dateSelect" value="12" />&nbsp;&nbsp;
				13 <input type="checkbox" class="dateSelect" value="13" />&nbsp;&nbsp;
				14 <input type="checkbox" class="dateSelect" value="14" />&nbsp;&nbsp;
				15 <input type="checkbox" class="dateSelect" value="15" />&nbsp;&nbsp;
				16 <input type="checkbox" class="dateSelect" value="16" />&nbsp;&nbsp;
				<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				17 <input type="checkbox" class="dateSelect" value="17" />&nbsp;&nbsp;
				18 <input type="checkbox" class="dateSelect" value="18" />&nbsp;&nbsp;
				19 <input type="checkbox" class="dateSelect" value="19" />&nbsp;&nbsp;
				20 <input type="checkbox" class="dateSelect" value="20" />&nbsp;&nbsp;
				21 <input type="checkbox" class="dateSelect" value="21" />&nbsp;&nbsp;
				22 <input type="checkbox" class="dateSelect" value="22" />&nbsp;&nbsp;
				23 <input type="checkbox" class="dateSelect" value="23" />&nbsp;&nbsp;
				24 <input type="checkbox" class="dateSelect" value="24" />&nbsp;&nbsp;
				<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				25 <input type="checkbox" class="dateSelect" value="25" />&nbsp;&nbsp;
				26 <input type="checkbox" class="dateSelect" value="26" />&nbsp;&nbsp;
				27 <input type="checkbox" class="dateSelect" value="27" />&nbsp;&nbsp;
				28 <input type="checkbox" class="dateSelect" value="28" />&nbsp;&nbsp;
				29 <input type="checkbox" class="dateSelect" value="29" />&nbsp;&nbsp;
				30 <input type="checkbox" class="dateSelect" value="30" />&nbsp;&nbsp;
				31 <input type="checkbox" class="dateSelect" value="31" />&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</div>
<span class="actionheader"><s:text name="setup.schedules.what" /><input type="hidden" id="defineActionsHidden" class="required displayStarHeader"/></span>
<div id="deviceactionsection" class="deviceactionsection">
	<table id="defineActionTable">
	</table>
</div>

<p style="color:blue;">___________________________________________</p>
<br>

<td>
<s:text name="End of Schedule" /> <s:checkbox theme="simple" id="special1" name="endAction" />
</td>
<br>
<s:hidden name="endSchedule.scheduleTime" id="endScheduleTimeExpression"></s:hidden>
<div id="endSchedule" class="endSchedule">

	<table>
		<tr>
			<td>
				<s:text name="setup.schedules.select.h" />
				
				<select id="scheduleHourSelectEnd">
				    <option value="0">00</option>
				    <option value="1">01</option>
				    <option value="2">02</option>
				    <option value="3">03</option>
				    <option value="4">04</option>
				    <option value="5">05</option>
				    <option value="6">06</option>
				    <option value="7">07</option>
				    <option value="8">08</option>
				    <option value="9">09</option>
				    <option value="10">10</option>
				    <option value="11">11</option>
				    <option value="12">12</option>
				    <option value="13">13</option>
				    <option value="14">14</option>
				    <option value="15">15</option>
				    <option value="16">16</option>
				    <option value="17">17</option>
				    <option value="18">18</option>
				    <option value="19">19</option>
				    <option value="20">20</option>
				    <option value="21">21</option>
				    <option value="22">22</option>
				    <option value="23">23</option>
				</select>
				
				
				<s:text name="setup.schedules.select.m" />
				<select id="scheduleMinuteSelectEnd">
				   <option value="0">00</option>
				    <option value="1">01</option>
				    <option value="2">02</option>
				    <option value="3">03</option>
				    <option value="4">04</option>
				    <option value="5">05</option>
				    <option value="6">06</option>
				    <option value="7">07</option>
				    <option value="8">08</option>
				    <option value="9">09</option>
				    <option value="10">10</option>
				    <option value="11">11</option>
				    <option value="12">12</option>
				    <option value="13">13</option>
				    <option value="14">14</option>
				    <option value="15">15</option>
				    <option value="16">16</option>
				    <option value="17">17</option>
				    <option value="18">18</option>
				    <option value="19">19</option>
				    <option value="20">20</option>
				    <option value="21">21</option>
				    <option value="22">22</option>
				    <option value="23">23</option>
				    <option value="24">24</option>
				    <option value="25">25</option>
				    <option value="26">26</option>
				    <option value="27">27</option>
				    <option value="28">28</option>
				    <option value="29">29</option>
				    <option value="30">30</option>
				    <option value="31">31</option>
				    <option value="32">32</option>
				    <option value="33">33</option>
				    <option value="34">34</option>
				    <option value="35">35</option>
				    <option value="36">36</option>
				    <option value="37">37</option>
				    <option value="38">38</option>
				    <option value="39">39</option>
				    <option value="40">40</option>
				    <option value="41">41</option>
				    <option value="42">42</option>
				    <option value="43">43</option>
				    <option value="44">44</option>
				    <option value="45">45</option>
				    <option value="46">46</option>
				    <option value="47">47</option>
				    <option value="48">48</option>
				    <option value="49">49</option>
				    <option value="50">50</option>
				    <option value="51">51</option>
				    <option value="52">52</option>
				    <option value="53">53</option>
				    <option value="54">54</option>
				    <option value="55">55</option>
				    <option value="56">56</option>
				    <option value="57">57</option>
				    <option value="58">58</option>
				    <option value="59">59</option>
				    
				</select>
			</td>
		</tr>
		</table>
		
				<br>
		
		
		
<span class="actionheader"><s:text name="setup.schedules.end" />&nbsp&nbsp<input type="hidden" id="defineActionsHiddenEnd" class="displayStarHeader"/></span>

<!-- <input type="hidden" id="updateNeighborCountEnd" value='0'/>
<br><br>
 -->

<div id="deviceendactionsection" class="deviceendactionsection">
	<table id="defineEndActionTable">
	</table>
</div><br><br>


</div>




<s:if test="userNotificationProfiles.size >0">
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
<br/>
<input type="hidden" name="schedule.scheduleTime" id="scheduleTimeExpression"/>
<input type="submit" onClick="if(validateActions() == true){$('#scheduleForm').attr('action','updateSchedule.action');}" value="<s:text name='general.save' />" id="saveSchedule"/>
</s:form>