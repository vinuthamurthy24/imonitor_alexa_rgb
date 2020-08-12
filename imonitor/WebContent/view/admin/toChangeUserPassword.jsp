<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<%@page import="com.opensymphony.xwork2.ActionContext"%>
<!--<s:property value="#session.user.userId"/><br>Enter your new password-->
<div class="pageTitle">
	<a class='' href="#" style="border: none;text-decoration: none;"><img src="/imonitor/resources/images/useradd.png"></a>
	<span class='titlespan'>&nbsp;Change Password</span>
	<div class="pagetitleline"></div>
</div>
<br/>
<br/>
<div style="color: red;"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<s:form action="changePassword.action" method="post" id="changePasswordForm" cssClass="ajaxinlineform">
<s:password name="oldPassword" label="Old Password" cssClass="required editdisplayStar"></s:password>
<s:password name="password" label="New Password" cssClass="required crosscheck confirmpass editdisplayStar" commonclass="confirmpass" ></s:password>
<s:password name="confirmPassword" label="Confirm Password" cssClass="required crosscheck confirmpass editdisplayStar" commonclass="confirmpass"></s:password>
<s:submit value="Save"/>
</s:form>
<script>
	$(document).ready(function() {
		var cForm = $("#changePasswordForm");
		Xpeditions.validateForm(cForm);
	});
</script>