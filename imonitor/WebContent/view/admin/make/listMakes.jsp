<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags"  prefix="s"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>

<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				var publicationgrouptable = $('#listmakestable').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"bSort": true,
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "listAskedMakes.action"
				});
			} );
		</script>
<h4>List Makes.</h4>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listmakestable">
	<thead>
		<tr>
			<th>Name</th>
			<th>Number</th>
			<th>Details</th>
			<th>Device Type</th>
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
			<th>Name</th>
			<th>Number</th>
			<th>Details</th>
			<th>Device Type</th>
			<th>Action</th>
		</tr>
	</tfoot>
</table>
<div style="color: red;algin:center;" id="messageSection">
	<s:property value="#session.message" />
</div>
<%ActionContext.getContext().getSession().remove("message"); %>