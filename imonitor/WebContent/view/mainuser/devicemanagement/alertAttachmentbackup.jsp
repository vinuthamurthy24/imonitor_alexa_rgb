<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
				<div class="closebutton">
					<a href="#" onclick="closeoverlay()"><img src="/imonitor/resources/images/close_button.png" title="close"/></a>
				</div>
			<div class="imageshowingtimedetails">
				<ul>
					<li style="color:#047AA5;" id="timeStamp"><span></span></li>
					<li style="color:#2F2F2F;" id="alertdetails"><span></span></li>
				</ul>
			<div>
				
				
				<div >				
					<img id="attachmentimageid" src="/imonitor<s:property value="object"/>" style="width:422px;height:245px;" class="imagedisplaying">
				</div>
				<div style="float: right;margin-right: 38px;">
					<a id="downloadLink" target="_blank" href="/imonitor<s:property value="object"/>">download</a>
				</div>
					
			</div>
			</div>

	<div class="displayimage">
	</div>

<script>
imvgDetailsbckup.addImageDetails ("<s:property value="imvgUploadContextPath + object[0]"/>");
	
	
		 var showImageId = "<s:property value="object[1]"/>" ;
	
	
	
</script>

<script type="text/javascript">

imvgDetailsbckup.showImage(1 * showImageId);
</script>