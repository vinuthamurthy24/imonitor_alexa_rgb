<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib prefix="s" uri="/struts-tags"%>                
	<s:form  action="toExcuteCommand.action" method="post" cssClass="editlinkCmd" id="CheckPass">
			<s:password name="PassWord"  cssClass="required" label="PassWord" size="20" maxlength="15"></s:password>
			<s:hidden name="gateWay.id"></s:hidden>
			<s:hidden name="gateWay.macId"></s:hidden>
            <s:submit align="right" value="Submit" ></s:submit>
	</s:form>
	
	<script>
	$(document).ready(function() {
		var cForm = $("#CheckPass");
		Xpeditions.validateForm(cForm);
	});
	</script>