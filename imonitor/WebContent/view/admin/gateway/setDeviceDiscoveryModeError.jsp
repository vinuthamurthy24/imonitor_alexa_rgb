<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: red"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>