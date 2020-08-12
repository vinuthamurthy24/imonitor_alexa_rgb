<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%
String applicationName = request.getContextPath();
%>
<html>
	<head>
		<title>
			Setup Page ...
		</title>
		
		<link rel="shortcut icon" href="<%=applicationName %>/resources/images/favicon.ico" type="image/-icon" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/imonitor.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/demo_table.css" />
		
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.5.1.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.user.js"></script>
		<style>
			.deviceicon{
				width:45px;
				height:45px;
				margin-top:5px;
			}
			
		
	
	.dataTables_length {
	width: 30%;
	float: left;
	margin-left: -75px;
	margin-top: 12px;
	}

	.dataTables_info {
	width: 60%;
	float: left;
	margin-top: 10px;
	margin-left: -220px;
}
	

						
		</style>
		<script>
		var DEVICE_TYPE_WISE = "DEVICE_TYPE_WISE";
		var LOCATION_WISE = "LOCATION_WISE";
		$(document).ready(function() {
			$('.ajaxlink').xpAjaxLink({target: 'contentsection'});

			var listScenarioAlerts = function(){
				var count = endCount*1;
				if(count <= 5){
				$.ajax({
					url: "listImvgAlerts.action",
					cache: false,
					dataType: 'xml',
					success: handleAlertSuccess
				});
				}else{
						$.ajax({
							url: "listImvgAlerts.action",
                            cache: false,
                            dataType: 'xml',
                            success: function(xml){
                            var totalcount =$(xml).find('totalcount').text();
                            var first = $('#rowdetails').find('b:first').text();
                            var second = $('#rowdetails').find('b:nth-child(2)').text();
                            var last = $('#rowdetails').find('b:last').text();
                            var difference =((totalcount*1)-(last*1));
                            var firstvalue = ((first*1)+(difference*1));
                            var secondvalue = ((second*1)+(difference*1));
                            $('#rowdetails').find('b:first').html(firstvalue);
                            $('#rowdetails').find('b:nth-child(2)').html(secondvalue);
                            $('#rowdetails').find('b:last').html(totalcount);
							}
							
						});
					}
					setTimeout(listImvgAlerts,10000);
			};

			
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

			$(".actionselect").live('change', function(){
				var currentSelect=$(this);
				var selectedVal=currentSelect.val();
				 if(selectedVal*1 ==12) 
			        {
					 $(this).parent().parent().find(".valuefordimmer").hide().val('');
			        }
				 if(selectedVal*1 ==11) 
				 {
					 $(this).parent().parent().find(".valuefordimmer").show();
					 $(this).parent().parent().find(".valuefordimmer").attr('devicetype','2');
			        }
			});
			
			$(".valuefordimmer").live('change', function(){
				var currentSelect=$(this);
				var devicetype = currentSelect.attr('devicetype');
				var selectedVal=currentSelect.val();
				var errormessage = "";
				if((devicetype*1)==1){
					var errormessage = "Please enter the value range between(0-99).";
					var intRegex = /^\d+$/;
					 if(!intRegex.test(selectedVal)) 
				        {
						 showResultAlert(errormessage);
				        $(this).val('0');
				        }else{	
					if(selectedVal*1>99){
						showResultAlert(errormessage);
						 $(this).val('99');
						}
					if(selectedVal*1<0){
						showResultAlert(errormessage);
						 $(this).val('0');
						}
				        }
					}
				
			if((devicetype*1)==2){
				var intRegex = /^\d+$/;
				var errormessage = "Please enter the value range between(16-30).";
				 if(!intRegex.test(selectedVal)) 
			        {
					 showResultAlert(errormessage);
			        $(this).val('16');
			        }else{	
				if(selectedVal*1>30){
					showResultAlert(errormessage);
					$(this).val('30');
					}
				if(selectedVal*1<16){
					showResultAlert(errormessage);
					$(this).val('16');
					}
			        }				
				}
			});
			$('.removedevicelink').live('click', function(){
				var targeturl = $(this).attr('href');
				$("#confirm").dialog("destroy");
				$("#confirm").html('Are you sure you want to remove this device ?');
				$("#confirm").dialog({
					stackfix: true,
					modal: true,
					buttons: {
						Ok: function() {
							$(this).dialog('close');
							$.ajax({
								  url: targeturl,
								  success: function(data){
								  }
								});
							$("#message").html("Your request to remove the device has send to your iMVG. Please remove the device from iMVG and refresh the page.");
							$("#message").dialog('open');
							$("#message" ).dialog({
								stackfix: true,
								modal: true,
								buttons: {
								Ok:function(){
									$(this).dialog('close');
								}
							}
							});
						},
						Cancel: function() {
							$(this).dialog('close');
						}

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
			$('.ajaxinlinepopupform').live('submit', function() {
				 var params = $(this).serialize();
				 $.post($(this).attr('action'), params, function(data){
					var a= $(data).find("p.message").text();
					var aa = a.split(":");
					if(aa[0] =='Failure'){
						$("#ErrorMessage").html(aa[1]);
						}
					else{
					 $('#contentsection').html(data);
					 $('#editmodal').dialog('close');
					 var message = $("#messageSection").html();
					 if(message == "" || message == null || message == undefined){
						 message = "Operation Completed.";
					 }
					 showResultAlert(message);
					}
				 });
				 return false;
			});

			$('.ajaxfileuploadformpopup').live('submit', function() {
				var dataTableId = $(this).attr("datatableid");
				var dataTableUrl = $(this).attr("datatableurl");
				$.covertToAjaxFileUpload(this,{successhandler:editAndReloadTable, dataTableId:dataTableId, dataTableUrl:dataTableUrl});
				return true;
				function editAndReloadTable(event){
					var data = $(this).contents().find('body').html();
					 //$('#contentsection').html(data);					 
					$('#editmodal').dialog('close');	
					$("#message").html(data);
					$("#message").dialog('open');
					$('#' + event.data.dataTableId).dataTable({
						"bProcessing": true,
						"bServerSide": true,
						"bDestroy": true,
						"sScrollY": 300,
						"sScrollX": "100%",
						"sScrollXInner": "110%",
						"sAjaxSource": event.data.dataTableUrl
					});
				 }
				 return false;
			});

			$('.executeScenarios').live('click', function() {
				var dataTableUrl = $(this).attr("dataTableUrl");
				var dataTableId = $(this).attr("dataTableId");
				$("#confirm").dialog("destroy");
				$("#confirm").html('Are you sure you want to execute it ?');
				var href = $(this).attr('href');
				$("#confirm").dialog({
						stackfix: true,
						modal: true,
						buttons: {
							Ok: function() {
								$(this).dialog('close');
								
					 $.get(href,function(data){
						$('#message').html(data);
						$('#message').dialog('open');
					 });
							},
							Cancel: function() {
								$(this).dialog('close');
							}

						}
					});
					 return false;
				});
			
			$('.deleteandreloadtablelink').live('click', function() {
				var dataTableUrl = $(this).attr("dataTableUrl");
				var dataTableId = $(this).attr("dataTableId");
				$("#confirm").dialog("destroy");
				$("#confirm").html('Are you sure you want to delete it ?');
						
				var href = $(this).attr('href');
				$("#confirm").dialog({
						stackfix: true,
						modal: true,
						buttons: {
							Ok: function() {
								$(this).dialog('close');
								
					 $.get(href,function(data){
						$('#message').html(data);
						$('#message').dialog('open');
						$("#messageSection").html("");
						$('#' + dataTableId).dataTable({
								"bProcessing": true,
								"bServerSide": true,
								"bDestroy": true,
								"sScrollY": 300,
								"sScrollX": "100%",
								"sScrollXInner": "100%",
								"sAjaxSource": dataTableUrl
							});
					 });
							},
							Cancel: function() {
								$(this).dialog('close');
							}

						}
					});
					 return false;
				});
			
			// Validation of form starts here.
			$('form').live('change', function() {
				Xpeditions.validateForm($(this));
			});
			$('form').live('keyup', function(e) {
				var isEverythingOK = Xpeditions.validateForm($(this));
				if(isEverythingOK){
					if (e.keyCode == 13) {
						$(this).submit();
					}

				}
			});
			// Validation of form ends here.
			$( "#message" ).dialog({
				height: 100,
				width: 200,
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
	<body class="bodyclass">
		<div class="maindiv">			
			<div class="titlebg">
				<div class="welcomeuser">
					<span style="color:#0C5AA3;">Welcome</span> <s:property value="#session.user.userId"/>
				</div>
				<div>
					<ul class="ulmenu topmenu" lihoverclass="hoverstyle">
						<li class="menuitem"><a href="home.action" class="menuitemnormal">Home </a></li>
						<!--<li class="menuitem"><a href="cameras.action" class="menuitemnormal">cameras </a></li>
						<li class="menuitem"><a href="preferences.action" class="menuitemnormal">preferences</a></li>
						-->
						<li class="menuitem " ><a href="setup.action" class="menuitemnormal">Setup </a></li>
						<li class="menuitem selectedtopmenu" ><a href="scenario.action" class="menuitemnormal">Scenario </a></li>
						<!--<li class="menuitem"><a href="#" class="menuitemnormal">account </a></li>
						--><li class="menuitem"><a href="../logout.action" class="menuitemnormal">Sign out</a></li>					
					</ul>
				</div>
					<img class="logo" src="<%=applicationName %>/resources/images/logo.png"/>											
			</div>
			<div class="menuitemslistbg">
			</div>		
				<div id="contentsection" class="contentsectionsenario">
				<s:include value="/view/mainuser/scenario/scenarios.jsp"></s:include>
				
				</div>				
				<div id="editmodal"></div>
				<div id="confirm"></div>
				<div id="message"></div>
			<div class="boottom">					
			</div>				
		</div>
			
	</body>
</html>