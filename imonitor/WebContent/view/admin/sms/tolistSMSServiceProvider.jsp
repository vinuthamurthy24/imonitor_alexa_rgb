<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<script type="text/javascript" charset="utf-8">
			var publicationgrouptable="";
			 $(document).ready(function() 
			{
				publicationgrouptable = $('#listSmsSevicetable').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"bSort": true,
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "listAskedSMSServices.action",
					"aoColumns": [
					 {"bSortable": true ,"bVisible": false},
     			     {"bSortable": false },
      			 	 {"bSortable": false },
					 {"bSortable": false,"bVisible": false},
					 {"bSortable": false },],
					
					"fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
						if(aData[3]==0)
						$(nRow).removeClass('row_selected');
						else
						$(nRow).addClass('row_selected');

						return nRow;
    					},
				});


				
									
					$('#listSmsSevicetable').delegate('.SelectAll','change',function() 
					{
					
					
					var url = "UpdateServiceStatus.action";
					var Id = $(this).val();
					var Status="";
					
     					if(this.checked)
						{
     					
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
     					
     					
     			
     				}
					else
					{
     					
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
						
			} );
			 

			 
		</script>
		

<div style="color: green;"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<table>
		<tr>
			<td id="AddServiceProvider" >
				<a class='editlink' href="toAddSMSServiceProvider.action" popupheight="350" popupwidth="950" popuptitle="Please fill form" 
				title="Add new Service Provider"><img src="/imonitor/resources/images/locationadd.png"></a>	</td>
				<td><a class='editlink' href="toAddSMSServiceProvider.action" popupheight="350" popupwidth="950" popuptitle="Please fill form" 
				title="Add new Service Provider" style="border: none;  text-decoration: none;  text-align: left;  font-family: Verdana,Arial,Helvetica,sans-serif;  font-size: 16px;  font-weight: bold;  color: #0099ff;  position: relative;
				">
				<span>SMS Service</span></a>
				</td>

		

		</tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listSmsSevicetable">
	<thead>
		<tr>
			<th>Id</th>
			<th>OperatorCode</th>
			<th>Status</th>
			<th>Status</th>
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
			<th>Id</th>
			<th>OperatorCode</th>
			<th>Status</th>
			<th>Status</th>
			<th>Action</th>
		</tr>
	</tfoot>
</table>
<div id="confirm"></div>