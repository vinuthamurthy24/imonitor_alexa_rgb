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
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Queue;

/**
 * @author Coladi
 * 
 */
public class ServeOneEventClient implements Runnable {

	private Socket socket;
	private CommandExecutor commandExecutor;
	private String imvgId;
	private long timeOut = 60000;
	private Thread gatewayThread;

	public ServeOneEventClient(Socket socket, CommandExecutor commandExecutor) {
		this.socket = socket;
		this.commandExecutor = commandExecutor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Queue<KeyValuePair> commands = null;
		String messageFromClient = null;
		DataInputStream dataInputStream = null;
		Queue<KeyValuePair> extractCommandQueue = null;
		try {
            
			InputStream inputStream = this.socket.getInputStream();
			dataInputStream = new DataInputStream(inputStream);
			messageFromClient = dataInputStream.readUTF();
			LogUtil.info("Message From Client:\n" + messageFromClient);
			extractCommandQueue = IMonitorUtil
					.commandToQueue(messageFromClient);
			String imvgId = IMonitorUtil.commandId(extractCommandQueue,
					Constants.IMVG_ID);
			this.imvgId = imvgId;
//			If the iMVG is reconnecting, we may have already one connection from same IMVG which is going to die.
//			We will kill that socket now itself.
			LogUtil.info("checking gateway connection " + this.commandExecutor.isConnectionExists(imvgId));
			if(this.commandExecutor.isConnectionExists(imvgId)){
				LogUtil.info("We are already having one connection to imvg(" + imvgId + "). So Killing the old one and keeping the new one !!!");
				this.commandExecutor.killConnection(imvgId);
			}

			this.commandExecutor.addImvg(imvgId, this.socket);

//			We are here, because iMVG reconnected/connected.
//			Just send an IMVG_UP command to the server.
//			Check the above command is not IMVG_UP
			String commandId = IMonitorUtil.commandId(extractCommandQueue, Constants.ALERT_EVENT);
			if(commandId == null || !commandId.equalsIgnoreCase(Constants.IMVG_UP)){
				sendImvgAlertCommandToServer(Constants.IMVG_UP);
				LogUtil.info("We have send one iMVG up to controller for imvg because imvg is reconnected/connected. " + imvgId);
			}
			
		} catch (SocketTimeoutException e) {
			LogUtil.error("First Reading - Time out occured from IMVG +" + this.imvgId + ": "
					+ e.getMessage());
			closeSocket();
			
			return;
		} catch (IOException e) {
			LogUtil.error("First Reading - Error when reading from the imvg " + this.imvgId
					+ ": " + e.getMessage());
			closeSocket();
			return;
		} 
		//vibhu start
		catch(Exception e)
		{
			LogUtil.error("First Reading - General error when sending IMVG_UP for imvg" +this.imvgId);
			LogUtil.info(e.getMessage(), e);
			closeSocket(); 
			return;
		}
		
		try
		{
			commands = IMonitorUtil.updateEvent(extractCommandQueue);
           // LogUtil.info("commands : " + commands);
			this.commandExecutor.executeCommand(commands);
		}
		catch (IllegalArgumentException e) {
				LogUtil.error("FR: Error when updating the event for imvg "
						+ this.imvgId + ": IllegalArgumentException " + e.getMessage());
		} catch (IllegalAccessException e) {
				LogUtil.error("FR: Error when updating the event for imvg "
						+ this.imvgId + ": IllegalAccessException " + e.getMessage());
		} catch (InvocationTargetException e) {
				LogUtil.error("FR: Error when updating the event for imvg "
						+ this.imvgId + ": InvocationTargetException " + e.getTargetException().getMessage());
				LogUtil.info("Remote stack: ", e.getTargetException());
		} catch (InstantiationException e) {
				LogUtil.error("FR: Error when updating the event for imvg "
						+ this.imvgId + ": InstantiationException " + e.getMessage());
			}
			catch(Exception e)
			{
				LogUtil.error("FR: Got an exception while communicating with gateway. Will close connection." +this.imvgId);
				LogUtil.info(e.getMessage(), e);
				closeSocket(); 
				return;
			}
		//vibhu end
		while (true) {
			try {
				messageFromClient = dataInputStream.readUTF();
				LogUtil.info("Message From Client:\n" + messageFromClient);
				extractCommandQueue = IMonitorUtil
						.commandToQueue(messageFromClient);
				
				commands = IMonitorUtil.updateEvent(extractCommandQueue);
				//LogUtil.info("step 2");
				if (!commands.isEmpty()) {
					//LogUtil.info("step 3");
					this.commandExecutor.executeCommand(commands);
				}
			} catch (SocketTimeoutException e) {
				LogUtil.error("Time out occured : " + e.getMessage());
				closeSocket();
				return;
			} catch (IOException e) {
				LogUtil.error("Error in communication from imvg "
						+ this.imvgId + ": IOException " + e.getMessage());
				closeSocket();
				//this.commandExecutor.killConnection(this.imvgId);
				return;
			} catch (IllegalArgumentException e) {
				LogUtil.error("Error when updating the event for imvg "
						+ this.imvgId + ": IllegalArgumentException " + e.getMessage());
			} catch (IllegalAccessException e) {
				LogUtil.error("Error when updating the event for imvg "
						+ this.imvgId + ": IllegalAccessException " + e.getMessage());
			} catch (InvocationTargetException e) {
				LogUtil.error("Error when updating the event for imvg "
						+ this.imvgId + ": InvocationTargetException " + e.getTargetException().getMessage());
				LogUtil.info("Remote stack: ", e.getTargetException());
			} catch (InstantiationException e) {
				LogUtil.error("Error when updating the event for imvg "
						+ this.imvgId + ": InstantiationException " + e.getMessage());
			}
			//vibhu start
			catch(Exception e)
			{
				LogUtil.error("Got an exception while communicating with gateway. Will close connection." +this.imvgId);
				LogUtil.info(e.getMessage(), e);
				closeSocket(); 
				return;
			}
			//vibhu end
		}
	}

	private void closeSocket() {
		this.commandExecutor.killConnection(this.imvgId);
		if(this.socket != null){
			try {
				if(this.socket.isClosed()){
						this.socket.close();
				}
			} catch (IOException e) {
				LogUtil.error("Error when closing the socket for iMVG(" + this.imvgId + ") : IOException - " + e.getMessage());
			}
		}
		this.socket = null;
		// Send the IMVGDOWN request for update in controller.
//vibhu start
		try
		{
			
				try {
					
					LogUtil.info("Entring into Sleep Of one Minute for Gateway "+this.imvgId);
					Thread.sleep(timeOut);
				} catch (InterruptedException e) {
					LogUtil.info("Got Exception: ", e);
					e.printStackTrace();
				}
				
			
			
			
				LogUtil.info("After Wait is Over Connection is "+this.commandExecutor.isConnectionExists(this.imvgId)+" Gateway "+this.imvgId);
			
			
				if(this.commandExecutor.isConnectionExists(this.imvgId))
				{
					LogUtil.info("With in one minute Gateway is reconnected,So Not Updateing IMVG Down");
				}
				else
				{
					sendImvgAlertCommandToServer(Constants.IMVG_DOWN);
					LogUtil.info("Sending IMVG_DOWN ("+ this.imvgId + ") alert to Controller. Somthing happened to the communication. Please check log for more details.");
				}
				
			
			
			
		}
		catch(Exception e)
		{
			LogUtil.error("ERROR:"+e.getMessage()+" when sending IMVG_DOWN" );
			LogUtil.info(e.getMessage(), e);
		}
		
//vibhu end
	}
//vibhu start	
/*	private void sendImvgAlertCommandToServer(String imvgAlert) {
		Queue<KeyValuePair> commands = IMonitorUtil.createImvgAlertCommand(imvgAlert, this.imvgId);
		try {
			IMonitorUtil.updateEvent(commands);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		} catch (InstantiationException e) {
		}
	}
*/
	private void sendImvgAlertCommandToServer(String imvgAlert) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Queue<KeyValuePair> commands = IMonitorUtil.createImvgAlertCommand(imvgAlert, this.imvgId);
	//	LogUtil.info("creating updating event" + this.imvgId);
		IMonitorUtil.updateEvent(commands);
	}
//vibhu end
	
	//Naveen start
		public void start () {
		      LogUtil.info("starting thread ");
		      if (gatewayThread == null) {
		    	  gatewayThread = new Thread (this);
		    	  gatewayThread.start ();
		      }
		   }
	
}
