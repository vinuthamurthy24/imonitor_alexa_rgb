<%-- Copyright � 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
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
			.listdeviceicon{
				width:45px;
				height:45px;
				margin-top:5px;
			}
			.deviceicon{
				width:45px;
				height:45px;
				margin-top:5px;
			}	
			a:link {text-decoration:none;} 		
		</style>
<%@include file="../messages.jsp" %>

		<script>
		
		if(window.history){
			window.history.forward();
		}
		
		function noBack() {
			if(window.history){
				window.history.forward();
			}
		}
		
		
		var DEVICE_TYPE_WISE = "DEVICE_TYPE_WISE";
		var LOCATION_WISE = "LOCATION_WISE";
		$(document).ready(function() {
                        $('#cameraview').hide();
			$('.ajaxlink').xpAjaxLink({target: 'contentsection'});
			
			//sumit start: avoid multiple ajax request
			$('.editlink').live('click', function(event){
				//event.handled = false;
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
					pTitle = "<s:text name='setup.msg.0001' />";
					
				}  

				try{
					if(event.handled !== true){
						$.ajax({
							  url: targeturl,
							  success: function(data){
								$('#editmodal').html(data);
								$('#editmodal').dialog('destroy');
								$('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true
								});
							  }
						});
						event.handled = true;	
					}
				}catch(ex){
					alert("Some Exception was caught.");
				}
				finally{}
				//sumit end:
				return false;
			});
			
			$('.editdeviceconfig').live('click', function(event){
				//event.handled = false;
				var targeturl = $(this).attr('href');
				var pHeight = $(this).attr('popupheight');
				var pWidth = $(this).attr('popupwidth');
				var pTitle = $(this).attr('popuptitle');
				if(pHeight == undefined){
					pHeight = 500;
					
				}
				if(pWidth == undefined){
					pWidth = 1000;
					
				}
				if(pTitle == undefined){
					pTitle = "<s:text name='setup.msg.0001' />";
					
				}  

				try{
					if(event.handled !== true){
						$.ajax({
							  url: targeturl,
							  success: function(data){
								$('#editmodal').html(data);
								$('#editmodal').dialog('destroy');
								$('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true
								});
							  }
						});
						event.handled = true;	
					}
				}catch(ex){
					alert("Some Exception was caught.");
				}
				finally{}
				//sumit end:
				return false;
			});
			
			// *********************************** sumit start: Sub-User Restriction ***********************************
			$('.customizeUserAccess').live('click', function(event){
				//event.handled = false;
				var targeturl = $(this).attr('href');
				var pHeight = $(this).attr('popupheight');
				var pWidth = $(this).attr('popupwidth');
				var pTitle = $(this).attr('popuptitle');
				if(pHeight == undefined){
					pHeight = 600;
				}
				if(pWidth == undefined){
					pWidth = 1000;
				}
				if(pTitle == undefined){
					pTitle = "<s:text name='setup.msg.0001' />";
				}  

				try{
					if(event.handled !== true){
						$.ajax({
							  url: targeturl,
							  success: function(data){
								$('#editmodal').html(data);
								$('#editmodal').dialog('destroy');
								$('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true,
									open: function(event, ui){			 
										$(this).parent().children().children('.ui-dialog-titlebar-close').hide();
									},
								});
							  }
						});
						event.handled = true;	
					}
				}catch(ex){
					alert("Some Exception was caught.");
				}finally{
					
				}
				return false;
			});
			// ************************************ sumit end: Sub-User Restriction ************************************
			
			$('.editroomlink').live('click', function(event){
				//event.handled = false;
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
					pTitle = "<s:text name='setup.msg.0001' />";
					
				}  

				try{
					if(event.handled !== true){
						$.ajax({
							  url: targeturl,
							  success: function(data){
								$('#editmodal').html(data);
								$('#editmodal').dialog('destroy');
								$('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true,
								open: function(event, ui) 
						{			 
							$(this).parent().children().children('.ui-dialog-titlebar-close').hide();
						},
								});
							  }
						});
						event.handled = true;	
					}
				}catch(ex){
					alert("Some Exception was caught.");
				}
				finally{}
				//sumit end:
				return false;
			});
			
			
			
			$('.Tonupdatedevicelink').live('click', function(){
				
				var targeturl = $(this).attr('href');
				var pHeight = $(this).attr('popupheight');
				var pWidth = $(this).attr('popupwidth');
				var pTitle = $(this).attr('popuptitle');
				
				if(pHeight == undefined){
					pHeight = 150;
					
				}
				if(pWidth == undefined){
					pWidth = 650;
					
				}
				if(pTitle == undefined){
					pTitle = "<s:text name='setup.msg.0001' />";
					
				}
				$.ajax({
					  	url: targeturl,
					  	success: function(data){
						$('#editmodal').html(data);
						$('#editmodal').dialog('destroy');
						$('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true,
						 open: function(event, ui) 
						{			 
							$(this).parent().children().children('.ui-dialog-titlebar-close').hide();
						},
						});
					  }
					});
				return false;
			});
			

			
			
			$('.Alarmlink').live('click', function(){
				var targeturl = $(this).attr('href');
				var pHeight = $(this).attr('popupheight');
				var pWidth = $(this).attr('popupwidth');
				var pTitle = $(this).attr('popuptitle');
				if(pHeight == undefined){
					pHeight = 250;
					
				}
				if(pWidth == undefined){
					pWidth = 300;
					
				}
				if(pTitle == undefined){
					pTitle = "<s:text name='setup.msg.0001' />";
					
				}
				$.ajax({
					  	url: targeturl,
					  	success: function(data){
						$('#editmodal').html(data);
						$('#editmodal').dialog('destroy');
						$('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true,
						 open: function(event, ui) 
						{			 
							$(this).parent().children().children('.ui-dialog-titlebar-close').hide();
						},
						});
					  }
					});
				return false;
			});
			
			//************************** sumit: Check if rule exists for a device, if YES then cuation the user. ********************
			$('.removedevicelink').live('click', function(){
			
			var form = $(this).closest("form");

				var targeturl = $(this).attr('href');
				var message = "";
				var toDisplay = null;
				var isFailure = true;
				//1.This call should get a message back.
				$.ajax({
					url: targeturl,
					async: false,
					success: function(data){
						var a= $(data).html().trim();
						var aa = a.split(":");
						if(aa[0] =='Failure')
						{
							isFailure = true;
							message = aa[1];
						}
						else
						{
							isFailure = false;
							message = a;
					}
				}
				});
				
				//3.Extract (device.generatedDeviceId=00:0e:8f:75:7c:9e-299&amp;device.gateWay.macId=00:0e:8f:75:7c:9e) from targeturl
				var deleteUrl = "removeDevice.action?" + targeturl.substring(26);
				//4.Proceed with deleting the device
				$("#confirm").dialog("destroy");
				//$("#confirm").html('Are you sure you want to remove this device ?<p style="color: red;"> This will result in deleting its entry in the corresponding rules!</p>');
				$("#confirm").html(message);
				$("#confirm").dialog({
					stackfix: true,
					modal: true,
					buttons: {
						'<s:text name="general.ok" />': function() 
						{
							$(this).dialog('close');
							
							//Change TargetUrl to removeDevice
							if(!isFailure)
							{
							$.ajax({
								  url: deleteUrl,
								  success: function(data){
								  }
								});
								("<s:text name='setup.devices.msg.0002' />");
							}
						},
						'<s:text name="general.cancel" />': function() 
						{
							$(this).dialog('close');
						}

					}
				});
				
				return false;
			});
			// **************************************************** sumit end ****************************************************************
			
			//*****************************************************Apoorva added for removing via unit****************************************
			
			$('.removevialink').live('click', function(){
			var form = $(this).closest("form");
		
				var targeturl = $(this).attr('href');
				var message = "";
				var toDisplay = null;
				var isFailure = true;
				//1.This call should get a message back.
				$.ajax({
					url: targeturl,
					async: false,
					success: function(data){
						var a= $(data).html().trim();
						var aa = a.split(":");
						 if(aa[0] =='Failure')
						{
							isFailure = true;
							message = aa[1];
						}
						else
						{
							isFailure = false;
							message = a;
					} 
				}
				});
				
				
				var deleteUrl = "removeVIADevice.action?" + targeturl.substring(26);
				//4.Proceed with deleting the device
				$("#confirm").dialog("destroy");
				//$("#confirm").html('Are you sure you want to remove this device ?<p style="color: red;"> This will result in deleting its entry in the corresponding rules!</p>');
				$("#confirm").html(message);
				$("#confirm").dialog({
					stackfix: true,
					modal: true,
					buttons: {
						'<s:text name="general.ok" />': function() 
						{
							$(this).dialog('close');
							
							//Change TargetUrl to removeDevice
							if(isFailure)
							{
							$.ajax({
								  url: deleteUrl,
								  success: function(data){
								  }
								});
								("<s:text name='setup.devices.msg.0002' />");
							}
						},
						'<s:text name="general.cancel" />': function() 
						{
							$(this).dialog('close');
						}

					}
				});
				
				return false;
			});
			
			
			
			
			
			
			//***************************************************Apoorva end****************************************************************
			
			
			//*****************************************Apoorva added for removing Slave******************************************************
			
			$('.removeSlavelink').live('click', function(){
			
			var form = $(this).closest("form");
		
				var targeturl = $(this).attr('href');
				var message = "";
				var toDisplay = null;
				var isFailure = true;
				//1.This call should get a message back.
				$.ajax({
					url: targeturl,
					async: false,
					success: function(data){
						var a= $(data).html().trim();
						var aa = a.split(":");
						 if(aa[0] =='Failure')
						{
							isFailure = true;
							message = aa[1];
						}
						else
						{
							isFailure = false;
							message = a;
					} 
				}
				});
				
				
				var deleteUrl = "removeSlaveDevice.action?" + targeturl.substring(26);
				//4.Proceed with deleting the device
				$("#confirm").dialog("destroy");
				//$("#confirm").html('Are you sure you want to remove this device ?<p style="color: red;"> This will result in deleting its entry in the corresponding rules!</p>');
				$("#confirm").html(message);
				$("#confirm").dialog({
					stackfix: true,
					modal: true,
					buttons: {
						'<s:text name="general.ok" />': function() 
						{
							$(this).dialog('close');
							//Change TargetUrl to removeDevice
							if(isFailure)
							{
							$.ajax({
								  url: deleteUrl,
								  success: function(data){
								  }
								});
								("<s:text name='setup.devices.msg.0002' />");
							}
						},
						'<s:text name="general.cancel" />': function() 
						{
							$(this).dialog('close');
						}

					}
				});
				
				return false;
			});
			
			//*****************************************Apoorva removeSLave end******************************************************
			
			
			//*****************************************Apoorva added for removing IDU******************************************************
			
			$('.removeIDUlink').live('click', function(){
			
			var form = $(this).closest("form");
				
				var targeturl = $(this).attr('href');
				var message = "";
				var toDisplay = null;
				var isFailure = true;
				//1.This call should get a message back.
				$.ajax({
					url: targeturl,
					async: false,
					success: function(data){
						var a= $(data).html().trim();
						var aa = a.split(":");
						 if(aa[0] =='Failure')
						{
							isFailure = true;
							message = aa[1];
						}
						else
						{
							isFailure = false;
							message = a;
					} 
				}
				});
				
				
				var deleteUrl = "removeIndoorUnit.action?" + targeturl.substring(26);
				//4.Proceed with deleting the device
				$("#confirm").dialog("destroy");
				//$("#confirm").html('Are you sure you want to remove this device ?<p style="color: red;"> This will result in deleting its entry in the corresponding rules!</p>');
				$("#confirm").html(message);
				$("#confirm").dialog({
					stackfix: true,
					modal: true,
					buttons: {
						'<s:text name="general.ok" />': function() 
						{
							$(this).dialog('close');
							//Change TargetUrl to removeDevice
							if(isFailure)
							{
							$.ajax({
								  url: deleteUrl,
								  success: function(data){
								  }
								});
								("<s:text name='setup.devices.msg.0002' />");
							}
						},
						'<s:text name="general.cancel" />': function() 
						{
							$(this).dialog('close');
						}

					}
				});
				
				return false;
			});
			
			//*****************************************Apoorva removeIDU end*************************************************
			
			
			//vibhu start
			$('.ToNupdateDevice').live('click', function()
			{
				var targeturl = $(this).attr('href');
				var pHeight = $(this).attr('popupheight');
				var pWidth = $(this).attr('popupwidth');
				var pTitle = $(this).attr('popuptitle');
				if(pHeight == undefined){
					pHeight = 250;
					
				}
				if(pWidth == undefined){
					pWidth = 300;
					
				}
				if(pTitle == undefined){
					pTitle = "<s:text name='setup.msg.0001' />";
					
				}
				$.ajax({
					  	url: targeturl,
					  	success: function(data){
						$('#editmodal').html(data);
						$('#editmodal').dialog('destroy');
						$('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true,
						 open: function(event, ui) 
						{			 
							$(this).parent().children().children('.ui-dialog-titlebar-close').hide();
						},
						});
					  }
					});
				return false;
			});
			
			//vibhu end
			
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

			$(".camerapantiltclass").live('click', function(){
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
			
			$(".stopLivestreamlink").live('click', function(){
				var vlc = document.getElementById("vlcPlayer");
				try{
					vlc.playlist.stop();
					vlc.playlist.clear();
				}catch(err){
				}
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
			$('.presetcameralink').hide();
			//$(".cameraadvancedfeatures").hide();

			$(document).keypress(function(e) { 
			    if (e.which == 27) {
			                 $('#overlay').hide();
			                 $('#imageshowing').hide();
			    }
			});
		
			// What should happen we we click on the play button of Player.
			$(".livestreamlink").die('click');
			$(".livestreamlink").live('click', function(){
				$("#deviceListUl").data("Stream",$(this).attr('deviceid')).data("DeviceID",$(this).attr('deviceid'));
				var clickedLink = $(this);
				var url = $(this).attr('href') + "?__=" + new Date();
				var deviceId = $(this).attr('deviceid');
				var gateWayId = $(this).attr('gateWayId');
				var motiondetection = $(this).attr('motiondetection');
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
						alert(mrl); 
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
						if(motiondetection == 'true'){
							$(".cameraadvancedfeatures").show();
						}else{
							$(".cameraadvancedfeatures").hide();
						}
						
							
					}
				});
				return false;
			});

				
			$('.presetcameralink').die('click');
			$('.presetcameralink').live('click', function(){

				//var inHTML = "<div class=''><span>"+formatMessage("setup.devices.msg.preset.set9")+"</span></div>";

									
					var device = $(this).attr('device');
					var macid = $(this).attr('macid');
					var url = "getLiveStream.action?__=" + new Date();
				$('#prestSelect').attr('deviceid',device);
				$('#prestSelect').attr('gateWayId',macid);
				
		
				$(".cameragechangelistener").attr('deviceid', $(this).attr('device'));
				$(".cameragechangelistener").attr('gateWayId', $(this).attr('macid'));
				/*$(".cameragechangelistener").attr('motiondetection', $(this).attr('motiondetection'));
				$(".cameragechangelistener").attr('controlNightVision', $(this).attr('controlNightVision'));*/
				

				var params = {"device.generatedDeviceId":device,"device.gateWay.macId":macid};
				$.ajax({
					url: url,
					dataType: 'xml',
					data: params,
					async: false,

					success: function(data)
						  {
							var mrl = $.trim($($(data).find('mrl').get(0)).text());
							if(mrl.indexOf("rtsp") == 0)
							{
								$('#cameraview').dialog('destroy');
								$('#cameraview').dialog({height: 400, width: 500, title: formatMessage("setup.devices.msg.preset.set7"), modal:true})
								var vlc = document.getElementById("vlcPlayer");
								var options = new Array(":aspect-ratio=4:3", "--rtsp-tcp");
								vlc.playlist.clear();
								var id = vlc.playlist.add(mrl, "Imonitor", options);
								vlc.playlist.playItem(id);
							}
							else
							{
								alert("Connection is not astablished between camera and server. Please try again later.");
							}
				
						  },
					error: function(data)
						  {
							alert("Unable to play video from the camera. Please try later.");
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
				 form.html("<img src='../resources/images/loading.gif'/><br><b>Please wait ...</b>");
				 $.post(act, params, function(data){
					var a= $(data).find("p.message").text();
					var aa = a.split(":");
					if(aa[0] =='Failure'){
						$('#editmodal').dialog('close');
						//$("#ErrorMessage").html(aa[1]);
						showResultAlert(aa[1]);
					}else if(aa[0] =='Success'){
						$('#editmodal').dialog('close');
						//$("#ErrorMessage").html(aa[1]);
						showResultAlert(aa[1]);
						$('#contentsection').html(data);
					}else if(aa[0] =='Sucess'){
						$('#editmodal').dialog('close');
						//$("#ErrorMessage").html(aa[1]);
						showResultAlert(aa[1]);
						$('#contentsection').html(data);
					}else{
						 $('#contentsection').html(data);
						 $('#editmodal').dialog('close');
						 var message = $("#messageSection").html();
						 if(message == "" || message == null || message == undefined){
							 message = "<s:text name='general.op.completed' />";
						 }
						 showResultAlert(message);
						 $('#contentsection').html(data);
					}
					$('#editmodal').dialog('close');
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
			/*
			$("#valueForBattery").live('change', function(){
				var currentSelect=$(this);
				var selectedVal=currentSelect.val();
				var intRegex = /^\d+$/;
				var errormessage = "Please enter the value range between(0-99).";
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
			});
			$(".valuefordimmer").live('change', function(){
				
				var currentSelect=$(this);
				var devicetype = currentSelect.attr('devicetype');
				var selectedVal=currentSelect.val();
				var errormessage = " ";
				if((devicetype*1)==1){
					errormessage = "Please enter the value range between(0-99).";
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
					errormessage = "Please enter the value range between(16-30).";
					var intRegex = /^\d+$/;
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
			});*/
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

			$('.ajaxsuspenandrevoke').live('click', function() {
				var dataTableUrl = $(this).attr("dataTableUrl");
				var dataTableId = $(this).attr("dataTableId");
				var type= $(this).attr("type");
				var textmessag ="";
				if(type*1==1){
					textmessag="<s:text name='setup.user.msg.0001a' />";
					}else{
						textmessag="<s:text name='setup.user.msg.0002a' />";
						}
				$("#confirm").dialog("destroy");
				$("#confirm").html(textmessag);
						
				var href = $(this).attr('href');
				$("#confirm").dialog({
						stackfix: true,
						modal: true,
						buttons: {
							'<s:text name="general.ok" />': function() {
								$(this).dialog('close');
								
					 $.get(href,function(data){
						$('#message').html(data);
						$('#message').dialog('open');
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
							'<s:text name="general.cancel" />': function() {
								$(this).dialog('close');
							}

						}
					});
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
		$("#confirm").dialog("destroy");
		$("#confirm").html('Do you wish to set preset'+sel.options[sel.selectedIndex].id+'?');
				$("#confirm").dialog({
					position: [361,469],
					buttons: {
						Set: function() 
							{
							$(this).dialog('close');
								var href="setToCameraPreset.action";
							
							$.ajax({
							url:href,
							data: params,
							cache:false,
							success: function(data)
							{
								$("#confirm").html(data);
								$("#confirm").dialog('open');
								$("#confirm" ).dialog({	
										position: [361,469],
											buttons: {
											Ok:function(){
												$(this).dialog('close');
                                                $("#confirm").dialog("destroy");												
											}
										}
										});
							}
							});
						//showResultAlert("<s:text name='setup.devices.msg.0004' />");
						},
						'<s:text name="general.cancel" />': function() {
							$(this).dialog('close');
							$(this).dialog("destroy");
						}

					}
				});
			sel.options[0].selected = true;
				return false;
			});


		//<!--pari end


			
			// ********************************************* sumit start: Schedule Activate/De-Activate *******************************
			$('.activatedeactivateschedule').live('click', function(){
				var targeturl = $(this).attr('href');
				var activationRequest = $(this).attr("activation");
				var schid = $(this).attr("schid");
//alert(schid);
				var anchor = $(this);

				if(activationRequest == 0){
					$("#confirm").dialog("destroy");
					$("#confirm").html("<s:text name='setup.sched.msg.0002' />");
				}
				else if(activationRequest == 1){
					$("#confirm").dialog("destroy");
					$("#confirm").html("<s:text name='setup.sched.msg.0001' />");
				}
				$("#confirm").dialog({
					stackfix: true,
					modal: true,
					buttons: {
						'<s:text name="general.ok" />': function() {
							$(this).dialog('close');
							$.ajax({
								  url: targeturl,
								  success: function(data){   
									  	//check if message is for success or not
									  	var mssg = $(data).find("p.message").text();
									  	var mssg1 = mssg.split(":");
									  	if(mssg1[0] == 'Success'){
									  		if(activationRequest == 0){
												anchor.attr("activation","1");
												anchor.attr("href","toActivateSchedule.action?schedule.id="+schid);
												anchor.attr("title","Activate Schedule");
												anchor.find('img').attr('src','/imonitor/resources/images/update_icon.png');
							
							
											}
											else if(activationRequest == 1){
												anchor.attr("activation","0");
												anchor.attr("href","toDeactivateSchedule.action?schedule.id="+schid);
												anchor.attr("title","De-activate Schedule");
												anchor.find('img').attr('src','/imonitor/resources/images/suspend.png');
											}
									  	}
										
										
										$('#message').html(mssg1[1]);
										$('#message').dialog('open');
										$("#message" ).dialog({
											stackfix: true,
											modal: true,
											buttons: {
											Ok:function(){
												$(this).dialog('close');
											}
										}
										});
								  }
							});
						},
						'<s:text name="general.cancel" />': function() {
							$(this).dialog('close');
						}

					}
				});
				return false;
			});
			// ********************************************** sumit end: Schedule Activate/De-Activate **********************************
			
			$('.deleteandreloadtablelink').live('click', function() {
				var dataTableUrl = $(this).attr("dataTableUrl");
				var dataTableId = $(this).attr("dataTableId");
				$("#confirm").dialog("destroy");
				$("#confirm").html("<s:text name='setup.msg.0002' />");
						
				var href = $(this).attr('href');
				$("#confirm").dialog({
						stackfix: true,
						modal: true,
						buttons: {
							<s:text name="general.ok" />: function() {
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
							<s:text name="general.cancel" />: function() {
								$(this).dialog('close');
							}

						}
					});
					 return false;
				});

				$(".changecamera").live('click', function(){
				// Cancelling the click, if the player is playing the same device.
				if($(".changecamera").hasClass("streamingstarted")){
				var Streamvalue = $("#deviceListUl").data("Stream");
				var deviceid=$(this).attr('deviceid');
					if(Streamvalue != deviceid)
					{
						alert("Already streaming for camera "+Streamvalue+". Please stop the stream and try again.");
					}
					return false;
				}
				$(".cameragechangelistener").attr('deviceid', $(this).attr('deviceid'));
				$(".cameragechangelistener").attr('gateWayId', $(this).attr('gateWayId'));
				$(".cameragechangelistener").attr('motiondetection', $(this).attr('motiondetection'));
				$(".cameragechangelistener").attr('controlNightVision', $(this).attr('controlNightVision'));
				
				var deviceName = $(this).attr('devicename');
				$("#cameramoviediv").html("The Selected Camera : " + deviceName);
				// Changing the now selected camera.
				$(".changecamera").removeClass('nowselectedcamera');
				$(this).addClass('nowselectedcamera');
				$(".livestreamlink").click();
				return false;
			});



//check all check boxes
			$("#checkallday").live('click', function(){
				if ($(this).is(':checked')) {  
					 $(".dayofweek input").each(function() {
					$(this).attr('checked', true);
						   });
					}
				else if ($(this).not(':checked')) {
					 $(".dayofweek input").each(function() {
							$(this).attr('checked', false);
								   });
					}
				});

			$("#checkallmonth").live('click', function(){
				if ($(this).is(':checked')) {  
					 $(".month input").each(function() {
					$(this).attr('checked', true);
						   });
					}
				else if ($(this).not(':checked')) {
					 $(".month input").each(function() {
							$(this).attr('checked', false);
								   });
					}
				});	
			//sumit start	
			$('.currentstep').live('change', function(e) {
				Xpeditions.validateElement($(this));
			});
			$('.currentstep').live('keyup', function(e) {
				Xpeditions.validateElement($(this));
			}); 
			//sumit end

			// Validation of form starts here.
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
			// Validation of form ends here.
			$( "#message" ).dialog({
				height: 100,
				width: 400,
				modal: true,
				autoOpen:false,
				buttons: {
					<s:text name="general.ok" />: function() {
							$(this).dialog('close');
						}
					}
			});
			
			
			var handleSuccess = function(xml){
			//var serializer = new XMLSerializer();
				//var string = serializer.serializeToString(xml); 
				//alert(string);
				// 1. Handling the storage.
				var storage = $(xml).find("storage");
				var gateWay = $(xml).find("gateway");
				userDetails.UserInformationHtml(storage);
				var featuresEnabled = gateWay.find("featuresEnabled").text();
				if((featuresEnabled & 4)==0){
					
				/* 	$('#dashboard').hide();
					$('#dashboard').addClass('displayclass'); */
					
					//Disabling dashboard start
					$('#dashboard').show();
					$('#dashboard').removeClass('displayclass');
					$('#dashboard').css("opacity", "0.5");
					$('#dash').removeAttr("href");
					
					$("#dash").click(function(){
					     alert("Dashboard feature is disabled.");
					    //$(document).off('click.once'); 
					     event.stopImmediatePropagation();
					}); 
					
					//Disabling dashboard end
						}
					else if((featuresEnabled & 4)==4)
					{
					$('#dashboard').show();
					$('#dashboard').removeClass('displayclass');
					}
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
	
	
			var viewRefresher = function(){
					showUserDetails();
				setTimeout(viewRefresher,100000);
			};
			viewRefresher();
		});
		
//bhavya added		
		$('.ajaxform').live('submit', function() {
			var form = $(this).closest("form");
			 var params = form.serialize();
			 $.post(form.attr('action'), params, function(data){
				//bhavya start
				 //$('#contentsection').html(data);
				 // If the popup is open, close it.
				 //$('#editmodal').dialog('close');
				var a= $(data).find("p.message").text();
					var aa = a.split("~");
					if(aa[0] =='Failure')
					{
						$('#editmodal').dialog('close');
						showResultAlert(aa[1]);
						$("#ErrorMessage").html(aa[1]);
					}
					else if(aa[0] == 'Sucess')
					{
						$('#editmodal').dialog('close');
						showResultAlert(aa[1]);	
					}
					else{
					$('#contentsection').html(data);
				 // If the popup is open, close it.
					$('#editmodal').dialog('close');
					}
				//bhavya end
			 });
			 return false;
		});
			
			function showResultAlert(message) {
		$("#confirm").dialog("destroy");
		 $("#confirm").html(message);
			$("#confirm").dialog('open');
			$("#confirm" ).dialog({
				stackfix: true,
				modal: true,
				buttons: {
				<s:text name="general.ok" />:function(){
					$(this).dialog('close');
					$("#confirm").html("");
					$("#confirm").dialog("destroy");
				}
			}
			});
		}
			
	//bhavya end		
/*
		function validateTextArea(textAreaAd)
		{
		    if((textAreaAd*1)<0){
		      alert("Please enter Value range 0-99 only!");
		     // document.getElementById(id).focus();
		    	return true;
		    }else{
		      return false;
		    }
		}

		function validateTextAreaForAc(textAreaAd)
		{
		    if(((textAreaAd*1)<16) || ((textAreaAd*1)>28)){
		      alert("Please enter Value range 16-28 only!");
		     // document.getElementById(id).focus();
		    	return true;
		    }else{
		      return false;
		    }
		}
		function validateTextBox(value)
		{
		    if(value == ''){
		      alert("Please enter Value range 16-28 only!");
		     // document.getElementById(id).focus();
		    	return true;
		    }else{
		      return false;
		    }
		}
		*/
		function CheckMainuser() 
		{
			<s:if test="#session.user.role.name == 'mainuser' ">
		 	return 1;
		 	</s:if>
		 	<s:else>
		 	return 0;
		 	</s:else>
		}
		function toggleSelectedTab(anchor)
		{
			$(".setupselectedtab").attr("class","setuptab");
			anchor.parent().attr("class","setupselectedtab");
		}

        function presetval(value)
		{
			
			$("#prestSelect").val(value);
			
		}
		function openHelpPage()
			{
				window.open ('<%=applicationName %>/resources/docs/USER MANUAL IMONITOR.pdf', 'newwindow');
			}
		</script>
	</head>
	<body onload="noBack();" class="bodyclass">
	<body class="bodyclass">
		<div class="maindiv">			
			<div class="titlebg">
				<div class="welcomeuser">
					<span style="color:#0C5AA3;"><s:text name="home.msg.0009" /></span> <s:property value="#session.user.userId"/><br><span style="margin-left: -39px; color:#0C5AA3;"><s:text name="home.msg.0017" /></span><s:date name="#session.user.lastLoginTime" format="dd/MM/yy 'at' hh:mm a"/>
				</div>
				<div>
					<ul class="ulmenu topmenu" lihoverclass="hoverstyle">
						<li class="menuitem"><a href="home.action" title="Home" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.main" /></div></a></li>
						<li class="menuitem selectedtopmenu" ><a href="setup.action" title="To Configure Device" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.setup" /></div></a></li>
						<li class="menuitem"><a href="getdeviceforalerts.action" title="View Alerts" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.alerts" /></div></a></li>
						<li class="menuitem"><a href="getlistofLocation.action" title="Prespective View" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.advance" /></div></a></li>
						<li class="menuitem"  id="dashboard"><a id="dash" href="getlistofLocationforEnergyMg.action" title='<s:text name="Tooltip.Energy" />' class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.dashboard" /></div></a></li>
						<li class="menuitem"><a href="../logout.action" title="Sign Out" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.signout" /></div></a></li>					
					</ul>
				</div>
					<img class="logo" src="<%=applicationName %>/resources/images/logo.png"/>											
			<div>
					<img class="helpicon" title="help" src="<%=applicationName %>/resources/images/help.png" onclick='javascript: openHelpPage()' />
				</div>
			</div>
			
			<table class="kg">
						<tr class="kg0">
						<s:if test="#session.user.role.name == 'mainuser' ">
						<td class="setupselectedtab" ><br/><a href="listLocation.action"    class="ajaxlink" onClick="javascript: toggleSelectedTab($(this))"><div><br/><s:text name="general.rooms" /></div></a></td>
						<td class="setuptab" ><br/><a href="toGetDevices.action"    class="ajaxlink" onClick="javascript: toggleSelectedTab($(this))"><div><br/><s:text name="general.devices" /></div></a></td>
						<td class="setuptab" ><br/><a href="toNotifications.action" class="ajaxlink" onClick="javascript: toggleSelectedTab($(this))"><div><br/><s:text name="general.notifications" /></div></a></td>
						<td class="setuptab" ><br/><a href="toAddNewAction.action" class="ajaxlink" onClick="javascript: toggleSelectedTab($(this))"><div><br/><s:text name="general.actions" /></div></a></td>
						<td class="setuptab" ><br/><a href="listRule.action"        class="ajaxlink" onClick="javascript: toggleSelectedTab($(this))"><div><br/><s:text name="general.rules" /></div></a></td>
						<td class="setuptab" ><br/><a href="listSchedule.action"    class="ajaxlink" onClick="javascript: toggleSelectedTab($(this))"><div><br/><s:text name="general.schedules" /></div></a></td>
						<td class="setuptab" ><br/><a href="listScenario.action"    class="ajaxlink" onClick="javascript: toggleSelectedTab($(this))"><div><br/><s:text name="general.scenarios" /></div></a></td>
						<td class="setuptab" ><br/><a href="toAddsystemAlert"       class="ajaxlink" onClick="javascript: toggleSelectedTab($(this))"><div><br/><s:text name="general.sysalerts" /></div></a></td>
						<td class="setuptab" ><br/><a href="editCustomerByuser"     class="ajaxlink" onClick="javascript: toggleSelectedTab($(this))"><div><br/><s:text name="general.profile" /></div></a></td>
						<td class="setuptab" ><br/><a href="toUserList.action"      class="ajaxlink" onClick="javascript: toggleSelectedTab($(this))"><div><br/><s:text name="general.users" /></div></a></td>
						</s:if>
						<!--   <td class="kg1" onmouseout='javascript: this.setAttribute("class", "kg1")' onmouseover='javascript: this.setAttribute("class", "kg2")'><br/><a href="toChangeUserPassword" class="ajaxlink">My Accounts</a></td>-->
						
						</tr>
						
						<tr>
							<td>
								<div id="contentsection" class="contentsection">
									<s:if test="#session.user.role.name == 'mainuser' ">

									<s:include value="/view/mainuser/setup/locations.jsp"></s:include>
									</s:if>
									<s:else>
									<s:include value="/view/mainuser/usermanagement/toChangeUserPassword.jsp"></s:include>
									</s:else>
			
								</div>
							</td>
						</tr>
						
						</table>
				<div id="editmodal"></div>
				<div id="confirm"></div>
				<div id="message"></div>
				<div id="cameraview">
					<div id="cameraSection" class="cameraSection">
						<div class="cameramovietitle" id="cameramoviediv"><s:text name="setup.devices.msg.preset.set8" /></div>
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
					  <div id="presetsection"> </div>

						
					<div class="cameraadvancedfeatures">
						<div class="setupcameramovebuttondiv">
							<ul class="cameramovebuttondivul" style="float:left; margin-top:15px;">
								<li><a class="camerapantiltclass cameragechangelistener" title='Click to move left' href="controlPanTilt.action" controlPantilt='L,5'><img src="<%=applicationName %>/resources/images/cameraleft.png"/></a></li>
							</ul>
							<ul class="cameramovebutton">
								<li style="height:13px;" ><a class="camerapantiltclass cameragechangelistener" title='Click to move up' href="controlPanTilt.action" controlPantilt='U,5'><img src="<%=applicationName %>/resources/images/cameraup.png"/></a>
								</li>
								<li style="padding-top:1px; height:20px;" ><a class="camerapantiltclass cameragechangelistener" title='Click for home' href="controlPanTilt.action" controlPantilt='H'><img src="<%=applicationName %>/resources/images/camerahome.png"/></a>
								</li>
								<li style="height:10px;" ><a class="camerapantiltclass cameragechangelistener" title='Click to move down' href="controlPanTilt.action" controlPantilt='D,5'><img src="<%=applicationName %>/resources/images/cameradown.png"/></a>
								</li>
							</ul>
							<ul style="float:left; margin-top:15px; ">
								<li><a class="camerapantiltclass cameragechangelistener" title='Click to move right' href="controlPanTilt.action" controlPantilt='R,5'><img src="<%=applicationName %>/resources/images/cameraright.png"/></a></li>
							</ul>
						</div>
						<div class="setupcameraclickmove">
							<ul>
								<li style="float:left;padding-left:20px;padding-top:6px;display:none"><a href="captureIamge.action" class="cameragechangelistener imagecaptureclass"><img src="<%=applicationName %>/resources/images/cameracapturebutton.png"/></a></li>
								<li title='Click to control night vision' style="padding-right:15px;float:right;padding-top:5px;"><a class='nightVisionClass cameragechangelistener'  href='controlNightVision.action' value='0' ><img src="<%=applicationName %>/resources/images/cameralightbutton.png" /></a></li>							
								<li style="padding-left:52px;padding-top:6px;display:none"><a href="#"><img src="<%=applicationName %>/resources/images/cameravideo.png"/></a></li>
							</ul>
						</div>					
					</div>
					</div>
					<div id="Preset" style="float: left;width: 100%; text-align: center; ">
					
					<div id="camerapreset"  class="SetupPreset cameragechangelistener" >
						<div>
						 	<select id="prestSelect"  name="prestSelect"  class="addpreset cameragechangelistener"  >
						 		<option id="0" >Set a preset</option>
						 		<option id="1" >preset1</option>
						 		<option id="2" >preset2</option>
						 		<option id="3" >preset3</option>
						 		<option id="4" >preset4</option>
						 	</select>
						</div>	
					</div>
				</div>
				</div>
			<div class="boottom">					
			</div>				
		</div>
			
	</body>
</html>