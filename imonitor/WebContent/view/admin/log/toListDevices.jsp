<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				var publicationgrouptable = $('#listlogtable').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"bSort": true,
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "listLogs.action?device.gateWay.macId=<s:property value="device.gateWay.macId"/>"
				});
			} );
		</script>
		
<h4>List Logs Of .<s:property value="device.gateWay.macId"/></h4>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listlogtable">
	<thead>
		<tr>
			<th>FileName</th>
			<th>FilePath</th>
			<th>UploadedDate</th>
			<th>GateWay</th>
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
			<th>FileName</th>
			<th>FilePath</th>
			<th>UploadedDate</th>
			<th>GateWay</th>
			<th>Action</th>
		</tr>
	</tfoot>
</table>
