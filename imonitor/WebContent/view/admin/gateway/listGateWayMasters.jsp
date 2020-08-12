<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				$('#example').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"sAjaxSource": "listAskedGateWayMasters.action"
				});
			} );
		</script>

<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
	<thead>
		<tr>

			<th>Make</th>
			<th>Model</th>
			<th>Description</th>
		</tr>

	</thead>
	<tbody>
		<tr>
			<td colspan="3">Data Loading ...</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th>Make</th>
			<th>Model</th>
			<th>Description</th>
		</tr>
	</tfoot>
</table>
