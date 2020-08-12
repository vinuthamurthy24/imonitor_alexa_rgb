<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: red" id="ErrorMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form action="addNotification.action" method="post" id="notificationsaveform" cssClass="ajaxinlinepopupform">
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
	+<s:textfield name="systemNotificaion.countryCode" style="background-color: #B7D6B7;" size="3" maxlength="3" cssClass="number" id="cCode" ></s:textfield><s:textfield name="systemNotificaion.whatsApp" style="background-color: #B7D6B7;" id="whatsApp"  ></s:textfield>
	</div>
	</div>
	</div>
	
	<input type="hidden" id="defineEmailandSms" class="required"/>
	
	<s:submit value="Save"></s:submit>
</s:form>
<script>
	$(document).ready(function() {
		$("#whatsAppHtml").hide();
		$("#defineEmailandSms").val("");
		var cForm = $("#notificationsaveform");
	    var email = "";
	    var smsm = "";
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
		var show="";
		<s:set var="selectedOffer" value="optin"/>
		
		<s:if test='#selectedOffer == ""'>
			show="";
		</s:if>				
		<s:else>
			show='<s:property value="#selectedOffer" />';
		</s:else>
		
		
		
		
	$('#EMail').live('keyup', function(e) {
            var EMailOption = $("#EMail");
		    email=$(this).val();
              if(email==""){
               EMailOption.removeClass('email');
               $("#defineEmailandSms").val("");
               
              }else{
                   EMailOption.addClass('email');
                   $("#defineEmailandSms").val($("#defineEmailandSms").val() + $(this).val());
}

			}); 

      $('#SMS').live('keyup', function(e) {
    	
		sms=$(this).val();
		var SmsOption=$("#SMS");
		if(sms==""){
			SmsOption.removeClass('email');
			 EMailOption.addClass('email');
			$("#defineEmailandSms").val("");
			
		}else{
              SmsOption.addClass('mobilenumber');
              $("#defineEmailandSms").val($("#defineEmailandSms").val() + $(this).val());
			
		}
              }); 
      $('#whatsApp').live('keyup', function(e) {
      	
  		whatsapp=$(this).val();
  		
  		if(whatsapp==""){
  			
  			$("#defineEmailandSms").val("");
  			
  		}else{
                
                $("#defineEmailandSms").val($("#defineEmailandSms").val() + $(this).val());
  			
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
      
  	
		
	});// end of document
	
	
</script>	