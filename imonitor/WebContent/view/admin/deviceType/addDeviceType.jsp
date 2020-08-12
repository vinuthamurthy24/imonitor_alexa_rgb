<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%@page import="com.opensymphony.xwork2.ActionContext"%>
<div style="color: green;"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<h4>Add DeviceType</h4>
	<s:form action="saveNewDeviceType" method="post" id="" cssClass="ajaxinlineform">
	<s:textfield name="newDeviceType.deviceType" label="Device Type" cssClass=""></s:textfield>
	<s:textfield name="newDeviceType.deviceTypeNumber" label="Device type Id" cssClass=""></s:textfield>
	<s:textfield name="newDeviceType.basicDeviceClass" label="Basic Device Class" cssClass=""></s:textfield>
	<s:textfield name="newDeviceType.genericDeviceClass" label="Generic Device Class" cssClass=""></s:textfield>
	<s:textfield name="newDeviceType.specificDeviceClass" label="Specific Device Class" cssClass=""></s:textfield>
	<s:textfield name="newDeviceType.supportedClass" label="Supported Classes" cssClass=""></s:textfield>
	<s:textfield name="newDeviceType.manufacturerId" label="Manufacturer Id" cssClass=""></s:textfield>
	<s:textfield name="newDeviceType.productId" label="Product Id" cssClass=""></s:textfield>
	<s:textfield name="newDeviceType.modelNumber" label="Model Number" cssClass=""></s:textfield>
	<s:submit value="Save"></s:submit>
</s:form>
<script>

</script>