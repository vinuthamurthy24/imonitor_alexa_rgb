<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%
String applicationName = request.getContextPath();
%>

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
			
<script>


$(document).ready(function() 
{
	
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
	
	$(".livestreamlink").die('click');
	$(".livestreamlink").live('click', function(){
		var clickedLink = $(this);
		var url = $(this).attr('href') + "?__=" + new Date();
		var deviceId = $(this).attr('deviceid');
		var gateWayId = $(this).attr('gateWayId');
		var modelnumber = $(this).attr('modelnumber');
		var controlNightVision = $(this).attr('controlNightVision');
		if(controlNightVision == 1){
			$(".nightVisionClass").find('img').attr('src', '/imonitor/resources/images/cameralightbuttonglow.png');
		}else{
			$(".nightVisionClass").find('img').attr('src', '/imonitor/resources/images/cameralightbutton.png');
		}
			
		var params = {"device.generatedDeviceId":deviceId,"device.gateWay.macId":gateWayId};
		$.ajax({
			url: url,
			dataType: 'xml',
			data: params,
			success: function(data){
				var mrl = $.trim($($(data).find('mrl').get(0)).text());
				//mrl = "rtsp://122.166.229.151/sample_h264_1mbit.mp4";
				/* alert(mrl); */ 
				//Write the condition to validate the mrl.
				if(mrl.indexOf("rtsp") == 0){
					var vlc = document.getElementById("vlcPlayer");
					var options = new Array(":aspect-ratio=4:3", "--rtsp-tcp");
					vlc.playlist.clear();
					//var id = vlc.playlist.add($.trim("rtsp://192.168.2.26/samp2.mp4"), "fancy name", options);
					var id = vlc.playlist.add(mrl, "Imonitor", options);
					vlc.playlist.playItem(id);
					// hide/show play/stop buttons.
					$(".stopLivestreamlink").show();
					$(".livestreamlink").hide();
				} else{
					// Get the effect of streaming stopped.
					$(".stopLivestreamlink").click();
				}
				if((modelnumber == "RC8061") ||  (modelnumber == "H264PT")){
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
	
	
	var handleSuccess = function(data){
			var deviceIndex = $(data).find("index").text();
			var deviceId = $(data).find("id").text();
			var name = $(data).find("name").text();
			var deviceType = $(data).find("type").text();
			var modelnumber=$(data).find("modelnumber").text();
			var controlNightVision = $(data).find("controlnightvision").text();
			var macid=$(data).find("macid").text();
			
			if((modelnumber == "RC8061") ||  (modelnumber == "H264PT") )
			{
				$(".livestreamlink").show();
				$(".cameraadvancedfeatures").show();
			}
			else
			{
				$(".livestreamlink").show();
			}
			
			$(".cameragechangelistener").attr('deviceid',deviceId);
			$(".cameragechangelistener").attr('gateWayId', macid);
			$(".cameragechangelistener").attr('modelnumber',modelnumber );
			$(".cameragechangelistener").attr('controlNightVision',controlNightVision);
			
		};
		
	var showUserDetails = function(){
		var DeviceId='<s:property value="device.generatedDeviceId"/>';
		var Macid='<s:property value="device.gateWay.macId"/>';
		var params = {"device.generatedDeviceId":DeviceId,"device.gateWay.macId":Macid};
		var lUrl = "GetStoredDevice.action";
		$.ajax({
			url: lUrl,
			data: params,
			dataType: 'xml',
			success: handleSuccess
		});
		
	};
	showUserDetails();
});
		
</script>