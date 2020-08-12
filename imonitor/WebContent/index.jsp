<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page pageEncoding="UTF-8"%>
<%
String applicationName = request.getContextPath();
%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<html>
	<head>
<title>iMonitor</title>
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
	  <link type="text/css" href="<%=applicationName %>/resources/css/style.css" rel="stylesheet" />
		<link rel="shortcut icon" href="<%=applicationName %>/resources/images/favicon.ico" type="image/-icon" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />	
	    <script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.5.1.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.js"></script>
		 <script type="text/javascript" src="<%=applicationName %>/resources/js/chili-1.7.pack.js"></script>
         <script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.cycle.all.js"></script>
         <script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.easing.1.3.js"></script>
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />
         <link type="text/css" href="<%=applicationName %>/resources/css/jq.css" rel="stylesheet" />
		 <link type="text/css" href="<%=applicationName %>/resources/css/cycle.css" rel="stylesheet" />
		 
		 <style>
		 * {
         box-sizing: border-box;
           }
		</style>
		 
   <script type="text/javascript">

$.fn.cycle.defaults.speed   = 900;
$.fn.cycle.defaults.timeout = 6000;

   
   
		if(window.history){
			window.history.forward();
		}
		
		function noBack() {
			if(window.history){
				window.history.forward();
			}
		}
		
		
		
		$(document).ready(function() {
		document.getElementById('username').focus();
			$('.ajaxlink').xpAjaxLink({target: 'contentsection'});

			$('.editlink').live('click', function(){
				var targeturl = $(this).attr('href');
				var pHeight = $(this).attr('popupheight');
				var pWidth = $(this).attr('popupwidth');
				var pTitle = $(this).attr('popuptitle');
				if(pHeight == undefined){
					pHeight = 500;
					
				}
				if(pWidth == undefined){
					pWidth = 500;
					
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
			

    
			
//			To show the messages, whenever needed.
			$( "#editmodal" ).dialog({
				height: 350,
				width: 500,
				modal: true,
				autoOpen:false
			});
			//sumit start: Forgot password user authentication
			$('.ajaxinlinepopupform').live('submit', function() {
				 var params = $(this).serialize();
				 $.post($(this).attr('action'), params, function(data){
					var a= $(data).find("p.message").text();
					var aa = a.split(":");
					if(aa[0] =='Failure'){
						//$("#ruleMessage").html(aa[1]);
						$("#message").html(aa[1]);
						$("#message").dialog('open');
						$('#editmodal').dialog('destroy');
					}else{
						$('#editmodal').dialog('destroy');
						$('#editmodal').html(data);
						$('#editmodal').dialog({
							height: 470, 
							width: 670, 
							title: 'The page says...', 
							modal:true,
							open: function(event, ui) 
							{			 
								$(this).parent().children().children('.ui-dialog-titlebar-close').show();
							},
							buttons: {
								Ok: function() {
										$(this).dialog('close');
									}
								},
						});
					}
				 });
				 return false;
			});
			
			$('.resetPassword').live('submit', function() {
				var params = $(this).serialize();
				$.post($(this).attr('action'), params, function(data){
					var a= $(data).find("p.message").text();
					var aa = a.split(":");
					$('#editmodal').dialog('destroy');
					if(aa[0] == 'Failure'){
						//$("#ruleMessage").html(aa[1]);
						$("#message").html(aa[1]);
						$("#message").dialog('open');
					}else if(aa[0] == 'Success'){
						$("#message").html(aa[1]);
						$("#message").dialog('open');
					}
				});
				return false;
			});
			// Validation of form ends here.
			$( "#message" ).dialog({
				height: 200,
				width: 350,
				modal: true,
				autoOpen:false,
				buttons: {
					Ok: function() {
							$(this).dialog('close');
						}
					}
			});
			
			$('#shuffle').cycle({ 
     fx:    'zoom', 
    sync:   0, 
    delay: -4000 
});
		});
		//sumit end: Forgot password user authentication

		
		
		</script>	
		
		</head>

<body onload="noBack();">

	<div class="container">
		<div class="header_container">
			<div class="header">
				<div class="logo">
					<a href="#"><img src="<%=applicationName %>/resources/images/logoBlack.png"
						alt="Applane" style=" width: 255px; height: 78px; margin-top: -32px; "/></a>
				</div>
				<h1 class="blue" style="font-size: 24px; float: right; margin: -70px 0px 0px 0px;"">MAKING HOMES INTELLIGENT</h1>
			</div>
		</div>

		<div class="body_container">
			<div class="body">
				<div class="left_part">
					

					
					<div id="shuffle" class="pics">
						<img src="<%=applicationName %>/resources/images/image for develop.bmp" alt=" "
							height="400" width="625" />
							<img src="<%=applicationName %>/resources/images/bg1.jpg" alt=" "
							height="400" width="625" />
							<img src="<%=applicationName %>/resources/images/bg2.jpg" alt=" "
							height="400" width="625" />
							<img src="<%=applicationName %>/resources/images/bg3.jpg" alt=" "
							height="400" width="625" />
							<img src="<%=applicationName %>/resources/images/bg4.jpg" alt=" "
							height="400" width="625" />
							<img src="<%=applicationName %>/resources/images/bg5.jpg" alt=" "
							height="400" width="625" />
						</div>
                    			
					
					

				</div>
				<div class="right_part">
					<form action="login.action" method="post" id="loginform" namespace="/">
						<div class="login">
							<h2>LOG IN</h2>
							<div style="color: red;"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
		</div>
							<div class="name">
								<input class="username" name="user.customer.customerId"
									id="username" type="text" placeholder="Customer Id"/>
							</div>
							<div class="name">
								 <input class="username" name="user.userId"
									type="text"  placeholder="User Name"/>
							</div>
							<div class="name">
								 <input class="password" name="user.password"
									type="password" placeholder="Password"/>
							</div>				
							<div class="signin_row">
								<div class="signin">
								
									<input type="submit" value="Log In" class="signin_btn"
										onclick="$('#loginform').submit();return false;"  />
								</div>
								<div class="remember">
									<a class='editlink rulenadd' href="forgetPassword.action" popupheight="250" popupwidth="400" popuptitle='<s:text name="setup.user.forget.pass.tile" />' 
									title="Forgot Password" style=" text-decoration: none; font-size: 13px; "><s:text name="login.forgotpass"/></a>
								</div>
								
							</div>

                            <%-- <div class="">
									<a class=''  target ='_blank' href="RegisterZingGateway.action" popupheight="600" popupwidth="600" popuptitle='<s:text name="Register Zing Gateway" />' 
									title="Register Zing" style=" text-decoration: none; font-size: 30px; margin-left: 30%; margin-top:50%; background-color:silver; color: black; "><s:text name="Click here for signup/register"/></a>
								</div>  --%>
							
						</div>
						
									
						<span><a class=''   target ='_blank' style="text-decoration: none; color: black; font-size: 20px;font-family: initial;color: crimson; margin: 0px 0px 0px 52px;" href="RegisterZingGateway.action" title="Register Zing" style=" text-decoration: none; color: black; "><s:text name="Click here for signup/register"/></a></span>
					</form>
					
				</div>

			</div>
		</div>
		

		
        <div id="editmodal"></div>
		<div id="confirm"></div>
		<div id="message"></div>
		<div class="footer_container">
			<div class="footer">
				<div style="  margin: 0px 0px 0px 34px; float: left; position: absolute; width: 100%;  ">	
<!-- Codes by HTML.am -->
<h2 style="margin: 0px 0px 3px 0px;width: 147px;"><li style="font-family:Times New Roman;">Security</li></h2>
<h2 style="margin: -45px 0px 0px 114px;width: 147px;"><li style="font-family:Times New Roman; ">Elder Care</li></h2>
 <h2 style="margin: -43px 0px 0px 264px;width: 210px;"><li style="font-family:Times New Roman; ">Home Automation</li></h2>
 <h2 style=" margin: -42px 0px 0px 482px; width: 242px;"><li style="font-family:Times New Roman; ">Energy Management</li></h2>
 <h2 style=" margin: -42px 0px 0px 723px; width: 270px;"><li style="font-family:Times New Roman; ">Audio/Video Integration</li></h2>
</div>
			</div>

		</div>
	</div>



</body>
</html>
