<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<title>Mobile Interface Testing...</title>
	</head>
	<body>
		<form action="synchronizegatewaydetails.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Devce Details"/>
		</form>
		<form action="synchronizedevicedetails.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Devce Details"/>
		</form>
		<form action="synchronizescenariodetails.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Scenario Details"/>
		</form>
		<form action="controldevice.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Control Device"/>
		</form>
		<form action="executeScenario.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Execute Scenario"/>
		</form>
		<form action="armDevice.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Arm Devices"/>
		</form>
		<form action="disarmDevice.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="DisArm Devices"/>
		</form>
		
		
		<form action="EnableCamera.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="EnableCamera"/>
		</form>
		<form action="DisableCamera.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="DisableCamera"/>
		</form>
		
		
		<form action="startlivestream.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Start Streaming."/>
		</form>
		<form action="controlcamera.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Control Camera"/>
		</form>
		<form action="controlnightvision.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Control Night Vision"/>
		</form>
	</body>
</html>