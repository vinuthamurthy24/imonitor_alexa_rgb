<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
		String applicationName = request.getContextPath();
%>
<html>
	<head>
		<link rel="shortcut icon" href="<%=applicationName %>/resources/images/favicon.ico" type="image/-icon" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/imonitor.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/demo_table.css" />
		<link type="text/css" href="<%=applicationName %>/resources/css/demo_page.css" rel="stylesheet" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/demo_table.css" rel="stylesheet" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/jquery-ui.css" rel="stylesheet" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/jquery-ui-1.7.2.custom.css" rel="stylesheet" />	
		
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.user.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.7.1.js"></script>
		<script src="<%=applicationName %>/resources/js/jquery-1.4.4.min.js" type="text/javascript"></script>
        <script src="<%=applicationName %>/resources/js/jquery.dataTables.js" type="text/javascript"></script>
        <script src="<%=applicationName %>/resources/js/jquery-ui.js" type="text/javascript"></script>
        <script src="<%=applicationName %>/resources/js/jquery.dataTables.columnFilter.js" type="text/javascript"></script>
        <script src="<%=applicationName %>/resources/js/FixedHeader" type="text/javascript"></script>
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
var check=false;	
var alertTable;
var alarmTable;
var alertBackupTable;
var alarmBackupTable;
var endCount = null;
var myRadio="Alerts";
var myRadio1="OldAlerts";
var SelectedAlerts =  [];
var SelectedAlarms =  [];
var SelectedOldAlerts =  [];
var SelectedOldAlarms =  [];
var imvgDetails = new ImvgDetals();
var imvgDetailsbckup = new ImvgDetalsforBackup();

	$('#listalertstable tbody tr ').live('click', function (event) 
	{
		var aData = alertTable.fnGetData(this);
		var iId = aData[0];
		if (event.target.type == 'checkbox') 
		{
			if (jQuery.inArray(iId, SelectedAlerts) == -1 )
			{
				SelectedAlerts[SelectedAlerts.length++] = iId;
			}
			else
			{
				SelectedAlerts = jQuery.grep(SelectedAlerts, function(value) {
				return value != iId;
			} );
		}
		
		$(this).toggleClass('row_selected');
		var sData = $('input:checked', alarmTable.fnGetNodes())
		if(sData.length==10)
		$('#SelectAllAlarms').attr("checked", true);
		else
		$('#SelectAllAlarms').attr("checked", false);
	 }
				
		
	});

	$('#listalarmstable tbody tr ').live('click', function (event) 
	{
		var aData = alarmTable.fnGetData(this);
		var iId = aData[0];
		if (event.target.type == 'checkbox') 
		{
			if (jQuery.inArray(iId, SelectedAlarms) == -1 )
			{
				SelectedAlarms[SelectedAlarms.length++] = iId;
			}
			else
			{
				SelectedAlarms = jQuery.grep(SelectedAlarms, function(value) {
				return value != iId;
				} );
			}
				
		$(this).toggleClass('row_selected');
		
		var sData = $('input:checked', alarmTable.fnGetNodes())
		if(sData.length==10)
		$('#SelectAllAlarms').attr("checked", true);
		else
		$('#SelectAllAlarms').attr("checked", false);
		}						
		});
	
	$('#listalertstablefrombackup tbody tr ').live('click', function (event) 
			{
		
				var aData = alertBackupTable.fnGetData(this);
				var iId = aData[0];
				if (event.target.type == 'checkbox') 
				{
					if (jQuery.inArray(iId, SelectedOldAlerts) == -1 )
					{
						SelectedOldAlerts[SelectedOldAlerts.length++] = iId;
					}
					else
					{
						SelectedOldAlerts = jQuery.grep(SelectedOldAlerts, function(value) {
						return value != iId;
					} );
				}
				
				$(this).toggleClass('row_selected');
				var sData = $('input:checked', alarmBackupTable.fnGetNodes())
				if(sData.length==10)
				$('#SelectAllOldAlarms').attr("checked", true);
				else
				$('#SelectAllOldAlarms').attr("checked", false);
			 }
						
				
			});
	
	$('#listalarmstablefrombackup tbody tr ').live('click', function (event) 
			{
				var aData = alarmBackupTable.fnGetData(this);
				var iId = aData[0];
				if (event.target.type == 'checkbox') 
				{
					if (jQuery.inArray(iId, SelectedOldAlarms) == -1 )
					{
						SelectedOldAlarms[SelectedOldAlarms.length++] = iId;
					}
					else
					{
						SelectedOldAlarms = jQuery.grep(SelectedOldAlarms, function(value) {
						return value != iId;
						} );
					}
						
				$(this).toggleClass('row_selected');
				
				var sData = $('input:checked', alarmBackupTable.fnGetNodes())
				if(sData.length==10)
				$('#SelectAllOldAlarms').attr("checked", true);
				else
				$('#SelectAllOldAlarms').attr("checked", false);
				}						
				});
	
	function backuptableview(){
		$(".hiddenhtml").val("1");			
		$('.requetForPdf').hide();
		$('.requetForOldPdf').show();
        $('.deleteBackUplink').show();
        $('.deletelink').hide();
		$('#AlarmTable').hide();
		$('#AlertTable').hide();
        $('#AlertTableFromBackup').show();
        $("#selctedDevice").val("0");
        $('.backupAlerts').hide();
      //  $("#selctedDevice").change(fnFilterGlobal);	
        $('.newAlert').show();
		InitBackupDataTable();
	};
	
	
function newAlertview(){
	    $(".hiddenhtml").val("0");			
		$('.requetForPdf').show();
		$('.requetForOldPdf').hide();
        $('.deleteBackUplink').hide();
        $('.deletelink').show();
		$('#AlarmTable').hide();
		$('#AlertTable').show();
        $('#AlertTableFromBackup').hide();
        $('#AlarmTableFromBackup').hide();
        $("#selctedDevice").val("0");
      //  $("#selctedDevice").change(fnFilterGlobal);	
        $('.backupAlerts').show();
        $('.newAlert').hide();
        InitDataTable();
	};
	
	function InitBackupDataTable()
	{
		
		check=true;
		/**Intializing Alert DataTable**/
		alertBackupTable = $('#listalertstablefrombackup').dataTable({
		"bServerSide": true,
		"bDestroy": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"sPaginationType": "two_button",
		"sAjaxSource": "listAlertsFromBackup.action",
		
		"aoColumns": [
		              { "bSortable": false, "bVisible": false },
			           { "bSortable": false },
			           { "bSortable": false },
			           { "bSortable": false ,"sSortDataType": "dom-checkbox"},
			           { "bSortable": false, "bVisible":false},
			           { "bSortable": false ,sWidth: '2%',"sSortDataType": "dom-checkbox"},
			         ], 
	  	"sDom": 'ltip',
		"oLanguage": 
		{
			"sLengthMenu": "<s:text name='setup.datatables.showentries' />",
			"sZeroRecords": "<s:text name='setup.datatables.emptytable' />",
			"sInfo": "<s:text name='setup.datatables.info' />",
			"sInfoEmpty": "<s:text name='setup.datatables.infoempty' />",
		    "sInfoFiltered": "",
		},
		"fnRowCallback": function( nRow, aData, iDisplayIndex ) 
		{
		if ( jQuery.inArray(aData[0], SelectedOldAlerts) != -1 )
		{
				$(nRow).addClass('row_selected');
				$(nRow).find('input:checkbox').attr("checked", true);
		}
		
		var sData = $('input:checked', alertBackupTable.fnGetNodes())
		
		
		if(sData.length==10)
		$('#SelectallOldAlerts').attr("checked", true);
		else
		$('#SelectallOldAlerts').attr("checked", false);

		
		return nRow;
		},
	});
		
		/*Refreshing Alert For Every 10 Sec*/ 
		setInterval( function () { RefreshAlert(1);}, 10000 );	

		 /*Intializing Alarm Tables */
		 alarmBackupTable = $('#listalarmstablefrombackup').dataTable({
			"bServerSide": true,
			 "bStateSave": true,
			"bDestroy": true,
			"sScrollY": 300,
			"sScrollX": "100%",
			"sScrollXInner": "100%",
			"sPaginationType": "two_button",
			"sAjaxSource": "listAlarmsFromBackup.action",
			
			"aoColumns": [

				           { "bSortable": false, "bVisible": false },
				           { "bSortable": false },
				           { "bSortable": false },
				           { "bSortable": false },
				           { "bSortable": false ,sWidth: '2%'},
				          
				            ], 
		  	"sDom": 'ltip',
			"oLanguage": {
				"sLengthMenu": "<s:text name='setup.datatables.showentries' />",
				"sZeroRecords": "<s:text name='setup.datatables.emptytable' />",
				"sInfo": "<s:text name='setup.datatables.info' />",
				"sInfoEmpty": "<s:text name='setup.datatables.infoempty' />",
			"sInfoFiltered": "",
			},
			"fnRowCallback": function( nRow, aData, iDisplayIndex ) 
			{
			if ( jQuery.inArray(aData[0], SelectedOldAlarms) != -1 )
			{
					$(nRow).addClass('row_selected');
					$(nRow).find('input:checkbox').attr("checked", true);
			}
			
			
			
			return nRow;
			},
		});		
	 
	    /*Refreshing Alarm For Every 10 Sec*/ 
		setInterval( function () {RefreshAlarm(1);}, 10000 );	
	}

function InitDataTable()
{
$('.requetForOldPdf').hide();
$('.deleteBackUplink').hide();
$('.newAlert').hide();
	/**Intializing Alert DataTable**/
	alertTable = $('#listalertstable').dataTable({
		"bServerSide": true,
		"bDestroy": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"sPaginationType": "two_button",
		"sAjaxSource": "listAlerts.action",
		
		"aoColumns": [
			           { "bSortable": false, "bVisible": false },
			           { "bSortable": false },
			           { "bSortable": false },
			           { "bSortable": false ,"sSortDataType": "dom-checkbox"},
			           { "bSortable": false, "bVisible":false},
			           { "bSortable": false ,sWidth: '2%',"sSortDataType": "dom-checkbox"},
			          
			         ], 
	  	"sDom": 'ltip',
		"oLanguage": 
		{
			"sLengthMenu": "<s:text name='setup.datatables.showentries' />",
			"sZeroRecords": "<s:text name='setup.datatables.emptytable' />",
			"sInfo": "<s:text name='setup.datatables.info' />",
			"sInfoEmpty": "<s:text name='setup.datatables.infoempty' />",
		    "sInfoFiltered": "",
		},
		"fnRowCallback": function( nRow, aData, iDisplayIndex ) 
		{
		if ( jQuery.inArray(aData[0], SelectedAlerts) != -1 )
		{
				$(nRow).addClass('row_selected');
				$(nRow).find('input:checkbox').attr("checked", true);
		}
		
		var sData = $('input:checked', alertTable.fnGetNodes())
		
		
		if(sData.length==10)
		$('#SelectallAlerts').attr("checked", true);
		else
		$('#SelectallAlerts').attr("checked", false);

		
		return nRow;
		},
	});
	
	/*Refreshing Alert For Every 10 Sec*/ 
	setInterval( function () { RefreshAlert(1);}, 10000 );	

	 /*Intializing Alarm Tables */
	 alarmTable = $('#listalarmstable').dataTable({
		"bServerSide": true,
		 "bStateSave": true,
		"bDestroy": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"sPaginationType": "two_button",
		"sAjaxSource": "listAlarms.action",
		
		"aoColumns": [

			           { "bSortable": false, "bVisible": false },
			           { "bSortable": false },
			           { "bSortable": false },
			           { "bSortable": false },
			           { "bSortable": false ,sWidth: '2%'}
			            ], 
	  	"sDom": 'ltip',
		"oLanguage": {
			"sLengthMenu": "<s:text name='setup.datatables.showentries' />",
			"sZeroRecords": "<s:text name='setup.datatables.emptytable' />",
			"sInfo": "<s:text name='setup.datatables.info' />",
			"sInfoEmpty": "<s:text name='setup.datatables.infoempty' />",
		"sInfoFiltered": "",
		},
		"fnRowCallback": function( nRow, aData, iDisplayIndex ) 
		{
		if ( jQuery.inArray(aData[0], SelectedAlarms) != -1 )
		{
				$(nRow).addClass('row_selected');
				$(nRow).find('input:checkbox').attr("checked", true);
		}
		
		var sData = $('input:checked', alarmTable.fnGetNodes())
		if(sData.length==10)
		$('#SelectAllAlarms').attr("checked", true);
		else
		$('#SelectAllAlarms').attr("checked", false);

		
		return nRow;
		},
	});		
 
    /*Refreshing Alarm For Every 10 Sec*/ 
	setInterval( function () {RefreshAlarm(1);}, 10000 );	
}


function RefreshAlert(value)
{
	alertTable.fnDraw(false);
	var oSettings = alertTable.fnSettings();
	var iTotalRecords = oSettings.fnRecordsTotal();
	
	
	if(iTotalRecords <= 10)
	{
		alertTable.fnDraw();
	}
	else
	{
		alertTable.fnDraw(false);
	}	
	
	
 	 
}

function RefreshOldAlert(value)
{
	alertBackupTable.fnDraw(false);
	var oSettings = alertBackupTable.fnSettings();
	var iTotalRecords = oSettings.fnRecordsTotal();
	
	
	if(iTotalRecords <= 10)
	{
		alertBackupTable.fnDraw();
	}
	else
	{
		alertBackupTable.fnDraw(false);
	}	
	
	
 	 
}

function RefreshAlarm(value)
{
	alarmTable.fnDraw(false);
	var oSettings = alarmTable.fnSettings();
	var iTotalRecords = oSettings.fnRecordsTotal();
	//alert("Start"+oSettings._iDisplayStart+"End:"+oSettings.fnDisplayEnd()+"Total Number:"+oSettings.fnRecordsTotal()+"Dispaly Length:"+oSettings._iDisplayLength+" NO of Pages Page"+Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength ));
	
	
	if(iTotalRecords <= 10)
	{
		alarmTable.fnDraw();
	}
	else
	{
		alarmTable.fnDraw(false);
	}	
}


function RefreshOldAlarm(value)
{
	alarmBackupTable.fnDraw(false);
	var oSettings = alarmBackupTable.fnSettings();
	var iTotalRecords = oSettings.fnRecordsTotal();
	//alert("Start"+oSettings._iDisplayStart+"End:"+oSettings.fnDisplayEnd()+"Total Number:"+oSettings.fnRecordsTotal()+"Dispaly Length:"+oSettings._iDisplayLength+" NO of Pages Page"+Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength ));
	
	
	if(iTotalRecords <= 10)
	{
		alarmBackupTable.fnDraw();
	}
	else
	{
		alarmBackupTable.fnDraw(false);
	}	
}

function fnFilterGlobal ()
{
	
	myRadio =$("input:radio:checked").val();
	var value=$("#selctedDevice :selected").text();
	if(myRadio=='Alerts')
	{
		/*if($('#SelectallAlerts').attr("checked", true)){
			
			$("input:checkbox[name=alerts]").attr("checked",false);
			
		}*/
		
		if(value != "No Device Selected")
		{	
			
		$('#listalertstable').dataTable().fnFilter
		( 
				
			$("#selctedDevice :selected").text(),
				2,true,true
			);
			
			$('#listalertstablefrombackup').dataTable().fnFilter
		( 
				
			$("#selctedDevice :selected").text(),
				2,true,true
			);}
		else
		{
			$('#listalertstable').dataTable().fnFilter
			( 
				
			"",
				2,true,true
			);
			$('#listalertstablefrombackup').dataTable().fnFilter
			( 
				
			"",
				2,true,true
			);
		}
	}
	else
	{
		if(value != "No Device Selected")
		{		

		$('#listalarmstable').dataTable().fnFilter( 
				
				$("#selctedDevice :selected").text(),
				2,true,true
				
			);
        $('#listalarmstablefrombackup').dataTable().fnFilter( 
				
				$("#selctedDevice :selected").text(),
				2,true,true
				
			);}
		else
		{
			$('#listalarmstable').dataTable().fnFilter( 
				
				"",
				2,true,true
				
			);
			
			$('#listalarmstablefrombackup').dataTable().fnFilter( 
					
					"",
					2,true,true
					
				);
		}
	}
	
	
}

function fnFilterColumn ()
{
	
	myRadio =$("input:radio:checked").val();
	if(myRadio=='Alerts')
	{
		var Date=$("#FromDate").val();
		
		Date += ":"+$("#ToDate").val();
		$('#listalertstable').dataTable().fnFilter( 
			Date,1,true,true
		);
		$('#listalertstablefrombackup').dataTable().fnFilter( 
				Date,1,true,true
			);
	
	}
	else
	{
		var Date=$("#FromDate").val();
		Date += ":"+$("#ToDate").val();
		$('#listalarmstable').dataTable().fnFilter( 
			Date,1,true,true
		);
		$('#listalarmstablefrombackup').dataTable().fnFilter( 
				Date,1,true,true
			);
	}
	

}


//3gp start		
var userDetails = new UserDetails();
var selectedGateWay = "";
var gateWayCount = 0;
//3gp end
$(document).ready(function() 
{
        
	    $(".hiddenhtml").val("0");						
		$("#selctedDevice").change(fnFilterGlobal);				
    	$("#FromDate").change(fnFilterColumn);
    	$("#ToDate").change(fnFilterColumn);			
			
    	InitDataTable(); 
		
		userDetails=new UserDetails();
		
		var handleSuccess = function(xml){
			
				// 1. Handling the storage.
				var storage = $(xml).find("storage");
				userDetails.UserInformationHtml(storage);
				
				
			};
			
			
			var showUserDetails = function(){
			
			
				var lUrl = "userdetails.action";
				$.ajax({
					url: lUrl,
					
					
					
							success: function(data)
							{		
									var storage = $(data).find("storage");
									var gateWay = $(data).find("gateway");
									
									//3gp start
									gateWayCount = $(data).find("gateway").size();
									if (gateWayCount > 1)
									{
										InitDataTableForMultipleGateway(); 
									} 
									else 
									{
										InitDataTable(); 
									}
									//3gp end
						
				userDetails.UserInformationHtml(storage);
				 var featuresEnabled = gateWay.find("featuresEnabled").text();
				 
				 if((featuresEnabled & 4)==0){		
                    /*  $('#dashboard').hide();				 
					$('#dashboard').addClass('displayclass'); */
					
					//Disabling dashboard start
					$('#dashboard').show();
					$('#dashboard').removeClass('displayclass');
					$('#dashboard').css("opacity", "0.5");
					$('#dash').removeAttr("href");
					
					$("#dash").click(function(){
					     alert("Dashboard feature is disabled.");
					     event.stopImmediatePropagation();
					   
					}); 
					
					//Disabling dashboard end
					
					
						}
					else if((featuresEnabled & 4)==4)
					{
					  $('#dashboard').show();
					$('#dashboard').removeClass('displayclass');
					}
				 
			  				}
					
					
				});
			};
		
			
			var viewRefresher = function(){
					showUserDetails();
				setTimeout(viewRefresher,100000);
			};
			viewRefresher();
		
		$('input[value="Alerts"]').attr('checked',true);
		$('#AlarmTable').hide();
		$('#AlertTableFromBackup').hide();
		$('#AlarmTableFromBackup').hide();
		
		$('.radioAlarm').live('click', function(){	
			
			myRadio =$("input:radio:checked").val();
			valueHtml = $(".hiddenhtml").val();
		
			if(myRadio=='Alerts')
			{
				if(valueHtml == 0){
                   // alert("new alert view");
					$('#AlarmTable').hide();
					$('#AlertTable').show();
					
				}else{
					// alert("old alert view");
					$('#AlarmTableFromBackup').hide();
					$('#AlertTableFromBackup').show();
				}
				
				alertTable.fnFilter( '', 2 );
				alertBackupTable.fnFilter( '', 2 );
			}
			else
			{
				if(valueHtml == 0){
					// alert("new alarm view");
				$('#AlarmTable').show();
				$('#AlertTable').hide();
			}else{
				// alert("old alarm view");
				$('#AlertTableFromBackup').hide();
				$('#AlarmTableFromBackup').show();
			}
			
			
			
			
			alarmTable.fnFilter( '', 2 );
			alarmBackupTable.fnFilter( '', 2 );

				
			}
			});
	
		$('.requetForPdf').live('click', function() 
				{
						var form = $(this).closest("form");
						//vibhu start
						var FromDate=$('input:text[name=FromDate]').val();
						var ToDate=$('input:text[name=ToDate]').val();
						if(validateDates(FromDate, ToDate) == false) return false;
						//vibhu end
						
						var params = form.serialize()+"&displayStart=0";
						//3gp start
						 var url = "";
						if (gateWayCount > 1) 
						{
							 url = "requetForPdfForMultipleGateways.action";
						} else
						{
							 url = $(this).attr('href');
						} 
						//3gp end
						//var url = $(this).attr('href');
						$.ajax({
							url: url,
						  	data:params,
						  	success: function(data)
						  	{
						  		$("#message").html(data);
								$("#message").dialog('open');
								$("#message" ).dialog({
									
									stackfix: true,
									modal: true,
										buttons: {
											Ok:function(){
												$(this).dialog('close');
												$("#confirm").dialog("destroy");
											}
										}
								});
								
						  	}
							});
					return false;	 	
				});
				
		
		
		$('.requetForOldPdf').live('click', function() 
				{
						var form = $(this).closest("form");
						
						//vibhu start
						var FromDate=$('input:text[name=FromDate]').val();
						var ToDate=$('input:text[name=ToDate]').val();
						if(validateDates(FromDate, ToDate) == false) return false;
						//vibhu end
						
						var params = form.serialize()+"&displayStart=0";
						var url = $(this).attr('href');
						$.ajax({
							url: url,
						  	data:params,
						  	success: function(data)
						  	{
						  		$("#message").html(data);
								$("#message").dialog('open');
								$("#message" ).dialog({
									
									stackfix: true,
									modal: true,
										buttons: {
											Ok:function(){
												$(this).dialog('close');
												$("#confirm").dialog("destroy");
											}
										}
								});
								
						  	}
							});
					return false;	 	
				});
		
		
		
				$(".datetime").datepicker({
				     showOn: 'button', 
				     buttonImage: '/imonitor/resources/images/calendar.gif', 
				     buttonImageOnly: true,
				     maxDate : new Date(),
				     dateFormat: "yy-mm-dd",
				     
				     
				});

				$(".mydatepicker").datepicker({
					showOn: 'button',
					buttonImage: '/imonitor/resources/images/calendar.gif',
					buttonImageOnly: true
					,
					beforeShow: function(input, inst) {
						$(".ui-datepicker").css('z-index', 5000);
					},
					onClose: function(dateText, inst) { 
						$(".ui-datepicker").css('z-index', 1);
						var testToDate = new Date(dateText);
						var testFromDate = new Date($('#FromDate').val());
						if (testFromDate > testToDate){
							showResultAlert("To date should be greater than From date");
			        }
					},
				
					dateFormat: "yy-mm-dd"
						
					});
     
					$(".mydatepicker" ).datepicker( "option", "showButtonPanel", true );
					$("#ui-datepicker-div").hide();

				var alertOptionHtml="<option value='0'>"+"<s:text name='alerts.nodevice' />"+"</option>";
				$(".selectAlertName").html(alertOptionHtml);

			    <s:iterator  value="devices" var="dev" status="x">
			    		alertOptionHtml+= "<option value='<s:property value="#dev.id" />'><s:property value='#dev.friendlyName' /></option>";
			    		$(".selectAlertName").html(alertOptionHtml);
			     </s:iterator>
				 
				
				 
			    
			    
			     
			    $('.deletelink').live('click', function()
			    { 	
			    	var selectedValues="";
			    	if(myRadio=='Alerts')
			    	{
			    		for(var i=0;i<SelectedAlerts.length;i++)
						{
							selectedValues +=  SelectedAlerts[i] +",";

						}
			    		SelectedAlerts = [];	//Clearing Selected Alerts
			    	}
			    	else
			    	{
			    		for(var i=0;i<SelectedAlarms.length;i++)
						{
							selectedValues +=  SelectedAlarms[i] +",";
                            
						}
			    		SelectedAlarms=[];//Clearing Selected Alarm 
			    	}
				
					
			    	if(selectedValues=="")
			    	{
							if(myRadio=='Alerts')
							{
								var sData = $(alertTable.fnGetNodes());
								
								if(sData.length != 0)
								showResultAlert("<s:text name='alerts.msg.0001' />");
								else
								showResultAlert("<s:text name='alerts.msg.0001a' />");
							}
							else
							{
								
								var sData = $(alarmTable.fnGetNodes());
								
								if(sData.length != 0)
								showResultAlert("<s:text name='alerts.msg.0001' />");
								else
								showResultAlert("<s:text name='alerts.msg.0001a' />");
							}
							
			    			
			    			return false;
			    	}
			    	var form = $(this).closest("form");
			    	var params = form.serialize()+"&selectedAlarms="+selectedValues;
			    	var url = $(this).attr('href');
						$.ajax({
						 	url: url,
						  	data:params,
						  	success:function(data)
						  	{	
						  		if(myRadio=='Alerts')
								{
									$('#SelectallAlerts').removeAttr('checked');
						  			RefreshAlert(2);
						  			showResultAlert('<s:text name="setup.alerts.delete" />');
								}
							  	else
							  	{
							  		
							  		$('#SelectAllAlarms').removeAttr('checked');
									 RefreshAlarm();
									 showResultAlert('<s:text name="setup.alarms.delete" />');
							  	}
						  	}
						
						});
					return false;	 
				});	
			    
			    $('.deleteBackUplink').live('click', function()
					    { 	
					    	var selectedValues="";
					    	if(myRadio1=='OldAlerts')
					    	{
					    		for(var i=0;i<SelectedOldAlerts.length;i++)
								{
									selectedValues +=  SelectedOldAlerts[i] +",";

								}
					    		SelectedOldAlerts = [];	//Clearing Selected Alerts
					    	}
					    	else
					    	{
					    		for(var i=0;i<SelectedOldAlarms.length;i++)
								{
									selectedValues +=  SelectedOldAlarms[i] +",";

								}
					    		SelectedOldAlarms=[];//Clearing Selected Alarm 
					    	}
						
							
					    	if(selectedValues=="")
					    	{
									if(myRadio1=='OldAlerts')
									{
										var sData = $(alertBackupTable.fnGetNodes());
										
										if(sData.length != 0)
										showResultAlert("<s:text name='alerts.msg.0001' />");
										else
										showResultAlert("<s:text name='alerts.msg.0001a' />");
									}
									else
									{
										
										var sData = $(alarmBackupTable.fnGetNodes());
										
										if(sData.length != 0)
										showResultAlert("<s:text name='alerts.msg.0001' />");
										else
										showResultAlert("<s:text name='alerts.msg.0001a' />");
									}
									
					    			
					    			return false;
					    	}
					    	var form = $(this).closest("form");
					    	var params = form.serialize()+"&selectedAlarms="+selectedValues;
					    	var url = $(this).attr('href');
								$.ajax({
								 	url: url,
								  	data:params,
								  	success:function(data)
								  	{	
								  		if(myRadio=='Alerts')
										{
											$('#SelectallOldAlerts').removeAttr('checked');
								  			RefreshOldAlert(2);
								  			
										}
									  	else
									  	{
									  		
									  		$('#SelectAllOldAlarms').removeAttr('checked');
											 RefreshOldAlarm();
									  	}
								  	}
								
								});
							return false;	 
						});			  
			    
			});
				


			function handleFormHide(value)
			{
				
			}

			function clear_form(ele) {
					
				    $(ele).find(':input').each(function() {
				        switch(this.type) {
				            case 'select-one':
				            case 'text':
				            case 'textarea':
				                $(this).val('');
				                break;
				        }
				    });
				}

			$('#SelectallAlerts').live('click', function()
			{
					if ($(this).attr('checked')) 
					{
			        		
						$("input:checkbox[name=alerts]").attr("checked",true);
						$('input:checked').closest("tr").addClass('row_selected');
						if(SelectedAlerts.length>0)
						{
						var sData = $('input:checked', alertTable.fnGetNodes())
						for(var i=0;i<sData.length;i++)
						{
						var iId = sData[i].value;
						if ( jQuery.inArray(iId, SelectedAlerts) != -1 )
						{
							SelectedAlerts = jQuery.grep(SelectedAlerts, function(value)
							{
								return value != iId;
							});
						}
					}

						}
			    		} 
					else
					{
						$("input:checkbox[name=alerts]").attr("checked",false);

					}

					
					var sData = $('input:checked', alertTable.fnGetNodes())
					for(var i=0;i<sData.length;i++)
					{
						var iId = sData[i].value;
						if ( jQuery.inArray(iId, SelectedAlerts) == -1 )
						{
							SelectedAlerts[SelectedAlerts.length++] = iId;
						}
						else
						{
							SelectedAlerts = jQuery.grep(SelectedAlerts, function(value)
							{
								return value != iId;
							});
						}
					}


					

					
					
					
					$('#listalertstable tbody tr ').each(function() {

			    		$(this).each(function() 
					{
			        		var nextValue = $("input:checkbox:not(:checked)", this).val();
			        		
						if(nextValue==undefined)
						{
					
						}
						else
						{
						
						if( jQuery.inArray(nextValue, SelectedAlerts) == -1 )
						{
							SelectedAlerts[SelectedAlerts.length++] = nextValue;
						}
						else
						{
							SelectedAlerts = jQuery.grep(SelectedAlerts, function(value)
							{
								return value != nextValue;
							});
						}
						$(this).removeClass('row_selected');

						}
						

						
			    		});
					});
					
					

					if ($(this).attr('checked')) 
					{
			        		//$('input:checked').closest("tr").addClass('row_selected');
			    		} 
					$("#button").show();
			});

			
			$('#SelectallOldAlerts').live('click', function()
					{
			

							if ($(this).attr('checked')) 
							{
					        		
								$("input:checkbox[name=alerts]").attr("checked",true);
								$('input:checked').closest("tr").addClass('row_selected');
								if(SelectedOldAlerts.length>0)
								{
								var sData = $('input:checked', alertBackupTable.fnGetNodes())
								for(var i=0;i<sData.length;i++)
								{
								var iId = sData[i].value;
								if ( jQuery.inArray(iId, SelectedOldAlerts) != -1 )
								{
									SelectedOldAlerts = jQuery.grep(SelectedOldAlerts, function(value)
									{
										return value != iId;
									});
								}
							}

								}
					    		} 
							else
							{
								$("input:checkbox[name=alerts]").attr("checked",false);

							}

							
							var sData = $('input:checked', alertBackupTable.fnGetNodes())
							for(var i=0;i<sData.length;i++)
							{
								var iId = sData[i].value;
								if ( jQuery.inArray(iId, SelectedOldAlerts) == -1 )
								{
									SelectedOldAlerts[SelectedOldAlerts.length++] = iId;
								}
								else
								{
									SelectedOldAlerts = jQuery.grep(SelectedOldAlerts, function(value)
									{
										return value != iId;
									});
								}
							}


							

							
							
							
							$('#listalertstablefrombackup tbody tr ').each(function() {

					    		$(this).each(function() 
							{
					        		var nextValue = $("input:checkbox:not(:checked)", this).val();
					        		
								if(nextValue==undefined)
								{
							
								}
								else
								{
								
								if( jQuery.inArray(nextValue, SelectedOldAlerts) == -1 )
								{
									SelectedOldAlerts[SelectedOldAlerts.length++] = nextValue;
								}
								else
								{
									SelectedOldAlerts = jQuery.grep(SelectedOldAlerts, function(value)
									{
										return value != nextValue;
									});
								}
								$(this).removeClass('row_selected');

								}
								

								
					    		});
							});
							
							

							if ($(this).attr('checked')) 
							{
					        		//$('input:checked').closest("tr").addClass('row_selected');
					    		} 
							
					});

			
			
			$(document).keypress(function(e) { 
			    if (e.which == 27) {
			                 $('#overlay').hide();
			                 $('#imageshowing').hide();
			    }
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
			
	        function closeoverlay() {
				el = document.getElementById("overlay");
				el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
				document.getElementById('overlay').style.height = $(document).height()+"px";
				document.getElementById('overlay').style.width = $(document).width()+"px";
				el = document.getElementById("imageshowing");
				el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
			}

			$('.imvgalertImage').live('click', function(){
				var url = $(this).attr('href');
				var deviceId = $(this).attr('deviceId');
				var alertFromImvgId = $(this).attr('alertFromImvgId');
				var params = {"alertFromImvgId":alertFromImvgId, "deviceId":deviceId};
				el = document.getElementById("overlay");
				el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
				/* document.getElementById('overlay').style.height = $(document).height()+"px";
				document.getElementById('overlay').style.width = $(document).width()+"px"; */
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
			
			//Naveen Added to show node protocol info and Routing Info	
			$('.imvgalertInfo').live('click', function(){
				var url = $(this).attr('href');
				var deviceId = $(this).attr('deviceId');
				var alertFromImvgId = $(this).attr('alertFromImvgId');
				var params = {"alertFromImvgId":alertFromImvgId, "deviceId":deviceId};
				el = document.getElementById("overlay");
				el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
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
			
			$('.imvgalertNodeInfo').live('click', function(){
			
				var alertValue = $(this).attr('deviceInfo');
				var alertFromImvgId = $(this).attr('alertFromImvgId');
				//var html = ;
				var data = alertValue.split("-");
				var generic = "";
				var basic = "";
				var specific = "";
				
				
				if(data[0]== "CONTROLLER")
					basic = "Portable Controller";
				else if(data[0] == "ROUTING_SLAVE")
				    basic = "Slave with routing capabilities";
				else if(data[0] == "SLAVE")
					basic = "Slave";
				else if(data[0] == "STATIC_CONTROLLER")
						basic = "Static Controller";
				else
					basic = "Invalid Basic Class";
				
				if(data[1] == "SENSOR_BINARY")
					generic = "Binary Sensor";
				else if(data[1] == "SENSOR_MULTILEVEL")
				     generic = "Multilevel Sensor";
				else if(data[1] == "SWITCH_BINARY")
					 generic = "Binary Switch";
				else if(data[1] == "SWITCH_MULTILEVEL")
					 generic = "Multilevel Switch";
				else if(data[1] == "REPEATER_SLAVE")
					 generic = "Repeater Slave";
				else if(data[1] == "ENTRY_CONTROL")
					generic = "Entry Control";
				else if(data[1] == "THERMOSTAT")
					generic = "Thermostat";
				else if(data[1] == "GENERIC_CONTROLLER")
					generic = "Remote Controller";
				else if(data[1] == "AV_CONTROL_POINT")
				    generic = "AV Control Point";
				else if(data[1] == "SENSOR_ALARM")
					generic = "Sensor Alarm";
				else
					generic = "Invalid Generic Class";
					
				if(data[2] == "NOT_USED")
					specific = "Specific Device Class not used";
				else if(data[2] == "ROUTING_SENSOR_BINARY")
					specific = "Routing Binary Sensor";
				else if(data[2] == "MULTI_SENSOR")
					specific = "Multi-Sensor";
				else if(data[2] == "CHIMNEY_FAN")
                    specific = "Chimney Fan";
				else if(data[2] == "ROUTING_SENSOR_MULTILEVEL")
					specific = "Routing Multilevel Sensor";
				else if(data[2] == "POWER_SWITCH_BINARY")
					specific = "Binary Power Switch";
				else if(data[2] == "SCENE_SWITCH_BINARY")
					specific = "Binary Scene Switch";
				else if(data[2] == "CLASS_A_MOTOR_CONTROL")
					specific = "Class A Motor Controller";
				else if(data[2] == "CLASS_B_MOTOR_CONTROL")
					specific = "Class B Motor Controller";
				else if(data[2] == "CLASS_C_MOTOR_CONTROL")
					specific = "Class C Motor Controller";
				else if(data[2] == "MOTOR_MULTIPOSITION")
					specific = "Multiposition Motor";
				else if(data[2] == "POWER_SWITCH_MULTILEVEL")
					specific = "Multilevel Power Switch";
				else if(data[2] == "SCENE_SWITCH_MULTILEVEL")
					specific = "Multilevel Scene Switch";
				else if(data[2] == "REPEATER_SLAVE")
					specific = "Basic Repeater Slave";
				else if(data[2] == "ADVANCED_DOOR_LOCK")
					specific = "Advanced Door Lock";
				else if(data[2] == "DOOR_LOCK")
					specific = "Door Lock";
				else if(data[2] == "SECURE_KEYPAD_DOOR_LOCK")
					specific = "Secure Keypad Door Lock";
				else if(data[2] == "SETBACK_SCHEDULE_THERMOSTAT")
					specific = "Setback Schedule Thermostat";
				else if(data[2] == "SETBACK_THERMOSTAT")
					specific = "Setback Thermostat";
				else if(data[2] == "SETPOINT_THERMOSTAT")
					specific = "Setpoint Thermostat";
				else if(data[2] == "THERMOSTAT_GENERAL")
					specific = "Thermostat General";
				else if(data[2] == "THERMOSTAT_GENERAL_V2")
					specific = "Thermostat General V2";
				else if(data[2] == "THERMOSTAT_HEATING")
					specific = "Thermostat Heating";
				else if(data[2] == "PORTABLE_INSTALLER_TOOL")
					specific = "Portable Installer Tool";
				else if(data[2] == "PORTABLE_REMOTE_CONTROLLER")
					specific = "Portable Remote Controller";
				else if(data[2] == "PORTABLE_SCENE_CONTROLLER")
					specific = "Portable Scene Controller";
				else if(data[2] == "DOORBELL")
					specific = "Door Bell";
				else if(data[2] == "SATELLITE_RECEIVER")
					specific = "Satellite Receiver";
				else if(data[2] == "SATELLITE_RECEIVER_V2")
					specific = "Satellite Receiver V2";
				else if(data[2] == "ADV_ZENSOR_NET_ALARM_SENSOR")
					specific = "Advanced Zensor Net Alarm Sensor";
				else if(data[2] == "ADV_ZENSOR_NET_SMOKE_SENSOR")
					specific = "Advanced Zensor Net Smoke Sensor";
				else if(data[2] == "BASIC_ROUTING_ALARM_SENSOR")
					specific = "Basic Routing Alarm Sensor";
				else if(data[2] == "BASIC_ROUTING_SMOKE_SENSOR")
					specific = "Basic Routing Smoke Sensor";
				else if(data[2] == "BASIC_ZENSOR_NET_ALARM_SENSOR")
					specific = "Basic Zensor Net Alarm Sensor";
				else if(data[2] == "BASIC_ZENSOR_NET_SMOKE_SENSOR")
					specific = "Basic Zensor Net Smoke Sensor";
				else if(data[2] == "ROUTING_ALARM_SENSOR")
					specific = "Routing Alarm Sensor";
				else if(data[2] == "ROUTING_SMOKE_SENSOR")
					specific = "Routing Smoke Sensor";
				else if(data[2] == "ZENSOR_NET_ALARM_SENSOR")
					specific = "Zensor Net Alarm Sensor";
				else if(data[2] == "ZENSOR_NET_SMOKE_SENSOR")
					specific = "Zensor Net Smoke Sensor";
				else if(data[2] == "ALARM_SENSOR")
					specific = "Sensor(Alarm) Device Type";
				else
					specific = "Invalid Specific Class";
				
				
				$("#message").html("<table><tr><td>BASIC:</td><td>"+basic+"</td></tr>"+
						           "<tr><td>GENERIC:</td><td>"+generic+"</td></tr>"+
						            "<tr><td>SPECIFIC:</td><td>"+ specific+ "</td></tr></table>");
				$("#message").dialog('open');
				$("#message" ).dialog({
					
					stackfix: true,
					modal: true,
					width:550,
						buttons: {
							Ok:function(){
								$(this).dialog('close');
								$("#confirm").dialog("destroy");
							}
						}
				});
				
				
				return false;
			});
			$('.imvgalertImagefrombackup').live('click', function(){
				var url = $(this).attr('href');
				var deviceId = $(this).attr('deviceId');
				
				var alertFromImvgId = $(this).attr('alertFromImvgId');
				var params = {"alertFromImvgId":alertFromImvgId, "deviceId":deviceId};
				el = document.getElementById("overlay");
				el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
				/* document.getElementById('overlay').style.height = $(document).height()+"px";
				document.getElementById('overlay').style.width = $(document).width()+"px"; */
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
			
	$('#SelectAllAlarms').live('click', function()
	{
			/* 	$("input:checkbox[name=alarm]").attr("checked", this.checked);
					var aData = alertTable.fnGetData();
					alert(aData);
					var iId = aData[5];
					alert(iId);
					if ( jQuery.inArray(iId, SelectedAlerts) == -1 )
					{
						SelectedAlerts[SelectedAlerts.length++] = iId;
					}
					else
					{
						SelectedAlerts = jQuery.grep(SelectedAlerts, function(value) {
							
							return value != iId;
						} );
					}
					
					$(this).toggleClass('row_selected'); */
					
		if ($(this).attr('checked')) 
		{
        		
			$("input:checkbox[name=alarm]").attr("checked",true);
			$('input:checked').closest("tr").addClass('row_selected');
			if(SelectedAlarms.length>0)
			{
			var sData = $('input:checked', alarmTable.fnGetNodes())
			for(var i=0;i<sData.length;i++)
			{
			var iId = sData[i].value;
			if ( jQuery.inArray(iId, SelectedAlarms) != -1 )
			{
				SelectedAlarms = jQuery.grep(SelectedAlarms, function(value)
				{
					return value != iId;
				});
			}
		}

			}
    		} 
		else
		{
			$("input:checkbox[name=alarm]").attr("checked",false);

		}

		

		var sData = $('input:checked', alarmTable.fnGetNodes())
		for(var i=0;i<sData.length;i++)
		{
			var iId = sData[i].value;
			if ( jQuery.inArray(iId, SelectedAlarms) == -1 )
			{
				SelectedAlarms[SelectedAlarms.length++] = iId;
			}
			else
			{
				SelectedAlarms = jQuery.grep(SelectedAlarms, function(value)
				{
					return value != iId;
				});
			}
		}


		

		
		
		
		$('#listalarmstable tbody tr ').each(function() {

    		$(this).each(function() 
			{
        		var nextValue = $("input:checkbox:not(:checked)", this).val();
        		
			if(nextValue==undefined)
			{
		
			}
			else
			{
			
			if( jQuery.inArray(nextValue, SelectedAlarms) == -1 )
			{
				SelectedAlarms[SelectedAlarms.length++] = nextValue;
			}
			else
			{
				SelectedAlarms = jQuery.grep(SelectedAlarms, function(value)
				{
					return value != nextValue;
				});
			}
			$(this).removeClass('row_selected');

			}
			

			
    		});
		});
		
		

		if ($(this).attr('checked')) 
		{
        		//$('input:checked').closest("tr").addClass('row_selected');
    	} 
		$("#button").show();
		});

	
	
	
	$('#SelectAllOldAlarms').live('click', function()
			{
							
				if ($(this).attr('checked')) 
				{
		        		
					$("input:checkbox[name=alarm]").attr("checked",true);
					$('input:checked').closest("tr").addClass('row_selected');
					if(SelectedOldAlarms.length>0)
					{
					var sData = $('input:checked', alarmBackupTable.fnGetNodes())
					for(var i=0;i<sData.length;i++)
					{
					var iId = sData[i].value;
					if ( jQuery.inArray(iId, SelectedOldAlarms) != -1 )
					{
						SelectedOldAlarms = jQuery.grep(SelectedOldAlarms, function(value)
						{
							return value != iId;
						});
					}
				}

					}
		    		} 
				else
				{
					$("input:checkbox[name=alarm]").attr("checked",false);

				}

				
				var sData = $('input:checked', alarmBackupTable.fnGetNodes())
				for(var i=0;i<sData.length;i++)
				{
					var iId = sData[i].value;
					if ( jQuery.inArray(iId, SelectedOldAlarms) == -1 )
					{
						SelectedOldAlarms[SelectedOldAlarms.length++] = iId;
					}
					else
					{
						SelectedOldAlarms = jQuery.grep(SelectedOldAlarms, function(value)
						{
							return value != iId;
						});
					}
				}


				

				
				
				
				$('#listalarmstablefrombackup tbody tr ').each(function() {

		    		$(this).each(function() 
					{
		        		var nextValue = $("input:checkbox:not(:checked)", this).val();
		        		
					if(nextValue==undefined)
					{
				
					}
					else
					{
					
					if( jQuery.inArray(nextValue, SelectedOldAlarms) == -1 )
					{
						SelectedOldAlarms[SelectedOldAlarms.length++] = nextValue;
					}
					else
					{
						SelectedOldAlarms = jQuery.grep(SelectedOldAlarms, function(value)
						{
							return value != nextValue;
						});
					}
					$(this).removeClass('row_selected');

					}
					

					
		    		});
				});
				
				

				if ($(this).attr('checked')) 
				{
		        		//$('input:checked').closest("tr").addClass('row_selected');
		    	} 
						});
	
	
			function openHelpPage()
			{
				window.open ('<%=applicationName %>/resources/docs/alarmPageManual.pdf', 'newwindow');
			}
			function showResultAlertOnHome(message) {
				$("#confirm").dialog("destroy");
				 $("#confirm").html(message);
					$("#confirm").dialog('open');
					$("#confirm" ).dialog({
						show: "blind",
						hide: "explode"
					});
				}

			//vibhu start
			function showResultAlert(message) {
				$("#confirm").dialog("destroy");
				 $("#confirm").html(message);
					$("#confirm").dialog('open');
					$("#confirm" ).dialog({
						stackfix: true,
						modal: true,
						buttons: {
							<s:text name='general.ok' />:function(){
							$(this).dialog('close');
							$("#confirm").dialog("destroy");
						}
					}
					});
				}
			
			
			//3gp by apoorva
			function InitDataTableForMultipleGateway(){
$('.requetForOldPdf').hide();
$('.deleteBackUplink').hide();
$('.newAlert').hide();
	/**Intializing Alert DataTable**/
	alertTable = $('#listalertstable').dataTable({
		"bServerSide": true,
		"bDestroy": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"sPaginationType": "two_button",
		"sAjaxSource": "listAlertsForMultipleGateways.action",
		
		"aoColumns": [
			           { "bSortable": false, "bVisible": false },
			           { "bSortable": false },
			           { "bSortable": false },
			           { "bSortable": false ,"sSortDataType": "dom-checkbox"},
			           { "bSortable": false, "bVisible":false},
			           { "bSortable": false ,sWidth: '2%',"sSortDataType": "dom-checkbox"},
			          
			         ], 
	  	"sDom": 'ltip',
		"oLanguage": 
		{
			"sLengthMenu": "<s:text name='setup.datatables.showentries' />",
			"sZeroRecords": "<s:text name='setup.datatables.emptytable' />",
			"sInfo": "<s:text name='setup.datatables.info' />",
			"sInfoEmpty": "<s:text name='setup.datatables.infoempty' />",
		    "sInfoFiltered": "",
		},
		"fnRowCallback": function( nRow, aData, iDisplayIndex ) 
		{
		if ( jQuery.inArray(aData[0], SelectedAlerts) != -1 )
		{
				$(nRow).addClass('row_selected');
				$(nRow).find('input:checkbox').attr("checked", true);
		}
		
		var sData = $('input:checked', alertTable.fnGetNodes())
		
		
		if(sData.length==10)
		$('#SelectallAlerts').attr("checked", true);
		else
		$('#SelectallAlerts').attr("checked", false);

		
		return nRow;
		},
	});
	
	/*Refreshing Alert For Every 10 Sec*/ 
	setInterval( function () { RefreshAlert(1);}, 10000 );	

	 /*Intializing Alarm Tables */
	 alarmTable = $('#listalarmstable').dataTable({
		"bServerSide": true,
		 "bStateSave": true,
		"bDestroy": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"sPaginationType": "two_button",
		"sAjaxSource": "listAlarmsForMultipleGateway.action",
		
		"aoColumns": [

			           { "bSortable": false, "bVisible": false },
			           { "bSortable": false },
			           { "bSortable": false },
			           { "bSortable": false },
			           { "bSortable": false ,sWidth: '2%'}
			            ], 
	  	"sDom": 'ltip',
		"oLanguage": {
			"sLengthMenu": "<s:text name='setup.datatables.showentries' />",
			"sZeroRecords": "<s:text name='setup.datatables.emptytable' />",
			"sInfo": "<s:text name='setup.datatables.info' />",
			"sInfoEmpty": "<s:text name='setup.datatables.infoempty' />",
		"sInfoFiltered": "",
		},
		"fnRowCallback": function( nRow, aData, iDisplayIndex ) 
		{
		if ( jQuery.inArray(aData[0], SelectedAlarms) != -1 )
		{
				$(nRow).addClass('row_selected');
				$(nRow).find('input:checkbox').attr("checked", true);
		}
		
		var sData = $('input:checked', alarmTable.fnGetNodes())
		if(sData.length==10)
		$('#SelectAllAlarms').attr("checked", true);
		else
		$('#SelectAllAlarms').attr("checked", false);

		
		return nRow;
		},
	});		
 
    /*Refreshing Alarm For Every 10 Sec*/ 
	setInterval( function () {RefreshAlarm(1);}, 10000 );	
}
			
			//3gp by apoorva end

			function validateDates(FromDate, ToDate)
			{
				if(FromDate!="" && ToDate=="")
				{
					showResultAlert("<s:text name='alerts.msg.0002' />");
					return false;							
				}
				if(FromDate=="" && ToDate!="")
				{
					showResultAlert("<s:text name='alerts.msg.0003' />");
					return false;							
				}
				if(FromDate!="" && ToDate!="")
				{
					var tdt = $.datepicker.parseDate('yy-mm-dd',ToDate);
					var fdt = $.datepicker.parseDate('yy-mm-dd',FromDate);
					if(tdt < fdt)
					{
						showResultAlert("<s:text name='alerts.msg.0004' />");
						return false;							
					}
				}
				return true;
				
			
				
		
	//$(".ajaxinlinepopupform").hide();
	//$("#button").hide();
	//$( "#radio" ).buttonset();
	 //$("#mainform").hide();
	/* var Dispaly = function()
	{
		<s:if test="#session.user.role.name == 'mainuser' ">
			$("#button").Show();
		</s:if>
		<s:else>
		$("#button").hide();
		</s:else>
	};
	
	$(".previousnextLink").live('click',function(){	
		var url = $(this).attr('href');
		var displayStart = $(this).attr('displayStart');
		var form = $(this).closest("form");
		var params = form.serialize()+"&displayStart="+displayStart;
		$.ajax({
			url:url,
			cache: false,
			dataType: 'xml',
			data: params,
			success: handleAlertSuccess
		});
		//setTimeout(listImvgAlerts,10000);
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
		var deviceArr = new Array();
		var alertsArr = new Array();

		$(data).find("messagealerts").each(function() 
		{
			var device = $(this).find('device').text();
			var alerts = $(this).find('alerts').text();
			deviceArr.push(device);
			alertsArr.push(alerts);
		});
		 

		$(data).find("message").each(function() {
			var time = $(this).find('time').text();
			var devicename = $(this).find('devicename').text();
			var alertdetails = $(this).find('alert').text();
			var alertcommand = $(this).find('alertcommand').text();
			var rulecount = $(this).find('rulecount').text();
			var imvgalertid = $(this).find('imvgalertid').text();
			var deviceid = $(this).find('deviceid').text();
			if(alertcommand=='POWER_INFORMATION')
				{
				var alertValue = $(this).find('alertValue').text();
				var colon=':';
				}
			else
				{
				alertValue =[];
				colon=[];
				}
			if(alertcommand == 1)alertcommand = "STREAM_FTP_SUCCESS";
			else if(alertcommand == 2)alertcommand = "IMAGE_FTP_SUCCESS";
			
			<s:if test="#session.user.role.name == 'mainuser' ">
			var tablerows="<tr class='alertstabledatas'><td style='text-align:left;'>"+time+"</td><td style='text-align: left;'>"+devicename+"</td><td style='text-align: left;'>"+formatMessage(alertcommand)+colon+alertValue+"</td><td style='text-align:center;width:10px;'><input class='checkbox' name='param' type='checkbox' value='" + imvgalertid + "'></input></td></tr>";
			</s:if>
			<s:else>
			var tablerows="<tr class='alertstabledatas'><td style='text-align:left;'>"+time+"</td><td style='text-align: left;'>"+devicename+"</td><td style='text-align: left;'>"+formatMessage(alertcommand)+"</td></tr>";
			</s:else>
		
		$('#imvgalerttable').find('tbody').append(tablerows);
		});
		
		if((endcount*1)>displayrow){		
			var previousvalue = (displayStart*1)-(displayrow*1);
			var previouslink ="<a class='previousnextLink' href='listAlarms.action' displayStart="+previousvalue+"><img title='Newer' src='/imonitor/resources/images/back_enabled.jpg'></a>";
		}
		else
		{
			var previouslink ="<img src='/imonitor/resources/images/back_disabled.jpg' title='inactive' >";
		}
		$('#previouslinkdiv').append(previouslink);			
		if((totalcount*1)>endcount){
			var value = (displayStart*1)+(displayrow*1);
				var nextlinkdiv ="<a class='previousnextLink' href='listAlarms.action' displayStart="+value+"><img title='Older' src='/imonitor/resources/images/forward_enabled.jpg'></a>";

			}else{
				var nextlinkdiv ="<img src='/imonitor/resources/images/forward_disabled.jpg' title='inactive'>";
				}
				$('#nextlinkdiv').append(nextlinkdiv);
				var rowdetails ="<span><b>"+startcount+"</b>-<b>"+endcount+"</b>&nbsp;&nbsp;of&nbsp;&nbsp;<b>"+totalcount+"</b></span>";
			$('#rowdetails').append(rowdetails);
		
		// $('#alertbodysection').html(data);
	};
	
	$(".previousnextLinkAlarm").live('click',function(){
		var displayStart = $(this).attr('displayStart');
		var form = $(this).closest("form");
		var params = form.serialize()+"&displayStart="+displayStart;
		var url = $(this).attr('href');
		$.ajax({
			url: url,
		  	data:params,
		  	success:handleAlarmSuccess
			});
		return false;	 

	});
	
	
	
	var handleAlarmSuccess = function(data){
		$('#alertbodysectionForAlarm').show();
  		$('#imvgAlarmtable').find('tbody').text('');
		$('#rowdetailsalarm').text('');
		$('#nextlink').text('');
		$('#previouslink').text('');
		var startcount =$(data).find('startcount').text();
		var endcount =$(data).find('endcount').text();
		var totalcount =$(data).find('totalcount').text();
		var rowSize = $(data).find('rowSize').text();
		var displayrow = $(data).find('displayrow').text();
		var displayStart = $(data).find('displayStart').text();
		endCount = endcount;
		
		//alert("ABC");
		
		$(data).find("message").each(function() {
			var time = $(this).find('time').text();
			var devicename = $(this).find('devicename').text();
			var alertdetails = $(this).find('alert').text();
			var alertcommand = $(this).find('alertcommand').text();
			var rulecount = $(this).find('rulecount').text();
			var imvgalertid = $(this).find('imvgalertid').text();
			var deviceid = $(this).find('deviceid').text();
			var Alertid = $(this).find('alertid').text();
			
			<s:if test="#session.user.role.name == 'mainuser' ">
			var tablerows="<tr class='alertstabledatas'><td style='text-align:left;'>"+time+"</td><td style='text-align: left;'>"+devicename+"</td><td style='text-align: left;'>"+formatMessage(alertcommand)+"</td><td style='text-align:center;width:10px;'><input class='checkbox' name='alarm' type='checkbox' value='" + imvgalertid + "'></input></td></tr>";
			</s:if>
			<s:else>
			var tablerows="<tr class='alertstabledatas'><td style='text-align:left;'>"+time+"</td><td style='text-align: left;'>"+devicename+"</td><td style='text-align: left;'>"+formatMessage(alertcommand)+"</td></tr>";
			</s:else>
			$('#imvgAlarmtable').find('tbody').append(tablerows);
		});
		
		if(totalcount >= 10)
		{
			if((endcount*1)>displayrow){
				
				var previousvalue = (displayStart*1)-(displayrow*1);
				var previouslink ="<a class='previousnextLinkAlarm' href='GetAlarmsByDevice.action' displayStart="+previousvalue+"><img title='Newer' src='/imonitor/resources/images/back_enabled.jpg'></a>";
				$('#refresh').attr('displayStart',previousvalue);
			}
			else
			{
				var previouslink ="<img src='/imonitor/resources/images/back_disabled.jpg' title='inactive' >";
			}
			$('#previouslink').append(previouslink);
				
			if((totalcount*1)>endcount){
				
				var value = (displayStart*1)+(displayrow*1);
					var nextlinkdiv ="<a class='previousnextLinkAlarm' href='GetAlarmsByDevice.action' displayStart="+value+"><img title='Older' src='/imonitor/resources/images/forward_enabled.jpg'></a>";
				$('#refresh').attr('displayStart',value);
				}else{
					var nextlinkdiv ="<img src='/imonitor/resources/images/forward_disabled.jpg' title='inactive'>";
					}
					$('#nextlink').append(nextlinkdiv);
					var rowdetails ="<span><b>"+startcount+"</b>-<b>"+endcount+"</b>&nbsp;&nbsp;of&nbsp;&nbsp;<b>"+totalcount+"</b></span>";
			$('#rowdetailsalarm').append(rowdetails);
			
	  	}
		else
		{
			var previouslink ="<img src='/imonitor/resources/images/back_disabled.jpg' title='inactive' >";
			$('#previouslink').append(previouslink);
			var nextlinkdiv ="<img src='/imonitor/resources/images/forward_disabled.jpg' title='inactive'>";
			$('#nextlink').append(nextlinkdiv);
			var rowdetails ="<span><b>"+startcount+"</b>-<b>"+endcount+"</b>&nbsp;&nbsp;of&nbsp;&nbsp;<b>"+totalcount+"</b></span>";
			$('#rowdetailsalarm').append(rowdetails);
		}
		
		
	};
	
	$('#refresh').live('click', function(){
		
		var singleValues = $("#selctedDevice").val();
		var FromDate=$('input:text[name=FromDate]').val();
		var ToDate=$('input:text[name=ToDate]').val();

		if(FromDate=="")
		{
			if(ToDate=="")
			{
					if(singleValues==1)
					{
						alert(":::");
						listImvgAlerts();
						return false;
					}
			}
		}
		
		var url = $(this).attr('href');
		var displayStart = $(this).attr('displayStart');
		var form = $(this).closest("form");
		var params = form.serialize()+"&displayStart="+displayStart;
		alert(params+url);
		$.ajax({
			url:url,
			cache: false,
			dataType: 'xml',
			data: params,
			success: handleAlertSuccess
		});
		return false;
		
		listImvgAlerts();
	}); 



	
	var listImvgAlerts = function(){	
		var count = endCount*1;		
		if(count <= 10){
		$.ajax({
			url: "listAlarms.action",
			cache: false,
			dataType: 'xml',
			success: handleAlertSuccess
		});
		}
		else{
				$.ajax({
					url: "listAlarms.action",
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
	};
	//listImvgAlerts();

	
	$('.ajaxinlinepopupform').live('click', function() 
	{	 
							var singleValues = $("#selctedDevice").val();
							var FromDate=$('input:text[name=FromDate]').val();
							var ToDate=$('input:text[name=ToDate]').val();

							//vibhu start
							if(validateDates(FromDate, ToDate) == false) return false;
							//vibhu end
								var form = $(this).closest("form");
								var params = form.serialize()+"&displayStart=0";
								var url = $(this).attr('href');
								$.ajax({
									url: url,
								  	data:params,	  	
								  	success:function(data)
								  	{
								  		if(myRadio=='Alerts')
										{
								  			handleAlertSuccess(data);
								  			
										}
									  	else
									  	{
									  		handleAlarmSuccess(data);
									  		<s:if test="#session.user.role.name == 'mainuser' ">
										 	$("#button").show();
										 	</s:if>
									  	}
								  	}
								  	
									});
								 
								return false;	 	
	});*/
	
	
}
//vibhu end	
</script>
</head>
<body onload="noBack();" class="bodyclass">
	<body class="bodyclass">
	<div style="color: blue;"><p class="message"><s:property value="#session.message" /></p>
			<%ActionContext.getContext().getSession().remove("message"); %>
	</div>
		<div class="maindiv">			
			<div class="titlebg">
				<div class="welcomeuser">
					<span style="color:#0C5AA3;"><s:text name="home.msg.0009" /></span> <s:property value="#session.user.userId"/><br><span style="margin-left: -39px;color:#0C5AA3;"><s:text name="home.msg.0017" /></span><s:date name="#session.user.lastLoginTime" format="dd/MM/yy 'at' hh:mm a"/>
				</div>
				<div>
					<ul class="ulmenu topmenu" lihoverclass="hoverstyle">
						<li class="menuitem"><a href="home.action" title="Home" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.main" /></div></a></li>
						 <s:if test="#session.user.role.name == 'mainuser' ">
							<li class="menuitem"><a href="setup.action" title="To Configure Device" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.setup" /></div></a></li>
						</s:if>
						<li class="menuitem selectedtopmenu"><a href="getdeviceforalerts.action" title="View Alerts" class="menuitemnormal"><span id="testme"><div style="width=100%;height=100%;"><s:text name="general.alerts" /></div></span></a></li>
						<li class="menuitem"><a href="getlistofLocation.action" title="Prespective View" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.advance" /></div></a></li>
						<li class="menuitem" id="dashboard"><a href="getlistofLocationforEnergyMg.action" id="dash" title='<s:text name="Tooltip.Perspective.View" />' class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.dashboard" /></div></a></li>
                        <li class="menuitem"><a href="../logout.action" title="Sign Out" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.signout" /></div></a></li>					
					</ul>
				</div>
					<img class="logo" src="<%=applicationName %>/resources/images/logo.png"/>											
				<div>
					<img class="helpicon" title="help" src="<%=applicationName %>/resources/images/help.png" onclick='javascript: openHelpPage()' />
				</div>
			</div>
	<div class="alarmtiltle" >	
	<span id ="alarmname" class="alarmname" ><s:text name="alerts.title" /></span>
				</div>
	
<div id="contentsection" class="contentsection">		
<s:form>
<input type="radio" name="wisetype"  value="Alerts" class="radioAlarm" onclick="clear_form(this.form)"/><s:text name="alerts.alerts" />
<input type="radio" name="wisetype"  value="Alarm"  class='radioAlarm' onclick="clear_form(this.form)"/><s:text name="alerts.alarms" />
<input type="hidden" name="diff" value ="0" class="hiddenhtml" />
<%-- <button type='button' style="float: right" class='bt bbtn' id='refresh' title='click to Refresh' onclick="clear_form(this.form)" ><s:text name="alerts.refresh" /></button> --%>
<table id="mainform">
<tr>

<td><s:textfield key="alerts.select.from"  id="FromDate" name="FromDate" cssClass="datetime"  readonly="true" ></s:textfield></td>
<td><s:textfield key="alerts.select.to" id="ToDate" name="ToDate" readonly="true" cssClass="mydatepicker"></s:textfield></td>
<td class="tdLabel"><label for="selctedDevice" class="label"><s:text name="alerts.select.device" /></label></td>
<td><select name='selctedDevice' id = 'selctedDevice' class='selectAlertName'></select></br></td>
<%-- <td></br>
<a href='GetAlarmsByDevice.action' class='ajaxinlinepopupform'><button   type='button' class='bt bbtn' title='click to list Alarms'><s:text name="alerts.view" /></button></a>
</td> --%>
<td>
<s:if test="#session.user.role.name == 'mainuser' ">
<button style="margin: 0px -89px 0px 375px;width: 130px;" onClick='backuptableview()' type='button' class='bt bbtn backupAlerts' title='click to View older Alerts'><s:text name="Old Alerts" /></button>
</s:if>
<s:else>
<button style="margin: 0px -89px 0px 375px;width: 130px;"  onClick='backuptableview()' type='button' class='bt bbtn backupAlerts' title='click to View older Alerts'><s:text name="Old Alerts" /></button>
</s:else>
</td>

<td>
<s:if test="#session.user.role.name == 'mainuser' ">
<button style="margin: 0px -89px 0px 375px;width: 130px;" onClick='newAlertview()' type='button' class='bt bbtn newAlert' title='click to View New Alerts'><s:text name="New Alerts" /></button>
</s:if>
<s:else>
<button style="margin: 0px -89px 0px 375px;width: 130px;"  onClick='newAlertview()' type='button' class='bt bbtn newAlert' title='click to View New Alerts'><s:text name="New Alerts" /></button>
</s:else>
</td>
<td>
<s:if test="#session.user.role.name == 'mainuser' ">
<button style="margin: 0px 0px 0px 90px;width: 130px;display:flex" href='requetForPdf.action' type='button' class='bt bbtn requetForPdf ' title='click to Get Alerts in PDF'><s:text name="alerts.pdf" /></button>
</s:if>
<s:else>
<button href='requetForPdf.action' style="margin: 0px 0px 0px 90px;display:flex" type='button' class='bt bbtn requetForPdf' title='click to Get Alerts in PDF'><s:text name="alerts.pdf" /></button>
</s:else>


</td>
<td>
<s:if test="#session.user.role.name == 'mainuser' ">
<button style="margin: 0px 0px 0px 90px;width: 130px;display:flex" href='requetForOldPdf.action' type='button' class='bt bbtn requetForOldPdf ' title='click to Get Old Alerts in PDF'><s:text name="alerts.pdf" /></button>
</s:if>
<s:else>
<button href='requetForOldPdf.action' style="margin: 0px 0px 0px 90px;" type='button' class='bt bbtn requetForOldPdf' title='click to Get Old Alerts in PDF'><s:text name="alerts.pdf" /></button>
</s:else>
</td>

<td>
<s:if test="#session.user.role.name == 'mainuser' ">
<button href='DeleteSelectedAlarm' style="float: right;margin-right:-10px;" id='button' type='button' class='bt bbtn deletelink' title='click to delete'><s:text name="alerts.delete"/> </button>
</s:if>
<s:else>
</s:else>
</td>
<td>
<s:if test="#session.user.role.name == 'mainuser' ">
<button href='DeleteSelectedOldAlarm' style="float: right;margin-right:-10px;" id='button' type='button' class='bt bbtn deleteBackUplink' title='delete'><s:text name="alerts.delete"/> </button>
</s:if>
<s:else>
</s:else>
</td>



</tr>
</table>
<div id="AlertTable">
<table cellpadding="2" cellspacing="2" border="1"  class="display"  id="listalertstable">
	<thead>
		<tr>
			<th>ID</th>
				
			<th>Time</th>
			<th>Device</th>
			<th>Alerts</th>
			<th></th>
			<s:if test="#session.user.role.name == 'mainuser' ">
			<th style="text-align:center;"><input id="SelectallAlerts" type="checkbox" ></th>
			</s:if>
			<s:else>
			
		<th></th>
			</s:else>
			
		</tr>
	</thead>
	 <tbody>
		<tr>
			<td colspan="3">Data Loading ...</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th>ID</th>
			<th>Time</th>
			<th>Device</th>
			<th>Alerts</th>
			<th>  </th>
			<th></th>
			
		</tr>
	</tfoot>
</table>
</div>
<div id="AlertTableFromBackup">
<table cellpadding="2" cellspacing="2" border="1"  class="display"  id="listalertstablefrombackup">
	<thead>
		<tr>
			<th>ID</th>
				
			<th>Time</th>
			<th>Device</th>
			<th>Alerts</th>
			<th></th>
			<s:if test="#session.user.role.name == 'mainuser' ">
			<th style="text-align:center;"><input id="SelectallOldAlerts" type="checkbox" ></th>
			</s:if>
			<s:else>
			
		<th></th>
			</s:else>
			
		</tr>
	</thead>
	 <tbody>
	<!-- 	<tr>
			<td colspan="3">Data Loading ...</td>
		</tr> -->
		
	</tbody>
	<tfoot>
		<tr>
			<th>ID</th>
			<th>Time</th>
			<th>Device</th>
			<th>Alerts</th>
			<th>  </th>
			<th></th>
			
		</tr>
	</tfoot>
</table>
</div>
<div id="AlarmTable">
<table cellpadding="2" cellspacing="2" border="1"  class="display"  id="listalarmstable">
	<thead>
		<tr>
			<th>ID</th>
			<th>Time</th>
			<th>Device</th>
			<th>Alarms</th>
			<s:if test="#session.user.role.name == 'mainuser' ">
			<th style="text-align:center;"><input id="SelectAllAlarms" type="checkbox" ></th>
			</s:if>
			<s:else>
			<th></th>
			</s:else>
		</tr>
	</thead>
	 <tbody>
		<tr>
			<td colspan="3">Data Loading ...</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th>ID</th>
			<th>Time</th>
			<th>Device</th>
			<th>Alarms</th>
			<th>  </th>
		</tr>
	</tfoot>
</table>
</div>
<div id="AlarmTableFromBackup">
<table cellpadding="2" cellspacing="2" border="1"  class="display"  id="listalarmstablefrombackup">
	<thead>
		<tr>
			<th>ID</th>
			<th>Time</th>
			<th>Device</th>
			<th>Alarms</th>
			<s:if test="#session.user.role.name == 'mainuser' ">
			<th style="text-align:center;"><input id="SelectAllOldAlarms" type="checkbox" ></th>
			</s:if>
			<s:else>
			<th></th>
			
			</s:else>
		</tr>
	</thead>
	 <tbody>
		<tr>
			<td colspan="3">Data Loading ...</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th>ID</th>
			<th>Time</th>
			<th>Device</th>
			<th>Alarms</th>
			<th>  </th>
		</tr>
	</tfoot>
</table>
</div>


</div>

		<!--  		<div id="alertbodysection" class="alertscontantsection">
					<table id="imvgalerttable" class="alertstables">
					<thead>
					<tr>
				 		<th style="text-align: left;"class="alertstablecell"><s:text name="alerts.timestamp" /> </th>
				 		<th style="text-align: left;"><s:text name="alerts.device" /></th>
				 		<th style="text-align: left;"><s:text name="alerts.alerts" /></th>
				 		<s:if test="#session.user.role.name == 'mainuser' ">
				 		<th style="text-align:left; width:30px;"><input id="SelectAllAlerts" type="checkbox" ></th>
				 		</s:if>
					</tr>
					</thead>
			 		<tbody>
			 		</tbody>
			 		<tfoot>
			 		<tr>
				 		<th style="text-align: left;"class="alertstablecell"><s:text name="alerts.timestamp" /> </th>
				 		<th style="text-align: left;"><s:text name="alerts.device" /></th>
				 		<th style="text-align: left;"><s:text name="alerts.alerts" /></th>
				 		<s:if test="#session.user.role.name == 'mainuser' ">
				 		<th style="text-align:left; width:30px;"></th>
				 		</s:if>
					</tr>
					</tfoot>
					</table>
				<div id="allrowdetails">
					<div id="rowdetails" style="float: left;margin-left:10px;">
					</div>
					<div id="nextlinkdiv" style="float: right;margin-right: -1px;" >
					</div>
					<div id="previouslinkdiv" style="margin-right:5px;float: right;" >
					</div>
				</div>
			</div>
			
			<div id="alertbodysectionForAlarm" class="alertscontantsection">
					<table id="imvgAlarmtable" class="alertstables">
					<thead>
					<tr>
				 		<th style="text-align: left;"class="alertstablecell"><s:text name="alerts.timestamp" /> </th>
				 		<th style="text-align: left;"><s:text name="alerts.device" /></th>
				 		<th style="text-align: left;"><s:text name="alerts.alarms" /></th>
				 		<s:if test="#session.user.role.name == 'mainuser' ">
				 		<th style="text-align:left; width:30px;"><input id="SelectAll" type="checkbox" ></th>
						</s:if>					
					</tr>
					</thead>
			 		<tbody>
			 		</tbody>
			 		<tfoot>
			 		<tr>
				 		<th style="text-align: left;"class="alertstablecell"><s:text name="alerts.timestamp" /> </th>
				 		<th style="text-align: left;"><s:text name="alerts.device" /></th>
				 		<th style="text-align: left;"><s:text name="alerts.alarms" /></th>
				 		<s:if test="#session.user.role.name == 'mainuser' ">
				 		<th style="text-align:left; width:30px;"></th>
				 		</s:if>
				 		
					</tr>
					</tfoot>
					</table>
				<div id="allrowdetailsAlarm">
					<div id="rowdetailsalarm" style="float: left;margin-left:10px;">
					</div>
					<div id="nextlink" style="float: right;margin-right: -1px;" >
					</div>
					<div id="previouslink" style="margin-right:5px;float: right;" >
					</div>
				</div>
			</div>-->
</br></br></a>
</s:form>
<div id="overlay" onclick="closeoverlay()">
		</div>
			<div id="imageshowing"  class="imageshowdiv">
		</div>
<div id="confirm"></div>
<div id="message"></div>
</body>
</html>
