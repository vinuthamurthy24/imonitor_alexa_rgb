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


<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.user.js"></script>

<s:form action="targetCost.action" method="post" cssClass="ajaxinlinepopupform" id="tarrifConfigForm">
	<span style=" margin: 0px 0px 0px -14px; color: red; font-size: small; ">*</span> <s:text name="Energy.targetcostmonth" />
	<table id="targetCost" border="1" style="margin: 63px 42px 20px 96px;">
	<input type='hidden' id="SelectedMonthValues" name="SelectedMonthValues"/>
	<th><s:text name="Energy.month" /></th><th><s:text name="Energy.Targetcost" />(&#8377;)</th><th><input type="checkbox" value="one_name1" title='<s:text name="Tooltip.SelectAll" />' id="selectall2" class="check"></th>
	<tr><td align="center"><s:text name="Energy.Jan" /></td><td><input id="jan" name="jan" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="jjj"></td></tr>
	<tr><td align="center"><s:text name="Energy.Feb" /></td><td><input id="feb" name="feb" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="fff"></td></tr>
	<tr><td align="center"><s:text name="Energy.March" /></td><td><input id="march" name="march" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="mmm"></td></tr>
	<tr><td align="center"><s:text name="Energy.April" /></td><td><input id="april" name="april" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="app"></td></tr>
	<tr><td align="center"><s:text name="Energy.May" /></td><td><input id="may" name="may" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="mayy"></td></tr>
	<tr><td align="center"><s:text name="Energy.June" /></td><td><input id="june" name="june" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="junee"></td></tr>
	<tr><td align="center"><s:text name="Energy.July" /></td><td><input id="july" name="july" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="julyy"></td></tr>
	<tr><td align="center"><s:text name="Energy.Aug" /></td><td><input id="aug" name="aug" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="augg"></td></tr>
	<tr><td align="center"><s:text name="Energy.Sep" /></td><td><input id="sep" name="sep" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="sepp"></td></tr>
	<tr><td align="center"><s:text name="Energy.Oct" /></td><td><input id="oct" name="oct" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="octt"></td></tr>
	<tr><td align="center"><s:text name="Energy.Nov" /></td><td><input id="nov" name="nov" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="novv"></td></tr>
	<tr><td align="center"><s:text name="Energy.Dec" /></td><td><input id="dec" name="dec" cssClass="required number">&#8377;</input></td><td><input type="checkbox" name="checkbox" value="one_name1" class="checkBoxClass" id="decc"></td></tr>
	</table>
	

	<s:submit key="general.save" onclick="handleFormSerialization()" ></s:submit>
</s:form>

<script type="text/javascript">
$(document).ready(function() {
var targetcost='<s:property value="cost"/>';
 var res = targetcost.split(",");
 if(res.length==12)
 {
 $('input:checkbox').attr('checked','checked');
 }
 for(var i=0;i<=res.length;i++)
 {
if(i==0)
{
	
  $("#jan").val(res[i]);
 }
 else if(i==1)
{
  $("#feb").val(res[i]);
 }
 else if(i==2)
{
  $("#march").val(res[i]);
 }
 else if(i==3)
{
  $("#april").val(res[i]);
 }
 else if(i==4)
{
  $("#may").val(res[i]);
 }
 else if(i==5)
{
  $("#june").val(res[i]);
 }
 else if(i==6)
{
  $("#july").val(res[i]);
 }
 else if(i==7)
{
  $("#aug").val(res[i]);
 }
 else if(i==8)
{
  $("#sep").val(res[i]);
 }
 else if(i==9)
{
  $("#oct").val(res[i]);
 }
 else if(i==10)
{
  $("#nov").val(res[i]);
 }
 else if(i==11)
{
  $("#dec").val(res[i]);
 }
 
 
 
 }
});
	$('.checkBoxClass').click(function() {
    if(!$(this).is(':checked'))
{
$(this).attr('disabled', 'disabled');
$(this).closest('tr').find('input:text').val("");
}
      });

    
    $(selectall2).on("click", function () {
	
	$(".checkBoxClass").removeAttr('disabled');	
	var value_entred = $('#targetCost tr:not(".headerRow")').eq(1).find('input').val();
 
	
   
    if($(this).is(':checked'))
	{
	$(".checkBoxClass").attr('checked', this.checked); 
	        
			$('#targetCost tr:not(.headerRow :first-child)').each(function() {

                $(this).find('input').val(value_entred);
			    	
		});
		
		
	}
	else
	{
	       $(".checkBoxClass").removeAttr('checked'); 
			$('#targetCost tr:not(:first-child)').each(function() {

				
				if($(this).index() != 1)
                $(this).find('input').val("");
				
			    	
		});
	}
	
   });
		   
		   
    function handleFormSerialization()
	{
	 var selctedValues="0";
		$('#targetCost tr:not(:first-child) ').each(function() {

					
						var nextValue =  $(this).find('input:not(:checkbox)').val();
						
						if($(this).index() == 1)
						{
						  if(nextValue != "")
						  {
							  selctedValues=nextValue;
						  }
						   else
						  {
							   selctedValues="0";
						  }
						}
						else
						{
						  if(nextValue != "")
						  {
							  selctedValues+=","+nextValue;
						  }
						   else
						  {
							   selctedValues+=","+"0";
						  }
						}
						
                    
							
					
				
					
				
				
                
				
			    	
		});
		
		$('#SelectedMonthValues').val(selctedValues);
	}
		   
	   
</script>
