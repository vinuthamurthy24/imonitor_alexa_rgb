<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
	<s:form action="saveEditedSuperUserDetails.action" method="post" id="" cssClass="ajaxinlinepopupform">
	
	
		<s:hidden name="superuser.id"></s:hidden>
		<h3><i>Customers Mapped</i></h3>
				 <s:iterator value="customersList"> 
    			<tr>
    			<td><b style="color:blue;"><s:property value="customerId"/></b></td>
				</tr>
				</s:iterator> 		
		<%-- <s:textfield name="superuser.superUserId"  label="Edit Name" required="true" cssClass="" ></s:textfield> --%>
		<s:password name="superuser.password" label="Enter New Password" cssClass=""></s:password>
		 <div id="editmodal"></div>
		<div id="confirm"></div>
		<div id="message"></div>
	
	<s:submit id="sub" value="Submit"></s:submit>
	</s:form>
