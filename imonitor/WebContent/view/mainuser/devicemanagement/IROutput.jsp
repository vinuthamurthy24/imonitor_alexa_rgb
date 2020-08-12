<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>

<s:form action="updateIROutputValue.action" method="post" cssClass="ajaxinlinepopupform" id="configureBlasterModelNumberform">
	<s:hidden name="device.generatedDeviceId" id="genDeviceId"></s:hidden>
	<s:hidden name="device.gateWay.macId" id="gwMacID"></s:hidden>
	<s:hidden name="device.modelNumber" id="modelnumber"></s:hidden>
	<s:hidden name="device.batteryStatus" id="batterylevel"></s:hidden>
		
	<s:hidden name="avChannelCode" id="avChannelCode"></s:hidden>
	<table>
	<tr>

	<td><input type="checkbox" class="Channel" id="channel" value="0" />External IR Port</td>
	</tr>
	</table>
	<s:submit key="general.save" id="saveBlasterModel"></s:submit>
</s:form>

<script>
	var cForm = $("#configureBlasterModelNumberform");
	Xpeditions.validateForm(cForm);
$(document).ready(function(){
	
	//Ac blaster changes for IR port
	var IRPort =$("#batterylevel").val(); 
	if (IRPort == 1 ) {
		$(".Channel").attr('checked', true);
	}
	 
	$("#saveBlasterModel").live('click', function(){ 
		var channel;
		//Channel Changes start
		 if ($(".Channel").attr('checked'))
		{
			//channel = $("#channel").val();
			channel = 1;
			$("#avChannelCode").val(channel);
		}
		 else {
			 channel = 0;
				$("#avChannelCode").val(channel);
		 }
		//Channel Changes end
	});

//Learn changes by apoorva end	
	
});

</script>