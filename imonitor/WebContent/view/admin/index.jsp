<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%
String applicationName = request.getContextPath();
%>

<%@page import="com.opensymphony.xwork2.ActionContext"%><html>
	<head>
		<title>
			imonitor
		</title>
		<link rel="shortcut icon" href="<%=applicationName %>/resources/images/favicon.ico" type="image/-icon" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/in.xpeditions.css" rel="stylesheet" />
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.5.1.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.js"></script>
		<script type="text/javascript">
		    window.history.forward();
		    function noBack() { window.history.forward();
 			}

			$(document).ready(function() {
				
				$('.editlink').live('click', function(){
					var targeturl = $(this).attr('href');
					var pHeight = $(this).attr('popupheight');
					var pWidth = $(this).attr('popupwidth');
					var pTitle = $(this).attr('popuptitle');
					if(pHeight == undefined){
						pHeight = 500;
						
					}
					if(pWidth == undefined){
						pWidth = 700;
						
					}
					if(pTitle == undefined){
						pTitle = "Please fill it and submit.";
						
					}
					$.ajax({
						  url: targeturl,
						  success: function(data){
							$('#editmodal').html(data);
							$('#editmodal').dialog('destroy');
							$('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true});
						  }
						});
					return false;
				});
				
//				To show the messages, whenever needed.
				$( "#editmodal" ).dialog({
					height:350,
					width: 700,
					modal: true,
					autoOpen:false
				});
				$('.ajaxinlinepopupform').live('submit', function() {
					 var params = $(this).serialize();
					 $.post($(this).attr('action'), params, function(data){
						var a= $(data).find("p.message").text();
						var aa = a.split(":");
						if(aa[0] =='Failure'){
							$("#ruleMessage").html(aa[1]);
							}
						else{
							$("#ruleMessage").html(aa[1]);
							// $('#editmodal').dialog('close');
						}
					 });
					 return false;
				});

				// Validation of form ends here.
				$( "#message" ).dialog({
					height: 100,
					width: 700,
					modal: true,
					autoOpen:false,
					buttons: {
						Ok: function() {
								$(this).dialog('close');
							}
						}
				});
			});
		</script>
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="" class="bodyclasslogin">
	<div style="width:100%; height:80px;">
		<div class="mainmenu">
					<!--  vibhuti commented <ul id="topmenu">
						<li>
							<a href="#">Home</a>
						</li>
						<li>
							<a href="#">About</a>
						</li>
						<li>
							<a href="#">Contact</a>
						</li>
						<li>
							<a href="#">Help</a>
						</li>
					</ul> -->
		</div> 
	 </div>
		<div class="maindivlogin">
			<div class="loginbg">
				<div class="login">
					<div style="color: red;"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
		</div>
					<s:form action="../imonitor/adminLogin.action" method="post" id="loginform">
					<table class="logincontant" style="margin-top:20px;">
					<tr>
							<td>
								User Name 
							</td>

							<td height="40px">
							<input type="text" name="user.userId" style="background-color:#CECECE;"/>
							</td>
						</tr>
						<tr>
							<td>
								Password 
							</td>

							<td height="40px">
							<input type="password" name="user.password" style="background-color:#CECECE;" />
							</td>
						</tr>
						<tr>
							<td height="40px" colspan="2" align="right">
							<input type="submit" style="visibility:hidden;"/><img src="/imonitor/resources/images/loginbutton.png" onclick="$('#loginform').submit();return false; " style="cursor:pointer;"/>
							</td>
						</tr>
						<tr>
							<td>
							<div class="pageTitle">
									<a class='editlink rulenadd' href="forgetPassword.action" popupheight="200" popupwidth="350" popuptitle="Forgot Password !" 
									title="Forgot Password">Forgot Password</a>
							</div>
							</td>
						</tr>
					</table>
					</s:form>
				</div>
					
			</div>
		</div>
		<div id="editmodal"></div>
				<div id="confirm"></div>
				<div id="message"></div>
	</body>
</html>	