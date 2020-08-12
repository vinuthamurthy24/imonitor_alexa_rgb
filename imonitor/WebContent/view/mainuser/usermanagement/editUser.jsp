<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>

	
<s:form action="editUser.action" method="post" id="usereditform" cssClass="ajaxinlinepopupform">
	<s:password name="user.password" key="setup.user.password.new" cssClass="passconfirm crosscheck" commonclass="passconfirm"></s:password>
	<s:password key="setup.user.password.confirm" cssClass="passconfirm crosscheck" commonclass="passconfirm"></s:password>
	<s:hidden name="user.id"></s:hidden>
	<s:hidden name="user.userId"></s:hidden>
	<s:textfield name="user.email" key="general.email" cssClass="required email"></s:textfield>
	<s:textfield name="user.mobile" key="general.mobile" cssClass="required mobilenumber"></s:textfield>
	<!-- <s:div style="color: red;">Password must contain atleast one alphabet(lowercase), one alphabet(uppercase), one special character and one digit.</s:div> -->
	<s:div><br><br></s:div>
	<s:div><s:text name='setup.user.editing' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:: <s:property value="user.userId"/></s:div>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr><td></td><td></td><td></td><td></td><td></td><td></td><td style=" float: right; "><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel"/></td><td><s:submit theme="simple" key="general.save"></s:submit></td></tr>
</s:form>
<script>
	$(document).ready(function() {
		var cForm = $("#usereditform");
		Xpeditions.validateForm(cForm);
	var inputs = cForm.find('input');
		inputs.each(function(index, inp)
		{
		var input = $(inp);
		if(input.hasClass('required'))
		{
			input.closest("tr").find("td:first-child").append("<td><span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: right;margin: -24px 0px 7px -31px'>*</span></td>");

		}
		});
	
		});
		function DestoryDialog()
        {
                   $("#editmodal").dialog('destroy'); 
        }
</script>