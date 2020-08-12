<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>

<s:form action="updateAVBlasterModelNumber.action" method="post" cssClass="ajaxinlinepopupform" id="configureBlasterModelNumberform">
	<s:hidden name="device.generatedDeviceId" id="genDeviceId"></s:hidden>
	<s:hidden name="device.gateWay.macId" id="gwMacID"></s:hidden>
	<s:hidden name="device.modelNumber" id="modelnumber"></s:hidden>
	<s:hidden name="device.batteryStatus" id="batterylevel"></s:hidden>
		
	
	<s:hidden name="stringOfAVBrands" id="stringOfAVBrands" value="<s:property value='#session.stringOfAVBrands' />"></s:hidden>
	<s:hidden name="avCategorySelect" id="avCategorySelect"></s:hidden>
	<s:hidden name="avBrandSelect" id="avBrandSelect"></s:hidden>
	<s:hidden name="avCodeSelect" id="avCodeSelect"></s:hidden>
	<input type=hidden  id="tostopsearch" value="0"/>
	<table>
	<tr>
	<td><input type="checkbox" class="Auto" value="Auto Search" />Auto Search
	<td><input type="checkbox" class="Learn" value="Leaan" />Learn</td>
	</tr>
	</table>
		
			
			
				<div id="CategorySelect">
				<label>Select Av Category:</label><span style="color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: 1px -9px 0px -3px;"> *</span>
					<s:select id="avCategory" list="avCategoryList" listKey="id" listValue="name" headerKey="-1" 
							  headerValue="Select Category" onchange='categoryy()' cssClass="required"  >
					</s:select>
				</div>
			
		
		<table>
		<tr id="BrandSection">
			<td>
				<label>Select AV Brand : </label><span style="color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: -22px -9px 0px -3px;"> *</span>
			</td>
			<td>
				<div id="BrandSelect">
					<select id="avBrandSelectBox" class="required">
						<option value='-1'>Select Brand</option>
					</select>
				</div>
			</td>
		</tr>
		<tr id="codesection">
			<td>
				<label>Select AV Code : </label><span style="color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: -22px -9px 0px -3px;"> *</span>

			</td>
			<td>
				<div id="CodeSelect">
					<select id="avCodeSelectBox" class="required">
						<option value='-1'>Select Code</option>
					</select>
				</div>
			</td>
		</tr>
	
	</table>
	<div id="autoCategorySelect">
		<s:select id="AutoavCategory" onchange='avAutocatselect()' list="avCategoryList" listKey="id" listValue="name" headerKey="-1" 
							  headerValue="Select Category" cssClass="required editdisplayStar" label="AvCategory">
					</s:select>
	</div>
	<div id="Autobrandsection">
	<label >Select AV Brand : </label><span style="color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: -22px -9px 0px -3px;"> *</span>
	<select id="AutoavBrandSelectBox" class="required">
						<option value='-1'>Select Brand</option>
					</select>
	
	</div>
	
	<div id="currentcode" >
	<label>Current model number </label>
	<s:textfield name="" id="current" size="5" key="" ></s:textfield>
	</div>
	
	<div id="previouscode" style="margin:-27px 5px 13px 228px";>
	<label>Previous model number </label>
	<s:textfield name="" id="previous" size="5" key=""></s:textfield>
	</div>
	<div id="loading" style="margin:0px 0px 0px 213px";></div>
	<button name="Search" value="Search" id="searchbutton" onClick='searchcode()' type='button' title='click to search code for selected brand'><s:text name="Search" /></button>
	<button name="Stop" value="Stop" id="stopbutton" onClick='stopSearching()' type='button' title='click to stop searching code'><s:text name="Stop" /></button>
	
	<s:submit key="general.save" id="saveBlasterModel"></s:submit>
</s:form>

<script>
    var selectedBrand = "";
    var selectedCode = "";
	var cForm = $("#configureBlasterModelNumberform");
	Xpeditions.validateForm(cForm);
	var brandcodes = new Array(); 
	 var j = 0;
	 function avAutocatselect(){
		 var categorySelected = $("#AutoavCategory").val();
		 $("#avCategorySelect").val(categorySelected);
		 var targetUrl = "getBrandListByCategory.action";
		 var params = {"categoryId" : categorySelected};
			$.ajax({

				url: targetUrl,
				data: params,
				dataType: 'xml',
				success: handleCategoryforAutoSelect
			});	 
		 
	 }
	 var handleCategoryforAutoSelect = function(xml) {
			var XMLS = new XMLSerializer(); 
			var inp_xmls = XMLS.serializeToString(xml);
			
			$("#avBrandSelect").val("-1");
			$("#avCodeSelect").val("-1");
			$("#AutoavBrandSelectBox").empty();
			var brandDefaultOption = "<option value='-1'>Select Brand</option>";
			$("#AutoavBrandSelectBox").append(brandDefaultOption);
			try{
				$(xml).find("avBrand").each(function() {
					var brandName = $(this).find("brandName").text();
					var categoryId = $(this).find().text("categoryId");
					$("#AutoavBrandSelectBox").append("<option value='"+brandName+"' categoryId='"+categoryId+"'>"+brandName+"</option>");
									
				});
			}catch(err){
				alert(err.message);
			}
			$("#AutoavBrandSelectBox").show();
		};

		
		$("#AutoavBrandSelectBox").live('change', function(){
			
			
				var brandSelected = $("#AutoavBrandSelectBox").val();
				var categoryId = $("#avCategorySelect").val();
				$("#avBrandSelect").val(brandSelected);
				var targetUrl = "getCodeListByCategoryAndBrand.action";
				var params = {"categoryId" : categoryId, "brandName": brandSelected};
				$.ajax({
					url: targetUrl,
					data: params,
					dataType: 'xml',
					success: handleBrandSelectForAutoSearch
				});	 
			
			
		});
		
		var handleBrandSelectForAutoSearch = function(xml) {

			try{
				var i = 0;
				$(xml).find("avCode").each(function() {
					
					var code = $(this).find("code").text();
					brandcodes[i] = code;
					i++;
				});
			}catch(err){
				alert(err.message);
			}
		};
	
		
		function searchcode(){
			 var name = $("#AutoavBrandSelectBox").val();
			 if(name == -1){
				 alert("Please select Av Blaster Brand to Proceed");
			 }else{
				 $("#searchbutton").hide();
				 $("#stopbutton").show();
				 $("#currentcode").show();
				 $("#previouscode").show();
			 $("#tostopsearch").val("1");
			 set_codes();
			 $('#loading').html("<img src='../resources/images/loading.gif'/>");
			
			 }
			};	
			
			
			function set_codes(){
				brandcodes[j];
				//alert("j"+ j);
				var k = $("#tostopsearch").val();
				$("#currentcode").attr("value", brandcodes[j]);
			
				if(brandcodes[j] != null && k == 1){
				
				
				var generatedDeviceId = $("#genDeviceId").val();
				var macId = $("#gwMacID").val();
				var params = "device.generatedDeviceId=" + generatedDeviceId;
			        params += "&device.gateWay.macId=" + macId;
			    var code = brandcodes[j];
			        params += "&avSetCode=" + code;
			        
					$.ajax({
			   		 url: "tosetAvCode.action",
						 data: params,
			    		 success: function(data){
					}
						});
				$("#current").val(brandcodes[j]);
				$("#previous").val(brandcodes[j-1]);
				}else if(brandcodes[j] == null){
					$("#stopbutton").hide();
					$("#saveBlasterModel").show();
					$("#loading").html("");
				}
				j++;
				setTimeout(set_codes,10000);
			
			}
	
			
			function stopSearching(){
				 
				 $("#tostopsearch").val("0");
				 $("#saveBlasterModel").show();
				 $("#loading").hide();
				 brandcodes.clear;
			};
			
			function categoryy(){
				
				var categorySelected = $("#avCategory").val();
				if(categorySelected == -1){
					alert("Select Category to proceed.");
					$("#avCategorySelect").val("-1");
					$("#avBrandSelect").val("-1");
					$("#avCodeSelect").val("-1");
					resetBrandCodeOption();
					return false;
				}else{
					//1. Save the categoryId to avBrandSelect hidden field
					$("#avCategorySelect").val(categorySelected);
					var targetUrl = "getBrandListByCategory.action";
					var params = {"categoryId" : categorySelected};
					$.ajax({

						url: targetUrl,
						data: params,
						dataType: 'xml',
						success: handleCategorySelectChange
					});	 
				}
			}

			var handleCategorySelectChange = function(xml) {
				
				var XMLS = new XMLSerializer(); 
				var inp_xmls = XMLS.serializeToString(xml);
				
				$("#avBrandSelect").val("-1");
				$("#avCodeSelect").val("-1");
				$("#avBrandSelectBox").empty();
				var brandDefaultOption = "<option value='-1'>Select Brand</option>";
				$("#avBrandSelectBox").append(brandDefaultOption);
				try{
					$(xml).find("avBrand").each(function() {
						var brandName = $(this).find("brandName").text();
						var categoryId = $(this).find().text("categoryId");
						$("#avBrandSelectBox").append("<option value='"+brandName+"' categoryId='"+categoryId+"'>"+brandName+"</option>");
										
					});
				}catch(err){
					alert(err.message);
				}
				 $('#avBrandSelectBox').find('option').each(function()
						   {
				
						      if($(this).val() == selectedBrand)
						         {
						    		
						    	//  alert("selected model number: "+ numberHtml);
						            $('#avBrandSelectBox').val(selectedBrand);
						            $(this).change();
						            
						         }
						   });
				$("#avBrandSelectBox").show();
			};
			
			
			var handleBrandSelectChange = function(xml) {
				var XMLS = new XMLSerializer(); 
				var inp_xmls = XMLS.serializeToString(xml);
				$("#avCodeSelect").val("-1");
				$("#avCodeSelectBox").empty();
				var codeDefaultOption = "<option value='-1'>Select Code</option>";
				$("#avCodeSelectBox").append(codeDefaultOption);
				try{
					$(xml).find("avCode").each(function() {
						var code = $(this).find("code").text();
						var brandName = $(this).find("brandName").text();
						var categoryId = $(this).find().text("categoryId");
						$("#avCodeSelectBox").append("<option value='"+code+"' >"+code+"</option>");
					});
				}catch(err){
					alert(err.message);
				}
				 $('#avCodeSelectBox').find('option').each(function()
						   {
				
						      if($(this).val() == selectedCode)
						         {
						    		
						    	//  alert("selected model number: "+ numberHtml);
						            $('#avCodeSelectBox').val(selectedCode);
						            $(this).change();
						            
						         }
						   });
				$("#avCodeSelectBox").show();
				
			};
			
			
			
			
$(document).ready(function(){
	//Learn changes by apoorva start
	var model = $("#modelnumber").val();
	var avconf = model.split("-");
	  if ((avconf[1]=="Learn")) 	
	{
		$(".Learn").attr('checked', true);
		$("#avCategory").show();
		$("#autoCategorySelect").hide();
		$("#brandsection").hide();
		$("#CategorySelect").show();
		$("#categorysection").show();
		$("#BrandSection").hide();
		$("#saveBlasterModel").show();
		$("#codesection").hide();
		$("#AutoavCategory").show();
		$("#AutoavBrandSelectBox").hide();
		$("#Autobrandsection").hide();
		$("#searchbutton").hide();
	}  
	//Learn changes by apoorva end
	
	j=0;
	$("#AutoavBrandSelectBox").hide();
	$("#autoCategorySelect").hide();
	$("#Autobrandsection").hide();
	$("#AutoavCategory").hide();
	$("#currentcode").hide();
	$("#previouscode").hide();
	$("#searchbutton").hide();
	$("#stopbutton").hide();
	var generatedDeviceId = $("#genDeviceId").val();
	var macId = $("#gwMacID").val();
	var modelNumber = $("#modelnumber").val();
	if(!modelNumber){
		$("#avCategorySelect").val("");
		$("#avBrandSelect").val("");
		$("#avCodeSelect").val("");
	}else if(modelNumber.indexOf("-") >0){
		var avconf = modelNumber.split("-");
		
		$("#avCategorySelect").val(avconf[0]);
		$("#avCategory").val(avconf[0]);
		categoryy();
		selectedBrand = avconf[1];
		$("#avBrandSelect").val(avconf[1]);
		$("#avCodeSelect").val(avconf[2]);
		selectedCode = avconf[2];
	}
	//1. Get Category List and populate the select box.
	var showConfigurationDetails = function(){
		var params = "device.generatedDeviceId=" + generatedDeviceId;
		params += "&device.gateWay.macId=" + macId;
		$.ajax({
			url: "toAVBlasterModelNumber.action",
			data: params,
			success: function(data){
				
			}
		});
	};
	
	showConfigurationDetails(); 

	var resetBrandCodeOption = function(){
		//Reset Brand Options
		$("#avBrandSelectBox").empty();
		var resetBrandOption = "<option value='-1'>Select Brand</option>";
		$("#avBrandSelectBox").append(resetBrandOption);
		
		//Reset Code Options
		$("#avCodeSelectBox").empty();
		var resetCodeOption = "<option value='-1'>Select Code</option>";
		$("#avCodeSelectBox").append(resetCodeOption);
	};
	
	
	
	
	var resetCodeOption = function(){
		//Reset Code Options
		$("#avCodeSelectBox").empty();
		var resetCodeOption = "<option value='-1'>Select Code</option>";
		$("#avCodeSelectBox").append(resetCodeOption);
	};
	
	$("#avBrandSelectBox").live('change', function(){
		var brandSelected = $("#avBrandSelectBox").val();
		var categoryId = $("#avCategorySelect").val();
		if(brandSelected == -1){
			alert("Select Brand to proceed.");
			$("#avBrandSelect").val("-1");
			$("#avCodeSelect").val("-1");
			resetCodeOption();
			return false;
		}else{
			// Get List of av codes based on category and  brand
			$("#avBrandSelect").val(brandSelected);
			var targetUrl = "getCodeListByCategoryAndBrand.action";
			var params = {"categoryId" : categoryId, "brandName": brandSelected};
			$.ajax({
				url: targetUrl,
				data: params,
				dataType: 'xml',
				success: handleBrandSelectChange
			});	 
		}
		
	});
	
	
	
	
	$("#avCodeSelectBox").die('change');
	$("#avCodeSelectBox").live('change', function(){
		var categoryId = $("#avCategorySelect").val();
		var brandSelected = $("#avBrandSelect").val();
		var codeSelected = $("#avCodeSelectBox").val();
		//alert("categoryId : " + categoryId + ", brandSelected : " + brandSelected + ", codeSelected : " + codeSelected);
		if(codeSelected == -1){
			alert("Select Code to proceed.");
			$("#avCodeSelect").val("-1");
			return false;
		}
	});

	
	$("#saveBlasterModel").live('click', function(){ 
		var categoryToSave;
		var brandToSave;
		var codeToSave;
		var code;
		var category;
		var brand;
		if($(".Auto").attr('checked')){
			
		 code = $("#current").val();
		 category = $("#AutoavCategory").val(); 
		categoryToSave = $("#avCategorySelect").val(category);
		brand = $("#AutoavBrandSelectBox").val();	
		brandToSave = $("#avBrandSelect").val(brand);
		codeToSave = $("#avCodeSelect").val(code);
		
		}
		//Learn changes by apoorva start
		else if ($(".Learn").attr('checked'))
		{
			category = $("#avCategory").val();
			categoryToSave = $("#avCategorySelect").val(category);
			if((categoryToSave == -1) || (categoryToSave == "")){
				alert("Select 'Category' to proceed.");
				return false;
			}
		}
		
		//Learn changes by apoorva end
		else{
			
		code = $("#avCodeSelectBox").val();
		brand = $("#avBrandSelectBox").val();
		category = $("#avCategory").val();
		categoryToSave = $("#avCategorySelect").val(category);
		brandToSave = $("#avBrandSelect").val(brand);
		codeToSave = $("#avCodeSelect").val(code);
		//alert("categoryToSave : " + categoryToSave + ", brandToSave : " + brandToSave + ", codeToSave : " + codeToSave);
		if((categoryToSave == -1) || (categoryToSave == "")){
			alert("Select 'Category' to proceed.");
			return false;
		}else if((brandToSave == -1) || (brandToSave == "")){
			alert("Select 'Brand' to proceed.");
			return false;
		}else if((codeToSave == -1) || (codeToSave == "")){
			alert("Select 'Code' to proceed.");
			return false;
		}
		}
	});
	
	
$(".Auto").live('click', function(){
		
		if($(this).attr('checked')){
			$("#CategorySelect").hide();
			$("#AutoavCategory").show();
			$("#autoCategorySelect").show();
			$("#brandsection").show();
			$("#avCategory").hide();
			$("#categorysection").hide();
			$("#BrandSection").hide();
			$("#saveBlasterModel").hide();
			$("#codesection").hide();
			$("#avCategory").hide();
			$("#AutoavBrandSelectBox").show();
			$("#searchbutton").show();
			$("#Autobrandsection").show();
		}else{
			$("#autoCategorySelect").hide();
			$("#brandsection").hide();
			$("#CategorySelect").show();
			$("#avCategory").show();
			$("#categorysection").show();
			$("#BrandSection").show();
			$("#saveBlasterModel").show();
			$("#codesection").show();
			$("#avCategory").show();
			$("#AutoavCategory").hide();
			$("#AutoavBrandSelectBox").hide();
			$("#Autobrandsection").hide();
			$("#searchbutton").hide();
		}
	});
	
//Learn changes by apoorva start
 $(".Learn").live('click', function(){
	
	if($(this).attr('checked')){
		$("#avCategory").show();
		$("#autoCategorySelect").hide();
		$("#brandsection").hide();
		$("#CategorySelect").show();
		$("#categorysection").show();
		$("#BrandSection").hide();
		$("#saveBlasterModel").show();
		$("#codesection").hide();
		$("#AutoavCategory").show();
		$("#AutoavBrandSelectBox").hide();
		$("#Autobrandsection").hide();
		$("#searchbutton").hide();
	}
	else
		{
		$("#autoCategorySelect").hide();
		$("#brandsection").hide();
		$("#CategorySelect").show();
		$("#avCategory").show();
		$("#categorysection").show();
		$("#BrandSection").show();
		$("#saveBlasterModel").show();
		$("#codesection").show();
		$("#avCategory").show();
		$("#AutoavCategory").hide();
		$("#AutoavBrandSelectBox").hide();
		$("#Autobrandsection").hide();
		$("#searchbutton").hide();
		}
}); 
//Learn changes by apoorva end	
	
});

</script>