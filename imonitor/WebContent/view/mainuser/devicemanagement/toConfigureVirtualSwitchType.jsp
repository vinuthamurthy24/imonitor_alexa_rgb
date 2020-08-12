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
		<td><select id="switchType" onchange='selectSwitchType()'><option value='4'>Select Switch Type</option>
		                            <option value='2'>Brightness controller</option>
		                            <option value='3'>Power level Controller</option>
		                             <option value='6'>2-Way Switch</option>
		                            <option value='7'>Scenario Control</option></select></td>
		                            
		</tr>
		<!-- <td><input type="radio" name="switchConfig" class="switchConf" id="Rocker" value="0" onClick="javascript: setSwitchType(this.value)">Rocker</td>
		<td><input type="radio" name="switchConfig" class="switchConf" id="Tact" value="1" onClick="javascript: setSwitchType(this.value)">Tact</td></tr> -->
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