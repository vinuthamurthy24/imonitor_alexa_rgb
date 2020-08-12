 <%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ page language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>

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
		
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.7.1.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui-1.7.3.custom.min.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/ui.core.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/ui.draggable.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/ui.droppable.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/ui.sortable.js"></script>
		
		<script type="text/javascript" src="<%=applicationName %>/resources/js/effects.blind.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/effects.bounce.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/effects.slide.js"></script>
	<!-- 	<script type="text/javascript" src="<%=applicationName %>/resources/js/effects.shake.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/"></script> -->
		
	<!-- 	<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.mouse.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.slider.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.sortable.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.droppable.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.draggable.js"></script> -->
		
	
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.user.js"></script>
	
		<style>
		
		#slider  .ui-slider-range { background: #729fcf; }
			#slider  .ui-slider-handle { border-color: #729fcf; }

			#slider1  .ui-slider-range { background: #729eaf; }
			#slider1  .ui-slider-handle { border-color: #729eaf; }
			
	#gatewaySection{
	    left: 530px;
	    line-height: 26px;
	    position: absolute;
	    float:right;
	    top: 3px;
	}
	.gatewayidsection {
	position:relative;
	float:right;
	left:300px;
	top:7px;
	margin-left: 5px;
	color:#555444; 
	font-size:14px;
	}
	
	.gatewayimgsection img {
		left: 95px;
		position:relative;
		float:left;
		top:10px;
		padding-left: 2px;
	}
	
	#DeviceControl .ui-dialog-titlebar { display: none;}
	
	.ui-dialog-titlebar-close
	{
	   
		display: block;
		position: fixed;
		right: 12px;
		top: 11px;
		height: 20px;
		width: 20px;
		background-color: #4B5667;
		font-size: 0px;
		border: 1px solid #9D9D9D;
	}
/*	#content {
  margin: 80px 70px;
  text-align: center;
  -moz-user-select: none;
  -webkit-user-select: none;
  user-select: none;
}*/



/* Slots for final card positions */

/*#cardSlots {
  margin: 50px auto 0 auto;
  background: #ddf;
}*/

/* The initial pile of unsorted cards */

#DeviceDiv {
  margin: 0 auto;
  background: #ffd;
}



 #DeviceDiv {
  width: 1024px;
   float: left;
  height: 120px;
  padding: 2px;
  border: 2px solid #333;
  -moz-border-radius: 10px;
  -webkit-border-radius: 10px;
  border-radius: 10px;
  -moz-box-shadow: 0 0 .3em rgba(0, 0, 0, .8);
  -webkit-box-shadow: 0 0 .3em rgba(0, 0, 0, .8);
  box-shadow: 0 0 .3em rgba(0, 0, 0, .8);
}

/* Individual cards and slots */

/*#cardSlots div
{
	float: left;
  width: 55px;
  height: 75px;
  padding: 2px;
  padding-top: 2px;
  padding-bottom: 0;
  border: 2px solid #333;
  -moz-border-radius: 10px;
  -webkit-border-radius: 10px;
  border-radius: 7px;
  margin: 0 0 0 10px;
  background: #fff;
}*/

 #DeviceDiv div {
  float: left;
  width: 55px;
  height: 75px;
  padding: 2px;
  padding-top: 2px;
  padding-bottom: 0;
  border: 2px solid #333;
  -moz-border-radius: 10px;
  -webkit-border-radius: 10px;
  border-radius: 7px;
  margin: 0 0 0 10px;
  background: #fff;
}

/*#cardSlots div:first-child,*/ #DeviceDiv div:first-child {
  margin-left: 0;
}

/*#cardSlots div.hovered {
  background: #aaa;
}*/


#DeviceDiv div {
  background: #666;
  color: #fff;
  font-size: 50px;
  text-shadow: 0 0 3px #000;
}

#DeviceDiv div.ui-draggable-dragging {
  -moz-box-shadow: 0 0 .5em rgba(0, 0, 0, .8);
  -webkit-box-shadow: 0 0 .5em rgba(0, 0, 0, .8);
  box-shadow: 0 0 .5em rgba(0, 0, 0, .8);
}

.ui-state-disabled, .ui-widget-content .ui-state-disabled, .ui-widget-header .ui-state-disabled {
opacity: 1.35;

}

</style>
<%@include file="../messages.jsp" %>
<script type="text/javascript">

if(window.history){
	window.history.forward();
}

function noBack() {
	if(window.history){
		window.history.forward();
	}
}


var userDetails = new UserDetails();
var DEVICE_TYPE_WISE = "DEVICE_TYPE_WISE";
var LOCATION_WISE = "LOCATION_WISE";
var lastSelectedlocation="";
var lastSelectedLocationId="";
var DragEventOnCardDiv="";
var DragEventOnDeviceDiv="";
var RepositionEvent="";
var Path="";
var DialogOpen =  {};

	$(document).ready(function() {
		Path='<s:property value="imvgUploadContextPath"/>';
		
		$('#cameraview').hide();

		/*var offset = $("#cardSlots").offset();
		 alert("TOP:"+offset.top+"LEFT:"+offset.left);*/
		
		$(".GetDevicePerLocation").live('click',function(){	
			var url = $(this).attr('href');
			var location = $(this).attr('location');
			
			alert("url:"+url+"location:"+location);
			/*$.ajax({
				url:url,
				cache: false,
				dataType: 'xml',
				data: params,
				success: handleAlertSuccess
			});*/
			
			return false;
		});
	
	
/*	var handleSuccessForLocation = function(data){
		//var string = (new XMLSerializer()).serializeToString(data);
		//alert(string);
		//var totalcount =$(data).find('totalcount').text();
		$(data).find("location").each(function() 
		{
			var id = $(this).find('id').text();
			var name = $(this).find('name').text();
			var details = $(this).find('details').text();
			var tablerows="<tr class='locationtable'><td style='text-align:left;'><a class='GetDevicePerLocation' href='GetDevicePerLocation.action' id="+id+">"+name+"</a></td></tr>";
			$('#listLocations').find('tbody').append(tablerows);
		});
		
	};

	
	
	var showUserDetailsByLocations = function()
	{
		var lUrl = "listlocationsForMap.action";
		$.ajax({
			url: lUrl,
			cache: false,
			dataType: 'xml',
			success: handleSuccessForLocation
		});
		
	};
	showUserDetailsByLocations();*/
	function GetFtpPath()  
	{
		return Path;
	};
	
	
	var handleGateWay = function(gWay){
		var gateWay = $(gWay);
		var macId = gateWay.find("macid").text();
		var status = gateWay.find("status").text();
		userDetails.addGateWay(macId);
		userDetails.setGateWayStatus(macId, status);
		gateWay.find("device").each(function(index, deviceObj) {
			userDetails.setDeviceDetails(macId, this);
		});
		// Generating the html contents.
	};
	var handleSuccess = function(xml){
		//arrangeGateWaySectionForSetup(xml);
		// 1. Handling the gatewy.
		userDetails.setGateWayCount($(xml).find("gateway").size());
		$(xml).find("gateway").each(function() {
			handleGateWay(this);
		});
		
		userDetails.generateHtmlForLocationInMap(Path);
		$(".firstLocation").click();
		
		$(xml).find("device").each(function(index, deviceObj) 
		{
			if($("#DeviceControl").dialog( "isOpen" ))
			{
				var Dialog=DialogOpen[1];
				var deviceId = $(this).find("id").text();
				if(Dialog==deviceId)
				{
				var deviceLi=userDetails.changeDeviceDetailsForControlDeviceFromMap(deviceObj);
				$("#DeviceControl").html(deviceLi).dialog({modal: true}).dialog('open');
				}
			}
						
		});
	};
	var showUserDetails = function(){
		$.ajax({
			url: "userdetails.action",
			cache: false,
			dataType: 'xml',
			success: handleSuccess
		});
		setTimeout(showUserDetails,10000);
	};
	showUserDetails();
	
	
	$('#DeviceDiv div').on("click","div", function(event){
	   	alert("CLICLLLLL");	       
	});
	
	  
	
	
	$('#cardSlots').delegate('div.controlDevice', 'click', function(event) {
		
	 	$("#DeviceControl").dialog( "destroy" );
	 	
	 	if(!($(this).hasClass('ui-draggable-disabled')))
		{
			return false;
		}
	 	
    	var deviceid = $(this).attr('deviceid');
        var status = $(this).attr('status');
		var deviceType = $(this).attr('deviceType');
		var dailogwidth=225;
		if(deviceType == 'Z_WAVE_AC_EXTENDER')
		{
		dailogwidth=405;
		}
    	status="false";
        if(status =="false")
    	{
        
        	
        	var Divwidth=$("#cardSlots").width();
        	var elmentwidth=$(this).width();
        	var elementleft=$(this).position().left;
        	
        	var left=$(this).offset().left;
        	var top=$(this).offset().top;
        	
        	if(Divwidth-(elmentwidth+elementleft)<0)
        	{
        		left=left-elmentwidth;
        		if(left>810)
        		{
        			left=810;
        		}
        		
        	}
  		  var deviceLi=GetDeviceUsing(deviceid);
		  $("#DeviceControl").html(deviceLi);
			  /* $("#DeviceControl").position({
				   my: "left top",
				   at: "right bottom",
				   of: "#cardSlots",
				   //collision: "fit flip"
				 });*/
				 
				 
			   $("#DeviceControl").dialog({
				   create: function(event,ui) 
				    {
					  //$(this).effect("slide", { times:3 }, 2000);
					   //$(this).show("slide", { direction: "down" }, 1000);
				    },
					stackfix: true,
					modal:true,
					draggable: false,
					resizable: false,
					stack: true,
					width: dailogwidth,
					//height:70,
					minHeight: 135,
					
					autoOpen: false,
					show:"slide",
					/*show:{
						effect: 'slide', 
				        direction: "right",
				    },*/
				   /* hide: {
			             effect: "fade",
			             duration: 2000
			         },
					/*{
						effect: 'bounce', duration: 350, times: 3,
				        direction: "right",
				    },*/
					hide:"explode",
					//autoResize:true,
					
				  	position: [left,top],
				   	//position:['right','top'],
				   /*	show:function() { 
				        
				        var $this = $(this);
				        $this.effect("bounce", {duration: 350,times: 3});
				     
				        
				    }*/
				   
				    open: function(event, ui) 
				    {
					     if(deviceType == 'Z_WAVE_AC_EXTENDER')
		                     {
		                   $(this).parent().children('.ui-dialog-titlebar').css({background: "#5E9FA8",margin: "3px 0px -32px 377px" ,width: "2px"});
		                     }
							 else
							 {
				    	 $(this).parent().children('.ui-dialog-titlebar').css({background: "#5E9FA8",margin: "3px 0px -32px 192px" ,width: "2px"});
						 }
						 $(this).parent().children().children('.ui-dialog-titlebar-close').css({margin: "-11px 2px 0px 0px"});
				    	/*$(".ui-dialog-titlebar").css({background: "#5E9FA8",margin: "3px 0px -32px 192px" ,width: "2px"});
				    	$(".ui-dialog-titlebar-close").css({margin: "-11px 2px 0px 0px"});
				    	 //$(this).effect("pulsate", { times:3 }, 2000);*/
				    	$(this).show("slide",{direction: "down"});
				    	
				    },
				    close: function(event, ui) 
				    { 
				    	//$(this).hide("slide",{direction: "down"});
				    }
				   	
					});
			   $("#DeviceControl").dialog("open");
			   $("#DeviceControl").dialog("option", "show", { effect: 'slide', direction: "up" } );
			   DialogOpen[1]=deviceid;
		return false;
 		}
    	else
    	{
    		return false;
    	}
	   
	});
	/* $(".controlDevice").live('click',function(){
		 	

	 });*/

	$(".updatedevicelink").live('click', function(){
		$(this).html('<img src="../resources/images/loading.gif" class="rightsideimageforaccontrol"/>');

		//vibhu change to stop refresh if command execution in progress
		var ele = $(this); 
		ele.attr("commandexecutioninprogress","true");

		var url = $(this).attr('href');
		url += "?timestamp=" + escape(new Date());
		var deviceId = $(this).attr('deviceId');
		var gateWayId = $(this).attr('gateWayId');
		var commandParam = $(this).attr('commandParam');
		var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
		$.ajax({
			url: url,
			dataType: 'xml',
			data: params,
			success: function(xml){
				/*var oSerializer = new XMLSerializer();  
				var sXML = oSerializer.serializeToString(xml);
				alert(sXML);*/
				try
				{
					$(xml).find("device").each(function(index, deviceObj) 
					{
						var deviceLi=userDetails.changeDeviceDetailsForControlDeviceFromMap(deviceObj);
						$("#DeviceControl").html(deviceLi).dialog({modal: true}).dialog('open');
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
	
	
	/* vibhu deprecated
	$(".updatedevicearm").live('click',function(){
		$(this).html('<img src="../resources/images/loading.gif" class="armclassControl"/>');
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
					 var deviceLi=userDetails.changeDeviceDetailsForControlDeviceFromMap(deviceObj);
					$("#DeviceControl").html(deviceLi).dialog({modal: true}).dialog('open');
				});
			
			}
		});
		return false;
	}); */
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
	
	$(".changecamera").live('click', function(){
		$("#DeviceControl").dialog("destroy");
		$('#cameraview').dialog('destroy');
		$("#CameraView").dialog('open');
		
		
			$(".cameragechangelistener").attr('deviceid', $(this).attr('deviceid'));
			$(".cameragechangelistener").attr('gateWayId', $(this).attr('gateWayId'));
			$(".cameragechangelistener").attr('pantiltControl', $(this).attr('pantiltControl'));
			$(".cameragechangelistener").attr('controlNightVision', $(this).attr('controlNightVision'));
			
			var deviceName = $(this).attr('devicename');
			$("#cameramoviediv").html("The Selected Camera : " + deviceName);
			$(".livestreamlink").click();
			return false;
		});



		
		// What should happen we we click on the play button of Player.
		$(".livestreamlink").die('click');
		$(".livestreamlink").live('click', function(){
			//$("#cardSlots").data("Stream",$(this).attr('deviceid')).data("DeviceID",$(this).attr('deviceid'));
			var url = $(this).attr('href') + "?__=" + new Date();
			var deviceId = $(this).attr('deviceid');
			var gateWayId = $(this).attr('gateWayId');
			var pantiltControl = $(this).attr('pantiltControl');
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
					//alert(mrl); 
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
					if(pantiltControl == '1'){
						$(".cameraadvancedfeatures").show();
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
			$("#cardSlots").data("Stream",0);
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

		
	/*	var deviceId = $(this).attr('deviceid');
		var gateWayId = $(this).attr('gateWayId');
		var params = {"device.generatedDeviceId":deviceId,"device.gateWay.macId":gateWayId};
		
		$.ajax({
			url: "getCameraView.action",
			data: params,
			success: function(data){
				$("#CameraView").html(data);
				$("#CameraView").dialog({
					draggable: false,
					resizable: false,
					width: 550,
					minHeight: 360,
					autoOpen: false,
					//show:"slide",
					modal: true,
					//hide:"explode",	
				    open: function(event, ui) 
				    {
				    	 $(".ui-dialog-titlebar").css({background: "#5E9FA8",margin: "14px 0px -16px 461px" ,width: "2px"});
				    	 $(".ui-dialog-titlebar-close").css({margin: "-11px 2px 0px 0px"});
				    	 $(this).show("slide",{direction: "down"});  	
				    },
				    close: function(event, ui) 
				    { 
				    	
				    }
					});
					/*if($(".changecamera").hasClass("streamingstarted")){
					var Streamvalue = $("#cardSlots").data("Stream");
					var deviceid=$(this).attr('deviceid');
						if(Streamvalue != deviceid)
						{
							alert("Already streaming for camera "+Streamvalue+". Please stop the stream and try again.");
						}
						return false;
					}
					$("#CameraView").dialog('open');
					/*$(".cameragechangelistener").attr('deviceid', $(this).attr('deviceid'));
					$(".cameragechangelistener").attr('gateWayId', $(this).attr('gateWayId'));
					$(".cameragechangelistener").attr('motiondetection', $(this).attr('motiondetection'));
					$(".cameragechangelistener").attr('controlNightVision', $(this).attr('controlNightVision'));*/
					
					/*var deviceName = $(this).attr('devicename');
					$("#cameramoviediv").html("The Selected Camera : " + deviceName);
					// Changing the now selected camera.
					$(".changecamera").removeClass('nowselectedcamera');
					$(this).addClass('nowselectedcamera');
					
					
					//$(".livestreamlink").click();
			}
		});
		// Cancelling the click, if the player is playing the same device.
		
		return false;
	});*/



	
	// What should happen we we click on the play button of Player.
/*	$(".livestreamlink").die('click');
	$(".livestreamlink").live('click', function(){
		
		//$("#cardSlots").data("Stream",$(this).attr('deviceid')).data("DeviceID",$(this).attr('deviceid'));
		var clickedLink = $(this);
		var url = $(this).attr('href') + "?__=" + new Date();
		var deviceId = $(this).attr('deviceid');
		var gateWayId = $(this).attr('gateWayId');
		var motiondetection = $(this).attr('motiondetection');
		var controlNightVision = $(this).attr('controlNightVision');
		/*if(controlNightVision == 1){
			$(".nightVisionClass").find('img').attr('src', '/imonitor/resources/images/cameralightbuttonglow.png');
		}else{
			$(".nightVisionClass").find('img').attr('src', '/imonitor/resources/images/cameralightbutton.png');
		}
			
		var params = {"device.generatedDeviceId":deviceId,"device.gateWay.macId":gateWayId};
		//$(".nowselectedcamera").html('<img src="../resources/images/loading.gif" />');
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
	// Stop the streaming.
	$(".stopLivestreamlink").live('click', function(){
		var vlc = document.getElementById("vlcPlayer");
		try{
			vlc.playlist.stop();
			vlc.playlist.clear();
		}catch(err){
		}
		$("#cardSlots").data("Stream",0);
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
	$(".cameraadvancedfeatures").hide();*/
	
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
	
	
	$("#cardSlots").on("click","a", function(event){
		$item=$(this).closest('div');
        var id = $item.attr('Id');
    	var deviceid = $item.attr('deviceid');
    	var mapid = $item.attr('mapid');
    	$target = $(event.target);
    	event.stopPropagation();
    	$("#DeviceControl").dialog( "destroy" );
		 $("#message").dialog( "destroy" );
		 $("#message").html('Do Wants Reposition Device?');			
         $("#message").dialog({
				buttons: {
					Ok: function(){
						var params = {"device.generatedDeviceId":deviceid,"device.id":id,"device.locationMap.id":mapid};
						$.ajax({
							url: "resetPositionOfDevice.action",
							data: params,
							success: function()
							{
								
								var pos = $("#DeviceDiv").offset();
						        var top = pos.top + "px";
						        var left=pos.left+"px";
						       
						        ///$('#message').html("U Can Drag To New Position!!!").dialog("open");
						      
								/* $item.height(75);
								 $item.width(65);*/
								// $item.css({ width: "65px" ,height: "70px",position:"static" ,margin: "7px 0px 0px 5px",});
								
								if(!($item.hasClass('ui-draggable')))
								{
								 /*$item.css({
									 	left:left,
						             	top:top,
									 	"position":"static",
							            "width": "55px",
							            "height": "75px",
							            "background": "#666",
							            "margin": "7px 0px 0px 5px",
							            //"overflow":"scroll"
								 });
									$item.css('border-radius' ,'6px');
									$item.find("img").css( { width: "55px" ,height: "40px" });
									$item.find("p").css("font-size" ,"7px");
									$item.find('img:eq(1)').css('margin', '-40px 0px 0px 0px');
									$item.find('a').css('margin', '0px 0px 0px 15px');
									
								
									$item.detach().appendTo('#DeviceDiv').draggable( {
								    	containment: '#cardSlots',
								        stack: '#DeviceDiv div',
								        handle: "detailsectionMap",
								        helper: "original",
								        start: function(){
								        	
								        },
								        drag: function(event, ui){ 
								        	$(this).animate({ width: "27px" ,height: "40px" });
								        	$(this).find("img").animate({  width: "27px" ,height: "20px" });
								        	$(this).find('img:eq(1)').css({ margin :'-20px 0px 0px 0px'});
								        	$(this).find("p").animate("font-size" ,"3px");
								        	//$(this).find( "a.ui-icon-trash" ).remove();
								        	
							    		},
								        stop: function(event, ui) 
								        { 
								        	$DragObj= $(this);
								        	var id = $(this).attr('Id');
								        	var deviceid = $(this).attr('deviceid');
								        	//alert("GEnerated:"+deviceid+"id:"+id);
								        	var pos = $(this).offset();
								            var eWidth =$(this).outerWidth();
								            var mWidth = $(this).outerWidth();
								            var left = (pos.left + eWidth - mWidth);
								            var top = pos.top;
								            //alert("eWidth:"+eWidth+"M:"+mWidth+"L:"+left+"T:"+top);
								            $("#message").html('Are U Ready To Rock And Roll!!!! in JSS  @nd time');			
								            $("#message").dialog({
												stackfix: true,
												modal: true,
												draggable: false,
												resizable: false,
												//position: [top,left],
												buttons: {
													Ok: function(){
														$(this).dialog("destroy");
														$DragObj.draggable('option','disabled',true);
														var params = {"device.locationMap.top":top,"device.locationMap.leftMap":left, "device.generatedDeviceId":deviceid,"device.id":id};
														$.ajax({
															url: "updatePositionOfDevice.action",
															data: params,
															success: function(){
															}
														});
													},
													Cancel: function() {
														$(this).dialog( "destroy" );
													}

												}
											});
								          						            
								        }
								    });*/
								    $item.data("reposition","card");
								    RepositionEvent="Reposition";
									$item.draggable('option','disabled',false);
								}
								 else
								{
									RepositionEvent="Reposition";
									$item.data("reposition","device");
									$item.draggable('option','disabled',false);
								 	/*$item.animate({ width: "55px" ,height: "75px" });
						        	$item.find("img").animate({  width: "27px" ,height: "20px" });
						        	$item.find('img:eq(1)').css({ margin :'-20px 0px 0px 0px'});
						        	$item.find("p").animate("font-size" ,"3px");
						        	$item.detach().appendTo('#DeviceDiv');*/
								}
								
							
							}
							
						});
						$(this).dialog("close");
					},
					Cancel: function() {
						$(this).dialog( "close" );
					}

				},
				draggable:false
         	});
         
			});
         
	$("#CameraView").dialog({
		draggable: true,
		resizable: false,
		width: 475,
		minHeight: 360,
		autoOpen: false,
		show:"slide",
		modal: false,
		 open: function(event, ui) 
		    {
			 $(this).parent().children('.ui-dialog-titlebar').css({background: "#5E9FA8",margin: "0px 0px 0px 0px" ,width: "448px"});
			 $(this).parent().children().children('.ui-dialog-titlebar-close').css({margin: "-10px 5px 0px -21px"});
			/* $(this).parents(".ui-dialog").find(".ui-dialog-titlebar").css({background: "#5E9FA8",margin: "0px 0px 0px 0px" ,width: "448px"});
			 $(this).parents(".ui-dialog").find(".ui-dialog-titlebar-close").css({margin: "-10px 5px 0px -21px"});
		    	/*$(".ui-dialog-titlebar").css({background: "#5E9FA8",margin: "0px 0px 0px 0px" ,width: "448px"});
		    	$(".ui-dialog-titlebar-close").css({margin: "-10px 5px 0px -21px"});*/
		    	
		    },
	   
		});
	});
	
	$("#cardSlots").on("mouseenter", "div", function(event){
		  /* $(this).html("");
		   var Deviceid=$(this).attr('deviceid');
		   var deviceLi=GetDeviceUsing(Deviceid);
		   $(this).html(deviceLi);
		   /*$(this).animate({ width: "350px" ,height: "70px" });
	       $(this).find("img").animate({  width: "70px" ,height: "70px" });
	       $(this).find('img:eq(1)').css({ margin :'0px 0px 0px -70px'});
	       $(this).find("p").animate("font-size" ,"25px");*/

	});
	$("#cardSlots").on("mouseleave", "div", function(event){
		/*$(this).css({background: "#BCD598"});
		$(this).animate({ width: "27px" ,height: "40px" });
	       $(this).find("img").animate({  width: "27px" ,height: "20px" });
	       $(this).find('img:eq(1)').css({ margin :'-20px 0px 0px 0px'});
	       $(this).find("p").animate("font-size" ,"3px");*/

	});

	/*$("#cardSlots").on({ 
		mouseenter: function()
		{
			$(this).css({background: "#63B4C4"});

		},
  		mouseleave: function()
		{
			$(this).css({background: "#BCD598"});
		}
	});*/


	function getattr1(location,image,anchor)
	{
		
		$(".firstLocation").attr("class","");
		anchor.attr("class","firstLocation");
		modal:true;
		
		
		if($('#message').dialog( "isOpen" ))
		{
			return false;
		}
		if(anchor.attr("id")!= lastSelectedLocationId)
		{
			$("#DeviceControl").dialog("destroy");
			lastSelectedLocationId=anchor.attr("id");
			DragEventOnCardDiv="";
			DragEventOnDeviceDiv="";
			RepositionEvent="";
			modal:true;
		}
	
		else
		{
				$('#cardSlots div').each(function() {
					   if($(this).hasClass('ui-draggable-dragging'))
						{
						   DragEventOnCardDiv="Card";
						   $(this).data("Drag","card");
						}
					       
				});
				$('#DeviceDiv div').each(function() {
					   if($(this).hasClass('ui-draggable-dragging'))
						{
						   $(this).data("Drag","device");
						   DragEventOnDeviceDiv="DEVICE";
						}
					       
				});
		}
		if((DragEventOnCardDiv=="")&&(DragEventOnDeviceDiv=="")&&(RepositionEvent==""))
		{
			lastSelectedlocation=location;
			userDetails.changeHtml("MAP",location,image);		
		}
		
	}
	
	function GetDeviceUsing(deviceid)
	{
		
		var deviceLi=userDetails.generateHtmlOfDevicePerLocationForInsertedDevice(deviceid,lastSelectedlocation);
		return deviceLi;
	}
	 
	
	function toggleSelectedTab(anchor)
	{
		
		$(".locationselectedtab").attr("class","locationtab");
		anchor.attr("class","locationselectedtab");
	}
	
	function ClearEvent(event)
	{
		if(event=="card")
		{
			DragEventOnCardDiv="";
		}
		if(event=="device")
		{
			DragEventOnDeviceDiv="";
		}
	}
	function ClearReposition()
	{
		RepositionEvent="";
	}
	$(init);

	function init() {
	  $('#DeviceDiv').html( '' );
	  $('#cardSlots').html( '' );

	  // Create the pile of shuffled cards
	/*  var numbers = [ 1, 2, 3, 4, 5 ];
	  numbers.sort( function() { return Math.random() - .5 } );

	  for ( var i=0; i<5; i++ ) {
	    $('<div>' + numbers[i] + '</div>').appendTo( '#DeviceDiv' ).draggable( {
	    	containment: '#cardSlots',
	        stack: '#DeviceDiv div',
	        cursor: 'move',
	    });
	  }

	 
	/*  for ( var i=1; i<=5; i++ ) {
	    $('<div>' + words[i-1] + '</div>').appendTo( '#cardSlots' ).droppable( {
	      accept: '#cardPile div',
	      hoverClass: 'hovered',
	      //drop: handleCardDrop
	    } );
	  }*/
	  
	 
	  
	  $('#cardSlots').droppable({
		/*  activeClass: "ui-state-default",
	      hoverClass: "ui-state-hover",*/
		 // accept: '#DeviceDiv div',
		  drop: function( event, ui ) {
			 	//alert("HELLLOOOOO");
			 /* var dropElem = ui.draggable.html();

              $clone = $(dropElem).clone(); // clone it and hold onto the jquery object
             	alert("HJHJJJJJ");
              clone.css("position", "absolute");
  			  clone.css("top", ui.absolutePosition.top);
              clone.css("left", ui.absolutePosition.left);
              alert("HJHJ");
      		//  clone.draggable({ containment: '#cardSlots' ,cursor: 'crosshair'});
              $(this).append(clone);*/
              var pos = ui.draggable.offset(), dPos = $(this).offset();
             // alert ("Top: " + (pos.top - dPos.top) + ", Left: " + (pos.left - dPos.left));
			 // alert("TOP Element CARRR:"+dPos.top+"Left Element CARRR:"+dPos.left);
              drag(ui.draggable,(pos.top - dPos.top),(pos.left - dPos.left));
				
			},
			activate: function(event, ui){
				//compress(ui.draggable);
			},
			over:function(event, ui){
				//drag( ui.draggable );
				}
	  });

	}
	


	function drag($item,top,left){
		/*var pos = $item.offset();
        var eWidth = $item.outerWidth();
        var mWidth = $item.outerWidth();
        var left = (pos.left + eWidth - mWidth) + "px";
        var top = pos.top + "px";
        var status = $item.attr('status');*/
       //alert(status);
       /* if(status =="true")
		{
			deviceLi.css({background: "#C9D6D6"});
		}
		else
		{
			deviceLi.css({background: "#5BC05F"});
		}*/
		
		 if($item.children('a').length==1)
		 {
			 top+=25;      
		 }
		
		$item.css({
             position:'absolute',
            // zIndex:1,
             left:left,
             top: top,
             width: "36px" ,
           	 height: "36px" ,
             background: "#F0FA03",
             //border: "3px solid #333",
             padding: "1px 1px 1px 1px"
     	});
		
		
		$item.css('border-radius' ,'7px');
        $item.find("img:eq(0)").css( { position: "relative",width: "30px" ,height: "30px" ,left: "3px",top: "3px"});
		//$item.find("p").css("font-size" ,"3px");
		
		//$item.find('img:eq(1)').css({ position: "relative",width: "30px" ,height: "30px" ,left: "3px",top: "3px",margin: '-30px 0px 0px 0px'});
		//$item.find('img:eq(2)').css( {position: "relative",margin: '-17px -40px 0px -1px',overflow: "visible" ,width: "15px", height: "15px", left: "3px", top: "3px"});
		
	
		//$item.find('a').css( {position: "relative",margin: '-17px -40px 0px -1px',overflow: "visible"});
		
		//$item.removeClass("widget color-white ui-draggable ui-draggable-dragging");
        //show the menu directly over the placeholder
       
		//$clone = $item.clone(true);
         
		 if($item.children('a').length==0)
		 {
			 top+=25;      
		 }
		$item.detach().appendTo('#cardSlots');
		var id = $item.attr('Id');
    	var deviceid = $item.attr('deviceid');
		var params = {"device.locationMap.top":top,"device.locationMap.leftMap":left,"device.generatedDeviceId":deviceid,"device.id":id};
		$.ajax({
			url: "updatePositionOfDevice.action",
			data: params,
			success: function(){
			}
		});
		//$item.remove();
		
		/*$item.appendTo( "#cardSlots" ).fadeIn(function() {
					$item
						.animate({ width: "48px" })
						.find( "img" )
							.animate({ height: "36px" });
				});*/
		//$item.appendTo( '#cardSlots');

		
	//$item.remove();
	}
	
	function compress($item){
		/*$item.animate({ width: "27px" ,height: "40px" });
		$item.find("img").animate({  width: "27px" ,height: "20px" });
		$item.find('img:eq(1)').css({ margin :'-20px 0px 0px 0px'});
		$item.find("p").animate("font-size" ,"3px");*/
    	
	}
	function getDistance(obj1, obj2){
	    var obj1 = document.getElementById(obj1);
	    var obj2 = document.getElementById(obj2);
	    var pos1 = getRelativePos(obj1);
	    var pos2 = getRelativePos(obj2);
	    var dx = pos1.offsetLeft - pos2.offsetLeft;
	    var dy = pos1.offsetTop - pos2.offsetTop;
	    return {x:dx, y:dy};
	}
	
	function getRelativePos(obj){
	var pos = {offsetLeft:0,offsetTop:0};
	while(obj!=null){
	    pos.offsetLeft += obj.offsetLeft;
	    pos.offsetTop += obj.offsetTop;
	    obj = isIE ? obj.parentElement : obj.offsetParent;
	}
	return pos;
	}
	//
	var obj = getDistance("#DeviceDiv","#cardSlots");
	//alert(obj.x+" | "+obj.y);
	
	function checkdivision(){
		//alert($("#cardSlots>.ui-draggable>div").size());
	}
	
	function updatedevicelink(data,data1,data2,data3){
		var url = "Curtainopenclose.action";
		var deviceId = data2;
		var gateWayId = data1;
		var commandParam = data;
		var make=data3;
		if(make==0)
		{
		alert("Curtain controller is not configured. Refer user manual for assistance.");
		return false;
		}
		
		var params = {"device.generatedDeviceId":deviceId, "device.gateWay.macId":gateWayId, "device.commandParam":commandParam};
		$.ajax({
			url: url,
			dataType:'xml',
			data: params,
			success: function(xml){
				$(xml).find("device").each(function(index, deviceObj) {
					 var deviceLi=userDetails.changeDeviceDetailsForControlDeviceFromMap(deviceObj);
					$("#DeviceControl").html(deviceLi).dialog({modal: true}).dialog('open');
				});
			}
		});
		return false;
	}

	function senddimmervalue(data,data1,data2,data3){
		var url = "dimmerChange.action";
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
					  var deviceLi=userDetails.changeDeviceDetailsForControlDeviceFromMap(deviceObj);
					$("#DeviceControl").html(deviceLi).dialog({modal: true}).dialog('open');
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
					 var deviceLi=userDetails.changeDeviceDetailsForControlDeviceFromMap(deviceObj);
					$("#DeviceControl").html(deviceLi).dialog({modal: true}).dialog('open');
				   });
			  	  }
			});
			return false;
		
	}
	//bhavya start for ac thermostat
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
						var deviceLi=userDetails.changeDeviceDetailsForControlDeviceFromMap(deviceObj);
					$("#DeviceControl").html(deviceLi).dialog({modal: true}).dialog('open');
					   });
				  	  }
				});
				return false;
			
		}
		
		
	$(".acupdatedevicelink").live('click', function(){
			var commandParam = $(this).attr('commandParam');
			$(this).html('<img src="../resources/images/loading.gif" class="rightsideimageaccontrol"/>');
			  if(commandParam==10)
			  {
			    $(this).html('<img src="../resources/images/loading.gif" class="AutoModebuttoncontrol"/>');
			  }
            else  if(commandParam==2)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="CoolModebuttoncontrol"/>');
			}
			else  if(commandParam==1)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="HeatModebuttoncontrol"/>');
			}
			else  if(commandParam==8)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="DryModebuttoncontrol"/>');
			}
			else  if(commandParam==6)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="FanModebuttoncontrol"/>');
			}
			else  if(commandParam==5)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="rightsideimagecontrol"/>');
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
								var deviceLi=userDetails.changeDeviceDetailsForControlDeviceFromMap(deviceObj);
								$("#DeviceControl").html(deviceLi).dialog({modal: true}).dialog('open');
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
			$(this).html('<img src="../resources/images/loading.gif" class="AcSwingimagecontrol"/>');
			if(acSwing==1)
			{
			$(this).html('<img src="../resources/images/loading.gif" class="AcSwingimagecontrol"/>');
			}
				//vibhu change to stop refresh if command execution in progress
				var ele = $(this); 
				ele.attr("commandexecutioninprogress","true");
                var url = $(this).attr('href');
                alert("url=="+url);
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
								var deviceLi=userDetails.changeDeviceDetailsForControlDeviceFromMap(deviceObj);
								$("#DeviceControl").html(deviceLi).dialog({modal: true}).dialog('open');
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
	
	//bhavya end for ac thermostat
	
	
	function CheckMainuser() 
	{
		<s:if test="#session.user.role.name == 'mainuser' ">
	 	return 1;
	 	</s:if>
	 	<s:else>
	 	return 0;
	 	</s:else>
	}
	function openHelpPage()
			{
				window.open ('<%=applicationName %>/resources/docs/alarmPageManual.pdf', 'newwindow');
			}
	$('#cardSlots div').click(function(){alert('on click');});
	/*.click(function(event) {
		alert("H*Y**&*&****");
		var $item = $(this),
			$target = $(event.target);

		if ( $target.is( "a.ui-icon-trash" ) ) {
			deleteImage( $item );
		} else if ( $target.is( "a.ui-icon-zoomin" ) ) {
			viewLargerImage( $target );
		} else if ( $target.is( "a.ui-icon-refresh" ) ) {
			recycleImage( $item );
		}

		return false;
	});*/

	</script>
	
	</head>
	<body onload="noBack();" class="bodyclass">
	<div class="maindiv">
			<div class="titlebg">
			<div class="welcomeuser">
					<span style="color:#0C5AA3;"><s:text name="home.msg.0009" /></span> <s:property value="#session.user.userId"/><br><span style="margin-left: -39px;color:#0C5AA3;"><s:text name="home.msg.0017" /></span><s:date name="#session.user.lastLoginTime" format="dd/MM/yy 'at' hh:mm a"/>
				</div>
				<img class="logo" src="<%=applicationName %>/resources/images/logo.png" />											
				<div>
					<ul class="ulmenu topmenu" lihoverclass="hoverstyle">
						<li class="menuitem "><a href="home.action" title="Home" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.main" /></div></a></li>
						 <s:if test="#session.user.role.name == 'mainuser' ">
						 	<li class="menuitem"><a href="setup.action" title="To Configure Device" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.setup" /></div></a></li>
						</s:if>
						<li class="menuitem"><a href="getdeviceforalerts.action" title="View Alerts" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.alerts" /></div></a></li>
						<li class="menuitem selectedtopmenu"><a href="getlistofLocation.action" title="Prespective View" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.advance" /></div></a></li>
						<li class="menuitem"><a href="getlistofLocationforEnergyMg.action" title='<s:text name="Tooltip.Perspective.View" />' class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="EnergyMG" /></div></a></li>
						<li class="menuitem"><a href="../logout.action" title="Sign Out" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.signout" /></div></a></li>					
					</ul>
				</div>
				<div>
					<img class="helpicon" title="Drag and Drop devices to Room" src="<%=applicationName %>/resources/images/help.png" onclick='javascript: openHelpPage()' />
				</div>
			</div>
			<div id="Locations"  class ='LocationsHeader'>
						<table id="listLocations" class="locationtable">
					<!-- <thead>
				 		<th style="text-align:left;">LOCATIONS</th>
					</thead> -->
					<tbody></tbody>
					</table>
			</div>
			<div id="content" style="background-color:#B1C5CA;height:570px;width:1024px;float:left;">
		<!-- 	<div id="deviceSection" class="deviceSection" style="height:160px;margin-top:410px;">
			
						<div class="devicegroup">
							<ul class="column" id="deviceListUl"></ul>
						</div>
					</div> -->
			
			<div id="cardSlots" style="background-color: #F5F6F7;height: 470px;width: 1024px;float:left;margin-top: -5px;position:relative"></div>		
			<div id="DeviceDiv" style="background-color: #4F5858;height: 93px;width: 1024x;float:left;"> </div>
  			
	</div>
	<div id="CameraView">
	<div id="cameraSection" class="cameraSectionControl">
				<div class="cameramovietitle" id="cameramoviediv"></div>
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
				<div class="cameramenuControl">					
					<div class="stopplaybutton">
						<ul class="cameraplaybutton">
							<li><a href="getLiveStream.action" class="cameragechangelistener livestreamlink"><img src="<%=applicationName %>/resources/images/cameraplaybutton.png"/></a>
							</li>
							 <li><a href="#" class="cameragechangelistener stopLivestreamlink"><img src="<%=applicationName %>/resources/images/camerastopbutton.png"/></a>
							</li> 
						</ul>
					</div>
					<div class="cameraadvancedfeatures">
						<div class="cameramovebuttondiv">
							<ul class="cameramovebuttondivul" style="float:left; margin-top:15px;">
								<li><a class="camerapantiltclass cameragechangelistener" href="controlPanTilt.action" controlPantilt='L,5'><img src="<%=applicationName %>/resources/images/cameraleft.png"/></a></li>
							</ul>
							<ul class="cameramovebutton">
								<li style="height:13px;" ><a class="camerapantiltclass cameragechangelistener" href="controlPanTilt.action" controlPantilt='U,5'><img src="<%=applicationName %>/resources/images/cameraup.png"/></a>
								</li>
								<li style="padding-top:1px; height:20px;" ><a class="camerapantiltclass cameragechangelistener" href="controlPanTilt.action" controlPantilt='H'><img src="<%=applicationName %>/resources/images/camerahome.png"/></a>
								</li>
								<li style="height:10px;" ><a class="camerapantiltclass cameragechangelistener" href="controlPanTilt.action" controlPantilt='D,5'><img src="<%=applicationName %>/resources/images/cameradown.png"/></a>
								</li>
							</ul>
							<ul style="float:left; margin-top:15px; ">
								<li><a class="camerapantiltclass cameragechangelistener" href="controlPanTilt.action" controlPantilt='R,5'><img src="<%=applicationName %>/resources/images/cameraright.png"/></a></li>
							</ul>
						</div>
						<div class="cameraclickmove">
							<ul>
								<li style="float:left;padding-left:20px;padding-top:6px;display:none"><a href="captureIamge.action" class="cameragechangelistener imagecaptureclass"><img src="<%=applicationName %>/resources/images/cameracapturebutton.png"/></a></li>
								<li style="padding-right:15px;float:right;padding-top:5px;"><a class='nightVisionClass cameragechangelistener' href='controlNightVision.action' value='0'><img src="<%=applicationName %>/resources/images/cameralightbutton.png"/></a></li>							
								<li style="padding-left:52px;padding-top:6px;display:none"><a href="#"><img src="<%=applicationName %>/resources/images/cameravideo.png"/></a></li>
							</ul>
						</div>					
					</div>				
				</div>
			</div>
	</div>
	<div id="message"></div>
	<div id="DeviceControl" style=" background-color:#A4ABAC; height: 70px; width: 390px; z-index: -1; "></div>
</body>
</html>