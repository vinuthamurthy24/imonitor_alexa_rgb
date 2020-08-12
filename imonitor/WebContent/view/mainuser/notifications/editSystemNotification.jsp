<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%><div style="color: red" id="ErrorMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<s:form action="editSystemNotification.action" method="post" id="notificationsaveform" cssClass="ajaxinlinepopupform">
	<s:hidden name="systemNotificaion.id"></s:hidden>
	<s:hidden name="systemNotificaion.status.id"></s:hidden>
	<table>
	<tr><s:textfield name="systemNotificaion.name" key="general.name" maxlength='25' cssClass="required"></s:textfield></tr>
	<tr><s:textfield name="systemNotificaion.EMail" id="EMail" key="setup.not.Email" ></s:textfield></tr>
	<tr><s:textfield name="systemNotificaion.SMS" id="SMS" key="setup.not.SMS" ></s:textfield></tr>
	</table>
	
	<div>
	<p style="color:blue;">___________________________________________</p>
	
	
	<s:text name="general.WhatsApp" /><s:checkbox theme="simple" id="checkWApp" name="checkWhatsApp"/>
	<div id="whatsAppHtml"style="margin-top: 10px;">
	<span style="margin-left: 15px;">CC</span>
	<span style="margin: 0px 0px 0px 45px;">Mobile number</span>
	<div>
	+<s:textfield name="systemNotificaion.countryCode" style="background-color: #B7D6B7;" size="3" maxlength="3" id="cCode" cssClass="number"></s:textfield><s:textfield name="systemNotificaion.whatsApp" style="background-color: #B7D6B7;" id="whatsApp"  ></s:textfield>
	</div>
	</div>
	</div>
	
	<input type="hidden" id="defineEmailandSms" class="required"/>	
	
	
	<s:submit key="general.save"></s:submit>
</s:form>

<script>
$(document).ready(function() {
	$("#whatsAppHtml").hide();
	if($("#checkWApp").attr('checked')){
		$("#whatsAppHtml").show();
	}
	if($("#checkSms").attr('checked')){
		$("#SMS").removeAttr("readOnly");
	}
	if($('#EMail').val() != ""){
	  var em = $('#EMail').val();
	$('#defineEmailandSms').val(em);
		
		
	}
	
	if($('#SMS').val() != ""){
		  var sm = $('#SMS').val();
		$('#defineEmailandSms').val(sm);
			
			
		}
	
	if($('#whatsApp').val() != ""){
		  var sm = $('#whatsApp').val();
		$('#defineEmailandSms').val(sm);
			
			
		}
	
	var cForm = $("#notificationsaveform");
	var email = "";
	var sms = "";
    
	var inputs = cForm.find('input');
	
	inputs.each(function(index, inp)
	{
	var input = $(inp);
	if(input.hasClass('required'))
	{
		input.closest("tr").find("td:first-child").append("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: -4px -2px 0px 0px;'>*</span>");

	}
	
	});
	
	
	
	
	Xpeditions.validateForm(cForm);
	
	
	
	
	
	$('#EMail').live('keyup', function(e) {
        var EMailOption = $("#EMail");
	    email=$(this).val();
          if(email==""){
        	  $("#defineEmailandSms").val("");
           EMailOption.removeClass('email');
           
          }else{
              
               EMailOption.addClass('email');
               
               $("#defineEmailandSms").val($(this).val());
}

		}); 

  $('#SMS').live('keyup', function(e) {
	sms=$(this).val();
	var SmsOption=$("#SMS");
	if(sms==""){
		  $("#defineEmailandSms").val("");
		SmsOption.removeClass('mobilenumber');
		
	}else{
		 
          SmsOption.addClass('mobilenumber');
          $("#defineEmailandSms").val($(this).val());
		
	}
          }); 
  
  $('#whatsApp').live('keyup', function(e) {
		sms=$(this).val();
		var SmsOption=$("#whatsApp");
		if(sms==""){
			  $("#defineEmailandSms").val("");
			
			
		}else{
			          
	          $("#defineEmailandSms").val($(this).val());
			
		}
	          });
 
  $("#checkWApp").live('click', function(){
		
		if($(this).attr('checked')){
			$("#whatsAppHtml").show();
			$("#cCode").addClass("required");
			$("#whatsApp").addClass("required");
			$("#whatsApp").addClass("number");
			
		}else{
			$("#whatsAppHtml").hide();
			$("#cCode").removeClass("required");
			$("#whatsApp").removeClass("required");
			$("#whatsApp").removeClass("number");
			$("#cCode").val("");
			$("#whatsApp").val("");
		}
			
	});
    
	

});
</script>	