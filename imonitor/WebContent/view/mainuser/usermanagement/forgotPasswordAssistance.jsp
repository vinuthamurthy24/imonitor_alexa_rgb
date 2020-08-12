<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: red;" id="ruleMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<div>
	<h4 align="center" style="font-style:italic;"><s:text name="setup.user.forget.pass.tile" /> </h4>
</div>
<s:form id="resetPasswordForm" action="resetPassword.action" method="GET" cssClass="resetPassword">
	<s:if test="hintQuestionFromDB != null && hintAnswerFromDB != null">
		<table>
			<s:hidden name="user.id" id="uId"></s:hidden>
			<s:hidden name="user.customer.customerId" id="custId"></s:hidden>
			<s:hidden name="user.customer.id" id="custId"></s:hidden>
			<tr><td><p style="font-style:italic; color:red;"><s:text name="setup.user.forget.pass.step1" /></p></td></tr>
			<tr><td><s:textfield  style="font-style:italic;" key="login.username" name="user.userId" id="userId"></s:textfield></td></tr>
			<tr><td><s:textfield style="font-style:italic" key="general.email" name="email" id="mailId" ></s:textfield></td></tr>
			<tr><td><s:textfield style="font-style:italic" key="general.mobile" name="mobile" id="mobileId" ></s:textfield></td></tr>
			<tr><td></td><td></td></tr>
	
			<tr><td style="font-style:italic; color:red;"><s:text name="setup.user.forget.pass.step2" /></td></tr>
			<tr><td><s:textfield readonly="true" key="setup.user.hint.q" name="hintQuestionFromDB" id="hintQuestionFromDB" size="35"></s:textfield>
			<tr><td><s:password id="hintAnswer" key="setup.user.hint.a" name="hintAnswer" label="Hint Answer"></s:password></td></tr>  <!-- parishod added name= "hintAnswer" -->
		</table>
	</s:if>
	<s:else>
		<table>
			<s:hidden name="user.id" id="uId"></s:hidden>
			<s:hidden name="user.customer.id" id="custId"></s:hidden>
			<s:hidden name="user.customer.customerId" id="custId"></s:hidden>
			<s:hidden name="hintAnswerFromDB" value="NOT_CONFIGURED"></s:hidden>
			<tr><p style="font-style:italic; color:red;"><s:text name="setup.user.forget.pass.step2" /></p></tr>
			<tr><td><s:textfield  style="font-style:italic;" key="login.username" name="user.userId" id="userId"></s:textfield></td></tr>
			<tr><td><s:textfield style="font-style:italic" key="general.email" name="email" id="mailId" ></s:textfield></td></tr>
			<tr><td><s:textfield style="font-style:italic" key="general.mobile" name="mobile" id="mobileNum" ></s:textfield></td></tr>
	</s:else>
	
	<tr><td style=" float: right;"><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel"/><s:submit theme="simple" key="general.save"></s:submit></td></tr>
</table>
	</s:form>

<script>
	$(document).ready(function() {
		var cForm = $("#resetPasswordForm");
		Xpeditions.validateForm(cForm);
	});

	function DestoryDialog()
        {
                   $("#editmodal").dialog('destroy'); 
        }	
	
	
</script>