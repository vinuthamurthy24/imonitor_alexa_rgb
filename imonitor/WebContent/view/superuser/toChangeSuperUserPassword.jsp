<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>


<%ActionContext.getContext().getSession().remove("message"); %>
<s:form action="saveNewPassword.action" method="post" id="Superusersaveform" cssClass="ajaxinlinepopupform">
<s:password name="superuser.oldPassword" label="Old Password" cssClass="required editdisplayStar" ></s:password>
<s:password name="superuser.password" label="New Password" cssClass="required crosscheck confirmpass editdisplayStar" commonclass="confirmpass"></s:password>
<s:password label="Confirm Password" cssClass="required crosscheck confirmpass editdisplayStar" commonclass="confirmpass"></s:password>
<s:submit value="Save" ></s:submit>
</s:form>
<!-- <div id="customerdetails"> </div> -->
	<script>
		$(document).ready(function() {
			var cForm = $("#Superusersaveform");
			Xpeditions.validateForm(cForm);
		});
	</script>
	
	<span style="display:none;">
<div style="color: red;" id="messageSection">
	<s:property value="#session.message" />
</div></span>
<%ActionContext.getContext().getSession().remove("message"); %>
	
