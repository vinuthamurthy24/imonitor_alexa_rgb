<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<h4>Add Customer.</h4>
<s:form action="saveCustomer.action" method="POST" id="addcustomerform" cssClass="ajaxinlinepopupform">
	<s:textfield name="customer.customerId" label="Customer ID"  cssClass="required alphanumeric editdisplayStar"></s:textfield>
	<s:textfield name="customer.firstName" label="Firstname" cssClass="alphanumeric"></s:textfield>
	<s:textfield name="customer.middleName" label="MiddleName" cssClass="alphanumeric"></s:textfield>
	<s:textfield name="customer.lastName" label="LastName" cssClass="alphanumeric"></s:textfield>
	<s:textfield name="customer.place" label="Address" ></s:textfield>
	<s:textfield name="customer.post" label="Post" ></s:textfield>
	<s:textfield name="customer.province" label="State" cssClass="alphanumeric"></s:textfield>
	<s:textfield name="customer.pincode"  cssClass="number" label="PinCode" minvalue="100000" maxvalue="999999"></s:textfield>
	<s:textfield name="customer.phoneNumber" cssClass="phonenumber" label="PhoneNumber" ></s:textfield>
	<s:textfield name="customer.email" label="Email ID" cssClass="required email editdisplayStar"></s:textfield>
	<s:textfield name="customer.mobile" label="Mobile Number" cssClass="required mobilenumber editdisplayStar"></s:textfield>
	<s:textfield name="customer.dateOfRegistration" readonly="true" label="Registration Date"  id="registrationdate" cssClass="mydatepicker"></s:textfield>
	<s:textfield name="customer.dateOfInstallation" readonly="true" label="Installation Date"  id="installationdate" cssClass="mydatepicker"></s:textfield>
	<s:select name="customer.renewalduration" label="RenewalDuration" value="12" list="#{'1':'1 month','2': '2 months','3': '3 months','4': '4 months','5': '5 months','6': '6 months','7': '7 months','8': '8 months','9': '9 months','10': '10 months','11': '11 months','12': '12 months','13': '13 months','14': '14 months','15': '15 months','16': '16 months', '17': '17 months','18': '18 months','19': '19 months','20': '20 months','21': '21 months','22': '22 months','23': '23 months','24': '24 months'}"></s:select>
	<s:textfield name="customer.smsThreshold" label="SMSThreshold" value="50" cssClass="number" ></s:textfield>

	<tr>
	<td>
	     System Integrator
	</td>
	<td>
		<select name="customer.systemIntegrator.id">
				<option>Select System Intergrator</option>
			<s:iterator  value="systemIntegrators" var="si" status="x">
				<option value="${si.id}">${si.name}</option>
			</s:iterator>
		</select>
	</td>
	</tr>
	<s:select label="Status" name="customer.status.id" list="statuses" listKey="id" listValue="name"></s:select>
	<s:textfield name="customer.freeStorageMB" label="FreeStorage(MB)" cssClass="required memory editdisplayStar"></s:textfield>
	<s:textfield name="customer.paidStorageMB" label="PaidStorage(MB)" cssClass="required memory editdisplayStar"></s:textfield>
	<s:textfield name="gateWayMacIds" label="GateWay Mac Id"  cssClass="macid required addnewrow ajaxchangelistener editdisplayStar" validateurl="getGateWayByMacId.action"></s:textfield>
	<table>
<tr>
<p style="color:blue;">_________________________________________________________________________________________________________________</p>	
  <h4>  Enabled Features.  </h4>
</tr>
  		<tr>
			<td>Health Check Device:</td>
			<td><s:checkbox theme="simple" name="healthcheck"/></td>
		</tr>
		<tr>
			<td>Preset:</td>
			<td><s:checkbox theme="simple" name="preset"/></td>
		</tr>
		 <tr>
			<td>Energy Dashboard:</td>
			<td><s:checkbox theme="simple" name="Dashboard"/></td>
		</tr> 
</table> 
	<s:submit value="Save"></s:submit>
</s:form>
<script>
	$(document).ready(function() {
		$(".mydatepicker").datepicker({
			showOn: 'button',
			buttonImage: '/imonitor/resources/images/calendar.gif',
			buttonImageOnly: true
			,
			beforeShow: function(input, inst) {
				$(".ui-datepicker").css('z-index', 5000);
			},
			onClose: function(dateText, inst) { 
				$(".ui-datepicker").css('z-index', 1);
			},
			dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'
			});
		//$(".addnewrow").xpAddImageAfter({addimgsrc:'/imonitor/resources/images/addbutton.png',removeimgsrc:'/imonitor/resources/images/deletbutton.png',addimgclass:'duplicatemyrow',removeimgclass:'removemyrow'});
		var cForm = $("#addcustomerform");
		Xpeditions.validateForm(cForm);
	});
	</script>