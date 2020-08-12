/*!
 * Copyright � 2012 iMonitor Solutions India Private Limited
 */
var UserDetails = function(){
	var gateWays = {};
	//vibhu start
	var scenarios = new Array();
	var locationicon = {};
	this.addLocation=function(location)
	{
		locationicon[location]={};
		locationicon[location]["id"]={};
	};
	
	//vibhu end
	var gateWayCount = 0;
	var lastSelectedWise = "";
	var lastSelectedWiseForDevice = "";
	var first=0;
	var lastSelectedClassification = "LOCATION_WISE";
	 $("#gatewaySectionDetailes").addClass('gatewaysection');
	//$("#gatewaySection").append(gateWaySection);
	this.addGateWay = function(gateWayId){
	// Check if gateway exists, if required.
	gateWays[gateWayId] = {};
	// Initializing the device section.
	gateWays[gateWayId]["devices"] = {};
	gateWays[gateWayId]["devices"]["locations"] = {};
	gateWays[gateWayId]["devices"]["devicetypes"] = {};
	
};

this.setGateWayCount = function(gateWayCount){
	this.gateWayCount = gateWayCount;
};

this.showGateWayStaus = function(gateWayId){
	for(var key in gateWays){
		gateWays[key]['statussection'].hide();
	}
	gateWays[gateWayId]['statussection'].show();
};

this.generateGateWayHtml = function(){
	$("#gatewaySection").html("");
	$('#reboot').html("");
	$('#devicestatus').html("");
	$('#allOn').html("");
	$('#allOff').html("");
	if(this.gateWayCount < 2){
		for(var gateWayId in gateWays){
			//sumit added style to href element
			//bhavya start
			
			var status = gateWays[gateWayId]['status'];
			
			var gateWayVersion = gateWays[gateWayId]['gateWayVersion'];
			
			var firmwareversion = gateWays[gateWayId]['Latestversion'];
			
			var FirmwareId = gateWays[gateWayId]['FirmwareId'];
			
			
			
			if(CheckMainuser()==1 && $.trim(status) == "Online")
			{
			if(firmwareversion)
			{
		var Userinitiatedfirmaware = "<a href='updateLatestFirmWare.action' class='FirmwareUpgrade' title='Click to Update The gateway With Latest Version' style='text-decoration: blink' targetid='reboot' firmwareversion='"+firmwareversion+"' FirmwareId='"+FirmwareId+"' gateWayVersion='"+gateWayVersion+"' gatewayid="
			+gateWayId+">"
			+formatMessage("msg.ui.Userinitiatedfirmaware")
			+"</a>";
			}
			}
			//bhavya end
			var reboot = "<a href='toReboot.action' class='remoteReboot' style='text-decoration: none' targetid='reboot' gatewayid="
				+gateWayId+" ><button type='button' class='bt bbtn' title='click to reboot Master Controller'>"
				+formatMessage("msg.ui.reboot")
				+"</button></a>";
			var devicestatus = "<a href='deviceStatusUpdate.action' class='devicestatus' style='text-decoration: none' targetid='reboot' gatewayid="
				+gateWayId+" ><button type='button' class='bt bbtn' title='click to refresh device status'>"+formatMessage("msg.ui.syncstatus")+"</button></a>";
			$('#reboot').append(reboot);
			$('#devicestatus').append(devicestatus);
			
			var onoffstatus=$.trim(gateWays[gateWayId]['AllOnOffStatus']);
			this.generateAllOnOFFButton(onoffstatus,gateWayId);
			
			var AlertExtends = "<span id='alertheadername' class='alertheadername'>"+formatMessage("msg.ui.alarms")+"</span>";
			$('.alerthedersection').html(AlertExtends);
			
			$("#reboot").append(Userinitiatedfirmaware);
			var statusSection = $("<span class='gatewayimgsection'></span>");
			gateWays[gateWayId]['statussection'] = statusSection;
			$("#gatewaySection").append(statusSection);
			var html = "&nbsp;&nbsp;&nbsp;&nbsp;";
			var status = gateWays[gateWayId]['status'];
			
						
			if($.trim(status) == "Online" || $.trim(status) == "Device Discovery Mode" || $.trim(status) == "GateWay Recovery"){
				html += "<img title='Online' src='../resources/images/getwayonline.png'/>";
			} else{
				html += "<img title='Offline' src='../resources/images/getwayoffline.png'/>";
			}
			statusSection.html(html);


			$("#gatewaySection").append($("<span class='gatewayidsection'></span>").html("&nbsp;&nbsp;&nbsp;&nbsp;"+formatMessage("msg.ui.mmc")));
			// ****************************************** sumit start *************************************************
			var curMode = gateWays[gateWayId]['currentMode'];
			this.setCurrentMode(curMode);
			// ****************************************** sumit end ***************************************************
		}
	} else{
		 //Added by apoorva
		var selectGateWay = $("<select class='gatewaychange' style='float:left;margin-left: 70px;margin-top: 5px;'></select>");
		var gatewayidReboot="";
		$("#gatewaySection").append(selectGateWay);
		for(var gateWayId in gateWays){
			
			//3gp 
			//var gateId=gateWays[gateWayId]['gateId'];
			//alert("gateId : "+gateId);
			//3gp  end
			var gatewayName = gateWays[gateWayId]['gatewayName'];
			gatewayidReboot = gateWayId;
			selectGateWay.append("<option value='"+gateWayId+"'>" + gatewayName + " </option>");
			
			//Sorting gateways based on alphabet
			var selectOptions = $(".gatewaychange option");
			selectOptions.sort(function(a, b) {
			    if (a.text > b.text) {
			        return 1;
			    }
			    else if (a.text < b.text) {
			        return -1;
			    }
			    else {
			        return 0;
			    }
			});
			$(".gatewaychange").empty().append(selectOptions);
			
			/*var selectedOption = $('.gatewaychange').val();
			
			$('.gatewaychange').val(selectedOption);*/
			//Sorting based on alphabet end
			
			
			
		}
		
		if(selectedGateWay != ""){
			$(".gatewaychange").val(selectedGateWay); 
		}
		var selected = $(".gatewaychange").val() ;
		
		//gateway online image start
		var statusSection = $("<span></span>");
		gateWays[selected]['statussection'] = statusSection;
		$("#gatewaySection").append(statusSection);
		var html = "&nbsp;&nbsp;&nbsp;&nbsp;";
		var status = gateWays[selected]['status'];
		
		if($.trim(status) == "Online" || $.trim(status) == "Device Discovery Mode" || $.trim(status) == "GateWay Recovery"){
			html += "<img src='../resources/images/getwayonline.png' style='float:left;margin-left: 5px;margin-top: 5px;' />";
		} else{
			html += "<img src='../resources/images/getwayoffline.png' style='float:left;margin-left: 5px;margin-top: 5px;' />";
		}
		
	
		/*if(this.gateWayCount > 1){
			
			statusSection.hide();
		}*/
		
		
		statusSection.html(html);
		//gateway online image end
		
		var reboot = "<a href='toReboot.action' class='remoteReboot' style='text-decoration: none' targetid='reboot' gatewayid="
			+selected+" ><button type='button' class='bt bbtn'>Reboot</button></a>";

		$('#reboot').append(reboot);
		/*if(this.gateWayCount > 1){
			$(".gatewaychange").change();
		}*/
		
	
		var devicestatus = "<a href='deviceStatusUpdate.action' class='devicestatus' style='text-decoration: none' targetid='reboot' gatewayid="
			+gateWayId+" ><button type='button' class='bt bbtn' title='click to refresh device status'>"+formatMessage("msg.ui.syncstatus")+"</button></a>";
		$('#devicestatus').append(devicestatus);
		/*if(this.gateWayCount > 1){
			$(".gatewaychange").change();
		}*/
		
		var onoffstatus=$.trim(gateWays[selected]['AllOnOffStatus']);
		this.generateAllOnOFFButton(onoffstatus,selected);
		
		var curMode = gateWays[gateWayId]['currentMode'];
		this.setCurrentMode(curMode);
	}

};


this.generateAllOnOFFButton = function(ststus,gateWayId)
{
	/*Aditya Added for All On OFF Button Impelemention*/
	
	$('#allOn').html("");
	$('#allOff').html("");
	
	var isOff = $.trim(ststus) == 0;
	var AllOFF="";
	var AllON="";
	
	
		AllOFF = "<a href='toalloff.action' class='allOnOff' style='text-decoration: none' targetid='reboot' gatewayid="
			+gateWayId+" OnOffStstus=0><button type='button' class='bt bbtn' title='Click to Switch off all devices'>"
			+formatMessage("home.ui.alloff")
			+"</button></a>";
	
		AllON = "<a href='toallon.action' class='allOnOff' style='text-decoration: none' targetid='reboot' gatewayid="
			+gateWayId+"  OnOffStstus=1 ><button type='button' class='bt bbtn' title='Click to Switch on all devices'>"
			+formatMessage("home.ui.allon")+"</button></a>";

	
	$('#allOn').append(AllON);
	$('#allOff').append(AllOFF);
};




this.setCurrentMode = function(curMode){
	var mode = formatMessage("general.home");//"HOME";
	if(curMode == '0')
	{
		
	}
	else if(curMode == '1')
	{
		mode = formatMessage("general.stay");//"STAY";
	}
	else
	{
		mode = formatMessage("general.away");//"AWAY";
	}
	var modeSelectH = document.getElementById('HOME');
	if(modeSelectH != null)
	{      	
	      	$('#curMode').html(formatMessage("msg.ui.currentmode")+"&nbsp;&nbsp;" +mode);
	}
};
//bhavya start
this.generateGateWayDeviceDiscoveryHtml = function(){
	for(var gateWayId in gateWays){
		if(($(".discoverysection").length)>=1)
		{
			$('.discoverysection').remove();
		}
		
		var href = "javascript:showResultAlert('"+formatMessage("msg.ui.setup.0001")+"')";
		var href2 = "javascript:showResultAlert('"+formatMessage("msg.ui.setup.0002")+"')";
		var href3 = "javascript:showResultAlert('"+formatMessage("msg.ui.setup.0001")+"')";
        var href4 = "abortMode.action";

		var cssClass = "";
		var cssClassup = "";
		var cssClassRemoveAll = "";
                var cssClassabort = "abortMode";

		var title1="Master controller is in discovery or is offline.";
		var title3="Master controller is in removal mode or is offline.";
		var titleRemoveAll="Master controller is in removal mode or is offline.";
		var title2='Click to abort devices';
		var discoveryImage = "<div class='discoveryimgDisabled'><span>"+formatMessage("msg.ui.setup.0011")+"</span></div>";
		var discoveryImage3 = "<div class='discoveryimgDisabled'><span>"+formatMessage("msg.ui.setup.0012")+"</span></div>";
		var discoveryImage1 = "<div class='discoveryimg'><span>"+formatMessage("msg.ui.setup.0013")+"</span></div>";
		var removeAllImage = "<img  class='discoveryimgDisabled' src='../resources/images/deviceReset.png'/>";
		
                 var status = gateWays[gateWayId]['status'];

		/*if($.trim(gateWays[gateWayId]['status']) == "Device Discovery Mode"){
			$("#gatewaySection").append($("<span class='gatewaystatussection' style='float:left;margin-left:-420px; color:#FF0000; font-size:14px;' ></span>").html(formatMessage("msg.ui.setup.0008")));//Gateway text is changed with MASTER CONTROLLER
			title2 = "Abort Discovery mode";
			title3 = "Master controller is in device discovery mode.";
			title1 = "Master controller is in device discovery mode.";
			href = "javascript:hideResultAlert('')";		//parishod removed the current status of gateway shown on popup window 
			href2 = "javascript:hideResultAlert('')";		//parishod removed the current status of gateway shown on popup window
			href3 = "javascript:hideResultAlert('')";		//parishod removed the current status of gateway shown on popup window
		}
		if($.trim(gateWays[gateWayId]['status']) == "GateWay Recovery"){
			$("#gatewaySection").append($("<span class='gatewaystatussection' style='float:left;margin-left:-420px; color:#FF0000; font-size:14px;' ></span>").html(formatMessage("msg.ui.setup.0007")));//Gateway text is changed with MASTER CONTROLLER
			title2 = "Abort Removal mode";
			title3 = "Master controller is in device removal mode.";
			title1 = "Master controller is in device removal mode.";
			href = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
			href2 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
			href3 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
		}
		if($.trim(gateWays[gateWayId]['status']) == "Offline")
			{
			$("#gatewaySection").append($("<span class='gatewaystatussection' style='float:left;margin-left:-420px; color:#FF0000; font-size:14px;' ></span>").html(formatMessage("msg.ui.setup.0009"))); //Gateway text is changed with MASTER CONTROLLER
			href4 = "javascript:hideResultAlert('')";
			cssClassabort = "";
			title2 = "Master controller is offline.";
			title3 = "Master controller is offline.";
			title1 = "Master controller is offline.";
			href = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
			href2 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
			href3 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
			discoveryImage1 = "<div class='discoveryimgDisabled'><span>"+formatMessage("msg.ui.setup.0013")+"</span></div>";
			}*/
		
		var gateId=""; //Added for 3gateways implementation
		gateId=$(".gatewaychange").val();//Added for 3gateways implementation
		//$('.discoverysection').attr('gatewayid',gateId); //Added for 3gateways implementation
		//$('.updategatewaylink').attr('gatewayid',gateId); //Added for 3gateways	implementation
		
		
		
		//****************Changes done by apoorva start for 3gateways implementation***********************
		
		if(selectedGateWay != ""){
			$(".gatewaychange").val(selectedGateWay); 
		}
		
		if (this.gateWayCount > 1) 
		{
			if($.trim(gateWays[gateId]['status']) == "Device Discovery Mode"){
				$("#gatewaySection").append($("<span class='gatewaystatussection' style='float:left;margin-left:-420px; color:#FF0000; font-size:14px;' ></span>").html(formatMessage("msg.ui.setup.0008")));//Gateway text is changed with MASTER CONTROLLER
				title2 = "Abort Discovery mode";
				title3 = "Master controller is in device discovery mode.";
				title1 = "Master controller is in device discovery mode.";
				href = "javascript:hideResultAlert('')";		//parishod removed the current status of gateway shown on popup window 
				href2 = "javascript:hideResultAlert('')";		//parishod removed the current status of gateway shown on popup window
				href3 = "javascript:hideResultAlert('')";		//parishod removed the current status of gateway shown on popup window
			}
			if($.trim(gateWays[gateId]['status']) == "GateWay Recovery"){
				$("#gatewaySection").append($("<span class='gatewaystatussection' style='float:left;margin-left:-420px; color:#FF0000; font-size:14px;' ></span>").html(formatMessage("msg.ui.setup.0007")));//Gateway text is changed with MASTER CONTROLLER
				title2 = "Abort Removal mode";
				title3 = "Master controller is in device removal mode.";
				title1 = "Master controller is in device removal mode.";
				href = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				href2 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				href3 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
			}
			if($.trim(gateWays[gateId]['status']) == "Offline")
				{
				$("#gatewaySection").append($("<span class='gatewaystatussection' style='float:left;margin-left:-420px; color:#FF0000; font-size:14px;' ></span>").html(formatMessage("msg.ui.setup.0009"))); //Gateway text is changed with MASTER CONTROLLER
				href4 = "javascript:hideResultAlert('')";
				cssClassabort = "";
				title2 = "Master controller is offline.";
				title3 = "Master controller is offline.";
				title1 = "Master controller is offline.";
				href = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				href2 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				href3 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				discoveryImage1 = "<div class='discoveryimgDisabled'><span>"+formatMessage("msg.ui.setup.0013")+"</span></div>";
				}
			
			if($.trim(gateWays[gateId]['status']) == "Online"){
				href = "setDeviceDiscoveryMode.action";
				href2 = "removeDeviceup.action";
				href3 = "removeAllDevices.action";
	            href4 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				cssClass = "updategatewaylink";
				cssClassup = "updategatewaylinkup";
				cssClassRemoveAll = "removeall";
	                        cssClassabort = "";
				title1 = 'Click to discover new devices';
				title3 = 'Click to remove devices';
				titleRemoveAll = 'Click to remove ALL devices';
				title2 = "Master controller is not in removal mode or discovery mode.";
				//alert(status);
				discoveryImage = "<div class='discoveryimg'><span>"+formatMessage("msg.ui.setup.0011")+"</span></div>";
				discoveryImage3 = "<div class='discoveryimg'><span>"+formatMessage("msg.ui.setup.0012")+"</span></div>";
				discoveryImage1 = "<div class='discoveryimgDisabled'><span>"+formatMessage("msg.ui.setup.0013")+"</span></div>";
				removeAllImage = "<img  class='discoveryimg' src='../resources/images/deviceReset.png'/>";
			}
		} 
		else 
		{
			if($.trim(gateWays[gateWayId]['status']) == "Device Discovery Mode"){
				$("#gatewaySection").append($("<span class='gatewaystatussection' style='float:left;margin-left:-420px; color:#FF0000; font-size:14px;' ></span>").html(formatMessage("msg.ui.setup.0008")));//Gateway text is changed with MASTER CONTROLLER
				title2 = "Abort Discovery mode";
				title3 = "Master controller is in device discovery mode.";
				title1 = "Master controller is in device discovery mode.";
				href = "javascript:hideResultAlert('')";		//parishod removed the current status of gateway shown on popup window 
				href2 = "javascript:hideResultAlert('')";		//parishod removed the current status of gateway shown on popup window
				href3 = "javascript:hideResultAlert('')";		//parishod removed the current status of gateway shown on popup window
			}
			if($.trim(gateWays[gateWayId]['status']) == "GateWay Recovery"){
				$("#gatewaySection").append($("<span class='gatewaystatussection' style='float:left;margin-left:-420px; color:#FF0000; font-size:14px;' ></span>").html(formatMessage("msg.ui.setup.0007")));//Gateway text is changed with MASTER CONTROLLER
				title2 = "Abort Removal mode";
				title3 = "Master controller is in device removal mode.";
				title1 = "Master controller is in device removal mode.";
				href = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				href2 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				href3 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
			}
			if($.trim(gateWays[gateWayId]['status']) == "Offline")
				{
				$("#gatewaySection").append($("<span class='gatewaystatussection' style='float:left;margin-left:-420px; color:#FF0000; font-size:14px;' ></span>").html(formatMessage("msg.ui.setup.0009"))); //Gateway text is changed with MASTER CONTROLLER
				href4 = "javascript:hideResultAlert('')";
				cssClassabort = "";
				title2 = "Master controller is offline.";
				title3 = "Master controller is offline.";
				title1 = "Master controller is offline.";
				href = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				href2 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				href3 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				discoveryImage1 = "<div class='discoveryimgDisabled'><span>"+formatMessage("msg.ui.setup.0013")+"</span></div>";
				}
			
			
			if($.trim(gateWays[gateWayId]['status']) == "Online"){
				href = "setDeviceDiscoveryMode.action";
				href2 = "removeDeviceup.action";
				href3 = "removeAllDevices.action";
	            href4 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
				cssClass = "updategatewaylink";
				cssClassup = "updategatewaylinkup";
				cssClassRemoveAll = "removeall";
	                        cssClassabort = "";
				title1 = 'Click to discover new devices';
				title3 = 'Click to remove devices';
				titleRemoveAll = 'Click to remove ALL devices';
				title2 = "Master controller is not in removal mode or discovery mode.";
				//alert(status);
				discoveryImage = "<div class='discoveryimg'><span>"+formatMessage("msg.ui.setup.0011")+"</span></div>";
				discoveryImage3 = "<div class='discoveryimg'><span>"+formatMessage("msg.ui.setup.0012")+"</span></div>";
				discoveryImage1 = "<div class='discoveryimgDisabled'><span>"+formatMessage("msg.ui.setup.0013")+"</span></div>";
				removeAllImage = "<img  class='discoveryimg' src='../resources/images/deviceReset.png'/>";
			}
		}
		//***********Changes done by apoorva start for 3gateways implementation end***********************
		
		
		/*if($.trim(gateWays[gateId]['status']) == "Online"){
			href = "setDeviceDiscoveryMode.action";
			href2 = "removeDeviceup.action";
			href3 = "removeAllDevices.action";
            href4 = "javascript:hideResultAlert('')";	//parishod removed the current status of gateway shown on popup window
			cssClass = "updategatewaylink";
			cssClassup = "updategatewaylinkup";
			cssClassRemoveAll = "removeall";
                        cssClassabort = "";
			title1 = 'Click to discover new devices';
			title3 = 'Click to remove devices';
			titleRemoveAll = 'Click to remove ALL devices';
			title2 = "Master controller is not in removal mode or discovery mode.";
			//alert(status);
			discoveryImage = "<div class='discoveryimg'><span>"+formatMessage("msg.ui.setup.0011")+"</span></div>";
			discoveryImage3 = "<div class='discoveryimg'><span>"+formatMessage("msg.ui.setup.0012")+"</span></div>";
			discoveryImage1 = "<div class='discoveryimgDisabled'><span>"+formatMessage("msg.ui.setup.0013")+"</span></div>";
			removeAllImage = "<img  class='discoveryimg' src='../resources/images/deviceReset.png'/>";
		}*/
		
		
		//***********Changes done by apoorva for 3gateways implementation***********************
		var discoverySection="";
		var removalSection="";
		var removalAllSection="";
		var abortmode="";
		if (this.gateWayCount > 1) 
		{
			 discoverySection = $("<span class='discoverysection'></span>")
			.append($("<a></a>")
			.attr('href', href)
			.attr('title', title1)
			.addClass(cssClass)
			.attr('gatewayid', gateId)
			.append(discoveryImage));
			 removalSection = $("<span class='discoverysection'></span>")
			.append($("<a></a>")
			.attr('href', href2)
			.attr('title', title3)									
			.addClass(cssClassup)
			.attr('gatewayid', gateId)
			.append(discoveryImage3));

			 removalAllSection = $("<span class='discoverysection'></span>")
			.append($("<a></a>")
			.attr('href', href3)
			.attr('title', titleRemoveAll)									
			.addClass(cssClassRemoveAll)
			.attr('gatewayid', gateId)
			.append(removeAllImage));

			 abortmode = $("<span class='discoverysection'></span>")
			.append($("<a></a>")
			.attr('href', href4)
			.attr('title', title2)
			.addClass(cssClassabort)
			.attr('gatewayid', gateId)
			.attr('status', status)
			.append(discoveryImage1));
		} 
		else 
		{
			 discoverySection = $("<span class='discoverysection'></span>")
			.append($("<a></a>")
			.attr('href', href)
			.attr('title', title1)
			.addClass(cssClass)
			.attr('gatewayid', gateWayId)
			.append(discoveryImage));
			 removalSection = $("<span class='discoverysection'></span>")
			.append($("<a></a>")
			.attr('href', href2)
			.attr('title', title3)									
			.addClass(cssClassup)
			.attr('gatewayid', gateWayId)
			.append(discoveryImage3));

			 removalAllSection = $("<span class='discoverysection'></span>")
			.append($("<a></a>")
			.attr('href', href3)
			.attr('title', titleRemoveAll)									
			.addClass(cssClassRemoveAll)
			.attr('gatewayid', gateWayId)
			.append(removeAllImage));

			abortmode = $("<span class='discoverysection'></span>")
			.append($("<a></a>")
			.attr('href', href4)
			.attr('title', title2)
			.addClass(cssClassabort)
			.attr('gatewayid', gateWayId)
			.attr('status', status)
			.append(discoveryImage1));
		}
		
		
		
		/*var discoverySection = $("<span class='discoverysection'></span>")
									.append($("<a></a>")
									.attr('href', href)
									.attr('title', title1)
									.addClass(cssClass)
									.attr('gatewayid', gateWayId)
									.append(discoveryImage));
      var removalSection = $("<span class='discoverysection'></span>")
									.append($("<a></a>")
									.attr('href', href2)
									.attr('title', title3)									
									.addClass(cssClassup)
									.attr('gatewayid', gateWayId)
									.append(discoveryImage3));

      var removalAllSection = $("<span class='discoverysection'></span>")
									.append($("<a></a>")
									.attr('href', href3)
									.attr('title', titleRemoveAll)									
									.addClass(cssClassRemoveAll)
									.attr('gatewayid', gateWayId)
									.append(removeAllImage));

     var abortmode = $("<span class='discoverysection'></span>")
									.append($("<a></a>")
									.attr('href', href4)
									.attr('title', title2)
									.addClass(cssClassabort)
									.attr('gatewayid', gateWayId)
									.attr('status', status)
									.append(discoveryImage1));*/
		
		//***********CHanges done by apoorva for 3gateways implementation end***********************
		gateWays[gateWayId]['statussection'] = discoverySection;
//		$("#pageTitle").prepend(removalAllSection);
                $("#pageTitle").prepend(abortmode);
		$("#pageTitle").prepend(removalSection);
		$("#pageTitle").prepend(discoverySection);
		
	}
};
//bhavya end
	
this.setGateWayStatus = function(gateWayId, status, featuresEnabled,Latestversion,FirmwareId,gateWayVersion,gatewayName){
	gateWays[gateWayId]['status'] = status;
	gateWays[gateWayId]['featuresEnabled'] = featuresEnabled;
	gateWays[gateWayId]['Latestversion'] = Latestversion;
	gateWays[gateWayId]['FirmwareId'] = FirmwareId;
	gateWays[gateWayId]['gateWayVersion'] = gateWayVersion;
/*	alert("in.user 551 :"+gatwayId);*/
	gateWays[gateWayId]['gatewayName'] = gatewayName; //3gp
};
// sumit start 
this.setGateWayStatusAndMode = function(gateWayId, status,curMode,Latestversion,FirmwareId,gateWayVersion,AllOnOff,gatewayName){
	//alert("gateWayId 340-- > "+gateWayId);
	gateWays[gateWayId]['status'] = status;
	gateWays[gateWayId]['currentMode'] = curMode;
	gateWays[gateWayId]['Latestversion'] = Latestversion;
	gateWays[gateWayId]['FirmwareId'] = FirmwareId;
	gateWays[gateWayId]['gateWayVersion'] = gateWayVersion;
	gateWays[gateWayId]['AllOnOffStatus'] = AllOnOff;
	gateWays[gateWayId]['gatewayName'] = gatewayName; //3gp
};

this.GetLocation = function(){


	
	var locationTypes = new Array();
	for(var gateWayId in gateWays){
		var locationTypesTemp = gateWays[gateWayId]["devices"]["locations"];
		for(var i in locationTypesTemp){
			locationTypes.push(i);
		}
		locationTypes.sort(function (a, b){return ((a < b) ? -1 : ((a > b) ? 1 : 0));});
	}
	
	return locationTypes;
	


};

this.GetDevicesForLocations = function(SlectedLocation){
       
	var deviceDetails = {};
	var deviceList = new Array();
	for(var gateWayId in gateWays)
	{
		var deviceDetailsTmp = gateWays[gateWayId]["devices"]["locations"];
		$.extend(deviceDetails, deviceDetailsTmp);

	}
	
	for(var key in deviceDetails)
	{
	
		
		if(SlectedLocation == key)
		{
			var devices = deviceDetails[key];
			
			for(var dKey in devices)
			{
				var device = devices[dKey];
				 
				 var deviceType = $.trim(device["deviceType"]);
				var enableDeviceListing = $.trim(device["enableList"]);

	
	if((deviceType != "MODE_HOME") && (deviceType != "MODE_STAY") && (deviceType != "MODE_AWAY") && (enableDeviceListing != "0"))
	{

				
						deviceList.push(device["deviceName"]);
					}
			}
		}	
	}
	return deviceList;
};





// sumit end 
this.generateSelectBoxes = function(Path){
	var pressed=$("#radioLable").attr('aria-pressed');
	if(pressed == 'false')
	{
	
	}
	if(lastSelectedWise=="" || pressed == 'true')
	{
	var locationTypes = new Array();
	for(var gateWayId in gateWays){
		var locationTypesTemp = gateWays[gateWayId]["devices"]["locations"];
		for(var i in locationTypesTemp){
			locationTypes.push(i);
		}
		locationTypes.sort(function (a, b){return ((a < b) ? -1 : ((a > b) ? 1 : 0));});
	}
	
	
	
	


	
		var locationhtml = "<span id='LocationInput' class='LocationInput' type='text' readonly='readonly'></span>";	
		$("#headersection").append(locationhtml);


	var locationOptionHtml = "<option value=''>Select a location</option>";
	$("#deviceListUl").html("");
	var deviceUl = $("#deviceListUl");
	deviceUl.html('');
	for(var i in locationTypes){		
		var locationType = locationTypes[i];	
		var contentDiv = $("<div></div>").addClass('widget-content');
		var detailSection = $("<div class='detailsection'></div>");
		detailSection.append("</br></br>"+locationType);
		detailSection.attr('location',locationType);
		
		var image="";
		var frame="";
		if(locationicon[locationType]["iconFromResource"] == 1)
		{
			image=locationicon[locationType]["icon"];
			contentDiv.append($("<img src='"+image+"' class='locationicon' ></img>"));
		}
		else
		{
			if(locationicon[locationType]["icon"]==null || locationicon[locationType]["icon"]==undefined || locationicon[locationType]["icon"]=='')
			{
				locationicon[locationType]["icon"] = "/imonitor/resources/images/defaultlocation.png";
				image=locationicon[locationType]["icon"];
				contentDiv.append($("<img src='"+image+"' class='locationicon' ></img>"));
			}
			else
			{
				/*image=Path+""+locationicon[locationType]["icon"];
				frame="/imonitor/resources/images/frame.png";
				contentDiv.append($("<img src='"+image+"' class='locationiconFromDb' ></img>"));
				contentDiv.append($("<img src='"+frame+"' class='LocationIconframe' ></img>"));*/
				
				//Added by Apoorva
				locationicon[locationType]["icon"] = "/imonitor/resources/images/defaultlocation.png";
				image=locationicon[locationType]["icon"];
				contentDiv.append($("<img src='"+image+"' class='locationicon' ></img>"));
				
			}
			
		}
		contentDiv.append(detailSection);
		var anchor = "<a onClick='getattr1(\""+locationType+"\")' class='getattr'></a>";
		var locationLi = $(anchor).attr('location',locationType);
		locationLi.append($("<li></li>").addClass('widgetLocation').addClass('color-white').attr("tabindex",i).append(contentDiv));
		deviceUl.append(locationLi);
		
	}
	var locationVal = $("#locationWiseSelect").val();
	$("#locationWiseSelect").html(locationOptionHtml);	
	$("#locationWiseSelect").val(locationVal);

		
	}};

	this.generateHtmlForLocationInMap = function(path){
		if(first==0)
		{
		var locationTypes = new Array();
		for(var gateWayId in gateWays){
			var locationTypesTemp = gateWays[gateWayId]["devices"]["locations"];
			for(var i in locationTypesTemp){
				locationTypes.push(i);
			}
			locationTypes.sort(function (a, b){return ((a < b) ? -1 : ((a > b) ? 1 : 0));});
		}
		
		
		for(var i in locationTypes){		
			var locationType = locationTypes[i];
			var tablerows="";
			var image="";
			if(locationicon[locationType]["iconFromResource"] == 1)
			{
				image=locationicon[locationType]["icon"];
			}
			else
			{
				if(locationicon[locationType]["icon"]==null || locationicon[locationType]["icon"]==undefined || locationicon[locationType]["icon"]=='')
				{
					locationicon[locationType]["icon"] = "/imonitor/resources/images/defaultlocation.png";
					image=locationicon[locationType]["icon"];
				}
				else
				{
					image=Path+""+locationicon[locationType]["icon"];
				}
				
			}
			if(first==0)
			{
				first=1;
				tablerows="<td class='locationselectedtab getattr firstLocation' onClick='getattr1(\""+locationType+"\",\""+image+"\",$(this))' style='text-align:center;font-family:serif;'><a id='"+locationicon[locationType]["id"]+"'>"+locationType+"</a></td>";	
			}
			else
			{
				tablerows="<td class='locationtab getattr' onClick='getattr1(\""+locationType+"\",\""+image+"\",$(this))' style='text-align: center;font-family:serif;'><a id='"+locationicon[locationType]["id"]+"'>"+locationType+"</a></td>";	
			}
			
			
			$('#listLocations').find('tbody').append(tablerows);
		}
		}
		else
		{
			return false;
		}
		};
	
	
	//bhavya start ENMGMT
	
		
this.generateTotalTab = function(storage,SlectedLanguage)
		{
			var d = new Date();
		   
		    var monthNames ="";
		   
		   
		   
			if(SlectedLanguage == 'en_US')
			{
				
			monthNames = [ "January", "February", "March", "April", "May", "June",
		    "July", "August", "September", "October", "November", "December" ];
		      }
		    else if(SlectedLanguage == 'zh_CN')
				{
		    	  monthNames = ["??", "??", "??", "??","??", "??","??","??","??","??","???","???"];
		      }
			  else if(SlectedLanguage == 'zh_TW'){
				  monthNames = ["??", "??", "??", "??","??", "??","??","??","??","??","???","???"];
				           
			  }
			var lastLoginTime=storage.find("ILT").text();
			var targetcost=storage.find("TC").text();
			var uptodateusage=storage.find("UPU").text();
			var uptodatecost=storage.find("UPCost").text();
			var uptoTimeusage=storage.find("UPTU").text();
			var uptotime=storage.find("UPTTime").text();
			var uptoTimecost=storage.find("UPTCost").text();
			var lastUsage = storage.find("LMU").text();
			lastUsage = Math.round(lastUsage);
			lastUsage=lastUsage/1000;
			uptodateusage = Math.round(uptodateusage);
			uptoTimeusage=Math.round(uptoTimeusage);
			uptoTimeusage=uptoTimeusage/1000;
			uptodateusage=uptodateusage/1000;
			var d=new Date();
			var monthNames = ["January", "February", "March", "April", "May", "June",
			                  "July", "August", "September", "October", "November", "December"
			                ];
			var n=monthNames[d.getMonth()];
			
		        var title1 = formatMessage("Energy.Totaltab");
				
				
		        
				$('#targetcost').html("");
				$('#uptodatecost').html("");
				var TargetCast="<span id='lable'>Target cost for "+ monthNames[d.getMonth()]+":</span><span id='lable'> &#8377;"+targetcost+"</span>";
				var update="<span>Upto date cost:</span><span style='padding:0px 0px 0px 6px'>&#8377;"+uptoTimecost+"</span>";
				               /*"<tr></tr><tr></tr><tr><td style='font-size:small;font-weight: bold;'><a href='totarrifConfig.action' class='editlinkForTarrif tariff' popupheight='550' popupwidth='950'style=' text-decoration: none;'><img title='Tariff Configuration' style='float:left;margin-top:-11px;width:40px;height:40px;' src='../resources/images/configsite.png'/><span>Tariff Configuration</span></a></td></tr>"+
				              "<tr><td style='font-size:small;font-weight: bold;'><a href='totargetCost.action' class='editlinkForTarrif target' popupheight='503' popupwidth='696'style=' text-decoration: none;'><img title='Tariff Configuration' style='float:left;margin-top:-11px;width:40px;height:40px;' src='../resources/images/configsite.png'/>Target Cost</a></td></tr>";*/
				$('#targetcost').append(TargetCast);
				$('#uptodatecost').append(update);
				/*var labelforboth="<tr><td id='lable'>"+formatMessage("Energy.up-to")+"</td></tr>" +
						          "<tr><td id='Usage'><h1>"+uptotime+"</h1><td></tr>"+
						          "<tr><tr></tr><tr></tr><td style='font-size:small;font-weight: bold;'><a href='totarrifConfig.action' class='editlinkForTarrif tariff' popupheight='550' popupwidth='950'style=' text-decoration: none;'><img title='Tariff Configuration' style='float:left;margin-top:-11px;width:40px;height:40px;' src='../resources/images/configsite.png'/><span>Tariff Configuration</span></a></td></tr>";
				$('#defineActionTable').append(labelforboth);*/
				
				$('#defineActionTableinside').html("");
				
				var usageDiaplay = "<h1 style='position: relative;font-size: large;padding: 13px;'>"+formatMessage("Energy.up-todate-usage")+" :"+uptoTimeusage+" kWh</h1>";
				var TargetCast="<tr><td id='lable'>"+formatMessage("Energy.up-to")+"</td></tr>" +
		                         "<tr><td id='Usage'><h1>"+uptotime+"</h1><td></tr>";
				$('#uptousagedisplay').html(usageDiaplay);
				$('#defineUsageTableinside').html(TargetCast);
				var TargetCast="<tr><td id='lable'>"+formatMessage("Energy.up-todate-cast")+"</td></tr><tr><td id='Usage'><h1>&#8377;"+uptoTimecost +"</h1></td></tr></tr>+" +
						 "<tr><tr></tr><tr></tr><td style='font-size:small;font-weight: bold;'><a href='totargetCost.action' class='editlinkForTarrif target' popupheight='503' popupwidth='696'style=' text-decoration: none;'><img title='Tariff Configuration' style='float:left;margin-top:-11px;width:40px;height:40px;' src='../resources/images/configsite.png'/>Target Cost</a></td></tr>";
		       var insidetable=$('#defineActionTableinside').append(TargetCast);
			   $('#defineActionTableborder').html("");
			   $('#defineActionTableborder').append(insidetable);
			   $("#UsageColumn").html(uptoTimeusage + " kWh");
			   $("#currentMonth").html(n);
			   $("#lastUpdate").html(uptotime);
			   $("#LastMonthUsage").html(lastUsage);
			   
			   var percentage = null;
			   var LastUsageDetails = null;
			  
			   if(lastUsage != null){
					if(lastUsage > uptoTimeusage){
						percentage = Math.round((lastUsage - uptoTimeusage)/lastUsage *100);
						
						LastUsageDetails = "<table style='float: left;'><tr><td><img style='width:60px;height:160;' src='/imonitor/resources/images/UparrowEnergy.png'></img></td><td><h1>"+percentage+"%</h1></td></tr></table>"+
	                       "<span style='font-size:small;font-weight: 600;font-family: serif;'>More energy consumed compare to last month<span>";
					}else if(uptoTimeusage > lastUsage){
						
						percentage = Math.round((uptoTimeusage - lastUsage)/uptoTimeusage *100);
						LastUsageDetails = "<table style='float: left;'><tr><td><img style='width:60px;height:160;' src='/imonitor/resources/images/Downarrowenergy.png'></img></td><td><h1>"+percentage+"%</h1></td></tr></table>"+
	                       "<span style='font-size:small;font-weight: 600;font-family: serif;'>Less energy consumed compare to last month<span>";
					}else if(uptoTimeusage == lastUsage){
						percentage = Math.round((lastUsage - uptoTimeusage)/lastUsage *100);
						LastUsageDetails = "<table style='float: left;'><tr><td><img style='width:60px;height:160;' src='/imonitor/resources/images/equal.png'></img></td><td><h1>"+percentage+"%</h1></td></tr></table>"+
	                       "<span style='font-size:small;font-weight: 600;font-family: serif;'>Equal energy consumed compare to last month<span>";
					}
				
					$("#lastMonthUsageInfo").html("");
					$("#lastMonthUsageInfo").append(LastUsageDetails);
			   }else{
				   $("#lastMonthUsageInfo").html("");
				
			   }
			   
			   
		};	
/*this.generateTotalTab = function(storage,SlectedLanguage)
{
	var d = new Date();
   
    var monthNames ="";
   
   
   
	if(SlectedLanguage == 'en_US')
	{
		
	monthNames = [ "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December" ];
      }
    else if(SlectedLanguage == 'zh_CN')
		{
    	  monthNames = ["??", "??", "??", "??","??", "??","??","??","??","??","???","???"];
      }
	  else if(SlectedLanguage == 'zh_TW'){
		  monthNames = ["??", "??", "??", "??","??", "??","??","??","??","??","???","???"];
		           
	  }
	var lastLoginTime=storage.find("ILT").text();
	var targetcost=storage.find("TC").text();
	var uptodateusage=storage.find("UPU").text();
	var uptodatecost=storage.find("UPCost").text();
	var uptoTimeusage=storage.find("UPTU").text();
	var uptotime=storage.find("UPTTime").text();
	var uptoTimecost=storage.find("UPTCost").text();
	 uptodateusage = Math.round(uptodateusage);
	uptoTimeusage=Math.round(uptoTimeusage);
	uptoTimeusage=uptoTimeusage/1000;
	uptodateusage=uptodateusage/1000;
        var title1 = formatMessage("Energy.Totaltab");
		
		
        
		$('#defineActionTable').html("");
		var TargetCast="<tr style='height: 58px;'><tr><td id='lable'>"+ monthNames[d.getMonth()]+" "+formatMessage("Energy.target-cast")+"</td></tr><tr><td id='Usage'><h1>&#8377;"+targetcost +"</h1></td></tr></tr><tr style='height: 58px;'>";
        $('#defineActionTable').append(TargetCast);
		var labelforboth="<tr><td id='lable'>"+formatMessage("Energy.up-to")+"  "+ uptotime +" </td></tr>"
		 $('#defineActionTable').append(labelforboth);
		
		$('#defineActionTableinside').html("");
		
		
		var TargetCast="<tr><td id='lable'>"+formatMessage("Energy.up-todate-usage")+"</td></tr><tr><td id='Usage'><h1>"+uptoTimeusage +"KWh</h1></td></tr></tr>";
        $('#defineActionTableinside').append(TargetCast);
		var TargetCast="<tr style='height: 20px;'><tr><td id='lable'>"+formatMessage("Energy.up-todate-cast")+"</td></tr><tr><td id='Usage'><h1>&#8377;"+uptoTimecost +"</h1></td></tr></tr><tr style='height: 10px;'>";
       var insidetable=$('#defineActionTableinside').append(TargetCast);
	   $('#defineActionTableborder').html("");
	   $('#defineActionTableborder').append(insidetable);
		
};	
*/
this.tarrifConfig = function()
{
	
    var initialActionRows = "<tr>";
	initialActionRows += "<th>"+formatMessage("setup.rules.time.start")+"</th><th></th><th>"+formatMessage("setup.rules.time.end")+"</th><th></th><th></th><th>"+formatMessage("Energy.TariffRate")+"(&#8377;)</th><th></th><th></th><th colspan=2>"+formatMessage("Energy.slabrange")+"</th></tr><tr>";
	initialActionRows += "<tr style='border: 1px solid lightgrey;' class='selectTimeRange' id='timetable'><td><span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: 0px 0px 0px 0px;'>*</span>"+formatMessage("setup.rules.time.hour")+"<select id='StartHourSelect' class='StartHourSelect'></select></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: 0px 0px 0px 0px;'>*</span>"+formatMessage("setup.rules.time.hour")+"<select id='EndHourSelect' class='EndHourSelect'></select></td><td></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: 0px 0px 0px 0px;'>*</span><input type='text' size='10' name='actionvalueclass' class='actionvalueclass'/>&#8377;</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td></td><td><span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: 0px 0px 0px 0px;'>*</span>"+formatMessage("Energy.Unit")+"<input type='text' size='4' name='startslabrange' class='startslabrange'/></td><td>"+formatMessage("Energy.to")+"<input type='text' size='4' name='Endslabrange' class='Endslabrange'/></td>";
	initialActionRows +="<td><a href='#' class='AddNextRowinTarrif' title="+formatMessage("Tooltip.add")+"><img src='/imonitor/resources/images/Upbutton.png' style='width: 25px; height: 25px;'></img></a></td>";
	initialActionRows +="<td><a href='#' class='removeCurrentRowinTarrif'><img src='/imonitor/resources/images/delete2.png'></img></a></td><td><input type='hidden' name='TariffID' class='TariffID' /></td><td><input type='hidden' name='alertExpressions' /></td></tr>";
    $('#Tarriffconfigtable').append(initialActionRows);
		
		
};	

this.generatelabel = function(labelroom)
{
		
		$('#labelforroom').html("");
		var lables="<table style='font-family:serif;float: right; margin: -535px 137px 0px 0px;'><tr style='font-family:serif;margin: 0px 0px 0px 312px;'><td id='graphtittle'><h1>"+labelroom +"</h1></td><tr></table>";
        $('#labelforroom').append(lables);	
};			
	
		
this.generateHtmlForLocationInENMGMT = function(storage,watrooms,topdev){
		
		
		
		
		var locationTypes = new Array();
		for(var gateWayId in gateWays){

			var locationTypesTemp = gateWays[gateWayId]["devices"]["locations"];
			for(var i in locationTypesTemp)
			{
			
				locationTypes.push(i);
			}
			locationTypes.sort(function (a, b){return ((a < b) ? -1 : ((a > b) ? 1 : 0));});
		}
		
		
		var deviceDetails = {};
		for(var gateWayId in gateWays)
		{
			var deviceDetailsTmp = gateWays[gateWayId]["devices"]["locations"];
			$.extend(deviceDetails, deviceDetailsTmp);

		}
		$('#accordionList').html("");
		$('#devicelist').html("");
		$('#toppowercomp').html("");
		var totalwattage=storage.find("TOW").text();	
		totalwattage=totalwattage/1000;
		var watval=null;
		var locations=null;
		var devname=null;
		var alertval=null;
		var comp;
		var powerconsumdev=$("<ul></ul>");
		
		$(topdev).find("toppower").each(function() {
			devname=$(this).find("devname").text();
			alertval=$(this).find("devval").text();
			powerconsumdev.append("<li style='padding:2px;'><label style='font-family:serif;font-size:14px;font-weight:bold;'>"+devname+"=</label><lable style='font-family:serif;font-size:14px;font-weight:bold;'>"+alertval+"kWh</label></li>");
		});
		$('#toppowercomp').append(powerconsumdev);
		/*$('#accordionList').html("");*/
		//var TotalTab="<li href='piechart.action' class='editlink totalrow' label='Total' labelroom='"+formatMessage("Energy.Totaltab")+"'>"+formatMessage("Energy.Total")+"</li>";
		var TotalTab="<li id='roomHead'style='font-family:serif;font-size:16px;text-align:center;height:21px;background:grey;'>List of Rooms</li><li id=accordion class='editlink totalrow' label='Total' labelroom='"+formatMessage("Energy.Totaltab")+"'>"+formatMessage("Energy.Total")+"<br><br><label>"+totalwattage+"kWh</label>"+"</li>";
		$('#accordionList').append(TotalTab);
		var DeviceTab="<li id='devicehead'style='font-family:serif;font-size:16px;text-align:center;height:21px;background:grey;'>List of Devices</li>";
		var Devicelst="<li></li>";
		$('#devicelist').append(DeviceTab);
		for(var i in locationTypes){		
			var locationType = locationTypes[i];
			var tablerows=$("<ul id='devicedata'></ul>");
			var image="";
			var devices="";
			var LocationId=locationicon[locationType]["id"];
			
			
			var detailSection = $("<div></div>");
			//var contentDiv = $("(<li id=accordion href='piechartroom.action' LocationId="+LocationId+" class="+LocationId+" labelroom="+ locationType +" ></li>)").attr("labelroom",locationType);
			var contentDiv = $("(<li id=accordion href='GetRoomWiseDataFromDB.action' label='hiii' LocationId="+LocationId+" class="+LocationId+"  labelroom='"+ locationType +"'></li>)").attr("labelroom",locationType);
			console.log("jjiojojojo");
			
			$(watrooms).find("watdetails").each(function() {
				
				locations=$(this).find("locname").text();
				watval=$(this).find("value").text();
				if(first==0)
				{
					first=1;
					
					if(locations==locationType){
						contentDiv.append(locationType);
						contentDiv.append("<br><br><label>"+watval+"kWh</label>");
					}
					
	       		}
				else
				{
					
					if(locations==locationType){
					contentDiv.append(locationType);
					contentDiv.append("<br><br><label>"+watval+"kWh</label>");
					}
				}
			});
		
		
			
			
		for(var key in deviceDetails)
		{
		
			if(locationType == key)
			{
				var devices = deviceDetails[key];
				
				for(var dKey in devices)
				{
					var device = devices[dKey];
					var deviceLi = this.generateDeviceLiForEGM(device);
					
						if(deviceLi != false)
						{
						tablerows.append(deviceLi);
						}
				
				}
			}	
		}
		//contentDiv.append(tablerows);
		//detailSection.append(contentDiv);
		
		//detailSection.append(tablerows);
		
		//$('#accordion')html(html).accordion({collapsible: true});
		$('#accordionList').append(contentDiv);
		$('#devicelist').append(tablerows);			
		}		
		};

this.UpdateInstantPower = function(selectdevice)
{
	
		
	
		var locationTypes = new Array();
		for(var gateWayId in gateWays){
			var locationTypesTemp = gateWays[gateWayId]["devices"]["locations"];
			for(var i in locationTypesTemp)
			{
			
				locationTypes.push(i);
			}
			locationTypes.sort(function (a, b){return ((a < b) ? -1 : ((a > b) ? 1 : 0));});
		}
		
		
		var deviceDetails = {};
		for(var gateWayId in gateWays)
		{
			var deviceDetailsTmp = gateWays[gateWayId]["devices"]["locations"];
			$.extend(deviceDetails, deviceDetailsTmp);

		}
		
		
		
		for(var i in locationTypes)
		{

			var locationType = locationTypes[i];
			
		for(var key in deviceDetails)
		{
		
			
			if(locationType == key)
			{
				var devices = deviceDetails[key];
				
				for(var dKey in devices)
				{
					var device = devices[dKey];
					
					
					if(selectdevice == $.trim(device["deviceId"]))
					{
					
						
						
					var powerinfo=device["dashboardpowerinfo"];
					var DeviceLable=device["deviceName"]+" "+formatMessage("Energy.InstantPowerconsumption")+":"+powerinfo;
		
					$('#labelforroom').html("");
					var lables="<table style='float: right; margin: -535px 137px 0px 0px;'><tr style='margin: 0px 0px 0px 312px;'><td id='graphtittle'><h1>"+DeviceLable +"</h1></td><tr></table>";
					$('#labelforroom').append(lables);
		
					$('.'+selectdevice+'').attr("labelroom",DeviceLable)
					
					
					}
					
				
				}
			}	
		}
		
		
		
			
			
			
			
		}

};

	//bhavya end 	ENMGMT
	

this.generateSelectBoxesForDevice = function(){
	   
		var deviceTypes = new Array();
		for(var gateWayId in gateWays){
		
		var deviceTypesTemp = gateWays[gateWayId]["devices"]["devicetypes"];
		for(var i in deviceTypesTemp){
			deviceTypes.push(i);


		}
			deviceTypes.sort(function (a, b){return ((a < b) ? -1 : ((a > b) ? 1 : 0));});
		}
		
		
		deviceTypes = Array.from(new Set(deviceTypes)); // added this line for 3 accounts merging. For single account this account is not required
			  
		
		if($("#deviceTypeWiseSelect").length < 1)
		{
			var devicehtml;
			devicehtml = "<select onchange='javascript:userDetails.changeHtml(DEVICE_TYPE_WISE,this.value,null)' id='deviceTypeWiseSelect' class='wisetypeselect'>";
			devicehtml += "</select>";
			$("#headersection").append(devicehtml);
		}
	
	var deviceOptionhtml = "<option value=''>"+formatMessage("msg.ui.home.0001")+"</option>";
	for(var i in deviceTypes){
		var deviceType = deviceTypes[i];
		//sumit start AVOID LISTING VIRTUAL DEVICETYPE
		if((deviceType != "MODE_HOME") && (deviceType != "MODE_STAY") && (deviceType != "MODE_AWAY")){
			/*
			var deviceName = deviceType;
			if(deviceType.substr(0,3) == 'Z_W')
			{
				deviceName = deviceType.substr(7);//Eliminate Z_WAVE_
			}*/
			deviceOptionhtml += "<option value='"+deviceType+"'>"+formatMessage(deviceType)+"</option>";
		}
	}
		
	var deviceVal = $("#deviceTypeWiseSelect").val();
	$("#deviceTypeWiseSelect").html(deviceOptionhtml);
	$("#deviceTypeWiseSelect").val(deviceVal);
};
//bhavya start
this.generateSelectBoxesForDiognostic = function(){
	   
	var deviceTypes = new Array();
	for(var gateWayId in gateWays){

	var deviceTypesTemp = gateWays[gateWayId]["devices"]["devicetypes"];
	for(var i in deviceTypesTemp){
		deviceTypes.push(i);
	}
		deviceTypes.sort(function (a, b){return ((a < b) ? -1 : ((a > b) ? 1 : 0));});
	}



var deviceOptionhtml = "<option value=''>"+formatMessage("msg.ui.home.0001")+"</option>";
for(var i in deviceTypes){
	var deviceType = deviceTypes[i];
	//sumit start AVOID LISTING VIRTUAL DEVICETYPE
	if((deviceType != "MODE_HOME") && (deviceType != "MODE_STAY") && (deviceType != "MODE_AWAY")){
		deviceOptionhtml += "<option value='"+deviceType+"'>"+formatMessage(deviceType)+"</option>";
	}
}
	
var deviceVal = $("#deviceTypeWiseSelect").val();
$("#deviceTypeWiseSelect").html(deviceOptionhtml);
$("#deviceTypeWiseSelect").val(deviceVal);
};
//bhavya end

this.setStorageDetails = function(gateWayId){
	// Implement it !!
};

this.UserInformationHtml = function(user,SystemIntegratorName,SystemIntegratorMobile,SystemIntegratorLogo,path){


	var lastLoginTime=user.find("ILT").text();
	var UserName=user.find("UN").text();
	
	
	if(SystemIntegratorName =="" && SystemIntegratorMobile =="")
	{
	    $('#UserInformation').html("<span style='color:#0C5AA3;'>"+formatMessage("home.msg.0009")+"</span>"+UserName+"<br><span style='color:#0C5AA3;margin-left: -46px;'>"+formatMessage("home.msg.0017")+"</span>"+lastLoginTime+"");
	}
	else
	{
	    if(SystemIntegratorLogo!="")
	    {
	    	 $('#SIInformation').addClass('SIInformation').html("<img src='"+path+SystemIntegratorLogo+"' class='SystemIntegratorLogo' >" +
	    				"</img> &nbsp;&nbsp; <span style='font-size: smaller;color: #181F25;'>"+formatMessage("home.msg.0018")+"</span>"+" "+SystemIntegratorName+"<br>" +
	    				"&nbsp;&nbsp; <span style='font-size: smaller;color: #181F25;'>"+formatMessage("home.msg.0019")+"</span>"+" "+SystemIntegratorMobile+"<br>" +
	    				"&nbsp;&nbsp; <span style='font-size: 10px;color:#181F25;'>"+formatMessage("home.msg.0017")+"</span>"+" "+lastLoginTime+"");
	    }
	    else
	    {
	    	 $('#SIInformation').addClass('SIInformationWithoutImage').html("&nbsp;&nbsp; <span style='font-size: smaller;color: #181F25;'>"+formatMessage("home.msg.0018")+"</span>"+" "+SystemIntegratorName+"<br>" +
	    				"&nbsp;&nbsp; <span style='font-size: smaller;color: #181F25;'>"+formatMessage("home.msg.0019")+"</span>"+" "+SystemIntegratorMobile+"<br>" +
	    				"&nbsp;&nbsp; <span style='font-size: 10px;color:#181F25;'>"+formatMessage("home.msg.0017")+"</span>"+" "+lastLoginTime+"");
	    }
	    
	}
	
	
	
			//"<span style='color:#0C5AA3;'>"+formatMessage("home.msg.0009")+"</span>"+UserName+"");

	
	
	//$('#UserInformation').html("<span style='color:#0C5AA3;'>"+formatMessage("home.msg.0009")+"</span>"+UserName+"<br><span style='color:#0C5AA3;margin-left: -46px;'>"+formatMessage("home.msg.0017")+"</span>"+lastLoginTime+"");
	//<span style="color:#0C5AA3;"><s:text name="home.msg.0009" /></span> <s:property value="#session.user.userId"/><br><span style=" color:#0C5AA3;margin-left: -46px;"><s:text name="home.msg.0017" /></span><s:date name="lastLoginTime" format="dd/MM/yy 'at' hh:mm a"/>
};



this.setDeviceDetails = function(gateWayId, devc){
	var device = $(devc);
	var locationWise = gateWays[gateWayId]["devices"]["locations"];
	var deviceTypeWise = gateWays[gateWayId]["devices"]["devicetypes"];

	var deviceDetails = {};
	var deviceIndex = device.find("index").text();
	var deviceId = device.find("id").text();
	var deviceId1=device.find("deviceId").text();
	
	var location = device.find("location").text();
	var deviceType = device.find("type").text();
	var deviceArmStatus = device.find("armStatus").text();
	var rulesCount = device.find("rules").text();
	var controlNightVision = device.find("controlnightvision").text();
	var pantiltControl = device.find("pantiltControl").text();
	var lastLoginTime = device.find("lastLoginTime").text();
	var switchType = device.find("switchType").text();
	this.addLocation(location);
	//kantha start
	var locationIconFile = device.find("locationicon").text();
	locationicon[location]["icon"] = locationIconFile;
	//locationicon[location]["icon"] = device.find("locationicon").text();
	locationicon[location]["id"]=device.find("locationId").text();
	var iconContains = locationIconFile.substr(1,8);
	if((iconContains == "imonitor") || (iconContains == null)){
		locationicon[location]["iconFromResource"] = 1;
	}else{
		locationicon[location]["iconFromResource"] = 0;
	}

	//kantha end
	
	deviceDetails["index"] = deviceIndex;
	deviceDetails["deviceId"] = deviceId;
	deviceDetails["deviceId1"] = deviceId1;
	deviceDetails["deviceName"] = device.find("name").text();
	deviceDetails["deviceType"] = deviceType;
	deviceDetails["deviceLocation"] = location;
	
	deviceDetails["commandparam"] = device.find("commandparam").text();
	deviceDetails["modelnumber"] = device.find("modelnumber").text();
//	deviceDetails["fanModes"] = device.find("fanModes").text();
	if(deviceDetails["modelnumber"]=="HMD")
	{
		deviceDetails["HMDalertTime"] = device.find("HMDalertTime").text();
		deviceDetails["HMDalertstatus"] = device.find("HMDalertstatus").text();
		deviceDetails["HMDalertValue"] = device.find('HMDalertValue').text();
		deviceDetails["HMDpowerinfo"] = device.find('HMDpowerinfo').text();
		
	}
	deviceDetails["alertparam"] = device.find("alertparam").text();
	deviceDetails["batterylevel"] = device.find("batterylevel").text();
	deviceDetails["iconfile"] = device.find("iconfile").text();
	deviceDetails["gatewayid"] = gateWayId;
	deviceDetails["deviceArmStatus"] = deviceArmStatus;
	deviceDetails["rulescount"] = rulesCount;
	deviceDetails["controlNightVision"] = controlNightVision;
	deviceDetails["pantiltControl"] = pantiltControl;	
	deviceDetails["enableStatus"] = device.find("enableStatus").text();
	deviceDetails["make"] = device.find("make").text();
	deviceDetails["enableList"] = device.find("enableList").text(); //sumit start: Handle Device getting Listed on home screen.
	//sumit start: Re-Ordering Of Devices.
	var positionIdx = device.find("positionIndex").text();
	deviceDetails["positionIndex"] = positionIdx;
	//sumit end: Re-Ordering Of Devices.
	deviceDetails["top"] = device.find("topMap").text();
	deviceDetails["left"] = device.find("leftMap").text();
	deviceDetails["mapid"] = device.find("mapId").text();
	deviceDetails["presetvalue"]=device.find("presetvalue").text();
	deviceDetails["lastLoginTime"]=lastLoginTime;
	deviceDetails["fanModeValue"] = device.find("fanModeValue").text();
	deviceDetails["fanMode"] = device.find("fanMode").text();
	deviceDetails["acSwing"] = device.find("acSwing").text();
	deviceDetails["acLearnValue"] = device.find("acLearnValue").text();
	deviceDetails["sensedTemp"] = device.find("sensedTemp").text();
	deviceDetails["switchType"] = switchType;
	//alert("tantandon: "+ deviceDetails["acLearnValue"]);
	var alertValue = device.find("alertValue").text();
	var alerttime = device.find("alerttime").text();
	deviceDetails["dashboardpowerinfo"] = device.find('dashboardpowerinfo').text();
	deviceDetails["alertValue"]=alertValue;
	deviceDetails["alerttime"]=alerttime;
	
	//Indoor Unit Configuration Apoorva
	deviceDetails["fanModeCapability"] = device.find("fanModeCapability").text();
	deviceDetails["coolModeCapability"] = device.find("coolModeCapability").text();
	deviceDetails["heatModeCapability"] = device.find("heatModeCapability").text();
	deviceDetails["autoModeCapability"] = device.find("autoModeCapability").text();
	deviceDetails["dryModeCapability"] = device.find("dryModeCapability").text();
	deviceDetails["fanDirectionLevelCapability"] = device.find("fanDirectionLevelCapability").text();
	deviceDetails["fanDirectionCapability"] = device.find("fanDirectionCapability").text();
	deviceDetails["fanVolumeLevelCapability"] = device.find("fanVolumeLevelCapability").text();
	deviceDetails["fanVolumeCapability"] = device.find("fanVolumeCapability").text();
	deviceDetails["coolingUpperlimit"] = device.find("coolingUpperlimit").text();
	deviceDetails["coolingLowerlimit"] = device.find("coolingLowerlimit").text();
	deviceDetails["heatingUpperlimit"] = device.find("heatingUpperlimit").text();
	deviceDetails["heatingLowerlimit"] = device.find("heatingLowerlimit").text();
	deviceDetails["ConnectStatus"] = device.find("ConnectStatus").text();
	deviceDetails["CommStatus"] = device.find("CommStatus").text();
	deviceDetails["fanVolumeValue"] = device.find("fanVolumeValue").text();
	deviceDetails["fanDirectionValue"] = device.find("fanDirectionValue").text();
	deviceDetails["errorMessage"] = device.find("errorMessage").text();
	deviceDetails["filterSignStatus"] = device.find("filterSignStatus").text();
	deviceDetails["temperatureValue"] = device.find("temperatureValue").text();
	
	if(locationWise[location] == undefined){
		locationWise[location] = {};
	}
	if(deviceTypeWise[deviceType] == undefined){
		deviceTypeWise[deviceType] = {};
	}
	locationWise[location][deviceId] = deviceDetails;
	deviceTypeWise[deviceType][deviceId] = deviceDetails;
};


//vibhu start
this.clearScenarios = function()
{
	scenarios = new Array();
};

this.setScenarioDetails = function(scen)
{
	
	var scenario = $(scen);
	var scenarioDetails = {};
	var scenarioId = scenario.find("id").text();
	scenarioDetails["id"] = scenarioId;	
	scenarioDetails["name"] = scenario.find("name").text();
	scenarioDetails["description"] = scenario.find("description").text();
	scenarioDetails["iconfile"] = scenario.find("iconfile").text();
	scenarios.push(scenarioDetails);
};

this.generateScenarioHtml = function(device)
{

	var deviceUl = $("#deviceListUl");
	deviceUl.html('');
	for (var i in scenarios)
	{

		scenario = scenarios[i];
		var contentDiv = $("<div></div>").addClass('widget-content');
		var detailSection = $("<div class='detailsection'></div>");
		var Title = scenario["name"];
		detailSection.append(scenario["name"]+"<br/><br/>"+scenario["description"]);

		if(scenario["iconfile"]==null || scenario["iconfile"]==undefined || scenario["iconfile"]=='')
		{
			scenario["iconfile"] = "/imonitor/resources/images/systemicons/defaultscenario.png";
		}


		contentDiv.append($("<img src='"+scenario["iconfile"]+"' class='locationicon' ></img>"));
		contentDiv.append(detailSection);	
		try
		{
		contentDiv.append($("<a></a>")
				.attr('href','executeScenario.action?scenario.id='+scenario["id"])
				.attr('title',Title)
				.addClass('executeScenarios')
				.html("<img src='/imonitor/resources/images/executescenario.png' class='rightsideimage' ></img>")
				);
		}
		catch(err)
		{}
		var scenarioLi = $("<li></li>").addClass('widget').addClass('color-white').attr("tabindex", scenario["id"]).append(contentDiv);
		deviceUl.append(scenarioLi);

	}
	
};
//vibhu end

this.changeEnableDeviceDetails = function(devc){
	var device = $(devc);
	var enableStatus = device.find("enableStatus").text();
	var deviceId = device.find("id").text();
	var enableElement = document.getElementById('enableDisable'+deviceId );
	
	if(enableStatus == 0)
	{
		enableElement.setAttribute('statusName', '1');
	 
		enableElement.innerHTML='<img src=/imonitor/resources/images/enablebutton.png class="enableclass" title="click to enable camera" ></img>';
	
	}
	else
	{
		enableElement.setAttribute('statusName', '0');
	
		enableElement.innerHTML='<img src=/imonitor/resources/images/disablebutton.png class="enableclass" title="click to disable camera" ></img>';
	
	}	
};


	
this.changeDeviceDetails = function(devc){
	
	var device = $(devc);
	var deviceIndex = device.find("index").text();
	var deviceId = device.find("id").text();
	var location = device.find("location").text();
	var deviceType = device.find("type").text();
	var deviceArmStatus = device.find("armStatus").text();
	var rulesCount = device.find("rules").text();
	var controlNightVision = device.find("controlnightvision").text();
	var pantiltControl = device.find("pantiltControl").text();
	var switchType = device.find("switchType").text();
	
	var deviceDetails = {};
	deviceDetails["index"] = deviceIndex;
	deviceDetails["deviceId"] = deviceId;
	deviceDetails["deviceName"] = device.find("name").text();
	deviceDetails["modelnumber"] = device.find("modelnumber").text();
	deviceDetails["deviceType"] = deviceType;
	deviceDetails["deviceLocation"] = location;
	deviceDetails["commandparam"] = device.find("commandparam").text();
	deviceDetails["alertparam"] = device.find("alertparam").text();
	deviceDetails["batterylevel"] = device.find("batterylevel").text();
	deviceDetails["iconfile"] = device.find("iconfile").text();
	deviceDetails["deviceArmStatus"] = deviceArmStatus;
	deviceDetails["rulescount"] = rulesCount;
	deviceDetails["controlNightVision"] = controlNightVision;
	deviceDetails["pantiltControl"] = pantiltControl;
	deviceDetails["enableStatus"] = device.find("enableStatus").text();
	deviceDetails["presetvalue"] = device.find("presetvalue").text();
	deviceDetails["fanModeValue"] = device.find("fanModeValue").text();
	deviceDetails["fanMode"] = device.find("fanMode").text();
	deviceDetails["acSwing"] = device.find("acSwing").text();
	deviceDetails["acLearnValue"] = device.find("acLearnValue").text();
	deviceDetails["sensedTemp"] = device.find("sensedTemp").text();
	var alertValue = device.find("alertValue").text();
	var alerttime = device.find("alerttime").text();
	deviceDetails["alertValue"]=alertValue;
	deviceDetails["alerttime"]=alerttime;
	if(deviceDetails["modelnumber"]=="HMD")
	{
		deviceDetails["HMDalertValue"] = device.find('HMDalertValue').text();
		deviceDetails["HMDpowerinfo"] = device.find('HMDpowerinfo').text();
	}
    
	deviceDetails["switchType"] = switchType;
	//Indoor Unit Configuration added by Apoorva
	deviceDetails["fanModeCapability"] = device.find("fanModeCapability").text();
	deviceDetails["coolModeCapability"] = device.find("coolModeCapability").text();
	deviceDetails["heatModeCapability"] = device.find("heatModeCapability").text();
	deviceDetails["autoModeCapability"] = device.find("autoModeCapability").text();
	deviceDetails["dryModeCapability"] = device.find("dryModeCapability").text();
	deviceDetails["fanDirectionLevelCapability"] = device.find("fanDirectionLevelCapability").text();
	deviceDetails["fanDirectionCapability"] = device.find("fanDirectionCapability").text();
	deviceDetails["fanVolumeLevelCapability"] = device.find("fanVolumeLevelCapability").text();
	deviceDetails["fanVolumeCapability"] = device.find("fanVolumeCapability").text();
	deviceDetails["coolingUpperlimit"] = device.find("coolingUpperlimit").text();
	deviceDetails["coolingLowerlimit"] = device.find("coolingLowerlimit").text();
	deviceDetails["heatingUpperlimit"] = device.find("heatingUpperlimit").text();
	deviceDetails["heatingLowerlimit"] = device.find("heatingLowerlimit").text();
	deviceDetails["ConnectStatus"] = device.find("ConnectStatus").text();
	deviceDetails["CommStatus"] = device.find("CommStatus").text();
	deviceDetails["fanModeValue"] = device.find("fanModeValue").text();
	deviceDetails["fanVolumeValue"] = device.find("fanVolumeValue").text();
	deviceDetails["fanDirectionValue"] = device.find("fanDirectionValue").text();
	deviceDetails["errorMessage"] = device.find("errorMessage").text();
	deviceDetails["filterSignStatus"] = device.find("filterSignStatus").text();
	deviceDetails["temperatureValue"] = device.find("temperatureValue").text();
	
	for(var gateWayId in gateWays){
		var locationWise = gateWays[gateWayId]["devices"]["locations"];
		var deviceTypeWise = gateWays[gateWayId]["devices"]["devicetypes"];
		deviceDetails["gatewayid"] = gateWayId;
		
		if(locationWise[location][deviceId] != undefined){
			var curLi = locationWise[location][deviceId]["deviceli"];
			var neLi = this.generateDevicesHtml(deviceDetails,true);
			if(neLi == false)
			{
				break;
			}
			else
			{
			curLi.replaceWith(neLi);
			deviceDetails["deviceli"] = neLi;
			locationWise[location][deviceId] = deviceDetails;
			deviceTypeWise[deviceType][deviceId] = deviceDetails;
			break;
			}
		}
	}
	
};

this.changeDeviceDetailsForControlDeviceFromMap = function(devc){
	var device = $(devc);
	var deviceIndex = device.find("index").text();
	var deviceId = device.find("id").text();
	var location = device.find("location").text();
	var deviceType = device.find("type").text();
	var deviceArmStatus = device.find("armStatus").text();
	var rulesCount = device.find("rules").text();
	var controlNightVision = device.find("controlnightvision").text();
	var pantiltControl = device.find("pantiltControl").text();
	var switchType = device.find("switchType").text();
	var deviceDetails = {};
	deviceDetails["index"] = deviceIndex;
	deviceDetails["deviceId"] = deviceId;
	deviceDetails["deviceName"] = device.find("name").text();
	deviceDetails["modelnumber"] = device.find("modelnumber").text();
	deviceDetails["deviceType"] = deviceType;
	deviceDetails["deviceLocation"] = location;
	deviceDetails["commandparam"] = device.find("commandparam").text();
	deviceDetails["alertparam"] = device.find("alertparam").text();
	deviceDetails["HMDalertValue"] = device.find('HMDalertValue').text();
	deviceDetails["HMDpowerinfo"] = device.find('HMDpowerinfo').text();
	deviceDetails["batterylevel"] = device.find("batterylevel").text();
	deviceDetails["iconfile"] = device.find("iconfile").text();
	deviceDetails["deviceArmStatus"] = deviceArmStatus;
	deviceDetails["rulescount"] = rulesCount;
	deviceDetails["controlNightVision"] = controlNightVision;
	deviceDetails["pantiltControl"] = pantiltControl;
	deviceDetails["fanModeValue"] = device.find("fanModeValue").text();
	deviceDetails["fanMode"] = device.find("fanMode").text();
	deviceDetails["acSwing"] = device.find("acSwing").text();
	deviceDetails["acLearnValue"] = device.find("acLearnValue").text();
	deviceDetails["sensedTemp"] = device.find("sensedTemp").text();
	var alertValue = device.find("alertValue").text();
	var alerttime = device.find("alerttime").text();
	deviceDetails["alertValue"]=alertValue;
	deviceDetails["alerttime"]=alerttime;
	deviceDetails["switchType"] = switchType;
	for(var gateWayId in gateWays){
		var locationWise = gateWays[gateWayId]["devices"]["locations"];
		var deviceTypeWise = gateWays[gateWayId]["devices"]["devicetypes"];
		deviceDetails["gatewayid"] = gateWayId;
		
		if(locationWise[location][deviceId] != undefined){
			var curLi = locationWise[location][deviceId]["deviceli"];
			var neLi = this.generateDevicesHtmlForDeviceControlInMap(deviceDetails,true);
			return neLi;
			/*if(neLi == false)
			{
				break;
			}
			else
			{
			curLi.replaceWith(neLi);
			deviceDetails["deviceli"] = neLi;
			locationWise[location][deviceId] = deviceDetails;
			deviceTypeWise[deviceType][deviceId] = deviceDetails;
			break;*/
			}
		}
	
	
};

//bhavya
this.generateHtmlForDeviceDiagnosis = function(classification, wiseType){
	if(classification == undefined){
		if(lastSelectedClassification != LOCATION_WISE)
		{
		classification = lastSelectedClassification;
		}
		else
		{
			classification = DEVICE_DIAGNOSTIC_WISE;

		}	
	}
	
	//alert("classfication="+classification);
	lastSelectedClassification = classification;
	if(wiseType == undefined){
		wiseType = lastSelectedWiseForDevice;
		lastSelectedWiseForDevice = wiseType;
	}
	
	lastSelectedWiseForDevice = wiseType;
	
	//DEVICE_TYPE_WISE,""
	var deviceDetails = {};
	for(var gateWayId in gateWays)
	{
		var deviceDetailsTmp = classification == DEVICE_TYPE_WISE ? gateWays[gateWayId]["devices"]["devicetypes"] : gateWays[gateWayId]["devices"]["locations"];
		$.extend(deviceDetails, deviceDetailsTmp);
	}
	classification = DEVICE_DIAGNOSTIC_WISE;
		if(classification==DEVICE_DIAGNOSTIC_WISE)
		{
		var deviceUl = $("#deviceListUl");
		var deviceList = [];
		for(var key in deviceDetails){
			if(wiseType == "" || wiseType == key){
				var devices = deviceDetails[key];
				for(var dKey in devices){
					var device = devices[dKey];
					var deviceLi = this.generateDiagnosisDevicesHtml(device,false);
					if(deviceLi !=false){
						device["deviceli"] = deviceLi;
						deviceList.push(deviceLi);
					}
				}
			}
		}
		
		deviceList.sort(function(a, b){
			return a.attr("tabindex") - b.attr("tabindex");
		});
		
		for ( var i = 0; i < deviceList.length; i++) {
			var deviceLi = deviceList[i];
			var oldLi = deviceUl.find('li[tabindex=' + deviceLi.attr("tabindex") + ']');
			if(oldLi.length > 0){
				oldLi.replaceWith(deviceLi);
			}else{
				deviceUl.append(deviceLi);
			}
		}
		}
};

//bhavya end
this.generateHtml = function(classification, wiseType){
	
	if(classification == undefined){
		if(lastSelectedClassification != LOCATION_WISE)
		{
		classification = lastSelectedClassification;
		}
		else
		{
			classification = DEVICE_TYPE_WISE;
		}	
	}
	//alert("classfication="+classification);
	classification = DEVICE_TYPE_WISE;
	lastSelectedClassification = classification;
	if(wiseType == undefined){
		wiseType = lastSelectedWiseForDevice;
		lastSelectedWiseForDevice = wiseType;
	}
	
	lastSelectedWiseForDevice = wiseType;
	
	//DEVICE_TYPE_WISE,""
	var deviceDetails = {};
	var gatewayIds = [];
	var deviceDetails1= {};
	var deviceDetails2= {};
	var deviceDetails3= {};
	
	
	if (this.gateWayCount > 1) 
	{
		for(var gateWayId in gateWays)
		{
			gatewayIds.push(gateWayId);
		}
		var gatewayid1=gatewayIds[0];
		var gatewayid2=gatewayIds[1];
		var gatewayid3=gatewayIds[2];
		deviceDetails1=gateWays[gatewayid1]["devices"]["devicetypes"];
		deviceDetails2=gateWays[gatewayid2]["devices"]["devicetypes"];
		deviceDetails3=gateWays[gatewayid3]["devices"]["devicetypes"];
		
		
		if(classification==DEVICE_TYPE_WISE)
		{
		var deviceUl = $("#deviceListUl");
		var deviceList = [];
		for(var key in deviceDetails1){
			if(wiseType == "" || wiseType == key){
				var devices = deviceDetails1[key];
				for(var dKey in devices){
					
					var device = devices[dKey];
					//alert("generated Device id -->"+device["deviceId"]);
					var deviceLi = this.generateDevicesHtml(device,true);
					if(deviceLi !=false){
						device["deviceli"] = deviceLi;
						deviceList.push(deviceLi);
					}
				}
			}
		}
		for(var key in deviceDetails2){
			//alert("dKey "+key);
			if(wiseType == "" || wiseType == key){
				var devices = deviceDetails2[key];
				for(var dKey in devices){
					
					var device = devices[dKey];
					//alert("generated Device id -->"+device["deviceId"]);
					var deviceLi = this.generateDevicesHtml(device,true);
					if(deviceLi !=false){
						device["deviceli"] = deviceLi;
						deviceList.push(deviceLi);
					}
				}
			}
		}
		for(var key in deviceDetails3){
			//alert("dKey "+key);
			if(wiseType == "" || wiseType == key){
				var devices = deviceDetails3[key];
				for(var dKey in devices){
					
					var device = devices[dKey];
					var deviceLi = this.generateDevicesHtml(device,true);
					if(deviceLi !=false){
						device["deviceli"] = deviceLi;
						deviceList.push(deviceLi);
					}
				}
			}
		}
		
		deviceList.sort(function(a, b){
			return a.attr("tabindex") - b.attr("tabindex");
		});
		
		
		
		for ( var i = 0; i < deviceList.length; i++) {
			var deviceLi = deviceList[i];
			var oldLi = deviceUl.find('li[tabindex=' + deviceLi.attr("tabindex") + ']');
			if(oldLi.length > 0){
				oldLi.replaceWith(deviceLi);
			}else{
				deviceUl.append(deviceLi);
			}
		}
		}
		
	}
	
	
	else
	{
	for(var gateWayId in gateWays)
	{
		//alert("gateway : " + gateWayId);
		var deviceDetailsTmp = classification == DEVICE_TYPE_WISE ? gateWays[gateWayId]["devices"]["devicetypes"] : gateWays[gateWayId]["devices"]["locations"];
		$.extend(deviceDetails, deviceDetailsTmp);
	}
	




	if(classification==DEVICE_TYPE_WISE)
	{
	var deviceUl = $("#deviceListUl");
	var deviceList = [];
	for(var key in deviceDetails){
		//alert("dKey "+key);
		if(wiseType == "" || wiseType == key){
			var devices = deviceDetails[key];
			for(var dKey in devices){
				
				var device = devices[dKey];
			//	alert("generated Device id -->"+device["deviceId"]);
				var deviceLi = this.generateDevicesHtml(device,true);
				if(deviceLi !=false){
					device["deviceli"] = deviceLi;
					deviceList.push(deviceLi);
				}
			}
		}
	}
	
	deviceList.sort(function(a, b){
		return a.attr("tabindex") - b.attr("tabindex");
	});
	
	
	
	for ( var i = 0; i < deviceList.length; i++) {
		var deviceLi = deviceList[i];
		var oldLi = deviceUl.find('li[tabindex=' + deviceLi.attr("tabindex") + ']');
		if(oldLi.length > 0){
			oldLi.replaceWith(deviceLi);
		}else{
			deviceUl.append(deviceLi);
		}
	}
	}
	}
	
};

this.newLocationBasedDeviceLists=function()
{
	var locationTypes = new Array();
	var deviceDetails = {};
	for(var gateWayId in gateWays)
	{
		var locationTypesTemp = gateWays[gateWayId]["devices"]["locations"];
		$.extend(deviceDetails, locationTypesTemp);
		
		for(var i in locationTypesTemp)
		{
			locationTypes.push(i);
		}
		locationTypes.sort(function (a, b){return ((a < b) ? -1 : ((a > b) ? 1 : 0));});
	}

	for(var i in locationTypes)
	{		
		var locationType = locationTypes[i];	
		var tablerows=$("<ul>"+locationType+"</ul>");
		
		
		
		
		
		
		var newTd=$("<li></li>");
		
		
		
		for(var key in deviceDetails)
		{
			if(locationType == key)
			{
				var devices = deviceDetails[key];
				for(var dKey in devices)
				{
					var device = devices[dKey];
					
					var deviceLi = this.generateDevicesTableColumn(device,false);
					newTd.append(deviceLi);
				}
			}
			
		}
		
		tablerows.append(newTd);
		
		$('#Locationaccordion').append(tablerows);
	}

};

this.generateHtmlForLocation = function(classification, wiseType){
	if(classification == undefined){
		classification = LOCATION_WISE;
	}
	lastSelectedClassification = classification;
	if(wiseType == undefined){
		wiseType = lastSelectedWise;
		lastSelectedWise = wiseType;
	}
	lastSelectedWise = wiseType;
	var deviceDetails = {};
	
	//Multiple gateway
	//Addded by apoorva start
	var gatewayIds = [];
	var deviceDetails1= {};
	var deviceDetails2= {};
	var deviceDetails3= {};
	
	
	if (this.gateWayCount > 1) 
	{
		for(var gateWayId in gateWays)
		{
			gatewayIds.push(gateWayId);
		}
		var gatewayid1=gatewayIds[0];
		var gatewayid2=gatewayIds[1];
		var gatewayid3=gatewayIds[2];
		
		
		
		deviceDetails1=gateWays[gatewayid1]["devices"]["locations"];
		deviceDetails2=gateWays[gatewayid2]["devices"]["locations"];
		deviceDetails3=gateWays[gatewayid3]["devices"]["locations"];
		
		if(classification==LOCATION_WISE)
		{
		var deviceUl = $("#deviceListUl");
		var deviceList = [];
		for(var key in deviceDetails1){
			if(wiseType == key)
			{
				var devices = deviceDetails1[key];
				for(var dKey in devices){
					var device = devices[dKey];
					var deviceLi = this.generateDevicesHtml(device,true); //naveen changed to true on 20th Jan 2015
					if(deviceLi !=false){
						device["deviceli"] = deviceLi;
						deviceList.push(deviceLi);
					}else if(wiseType == ""){
						this.generateSelectBoxes();
					}else{
						this.generateSelectBoxes();
					}
				}
			}
		}
		for(var key in deviceDetails2){
			if(wiseType == key)
			{
				var devices = deviceDetails2[key];
				for(var dKey in devices){
					var device = devices[dKey];
					var deviceLi = this.generateDevicesHtml(device,true); //naveen changed to true on 20th Jan 2015
					if(deviceLi !=false){
						device["deviceli"] = deviceLi;
						deviceList.push(deviceLi);
					}else if(wiseType == ""){
						this.generateSelectBoxes();
					}else{
						this.generateSelectBoxes();
					}
				}
			}
		}
		
		for(var key in deviceDetails3){
			if(wiseType == key)
			{
				var devices = deviceDetails3[key];
				for(var dKey in devices){
					var device = devices[dKey];
					var deviceLi = this.generateDevicesHtml(device,true); //naveen changed to true on 20th Jan 2015
					if(deviceLi !=false){
						device["deviceli"] = deviceLi;
						deviceList.push(deviceLi);
					}else if(wiseType == ""){
						this.generateSelectBoxes();
					}else{
						this.generateSelectBoxes();
					}
				}
			}
		}
		//sumit start: Order Devices by its Position Index, not by device.id (Applied for only Locations)
		deviceList.sort(function(a, b){
			//return a.attr("tabindex") - b.attr("tabindex");
			
			var positionA = a.attr("id").split("-");
			var positionB = b.attr("id").split("-");
			//var idxA = positionA.split("-");
			//var idxB = positionB.split("-");
			//alert(idxA[0]+", "+idxB[0]);
			//alert("a:"+positionA[0]+", b: "+positionB[0]);
			return positionA[0]-positionB[0];
		});
		
		for ( var i = 0; i < deviceList.length; i++) {
			var deviceLi = deviceList[i];
			var oldLi = deviceUl.find('li[tabindex=' + deviceLi.attr("tabindex") + ']');
			if(oldLi.length > 0){
				oldLi.replaceWith(deviceLi);
			}else{
				deviceUl.append(deviceLi);
			}
		}
		}
		
	}
	
	//Multiple gateway end
	//Addded by apoorva end
	else
		{

	//Single gatway start
	for(var gateWayId in gateWays){


		var deviceDetailsTmp = classification == DEVICE_TYPE_WISE ? gateWays[gateWayId]["devices"]["devicetypes"] : gateWays[gateWayId]["devices"]["locations"];
		
		for (var key in deviceDetailsTmp)
		{
			console.log(key, deviceDetailsTmp[key]);
		}
		
		$.extend(deviceDetails, deviceDetailsTmp);
	}
		
		var deviceUl = $("#deviceListUl");
		var deviceList = [];
		for(var key in deviceDetails){
			if(wiseType == key)
			{
				var devices = deviceDetails[key];
				for(var dKey in devices){
					var device = devices[dKey];
					var deviceLi = this.generateDevicesHtml(device,true); //naveen changed to true on 20th Jan 2015
					if(deviceLi !=false){
						device["deviceli"] = deviceLi;
						deviceList.push(deviceLi);
					}else if(wiseType == ""){
						this.generateSelectBoxes();
					}else{
						this.generateSelectBoxes();
					}
				}
			}
		}
		//sumit start: Order Devices by its Position Index, not by device.id (Applied for only Locations)
		deviceList.sort(function(a, b){
			//return a.attr("tabindex") - b.attr("tabindex");
			
			var positionA = a.attr("id").split("-");
			var positionB = b.attr("id").split("-");
			//var idxA = positionA.split("-");
			//var idxB = positionB.split("-");
			//alert(idxA[0]+", "+idxB[0]);
			//alert("a:"+positionA[0]+", b: "+positionB[0]);
			return positionA[0]-positionB[0];
		});
		
		for ( var i = 0; i < deviceList.length; i++) {
			var deviceLi = deviceList[i];
			var oldLi = deviceUl.find('li[tabindex=' + deviceLi.attr("tabindex") + ']');
			if(oldLi.length > 0){
				oldLi.replaceWith(deviceLi);
			}else{
				deviceUl.append(deviceLi);
			}
		}
		}
		//Single gateway end
};

this.generateHtmlOfDevicePerLocationForMap = function(classification, wiseType,image){

	var deviceDetails = {};
	for(var gateWayId in gateWays){
		var deviceDetailsTmp = gateWays[gateWayId]["devices"]["locations"];
		$.extend(deviceDetails, deviceDetailsTmp);
	}
		$("#DeviceDiv").html("");
		$("#cardSlots").html("<img src='"+image+"' class='myimage'></img>");
		
		var deviceList = [];
		
		for(var key in deviceDetails){
			if(wiseType == key)
			{
				var devices = deviceDetails[key];
				for(var dKey in devices){
					var device = devices[dKey];
					
					var deviceLi = this.generateDevicesDivForMap(device,false);
					
					if(deviceLi != false)
					{
						device["deviceli"] = deviceLi;
						deviceList.push(deviceLi);
					}
					
			else if(wiseType == "")
			{
				this.generateSelectBoxes();
				
			}
			else
			{
				
				this.generateSelectBoxes();
			}
			}
			}
		}
		
		deviceList.sort(function(a, b){
			return a.attr("tabindex") - b.attr("tabindex");
		});
		
		for ( var i = 0; i < deviceList.length; i++) {
			var deviceLi = deviceList[i];
			var top = deviceLi.attr('top');
			var left=deviceLi.attr('left');
			var status = deviceLi.attr('status');
			var mapid=deviceLi.attr('mapid');
			
			if(left != undefined && top != undefined)
			{
				deviceLi.css({
		             position: 'absolute',
		             zIndex:1,
		             left:left+"px",
		             top: top+"px",
		             width: "36px" ,
		           	 height: "36px" ,
		             //background: "#BCD598",
		             //border: "3px solid #333",
		             padding: "1px 1px 1px 1px",
		            // opacity: ".35"
		     	});
				if(status =="true")
				{
					deviceLi.css({background: "#F0FA03"});
				}
				else
				{
					deviceLi.css({background: "#F0FA03"});
				}
				
				deviceLi.css('border-radius' ,'7px');
				deviceLi.appendTo('#cardSlots').draggable( {
			    	containment: '#cardSlots',
			    	disabled: "true",
			    	stack: '#DeviceDiv div',
			        handle: "detailsectionMap",
			        helper: "original",
			        revert: 'true',
			        start: function() 
			        {
			        	$('#DeviceDiv div').each(function() {
							   if($(this).hasClass('ui-draggable'))
								{
								   $(this).draggable('option','disabled',true);
								  
								}
							       
						});
			        },
			        
			        drag: function(event, ui){ 
			        	$(this).animate({ width: "36px" ,height: "36px" ,background: "#F0FA03"});
			        	$(this).find("img:eq(0)").animate({ position: "relative", width: "30px" ,height: "30px",left: "3px", top: "3px" });
			        	$(this).find('img:eq(1)').animate({ position: "relative", width: "30px" ,height: "30px",left: "3px", top: "3px",margin :'-30px 0px 0px 0px'});
			        	//$(this).find('img:eq(2)').css( {position: "relative",margin: '-17px -40px 0px -1px',overflow: "visible"});
			        	
			        	$(this).find('img:eq(2)').animate( {position: "relative",margin: '-17px -40px 0px -1px',overflow: "visible" ,width: "15px", height: "15px", left: "3px", top: "3px"});
			        	//$(this).find('img:eq(2)').removeClass('deleteDeviceMap').addClass('deleteDeviceMap');
			        	
			        	//css( {position: "relative",margin: '-17px -40px 0px -1px',overflow: "visible" ,width: "15px", height: "15px", left: "3px", top: "3px"});
			        	//$(this).find('a').css( {position: "relative",margin: '-17px -40px 0px -1px' ,overflow: "visible"});
			        	$(this).find("p").remove();
			        	
		    		},
			        stop: function(event, ui) 
			        { 
			        	$('#DeviceDiv div').each(function() {
							   if($(this).hasClass('ui-draggable'))
								{
								   $(this).draggable('option','disabled',false);
								  
								}
							       
						});
			        	
			        	ClearEvent("card");
			        	ClearReposition();
			        	$DragObj= $(this);
			        	var id = $(this).attr('Id');
			        	var deviceid = $(this).attr('deviceid');
			        	
			        	var pos = $DragObj.offset(), dPos = $("#cardSlots").offset();
			        	/* alert ("Top: " + (pos.top - dPos.top) + ", Left: " + (pos.left - dPos.left));
						 alert("TOP Element:"+pos.top+"Left Element:"+pos.left);*/
						 
			           	var left = pos.left - dPos.left;
			            var top =  pos.top - dPos.top;
			           /* $DragObj.draggable('option','disabled',true);
						var params = {"device.locationMap.top":top,"device.locationMap.leftMap":left,"device.generatedDeviceId":deviceid,"device.id":id};
						$.ajax({



							url: "updatePositionOfDevice.action",
							data: params,
							success: function(){
								
							
						});*/

			            $DragObj.draggable('option','disabled',true);
						var params = {"device.locationMap.top":top,"device.locationMap.leftMap":left,"device.generatedDeviceId":deviceid,"device.id":id};
						/*$.ajax({
							url: "updatePositionOfDevice.action",
							data: params,
							success: function(){
								
							}
						});*/
						
			            /*$("#message").dialog( "destroy" );
			            $("#message").html('Please Confirm');			
			            $("#message").dialog({
							stackfix: true,
							modal: true,
							draggable: false,
							resizable: false,
							buttons: {
								Ok: function(){
									$(this).dialog("destroy");
									$DragObj.draggable('option','disabled',true);
									var params = {"device.locationMap.top":top,"device.locationMap.leftMap":left,"device.generatedDeviceId":deviceid,"device.id":id};
									$.ajax({
										url: "updatePositionOfDevice.action",
										data: params,
										success: function(){
											
										}
									});
								},
								/*Cancel: function() {
									$(this).dialog( "destroy" );
								}

							}
						});*/
			            
						/* if($DragObj.children('a').length==0)
				         {
				            var DeleteIcon=$("<a><img src='/imonitor/resources/images/pin.png' class='deleteDeviceMap' title='click Change Position'></img></a>");
				            $DragObj.append(DeleteIcon);
				          }*/
			            var DeleteIcon=$("<a class='deleteDeviceMap'></a>");
			    		DeleteIcon.html("<img src='/imonitor/resources/images/pin.png'  title='Click to change position'></img>");
			    		
			           // $DragObj.append(DeleteIcon);
			            /*var ControlIcon=$("<a class='ui-icon ui-icon-refresh controlDevice'></a>");
			        	$DragObj.append(ControlIcon);*/
			        }
			    });
			
			
		}
		else
		{
			deviceLi.css({
	           /*  position: 'static',*/
	             margin: '7px 5px 0px 0px',
	            // opacity: ".35"
	     	});
			
			
			deviceLi.appendTo( '#DeviceDiv' ).draggable( {
		    	containment: '#cardSlots',
		        stack: '#DeviceDiv div',
		        //refreshPositions: true,
		       // cursor: "pointer",
		        handle: "detailsectionMap",
		        helper: "original",
		        revert: 'true',
		        start: function(){
		        	$('#DeviceDiv div').each(function(){
						   	if($(this).hasClass('ui-draggable'))
							{
							   $(this).draggable('option','disabled',true);
							  
							}
						       
					});
		        	/*$(this).animate({ width: "27px" ,height: "40px" });
		        	$(this).find("img").animate({  width: "27px" ,height: "20px" });
		        	$(this).find('img:eq(1)').css({ margin :'-20px 0px 0px 0px'});
		        	$(this).find("p").animate("font-size" ,"3px");*/
		        },
		        drag: function(event, ui){ 
		        	$(this).animate({ width: "36px" ,height: "36px" ,background: "#F0FA03"});
		        	$(this).find("img:eq(0)").animate({ position: "relative", width: "30px" ,height: "30px",left: "3px", top: "3px" });
		        	$(this).find('img:eq(1)').animate({ position: "relative", width: "30px" ,height: "30px",left: "3px", top: "3px" ,margin :'-30px 0px 0px 0px'});
		        	$(this).find('img:eq(2)').animate({position: "relative",margin: '-17px -40px 0px -1px',overflow: "visible" ,width: "15px", height: "15px", left: "3px", top: "3px"});
		        	//$(this).find('img:eq(2)').removeClass('deleteDeviceMap').addClass('deleteDeviceMap');
		        	//$(this).find('img:eq(2)').css( {position: "relative",margin: '-17px -40px 0px -1px',overflow: "visible"});
		        	//$(this).find('img:eq(2)').css( {position: "relative",margin: '-17px -40px 0px -1px',overflow: "visible" ,width: "15px", height: "15px", left: "3px", top: "3px"});
		        	//$(this).find('a').css( {position: "relative",margin: '-17px -40px 0px -1px',overflow: "visible"});
		        	//$(this).find("p").animate("font-size" ,"3px");
		        	$(this).find("p").remove();
		        	//$(this).find( "a.ui-icon-trash" ).remove();
		        	
	    		},
		        stop: function(event, ui) 
		        { 
		        	
		        	$('#DeviceDiv div').each(function() {
						   if($(this).hasClass('ui-draggable'))
							{
							   $(this).draggable('option','disabled',false);

							  
							}
						       
					});
		        	
		        	ClearEvent("device");
		        	ClearReposition();
		        	$DragObj= $(this);
		        	
		        	var id = $(this).attr('Id');
		        	var deviceid = $(this).attr('deviceid');
		        	var mapid=$(this).attr('mapid');
		        	
		        	//alert("GEnerated:"+deviceid+"id:"+id);
		        	var pos = $(this).offset();
		            var eWidth =$(this).outerWidth();
		            var mWidth = $(this).outerWidth();
		            var left = (pos.left + eWidth - mWidth);
		            var top = pos.top;
		            $DragObj.draggable('option','disabled',true);
					/*var params = {"device.locationMap.top":top,"device.locationMap.leftMap":left, "device.generatedDeviceId":deviceid,"device.id":id};
					//var params = {"device.locationMap.top":top,"device.locationMap.leftMap":left, "device.generatedDeviceId":deviceid,"device.id":id,"device.location.name":location};
					$.ajax({
						url: "updatePositionOfDevice.action",
						data: params,
						success: function(){
						}
					});*/
		            //alert("eWidth:"+eWidth+"M:"+mWidth+"L:"+left+"T:"+top);
		           /* $("#message").dialog( "destroy" );
		            $("#message").html('Please Confirm');			
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
								//var params = {"device.locationMap.top":top,"device.locationMap.leftMap":left, "device.generatedDeviceId":deviceid,"device.id":id,"device.location.name":location};
								$.ajax({
									url: "updatePositionOfDevice.action",
									data: params,
									success: function(){
									}
								});
							},
							/*Cancel: function() {
								$(this).dialog( "destroy" );
							}

						}
					});*/
		            
		            var status = $DragObj.attr('status');
		            if(status =="true")
		     		{
		            	$DragObj.css("background", "#F0FA03");
		     		}
		     		else
		     		{
		     			$DragObj.css("background", "#F0FA03");
		     		}
		          ///  $(this).append($("<a title='Remove From Map' class='deleteDeviceMap' ></a>").append($("<img/>").attr('src','/imonitor/resources/images/delete.png')));
		            
		           /* var ControlIcon=$("<a class='ui-icon ui-icon-refresh controlDevice'></a>");
		        	$DragObj.append(ControlIcon);*/
		            
		           
		            
		            /*var DeleteIcon=$("<a class='deleteDeviceMap'></a>");
		    		DeleteIcon.html("<img src='/imonitor/resources/images/pin.png'  title='click Change Position'></img>");
		           
		    		$DragObj.append(DeleteIcon);*/
		            $DragObj.css("opacity","1.23");
		            if($DragObj.children('a').length==0)
		            {
		            var DeleteIcon=$("<a><img src='/imonitor/resources/images/pin.png' class='deleteDeviceMap' title='click Change Position'></img></a>");
		            $DragObj.append(DeleteIcon);
		            }
		        }
		    });
		}
	}
		
		
};


this.generateHtmlOfDevicePerLocationForInsertedDevice=function(Deviceid,location){
	 
	var deviceDetails = {};
	for(var gateWayId in gateWays)
	{
		var deviceDetailsTmp = gateWays[gateWayId]["devices"]["locations"];
		$.extend(deviceDetails, deviceDetailsTmp);

	}

		
		var deviceList = [];
		
		for(var key in deviceDetails){
			if(location == key)
			{
				var devices = deviceDetails[key];
				
				for(var dKey in devices)
				{
					
					if(dKey==Deviceid)
					{
					var device = devices[dKey];
					var deviceLi = this.generateDevicesHtmlForDeviceControlInMap(device,false);
					return deviceLi;
					}
			}
		}
	}
};


this.generateDeviceListforEGM=function(location){
	var deviceDetails = {};
	for(var gateWayId in gateWays)
	{
		var deviceDetailsTmp = gateWays[gateWayId]["devices"]["locations"];
		$.extend(deviceDetails, deviceDetailsTmp);

	}

		
		var deviceList = [];
		
		for(var key in deviceDetails)
		{
			if(location == key)
			{
				var devices = deviceDetails[key];
				
				for(var dKey in devices)
				{
					var device = devices[dKey];
					var deviceLi = this.generateDeviceLiForEGM(device);
					
					if(deviceLi != false)
					{
						device["deviceli"] = deviceLi;
						deviceList.push(deviceLi);
					}
				
				}
			}
		}
		
		for ( var i = 0; i < deviceList.length; i++) 
		{
			var deviceLi = deviceList[i];
			$("#"+location).append(deviceLi);
		}
};



this.generateDeviceLiForEGM = function(device){

	
	var deviceType = $.trim(device["deviceType"]);
	var location = $.trim(device["deviceLocation"]);
	var deviceName = $.trim(device["deviceName"]);
	var alertcommand = $.trim(device["alertparam"]);
	//alert("deviceType=="+deviceName);
	var deviceLi = "";
	var deviceicon=$("<img/>");
	var all=$("<div></div>");
	var Nameofdevice=$("<div></div>");
	var content=$("<div></div>");
	var enableDeviceListing = $.trim(device["enableList"]);
	
	var alertValue = $.trim(device["alertValue"]);
	var alerttime = $.trim(device["alerttime"]);
	//alert("deviceName=="+deviceName+"alertValue=="+alertValue+"alerttime=="+alerttime);
	
	if((deviceType != "MODE_HOME") && (deviceType != "MODE_STAY") && (deviceType != "MODE_AWAY") && (enableDeviceListing != "0"))
	{
		var imgContent = "";
		var top = $.trim(device["top"]);
		var left= $.trim(device["left"]);
		var isOff = $.trim(device["commandparam"]) == 0;
		var isGateWayOfline = gateWays[device["gatewayid"]]['status'] != "Online";
		var isDeviceOffline = device["alertparam"] != "DEVICE_DOWN" ? false : true;
		
		var isOffline = isDeviceOffline || isGateWayOfline;
		var powerinfo=device["dashboardpowerinfo"];
		var DeviceLable=device["deviceName"]+" "+formatMessage("Energy.InstantPowerconsumption")+":"+powerinfo;
		deviceLi = $("<li href='barchartweek.action' class='editlink "+location+"'></li>").attr("tabindex", device["index"]).attr("labelroom",DeviceLable).attr("label",device["deviceName"]).attr("deviceId",device["deviceId"]);
		Nameofdevice.append(device['deviceName']);
		//deviceLi.append(device['deviceName']);
		//deviceLi.append(Nameofdevice);
		var offLineIdleImage = "<img src='/imonitor/resources/images/idleswitch.png' class='rightsideimageEnmt idleswitch' title='inactive'></img>";
		
		var title="";
		/*if(isOffline)
		{
			title+=" "+device["deviceName"]+"  is Inactive";
			imgContent += offLineIdleImage;
			
		}*/
		/*if(isOff)
		{
			
			imgContent += "<img src='/imonitor/resources/images/offnormalswitch.png' class='switchon rightsideimageEnmt' title='click to switch on'></img>";
		}else{
			title+= formatMessage("msg.ui.setup.0010");
			imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff rightsideimageEnmt' title='click to switch off'></img>";
		}*/
		deviceicon.attr('src', device["iconfile"]).addClass('deviceLiIcon').attr('title',title);
		content.append(deviceicon);
		all.append(content);
		all.append(Nameofdevice);
		/*all.append(imgContent);*/
		deviceLi.append(all);
		deviceLi.attr("deviceId", device["deviceId"]);
		deviceLi.attr("locationName",location);
		deviceLi.attr("Id", device["index"] );
		deviceLi.attr("top", top);
		deviceLi.attr("left", left);
		deviceLi.attr("mapid",device["mapid"]);
		deviceLi.attr("status",isOffline);
		deviceLi.attr("deviceType", device["deviceType"]);
		deviceLi.attr("alertValue", device["alertValue"]);
				
	}
	else
	{
		return false;
	}

	return deviceLi;
	


}

this.generateHtmlForEdit = function(){
	
	var deviceUl = $("#deviceListUl");
	var deviceList = [];
	for(var gateWayId in gateWays){
		var deviceDetails = gateWays[gateWayId]["devices"]["devicetypes"];
		
		if($("#devicelistheaderdiv").length < 1){
			var headerDiv = $("<div id='devicelistheaderdiv'></div>");
			var headerUl = $("<ul class='headerul'></ul>");
			headerUl.append("<li><span>"+formatMessage("setup.devices.name")+"</span></li><li><span>"+formatMessage("setup.devices.type")+"</span></li><li><span>"+formatMessage("setup.devices.location")+"</span></li><li><span>"+formatMessage("setup.devices.action")+"</span></li>");
			headerDiv.append(headerUl);
			$("#deviceSection").prepend("&nbsp;");
			$("#deviceSection").prepend(headerDiv);
		}
		for(var key in deviceDetails){
			var devices = deviceDetails[key];
			for(var dKey in devices){
				var device = devices[dKey];
				var deviceLi = this.generateDevicesHtmlForEdit(device);
				device["deviceli"] = deviceLi;
				deviceList.push(deviceLi);
			}
		}
	}
	deviceList.sort(function(a, b){
		return a.attr("tabindex") - b.attr("tabindex");
	});
	deviceUl.html("");
	for ( var i = 0; i < deviceList.length; i++) {
		var deviceLi = deviceList[i];
			deviceUl.append(deviceLi);
		//var oldLi = deviceUl.find('li[tabindex=' + deviceLi.attr("tabindex") + ']');
		//if(oldLi.length > 0){
		//	oldLi.replaceWith(deviceLi);
		//}else{
		//	deviceUl.append(deviceLi);
		//}
	}
};
//SUMIT TBD AVOID LISTING OF VIRTUAL DEVICE and UserChecked Configured Devices ON HOME PAGE
// Generating the html for Devices.
this.generateDevicesHtml = function(device,isForChangeDevice){
	var modelnumber = $.trim(device["modelnumber"]);
	var deviceType = $.trim(device["deviceType"]);
	
	var alertcommand = $.trim(device["alertparam"]);
	var enableDeviceListing = $.trim(device["enableList"]);
    var gatewayVersion = gateWays[device["gatewayid"]]['gateWayVersion'];
	if((deviceType != "MODE_HOME") && (deviceType != "MODE_STAY") && (deviceType != "MODE_AWAY") && (enableDeviceListing != "0"))
	{

	var deviceUl = $("#deviceListUl");
	var isFirst = deviceUl.find('li[tabindex=' + device["index"] + ']').get(0) == undefined;

	//vibhu change to stop refresh if command execution in progress
	var str = deviceUl.find('li[tabindex=' + device["index"] + ']').html();
	if(!isForChangeDevice && str != null && str.toLowerCase().indexOf("commandexecutioninprogress") >= 0)
	{
		return false;
	}

	var isGateWayOfline = gateWays[device["gatewayid"]]['status'] != "Online";
	var isDeviceOffline = device["alertparam"] != "DEVICE_DOWN" ? false : true;
	var isOffline = isDeviceOffline || isGateWayOfline;
	var offLineIdleImage = "<img src='/imonitor/resources/images/idleswitch.png' class='rightsideimage idleswitch' title='inactive'></img>";
	//vibhu deprecated var armImage = "<img src='/imonitor/resources/images/armbutton.png' class='armclass' title='click to change'>";
	//sumit start: Re-Ordering Of Devices
	var posIdx = $.trim(device["positionIndex"]);
	//alert("posIdx: "+posIdx);
	//var deviceLi = $("<li></li>").addClass('widget').addClass('color-white').attr("tabindex", device["index"]);
	//var deviceLi = $("<li></li>").addClass('widget').addClass('color-white').attr("tabindex", device["index"]).attr("id", "idx_"+device["index"]);
	var deviceLi = $("<li></li>").addClass('widget').addClass('color-white').attr("tabindex", device["index"]).attr("id", posIdx+"-"+device["index"]);
	//sumit end: Re-Ordering Of Devices
	if(deviceType == "Z_WAVE_AC_EXTENDER")
	{
	var isOff = $.trim(device["commandparam"]) == 0;
	if((!isOff) && (!isOffline))
	{
	var contentDiv = $("<div></div>").addClass('widget-content-acex');
	}
	else
	{
	var contentDiv = $("<div></div>").addClass('widget-content');
	}
	}
	else
	{
	var contentDiv = $("<div></div>").addClass('widget-content');
	}
	contentDiv.append($("<img />").attr('src', device["iconfile"]).addClass('deviceicon').attr('title','Active'));
	if(isOffline)
	{
		contentDiv.append($("<img />").attr('src', '/imonitor/resources/images/deviceidle.png').attr('title','Inactive').addClass('deviceicon').addClass('idleswitch'));
	}
	var detailSection = $("<div class='detailsection'></div>");
	detailSection.append(device["deviceName"]);
	var isOff = $.trim(device["commandparam"]) == 0;
	if((device["HMDpowerinfo"] == "POWER_INFORMATION") && (!isOff))
	{
		detailSection.append("</br><span type='text' readonly='readonly'>POWER CONSUMPTION :  "+Math.round(device["HMDalertValue"].substring(0,(device["HMDalertValue"].length)))+" W </span>");
	}
	contentDiv.append(detailSection);
	
	var istimeoutanabled=$.trim(device["enableStatus"])==1;
	var armStatus = $.trim(device["deviceArmStatus"]);
	var ruleCount = 1 * $.trim(device["rulescount"]);
	var isArm = armStatus == 'ARM' ? true : false;
	var switchType = $.trim(device["switchType"]);
		var deviceType = $.trim(device["deviceType"]);
		if(deviceType == "Z_WAVE_SWITCH" || deviceType == "Z_WAVE_SIREN" || deviceType == "Z_WAVE_HEALTH_MONITOR")
		{
			var imgContent = "";
			var imgContent1 = "";
			var isOff = $.trim(device["commandparam"]) == 0;
			var Timeout=TimeOutValues[device["deviceId"]];
			
			if(isOffline){
				imgContent += offLineIdleImage;
			} 
			else if(isOff)
			{
				
				
				if(Timeout == undefined)
				{
					Timeout="0";
					if(istimeoutanabled)
					{
			            imgContent1= "<div style=' float: right; margin: 20px -164px 0px 14px;'>Desired Time out:<input class='timeoutvalueclass' minvalue='0' maxvalue='60'  deviceId="+device["deviceId"]+" type='text' id='timeout' maxlength='2' size='1' name='timeout' placeholder='sec'></div>";
					}
				}
				else
				{
					if(istimeoutanabled)
					{
				       imgContent1= "<div style=' float: right; margin: 20px -164px 0px 14px;'>Desired Time out:<input class='timeoutvalueclass' minvalue='0' maxvalue='60' value="+Timeout+" deviceId="+device["deviceId"]+" type='text' id='timeout' maxlength='2' size='1' name='timeout' placeholder='sec'></div>";
					}
				}
				
				
			    imgContent += "<img src='/imonitor/resources/images/offnormalswitch.png' class='switchon rightsideimage' title='click to switch on'></img>";
			}else{
				
			    imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff rightsideimage' title='click to switch off'></img>";
			}
			detailSection.append(imgContent1);
			contentDiv.append(detailSection);
			if(Timeout == undefined)
				{
					Timeout="0";
				}
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'switchonff.action')
					.addClass(isOffline ? '' : 'updatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("Devicetimeout", Timeout)
					.attr("switchType", switchType)
					.attr("commandParam", isOff ? 1 : 0)
					.html(imgContent)

					);
		}
		if(deviceType == "Z_WAVE_LUX_SENSOR"){
			try{
				var controlContent = "";
				var controlContent2 = "";
				if(isOffline){
					controlContent = offLineIdleImage;
					contentDiv.append(controlContent);
				}else{
	
					var abc=device["commandparam"];
					
					controlContent = "";
					contentDiv.append(controlContent);

					contentDiv.append($("<div class='Lux'>"+"Current Value: "+abc+" lux</div>"));
					}	
				
			}catch(err){
			}
		}if(deviceType == "Z_WAVE_PM_SENSOR"){
			try{
				var controlContent = "";
				var controlContent2 = "";
				if(isOffline){
					controlContent = offLineIdleImage;
					contentDiv.append(controlContent);
				}else{
	
					var abc=device["commandparam"];
					
					controlContent = "";
					contentDiv.append(controlContent);

					contentDiv.append($("<div class='Lux'>"+"<b>PM Level<b>:<b> "+abc+"<b><b> &#956g/m<sup>3<sup><b></div>"));
					}	
				
			}catch(err){
			}
		}if(deviceType=="ENCODER"){
			try{
				var controlContent = "";
				var controlContent2 = "";
				if(isOffline){
					controlContent = offLineIdleImage;
					contentDiv.append(controlContent);
				}else{
	
					var abc=device["commandparam"];
					controlContent = "";
					contentDiv.append(controlContent);
					contentDiv.append($("<div class='Lux'></div>"));
				}	
				
			}catch(err){
			}

		}
		if(deviceType == "Z_WAVE_DIMMER"){
			try{
				var controlContent = "";
				var controlContent2 = "";
				if(isOffline){
					controlContent = offLineIdleImage;
					contentDiv.append(controlContent);
				}else{

				var Timeout=TimeOutValues[device["deviceId"]];
				if(Timeout == undefined)
				{
					Timeout="sec";
					if(istimeoutanabled)
					{
			         imgContent1= "<div style='margin: 10px 0px 0px 0px;'>Desired Time out:<input class='Dimmertimeoutvalueclass' minvalue='0' maxvalue='60'  deviceId="+device["deviceId"]+" type='text' id='timeout' maxlength='2' size='1' name='timeout' placeholder='sec'></div>";
					}
				}
				else
				{
					if(istimeoutanabled)
					{
				       imgContent1= "<div style='margin: 10px 0px 0px 0px;'>Desired Time out:<input class='Dimmertimeoutvalueclass' minvalue='0' maxvalue='60' value="+Timeout+" deviceId="+device["deviceId"]+" type='text' id='timeout' maxlength='2' size='1' name='timeout' placeholder='sec'></div>";
					}
				}
				
				
				if(Timeout == undefined)
				{
					Timeout="0";
				}
					//imgContent1= "<div style='margin: 10px 0px 0px 0px;'>Desired Time out:<input class='Dimmertimeoutvalueclass' minvalue='0' maxvalue='60' type='text' id='timeout' maxlength='2' size='1' name='timeout' placeholder='sec'></div>";
					detailSection.append(imgContent1);
					contentDiv.append(detailSection);
					var abc=device["commandparam"];
					controlContent = "";
					contentDiv.append(controlContent);

					contentDiv.append($("<div class='slider' id='slider'><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"><div class='closeDim'>"+formatMessage("msg.ui.min")+"</div><div class='openDim'>"+formatMessage("msg.ui.max")+"</div></div>").slider({
					range: "min",
					min: 0,
					max: 99,
					value: abc,
					
					slide: function(event, ui) 
					{
						var $this_text = jQuery(this).find("input");
						$this_text.val(ui.value);
					},
			   		stop: function( event,ui )
					{
			   			var TimeOut = TimeOutValues[device["deviceId"]];
					var result=senddimmervalue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"],TimeOut);
					}
					}));
					
					}
			
				
			}catch(err){
			}
		}
		
		//RGB
		if(deviceType == "DEV_ZWAVE_RGB"){
			try{
				var controlContent = "";
				var controlContent2 = "";
				if(isOffline){
					controlContent = offLineIdleImage;
					contentDiv.append(controlContent);
				}else{

				var Timeout=TimeOutValues[device["deviceId"]];
				if(Timeout == undefined)
				{
					Timeout="sec";
					if(istimeoutanabled)
					{
			         imgContent1= "<div style='margin: 10px 0px 0px 0px;'>Desired Time out:<input class='Dimmertimeoutvalueclass' minvalue='0' maxvalue='60'  deviceId="+device["deviceId"]+" type='text' id='timeout' maxlength='2' size='1' name='timeout' placeholder='sec'></div>";
					}
				}
				else
				{
					if(istimeoutanabled)
					{
				       imgContent1= "<div style='margin: 10px 0px 0px 0px;'>Desired Time out:<input class='Dimmertimeoutvalueclass' minvalue='0' maxvalue='60' value="+Timeout+" deviceId="+device["deviceId"]+" type='text' id='timeout' maxlength='2' size='1' name='timeout' placeholder='sec'></div>";
					}
				}
				
				
				if(Timeout == undefined)
				{
					Timeout="0";
				}
					//imgContent1= "<div style='margin: 10px 0px 0px 0px;'>Desired Time out:<input class='Dimmertimeoutvalueclass' minvalue='0' maxvalue='60' type='text' id='timeout' maxlength='2' size='1' name='timeout' placeholder='sec'></div>";
					detailSection.append(imgContent1);
					contentDiv.append(detailSection);
					var abc=device["commandparam"];
					controlContent = "";
					contentDiv.append(controlContent);

					contentDiv.append($("<div class='slider' id='slider'><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"><div class='closeDim'>"+formatMessage("msg.ui.min")+"</div><div class='openDim'>"+formatMessage("msg.ui.max")+"</div></div>").slider({
					range: "min",
					min: 0,
					max: 99,
					value: abc,
					
					slide: function(event, ui) 
					{
						var $this_text = jQuery(this).find("input");
						$this_text.val(ui.value);
					},
			   		stop: function( event,ui )
					{
			   			var TimeOut = TimeOutValues[device["deviceId"]];
					var result=senddimmervalue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"],TimeOut);
					}
					}));
					
					}
				
				//ColorPicker Start
				//alert("Switch Type : " + device["switchType"]);
				var colorpicker = "";
				//device["switchType"] = 8;
				
					colorpicker ="<input type='button' value='Enter ColorCode' class='rgb colorrgb' title='Click to add Value' style='text-decoration: none;' ></input>";
					contentDiv.append($("<a></a>")
							.attr('href',isOffline ? '#' :'sendRGBValue.action')
							.addClass(isOffline ? '' : 'rgbColorPicker')
							.attr("gateWayId", device["gatewayid"])
							.attr("deviceId", device["deviceId"])
							.html(colorpicker)
							);
				

				
				//ColorPicker End
				
				
			}catch(err){
			}
		}
		
		
		if(deviceType == "IP_CAMERA")
			{	
			var controlContent = "";
			var ismodelno = device["modelnumber"] ;
			var modelno = device["deviceName"];
			var enableStatus = device["enableStatus"];
			var deviceid=	device["deviceId"];
			var EnableContent = "";
			var camerpt= device["pantiltControl"];
			
			if(isOffline)
			{
				controlContent = offLineIdleImage;
			}
			else
			{
			var Streamvalue = $("#deviceListUl").data("Stream");
			if(Streamvalue==deviceid)
			{
					$("#deviceListUl").data("Stream",device["deviceId"]);
					controlContent = $("<div id='Camera' style='background-color:red' ></div>").append($("<a href='#'></a>").append($("<img/>")
						.attr('src','../resources/images/stopbutton.png'))
						.addClass('rightsideimage')
						.addClass('changecamera')
						.addClass('nowselectedcamera')
						.addClass('streamingstarted')
						.attr('deviceid',device["deviceId"])
						.attr("gateWayId", device["gatewayid"])
						.attr('devicename',device["deviceName"])
						.attr('presetvalue',device["presetvalue"])
						.attr('title','click to stop')
						.attr('pantiltControl',device["pantiltControl"])
						.attr('controlNightVision',device["controlNightVision"]));

			}
			else
			{
				var Streamvalue = $("#deviceListUl").data("Stream");
				var DeviceId = $("#deviceListUl").data("DeviceID");
				if(Streamvalue == deviceid)
				{
					$("#deviceListUl").data("Stream",0);
				}
				if(DeviceId == deviceid)
				{
					controlContent = $("<div id='Camera'></div>").append($("<a href='#'></a>").append($("<img/>")
						.attr('src','/imonitor/resources/images/device/playbutton.png'))
						.addClass('rightsideimage')
						.addClass('changecamera')
						.addClass('nowselectedcamera')
						.attr('deviceid',device["deviceId"])
						.attr("gateWayId", device["gatewayid"])
						.attr('devicename',device["deviceName"])
						.attr('presetvalue',device["presetvalue"])
						.attr('title','click to play camera')
						.attr('pantiltControl',device["pantiltControl"])
						.attr('controlNightVision',device["controlNightVision"]));
				}
				else
				{		
						controlContent = $("<div id='Camera'></div>").append($("<a href='#'></a>").append($("<img/>")
						.attr('src','/imonitor/resources/images/device/playbutton.png'))
						.addClass('rightsideimage')
						.addClass('changecamera')
						.attr('deviceid',device["deviceId"])
						.attr("gateWayId", device["gatewayid"])
						.attr('devicename',device["deviceName"])
						.attr('presetvalue',device["presetvalue"])
						.attr('title','click to play camera')
						.attr('pantiltControl',device["pantiltControl"])
						.attr('controlNightVision',device["controlNightVision"]));
				
				}}}
			contentDiv.append(controlContent);
		}
		if(deviceType == "Z_WAVE_PIR_SENSOR" || deviceType == "Z_WAVE_MULTI_SENSOR"){
			var batteryImg = findProperImageForBatteryLevel(device["batterylevel"]);
			var imgContent = "<img src='/imonitor/resources/images/refreshnormal.png' class='switchon rightsideimage' title='click to switch on'></img>";
			var controlContent = "";
			if(isOffline){
				controlContent = offLineIdleImage;
			}else{
				//sumit start: hiding Get Temperature for PIR SENSOR from home page
				controlContent = $("<a href='retrieveTemperature.action'></a>")
				.addClass('updatedevicelink')
				.attr("gateWayId", device["gatewayid"])
				.attr("deviceId", device["deviceId"])
				.attr("commandParam", device["commandparam"]);
				//.html(imgContent);
			}
			contentDiv.append(controlContent);

			contentDiv.append($("<img src='" + batteryImg + "' class='batteryimage'/>").attr('title', device["batterylevel"]));
			//detailSection.append("<br/>Temperature Status : " + device["commandparam"]);
			//sumit end
		}
		
		if(deviceType == "Z_WAVE_MOTOR_CONTROLLER")

		{
			var controlContent="";
			var imgOpenContent="";
			var imgCloseContent="";
			var make=device["make"];
			var fullOpenClose ="";
			
			var isOff = $.trim(device["commandparam"]) == 0;
			if((gatewayVersion.includes("IMSZING")) || (gatewayVersion.includes("IMSPRIME") !=0) || (gatewayVersion.indexOf("05.02.10") != -1)){
				
				if(isOffline)
				{
					controlContent = offLineIdleImage;
					contentDiv.append(controlContent);
				}
				else
				{
				
				controlContent += "<div>";
				controlContent += "";
				controlContent += "</div>";
				
				imgOpenContent += "<img src='/imonitor/resources/images/curtain_open_btn.png' class='rightsideimage1' title='click to switch off'></img>";
				imgCloseContent += "<img src='/imonitor/resources/images/curtain_close_btn.png' class='rightsideimage2' title='click to switch off'></img>";
				contentDiv.append($("<a></a>")
						//.attr('href',isOffline ? '#' :'switchonff.action')
						.addClass(isOffline ? '' : 'updateOpenControl')
						.attr("gateWayId", device["gatewayid"])
						.attr("deviceId", device["deviceId"])
						.attr("commandParam", isOff ? 1 : 0)
						.html(imgOpenContent)

						);
				
				contentDiv.append($("<a></a>")
						//.attr('href',isOffline ? '#' :'switchonff.action')
						.addClass(isOffline ? '' : 'updateCloseControl')
						.attr("gateWayId", device["gatewayid"])
						.attr("deviceId", device["deviceId"])
						.attr("commandParam", isOff ? 1 : 0)
						.html(imgCloseContent)

						);
				contentDiv.append($("<a></a>"));
				if(isOff){
					fullOpenClose +="<input type='button' value='Full Open' class='bbtnMode' title='click to full open curtain' style='text-decoration: none;float: right;margin-right: 33px;margin-top: 23px;' ></input>";
				}else{
					
					fullOpenClose +="<input type='button' value='Full Close' class='bbtnMode' title='click to full close curtain' style='text-decoration: none;float: right;margin-right: 33px;margin-top: 23px;' ></input>";
				}
				contentDiv.append($("<a></a>")
						//.attr('href',isOffline ? '#' :'controlac.action')
						.addClass(isOffline ? '' : 'updateMotorControl')
						.attr("gateWayId", device["gatewayid"])
						.attr("deviceId", device["deviceId"])
						.attr("commandParam", device["commandparam"])
						.html(fullOpenClose)
						
						);
				//contentDiv.append($("<div class='close'>"+formatMessage("msg.ui.close")+"</div>"));	
				//contentDiv.append($("<div class='open'>"+formatMessage("msg.ui.open")+"</div>"));
				
				}
				
			}else{

				//Curtain controller changes for gateways NA900 start
				var split = gatewayVersion.split('_');
				var versionn =  split[1];
				if (versionn >= '05.02.10')
				{
					if(isOffline)
					{
						controlContent = offLineIdleImage;
						contentDiv.append(controlContent);
					}
					else
					{
					
					controlContent += "<div>";
					controlContent += "";
					controlContent += "</div>";
					
					imgOpenContent += "<img src='/imonitor/resources/images/curtain_open_btn.png' class='rightsideimage1' title='click to switch off'></img>";
					imgCloseContent += "<img src='/imonitor/resources/images/curtain_close_btn.png' class='rightsideimage2' title='click to switch off'></img>";
					contentDiv.append($("<a></a>")
							//.attr('href',isOffline ? '#' :'switchonff.action')
							.addClass(isOffline ? '' : 'updateOpenControl')
							.attr("gateWayId", device["gatewayid"])
							.attr("deviceId", device["deviceId"])
							.attr("commandParam", isOff ? 1 : 0)
							.html(imgOpenContent)

							);
					
					contentDiv.append($("<a></a>")
							//.attr('href',isOffline ? '#' :'switchonff.action')
							.addClass(isOffline ? '' : 'updateCloseControl')
							.attr("gateWayId", device["gatewayid"])
							.attr("deviceId", device["deviceId"])
							.attr("commandParam", isOff ? 1 : 0)
							.html(imgCloseContent)

							);
					contentDiv.append($("<a></a>"));
					if(isOff){
						fullOpenClose +="<input type='button' value='Full Open' class='bbtnMode' title='click to full open curtain' style='text-decoration: none;float: right;margin-right: 33px;margin-top: 23px;' ></input>";
					}else{
						
						fullOpenClose +="<input type='button' value='Full Close' class='bbtnMode' title='click to full close curtain' style='text-decoration: none;float: right;margin-right: 33px;margin-top: 23px;' ></input>";
					}
					contentDiv.append($("<a></a>")
							//.attr('href',isOffline ? '#' :'controlac.action')
							.addClass(isOffline ? '' : 'updateMotorControl')
							.attr("gateWayId", device["gatewayid"])
							.attr("deviceId", device["deviceId"])
							.attr("commandParam", device["commandparam"])
							.html(fullOpenClose)
							
							);
					//contentDiv.append($("<div class='close'>"+formatMessage("msg.ui.close")+"</div>"));	
					//contentDiv.append($("<div class='open'>"+formatMessage("msg.ui.open")+"</div>"));
					
					}
				}
				else 
				{
					if(isOffline)
					{
						controlContent = offLineIdleImage;
						contentDiv.append(controlContent);
					}


					else
					{
					var abc=device["commandparam"];
					controlContent += "<div>";
					controlContent += "";
					controlContent += "</div>";
					contentDiv.append(controlContent);

					contentDiv.append($("<div class='slider' id='slider'><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
						range: "min",
						min: 0,
						max: 100,
						step: 25,
						value: abc,
						toolTip: true,
						slide: function(event, ui) 
							{
								var $this_text = jQuery(this).find("input");
								$this_text.val(ui.value);
		                    			          			
							},

					   	stop: function( event,ui )
						{
					   		if(device["commandparam"]!= ui.value)
							{
							updatedevicelink(ui.value,device["gatewayid"],device["deviceId"],make);
							}	
						}
						
						
		        		
						}));
					
					contentDiv.append($("<div class='close'>"+formatMessage("msg.ui.close")+"</div>"));	
					contentDiv.append($("<div class='open'>"+formatMessage("msg.ui.open")+"</div>"));
					
					}
				}
				//Curtain controller changes for gateways NA900 end
				
			}
			
			
			
			}

		//sumit start: SMOKE SENSOR
		if(deviceType == "Z_WAVE_SMOKE_SENSOR"){
			var batteryImg = findProperImageForBatteryLevel(device["batterylevel"]);
			contentDiv.append($("<img src='" + batteryImg + "' class='batteryimage'/>").attr('title', device["batterylevel"]));
		}
		//sumit end: SMOKE SENSOR
		//sumit start: SHOCK_DETECTOR
		if(deviceType == "Z_WAVE_SHOCK_DETECTOR"){
			var batteryImg = findProperImageForBatteryLevel(device["batterylevel"]);
			contentDiv.append($("<img src='" + batteryImg + "' class='batteryimage'/>").attr('title', device["batterylevel"]));
		}
		//sumit end: SHOCK_DETECTOR
		if(deviceType == "Z_WAVE_DOOR_SENSOR" ){
			var batteryImg = findProperImageForBatteryLevel(device["batterylevel"]);
			contentDiv.append($("<img src='" + batteryImg + "' class='batteryimage'/>").attr('title', device["batterylevel"]));
			var isOff=device["commandparam"];
			var imgContent="";
				
				if(isOffline)
				{
				
				}
				else
				{
				if(isOff!=1)
				{
				imgContent += "<img src='/imonitor/resources/images/doorclose.png' class='rightsideimage'></img>";
				}
				else
				{
				imgContent += "<img src='/imonitor/resources/images/dooropen.png' class='rightsideimage'></img>";
				}	
			}
			contentDiv.append(imgContent);

		}
		if(deviceType == "Z_WAVE_DOOR_LOCK")
		{
			
			var imgContent = "";
			var isOff = $.trim(device["commandparam"]) == 0;
			if(isOffline){
				imgContent += offLineIdleImage;
			} else if(isOff){
				imgContent += "<img src='/imonitor/resources/images/doorunlock.png' class='rightsideimage' title='click to lock'></img>";
			}else{
				imgContent += "<img src='/imonitor/resources/images/doorlock.png' class='rightsideimage' title='click to unlock'></img>";
			}
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'lockUnlock.action')
					.addClass(isOffline ? '' : 'updatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", isOff ? 1 : 0)
					.attr("switchType", device["switchType"])
					.html(imgContent)
					);

			}
		/**Added by Apoorva & Naveen
		 * For displaying Mod bus Via UNIT
		 * 19th June 2018
		 */
		if(deviceType == "VIA_UNIT")
			{
			var DryModeContent ="";
			var AutoModeContent ="";
			var CoolModeContent ="";
			var HeatModeContent ="";
			var FanModeContent ="";
			var onContent = "";
			var FanDirectionText = "";
			var FanVolumeText = "";
			var fanmode=device["fanModeValue"];
			var fanVolumeValue=device["fanVolumeValue"];
			var fanDirectionValue=device["fanDirectionValue"];
			var filterSignStatus= device["filterSignStatus"] ;  
			var resetFilterStatus="";
			var autoModeCapability=device["autoModeCapability"];
			var dryModeCapability=device["dryModeCapability"];
			var heatModeCapability=device["heatModeCapability"];
			var coolModeCapability=device["coolModeCapability"];
			var fanModeCapability = device["fanModeCapability"];
			var fanDirectionCapability=device["fanDirectionCapability"];
			var fanVolumeCapability=device["fanVolumeCapability"];
			var fanVolumeLevelCapability=device["fanVolumeLevelCapability"];
			//alert("Error message :"+device["errorMessage"]);
			
			if(fanModeCapability != ""){
				
			var isOff = $.trim(device["commandparam"]) == 0;
			
			var sensedTemp=device["sensedTemp"];
			
				if (sensedTemp == null || sensedTemp == "" || sensedTemp == 0){
					sensedTemp = "NA";
			}
			
			var commandparam= device["commandparam"];
			if(isOffline){
				onContent += offLineIdleImage;
			}else if(isOff){
				
				 onContent +="<input type='button' value='OFF' class='viaControlButton' title='Click to switch On' style='text-decoration: none;' ></input>";
				 contentDiv.append($("<a></a>")
							.attr('href',isOffline ? '#' :'controlIndoorUnit.action')
							.addClass(isOffline ? '' : 'acupdatedevicelink')
							.attr("gateWayId", device["gatewayid"])
							.attr("deviceId", device["deviceId"])
							.attr("commandParam", isOff ? 5 : 0)
							.html(onContent)
							
							);
			
			}else{
				
				 onContent +="<input type='button' value='ON' class='viaControlButton' title='Click to switch off' style='text-decoration: none;' ></input>";
				//contentDiv.append($(onContent));
				 contentDiv.append($("<a></a>")
							.attr('href',isOffline ? '#' :'controlIndoorUnit.action')
							.addClass(isOffline ? '' : 'acupdatedevicelink')
							.attr("gateWayId", device["gatewayid"])
							.attr("deviceId", device["deviceId"])
							.attr("commandParam", isOff ? 5 : 0)
							.html(onContent)
							
							);
				
				
				if (dryModeCapability==1) 
				{
					if(fanmode==8)
					 {
						 DryModeContent ="<input type='button' value='Dry' class='highlightViabbtnMode ViaDryModebutton' title='Click Dry mode' style='text-decoration: none;' ></input>";
					 }
					 else
						 {
						 DryModeContent ="<input type='button' value='Dry' class='ViabbtnMode ViaDryModebutton' title='Click Dry mode' style='text-decoration: none;' ></input>";
						 }
					 
					
					contentDiv.append($("<a></a>")
								.attr('href',isOffline ? '#' :'controlIndoorUnit.action')
								.addClass(isOffline ? '' : 'acupdatedevicelinkforlearn')
								.attr("gateWayId", device["gatewayid"])
								.attr("deviceId", device["deviceId"])
								.attr("commandParam", 8)
								.html(DryModeContent )
								);
				} 
				 
				 	
				
				if (autoModeCapability==1) 
				{
					if(fanmode==10)
					{
					AutoModeContent +="<input type='button' value='Auto' class='highlightViabbtnMode ViaAutoModebutton' title='Click Auto mode' style='text-decoration: none;' ></input>";
					}
				else
					{
					AutoModeContent +="<input type='button' value='Auto' class='ViabbtnMode ViaAutoModebutton' title='Click Auto mode' style='text-decoration: none;' ></input>";
					}
				contentDiv.append($("<a></a>")
						.attr('href',isOffline ? '#' :'controlIndoorUnit.action')
						.addClass(isOffline ? '' : 'acupdatedevicelink')
						.attr("gateWayId", device["gatewayid"])
						.attr("deviceId", device["deviceId"])
						.attr("deviceId", device["deviceId"])
						.attr("commandParam", 10)
						.html(AutoModeContent )
						
						);
				}
				
				if (coolModeCapability==1)
				{
					if(fanmode==2 || fanmode==5)
					{
						CoolModeContent +="<input type='button' value='Cool' class='highlightViabbtnMode ViaCoolModebutton' title='Click Cool mode' style='text-decoration: none;' ></input>";
					}
					else
					{
						CoolModeContent +="<input type='button' value='Cool' class='ViabbtnMode ViaCoolModebutton' title='Click Cool mode' style='text-decoration: none;' ></input>";
					}
					
					contentDiv.append($("<a></a>")
							.attr('href',isOffline ? '#' :'controlIndoorUnit.action')
							.addClass(isOffline ? '' : 'acupdatedevicelinkforlearn')
							.attr("gateWayId", device["gatewayid"])
							.attr("deviceId", device["deviceId"])
							.attr("commandParam", 2)
							.html(CoolModeContent )
							);
				}
				
				
					
				if (heatModeCapability==1) 
				{
					if(fanmode==1)
					{
					HeatModeContent +="<input type='button' value='Heat' class='highlightViabbtnMode ViaHeatModebutton ' title='Click Heat mode' style='text-decoration: none;' ></input>";
					}
				else
					{
					HeatModeContent +="<input type='button' value='Heat' class='ViabbtnMode ViaHeatModebutton ' title='Click Heat mode' style='text-decoration: none;' ></input>";
					}
				contentDiv.append($("<a></a>")
						.attr('href',isOffline ? '#' :'controlIndoorUnit.action')
						.addClass(isOffline ? '' : 'acupdatedevicelinkforlearn')
						.attr("gateWayId", device["gatewayid"])
						.attr("deviceId", device["deviceId"])
						.attr("commandParam", 1)
						.html(HeatModeContent )
						);	
				}
				
				
				if (fanModeCapability==1)
				{
					if(fanmode==6)
					{
					FanModeContent +="<input type='button' value='Fan' class='highlightViabbtnMode ViaFanModebutton' title='Click Fan mode' style='text-decoration: none;' ></input>";
					}
				else
					{
					FanModeContent +="<input type='button' value='Fan' class='ViabbtnMode ViaFanModebutton' title='Click Fan mode' style='text-decoration: none;' ></input>";
					}
				
				contentDiv.append($("<a></a>")
						.attr('href',isOffline ? '#' :'controlIndoorUnit.action')
						.addClass(isOffline ? '' : 'acupdatedevicelinkforlearn')
						.attr("gateWayId", device["gatewayid"])
						.attr("deviceId", device["deviceId"])
						.attr("commandParam", 6)
						.html(FanModeContent )
						);
				}
				
				
				
			/***************************************	Fan Direction block start****************************/
				
				if (fanDirectionCapability==1 && fanModeCapability==1) 
				{
						//**************if fanDirectionValue is not null start
				if (fanDirectionValue!="") 
				{
					
				if(fanDirectionValue==0){
					FanDirectionText +="<div class='fanDirectiontext'><div style='margin-left: 39px;color: black;'>Fan direction</div>"+
					"<input type='button' value='P0' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='2' class='ViabbtnMode pZero updateFanDirection'  title='Click Fan mode' style='text-decoration: none;'></input>"+
					"<input type='button' value='P1' class='ViabbtnMode pone updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='3'  style='margin-left: 28px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P2' class='ViabbtnMode ptwo updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='4' style='margin-left: 26px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P3' class='ViabbtnMode pthree updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='5' style='margin-top: 10px;margin-left: 7px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P4' class='ViabbtnMode pfour updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='6' style='margin-left: 23px;' title='Click Fan mode' style='text-decoration: none;'></input>" + 
						"<input type='button' value='Swing' class='ViabbtnMode pSwing updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='1'  style='margin-top:12px;margin-left: 48px;' title='Click to Switch On Swing Mode' style='text-decoration: none;'></input></div>";

				}
				if (fanDirectionValue==1) 
				{
					FanDirectionText +="<div class='fanDirectiontext'><div style='margin-left: 39px;color: black;'>Fan direction</div><input type='button' value='P0' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='2' class='ViabbtnMode pZero updateFanDirection'  title='Click Fan mode' style='text-decoration: none;'></input>"+
			        "<input type='button' value='P1' class='ViabbtnMode pone updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='3'  style='margin-left: 28px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P2' class='ViabbtnMode ptwo updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='4' style='margin-left: 26px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P3' class='ViabbtnMode pthree updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='5' style='margin-top: 10px;margin-left: 7px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P4' class='ViabbtnMode pfour updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='6' style='margin-left: 23px;' title='Click Fan mode' style='text-decoration: none;'></input>" +
			         "<input type='button' value='Swing' class='highlightViabbtnMode pSwing updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='0'  style='margin-top:12px;margin-left: 48px;' title='Click to Switch Off Swing Mode' style='text-decoration: none;'></input></div>";
				}
				
				if (fanDirectionValue==2) 
				{
					FanDirectionText +="<div class='fanDirectiontext'><div style='margin-left: 39px;color: black;'>Fan direction</div><input type='button' value='P0' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='2' class='highlightViabbtnMode pZero updateFanDirection'  title='Click Fan mode' style='text-decoration: none;'></input>"+
			        "<input type='button' value='P1' class='ViabbtnMode pone updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='3'  style='margin-left: 28px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P2' class='ViabbtnMode ptwo updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='4' style='margin-left: 26px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P3' class='ViabbtnMode pthree updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='5' style='margin-top: 10px;margin-left: 7px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P4' class='ViabbtnMode pfour updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='6' style='margin-left: 23px;' title='Click Fan mode' style='text-decoration: none;'></input>" +
			         "<input type='button' value='Swing' class='ViabbtnMode pSwing updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='1'  style='margin-top:12px;margin-left: 48px;' title='Click to toggle Swing' style='text-decoration: none;'></input></div>";
				}
				else if (fanDirectionValue==3)
				{
					FanDirectionText +="<div class='fanDirectiontext'><div style='margin-left: 39px;color: black;'>Fan direction</div><input type='button' value='P0' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='2' class='ViabbtnMode pZero updateFanDirection'  title='Click Fan mode' style='text-decoration: none;'></input>"+
			        "<input type='button' value='P1' class='highlightViabbtnMode pone updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='3'  style='margin-left: 28px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P2' class='ViabbtnMode ptwo updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='4' style='margin-left: 26px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P3' class='ViabbtnMode pthree updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='5' style='margin-top: 10px;margin-left: 7px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P4' class='ViabbtnMode pfour updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='6' style='margin-left: 23px;' title='Click Fan mode' style='text-decoration: none;'></input>" +
			         "<input type='button' value='Swing' class='ViabbtnMode pSwing updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='1'  style='margin-top:12px;margin-left: 48px;' title='Click to toggle Swing' style='text-decoration: none;'></input></div>";
				}
				else if (fanDirectionValue==4) 
				{
					FanDirectionText +="<div class='fanDirectiontext'><div style='margin-left: 39px;color: black;'>Fan direction</div><input type='button' value='P0' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='2' class='ViabbtnMode pZero updateFanDirection'  title='Click Fan mode' style='text-decoration: none;'></input>"+
			        "<input type='button' value='P1' class='ViabbtnMode pone updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='3'  style='margin-left: 28px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P2' class='highlightViabbtnMode ptwo updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='4' style='margin-left: 26px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P3' class='ViabbtnMode pthree updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='5' style='margin-top: 10px;margin-left: 7px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P4' class='ViabbtnMode pfour updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='6' style='margin-left: 23px;' title='Click Fan mode' style='text-decoration: none;'></input>" +
			         "<input type='button' value='Swing' class='ViabbtnMode pSwing updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='1'  style='margin-top:12px;margin-left: 48px;' title='Click to toggle Swing' style='text-decoration: none;'></input></div>";
				}
				else if (fanDirectionValue==5)
				{
					FanDirectionText +="<div class='fanDirectiontext'><div style='margin-left: 39px;color: black;'>Fan direction</div><input type='button' value='P0' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='2' class='ViabbtnMode pZero updateFanDirection'  title='Click Fan mode' style='text-decoration: none;'></input>"+
			        "<input type='button' value='P1' class='ViabbtnMode pone updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='3'  style='margin-left: 28px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P2' class='ViabbtnMode ptwo updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='4' style='margin-left: 26px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P3' class='highlightViabbtnMode pthree updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='5' style='margin-top: 10px;margin-left: 7px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P4' class='ViabbtnMode pfour updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='6' style='margin-left: 23px;' title='Click Fan mode' style='text-decoration: none;'></input>" +
			         "<input type='button' value='Swing' class='ViabbtnMode pSwing updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='1'  style='margin-top:12px;margin-left: 48px;' title='Click to toggle Swing' style='text-decoration: none;'></input></div>";
				}
				else if(fanDirectionValue==6)
				{
					FanDirectionText +="<div class='fanDirectiontext'><div style='margin-left: 39px;color: black;'>Fan direction</div><input type='button' value='P0' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='2' class='ViabbtnMode pZero updateFanDirection'  title='Click Fan mode' style='text-decoration: none;'></input>"+
			        "<input type='button' value='P1' class='ViabbtnMode pone updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='3'  style='margin-left: 28px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P2' class='ViabbtnMode ptwo updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='4' style='margin-left: 26px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P3' class='ViabbtnMode pthree updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='5' style='margin-top: 10px;margin-left: 7px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P4' class='highlightViabbtnMode pfour updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='6' style='margin-left: 23px;' title='Click Fan mode' style='text-decoration: none;'></input>" +
			         "<input type='button' value='Swing' class='ViabbtnMode pSwing updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='1'  style='margin-top:12px;margin-left: 48px;' title='Click to toggle Swing' style='text-decoration: none;'></input></div>";
				}
				
				}
				//**************if fanDirectionValue is not null end
				else
					//**************if fanDirectionValue is null start
					{
					FanDirectionText +="<div class='fanDirectiontext'><div style='margin-left: 39px;color: black;'>Fan direction</div>"+
					"<input type='button' value='P0' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='2' class='ViabbtnMode pZero updateFanDirection'  title='Click Fan mode' style='text-decoration: none;'></input>"+
					"<input type='button' value='P1' class='ViabbtnMode pone updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='3'  style='margin-left: 28px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P2' class='ViabbtnMode ptwo updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='4' style='margin-left: 26px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P3' class='ViabbtnMode pthree updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='5' style='margin-top: 10px;margin-left: 7px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
			         "<input type='button' value='P4' class='ViabbtnMode pfour updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='6' style='margin-left: 23px;' title='Click Fan mode' style='text-decoration: none;'></input>" + 
						"<input type='button' value='Swing' class='ViabbtnMode pSwing updateFanDirection' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='1'  style='margin-top:12px;margin-left: 48px;' title='Click to Switch On Swing Mode' style='text-decoration: none;'></input></div>";
					}
				//**************if fanDirectionValue is null end
				}
				
				
				/***************************************	Fan Direction block end****************************/
				
				/***************************************	Fan VOLUME block start****************************/
				if (fanModeCapability==1 && fanVolumeCapability==1)
				{
					if (fanVolumeValue!="") 
					{
						//*****************if fanVolumeValue is not null start**************************
					if (fanVolumeLevelCapability==2) 
					{
						// 2 STEP CAPABILITY
						if (fanVolumeValue==1) 
						{
							FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div>"+
					        "<input type='button' value='L' class='highlightViabbtnMode pone updateFanVolume' id='inputLow'  modeval='1' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='H' class='ViabbtnMode ptwo updateFanVolume' modeval='5' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>";
					         
						}
						else if (fanVolumeValue==5) 
						{
							FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div>"+
					        "<input type='button' value='L' class='ViabbtnMode pone updateFanVolume' id='inputLow'  modeval='1' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='H' class='highlightViabbtnMode ptwo updateFanVolume' modeval='5' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>";
					        
						}
						
					}
					else if (fanVolumeLevelCapability==3)
					{
						// 3 STEP CAPABILITY
						if (fanVolumeValue==1) 
						{
							FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div>"+
					        "<input type='button' value='L' class='highlightViabbtnMode pone updateFanVolume' id='inputLow'  modeval='1' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='H' class='ViabbtnMode ptwo updateFanVolume' modeval='5' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='M' class='ViabbtnMode pthree updateFanVolume' modeval='3' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-top: 10px;margin-left: 57px;' title='Click Fan mode' style='text-decoration: none;'></input>";
						}
						else if (fanVolumeValue==5) 
						{
							FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div>"+
					        "<input type='button' value='L' class='ViabbtnMode pone updateFanVolume' id='inputLow'  modeval='1' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='H' class='highlightViabbtnMode ptwo updateFanVolume' modeval='5' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='M' class='ViabbtnMode pthree updateFanVolume' modeval='3' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-top: 10px;margin-left: 57px;' title='Click Fan mode' style='text-decoration: none;'></input>";
						}
						else if (fanVolumeValue==3) 
						{
							FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div>"+
					        "<input type='button' value='L' class='ViabbtnMode pone updateFanVolume' id='inputLow'  modeval='1' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='H' class='ViabbtnMode ptwo updateFanVolume' modeval='5' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='M' class='highlightViabbtnMode pthree updateFanVolume' modeval='3' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-top: 10px;margin-left: 57px;' title='Click Fan mode' style='text-decoration: none;'></input>";
						}
					}
					else
					{
						// 5 STEP CAPABILITY
						if (fanVolumeValue==1) 
						{
							FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div><input type='button' value='LL' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='1' style=' margin-top: 12px;margin-left: 23px;' class='highlightViabbtnMode updateFanVolume' title='Click Fan mode' style='text-decoration: none;'></input>"+
					        "<input type='button' value='L' class='ViabbtnMode pone updateFanVolume' id='inputLow' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='2' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='M' class='ViabbtnMode ptwo updateFanVolume' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='3' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='H' class='ViabbtnMode pthree updateFanVolume' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='4' style='margin-top: 10px;margin-left: 57px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='HH' class='ViabbtnMode pfour updateFanVolume' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='5' style='margin-left: 61px;margin-top: 7px;' title='Click Fan mode' style='text-decoration: none;'></input></div>";
						}
						else if (fanVolumeValue==2) 
						{
							FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div><input type='button' value='LL' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='1' style=' margin-top: 12px;margin-left: 23px;' class='ViabbtnMode updateFanVolume' title='Click Fan mode' style='text-decoration: none;'></input>"+
					        "<input type='button' value='L' class='highlightViabbtnMode pone updateFanVolume' id='inputLow' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='2' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='M' class='ViabbtnMode ptwo updateFanVolume' modeval='3' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='H' class='ViabbtnMode pthree updateFanVolume' modeval='4' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-top: 10px;margin-left: 57px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='HH' class='ViabbtnMode pfour updateFanVolume' modeval='5' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 61px;margin-top: 7px;' title='Click Fan mode' style='text-decoration: none;'></input></div>";
						}
						else if (fanVolumeValue==3) 
						{
							FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div><input type='button' value='LL' modeval='1' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style=' margin-top: 12px;margin-left: 23px;' class='ViadirectionbbtnMode updateFanVolume' title='Click Fan mode' style='text-decoration: none;'></input>"+
					        "<input type='button' value='L' class='ViabbtnMode pone updateFanVolume' id='inputLow'  modeval='2' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='M' class='highlightViabbtnMode ptwo updateFanVolume' modeval='3' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='H' class='ViabbtnMode pthree updateFanVolume' modeval='4' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-top: 10px;margin-left: 57px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='HH' class='ViabbtnMode pfour updateFanVolume' modeval='5' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 61px;margin-top: 7px;' title='Click Fan mode' style='text-decoration: none;'></input></div>";
						}
						else if (fanVolumeValue==4)
						{
							FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div><input type='button' value='LL' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='1' style=' margin-top: 12px;margin-left: 23px;' class='ViadirectionbbtnMode updateFanVolume' title='Click Fan mode' style='text-decoration: none;'></input>"+
					        "<input type='button' value='L' class='ViabbtnMode pone updateFanVolume' id='inputLow'  modeval='2' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='M' class='ViabbtnMode ptwo updateFanVolume' modeval='3' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='H' class='highlightViabbtnMode pthree updateFanVolume' modeval='4' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-top: 10px;margin-left: 57px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='HH' class='ViabbtnMode pfour updateFanVolume' modeval='5' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 61px;margin-top: 7px;' title='Click Fan mode' style='text-decoration: none;'></input></div>";
						}
						else
						{
							FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div><input type='button' value='LL' modeval='1' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style=' margin-top: 12px;margin-left: 23px;' class='ViadirectionbbtnMode updateFanVolume' title='Click Fan mode' style='text-decoration: none;'></input>"+
					        "<input type='button' value='L' class='ViabbtnMode pone updateFanVolume' id='inputLow'  modeval='2' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='M' class='ViabbtnMode ptwo updateFanVolume' modeval='3' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='H' class='ViabbtnMode pthree updateFanVolume' modeval='4' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-top: 10px;margin-left: 57px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
					         "<input type='button' value='HH' class='highlightViabbtnMode pfour updateFanVolume' modeval='5' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'s style='margin-left: 61px;margin-top: 7px;' title='Click Fan mode' style='text-decoration: none;'></input></div>";
						}
					}
					//*******************if fanVolumeValue is not null end***********************
					}
					else
						{
						//***********if fanVolumeValue is null start***************
						
						if (fanVolumeLevelCapability==2) 
						{
							// 2 STEP CAPABILITY
								FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div>"+
						        "<input type='button' value='L' class='ViabbtnMode pone updateFanVolume' id='inputLow'  modeval='1' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
						         "<input type='button' value='H' class='ViabbtnMode ptwo updateFanVolume' modeval='5' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>";
		
						}
						else if (fanVolumeLevelCapability==3)
						{
							// 3 STEP CAPABILITY
								FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div>"+
						        "<input type='button' value='L' class='ViabbtnMode pone updateFanVolume' id='inputLow'  modeval='1' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
						         "<input type='button' value='H' class='ViabbtnMode ptwo updateFanVolume' modeval='5' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
						         "<input type='button' value='M' class='ViabbtnMode pthree updateFanVolume' modeval='3' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' style='margin-top: 10px;margin-left: 57px;' title='Click Fan mode' style='text-decoration: none;'></input>";

						}
						else
						{
							// 5 STEP CAPABILITY
								FanVolumeText +="<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div><input type='button' value='LL' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='1' style=' margin-top: 12px;margin-left: 23px;' class='ViabbtnMode updateFanVolume' title='Click Fan mode' style='text-decoration: none;'></input>"+
						        "<input type='button' value='L' class='ViabbtnMode pone updateFanVolume' id='inputLow' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"'  modeval='2' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
						         "<input type='button' value='M' class='ViabbtnMode ptwo updateFanVolume' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='3' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
						         "<input type='button' value='H' class='ViabbtnMode pthree updateFanVolume' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='4' style='margin-top: 10px;margin-left: 57px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
						         "<input type='button' value='HH' class='ViabbtnMode pfour updateFanVolume' gateWayId='"+device["gatewayid"]+"' deviceId='"+device["deviceId"]+"' modeval='5' style='margin-left: 61px;margin-top: 7px;' title='Click Fan mode' style='text-decoration: none;'></input></div>";

						}
						//**************if fanVolumeValue is null end*****************
						}
					
				}
				
				/***************************************	Fan VOLUME block end****************************/
				
				var ambientContent = "<img src='/imonitor/resources/images/ambient.png'  class = 'IDUambeint' title='Sensed Temperature'></img><br>";
				var ambientTemp = "<div style='font-size: x-large;font-weight: 700;margin-top: 77px;margin-left: 9px;'>"+sensedTemp+"&#8451;</div>";
				contentDiv.append($(ambientTemp));
				contentDiv.append($(ambientContent));
				
				/*contentDiv.append($("<div class='fanDirectiontext'><div style='margin-left: 39px;color: black;'>Fan direction</div><input type='button' value='P0' class='ViadirectionbbtnMode pZero' title='Click Fan mode' style='text-decoration: none;'></input>"+
						        "<input type='button' value='P1' class='highlightViabbtnMode pone' style='margin-left: 28px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
						         "<input type='button' value='P2' class='ViabbtnMode ptwo' style='margin-left: 26px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
						         "<input type='button' value='P3' class='ViabbtnMode pthree' style='margin-top: 10px;margin-left: 7px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
						         "<input type='button' value='P4' class='ViabbtnMode pfour' style='margin-left: 23px;' title='Click Fan mode' style='text-decoration: none;'></input>" +
						         "<input type='button' value='P5' class='ViabbtnMode pfive' style='margin-left: 25px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
						         "<input type='button' value='Swing' class='ViabbtnMode pSwing' style='margin-top:12px;margin-left: 48px;' title='Click Fan mode' style='text-decoration: none;'></input></div>"));*/
				contentDiv.append($("<a></a>")
						.html(FanDirectionText )
						);	
				
				
				/*contentDiv.append($("<div class='fanVolumetext'><div style='margin-left: 55px;color: black;'>Fan level</div><input type='button' value='LL' style=' margin-top: 12px;margin-left: 23px;' class='ViadirectionbbtnMode' title='Click Fan mode' style='text-decoration: none;'></input>"+
				        "<input type='button' value='L' class='ViabbtnMode pone' style='margin-left: 55px; 'title='Click Fan mode' style='text-decoration: none;'></input>"+
				         "<input type='button' value='M' class='ViabbtnMode ptwo' style='margin-left: 24px;margin-top: 12px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
				         "<input type='button' value='H' class='highlightViabbtnMode pthree' style='margin-top: 10px;margin-left: 57px;' title='Click Fan mode' style='text-decoration: none;'></input>"+
				         "<input type='button' value='HH' class='ViabbtnMode pfour' style='margin-left: 61px;margin-top: 7px;' title='Click Fan mode' style='text-decoration: none;'></input></div>"));*/
				
				contentDiv.append($("<a></a>")
						//.attr('href',isOffline ? '#' :'fanVolumeControl.action')
						.html(FanVolumeText )
						);
				
				if(fanmode==8 || fanmode==6)	
				{
					
				}
				else
					{
					contentDiv.append($("<div class='viaHeatmodeslider' id='slider' style='height: 140px;'></div>").slider({
						orientation: "vertical",
						range: "min",
						min: 16,
						max: 32,
						value: commandparam,
						step: 1,
						slide: function(event, ui) 
						{
							var $this_text = jQuery(this).find("input");
							$this_text.val(ui.value);
						},
				   		stop: function( event,ui )
						{
						SendIduTempValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
						}
						})
						);
					//alert("Temperature value "+ device["temperatureValue"]);
					contentDiv.append($("<div class='minAc'>16</div>"));
					contentDiv.append($("<div><input class='viainputfield' type='text' readonly='readonly' value="+device["commandparam"]+"&#8451"+" ></div>"));
					//value='23&#8451;'
					contentDiv.append($("<div class='maxAc'>32</div>"));
					}
				
				
				
				
				if (filterSignStatus==0)
				{
					resetFilterStatus+="";
				}
				else
					{
					resetFilterStatus += "<img src='/imonitor/resources/images/filter.png' width='50px' height='60px' class='filterStatus' title='Click to update Filter status'></img>";
					contentDiv.append($("<a></a>")
							.attr('href',isOffline ? '#' :'resetFilterStatus.action')
							.addClass(isOffline ? '' : 'filterStatusdevicelink')
							.attr("gateWayId", device["gatewayid"])
							.attr("deviceId", device["deviceId"])
							.attr("commandParam", isOff ? 5 : 0)
							.html(resetFilterStatus)
					);
					}
				
				
			}
			
			
			
			
			
			
			
			//end else
			}else{
				
				
			}
			
			}// end via unit
		
			
		if(deviceType == "Z_WAVE_AC_EXTENDER"){
			var aclearnedValues = $.trim(device["acLearnValue"]); //deviceDetails["acLearnValue"] = device.find("acLearnValue").text();
		//	alert("aclearnedValues: "+ aclearnedValues);
			var batteryImg = findProperImageForBatteryLevel(device["batterylevel"]);
			/* contentDiv.append($("<img src='" + batteryImg + "' class='batteryimage'/>").attr('title', device["batterylevel"])); */
			var sensedTemperature = $.trim(device["sensedTemp"]);
			if (sensedTemperature == null || sensedTemperature == "" || sensedTemperature == 0){
				
				sensedTemperature = "NA";
			}
			var imgContent = "";
			var AcSwingimgContent = "";
            var AutoModeContent ="";
			var CoolModeContent ="";
			var HeatModeContent ="";
			var DryModeContent ="";
			var FanModeContent ="";
			var isOff = $.trim(device["commandparam"]) == 0;
			var isacSwingOff = $.trim(device["acSwing"]) == 0;
			var controlContent = "";
			var sensedTemp = "";
			if(isOffline){
				imgContent += offLineIdleImage;
			} 
			else if(isOff){
				imgContent += "<img src='/imonitor/resources/images/offnormalswitch.png' class='switchon rightsideimage' title='click to switch on'></img>";
				//sensedTemp += "<div style=' font-weight:bold; float: left; margin: 20px -164px 0px -10px;'>Ambient Temperature:<input   minvalue='0' maxvalue='60'   maxlength='2' size='1' value='"+sensedTemperature+"' readonly></div>";
				sensedTemp += "<div style=' font-weight:bold; float: left; margin: 20px -164px 0px -10px;'>Ambient Temperature:"+sensedTemperature+"</div>";
			}else if(aclearnedValues.length > 0){
				
				var abc=device["commandparam"];
				var mode=device["fanModeValue"];
				var fanmode=device["fanMode"];
				
				var indexValues = aclearnedValues.split(",");
			//	if(indexValues[0] == 1){
					imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff rightsideimageforac' title='click to switch off'></img>";
					sensedTemp += "<div style=' font-weight:bold; float: left; margin: -6px -161px 6px 77px;'>Ambient Temperature:"+sensedTemperature+"  </div>";
				//}
					
						controlContent = "";
						contentDiv.append(controlContent);

						
							
			if(fanmode==2 || fanmode==5)
			{
			CoolModeContent +="<input type='button' value='Cool' class='highlightbbtnMode CoolModebutton' title='Click Cool mode' style='text-decoration: none;' ></input>";
			
			contentDiv.append($("<div class='sliderAc' id='slider' ><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
				range: "min",
				min: 16,
				max: 28,
				value: abc,
				
				slide: function(event, ui) 
				{
					var $this_text = jQuery(this).find("input");
					$this_text.val(ui.value);
				},
		   		stop: function( event,ui )
				{
				SendAcValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
				}
				})
				);
			contentDiv.append($("<div class='openAc'>"+formatMessage("msg.ui.min")+"</div>"));	
			contentDiv.append($("<div class='closeAc'>"+formatMessage("msg.ui.max")+"</div>"));
			}
			else
			{
			CoolModeContent +="<input type='button' value='Cool' class='bbtnMode CoolModebutton' title='Click Cool mode' style='text-decoration: none;' ></input>";
			}
			
			if(fanmode==1)
			{
			HeatModeContent +="<input type='button' value='Heat' class='highlightbbtnMode HeatModebutton' title='Click Heat mode' style='text-decoration: none;' ></input>";
			contentDiv.append($("<div class='sliderAc' id='slider' ><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
				range: "min",
				min: 16,
				max: 28,
				value: abc,
				
				slide: function(event, ui) 
				{
					var $this_text = jQuery(this).find("input");
					$this_text.val(ui.value);
				},
		   		stop: function( event,ui )
				{
				SendAcValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
				}
				})
				);
			contentDiv.append($("<div class='openAc'>"+formatMessage("msg.ui.min")+"</div>"));	
			contentDiv.append($("<div class='closeAc'>"+formatMessage("msg.ui.max")+"</div>"));
			}
			else
			{
			 HeatModeContent +="<input type='button' value='Heat' class='bbtnMode HeatModebutton' title='Click Heat mode' style='text-decoration: none;' ></input>";
			}
			
			
			// this is to show dry mode if ac extender has learned dry mode
		if(fanmode==8){	
			DryModeContent ="<input type='button' value='Dry' class='highlightbbtnMode DryModebuttonindry' title='Click Dry mode' style='text-decoration: none;' ></input>";
            CoolModeContent ="<input type='button' value='Cool' class='bbtnMode CoolModebuttonindry' title='Click Cool mode' style='text-decoration: none;' ></input>";
            HeatModeContent ="<input type='button' value='Heat' class='bbtnMode HeatModebuttonindry' title='Click Heat mode' style='text-decoration: none;' ></input>";
            contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelinkforlearn')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 8)
					.html(DryModeContent )
					
					);		
			
			
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelinkforlearn')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 1)
					.html(HeatModeContent )
					
					);
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelinkforlearn')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 2)
					.html(CoolModeContent )
					
					);

		}else
		    {	
			 DryModeContent +="<input type='button' value='Dry' class='bbtnMode DryModebutton' title='Click Dry mode' style='text-decoration: none;' ></input>";
		     }
		
		if(fanmode != 8){
		
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 8)
					.html(DryModeContent )
					
					);		
			
			
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 1)
					.html(HeatModeContent )
					
					);
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 2)
					.html(CoolModeContent )
					
					);
		}
			}
//naveen end
			else
			{
				var abc=device["commandparam"];
				var mode=device["fanModeValue"];
				var fanmode=device["fanMode"];
				
				
				//alert("fanmode----"+fanmode);
				AcSwingimgContent += "<img src='/imonitor/resources/images/swingon.png' class='swingoff AcSwingimage' title='click to switch off AC Swing'></img>";
				if(isacSwingOff)
					{
					AcSwingimgContent = "<img src='/imonitor/resources/images/swingoff.png' class='swingoff AcSwingimage' title='click to switch on AC Swing'></img>";
				
					}
				if(fanmode==6)
				{
						imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff rightsideimageforac' title='click to switch off'></img>";
						sensedTemp += "<div style=' font-weight:bold; float: left; margin: -6px -161px 6px 77px;'>Ambient Temperature:"+sensedTemperature+"  </div>";
					//	sensedTemp += "<div style=' font-weight:bold; float: left; margin: 20px -164px 0px -10px;'>Ambient Temperature:"+sensedTemperature+"</div>";
						AcSwingimgContent = "<img src='/imonitor/resources/images/swingon.png' class='swingoff AcSwingimage' title='click to switch off AC Swing'></img>";
						if(isacSwingOff)
							{
						    AcSwingimgContent = "<img src='/imonitor/resources/images/swingoff.png' class='swingoff AcSwingimage' title='click to switch on AC Swing'></img>";
							}
						controlContent = "";
						contentDiv.append(controlContent);

						contentDiv.append($("<div class='sliderAc' id='slider' style='display: none'><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
						range: "min",
						min: 16,
						max: 28,
						value: abc,
						
						slide: function(event, ui) 
						{
							var $this_text = jQuery(this).find("input");
							$this_text.val(ui.value);
						},
				   		stop: function( event,ui )
						{
						SendAcValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
						}
						})
						);
					contentDiv.append($("<div class='openAc' style='display: none'>"+formatMessage("msg.ui.min")+"</div>"));	
					contentDiv.append($("<div class='closeAc' style='display: none'>"+formatMessage("msg.ui.max")+"</div>"));
					
					
					contentDiv.append($("<div id='slider' style='margin: 14px 0px 22px 277px'></div>").slider({
						orientation: "vertical",
						range: "min",
						min: 1,
						max: 3,
						value: mode,
						step: 1,
						slide: function(event, ui) 
						{
							var $this_text = jQuery(this).find("input");
							$this_text.val(ui.value);
						},
				   		stop: function( event,ui )
						{
						SendFanModeValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
						}
						})
						);
					contentDiv.append($("<div class='HighFanModedisplay'>"+"-"+formatMessage("msg.ui.high")+"</div>"));	
					contentDiv.append($("<div class='MidFanModedisplay'>"+"-"+formatMessage("msg.ui.mid")+"</div>"));	
					contentDiv.append($("<div class='MinFanModedisplay'>"+"-"+formatMessage("msg.ui.low")+"</div>"));
					}
					else
					{
						//sensedTemp += "<div style=' float: left; margin: margin: -6px -161px 6px 77px;'>Sensed Temperature:<input   minvalue='0' maxvalue='60'   maxlength='2' size='1' name='timeout' readonly></div>";
						sensedTemp += "<div style=' font-weight:bold; float: left; margin: -6px -161px 6px 77px;'>Ambient Temperature:"+sensedTemperature+"  </div>";
						imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff rightsideimageforac' title='click to switch off'></img>";
						controlContent = "";
						contentDiv.append(controlContent);

						contentDiv.append($("<div class='sliderAc' id='slider' ><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
						range: "min",
						min: 16,
						max: 28,
						value: abc,
						
						slide: function(event, ui) 
						{
							var $this_text = jQuery(this).find("input");
							$this_text.val(ui.value);
						},
				   		stop: function( event,ui )
						{
						SendAcValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
						}
						})
						);
					contentDiv.append($("<div class='openAc'>"+formatMessage("msg.ui.min")+"</div>"));	
					contentDiv.append($("<div class='closeAc'>"+formatMessage("msg.ui.max")+"</div>"));
					
					
					contentDiv.append($("<div class='sliderAcforFanMode' id='slider' ></div>").slider({
						orientation: "vertical",
						range: "min",
						min: 1,
						max: 3,
						value: mode,
						step: 1,
						slide: function(event, ui) 
						{
							var $this_text = jQuery(this).find("input");
							$this_text.val(ui.value);
						},
				   		stop: function( event,ui )
						{
						SendFanModeValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
						}
						})
						);
					contentDiv.append($("<div class='HighFanMode'>"+"-"+formatMessage("msg.ui.high")+"</div>"));	
					contentDiv.append($("<div class='MidFanMode'>"+"-"+formatMessage("msg.ui.mid")+"</div>"));	
					contentDiv.append($("<div class='MinFanMode'>"+"-"+formatMessage("msg.ui.low")+"</div>"));
	}
//alert("fanmode---"+fanmode);
			if(fanmode==10)
			{
			AutoModeContent +="<input type='button' value='Auto' class='highlightbbtnMode AutoModebutton' title='Click Auto mode' style='text-decoration: none;' ></input>";
			}
			else
			{
			AutoModeContent +="<input type='button' value='Auto' class='bbtnMode AutoModebutton' title='Click Auto mode' style='text-decoration: none;' ></input>";       
			}
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 10)
					.html(AutoModeContent )
					
					);
			if(fanmode==2 || fanmode==5)
			{
			CoolModeContent +="<input type='button' value='Cool' class='highlightbbtnMode CoolModebutton' title='Click Cool mode' style='text-decoration: none;' ></input>";
			}
			else
			{
			CoolModeContent +="<input type='button' value='Cool' class='bbtnMode CoolModebutton' title='Click Cool mode' style='text-decoration: none;' ></input>";
			}
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 2)
					.html(CoolModeContent )
					
					);
			if(fanmode==1)
			{
			HeatModeContent +="<input type='button' value='Heat' class='highlightbbtnMode HeatModebutton' title='Click Heat mode' style='text-decoration: none;' ></input>";
			}
			else
			{
			 HeatModeContent +="<input type='button' value='Heat' class='bbtnMode HeatModebutton' title='Click Heat mode' style='text-decoration: none;' ></input>";
			}
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 1)
					.html(HeatModeContent )
					
					);
	
			if(fanmode==8)
			{
			 DryModeContent +="<input type='button' value='Dry' class='highlightbbtnMode DryModebutton' title='Click Dry mode' style='text-decoration: none;' ></input>";
			}
			else
			{
			 DryModeContent +="<input type='button' value='Dry' class='bbtnMode DryModebutton' title='Click Dry mode' style='text-decoration: none;' ></input>";
			}
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 8)
					.html(DryModeContent )
					
					);
			if(fanmode==6)
			{
			 FanModeContent +="<input type='button' value='Fan' class='highlightbbtnMode FanModebutton' title='Click Fan mode' style='text-decoration: none;' ></input>";
			}
	           else
            {
             FanModeContent +="<input type='button' value='Fan' class='bbtnMode FanModebutton' title='Click Fan mode' style='text-decoration: none;' ></input>";
            }		
                    contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')					
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 6)
					.html(FanModeContent )
					
					);
					}
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", isOff ? 5 : 0)
					.html(imgContent)
					
					);
			
			contentDiv.append($("<a></a>")
					.html(sensedTemp)
					);
					
					contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlacswing.action')
					.addClass(isOffline ? '' : 'acswingupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("acSwing", isacSwingOff ? 1: 0)
					.html(AcSwingimgContent)
					);
			contentDiv.append(controlContent);
		}
		//var activeMessage = isOffline ? "<img src='/imonitor/resources/images/device/inactive.png'/>" : "<img src='/imonitor/resources/images/device/active.png'/>";
		//detailSection.append("<br/>" + activeMessage );
		//detailSection.append("<br/>");
		/* vibhu deprecated
		if(armStatus == 'ARM'){
			armImage ="<img src='/imonitor/resources/images/disarmbutton.png' title='click to change' class='armclass' ></img>";
		}
		if(ruleCount > 0) {
			//contentDiv
			detailSection.append("<br />");
			detailSection.append($("<a></a>")
				.attr('href','armdisamAction.action')
				.addClass(isArm ? 'updatedevicearm':'updatedevicearm')
				.attr("gateWayId", device["gatewayid"])
				.attr("deviceId", device["deviceId"])
				.attr("statusName", isArm ? 'DISARM' : 'ARM')
				.html(armImage)
				);
		}*/
		

	if(deviceType == "IP_CAMERA")
	{
		var controlContent="";

		 var returned= CheckMainuser();
		 
		 if(returned == 1)
			{
			 if(enableStatus==0) 
				{ 


					controlContent=($("<a></a>")
					.attr('href','enableDisableAction.action')
					.addClass('enableStatus')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("id", 'enableDisable'+device["deviceId"])
					.attr("statusName", '1')
					.html("<img src=/imonitor/resources/images/enablebutton.png  class='enableclass' title='click to Enable camera'></img>")
					);
				}
				else
					{

					
					controlContent=($("<a></a>")
					.attr('href','enableDisableAction.action')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("statusName", '0')
					.attr("id", 'enableDisable'+device["deviceId"])
					.addClass('enableStatus')
					.html("<img src=/imonitor/resources/images/disablebutton.png title='click to disable camera' class='enableclass'  ></img>")
					);
					}
				contentDiv.append(controlContent);
			}
		 	
			
	
	}
	
	deviceLi.append(contentDiv);
	return deviceLi;
	}
};
//bhavya diagnosis
this.generateDiagnosisDevicesHtml = function(device,isForChangeDevice){
	var modelnumber = $.trim(device["modelnumber"]);
	//var make = $.trim(device["make"]);
	var deviceType = $.trim(device["deviceType"]);
	var enableDeviceListing = $.trim(device["enableList"]);
	if((deviceType != "MODE_HOME") && (deviceType != "MODE_STAY") && (deviceType != "MODE_AWAY") && (enableDeviceListing != "0"))
	{
       if(modelnumber == "HMD")
    	  {
	var deviceUl = $("#deviceListUl");
	//var isFirst = deviceUl.find('li[tabindex=' + device["index"] + ']').get(0) == undefined;

	//vibhu change to stop refresh if command execution in progress
	var str = deviceUl.find('li[tabindex=' + device["index"] + ']').html();
	if(!isForChangeDevice && str != null && str.toLowerCase().indexOf("commandexecutioninprogress") >= 0)
	{
		return false;
	}

	var isGateWayOfline = gateWays[device["gatewayid"]]['status'] != "Online";
	var isDeviceOffline = device["alertparam"] != "DEVICE_DOWN" ? false : true;
	var isOffline = isDeviceOffline || isGateWayOfline;
	var posIdx = $.trim(device["positionIndex"]);
	var deviceLi = $("<li></li>").addClass('widget').addClass('color-white').attr("tabindex", device["index"]).attr("id", posIdx+"-"+device["index"]);
	var contentDiv = $("<div></div>").addClass('widget-content');
	contentDiv.append($("<img />").attr('src', device["iconfile"]).addClass('deviceicon').attr('title','Active'));
	if(isOffline)
	{
		contentDiv.append($("<img />").attr('src', '/imonitor/resources/images/deviceidle.png').attr('title','Inactive').addClass('deviceicon').addClass('idleswitch'));
	}
	var detailSection = $("<div class='detailsection'></div>");
	detailSection.append(device["deviceName"]);
	contentDiv.append(detailSection);	
			var imgContent = "";
                     var scanContent ="";
			var controlContent = "";
			var scanSECOND="";
			//var isOff = $.trim(device["commandparam"]) == 0;
var imagesrc = "";
var tittle="";
if((device["HMDalertstatus"])=="HMD_FAILURE")
{
	imagesrc = "/imonitor/resources/images/healthnotok.png";
	tittle = "health Failure";
} 
else if((device["HMDalertstatus"])=="HMD_NORMAL")
{
	imagesrc = "/imonitor/resources/images/healthok.png";
	tittle = "health is Normal";
} 
else if((device["HMDalertstatus"])=="HMD_WARNING")
{
	imagesrc = "/imonitor/resources/images/healthwarn.png";
	tittle = "health Warning";
} 

if(isOffline){
//alert((device["HMDalertstatus"]));

if(imagesrc!="")
{
imgContent += "<img src='"+imagesrc+"' class='rightsideimage' title='inactive'></img>"
		+"<span class='lastscan'>Last Scan: "+device["HMDalertTime"]
		+ "</span>"
		+ "<input type='button' value='Scan' class='bbtndiognosis scanbutton' title='inactive' ></input>"
		+ "<input type='button' value='Current status' class='bbtndiognosis currentstatusbutton' title='inactive' ></input>";

}
else
{
imgContent += "<span class='lastscanOffline'>Last Scan: "+device["HMDalertTime"]
		+ "</span>"
		+ "<input type='button' value='Scan' class='bbtndiognosis scanbuttoninoffline' title='inactive' ></input>"
		+ "<input type='button' value='Current status' class='bbtndiognosis currentstatusbuttonOffline' title='inactive' ></input>";

}


		contentDiv.append(imgContent);
}
else{
if(imagesrc!="")
{
imgContent += "<img src='"+imagesrc+"' class='rightsideimage' title='"+tittle+"'></img>"
              +"<div class='lastscan'>Last Scan: "+device["HMDalertTime"]
              +"</div>";
	scanContent +="<input type='button' value='Scan' class='bbtndiognosis scanbutton' title='click to scan' style='text-decoration: none;' ></input>";
		 scanSECOND += "<input type='button' value='Graph' class='bbtndiognosis graphbutton' title='click to scan' style='display: none;' ></input>";
	       controlContent+="<input type='button' value='Current status' class='bbtndiognosis currentstatusbutton' title='click to Current Status'></input>";
}
else
{


		if(device["HMDalertTime"]=="")
		{
		imgContent += "<span class='lastscanNoValue'>Last Scan: "+device["HMDalertTime"]+"</span>";
		scanContent +="<input type='button' value='Scan' class='bbtndiognosis scanbuttonNovalues' title='click to scan' ></input>";
		scanSECOND += "<input type='button' value='Graph' class='bbtndiognosis graphbuttonNovalues' title='click to scan' style='display: none;' ></input>";
	    controlContent+="<input type='button' value='Current status' class='bbtndiognosis currentstatusbuttonNovalues' title='click to Current Status'></input>";
}

}

		contentDiv.append(imgContent);
                     		contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'ScanNow.action')
					.addClass(isOffline ? '' : 'updatescanlink')
					.attr("make", device["make"])
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.html(scanContent )
					
					);
					
					
                     		contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'ScanNow.action')
					.addClass(isOffline ? '' : 'updatescanlink')
					.attr("make", device["make"])
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.html(scanSECOND)
					
					);
					contentDiv.append($("<a></a>")
					.attr('href','CurrentStatus.action')
                    			.addClass(isOffline ? '' : 'updatecurentstatuslink')
					.attr("make", device["make"])
                    			.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.html(controlContent)
					);
}
                      
		

	deviceLi.append(contentDiv);
	return deviceLi;
	}
	}
};
//bhavya d end
this.generateDevicesDivForMap = function(device,isForChangeDevice){
	var deviceType = $.trim(device["deviceType"]);
	
	var deviceLi = "";
	var enableDeviceListing = $.trim(device["enableList"]);

	
	if((deviceType != "MODE_HOME") && (deviceType != "MODE_STAY") && (deviceType != "MODE_AWAY") && (enableDeviceListing != "0"))
	{
		
		var top = $.trim(device["top"]);
		var left= $.trim(device["left"]);
		var isGateWayOfline = gateWays[device["gatewayid"]]['status'] != "Online";
		var isDeviceOffline = device["alertparam"] != "DEVICE_DOWN" ? false : true;
		var isOffline = isDeviceOffline || isGateWayOfline;
		var offLineIdleImage = "";//"<img src='/imonitor/resources/images/idleswitch.png' class='rightsideimage idleswitch' title='inactive'></img>";
		//vibhu deprecated var armImage = "";//"<img src='/imonitor/resources/images/armbutton.png' class='armclass' title='click to change'>";
		deviceLi = $("<div></div>").addClass('widget').addClass('color-white').attr("tabindex", device["index"]).addClass('controlDevice');
		
	if(left!="" && top!="")
	{

		
		var title="";
		if(isOffline)
		{
			title+=" "+device["deviceName"]+"  is Inactive";
		}
		else
		{
			title+= formatMessage("msg.ui.setup.0010");
		}
		deviceLi.append($("<img />").attr('src', device["iconfile"]).addClass('deviceiconMapAssigned').attr('title',title));
		
		/*var detailSection = $("<p class='detailsectionMapAssigned'></p>");
		detailSection.append(device["deviceName"]);
		deviceLi.append(detailSection);*/
		deviceLi.attr("deviceId", device["deviceId"]);
		deviceLi.attr("Id", device["index"] );
		deviceLi.attr("top", top);
		deviceLi.attr("left", left);
		deviceLi.attr("mapid",device["mapid"]);
		deviceLi.attr("status",isOffline);
		deviceLi.attr("deviceType", device["deviceType"]);
		deviceLi.attr("CommandParam", device["commandparam"]);
		/*var DeleteIcon=$("<a class='deleteDeviceMap ui-icon ui-icon-trash' ></a>");
		deviceLi.append(DeleteIcon);*/
		/*var ControlIcon=$("<a class='ui-icon ui-icon-refresh controlDevice'></a>");
		deviceLi.append(ControlIcon);*/
		var DeleteIcon=$("<a><img src='/imonitor/resources/images/pin.png' class='deleteDeviceMap' title='click Change Position'></img></a>");
		deviceLi.append(DeleteIcon);
		//deviceLi.apend($("<a></a>"));
		//deviceLi.apend($("<a title='Remove From Map' class='deleteDeviceMap' ></a>"));//.append($("<img/>").attr('src','/imonitor/resources/images/delete.png')));
		}
	else
	{
	
	//var deviceUl = $("#DeviceDiv");
	//var isFirst = deviceUl.find('li[tabindex=' + device["index"] + ']').get(0) == undefined;

	//vibhu change to stop refresh if command execution in progress
/*	var str = deviceUl.find('li[tabindex=' + device["index"] + ']').html();
	if(!isForChangeDevice && str != null && str.toLowerCase().indexOf("commandexecutioninprogress") >= 0)
	{
		return false;
	}*/

	
	//var contentDiv = $("<div></div>").addClass('widget-content');
	
		var title="";
		if(isOffline)
		{
			title+=" "+device["deviceName"]+"  is Inactive";
		}
		else
		{
			title+=" "+device["deviceName"]+"  is Active";
		}
	deviceLi.append($("<img />").attr('src', device["iconfile"]).addClass('deviceiconMap').attr('title',title));
	
	if(isOffline)
	{
		deviceLi.attr('title',device["deviceName"]+' is InActive');
	}
	var detailSection = $("<p class='detailsectionMap'></p>");
	detailSection.append(device["deviceName"]);
	deviceLi.append(detailSection);
	deviceLi.attr("deviceId", device["deviceId"]);
	deviceLi.attr("Id", device["index"]);
	deviceLi.attr("mapid",device["mapid"]);
	deviceLi.attr("status",isOffline);
	deviceLi.attr("deviceType", device["deviceType"]);
	deviceLi.attr("CommandParam", device["commandparam"]);
	
	//apend($("<a title='Remove From Map' class='deleteDeviceMap' ></a>"));//.append($("<img/>").attr('src','/imonitor/resources/images/delete.png')));
	}
	}
	else
	{
		return false;
	}
	
	//deviceLi.append("<a title='Remove From Map' class='ui-icon ui-icon-trash deleteDeviceMap'></a>");
/*var armStatus = $.trim(device["deviceArmStatus"]);
	var ruleCount = 1 * $.trim(device["rulescount"]);
	var isArm = armStatus == 'ARM' ? true : false;
		var deviceType = $.trim(device["deviceType"]);
		if(deviceType == "Z_WAVE_SWITCH" || deviceType == "Z_WAVE_SIREN"){
			var imgContent = "";
			var isOff = $.trim(device["commandparam"]) == 0;
			if(isOffline){
				imgContent += offLineIdleImage;
			} else if(isOff){
				imgContent += "<img src='/imonitor/resources/images/offnormalswitch.png' class='switchon rightsideimage' title='click to switch on'></img>";
			}else{
				imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff rightsideimage' title='click to switch off'></img>";
			}
		deviceLi.append($("<a></a>")
					.attr('href',isOffline ? '#' :'switchonff.action')
					.addClass(isOffline ? '' : 'updatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", isOff ? 1 : 0)
					.html(imgContent)
					);
		}
		if(deviceType == "Z_WAVE_DIMMER"){
			try{
				var controlContent = "";
				var controlContent2 = "";
				if(isOffline){
					controlContent = offLineIdleImage;
					deviceLi.append(controlContent);
				}else{

	
					var abc=device["commandparam"];
					
					controlContent = "";
				deviceLi.append(controlContent);

					deviceLi.append($("<div class='slider' id='slider' ><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
					range: "min",
					min: 0,
					max: 99,
					value: abc,
					
					slide: function(event, ui) 
					{
						var $this_text = jQuery(this).find("input");
						$this_text.val(ui.value);
					},
			   		stop: function( event,ui )
					{
					var result=senddimmervalue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
					}
					}));
					deviceLi.append($("<div class='close'>MIN</div>"));	
					deviceLi.append($("<div class='open'>MAX</div>"));
					}
			
				
			}catch(err){
			}
		}
		if(deviceType == "IP_CAMERA")
			{	
			var controlContent = "";
			var ismodelno = device["modelnumber"] ;
			var modelno = device["deviceName"];
			var enableStatus = device["enableStatus"];
			var deviceid=	device["deviceId"];
			var EnableContent = "";
			
			if(isOffline)
			{
				controlContent = offLineIdleImage;
			}
			else
			{
			var Streamvalue = $("#deviceListUl").data("Stream");
			if(Streamvalue==deviceid)
			{
					$("#deviceListUl").data("Stream",device["deviceId"]);
					controlContent = $("<div id='Camera' style='background-color:red' ></div>").append($("<a href='#'></a>").append($("<img/>")
						.attr('src','../resources/images/stopbutton.png'))
						.addClass('rightsideimage')
						.addClass('changecamera')
						.addClass('nowselectedcamera')
						.addClass('streamingstarted')
						.attr('deviceid',device["deviceId"])
						.attr("gateWayId", device["gatewayid"])
						.attr('devicename',device["deviceName"])
						.attr('title','click to stop')
						.attr('motiondetection',device["modelnumber"] == "RC8061" ||  device["modelnumber"] == "H264PT"? true : false )
						.attr('controlNightVision',device["controlNightVision"]));

			}
			else
			{
				var Streamvalue = $("#deviceListUl").data("Stream");
				var DeviceId = $("#deviceListUl").data("DeviceID");
				if(Streamvalue == deviceid)
				{
					$("#deviceListUl").data("Stream",0);
				}
				if(DeviceId == deviceid)
				{
					controlContent = $("<div id='Camera'></div>").append($("<a href='#'></a>").append($("<img/>")
						.attr('src','/imonitor/resources/images/device/playbutton.png'))
						.addClass('rightsideimage')
						.addClass('changecamera')
						.addClass('nowselectedcamera')
						.attr('deviceid',device["deviceId"])
						.attr("gateWayId", device["gatewayid"])
						.attr('devicename',device["deviceName"])
						.attr('title','click to play')
						.attr('motiondetection',device["modelnumber"] == "RC8061" ||  device["modelnumber"] == "H264PT"? true : false )
						.attr('controlNightVision',device["controlNightVision"]));
				}
				else
				{		
						controlContent = $("<div id='Camera'></div>").append($("<a href='#'></a>").append($("<img/>")
						.attr('src','/imonitor/resources/images/device/playbutton.png'))

						.addClass('rightsideimage')
						.addClass('changecamera')
						.attr('deviceid',device["deviceId"])
						.attr("gateWayId", device["gatewayid"])
						.attr('devicename',device["deviceName"])
						.attr('title','click to play')
						.attr('motiondetection',device["modelnumber"] == "RC8061" ||  device["modelnumber"] == "H264PT"? true : false )
						.attr('controlNightVision',device["controlNightVision"]));
				
				}}}
			deviceLi.append(controlContent);
		}
		if(deviceType == "Z_WAVE_PIR_SENSOR" || deviceType == "Z_WAVE_MULTI_SENSOR"){
			var batteryImg = findProperImageForBatteryLevel(device["batterylevel"]);
			var imgContent = "<img src='/imonitor/resources/images/refreshnormal.png' class='switchon rightsideimage' title='click to on'></img>";
			var controlContent = "";
			if(isOffline){
				controlContent = offLineIdleImage;
			}else{
				controlContent = $("<a href='retrieveTemperature.action'></a>")
				.addClass('updatedevicelink')
				.attr("gateWayId", device["gatewayid"])
				.attr("deviceId", device["deviceId"])
				.attr("commandParam", device["commandparam"])
				.html(imgContent);
			}
			deviceLi.append(controlContent);
			deviceLi.append($("<img src='" + batteryImg + "' class='batteryimage'/>").attr('title', device["batterylevel"]));
			detailSection.append("<br/>Temperature Status : " + device["commandparam"]);
		}
		
		if(deviceType == "Z_WAVE_MOTOR_CONTROLLER")
		{
			var controlContent="";
			var imgContent="";
			var make=device["make"];
			
			var isOff = $.trim(device["commandparam"]) == 0;
			if(isOffline)
			{
				controlContent = offLineIdleImage;
				deviceLi.append(controlContent);
			}

			else
			{
			var abc=device["commandparam"];
			controlContent += "<div>";
			controlContent += "";
			controlContent += "</div>";
			deviceLi.append(controlContent);

			deviceLi.append($("<div class='slider' id='slider'><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
				range: "min",
				min: 0,
				max: 100,
				step: 25,
				value: abc,
				toolTip: true,
				slide: function(event, ui) 
					{
						var $this_text = jQuery(this).find("input");
						$this_text.val(ui.value);
                    			          			
					},

			   	stop: function( event,ui )
				{
			   		if(device["commandparam"]!= ui.value)
					{
					updatedevicelink(ui.value,device["gatewayid"],device["deviceId"],make);
					}	
				}
				
				
        		
				}));
			
			deviceLi.append($("<div class='close'>CLOSE</div>"));	
			deviceLi.append($("<div class='open'>OPEN</div>"));
			
			}
			}
	
		if(deviceType == "Z_WAVE_DOOR_SENSOR" ){
		//	var batteryImg = findProperImageForBatteryLevel(device["batterylevel"]);
			//contentDiv.append($("<img src='" + batteryImg + "' class='batteryimage'/>").attr('title', device["batterylevel"]));
			var isOff=device["commandparam"];
			var imgContent="";
				
				if(isOffline)
				{
				
				}
				else
				{
				if(isOff!=1)
				{
				imgContent += "<img src='/imonitor/resources/images/doorclose.png' class='rightsideimage'></img>";
				}
				else
				{
				imgContent += "<img src='/imonitor/resources/images/dooropen.png' class='rightsideimage'></img>";
				}	
			}
				deviceLi.append(imgContent);

		}
		if(deviceType == "Z_WAVE_DOOR_LOCK")
		{
			
			var imgContent = "";
			var imgContentUnlock="";
			var isOff = $.trim(device["commandparam"]) == 0;
			if(isOffline){
				imgContent += offLineIdleImage;
			} 
			else
			{
			imgContent += "<img src='/imonitor/resources/images/doorlock.png' class='rightsideimage' title='click to lock'></img>";
				imgContentUnlock+="<img src='/imonitor/resources/images/doorunlock.png' class='rightsideimage' title='click to unlock'></img>";
				deviceLi.append($("<a></a>")
					.attr('href',isOffline ? '#' :'lockUnlock.action')
					.addClass('updatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam",1)
					.html(imgContentUnlock)
				);
			}
			deviceLi.append($("<a></a>")
					.attr('href',isOffline ? '#' :'lockUnlock.action')
					.addClass('updatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam",0)
					.html(imgContent));

			
			
	
			}
			
		if(deviceType == "Z_WAVE_AC_EXTENDER"){
			var batteryImg = findProperImageForBatteryLevel(device["batterylevel"]);
			contentDiv.append($("<img src='" + batteryImg + "' class='batteryimage'/>").attr('title', device["batterylevel"])); 
			

			var imgContent = "";
			var isOff = $.trim(device["commandparam"]) == 0;
			var controlContent = "";
			if(isOffline){
				imgContent += offLineIdleImage;
			} 
			else if(isOff){
				imgContent += "<img src='/imonitor/resources/images/offnormalswitch.png' class='switchon rightsideimage' title='click to on'></img>";
			}

			else
			{
				var abc=device["commandparam"];
						imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff rightsideimage' title='click to off'></img>";
						controlContent = "";
						deviceLi.append(controlContent);

						deviceLi.append($("<div class='sliderAc' id='slider' ><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
						range: "min",
						min: 16,
						max: 28,
						value: abc,
						
						slide: function(event, ui) 
						{
							var $this_text = jQuery(this).find("input");
							$this_text.val(ui.value);
						},
				   		stop: function( event,ui )
						{
						SendAcValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
						}
						})
						);
						deviceLi.append($("<div class='openAc'>MIN</div>"));	
						deviceLi.append($("<div class='closeAc'>MAX</div>"));
	

			}
			deviceLi.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'updatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", isOff ? 5 : 0)
					.html(imgContent)
					);
			deviceLi.append(controlContent);
		}
		//var activeMessage = isOffline ? "<img src='/imonitor/resources/images/device/inactive.png'/>" : "<img src='/imonitor/resources/images/device/active.png'/>";
		//detailSection.append("<br/>" + activeMessage );
		//detailSection.append("<br/>");
		if(armStatus == 'ARM'){
			armImage ="<img src='/imonitor/resources/images/disarmbutton.png' title='click to change' class='armclass' ></img>";
		}
		if(ruleCount > 0) {
			//contentDiv
			detailSection.append("<br />");
			detailSection.append($("<a></a>")
				.attr('href','armdisamAction.action')
				.addClass(isArm ? 'updatedevicearm':'updatedevicearm')
				.attr("gateWayId", device["gatewayid"])
				.attr("deviceId", device["deviceId"])
				.attr("statusName", isArm ? 'DISARM' : 'ARM')
				.html(armImage)
				);
		}
		

	if(deviceType == "IP_CAMERA")
	{
		var controlContent="";

		 var returned= CheckMainuser();
		 
		 if(returned == 1)
			{
			 if(enableStatus==0) 
				{ 


					controlContent=($("<a></a>")
					.attr('href','enableDisableAction.action')
					.addClass('enableStatus')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("id", 'enableDisable'+device["deviceId"])
					.attr("statusName", '1')
					.html("<img src=/imonitor/resources/images/enablebutton.png  class='enableclass' title='click to Enable camera'></img>")
					);
				}
				else
					{

					
					controlContent=($("<a></a>")
					.attr('href','enableDisableAction.action')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("statusName", '0')
					.attr("id", 'enableDisable'+device["deviceId"])
					.addClass('enableStatus')
					.html("<img src=/imonitor/resources/images/disablebutton.png title='click to Disable camera' class='enableclass'  ></img>")
					);
					}
			 deviceLi.append(controlContent);
			}
		 	
			
	
	}
	
	//deviceLi.append(contentDiv);*/
	return deviceLi;
	
};

this.generateDevicesHtmlForDeviceControlInMap = function(device,isForChangeDevice){
	
	var deviceType = $.trim(device["deviceType"]);
	var enableDeviceListing = $.trim(device["enableList"]);
	var gatewayVersion = gateWays[device["gatewayid"]]['gateWayVersion'];
	if((deviceType != "MODE_HOME") && (deviceType != "MODE_STAY") && (deviceType != "MODE_AWAY") && (enableDeviceListing != "0"))
	{

	var deviceUl = $("#deviceListUl");
	/*var isFirst = deviceUl.find('li[tabindex=' + device["index"] + ']').get(0) == undefined;

	//vibhu change to stop refresh if command execution in progress
	var str = deviceUl.find('li[tabindex=' + device["index"] + ']').html();
	if(!isForChangeDevice && str != null && str.toLowerCase().indexOf("commandexecutioninprogress") >= 0)
	{
		return false;
	}*/

	var isGateWayOfline = gateWays[device["gatewayid"]]['status'] != "Online";
	var isDeviceOffline = device["alertparam"] != "DEVICE_DOWN" ? false : true;
	var isOffline = isDeviceOffline || isGateWayOfline;
	var offLineIdleImage = "<img src='/imonitor/resources/images/idleswitch.png' class='rightsideimageControl' title='inactive' style='margin: 15px 0px 0px 70px; position: relative; opacity: 1;' ></img>";
	
	
	//vibhu deprecated var armImage="";
	if(deviceType != "Z_WAVE_AC_EXTENDER")
	{
		//vibhu deprecated armImage = "<img src='/imonitor/resources/images/armbutton.png' class='armclassControl' title='click to change'>";
	}
	else
	{
		//vibhu deprecated armImage = "<img src='/imonitor/resources/images/armbutton.png' class='armclass' title='click to change'>";
	}
	
	var deviceTable=$("<table></table>");
	if(deviceType != "Z_WAVE_AC_EXTENDER")
	{
		deviceTable.append($('<tr>').append($('<th>').addClass('detailsectionControl').append(device["deviceName"])));	
	}
	else 
	{
    deviceTable.append($('<tr>').append($('<th>').addClass('detailsectionControlac').append(device["deviceName"])));
    }
	
	var contentDiv = $("<div></div>");//$("<tr><td></td><tr>");
	/*var deviceLi = $("<li></li>").addClass('widget').addClass('color-white').attr("tabindex", device["index"]);
	var contentDiv = $("<div></div>").addClass('widget-content');
	/*contentDiv.append($("<img />").attr('src', device["iconfile"]).addClass('deviceicon').attr('title','Active'));
	if(isOffline)
	{
		contentDiv.append($("<img />").attr('src', '/imonitor/resources/images/deviceidle.png').attr('title','Inactive').addClass('deviceicon').addClass('idleswitch'));
	}*/
	var detailSection = $("<div></div>");//$("<tr><tr>");
	
	contentDiv.append(detailSection);
	
	var armStatus = $.trim(device["deviceArmStatus"]);
	var ruleCount = 1 * $.trim(device["rulescount"]);
	var isArm = armStatus == 'ARM' ? true : false;
	var deviceType = $.trim(device["deviceType"]);
	
	
	
	
	if(deviceType == "Z_WAVE_SWITCH" || deviceType == "Z_WAVE_SIREN"){
		
			var imgContent = "";
			var isOff = $.trim(device["commandparam"]) == 0;
			if(isOffline){
				imgContent += offLineIdleImage;
			} else if(isOff){
				imgContent += "<img src='/imonitor/resources/images/offnormalswitch.png' class='switchon rightsideimageControl' title='click to switch on'></img>";
			}else{
				imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff rightsideimageControl' title='click to switch off'></img>";
			}
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'switchonff.action')
					.addClass(isOffline ? '' : 'updatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", isOff ? 1 : 0)
					.attr("switchType", device["switchType"])
					.html(imgContent)
					);
		}
		
		if(deviceType == "Z_WAVE_LUX_SENSOR"){
			try{
				var controlContent = "";
				var controlContent2 = "";
				if(isOffline){
					controlContent = offLineIdleImage;
					contentDiv.append(controlContent);
				}else{
	
					var abc=device["commandparam"];
					
					controlContent = "";
					contentDiv.append(controlContent);

					contentDiv.append($("<div class='Luxmap'>"+"Current Value: "+abc+" lux</div>"));
					}	
				
			}catch(err){
			}
		}
		if(deviceType == "Z_WAVE_PM_SENSOR"){
			try{
				var controlContent = "";
				var controlContent2 = "";
				if(isOffline){
					controlContent = offLineIdleImage;
					contentDiv.append(controlContent);
				}else{
	
					var abc=device["commandparam"];
					
					controlContent = "";
					contentDiv.append(controlContent);

					contentDiv.append($("<div class='PM'>"+"<b>PM Level<b>:<b> "+abc+"<b><b> &#956g/m<sup>3<sup><b></div>"));
					}	
				
			}catch(err){
			}
		}if(deviceType=="ENCODER"){
			try{
				var controlContent = "";
				var controlContent2 = "";
				if(isOffline){
					controlContent = offLineIdleImage;
					contentDiv.append(controlContent);
				}else{
	
					var abc=device["commandparam"];
					controlContent = "";
					contentDiv.append(controlContent);
					contentDiv.append($("<div class='Lux'></div>"));
				}	
				
			}catch(err){
			}

		}
		
		if(deviceType == "Z_WAVE_DIMMER"){
			try{
				var controlContent = "";
				var controlContent2 = "";
				if(isOffline){
					controlContent = offLineIdleImage;
					contentDiv.append(controlContent);
				}else{

	
					var abc=device["commandparam"];
					
					controlContent = "";
					contentDiv.append(controlContent);

					contentDiv.append($("<div class='slidercontrol' id='slider' ><input class='inputfieldcontrol' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
					range: "min",
					min: 0,
					max: 99,
					value: abc,
					
					slide: function(event, ui) 
					{
						var $this_text = jQuery(this).find("input");
						$this_text.val(ui.value);
					},
			   		stop: function( event,ui )
					{
					var result=senddimmervalue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
					}
					}));
					contentDiv.append($("<div class='closecontrol'>"+formatMessage("msg.ui.min")+"</div>"));	
					contentDiv.append($("<div class='open'>"+formatMessage("msg.ui.max")+"</div>"));
					}
			
				
			}catch(err){
			}
		}
		if(deviceType == "IP_CAMERA")
			{	
			offLineIdleImage = "<img src='/imonitor/resources/images/idleswitch.png' class='rightsideimageControl' title='inactive' style='margin: 13px -160px 0px 120px; position: relative; opacity: 1;' ></img>";
			var controlContent = "";
			var ismodelno = device["modelnumber"] ;
			var modelno = device["deviceName"];
			var enableStatus = device["enableStatus"];
			var deviceid=	device["deviceId"];
			var EnableContent = "";
			/*var Streamvalue = $("#cardSlots").data("Stream");
			if(Streamvalue==deviceid)
			{
					$("#cardSlots").data("Stream",device["deviceId"]);
			}
			else
			{
				    $("#cardSlots").data("Stream",0);
			}*/
			if(isOffline)
			{
				controlContent = offLineIdleImage;
			}
			else
			{
				controlContent = $("<div id='Camera'></div>").append($("<a href='#'></a>").append($("<img/>")
						.attr('src','/imonitor/resources/images/video-icon.png'))
						.addClass('rightsideimage')
						.addClass('changecamera')
						.attr('deviceid',device["deviceId"])
						.attr("gateWayId", device["gatewayid"])
						.attr('devicename',device["deviceName"])
						.attr('title','click to stream video')
						.attr('pantiltControl',device["pantiltControl"])
						.attr('controlNightVision',device["controlNightVision"]));
			}
			
			
			/*	
			var Streamvalue = $("#cardSlots").data("Stream");
			
			if(Streamvalue==deviceid)
			{
					$("#cardSlots").data("Stream",device["deviceId"]);
					controlContent = $("<div id='Camera' style='background-color:red' ></div>").append($("<a href='#'></a>").append($("<img/>")
						.attr('src','../resources/images/stopbutton.png'))
						.addClass('rightsideimage')
						.addClass('changecamera')
						.addClass('nowselectedcamera')
						.addClass('streamingstarted')
						.attr('deviceid',device["deviceId"])
						.attr("gateWayId", device["gatewayid"])
						.attr('devicename',device["deviceName"])
						.attr('title','click to stop')
						.attr('motiondetection',device["modelnumber"] == "RC8061" ||  device["modelnumber"] == "H264PT"? true : false )
						.attr('controlNightVision',device["controlNightVision"]));

			}
			else
			{
				var Streamvalue = $("#cardSlots").data("Stream");
				var DeviceId = $("#cardSlots").data("DeviceID");
				if(Streamvalue == deviceid)
				{
					$("#deviceListUl").data("Stream",0);
				}
				if(DeviceId == deviceid)
				{
					controlContent = $("<div id='Camera'></div>").append($("<a href='#'></a>").append($("<img/>")
						.attr('src','/imonitor/resources/images/video-icon.png'))
						.addClass('rightsideimage')
						.addClass('changecamera')
						.addClass('nowselectedcamera')
						.attr('deviceid',device["deviceId"])
						.attr("gateWayId", device["gatewayid"])
						.attr('devicename',device["deviceName"])
						.attr('title','click to play')
						.attr('motiondetection',device["modelnumber"] == "RC8061" ||  device["modelnumber"] == "H264PT"? true : false )
						.attr('controlNightVision',device["controlNightVision"]));
				}
				else
				{	
						controlContent = $("<div id='Camera'></div>").append($("<a href='#'></a>").append($("<img/>")
						.attr('src','/imonitor/resources/images/video-icon.png'))
						.addClass('rightsideimage')
						.addClass('changecamera')
						.attr('deviceid',device["deviceId"])
						.attr("gateWayId", device["gatewayid"])
						.attr('devicename',device["deviceName"])
						.attr('title','click to play')
						.attr('motiondetection',device["modelnumber"] == "RC8061" ||  device["modelnumber"] == "H264PT"? true : false )
						.attr('controlNightVision',device["controlNightVision"]));
				
				}}}*/
			contentDiv.append(controlContent);
		}
		if(deviceType == "Z_WAVE_PIR_SENSOR" || deviceType == "Z_WAVE_MULTI_SENSOR"){
			var batteryImg = findProperImageForBatteryLevel(device["batterylevel"]);
			//var imgContent = "<img src='/imonitor/resources/images/refreshnormal.png' class='switchon rightsideimageControl' title='click to switch on'></img>";
			var controlContent = "";
			if(isOffline){
				controlContent = offLineIdleImage;
			}else{
//				controlContent = $("<a href='retrieveTemperature.action'></a>")
//				.addClass('updatedevicelink')
//				.attr("gateWayId", device["gatewayid"])
//				.attr("deviceId", device["deviceId"])
//				.attr("commandParam", device["commandparam"])
//				.html(imgContent);
			}
			contentDiv.append(controlContent);
			contentDiv.append($("<img src='" + batteryImg + "' class='batteryimage'/>").attr('title', device["batterylevel"]));
			//detailSection.append("<br/>Temperature Status : " + device["commandparam"]);
		}
		
		if(deviceType == "Z_WAVE_MOTOR_CONTROLLER")
		{
			var controlContent="";
			var imgOpenContent="";
			var imgCloseContent="";
			var make=device["make"];
			var fullOpenClose ="";
			
			var isOff = $.trim(device["commandparam"]) == 0;
			if(isOffline)
			{
				controlContent = offLineIdleImage;
				contentDiv.append(controlContent);
			}
			else
			{
			if((gatewayVersion.indexOf("01.01.03") >= 0) || (gatewayVersion.indexOf("05.02.03") >=0) || (gatewayVersion.indexOf("05.02.10") >= 0)){
			controlContent += "<div>";
			controlContent += "";
			controlContent += "</div>";
			
			imgOpenContent += "<img src='/imonitor/resources/images/curtain_open_btn.png' class='rightsideimage1' title='click to switch off'></img>";
			imgCloseContent += "<img src='/imonitor/resources/images/curtain_close_btn.png' class='rightsideimage2' title='click to switch off'></img>";
			contentDiv.append($("<a></a>")
					//.attr('href',isOffline ? '#' :'switchonff.action')
					.addClass(isOffline ? '' : 'updateOpenControl')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", isOff ? 1 : 0)
					.html(imgOpenContent)

					);
			
			contentDiv.append($("<a></a>")
					//.attr('href',isOffline ? '#' :'switchonff.action')
					.addClass(isOffline ? '' : 'updateCloseControl')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", isOff ? 1 : 0)
					.html(imgCloseContent)

					);
					
			if(isOff){
				fullOpenClose +="<input type='button' value='Full Open' class='bbtnMode' title='click to full open curtain' style='text-decoration: none;float: right;margin-right: 7px;margin-top: 15px;' ></input>";
			}else{
				
				fullOpenClose +="<input type='button' value='Full Close' class='bbtnMode' title='click to full close curtain' style='text-decoration: none;float: right;margin-right: 7px;margin-top: 15px;' ></input>";
			}
			contentDiv.append($("<a></a>")
					//.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'updateMotorControl')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", device["commandparam"])
					.html(fullOpenClose)
					
					);
			}else{
				
				var abc=device["commandparam"];
				controlContent += "<div>";
				controlContent += "";
				controlContent += "</div>";
				contentDiv.append(controlContent);

				contentDiv.append($("<div class='slidercontrol' id='slider'><input class='inputfieldcontrol' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
					range: "min",
					min: 0,
					max: 100,
					step: 25,
					value: abc,
					toolTip: true,
					slide: function(event, ui) 
						{
							var $this_text = jQuery(this).find("input");
							$this_text.val(ui.value);
	                    			          			
						},

				   	stop: function( event,ui )
					{
				   		if(device["commandparam"]!= ui.value)
						{
						updatedevicelink(ui.value,device["gatewayid"],device["deviceId"],make);
						}	
					}
					
					
	        		
					}));
				
				contentDiv.append($("<div class='closecontrol'>"+formatMessage("msg.ui.close")+"</div>"));	
				contentDiv.append($("<div class='open'>"+formatMessage("msg.ui.open")+"</div>"));
				
			}
			}
			}
	
		if(deviceType == "Z_WAVE_DOOR_SENSOR" ){
			var batteryImg = findProperImageForBatteryLevel(device["batterylevel"]);
			contentDiv.append($("<img src='" + batteryImg + "' class='batteryimage'/>").attr('title', device["batterylevel"]));
			var isOff=device["commandparam"];
			var imgContent="";
				
				if(isOffline)
				{
				imgContent += offLineIdleImage;
				}
				else
				{
				if(isOff!=1)
				{
				imgContent += "<img src='/imonitor/resources/images/doorclose.png' class='rightsideimage'></img>";
				}
				else
				{
				imgContent += "<img src='/imonitor/resources/images/dooropen.png' class='rightsideimage'></img>";
				}	
			}
			contentDiv.append(imgContent);

		}
		if(deviceType == "Z_WAVE_DOOR_LOCK")
		{
			
			var imgContent = "";
			var isOff = $.trim(device["commandparam"]) == 0;
			if(isOffline){
				imgContent += offLineIdleImage;
			} else if(isOff){
				imgContent += "<img src='/imonitor/resources/images/doorunlock.png' class='rightsideimageControl' title='click to lock'></img>";
			}else{
				imgContent += "<img src='/imonitor/resources/images/doorlock.png' class='rightsideimageControl' title='click to unlock'></img>";
			}
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'lockUnlock.action')
					.addClass(isOffline ? '' : 'updatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", isOff ? 1 : 0)
					.attr("switchType", device["switchType"])
					.html(imgContent)
					);
				
			}
			
		if(deviceType == "Z_WAVE_AC_EXTENDER"){
			var aclearnedValues = $.trim(device["acLearnValue"]); 
			var batteryImg = findProperImageForBatteryLevel(device["batterylevel"]);
			// contentDiv.append($("<img src='" + batteryImg + "' class='batteryimage'/>").attr('title', device["batterylevel"])); 
			var imgContent = "";
			var AcSwingimgContent = "";
            var AutoModeContent ="";
			var CoolModeContent ="";
			var HeatModeContent ="";
			var DryModeContent ="";
			var FanModeContent ="";
			var isOff = $.trim(device["commandparam"]) == 0;
			var isacSwingOff = $.trim(device["acSwing"]) == 0;
			var fanmode=device["fanMode"];
			var controlContent = "";
			if(isOffline){
				imgContent += offLineIdleImage;
			} 
			else if(isOff){
				imgContent += "<img src='/imonitor/resources/images/offnormalswitch.png' class='switchon rightsideimageControlforac' title='click to switch on'></img>";
			}else if(aclearnedValues.length > 0){
				
				var abc=device["commandparam"];
				var mode=device["fanModeValue"];
				var fanmode=device["fanMode"];
				var indexValues = aclearnedValues.split(",");
			//	if(indexValues[0] == 1){
					imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff rightsideimageforac' title='click to switch off'></img>";
			//	}
					
						controlContent = "";
						contentDiv.append(controlContent);

						
						
			if(fanmode==2 || fanmode==5)
			{
			CoolModeContent +="<input type='button' value='Cool' class='highlightbbtnMode CoolModebutton' title='Click Cool mode' style='text-decoration: none;' ></input>";
			contentDiv.append($("<div class='sliderAc' id='slider' ><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
				range: "min",
				min: 16,
				max: 28,
				value: abc,
				
				slide: function(event, ui) 
				{
					var $this_text = jQuery(this).find("input");
					$this_text.val(ui.value);
				},
		   		stop: function( event,ui )
				{
				SendAcValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
				}
				})
				);
			}
			else
			{
			CoolModeContent +="<input type='button' value='Cool' class='bbtnMode CoolModebutton' title='Click Cool mode' style='text-decoration: none;' ></input>";
			}
			
			if(fanmode==1)
			{
			HeatModeContent +="<input type='button' value='Heat' class='highlightbbtnMode HeatModebutton' title='Click Heat mode' style='text-decoration: none;' ></input>";
			contentDiv.append($("<div class='sliderAc' id='slider' ><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
				range: "min",
				min: 16,
				max: 28,
				value: abc,
				
				slide: function(event, ui) 
				{
					var $this_text = jQuery(this).find("input");
					$this_text.val(ui.value);
				},
		   		stop: function( event,ui )
				{
				SendAcValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
				}
				})
				);
			contentDiv.append($("<div class='openAc'>"+formatMessage("msg.ui.min")+"</div>"));	
			contentDiv.append($("<div class='closeAc'>"+formatMessage("msg.ui.max")+"</div>"));
			}
			else
			{
			 HeatModeContent +="<input type='button' value='Heat' class='bbtnMode HeatModebutton' title='Click Heat mode' style='text-decoration: none;' ></input>";
			}
			
			
			// this is to show dry mode if user has learned dry mode for Ac
	       
			if(fanmode==8)
			{
			 DryModeContent +="<input type='button' value='Dry' class='highlightbbtnMode DryModebuttoncontrol' title='Click Dry mode' style='text-decoration: none;' ></input>";
			}
			else if(fanmode != 6)
			{
			 DryModeContent +="<input type='button' value='Dry' class='bbtnMode DryModebuttoncontrol' title='Click Dry mode' style='text-decoration: none;' ></input>";
			}
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("mode", fanmode)
					.attr("commandParam", 8)
					.html(DryModeContent )
					
					);
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 1)
					.html(HeatModeContent )
					
					);
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'acupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("commandParam", 2)
					.html(CoolModeContent )
					
					);
			}
//naveen end
			

			
			else
			{
				var abc=device["commandparam"];
				var mode=device["fanModeValue"];
				
				AcSwingimgContent += "<img src='/imonitor/resources/images/swingon.png' class='switchoff AcSwingimagecontrol' title='click to switch off AC Swing'></img>";
				if(isacSwingOff)
					{
					AcSwingimgContent = "<img src='/imonitor/resources/images/swingoff.png' class='switchoff AcSwingimagecontrol' title='click to switch on AC Swing'></img>";
					}
			
				if(fanmode==6)
				{
				        AutoModeContent ="<input type='button' value='Auto' class='bbtnMode AutoModebuttonforfancontrol' title='Click Auto mode' style='text-decoration: none;' ></input>";
                        CoolModeContent ="<input type='button' value='Cool' class='bbtnMode CoolModebuttonforfancontrol' title='Click Cool mode' style='text-decoration: none;' ></input>";					    
						DryModeContent  ="<input type='button' value='Dry' class='bbtnMode DryModebuttonforfancontrol' title='Click Dry mode' style='text-decoration: none;' ></input>";
						HeatModeContent ="<input type='button' value='Heat' class='bbtnMode HeatModebuttonforfancontrol' title='Click Heat mode' style='text-decoration: none;' ></input>";
						FanModeContent ="<input type='button' value='Fan' class='bbtnMode FanModebuttonforfancontrol' title='Click Fan mode' style='text-decoration: none;' ></input>";
						
						AcSwingimgContent = "<img src='/imonitor/resources/images/swingon.png' class='switchoff AcSwingimageforfanmodecontrol' title='click to switch off AC Swing'></img>";
						if(isacSwingOff)
						{
						AcSwingimgContent = "<img src='/imonitor/resources/images/swingoff.png' class='switchoff AcSwingimageforfanmodecontrol' title='click to switch on AC Swing'></img>";
						}
						imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff rightsideimagefanmodecontrol' title='click to switch off'></img>";
						controlContent = "";
						detailSection.append(controlContent);

						contentDiv.append($("<div class='sliderAcControl' id='slider' style='display: none'><input class='inputfieldcontrol' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
						range: "min",
						min: 16,
						max: 28,
						value: abc,
						
						slide: function(event, ui) 
						{
							var $this_text = jQuery(this).find("input");
							$this_text.val(ui.value);
						},
				   		stop: function( event,ui )
						{
						SendAcValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
						}
						})
						);
					contentDiv.append($("<div class='openAcmap' style='display: none'>"+formatMessage("msg.ui.min")+"</div>"));	
					contentDiv.append($("<div class='closeAcmap' style='display: none'>"+formatMessage("msg.ui.max")+"</div>"));
					contentDiv.append($("<div class='sliderAcforFanModeDisplaymap' id='slider'></div>").slider({
						orientation: "vertical",
						range: "min",
						min: 1,
						max: 3,
						value: mode,
						step: 1,
						slide: function(event, ui) 
						{
							var $this_text = jQuery(this).find("input");
							$this_text.val(ui.value);
						},
				   		stop: function( event,ui )
						{
						SendFanModeValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
						}
						})
						);
					contentDiv.append($("<div class='HighFanModedisplaymap'>"+"-"+formatMessage("msg.ui.high")+"</div>"));	
					contentDiv.append($("<div class='MidFanModedisplaymap'>"+"-"+formatMessage("msg.ui.mid")+"</div>"));	
					contentDiv.append($("<div class='MinFanModedisplaymap'>"+"-"+formatMessage("msg.ui.low")+"</div>"));
					}
				else
				{
					imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff rightsideimageforaccontrol' title='click to switch off'></img>";
						controlContent = "";
						contentDiv.append(controlContent);

						contentDiv.append($("<div class='sliderAcmap' id='slider' ><input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+"></div>").slider({
						range: "min",
						min: 16,
						max: 28,
						value: abc,
						
						slide: function(event, ui) 
						{
							var $this_text = jQuery(this).find("input");
							$this_text.val(ui.value);
						},
				   		stop: function( event,ui )
						{
						SendAcValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
						}
						})
						);
					contentDiv.append($("<div class='openAcmap'>"+formatMessage("msg.ui.min")+"</div>"));	
					contentDiv.append($("<div class='closeAcmap'>"+formatMessage("msg.ui.max")+"</div>"));
					
					
					contentDiv.append($("<div class='sliderAcforFanModemap' id='slider' ></div>").slider({
						orientation: "vertical",
						range: "min",
						min: 1,
						max: 3,
						value: mode,
						step: 1,
						slide: function(event, ui) 
						{
							var $this_text = jQuery(this).find("input");
							$this_text.val(ui.value);
						},
				   		stop: function( event,ui )
						{
						SendFanModeValue(ui.value,device["gatewayid"],device["deviceId"],device["deviceName"]);
						}
						})
						);
					contentDiv.append($("<div class='HighFanModemap'>"+"-"+formatMessage("msg.ui.high")+"</div>"));	
					contentDiv.append($("<div class='MidFanModemap'>"+"-"+formatMessage("msg.ui.mid")+"</div>"));	
					contentDiv.append($("<div class='MinFanModemap'>"+"-"+formatMessage("msg.ui.low")+"</div>"));
	}
			
				if(fanmode==10)
				{
				AutoModeContent +="<input type='button' value='Auto' class='highlightbbtnMode AutoModebuttoncontrol' title='Click Auto mode' style='text-decoration: none;' ></input>";
				}
				else if(fanmode!=6)
				{
				AutoModeContent +="<input type='button' value='Auto' class='bbtnMode AutoModebuttoncontrol' title='Click Auto mode' style='text-decoration: none;' ></input>";       
				}
				contentDiv.append($("<a></a>")
						.attr('href',isOffline ? '#' :'controlac.action')
						.addClass(isOffline ? '' : 'acupdatedevicelink')
						.attr("gateWayId", device["gatewayid"])
						.attr("deviceId", device["deviceId"])
						.attr("deviceId", device["deviceId"])
						.attr("mode", fanmode)
						.attr("commandParam", 10)
						.html(AutoModeContent )
						
						);
				if(fanmode==2 || fanmode==5)
				{
				CoolModeContent +="<input type='button' value='Cool' class='highlightbbtnMode CoolModebuttoncontrol' title='Click Cool mode' style='text-decoration: none;' ></input>";
				}
				else if(fanmode!=6)
				{
				CoolModeContent +="<input type='button' value='Cool' class='bbtnMode CoolModebuttoncontrol' title='Click Cool mode' style='text-decoration: none;' ></input>";
				}
				contentDiv.append($("<a></a>")
						.attr('href',isOffline ? '#' :'controlac.action')
						.addClass(isOffline ? '' : 'acupdatedevicelink')
						.attr("gateWayId", device["gatewayid"])
						.attr("deviceId", device["deviceId"])
						.attr("mode", fanmode)
						.attr("commandParam", 2)
						.html(CoolModeContent )
						
						);
				if(fanmode==1)
				{
				HeatModeContent +="<input type='button' value='Heat' class='highlightbbtnMode HeatModebuttoncontrol' title='Click Heat mode' style='text-decoration: none;' ></input>";
				}
				else if(fanmode!=6)
				{
				 HeatModeContent +="<input type='button' value='Heat' class='bbtnMode HeatModebuttoncontrol' title='Click Heat mode' style='text-decoration: none;' ></input>";
				}
				contentDiv.append($("<a></a>")
						.attr('href',isOffline ? '#' :'controlac.action')
						.addClass(isOffline ? '' : 'acupdatedevicelink')
						.attr("gateWayId", device["gatewayid"])
						.attr("deviceId", device["deviceId"])
						.attr("mode", fanmode)
						.attr("commandParam", 1)
						.html(HeatModeContent )
						
						);
		
				if(fanmode==8)
				{
				 DryModeContent +="<input type='button' value='Dry' class='highlightbbtnMode DryModebuttoncontrol' title='Click Dry mode' style='text-decoration: none;' ></input>";
				}
				else if(fanmode != 6)
				{
				 DryModeContent +="<input type='button' value='Dry' class='bbtnMode DryModebuttoncontrol' title='Click Dry mode' style='text-decoration: none;' ></input>";
				}
				contentDiv.append($("<a></a>")
						.attr('href',isOffline ? '#' :'controlac.action')
						.addClass(isOffline ? '' : 'acupdatedevicelink')
						.attr("gateWayId", device["gatewayid"])
						.attr("deviceId", device["deviceId"])
						.attr("mode", fanmode)
						.attr("commandParam", 8)
						.html(DryModeContent )
						
						);
				if(fanmode==6)
				{
				 FanModeContent ="<input type='button' value='Fan' class='highlightbbtnMode FanModebuttonforfancontrol' title='Click Fan mode' style='text-decoration: none;' ></input>";
				}
		           else 
	            {
	             FanModeContent +="<input type='button' value='Fan' class='bbtnMode FanModebuttoncontrol' title='Click Fan mode' style='text-decoration: none;' ></input>";
	            }		
	                    contentDiv.append($("<a></a>")
						.attr('href',isOffline ? '#' :'controlac.action')
						.addClass(isOffline ? '' : 'acupdatedevicelink')					
						.attr("gateWayId", device["gatewayid"])
						.attr("deviceId", device["deviceId"])
						.attr("mode", fanmode)
						.attr("commandParam", 6)
						.html(FanModeContent )
						
						);
						}
			
			
			
			detailSection.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlac.action')
					.addClass(isOffline ? '' : 'updatedevicelinkforac')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("mode", fanmode)
					.attr("commandParam", isOff ? 5 : 0)
					.html(imgContent)
					);
			
			contentDiv.append($("<a></a>")
					.attr('href',isOffline ? '#' :'controlacswing.action')
					.addClass(isOffline ? '' : 'acswingupdatedevicelink')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("mode", fanmode)
					.attr("acSwing", isacSwingOff ? 1: 0)
					.html(AcSwingimgContent)
					);
			contentDiv.append(controlContent);
		}
			
			//contentDiv.append(controlContent);
		
		//var activeMessage = isOffline ? "<img src='/imonitor/resources/images/device/inactive.png'/>" : "<img src='/imonitor/resources/images/device/active.png'/>";
		//detailSection.append("<br/>" + activeMessage );
		//detailSection.append("<br/>");
		/* vibhu deprecated
		if(armStatus == 'ARM')
		{
		if(deviceType != "Z_WAVE_AC_EXTENDER")
		{
			armImage = "<img src='/imonitor/resources/images/disarmbutton.png' class='armclassControl' title='click to change'>";
		}
		else
		{
			armImage ="<img src='/imonitor/resources/images/disarmbutton.png' title='click to change' class='armclass' ></img>";
		}
			
			
		}
		if(ruleCount > 0) {
			//contentDiv
			detailSection.append("<br />");
			detailSection.append($("<a></a>")
				.attr('href','armdisamAction.action')
				.addClass(isArm ? 'updatedevicearm':'updatedevicearm')
				.attr("gateWayId", device["gatewayid"])
				.attr("deviceId", device["deviceId"])
				.attr("statusName", isArm ? 'DISARM' : 'ARM')
				.html(armImage)
				);
		}
		*/

	if(deviceType == "IP_CAMERA")
	{
		var controlContent="";

		 var returned= CheckMainuser();
		 
		 if(returned == 1)
			{
			 if(enableStatus==0) 
				{ 


					controlContent=($("<a></a>")
					.attr('href','enableDisableAction.action')
					.addClass('enableStatus')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("id", 'enableDisable'+device["deviceId"])
					.attr("statusName", '1')
					.html("<img src=/imonitor/resources/images/enablebutton.png  class='enableclass' title='click to Enable camera'></img>")
					);
				}
				else
					{
					controlContent=($("<a></a>")
					.attr('href','enableDisableAction.action')
					.attr("gateWayId", device["gatewayid"])
					.attr("deviceId", device["deviceId"])
					.attr("statusName", '0')
					.attr("id", 'enableDisable'+device["deviceId"])
					.addClass('enableStatus')
					.html("<img src=/imonitor/resources/images/disablebutton.png title='click to disable camera' class='enableclass'  ></img>")
					);
					}
				
			}
		contentDiv.append(controlContent);
			
	
	}
	
	deviceTable.append($('<tr>').append($('<td>').append(contentDiv)));
	deviceTable.append($('<tr>').append($('<td>').append(detailSection)));
	
	
	//deviceTable.find('tbody').text("<tr><td>"+contentDiv+"</td><tr><tr><td>"+detailSection+"</td><tr>");;
	return deviceTable;
	}
};
//sumit TBD AVOID LISTING VIRTUAL DEVICE

// Generating the edit-html for Devices.
this.generateDevicesHtmlForEdit = function(device){
	//sumit start
	var modelnumber = $.trim(device["modelnumber"]);
	if((device['deviceType'] != "MODE_HOME") && (device['deviceType'] != "MODE_STAY") && (device['deviceType'] != "MODE_AWAY") && (device['deviceType'] != "Z_WAVE_ENERGY_MONITOR")){
	var deviceLi = $("<li></li>").addClass('widget').addClass("color-gray").attr("tabindex", device["index"]);
	
	var contentDiv = $("<div></div>").addClass('widget-content');
	contentDiv.append($("<img />").attr('src', device["iconfile"]).addClass('deviceicon'));
	var confiDimmerType="";
	var params = "device.generatedDeviceId=" + device["deviceId"];
	params += "&device.gateWay.macId=" + device["gatewayid"];
	params += "&device.id=" + device["index"];
	params += "&device.deviceId=" + device["deviceId1"];
	
	for(var gateWayId in gateWays){  //parishod added to check gateway status
	var gatewayVersion = gateWays[gateWayId]['gateWayVersion'];
	
	if($.trim(gateWays[gateWayId]['status']) == "Online"){
	var modelEditSection = "";
	var feature = $.trim(gateWays[gateWayId]['featuresEnabled']);
	if(device['deviceType'] == "Z_WAVE_AC_EXTENDER"){
		params += "&device.modelNumber=" + device["modelnumber"];
		modelEditSection  = "<a title='Edit Model' href='toUpdateModelNumber.action?"+params+"' class='editlink'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
	}
	if(device['deviceType'] == "Z_WAVE_MOTOR_CONTROLLER"){
		modelEditSection  = "<a title='Edit Model' href='toupdateCurtainModel.action?"+params+"' class='editroomlink'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
	}
		if(((feature & 1) == 1) | ((feature & 3) == 3))
		{		

			if(device['deviceType'] == "Z_WAVE_SWITCH")
			{
				modelEditSection += "<a title='Edit Health Check Model' href='toupdatehealthcheckModel.action?"+params+"' class='editlink'  popupwidth='650' popuptitle='Edit health check device model'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
			}
			if(device['deviceType'] == "Z_WAVE_DIMMER")
			{
				modelEditSection += "<a title='Edit Health Check Model' href='toupdatehealthcheckModel.action?"+params+"' class='editlink'  popupwidth='650' popuptitle='Edit health check device model'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
				
			}
			if(device['deviceType'] == "Z_WAVE_MOTOR_CONTROLLER"){
				modelEditSection += "<a title='Edit Health Check Model' href='toupdatehealthcheckModel.action?"+params+"' class='editlink'  popupwidth='650' popuptitle='Edit health check device model'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
			}
		}
	/*if(modelnumber == "HMD"){
		
		if($.trim(gateWays[gateWayId]['featuresEnabled']) == 1 ||($.trim(gateWays[gateWayId]['featuresEnabled']) == 3))
        {
        }
	}*/
	//sumit start: Camera Orientation Configuration
	var cameraOrientationSection = "";
	if(device['deviceType'] == "IP_CAMERA"){
		cameraOrientationSection = "<a title='Configure Camera Orientation' popuptitle='"+formatMessage("setup.devices.camera.title")+"' " 
			+"popupheight='215' href='toConfigureCameraOrientation.action?"+params
			+"' class='editlink'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
			
		//alert(feature);
		//var presetSection1 ="";
			if(((feature & 3) == 3) | ((feature & 2) == 2))		
			{
        	 //if(device['deviceType'] == "IP_CAMERA"){
				 if($.trim(device["modelnumber"]) == "RC8061" | $.trim(device["modelnumber"]) == "H264PT"){
        		 var dev = device["deviceId"];
        		 var mac = device["gatewayid"];
        		 cameraOrientationSection += "<a title='Set Preset Value' device="+dev+" macid="+mac+" popupheight='500' popupwidth='500' popuptitle='hai' class='presetcameralink' > <img class='actionimage' src='../resources/images/nupdate.png'/> </a>";
				}
        	 //}
			}
	}
	
	var LCDRemoteConfiguration = "";
	if(device['deviceType'] == "Z_WAVE_LCD_REMOTE"){
		LCDRemoteConfiguration = "<a title='Configure LCD Remote' popuptitle='"+formatMessage("setup.devices.LCDremote.title")+"' " 
			+"popupheight='215' href='toLCDremoteConfiguration.action?"+params
			+"' class='editlink'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
	}
	
	//Apoorva
	var SceneControlConfiguration = "";
	if(device['deviceType'] == "DEV_ZWAVE_SCENE_CONTROLLER"){	
		SceneControlConfiguration = "<a title='SceneController Configuration' popuptitle='"+formatMessage("setup.devices.SceneController.title")+"' " 
			+"popupheight='450' popupwidth='550' href='toSceneControllerConfiguration.action?"+params
			+"' class='editlink'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
		
		
	}
	//Navee added
	var WallmoteConfiguration = "";
	if(device['deviceType'] == "DEV_ZWAVE_WALL_MOTE_QUAD"){	
		WallmoteConfiguration = "<a title='Wallmote Configuration' popuptitle='"+formatMessage("setup.devices.Wallmote.title")+"' " 
			+"popupheight='450' popupwidth='550' href='toWallmoteConfiguration.action?"+params
			+"' class='editlink'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
		
		
	}
	//Navee added
	var keyfobeConfiguration = "";
	if(device['deviceType'] == "DEV_ZWAVE_FIB_KEYFOBE"){	
		keyfobeConfiguration = "<a title='Keyfobe Configuration' popuptitle='"+formatMessage("setup.devices.Wallmote.title")+"' " 
			+"popupheight='450' popupwidth='550' href='toKeyfobeConfiguration.action?"+params
			+"' class='editlink'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
		
		
	}
	
	//Diaken VIA
	var DiscoverVRVUnit="";
	if(device['deviceType'] == "VIA"){
		//alert(device['deviceType']);
		DiscoverVRVUnit = "<a title='Discover Slave Unit' popuptitle='"+formatMessage("setup.devices.VIA.title")+"' " 
		+"popupheight='200' popupwidth='675' href='toModbusSlave.action?"+params
		+"' class='editroomlink'> <img class='actionimage' src='../resources/images/excute.png'/> </a>";
		
		
	}
	
	//Diaken VIA HA_IF
	var HAIFUnit="";
	if(device['deviceType'] == "HA_IF"){
		
		//HAIFUnit = "<button class='haif_button'><img class='actionimage' src='../resources/images/excute.png'/></button>";
	
	/*	HAIFUnit = "<a title='Discover Indoor Units' popuptitle='"+formatMessage("setup.devices.Indoor.title")+"' " 
		+"popupheight='200' popupwidth='600' href=''"+
		" class='editlink'> <img class='actionimage' src='../resources/images/excute.png'/> </a>";*/
		
		HAIFUnit = "<a title='Discover Indoor Units' popuptitle='"+formatMessage("setup.devices.Indoor.title")+"' " 
		+"popupheight='150' popupwidth='350' href='toDiscoverIndoorUnit.action?"+params
		+"' class='editroomlink'> <img class='actionimage' src='../resources/images/excute.png'/> </a>";
		
	}
	
	//Diaken VIA Indoor units
	var IndoorUnits="";
	var resetFilter="";
	if(device['deviceType'] == "VIA_UNIT")
	{
		IndoorUnits = "<a title='Get Capability' popuptitle='"+formatMessage("setup.devices.Capability.title")+"' " 
		+"popupheight='200' popupwidth='600' href='toGetCapability.action?"+params
		+"' class='editroomlink'> <img class='actionimage' src='../resources/images/idleswitch.png'/> </a>";
		var filterSignStatus= device["filterSignStatus"] ;      
		/*if (filterSignStatus=="0") 
		{
			resetFilter="";
		} 
		else 
		{
			resetFilter = "<a title='Reset Filter Status' popuptitle='"+formatMessage("setup.devices.Filter.title")+"' popupheight='200' popupwidth='600'  href='toResetFilterStatus.action?"+params+"' class='editroomlink'> " +
					"<img class='actionimage' src='../resources/images/filter.png'/> </a>";
			
			
			resetFilter = "<a title='Reset Filter Status' popuptitle='"+formatMessage("setup.devices.Filter.title")+"' " 
			+"popupheight='200' popupwidth='600' href='resetFilterStatus.action?"+params
			+"' class='editroomlink'> <img class='actionimage' src='../resources/images/filter.png'/> </a>";
		}*/
		
	}
	
	//sumit start: No Motion Detection for X hours for PIR sensor
	var noMotionConfiguration ="";
	if(device['deviceType'] == "Z_WAVE_PIR_SENSOR" || device['deviceType'] == "Z_WAVE_MULTI_SENSOR"){
		noMotionConfiguration = "<a title='Configure No Motion Dectection Period' popuptitle='"+formatMessage("setup.devices.pir.title")+"'  href='toConfigureNoMotionPeriod.action?"
								+params+"' class='editlink'> <img class='actionimage' src='../resources/images/configurenomotion.png'/> </a>";
	}
	
	
	var editSection = "<a title='Configure Device Details' popuptitle='"+formatMessage("setup.devices.edit.title")+"'  href='toUpdateFriendlyNameAndLocation.action?"+params+"' class='editroomlink'> <img class='actionimage' src='../resources/images/edit.png'/> </a>";
	
	//Diaken VIA for Modbus Slave
	if(device['deviceType'] == "HA_IF")
	{
	editSection = "<a title='Configure Slave' popuptitle='"+formatMessage("setup.devices.edit.title")+"'  href='toUpdateFriendlyNameAndLocationForSlave.action?"+params+"' class='editroomlink'> <img class='actionimage' src='../resources/images/edit.png'/> </a>";
	}
	
	
	//Diaken VIA for Indoor Unit
	if(device['deviceType'] == "VIA_UNIT")
	{
	editSection = "<a title='Configure IndoorUnit' popuptitle='"+formatMessage("setup.devices.edit.title")+"'  href='toUpdateFriendlyNameAndLocation.action?"+params+"' class='editroomlink'> <img class='actionimage' src='../resources/images/edit.png'/> </a>";
	}
	
	
	
	//sumit: avoid configure alarms for URC/LCD Remotes
	var AlertSection = "";
	if((device['deviceType'] != "Z_WAVE_LCD_REMOTE") && (device['deviceType'] != "VIA" ) && (device['deviceType'] != "HA_IF") && (device['deviceType'] != "VIA_UNIT")){
		AlertSection = "<a title='Configure Alarms' popuptitle='"+formatMessage("setup.devices.alarms.title")+"' href='toChooseAlert.action?"+params+"' class='Alarmlink'> <img class='actionimage' src='../resources/images/configurealarms.png'/> </a>";
	}
	
	//sumit: TO CHECK RULE COUNT BEFORE DELETING A DEVICE
	//var removeSection = "<a title='Remove Device' href='removeDevice.action?"+params+"' class='removedevicelink' title='Remove Device " + device['deviceName'] + "'> <img src='../resources/images/delete.jpg'/> </a>";
	var removeSection = "<a title='Remove Device' href='checkDeviceForRule.action?"+params+"' class='removedevicelink' title='Remove Device " + device['deviceName'] + "'> <img class='actionimage' src='../resources/images/delete.png'/> </a>";
	
	
	//Diaken VIA 
	if(device['deviceType'] == "VIA")
		{
	
		removeSection = "<a title='Remove VIA' href='checkViaForSlave.action?"+params+"' class='removevialink' title='Remove Device " + device['deviceName'] + "'> <img class='actionimage' src='../resources/images/delete.png'/> </a>";
		}
	
	//Diaken VIA
	if(device['deviceType'] == "HA_IF")
	{
	removeSection = "<a title='Remove Slave Device' href='checkSlaveForIndoorUnits.action?"+params+"' class='removeSlavelink' title='Remove Device " + device['deviceName'] + "'> <img class='actionimage' src='../resources/images/delete.png'/> </a>";
	}
	
	if(device['deviceType'] == "VIA_UNIT")
	{
	removeSection = "<a title='Remove Indoor Unit' href='checkIDUDeviceForRule.action?"+params+"' class='removeIDUlink' title='Remove Device " + device['deviceName'] + "'> <img class='actionimage' src='../resources/images/delete.png'/> </a>";
	}
	
	
	var nupdateSection = ""
	//vibhu start , Naveen added if statement on 5th June 2018
	if((device['deviceType'] != "VIA" ) && (device['deviceType'] != "HA_IF") && (device['deviceType'] != "VIA_UNIT")){
	//alert("device type: " + device['deviceType']);
		nupdateSection = "<a title='Device configure' popuptitle='Update Configuration' href='ToNupdateDevice.action?"+params+"' class='Tonupdatedevicelink' > <img class='actionimage' src='../resources/images/nupdate.png'/> </a>";
	}
	//vibhu end
	
	//sumit start: Rocker or Tact Switch for Switch and Dimmer
	var switchTypeSection = "";
	var networkStatus = "";
	
	
	
	if((gatewayVersion.includes("IMSZING")) || (gatewayVersion.includes("IMSPRIME") !=0)){
		networkStatus="<a id='network' title='Network Stats' href='sendNetworkStatusRequest.action?"+params+"' class='editlink' popupheight='200' popupwidth='950'  popuptitle='Signal Strength of  " + device['deviceName'] + "'> <img class='actionimage' src='../resources/images/signalfilled.png'/> </a>";
	}
	
	
	if(device['deviceType'] == "Z_WAVE_SWITCH"  || device['deviceType'] == "Z_WAVE_DIMMER"){
		switchTypeSection = "<a title='Configure Switch Type' popuptitle='"+formatMessage("setup.devices.switches.switchType")+"'  href='toConfigureSwitchType.action?"
								+params+"' class='editlink'> <img class='actionimage' src='../resources/images/configurenomotion.png'/> </a>";
	
	}
	
	var configureRGB="";
	if(device['deviceType'] == "DEV_ZWAVE_RGB") {
		configureRGB = "<a title='Configure RGB Type' popuptitle='"+formatMessage("setup.devices.RGB.switchType")+"'  href='toConfigureRGB.action?"
								+params+"' class='editlink'> <img class='actionimage' src='../resources/images/nupdate.png'/> </a>";
		
	
	}
	
	//Virtual Switch Feature start
	var virtualSwitchConfiguration = "";
	if(device['deviceType'] == "DEV_ZWAVE_VIRTUAL_SW"){
		switchTypeSection = "<a title='Configure Switch Type' popuptitle='"+formatMessage("setup.devices.switches.switchType")+"'  href='toConfigureVirtualSwitchType.action?"
								+params+"' class='editlink'> <img class='actionimage' src='../resources/images/configurenomotion.png'/> </a>";
		//("SwitchType of DEV_ZWAVE_VIRTUAL_SW : " + device['switchType']  );
		if (device['switchType'] == 7) 
		{
			virtualSwitchConfiguration = "<a title='Virtual Switch Configuration' popupheight='200' popupwidth='550' popuptitle='"+formatMessage("setup.devices.VirtualSwitchConfiguration.title")+"'  href='toVirtualSwitchConfiguration.action?"
			+params+"' class='editlink'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
		}
	
	}
	//Virtual Switch Feature End
	
	//Naveen Commented on 4th Oct 2017
	/*if(device['deviceType'] == "Z_WAVE_DIMMER" ){
		confiDimmerType = "<a title='Configure dimmer type' popuptitle='"+formatMessage("setup.devices.switches.switchType")+"'  href='toConfigureDimmer.action?"
		+params+"' class='editlink'> <img class='actionimage' src='../resources/images/configurenomotion.png'/> </a>";
	
	
	}*/ 
	//naveen start: For siren configuration
	var configuresiren = "";
	if(device['deviceType'] == "Z_WAVE_SIREN" ){
		configuresiren = "<a title='Configure Siren' popuptitle='Siren Configuration'  href='ToConfigureSiren.action?"+params+"' class='Tonupdatedevicelink' > <img class='actionimage' src='../resources/images/CustomWidgets.png'/> </a>";
		
	}
	// naveen end
	// naveen start for setting user password in door lock
	var configureusermode = "";
	if(device['deviceType'] == "Z_WAVE_DOOR_LOCK" ){
		configureusermode = "<a title='Configure user code' popuptitle='Configure user password'  href='Toupdateusermode.action?"+params+"' class='Tonupdatedevicelink' > <img class='actionimage' src='../resources/images/CustomWidgets.png'/> </a>";
		
	}
	// Naveen added to implement minimote configuration on 23rd June 2014
	var minimoteconfig = "";
	if(device['deviceType'] == "Z_WAVE_MINIMOTE"){
		minimoteconfig = "<a title='Configure minimote ' popuptitle='Minimote Configuration' href='toUpdateminimoteConfiguration.action?"+params+"' class='editdeviceconfig'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>"
	}
	
	//IR Output changes
	var irOutput= "";
	// minimote config end
	if(device['deviceType'] == "Z_WAVE_AV_BLASTER"){
		params += "&device.modelNumber=" + device["modelnumber"] + "&device.batteryStatus=" + device["batterylevel"] ;
		modelEditSection  = "<a title='Edit Model' href='toAVBlasterModelNumber.action?"+params+"' class='editlink'> <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";
		
		//IR Output changes
		irOutput = "<a title='IROutput Port Configuration' popuptitle='IR Output Port Configuration' href='toIROutputValue.action?"+params+"' class='editlink' popupheight='100' popupwidth='250'> <img class='actionimage' src='../resources/images/adminReboot.png'/> </a>";
	}
	
	//sumit: Unsupported Device Type
	var unsupportedDeviceSection = "";
	if(device['deviceType'] == "UNSUPPORTED"){
		var deviceModel = device["modelnumber"];
		var classInfo = deviceModel.split("$");
		var generiClass = classInfo[0];
		var specificClass = classInfo[1];

		var deviceNode = device["deviceId"];
		var nodeInfo = deviceNode.split("-");
		var nodeId = nodeInfo[1]; 
		//alert(deviceModel);
		unsupportedDeviceSection = '<a title="View Device Detail" onClick="javascript:showResultAlert('
				+"'Node ID: "+nodeId+"<br>Generic Class: "+generiClass+"<br>Specific Class: "+specificClass+"'"+')">' 
				+" <img class='actionimage' src='../resources/images/editmodel.png'/> </a>";


		contentDiv.append($("<ul class='deviceeditul'><ul>")
				.append("<li class='deviceeditli'><span>" + device['deviceName'] + "</span></li>")
				.append("<li class='deviceeditli'><span>" + formatMessage(device['deviceType']) + "</span></li>")
				.append("<li class='deviceeditli'><span>" + device['deviceLocation'] + "</span></li>")
				.append("<li class='deviceeditli'><span>" + removeSection + unsupportedDeviceSection + SceneControlConfiguration + networkStatus + DiscoverVRVUnit + HAIFUnit + IndoorUnits+ resetFilter+ "</span></li>")
);
	}else{
	
	contentDiv.append($("<ul class='deviceeditul'><ul>")
					.append("<li class='deviceeditli'><span>" + device['deviceName'] + "</span></li>")
					.append("<li class='deviceeditli'><span>" + formatMessage(device['deviceType']) + "</span></li>")
					.append("<li class='deviceeditli'><span>" + device['deviceLocation'] + "</span></li>")
//vibhu start: hiding AlertSection temporarily, until it is iMVG is ready
					.append("<li class='deviceeditli'><span>" + editSection + removeSection + AlertSection + nupdateSection + modelEditSection  + noMotionConfiguration  + cameraOrientationSection+ LCDRemoteConfiguration + switchTypeSection+ configureRGB +configuresiren+configureusermode+minimoteconfig+confiDimmerType+SceneControlConfiguration+DiscoverVRVUnit + HAIFUnit+IndoorUnits+resetFilter+WallmoteConfiguration+keyfobeConfiguration+ irOutput + virtualSwitchConfiguration + "</span></li>")
//vibhu end
			);
	}
	
	
	}//parishod start-added else part.
	else{	
		var modelEditSection = "";
	if(device['deviceType'] == "Z_WAVE_AC_EXTENDER"){
		
		modelEditSection  = "<a title='Either master controller is in discovery/removal mode or offline.' ?'> <img class='actionimagedisabled' src='../resources/images/editmodel.png'/> </a>";
	}
	if(device['deviceType'] == "Z_WAVE_MOTOR_CONTROLLER"){
		modelEditSection  = "<a title='Either master controller is in discovery/removal mode or offline.' ?'> <img class='actionimagedisabled' src='../resources/images/editmodel.png'/> </a>";
	}
	//bhavya start
	if(modelnumber == "HMD"){
		 if($.trim(gateWays[gateWayId]['featuresEnabled']) == 1)
			 {
		modelEditSection  = "<a title='Either master controller is in discovery/removal mode or offline.' ?'> <img class='actionimagedisabled' src='../resources/images/editmodel.png'/> </a>";
	
			 }
	}

	//bhavya end
	//sumit start: Camera Orientation Configuration
	var cameraOrientationSection = "";
	if(device['deviceType'] == "IP_CAMERA"){
		cameraOrientationSection = "<a title='Either master controller is in discovery/removal mode or offline.'?> <img class='actionimagedisabled' src='../resources/images/editmodel.png'/> </a>";
	}
	
	//sumit start: No Motion Detection for X hours for PIR sensor
	var noMotionConfiguration ="";
	if(device['deviceType'] == "Z_WAVE_PIR_SENSOR" || device['deviceType'] == "Z_WAVE_MULTI_SENSOR"){
		noMotionConfiguration = "<a title='Either master controller is in discovery/removal mode or offline.'?'> <img class='actionimagedisabled' src='../resources/images/configurenomotion.png'/> </a>";
	}
	var LCDRemoteConfiguration ="";
	if(device['deviceType'] == "Z_WAVE_LCD_REMOTE"){
		LCDRemoteConfiguration = "<a title='Either master controller is in discovery/removal mode or offline.'?> <img class='actionimagedisabled' src='../resources/images/editmodel.png'/> </a>";
	}
	
	//Apoorva
	var SceneControlConfiguration = "";
	if(device['deviceType'] == "DEV_ZWAVE_SCENE_CONTROLLER"){
		SceneControlConfiguration = "<a title='Either master controller is in discovery/removal mode or offline.'?> <img class='actionimagedisabled' src='../resources/images/editmodel.png'/> </a>";
		
		
	}
	
	
	//Diaken VIA
	var DiscoverVRVUnit="";
	if(device['deviceType'] == "VIA"){
		DiscoverVRVUnit = "<a title='Either master controller is in discovery/removal mode or offline.'?> <img class='actionimagedisabled' src='../resources/images/editmodel.png'/> </a>";
		
	}
	
	//Diaken
	var HAIFUnit="";
	if(device['deviceType'] == "HA_IF"){
		HAIFUnit = "<a title='Either master controller is in discovery/removal mode or offline.'?> <img class='actionimagedisabled' src='../resources/images/editmodel.png'/> </a>";
	}
	
	var editSection = "<a title='Either master controller is in discovery/removal mode or offline.' href='javascript:alert('Either master controller is in discovery mode or offline.')?'> <img class='actionimagedisabled' src='../resources/images/edit.png'/> </a>";
	var AlertSection = "<a title='Either master controller is in discovery/removal mode or offline.' href='javascript:alert('Either master controller is in discovery mode or offline.')?'> <img class='actionimagedisabled' src='../resources/images/configurealarms.png'/> </a>";
	//sumit: TO CHECK RULE COUNT BEFORE DELETING A DEVICE
	//var removeSection = "<a title='Remove Device' href='removeDevice.action?"+params+"' class='removedevicelink' title='Remove Device " + device['deviceName'] + "'> <img src='../resources/images/delete.jpg'/> </a>";
	var removeSection = "<a title='Either master controller is in discovery/removal mode or offline.' href='javascript:alert('Either master controller is in discovery mode or offline.')?'> <img class='actionimagedisabled' src='../resources/images/delete.png'/> </a>";
	//vibhu start
	var nupdateSection = "<a title='Either master controller is in discovery/removal mode or offline.' href='javascript:alert('Either master controller is in discovery mode or offline.')'?> <img class='actionimagedisabled' src='../resources/images/nupdate.png'/> </a>";
	//vibhu end	
	
	//sumit start: 
	var switchTypeSection = "";
	
	//IR OUTPUT
	var irOutput ="";
	
	//Virtual Switch
	var virtualSwitchConfiguration= "";
	if(device['deviceType'] == "Z_WAVE_SWITCH" || device['deviceType'] == "Z_WAVE_DIMMER"){
		switchTypeSection = "<a title='Either master controller is in discovery/removal mode or offline.'?'> <img class='actionimagedisabled' src='../resources/images/configurenomotion.png'/> </a>";
	}
	
	//IR OUTPUT
	if(device['deviceType'] == "Z_WAVE_AV_BLASTER"){
		irOutput = "<a title='Either master controller is in discovery/removal mode or offline.'?'> <img class='actionimagedisabled' src='../resources/images/configurenomotion.png'/> </a>";
	}
	
	//Virtual Switch
	if(device['deviceType'] == "DEV_ZWAVE_VIRTUAL_SW"){
		switchTypeSection = "<a title='Either master controller is in discovery/removal mode or offline.'?'> <img class='actionimagedisabled' src='../resources/images/configurenomotion.png'/> </a>";
		virtualSwitchConfiguration = "<a title='Either master controller is in discovery/removal mode or offline.'?'> <img class='actionimagedisabled' src='../resources/images/configurenomotion.png'/> </a>";
	}
	
	var networkStatus="<a title='Either master controller is in discovery/removal mode or offline.' href='javascript:alert('Either master controller is in discovery mode or offline.')?'> <img class='actionimagedisabled' src='../resources/images/idle.png'/> </a>";
	
	//sumit: Unsupported Device Type
	var unsupportedDeviceSection = "";
	if(device['deviceType'] == "UNSUPPORTED"){
		unsupportedDeviceSection = "<a title='Either master controller is in discovery/removal mode or offline.' href='javascript:alert('Either master controller is in discovery mode or offline.')'?> <img class='actionimagedisabled' src='../resources/images/editmodel.png'/> </a>";
		contentDiv.append($("<ul class='deviceeditul'><ul>")
				.append("<li class='deviceeditli'><span>" + device['deviceName'] + "</span></li>")
				.append("<li class='deviceeditli'><span>" + formatMessage(device['deviceType']) + "</span></li>")
				.append("<li class='deviceeditli'><span>" + device['deviceLocation'] + "</span></li>")
				.append("<li class='deviceeditli'><span>" + removeSection + unsupportedDeviceSection +"</span></li>")
				);
	}else{
		var gateWayVersion = gateWays[gateWayId]['gateWayVersion'];
		//alert(gateWayVersion);
		if (gateWayVersion==="IMSPRIME_1.0.2_15_02_2018") 
		{
			alert("True");
			contentDiv.append($("<ul class='deviceeditul'><ul>")
					.append("<li class='deviceeditli'><span>" + device['deviceName'] + "</span></li>")
					.append("<li class='deviceeditli'><span>" + formatMessage(device['deviceType']) + "</span></li>")
					.append("<li class='deviceeditli'><span>" + device['deviceLocation'] + "</span></li>")
		//vibhu start: hiding AlertSection temporarily, until it is iMVG is ready
					.append("<li class='deviceeditli'><span>" + editSection + removeSection + AlertSection + nupdateSection + modelEditSection  + noMotionConfiguration  + cameraOrientationSection + LCDRemoteConfiguration + switchTypeSection + configureRGB +confiDimmerType+SceneControlConfiguration+networkStatus+ irOutput + virtualSwitchConfiguration +"</span></li>")
		//vibhu end
					);
		} else
		{
			
			contentDiv.append($("<ul class='deviceeditul'><ul>")
					.append("<li class='deviceeditli'><span>" + device['deviceName'] + "</span></li>")
					.append("<li class='deviceeditli'><span>" + formatMessage(device['deviceType']) + "</span></li>")
					.append("<li class='deviceeditli'><span>" + device['deviceLocation'] + "</span></li>")
		//vibhu start: hiding AlertSection temporarily, until it is iMVG is ready
					.append("<li class='deviceeditli'><span>" + editSection + removeSection + AlertSection + nupdateSection + modelEditSection  + noMotionConfiguration  + cameraOrientationSection + LCDRemoteConfiguration + switchTypeSection + configureRGB +confiDimmerType+SceneControlConfiguration+networkStatus+ irOutput + virtualSwitchConfiguration +"</span></li>")
		//vibhu end
					);
		}
		
	
	}
	
	
	}//parishod end.
		
	//deviceLi.append(hederdDiv);
	deviceLi.append(contentDiv);
	
	return deviceLi;
	}
	}
};

var findProperImageForBatteryLevel = function(batteryLevel){
	if(batteryLevel < 21){
		return "/imonitor/resources/images/Batt20.jpg";
	}
	if(batteryLevel < 41){
		return "/imonitor/resources/images/Batt40.jpg";
	}
	if(batteryLevel < 61){
		return "/imonitor/resources/images/Batt60.jpg";
	}
	if(batteryLevel < 81){
		return "/imonitor/resources/images/Batt80.jpg";
	}
	if(batteryLevel < 101){
		return "/imonitor/resources/images/Batt100.jpg";
	}
	return "/imonitor/resources/images/Batt20.jpg";
};
this.changeHtml = function(classification,wiseType,image){
	if(classification==DEVICE_TYPE_WISE)
	{
	$("#deviceListUl").html("");
	this.generateHtml(classification, wiseType);
	}
	else if(classification == "MAP")
	{
		
		this.generateHtmlOfDevicePerLocationForMap(classification, wiseType,image);
	}
	else
	{
	$("#deviceListUl").html("");
	$("#LocationInput").show();
	$("#LocationInput").html(wiseType);
	if($("#radioLable").attr('aria-pressed', 'true'))
	{
		$("#radioLable").attr('aria-pressed', 'false');
        	$("#radioLable").removeClass('ui-state-active');
	}
//sumit start: Re-Ordering Of Devices
	if($("#deviceListUl").hasClass('ActivateOrdering')){
		$("#deviceListUl" ).sortable("enable");
		$("#deviceListUl" ).sortable({
			revert : true, 
			containment : 'parent',
			tolerance : 'pointer',
			cursor : 'pointer',
			opacity : 0.80,
			update : function(){
				//positionIndexes = $("#deviceListUl").sortable("serialize");
				var data = new Array();
				data = $( "#deviceListUl" ).sortable('toArray');
				setOrderForDevice(data);
			}
		});
	}
//sumit end: Re-Ordering Of Devices
	this.generateHtmlForLocation(classification, wiseType);
	}
};
this.changeHtmlForDashboard = function(classification,wiseType,image){
	if(classification==DEVICE_TYPE_WISE)
	{
	$("#deviceListUl").html("");
	this.generateHtml(classification, wiseType);
	}
	else if(classification == "MAP")
	{
		
		this.generateHtmlOfDevicePerLocationForENMGMT(classification, wiseType,image);
	}
	else
	{
	$("#deviceListUl").html("");
	$("#LocationInput").show();
	$("#LocationInput").html(wiseType);
	if($("#radioLable").attr('aria-pressed', 'true'))
	{
		$("#radioLable").attr('aria-pressed', 'false');
        	$("#radioLable").removeClass('ui-state-active');
	}
//sumit start: Re-Ordering Of Devices
	if($("#deviceListUl").hasClass('ActivateOrdering')){
		$("#deviceListUl" ).sortable("enable");
		$("#deviceListUl" ).sortable({
			revert : true, 
			containment : 'parent',
			tolerance : 'pointer',
			cursor : 'pointer',
			opacity : 0.80,
			update : function(){
				//positionIndexes = $("#deviceListUl").sortable("serialize");
				var data = new Array();
				data = $( "#deviceListUl" ).sortable('toArray');
				setOrderForDevice(data);
			}
		});
	}
//sumit end: Re-Ordering Of Devices
	this.generateHtmlForLocation(classification, wiseType);
	}
};



this.generateLocationwiseLists = function(Path)
{
	var pressed=$("#radioLable").attr('aria-pressed');
	if(lastSelectedWise=="" || pressed == 'true')
	{
		var locationTypes = new Array();
		var deviceDetails = new Array();
		var devicelist = new Array();
	 
		for(var gateWayId in gateWays){

			var locationTypesTemp = gateWays[gateWayId]["devices"]["locations"];
			for(var i in locationTypesTemp)
			{
			
				locationTypes.push(i);
			}
			locationTypes.sort(function (a, b){return ((a < b) ? -1 : ((a > b) ? 1 : 0));});
		}
		
		for(var gateWayId in gateWays){
			//var locationTypesTemp = gateWays[gateWayId]["devices"]["locations"];
			
			var TypesTemp = gateWays[gateWayId]["devices"]["locations"];
			$.extend(deviceDetails, TypesTemp);
			/*for(var i in locationTypesTemp){
				locationTypes.push(i);
			}
			locationTypes.sort(function (a, b){return ((a < b) ? -1 : ((a > b) ? 1 : 0));});*/
		}
		
		var locationhtml = "<span id='LocationInput' class='LocationInput' type='text' readonly='readonly'></span>";	
		$("#headersection").append(locationhtml);
		
		var locationOptionHtml = "<option value=''>Select a location</option>";
	
		$("#deviceListUl").html("");
		var deviceUl = $("#deviceListUl");
		deviceUl.html('');

		var deviceTable=$("<table id='locationwithdevice'></table>");
		
		
		for(var i in locationTypes)
		{
			
			var locationType = locationTypes[i];
			
			var newVar=$('<tr class="roomdisplaysection">');
			
			newVar.append($('<td class="locationName">').append(locationType));
			
			for(var key in deviceDetails)
			{
				if(locationType == key)
				{
					var device = [];
					 device = deviceDetails[key];
					
					
					var nwTr = $('<tr >');		
			        var neTd = $('<td >');
					neTd.append(nwTr);
					
					for(var dKey in devices)
					{
						var devic = devices[dKey];
						device.push(devic);
						var deviceType = $.trim(device["deviceType"]);
						// Allowing only switches,motorcontroller and dimmers to display
						if(deviceType == "Z_WAVE_SWITCH" || deviceType == "Z_WAVE_MOTOR_CONTROLLER"  || deviceType=="Z_WAVE_DIMMER"){
						var deviceLi = this.generateDevicesTableColumn(device,false);
						nwTr.append(deviceLi);
						}
						
					}
					
				
					
					newVar.append(neTd);
					
					
				}
				
				deviceTable.append(newVar);
				
				
			}
			deviceTable.append($("<tr id='roombisector' style='background-color: black;width: 100%;'></tr>"));
		    $('#deviceListUl').append(deviceTable);
		
	}

	}
};

this.generateDevicesTableColumn = function(device,isForChangeDevice){
	
	var deviceType = $.trim(device["deviceType"]);
	var deviceIcon = $.trim(device["iconfile"]);
	var enableDeviceListing = $.trim(device["enableList"]);
	var devicename = device["deviceName"];
	
	
	if(deviceType == "Z_WAVE_SWITCH" || deviceType == "Z_WAVE_MOTOR_CONTROLLER"  || deviceType=="Z_WAVE_DIMMER")
	{
			
	var isGateWayOfline = gateWays[device["gatewayid"]]['status'] != "Online";
	var isDeviceOffline = device["alertparam"] != "DEVICE_DOWN" ? false : true;
	var switchType = device["switchType"];
	var isOffline = isDeviceOffline || isGateWayOfline;
	var detailSection = $("<td class='deviceRow'>");	
	var contentDiv = $("<div></div>").addClass('widget-content');
	var deviceNameSection = $("<div class='detailsection'></div>");
	deviceNameSection.append(device["deviceName"]);
	
	contentDiv.append(deviceNameSection);	
	var offLineIdleImage = "<img src='/imonitor/resources/images/idleswitch.png' class='devicecontrolimage idleswitch' title='inactive'></img>";
	if(deviceType == "Z_WAVE_SWITCH")
	{
		var imgContent = "";
		var imgContent1 = "";
		var isOff = $.trim(device["commandparam"]) == 0;
		
		
		if(isOffline){
			imgContent += offLineIdleImage;
		} 
		else if(isOff)
		{		
		    imgContent += "<img src='/imonitor/resources/images/offnormalswitch.png' class='switchon devicecontrolimage' title='click to switch on'></img>";
		}else{
			
		    imgContent += "<img src='/imonitor/resources/images/onswitchnormal.png' class='switchoff devicecontrolimage' title='click to switch off'></img>";
		}
		
		
		contentDiv.append($("<a></a>")
				.attr('href',isOffline ? '#' :'switchonff.action')
				.addClass(isOffline ? '' : 'updatedevicelink')
				.attr("gateWayId", device["gatewayid"])
				.attr("deviceId", device["deviceId"])
				.attr("switchType",device["switchType"])
				.attr("commandParam", isOff ? 1 : 0)
				.html(imgContent)

				);
		
	}else if((deviceType == "Z_WAVE_MOTOR_CONTROLLER") || (deviceType == "Z_WAVE_DIMMER")){
		var abc=device["commandparam"];
		contentDiv.append($("<div class='slider' id='slider' style='width: 99px;margin-top: 0px;'></div>").slider({//<input class='inputfield' type='text' readonly='readonly' value="+device["commandparam"]+">
			range: "min",
			min: 0,
			max: 100,
			step: 25,
			value: abc,
			toolTip: true,
			slide: function(event, ui) 
				{
					/*var $this_text = jQuery(this).find("input");
					$this_text.val(ui.value);*/
                			          			
				},

		   	stop: function( event,ui )
			{
		   		if(device["commandparam"]!= ui.value)
				{
				updatedevicelink(ui.value,device["gatewayid"],device["deviceId"],make);
				}	
			}
			
			
    		
			}));
		
		contentDiv.append($("<div class='close' style='margin-top: -14px;float: left;margin-left: 155px;font-weight: 300;'>"+formatMessage("msg.ui.close")+"</div>"));	
		contentDiv.append($("<div class='open' style='margin-top: -15px;float: right;margin-right: 159px;font-weight: 300;'>"+formatMessage("msg.ui.open")+"</div>"));
		
		
	}
	
	
	
	var isOff = $.trim(device["commandparam"]) == 0;
		
	//detailSection.append("<img src='"+deviceIcon+"' class='deviceiconinlocation'></img>"+ deviceSection);
	detailSection.append(contentDiv);
	return detailSection;
	
	
	}
};

};

var ImvgDetals = function(){
	 var imgDetails = {};
	 var imvgIds = [];
		this.addImageDetails = function(id,filePath,timeStamp,alertdetails){
			var index = $.inArray(id*1,imvgIds);
				if(index>-1){
					return;
				}
				
					imvgIds.push(id);
					imgDetails [id] = {};
					imgDetails [id]["filePath"] =filePath;
					imgDetails [id]["timeStamp"] = timeStamp;
					imgDetails [id]["alertdetails"] = alertdetails;
					
			};
		this.printDetails=function(){
		};
			
			this.showbuttons = function () {
				if(imvgIds.length ==1){
					$("#nextimagebutton").attr('src', " ");
					$("#previousimagebutton").attr('src', " ");
				}
			};
		this.showPreviousImage = function(currentId){
			var index = $.inArray(currentId*1,imvgIds);
			var nextImageId = imvgIds[index-1];
			this.showImage(nextImageId);
		};
		
		this.showImage = function(imageId){
				var nextImageFilePath = imgDetails[imageId]["filePath"];
				var time = imgDetails[imageId]["timeStamp"];
				var abc= time.split(".");
				var details = imgDetails[imageId]["alertdetails"];
				$("#attachmentimageid").attr('src',nextImageFilePath);
				$("#downloadLink").attr('href',nextImageFilePath);
				$("#timeStamp").text(abc[0]);

				$("#alertdetails").text(formatMessage("IMAGE_FTP_SUCCESS"));

				$(".nextimagelink, .previousimagelink").attr("currentimageid", imageId);
				var index = $.inArray(imageId*1,imvgIds);
				$("#previousimagebutton").show();
				$("#nextimagebutton").show();
				
				if(index < 1){
					$("#previousimagebutton").hide();
				}
				if(index == (imvgIds.length - 1)){
					$("#nextimagebutton").hide();
				}
		};
		
		this.showNextImage = function(currentId){
			var index = $.inArray(currentId*1,imvgIds);
			var nextImageId = imvgIds[index + 1];
			this.showImage(nextImageId);
		};

		this.sortIds = function(){
			imvgIds.sort(function(a,b){
				return a-b;
			})
		};
		
	};
	
	var ImvgDetalsforBackup = function(){
		
		 var imgDetails = {};
		 var imvgIds = [];
			this.addImageDetails = function(filePath){
				
				
				imgDetails["filePath"] =filePath;	
						
					//	imgDetails [id]["filePath"] =filePath;
						//alert(imgDetails [id]["timeStamp"]);
				};
			this.printDetails=function(){
			};
				
			
			
			this.showImage = function(imageId){
					var nextImageFilePath = imgDetails["filePath"];
				
					$("#attachmentimageid").attr('src',nextImageFilePath);
					$("#downloadLink").attr('href',nextImageFilePath);
			
//					$("#alertdetails").text(details); parishod commented changed image upload success to Upload Success
					$("#alertdetails").text(formatMessage("IMAGE_FTP_SUCCESS"));

					
					
					
			};
			
			

			this.sortIds = function(){
				imvgIds.sort(function(a,b){
					return a-b;
				})
			};
			
		};

$(document).ready(function() {
	
	
	

	$('.ajaxlinkalert').xpAjaxLink({target: 'alerthedersection'});
	
	$('.ajaxlinkalert').live('mouseover', function(){
		$(this).xpAjaxLink({target: 'alertbodysection'});
	});
	
	$('.ajaxinlineform').live('submit', function() {
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
	$('.ajaxinlinefileuploadform').live('submit', function() {
		function handleSuccess(){
			var data = $(this).contents().find('body').html();
			 $('#contentsection').html(data);
		}
		$.covertToAjaxFileUpload(this,{successhandler:handleSuccess});
		return true;
	});
	
	
	$('.imvgalertlink').live('click', function(){
		var url = $(this).attr('href');
		var deviceId = $(this).attr('deviceId');
		var alertFromImvgId = $(this).attr('alertFromImvgId');
		var params = {"alertFromImvgId":alertFromImvgId, "deviceId":deviceId};
		$.ajax({
			  url: url,
			  data:params,
			  success: function(data){
				$('#alertImage').html(data);
				$('#alertImage').dialog('open');
				//$(".ui-dialog-titlebar").hide();

			  }
			});
		return false;
	});
	
	
	$( "#alertImage" ).dialog({
		height: 350,
		width: 500,
		modal: true,
		autoOpen:false
	});
	
	$( ".network" ).dialog({
		height: 350,
		width: 500,
		modal: true,
		autoOpen:false,
		resizable: false
		
	});
	

	
	
});