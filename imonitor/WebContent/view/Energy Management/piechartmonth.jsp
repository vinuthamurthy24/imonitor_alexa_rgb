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
				
		<link rel="shortcut icon" href="<%=applicationName %>/resources/images/favicon.ico" type="image/-icon" />		
		<link type="text/css" href="<%=applicationName %>/resources/css/imonitor.css" rel="stylesheet" />
		<link type="text/css" href="<%=applicationName %>/resources/css/jquery-ui.css" rel="stylesheet" />
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />
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
		<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.min.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.time.js"></script>    
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jshashtable-2.1.js"></script>    
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.numberformatter-1.2.3.min.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.pie.min.js"></script><script type="text/javascript" src="/js/flot/jquery.flot.symbol.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.flot.tooltip.min.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/flot/jquery.flot.axislabels.js"></script>	
	
		<style>
#labelpie{
position: absolute;
    margin: 255px 0px 0px 1225px;
    font-size: 13px;
    font-family: sans-serif;
    font-weight: bold;
  	width: 140px;
}		
#legends {
        margin: 112px 0px 0px 1046px;
        position: absolute;
        word-break: break-word;
        font-size: 15px;
    	font-family: serif;
    	  width: 120px;
      }		
.subflot{
	float:right;
	margin-right: -8px;
    margin-top: -148px;
    width: 200px;
    height: 150px;
}
.flotTip {
    padding: 3px 5px;
    text-align: center;
    border-radius: 6px;
    position: absolute;
    z-index: 100;
    background-color: #000;
    color: #fff;
    opacity: .80;
    filter: alpha(opacity=85);
     
}
</style>
<script>


var dataSet =[];
var CSS_COLOR_NAMES = ["DarkViolet","GreenYellow","Aqua","DeepSkyBlue","MediumSeaGreen","DeepPink","Gray","Lime","Yellow","SlateBlue","Purple","MediumSpringGreen","PeachPuff","Peru","Orange","Plum","PowderBlue","Red","RosyBrown","DarkBlue","DarkCyan","DarkGoldenRod","DarkGray","DarkGrey","DarkGreen","DarkKhaki","DarkMagenta","DarkOliveGreen","Darkorange","DarkOrchid","DarkRed","DarkSalmon","DarkSeaGreen","DarkSlateBlue","DarkSlateGray","DarkSlateGrey","DarkTurquoise","AliceBlue","AntiqueWhite","Aquamarine","Azure","Beige","Bisque","Black","BlanchedAlmond","Blue","BlueViolet","Brown","BurlyWood","CadetBlue","Chartreuse","Chocolate","Coral","CornflowerBlue","Cornsilk","Crimson","Cyan","DarkViolet","DeepPink","DeepSkyBlue","DimGray","DimGrey","DodgerBlue","FireBrick","FloralWhite","ForestGreen","Fuchsia","Gainsboro","GhostWhite","Gold","GoldenRod","Gray","Grey","Green","GreenYellow","HoneyDew","HotPink","IndianRed","Indigo","Ivory","Khaki","Lavender","LavenderBlush","LawnGreen","LemonChiffon","LightBlue","LightCoral","LightCyan","LightGoldenRodYellow","LightGray","LightGrey","LightGreen","LightPink","LightSalmon","LightSeaGreen","LightSkyBlue","LightSlateGray","LightSlateGrey","LightSteelBlue","LightYellow","Lime","LimeGreen","Linen","Magenta","MediumAquaMarine","MediumBlue","MediumOrchid","MediumPurple","MediumSeaGreen","MediumSlateBlue","MediumSpringGreen","MediumTurquoise","MediumVioletRed","MidnightBlue","MintCream","MistyRose","Moccasin","NavajoWhite","Navy","OldLace","Olive","OliveDrab","RoyalBlue","SaddleBrown","Salmon","SandyBrown","SeaGreen","SeaShell","Sienna","Silver","SkyBlue","SlateBlue","SlateGray","SlateGrey","Snow","SpringGreen","SteelBlue","Tan","Teal","Thistle","Tomato","Turquoise","Violet"];
var userDetails = null;
		var imvgDetails = new ImvgDetals();
		
		var DEVICE_TYPE_WISE = "DEVICE_TYPE_WISE";
		var LOCATION_WISE = "LOCATION_WISE";
		userDetails = new UserDetails();
		
		
var options1 = {
    series: {
        pie: {
            show: true,
            innerRadius:0.5,
            /* grid: {
                hoverable: true
            }, */
			/* label: {
                show: true,
                radius: 180,
                formatter: function (label, series) {
                    return '<div style="border:1px solid grey;font-size:8pt;text-align:center;padding:1px;color:white;">' +
                    label + ' : ' +
                    parseFloat(series.percent).toFixed(2)  +
                    '%</div>';
                },
                background: {
                    opacity: 0.8,
                    color: '#000'
                }
            } */
           
             }
    },
	legend: {
        show: true,
        container:$("#legends")
    },
   
    grid: {
        hoverable: true,
    },
    tooltip: true,
    tooltipOpts: {
        content: function(label,x,y){
            return y.toFixed(2)+"kwh,%s";
        }, // show percentages, rounding to 2 decimal places
        shifts: {
            x: 20,
            y: 0
        },
        defaultTheme: false
    }
};


$(document).ready(function () {

 
             var locationTypes=[];
             var filteredLocation=[];
             var test;
			<s:iterator value="currentDate" var="object">
			test="<s:property value='#object[2]'/>";
		    locationTypes.push("<s:property value='#object[1]'/>", "<s:property value='#object[0]'/>"); 
		    </s:iterator>
  
            var previous =  null;
    	    var currentValue = 0;
    	    
    	    for(var i=0;i<=locationTypes.length;i++){
    	    	var current = locationTypes[i];
    	    	var alertValue = locationTypes[i+1];
    	    	//alert("previous location: "+previous);
    	    	if((current == previous) || (previous == null)){
    	    	//	alert("current valueeeee"+ currentValue);
    	    		currentValue = parseFloat(currentValue)+parseFloat(alertValue);
    	    		
    	    	}else{
    	    		
    	    		//alert(currentValue);
    	    		filteredLocation.push(previous,currentValue);
    	    		currentValue = locationTypes[i+1];
    	    	}
    	    	i=i+1;
    	    	previous = current;
    	    }
    	    
        	
				for(var i=0;i<filteredLocation.length;i++){		
				var locationType = filteredLocation[i];
				var value=filteredLocation[i+1];
				value=value/1000;
					i=i+1;
					var coord = {label: locationType, data: value, color: CSS_COLOR_NAMES[i]};
					dataSet.push(coord);
					
				}
				
				
				$.plot($("#subflot-placeholder"), dataSet, options1);
				$("#labelpie").text(test);
				
				
		
    
    /* $("#flot-placeholder").showMemo(); */
	
	});


$.fn.showMemo = function () {
    $(this).bind("plothover", function (event, pos, item) {
        if (!item) { return; }
       alert(item.series.data);
        var html = [];
        var percent = parseFloat(item.series.percent).toFixed(2);        
		
        html.push("<div style=\"border:1px solid grey;font-size: 10px;background-color:",
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
<body>
<div  id="subflot-placeholder" class="subflot"></div>
<!-- <div id="displaydailyinfo" style="font-weight: bold;
font-size: medium;margin: 0px 4px -28px 20px;">Today's Consumption</div> -->
<!-- <div id="flot-placeholder" style="width:250px;margin:52px 1px -9px -150px;float:left;height:125px;margin:52 auto"></div> 
 <div id="flot-memo" style="font-family: serif; width:100px;height:20px;text-align:center;float: left;margin: 182px 123px 0px -171px;"></div> -->
<div id="legends"></div>
<div><label id="labelpie"></label></div>
</body>
</html>

  