<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%><html>
<div style="color: blue;display:none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<script>
$(document).ready(function(){
	
/* 	var broadcast = $("#midPort").val();
	var loginport = $("#midLoginPort").val();
	var lftp = $("#lftp").val(lftp);
	
	if(broadcast == "FAIL"){
		
	}else{
		
		$("#broadcast").val(broadcast);
	}
	
	$("#LoginPort").val(loginport);
	$("#lftp").val(loginport); */
	
});


</script>
<s:form action="" theme="simple" method="post" cssClass="ajaxinlinepopupform" >
 
<table>
<tr>
<td>Mid Broadcast port :</td>
<td id="broadcast"> <s:textfield name="midPort" size="5" ></s:textfield></td>
</tr>
<tr>
<td>Mid Login Port : </td><td id="LoginPort"><s:textfield name="midLoginPort" size="5" ></s:textfield></td>
</tr>
<tr>
<td>Lftp Download :</td><td id="lftp"> <s:textfield name="lftpDownload" size="5" ></s:textfield></td>
</tr>
</table>
</s:form>