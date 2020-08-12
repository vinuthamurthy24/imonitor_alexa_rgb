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
		rss = "scenarioadd";
		// handling the gateway change...
		//$("#gatewayselect").hide();
		
		//3gp start
		var handleSuccess = function(xml){
			//arrangeGateWaySectionForSetup(xml);
			
			userDetails.setGateWayCount($(xml).find("gateway").size());
			 gateWayCount = $(xml).find("gateway").size();
			 if (gateWayCount > 1)
			 {
				 $("#gatewayselect").show();
			} 
			 else 
			{
				 $("#gatewayselect").hide();
			}
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
		//3gp end
		
		toggleSteps(1);

		$(".selectActionDevice").die("change");
		$(".selectActionDevice").live("change",handleActionSelectChange);
		
		$(".selectActionName").die("change");
		$(".selectActionName").live("change",handleActionNameSelectChange);

		$(".removeCurrentRow").die("click");
		$(".removeCurrentRow").live("click",removeCurrentRow);

		$(".actionvalueclass").die("keyup");
		$(".actionvalueclass").live("keyup",handleActionValueChange); 
		
		$(".colorvalueclass").die("keyup");
		$(".colorvalueclass").live("keyup",handleActionValueChange);
		
		$(".FanModevalue").die("click");
		$(".FanModevalue").live("click",handleFANModeChange);

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
			$("#gatewayselect").append("<option value='" + gateWayId + "'>" + gatewayName + "</option>");
		}
		// 2. Add the change listener to gateway select.
		$("#gatewayselect").bind("change",handleGatewaySelectChange);
		$("#gatewayselect").change();

	});
	

/*
	function toggleSteps(nextStep)
	{

		for(i=1;i<=4;i++)
		{
			$("#step"+i).hide();
			$("#step"+i).removeClass('currentstep');
		}
		$("#step"+nextStep).show();
		$("#step"+nextStep).addClass('currentstep');
		Xpeditions.validateElement($("#step"+nextStep));
		$("#editmodal").dialog('option', 'title', 'Add Scenario (Step '+nextStep+' of 3)');
	}*/
</script>
<br/>
<div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form id="scenarioForm" action="saveScenario.action" method="POST" cssClass="ajaxinlinepopupform">
<table></table>
<span id="step1" >
<span class="actionheader"><s:text name="setup.scenarios.header" /></br></span>
 <s:hidden name="scenario.iconFile" id="iconFile"></s:hidden><br>
 <table style='width:100%;'>
 <tr></tr><tr></tr>
 	<tr>
 		<!-- <td>Select Gateway:</td> -->
 		<td>
			<select id="gatewayselect" name="scenario.gateWay.id"></select>
 		</td>
 	</tr>
 	<tr>
 		<td width="20%"><s:text name="setup.scenarios.name" /></td>
 		<td width="80%">
			<s:textfield name="scenario.name" maxlength='35' theme="simple" cssClass="required utf8alphanumeric"></s:textfield>
 		</td>
 	</tr>
 	<tr>
 		<td width="20%"><s:text name="setup.scenarios.description" /></td>
 		<td width="80%">
			<s:textarea  name="scenario.description" maxlength="120" cols="18" rows="1" theme="simple" cssClass="required utf8alphanumeric"></s:textarea>
 		</td>
 	</tr>
 </table>
<div id="popwarning" class="popwarning"></div>
<table width="100%" ><tr ><td align="left"></td><td  align="right"><input type="button" value='<s:text name="general.next" />' onClick="toggleSteps('2')" class="navigate" /></td></tr></table>
</span>



<span id="step2">
<span class="actionheader"><s:text name="setup.scenarios.what" />&nbsp&nbsp<input type="hidden" id="defineActionsHidden" class="required"/></span><br><br>
<div id="deviceactionsection" class="deviceactionsection">
	<table id="defineActionTable">
	</table>
</div>
<br><br>
<table width="100%" ><tr ><td align="left"><input type="button" value='<s:text name="general.prev" />' onClick="toggleSteps('1')" /></td><td  align="right"><input type="button" value='<s:text name="general.next" />' onClick="toggleSteps('3',true)" class="navigate" /></td></tr></table>
</span>

<span id="step3">
<!-- pari start -->
<s:div>
		<tr><td></td></tr>
               
                        <tr>
                        <td valign="top"><span class="actionheader"><s:text name="setup.scenarios.icon" /></span></td>
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
    	<script type="text/javascript">
        function populateIcon(iconId)
        {
                    document.getElementById("iconFile").value = iconId;
        }	
	</script>
<!-- pari end -->

<table width="100%" ><tr ><td align="left"><input type="button" value='<s:text name="general.prev" />' onClick="toggleSteps('2')" /></td><td  align="right"><input type="submit" value='<s:text name="general.save" />' id="saveRule"/></td></tr></table>
<!-- <input type="submit" value="Save" id="saveScenaio"/> -->
</span>
</s:form>
