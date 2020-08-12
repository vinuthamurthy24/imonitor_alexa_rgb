<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>

<div style="color: red;"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<div>
<br>
<h4 align="center" style="font-style:italic;"><s:text name="setup.user.msg.0002" /> </h4>
</div>
<s:form name="resetPassConf" id="configureResetPasswordInfo" action="saveResetPasswordInfoFromHome.action" method="POST" cssClass="ajaxinlinepopupform">
	<div><br><br></div>
	<s:hidden name="selectedQuestionId" id="selectedQuestionId"></s:hidden>
	<s:hidden name="hintAnswerFromDB" id="hintAnswerFromDB"></s:hidden>
<%-- 	<s:hidden name="hintQuestionFromDB" id="hintQuestionFromDB"></s:hidden> --%>
	
	<s:password name="oldPassword" key="setup.user.password" cssClass="required editdisplayStar"></s:password>
	<s:textfield name="email" key="general.email" cssClass="required email editdisplayStar"></s:textfield>
	<s:select id="questionId" cssClass="required editdisplayStar" onchange="javascript:setQuestionIdBeforeSave()" key="setup.user.hint.q" 
				name="hintQuestion" list="hintQuestionList" listKey="id" listValue="question" headerKey="-1" 
				headerValue="---Please select a question---">
	</s:select>
<%-- 	<s:if test="selectedQuestionId > 0"> --%>
<%-- 		<s:textfield label="Previous Question" readonly="true" name="hintQuestionFromDB" id="hintQuestionFromDB" size="45"></s:textfield> --%>
<%-- 	</s:if> --%>
	<s:password id="hintAnswer" name="hintAnswer" key="setup.user.hint.a" cssClass="required editdisplayStar"></s:password>
	<s:submit key="general.save" id="saveInfo"></s:submit>
</s:form>

<script>
	$(document).ready(function() {
		var cForm = $("#configureResetPasswordInfo");
		Xpeditions.validateForm(cForm);
		
		var selectedQuesId = $("#selectedQuestionId").val();
		$("#questionId").val(selectedQuesId);
		
		var hintAnsFromDB = $("#hintAnswerFromDB").val();
		if(selectedQuesId != -1){
			document.forms["resetPassConf"]["hintAnswer"].value = hintAnsFromDB;
		}

	});

	$("#saveInfo").live("click", function(){
		var selectedId = $("#questionId").val();
		if(selectedId == -1){
			showResultAlert('<s:text name="setup.user.msg.0003" />');
			return false;
		}
	});


	
	function setQuestionIdBeforeSave(){
		//$("#selectedQuestionId")
		var selectedId = $("#questionId").val();
		if(selectedId != -1){
			document.forms["resetPassConf"]["selectedQuestionId"].value = selectedId;
		}else{
			showResultAlert('<s:text name="setup.user.msg.0002" />');
		}
	}
</script>