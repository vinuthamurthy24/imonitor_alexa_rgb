<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: black;">
<s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
		</div>