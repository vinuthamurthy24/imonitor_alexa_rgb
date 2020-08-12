<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
	<devices>
		<device>
			<index><s:property value="device.id"/></index>
			<id><s:property value="device.generatedDeviceId"/></id>
			<name><s:property value="device.friendlyName"/></name>
			<type><s:property value="device.deviceType.details"/></type>
			<location><s:property value="device.location.name"/></location>
			<commandparam><s:property value="device.commandParam"/></commandparam>
			<alertparam><s:property value="device.lastAlert.alertCommand"/></alertparam>
			<batterylevel><s:property value="device.batteryStatus"/></batterylevel>
			<%-- <iconfile><s:property value="device.deviceType.iconFile"/></iconfile> --%>
			<iconfile><s:property value="device.icon.fileName"/></iconfile>
			<armStatus><s:property value="device.armStatus.name"/></armStatus>
			<rules><s:if test="device.rules == null">0</s:if><s:else>1</s:else></rules>
			<modelnumber><s:property value="device.modelNumber"/></modelnumber>
			<enableStatus><s:property value="device.enableStatus"/></enableStatus>
			<s:if test='device.modelNumber=="HMD"'>
             <HMDalertValue><s:property value="device.HMDalertValue"/></HMDalertValue>
            <HMDpowerinfo><s:property value="device.HMDpowerinfo"/></HMDpowerinfo>      
            </s:if>
            <fanModeValue><s:property value="device.deviceConfiguration.fanModeValue"/></fanModeValue>
            <fanMode><s:property value="device.deviceConfiguration.fanMode"/></fanMode>
            <acSwing><s:property value="device.deviceConfiguration.acSwing"/></acSwing>
            <acLearnValue><s:property value="device.deviceConfiguration.acLearnValue"/></acLearnValue>
            
          <!-- 	Indoor Unit Configuration -->
			<fanModeCapability><s:property value="device.deviceConfiguration.fanModeCapability"/></fanModeCapability>
			<coolModeCapability><s:property value="device.deviceConfiguration.coolModeCapability"/></coolModeCapability>
			<heatModeCapability><s:property value="device.deviceConfiguration.heatModeCapability"/></heatModeCapability>
			<autoModeCapability><s:property value="device.deviceConfiguration.autoModeCapability"/></autoModeCapability>
			<dryModeCapability><s:property value="device.deviceConfiguration.dryModeCapability"/></dryModeCapability>
			<fanDirectionLevelCapability><s:property value="device.deviceConfiguration.fanDirectionLevelCapability"/></fanDirectionLevelCapability>
			<fanDirectionCapability><s:property value="device.deviceConfiguration.fanDirectionCapability"/></fanDirectionCapability>
			<fanVolumeLevelCapability><s:property value="device.deviceConfiguration.fanVolumeLevelCapability"/></fanVolumeLevelCapability>
			<fanVolumeCapability><s:property value="device.deviceConfiguration.fanVolumeCapability"/></fanVolumeCapability>
			<coolingUpperlimit><s:property value="device.deviceConfiguration.coolingUpperlimit"/></coolingUpperlimit>
			<coolingLowerlimit><s:property value="device.deviceConfiguration.coolingLowerlimit"/></coolingLowerlimit>
			<heatingUpperlimit><s:property value="device.deviceConfiguration.heatingUpperlimit"/></heatingUpperlimit>
			<heatingLowerlimit><s:property value="device.deviceConfiguration.heatingLowerlimit"/></heatingLowerlimit> 
			<ConnectStatus><s:property value="device.deviceConfiguration.ConnectStatus"/></ConnectStatus>
			<CommStatus><s:property value="device.deviceConfiguration.CommStatus"/></CommStatus>
			<fanVolumeValue><s:property value="device.deviceConfiguration.fanVolumeValue"/></fanVolumeValue>
			<fanDirectionValue><s:property value="device.deviceConfiguration.fanDirectionValue"/></fanDirectionValue>
			<errorMessage><s:property value="device.deviceConfiguration.errorMessage"/></errorMessage>
			 <filterSignStatus><s:property value="device.deviceConfiguration.filterSignStatus"/></filterSignStatus>   
			 <temperatureValue><s:property value="#device.deviceConfiguration.temperatureValue"/></temperatureValue>
			 <sensedTemp><s:property value="#device.deviceConfiguration.sensedTemperature"/></sensedTemp>
         
		<switchType><s:property value="device.switchType"/></switchType>
		</device>
	</devices>
	<event>
		<actionperformed><s:property value="event.actionPerformed"/></actionperformed>			
	</event>
</imonitor>