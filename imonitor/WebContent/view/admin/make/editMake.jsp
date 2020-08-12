<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
	
<%@page import="com.opensymphony.xwork2.ActionContext"%><div align="center" class="make">
		<div style="color: blue;">
		<s:property value="#session.message"/>
		</div>
		<%ActionContext.getContext().getSession().remove("message");%>
		<s:form action="updatemake.action" method="post" cssClass="ajaxinlineform formalign" id="editmakeForm" >
			<s:hidden name="make.id"></s:hidden>
			<s:textfield label="Name" name="make.name" cssClass="requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
			<s:textarea label="Details" id="Details" cssClass="Details" name="make.details" rows="4" cols="15"></s:textarea>
			<s:submit align="center"value="Save"></s:submit>
			<s:hidden name="make.deviceType.id" id="hiddenname"></s:hidden>
		</s:form>

		<s:form action="updatemake.action" method="post" cssClass="ajaxinlineform formalign" id="CurtainForm">
						<s:hidden name="make.id"></s:hidden>
                        <s:textfield label="Name" name="make.name" cssClass="requiredWithoutWhiteSpace editdisplayStar editdisplayStar"></s:textfield>
                        <s:textfield label="Rotations Per Minute(RPM)" name="make.number" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
                        <s:textfield label="Linear Displacement(LD)" name="make.linearDisplacement" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
                        <s:textfield label="Buffer Time" name="make.bufferTime" cssClass="makenumber required editdisplayStar"></s:textfield>
                        <s:submit align="center" value="Save"></s:submit>
                        <s:hidden name="make.deviceType.id" id="hiddenname"></s:hidden>
                </s:form>

<s:form action="updatemake.action" method="post" cssClass="ajaxinlineform formalign" id="Helthcheckform">
                        <table>
                        <tr>
                        <s:textfield label="Name" name="make.name" cssClass="requiredWithoutWhiteSpace editdisplayStar"></s:textfield></tr>
                       <tr> <s:textfield label="Number" name="make.number" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield></tr>
                       <tr><td>
<s:select label="HMD_TYPES" name="make.hmdTypes" id="hiddenname1" list="#{'0':'-- Please Select --','1':'AC','2':'PUMP'}" Class="requiredselect editdisplayStar" onchange="javascript: RowVisibility(this.options[this.selectedIndex])"></s:select>
</td></tr>
 <tr id="acon"><s:textfield label="AC_ON_TIME(in sec)" id="acOnTime" name="make.acOnTime" Class="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield></tr>                      
                     <!--     <tr id="acon"><td><label>AC_ON_TIME:</label></td><td><input name="make.acOnTime" Class="makenumber requiredWithoutWhiteSpace editdisplayStar"></input></td><td>in sec</td></tr>   -->                   
 <tr> <s:textfield label="HMD_RATING" name="make.hmdRating" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield></tr>
                       <tr><td>
<s:select label="SCALE" name="make.Scale" list="#{'0':'-- Please Select --','1':'W','2':'KWH','3':'AMP'}" Class="requiredselect editdisplayStar"></s:select>
</td></tr>
 <tr><td><label><b>READING_LIMITS:</b></label>
                       <s:textfield label="NRL" name="make.NRL" cssClass="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></s:textfield>
                       <s:textfield label="NRH" name="make.NRH" cssClass="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></s:textfield>
                       <s:textfield label="WRL" name="make.WRL" cssClass="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></s:textfield>
                       <s:textfield label="WRH" name="make.WRH" cssClass="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></s:textfield>
                       <s:textfield label="FRL" name="make.FRL" cssClass="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></s:textfield>
                       <s:textfield label="FRH" name="make.FRH" cssClass="number requiredWithoutWhiteSpace editdisplayStar" minvalue="0" maxvalue="3500"></s:textfield>
                      

<s:textfield label="STABILIZATION_PERIOD" name="make.stabilizationPeriod" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
<s:textfield label="TOTAL_TEST_RUN_TIME" name="make.totalTestRunTime" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield>
<s:textfield label="INTERVAL_BWN_REPORTS" name="make.intervalBwnReports" cssClass="makenumber requiredWithoutWhiteSpace editdisplayStar"></s:textfield>




                     <!-- <tr><td><label>READING_LIMITS:</label></td><td><label>NRL:</label><input name="make.NRL" Class="makenumber requiredWithoutWhiteSpace editdisplayStar"></input></td></tr>
                        <tr><td></td><td><label>NRH:</label><input name="make.NRH" Class="makenumber required"></input></td></tr>
                          <tr><td></td><td><label>WRL:</label><input name="make.WRL" Class="makenumber required"></input></td></tr>
                            <tr><td></td><td><label>WRH:</label><input name="make.WRH" Class="makenumber required"></input></td></tr>
                          <tr><td></td><td><label>FRL:</label><input name="make.FRL" Class="makenumber required"></input></td></tr>
                           <tr><td></td><td><label>FRH:</label><input name="make.FRH" Class="makenumber required"></input></td></tr> -->

                       

<!--  	 <s:select label="READING_LIMITS" name="make.readinLimits" list="#{'0':'-- Please Select --','1':'NRL:1000','2':'NRH:1200','3':'WRL:500','4':'WRH:999','5':'FRL:0','6':'FRH:499'}" ></s:select>

</td></tr> -->

                      
                    <!--  <tr><td> <label>STABILIZATION_PERIOD:</label></td><td><input name="make.stabilizationPeriod" cssClass="makenumber required"></input></td><td>in sec</td></tr>
                       <tr><td> <label>TOTAL_TEST_RUN_TIME:</label></td><td><input name="make.totalTestRunTime" cssClass="makenumber required"></input></td><td>in sec</td></tr>
                       <tr><td> <label>INTERVAL_BWN_REPORTS:</label></td><td><input name="make.intervalBwnReports" cssClass="makenumber required"></input></td><td>in sec</td></tr> -->
</table>
                  
                        <s:submit align="center" value="Save"></s:submit>
                        <s:hidden name="make.deviceType.id" id="hiddenname"></s:hidden>
						 <s:hidden name="make.deviceType.name" id="devicetypename"></s:hidden>
				<s:hidden name="make.id"></s:hidden>
                </s:form>

	</div>
	<script>
function RowVisibility(selVal)
        {
               if(selVal.label=="--Please Select--")        		{
        		  document.getElementById('acon').style.visibility = 'hidden';
				   $("#acOnTime").removeClass("requiredWithoutWhiteSpace");
        		}
        	else if(selVal.label=="AC") 
        		{
        		  document.getElementById('acon').style.visibility = 'visible';
				   $("#acOnTime").addClass("requiredWithoutWhiteSpace");
        		}
              else
                     {
                      document.getElementById('acon').style.visibility = 'hidden';
					   $("#acOnTime").removeClass("requiredWithoutWhiteSpace");
                     }
        }

$(document).ready(function() {
	 $("#CurtainForm").hide();
     $("#editmakeForm").hide();
       $("Helthcheckform").hide(); 
        var cForm = $("#saveMakeForm");
        Xpeditions.validateForm(cForm);
	var val=document.getElementById("devicetypename").value;
       var val1=document.getElementById("hiddenname1").value; 
         
       if(val1==2 || val1==0)
             {
    	   document.getElementById('acon').style.visibility = 'hidden';
             }
			   //alert("value=="+val);
	if(val=='Z_WAVE_MOTOR_CONTROLLER')
	{
                $("#Helthcheckform").hide();
                $("#CurtainForm").show();
                $("#editmakeForm").hide();
	}
	else if(val=='Z_WAVE_HEALTH_MONITOR')
		{
		$("#Helthcheckform").show();
		$("#CurtainForm").hide();
        $("#editmakeForm").hide();
        }
	else
	{
                $("#CurtainForm").hide();
                $("#Helthcheckform").hide();
                $("#editmakeForm").show();

	}

});
</script>
