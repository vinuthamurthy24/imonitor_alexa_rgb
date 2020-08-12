<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>

<div>
<h5 align="center" style="font-style:italic;">Forgot Your Password ??</h5>
<%-- <s:form id="ruleForm" action="resetPassword.action" method="GET" cssClass="ajaxinlinepopupform"> --%>
<form id="ruleForm" action="" method="GET"  class="ajaxinlinepopupform">
		<div align="center">
					<table class="logincontant">
						<tr>
							<td>
								<b>Customer Id</b>
							</td>

							<td height="40px">       
							<input type="text" Class="required" name="customerid" style="background-color:#CECECE;" />
							</td>
						</tr>
						<tr>
							<td>
								<b>User Name</b>
							</td>

							<td height="40px">
							<input type="text" Class='required' name="userid" style="background-color:#CECECE;"/>
							</td>
						</tr>
						<tr style="padding-left:300px;">
							<td height="4px" colspan="2" align="right">
							<input type="submit" value="Next" />
							</td>
						</tr>	
					</table>
			</div>
			</form>
		
</div>