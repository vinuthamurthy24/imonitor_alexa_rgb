<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<!--<s:property value="#session.user.userId"/><br>Enter your new password-->
<!-- <div class="pageTitle"> -->
<!-- 	<a class='' href="#" style="border: none;text-decoration: none;"><img src="/imonitor/resources/images/useradd.png"></a> -->
<%-- 	<span class='titlespan'>&nbsp;Change Password</span> --%>
<!-- 	<div class="pagetitleline"></div> -->
</div>
<br/>

<div style="color: red;"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<!-- <s:div style="color: red;">Password must contain atleast one alphabet(lowercase), one alphabet(uppercase), one special character and one digit.</s:div> -->
<s:div><br><br></s:div>
<s:form action="changePassword.action" method="post" id="changePasswordForm" name="changePasswordForm" cssClass="ajaxinlinepopupform">
<s:password name="oldPassword" key="setup.user.password.old" cssClass="required"></s:password>
<s:password name="password" key="setup.user.password.new" cssClass="required crosscheck confirmpass" commonclass="confirmpass" ></s:password>
<s:password name="confirmPassword" key="setup.user.password.confirm" cssClass="required crosscheck confirmpass" commonclass="confirmpass"></s:password>
<s:submit key="general.save" id="saveInfo"/>
</s:form>
<script>
	$(document).ready(function() {
		var cForm = $("#changePasswordForm");
		Xpeditions.validateForm(cForm);
		var inputs = cForm.find('input');
		inputs.each(function(index, inp)
		{
		var input = $(inp);
		if(input.hasClass('required'))
		{
			input.closest("tr").find("td:first-child").before("<td><span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: right;margin: 0px -4px 7px -31px;'>*</span></td>");

		}
		});

	});

	$("#saveInfo").live("click", function(){
		var oldPass = document.forms["changePasswordForm"]["oldPassword"].value;
		var newPass = document.forms["changePasswordForm"]["password"].value;
		if(oldPass == newPass){
			showResultAlert("<s:text name='setup.user.msg.0001'/>");
			return false;
		}
	});

</script>