<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags"  prefix="s"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%>

 <script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				var publicationgrouptable = $('#listdeviceTypetable').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"bSort": true,
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "listAskedDeviceTypes.action"
				});
			} );
		</script> 
<h4>List of DeviceTypes</h4>
 <table cellpadding="0" cellspacing="0" border="0" class="display" id="listdeviceTypetable">
	<thead>
		<tr>
			<th>Device Type</th>
			<th>Basic Device Class</th>
			<th>Generic Device Class</th>
			<th>Specific Device Class</th>
			<th>Supported Classes</th>
			<th>Manufacturer Id</th>
			<th>Product Id</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="3">Data Loading ...</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
		<th>Device Type</th>
			<th>Basic Device Class</th>
			<th>Generic Device Class</th>
			<th>Specific Device Class</th>
			<th>Supported Classes</th>
			<th>Manufacturer Id</th>
			<th>Product Id</th>
		</tr>
	</tfoot>
</table> 
<div style="color: red;algin:center;" id="messageSection">
	<s:property value="#session.message" />
</div>
<%ActionContext.getContext().getSession().remove("message"); %>