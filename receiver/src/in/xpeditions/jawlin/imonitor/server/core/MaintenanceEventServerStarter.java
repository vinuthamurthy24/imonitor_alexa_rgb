package in.xpeditions.jawlin.imonitor.server.core;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

import javax.net.ssl.SSLServerSocketFactory;

public  class MaintenanceEventServerStarter implements Runnable{
	
	private Properties properties;
	private ServerSocket serverSocket;
	private CommandExecutor commandExecutor;

	public MaintenanceEventServerStarter(Properties properties, CommandExecutor commandExecutor) {
		this.properties = properties;
		this.commandExecutor=commandExecutor;
	}
	
	@Override
	public void run() {
		try {
			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			int port = Integer.parseInt(this.properties.getProperty("maintenanceport"));
			
			String mode = this.properties.getProperty("securemode");
			boolean isSecureMode = Boolean.parseBoolean(mode);
			
			
//			String sTimeOut = ImonitorControllerCommunicator.getPropertyMap().get(Constants.IMVG_CONNECTION_TIMEOUT);
//			int timeOut = Integer.parseInt(sTimeOut);
			if(isSecureMode){
				this.serverSocket = sslserversocketfactory.createServerSocket(port);
			}else{
				this.serverSocket = new ServerSocket(port);
			}
			while (true) 
			{
				if (this.serverSocket.isClosed()) {
					break;
				}
				Socket socket = null;
				try {
					socket = this.serverSocket.accept();
					InetAddress inetAddress = socket.getInetAddress();
					if(inetAddress != null){
						LogUtil.info("New Connection from : " + inetAddress.getCanonicalHostName() + "," + inetAddress.getHostAddress() + "," + inetAddress.getHostName());
					}
					MaintainNewControllerClient maintainNewControllerClient = new MaintainNewControllerClient(socket, this.commandExecutor);
					Thread t = new Thread(maintainNewControllerClient);
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

	public CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}

	public void setCommandExecutor(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}

}
