<%-- Copyright ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>

<style>
  .ui-progressbar {
position: relative;
}
.progress-label {
position: absolute;
left: 20%;
top: 4px;
font-weight: bold;
text-shadow: 1px 1px 0 #fff;
}
 .ui-dialog .ui-dialog-titlebar-close span
{
    display:none;
} 
  </style>
  
<s:form action="updateModelNumber.action" method="post" cssClass="ajaxinlinepopupform" id="updateModelNumberform">
	<s:hidden name="device.generatedDeviceId" id="genDeviceId"></s:hidden>
	<s:hidden name="device.gateWay.macId" id="gwMacID"></s:hidden>
	<s:hidden name="device.modelNumber" id="modelnumber"></s:hidden>
	<s:hidden name="acBrandSelected" id="avBrandSelected"></s:hidden>
	<s:hidden name="avCodeSelected" id="avCodeSelected"></s:hidden>
	<s:hidden name="manualSelectedBrand" id="manualSelectedBrand"></s:hidden>
	<s:hidden name="manualSelectedCode" id="manualSelectedCode"></s:hidden>
	<input type=hidden  id="tostopsearch" value="0"/>
	<s:hidden name="configuredMake.name" id="selectedMakeName"></s:hidden>
	<s:hidden name="configuredMake.number" id="selectedModelNumber"></s:hidden>
	<s:hidden name="learnValue" id="aclearn"></s:hidden>
	<s:hidden name="selectedValue" id="selectedValue"></s:hidden>
	<table>
	<tr>
	
	<td></td>
	<td></td>
	<td></td>
	<td>
	<input type="checkbox" class="Auto" value="Auto Search" />Auto Search
	</td>
	</tr>
	</table>
	<div id="manualselect">
	<label>Select Brand for Ac:</label>
	<s:select name="configuredMake.name" id="manualselectbrand" list="acManualSelect" class="required" onchange='acmanualbrandselect()'>
						<!-- <option value='-1'>Select Brand</option> -->
					</s:select>
	
    
   </div>
<div id="ManualCodeSelect">
	<label>Select Model Number for Ac:</label>
					<select name="configuredMake.number" id="modelSelectBox"  class="required">
						<option value='-1'>Select Model number</option>
					</select>
				</div>
	<div id="BrandSelect">
	<label>Select brand for Ac:</label>
					<select id="acBrandSelectBox"  class="required" onchange='acbrandselect()'>
						<option value='-1'>Select Brand name</option>
					</select>
				</div>
	
	<div id="currentcode" >
	<label>Current model number </label>
	<s:textfield name="" id="current" size="5" key="" ></s:textfield>
	</div>
	
	<div id="previouscode" style="margin:-27px 5px 13px 228px";>
	<label>Previous model number </label>
	<s:textfield name="" id="previous" size="5" key=""></s:textfield>
	</div>
	<div id="loading" style="margin:0px 0px 0px 213px";></div>
	<button name="Search" value="Search" id="searchbutton" onClick='searchcode()' type='button' title='click to search code for selected brand'><s:text name="Search" /></button>
	<button name="Stop" value="Stop" id="stopbutton" onClick='stopSearching()' type='button' title='click to stop searching code'><s:text name="Stop" /></button>
	
	<table id="setPollInterval">
		<tr>
			<td><s:textfield name="pollingInterval" id="pollingInterval" class="required number" key="setup.devices.model.polling"></s:textfield></td>
		</tr>	
	</table>
	<table border="1" id="learningTable">

	<tr><td>OFF</td><td id="td1"> Available </td><td ><button type='button' learnvalue="0" name='l1' class='startLearn'>Start Learning</button><button type='button' class='onOffDryMode' commandParam='0' disabled>Test</button><button type='button' name='ok' class='ok' value='0' disabled>OK</button></td></tr>
	<tr><td>ON (resume)</td><td id="td2">Available</td><td><button type='button' learnvalue="1" name='l2' class='startLearn'>Start Learning</button><button type='button' class='onOffDryMode' commandParam='5' disabled>Test</button><button type='button' name='ok' class='ok' value='0'disabled>OK</button></td></tr>
	<tr><td>19 C cool</td><td id="td3">Available</td><td><button type='button' learnvalue="2" name='l3' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='19' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>20 C cool</td><td id="td4">Available</td><td><button type='button' learnvalue="3" name='l4' class='startLearn'>Start Learning</button><button type='button'  class='test' commandParam='20' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>21 C cool</td><td id="td5">Available</td><td><button type='button' learnvalue="4" name='l5' class='startLearn'>Start Learning</button><button type='button'  class='test' commandParam='21' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>22 C cool</td><td id="td6">Available</td><td><button type='button' learnvalue="5" name='l6' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='22' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button ></td></tr>
	<tr><td>23 C cool</td><td id="td7">Available</td><td><button type='button' learnvalue="6" name='l7' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='23' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>24 C cool</td><td id="td8">Available</td><td><button type='button' learnvalue="7" name='l8' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='24' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>25 C cool</td><td id="td9">Available</td><td><button type='button' learnvalue="8" name='l9' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='25' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>26 C cool</td><td id="td10">Available</td><td><button type='button' learnvalue="9" name='l10' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='26' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>27 C cool</td><td id="td11">Available</td><td><button type='button' learnvalue="10" name='l11' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='27' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>28 C cool</td><td id="td12">Available</td><td><button type='button' learnvalue="11" name='l12' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='28' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>19 C heat</td><td id="td13">Available</td><td><button type='button' learnvalue="12" name='l13' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='19' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>20 C heat</td><td id="td14">Available</td><td><button type='button' learnvalue="13" name='l14' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='20' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>21 C heat</td><td id="td15">Available</td><td><button type='button' learnvalue="14" name='l15' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='21' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>22 C heat</td><td id="td16">Available</td><td><button type='button' learnvalue="15" name='l16' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='22' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>23 C heat</td><td id="td17">Available</td><td><button type='button' learnvalue="16" name='l17' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='23' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>24 C heat</td><td id="td18">Available</td><td><button type='button' learnvalue="17" name='l18' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='24' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>25 C heat</td><td id="td19">Available</td><td><button type='button' learnvalue="18" name='l19' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='25' disabled>Test</button><button type='button'  name='ok'value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>26 C heat</td><td id="td20">Available</td><td><button type='button' learnvalue="19" name='l20' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='26' disabled>Test</button><button type='button'  name='ok'value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>27 C heat</td><td id="td21">Available</td><td><button type='button' learnvalue="20" name='l21' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='27' disabled>Test</button><button type='button'  name='ok'value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>28 C heat</td><td id="td22">Available</td><td><button type='button' learnvalue="21" name='l22' class='startLearn'>Start Learning</button><button type='button' class='test' commandParam='28' disabled>Test</button><button type='button'  name='ok'value=0 class='ok' disabled>OK</button></td></tr>
	<tr><td>Dry mode</td><td id="td23">Available</td><td><button type='button'  learnvalue="22" name='l23' class='startLearn'>Start Learning</button><button type='button' class='onOffDryMode' commandParam='8' disabled>Test</button><button type='button' name='ok' value=0 class='ok' disabled>OK</button></td></tr>
	
	</table>


<table>
<tr><td></td><td style=" float: right;margin: 0px 0px 0px 333px;"><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel"/></td><td><s:submit theme="simple" key="general.save" id="validateMakeBeforeSave" onClick="javascript:DestoryDialog()"></s:submit></td></tr>
</table>
</s:form>
	<div id="instDialog"></div>
<script>

function DestoryDialog()
{
           $("#editmodal").dialog('destroy'); 
}	


	var cForm = $("#updateModelNumberform");
	Xpeditions.validateForm(cForm);
	 var j = 0;
	
	 var brandcodes = []; 
	 
	 function acmanualbrandselect(){
		 var name = $("#manualselectbrand").val();
		 var params = {"acSelectedBrand" : name};
		 $.ajax({
				url: "togetbrandCodesforselect.action",
				data: params,
				dataType: 'xml',
				success:  handleManualSelectChange
				
			});	 
		 if(name == "Learn"){
			$("#learningTable").show();
		 }else{
			 $("#learningTable").hide();
		 }
		 
	 }
	 
	 var handleManualSelectChange = function(xml) {
			
			var alertOptionHtml = "";
		    $(xml).find("acCode").each(function() {
		    	
			var Number = $(this).find("code").text();
			alertOptionHtml += "<option value='" + Number + "'>" + Number+ "</option>";
			$("#modelSelectBox").html(alertOptionHtml);
						
		});
		    
		    var numberHtml = $("#selectedModelNumber").val();
			 $('#modelSelectBox').find('option').each(function()
					   {
				
					      if($(this).val() == numberHtml)
					         {
					    	//  alert("selected model number: "+ numberHtml);
					            $('#modelSelectBox').val(numberHtml);
					            $(this).change();
					            
					         }
					   });
		};	
		
	 function acbrandselect(){
		 var name = $("#acBrandSelectBox").val();
		 var params = {"acSelectedBrand" : name};
		 
		 $.ajax({
				url: "togetAcbrandCodes.action",
				data: params,
				dataType: 'xml',
				success:  handleBrandSelectChange
				
			});	 
		 
	 }
		var handleBrandSelectChange = function(xml) {
			brandcodes[i] = "";
			try{
				var i = 0;
				$(xml).find("acCode").each(function() {
					
					var code = $(this).find("code").text();
					brandcodes[i] = code;
					i++;
				});
			}catch(err){
				alert(err.message);
			}
		};
	 
	 
	 function searchcode(){
		 var name = $("#acBrandSelectBox").val();

		 if(name == -1){
			 alert("Please select Ac Brand to Proceed");
		 }else{
	//	 $("#progressbar").show();
		 $("#currentcode").show();
		 $("#previouscode").show();
		 $("#searchbutton").hide();
		 $("#stopbutton").show();
		 $("#tostopsearch").val("1");
		 set_codes();
			$('#loading').html("<img src='../resources/images/loading.gif'/>");
		
		 }
		};
        
		
		function set_codes(){
			brandcodes[j];
		
			var k = $("#tostopsearch").val();
			$("#currentcode").attr("value", brandcodes[j]);
		
			if(brandcodes[j] != null && k == 1){
			
			
			var generatedDeviceId = $("#genDeviceId").val();
			var macId = $("#gwMacID").val();
			var params = "device.generatedDeviceId=" + generatedDeviceId;
		        params += "&device.gateWay.macId=" + macId;
		    var code = brandcodes[j];
		        params += "&acSetCode=" + code;
		        
				$.ajax({
		   		 url: "tosetAcCode.action",
					 data: params,
		    		 success: function(data){
				}
					});
			$("#current").val(brandcodes[j]);
			$("#previous").val(brandcodes[j-1]);
			setTimeout(set_codes,10000);
			}else if(brandcodes[j] == null){
			//	$("#stopbutton").hide();
				$("#validateMakeBeforeSave").show();
				$("#loading").html("");
			}
			j++;
			
		
		}	
		
		
		function stopSearching(){
			 
			 $("#tostopsearch").val("0");
			 $("#validateMakeBeforeSave").show();
			 $("#loading").html("");
			 brandcodes.length = 0;
			 j = 0;
             			
		};
		
		var handleCategorySelectChange = function(xml) {
			
		    $(xml).find("acBrand").each(function() {
		    	
			var brandName = $(this).find("brandName").text();
			$("#acBrandSelectBox").append("<option value='"+brandName+"' >"+brandName+"</option>");
						
		});
		};	
		
		
		
		
			$(".startLearn").click(function() {	
				
			
			var valueToLearn = $(this).attr('learnvalue');
			
			$curr = $(this).next();
			$curr.removeAttr("disabled");
			$curr.next().removeAttr("disabled");
			var generatedDeviceId = $("#genDeviceId").val();
			var macId = $("#gwMacID").val();
			
			 $("#instDialog").html('<img src="../resources/images/learn.png" "/>'+ 
					              '<table><tr><td> 1: Request end user to configure his original remote control to the operating mode (dry, heat '+
                                   'or cool), target temperature, and other desired settings that he would like to learn</td></tr><tr></tr><tr></tr><tr></tr>'+
                                   '<tr><td>2: Press the "OFF" or "STOP" button of his original remote control</td></tr><tr></tr><tr><tr><tr></tr>'+
                                   '<tr><td>3: Request end user to "Press and HOLD" the "ON" or "START" button of your original remote control until the status LED of ZXT-120 starts flashing. ZXT-120 will flash the status LED'+
                                   'twice if the operation is successful. It will flash the LED six times if it fails. Some remotecontrols have weak IR transmission signals. If it failed, we should try again by moving the'+
                                    'original remote closer to the IR receiver of ZXT-120.</td></tr></table>');
			 
		     $("#instDialog").dialog('open');
			 $("#instDialog").dialog({
				       
				        width: 550,
		                height: 650,
		                modal : true,
		                
		                title: "Follow this procedure",
		                 buttons: {
		 					Ok: function() {
		 					
		 						$( this ).dialog('destroy');
		 						var params = "device.generatedDeviceId=" + generatedDeviceId;
		 				        params += "&device.gateWay.macId=" + macId;
		 				        params += "&learnValue=" + valueToLearn;
		 						$.ajax({
		 					   		     url: "toLearnAc.action",
		 								 data: params,
		 					    		 success: function(data){
		 					    		
		 							}
		 							});
		 						
		 					},
			 Cancel:function(){
				 $(this).dialog('close');
				 $(this).dialog('destroy');
			 }
		 					}
		        		});
			
					
		 });
			$(".onOffDryMode").click(function() {	
		//$(".onOffDryMode").live('click', function(){
			var command = $(this).attr('commandParam');
			var generatedDeviceId = $("#genDeviceId").val();
			var macId = $("#gwMacID").val();
			var params = "device.generatedDeviceId=" + generatedDeviceId;
			    params += "&device.commandParam=" + command;
		        params += "&device.gateWay.macId=" + macId;
			$.ajax({
		   		     url:"controlactoTestlearn.action",
					 data: params,
		    		 success: function(data){
		    			 
				}
					});
			
		});
		$(".test").click(function() {	
		//$(".test").live('click', function(){
			var command = $(this).attr('commandParam');
			var generatedDeviceId = $("#genDeviceId").val();
			var macId = $("#gwMacID").val();
			var params = "device.generatedDeviceId=" + generatedDeviceId;
			    params += "&device.commandParam=" + command;
		        params += "&device.gateWay.macId=" + macId;
			$.ajax({
		   		     url:"changeAcTemperatureForLearn.action",
					 data: params,
		    		 success: function(data){
		    			 
				}
					});
			
		});
		
		$(".ok").click(function() {	
		//$(".ok").live('click', function(){
			$curr = $(this).prev();
			$curr.attr("disabled", "disabled");
			$curr.prev().attr("disabled", "disabled");
			$(this).val(1);
					
		});
	    
$(document).ready(function(){
	j=0;
	var learn='<s:property value="learnValue"/>';
	 var res = learn.split(",");
	 if(learn != null){
		// var sp = res[0].replace("[","");
         //      var ps = res[22].replace("]","");
           //    res[0] = sp;
            //   res[22] = ps;

	 for(var i=0;i<=res.length;i++)
		 {
			 
			 if(res[i] == 1 && i == 0){$("#td1").html("Used");$("#td1").closest("td").next().find('button[name="l1"]').html("Re-learn"); $("#td1").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 1){$("#td2").html("Used"); $("#td2").closest("td").next().find('button[name="l2"]').html("Re-learn");$("#td2").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 2){$("#td3").html("Used");$("#td3").closest("td").next().find('button[name="l3"]').html("Re-learn");$("#td3").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 3){$("#td4").html("Used");$("#td4").closest("td").next().find('button[name="l4"]').html("Re-learn");$("#td4").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 4){$("#td5").html("Used");$("#td5").closest("td").next().find('button[name="l5"]').html("Re-learn");$("#td5").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 5){$("#td6").html("Used");$("#td6").closest("td").next().find('button[name="l6"]').html("Re-learn");$("#td6").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 6){$("#td7").html("Used");$("#td7").closest("td").next().find('button[name="l7"]').html("Re-learn");$("#td7").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 7){$("#td8").html("Used");$("#td8").closest("td").next().find('button[name="l8"]').html("Re-learn");$("#td8").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 8){$("#td9").html("Used");$("#td9").closest("td").next().find('button[name="l9"]').html("Re-learn");$("#td9").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 9){$("#td10").html("Used");$("#td10").closest("td").next().find('button[name="l10"]').html("Re-learn");$("#td10").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 10){$("#td11").html("Used");$("#td11").closest("td").next().find('button[name="l11"]').html("Re-learn");$("#td11").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 11){$("#td12").html("Used");$("#td12").closest("td").next().find('button[name="l12"]').html("Re-learn");$("#td12").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 12){$("#td13").html("Used");$("#td13").closest("td").next().find('button[name="l13"]').html("Re-learn");$("#td13").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 13){$("#td14").html("Used");$("#td14").closest("td").next().find('button[name="l14"]').html("Re-learn");$("#td14").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 14){$("#td15").html("Used");$("#td15").closest("td").next().find('button[name="l15"]').html("Re-learn");$("#td15").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 15){$("#td16").html("Used");$("#td16").closest("td").next().find('button[name="l16"]').html("Re-learn");$("#td16").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 16){$("#td17").html("Used");$("#td17").closest("td").next().find('button[name="l17"]').html("Re-learn");$("#td17").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 17){$("#td18").html("Used");$("#td18").closest("td").next().find('button[name="l18"]').html("Re-learn");$("#td18").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 18){$("#td19").html("Used");$("#td19").closest("td").next().find('button[name="l19"]').html("Re-learn");$("#td19").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 19){$("#td20").html("Used");$("#td20").closest("td").next().find('button[name="l20"]').html("Re-learn");$("#td20").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 20){$("#td21").html("Used");$("#td21").closest("td").next().find('button[name="l21"]').html("Re-learn");$("#td21").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 21 ){$("#td22").html("Used");$("#td22").closest("td").next().find('button[name="l22"]').html("Re-learn");$("#td22").closest("td").next().find('button[name="ok"]').val(1);}
			 if(res[i] == 1 && i == 22){$("#td23").html("Used");$("#td23").closest("td").next().find('button[name="l23"]').html("Re-learn");$("#td23").closest("td").next().find('button[name="ok"]').val(1);}
		 }
	 }
	/*if(learn != null){
		var l = 0;
		var m = 0;
		var abc = learn.split(",");
		for(l=0; l<=abc.length;l++){
			//alert(abc[l]);
			if(abc[l]== "0"){
				//alert("inside if");
				var statushtml = $(".staus["+m+"]");
				statushtml.html("Available");
				m++;
			}else{
				
			//	$(".staus[]").html("Used");
				m++;
			}
		}
		
	}*/
	$("#learningTable").hide();
	acmanualbrandselect();
	<s:iterator value="acManualSelect" var="ac" status="statusVar">
    $('#manualselectbrand').append('<option value="<s:property value="#ac"/>">'+ "<s:property value="#ac"/>" +'</option>');
	</s:iterator>

   $("#progressbar").hide();
   $("#BrandSelect").hide();
   $("#stopbutton").hide();
   $("#autoMakeselect").hide();
   $("#searchbutton").hide();
   $("#currentcode").hide();
   $("#previouscode").hide();
	var poll = $("#pollingInterval").val();
	
	if(poll < '10'){
		$("#setPollInterval").hide();
		
	}
   
	var modelNum = $("#modelnumber").val();
		if(modelNum == "ZXT110"){
			$("#setPollInterval").hide();
			
		}
	
	//$("#makeSelect").live('change', function(){
	//	var pollHtml = $("#pollingInterval");
	//	var modelNum = $("#modelnumber").val();
	//	if(modelNum == "ZXT110"){
	//		pollHtml.removeClass('number');
	//		pollHtml.removeClass('errorclass');
	//	}
		
	//});	

	//var modelNum = $("#modelnumber").val();
	//if(modelNum == "ZXT110"){
	//	$("#pollingInterval").val("invalid");
		
	//}

   $("#validateMakeBeforeSave").click(function() {	
	//$("#validateMakeBeforeSave").live('click', function(){
		var val = $("#manualselectbrand").val();
		var currentcode = $("#current").val();
		if(val == -1 && currentcode == "" ){
			
			
			showResultAlert('<s:text name="setup.devices.msg.0006" />' );	
			return false;	
		}
		else if(currentcode != ""){
			
			var name = $("#acBrandSelectBox").val();
		//	var abc = $('#acBrandSelectBox').find(":selected").text();
			$("#avCodeSelected").val(currentcode);
			$("#avBrandSelected").val(name);
			
			
		}else{
			var manualselectcode = $("#modelSelectBox").val();
			var manualselectname = $("#manualselectbrand").val();
			var selectValue = "";
			if(manualselectname == "Learn"){
				
				$('#learningTable tr').each(function() {
					var nextValue =  $(this).find('button[name="ok"]').val();
					selectValue += nextValue + ",";
				     
				    });
				//selectValue = selectValue.substring(0, selectValue.length - 1);
							
			}
			
			$("#selectedValue").val(selectValue);
			$("#avCodeSelected").val(manualselectcode);
			$("#avBrandSelected").val(manualselectname);
		}
	});

	
	
		
	var pollHtml = $("#pollingInterval");
	var modelNum = $("#modelnumber").val();
	if((modelNum == "ZXT120") || (modelNum == "ZXT600")){
		
		pollHtml.addClass('number');
	}
	
	pollHtml.attr('size',3);
	pollHtml.attr('maxlength',3);
	pollHtml.attr('minvalue',10);
	pollHtml.attr('maxvalue',180);

	
	$(".Auto").live('click', function(){
		
		if($(this).attr('checked')){
		
		 $("#BrandSelect").show();
		 $("#manualcodeselect").hide();
		 $("#autoMakeselect").show();
		 $("#manualselect").hide();
		 $("#searchbutton").show();
	     $("#validateMakeBeforeSave").hide();
	     $("#ManualCodeSelect").hide();
	     $("#learningTable").hide();
	     var generatedDeviceId = $("#genDeviceId").val();
	     var targetUrl = "getModelListOfAc.action";
	     var params = "device.generatedDeviceId=" + generatedDeviceId;
 	     $.ajax({

				url: targetUrl,
				data: params,
				dataType: 'xml',
				success: handleCategorySelectChange
			});	 
		}else{
			$("#learningTable").hide();
			 $("#currentcode").hide();
			 $("#previouscode").hide();
			 $("#BrandSelect").hide();
			 $("#manualselect").show();
			 $("#manualcodeselect").show();
			 $("#searchbutton").hide();
			 $("#validateMakeBeforeSave").show();
			 $(".manual").attr('checked','true');
			 $("#ManualCodeSelect").show();
			 $("#avCodeSelected").val();
			$("#avBrandSelected").val();
			$("#stopbutton").hide();
		}
	});
	
	
	
	
	
	

});

</script>