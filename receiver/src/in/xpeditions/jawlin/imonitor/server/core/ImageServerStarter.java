package in.xpeditions.jawlin.imonitor.server.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

import javax.net.ssl.SSLServerSocketFactory;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor;
import in.xpeditions.jawlin.imonitor.server.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.server.util.Constants;

public class ImageServerStarter implements Runnable{

	
	private Properties properties;
	private ServerSocket serverSocket;
	private CommandExecutor commandExecutor;

	public ImageServerStarter(Properties properties, CommandExecutor commandExecutor) {
		this.properties = properties;
		this.commandExecutor = commandExecutor;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			int port = Integer.parseInt(this.properties.getProperty("imagereceiverport"));
			String mode = this.properties.getProperty("securemode");
			boolean isSecureMode = Boolean.parseBoolean(mode);
			String sTimeOut = ImonitorControllerCommunicator.getPropertyMap().get(Constants.IMVG_CONNECTION_TIMEOUT);
			int timeOut = Integer.parseInt(sTimeOut);
			if(isSecureMode){
				this.serverSocket = sslserversocketfactory.createServerSocket(port);
			}else{
				this.serverSocket = new ServerSocket(port);
			}
			while (true) {
				if (this.serverSocket.isClosed()) {
					break;
				}
				// Blocks until a connection occurs:
				// If the server is stopped then break the loop.
				Socket socket = null;
				try {
					socket = this.serverSocket.accept();
					socket.setSoTimeout(timeOut);
					
					 // obtaining input and out streams
	                InputStream dis = new DataInputStream(socket.getInputStream());
	                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					ServerOneCameraClient serveOneEventClient = new ServerOneCameraClient(
							socket,dis,dos);
					Thread t = new Thread(serveOneEventClient);
					t.start();
					
				} catch (SocketException e) {
					// If exception then it means, the server is closed.
					// Then break the loop.
					LogUtil.error("Exception in Socket Communication : " + e.getMessage());
					break;
				}
			}
		} catch (IOException e) {
			LogUtil.error("Exception in Socket Communication : " + e.getMessage());
		} finally {
			try {
				if (!this.serverSocket.isClosed()) {
					this.serverSocket.close();
				}
			} catch (IOException e) {
				LogUtil.error("Exception in Socket Communication : " + e.getMessage());
			}
		}
		
	}

}
