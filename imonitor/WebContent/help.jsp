<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%
String applicationName = request.getContextPath();
%>

<%@page import="com.opensymphony.xwork2.ActionContext"%><html>
	<head>
		<title>
			imonitor - about
		</title>
		
		<link rel="shortcut icon" href="<%=applicationName %>/resources/images/favicon.ico" type="image/-icon" />	
		<link type="text/css" href="resources/css/in.xpeditions.css" rel="stylesheet" />
		
		
	
</head>
	<body class="bodyclasslogin">
	<div style="width:100%; height:80px;">
		<div class="mainmenu">
					<ul id="topmenu">
						<li>
							<a href="/imonitor/">Home</a>
						</li>
						<li>
							<a href="about.jsp">About</a>
						</li>
						<li>
							<a href="contact.jsp">Contact</a>
						</li>
						<li>
							<a href="help.jsp">Help</a>
						</li>
					</ul>
		</div>
	 </div>
	</body>
</html>	