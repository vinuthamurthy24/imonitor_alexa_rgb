<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<form action="saveAlert" method="post">
	<s:checkboxlist name="alert" list="responses"></s:checkboxlist>
	<input type="hidden" name="alertType" value="<s:property value="alertType"/>"></input>
<s:submit key="general.save"></s:submit>
</form>