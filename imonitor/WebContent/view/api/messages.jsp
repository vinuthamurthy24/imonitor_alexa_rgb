<%-- Copyright � 2012 iMonitor Solutions India Private Limited --%>

<script>
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
                 "ZW_SWITCH_COLOR":"<s:text name='ZW_SWITCH_COLOR' />"
                 
                
		};
	
		function formatMessage(messageId)
		{
			var result = messageMap[messageId];
			if(result == "undefined" || result == "")result = "Generic message";
			return result;
		};
		
		
</script>