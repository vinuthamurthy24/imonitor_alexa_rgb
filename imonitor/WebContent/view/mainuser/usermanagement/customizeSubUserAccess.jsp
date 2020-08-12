<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>

<%
String applicationName = request.getContextPath();
%>
  
  <style>
  #accordion-resizer {

    width: 910px;
	height: 450px;
  }

  .u_access_deviceIcon {
	width: 45px;
	height: 45px;
	margin-top: 5px;
  }
  .u_access_deviceName {
	float: initial;
	width: 225px;
	margin-left: 10px;
	margin-top: 18px;	
  }
  .u_access_deviceType {
	float: inherit;
	width: 250px;
	margin-left: 10px;
	margin-top: 18px;
  }
  .u_access_deviceLocation {
	float: inherit;
	width: 200px;
	margin-left: 10px;
	margin-top: 18px;
  }
  
  .deviceAccessForCustomUser{
	float: inherit;
	width: 70px;
	margin-left: 10px;
	margin-top: 18px;
  }
  
  .u_access_deviceIcon{
	
  }
  .deviceSection{
	width: 1000px;
  }
	
  .scenarioHeader{
	float: left;
	font-weight: bold;
	margin-left: 13px;
	margin-top: 5px;
	width: 400px;
  }
  
  .scenarioSection {
	float: left;
	margin-left: 13px;
	margin-top: 5px;
	width: 1000px;
  }
  
  .u_access_scenarioName {
	width : 350px;
  }
  
  .u_access_scenarioDesc {
	width : 350px;
  }
  
  .scenarioAccessForCustomUser{
	float: right;
	width: 70px;
  }
  
  .widget {
    margin: 3px 5px 0 3px;
    padding: 2px;
    -moz-border-radius: 4px;
    -webkit-border-radius: 4px;
	border: 1px solid #DDDDDD;
	
}
	.widget .widget-head {
    color: #000;
    overflow: hidden;

    width: 100%;
    height: 30px;
    line-height: 30px;
}
.widget .widget-head h3 {
    padding: 0 5px;
    float: left;
}
.widget .widget-content {
    padding: 0 5px;
    color: #6C6B6B;
    -moz-border-radius-bottomleft: 2px;
    -moz-border-radius-bottomright: 2px;
    -webkit-border-bottom-left-radius: 2px;
    -webkit-border-bottom-right-radius: 2px;
    line-height: 1.2em;
    overflow: hidden;
    min-height: 40px;
    text-align: left;
}

	.noDeviceMessage {
		text-shadow: 5px 5px 5px #D1D1E9;
	}
	.noScenarioMessage{
		text-shadow: 5px 5px 5px #D1D1E9;
	}
	
  </style>
  <script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.js"></script>
  <script>
  	$(document).ready(function() {
	
  	//1.Prepare Device Details for View. 
  		var deviceDetailsMap = {
  				<s:iterator value="deviceList" var="device" status="devStatus">
  					"<s:property value="#device.id" />":{
						"deviceId":"<s:property value="#device.id" />",
  						"deviceIcon":"<s:property value="#device.icon.fileName" />",
  						"deviceName":"<s:property value="#device.friendlyName" />",
  						"deviceType":"<s:property value="#device.deviceType.name"/>",
  						"deviceLocation":"<s:property value="#device.location.name" />",
  						"subuseraccess":"<s:property value="#device.subUserAccess"/>",
  					 }<s:if test="#devStatus.last != true">,</s:if>
  				</s:iterator>
  		}
		
		
  		for(var deviceId in deviceDetailsMap){
			var device = deviceDetailsMap[deviceId];
			var deviceId = device["deviceId"];
			var deviceIcon = device["deviceIcon"];
			var deviceName = device["deviceName"];
			var deviceType = device["deviceType"];
			var deviceLocation = device["deviceLocation"];
			var subuseraccess = device["subuseraccess"];
			
			if((device['deviceType'] != "MODE_HOME") 
				&& (device['deviceType'] != "MODE_STAY") 
				&& (device['deviceType'] != "MODE_AWAY") 
				&& (device['deviceType'] != "Z_WAVE_ENERGY_MONITOR")){
				
				
				//alert("deviceName : " + deviceName + ", subuseraccess : " + subuseraccess);

				var deviceicon=$("<img />");
				deviceicon.addClass("u_access_deviceIcon").attr('src', deviceIcon);
				
				var deiviceicontd = $("<td />");
				deiviceicontd.append(deviceicon);
				
				var devicename=$("<td />");
				devicename.addClass("u_access_deviceName").append(deviceName);
				
				var devicetype=$("<td />");
				devicetype.addClass("u_access_deviceType").append(deviceType);
				
				var devicelocation=$("<td />");
				devicelocation.addClass("u_access_deviceLocation").append(deviceLocation);
				
				
				var contentDiv = $("<div></div>").addClass('widget-content');
				var deviceSection = $("<tr class='deviceSection'></tr>");
			
				var deviceAccess = $("<input>", {type:"checkbox"});
				deviceAccess.addClass("deviceAccessForCustomUser").attr("id","deviceAccessForCustomUser").attr("value",deviceId);
				
				var deviceCheck = $("<td />");
				deviceCheck.append(deviceAccess);
				
				deviceSection.append(deviceicon).append(devicename).append(devicetype).append(devicelocation).append(deviceAccess);
				
				contentDiv.append(deviceSection);
				var deviceLi = $("<li></li>").addClass('widget').addClass('color-white').append(contentDiv);
				$('#deviceForCustomization').append(deviceLi);
			}
			
		}
	
  		//2.Prepare Scenario Details for View.
  		var scenarioDetailsMap = {
  				<s:iterator value="scenarioList" var="scenario" status="scenarioStatus">
  					"<s:property value="#scenario.id" />":{
						"scenarioId":"<s:property value="#scenario.id" />",
  						"scenarioName":"<s:property value="#scenario.name" />",
  						"description":"<s:property value="#scenario.description" />",
  						"subuseraccess":"<s:property value="#scenario.subUserAccess" />"
  					 }<s:if test="#scenarioStatus.last != true">,</s:if>
  				</s:iterator>
  		}
		
  		for(var scenarioId in scenarioDetailsMap){
			var scenario = scenarioDetailsMap[scenarioId];
			var scenarioId = scenario["scenarioId"];
			var scenarioName = scenario["scenarioName"];
			var description = scenario["description"];
			var subuseraccess = scenario["subuseraccess"];
			
			//alert("scenarioName : " + scenarioName + ", subuseraccess : " + subuseraccess);
			
			var contentDiv = $("<div></div>").addClass('widget-content');
			var scenarioSection = $("<tr class='scenarioSection'></tr>");
			
			var scenarioname=$("<td />");
			scenarioname.addClass("u_access_scenarioName").append(scenarioName);
			
			var scenariodesc=$("<td />");
			scenariodesc.addClass("u_access_scenarioDesc").append(description);
			
			var scenarioAccess = $("<input>", {type:"checkbox"});
			scenarioAccess.addClass("scenarioAccessForCustomUser").attr("id","scenarioAccessForCustomUser").attr("value",scenarioId);
			
			var accessCheck = $("<td />");
			accessCheck.append(scenarioAccess);
			
			scenarioSection.append(scenarioname).append(scenariodesc).append(accessCheck);
			
			contentDiv.append(scenarioSection);
			var scenarioLi = $("<li></li>").addClass('widget').addClass('color-white').append(contentDiv);
			
			$('#scenarioForCustomization').append(scenarioLi);
		} 
		
		//3.Prepare View for Devices accessible to sub user.
		var suDeviceDetailsMap = {
  				<s:iterator value="subuserDeviceList" var="suDevice" status="suDeviceStatus">
  					"<s:property value="#suDevice.id" />":{
  						"subuserID":"<s:property value="#suDevice.user.id" />",
  						"subuserDeviceID":"<s:property value="#suDevice.device.id" />",
  					 }<s:if test="#suDeviceStatus.last != true">,</s:if>
  				</s:iterator>
  		}
		
		for(var deviceId in suDeviceDetailsMap){
			var suDevice = suDeviceDetailsMap[deviceId];
			var subuserID = suDevice["subuserID"];
			var subuserDeviceID = suDevice["subuserDeviceID"];
			
			//alert("subuserID : " + subuserID + ", subuserDeviceID : " + subuserDeviceID);
			//var scenarioCheckBox = $("#scenarioAccessForCustomUser");
			
			//There are Devices Configured for this sub user.
			if(deviceId != null){
				for(var deviceId in deviceDetailsMap){
					var device = deviceDetailsMap[deviceId];
					var deviceId = device["deviceId"];
					//alert("subuserDeviceID : "+subuserDeviceID+", deviceId : "+deviceId);
					if(subuserDeviceID == deviceId){
						
						$(".deviceSection .deviceAccessForCustomUser").each(function (){
							var oneDeviceCheck = $(this);//.attr("value");
							if(oneDeviceCheck.val() == deviceId){
								oneDeviceCheck.attr("checked", true);
							}
						});
					}else{
						
						
					}	
				}
			}
		}
		
		
		//4.Prepare View for Scenarios accessible to sub user.
		var suScenarioDetailsMap = {
  				<s:iterator value="subuserScenarioList" var="suScenario" status="suScenarioStatus">
  					"<s:property value="#suScenario.id" />":{
  						"subuserID":"<s:property value="#suScenario.user.id" />",
  						"subuserScenarioID":"<s:property value="#suScenario.scenario.id" />",
  					 }<s:if test="#suScenarioStatus.last != true">,</s:if>
  				</s:iterator>
  		}
		
  		for(var scenarioId in suScenarioDetailsMap){
			var suScenario = suScenarioDetailsMap[scenarioId];
			var subuserID = suScenario["subuserID"];
			var subuserScenarioID = suScenario["subuserScenarioID"];
			
		
			
			//alert("subuserID : " + subuserID + ", subuserScenarioID : " + subuserScenarioID);
			var scenarioCheckBox = $("#scenarioAccessForCustomUser");
			
			//There are Scenarios Configured for this sub user.
			if(scenarioId != null){
				for(var scenarioId in scenarioDetailsMap){
					var scenario = scenarioDetailsMap[scenarioId];
					var scenarioId = scenario["scenarioId"];
					//alert("subuserScenarioID : "+subuserScenarioID+", scenarioId : "+scenarioId);
					if(subuserScenarioID == scenarioId){
						
						$(".scenarioSection .scenarioAccessForCustomUser").each(function (){
							var oneScenarioCheck = $(this);//.attr("value");
							if(oneScenarioCheck.val() == scenarioId){
								oneScenarioCheck.attr("checked", true);
							}
						});
						
						//var scenarioCheck = $("#scenarioAccessForCustomUser");
						//alert(scenarioCheck.val());
						//scenarioCheck.attr("checked",true);
						/*$('#scenarioAccessForCustomUser input[type=checkbox]').each(function () {
							//var sThisVal = $(this).val();//(this.checked ? $(this).val() : "");
							alert("sThisVal = "+sThisVal);
						});*/
					}else{
						
					}
				
				
				}
			}
			
		
		} 
  		
  		
		//5. Generate the Device Header Section
		
		
		//6. Generate the Scenario Header Section
		/*var scenarioHeader = $("#scenarioListHeader");
		var scenarioHeaderContent = $("<tr></tr>");
		var scenarioName = "<td> Scenario Name</td>";
		var scenarioDescription = "<td>Description</td>";
		var scenarioAllowed = "<td>Allowed</td>";
	
		scenarioHeaderContent.addClass("scenarioHeader").append(scenarioName).append(scenarioDescription).append(scenarioAllowed);
		scenarioHeader.append(scenarioHeaderContent);*/
  	
	
		var devCount =$("#deviceCount").val();
		if(devCount == 0){
			var NoDeviceDiv = $("#noDeviceSection");
			NoDeviceDiv.append("<span class='noDeviceMessage'> No device added to this account. Please add some devices first. </span>");
			
		}
		var sceCount =$("#scenarioCount").val();
		if(sceCount == 0){
			var NoScenarioDiv = $("#noScenarioSection"); 
			NoScenarioDiv.append("<span class='noScenarioMessage'> No scenario created for this account. Please create some scenarios first. </span>");
		}
		
  	});

  function DestoryDialog(){
	     	$("#editmodal").dialog('destroy'); 
	     }
  
  $(function() {
    $( "#accordion" ).accordion({
		fillSpace : true
    });
  });
  $(function() {
    $( "#accordion-resizer" ).resizable({
      minHeight: 450,
      minWidth: 910,
	  maxHeight: 450,
	  maxWidth: 910,
      resize: function() {
        $( "#accordion" ).accordion( "refresh" );
      }
    });
  });
  
  function handleCheckedDevicesAndScenarios(){

	//1. Save the checked scenarios to the hidden field.
	var deviceValues = $('input:checkbox:checked.deviceAccessForCustomUser').map(function () {
		return this.value;
	}).get();
	$("#devicesChecked").val(deviceValues);
	
	
	//2. Save the checked scenarios to the hidden field.
	var scenarioValues = $('input:checkbox:checked.scenarioAccessForCustomUser').map(function () {
		return this.value;
	}).get();
	$("#scenariosChecked").val(scenarioValues);
	
	//alert("devicesChecked : "+$("#devicesChecked").val()+" \n scenariosChecked : "+$("#scenariosChecked").val());
	return false;
	
  }
  </script>


<s:form action="customizeSubUserAccess.action" method="post" id="userAccessCustomizationForm" cssClass="ajaxinlinepopupform">
<s:hidden id="customUser" name="user.id"></s:hidden>
<s:hidden id="devicesChecked" name="devicesChecked"></s:hidden>
<s:hidden id="scenariosChecked" name="scenariosChecked"></s:hidden>
<s:hidden id="deviceCount" name="deviceCount"></s:hidden>
<s:hidden id="scenarioCount" name="scenarioCount"></s:hidden>
<div id="accordion-resizer" class="ui-widget-content">
  <div id="accordion">
    <h3 class="titleHeader">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Customize Devices</h3>
    	<div>
			<div id="deviceListHeader"></div>
			<div id="deviceForCustomization"></div>
			<div id="noDeviceSection"></div>
		</div>
    <h3 class="titleHeader">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Customize Scenarios</h3>
    	<div>
			<div id="scenarioListHeader"></div>
			<div id="scenarioForCustomization"></div>
			<div id="noScenarioSection"></div>
		</div>
  </div>
</div>

<tr style="margin-top: 70px; float: right;">
	<td><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel"/></td>
	<td><s:submit theme="simple" key="general.save" onclick="javascript:handleCheckedDevicesAndScenarios()"></s:submit></td>
</tr>

</s:form>
