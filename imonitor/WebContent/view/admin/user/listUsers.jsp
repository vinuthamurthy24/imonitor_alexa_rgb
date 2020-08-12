<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				var publicationgrouptable = $('#listusertable').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"bSort": true,
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "listAskedUsersFromAdmin.action"
						
				});
			} );
		</script>
<h4>List User.</h4>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listusertable">
	<thead>
		<tr>
			<th>UserId</th>
			<th>CutomerId</th>
			<th>Status</th>
			<th>Role</th>
			<th>Action</th>
			
			
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="3">Data Loading ...</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th>UserId</th>
			<th>CutomerId</th>
			<th>Status</th>
			 <th>Role</th>
			<th>Action</th>
			
		</tr>
	</tfoot>
</table>
