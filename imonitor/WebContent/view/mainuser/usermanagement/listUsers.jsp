<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: blue;display:none"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<style>
	.dataTables_length {
	width: 30%;
	float: left;
	margin-left: -75px;
	margin-top: 12px;
	}

	.dataTables_info {
	width: 60%;
	float: left;
	margin-top: 10px;
	margin-left: -220px;
}
	
</style>
<script type="text/javascript" charset="utf-8">

	$(document).ready(function() {
		var publicationgrouptable = $('#listusertable').dataTable( {
			"bProcessing" : true,
			"bServerSide" : true,
			"bDestroy" : true,
			"bSort": true,
			"sScrollY" : 300,
			"sScrollX" : "100%",
			"sScrollXInner" : "100%",
			"sAjaxSource" : "listAskedUsers.action",
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
	<table>
		<tr>
			<td>
				<a class='editroomlink' href="toAddUser.action"  popupheight="350" popupwidth="550" popuptitle="<s:text name='setup.user.add' />"
						title="Click to Add New User"><img src="/imonitor/resources/images/useradd.png"></a>			
			</td> 
			<td>
				<a class='editroomlink' href="toAddUser.action"  popupheight="350" popupwidth="550" popuptitle="<s:text name='setup.user.add' />"
						title="Click to Add New User"> <span class='titlespangeneric'>&nbsp;<s:text name='setup.user.add' /></span></a>			
			</td> 
		</tr>
	</table>


<div class="pagetitleline"></div>
</div>
<table class="display"
	id="listusertable">
	<thead>
		<tr>
			<th><s:text name='setup.user.id' /></th>
			<th><s:text name='setup.user.status' /></th>
			<th><s:text name='setup.user.last.login' /></th>
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
			<th><s:text name='setup.user.id' /></th>
			<th><s:text name='setup.user.status' /></th>
			<th><s:text name='setup.user.last.login' /></th>
			<th><s:text name="setup.datatables.actions" /></th>
		</tr>
	</tfoot>
</table>
