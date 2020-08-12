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
    
    var unResolvedTable;
    var resolvedTable;
    var myRadio="UnResolved";
    
	function InitDataTable()
	{
		unResolvedTable = $('#listcustomereports').dataTable({
		
		
		
		"aoColumns": [
                       { "sWidth": "8%" ,"sClass": "center", "bSortable": true},
		               { "sWidth": "5%" ,"sClass": "center", "bSortable": false},
		               { "sWidth": "3%", "sClass": "center", "bSortable": true },
		               { "sWidth": "4%", "sClass": "center", "bSortable": true }, 
		               { "sWidth": "10%", "sClass": "center", "bSortable": false },
		               { "sWidth": "6%" ,"sClass": "center", "bSortable": false },
		               { "sWidth": "4%" ,"sClass": "center", "bSortable": false },
		               { "sWidth": "4%","sClass": "center", "bSortable": true  },
		               { "bSortable": false, "bVisible":false},
		               { "sWidth": "2%","sClass": "center", "bSortable": false  },
		             

		             
		            ],
		             "fnRowCallback": function( nRow, aData, iDisplayIndex,iDisplayIndexFull) {
		            	 $(nRow).children().each(function(index, td) {
		            		 
		            		 
		            		 if($(td).html() === "Critical")
								{
		            			 
		            			 $(this).css("background-color", "#F26F6F");
								}
		            		 
		            		
		            	 });
		            	 return nRow;
		            }, 
		"bProcessing": true,
		"bServerSide": true,
		"bDestroy": true,
		"bSort": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"sAjaxSource": "listAskedCustomerReports.action",
		"oLanguage": 
		{
			"sLengthMenu": "<s:text name='setup.datatables.showentries' />",
			"sProcessing": "<s:text name='setup.datatables.processing' />",
			"sZeroRecords": "<s:text name='setup.datatables.emptytable' />",
			"sInfo": "<s:text name='setup.datatables.info' />",
			"sInfoEmpty": "<s:text name='setup.datatables.infoempty' />",
		    "sInfoFiltered": "",
		},
		
		/* "order": [[1, 'desc']], */
	});
		
		setInterval( function () { RefreshReports(1);}, 10000 );
		

	
		//setInterval( function () { RefreshResolvedReport(1);}, 10000 );
	}
 	
 	
 	function RefreshReports(value)
	{
		unResolvedTable.fnDraw(false);
		var oSettings = unResolvedTable.fnSettings();
		var iTotalRecords = oSettings.fnRecordsTotal();
		
		
		if(iTotalRecords <= 10)
		{
			unResolvedTable.fnDraw();
		}
		/* else
		{
			unResolvedTable.fnDraw(false);
		}	 */
		
		
	 	 
	}
	
	
	

    
function fnFilterSeverity ()
{
	var sort=$("#SeverityOption").val();
		
	$('#listcustomereports').dataTable().fnFilter( 
				sort,2,true,true
		);
		
	
	
	

}

function fnFilterStatus ()
{
	
	var sort=$("#statusoption").val();
		
		$('#listcustomereports').dataTable().fnFilter( 
				sort,1,true,true
		);
		

}

function fnFilterRootCause ()
{
	
	var sort=$("#causeOption").val();
		
		$('#listcustomereports').dataTable().fnFilter( 
				sort,3,true,true
		);
		

}

    $(document).ready(function() {
    	
    	
    	InitDataTable();
    	
    	//$("#statusoption").hide();
    	//$("#SeverityOption").hide();
    	$("#statusoption").change(fnFilterStatus);
    	$("#SeverityOption").change(fnFilterSeverity);	
    	$("#causeOption").change(fnFilterRootCause);
     
    	//$('#resolvedreports').hide();
    	
    	
    	$(".checkStatus").live('click', function(){
		
		if($(this).attr('checked')){
			$("#statusoption").change(fnFilterStatus);
			$("#statusoption").show();
			
		}else{
			
			$("#statusoption").hide();
			$("#statusoption").val("");
			$("#statusoption").change(fnFilterStatus);
		}
		
});

$(".checkSeverity").live('click', function(){
	
	if($(this).attr('checked')){
		$("#SeverityOption").change(fnFilterSeverity);	
		$("#SeverityOption").show();
		
	}else{
		
		$("#SeverityOption").hide();
		$("#SeverityOption").val("");
		$("#SeverityOption").change(fnFilterSeverity);	
	}
	
});
    	
  
    	
    	function loadingDialog(dOpts, text)
        {
        	var dlg = $("<div><img src='../resources/images/loading.gif' style='margin: 0px 0px 0px 108px;'/> "+text+"<div>").dialog(dOpts);
       		$(".ui-dialog-titlebar").hide();

        return dlg;
  
     
        
    }
    	
 $("#GetReportsInExcel").live('click', function() {
        	
        	
         	var url = $(this).attr("href");
        	
        	$.ajax({
        		url: url,
    		  	success: function(data){
    		  		var pTitle = "Customer report";
    		  		$("#editmodal").html(data);
					$("#editmodal").dialog('open');
					$("#editmodal" ).dialog({
						
						stackfix: true,
						modal: true,
						title: pTitle,
						show: {
				            effect: "scale",
				            easing: "easeOutBack"
				        },
							buttons: {
								Ok:function(){
									$(this).dialog('close');
									$("#confirm").dialog("destroy");
								}
							}
					
					});
    		  		
    		  	}
        	});   
        	
        	
        	
        });
    	  
 //to get list of final status,severity and root cause for sorting
 
 /* function Getlistforsorting()
	{
	 
	 var lUrl = "getreportsortinglist.action";
		$.ajax({
			url: lUrl,
			cache: false,
			dataType: 'xml',
			success: function(){
				
				
			}
		
		});
	 
	}; */
 
 
 
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

#createnew{
float: left;
text-decoration: none;
color: white;
top: 7px;
position: relative;
left: 35px;
}

#logoutlink1{
text-decoration: none;
position: relative;
top: 7px;
left: 1088px;
color: white;
}

.bt
{
font-size:1em !important;
line-height:1em
color:#ffffff
cursor: pointer;
}
.contentsection{
 background: url('/imonitor/resources/images/background_image5.jpg')no-repeat fixed center; 
	-moz-background-size: cover;
-webkit-background-size: cover;
}

#listcustomereports td{
   overflow: hidden; 
   text-overflow: ellipsis;
   max-width:1px;
}
</style>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload=""  style="background-color: EDFCFC">
	<div class="maindiv">
		<div id="logo1" class="logo">
		<a href="#"><img src="<%=applicationName %>/resources/images/logoBlack.png"
						alt="Applane" style=" width: 214px; height: 50px; margin-top: 2px; "/></a>
		</div>
		<div id="titlebar" class="titlebar">
		<a id="createnew" href="createnewreport.action" class='ajaxlink'>Create New</a>
		<a id="logoutlink1" href="../logout.action">logout</a>
		</div>

		<div id="contentsection" class="contentsection">
		<s:form>
		<!-- <input type="radio" name="wisetype"  value="UnResolved" class="radioAlarm" onclick="clear_form(this.form)"/>Un-Resolved
        <input type="radio" name="wisetype"  value="Resolved"  class='radioAlarm' onclick="clear_form(this.form)"/>Resolved -->
		
		<!-- <div id="sortview"> -->
		
		
		<div id="finalstatus" style="margin: 12px 0px 0px 2px;">
		
		
		<select id="statusoption">
		<option value="">Select final status</option>
		<option value="Resolved">Resolved</option>
		<option value="Open">Open</option>
		<option value="Closed">Closed</option>
		<option value="Can't Fix">Can't Fix</option>
		<option value="In Progress">In progress</option>
		</select> 
		</div>
		
		
		<div id="severity" style="margin: -18px 0px 0px 155px;">
		<select id="SeverityOption">
		<option value="">Select severity</option>
		<option value="Critical">Critical</option>
		<option value="Major">Major</option>
		<option value="Minor">Minor</option>
		<option value="Moderate">Moderate</option>
		</select>
		</div> 
		
		<div id="rootcause" style="margin: -18px 0px 0px 298px;">
		<select id="causeOption">
		<option value="">Select root cause</option>
		<option value="Dual Switch Version Issues">Dual Switch Version Issues</option>
		<option value="Dual Switch Version Issues">Dual Switch Version Issues</option>
		<option value="Gateway Losing Data/Configuration">Gateway Losing Data/Configuration</option>
		<option value="Zwave Network/Range Issues">Zwave Network/Range Issues</option>
		<option value="Zwave Not retaining the network">Zwave Not retaining the network</option>
		<option value="Not following Standard Operation Procedures (SOP)">Not following Standard Operation Procedures (SOP)</option>
		<option value="Not Trained">Not Trained</option>
		<option value="Installation Wiring Issues"> Installation Wiring Issues</option>
		<option value="Technical Competency Issue">Technical Competency Issue</option>
		<option value="Low Internet or Power Issue">Low Internet or Power Issue</option>
		<option value="Android MID App Issue">Android MID App Issue</option>
		<option value="IOS MID App Issue">IOS MID App Issue</option>
		<option value="CMS UI Issue">CMS UI Issue</option>
		<option value="Support Communication Issue">Support Communication Issue</option>
		<option value="Product Spec not suitable for Requirements">Product Spec not suitable for Requirements</option>
		<option value="Other">Other</option>
		</select>
		</div>
		<!-- </div> -->
		
		<div id="downloadsection" style="margin:2px 0px 0px 0px;">
		
		<button style="margin: 0px 20px 0px 27px;width: 110px;float: right;height: 40px;" href='downloadCustomerReports.action' id="GetReportsInExcel"  type='button' class='bt bbtn' title='click to get reports in exel'>Download Reports</button>
		
		</div>

		<div id="viewreports">
		<table cellpadding="0" cellspacing="2" border="0" class="display" id="listcustomereports" style="white-space: nowrap; table-layout: fixed;">
		<thead>
		<tr>
			<th style="text-align: center;">CustomerId</th>
			<th style="text-align: center;">Reported Date</th>
			<th style="text-align: center;">Severity</th>
			<th style="text-align: center;">Final Status</th>
			<th style="text-align: center;">Issue Description</th>
			<th style="text-align: center;">Resolution</th>
			<th style="text-align: center;">Resolution Date</th>
			<th style="text-align: center;">Root cause</th>
			<th></th>
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
			<th style="text-align: center;">CustomerId</th>
			<th style="text-align: center;">Reported Date</th>
			<th style="text-align: center;">Severity</th>
			<th style="text-align: center;">Final Status</th>
			<th style="text-align: center;">Issue Description</th>
			<th style="text-align: center;">Resolution</th>
			<th style="text-align: center;">Resolution Date</th>
			<th style="text-align: center;">Root cause</th>
		<th></th>
			<th style="text-align: center;">Action</th>
				
		</tr>
	</tfoot>
		</table>
		</div>
	<!-- 	<div id="resolvedreports">
		<table cellpadding="0" cellspacing="2" border="0" class="display" id="listcustomeresolvedreports">
		<thead>
		<tr>
			<th style="text-align: center;">CustomerId</th>
			<th style="text-align: center;">Reported Date</th>
			<th style="text-align: center;">Severity</th>
			<th style="text-align: center;">Final Status</th>
			<th style="text-align: center;">Issue Description</th>
			<th style="text-align: center;">resolution</th>
			<th style="text-align: center;">resolutionDate</th>
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
			<th style="text-align: center;">CustomerId</th>
			<th style="text-align: center;">Reported Date</th>
			<th style="text-align: center;">Severity</th>
			<th style="text-align: center;">Final Status</th>
			<th style="text-align: center;">Issue Description</th>
			<th style="text-align: center;">resolution</th>
			<th style="text-align: center;">resolutionDate</th>
			<th style="text-align: center;">Action</th>
		</tr>
	</tfoot>
		</table>
		
		</div> -->
		
	
		</div>
		</s:form>
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
