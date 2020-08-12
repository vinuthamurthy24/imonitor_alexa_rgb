<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String applicationName = request.getContextPath();
%>

<html>
<head>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.slider.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.util.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/in.xpeditions.user.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.min.js"></script>
<!-- sumit start -->
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery.ui.sortable.js"></script>
<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui-1.8.21.custom.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style type="text/css">

.signin_btn
{
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

}

table {
	border: 1;
	padding: 10px;
}

</style>

<script type="text/javascript">

$( function() {
    $( "#dialog" ).dialog();
  } );


function DestoryDialog()
{
           $("#editmodal").dialog('destroy'); 
}	

</script>
</head>
<body>
<div id="">
<h1>Indoor Units</h1>
<form action="" method="post" id="vrvForm">
<s:hidden name="device.generatedDeviceId"></s:hidden>
<s:hidden name="device.gateWay.macId"></s:hidden>
<s:hidden name="device.id"></s:hidden>
<s:hidden name="device.deviceId"></s:hidden>

<table cellpadding="20px" border="1" class="">
<tr><th>VRV 1<table border="0.5"><tr><th>Connection Status</th><th>Communication Status</th></tr></table></th><th>VRV 2</th><th>VRV 3</th><th>VRV 4</th></tr>
<tr><td>1</td><td>1</td><td>1</td><td>1</td></tr>
<tr><td>2</td><td>2</td><td>2</td><td>2</td></tr>
<tr><td>3</td><td>3</td><td>3</td><td>3</td></tr>
<tr><td>4</td><td>4</td><td>4</td><td>4</td></tr>
</table>
<%-- <div align="center">
<input type="submit" value="Yes" class="signin_btn" onclick=""  />&nbsp;&nbsp;
<!-- <button class="signin_btn" onclick="close()">No</button> -->
<input  type="button" class="signin_btn" value="No" onclick="javascript:DestoryDialog()" />

<!-- <input type="button" value="No" class="signin_btn" onclick="close()"  /> -->
</div> --%>
</form>
</div>
</body>
</html>