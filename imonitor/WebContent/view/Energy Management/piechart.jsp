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
		
	
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.user.js"></script>
	
	    
	    
<!-- <script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery-1.8.3.min.js"></script> -->
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.min.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.time.js"></script>    
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jshashtable-2.1.js"></script>    
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.numberformatter-1.2.3.min.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.pie.min.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.axislabels.js"></script>
	    
	
	
	
	
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
<script>
//******* PIE CHART

var dataSet =[];
var CSS_COLOR_NAMES = ["Salmon","SeaGreen","Orchid","PeachPuff","Peru","Wheat","WhiteSmoke","Yellow","YellowGreen"];
var userDetails = null;
		var imvgDetails = new ImvgDetals();
		
		var DEVICE_TYPE_WISE = "DEVICE_TYPE_WISE";
		var LOCATION_WISE = "LOCATION_WISE";
		userDetails = new UserDetails();


var options = {
    series: {
        pie: {
            show: true,
            label: {
                show: true,
				tilt: 0.5,
                radius: 180,
                formatter: function (label, series) {
                    return '<div style="border:1px solid grey;font-size:8pt;text-align:center;padding:5px;color:white;">' +
                    label + ' : ' +
                   parseFloat(series.percent).toFixed(2) +
                    '%</div>';
                },
                background: {
                    opacity: 0.8,
                    color: '#000'
                }
            }
        }
    },
    legend: {
        show: true
    },
    grid: {
        hoverable: true
    }
};


$(document).ready(function () {
	var DevicesForlocationTypes=[];
			<s:iterator value="currentDate" var="object">
			var test="<s:property value='#object[0]'/>";
		DevicesForlocationTypes.push("<s:property value='#object[1]'/>", "<s:property value='#object[0]'/>"); 
        	</s:iterator>
	
	for(var i=0;i<DevicesForlocationTypes.length;i++)
		{		
		var DevicesForlocationType = DevicesForlocationTypes[i];
		var value=DevicesForlocationTypes[i+1];
		value=value/1000;
		i=i+1
		var coord = {label: DevicesForlocationType, data:value, color: CSS_COLOR_NAMES[i]};
		dataSet.push(coord);	
		}
	
		$.plot($("#flot-placeholder"), dataSet, options);
		$("#flot-placeholder").showMemo();
	
	});
	
    
    
    
    
    
   


$.fn.showMemo = function () {
    $(this).bind("plothover", function (event, pos, item) {
        if (!item) { return; }
        console.log(item.series.data)
        var html = [];
        var percent = parseFloat(item.series.percent).toFixed(2);        

        html.push("<div style=\"border:1px solid grey;font-size: 22px;background-color:",
             item.series.color,
             "\">",
             "<span style=\"color:black\">",
             item.series.label,
             " : ",
             $.formatNumber(item.series.data[0][1], { format: "#,###,##0.00", locale: "us" }),
              "KWh (", percent, "%)",
             "</span>", 
             "</div>");
        $("#flot-memo").html(html.join(''));
    });
};

</script>
</head>
<div id="flot-placeholder" style="width:626px;margin: -11px -10px 10px 8px;float: right;height:481px;margin:52 auto"></div>
<div id="flot-memo" style="text-align:center;height:30px;width:250px;height:20px;text-align:center;float: right;margin: -54px 0px 0px 5px;"></div>
</body>
</html>

  