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
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.symbol.js"></script>	    
<!-- bar chart -->

<script  type="text/javascript" src="<%=applicationName %>/resources/css/barchart/jquery.jqplot.min.js"></script>
    <script type="text/javascript" src="<%=applicationName %>/resources/css/barchart/shCore.min.js"></script>
    <script type="text/javascript" src="<%=applicationName %>/resources/css/barchart/shBrushJScript.min.js"></script>
    <script type="text/javascript" src="<%=applicationName %>/resources/css/barchart/shBrushXml.min.js"></script>


  <script  type="text/javascript" src="<%=applicationName %>/resources/js/bar chart/jquery.jqplot.min.js"></script>
  <script  type="text/javascript" src="<%=applicationName %>/resources/js/bar chart/jqplot.barRenderer.min.js"></script>
  <script  type="text/javascript" src="<%=applicationName %>/resources/js/bar chart/jqplot.pieRenderer.min.js"></script>
  <script  type="text/javascript" src="<%=applicationName %>/resources/js/bar chart/jqplot.categoryAxisRenderer.min.js"></script>
  <script  type="text/javascript" src="<%=applicationName %>/resources/js/bar chart/jqplot.pointLabels.min.js"></script>

	<script  type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.canvasTextRenderer.min.js"></script>

     <script  type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.canvasAxisLabelRenderer.min.js"></script>
	
	
	
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
<script class="code" type="text/javascript">
$(document).ready(function () {

var data=[];
var ticks=[];
var SelectedMonth;
	 
for(var i=0;i<12;i++)
{

	  
			<s:iterator value="currentDate" var="object">
			var test="<s:property value='#object[4]'/>";
			test=test-1;
			X_axisLable="<s:date name="#object[4]"/>";
			//ticks.push(["<s:date name="#object[4]" format="MMM-dd"/>"]); 
		
			if(i==test)
			{
			
				data.push([test,Math.round("<s:property value='#object[0]'/>")/1000]);
				SelectedMonth=test;
			}
			</s:iterator>
	
	    if(SelectedMonth != i)
		data.push([i,"0"]);	
		
}

/*var data = [    
    [0, 1100], //London, UK
    [01, 1500], //New York, USA
    [02, 2500], //New Delhi, India
    [03, 2400], //Taipei, Taiwan
    [04, 1300], //Beijing, China
    [05, 1800],  //Sydney, AU
	[06, 2000],
	[07, 3000],
	[08, 4000],
	[09, 0],
	[10, 0],
	[11, 0],
];*/

var dataset = [
    { label: "X_axisLable Power Consumption", data: data, color: "#5482FF" }
];
 
 var SlectedLanguage='<s:property value="locale"/>';
if(SlectedLanguage == 'en_US')
		{
		
		var dataset = [
    { label: "2013 Power Consumption", data: data, color: "#5482FF" }
];
		var ticks = [
    [00, "Jan"], [01, "Feb"], [02, "March"], [03, "April"],
    [04, "May"], [05, "June"],[06,"July"],[07,"Aug"],[08,"Sep"],[09,"Oct"],[10,"Nov"],[11,"Dec"],
];
		}else if(SlectedLanguage == 'zh_CN'){
		var dataset = [
    { label: "2013功耗", data: data, color: "#5482FF" }
];
		
		 var ticks = [
    [0, "一月"], [1, "二月"], [2, "三月"], [3, "四月"],
    [4, "五月"], [5, "六月"],[6,"七月"],[7,"八月"],[8,"九月"],[9,"十月"],[10,"十一月"],[11,"十二月"],
];
		}else if(SlectedLanguage == 'zh_TW'){
		
		var dataset = [
    { label: "2013功耗", data: data, color: "#5482FF" }
];
		var ticks = [
    [0, "一月"], [1, "二月"], [2, "三月"], [3, "四月"],
    [4, "五月"], [5, "六月"],[6,"七月"],[7,"八月"],[8,"九月"],[9,"十月"],[10,"十一月"],[11,"十二月"],
];
		}

var options = {
    series: {
        bars: {
            show: true
        },
		series:[
                {
                    pointLabels: {
                        show: true
                    },
                    renderer: $.jqplot.BarRenderer,
                    showHighlight: false,
                    rendererOptions: {
                        // Speed up the animation a little bit.
                        // This is a number of milliseconds.  
                        // Default for bar series is 3000.  
                        animation: {
                            speed: 2500
                        },
                        barWidth: 15,
                        barPadding: -15,
                        barMargin: 0,
                        highlightMouseOver: false
                    }
                }, 
                {
                    rendererOptions: {
                        // speed up the animation a little bit.
                        // This is a number of milliseconds.
                        // Default for a line series is 2500.
                        animation: {
                            speed: 2000
                        }
                    }
                }
            ],
    },
    bars: {
        barWidth: 0.5
    },
    xaxis: {
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: 'Verdana, Arial',
        axisLabelPadding: 10,
        ticks: ticks
        
    },
    yaxis: {
    	axisLabel: "KW",
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: 'Verdana, Arial',
        axisLabelPadding: 3,
        tickFormatter: function (v, axis) {
            return v + "";
        }
    },
	
    legend: {
        noColumns: 0,
        labelBoxBorderColor: "red",
        position: "nw"
    },
    grid: {
        hoverable: true,
        borderWidth: 2,        
        backgroundColor: { colors: ["#ffffff", "#EDF5FF"] }
    }
};


	 
	 
	 
	 
	 
$.jqplot.config.enablePlugins = true;
    $.plot($("#flot-placeholder"),dataset, options);    
    $("#flot-placeholder").UseTooltip();

});




function gd(year, month, day) {
    return new Date(year, month, day).getTime();
}

var previousPoint = null, previousLabel = null;

$.fn.UseTooltip = function () {
    $(this).bind("plothover", function (event, pos, item) {
        if (item) {
            if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
                previousPoint = item.dataIndex;
                previousLabel = item.series.label;
                $("#tooltip").remove();

                var x = item.datapoint[0];
                var y = item.datapoint[1];

                var color = item.series.color;

                //console.log(item.series.xaxis.ticks[x].label);                
                
                showTooltip(item.pageX,
                        item.pageY,
                        color,
                        "<strong>" + item.series.label + "</strong><br>" + item.series.xaxis.ticks[x].label + " : <strong>" + y + "</strong> KWh");                
            }
        } else {
            $("#tooltip").remove();
            previousPoint = null;
        }
    });
};

function showTooltip(x, y, color, contents) {
    $('<div id="tooltip">' + contents + '</div>').css({
        position: 'absolute',
        display: 'none',
        top: y - 40,
        left: x - 120,
        border: '2px solid ' + color,
        padding: '3px',
        'font-size': '9px',
        'border-radius': '5px',
        'background-color': '#fff',
        'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
        opacity: 0.9
    }).appendTo("body").fadeIn(200);
}



    </script>
</head>
 <div id="flot-placeholder" style="background: rgb(242, 245, 236);height:306px;margin: 140px 0px 4px 110px;"></div>
 </body>
</html>

  