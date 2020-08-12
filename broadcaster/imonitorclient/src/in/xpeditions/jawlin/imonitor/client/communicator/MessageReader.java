/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.communicator;

import in.xpeditions.jawlin.imonitor.client.executor.CaptureImageExecutor;
import in.xpeditions.jawlin.imonitor.client.executor.DiscoverDeviceSuccessExecutor;
import in.xpeditions.jawlin.imonitor.client.executor.DiscoverDevicesExecutor;
import in.xpeditions.jawlin.imonitor.client.executor.GetLiveStreamExecutor;
import in.xpeditions.jawlin.imonitor.client.util.Constants;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Coladi
 *
 */
public class MessageReader implements Runnable{

	private DataInputStream dataInputStream;

	public MessageReader(DataInputStream dataInputStream) {
		this.dataInputStream = dataInputStream;
	}

	@Override
	public void run() {
		while(true){
			String readUTF;
			try {
				System.out.println("waiting for a message from imvg server ...");
				readUTF = this.dataInputStream.readUTF();
				System.out.println(readUTF);
				String property = "java.io.tmpdir";
				String tempDir = System.getProperty(property);
				 File f=new File(tempDir + "/my1.properties");
				 f.createNewFile();

				String tempFileName = tempDir + "/my1.properties";
				FileWriter fstream = new FileWriter(tempFileName);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(readUTF);
				out.close();
				Properties properties=new Properties();
				properties.load(new FileInputStream(tempFileName));
				
				String cmdId = (String) properties.get(Constants.CMD_ID);
//				Handling DISCOVER_DEVICES
				if(cmdId.compareTo(Constants.DISCOVER_DEVICES) == 0){
					DiscoverDevicesExecutor devicesExecutor = new  DiscoverDevicesExecutor(properties);
					Thread t = new Thread(devicesExecutor);
					t.start();
				}
//				Handling DEVICE_DISCOVERED_SUCCESS
				if(cmdId.compareTo(Constants.DEVICE_DISCOVERED_SUCCESS) == 0){
					DiscoverDeviceSuccessExecutor devicesExecutor = new  DiscoverDeviceSuccessExecutor(properties);
					Thread t = new Thread(devicesExecutor);
					t.start();
				}
//				Handling Constants.DEVICE_CONTROL
				if(cmdId.compareTo(Constants.DEVICE_CONTROL) == 0){
					DeviceControlExecutor devicesExecutor = new  DeviceControlExecutor(properties);
					Thread t = new Thread(devicesExecutor);
					t.start();
				}
//				Handling Constants.IMVG_UNREGISTER
				if(cmdId.compareTo(Constants.IMVG_UNREGISTER) == 0){
					ImvgUnRegisterExecutor unRegisterExecutor = new  ImvgUnRegisterExecutor(properties);
					Thread t = new Thread(unRegisterExecutor);
					t.start();
				}
//				Handling Constants.IMVG_UNREGISTER
				if(cmdId.compareTo(Constants.IPC_GET_LIVE_STREAM) == 0){
					GetLiveStreamExecutor unRegisterExecutor = new  GetLiveStreamExecutor(properties);
					Thread t = new Thread(unRegisterExecutor);
					t.start();
				}
//				Handling Constants.IPC_GET_CAPTURE_IMAGE
				if(cmdId.compareTo(Constants.IPC_GET_CAPTURE_IMAGE) == 0){
					CaptureImageExecutor captureImageExecutor = new  CaptureImageExecutor(properties);
					Thread t = new Thread(captureImageExecutor);
					t.start();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
