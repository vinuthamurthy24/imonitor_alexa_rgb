<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>


<div style="color: red;" id="ruleMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<div>
<h5 align="center" style="font-style:italic;"><s:text name="setup.user.msg.0004" /></h5>
</div>
<div>
<%-- <s:form id="ruleForm" action="resetPassword.action" method="GET" cssClass="ajaxinlinepopupform"> --%>
<s:form id="ruleForm" action="forgotPasswordAssistance.action" method="GET" cssClass="ajaxinlinepopupform">
					<table class="logincontant">
						<tr>
							<td>
								<s:text name="login.custid" />
							</td>

							<td height="40px">       
							<input type="text" Class="required" name="user.customer.customerId"style="background-color:#CECECE;" />
							</td>
						</tr>
						<tr>
							<td>
								<s:text name="login.username" />
							</td>

							<td height="40px">
							<input type="text" Class='required' name="user.userId" style="background-color:#CECECE;"/>
							</td>
						</tr>
						<tr style="padding-left:300px;">
							<td height="4px" colspan="2" align="right">
							<input type="submit" value="<s:text name='general.next' />" />
							</td>
						</tr>	
					</table>
		</s:form>
</div>