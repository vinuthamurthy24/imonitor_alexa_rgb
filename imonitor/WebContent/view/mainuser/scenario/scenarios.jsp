<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>

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
<!-- Parishod changed to show the status of gateway -->
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
$(".gatewaychange").live("change", function(){
		userDetails.showGateWayStaus($(this).val());
	});
});
</script>
<!-- parishod end -->

<script>

$(document).ready(function() {

	var publicationgrouptable = $('#listruletable').dataTable({
		"bProcessing": true,
		"bServerSide": true,
		"bDestroy": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"sAjaxSource": "listAskedScenario.action",
		"oLanguage": {
	         "sSearch": "<s:text name='setup.datatables.search' />",
	         "sProcessing": "<s:text name='setup.datatables.processing' />",
	         "sLengthMenu": "<s:text name='setup.datatables.showentries' />",
	         "sInfo": "<s:text name='setup.datatables.info'/>",
	         "sInfoEmpty": "<s:text name='setup.datatables.infoempty'/>",
	         "sEmptyTable": "<s:text name='setup.datatables.emptytable'/>"
	       }
	});
});
</script>
<div class="pageTitle">
	<table>
		<tr>
			<td id="scenarioAdd" >
				<a class='editlink rulenadd' href="toAddScenario.action" popupheight="300" popupwidth="800" popuptitle="<s:text name='setup.scenarios.add.step' />" 
				title="Add a new Scenario"><img src="/imonitor/resources/images/locationadd.png"></a>
			</td>
			<td>
				<a class='editlink rulenadd' href="toAddScenario.action" popupheight="300" popupwidth="800" popuptitle="<s:text name='setup.scenarios.add.step' />" 
				title="Add a new Scenario"><span class='titlespangeneric'><s:text name='setup.scenarios.add' /></span></a>
			</td>
		</tr>
	</table>
	
	<div class="pagetitleline"></div>
	<div id="gatewaySection"></div>
</div>
<table class="display" id="listruletable">
	<thead>
		<tr>
			<th><s:text name="general.name" /></th>
			<th><s:text name="general.description" /></th>
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
			<th><s:text name="general.name" /></th>
			<th><s:text name="general.description" /></th>
			<th><s:text name="setup.datatables.actions" /></th>
		</tr>
	</tfoot>
</table>

<span style="display:none;">
<div style="color: red;" id="messageSection">
	<s:property value="#session.message" />
</div></span>
<%ActionContext.getContext().getSession().remove("message"); %>
