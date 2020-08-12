<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
					"sAjaxSource": "listAskedDevices.action?device.gateWay.macId=<s:property value="device.gateWay.macId"/>"
				});
			} );
		</script>
		
<h4>List Devices Of .<s:property value="device.gateWay.macId"/></h4>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listgatewaytable">
	<thead>
		<tr>
			<th>Device ID</th>
			<th>Name</th>
			<th>Type</th>
			<th>Location</th>
			<th>Gateway</th>
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
			<th>Device ID</th>
			<th>Name</th>
			<th>Type</th>
			<th>Location</th>
			<th>Gateway</th>
			<th>Action</th>
		</tr>
	</tfoot>
</table>
