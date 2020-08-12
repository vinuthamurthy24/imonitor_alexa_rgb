<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
               <s:property value="#session.message"/>
                <%ActionContext.getContext().getSession().remove("message");%>
                
			<form>
			<h4>Choose Action</h4><br />
			<input type="radio"  name="log" value="START CAPTURE OF LOG" onclick="javascript:setvalue(this.value)"/>START CAPTURE OF LOG <br />
			<input type="radio"  name="log" value="REQUEST FOR UPLOAD"  onclick="javascript:setvalue(this.value)"/>REQUEST FOR UPLOAD <br />
			<input type="radio"  name="log" value="REQUEST FOR DELETE"  onclick="javascript:setvalue(this.value)"/>REQUEST FOR DELETE <br />
			
			</form>	
			
			</br></br>
			<s:form  id="loglevel" action="RequestForLog.action" method="post" cssClass="ajaxinlineform formalign">
			<s:select name="selectedValue"  label="Choose Log Level" list="#{'1':'LOGLEVEL ERROR','2':'LOGLEVEL WARNING','3':'LOGLEVEL DEBUG','4':'LOGLEVEL INFO','5':'NOLOGS'}"></s:select>
			<s:textfield name="timeout" size="5" label="Choose Time Out" ></s:textfield>
			<s:hidden name="gateWay.id"></s:hidden>
			<s:hidden name="gateWay.macId"></s:hidden>
            <s:submit align="right" value="Save" ></s:submit>
			</s:form>
			
			
			<s:form  id="upload" action="RequestForLog.action" method="post" cssClass="ajaxinlineform formalign">
			<s:checkboxlist label=" Upload Log Of Days" list="days" name="upload"/>
			<s:hidden name="gateWay.id"></s:hidden>
			<s:hidden name="gateWay.macId"></s:hidden>
            		<s:submit align="right" value="Save" ></s:submit>
			</s:form>
			
			<s:form  id="delete" action="RequestForLog.action" method="post" cssClass="ajaxinlineform formalign">
			<s:checkboxlist label="Delete Log Of Days" list="days" name="delete"/>
            		<s:hidden name="gateWay.id"></s:hidden>
			<s:hidden name="gateWay.macId"></s:hidden>
            		<s:submit align="right" value="Save"></s:submit>
			</s:form>
<script>
$(document).ready(function() {
    
    $("#loglevel").hide();
    $("#upload").hide();
    $("#delete").hide();
});
function setvalue(value)
{
	
	if(value=="START CAPTURE OF LOG")
	{$("#loglevel").show();
	$("#upload").hide();
    $("#delete").hide();}

	if(value=="REQUEST FOR UPLOAD")
	{
 	$("#upload").show();
 	$("#delete").hide();
	$("#loglevel").hide();
	}

	if(value=="REQUEST FOR DELETE")
	{$("#delete").show();
	$("#loglevel").hide();
  	 $("#upload").hide();}
}
</script>
