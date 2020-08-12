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
	<script src="<%=applicationName %>/resources/js/flotgraph/jquery.flot.js" type="text/javascript"></script>
	 <script src="<%=applicationName %>/resources/js/jquery.jqplot.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.time.js"></script>
	    <script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.pie.min.js"></script><script type="text/javascript" src="/js/flot/jquery.flot.symbol.js"></script>
		<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.axislabels.js"></script>
	<style>
	
		
	#monthflotcontainer {
    width: 600px;
    height: 400px;
    text-align: left;
}
#legend2{
	margin: -2px 0px 0px 375px;
    position: absolute;
    width: 152px;
}
</style>

<script>
//******* PIE CHART


var ticks=[];
var maxval;
var COLOR_NAMES = ["BlueViolet","Orange","Maroon","DarkGoldenRod","Salmon","SeaGreen","Orchid","Orchid","PaleGoldenRod","PaleGreen","PaleTurquoise","PaleVioletRed","PapayaWhip","PeachPuff","Peru","Pink","Plum","PowderBlue","Purple","Red","RosyBrown","DarkBlue","DarkCyan","DarkGoldenRod","DarkGray","DarkGrey","DarkGreen","DarkKhaki","DarkMagenta","DarkOliveGreen","Darkorange","DarkOrchid","DarkRed","DarkSalmon","DarkSeaGreen","DarkSlateBlue","DarkSlateGray","DarkSlateGrey","DarkTurquoise","AliceBlue","AntiqueWhite","Aqua","Aquamarine","Azure","Beige","Bisque","Black","BlanchedAlmond","Blue","BlueViolet","Brown","BurlyWood","CadetBlue","Chartreuse","Chocolate","Coral","CornflowerBlue","Cornsilk","Crimson","Cyan","DarkViolet","DeepPink","DeepSkyBlue","DimGray","DimGrey","DodgerBlue","FireBrick","FloralWhite","ForestGreen","Fuchsia","Gainsboro","GhostWhite","Gold","GoldenRod","Gray","Grey","Green","GreenYellow","HoneyDew","HotPink","IndianRed","Indigo","Ivory","Khaki","Lavender","LavenderBlush","LawnGreen","LemonChiffon","LightBlue","LightCoral","LightCyan","LightGoldenRodYellow","LightGray","LightGrey","LightGreen","LightPink","LightSalmon","LightSeaGreen","LightSkyBlue","LightSlateGray","LightSlateGrey","LightSteelBlue","LightYellow","Lime","LimeGreen","Linen","Magenta","MediumAquaMarine","MediumBlue","MediumOrchid","MediumPurple","MediumSeaGreen","MediumSlateBlue","MediumSpringGreen","MediumTurquoise","MediumVioletRed","MidnightBlue","MintCream","MistyRose","Moccasin","NavajoWhite","Navy","OldLace","Olive","OliveDrab","RoyalBlue","SaddleBrown","Salmon","SandyBrown","SeaGreen","SeaShell","Sienna","Silver","SkyBlue","SlateBlue","SlateGray","SlateGrey","Snow","SpringGreen","SteelBlue","Tan","Teal","Thistle","Tomato","Turquoise","Violet"];

var userDetails = null;
		var imvgDetails = new ImvgDetals();
		
		var DEVICE_TYPE_WISE = "DEVICE_TYPE_WISE";
		var LOCATION_WISE = "LOCATION_WISE";
		userDetails = new UserDetails();
	    

	    var options = {
	        series: {
	            bars: {
	                show: true,
	                barWidth: 0.6,
	                align: "center"
	                
	            }
	        },
	        xaxis: {
	            axisLabel: "Room List",
	            axisLabelUseCanvas: true,
	            axisLabelFontSizePixels: 12,
	            axisLabelFontFamily: 'Verdana, Arial',
	            axisLabelPadding: 10,
	            ticks: ticks,
	            tickLength: 0
	        },
	        yaxis: {
	            axisLabel: "Power Consumption kWh",
	            axisLabelUseCanvas: true,
	            axisLabelFontSizePixels: 12,
	            axisLabelFontFamily: 'Verdana, Arial',
	            axisLabelPadding: 3,
	            min:0,
	            max:maxval
	           /*  tickSize: 5, */
	             /* tickFormatter: function (values, axis) {
	                return values;
	            }  */
	        },
	        legend: {
	            noColumns: 0,
	            labelBoxBorderColor: "#000000",
	            position: "nw",
	            container:$("#legend2")
	        },
	        grid: {
	            hoverable: true,
	            borderWidth: 2,
	            backgroundColor: { colors: ["#ffffff", "#EDF5FF"] }
	        }
	    };
	    
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
	                    "<strong>" + item.series.label + "</strong><br>" + item.series.xaxis.ticks[item.dataIndex].label + " : <strong>" + y + "</strong>");
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
	    /*  var dat = [[0, 11],[1, 15],[2, 25],[3, 24],[4, 13],[5, 18]]; */
        				/* {label: "2012 Average Temperature", data: val, color: "Green"} ];*/
       // var ticks = [[0, "London"], [1, "New York"], [2, "New Delhi"], [3, "Taipei"],[4, "Beijing"], [5, "Sydney"]];		 
        	      	           
$(document).ready(function () {

 
			var val=[];
			var dataset =[{label:"Total Energy Usuage",data:val,color:"#e74c3c"}]; 
            var monthdate=[];
            var monthsname="";
            var previous="";
            var num=1;
            var number=1;
			<s:iterator value="currentDate" var="object">
			var tempval=("<s:property value='#object[0]'/>");
			tempval=tempval/1000;
			var alertval=("<s:property value='#object[0]'/>");
			val.push([number,alertval/1000]);
        	monthdate.push("<s:property value='#object[1]'/>");
        	if((tempval>=previous)||(previous==null)){
        		previous=Math.round(tempval);
        	}
        	number=number+1;
		    </s:iterator>
        	 maxval=previous+10;     
        	
				 for(var i=0;i<monthdate.length;i++){
					var datetime=(monthdate[i]);
					var values=datetime.split(" ");
					var date=new Date(values[0]);
					locale = "en-us",
				    month = date.toLocaleString(locale, { month: "long" });
				 	if(monthsname!=month){
				 	var tickers = [num,month];
					num+=1;
					ticks.push(tickers);
				 	}
				 	monthsname=month;
				 }  
				
				 $.plot($("#barchartusuage"),dataset, options);
				 
			     $("#barchartusuage").UseTooltip();

			
		});

 



</script>
	</head>
		<body>
		<div id="legend2"></div>
		<div  id="barchartusuage" style="width:550px;height:280px;position: absolute;margin-top: 14px;" ></div>
		</body>
</html>
