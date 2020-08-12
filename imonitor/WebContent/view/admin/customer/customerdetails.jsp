<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: red;"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:hidden name="customer.id" id="forhiddenfieldchange"></s:hidden>
<s:property value="customer.firstName"/>
<s:property value="customer.middleName"/>
<s:property value="customer.lastName"/>
<s:property value="customer.place"/>