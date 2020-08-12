<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>


<s:form action="updateSwitchTypeForSwitchesAndDimmers.action" method="post" cssClass="ajaxinlinepopupform" id="updateModelNumberform">
	<s:hidden name="device.id"></s:hidden>
	<s:hidden name="device.gateWay.macId"></s:hidden>
	<s:hidden name="device.generatedDeviceId"></s:hidden>
	<s:hidden name="device.switchType" id="switchTypeValue"></s:hidden>
	
	<table>
		<tr><td><s:text name="setup.devices.switches.switchType"></s:text></td>
		<td><select id="switchType" onchange='selectSwitchType()'><option value=''>Select Switch Type</option>
		                            <option value='0'>Brightness controller</option>
		                            <option value='1'>Power level controller</option></select></td>
		</tr>
		
	</table>
	<s:submit key="general.save"></s:submit>
</s:form>


<script type="text/javascript">

		$(document).ready(function() {
			 var value = $("#switchTypeValue").val();
			 
			 $('#switchType').find('option').each(function()
					   {
				 
				 if($(this).val() == value)
		         {
		    	
		            $('#switchType').val(value);
		            $(this).change();
		            
		         }
				 
					   });
			/* if(value == 0){
				$("#Rocker").attr("checked", "checked");
			}else if(value == 1){
				$("#Tact").attr("checked", "checked");
			}  */
			
			
			
			
		});

		function selectSwitchType(){
			
			 var value = $("#switchType").val();
			 document.getElementById("switchTypeValue").value = value;
		 }
		
                function populateIcon(iconId)
                {
                            document.getElementById("d.icon").value = iconId;
                }
                
                function setSwitchType(switchConfId){
                	
                	document.getElementById("switchTypeValue").value = switchConfId;
                }
                
</script>