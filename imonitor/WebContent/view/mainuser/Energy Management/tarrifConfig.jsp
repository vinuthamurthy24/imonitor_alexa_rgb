<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>

<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
String applicationName = request.getContextPath();
%>

<div style="color: blue;" id="ErrorMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<%-- <script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.user.js"></script> --%>
<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
<s:form action="tarrifConfig.action" method="post" cssClass="ajaxinlinepopupform" id="tarrifConfigForm">
	<br><br><br>
	<span style=" font-family: inherit; font-size: large;">1 Unit=</span><input id="unitValue" name="unitValue" maxlength='6'><span style=" font-size: large; ">KW</span>
	
	<br><br><br>
	<table id="Tarriffconfigtable"><span><input type="hidden" id="criteriaValidateHidden"/></span></table>
     <div id="confirm"></div>
	<s:submit key="general.save" id="validate" onclick="validateunitvalue()" ></s:submit>
</s:form>
<script>

	var cForm = $("#tarrifConfigForm");
	Xpeditions.validateForm(cForm);
	
	
	var handleAlertValueChange = function(ev){
		
	var val = $(this).val();
	if(val <= 0){
		$('#validate').prop("disabled", true);
		//$("#validate").hide();
	}else{
		
		//$("#validate").show();
		$('#validate').prop("disabled", false);
	}
		
	};
	
	function showResultAlert(message) {
	$("#confirm").dialog("destroy");
	 $("#confirm").html(message);
		$("#confirm").dialog('open');
		$("#confirm" ).dialog({
			stackfix: true,
			modal: true,
			buttons: {
			Ok:function(){
				$(this).dialog('close');
				$("#confirm").html("");
				$("#confirm").dialog("destroy");
			}
		}
		});
	}
	
	
	function validateunitvalue() {
	if($("#unitValue").val()==0)
	{
	showResultAlert("please enter valid unit value"); 
	}
	}
	
	$(document).ready(function() {
		
	    userDetails.tarrifConfig();
	  
    var unitValue='<s:property value="unitValue"/>';
		
        
	    $("#unitValue").val(unitValue);
      
	    $("#unitValue").die("keyup");
		$("#unitValue").live("keyup",handleAlertValueChange);
	    
	    
	    
	    var AddNextRowinTarrif = function()
	    {
		var cRow = $(this).parent().parent();
		var cStartHours = cRow.find('#StartHourSelect').val();
	
		var cEndHours = cRow.find('#EndHourSelect').val();
	
		var alertValueText = cRow.find('.actionvalueclass').val();
	
		var startUnit = cRow.find('.startslabrange').val();
	
		var cEndUnit = cRow.find('.Endslabrange').val();
	
		var TariffID=cRow.find('.TariffID').val();
		
		if(cEndUnit>=startUnit)
		{
		var isLastRow = cRow.nextAll().length == 0;
		if(isLastRow)
		{
			if($('.selectTimeRange').length < 10) 
		{	
		
	
	    if(alertValueText==""){
			alertValueText=0;
		}
		var experssion = "";
	    if($.trim(cStartHours + cEndHours + alertValueText) != ""){
		experssion = TariffID+"="+cStartHours + "=" + cEndHours+ "=" + alertValueText +"=" + startUnit +"=" + cEndUnit;
		}
		
		cRow.find('input[name="alertExpressions"]').val(experssion); 
		$("#criteriaValidateHidden").val("");
	     $('input[name="alertExpressions"]').each(function(){
		
		 $("#criteriaValidateHidden").val($("#criteriaValidateHidden").val() + $(this).val());
	      });	
		  
		  var cloneLastRow = cRow.clone();
		  $(cloneLastRow).find('input').val("");
		  cRow.after(cloneLastRow);
		}	
		else
		{
		showResultAlert("Limit for create Tariff Configaration is 10"); 
		}
		}
		}
		else
		{
		showResultAlert("Please Enter End Unit is greater than start unit");
		cRow.find('.Endslabrange').val("");
		}
		};
		
	
	
	var handleEndChange = function(ev){
	var alertValueText = $(this).val();
	var cRow = $(this).parent().parent();
	var cStartHours = cRow.find('.StartHourSelect').val();
	var cEndHours = cRow.find('.EndHourSelect').val();
	
	var alertValueText = cRow.find('.actionvalueclass').val();
	var startUnit = cRow.find('.startslabrange').val();
	var cEndUnit = cRow.find('.Endslabrange').val();
	var TariffID=cRow.find('.TariffID').val();

	
	var experssion = "";
	if($.trim(cStartHours + cEndHours + alertValueText) != ""){
		experssion = TariffID+"="+cStartHours + "=" + cEndHours+ "=" + alertValueText +"=" + startUnit +"=" + cEndUnit;
		}
	
	cRow.find('input[name="alertExpressions"]').val(experssion); 
	$("#criteriaValidateHidden").val("");
	$('input[name="alertExpressions"]').each(function(){
	
		$("#criteriaValidateHidden").val($("#criteriaValidateHidden").val() + $(this).val());
	});
};
	
	
	
	var handleStartEndChange = function(ev){
	
	var alertValueText = $(this).val();
	var cRow = $(this).parent().parent();
	var cStartHours = cRow.find('#StartHourSelect').val();
	var cEndHours = cRow.find('#EndHourSelect').val();
	var alertValueText = cRow.find('.actionvalueclass').val();
	var startUnit = cRow.find('.startslabrange').val();
	var cEndUnit = cRow.find('.Endslabrange').val();
	var TariffID=cRow.find('.TariffID').val();
	var experssion = "";
	if($.trim(cStartHours + cEndHours + alertValueText) != ""){
		experssion = TariffID+"="+cStartHours + "=" + cEndHours+ "=" + alertValueText +"=" + startUnit +"=" + cEndUnit;
		}
	
	cRow.find('input[name="alertExpressions"]').val(experssion); 
	$("#criteriaValidateHidden").val("");
	$('input[name="alertExpressions"]').each(function(){
	
		$("#criteriaValidateHidden").val($("#criteriaValidateHidden").val() + $(this).val());
	});
};
	
	var handleStartEndUnit = function(ev){
	
	var alertValueText = $(this).val();
	var cRow = $(this).parent().parent();
	var cStartHours = cRow.find('#StartHourSelect').val();
	var cEndHours = cRow.find('#EndHourSelect').val();
	var alertValueText = cRow.find('.actionvalueclass').val();
	var startUnit = cRow.find('.startslabrange').val();
	var cEndUnit = cRow.find('.Endslabrange').val();
	var TariffID=cRow.find('.TariffID').val();
	var experssion = "";
	if($.trim(cStartHours + cEndHours + alertValueText) != ""){
		experssion = TariffID+"="+cStartHours + "=" + cEndHours+ "=" + alertValueText +"=" + startUnit +"=" + cEndUnit;
		}
	
	cRow.find('input[name="alertExpressions"]').val(experssion); 
	$("#criteriaValidateHidden").val("");
	$('input[name="alertExpressions"]').each(function(){
	
		$("#criteriaValidateHidden").val($("#criteriaValidateHidden").val() + $(this).val());
	});
};


	
		
	var removeCurrentRowinTarrif = function(){
	var cRow = $(this).parent().parent();
	var cActionId = cRow.find(".actionvalueclass").val();
	var remainingRowCount = cRow.siblings().size();
    var TariffID =cRow.find('.TariffID').val();
	
       
	var params = {"TariffID":TariffID};
	var isFinalRow = remainingRowCount == 2;			
	var selects = $(this).closest('table').find('select');
	if(!isFinalRow)
	{
	$.ajax({
			url: "removeRow.action",
			data:params,
			success: function(data){
							  								
								cRow.remove();
							  }
		});
	}

	if(remainingRowCount==1)
	{
		selects.change();
	}
	return false;
};
	
		
		<s:iterator value="hours" var ="hr">
		$('#StartHourSelect').append('<option value="<s:property value="#hr"/>">'+ "<s:property value="#hr"/>" +'</option>');
		$('#EndHourSelect').append('<option value="<s:property value="#hr"/>">'+ "<s:property value="#hr"/>" +'</option>');
		</s:iterator>
		<s:iterator value="minutes" var ="min">
		$('#StartMinuteSelect').append('<option value="<s:property value="#min"/>">'+ "<s:property value="#min"/>" +'</option>');
		$('#EndMinuteSelect').append('<option value="<s:property value="#min"/>">'+ "<s:property value="#min"/>" +'</option>');
		</s:iterator>
		
		<s:iterator value="slabrange" var ="slbrange">
		$('#startslabrange').append('<option value="<s:property value="#slbrange"/>">'+ "<s:property value="#slbrange"/>" +'</option>');
		</s:iterator>
			
		<s:iterator value="Endslabrange" var ="Endslabrange">
		$('#Endslabrange').append('<option value="<s:property value="#Endslabrange"/>">'+ "<s:property value="#Endslabrange"/>" +'</option>');
		</s:iterator>
		
		
		
		

		$("#EndHourSelect").die("change");
		$("#EndHourSelect").live("change",handleEndChange);

		$("#StartHourSelect").die("change");
		$("#StartHourSelect").live("change",handleEndChange);
		
		$(".actionvalueclass").die("keyup");
		$(".actionvalueclass").live("keyup",handleStartEndChange);
		
		$(".removeCurrentRowinTarrif").die("click");
		$(".removeCurrentRowinTarrif").live("click",removeCurrentRowinTarrif);
		
		$(".AddNextRowinTarrif").die("click");
		$(".AddNextRowinTarrif").live("click",AddNextRowinTarrif);
		
		$(".startslabrange").die("keyup");
		$(".startslabrange").live("keyup",handleStartEndChange);
		
		$(".Endslabrange").die("keyup");
		$(".Endslabrange").live("keyup",handleStartEndChange);
		
		<s:iterator  value="tariffConfigg" var="dev" status="valueStatus1" >
       
      	var cRow = $("#Tarriffconfigtable").find('tr:last');
      	
		cRow.find('#StartHourSelect').val('<s:property value="#dev.startHour" />');
      	cRow.find('#EndHourSelect').val('<s:property value="#dev.endHour" />');
 
    	cRow.find('.actionvalueclass').val('<s:property value="#dev.tarrifRate" />');
    	
    	cRow.find('.startslabrange').val('<s:property value="#dev.startSlabRange" />');
    	
    	cRow.find('.Endslabrange').val('<s:property value="#dev.endSlabRange" />'); 
    	
		cRow.find('.TariffID').val('<s:property value="#dev.id" />');
		
		cRow.find('#StartHourSelect').change();
		<s:if test="#valueStatus1.last != true">
      	
    		cRow.find('.AddNextRowinTarrif').click();
    	
      	</s:if>
      	
	  </s:iterator>
	  
	  
	});
</script>