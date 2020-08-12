<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%><div align="left" class="make">
                <div style="color: blue;">
                <s:property value="#session.message"/>
                </div>
                <%ActionContext.getContext().getSession().remove("message");%>
			<s:select list="deviceTypes" name="make.deviceType.id" listKey="id" listValue="name" headerKey="0" headerValue="Please Select any Device Type"  onchange="javascript: formVisibility(this.options[this.selectedIndex])" align="left"></s:select>
              
        <!--   <select name="make.deviceType.id" id="make_deviceType_id" onchange="javascript: formVisibility(this.options[this.selectedIndex])" >
    					<option value="0">--Please Select Any Device Type--</option>
    					<option value="1">IP_CAMERA</option>
    					<option value="2">Z_WAVE_SWITCH</option>
    					<option value="3">Z_WAVE_DIMMER</option>
    					<option value="4">Z_WAVE_DOOR_LOCK</option>
    					<option value="5">Z_WAVE_DOOR_LOCK_NUM_PAD</option>
    					<option value="6">Z_WAVE_DOOR_SENSOR</option>
    					<option value="7">Z_WAVE_PIR_SENSOR</option>
    					<option value="8">Z_WAVE_MINIMOTE</option>
    					<option value="9">Z_WAVE_REPEATER</option>
    					<option value="10">Z_WAVE_MULTI_SENSOR</option>
    					<option value="11">Z_WAVE_SIREN</option>
    					<option value="12">Z_WAVE_AC_EXTENDER</option>
    					<option value="13">Z_WAVE_MOTOR_CONTROLLER</option>
		                <option value="20">Z_WAVE_SHOCK_DETECTOR </option>
                        <option value="21"> Z_WAVE_SMOKE_SENSOR</option>
                        <option value="23">Z_WAVE_LCD_REMOTE</option>
                        <option value="24">Z_WAVE_HEALTH_MONITOR</option>
		</select> -->
                
                <s:form action="savemake.action" method="post" cssClass="ajaxinlineform formalign" id="saveMakeForm">
                        <s:textfield label="Name" name="make.name" cssClass="requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
                        <s:textfield label="Number" name="make.number" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
                        <s:submit align="center" value="Save" onClick=""></s:submit>
                        <s:hidden name="make.deviceType.id" id="hiddenname1"></s:hidden>
                </s:form>

                <s:form action="savemake.action" method="post" cssClass="ajaxinlineform formalign" id="CurtainForm">
                        <s:textfield label="Name" name="make.name" cssClass="requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
                        <s:textfield label="Rotations Per Minute(RPM)" name="make.number" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
                        <s:textfield label="Linear Displacement(LD)" name="make.linearDisplacement" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
                        <s:textfield label="Buffer Time" name="make.bufferTime" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
                        <s:submit align="center" value="Save"></s:submit>
                        <s:hidden name="make.deviceType.id" id="hiddenname2"></s:hidden>
                </s:form>
<s:form action="saveSceneControllermake.action" method="post" cssClass="ajaxinlineform formalign" id="SceneController">
                        <s:textfield label="Model Number" name="sceneControllerMake.ModelName" cssClass=""></s:textfield>
                        <s:textfield label="Key Name" name="sceneControllerMake.keyName" cssClass=""></s:textfield>
                        <s:select label="Number of Keys" name="sceneControllerMake.noOfKeys"  list="{'1','2','3','4','5','6','7','8','9'}" />
                        <s:select label="Key PressType" name="sceneControllerMake.pressType"  list="{'Single Click','Double Click'}" />
                        <s:textfield label="KeyCode" name="sceneControllerMake.keyCode" cssClass=""></s:textfield>
                        <s:submit align="center" value="Save"></s:submit>
                        <s:hidden name="sceneControllerMake.id" id="hiddenname7"></s:hidden>
                </s:form>
				
				<s:form action="savemake.action" method="post" cssClass="ajaxinlineform formalign" id="AVChannelForm">
                        <s:textfield label="Name" name="make.name" cssClass="requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
                        <s:textfield label="Number" maxlength='4' name="make.number" minvalue="4" cssClass="required number editdisplayStar"></s:textfield>
                        <s:submit align="center" value="Save" onClick=""></s:submit>
                        <s:hidden name="make.deviceType.id" id="hiddenname4"></s:hidden>
                </s:form>

                <s:form action="savemake.action" method="post" cssClass="ajaxinlineform formalign" id="Helthcheckform">
                        <table>
                      <tr><td>&nbsp;</td></tr>  <tr>
                        <s:textfield label="Name" name="make.name" cssClass="requiredWithoutWhiteSpace editdisplayStar"></s:textfield></tr>
                       <tr><td><label>HMD_TYPES:</label></td><td> <select name="make.hmdTypes" Class="requiredselect editdisplayStar" onchange="javascript: RowVisibility(this.options[this.selectedIndex])" >                       
                         <option value="0">-- Please Select --</option>
                                   <option value="1">AC</option>
    					<option value="2">PUMP</option>
                        </select></td></tr>
                      <tr id="acon"><td><label>AC_ON_TIME:</label></td><td><input id="acOnTime" name="make.acOnTime" Class="makenumber requiredWithoutWhiteSpace editdisplayStar"></input></td><td>in sec</td></tr>                      
                       <tr> <s:textfield label="HMD_RATING" name="make.hmdRating" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield></tr>
                       <tr><td><label>SCALE:</label></td><td> <select name="make.Scale" Class="requiredselect editdisplayStar">
                        <option value="0">-- Please Select --</option>
                        <option value="1">W</option>
    					<option value="2">KWH</option>
    					<option value="3">AMP</option>
                        </select></td></tr><tr></tr><tr></tr>
<tr><td>&nbsp;</td></tr>
                       <tr><td><label>READING_LIMITS:</label></tr>
                       <tr><td><label>NRL:</label></td><td><input name="make.NRL" Class="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></input></td></tr>
                        <tr><td><label>NRH:</label></td><td><input name="make.NRH" Class="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></input></td></tr>
                          <tr><td><label>WRL:</label></td><td><input name="make.WRL" Class="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></input></td></tr>
                            <tr><td><label>WRH:</label></td><td><input name="make.WRH" Class="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></input></td></tr>
                          <tr><td><label>FRL:</label></td><td><input name="make.FRL" Class="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></input></td></tr>
                           <tr><td><label>FRH:</label></td><td><input name="make.FRH" Class="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></input></td></tr>
<tr><td>&nbsp;</td></tr>
                       <!-- </td><td> <select name="make.readinLimits">
                        <option value="0">-- Please Select --</option>
                                   <option value="1">NRL:1000</option>
    					<option value="2">NRH:1200</option>
    					<option value="3">WRL:500</option>
    					<option value="4">WRH:999</option>
    					<option value="5">FRL:0</option>
    					<option value="6">FRH:499</option>
                        </select></td></tr> -->

                      
                       <tr><td> <label>STABILIZATION_PERIOD:</label></td><td><input name="make.stabilizationPeriod" Class="makenumber requiredWithoutWhiteSpace editdisplayStar"></input></td><td>in sec</td></tr>
                       <tr><td> <label>TOTAL_TEST_RUN_TIME:</label></td><td><input name="make.totalTestRunTime" Class="makenumber requiredWithoutWhiteSpace editdisplayStar"></input></td><td>in sec</td></tr>
                       <tr><td> <label>INTERVAL_BWN_REPORTS:</label></td><td><input name="make.intervalBwnReports" Class="makenumber requiredWithoutWhiteSpace editdisplayStar"></input></td><td>in sec</td></tr>
                       

</table>
                  
                        <s:submit align="center" value="Save"></s:submit>
                        <s:hidden name="make.deviceType.id" id="hiddenname3"></s:hidden>
                </s:form>



        </div>
        <script>


$(document).ready(function() {
        $("#saveMakeForm").hide();
        $("#CurtainForm").hide();
        $("#Helthcheckform").hide();
        $("#acon").hide();
		$("#AVChannelForm").hide();
$("#SceneController").hide();
        var cForm = $("#saveMakeForm");
        Xpeditions.validateForm(cForm);

        var kForm=$("#CurtainForm");
        Xpeditions.validateForm(kForm);

        var hForm=$("#Helthcheckform");
        Xpeditions.validateForm(hForm);
		
		var AVForm=$("#AVChannelForm");
        Xpeditions.validateForm(AVForm);
 var SceneControllerForm=$("#SceneController");
        Xpeditions.validateForm(SceneControllerForm);
});
function RowVisibility(selVal)
        {

               if(selVal.label=="--Please Select--")        		{
        		$("#acon").hide();
        		}
        	else if(selVal.label=="AC") 
        		{
        		$("#acon").show();
                $("#acOnTime").addClass("requiredWithoutWhiteSpace");
        		}
              else
                     {
                     $("#acon").hide();
                     $("#acOnTime").removeClass("requiredWithoutWhiteSpace");
                     }
        }

	function formVisibility(selVal)
	{
		
	    document.getElementById("hiddenname1").value =	selVal.value;
	    document.getElementById("hiddenname2").value =	selVal.value;
           document.getElementById("hiddenname3").value =	selVal.value;
           document.getElementById("hiddenname4").value =	selVal.value;


             if(selVal.label=="--Please Select--")
	        {
	                $("#saveMakeForm").hide();
	        	$("#CurtainForm").hide();
                     $("#Helthcheckform").hide();

 $("#SceneController").hide();
	        }
              else if(selVal.label=="Z_WAVE_MOTOR_CONTROLLER")
		{ 
	                $("#CurtainForm").show();
			 $("#saveMakeForm").hide();
                       $("#Helthcheckform").hide();
                       $("#AVChannelForm").hide();
 $("#SceneController").hide();
	        }
	 else if(selVal.label=="Z_WAVE_HEALTH_MONITOR")
	        {
                       $("#Helthcheckform").show();
	        	$("#CurtainForm").hide();
	                $("#saveMakeForm").hide();
	                $("#AVChannelForm").hide();
$("#SceneController").hide();
		}else if(selVal.label=="Z_WAVE_AV_BLASTER")
	        {
                       $("#AVChannelForm").show();
	        	$("#CurtainForm").hide();
	                $("#saveMakeForm").hide();
 $("#SceneController").hide();
		}
             
	 else if(selVal.label=="DEV_ZWAVE_SCENE_CONTROLLER")
     {
                $("#AVChannelForm").hide();
     	$("#CurtainForm").hide();
             $("#saveMakeForm").hide();
            
             $("#SceneController").show();
	}
	     else
	        {
	        	
	                $("#saveMakeForm").show();
	                $("#CurtainForm").hide();
                       $("#Helthcheckform").hide();
                       $("#AVChannelForm").hide();
$("#SceneController").hide();
	        }
	}

</script>
