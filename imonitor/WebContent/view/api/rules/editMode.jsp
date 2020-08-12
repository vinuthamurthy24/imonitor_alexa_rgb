<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ page language="java" %>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>


<script>
// ****************************************************** sumit start: Alert Device Feature ****************************************************  
	$(document).ready(function() {
		var alertDevices = {
				"devices":{
					<s:iterator value="devices" var="device" status="deviceStat">
						"<s:property value="#device.id"/>":{
							"devicename":"<s:property value="#device.friendlyName"/>",
							"generatedDeviceId":"<s:property value="#device.generatedDeviceId"/>",
							"deviceType":"<s:property value="#device.deviceType.id"/>"
						}<s:if test="#deviceStat.last != true">,</s:if>
					</s:iterator>
				}
				
			};
		var alertDeviceList = alertDevices["devices"];
		var alertDeviceSelect = "<tr><td>"+"<s:text name='setup.rules.mode.select' />"+"<select class='selectAlertDevice'></select></td>";
		$("#alertDeviceTable").html(alertDeviceSelect);

		var alertDeviceOptions = "<option value=''>"+"<s:text name='setup.rules.mode.select' />"+"</option>";

		//1.Fill the alertDevice options with deviceIds.  
		for(var aDevice in alertDeviceList){
			var device = alertDeviceList[aDevice];
			var deviceName = device["devicename"];
			var generatedDevcId = device["generatedDeviceId"];
			var deviceType = device["deviceType"];

			//2.Filter the SelectBox Options to Switches, Dimmer and Siren only. 
			if((deviceType == 2) || (deviceType == 3) || (deviceType == 11)){
				alertDeviceOptions += "<option value='" + aDevice + "'>" + deviceName + "</option>";
			}
		}
		$(".selectAlertDevice").html(alertDeviceOptions);
		$(".selectAlertDevice").change();
		
		var handleAlertDeviceChange = function(ev){
			var selectedDeviceId = $(this).val();
 			//3.Set this deviceId as value to the hidden field 'alertDeviceId'. 
			$("#alertDeviceId").val(selectedDeviceId);
 		};
		
		$(".selectAlertDevice").die("change");
		$(".selectAlertDevice").live("change",handleAlertDeviceChange);
		
		var alertDevc = "<s:property value="alertDeviceId"></s:property>";
		$(".selectAlertDevice").val(alertDevc);
		$(".selectAlertDevice").change();

		//sumit start:
		var dsCount = 0;
		for(var aDevice in alertDeviceList){
			var device = alertDeviceList[aDevice];
			var deviceTypeId = device["deviceType"];
			
			if(deviceTypeId == 6){
				dsCount ++;				
				//	
				//alert(JSON.stringify(device));
			}

		}
		if(dsCount > 0){
			document.getElementById("checkDS").value = "DSexists";
		}else{
			document.getElementById("checkDS").value = "DSnotexists";
		}
		//sumit end

	});
	// *************************************************** sumit end: Alert Device Feature **************************************************  
	
 	function handleSync(){
		document.forms["editModeForm"]["cmdParamId"].value = "1";
	 	//document.getElementById("cmdParamId").value = "1";
 }
</script>


<br>
<div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
		<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:if test="devices.size >0">
<s:form id="editModeForm" theme="simple" action="saveMode.action" method="POST" cssClass="ajaxinlinepopupform" >
										 
<table >

	<thead>
 		<tr bgcolor="#E1E1E1">
			<th width="70px"><s:text name="general.devices" /></th>
 			<th width="350px"><s:text name="general.name" /></th>
		<!--	<th width="50px">HOME</th>	-->
			<th width="50px"><s:text name="general.stay" /></th>
			<th width="50px"><s:text name="general.away" /></th>
		</tr>
	</thead>
	<s:iterator value="devices" var="dev" status="stat" >
	<!-- ******************************************** sumit start: AVOID LISTING VIRTUAL DEVICES here. **************************************** -->
		<s:hidden name="deviceId" value="%{devices[#stat.index].deviceId}" />
		<s:hidden name="deviceTypeId" value="%{devices[#stat.index].deviceType.id}" />
		<s:if test="%{deviceId != 'STAY' && deviceId != 'HOME' && deviceId != 'AWAY' && devices[#stat.index].deviceType.id == 6}">
		
			<tr bgcolor="#F1F1F1">
				<td width="70px"><img class='deviceicon' style="align: center; margin-bottom: 6px; margin-left:13px;" src='<s:property value="%{devices[#stat.index].icon.fileName}"/>' /></td>
				<td style="text-align:left;" width="350px"><s:property value="%{devices[#stat.index].friendlyName}"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<s:hidden name="devices[%{#stat.index}].generatedDeviceId" />
				<s:hidden name="devices[%{#stat.index}].currentMode" />
			<!--	<td width="50px"><s:checkbox style="margin-left:17px;" name="devices[%{#stat.index}].modeHome" value="%{devices[#stat.index].modeHome}"/></td>		-->
				<td width="50px"><s:checkbox style="margin-left:17px;" name="devices[%{#stat.index}].modeStay" value="%{devices[#stat.index].modeStay}"/></td>
				<td width="50px"><s:checkbox style="margin-left:17px;" name="devices[%{#stat.index}].modeAway" value="%{devices[#stat.index].modeAway}"/></td>
			</tr>	
		</s:if>
		<s:else>
		<!-- ***************************************** HANDLE SAVE/SYNC FOR VIRTUAL DEVICES *************************************************** -->
			<s:hidden name="devices[%{#stat.index}].generatedDeviceId" value="%{devices[#stat.index].generatedDeviceId}"/>
			<s:hidden name="devices[%{#stat.index}].commandParam" value="0"/>
		<!--	<s:hidden name="devices[%{#stat.index}].modeHome" value="%{devices[#stat.index].modeHome}"/>	-->
			<s:hidden name="devices[%{#stat.index}].modeStay" value="%{devices[#stat.index].modeStay}"/>
			<s:hidden name="devices[%{#stat.index}].modeAway" value="%{devices[#stat.index].modeAway}"/>
			<s:hidden name="devices[%{#stat.index}].currentMode" value="%{devices[#stat.index].currentMode}"/>
		</s:else>
	<!-- ********************************************************** sumit end ******************************************************************* -->
	</s:iterator>

	</table>	
	<table>
		<tr>
			<s:hidden id="cmdParamId" name="devices[0].commandParam" />
			<s:hidden name="devices[0].gateWay.id" />
			<s:hidden name="devices[0].gateWay.macId" />
			<p style="text-align:center;color:red;">_________________________________________________________________</p>
			<tr>
				<td><s:text name="setup.rules.mode.delay.h" /></td><td><s:textfield id="delayHome" maxlength="3" size="3" cssClass="required number" name="devices[0].gateWay.delayHome" label=""/></td>
			</tr>
			<tr>
				<td><s:text name="setup.rules.mode.delay.s" /></td><td><s:textfield id="delayStay" maxlength="3" size="3" cssClass="required number" name="devices[0].gateWay.delayStay" label=""/></td>
			</tr>
			<tr>
				<td><s:text name="setup.rules.mode.delay.a" /></td><td><s:textfield id="delayAway" maxlength="3" size="3" cssClass="required number" name="devices[0].gateWay.delayAway" label=""/></td>
			</tr>
		</tr>
</table>

<!-- sumit start: Alert Device feature -->
<p style="text-align:center;color:red;">_____________________________________________________________</p>	
<div id="alertDeviceSection" class="alertDeviceSection">
	<table id="alertDeviceTable"></table>
</div>
<s:hidden name="alertDeviceId" id="alertDeviceId" value=""></s:hidden>
<!-- sumit end: Alert Device Feature -->

	<s:submit key="general.save" onclick="javascript:handleSync()"/>
<%-- 	<s:submit value="RESET" /> --%>
</s:form>
</s:if>
<s:else>
<p><s:text name="setup.rules.mode.error" /></p>
</s:else>