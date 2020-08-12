<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>

<!-- this is a dummy form that preceeds the actual form -->
<s:form  id="dummy" >
        


 </s:form>
 <!-- end of dummy form -->
 
<s:form action="updateFriendlyNameAndLocation.action" theme="simple" method="post" cssClass="ajaxinlinepopupform" id="updatefriendlynameandlocationform">
        <s:hidden name="device.id" ></s:hidden>
        <s:hidden name="device.icon.id" id="d.icon"></s:hidden>
        <s:hidden name="device.icon.fileName" id="currentIcon"></s:hidden>
       	<s:hidden name="oldLocationId" id="oldLocationId"></s:hidden>
 	<table>
        <tr><td><s:text name="setup.devices.edit.name" /></td><td>
	<s:textfield name="device.friendlyName" label="" id="fName" maxlength='35' cssClass="required editdisplayStar"></s:textfield>
	</td></tr>
		<tr><td></td></tr>
        <tr><td><s:text name="setup.devices.edit.location" /></td><td >
        <s:select id="loc" name="device.location.id" label="" list="locations" listKey="id" listValue="name"></s:select>
	</td> 
	</tr>
	<tr><td>
	<!-- Add Room buttom enhancement -->
	<td>
				<a class='editroomlink' href="toAddLocation.action"  popupheight="450" popupwidth="850" popuptitle='<s:text name="setup.rooms.add" />'
				title="Add a new a room"><span class='titlespangeneric'><s:text name="setup.rooms.add" /></span></a>
			</td>
	</td></tr>
	<tr><td></td></tr>
        <s:if test="makes.size > 0">
	<tr><td><s:text name="setup.devices.edit.model" /></td><td>
	
                <s:select name="device.modelNumber" id="make" label="" list="makes" listKey="number" listValue="name"></s:select>
	</td></tr>
	<tr><td></td></tr>
        </s:if>
		<s:div>
		<tr><td></td></tr>
                <tr>
                    <td valign="top"><s:text name="setup.devices.edit.curricon" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td ><img src="<s:property value='device.icon.fileName' />" class="deviceicon" /></td>
                        </tr>

                        <tr>
                        <td valign="top"><s:text name="setup.devices.edit.chooseicon" /></td>
                        <td>
                                <table>
                                	<tr>
                                    	<s:iterator value="listIcons"  status="abc" >
        									<s:if test="#abc.count == 5 || #abc.count == 9 ||  #abc.count == 13 ||  #abc.count == 17">
                					</tr>
                					<tr>
        									</s:if>
        									<td><input type='radio'  onClick="javascript: populateIcon(<s:property value='id' />)"  name='selectIcon' value='<s:property value="id" />'/>
        									<img class='listdeviceicon' src='<s:property value="fileName" />'/></td>
        									<td>&nbsp;</td>
                                        </s:iterator>
                                    </tr>
                                        </table>
                                </td>
                        </tr>

                </s:div>
    	<tr><td></td></tr>
	
	<tr><td><s:text name="setup.devices.edit.show" /></td>
	<td><s:checkbox name="device.checkEnableList" value="%{device.checkEnableList}"/>
	</td></tr>
	
	<s:if test='device.deviceType.name.equals("Z_WAVE_SIREN") || device.deviceType.name.equals("Z_WAVE_SWITCH") || device.deviceType.name.equals("Z_WAVE_DIMMER") || device.deviceType.name.equals("ZWAVE_CLAMP_SWITCH")' >
	<%-- <s:if test='device.deviceType.name.equals("Z_WAVE_SIREN")'>
	
	<tr><td><s:text name="setup.devices.edit.timeout" /></td>
	<td><s:checkbox name="zWavetimeout" value="%{zWavetimeout}"/>
	 <s:hidden name="timeout" value="0"></s:hidden>
	</td></tr>
	</s:if> --%>
	
	 <span><td>Add PulseTimeOut<input type="checkbox" id="pulseTimecheckBox" name="" value=""></td></span>
	
	
	<s:if test='device.deviceType.name.equals("Z_WAVE_SWITCH") || device.deviceType.name.equals("Z_WAVE_DIMMER") || device.deviceType.name.equals("Z_WAVE_SIREN")' >
	<tr id="timeOutSection"> <td><s:text name="setup.devices.edit.timeout" /></td> 
	<td><s:radio theme="simple" list="#{'1':''}"   name="timeout"  cssClass="timeOut"/>
	</td></tr>
	<s:if test='device.deviceType.name.equals("Z_WAVE_SWITCH") || device.deviceType.name.equals("Z_WAVE_SIREN")' >
	<tr id="pulsetimeOutSection">
	
	<td>Pulse Time Out: </td>
	<td><s:radio theme="simple" list="#{'2':''}"  name="timeout"  cssClass="timeOut" /> <s:textfield id="showPulseTime" name='device.pulseTimeOut' label="Value" size="5" minvalue="0" maxvalue="60" ></s:textfield> 
		</td>
	
	</tr>
	</s:if> 
	<s:else>
	<s:hidden name="device.pulseTimeOut" value="0" />
	</s:else>
	</s:if>

	
	<s:if test='device.deviceType.name.equals("Z_WAVE_SWITCH")' >
	<tr><td><s:text name="setup.devices.edit.HMD" /></td>
	<td><s:checkbox name="hmdDevice" value="%{hmdDevice}"/>
	</td></tr>
	</s:if>
	</s:if>
	 <s:else>
		<s:hidden name="timeout" value="0" />
		<s:hidden name="device.pulseTimeOut" value="0" />
	</s:else> 
	<tr><td></td></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr><td></td><td style=" float: right;"><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel"/></td><td><s:submit theme="simple" key="general.save"></s:submit></td></tr>
	</table>
</s:form>
<script>
	var cForm = $("#updatefriendlynameandlocationform");
	Xpeditions.validateForm(cForm);
</script>

<script type="text/javascript">

			var pulse=null;
             $(document).ready(function(){ 
            	
   
            	 pulse = $("#showPulseTime").val();

            	 
            	 
            	 if((pulse > 0) || ($('.timeOut').is(':checked')) ){
            		 $("#timeOutSection").show();
             		$("#pulsetimeOutSection").show();
             		$("#pulseTimecheckBox").attr("checked",true);
            	 }else{
            		 $("#pulseTimecheckBox").attr("checked",false);
            		 $("#timeOutSection").hide();
              		$("#pulsetimeOutSection").hide();
            	 }
            	 
             });
            	
            	$("#showPulseTime").hide();
            	
            
                function DestoryDialog()
                {
                           $("#editmodal").dialog('destroy'); 
                }	
                
                function populateIcon(iconId)
                {
                            document.getElementById("d.icon").value = iconId;
                }
                
              
               
                 
                 $(".timeOut").die("click");
                 $(".timeOut").live("click", function(){
         			var selectedOption = $(this).val();
         			
         			if(selectedOption == "2"){
         				
         				$("#showPulseTime").show();
         				$("#showPulseTime").addClass("number");
         				$("#showPulseTime").addClass("starclass");
         				$("#showPulseTime").val(pulse);
         				//$("#showPulseTime").addClass("required");
         			}else{
         				
         				$("#showPulseTime").hide();
         				$("#showPulseTime").val("0");
         				/* $("#showPulseTime").removeClass("required");
         				$("#showPulseTime").removeClass("number");
         				$("#showPulseTime").removeClass("starclass"); */
         			}
         			return true;
         		});
                 
                  if($('input[name=timeout]:radio:checked').val() == "2"){
                	  $("#showPulseTime").show();
       				$("#showPulseTime").addClass("number");
                 } 
                  
                  
                    $('#pulseTimecheckBox').click(function() {
                    	
                    	if ($("#pulseTimecheckBox").attr("checked")){
                    		$("#timeOutSection").show();
                    		$("#pulsetimeOutSection").show();
                    	}else{
                    		$("#timeOutSection").hide();
                    		$("#pulsetimeOutSection").hide();
                    		$("#showPulseTime").val("0");
                    		$(".timeOut").removeAttr('checked');
                    	}
                	   
                	});   
                  
	    
          /*   }); */

                
                
</script>
				