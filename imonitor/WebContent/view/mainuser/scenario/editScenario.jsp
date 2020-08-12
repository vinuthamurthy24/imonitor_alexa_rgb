<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
String applicationName = request.getContextPath();
%>
<script type="text/javascript" src="<%=applicationName %>/resources/js/com.imonitorsolutions.rss.js"></script>


<script>
	$(document).ready(function() {
		rss = "scenarioedit";
		// handling the gateway change...
		//$("#gatewayselect").hide();

		$(".selectActionDevice").die("change");
		$(".selectActionDevice").live("change",handleActionSelectChange);
		
		$(".selectActionName").die("change");
		$(".selectActionName").live("change",handleActionNameSelectChange);

		$(".removeCurrentRow").die("click");
		$(".removeCurrentRow").live("click",removeCurrentRow);

		$(".actionvalueclass").die("keyup");
		$(".actionvalueclass").live("keyup",handleActionValueChange);
		
		$(".FanModevalue").die("change");
		$(".FanModevalue").live("change",handleFANModeChange);

		gateWayDetailsMap = {
				<s:iterator value="gateWays" var="gateWay" status="gateWayStatus">
					"<s:property value="#gateWay.id"/>":{
					    "gatewayid":"<s:property value="#gateWay.macId"/>",
					    "devices":{<s:iterator value="#gateWay.devices" var="device" status="deviceStatus">
					    	"<s:property value="#device.id"/>":{"devicename":"<s:property value="#device.friendlyName"/>",
						"modelNumber":"<s:property value="#device.modelNumber"/>",
				    	"makets":"<s:property value="#device.make.id"/>",
						"deviceType":"<s:property value="#device.deviceType.name"/>",
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
			
			// 1. Filling the gateway.
			$("#gatewayselect").append("<option value='" + gateWayId + "'>" + macId + "</option>");
		}
		// 2. Add the change listener to gateway select.
		$("#gatewayselect").bind("change",handleGatewaySelectChange);
		$("#gatewayselect").change();
		// Updating the boxes with current values.
		var scenarioDetails = {
			"actions":{
			<s:iterator value="scenario.scenarioActions" var="scenarioAction" status="scenarioActionStatus">
				"<s:property value="#scenarioAction.id"/>":{
					"deviceId":"<s:property value="#scenarioAction.device.id"/>",
					"actionId":"<s:property value="#scenarioAction.actionType.id"/>",
					"level":"<s:property value="#scenarioAction.level"/>"
				}<s:if test="#scenarioActionStatus.last != true">,</s:if>
			</s:iterator>
			}
		};
		var cActions = scenarioDetails["actions"];
		for(var aId in cActions){
			var cAction = cActions[aId];
			var alDeviceId = cAction["deviceId"];
			var alActionId = cAction["actionId"];
			var splited = cAction["level"].split(":");
			var level = splited[0];
			//var level = cAction["level"];
			var cRow = $("#defineActionTable").find('tr:last');
			var alDeviceSelect = cRow.find('.selectActionDevice');
			alDeviceSelect.val(alDeviceId);
			alDeviceSelect.change();
			var alActionSelect = cRow.find('.selectActionName');
			alActionSelect.val(alActionId);
			alActionSelect.change();
			
			var alActionValueText = cRow.find('.actionvalueclass');
			alActionValueText.val(level);
			alActionValueText.keyup();
			
			if(splited.length == 2)
			{
				//alert(splited[1]);
				var AcModeSelect = cRow.find('.FanModevalue');
				AcModeSelect.val(splited[1]);
				AcModeSelect.change();
			}
			else
			{
			var AcModeSelect = cRow.find('.FanModevalue');
				AcModeSelect.val(splited[0]);
				AcModeSelect.change();
			}
			
		}
	});
</script>
<br/>
<div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form id="scheduleForm" action="#" method="POST" cssClass="ajaxinlinepopupform">
 <s:hidden name="scenario.iconFile" id="currentIcon"></s:hidden>
 <table>
 	<tr>
 		<!-- <td>Select Gateway:</td> -->
 		<td>
			<select id="gatewayselect" name="scenario.gateWay.id"></select>
 		</td>
 	</tr>
 	<tr>
 		<td><s:text name="setup.scenarios.name" /></td>
 		<td>
			<s:hidden name="scenario.id"></s:hidden>
			<s:textfield name="scenario.name" maxlength='35' theme="simple" cssClass="required utf8alphanumeric editdisplayStar"></s:textfield>
 		</td>
 	</tr>
 	<tr>
 		<td>
 			<s:text name="setup.scenarios.description" />
 		</td>
 		<td>
			<s:textarea  name="scenario.description" maxlength="120"  cols="18" rows="1" theme="simple" cssClass="required utf8alphanumeric" ></s:textarea>
 		</td>
 	</tr>
 </table>
<div id="popwarning" class="popwarning"></div>

<span class="actionheader"><s:text name="setup.scenarios.what" /><input type="hidden" id="defineActionsHidden" class="required displayStarHeader"/></span>
<div id="deviceactionsection" class="deviceactionsection">
	<table id="defineActionTable">
	</table>
</div>
<s:div>
<table>
		<tr><td></td></tr>
                <tr>
                    <td valign="top"><s:text name="setup.scenarios.curricon" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td ><img src="<s:property value='scenario.iconFile' />" class="locationicon" /></td>
                        </tr>

                        <tr>
                        <td valign="top"><s:text name="setup.scenarios.icon" /></td>
                        <td>
                                <table>
                                	<tr>
                                    	<s:iterator value="listIcons"  status="abc" >
        									<s:if test="#abc.count == 7 || #abc.count == 13 ||  #abc.count == 19 ||  #abc.count == 25">
                					</tr>
                					<tr>
        									</s:if>
        									<td><input type='radio'  onClick="javascript: populateIcon('<s:property value="fileName" />')"  name='selectIcon' value='<s:property value="id" />'/>
        									<img class='listlocationicon' src='<s:property value="fileName" />'/></td>
        									<td>&nbsp;</td>
                                        </s:iterator>
                                    </tr>
                                        </table>
                                </td>
                        </tr>

                </s:div>
    	<tr><td></td></tr>
</table>
<input type="submit" value='<s:text name="general.save" />'  onClick="if(validateActions() == true){$('#scheduleForm').attr('action','updateScenario.action');}" id="saveSchedule"/>
</s:form>
<script>
        function populateIcon(iconId)
        {
           document.getElementById("currentIcon").value = iconId;
        }	

	</script>			
	
