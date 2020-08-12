<%-- Copyright Ãƒâ€šÃ‚Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ page language="java" contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
    <%
String applicationName = request.getContextPath();
%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@page import="com.opensymphony.xwork2.ActionContext"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"></meta>
<title>Welcome</title>

	<link rel="shortcut icon" href="<%=applicationName %>/resources/images/favicon.ico" type="image/-icon" />
	<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.7.1.js"></script>
	 <script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.1.8.9.js"></script> 
	<script src="<%=applicationName %>/resources/js/jquery.in.xpeditions.accordion.js" type="text/javascript"></script>
	<script src="<%=applicationName %>/resources/js/jquery.dataTables.js" type="text/javascript"></script>
	<script src="<%=applicationName %>/resources/js/in.xpeditions.util.js" type="text/javascript"></script>
	<script src="<%=applicationName %>/resources/js/in.xpeditions.js" type="text/javascript"></script>
	
	<link rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" />
	<link rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/in.xpeditions.css" />
	<link rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/demo_table.css" />
	
	<%-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.js"></script> --%>
	
	
</head>
<%@include file="../mainuser/messages.jsp" %>
<script type="text/javascript">
    window.history.forward();
    function noBack() { window.history.forward();}
    
   $(document).ready(function() {
    	
	   $('#viewAlert').click(function() {
		   alert("dsdsd");
		   $('#contentsection').css({
		       position:'absolute', //or fixed depending on needs 
		       top: $(window).scrollTop(), // top pos based on scoll pos
		       left: 0,
		       height: '100%',
		       width: '100%'
		   });
		 });
	   
	   $('#createnew').trigger('click');
 
    });
	

    
    
</script>


<style>

#sortview{
margin: -2px 0px 0px 8px;
width: 343px;
border-bottom-color: black;
background-color: rgb(220, 220, 224);
height: 87px;
-webkit-box-shadow: 0px 0px 12px rgba(0, 0, 0, 5.4); 
	-moz-box-shadow: 0px 1px 6px rgba(23, 69, 88, .5);
	-webkit-border-radius: 12px;
	-moz-border-radius: 7px; 
}
.bbtn
{
padding:4px 10px;
letter-spacing:-0.03em;
text-align:center;
background:#3a8fce url(../images/bkg-btn-blue.gif) repeat-x 0 top;
color:#fff;
text-shadow:0 -1px 2px #2063AB;
box-shadow: 2px 2px 2px rgba(0,0,0,.5);
border:1px solid #2f6ea7;border-color:#508fcd #4483bf #2f6ea7 #3f7eb9;
border-radius:4px;
text-decoration:none;
cursor: pointer;
} 
#contentsection{
float: left;
width: 1184px;
height: 519px;
margin-left: 10px;
background-color: #F4F5E9;
padding-left: 20px;
padding-top: 20px;
overflow: auto;
}

.Logout{
text-decoration: none;
position: relative;
top: 33px;
left: 1114px;
color: white;
}
#bt {
  background: #3498db;
  background-image: -webkit-linear-gradient(top, #3498db, #2980b9);
  background-image: -moz-linear-gradient(top, #3498db, #2980b9);
  background-image: -ms-linear-gradient(top, #3498db, #2980b9);
  background-image: -o-linear-gradient(top, #3498db, #2980b9);
  background-image: linear-gradient(to bottom, #3498db, #2980b9);
  -webkit-border-radius: 14;
  -moz-border-radius: 14;
  border-radius: 8px;
  font-family: Arial;
  color: #ffffff;
  font-size: 20px;
  padding: 10px 20px 10px 20px;
  text-decoration: none;
}

#btn:hover {
  background: #3cb0fd;
  background-image: -webkit-linear-gradient(top, #3cb0fd, #3498db);
  background-image: -moz-linear-gradient(top, #3cb0fd, #3498db);
  background-image: -ms-linear-gradient(top, #3cb0fd, #3498db);
  background-image: -o-linear-gradient(top, #3cb0fd, #3498db);
  background-image: linear-gradient(to bottom, #3cb0fd, #3498db);
  text-decoration: none;
}
.bt
{
font-size:1em !important;
line-height:1em
color:#ffffff
cursor: pointer;
}
.contentsection{
 /* background: url('/imonitor/resources/images/background_image5.jpg')no-repeat fixed center;  */
	-moz-background-size: cover;
-webkit-background-size: cover;
}


.mainTab
{
    background-color:#3C3A38;
	width:1226px;
	height:43px;
	text-align:center;
	font-weight: bolder;
    font-size: x-large;
}

.subTab{
width: 185px;
    padding: 0px;
    padding-left: 0px;
    text-align: center;
    color: blue;
  
}
.spaceTab{
    width: 1px;
    background-color: black;
}
.Tabdecorate{
width: 185px;
    height: 6px;
    /* border: 1px solid #000; */
    background-color: #F1A57B;
}
</style>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload=""  style="background-color: EDFCFC">
	<div class="maindiv">
		<div id="logo1" class="logo">
		<a href="#"><img src="<%=applicationName %>/resources/images/logoBlack.png"
						alt="Applane" style=" width: 214px; height: 50px; margin-top: 2px; "/></a>
		</div>
		<div class="Logout"><a id="bt" href="../logout.action">logout</a></div>
		<table id="mainTab" class="mainTab">
		<tbody>
		<tr>
		<td class="subTab">
		<!-- <div class="Tabdecorate"></div> -->
		<a id="createnew" href="getListToModifyAlerts.action" class='ajaxlink' style="text-decoration:none;color: aliceblue;">Security Service</a>
		</td>
		
		<!-- <td class="subTab">
		<div class="Tabdecorate"></div>
		<a id="createnew" href="getListOfMonitoringAlerts.action" class='ajaxlink' id="viewAlert">View Alerts</a>
		</td> -->
		
		<!-- <td>
		<a id="logoutlink1" href="../logout.action">logout</a>
		</td> -->
		</tr>
		</tbody>
		</table>

		<div id="contentsection" class="contentsection">

		</div>
		
		<div class="footer">
			Powered by &#169; iMonitor Solutions
		</div>
	</div>
	<div id="editmodal"></div>
	<div id="confirm"></div>
	<%-- <div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div> --%>
</body>
</html>
