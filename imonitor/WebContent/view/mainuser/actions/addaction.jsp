<%-- Copyright Ã‚Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ page language="java" %>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>




<br>
<div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
		<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<s:form id="editModeForm" theme="simple" action="saveAction.action" method="POST" cssClass="ajaxinlinepopupform" >
<s:hidden name="gateWay.id" id="forhiddenfieldchange"></s:hidden>
<s:hidden name="gateWay.macId" ></s:hidden>
<s:hidden name="aName" id="aName"></s:hidden>
<s:hidden name="actionName" id="actionName"></s:hidden>
<s:hidden name="action.device.id" id="device"></s:hidden>
<s:hidden name="action.scenario.id" id="scenario"></s:hidden>
<input type="hidden" id="ValidFunction"/>
<input type="hidden" id="ValidDevice"/>
<table>
<thead>
</thead>
<tbody>
<tr>
<th Width="20px">Action Name</th>
<th Width="20px">Function Name</th>
<th Width="120px">DeviceId/Scenario</th>
<th Width="120px"><div id="headparameter">Parameter</div></th>
</tr>
<tr>
<td><s:textfield name="action.actionName" cssClass="required alphanumeric"></s:textfield></td>
<td Width="20px"><s:select name = "action.functions.id" id="actionCategory" listKey="name" list = "functions" listValue="name"  onchange="categoryy()" headerKey="-1" headerValue="Select Function" cssClass="required" /></td>
<td Width="120px"><select name = "action.configurationId" id="devicelist" class="required" onchange="deviceAction()"><option value='-1'>Select Device</option></select></td>
<td Width="120px"><div id="parameter"><s:textfield name = "action.parameter" id="" key = ""  /></div></td>
</tr>
</tbody>
</table>
<s:submit id="mySubmit" value="Save"></s:submit>
</s:form>

<script>
var userDetails = new UserDetails();
var selectedGateWay = "";
var gateWayCount = 0;
$(document).ready(function(){
	$("#parameter").hide();
	$("#headparameter").hide();
	var cForm = $("#editModeForm");
	Xpeditions.validateForm(cForm);
	$("#ValidFunction").addClass("required");
	$("#ValidDevice").addClass("required");
	
	
	var handleSuccess = function(xml){
		//arrangeGateWaySectionForSetup(xml);
		// 1. Handling the gatewy.
		userDetails.setGateWayCount($(xml).find("gateway").size());
		 gateWayCount = $(xml).find("gateway").size();
	};
	
	var showUserDetails = function(){
		$.ajax({
			url: "userdetails.action",
			cache: false,
			dataType: 'xml',
			success: handleSuccess
		});
		setTimeout(showUserDetails,10000);
	};
	showUserDetails();
	
	
	
	
	
	
	
});

var resetDeviceOption = function(){
	//Reset Brand Options
	$("#deviceCategory").empty();
};
function deviceAction(){
	var Selected = $("#devicelist").val();
	//alert(Selected);
	if (Selected == "-1") 
	{
		$("#ValidDevice").val("");
		cForm.find('.navigate').attr('disabled', 'disabled');
	}
	$("#ValidDevice").val(Selected);
	
}
function categoryy(){
	var actionSelected = $("#actionCategory").val();
	$("#ValidFunction").val(actionSelected);
	if (actionSelected=="SCENARIO") 
	{	
		$("#devicelist").empty();
		//alert("Scenario block");
		var options = document.getElementById("actionCategory").options;
		 var selectedIndex = document.getElementById("actionCategory").selectedIndex; 
		 var selectedOptionText = options[selectedIndex].text;
	     $("#aName").val(selectedOptionText);
	     $("#actionName").val(actionSelected);
	     
		var targetUrl = "getScenarioListByAction.action";
		var id= $("#forhiddenfieldchange").val();
		var params = {"actionName" : actionSelected,"gateWay.id" :id };
		$.ajax({

			url: targetUrl,
			data: params,
			dataType: 'xml',
			success: handleScenarioList
		});	
		
	} 
	else if (actionSelected=="CURTAIN_OPEN"|| actionSelected=="CURTAIN_CLOSE")
		
	{
		actionSelected ="Z_WAVE_MOTOR_CONTROLLER";
		var options = document.getElementById("actionCategory").options;
		 var selectedIndex = document.getElementById("actionCategory").selectedIndex; 
		 var selectedOptionText = options[selectedIndex].text;
	     $("#aName").val(selectedOptionText);
	     $("#actionName").val(actionSelected);
			var targetUrl = "getDeviceListByAction.action";
			
			/* 3 gateways changes */
		     if (gateWayCount > 1) 
				{
					targetUrl = "3gatewayDevices.action";
					var id= $("#forhiddenfieldchange").val();
					var params = {"actionName" : actionSelected,"gateWay.id" :id };
					$.ajax({
						url: targetUrl,
						data: params,
						dataType: 'xml',
						success: handlefor3GatewaysMotor
					});	
				}
		     else{ 

			var id= $("#forhiddenfieldchange").val();
			var params = {"actionName" : actionSelected,"gateWay.id" :id };
			$.ajax({

				url: targetUrl,
				data: params,
				dataType: 'xml',
				success: handleCategorySelectChange
			});	
		     }
		     /* 3 gateways changes end*/
		//	alert(selectedOptionText);
	}
	else if (actionSelected=="DIM_UP"||actionSelected=="DIM_DOWN")
		
	{
		actionSelected ="Z_WAVE_DIMMER";
		var options = document.getElementById("actionCategory").options;
		 var selectedIndex = document.getElementById("actionCategory").selectedIndex; 
		 var selectedOptionText = options[selectedIndex].text;
	     $("#aName").val(selectedOptionText);
	     $("#actionName").val(actionSelected);
			var targetUrl = "getDeviceListByAction.action";
			
			/* 3 gateways changes */
		     if (gateWayCount > 1) 
				{
					targetUrl = "3gatewayDevices.action";
					var id= $("#forhiddenfieldchange").val();
					var params = {"actionName" : actionSelected,"gateWay.id" :id };
					$.ajax({
						url: targetUrl,
						data: params,
						dataType: 'xml',
						success: handlefor3GatewaysDimmer
					});	
				}	
		     else{

			var id= $("#forhiddenfieldchange").val();
			var params = {"actionName" : actionSelected,"gateWay.id" :id };
			$.ajax({

				url: targetUrl,
				data: params,
				dataType: 'xml',
				success: handleCategorySelectChange
			});	
	    	 
		     }
		     /* 3 gateways changes end*/
	}
	
	//Added For SceneController Panic
	else if (actionSelected=="PANIC_SITUATION")
		{
		actionSelected ="DEV_ZWAVE_SCENE_CONTROLLER";
		var options = document.getElementById("actionCategory").options;
		 var selectedIndex = document.getElementById("actionCategory").selectedIndex; 
		 var selectedOptionText = options[selectedIndex].text;
	     $("#aName").val(selectedOptionText);
	     $("#actionName").val(actionSelected);
			var targetUrl = "getDeviceListByAction.action";
			/* 3 gateways changes */
		     if (gateWayCount > 1) 
				{
					targetUrl = "3gatewayDevices.action";
					var id= $("#forhiddenfieldchange").val();
					var params = {"actionName" : actionSelected,"gateWay.id" :id };
					$.ajax({
						url: targetUrl,
						data: params,
						dataType: 'xml',
						success: handlefor3GatewaysScene
					});	
				}	
		     else{
	
			var id= $("#forhiddenfieldchange").val();
			var params = {"actionName" : actionSelected,"gateWay.id" :id };
			$.ajax({

				url: targetUrl,
				data: params,
				dataType: 'xml',
				success: handleCategorySelectChange
			});	
			/* 3 gateways changes end*/
		     }
			
		}
	
	else
	{
		$("#devicelist").empty();
		//alert("Device block");
		var targetUrl = "getDeviceListByAction.action";
		
		/* 3 gateways changes */
		//Modification done to change targetUrl for 2WAY switch
		if (gateWayCount > 1) 
		{
		
			actionSelected = "Z_WAVE_SWITCH";
		}	
		else
		{	
			//Single gateway block
		if(actionSelected=="2_WAY"){
				actionSelected = "Z_WAVE_SWITCH";
				targetUrl = "getNonHmdDeviceListByAction.action";
		}else{
				actionSelected = "Z_WAVE_SWITCH";
		}
		}
		/* 3 gateways changes end*/
		
		var options = document.getElementById("actionCategory").options;
		 var selectedIndex = document.getElementById("actionCategory").selectedIndex; 
		 var selectedOptionText = options[selectedIndex].text;
	     $("#aName").val(selectedOptionText);
	     
	     $("#actionName").val(actionSelected);
	   /* 3 gateways changes */
	   //Added for multiple gateways
	     if (gateWayCount > 1) 
			{
	    	 	if (actionSelected=="2_WAY") 
	    	 	{
	    	 		actionSelected = "Z_WAVE_SWITCH";
	    			targetUrl = "3gatewayDevicesHMD.action";
				}
	    	 	else
	    	 	{
	    	 		targetUrl = "3gatewayDevices.action";
	    	 	}
	    	 	
				var id= $("#forhiddenfieldchange").val();
				var params = {"actionName" : actionSelected,"gateWay.id" :id };
				$.ajax({
					url: targetUrl,
					data: params,
					dataType: 'xml',
					success: handlefor3GatewaysSwitches
				});	
			}	     
	     else
	    	 {
			var id= $("#forhiddenfieldchange").val();
			var params = {"actionName" : actionSelected,"gateWay.id" :id };
			$.ajax({
				url: targetUrl,
				data: params,
				dataType: 'xml',
				success: handleCategorySelectChange
			});	
	    	 }
	     /* 3 gateways changes */
	     
	}
	  
}

var handleCategorySelectChange = function(xml) {
	var XMLS = new XMLSerializer(); 
	var inp_xmls = XMLS.serializeToString(xml);
	
	$("#devicelist").empty();
	var deviceDefaultOption = "<option value='-1'>Select Device</option>";
	$("#devicelist").append(deviceDefaultOption);
	var actionSelected = $("#actionCategory").val();
	try{
		$(xml).find("Device").each(function() {
			var deviceName = $(this).find("friendlyName").text();
			var Id = $(this).find("id").text();
		
			$("#devicelist").append("<option value='"+Id+"'>"+deviceName+"</option>");
							
		});
	}catch(err){
		alert(err.message);
	}
	$("#devicelist").show();
};

var handleScenarioList = function(xml) {
//	alert("handleScenarioList");
	 var XMLS = new XMLSerializer(); 
	var inp_xmls = XMLS.serializeToString(xml);
	
	$("#devicelist").empty();
	var deviceDefaultOption = "<option value='-1'>Select Scenario</option>";
	$("#devicelist").append(deviceDefaultOption);
	try{
		$(xml).find("Scenario").each(function() {
			var ScenarioName = $(this).find("name").text();
			var Id = $(this).find("id").text();
			$("#devicelist").append("<option value='"+Id+"'>"+ScenarioName+"</option>");
							
		});
	}catch(err){
		alert(err.message);
	}
	$("#devicelist").show();
};

/* 3 gateways changes start*/
var handlefor3GatewaysSwitches = function(xml) {
	var XMLS = new XMLSerializer(); 
	var inp_xmls = XMLS.serializeToString(xml);
	
	$("#devicelist").empty();
	var deviceDefaultOption = "<option value='-1'>Select Device</option>";
	$("#devicelist").append(deviceDefaultOption);
	try{
		$(xml).find("device").each(function() {
			var deviceName = $(this).find("name").text();
			var Id = $(this).find("index").text();
			var devicetype = $(this).find("devicetype").text();
			//alert("devicetype : "+devicetype);
			var actionSelected = $("#actionCategory").val();
		 if ( devicetype == "Z_WAVE_SWITCH" &&  devicetype != "MODE_HOME" &&  devicetype != "MODE_STAY" &&  devicetype != "MODE_AWAY" ) 
		{ 
					$("#devicelist").append("<option value='"+Id+"'>"+deviceName+"</option>");	
		} 
							
		});
	}
	catch(err){
		alert(err.message);
	}
	$("#devicelist").show();
};
var handlefor3GatewaysScene = function(xml) {
	var XMLS = new XMLSerializer(); 
	var inp_xmls = XMLS.serializeToString(xml);
	
	$("#devicelist").empty();
	var deviceDefaultOption = "<option value='-1'>Select Device</option>";
	$("#devicelist").append(deviceDefaultOption);
	try{
		$(xml).find("device").each(function() {
			var deviceName = $(this).find("name").text();
			var Id = $(this).find("index").text();
			var devicetype = $(this).find("devicetype").text();
			//alert("devicetype : "+devicetype);
			
		 if ( devicetype == "DEV_ZWAVE_SCENE_CONTROLLER" &&  devicetype != "MODE_HOME" &&  devicetype != "MODE_STAY" &&  devicetype != "MODE_AWAY" ) 
		{ 
					$("#devicelist").append("<option value='"+Id+"'>"+deviceName+"</option>");	
		} 
		 				
		});
	}catch(err){
		alert(err.message);
	}
	$("#devicelist").show();
};
var handlefor3GatewaysDimmer = function(xml) {
	var XMLS = new XMLSerializer(); 
	var inp_xmls = XMLS.serializeToString(xml);
	
	$("#devicelist").empty();
	var deviceDefaultOption = "<option value='-1'>Select Device</option>";
	$("#devicelist").append(deviceDefaultOption);
	try{
		$(xml).find("device").each(function() {
			var deviceName = $(this).find("name").text();
			var Id = $(this).find("index").text();
			var devicetype = $(this).find("devicetype").text();
			//alert("devicetype : "+devicetype);
			
		 if ( devicetype == "Z_WAVE_DIMMER" &&  devicetype != "MODE_HOME" &&  devicetype != "MODE_STAY" &&  devicetype != "MODE_AWAY" ) 
		{ 
					$("#devicelist").append("<option value='"+Id+"'>"+deviceName+"</option>");	
		} 
							
		});
	}catch(err){
		alert(err.message);
	}
	$("#devicelist").show();
};
var handlefor3GatewaysMotor = function(xml) {
	var XMLS = new XMLSerializer(); 
	var inp_xmls = XMLS.serializeToString(xml);
	
	$("#devicelist").empty();
	var deviceDefaultOption = "<option value='-1'>Select Device</option>";
	$("#devicelist").append(deviceDefaultOption);
	try{
		$(xml).find("device").each(function() {
			var deviceName = $(this).find("name").text();
			var Id = $(this).find("index").text();
			var devicetype = $(this).find("devicetype").text();
			//alert("devicetype : "+devicetype);
			
		 if ( devicetype == "Z_WAVE_MOTOR_CONTROLLER" &&  devicetype != "MODE_HOME" &&  devicetype != "MODE_STAY" &&  devicetype != "MODE_AWAY" ) 
		{ 
					$("#devicelist").append("<option value='"+Id+"'>"+deviceName+"</option>");	
		} 
							
		});
	}catch(err){
		alert(err.message);
	}
	$("#devicelist").show();
};


/* 3 gateways changes  end*/

</script>

