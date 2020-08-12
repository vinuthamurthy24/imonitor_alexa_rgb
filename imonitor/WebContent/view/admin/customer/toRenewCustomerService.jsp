
<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
	<%@ taglib uri="/struts-tags" prefix="s"%>
	<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
	<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
	<%@page import="com.opensymphony.xwork2.ActionContext"%>
	<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
	<%ActionContext.getContext().getSession().remove("message"); %>
	</div>
	
	
	<s:form action="RenewCustomerService.action" method="POST" id="CustomerRenewalServiceform" cssClass="ajaxinlinepopupform">
	<div id="renewal">
	<s:hidden name="customer.id"></s:hidden>
	
	<h4>Renewal option</h4>
	<table>
	<tr>
	<td><s:textfield name="customer.customerId" label="CustomerId" readonly="true"></s:textfield>
	<s:textfield name="customer.renewalDate" readonly="true" label="RenewalDate"  id="renewaldate" cssClass="mydatepicker"></s:textfield>
	<%-- <td><s:textfield label="RenewalDate" name="renewalDate" cssClass="mydatepicker" id="date"/></td> --%>
	<td><s:select label="RenewalDuration" name="customer.renewalDuration" list="#{'1':'1 month','2': '2 months','3': '3 months','4': '4 months','5': '5 months','6': '6 months','7': '7 months','8': '8 months','9': '9 months','10': '10 months','11': '11 months','12': '12 months','13': '13 months','14': '14 months','15': '15 months','16': '16 months', '17': '17 months','18': '18 months','19': '19 months','20': '20 months','21': '21 months','22': '22 months','23': '23 months','24': '24 months'}"/>
	</tr>
	</table>
</div>

<table>
<tr>	
<p style="color:blue;">___________________________________________</p>
<h4>  Intimation  </h4>
</table>

<div id="intimation"  >
	<div>
	<s:checkbox name="support"/><span>support@imonitorsolutions.com</span>
	</div>
	
	<div>
	<s:checkbox name="customeremail"/><s:text name="customer.email" />
	</div>
	
	<div>
	<s:checkbox name="otherMail" id="intimate"/><s:textfield name="othermail" label="otherMailId"  id="othermail" readonly="true"></s:textfield> 
	</div>

</div>	

<s:submit value="Save"></s:submit>
	</s:form>

<script type="text/javascript">
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
	$(".mydatepicker" ).datepicker( "option", "showButtonPanel", true );
	$("#ui-datepicker-div").hide();
	
	
			var cForm = $("#CustomerRenewalServiceform");
			Xpeditions.validateForm(cForm);
			
			$("#intimate").live('click', function(){
				
				if($(this).attr('checked')){
				    $("#othermail").removeAttr("readOnly");
					$("#othermail").addClass("required");
					$("#othermail").addClass("email");
				
				}else{
					$("#othermail").attr("readOnly",true);
					$("#othermail").val("");
					$("#othermail").removeClass("required");
					$("#othermail").removeClass("email");
			}
		});
	
	});
</script>