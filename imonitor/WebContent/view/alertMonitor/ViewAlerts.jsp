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
<title>Welcome Admin...</title>

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
    
   
	
    function InitDataTable()
	{
    	
		unResolvedTable = $('#listcustomealerts').dataTable({
		
	
			"aoColumns": [
			              { "bSortable": false , "sClass": "center"},
				           { "bSortable": false , "sClass": "center"},
				           { "bSortable": false, "sClass": "center" },
				           { "bSortable": false, "sClass": "center" },
				           { "bSortable": false, "sClass": "center"},
				           { "bSortable": false, "sClass": "center"},
				           
				         ], 
		/* "aoColumns": [
                       { "sWidth": "8%" ,"sClass": "center", "bSortable": true},
		               { "sWidth": "5%" ,"sClass": "center", "bSortable": false},
		               { "sWidth": "3%", "sClass": "center", "bSortable": true },
		               { "sWidth": "4%", "sClass": "center", "bSortable": true }, 
		               { "sWidth": "10%", "sClass": "center", "bSortable": false },
		               { "bSortable": false, "bVisible":false},
		            
		             

		             
		            ], */
		        
		"bProcessing": true,
		"bServerSide": true,
		"bDestroy": true,
		"bSort": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"sAjaxSource": "listAllCustomersAlerts.action",
		"oLanguage": 
		{
			"sLengthMenu": "<s:text name='setup.datatables.showentries' />",
			"sProcessing": "<s:text name='setup.datatables.processing' />",
			"sZeroRecords": "<s:text name='setup.datatables.emptytable' />",
			"sInfo": "<s:text name='setup.datatables.info' />",
			"sInfoEmpty": "<s:text name='setup.datatables.infoempty' />",
		    "sInfoFiltered": "",
		},
		
		"order": [[1, 'desc']],
	});
		
		//setInterval( function () { RefreshReports(1);}, 10000 );
		

	
		
	}
 

    $(document).ready(function() {
    	
    	 

    	InitDataTable();
    	  
 
 
 
 
    });
	

    
    
</script>


<style>

</style>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload=""  style="background-color: EDFCFC">
	
	<s:form>
	<div id="viewAlerts">
		<table cellpadding="0" cellspacing="2" border="0" class="display" id="listcustomealerts" style="white-space: nowrap; table-layout: fixed;">
		<thead>
		<tr>
			<th style="text-align: center;">Alert Time</th>
			<th style="text-align: center;">Area Code</th>
			<th style="text-align: center;">Customer</th>
			<th style="text-align: center;">SMS Description</th>
			<th style="text-align: center;">Contact No.</th>
				<th></th>	
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="3">Data Loading ...</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th style="text-align: center;">Alert Time</th>
			<th style="text-align: center;">Area Code</th>
			<th style="text-align: center;">Customer</th>
			<th style="text-align: center;">SMS Description</th>
			<th style="text-align: center;">Contact No.</th>
		<th></th>
			
				
		</tr>
	</tfoot>
		</table>
		</div>
	
	
	
		</s:form>
		
	
	<div id="editmodal"></div>
	<div id="confirm"></div>
	<%-- <div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div> --%>
</body>
</html>
