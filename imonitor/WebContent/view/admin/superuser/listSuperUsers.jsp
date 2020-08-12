<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.opensymphony.xwork2.ActionContext"%><div style="color: blue">
	<s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				var publicationgrouptable = $('#listsuperUsertable').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"bSort": true,
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "/imonitor/superuser/listAskedSuperUsers.action",
					
					"aoColumns": [
						           { "bSortable": false },
						           { "bSortable": false }
						            ], 
				});
			} );
		</script>
<h4>List SuperUsers.</h4>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listsuperUsertable">
	<thead>
		<tr>
			<th>SuperUserName</th>
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
			<th>SuperUserName</th>
			<th>Action</th>
		</tr>
	</tfoot>
</table>
