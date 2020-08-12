/*!
 * Copyright © 2012 iMonitor Solutions India Private Limited
 */
$(document).ready(function() {
	
	// Validation of form starts here.
	$('form').live('change', function() {
		Xpeditions.validateForm($(this));
	});
	$('form').live('keyup', function() {
		Xpeditions.validateForm($(this));
	});
	
	$('.hasblurlistener').live('blur', function() {
		var selectedValue=$(this).val();
		var targeturl=$(this).attr('targeturl');
		var targetid=$(this).attr('targetid');
		var targetSection=$("#"+targetid);
		var targethidden=$(this).attr('targethidden');
		var targethiddensection=$("#"+targethidden);
		$.ajax({
			  url: targeturl,
			  data: {"selectedValue":selectedValue},
			  context: document.body,
			  success: function(data){
				  targetSection.html(data);
				  var hiddenfieldvalue=$("#forhiddenfieldchange").val();
				  targethiddensection.val(hiddenfieldvalue);
			  }
			});
	});
	// Validation of form ends here.
	
	//initMenu();
	$('#menu').xpAccordionMenu({expandFirst: true});
	$('.ajaxlink').xpAjaxLink({target: 'contentsection'});
	$('.ajaxlink').live('mouseover', function(){
		$(this).xpAjaxLink({target: 'contentsection'});
	});
	$('.ajaxinlineform').live('submit', function() {
		var form = $(this).closest("form");
		 //alert($(this).attr('action'));
		 var params = form.serialize();
		 $.post(form.attr('action'), params, function(data){
			 var a= $(data).find("p.message").text();
				var aa = a.split("~");
				if(aa[0] =='Failure')
				{
					$('#editmodal').dialog('close');
					showResultAlert(aa[1]);
					$("#ErrorMessage").html(aa[1]);
				}
				else if(aa[0] == 'Sucess')
				{
					$('#editmodal').dialog('close');
					showResultAlert(aa[1]);
					
					
					
				}
				$('#contentsection').html(data);
			 // If the popup is open, close it.
				$('#editmodal').dialog('close');
		 });
		 return false;
	});
	$('.ajaxinlineformExecuteCommnd').live('submit', function() {
		var form = $(this).closest("form");
		 //alert($(this).attr('action'));
		 var params = form.serialize();
		 $.post(form.attr('action'), params, function(data){
			 var a= $(data).find("p.message").text();
			 	
				var aa = a.split("~");
				if(aa[0] =='Failure')
				{
					//$('#editmodal').dialog('close');
					//showResultAlert(aa[1]);
					$('#commandresult').val(a);
					//$("#ErrorMessage").html(aa[1]);
				}
				else if(aa[0] == 'Sucess')
				{
					//$('#editmodal').dialog('close');
					//showResultAlert(aa[1]);
					
					$('#commandresult').val(aa[2]);
				}
				else{
				$('#contentsection').html(data);
			 // If the popup is open, close it.
				$('#editmodal').dialog('close');
				}
		 });
		 return false;
	});
	
	$('.ajaxinlinepopupform').live('submit', function() {
		var form = $(this).closest("form");
		 //alert($(this).attr('action'));
		 var params = form.serialize();
		 $.post(form.attr('action'), params, function(data){
			var a= $(data).find("p.message").text();
			var aa = a.split("~");
			if(aa[0] =='Failure'){
				showResultAlert(aa[1]);
				}
			else if(aa[0] == 'Sucess')
				{
					$('#editmodal').dialog('close');
					showResultAlert(aa[1]);
				}
				$('#contentsection').html(data);
			 // If the popup is open, close it.
				$('#editmodal').dialog('close');
		 });
		 return false;
	});
	$('.ajaxinlinefileuploadform').die('submit');
	$('.ajaxinlinefileuploadform').live('submit', function() {
		$('#contentsection').append("<img src='../resources/images/loading.gif'/>");
		function handleSuccess(){
			var data = $(this).contents().find('body').html();
			 $('#contentsection').html(data);
		}
		var formId = $(this).attr('id');
		 var frameId = '#jUploadFrame' + formId;
		 $(frameId).remove();
		$.covertToAjaxFileUpload(this,{successhandler:handleSuccess});
		return true;
	});
	
	
	$('.ajaxinlinefileuploadformWithDialog').die('submit');
	$('.ajaxinlinefileuploadformWithDialog').live('submit', function() {
		
		$('#editmodal').html("<img src='../resources/images/loading.gif'/>");
		
		
		function handleSuccess()
		{
			
			var data = $(this).contents().find('body').html();
			 $('#contentsection').html(data);
			 $("#editmodal" ).dialog('destroy');
			 
		}
		var formId = $(this).attr('id');
		 var frameId = '#jUploadFrame' + formId;
		 $(frameId).remove();
		$.covertToAjaxFileUpload(this,{successhandler:handleSuccess});
		return true;
	});
	
	$('.ajaxfileuploadformpopup').live('submit', function() {
		var dataTableId = $(this).attr("datatableid");
		var dataTableUrl = $(this).attr("datatableurl");
		$.covertToAjaxFileUpload(this,{successhandler:editAndReloadTable, dataTableId:dataTableId, dataTableUrl:dataTableUrl});
		return true;
		function editAndReloadTable(event){
			var data = $(this).contents().find('body').html();
			$('#editmodal').dialog('close');	
			$("#message").html(data);
			$("#message").dialog('open');
			$('#' + event.data.dataTableId).dataTable({
				"bProcessing": true,
				"bServerSide": true,
				"bDestroy": true,
				"sScrollY": 300,
				"sScrollX": "100%",
				"sScrollXInner": "110%",
				"sAjaxSource": event.data.dataTableUrl
			});
		 }
		 return false;
	});
	$('.doubleselect').live('change', function(){
		var cSelect = $(this);
		var selectedVal = cSelect.val();
		var targetUrl = cSelect.attr('targeturl');
		var targetId = cSelect.attr('targetid');
		var data = {selectedval:selectedVal};
		$.ajax({
			url: targetUrl,
			dataType: 'json',
			data: data,
			success: handleSuccess
		});
		function handleSuccess(data){
			var options = "";
			for(var key in data){
				options += "<option value=\"" + key + "\">" + data[key] + "</option>";
			}
			$("#" + targetId).html(options);
		}
	});
	
	$('.editlink').live('click', function(){
		var pHeight = 400;
		var pWidth  = 600;
		var targeturl = $(this).attr('href');
		$.ajax({
			  url: targeturl,
			  success: function(data){
				$('#editmodal').html(data);
				 $("#editmodal" ).dialog('destroy');
				 $('#editmodal').dialog({height: pHeight, width: pWidth, modal:true});
			  }
			});
		return false;
	});
	
	
	
	$('.editlinkCmd').live('submit', function(){
		var pHeight = 750;
		var pWidth  = 850;
		var pTitle = "Execute";
		$('#editmodal').dialog('close');
		var form = $(this).closest("form");
		 var params = form.serialize();
		 $.post(form.attr('action'),params, function(data){
			 var a= $(data).find("p.message").text();
				var aa = a.split("~");
				if(aa[0] =='Failure')
				{
					$('#editmodal').dialog('close');
					showResultAlert(aa[1]);
				}
				else
				{
					 $('#editmodal').html(data);
					 $("#editmodal" ).dialog('destroy');
					 $('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true});
				}
			  });
		
		return false;
	});
	
	$('.checkpass').live('click', function(){
		var targeturl = $(this).attr('href');
		var pHeight = 100;
		var pWidth  = 350;
		var pTitle = "Enter PassWord";
		$.ajax({
			  url: targeturl,
			  success: function(data){
				  $('#editmodal').html(data);
				  $("#editmodal" ).dialog('destroy');
				  $('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true});
			  }
			});
		return false;
	});
	
	
	
	$('.deletelink').live('click', function(){
		var targeturl = $(this).attr('href');
		
		$( "#confirm" ).dialog('destroy');
		$( "#confirm" ).html("You really want to delete it !!");
		$( "#confirm" ).dialog({
			stackfix: true,
			modal: true,
			buttons: {
			Ok:function(){
				$(this).dialog('close');
			$.ajax({
				  url: targeturl,
				  success: function(data){
					  $( "#confirm" ).dialog('close');
					$("#contentsection").html(data);
					
				  }
				});
				},
			Cancel:function(){
					$(this).dialog('close');
				}
		}
		});

		return false;
	});
	$('.unregisterlink').live('click', function(){
		var targeturl = $(this).attr('href');
		
		$( "#confirm" ).dialog('destroy');
		$( "#confirm" ).html("Do you really want to un-register the gateway ?");
		$( "#confirm" ).dialog({
			stackfix: true,
			modal: true,
			buttons: {
			Ok:function(){
			$.ajax({
				url: targeturl,
				success: function(data){
				$( "#confirm" ).dialog('close');
			}
			});
		},
		Cancel:function(){
			$(this).dialog('close');
		}
		}
		});
		
		return false;
	});
	
	$('.removenodelink').live('click', function(){
		var targeturl = $(this).attr('href');
		
		$( "#confirm" ).dialog('destroy');
		$( "#confirm" ).html("Do you really want to remove failed nodes from the gateway ?");
		$( "#confirm" ).dialog({
			stackfix: true,
			modal: true,
			buttons: {
			Ok:function(){
			$( "#confirm" ).dialog('close');
			$.ajax({
				url: targeturl,
				success: function(data){
					var a= $(data).find("p.message").text();
					showResultAlert(a);
			}
			});
		},
		Cancel:function(){
			$(this).dialog('close');
		}
		}
		});
		
		return false;
	});
	$('.remoteReboot').live('click', function(){
		var targeturl = $(this).attr('href');
		var gateWayId = $(this).attr('gateWayId');
		$( "#confirm" ).dialog('destroy');
		$( "#confirm" ).html("Are you sure you want to reboot the Master Controller "+$(this).attr('gateWayId')+"?");
		$( "#confirm" ).dialog({
			stackfix: true,
			modal: true,
			buttons: {
			Ok:function(){
			$( "#confirm" ).dialog('close');
			$.ajax({
				url: targeturl,
				success: function(data){
					var a= $(data).find("p.message").text();
					showResultAlert(a);
			}
			});
		},
		Cancel:function(){
			$(this).dialog('close');
		}
		}
		});
		
		return false;
	});
	
	$( "#editmodal" ).dialog({
		height: 350,
		width: 500,
		modal: true,
		autoOpen:false
	});
	
	$(".editdeviceconfig").live('click', function(){
		var pHeight = 400;
		var pWidth  = 900;
		var targeturl = $(this).attr('href');
		$.ajax({
			  url: targeturl,
			  success: function(data){
				$('#editmodal').html(data);
				 $("#editmodal" ).dialog('destroy');
				 $('#editmodal').dialog({height: pHeight, width: pWidth, modal:true});
			  }
			});
		return false;
	});
	
	$('.editlinkDelete').live('submit', function(){
		var pHeight = 250;
		var pWidth  = 450;
		var pTitle = "Delete";
		$('#editmodal').dialog('close');
		var form = $(this).closest("form");
		 var params = form.serialize();
		 $.post(form.attr('action'),params, function(data){
			 var a= $(data).find("p.message").text();
				var aa = a.split("~");
				if(aa[0] =='Failure')
				{
					$('#editmodal').dialog('close');
					showResultAlert(aa[1]);
				}
				else
				{
					 $('#editmodal').html(data);
					 $("#editmodal" ).dialog('destroy');
					 $('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true});
				}
			  });
		
		return false;
	});
	
	
	$('.editlinkReport').live('submit', function(){
		var pHeight = 250;
		var pWidth  = 450;
		var pTitle = "Customer report";
		$('#editmodal').dialog('close');
		var form = $(this).closest("form");
		var params = form.serialize();
		 $.post(form.attr('action'),params, function(data){
			 var a= $(data).find("p.message").text();
				var aa = a.split("~");
				if(aa[0] =='Failure')
				{
					$('#editmodal').dialog('close');
					showResultAlert(aa[1]);
				}
				else
				{
					 $('#editmodal').html(data);
					 $("#editmodal" ).dialog('destroy');
					 $('#editmodal').dialog({height: pHeight, width: pWidth, title: pTitle, modal:true});
				}
			  });
		
		return false;
	});
	
	$('.backuplink').live('click', function(){
		var targeturl = $(this).attr('href');
		
		$( "#confirm" ).dialog('destroy');
		$( "#confirm" ).html("Do you wish to take backup from your gateway??");
		$( "#confirm" ).dialog({
			stackfix: true,
			modal: true,
			buttons: {
			Ok:function(){
				$(this).dialog('close');
			$.ajax({
				  url: targeturl,
				  success: function(data){
					  $( "#confirm" ).dialog('close');
					$("#contentsection").html(data);
					
				  }
				});
				},
			Cancel:function(){
					$(this).dialog('close');
				}
		}
		});

		return false;
	});
	
	
	$('.restorelink').live('click', function(){
		var targeturl = $(this).attr('href');
		
		$( "#confirm" ).dialog('destroy');
		var confirmHtml = "<table><tr><td><input type='radio' name='type' class='bckType' value='0'/></td><td>Full restore</td></tr>"
			              +"<tr><td><input type='radio' name='type' class='bckType' value='1'/></td><td>Partial (Only eeprom data)</td></tr></table>";
		$( "#confirm" ).html(confirmHtml);
		
		$( "#confirm" ).dialog({
			stackfix: true,
			modal: true,
			title:"Select restore method",
			buttons: {
			Ok:function(){
				var value =$("input:radio:checked").val();
				var params = {"restoreType":value};
				$(this).dialog('close');
			$.ajax({
				  url: targeturl,
				  data: params,
				  success: function(data){
					  $( "#confirm" ).dialog('close');
					  $("#contentsection").html(data);
					
				  }
				});
				},
			Cancel:function(){
					$(this).dialog('close');
				}
		}
		});

		return false;
	});
	
	
	$('.ajaxinlinegatewaybackupform').live('submit', function() {
		var form = $(this).closest("form");
		 //alert($(this).attr('action'));
		 var params = form.serialize();
		 $.post(form.attr('action'), params, function(data){
			var a= $(data).find("p.message").text();
			var aa = a.split("~");
			if(aa[0] =='Failure'){
				showResultAlert(aa[1]);
				}
			else if(aa[0] == 'Sucess')
				{
					$('#editmodal').dialog('close');
					showResultAlert(aa[1]);
				}
				$('#contentsection').html(data);
			 // If the popup is open, close it.
				$('#editmodal').dialog('close');
		 });
		 return false;
	});
});


