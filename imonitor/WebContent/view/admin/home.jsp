<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
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
<title>Welcome Admin...</title>

	<link rel="shortcut icon" href="<%=applicationName %>/resources/images/favicon.ico" type="image/-icon" />
	<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="<%=applicationName %>/resources/js/jquery-ui.js"></script>
	<script src="<%=applicationName %>/resources/js/jquery.in.xpeditions.accordion.js" type="text/javascript"></script>
	<script src="<%=applicationName %>/resources/js/jquery.dataTables.js" type="text/javascript"></script>
	<script src="<%=applicationName %>/resources/js/in.xpeditions.util.js" type="text/javascript"></script>
	<script src="<%=applicationName %>/resources/js/in.xpeditions.js" type="text/javascript"></script>
	
	<link rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" />
	<link rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/in.xpeditions.css" />
	<link rel="stylesheet" type="text/css" href="<%=applicationName %>/resources/css/demo_table.css" />
</head>
<%@include file="../mainuser/messages.jsp" %>
<script type="text/javascript">
    window.history.forward();
    function noBack() { window.history.forward();
 }
    
</script>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload=""  style="background-color: EDFCFC">
	<div class="maindiv">
		<div id="logo" class="logo">
		</div>
		<div id="titlebar" class="titlebar">
		<a id="logoutlink" href="../logout.action">logout</a>
		</div>
		<div id="leftmenu" class="leftmenu">
		<ul id="menu" class="xpaccordionmenu">
			<li>
				<a href="#">GateWay</a>
				<ul>
					<li><a href="toAddMake.action" class="ajaxlink">Add Make</a></li>
					<li><a href="listMakes.action" class="ajaxlink">List Make</a></li>
					<li><a href="addNewDeviceType.action" class="ajaxlink">Add DeviceType</a></li>
					<li><a href="listDeviceTypes.action" class="ajaxlink">List DeviceType</a></li>
					<li><a href="toAddAgent.action" class="ajaxlink">Add Agent</a></li>
					<li><a href="toListAgent.action" class="ajaxlink">List Agents</a></li>
					<li><a href="toAddGateWay.action" class="ajaxlink">Add GateWay</a></li>
					<li><a href="toListGateWay.action" class="ajaxlink">List Gateways</a></li>
					<li><a href="toListAVBlasterCodes.action" class="ajaxlink">AV Blaster</a></li>
					<li><a href="toUploadFirmWare.action" class="ajaxlink">Upload FirmWare</a></li>
					<li><a href="toUpdateFirmWare.action" class="ajaxlink">Update FirmWare</a></li>
					<li><a href="toListFirmWare.action" class="ajaxlink">List FirmWare</a></li>
					<!--<li><a href="toUpdateCameraConfiguration.action" class="ajaxlink">Camera Configuration</a></li>-->
					<li><a href="toRequestLog.action" class="ajaxlink">Maintenance</a></li>
					<li><a href="toAddDeviceDetails.action" class="ajaxlink">Device type details</a></li>
					<li><a href="toAddGatewayList.action" class="ajaxlink">Add gateway details</a></li>
					<li><a href="toListZingGateWays.action" class="ajaxlink">List Zing GateWay Details</a></li>
				</ul>
			</li>
			<li>
				<a href="#">Users</a>
				<ul>
					<li><a href="toAddCustomer.action" class="ajaxlink">Add Customer</a></li>
					<li><a href="toListCustomer" class="ajaxlink">List Customer</a></li>
					<li><a href="toAddUser.action" class="ajaxlink">Add User</a></li>
					<li><a href="listUsers.action" class="ajaxlink">List User</a></li>  
					<li><a href="toChangeUserPassword.action" class="ajaxlink">My Account</a></li>
					<li><a href="toEditUser.action" class="ajaxlink">My Profile</a></li>
					<li><a href="toAddSystemIntegrator.action" class="ajaxlink">Add System Integrator</a></li>
					<li><a href="toListSystemIntegrators.action" class="ajaxlink">System Integrator</a></li>
					<li><a href="tolistSMSServiceProvider.action" class="ajaxlink">SMS Service Provider </a></li>
                    <li><a href="tolistSMSReports.action" class="ajaxlink">SMS Reports </a></li>
                 <!-- SuperUser -->
                    <li><a href="toAddSuperUser.action" class="ajaxlink">Add SuperUser</a></li>
                    <li><a href="tolistSuperUsers.action" class="ajaxlink">List SuperUsers</a></li>
				</ul>
			</li>
		</ul>
		</div>
		<div id="contentsection" class="contentsection">
		</div>
		<div class="footer">
			footer section... 
		</div>
	</div>
	<div id="editmodal"></div>
	<div id="confirm"></div>
</body>
</html>


