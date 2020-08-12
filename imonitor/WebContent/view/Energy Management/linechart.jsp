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
		<!-- <link type="text/css" href="<%=applicationName %>/resources/css/demos.css" rel="stylesheet" />-->
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
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.pie.min.js"></script><script type="text/javascript" src="/js/flot/jquery.flot.symbol.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.axislabels.js"></script>
	    
	<link class="include" rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/jquery.jqplot.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/examples.min.css" />
    <link type="text/css" rel="stylesheet" href="<%=applicationName %>/resources/css/shCoreDefault.min.css" />
    <link type="text/css" rel="stylesheet" href="<%=applicationName %>/resources/css/shThemejqPlot.min.css" />
    <link  rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/jquery-ui.css" />
	
	 <script class="include" type="text/javascript" src="<%=applicationName %>/resources/css/jquery.min.js"></script>
    	<script class="include" type="text/javascript" src="<%=applicationName %>/resources/js/jquery.jqplot.min.js"></script>
    	<script type="text/javascript" src="<%=applicationName %>/resources/js/shCore.min.js"></script>
    	<script type="text/javascript" src="<%=applicationName %>/resources/js/shBrushJScript.min.js"></script>
    	<script type="text/javascript" src="<%=applicationName %>/resources/js/shBrushXml.min.js"></script>
	
	<script  type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.dateAxisRenderer.min.js"></script>
    <script  type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.logAxisRenderer.min.js"></script>
    <script  type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.canvasTextRenderer.min.js"></script>
    <script  type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.canvasAxisTickRenderer.min.js"></script>
     <script  type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.canvasAxisLabelRenderer.min.js"></script>
    <script  type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.highlighter.min.js"></script>
	<script  type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.canvasOverlay.min.js"></script>
	<script  type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.barRenderer.min.js"></script>
	
	
	
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
      .jqplot-target {
            margin: 20px;
            height: 450px;
            width: 700px;
            color: #dddddd;
        }

        table.jqplot-table-legend {
            border: 0px;
            background-color: rgba(100,100,100, 0.0);
        }

        .jqplot-highlighter-tooltip {
            background-color: rgba(57,57,57, 0.9);
            padding: 7px;
            color: #dddddd;
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


.jqplot-yaxis {
  margin: 0px 0px 0px -15px!important;
width: 80px !important;
}

.jqplot-yaxis-label
{
position: absolute;
	top: 38.5px;
	float:left;
	left: -20px ;!important;
	background-color: white;
	margin: 0px 0px 0px -20px;
}
.jqplot-xaxis-label
{
position: absolute;
left: 249.5px;
bottom: 0px;
background-color: white;

}

</style>
<script class="code" type="text/javascript">
 
	var plot1;
	
	function renderGraph(CurrentArr) {
		
		if (plot1) {
			plot1.destroy();
		}
		 PlotGraph(CurrentArr);
			
	}
	
	
	
        $(document).ready(function () 
   {
       
       // alert("DAys:"+'<s:property value="DiffDays"/>');
             var CurrentArr=[];
			<s:iterator value="currentDate" var="object">
			var test="<s:property value='#object[0]'/>";
		CurrentArr.push(["<s:date name="#object[4]" format="yyyy-MM-dd hh:mm a"/>", parseInt(test.substring(0,(test.length-1)))]); 
        	</s:iterator>
         renderGraph(CurrentArr); 
  });
	

        


  function applyChartText(plot, text, lineValue) {
     var maxVal = plot.axes.yaxis.max;
     var minVal = plot.axes.yaxis.min;
	 
     var range = maxVal + Math.abs(minVal); // account for negative values  
     var titleHeight = plot.title.getHeight();

     if (plot.title.text.indexOf("<br") > -1) { // account for line breaks in the title
          titleHeight = titleHeight * 0.5; // half it
     } 

     // you now need to calculate how many pixels make up each point in your y-axis
     var pixelsPerPoint = (plot._height - titleHeight  - plot.axes.xaxis.getHeight()) / range;

     var valueHeight = ((maxVal - lineValue) * pixelsPerPoint) + 10;

     // insert the label div as a child of the jqPlot parent
     var title_selector = $(plot.target.selector).children('.jqplot-overlayCanvas-canvas');
     $('<div class="jqplot-point-label " style="margin: 0px 0px 0px 54px; position:absolute;  text-align:left;width:95%;top:' + valueHeight + 'px;">' + text + '</div>').insertAfter(title_selector);
}

function PlotGraph(CurrentArr)
{
$.jqplot.config.enablePlugins = true;
$.jqplot._noToImageButton = true;
		
           /* var currYear = [["2011-08-01",796.01], ["2011-08-02",510.5], ["2011-08-03",527.8], ["2011-08-04",308.48], 
            ["2011-08-05",420.36], ["2011-08-06",219.47], ["2011-08-07",333.82], ["2011-08-08",660.55], ["2011-08-09",1093.19], 
            ["2011-08-10",521], ["2011-08-11",660.68], ["2011-08-12",928.65], ["2011-08-13",864.26], ["2011-08-14",395.55], 
            ["2011-08-15",623.86], ["2011-08-16",1300.05], ["2011-08-17",972.25], ["2011-08-18",661.98], ["2011-08-19",1008.67], 
            ["2011-08-20",1546.23], ["2011-08-21",593], ["2011-08-22",560.25], ["2011-08-23",857.8], ["2011-08-24",939.5], 
            ["2011-08-25",1256.14], ["2011-08-26",1033.01], ["2011-08-27",811.63], ["2011-08-28",735.01], ["2011-08-29",985.35], 
            ["2011-08-30",1401.58], ["2011-08-31",1177], ["2011-09-01",1023.66], ["2011-09-02",1442.31], ["2011-09-03",1299.24], 
            ["2011-09-04",1306.29], ["2011-09-06",1800.62], ["2011-09-07",1607.18], ["2011-09-08",1702.38], 
            ["2011-09-09",4118.48], ["2011-09-10",1988.11], ["2011-09-11",1485.89], ["2011-09-12",2681.97], 
            ["2011-09-13",1679.56], ["2011-09-14",3538.43], ["2011-09-15",3118.01], ["2011-09-16",4198.97], 
            ["2011-09-17",3020.44], ["2011-09-18",3383.45], ["2011-09-19",2148.91], ["2011-09-20",3058.82], 
            ["2011-09-21",3752.88], ["2011-09-22",3972.03], ["2011-09-23",2923.82], ["2011-09-24",2920.59], 
            ["2011-09-25",2785.93], ["2011-09-26",4329.7], ["2011-09-27",3493.72], ["2011-09-28",4440.55], 
            ["2011-09-29",5235.81], ["2011-09-30",6473.25]];

			
			
             plot1 = $.jqplot("chart1", [CurrentArr],   {
                seriesColors: ["#0A0707"],
                highlighter: {
                    show: true,
                    sizeAdjust: 1,
                    tooltipOffset: 9
                },
                grid: {
                    background: 'gray',
                    drawBorder: true,
                    gridLineWidth: 1.5,
					drawGridlines: false,
					gridLineColor: 'red',
					
                },
                legend: {
                    show: false,
                    placement: 'insideGrid'
                },
                seriesDefaults: {
                    rendererOptions: {
                        smooth: true,
                        animation: {
                            show: true
                        }
                    },
                    showMarker: false,
					pointLabels: {
          show: true,
          edgeTolerance: 5
        },
					markerRenderer: $.jqplot.MarkerRenderer,
					
        markerOptions: {
            show: true,             
            style: 'filledCircle',  
                                   
            
        }
                                                 
        
                },
				
                series: [
                    {
                        fill: false,
                        
                    },
					{ 
					showMarker:true,
					pointLabels: { show:true, location: 'ne' } 
					}
                ],
                axesDefaults: {
                    rendererOptions: {
                        baselineWidth: 15,
                        baselineColor: '#444444',
                        drawBaseline: false,
						labelRenderer: $.jqplot.CanvasAxisLabelRenderer
                    }
                },
                axes: {
                    xaxis: {
                        renderer: $.jqplot.DateAxisRenderer,
                        tickRenderer: $.jqplot.CanvasAxisTickRenderer,
						tickOptions: {
                            angle: -30,
                            textColor: '#dddddd',
							showMark: true
                        },
                        min: "2013-10-22",
                        max: "2013-10-26",
                        tickInterval: "1 days",
                        drawMajorGridlines: true,
						
							
                    },
                    yaxis: {
                       
                        pad: 0,
						drawMajorGridlines: true,
                        rendererOptions: {
                            minorTicks: 1
                        },
                        tickOptions: {
                            formatString: "%'d",
                            showMark: true
                        },
						label:'KWH'
                    }
                },
				canvasOverlay: {
            show: true,
            objects: [
                 
                {horizontalLine: {
                    name: 'pebbles',
                    y: 500,
                    lineWidth: 3,
                    xOffset: 0,
                   color: 'rgba(255, 0, 0,0.45)',
                    shadow: false
                }},
                {horizontalLine: {
                    name: 'pebbles',
                    y: 1500,
                    lineWidth: 3,
                    xOffset: 0,
                    color: 'rgba(145, 213, 67,0.45)',
                    shadow: false
                }},
                  {horizontalLine: {
                    name: 'pebbles',
                    y:3500 ,
                    lineWidth: 3,
                    xOffset: 0,
                    color: 'rgba(145, 213, 67,0.45)',
                    shadow: false
                }},

				 {horizontalLine: {
                    name: 'pebbles',
                    y:4500 ,
                    lineWidth: 3,
                    xOffset: 0,
                    color: 'rgba(255, 0, 0,0.45)',
                    shadow: false
                }},


            ]

        }
            });
				
			
				
              $('.jqplot-highlighter-tooltip').addClass('ui-corner-all')
			  
			   applyChartText(plot1,"HMD success",1500);
			   applyChartText(plot1,"HMD warning",2000);
			   applyChartText(plot1,"HMD failure",4000);
			  
			  
}
    </script>

</head>
<div class="ui-widget ui-corner-all">
            <div id="chart1"  style="background-color: gray; height: 306px; width: 541px; margin: 256px 0px 0px 140px; position: relative; background-position: initial initial; background-repeat: initial initial;"></div>  
    </div>
</body>
</html>

  