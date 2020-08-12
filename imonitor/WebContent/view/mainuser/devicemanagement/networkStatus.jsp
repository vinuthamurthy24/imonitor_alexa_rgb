<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<html>
<script type="text/javascript">
	/* var iconValue=$("#icon").val();
	$("#displayIcon").attr('src',iconValue);
	$("#displayIcon1").attr('src',iconValue);
	$("#displayIcon2").attr('src',iconValue); */
	
	//var colorcode=$("#colorcode").val();
    

	
	
	var listsize=$("#listSize").val();
	
	
	
	
	/* for ( var int = 1; int <=listsize; int++)
	{
		$("#rssiListUl").append("<li class='images'> <img id='' width='70' height='70'  src='"+iconValue+"'/></li>");
		$("#rssiListUl").append("<li class='images'> <img id='' width='70' height='70'  src='/imonitor/resources/images/straight-right-arrow.png'/></li>");
	};
	
	$("#rssiListUl").append("<li class='images'> <img id='' width='140' height='100'  src='/imonitor/resources/images/Router-PNG-Image1.png'/></li>"); */
	

	/* var rssiUl = $("#rssiListUl");

	var rssiLi=this.generateHtml();
	alert("3");
	rssiUl.append(rssiLi);
	rssiUl.html("");
	
	    function generateHtml(){
		alert("Function");
	    var contentDiv = $("<div></div>");
	    contentDiv.append($("<ul class='deviceeditul'><ul>")
				.append("<li > <img id='' width='' height=''  src="+ iconValue+"/></li>")
		);
	    return contentDiv;
	};
	
	rssiUl.html("");
	 */
	

	  $(document).ready(function() {
		  
		  var rssiRow = "";
	 	  var rssiInfoCloum = "";
		  
	 		<s:iterator value="rssiInfos" var="rssidetail" >
	 		var device =<s:property value="deviceName" />;
	 		//var icon = <s:property value="icon" />
	 		var value = <s:property value="rssiValue" />
	 		var color = <s:property value="color" />
	 		var arrowvalue="";
	 		if (color==0)
	 		{
	 			 arrowvalue+="/imonitor/resources/images/straight-right-arrow-red.png";
	 			
	 		}else if (color==1) 
	 		{
	 			 arrowvalue+="/imonitor/resources/images/rightOrange.png";
	 		}
	 		else
	 		{
	 			 arrowvalue+="/imonitor/resources/images/straight-right-arrow.png";
	 		}
	 		
	 		rssiInfoCloum += "<td class='deviceName'>"+device+"</td><td><span class='span'><b>"+value+"</b></span><span><img id='' width='90' height='70'  src='"+arrowvalue+"'/></span></td>";
	 		
	 		
	 		
	 		</s:iterator>
	 		rssiInfoCloum +="<td><img id='' width='140' height='100'  src='/imonitor/resources/images/Router-PNG-Image1.png'/></td>";
	 		rssiRow+="<tr>"+rssiInfoCloum+"</tr>";
	 		$("#rssiTable").html(rssiRow);
	 		
	 		/* $("#rssiTable").dialog('open');
			 $("#rssiTable").dialog({
				       
				        width: 550,
		                height: 650,
		                modal : true,
		                
		                title: "Follow this procedure",
		                 buttons: {
		 					Ok: function() {
		 					
		 						$( this ).dialog('destroy');
		 						
		 						
		 					},
			 Cancel:function(){
				 $(this).dialog('close');
				 $(this).dialog('destroy');
			 }
		 					}
		        		});*/
	 		
	 	});  
	
</script>



<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">

.rssivalues {
    position: absolute;
    top: 44%;
    left: 4%;
	color: black;
	
}

.gatewayName {
    position: absolute;
    top: 73%;
    left: 73%;
	color: blue;
	font-size: large;
}

.gatewayImg{
   position: absolute;
   left:70%;
   top:27%;
}


.images{
display: inline;
	padding-right: 65px;
	margin-top:44px;
	margin-left: 40px;
	
}

ul.ui {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
}

.liRssi {
position:absolute;
top:42%;
left:2%;
margin-left: 15%;
}

li.liDeviceName {
	position:absolute;
	display:inline;
	float: left;
	top:65%;
	left:2%;
	padding-right: 0px;
	margin-left: 3%;
}

li.licolor {
display:inline;
	float: left;
	padding-right: 80px;
}

.images{
float: left;
margin-left:0px;
margin-top:20px;
padding-right: 3%;
}


.deviceName{
background-color: gray;
border: none;
    color: white;
    padding: 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 12px;
     height:50px;
   width:50px; */
}


.span{
	position: relative;	
	top:20%;
	left:50%;
}


</style>
</head>
<body>
<div id="">

 <!-- <div id="images" class="images" >
 <img id="displayIcon1" width="70" height="70" src=""/>
 <img id="arrow1" width="90" height="80" alt="" src="">
<img id="displayIcon2" width="70" height="70" src=""/>
<img id="arrow2" width="90" height="80" alt="" src="">
<img id="displayIcon3" width="70" height="70" src=""/>
<img id="arrow3" width="90" height="80" alt="" src="">
<img width="140" height="100" class="gatewayImg" src="/imonitor/resources/images/Router-PNG-Image1.png">
</div> -->


<%-- <div class="">
<s:iterator value="rssiInfos" >
<i></i><s:hidden id="" name=""></s:hidden>
<li class="liDeviceName"><b><s:property value="deviceName" /></b></li>
<s:hidden id="icon" name="icon"></s:hidden>
<s:hidden id="listSize" name="rssiInfos.size()"></s:hidden>
<s:hidden id="colorcode" name="color"></s:hidden>
<li class="liRssi"><b><s:property value="rssiValue" /></b></li>
</s:iterator>
</div> --%>


<div id="rssiListUl" class="">
	
</div>
<table id='rssiTable' cellpadding="10">
	</table>
</div> 
</body>
</html>