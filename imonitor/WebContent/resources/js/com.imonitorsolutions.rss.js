/*!
 * Copyright � 2012 iMonitor Solutions India Private Limited
 */

var rss = "";

var gateWayDetailsMap = {};

var handleGatewaySelectChange = function(ev){

	var selectedGatewayId = $(this).val();
	var selectedGateWayDetails = gateWayDetailsMap[selectedGatewayId];
	var devices = selectedGateWayDetails["devices"];
	
	// Clear the tables.
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var initialAlertRows = "<tr><th>"+formatMessage("setup.devices.name")+"</th><th></th><th>"+formatMessage("setup.rules.alert")+"</th><th></th><th></th></tr>";
		initialAlertRows += "<tr id='firstalertrow'><td><select class='selectAlertDevice'></select></td>"
							+"<td>Sends</td><td><select class='selectAlertName'></select></td>"
							+"<td><table style='border: 1px solid lightgrey;' class='deviceSpecificAlert'>"
							+"<tr><td><select class='selectDeviceSpecificAlert'></select></td><td><input type='text' size='3' class='alertvalueclass' maxlength='2'/></td></tr>"
							+"</table></td>"
							+"<td>"
							+"</td>"
							+"</td><input type='hidden' name='alertExpressions' /></td></tr>";
							
		$("#createCriteriaTable").html(initialAlertRows);
	}
	
	var initialActionRows = "<tr>";
	if(rss == "ruleadd" || rss == "ruleedit")initialActionRows += "<th width='27px'>"+formatMessage("setup.rules.afterdelay")+"</th>";
	initialActionRows += "<th>"+formatMessage("setup.devices.name")+"</th><th>"+formatMessage("setup.devices.action")+"</th><th></th><th>"+formatMessage("general.value")+"</th></tr><tr>";
	if(rss == "ruleadd" || rss == "ruleedit")initialActionRows += "<td><input type='checkbox' class='Afterdelay'/></td>";
	initialActionRows += "<td><select class='selectActionDevice'></select></td>"
						+"<td><select class='selectActionName'></select></td>"
						+"<td><select style='display:none' class='FanModevalue'/></td>"
						+"<td><input type='hidden' class='expressionValue' name='actionExpressions' />"
						//parishod start
						+"<input type='text' maxlength='3' size='3' style='display:none' class='actionvalueclass'/></td>"
						+"<input type='color' style='display:none'  class='colorvalueclass'/></td>"
						+"<td><select style='display:none' class='camerapresetvalueclass'/></td>"
						//parishod end
						
						+"<td><a href='#' class='removeCurrentRow'><img src='/imonitor/resources/images/delete2.png'></img></a></td></tr>";
	$("#defineActionTable").html(initialActionRows);
	var alertDeviceOptions = "<option value=''>"+formatMessage("setup.rss.selectdevice")+"</option>";
	var actionDeviceOptions = "<option value=''>"+formatMessage("setup.rss.selectdevice")+"</option>";
	for(var deviceID in devices){
		// 2. Filling the alerts section.
		var device = devices[deviceID];
		var deviceName = device["devicename"];
		
		
		if(rss == "ruleadd" || rss == "ruleedit")
		{
			//vibhu allow DEVICE_UP and DEVICE_DOWN rules for only SWITCH and DIMMER. for following devices we have only UP and DOWN.
			if(	   device["deviceType"] == "IP_CAMERA" 
				|| device["deviceType"] == "Z_WAVE_SIREN"
				|| device["deviceType"] == "Z_WAVE_MOTOR_CONTROLLER"
				|| (device["deviceType"] == "Z_WAVE_AC_EXTENDER" && device["modelNumber"] != "ZXT120" && device["modelNumber"] != "ZXT600")
			) continue;
			
			if(device["alerts"] != undefined){
				if(deviceName == "Home")deviceName = formatMessage("general.home");
				else if(deviceName == "Stay")deviceName = formatMessage("general.stay");
				else if(deviceName == "Away")deviceName = formatMessage("general.away");

				alertDeviceOptions += "<option value='" + deviceID + "'>" + deviceName + "</option>";
			}
		}
		
	}
	for(var deviceID in devices){
		// 2. Filling the alerts section.
		var device = devices[deviceID];
		var deviceName = device["devicename"];
	
		//sumit start AVOID LISTING VIRTUAL DEVICES here
		if(		((deviceName!="Home") && (deviceName!="Stay") && (deviceName!="Away")) 
				|| (rss == "scheduleadd" || rss == "scheduleedit") )
			{
			if(device["actions"] != undefined){
				if(device["deviceType"] == "IP_CAMERA" && (rss == "scenarioadd" || rss == "scenarioedit" || rss == "scheduleadd" || rss == "scheduleedit")) continue;  //Naveen added "scheduleadd" and "acheduleedit" to remove camera from schedule
				//vibhu added deviceType
				if(deviceName == "Home")deviceName = formatMessage("general.home");
				else if(deviceName == "Stay")deviceName = formatMessage("general.stay");
				else if(deviceName == "Away")deviceName = formatMessage("general.away");
				
			   actionDeviceOptions += "<option devicetype='"+device["deviceType"]+"' value='" + deviceID + "'>" + deviceName + "</option>";
			}
		}	
	}

	if(rss == "ruleadd" || rss == "ruleedit")$(".selectAlertDevice").html(alertDeviceOptions);
	$(".selectActionDevice").html(actionDeviceOptions);
	// Intialize the alert/action selects
	if(rss == "ruleadd" || rss == "ruleedit")$(".selectAlertDevice").change();
	$(".selectActionDevice").change();
	
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var kForm=$("#ruledelay");
		kForm.addClass('number');
		kForm.attr('minvalue', 0);
		kForm.attr('maxvalue', 600);
		
		var alertOption = $(".alertvalueclass");
		alertOption.addClass('required');
		alertOption.addClass('number');
		alertOption.attr('minvalue',0);
		alertOption.attr('maxvalue',40);
	}
	try{
}catch(Err){alert("Err "+Err.message);}
};




var handleGatewaySelectChangeForEndAction = function(ev){

	var selectedGatewayId = $(this).val();
	var selectedGateWayDetails = gateWayDetailsMap[selectedGatewayId];
	var devices = selectedGateWayDetails["devices"];
	
	// Clear the tables.
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var initialAlertRows = "<tr><th>"+formatMessage("setup.devices.name")+"</th><th></th><th>"+formatMessage("setup.rules.alert")+"</th><th></th><th></th></tr>";
		initialAlertRows += "<tr id='firstalertrow'><td><select class='selectAlertDevice'></select></td>"
							+"<td>Sends</td><td><select class='selectAlertName'></select></td>"
							+"<td><table style='border: 1px solid lightgrey;' class='deviceSpecificAlert'>"
							+"<tr><td><select class='selectDeviceSpecificAlert'></select></td><td><input type='text' size='3' class='alertvalueclass' maxlength='2'/></td></tr>"
							+"</table></td>"
							+"<td>"
							+"</td>"
							+"</td><input type='hidden' name='alertExpressions' /></td></tr>";
							
		$("#createCriteriaTable").html(initialAlertRows);
	}
	
	var initialActionRows = "<tr>";
	if(rss == "ruleadd" || rss == "ruleedit")initialActionRows += "<th width='27px'>"+formatMessage("setup.rules.afterdelay")+"</th>";
	initialActionRows += "<th>"+formatMessage("setup.devices.name")+"</th><th>"+formatMessage("setup.devices.action")+"</th><th></th><th>"+formatMessage("general.value")+"</th></tr><tr>";
	if(rss == "ruleadd" || rss == "ruleedit")initialActionRows += "<td><input type='checkbox' class='Afterdelay'/></td>";
	initialActionRows += "<td><select class='selectActionDeviceEnd'></select></td>"
						+"<td><select class='selectActionNameEnd'></select></td>"
						+"<td><select style='display:none' class='FanModevalueEnd'/></td>"
						+"<td><input type='hidden' class='expressionValue' name='actionExpressionsEnd' />"
						//parishod start
						//+"<input type='text' maxlength='3' size='3' style='display:none' class='actionvalueclass'/></td>"
						+"<input type='text' maxlength='3' size='3' style='display:none' class='actionvalueclassEnd'/></td>"
						+"<td><select style='display:none' class='camerapresetvalueclassEnd'/></td>"
						//parishod end
						+"<td><a href='#' class='removeCurrentRowEnd'><img src='/imonitor/resources/images/delete2.png'></img></a></td></tr>";
	$("#defineEndActionTable").html(initialActionRows);
	var alertDeviceOptions = "<option value=''>"+formatMessage("setup.rss.selectdevice")+"</option>";
	var actionDeviceOptions = "<option value=''>"+formatMessage("setup.rss.selectdevice")+"</option>";
	for(var deviceID in devices){
		// 2. Filling the alerts section.
		var device = devices[deviceID];
		var deviceName = device["devicename"];
		if(rss == "ruleadd" || rss == "ruleedit")
		{
			//vibhu allow DEVICE_UP and DEVICE_DOWN rules for only SWITCH and DIMMER. for following devices we have only UP and DOWN.
			if(	   device["deviceType"] == "IP_CAMERA" 
				|| device["deviceType"] == "Z_WAVE_SIREN"
				|| device["deviceType"] == "Z_WAVE_MOTOR_CONTROLLER"
				|| (device["deviceType"] == "Z_WAVE_AC_EXTENDER" && device["modelNumber"] != "ZXT120" || device["modelNumber"] != "ZXT600")
			) continue;
			
			if(device["alerts"] != undefined){
				if(deviceName == "Home")deviceName = formatMessage("general.home");
				else if(deviceName == "Stay")deviceName = formatMessage("general.stay");
				else if(deviceName == "Away")deviceName = formatMessage("general.away");

				alertDeviceOptions += "<option value='" + deviceID + "'>" + deviceName + "</option>";
			}
		}			
	}
	for(var deviceID in devices){
		// 2. Filling the alerts section.
		var device = devices[deviceID];
		var deviceName = device["devicename"];
	
		//sumit start AVOID LISTING VIRTUAL DEVICES here
		if(		((deviceName!="Home") && (deviceName!="Stay") && (deviceName!="Away")) 
				|| (rss == "scheduleadd" || rss == "scheduleedit") )
			{
			if(device["actions"] != undefined){
				if(device["deviceType"] == "IP_CAMERA" && (rss == "scenarioadd" || rss == "scenarioedit" || rss == "scheduleadd" || rss == "scheduleedit")) continue;  //Naveen added "scheduleadd" and "acheduleedit" to remove camera from schedule
				//vibhu added deviceType
				if(deviceName == "Home")deviceName = formatMessage("general.home");
				else if(deviceName == "Stay")deviceName = formatMessage("general.stay");
				else if(deviceName == "Away")deviceName = formatMessage("general.away");
				
			   actionDeviceOptions += "<option devicetype='"+device["deviceType"]+"' value='" + deviceID + "'>" + deviceName + "</option>";
			}
		}	
	}

	if(rss == "ruleadd" || rss == "ruleedit")$(".selectAlertDevice").html(alertDeviceOptions);
	$(".selectActionDeviceEnd").html(actionDeviceOptions);
	// Intialize the alert/action selects
	if(rss == "ruleadd" || rss == "ruleedit")$(".selectAlertDevice").change();
	$(".selectActionDeviceEnd").change();
	
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var kForm=$("#ruledelay");
		kForm.addClass('number');
		kForm.attr('minvalue', 0);
		kForm.attr('maxvalue', 600);
		
		var alertOption = $(".alertvalueclass");
		alertOption.addClass('required');
		alertOption.addClass('number');
		alertOption.attr('minvalue',0);
		alertOption.attr('maxvalue',40);
	}
	try{
}catch(Err){alert("Err "+Err.message);}
};

var handleActionSelectChange = function(ev){
	var selectedDeviceId = $(this).val();
	var cRow = $(this).parent().parent();
	if(selectedDeviceId == ""){
		

		// User have selected 'Select A Device' Option.
		var cRow = $(this).parent().parent();
		var saSelect = cRow.find(".selectActionName");
		saSelect.html("<option value=''>"+formatMessage("setup.rss.nodevice")+"</option>");
		saSelect.change();
		cRow.find('input[type="hidden"]').val("");
		if(rss == "ruleadd" || rss == "ruleedit")
		{
			cRow.find(".Afterdelay").attr('disabled','true');
		}
		return;
	}
	//vibhu added else
	else if((rss == "ruleadd" || rss == "ruleedit") && ($("#ruledelay").val() != "" && $("#ruledelay").val() != 0))
	{
		var cRow = $(this).parent().parent();
		cRow.find(".Afterdelay").removeAttr('disabled');
	}
	
	var actionOptionHtml = "";
	var selectedGateWayId = $("#gatewayselect").val();
	var selectedGateWayDetails = gateWayDetailsMap[selectedGateWayId];
	var devices = selectedGateWayDetails["devices"];
	var actions = devices[selectedDeviceId]["actions"];
	var modelNumber = devices[selectedDeviceId]["modelNumber"];
	var message = formatMessage("setup.devices.curtain.make");
	for(var actionId in actions){
		var actionDetails = actions[actionId];
		var actionName = actionDetails["name"];
		
		//sumit start: AUG 13,2012 - Camera Recording is not in our road map, so hiding this feature.
		//actionOptionHtml += "<option value='" + actionId + "'>" + actionName + "</option>";
		if((modelNumber == "RC8021") || (modelNumber != "RC8061") || (modelNumber != "H264PT") || (modelNumber != "H264FIXED")){
			if(actionName =="Start Recording"){
				continue;	
			}			
		}
		//sumit end:
               //pari start
			   //alert("parishod---"+devices[selectedDeviceId]["deviceType"]);

	   
		if(devices[selectedDeviceId]["deviceType"] == "IP_CAMERA"){

			if(modelNumber == "RC8061" && actionName == "Move to Preset" && devices[selectedDeviceId]['presets'] != '0')
			{
			  // alert(actionName);
				actionOptionHtml += "<option an='"+actionName+"' preset='"+devices[selectedDeviceId]['presets']+"'   value='" + actionId + "'>" + formatMessage(actionDetails["details"]) + "</option>";
			}
			else if(modelNumber == "H264PT" && actionName == "Move to Preset" && devices[selectedDeviceId]['presets'] != '0')
			{
				actionOptionHtml += "<option an='"+actionName+"' preset='"+devices[selectedDeviceId]['presets']+"'   value='" + actionId + "'>" + formatMessage(actionDetails["details"]) + "</option>";
			}
			else if(actionName == "Capture Image"){
				actionOptionHtml += "<option value='" + actionId + "'>" + actionName + "</option>";
			}			
		}else if((rss == "scheduleadd" || rss == "scheduleedit") && actionName == "Update neighbour"){
			
			actionOptionHtml += "<option an='"+actionName+"' value='" + actionId + "'>" + formatMessage(actionDetails["details"]) + "</option>";
		}
		else if(actionName != "Update neighbour"){
				actionOptionHtml += "<option an='"+actionName+"' value='" + actionId + "'>" + formatMessage(actionDetails["details"]) + "</option>";
				
			}
		//pari end

	}
	
	
	var cRow = $(this).parent().parent();
	//vibhu clean the value field now we have a new device
	cRow.find('.actionvalueclass').val("");
	cRow.find('.actionvalueclass').hide();

	if(devices[selectedDeviceId]["deviceType"] == "Z_WAVE_MOTOR_CONTROLLER" && devices[selectedDeviceId]['makets'] == 0){
			showResultAlert(message);
				cRow.find('.selectActionDevice').val("");
				cRow.find(".removeCurrentRow").click();
				return ;			
				
				}
	
	
	cRow.find(".selectActionName").html(actionOptionHtml);
	var isLastRow = cRow.nextAll().length == 0;
	if(isLastRow){
		
		if(rss == "ruleadd" || rss == "ruleedit")
		{
			cRow.find(".Afterdelay").removeAttr('disabled');
		}
		//vibhu added the if condition.
		if($('.selectActionName').length < 20) 
		{				
			var cloneLastRow = cRow.clone();
			cRow.after(cloneLastRow);
			cloneLastRow.find('.selectActionDevice').change();
		}
	}
	
	cRow.find(".selectActionName").change();
	var cActionId = cRow.find(".selectActionName").val();
	var cExpression = "";
	
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var cActionCheckBox = cRow.find('input[type="checkbox"]');
		var value = $("#ruledelay").val();
		//vibhu removed prevAll statements.
		if((value == "") || (value==0))
		{
			cRow.find(".Afterdelay").attr('disabled','true');
		}

		if(cActionCheckBox.is(':checked'))
		{
			cExpression = "1=";
		}
		else
		{
			cExpression = "0=";
		}
	}
	cExpression += selectedDeviceId + "=" + cActionId ;
	var cActionValueText = cRow.find('input[type="text"]');
	//var actionName = cRow.find(".selectActionName").find("option:selected").text();
	//if(cActionValueText.is(":visible")  || (actionName == "Curtain Open"|| actionName == "Curtain Close" )){
	if(cActionValueText.val() != "")
		cExpression += ("=" + cActionValueText.val());
	//}
	cRow.find('input[type="hidden"]').val(cExpression);
};


var handleActionSelectChangeEnd = function(ev){
	var selectedDeviceId = $(this).val();
	var cRow = $(this).parent().parent();
	if(selectedDeviceId == ""){
		

		// User have selected 'Select A Device' Option.
		var cRow = $(this).parent().parent();
		var saSelect = cRow.find(".selectActionNameEnd");
		saSelect.html("<option value=''>"+formatMessage("setup.rss.nodevice")+"</option>");
		saSelect.change();
		cRow.find('input[type="hidden"]').val("");
		if(rss == "ruleadd" || rss == "ruleedit")
		{
			cRow.find(".Afterdelay").attr('disabled','true');
		}
		return;
	}
	//vibhu added else
	else if((rss == "ruleadd" || rss == "ruleedit") && ($("#ruledelay").val() != "" && $("#ruledelay").val() != 0))
	{
		var cRow = $(this).parent().parent();
		cRow.find(".Afterdelay").removeAttr('disabled');
	}
	var selectedGateWayId = $("#gatewayselect").val();
	var selectedGateWayDetails = gateWayDetailsMap[selectedGateWayId];
	var devices = selectedGateWayDetails["devices"];
	var actions = devices[selectedDeviceId]["actions"];
	var modelNumber = devices[selectedDeviceId]["modelNumber"];
	var message = formatMessage("setup.devices.curtain.make");
	//var make = devices[selectedDeviceId]['make'];
//alert(devices[selectedDeviceId]['makets']);
	var actionOptionHtml = "";
	//naveen made changes to remove virtual devices in schedules
	/*if (devices[selectedDeviceId]["deviceType"] == "MODE_HOME"){
		
		$(".selectActionDevice option[devicetype='MODE_STAY']").remove();
		$(".selectActionDevice option[devicetype='MODE_AWAY']").remove();
		
	}else if(devices[selectedDeviceId]["deviceType"] == "MODE_STAY"){
		$(".selectActionDevice option[devicetype='MODE_HOME']").remove();
		$(".selectActionDevice option[devicetype='MODE_AWAY']").remove();
	}else if(devices[selectedDeviceId]["deviceType"] == "MODE_AWAY"){
		$(".selectActionDevice option[devicetype='MODE_HOME']").remove();
		$(".selectActionDevice option[devicetype='MODE_STAY']").remove();
	}*/
	for(var actionId in actions){
		var actionDetails = actions[actionId];
		var actionName = actionDetails["name"];
		
		//sumit start: AUG 13,2012 - Camera Recording is not in our road map, so hiding this feature.
		//actionOptionHtml += "<option value='" + actionId + "'>" + actionName + "</option>";
		if((modelNumber == "RC8021") || (modelNumber != "RC8061") || (modelNumber != "H264PT") || (modelNumber != "H264FIXED")){
			if(actionName =="Start Recording"){
				continue;	
			}			
		}
		//sumit end:
               //pari start
			   //alert("parishod---"+devices[selectedDeviceId]["deviceType"]);

	   
		if(devices[selectedDeviceId]["deviceType"] == "IP_CAMERA"){

			if(modelNumber == "RC8061" && actionName == "Move to Preset" && devices[selectedDeviceId]['presets'] != '0')
			{
			  // alert(actionName);
				actionOptionHtml += "<option an='"+actionName+"' preset='"+devices[selectedDeviceId]['presets']+"'   value='" + actionId + "'>" + formatMessage(actionDetails["details"]) + "</option>";
			}
			else if(modelNumber == "H264PT" && actionName == "Move to Preset" && devices[selectedDeviceId]['presets'] != '0')
			{
				actionOptionHtml += "<option an='"+actionName+"' preset='"+devices[selectedDeviceId]['presets']+"'   value='" + actionId + "'>" + formatMessage(actionDetails["details"]) + "</option>";
			}
			else if(actionName == "Capture Image"){
				actionOptionHtml += "<option value='" + actionId + "'>" + actionName + "</option>";
			}			
		}else if((rss == "scheduleadd" || rss == "scheduleedit") && actionName == "Update neighbour"){
			
			actionOptionHtml += "<option an='"+actionName+"' value='" + actionId + "'>" + formatMessage(actionDetails["details"]) + "</option>";
		}
		else if(actionName != "Update neighbour"){
				actionOptionHtml += "<option an='"+actionName+"' value='" + actionId + "'>" + formatMessage(actionDetails["details"]) + "</option>";
			}
		//pari end

	}
	var cRow = $(this).parent().parent();
	//vibhu clean the value field now we have a new device
	cRow.find('.actionvalueclassEnd').val("");
	cRow.find('.actionvalueclassEnd').hide();

	if(devices[selectedDeviceId]["deviceType"] == "Z_WAVE_MOTOR_CONTROLLER" && devices[selectedDeviceId]['makets'] == 0){
			showResultAlert(message);
			
				cRow.find('.selectActionDeviceEnd').val("");
				cRow.find(".removeCurrentRowEnd").click();
				return ;			
				
				}
	
	
	cRow.find(".selectActionNameEnd").html(actionOptionHtml);
	var isLastRow = cRow.nextAll().length == 0;
	if(isLastRow){
		
		if(rss == "ruleadd" || rss == "ruleedit")
		{
			cRow.find(".Afterdelay").removeAttr('disabled');
		}
		//vibhu added the if condition.
		if($('.selectActionNameEnd').length < 20) 
		{				
			var cloneLastRow = cRow.clone();
			cRow.after(cloneLastRow);
			cloneLastRow.find('.selectActionDeviceEnd').change();
		}
	}
	
	cRow.find(".selectActionNameEnd").change();
	var cActionId = cRow.find(".selectActionNameEnd").val();
	var cExpressionEnd = "";
	
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var cActionCheckBox = cRow.find('input[type="checkbox"]');
		var value = $("#ruledelay").val();
		//vibhu removed prevAll statements.
		if((value == "") || (value==0))
		{
			cRow.find(".Afterdelay").attr('disabled','true');
		}

		if(cActionCheckBox.is(':checked'))
		{
			cExpressionEnd = "1=";
		}
		else
		{
			cExpressionEnd = "0=";
		}
	}
	cExpressionEnd += selectedDeviceId + "=" + cActionId ;
	var cActionValueText = cRow.find('input[type="text"]');
	//var actionName = cRow.find(".selectActionNameEnd").find("option:selected").text();
	//if(cActionValueText.is(":visible")  || (actionName == "Curtain Open"|| actionName == "Curtain Close" )){
	if(cActionValueText.val() != "")
		cExpressionEnd += ("=" + cActionValueText.val());
	//}
	cRow.find('input[type="hidden"]').val(cExpressionEnd);
};



var handleActionNameSelectChange = function(ev){
	var selectedText = $(this).find("option:selected").attr("an");
	var cRow = $(this).parent().parent();
	//alert("TEXT=="+selectedText);
	//pari start
	var presetValueText = cRow.find('.camerapresetvalueclass');
	//bhavya start for ac thermostat
	var FanmodevalueText = cRow.find('.FanModevalue');
	//bhavya end for ac thermostat
	//alert("v1=="+presetValueText);
	presetValueText.hide();
	FanmodevalueText.hide();
	//pari end
	//vibhu start
	//cRow.find('.percentage').remove();
	//vibhu end
	//Change Dimmer Level
	var actionValueText = cRow.find('.actionvalueclass');
	var colorClass = cRow.find('.colorvalueclass'); 
	var colorvalue = cRow.find('.colorvalueclass').val(); 
	alert("colorrrr " + colorvalue);
	
	if(selectedText == "Change Dimmer Level"){
		actionValueText.addClass('required');
		actionValueText.addClass('editdisplayStar');
		actionValueText.addClass('number');
		actionValueText.attr('minvalue', 0);
		actionValueText.attr('maxvalue', 99);
// 				var CuratinValue = cRow.find('#CurtainRule');
// 				CuratinValue.hide();				
		actionValueText.show();
		actionValueText.closest("tr").find("td:first-child").find("span").show();
	}else if(selectedText == "Switch Color"){
		alert("TEXT=="+selectedText);
		//colorClass.addClass('required');
		//colorClass.addClass('editdisplayStar');
		//colorClass.addClass('number');
		var colorvalue = cRow.find('.colorvalueclass').val(); 
		alert("colorrrr " + colorvalue);
		colorClass.show();
		colorClass.closest("tr").find("td:first-child").find("span").show();
	}  
	
	else if(selectedText == "AC Control"){
		actionValueText.addClass('required');
		actionValueText.addClass('editdisplayStar');
		actionValueText.addClass('number');
		actionValueText.attr('minvalue', 16);
		actionValueText.attr('maxvalue', 28);
// 				var CuratinValue = cRow.find('#CurtainRule');
// 				CuratinValue.hide();				
		actionValueText.show();
		actionValueText.closest("tr").find("td:first-child").find("span").show();
		}else if(selectedText == "AC Mode"){
		actionValueText.addClass('required');
		actionValueText.addClass('editdisplayStar');
		actionValueText.addClass('number');
		actionValueText.attr('minvalue', 16);
		actionValueText.attr('maxvalue', 28);
		actionValueText.closest("tr").find("td:first-child").find("span").show();
		var ModeOption = "";
		
		    ModeOption += "<option value='10'>Auto</option>";
		
		    ModeOption += "<option value='2'>Cool</option>";
		
		    ModeOption += "<option value='8'>Dry</option>";
		
		    ModeOption += "<option value='1'>Heat</option>";
			
			ModeOption += "<option value='6'>Fan</option>";
		FanmodevalueText.html(ModeOption);	
		actionValueText.show();
		FanmodevalueText.show();
	}else if((selectedText == "Curtain Open")||(selectedText == "Curtain Close")){
		/*$("<span  class='percentage' style='font-size:15px;font-weight:bold;'>&nbsp;%</span>").insertAfter(actionValueText);
		actionValueText.addClass('required');
		actionValueText.addClass('number');
		actionValueText.show();*/
		actionValueText.attr('minvalue', 0);
		actionValueText.attr('maxvalue', 100);
		actionValueText.val(100);
		actionValueText.closest("tr").find("td:first-child").find("span").hide();
// 				var CuratinValue = cRow.find('#CurtainRule');
// 				CuratinValue.show();
// 				CuratinValue.attr('value', "%");
	}else if(selectedText == "Move to Preset"){
		//actionValueText.addClass('required');
		//actionValueText.addClass('number');
		//actionValueText.attr('minvalue', 1);
		//actionValueText.attr('maxvalue', 4);
		var CuratinValue = cRow.find('#CurtainRule');
		CuratinValue.hide();
		actionValueText.hide();

		var campreset = $(this).find("option:selected").attr("preset");
			//alert("v4=="+campreset);

//alert("a1"+(".presets").val);
//alert(campreset & 1);
//alert(campreset & 2);
//alert(campreset & 4);
//alert(campreset & 8);
		
		var presetOption = "";
		if( (campreset & 1) > 0 )
		    presetOption += "<option value='1'>1</option>";
		if( (campreset & 2) > 0 )
		    presetOption += "<option value='2'>2</option>";
		if( (campreset & 4) > 0 )
		    presetOption += "<option value='3'>3</option>";
		if( (campreset & 8) > 0 )
		    presetOption += "<option value='4'>4</option>";
		presetValueText.html(presetOption);	
		presetValueText.show();
	}else if(selectedText == "Update neighbour"){
		var count = $("#updateNeighborCount").val(parseInt($("#updateNeighborCount").val())+1);
		actionValueText.removeClass('required');
		actionValueText.removeClass('number');
		actionValueText.removeClass('editdisplayStar');
		actionValueText.removeAttr('minvalue');
		actionValueText.removeAttr('maxvalue');
		actionValueText.closest("tr").find("td:first-child").find("span").hide();
		actionValueText.hide();
		
	}else{
		actionValueText.removeClass('required');
		actionValueText.removeClass('number');
		actionValueText.removeClass('editdisplayStar');
		actionValueText.removeAttr('minvalue');
		actionValueText.removeAttr('maxvalue');
		actionValueText.closest("tr").find("td:first-child").find("span").hide();
		actionValueText.hide();
// 				var CuratinValue = cRow.find('#CurtainRule');
// 				CuratinValue.hide();				
	}
	var selectedDeviceId = cRow.find('.selectActionDevice').val();
	var cActionId = $(this).val();

	var cExpression = "";
	var cExpression1 = "";
	
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var cActionCheckBox = cRow.find('input[type="checkbox"]');
	
		if(selectedDeviceId!=""){
			if(cActionCheckBox.is(':checked')){
				cExpression = "1=";
				cExpression1 = "1=";
			}else{
				cExpression = "0=";
				cExpression1 = "0=";
			}
		}
	}

	if($.trim(selectedDeviceId + cActionId) != ""){
		cExpression += selectedDeviceId + "=" + cActionId;
		cExpression1 += selectedDeviceId + "=" + cActionId;
	}
	
	var pValueText = cRow.find('.camerapresetvalueclass').val();
	//alert("paris="+pValueText);
		if(pValueText == null){
		pValueText = "";
		}else{
		cExpression1 += ("=" + pValueText);
		}
	var cActionValueText = cRow.find('input[type="text"]');
	//if(cActionValueText.is(":visible") || (selectedText == "Curtain Open"|| selectedText == "Curtain Close" )){

	
	
	if(cActionValueText.val() != "")
	{
		if(cActionValueText.is(":visible"))
		{
		cExpression += ("=" + cActionValueText.val());
		cExpression1 += ("=" + cActionValueText.val());
		}

	}
		
	//}
	/*if(presetValueText.val() != "0"){
				alert("value="+ presetValueText.val());
			cExpression += ("=" + presetValueText.val());
	}*/
	cRow.find('input[type="hidden"]').val(cExpression);
	cRow.find('input[type="hidden"]').val(cExpression1);
	
	$("#defineActionsHidden").val("");
	/*alert("start action");*/
	$('input[name="actionExpressions"]').each(function(){
		$("#defineActionsHidden").val($("#defineActionsHidden").val() + $(this).val());
	});
	
	return true;
};





var handleActionNameSelectChangeEnd = function(ev){
	var selectedText = $(this).find("option:selected").attr("an");

	var cRow = $(this).parent().parent();
	//alert("TEXT=="+selectedText);
	//pari start
	var presetValueText = cRow.find('.camerapresetvalueclassEnd');
	//bhavya start for ac thermostat
	var FanmodevalueText = cRow.find('.FanModevalueEnd');
	//bhavya end for ac thermostat
	//alert("v1=="+presetValueText);
	presetValueText.hide();
	FanmodevalueText.hide();
	//pari end
	//vibhu start
	//cRow.find('.percentage').remove();
	//vibhu end
	//Change Dimmer Level
	var actionValueText = cRow.find('.actionvalueclassEnd');
	if(selectedText == "Change Dimmer Level"){
		actionValueText.addClass('required');
		actionValueText.addClass('editdisplayStar');
		actionValueText.addClass('number');
		actionValueText.attr('minvalue', 0);
		actionValueText.attr('maxvalue', 99);
// 				var CuratinValue = cRow.find('#CurtainRule');
// 				CuratinValue.hide();				
		actionValueText.show();
		actionValueText.closest("tr").find("td:first-child").find("span").show();
	}else if(selectedText == "AC Control"){
		actionValueText.addClass('required');
		actionValueText.addClass('editdisplayStar');
		actionValueText.addClass('number');
		actionValueText.attr('minvalue', 16);
		actionValueText.attr('maxvalue', 28);
// 				var CuratinValue = cRow.find('#CurtainRule');
// 				CuratinValue.hide();				
		actionValueText.show();
		actionValueText.closest("tr").find("td:first-child").find("span").show();
		}else if(selectedText == "AC Mode"){
		actionValueText.addClass('required');
		actionValueText.addClass('editdisplayStar');
		actionValueText.addClass('number');
		actionValueText.attr('minvalue', 16);
		actionValueText.attr('maxvalue', 28);
		actionValueText.closest("tr").find("td:first-child").find("span").show();
		var ModeOption = "";
		
		    ModeOption += "<option value='10'>Auto</option>";
		
		    ModeOption += "<option value='2'>Cool</option>";
		
		    ModeOption += "<option value='8'>Dry</option>";
		
		    ModeOption += "<option value='1'>Heat</option>";
			
			ModeOption += "<option value='6'>Fan</option>";
		FanmodevalueText.html(ModeOption);	
		actionValueText.show();
		FanmodevalueText.show();
	}else if((selectedText == "Curtain Open")||(selectedText == "Curtain Close")){
		/*$("<span  class='percentage' style='font-size:15px;font-weight:bold;'>&nbsp;%</span>").insertAfter(actionValueText);
		actionValueText.addClass('required');
		actionValueText.addClass('number');
		actionValueText.show();*/
		actionValueText.attr('minvalue', 0);
		actionValueText.attr('maxvalue', 100);
		actionValueText.val(100);
		actionValueText.closest("tr").find("td:first-child").find("span").hide();
// 				var CuratinValue = cRow.find('#CurtainRule');
// 				CuratinValue.show();
// 				CuratinValue.attr('value', "%");
	}else if(selectedText == "Move to Preset"){
		//actionValueText.addClass('required');
		//actionValueText.addClass('number');
		//actionValueText.attr('minvalue', 1);
		//actionValueText.attr('maxvalue', 4);
		var CuratinValue = cRow.find('#CurtainRule');
		CuratinValue.hide();
		actionValueText.hide();

		var campreset = $(this).find("option:selected").attr("preset");
			//alert("v4=="+campreset);

//alert("a1"+(".presets").val);
//alert(campreset & 1);
//alert(campreset & 2);
//alert(campreset & 4);
//alert(campreset & 8);
		
		var presetOption = "";
		if( (campreset & 1) > 0 )
		    presetOption += "<option value='1'>1</option>";
		if( (campreset & 2) > 0 )
		    presetOption += "<option value='2'>2</option>";
		if( (campreset & 4) > 0 )
		    presetOption += "<option value='3'>3</option>";
		if( (campreset & 8) > 0 )
		    presetOption += "<option value='4'>4</option>";
		presetValueText.html(presetOption);	
		presetValueText.show();
	}else if(selectedText == "Update neighbour"){
		var count = $("#updateNeighborCountEnd").val(parseInt($("#updateNeighborCountEnd").val())+1);
		actionValueText.removeClass('required');
		actionValueText.removeClass('number');
		actionValueText.removeClass('editdisplayStar');
		actionValueText.removeAttr('minvalue');
		actionValueText.removeAttr('maxvalue');
		actionValueText.closest("tr").find("td:first-child").find("span").hide();
		actionValueText.hide();
		
	}else{
		actionValueText.removeClass('required');
		actionValueText.removeClass('number');
		actionValueText.removeClass('editdisplayStar');
		actionValueText.removeAttr('minvalue');
		actionValueText.removeAttr('maxvalue');
		actionValueText.closest("tr").find("td:first-child").find("span").hide();
		actionValueText.hide();
// 				var CuratinValue = cRow.find('#CurtainRule');
// 				CuratinValue.hide();				
	}
	var selectedDeviceId = cRow.find('.selectActionDeviceEnd').val();
	var cActionId = $(this).val();

	var cExpressionEnd = "";
	var cExpressionEnd1 = "";
	
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var cActionCheckBox = cRow.find('input[type="checkbox"]');
	
		if(selectedDeviceId!=""){
			if(cActionCheckBox.is(':checked')){
				cExpressionEnd = "1=";
				cExpressionEnd1 = "1=";
			}else{
				cExpressionEnd = "0=";
				ccExpressionEnd1 = "0=";
			}
		}
	}

	if($.trim(selectedDeviceId + cActionId) != ""){
		cExpressionEnd += selectedDeviceId + "=" + cActionId;
		cExpressionEnd1 += selectedDeviceId + "=" + cActionId;
	}
	
	var pValueText = cRow.find('.camerapresetvalueclassEnd').val();
	//alert("paris="+pValueText);
		if(pValueText == null){
		pValueText = "";
		}else{
			cExpressionEnd1 += ("=" + pValueText);
		}
	var cActionValueText = cRow.find('input[type="text"]');
	//if(cActionValueText.is(":visible") || (selectedText == "Curtain Open"|| selectedText == "Curtain Close" )){

	
	
	if(cActionValueText.val() != "")
	{
		if(cActionValueText.is(":visible"))
		{
			cExpressionEnd += ("=" + cActionValueText.val());
			cExpressionEnd1 += ("=" + cActionValueText.val());
		}

	}
		
	//}
	/*if(presetValueText.val() != "0"){
				alert("value="+ presetValueText.val());
			cExpression += ("=" + presetValueText.val());
	}*/
	cRow.find('input[type="hidden"]').val(cExpressionEnd);
	cRow.find('input[type="hidden"]').val(cExpressionEnd1);
	
	$("#defineActionsHiddenEnd").val("");
	/*alert("End action");*/
	$('input[name="actionExpressionsEnd"]').each(function(){
		$("#defineActionsHiddenEnd").val($("#defineActionsHiddenEnd").val() + $(this).val());
	});
	

	
	return true;
};

var handleGatewayActionSelectChange = function(ev){
	
	var selectedGatewayId = $(this).val();
	
	var selectedGateWayDetails = gateWayDetailsMap[selectedGatewayId];
	var devices = selectedGateWayDetails["devices"];
	var actionDeviceOptions = "<option value=''>"+formatMessage("setup.rss.selectdevice")+"</option>";
	for(var deviceID in devices){
		// 2. Filling the alerts section.
		var device = devices[deviceID];
		var deviceName = device["devicename"];
	
		
		
			if(device["actions"] != undefined){
				
				
				if(deviceName == "Home")deviceName = formatMessage("general.home");
				else if(deviceName == "Stay")deviceName = formatMessage("general.stay");
				else if(deviceName == "Away")deviceName = formatMessage("general.away");
				
			   actionDeviceOptions += "<option devicetype='"+device["deviceType"]+"' value='" + deviceID + "'>" + deviceName + "</option>";
			}
		
	}
	var cRow = $(this).parent().parent();
	cRow.find(".selectActionDevice").html(actionDeviceOptions);
	
	
};

var handleSecurityActionSelectChange = function(ev){
	
	var selectedDeviceId = $(this).val();
	
	var cRow = $(this).parent().parent();
	if(selectedDeviceId == ""){
		

		// User have selected 'Select A Device' Option.
		var cRow = $(this).parent().parent();
		var saSelect = cRow.find(".selectActionName");
		saSelect.html("<option value=''>"+formatMessage("setup.rss.nodevice")+"</option>");
		saSelect.change();
		cRow.find('input[type="hidden"]').val("");
		
		return;
	}
	
	var actionOptionHtml = "";
	var selectedGateWayId = cRow.find(".selectActionGateway").val();
	var selectedGateWayDetails = gateWayDetailsMap[selectedGateWayId];
	var devices = selectedGateWayDetails["devices"];
	var actions = devices[selectedDeviceId]["actions"];
	var modelNumber = devices[selectedDeviceId]["modelNumber"];
	var message = formatMessage("setup.devices.curtain.make");
	for(var actionId in actions){
		var actionDetails = actions[actionId];
		var actionName = actionDetails["name"];
		
		
		if((modelNumber == "RC8021") || (modelNumber != "RC8061") || (modelNumber != "H264PT") || (modelNumber != "H264FIXED")){
			if(actionName =="Start Recording"){
				continue;	
			}			
		}
	

	   
		if(devices[selectedDeviceId]["deviceType"] == "IP_CAMERA"){

			if(modelNumber == "RC8061" && actionName == "Move to Preset" && devices[selectedDeviceId]['presets'] != '0')
			{
			  // alert(actionName);
				actionOptionHtml += "<option an='"+actionName+"' preset='"+devices[selectedDeviceId]['presets']+"'   value='" + actionId + "'>" + formatMessage(actionDetails["details"]) + "</option>";
			}
			else if(modelNumber == "H264PT" && actionName == "Move to Preset" && devices[selectedDeviceId]['presets'] != '0')
			{
				actionOptionHtml += "<option an='"+actionName+"' preset='"+devices[selectedDeviceId]['presets']+"'   value='" + actionId + "'>" + formatMessage(actionDetails["details"]) + "</option>";
			}
			else if(actionName == "Capture Image"){
				actionOptionHtml += "<option value='" + actionId + "'>" + actionName + "</option>";
			}			
		}else{
			
			actionOptionHtml += "<option an='"+actionName+"' value='" + actionId + "'>" + formatMessage(actionDetails["details"]) + "</option>";
		}
		
		

	}
	
	
	var cRow = $(this).parent().parent();
	//vibhu clean the value field now we have a new device
	cRow.find('.actionvalueclass').val("");
	cRow.find('.actionvalueclass').hide();

	
	
	
	cRow.find(".selectActionName").html(actionOptionHtml);
	var isLastRow = cRow.nextAll().length == 0;
	if(isLastRow){
		
		
		
		if($('.selectActionName').length < 6) 
		{				
			var cloneLastRow = cRow.clone();
			cRow.after(cloneLastRow);
			cloneLastRow.find('.selectActionDevice').change();
		}
	}
	
	cRow.find(".selectActionName").change();
	var cActionId = cRow.find(".selectActionName").val();
	var cExpression = "";
	
	
	cExpression += selectedGateWayId + "=" +selectedDeviceId + "=" + cActionId ;
	var cActionValueText = cRow.find('input[type="text"]');
	
	if(cActionValueText.val() != "")
		cExpression += ("=" + cActionValueText.val());
	
	
	cRow.find('input[type="hidden"]').val(cExpression);
};

var handleGatewayActionNameSelectChange = function(ev){
	var selectedText = $(this).find("option:selected").attr("an");
	var cRow = $(this).parent().parent();
	var presetValueText = cRow.find('.camerapresetvalueclass');
	var FanmodevalueText = cRow.find('.FanModevalue');
	presetValueText.hide();
	FanmodevalueText.hide();
	var actionValueText = cRow.find('.actionvalueclass');
	if(selectedText == "Change Dimmer Level"){
		actionValueText.addClass('required');
		actionValueText.addClass('editdisplayStar');
		actionValueText.addClass('number');
		actionValueText.attr('minvalue', 0);
		actionValueText.attr('maxvalue', 99);
    	actionValueText.show();
		actionValueText.closest("tr").find("td:first-child").find("span").show();
	}else if(selectedText == "AC Control"){
		actionValueText.addClass('required');
		actionValueText.addClass('editdisplayStar');
		actionValueText.addClass('number');
		actionValueText.attr('minvalue', 16);
		actionValueText.attr('maxvalue', 28);
		actionValueText.show();
		actionValueText.closest("tr").find("td:first-child").find("span").show();
		}else if(selectedText == "AC Mode"){
		actionValueText.addClass('required');
		actionValueText.addClass('editdisplayStar');
		actionValueText.addClass('number');
		actionValueText.attr('minvalue', 16);
		actionValueText.attr('maxvalue', 28);
		actionValueText.closest("tr").find("td:first-child").find("span").show();
		var ModeOption = "";
		
		    ModeOption += "<option value='10'>Auto</option>";
		
		    ModeOption += "<option value='2'>Cool</option>";
		
		    ModeOption += "<option value='8'>Dry</option>";
		
		    ModeOption += "<option value='1'>Heat</option>";
			
			ModeOption += "<option value='6'>Fan</option>";
		FanmodevalueText.html(ModeOption);	
		actionValueText.show();
		FanmodevalueText.show();
	}else if((selectedText == "Curtain Open")||(selectedText == "Curtain Close")){
		
		actionValueText.attr('minvalue', 0);
		actionValueText.attr('maxvalue', 100);
		actionValueText.val(100);
		actionValueText.closest("tr").find("td:first-child").find("span").hide();

	}else if(selectedText == "Move to Preset"){
		
		var CuratinValue = cRow.find('#CurtainRule');
		CuratinValue.hide();
		actionValueText.hide();

		var campreset = $(this).find("option:selected").attr("preset");
		
		
		var presetOption = "";
		if( (campreset & 1) > 0 )
		    presetOption += "<option value='1'>1</option>";
		if( (campreset & 2) > 0 )
		    presetOption += "<option value='2'>2</option>";
		if( (campreset & 4) > 0 )
		    presetOption += "<option value='3'>3</option>";
		if( (campreset & 8) > 0 )
		    presetOption += "<option value='4'>4</option>";
		presetValueText.html(presetOption);	
		presetValueText.show();
	}else if(selectedText == "Update neighbour"){
		var count = $("#updateNeighborCount").val(parseInt($("#updateNeighborCount").val())+1);
		actionValueText.removeClass('required');
		actionValueText.removeClass('number');
		actionValueText.removeClass('editdisplayStar');
		actionValueText.removeAttr('minvalue');
		actionValueText.removeAttr('maxvalue');
		actionValueText.closest("tr").find("td:first-child").find("span").hide();
		actionValueText.hide();
		
	}else{
		actionValueText.removeClass('required');
		actionValueText.removeClass('number');
		actionValueText.removeClass('editdisplayStar');
		actionValueText.removeAttr('minvalue');
		actionValueText.removeAttr('maxvalue');
		actionValueText.closest("tr").find("td:first-child").find("span").hide();
		actionValueText.hide();

	}
	var selectedDeviceId = cRow.find('.selectActionDevice').val();
	var cActionId = $(this).val();

	var cExpression = "";
	var cExpression1 = "";
	
	var gateway = cRow.find('.selectActionGateway').val();
	cExpression += gateway+"=";
	cExpression1 += gateway+"="
	
	if($.trim(selectedDeviceId + cActionId) != ""){
		cExpression += selectedDeviceId + "=" + cActionId;
		cExpression1 += selectedDeviceId + "=" + cActionId;
	}
	
	var pValueText = cRow.find('.camerapresetvalueclass').val();
	
		if(pValueText == null){
		pValueText = "";
		}else{
		cExpression1 += ("=" + pValueText);
		}
	var cActionValueText = cRow.find('input[type="text"]');
	//if(cActionValueText.is(":visible") || (selectedText == "Curtain Open"|| selectedText == "Curtain Close" )){

	
	
	if(cActionValueText.val() != "")
	{
		if(cActionValueText.is(":visible"))
		{
		cExpression += ("=" + cActionValueText.val());
		cExpression1 += ("=" + cActionValueText.val());
		}

	}
		
	
	cRow.find('input[type="hidden"]').val(cExpression);
	cRow.find('input[type="hidden"]').val(cExpression1);
	
	$("#defineActionsHidden").val("");
	
	$('input[name="actionExpressions"]').each(function(){
		$("#defineActionsHidden").val($("#defineActionsHidden").val() + $(this).val());
	});
	
	return true;
};

var removeCurrentRow = function(){
	/*var cRow = $(this).parent().parent();
	var remainingRowCount = cRow.siblings().size();
	var isFinalRow = remainingRowCount == 1;
	var selects = $(this).closest('table').find('select');
	if(!isFinalRow){
		cRow.remove();
	}
	//selects.change();parishodcommented 
	return false; KANTH RAJ COMMENTED*/
	var cRow = $(this).parent().parent();
	var cActionId = cRow.find(".selectActionName").val();
//	var cActionField = $(this).closest('input[name="actionExpressions"]').val("");
	var remainingRowCount = cRow.siblings().size();
	var isFinalRow = remainingRowCount == 1;			
	var selects = $(this).closest('table').find('select');
	if(!isFinalRow){
		if(cActionId)
		{
			//vibhu start
			lastAction = cRow.siblings(':last');
			if(lastAction.find(".selectActionName").val())
			{
				var cloneLastRow = lastAction.clone();
				lastAction.after(cloneLastRow);
				cloneLastRow.find('.selectActionDevice').change();
				remainingRowCount+=1;
			}
			//vibhu end					
			cRow.remove();
			//vibhu added validations
			Xpeditions.validateElement($('.currentstep'));
			Xpeditions.validateForm($('#ruleForm'));
			remainingRowCount-=1;
		}
		//vibhu added elseif
		else
		{
			jQuery.each(cRow.nextAll(), function() 
			{
				if(!$(this).find(".selectActionName").val())
				{
					cRow.remove();
					return;
				}
			});
		}
		
	}

	if(remainingRowCount==1)
	{
		selects.change();
	}
	return false;
};


var removeCurrentRowEnd = function(){
	
	var cRow = $(this).parent().parent();
	var cActionId = cRow.find(".selectActionNameEnd").val();
	var remainingRowCount = cRow.siblings().size();
	var isFinalRow = remainingRowCount == 1;
	var selects = $(this).closest('table').find('select');
	
	if(!isFinalRow){
		if(cActionId)
		{
			
			//vibhu start
			lastAction = cRow.siblings(':last');
			if(lastAction.find(".selectActionNameEnd").val())
			{
				
				var cloneLastRow = lastAction.clone();
				lastAction.after(cloneLastRow);
				cloneLastRow.find('.selectActionDeviceEnd').change();
				remainingRowCount+=1;
			}
			
					
			cRow.remove();
			Xpeditions.validateElement($('.currentstep'));
			Xpeditions.validateForm($('#ruleForm'));
			remainingRowCount-=1;
		}
		//vibhu added elseif
		else
		{
			
			jQuery.each(cRow.nextAll(), function() 
			{
			
				if(!$(this).find(".selectActionNameEnd").val())
				{
					
					cRow.remove();
					return;
				}
			});
		}
		
	}
	
	if(remainingRowCount==1)
	{
		
		selects.change();
	}
	return false;
};





var handleActionValueChange = function(){
	  var theInput = document.getElementById("favcolor");
	    var theColor = theInput.value;
	    theInput.addEventListener("input", function() {
	    
	    document.getElementById("hex").innerHTML = theInput.value;
	    }, false);
	alert("color code "+$(".colorvalueclass").val());
	var cRow = $(this).parent().parent();
	var cDeviceId = cRow.find('.selectActionDevice').val();
	var cActionId = cRow.find('.selectActionName').val();
	var cColorId  = cRow.find('.colorvalueclass').val();
	alert("cColorId="+cColorId);
	
	
	var experssion = "";
	if(cColorId == null){
		cColorId = "";
	}else{
	expression += ("=" + cColorId);
	}
	
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var cActionCheckBox = cRow.find('input[type="checkbox"]');
		
		if(cActionCheckBox.is(':checked'))
		{
			experssion += "1=";
		}
		else
		{
			experssion += "0=";
		}
	}
	
	var cValue="";
	var FanmodevalueText = cRow.find('.FanModevalue').val();
	
	if(FanmodevalueText == null)
	{
		cValue = $(this).val();
		
	}
	else
	{
		
		cValue = $(this).val()+":"+FanmodevalueText;
	}
		
	if($.trim(cDeviceId + cActionId + cValue) != ""){
		experssion += cDeviceId + "=" + cActionId + "=" + cValue;
	}
	cRow.find('input[type="hidden"]').val(experssion); 
	return true;
};


var handleActionValueChangeEnd = function(){
	var cRow = $(this).parent().parent();
	var cDeviceId = cRow.find('.selectActionDeviceEnd').val();
	var cActionId = cRow.find('.selectActionNameEnd').val();
	
	
	var experssion = "";
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var cActionCheckBox = cRow.find('input[type="checkbox"]');
		
		if(cActionCheckBox.is(':checked'))
		{
			experssion += "1=";
		}
		else
		{
			experssion += "0=";
		}
	}
	
	var cValue="";
	var FanmodevalueText = cRow.find('.FanModevalueEnd').val();
	if(FanmodevalueText == null)
	{
		cValue = $(this).val();
		
	}
	else
	{
		
		cValue = $(this).val()+":"+FanmodevalueText;
	}
	
	

	if($.trim(cDeviceId + cActionId + cValue) != ""){
		experssion += cDeviceId + "=" + cActionId + "=" + cValue;
	}
	cRow.find('input[type="hidden"]').val(experssion); 
	return true;
};

var handleFANModeChange = function(){
	var cRow = $(this).parent().parent();
	var cDeviceId = cRow.find('.selectActionDevice').val();
	var cActionId = cRow.find('.selectActionName').val();
	var cActionValueText = cRow.find('input[type="text"]');
	var cValue = "";
	
	var actionValueText = cRow.find('.actionvalueclass');
	//alert("$(this).val()=="+$(this).val());
	if($(this).val()==6)
	{
	actionValueText.removeClass('required');
	actionValueText.removeClass('editdisplayStar');
	actionValueText.closest("tr").find("td:first-child").find("span").hide();
	actionValueText.removeClass('number');
	actionValueText.removeAttr('minvalue');
	actionValueText.removeAttr('maxvalue');
	actionValueText.hide();
	actionValueText.change();
	cValue = $(this).val();
	}
	else if($(this).val()!= null)
	{
		actionValueText.show();
		actionValueText.addClass('required');
		actionValueText.addClass('editdisplayStar');
		actionValueText.closest("tr").find("td:first-child").find("span").show();
		actionValueText.addClass('number');
		actionValueText.attr('minvalue', 16);
		actionValueText.attr('maxvalue', 28);
		actionValueText.val(actionValueText.val());
		actionValueText.change();
		cValue = cActionValueText.val()+":"+$(this).val();
	}
	else
	{
		cValue = actionValueText.val();
		
	}
	var experssion = "";
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var cActionCheckBox = cRow.find('input[type="checkbox"]');
		
		if(cActionCheckBox.is(':checked'))
		{
			experssion += "1=";
		}
		else
		{
			experssion += "0=";
		}
	}
	
	if($.trim(cDeviceId + cActionId + cValue) != ""){
		experssion += cDeviceId + "=" + cActionId + "=" + cValue;
	}
	cRow.find('input[type="hidden"]').val(experssion); 
	return true;
};


var handleFANModeChangeEnd = function(){
	var cRow = $(this).parent().parent();
	var cDeviceId = cRow.find('.selectActionDeviceEnd').val();
	var cActionId = cRow.find('.selectActionNameEnd').val();
	var cActionValueText = cRow.find('input[type="text"]');
	var cValue = "";
	
	var actionValueText = cRow.find('.actionvalueclassEnd');
	//alert("$(this).val()=="+$(this).val());
	if($(this).val()==6)
	{
	actionValueText.removeClass('required');
	actionValueText.removeClass('editdisplayStar');
	actionValueText.closest("tr").find("td:first-child").find("span").hide();
	actionValueText.removeClass('number');
	actionValueText.removeAttr('minvalue');
	actionValueText.removeAttr('maxvalue');
	actionValueText.hide();
	actionValueText.change();
	cValue = $(this).val();
	}
	else if($(this).val()!= null)
	{
		actionValueText.show();
		actionValueText.addClass('required');
		actionValueText.addClass('editdisplayStar');
		actionValueText.closest("tr").find("td:first-child").find("span").show();
		actionValueText.addClass('number');
		actionValueText.attr('minvalue', 16);
		actionValueText.attr('maxvalue', 28);
		actionValueText.val(actionValueText.val());
		actionValueText.change();
	cValue = cActionValueText.val()+":"+$(this).val();
	
	}
	else
	{
		cValue = actionValueText.val();
	}
	var experssion = "";
	if(rss == "ruleadd" || rss == "ruleedit")
	{
		var cActionCheckBox = cRow.find('input[type="checkbox"]');
		
		if(cActionCheckBox.is(':checked'))
		{
			experssion += "1=";
		}
		else
		{
			experssion += "0=";
		}
	}
	
	
	

	if($.trim(cDeviceId + cActionId + cValue) != ""){
		experssion += cDeviceId + "=" + cActionId + "=" + cValue;
	}
	cRow.find('input[type="hidden"]').val(experssion); 
	return true;
};



function toggleSteps(nextStep, validateactions)
{
	//vibhu added if
	if(validateactions == true)
	{
		if(validateActions() == false) return false;
	}
	for(var i=1;i<=4;i++)
	{
		$("#step"+i).hide();
		$("#step"+i).removeClass('currentstep');
	}
	$("#step"+nextStep).show();
	$("#step"+nextStep).addClass('currentstep');
	
	Xpeditions.validateElement($("#step"+nextStep));
	
	var title = "";
	if(rss == "ruleadd")
	{
		title = formatMessage("setup.toggle.rule")+nextStep+" "+formatMessage("setup.toggle.step.4");
		
		
		var cForm = $("#step"+nextStep);
		var inputs = cForm.find('input');
		if(nextStep==1)
		{
		inputs.each(function(index, inp)
		{
		var input = $(inp);
		if(input.hasClass('required'))
		{
			input.before("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: -5px 0px 0px -191px;'>*</span>");

		}
		});
		}
		else if(nextStep==2)
		{
		inputs.each(function(index, inp)
		{
		var input = $(inp);
		if(input.hasClass('required'))
		{
			input.before("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin:-3px -18px 0px -7px;'>*</span>");

		}
		});
		}
	}
	else if(rss == "scheduleadd")
	{
		title = formatMessage("setup.toggle.schedule")+nextStep+" "+formatMessage("setup.toggle.step.4");
	 
	 
	 var cForm = $("#step"+nextStep);
	var inputs = cForm.find('input');
	if(nextStep==1)
		{
		inputs.each(function(index, inp)
		{
		var input = $(inp);
		if(input.hasClass('required'))
		{
			input.before("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: -5px 0px 0px -131px;'>*</span>");

		}
		});
		}
		else if(nextStep==2)
		{
	inputs.each(function(index, inp)
		{
		var input = $(inp);
		if(input.hasClass('required'))
		{
			input.before("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: 7px -24px -15px -5px;'>*</span>");

		}
		});
		}
		else if(nextStep==3)
		{
	inputs.each(function(index, inp)
		{
		var input = $(inp);
		/*if(input.hasClass('required'))
		{
			input.before("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: -5px -24px -15px -5px;'>*</span>");

		}*/
		});
		}
	}
	else if(rss == "scenarioadd")
	{
		title = formatMessage("setup.toggle.scenario")+nextStep+" "+formatMessage("setup.toggle.step.3");
		var cForm = $("#step"+nextStep);
	var inputs = cForm.find('input');
	if(nextStep==1)
		{
		inputs.each(function(index, inp)
		{
		var input = $(inp);
		if(input.hasClass('required'))
		{
			input.before("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: -5px 0px 0px -161px;'>*</span>");

		}
		});
		}
		else if(nextStep==2)
		{
	inputs.each(function(index, inp)
		{
		var input = $(inp);
		/*if(input.hasClass('required'))
		{
			input.before("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin:-3px -18px 0px -7px;'>*</span>");

		}*/
		});
		}
	}
	
	$("#editmodal").dialog('option', 'title', title);
}



//vibhu added function
function validateActions() 
{
	var selections = $(".selectActionDevice").find(":selected");
	
	//validate camera does not appear more than twice
	var cameraCount = selections.filter('[devicetype="IP_CAMERA"]').length ;
	if(cameraCount > 2)
	{
		showResultAlert("You have added "+cameraCount+" actions on cameras. <br/> Please do not add more than two actions on cameras.");
		return false;
	}
	return true;
};
