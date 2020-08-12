<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%><div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form action="addUser.action" method="post" id="usersaveform" cssClass="ajaxinlinepopupform">
	<s:textfield name="user.userId" key="setup.user.id" cssClass="required alphanumeric"></s:textfield>
	<s:password name="user.password" key="setup.user.password" cssClass="required passconfirm crosscheck" commonclass="passconfirm"></s:password>
	<s:password key="setup.user.password.confirm" cssClass="required passconfirm crosscheck" commonclass="passconfirm"></s:password>
	<s:textfield name="user.email" key="general.email" cssClass="required email"></s:textfield>
	<s:textfield name="user.mobile" key="general.mobile" cssClass="required mobilenumber"></s:textfield>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<!-- <s:div style="color: red;">Password must contain atleast one alphabet(lowercase), one alphabet(uppercase), one special character and one digit.</s:div> -->
	<tr><td></td><td></td><td><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel"/><s:submit theme="simple" key="general.save"></s:submit></td></tr>
</s:form>
	<script>
		$(document).ready(function() {
			var cForm = $("#usersaveform");
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
		function DestoryDialog()
        {
                   $("#editmodal").dialog('destroy'); 
        }
	</script>			