<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form action="/superuser/saveSuperUser.action" method="post" id="Superusersaveform" cssClass="ajaxinlineform">
<s:textfield name="superuser.superUserId" label="SuperUserName" cssClass="required alphanumeric editdisplayStar"></s:textfield>
<s:password name="superuser.password" label="Password" cssClass="required crosscheck confirmpass editdisplayStar" commonclass="confirmpass"></s:password>
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