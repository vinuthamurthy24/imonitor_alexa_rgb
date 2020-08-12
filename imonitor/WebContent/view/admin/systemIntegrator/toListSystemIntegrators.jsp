<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var publicationgrouptable="";
$(document).ready(function() {
	publicationgrouptable = $('#listSystemIntegratortable').dataTable({
		"bProcessing": true,
		"bServerSide": true,
		"bDestroy": true,
		"bSort": true,
		"sScrollY": 300,
		"sScrollX": "100%",
		"sScrollXInner": "100%",
		"aoColumns": [
						 {"bSortable": true ,"bVisible": false},
	     			     {"bSortable": false },
	      			 	 {"bSortable": false },
						 {"bSortable": false },
					],
		"sAjaxSource": "listAskedSystemIntegrator.action", //TBD listAskedSystemIntegrators as sAjaxSource
	});
	
	$('#listSystemIntegratortable').delegate('.SelectAll','change',function() {
		var url = "UpdateServiceStatus.action";
		var Id = $(this).val();
		var Status="";

		if(this.checked){
			$("#confirm").html("Do wants Enable This Service Provider?");
			$("#confirm").dialog({
				stackfix: true,
				modal: true,
				buttons: {
					Ok: function() {
						$(this).dialog("destroy");
						Status="1";
     					var params = {"smsService.id":Id, "smsService.status":Status};
     					$.ajax({
     						url:url,
     						dataType:'xml',
     						data:params,
     						success:function(){
     							publicationgrouptable.fnDraw();
     						}
   						});
					},
					Cancel: function() {
						$(this).dialog("destroy");
						publicationgrouptable.fnDraw();
					}
				}
  			});
		}else{
			$("#confirm").html("Do wants Disable This Service Provider?");
			$("#confirm").dialog({
				stackfix: true,
				modal: true,
				buttons: {
					Ok: function() {
						$(this).dialog("destroy");
						Status="0";
     					var params = {"smsService.id":Id, "smsService.status":Status};
	     				$.ajax({
   							url:url,
   							dataType:'xml',
   							data:params,
   							success:function(){
   								publicationgrouptable.fnDraw();
    						}
     					});
					},
					Cancel: function() {
						$(this).dialog("destroy");
						publicationgrouptable.fnDraw();
					}
				}
			});
  		} 
  	});			
});
 

 
</script>
 

<table cellpadding="0" cellspacing="0" border="0" class="display" id="listSystemIntegratortable">
	<thead>
		<tr>
			<th>ID</th>
			<th>Integrator Id</th>
			<th>Name</th>
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
			<th>ID</th>
			<th>Integrator Id</th>
			<th>Name</th>
			<th>Action</th>
		</tr>
	</tfoot>
</table>
<%@page import="com.opensymphony.xwork2.ActionContext"%><div align="center" class="make">
		<div style="color: blue;">
		<s:property value="#session.message"/>
		</div>
<%ActionContext.getContext().getSession().remove("message");%>