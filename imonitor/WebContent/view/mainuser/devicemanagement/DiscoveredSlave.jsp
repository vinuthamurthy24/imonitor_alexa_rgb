<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String applicationName = request.getContextPath();
%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<span style="display:none;">
<div style="color: red;" id="messageSection">
	<s:property value="#session.message" />
</div> </span>
<%ActionContext.getContext().getSession().remove("message"); %>





<style type="text/css">

.signin_btn
{
background-color: gray; 
    border: none;
    color: white;
    padding: 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 12px;

}


.action_btn
{
background-color: grey; 
    border: none;
    color: white;
    padding: 10px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 12px;

}

</style>
<script type="text/javascript">
$( function() {
    $( "#dialog" ).dialog();
  } );
  
function DestoryDialog()
{
           $("#editmodal").dialog('destroy'); 
}	
</script>


<div id="">
<s:form action="DiscoverIndoorUnit.action" method="post" cssClass="ajaxinlinepopupform" id="vrvForm" >
<s:hidden name="device.generatedDeviceId"></s:hidden>
<s:hidden name="device.gateWay.macId"></s:hidden>
<s:hidden name="device.id"></s:hidden>
<s:hidden name="device.deviceId"></s:hidden>
<span>Do you want to add/discover Indoor units?</span>
<div class="division">
<input type="submit" value="Yes" class="action_btn"  />
<input  type="button" class="action_btn" value="No" onclick="javascript:DestoryDialog()" />
</div>
</s:form>

</div>
