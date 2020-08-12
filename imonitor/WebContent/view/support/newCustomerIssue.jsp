<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>


<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>


<div style="color: red;"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<script type="text/javascript">
    window.history.forward();
    function noBack() { window.history.forward();
 }
    var data = [];
    $(document).ready(function() {
    	
    	
    
    	var scenarioDetailsMap = {
  				<s:iterator value="customers" var="customer" status="scenarioStatus">
  					"<s:property value="#customer.id" />":{
						"scenarioId":"<s:property value="#customer.id" />",
  						"scenarioName":"<s:property value="#customer.customerId" />"
  						
  					 }<s:if test="#scenarioStatus.last != true">,</s:if>
  				</s:iterator>
  		}
		
  		for(var scenarioId in scenarioDetailsMap){
			var scenario = scenarioDetailsMap[scenarioId];
			var scenarioId = scenario["scenarioId"];
			var scenarioName = scenario["scenarioName"];
			//alert(scenarioName);
			data.push(scenarioName);
  		}
    	 
    
    	var cForm = $("#saveCustomerReportForm");
		Xpeditions.validateForm(cForm);
    	
    	$("#reportingdate").datepicker({showOn: 'button', buttonImage: '/imonitor/resources/images/calendar.gif', buttonImageOnly: true,dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'});
    	$("#resolutiondate").datepicker({showOn: 'button', buttonImage: '/imonitor/resources/images/calendar.gif', buttonImageOnly: true,dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'});
    	$("#actiondate").datepicker({showOn: 'button', buttonImage: '/imonitor/resources/images/calendar.gif', buttonImageOnly: true,dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'});
    	$("#finaldate").datepicker({showOn: 'button', buttonImage: '/imonitor/resources/images/calendar.gif', buttonImageOnly: true,dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'});
   
    	
    	$("#allocation").hide();
    	$("#slot1").hide();
    	$("#slot2").hide();
    	$("#slot3").hide();
    	$("#condition").hide();
    	$("#others").hide();
    	$("#otherCause").hide();
    	
    });
    
    function customerselect(){
		
		var customerid = $("#customerSelect").val();
		$("#selectedCustomer").val(customerid);
	}

$(".Auto").live('click', function(){
		
		if($(this).attr('checked')){
			
			$("#allocation").show();
			$("#slot1").show();
	    	$("#slot2").show();
	    	$("#slot3").show();
	    	$("#allocationslot1").addClass("required");
			$("#allocationslot2").addClass("required");
		}else{
			
			$("#slot1").hide();
	    	$("#slot2").hide();
	    	$("#slot3").hide();
	    	$("#allocationslot1").removeClass("required");
			$("#allocationslot2").removeClass("required");
		}
		
});

$("#customerField").autocomplete({
    source: data
});

function handleselectstatus(){
	
	var name = $("#finalstatus option:selected").text();
	
	if(name != "Resolved"){
		
		$("#condition").show();
		
		
	}else{
		
		$("#condition").hide();
		/* $("#allocationslot1").removeClass("required");
		$("#allocationslot2").removeClass("required"); */
		
	}
	
}

function handlerootcause(){
	
	var name = $("#rootcause option:selected").text();
	
	if(name == "Other"){
		
		
		$("#others").show();
		$("#otherCause").show();
		var pollHtml = $("#otherCause");
		pollHtml.addClass('required');
	}else{
		
		$("#others").hide();
		$("#otherCause").hide();
		$("#otherCause").removeClass("required");
	}
	
}
    
</script>
<style>
#contentsection{
float: left;
width: 1184px;
height: 519px;
margin-left: 10px;
background-color: #F4F5E9;
padding-left: 20px;
padding-top: 20px;
overflow: auto;
}

#customerDetails{
    width: 631px;
	height: 260px;
	margin: -278px 0px 0px 475px;
	background-color:white;
	border-radius: 13px;
	-webkit-box-shadow: 0px 0px 12px rgba(0, 0, 0, 5.4); 
	-moz-box-shadow: 0px 1px 6px rgba(23, 69, 88, .5);
	-webkit-border-radius: 12px;
	-moz-border-radius: 7px; 
	border-radius: 7px;
	position:fixed;
}

/* .saveReport
{
padding:4px 10px;
letter-spacing:-0.03em;
text-align:center;
background:#3a8fce url(../images/bkg-btn-blue.gif) repeat-x 0 top;
color:#fff;
text-shadow:0 -1px 2px #2063AB;
box-shadow: 2px 2px 2px rgba(0,0,0,.5);
border:1px solid #2f6ea7;border-color:#508fcd #4483bf #2f6ea7 #3f7eb9;
border-radius:4px;
text-decoration:none;
cursor: pointer;
}  */

.ui-menu {
	list-style:none;
	padding: 2px;
	margin: 0;
	display:block;
	float: left;
}
.ui-menu .ui-menu {
	margin-top: -3px;
}
.ui-menu .ui-menu-item {
	margin:0;
	padding: 0;
	zoom: 1;
	float: left;
	clear: left;
	width: 100%;
}
.ui-menu .ui-menu-item a {
	text-decoration:none;
	display:block;
	padding:.2em .4em;
	line-height:1.5;
	zoom:1;
}
.ui-menu .ui-menu-item a.ui-state-hover,
.ui-menu .ui-menu-item a.ui-state-active {
	font-weight: normal;
	margin: -1px;
}

</style>


<s:form action="saveCustomerReport" method="post" id="saveCustomerReportForm" cssClass="ajaxinlineform">
 <a  class="back" href="redirectToCustomerList.action"style="text-shadow: white 0.1em 0.1em 0.2em">
		<img src="/imonitor/resources/images/back2.png" style="width: 20px; height: 20px;float: right;margin: 0px 11px 0px 0px;cursor: pointer;"></a>
 <%-- <s:hidden id="selectedCustomer" name="selectedCustomer"></s:hidden> --%>
 
  <s:textfield label="Enter Customer" name="selectedCustomer" id="customerField"></s:textfield> 
<%-- <s:select id='customerSelect' label="Customer" name="customerReport.Customer.id" list="customers" listKey="id" listValue="customerId" headerKey="-1" headerValue="Select Customer from list" onchange='customerselect()'></s:select> --%>
 
<s:textfield name="customerReport.reportPerson" label="Reported by" cssClass="required"></s:textfield> 
<s:textfield id="reportperson" name="customerReport.reportedDate" label="Reported date" cssClass="mydatepicker required" id="reportingdate" readonly='true'></s:textfield>
<s:select name="customerReport.severity.id" label="Severity" list="severity" listKey="id" listValue="severityLevel"></s:select>
<s:textarea name="customerReport.issueDescription" label="Issue Description" id="Details" cssClass="Details required" rows="7" cols="38"></s:textarea>

<s:textarea name="customerReport.resolution" label="Resolution Provided" id="resolutionDetails" cssClass="resolutionDetails" rows="4" cols="38"></s:textarea>
<s:textfield name="customerReport.resolutionDate" label="Resolution date" cssClass="mydatepicker" id="resolutiondate" readonly='true'></s:textfield>
<s:select name="customerReport.finalState.id" id="finalstatus" label="Final status" list="issueStatus" listKey="id" listValue="name" cssClass="alphanumeric" onchange='handleselectstatus()'></s:select>
<s:select name="customerReport.rootCause.id" id="rootcause" label="Root cause" list="rootCauseList" listKey="id" listValue="cause" onchange='handlerootcause()'></s:select>

<table id="others" style="margin:0px 0px 0px -14px;">

<tr><!-- <td><label style="padding-right: 30px;">Specify the root:</label></td> --><td><s:textfield name="customerReport.otherCause" label="Specify the root cause" id="otherCause"></s:textfield></td></tr>

</table>

<table id="condition">
<tr>
<td>Allocated<input type="checkbox" class="Auto" value="Auto Search" /></td><td></td><td>(Check if this issue is allocated to concerned person)</td>
</tr>
</table>

<div id="allocation">

<div id="slot1">
<label style="padding-right: 29px;">Allocated Person:</label><s:textfield name="customerReport.allocatedPerson" cssClass="" id="allocationslot1"></s:textfield>
</div>
 <div id="slot2" style="margin: 8px 0px 0px 1px;">
<label style="padding-right: 64px;">Next Action:</label><s:textarea name="customerReport.action" label="" cssClass="actionDetails" id="actionDetails" rows="4" columns="38"></s:textarea>
</div>


<div id="slot3" style="margin: 10px 0px 0px 0px;">
<label style="padding-right: 64px;">Action Date:</label><s:textfield name="customerReport.actionDate" cssClass="mydatepicker" id="actiondate" readonly='true'></s:textfield>
</div>
</div>

<s:submit value="Save"  style="margin: 35px 758px 15px 23px;"></s:submit>
</s:form>
<!-- <div id="customerDetails">

</div> -->