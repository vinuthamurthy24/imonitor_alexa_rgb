<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
String applicationName = request.getContextPath();
%>



<html>
<head>
<title>iMonitor</title>
     
	  <link type="text/css" href="<%=applicationName %>/resources/css/style.css" rel="stylesheet" />
		<link rel="shortcut icon" href="<%=applicationName %>/resources/images/favicon.ico" type="image/-icon" />	
		<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />	
		 	<link type="text/css" href="<%=applicationName %>/resources/css/themes/base/ui.all.css" rel="stylesheet" />
         <link type="text/css" href="<%=applicationName %>/resources/css/jq.css" rel="stylesheet" />
		 <link type="text/css" href="<%=applicationName %>/resources/css/cycle.css" rel="stylesheet" />

<script>
</script>

		 <style type="">
		 .division
		 {
		 line-height: 5px;
		 
		 }
		 
		 h1,h2{
		 font-family: 'trebuchet ms', verdana, arial;
		 }
		 
		/*  input {
		border: 2px solid black;
		border-radius : 2px;
		} */
		
		 .texts
		 {
		  font-family: 'trebuchet ms', verdana, arial;
		  font-size: 16px;
		  border-radius:8px;
		  background-color: #3a8fce;
		  border: none;
		  padding: 10px;
		  text-align: center;
		  text-decoration: none;
		  display: inline-block;
		  margin: 2px 0px 0px -82px;
		  cursor: pointer;
		  color: white;
		  width: 100px;
		 }
		 
	label {
			color: teal;
			 font-family: 'trebuchet ms', verdana, arial;
			font-size: 20px;
			margin-top: 2px;
			text-align: left;
			}
			
		 table {
			border: thick;	
			
			}
			
			.helpicon{
			width: 20px;
			height: 20px;
			margin-top:21px;
			margin-left:220px;
			position:absolute;
			cursor:help;
			}
			
			td{
			vertical-align: middle;
			text-align: left;
	/* 		width: 171px; */
			
			}
			
			td:nth-child(even) {
    				width: 171px;
					}
					
					.message{
					font-family: serif;
    				font-size: 41px;
   					 margin-left: 80px;
   					 color: black;
					}
			
		
			
		 </style>
		 
</head>
<body>
<div class="header_container">
			<div class="header">
				<div class="logo">
					<a href="#"><img src="<%=applicationName %>/resources/images/logoBlack.png"
						alt="Applane" style=" width: 255px; height: 78px; margin-top: -32px; "/></a>
				</div>
				<h1 class="blue" style="font-size: 24px; float: right; margin: -58px 0px 0px 0px;"">MAKING HOMES INTELLIGENT</h1>
			</div>
		</div>	
		
<div class="message">
<s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
		</div>
		</body>
	</html>



