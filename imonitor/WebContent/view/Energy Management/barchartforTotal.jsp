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
		

		
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.jqplot.min.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.logAxisRenderer.min.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.canvasTextRenderer.min.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.canvasAxisLabelRenderer.min.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.canvasAxisTickRenderer.min.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.dateAxisRenderer.min.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/bar chart/jqplot.categoryAxisRenderer.min.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/jqplot.barRenderer.min.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/barchart/jquery.jqplot.min.css" />
        <script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.min.js"></script>
        <script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.user.js"></script>
	    <script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jshashtable-2.1.js"></script> 
	    <link type="text/css" href="<%=applicationName %>/resources/css/themes/base/jquery.ui.all.css" rel="stylesheet" />
		<link type="text/css" href="<%=applicationName %>/resources/css/demo_table.css" rel="stylesheet"/>

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
.jqplot-yaxis-label
{
color: black;
margin: 0px 3px 3px -18px;
}
.jqplot-xaxis-label
{
color: black;
margin: 13px 8px -22px 5px;
}


</style>

<script class="code" type="text/javascript">


var userDetails = null;
var imvgDetails = new ImvgDetals();

var DEVICE_TYPE_WISE = "DEVICE_TYPE_WISE";
var LOCATION_WISE = "LOCATION_WISE";
userDetails = new UserDetails();


      
   var k = $("#findDays").val();      
	
var SlectedLanguage='<s:property value="locale"/>';

var s1=[];

	  var ticks=[];
	  var SelectedMonth;
	  
	  if (k == "Weekly Power Consumption"){
			for(var i=1;i<8;i++)
        {
		var day;
		   if(i==1)
		   {
		   day="Sun";
		   }
           else if(i==2)
		   {
		   day="Mon";
		   }
		  else if(i==3)
		   {
		   day="Tue";
		   }
		   else if(i==4)
		   {
		   day="Wed";
		   }
		   else if(i==5)
		   {
		   day="Thu";
		   }
		  else if(i==6)
		   {
		   day="Fri";
		   }
		  else if(i==7)
		   {
		   day="Sat";
		   }
	
		
			<s:iterator value="currentDate" var="object">
			var test="<s:date name="#object[2]" format="E"/>";
			X_axisLable="<s:date name="#object[2]" format="yyyy"/>";


			ticks.push(["<s:date name="#object[2]" format="MMM-dd"/>"]); 
		
			if(day == test)
			{
				s1.push(Math.round("<s:property value='#object[0]'/>")/1000);
				SelectedMonth=test;
			}
			</s:iterator>
	
	    if(SelectedMonth != day)
		s1.push("0");	
	    
		
}
           
        $.jqplot.config.enablePlugins = true;
     
        if(SlectedLanguage == 'en_US')
		{
         
		 ticks = ['Sun','Mon', 'Tue', 'Wed', 'Thur','Fri','Sat'];
		
		}else if(SlectedLanguage == 'zh_CN'){
		var ticks = ['星期一', '星期二', '星期三', '星期四','星期五','星期六','星期日'];
		}else if(SlectedLanguage == 'zh_TW'){
		var ticks = ['星期一', '星期二', '星期三', '星期四','星期五','星期六','星期日'];
		}
		
		
		
		
		
    
 
    var plot4 = $.jqplot('chart', [s1], {
        series:[
			{
				renderer:$.jqplot.BarRenderer,
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
                    	   },
			
			   pointLabels: {
                        show: true
                    		},
			}
		],
	 animate: !$.jqplot.use_excanvas,
        axes: {
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                label:X_axisLable ,
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                tickOptions: {
                    		fontFamily: 'Georgia',
          			fontSize: '10pt',
          			angle: -45,
				textColor: '#000000',                
		},
		 
	labelOptions: {
            fontFamily: 'Georgia, Serif',
            fontSize: '14pt',
	     textColor: '#000000',  
          },
	          ticks: ticks,
                tickInterval: 1,
                drawMajorGridlines: false,
                drawMinorGridlines: false,
                drawMajorTickMarks: false,
            },
            yaxis: {
                autoscale:true,
                label: 'KWh',
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                tickOptions: {
                    angle: -30,
		      textColor: '#000000',
                },
			
		labelOptions: {
            fontFamily: 'Georgia, Serif',
            fontSize: '14pt',
	     textColor: '#000000',  
          },


		  
            },
            
        }
    });		
		
	  }else if (k == "Monthly Power Consumption") {
		  
		  <s:iterator value="currentDate" var="object">
			var test="<s:date name="#object[2]" format="MMM-dd"/>";
			
			var month="<s:date name="#object[2]" format="MM"/>";
			if(month == 1)
			{
			ticks.push(["<s:text name="Energy.jann" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			X_axisLable="<s:text name="Energy.Jan" />";
			}
			else if(month == 2)
			{
			ticks.push(["<s:text name="Energy.febb" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			X_axisLable="<s:text name="Energy.Feb" />";
			}
			else if(month == 3)
			{
			ticks.push(["<s:text name="Energy.March" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			X_axisLable="<s:text name="Energy.March" />";
			}
			else if(month == 4)
			{
			X_axisLable="<s:text name="Energy.April" />";
			ticks.push(["<s:text name="Energy.April" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 5)
			{
			X_axisLable="<s:text name="Energy.May" />";
			ticks.push(["<s:text name="Energy.May" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 6)
			{
			X_axisLable="<s:text name="Energy.June" />";
			ticks.push(["<s:text name="Energy.June" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 7)
			{
			X_axisLable="<s:text name="Energy.July" />";
			ticks.push(["<s:text name="Energy.July" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 8)
			{
			X_axisLable="<s:text name="Energy.augg" />";
			ticks.push(["<s:text name="Energy.Aug" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 9)
			{
			X_axisLable="<s:text name="Energy.sepp" />";
			ticks.push(["<s:text name="Energy.Sep" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 10)
			{
			ticks.push(["<s:text name="Energy.Oct" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			X_axisLable="<s:text name="Energy.octt" />";
			}
			else if(month == 11)
			{
			X_axisLable="<s:text name="Energy.novv" />";
			ticks.push(["<s:text name="Energy.Nov" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 12)
			{
			X_axisLable="<s:text name="Energy.decc" />";
			ticks.push(["<s:text name="Energy.Dec" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
      s1.push(Math.round("<s:property value='#object[0]'/>")/1000);
			</s:iterator>
			
      $.jqplot.config.enablePlugins = true;
      var X_axisLable;
      if(SlectedLanguage == 'en_US')
		{
		// ticks = ['jan 1', 'jan 2', 'jan 3', 'jan 4','jan 5','jan 6','jan 7','jan 8','jan 9','jan 10','jan 11','jan 12','jan 13','jan 14', 'jan 15', 'jan 16', 'jan 17','jan 18','jan 19','jan 20','jan 21','jan 22','jan 23','jan 24','jan 25','jan 26','jan 27','jan 28','jan 29','jan 30','jan 31'];
		// X_axisLable="September";
		}else if(SlectedLanguage == 'zh_CN'){
		// ticks = ['一月 1', '一月 2', '一月 3', '一月 4 ','一月 5','一月 6','一月 7','一月 8','一月 9','一月 10','一月 11','一月 12','一月 13','一月 14', '一月 15', '一月 16', '一月 17','一月 18','一月 19','一月  20','一月  21','一月  22','一月  23','一月  24','一月  25','一月  26','一月  27','一月  28','一月  29','一月  30','一月  31'];
		// X_axisLable="9月";
		}else if(SlectedLanguage == 'zh_TW'){
		// ticks = ['一月 1', '一月 2', '一月 3', '一月 4 ','一月 5','一月 6','一月7','一月 8','一月 9','一月10','一月11','一月12','一月13','一月14', '一月 15', '一月16', '一月17','一月18','一月19','一月20','一月 21','一月22','一月23','一月24','一月 25','一月 26','一月27','一月28','一月29','一月30','一月31'];
		// X_axisLable="9月";
		}
		
		
  var line = [['Cup Holder Pinion Bob', 7], ['Generic Fog Lamp', 9], ['HDTV Receiver', 15],
  ['8 Track Control Module', 12], [' Sludge Pump Fourier Modulator', 3],
  ['Transcender/Spice Rack', 6], ['Hair Spray Danger Indicator', 18]];

  var line2 = [['Nickle', 28], ['Aluminum', 13], ['Xenon', 54], ['Silver', 47],
  ['Sulfer', 16], ['Silicon', 14], ['Vanadium', 23]];

  var plot4 = $.jqplot('chart', [s1], {
      series:[
			{
				renderer:$.jqplot.BarRenderer,
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
                  	   },
			
			   pointLabels: {
                      show: true
                  		},
			}
		],
	 animate: !$.jqplot.use_excanvas,
      axes: {
          xaxis: {
              renderer: $.jqplot.CategoryAxisRenderer,
              label:X_axisLable ,
              labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
              tickRenderer: $.jqplot.CanvasAxisTickRenderer,
              tickOptions: {
                  		fontFamily: 'Georgia',
        			fontSize: '10pt',
        			angle: -45,
				textColor: '#000000',
				
		},
		 
	labelOptions: {
          fontFamily: 'Georgia, Serif',
          fontSize: '14pt',
	     textColor: '#000000',  
        },
	          ticks: ticks,
              tickInterval: 1,
              drawMajorGridlines: false,
              drawMinorGridlines: false,
              drawMajorTickMarks: false,
          },
          yaxis: {
              autoscale:true,
              label: 'KWh',
              labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
              tickRenderer: $.jqplot.CanvasAxisTickRenderer,
              tickOptions: {
                  angle: -30,
		      textColor: '#000000',
              },
			
		labelOptions: {
          fontFamily: 'Georgia, Serif',
          fontSize: '14pt',
	     textColor: '#000000',  
        },


		  
          },
          
      }
  });		
		
	  } else {
		  
		  <s:iterator value="currentDate" var="object">
			var test="<s:date name="#object[2]" format="MMM-dd"/>";
			
			var month="<s:date name="#object[2]" format="MM"/>";
			if(month == 1)
			{
			ticks.push(["<s:text name="Energy.jann" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			X_axisLable="<s:text name="Energy.Jan" />";
			}
			else if(month == 2)
			{
			ticks.push(["<s:text name="Energy.febb" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			X_axisLable="<s:text name="Energy.Feb" />";
			}
			else if(month == 3)
			{
			ticks.push(["<s:text name="Energy.March" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			X_axisLable="<s:text name="Energy.March" />";
			}
			else if(month == 4)
			{
			X_axisLable="<s:text name="Energy.April" />";
			ticks.push(["<s:text name="Energy.April" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 5)
			{
			X_axisLable="<s:text name="Energy.May" />";
			ticks.push(["<s:text name="Energy.May" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 6)
			{
			X_axisLable="<s:text name="Energy.June" />";
			ticks.push(["<s:text name="Energy.June" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 7)
			{
			X_axisLable="<s:text name="Energy.July" />";
			ticks.push(["<s:text name="Energy.July" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 8)
			{
			X_axisLable="<s:text name="Energy.augg" />";
			ticks.push(["<s:text name="Energy.Aug" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 9)
			{
			X_axisLable="<s:text name="Energy.sepp" />";
			ticks.push(["<s:text name="Energy.Sep" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 10)
			{
			ticks.push(["<s:text name="Energy.Oct" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			X_axisLable="<s:text name="Energy.octt" />";
			}
			else if(month == 11)
			{
			X_axisLable="<s:text name="Energy.novv" />";
			ticks.push(["<s:text name="Energy.Nov" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
			else if(month == 12)
			{
			X_axisLable="<s:text name="Energy.decc" />";
			ticks.push(["<s:text name="Energy.Dec" />"]+" "+["<s:date name="#object[2]" format="dd"/>"]); 
			}
    s1.push(Math.round("<s:property value='#object[0]'/>")/1000);
			</s:iterator>
			
    $.jqplot.config.enablePlugins = true;
    var X_axisLable;
    if(SlectedLanguage == 'en_US')
		{
		// ticks = ['jan 1', 'jan 2', 'jan 3', 'jan 4','jan 5','jan 6','jan 7','jan 8','jan 9','jan 10','jan 11','jan 12','jan 13','jan 14', 'jan 15', 'jan 16', 'jan 17','jan 18','jan 19','jan 20','jan 21','jan 22','jan 23','jan 24','jan 25','jan 26','jan 27','jan 28','jan 29','jan 30','jan 31'];
		// X_axisLable="September";
		}else if(SlectedLanguage == 'zh_CN'){
		// ticks = ['一月 1', '一月 2', '一月 3', '一月 4 ','一月 5','一月 6','一月 7','一月 8','一月 9','一月 10','一月 11','一月 12','一月 13','一月 14', '一月 15', '一月 16', '一月 17','一月 18','一月 19','一月  20','一月  21','一月  22','一月  23','一月  24','一月  25','一月  26','一月  27','一月  28','一月  29','一月  30','一月  31'];
		// X_axisLable="9月";
		}else if(SlectedLanguage == 'zh_TW'){
		// ticks = ['一月 1', '一月 2', '一月 3', '一月 4 ','一月 5','一月 6','一月7','一月 8','一月 9','一月10','一月11','一月12','一月13','一月14', '一月 15', '一月16', '一月17','一月18','一月19','一月20','一月 21','一月22','一月23','一月24','一月 25','一月 26','一月27','一月28','一月29','一月30','一月31'];
		// X_axisLable="9月";
		}
		
		
var line = [['Cup Holder Pinion Bob', 7], ['Generic Fog Lamp', 9], ['HDTV Receiver', 15],
['8 Track Control Module', 12], [' Sludge Pump Fourier Modulator', 3],
['Transcender/Spice Rack', 6], ['Hair Spray Danger Indicator', 18]];

var line2 = [['Nickle', 28], ['Aluminum', 13], ['Xenon', 54], ['Silver', 47],
['Sulfer', 16], ['Silicon', 14], ['Vanadium', 23]];

var plot4 = $.jqplot('chart', [s1], {
    series:[
			{
				renderer:$.jqplot.BarRenderer,
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
                	   },
			
			   pointLabels: {
                    show: true
                		},
			}
		],
	 animate: !$.jqplot.use_excanvas,
    axes: {
        xaxis: {
            renderer: $.jqplot.CategoryAxisRenderer,
            label:X_axisLable ,
            labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            tickRenderer: $.jqplot.CanvasAxisTickRenderer,
            tickOptions: {
                		fontFamily: 'Georgia',
      			fontSize: '10pt',
      			angle: -45,
				textColor: '#000000',
				
		},
		 
	labelOptions: {
        fontFamily: 'Georgia, Serif',
        fontSize: '14pt',
	     textColor: '#000000',  
      },
	          ticks: ticks,
            tickInterval: 1,
            drawMajorGridlines: false,
            drawMinorGridlines: false,
            drawMajorTickMarks: false,
        },
        yaxis: {
            autoscale:true,
            label: 'KWh',
            labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            tickRenderer: $.jqplot.CanvasAxisTickRenderer,
            tickOptions: {
                angle: -30,
		      textColor: '#000000',
            },
			
		labelOptions: {
        fontFamily: 'Georgia, Serif',
        fontSize: '14pt',
	     textColor: '#000000',  
      },


		  
        },
        
    }
});		
		 
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
<s:hidden name="labelroom"  id="findDays"></s:hidden>

 <div id="chart" style="margin: 140px 0px 4px 120px;"></div>
 <!-- <div id="flot-placeholder" style="background: rgb(242, 245, 236);height:306px;margin: 140px 0px 4px 110px;"></div> -->
 <div id="flot-memo" style="text-align:center;height:30px;width:250px;height:20px;text-align:center;float: right;margin: -54px 0px 0px 5px;"></div>
</body>
</html>

  