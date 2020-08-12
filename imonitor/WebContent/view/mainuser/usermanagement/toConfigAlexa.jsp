<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%><html>
<div style="color: blue;display:none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<s:form action="updateAmazondetails.action" method="POST" id="editcustomerform" cssClass="ajaxinlineform" style="text-align: left;">
	
	<s:hidden name="user.id"></s:hidden>
	<s:textfield name="userName" label="Amazon user name" cssClass="required"></s:textfield>
	<s:textfield name="amazonId" label="Amazon ID" cssClass="required email editdisplayStar"></s:textfield>
	<s:submit value="Save"></s:submit>
	
</s:form>