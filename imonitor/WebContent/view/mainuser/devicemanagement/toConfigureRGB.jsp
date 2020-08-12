<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>


<s:form action="UpdateRgb.action" method="post" cssClass="ajaxinlinepopupform" id="updateModelNumberform">
	<s:hidden name="device.id"></s:hidden>
	<s:hidden name="device.gateWay.macId"></s:hidden>
	<s:hidden name="device.generatedDeviceId"></s:hidden>
	<s:hidden name="device.switchType" id="switchTypeValue"></s:hidden>
	
	<table>
		<tr><td><s:text name="setup.devices.RGB.switchType"></s:text></td>
		<td><select id="rgbType" onchange='selectRGBType()'><option value=''>Select RGB</option>
		                            <option value='0'>color not Selected</option>
		                            <option value='1'>color Selected</option></select></td>
		</tr>
		
	</table>
	<s:submit key="general.save"></s:submit>
</s:form>


<script type="text/javascript">

		$(document).ready(function() {
			 var value = $("#switchTypeValue").val();
			
			 $('#rgbType').find('option').each(function()
					   {
				 
				 if($(this).val() == value)
		         {
		    	
		            $('#rgbType').val(value);
		            $(this).change();
		            
		         }
				 
					   });
			
			
			
			
		});

		function selectRGBType(){
			
			 var value = $("#rgbType").val();
			
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