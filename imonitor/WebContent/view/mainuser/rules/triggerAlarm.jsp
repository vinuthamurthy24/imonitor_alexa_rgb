<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
String applicationName=request.getContextPath();
%>
<script type="text/javascript" src="<%=applicationName %>/resources/js/com.imonitorsolutions.rss.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/com.imonitorsolutions.rules.js"></script>

<script>
	$(document).ready(function() {
		
		
		var initialActionRows = "<tr>";
		initialActionRows += "<th>Gateway</th><th>"+formatMessage("setup.devices.name")+"</th><th>"+formatMessage("setup.devices.action")+"</th><th></th><th>"+formatMessage("general.value")+"</th></tr><tr>";
		initialActionRows += "<td><select class='selectActionGateway'></select></td>"
			+"<td><select class='selectActionDevice'></select></td>"
			+"<td><select class='selectActionName'></select></td>"
			+"<td><select style='display:none' class='FanModevalue'/></td>"
			+"<td><input type='hidden' class='expressionValue' name='actionExpressions' />"
			+"<input type='text' maxlength='3' size='3' style='display:none' class='actionvalueclass'/></td>"
			+"<td><select style='display:none' class='camerapresetvalueclass'/></td>"
			+"<td><a href='#' class='removeCurrentRow'><img src='/imonitor/resources/images/delete2.png'></img></a></td></tr>";
		$("#defineActionTable").html(initialActionRows);
		var actionDeviceOptions = "<option value=''>"+formatMessage("setup.rss.selectdevice")+"</option>";
		var gatewayOptions = "<option value=''>Select a gateway</option>";
		$(".selectActionDevice").html(actionDeviceOptions);
		
		gateWayDetailsMap = {
				<s:iterator value="gateWays" var="gateWay" status="gateWayStatus">
					"<s:property value="#gateWay.id"/>":{
					    "gatewayid":"<s:property value="#gateWay.macId"/>",
					    "gatewayName":"<s:property value="#gateWay.name"/>",
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
			var gatewayName = oneGateWayDetails["gatewayName"];
			// 1. Filling the gateway.
			gatewayOptions += "<option value ='" + gateWayId + "'>" + gatewayName + "</option>";
			
		}
		$(".selectActionGateway").append(gatewayOptions);
		$('.selectActionDevice').change();
		$(".selectActionGateway").live("change",handleGatewayActionSelectChange);
		
		$(".selectActionDevice").die("change");
		$(".selectActionDevice").live("change",handleSecurityActionSelectChange);
		
		$(".selectActionName").die("change");
		$(".selectActionName").live("change",handleGatewayActionNameSelectChange);
		
		$(".removeCurrentRow").die("click");
		$(".removeCurrentRow").live("click",removeCurrentRow);
		
		
		// Updating the boxes with current values.
		var ruleDetails = {
				"actions":{
				<s:iterator value="imvgSecurityActions" var="deviceAction" status="deviceActionStatus">
					"<s:property value="#deviceAction.id"/>":{
						"gatewayId":"<s:property value="#deviceAction.gateway.id"/>",
						"deviceId":"<s:property value="#deviceAction.device.id"/>",
						//"level":"<s:property value="#deviceAction.level"/>",
						"actionId":"<s:property value="#deviceAction.actionType.id"/>"
					}<s:if test="#deviceActionStatus.last != true">,</s:if>
				</s:iterator>
				}
			};
		var cActions = ruleDetails["actions"];
		for(var aId in cActions){
			var cAction = cActions[aId];
			var gatewayId = cAction["gatewayId"];
			var deviceId = cAction["deviceId"];
			var actionId = cAction["actionId"];
	

	var cRow = $("#defineActionTable").find('tr:last');
	
	var gatewaySelect = cRow.find('.selectActionGateway');
	gatewaySelect.val(gatewayId);
	gatewaySelect.change();
	
	var deviceSelect = cRow.find('.selectActionDevice');
	deviceSelect.val(deviceId);
	deviceSelect.change();
	
	var actionSelect = cRow.find('.selectActionName');
	actionSelect.val(actionId);
	actionSelect.change();
			
			
		}
		
		
	});
</script>

<html>
<body>
<s:form id="editModeForm" theme="simple" action="saveSecurityConfigure.action" method="POST" cssClass="ajaxinlinepopupform" >
<s:hidden name="customer"></s:hidden>

<div id="deviceactionsection" class="deviceactionsection">
	<table id="defineActionTable">
	</table>
</div>
<s:submit key="general.save" onclick="javascript:handleSync()"/>
</s:form>
</body>
</html>