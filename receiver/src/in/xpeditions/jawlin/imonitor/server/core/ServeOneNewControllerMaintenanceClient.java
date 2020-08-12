package in.xpeditions.jawlin.imonitor.server.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import javax.net.ssl.SSLSocket;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ServeOneNewControllerMaintenanceClient implements Runnable {
	
	private SSLSocket socket;
	private CommandExecutor commandExecutor;

	public ServeOneNewControllerMaintenanceClient(SSLSocket socket,
			CommandExecutor commandExecutor) {
		this.socket=socket;
		this.commandExecutor=commandExecutor;
	}

	
		public void run() 
		{
			String imvgId = null;
			try {
				InputStream inputStream = this.socket.getInputStream();
				DataInputStream dataInputStream = new DataInputStream(inputStream);
				String commandInXml = dataInputStream.readUTF();
				Queue<KeyValuePair> extractedCommandsFromXml = IMonitorUtil.extractCommandsQueueFromXml(commandInXml);
				imvgId = IMonitorUtil.commandId(extractedCommandsFromXml, Constants.IMVG_ID);
				this.commandExecutor.executeCommand(extractedCommandsFromXml);
			} catch (IOException e) {
				LogUtil.error("Error when sending messaage to the client '" + imvgId + "' and the message is " + e.getMessage());
				return;
			} catch (ParserConfigurationException e) {
				LogUtil.error("Error when sending messaage to the client '" + imvgId + "' and the message is " + e.getMessage());
				return;
			} catch (SAXException e) {
				LogUtil.error("Error when sending messaage to the client '" + imvgId + "' and the message is " + e.getMessage());
				return;
			}
			
		
		}
	
}
