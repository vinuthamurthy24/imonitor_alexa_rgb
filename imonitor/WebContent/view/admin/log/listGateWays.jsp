<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				var publicationgrouptable = $('#listgatewaytable').dataTable({

					//"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"aoColumns": [
					 {"bSortable": false },
     			     {"bSortable": false },
      			 	 {"bSortable": false },
					 {"bSortable": false },
					 {"bSortable": false,"bVisible": false},
					 {"bSortable": false },],
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "listAskedGateWaysforlog.action",
					"fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
						if(aData[4]==0)
						$(nRow).css({background: "#6DC3DF"});
						else if(aData[4]==1)
						$(nRow).css({background: "#8Db3DF"});
						else
						$(nRow).css({background: "#3Df3DF"});
						return nRow;
    					},
				});
			} );
			
			setInterval('$("#listgatewaytable").dataTable().fnDraw()',10000);
		</script>

<h4>List Gateways.</h4>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listgatewaytable">
	<thead>
		<tr>
			<th>MacId</th>
			<th>Status</th>
			<th>Agent</th>
			<th>Customer</th>
			<th>MaintenanceMode</th>
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
			<th>MaintenanceMode</th>
			<th>Action</th>
		</tr>
	</tfoot>
</table>
