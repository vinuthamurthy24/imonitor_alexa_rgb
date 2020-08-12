<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
<%
String applicationName = request.getContextPath();
%>
<style>
	.dataTables_length {
	width: 30%;
	float: left;
	margin-left: -75px;
	margin-top: 12px;
	}

	.dataTables_info {
margin: 20px 0px 0px 10px;
}
	
</style>
	
<script>
var userDetails = new UserDetails();
var selectedGateWay = "";
$(document).ready(function() {

	
	var publicationgrouptable = $('#listlocationstable').dataTable({
		"bProcessing": true,
		"bServerSide": true,
		"bDestroy": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"sAjaxSource": "listAskedLocations.action",
		"oLanguage": {
	         "sSearch": "<s:text name='setup.datatables.search' />",
	         "sProcessing": "<s:text name='setup.datatables.processing' />",
	         "sLengthMenu": "<s:text name='setup.datatables.showentries' />",
	         "sInfo": "<s:text name='setup.datatables.info'/>",
	         "sInfoEmpty": "<s:text name='setup.datatables.infoempty'/>",
	         "sEmptyTable": "<s:text name='setup.datatables.emptytable'/>"
	       }
	});
	
	
	var handleGateWay = function(gWay){
		var gateWay = $(gWay);
		var macId = gateWay.find("macid").text();
				var status = gateWay.find("status").text();
				var curMode = gateWay.find("currentMode").text();
                var featuresEnabled = gateWay.find("featuresEnabled").text();
                var gateWayVersion = gateWay.find("gateWayVersion").text();
                var Latestversion = gateWay.find("Latestversion").text();
                var FirmwareId = gateWay.find("FirmwareId").text();
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
$(".gatewaychange").live("change", function(){
		userDetails.showGateWayStaus($(this).val());
	});

$(".gatewaychange").live('change',function(){
	var gateWayId = $(this).val();
	selectedGateWay = gateWayId;
});
	
	
	var selectGateWay = $("<select class='gatewaychange' style='float:left;margin-left: 5px;margin-top: 5px;'></select>");

	$("#gatewaySection").append(selectGateWay);
	
});
</script>
<div class="pageTitle">
<!--	<table>
		<tr>
			<td>
				<a class='editlink locationadd' href="toAddLocation.action"  popupheight="450" popupwidth="850" popuptitle="Add Room " 
				title="Add a new a room"><img src="/imonitor/resources/images/locationadd.png"></a>
			</td> 
			<td>
				<a class='editlink locationadd' href="toAddLocation.action"  popupheight="450" popupwidth="850" popuptitle="Add Room " 
				title="Add a new a room"><span class='titlespangeneric'>Add Room</span></a>			
			</td> 
		</tr>
	</table>			
	-->
		<table>
		<tr>
			<td>
				<a class='editroomlink' href="toAddLocation.action"  popupheight="450" popupwidth="850" popuptitle='<s:text name="setup.rooms.add" />'
				title="Add a new a room"><img src="/imonitor/resources/images/locationadd.png"></a>
			</td> 
			<td>
				<a class='editroomlink' href="toAddLocation.action"  popupheight="450" popupwidth="850" popuptitle='<s:text name="setup.rooms.add" />' 
				title="Add a new a room"><span class='titlespangeneric'><s:text name="setup.rooms.add" /></span></a>	

			</td> 
		</tr>
	</table>
	<div class="pagetitleline"></div>
</div>
<!-- 
<div id="gatewaySection" style="width: 40%;float:left;" >
				</div> -->
<table class="display" id="listlocationstable">
	<thead>
		<tr>
			<th><s:text name="setup.datatables.name" /> </th>
			<th><s:text name="setup.datatables.description" /></th>
			<th><s:text name="setup.datatables.actions" /></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="3"><s:text name="setup.datatables.dataloading" /></td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th><s:text name="setup.datatables.name" /> </th>
			<th><s:text name="setup.datatables.description" /></th>
			<th><s:text name="setup.datatables.actions" /></th>
		</tr>
	</tfoot>
</table>

