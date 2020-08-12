<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>


<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: green;"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>

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



</style>


<s:form action="updateCustomerReport.action" method="POST" id="updateCustomerReportForm" cssClass="editlinkReport">
 
 <s:hidden name="customerReport.id"></s:hidden>
 <s:hidden name="customerReport.customerId.id"></s:hidden>
<s:textfield name="customerId" label="Customer" readonly="true" size="30"></s:textfield>
<s:textfield name="customerReport.reportPerson" label="Reported by" cssClass="required"></s:textfield>
<s:date name="customerReport.reportedDate" format="%{dateFormat}" var="reportedDate"/> 
<s:textfield id="reportperson" name="customerReport.reportedDate" value="%{reportedDate}" label="Reported date" cssClass="" id="reportingdate" readonly='true'></s:textfield>
<s:select name="customerReport.severity.id" label="Severity" list="severity" listKey="id" listValue="severityLevel"></s:select>
<s:textarea name="customerReport.issueDescription" label="Issue Description" id="Details" cssClass="Details" rows="6" cols="38"></s:textarea>
<s:textarea name="customerReport.resolution" label="Resolution Provided" id="resolutionDetails" cssClass="resolutionDetails" rows="4" cols="38"></s:textarea>
<s:date name="customerReport.resolutionDate" format="%{dateFormat}" var="resolutionDate"/>
<s:textfield name="customerReport.resolutionDate" label="Resolution date" value="%{resolutionDate}" cssClass="datetime" id="resolutiondate" readonly='true'></s:textfield>
<s:select name="customerReport.finalState.id" label="Final status" list="issueStatus" listKey="id" listValue="name" cssClass="alphanumeric"></s:select>
<s:select name="customerReport.rootCause.id" id="rootcause" label="Root cause" list="rootCauseList" listKey="id" listValue="cause" ></s:select>
<table id="others">

<tr><td><s:textfield name="customerReport.otherCause" label="Specify the root cause" id="otherCause"></s:textfield></td></tr>

</table>


<!-- <table>
<tr>
<td>Allocated<input type="checkbox" class="Auto" value="Auto Search" /></td><td></td><td>(Check if this issue is allocated to concerned person)</td>
</tr>
</table> -->

<div id="allocation">

<div id="slot1">
<label style="padding-right: 29px;">Allocated Person:</label><s:textfield name="customerReport.allocatedPerson" cssClass="alphanumeric" id="allocationslot1"></s:textfield>
</div>
 <div id="slot2" style="margin: 8px 0px 0px 1px;">
 
<label style="padding-right: 64px;">Next Action:</label><s:textarea name="customerReport.action"cssClass="alphanumeric" id="allocationslot2" rows="4" columns="38"></s:textarea>
</div>


<div id="slot3" style="margin: 10px 0px 0px 0px;">
<s:date name="customerReport.actionDate" format="%{dateFormat}" var="actionDate"/>
<label style="padding-right: 64px;">Action Date:</label><s:textfield name="customerReport.actionDate" value="%{actionDate}" cssClass="datetime" id="actiondate"  readonly='true'></s:textfield>
</div>
</div> 





<%-- <s:textfield name="customerReport.finalDate" label="Final date" cssClass="mydatepicker" id="finaldate"></s:textfield> --%>

<s:submit value="Save"></s:submit> 
 
</s:form>
<script type="text/javascript">
   
    
    $(document).ready(function() {
    	
    	var cForm = $("#updateCustomerReportForm");
		Xpeditions.validateForm(cForm);
    	
		$("#actiondate").datepicker({
			showOn: 'button', 
			buttonImage: '/imonitor/resources/images/calendar.gif', 
			buttonImageOnly: true,
			beforeShow: function(input, inst) {
				$(".ui-datepicker").css('z-index', 5000);
			},
			onClose: function(dateText, inst) { 
				$(".ui-datepicker").css('z-index', 1);
			},
			dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'
			});
		
		$("#resolutiondate").datepicker({
			showOn: 'button', 
			buttonImage: '/imonitor/resources/images/calendar.gif', 
			buttonImageOnly: true,
			beforeShow: function(input, inst) {
				$(".ui-datepicker").css('z-index', 5000);
			},
			onClose: function(dateText, inst) { 
				$(".ui-datepicker").css('z-index', 1);
			},
			dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'
			});
		
		
		
    	
    	
    	
   
    	
    	/* $("#allocation").hide();
    	$("#slot1").hide();
    	$("#slot2").hide();
    	$("#slot3").hide(); */
    	
    	
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
		}else{
			
			$("#slot1").hide();
	    	$("#slot2").hide();
	    	$("#slot3").hide();
		}
		
});
    
</script>