<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page pageEncoding="UTF-8"%>

<style>
	.dataTables_length {
	width: 30%;
	float: left;
	margin-left: -75px;
	margin-top: 12px;
	}

	 .dataTables_info {
margin: 20px 0px 0px 10px;
}
	
</style>
<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				var publicationgrouptable = $('#listnotificationtable').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "listAskedSystemNotifications.action",
					"oLanguage": {
				         "sSearch": "<s:text name='setup.datatables.search' />",
				         "sProcessing": "<s:text name='setup.datatables.processing' />",
				         "sLengthMenu": "<s:text name='setup.datatables.showentries' />",
				         "sInfo": "<s:text name='setup.datatables.info'/>",
				         "sInfoEmpty": "<s:text name='setup.datatables.infoempty'/>",
				         "sEmptyTable": "<s:text name='setup.datatables.emptytable'/>"
				         
				       },
				       "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
							if(aData[3]== "Un-subscribed")
							$(nRow).css({background: "#CD5C5C"});
							
							 $(nRow).children().each(function(index, td) {

		                            if(index == 2)  {

		                                if ($(td).html() === "1") {
		                                    $(td).html("Enabled");
		                                } 
		                                 else {
		                                    $(td).html("Disabled");
		                                }                    


		                            }
		                });                       
							
							
							return nRow;
	    					},
				});
				
				
				
				
				
				
				
			} );
		</script>
<div class="pageTitle">
	<table>
		<tr>
			<td>
				<a class='editlink' href="toAddNotification.action" title="Click to Add New Notification " 
				 popupheight="285" popupwidth="648" popuptitle="<s:text name='setup.not.add' />" ><img src="/imonitor/resources/images/locationadd.png"></a>
			</td> 
			<td>
				<a class='editlink' href="toAddNotification.action" title="Click to Add New Notification " 
				 popupheight="285" popupwidth="648" popuptitle="<s:text name='setup.not.add' />" ><span class='titlespangeneric'><s:text name='setup.not.add' /> </span></a>

			</td> 
		</tr>
	</table>
	<div class="pagetitleline"></div>
</div>
<table class="display" id="listnotificationtable">
	<thead>
		<tr>
			<th><s:text name='setup.datatables.name' /> </th>
			
			<th><s:text name='setup.not.actname' /></th>
			<th>WhatsApp</th>
			<th>SMS Status</th>
			<th><s:text name='setup.datatables.actions' /></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="3"><s:text name='setup.datatables.dataloading' /></td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th><s:text name='setup.datatables.name' /></th>
			
			<th><s:text name='setup.not.actname' /></th>
			<th>WhatsApp</th>
			<th>SMS Status</th>
			<th><s:text name='setup.datatables.actions' /></th>
		</tr>
	</tfoot>
</table>
<span style="display:none;">
<div style="color: red;" id="messageSection">
	<s:property value="#session.message" />
</div></span>
<%ActionContext.getContext().getSession().remove("message"); %>