<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
String applicationName = request.getContextPath();
%>
<span style="display:none;">
<div style="color: red;" id="messageSection">
	<s:property value="#session.message" />
</div> </span>
<%ActionContext.getContext().getSession().remove("message"); %>
<style>
	#gatewaySection{
	    left: 530px;
	    line-height: 26px;
	    position: absolute;
	    float:right;
	    top: 3px;
	}
	.gatewayidsection {
	left: 255px;
	}
	
	.gatewayimgsection img {
		left: 250px;	
		}
		
		
		.cmb {
    display: block;
    margin: 0 auto;
}
		
	/* 	.ui-dialog-titlebar-close{
    display: none;
} */
</style>
<script type="text/javascript">
var userDetails = new UserDetails();
var DEVICE_TYPE_WISE = "DEVICE_TYPE_WISE";
var LOCATION_WISE = "LOCATION_WISE";



$(document).ready(function() {
	
	var handleGateWay = function(gWay){
		var gateWay = $(gWay);
		var macId = gateWay.find("macid").text();
				var status = gateWay.find("status").text();
				var curMode = gateWay.find("currentMode").text();
                var featuresEnabled = gateWay.find("featuresEnabled").text();
                var gateWayVersion = gateWay.find("gateWayVersion").text();
                var Latestversion = gateWay.find("Latestversion").text();
                var FirmwareId = gateWay.find("FirmwareId").text();
                //3gp
                var GatewayName = gateWay.find("gatewayName").text();
		userDetails.addGateWay(macId);
		userDetails.setGateWayStatus(macId,status,featuresEnabled,Latestversion,FirmwareId,gateWayVersion,GatewayName);
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
	

		userDetails.generateGateWayHtml();
		userDetails.generateGateWayDeviceDiscoveryHtml();
		userDetails.generateHtmlForEdit();
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
	
	$(".updategatewaylink").die('click');
	$(".updategatewaylinkup").die('click');
	$(".removeall").die('click');
    $(".abortMode").die('click');
	
	/*$(".removeall").live("click", function(){
        var clickedSection = $(this);
		var discoverHtml = clickedSection.html();
		var toDisplay = '<div><p style="color: red;">Do you want to go back to factory defaults?</p>';
        var url = $(this).attr('href');
        $(this).attr('href',"javascript:alert('Either gateway is in removal mode or offline !!!')");
		var gateWayId = $(this).attr('gatewayid');
		var params = {"gateWay.macId":gateWayId};
		$("#confirm").dialog("destroy");
		$("#confirm").html(toDisplay);
		$("#confirm").dialog({
					stackfix: true,
					modal: true,
					buttons: {
						Ok: function() {
							$(this).dialog('close');                                            
							var params={"gateWay.macId":gateWayId};
							var targeturl = url;
							$.ajax({
                                  url: url,
                                  data:params,
                                  dataType: 'xml',
                                  success: function(xml){
	                                    clickedSection.html("");
	                                    $(xml).find("gateway").each(function() {
				                              var gateWay = $(this);
				                              var macId = gateWay.find("macid").text();
				                              var status = gateWay.find("status").text();
				                              userDetails.setGateWayStatus(macId, status);
				                              clickedSection.html(discoverHtml);
                                        });
                                  }
		                   	});
 						},
						Cancel: function() {
							$(this).dialog('close');
						}
 					}
				}); 
		return false;
 	});*/
	
	$(".updategatewaylink").live('click', function(){
		$("#discoverydialog").dialog('close');
		$("#discoverydialog").dialog('destroy');
		$("#confirm1").dialog("destroy");
		var para1 = '<div><input type="radio" name="devicetype" value="ZWave Device" checked style="color: black;">'+"Do you want to add Zwave Devices?"+"<br>";
		var para10 = '<div><input type="radio" name="devicetype" value="VIA" style="color: red;">'+"<s:text name='msg.ui.setup.0026'/>";
		var para = '<div><p style="color: black;">'+"<s:text name='msg.ui.setup.0003'/>"+'</p>';	
		var clickedSection = $(this);
		var discoverHtml = clickedSection.html();
		var temp=false;
		
		$("#discoverydialog").html(para1+para10);		
   		$("#discoverydialog").dialog({
				stackfix: true,
				position: [500,250],
				modal: true,
				buttons: {
					<s:text name='Yes' />: function() {
						var checkedDevice=$('input[name=devicetype]:radio:checked').val(); //Apoorva
						
							$(this).dialog('close');
						
						$("#discoverydialog").dialog("destroy");
						$("#discoverydialog").dialog("close");
						
						
				
							if (checkedDevice === "VIA") 
						{
				 		 	var device_name="VIA";
							var device_model="NULL";
							var device_serial_no="NULL";
							  $(this).dialog('close');
							temp=true;
						var gateWayId = clickedSection.attr('gatewayid');
						var params = {"gateWay.macId":gateWayId,"lowPower":temp,"checkedDevice":device_name,"deviceModel":device_model,"deviceSerial":device_serial_no};
						$.ajax({
							url: "setDeviceDiscoveryMode.action",
							data:params,
							dataType: 'xml',
							success: function(xml){
								clickedSection.html("");
								$(xml).find("gateway").each(function() {
									var gateWay = $(this);
									var macId = gateWay.find("macid").text();
									var status = gateWay.find("status").text();
									userDetails.setGateWayStatus(macId, status);
									clickedSection.html(discoverHtml);
								});
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
						}
							
							//Else Start 
						else{
	
							var device_model="NULL";
								var device_serial_no="NULL";
							$("#confirm1").html(para);
							$("#confirm1").dialog({
								stackfix: true,
								position: [500,250],
								modal: true,
							buttons: {
								<s:text name='Yes' />: function() {
								$(this).dialog('close');
								temp=true;
							var gateWayId = clickedSection.attr('gatewayid');
							var params = {"gateWay.macId":gateWayId,"lowPower":temp,"checkedDevice":checkedDevice,"deviceModel":device_model,"deviceSerial":device_serial_no};
							$.ajax({
								url: url,
								data:params,
								dataType: 'xml',
								success: function(xml){
									clickedSection.html("");
									$(xml).find("gateway").each(function() {
										var gateWay = $(this);
										var macId = gateWay.find("macid").text();
										var status = gateWay.find("status").text();
										userDetails.setGateWayStatus(macId, status);
										clickedSection.html(discoverHtml);
									});
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
								<s:text name='No' />: function(){
								$(this).dialog('close');
								
							var gateWayId = clickedSection.attr('gatewayid');
							var params = {"gateWay.macId":gateWayId,"lowPower":temp,"checkedDevice":checkedDevice,"deviceModel":device_model,"deviceSerial":device_serial_no};
							$.ajax({
								url: url,
								data:params,
								dataType: 'xml',
								success: function(xml){
									clickedSection.html("");
									$(xml).find("gateway").each(function() {
										var gateWay = $(this);
										var macId = gateWay.find("macid").text();
										var status = gateWay.find("status").text();
										userDetails.setGateWayStatus(macId, status);
										clickedSection.html(discoverHtml);
									});
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
								}	
							}
								
						});		
						}
						
						
						
						//Else End
						clickedSection.html('<img src="../resources/images/loading.gif" />');
						var url = clickedSection.attr('href');
						clickedSection.removeClass('updategatewaylink');
						clickedSection.attr('href',"javascript:alert('"+"<s:text name='msg.ui.setup.0001' />"+"')");
				},
					<s:text name='No' />: function(){
						$(this).dialog('close');
						$("#discoverydialog").dialog("destroy");
					}
				},
   		close: function(ev, ui) { 
   			
   			$("#discoverydialog").dialog("destroy");
   		}
			});
		

		return false;
	});
	//bhavya start	
	$(".updategatewaylinkup").live("click", function(){
        var clickedSection = $(this);
		var discoverHtml = clickedSection.html();
		var toDisplay = '<div><p style="color: red;">'+"<s:text name='setup.devices.msg.0005' />"+'</p>';
        var url = $(this).attr('href');
        $(this).attr('href',"javascript:alert('"+"<s:text name='msg.ui.setup.0002' />"+"')");
		var gateWayId = $(this).attr('gatewayid');
		var params = {"gateWay.macId":gateWayId};
		$("#confirm2").dialog("destroy");
		$("#confirm2").html(toDisplay);
		$("#confirm2").dialog({
					stackfix: true,
					modal: true,
					buttons: {
						<s:text name='Yes' />: function() {
							$(this).dialog('close');                                            
							var params={"gateWay.macId":gateWayId};
							var targeturl = url;
							$.ajax({
                                  url: url,
                                  data:params,
                                  dataType: 'xml',
                                  success: function(xml){
	                                   					alert("Caution!! Press the learn button on the intended device to be removed");
	                                    clickedSection.html("");
	                                    $(xml).find("gateway").each(function() {
				                              var gateWay = $(this);
				                              var macId = gateWay.find("macid").text();
				                              var status = gateWay.find("status").text();
				                              userDetails.setGateWayStatus(macId, status);
				                              clickedSection.html(discoverHtml);
                                        });
                                  }
		                   	});
 						},
 						<s:text name='No' />: function() {
							$(this).dialog('close');
						}
 					},
 					close: function(ev,ui){
 						$(this).dialog('destroy');
 					}
				}); 
		return false;
 	});
	
	$(".abortMode").live("click", function(){
        var clickedSection = $(this);
		var discoverHtml = clickedSection.html();
		var toDisplay = '<div><p style="color: red;">'+"<s:text name='msg.ui.setup.0006' />"+'</p>';
		
        var url = $(this).attr('href');
        $(this).attr('href',"javascript:alert('"+"<s:text name='msg.ui.setup.0002' />"+"')");
		var gateWayId = $(this).attr('gatewayid');
		var stat = $(this).attr('status');
		if(stat == "Device Discovery Mode")
		{
			toDisplay += '<div><p style="color: red;">'+"<s:text name='Device Discovery mode' />"+'</p>'; //$(this).attr('status');
		}else{
			toDisplay += '<div><p style="color: red;">'+"<s:text name='Device Removal mode' />"+'</p>';
		}
		var params = {"gateWay.macId":gateWayId,"status":stat};
		$("#confirm3").dialog("destroy");
		$("#confirm3").html(toDisplay);
		$("#confirm3").dialog({
					stackfix: true,
					modal: true,
					buttons: {
						<s:text name='Yes' />: function() {
							$(this).dialog('close');                                            
							var params={"gateWay.macId":gateWayId,"status":stat};
							var targeturl = url;
							$.ajax({
                                  url: url,
                                  data:params,
                                  dataType: 'xml',
                                  success: function(xml){
	                                    clickedSection.html("");
	                                    $(xml).find("gateway").each(function() {
				                              var gateWay = $(this);
				                              var macId = gateWay.find("macid").text();
				                              var status = gateWay.find("status").text();
				                              userDetails.setGateWayStatus(macId, status);
				                              clickedSection.html(discoverHtml);
                                        });
                                  }
		                   	});
 						},
 						<s:text name='No' />: function() {
							$(this).dialog('close');
						}
 					}
				}); 
		return false;
 	});
	$(".gatewaychange").live("change", function(){
		userDetails.showGateWayStaus($(this).val());
	});
});
</script>

<div class="pageTitle" id="pageTitle">
	<!--<span class='titlespandevices'>&nbsp;<s:text name='general.devices' /></span>-->
	<div class="pagetitleline"></div>
	<div id="gatewaySection">
		
	</div>
</div>
<div id="deviceSection" class="deviceSectionInDevice">
	<div class="devicegroup">
		<ul class="column" id="deviceListUl"></ul>
	</div>
</div>
<div id ="discoverydialog"></div>
<div id="confirm"></div><div id="confirm1"></div><div id="confirm2"></div><div id="confirm3"></div>
<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
