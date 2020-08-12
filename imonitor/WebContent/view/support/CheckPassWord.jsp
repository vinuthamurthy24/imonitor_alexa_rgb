<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib prefix="s" uri="/struts-tags"%>                
	<s:form  action="deleteCustomerReport.action" method="post" cssClass="editlinkDelete" id="CheckPass">
			<s:password name="PassWord"  cssClass="required" label="Password" size="20" maxlength="15"></s:password>
			<s:hidden name="selectedReport"></s:hidden>
            <s:submit align="right" value="Submit" ></s:submit>
	</s:form>
	
	<script>
	$(document).ready(function() {
		var cForm = $("#CheckPass");
		Xpeditions.validateForm(cForm);
	});
	</script>