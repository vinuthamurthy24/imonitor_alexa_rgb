<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<script type="text/javascript" charset="utf-8">

			$(document).ready(function() {
				var publicationgrouptable = $('#listgatewaytable').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"bSort": true,
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "listAskedGateWays.action"
				});
			} );
			function cannotDelete()
			{
				alert("Cannot delete the gateway as it has customer associated with it.");
			}
		</script>
<h4>List Gateways.</h4>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listgatewaytable">
	<thead>
		<tr>
			<th>MacId</th>
			<th>Status</th>
			<th>Agent</th>
			<th>Customer</th>
			<th>Version</th>
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
			<th>MacId</th>
			<th>Status</th>
			<th>Agent</th>
			<th>Customer</th>
			<th>Version</th>
			<th>Action</th>
		</tr>
	</tfoot>
</table>
