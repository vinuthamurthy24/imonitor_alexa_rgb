<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@page import="com.opensymphony.xwork2.ActionContext"%><html>

<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				var publicationgrouptable = $('#listagenttable').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"bSort": true,
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "listAskedFirmWares.action",
					
						"aoColumns": [
		                       { "sWidth": "6%" ,"sClass": "center", "bSortable": true},
				               { "sWidth": "5%" ,"sClass": "center", "bSortable": false},
				               { "sWidth": "6%", "sClass": "center", "bSortable": true },
				               { "sWidth": "2%", "sClass": "center", "bSortable": true }, 
				              
				             

				             
				            ],
				});
			} );
		</script>

<% String msg = (String)ActionContext.getContext().getSession().remove("message"); %>
<div style="color: blue;"><p class="message"><s:property value="#session.message" /><%= (msg==null?"":msg) %></p></div>

<h4>Listing FirmWare.</h4>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listagenttable">
	<thead>
		<tr>
			<th>Version</th>
			<th>File</th>
			<th>Description</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="3">Data Loading ...</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th>Version</th>
			<th>File</th>
			<th>Description</th>
			<th>Actions</th>
		</tr>
	</tfoot>
</table>
