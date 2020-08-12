
<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<!--<s:property value="#session.user.userId"/><br>Enter your new password-->
<!-- <div class="pageTitle"> -->
<!-- 	<a class='' href="#" style="border: none;text-decoration: none;"><img src="/imonitor/resources/images/useradd.png"></a> -->
<%-- 	<span class='titlespan'>&nbsp;Change Password</span> --%>
<!-- 	<div class="pagetitleline"></div> -->
<br/>

<div style="color: red;"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<s:form action="saveGatewayNames.action" method="post" id="" name="" cssClass="ajaxinlinepopupform">
<s:hidden name="gateWay1.macId"></s:hidden>
<s:hidden name="gateWay2.macId"></s:hidden>
<s:hidden name="gateWay3.macId"></s:hidden>

<table>
<tr><td>1.</td><td><s:text name="gateWay1.macId" ></s:text></td><td><s:textfield name="gateWay1.name"></s:textfield></td></tr>
<tr><td>2.</td><td><s:text name="gateWay2.macId" ></s:text></td><td><s:textfield name="gateWay2.name"></s:textfield></td></tr>
<tr><td>3.</td><td><s:text name="gateWay3.macId" ></s:text></td><td><s:textfield name="gateWay3.name"></s:textfield></td></tr>
</table>
<br><br>



<s:submit key="general.save" id="saveInfo"/>
</s:form>
