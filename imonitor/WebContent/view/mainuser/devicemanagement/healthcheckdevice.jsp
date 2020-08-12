<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
String applicationName = request.getContextPath();
%>
<script type="text/javascript" src="<%=applicationName %>/resources/js/com.imonitorsolutions.rss.js"></script>

<s:form action="updatehealthcheckModel.action" method="post" cssClass="ajaxinlinepopupform" id="updateCurtainModel">
<span class="actionheader">	<s:text name="home.msg.0015" />&nbsp;<input type="hidden" id="validateModel" class="required displayStarHeader"/></span>
	<table>
       <s:hidden name="device.generatedDeviceId"></s:hidden>
	<s:hidden name="device.gateWay.macId"></s:hidden>
	
	<tr><td><s:text name="setup.devices.model.selectmodel" /></td><td><s:select theme="simple" name="device.make.id" id="hiddenname"  list="makes" listKey="id" listValue="name" headerKey="0" headerValue="%{getText('home.msg.0015')}" onchange="javascript: RowVisibility(this.options[this.selectedIndex])"></s:select></td></tr>
	<tr id='acselectrow'><td><s:text name="setup.devices.model.select" /></td><td><s:select theme="simple" name="acdevice.generatedDeviceId" list="devices" listKey="generatedDeviceId" listValue="friendlyName" headerKey="0" headerValue="%{getText('home.msg.0016')}"></s:select></td></tr>
	</table>
<br>
<br>
 		<span class="actionheader">	<s:text name="setup.devices.msg.0007" /></span>
 	<br>
 	<s:radio theme="simple" name="selected" list="#{'enable':getText('setup.devices.msg.enable')}" cssClass="togglehealthmodelselect"/> 
<s:radio theme="simple" name="selected" list="#{'disable':getText('setup.devices.msg.Disable')}" cssClass="togglehealthmodelselect"/> <br />
	<!-- <input type="radio" name="healthcheckschedule"  value="enable" class="togglehealthmodelselect" /> <s:text name="setup.devices.msg.enable" />
       <input type="radio" name="togglehealthmodelselect" value="disable" class="togglehealthmodelselect" /> <s:text name="setup.devices.msg.Disable" />	-->

<br><br>
<table>
<div class="enableselectedrow">	
<tr class="enableselectedrow">
<td><span class="actionheader"><s:text name="setup.schedules.when" /></span></td></tr> 	
<tr class="enableselectedrow"><td>
<div id="popwarning" class="popwarning"></div>
<input type="radio" value="week" class="toggletimeselect" name="toggletimeselect"/> <s:text name="setup.schedules.weekly" />
<input type="radio" value="month" class="toggletimeselect" name="toggletimeselect"/> <s:text name="setup.schedules.monthly" />
</td></tr>
<div id="expressionsection" class="expressionsection">
		<tr class="enableselectedrow">
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
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				1 <input type="checkbox" class="dateSelect" value="1" />&nbsp;&nbsp;&nbsp;
				2 <input type="checkbox" class="dateSelect" value="2" />&nbsp;&nbsp;&nbsp;
				3 <input type="checkbox" class="dateSelect" value="3" />&nbsp;&nbsp;&nbsp;
				4 <input type="checkbox" class="dateSelect" value="4" />&nbsp;&nbsp;&nbsp;
				5 <input type="checkbox" class="dateSelect" value="5" />&nbsp;&nbsp;&nbsp;
				6 <input type="checkbox" class="dateSelect" value="6" />&nbsp;&nbsp;&nbsp;
				7 <input type="checkbox" class="dateSelect" value="7" />&nbsp;&nbsp;&nbsp;
				8 <input type="checkbox" class="dateSelect" value="8" />&nbsp;&nbsp;&nbsp;
				<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
<input type="hidden" name="schedule.scheduleTime" id="scheduleTimeExpression"/>
<tr><td><td><s:submit key="general.save"></s:submit></tr>
	
</s:form>
<script>
		makeDetailsMap = {
				<s:iterator value="makes" var="make" status="makeStatus">
					"<s:property value="#make.id"/>":{
					    "details":"<s:property value="#make.details"/>"
					  }<s:if test="#makeStatus.last != true">,</s:if> 
				</s:iterator>
				};	
		
$(document).ready(function() 
{
	var val=document.getElementById("hiddenname").value;
	if(val != 0)
	{
		$("#validateModel").val(val);
	}
	
	if(makeDetailsMap[val] != undefined && (makeDetailsMap[val]["details"].substr(0,1))==1)
	{
		$("#acselectrow").show();
	}
	else
	{
		$("#acselectrow").hide();
	}

    var cForm = $("#updateCurtainModel");
	Xpeditions.validateForm(cForm);
	var sheduleTime = "<s:property value="schedule.scheduleTime"/>";
	var isScheduleBlank = false;
	if(sheduleTime==[])
	{
		isScheduleBlank = true;
	}
	$(".togglehealthmodelselect").die("click");
	$(".togglehealthmodelselect").live("click", function()
	{
		var selectedOption1 = $(this).val();
        if(selectedOption1 == "enable")
		{
			$(".toggletimeselect").die("click");
			$(".toggletimeselect").live("click", function()
			{
				var selectedOption = $(this).val();
				if(selectedOption == "month")
				{
					$(".weeklyselectrow").hide();
					$(".monthlyselectrow").show();
					$(".everyDaySelect").attr("checked","checked");
					$(".everyDaySelect").change();
				}
				if(selectedOption == "week")
				{
					$(".monthlyselectrow").hide();
					$(".weeklyselectrow").show();
					$(".everyMonthSelect").attr("checked","checked");
					$(".everyMonthSelect").change();
					$(".everyDateSelect").attr("checked","checked");
					$(".everyDateSelect").change();
				}
				return true;
            }); 

			$(".enableselectedrow").show();
			$(".weeklyselectrow").show();
			
			if($("#dayexpressionholder").val() == "*")
			{
				$('.toggletimeselect[value="month"]').click();
            }
			if($("#dateexpressionholder").val() == "*" && $("#monthexpressionholder").val() == "*")
			{
				$('.toggletimeselect[value="week"]').click();
			}
			
        }
		else if(selectedOption1 == "disable")
		{
			$(".enableselectedrow").hide();
			$(".weeklyselectrow").hide();
			$(".monthlyselectrow").hide();
		}
		return true;
	});


	
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
				$(this).siblings().removeAttr("checked");
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
				$(this).siblings().removeAttr("checked");
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
				$(this).siblings().removeAttr("checked");
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
		$(".dateSelect").die("change");
		$(".dateSelect").live("change", handleDateSelectCheckBoxChange);
		
		$(".monthSelect").die("change");
		$(".monthSelect").live("change", handleMonthSelectCheckBoxChange);
		
		$(".daySelect").die("change");
		$(".daySelect").live("change", handleDaySelectCheckBoxChange);
		$("#scheduleHourSelect").unbind("change");
		$("#scheduleHourSelect").bind("change", handleScheduleHourSelectChange);
		$("#scheduleMinuteSelect").bind("change", handleScheduleMinuteSelectChange);
		
		var sheduleTime = "<s:property value="schedule.scheduleTime"/>";
		if(sheduleTime ==[])
		{
			sheduleTime="0 0 * * *";
		}
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
		// Updating the boxes with current values.
			if(isScheduleBlank)
			{
				$('.togglehealthmodelselect[value="disable"]').click();
			}
			else
			{
				$('.togglehealthmodelselect[value="enable"]').click();
			}
      	});


	function RowVisibility(selVal)
	{
		var val = selVal.value;
		if(val != 0)
		{
			$("#validateModel").val(val);
		}
		else
		{
			$("#validateModel").val("");
		}
	
		if((makeDetailsMap[selVal.value]["details"].substr(0,1))==1)        		
		{
			$("#acselectrow").show();
		}
		else
		{
			$("#acselectrow").hide();
		}
	};
</script>
		