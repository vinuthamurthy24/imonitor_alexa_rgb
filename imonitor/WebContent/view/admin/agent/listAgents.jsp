<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%><script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				var publicationgrouptable = $('#listagenttable').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"bSort": true,
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "listAskedAgents.action"
				});
			} );
		</script>
<h4>Listing Agents.</h4>
<div style="color: red;" id="ErrorMessage"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listagenttable">
	<thead>
		<tr>
			<th>Name</th>
			<th>IP Address</th>
			<th>Receiver Port</th>
			<th>C 2 R Port</th>
			<th>C 2 B Port</th>
			<th>Streaming IP</th>
			<th>Streaming Port</th>
			<th>Ftp IP</th>
			<th>Ftp Port</th>
			<th>Status</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody style="text-align: left;">
		<tr>
			<td colspan="3">Data Loading ...</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th>Name</th>
			<th>IP Address</th>
			<th>Receiver Port</th>
			<th>C 2 R Port</th>
			<th>C 2 B Port</th>
			<th>Streaming IP</th>
			<th>Streaming Port</th>
			<th>Ftp IP</th>
			<th>Ftp Port</th>
			<th>Status</th>
			<th>Action</th>
		</tr>
	</tfoot>
</table>
