/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.core;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Queue;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * @author Coladi
 *
 */
public class ServeOneNewControllerClient implements Runnable {

	private Socket socket;
	private CommandExecutor commandExecutor;

	public ServeOneNewControllerClient(Socket socket, CommandExecutor commandExecutor) {
		this.socket = socket;
		this.commandExecutor = commandExecutor;
	}

	@Override
	public void run() {
		
		String imvgId = null;
		try {
			InputStream inputStream = this.socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			String commandInXml = dataInputStream.readUTF();
			// Extract the xml content into one queue;
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
