<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>

<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<div class="pageTitle">
	<table>
		<tr>
			<td>
				<a class='' href="#" style="border: none;text-decoration: none;"><img src="/imonitor/resources/images/useradd.png"></a>		
			</td> 
			<td>
				<a class='' href="#" style="border: none;text-decoration: none;"><span class='titlespangeneric'>&nbsp;<s:text name="setup.systemalerts.edit"/></span></a>		
			</td> 
		</tr>
	</table>

	<div class="pagetitleline"></div>
</div>
<br/>


<s:form action="updatesystemAlert.action" method="POST" id="updatesystemAlert" cssClass="ajaxinlineform" theme="simple">
	<table>
		<th><s:text name="setup.systemalerts.title"/> </th>
		<th><s:text name="general.sms"/></th>
		<th>&nbsp;</th><th>&nbsp;</th>
		<th><s:text name="general.email"/></th>
		<th>&nbsp;</th><th>&nbsp;</th>		
	<s:iterator value="systemAlerts" var="systemAlert">
	<% 
		String checkeSMS = "";
		String checkeEmail = "";
		
	%>
	
			<s:iterator value="systemAlertActionsByUsers" var="userAlertId">
				<s:if test="#systemAlert.id == #userAlertId[1]">
					<s:if test="#userAlertId[2] == 'SMS'">
						<% checkeSMS = "checked";%>
					</s:if>
					<s:if test="#userAlertId[2] == 'E-Mail'">
						<% checkeEmail = "checked";%>
					</s:if>
					
				</s:if>
			</s:iterator>
			<tr>
			
			<td>
			<s:if test='utf8name != "Logged Out"'>
						
						<s:property value="utf8name"/>
						
					
			</td>
			<td>
				<input type="checkbox" name="sms" value="<s:property value="id"/>" <%=checkeSMS %>></input>
			</td>
			<td>&nbsp;</td><td>&nbsp;</td>
			<td>
				<input type="checkbox" name="emal" value="<s:property value="id"/>" <%=checkeEmail %>></input>
			</td>
			
			</tr>
			</s:if>
		</s:iterator>
	</table>
</s:form>
<script>
$(':checkbox').live('click',function() {
    $(this).closest('form').submit();
}); 
</script>