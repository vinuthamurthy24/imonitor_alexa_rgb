<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>

 
<%
String applicationName = request.getContextPath();
%>

<div class="message">
<s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
		</div>

<html>
<head>
<title>iMonitor</title>
     
	    <link type="text/css" href="<%=applicationName %>/resources/css/style.css" rel="stylesheet" />
		<link rel="shortcut icon" href="<%=applicationName %>/resources/images/favicon.ico" type="image/-icon" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />	
	    <script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.5.1.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.js"></script>
		 <script type="text/javascript" src="<%=applicationName %>/resources/js/chili-1.7.pack.js"></script>
         <script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.cycle.all.js"></script>
         <script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.easing.1.3.js"></script>
		 <link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />
         <link type="text/css" href="<%=applicationName %>/resources/css/jq.css" rel="stylesheet" />
		 <link type="text/css" href="<%=applicationName %>/resources/css/cycle.css" rel="stylesheet" />

<script>


$(document).ready(function() {
	
	
	$('form').live('change', function() {
		Xpeditions.validateForm($(this));
	});
	$('form').live('keyup', function() {
		Xpeditions.validateForm($(this));
	});
	
	
	
	var cForm = $("#registerZingGtwyForm");
	Xpeditions.validateForm(cForm);
	
	
	$("#register").live('click', function(){
		
		/*  $("#instDialog").html('<img src="../resources/images/learn.png" "/>'+ 
	              '<table><tr><td> 1: Request end user to configure his original remote control to the operating mode (dry, heat '+
                  'or cool), target temperature, and other desired settings that he would like to learn</td></tr><tr></tr><tr></tr><tr></tr>'+
                  '<tr><td>2: Press the "OFF" or "STOP" button of his original remote control</td></tr><tr></tr><tr><tr><tr></tr>'+
                  '<tr><td>3: Request end user to "Press and HOLD" the "ON" or "START" button of your original remote control until the status LED of ZXT-120 starts flashing. ZXT-120 will flash the status LED'+
                  'twice if the operation is successful. It will flash the LED six times if it fails. Some remotecontrols have weak IR transmission signals. If it failed, we should try again by moving the'+
                   'original remote closer to the IR receiver of ZXT-120.</td></tr></table>'); */

$("#instDialog").dialog('open');
		
		
	});
	
	
});

var messageMap =
{
		"general.home":"<s:text name='general.home' />",
		"general.stay":"<s:text name='general.stay' />",
		"general.away":"<s:text name='general.away' />",
		"general.value":"<s:text name='general.value' />",
		"setup.devices.name":"<s:text name='setup.devices.name' />",
		"setup.devices.type":"<s:text name='setup.devices.type' />",
		"setup.devices.location":"<s:text name='setup.devices.location' />",
		"setup.devices.action":"<s:text name='setup.devices.action' />",
		"setup.devices.curtain.make":"<s:text name='setup.devices.curtain.make' />",
		"setup.devices.edit.title":"<s:text name='setup.devices.edit.title' />",
		"setup.devices.pir.title":"<s:text name='setup.devices.pir.title' />",
		"setup.devices.LCDremote.title":"<s:text name='setup.devices.LCDremote.title' />",
		"setup.devices.camera.title":"<s:text name='setup.devices.camera.title' />",
		"setup.devices.alarms.title":"<s:text name='setup.devices.alarms.title' />",
		"setup.rules.alert":"<s:text name='setup.rules.alert' />",
		"setup.rules.afterdelay":"<s:text name='setup.rules.afterdelay' />",
		"setup.rules.sends":"<s:text name='setup.rules.sends' />",
		"setup.rss.selectdevice":"<s:text name='setup.rss.selectdevice' />",
		"setup.rss.nodevice":"<s:text name='setup.rss.nodevice' />",
		"setup.toggle.rule":"<s:text name='setup.toggle.rule' />",
		"setup.toggle.schedule":"<s:text name='setup.toggle.schedule' />",
		"setup.toggle.scenario":"<s:text name='setup.toggle.scenario' />",
		"setup.toggle.step.3":"<s:text name='setup.toggle.step.3' />",
		"setup.toggle.step.4":"<s:text name='setup.toggle.step.4' />",
		"msg.ui.reboot":"<s:text name='msg.ui.reboot' />",
		"msg.ui.Userinitiatedfirmaware":"<s:text name='msg.ui.Userinitiatedfirmaware' />",
		"msg.ui.devicescan":"<s:text name='msg.ui.devicescan' />",
		"msg.ui.devicecurrentstatus":"<s:text name='msg.ui.devicecurrentstatus' />",
		"msg.ui.syncstatus":"<s:text name='msg.ui.syncstatus' />",
		"msg.ui.alarms":"<s:text name='msg.ui.alarms' />",
		"msg.ui.mmc":"<s:text name='msg.ui.mmc' />",
		"msg.ui.currentmode":"<s:text name='msg.ui.currentmode' />",
		"msg.ui.close":"<s:text name='msg.ui.close' />",
		"msg.ui.open":"<s:text name='msg.ui.open' />",
		"msg.ui.min":"<s:text name='msg.ui.min' />",
		"msg.ui.max":"<s:text name='msg.ui.max' />",
		"msg.ui.high":"<s:text name='msg.ui.high' />",
		"msg.ui.low":"<s:text name='msg.ui.low' />",
		"msg.ui.mid":"<s:text name='msg.ui.mid' />",
		"msg.ui.home.0001":"<s:text name='msg.ui.home.0001' />",
		"msg.ui.setup.0001":"<s:text name='msg.ui.setup.0001' />",
		"msg.ui.setup.0007":"<s:text name='msg.ui.setup.0007' />",
		"msg.ui.setup.0008":"<s:text name='msg.ui.setup.0008' />",
		"msg.ui.setup.0009":"<s:text name='msg.ui.setup.0009' />",
		"msg.ui.setup.0017":"<s:text name='msg.ui.setup.0017' />",
		"msg.ui.setup.0010":"<s:text name='msg.ui.setup.0010' />",
		"msg.ui.setup.0011":"<s:text name='msg.ui.setup.0011' />",
		"setup.devices.msg.preset.set7":"<s:text name='setup.devices.msg.preset.set7' />",
		"setup.devices.msg.preset.set9":"<s:text name='setup.devices.msg.preset.set9' />",
		"setup.devices.msg.preset.set10":"<s:text name='setup.devices.msg.preset.set10' />",
		"msg.ui.setup.0012":"<s:text name='msg.ui.setup.0012' />",
		"msg.ui.setup.0013":"<s:text name='msg.ui.setup.0013' />",
		"msg.ui.setup.0002":"<s:text name='msg.ui.setup.0002' />",
		"msg.ui.general.0001":"<s:text name='msg.ui.general.0001' />",
		"msg.ui.general.0001a":"<s:text name='msg.ui.general.0001a' />",
		"msg.ui.general.0002":"<s:text name='msg.ui.general.0002' />",
		"msg.ui.general.0002a":"<s:text name='msg.ui.general.0002a' />",
		"msg.ui.general.0002b":"<s:text name='msg.ui.general.0002b' />",
		"msg.ui.general.0003":"<s:text name='msg.ui.general.0003' />",
		"msg.ui.general.0003a":"<s:text name='msg.ui.general.0003a' />",
		"msg.ui.general.0003b":"<s:text name='msg.ui.general.0003b' />",
		"msg.ui.general.0004":"<s:text name='msg.ui.general.0004' />",
		"msg.ui.general.0005":"<s:text name='msg.ui.general.0005' />",
		"msg.ui.general.0006":"<s:text name='msg.ui.general.0006' />",
		"msg.ui.general.0007":"<s:text name='msg.ui.general.0007' />",
		"msg.ui.general.0008":"<s:text name='msg.ui.general.0008' />",
		"msg.ui.general.0009":"<s:text name='msg.ui.general.0009' />",
		"msg.ui.general.0010":"<s:text name='msg.ui.general.0010' />",
		"msg.ui.general.0011":"<s:text name='msg.ui.general.0011' />",
		"msg.ui.general.0012":"<s:text name='msg.ui.general.0012' />",
		"msg.ui.general.0013":"<s:text name='msg.ui.general.0013' />",
		"msg.ui.general.0014":"<s:text name='msg.ui.general.0014' />",
		"msg.ui.general.0015":"<s:text name='msg.ui.general.0015' />",
		"msg.ui.general.0016":"<s:text name='msg.ui.general.0016' />",
		"IP_CAMERA":"<s:text name='IP_CAMERA' />",
		"Z_WAVE_SWITCH":"<s:text name='Z_WAVE_SWITCH' />",
		"Z_WAVE_DIMMER":"<s:text name='Z_WAVE_DIMMER' />",
		"Z_WAVE_DOOR_LOCK":"<s:text name='Z_WAVE_DOOR_LOCK' />",
		"Z_WAVE_DOOR_SENSOR":"<s:text name='Z_WAVE_DOOR_SENSOR' />",
		"Z_WAVE_LUX_SENSOR":"<s:text name='Z_WAVE_LUX_SENSOR' />",
		"Z_WAVE_PIR_SENSOR":"<s:text name='Z_WAVE_PIR_SENSOR' />",
		"Z_WAVE_MINIMOTE":"<s:text name='Z_WAVE_MINIMOTE' />",
		"Z_WAVE_LCD_REMOTE":"<s:text name='Z_WAVE_LCD_REMOTE' />",
		"Z_WAVE_HEALTH_MONITOR":"<s:text name='Z_WAVE_HEALTH_MONITOR' />",
		"Z_WAVE_MULTI_SENSOR":"<s:text name='Z_WAVE_MULTI_SENSOR' />",
		"Z_WAVE_SIREN":"<s:text name='Z_WAVE_SIREN' />",
		"Z_WAVE_AC_EXTENDER":"<s:text name='Z_WAVE_AC_EXTENDER' />",
		"Z_WAVE_MOTOR_CONTROLLER":"<s:text name='Z_WAVE_MOTOR_CONTROLLER' />",
		"Z_WAVE_SHOCK_DETECTOR":"<s:text name='Z_WAVE_SHOCK_DETECTOR' />",
		"Z_WAVE_SMOKE_SENSOR":"<s:text name='Z_WAVE_SMOKE_SENSOR' />",
		"Z_WAVE_AV_BLASTER":"<s:text name='Z_WAVE_AV_BLASTER' />",
		"UNSUPPORTED":"<s:text name='UNSUPPORTED' />",
		"IMVG_UP":"<s:text name='IMVG_UP' />",
		"MOTION_DETECTED":"<s:text name='MOTION_DETECTED' />",
		"NOMOTION_DETECTED":"<s:text name='NOMOTION_DETECTED' />",
		"SENSED_LIGHT_INTENSITY":"<s:text name='SENSED_LIGHT_INTENSITY' />",
		"DEVICE_DOWN":"<s:text name='DEVICE_DOWN' />",
		"DEVICE_UP":"<s:text name='DEVICE_UP' />",
		"MONITOR_DEVICE_HEALTH_SUCCESS":"<s:text name='MONITOR_DEVICE_HEALTH_SUCCESS' />",
		"BATTERY_STATUS":"<s:text name='BATTERY_STATUS' />",
		"DOOR_LOCK_CLOSE":"<s:text name='DOOR_LOCK_CLOSE' />",
		"DOOR_LOCK_OPEN":"<s:text name='DOOR_LOCK_OPEN' />",
		"DOOR_OPEN":"<s:text name='DOOR_OPEN' />",
		"DOOR_CLOSE":"<s:text name='DOOR_CLOSE' />",
		"STREAM_FTP_SUCCESS":"<s:text name='STREAM_FTP_SUCCESS' />",
		"STREAM_FTP_FAILED":"<s:text name='STREAM_FTP_FAILED' />",
		"IMAGE_FTP_SUCCESS":"<s:text name='IMAGE_FTP_SUCCESS' />",
		"IMAGE_FTP_FAILED":"<s:text name='IMAGE_FTP_FAILED' />",
		"PANIC_SITUATION":"<s:text name='PANIC_SITUATION' />",
		"DEVICE_STATE_CHANGED":"<s:text name='DEVICE_STATE_CHANGED' />",
		"CURTAIN_OPEN":"<s:text name='CURTAIN_OPEN' />",
		"CURTAIN_CLOSE":"<s:text name='CURTAIN_CLOSE' />",
		"REQUEST_NEIGHBOR_UPDATE_STARTED":"<s:text name='REQUEST_NEIGHBOR_UPDATE_STARTED' />",
		"REQUEST_NEIGHBOR_UPDATE_DONE":"<s:text name='REQUEST_NEIGHBOR_UPDATE_DONE' />",
		"REQUEST_NEIGHBOR_UPDATE_FAILED":"<s:text name='REQUEST_NEIGHBOR_UPDATE_FAILED' />",
		"HOME_SUCCESS":"<s:text name='HOME_SUCCESS' />",
		"HOME_FAILURE":"<s:text name='HOME_FAILURE' />",
		"STAY_SUCCESS":"<s:text name='STAY_SUCCESS' />",
		"STAY_FAILURE":"<s:text name='STAY_FAILURE' />",
		"AWAY_SUCCESS":"<s:text name='AWAY_SUCCESS' />",
		"AWAY_FAILURE":"<s:text name='AWAY_FAILURE' />",
		"SHOCK_DETECTED":"<s:text name='SHOCK_DETECTED' />",
		"SHOCK_STOPPED":"<s:text name='SHOCK_STOPPED' />",
		"SMOKE_SENSED":"<s:text name='SMOKE_SENSED' />",
		"SMOKE_CLEARED":"<s:text name='SMOKE_CLEARED' />",
		"HMD_WARNING":"<s:text name='HMD_WARNING' />",
		"HMD_NORMAL":"<s:text name='HMD_NORMAL' />",
		"HMD_FAILURE":"<s:text name='HMD_FAILURE' />",
		"POWER_INFORMATION":"<s:text name='POWER_INFORMATION' />",
		"TEMPERATURE_CHANGE":"<s:text name='TEMPERATURE_CHANGE' />",
		"SENSED_PM_LEVEL":"<s:text name='SENSED_PM_LEVEL'/>",
		"SWITCH_OFF":"<s:text name='SWITCH_OFF' />",
		"SWITCH_ON":"<s:text name='SWITCH_ON' />",
		"DEACTIVATE_ALARM":"<s:text name='DEACTIVATE_ALARM' />",
		"ACTIVATE_ALARM":"<s:text name='ACTIVATE_ALARM' />",
		"START_RECORDING":"<s:text name='START_RECORDING' />",
		"CAPTURE_IMAGE":"<s:text name='CAPTURE_IMAGE' />",
		"PRESET":"<s:text name='PRESET' />",
		"ENABLE_CAMERA":"<s:text name='ENABLE_CAMERA' />",
		"DISABLE_CAMERA":"<s:text name='DISABLE_CAMERA' />",
		"DIMMER_CHANGE":"<s:text name='DIMMER_CHANGE' />",
		"GET_TEMPERATURE":"<s:text name='GET_TEMPERATURE' />",
		"AC_ON":"<s:text name='AC_ON' />",
		"AC_OFF":"<s:text name='AC_OFF' />",
		"E-Mail":"<s:text name='E-Mail' />",
		"SMS":"<s:text name='SMS' />",
		"AC_CONTROL":"<s:text name='AC_CONTROL' />",
		"AC_MODE":"<s:text name='AC_MODE' />",
		"HOME_SELECTED":"<s:text name='HOME_SELECTED' />",
		"STAY_SELECTED":"<s:text name='STAY_SELECTED' />",
		"AWAY_SELECTED":"<s:text name='AWAY_SELECTED' />",
		"LOCK":"<s:text name='LOCK' />",
		"UNLOCK":"<s:text name='UNLOCK' />",
		"setup.devices.switches.switchType":"<s:text name='setup.devices.switches.switchType' />",
		"setup.devices.RGB.switchType":"<s:text name='setup.devices.RGB.switchType' />",
		"setup.devices.SceneController.title":"<s:text name='setup.devices.SceneController.title' />",
		"msg.controller.rules.0019":"<s:text name='msg.controller.rules.0019' />",
		"REQUEST_CONFIGURATION_STARTED":"<s:text name='REQUEST_CONFIGURATION_STARTED' />",
		"REQUEST_CONFIGURATION_DONE":"<s:text name='REQUEST_CONFIGURATION_DONE' />",
		"REQUEST_CONFIGURATION_FAILED":"<s:text name='REQUEST_CONFIGURATION_FAILED' />",
		"REQUEST_ASSOCIATION_STARTED":"<s:text name='REQUEST_ASSOCIATION_STARTED' />",
		"REQUEST_ASSOCIATION_DONE":"<s:text name='REQUEST_ASSOCIATION_DONE' />",
		"REQUEST_ASSOCIATION_FAILED":"<s:text name='REQUEST_ASSOCIATION_FAILED' />",
		"home.msg.0017":"<s:text name='home.msg.0017' />",
		"home.msg.0009":"<s:text name='home.msg.0009' />",
		"home.msg.0018":"<s:text name='home.msg.0018' />",
		"home.msg.0019":"<s:text name='home.msg.0019' />",
		"home.ui.allon":"<s:text name='home.ui.allon' />",
		"home.ui.alloff":"<s:text name='home.ui.alloff' />",
		"Energy.Totaltab":"<s:text name='Energy.Totaltab' />",
		"Energy.Total":"<s:text name='Energy.Total' />",
		"Energy.up-todate-cast":"<s:text name='Energy.up-todate-cast' />",
		"Energy.up-todate-usage":"<s:text name='Energy.up-todate-usage' />",
		"Energy.target-cast":"<s:text name='Energy.target-cast' />",
		"Energy.Week":"<s:text name='Energy.Week' />",
		"Energy.Month":"<s:text name='Energy.Month' />",
		"Energy.Year":"<s:text name='Energy.Year' />",
		"Energy.Powerconsumption":"<s:text name='Energy.Powerconsumption' />",
		"POWER_CONSUMED":"<s:text name='POWER_CONSUMED' />",
		"Tooltip.Edit.healthcheckModel":"<s:text name='Tooltip.Edit.healthcheckModel' />",
        "setup.rules.time.start":"<s:text name='setup.rules.time.start' />",
		"setup.rules.time.end":"<s:text name='setup.rules.time.end' />",
		"setup.rules.time.hour":"<s:text name='setup.rules.time.hour' />",
		"setup.rules.time.min":"<s:text name='setup.rules.time.min' />",
		"Energy.TariffRate":"<s:text name='Energy.TariffRate' />",
		"Energy.to":"<s:text name='Energy.to' />",
		"Energy.Unit":"<s:text name='Energy.Unit' />",
		"Energy.slabrange":"<s:text name='Energy.slabrange' />",
		"Energy.Jan":"<s:text name='Energy.Jan' />",
		"Energy.Feb":"<s:text name='Energy.Feb' />",
		"Energy.March":"<s:text name='Energy.March' />",
		"Energy.April":"<s:text name='Energy.April' />",
		"Energy.May":"<s:text name='Energy.May' />",
		"Energy.June":"<s:text name='Energy.June' />",
		"Energy.July":"<s:text name='Energy.July' />",
		"Energy.Aug":"<s:text name='Energy.Aug' />",
		"Energy.Sep":"<s:text name='Energy.Sep' />",
		"Energy.Oct":"<s:text name='Energy.Oct' />",
		"Energy.Nov":"<s:text name='Energy.Nov' />",
		"Energy.Dec":"<s:text name='Energy.Dec' />",
		"Energy.TariffConfig":"<s:text name='Energy.TariffConfig' />",
		"Energy.Targetcost":"<s:text name='Energy.Targetcost' />",
		"Energy.Jann":"<s:text name='Energy.Jann' />",
		"Energy.Febb":"<s:text name='Energy.Febb' />",
		"Energy.Augg":"<s:text name='Energy.Augg' />",
		"Energy.Sepp":"<s:text name='Energy.Sepp' />",
		"Energy.Octt":"<s:text name='Energy.Octt' />",
		"Energy.Novv":"<s:text name='Energy.Novv' />",
		"Energy.Decc":"<s:text name='Energy.Decc' />",
		"Energy.targetcostmonth":"<s:text name='Energy.targetcostmonth' />",
		"PWR_LMT_REACHED":"<s:text name='PWR_LMT_REACHED' />",
		"PWR_LMT_WARNING":"<s:text name='PWR_LMT_WARNING' />",
		"Tooltip.Energy":"<s:text name='Tooltip.Energy' />",
		"Tooltip.weekdata":"<s:text name='Tooltip.weekdata' />",
		"Tooltip.monthdata":"<s:text name='Tooltip.monthdata' />",
		"Tooltip.yeardata":"<s:text name='Tooltip.yeardata' />",
		"Tooltip.tariff":"<s:text name='Tooltip.tariff' />",
		"Tooltip.targetcost":"<s:text name='Tooltip.targetcost' />",
		"Energy.targetcostmonth":"<s:text name='Energy.targetcostmonth'/>",
		"Tooltip.add":"<s:text name='Tooltip.add'/>",
		"Energy.month":"<s:text name='Energy.month'/>",
		"Tooltip.SelectAll":"<s:text name='Tooltip.SelectAll'/>",
		"Energy.up-to":"<s:text name='Energy.up-to' />",
		"Energy.InstantPowerconsumption":"<s:text name='Energy.InstantPowerconsumption' />",
         "NEW_ALARM":"<s:text name='NEW_ALARM'/>",
         "DEVICE_ON":"<s:text name='DEVICE_ON'/>",
         "DEVICE_OFF":"<s:text name='DEVICE_OFF'/>",
         "ZWAVE_CLAMP_SWITCH":"<s:text name='ZWAVE_CLAMP_SWITCH' />",
         "Z_WAVE_SLAVE":"<s:text name='Z_WAVE_SLAVE' />",
         "Z_WAVE_PM_SENSOR":"<s:text name='Z_WAVE_PM_SENSOR'/>",
         "NEIGHBOUR_UPDATE":"<s:text name='NEIGHBOUR_UPDATE' />",
         "DEV_ZWAVE_SCENE_CONTROLLER":"<s:text name='DEV_ZWAVE_SCENE_CONTROLLER' />",
         "setup.devices.SceneController.title":"<s:text name='setup.devices.SceneController.title' />",
         
        

};
function formatMessage(messageId)
{
	var result = messageMap[messageId];
	if(result == "undefined" || result == "")result = "Generic message";
	return result;
};
</script>

		 <style type="">
		 .division
		 {
		 line-height: 5px;
		 
		 }
		 
		 h1,h2{
		 font-family: 'trebuchet ms', verdana, arial;
		 }
		 
		/*  input {
		border: 2px solid black;
		border-radius : 2px;
		} */
		
		 .texts
		 {
		  font-family: 'trebuchet ms', verdana, arial;
		  font-size: 16px;
		  border-radius:8px;
		  background-color: #3a8fce;
		  border: none;
		  padding: 10px;
		  text-align: center;
		  text-decoration: none;
		  display: inline-block;
		  margin: 2px 0px 0px -82px;
		  cursor: pointer;
		  color: white;
		  width: 100px;
		 }
		 
	label {
			color: teal;
			 font-family: 'trebuchet ms', verdana, arial;
			font-size: 20px;
			margin-top: 2px;
			text-align: left;
			}
			
		 table {
			border: thick;	
			
			}
			
			.helpicon{
			width: 20px;
			height: 20px;
			margin-top:21px;
			margin-left:220px;
			position:absolute;
			cursor:help;
			}
			
			td{
			vertical-align: middle;
			text-align: left;
	/* 		width: 171px; */
			
			}
			
			td:nth-child(even) {
    				width: 171px;
					}
			
		.message{
					font-family: serif;
    				font-size: 32px;
   					 margin-left: 400px;
   					 margin-top: 9px;
   					 color: red;
					}
			
		 </style>
		 
</head>
<body>
<div class="header_container">
			<div class="header">
				<div class="logo">
					<a href="#"><img src="<%=applicationName %>/resources/images/logoBlack.png"
						alt="Applane" style=" width: 255px; height: 78px; margin-top: -32px; "/></a>
				</div>
				<h1 class="blue" style="font-size: 24px; float: right; margin: -58px 0px 0px 0px;"">MAKING HOMES INTELLIGENT</h1>
			</div>
		</div>
<div align="center" class="division">



    <s:form action="saveZingGateway.action" method="POST" id="registerZingGtwyForm" cssClass="ajaxinlineform" >
	<s:textfield name="gateWay.macId" label="GateWay Mac Id"  cssClass="macid required  ajaxchangelistener editdisplayStar"  validateurl="getGateWayByMacId.action"></s:textfield> 
	<img class="helpicon" title="Mac Id-->(00:c0:02:7b:7f:98)" src="/imonitor/resources/images/help.png" onmouseover="" onclick="alertFunction()">
	<s:textfield name="customer.customerId" label="Customer ID"  cssClass="required alphanumeric editdisplayStar"></s:textfield>
	<s:textfield name="user.userId" label="User ID" cssClass="alphanumeric required editdisplayStar"></s:textfield>
	<s:password name="user.password" label="Password" cssClass="required crosscheck confirmpass editdisplayStar  errorclass" commonclass="confirmpass"></s:password>
	<s:password label="Confirm Password" cssClass="required crosscheck confirmpass editdisplayStar  errorclass" commonclass="confirmpass"></s:password>
	<s:textfield name="customer.firstName" label="Firstname" cssClass="alphanumeric required editdisplayStar"></s:textfield>
	<s:textfield name="customer.middleName" label="MiddleName" cssClass="alphanumeric"></s:textfield>
	<s:textfield name="customer.lastName" label="LastName" cssClass="alphanumeric"></s:textfield>
	<s:textfield name="customer.place" label="Address" cssClass="required editdisplayStar" ></s:textfield>
	<s:textfield name="customer.post" label="Post" cssClass="required editdisplayStar" ></s:textfield>
	<s:textfield name="customer.city" label="City" cssClass="alphanumeric required editdisplayStar"></s:textfield>
	<%-- <s:textfield name="customer.province" label="State" cssClass="alphanumeric"></s:textfield> --%>
	
	<s:select name="customer.province"  label="State" cssClass="alphanumeric required editdisplayStar" list="#{'Select State':'Select State','Andaman and Nicobar Islands':'Andaman and Nicobar Islands','Andhra Pradesh':'Andhra Pradesh','Arunachal Pradesh':'Arunachal Pradesh','Assam':'Assam','Bihar':'Bihar',
						'Chhattisgarh':'Chhattisgarh','Chandigarh':'Chandigarh','Dadra and Nagar Haveli':'Dadra and Nagar Haveli','Daman and Diu':'Daman and Diu','Delhi':'Delhi',
						'Goa':'Goa','Gujarat':'Gujarat','Haryana':'Haryana','Himachal Pradesh':'Himachal Pradesh','Jammu and Kashmir':'Jammu and Kashmir','Jharkhand':'Jharkhand','Karnataka':'Karnataka','Kerala':'Kerala','Lakshadweep':'Lakshadweep','Madhya Pradesh':'Madhya Pradesh','Maharashtra':'Maharashtra',
						'Manipur':'Manipur','Meghalaya':'Meghalaya','Mizoram':'Mizoram','Nagaland':'Nagaland','Odisha':'Odisha','Puducherry':'Puducherry','Punjab':'Punjab','Rajasthan':'Rajasthan','Sikkim':'Sikkim',
						'Tamil Nadu':'Tamil Nadu','Telangana':'Telangana','Tripura':'Tripura','Uttarakhand':'Uttarakhand','Uttar Pradesh':'Uttar Pradesh','West Bengal':'West Bengal'}"></s:select>
	
	<s:textfield name="customer.pincode"  cssClass="number required editdisplayStar" label="PinCode" minvalue="100000" maxvalue="999999"></s:textfield>
	<s:textfield name="customer.email" label="Email ID" cssClass="required email editdisplayStar"></s:textfield>
	<s:textfield name="customer.mobile" label="Mobile Number" cssClass="required mobilenumber editdisplayStar"></s:textfield>

	
	<s:submit align="center" cssClass="texts" id="register" value="Register"></s:submit>
</s:form>
</div>
<div id="instDialog"></div>
</body>
	</html>



