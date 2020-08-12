/*!
 * Copyright © 2012 iMonitor Solutions India Private Limited
 */
(function($) {

    var XpAjaxLink = function(element, options){
		//Defaults are below
		
    	var settings = $.extend({}, $.fn.xpAjaxLink.defaults, options);
		
		var vars = {
            thisSectionMayUseLater : 0
        };
		element = $(element);
		element.click(function(){
			var url = $(this).attr('href');
			var targetSection = $('#' + settings.target);
			var overlay = Xpeditions.createOverlaySection (targetSection);
			$.ajax({
				  url: url,
				  context: document.body,
				  success: function(data){
					  targetSection.html(data);
					  overlay.remove();
			  	  }
			});
			return false;
		});
	};
    $.fn.xpAjaxLink = function(options) {
    
        return this.each(function(){
            var element = $(this);
            // Return early if this element already has a plugin instance
            if (element.data('xpAjaxLink')) return;
            // Pass options to plugin constructor
            var xpAjaxLink = new XpAjaxLink(this, options);
            // Store plugin object in this element's data
            element.data('xpAjaxLink', xpAjaxLink);
        });

	};
	
	//Default settings
	$.fn.xpAjaxLink.defaults = {
			target: ""
	};
	
	$.fn._reverse = [].reverse;
	
})(jQuery);
(function($) {
	
	var XpAddImageAfter = function(element, options){
		//Defaults are below
		
		var settings = $.extend({}, $.fn.xpAddImageAfter.defaults, options);
		
		var vars = {
				thisSectionMayUseLater : 0
		};
		
		element = $(element);
		var removeimg = $( document.createElement('img') );
		removeimg.attr("src",settings.removeimgsrc);
		removeimg.addClass(settings.removeimgclass);
		element.addClass('removerowimage');
		removeimg.insertAfter(element);
		
		var img = $( document.createElement('img') );
		img.attr("src",settings.addimgsrc);
		img.addClass(settings.addimgclass);
		element.addClass('newrowimage');
		img.insertAfter(element);
	};
	$.fn.xpAddImageAfter = function(options) {
		
		return this.each(function(){
			var element = $(this);
			// Return early if this element already has a plugin instance
			if (element.data('xpAddImageAfter')) return;
			// Pass options to plugin constructor
			var xpAddImageAfter = new XpAddImageAfter(this, options);
			// Store plugin object in this element's data
			element.data('xpAddImageAfter', xpAddImageAfter);
		});
		
	};
	
	//Default settings
	$.fn.xpAddImageAfter.defaults = {
			target: ""
	};
	
	$.fn._reverse = [].reverse;
	
})(jQuery);

jQuery.covertToAjaxFileUpload = function(cForm, options){

	cForm = $(cForm);
	var iFrame = $(createFileUploadIframe(cForm.attr('id'), "#"));
		iFrame.bind('load', {dataTableId:options.dataTableId, dataTableUrl:options.dataTableUrl},options['successhandler']);
		var iFrameId = iFrame.attr('id');
		cForm.attr("target", iFrameId);
		function createFileUploadIframe(id, uri){
			//create frame
            var frameId = 'jUploadFrame' + id;
            
            if(window.ActiveXObject) {
                var io = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');
                if(typeof uri== 'boolean'){
                    io.src = 'javascript:false';
                }
                else if(typeof uri== 'string'){
                    io.src = uri;
                }
            }
            else {
                var io = document.createElement('iframe');
                io.id = frameId;
                io.name = frameId;
            }
            io.style.position = 'absolute';
            io.style.top = '-1000px';
            io.style.left = '-1000px';

            document.body.appendChild(io);

            return io;		
    }
};
Xpeditions = {};
Xpeditions.createOverlaySection = function(targetSection){
	var height = targetSection.height();
	var width = targetSection.width();
	
	height = height == null ? 600 : height;
	width = width == null ? 600 : width;
	var targetPostion  = targetSection.position();
	var left = 0;
	var top = 0;
	if(targetPostion != undefined){
		left = targetPostion.left;
		top = targetPostion.top;
	}
	var overlaySection = $("<div></div>");
	overlaySection.height(height).width(width).css( { 
		position: 'absolute',
		'text-align': 'center',
		'vertical-align': 'center',
		'margin-top': height/2,
		zIndex: 3,
		left: left, 
		top: top
	} ).html("<img src='../resources/images/loading.gif'/>");
	$(document.body).append(overlaySection);
	return overlaySection;
};

Xpeditions.validateField = function(input){
	var cValue = input.val();
	if(input.hasClass('required')){
		if($.trim(cValue) == ""){
			return formatMessage(" ");
		}
	}
	
	if(input.hasClass('requiredselect')){
		if($.trim(cValue) == "0"){
			return formatMessage(" ");
		}
	}
	
//bhavya changed on 11th march 2013	for fix bug 974
	if(input.hasClass('requiredWithoutWhiteSpace')){
		if(cValue == ""){
			return formatMessage(" ");
		}
		else
		if($.trim(cValue)!=cValue)
		{
		return formatMessage("msg.ui.general.0001a");
		}
	}   
//bhavya end changed on 11th march 2013	for fix bug 974
	
	if(input.hasClass('alphabets')){
		if($.trim(cValue) != ""){
			var ipValue = $.trim(cValue);
			var alphaNumPattern = new RegExp(/^[A-Za-z]+$/);
			if(!alphaNumPattern.test(ipValue)){
				return formatMessage("msg.ui.general.0002");
			}
		}
	}
	//sumit start: validate alphanumeric entry.
	if(input.hasClass('alphanumeric')){
		if($.trim(cValue) != ""){
			var ipValue = $.trim(cValue);
			var alphaNumPattern = new RegExp(/^[A-Za-z\d\s]+$/);
			if(!alphaNumPattern.test(ipValue)){
				return formatMessage("msg.ui.general.0002a");
			}
		}
	}
	
	if(input.hasClass('password')){
		if($.trim(cValue) != ""){
			var ipValue = $.trim(cValue);
			var alphaNumPattern = new RegExp(/^[A-Za-z\d\s.@()\[\]?:{}*&~`$'"+-\/\\|!.,#%!_^]+$/);
			if(!alphaNumPattern.test(ipValue)){
				return formatMessage("msg.ui.general.0002b");
			}
		}
	} 
	
	if(input.hasClass('alphanumericWithDot')){
		if($.trim(cValue) != ""){
			var ipValue = $.trim(cValue);
			if((ipValue.length > 5) || (ipValue.length < 5)){
		       return formatMessage("msg.ui.general.0003b");
		         }
				 else if(ipValue=5)
				 {
			var alphaNumPattern = new RegExp(/^[A-Za-z\d\s.]+$/);
			if(!alphaNumPattern.test(ipValue)){
				return formatMessage("msg.ui.general.0002");
			}
			}
	}
}
	
	if(input.hasClass('alphanumericWithDott')){
		if($.trim(cValue) != ""){
			var ipValue = $.trim(cValue);
			var splits = ipValue.split(".");
			if(splits.length < 4){
				return formatMessage("msg.ui.general.0003a");
			}
			var  numberPattern = new RegExp("^([1-9]|[1-9][0-9]|[a-z][A-Z]|[A-Z][a-z]|[a-z][0-9]|[0-9][a-z]|[A-Z][0-9]|[0-9][A-Z])(\.([0-9]|[1-9][0-9]|[a-z][A-Z]|[A-Z][a-z]|[a-z][0-9]|[0-9][a-z]|[A-Z][0-9]|[0-9][A-Z])){3}$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0003a");
			}
		}
	}
	
	if(input.hasClass('utf8alphabets')){
		if($.trim(cValue) != ""){
			var ipValue = $.trim(cValue);
			var alphaNumPattern = new RegExp(/^[A-Za-zಂ-ೲ]+$/);
			if(!alphaNumPattern.test(ipValue)){
				return formatMessage("msg.ui.general.0002");
			}
		}
	}
	//sumit start: validate alphanumeric entry.
	if(input.hasClass('utf8alphanumeric')){
		if($.trim(cValue) != ""){
			var ipValue = $.trim(cValue);
			var alphaNumPattern = new RegExp(/^[A-Za-zಂ-ೲ\d\s]+$/);
			if(!alphaNumPattern.test(ipValue)){
				return formatMessage("msg.ui.general.0002");
			}
		}
	}
	if(input.hasClass('utf8alphanumericWithDot')){
		if($.trim(cValue) != ""){
			var ipValue = $.trim(cValue);
			var alphaNumPattern = new RegExp(/^[A-Za-zಂ-ೲ\d\s.]+$/);
			if(!alphaNumPattern.test(ipValue)){
				return formatMessage("msg.ui.general.0002");
			}
		}
	}
	
	//sumit end: validate alphanumeric entry.
	if(input.hasClass('ipaddress')){
		if($.trim(cValue) != ""){
			var ipValue = $.trim(cValue);
			var splits = ipValue.split(".");
			if(splits.length < 4){
				return formatMessage("msg.ui.general.0003");
			}
			var  numberPattern = new RegExp("^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0003");
			}
		}
	}
	if(input.hasClass('macid')){
		if($.trim(cValue) != ""){
			var  numberPattern = new RegExp("^([0-9A-Fa-f]{2}[:]){5}[0-9A-Fa-f]{2}$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0016");
			}
		}
	}
	if(input.hasClass('makenumber')){
		if($.trim(cValue) != ""){
			var  numberPattern = new RegExp("^[0-999]+$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0004a");
			}else{ if(cValue < 0 || cValue > 999){
				return formatMessage("msg.ui.general.0004a");
			}
			}
		}
	}
	if(input.hasClass('number')){
		if($.trim(cValue) != ""){
			var  numberPattern = new RegExp("^[0-9]+$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0005");
			}
			var minValue = input.attr('minvalue');
			if(minValue != undefined){
				minValue = 1 * minValue;
				cNumber = 1 * cValue;
				if(cNumber < minValue){
					return formatMessage("msg.ui.general.0006") + minValue;
				}
			}
			var maxValue = input.attr('maxvalue');
			if(maxValue != undefined){
				maxValue = 1 * maxValue;
				cNumber = 1 * cValue;
				if(cNumber > maxValue){
					return formatMessage("msg.ui.general.0007") + maxValue;
				}
			}
		}
	}
	if(input.hasClass('double')){
		if($.trim(cValue) != ""){
			var  doublePattern = new RegExp("^[0-9]*[\\.]?[0-9]+$","gi");
			if(!doublePattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0008");
			}
		}
	}
	if(input.hasClass('phonenumber')){
		if($.trim(cValue) != ""){
			var  phoneNumberPattern = new RegExp("^[\\+]{0,1}[0-9]{6,12}$","gi");
			if(!phoneNumberPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0009");
			} 
		}
	}
	if(input.hasClass('mobilenumber')){
		if($.trim(cValue) != ""){
			var  mobileNumberPattern = new RegExp("^([\\+][9][1]){0,1}[0-9]{10}$","gi");
			if(!mobileNumberPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0010");
			} 
		}
	}
	if(input.hasClass('usercode')){
		if($.trim(cValue) != ""){
			var  mobileNumberPattern = new RegExp('^[0-9]{6}$');
			if(!mobileNumberPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.doorlock.error");
			} 
		}
	}
	
	if(input.hasClass('domainName')){
		if($.trim(cValue) != ""){
			var ipValue = $.trim(cValue);
			var splits = ipValue.split(".");
			var  numberPattern = new RegExp(/[^w{3}\.]([a-zA-Z0-9]([a-zA-Z0-9\-]{0,65}[a-zA-Z0-9])?\.)+[a-zA-Z]{2,6}/igm);
			if(!numberPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0003b");
			}
		}
	}

	
	if(input.hasClass('memory')){   //pari added for Paid storage
		if($.trim(cValue) != ""){
			var  numberPattern = new RegExp("^[0-999]+$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0004");
			}else{ if(cValue < 0 || cValue > 999){
				return formatMessage("msg.ui.general.0004");
			}
			}
		}
	}
	if(input.hasClass('email')){
		if($.trim(cValue) != ""){
			var  emailPattern = new RegExp(/^[+a-zA-Z0-9._-]+@([a-zA-Z0-9.-]{2,100})+\.[a-zA-Z]{2,4}$/i);
			if(!emailPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0011");
			} 
		}
	}
	if(input.hasClass('valuerange')){
		if($.trim(cValue) != ""){
			var  numberPattern = new RegExp("^([0-9]{1,2})$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return formatMessage("msg.ui.general.0012");
			}
		}
	}
	//sumit start: Strong Password
	if(input.hasClass('crosscheck')){
		if($.trim(cValue) != ""){
			var nowValue = $.trim(cValue);
			
			var otherInputs = $("." + input.attr('commonclass'));
			var fieldLength = true;
			//var passwordPattern = new RegExp(/(?=[^A-Z]*[A-Z])(?=[^0-9]*[0-9])(?=[^!@#\$%]*[!@#\$%]).{6,12}$/);
			var passwordPattern = new RegExp(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s)(?=[^!@#\$%]*[!@#\$%]).{6,12}$/);
			var validPass = true;
			var isSame = true;
			otherInputs.each(function(){
				//Must contain a minimum of 6 characters.
				if(nowValue.length < 6){
					fieldLength = false;
				}
				//Must contain atleat 1-alphabet, 1-specialCharacter, 1-numeral
			//	if(!passwordPattern.test(nowValue)){
			//		validPass = false;
			//	}				
				if(nowValue != $(this).val()){
					isSame = false;
				}
			});
			if(!fieldLength){
				return formatMessage("msg.ui.general.0013");
			}
			if(!validPass){
				return formatMessage("msg.ui.general.0014");
			}
			if(!isSame){
				return formatMessage("msg.ui.general.0015");
			}
		}
	}
	//sumit end
	return true;
};

//sumit start
Xpeditions.validateForm = function(cForm){
	var isEveryThingOk = Xpeditions.validateFormInternal(cForm);

	if(!isEveryThingOk){
		cForm.find('input[type=submit]').attr('disabled', 'disabled');
	}else{
		cForm.find('input[type=submit]').removeAttr('disabled');
	}
	return isEveryThingOk;
};


Xpeditions.validateElement = function(cForm){
	var isEveryThingOk = Xpeditions.validateFormInternal(cForm);

	if(!isEveryThingOk){
		cForm.find('.navigate').attr('disabled', 'disabled');
	}else{
		cForm.find('.navigate').removeAttr('disabled');
	}
	return isEveryThingOk;
};

Xpeditions.validateFormInternal = function(cForm){
	var inputs = cForm.find('input');
	$.merge(inputs, cForm.find('select'));
	$.merge(inputs, cForm.find('textarea'));
	var isEveryThingOk = true;
	inputs.each(function(index, inp){
		var input = $(inp);
		var resultOfValidation = Xpeditions.validateField(input);
		
		
		if(input.hasClass('displayStar'))
		{
			if(!(input.hasClass('starclass')))
			{
				input.addClass('starclass');
				input.closest("tr").find("td:first-child").append("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: right;margin: -21px 180px 0px -9px;'>*</span>");
			}
			
		}
		if(input.hasClass('editdisplayStar'))
		{
			
			if(!(input.hasClass('starclass')))
			{
				input.addClass('starclass');
				input.closest("tr").find("td:first-child").append("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: -7px -9px 0px -3px;'>*</span>");
			}
			
		}
		if(input.hasClass('editdisplayStaradmin'))
		{
			
			if(!(input.hasClass('starclass')))
			{
				input.addClass('starclass');
				input.closest("tr").find("td:first-child").append("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 21px;float: left;margin: -10px -12px 0px -5px;'>*</span>");
			}
			
		}
		if(input.hasClass('displayStarHeader'))
		{
			if(!(input.hasClass('starclass')))
			{
				input.addClass('starclass');
				input.parent().append("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin:-3px -21px 0px -6px;'>*</span>");
			}
			
		}
		if(input.hasClass('displayStarSelect'))
		{
			if(!(input.hasClass('starclass')))
			{
				input.addClass('starclass');
				input.parent().append("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: 0px 0px -38px -4px;'>*</span>");
			}
			
		}
		
		
		
		
		if(resultOfValidation != true){
			var errorSpan = input.nextAll('.errormessageclass');
			if(errorSpan.length < 1){
//				$("<span class='errormessageclass'></span>").insertAfter(input);
				input.parent().append("<span class='errormessageclass'></span>");
				errorSpan = input.nextAll('.errormessageclass');
			}
			input.addClass('errorclass');
			errorSpan.show();
			errorSpan.html(resultOfValidation);
			isEveryThingOk = false;
		} else {
			input.removeClass('errorclass');
			input.nextAll('.errormessageclass').hide();
		}
	});
	return isEveryThingOk;
};


//sumit end

$(document).ready(function() {
	$(".duplicatemyrow").live('click', function(){
		var parentRow = $(this).parent().parent();
		var clonedRow = parentRow.clone();
		clonedRow.insertAfter(parentRow);
	});
	
	$(".removemyrow").live('click', function(){
		if($('.removemyrow').size() < 2){
			return false;
		}
		var parentRow = $(this).parent().parent();
		parentRow.remove();
	});
	//Hover style for Menu Stars
	$(".ulmenu li").bind('mouseover', function(){
		$(this).addClass($(this).parent().attr('lihoverclass'));
	});
	$(".ulmenu li").bind('mouseout', function(){
		$(this).removeClass($(this).parent().attr('lihoverclass'));
	});
	$(".ulmenu li, .ulmenu li a").bind('click', function(){
		if($(this).is('a')){
			var clickClass = $(this).parent().attr('liclickedstyle');
			clickClass = $(this).parent().parent().attr('liclickedstyle');
			var nowLi = $(this).parent();
			if(clickClass != undefined){
				$(".ulmenu li").removeClass(clickClass);
				nowLi.addClass(clickClass);
			}
		}else {
			$(this).find('a').click();
		}
	});
	
/*	
	$('.removedrowbybutton').live('click', function(){
		var selectboxes =$(this).parent().parent();
		var selects = selectboxes.find('select');
		var dSelect = $(selects.get(0));
		var selectboxvalue =dSelect.val();
		var confirMessage="";
					if(selectboxvalue ==""){
						confirMessage = "There is no selected device in this row.";
					}
					if(confirMessage == ""){
						confirMessage = "Are you sure you want to remove this row ?";
					}
		$("#confirm").dialog("destroy");
		$("#confirm").html(confirMessage);
		$("#confirm").dialog({
			stackfix: true,
			modal: true,
			buttons: {
				Ok: function() {
					$(this).dialog('close');
					$("#confirm").dialog("destroy");
					var cRow = $(this).parent().parent();
					var className = cRow.attr("class");
					//$input = $('#mainTable tbody>tr:last').closest("tr").find("td > input:text");
				//	var selectboxes =$(this).parent().parent();
					var selects = selectboxes.find('select');
					var dSelect = $(selects.get(0));
					var selectboxvalue =dSelect.val();
					if(selectboxvalue ==""){
						return false;
					}
					if(selectboxvalue !=""){
						//var parentRow = $(this).parent().parent();
						selectboxes.remove();
					}
				},
				Cancel: function() {
					$(this).dialog('close');
					$("#confirm").dialog("destroy");
				}
					
			}
		
		});
		return false;
	});*/
	//remove enter in descriptions
	$(".Details").live('change', function(){
		var textArea = $('#Details').val().replace(/\r\n|\r|\n/g," ");
		$('#Details').val(textArea);
	});
	//Hover style for Menu Ends
	$('.ajaxchangelistener').live('blur', function(){
		var curField = $(this);
		var curVal = curField.val();
		var targetUrl = curField.attr('validateurl');
		var nameOfFieldName = curField.attr('name');
		var params = {nameOfFieldName: curVal};
	});
});
function showResultAlert(message) {
	$("#confirm").dialog("destroy");
	 $("#confirm").html(message);
		$("#confirm").dialog('open');
		$("#confirm" ).dialog({
			stackfix: true,
			modal: true,
			buttons: {
			Ok:function(){
				$(this).dialog('close');
				$("#confirm").html("");
				$("#confirm").dialog("destroy");
			}
		}
		});
	}
function showResultAlertOnHome(message) {
	$("#confirm").dialog("destroy");
	 $("#confirm").html(message);
		$("#confirm").dialog('open');
		$("#confirm" ).dialog({
			stackfix: true,
			position: [350,100],
			modal: true,
			buttons: {
			Ok:function(){
				$(this).dialog('close');
				$("#confirm").html("");
				$("#confirm").dialog("destroy");
			}
		}
		});
	}
// Added by Naveen
//To remove enter in customerreports
$(".resolutionDetails").live('change', function(){
	var textArea = $('#resolutionDetails').val().replace(/\r\n|\r|\n/g," ");
	$('#resolutionDetails').val(textArea);
});

$(".actionDetails").live('change', function(){
	var textArea = $('#actionDetails').val().replace(/\r\n|\r|\n/g," ");
	$('#actionDetails').val(textArea);
});


function showUserTipsOnHome(message) {
	$("#confirm").dialog("destroy");
	 $("#confirm").html("<img src='../resources/images/tipsalert.jpg' style='width:131px;'/><br><br>Dear Customer,<br><br>"+message+
			           "<br><br><table><tr><td><input type='checkbox'  id='checkTip' /></td><td><p>Don't show this again</p></td></tr>");
		$("#confirm").dialog('open');
		$("#confirm" ).dialog({
			stackfix: true,
			position: [500,100],
			modal: true,
			height:400,
			width:623,
			title:'Alert!!!!!!',
			   show: {
			        effect: "slide",
			        duration: 1000
			      },
			      hide: {
			        effect: "explode",
			        duration: 1000
			      },
			buttons: {
			Ok:function(){
				
				if($("#checkTip").attr("checked")){
					var url = "updateShowTipsForUser.action";
					$.ajax({
						url:url,
						success: function(data){
							
						}
					});
				}
				$(this).dialog('close');
				$("#confirm").html("");
				$("#confirm").dialog("destroy");
			}
		}
		});
	}
