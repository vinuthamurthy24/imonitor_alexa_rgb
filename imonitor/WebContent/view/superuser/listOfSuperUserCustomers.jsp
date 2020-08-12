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
		  //SuperUser Changes
		    var customer = "";
			$(document).ready(function() {
				
				//SuperUser Changes
				customer = $('input[name=radioButton]:checked').val();
				
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
			
			function singleCustomerRequest() {
				//var customer = $('.radio').val();
				 customer = $('input[name=radioButton]:checked').val();

	
			     var url = "getDeviceDetailsForSingleSuperUser.action";
				var params = {"superuserName":customer};
				try
				{
					$.ajax({
						url:url,
						cache: false,
						dataType: 'xml',
						data: params,
						success: function(data){
							
							
						  }
					});
					
					
				} 
				catch (e)
				{
					
					alert("Some Exception was caught. : "+ e.message);
				}
				
			    
			}
			
			function multipleCustomerRequest() {
				//var customer = $('.radio').val();
				 customer = $('input[name=radioButton]:checked').val();
			     var url = "getDeviceDetailsForMultipleSuperUser.action";
				var params = {"superuserName":customer};
				try
				{
					$.ajax({
						url:url,
						cache: false,
						dataType: 'xml',
						data: params,
						success: function(data){

						  }
					});

				} 
				catch (e)
				{
					
					alert("Some Exception was caught. : "+ e.message);
				}
				
			    
			}
	
		</script>
		
		
		<style>
.loginSection{
	width: 422px;
	height: 331px;
	margin: -47px 0px 0px 449px;
	background-color:#a09393;
	border-radius: 13px;
	-webkit-box-shadow: 0px 0px 12px rgba(0, 0, 0, 5.4); 
	-moz-box-shadow: 0px 1px 6px rgba(23, 69, 88, .5);
	-webkit-border-radius: 12px;
	-moz-border-radius: 7px; 
	border-radius: 7px;
}
	.supportusername{height: 28px;
width: 277px;
line-height: 28px;
padding: 3px;
margin: 50px 0px 0px 70px;
float: left;	
}
.supportpassword{height: 28px;
width: 277px;
line-height: 28px;
padding: 3px;
margin: 29px 0px 0px 68px;
float: left;	
}
.signin_btn{
background: #4c90fd;
color: #fff;
font-size: 14px;
width: 289px;
text-align: center;
border: 0 none;
padding: 10px 0;
margin: 25px 0px 0px 68px;
float: left;
cursor: pointer;
font-size: x-large;
	}
.display{	
	color: white;
allign: center;
float: center;
width: 691px;
margin: 0px 0px 0px 449px;
}

.circular {
	width: 120px;
    height: 120px;
	border-radius: 150px;
	-webkit-border-radius: 150px;
	-moz-border-radius: 150px;
	/* background: url(../images/imsipl_logo.jpg) no-repeat; */
	}
	
	#radioLable{
		border: none;
  color: black;
  padding: 10px;
  text-align: center;
  text-decoration: none;
  background-color:white;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 8px;
  margin : 0px 0px 1px -1px ;
	}
	
	label{
		border: none;
  color: black;
  padding: 10px;
  text-align: center;
  text-decoration: none;
  background-color:white;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 8px;
  margin : 6px 336px 7px -48px ;
	} 
	
	
	input{
		border: none;
  color: black;
  padding: 10px;
  text-align: center;
  text-decoration: none;
  background-color:white;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 8px;
  margin : 8px 59px 44px 214px ;
	} 
	
	#sub{
	border: none;
  color: black;
  padding: 10px;
  text-align: center;
  text-decoration: none;
  background-color:#cc8080;
  display: inline-block;
  font-size: 25px;
  margin: 4px 2px;
  cursor: pointer;
  width: 154px;
  border-radius: 8px;
  margin : 6px 336px 7px 10px ;
	}
</style>
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="" class="bodyclasssupportlogin">
	<s:form action="getDeviceDetailsForSingleSuperUser" method="post" id="" cssClass="">
	 <div style="width:100%; height:80px;">
		<div class="mainmenu">
					
		</div> 
	 </div>
		<div class="maindivsupport1">
		<h1 class="display">Customers of the Super User</h1>
			<div class="">
			<div style="margin: 17px 0px 0px 589px;"><img src="<%=applicationName %>/resources/images/imsipl_logo.jpg" class="circular"/>
			</div>
				<div class="maindivsupport1"> 
				 <s:iterator value="customersList"> 
    		<s:radio  cssClass="" name="superuserName" list="{myObject}" listKey="customerId" listValue="customerId"/><br/> 
				</s:iterator> 
					
								
								
			</div>
		<tr><td class="locationrow" title=""><input type="radio" onclick="multipleCustomerRequest()" id="radio1"  name="radioButton" value='<s:property value="%{superuser.superUserId}"/>' class="radio" >
		<b>Select All Customers</b></input></td></tr> <br/> 

		<div id="editmodal"></div>
		<div id="confirm"></div>
		<div id="message"></div>
	
	<s:submit id="sub" value="Submit"></s:submit>
	</s:form>
	</body>
</html>	