<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
Editing the User :: <s:property value="user.userId"/>
<s:form action="editAdminUser.action" method="post" id="usereditform" cssClass="ajaxinlinepopupform">
	<s:password name="user.password" label="New Password" cssClass="required passconfirm crosscheck editdisplayStar" commonclass="passconfirm"></s:password>
	<s:password label="Confirm Password" cssClass="required passconfirm crosscheck editdisplayStar" commonclass="passconfirm"></s:password>
	<s:hidden name="user.id"></s:hidden>
	<s:textfield name="user.email" label="Email ID" cssClass="required email editdisplayStar"></s:textfield>
	<s:textfield name="user.mobile" label="Mobile Number" cssClass="required mobilenumber editdisplayStar"></s:textfield>
	<s:submit value="Save" ></s:submit>
</s:form>
<script>
	$(document).ready(function() {
		var cForm = $("#usereditform");
		Xpeditions.validateForm(cForm);
	});
</script>