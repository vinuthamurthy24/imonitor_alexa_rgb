 <%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ page language="java" %>
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
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/jquery.ui.all.css" rel="stylesheet" />
		<link type="text/css" href="<%=applicationName %>/resources/css/demo_table.css" rel="stylesheet"/>
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/jquery-ui.css" rel="stylesheet" />	
		<link rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/in.xpeditions.css" />
		
		
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.7.1.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui-1.7.3.custom.min.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/ui.core.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/ui.draggable.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/ui.droppable.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/ui.sortable.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/effects.blind.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/effects.bounce.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/effects.slide.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.user.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/pace.min.js"></script>
		
 		<script src="<%=applicationName %>/resources/js/flotgraph/jquery.flot.js" type="text/javascript"></script>
		<script src="<%=applicationName %>/resources/js/flotgraph/jquery.flot.stack.js" type="text/javascript"></script>
		<script src="<%=applicationName %>/resources/js/flotgraph/jquery.flot.time.js" type="text/javascript"></script>
		<script src="<%=applicationName %>/resources/js/flotgraph/jquery.flot.axislabels.js" type="text/javascript"></script>
        <script src="<%=applicationName %>/resources/js/jquery.jqplot.min.js" type="text/javascript"></script>
		<script src="<%=applicationName %>/resources/js/jquery.in.xpeditions.accordion.js" type="text/javascript"></script>

<style>
		
	/* #defineActionTable td {padding:0px 1px 1px; text-align:center; }
	#defineActionTable td {border :1px solid #57D4DF; border-right:solid #57D4DF;}
	#defineActionTable tr.odd-row td {background:#f6f6f6;}
	#defineActionTable td.first, th.first {text-align:left}
	#defineActionTable td.last {border-right:none;}
	#instantTable td {padding:0px 5px 1px; text-align:center; }
	#instantTable td {border :2px solid #57D4DF; border-right:1px solid #57D4DF;}
	#instantTable tr.odd-row td {background:#f6f6f6;}
	#instantTable td.first, th.first {text-align:left}
	#instantTable td.last {border-right:none;} */
	
	#energytab{
	
	}
	.comparative{
	position: absolute;
	}
	#comparativedata{
	position: absolute;
    margin: 30px 0px 0px 435px;
    background-color: #64bcfc;
    width: 222px;
    height: 276px;
    border-radius: 7px;
    }
	.radiopie{
	position:absolute;
	margin:57px 0px 0px 736px;
	}
	.radiobar{
	position:absolute;
	margin:-20px 0px 0px 63px;
	width:450px;
	}
	#horizontal{
	width: 1372px;
    height: 0px;
    border-bottom: 1px solid black;
    position: absolute;
    margin-top: 315px;
	}
	#vertical{
	width: 0;
    height: 679px;
    position: absolute;
    border-left: 1px solid black;
    margin: 0px 0px 0px 664px;
	}
	#designline{
	height: 18px;
    position: absolute;
    border-left: 1px solid black;
    margin: -34px 0px 0px 264px;
	}
	#designlinefordownload{
	height: 20px;
    position: absolute;
    border-left: 1px solid black;
    margin: -35px 0px 0px 440px;
	}
	#seperateline{
	width: 220px;
    height: 0px;
    border-bottom: 2px solid black;
    position: absolute;
    margin-top: 125px;
	
	}
	
	#bottomline{
	width: 220px;
    height: 0px;
    border-bottom: 2px solid black;
    position: absolute;
    margin-top: 200px;
	
	}
	
	.chartselection{
			width: 61%;
			position:absolute;
			margin:2px 0px 0px 28px;
			background: #2E9ECD;
			display: -webkit-inline-box;
            height:52px;
			font-family:serif;
			border-radius:19px 04px
	}
	#chartContainer{
	margin:0px 0px 0px 0px;
	float:left;
	width:400px;
	height:200px;
	}
	.menuchart{
	display: table-cell;
	*display: inline;
	width:120px;
	height:20px;
	text-align:center;
	font-weight:650;
	padding-top:5px;
	padding-left:3px;	
	font-size:12px;
	font-weight:bold;
	}
	.targetchart{
	display: table-cell;
	*display: inline;
	width:200px;
	height:20px;
	text-align:center;
	font-weight:650;
	padding-left:2px;	
	font-size:12px;
	font-weight:bold;
	}
	.costchart{
	display: table-cell;
	*display: inline;
	width:200px;
	height:20px;
	text-align:center;
	font-weight:650;
	padding-left:2px;	
	font-size:12px;
	font-weight:bold;
	}
	
	#energyInfo{
	float:left;
	margin:37px 22px 0px 0px;
	}				
				
	#totalInfo{
	margin-top: -9px;
	}
			 
	#usageInfo{
	float:right;
	margin: -37px 0px 0px 504px;
	}		

 	#lastUpdateInfo{
	position: absolute;
    margin-left: 10px;
    margin-top: -33px;
    font-size: 9px;
	}
		 
	#UseageValue{
    width: 220px;
    float:left;
    margin: 408px 0px 0px -220px;
    background-color: rgb(139, 167, 179);
    height: 166px;
    border-radius: 13px;
	-webkit-box-shadow: 0px 0px 12px rgba(0, 0, 0, 5.4); 
	-moz-box-shadow: 0px 1px 6px rgba(23, 69, 88, .5);
	/* rounded corners */
	-webkit-border-radius: 12px;
	-moz-border-radius: 7px; 
	border-radius: 7px;
	}
	
	#UptoUsage{
    width: 262px;
    margin: -225px 0px 0px 724px;
    background-color: rgb(139, 167, 179);
    height: 224px;
    border-radius: 13px;
/* outer shadows  (note the rgba is red, green, blue, alpha) */
	-webkit-box-shadow: 0px 0px 12px rgba(0, 0, 0, 5.4); 
	-moz-box-shadow: 0px 1px 6px rgba(23, 69, 88, .5);
	/* rounded corners */
	-webkit-border-radius: 12px;
	-moz-border-radius: 7px; 
	border-radius: 7px;
	}
	
	#DetailDisplay{
    width: 233px;
   /*  margin: 6px 0px 0px 178px; */
    margin: -335px -29px 0px 399px;
   /*  background-color: rgb(139, 167, 179); */
    background-color:white;
    height: 156px;
    float:right;
    border-radius: 13px;
/* outer shadows  (note the rgba is red, green, blue, alpha) */
	-webkit-box-shadow: 0px 0px 12px rgba(0, 0, 0, 5.4); 
	-moz-box-shadow: 0px 1px 6px rgba(23, 69, 88, .5);
	/* rounded corners */
	-webkit-border-radius: 12px;
	-moz-border-radius: 7px; 
	border-radius: 7px;
	}

	.energymonitoring{
    width: 1372px;
  /*   margin: -226px 0px 0px 597px; */
    	margin: 1px 0px 0px -176px;
   /*  background-color: rgb(139, 167, 179); */
   	background-color:white;
    height: 680px;
    border-radius: 13px;
/* outer shadows  (note the rgba is red, green, blue, alpha) */
	-webkit-box-shadow: 0px 0px 12px rgba(0, 0, 0, 5.4); 
	-moz-box-shadow: 0px 1px 6px rgba(23, 69, 88, .5);
	/* rounded corners */
	-webkit-border-radius: 12px;
	-moz-border-radius: 7px; 
	border-radius: 7px;
	}

	#lastMonthUsageInfo{
    width: 220px;
    position:absolute;
    margin:133px 0px 0px 1px;
   /*  background-color: rgb(139, 167, 179); */
    height: 173px;
    /*  border-radius: 13px; */
/* outer shadows  (note the rgba is red, green, blue, alpha) */
	/*-webkit-box-shadow: 0px 0px 12px rgba(0, 0, 0, 5.4); 
	-moz-box-shadow: 0px 1px 6px rgba(23, 69, 88, .5);*/
	/* rounded corners */
	/*-webkit-border-radius: 12px;
	-moz-border-radius: 7px; 
	border-radius: 7px; */
	}
	
 	.graphs{
	width: 750px;
	height: 280px;
	float: right;
	margin: 141px -104px 0px 0px;
	}
	
	/* .tabs {
	width:100%;
	display:inline-block;
	}
	
	.tab-links li {
	margin: 1px 2px;
	float: left;
	list-style: none;
	padding: 3px 10px;
	display: inline-block;
	border-radius: 14px 14px 0px 0px;
	background: rgba(166, 172, 161, 0.95);
	font-size: 16px;
	font-weight: bolder;
	transition: all linear 0.15s;
	} */
	
     .tab-content {
		border-radius: 3px;
		width: 895px;
		margin: 1px 0px 0px 2px;
		height: 254px;
		float: left;
	    } 
    .Legend
		{	
			width: 67%;
			/* background: #2E9ECD; */
			display: -webkit-inline-box;
            height:42px;
			font-family:serif;
			position: absolute;
    		margin:35px 0px 0px -35px;
    		word-break: break-word;
    		width: 170px;
		}
	.legend{
			width: 67%;
			/* background: #2E9ECD; */
			display: -webkit-inline-box;
            height:42px;
			font-family:serif;
			position: absolute;
    		margin:432px 0px 0px 7px;
    		word-break: break-word;
    		width: 170px;
	
	}	
	.overviewlegend{
		width: 84%;

		}
	.overviewlegend table { 
		display: table;
		margin: 0 auto;
		
		 }

	.overviewlegend table tr { 
			font-size: 12px;
			color: #020000;
			
		 }	

	.overviewLegend{
		width: 84%;

		}
	.gphTitle{
		width: 50%;
		margin: -17px 0px 0px 307px;
		text-align: -webkit-center;
	}	

	.graphTitle{
		width: 50%;
		margin: -17px 0px 0px 307px;
		text-align: -webkit-center;
	}

	.overviewLegend table { 
		display: table;
		margin: 0 auto;
		
		 }

	.overviewLegend table tr { 
			font-size: 12px;
			color: #020000;
			
		 }
		 
    .legendColorBox 
		 {
			font-size: 12px;
			color: #020000;
		 }
	
	#graphTab{
	display: block;
	background-color: #615656;;
	text-align: center;
	font-weight: bold;
	margin: -1px 0px 19px 0px;
	width: 202px;
    height: 30px;
    margin-top: -14px;
	cursor: pointer;
	padding: 5 5 5 7px;
	list-style: circle;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 0px;
}

	body, input{
	font-family: Calibri, Arial;
	}
	
	#devicelist li{
	font-size: 12px;
	font-family: sans-serif;
	display: block;
	background-color: rgb(100, 188, 252);
	font-weight: bold;
	margin: 1px 0px 2px 0px;
	width: 210px;
	height: 45px;
	text-align: center;
	cursor: pointer;
	padding: 5 5 5 7px;
	list-style: circle;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 4px;
	}
	#devicelist ul li.accordionListclass{
	/* background-color: rgb(247, 140, 174); */
	background-color: rgb(5, 245, 242);
	}

	#devicelist.devicenameclass{
	background-color: rgb(156, 155, 155);
	}
	#devicelist ul {
	list-style: none;
	padding: 0 0 0 0;
	}
	
	#devicelist ul li{
	margin: 1px 0px 0px 0px;
	cursor: auto;
	/* background-color: rgb(196, 218, 233); */
	background-color:rgb(100, 188, 252);
	padding: 2px 0px 0px 0px;
	height: 39px;
	width: 210px;
	}

	#accordionList li{
	font-size: 12px;
	font-family: sans-serif;
	display: block;
	background-color: rgb(100, 188, 252);
	font-weight: bold;
	margin: 1px 0px 2px 0px;
	width: 210px;
	height: 45px;
	text-align: center;
	cursor: pointer;
	padding: 5 5 5 7px;
	list-style: circle;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 2px;
	}
	
	#accordionList ul {
	list-style: none;
	padding: 0 0 0 0;
	display: none;
	}
	
	#accordionList ul li{
	margin: 1px 0px 0px 13px;
	cursor: auto;
	background-color: rgb(196, 218, 233);
	padding: 2px 0px 0px 0px;
	height: 39px;
	width: 243px;
	}

	#accordionList ul li.accordionListclass{
	background-color: rgb(247, 140, 174);
	}

	#accordion.devicenameclass{
	background-color: rgb(156, 155, 155);
	}

	#accordionList li.devicenameclass{
	background-color: rgb(7, 229, 236);
	}

	#accordionList a {
	text-decoration: none;
	}
	
	#tabContainer {
	width: 810px;
	float: right;
	height: 38px;
	padding: 5px;
	background-color: #2e2e2e;
	-moz-border-radius: 4px;
	border-radius: 15px; 
}

	#tabContainerchange
	{
	width: 1015px;
	height: 46px;
	padding: 5px;
	background-color: rgb(216, 216, 216);
	-moz-border-radius: 4px;
	border-radius: 4px; 
	}
	
	.tabs{
	height:30px;
	}

	.tabs > ul{
	font-size: 1em;
	list-style:none;
	}
	.tabs > ul > li{
	float:left;
	}
	/* .tabs > ul > li{
	margin:0 2px 0 0;
	padding:2px 10px;
	display:block;
	float:left;
	color:#FFF;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
	-moz-border-radius-topleft: 4px;
	-moz-border-radius-topright: 4px;
	-moz-border-radius-bottomright: 0px;
	-moz-border-radius-bottomleft: 0px;
	border-top-left-radius:4px;
	border-top-right-radius: 4px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px; 
	background: #C9C9C9; /* old browsers */
	background: -moz-linear-gradient(top, #0C91EC 0%, #257AB6 100%); /* firefox */
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#0C91EC), color-stop(100%,#257AB6)); /* webkit */
	} */

	/* .tabs > ul > li:hover{
	background: #FFFFFF; /* old browsers */
	background: -moz-linear-gradient(top, #FFFFFF 0%, #F3F3F3 10%, #F3F3F3 50%, #FFFFFF 100%); /* firefox */
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#FFFFFF), color-stop(10%,#F3F3F3), color-stop(50%,#F3F3F3), color-stop(100%,#FFFFFF)); /* webkit */
	cursor:pointer;
	color: #333;
	} */

	 .tabs > ul > li.tabActiveHeader{
	background: #FFFFFF; /* old browsers */
	background: -moz-linear-gradient(top, #FFFFFF 0%, #F3F3F3 10%, #F3F3F3 50%, #FFFFFF 100%); /* firefox */
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#FFFFFF), color-stop(10%,#F3F3F3), color-stop(50%,#F3F3F3), color-stop(100%,#FFFFFF)); /* webkit */
	cursor:pointer;
	color: #333;
} 

	.graphHolder
	{
	width: 480px;
	height: 220px;
	margin-left: 150px;
	margin-top: 40px;
	}
.ComprativegraphHolder
	{
	width: 480px;
	height: 220px;
	margin-left: 174px;
	margin-top: 442px;
	position:absolute;
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



var Path="";
var DialogOpen =  {};
var deviceName="";
var clickedone="Total";
var selecttab="totalrow";
var selectdevice="";
var selectedup="weekly";
var selectedlabel="total";
var Type = "";
var previousPoint = null, previousLabel = null;


	$(document).ready(function() {
		
		
		$(".comparative").hide();
		$('#DateSelect').hide();
				
		$( "#hourdialog" ).dialog({
		height: 620,
		width: 1000,
		modal: true,
		autoOpen:false
		});
		
		$('.editlink').live('click', function(event){
		$('.tabActiveHeader').removeClass('tabActiveHeader');
		$('#week').addClass('tabActiveHeader');
		
		
		$('.accordionListclass').removeClass('accordionListclass'); 
	    $(this).closest('li').addClass('accordionListclass');
				
				var targeturl = $(this).attr('href');
				var labelforroom = $(this).attr('labelroom');
				var label = $(this).attr('label');
				
				var deviceId = $(this).attr('deviceId');
				selectdevice=deviceId;
				
				if(label === "Total")
				{
				clickedone="Total";
			    $('#hourli').click();
			    $("#radiotabs li input[name=radioline][value=hour]").attr('checked',true);
			    $('.Legend').show();
			    var day = new Date(); 
				day = day.toDateString();
			    $("#graphTitle").html("<span style='font-size: 15px;font-weight: bolder;'>"+day +"</span>");
				$('.devicenameclass').removeClass('devicenameclass'); 
		        $(this).closest('li').addClass('devicenameclass');
				}
				
				
				else 
				{
				clickedone="device";
				//targeturl="barchartweek.action";
				deviceName=deviceId;
				 $('#hourli').click();
				 $('.Legend').show();
				}
				
			
				
				
				
				return false;
			});

			
			$('ul li').click(function(){
            $('ul li.tabActiveHeader').removeClass('tabActiveHeader');
            $(this).closest('li').addClass('tabActiveHeader');
               });
			
			
			// Naveen added for New dashboard design
			$('#radiotabs li').live('click', function(e)  
				    {
			   Type = $(this).attr("data-type");
			   var targeturl = "";
			  
			  
			   /* if(Type == "Target"){
				   $('#DateSelect').hide();
				   targeturl = "GetTargetGraphOfCustomer.action";
				   $.ajax({
						url: targeturl,
						cache: false,
						dataType: 'xml',
						success:DrawTargetGraphs 	
					}); 
				   
			   }*/ 
			    if(clickedone == "Total"){
					
					targeturl = "GetHourlyDatafromDB.action";
					SendAjaxRequest(Type,clickedone,targeturl);
					
				}else if(clickedone == "room"){
					
					targeturl = "GetRoomWiseDataFromDB.action";
					sendAjaxRequestForRoom(Type,clickedone,targeturl,selectedlabel);
					
				}else if(clickedone == "device"){
					
					
					targeturl = "GetDeviceWiseDataFromDB.action";
					sendAjaxRequestForDevice(Type,clickedone,targeturl,deviceName);
					
				}
				    });
			
			var SendAjaxRequest = function(type,selectedType,url)
			{
				var params = {"type":type,"selectedType":selectedType};
				$.ajax({
					url: url,
					data: params,
					cache: false,
					dataType: 'xml',
					success:DrawGraphs 	
				}); 
			};
			
			var sendAjaxRequestForRoom = function(type,selectedType,url,label)
			{
				var params = {"type":type,"selectedType":selectedType,"labelroom":label};
				$.ajax({
					url: url,
					data: params,
					cache: false,
					dataType: 'xml',
					success:DrawGraphs 	
				}); 
			};
			
			var sendAjaxRequestForDevice = function(type,selectedType,url,device)
			{
				var params = {"type":type,"selectedType":selectedType,"Name":device};
				$.ajax({
					url: url,
					data: params,
					cache: false,
					dataType: 'xml',
					success:DrawGraphs 	
				}); 
			};
			
			
			var DrawTargetGraphs = function(xml)
			{
			
			var data=[];
			label ="";
			
			var cost = "";
			var date = "";
			$(xml).find("costdetails").each(function(index) 
					{
				
				
						cost =  $(this).find("cost").text();
						date =  $(this).find("alertTime").text();
						var totalticks=cost;
						data.push([date,totalticks]);					
						
									});
			
			var today = new Date();
			var currentYear=today.getFullYear();
			var currentmoth=today.getMonth();
			var currentdate=today.getDate();
			var month = new Array();
			month[0] = "January";
			month[1] = "February";
			month[2] = "March";
			month[3] = "April";
			month[4] = "May";
			month[5] = "June";
			month[6] = "July";
			month[7] = "August";
			month[8] = "September";
			month[9] = "October";
			month[10] = "November";
			month[11] = "December";
			var n = month[today.getMonth()];
			
			$('.Legend').show();
			$("#graphTitle").html("<span style='font-size: 15px;font-weight: bolder;'>Target graph for " +n +" "+ currentYear+"</span>");
		    
			
			$.plot("#graphHolder", [{
              data: data,label: "Cost in &#8377;",lines: { show: true},points: { show: true }
            }
        ],{            
		    		
		
			
			grid: {
				hoverable: true,
				clickable: true,
				backgroundColor: { colors: ["#D1D1D1", "#7A7A7A"] }
			},
			legend: {
		        container: $("#overviewLegend")
		    },
		
		
		    xaxis: {
	    	mode: "time",
			
			min: (new Date(currentYear, currentmoth,1 )).getTime(),
			max: (new Date(currentYear, currentmoth+1,1)).getTime(),
			
		    timeformat: "%d",
			tickSize: [1, "day"],
		
			axisLabelFontSizePixels: 30,
	    	axisLabelFontFamily: 'Verdana, Arial',
			axisLabelPadding: 10,
			axisLabelColour:'honeydew',
			},
		
		yaxis: {
		axisLabel: "Cost in rupees",
		axisLabelFontSizePixels: 35,
		axisLabelFontFamily: 'Verdana, Arial',
		axisLabelPadding: 3,
		axisLabelColour:'honeydew',
		
		},
			}
		); 
			 $("#graphHolder").bind("plothover", function (event, pos, item) {                        
			        if (item) {
			            if (previousPoint != item.dataIndex) {
			                previousPoint = item.dataIndex;
			 
			                $("#tooltip").remove();
			                 
			                var x = item.datapoint[0];
			                var y = item.datapoint[1];                
			 			 	
			                showTargetTooltip(item.pageX, item.pageY,
			                months[x-  1] + "<br/>" + "<strong>" + y + "</strong> (" + item.series.label + ")");
			            }
			        }
			        else {
			            $("#tooltip").remove();
			            previousPoint = null;
			        }
			    });
			 
			};
			
			
			   
		
			
			function showTargetTooltip(x, y, color, contents) {
				$('<div id="tooltip">' + contents + '</div>').css({
				position: 'absolute',
				
				top: y,
				left: x,
				border: '2px solid ' + color,
				padding: '3px',
				'border-radius': '5px',
				'background-color': '#fff',
				'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
				opacity: 0.9
				}).appendTo("body");
				};
			
	///////////////////////////////////////////////////////	
			var DrawComparativeGraph = function(xml)
			{
				var totalPower = 0;
				data=[];
				data1=[];
				data2=[];
				
			
				label ="";
				label1="";
				label2="";
				var today = new Date();
				var currentYear=today.getUTCFullYear();
			
				$(xml).find("device").each(function(index) 
				{
					
					if(index==0)
					{
					
					data=[];
					
					label =  $(this).find("name").text();
					//alert(label);
					var devices = $(this).find("pw");
					devices.each(function(index) 
					{
						var AlertValue=$(this).find("alertValue").text();
						
						var a = parseInt(AlertValue);
						var totalticks=AlertValue/1000;
						totalPower += a;
						
						totalticks=Math.round(totalticks*100)/100;
						
						var AlertTime=$(this).find("alertTime").text();
						
						data.push([AlertTime,totalticks]);
						
					});
					}
			
					
					if(index==1)
					{
					
					data1=[];
			
					label1 =  $(this).find("name").text();
					var devices = $(this).find("pw");
					devices.each(function(index) 
					{
						var AlertValue=$(this).find("alertValue").text();
						var a = parseInt(AlertValue);
						var totalticks=AlertValue/1000;
						totalPower += a;
						totalticks=Math.round(totalticks*100)/100;
						var AlertTime=$(this).find("alertTime").text();
			
						
						
			
						data1.push([AlertTime,totalticks]);
			
					});
					}
					
					if(index==2)
					{
					
					data2=[];
					label2 =  $(this).find("name").text();
					var devices = $(this).find("pw");
					devices.each(function(index) 
					{
						var AlertValue=$(this).find("alertValue").text();
						var a = parseInt(AlertValue);
						var totalticks=AlertValue/1000;
						totalPower += a;
						totalticks=Math.round(totalticks*100)/100;
						var AlertTime=$(this).find("alertTime").text();
			
						
						
			
						data2.push([AlertTime,totalticks]);
			
					});
					}
			
			
					
			
			
			
				
				
				
				
			
			
				});
				
				
				
				$('#ComprativegraphHolder').empty();
				$.plot("#ComprativegraphHolder",[
			{ data: data,  label:label,lines: {show: true },points: { show: true ,radius: 1 },color: '#E4287C'},
			{ data: data1, label:label1,lines: {show: true },points: { show: true,radius: 1 },color: '#0041C2'},
			{ data: data2, label:label2 ,lines: {show: true },points: { show: true,radius: 1 },color: '#ECB21B'},
			
				
				],{            
				grid: {
					hoverable: true,
					clickable: true,
					/* mouseActiveRadius: 30, */
					backgroundColor: { colors: ["#D1D1D1", "#7A7A7A"] }	
				},
				legend: {
			        
				container: $("#overviewlegend") 
			    },
			
			
			    xaxis: {
			    	mode: "time",
					min: (new Date(currentYear, 0,1 )).getTime(),
					max: (new Date(currentYear, 12,1)).getTime(),
			        tickSize: [1, 'month'],
			      //  ticks: [[1, "Jan"], [2, "Feb"], [3, "Mar"],[4, "april"],[5, "may"],[6,"jun"],[7,"july"],[8, "aug"],[9,"sep"],[10,"oct"],[11,"nov"],[12,"dec"]],
					axisLabelFontSizePixels: 30,
			    	axisLabelFontFamily: 'Verdana, Arial',
					axisLabelPadding: 10,
					axisLabelColour:'honeydew',
					},
				
			
			yaxes: [{
			position: "left",
			axisLabel: "Power Consumption in kWh",
			axisLabelFontSizePixels: 35,
			axisLabelFontFamily: 'Verdana, Arial',
			axisLabelPadding: 3,
			axisLabelColour:'honeydew',
			
			
			}/* , {
			position: "right",
			axisLabel: "Temperature &deg;C",
			        axisLabelFontSizePixels: 35,
			        axisLabelFontFamily: 'Verdana, Arial',
			        axisLabelPadding: 3,
					axisLabelColour:'honeydew',
			
			
			},  */
			],
				}
			);
				$("#ComprativegraphHolder").bind("plothover", function (event, pos, item) {
					if (item) {
					if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
					previousPoint = item.dataIndex;
					previousLabel = item.series.label;
					$("#tooltip").remove();
					var x = item.datapoint[0];
					var y = item.datapoint[1];
					var unit;
					var value;
					value=y;
					unit = "KW";
					var color = item.series.color;
					if(previousLabel=="Temp Sensor")
					{
					unit = "&deg;C";
					} 
					var today ;
					var dd = new Date(x).getDate();
					var mm = new Date(x).getMonth()+1; //January is 0!
					var yyyy = new Date(x).getFullYear();
					var hours = new Date(x).getHours();
					var minute = new Date(x).getMinutes();
					if(dd<10) {dd='0'+dd} 
					if(mm<10) {mm='0'+mm} 
					if(hours <10) {hours ='0'+dd}
					if(minute <10) {minute ='0'+mm} 
					today = mm+'/'+dd+'/'+yyyy;
					showTooltip(item.pageX, item.pageY, color,
					        "<strong>" + item.series.label + "</strong><br>" + today +
					        " : <strong>" + value + "</strong> " + unit + "");
					}
					} else {
					$("#tooltip").remove();
					previousPoint = null;
					}
					});
			}
			
			var DrawGraphs = function(xml)
			{
		
		
			var totalPower = 0;
			data=[];
			data1=[];
			data2=[];
			data3=[];
			data4=[];
			data5=[];
			data6=[];
			data7=[];
			data8=[];
			data9=[];
			data10=[];
			data11=[];
			data12=[];
			data13=[];
			data14=[];
			data15=[];
		
			label ="";
			label1="";
			label2="";
			label3="";
			label4="";
			label5="";
			label6="";
			label7="";
			label8="";
			label9="";
			label10="";
			label11="";
			label12="";
			label13="";
			label14="";
			label15="";
		
		
			
		
			$(xml).find("device").each(function(index) 
			{
				
				if(index==0)
				{
				
				data=[];
				
				label =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data.push([AlertTime,totalticks]);
					
				});
				}
		
				
				if(index==1)
				{
				
				data1=[];
		
				label1 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
		
					
					
		
					data1.push([AlertTime,totalticks]);
		
				});
				}
				
				if(index==2)
				{
				
				data2=[];
				label2 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
		
					
					
		
					data2.push([AlertTime,totalticks]);
		
				});
				}
		
		
				if(index==3)
				{
				
				data3=[];
				label3 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue); 
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data3.push([AlertTime,totalticks]); 
				});
				}
				if(index==4)
				{
				
				data4=[];
				label4 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					 data4.push([AlertTime,totalticks]); 
				});
				}
				if(index==5)
				{
					data5=[];
				
					label5 =  $(this).find("name").text();
					var devices = $(this).find("pw");
					devices.each(function(index) 
					{
						var AlertValue=$(this).find("alertValue").text();
						var a = parseInt(AlertValue);
						var totalticks=AlertValue/1000;
						totalPower += a;
						totalticks=Math.round(totalticks*100)/100;
						var AlertTime=$(this).find("alertTime").text();
						data5.push([AlertTime,totalticks]);
					});
				}
				if(index==6)
				{
				
				data6=[];
				label6 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data6.push([AlertTime,totalticks]);
				});
				}
		
				if(index==7)
				{
				
				data7=[];
				
				label7 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data7.push([AlertTime,totalticks]);
		
				});
				}
				if(index==8)
				{
				
				data8=[];
				
				label8 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data8.push([AlertTime,totalticks]);
		
				});
				}
				if(index==9)
				{
				
				data9=[];
				
				label9 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data9.push([AlertTime,totalticks]);
		
				});
				}
				if(index==10)
				{
				
				data10=[];
				
				label10 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data10.push([AlertTime,totalticks]);
		
				});
				}
				if(index==11)
				{
				
				data11=[];
				
				label11 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data11.push([AlertTime,totalticks]);
		
				});
				}
				if(index==12)
				{
				
				data12=[];
				
				label12 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data12.push([AlertTime,totalticks]);
		
				});
				}
				if(index==13)
				{
				
				data13=[];
				
				label13 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data13.push([AlertTime,totalticks]);
		
				});
				}
				if(index==14)
				{
				
				data14=[];
				
				label14 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data14.push([AlertTime,totalticks]);
		
				});
				}
				if(index==15)
				{
				
				data15=[];
				
				label15 =  $(this).find("name").text();
				var devices = $(this).find("pw");
				devices.each(function(index) 
				{
					var AlertValue=$(this).find("alertValue").text();
					var a = parseInt(AlertValue);
					var totalticks=AlertValue/1000;
					totalPower += a;
					totalticks=Math.round(totalticks*100)/100;
					var AlertTime=$(this).find("alertTime").text();
					data15.push([AlertTime,totalticks]);
		
				});
				}
		
		
			});
			
			$('#graphHolder').empty();
			var epochT = new Date().getTime();
		
			var today = new Date();
		
			var nextweek = new Date(today.getFullYear(), today.getMonth(), today.getDate(),23,59,59).getTime();
			var lastweek = new Date(today.getFullYear(), today.getMonth(), today.getDate(),0,0,0).getTime();
		
			totalPower = totalPower/1000;
		
		
		if(Type=="hour")
		{
			var day = new Date(); 
			day = day.toDateString();
			var hrs = new Date().getHours();
			var amd = "AM";
			$('#DateSelect').hide();
			//$("#DetailDisplay").html("<span id='DisplayLabel' style='font-size:x-large;font-weight:bold;padding:11px'>Total Power Consumption on "+day+
			                    // " upto "+hrs+":00</span>");
			$("#totalPower").html(totalPower+" kWh");
			$("#graphTitle").html("<span style='font-size: 15px;font-weight: bolder;font-family:serif;'>"+day+"</span>");
			
			$.plot("#graphHolder",[
		{ data: data,  label:label,lines: {show: true },points: { show: true ,radius: 1 },color: '#E4287C'},
		{ data: data1, label:label1,lines: {show: true },points: { show: true,radius: 1 },color: '#0041C2'},
		{ data: data2, label:label2 ,lines: {show: true },points: { show: true,radius: 1 },color: '#ECB21B'},
		{ data: data3, label:label3 ,lines: {show: true },points: { show: true,radius: 1 },color: '#009900'},
		{ data: data4, label:label4 ,lines: {show: true },points: { show: true,radius: 1 },color: '#99FF33'},
		{ data: data5, label:label5 ,lines: {show: true },points: { show: true,radius: 1 },color: '#00478F'},
		{ data: data6, label:label6 ,lines: {show: true },points: { show: true,radius: 1 },color: '#00478F'},
		{ data: data7, label:label7 ,lines: {show: true },points: { show: true,radius: 1 },color: '#4F4343'},
		{ data: data8, label:label8 ,lines: {show: true },points: { show: true,radius: 1 },color: '#4BF712'},
		{ data: data9, label:label9 ,lines: {show: true },points: { show: true,radius: 1 },color: '#234B16'},
		{ data: data10, label:label10 ,lines: {show: true },points: { show: true,radius: 1 },color: '#E1DB20'},
		{ data: data11, label:label11 ,lines: {show: true },points: { show: true,radius: 1 },color: '#E12040'},
		{ data: data12, label:label12 ,lines: {show: true },points: { show: true,radius: 1 },color: '#272D44'},
		{ data: data13, label:label13 ,lines: {show: true },points: { show: true,radius: 1 },color: '#F631FA'},
		{ data: data14, label:label14 ,lines: {show: true },points: { show: true,radius: 1 },color: '#FA9C31'},
		{ data: data15, label:label15 ,lines: {show: true },points: { show: true,radius: 1 },color: '#7DA3A2'}
			
			],{            
			grid: {
				hoverable: true,
				clickable: true,
				/* mouseActiveRadius: 30, */
				backgroundColor: { colors: ["#D1D1D1", "#7A7A7A"] }	
			},
			legend: {
		        
			container: $("#overviewLegend") 
		    },
		
		
		    xaxis: {
		    	mode: "time",
				timeformat: "%H",
				tickSize: [1, Type],
				twelveHourClock: true,
				min:lastweek  , // time right now - 24 hours ago in milliseonds
				max:nextweek  ,
				timezone: "browser" // switch to using local time on plot
					,axisLabelFontSizePixels: 30,
		        	axisLabelFontFamily: 'Verdana, Arial',
					axisLabelPadding: 10,
					axisLabelColour:'honeydew',
					},
			
		
		yaxes: [{
		position: "left",
		axisLabel: "Power Consumption in kWh",
		axisLabelFontSizePixels: 35,
		axisLabelFontFamily: 'Verdana, Arial',
		axisLabelPadding: 3,
		axisLabelColour:'honeydew',
		
		
		}/* , {
		position: "right",
		axisLabel: "Temperature &deg;C",
		        axisLabelFontSizePixels: 35,
		        axisLabelFontFamily: 'Verdana, Arial',
		        axisLabelPadding: 3,
				axisLabelColour:'honeydew',
		
		
		},  */
		],
			}
		);
			$("#graphHolder").bind("plothover", function (event, pos, item) {
				if (item) {
				if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
				previousPoint = item.dataIndex;
				previousLabel = item.series.label;
				$("#tooltip").remove();
				var x = item.datapoint[0];
				var y = item.datapoint[1];
				var unit;
				var value;
				value=y;
				unit = "KW";
				var color = item.series.color;
				if(previousLabel=="Temp Sensor")
				{
				unit = "&deg;C";
				} 
				var today ;
				var dd = new Date(x).getDate();
				var mm = new Date(x).getMonth()+1; //January is 0!
				var yyyy = new Date(x).getFullYear();
				var hours = new Date(x).getHours();
				var minute = new Date(x).getMinutes();
				if(dd<10) {dd='0'+dd} 
				if(mm<10) {mm='0'+mm} 
				if(hours <10) {hours ='0'+dd}
				if(minute <10) {minute ='0'+mm} 
				today = mm+'/'+dd+'/'+yyyy;
				showTooltip(item.pageX, item.pageY, color,
				        "<strong>" + item.series.label + "</strong><br>" + today +
				        " : <strong>" + value + "</strong> " + unit + "");
				}
				} else {
				$("#tooltip").remove();
				previousPoint = null;
				}
				});
				
		}
		 else if(Type=="day")
			{

		$('#DateSelect').hide();	
		var today = new Date();

			var nextweek = new Date(today.getFullYear(), today.getMonth(), today.getDate()+1).getTime();
			var lastweek = new Date(today.getFullYear(), today.getMonth(), today.getDate()-7).getTime();

			var curr = new Date; 
			var lastwk=new Date;
			var first = curr.getDate() - curr.getDay(); // First day is the day of the month - the day of the week
			var last = first + 6; // last day is the first day + 6
			var firstday = new Date(curr.setDate(first)).getTime();
			var lastday = new Date(curr.setDate(last)).getTime();
			lastwk.setDate(last);
				

				
			/* $('.Legend').show(); */
			$("#graphTitle").html("<span style='font-size: 15px;font-weight: bolder;font-family:serif;'>"+today.getFullYear()+"-"+(today.getMonth()+1)+"-"+first + " to "+ today.getFullYear()+"-"+(today.getMonth()+1)+"-"+last+"</span>");
			$("#totalPower").html(totalPower+" kWh");
			
				$.plot("#graphHolder",[
				                  	{ data: data,  label:label,lines: {show: true },points: { show: true },color: '#E4287C'},
					{ data: data1, label:label1,lines: {show: true },points: { show: true },color: '#0041C2'},
					{ data: data2, label:label2 ,lines: {show: true },points: { show: true },color: '#ECB21B'},
					{ data: data3, label:label3 ,lines: {show: true },points: { show: true },color: '#009900'},
					{ data: data4, label:label4 ,lines: {show: true },points: { show: true },color: '#99FF33'},
					{ data: data5, label:label5 ,lines: {show: true },points: { show: true },color: '#00478F'},
					{ data: data6, label:label6 ,lines: {show: true },points: { show: true },color: '#00478F'},
					{ data: data7, label:label7 ,lines: {show: true },points: { show: true,radius: 1 },color: '#4F4343'},
					{ data: data8, label:label8 ,lines: {show: true },points: { show: true,radius: 1 },color: '#4BF712'},
					{ data: data9, label:label9 ,lines: {show: true },points: { show: true,radius: 1 },color: '#234B16'},
					{ data: data10, label:label10 ,lines: {show: true },points: { show: true,radius: 1 },color: '#E1DB20'},
					{ data: data11, label:label11 ,lines: {show: true },points: { show: true,radius: 1 },color: '#E12040'},
					{ data: data12, label:label12 ,lines: {show: true },points: { show: true,radius: 1 },color: '#272D44'},
					{ data: data13, label:label13 ,lines: {show: true },points: { show: true,radius: 1 },color: '#F631FA'},
					{ data: data14, label:label14 ,lines: {show: true },points: { show: true,radius: 1 },color: '#FA9C31'},
					{ data: data15, label:label15 ,lines: {show: true },points: { show: true,radius: 1 },color: '#7DA3A2'}],{            
			    		

				
				grid: {
					hoverable: true,
					clickable: true,
					/* mouseActiveRadius: 30, */
					backgroundColor: { colors: ["#D1D1D1", "#7A7A7A"] }				},
				legend: {
					container: $("#overviewLegend") 
			    },


			    xaxis: {
			    	mode: "time",
					tickSize: [1, Type],
					min: lastweek,
				    max: nextweek,
					timeformat: "%a"
				
				,axisLabelFontSizePixels: 30,
			axisLabelFontFamily: 'Verdana, Arial',
			axisLabelPadding: 10,
			axisLabelColour:'honeydew',
			},

			yaxis: {
			axisLabel: "Power Consumption in kWh",
			axisLabelFontSizePixels: 35,
			axisLabelFontFamily: 'Verdana, Arial',
			axisLabelPadding: 3,
			axisLabelColour:'honeydew',
			},
				}
			);
			
			$("#graphHolder").bind("plothover", function (event, pos, item) {
				if (item) {
				if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
				previousPoint = item.dataIndex;
				previousLabel = item.series.label;
				$("#tooltip").remove();
				var x = item.datapoint[0];
				var y = item.datapoint[1];
				var unit;
				var value;
				value=y;
				unit = "KW";
				var color = item.series.color;
				if(previousLabel=="Temp Sensor")
				{
				unit = "&deg;C";
				} 
				var today ;
				var dd = new Date(x).getDate();
				var mm = new Date(x).getMonth()+1; //January is 0!
				var yyyy = new Date(x).getFullYear();
				var hours = new Date(x).getHours();
				var minute = new Date(x).getMinutes();
				if(dd<10) {dd='0'+dd} 
				if(mm<10) {mm='0'+mm} 
				if(hours <10) {hours ='0'+dd}
				if(minute <10) {minute ='0'+mm} 
				today = mm+'/'+dd+'/'+yyyy;
				showTooltip(item.pageX, item.pageY, color,
				        "<strong>" + item.series.label + "</strong><br>" + today +
				        " : <strong>" + value + "</strong> " + unit + "");
				}
				} else {
				$("#tooltip").remove();
				previousPoint = null;
				}
				});
				
			}
		else if(Type=="month")
		{
		
		$('#DateSelect').hide();	
		var today = new Date();
		var currentYear=today.getFullYear();
		var currentmoth=today.getMonth();
		var currentdate=today.getDate();
		var month = new Array();
		month[0] = "January";
		month[1] = "February";
		month[2] = "March";
		month[3] = "April";
		month[4] = "May";
		month[5] = "June";
		month[6] = "July";
		month[7] = "August";
		month[8] = "September";
		month[9] = "October";
		month[10] = "November";
		month[11] = "December";
		var n = month[today.getMonth()];
		
		$("#totalPower").html(totalPower+" kWh");
		//$("#displayname").html("Total power for "+ n);
		$('.Legend').show();
		$("#graphTitle").html("<span style='font-size: 15px;font-weight: bolder;font-family:serif;'>Month: " +n +" "+ currentYear+"</span>");
		
			$.plot("#graphHolder", [
					{ data: data,  label:label,lines: {show: true },points: { show: true },color: '#E4287C'},
					{ data: data1, label:label1,lines: {show: true },points: { show: true },color: '#0041C2'}, 
					{ data: data2, label:label2 ,lines: {show: true },points: { show: true },color: '#ECB21B'},
					{ data: data3, label:label3 ,lines: {show: true },points: { show: true },color: '#009900'},
					{ data: data4, label:label4 ,lines: {show: true },points: { show: true },color: '#99FF33'},
					{ data: data5, label:label5 ,lines: {show: true },points: { show: true },color: '#00478F'},
					{ data: data6, label:label6 ,lines: {show: true },points: { show: true },color: '#00478F'}, 
					{ data: data7, label:label7 ,lines: {show: true },points: { show: true,radius: 1 },color: '#4F4343'},
					{ data: data8, label:label8 ,lines: {show: true },points: { show: true,radius: 1 },color: '#4BF712'},
					{ data: data9, label:label9 ,lines: {show: true },points: { show: true,radius: 1 },color: '#234B16'},
					{ data: data10, label:label10 ,lines: {show: true },points: { show: true,radius: 1 },color: '#E1DB20'},
					{ data: data11, label:label11 ,lines: {show: true },points: { show: true,radius: 1 },color: '#E12040'},
					{ data: data12, label:label12 ,lines: {show: true },points: { show: true,radius: 1 },color: '#272D44'},
					{ data: data13, label:label13 ,lines: {show: true },points: { show: true,radius: 1 },color: '#F631FA'},
					{ data: data14, label:label14 ,lines: {show: true },points: { show: true,radius: 1 },color: '#FA9C31'},
					{ data: data15, label:label15 ,lines: {show: true },points: { show: true,radius: 1 },color: '#7DA3A2'}             ],{            
		    		
		
			
			grid: {
				hoverable: true,
				clickable: true,
				/* mouseActiveRadius: 30, */
				backgroundColor: { colors: ["#D1D1D1", "#7A7A7A"] }		
			},
			legend: {
		        container: $("#overviewLegend")
		    },
		
		
		    xaxis: {
		    		mode: "time",
				
				min: (new Date(currentYear, currentmoth,1 )).getTime(),
				max: (new Date(currentYear, currentmoth+1,1)).getTime(),
				
			    timeformat: "%d",
				tickSize: [1, "day"],
			
				axisLabelFontSizePixels: 30,
		    	axisLabelFontFamily: 'Verdana, Arial',
				axisLabelPadding: 10,
				axisLabelColour:'honeydew',
				tickOptions:{
					formatString:'%s'}, 
				},
			
		yaxis: {
		    axisLabel: "Power Consumption in kWh",
		    axisLabelFontSizePixels: 35,
		    axisLabelFontFamily: 'Verdana, Arial',
		    axisLabelPadding: 3,
			axisLabelColour:'honeydew',
			tickOptions:{
				formatString:'%f'},
		},
			}
		);
			$("#graphHolder").bind("plothover", function (event, pos, item) {
				if (item) {
				if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
				previousPoint = item.dataIndex;
				previousLabel = item.series.label;
				$("#tooltip").remove();
				var x = item.datapoint[0];
				var y = item.datapoint[1];
				var unit;
				var value;
				value=y;
				unit = "KW";
				var color = item.series.color;
				if(previousLabel=="Temp Sensor")
				{
				unit = "&deg;C";
				} 
				var today ;
				var dd = new Date(x).getDate();
				var mm = new Date(x).getMonth()+1; //January is 0!
				var yyyy = new Date(x).getFullYear();
				var hours = new Date(x).getHours();
				var minute = new Date(x).getMinutes();
				if(dd<10) {dd='0'+dd} 
				if(mm<10) {mm='0'+mm} 
				if(hours <10) {hours ='0'+dd}
				if(minute <10) {minute ='0'+mm} 
				today = mm+'/'+dd+'/'+yyyy;
				showTooltip(item.pageX, item.pageY, color,
				        "<strong>" + item.series.label + "</strong><br>" + today +
				        " : <strong>" + value + "</strong> " + unit + "");
				}
				} else {
				$("#tooltip").remove();
				previousPoint = null;
				}
				});
				
		}
	
		else if(Type=="year")
		{
			
		
		$('#DateSelect').hide();
		var today = new Date();
		var currentYear=today.getFullYear();
		var currentmoth=today.getMonth();
		var currentdate=today.getDate();
		$("#totalPower").html(totalPower+" kWh");
		
		//$("#displayname").html("Total power for "+ currentYear);
		$("#displayDate").html("<span style='color: red; font-size: small;'>*</span>Run report for the year: " +currentYear);
		
		$('.Legend').show();
		$("#graphTitle").html("<span style='font-size: 15px;font-weight: bolder;font-family:serif;'> Year: " +currentYear+"</span>");
			$.plot("#graphHolder",[
				{ data: data,  label:label,lines: {show: true },points: { show: true },color: '#E4287C'},
				{ data: data1, label:label1,lines: {show: true },points: { show: true },color: '#0041C2'},
				{ data: data2, label:label2 ,lines: {show: true },points: { show: true },color: '#ECB21B'},
				{ data: data3, label:label3 ,lines: {show: true },points: { show: true },color: '#009900'},
				{ data: data4, label:label4 ,lines: {show: true },points: { show: true },color: '#99FF33'},
				{ data: data5, label:label5 ,lines: {show: true },points: { show: true },color: '#00478F'},
				{ data: data6, label:label6 ,lines: {show: true },points: { show: true },color: '#00478F'} ,
				{ data: data7, label:label7 ,lines: {show: true },points: { show: true,radius: 1 },color: '#4F4343'},
				{ data: data8, label:label8 ,lines: {show: true },points: { show: true,radius: 1 },color: '#4BF712'},
				{ data: data9, label:label9 ,lines: {show: true },points: { show: true,radius: 1 },color: '#234B16'},
				{ data: data10, label:label10 ,lines: {show: true },points: { show: true,radius: 1 },color: '#E1DB20'},
				{ data: data11, label:label11 ,lines: {show: true },points: { show: true,radius: 1 },color: '#E12040'},
				{ data: data12, label:label12 ,lines: {show: true },points: { show: true,radius: 1 },color: '#272D44'},
				{ data: data13, label:label13 ,lines: {show: true },points: { show: true,radius: 1 },color: '#F631FA'},
				{ data: data14, label:label14 ,lines: {show: true },points: { show: true,radius: 1 },color: '#FA9C31'},
				{ data: data15, label:label15 ,lines: {show: true },points: { show: true,radius: 1 },color: '#7DA3A2'}                 
				],{            
		    		
		
			
			grid: {
				hoverable: true,
				clickable: true,
				/* mouseActiveRadius: 30, */
				backgroundColor: { colors: ["#D1D1D1", "#7A7A7A"] }		
			},
			legend: {
		       container: $("#overviewLegend")
		    },
		
		
		    xaxis: {
		    	mode: "time",
				min: (new Date(currentYear, 0,1 )).getTime(),
				max: (new Date(currentYear+1, 0,1)).getTime(),
			   
				tickSize: [1, "month"],
			
				axisLabelFontSizePixels: 30,
		    	axisLabelFontFamily: 'Verdana, Arial',
				axisLabelPadding: 10,
				axisLabelColour:'honeydew',
				},
			
		yaxis: {
		    axisLabel: "Power Consumption in kWh",
		    axisLabelFontSizePixels: 35,
		    axisLabelFontFamily: 'Verdana, Arial',
		    axisLabelPadding: 3,
			axisLabelColour:'honeydew',
		    
		},
			}
		);
		
			$("#graphHolder").bind("plothover", function (event, pos, item) {
				if (item) {
				if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
				previousPoint = item.dataIndex;
				previousLabel = item.series.label;
				$("#tooltip").remove();
				var x = item.datapoint[0];
				var y = item.datapoint[1];
				var unit;
				var value;
				value=y;
				unit = "KW";
				var color = item.series.color;
				if(previousLabel=="Temp Sensor")
				{
				unit = "&deg;C";
				} 
				var today ;
				var dd = new Date(x).getDate();
				var mm = new Date(x).getMonth()+1; //January is 0!
				var yyyy = new Date(x).getFullYear();
				var hours = new Date(x).getHours();
				var minute = new Date(x).getMinutes();
				if(dd<10) {dd='0'+dd} 
				if(mm<10) {mm='0'+mm} 
				if(hours <10) {hours ='0'+dd}
				if(minute <10) {minute ='0'+mm} 
				today = mm+'/'+dd+'/'+yyyy;
				showTooltip(item.pageX, item.pageY, color,
				        "<strong>" + item.series.label + "</strong><br>" + today +
				        " : <strong>" + value + "</strong> " + unit + "");
				}
				} else {
				$("#tooltip").remove();
				previousPoint = null;
				}
				});
				
		} else if(Type=="custom")
		{
			
			$.plot("#graphHolder",[{ data: data,  label:label,lines: {show: true },points: { show: true },color: '#E4287C'},
{ data: data1, label:label1,lines: {show: true },points: { show: true },color: '#0041C2'},
{ data: data2, label:label2 ,lines: {show: true },points: { show: true },color: '#ECB21B'},
{ data: data3, label:label3 ,lines: {show: true },points: { show: true },color: '#009900'},
{ data: data4, label:label4 ,lines: {show: true },points: { show: true },color: '#99FF33'},
{ data: data5, label:label5 ,lines: {show: true },points: { show: true },color: '#00478F'},
{ data: data6, label:label6 ,lines: {show: true },points: { show: true },color: '#00478F'},
{ data: data7, label:label7 ,lines: {show: true },points: { show: true,radius: 1 },color: '#4F4343'},
{ data: data8, label:label8 ,lines: {show: true },points: { show: true,radius: 1 },color: '#4BF712'},
{ data: data9, label:label9 ,lines: {show: true },points: { show: true,radius: 1 },color: '#234B16'},
{ data: data10, label:label10 ,lines: {show: true },points: { show: true,radius: 1 },color: '#E1DB20'},
{ data: data11, label:label11 ,lines: {show: true },points: { show: true,radius: 1 },color: '#E12040'},
{ data: data12, label:label12 ,lines: {show: true },points: { show: true,radius: 1 },color: '#272D44'},
{ data: data13, label:label13 ,lines: {show: true },points: { show: true,radius: 1 },color: '#F631FA'},
{ data: data14, label:label14 ,lines: {show: true },points: { show: true,radius: 1 },color: '#FA9C31'},
{ data: data15, label:label15 ,lines: {show: true },points: { show: true,radius: 1 },color: '#7DA3A2'}],{            
		    		

			
			grid: {
				hoverable: true,
				clickable: true,
				backgroundColor: { colors: ["#D1D1D1", "#7A7A7A"] }
			},
			legend: {
		        
				container: $("#overviewLegend") 
			    },


		    xaxis: {
		    	mode: "time",
				tickSize: [1, "day"],
				timeformat: "%d",

				axisLabelFontSizePixels: 30,
		    	axisLabelFontFamily: 'Verdana, Arial',
				axisLabelPadding: 10,
				axisLabelColour:'honeydew',
				},
			
		yaxis: {
		    axisLabel: "Power Consumption in kWh",
		    axisLabelFontSizePixels: 35,
		    axisLabelFontFamily: 'Verdana, Arial',
		    axisLabelPadding: 3,
			axisLabelColour:'honeydew',
		    
		},
			}
		);
			$("#graphHolder").bind("plothover", function (event, pos, item) {
				if (item) {
				if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
				previousPoint = item.dataIndex;
				previousLabel = item.series.label;
				$("#tooltip").remove();
				var x = item.datapoint[0];
				var y = item.datapoint[1];
				var unit;
				var value;
				value=y;
				unit = "KW";
				var color = item.series.color;
				if(previousLabel=="Temp Sensor")
				{
				unit = "&deg;C";
				} 
				var today ;
				var dd = new Date(x).getDate();
				var mm = new Date(x).getMonth()+1; //January is 0!
				var yyyy = new Date(x).getFullYear();
				var hours = new Date(x).getHours();
				var minute = new Date(x).getMinutes();
				if(dd<10) {dd='0'+dd} 
				if(mm<10) {mm='0'+mm} 
				if(hours <10) {hours ='0'+dd}
				if(minute <10) {minute ='0'+mm} 
				today = mm+'/'+dd+'/'+yyyy;
				showTooltip(item.pageX, item.pageY, color,
				        "<strong>" + item.series.label + "</strong><br>" + today +
				        " : <strong>" + value + "</strong> " + unit + "");
				}
				} else {
				$("#tooltip").remove();
				previousPoint = null;
				}
				});			
		}; 
			};
		
			
		
			function showTooltip(x, y, color, contents) {
				$('<div id="tooltip">' + contents + '</div>').css({
				position: 'absolute',

				top: y,
				left: x,
				border: '2px solid ' + color,
				padding: '3px',
				'font-size': '15px',
				'border-radius': '5px',
				'background-color': '#fff',
				'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
				opacity: 0.9
				}).appendTo("body");
				};
			
			
			//End New dashboard design
			
			$('.editlinkw').live('click', function(event){
				
				var targeturl = $(this).attr('href');
				
				if(clickedone == "room")
				{
				
				targeturl="piechartroom.action";
				}
				else if(clickedone == "Total")
				{
			
				targeturl="piechart.action";
				}
				else if(clickedone == "device")
				{
				
				targeturl = $(this).attr('href');
				
				}
				var label=$(this).attr('label');
				var labelforroom = $(this).attr('labelroom');
				selectedup=label;
				var params = {"labelroom":labelforroom,"Name":deviceName};
		      //  userDetails.generatelabel(labelforroom);	
				
				try{
					if(event.handled !== true){
						$.ajax({
							  url: targeturl,
				              data:params,
							  success: function(data){
								
								$('#GraphDisplay').html(data);
								
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
			
			
			
				$('.hourlyData').live('click', function(event){
				
				var targeturl = $(this).attr('href');
				
				
				var label=$(this).attr('label');
				var labelforroom = $(this).attr('labelroom');
				selectedup=label;
				var params = {"labelroom":labelforroom,"Name":deviceName};
		      	
				
				try{
					if(event.handled !== true){
						$.ajax({
							  url: targeturl,
				              data:params,
							  success: function(data){
								
								$('#GraphDisplay').html(data);
								
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
			
	$('.ajaxinlinepopupform').live('submit', function() {
		var form = $(this).closest("form");
		 var params = form.serialize();
		 $.post(form.attr('action'), params, function(data){
			var a= $(data).find("p.message").text();
			var aa = a.split("~");
			if(aa[0] =='Failure'){
				$('#editmodal').dialog('close');
						//$("#ErrorMessage").html(aa[1]);
						showResultAlert(aa[1]);
				}
			else if(aa[0] == 'Sucess')
				{
					$('#editmodal').dialog('close');
						showResultAlert(aa[1]);
				}
				$('#contentsection').html(data);
			 // If the popup is open, close it.
				$('#editmodal').dialog('close');
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
			Ok:function(){
				$(this).dialog('close');
				$("#confirm").html("");
				$("#confirm").dialog("destroy");
			}
		}
		});
	}
			
            $('.editlinkForTarrif').live('click', function(event){
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
	
            
            $('.PopUpforHourlydata').live('click', function(event){
				
				var targeturl = $(this).attr('href');
				var pHeight = $(this).attr('popupheight');
				var pWidth = $(this).attr('popupwidth');
				var pTitle = $(this).attr('popuptitle');
				
				if(pHeight == undefined){
					pHeight = 620;
					
				}
				if(pWidth == undefined){
					pWidth = 1000;
					
				}
				if(pTitle == undefined){
					pTitle = "<s:text name='setup.msg.0001' />";
					
				}  

				try{
					
						$.ajax({
							  url: targeturl,
							  success: function(data){
								
								$('#hourdialog').html(data);
				 				$('#hourdialog' ).dialog('destroy');
				 				$('#hourdialog').dialog({height: pHeight, width: pWidth, modal:true});
							  }
						});
						
					
				}catch(ex){
					alert("Some Exception was caught.");
				}
				finally{}
				
			});
            

            
            
		
		Path='<s:property value="imvgUploadContextPath"/>';
		

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
	$('.tabActiveHeader').removeClass('tabActiveHeader');
	$('#week').addClass('tabActiveHeader');
		//arrangeGateWaySectionForSetup(xml);
		// 1. Handling the gatewy.
		
		//var serializer = new XMLSerializer();
			//	var string = serializer.serializeToString(xml); 
				//alert(string);
		
		userDetails.setGateWayCount($(xml).find("gateway").size());
		$(xml).find("gateway").each(function() {
			handleGateWay(this);
		});
		var storage = $(xml).find("storage");
		var watrooms=$(xml).find("watroom");
		var topdev=$(xml).find("topdevpowerconsumed");
		userDetails.UserInformationHtml(storage);
		var SlectedLanguage = '<s:property value="locale"/>';
		userDetails.generateTotalTab(storage,SlectedLanguage);
		userDetails.generateHtmlForLocationInENMGMT(storage,watrooms,topdev);
		$('.tabActiveHeader').removeClass('tabActiveHeader');
		$("."+selectedup+"").addClass('tabActiveHeader');
		if(selectdevice!="")
		{
		$('.'+selecttab+'').next('ul').slideToggle(300);
		$('.devicenameclass').removeClass('devicenameclass'); 
		$('.'+selecttab+'').closest('li').addClass('devicenameclass');
		//$('.'+selectdevice+'').click();
		$('.accordionListclass').removeClass('accordionListclass'); 
		$('.'+selectdevice+'').closest('li').addClass('accordionListclass');
		}
		else
		{
		$('.'+selecttab+'').click();
		}
		
	};
	
	
	
	var RefreshhandleSuccess = function(xml){
	
	
		//arrangeGateWaySectionForSetup(xml);
		// 1. Handling the gatewy.
		
		//var serializer = new XMLSerializer();
			//	var string = serializer.serializeToString(xml); 
				//alert(string);
		
	
		userDetails.setGateWayCount($(xml).find("gateway").size());
		$(xml).find("gateway").each(function() {
			handleGateWay(this);
		});
		var storage = $(xml).find("storage");
		userDetails.UserInformationHtml(storage);
		var SlectedLanguage = '<s:property value="locale"/>';
		userDetails.generateTotalTab(storage,SlectedLanguage);
		//if(selectdevice!="")
		//{
		//	$('.'+selectedup+'').click();
			
		//	userDetails.UpdateInstantPower(selectdevice);
			
		
		//}
		
		
		//userDetails.generatelabel(selestedlabel);
	};
	
	var RefreshPaiChartDaily = function(){
		$("#pieselection input[name=radiopie][value=1]").attr('checked',true);
		$.ajax({
			url: "GetDailyPieChart.action",
			cache: false,
			success: function(data){
				
				$('#piechartdaily').html(data);
				
			  }
		});
	//	setTimeout(RefreshPaiChartDaily,10000);
	};
	
	var RefreshBarchart = function(){
		$("#barselection input[name=radiobar][value=1]").attr('checked',true);
		$.ajax({
			url: "GetthreeMonthlybarChart.action",
			cache: false,
			success: function(data){
				
				$('#barchartdetails').html(data);
				
			  }
		});
		
		
	};
	
	var RefreshPaiChartMonth = function(){
		$.ajax({
			url: "GetMonthlyPieChart.action",
			cache: false,
			success: function(data){
				
				
				$('#piechartmonth').html(data);
			  }
		});
		//setTimeout(RefreshPaiChartMonth,10000);
	}; 
	
	RefreshBarchart();
	RefreshPaiChartDaily();
	RefreshPaiChartMonth();
	var RefreshUserDetails = function(){
		$.ajax({
			url: "dashboarddetails.action",
			cache: false,
			dataType: 'xml',
			success: RefreshhandleSuccess
		});
		setTimeout(RefreshUserDetails,10000);
	};
	
	
	setTimeout(function(){
	
	RefreshUserDetails();
	//RefreshPaiChartDaily();
	showUserDetails();
		},10000); 
	
	
	var showUserDetails = function(){
		$.ajax({
			url: "dashboarddetails.action",
			cache: false,
			dataType: 'xml',
			success: handleSuccess
		});
		
	};
	showUserDetails();
	
	$("#report").live('click', function() {
	var targeturl = $(this).attr('href');
	var pHeight = $(this).attr('popupheight');
	var pWidth = $(this).attr('popupwidth');
	var pTitle = $(this).attr('popuptitle');
	
	if(pHeight == undefined){
		pHeight = 110;
		
	}
	if(pWidth == undefined){
		pWidth = 400;
		
	}
	if(pTitle == undefined){
		pTitle = "<s:text name='setup.msg.0001' />";
		
	}  

	try{
		
			$.ajax({
				  url: targeturl,
				  success: function(data){
					
					  $('#editmodal').html(data);
						$('#editmodal').dialog('destroy');
						$('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true	
						});		
						} 
				  });
						
	}catch(ex){
		alert("Some Exception was caught.");
	}	
	return false;	 	
});
 
	$(".downloadpopupform").live('submit', function() {
		var form = $(this).closest("form");
		 //alert($(this).attr('action'));
		 var params = form.serialize();
		 $.post(form.attr('action'), params, function(data){
					$('#editmodal').dialog('close');
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
		 });
			return false;
		});
	
	$('body').on('click', '#accordion', function()
	{
	   // alert("body click");
		$('.tabActiveHeader').removeClass('tabActiveHeader');
		$('#week').addClass('tabActiveHeader');
		selectedup="weekly";
		var targeturl = $(this).attr('href');
		
		var labelforroom = $(this).attr('labelroom');
		var LocationId = $(this).attr('LocationId');
		deviceName=LocationId;
		var params = {"labelroom":labelforroom,"Name":LocationId};
		clickedone="room";
        selecttab=LocationId;
		var Roomlable=labelforroom+" "+formatMessage("Energy.Powerconsumption");
		userDetails.generatelabel(Roomlable);
		//selectedlabel=Roomlable;
		selectedlabel=labelforroom;
		 $('#hourli').click();
		 $('.Legend').show();
		selectdevice="";
		$('.accordionListclass').removeClass('accordionListclass'); 
		$('.devicenameclass').removeClass('devicenameclass'); 
		 $(this).closest('li').addClass('devicenameclass');
		
		$("#SelectdLocation").val(labelforroom);
		/* var name=$('#devicedata li').attr('locationname');
		alert(name); */
		/* var loc=; */
		/* if(labelforroom=="Total Power Consumption"){
		}else{
		} */
		if(false == $(this).next().is(':visible')) 
		{
		
			$(this).next('ul').slideUp(300);
		}
		if(true == $(this).next().is(':visible')) 
		{
		
			$(this).next('ul').slideToggle(300);
		}
		
		if(false == $(this).next("ul").is(':visible')) 
		{
			
			$(this).next('ul').slideToggle(300);
		}
		
		if(true == $(this).prev("ul").is(':visible')) 
		{
			
			$(this).prev('ul').slideToggle(300);
		}
		
		
		
		/* setTimeout(function(){
				$.ajax({
				url: targeturl,
				data:params,
							  success: function(data){
								 				
								$('#GraphDisplay').html(data);
									
				
							  }
						});
},1000);		 */
		
		
		
		
		
			
	});
	
	$("#pieselection").click(function(){
		var pievalue=$("#pieselection input[name=radiopie]:checked").val();
		if(pievalue==1){
			$.ajax({
				url: "GetDailyPieChart.action",
				cache: false,
				success: function(data){
					
					$('#piechartdaily').html(data);
					
				  }
			});
		}else if(pievalue==2){
		$.ajax({
			url: "GetWeekPieChart.action",
			cache: false,
			success: function(data){
				
				$('#piechartdaily').html(data);
				
			  }
		});
	}else if(pievalue==3){
			$.ajax({
				url: "GetMonthlyPieChart.action",
				cache: false,
				success: function(data){
					
					$('#piechartdaily').html(data);
					
				  }
			});
		}
		
	});
	$("#barselection").click(function(){
		var barvalue=$("#barselection input[name=radiobar]:checked").val();
	if(barvalue==4){
		$('#barchartdetails').hide();
		$(".comparative").show();
		$.ajax({
			
			url: "GetComapartiveUsage.action?",
			cache: false,
			dataType: 'xml',
			success:DrawComparativeGraph
			
		});
		
		
		
	}else{$(".comparative").hide();
	$('#barchartdetails').show();
		$.ajax({
			url: "GetEnergyUsageOverTime.action?period="+barvalue,
			cache: false,
			success: function(data){
				
				$('#barchartdetails').html(data);
				
			  }
		});
		
	}
	/* 	if(barvalue==1){
			$.ajax({
				url: "GetthreeMonthlybarChart.action",
				cache: false,
				success: function(data){
					
					$('#barchartdetails').html(data);
					
				  }
			});
		}else if(barvalue==2){
		$.ajax({
			url: "GetMonthlybarChart.action",
			cache: false,
			success: function(data){
				
				$('#barchartdetails').html(data);
				
			  }
		});
	}	 */
	});
	
	
	$('#accordion > ul:eq(0)').show();

	/* $(".fromdatepicker").click(function(){alert("yes"); */
		/* $(".fromdatepicker").datepicker(); */
		/* $(".fromdatepicker").datepicker({
			showOn: 'button',
			buttonImage: '/imonitor/resources/images/calendar.gif',
			buttonImageOnly: true,
			beforeShow: function(input, inst) {
				$(".ui-datepicker").css('z-index',9999);
			},
			onClose: function(dateText, inst) { 
				$(".ui-datepicker").css('z-index', 1);
			},
			dateFormat:'DD/MM/YYYY',
	     onSelect: function() {alert("yes");
	        var date = $('#DateFrom').datepicker('getDate');
	        alert(date);
	        date.setTime(date.getTime() + (1000*60*60*24*45));
	        $('#DateTo').datepicker('option', 'maxDate', date);
	        $('#DateTo').datepicker('option', 'minDate',             
	    $('#DateFrom').datepicker('getDate'));
	        
	    }, 
	    
	}); 
	 }); 
	
$('#DateTo').datepicker({
    	showOn: 'button',
		buttonImage: '/imonitor/resources/images/calendar.gif',
		buttonImageOnly: true
		,
		beforeShow: function(input, inst) {
			$(".ui-datepicker").css('z-index',9999);
		},
		onClose: function(dateText, inst) { 
			$(".ui-datepicker").css('z-index', 1);
			var FromDate=$('.fromdatepicker').val();
			var ToDate=$('.todatepicker').val();
			
			Type="custom";
			if(clickedone=="Total"){
			SendAjaxRequestWithTwoDate(Type,FromDate,ToDate,clickedone);
			}else if(clickedone="device"){
				SendAjaxRequestWithTwoDateforDevice(Type,FromDate,ToDate,clickedone,device);
			}else if(clickedone="room"){
			SendAjaxRequestWithTwoDateforRoom(Type,FromDate,ToDate,clickedone,selectedlabel);
			}
		}, 
		dateFormat:'yy-mm-dd',

    minDate: -90, 
    maxDate: "+1D",    
}); */

SendAjaxRequestWithTwoDate = function(Type,FromDate,ToDate,clickedone)
{
	
	var params = {"fromDate":FromDate,"toDate":ToDate,"clickdone":clickedone};
//	alert(params);
	$.ajax({
		url: "GetPowerinfoOfTwoDate.action",
		data: params,
		cache: false,
		dataType: 'xml',
		success:DrawGraphs 	
	});
};
SendAjaxRequestWithTwoDateforDevice= function(Type,FromDate,ToDate,clickedone,device){
	var params = {"fromDate":FromDate,"toDate":ToDate,"clickdone":clickedone,"Name":device};
	alert(params);
	$.ajax({
		url: "GetPowerinfoOfTwoDateforDevice.action",
		data: params,
		cache: false,
		dataType: 'xml',
		success:DrawGraphs 	
	});
};
SendAjaxRequestWithTwoDateforRoom= function(Type,FromDate,ToDate,clickedone,selectedlabel){
	var params = {"fromDate":FromDate,"toDate":ToDate,"clickdone":clickedone,"labelroom":selectedlabel};
	alert(params);
	$.ajax({
		url: "GetPowerinfoOfTwoDateforRoom.action",
		data: params,
		cache: false,
		dataType: 'xml',
		success:DrawGraphs 	
	});
};
	});
	
	
	
	
	function toggleSelectedTotalTab(anchor)
	{
		
		
		$(".locationselectedtabtotalDashboard").attr("class","locationtabDashboard");
		anchor.attr("class","locationselectedtabtotalDashboard");
	};
	
	
	function toggleSelectedTab(anchor)
	{
		
		$(".locationselectedtabDashboard").attr("class","locationtabDashboard");
		anchor.attr("class","locationselectedtabDashboard");
		
		
		
	};
	

	
	
	
	


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
	<div class="maindivdash">
			<div class="titlebg">
			<div class="welcomeuser">
		<%--	<div id="UserInformation" ></div> --%>
					 <span style="color:#0C5AA3;"><s:text name="home.msg.0009" /></span> <s:property value="#session.user.userId"/><br><span style="margin-left: -39px;color:#0C5AA3;"><s:text name="home.msg.0017" /></span><s:date name="#session.user.lastLoginTime" format="dd/MM/yy 'at' hh:mm a"/> 
				</div>
				<img class="dashboardlogo" src="<%=applicationName %>/resources/images/logo.png" />											
				<div>
					<ul class="ulmenu topmenu" lihoverclass="hoverstyle">
						<li class="menuitem "><a href="home.action" title="Home" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.main" /></div></a></li>
						 <s:if test="#session.user.role.name == 'mainuser' ">
						 	<li class="menuitem"><a href="setup.action" title="To Configure Device" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.setup" /></div></a></li>
						</s:if>
						<li class="menuitem"><a href="getdeviceforalerts.action" title="View Alerts" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.alerts" /></div></a></li>
						<li class="menuitem"><a href="getlistofLocation.action" title="Prespective View" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.advance" /></div></a></li>
						<li class="menuitem selectedtopmenu"><a href="getlistofLocationforEnergyMg.action" title='<s:text name="Tooltip.Energy" />' class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.dashboard" /></div></a></li>
						<li class="menuitem"><a href="../logout.action" title="Sign Out" class="menuitemnormal"><div style="width=100%;height=100%;"><s:text name="general.signout" /></div></a></li>					
					</ul>
				</div>
				<div>
					<img class="helpicon" title="Drag and Drop devices to Room" src="<%=applicationName %>/resources/images/help.png" onclick='javascript: openHelpPage()' />
				</div>
			</div>
			<!-- <div id="tabContainerchange">
			
			 
			
              </div> -->
             <%--  
              
              <div id="lastMonthUsageInfo">
              
              </div>
               <button id="report" type="button" class='bt bbtn download'  href="torequestforExel.action" style=" font-family: serif; margin-left: 50px;popupheight='104';popupwidth='408';text-decoration: none;" title="Click to Download Energy Report">Download Report</button>
              <!-- <div id="lastUpdateInfo"><table><tr><td><h2 style="float:left;font-family: serif;">Last Updated on: </h2></td></tr><tr><td><h2 style="font-weight: bold;float:right;font-family: serif;" id="lastUpdate"></h2></td></tr></table></div>  -->
              <div id="lastUpdateInfo"><h3 style="float:left;font-family: serif;">Last Updated on: </h3></span></div>
              <div style="margin: 0px 54px 0px 0px;"><h3 style="font-weight: bold;float:right;font-family: serif;" id="lastUpdate"></h3></div>
              </div> --%>
              
              
              
              
              
            <%--  <div id="UseageValue" >
            
           <!--  <table id="defineActionTable" ></table> -->
		 	<!-- <div id="ConfigTable" style="margin:34px 0px 0px -25px;"></div> -->
		 	<table>
		 	 <tr></tr><tr></tr><tr><td style='font-size:small;font-weight: bold;font-family:serif;'><a href='totarrifConfig.action' class='editlinkForTarrif tariff' popupheight='550' popupwidth='950'style=' text-decoration: none;'><img title='Tariff Configuration' style='font-family: serif;float:left;margin-top:-11px;width:40px;height:40px;' src='../resources/images/configsite.png'/><span>Tariff Configuration</span></a></td></tr>
				             <tr><td style='font-size:small;font-weight: bold;font-family:serif;'><a href='totargetCost.action' class='editlinkForTarrif target' popupheight='503' popupwidth='696'style=' text-decoration: none;'><img title='Target Cost' style='font-family: serif; float:left;margin-top:-11px;width:40px;height:40px;' src='../resources/images/configsite.png'/>Target Cost</a></td></tr>
		 	</table>
		 	</div> --%>
             
		<!--  	</div>end of tabContainerchange -->
		 	<div class="energymonitoring">
		 	 <hr id="horizontal"></hr>
		 	 <hr id="vertical"></hr>
		 	 <div id="leftmenu" class="Energymenu">
		 	 <div id="energyInfo">
		 	<div id="lastUpdateInfo"><h2 style="float:left;font-family: serif;;">Last Updated on: </h2><h2 style="font-weight: bold;float:right;padding: 0px 0px 0px 6px;font-family: serif;;" id="lastUpdate"></h2></div> 
		 	<div id="designline"></div>
		 	<div id="uptodatecost" style="position: absolute;margin: -33px 0px 0px 283px;font-size: 13px;font-family: sans-serif;font-weight: bold;"></div>
		 	<div id="designlinefordownload"></div>
		 	<a id="report" href="torequestforExel.action" style=" font-family: sans-serif;font-size: 14px;color: black;font-weight: bold;;margin: -34px 0px 0px 460px;position: absolute;popupheight='104';popupwidth='408';text-decoration: underline;" title="Click to Download Energy Report">Download Report</a>
		 	<!-- <a href='totarrifConfig.action' class='editlinkForTarrif tariff' popupheight='550' popupwidth='950'style=' text-decoration: none;margin:-36px 0px 0px 561px;position: absolute;'><img title='Tariff Configuration' href='totarrifConfig.action' class='editlinkForTarrif tariff' popupheight='550' popupwidth='950'style='font-family: serif;position: absolute;width:25px;height:25px;' src='../resources/images/configsite.png'/></a>
		 	<a href='totargetCost.action' class='editlinkForTarrif target' popupheight='503' popupwidth='696'style=' text-decoration: none;margin:-36px 0px 0px 605px;position: absolute;'><img title='Target Cost' style='font-family: serif;position: absolute;width:25px;height:25px;' src='../resources/images/configsite.png'/></a>  -->
		 	</div>
		 	 <!-- <table id="energydevices" style="font-family:serif; margin: 7px 0px 0px 6px;">
		 	 </table> -->
		 	 <ul id="accordionList" style="font-family:serif;position: absolute; margin: 31px 0px 0px 6px;width: 205px;height:278px;overflow-x: hidden;overflow-y: auto">
			  </ul>
			  <ul id="devicelist" style="font-family:serif;position: absolute;margin: 31px 0px 0px 220px;overflow-x: hidden;overflow-y: auto;width:210px;height:278px;">
			  </ul>
			  <div id="comparativedata">
			  <h2 style="font-size: 16px;position: absolute;margin: 9px 0px 0px 13px;width: 208px;">Top  Energy Consuming Appliances</h2>
			  <div id="toppowercomp" style="margin: 54px 0px 0px 5px;position: absolute;overflow-y: scroll;height: 69px;width: 218px;"></div>
			  <div id="seperateline"></div>
			 <div id="lastMonthUsageInfo"></div>
			 <div id="bottomline"></div>
			 <div id="tariffSection" style="margin: 206px 0px 0px 8px;">
			 <a href='totarrifConfig.action' class='editlinkForTarrif tariff' popupheight='550' popupwidth='950'style=' text-decoration: none;position: absolute;'><img title='Tariff Configuration' href='totarrifConfig.action' class='editlinkForTarrif tariff' popupheight='550' popupwidth='950'style='font-family: serif;position: absolute;width:25px;height:25px;' src='../resources/images/tariff_settings.png'/><span style='margin: 4px 0px 0px 35px;float: right;font-size: larger;color: black;font-weight: bold;'>Tariff configuration</span> </a>
		 	 </div>
		 	 <a href='totargetCost.action' class='editlinkForTarrif target' popupheight='503' popupwidth='696'style=' text-decoration: none;margin:31px 0px 0px 8px;position: absolute;'><img title='Target Cost' style='font-family: serif;position: absolute;width:25px;height:25px;' src='../resources/images/tariff_settings.png'/><span style='margin: 5px 0px 0px 35px;float: right;font-size: larger;color: black;font-weight: bold;'>Target cost</span></a>
			 
			
			  </div>
			  </div>
		 	<%-- <div class="chartselection">
		 	<ul class="energytab">
		 	<li class="menuchart"><div id="lastUpdateInfo"><h3 style="float:left;font-family: serif;">Last Updated on: </h3></span></div></li>
            <li class="menuchart"><div style="margin: 0px -9px 0px 0px;"><h3 style="font-weight: bold;float:right;font-family: serif;" id="lastUpdate"></h3></div></li>
		 	<li class="targetchart"><div id="targetcost" style="margin:0px -50px 0px 15px;"/></li>
		 	<li class="costchart"><div id="uptodatecost" style="margin:0px -50px 0px 15px;"/></li>
		 	<li class="menuchart"> <button id="report" type="button" class='bt bbtn download'  href="torequestforExel.action" style=" font-family: serif; margin-left: 50px;popupheight='104';popupwidth='408';text-decoration: none;" title="Click to Download Energy Report">Download Report</button></li> --%>
		 	<%-- <li class="menuchart"><s:radio theme="simple" list="#{'1':'BarChart'}" id="charts"></s:radio></li>
		 	<li class="menuchart"><s:radio theme="simple" list="#{'2':'LineChart'}" id="charts"></s:radio></li>
		 	<li class="menuchart"><s:radio theme="simple" list="#{'3':'PieChart'}" id="charts"></s:radio></li> --%>
		 	<!-- </ul>
		 	</div>
		 	</div> -->
		 	<div class="piechart" style="margin:0px 0px 0px 0px;">
		 	<div class="radiopie" id="pieselection">
		 	<input type="radio"  name="radiopie" value="1" style="width: 27px;"/><span style="font-size: 17px;font-family: serif;display: inline-block;">Last 24 Hrs</span>
		 	<input type="radio" name="radiopie" value="2" style="width: 27px;"/><span style="font-size: 17px;font-family: serif;display: inline-block;">Last 7 Days</span>
		 	<input type="radio"  name="radiopie" value="3" style="width: 27px;"/><span style="font-size: 17px;font-family: serif;display: inline-block;">Last Month</span>
		 	</div>
		 	 <div id="piechartdaily"></div>
		 	<div id="subpiechart"></div> 
		 	</div>
		 	
		 	<div id="barchart" style="position: absolute;margin: 394px 0px 0px 87px;">
		 	<h2 style="float: left;margin-top: -64px;margin-left: 57px;display: block;font-family: serif;font-size:x-large">Total Energy Usage Over Time</h2>
		 	<div class="radiobar" id="barselection" >
		 	<input type="radio"  name="radiobar" value="1" /><span style="font-size: 17px;font-family: serif;display: inline-block;">Last 3 Months</span>
		 	<input type="radio" name="radiobar" value="2" /><span style="font-size: 17px;font-family: serif;display: inline-block;">Last 6 Months</span>
		 	<input type="radio" name="radiobar" value="3" /><span style="font-size: 17px;font-family: serif;display: inline-block;">Yearly</span>
		 	<input type="radio" name="radiobar" value="4" /><span style="font-size: 17px;font-family: serif;display: inline-block;">Comparative</span>
		 	</div>
		 	<div id="barchartdetails"></div>        
		 	</div>
		 	<div class="comparative">
	<div class='legend'>
	<div id='overviewlegend' class='overviewlegend' ></div>
	</div>
        <div id="ComprativegraphHolder" class="tab active ComprativegraphHolder">
            </div>
       </div>
		 <%-- <div id="energyInfo">
		 	<div id="totalInfo"><table><td><img style="width:29px;"src="<%=applicationName %>/resources/images/bulp.png"></img></td><td><h2 style="font-family: serif;color:#ec0404;">Total energy:&nbsp;</h2></td><td><h2 id="totalPower" style="font-weight: bold;font-family: serif;color:#ec0404;"></h2></td></table></div>
		 	<div id="usageInfo"><table><td><h2 style="font-family: serif;color:#ec0404;">Month Usage:&nbsp;</h2></td><td><h2 style="font-weight: bold;font-family: serif;color:#ec0404;" id="UsageColumn"></h2></td></table></div> --%>
		 	<!-- <div id="lastUpdateInfo"><h2 style="float:left;font-family: serif;color:darkblue;">Last Updated on: </h2><h2 style="font-weight: bold;float:right;font-family: serif;color:darkblue;" id="lastUpdate"></h2></div> -->
		 	<!-- </div>  -->
 		 	  <div class="graphs">
 		 	  <h2 style="float: right;margin-top: -50px;margin-left: 116px;position: absolute;display: block;font-family: serif;font-size:x-large">Power Appliances Usage </h2>
            <div class="tabs">
    <ul id="radiotabs">
        <li style="font-family: serif;" data-type="hour" id="hourli"><input type="radio" name="radioline" value='hour' style="width: 27px;"/><span style="font-size: 17px;font-family: serif;display: inline-block;">Hour</span></li>
        <li style="font-family: serif;" data-type="day" ><input type="radio" name="radioline"style="width: 27px;"/><span style="font-size: 17px;font-family: serif;display: inline-block;">Week</span></li>
        <li style="font-family: serif;" data-type="month" ><input type="radio" name="radioline"style="width: 27px;"/><span style="font-size: 17px;font-family: serif;display: inline-block;">Month</span></li>
        <li style="font-family: serif;" data-type="year" ><input type="radio" name="radioline"style="width: 27px;"/><span style="font-size: 17px;font-family: serif;display: inline-block;">Year</span></li>
		
    </ul>
   
			 <div class="tab-content">
			 <div id='graphTitle' class='graphTitle' ></div>
	<div class='Legend'>
	<div id='overviewLegend' class='overviewLegend' ></div>
	<div id='graphTitle' class='graphTitle' ></div>
	</div>
        <div id="graphHolder" class="tab active graphHolder">
            </div>
       </div>
       </div>
       </div>
   		<%-- <ul>
   		<li style="margin: 36px 0px 0px -70px;float: left;"><s:radio list="#{'1':'Last Month'}"></s:radio></li>
   		<li style="margin: 36px 0px 0px 73px;float: left;"><s:radio list="#{'2':'Last 6 Months'}"></s:radio></li>
   		</ul> --%>
       <div id="chartContainer" style="height:300px;width:100%;"></div>
	<div id="message"></div>
	<div id="confirm"></div>
	<div id="editmodal"></div>
	<div id="LastMonthUsage" style="display:none"></div>
	<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
</body>
</html>