<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
<%
String applicationName = request.getContextPath();
%>
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
	
<script>
var userDetails = new UserDetails();
$(document).ready(function() {

	
	var publicationgrouptable = $('#listlocationstable').dataTable({
		"bProcessing": true,
		"bServerSide": true,
		"bDestroy": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"sAjaxSource": "listAskedLocations.action",
		"oLanguage": {
	         "sSearch": "<s:text name='setup.datatables.search' />",
	         "sProcessing": "<s:text name='setup.datatables.processing' />",
	         "sLengthMenu": "<s:text name='setup.datatables.showentries' />",
	         "sInfo": "<s:text name='setup.datatables.info'/>",
	         "sInfoEmpty": "<s:text name='setup.datatables.infoempty'/>",
	         "sEmptyTable": "<s:text name='setup.datatables.emptytable'/>"
	       }
	});
});
</script>
<div class="pageTitle">
<!--	<table>
		<tr>
			<td>
				<a class='editlink locationadd' href="toAddLocation.action"  popupheight="450" popupwidth="850" popuptitle="Add Room " 
				title="Add a new a room"><img src="/imonitor/resources/images/locationadd.png"></a>
			</td> 
			<td>
				<a class='editlink locationadd' href="toAddLocation.action"  popupheight="450" popupwidth="850" popuptitle="Add Room " 
				title="Add a new a room"><span class='titlespangeneric'>Add Room</span></a>			
			</td> 
		</tr>
	</table>			
	-->
		<table>
		<tr>
			<td>
				<a class='editroomlink' href="toAddLocation.action"  popupheight="450" popupwidth="850" popuptitle='<s:text name="setup.rooms.add" />'
				title="Add a new a room"><img src="/imonitor/resources/images/locationadd.png"></a>
			</td> 
			<td>
				<a class='editroomlink' href="toAddLocation.action"  popupheight="450" popupwidth="850" popuptitle='<s:text name="setup.rooms.add" />' 
				title="Add a new a room"><span class='titlespangeneric'><s:text name="setup.rooms.add" /></span></a>	

			</td> 
		</tr>
	</table>
	<div class="pagetitleline"></div>
</div>
<table class="display" id="listlocationstable">
	<thead>
		<tr>
			<th><s:text name="setup.datatables.name" /> </th>
			<th><s:text name="setup.datatables.description" /></th>
			<th><s:text name="setup.datatables.actions" /></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="3"><s:text name="setup.datatables.dataloading" /></td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th><s:text name="setup.datatables.name" /> </th>
			<th><s:text name="setup.datatables.description" /></th>
			<th><s:text name="setup.datatables.actions" /></th>
		</tr>
	</tfoot>
</table>

