<%-- Copyright Ãƒâ€šÃ‚Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ page language="java" contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
    <%
String applicationName = request.getContextPath();
%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@page import="com.opensymphony.xwork2.ActionContext"%><html>
<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
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
<div style="color: blue">
	<s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<script type="text/javascript">
    window.history.forward();
    function noBack() { window.history.forward();}
    
   
	
	/* $('.ajaxinlinepopupform').live('submit', function() {
		 var params = $(this).serialize();
		 $.post($(this).attr('action'), params, function(data){
			var a= $(data).find("p.message").text();
			alert(a);
			var aa = a.split(":");
			alert(aa[0]);
			alert(aa[1]);
			if(aa[0] =='Failure'){
				$("#ruleMessage").html(aa[1]);
				}
			else{
				$("#ruleMessage").html(aa[1]);
			 // $('#editmodal').dialog('close');
			}
		 });
		 return false;
	}); */


    $(document).ready(function() {
    	
    	var alertgroupTable = $('#listcustomealerts').dataTable({
    		
    		"sScrollY": 300,
			"sScrollX": "100%",
			"sScrollXInner": "100%",
			"aoColumns": [

			              { "bSortable": false , "sClass": "center"},
				           { "bSortable": false , "sClass": "center"},
				           { "bSortable": false, "sClass": "center" },
				           { "bSortable": false, "sClass": "center" },
				           { "bSortable": false, "sClass": "center"},
				           { "bSortable": false, "sClass": "center"},
				           { "bSortable": false, "sClass": "center"},
				           
				          
				         ],
		"fnRowCallback": function( nRow, aData, iDisplayIndex,iDisplayIndexFull) {
		            	 
			                $(nRow).each(function(index, td) {
			 					
			                   var a=$('td',nRow).html();
			                   var dlt=a.split(" ");
			                   var dlt2=dlt[0].split("-");
			              	   var dlt3=dlt[1].split(":");
			              	   var year=dlt2[2];
			              	   var month=dlt2[1];
			          
			                   var altime=new Date(year,month,dlt2[1],dlt3[0],dlt3[1],dlt3[2]);
			                   var currentdate=new Date();
			                   var diff=(currentdate.getTime()-(altime.getTime()))/(1000*60);
			                   
			                   if(diff<=1){
			                	
			                	   $(td).css("background-color", "#869470");
			                   }else if(diff>1 && diff<5){
			                	   $(td).css("background-color", "#FD7388");
			                   }else if(diff>=5 && diff<10){
			                	   $(td).css("background-color", "#FFD7DD");
			                   }else if(diff>=10 && diff<60){
			                	   $(td).css("background-color", "#A89696");
			                   }else if(diff>60){
			                	   $(td).css("background-color", "#869470");
			                   };
			                   });
			                return nRow;},
			                   
		
		        
		"bProcessing": true,
		"bServerSide": true,
		"bDestroy": true,
		"bSort": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"sAjaxSource": "manageCustomersAlerts.action",
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

    	setInterval( function () { RefreshSiteList(1);}, 10000 );	
		
		
		function RefreshSiteList(value)
		{
			alertgroupTable.fnDraw(false);
			var oSettings = alertgroupTable.fnSettings();
			var iTotalRecords = oSettings.fnRecordsTotal();
			
			
			if(iTotalRecords <= 10)
			{
				alertgroupTable.fnDraw();
			}
			else
			{
				alertgroupTable.fnDraw(false);
			}	
			
			//InitDataTable();
			
		} 
    	  
 
 
 
 
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
			<th style="text-align: center;">Apartment/Villa</th>
			<th style="text-align: center;">Status</th>
			<th style="text-align: center;">Description</th>
			<th style="text-align: center;">Contact No.</th>
			<th style="text-align: center;">Attended by</th>
		 <th style="text-align: center;">Action</th> 

					
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
			<th style="text-align: center;">Apartment/Villa</th>
			<th style="text-align: center;">Status</th>
			<th style="text-align: center;">Description</th>
			<th style="text-align: center;">Contact No.</th>
			<th style="text-align: center;">Attended by</th>
			 <th style="text-align: center;">Action</th> 
		
				
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
