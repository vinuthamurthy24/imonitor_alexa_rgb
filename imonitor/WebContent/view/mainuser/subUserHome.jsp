 <%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
String applicationName = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<html>
	<head>
		<title>
			imonitor - <s:property value="#session.user.userId"/> 
		</title>
		<link rel="shortcut icon" href="<%=applicationName %>/resources/images/favicon.ico" type="image/-icon" />		
		<link type="text/css" href="<%=applicationName %>/resources/css/imonitor.css" rel="stylesheet" />
		<link type="text/css" href="<%=applicationName %>/resources/css/jquery-ui.css" rel="stylesheet" />
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />
		<!-- <link type="text/css" href="<%=applicationName %>/resources/css/demos.css" rel="stylesheet" /> -->
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/jquery.ui.all.css" rel="stylesheet" />
		<link type="text/css" href="<%=applicationName %>/resources/css/demo_table.css" rel="stylesheet"/>
	
		<link type="text/css" href="<%=applicationName %>/resources/css/TableTools.css" rel="stylesheet" />
		
		
		<!--<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.5.1.js"></script>-->
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.7.1.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.mouse.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.slider.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.user.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.min.js"></script>
		<!-- sumit start -->
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.sortable.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui-1.8.21.custom.js"></script>
		<!-- sumit end -->
		
		
		
		<style>
			#slider  .ui-slider-range { background: #729fcf; }
			#slider  .ui-slider-handle { border-color: #729fcf; }

			#slider1  .ui-slider-range { background: #729eaf; }
			#slider1  .ui-slider-handle { border-color: #729eaf; }

			.executeScenarios.ui-dialog-titlebar-close {
			visibility: hidden;
			}
			.no-close .ui-dialog-titlebar-close {display: none }
			
		</style>
		<%@include file="./messages.jsp" %>
		<script>
		
		if(window.history){
			window.history.forward();
		}
		
		function noBack() {
			if(window.history){
				window.history.forward();
			}
		}
		
		var userDetails = null;
		var imvgDetails = new ImvgDetals();
		var selectedGateWay = "";
		var DEVICE_TYPE_WISE = "DEVICE_TYPE_WISE";
		var LOCATION_WISE = "LOCATION_WISE";
//vibhu start
		var SCENARIO_WISE = "SCENARIO_WISE";
		var DEVICE_DIAGNOSTIC_WISE="DEVICE_DIAGNOSTIC_WISE";
		var selectedWiseType = LOCATION_WISE;
		var refreshInterval=10000;
//vibhu end		
		var endCount = null;
		var Path="";
		var SystemIntegratorName="";
		var SystemIntegratorMobile="";
		var SystemIntegratorLogo="";
		var TimeOutValues =  {};
		
		$(document).ready(function() {
			
			
		Path='<s:property value="imvgUploadContextPath"/>';
		
		SystemIntegratorName = '<s:property value="#session.user.customer.systemIntegrator.name"/>';
		SystemIntegratorMobile ='<s:property value="#session.user.customer.systemIntegrator.mobile"/>';
		SystemIntegratorLogo = '<s:property value="#session.user.customer.systemIntegrator.logo"/>';
		
		
				

		
		
	
	
		$( "#radio" ).buttonset();
		userDetails = new UserDetails();
			//var overlay = Xpeditions.createOverlaySection ($("#maindiv"));
			var handleGateWay = function(gWay){
				var gateWay = $(gWay);
				var macId = gateWay.find("macid").text();
				var status = gateWay.find("status").text();
				var curMode = gateWay.find("currentMode").text();
                var featuresEnabled = gateWay.find("featuresEnabled").text();
                var gateWayVersion = gateWay.find("gateWayVersion").text();
                var Latestversion = gateWay.find("Latestversion").text();
                var FirmwareId = gateWay.find("FirmwareId").text();
                var AllOnOff = gateWay.find("allOnOff").text();
                userDetails.addGateWay(macId);
				//userDetails.setGateWayStatus(macId, status);
				userDetails.setGateWayStatusAndMode(macId, status, curMode,Latestversion,FirmwareId,gateWayVersion,AllOnOff);
				// Handling devices.
				//naveen added to fix bug 1353- even if dashboard is disabled from admin it was displaying in jsp
				if((featuresEnabled & 4)==0){		
                    $('#dashboard').hide();				 
					$('#dashboard').addClass('displayclass');
						}
					else if((featuresEnabled & 4)==4)
					{
					  $('#dashboard').show();
					$('#dashboard').removeClass('displayclass');
					}
				if((featuresEnabled & 1)==0){
					$(".diagnosticwiserow").hide();
					
				}
				else if((featuresEnabled & 1)==1)
				{
					$(".diagnosticwiserow").show();
				}
				var devices = gateWay.find("device");
				devices.each(function(index, deviceObj) {
					userDetails.setDeviceDetails(macId, this);
					if((featuresEnabled & 1)==0){
						$(".diagnosticwiserow").hide();
						
					}
					else if((featuresEnabled & 1)==1)
					{
						$(".diagnosticwiserow").show();
					}
					
					if((featuresEnabled & 2)==0){						
						$(".addpreset").hide();
						}
					
					if((featuresEnabled & 4)==0){	
						$('#dashboard').hide();				 
						$('#dashboard').addClass('displayclass');
						}
					else if((featuresEnabled & 4)==4)
					{
						
				    $('#dashboard').show();
					$('#dashboard').removeClass('displayclass');
					}
					
				});
				
			};

			var handleSuccessForLocation = function(xml){
				
				// 1. Handling the storage.
				var storage = $(xml).find("storage");
				userDetails.setStorageDetails(storage);
				userDetails.UserInformationHtml(storage,SystemIntegratorName,SystemIntegratorMobile,SystemIntegratorLogo,Path);
				
				// 2. Handling the gatewy.
				userDetails.setGateWayCount($(xml).find("gateway").size());
				$(xml).find("gateway").each(function() {
					handleGateWay(this);
				});
				userDetails.generateGateWayHtml();

				//Generating select boxes.
				
				userDetails.generateSelectBoxes(Path);
				// Generating the html contents.
				var pressed=$("#radioLable").attr('aria-pressed');
				if(pressed == 'false')
				{
					userDetails.generateHtmlForLocation();
				}		
				//userDetails.generateHtmlForLocation();
				//overlay.remove();
			};
			//bhavya start
			var handleSuccessForDiagnosis = function(xml){
				// 1. Handling the storage.
				var storage = $(xml).find("storage");
				
				userDetails.setStorageDetails(storage);
				userDetails.UserInformationHtml(storage,SystemIntegratorName,SystemIntegratorMobile,SystemIntegratorLogo,Path);
	
				// 2. Handling the gatewy.
				userDetails.setGateWayCount($(xml).find("gateway").size());
				$(xml).find("gateway").each(function() {
					handleGateWay(this);
				});
				userDetails.generateGateWayHtml();

			
				// Generating the html contents.
				userDetails.generateHtmlForDeviceDiagnosis();
				//overlay.remove();
			};
			//bhavya end
			var handleSuccess = function(xml){
				// 1. Handling the storage.
				var storage = $(xml).find("storage");
				
				//var serializer = new XMLSerializer();
				//var string = serializer.serializeToString(storage); 
				//alert(string);
				
				
				userDetails.setStorageDetails(storage);
				userDetails.UserInformationHtml(storage,SystemIntegratorName,SystemIntegratorMobile,SystemIntegratorLogo,Path);
				
				// 2. Handling the gatewy.
				userDetails.setGateWayCount($(xml).find("gateway").size());
				$(xml).find("gateway").each(function() {
					handleGateWay(this);
				});
				
				userDetails.generateGateWayHtml();
				//Generating select boxes.
				userDetails.generateSelectBoxesForDevice();
				// Generating the html contents.
				userDetails.generateHtml();
				//overlay.remove();
			};
			
//vibhu start			
			var handleScenarioSuccess = function(xml){
				// 1. Handling the storage.
				var storage = $(xml).find("storage");
				userDetails.setStorageDetails(storage);
				userDetails.UserInformationHtml(storage,SystemIntegratorName,SystemIntegratorMobile,SystemIntegratorLogo,Path);
				
				
				// 2. Handling the gatewy.
				userDetails.setGateWayCount($(xml).find("gateway").size());
				$(xml).find("gateway").each(function() {
					handleGateWay(this);
				});
				
				userDetails.generateGateWayHtml();

				//Generating select boxes.
				//userDetails.generateSelectBoxes();
				
				// Handling the Scenarios.
				userDetails.clearScenarios();
				$(xml).find("scenario").each(function() {
					userDetails.setScenarioDetails(this);
				});
				userDetails.generateScenarioHtml();
				
				
				//userDetails.generateHtml();
				//overlay.remove();
			};
			//sumit start: Device Diagnosis Prototype
			var generateDiagnosisHtml = function(){
				var lUrl = "userdetails.action";
				$.ajax({
					url: lUrl,
					cache: false,
					dataType: 'xml',
					success: handleSuccessForDiagnosis
				//userDetails.generateHtmlForDeviceDiagnosis();
				});
			};
			//sumit end: Device Diagnosis Prototype

			var showUserDetailsByLocations = function(){
				var lUrl = "userdetails.action";
				$.ajax({
					url: lUrl,
					cache: false,
					dataType: 'xml',
					success: handleSuccessForLocation
				});
			};

			var showUserDetails = function(){
				var lUrl = "userdetails.action";
				$.ajax({
					url: lUrl,
					cache: false,
					dataType: 'xml',
					success: handleSuccess
				});
			};
			
			var showScenarioDetails = function(){
				var lUrl = "scenariodetails.action";
				//alert(lUrl);
				$.ajax({
					url: lUrl,
					cache: false,
					dataType: 'xml',
					success: handleScenarioSuccess
				});
			};

			var viewRefresher = function(){
				if(selectedWiseType == SCENARIO_WISE)
				{
					showScenarioDetails();
				}
				else if(selectedWiseType == LOCATION_WISE)

				{
					showUserDetailsByLocations();
				}
				else if(selectedWiseType == DEVICE_DIAGNOSTIC_WISE)
				{
					generateDiagnosisHtml();
				}
				else
				{
					showUserDetails();
				}
				setTimeout(viewRefresher,refreshInterval);
			};
			
			
			

			viewRefresher();
//vibhu end			
			$(".previousnextLink").live('click',function(){
				//$(this).html('<img src="../resources/images/loading.gif" class="armclass"/>');
				var url = $(this).attr('href');
				var displayStart = $(this).attr('displayStart');
				var params = {"displayStart":displayStart};
				$.ajax({
					url:url,
					cache: false,
					dataType: 'xml',
					data: params,
					success: handleAlertSuccess
				});
				setTimeout(listImvgAlerts,10000);
				return false;
			});

			
			var handleAlertSuccess = function(data){
				$('#imvgalerttable').find('tbody').text('');
				$('#rowdetails').text('');
				$('#nextlinkdiv').text('');
				$('#previouslinkdiv').text('');
				var startcount =$(data).find('startcount').text();
				var endcount =$(data).find('endcount').text();
				var totalcount =$(data).find('totalcount').text();
				var rowSize = $(data).find('rowSize').text();
				var displayrow = $(data).find('displayrow').text();
				var displayStart = $(data).find('displayStart').text();
				endCount = endcount;
				$(data).find("message").each(function() {
					var time = $(this).find('time').text();
					var devicename = $(this).find('devicename').text();
					//var alertdetails = $(this).find('alert').text();
					var alertcommand = $(this).find('alertcommand').text();
					var alertcom = alertcommand;
					if(alertcom == 1)alertcom = "STREAM_FTP_SUCCESS";
					if(alertcom == 2)alertcom = "IMAGE_FTP_SUCCESS";
					var alertdetails = formatMessage(alertcom);
					var rulecount = $(this).find('rulecount').text();
					var imvgalertid = $(this).find('imvgalertid').text();
					var deviceid = $(this).find('deviceid').text();
					var Alertid = $(this).find('alertid').text();
					var Alertval = $(this).find('alertValue').text();

					var Colour = $(this).find('colour').text();
					var deviceArr = new Array();
					var alertsArr = new Array();
					var alarmStatus = $(this).find('alarmStatus').text();

					
					if(alertcommand=='POWER_INFORMATION')
					{
					var AlertValue = $(this).find('alertValue').text();
					var colon=':';
					}
					else
					{
					AlertValue=[];
					colon=[];
					}

					$(data).find("messagealerts").each(function() 
					{
						var device = $(this).find('device').text();
						var alerts = $(this).find('alerts').text();
						deviceArr.push(device);
						alertsArr.push(alerts);
					});

					var attachment ="";
					var i=0;
					for (i=0;i<deviceArr.length;i++)
					{
						
						if(deviceArr[i]==deviceid)
						{
							var alerts=alertsArr[i];
							var temp = new Array();
							temp = alerts.split(', ');
							var x=0;
							for(x=0;x<temp.length;x++)
							{
								if(temp[x]==Alertid)
								{
									
								}
							}	
						
						}
			
					}

					if((alertcommand*1) == 1){
						 attachment = "<a class='imvgalertStream'  href='isStreamAttachment.action' alertFromImvgId="+imvgalertid+" deviceId="+deviceid+" style='border: none;text-decoration: none;'><img title='click to view video' src='/imonitor/resources/images/videoicon.PNG' style='float: left;'></a>";
					}
					if((alertcommand*1) == 2){
						attachment = "<a class='imvgalertImage'  href='isAttachment.action' alertFromImvgId="+imvgalertid+" deviceId="+deviceid+" style='border: none;text-decoration: none;'><img title='click to view image' src='/imonitor/resources/images/attachment.gif' style='float: left;'></a>";
					}
				
					var tablerows="";
					if(Colour >= 60)
					{
						tablerows="<tr  id='check' class='alertstabledatas'><td style='text-align: right;'>"+attachment+time+"</td><td style='text-align: left;'>"+devicename+"</td><td style='text-align: left;'>"+alertdetails+colon+AlertValue+"</td><td><input type='hidden' value='"+imvgalertid+"' id='alertExpressions' /></td></tr>";
					}
					if(Colour >= 10 && Colour < 60)
					{
						tablerows="<tr  id='check' class='alertstabledatas1' ><td style='text-align: right;'>"+attachment+time+"</td><td style='text-align: left;'>"+devicename+"</td><td style='text-align: left;'>"+alertdetails+colon+AlertValue+"</td><td><input type='hidden' value='"+imvgalertid+"' name='alertExpressions' /></td></tr>";
					}
					if(Colour >= 5 && Colour < 10)
					{

						tablerows="<tr id='check' class='alertstabledatas2' ><td style='text-align: right;'>"+attachment+time+"</td><td style='text-align: left;'>"+devicename+"</td><td style='text-align: left;'>"+alertdetails+colon+AlertValue+"</td><td><input type='hidden' value='"+imvgalertid+"' name='alertExpressions' /></td></tr>";
					}
					if(Colour > 1 && Colour < 5)
					{

						tablerows="<tr id='check' class='alertstabledatas3' ><td style='text-align: right;'>"+attachment+time+"</td><td style='text-align: left;'>"+devicename+"</td><td style='text-align: left;'>"+alertdetails+colon+AlertValue+"</td><td><input type='hidden' value='"+imvgalertid+"' name='alertExpressions' /></td></tr>";
					}
					if(Colour <= 1)
					{	
						if(alarmStatus>1)
						{							
								tablerows="<tr id='check' class='alertstabledatas3' ><td style='text-align: right;'>"+attachment+time+"</td><td style='text-align: left;'>"+devicename+"</td><td style='text-align: left;'>"+alertdetails+colon+AlertValue+"</td><td><input type='hidden' value='"+imvgalertid+"' id='alertExpressions' /></td></tr>";
																						
						}else{
							tablerows="<tr id='check' class='alertstabledatas4' ><td style='text-align: right;'>"+attachment+time+"</td><td style='text-align: left;'>"+devicename+"</td><td style='text-align: left;'>"+alertdetails+colon+AlertValue+"</td><td><input type='hidden' value='"+imvgalertid+"' id='alertExpressions' /></td></tr>";
						}
						
					}		
					$('#imvgalerttable').find('tbody').append(tablerows);
				});
					
				if((endcount*1)>displayrow){
					var previousvalue = (displayStart*1)-(displayrow*1);
					var previouslink ="<a class='previousnextLink' href='listImvgAlerts.action' displayStart="+previousvalue+"><img title='Newer' src='/imonitor/resources/images/back_enabled.jpg'></a>";

				}else{
					var previouslink ="<img src='/imonitor/resources/images/back_disabled.jpg' title='inactive' >";
					}
					$('#previouslinkdiv').append(previouslink);
					if((totalcount*1)>endcount){
					var value = (displayStart*1)+(displayrow*1);
						var nextlinkdiv ="<a class='previousnextLink' href='listImvgAlerts.action' displayStart="+value+"><img title='Older' src='/imonitor/resources/images/forward_enabled.jpg'></a>";

					}else{
						var nextlinkdiv ="<img src='/imonitor/resources/images/forward_disabled.jpg' title='inactive'>";
						}
						$('#nextlinkdiv').append(nextlinkdiv);
						var rowdetails ="<span><b>"+startcount+"</b>-<b>"+endcount+"</b>&nbsp;&nbsp;of&nbsp;&nbsp;<b>"+totalcount+"</b></span>";
					$('#rowdetails').append(rowdetails);
				
				// $('#alertbodysection').html(data);
			};
			
			var listImvgAlerts = function(){
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
			listImvgAlerts();
			$('.switchon').live('mouseup', function() {
				var imgsrc = "../resources/images/onswitchnormal.png";
				$(this).attr('src',imgsrc);
			}).live('mousedown', function() {
				var imgsrc = "../resources/images/onoverswitch.png";
				$(this).attr('src',imgsrc);
			});
		//	bhavya
			$('.switchon1').live('mouseup', function() {
				var imgsrc = "../resources/images/loading.gif";
				$(this).attr('src',imgsrc);
			}).live('mousedown', function() {
				var imgsrc = "../resources/images/loading.gif";
				$(this).attr('src',imgsrc);
			});
			//bhavya
			$('.widgetLocation').live('mouseover', function() 
			{
				$(this).removeClass('color-white');
				$(this).addClass('color-gray');
				$("#deviceListUl").addClass('ActivateOrdering');

				
			});
			
			$('.widgetLocation').live('mouseout', function(){
				$(this).removeClass('color-gray');
				$(this).addClass('color-white');
				$("#deviceListUl").removeClass('ActivateOrdering');
			});


		
			$('.switchoff').live('mouseup', function() {
				var imgsrc = "../resources/images/offnormalswitch.png";
				$(this).attr('src',imgsrc);
			}).live('mousedown', function() {
				var imgsrc = "../resources/images/offmouseoverswitch.png";
				$(this).attr('src',imgsrc);
			});
			
			
			$('.swingoff').live('mouseup', function() {
				var imgsrc = "../resources/images/swingoff.png";
				$(this).attr('src',imgsrc);
			}).live('mousedown', function() {
				var imgsrc = "../resources/images/swingon.png";
				$(this).attr('src',imgsrc);
			});
			
				var CheckClass = function(){
				//alert($('#check').hasClass('alertstabledatas4'));
				$('#imvgalerttable tr').each(function() 
				{
    			
					//alert($(this).hasClass('alertstabledatas4'));    
 				if($(this).hasClass('alertstabledatas4'))
				{
					$(this).removeClass('alertstabledatas4');
					$(this).addClass('alertstabledata');

				}
				else
				{
					if($(this).hasClass('alertstabledata'))
					{
						$(this).addClass('alertstabledatas4');
						$(this).removeClass('alertstabledata');
					}
				}		
				});
				
				/*if($('#check').hasClass('alertstabledatas4'))
				{
					$('#check').removeClass('alertstabledatas4')
				}
				else
				{
					$('#check').addClass('alertstabledatas4')
				}	*/			
				setTimeout(CheckClass,1000);
			};
			
			CheckClass();

			$(".alertstabledatas4").live('click', function(){
			
				$(this).removeClass('alertstabledatas4');
				$(this).addClass('alertstabledatas3');
				var params = {"alertId":$(this).find("input:first").val()};			
				var href="updatealert.action";
						$.ajax({
							url:href,
							data:params,
							cache:false,
							success: function(data)
							{					
			  				}
							});				
				return false;				
			});
			
			$(".timeoutvalueclass").live("keyup",function(){
			
			     var cValue = $(this).val();
				 var deviceId = $(this).attr('deviceId');
				 if(cValue>=60)
				 {
				  message = 'Please enter value less than or equal to 60';
				  showResultAlertOnHome(message);
				  $(this).val("");
				  return ;
				 }
				 if(cValue == "")
				 {
				delete TimeOutValues[deviceId];
				 }
				 else
				 {
				 TimeOutValues[deviceId]=cValue;
				 }
				$('.updatedevicelink').attr('Devicetimeout', cValue);
			});
			
			
			
			$(".Dimmertimeoutvalueclass").live("keyup",function(){
				
			     var cValue = $(this).val();
				 var deviceId = $(this).attr('deviceId');
				 if(cValue>=60)
				 {
				  message = 'Please enter value less than or equal to 60';
				  showResultAlertOnHome(message);
				  $(this).val("");
				  return ;
				 }
				 if(cValue == "")
				 {
				delete TimeOutValues[deviceId];
				 }
				 else
				 {
				 TimeOutValues[deviceId]=cValue;
				 }
				 $('#slider').attr('Devicetimeout', cValue);
			});
			
			$('.updateOpenControl').live('mousedown',function() {
				var ele = $(this); 
				

				ele.attr("commandexecutioninprogress","true");
			    var url = "CurtainOpenStart.action";
			    var deviceId = $(this).attr('deviceId');
				var gateWayId = $(this).attr('gateWayId');
				var commandParam = $(this).attr('commandParam');
				var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
				
				$.ajax({
					url: url,
					dataType: 'xml',
					data: params,
					success: function(xml){
						try
						{
							$(xml).find("device").each(function(index, deviceObj) 
							{
								//userDetails.changeDeviceDetails(deviceObj);
								
							});
						}
						catch(e){}
						finally
						{
							
							ele.removeAttr("commandexecutioninprogress");
						}
					}
				});
				
			});

			 $('.updateOpenControl').live('mouseup',function() {
				var ele = $(this); 
				
				ele.attr("commandexecutioninprogress","true");
			    var url = "CurtainOpenStop.action";
			    var deviceId = $(this).attr('deviceId');
				var gateWayId = $(this).attr('gateWayId');
				var commandParam = $(this).attr('commandParam');
				var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
				
				$.ajax({
					url: url,
					dataType: 'xml',
					data: params,
					success: function(xml){
						try
						{
							$(xml).find("device").each(function(index, deviceObj) 
							{
								//userDetails.changeDeviceDetails(deviceObj);
								
							});
						}
						catch(e){}
						finally
						{
							
							ele.removeAttr("commandexecutioninprogress");
						}
					}
				});
				
			});  
			
			 $('.updateCloseControl').live('mousedown',function() {
					var ele = $(this); 
					
					ele.attr("commandexecutioninprogress","true");
				    var url = "CurtainCloseStart.action";
				    var deviceId = $(this).attr('deviceId');
					var gateWayId = $(this).attr('gateWayId');
					var commandParam = $(this).attr('commandParam');
					var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
					
					$.ajax({
						url: url,
						dataType: 'xml',
						data: params,
						success: function(xml){
							try
							{
								$(xml).find("device").each(function(index, deviceObj) 
								{
									//userDetails.changeDeviceDetails(deviceObj);
									
								});
							}
							catch(e){}
							finally
							{
								
								ele.removeAttr("commandexecutioninprogress");
							}
						}
					});
					
				}); 
			  

				 $('.updateCloseControl').live('mouseup',function() {
					var ele = $(this); 
					
					ele.attr("commandexecutioninprogress","true");
				    var url = "CurtainCloseStop.action";
				    var deviceId = $(this).attr('deviceId');
					var gateWayId = $(this).attr('gateWayId');
					var commandParam = $(this).attr('commandParam');
					var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
					
					$.ajax({
						url: url,
						dataType: 'xml',
						data: params,
						success: function(xml){
							try
							{
								$(xml).find("device").each(function(index, deviceObj) 
								{
									//userDetails.changeDeviceDetails(deviceObj);
									
								});
							}
							catch(e){}
							finally
							{
								
								ele.removeAttr("commandexecutioninprogress");
							}
						}
					});
					
				}); 
				 
				//Naveen added on 24th Feb
					$(".updateMotorControl").live('click', function(){
					
					var commandParam = $(this).attr('commandParam');
					$(this).html('<img src="../resources/images/loading.gif" class="rightsideimageac"/>');
					var ele = $(this); 
					ele.attr("commandexecutioninprogress","true");
		            var url = "Curtainfullopenclose.action";
		            var deviceId = $(this).attr('deviceId');
					var gateWayId = $(this).attr('gateWayId');
					var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
					
					$.ajax({
						url: url,
						dataType: 'xml',
						data: params,
						success: function(xml){
							try
							{
								$(xml).find("device").each(function(index, deviceObj) 
								{
									userDetails.changeDeviceDetails(deviceObj);
								});
							}
							catch(e){}
							finally
							{
								
								ele.removeAttr("commandexecutioninprogress");
							}
						}
					});
					
					});
			 
			$(".updatedevicelink").live('click', function(){
				$(this).html('<img src="../resources/images/loading.gif" class="rightsideimage"/>');
				
				$('.timeoutvalueclass').val("");
				
				//vibhu change to stop refresh if command execution in progress
				var ele = $(this); 
				ele.attr("commandexecutioninprogress","true");

				var url = $(this).attr('href');
				url += "?timestamp=" + escape(new Date());
				var deviceId = $(this).attr('deviceId');
				var gateWayId = $(this).attr('gateWayId');
				var TimeOut = $(this).attr('Devicetimeout');
				
				delete TimeOutValues[deviceId];
				//TimeOutValues[deviceId]=undefined;
				
				if(TimeOut==undefined)
				{
				TimeOut=0;
				}
				var commandParam = $(this).attr('commandParam');
				var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam,"device.devicetimeout":TimeOut};
				$.ajax({
					url: url,
					dataType: 'xml',
					data: params,
					success: function(xml){
						try
						{
							$(xml).find("device").each(function(index, deviceObj) 
							{
								userDetails.changeDeviceDetails(deviceObj);
							});
						}
						catch(e){}
						finally
						{
							//vibhu change to stop refresh if command execution in progress
							ele.removeAttr("commandexecutioninprogress");
						}
					}
				});
				return false;
			});
			
			
			
			$(".acupdatedevicelink").live('click', function(){
			var commandParam = $(this).attr('commandParam');
			$(this).html('<img src="../resources/images/loading.gif" class="rightsideimageac"/>');
			  if(commandParam==10)
			  {
			    $(this).html('<img src="../resources/images/loading.gif" class="AutoModebutton"/>');
			  }
            else  if(commandParam==2)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="CoolModebutton"/>');
			}
			else  if(commandParam==1)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="HeatModebutton"/>');
			}
			else  if(commandParam==8)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="DryModebutton"/>');
			}
			else  if(commandParam==6)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="FanModebutton"/>');
			}
			else  if(commandParam==5)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="rightsideimage"/>');
			}
			else  if(commandParam==0)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="rightsideimageforac"/>');
			}
				//vibhu change to stop refresh if command execution in progress
				var ele = $(this); 
				ele.attr("commandexecutioninprogress","true");
                var url = $(this).attr('href');
				url += "?timestamp=" + escape(new Date());
				var deviceId = $(this).attr('deviceId');
				var gateWayId = $(this).attr('gateWayId');
				var TimeOut = $(this).attr('Devicetimeout');
				if(TimeOut==undefined)
				{
				TimeOut=0;
				}
				var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
				$.ajax({
					url: url,
					dataType: 'xml',
					data: params,
					success: function(xml){
						try
						{
							$(xml).find("device").each(function(index, deviceObj) 
							{
								userDetails.changeDeviceDetails(deviceObj);
							});
						}
						catch(e){}
						finally
						{
							//vibhu change to stop refresh if command execution in progress
							ele.removeAttr("commandexecutioninprogress");
						}
					}
				});
				return false;
			});
			
			$(".acswingupdatedevicelink").live('click', function(){
			var acSwing = $(this).attr('acSwing');			
			$(this).html('<img src="../resources/images/loading.gif" class="AcSwingimage"/>');
			if(acSwing==1)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="AcSwingimage"/>');
			}
				//vibhu change to stop refresh if command execution in progress
				var ele = $(this); 
				ele.attr("commandexecutioninprogress","true");
                var url = $(this).attr('href');
				url += "?timestamp=" + escape(new Date());
				var deviceId = $(this).attr('deviceId');
				var gateWayId = $(this).attr('gateWayId');
				
				var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "acConfiguration.acSwing":acSwing};
				$.ajax({
					url: url,
					dataType: 'xml',
					data: params,
					success: function(xml){
						try
						{
							$(xml).find("device").each(function(index, deviceObj) 
							{
								userDetails.changeDeviceDetails(deviceObj);
							});
						}
						catch(e){}
						finally
						{
							//vibhu change to stop refresh if command execution in progress
							ele.removeAttr("commandexecutioninprogress");
						}
					}
				});
				return false;
			});
			
			
			$('.ajaxinlinepopupform').live('submit', function(event) {
				var form = $(this).closest("form");
				form.find(".destinationselect option").attr("selected", true);
				//vibhu added
				if(form.attr("action").indexOf("#.action") > 0)
				{
					return false;
				}
				 var params = form.serialize();
				 var act = form.attr('action');
				 form.html("<img src='../resources/images/loading.gif'/>");
				 $.post(act, params, function(data){
					
					var a= $(data).find("p.message").text();
					var aa = a.split("~");
					
					if(aa[0] =='Failure'){
						$('#editmodal').dialog('close');
						//$("#ErrorMessage").html(aa[1]);
						showResultAlert(aa[1]);
						}
					else if(aa[0] =='Sucess'){
						$('#editmodal').dialog('close');
						showResultAlert(aa[1]);
						 $('#toConfigureResetPasswordInfo').hide();
						}
					
					$('#editmodal').dialog('close');
					 
				 });

				
				 return false;
			});
			
			//bhavya start
			$(".updatescanlink").live('click', function(){
				var make=$(this).attr('make');
				if(make==0)
					{
                                       message = '<s:text name="setup.devices.healthcheck.scanpopup" />';
                                       showResultAlertOnHome(message);

                                     // ("HealthCheckModel Is Not Configured.Please Configure the Model From Device ConfigureModel Section."+make);
                                   }
                          else
                                 {

				$(this).html('<img src="../resources/images/loading.gif" class="scanclass"/>');

				//vibhu change to stop refresh if command execution in progress
				var ele = $(this); 
				ele.attr("commandexecutioninprogress","true");

				var url = $(this).attr('href');
				url += "?timestamp=" + escape(new Date());
				ele.removeAttr("commandexecutioninprogress");
				var deviceId = $(this).attr('deviceId');
				var gateWayId = $(this).attr('gateWayId');
				var commandParam = $(this).attr('commandParam');
				var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
				$.ajax({
					url: url,
					
					data: params,
					success: function(xml){
						var a= $(xml).find("p.message").text();
						var aa = a.split(":");
						if(aa[0] =='Failure')
						{
							showResultAlertOnHome(aa[1]);
							ele.removeAttr("commandexecutioninprogress");
						}
						else
						{
							 var message =a;
							 if(message == "" || message == null || message == undefined){
								 message = '<s:text name="general.op.completed" />';
							 }
							 showResultAlertOnHome(message);
							 ele.removeAttr("commandexecutioninprogress");
						}

					}
				
				});
  }
      				return false;
			});
			$(".updatecurentstatuslink").live('click', function(){
				var make=$(this).attr('make');
				if(make==0)
					{
                                       message = '<s:text name="setup.devices.healthcheck.scanpopup" />';
                                       showResultAlertOnHome(message);

                                     // ("HealthCheckModel Is Not Configured.Please Configure the Model From Device ConfigureModel Section."+make);
                                   }
                          else

                        	  
				$(this).html('<img src="../resources/images/loading.gif" class="currentstatusclass"/>');

				//vibhu change to stop refresh if command execution in progress
				var ele = $(this); 
				ele.attr("commandexecutioninprogress","true");

				var url = $(this).attr('href');
				url += "?timestamp=" + escape(new Date());
				ele.removeAttr("commandexecutioninprogress");
				var deviceId = $(this).attr('deviceId');
				var gateWayId = $(this).attr('gateWayId');
				var commandParam = $(this).attr('commandParam');
				var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
				$.ajax({
					url: url,
					dataType: 'xml',
					data: params,
					success: function(xml){
						var a= $(xml).find("p.message").text();
						var aa = a.split(":");
						if(aa[0] =='Failure')
						{
							showResultAlertOnHome(aa[1]);
						}
						else
						{
							 var message =a;
							 if(message == "" || message == null || message == undefined){
								 message = '<s:text name="general.op.completed" />';
							 }
							 showResultAlertOnHome(message);
						}

					}
				
				});
				return false;
			});
			//bhavya end
			/* vibhu deprecated
			$(".updatedevicearm").live('click',function(){
				$(this).html('<img src="../resources/images/loading.gif" class="armclass"/>');
				var url = $(this).attr('href');
				var deviceId = $(this).attr('deviceId');
				var gateWayId = $(this).attr('gateWayId');
				var name = $(this).attr('statusName');
				var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId,"device.armStatus.name":name};
				$.ajax({
					url:url,
					dataType:'xml',
					data:params,
					success:function(xml){
						$(xml).find("device").each(function(index,deviceObj){
							userDetails.changeDeviceDetails(deviceObj);
						});
					
					}
				});
				return false;
			}); */
			
			$(".enableStatus").live('click',function(){
				$(this).html('<img src="../resources/images/loading.gif"/>');
				var url = $(this).attr('href');
				var deviceId = $(this).attr('deviceId');
				var gateWayId = $(this).attr('gateWayId');
				var name = $(this).attr('statusName');
				var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId,"device.enableStatus":name};
				$.ajax({
					url:url,
					dataType:'xml',
					data:params,
					success:function(xml){
						$(xml).find("device").each(function(index,deviceObj){
							userDetails.changeEnableDeviceDetails(deviceObj);

						});
					
					}
				});
				return false;
			});
			
			$(".gatewaychange").live('change',function(){
				var gateWayId = $(this).val();
				selectedGateWay = gateWayId;
			});

			 $('.remoteReboot').live('click', function() {
					var url = $(this).attr('href');
					var gateWayId = $(this).attr('gateWayId');
					var targetid = $(this).attr('targetid');
					var params = {"gateWayId":gateWayId};
					$("#confirm").dialog("destroy");
					$("#confirm").html("<s:text name='home.msg.0002' />");
					var href = $(this).attr('href');
					$("#confirm").dialog({
							stackfix: true,
							modal: true,
							position: [350,100],
							buttons: {
								<s:text name='general.yes' />: function() {
									$(this).dialog('close');
									$.ajax({
										url:url,
										dataType:'xml',
										data:params,
										success: function(data){
									  	}
										
									});
								},
								<s:text name='general.no' />: function() {
									$(this).dialog('close');
								}

							}
						});
						 return false;
					});
					//bhavya start
					 $('.FirmwareUpgrade').live('click', function() {
					var url = $(this).attr('href');
					var gateWayId = $(this).attr('gateWayId');
					var FirmwareId = $(this).attr('FirmwareId');
					var firmwareversion = $(this).attr('firmwareversion');
					var gateWayVersion = $(this).attr('gateWayVersion');
					//alert(gateWayId);
					var targetid = $(this).attr('targetid');
					var params = {"gateWayId":gateWayId,"FirmwareId":FirmwareId};
					$("#confirm").dialog("destroy");
					$("#confirm").html("<s:text name='home.msg.0002b'/>"+firmwareversion+"?");
					var href = $(this).attr('href');
					$("#confirm").dialog({
							stackfix: true,
							modal: true,
							position: [350,100],
							buttons: {
								<s:text name='general.ok' />: function() {
									$(this).dialog('close');
									$.ajax({
										url:url,
										dataType:'xml',
										data:params,
										success: function(data){
									  	}
										
									});
								},
								<s:text name='general.cancel' />: function() {
									$(this).dialog('close');
								}

							}
						});
						 return false;
					});
					
					//bhavya end
		$('.Alertlink').live('click', function(){
					var targeturl = $(this).attr('href');
					var pHeight = $(this).attr('popupheight');
					var pWidth = $(this).attr('popupwidth');
					var pTitle = $(this).attr('popuptitle');
					if(pHeight == undefined){
						pHeight = 600;
						
					}
					if(pWidth == undefined){
						pWidth = 550;
						
					}
					if(pTitle == undefined){
						pTitle = "Alarms";
						
					}
					$.ajax({
						  url: targeturl,
						  success: function(data){
							$('#editmodal').html(data);
							$('#editmodal').dialog('destroy');
							$('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true,position: [150,100]});
						  }
						});
					return false;
				});

			 $('.devicestatus').live('click', function() {
					var url = $(this).attr('href');
					var gateWayId = $(this).attr('gateWayId');
					var targetid = $(this).attr('targetid');
					var params = {"gateWayId":gateWayId};
					$("#confirm").dialog("destroy");
					$("#confirm").html("<s:text name='home.msg.0001' />");
					var href = $(this).attr('href');
					$("#confirm").dialog({
							stackfix: true,
							modal: true,
							position: [350,100],
							buttons: {
								<s:text name='general.yes' />: function() {
									$(this).dialog('close');
									$.ajax({
										url:url,
										dataType:'xml',
										data:params,
										success: function(data){
									  	}
										
									});
								},
								<s:text name='general.no' />: function() {
									$(this).dialog('close');
								}

							}
						});
						 return false;
					});
		//Aditya Start
		
			 $('.allOnOff').live('click', function() {
					var url = $(this).attr('href');
					var gateWayId = $(this).attr('gateWayId');
					var OnOffStstus = $(this).attr('OnOffStstus');
					
					var params = {"gateWay.macId":gateWayId,"gateWay.allOnOffStatus":OnOffStstus};
					$("#confirm").dialog("destroy");
					if(OnOffStstus != 0)
					{
						$("#confirm").html("<s:text name='home.msg.0020' />");
					}
					else
					{
						$("#confirm").html("<s:text name='home.msg.0021' />");
					}
					
					var href = $(this).attr('href');
					$("#confirm").dialog({
							stackfix: true,
							modal: true,
							position: [350,100],
							buttons: {
								<s:text name='general.yes' />: function() {
								   $(this).dialog('close');
								   $.ajax({
										url:url,
										dataType:'xml',
										data:params,
										success: function(data)
										{
											userDetails.generateAllOnOFFButton(OnOffStstus);
											var a= $(data).find("p.message").text();
											showResultAlertOnHome(a);
											
											
									  	}
										
									});
								},
								<s:text name='general.no' />: function() {	
									$(this).dialog('close');
									
								}

							}
						});
						 return false;
					});	 
			
			 //Aditya End
			 
		$(".allArmrDisarm").change(function(){
			var statusName = null;
			var statusDisplayName = "<s:text name='general.home' />";
			var href="allArmDisarmSelectivearm.action";
			var sel = document.getElementById('modeSelect');

			if(0 == sel.selectedIndex)
			{
				return false;
			}
			statusName = sel.options[sel.selectedIndex].value;
			var params = {"statusName":statusName};
			
			if(sel.options[sel.selectedIndex].id == "STAY")statusDisplayName = "<s:text name='general.stay' />"
			else if(sel.options[sel.selectedIndex].id == "AWAY")statusDisplayName = "<s:text name='general.away' />"
			
			$("#confirm").dialog("destroy");
			$("#confirm").html("<s:text name='home.msg.0003a' /> "+statusDisplayName+" <s:text name='home.msg.0003b' />");
			$("#confirm").dialog({
							stackfix: true,
							modal: true,
							position: [350,100],
							buttons: {
								<s:text name='general.yes' />: function() 
								{
									$(this).dialog('close');
							$.ajax({
								url:href,
								data:params,
								cache:false,
								success: function(data)
								{
									$("#message").html(data);
									$("#message").dialog('open');
									$("#message" ).dialog({
										stackfix: true,
										modal: true,
										position: [350,100],
										buttons: {
											<s:text name='general.ok' />:function(){
												$(this).dialog('close');
											}
										}
								});
							//cAnchor.find('img').attr('src', cImgSrc);
								if(selectedWiseType == 'SCENARIO_WISE')
								{
									showScenarioDetails();
								}
								else if(selectedWiseType == 'LOCATION_WISE')
								{
									showUserDetailsByLocations();
								}
								else if(selectedWiseType == 'DEVICE_DIAGNOSTIC_WISE')
									{
									generateDiagnosisHtml();
									}
								else
								{
									showUserDetails();
								}

				  				}
					
								});
								},
								<s:text name='general.no' />: function() {
									$(this).dialog('close');
								}

							}
						});
				sel.options[0].selected = true;
				return false;
			});
 
		
                        //<!--pari add
		$(".addpreset").change(function(){
			//$("#deviceListUl").data("Stream",$(this).attr('deviceid')).data("DeviceID",$(this).attr('deviceid'));
		var presetNumber = null;
		var deviceid = $(this).attr('deviceid');
		var gateWayId = $(this).attr('gateWayId');
		var sel = document.getElementById('prestSelect');

		if(0 == sel.selectedIndex)
		{
			return false;
		}
		presetNumber= sel.options[sel.selectedIndex].id;
		var params = {"presetNumber":presetNumber,"device.gateWay.macId":gateWayId, "device.generatedDeviceId" : deviceid};
		//$('#presetsection').html(inHTML);

		//Sumit: TBD Internationalization not handled
		$("#confirm").dialog("destroy");
		$("#confirm").html(formatMessage("setup.devices.msg.preset.set10")+' '+sel.options[sel.selectedIndex].id+'?');
		$("#confirm").dialog({
						stackfix: true,
						modal: true,
						position: [350,100],
						buttons: {
							/*Set: function() 
							{
								$(this).dialog('close');
								var href="setToCameraPreset.action";

						$.ajax({
							url:href,
							data: params,
							cache:false,
							success: function(data)
							{
								$("#message").html(data);
								$("#message").dialog('open');
								$("#message" ).dialog({
									stackfix: true,
									modal: true,
									position: [350,100],
									buttons: {
										Ok:function(){
											$(this).dialog('close');
										}
									}
							});
						//cAnchor.find('img').attr('src', cImgSrc);
							if(selectedWiseType == 'SCENARIO_WISE')
							{
								showScenarioDetails();
							}
							else if(selectedWiseType == 'LOCATION_WISE')
							{
								showUserDetailsByLocations();
							}
							else
							{
								showUserDetails();
							}

			  				}
				
							});
							},*/<s:text name='general.move' />: function() 
							{
								$(this).dialog('close');
								var href="moveToCameraPreset.action";
						$.ajax({
							url:href,
							data:params,
							cache:false,
							success: function(data)
							{
								$("#message").html(data);
								$("#message").dialog('open');
								$("#message" ).dialog({
									stackfix: true,
									modal: true,
									position: [350,100],
									buttons: {
										<s:text name='general.ok' />:function(){
											$(this).dialog('close');
										}
									}
							});							

			  				}
				
							});
							},
							<s:text name='general.cancel' />: function() {
								$(this).dialog('close');
							}

						}
					});
			sel.options[0].selected = true;
			return false;
		});
		//<!--pari end


			$(".previousimagelink").live('click',function(){
				var url = $(this).attr('href');
				var currentimageId = $(this).attr('currentimageId');
				imvgDetails.showPreviousImage(currentimageId);
				return false;
			});

			$(".nextimagelink").live('click',function(){
				var url = $(this).attr('href');
				var currentimageId = $(this).attr('currentimageId');
				imvgDetails.showNextImage(currentimageId);
				return false;
			});


			$(".camerapantiltclass").live('click', function(){
				var url = $(this).attr('href') + "?__=" + new Date();
				var controlPantilt = $(this).attr('controlPantilt');
				var deviceid = $(this).attr('deviceid');
				var gateWayId = $(this).attr('gateWayId');
				var params = {"cameraConfiguration.controlPantilt" : controlPantilt,"device.gateWay.macId":gateWayId, "device.generatedDeviceId" : deviceid};
				$.ajax({
					url: url,
					dataType: 'html',
					data: params,
					success: function(mrl){
					}
				});
				return false;
			});
			
			
			$(".sliderfordimmer").live('click', function(){
				var clickedLink = $(this);
				//clickedLink.html('<img src="../resources/images/loading.gif" />');
				var clickedbutton = $(this).attr('up');
				var url = $(this).attr('href');
				url += "?timestamp=" + escape(new Date());
				var deviceid = $(this).attr('deviceid');
				var gateWayId = $(this).attr('gateWayId');
				var commandParam = ( $(this).attr('commandparam')*1);
				var interval =  $(this).attr("interval");
				if(clickedbutton =='up'){
				commandParam = commandParam + (1 * interval);
					if(commandParam > 100){
						commandParam = 100;
					}
				}else{
					commandParam =commandParam - (1 * interval);
					if(commandParam < 0){
						commandParam = 0;
					}
				}
				var params = {"device.generatedDeviceId":deviceid,"device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
				$.ajax({
					  url: url,
					  data: params,
					  success: function(xml){
					  // Change the device section.
					  $(xml).find("device").each(function(index, deviceObj) {
							userDetails.changeDeviceDetails(deviceObj);
					   });
				  	  }
				});
				return false;
			});

			/* $(".camerapantiltclass").live('click', function(){
				var url = $(this).attr('href');
				var controlPantilt = $(this).attr('controlPantilt');
				var deviceid = $(this).attr('deviceid');
				var gateWayId = $(this).attr('gateWayId');
				var params = {"cameraConfiguration.controlPantilt" : controlPantilt,"device.gateWay.macId":gateWayId, "device.generatedDeviceId" : deviceid};
				$.ajax({
					url: url,
					dataType: 'html',
					data: params,
					success: function(mrl){
					}
				});
				return false;
			});
 */
			$(".nightVisionClass").live('click', function(){
				var clickedLink = $(this);
				var url = clickedLink.attr('href') + "?__=" + new Date();
				var name = clickedLink.attr('name');
				var deviceid = clickedLink.attr('deviceid');
				var gateWayId = clickedLink.attr('gateWayId');
				var value = clickedLink.attr('value');
				var params = {"device.generatedDeviceId" : deviceid, "device.gateWay.macId":gateWayId, "cameraConfiguration.controlNightVision": value};
				$.ajax({
					url: url,
					dataType: 'html',
					data: params,
					success: function(mrl){
						clickedLink.attr("value", value==0?1:0);
						clickedLink.find('img').attr('src', value==0? '/imonitor/resources/images/cameralightbutton.png' : '/imonitor/resources/images/cameralightbuttonglow.png');
						$(".nowselectedcamera").attr('controlnightvision', value);
					}
				});
				return false;
			});
//vibhu start
			$(".wisetype").live('click', function(){
				selectedWiseType = $(this).val();

				//sumit start: Device Diagnosis Prototype
				if(selectedWiseType == 'DEVICE_DIAGNOSTIC_WISE'){
					$("#deviceListUl").sortable({disabled : true});
					$("#LocationInput").hide();
					$("#locationWiseSelect").hide();
					$("#scenarioWiseSelect").hide();
					$("#diognosisWiseSelect").hide();
					$("#deviceTypeWiseSelect").hide();
					$("#deviceTypeWiseSelect").change();
					$("#deviceListUl").html("");
					generateDiagnosisHtml();
				}
				//sumit end: Device Diagnosis Prototype

				//DEVICE_TYPE_WISE
				if(selectedWiseType == 'DEVICE_TYPE_WISE'){
					$("#deviceListUl").sortable({disabled : true});
					$("#LocationInput").hide();
					$("#locationWiseSelect").hide();
					$("#scenarioWiseSelect").hide();
					$("#diognosisWiseSelect").hide();
					$("#deviceTypeWiseSelect").show();
					$("#deviceTypeWiseSelect").change();
					$("#deviceListUl").html("");
					showUserDetails();
				}
				//LOCATION_WISE
				if(selectedWiseType == 'LOCATION_WISE'){
					$("#LocationInput").hide();
					$("#deviceTypeWiseSelect").hide();
					$("#scenarioWiseSelect").hide();
					$("#diognosisWiseSelect").hide();
					$("#locationWiseSelect").show();
					$("#locationWiseSelect").change();
					showUserDetailsByLocations();
				}
				//SCENARIO_WISE
				if(selectedWiseType == 'SCENARIO_WISE'){
					$("#deviceListUl").sortable({disabled : true});
					$("#LocationInput").hide();
					$("#locationWiseSelect").hide();
					$("#deviceTypeWiseSelect").hide();
					$("#diognosisWiseSelect").hide();
					$("#scenarioWiseSelect").show();
					$("#scenarioWiseSelect").change();
					showScenarioDetails();
				}
			});
//vibhu end
			$(".gatewaychange").live("change", function(){
				userDetails.showGateWayStaus($(this).val());
				$('.remoteReboot').attr('gatewayid',$(this).val());
				//alert($(this).val());
			});	
			
			//Fire when we click on the play button of the camera device.
			$(".changecamera").live('click', function(){
				// Cancelling the click, if the player is playing the same device.
				if($(".changecamera").hasClass("streamingstarted")){
				var Streamvalue = $("#deviceListUl").data("Stream");
				var deviceid=$(this).attr('deviceid');
					if(Streamvalue != deviceid)
					{
						showResultAlertOnHome("<s:text name='home.msg.0004a' />"+Streamvalue+"<s:text name='home.msg.0004b' />");
					}
					return false;
				}
				$(".cameragechangelistener").attr('deviceid', $(this).attr('deviceid'));
				$(".cameragechangelistener").attr('gateWayId', $(this).attr('gateWayId'));
				$(".cameragechangelistener").attr('pantiltControl', $(this).attr('pantiltControl'));
				$(".cameragechangelistener").attr('controlNightVision', $(this).attr('controlNightVision'));
				$(".cameragechangelistener").attr('presetvalue', $(this).attr('presetvalue'));
				
				var deviceName = $(this).attr('devicename');
				$("#cameramoviediv").html("<s:text name='home.msg.0005' />" + deviceName);
				// Changing the now selected camera.
				$(".changecamera").removeClass('nowselectedcamera');
				$(this).addClass('nowselectedcamera');
				$(".livestreamlink").click();
				return false;
			});



			
			// What should happen we we click on the play button of Player.
			$(".livestreamlink").die('click');
			$(".livestreamlink").live('click', function(){
				$("#deviceListUl").data("Stream",$(this).attr('deviceid')).data("DeviceID",$(this).attr('deviceid'));
				var clickedLink = $(this);
				
				var url = $(this).attr('href') + "?__=" + new Date();
				var deviceId = $(this).attr('deviceid');
				var gateWayId = $(this).attr('gateWayId');
				var pantiltControl = $(this).attr('pantiltControl');
				var presetvalue = $(this).attr('presetvalue');
				
				
				var controlNightVision = $(this).attr('controlNightVision');
				if(controlNightVision == 1){
					$(".nightVisionClass").find('img').attr('src', '/imonitor/resources/images/cameralightbuttonglow.png');
				}else{
					$(".nightVisionClass").find('img').attr('src', '/imonitor/resources/images/cameralightbutton.png');
				}
					
				var params = {"device.generatedDeviceId":deviceId,"device.gateWay.macId":gateWayId};
				$(".nowselectedcamera").html('<img src="../resources/images/loading.gif" />');
				$.ajax({
					url: url,
					dataType: 'xml',
					data: params,
					success: function(data){
						var mrl = $.trim($($(data).find('mrl').get(0)).text());
						//mrl = "rtsp://122.166.229.151/sample_h264_1mbit.mp4";
						//alert(mrl);   Parishod commented rtsp showing on home page 
					
						//Write the condition to validate the mrl.
						if(mrl.indexOf("rtsp") == 0){
							var vlc = document.getElementById("vlcPlayer");
							var options = new Array(":aspect-ratio=4:3", "--rtsp-tcp");
							vlc.playlist.clear();
							//var id = vlc.playlist.add($.trim("rtsp://192.168.2.26/samp2.mp4"), "fancy name", options);
							var id = vlc.playlist.add(mrl, "Imonitor", options);
							vlc.playlist.playItem(id);
							$(".nowselectedcamera").html('<img src="../resources/images/stopbutton.png" title="click to stop" />');
							$(".nowselectedcamera").addClass('streamingstarted');
							// hide/show play/stop buttons.
							$(".stopLivestreamlink").show();
							$(".livestreamlink").hide();
						} else{
							// Get the effect of streaming stopped.
							$(".stopLivestreamlink").click();
						}
						
						//$(".addpreset").hide();			
						if(pantiltControl == '1')
						{
							$(".cameraadvancedfeatures").show();
							var presetValueText = $("#prestSelect");
                             var presetOption="";
                                 if(presetvalue==0)
								 {
									presetValueText.hide();
								 }
								 else
								 {
									presetOption += "<option id='0' value='0'>Move to preset</option>";
                                               if( (presetvalue & 1) > 0 )
                                                   presetOption += "<option id='1' value='1'>preset1</option>";
                                               if( (presetvalue & 2) > 0 )
                                                   presetOption += "<option id='2' value='2'>preset2</option>";
                                               if( (presetvalue & 4) > 0 )
                                                   presetOption += "<option id='3' value='3'>preset3</option>";
                                               if( (presetvalue & 8) > 0 )
                                                   presetOption += "<option id='4' value='4'>preset4</option>";
                                               presetValueText.html(presetOption);
								 }
                                               
 
 

						}else{
							$(".cameraadvancedfeatures").hide();
						}
						
						
							
					}
				});
				return false;
			});
			// Stop the streaming.
			$(".stopLivestreamlink").live('click', function(){
				var vlc = document.getElementById("vlcPlayer");
				try{
					vlc.playlist.stop();
					vlc.playlist.clear();
				}catch(err){
				}
				
			/*	var deviceId = $(this).attr('deviceid');
				var gateWayId = $(this).attr('gateWayId');
				
				var params = {"device.generatedDeviceId":deviceId,"device.gateWay.macId":gateWayId};
				$.ajax({
					url: "stopstream.action",
					dataType: 'xml',
					data: params,
					success: function(data){
						$("#deviceListUl").data("Stream",0);
						$(".nowselectedcamera").removeClass('streamingstarted');
						$(".nowselectedcamera").html('<img src="../resources/images/playbutton.png" />');
						// show/hide play/stop buttons.
						$(".stopLivestreamlink").hide();
						$(".livestreamlink").show();
						
						}
				});*/
				
				
				$("#deviceListUl").data("Stream",0);
				$(".nowselectedcamera").removeClass('streamingstarted');
				$(".nowselectedcamera").html('<img src="../resources/images/playbutton.png" />');
				// show/hide play/stop buttons.
				$(".stopLivestreamlink").hide();
				$(".livestreamlink").show();
				return false;
			});
			
			
			$(".streamingstarted").live('click', function(){
				$(".stopLivestreamlink").click();
				return false;
			});
			// We hide the play/stop button initially.
			$(".stopLivestreamlink").hide();
			$(".livestreamlink").hide();
			$(".cameraadvancedfeatures").hide();

			$(document).keypress(function(e) { 
			    if (e.which == 27) {
			                 $('#overlay').hide();
			                 $('#imageshowing').hide();
			    }
			});
						
			$('.imvgalertImage').live('click', function(){
				var url = $(this).attr('href');
				var deviceId = $(this).attr('deviceId');
				var alertFromImvgId = $(this).attr('alertFromImvgId');
				var params = {"alertFromImvgId":alertFromImvgId, "deviceId":deviceId};
				el = document.getElementById("overlay");
				el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
				document.getElementById('overlay').style.height = $(document).height()+"px";
				document.getElementById('overlay').style.width = $(document).width()+"px";
				el = document.getElementById("imageshowing");
				el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
				$.ajax({
					  url: url,
					  data:params,
					  success: function(data){
						$('#imageshowing').html(data);
					  }
					});
				return false;
			});

			$('.imvgalertStream').live('click', function(){
				var url = $(this).attr('href');
				alert("Implementing the stored screen");
				var deviceId = $(this).attr('deviceId');
				var alertFromImvgId = $(this).attr('alertFromImvgId');
				var params = {"alertFromImvgId":alertFromImvgId, "deviceId":deviceId};
				$.ajax({
					  url: url,
					  data:params,
					  success: function(data){
						$('#imageshowing').html(data);
					  }
					});
				return false;
			});

		  $(".acoptionselect").live("change", function(){
				var url = $(this).attr('href');
				var deviceId = $(this).attr('deviceId');
				var gateWayId = $(this).attr('gateWayId');
				var commandParam = $(this).val();
				var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
				$.ajax({
					url: url,
					dataType: 'xml',
					data: params,
					success: function(xml){
						$(xml).find("device").each(function(index, deviceObj) {
							var aa = $(deviceObj);
							userDetails.changeDeviceDetails(deviceObj);
						});
					}
				});

			  $(this).replaceWith('<img src="../resources/images/loading.gif" class="rightsideimage"/>');
		  });	
//vibhu start
			$('.executeScenarios').live('click', function() {
				$("#confirm").dialog("destroy");
				$("#confirm").html("<s:text name='home.msg.0006' />");
				var href = $(this).attr('href');
				var tittle = $(this).attr('title');
				
				$("#confirm").dialog({
						stackfix: true,
						modal: true,
						title: tittle,
						position: [350,100],
						 open: function(event, ui) 
						{			 
							$(this).parent().children().children('.ui-dialog-titlebar-close').hide();
						},						
						buttons: {
							<s:text name='general.yes' />: function() {
								$(this).dialog('close');
											
								$.ajax({
									url: href,
									success: function(xml){
										
										var a= $(xml).find("p.message").text();
										var aa = a.split(":");
										if(aa[0] =='Failure')
										{
											showResultAlertOnHome(aa[1]);
										}
										else
										{
											 var message =a;
											 if(message == "" || message == null || message == undefined){
												 message = '<s:text name="general.op.completed" />';
											 }
											 showResultAlertOnHome(message);
										}

									}
								});
							},
							<s:text name='general.no' />: function() {
								$(this).dialog('close');
							}

						}
					});
					 return false;
				});
//vibhu end
		('.getattr').live('click', function(){

			alert($(this).attr('location'));
		});
		
		 
		});
		document.onkeydown=function(e)
        {
                var e = window.event || e;
                if(e.keyCode == 27)
                {
                        flag=false;
                        closeoverlay();
                }
        }  ;
		function getattr1(location)
		{
			userDetails.changeHtml(LOCATION_WISE,location);		
		}


		function closeoverlay() {
			el = document.getElementById("overlay");
			el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
			document.getElementById('overlay').style.height = $(document).height()+"px";
			document.getElementById('overlay').style.width = $(document).width()+"px";
			el = document.getElementById("imageshowing");
			el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
		}

		function updatedevicelink(data,data1,data2,data3){
			var url = "Curtainopenclose.action";
			var deviceId = data2;
			var gateWayId = data1;
			var commandParam = data;
			var make=data3;
			if(make==0)
			{
				showResultAlertOnHome("<s:text name='setup.devices.curtain.make' />");
			return false;
			}
			
			var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
			$.ajax({
				url: url,
				dataType:'xml',
				data: params,
				success: function(xml){
					$(xml).find("device").each(function(index, deviceObj) {
						userDetails.changeDeviceDetails(deviceObj);
					});
				}
			});
			return false;
		}
		// ************************************************** sumit start: ReOrdering Of Devices **********************************************
		function setOrderForDevice(dataToProcess){
			var positionIndexes = new Array();
			positionIndexes = dataToProcess;
			var indices = "";
			for(var i=0; i< positionIndexes.length; i++){
				indices += positionIndexes[i];
				if(i != positionIndexes.length - 1){
					indices += ",";		
				}		
			}
			var params = {"position":indices};
			targetUrl = "reOrderDevicesListing.action";
			$.ajax({
				url: targetUrl,
				data: params,
				cache: false,
				success: function(data){
					showResultAlertOnHome("<s:text name='home.msg.0008' />");
				}
			});

		}
		// ************************************************* sumit end: ReOrdering Of Devices ****************************************************
		
		function senddimmervalue(data,data1,data2,data3,data4){
			var url = "dimmerChange.action";
			var deviceId = data2;
			var gateWayId = data1;
			var commandParam = data;
			var TimeOut = data4;
			if(TimeOut==undefined)
			{
			TimeOut=0;
			}
			$('.Dimmertimeoutvalueclass').val("");
			delete TimeOutValues[deviceId];
			var params = {"device.generatedDeviceId":deviceId ,"device.gateWay.macId":gateWayId , "device.commandParam":commandParam,"device.devicetimeout":TimeOut};
				$.ajax({
					  url: url,
					  dataType:'xml',
					  data: params,
					  success: function(xml){
					  // Change the device section.
					  $(xml).find("device").each(function(index, deviceObj) {
						userDetails.changeDeviceDetails(deviceObj);
					   });
				  	  }
				});
				return false;
			
		}
		function SendAcValue(data,data1,data2,data3){
			var url ='changeAcTemperature.action';
			var deviceId = data2;
			var gateWayId = data1;
			var commandParam = data;
			
			var params = {"device.generatedDeviceId":deviceId ,"device.gateWay.macId":gateWayId , "device.commandParam":commandParam };
			
			$.ajax({
					  url: url,
					  dataType:'xml',
					  data: params,
					  success: function(xml){
					  // Change the device section.
					  $(xml).find("device").each(function(index, deviceObj) {
						userDetails.changeDeviceDetails(deviceObj);
					   });
				  	  }
				});
				return false;
			
		}
		
		function SendFanModeValue(data,data1,data2,data3){
			var url ='changeFanMode.action';
			var deviceId = data2;
			var gateWayId = data1;
			var FanMode = data;
			var params = {"device.generatedDeviceId":deviceId ,"device.gateWay.macId":gateWayId , "acConfiguration.fanModeValue":FanMode};
			
			$.ajax({
					  url: url,
					  dataType:'xml',
					  data: params,
					  success: function(xml){
					  // Change the device section.
					  
					  $(xml).find("device").each(function(index, deviceObj) {
						userDetails.changeDeviceDetails(deviceObj);
					   });
				  	  }
				});
				return false;
			
		}
		
		
		function openHelpPage()
		{
			window.open ('<%=applicationName %>/resources/docs/mainPageManual.pdf', 'newwindow');
		}
		
		function CheckMainuser() 
		{
			<s:if test="#session.user.role.name == 'mainuser' ">
			return 1;
		 	</s:if>
		 	<s:else>
		 	return 0;
		 	</s:else>
		}
		$("#toConfigureResetPasswordInfo").live("click", function(){
		//var pass = prompt("This is a secured area. Please enter your password to proceed.","your password");
		var targeturl = $(this).attr('href');
		var toDisplay = '<div><p style="color: red;">'+ '<s:text name="setup.user.password.secure"/>' +'</p>';
		toDisplay += "<br><input class='confirmPasssword' type='password'  placeholder='"+'<s:text name="setup.user.password.secure"/>'+"'></input></div>";
		$("#confirm").dialog("destroy");
		$("#confirm").html(toDisplay);
		$("#confirm").dialog({
					stackfix: true,
					modal: true,
					buttons: {
						<s:text name="general.ok" />: function() {
							$(this).dialog('close');
							var pass = $('.confirmPasssword').val();
							var params='password='+pass;
							//var targeturl = "toConfigureResetPasswordInfo.action";
							$.ajax({
								url: targeturl,
								type: "POST",
								data: params,
								success: function(data){
									var a= $(data).find("p.message").text();
									var aa = a.split(":");
									if(aa[0] =='Failure'){
										showResultAlert(aa[1]);
									}
									else
									{
										$('#editmodal').html(data);
										$('#editmodal').dialog('destroy');
										$('#editmodal').dialog({height: 400, width: 550, title: "<s:text name='setup.user.password.configure' />", modal:true});

									}
								}
							});
						},
						<s:text name="general.cancel" />: function() {
							$(this).dialog('close');
						}

					}
				}); 
		return false;
 	});
		$('form').live('change', function(e) {
			
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
		
 		</script>
		<style type="text/css">
			.ui-progressbar-value {
				background-image: url(<%=applicationName %>/resources/images/animatedbar.gif);
				background-color:#FFFFFF; 
			}
			.blink {text-decoration: blink;}
		</style>
	</head>
	<body onload="noBack();" class="bodyclass">
	<div class="maindiv">
			<div class="titlebg">
			<div class="welcomeuser">
				<div id="UserInformation" ></div>
				<div id="SIInformation" style="margin: 0px 0px 0px -56px;"></div>
				
				<%-- <span style="color:#0C5AA3;"><s:text name="home.msg.0009" /></span> <s:property value="#session.user.userId"/><br><span style=" color:#0C5AA3;margin-left: -46px;"><s:text name="home.msg.0017" /></span><s:date name="lastLoginTime" format="dd/MM/yy 'at' hh:mm a"/> --%>
				<s:if test="#session.user.forgetPassword == 1">
				<s:if test="hintQuestionFromDB == null && hintAnswerFromDB == null">
				
				<div class="blink" style=" margin-left: -535px;margin-top: -30px;">
				<a class='editlink rulenadd' id="toConfigureResetPasswordInfo" href="toConfigureResetPasswordInfoFromHome.action" style="margin:0px 0px 0px -488px;"><s:text name='home.user.password.configure'/></a>
				</div>
				</s:if>
				</s:if>
				</div>
				<img class="logo" src="<%=applicationName %>/resources/images/logo.png" />		
                				
				<div>
					<ul class="ulmenu topmenu" lihoverclass="hoverstyle">
						<li class="menuitem selectedtopmenu "><a href="home.action" title="Home" class="menuitemnormal Clicking"><div style="width=100%;height=100%;"><s:text name="general.main" /></div></a></li>
						 <s:if test="#session.user.role.name == 'mainuser' ">
						 	<li class="menuitem"><a href="setup.action" title="To Configure Device" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.setup" /></div></a></li>
						</s:if>
						<li class="menuitem"><a href="getdeviceforalerts.action" title="View Alerts" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.alerts" /></div></a></li>
						<li class="menuitem"><a href="getlistofLocation.action" title="Prespective View" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.advance" /></div></a></li>
					 	<li class="menuitem" id="dashboard"><a href="getlistofLocationforEnergyMg.action" title='<s:text name="Tooltip.Energy" />' class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.dashboard" /></div></a></li> 
						<li class="menuitem"><a href="../logout.action" title="Sign Out" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.signout" /></div></a></li>					
					</ul>
				</div>
				<div>
				<img class="helpicon" title="help" src="<%=applicationName %>/resources/images/help.png" onclick='javascript: openHelpPage()' />
				</div>
			</div>
 <!-- **************************************************** sumit begin *********************************************************** -->
			<div id="gatewaySectionDetailes" >	
				<div id="gatewaySection" style="width: 40%;float:left;" >
				</div>

			 	<div id="homeAway" style="margin: 0px 0px 0px -40px;float: left;width: 36%; text-align: center; ">
					<div id="curMode" class="curMode">
					</div>
					<div id="mode"  class="modeSelect" >
						<div>
						 	<select id="modeSelect"  name="modeSelect"  class="allArmrDisarm"  >
						 		<option id="modeSelect"><s:text name="home.msg.0010"/> </option>
						 		<option id="HOME" value="DISARM"><s:text name="general.home"/> </option>
						 		<option id="STAY" value="STAY"><s:text name="general.stay"/></option>
						 		<option id="AWAY" value="ARM"><s:text name="general.away"/></option>
						 	</select>
						</div>	
					</div>
				</div>
				<div id="rebootSection" style="width: 9%;float:left;" >
				 		<s:if test="#session.user.role.name == 'mainuser' ">
					 		<div id="reboot" class="reboot" >
					  		</div>
				 		</s:if>
				 </div> 
				<div id="devicestatus" style="width: 9%;float:left; text-align:center;" class="devicestatus" > </div>
				<div id="allOnOff" class="allOnOff" > </div>
					 
					 <!-- 												ORIGINAL CODE 
					 <div id="home" class="home" >
					 <a href="allArmDisarm.action" id="homebutton" statusName ="DISARM" changedStatusName="ARM" targetid="home" class="allArmrDisarm">
					 			<img  src="/imonitor/resources/images/homebutton.png" style="b" title="home"></img></a>
					  </div>
					 <div id="away" class="away" >
					 <a href="allArmDisarm.action" statusName ="ARM" changedStatusName="DISARM" targetid="away" class="allArmrDisarm">
					 			<img src="/imonitor/resources/images/awaybutton.png" title="away"></img></a>
					 </div> -->		
					 
					 <!-- ********************************************************** sumit end ******************************************************* -->

			</div> <!--  gatewaySectionDetailes Division  -->

			<div>
				<div class="deviceContainerSection">
					<div class='headersection' id="headersection">
					<div  id="radio" class="radiobutton">
<!-- vibhu start -->
<table >
					<tr><td class="locationrow" title="click to list rooms"><input type="radio" id="radio1"  name="wisetype" value="LOCATION_WISE"  class="wisetype wisetype1" checked="checked" >
						<label id="radioLable" for="radio1"><s:text name="general.rooms"/></label></input></td>
						
						<td class="devicetyperow" title="click to list devices"><input type="radio" id="radio2" name="wisetype" value="DEVICE_TYPE_WISE"  class="wisetype wisetype1" >
						<label for="radio2"><s:text name="general.devices"/></label></input></td>
												
					<td class="senariowiserow" title="click to list scenarios"><input type="radio" id="radio3" name="wisetype" value="SCENARIO_WISE"  class="wisetype wisetype1" >
						<label for="radio3"><s:text name="general.scenarios"/></label></input></td>
						
			<!-- sumit start: Device Diagnostic Prototype -->
					<td class="diagnosticwiserow" title="click to list health monitored devices"><input  type="radio" id="radio4" name="wisetype" value="DEVICE_DIAGNOSTIC_WISE" class="wisetype wisetype1">
						<label for="radio4"><s:text name="general.diagnosis"/></label></input></td></tr>
						
			<!-- sumit end: Device Diagnostic Prototype -->
<!-- vibhu end --></table>
					</div>
						<div style="padding-top:10px;">
<!--							<select onchange="javascript:userDetails.changeHtml(this.value)">-->
<!--								<option value="DEVICE_TYPE_WISE">Device Wise</option>-->
<!--								<option value="LOCATION_WISE">Location Wise</option>-->
<!--							</select>-->
						</div>
					</div>
					<div id="deviceSection" class="deviceSection">
						<div class="devicegroup">
							<ul class="column" id="deviceListUl"></ul>
						</div>
					</div>
				</div>
			</div>
		<div style="float:right">
			<div id="cameraSection" class="cameraSection">
				<div class="cameramovietitle" id="cameramoviediv"><s:text name="home.msg.0011"></s:text> </div>
				<div class="cameramovieembed">
				<%
					String userAgent = request.getHeader("user-agent");
					if (userAgent.indexOf("MSIE") > -1) {
						%>
					<object classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921"
				    codebase="http://downloads.videolan.org/pub/videolan/vlc/latest/win32/axvlc.cab"
				       width="410" height="215" id="vlcPlayer" events="True">

					 <param name="Src" value="" />
					 <param name="ShowDisplay" value="True" />
					 <param name="AutoLoop" value="False" />
					 <param name="AutoPlay" value="True" />
					 </object>
				 <%
					}else{
						 %>
					<embed top="0" id="vlcPlayer" width="410px" height="215" volume="100" loop="no" autoplay="no" name="VLC" type="application/x-vlc-plugin" wmode="transparent"></embed>
						 <%
					}
				%>
				</div>				
				<div class="cameramenu">					
					<div class="stopplaybutton">
						<ul class="cameraplaybutton">
							<li><a href="getLiveStream.action" class="cameragechangelistener livestreamlink"><img src="<%=applicationName %>/resources/images/cameraplaybutton.png"/></a>
							</li>
							<li><a href="#" title="click to stop" class="cameragechangelistener stopLivestreamlink"><img src="<%=applicationName %>/resources/images/camerastopbutton.png"/></a>
							</li>
						</ul>
					</div>
					
				  <div id="presetsection"> </div>

					<div class="cameraadvancedfeatures">
						<div class="cameramovebuttondiv">
							<ul class="cameramovebuttondivul" title="click to move left" style="float:left; margin-top:15px;">
								<li><a class="camerapantiltclass cameragechangelistener" href="controlPanTilt.action" controlPantilt='L,5'><img src="<%=applicationName %>/resources/images/cameraleft.png"/></a></li>
							</ul>
							<ul class="cameramovebutton">
								<li style="height:13px;" ><a class="camerapantiltclass cameragechangelistener" title="click to move up" href="controlPanTilt.action" controlPantilt='U,5'><img src="<%=applicationName %>/resources/images/cameraup.png"/></a>
								</li>
								<li style="padding-top:1px; height:20px;" ><a class="camerapantiltclass cameragechangelistener" title="click for home" href="controlPanTilt.action" controlPantilt='H'><img src="<%=applicationName %>/resources/images/camerahome.png"/></a>
								</li>
								<li style="height:10px;" ><a class="camerapantiltclass cameragechangelistener" title="click to move down" href="controlPanTilt.action" controlPantilt='D,5'><img src="<%=applicationName %>/resources/images/cameradown.png"/></a>
								</li>
							</ul>
							<ul style="float:left; margin-top:15px; ">
								<li><a class="camerapantiltclass cameragechangelistener" href="controlPanTilt.action" title="click to move right" controlPantilt='R,5'><img src="<%=applicationName %>/resources/images/cameraright.png"/></a></li>
							</ul>
						</div>
						<div class="cameraclickmove" title="control Night Vision">
							<ul>
								<li style="float:left;padding-left:20px;padding-top:6px;display:none"><a href="captureIamge.action" class="cameragechangelistener imagecaptureclass"><img src="<%=applicationName %>/resources/images/cameracapturebutton.png"/></a></li>
								<li style="padding-right:15px;float:right;padding-top:5px;"><a class='nightVisionClass cameragechangelistener' href='controlNightVision.action' value='0'><img src="<%=applicationName %>/resources/images/cameralightbutton.png"/></a></li>							
								<li style="padding-left:52px;padding-top:6px;display:none"><a href="#"><img src="<%=applicationName %>/resources/images/cameravideo.png"/></a></li>
							</ul>
						</div>					
						<div id="Preset" style="float: right;width: 50%; text-align: center; ">
					
					<div id="camerapreset"  class="prestSelect cameragechangelistener" >
						<div>
						 	<select id="prestSelect"  name="prestSelect"  class="addpreset cameragechangelistener"  >
						 		

						 	</select>
						</div>	
					</div>
					</div>				
					</div>				
				</div>
			</div>
			
			<div class="alertsection">
				<div class="alerthedersection" id="alerthedersection">
				</div>
				<div id="alertbodysection" class="alertscontantsection">
					<table id="imvgalerttable" class="alertstables">
					<thead>
					<tr>
				 		<th class="alertstablecell"><s:text name="home.msg.0012" /> </th>
				 		<th style="text-align: left;"><s:text name="home.msg.0013" /></th>
				 		<th style="text-align: left;"><s:text name="home.msg.0014" /></th>
					</tr>
					</thead>
			 		<tbody>
			 		</tbody>
					</table>
				<div id="allrowdetails">
					<div id="rowdetails" style="float: left;margin-left:10px;">
					</div>
					<div id="nextlinkdiv" style="float: right;margin-right: 10px;" >
					</div>
					<div id="previouslinkdiv" style="margin-right:5px;float: right;" >
					</div>
				</div>
			</div>

		</div>
	</div>

	
        				
	</div>
    	
		<div id="overlay" onclick="closeoverlay()">
		
		
		</div>
			<div id="imageshowing"  class="imageshowdiv">
		</div>
			<div id="editmodal"></div>
				<div id="confirm"></div>
				<div id="message"></div>
				<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>

	</body>
</html>