<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page pageEncoding="UTF-8"%>
<%
String applicationName = request.getContextPath();
%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
 
<html>

	<head>
	
<title>iMonitor</title>
    
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

.amazon{
width: 150px;
height: 35px;
color: Black;
background: #fd8f4c;
border: 0px none;
cursor: pointer;
}

.submit_btn{background: #4c90fd;
    color: #fff;
    font-size: 14px;
    width: 141px;
    text-align: center;
    border: 0 none;
    padding: 10px 0;
    margin: 0;
    cursor: pointer;
}

.deny_btn{background: #4c90fd;
    color: #fff;
    font-size: 14px;
    width: 100px;
    text-align: center;
    border: 0 none;
    padding: 10px 0;
    margin: 0;
    cursor: pointer;
}

.tablink {
    background-color: white;
    color: red;
    float: left;
    border: none;
    outline: none;
    cursor: pointer;
    padding: 14px 16px;
    font-size: 17px;
    width: 15%;
}

.forpass{
	background-color: grey;
    color: white;
    border: none;
    outline: none;
    cursor: pointer;
    font-size: 17px;
    padding: 8px 8px;
    width: 100%;
    margin-left: 30px;
    margin-top: -10px;
}

.tablink:hover {
    background-color:#e0e1e2;
    border-left: 6px solid red;
    background-color: lightgrey;
}

input[type="checkbox"] {
  transform:scale(1.5, 1.5);
}
hr {
	clear: both;
    visibility: hidden;
}

.button {
    background-color: #4c8bef; /* Green */
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    margin: 4px 2px;
    cursor: pointer;
    font-size: 14px;
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
		function confirm(){
			
			$(".right_part").hide();
			$(".left_part").hide();
			//$(".second_right_part").show();
			
		}
		function logform(){
			var custid=document.getElementById('username').value;
			var userid=document.getElementById('user').value;
			var password=document.getElementById('pass').value;
			var qs = getQueryStrings();
			var state=qs["state"];
			var scope=qs["scope"];
			var clientid=qs["client_id"];
			if(clientid=="imonitorgooglesmart"){
				var redirecturi=qs["redirect_uri"];
				var type=qs["response_type"];
				var uri="https://www.myhomeqi.com:9443/imonitorapi/google/auth/authcode?custid="+custid+"&userid="+userid+"&password="+password+"&client_id="+clientid+"&redirect_uri="+redirecturi+"&response_type="+type+"&callback=?";
				//var uri="http://10.0.0.16:8083/imonitorapi/google/auth/authcode?custid="+custid+"&userid="+userid+"&password="+password+"&client_id="+clientid+"&redirect_uri="+redirecturi+"&state="+state+"&response_type="+type+"";
				  $.ajax({
				     url:uri,
				     type: "GET",
				     crossDomain: false,
				     contentType: "application/json",
				     dataType: "jsonp",
	                 jsonpCallback: "callback",
	                 jsonp:false,
	                 async :true,
	                 cache    : false,
	                 success:function(data){
	                	 var url=JSON.stringify(data);
	                	 var error="{"+'"error"'+":"+'"message"'+"}";
	                	 if(url==error){
	                		 alert("Incorrect Password or UserId Please check and Try Again");
	                		 location.reload();
	                	 }else{
	                	 url=url.replace("\":\"","=");
	             		url=url.replace("\",\"","&");
	             		url=url.replace("\":\"","=");
	             		url=url.replace("{","");
	             		url=url.replace("}","");
	             		url=url.replace('""','');
	             	    var n = url.substring(url.indexOf("?state"));
	             	    n=n.replace(/"/g,"");
	             		var result="https://oauth-redirect.googleusercontent.com/r/imonitorskill"+n;
	             		 window.location.replace(result);
	                	 }
	                 },  
				      error:function(data,status,errorThrown){
				        	if(errorThrown=="Invalid JSON: error messsage"){
				        		alert("Incorrect Password or UserId Please check and Try Again");
				        	}
				         } 
				 }); 		
			}else{
				
				
			var redirecturi=qs["redirect_uri"];
			var type=qs["response_type"];
			var uri="https://www.myhomeqi.com/imonitorapi/alexaefl/auth/authcode?custid="+custid+"&userid="+userid+"&password="+password+"&state="+state+"&client_id="+clientid+"&redirect_uri="+redirecturi+"&response_type="+type+"";
			
			$.ajax({
				url:uri,
			     type: "GET",
			     crossDomain: false,
			     contentType: "application/json",
			     dataType: "jsonp",
                jsonpCallback: "callback",
                jsonp :false,
                async :true,
                cache : false,
                success:function(data){
                	var url=JSON.stringify(data);
                	console.log("Json Data :"+url);
                	
               	 var error="{"+'"error"'+":"+'"message"'+"}";
               	 if(url==error){
               		 alert("Incorrect Password or UserId Please check and Try Again");
               		 location.reload();
               	 }else{
               	 url=url.replace("\":\"","=");
            		url=url.replace("\",\"","&");
            		url=url.replace("\":\"","=");
            		url=url.replace("{","");
            		url=url.replace("}","");
            		url=url.replace('""','');
            		var code = url.substring(url.indexOf("code"));
     	           
            		code=code.replace(/"/g,"");
	          
	              /*    var index=n.indexOf("&");
	             	var co=n.substr(0,index);
	             	var st=n.substr(index+1); */
	             	
	             //	st=st.replace('?',"&");
	             	var result="https://www.myhomeqi.com/imonitorapi/alexaefl/auth/sample?"+code+"&state="+state+"&redirecturi="+redirecturi;
	             	//var result="https://pitangui.amazon.com/spa/skill/account-linking-status.html?"+co+""+st;
	             	
	             	window.location.replace(result); 
                }
                },
			     error:function(data,status,errorThrown){
			        	if(errorThrown=="Invalid JSON: error messsage"){
			        		alert("Incorrect Password or UserId Please check and Try Again");
			        	}
			         }
			 });
			}
		}
		function callback(data){
			
			
		}
		$(document).ready(function() {
			//$(".second_right_part").hide();
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
			
			//Hide SIgnIn button
			
			/* $("#signin").attr('disabled','disabled');
			$("#signin").css("background","red");
			 $('input[type="checkbox"]').click(function(){
				 $("#signin").removeAttr('disabled');
				 $("#signin").css("background","green");
    		}); */ 
    		
    		$("#signin").attr('disabled','disabled');
			$("#signin").css("opacity","0.5");
    		EnableSubmit = function(val)
    		{
    		    var sbmt = document.getElementById("signin");

    		    if (val.checked == true)
    		    {
    		        sbmt.disabled = false;
    		        $("#signin").css("opacity","1");
    		        $("#signin").removeAttr('disabled');
    		    }
    		    else
    		    {
    		        sbmt.disabled = true;
    		        $("#signin").css("opacity","0.5");
    		        $("#signin").attr('disabled','disabled');
    		    }
    		};
			
			$('#shuffle').cycle({ 
     fx:    'zoom', 
    sync:   0, 
    delay: -4000 
});
		});
		//sumit end: Forgot password user authentication

		 function redirect(){alert("yes");
			var url="http://www.google.com";
			alert(url);
			location.href(url);
		}
		 function getQueryStrings() { 
			  var assoc  = {};
			  var decode = function (s) { return decodeURIComponent(s.replace(/\+/g, " ")); };
			  var queryString = location.search.substring(1); 
			  var keyValues = queryString.split('&'); 

			  for(var i in keyValues) { 
			    var key = keyValues[i].split('=');
			    if (key.length > 1) {
			      assoc[decode(key[0])] = decode(key[1]);
			    }
			  } 

			  return assoc; 
			} 
		 
		 function forgotPassword() {
			
			 $("#divdeps").html('<div align="center">'+
		               '<b>Forgot Your Password ??</b>'+
		               '<table class="logincontant"><tr><td><b>Customer_Id</b></td><td height="40px">'+
		               '<input type="text" class="customer" name="customerid" style="background-color:#CECECE;" /></td></tr>'+
			           '<tr><td><b>User Name</b></td><td height="40px"><input type="text" class="user" name="userid" style="background-color:#CECECE;"/></td></tr></table></div>');
			 $("#divdeps").dialog({
				 stackfix: true,
					position: [500,250],
					modal: true,
					width: 350,
					height:300,
					title: "Password Assistance",
					buttons: {
						Submit:function(){
							
							
							 var customer = $(".customer").val();
							 var user = $(".user").val();
							
							 $("#divdeps").html("Please wait.....");
							 $.ajax({
 					   		     url: "https://myhomeqi.com/imonitorapi/alexaefl/password/generate?customer="+customer+"&user="+user,
 								 success: function(data){
 									$("#divdeps").dialog('close'); 
 									$("#SuccessMessage").html(data);
 									$("#SuccessMessage").dialog({
 										 stackfix: true,
 											position: [500,250],
 											modal: true,
 											width: 350,
 											height:150,
 											buttons: {
 										        'OK' : function() {
 										        	$("#SuccessMessage").html();
 										            $(this).dialog('destroy');
 										           
 										          
 										        }
 										    }
 									});
 							}
 							});
							$("#confirm").dialog("destroy");
							
						}
					},
						close: function (event, ui) {
							   $(this).dialog("destroy");
							}
				});
			}
		 
		 
		 
		 
		</script>	
		
		</head>

<body onload="noBack();">

	<div class="container">
		<div class="header_container">
			<div class="header">
				<div class="logo">
				<a href="#"><img src="<%=applicationName %>/resources/images/efl_logo.png"
						alt="Applane" style=" width: 154px; height: 101px; margin-top: -37px; "/></a>
			<a href="#"><img src="<%=applicationName %>/resources/images/eureka.png"
						alt="Applane" style="     width: 158px;
    height: 26px;
    /* margin-top: -128px; */
    margin: -85px 0px 58px 839px; "/></a>
				</div>
				
			</div>
		</div>

		<div class="body_container">
			<div class="body">
				<div class="left_part">
								
					<div id="shuffle" class="pics">
						<img src="<%=applicationName %>/resources/images/HAS.jpg" alt=" "
							height="400" width="625" />
							<img src="<%=applicationName %>/resources/images/iLiving.jpg" alt=" "
							height="350" width="625" />
					</div>
					
					

				</div>
				<div class="right_part">
					<!-- <form action="login.action" method="post" id="loginform" namespace="/"> -->
						<div class="login">
							<h2>LOG IN</h2>
							<div style="color: red;">
		</div>
							<div class="name">
								<input class="username" 
									id="username" type="text" placeholder="Customer Id"/>
							</div>
							<div class="name">
								 <input class="username" id="user"
									type="text"  placeholder="User Name"/>
							</div>
							<div class="name">
								 <input class="password" id="pass"
									type="password" placeholder="Password"/>
							</div>
							<br />	
							<br />
							<hr size="3" />
							<div>
							<br />
							<input type="checkbox" name="TOS" value="" onclick="EnableSubmit(this)"></input>
							<b>Allow amazon alexa to access your Eurovigil account details and smart home device information?</b>
							</div>			
							<div class="signin_row">
								<div  id="showsignin">
								
									<input type="submit" value="Log In" id="signin" class="signin_btn"
										onclick="logform()"  />
								</div>
								
								
								
								<div class="remember">
									<button  onclick="forgotPassword()" class="forpass" >Forgot Password</button>
								</div>
								
							 <div id="divdeps" style="display:none" title="">
									
								</div> 
								<div id="SuccessMessage">
								</div>
								
							</div>


							
						</div>
						<div>
							<div style="color: #666666;
    margin-left: 214px;
    font-size: 8px;">Powered by</div>
							<div>
							<a href="#"><img src="<%=applicationName %>/resources/images/Logo_grey_Dark.png"
						alt="Applane" style="    width: 92px;
    height: 29px;
    margin-top: -8px;
    margin-left: 258px;"/></a>
							</div>
							
							</div>	
					<!-- </form> -->
					
				</div>
				
			</div>
		</div>
        <div id="editmodal"></div>
		<div id="confirm"></div>
		<div id="message"></div>
		
		<div style="  margin: -23px 0px 0px 500px; float: left; position: absolute; width: 100%;">
		<button class="tablink" width: 147px; onclick=" window.open('/imonitorapi/alexa/auth/terms','_blank')" >Terms and Conditions</button>
		<button class="tablink" onclick=" window.open('/imonitorapi/alexa/auth/privacy','_blank')" >Privacy</button>
		</div>
		
		
	</div>
	
	
	
<div id="resultpage"></div>
</body>
</html>
