<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>


<div style="color: blue"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form action="updateAVBlasterCodeInfo.action" method="post" id="AVBlasterCodeInfoForm" cssClass="ajaxinlineform">
<%-- 	<s:select name="avBlaster.category"  label="Category" list="#{'Select AV Category':'Default','Amp':'AMP','Audio':'AUDIO','CD':'CD','Cable':'CABLE','DVB':'DVB','DVD':'DVD','Satellite':'SAT','Television':'TV'}"></s:select> --%>
	<s:hidden name="avBlaster.id"></s:hidden>
	<s:select name="avBlaster.category.id" label="Category" list="avCategoryList" listKey="id" listValue="name"></s:select>
	<s:textfield name="avBlaster.region" label="Region" cssClass="alphanumeric"></s:textfield>
	<s:textfield name="avBlaster.brand" label="Brand" cssClass="alphanumeric required"></s:textfield>
	<s:textfield name="avBlaster.code" label="IR Code Number" cssClass="required"></s:textfield>
	<s:submit value="Save" ></s:submit>
</s:form>


<script>
	$(document).ready(function() {
		var cForm = $("#AVBlasterCodeInfoForm");
		Xpeditions.validateForm(cForm);
	});
</script>