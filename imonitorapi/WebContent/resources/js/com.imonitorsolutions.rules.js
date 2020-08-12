/*!
 * Copyright © 2012 iMonitor Solutions India Private Limited
 */


var handleAlertSelectChange = function(ev){

	var selectedDeviceId = $(this).val();
	if(selectedDeviceId == ""){
		// User have selected 'Select A Device' Option.
		var cRow = $(this).parent().parent();
		cRow.find(".selectAlertName").html("<option value=''>No Device Selected.</option>");
		cRow.find('input[type="hidden"]').val(" ");
		cRow.find(".selectAlertName").change();
		return;
	}
	var selectedGateWayId = $("#gatewayselect").val();
	var selectedGateWayDetails = gateWayDetailsMap[selectedGateWayId];
	var devices = selectedGateWayDetails["devices"];
	var alerts = devices[selectedDeviceId]["alerts"];
	var modelNumber = devices[selectedDeviceId]["modelNumber"];
	var deviceType = devices[selectedDeviceId]["deviceType"];
	//vibhu start
	var devName = devices[selectedDeviceId]["devicename"];
	if(devName == "Home" || devName == "Stay" || devName == "Away")
	{
		if(rss == "ruleadd")
				$("#rule_mode8").attr("checked","checked");
		else if(rss == "ruleedit")
				$("#ruleForm_rule_mode8").attr("checked","checked");
		
		$('#StartHourSelect').val('0');
		$('#StartMinuteSelect').val('0');
		$('#EndHourSelect').val('0');
		$('#EndMinuteSelect').val('0');
		handleModeRadioChange();
		$("#securityModeDiv").hide();
	}
	else
	{
		$("#securityModeDiv").show();
	}
	
	//vibhu end
	var alertOptionHtml = "";
	for(var alertId in alerts){
		var alertDetails = alerts[alertId];
		var alertName = alertDetails["name"];
		if(modelNumber == "ZXT110"){
			if(alertName == "Sensed Temperature"){
				continue;
			}
		}
		//vibhu allow DEVICE_UP and DEVICE_DOWN rules for only SWITCH and DIMMER
		if((alertName == "Device Up" || alertName == "Device Down" || alertName == "Device On" || alertName == "Device Off") && (deviceType != "Z_WAVE_SWITCH" && deviceType != "Z_WAVE_DIMMER"))continue;
		
         
		
		alertOptionHtml += "<option an='"+alertName+"' value='" + alertId + "'>" + formatMessage(alertDetails["details"]) + "</option>";
		
	}
	var cRow = $(this).parent().parent();
	cRow.find(".selectAlertName").html(alertOptionHtml);
	cRow.find(".selectAlertName").change();

	try{	
	}
	catch(Err){alert("ERR "+Err.message);}
};

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

var handleModeCheckChange = function(ev)
{
	
	if($(".Home").attr('checked'))
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

/*$(".modecheck").live('click', function(){
	
	 $('input[name="modeselect"]:checked').each(function(){
		var userSelectMode = $(this).val();
		 if(userSelectMode == "Stay" &&  userSelectMode != "Away" && userSelectMode != "Home"){
			
			 $("#timetable").hide();
			 $("#selectedMode").val("3");
			
		 }else if(userSelectMode == "Home"){
			
			 $("#timetable").show();
			 $("#selectedMode").val("8");
		 }else if(userSelectMode == "Stay"){
			 alert("stay");
			 $("#selectedMode").val("2");
		 }else if(userSelectMode == "Away"){
			 
			 $("#timetable").hide();
			 $("#selectedMode").val("1");
		 }
	 });
	
	
});*/

var handleStartEndChange = function(ev){
	var cRow = $("#firstalertrow");
	var tRow = $("#timetable");
	var cDeviceId = cRow.find('.selectAlertDevice').val();
	var cStartHours = tRow.find('#StartHourSelect').val();
	var cStartMinutes = tRow.find('#StartMinuteSelect').val();
	var cEndHours = tRow.find('#EndHourSelect').val();
	var cEndMinute = tRow.find('#EndMinuteSelect').val();

	var cAlertId = cRow.find('.selectAlertName').val();
	var alertValueText = cRow.find('.alertvalueclass').val();
	var Value = cRow.find('.alertvalue').val();

	//vibhu added
	var alertName = cRow.find('.selectAlertName option:selected').attr("an");
	
	var deviceSpecificAlertValue = cRow.find('.selectDeviceSpecificAlert').val();
	
	var valueofdeviceSpecificAlert = cRow.find('.selectDeviceSpecific').val();

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
		experssion += "="+alertName; //vibhu added
		
		if(valueofdeviceSpecificAlert==""||valueofdeviceSpecificAlert==null){
			valueofdeviceSpecificAlert=0;
		}
		experssion += "="+valueofdeviceSpecificAlert+"="+Value;	
		experssion += "="+alertName; //vibhu added
		
		
	}
	cRow.find('input[type="hidden"]').val(experssion); 
	$("#criteriaValidateHidden").val("");
	$('input[name="alertExpressions"]').each(function(){
		$("#criteriaValidateHidden").val($("#criteriaValidateHidden").val() + $(this).val());
	});
};

//********************************************************** sumit start: ZXT120 ************************************************** 
var handleAlertValueChange = function(ev){
	var alertValueText = $(this).val();
	var cRow = $("#firstalertrow");
	var tRow = $("#timetable");
	var cDeviceId = cRow.find('.selectAlertDevice').val();
	var cStartHours = tRow.find('#StartHourSelect').val();
	var cStartMinutes = tRow.find('#StartMinuteSelect').val();
	var cEndHours = tRow.find('#EndHourSelect').val();
	var cEndMinute = tRow.find('#EndMinuteSelect').val();
	var deviceSpecificAlertValue = cRow.find('.selectDeviceSpecificAlert').val();
	//vibhu added
	var alertName = cRow.find('.selectAlertName option:selected').attr("an");
   	if(deviceSpecificAlertValue==null)
		{
		deviceSpecificAlertValue="1";
		}
	if(alertValueText=="")
	{
		alertValueText="0";
	}
	var cAlertId = cRow.find('.selectAlertName').val();
	var experssion = "";
	if($.trim(cDeviceId + cAlertId) != ""){
		experssion = cDeviceId + "=" + cAlertId+ "=" +cStartHours+ "=" +cStartMinutes+ "=" +cEndHours+ "=" +cEndMinute+"="+deviceSpecificAlertValue+"="+alertValueText;
	}
	experssion += "="+alertName; //vibhu added
	
	cRow.find('input[type="hidden"]').val(experssion); 
	$("#criteriaValidateHidden").val("");
	$('input[name="alertExpressions"]').each(function(){
		$("#criteriaValidateHidden").val($("#criteriaValidateHidden").val() + $(this).val());
	});
	
	
};

var handleDeviceSpecificAlertChange = function(ev){
	var cRow = $("#firstalertrow");
	var tRow = $("#timetable");
	var selectedText = cRow.find('.selectDeviceSpecificAlert').val();
	var cDeviceId = cRow.find('.selectAlertDevice').val();
	var cStartHours = tRow.find('#StartHourSelect').val();
	var cStartMinutes = tRow.find('#StartMinuteSelect').val();
	var cEndHours = tRow.find('#EndHourSelect').val();
	var cEndMinute = tRow.find('#EndMinuteSelect').val();
	var alertValueText = cRow.find('.alertvalueclass').val();
	//vibhu added
	var alertName = cRow.find('.selectAlertName option:selected').attr("an");

	
	if(alertValueText=="")
	{
		alertValueText="0";
	}
	var cAlertId = cRow.find('.selectAlertName').val();
	var experssion = "";
	if($.trim(cDeviceId + cAlertId) != ""){
		experssion = cDeviceId + "=" + cAlertId+ "=" +cStartHours+ "=" +cStartMinutes+ "=" +cEndHours+ "=" +cEndMinute+"="+selectedText+"="+alertValueText;
	}
	experssion += "="+alertName; //vibhu added
	
	cRow.find('input[type="hidden"]').val(experssion); 
	$("#criteriaValidateHidden").val("");
	$('input[name="alertExpressions"]').each(function(){
		$("#criteriaValidateHidden").val($("#criteriaValidateHidden").val() + $(this).val());
	});
	
};

var handleAlertNameSelectChange = function(ev){
	var cRow = $("#firstalertrow");
	var tRow = $("#timetable");
	var selectedText = $(this).find("option:selected").attr("an");
	
	if(selectedText == "Sensed Temperature" || selectedText == "Sensed light intensity" || selectedText =="Sensed PM Level"){
		var alertvalueOption = $(".alertvalueclass");
		alertvalueOption.addClass('required'); 
		alertvalueOption.addClass('number');
		if(selectedText == "Sensed Temperature")
		{
			alertvalueOption.attr('minvalue',0);
			alertvalueOption.attr('maxvalue',40);
			alertvalueOption.attr('maxlength',2);
			alertvalueOption.attr('size',3);
		}
		else if(selectedText == "Sensed light intensity")
		{
			alertvalueOption.attr('minvalue',30);
			alertvalueOption.attr('maxvalue',3000);
			alertvalueOption.attr('maxlength',4);
			alertvalueOption.attr('size',5);
		}else if(selectedText == "Sensed PM Level")
		{
			alertvalueOption.attr('minvalue',0);
			alertvalueOption.attr('maxvalue',999);
			alertvalueOption.attr('maxlength',4);
			alertvalueOption.attr('size',5);
		}else if(selectedText == "After Delay"){
			deviceSpecificAlertOptions="<s:textfiled label='Minute' name=rule.delayMinute cssClass='required'>"+
			"<s:textfiled name=rule.delaySeconds cssClass='required'>";
		}
		
		
		var deviceSpecificAlertOptions = "<option value='1'>is equal to</option>";
		deviceSpecificAlertOptions += "<option value='2'>is less than</option>";
		deviceSpecificAlertOptions += "<option value='3'>is greater than</option>";
		$('.selectDeviceSpecificAlert').html(deviceSpecificAlertOptions);
		$('.selectDeviceSpecificAlert').change();
		$('.deviceSpecificAlert').show();
    	}
         else if(selectedText == "power limit reached" || selectedText == "power limit warning" )
	    {
		
	
		$('.deviceSpecificAlert').show();
		$('.selectDeviceSpecificAlert').hide();
		var alertvalueOption = cRow.find(".alertvalueclass");
		alertvalueOption.addClass('required'); 
		alertvalueOption.addClass('number');
		alertvalueOption.attr('minvalue',1);
		alertvalueOption.attr('maxvalue',100);
		alertvalueOption.attr('maxlength',3);
		alertvalueOption.show();
    	}
    else{
		var alertvalueOption = $(".alertvalueclass");
		alertvalueOption.removeClass('required');
		$('.deviceSpecificAlert').hide();
	    }
	
	
	//var cRow = $(this).parent().parent();
	
	var cDeviceId = cRow.find('.selectAlertDevice').val();
	var cStartHours = tRow.find('#StartHourSelect').val();
	var cStartMinutes = tRow.find('#StartMinuteSelect').val();
	var cEndHours = tRow.find('#EndHourSelect').val();
	var cEndMinute = tRow.find('#EndMinuteSelect').val();
	var cAlertId = $(this).val();
	var alertValueText = cRow.find('.alertvalueclass').val();
	var deviceSpecificAlertValue = cRow.find('.selectDeviceSpecificAlert').val();
	//vibhu added
	var alertName = cRow.find('.selectAlertName option:selected').attr("an");

	
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
		experssion += "="+alertName; //vibhu added
	}
	cRow.find('input[type="hidden"]').val(experssion); 
	$("#criteriaValidateHidden").val("");
	$('input[name="alertExpressions"]').each(function(){
		$("#criteriaValidateHidden").val($("#criteriaValidateHidden").val() + $(this).val());
	});
};
// ****************************************************** sumit end: ZXT120 ***************************************************************


var handleActionDelayClick = function(){
	var cRow = $(this).parent().parent();
	var cDeviceId = cRow.find('.selectActionDevice').val();
	var cActionId = cRow.find('.selectActionName').val();
	var cActionCheckBox = cRow.find('input[type="checkbox"]');
	var cValue = "";
	if(cActionCheckBox.is(':checked'))
	{
		cValue = 1;

	}
	else
	{
		cValue = 0;

	}
	var cTextValue="";
	var cActionValueText = cRow.find('input[type="text"]');
	//if(cActionValueText.is(":visible")){
		cTextValue=cActionValueText.val();
	//}
	

	
	var experssion = "";
	if($.trim(cValue +cDeviceId + cActionId + cTextValue) != ""){
		if(cTextValue!="")
		{
		experssion =cValue + "=" + cDeviceId + "=" + cActionId + "=" +cTextValue;
		}
		else
		{
			experssion =cValue + "=" + cDeviceId + "=" + cActionId;
		}

	}
	
	cRow.find('input[type="hidden"]').val(experssion); 
	return true;
};

var handaleRuleDelayChange = function(){
   var cValue = $(this).val();
   if((cValue == "") || (cValue==0))
   {
	  
	   $("#defineActionTable tr").each(function() 
	   {
		  //vibhu changed if clause
		  if($(this).find('.selectActionName').val())
		  {
			  var AfterDelay =  $(this).find(".Afterdelay");
			  AfterDelay.attr('checked',false);
			  AfterDelay.attr('disabled','true');

				var cRow = $(this);
				var cDeviceId = cRow.find('.selectActionDevice').val();
				var cActionId = cRow.find('.selectActionName').val();
				var cActionCheckBox = cRow.find('input[type="checkbox"]');
				var cValue = "";
				if(cActionCheckBox.is(':checked'))
				{
					cValue = 1;

				}
				else
				{
					cValue = 0;
		
				}
				var cTextValue="";
				var cActionValueText = cRow.find('input[type="text"]');
				//if(cActionValueText.is(":visible")){
					cTextValue=cActionValueText.val();
				//}
				

				
				var experssion = "";
				if($.trim(cValue +cDeviceId + cActionId + cTextValue) != ""){
					if(cTextValue!="")
					{
					experssion =cValue + "=" + cDeviceId + "=" + cActionId + "=" +cTextValue;
					}
					else
					{
						experssion =cValue + "=" + cDeviceId + "=" + cActionId;
					}
				}
				
				cRow.find('input[type="hidden"]').val(experssion); 
		  }
	   });
   }
   else
	{
	   $("#defineActionTable tr").each(function() {
			//vibhu changed if clause
			if($(this).find('.selectActionName').val())
			  {
				  var AfterDelay =  $(this).find(".Afterdelay");
				  //AfterDelay.attr('checked',false);
				  AfterDelay.removeAttr('disabled');

				  
					var cRow = $(this);
					var cDeviceId = cRow.find('.selectActionDevice').val();
					var cActionId = cRow.find('.selectActionName').val();
					var cActionCheckBox = cRow.find('input[type="checkbox"]');
					var cValue = "";
					if(cActionCheckBox.is(':checked'))
					{
						cValue = 1;

					}
					else
					{
						cValue = 0;
			
					}
					var cTextValue="";
					var cActionValueText = cRow.find('input[type="text"]');
					//if(cActionValueText.is(":visible")){
						cTextValue=cActionValueText.val();
					//}
					

					
					var experssion = "";
					if($.trim(cValue +cDeviceId + cActionId + cTextValue) != ""){
						if(cTextValue!="")
						{
						experssion =cValue + "=" + cDeviceId + "=" + cActionId + "=" +cTextValue;
						}
						else
						{
							experssion =cValue + "=" + cDeviceId + "=" + cActionId;
						}
					}
					
					cRow.find('input[type="hidden"]').val(experssion); 
			  }
		   });
	}
   
   
   
	
};


