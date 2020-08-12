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
import java.util.Queue;


public class MaintainNewControllerClient implements Runnable { 
		private Socket socket;
		private CommandExecutor commandExecutor;
		private String imvgId;

		public MaintainNewControllerClient(Socket socket, CommandExecutor commandExecutor) {
			this.socket = socket;
			this.commandExecutor = commandExecutor;
		}

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
				extractCommandQueue = IMonitorUtil.commandToQueue(messageFromClient);
				String imvgId = IMonitorUtil.commandId(extractCommandQueue,Constants.IMVG_ID);
				this.imvgId = imvgId;
				
				if(this.commandExecutor.isConnectionExists(imvgId)){
					LogUtil.info("We are already having one connection to imvg(" + imvgId + "). So Killing the old one and keeping the new one !!!");
					this.commandExecutor.killConnection(imvgId);
				}
				this.commandExecutor.addImvg(imvgId,this.socket);
				
				commands = IMonitorUtil.UpdateMaintenceEvents(extractCommandQueue);
				this.commandExecutor.executeCommand(commands);
				
				}
				catch (IOException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			while(true)
			{
				try {
					messageFromClient = dataInputStream.readUTF();
					LogUtil.info("Message From Client:\n" + messageFromClient);
					extractCommandQueue = IMonitorUtil.commandToQueue(messageFromClient);
					commands = IMonitorUtil.UpdateMaintenceEvents(extractCommandQueue);
					if (!commands.isEmpty()) {
						this.commandExecutor.executeCommand(commands);
					}
				} catch (IllegalArgumentException e) {
					LogUtil.error("Error when updating the event for imvg "
							+ this.imvgId + ": IllegalArgumentException " + e.getMessage());
				} catch (IllegalAccessException e) {
					LogUtil.error("Error when updating the event for imvg "
							+ this.imvgId + ": IllegalAccessException " + e.getMessage());
				} catch (InvocationTargetException e) {
					LogUtil.error("Error when updating the event for imvg "+ this.imvgId + ": InvocationTargetException " + e.getTargetException().getMessage());
					LogUtil.info("Remote stack: ", e.getTargetException());
				} catch (InstantiationException e) {
					LogUtil.error("Error when updating the event for imvg "+ this.imvgId + ": InstantiationException " + e.getMessage());
				}
				catch(Exception e)
				{
					LogUtil.error("Got an exception while communicating with gateway. Will close connection." +this.imvgId);
					
					closeSocket(); 
					return;
				}
			}
		}

		private void closeSocket() {
				this.commandExecutor.killConnection(this.imvgId);
				if(this.socket != null){
				try 
				{
					if(this.socket.isClosed())
					{
							this.socket.close();
					}
				} catch (IOException e) {
					LogUtil.error("Error when closing the socket for iMVG(" + this.imvgId + ") : IOException - " + e.getMessage());
				}
			}
		}

		
}
