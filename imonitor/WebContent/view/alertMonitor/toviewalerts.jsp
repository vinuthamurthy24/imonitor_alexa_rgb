<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>

<h4>Edit AlertMonitor</h4>
<s:form action="updatecustomeralerts.action" method="post"  id="usersaveform" cssClass="ajaxinlinepopupform">
<s:hidden name="alertMonitor.id"></s:hidden>

<s:textfield name="alertMonitor.alertTime" label="AlertTime"></s:textfield>
<s:textfield name="alertMonitor.customer.customerId" label="Customer"></s:textfield>
<s:textfield name="alertMonitor.attended" label="AttendedBy" cssClass="required alphanumeric editdisplayStar"></s:textfield>
<s:textfield name="alertMonitor.contactnumber" label="ContactNumber" cssClass="required mobilenumber editdisplayStar"></s:textfield>
<s:select name="alertMonitor.alertStatus.id" label="Status" headerKey="-1" headerValue="Select Status" list="#{'1':'Open','2': 'Assigned','3': 'Attended'}" cssClass="required editdisplayStar"></s:select>
<s:submit value="Save"></s:submit>
</s:form>

<script>
$(document).ready(function() {
	var cForm = $("#usersaveform");
	Xpeditions.validateForm(cForm);		
});


</script>