<%-- Copyright Â© 2014 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%><html>
<%
String applicationName = request.getContextPath();
%>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.js"></script>

<style>

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

			
			.cumulativeTable th {padding:0px 6px 0px; text-align:center; }
	
	

	.cumulativeTable td {padding:0px 5px 1px; text-align:center; }
	
	.cumulativeTable th {padding-top:5px; background:rgba(166, 172, 161, 0.95);}
	
	.cumulativeTable td {border :1px solid #57D4DF; border-right:1px solid #57D4DF;}
	
	.cumulativeTable tr.odd-row td {background:#f6f6f6;}
	
	.cumulativeTable td.first, th.first {text-align:left}
	
	.cumulativeTable td.last {border-right:none;}
			
			/*
			Background gradients are completely unnecessary but a neat effect.
			*/
			
			.cumulativeTable td {
				
			}
			
			.cumulativeTable tr.odd-row td {
				
			}
			
			.cumulativeTable th {
				rgba(166, 172, 161, 0.95);
			}
			
			
			.cumulativeTable tr:first-child th.first {
				-moz-border-radius-topleft:5px;
				-webkit-border-top-left-radius:5px; /* Saf3-4 */
			}
			
			.cumulativeTable tr:first-child th.last {
				-moz-border-radius-topright:5px;
				-webkit-border-top-right-radius:5px; /* Saf3-4 */
			}
			
			.cumulativeTable tr:last-child td.first {
				-moz-border-radius-bottomleft:5px;
				-webkit-border-bottom-left-radius:5px; /* Saf3-4 */
			}
			
			.cumulativeTable tr:last-child td.last {
				-moz-border-radius-bottomright:5px;
				-webkit-border-bottom-right-radius:5px; /* Saf3-4 */
			}
  </style>
  
<script>
function DestoryDialog()
{
           $("#editmodal").dialog('destroy'); 
}	

function getDeviceList(){
	var macId = $("#macId").val();
	var params = {"macID" : macId};
	$('#deviceForCustomization').html("<img src='../resources/images/loading.gif'/>");
	$.ajax({
		url: "togetDeviceListWithDeadNodes.action",
		data: params,
		dataType: 'xml',
		success:  handleDeviceList
		
	});	 
	
	
	
}
function getDiagnosticInfo(){
	
	var macId = $("#macId").val();
	var params = {"macID" : macId};
	$('#deviceForCustomization').html("<img src='../resources/images/loading.gif'/>");
	$.ajax({
		url: "toGetDiagnosticInfo.action",
		data: params,
		dataType: 'xml',
		success:  function(data){
			
			
		}
		
	});	 
	
}

function getGatewayVersion(){
	var version = $("#zVersion").val();
	var homeId = $("#homeId").val();
	var nodeId = $("#nodeId").val();
	var chipType = $("#chipType").val();
	var numbOfNodes = $("#numbOfNodes").val();
	var chipVersion = $("#chipVersion").val();
	var chipFlag = $("#chipFlag").val();
	var zWaveSeries = $("#zWaveSeries").val();
	if(version != "VERSION_NOT_FILLED" || varsion != "failure"){
		
		$("#displayInfo").html("<table class='cumulativeTable'><tr><th>Gateway</th><th>ZWave Version</th><th>Home Id</th><th>Node Id</th><th>Chip Type</th>"+
				"<th>No. of Nodes</th><th>Chip Version</th><th>Chip Flag</th><th>API Version</th>"+
				"<tr><td>Master</td><td>"+version+"</td><td>"+homeId+"</td><td>"+nodeId+"</td><td>"+chipType+"</td><td>"+numbOfNodes+"</td><td>"+
				chipVersion+"</td><td>"+chipFlag+"</td><td>"+zWaveSeries+"</td></tr>");
		$("#displayInfo").dialog('open');
		$("#displayInfo").dialog({
			
			width: 750,
			modal : true,
			title: "Gateway information",
			 buttons: {
				 
				 Ok: function() {
					 $( this ).dialog('destroy');
					 $(this).dialog('close');
				 }
			 }
			
		}); 
		/* showResultAlert('<s:text name="msg.imonitor.userconfigure.0000" />'+ version+ "<BR>" +
				        '<s:text name="msg.imonitor.userconfigure.0002" />'+ homeId+ "<BR>" +
				        '<s:text name="msg.imonitor.userconfigure.0003" />'+ nodeId+ "<BR>" +
				        '<s:text name="msg.imonitor.userconfigure.0006" />'+ chipType+ "<BR>" +
				        '<s:text name="msg.imonitor.userconfigure.0004" />'+ numbOfNodes+ "<BR>" +
				        '<s:text name="msg.imonitor.userconfigure.0005" />'+ chipVersion+ "<BR>" +
				        '<s:text name="msg.imonitor.userconfigure.0007" />'+ chipFlag + "<BR>" +
				        '<s:text name="msg.imonitor.userconfigure.0008" />'+ zWaveSeries);  */
		
	}else{
		
		showResultAlert('<s:text name="msg.imonitor.userconfigure.0001" />');
	}
	
	
}


var handleDeviceList = function(xml) {

	$('#deviceForCustomization').html("");
	var devicecolumn=$("<th/>");
	var deviceNameColumn=$("<th/>");
	var deviceAction=$("<th/>");
	
//	devicecolumn.addClass("u_access_deviceName").append("Device ID");
	//deviceNameColumn.addClass("u_access_deviceName").append("Device Name");
	//deviceAction.addClass("u_access_deviceName").append("Action");
	
//	var content = $("<div></div>").addClass('widget-content');
	//var headerSection = $("<tr class='deviceSection'></tr>");
	//headerSection.append(devicecolumn).append(deviceNameColumn).append(deviceAction);
	//content.append(headerSection);
	
//	var columnLi = $("<li></li>").addClass('widget').addClass('color-white').append(content);
	//$('#deviceListHeader').append(columnLi);
	$(xml).find("Device").each(function() {
		var nodeId = $(this).find("nodeId").text();
		var macId = $(this).find("macId").text();
		deviceId = macId+"-"+nodeId;
		var deviceIcon = $(this).find("deviceIcon").text();
		var deviceType =  $(this).find("name").text();
		var friendlyName =  $(this).find("friendlyName").text();
			
		
		if((deviceType != "MODE_HOME") 
				&& (deviceType != "MODE_STAY") 
				&& (deviceType != "MODE_AWAY") 
				&& (deviceType != "Z_WAVE_ENERGY_MONITOR") && (deviceType != "IP_CAMERA")){
		
			var deviceicon=$("<img/>");
			deviceicon.addClass("u_access_deviceIcon").attr('src', deviceIcon);
			
			var generatedDeviceId=$("<td/>");
			generatedDeviceId.addClass("u_access_deviceType").append(deviceId);
			
			var devicename=$("<td/>");
			devicename.addClass("u_access_deviceName").append(friendlyName);
			
			var deleteNode = $("<input>", {type:"radio"});
			deleteNode.addClass("devicCheckForDelete").attr("id","devicCheckForDelete").attr("value",deviceId).attr("name","myRadio");
			
			var contentDiv = $("<div></div>").addClass('widget-content');
			var deviceSection = $("<tr class='deviceSection'></tr>");
			
			deviceSection.append(deviceicon).append(generatedDeviceId).append(devicename).append(deleteNode);
			
			contentDiv.append(deviceSection);
			var deviceLi = $("<li></li>").addClass('widget').addClass('color-white').append(contentDiv);
			$('#deviceForCustomization').append(deviceLi);
			
		}
		
	
	});
	
	
	
	
	
};

$("#validateDeadNodes").live('click', function(){
	var selectedDevice = $("input[name=myRadio]:checked").val();
	
	$("#deviceToDelete").val(selectedDevice);
	
	
});

$(".devicCheckForDelete").live('click', function(){
	$("#validateDeadNodes").show();
	$("#CancelButton").show();
	    /*    var deviceCheck = $(this).val();
			var targeturl = "checkDeviceForRule.action";
			var message = "";
			
			var isFailure = true;
			var params = {"deviceToDelete" : deviceCheck};
			//1.This call should get a message back.
			$.ajax({
				url: targeturl,
				data: params,
				async: false,
				dataType: 'xml',
				success: function(data){
					var a= $(data).html().trim();
					alert(a);
					var aa = a.split(":");
					if(aa[0] =='Failure')
					{
						isFailure = true;
						message = aa[1];
					}
					else
					{
						isFailure = false;
						message = a;
				}
			}
			});
			
			
		
			//4.Proceed with deleting the device
			$("#confirm").dialog("destroy");
			//$("#confirm").html('Are you sure you want to remove this device ?<p style="color: red;"> This will result in deleting its entry in the corresponding rules!</p>');
			$("#confirm").html(message);
			
			return false;*/
	
	
	
});
	
	

$(document).ready(function() {

	$("#validateDeadNodes").hide();
	$("#CancelButton").hide();
	var status = $("#status").val();

//	$("#localIP").hide();
    if(status == "Offline"){
    	
    	$("#accordion").html("Your Master Controller is Offline!!!");
    	$('#accordion').css({ "color": 'red'});
    	$('#accordion').css({ "font-size": '25px'});
    	$("#getDevice").hide();
    	$("#gatewayVersion").hide();
    }
    


});



</script>

<div style="color: blue;display:none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<div class="pageTitle">
<s:form action="deleteDeadNode.action" method="POST"  cssClass="ajaxinlinepopupform" style="text-align: left;">
<s:hidden name="gateway.macId" id='macId'></s:hidden>
<s:hidden id="customUser" name="user.id"></s:hidden>
<s:hidden id="deviceCount" name="deviceCount"></s:hidden>
<s:hidden id="deviceToDelete" name="deviceToDelete"></s:hidden>
<s:hidden id="status" name="gateway.status.name"></s:hidden>
<s:hidden id="zVersion" name="zWaveVersion"></s:hidden>
<s:hidden id="homeId" name="homeId"></s:hidden>
<s:hidden id="nodeId" name="nodeId"></s:hidden>
<s:hidden id="chipType" name="chipType"></s:hidden>
<s:hidden id="numbOfNodes" name="numbOfNodes"></s:hidden>
<s:hidden id="chipVersion" name="chipVersion"></s:hidden>
<s:hidden id="chipFlag" name="chipFlag"></s:hidden>
<s:hidden id="zWaveSeries" name="zWaveSeries"></s:hidden>
<!--<s:textfield   name="gateway.localIp" label="Local IP" id="localIP" readonly="true"></s:textfield>
<s:textfield   name="gateway.gateWayVersion" label="Firmware Version" maxlength="20" readonly="true"></s:textfield>-->

<table>
<tr>
<td>
<button style="width: 130px;"  type='button' id="getDevice" onClick='getDeviceList()' class='bt bbtn backupAlerts' title='click to get device list'><s:text name="Get Device List" /></button>
</td>
<td>
<button style="width: 130px;"  type='button' class='bt bbtn backupAlerts' onClick='getGatewayVersion()' id="gatewayVersion" title='click to get gateway details'><s:text name="Gateway Info" /></button>
</td>
<td>
 <button style="width: 130px;"  type='button' id="getDiagnosticInfo" href="toGetDiagnosticInfo.action"  class='bt bbtn editlink' title='click to get diagnostic info'><s:text name="Diagnostic Info" /></button> 
</td>
</tr>
</table>

  <div id="accordion">
 
    	<div>
			<div id="deviceListHeader">
			<table>
			<tr>
			<th class='u_access_deviceName'>Device ID</th>
			<th class='u_access_deviceName'>Device Name</th>
			<th class='u_access_deviceName'>Action</th>
			</tr>
			</table>
			</div>
			<div id="deviceForCustomization"></div>
			<div id="noDeviceSection"></div>
		</div>
   
  </div>

<table>
<tr style="margin-top: 70px; float: right;">
	<td><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel"/></td>
	<td><s:submit theme="simple" key="alerts.delete" id="validateDeadNodes"></s:submit></td>
</tr>
</table>
<div id="displayInfo"></div>
</s:form>
</div>