<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ page language="java" %>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>




<br>
<div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
		<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<html>
<style>
h1 {
	color: red;
}
</style>
<h1 >Actions not Configured</h1>
<input type="button" name="answer" value="Click To Add Action" onclick="showDiv()" />

<div id="division">
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
<td Width="20px"><s:select name = "action.functions.id" id="actionCategory" listKey="category" list = "functions" listValue="name"  onchange="categoryy()" headerKey="-1" headerValue="Select Function" cssClass="required" /></td>
<td Width="120px"><select name = "action.configurationId" id="devicelist" class="required" style="width:90%;" onchange="deviceAction()"><option value='-1'>Select Device</option></select></td>
<td Width="120px"><div id="parameter"><s:textfield name = "action.parameter" id="" key = ""  /></div></td>
</tr>
</tbody>
</table>
<s:submit value="Save"></s:submit>
</s:form>
</div>
</html>

<script >

function showDiv() {
	$("#division").show();
	}


$(document).ready(function(){
	$("#parameter").hide();
	$("#division").hide();
	$("#headparameter").hide();
	var cForm = $("#editModeForm");
	Xpeditions.validateForm(cForm);
	$("#ValidFunction").addClass("required");
	$("#ValidDevice").addClass("required");
});

var resetDeviceOption = function(){
	//Reset Brand Options
	$("#deviceCategory").empty();
};
function deviceAction(){
	var Selected = $("#devicelist").val();
	$("#ValidDevice").val(Selected);
	
}
function categoryy(){
	var actionSelected = $("#actionCategory").val();
	//alert(actionSelected);
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
	else if (actionSelected=="CURTAIN_OPEN"||actionSelected=="CURTAIN_CLOSE")
	{
		actionSelected ="Z_WAVE_MOTOR_CONTROLLER";
		var options = document.getElementById("actionCategory").options;
		 var selectedIndex = document.getElementById("actionCategory").selectedIndex; 
		 var selectedOptionText = options[selectedIndex].text;
	     $("#aName").val(selectedOptionText);
	     $("#actionName").val(actionSelected);
			var targetUrl = "getDeviceListByAction.action";
			var id= $("#forhiddenfieldchange").val();
			var params = {"actionName" : actionSelected,"gateWay.id" :id };
			$.ajax({

				url: targetUrl,
				data: params,
				dataType: 'xml',
				success: handleCategorySelectChange
			});	
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
			var id= $("#forhiddenfieldchange").val();
			var params = {"actionName" : actionSelected,"gateWay.id" :id };
			$.ajax({

				url: targetUrl,
				data: params,
				dataType: 'xml',
				success: handleCategorySelectChange
			});	
	}
	
	else
	{
		$("#devicelist").empty();
		//alert("Device block");
		if(actionSelected=="2_WAY"){
			actionSelected = "Z_WAVE_SWITCH";
		}
		var options = document.getElementById("actionCategory").options;
		 var selectedIndex = document.getElementById("actionCategory").selectedIndex; 
		 var selectedOptionText = options[selectedIndex].text;
	     $("#aName").val(selectedOptionText);
	     $("#actionName").val(actionSelected);
			var targetUrl = "getDeviceListByAction.action";
			var id= $("#forhiddenfieldchange").val();
			var params = {"actionName" : actionSelected,"gateWay.id" :id };
			$.ajax({

				url: targetUrl,
				data: params,
				dataType: 'xml',
				success: handleCategorySelectChange
			});	
	}
	  
}

var handleCategorySelectChange = function(xml) {
	 var XMLS = new XMLSerializer(); 
	var inp_xmls = XMLS.serializeToString(xml);
	
	$("#devicelist").empty();
	var deviceDefaultOption = "<option value='-1'>Select Device</option>";
	$("#devicelist").append(deviceDefaultOption);
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


</script>

