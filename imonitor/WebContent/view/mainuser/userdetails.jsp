<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
	<storage>
		<UN><s:property value="user.userId"/></UN>
	    <ILT><s:date name="user.lastLoginTime" format="dd/MM/yy 'at' hh:mm a"/></ILT>
		<totalspace><s:property value="#user.customer.paidStorageMB + #user.customer.freeStorageMB"/></totalspace>
		<usedspace><s:property value="#totalUsedSpace"/></usedspace>
	</storage>	
	<s:iterator value="gateWays" var="gateWay">
		<gateway>
			<id><s:property value="#gateWay.id"/></id>
			<gatewayName><s:property value="#gateWay.name"/></gatewayName>
			<macid><s:property value="#gateWay.macId"/></macid>
			<!--  sumit added one line -->
			<currentMode><s:property value="#gateWay.currentMode"/></currentMode>
			<status><s:property value="#gateWay.status.name"/></status>
			<featuresEnabled><s:property value="#gateWay.customer.featuresEnabled"/></featuresEnabled>
			<gateWayVersion><s:property value="#gateWay.gateWayVersion"/></gateWayVersion>
			<FirmwareId><s:property value="#gateWay.FirmwareId"/></FirmwareId>
			<Latestversion><s:property value="#gateWay.Latestversion"/></Latestversion>
			<allOnOff><s:property value="#gateWay.allOnOffStatus"/></allOnOff>
			<devices>
			<s:iterator value="#gateWay.devices" var="device">
				<device>
					<index><s:property value="#device.id"/></index>
					<id><s:property value="#device.generatedDeviceId"/></id>
					<deviceId><s:property value="#device.deviceId"/></deviceId>
					<name><s:property value="#device.friendlyName"/></name>
					<type><s:property value="#device.deviceType.details"/></type>
					<location><s:property value="#device.location.name"/></location>
					<commandparam><s:property value="#device.commandParam"/></commandparam>
					<alertparam><s:property value="#device.lastAlert.alertCommand"/></alertparam>
					<batterylevel><s:property value="#device.batteryStatus"/></batterylevel>
					<locationicon><s:property value="#device.location.iconFile"/></locationicon>
				<!--<iconfile><s:property value="#device.deviceType.iconFile"/></iconfile> -->
					<iconfile><s:property value="#device.icon.fileName"/></iconfile>
					<!--  <armStatus><s:property value="#device.armStatus.name"/></armStatus> 
					<rules><s:if test="#device.rules == null">0</s:if><s:else>1</s:else></rules> -->
					<modelnumber><s:property value="#device.modelNumber"/></modelnumber>
					<s:if test='#device.modelNumber.equals("HMD")'>
                    <HMDalertTime><s:property value="#device.HMDalertTime"/></HMDalertTime>
                    <HMDalertstatus><s:property value="#device.HMDalertstatus"/></HMDalertstatus>
                    <HMDalertValue><s:property value="#device.HMDalertValue"/></HMDalertValue>
                     <HMDpowerinfo><s:property value="#device.HMDpowerinfo"/></HMDpowerinfo>
                    </s:if>
					<make><s:property value="#device.make.id"/></make>
					<enableStatus><s:property value="#device.enableStatus"/></enableStatus>
					<enableList><s:property value="#device.enableList"/></enableList>
					<!-- sumit start: Re-Ordering Of Device -->
					<positionIndex><s:property value="#device.posIdx"/></positionIndex>
					<!-- sumit end: Re-Ordering Of Device --> 
					<controlnightvision><s:property value="#device.deviceConfiguration.controlNightVision"/></controlnightvision>
					<topMap><s:property value="#device.locationMap.top"/></topMap>
					<leftMap><s:property value="#device.locationMap.leftMap"/></leftMap>
					<mapId><s:property value="#device.locationMap.id"/></mapId>
					<locationId><s:property value="#device.location.id"/></locationId>
					<pantiltControl><s:property value="#device.deviceConfiguration.pantiltControl"/></pantiltControl>
					<presetvalue><s:property value="#device.deviceConfiguration.presetvalue"/></presetvalue>
				    <fanModeValue><s:property value="#device.deviceConfiguration.fanModeValue"/></fanModeValue>
				    <fanMode><s:property value="#device.deviceConfiguration.fanMode"/></fanMode>
				    <acSwing><s:property value="#device.deviceConfiguration.acSwing"/></acSwing>
				    <acLearnValue><s:property value="#device.deviceConfiguration.acLearnValue"/></acLearnValue>
				     <sensedTemp><s:property value="#device.deviceConfiguration.sensedTemperature"/></sensedTemp>
				<switchType><s:property value="#device.switchType"/></switchType>
               <devicetype><s:property value="#device.deviceType.details"/></devicetype>
			 <!-- 	Indoor Unit Configuration -->
			<fanModeCapability><s:property value="#device.deviceConfiguration.fanModeCapability"/></fanModeCapability>
			<coolModeCapability><s:property value="#device.deviceConfiguration.coolModeCapability"/></coolModeCapability>
			<heatModeCapability><s:property value="#device.deviceConfiguration.heatModeCapability"/></heatModeCapability>
			<autoModeCapability><s:property value="#device.deviceConfiguration.autoModeCapability"/></autoModeCapability>
			<dryModeCapability><s:property value="#device.deviceConfiguration.dryModeCapability"/></dryModeCapability>
			<fanDirectionLevelCapability><s:property value="#device.deviceConfiguration.fanDirectionLevelCapability"/></fanDirectionLevelCapability>
			<fanDirectionCapability><s:property value="#device.deviceConfiguration.fanDirectionCapability"/></fanDirectionCapability>
			<fanVolumeLevelCapability><s:property value="#device.deviceConfiguration.fanVolumeLevelCapability"/></fanVolumeLevelCapability>
			<fanVolumeCapability><s:property value="#device.deviceConfiguration.fanVolumeCapability"/></fanVolumeCapability>
			<coolingUpperlimit><s:property value="#device.deviceConfiguration.coolingUpperlimit"/></coolingUpperlimit>
			<coolingLowerlimit><s:property value="#device.deviceConfiguration.coolingLowerlimit"/></coolingLowerlimit>
			<heatingUpperlimit><s:property value="#device.deviceConfiguration.heatingUpperlimit"/></heatingUpperlimit>
			<heatingLowerlimit><s:property value="#device.deviceConfiguration.heatingLowerlimit"/></heatingLowerlimit> 
			<ConnectStatus><s:property value="#device.deviceConfiguration.ConnectStatus"/></ConnectStatus>
			<CommStatus><s:property value="#device.deviceConfiguration.CommStatus"/></CommStatus>
			<fanVolumeValue><s:property value="#device.deviceConfiguration.fanVolumeValue"/></fanVolumeValue>
			<fanDirectionValue><s:property value="#device.deviceConfiguration.fanDirectionValue"/></fanDirectionValue>
			<errorMessage><s:property value="#device.deviceConfiguration.errorMessage"/></errorMessage>
			 <filterSignStatus><s:property value="#device.deviceConfiguration.filterSignStatus"/></filterSignStatus>
			 <temperatureValue><s:property value="#device.deviceConfiguration.temperatureValue"/></temperatureValue>
			 
				
				</device>
			</s:iterator>
			</devices>
		</gateway>
	</s:iterator>
</imonitor>