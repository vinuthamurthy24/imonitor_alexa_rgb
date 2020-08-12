<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>

<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form action="addUser.action" method="post" id="usersaveform" cssClass="ajaxinlineform">
<s:textfield name="user.userId" label="User ID" cssClass="required alphanumeric editdisplayStar"></s:textfield>
<s:password name="user.password" label="Password" cssClass="required crosscheck confirmpass editdisplayStar" commonclass="confirmpass"></s:password>
<s:password label="Confirm Password" cssClass="required crosscheck confirmpass editdisplayStar" commonclass="confirmpass"></s:password>
<s:textfield name="user.email" label="Email ID" cssClass="required email editdisplayStar"></s:textfield>
<s:textfield name="user.mobile" label="Mobile Number" cssClass="required mobilenumber editdisplayStar"></s:textfield>
<s:textfield name="user.registrationDate" readonly="true" label="Registration Date" id="registrationDate" cssClass="datetime"></s:textfield>
<s:textfield name="user.customer.customerId" label="Customer ID" cssClass="hasblurlistener required editdisplayStar" targeturl="getCustomerByCustomerId.action" targetid="customerdetails" targethidden="hiddencustomerid"></s:textfield>
<s:hidden name="user.customer.id" id="hiddencustomerid" ></s:hidden>
<td class="tdLabel"><label for="roles" class="label"><s:text name="Roles:" /></label></td>
<td><select name='user.role.id' id = 'user' class='selectAlertName'></select></td>
<!--<s:select label="Role" name="user.role.id" list="roles" listKey="id" listValue="name"></s:select>-->
<s:select label="Status" name="user.status.id" list="statuses" listKey="id" listValue="name"></s:select>
<s:submit value="Save" ></s:submit>
</s:form>
<div id="customerdetails">
</div>
	<script>
		$(document).ready(function() {
			$("#registrationDate").datepicker({
				showOn: 'button',
				buttonImage: '/imonitor/resources/images/calendar.gif',
				buttonImageOnly: true,
				dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>',
				beforeShow: function(input, inst) {
					$(".ui-datepicker").css('z-index', 5000);
				},
				onClose: function(dateText, inst) { 
					$(".ui-datepicker").css('z-index', 1);
				},
				dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'
				});
			$("#registrationDate").datepicker({showOn: 'button', buttonImage: '/imonitor/resources/images/calendar.gif', buttonImageOnly: true,dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'});
			var cForm = $("#usersaveform");
			Xpeditions.validateForm(cForm);
			
			
			var roles="";
			

		    <s:iterator  value="roles" var="test" status="x">
			<s:if test='#test.name!="adminuser"'>
             roles+= "<option value='<s:property value="#test.id" />'><s:property value='#test.name'/></option>";
			 $(".selectAlertName").html(roles);
            </s:if>
		   
			
		     </s:iterator>
			
		});
	</script>