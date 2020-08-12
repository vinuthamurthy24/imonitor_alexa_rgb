<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.opensymphony.xwork2.ActionContext"%><html>
<div style="color: blue;"><p class="message"><s:property value="#session.message" /></p>
</div>
<%ActionContext.getContext().getSession().remove("message"); %>