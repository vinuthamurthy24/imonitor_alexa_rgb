<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<title>Mobile Interface Testing...</title>
	</head>
	<body>
		<form action="login.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Login"/>
		</form>
		<form action="listDevices.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Devices"/>
		</form>
		<form action="controlDevice.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Control"/>
		</form>
		<form action="startLiveStream.action" method="post">
			<textarea name="data"></textarea>
			<input type="submit" value="Live Stream"/>
		</form>
	</body>
</html>