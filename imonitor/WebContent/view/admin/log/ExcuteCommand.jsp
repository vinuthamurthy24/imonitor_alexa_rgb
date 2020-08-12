<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib prefix="s" uri="/struts-tags"%>                
	<s:form  action="ExecuteCommand.action" id="ExecuteCommand" method="post" cssClass="ajaxinlineformExecuteCommnd formalign">
			<s:textfield name="Command" required="true" cssClass="required"  label="Type in command" ></s:textfield><s:textfield name="timeout"  required="true" cssClass="required number" size="4" label="TimeOut" ></s:textfield>
			<s:hidden name="gateWay.id"></s:hidden>
			<s:hidden name="gateWay.macId"></s:hidden>
            <s:submit align="right" value="Execute" ></s:submit>
		<s:textarea  readonly="true" cssStyle="font-family: Courier New;" name="result" id="commandresult" label="Execution result" rows="30" cols="70" ></s:textarea>
	</s:form>
			
<script>
	$(document).ready(function() {
		var cForm = $("#ExecuteCommand");
		Xpeditions.validateForm(cForm);
	});
	</script>