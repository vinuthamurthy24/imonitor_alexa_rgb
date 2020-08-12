<%-- Copyright Ãƒâ€šÃ‚Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>


<s:form action="nupdateDevice.action" method="post" cssClass="ajaxinlinepopupform" id="usersaveform">
	<s:hidden id="did" name="device.id"></s:hidden>
	<s:hidden id="gwMacID" name="device.gateWay.macId"></s:hidden>
	<s:hidden id="genDeviceId" name="device.generatedDeviceId"></s:hidden>
	
	
	<s:hidden name="device1.id"> </s:hidden>
	<s:hidden name="device2.id"> </s:hidden>
	<s:hidden name="device3.id"> </s:hidden>
	<s:hidden name="device4.id"> </s:hidden>
	<s:hidden name="device5.id"> </s:hidden>

	<table>
		<tr><%-- <s:text name="setup.devices.operationMode"></s:text> --%>
		<s:select label='Choose Operation' id='selectTag' onchange="redirectToAction()" list="#{'':'Select Option','1':'Update Neighbour','2':'Associate','3':'Switch Configure','4':'Assign Return Route','5':'Normal Switch','6':'Node Protocol Info','7':'Get Routing Info','8':'MultiChannel Associate'}" value="Status" cssClass="required" name="device.switchType"></s:select></tr>
		
		<table id='table'>
	<tr><td Width="15px">1.</td><td Width="150px"><select name = "device1.id" id="devicelist1" class="required" onchange="deviceList()"><option value="0">Select Device</option></select></td></tr>
	<tr><td>2.</td><td Width="150px"><select name = "device2.id" id="devicelist2" class="required" onclick=""><option value="0">Select Device</option></select></td></tr>
	<tr><td>3.</td><td Width="150px"><select name = "device3.id" id="devicelist3" class="required" onclick=""><option value="0">Select Device</option></select></td></tr>
	<tr><td>4.</td><td Width="150px"><select name = "device4.id" id="devicelist4" class="required" onclick=""><option value="0">Select Device</option></select></td></tr>
	<tr><td>5.</td><td Width="150px"><select name = "device5.id" id="devicelist5" class="required" onclick=""><option value="0">Select Device</option></select></td></tr>
	</table>
		
		<tr><td style=" float: right;"><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel"/></td>
		<td><s:submit disabled="true" theme="simple" id="Save" key="general.save"></s:submit></td>
		</tr>	
	
	
	</table>
	
	
	
</s:form>

<div id="instDialog"></div>
<script type="text/javascript">

	

$(document).ready(function(){
	 $("#table").hide();
	 var generatedDeviceId = $("#genDeviceId").val();
		var macId = $("#gwMacID").val();
		var did = $("#did").val();
	 
	 var params = "device.generatedDeviceId=" + generatedDeviceId;
     params += "&device.gateWay.macId=" + macId;
    params += "&device.id=" + did;
		$.ajax({
	   		     url: "multichannel.action",
				 data: params,
	    		 success: function(data){
	    		handleCategorySelectChange(data);
			}
			});
});






function DestoryDialog()
        {
                   $("#editmodal").dialog('destroy'); 
        }	
	 
	 function redirectToAction()
	 {
		 
		 var value = $("#selectTag").val();
		 if (value==8) 
		 {
			 $("#table").show(); 
			//$('#usersaveform').attr('action', 'sendMultiAssociateDevices.action');
			 $("#Save").click(function(e) {
				  //e.preventDefault();
				 $('#usersaveform').attr('action', 'sendMultiAssociateDevices.action');
				 var generatedDeviceId = $("#genDeviceId").val();
					var macId = $("#gwMacID").val();
					var did = $("#did").val();
				 
					
					
				 var idElement1 = document.getElementById("devicelist1");
					var selectedValue1 = idElement1.options[idElement1.selectedIndex].value;
					
					var idElement2 = document.getElementById("devicelist2");
					var selectedValue2 = idElement2.options[idElement2.selectedIndex].value;
					
					var idElement3 = document.getElementById("devicelist3");
					var selectedValue3 = idElement3.options[idElement3.selectedIndex].value;
					
					var idElement4 = document.getElementById("devicelist4");
					var selectedValue4 = idElement4.options[idElement4.selectedIndex].value;
					
					var idElement5 = document.getElementById("devicelist5");
					var selectedValue5 = idElement5.options[idElement5.selectedIndex].value;
					 
				
					 
					 
					 var params = "device.generatedDeviceId=" + generatedDeviceId;
			        params += "&device.gateWay.macId=" + macId;
			       	params += "&device.id=" + did;
					 params += "&device1.generatedDeviceId=" + selectedValue1;
			        params += "&device2.generatedDeviceId=" + selectedValue2;
			       params += "&device3.generatedDeviceId=" + selectedValue3; 
			      params += "&device4.generatedDeviceId=" + selectedValue4; 
			     params += "&device5.generatedDeviceId=" + selectedValue5; 
			    // alert(params);	
			     
			     $.ajax({
				   		     url: "sendMultiAssociateDevices.action",
							 data: params,
				    		 success: function(data){
				    		//handleCategorySelectChange(data);
				    			 $("#editmodal").dialog('close'); 
				    			
						}
						}); 
				  
				});
			
		
		 }
		 else
			 {
			 $("#table").hide();
			 }
			 /* var generatedDeviceId = $("#genDeviceId").val();
				var macId = $("#gwMacID").val();
				var did = $("#did").val();
			 
			 var params = "device.generatedDeviceId=" + generatedDeviceId;
		        params += "&device.gateWay.macId=" + macId;
		       params += "&device.id=" + did;
				$.ajax({
			   		     url: "multichannel.action",
						 data: params,
			    		 success: function(data){
			    		handleCategorySelectChange(data);
					}
					});
			 
			 var valueToLearn = value;
				
				$curr = $(this).next();
				$curr.removeAttr("disabled");
				$curr.next().removeAttr("disabled");
				var generatedDeviceId = $("#genDeviceId").val();
				var macId = $("#gwMacID").val();
				var did = $("#did").val();
				 $("#instDialog").html('<table id="table"><tr><td></td></tr>'
						+'<tr><td Width="15px">1.</td><td Width="150px"><select name = "device1.id" id="devicelist1" class="required" onchange="deviceList()"><option value="0">Select Device</option></select></td></tr>'
						+'<tr><td>2.</td><td Width="150px"><select name = "device2.id" id="devicelist2" class="required" onclick=""><option value="0">Select Device</option></select></td></tr>'
						+'<tr><td>3.</td><td Width="150px"><select name = "device3.id" id="devicelist3" class="required" onclick=""><option value="0">Select Device</option></select></td></tr>'
						+'<tr><td>4.</td><td Width="150px"><select name = "device4.id" id="devicelist4" class="required" onclick=""><option value="0">Select Device</option></select></td></tr>'
						 +'<tr><td>5.</td><td Width="150px"><select name = "device5.id" id="devicelist5" class="required" onclick=""><option value="0">Select Device</option></select></td></tr></table>');
				 // $("#editmodal").dialog('destroy'); 
			     $("#instDialog").dialog('open');
				 $("#instDialog").dialog({
					       
					        width: 600,
			                height: 350,
			                modal : true,
			                
			                title: "Multi Channel Association",
			                 buttons: {
			 					Ok: function() {
			 						$( this ).dialog('destroy');
			 						
			 						var idElement1 = document.getElementById("devicelist1");
			 						var selectedValue1 = idElement1.options[idElement1.selectedIndex].value;
			 						
			 						var idElement2 = document.getElementById("devicelist2");
			 						var selectedValue2 = idElement2.options[idElement2.selectedIndex].value;
			 						
			 						var idElement3 = document.getElementById("devicelist3");
			 						var selectedValue3 = idElement3.options[idElement3.selectedIndex].value;
			 						
			 						var idElement4 = document.getElementById("devicelist4");
			 						var selectedValue4 = idElement4.options[idElement4.selectedIndex].value;
			 						
			 						var idElement5 = document.getElementById("devicelist5");
			 						var selectedValue5 = idElement5.options[idElement5.selectedIndex].value;
			 						
			 						
			 						 var params = "device.generatedDeviceId=" + generatedDeviceId;
			 				        params += "&device.gateWay.macId=" + macId;
			 				       	params += "&device.id=" + did;
			 						 params += "&device1.generatedDeviceId=" + selectedValue1;
			 				        params += "&device2.generatedDeviceId=" + selectedValue2;
			 				       params += "&device3.generatedDeviceId=" + selectedValue3; 
			 				      params += "&device4.generatedDeviceId=" + selectedValue4; 
			 				     params += "&device5.generatedDeviceId=" + selectedValue5; 
			 						$.ajax({
			 					   		     url: "sendMultiAssociateDevices.action",
			 								 data: params,
			 					    		 success: function(data){
			 					    		//handleCategorySelectChange(data);
			 					    			//$( this ).dialog('destroy');
			 							}
			 							}); 
			 						
			 					},
				 Cancel:function(){
					 $(this).dialog('close');
					 $(this).dialog('destroy');
				 }
			 					}
			        		});
		}
		 else
			 {
			 return;
			 } */
	 }
	 
	 function deviceList()
	 {
			//handleCategorySelectChange();
			
		/* 	//Add Action
			//var targetUrl = ".action";
			var id= $("#gwMacID").val();
			var params = {"actionName" : actionSelected,"gateWay.id" :id };
			$.ajax({

				url: targetUrl,
				data: params,
				dataType: 'xml',
				success: handleCategorySelectChange
			});	 */
		
	 }
	 
	 var handleCategorySelectChange = function(xml) {	
		/*  var XMLS = new XMLSerializer(); 
			var inp_xmls = XMLS.serializeToString(xml); */
			
			//$("#devicelist").empty();
		/* 	var deviceDefaultOption = "<option value='-1'>Select Device</option>";
			$("#devicelist").append(deviceDefaultOption); */
			try{
				$(xml).find("Device").each(function() {
					var deviceGenId = $(this).find("deviceGenId").text();
					var friendlyName = $(this).find("friendlyName").text();
					var Id = $(this).find("id").text();
					$("#devicelist1").append("<option value='"+deviceGenId+"'>"+friendlyName+"</option>");
					$("#devicelist2").append("<option value='"+deviceGenId+"'>"+friendlyName+"</option>");	
					$("#devicelist3").append("<option value='"+deviceGenId+"'>"+friendlyName+"</option>");			
					$("#devicelist4").append("<option value='"+deviceGenId+"'>"+friendlyName+"</option>");			
					$("#devicelist5").append("<option value='"+deviceGenId+"'>"+friendlyName+"</option>");			
				});
			}catch(err){
				alert(err.message);
			}
			$("#devicelist").show();
		};
	 
	 

</script>